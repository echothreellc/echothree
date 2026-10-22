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
import graphql.annotations.annotationTypes.GraphQLUnion;
import graphql.annotations.processor.GraphQLAnnotations;
import graphql.schema.GraphQLSchema;
import graphql.schema.GraphQLUnionType;
import io.github.classgraph.ClassGraph;
import java.util.List;

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
    
    private GraphQLAnnotations createAnnotationsProcessor(List<Class<?>> unionTypes) {
        var annotations = new GraphQLAnnotations();

        annotations.setRelay(new RelayWithCounting());
        annotations.getObjectHandler().getTypeRetriever().getGraphQLFieldRetriever().setAlwaysPrettify(true);

        // Build unions before query roots so recursive member fields resolve to type references.
        // The annotations library's UnionBuilder requires concrete possible types when the union completes.
        for(var unionType : unionTypes) {
            annotations.getObjectHandler().getGraphQLType(unionType, annotations.getContainer());
        }

        return annotations;
    }

    private GraphQLSchema buildSchema(boolean readOnly, List<Class<?>> unionTypes) {
        var annotations = createAnnotationsProcessor(unionTypes);
        var schemaBuilder = GraphQLSchema.newSchema();

        annotations.getTypeRegistry().values().stream()
                .filter(GraphQLUnionType.class::isInstance)
                .forEach(schemaBuilder::additionalType);

        var builder = AnnotationsSchemaCreator.newAnnotationsSchema()
                .setAnnotationsProcessor(annotations)
                .setGraphQLSchemaBuilder(schemaBuilder)
                .setRelay(new RelayWithCounting())
                .query(GraphQlQueries.class)
                .setAlwaysPrettify(true);

        if(!readOnly) {
            builder.mutation(GraphQlMutations.class);
        }

        return builder.build();
    }

    private void buildSchema() {
        try(var scan = new ClassGraph()
                .acceptPackages("com.echothree.model.control")
                .enableAnnotationInfo()
                .scan()) {
            var unionTypes = scan.getClassesWithAnnotation(GraphQLUnion.class.getName()).loadClasses();

            readOnlySchema = buildSchema(true, unionTypes);
            schema = buildSchema(false, unionTypes);
        }
    }

    public GraphQLSchema getReadOnlySchema() {
        return readOnlySchema;
    }

    public GraphQLSchema getSchema() {
        return schema;
    }

}
