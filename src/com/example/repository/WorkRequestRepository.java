package com.example.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.demo.model.WorkRequest;

public class WorkRequestRepository {

	private static HashMap<String, HashMap<String, List<WorkRequest>>> userWorkRequestMap = new HashMap<>();
	private static HashMap<String, HashMap<String, Integer>> userQrMap = new HashMap<>();

	public static void saveWorkRequest(WorkRequest workRequest) {

		String user = workRequest.getUser();
		String workRequestType = workRequest.getWorkRequestType();
		if (userWorkRequestMap != null || !userWorkRequestMap.isEmpty()) {
			if (userWorkRequestMap.containsKey(user) && userWorkRequestMap.get(user).containsKey(workRequestType)) {

				userWorkRequestMap.get(user).get(workRequestType).add(workRequest);
			} else {
				List<WorkRequest> wrList = new ArrayList<>();
				wrList.add(workRequest);
				HashMap<String, List<WorkRequest>> wrMap = new HashMap<>();
				wrMap.put(workRequestType, wrList);

				userWorkRequestMap.put(user, wrMap);
			}
		} else {
			List<WorkRequest> wrList = new ArrayList<>();
			wrList.add(workRequest);
			HashMap<String, List<WorkRequest>> wrMap = new HashMap<>();
			wrMap.put(workRequestType, wrList);

			userWorkRequestMap.put(user, wrMap);
		}
	}

	public static int getTotalProcessedCount(WorkRequest workRequest) {
		String user = workRequest.getUser();
		String workRequestType = workRequest.getWorkRequestType();

		int size = userWorkRequestMap.get(user).get(workRequestType).size();
		return size;
	}

	public static int getQrPercent(WorkRequest workRequest) {

		String user = workRequest.getUser();
		String workRequestType = workRequest.getWorkRequestType();
		int qrPercent = userQrMap.get(user).get(workRequestType);
		return qrPercent;
	}

	public static int getTotalQrProcessedRollingBucket(WorkRequest workRequest, int bucketSize, int totalProcessedCount) {

		String user = workRequest.getUser();
		String workRequestType = workRequest.getWorkRequestType();
		int startIndex = totalProcessedCount - bucketSize;
		int endIndex = totalProcessedCount - 1;

		List<WorkRequest> workRequestList = userWorkRequestMap.get(user).get(workRequestType);
		int qrCount = 0;

		for (int i = startIndex; i < endIndex; i++) {

			WorkRequest wr = workRequestList.get(i);
			if (wr != null && wr.isQrProcessed() == true) {
				qrCount++;
			}
		}
		return qrCount;
	}

	public static int getTotalQrProcessedInCurrentBucket(WorkRequest workRequest, int currentPosition,
			int totalProcessedCount) {
		String user = workRequest.getUser();
		String workRequestType = workRequest.getWorkRequestType();

		int qrCount = 0;
		int startIndex = totalProcessedCount - currentPosition;
		int endIndex = totalProcessedCount - 1;

		List<WorkRequest> workRequestList = userWorkRequestMap.get(user).get(workRequestType);

		for (int i = startIndex; i < endIndex; i++) {

			WorkRequest wr = workRequestList.get(i);
			if (wr != null && wr.isQrProcessed() == true) {
				qrCount++;
			}
		}

		return qrCount;
	}

	public static void updateWorkRequest(WorkRequest workRequest, boolean status) {
		String user = workRequest.getUser();
		String workRequestType = workRequest.getWorkRequestType();

		workRequest.setQrProcessed(status);
		userWorkRequestMap.get(user).get(workRequestType).remove(workRequest.getWorkRequestId() - 1);
		userWorkRequestMap.get(user).get(workRequestType).add(workRequest.getWorkRequestId() - 1, workRequest);
		System.out.println(
				"\nWorkRequest_" + workRequest.getWorkRequestId() + " status - " + workRequest.isQrProcessed());

	}

	public static void setUp(String user, String workRequestType) {

		HashMap<String, Integer> wrQrMap = new HashMap<>();
		wrQrMap.put(workRequestType, 50);
		userQrMap.put(user, wrQrMap);
	}
}
