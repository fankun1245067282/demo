package com.fankun._02.nio;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class ByteBufferTest {

	/**
	 * 
	    当读取到码流后，进行解码。首先对ByteBuffer进行flip操作，
	    它的作用是将缓冲区当前的limit设置为position,position设置为0
	 flip方法将Buffer从写模式切换到读模式。调用flip()方法会将position设回0，并将limit设置成之前position的值。
	    这里的flip()方法，在详细的描述一下，其事这里是理解position和limit这两个属性的关键。
	    用于后续对缓冲区的读取操作。然后根据缓冲区可读的字节个数创建字节数组，
	    调用ByteBuffer的get操作将缓冲区可读的字节(获取position到limit的字节)
	    数组复制到新创建的字节数组中，最后调用字符串的构造函数创建请求消息体并打印。
	 */
	public static void testRemaining() {
		System.out.println("------testRemaining----------------------");
		ByteBuffer buffer = ByteBuffer.wrap("I am a man".getBytes());
		System.out.println(buffer.position());
		System.out.println(buffer.limit());
		System.out.println(buffer.remaining());
		byte[] b = new byte[4];
		buffer.get(b);
		System.out.println(new String(b));
		System.out.println("----------------------------");
		System.out.println(buffer.position());//将要读取的位置，从0开始
		System.out.println(buffer.limit());//可以读取的最终位置
		System.out.println(buffer.remaining());//剩余可以读取的长度
		buffer.flip();
		//filp是将limit变成position，再讲position变成0，remaining就是limit-position
		System.out.println("----------------------------");
		System.out.println(buffer.position());
		System.out.println(buffer.limit());
		System.out.println(buffer.remaining());
		System.out.println("----------------------------");
	}
	
	/**
	 * 
	 */
	public static void testmark() {
		System.out.println("------testmark----------------------");
        String str = "helloWorld";  
        ByteBuffer buff  = ByteBuffer.wrap(str.getBytes());  
        System.out.println("position:"+buff.position()+"\t limit:"+buff.limit());  
        System.out.println("----------------------------");
	}
	public static void main(String[] args) {
//		testRemaining();
//		testmark();
//		allocateTest();
//		flipFunTest();
//		clearFunTest();
//		rewindFunTest();
		markFunTest();

	}
	
	   /**
     * 测试ByteBuffer.allocate(int capacity)方法
     * 作用：分配指定capacity大小的缓冲区，默认存的数据为0
     * 例如：分配指定15字节的缓冲区，那么缓冲区中默认存的是15个0
     */
    public static void allocateTest(){
        ByteBuffer buffer = ByteBuffer.allocate(15); //15字节缓冲区，注意：分配的缓冲区，默认存的是15个0
        System.out.println("【allocateTest】上限：" + buffer.limit() + " 容量：" + buffer.capacity() + " 位置：" + buffer.position());
        System.out.println("【allocateTest】刚分配(allocate)的buffer缓冲区中的数据：");
        for(int i = 0; i < buffer.limit(); i++){
              System.out.println(buffer.get());
        }
    }
 
    /**
     * 测试ByteBuffer.flip()方法
     * 作用：重置位置为0，上限值变成重置前的位置
     * 例如：有个容量为15字节的buffer，重置前的位置为10，那么重置后，位置 0，上限 10 ，容量 15
     */
    public static void flipFunTest(){
        ByteBuffer buffer = ByteBuffer.allocate(15); //15字节缓冲区，注意：分配的缓冲区，默认存的是15个0
        System.out.println("【flipFunTest】 1 上限：" + buffer.limit() + " 容量：" + buffer.capacity() + " 位置：" + buffer.position());
        for(int i = 0; i < 10; i++){
            buffer.put((byte)i);
        }
        System.out.println("【flipFunTest】 2 上限：" + buffer.limit() + " 容量：" + buffer.capacity() + " 位置：" + buffer.position());
        buffer.flip();
        System.out.println("【flipFunTest】 3 上限：" + buffer.limit() + " 容量：" + buffer.capacity() + " 位置：" + buffer.position());
        for(int i = 0; i < 4; i++){
            buffer.get();
        }
        System.out.println("【flipFunTest】 4 上限：" + buffer.limit() + " 容量：" + buffer.capacity() + " 位置：" + buffer.position());
        buffer.flip();
        System.out.println("【flipFunTest】 5 上限：" + buffer.limit() + " 容量：" + buffer.capacity() + " 位置：" + buffer.position());
        System.out.println("【flipFunTest】buffer缓冲区中的数据：");
        for(int i = 0; i < buffer.limit(); i++){
              System.out.println(buffer.get());
        }
    }
 
 
    /**
     * 测试ByteBuffer.clear()方法
     * 作用：位置重置为0，但是和flip()方法不同，上限值为重置前的缓冲区的容量大小
     */
    public static void clearFunTest(){
        ByteBuffer buffer = ByteBuffer.allocate(15); //15字节缓冲区，注意：分配的缓冲区，默认存的是15个0
        System.out.println("【clearFunTest】 1 上限：" + buffer.limit() + " 容量：" + buffer.capacity() + " 位置：" + buffer.position());
        for(int i = 0; i < 10; i++){
            buffer.put((byte)i);
        }
        System.out.println("【clearFunTest】 2 上限：" + buffer.limit() + " 容量：" + buffer.capacity() +  "位置：" + buffer.position());
        buffer.clear();
        System.out.println("【clearFunTest】 3 上限：" + buffer.limit() + " 容量：" + buffer.capacity() +  "位置：" + buffer.position());
        for(int i = 0; i < 4; i++){
            buffer.get();
        }
        buffer.flip();
        System.out.println("【clearFunTest】 4 上限：" + buffer.limit() + " 容量：" + buffer.capacity() +  " 位置：" + buffer.position());
        buffer.clear();
        System.out.println("【clearFunTest】 5 上限：" + buffer.limit() + " 容量：" + buffer.capacity() +  " 位置：" + buffer.position());
        System.out.println("【clearFunTest】buffer缓冲区中的数据：");
        for(int i = 0; i < buffer.limit(); i++){
              System.out.println(buffer.get());
        }
    }
 
 
    /**
     * 测试ByteBuffer.rewind()方法
     * 作用：位置重置为0，上限值不会改变，上限值还是重置前的值
     */
    public static void rewindFunTest(){
        ByteBuffer buffer = ByteBuffer.allocate(15); //15字节缓冲区，注意：分配的缓冲区，默认存的是15个0
        System.out.println("【rewindFunTest】 1 上限：" + buffer.limit() + " 容量：" + buffer.capacity() + " 位置：" + buffer.position());
        for(int i = 0; i < 10; i++){
            buffer.put((byte)i);
        }
        System.out.println("【rewindFunTest】 2 上限：" + buffer.limit() + " 容量：" + buffer.capacity() +  " 位置：" + buffer.position());
        buffer.rewind();
        System.out.println("【rewindFunTest】 3 上限：" + buffer.limit() + " 容量：" + buffer.capacity() +  " 位置：" + buffer.position());
        for(int i = 0; i < 4; i++){
            buffer.get();
        }
        System.out.println("【rewindFunTest】 4 上限：" + buffer.limit() + " 容量：" + buffer.capacity() +  " 位置：" + buffer.position());
        buffer.rewind();
        System.out.println("【rewindFunTest】 5 上限：" + buffer.limit() + " 容量：" + buffer.capacity() +  " 位置：" + buffer.position());
        for(int i = 0; i < 4; i++){
            buffer.get();
        }
        buffer.flip();
        System.out.println("【rewindFunTest】 6 上限：" + buffer.limit() + " 容量：" + buffer.capacity() +  " 位置：" + buffer.position());
        buffer.rewind();
        System.out.println("【rewindFunTest】 7 上限：" + buffer.limit() + " 容量：" + buffer.capacity() +  " 位置：" + buffer.position());
        System.out.println("【rewindFunTest】buffer缓冲区中的数据：");
        for(int i = 0; i < buffer.limit(); i++){
              System.out.println(buffer.get());
        }
    }
 
    /**
     * 测试ByteBuffer.mark()方法
     * 作用：可以对当前位置进行标记，以后使用ByteBuffer.reset()方法
     *      可以使缓冲区的位置重置为以前标记的位置,从而可以返回到标记的位置
     *      对缓冲区的数据进行操作
     */
    public static void markFunTest(){
        ByteBuffer buffer = ByteBuffer.allocate(15); //15字节缓冲区，注意：分配的缓冲区，默认存的是15个0
        for(int i=0; i < 10; i++){
            buffer.put((byte)i);
        }
        buffer.clear();
        System.out.println("【markFunTest】上限：" + buffer.limit() + " 容量：" + buffer.capacity() +  " 位置：" + buffer.position());
        byte[] b = new byte[15];
        buffer.get(b);
        System.out.println("【markFunTest】buffer缓冲区中的数据："+Arrays.toString(b));
//        for(int i = 0; i < buffer.limit(); i++){
//              System.out.println(buffer.get());
//        }
        System.out.println("【markFunTest】上限：" + buffer.limit() + " 容量：" + buffer.capacity() +  " 位置：" + buffer.position());
        buffer.clear();
        System.out.println("【markFunTest】上限：" + buffer.limit() + " 容量：" + buffer.capacity() +  " 位置：" + buffer.position());
        //使用mark方法进行标记，在位置为4处进行标记
        buffer.position(4);
        buffer.mark();
        System.out.println("标志的位置：" + buffer.position());
        System.out.println("【markFunTest】上限：" + buffer.limit() + " 容量：" + buffer.capacity() +  " 位置：" + buffer.position());
        buffer.reset(); //将此缓冲区的位置重置为以前标记的位置
        System.out.println("【markFunTest】上限：" + buffer.limit() + " 容量：" + buffer.capacity() +  " 位置：" + buffer.position());
        //判断在当前位置和上限(最大的位置)之间是否有元素
        boolean isFirst = true;
        while(buffer.hasRemaining()){
            if(isFirst){
                System.out.println("【markFunTest】当前位置和上限(最大的位置)之间的数据：");
                isFirst = false;
            }
            System.out.println(buffer.get());
        }
        //修改标志的位置的元素值
        buffer.reset();
        buffer.put((byte)100);
        buffer.clear();
        
        b = new byte[15];
        buffer.get(b);
        System.out.println("【markFunTest】buffer缓冲区中的数据："+Arrays.toString(b));
    }

}
