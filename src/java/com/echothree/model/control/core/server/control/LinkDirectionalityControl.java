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
import com.echothree.model.control.core.common.choice.LinkDirectionalityChoicesBean;
import com.echothree.model.control.core.common.transfer.LinkDirectionalityDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.LinkDirectionalityTransfer;
import com.echothree.model.control.core.server.transfer.LinkDirectionalityDescriptionTransferCache;
import com.echothree.model.control.core.server.transfer.LinkDirectionalityTransferCache;
import com.echothree.model.data.core.common.pk.LinkDirectionalityPK;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.LinkDirectionality;
import com.echothree.model.data.core.server.entity.LinkDirectionalityDescription;
import com.echothree.model.data.core.server.factory.LinkDirectionalityDescriptionFactory;
import com.echothree.model.data.core.server.factory.LinkDirectionalityDetailFactory;
import com.echothree.model.data.core.server.factory.LinkDirectionalityFactory;
import com.echothree.model.data.core.server.value.LinkDirectionalityDescriptionValue;
import com.echothree.model.data.core.server.value.LinkDirectionalityDetailValue;
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
import java.util.Objects;
import javax.inject.Inject;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.LINK_DIRECTIONALITIES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.tables.core.LinkDirectionalities.LinkDirectionalities;
import static com.echothree.model.jooq.server.tables.core.LinkDirectionalityDescriptions.LinkDirectionalityDescriptions;
import static com.echothree.model.jooq.server.tables.core.LinkDirectionalityDetails.LinkDirectionalityDetails;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;

@CommandScope
public class LinkDirectionalityControl
        extends BaseModelControl {

    @Inject
    LinkDirectionalityDescriptionTransferCache linkDirectionalityDescriptionTransferCache;

    @Inject
    LinkDirectionalityTransferCache linkDirectionalityTransferCache;

    /**
     * Creates a new instance of LinkDirectionalityControl
     */
    protected LinkDirectionalityControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Link Directionalities
    // --------------------------------------------------------------------------------

    @Inject
    protected LinkDirectionalityDetailFactory linkDirectionalityDetailFactory;

    @Inject
    protected LinkDirectionalityFactory linkDirectionalityFactory;

    public LinkDirectionality createLinkDirectionality(String linkDirectionalityName,
            Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultLinkDirectionality = getDefaultLinkDirectionality();
        var defaultFound = defaultLinkDirectionality != null;

        if(defaultFound && isDefault) {
            var defaultLinkDirectionalityDetailValue = getDefaultLinkDirectionalityDetailValueForUpdate();

            defaultLinkDirectionalityDetailValue.setIsDefault(false);
            updateLinkDirectionalityFromValue(defaultLinkDirectionalityDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var linkDirectionality = linkDirectionalityFactory.create();
        var linkDirectionalityDetail = linkDirectionalityDetailFactory.create(linkDirectionality,
                linkDirectionalityName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        linkDirectionality = linkDirectionalityFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                linkDirectionality.getPrimaryKey());
        linkDirectionality.setActiveDetail(linkDirectionalityDetail);
        linkDirectionality.setLastDetail(linkDirectionalityDetail);
        linkDirectionality.store();

        sendEvent(linkDirectionality.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return linkDirectionality;
    }

    /**
     * Assume that the entityInstance passed to this function is a ECHO_THREE.LinkDirectionality
     */
    public LinkDirectionality getLinkDirectionalityByEntityInstance(final EntityInstance entityInstance,
            final EntityPermission entityPermission) {
        var pk = new LinkDirectionalityPK(entityInstance.getEntityUniqueId());

        return linkDirectionalityFactory.getEntityFromPK(entityPermission, pk);
    }

    public LinkDirectionality getLinkDirectionalityByEntityInstance(final EntityInstance entityInstance) {
        return getLinkDirectionalityByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public LinkDirectionality getLinkDirectionalityByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getLinkDirectionalityByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public LinkDirectionality getLinkDirectionalityByPK(LinkDirectionalityPK pk) {
        return linkDirectionalityFactory.getEntityFromPK(EntityPermission.READ_ONLY, pk);
    }

    public long countLinkDirectionalities() {
        return session.getDslContext()
                .selectCount()
                .from(LinkDirectionalities)
                .join(LinkDirectionalityDetails).onKey(LINK_DIRECTIONALITIES_LAST_DETAIL_FK)
                .where(LinkDirectionalities.ACTIVE_DETAIL.isNotNull())
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public LinkDirectionality getLinkDirectionalityByName(final String linkDirectionalityName,
            final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(LinkDirectionalities.fields())
                .from(LinkDirectionalities)
                .join(LinkDirectionalityDetails).onKey(LINK_DIRECTIONALITIES_LAST_DETAIL_FK)
                .where(LinkDirectionalities.ACTIVE_DETAIL.isNotNull())
                .and(LinkDirectionalityDetails.LINK_DIRECTIONALITY_NAME.eq(linkDirectionalityName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return linkDirectionalityFactory.getEntityFromQuery(entityPermission, query);
    }

    public LinkDirectionality getLinkDirectionalityByName(String linkDirectionalityName) {
        return getLinkDirectionalityByName(linkDirectionalityName, EntityPermission.READ_ONLY);
    }

    public LinkDirectionality getLinkDirectionalityByNameForUpdate(String linkDirectionalityName) {
        return getLinkDirectionalityByName(linkDirectionalityName, EntityPermission.READ_WRITE);
    }

    public LinkDirectionalityDetailValue getLinkDirectionalityDetailValueForUpdate(LinkDirectionality linkDirectionality) {
        return linkDirectionality == null ? null : linkDirectionality.getLastDetailForUpdate().getLinkDirectionalityDetailValue().clone();
    }

    public LinkDirectionalityDetailValue getLinkDirectionalityDetailValueByNameForUpdate(String linkDirectionalityName) {
        return getLinkDirectionalityDetailValueForUpdate(getLinkDirectionalityByNameForUpdate(linkDirectionalityName));
    }

    public LinkDirectionality getDefaultLinkDirectionality(final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(LinkDirectionalities.fields())
                .from(LinkDirectionalities)
                .join(LinkDirectionalityDetails).onKey(LINK_DIRECTIONALITIES_LAST_DETAIL_FK)
                .where(LinkDirectionalities.ACTIVE_DETAIL.isNotNull())
                .and(LinkDirectionalityDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return linkDirectionalityFactory.getEntityFromQuery(entityPermission, query);
    }

    public LinkDirectionality getDefaultLinkDirectionality() {
        return getDefaultLinkDirectionality(EntityPermission.READ_ONLY);
    }

    public LinkDirectionality getDefaultLinkDirectionalityForUpdate() {
        return getDefaultLinkDirectionality(EntityPermission.READ_WRITE);
    }

    public LinkDirectionalityDetailValue getDefaultLinkDirectionalityDetailValueForUpdate() {
        return getDefaultLinkDirectionalityForUpdate().getLastDetailForUpdate().getLinkDirectionalityDetailValue().clone();
    }

    private List<LinkDirectionality> getLinkDirectionalities(final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(LinkDirectionalities.fields())
                .from(LinkDirectionalities)
                .join(LinkDirectionalityDetails).onKey(LINK_DIRECTIONALITIES_LAST_DETAIL_FK)
                .where(LinkDirectionalities.ACTIVE_DETAIL.isNotNull());

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(LinkDirectionalityDetails.SORT_ORDER, LinkDirectionalityDetails.LINK_DIRECTIONALITY_NAME),
                    LinkDirectionalityFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return linkDirectionalityFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<LinkDirectionality> getLinkDirectionalities() {
        return getLinkDirectionalities(EntityPermission.READ_ONLY);
    }

    public List<LinkDirectionality> getLinkDirectionalitiesForUpdate() {
        return getLinkDirectionalities(EntityPermission.READ_WRITE);
    }

    public LinkDirectionalityTransfer getLinkDirectionalityTransfer(UserVisit userVisit, LinkDirectionality linkDirectionality) {
        return linkDirectionalityTransferCache.getTransfer(userVisit, linkDirectionality);
    }

    public List<LinkDirectionalityTransfer> getLinkDirectionalityTransfers(UserVisit userVisit, Collection<LinkDirectionality> linkDirectionalities) {
        List<LinkDirectionalityTransfer> linkDirectionalityTransfers = new ArrayList<>(linkDirectionalities.size());

        linkDirectionalities.forEach((linkDirectionality) ->
                linkDirectionalityTransfers.add(linkDirectionalityTransferCache.getTransfer(userVisit, linkDirectionality))
        );

        return linkDirectionalityTransfers;
    }

    public List<LinkDirectionalityTransfer> getLinkDirectionalityTransfers(UserVisit userVisit) {
        return getLinkDirectionalityTransfers(userVisit, getLinkDirectionalities());
    }

    public LinkDirectionalityChoicesBean getLinkDirectionalityChoices(String defaultLinkDirectionalityChoice,
            Language language, boolean allowNullChoice) {
        var linkDirectionalities = getLinkDirectionalities();
        var size = linkDirectionalities.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultLinkDirectionalityChoice == null) {
                defaultValue = "";
            }
        }

        for(var linkDirectionality : linkDirectionalities) {
            var linkDirectionalityDetail = linkDirectionality.getLastDetail();

            var label = getBestLinkDirectionalityDescription(linkDirectionality, language);
            var value = linkDirectionalityDetail.getLinkDirectionalityName();

            labels.add(label == null ? value : label);
            values.add(value);

            var usingDefaultChoice = defaultLinkDirectionalityChoice != null && defaultLinkDirectionalityChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && linkDirectionalityDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new LinkDirectionalityChoicesBean(labels, values, defaultValue);
    }

    private void updateLinkDirectionalityFromValue(LinkDirectionalityDetailValue linkDirectionalityDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(linkDirectionalityDetailValue.hasBeenModified()) {
            var linkDirectionality = linkDirectionalityFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    linkDirectionalityDetailValue.getLinkDirectionalityPK());
            var linkDirectionalityDetail = linkDirectionality.getActiveDetailForUpdate();

            linkDirectionalityDetail.setThruTime(session.getStartTime());
            linkDirectionalityDetail.store();

            var linkDirectionalityPK = linkDirectionalityDetail.getLinkDirectionalityPK(); // Not updated
            var linkDirectionalityName = linkDirectionalityDetailValue.getLinkDirectionalityName();
            var isDefault = linkDirectionalityDetailValue.getIsDefault();
            var sortOrder = linkDirectionalityDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultLinkDirectionality = getDefaultLinkDirectionality();
                var defaultFound = defaultLinkDirectionality != null && !defaultLinkDirectionality.equals(linkDirectionality);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultLinkDirectionalityDetailValue = getDefaultLinkDirectionalityDetailValueForUpdate();

                    defaultLinkDirectionalityDetailValue.setIsDefault(false);
                    updateLinkDirectionalityFromValue(defaultLinkDirectionalityDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            linkDirectionalityDetail = linkDirectionalityDetailFactory.create(linkDirectionalityPK,
                    linkDirectionalityName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

            linkDirectionality.setActiveDetail(linkDirectionalityDetail);
            linkDirectionality.setLastDetail(linkDirectionalityDetail);

            sendEvent(linkDirectionalityPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateLinkDirectionalityFromValue(LinkDirectionalityDetailValue linkDirectionalityDetailValue, BasePK updatedBy) {
        updateLinkDirectionalityFromValue(linkDirectionalityDetailValue, true, updatedBy);
    }

    private void deleteLinkDirectionality(LinkDirectionality linkDirectionality, boolean checkDefault, BasePK deletedBy) {
        var linkDirectionalityDetail = linkDirectionality.getLastDetailForUpdate();

        deleteLinkDirectionalityDescriptionsByLinkDirectionality(linkDirectionality, deletedBy);

        linkDirectionalityDetail.setThruTime(session.getStartTime());
        linkDirectionality.setActiveDetail(null);
        linkDirectionality.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var defaultLinkDirectionality = getDefaultLinkDirectionality();
            if(defaultLinkDirectionality == null) {
                var linkDirectionalities = getLinkDirectionalitiesForUpdate();

                if(!linkDirectionalities.isEmpty()) {
                    var iter = linkDirectionalities.iterator();
                    if(iter.hasNext()) {
                        defaultLinkDirectionality = iter.next();
                    }
                    var linkDirectionalityDetailValue = Objects.requireNonNull(defaultLinkDirectionality).getLastDetailForUpdate().getLinkDirectionalityDetailValue().clone();

                    linkDirectionalityDetailValue.setIsDefault(true);
                    updateLinkDirectionalityFromValue(linkDirectionalityDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(linkDirectionality.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteLinkDirectionality(LinkDirectionality linkDirectionality, BasePK deletedBy) {
        deleteLinkDirectionality(linkDirectionality, true, deletedBy);
    }

    private void deleteLinkDirectionalities(List<LinkDirectionality> linkDirectionalities, boolean checkDefault, BasePK deletedBy) {
        linkDirectionalities.forEach((linkDirectionality) -> deleteLinkDirectionality(linkDirectionality, checkDefault, deletedBy));
    }

    public void deleteLinkDirectionalities(List<LinkDirectionality> linkDirectionalities, BasePK deletedBy) {
        deleteLinkDirectionalities(linkDirectionalities, true, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Link Directionality Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected LinkDirectionalityDescriptionFactory linkDirectionalityDescriptionFactory;

    public LinkDirectionalityDescription createLinkDirectionalityDescription(LinkDirectionality linkDirectionality, Language language, String description, BasePK createdBy) {
        var linkDirectionalityDescription = linkDirectionalityDescriptionFactory.create(linkDirectionality, language, description,
                session.getStartTime(), Session.MAX_TIME);

        sendEvent(linkDirectionality.getPrimaryKey(), EventTypes.MODIFY, linkDirectionalityDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return linkDirectionalityDescription;
    }

    public LinkDirectionalityDescription getLinkDirectionalityDescription(final LinkDirectionality linkDirectionality,
            final Language language, final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(LinkDirectionalityDescriptions.fields())
                .from(LinkDirectionalityDescriptions)
                .where(LinkDirectionalityDescriptions.LINK_DIRECTIONALITY.eq(linkDirectionality.getPrimaryKey()),
                        LinkDirectionalityDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        LinkDirectionalityDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return linkDirectionalityDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }

    public LinkDirectionalityDescription getLinkDirectionalityDescription(LinkDirectionality linkDirectionality, Language language) {
        return getLinkDirectionalityDescription(linkDirectionality, language, EntityPermission.READ_ONLY);
    }

    public LinkDirectionalityDescription getLinkDirectionalityDescriptionForUpdate(LinkDirectionality linkDirectionality, Language language) {
        return getLinkDirectionalityDescription(linkDirectionality, language, EntityPermission.READ_WRITE);
    }

    public LinkDirectionalityDescriptionValue getLinkDirectionalityDescriptionValue(LinkDirectionalityDescription linkDirectionalityDescription) {
        return linkDirectionalityDescription == null ? null : linkDirectionalityDescription.getLinkDirectionalityDescriptionValue().clone();
    }

    public LinkDirectionalityDescriptionValue getLinkDirectionalityDescriptionValueForUpdate(LinkDirectionality linkDirectionality, Language language) {
        return getLinkDirectionalityDescriptionValue(getLinkDirectionalityDescriptionForUpdate(linkDirectionality, language));
    }

    private List<LinkDirectionalityDescription> getLinkDirectionalityDescriptionsByLinkDirectionality(
            final LinkDirectionality linkDirectionality, final EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(LinkDirectionalityDescriptions.fields())
                    .from(LinkDirectionalityDescriptions)
                    .join(Languages).on(LinkDirectionalityDescriptions.LANGUAGE.eq(Languages.LANGUAGE))
                    .where(LinkDirectionalityDescriptions.LINK_DIRECTIONALITY.eq(linkDirectionality.getPrimaryKey()),
                            LinkDirectionalityDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    LinkDirectionalityDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(LinkDirectionalityDescriptions.fields())
                    .from(LinkDirectionalityDescriptions)
                    .where(LinkDirectionalityDescriptions.LINK_DIRECTIONALITY.eq(linkDirectionality.getPrimaryKey()),
                            LinkDirectionalityDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return linkDirectionalityDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<LinkDirectionalityDescription> getLinkDirectionalityDescriptionsByLinkDirectionality(LinkDirectionality linkDirectionality) {
        return getLinkDirectionalityDescriptionsByLinkDirectionality(linkDirectionality, EntityPermission.READ_ONLY);
    }

    public List<LinkDirectionalityDescription> getLinkDirectionalityDescriptionsByLinkDirectionalityForUpdate(LinkDirectionality linkDirectionality) {
        return getLinkDirectionalityDescriptionsByLinkDirectionality(linkDirectionality, EntityPermission.READ_WRITE);
    }

    public long countLinkDirectionalityDescriptionsByLinkDirectionality(LinkDirectionality linkDirectionality) {
        return session.getDslContext()
                .selectCount()
                .from(LinkDirectionalityDescriptions)
                .where(LinkDirectionalityDescriptions.LINK_DIRECTIONALITY.eq(linkDirectionality.getPrimaryKey()),
                        LinkDirectionalityDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public String getBestLinkDirectionalityDescription(LinkDirectionality linkDirectionality, Language language) {
        String description;
        var linkDirectionalityDescription = getLinkDirectionalityDescription(linkDirectionality, language);

        if(linkDirectionalityDescription == null && !language.getIsDefault()) {
            linkDirectionalityDescription = getLinkDirectionalityDescription(linkDirectionality, partyControl.getDefaultLanguage());
        }

        if(linkDirectionalityDescription == null) {
            description = linkDirectionality.getLastDetail().getLinkDirectionalityName();
        } else {
            description = linkDirectionalityDescription.getDescription();
        }

        return description;
    }

    public LinkDirectionalityDescriptionTransfer getLinkDirectionalityDescriptionTransfer(UserVisit userVisit, LinkDirectionalityDescription linkDirectionalityDescription) {
        return linkDirectionalityDescriptionTransferCache.getTransfer(userVisit, linkDirectionalityDescription);
    }

    public List<LinkDirectionalityDescriptionTransfer> getLinkDirectionalityDescriptionTransfers(UserVisit userVisit,
            Collection<LinkDirectionalityDescription> linkDirectionalityDescriptions) {
        List<LinkDirectionalityDescriptionTransfer> linkDirectionalityDescriptionTransfers = new ArrayList<>(linkDirectionalityDescriptions.size());

        linkDirectionalityDescriptions.forEach((linkDirectionalityDescription) ->
                linkDirectionalityDescriptionTransfers.add(linkDirectionalityDescriptionTransferCache.getTransfer(userVisit, linkDirectionalityDescription))
        );

        return linkDirectionalityDescriptionTransfers;
    }

    public List<LinkDirectionalityDescriptionTransfer> getLinkDirectionalityDescriptionTransfersByLinkDirectionality(UserVisit userVisit, LinkDirectionality linkDirectionality) {
        return getLinkDirectionalityDescriptionTransfers(userVisit, getLinkDirectionalityDescriptionsByLinkDirectionality(linkDirectionality));
    }

    public void updateLinkDirectionalityDescriptionFromValue(LinkDirectionalityDescriptionValue linkDirectionalityDescriptionValue, BasePK updatedBy) {
        if(linkDirectionalityDescriptionValue.hasBeenModified()) {
            var linkDirectionalityDescription = linkDirectionalityDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    linkDirectionalityDescriptionValue.getPrimaryKey());

            linkDirectionalityDescription.setThruTime(session.getStartTime());
            linkDirectionalityDescription.store();

            var linkDirectionality = linkDirectionalityDescription.getLinkDirectionality();
            var language = linkDirectionalityDescription.getLanguage();
            var description = linkDirectionalityDescriptionValue.getDescription();

            linkDirectionalityDescription = linkDirectionalityDescriptionFactory.create(linkDirectionality, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(linkDirectionality.getPrimaryKey(), EventTypes.MODIFY, linkDirectionalityDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteLinkDirectionalityDescription(LinkDirectionalityDescription linkDirectionalityDescription, BasePK deletedBy) {
        linkDirectionalityDescription.setThruTime(session.getStartTime());

        sendEvent(linkDirectionalityDescription.getLinkDirectionalityPK(), EventTypes.MODIFY, linkDirectionalityDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteLinkDirectionalityDescriptionsByLinkDirectionality(LinkDirectionality linkDirectionality, BasePK deletedBy) {
        var linkDirectionalityDescriptions = getLinkDirectionalityDescriptionsByLinkDirectionalityForUpdate(linkDirectionality);

        linkDirectionalityDescriptions.forEach((linkDirectionalityDescription) ->
                deleteLinkDirectionalityDescription(linkDirectionalityDescription, deletedBy)
        );
    }

}
