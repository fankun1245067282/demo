package com.fk.test.paxos;

import java.util.Arrays;
import java.util.Random;

/**
 * 提议者
 * 
 * @author Administrator Proposer发送获取相应的最大的M0的value值
 */
public class Proposer extends Thread {
	public static volatile boolean IS_CHOOSEN = false;//是否产生出选举结果 false:没有 true已经产生
	private Acceptor[] acceptors = null;// 决策者
	public int proposerNum = 0;//提议者的编号
	private Request preRequest = null;//上一个请求
	private int times = 0;// 第几轮发送请求了
	private Response[] responseArr = null;
	
	public Proposer(String name, Acceptor[] acceptors, Integer proposerNum) {
		super(name);
		this.proposerNum = proposerNum;
		this.acceptors = acceptors;
	}

	public void run() {
		Random random = new Random();
		
		while (!IS_CHOOSEN) {
			times++;// 发起轮次请求次数

			Request request = new Request();
			//1、说明之前没有发送任何请求，发送提议请求
			if (preRequest == null) {
				request.setType(Request.REQUEST_TYPE_PROPOSE);
				
			//2、当请求类型ACCEPT，说明之前发送的是ACCEPT，判断是超过半数批准，超过就结束，没有超过半数继续发送发送提议请求
			} else if(preRequest.getType().equals(Request.REQUEST_TYPE_ACCEPT)) {
				int responseCount = 0;//响应的数量
				//根据请求类型，请求提议编号，获取返回结果
				for (int i = 0; i < acceptors.length; i++) {
					Response resp = acceptors[i].propseResponse.get(preRequest.getType()+preRequest.getN());
					//统计自己的提议被决策者接受的数量
					if (resp.getStatus() == Response.AOK) {
						responseCount++;
					}
				}
				
				//提议者的提议被大于一半的决策者接受，则结束，否则继续发送prepare请求
				if(responseCount>acceptors.length/2) {//responseCount>acceptors.length/2
					System.err.println("提议者" + this.getName()+" accept请求，超过一半的响应，执行执行结束了."+"proValue:"+preRequest.getV()+"  respCount:"+responseCount+"\n"+Arrays.toString(responseArr));
					IS_CHOOSEN = true;
					continue;
					//发送prepare请求
				}
				
				request.setType(Request.REQUEST_TYPE_PROPOSE);
				
				
			//3、当请求类型PROPOSE，说明之前发送的是PROPOSE，判断是超过半数返回，就发送ACCEPT请求，否则，继续发送发送提议请求	
			}else {
				int maxResponseProposalNum = 0;
				Integer responseValue = null;
				int responseCount = 0;//响应的数量
				for (int i = 0; i < acceptors.length; i++) {
					Response resp = acceptors[i].propseResponse.get(preRequest.getType()+preRequest.getN());
					
					//说明有响应，没有响应的不统计
					if(resp.getStatus() == Response.POK){
						responseCount++;
						if (resp.getAcceptN()!=null && resp.getAcceptN()>maxResponseProposalNum) {
							maxResponseProposalNum = resp.getAcceptN();//获取最大响应提议编号
							responseValue = resp.getAcceptV();
						}
					}
				}
				
				//prepare请求，进入accept请求的前提时，大于一半的请求响应，否则，继续prepose请求
				if(responseCount>acceptors.length/2) {
					request.setType(Request.REQUEST_TYPE_ACCEPT);
					if (responseValue == null) {//为空，设置自己的值
						request.setV(proposerNum);
					}else {
						request.setV(responseValue);
					}
					request.setN(preRequest.getN());
				//响应没有超过一半，继续执行，法发送propose请求
				}else {
					request.setType(Request.REQUEST_TYPE_PROPOSE);
				}
				//accept请求的返回结果，如果自己的提议被 多数决策者接受，就结束了
			}
			
			//设置提议号码
			if(request.getType()==Request.REQUEST_TYPE_PROPOSE) {
				request.setN(TestPaxos.getNextNum());
			}
			request.setpName(this.getName());
			request.setpNum(proposerNum);
			
			
			//************************************************************************
			//发送请求。。。。。。
			//************************************************************************
			System.out.println("*****提议者" + this.getName() + " 开始发送第" + times + "轮发送请求：" + request);
			for (int i = 0; i < acceptors.length; i++) {
				try {
					try {
						Thread.sleep(random.nextInt(10) + 10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					acceptors[i].requests.put(request);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			
			preRequest = request;
			
			//************************************************************************
			//等待回复结果
			//************************************************************************
			
			responseArr = new Response[acceptors.length];
			int n = 0;
			while (n != acceptors.length) {//需要所有的决策者都回复
				
				
				n = 0;
				String resultKey = preRequest.getType()+preRequest.getN();
				for (int i = 0; i < acceptors.length; i++) {
					responseArr[i] = acceptors[i].propseResponse.get(resultKey);
					if(responseArr[i]!=null) {
						n++;
					}
//					System.out.println(""+this.getName()+"-----------resultKey："+resultKey+"   i:"+i+"  N:"+n);
//					System.out.println(""+this.getName()+"-----------propseResponse："+acceptors[i].propseResponse);
				}
			}
			System.out.println("*****提议者" + this.getName() + " 接收到第" + times + " 轮发送请求响应：" + Arrays.toString(responseArr));
			//************************************************************************
		}

	}

}