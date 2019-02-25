package com.example.demo.service;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.example.demo.model.UserWorkRequestInfo;

public class FixedBucketWRService {

	UserWorkRequestInfo userWorkRequestInfo = null;
	List<UserWorkRequestInfo> userWorkRequestInfoList = new ArrayList<>();

	public void WorkRequestService(String workRequest) {
		String status = null;
		int qrPercent = 0;
		String workRequesttype = "wR1";
		String user = "user1";
		int workRequestProcessedCount = 0;

		int bucketSize;
		int totalProcessedInCurrentBucket;
		boolean qrOutput = false;
		int qrToBeProcessedPerBucket = 0;

		if (userWorkRequestInfoList.size() != 0) {
			for (UserWorkRequestInfo uWrInfo : userWorkRequestInfoList) {

				if (uWrInfo.getUser().equals(user) && uWrInfo.getWorkRequestType().equals(workRequesttype)) {
					uWrInfo.setWorkRequestProcessedCount(uWrInfo.getWorkRequestProcessedCount() + 1);
					workRequestProcessedCount = uWrInfo.getWorkRequestProcessedCount();
					userWorkRequestInfo = uWrInfo;
					break;
				}

			}
			if (userWorkRequestInfo == null) {
				UserWorkRequestInfo uWr = new UserWorkRequestInfo(user, workRequesttype);
				uWr.setWorkRequestProcessedCount(1);
				userWorkRequestInfoList.add(uWr);
				workRequestProcessedCount = uWr.getWorkRequestProcessedCount();
				userWorkRequestInfo = uWr;
			}
		} else {
			UserWorkRequestInfo uWr = new UserWorkRequestInfo(user, workRequesttype);
			uWr.setWorkRequestProcessedCount(1);
			userWorkRequestInfoList.add(uWr);
			workRequestProcessedCount = uWr.getWorkRequestProcessedCount();
			userWorkRequestInfo = uWr;
		}

		try {
			FileReader fr = new FileReader("application.properties");

			Properties p = new Properties();
			p.load(fr);

			bucketSize = Integer.parseInt(p.getProperty("bucketSize" + workRequesttype + user));
			qrPercent = Integer.parseInt(p.getProperty("QRPercent" + workRequesttype + user));

			System.out.println("\nBucket Size " + bucketSize);

			if (workRequestProcessedCount % bucketSize > 0) {
				totalProcessedInCurrentBucket = Math.min(bucketSize, (workRequestProcessedCount % bucketSize));
			} else {
				totalProcessedInCurrentBucket = bucketSize;
			}

			qrToBeProcessedPerBucket = (qrPercent * bucketSize) / 100;

			if ((qrToBeProcessedPerBucket - userWorkRequestInfo.getQrProcessedCounterForCurrentBucket()) != (bucketSize
					- (totalProcessedInCurrentBucket - 1))) {
				if (qrToBeProcessedPerBucket > userWorkRequestInfo.getQrProcessedCounterForCurrentBucket()) {

					qrOutput = isQRNeed(qrPercent, totalProcessedInCurrentBucket, qrToBeProcessedPerBucket);

				}

				if (qrOutput) {
					userWorkRequestInfo
							.setTotalQrProcessedCounter(userWorkRequestInfo.getTotalQrProcessedCounter() + 1);
					userWorkRequestInfo.setQrProcessedCounterForCurrentBucket(
							userWorkRequestInfo.getQrProcessedCounterForCurrentBucket() + 1);
					status = "QR Sent";
					updateWorkRequest(workRequest, status);
				}

				else {

					status = "Not Sent To QR";
					updateWorkRequest(workRequest, status);
				}
			} else {

				userWorkRequestInfo.setQrProcessedCounterForCurrentBucket(
						userWorkRequestInfo.getQrProcessedCounterForCurrentBucket() + 1);
				status = "Auto Sent to QR";
				updateWorkRequest(workRequest, status);
			}

			if (workRequestProcessedCount % bucketSize == 0) {
				userWorkRequestInfo.setQrProcessedCounterForCurrentBucket(0);
				totalProcessedInCurrentBucket = 0;
				userWorkRequestInfo.setTotalProcessedInCurrentBucket(0);

				System.out.println("\\*============================================*\\");
			}

		}

		catch (Exception e) {
			System.out.println("Exception In Properties File " + e);
		}
	}

	public boolean isQRNeed(int qrPercent, int totalProcessedInCurrentBucket, int qrToBeProcessedPerBucket) {
		boolean result = false;
		int probabilityResult = 0;
		int qrToBeProcessedFromCurrentBucketPosition = (qrPercent * totalProcessedInCurrentBucket) / 100;

		if (qrToBeProcessedFromCurrentBucketPosition == 0 && totalProcessedInCurrentBucket > 0) {
			probabilityResult = ProbabilityGenerator(qrPercent);
		}

		else if (qrToBeProcessedFromCurrentBucketPosition > 0
				&& qrToBeProcessedFromCurrentBucketPosition <= qrToBeProcessedPerBucket)

		{
			if (userWorkRequestInfo.getQrProcessedCounterForCurrentBucket() < qrToBeProcessedPerBucket) {
				probabilityResult = ProbabilityGenerator(qrPercent);
			}
		}
		if (probabilityResult == 1) {
			result = true;
		}
		return result;
	}

	public int ProbabilityGenerator(int qrPercent) {
		int probResult = 0;
		int randomNumber = (int) (Math.random() * 100);
		if (randomNumber <= qrPercent) {
			probResult = 1;
		} else {
			probResult = 0;
		}
		return probResult;
	}

	public static void updateWorkRequest(String workRequest, String status) {

		System.out.println("WorkRequest - " + workRequest + " Status - " + status);

	}
}
