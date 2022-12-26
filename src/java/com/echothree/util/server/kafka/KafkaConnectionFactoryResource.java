// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// --------------------------------------------------------------------------------

package com.echothree.util.server.kafka;

import fish.payara.cloud.connectors.kafka.api.KafkaConnectionFactory;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class KafkaConnectionFactoryResource {

    private final Log log = LogFactory.getLog(KafkaConnectionFactoryResource.class);

    private static final String KCF = "java:/KafkaConnectionFactory";

    private static final KafkaConnectionFactoryResource instance = new KafkaConnectionFactoryResource();

    private final KafkaConnectionFactory kafkaConnectionFactory;

    @SuppressWarnings("BanJNDI")
    protected KafkaConnectionFactoryResource() {
        KafkaConnectionFactory kafkaConnectionFactory;

        // This provides a soft failure vs. the hard failure from using @Resource.
        try {
            kafkaConnectionFactory = InitialContext.doLookup(KCF);
            log.info("Found " + KCF + ", KafkaConnectionFactory available");
        } catch (NamingException ne) {
            kafkaConnectionFactory = null;
            log.error("Unable to locate " + KCF + ", KafkaConnectionFactory not available");
        }

        this.kafkaConnectionFactory = kafkaConnectionFactory;
    }

    public static KafkaConnectionFactoryResource getInstance() {
        return instance;
    }

    public KafkaConnectionFactory getKafkaConnectionFactory() {
        return kafkaConnectionFactory;
    }

}
