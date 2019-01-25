package com.fankun.netty.server3;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Client2 {
	public static void main(String[] args) throws Exception {
			NettyClientBootstrap bootstrap = new NettyClientBootstrap(9999, "127.0.0.1");
			int i = 1;
	 
			while (true) {
				TimeUnit.SECONDS.sleep(10);
				RequestInfoVO req = new RequestInfoVO();
				req.setVersion((byte)1);
				req.setType((byte) 2);
				req.setSequence(i);
				req.setName("jim");
				req.setNameLength(req.getName().length());
				req.setBody(String.valueOf((new Date()).getTime()));
				req.setBodyLength(req.getBodyLength());
				bootstrap.getSocketChannel().writeAndFlush(req);
				i++;
				System.out.println("========i:"+i);
			}
		}
	
}

