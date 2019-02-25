package com.example.demo.model;

public class UserWorkRequestMapping {

	private String user;
	private String workRequestType;
	private int bucketSize;
	private double qrPercent;
	private double totalQrProcessedCounter;
	private double workRequestProcessedCount;
	private int wrToBeSentToQR;

	public UserWorkRequestMapping(String user, String workRequestType) {
		super();
		this.user = user;
		this.workRequestType = workRequestType;
		this.bucketSize = 0;
		this.qrPercent = 0.0;
		this.totalQrProcessedCounter = 0;
		this.workRequestProcessedCount = 0;
		this.wrToBeSentToQR = 0;
	}

	public int getWrToBeSentToQR() {
		return wrToBeSentToQR;
	}

	public void setWrToBeSentToQR(int wrToBeSentToQR) {
		this.wrToBeSentToQR = wrToBeSentToQR;
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

	public int getBucketSize() {
		return bucketSize;
	}

	public void setBucketSize(int bucketSize) {
		this.bucketSize = bucketSize;
	}

	public double getQrPercent() {
		return qrPercent;
	}

	public void setQrPercent(double qrPercent) {
		this.qrPercent = qrPercent;
	}

	public double getTotalQrProcessedCounter() {
		return totalQrProcessedCounter;
	}

	public void setTotalQrProcessedCounter(double totalQrProcessedCounter) {
		this.totalQrProcessedCounter = totalQrProcessedCounter;
	}

	public double getWorkRequestProcessedCount() {
		return workRequestProcessedCount;
	}

	public void setWorkRequestProcessedCount(double workRequestProcessedCount) {
		this.workRequestProcessedCount = workRequestProcessedCount;
	}

}
