package com.fk.test.references;
import java.io.IOException;
import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
public class TestReference {
    private static ReferenceQueue<Grocery> rq = new ReferenceQueue<Grocery>(); 
    private static int n = 0;
    public static void checkQueue(){ 
    		while(true) {
    			System.gc(); 
                Reference<? extends Grocery> inq = null;
    			try {
    				inq = rq.remove();
    			} catch (InterruptedException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    			
                if(inq!=null){ 
                	n++;
                	try {
                		System.out.println("clazz:"+ inq.getClass());
						Field field =  inq.getClass().getSuperclass().getDeclaredField("referent");
						field.setAccessible(true);//可以访问私有，final属性，修改它
						Object obj = field.get(inq);
						System.out.println("In queue num: "+n+"  value: "+inq.get()+"  value2: "+obj+"=="+inq); 
					} catch (Exception e) {
						e.printStackTrace();
					}
                }	
    		}

    } 
    
	/**
	 * 为了确保System.gc()后,SoftReference引用的referent被回收需要加入下面的参数
	 * -XX:SoftRefLRUPolicyMSPerMB=0
	 * 当垃圾收集后，进入到pending队列，不管reference.queue是否为null，为null的进入不了队列
	 * 断点发现：
	 * 当为weakReference时，pending对象的referent为空，
	 * 当为SoftReference时，pending对象的referent为空，
	 * 当为Finalizer时，pending对象的referent不为空，在加入queue时，也不为空
	 * 当为PhantomReference时，pending对象的referent不为空，在加入queue时，也不为空,queue.remove之后，可以
	 * 获取PhantomReference引用的referent属性不为空（通过反射获取），但是finalize已经执行一次了（重写了，没有重新写时，也不为空）
	 * 应该需要自己手动释放，因为PhantomReference主要是为了获取垃圾回收通知。。。
	 * clear可以导致不会加入队列，但是也不全是，当weakReference的个数大于等于8个时，
	 * 会出现一个进入队列的现象（特殊情况，当有SoftReference也clear时，就没有了，应该是一个bug）
	 * 
	 * -XX:SoftRefLRUPolicyMSPerMB=0时，虚引用不会进入队列
	 * 
	 */
    public static void main(String[] args){ 
            final int size = 10; 
            Set<SoftReference<Grocery>> sa = new HashSet<SoftReference<Grocery>>(); 
            for(int i=0;i<size;i++){ 
                    SoftReference<Grocery> ref = 
                            new SoftReference<Grocery>(new Grocery("Soft "+i),rq); 
                    System.out.println("Just Soft created: "+ref.get()); 
                    sa.add(ref); 
//                    ref.clear();
            } 
            System.gc(); 
            new Thread() {
            	public void run() {
            		System.gc(); 
            		checkQueue();
            	}
            }.start();
            Set<WeakReference<Grocery>> wa = new HashSet<WeakReference<Grocery>>(); 
            for(int i=0;i<size;i++){ 
                    WeakReference<Grocery> ref = 
                                    new WeakReference<Grocery>(new Grocery("Weak "+i),rq); 
                    System.out.println("Just created: "+ref.get()); 
                    wa.add(ref); 
//                    ref.clear();
            } 
            
            try {
				Thread.sleep(300);
				 System.gc(); 
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
            Set<PhantomReference<Grocery>> pa = new HashSet<PhantomReference<Grocery>>(); 
            for(int i=0;i<size;i++) { 
                    PhantomReference<Grocery> ref = 
                            new PhantomReference<Grocery>(new Grocery("Phantom "+i),rq); 
                    System.out.println("Just created: "+ref.get()); 
                    pa.add(ref); 
//                    ref.clear();
            } 
           
            try {
				Thread.sleep(100);
				 System.gc(); 
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
            
           try {
				System.in.read();
			} catch (IOException e) {
				e.printStackTrace();
			} 
    }
}
class Grocery { 
    private static final int SIZE=1024*1024; 
    private double[] d = new double[SIZE]; 
    private String id; 
    public Grocery(String id) { 
            this.id = id; 
    } 
    public String toString(){ 
            return id; 
    } 
//    public void finalize(){ 
//            System.out.println("Finalizing ... "+id); 
//    } 
} 