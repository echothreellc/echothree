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

package com.echothree.service.graphql.internal.invocation;

import com.echothree.service.graphql.internal.GraphQlRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;
import javax.websocket.server.HandshakeRequest;
import java.util.List;

public class GraphQlInvocationInputFactory {

    protected GraphQlInvocationInputFactory() {
    }

    public GraphQlSingleInvocationInput create(GraphQlRequest graphQlRequest, HttpServletRequest request,
                                               HttpServletResponse response) {
        return create(graphQlRequest, request, response, false);
    }

    public GraphQlBatchedInvocationInput create(List<GraphQlRequest> graphQlRequests, HttpServletRequest request,
                                                HttpServletResponse response) {
        return create(graphQlRequests, request, response, false);
    }

    public GraphQlSingleInvocationInput createReadOnly(GraphQlRequest graphQlRequest, HttpServletRequest request,
           HttpServletResponse response) {
        return create(graphQlRequest, request, response, true);
    }

    public GraphQlBatchedInvocationInput createReadOnly(List<GraphQlRequest> graphQlRequests, HttpServletRequest request,
            HttpServletResponse response) {
        return create(graphQlRequests, request, response, true);
    }

    public GraphQlSingleInvocationInput create(GraphQlRequest graphQlRequest) {
        return new GraphQlSingleInvocationInput(graphQlRequest);
    }

    private GraphQlSingleInvocationInput create(GraphQlRequest graphQlRequest, HttpServletRequest request,
            HttpServletResponse response, boolean readOnly) {
        return new GraphQlSingleInvocationInput(graphQlRequest);
    }

    private GraphQlBatchedInvocationInput create(List<GraphQlRequest> graphQlRequests, HttpServletRequest request,
             HttpServletResponse response, boolean readOnly) {
        return new GraphQlBatchedInvocationInput(graphQlRequests);
    }

    public GraphQlSingleInvocationInput create(GraphQlRequest graphQlRequest, Session session, HandshakeRequest request) {
        return new GraphQlSingleInvocationInput(graphQlRequest);
    }

    public GraphQlBatchedInvocationInput create(List<GraphQlRequest> graphQlRequest, Session session,
            HandshakeRequest request) {
        return new GraphQlBatchedInvocationInput(graphQlRequest);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {

        public GraphQlInvocationInputFactory build() {
            return new GraphQlInvocationInputFactory();
        }
    }
}
