package com.imageworks.spcue.events;

public class FrameCompletedEvent implements Event {
    public String FrameId;
    public Integer FrameNumber;
    public Integer RetryCount;
    public String FrameState;
    public String LayerId;
    public String JobId;
    public String HostId;
    public String HostRenderNodeId;
    public Long StopTime;
    
    public FrameCompletedEvent(String frameId, Integer frameNumber, Integer retryCount, String frameState, String layerId, String jobId, String hostId, String hostRenderNodeId, Long stopTime) {
        FrameId = frameId;
        FrameNumber = frameNumber;
        RetryCount = retryCount;
        FrameState = frameState;
        LayerId = layerId;
        JobId = jobId;
        HostId = hostId;
        HostRenderNodeId = hostRenderNodeId;
        StopTime = stopTime;
    }

    @Override
    public String getPartitionKey() {
        return this.JobId;
    }
}
