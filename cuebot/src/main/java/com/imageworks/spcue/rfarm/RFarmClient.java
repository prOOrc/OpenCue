
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

import com.imageworks.spcue.JobInterface;
import com.imageworks.spcue.grpc.rfarm.RenderHost;
import com.imageworks.spcue.grpc.rfarm.RunningFrameInfo;

public interface RFarmClient {

    /**
     * Setting to true pretends all remote procedures execute perfectly.
     *
     * @param tests
     */
    public void setTestMode(boolean tests);

    /**
     * Report about frame complete
     *
     * @param host
     * @param frameInfo
     * @return response
     */
    void reportFrameComplete(RenderHost host, RunningFrameInfo frameInfo);

    /**
     * Report about job complete
     *
     * @param jobId
     * @return response
     */
    void reportJobComplete(JobInterface job);
}
