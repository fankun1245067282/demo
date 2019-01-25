package com.fk.test.unsafe;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

public class UnsafeTest1 {
	private static Unsafe unsafe;
 
	public static void main(String[] args) throws Exception {
		try {
			//通过反射获取rt.jar下的Unsafe类
			Field field = Unsafe.class.getDeclaredField("theUnsafe");
			field.setAccessible(true);
			unsafe = (Unsafe) field.get(null);
			Integer target = 12;
			//compareAndSwapInt方法的属性分别是：目标对象实例，目标对象属性偏移量，当前预期值，要设的值.
			//compareAndSwapInt方法是通过反射修改对象的值，具体修改对象下面那个值，可以通过偏移量，对象字段的偏移量可以通过objectFieldOffset获取
			System.out.println(unsafe.compareAndSwapInt(target, 12, 11, 10));
			System.out.println(target);
			long offset =  unsafe.objectFieldOffset(Integer.class.getDeclaredField("value"));
			System.out.println("offset:"+offset);
			//这个报错了：Get Unsafe instance occur errorjava.lang.IllegalArgumentException
//			long staticOffset =  unsafe.staticFieldOffset(Integer.class.getDeclaredField("value"));
//			System.out.println("staticOffset:"+staticOffset);
			System.out.println(unsafe.compareAndSwapInt(target, offset, 12, 10));
			System.out.println(target);
		} catch (Exception e) {
			System.out.println("Get Unsafe instance occur error" + e);
		}
	}
}
