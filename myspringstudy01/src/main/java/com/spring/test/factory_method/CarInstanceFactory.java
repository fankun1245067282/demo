package com.spring.test.factory_method;
import java.util.HashMap;
import java.util.Map;
/**
 * Factory方法模式--实例工厂方法方式
 * @author Administrator
 *
 */
public class CarInstanceFactory {
    private Map<Integer, Car> map = new HashMap<Integer,Car>();

    public void setMap(Map<Integer, Car> map) {
        this.map = map;
    }

    public CarInstanceFactory(){
    }

    public Car getCar(int id){
        return map.get(id);
    }
}