package com.imageworks.spcue.events;

public class RunningFrame implements Event {
    public String FrameId;
    public Integer FrameNumber;
    public String LayerId;
    public String JobId;
    public Long StartTime;
    public RunningFrame(String frameId, Integer frameNumber, String layerId, String jobId, Long startTime) {
        FrameId = frameId;
        FrameNumber = frameNumber;
        LayerId = layerId;
        JobId = jobId;
        StartTime = startTime;
    }

    @Override
    public String getPartitionKey() {
        return this.JobId;
    }
}
