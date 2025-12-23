// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
// Copyright 2016 Yurii Rashkovskii and Contributors
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

package com.echothree.service.graphql;

import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.service.graphql.internal.GraphQlRequest;
import com.echothree.service.graphql.internal.HttpRequestHandler;
import com.echothree.service.graphql.internal.invocation.GraphQlInvocationInput;
import com.echothree.service.graphql.internal.invocation.GraphQlInvocationInputFactory;
import com.echothree.service.graphql.internal.invoker.GraphQlQueryInvoker;
import com.echothree.view.client.web.WebConstants;
import com.echothree.view.client.web.util.HttpSessionUtils;
import com.google.common.io.CharStreams;
import static com.google.common.net.HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS;
import static com.google.common.net.HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS;
import static com.google.common.net.HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS;
import static com.google.common.net.HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN;
import static com.google.common.net.HttpHeaders.ACCESS_CONTROL_MAX_AGE;
import static com.google.common.net.HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS;
import static com.google.common.net.HttpHeaders.CONTENT_TYPE;
import static com.google.common.net.HttpHeaders.ORIGIN;
import com.google.common.net.MediaType;
import static com.google.common.net.MediaType.JSON_UTF_8;
import graphql.introspection.IntrospectionQuery;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;
import javax.naming.NamingException;
import javax.servlet.AsyncContext;
import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "graphql", urlPatterns = {"/"}, asyncSupported = true)
public class GraphQlServlet
        extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(GraphQlServlet.class);

    protected static final MediaType JSON = JSON_UTF_8.withoutParameters();
    protected static final MediaType GRAPHQL_UTF_8 = MediaType.parse("application/graphql;charset=utf-8");
    protected static final MediaType GRAPHQL = GRAPHQL_UTF_8.withoutParameters();

    protected static final String METHOD_GET = "GET";
    protected static final String METHOD_OPTIONS = "OPTIONS";
    protected static final String METHOD_POST = "POST";

    protected static final String SCHEME_HTTPS = "https";

    protected static final GraphQlRequest INTROSPECTION_REQUEST = new GraphQlRequest(IntrospectionQuery.INTROSPECTION_QUERY, null, null);

    protected GraphQlConfiguration configuration;
    protected HttpRequestHandler getHandler;
    protected HttpRequestHandler postHandler;

    protected GraphQlConfiguration getConfiguration() {
        return GraphQlConfiguration.with(GraphQlInvocationInputFactory.newBuilder().build())
                .build();
    }

    @Override
    public void init(ServletConfig servletConfig) {
        this.configuration = getConfiguration();

        this.getHandler = (request, response) -> {
            var path = request.getPathInfo();
            var invocationInputFactory = configuration.getInvocationInputFactory();
            var queryInvoker = configuration.getQueryInvoker();

            if(path == null) {
                path = request.getServletPath();
            }

            if(path.contentEquals("/schema.json")) {
                query(queryInvoker, invocationInputFactory.create(
                        INTROSPECTION_REQUEST), request, response);
            } else {
                var query = request.getParameter("query");

                if(query != null) {
                    var variables = request.getParameter("variables");
                    var operationName = request.getParameter("operationName");

                    query(queryInvoker, invocationInputFactory.createReadOnly(
                            new GraphQlRequest(query, variables, operationName)), request, response);
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    log.info("Bad GET request: path was not \"/schema.json\" or no query variable named \"query\" given");
                }
            }
        };

        this.postHandler = (request, response) -> {
            try {
                var invocationInputFactory = configuration.getInvocationInputFactory();
                var queryInvoker = configuration.getQueryInvoker();
                var contentType = request.getContentType();
                var mediaType = MediaType.parse(contentType);

                if(GRAPHQL.equals(mediaType) || GRAPHQL_UTF_8.equals(mediaType)) {
                    var query = CharStreams.toString(request.getReader());

                    query(queryInvoker, invocationInputFactory.create(
                            new GraphQlRequest(query, null, null)), request, response);
                } else if(JSON.equals(mediaType) || JSON_UTF_8.equals(mediaType)) {
                    var json = CharStreams.toString(request.getReader());

                    query(queryInvoker, invocationInputFactory.create(
                            new GraphQlRequest(json)), request, response);
                }
            } catch (Exception e) {
                log.info("Bad POST request: parsing failed", e);
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        };
    }

    private void doRequestAsync(HttpServletRequest request, HttpServletResponse response, HttpRequestHandler handler) {
        var asyncContext = request.startAsync();
        var asyncRequest = (HttpServletRequest) asyncContext.getRequest();
        var asyncResponse = (HttpServletResponse) asyncContext.getResponse();

        HttpSessionUtils.getInstance().setupUserVisit(request, response, true);

        new Thread(() -> doRequest(asyncRequest, asyncResponse, handler, asyncContext)).start();
    }

    private void doRequest(HttpServletRequest request, HttpServletResponse response, HttpRequestHandler handler, AsyncContext asyncContext) {
        try {
            if(request.getScheme().equals(SCHEME_HTTPS))
            {
                handler.handle(request, response);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Throwable t) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            log.error("Error executing GraphQL request!", t);
        } finally {
            asyncContext.complete();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        doRequestAsync(request, response, getHandler);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doRequestAsync(request, response, postHandler);
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) {
        var origin = request.getHeader(ORIGIN);
        var accessControlRequestHeaders = request.getHeader(ACCESS_CONTROL_REQUEST_HEADERS);

        response.addHeader(ACCESS_CONTROL_ALLOW_ORIGIN, origin == null ? "*" : origin);
        response.addHeader(ACCESS_CONTROL_ALLOW_CREDENTIALS, String.valueOf(true));
        response.addHeader(ACCESS_CONTROL_ALLOW_METHODS, METHOD_POST + ", " + METHOD_GET + ", " + METHOD_OPTIONS);
        response.addHeader(ACCESS_CONTROL_ALLOW_HEADERS,
                accessControlRequestHeaders == null ? CONTENT_TYPE + ", " + ORIGIN : accessControlRequestHeaders);
        response.addHeader(ACCESS_CONTROL_MAX_AGE, "86400");
    }

    public UserVisitPK getUserVisitPK(HttpServletRequest request) {
        var httpSession = request.getSession(true);

        return (UserVisitPK)httpSession.getAttribute(WebConstants.Session_USER_VISIT);
    }

    private void query(GraphQlQueryInvoker queryInvoker, GraphQlInvocationInput invocationInput,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, NamingException, ExecutionException, InterruptedException {
        var origin = request.getHeader(ORIGIN);
        var remoteAddr = request.getRemoteAddr();
        var result = queryInvoker.query(getUserVisitPK(request), invocationInput, remoteAddr);

        response.addHeader(ACCESS_CONTROL_ALLOW_ORIGIN, origin == null ? "*" : origin);
        response.addHeader(ACCESS_CONTROL_ALLOW_CREDENTIALS, String.valueOf(true));
        response.setContentType(JSON_UTF_8.toString());
        response.setStatus(HttpServletResponse.SC_OK);
        response.getOutputStream().write(result.getBytes(StandardCharsets.UTF_8));
    }
}
