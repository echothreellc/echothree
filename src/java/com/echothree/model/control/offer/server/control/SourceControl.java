// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.offer.common.pk.SourcePK;
import com.echothree.model.data.offer.server.entity.OfferUse;
import com.echothree.model.data.offer.server.entity.OfferUseDetail;
import com.echothree.model.data.offer.server.entity.Source;
import com.echothree.model.data.offer.server.entity.SourceDetail;
import com.echothree.model.data.offer.server.factory.SourceDetailFactory;
import com.echothree.model.data.offer.server.factory.SourceFactory;
import com.echothree.model.data.offer.server.value.SourceDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class SourceControl
        extends BaseOfferControl {

    /** Creates a new instance of UseControl */
    public SourceControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Sources
    // --------------------------------------------------------------------------------

    public Source createSource(String sourceName, OfferUse offerUse, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        Source defaultSource = getDefaultSource();
        boolean defaultFound = defaultSource != null;

        if(defaultFound && isDefault) {
            SourceDetailValue defaultSourceDetailValue = getDefaultSourceDetailValueForUpdate();

            defaultSourceDetailValue.setIsDefault(Boolean.FALSE);
            updateSourceFromValue(defaultSourceDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        Source source = SourceFactory.getInstance().create();
        SourceDetail sourceDetail = SourceDetailFactory.getInstance().create(source, sourceName, offerUse, isDefault,
                sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        source = SourceFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, source.getPrimaryKey());
        source.setActiveDetail(sourceDetail);
        source.setLastDetail(sourceDetail);
        source.store();

        sendEventUsingNames(offerUse.getLastDetail().getOfferPK(), EventTypes.MODIFY.name(), source.getPrimaryKey(),
                EventTypes.CREATE.name(), createdBy);

        return source;
    }

    public long countSourcesByOfferUse(OfferUse offerUse) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM sources, sourcedetails " +
                        "WHERE src_activedetailid = srcdt_sourcedetailid AND srcdt_ofruse_offeruseid = ?",
                offerUse);
    }

    /** Assume that the entityInstance passed to this function is a ECHOTHREE.Source */
    public Source getSourceByEntityInstance(EntityInstance entityInstance) {
        SourcePK pk = new SourcePK(entityInstance.getEntityUniqueId());
        Source source = SourceFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);

        return source;
    }

    private Source getDefaultSource(EntityPermission entityPermission) {
        String query = null;

        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM sources, sourcedetails " +
                    "WHERE src_activedetailid = srcdt_sourcedetailid AND srcdt_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM sources, sourcedetails " +
                    "WHERE src_activedetailid = srcdt_sourcedetailid AND srcdt_isdefault = 1 " +
                    "FOR UPDATE";
        }

        PreparedStatement ps = SourceFactory.getInstance().prepareStatement(query);

        return SourceFactory.getInstance().getEntityFromQuery(entityPermission, ps);
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
        String query = null;

        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM sources, sourcedetails " +
                    "WHERE src_activedetailid = srcdt_sourcedetailid " +
                    "ORDER BY srcdt_sortorder, srcdt_sourcename";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM sources, sourcedetails " +
                    "WHERE src_activedetailid = srcdt_sourcedetailid " +
                    "FOR UPDATE";
        }

        PreparedStatement ps = SourceFactory.getInstance().prepareStatement(query);

        return SourceFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }

    public List<Source> getSources() {
        return getSources(EntityPermission.READ_ONLY);
    }

    public List<Source> getSourcesForUpdate() {
        return getSources(EntityPermission.READ_WRITE);
    }

    private Source getSourceByName(String sourceName, EntityPermission entityPermission) {
        Source source = null;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM sources, sourcedetails " +
                        "WHERE src_activedetailid = srcdt_sourcedetailid AND srcdt_sourcename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM sources, sourcedetails " +
                        "WHERE src_activedetailid = srcdt_sourcedetailid AND srcdt_sourcename = ? " +
                        "FOR UPDATE";
            }

            PreparedStatement ps = SourceFactory.getInstance().prepareStatement(query);

            ps.setString(1, sourceName);

            source = SourceFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return source;
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
        List<Source> sources = null;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM sources, sourcedetails " +
                        "WHERE src_activedetailid = srcdt_sourcedetailid AND srcdt_ofruse_offeruseid = ? " +
                        "ORDER BY srcdt_sortorder, srcdt_sourcename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM sources, sourcedetails " +
                        "WHERE src_activedetailid = srcdt_sourcedetailid AND srcdt_ofruse_offeruseid = ? " +
                        "FOR UPDATE";
            }

            PreparedStatement ps = SourceFactory.getInstance().prepareStatement(query);

            ps.setLong(1, offerUse.getPrimaryKey().getEntityId());

            sources = SourceFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return sources;
    }

    public List<Source> getSourcesByOfferUse(OfferUse offerUse) {
        return getSourcesByOfferUse(offerUse, EntityPermission.READ_ONLY);
    }

    public List<Source> getSourcesByOfferUseForUpdate(OfferUse offerUse) {
        return getSourcesByOfferUse(offerUse, EntityPermission.READ_WRITE);
    }

    public SourceChoicesBean getSourceChoices(String defaultSourceChoice, Language language, boolean allowNullChoice) {
        List<Source> sources = getSources();
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
            SourceDetail sourceDetail = source.getLastDetail();

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
        var offerControl = Session.getModelController(OfferControl.class);
        var useControl = Session.getModelController(UseControl.class);
        SourceDetail sourceDetail = source.getLastDetail();
        String sourceName = sourceDetail.getSourceName();
        OfferUseDetail offerUseDetail = sourceDetail.getOfferUse().getLastDetail();
        String offerDescription = offerControl.getBestOfferDescription(offerUseDetail.getOffer(), language);
        String useDescription = useControl.getBestUseDescription(offerUseDetail.getUse(), language);

        return new StringBuilder(sourceName).append(", ").append(offerDescription).append(", ").append(useDescription).toString();
    }

    public SourceTransfer getSourceTransfer(UserVisit userVisit, Source source) {
        return getOfferTransferCaches(userVisit).getSourceTransferCache().getSourceTransfer(source);
    }

    public List<SourceTransfer> getSourceTransfers(UserVisit userVisit, Collection<Source> sources) {
        List<SourceTransfer> sourceTransfers = new ArrayList<>(sources.size());

        sources.forEach((source) -> {
            sourceTransfers.add(getOfferTransferCaches(userVisit).getSourceTransferCache().getSourceTransfer(source));
        });

        return sourceTransfers;
    }

    public List<SourceTransfer> getSourceTransfers(UserVisit userVisit) {
        return getSourceTransfers(userVisit, getSources());
    }

    private void updateSourceFromValue(SourceDetailValue sourceDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(sourceDetailValue.hasBeenModified()) {
            Source source = SourceFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    sourceDetailValue.getSourcePK());
            SourceDetail sourceDetail = source.getActiveDetailForUpdate();

            sourceDetail.setThruTime(session.START_TIME_LONG);
            sourceDetail.store();

            SourcePK sourcePK = sourceDetail.getSourcePK(); // Do not update
            String sourceName = sourceDetailValue.getSourceName();
            OfferUse offerUse = sourceDetail.getOfferUse(); // Do not update
            Boolean isDefault = sourceDetailValue.getIsDefault();
            Integer sortOrder = sourceDetailValue.getSortOrder();

            if(checkDefault) {
                Source defaultSource = getDefaultSource();
                boolean defaultFound = defaultSource != null && !defaultSource.equals(source);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    SourceDetailValue defaultSourceDetailValue = getDefaultSourceDetailValueForUpdate();

                    defaultSourceDetailValue.setIsDefault(Boolean.FALSE);
                    updateSourceFromValue(defaultSourceDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            sourceDetail = SourceDetailFactory.getInstance().create(sourcePK, sourceName, offerUse.getPrimaryKey(), isDefault,
                    sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            source.setActiveDetail(sourceDetail);
            source.setLastDetail(sourceDetail);

            sendEventUsingNames(offerUse.getLastDetail().getOfferPK(), EventTypes.MODIFY.name(), source.getPrimaryKey(),
                    EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void updateSourceFromValue(SourceDetailValue sourceDetailValue, BasePK updatedBy) {
        updateSourceFromValue(sourceDetailValue, true, updatedBy);
    }

    public void deleteSource(Source source, BasePK deletedBy) {
        SourceDetail sourceDetail = source.getLastDetailForUpdate();
        sourceDetail.setThruTime(session.START_TIME_LONG);
        source.setActiveDetail(null);
        source.store();

        // Check for default, and pick one if necessary
        Source defaultSource = getDefaultSource();
        if(defaultSource == null) {
            List<Source> sources = getSourcesForUpdate();

            if(!sources.isEmpty()) {
                Iterator<Source> iter = sources.iterator();
                if(iter.hasNext()) {
                    defaultSource = iter.next();
                }
                SourceDetailValue sourceDetailValue = Objects.requireNonNull(defaultSource).getLastDetailForUpdate().getSourceDetailValue().clone();

                sourceDetailValue.setIsDefault(Boolean.TRUE);
                updateSourceFromValue(sourceDetailValue, false, deletedBy);
            }
        }

        sendEventUsingNames(source.getLastDetail().getOfferUse().getLastDetail().getOfferPK(),
                EventTypes.MODIFY.name(), source.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
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
