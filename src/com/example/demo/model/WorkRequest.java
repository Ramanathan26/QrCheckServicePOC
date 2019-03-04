package com.example.demo.model;

public class WorkRequest {

	private int workRequestId;
	private String user;
	private String workRequestType;
	private boolean isQrProcessed;

	public WorkRequest(int workRequestId, String user, String workRequestType) {
		super();
		this.workRequestId = workRequestId;
		this.user = user;
		this.workRequestType = workRequestType;
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

	public boolean isQrProcessed() {
		return isQrProcessed;
	}

	public int getWorkRequestId() {
		return workRequestId;
	}

	public void setWorkRequestId(int workRequestId) {
		this.workRequestId = workRequestId;
	}

	public void setQrProcessed(boolean isQrProcessed) {
		this.isQrProcessed = isQrProcessed;
	}

}
