package com.spring.test.model.factory.meth;

import com.spring.test.model.factory.Car;

/**
 * 工厂接口，定义了所有工厂的执行标准
 *
 */
public interface Factory {
	
	/**
	 * 符合汽车上路标准
	 * 汽车尾气排放标准
	 * 电子设备安全系数
	 * 必须必备安全带，安全气囊
	 */
	public Car getCar();

}
