package com.fk.test.paxos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
/**
 * 决策者
 * @author Administrator
 *
 */
public class Acceptor extends Thread {
	//Acceptor批准的value值，会批准最大编号请求的value
	public Integer AcceptV = null;//选定提议值
	public Integer AcceptN = null;//选定提议编号
	public Integer MaxN = 0; //所有request请求提议的最大编号，要比这个大才能返回OK，所有的编号都比0大，第一次请求就会保存提议编号
	public List<Request> allAcceptRequests = new ArrayList<>();;
	//决策者获取的请求，同步处理
	public LinkedBlockingQueue<Request> requests = new LinkedBlockingQueue<>();
	public Map<String, Response>  propseResponse = new ConcurrentHashMap<>();
	
	public Acceptor(String name) {
		super(name);
	}
	
	public void run() {
		while(!Proposer.IS_CHOOSEN) {
			try {
				Request request = requests.take();//获取请求队列中的请求，没有会阻塞
				Response response = new Response();
				//proposer请求
				if(Request.REQUEST_TYPE_PROPOSE.equals(request.getType())) {
					 //大于的保存提议编号，请求proposalNum>MaxN,MaxN默认值为0，任何请求都大于0
					if (request.getN().intValue() > MaxN) {
						MaxN = request.getN().intValue();
						response.setAcceptV(AcceptV);
						response.setAcceptN(AcceptN);
						response.setStatus(Response.POK);
					} else {// 小于的返回NULL
						response.setStatus(Response.PNO);
					}
					response.setAcceptorName(this.getName());
					response.setProposerName(request.getpName());
					propseResponse.put(request.getType()+request.getN(), response);
					System.out.println(this.getName() + "处理了：::"+request+"  结果："+request.getN()+"==="+response);
					
					
				//acceptor请求,只有在这个阶段才会选定 提议，prepare阶段不会
				}else {
					//大于等于的批准，请求proposalNum>=maxRequestProposalNum
					if(request.getN()>=MaxN.intValue()){
						System.out.println("批准时，看看value是否相同：requestValue:"+
						request.getV()+" ~  AcceptV:"+AcceptV+""+request);
						AcceptV = request.getV().intValue();
						AcceptN =  request.getN().intValue();
						response.setStatus(Response.AOK);
					}else {//小于的返回NULL
						response.setStatus(Response.ANO);
					}
					response.setAcceptV(AcceptV);
					response.setAcceptN(AcceptN);
					response.setAcceptorName(this.getName());
					response.setProposerName(request.getpName());
					propseResponse.put(request.getType()+request.getN(), response);
					System.out.println(this.getName() + "处理了：::"+request+"  结果："+request.getN()+"==="+response);
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
		System.err.println("决策者"+this.getName()+" 的AcceptN："+AcceptN+"  AcceptV："+AcceptV);
	}
	
}
