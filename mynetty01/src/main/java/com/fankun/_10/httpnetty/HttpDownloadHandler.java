package com.fankun._10.httpnetty;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.apache.log4j.Logger;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelProgressiveFuture;
import io.netty.channel.ChannelProgressiveFutureListener;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.stream.ChunkedFile;
/**
 * 测试文件下载
 * @author Administrator
 *
 */
public class HttpDownloadHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
//	public static Log log = LogFactory.getLog(HttpDownloadHandler.class);
	private static Logger logger = Logger.getLogger(HttpDownloadHandler.class);  
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
		// 下载文件测试：：：：

		File file = new File("D:\\home\\ccmt\\poc-ywf\\logs\\abc.xls");
		final RandomAccessFile randomAccessFile;
		try {
			randomAccessFile = new RandomAccessFile(file, "r");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		long fileLength = 0;
		try {
			fileLength = randomAccessFile.length();
		} catch (IOException e) {
			e.printStackTrace();
		}
		HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
		response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
		response.headers().set(HttpHeaderNames.CONTENT_LENGTH, fileLength);
		//指定类型为文件流
		response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/octet-stream");
		//指定下载名称
		response.headers().add(HttpHeaderNames.CONTENT_DISPOSITION,
				String.format("attachment; filename=\"%s\"", file.getName()));
		ctx.write(response);
		ChannelFuture sendFileFuture;
		sendFileFuture = ctx.write(new ChunkedFile(randomAccessFile, 0, fileLength, 8192), ctx.newProgressivePromise());

		sendFileFuture.addListener(new ChannelProgressiveFutureListener() {

			@Override
			public void operationComplete(ChannelProgressiveFuture future) throws Exception {
				System.out.println("file " + file.getName() + " transfer complete.");
				randomAccessFile.close();
			}

			@Override
			public void operationProgressed(ChannelProgressiveFuture future, long progress, long total)
					throws Exception {
				if (total < 0) {
					logger.warn("file"+file.getName()+"transfer progress: "+progress);
				} else {
					logger.warn("file"+file.getName()+"transfer progress: "+progress+"/"+total);
				}
			}
		});
		//标识所有的消息都发送完毕，同事flush一下
	    ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
	}

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
       super.channelInactive(ctx);
       System.out.println("链接已经拉了");
    }
    
}
