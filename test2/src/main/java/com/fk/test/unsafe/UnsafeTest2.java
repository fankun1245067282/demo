package com.fk.test.unsafe;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
 
import org.junit.Before;
import org.junit.Test;
 
import sun.misc.Unsafe;
 
/**
 * @version 1.0
 * @author Fish
 */
public class UnsafeTest2 {
	public static Unsafe unsafe;
 
	@Before
	public void init() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		// 通过反射获取rt.jar下的Unsafe类
		Field field = Unsafe.class.getDeclaredField("theUnsafe");
		field.setAccessible(true);
		unsafe = (Unsafe) field.get(null);
	}
 
	/**
	 * allocateInstance初始化对象不调用构造方法
	 * 
	 * @Description:
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @author Fish
	 * @date 2015年6月24日
	 */
	@Test
	public void testAllocateInstance() throws InstantiationException, IllegalAccessException {
		UnsafeClass un = new UnsafeClass(); // constructor
		System.out.println(un.a()); // prints 1
		UnsafeClass uu2 = UnsafeClass.class.newInstance(); // reflection
		System.out.println(uu2.a()); // prints 1
		UnsafeClass uu3 = (UnsafeClass) unsafe.allocateInstance(UnsafeClass.class); // unsafe
		System.out.println(uu3.a());// prints 0
	}
 
	/**
	 * 修改private变量的值
	 * 
	 * @Description:
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @author Fish
	 * @date 2015年6月24日
	 */
	@Test
	public void testObjectFieldOffset() {
		try {
			UnsafeClass un = new UnsafeClass();
			System.out.println(un.isEqual());
			Field f = un.getClass().getDeclaredField("a");
			unsafe.putInt(un, unsafe.objectFieldOffset(f), 42); // memory
			System.out.println(un.isEqual()); // true, access granted
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
 
	/**
	 * 获取对象的大小
	 * @Description: 
	 * @author Fish
	 * @date 2015年6月24日
	 */
	@Test
	public void testSizeof() {
		UnsafeClass un = new UnsafeClass();
		System.out.println(sizeOf1(un)); 
		SuperArray sa = new SuperArray(0);
		System.out.println(sizeOf1(sa)); 
	}
 
	public static long sizeOf1(Object object) {
		HashSet<Field> fields = new HashSet<Field>();
		Class<?> c = object.getClass();
		while (c != Object.class) {
			for (Field f : c.getDeclaredFields()) {
				if ((f.getModifiers() & Modifier.STATIC) == 0) {
					fields.add(f);
//					System.out.println("t:"+f); 
				}else {
//					System.out.println("f:"+f);这是静态属性
				}
			}
			c = c.getSuperclass();
		}
		// get offset
		long maxSize = 0;
		for (Field f : fields) {
			long offset = unsafe.objectFieldOffset(f);
			if (offset > maxSize) {
				maxSize = offset;
			}
		}
		System.out.println("max:"+maxSize);
		return ((maxSize / 8) + 1) * 8; // padding
	}
 
	/**
	 * 浅拷贝：该方法可以用来拷贝任何类型的对象，动态计算它的大小。
	 * 
	 * @Description:
	 * @author Fish
	 * @date 2015年6月24日
	 */
	@Test
	public void testShallowCopy() {
		List<String> list = new ArrayList<String>();
		list.add("123A");
		list.add("123B");
		list.add("123C");
		long size = sizeOf1(list);
		long start = toAddress(list);
		System.out.println("start："+start);
		long address = unsafe.allocateMemory(size);
		System.out.println("to："+address);
		unsafe.copyMemory(start, address, size);
		System.out.println(fromAddress(address));
	}
 
	private static long normalize(int value) {
		if (value >= 0)
			return value;
		return (~0L >>> 32) & value;
	}
 
	public static long toAddress(Object obj) {
		Object[] array = new Object[] { obj };
		//数组的每个元素是一个地址
		long baseOffset = unsafe.arrayBaseOffset(Object[].class);
		//获取数组的第一个元素的值，是一个地址，就是obj地址
		return normalize(unsafe.getInt(array, baseOffset));
	}
 
	static Object fromAddress(long address) {
		Object[] array = new Object[] { null };
		long baseOffset = unsafe.arrayBaseOffset(Object[].class);
		unsafe.putLong(array, baseOffset, address);
		return array[0];
	}
 
	/**
	 * 密码隐藏：通常我们会将用完的密码设置为null，但是在设置为null到GC垃圾回收该对象，是有一定的时间。在这段时间内，该对象还存在于内存中，
	 * 就在这段时间内，很可能被黑客利用
	 * 
	 * @Description:
	 * @author Fish
	 * @date 2015年6月24日
	 */
	@Test
	public void testPassword() {
		String password = new String("l00k@myHor$e");
		String fake = new String(password.replaceAll(".", "?"));
		System.out.println(password); // l00k@myHor$e
		System.out.println(fake); // ????????????
		unsafe.copyMemory(fake, 0L, null, toAddress(password), sizeOf1(password));
		
//		unsafe.copyMemory(toAddress(fake), toAddress(password), sizeOf1(password));
		System.out.println(password); // ????????????
		System.out.println(fake); // ????????????
	}
 
	 /**
	 * 动态创建类
	 * @Description:
	 * @author Fish
	 * @date 2015年6月24日
	 */
	 @Test
	 public void testDynamicClasses() {
//		 try {
//			 File f = new File("D:\\A.class");
//			 FileInputStream input = new FileInputStream(f);
//			 byte[] content = new byte[(int) f.length()];
//			 input.read(content);
//			 input.close();
                        // unsafe.defineClass(null, content, 0, content.length);该方法在JDK8找不到</span>
//                         Class<?> c = unsafe.defineClass(null, content, 0, content.length);
//			 c.getMethod("a").invoke(c.newInstance(), null); // 1
//		 } catch (FileNotFoundException e) {
//			 e.printStackTrace();
//		 } catch (IOException e) {
//			 e.printStackTrace();
//		 }
	 }
 
	/**
	 * 大数组：这种方式的内存分配不在堆上，且不受GC管理，所以必须小心Unsafe.freeMemory()的使用
	 * @Description: 
	 * @author Fish
	 * @date 2015年6月24日
	 */
	@Test
	public void testBigArray() {
		long SUPER_SIZE = (long) Integer.MAX_VALUE * 2;
		SuperArray array = new SuperArray(SUPER_SIZE);
		System.out.println("Array size:" + array.size()); // 4294967294
		int sum = 0;
		for (int i = 0; i < 100; i++) {
			array.set((long) Integer.MAX_VALUE + i, (byte)i);
			sum += array.get((long) Integer.MAX_VALUE + i);
		}
		System.out.println("Sum of 100 elements:" + sum); // 300
	}
 
}
 
class SuperArray {
	private final static int BYTE = 1;
 
	private long size;
 
	private long address;
 
	public SuperArray(long size) {
		this.size = size;
		address = UnsafeTest2.unsafe.allocateMemory(size * BYTE);
	}
 
	public void set(long i, byte value) {
		UnsafeTest2.unsafe.putByte(address + i * BYTE, value);
	}
 
	public int get(long idx) {
		return UnsafeTest2.unsafe.getByte(address + idx * BYTE);
	}
 
	public long size() {
		return size;
	}
}
 
class UnsafeClass {
	private long a = 2;
 
	public UnsafeClass() {
		this.a = 1;
	}
 
	public long a() {
		return this.a;
	}
 
	public boolean isEqual() {
		return 42 == a;
	}
}