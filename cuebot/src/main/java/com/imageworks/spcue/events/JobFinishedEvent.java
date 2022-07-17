package com.imageworks.spcue.events;

public class JobFinishedEvent implements Event {
    public String JobId;
    
    public JobFinishedEvent(String jobId) {
        JobId = jobId;
    }

    @Override
    public String getPartitionKey() {
        return this.JobId;
    }
}
