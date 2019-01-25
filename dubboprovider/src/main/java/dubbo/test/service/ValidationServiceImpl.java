package dubbo.test.service;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.rpc.RpcContext;

import dubbo.test.bean.ValidationParameter;
import dubbo.test.interfaces.ValidationService;

@Service("validationService")
public class ValidationServiceImpl implements ValidationService{

	@Override
	public void save(ValidationParameter parameter) {
		System.out.println("ValidationServiceImpl"+".save");
		System.out.println("==="+parameter);
        // 本端是否为提供端，这里会返回true
        boolean isProviderSide = RpcContext.getContext().isProviderSide();
       
        // 获取调用方IP地址
        String clientIP = RpcContext.getContext().getRemoteHost();
        // 获取当前服务配置信息，所有配置信息都将转换为URL的参数
        String application = RpcContext.getContext().getUrl().getParameter("application");
        // 注意：每发起RPC调用，上下文状态会变化
        //yyyService.yyy();
        // 此时本端变成消费端，这里会返回false
        boolean isProviderSide2 = RpcContext.getContext().isProviderSide();
        System.out.println("-----isProviderSide:"+isProviderSide);
        System.out.println("-----clientIP:"+clientIP);
        System.out.println("-----application:"+application);
        System.out.println("-----isProviderSide2:"+isProviderSide2);
        
		String index = RpcContext.getContext().getAttachment("index");
		System.out.println("ValidationServiceImpl save index:::"+index);
		System.out.println("--------------------------------------------");
	}

	@Override
	public void update(ValidationParameter parameter) {
		System.out.println("ValidationServiceImpl "+".update");
		System.out.println("==="+parameter);
		
		String index = RpcContext.getContext().getAttachment("index");
		System.out.println("ValidationServiceImpl update index:::"+index);
		System.out.println("--------------------------------------------");
	}

}
