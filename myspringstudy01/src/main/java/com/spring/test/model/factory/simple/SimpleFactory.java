package com.spring.test.model.factory.simple;

import com.spring.test.model.factory.Auodi;
import com.spring.test.model.factory.Benz;
import com.spring.test.model.factory.Bmw;
import com.spring.test.model.factory.Car;

/**
 * 工厂太强了，为什么？什么车都能生产
 * 编码是一种艺术（融会贯通），艺术源自于生活，回归到生活
 *
 */
public class SimpleFactory {
	
	/**
	 * 实现统一、专业化管理（卫生监督局工作难度大大减轻）
	 * 如果没有工厂模式：自产自消，小作坊，没有执行标准
	 * 如果买到三无产品（没有标准）
	 * 
	 * 中国制造（按照人家的标准）
	 * 中国制造向中国创造改变（技术不是问题，问题是什么？思维能力）
	 * 码农就是执行标准的人
	 * 架构师是制定标准的人
	 * 
	 * 不只做一个技术者，更要做一个思考者
	 */
	public Car getCar(String name) {
		if("BMW".equalsIgnoreCase(name)) {
			return new Bmw();
		}else if("Benz".equalsIgnoreCase(name)) {
			return new Benz();
		}else if("Auodi".equalsIgnoreCase(name)) {
			return new Auodi();
		}else {
			System.out.println("这个产品产不出来");
		}
		return null;
	}
	

}
