package com.imageworks.spcue.events;

public class FrameCompletedEvent {
    public String FrameId;
    public String LayerId;
    public String JobId;
    public String HostId;
    public String HostRenderNodeId;
    public Long StopTime;
    
    public FrameCompletedEvent(String frameId, String layerId, String jobId, String hostId, String hostRenderNodeId, Long stopTime) {
        FrameId = frameId;
        LayerId = layerId;
        JobId = jobId;
        HostId = hostId;
        HostRenderNodeId = hostRenderNodeId;
        StopTime = stopTime;
    }
}
