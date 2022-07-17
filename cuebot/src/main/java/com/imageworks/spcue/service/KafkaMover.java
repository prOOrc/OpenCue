
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

package com.imageworks.spcue.service;

import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.util.JsonFormat;
import com.imageworks.spcue.events.Event;
import com.imageworks.spcue.util.CueExceptionUtil;

import org.apache.log4j.Logger;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public class KafkaMover extends ThreadPoolExecutor {
    private static final Logger logger = Logger.getLogger(KafkaMover.class);
    private final Gson gson = new GsonBuilder().serializeNulls().create();

    @Autowired
    private Environment env;
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final int THREAD_POOL_SIZE_INITIAL = 1;
    private static final int THREAD_POOL_SIZE_MAX = 1;
    private static final int QUEUE_SIZE_INITIAL = 1000;

    public KafkaMover() {
        super(THREAD_POOL_SIZE_INITIAL, THREAD_POOL_SIZE_MAX, 10, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(QUEUE_SIZE_INITIAL));
    }

    public void send(String topic, Object m) {
        if (env.getRequiredProperty("messaging.enabled", Boolean.class)) {
            try {
                execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String message;
                            String partitionKey;
                            if (m instanceof Event) {
                                Event event = (Event) m;
                                partitionKey = event.getPartitionKey();
                            } else {
                                partitionKey = null;
                            }
                            if (m instanceof Message) {
                                Message protobufMessage = (Message) m;
                                message = JsonFormat.printer().print(protobufMessage);
                            } else {
                                message = gson.toJson(m);
                            }
                            kafkaTemplate.send(topic, partitionKey, message);
                        } catch (KafkaException e) {
                            logger.warn("Failed to send Kafka message");
                            CueExceptionUtil.logStackTrace(
                                    "KafkaProducer " + this.getClass().toString() +
                                            " caught error ",
                                    e);
                        } catch (InvalidProtocolBufferException e) {
                            logger.warn("Failed to send Kafka message");
                            CueExceptionUtil.logStackTrace(
                                    "KafkaProducer " + this.getClass().toString() +
                                            " caught error ",
                                    e);
                        }
                    }
                });
            } catch (RejectedExecutionException e) {
                logger.warn("Outgoing Kafka message queue is full!");
                CueExceptionUtil.logStackTrace(
                        "KafkaProducer " + this.getClass().toString() +
                                " caught error ",
                        e);
            }
        }
    }

    public KafkaTemplate<String, String> getKafkaTemplate() {
        return kafkaTemplate;
    }

    public void setKafkaTemplate(KafkaTemplate<String, String> template) {
        this.kafkaTemplate = template;
    }
}
