package com.gtn.ejbjboss.ejb;

import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class Auditer {
	private int requestCounter;
	
	public void increment() {
		requestCounter++;
		System.out.println("total number of requests so far "+requestCounter);
	}
}
