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

import com.echothree.model.control.core.server.control.LinkDirectionalityControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.core.server.entity.LinkDirectionality;
import com.echothree.model.data.core.server.entity.LinkDirectionalityDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("link directionality object")
@GraphQLName("LinkDirectionality")
public class LinkDirectionalityObject
        extends BaseEntityInstanceObject {

    private final LinkDirectionality linkDirectionality; // Always Present

    public LinkDirectionalityObject(final LinkDirectionality linkDirectionality) {
        super(linkDirectionality.getPrimaryKey());

        this.linkDirectionality = linkDirectionality;
    }

    private LinkDirectionalityDetail linkDirectionalityDetail; // Optional, use getLinkDirectionalityDetail()

    private LinkDirectionalityDetail getLinkDirectionalityDetail() {
        if(linkDirectionalityDetail == null) {
            linkDirectionalityDetail = linkDirectionality.getLastDetail();
        }

        return linkDirectionalityDetail;
    }

    @GraphQLField
    @GraphQLDescription("link directionality name")
    @GraphQLNonNull
    public String getLinkDirectionalityName() {
        return getLinkDirectionalityDetail().getLinkDirectionalityName();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getLinkDirectionalityDetail().getIsDefault();
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getLinkDirectionalityDetail().getSortOrder();
    }

    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var linkDirectionalityControl = Session.getModelController(LinkDirectionalityControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return linkDirectionalityControl.getBestLinkDirectionalityDescription(linkDirectionality, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

}
