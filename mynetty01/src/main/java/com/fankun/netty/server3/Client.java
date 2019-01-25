package com.fankun.netty.server3;

import java.util.concurrent.TimeUnit;

public class Client {
	public static void main(String[] args) throws Exception {
			NettyClientBootstrap bootstrap = new NettyClientBootstrap(9999, "127.0.0.1");
			int i = 1;
	 
//			while (true) {
				TimeUnit.SECONDS.sleep(2);
				RequestInfoVO req = new RequestInfoVO();
				req.setVersion((byte)1);
				req.setType((byte) 1);
				req.setSequence(i);
				req.setName("fankun");
				req.setNameLength(req.getName().length());
				req.setBody("插上红旗！中国看上一块宝地，面积比中国还大，美国痛失大机遇！");
				req.setBodyLength(req.getBodyLength());
				bootstrap.getSocketChannel().writeAndFlush(req);
				i++;
//				System.out.println("========i:"+i);
				System.out.println("========i am "+req.getName()+", i send a message '"+req.getBody()+"'");
//			}
		}
	
}

