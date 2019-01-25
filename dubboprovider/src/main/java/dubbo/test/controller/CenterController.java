package dubbo.test.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dubbo.test.bean.TestTB;
import dubbo.test.interfaces.TestTbService;


@Controller
@RequestMapping("/test")
public class CenterController {
    
    @Autowired
    private TestTbService testTbService;
    //测试
    @RequestMapping(value = "/index.do")
    public String index(){
    	System.out.println("========111=======/test/index.do");
        TestTB testTb = new TestTB();
        testTb.setName("范冰冰");
        testTb.setBirthday(new Date());
        testTbService.insertTestTb(testTb); 
        return "index";
        
    }
}