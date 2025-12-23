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

package com.echothree.control.user.graphql.server.schema.util;

import com.echothree.control.user.graphql.server.schema.GraphQlMutations;
import com.echothree.control.user.graphql.server.schema.GraphQlQueries;
import com.echothree.model.control.graphql.server.graphql.count.RelayWithCounting;
import graphql.annotations.AnnotationsSchemaCreator;
import graphql.annotations.processor.GraphQLAnnotations;
import graphql.schema.GraphQLSchema;

public class GraphQlSchemaUtils {
    
    private GraphQLSchema readOnlySchema;
    private GraphQLSchema schema;

    private GraphQlSchemaUtils() {
        buildSchema();
    }
    
    private static class GraphQlSchemaUtilsHolder {
        static GraphQlSchemaUtils instance = new GraphQlSchemaUtils();
    }
    
    public static GraphQlSchemaUtils getInstance() {
        return GraphQlSchemaUtilsHolder.instance;
    }
    
    private void buildSchema() {
        readOnlySchema = AnnotationsSchemaCreator.newAnnotationsSchema()
                .setAnnotationsProcessor(new GraphQLAnnotations())
                .setRelay(new RelayWithCounting())
                .query(GraphQlQueries.class)
                .setAlwaysPrettify(true)
                .build();

        schema = AnnotationsSchemaCreator.newAnnotationsSchema()
                .setAnnotationsProcessor(new GraphQLAnnotations())
                .setRelay(new RelayWithCounting())
                .query(GraphQlQueries.class)
                .mutation(GraphQlMutations.class)
                .setAlwaysPrettify(true)
                .build();
    }

    public GraphQLSchema getReadOnlySchema() {
        return readOnlySchema;
    }

    public GraphQLSchema getSchema() {
        return schema;
    }

}
