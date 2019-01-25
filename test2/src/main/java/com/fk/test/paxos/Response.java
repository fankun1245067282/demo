package com.fk.test.paxos;
/**
 * 提议者向决策者发起的请求参数
 * @author Administrator
 */
public class Response {
	public final static String POK = "pok";//提议返回ok
	public final static String PNO = "pno";//提议返回no
	public final static String AOK = "aok";//批准返回ok
	public final static String ANO = "ano";//批准返回no
	private String acceptorName;//决策者名称
	private String proposerName;//提议者名称
	private String status;//请求返回状态
	private Integer AcceptN;//批准的提议编号
	private Integer AcceptV;//每个决策者是批准value相同的Request，就是第一个Reqeust的value
	public Request request = null; 
	
	public String getAcceptorName() {
		return acceptorName;
	}


	public void setAcceptorName(String acceptorName) {
		this.acceptorName = acceptorName;
	}


	public String getProposerName() {
		return proposerName;
	}


	public void setProposerName(String proposerName) {
		this.proposerName = proposerName;
	}


	public Integer getAcceptN() {
		return AcceptN;
	}


	public void setAcceptN(Integer acceptN) {
		AcceptN = acceptN;
	}


	public Integer getAcceptV() {
		return AcceptV;
	}


	public void setAcceptV(Integer acceptV) {
		AcceptV = acceptV;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	@Override
	public String toString() {
		return "Response [a=" + acceptorName + ", p=" + proposerName + ", stu=" + status
				+ ", AN=" + AcceptN + ", AV=" + AcceptV + "]";
	}

}
