package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

public class UserWorkRequestDetails {

	private String user;
	private String workRequestType;
	private int totalQrProcessedCounter;
	private int numberOfWRsentToQRforCurrentBucket;
	private int wrToBeSentToQR;
	private List<Integer> WRSentTracker = new ArrayList<Integer>();
	private int workRequestProcessedCount;

	private int qrProcessedCounterForCurrentBucket;
	private int totalProcessedInCurrentBucket;
	private int wrToBeSentToQRforCurrentBucket;
	private int totalWRsent;

	public List<Integer> getWRSentTracker() {
		return WRSentTracker;
	}

	public void setWRSentTracker(List<Integer> wRSentTracker) {
		WRSentTracker = wRSentTracker;
	}

	public int getTotalWRsent() {
		return totalWRsent;
	}

	public void setTotalWRsent(int totalWRsent) {
		this.totalWRsent = totalWRsent;
	}

	public int getNumberOfWRSentToQRForCurrentBucket() {
		return numberOfWRsentToQRforCurrentBucket;
	}

	public void setNumberOfWRSentToQRForCurrentBucket(int numberOfWRSentToQRForCurrentBucket) {
		numberOfWRsentToQRforCurrentBucket = numberOfWRSentToQRForCurrentBucket;
	}

	public int getWRs_ToBe_Sent_To_QRforcurrentbucket() {
		return wrToBeSentToQRforCurrentBucket;
	}

	public void setWRs_ToBe_Sent_To_QRforcurrentbucket(int wRs_ToBe_Sent_To_QRforcurrentbucket) {
		wrToBeSentToQRforCurrentBucket = wRs_ToBe_Sent_To_QRforcurrentbucket;
	}

	public String getUser() {
		return user;
	}

	public int getWRs_ToBe_Sent_To_QR() {
		return wrToBeSentToQR;
	}

	public void setWRs_ToBe_Sent_To_QR(int wRs_ToBe_Sent_To_QR) {
		wrToBeSentToQR = wRs_ToBe_Sent_To_QR;
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

	public void setTotalQrProcessedCounter(int totalQrProcessedCounter) {
		this.totalQrProcessedCounter = totalQrProcessedCounter;
	}

	public int getQrProcessedCounterForCurrentBucket() {
		return qrProcessedCounterForCurrentBucket;
	}

	public void setQrProcessedCounterForCurrentBucket(int qrProcessedCounterForCurrentBucket) {
		this.qrProcessedCounterForCurrentBucket = qrProcessedCounterForCurrentBucket;
	}

	public int getTotalProcessedInCurrentBucket() {
		return totalProcessedInCurrentBucket;
	}

	public void setTotalProcessedInCurrentBucket(int totalProcessedInCurrentBucket) {
		this.totalProcessedInCurrentBucket = totalProcessedInCurrentBucket;
	}

	public int getWorkRequestProcessedCount() {
		return workRequestProcessedCount;
	}

	public void setWorkRequestProcessedCount(int workRequestProcessedCount) {
		this.workRequestProcessedCount = workRequestProcessedCount;
	}

	public UserWorkRequestDetails(String user, String workRequestType, int totalQrProcessedCounter,
			int qrProcessedCounterForCurrentBucket, int totalProcessedInCurrentBucket, int workRequestProcessedCount) {
		super();
		this.user = user;
		this.workRequestType = workRequestType;
		this.totalQrProcessedCounter = totalQrProcessedCounter;
		this.qrProcessedCounterForCurrentBucket = qrProcessedCounterForCurrentBucket;
		this.totalProcessedInCurrentBucket = totalProcessedInCurrentBucket;
		this.workRequestProcessedCount = workRequestProcessedCount;
	}

	public UserWorkRequestDetails(String user, String workRequestType) {
		super();
		this.user = user;
		this.workRequestType = workRequestType;
	}

}
