package com.imageworks.spcue.events;

public class FrameStartedEvent {
    public String FrameId;
    public String LayerId;
    public String JobId;
    public String HostId;
    public String HostVmId;
    public Long StartTime;
    
    public FrameStartedEvent(String frameId, String layerId, String jobId, String hostId, String hostVmId, Long startTime) {
        FrameId = frameId;
        LayerId = layerId;
        JobId = jobId;
        HostId = hostId;
        HostVmId = hostVmId;
        StartTime = startTime;
    }
}
