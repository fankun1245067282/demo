package com.gupaoedu.vip.mvc.demo.mvc.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gupaoedu.vip.mvc.demo.service.INamedService;
import com.gupaoedu.vip.mvc.demo.service.IService;
import com.gupaoedu.vip.mvc.framework.annotation.GPAutowired;
import com.gupaoedu.vip.mvc.framework.annotation.GPController;
import com.gupaoedu.vip.mvc.framework.annotation.GPRequestMapping;
import com.gupaoedu.vip.mvc.framework.annotation.GPRequestParam;
import com.gupaoedu.vip.mvc.framework.servlet.GPModelAndView;
/**
 * 由于经常使用springboot，忘记了输入应用名称。。。。。。
 * @author Administrator
 *
 */
@GPController
@GPRequestMapping("/web")
public class FirstAction {

	@GPAutowired private IService service;
	
	@GPAutowired("myName") private INamedService namedService;
	
	/**
	 * 测试url：
	 * http://localhost:8080/gupaoedu-vip-springmvc/web/query/a.json
	 * http://localhost:8080/gupaoedu-vip-springmvc/web/query/a.json?name=fankun&addr=china
	 * @param request
	 * @param response
	 * @param name
	 * @param addr
	 * @return
	 */
	@GPRequestMapping("/query/a.json")
//	@GPResponseBody
	public GPModelAndView query(HttpServletRequest request,HttpServletResponse response, 
			@GPRequestParam(value="name",required=false) String name,
			@GPRequestParam("addr") String addr){
		//out(response,"get params name = " + name);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("name", name);
		model.put("addr", addr);
		return new GPModelAndView("first.pgml",model);
	}
	
	
	@GPRequestMapping("/add.json")
	public GPModelAndView add(HttpServletRequest request,HttpServletResponse response){
		out(response,"this is json string");
		return null;
	}
	
	
	
	
	public void out(HttpServletResponse response,String str){
		try {
			response.getWriter().write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
