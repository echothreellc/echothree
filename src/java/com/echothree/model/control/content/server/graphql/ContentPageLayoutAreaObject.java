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

package com.echothree.model.control.content.server.graphql;

import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.content.server.entity.ContentPageLayoutArea;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("content page layout area")
@GraphQLName("ContentPageLayoutArea")
public class ContentPageLayoutAreaObject
        extends BaseEntityInstanceObject {
    
    private final ContentPageLayoutArea contentPageLayoutArea; // Always Present
    
    public ContentPageLayoutAreaObject(ContentPageLayoutArea contentPageLayoutArea) {
        super(contentPageLayoutArea.getPrimaryKey());
        
        this.contentPageLayoutArea = contentPageLayoutArea;
    }

    @GraphQLField
    @GraphQLDescription("content page layout")
    @GraphQLNonNull
    public ContentPageLayoutObject getContentPageLayout(final DataFetchingEnvironment env) {
        return ContentSecurityUtils.getHasContentPageLayoutAccess(env) ? new ContentPageLayoutObject(contentPageLayoutArea.getContentPageLayout()) : null;
    }

    @GraphQLField
    @GraphQLDescription("content page area type")
    @GraphQLNonNull
    public ContentPageAreaTypeObject getContentPageAreaType(final DataFetchingEnvironment env) {
        return new ContentPageAreaTypeObject(contentPageLayoutArea.getContentPageAreaType());
    }

    @GraphQLField
    @GraphQLDescription("show description field")
    @GraphQLNonNull
    public boolean getShowDescriptionField() {
        return contentPageLayoutArea.getShowDescriptionField();
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return contentPageLayoutArea.getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var contentControl = Session.getModelController(ContentControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return contentControl.getBestContentPageLayoutAreaDescription(contentPageLayoutArea, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }
    
}
