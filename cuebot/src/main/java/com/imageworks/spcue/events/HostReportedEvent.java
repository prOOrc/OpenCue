package com.imageworks.spcue.events;

import java.util.LinkedList;
import java.util.List;

public class HostReportedEvent implements Event {
    public String HostId;
    public String HostRenderNodeId;
    public Boolean IsBoot;
    public List<RunningFrame> Frames;
    public HostReportedEvent(String hostId, String hostRenderNodeId, Boolean isBoot, List<RunningFrame> frames) {
        HostId = hostId;
        HostRenderNodeId = hostRenderNodeId;
        IsBoot = isBoot;
        Frames = new LinkedList<RunningFrame>();
        Frames.addAll(frames);
    }

    @Override
    public String getPartitionKey() {
        return this.HostId;
    }
}
