package com.imageworks.spcue.events;

public class FrameCompletedEvent {
    public String FrameId;
    public String LayerId;
    public String JobId;
    public String HostId;
    public String HostVmId;
    public Long StopTime;
    
    public FrameCompletedEvent(String frameId, String layerId, String jobId, String hostId, String hostVmId, Long stopTime) {
        FrameId = frameId;
        LayerId = layerId;
        JobId = jobId;
        HostId = hostId;
        HostVmId = hostVmId;
        StopTime = stopTime;
    }
}
