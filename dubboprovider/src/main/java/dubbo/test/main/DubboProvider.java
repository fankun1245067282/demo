package dubbo.test.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DubboProvider {  
  
    public static void main(String[] args) {  
        try {  
            // 用于加载Spring的配置文件  
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(  
                    "applicationContext.xml");  
            System.out.println("DubboProvider start");
            context.start();  
        } catch (Exception exception) {  
            throw new RuntimeException("DubboProvider context start error: "  
                    + exception);  
        }  
        synchronized (DubboProvider.class) {  
            while (true) {  
                try {  
                    DubboProvider.class.wait();  
                } catch (InterruptedException e) {  
                    throw new RuntimeException("synchronized error: " + e);  
                }  
            }  
  
        }  
  
    }  
}  