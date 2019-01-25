package com.fk.test.unsafe;

import java.lang.reflect.Field;
import java.util.Arrays;

import sun.misc.Unsafe;

public class TestUnsafe {
	public static void main(String[] args) throws Exception {
//		Unsafe unsafe = Unsafe.getUnsafe();
//	        
//		int n1 = unsafe.arrayIndexScale(new int[] {}.getClass());
//		System.out.println(n1);
		
        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        Unsafe UNSAFE = (Unsafe) theUnsafe.get(null);
        System.out.println(UNSAFE);
		int n = UNSAFE.arrayIndexScale(new long[] {}.getClass());
		System.out.println(n);
		n = UNSAFE.arrayIndexScale(byte[].class);
		System.out.println(n);
		
		byte[] bytedata = new byte[10];
		System.out.println("bytedata:" + Arrays.toString(bytedata));
		int byteArrayBaseOffset = UNSAFE.arrayBaseOffset(byte[].class);

		System.out.println("byteArrayBaseOffset:"+byteArrayBaseOffset);
		UNSAFE.putByte(bytedata, byteArrayBaseOffset, (byte) 1);
		UNSAFE.putByte(bytedata, byteArrayBaseOffset + 5, (byte) 5);
		System.out.println(Arrays.toString(bytedata));

		int[] intdata = new int[10];
		System.out.println("intdata:" + Arrays.toString(intdata));
		int intArrayBaseOffset = UNSAFE.arrayBaseOffset(int[].class);
		int intIndexScale = UNSAFE.arrayIndexScale(int[].class);
		System.out.println("intArrayBaseOffset:"+intArrayBaseOffset);
		System.out.println("intIndexScale:" + intIndexScale);
		UNSAFE.putInt(intdata, intArrayBaseOffset + intIndexScale, 18);
		UNSAFE.putInt(intdata, intArrayBaseOffset + intIndexScale * 4, 19);
		System.out.println("intdata:" + Arrays.toString(intdata));
		//测试setMemory方法：
		UNSAFE.setMemory(intdata, intArrayBaseOffset, 4, (byte)1);
		System.out.println("intdata:" + Arrays.toString(intdata));
		System.out.println("bytedata:" + Arrays.toString(bytedata));
		
		System.out.println(16843009/256/256/256%256);
		System.out.println(16843009/256/256%256);
		System.out.println(16843009/256%256);
		System.out.println(16843009%256);
	}
}
