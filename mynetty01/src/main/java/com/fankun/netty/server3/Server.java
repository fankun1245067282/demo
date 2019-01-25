package com.fankun.netty.server3;

public class Server {
	public static void main(String[] args) throws InterruptedException {
			try {
				new NettyServerBootstrap(9999);
			} catch (Exception e) {
				e.printStackTrace();
			}
			int i = 5;
			
			while(i<8) {
				Thread.sleep(5000);
				if(NettyChannelMap.get("fankun")==null) {
					continue;
				}
				i++;
				RequestInfoVO req = new RequestInfoVO();
				req.setVersion((byte)1);
				req.setType((byte) 1);
				req.setSequence(i);
				req.setName("fankun");
				req.setNameLength(req.getName().length());
				req.setBody("哈哈呵呵呵呵呵");
				req.setBodyLength(req.getBodyLength());
				NettyChannelMap.get("fankun").writeAndFlush(req);
			}
	}
	
}

