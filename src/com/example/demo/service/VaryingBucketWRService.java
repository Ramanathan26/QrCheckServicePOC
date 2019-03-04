package com.example.demo.service;

import java.io.FileReader;
import java.util.Properties;

import com.example.demo.model.WorkRequest;
import com.example.repository.WorkRequestRepository;

public class VaryingBucketWRService {


	public void workRequestService(WorkRequest workRequest) {
		boolean status = false;
		int qrPercent;
		String workRequestType = workRequest.getWorkRequestType();
		String user = workRequest.getUser();

		int workRequestProcessedCount = 0;
		int totalProcessedInCurrentBucket = 0;
		int totalQrProcessedInCurrentBucket = 0;
		int wrsToBeSentToQr = 0;

		int qrToBeProcessedPerBucket = 0;
		int bucketSize;
		boolean qrOutput = false;

		try {
			FileReader fr = new FileReader("application.properties");

			Properties p = new Properties();
			p.load(fr);

			qrPercent = WorkRequestRepository.getQrPercent(workRequest);
			bucketSize = Integer.parseInt(p.getProperty("bucketSize" + workRequestType + user));

			qrToBeProcessedPerBucket = (qrPercent * bucketSize) / 100;
			workRequestProcessedCount = WorkRequestRepository.getTotalProcessedCount(workRequest);

			if (workRequestProcessedCount % bucketSize > 0) {
				totalProcessedInCurrentBucket = workRequestProcessedCount % bucketSize;
			} else {
				totalProcessedInCurrentBucket = bucketSize;
			}

			totalQrProcessedInCurrentBucket = WorkRequestRepository.getTotalQrProcessedInCurrentBucket(workRequest,
					totalProcessedInCurrentBucket, workRequestProcessedCount);

			wrsToBeSentToQr = qrToBeProcessedPerBucket - totalQrProcessedInCurrentBucket;
			int remainingBucketSize = bucketSize - (totalProcessedInCurrentBucket - 1);

			if ((qrToBeProcessedPerBucket - totalQrProcessedInCurrentBucket) != (bucketSize
					- (totalProcessedInCurrentBucket - 1))) {
				if (qrToBeProcessedPerBucket > totalQrProcessedInCurrentBucket) {

					qrOutput = isQRNeed(wrsToBeSentToQr, remainingBucketSize);
				}

				if (qrOutput) {

					status = true;
					WorkRequestRepository.updateWorkRequest(workRequest, status);
				}

				else {

					status = false;
					WorkRequestRepository.updateWorkRequest(workRequest, status);
				}
			} else {
				status = true;
				WorkRequestRepository.updateWorkRequest(workRequest, status);
			}

			if (workRequestProcessedCount % bucketSize == 0) {
				System.out.println("\\*============================================*\\");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isQRNeed(int wrsToBeSentToQr, int remainingBucketSize) {
		boolean result = false;
		int randomNumber = (int) (Math.random() * 100);
		int qrPercent = 0;
		if (remainingBucketSize != 0) {
			qrPercent = Math.round(wrsToBeSentToQr * 100 / remainingBucketSize);
		}
		if (wrsToBeSentToQr != 0) {
			if (randomNumber <= qrPercent) {

				result = true;
			}
		}

		else {
			result = false;
		}
		return result;
	}

}
