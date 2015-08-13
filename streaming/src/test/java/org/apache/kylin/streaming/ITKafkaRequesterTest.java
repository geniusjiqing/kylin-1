/*
 *
 *
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *
 *  contributor license agreements. See the NOTICE file distributed with
 *
 *  this work for additional information regarding copyright ownership.
 *
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *
 *  (the "License"); you may not use this file except in compliance with
 *
 *  the License. You may obtain a copy of the License at
 *
 *
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *
 *
 *  Unless required by applicable law or agreed to in writing, software
 *
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 *  See the License for the specific language governing permissions and
 *
 *  limitations under the License.
 *
 * /
 */

package org.apache.kylin.streaming;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.kylin.common.KylinConfig;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 */
public class ITKafkaRequesterTest extends KafkaBaseTest {

    private static final String NON_EXISTED_TOPIC = "non_existent_topic";
    private StreamingConfig streamingConfig;
    private KafkaClusterConfig kafkaClusterConfig;

    @Before
    public void before() {
        streamingConfig = StreamingManager.getInstance(KylinConfig.getInstanceFromEnv()).getStreamingConfig("kafka_test");
        kafkaClusterConfig = streamingConfig.getKafkaClusterConfigs().get(0);
    }

    @AfterClass
    public static void afterClass() {
    }

    @Test
    @Ignore("since ci does not enable kafka")
    public void testTopicMeta() throws Exception {
        TopicMeta kafkaTopicMeta = KafkaRequester.getKafkaTopicMeta(kafkaClusterConfig);
        assertNotNull(kafkaTopicMeta);
        assertEquals(2, kafkaTopicMeta.getPartitionIds().size());
        assertEquals(streamingConfig.getTopic(), kafkaTopicMeta.getName());

        StreamingConfig anotherTopicConfig = streamingConfig.clone();
        KafkaClusterConfig anotherClusterConfig = anotherTopicConfig.getKafkaClusterConfigs().get(0);
        anotherTopicConfig.setTopic(NON_EXISTED_TOPIC);

        kafkaTopicMeta = KafkaRequester.getKafkaTopicMeta(anotherClusterConfig);
        assertTrue(kafkaTopicMeta == null);
    }
}
