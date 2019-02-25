package com.example.demo.service;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.example.demo.model.UserWorkRequestDetails;

public class RollingBucketWRService {

	private UserWorkRequestDetails userWorkRequestDetails = null;
	private List<UserWorkRequestDetails> userWorkRequestDetailsList = new ArrayList<>();

	public void WorkRequestService(String workRequest) {
		String status = null;
		int qrPercent = 0;
		String workRequesttype = "wR1";
		String user = "user1";
		int bucketSize;
		boolean qrOutput = false;
		if (userWorkRequestDetailsList.size() != 0) {
			for (UserWorkRequestDetails uWrInfo : userWorkRequestDetailsList) {

				if (uWrInfo.getUser().equals(user) && uWrInfo.getWorkRequestType().equals(workRequesttype)) {
					uWrInfo.setWorkRequestProcessedCount(uWrInfo.getWorkRequestProcessedCount() + 1);
					uWrInfo.getWorkRequestProcessedCount();
					userWorkRequestDetails = uWrInfo;
					break;
				}

			}
			if (userWorkRequestDetails == null) {
				UserWorkRequestDetails uWr = new UserWorkRequestDetails(user, workRequesttype);
				uWr.setWorkRequestProcessedCount(1);
				userWorkRequestDetailsList.add(uWr);
				uWr.getWorkRequestProcessedCount();
				userWorkRequestDetails = uWr;
			}
		} else {
			UserWorkRequestDetails uWr = new UserWorkRequestDetails(user, workRequesttype);
			uWr.setWorkRequestProcessedCount(1);
			userWorkRequestDetailsList.add(uWr);
			uWr.getWorkRequestProcessedCount();
			userWorkRequestDetails = uWr;
		}

		List<Integer> sample = userWorkRequestDetails.getWRSentTracker();
		if (sample == null) {
			System.out.println(sample);
			sample = new ArrayList<Integer>();
			userWorkRequestDetails.setWRSentTracker(sample);
		}

		try {

			FileReader fr = new FileReader("application.properties");

			Properties p = new Properties();
			p.load(fr);

			bucketSize = Integer.parseInt(p.getProperty("bucketSize" + workRequesttype + user));
			qrPercent = Integer.parseInt(p.getProperty("QRPercent" + workRequesttype + user));

			int workRequestCount = userWorkRequestDetails.getWorkRequestProcessedCount();

			System.out.println("BucketSize: " + bucketSize + " QRPercent: " + qrPercent);

			qrOutput = isQRNeed(qrPercent, bucketSize, workRequestCount);

			if (qrOutput) {

				sample.add(workRequestCount - 1, 1);

				userWorkRequestDetails.setWRSentTracker(sample);

				userWorkRequestDetails.setNumberOfWRSentToQRForCurrentBucket(
						userWorkRequestDetails.getNumberOfWRSentToQRForCurrentBucket() + 1);
				userWorkRequestDetails
						.setTotalQrProcessedCounter(userWorkRequestDetails.getTotalQrProcessedCounter() + 1);
				userWorkRequestDetails.setQrProcessedCounterForCurrentBucket(
						userWorkRequestDetails.getQrProcessedCounterForCurrentBucket() + 1);
				status = "QR Sent";
				userWorkRequestDetails.setTotalWRsent(userWorkRequestDetails.getTotalWRsent() + 1);
				updateWorkRequest(workRequest, status);
				System.out.println("Total WR Sent To QR :" + userWorkRequestDetails.getTotalQrProcessedCounter());

			}

			else {

				status = "Not Sent To QR";

				sample.add(workRequestCount - 1, 0);

				userWorkRequestDetails.setWRSentTracker(sample);

				updateWorkRequest(workRequest, status);
				System.out.println("Total WR Sent To QR :" + userWorkRequestDetails.getTotalQrProcessedCounter());
			}

		}

		catch (Exception e) {
			System.out.println(e);
		}

	}

	public boolean isQRNeed(int qrPercent, int bucketSize, int workRequestCount) {

		int Min_Value = Math.min(bucketSize, workRequestCount);

		userWorkRequestDetails.setWRs_ToBe_Sent_To_QR((qrPercent * Min_Value) / 100);

		if (workRequestCount >= bucketSize) {

			List<Integer> sample = userWorkRequestDetails.getWRSentTracker();
			int index = workRequestCount - bucketSize;
			Object x = sample.get((index));

			if (x.equals(1)) {
				userWorkRequestDetails.setNumberOfWRSentToQRForCurrentBucket(
						userWorkRequestDetails.getNumberOfWRSentToQRForCurrentBucket() - 1);
			}

		}

		if (userWorkRequestDetails.getNumberOfWRSentToQRForCurrentBucket() <= userWorkRequestDetails
				.getWRs_ToBe_Sent_To_QR()) {

			if ((ProbabilityGenerator(qrPercent)) == true) {

				return true;

			} else {

				return false;

			}

		} else {

			return false;

		}
	}

	public boolean ProbabilityGenerator(int qrPercent) {

		return (Math.random() * 100) <= qrPercent;

	}

	public static void updateWorkRequest(String workRequest, String status) {

		System.out.println("WorkRequest - " + workRequest + " Status -" + status);
	}

}
