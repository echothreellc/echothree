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
import com.echothree.model.control.core.server.control.LinkTypeControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.core.common.LinkTypeConstants;
import com.echothree.model.data.core.server.entity.LinkDirectionality;
import com.echothree.model.data.core.server.entity.LinkDirectionalityDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.stream.Collectors;

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

    @GraphQLField
    @GraphQLDescription("link types")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<LinkTypeObject> getLinkTypes(final DataFetchingEnvironment env) {
        if(CoreSecurityUtils.getHasLinkTypesAccess(env)) {
            var linkTypeControl = Session.getModelController(LinkTypeControl.class);
            var condition = linkTypeControl.getLinkTypeFilter(null, null, null, null, linkDirectionality, null);
            var totalCount = linkTypeControl.countLinkTypes(condition);

            try(var objectLimiter = new ObjectLimiter(env, LinkTypeConstants.COMPONENT_VENDOR_NAME,
                    LinkTypeConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = linkTypeControl.getLinkTypes(condition);
                var linkTypes = entities.stream()
                        .map(LinkTypeObject::new)
                        .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, linkTypes);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

}
