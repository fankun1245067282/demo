package com.fankun.io.channel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.management.ManagementFactory;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Arrays;
import java.util.Random;

public class FileLockTest {
	private static final int SIZEOF_INT = 4;
	private static final int INDEX_START = 0;
	private static final int INDEX_COUNT = 10;
	private static final int INDEX_SIZE = SIZEOF_INT*INDEX_COUNT;
	private ByteBuffer buffer = ByteBuffer.allocate(INDEX_SIZE);
	private IntBuffer indexBuffer = buffer.asIntBuffer();
	private Random rand = new Random();
	private String pid = null;
	public FileLockTest() {
//		import sun.management.ManagementFactory;    
		// get name representing the running Java virtual machine.  
		String name = ManagementFactory.getRuntimeMXBean().getName();  
		System.out.println(name);  
		// get pid  
		pid = name.split("@")[0];  
	}
	
	public static void main(String[] args) throws Exception {
		int n = 2;
		boolean writer = n==1?true:false;
		String filename = "D:/test/filelock.txt";
		RandomAccessFile raf = new RandomAccessFile(filename, writer?"rw":"r");
		FileChannel fc = raf.getChannel();
		FileLockTest lockTest = new FileLockTest();
		if(writer) {
			lockTest.doUpdates(fc);
		}else {
			lockTest.doQueries(fc);
		}
	}

	public void doQueries(FileChannel fc) throws IOException, InterruptedException {
		while(true) {
			println("trying for shared lock...");
			FileLock lock = fc.lock(INDEX_START, INDEX_SIZE, true);
			println("trying for shared lock... is shared:"+lock.isShared());
			int reps = rand.nextInt(20)+20;
		
			buffer.clear();
			fc.read(buffer,0);
			
			String str = "";
			for(int j=0; j<INDEX_COUNT; j++) {
				str+="  "+indexBuffer.get(j);
			}
			System.out.println("primary array: "+str);
			
			for (int i = 0; i < reps; i++) {
				int n = rand.nextInt(INDEX_COUNT);
				int position = INDEX_START+n*SIZEOF_INT;
				buffer.clear();
				fc.read(buffer,position);
				
				str = "";
				for(int j=0; j<INDEX_COUNT; j++) {
					str+="  "+indexBuffer.get(j);
				}
				System.out.println(" "+i+" array: "+str);
				int value = indexBuffer.get(n);
				println(" "+i+" Index entry "+n+"="+value);
				Thread.sleep(400);
			}
			lock.release();
			println("<sleeping>");
			Thread.sleep(rand.nextInt(2000)+1000);
		}
	}
	
	private int idxval = 1;
	public void doUpdates(FileChannel fc) throws IOException, InterruptedException {
		while(true) {
			println("trying for exclusive lock...");
			FileLock lock = fc.lock(INDEX_START, INDEX_SIZE, false);
			//update index.... start
			indexBuffer.clear();
			for (int i = 0; i < INDEX_COUNT; i++) {
				idxval++;
				println("Updating index "+i+"="+idxval);
				indexBuffer.put(idxval);
				Thread.sleep(500);
			}
			buffer.clear();
			fc.write(buffer, INDEX_START);
			//update index.... end
			lock.release();
			println("<sleeping>");
			Thread.sleep(rand.nextInt(1000)+1000);
		}
	}
	
	private int lastLineLen = 0;
	public void println(String msg) {
		System.out.print("\r "+pid+" ");
		System.out.print(msg);
		for (int i = msg.length(); i < lastLineLen; i++) {
			System.out.print(" ");
		}
		System.out.print("\r ");
		System.out.flush();
		lastLineLen = msg.length();
	}

}	
