// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.model.control.core.server.graphql;

import com.echothree.model.data.core.server.entity.AppearanceTextTransformation;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;

@GraphQLDescription("appearance text transformation object")
@GraphQLName("AppearanceTextTransformation")
public class AppearanceTextTransformationObject {
    
    private final AppearanceTextTransformation appearanceTextTransformation; // Always Present
    
    public AppearanceTextTransformationObject(AppearanceTextTransformation appearanceTextTransformation) {
        this.appearanceTextTransformation = appearanceTextTransformation;
    }

    @GraphQLField
    @GraphQLDescription("appearance")
    @GraphQLNonNull
    public AppearanceObject getAppearance() {
        return new AppearanceObject(appearanceTextTransformation.getAppearance());
    }
    
    @GraphQLField
    @GraphQLDescription("text transformation")
    @GraphQLNonNull
    public TextTransformationObject getTextTransformation() {
        return new TextTransformationObject(appearanceTextTransformation.getTextTransformation());
    }
        
}
