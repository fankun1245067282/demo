package com.spring.test.model.myproxy;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 代码生成,编译,重新动态load到jvm中
 * @author Administrator
 *
 */
public class MyClassLoader extends ClassLoader{

	private File baseDir;
	public MyClassLoader() {
		String basePath = MyClassLoader.class.getResource("").getPath();
		System.out.println("basePath:"+basePath);
		this.baseDir = new File(basePath);
	}
	
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		String className = MyClassLoader.class.getPackage().getName()+"."+name;
		System.out.println("className(fullPath):"+className);
		System.out.println("baseDir:"+baseDir);
		System.out.println("className:"+(name.replaceAll("\\.", "/")+".class"));
		if(baseDir!=null) {
			File classFile = new File(baseDir,name.replaceAll("\\.", "/")+".class");
			
			if(classFile.exists()) {
				FileInputStream in = null;
				ByteArrayOutputStream out = null;
				try {
					in = new FileInputStream(classFile);
					out = new ByteArrayOutputStream();
					byte[] buff = new byte[1024];
					int len;
					while((len=in.read(buff))!=-1) {
						out.write(buff, 0, len);
					}
					return defineClass(className, out.toByteArray(), 0,out.size());
					
				} catch (IOException e) {
					e.printStackTrace();
				}finally {
					if(null!=in) {
						try {
							in.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if(null!=out) {
						try {
							out.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					//删除.class文件
					classFile.delete();
				}
				
			}
		}
		return null;
	}
	
}
