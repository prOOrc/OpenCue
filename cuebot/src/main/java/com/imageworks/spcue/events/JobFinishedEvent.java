package com.imageworks.spcue.events;

public class JobFinishedEvent {
    public String JobId;
    
    public JobFinishedEvent(String jobId) {
        JobId = jobId;
    }
}
