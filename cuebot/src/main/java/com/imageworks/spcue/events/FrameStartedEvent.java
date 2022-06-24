package com.imageworks.spcue.events;

public class FrameStartedEvent {
    public String FrameId;
    public String LayerId;
    public String JobId;
    public String HostId;
    public String HostRenderNodeId;
    public Long StartTime;
    
    public FrameStartedEvent(String frameId, String layerId, String jobId, String hostId, String hostRenderNodeId, Long startTime) {
        FrameId = frameId;
        LayerId = layerId;
        JobId = jobId;
        HostId = hostId;
        HostRenderNodeId = hostRenderNodeId;
        StartTime = startTime;
    }
}
