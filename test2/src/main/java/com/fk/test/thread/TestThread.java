package com.fk.test.thread;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.security.AccessController;
import java.security.PrivilegedAction;

public class TestThread extends Thread{
	public static void main(String[] args) {
		ReferenceQueue<Class<?>> subclassAuditsQueue = new ReferenceQueue<>();
		
		WeakClassKey a = new WeakClassKey(String.class,subclassAuditsQueue);
		WeakClassKey b = new WeakClassKey(String.class,subclassAuditsQueue);
		System.out.println(a.hashCode());
		System.out.println(b.hashCode());
		System.out.println(a.equals(b));
		
		 Boolean result = AccessController.doPrivileged(
		            new PrivilegedAction<Boolean>() {
		                public Boolean run() {
		                    for (Class<?> cl = TestThread.class;
		                         cl != Thread.class;
		                         cl = cl.getSuperclass())
		                    {
		                        try {
		                            cl.getDeclaredMethod("getContextClassLoader", new Class<?>[0]);
		                            return Boolean.TRUE;
		                        } catch (NoSuchMethodException ex) {
		                        	System.out.println("===========1========");
		                        }
		                        try {
		                            Class<?>[] params = {ClassLoader.class};
		                            cl.getDeclaredMethod("setContextClassLoader", params);
		                            return Boolean.TRUE;
		                        } catch (NoSuchMethodException ex) {
		                        	System.out.println("===========2========");
		                        }
		                    }
		                    return Boolean.FALSE;
		                }
		            }
		        );
		 System.out.println(result);
		 try {
			TestThread.class.getDeclaredMethod("getContextClassLoader", new Class<?>[0]);
			System.out.println("333");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 try {
			 WeakClassKey.class.getDeclaredMethod("hashCode", new Class<?>[0]);
			 System.out.println("22222");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 try {
			 WeakClassKey.class.getDeclaredMethod("get", new Class<?>[0]);
			 System.out.println("llllll");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	 static class WeakClassKey extends WeakReference<Class<?>> {
	        /**
	         * saved value of the referent's identity hash code, to maintain
	         * a consistent hash code after the referent has been cleared
	         */
	        private final int hash;

	        /**
	         * Create a new WeakClassKey to the given object, registered
	         * with a queue.
	         */
	        WeakClassKey(Class<?> cl, ReferenceQueue<Class<?>> refQueue) {
	            super(cl, refQueue);
	            hash = System.identityHashCode(cl);
	        }

	        /**
	         * Returns the identity hash code of the original referent.
	         */
	        @Override
	        public int hashCode() {
	            return hash;
	        }

	        /**
	         * Returns true if the given object is this identical
	         * WeakClassKey instance, or, if this object's referent has not
	         * been cleared, if the given object is another WeakClassKey
	         * instance with the identical non-null referent as this one.
	         */
	        @Override
	        public boolean equals(Object obj) {
	            if (obj == this)
	                return true;

	            if (obj instanceof WeakClassKey) {
	                Object referent = get();
	                return (referent != null) &&
	                       (referent == ((WeakClassKey) obj).get());
	            } else {
	                return false;
	            }
	        }
	    }

}	
