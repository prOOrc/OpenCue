package com.imageworks.spcue.events;

import java.util.LinkedList;
import java.util.List;

import com.imageworks.spcue.grpc.report.RunningFrameInfo;

public class HostReportedEvent {
    public String HostId;
    public String HostVmId;
    public Boolean IsBoot;
    public List<RunningFrame> Frames;
    public HostReportedEvent(String hostId, String hostVmId, Boolean isBoot, List<RunningFrameInfo> frames) {
        HostId = hostId;
        HostVmId = hostVmId;
        IsBoot = isBoot;
        Frames = new LinkedList<RunningFrame>();
        for (RunningFrameInfo runningFrameInfo : frames) {
            Frames.add(
                new RunningFrame(
                    runningFrameInfo.getFrameId(),
                    runningFrameInfo.getLayerId(),
                    runningFrameInfo.getJobId(),
                    runningFrameInfo.getStartTime()));
        }
    }
}