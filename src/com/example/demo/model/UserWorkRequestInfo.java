package com.example.demo.model;



public class UserWorkRequestInfo {
	
	private String user;
	private String workRequestType;
	private  int totalQrProcessedCounter;
    private  int qrProcessedCounterForCurrentBucket;
	private  int totalProcessedInCurrentBucket;
	private int workRequestProcessedCount;
	
	
	public UserWorkRequestInfo(String user, String workRequestType) {
	
		this.user = user;
		this.workRequestType = workRequestType;
		this.totalQrProcessedCounter=0;
	    this.qrProcessedCounterForCurrentBucket=0;
	    this. totalProcessedInCurrentBucket=0;
	    this.workRequestProcessedCount=0;
		
	}


	public int getWorkRequestProcessedCount() {
		return workRequestProcessedCount;
	}


	public void setWorkRequestProcessedCount(int workRequestProcessedCount) {
		this.workRequestProcessedCount = workRequestProcessedCount;
	}


	public String getUser() {
		return user;
	}


	public void setUser(String user) {
		this.user = user;
	}


	public String getWorkRequestType() {
		return workRequestType;
	}


	public void setWorkRequestType(String workRequestType) {
		this.workRequestType = workRequestType;
	}


	public int getTotalQrProcessedCounter() {
		return totalQrProcessedCounter;
	}


	public void setTotalQrProcessedCounter(int totalqrProcessedCounter) {
		this.totalQrProcessedCounter = totalqrProcessedCounter;
	}


	public int getQrProcessedCounterForCurrentBucket() {
		return qrProcessedCounterForCurrentBucket;
	}


	public void setQrProcessedCounterForCurrentBucket(
			int qrProcessedCounterForCurrentBucket) {
		this.qrProcessedCounterForCurrentBucket = qrProcessedCounterForCurrentBucket;
	}


	public int getTotalProcessedInCurrentBucket() {
		return totalProcessedInCurrentBucket;
	}


	public void setTotalProcessedInCurrentBucket(int totalProcessedInCurrentBucket) {
		this.totalProcessedInCurrentBucket = totalProcessedInCurrentBucket;
	}

	
	
}
