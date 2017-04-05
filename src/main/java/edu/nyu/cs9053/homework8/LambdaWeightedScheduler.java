package edu.nyu.cs9053.homework8;

import java.util.*;

class LambdaWeighted {
	private final int startTime;
	private final int endTime;
	private final int weight;

	LambdaWeighted(int startTime, int endTime, int weight) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.weight = weight;
	}

	public int getStartTime() {
		return this.startTime;
	}

	public int getEndTime() {
		return this.endTime;
	}

	public int getWeight() {
		return this.weight;
	}
}

public class LambdaWeightedScheduler {

	private static int previousCompatible(List<LambdaWeighted> jobList, int i) {
		for (int j = i - 1; j >= 0; j--) {
			if (jobList.get(j).getEndTime() <= jobList.get(i).getStartTime()) {
				return j;
			}
		}

		return -1;
	}

	private static void retrieve(List<LambdaWeighted> jobList, List<LambdaWeighted> scheduled, int[] record, int i) {
		if (i == 0) {
			return;
		}

		LambdaWeighted curJob = jobList.get(i);

		int previousCompatible = previousCompatible(jobList, i);

		if (previousCompatible == -1 && curJob.getWeight() > record[i-1]) {
			scheduled.add(curJob);
		} else if (previousCompatible != -1 && (curJob.getWeight() + record[previousCompatible]) > record[i - 1]) {
			scheduled.add(curJob);
			retrieve(jobList, scheduled, record, previousCompatible);
		} else {
			retrieve(jobList, scheduled, record, i-1);
		}
	}

	public static List<LambdaWeighted> schedule(List<LambdaWeighted> jobList) {
		if (jobList == null || jobList.isEmpty()) {
			return jobList;
		}

		Collections.sort(jobList, new Comparator<LambdaWeighted>(){
			@Override public int compare(LambdaWeighted a, LambdaWeighted b) {
				return a.getEndTime() - b.getEndTime();
			}
		});

		int size = jobList.size();
		int[] record = new int[size];
		record[0] = jobList.get(0).getWeight();

		for (int i = 1; i < size; i++) {
			int weight = jobList.get(i).getWeight();
			int previousCompatible = previousCompatible(jobList, i);
			if (previousCompatible != -1) {
				weight += record[previousCompatible];
			}

			record[i] = Math.max(weight, record[i - 1]);
		}

		List<LambdaWeighted> scheduled = new ArrayList<>();

		retrieve(jobList, scheduled, record, size-1);

		return scheduled;
	}
}