package com.example.demo.test;

import java.util.Scanner;

import com.example.demo.service.FixedBucketWRService;
import com.example.demo.service.RollingBucketWRService;
import com.example.demo.service.VaryingBucketWRService;

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
			for (int i = 1; i <= 100; i++) {
				String workRequest = "WR";
				workRequest = workRequest.concat(Integer.toString(i));
				wr.WorkRequestService(workRequest);
			}
		}
			break;
		case 2: {
			VaryingBucketWRService wr = new VaryingBucketWRService();
			for (int i = 1; i <= 100; i++) {
				String workRequest = "WR";
				workRequest = workRequest.concat(Integer.toString(i));
				wr.WorkRequestService(workRequest);
			}
		}
			break;
		case 3: {
			RollingBucketWRService wr = new RollingBucketWRService();
			for (int i = 1; i <= 100; i++) {
				String workRequest = "WR";
				workRequest = workRequest.concat(Integer.toString(i));
				wr.WorkRequestService(workRequest);
			}

		}
			break;
		}
	}
}
