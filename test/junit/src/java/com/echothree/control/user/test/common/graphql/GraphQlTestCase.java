// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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

package com.echothree.control.user.test.common.graphql;

import com.echothree.util.common.string.GraphQlUtils;
import com.google.common.io.CharStreams;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;

public abstract class GraphQlTestCase {

    protected boolean getBoolean(Object bean, String name)
            throws Exception {
        return (Boolean)PropertyUtils.getProperty(bean, name);
    }

    protected int getInteger(Object bean, String name)
            throws Exception {
        return Long.valueOf(getLong(bean, name)).intValue();
    }

    protected long getLong(Object bean, String name)
            throws Exception {
        return Math.round(getDouble(bean, name));
    }

    protected double getDouble(Object bean, String name)
            throws Exception {
        var o = PropertyUtils.getProperty(bean, name);
        double result;
        
        if(o instanceof Double) {
            result = (double)o;
        } else if(o instanceof String) {
            result = Double.parseDouble((String)o);
        } else {
            throw new Exception("Unidentified type for o");
        }
        
        return result;
    }

    protected String getString(Object bean, String name)
            throws Exception {
        return (String)PropertyUtils.getProperty(bean, name);
    }

    protected List<Map<String, Object>> getList(Object bean, String name)
            throws Exception {
        return (List<Map<String, Object>>)PropertyUtils.getProperty(bean, name);
    }

    protected Map<String, Object> getMap(Object bean, String name)
            throws Exception {
        return (Map<String, Object>)PropertyUtils.getProperty(bean, name);
    }

    protected Object getObject(Object bean, String name)
            throws Exception {
        return PropertyUtils.getProperty(bean, name);
    }
    
    private static SSLConnectionSocketFactory sslSocketFactory;
    
    @BeforeClass
    public static void beforeClass()
            throws Exception {
        sslSocketFactory = new SSLConnectionSocketFactory(
                SSLContexts.custom()
                        .loadTrustMaterial(null, new TrustSelfSignedStrategy())
                        .build(),
                NoopHostnameVerifier.INSTANCE);
    }
    
    @AfterClass
    public static void afterClass() {
        sslSocketFactory = null;
    }
    
    private CloseableHttpClient client;
    
    @Before
    public void before() {
        client = HttpClientBuilder
                .create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setSocketTimeout(5000)
                        .setConnectTimeout(5000)
                        .setConnectionRequestTimeout(5000)
                        .build())
                .setSSLSocketFactory(sslSocketFactory)
                .build();
    }
    
    @After
    public void after()
            throws Exception {
        client.close();
    }

    private URIBuilder getURIBuilder() {
        return new URIBuilder().setScheme("https")
                .setHost("127.0.0.1")
                .setPath("/graphql");
    }

    protected Map<String, Object> executeUsingGet(String query)
            throws IOException, URISyntaxException {
        var builder = getURIBuilder();

        if(query != null) {
            builder.setParameter("query", query);
        }

        var httpGet = new HttpGet(builder.build());

        return doRequest(client.execute(httpGet));
    }

    protected Map<String, Object> executeUsingPost(String query)
            throws IOException, URISyntaxException {
        var builder = getURIBuilder();
        Map<String, Object> map = new HashMap<>();

        if(query != null) {
            map.put("query", query);
        }

        var httpPost = new HttpPost(builder.build());
        httpPost.setHeader("Content-type", "application/json");
        var requestEntity = new StringEntity(GraphQlUtils.getInstance().toJson(map));
        httpPost.setEntity(requestEntity);

        return doRequest(client.execute(httpPost));
    }

    private Map<String, Object> doRequest(CloseableHttpResponse execute)
            throws IOException {
        Map<String, Object> body = null;

        try(var closeableHttpResponse = execute) {
            var statusCode = closeableHttpResponse.getStatusLine().getStatusCode();

            Assert.assertEquals(200, statusCode);

            var responseEntity = closeableHttpResponse.getEntity();
            if(responseEntity != null) {
                var text = CharStreams.toString(new InputStreamReader(responseEntity.getContent(), StandardCharsets.UTF_8));

                body = GraphQlUtils.getInstance().toMap(text);
            }

            EntityUtils.consume(responseEntity);
        }

        return body;
    }
}
