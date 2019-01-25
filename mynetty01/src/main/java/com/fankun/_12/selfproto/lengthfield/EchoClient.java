package com.fankun._12.selfproto.lengthfield;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
/**
 * 测试LengthFieldBasedFrameDecoder的几个属性
 * @param maxFrameLength  帧的最大长度
 * @param lengthFieldOffset length字段偏移的地址
 * @param lengthFieldLength length字段所占的字节长
 * @param lengthAdjustment 修改帧数据长度字段中定义的值，可以为负数 因为有时候我们习惯把头部记入长度,若为负数,则说明要推后多少个字段
 * @param initialBytesToStrip 解析时候跳过多少个长度
 * @param failFast 为true，当frame长度超过maxFrameLength时立即报TooLongFrameException异常，为false，读取完整个帧再报异
 */
public class EchoClient {
	
	public void connect(String host,int port) throws InterruptedException {
		//配置客户端NIO线程组
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group)
			.channel(NioSocketChannel.class)
			.option(ChannelOption.TCP_NODELAY, true)
			.handler(new ChannelInitializer<SocketChannel>(){

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					NettyMessagePackDecoder nettyMessagePackDecoder = 
							//没看到有啥影响。。。。
							new NettyMessagePackDecoder(1024*1024,4,4,4,4);
					ch.pipeline().addLast("nettyMessageDecoder",nettyMessagePackDecoder);
					ch.pipeline().addLast("nettyMessageEncoder",new NettyMessagePackEncoder());
					
					ch.pipeline().addLast(new EchoClientHandler());
				}});
			
			//发起异步链接请求
			ChannelFuture f = b.connect(host, port).sync();
			
			//等待客户端链路关闭（这个一直挂起，知道channel关闭，手动关闭ctx.close()）
			f.channel().closeFuture().sync();
		} finally {
			//优雅退出，释放NIO线程组
			group.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		new EchoClient().connect("127.0.0.1", 8088);
	}
}
