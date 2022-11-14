package com.imageworks.spcue.events;

public class RunningFrame implements Event {
    public String FrameId;
    public Integer FrameNumber;
    public String LayerId;
    public String JobId;
    public Long StartTime;
    public Long Rss;
    public Long MaxRss;
    public Long Vsize;
    public Long MaxVsize;
    public Long UsedGpuMemory;
    public Long MaxUsedGpuMemory;

    public RunningFrame(
        String frameId,
        Integer frameNumber,
        String layerId,
        String jobId,
        Long startTime,
        Long rss,
        Long maxRss,
        Long vSize,
        Long maxVsize,
        Long usedGpuMemory,
        Long maxUsedGpuMemory) {
        FrameId = frameId;
        FrameNumber = frameNumber;
        LayerId = layerId;
        JobId = jobId;
        StartTime = startTime;
        Rss = rss;
        MaxRss = maxRss;
        Vsize = vSize;
        MaxVsize = maxVsize;
        UsedGpuMemory = usedGpuMemory;
        MaxUsedGpuMemory = maxUsedGpuMemory;
    }

    @Override
    public String getPartitionKey() {
        return this.JobId;
    }
}
