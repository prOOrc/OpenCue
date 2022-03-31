
/*
 * Copyright Contributors to the OpenCue Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.imageworks.spcue.rfarm;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.apache.log4j.Logger;

import com.imageworks.spcue.grpc.rfarm.RenderHost;
import com.imageworks.spcue.JobInterface;
import com.imageworks.spcue.grpc.rfarm.FrameCompleteReport;
import com.imageworks.spcue.grpc.rfarm.JobCompleteReport;
import com.imageworks.spcue.grpc.rfarm.RFarmInterfaceGrpc;
import com.imageworks.spcue.grpc.rfarm.RFarmReportFrameCompleteRequest;
import com.imageworks.spcue.grpc.rfarm.RFarmReportFrameCompleteResponse;
import com.imageworks.spcue.grpc.rfarm.RFarmReportJobCompleteRequest;
import com.imageworks.spcue.grpc.rfarm.RunningFrameInfo;

public final class RFarmClientGrpc implements RFarmClient {
    private static final Logger logger = Logger.getLogger(RFarmClientGrpc.class);

    private final int rqdTaskDeadlineSeconds;
    private ManagedChannel channel;

    private boolean testMode = false;

    public RFarmClientGrpc(String rFarmServerHost, int rFarmServerPort, int rqdTaskDeadline) {
        this.rqdTaskDeadlineSeconds = rqdTaskDeadline;
        ManagedChannelBuilder<?> channelBuilder = ManagedChannelBuilder
                .forAddress(rFarmServerHost, rFarmServerPort)
                .usePlaintext();
        this.channel = channelBuilder.build();
    }

    private RFarmInterfaceGrpc.RFarmInterfaceBlockingStub getStub() throws ExecutionException {
        return RFarmInterfaceGrpc
                .newBlockingStub(channel)
                .withDeadlineAfter(rqdTaskDeadlineSeconds, TimeUnit.SECONDS);
    }

    @Override
    public void setTestMode(boolean testMode) {
        this.testMode = testMode;
    }

    @Override
    public void reportFrameComplete(
            RenderHost host,
            RunningFrameInfo frameInfo) {
        RFarmReportFrameCompleteRequest request = RFarmReportFrameCompleteRequest.newBuilder()
                .setFrameCompleteReport(
                        FrameCompleteReport.newBuilder()
                                .setHost(host)
                                .setFrame(frameInfo)
                                .build())
                .build();

        if (testMode) {
            return;
        }

        try {
            getStub().reportFrameComplete(request);
        } catch (StatusRuntimeException | ExecutionException e) {
            throw new RFarmClientException("failed to send report", e);
        }
    }

    @Override
    public void reportJobComplete(JobInterface job) {
        RFarmReportJobCompleteRequest request = RFarmReportJobCompleteRequest.newBuilder()
                .setJobCompleteReport(
                        JobCompleteReport.newBuilder()
                                .setJobId(job.getId())
                                .build())
                .build();

        if (testMode) {
            return;
        }

        try {
            getStub().reportJobComplete(request);
        } catch (StatusRuntimeException | ExecutionException e) {
            throw new RFarmClientException("failed to send report", e);
        }
    }

    public void shutdown() {
        channel.shutdown();
    }
}
