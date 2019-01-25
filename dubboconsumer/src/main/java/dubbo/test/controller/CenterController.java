package dubbo.test.controller;

import java.util.Date;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dubbo.test.bean.TestTB;
import dubbo.test.interfaces.TestTbService;

@RestController
@RequestMapping("/dubbo")
public class CenterController {
//public class CenterController implements ApplicationContextAware {
    
    @Autowired
    private TestTbService testTbService;//这个不行

    //测试
    @RequestMapping(value = "/index.do")
    public String index(Model model){
    	System.out.println("===============/dubbo/index.do");
        TestTB testTb = new TestTB();
        testTb.setName("钱学森");
        System.out.println(testTb);
        testTbService.insertTestTb(testTb); 
        return "index";
        
    }
    
    @RequestMapping("/testbody.do")//使用(value="/testbody.do"),返回不正确，使用("/testbody.do")可以，无语了
    @ResponseBody
    public Object returnbody() {
    	System.out.println("================testbody");
//    	return "{name:\"tom\"}";
    	return new TestTB().toString();//返回new TestTB()不可以，提示“NOT_ACCEPTBLE”,new TestTB().toString()可以
    }
}