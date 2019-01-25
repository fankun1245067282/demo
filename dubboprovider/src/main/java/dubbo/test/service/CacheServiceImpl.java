package dubbo.test.service;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import dubbo.test.interfaces.CacheService;

/**
 * ValidationServiceImpl
 */
@Service("cacheService")
public class CacheServiceImpl implements CacheService {

    private final AtomicInteger i = new AtomicInteger();

    @Override
    public String findCache(String id) {
    	String str = "request: " + id + ", response: " + i.getAndIncrement();
    	System.out.println("findCache:::"+str);
    	return str;
    }

}