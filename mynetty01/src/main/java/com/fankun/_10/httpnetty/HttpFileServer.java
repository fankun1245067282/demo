package com.fankun._10.httpnetty;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

public class HttpFileServer {
	
	public static final String DEFAULT_URL = "/src/com/fankun/netty";
	public void bind(int port,String url) throws InterruptedException {
		//配置服务端的NIO线程组
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup,workerGroup)
			.channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG, 1024)
			.childHandler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast("http-decoder",new HttpRequestDecoder());
					//把多个消息转换成单一的FullHttpRequest|FullHttpResponse，原因是HTTP解码器
					//会在每个HTTP消息中生成多个消息对象：HttpRequest,HttpResponse;HttpContent;LastHttpContent
					ch.pipeline().addLast("http-aggregator",new HttpObjectAggregator(65536));
					
					ch.pipeline().addLast("http-encoder",new HttpResponseEncoder());
					//主要作用是异步发送大的码流，不会占用大的内存，防止内存溢出
					ch.pipeline().addLast("http-chunked",new ChunkedWriteHandler());
					//测试http请求
//					ch.pipeline().addLast("fileServerHandler",new HttpFileServerHandler());
					// 用于下载文件
					ch.pipeline().addLast(new HttpDownloadHandler());
				}});
			String ip = "192.168.4.63";
			//绑定端口，同步等待成功
			ChannelFuture f= b.bind(ip,port).sync();
			System.out.println("http文件目录服务启动，网址是[http://"+ip+":"+port+""+url+"]");
			System.out.println("--------------1---------------");
			
			//等待服务端监听端口关闭
			f.channel().closeFuture().sync();
			System.out.println("--------------2---------------");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			System.out.println("--------------3---------------");
			//优雅退出，释放线程池资源
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
			System.out.println("--------------4---------------");
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		String url = DEFAULT_URL;
		new HttpFileServer().bind(8088,url);
	}
}
