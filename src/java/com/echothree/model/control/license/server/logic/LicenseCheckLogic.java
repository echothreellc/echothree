// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.model.control.license.server.logic;

import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.persistence.Session;
import com.google.common.io.CharStreams;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

@ApplicationScoped
public class LicenseCheckLogic
        extends BaseLogic {

    private static final long DEMO_LICENSE_DURATION = 24 * 60 * 60 * 1000; // 24 hours
    private static final long LICENSE_RENEWAL_PERIOD = 72 * 60 * 60 * 1000; // 72 hours
    private static final long RETRY_DELAY = 15 * 60 * 1000; // 15 minutes
    
    final private Log log = LogFactory.getLog(this.getClass());
    final private AtomicBoolean executionPermitted = new AtomicBoolean(true);
    final private AtomicLong licenseValidUntilTime;
    private Long lastLicenseAttempt;
    
    protected LicenseCheckLogic() {
        var initializedTime = System.currentTimeMillis();

        licenseValidUntilTime = new AtomicLong(initializedTime + DEMO_LICENSE_DURATION);
        log.info("Demo license installed for this instance.");
    }

    public static LicenseCheckLogic getInstance() {
        return CDI.current().select(LicenseCheckLogic.class).get();
    }

    public List<String> getFoundServerNames() {
        var foundServerNames = new ArrayList<String>();

        try {
            NetworkInterface.networkInterfaces()
                    .forEach(ni -> ni.inetAddresses()
                            .forEach(ia -> {
                                if(!ia.isLoopbackAddress()) {
                                    foundServerNames.add(ia.getCanonicalHostName());
                                }
                            }));
        } catch(SocketException se) {
            // Leave serverNames empty.
            log.error("Exception determining server names: ", se);
        }
        
        return foundServerNames;
    }
    
    public boolean attemptRetrieval() {
        var foundServerNames = getFoundServerNames();
        var licenseUpdated = false;
        
        try(var client = HttpClientBuilder
                .create()
                .setUserAgent("Echo Three/1.0")
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setSocketTimeout(5000)
                        .setConnectTimeout(5000)
                        .setConnectionRequestTimeout(5000)
                        .build())
                .build()) {
            for(var foundServerName : foundServerNames) {
                var httpGet = new HttpGet("https://www.echothree.com/licenses/v1/" + URLEncoder.encode(foundServerName, StandardCharsets.UTF_8) + ".xml");

                log.info("Requesting license for: " + foundServerName);

                try {
                    try(var closeableHttpResponse = client.execute(httpGet)) {
                        var statusCode = closeableHttpResponse.getStatusLine().getStatusCode();

                        if(statusCode == 200) {
                            var entity = closeableHttpResponse.getEntity();

                            if(entity != null) {
                                var text = CharStreams.toString(new InputStreamReader(entity.getContent(), StandardCharsets.UTF_8));
                                var properties = new Properties();

                                properties.loadFromXML(new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8)));

                                var retrievedServerName = properties.getProperty("serverName");

                                if(foundServerName.equals(retrievedServerName)) {
                                    var retrievedLicenseValidUntilTime = properties.getProperty("licenseValidUntilTime");

                                    licenseValidUntilTime.set(ZonedDateTime.parse(retrievedLicenseValidUntilTime).toInstant().toEpochMilli());

                                    log.info("License valid until: " + retrievedLicenseValidUntilTime);

                                    licenseUpdated = true;
                                } else {
                                    log.error("The detected server name is not equal to the retrieved server name.");
                                }
                            }

                            EntityUtils.consume(entity);

                            if(licenseUpdated) {
                                break;
                            }
                        } else {
                            log.info("Request failed for " + foundServerName + ": " + statusCode);
                        }
                    }
                } catch(IOException ioe) {
                    log.error("Request failed: IOException.", ioe);
                }
            }
        } catch(IOException ioe) {
            log.error("HttpClientBuilder failed: IOException.", ioe);
        }
        
        return licenseUpdated;
    }
    
    public void updateLicense(final Session session) {
        // If an attempt was made, and it failed, and RETRY_DELAY has passed, then
        // clear the last attempt time and allow another to happen.
        if(lastLicenseAttempt != null) {
            if(session.getStartTime() - lastLicenseAttempt > RETRY_DELAY) {
                log.info("Clearing last license retrieval attempt.");
                lastLicenseAttempt = null;
            }
        }
        
        // If we're within LICENSE_RENEWAL_PERIOD of the licenseValidUntilTime, start
        // our attempts to renew the license.
        if(session.getStartTime() + LICENSE_RENEWAL_PERIOD > licenseValidUntilTime.get() && lastLicenseAttempt == null) {
            log.info("Attempting license retrieval.");
            if(attemptRetrieval()) {
                // If the attempt was successful in retrieving something, clear the time we last attempted.
                log.info("Retrieval succeeded.");
                lastLicenseAttempt = null;
            } else {
                // Otherwise, if there was no last attempt time record it, save it.
                lastLicenseAttempt = session.getStartTimeLong();
                log.info("Retrieval failed.");
            }
        }
    }

    public boolean permitExecution(final Session session) {
        var result = executionPermitted.get();

        // If we're past the licenseValidUntilTime, disable command execution.
        if(session.getStartTime() > licenseValidUntilTime.get() && executionPermitted.get()) {
            log.error("Disabling command execution, license no longer valid.");
            executionPermitted.set(false);
            result = false;
        }

        return result;
    }
    
}
