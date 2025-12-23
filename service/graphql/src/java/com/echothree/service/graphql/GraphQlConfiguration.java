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

import com.echothree.service.graphql.internal.invoker.GraphQlQueryInvoker;
import com.echothree.service.graphql.internal.invocation.GraphQlInvocationInputFactory;

public class GraphQlConfiguration {

    private GraphQlInvocationInputFactory invocationInputFactory;
    private GraphQlQueryInvoker queryInvoker;

    public static GraphQlConfiguration.Builder with(GraphQlInvocationInputFactory invocationInputFactory) {
        return new Builder(invocationInputFactory);
    }

//    public static GraphQlConfiguration build(GraphQlConfiguration.Builder builder) {
//        return builder.build();
//    }

    private GraphQlConfiguration(GraphQlInvocationInputFactory invocationInputFactory, GraphQlQueryInvoker queryInvoker) {
        this.invocationInputFactory = invocationInputFactory;
        this.queryInvoker = queryInvoker;
    }

    public GraphQlInvocationInputFactory getInvocationInputFactory() {
        return invocationInputFactory;
    }

    public GraphQlQueryInvoker getQueryInvoker() {
        return queryInvoker;
    }

    public static class Builder {

        private GraphQlInvocationInputFactory.Builder invocationInputFactoryBuilder;
        private GraphQlInvocationInputFactory invocationInputFactory;
        private GraphQlQueryInvoker queryInvoker = GraphQlQueryInvoker.newBuilder().build();

//        private Builder(GraphQlInvocationInputFactory.Builder invocationInputFactoryBuilder) {
//            this.invocationInputFactoryBuilder = invocationInputFactoryBuilder;
//        }

        private Builder(GraphQlInvocationInputFactory invocationInputFactory) {
            this.invocationInputFactory = invocationInputFactory;
        }

//        public Builder with(GraphQlQueryInvoker queryInvoker) {
//            this.queryInvoker = queryInvoker;
//            return this;
//        }

        public GraphQlConfiguration build() {
            return new GraphQlConfiguration(
                    this.invocationInputFactory != null ? this.invocationInputFactory : invocationInputFactoryBuilder.build(),
                    queryInvoker
            );
        }
    }
}
