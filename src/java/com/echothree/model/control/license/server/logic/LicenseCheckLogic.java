// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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
import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URLEncoder;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class LicenseCheckLogic
        extends BaseLogic {

    private static final long DEMO_LICENSE_DURATION = 24 * 60 * 60 * 1000; // 24 hours
    private static final long LICENSE_RENEWAL_PERIOD = 72 * 60 * 60 * 1000; // 72 hours
    private static final long RETRY_DELAY = 15 * 60 * 1000; // 15 minutes
    
    final private Log log = LogFactory.getLog(this.getClass());
    final private AtomicBoolean executionPermitted = new AtomicBoolean(true);
    final private long initializedTime;
    final private AtomicLong licenseValidUntilTime;
    private Long lastLicenseAttempt;
    
    private LicenseCheckLogic() {
        super();
        
        initializedTime = System.currentTimeMillis();
        licenseValidUntilTime = new AtomicLong(initializedTime + DEMO_LICENSE_DURATION);
        log.info("Demo license installed for this instance.");
    }

    private static class LicenseCheckLogicLogicHolder {
        static LicenseCheckLogic instance = new LicenseCheckLogic();
    }

    public static LicenseCheckLogic getInstance() {
        return LicenseCheckLogicLogicHolder.instance;
    }
    
    public List<String> getFoundServerNames() {
        List<String> foundServerNames = new ArrayList<>();

        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

            while(interfaces.hasMoreElements()) {
                NetworkInterface nic = interfaces.nextElement();
                Enumeration<InetAddress> addresses = nic.getInetAddresses();
                
                while(addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    
                    if(!address.isLoopbackAddress()) {
                        foundServerNames.add(address.getCanonicalHostName());
                    }
                }
            }
        } catch(SocketException ex) {
            // Leave serverNames empty.
        }
        
        return foundServerNames;
    }
    
    public boolean attemptRetrieval() {
        List<String> foundServerNames = getFoundServerNames();
        boolean licenseUpdated = false;
        
        try(CloseableHttpClient client = HttpClientBuilder
                .create()
                .setUserAgent("Echo Three/1.0")
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setSocketTimeout(5000)
                        .setConnectTimeout(5000)
                        .setConnectionRequestTimeout(5000)
                        .build())
                .build()) {
            for(String foundServerName : foundServerNames) {
                HttpGet httpGet = null;

                try {
                    httpGet = new HttpGet("https://www.echothree.com/licenses/v1/" + URLEncoder.encode(foundServerName, "UTF-8") + ".xml");
                } catch(UnsupportedEncodingException uee) {
                    // httpGet remains null.
                }

                if(httpGet != null) {
                    log.info("Requesting license for: " + foundServerName);

                    try {
                        try(CloseableHttpResponse closeableHttpResponse = client.execute(httpGet)) {
                            if(closeableHttpResponse.getStatusLine().getStatusCode() == 200) {
                                HttpEntity entity = closeableHttpResponse.getEntity();

                                if(entity != null) {
                                    String text = CharStreams.toString(new InputStreamReader(entity.getContent(), Charsets.UTF_8));
                                    Properties properties = new Properties();

                                    properties.loadFromXML(new ByteArrayInputStream(text.getBytes(Charsets.UTF_8)));
                                    
                                    String retrievedServerName = properties.getProperty("serverName");
                                    
                                    if(foundServerName.equals(retrievedServerName)) {
                                        String retrievedLicenseValidUntilTime = properties.getProperty("licenseValidUntilTime");
                                        DateTimeFormatter fmt = DateTimeFormatter.ISO_DATE_TIME;
                                        
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
                                log.info("Request failed.");
                            }
                        }
                    } catch(IOException ioe) {
                        log.info("Request failed: IOException.");
                    }
                }
            }
        } catch(IOException ioe) {
            log.info("HttpClientBuilder failed: IOException.");
        }
        
        return licenseUpdated;
    }
    
    public void updateLicense(final Session session) {
        // If an attempt was made, and it failed, and RETRY_DELAY has passed, then
        // clear the last attempt time and allow another to happen.
        if(lastLicenseAttempt != null) {
            if(session.START_TIME - lastLicenseAttempt > RETRY_DELAY) {
                log.info("Clearing last license retrieval attempt.");
                lastLicenseAttempt = null;
            }
        }
        
        // If we're within LICENSE_RENEWAL_PERIOD of the licenseValidUntilTime, start
        // our attempts to renew the license.
        if(session.START_TIME + LICENSE_RENEWAL_PERIOD > licenseValidUntilTime.get() && lastLicenseAttempt == null) {
            log.info("Attempting license retrieval.");
            if(attemptRetrieval()) {
                // If the attempt was successful in retrieving something, clear the time we last attempted.
                log.info("Retrieval succeeded.");
                lastLicenseAttempt = null;
            } else {
                // Otherwise, if there was no last attempt time record it, save it.
                lastLicenseAttempt = session.START_TIME_LONG;
                log.info("Retrieval failed.");
            }
        }
    }

    public boolean permitExecution(final Session session) {
        boolean result = executionPermitted.get();

        // If we're past the licenseValidUntilTime, disable command execution.
        if(session.START_TIME > licenseValidUntilTime.get() && executionPermitted.get()) {
            log.error("Disabling command execution, license no longer valid.");
            executionPermitted.set(false);
            result = false;
        }

        return result;
    }
    
}
