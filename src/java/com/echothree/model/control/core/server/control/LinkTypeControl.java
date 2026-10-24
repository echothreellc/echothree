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

package com.echothree.model.control.core.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.common.choice.LinkTypeChoicesBean;
import com.echothree.model.control.core.common.transfer.LinkTypeTransfer;
import com.echothree.model.control.core.common.transfer.LinkTypeTranslationTransfer;
import com.echothree.model.control.core.server.transfer.LinkTypeTransferCache;
import com.echothree.model.control.core.server.transfer.LinkTypeTranslationTransferCache;
import com.echothree.model.data.core.common.pk.LinkTypePK;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.core.server.entity.LinkCardinality;
import com.echothree.model.data.core.server.entity.LinkDirectionality;
import com.echothree.model.data.core.server.entity.LinkType;
import com.echothree.model.data.core.server.entity.LinkTypeTranslation;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.core.server.factory.LinkTypeDetailFactory;
import com.echothree.model.data.core.server.factory.LinkTypeFactory;
import com.echothree.model.data.core.server.factory.LinkTypeTranslationFactory;
import com.echothree.model.data.core.server.value.LinkTypeDetailValue;
import com.echothree.model.data.core.server.value.LinkTypeTranslationValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import org.jooq.Condition;
import org.jooq.impl.DSL;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.LINK_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.tables.core.LinkTypeDetails.LinkTypeDetails;
import static com.echothree.model.jooq.server.tables.core.LinkTypeTranslations.LinkTypeTranslations;
import static com.echothree.model.jooq.server.tables.core.LinkTypes.LinkTypes;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;

@CommandScope
public class LinkTypeControl
        extends BaseModelControl {

    @Inject
    LinkTypeTransferCache linkTypeTransferCache;

    @Inject
    LinkTypeTranslationTransferCache linkTypeTranslationTransferCache;

    /**
     * Creates a new instance of LinkTypeControl
     */
    protected LinkTypeControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Link Types
    // --------------------------------------------------------------------------------

    @Inject
    protected LinkTypeDetailFactory linkTypeDetailFactory;

    @Inject
    protected LinkTypeFactory linkTypeFactory;

    public LinkType createLinkType(String linkTypeName, LinkType parentLinkType, EntityType sourceEntityType,
            EntityType targetEntityType, LinkCardinality linkCardinality, LinkDirectionality linkDirectionality,
            Boolean isTransitive, LinkType inverseLinkType, Integer sortOrder, BasePK createdBy) {
        var linkType = linkTypeFactory.create();
        var linkTypeDetail = linkTypeDetailFactory.create(linkType, linkTypeName, parentLinkType, sourceEntityType,
                targetEntityType, linkCardinality, linkDirectionality, isTransitive, inverseLinkType, sortOrder,
                session.getStartTime(), Session.MAX_TIME);

        linkType = linkTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE, linkType.getPrimaryKey());
        linkType.setActiveDetail(linkTypeDetail);
        linkType.setLastDetail(linkTypeDetail);
        linkType.store();
        sendEvent(linkType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return linkType;
    }

    /**
     * Assume that the entityInstance passed to this function is a ECHO_THREE.LinkType
     */
    public LinkType getLinkTypeByEntityInstance(final EntityInstance entityInstance,
            final EntityPermission entityPermission) {
        var pk = new LinkTypePK(entityInstance.getEntityUniqueId());

        return linkTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public LinkType getLinkTypeByEntityInstance(final EntityInstance entityInstance) {
        return getLinkTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public LinkType getLinkTypeByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getLinkTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public LinkType getLinkTypeByPK(LinkTypePK pk) {
        return linkTypeFactory.getEntityFromPK(EntityPermission.READ_ONLY, pk);
    }

    public Condition getLinkTypeFilter(LinkType parentLinkType, EntityType sourceEntityType, EntityType targetEntityType,
            LinkCardinality linkCardinality, LinkDirectionality linkDirectionality, LinkType inverseLinkType) {
        Condition condition = DSL.trueCondition();

        if(parentLinkType != null) {
            condition = condition.and(LinkTypeDetails.PARENT_LINK_TYPE.eq(parentLinkType.getPrimaryKey()));
        }

        if(sourceEntityType != null) {
            condition = condition.and(LinkTypeDetails.SOURCE_ENTITY_TYPE.eq(sourceEntityType.getPrimaryKey()));
        }

        if(targetEntityType != null) {
            condition = condition.and(LinkTypeDetails.TARGET_ENTITY_TYPE.eq(targetEntityType.getPrimaryKey()));
        }

        if(linkCardinality != null) {
            condition = condition.and(LinkTypeDetails.LINK_CARDINALITY.eq(linkCardinality.getPrimaryKey()));
        }

        if(linkDirectionality != null) {
            condition = condition.and(LinkTypeDetails.LINK_DIRECTIONALITY.eq(linkDirectionality.getPrimaryKey()));
        }

        if(inverseLinkType != null) {
            condition = condition.and(LinkTypeDetails.INVERSE_LINK_TYPE.eq(inverseLinkType.getPrimaryKey()));
        }

        return condition;
    }

    public long countLinkTypes(Condition condition) {
        return session.getDslContext()
                .selectCount()
                .from(LinkTypes)
                .join(LinkTypeDetails).onKey(LINK_TYPES_LAST_DETAIL_FK)
                .where(condition)
                .and(LinkTypes.ACTIVE_DETAIL.isNotNull())
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countLinkTypes() {
        return countLinkTypes(DSL.trueCondition());
    }

    public LinkType getLinkTypeByName(final String linkTypeName,
            final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(LinkTypes.fields())
                .from(LinkTypes)
                .join(LinkTypeDetails).onKey(LINK_TYPES_LAST_DETAIL_FK)
                .where(LinkTypeDetails.LINK_TYPE_NAME.eq(linkTypeName))
                .and(LinkTypes.ACTIVE_DETAIL.isNotNull());

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return linkTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public LinkType getLinkTypeByName(String linkTypeName) {
        return getLinkTypeByName(linkTypeName, EntityPermission.READ_ONLY);
    }

    public LinkType getLinkTypeByNameForUpdate(String linkTypeName) {
        return getLinkTypeByName(linkTypeName, EntityPermission.READ_WRITE);
    }

    public LinkTypeDetailValue getLinkTypeDetailValueForUpdate(LinkType linkType) {
        return linkType == null ? null : linkType.getLastDetailForUpdate().getLinkTypeDetailValue().clone();
    }

    public LinkTypeDetailValue getLinkTypeDetailValueByNameForUpdate(String linkTypeName) {
        return getLinkTypeDetailValueForUpdate(getLinkTypeByNameForUpdate(linkTypeName));
    }

    private List<LinkType> getLinkTypes(final EntityPermission entityPermission, Condition condition) {
        var baseQuery = session.getDslContext()
                .select(LinkTypes.fields())
                .from(LinkTypes)
                .join(LinkTypeDetails).onKey(LINK_TYPES_LAST_DETAIL_FK)
                .where(condition)
                .and(LinkTypes.ACTIVE_DETAIL.isNotNull());

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(LinkTypeDetails.SORT_ORDER, LinkTypeDetails.LINK_TYPE_NAME),
                    LinkTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return linkTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<LinkType> getLinkTypes() {
        return getLinkTypes(EntityPermission.READ_ONLY, DSL.trueCondition());
    }

    public List<LinkType> getLinkTypesForUpdate() {
        return getLinkTypes(EntityPermission.READ_WRITE, DSL.trueCondition());
    }

    public List<LinkType> getLinkTypes(Condition condition) {
        return getLinkTypes(EntityPermission.READ_ONLY, condition);
    }

    public List<LinkType> getLinkTypesForUpdate(Condition condition) {
        return getLinkTypes(EntityPermission.READ_WRITE, condition);
    }

    public LinkTypeTransfer getLinkTypeTransfer(UserVisit userVisit, LinkType linkType) {
        return linkTypeTransferCache.getTransfer(userVisit, linkType);
    }

    public List<LinkTypeTransfer> getLinkTypeTransfers(UserVisit userVisit, Collection<LinkType> linkTypes) {
        List<LinkTypeTransfer> linkTypeTransfers = new ArrayList<>(linkTypes.size());

        linkTypes.forEach((linkType) ->
                linkTypeTransfers.add(linkTypeTransferCache.getTransfer(userVisit, linkType))
        );

        return linkTypeTransfers;
    }

    public List<LinkTypeTransfer> getLinkTypeTransfers(UserVisit userVisit) {
        return getLinkTypeTransfers(userVisit, getLinkTypes());
    }

    public LinkTypeChoicesBean getLinkTypeChoices(String defaultLinkTypeChoice,
            Language language, boolean allowNullChoice) {
        var linkTypes = getLinkTypes();
        var size = linkTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultLinkTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var linkType : linkTypes) {
            var linkTypeDetail = linkType.getLastDetail();

            var label = getBestLinkTypeDescription(linkType, language);
            var value = linkTypeDetail.getLinkTypeName();

            labels.add(label == null ? value : label);
            values.add(value);

            var usingDefaultChoice = defaultLinkTypeChoice != null && defaultLinkTypeChoice.equals(value);
            if(usingDefaultChoice) {
                defaultValue = value;
            }
        }

        return new LinkTypeChoicesBean(labels, values, defaultValue);
    }

    public void updateLinkTypeFromValue(LinkTypeDetailValue linkTypeDetailValue, BasePK updatedBy) {
        if(linkTypeDetailValue.hasBeenModified()) {
            var linkType = linkTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE, linkTypeDetailValue.getLinkTypePK());
            var linkTypeDetail = linkType.getActiveDetailForUpdate();

            linkTypeDetail.setThruTime(session.getStartTime());
            linkTypeDetail.store();
            linkTypeDetail = linkTypeDetailFactory.create(linkTypeDetailValue.getLinkTypePK(), linkTypeDetailValue.getLinkTypeName(),
                    linkTypeDetailValue.getParentLinkTypePK(), linkTypeDetailValue.getSourceEntityTypePK(),
                    linkTypeDetailValue.getTargetEntityTypePK(), linkTypeDetailValue.getLinkCardinalityPK(),
                    linkTypeDetailValue.getLinkDirectionalityPK(), linkTypeDetailValue.getIsTransitive(),
                    linkTypeDetailValue.getInverseLinkTypePK(), linkTypeDetailValue.getSortOrder(), session.getStartTime(), Session.MAX_TIME);

            linkType.setActiveDetail(linkTypeDetail);
            linkType.setLastDetail(linkTypeDetail);
            linkType.store();
            sendEvent(linkType.getPrimaryKey(), EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void deleteLinkType(LinkType linkType, BasePK deletedBy) {
        deleteLinkTypeTranslationsByLinkType(linkType, deletedBy);

        var linkTypeDetail = linkType.getLastDetailForUpdate();
        linkTypeDetail.setThruTime(session.getStartTime());
        linkType.setActiveDetail(null);
        linkType.store();

        sendEvent(linkType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Link Type Translations
    // --------------------------------------------------------------------------------

    @Inject
    protected LinkTypeTranslationFactory linkTypeTranslationFactory;

    public LinkTypeTranslation createLinkTypeTranslation(LinkType linkType, Language language, String description, MimeType guidanceMimeType, String guidance, BasePK createdBy) {
        var linkTypeTranslation = linkTypeTranslationFactory.create(linkType, language, description, guidanceMimeType, guidance,
                session.getStartTime(), Session.MAX_TIME);

        sendEvent(linkType.getPrimaryKey(), EventTypes.MODIFY, linkTypeTranslation.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return linkTypeTranslation;
    }

    public LinkTypeTranslation getLinkTypeTranslation(final LinkType linkType,
            final Language language, final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(LinkTypeTranslations.fields())
                .from(LinkTypeTranslations)
                .where(LinkTypeTranslations.LINK_TYPE.eq(linkType.getPrimaryKey()),
                        LinkTypeTranslations.LANGUAGE.eq(language.getPrimaryKey()),
                        LinkTypeTranslations.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return linkTypeTranslationFactory.getEntityFromQuery(entityPermission, query);
    }

    public LinkTypeTranslation getLinkTypeTranslation(LinkType linkType, Language language) {
        return getLinkTypeTranslation(linkType, language, EntityPermission.READ_ONLY);
    }

    public LinkTypeTranslation getLinkTypeTranslationForUpdate(LinkType linkType, Language language) {
        return getLinkTypeTranslation(linkType, language, EntityPermission.READ_WRITE);
    }

    public LinkTypeTranslationValue getLinkTypeTranslationValue(LinkTypeTranslation linkTypeTranslation) {
        return linkTypeTranslation == null ? null : linkTypeTranslation.getLinkTypeTranslationValue().clone();
    }

    public LinkTypeTranslationValue getLinkTypeTranslationValueForUpdate(LinkType linkType, Language language) {
        return getLinkTypeTranslationValue(getLinkTypeTranslationForUpdate(linkType, language));
    }

    private List<LinkTypeTranslation> getLinkTypeTranslationsByLinkType(
            final LinkType linkType, final EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(LinkTypeTranslations.fields())
                    .from(LinkTypeTranslations)
                    .join(Languages).on(LinkTypeTranslations.LANGUAGE.eq(Languages.LANGUAGE))
                    .where(LinkTypeTranslations.LINK_TYPE.eq(linkType.getPrimaryKey()),
                            LinkTypeTranslations.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    LinkTypeTranslationFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(LinkTypeTranslations.fields())
                    .from(LinkTypeTranslations)
                    .where(LinkTypeTranslations.LINK_TYPE.eq(linkType.getPrimaryKey()),
                            LinkTypeTranslations.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return linkTypeTranslationFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<LinkTypeTranslation> getLinkTypeTranslationsByLinkType(LinkType linkType) {
        return getLinkTypeTranslationsByLinkType(linkType, EntityPermission.READ_ONLY);
    }

    public List<LinkTypeTranslation> getLinkTypeTranslationsByLinkTypeForUpdate(LinkType linkType) {
        return getLinkTypeTranslationsByLinkType(linkType, EntityPermission.READ_WRITE);
    }

    public long countLinkTypeTranslationsByLinkType(LinkType linkType) {
        return session.getDslContext()
                .selectCount()
                .from(LinkTypeTranslations)
                .where(LinkTypeTranslations.LINK_TYPE.eq(linkType.getPrimaryKey()), LinkTypeTranslations.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public LinkTypeTranslation getBestLinkTypeTranslation(LinkType linkType, Language language) {
        var linkTypeTranslation = getLinkTypeTranslation(linkType, language);

        if(linkTypeTranslation == null && !language.getIsDefault()) {
            linkTypeTranslation = getLinkTypeTranslation(linkType, partyControl.getDefaultLanguage());
        }

        return linkTypeTranslation;
    }

    public String getBestLinkTypeDescription(LinkType linkType, Language language) {
        var linkTypeTranslation = getBestLinkTypeTranslation(linkType, language);

        return linkTypeTranslation == null ? linkType.getLastDetail().getLinkTypeName() : linkTypeTranslation.getDescription();
    }

    public LinkTypeTranslationTransfer getLinkTypeTranslationTransfer(UserVisit userVisit, LinkTypeTranslation linkTypeTranslation) {
        return linkTypeTranslationTransferCache.getTransfer(userVisit, linkTypeTranslation);
    }

    public List<LinkTypeTranslationTransfer> getLinkTypeTranslationTransfers(UserVisit userVisit,
            Collection<LinkTypeTranslation> linkTypeTranslations) {
        List<LinkTypeTranslationTransfer> linkTypeTranslationTransfers = new ArrayList<>(linkTypeTranslations.size());

        linkTypeTranslations.forEach((linkTypeTranslation) ->
                linkTypeTranslationTransfers.add(linkTypeTranslationTransferCache.getTransfer(userVisit, linkTypeTranslation))
        );

        return linkTypeTranslationTransfers;
    }

    public List<LinkTypeTranslationTransfer> getLinkTypeTranslationTransfersByLinkType(UserVisit userVisit, LinkType linkType) {
        return getLinkTypeTranslationTransfers(userVisit, getLinkTypeTranslationsByLinkType(linkType));
    }

    public void updateLinkTypeTranslationFromValue(LinkTypeTranslationValue linkTypeTranslationValue, BasePK updatedBy) {
        if(linkTypeTranslationValue.hasBeenModified()) {
            var linkTypeTranslation = linkTypeTranslationFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    linkTypeTranslationValue.getPrimaryKey());

            linkTypeTranslation.setThruTime(session.getStartTime());
            linkTypeTranslation.store();

            var linkType = linkTypeTranslation.getLinkType();
            var language = linkTypeTranslation.getLanguage();
            var description = linkTypeTranslationValue.getDescription();

            linkTypeTranslation = linkTypeTranslationFactory.create(linkType.getPrimaryKey(), language.getPrimaryKey(), description,
                    linkTypeTranslationValue.getGuidanceMimeTypePK(), linkTypeTranslationValue.getGuidance(), session.getStartTime(), Session.MAX_TIME);

            sendEvent(linkType.getPrimaryKey(), EventTypes.MODIFY, linkTypeTranslation.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteLinkTypeTranslation(LinkTypeTranslation linkTypeTranslation, BasePK deletedBy) {
        linkTypeTranslation.setThruTime(session.getStartTime());

        sendEvent(linkTypeTranslation.getLinkTypePK(), EventTypes.MODIFY, linkTypeTranslation.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteLinkTypeTranslationsByLinkType(LinkType linkType, BasePK deletedBy) {
        var linkTypeTranslations = getLinkTypeTranslationsByLinkTypeForUpdate(linkType);

        linkTypeTranslations.forEach((linkTypeTranslation) ->
                deleteLinkTypeTranslation(linkTypeTranslation, deletedBy)
        );
    }

}
