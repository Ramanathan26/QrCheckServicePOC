package com.example.demo.test;

import java.util.Scanner;

import com.example.demo.model.WorkRequest;
import com.example.demo.service.FixedBucketWRService;
import com.example.demo.service.RollingBucketWRService;
import com.example.demo.service.VaryingBucketWRService;
import com.example.repository.WorkRequestRepository;

public class QrCheckApplication {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int approach;
		System.out.println("1.Fixed Bucket 2.Varying Bucket 3.Rolling Bucket");
		System.out.println("\nEnter approach:");
		Scanner scanner = new Scanner(System.in);

		approach = scanner.nextInt();
		scanner.close();

		switch (approach) {
		case 1: {
			FixedBucketWRService wr = new FixedBucketWRService();
			
			WorkRequestRepository.setUp("user1", "WR1");
			for (int i = 1; i <= 100; i++) {
				String user = "user1";
				String workRequestType = "WR1";
				WorkRequest workRequest = new WorkRequest(i, user, workRequestType);
				WorkRequestRepository.saveWorkRequest(workRequest);
				wr.workRequestService(workRequest);
			}
			break;
		}
			
		case 2: {
			VaryingBucketWRService wr = new VaryingBucketWRService();
			WorkRequestRepository.setUp("user2", "WR2");
			for (int i = 1; i <= 100; i++) {
				String user = "user2";
				String workRequestType = "WR2";
				WorkRequest workRequest = new WorkRequest(i, user, workRequestType);
				WorkRequestRepository.saveWorkRequest(workRequest);
				wr.workRequestService(workRequest);
			}
			break;
		}
			
		case 3: {
			RollingBucketWRService wr = new RollingBucketWRService();
			
			WorkRequestRepository.setUp("user3", "WR3");
			for (int i = 1; i <= 100; i++) {
				String user = "user3";
				String workRequestType = "WR3";
				WorkRequest workRequest = new WorkRequest(i, user, workRequestType);
				WorkRequestRepository.saveWorkRequest(workRequest);
				wr.workRequestService(workRequest);
			}
break;
		}
		
		}
	}
}
