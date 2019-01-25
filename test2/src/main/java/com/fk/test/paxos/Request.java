package com.fk.test.paxos;
/**
 * 提议者向决策者发起的请求参数
 * @author Administrator
 */
public class Request {
	//两种请求类型
	public static final String REQUEST_TYPE_PROPOSE = "PROPOSE";
	public static final String REQUEST_TYPE_ACCEPT = "ACCEPT";
	private String type = null;//请求类型
	private String pName;//提议者名称
	private int pNum;//提议者编号
	private Integer N;//提议者的提议编号，每发起一轮请求，同步获取一次
	private Integer V;//提议者建议接受值（如果是选举，就是备选人的编号，如果可以自己选择自己，那应该是选举人自己的编号）
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getpName() {
		return pName;
	}
	public void setpName(String pName) {
		this.pName = pName;
	}
	public int getpNum() {
		return pNum;
	}
	public void setpNum(int pNum) {
		this.pNum = pNum;
	}
	public Integer getN() {
		return N;
	}
	public void setN(Integer n) {
		N = n;
	}
	public Integer getV() {
		return V;
	}
	public void setV(Integer v) {
		V = v;
	}
	@Override
	public String toString() {
		return "Request [type=" + type + ", pName=" + pName + ", pNum=" + pNum + ", N=" + N + ", V=" + V + "]";
	}
	

	



}
