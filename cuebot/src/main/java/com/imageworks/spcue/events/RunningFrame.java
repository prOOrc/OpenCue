package com.imageworks.spcue.events;

public class RunningFrame {
    public String FrameId;
    public String LayerId;
    public String JobId;
    public Long StartTime;
    public RunningFrame(String frameId, String layerId, String jobId, Long startTime) {
        FrameId = frameId;
        LayerId = layerId;
        JobId = jobId;
        StartTime = startTime;
    }
}
