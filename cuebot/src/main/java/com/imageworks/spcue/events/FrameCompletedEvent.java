package com.imageworks.spcue.events;

public class FrameCompletedEvent {
    public String FrameId;
    public String LayerId;
    public String JobId;
    public String HostId;
    public String HostVmId;
    
    public FrameCompletedEvent(String frameId, String layerId, String jobId, String hostId, String hostVmId) {
        FrameId = frameId;
        LayerId = layerId;
        JobId = jobId;
        HostId = hostId;
        HostVmId = hostVmId;
    }
}
