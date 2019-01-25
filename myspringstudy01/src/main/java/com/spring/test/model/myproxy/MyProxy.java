package com.spring.test.model.myproxy;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

/**
 * 生成代理对象的代码(jdk的动态代理)
 * @author Administrator
 *
 */
public class MyProxy {
	
	private static String ln = "\r\n";
	public static Object newProxyInstance(
			MyClassLoader loader, Class<?>[] interfaces, MyInvocationHandler h) throws IOException, ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		System.out.println("动态代理方法开始生成--------------");
		//1、生成源码
		String proxySrc = generateSrc(interfaces[0]);
		
		//2、将生成的源码输出到磁盘，保存为.java文件(把代码复制到eclipse里面，看看是否有异常)
		String filePath = MyProxy.class.getResource("").getPath();
		System.out.println("filePath："+filePath);
		File f = new File(filePath+"$Proxy0.java");
		FileWriter fw = new FileWriter(f);
		fw.write(proxySrc);
		fw.flush();
		fw.close();
		
		//3、编译源码，并且生成.class文件
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		System.out.println("compiler==null:"+(compiler==null));//为空的原因，是因为tools.jar没有加入到jre/lib路径下
		StandardJavaFileManager manager = compiler.getStandardFileManager(null, null, null);
		Iterable iterable = manager.getJavaFileObjects(f);
		
		CompilationTask task = compiler.getTask(null, manager, null	, null, null, iterable);
		task.call();
		manager.close();
		
		//4、将.class文件中的内容，动态加载到JVM中
		Class proxyClass = loader.findClass("$Proxy0");
		Constructor<?> constructor = proxyClass.getConstructor(MyInvocationHandler.class);
		
		//5、返回被代理后的代理对象
		System.out.println("动态代理方法结束生成--------------");
		//删除.java文件
		//f.delete();
		return constructor.newInstance(h);
	}
	
	private static String generateSrc(Class<?> interfaces) {
		StringBuffer src = new StringBuffer();
		src.append("package com.spring.test.model.myproxy;").append(ln);
		//导入类
		src.append("import java.lang.reflect.Method;").append(ln);
		//类定义
		src.append("public class $Proxy0 implements " +interfaces.getName()+ "{"+ln);
		//属性
		src.append("MyInvocationHandler h;").append(ln);
		//构造方法
		src.append("public $Proxy0(MyInvocationHandler h){").append(ln);
		src.append("this.h = h;").append(ln);
		src.append("}").append(ln);
		//一般方法
		for(Method m:interfaces.getMethods()) {
			//方法参数
			Class<?>[] parameterTypes = m.getParameterTypes();
			Parameter[] parameters = m.getParameters();
			String parametersStr = "";
			String parameterTypesStr = "";
			if(parameterTypes!=null && parameterTypes.length>0) {
				for (int i = 0; i < parameters.length; i++) {
					parametersStr+= parameterTypes[i].getName()+" "+parameters[i].getName()+",";
					parameterTypesStr+= parameterTypes[i].getName()+".class,";
				}
			}
			if(!"".equals(parametersStr)) {
				parametersStr = parametersStr.substring(0, parametersStr.length()-1);
				parameterTypesStr = parameterTypesStr.substring(0, parameterTypesStr.length()-1);
			}
			System.out.println("parametersStr："+parametersStr);
			System.out.println("parameterTypesStr："+parameterTypesStr);
			//方法抛出的异常
			Class<?>[] exceptionTypes = m.getExceptionTypes();
			String exceptionTypesStr = "";
			if(exceptionTypes!=null && exceptionTypes.length>0) {
				for (int i = 0; i < exceptionTypes.length; i++) {
					exceptionTypesStr+= exceptionTypes[i].getName()+",";
				}
			}
			if(!"".equals(exceptionTypesStr)) {
				exceptionTypesStr = " throws "+exceptionTypesStr.substring(0, exceptionTypesStr.length()-1);
			}
			System.out.println("exceptionTypesStr："+exceptionTypesStr);
			//方法签名
			src.append("public "+m.getReturnType().getName()+" "+m.getName()+"("+parametersStr+")"+exceptionTypesStr+"{ ").append(ln);
			
			//方法体
			if("".equals(exceptionTypesStr)) {//没有异常抛出，自己捕获，再抛出
				src.append("try{").append(ln);
				
				src.append("Method m = "+interfaces.getName()+".class.getMethod(\""+m.getName()+"\",new Class[]{"+parameterTypesStr+"});").append(ln);
				//判断是否有返回值：有返回值
				if(!"void".equals(m.getReturnType().getName())){
					src.append("return ("+m.getReturnType().getName()+")");
				}
				src.append("this.h.invoke(this,m,null);").append(ln);
				
				src.append("}catch(java.lang.Throwable e){").append(ln);
				src.append("throw new java.lang.RuntimeException(e);").append(ln);
				src.append("}").append(ln);
				
			}else {//有异常抛出，不处理
				
				src.append("Method m = "+interfaces.getName()+".class.getMethod(\""+m.getName()+"\",new Class[]{"+parameterTypesStr+"});").append(ln);
				//判断是否有返回值：有返回值
				if(!"void".equals(m.getReturnType().getName())){
					src.append("return ("+m.getReturnType().getName()+")");
				}
				src.append("this.h.invoke(this,m,null);").append(ln);
			}

			
			src.append("}").append(ln);
		}
		src.append("}");
		return src.toString();
	}
}
