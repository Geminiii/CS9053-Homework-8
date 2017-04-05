package edu.nyu.cs9053.homework8;

import java.util.*;

class Lambda {
	private final int startTime;
	private final int endTime;

	Lambda(int startTime, int endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public int getStartTime() {
		return this.startTime;
	}

	public int getEndTime() {
		return this.endTime;
	}

}

public class LambdaScheduler {

	public static List<Lambda> schedule(List<Lambda> jobList) {
		if (jobList == null || jobList.isEmpty()) {
			return jobList;
		}

		List<Lambda> scheduled = new ArrayList<>();

		Collections.sort(jobList, new Comparator<Lambda>(){
			@Override public int compare(Lambda a, Lambda b) {
				return a.getEndTime() - b.getEndTime();
			}
		});

		Lambda firstJob = jobList.get(0);
		int lastEndTime = firstJob.getEndTime();
		scheduled.add(firstJob);

		for (Lambda job: jobList) {
			if (job.getStartTime() > lastEndTime) {
				scheduled.add(job);
				lastEndTime = job.getEndTime();
			}
		}

		return scheduled;
	}
}
