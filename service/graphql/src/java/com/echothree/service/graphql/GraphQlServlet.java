// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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
import com.echothree.view.client.web.WebConstants;
import com.echothree.view.client.web.util.HttpSessionUtils;
import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import graphql.introspection.IntrospectionQuery;
import javax.naming.NamingException;
import javax.servlet.AsyncContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(urlPatterns = {"/"}, asyncSupported = true)
public class GraphQlServlet
        extends HttpServlet {

    public static final Logger log = LoggerFactory.getLogger(GraphQlServlet.class);

    public static final String APPLICATION_JSON = "application/json";
    public static final String APPLICATION_JSON_UTF8 = "application/json;charset=UTF-8";
    public static final String APPLICATION_GRAPHQL = "application/graphql";
    public static final String APPLICATION_GRAPHQL_UTF8 = "application/graphql;charset=UTF-8";

    public static final int STATUS_OK = 200;
    public static final int STATUS_BAD_REQUEST = 400;

    private static final GraphQlRequest INTROSPECTION_REQUEST = new GraphQlRequest(IntrospectionQuery.INTROSPECTION_QUERY, null, null);
    private static final String[] MULTIPART_KEYS = new String[]{"operations", "graphql", "query"};

    private GraphQlConfiguration configuration;
    private HttpRequestHandler getHandler;
    private HttpRequestHandler postHandler;

    protected GraphQlConfiguration getConfiguration() {
        return GraphQlConfiguration.with(GraphQlInvocationInputFactory.newBuilder().build())
                .build();
    }

    @Override
    public void init(ServletConfig servletConfig) {
        this.configuration = getConfiguration();

        this.getHandler = (request, response) -> {
            String path = request.getPathInfo();
            GraphQlInvocationInputFactory invocationInputFactory = configuration.getInvocationInputFactory();
            GraphQlQueryInvoker queryInvoker = configuration.getQueryInvoker();

            if (path == null) {
                path = request.getServletPath();
            }

            if (path.contentEquals("/schema.json")) {
                query(queryInvoker, invocationInputFactory.create(INTROSPECTION_REQUEST, request, response), request, response);
            } else {
                String query = request.getParameter("query");

                if (query != null) {
                    if (isBatchedQuery(query)) {
                        //queryBatched(queryInvoker, invocationInputFactory.createReadOnly(graphQLObjectMapper.readBatchedGraphQLRequest(query), request, response), response);
                    } else {
                        String variables = request.getParameter("variables");
                        String operationName = request.getParameter("operationName");

                        query(queryInvoker, invocationInputFactory.createReadOnly(new GraphQlRequest(query, variables, operationName), request, response), request, response);
                    }
                } else {
                    response.setStatus(STATUS_BAD_REQUEST);
                    log.info("Bad GET request: path was not \"/schema.json\" or no query variable named \"query\" given");
                }
            }
        };

        this.postHandler = (request, response) -> {
            try {
                GraphQlInvocationInputFactory invocationInputFactory = configuration.getInvocationInputFactory();
                GraphQlQueryInvoker queryInvoker = configuration.getQueryInvoker();
                String contentType = request.getContentType();

                if (APPLICATION_GRAPHQL.equals(contentType) || APPLICATION_GRAPHQL_UTF8.equals(contentType)) {
                    String query = CharStreams.toString(request.getReader());

                    query(queryInvoker, invocationInputFactory.create(new GraphQlRequest(query, null, null)), request, response);
                } else if (APPLICATION_JSON.equals(contentType) || APPLICATION_JSON_UTF8.equals(contentType)) {
                    String json = CharStreams.toString(request.getReader());

                    query(queryInvoker, invocationInputFactory.create(new GraphQlRequest(json)), request, response);
                }
//                else if (request.getContentType() != null && request.getContentType().startsWith("multipart/form-data") && !request.getParts().isEmpty()) {
//                    final Map<String, List<Part>> fileItems = request.getParts()
//                            .stream()
//                            .collect(Collectors.groupingBy(Part::getName));
//
//                    for (String key : MULTIPART_KEYS) {
//                        // Check to see if there is a part under the key we seek
//                        if (!fileItems.containsKey(key)) {
//                            continue;
//                        }
//
//                        final Optional<Part> queryItem = getFileItem(fileItems, key);
//                        if (!queryItem.isPresent()) {
//                            // If there is a part, but we don't see an item, then break and return BAD_REQUEST
//                            break;
//                        }
//
//                        InputStream inputStream = asMarkableInputStream(queryItem.get().getInputStream());
//
//                        final Optional<Map<String, List<String>>> variablesMap =
//                                getFileItem(fileItems, "map").map(graphQLObjectMapper::deserializeMultipartMap);
//
//                        if (isBatchedQuery(inputStream)) {
//                            List<GraphQlRequest> graphQlRequests =
//                                    graphQLObjectMapper.readBatchedGraphQLRequest(inputStream);
//                            variablesMap.ifPresent(map -> graphQlRequests.forEach(r -> mapMultipartVariables(r, map, fileItems)));
//                            GraphQlBatchedInvocationInput invocationInput =
//                                    invocationInputFactory.create(graphQlRequests, request, response);
//                            invocationInput.getContext().setParts(fileItems);
//                            queryBatched(queryInvoker, graphQLObjectMapper, invocationInput, response);
//                            return;
//                        } else {
//                            GraphQlRequest graphQLRequest;
//                            if ("query".equals(key)) {
//                                graphQLRequest = buildRequestFromQuery(inputStream, graphQLObjectMapper, fileItems);
//                            } else {
//                                graphQLRequest = graphQLObjectMapper.readGraphQLRequest(inputStream);
//                            }
//
//                            variablesMap.ifPresent(m -> mapMultipartVariables(graphQLRequest, m, fileItems));
//                            GraphQlSingleInvocationInput invocationInput =
//                                    invocationInputFactory.create(graphQLRequest, request, response);
//                            invocationInput.getContext().setParts(fileItems);
//                            query(queryInvoker, graphQLObjectMapper, invocationInput, response);
//                            return;
//                        }
//                    }
//
//                    response.setStatus(STATUS_BAD_REQUEST);
//                    log.info("Bad POST multipart request: no part named " + Arrays.toString(MULTIPART_KEYS));
//                } else {
//                    // this is not a multipart request
//                    String query = request.getParameter("query");
//                    String variables = request.getParameter("variables");
//                    String operationName = request.getParameter("operationName");
//                    InputStream inputStream = asMarkableInputStream(request.getInputStream());
//
//                    if (isBatchedQuery(inputStream)) {
//                        //queryBatched(queryInvoker, invocationInputFactory.create(graphQLObjectMapper.readBatchedGraphQLRequest(inputStream), request, response), response);
//                    } else {
//                        query(queryInvoker, invocationInputFactory.create(new GraphQlRequest(query, variables, operationName)), request, response);
//                    }
//                }
            } catch (Exception e) {
                log.info("Bad POST request: parsing failed", e);
                response.setStatus(STATUS_BAD_REQUEST);
            }
        };
    }

    private static InputStream asMarkableInputStream(InputStream inputStream) {
        if (!inputStream.markSupported()) {
            inputStream = new BufferedInputStream(inputStream);
        }

        return inputStream;
    }

    private boolean isBatchedQuery(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return false;
        }

        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[128];
        int length;

        inputStream.mark(0);
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
            String chunk = result.toString();
            Boolean isArrayStart = isArrayStart(chunk);
            if (isArrayStart != null) {
                inputStream.reset();
                return isArrayStart;
            }
        }

        inputStream.reset();
        return false;
    }

    private boolean isBatchedQuery(String query) {
        if (query == null) {
            return false;
        }

        Boolean isArrayStart = isArrayStart(query);
        return isArrayStart != null && isArrayStart;
    }


    // return true if the first non whitespace character is the beginning of an array
    private Boolean isArrayStart(String s) {
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (!Character.isWhitespace(ch)) {
                return ch == '[';
            }
        }

        return null;
    }

    private void doRequestAsync(HttpServletRequest request, HttpServletResponse response, HttpRequestHandler handler) {
        AsyncContext asyncContext = request.startAsync();
        HttpServletRequest asyncRequest = (HttpServletRequest) asyncContext.getRequest();
        HttpServletResponse asyncResponse = (HttpServletResponse) asyncContext.getResponse();

        HttpSessionUtils.getInstance().setupUserVisit(request, response);

        new Thread(() -> doRequest(asyncRequest, asyncResponse, handler, asyncContext)).start();
    }

    private void doRequest(HttpServletRequest request, HttpServletResponse response, HttpRequestHandler handler, AsyncContext asyncContext) {
        try {
            handler.handle(request, response);
        } catch (Throwable t) {
            response.setStatus(500);
            log.error("Error executing GraphQL request!", t);
        } finally {
            asyncContext.complete();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doRequestAsync(request, response, getHandler);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doRequestAsync(request, response, postHandler);
    }

    private Optional<Part> getFileItem(Map<String, List<Part>> fileItems, String name) {
        return Optional.ofNullable(fileItems.get(name)).filter(list -> !list.isEmpty()).map(list -> list.get(0));
    }

    public UserVisitPK getUserVisitPK(HttpServletRequest request) {
        HttpSession httpSession = request.getSession(true);

        return (UserVisitPK)httpSession.getAttribute(WebConstants.Session_USER_VISIT);
    }

    private void query(GraphQlQueryInvoker queryInvoker, GraphQlSingleInvocationInput invocationInput,
            HttpServletRequest request, HttpServletResponse resp)
            throws IOException, NamingException {
        String result = queryInvoker.query(getUserVisitPK(request), invocationInput);

        resp.setContentType(APPLICATION_JSON_UTF8);
        resp.setStatus(STATUS_OK);
        resp.getOutputStream().write(result.getBytes(Charsets.UTF_8));
    }

    private void queryBatched(GraphQlQueryInvoker queryInvoker, GraphQlBatchedInvocationInput invocationInput,
          HttpServletRequest request, HttpServletResponse resp)
          throws Exception {
        OutputStream stream = resp.getOutputStream();

        resp.setContentType(APPLICATION_JSON_UTF8);
        resp.setStatus(STATUS_OK);

        stream.write('[');

        Iterator<String> graphQlExecutionResults = queryInvoker.query(getUserVisitPK(request), invocationInput).iterator();
        while(graphQlExecutionResults.hasNext()) {
            resp.getOutputStream().write(graphQlExecutionResults.next().getBytes(Charsets.UTF_8));

            if(graphQlExecutionResults.hasNext()) {
                stream.write(',');
            }
        }

        stream.write(']');
    }
}
