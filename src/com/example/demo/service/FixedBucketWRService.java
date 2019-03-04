package com.example.demo.service;

import java.io.FileReader;
import java.util.Properties;
import com.example.demo.model.WorkRequest;
import com.example.repository.WorkRequestRepository;

public class FixedBucketWRService {


	public void workRequestService(WorkRequest workRequest) {
		int totalQrProcessedInCurrentBucket;
		boolean status = false;
		int qrPercent = 0;
		int workRequestProcessedCount = 0;
		int totalProcessedInCurrentBucket = 0;
		int bucketSize;
		boolean qrOutput = false;
		int qrToBeProcessedPerBucket = 0;

		try {
			
			FileReader fr = new FileReader("application.properties");

			Properties p = new Properties();
			p.load(fr);

			bucketSize = Integer
					.parseInt(p.getProperty("bucketSize" + workRequest.getWorkRequestType() + workRequest.getUser()));
			workRequestProcessedCount = WorkRequestRepository.getTotalProcessedCount(workRequest);
			qrPercent = WorkRequestRepository.getQrPercent(workRequest);
			if (workRequestProcessedCount % bucketSize > 0) {
				totalProcessedInCurrentBucket = workRequestProcessedCount % bucketSize;
			} else {
				totalProcessedInCurrentBucket = bucketSize;
			}

			totalQrProcessedInCurrentBucket = WorkRequestRepository.getTotalQrProcessedInCurrentBucket(workRequest,
					totalProcessedInCurrentBucket, workRequestProcessedCount);

			qrToBeProcessedPerBucket = (qrPercent * bucketSize) / 100;

			if ((qrToBeProcessedPerBucket - totalQrProcessedInCurrentBucket) != (bucketSize
					- (totalProcessedInCurrentBucket - 1))) {
				if (qrToBeProcessedPerBucket > totalQrProcessedInCurrentBucket) {

					qrOutput = isQRNeed(qrPercent, totalProcessedInCurrentBucket, qrToBeProcessedPerBucket);

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

		}

		catch (Exception e) {
			System.out.println("Exception In Properties File " + e);
		}
	}

	public boolean isQRNeed(int qrPercent, int totalProcessedInCurrentBucket, int qrToBeProcessedPerBucket) {
		boolean result = false;
		int probabilityResult = 0;
		int qrToBeProcessedFromCurrentBucketPosition = (qrPercent * totalProcessedInCurrentBucket) / 100;

		if (qrToBeProcessedFromCurrentBucketPosition >= 0
				&& qrToBeProcessedFromCurrentBucketPosition <= qrToBeProcessedPerBucket)

		{
				probabilityResult = probabilityGenerator(qrPercent);
			}
		if (probabilityResult == 1) {
			result = true;
		}
		return result;
	}

	public int probabilityGenerator(int qrPercent) {
		int probResult = 0;
		int randomNumber = (int) (Math.random() * 100);
		if (randomNumber <= qrPercent) {
			probResult = 1;
		} else {
			probResult = 0;
		}
		return probResult;
	}

}
