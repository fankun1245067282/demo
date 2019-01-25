package com.fk.test.zk;

import java.io.File;
import java.io.IOException;

public class TestFile {
	public static String rootPath = "D:\\home\\ccmt\\poc-ywf\\logs";
	
	public static void main(String[] args) throws IOException {
		
		for (int i = 0; i <20; i++) {
			File f = new File(rootPath+"/"+i+"x.txt");
			f.createNewFile();
		}
	}
	
}
