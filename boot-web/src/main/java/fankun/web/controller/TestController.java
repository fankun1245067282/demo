package fankun.web.controller;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

@RestController
public class TestController {
	
	@PostMapping(path="/api/province/query_all_province")
//	@GetMapping(path="/api/province/query_all_province",produces="text/plain;charset=UTF-8")
	public String index(HttpServletResponse response) {
//		不起作用
//		response.setContentType("text/html;charset=UTF-8");
//		response.setCharacterEncoding("UTF-8");
		String jsonStr = "{\"errorLog\":null,\"status\":0,\"message\":\"成功\",\"data\":{\"lists\":[{\"provinceid\":1,\"provincename\":\"广东省\"},{\"provinceid\":2,\"provincename\":\"江苏省\"},{\"provinceid\":3,\"provincename\":\"浙江省\"},{\"provinceid\":4,\"provincename\":\"上海市\"},{\"provinceid\":5,\"provincename\":\"山东省\"},{\"provinceid\":6,\"provincename\":\"北京市\"},{\"provinceid\":7,\"provincename\":\"河南省\"},{\"provinceid\":8,\"provincename\":\"湖北省\"},{\"provinceid\":9,\"provincename\":\"福建省\"},{\"provinceid\":10,\"provincename\":\"湖南省\"},{\"provinceid\":11,\"provincename\":\"四川省\"},{\"provinceid\":12,\"provincename\":\"河北省\"},{\"provinceid\":13,\"provincename\":\"广西壮族自治区\"},{\"provinceid\":14,\"provincename\":\"安徽省\"},{\"provinceid\":15,\"provincename\":\"辽宁省\"},{\"provinceid\":16,\"provincename\":\"山西省\"},{\"provinceid\":17,\"provincename\":\"江西省\"},{\"provinceid\":18,\"provincename\":\"陕西省\"},{\"provinceid\":19,\"provincename\":\"重庆市\"},{\"provinceid\":20,\"provincename\":\"天津市\"},{\"provinceid\":21,\"provincename\":\"黑龙江省\"},{\"provinceid\":22,\"provincename\":\"云南省\"},{\"provinceid\":23,\"provincename\":\"新疆维吾尔自治区\"},{\"provinceid\":24,\"provincename\":\"贵州省\"},{\"provinceid\":25,\"provincename\":\"吉林省\"},{\"provinceid\":26,\"provincename\":\"内蒙古自治区\"},{\"provinceid\":27,\"provincename\":\"甘肃省\"},{\"provinceid\":28,\"provincename\":\"海南省\"},{\"provinceid\":29,\"provincename\":\"宁夏回族自治区\"},{\"provinceid\":30,\"provincename\":\"青海省\"},{\"provinceid\":31,\"provincename\":\"西藏自治区\"},{\"provinceid\":32,\"provincename\":\"澳门\"},{\"provinceid\":33,\"provincename\":\"香港\"},{\"provinceid\":34,\"provincename\":\"台湾\"},{\"provinceid\":35,\"provincename\":\"印度尼西亚\"},{\"provinceid\":36,\"provincename\":\"泰国\"},{\"provinceid\":37,\"provincename\":\"马来西亚\"},{\"provinceid\":38,\"provincename\":\"新加坡\"},{\"provinceid\":39,\"provincename\":\"美国\"},{\"provinceid\":40,\"provincename\":\"加拿大\"},{\"provinceid\":41,\"provincename\":\"澳大利亚\"},{\"provinceid\":42,\"provincename\":\"新西兰\"},{\"provinceid\":43,\"provincename\":\"日本\"},{\"provinceid\":44,\"provincename\":\"韩国\"},{\"provinceid\":45,\"provincename\":\"英国\"},{\"provinceid\":46,\"provincename\":\"法国\"},{\"provinceid\":47,\"provincename\":\"德国\"},{\"provinceid\":48,\"provincename\":\"意大利\"},{\"provinceid\":49,\"provincename\":\"瑞典\"},{\"provinceid\":50,\"provincename\":\"俄罗斯\"},{\"provinceid\":51,\"provincename\":\"印度\"}]}}";
		System.out.println("---------v-vvvvvv-");
		JSONObject obj = JSONObject.parseObject(jsonStr);
		System.out.println("------------------------："+obj.toJSONString());
		return obj.toJSONString();
	}
	
	@GetMapping(path="/api/province/query_all_province2")
//	@GetMapping(path="/api/province/query_all_province",produces="text/plain;charset=UTF-8")
	public String index2(HttpServletResponse response) {
//		不起作用
//		response.setContentType("text/html;charset=UTF-8");
//		response.setCharacterEncoding("UTF-8");
		String jsonStr = "{\"errorLog\":null,\"status\":0,\"message\":\"成功\",\"data\":{\"lists\":[{\"provinceid\":1,\"provincename\":\"广东省\"},{\"provinceid\":2,\"provincename\":\"江苏省\"},{\"provinceid\":3,\"provincename\":\"浙江省\"},{\"provinceid\":4,\"provincename\":\"上海市\"},{\"provinceid\":5,\"provincename\":\"山东省\"},{\"provinceid\":6,\"provincename\":\"北京市\"},{\"provinceid\":7,\"provincename\":\"河南省\"},{\"provinceid\":8,\"provincename\":\"湖北省\"},{\"provinceid\":9,\"provincename\":\"福建省\"},{\"provinceid\":10,\"provincename\":\"湖南省\"},{\"provinceid\":11,\"provincename\":\"四川省\"},{\"provinceid\":12,\"provincename\":\"河北省\"},{\"provinceid\":13,\"provincename\":\"广西壮族自治区\"},{\"provinceid\":14,\"provincename\":\"安徽省\"},{\"provinceid\":15,\"provincename\":\"辽宁省\"},{\"provinceid\":16,\"provincename\":\"山西省\"},{\"provinceid\":17,\"provincename\":\"江西省\"},{\"provinceid\":18,\"provincename\":\"陕西省\"},{\"provinceid\":19,\"provincename\":\"重庆市\"},{\"provinceid\":20,\"provincename\":\"天津市\"},{\"provinceid\":21,\"provincename\":\"黑龙江省\"},{\"provinceid\":22,\"provincename\":\"云南省\"},{\"provinceid\":23,\"provincename\":\"新疆维吾尔自治区\"},{\"provinceid\":24,\"provincename\":\"贵州省\"},{\"provinceid\":25,\"provincename\":\"吉林省\"},{\"provinceid\":26,\"provincename\":\"内蒙古自治区\"},{\"provinceid\":27,\"provincename\":\"甘肃省\"},{\"provinceid\":28,\"provincename\":\"海南省\"},{\"provinceid\":29,\"provincename\":\"宁夏回族自治区\"},{\"provinceid\":30,\"provincename\":\"青海省\"},{\"provinceid\":31,\"provincename\":\"西藏自治区\"},{\"provinceid\":32,\"provincename\":\"澳门\"},{\"provinceid\":33,\"provincename\":\"香港\"},{\"provinceid\":34,\"provincename\":\"台湾\"},{\"provinceid\":35,\"provincename\":\"印度尼西亚\"},{\"provinceid\":36,\"provincename\":\"泰国\"},{\"provinceid\":37,\"provincename\":\"马来西亚\"},{\"provinceid\":38,\"provincename\":\"新加坡\"},{\"provinceid\":39,\"provincename\":\"美国\"},{\"provinceid\":40,\"provincename\":\"加拿大\"},{\"provinceid\":41,\"provincename\":\"澳大利亚\"},{\"provinceid\":42,\"provincename\":\"新西兰\"},{\"provinceid\":43,\"provincename\":\"日本\"},{\"provinceid\":44,\"provincename\":\"韩国\"},{\"provinceid\":45,\"provincename\":\"英国\"},{\"provinceid\":46,\"provincename\":\"法国\"},{\"provinceid\":47,\"provincename\":\"德国\"},{\"provinceid\":48,\"provincename\":\"意大利\"},{\"provinceid\":49,\"provincename\":\"瑞典\"},{\"provinceid\":50,\"provincename\":\"俄罗斯\"},{\"provinceid\":51,\"provincename\":\"印度\"}]}}";
		System.out.println("---------v-vvvvvv-");
		JSONObject obj = JSONObject.parseObject(jsonStr);
		System.out.println("------------------------："+obj.toJSONString());
		return obj.toJSONString();
	}
	
	@PostMapping(path="/api/login")
//	@GetMapping(path="/api/province/query_all_province",produces="text/plain;charset=UTF-8")
	public String index3(HttpServletRequest request,HttpServletResponse response) {
//		不起作用
//		response.setContentType("text/html;charset=UTF-8");
//		response.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		String pass = request.getParameter("pass");
		JSONObject obj = new JSONObject();
		
		
		if("boss".equals(name) && "123".equals(pass)) {
			obj.put("status", "0");
			obj.put("message", "成功");
		}else {
			obj.put("status", "1");
			obj.put("message", "失败");
		}
		JSONObject obj1 = new JSONObject();
		obj1.put("name", name);
		obj1.put("pass", pass);
		obj.put("data", obj1);
		System.out.println("------------------------："+obj.toJSONString());
		return obj.toJSONString();
	}

}
