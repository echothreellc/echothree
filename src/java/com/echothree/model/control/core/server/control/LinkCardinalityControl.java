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
import com.echothree.model.control.core.common.choice.LinkCardinalityChoicesBean;
import com.echothree.model.control.core.common.transfer.LinkCardinalityDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.LinkCardinalityTransfer;
import com.echothree.model.control.core.server.transfer.LinkCardinalityDescriptionTransferCache;
import com.echothree.model.control.core.server.transfer.LinkCardinalityTransferCache;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.common.pk.LinkCardinalityPK;
import com.echothree.model.data.core.server.entity.LinkCardinality;
import com.echothree.model.data.core.server.entity.LinkCardinalityDescription;
import com.echothree.model.data.core.server.factory.LinkCardinalityDescriptionFactory;
import com.echothree.model.data.core.server.factory.LinkCardinalityDetailFactory;
import com.echothree.model.data.core.server.factory.LinkCardinalityFactory;
import com.echothree.model.data.core.server.value.LinkCardinalityDescriptionValue;
import com.echothree.model.data.core.server.value.LinkCardinalityDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.LINK_CARDINALITIES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.tables.core.LinkCardinalityDescriptions.LinkCardinalityDescriptions;
import static com.echothree.model.jooq.server.tables.core.LinkCardinalityDetails.LinkCardinalityDetails;
import static com.echothree.model.jooq.server.tables.core.LinkCardinalities.LinkCardinalities;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;

@CommandScope
public class LinkCardinalityControl
        extends BaseModelControl {

    @Inject
    LinkCardinalityTransferCache linkCardinalityTransferCache;

    @Inject
    LinkCardinalityDescriptionTransferCache linkCardinalityDescriptionTransferCache;

    /**
     * Creates a new instance of LinkCardinalityControl
     */
    protected LinkCardinalityControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Link Cardinalities
    // --------------------------------------------------------------------------------

    @Inject
    protected LinkCardinalityDetailFactory linkCardinalityDetailFactory;

    @Inject
    protected LinkCardinalityFactory linkCardinalityFactory;

    public LinkCardinality createLinkCardinality(String linkCardinalityName,
            Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultLinkCardinality = getDefaultLinkCardinality();
        var defaultFound = defaultLinkCardinality != null;

        if(defaultFound && isDefault) {
            var defaultLinkCardinalityDetailValue = getDefaultLinkCardinalityDetailValueForUpdate();

            defaultLinkCardinalityDetailValue.setIsDefault(false);
            updateLinkCardinalityFromValue(defaultLinkCardinalityDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var linkCardinality = linkCardinalityFactory.create();
        var linkCardinalityDetail = linkCardinalityDetailFactory.create(linkCardinality,
                linkCardinalityName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        linkCardinality = linkCardinalityFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                linkCardinality.getPrimaryKey());
        linkCardinality.setActiveDetail(linkCardinalityDetail);
        linkCardinality.setLastDetail(linkCardinalityDetail);
        linkCardinality.store();

        sendEvent(linkCardinality.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return linkCardinality;
    }

    /**
     * Assume that the entityInstance passed to this function is a ECHO_THREE.LinkCardinality
     */
    public LinkCardinality getLinkCardinalityByEntityInstance(final EntityInstance entityInstance,
            final EntityPermission entityPermission) {
        var pk = new LinkCardinalityPK(entityInstance.getEntityUniqueId());

        return linkCardinalityFactory.getEntityFromPK(entityPermission, pk);
    }

    public LinkCardinality getLinkCardinalityByEntityInstance(final EntityInstance entityInstance) {
        return getLinkCardinalityByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public LinkCardinality getLinkCardinalityByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getLinkCardinalityByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public LinkCardinality getLinkCardinalityByPK(LinkCardinalityPK pk) {
        return linkCardinalityFactory.getEntityFromPK(EntityPermission.READ_ONLY, pk);
    }

    public long countLinkCardinalities() {
        return session.getDslContext()
                .selectCount()
                .from(LinkCardinalities)
                .join(LinkCardinalityDetails).onKey(LINK_CARDINALITIES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public LinkCardinality getLinkCardinalityByName(final String linkCardinalityName,
            final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(LinkCardinalities.fields())
                .from(LinkCardinalities)
                .join(LinkCardinalityDetails).onKey(LINK_CARDINALITIES_ACTIVE_DETAIL_FK)
                .where(LinkCardinalityDetails.LINK_CARDINALITY_NAME.eq(linkCardinalityName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return linkCardinalityFactory.getEntityFromQuery(entityPermission, query);
    }

    public LinkCardinality getLinkCardinalityByName(String linkCardinalityName) {
        return getLinkCardinalityByName(linkCardinalityName, EntityPermission.READ_ONLY);
    }

    public LinkCardinality getLinkCardinalityByNameForUpdate(String linkCardinalityName) {
        return getLinkCardinalityByName(linkCardinalityName, EntityPermission.READ_WRITE);
    }

    public LinkCardinalityDetailValue getLinkCardinalityDetailValueForUpdate(LinkCardinality linkCardinality) {
        return linkCardinality == null ? null : linkCardinality.getLastDetailForUpdate().getLinkCardinalityDetailValue().clone();
    }

    public LinkCardinalityDetailValue getLinkCardinalityDetailValueByNameForUpdate(String linkCardinalityName) {
        return getLinkCardinalityDetailValueForUpdate(getLinkCardinalityByNameForUpdate(linkCardinalityName));
    }

    public LinkCardinality getDefaultLinkCardinality(final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(LinkCardinalities.fields())
                .from(LinkCardinalities)
                .join(LinkCardinalityDetails).onKey(LINK_CARDINALITIES_ACTIVE_DETAIL_FK)
                .where(LinkCardinalityDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return linkCardinalityFactory.getEntityFromQuery(entityPermission, query);
    }

    public LinkCardinality getDefaultLinkCardinality() {
        return getDefaultLinkCardinality(EntityPermission.READ_ONLY);
    }

    public LinkCardinality getDefaultLinkCardinalityForUpdate() {
        return getDefaultLinkCardinality(EntityPermission.READ_WRITE);
    }

    public LinkCardinalityDetailValue getDefaultLinkCardinalityDetailValueForUpdate() {
        return getDefaultLinkCardinalityForUpdate().getLastDetailForUpdate().getLinkCardinalityDetailValue().clone();
    }

    private List<LinkCardinality> getLinkCardinalities(final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(LinkCardinalities.fields())
                .from(LinkCardinalities)
                .join(LinkCardinalityDetails).onKey(LINK_CARDINALITIES_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(LinkCardinalityDetails.SORT_ORDER, LinkCardinalityDetails.LINK_CARDINALITY_NAME),
                    LinkCardinalityFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return linkCardinalityFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<LinkCardinality> getLinkCardinalities() {
        return getLinkCardinalities(EntityPermission.READ_ONLY);
    }

    public List<LinkCardinality> getLinkCardinalitiesForUpdate() {
        return getLinkCardinalities(EntityPermission.READ_WRITE);
    }

    public LinkCardinalityTransfer getLinkCardinalityTransfer(UserVisit userVisit, LinkCardinality linkCardinality) {
        return linkCardinalityTransferCache.getTransfer(userVisit, linkCardinality);
    }

    public List<LinkCardinalityTransfer> getLinkCardinalityTransfers(UserVisit userVisit, Collection<LinkCardinality> linkCardinalities) {
        List<LinkCardinalityTransfer> linkCardinalityTransfers = new ArrayList<>(linkCardinalities.size());

        linkCardinalities.forEach((linkCardinality) ->
                linkCardinalityTransfers.add(linkCardinalityTransferCache.getTransfer(userVisit, linkCardinality))
        );

        return linkCardinalityTransfers;
    }

    public List<LinkCardinalityTransfer> getLinkCardinalityTransfers(UserVisit userVisit) {
        return getLinkCardinalityTransfers(userVisit, getLinkCardinalities());
    }

    public LinkCardinalityChoicesBean getLinkCardinalityChoices(String defaultLinkCardinalityChoice,
            Language language, boolean allowNullChoice) {
        var linkCardinalities = getLinkCardinalities();
        var size = linkCardinalities.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultLinkCardinalityChoice == null) {
                defaultValue = "";
            }
        }

        for(var linkCardinality : linkCardinalities) {
            var linkCardinalityDetail = linkCardinality.getLastDetail();

            var label = getBestLinkCardinalityDescription(linkCardinality, language);
            var value = linkCardinalityDetail.getLinkCardinalityName();

            labels.add(label == null ? value : label);
            values.add(value);

            var usingDefaultChoice = defaultLinkCardinalityChoice != null && defaultLinkCardinalityChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && linkCardinalityDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new LinkCardinalityChoicesBean(labels, values, defaultValue);
    }

    private void updateLinkCardinalityFromValue(LinkCardinalityDetailValue linkCardinalityDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(linkCardinalityDetailValue.hasBeenModified()) {
            var linkCardinality = linkCardinalityFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    linkCardinalityDetailValue.getLinkCardinalityPK());
            var linkCardinalityDetail = linkCardinality.getActiveDetailForUpdate();

            linkCardinalityDetail.setThruTime(session.getStartTime());
            linkCardinalityDetail.store();

            var linkCardinalityPK = linkCardinalityDetail.getLinkCardinalityPK(); // Not updated
            var linkCardinalityName = linkCardinalityDetailValue.getLinkCardinalityName();
            var isDefault = linkCardinalityDetailValue.getIsDefault();
            var sortOrder = linkCardinalityDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultLinkCardinality = getDefaultLinkCardinality();
                var defaultFound = defaultLinkCardinality != null && !defaultLinkCardinality.equals(linkCardinality);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultLinkCardinalityDetailValue = getDefaultLinkCardinalityDetailValueForUpdate();

                    defaultLinkCardinalityDetailValue.setIsDefault(false);
                    updateLinkCardinalityFromValue(defaultLinkCardinalityDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            linkCardinalityDetail = linkCardinalityDetailFactory.create(linkCardinalityPK,
                    linkCardinalityName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

            linkCardinality.setActiveDetail(linkCardinalityDetail);
            linkCardinality.setLastDetail(linkCardinalityDetail);

            sendEvent(linkCardinalityPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateLinkCardinalityFromValue(LinkCardinalityDetailValue linkCardinalityDetailValue, BasePK updatedBy) {
        updateLinkCardinalityFromValue(linkCardinalityDetailValue, true, updatedBy);
    }

    private void deleteLinkCardinality(LinkCardinality linkCardinality, boolean checkDefault, BasePK deletedBy) {
        var linkCardinalityDetail = linkCardinality.getLastDetailForUpdate();

        deleteLinkCardinalityDescriptionsByLinkCardinality(linkCardinality, deletedBy);

        linkCardinalityDetail.setThruTime(session.getStartTime());
        linkCardinality.setActiveDetail(null);
        linkCardinality.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var defaultLinkCardinality = getDefaultLinkCardinality();
            if(defaultLinkCardinality == null) {
                var linkCardinalities = getLinkCardinalitiesForUpdate();

                if(!linkCardinalities.isEmpty()) {
                    var iter = linkCardinalities.iterator();
                    if(iter.hasNext()) {
                        defaultLinkCardinality = iter.next();
                    }
                    var linkCardinalityDetailValue = Objects.requireNonNull(defaultLinkCardinality).getLastDetailForUpdate().getLinkCardinalityDetailValue().clone();

                    linkCardinalityDetailValue.setIsDefault(true);
                    updateLinkCardinalityFromValue(linkCardinalityDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(linkCardinality.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteLinkCardinality(LinkCardinality linkCardinality, BasePK deletedBy) {
        deleteLinkCardinality(linkCardinality, true, deletedBy);
    }

    private void deleteLinkCardinalities(List<LinkCardinality> linkCardinalities, boolean checkDefault, BasePK deletedBy) {
        linkCardinalities.forEach((linkCardinality) -> deleteLinkCardinality(linkCardinality, checkDefault, deletedBy));
    }

    public void deleteLinkCardinalities(List<LinkCardinality> linkCardinalities, BasePK deletedBy) {
        deleteLinkCardinalities(linkCardinalities, true, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Link Cardinality Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected LinkCardinalityDescriptionFactory linkCardinalityDescriptionFactory;

    public LinkCardinalityDescription createLinkCardinalityDescription(LinkCardinality linkCardinality, Language language, String description, BasePK createdBy) {
        var linkCardinalityDescription = linkCardinalityDescriptionFactory.create(linkCardinality, language, description,
                session.getStartTime(), Session.MAX_TIME);

        sendEvent(linkCardinality.getPrimaryKey(), EventTypes.MODIFY, linkCardinalityDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return linkCardinalityDescription;
    }

    public LinkCardinalityDescription getLinkCardinalityDescription(final LinkCardinality linkCardinality,
            final Language language, final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(LinkCardinalityDescriptions.fields())
                .from(LinkCardinalityDescriptions)
                .where(LinkCardinalityDescriptions.LINK_CARDINALITY.eq(linkCardinality.getPrimaryKey()),
                        LinkCardinalityDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        LinkCardinalityDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return linkCardinalityDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }

    public LinkCardinalityDescription getLinkCardinalityDescription(LinkCardinality linkCardinality, Language language) {
        return getLinkCardinalityDescription(linkCardinality, language, EntityPermission.READ_ONLY);
    }

    public LinkCardinalityDescription getLinkCardinalityDescriptionForUpdate(LinkCardinality linkCardinality, Language language) {
        return getLinkCardinalityDescription(linkCardinality, language, EntityPermission.READ_WRITE);
    }

    public LinkCardinalityDescriptionValue getLinkCardinalityDescriptionValue(LinkCardinalityDescription linkCardinalityDescription) {
        return linkCardinalityDescription == null ? null : linkCardinalityDescription.getLinkCardinalityDescriptionValue().clone();
    }

    public LinkCardinalityDescriptionValue getLinkCardinalityDescriptionValueForUpdate(LinkCardinality linkCardinality, Language language) {
        return getLinkCardinalityDescriptionValue(getLinkCardinalityDescriptionForUpdate(linkCardinality, language));
    }

    private List<LinkCardinalityDescription> getLinkCardinalityDescriptionsByLinkCardinality(
            final LinkCardinality linkCardinality, final EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(LinkCardinalityDescriptions.fields())
                    .from(LinkCardinalityDescriptions)
                    .join(Languages).on(LinkCardinalityDescriptions.LANGUAGE.eq(Languages.LANGUAGE))
                    .where(LinkCardinalityDescriptions.LINK_CARDINALITY.eq(linkCardinality.getPrimaryKey()),
                            LinkCardinalityDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    LinkCardinalityDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(LinkCardinalityDescriptions.fields())
                    .from(LinkCardinalityDescriptions)
                    .where(LinkCardinalityDescriptions.LINK_CARDINALITY.eq(linkCardinality.getPrimaryKey()),
                            LinkCardinalityDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return linkCardinalityDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<LinkCardinalityDescription> getLinkCardinalityDescriptionsByLinkCardinality(LinkCardinality linkCardinality) {
        return getLinkCardinalityDescriptionsByLinkCardinality(linkCardinality, EntityPermission.READ_ONLY);
    }

    public List<LinkCardinalityDescription> getLinkCardinalityDescriptionsByLinkCardinalityForUpdate(LinkCardinality linkCardinality) {
        return getLinkCardinalityDescriptionsByLinkCardinality(linkCardinality, EntityPermission.READ_WRITE);
    }

    public String getBestLinkCardinalityDescription(LinkCardinality linkCardinality, Language language) {
        String description;
        var linkCardinalityDescription = getLinkCardinalityDescription(linkCardinality, language);

        if(linkCardinalityDescription == null && !language.getIsDefault()) {
            linkCardinalityDescription = getLinkCardinalityDescription(linkCardinality, partyControl.getDefaultLanguage());
        }

        if(linkCardinalityDescription == null) {
            description = linkCardinality.getLastDetail().getLinkCardinalityName();
        } else {
            description = linkCardinalityDescription.getDescription();
        }

        return description;
    }

    public LinkCardinalityDescriptionTransfer getLinkCardinalityDescriptionTransfer(UserVisit userVisit, LinkCardinalityDescription linkCardinalityDescription) {
        return linkCardinalityDescriptionTransferCache.getTransfer(userVisit, linkCardinalityDescription);
    }

    public List<LinkCardinalityDescriptionTransfer> getLinkCardinalityDescriptionTransfersByLinkCardinality(UserVisit userVisit, LinkCardinality linkCardinality) {
        var linkCardinalityDescriptions = getLinkCardinalityDescriptionsByLinkCardinality(linkCardinality);
        List<LinkCardinalityDescriptionTransfer> linkCardinalityDescriptionTransfers = new ArrayList<>(linkCardinalityDescriptions.size());

        linkCardinalityDescriptions.forEach((linkCardinalityDescription) ->
                linkCardinalityDescriptionTransfers.add(linkCardinalityDescriptionTransferCache.getTransfer(userVisit, linkCardinalityDescription))
        );

        return linkCardinalityDescriptionTransfers;
    }

    public void updateLinkCardinalityDescriptionFromValue(LinkCardinalityDescriptionValue linkCardinalityDescriptionValue, BasePK updatedBy) {
        if(linkCardinalityDescriptionValue.hasBeenModified()) {
            var linkCardinalityDescription = linkCardinalityDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    linkCardinalityDescriptionValue.getPrimaryKey());

            linkCardinalityDescription.setThruTime(session.getStartTime());
            linkCardinalityDescription.store();

            var linkCardinality = linkCardinalityDescription.getLinkCardinality();
            var language = linkCardinalityDescription.getLanguage();
            var description = linkCardinalityDescriptionValue.getDescription();

            linkCardinalityDescription = linkCardinalityDescriptionFactory.create(linkCardinality, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(linkCardinality.getPrimaryKey(), EventTypes.MODIFY, linkCardinalityDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteLinkCardinalityDescription(LinkCardinalityDescription linkCardinalityDescription, BasePK deletedBy) {
        linkCardinalityDescription.setThruTime(session.getStartTime());

        sendEvent(linkCardinalityDescription.getLinkCardinalityPK(), EventTypes.MODIFY, linkCardinalityDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteLinkCardinalityDescriptionsByLinkCardinality(LinkCardinality linkCardinality, BasePK deletedBy) {
        var linkCardinalityDescriptions = getLinkCardinalityDescriptionsByLinkCardinalityForUpdate(linkCardinality);

        linkCardinalityDescriptions.forEach((linkCardinalityDescription) ->
                deleteLinkCardinalityDescription(linkCardinalityDescription, deletedBy)
        );
    }

}
