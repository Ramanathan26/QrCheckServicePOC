package com.example.demo.service;

import java.io.FileReader;
import java.util.Properties;

import com.example.demo.model.WorkRequest;
import com.example.repository.WorkRequestRepository;

public class RollingBucketWRService {

	private static int qrProcessedForCurrentBucket;

	public void workRequestService(WorkRequest workRequest) {
		boolean status = false;
		int qrPercent = 0;
		String workRequesttype = workRequest.getWorkRequestType();
		String user = workRequest.getUser();
		int bucketSize;
		boolean qrOutput = false;

		try {

			FileReader fr = new FileReader("application.properties");

			Properties p = new Properties();
			p.load(fr);

			bucketSize = Integer.parseInt(p.getProperty("bucketSize" + workRequesttype + user));
			qrPercent = WorkRequestRepository.getQrPercent(workRequest);

			int totalProcessedCount = WorkRequestRepository.getTotalProcessedCount(workRequest);

			if (totalProcessedCount >= bucketSize) {

				qrProcessedForCurrentBucket = WorkRequestRepository.getTotalQrProcessedRollingBucket(workRequest, bucketSize,
						totalProcessedCount);

			}

			qrOutput = isQRNeed(qrPercent, bucketSize, totalProcessedCount);

			if (qrOutput) {
				status = true;
				WorkRequestRepository.updateWorkRequest(workRequest, status);

			}

			else {
				status = false;
				WorkRequestRepository.updateWorkRequest(workRequest, status);
			}

		}

		catch (Exception e) {
			System.out.println(e);
		}

	}

	public boolean isQRNeed(int qrPercent, int bucketSize, int workRequestCount) {

		int minValue = Math.min(bucketSize, workRequestCount);

		int wrsToBeSentToQR = (qrPercent * minValue) / 100;

		if (qrProcessedForCurrentBucket <= wrsToBeSentToQR) {

			if ((probabilityGenerator(qrPercent)) == true) {

				return true;

			} else {

				return false;

			}

		} else {

			return false;

		}
	}

	public boolean probabilityGenerator(int qrPercent) {

		return (Math.random() * 100) <= qrPercent;

	}

}
