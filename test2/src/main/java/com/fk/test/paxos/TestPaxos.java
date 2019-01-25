package com.fk.test.paxos;

import java.io.IOException;

public class TestPaxos {
	private static Integer num = 0;
	public static synchronized  Integer getNextNum() {
		num++;
		return num;
	}
	
	public static void main(String[] args) throws IOException {
		Acceptor[] acceptors = new Acceptor[7];
		for (int i = 1; i <= acceptors.length; i++) {
			acceptors[i-1] = new Acceptor("A"+i);
		}
		for (int i = 1; i <= acceptors.length; i++) {
			acceptors[i-1].start();
		}
		
		Proposer[] proposers = new Proposer[10];
		for (int i = 1; i <= proposers.length; i++) {
			proposers[i-1] = new Proposer("P"+i,acceptors,i);
		}
		for (int i = 1; i <= proposers.length; i++) {
			proposers[i-1].start();
		}
		while(!Proposer.IS_CHOOSEN) {
			try {
				Thread.sleep(500);
				System.out.println("------------");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		try {
			Thread.sleep(500);
			System.out.println("------------");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("--------00000000000000000----");
	}
}
