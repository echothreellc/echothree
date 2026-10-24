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

import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.core.server.control.LinkTypeControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import graphql.annotations.connection.GraphQLConnection;
import com.echothree.model.data.core.common.LinkTypeTranslationConstants;
import com.echothree.model.data.core.server.entity.LinkType;
import com.echothree.model.data.core.server.entity.LinkTypeDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("link type object")
@GraphQLName("LinkType")
@SuppressWarnings("StringConcatToTextBlock")
public class LinkTypeObject
        extends BaseEntityInstanceObject {

    private final LinkType linkType; // Always Present

    public LinkTypeObject(final LinkType linkType) {
        super(linkType.getPrimaryKey());

        this.linkType = linkType;
    }

    private LinkTypeDetail linkTypeDetail; // Optional, use getLinkTypeDetail()

    private LinkTypeDetail getLinkTypeDetail() {
        if(linkTypeDetail == null) {
            linkTypeDetail = linkType.getLastDetail();
        }

        return linkTypeDetail;
    }

    @GraphQLField
    @GraphQLNonNull
    public String getLinkTypeName() {
        return getLinkTypeDetail().getLinkTypeName();
    }

    @GraphQLField
    public LinkTypeObject getParentLinkType(final DataFetchingEnvironment env) {
        if(CoreSecurityUtils.getHasLinkTypeAccess(env)) {
            var parentLinkType = getLinkTypeDetail().getParentLinkType();

            return parentLinkType == null ? null : new LinkTypeObject(parentLinkType);
        } else {
            return null;
        }
    }

    @GraphQLField
    public EntityTypeObject getSourceEntityType(final DataFetchingEnvironment env) {
        return CoreSecurityUtils.getHasEntityTypeAccess(env) ? new EntityTypeObject(getLinkTypeDetail().getSourceEntityType()) : null;
    }

    @GraphQLField
    public EntityTypeObject getTargetEntityType(final DataFetchingEnvironment env) {
        return CoreSecurityUtils.getHasEntityTypeAccess(env) ? new EntityTypeObject(getLinkTypeDetail().getTargetEntityType()) : null;
    }

    @GraphQLField
    public LinkCardinalityObject getLinkCardinality(final DataFetchingEnvironment env) {
        return CoreSecurityUtils.getHasLinkCardinalityAccess(env) ? new LinkCardinalityObject(getLinkTypeDetail().getLinkCardinality()) : null;
    }

    @GraphQLField
    public LinkDirectionalityObject getLinkDirectionality(final DataFetchingEnvironment env) {
        return CoreSecurityUtils.getHasLinkDirectionalityAccess(env) ? new LinkDirectionalityObject(getLinkTypeDetail().getLinkDirectionality()) : null;
    }

    @GraphQLField
    @GraphQLNonNull
    public boolean getIsTransitive() {
        return getLinkTypeDetail().getIsTransitive();
    }

    @GraphQLField
    public LinkTypeObject getInverseLinkType(final DataFetchingEnvironment env) {
        if(CoreSecurityUtils.getHasLinkTypeAccess(env)) {
            var inverseLinkType = getLinkTypeDetail().getInverseLinkType();

            return inverseLinkType == null ? null : new LinkTypeObject(inverseLinkType);
        } else {
            return null;
        }
    }

    @GraphQLField
    @GraphQLNonNull
    public int getSortOrder() {
        return getLinkTypeDetail().getSortOrder();
    }

    @GraphQLField
    @GraphQLDescription("link type translation")
    public LinkTypeTranslationObject getLinkTypeTranslation(final DataFetchingEnvironment env) {
        if(CoreSecurityUtils.getHasLinkTypeTranslationAccess(env)) {
            var linkTypeControl = Session.getModelController(LinkTypeControl.class);
            var userControl = Session.getModelController(UserControl.class);
            var linkTypeTranslation = linkTypeControl.getBestLinkTypeTranslation(linkType,
                    userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));

            return linkTypeTranslation == null ? null : new LinkTypeTranslationObject(linkTypeTranslation);
        } else {
            return null;
        }
    }

    @GraphQLField
    @GraphQLDescription("link type translations")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<LinkTypeTranslationObject> getLinkTypeTranslations(final DataFetchingEnvironment env) {
        if(CoreSecurityUtils.getHasLinkTypeTranslationsAccess(env)) {
            var linkTypeControl = Session.getModelController(LinkTypeControl.class);
            var totalCount = linkTypeControl.countLinkTypeTranslationsByLinkType(linkType);

            try(var objectLimiter = new ObjectLimiter(env, LinkTypeTranslationConstants.COMPONENT_VENDOR_NAME,
                    LinkTypeTranslationConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = linkTypeControl.getLinkTypeTranslationsByLinkType(linkType);
                var linkTypeTranslations = entities.stream().map(LinkTypeTranslationObject::new).toList();

                return new CountedObjects<>(objectLimiter, linkTypeTranslations);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

}
