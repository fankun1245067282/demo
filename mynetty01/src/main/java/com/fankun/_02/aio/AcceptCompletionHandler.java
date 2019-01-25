package com.fankun._02.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, AsyncTimeServerHandler> {

	@Override
	public void completed(AsynchronousSocketChannel result, AsyncTimeServerHandler attachment) {
		//AsyncTimeServerHandler 中有AsynchronousServerSocketChannel
		//这里的作用是：AsynchronousServerSocketChannel继续监听端口
//		public void doAccept() {
//			asynchronousServerSocketChannel.accept(this,new AcceptCompletionHandler());
//		}
		attachment.getAsynchronousServerSocketChannel().accept(attachment,this);
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		//result 是AsynchronousSocketChannel 就是 socketChannel
		result.read(buffer, buffer, new ReadCompletionHandler(result));
	}

	@Override
	public void failed(Throwable exc, AsyncTimeServerHandler attachment) {
		exc.printStackTrace();
		attachment.getLatch().countDown();
		
	}


}
