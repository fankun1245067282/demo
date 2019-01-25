package com.fankun.netty.server3;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.Channel;
import io.netty.channel.socket.SocketChannel;

public class NettyChannelMap {
    private static Map<String,SocketChannel> map=new ConcurrentHashMap<String, SocketChannel>();

    public static int size() { 
    	return map.size();
    }
    
	public static void add(String clientName, SocketChannel socketChannel) {
		map.put(clientName, socketChannel);
	}

	public static Channel get(String clientId) {
		return map.get(clientId);
	}

	public static void remove(SocketChannel socketChannel) {
		for (Map.Entry<String, SocketChannel> entry : map.entrySet()) {
			if (entry.getValue() == socketChannel) {
				map.remove(entry.getKey());
			}
		}
	}
	
	public static String getName(SocketChannel socketChannel) {
		for (Map.Entry<String, SocketChannel> entry : map.entrySet()) {
			if (entry.getValue() == socketChannel) {
				return entry.getKey();
			}
		}
		return null;
	}
 
}