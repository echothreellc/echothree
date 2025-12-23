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

package com.echothree.service.graphql.internal.invocation;

import com.echothree.service.graphql.internal.GraphQlRequest;

public class GraphQlInvocationInputFactory {

    protected GraphQlInvocationInputFactory() {
    }

    public GraphQlInvocationInput create(GraphQlRequest graphQlRequest) {
        return create(graphQlRequest, false);
    }

    public GraphQlInvocationInput createReadOnly(GraphQlRequest graphQlRequest) {
        return create(graphQlRequest, true);
    }

    private GraphQlInvocationInput create(GraphQlRequest graphQlRequest, boolean readOnly) {
        return new GraphQlInvocationInput(readOnly, graphQlRequest);
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
