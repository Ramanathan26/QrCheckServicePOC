package com.example.demo.service;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.example.demo.model.UserWorkRequestMapping;

public class VaryingBucketWRService {

	UserWorkRequestMapping userWorkRequestMapping = null;
	List<UserWorkRequestMapping> userWorkRequestMappingList = new ArrayList<>();

	public void WorkRequestService(String workRequest) {
		String status = null;
		double qrPercent;
		String workRequesttype = "wR1";
		String user = "user1";

		int bucketSize;
		boolean qrOutput = false;

		if (userWorkRequestMappingList.size() != 0) {
			for (UserWorkRequestMapping uWrInfo : userWorkRequestMappingList) {

				if (uWrInfo.getUser().equals(user) && uWrInfo.getWorkRequestType().equals(workRequesttype)) {
					uWrInfo.setWorkRequestProcessedCount(uWrInfo.getWorkRequestProcessedCount() + 1);
					userWorkRequestMapping = uWrInfo;
					break;
				}

			}
			if (userWorkRequestMapping == null) {
				UserWorkRequestMapping uWr = new UserWorkRequestMapping(user, workRequesttype);
				uWr.setWorkRequestProcessedCount(1);
				userWorkRequestMappingList.add(uWr);
				userWorkRequestMapping = uWr;
			}
		} else {
			UserWorkRequestMapping uWr = new UserWorkRequestMapping(user, workRequesttype);
			uWr.setWorkRequestProcessedCount(1);
			userWorkRequestMappingList.add(uWr);
			userWorkRequestMapping = uWr;
		}

		try {
			FileReader fr = new FileReader("application.properties");

			Properties p = new Properties();
			p.load(fr);

			if (userWorkRequestMapping.getBucketSize() == 0 && userWorkRequestMapping.getQrPercent() == 0.0) {
				bucketSize = Integer.parseInt(p.getProperty("bucketSize" + workRequesttype + user));
				qrPercent = Double.parseDouble(p.getProperty("QRPercent" + workRequesttype + user));
			} else {
				bucketSize = userWorkRequestMapping.getBucketSize();
				qrPercent = userWorkRequestMapping.getQrPercent();
			}

			System.out.println("\nBucket Size " + bucketSize);

			qrOutput = isQRNeed(qrPercent, bucketSize);

			if (qrOutput) {
				userWorkRequestMapping
						.setTotalQrProcessedCounter(userWorkRequestMapping.getTotalQrProcessedCounter() + 1);
				status = "QR Sent";
				updateWorkRequest(workRequest, status);
			}

			else {

				status = "Not Sent To QR";
				updateWorkRequest(workRequest, status);
			}

			if (userWorkRequestMapping.getBucketSize() == 0) {
				userWorkRequestMapping.setQrPercent(0.0);
				userWorkRequestMapping.setBucketSize(0);
				userWorkRequestMapping.setWrToBeSentToQR(0);

				System.out.println("\\*============================================*\\");
			}
		} catch (Exception e) {
			System.out.println("Exception In Properties File " + e);
		}
	}

	public boolean isQRNeed(double qrPercent, int bucketSize) {
		boolean result = false;
		int randomNumber = (int) (Math.random() * 100);

		int wrsToBeSentToQr = (int) Math.ceil((qrPercent * bucketSize) / 100);

		if (wrsToBeSentToQr != 0) {
			if (randomNumber <= qrPercent) {

				bucketSize--;
				userWorkRequestMapping.setBucketSize(bucketSize);
				wrsToBeSentToQr--;
				userWorkRequestMapping.setWrToBeSentToQR(wrsToBeSentToQr);
				if (bucketSize != 0) {
					qrPercent = Math.round((userWorkRequestMapping.getWrToBeSentToQR() * 100)
							/ userWorkRequestMapping.getBucketSize());
				}
				userWorkRequestMapping.setQrPercent(qrPercent);
				result = true;
			} else {
				bucketSize--;
				userWorkRequestMapping.setBucketSize(bucketSize);
				userWorkRequestMapping.setWrToBeSentToQR(wrsToBeSentToQr);
				if (bucketSize != 0) {
					qrPercent = Math.round((userWorkRequestMapping.getWrToBeSentToQR() * 100)
							/ userWorkRequestMapping.getBucketSize());
				}
				userWorkRequestMapping.setQrPercent(qrPercent);
				result = false;
			}

		} else {
			bucketSize--;
			userWorkRequestMapping.setBucketSize(bucketSize);
		}
		return result;
	}

	public static void updateWorkRequest(String workRequest, String status) {
		System.out.println("WorkRequest - " + workRequest + " Status - " + status);
	}
}
