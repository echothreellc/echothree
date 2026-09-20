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

package com.echothree.model.control.offer.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.offer.common.choice.SourceChoicesBean;
import com.echothree.model.control.offer.common.transfer.SourceTransfer;
import com.echothree.model.control.offer.server.transfer.SourceTransferCache;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.offer.common.pk.SourcePK;
import com.echothree.model.data.offer.server.entity.OfferUse;
import com.echothree.model.data.offer.server.entity.Source;
import com.echothree.model.data.offer.server.factory.SourceDetailFactory;
import com.echothree.model.data.offer.server.factory.SourceFactory;
import com.echothree.model.data.offer.server.value.SourceDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.offer.OfferForeignKeys.SOURCES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.tables.offer.SourceDetails.SourceDetails;
import static com.echothree.model.jooq.server.tables.offer.Sources.Sources;
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
public class SourceControl
        extends BaseModelControl {

    @Inject
    private OfferControl offerControl;

    @Inject
    protected UseControl useControl;

    /** Creates a new instance of UseControl */
    protected SourceControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Sources
    // --------------------------------------------------------------------------------

    @Inject
    protected SourceDetailFactory sourceDetailFactory;

    @Inject
    private SourceFactory sourceFactory;

    @Inject
    private SourceTransferCache sourceTransferCache;

    public Source createSource(String sourceName, OfferUse offerUse, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultSource = getDefaultSource();
        var defaultFound = defaultSource != null;

        if(defaultFound && isDefault) {
            var defaultSourceDetailValue = getDefaultSourceDetailValueForUpdate();

            defaultSourceDetailValue.setIsDefault(false);
            updateSourceFromValue(defaultSourceDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var source = sourceFactory.create();
        var sourceDetail = sourceDetailFactory.create(source, sourceName, offerUse, isDefault,
                sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        source = sourceFactory.getEntityFromPK(EntityPermission.READ_WRITE, source.getPrimaryKey());
        source.setActiveDetail(sourceDetail);
        source.setLastDetail(sourceDetail);
        source.store();

        sendEvent(offerUse.getLastDetail().getOfferPK(), EventTypes.MODIFY, source.getPrimaryKey(),
                EventTypes.CREATE, createdBy);

        return source;
    }

    public long countSources() {
        return session.getDslContext()
                .selectCount()
                .from(Sources)
                .join(SourceDetails).onKey(SOURCES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countSourcesByOfferUse(OfferUse offerUse) {
        return session.getDslContext()
                .selectCount()
                .from(Sources)
                .join(SourceDetails).onKey(SOURCES_ACTIVE_DETAIL_FK)
                .where(SourceDetails.OFFER_USE.eq(offerUse.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.Source */
    public Source getSourceByEntityInstance(EntityInstance entityInstance) {
        var pk = new SourcePK(entityInstance.getEntityUniqueId());
        var source = sourceFactory.getEntityFromPK(EntityPermission.READ_ONLY, pk);

        return source;
    }

    private Source getDefaultSource(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Sources.fields())
                .from(Sources)
                .join(SourceDetails).onKey(SOURCES_ACTIVE_DETAIL_FK)
                .where(SourceDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return sourceFactory.getEntityFromQuery(entityPermission, query);
    }

    public Source getDefaultSource() {
        return getDefaultSource(EntityPermission.READ_ONLY);
    }

    public Source getDefaultSourceForUpdate() {
        return getDefaultSource(EntityPermission.READ_WRITE);
    }

    public SourceDetailValue getDefaultSourceDetailValueForUpdate() {
        return getDefaultSourceForUpdate().getLastDetailForUpdate().getSourceDetailValue().clone();
    }

    private List<Source> getSources(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Sources.fields())
                .from(Sources)
                .join(SourceDetails).onKey(SOURCES_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(SourceDetails.SORT_ORDER, SourceDetails.SOURCE_NAME),
                    SourceFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return sourceFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<Source> getSources() {
        return getSources(EntityPermission.READ_ONLY);
    }

    public List<Source> getSourcesForUpdate() {
        return getSources(EntityPermission.READ_WRITE);
    }

    private Source getSourceByName(String sourceName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Sources.fields())
                .from(Sources)
                .join(SourceDetails).onKey(SOURCES_ACTIVE_DETAIL_FK)
                .where(SourceDetails.SOURCE_NAME.eq(sourceName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return sourceFactory.getEntityFromQuery(entityPermission, query);
    }

    public Source getSourceByName(String sourceName) {
        return getSourceByName(sourceName, EntityPermission.READ_ONLY);
    }

    public Source getSourceByNameForUpdate(String sourceName) {
        return getSourceByName(sourceName, EntityPermission.READ_WRITE);
    }

    public SourceDetailValue getSourceDetailValueForUpdate(Source source) {
        return source == null? null: source.getLastDetailForUpdate().getSourceDetailValue().clone();
    }

    public SourceDetailValue getSourceDetailValueByNameForUpdate(String sourceName) {
        return getSourceDetailValueForUpdate(getSourceByNameForUpdate(sourceName));
    }

    private List<Source> getSourcesByOfferUse(OfferUse offerUse, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Sources.fields())
                .from(Sources)
                .join(SourceDetails).onKey(SOURCES_ACTIVE_DETAIL_FK)
                .where(SourceDetails.OFFER_USE.eq(offerUse.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(SourceDetails.SORT_ORDER, SourceDetails.SOURCE_NAME),
                    SourceFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return sourceFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<Source> getSourcesByOfferUse(OfferUse offerUse) {
        return getSourcesByOfferUse(offerUse, EntityPermission.READ_ONLY);
    }

    public List<Source> getSourcesByOfferUseForUpdate(OfferUse offerUse) {
        return getSourcesByOfferUse(offerUse, EntityPermission.READ_WRITE);
    }

    public SourceChoicesBean getSourceChoices(String defaultSourceChoice, Language language, boolean allowNullChoice) {
        var sources = getSources();
        var size = sources.size() + (allowNullChoice? 1: 0);
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultSourceChoice == null) {
                defaultValue = "";
            }
        }

        for(var source : sources) {
            var sourceDetail = source.getLastDetail();

            var label = getBestSourceDescription(source, language);
            var value = sourceDetail.getSourceName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultSourceChoice != null && defaultSourceChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && sourceDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new SourceChoicesBean(labels, values, defaultValue);
    }

    public String getBestSourceDescription(Source source, Language language) {
        var sourceDetail = source.getLastDetail();
        var sourceName = sourceDetail.getSourceName();
        var offerUseDetail = sourceDetail.getOfferUse().getLastDetail();
        var offerDescription = offerControl.getBestOfferDescription(offerUseDetail.getOffer(), language);
        var useDescription = useControl.getBestUseDescription(offerUseDetail.getUse(), language);

        return sourceName + ", " + offerDescription + ", " + useDescription;
    }

    public SourceTransfer getSourceTransfer(UserVisit userVisit, Source source) {
        return sourceTransferCache.getSourceTransfer(userVisit, source);
    }

    public List<SourceTransfer> getSourceTransfers(UserVisit userVisit, Collection<Source> sources) {
        List<SourceTransfer> sourceTransfers = new ArrayList<>(sources.size());

        sources.forEach((source) -> {
            sourceTransfers.add(sourceTransferCache.getSourceTransfer(userVisit, source));
        });

        return sourceTransfers;
    }

    public List<SourceTransfer> getSourceTransfers(UserVisit userVisit) {
        return getSourceTransfers(userVisit, getSources());
    }

    private void updateSourceFromValue(SourceDetailValue sourceDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(sourceDetailValue.hasBeenModified()) {
            var source = sourceFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    sourceDetailValue.getSourcePK());
            var sourceDetail = source.getActiveDetailForUpdate();

            sourceDetail.setThruTime(session.getStartTime());
            sourceDetail.store();

            var sourcePK = sourceDetail.getSourcePK(); // Do not update
            var sourceName = sourceDetailValue.getSourceName();
            var offerUse = sourceDetail.getOfferUse(); // Do not update
            var isDefault = sourceDetailValue.getIsDefault();
            var sortOrder = sourceDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultSource = getDefaultSource();
                var defaultFound = defaultSource != null && !defaultSource.equals(source);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultSourceDetailValue = getDefaultSourceDetailValueForUpdate();

                    defaultSourceDetailValue.setIsDefault(false);
                    updateSourceFromValue(defaultSourceDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            sourceDetail = sourceDetailFactory.create(sourcePK, sourceName, offerUse.getPrimaryKey(), isDefault,
                    sortOrder, session.getStartTime(), Session.MAX_TIME);

            source.setActiveDetail(sourceDetail);
            source.setLastDetail(sourceDetail);

            sendEvent(offerUse.getLastDetail().getOfferPK(), EventTypes.MODIFY, source.getPrimaryKey(),
                    EventTypes.MODIFY, updatedBy);
        }
    }

    public void updateSourceFromValue(SourceDetailValue sourceDetailValue, BasePK updatedBy) {
        updateSourceFromValue(sourceDetailValue, true, updatedBy);
    }

    public void deleteSource(Source source, BasePK deletedBy) {
        var sourceDetail = source.getLastDetailForUpdate();
        sourceDetail.setThruTime(session.getStartTime());
        source.setActiveDetail(null);
        source.store();

        // Check for default, and pick one if necessary
        var defaultSource = getDefaultSource();
        if(defaultSource == null) {
            var sources = getSourcesForUpdate();

            if(!sources.isEmpty()) {
                var iter = sources.iterator();
                if(iter.hasNext()) {
                    defaultSource = iter.next();
                }
                var sourceDetailValue = Objects.requireNonNull(defaultSource).getLastDetailForUpdate().getSourceDetailValue().clone();

                sourceDetailValue.setIsDefault(true);
                updateSourceFromValue(sourceDetailValue, false, deletedBy);
            }
        }

        sendEvent(source.getLastDetail().getOfferUse().getLastDetail().getOfferPK(),
                EventTypes.MODIFY, source.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteSources(List<Source> sources, BasePK deletedBy) {
        sources.forEach((source) -> 
                deleteSource(source, deletedBy)
        );
    }

    public void deleteSourcesByOfferUse(OfferUse offerUse, BasePK deletedBy) {
        deleteSources(getSourcesByOfferUseForUpdate(offerUse), deletedBy);
    }

}
