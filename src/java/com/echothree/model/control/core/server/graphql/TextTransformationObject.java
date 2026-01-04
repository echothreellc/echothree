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

package com.echothree.model.control.core.server.graphql;

import com.echothree.model.control.core.server.control.TextControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.core.server.entity.TextTransformation;
import com.echothree.model.data.core.server.entity.TextTransformationDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("text transformation object")
@GraphQLName("TextTransformation")
public class TextTransformationObject
        extends BaseEntityInstanceObject {
    
    private final TextTransformation textTransformation; // Always Present
    
    public TextTransformationObject(TextTransformation textTransformation) {
        super(textTransformation.getPrimaryKey());
        
        this.textTransformation = textTransformation;
    }

    private TextTransformationDetail textTransformationDetail; // Optional, use getTextTransformationDetail()
    
    private TextTransformationDetail getTextTransformationDetail() {
        if(textTransformationDetail == null) {
            textTransformationDetail = textTransformation.getLastDetail();
        }
        
        return textTransformationDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("text transformation name")
    @GraphQLNonNull
    public String getTextTransformationName() {
        return getTextTransformationDetail().getTextTransformationName();
    }
    
    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getTextTransformationDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getTextTransformationDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var textControl = Session.getModelController(TextControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return textControl.getBestTextTransformationDescription(textTransformation, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }
    
}
