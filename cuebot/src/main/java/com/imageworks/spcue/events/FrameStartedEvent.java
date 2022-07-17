package com.imageworks.spcue.events;

public class FrameStartedEvent implements Event {
    public String FrameId;
    public Integer FrameNumber;
    public String LayerId;
    public String JobId;
    public String HostId;
    public String HostRenderNodeId;
    public Long StartTime;
    
    public FrameStartedEvent(String frameId, Integer frameNumber, String layerId, String jobId, String hostId, String hostRenderNodeId, Long startTime) {
        FrameId = frameId;
        FrameNumber = frameNumber;
        LayerId = layerId;
        JobId = jobId;
        HostId = hostId;
        HostRenderNodeId = hostRenderNodeId;
        StartTime = startTime;
    }

    @Override
    public String getPartitionKey() {
        return this.JobId;
    }
}
