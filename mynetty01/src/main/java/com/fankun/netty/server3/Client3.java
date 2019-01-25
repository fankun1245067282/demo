package com.fankun.netty.server3;

import java.util.concurrent.TimeUnit;

public class Client3 {
	public static void main(String[] args) throws Exception {
			NettyClientBootstrap bootstrap = new NettyClientBootstrap(9999, "127.0.0.1");
			int i = 1;
	 
//			while (true) {
				TimeUnit.SECONDS.sleep(10);
				RequestInfoVO req = new RequestInfoVO();
				req.setVersion((byte)1);
				req.setType((byte) 1);
				req.setSequence(i);
				req.setName("tom");
				req.setNameLength(req.getName().length());
				req.setBody("最近做项目的时候，一直发现常用的oschina maven源一直都没有反应，后面发现原来oschina竟然关闭了maven源服务");
				req.setBodyLength(req.getBodyLength());
				bootstrap.getSocketChannel().writeAndFlush(req);
				i++;
//				System.out.println("========i:"+i);
				System.out.println("========i am "+req.getName()+", i send a message '"+req.getBody()+"'");
//			}
		}
	
}

