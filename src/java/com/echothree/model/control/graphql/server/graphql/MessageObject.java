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

package com.echothree.model.control.graphql.server.graphql;

import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;

@GraphQLDescription("message object")
@GraphQLName("Message")
public class MessageObject {
    
    private final String property; // Always Present
    private final String key; // Always Present
    private final String message; // Always Present
    
    public MessageObject(String property, String key, String message) {
        this.property = property;
        this.key = key;
        this.message = message;
    }

    @GraphQLField
    @GraphQLDescription("property")
    @GraphQLNonNull
    public String property() {
        return property;
    }

    @GraphQLField
    @GraphQLDescription("key")
    @GraphQLNonNull
    public String key() {
        return key;
    }

    @GraphQLField
    @GraphQLDescription("message")
    @GraphQLNonNull
    public String message() {
        return message;
    }

}
