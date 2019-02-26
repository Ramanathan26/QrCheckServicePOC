package com.example.demo.model;

public class WorkRequest {

	private int workRequestId;
	private String User;
	private String workRequestType;
	private boolean isQrProcessed;

	public WorkRequest(int workRequestId, String user, String workRequestType) {
		super();
		this.workRequestId = workRequestId;
		User = user;
		this.workRequestType = workRequestType;
	}

	public String getUser() {
		return User;
	}

	public void setUser(String user) {
		User = user;
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
