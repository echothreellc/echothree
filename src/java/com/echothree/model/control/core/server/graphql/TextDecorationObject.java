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

import com.echothree.model.control.core.server.control.TextControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.core.server.entity.TextDecoration;
import com.echothree.model.data.core.server.entity.TextDecorationDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("text decoration object")
@GraphQLName("TextDecoration")
public class TextDecorationObject
        extends BaseEntityInstanceObject {
    
    private final TextDecoration textDecoration; // Always Present
    
    public TextDecorationObject(TextDecoration textDecoration) {
        super(textDecoration.getPrimaryKey());
        
        this.textDecoration = textDecoration;
    }

    private TextDecorationDetail textDecorationDetail; // Optional, use getTextDecorationDetail()
    
    private TextDecorationDetail getTextDecorationDetail() {
        if(textDecorationDetail == null) {
            textDecorationDetail = textDecoration.getLastDetail();
        }
        
        return textDecorationDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("text decoration name")
    @GraphQLNonNull
    public String getTextDecorationName() {
        return getTextDecorationDetail().getTextDecorationName();
    }
    
    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getTextDecorationDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getTextDecorationDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var textControl = Session.getModelController(TextControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return textControl.getBestTextDecorationDescription(textDecoration, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }
    
}
