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

import com.echothree.model.control.core.server.control.LinkCardinalityControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.core.server.entity.LinkCardinality;
import com.echothree.model.data.core.server.entity.LinkCardinalityDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("link cardinality object")
@GraphQLName("LinkCardinality")
public class LinkCardinalityObject
        extends BaseEntityInstanceObject {

    private final LinkCardinality linkCardinality; // Always Present

    public LinkCardinalityObject(final LinkCardinality linkCardinality) {
        super(linkCardinality.getPrimaryKey());

        this.linkCardinality = linkCardinality;
    }

    private LinkCardinalityDetail linkCardinalityDetail; // Optional, use getLinkCardinalityDetail()

    private LinkCardinalityDetail getLinkCardinalityDetail() {
        if(linkCardinalityDetail == null) {
            linkCardinalityDetail = linkCardinality.getLastDetail();
        }

        return linkCardinalityDetail;
    }

    @GraphQLField
    @GraphQLDescription("link cardinality name")
    @GraphQLNonNull
    public String getLinkCardinalityName() {
        return getLinkCardinalityDetail().getLinkCardinalityName();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getLinkCardinalityDetail().getIsDefault();
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getLinkCardinalityDetail().getSortOrder();
    }

    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var linkCardinalityControl = Session.getModelController(LinkCardinalityControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return linkCardinalityControl.getBestLinkCardinalityDescription(linkCardinality, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

}
