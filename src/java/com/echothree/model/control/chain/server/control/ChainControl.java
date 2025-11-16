// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.model.control.chain.server.control;

import com.echothree.model.control.chain.common.ChainConstants;
import com.echothree.model.control.chain.common.choice.ChainActionSetChoicesBean;
import com.echothree.model.control.chain.common.choice.ChainActionTypeChoicesBean;
import com.echothree.model.control.chain.common.choice.ChainChoicesBean;
import com.echothree.model.control.chain.common.choice.ChainKindChoicesBean;
import com.echothree.model.control.chain.common.choice.ChainTypeChoicesBean;
import com.echothree.model.control.chain.common.transfer.ChainActionChainActionSetTransfer;
import com.echothree.model.control.chain.common.transfer.ChainActionDescriptionTransfer;
import com.echothree.model.control.chain.common.transfer.ChainActionLetterTransfer;
import com.echothree.model.control.chain.common.transfer.ChainActionSetDescriptionTransfer;
import com.echothree.model.control.chain.common.transfer.ChainActionSetTransfer;
import com.echothree.model.control.chain.common.transfer.ChainActionSurveyTransfer;
import com.echothree.model.control.chain.common.transfer.ChainActionTransfer;
import com.echothree.model.control.chain.common.transfer.ChainActionTypeDescriptionTransfer;
import com.echothree.model.control.chain.common.transfer.ChainActionTypeTransfer;
import com.echothree.model.control.chain.common.transfer.ChainDescriptionTransfer;
import com.echothree.model.control.chain.common.transfer.ChainEntityRoleTypeDescriptionTransfer;
import com.echothree.model.control.chain.common.transfer.ChainEntityRoleTypeTransfer;
import com.echothree.model.control.chain.common.transfer.ChainInstanceEntityRoleTransfer;
import com.echothree.model.control.chain.common.transfer.ChainInstanceStatusTransfer;
import com.echothree.model.control.chain.common.transfer.ChainInstanceTransfer;
import com.echothree.model.control.chain.common.transfer.ChainKindDescriptionTransfer;
import com.echothree.model.control.chain.common.transfer.ChainKindTransfer;
import com.echothree.model.control.chain.common.transfer.ChainTransfer;
import com.echothree.model.control.chain.common.transfer.ChainTypeDescriptionTransfer;
import com.echothree.model.control.chain.common.transfer.ChainTypeTransfer;
import com.echothree.model.control.chain.server.transfer.ChainTransferCaches;
import com.echothree.model.control.contactlist.server.control.ContactListControl;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.letter.server.control.LetterControl;
import com.echothree.model.control.offer.server.control.OfferControl;
import com.echothree.model.data.chain.server.entity.Chain;
import com.echothree.model.data.chain.server.entity.ChainAction;
import com.echothree.model.data.chain.server.entity.ChainActionChainActionSet;
import com.echothree.model.data.chain.server.entity.ChainActionDescription;
import com.echothree.model.data.chain.server.entity.ChainActionLetter;
import com.echothree.model.data.chain.server.entity.ChainActionSet;
import com.echothree.model.data.chain.server.entity.ChainActionSetDescription;
import com.echothree.model.data.chain.server.entity.ChainActionSurvey;
import com.echothree.model.data.chain.server.entity.ChainActionType;
import com.echothree.model.data.chain.server.entity.ChainActionTypeDescription;
import com.echothree.model.data.chain.server.entity.ChainActionTypeUse;
import com.echothree.model.data.chain.server.entity.ChainDescription;
import com.echothree.model.data.chain.server.entity.ChainEntityRoleType;
import com.echothree.model.data.chain.server.entity.ChainEntityRoleTypeDescription;
import com.echothree.model.data.chain.server.entity.ChainInstance;
import com.echothree.model.data.chain.server.entity.ChainInstanceEntityRole;
import com.echothree.model.data.chain.server.entity.ChainInstanceStatus;
import com.echothree.model.data.chain.server.entity.ChainKind;
import com.echothree.model.data.chain.server.entity.ChainKindDescription;
import com.echothree.model.data.chain.server.entity.ChainType;
import com.echothree.model.data.chain.server.entity.ChainTypeDescription;
import com.echothree.model.data.chain.server.factory.ChainActionChainActionSetFactory;
import com.echothree.model.data.chain.server.factory.ChainActionDescriptionFactory;
import com.echothree.model.data.chain.server.factory.ChainActionDetailFactory;
import com.echothree.model.data.chain.server.factory.ChainActionFactory;
import com.echothree.model.data.chain.server.factory.ChainActionLetterFactory;
import com.echothree.model.data.chain.server.factory.ChainActionSetDescriptionFactory;
import com.echothree.model.data.chain.server.factory.ChainActionSetDetailFactory;
import com.echothree.model.data.chain.server.factory.ChainActionSetFactory;
import com.echothree.model.data.chain.server.factory.ChainActionSurveyFactory;
import com.echothree.model.data.chain.server.factory.ChainActionTypeDescriptionFactory;
import com.echothree.model.data.chain.server.factory.ChainActionTypeDetailFactory;
import com.echothree.model.data.chain.server.factory.ChainActionTypeFactory;
import com.echothree.model.data.chain.server.factory.ChainActionTypeUseFactory;
import com.echothree.model.data.chain.server.factory.ChainDescriptionFactory;
import com.echothree.model.data.chain.server.factory.ChainDetailFactory;
import com.echothree.model.data.chain.server.factory.ChainEntityRoleTypeDescriptionFactory;
import com.echothree.model.data.chain.server.factory.ChainEntityRoleTypeDetailFactory;
import com.echothree.model.data.chain.server.factory.ChainEntityRoleTypeFactory;
import com.echothree.model.data.chain.server.factory.ChainFactory;
import com.echothree.model.data.chain.server.factory.ChainInstanceDetailFactory;
import com.echothree.model.data.chain.server.factory.ChainInstanceEntityRoleFactory;
import com.echothree.model.data.chain.server.factory.ChainInstanceFactory;
import com.echothree.model.data.chain.server.factory.ChainInstanceStatusFactory;
import com.echothree.model.data.chain.server.factory.ChainKindDescriptionFactory;
import com.echothree.model.data.chain.server.factory.ChainKindDetailFactory;
import com.echothree.model.data.chain.server.factory.ChainKindFactory;
import com.echothree.model.data.chain.server.factory.ChainTypeDescriptionFactory;
import com.echothree.model.data.chain.server.factory.ChainTypeDetailFactory;
import com.echothree.model.data.chain.server.factory.ChainTypeFactory;
import com.echothree.model.data.chain.server.value.ChainActionChainActionSetValue;
import com.echothree.model.data.chain.server.value.ChainActionDescriptionValue;
import com.echothree.model.data.chain.server.value.ChainActionDetailValue;
import com.echothree.model.data.chain.server.value.ChainActionLetterValue;
import com.echothree.model.data.chain.server.value.ChainActionSetDescriptionValue;
import com.echothree.model.data.chain.server.value.ChainActionSetDetailValue;
import com.echothree.model.data.chain.server.value.ChainActionSurveyValue;
import com.echothree.model.data.chain.server.value.ChainActionTypeDescriptionValue;
import com.echothree.model.data.chain.server.value.ChainActionTypeDetailValue;
import com.echothree.model.data.chain.server.value.ChainDescriptionValue;
import com.echothree.model.data.chain.server.value.ChainDetailValue;
import com.echothree.model.data.chain.server.value.ChainEntityRoleTypeDescriptionValue;
import com.echothree.model.data.chain.server.value.ChainEntityRoleTypeDetailValue;
import com.echothree.model.data.chain.server.value.ChainInstanceDetailValue;
import com.echothree.model.data.chain.server.value.ChainKindDescriptionValue;
import com.echothree.model.data.chain.server.value.ChainKindDetailValue;
import com.echothree.model.data.chain.server.value.ChainTypeDescriptionValue;
import com.echothree.model.data.chain.server.value.ChainTypeDetailValue;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.letter.server.entity.Letter;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.survey.server.entity.Survey;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseModelControl;
import javax.enterprise.inject.spi.CDI;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class ChainControl
        extends BaseModelControl {
    
    /** Creates a new instance of ChainControl */
    protected ChainControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Chain Transfer Caches
    // --------------------------------------------------------------------------------
    
    private ChainTransferCaches chainTransferCaches;
    
    public ChainTransferCaches getChainTransferCaches() {
        if(chainTransferCaches == null) {
            chainTransferCaches = CDI.current().select(ChainTransferCaches.class).get();
        }
        
        return chainTransferCaches;
    }
    
    // --------------------------------------------------------------------------------
    //   Chain Kinds
    // --------------------------------------------------------------------------------

    public ChainKind createChainKind(String chainKindName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultChainKind = getDefaultChainKind();
        var defaultFound = defaultChainKind != null;

        if(defaultFound && isDefault) {
            var defaultChainKindDetailValue = getDefaultChainKindDetailValueForUpdate();

            defaultChainKindDetailValue.setIsDefault(false);
            updateChainKindFromValue(defaultChainKindDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var chainKind = ChainKindFactory.getInstance().create();
        var chainKindDetail = ChainKindDetailFactory.getInstance().create(chainKind, chainKindName, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        chainKind = ChainKindFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                chainKind.getPrimaryKey());
        chainKind.setActiveDetail(chainKindDetail);
        chainKind.setLastDetail(chainKindDetail);
        chainKind.store();

        sendEvent(chainKind.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return chainKind;
    }

    private static final Map<EntityPermission, String> getChainKindByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM chainkinds, chainkinddetails "
                + "WHERE chnk_activedetailid = chnkdt_chainkinddetailid AND chnkdt_chainkindname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM chainkinds, chainkinddetails "
                + "WHERE chnk_activedetailid = chnkdt_chainkinddetailid AND chnkdt_chainkindname = ? "
                + "FOR UPDATE");
        getChainKindByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private ChainKind getChainKindByName(String chainKindName, EntityPermission entityPermission) {
        return ChainKindFactory.getInstance().getEntityFromQuery(entityPermission, getChainKindByNameQueries,
                chainKindName);
    }

    public ChainKind getChainKindByName(String chainKindName) {
        return getChainKindByName(chainKindName, EntityPermission.READ_ONLY);
    }

    public ChainKind getChainKindByNameForUpdate(String chainKindName) {
        return getChainKindByName(chainKindName, EntityPermission.READ_WRITE);
    }

    public ChainKindDetailValue getChainKindDetailValueForUpdate(ChainKind chainKind) {
        return chainKind == null? null: chainKind.getLastDetailForUpdate().getChainKindDetailValue().clone();
    }

    public ChainKindDetailValue getChainKindDetailValueByNameForUpdate(String chainKindName) {
        return getChainKindDetailValueForUpdate(getChainKindByNameForUpdate(chainKindName));
    }

    private static final Map<EntityPermission, String> getDefaultChainKindQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM chainkinds, chainkinddetails "
                + "WHERE chnk_activedetailid = chnkdt_chainkinddetailid AND chnkdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM chainkinds, chainkinddetails "
                + "WHERE chnk_activedetailid = chnkdt_chainkinddetailid AND chnkdt_isdefault = 1 "
                + "FOR UPDATE");
        getDefaultChainKindQueries = Collections.unmodifiableMap(queryMap);
    }

    private ChainKind getDefaultChainKind(EntityPermission entityPermission) {
        return ChainKindFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultChainKindQueries);
    }

    public ChainKind getDefaultChainKind() {
        return getDefaultChainKind(EntityPermission.READ_ONLY);
    }

    public ChainKind getDefaultChainKindForUpdate() {
        return getDefaultChainKind(EntityPermission.READ_WRITE);
    }

    public ChainKindDetailValue getDefaultChainKindDetailValueForUpdate() {
        return getDefaultChainKind(EntityPermission.READ_WRITE).getLastDetailForUpdate().getChainKindDetailValue();
    }

    private static final Map<EntityPermission, String> getChainKindsQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM chainkinds, chainkinddetails "
                + "WHERE chnk_activedetailid = chnkdt_chainkinddetailid "
                + "ORDER BY chnkdt_sortorder, chnkdt_chainkindname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM chainkinds, chainkinddetails "
                + "WHERE chnk_activedetailid = chnkdt_chainkinddetailid "
                + "FOR UPDATE");
        getChainKindsQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ChainKind> getChainKinds(EntityPermission entityPermission) {
        return ChainKindFactory.getInstance().getEntitiesFromQuery(entityPermission, getChainKindsQueries);
    }

    public List<ChainKind> getChainKinds() {
        return getChainKinds(EntityPermission.READ_ONLY);
    }

    public List<ChainKind> getChainKindsForUpdate() {
        return getChainKinds(EntityPermission.READ_WRITE);
    }

    public ChainKindChoicesBean getChainKindChoices(String defaultChainKindChoice, Language language, boolean allowNullChoice) {
        var chainKinds = getChainKinds();
        var size = chainKinds.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultChainKindChoice == null) {
                defaultValue = "";
            }
        }

        for(var chainKind : chainKinds) {
            var chainKindDetail = chainKind.getLastDetail();

            var label = getBestChainKindDescription(chainKind, language);
            var value = chainKindDetail.getChainKindName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultChainKindChoice != null && defaultChainKindChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && chainKindDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ChainKindChoicesBean(labels, values, defaultValue);
    }

    public ChainKindTransfer getChainKindTransfer(UserVisit userVisit, ChainKind chainKind) {
        return getChainTransferCaches().getChainKindTransferCache().getChainKindTransfer(userVisit, chainKind);
    }

    public List<ChainKindTransfer> getChainKindTransfers(UserVisit userVisit) {
        var chainKinds = getChainKinds();
        List<ChainKindTransfer> chainKindTransfers = new ArrayList<>(chainKinds.size());
        var chainKindTransferCache = getChainTransferCaches().getChainKindTransferCache();

        chainKinds.forEach((chainKind) ->
                chainKindTransfers.add(chainKindTransferCache.getChainKindTransfer(userVisit, chainKind))
        );

        return chainKindTransfers;
    }

    private void updateChainKindFromValue(ChainKindDetailValue chainKindDetailValue, boolean checkDefault, BasePK updatedBy) {
        var chainKind = ChainKindFactory.getInstance().getEntityFromPK(session,
                EntityPermission.READ_WRITE, chainKindDetailValue.getChainKindPK());
        var chainKindDetail = chainKind.getActiveDetailForUpdate();

        chainKindDetail.setThruTime(session.START_TIME_LONG);
        chainKindDetail.store();

        var chainKindPK = chainKindDetail.getChainKindPK();
        var chainKindName = chainKindDetailValue.getChainKindName();
        var isDefault = chainKindDetailValue.getIsDefault();
        var sortOrder = chainKindDetailValue.getSortOrder();

        if(checkDefault) {
            var defaultChainKind = getDefaultChainKind();
            var defaultFound = defaultChainKind != null && !defaultChainKind.equals(chainKind);

            if(isDefault && defaultFound) {
                // If I'm the default, and a default already existed...
                var defaultChainKindDetailValue = getDefaultChainKindDetailValueForUpdate();

                defaultChainKindDetailValue.setIsDefault(false);
                updateChainKindFromValue(defaultChainKindDetailValue, false, updatedBy);
            } else if(!isDefault && !defaultFound) {
                // If I'm not the default, and no other default exists...
                isDefault = true;
            }
        }

        chainKindDetail = ChainKindDetailFactory.getInstance().create(chainKindPK, chainKindName, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        chainKind.setActiveDetail(chainKindDetail);
        chainKind.setLastDetail(chainKindDetail);
        chainKind.store();

        sendEvent(chainKindPK, EventTypes.MODIFY, null, null, updatedBy);
    }

    public void updateChainKindFromValue(ChainKindDetailValue chainKindDetailValue, BasePK updatedBy) {
        updateChainKindFromValue(chainKindDetailValue, true, updatedBy);
    }

    public void deleteChainKind(ChainKind chainKind, BasePK deletedBy) {
        deleteChainTypesByChainKind(chainKind, deletedBy);
        deleteChainKindDescriptionsByChainKind(chainKind, deletedBy);

        var chainKindDetail = chainKind.getLastDetailForUpdate();
        chainKindDetail.setThruTime(session.START_TIME_LONG);
        chainKind.setActiveDetail(null);
        chainKind.store();

        // Check for default, and pick one if necessary
        var defaultChainKind = getDefaultChainKind();
        if(defaultChainKind == null) {
            var chainKinds = getChainKindsForUpdate();

            if(!chainKinds.isEmpty()) {
                var iter = chainKinds.iterator();
                if(iter.hasNext()) {
                    defaultChainKind = iter.next();
                }
                var chainKindDetailValue = Objects.requireNonNull(defaultChainKind).getLastDetailForUpdate().getChainKindDetailValue().clone();

                chainKindDetailValue.setIsDefault(true);
                updateChainKindFromValue(chainKindDetailValue, false, deletedBy);
            }
        }

        sendEvent(chainKind.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Chain Kind Descriptions
    // --------------------------------------------------------------------------------

    public ChainKindDescription createChainKindDescription(ChainKind chainKind, Language language, String description,
            BasePK createdBy) {
        var chainKindDescription = ChainKindDescriptionFactory.getInstance().create(chainKind,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(chainKind.getPrimaryKey(), EventTypes.MODIFY, chainKindDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return chainKindDescription;
    }

    private static final Map<EntityPermission, String> getChainKindDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM chainkinddescriptions "
                + "WHERE chnkd_chnk_chainkindid = ? AND chnkd_lang_languageid = ? AND chnkd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM chainkinddescriptions "
                + "WHERE chnkd_chnk_chainkindid = ? AND chnkd_lang_languageid = ? AND chnkd_thrutime = ? "
                + "FOR UPDATE");
        getChainKindDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private ChainKindDescription getChainKindDescription(ChainKind chainKind, Language language, EntityPermission entityPermission) {
        return ChainKindDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getChainKindDescriptionQueries,
                chainKind, language, Session.MAX_TIME);
    }

    public ChainKindDescription getChainKindDescription(ChainKind chainKind, Language language) {
        return getChainKindDescription(chainKind, language, EntityPermission.READ_ONLY);
    }

    public ChainKindDescription getChainKindDescriptionForUpdate(ChainKind chainKind, Language language) {
        return getChainKindDescription(chainKind, language, EntityPermission.READ_WRITE);
    }

    public ChainKindDescriptionValue getChainKindDescriptionValue(ChainKindDescription chainKindDescription) {
        return chainKindDescription == null? null: chainKindDescription.getChainKindDescriptionValue().clone();
    }

    public ChainKindDescriptionValue getChainKindDescriptionValueForUpdate(ChainKind chainKind, Language language) {
        return getChainKindDescriptionValue(getChainKindDescriptionForUpdate(chainKind, language));
    }

    private static final Map<EntityPermission, String> getChainKindDescriptionsByChainKindQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM chainkinddescriptions, languages "
                + "WHERE chnkd_chnk_chainkindid = ? AND chnkd_thrutime = ? AND chnkd_lang_languageid = lang_languageid "
                + "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM chainkinddescriptions "
                + "WHERE chnkd_chnk_chainkindid = ? AND chnkd_thrutime = ? "
                + "FOR UPDATE");
        getChainKindDescriptionsByChainKindQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ChainKindDescription> getChainKindDescriptionsByChainKind(ChainKind chainKind, EntityPermission entityPermission) {
        return ChainKindDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getChainKindDescriptionsByChainKindQueries,
                chainKind, Session.MAX_TIME);
    }

    public List<ChainKindDescription> getChainKindDescriptionsByChainKind(ChainKind chainKind) {
        return getChainKindDescriptionsByChainKind(chainKind, EntityPermission.READ_ONLY);
    }

    public List<ChainKindDescription> getChainKindDescriptionsByChainKindForUpdate(ChainKind chainKind) {
        return getChainKindDescriptionsByChainKind(chainKind, EntityPermission.READ_WRITE);
    }

    public String getBestChainKindDescription(ChainKind chainKind, Language language) {
        String description;
        var chainKindDescription = getChainKindDescription(chainKind, language);

        if(chainKindDescription == null && !language.getIsDefault()) {
            chainKindDescription = getChainKindDescription(chainKind, partyControl.getDefaultLanguage());
        }

        if(chainKindDescription == null) {
            description = chainKind.getLastDetail().getChainKindName();
        } else {
            description = chainKindDescription.getDescription();
        }

        return description;
    }

    public ChainKindDescriptionTransfer getChainKindDescriptionTransfer(UserVisit userVisit, ChainKindDescription chainKindDescription) {
        return getChainTransferCaches().getChainKindDescriptionTransferCache().getChainKindDescriptionTransfer(userVisit, chainKindDescription);
    }

    public List<ChainKindDescriptionTransfer> getChainKindDescriptionTransfersByChainKind(UserVisit userVisit, ChainKind chainKind) {
        var chainKindDescriptions = getChainKindDescriptionsByChainKind(chainKind);
        List<ChainKindDescriptionTransfer> chainKindDescriptionTransfers = new ArrayList<>(chainKindDescriptions.size());

        chainKindDescriptions.forEach((chainKindDescription) -> {
            chainKindDescriptionTransfers.add(getChainTransferCaches().getChainKindDescriptionTransferCache().getChainKindDescriptionTransfer(userVisit, chainKindDescription));
        });

        return chainKindDescriptionTransfers;
    }

    public void updateChainKindDescriptionFromValue(ChainKindDescriptionValue chainKindDescriptionValue, BasePK updatedBy) {
        if(chainKindDescriptionValue.hasBeenModified()) {
            var chainKindDescription = ChainKindDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     chainKindDescriptionValue.getPrimaryKey());

            chainKindDescription.setThruTime(session.START_TIME_LONG);
            chainKindDescription.store();

            var chainKind = chainKindDescription.getChainKind();
            var language = chainKindDescription.getLanguage();
            var description = chainKindDescriptionValue.getDescription();

            chainKindDescription = ChainKindDescriptionFactory.getInstance().create(chainKind, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(chainKind.getPrimaryKey(), EventTypes.MODIFY, chainKindDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteChainKindDescription(ChainKindDescription chainKindDescription, BasePK deletedBy) {
        chainKindDescription.setThruTime(session.START_TIME_LONG);

        sendEvent(chainKindDescription.getChainKindPK(), EventTypes.MODIFY, chainKindDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteChainKindDescriptionsByChainKind(ChainKind chainKind, BasePK deletedBy) {
        var chainKindDescriptions = getChainKindDescriptionsByChainKindForUpdate(chainKind);

        chainKindDescriptions.forEach((chainKindDescription) -> 
                deleteChainKindDescription(chainKindDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Chain Types
    // --------------------------------------------------------------------------------

    public ChainType createChainType(ChainKind chainKind, String chainTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultChainType = getDefaultChainType(chainKind);
        var defaultFound = defaultChainType != null;

        if(defaultFound && isDefault) {
            var defaultChainTypeDetailValue = getDefaultChainTypeDetailValueForUpdate(chainKind);

            defaultChainTypeDetailValue.setIsDefault(false);
            updateChainTypeFromValue(defaultChainTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var chainType = ChainTypeFactory.getInstance().create();
        var chainTypeDetail = ChainTypeDetailFactory.getInstance().create(session, chainType, chainKind, chainTypeName, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        chainType = ChainTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                chainType.getPrimaryKey());
        chainType.setActiveDetail(chainTypeDetail);
        chainType.setLastDetail(chainTypeDetail);
        chainType.store();

        sendEvent(chainType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return chainType;
    }

    private static final Map<EntityPermission, String> getChainTypesByChainKindQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM chaintypes, chaintypedetails "
                + "WHERE chntyp_activedetailid = chntypdt_chaintypedetailid AND chntypdt_chnk_chainkindid = ? "
                + "ORDER BY chntypdt_sortorder, chntypdt_chaintypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM chaintypes, chaintypedetails "
                + "WHERE chntyp_activedetailid = chntypdt_chaintypedetailid AND chntypdt_chnk_chainkindid = ? "
                + "FOR UPDATE");
        getChainTypesByChainKindQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ChainType> getChainTypesByChainKind(ChainKind chainKind, EntityPermission entityPermission) {
        return ChainTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getChainTypesByChainKindQueries,
                chainKind);
    }

    public List<ChainType> getChainTypesByChainKind(ChainKind chainKind) {
        return getChainTypesByChainKind(chainKind, EntityPermission.READ_ONLY);
    }

    public List<ChainType> getChainTypesByChainKindForUpdate(ChainKind chainKind) {
        return getChainTypesByChainKind(chainKind, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getChainTypesByEntityTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM chainkinds, chainkinddetails, chaintypes, chaintypedetails "
                + "WHERE chntyp_activedetailid = chntypdt_chaintypedetailid AND chnertypdt_ent_entitytypeid = ? "
                + "AND chntypdt_chnk_chainkindid = chnk_chainkindid AND chnk_lastdetailid = chnkdt_chainkinddetailid "
                + "ORDER BY chnkdt_sortorder, chnkdt_chainkindname, chntypdt_sortorder, chntypdt_chaintypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM chaintypes, chaintypedetails "
                + "WHERE chntyp_activedetailid = chntypdt_chaintypedetailid AND chnertypdt_ent_entitytypeid = ? "
                + "FOR UPDATE");
        getChainTypesByEntityTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ChainType> getChainTypesByEntityType(EntityType entityType, EntityPermission entityPermission) {
        return ChainTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getChainTypesByEntityTypeQueries,
                entityType);
    }

    public List<ChainType> getChainTypesByEntityType(EntityType entityType) {
        return getChainTypesByEntityType(entityType, EntityPermission.READ_ONLY);
    }

    public List<ChainType> getChainTypesByEntityTypeForUpdate(EntityType entityType) {
        return getChainTypesByEntityType(entityType, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getDefaultChainTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM chaintypes, chaintypedetails "
                + "WHERE chntyp_activedetailid = chntypdt_chaintypedetailid "
                + "AND chntypdt_chnk_chainkindid = ? AND chntypdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM chaintypes, chaintypedetails "
                + "WHERE chntyp_activedetailid = chntypdt_chaintypedetailid "
                + "AND chntypdt_chnk_chainkindid = ? AND chntypdt_isdefault = 1 "
                + "FOR UPDATE");
        getDefaultChainTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private ChainType getDefaultChainType(ChainKind chainKind, EntityPermission entityPermission) {
        return ChainTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultChainTypeQueries,
                chainKind);
    }

    public ChainType getDefaultChainType(ChainKind chainKind) {
        return getDefaultChainType(chainKind, EntityPermission.READ_ONLY);
    }

    public ChainType getDefaultChainTypeForUpdate(ChainKind chainKind) {
        return getDefaultChainType(chainKind, EntityPermission.READ_WRITE);
    }

    public ChainTypeDetailValue getDefaultChainTypeDetailValueForUpdate(ChainKind chainKind) {
        return getDefaultChainTypeForUpdate(chainKind).getLastDetailForUpdate().getChainTypeDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getChainTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM chaintypes, chaintypedetails "
                + "WHERE chntyp_activedetailid = chntypdt_chaintypedetailid "
                + "AND chntypdt_chnk_chainkindid = ? AND chntypdt_chaintypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM chaintypes, chaintypedetails "
                + "WHERE chntyp_activedetailid = chntypdt_chaintypedetailid "
                + "AND chntypdt_chnk_chainkindid = ? AND chntypdt_chaintypename = ? "
                + "FOR UPDATE");
        getChainTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private ChainType getChainTypeByName(ChainKind chainKind, String chainTypeName, EntityPermission entityPermission) {
        return ChainTypeFactory.getInstance().getEntityFromQuery(entityPermission, getChainTypeByNameQueries,
                chainKind, chainTypeName);
    }

    public ChainType getChainTypeByName(ChainKind chainKind, String chainTypeName) {
        return getChainTypeByName(chainKind, chainTypeName, EntityPermission.READ_ONLY);
    }

    public ChainType getChainTypeByNameForUpdate(ChainKind chainKind, String chainTypeName) {
        return getChainTypeByName(chainKind, chainTypeName, EntityPermission.READ_WRITE);
    }

    public ChainTypeDetailValue getChainTypeDetailValueForUpdate(ChainType chainType) {
        return chainType == null? null: chainType.getLastDetailForUpdate().getChainTypeDetailValue().clone();
    }

    public ChainTypeDetailValue getChainTypeDetailValueByNameForUpdate(ChainKind chainKind, String chainTypeName) {
        return getChainTypeDetailValueForUpdate(getChainTypeByNameForUpdate(chainKind, chainTypeName));
    }

    public ChainTypeChoicesBean getChainTypeChoices(String defaultChainTypeChoice, Language language,
            boolean allowNullChoice, ChainKind chainKind) {
        var chainTypes = getChainTypesByChainKind(chainKind);
        var size = chainTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultChainTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var chainType : chainTypes) {
            var chainTypeDetail = chainType.getLastDetail();
            var label = getBestChainTypeDescription(chainType, language);
            var value = chainTypeDetail.getChainTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultChainTypeChoice != null && defaultChainTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && chainTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ChainTypeChoicesBean(labels, values, defaultValue);
    }

    public ChainTypeTransfer getChainTypeTransfer(UserVisit userVisit, ChainType chainType) {
        return getChainTransferCaches().getChainTypeTransferCache().getChainTypeTransfer(userVisit, chainType);
    }

    public List<ChainTypeTransfer> getChainTypeTransfersByChainKind(UserVisit userVisit, ChainKind chainKind) {
        var chainTypes = getChainTypesByChainKind(chainKind);
        List<ChainTypeTransfer> chainTypeTransfers = new ArrayList<>(chainTypes.size());
        var chainTypeTransferCache = getChainTransferCaches().getChainTypeTransferCache();

        chainTypes.forEach((chainType) ->
                chainTypeTransfers.add(chainTypeTransferCache.getChainTypeTransfer(userVisit, chainType))
        );

        return chainTypeTransfers;
    }

    private void updateChainTypeFromValue(ChainTypeDetailValue chainTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(chainTypeDetailValue.hasBeenModified()) {
            var chainType = ChainTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     chainTypeDetailValue.getChainTypePK());
            var chainTypeDetail = chainType.getActiveDetailForUpdate();

            chainTypeDetail.setThruTime(session.START_TIME_LONG);
            chainTypeDetail.store();

            var chainTypePK = chainTypeDetail.getChainTypePK();
            var chainKind = chainTypeDetail.getChainKind();
            var chainKindPK = chainKind.getPrimaryKey();
            var chainTypeName = chainTypeDetailValue.getChainTypeName();
            var isDefault = chainTypeDetailValue.getIsDefault();
            var sortOrder = chainTypeDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultChainType = getDefaultChainType(chainKind);
                var defaultFound = defaultChainType != null && !defaultChainType.equals(chainType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultChainTypeDetailValue = getDefaultChainTypeDetailValueForUpdate(chainKind);

                    defaultChainTypeDetailValue.setIsDefault(false);
                    updateChainTypeFromValue(defaultChainTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            chainTypeDetail = ChainTypeDetailFactory.getInstance().create(chainTypePK, chainKindPK, chainTypeName, isDefault, sortOrder,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            chainType.setActiveDetail(chainTypeDetail);
            chainType.setLastDetail(chainTypeDetail);

            sendEvent(chainTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateChainTypeFromValue(ChainTypeDetailValue chainTypeDetailValue, BasePK updatedBy) {
        updateChainTypeFromValue(chainTypeDetailValue, true, updatedBy);
    }

    public void deleteChainType(ChainType chainType, BasePK deletedBy) {
        deleteChainsByChainType(chainType, deletedBy);
        deleteChainEntityRoleTypesByChainType(chainType, deletedBy);
        deleteChainTypeDescriptionsByChainType(chainType, deletedBy);

        var chainTypeDetail = chainType.getLastDetailForUpdate();
        chainTypeDetail.setThruTime(session.START_TIME_LONG);
        chainType.setActiveDetail(null);
        chainType.store();

        // Check for default, and pick one if necessary
        var chainKind = chainTypeDetail.getChainKind();
        var defaultChainType = getDefaultChainType(chainKind);
        if(defaultChainType == null) {
            var chainTypes = getChainTypesByChainKind(chainKind);

            if(!chainTypes.isEmpty()) {
                var iter = chainTypes.iterator();
                if(iter.hasNext()) {
                    defaultChainType = iter.next();
                }
                var chainTypeDetailValue = Objects.requireNonNull(defaultChainType).getLastDetailForUpdate().getChainTypeDetailValue().clone();

                chainTypeDetailValue.setIsDefault(true);
                updateChainTypeFromValue(chainTypeDetailValue, false, deletedBy);
            }
        }

        sendEvent(chainType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteChainTypes(List<ChainType> chainTypes, BasePK deletedBy) {
        chainTypes.forEach((chainType) -> 
                deleteChainType(chainType, deletedBy)
        );
    }

    public void deleteChainTypesByChainKind(ChainKind chainKind, BasePK deletedBy) {
        deleteChainTypes(getChainTypesByChainKind(chainKind), deletedBy);
    }

    public void deleteChainTypesByEntityType(EntityType entityType, BasePK deletedBy) {
        deleteChainTypes(getChainTypesByEntityTypeForUpdate(entityType), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Chain Type Descriptions
    // --------------------------------------------------------------------------------

    public ChainTypeDescription createChainTypeDescription(ChainType chainType, Language language, String description,
            BasePK createdBy) {
        var chainTypeDescription = ChainTypeDescriptionFactory.getInstance().create(chainType,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(chainType.getPrimaryKey(), EventTypes.MODIFY, chainTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return chainTypeDescription;
    }

    private static final Map<EntityPermission, String> getChainTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM chaintypedescriptions "
                + "WHERE chntypd_chntyp_chaintypeid = ? AND chntypd_lang_languageid = ? AND chntypd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM chaintypedescriptions "
                + "WHERE chntypd_chntyp_chaintypeid = ? AND chntypd_lang_languageid = ? AND chntypd_thrutime = ? "
                + "FOR UPDATE");
        getChainTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private ChainTypeDescription getChainTypeDescription(ChainType chainType, Language language, EntityPermission entityPermission) {
        return ChainTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getChainTypeDescriptionQueries,
                chainType, language, Session.MAX_TIME);
    }

    public ChainTypeDescription getChainTypeDescription(ChainType chainType, Language language) {
        return getChainTypeDescription(chainType, language, EntityPermission.READ_ONLY);
    }

    public ChainTypeDescription getChainTypeDescriptionForUpdate(ChainType chainType, Language language) {
        return getChainTypeDescription(chainType, language, EntityPermission.READ_WRITE);
    }

    public ChainTypeDescriptionValue getChainTypeDescriptionValue(ChainTypeDescription chainTypeDescription) {
        return chainTypeDescription == null? null: chainTypeDescription.getChainTypeDescriptionValue().clone();
    }

    public ChainTypeDescriptionValue getChainTypeDescriptionValueForUpdate(ChainType chainType, Language language) {
        return getChainTypeDescriptionValue(getChainTypeDescriptionForUpdate(chainType, language));
    }

    private static final Map<EntityPermission, String> getChainTypeDescriptionsByChainTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM chaintypedescriptions, languages "
                + "WHERE chntypd_chntyp_chaintypeid = ? AND chntypd_thrutime = ? AND chntypd_lang_languageid = lang_languageid "
                + "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM chaintypedescriptions "
                + "WHERE chntypd_chntyp_chaintypeid = ? AND chntypd_thrutime = ? "
                + "FOR UPDATE");
        getChainTypeDescriptionsByChainTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ChainTypeDescription> getChainTypeDescriptionsByChainType(ChainType chainType, EntityPermission entityPermission) {
        return ChainTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getChainTypeDescriptionsByChainTypeQueries,
                chainType, Session.MAX_TIME);
    }

    public List<ChainTypeDescription> getChainTypeDescriptionsByChainType(ChainType chainType) {
        return getChainTypeDescriptionsByChainType(chainType, EntityPermission.READ_ONLY);
    }

    public List<ChainTypeDescription> getChainTypeDescriptionsByChainTypeForUpdate(ChainType chainType) {
        return getChainTypeDescriptionsByChainType(chainType, EntityPermission.READ_WRITE);
    }

    public String getBestChainTypeDescription(ChainType chainType, Language language) {
        String description;
        var chainTypeDescription = getChainTypeDescription(chainType, language);

        if(chainTypeDescription == null && !language.getIsDefault()) {
            chainTypeDescription = getChainTypeDescription(chainType, partyControl.getDefaultLanguage());
        }

        if(chainTypeDescription == null) {
            description = chainType.getLastDetail().getChainTypeName();
        } else {
            description = chainTypeDescription.getDescription();
        }

        return description;
    }

    public ChainTypeDescriptionTransfer getChainTypeDescriptionTransfer(UserVisit userVisit, ChainTypeDescription chainTypeDescription) {
        return getChainTransferCaches().getChainTypeDescriptionTransferCache().getChainTypeDescriptionTransfer(userVisit, chainTypeDescription);
    }

    public List<ChainTypeDescriptionTransfer> getChainTypeDescriptionTransfersByChainType(UserVisit userVisit, ChainType chainType) {
        var chainTypeDescriptions = getChainTypeDescriptionsByChainType(chainType);
        List<ChainTypeDescriptionTransfer> chainTypeDescriptionTransfers = new ArrayList<>(chainTypeDescriptions.size());

        chainTypeDescriptions.forEach((chainTypeDescription) -> {
            chainTypeDescriptionTransfers.add(getChainTransferCaches().getChainTypeDescriptionTransferCache().getChainTypeDescriptionTransfer(userVisit, chainTypeDescription));
        });

        return chainTypeDescriptionTransfers;
    }

    public void updateChainTypeDescriptionFromValue(ChainTypeDescriptionValue chainTypeDescriptionValue, BasePK updatedBy) {
        if(chainTypeDescriptionValue.hasBeenModified()) {
            var chainTypeDescription = ChainTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     chainTypeDescriptionValue.getPrimaryKey());

            chainTypeDescription.setThruTime(session.START_TIME_LONG);
            chainTypeDescription.store();

            var chainType = chainTypeDescription.getChainType();
            var language = chainTypeDescription.getLanguage();
            var description = chainTypeDescriptionValue.getDescription();

            chainTypeDescription = ChainTypeDescriptionFactory.getInstance().create(chainType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(chainType.getPrimaryKey(), EventTypes.MODIFY, chainTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteChainTypeDescription(ChainTypeDescription chainTypeDescription, BasePK deletedBy) {
        chainTypeDescription.setThruTime(session.START_TIME_LONG);

        sendEvent(chainTypeDescription.getChainTypePK(), EventTypes.MODIFY, chainTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteChainTypeDescriptionsByChainType(ChainType chainType, BasePK deletedBy) {
        var chainTypeDescriptions = getChainTypeDescriptionsByChainTypeForUpdate(chainType);

        chainTypeDescriptions.forEach((chainTypeDescription) -> 
                deleteChainTypeDescription(chainTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Chain Entity Role Types
    // --------------------------------------------------------------------------------

    public ChainEntityRoleType createChainEntityRoleType(ChainType chainType, String chainEntityRoleTypeName, EntityType entityType, Integer sortOrder,
            BasePK createdBy) {
        var chainEntityRoleType = ChainEntityRoleTypeFactory.getInstance().create();
        var chainEntityRoleTypeDetail = ChainEntityRoleTypeDetailFactory.getInstance().create(session, chainEntityRoleType, chainType,
                chainEntityRoleTypeName, entityType, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        chainEntityRoleType = ChainEntityRoleTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                chainEntityRoleType.getPrimaryKey());
        chainEntityRoleType.setActiveDetail(chainEntityRoleTypeDetail);
        chainEntityRoleType.setLastDetail(chainEntityRoleTypeDetail);
        chainEntityRoleType.store();

        sendEvent(chainEntityRoleType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return chainEntityRoleType;
    }

    private static final Map<EntityPermission, String> getChainEntityRoleTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM chainentityroletypes, chainentityroletypedetails "
                + "WHERE chnertyp_activedetailid = chnertypdt_chainentityroletypedetailid AND chnertypdt_chntyp_chaintypeid = ? "
                + "ORDER BY chnertypdt_sortorder, chnertypdt_chainentityroletypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM chainentityroletypes, chainentityroletypedetails "
                + "WHERE chnertyp_activedetailid = chnertypdt_chainentityroletypedetailid AND chnertypdt_chntyp_chaintypeid = ? "
                + "FOR UPDATE");
        getChainEntityRoleTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ChainEntityRoleType> getChainEntityRoleTypes(ChainType chainType, EntityPermission entityPermission) {
        return ChainEntityRoleTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getChainEntityRoleTypesQueries,
                chainType);
    }

    public List<ChainEntityRoleType> getChainEntityRoleTypes(ChainType chainType) {
        return getChainEntityRoleTypes(chainType, EntityPermission.READ_ONLY);
    }

    public List<ChainEntityRoleType> getChainEntityRoleTypesForUpdate(ChainType chainType) {
        return getChainEntityRoleTypes(chainType, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getChainEntityRoleTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM chainentityroletypes, chainentityroletypedetails "
                + "WHERE chnertyp_activedetailid = chnertypdt_chainentityroletypedetailid "
                + "AND chnertypdt_chntyp_chaintypeid = ? AND chnertypdt_chainentityroletypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM chainentityroletypes, chainentityroletypedetails "
                + "WHERE chnertyp_activedetailid = chnertypdt_chainentityroletypedetailid "
                + "AND chnertypdt_chntyp_chaintypeid = ? AND chnertypdt_chainentityroletypename = ? "
                + "FOR UPDATE");
        getChainEntityRoleTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private ChainEntityRoleType getChainEntityRoleTypeByName(ChainType chainType, String chainEntityRoleTypeName, EntityPermission entityPermission) {
        return ChainEntityRoleTypeFactory.getInstance().getEntityFromQuery(entityPermission, getChainEntityRoleTypeByNameQueries,
                chainType, chainEntityRoleTypeName);
    }

    public ChainEntityRoleType getChainEntityRoleTypeByName(ChainType chainType, String chainEntityRoleTypeName) {
        return getChainEntityRoleTypeByName(chainType, chainEntityRoleTypeName, EntityPermission.READ_ONLY);
    }

    public ChainEntityRoleType getChainEntityRoleTypeByNameForUpdate(ChainType chainType, String chainEntityRoleTypeName) {
        return getChainEntityRoleTypeByName(chainType, chainEntityRoleTypeName, EntityPermission.READ_WRITE);
    }

    public ChainEntityRoleTypeDetailValue getChainEntityRoleTypeDetailValueForUpdate(ChainEntityRoleType chainEntityRoleType) {
        return chainEntityRoleType == null? null: chainEntityRoleType.getLastDetailForUpdate().getChainEntityRoleTypeDetailValue().clone();
    }

    public ChainEntityRoleTypeDetailValue getChainEntityRoleTypeDetailValueByNameForUpdate(ChainType chainType, String chainEntityRoleTypeName) {
        return getChainEntityRoleTypeDetailValueForUpdate(getChainEntityRoleTypeByNameForUpdate(chainType, chainEntityRoleTypeName));
    }

    public ChainEntityRoleTypeTransfer getChainEntityRoleTypeTransfer(UserVisit userVisit, ChainEntityRoleType chainEntityRoleType) {
        return getChainTransferCaches().getChainEntityRoleTypeTransferCache().getChainEntityRoleTypeTransfer(userVisit, chainEntityRoleType);
    }

    public List<ChainEntityRoleTypeTransfer> getChainEntityRoleTypeTransfersByChainType(UserVisit userVisit, ChainType chainType) {
        var chainEntityRoleTypes = getChainEntityRoleTypes(chainType);
        List<ChainEntityRoleTypeTransfer> chainEntityRoleTypeTransfers = new ArrayList<>(chainEntityRoleTypes.size());
        var chainEntityRoleTypeTransferCache = getChainTransferCaches().getChainEntityRoleTypeTransferCache();

        chainEntityRoleTypes.forEach((chainEntityRoleType) ->
                chainEntityRoleTypeTransfers.add(chainEntityRoleTypeTransferCache.getChainEntityRoleTypeTransfer(userVisit, chainEntityRoleType))
        );

        return chainEntityRoleTypeTransfers;
    }

    public void updateChainEntityRoleTypeFromValue(ChainEntityRoleTypeDetailValue chainEntityRoleTypeDetailValue, BasePK updatedBy) {
        if(chainEntityRoleTypeDetailValue.hasBeenModified()) {
            var chainEntityRoleType = ChainEntityRoleTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     chainEntityRoleTypeDetailValue.getChainEntityRoleTypePK());
            var chainEntityRoleTypeDetail = chainEntityRoleType.getActiveDetailForUpdate();

            chainEntityRoleTypeDetail.setThruTime(session.START_TIME_LONG);
            chainEntityRoleTypeDetail.store();

            var chainEntityRoleTypePK = chainEntityRoleTypeDetail.getChainEntityRoleTypePK();
            var chainType = chainEntityRoleTypeDetail.getChainType();
            var chainTypePK = chainType.getPrimaryKey();
            var chainEntityRoleTypeName = chainEntityRoleTypeDetailValue.getChainEntityRoleTypeName();
            var entityTypePK = chainEntityRoleTypeDetailValue.getEntityTypePK();
            var sortOrder = chainEntityRoleTypeDetailValue.getSortOrder();

            chainEntityRoleTypeDetail = ChainEntityRoleTypeDetailFactory.getInstance().create(chainEntityRoleTypePK, chainTypePK, chainEntityRoleTypeName,
                    entityTypePK, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            chainEntityRoleType.setActiveDetail(chainEntityRoleTypeDetail);
            chainEntityRoleType.setLastDetail(chainEntityRoleTypeDetail);

            sendEvent(chainEntityRoleTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void deleteChainEntityRoleType(ChainEntityRoleType chainEntityRoleType, BasePK deletedBy) {
        deleteChainInstanceEntityRolesByChainEntityRoleType(chainEntityRoleType, deletedBy);
        deleteChainEntityRoleTypeDescriptionsByChainEntityRoleType(chainEntityRoleType, deletedBy);

        var chainEntityRoleTypeDetail = chainEntityRoleType.getLastDetailForUpdate();
        chainEntityRoleTypeDetail.setThruTime(session.START_TIME_LONG);
        chainEntityRoleType.setActiveDetail(null);
        chainEntityRoleType.store();

        sendEvent(chainEntityRoleType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteChainEntityRoleTypesByChainType(ChainType chainType, BasePK deletedBy) {
        var chainEntityRoleTypes = getChainEntityRoleTypesForUpdate(chainType);

        chainEntityRoleTypes.forEach((chainEntityRoleType) -> 
                deleteChainEntityRoleType(chainEntityRoleType, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Chain Entity Role Type Descriptions
    // --------------------------------------------------------------------------------

    public ChainEntityRoleTypeDescription createChainEntityRoleTypeDescription(ChainEntityRoleType chainEntityRoleType, Language language, String description,
            BasePK createdBy) {
        var chainEntityRoleTypeDescription = ChainEntityRoleTypeDescriptionFactory.getInstance().create(chainEntityRoleType,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(chainEntityRoleType.getPrimaryKey(), EventTypes.MODIFY, chainEntityRoleTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return chainEntityRoleTypeDescription;
    }

    private static final Map<EntityPermission, String> getChainEntityRoleTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM chainentityroletypedescriptions "
                + "WHERE chnertypd_chnertyp_chainentityroletypeid = ? AND chnertypd_lang_languageid = ? AND chnertypd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM chainentityroletypedescriptions "
                + "WHERE chnertypd_chnertyp_chainentityroletypeid = ? AND chnertypd_lang_languageid = ? AND chnertypd_thrutime = ? "
                + "FOR UPDATE");
        getChainEntityRoleTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private ChainEntityRoleTypeDescription getChainEntityRoleTypeDescription(ChainEntityRoleType chainEntityRoleType, Language language, EntityPermission entityPermission) {
        return ChainEntityRoleTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getChainEntityRoleTypeDescriptionQueries,
                chainEntityRoleType, language, Session.MAX_TIME);
    }

    public ChainEntityRoleTypeDescription getChainEntityRoleTypeDescription(ChainEntityRoleType chainEntityRoleType, Language language) {
        return getChainEntityRoleTypeDescription(chainEntityRoleType, language, EntityPermission.READ_ONLY);
    }

    public ChainEntityRoleTypeDescription getChainEntityRoleTypeDescriptionForUpdate(ChainEntityRoleType chainEntityRoleType, Language language) {
        return getChainEntityRoleTypeDescription(chainEntityRoleType, language, EntityPermission.READ_WRITE);
    }

    public ChainEntityRoleTypeDescriptionValue getChainEntityRoleTypeDescriptionValue(ChainEntityRoleTypeDescription chainEntityRoleTypeDescription) {
        return chainEntityRoleTypeDescription == null? null: chainEntityRoleTypeDescription.getChainEntityRoleTypeDescriptionValue().clone();
    }

    public ChainEntityRoleTypeDescriptionValue getChainEntityRoleTypeDescriptionValueForUpdate(ChainEntityRoleType chainEntityRoleType, Language language) {
        return getChainEntityRoleTypeDescriptionValue(getChainEntityRoleTypeDescriptionForUpdate(chainEntityRoleType, language));
    }

    private static final Map<EntityPermission, String> getChainEntityRoleTypeDescriptionsByChainEntityRoleTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM chainentityroletypedescriptions, languages "
                + "WHERE chnertypd_chnertyp_chainentityroletypeid = ? AND chnertypd_thrutime = ? AND chnertypd_lang_languageid = lang_languageid "
                + "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM chainentityroletypedescriptions "
                + "WHERE chnertypd_chnertyp_chainentityroletypeid = ? AND chnertypd_thrutime = ? "
                + "FOR UPDATE");
        getChainEntityRoleTypeDescriptionsByChainEntityRoleTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ChainEntityRoleTypeDescription> getChainEntityRoleTypeDescriptionsByChainEntityRoleType(ChainEntityRoleType chainEntityRoleType, EntityPermission entityPermission) {
        return ChainEntityRoleTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getChainEntityRoleTypeDescriptionsByChainEntityRoleTypeQueries,
                chainEntityRoleType, Session.MAX_TIME);
    }

    public List<ChainEntityRoleTypeDescription> getChainEntityRoleTypeDescriptionsByChainEntityRoleType(ChainEntityRoleType chainEntityRoleType) {
        return getChainEntityRoleTypeDescriptionsByChainEntityRoleType(chainEntityRoleType, EntityPermission.READ_ONLY);
    }

    public List<ChainEntityRoleTypeDescription> getChainEntityRoleTypeDescriptionsByChainEntityRoleTypeForUpdate(ChainEntityRoleType chainEntityRoleType) {
        return getChainEntityRoleTypeDescriptionsByChainEntityRoleType(chainEntityRoleType, EntityPermission.READ_WRITE);
    }

    public String getBestChainEntityRoleTypeDescription(ChainEntityRoleType chainEntityRoleType, Language language) {
        String description;
        var chainEntityRoleTypeDescription = getChainEntityRoleTypeDescription(chainEntityRoleType, language);

        if(chainEntityRoleTypeDescription == null && !language.getIsDefault()) {
            chainEntityRoleTypeDescription = getChainEntityRoleTypeDescription(chainEntityRoleType, partyControl.getDefaultLanguage());
        }

        if(chainEntityRoleTypeDescription == null) {
            description = chainEntityRoleType.getLastDetail().getChainEntityRoleTypeName();
        } else {
            description = chainEntityRoleTypeDescription.getDescription();
        }

        return description;
    }

    public ChainEntityRoleTypeDescriptionTransfer getChainEntityRoleTypeDescriptionTransfer(UserVisit userVisit, ChainEntityRoleTypeDescription chainEntityRoleTypeDescription) {
        return getChainTransferCaches().getChainEntityRoleTypeDescriptionTransferCache().getChainEntityRoleTypeDescriptionTransfer(userVisit, chainEntityRoleTypeDescription);
    }

    public List<ChainEntityRoleTypeDescriptionTransfer> getChainEntityRoleTypeDescriptionTransfersByChainEntityRoleType(UserVisit userVisit, ChainEntityRoleType chainEntityRoleType) {
        var chainEntityRoleTypeDescriptions = getChainEntityRoleTypeDescriptionsByChainEntityRoleType(chainEntityRoleType);
        List<ChainEntityRoleTypeDescriptionTransfer> chainEntityRoleTypeDescriptionTransfers = new ArrayList<>(chainEntityRoleTypeDescriptions.size());

        chainEntityRoleTypeDescriptions.forEach((chainEntityRoleTypeDescription) -> {
            chainEntityRoleTypeDescriptionTransfers.add(getChainTransferCaches().getChainEntityRoleTypeDescriptionTransferCache().getChainEntityRoleTypeDescriptionTransfer(userVisit, chainEntityRoleTypeDescription));
        });

        return chainEntityRoleTypeDescriptionTransfers;
    }

    public void updateChainEntityRoleTypeDescriptionFromValue(ChainEntityRoleTypeDescriptionValue chainEntityRoleTypeDescriptionValue, BasePK updatedBy) {
        if(chainEntityRoleTypeDescriptionValue.hasBeenModified()) {
            var chainEntityRoleTypeDescription = ChainEntityRoleTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     chainEntityRoleTypeDescriptionValue.getPrimaryKey());

            chainEntityRoleTypeDescription.setThruTime(session.START_TIME_LONG);
            chainEntityRoleTypeDescription.store();

            var chainEntityRoleType = chainEntityRoleTypeDescription.getChainEntityRoleType();
            var language = chainEntityRoleTypeDescription.getLanguage();
            var description = chainEntityRoleTypeDescriptionValue.getDescription();

            chainEntityRoleTypeDescription = ChainEntityRoleTypeDescriptionFactory.getInstance().create(chainEntityRoleType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(chainEntityRoleType.getPrimaryKey(), EventTypes.MODIFY, chainEntityRoleTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteChainEntityRoleTypeDescription(ChainEntityRoleTypeDescription chainEntityRoleTypeDescription, BasePK deletedBy) {
        chainEntityRoleTypeDescription.setThruTime(session.START_TIME_LONG);

        sendEvent(chainEntityRoleTypeDescription.getChainEntityRoleTypePK(), EventTypes.MODIFY, chainEntityRoleTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteChainEntityRoleTypeDescriptionsByChainEntityRoleType(ChainEntityRoleType chainEntityRoleType, BasePK deletedBy) {
        var chainEntityRoleTypeDescriptions = getChainEntityRoleTypeDescriptionsByChainEntityRoleTypeForUpdate(chainEntityRoleType);

        chainEntityRoleTypeDescriptions.forEach((chainEntityRoleTypeDescription) -> 
                deleteChainEntityRoleTypeDescription(chainEntityRoleTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Chains
    // --------------------------------------------------------------------------------

    public Chain createChain(ChainType chainType, String chainName, Sequence chainInstanceSequence, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultChain = getDefaultChain(chainType);
        var defaultFound = defaultChain != null;

        if(defaultFound && isDefault) {
            var defaultChainDetailValue = getDefaultChainDetailValueForUpdate(chainType);

            defaultChainDetailValue.setIsDefault(false);
            updateChainFromValue(defaultChainDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var chain = ChainFactory.getInstance().create();
        var chainDetail = ChainDetailFactory.getInstance().create(session, chain, chainType, chainName, chainInstanceSequence, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        chain = ChainFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, chain.getPrimaryKey());
        chain.setActiveDetail(chainDetail);
        chain.setLastDetail(chainDetail);
        chain.store();

        sendEvent(chain.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return chain;
    }

    private static final Map<EntityPermission, String> getChainsByChainTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM chains, chaindetails "
                + "WHERE chn_activedetailid = chndt_chaindetailid AND chndt_chntyp_chaintypeid = ? "
                + "ORDER BY chndt_sortorder, chndt_chainname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM chains, chaindetails "
                + "WHERE chn_activedetailid = chndt_chaindetailid AND chndt_chntyp_chaintypeid = ? "
                + "FOR UPDATE");
        getChainsByChainTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<Chain> getChainsByChainType(ChainType chainType, EntityPermission entityPermission) {
        return ChainFactory.getInstance().getEntitiesFromQuery(entityPermission, getChainsByChainTypeQueries,
                chainType);
    }

    public List<Chain> getChainsByChainType(ChainType chainType) {
        return getChainsByChainType(chainType, EntityPermission.READ_ONLY);
    }

    public List<Chain> getChainsByChainTypeForUpdate(ChainType chainType) {
        return getChainsByChainType(chainType, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getChainsByChainInstanceSequenceQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM chains, chaindetails, chaintypes, chaintypedetails, chainkinds, chainkinddetails "
                + "WHERE chn_activedetailid = chndt_chaindetailid AND chndt_chaininstancesequenceid = ? "
                + "AND chndt_chntyp_chaintypeid = chntyp_chaintypeid AND chntyp_lastdetailid = chntypdt_chaintypedetailid "
                + "AND chntypdt_chnk_chainkindid = chnk_chainkindid AND chnk_lastdetailid = chnkdt_chainkinddetailid "
                + "ORDER BY chnkdt_sortorder, chnkdt_chainkindname, chntypdt_sortorder, chntypdt_chaintypename, chndt_sortorder, chndt_chainname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM chains, chaindetails "
                + "WHERE chn_activedetailid = chndt_chaindetailid AND chndt_chaininstancesequenceid = ? "
                + "FOR UPDATE");
        getChainsByChainInstanceSequenceQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<Chain> getChainsByChainInstanceSequence(Sequence chainInstanceSequence, EntityPermission entityPermission) {
        return ChainFactory.getInstance().getEntitiesFromQuery(entityPermission, getChainsByChainInstanceSequenceQueries,
                chainInstanceSequence);
    }

    public List<Chain> getChainsByChainInstanceSequence(Sequence chainInstanceSequence) {
        return getChainsByChainInstanceSequence(chainInstanceSequence, EntityPermission.READ_ONLY);
    }

    public List<Chain> getChainsByChainInstanceSequenceForUpdate(Sequence chainInstanceSequence) {
        return getChainsByChainInstanceSequence(chainInstanceSequence, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getDefaultChainQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM chains, chaindetails "
                + "WHERE chn_activedetailid = chndt_chaindetailid "
                + "AND chndt_chntyp_chaintypeid = ? AND chndt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM chains, chaindetails "
                + "WHERE chn_activedetailid = chndt_chaindetailid "
                + "AND chndt_chntyp_chaintypeid = ? AND chndt_isdefault = 1 "
                + "FOR UPDATE");
        getDefaultChainQueries = Collections.unmodifiableMap(queryMap);
    }

    private Chain getDefaultChain(ChainType chainType, EntityPermission entityPermission) {
        return ChainFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultChainQueries,
                chainType);
    }

    public Chain getDefaultChain(ChainType chainType) {
        return getDefaultChain(chainType, EntityPermission.READ_ONLY);
    }

    public Chain getDefaultChainForUpdate(ChainType chainType) {
        return getDefaultChain(chainType, EntityPermission.READ_WRITE);
    }

    public ChainDetailValue getDefaultChainDetailValueForUpdate(ChainType chainType) {
        return getDefaultChainForUpdate(chainType).getLastDetailForUpdate().getChainDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getChainByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM chains, chaindetails "
                + "WHERE chn_activedetailid = chndt_chaindetailid "
                + "AND chndt_chntyp_chaintypeid = ? AND chndt_chainname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM chains, chaindetails "
                + "WHERE chn_activedetailid = chndt_chaindetailid "
                + "AND chndt_chntyp_chaintypeid = ? AND chndt_chainname = ? "
                + "FOR UPDATE");
        getChainByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private Chain getChainByName(ChainType chainType, String chainName, EntityPermission entityPermission) {
        return ChainFactory.getInstance().getEntityFromQuery(entityPermission, getChainByNameQueries,
                chainType, chainName);
    }

    public Chain getChainByName(ChainType chainType, String chainName) {
        return getChainByName(chainType, chainName, EntityPermission.READ_ONLY);
    }

    public Chain getChainByNameForUpdate(ChainType chainType, String chainName) {
        return getChainByName(chainType, chainName, EntityPermission.READ_WRITE);
    }

    public ChainDetailValue getChainDetailValueForUpdate(Chain chain) {
        return chain == null? null: chain.getLastDetailForUpdate().getChainDetailValue().clone();
    }

    public ChainDetailValue getChainDetailValueByNameForUpdate(ChainType chainType, String chainName) {
        return getChainDetailValueForUpdate(getChainByNameForUpdate(chainType, chainName));
    }

    public ChainChoicesBean getChainChoices(String defaultChainChoice, Language language,
            boolean allowNullChoice, ChainType chainType) {
        var chains = getChainsByChainType(chainType);
        var size = chains.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultChainChoice == null) {
                defaultValue = "";
            }
        }

        for(var chain : chains) {
            var chainDetail = chain.getLastDetail();
            var label = getBestChainDescription(chain, language);
            var value = chainDetail.getChainName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultChainChoice != null && defaultChainChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && chainDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ChainChoicesBean(labels, values, defaultValue);
    }

    public ChainTransfer getChainTransfer(UserVisit userVisit, Chain chain) {
        return getChainTransferCaches().getChainTransferCache().getChainTransfer(userVisit, chain);
    }

    public List<ChainTransfer> getChainTransfersByChainType(UserVisit userVisit, ChainType chainType) {
        var chains = getChainsByChainType(chainType);
        List<ChainTransfer> chainTransfers = new ArrayList<>(chains.size());
        var chainTransferCache = getChainTransferCaches().getChainTransferCache();

        chains.forEach((chain) ->
                chainTransfers.add(chainTransferCache.getChainTransfer(userVisit, chain))
        );

        return chainTransfers;
    }

    private void updateChainFromValue(ChainDetailValue chainDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(chainDetailValue.hasBeenModified()) {
            var chain = ChainFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     chainDetailValue.getChainPK());
            var chainDetail = chain.getActiveDetailForUpdate();

            chainDetail.setThruTime(session.START_TIME_LONG);
            chainDetail.store();

            var chainPK = chainDetail.getChainPK();
            var chainType = chainDetail.getChainType();
            var chainTypePK = chainType.getPrimaryKey();
            var chainName = chainDetailValue.getChainName();
            var chainInstanceSequencePK = chainDetailValue.getChainInstanceSequencePK();
            var isDefault = chainDetailValue.getIsDefault();
            var sortOrder = chainDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultChain = getDefaultChain(chainType);
                var defaultFound = defaultChain != null && !defaultChain.equals(chain);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultChainDetailValue = getDefaultChainDetailValueForUpdate(chainType);

                    defaultChainDetailValue.setIsDefault(false);
                    updateChainFromValue(defaultChainDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            chainDetail = ChainDetailFactory.getInstance().create(chainPK, chainTypePK, chainName, chainInstanceSequencePK, isDefault, sortOrder,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            chain.setActiveDetail(chainDetail);
            chain.setLastDetail(chainDetail);

            sendEvent(chainPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateChainFromValue(ChainDetailValue chainDetailValue, BasePK updatedBy) {
        updateChainFromValue(chainDetailValue, true, updatedBy);
    }

    public void deleteChain(Chain chain, BasePK deletedBy) {
        var contactListControl = Session.getModelController(ContactListControl.class);
        var offerControl = Session.getModelController(OfferControl.class);
        
        contactListControl.deleteContactListTypesByChain(chain, deletedBy);
        offerControl.deleteOfferChainTypesByChain(chain, deletedBy);
        deleteChainInstancesByChain(chain, deletedBy);
        deleteChainActionSetsByChain(chain, deletedBy);
        deleteChainDescriptionsByChain(chain, deletedBy);

        var chainDetail = chain.getLastDetailForUpdate();
        chainDetail.setThruTime(session.START_TIME_LONG);
        chain.setActiveDetail(null);
        chain.store();

        // Check for default, and pick one if necessary
        var chainType = chainDetail.getChainType();
        var defaultChain = getDefaultChain(chainType);
        if(defaultChain == null) {
            var chains = getChainsByChainType(chainType);

            if(!chains.isEmpty()) {
                var iter = chains.iterator();
                if(iter.hasNext()) {
                    defaultChain = iter.next();
                }
                var chainDetailValue = Objects.requireNonNull(defaultChain).getLastDetailForUpdate().getChainDetailValue().clone();

                chainDetailValue.setIsDefault(true);
                updateChainFromValue(chainDetailValue, false, deletedBy);
            }
        }

        sendEvent(chain.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteChains(List<Chain> chains, BasePK deletedBy) {
        chains.forEach((chain) -> 
                deleteChain(chain, deletedBy)
        );
    }

    public void deleteChainsByChainType(ChainType chainType, BasePK deletedBy) {
        deleteChains(getChainsByChainType(chainType), deletedBy);
    }

    public void deleteChainsBySequence(Sequence sequence, BasePK deletedBy) {
        deleteChains(getChainsByChainInstanceSequenceForUpdate(sequence), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Chain Descriptions
    // --------------------------------------------------------------------------------

    public ChainDescription createChainDescription(Chain chain, Language language, String description,
            BasePK createdBy) {
        var chainDescription = ChainDescriptionFactory.getInstance().create(chain,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(chain.getPrimaryKey(), EventTypes.MODIFY, chainDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return chainDescription;
    }

    private static final Map<EntityPermission, String> getChainDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM chaindescriptions "
                + "WHERE chnd_chn_chainid = ? AND chnd_lang_languageid = ? AND chnd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM chaindescriptions "
                + "WHERE chnd_chn_chainid = ? AND chnd_lang_languageid = ? AND chnd_thrutime = ? "
                + "FOR UPDATE");
        getChainDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private ChainDescription getChainDescription(Chain chain, Language language, EntityPermission entityPermission) {
        return ChainDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getChainDescriptionQueries,
                chain, language, Session.MAX_TIME);
    }

    public ChainDescription getChainDescription(Chain chain, Language language) {
        return getChainDescription(chain, language, EntityPermission.READ_ONLY);
    }

    public ChainDescription getChainDescriptionForUpdate(Chain chain, Language language) {
        return getChainDescription(chain, language, EntityPermission.READ_WRITE);
    }

    public ChainDescriptionValue getChainDescriptionValue(ChainDescription chainDescription) {
        return chainDescription == null? null: chainDescription.getChainDescriptionValue().clone();
    }

    public ChainDescriptionValue getChainDescriptionValueForUpdate(Chain chain, Language language) {
        return getChainDescriptionValue(getChainDescriptionForUpdate(chain, language));
    }

    private static final Map<EntityPermission, String> getChainDescriptionsByChainQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM chaindescriptions, languages "
                + "WHERE chnd_chn_chainid = ? AND chnd_thrutime = ? AND chnd_lang_languageid = lang_languageid "
                + "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM chaindescriptions "
                + "WHERE chnd_chn_chainid = ? AND chnd_thrutime = ? "
                + "FOR UPDATE");
        getChainDescriptionsByChainQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ChainDescription> getChainDescriptionsByChain(Chain chain, EntityPermission entityPermission) {
        return ChainDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getChainDescriptionsByChainQueries,
                chain, Session.MAX_TIME);
    }

    public List<ChainDescription> getChainDescriptionsByChain(Chain chain) {
        return getChainDescriptionsByChain(chain, EntityPermission.READ_ONLY);
    }

    public List<ChainDescription> getChainDescriptionsByChainForUpdate(Chain chain) {
        return getChainDescriptionsByChain(chain, EntityPermission.READ_WRITE);
    }

    public String getBestChainDescription(Chain chain, Language language) {
        String description;
        var chainDescription = getChainDescription(chain, language);

        if(chainDescription == null && !language.getIsDefault()) {
            chainDescription = getChainDescription(chain, partyControl.getDefaultLanguage());
        }

        if(chainDescription == null) {
            description = chain.getLastDetail().getChainName();
        } else {
            description = chainDescription.getDescription();
        }

        return description;
    }

    public ChainDescriptionTransfer getChainDescriptionTransfer(UserVisit userVisit, ChainDescription chainDescription) {
        return getChainTransferCaches().getChainDescriptionTransferCache().getChainDescriptionTransfer(userVisit, chainDescription);
    }

    public List<ChainDescriptionTransfer> getChainDescriptionTransfersByChain(UserVisit userVisit, Chain chain) {
        var chainDescriptions = getChainDescriptionsByChain(chain);
        List<ChainDescriptionTransfer> chainDescriptionTransfers = new ArrayList<>(chainDescriptions.size());

        chainDescriptions.forEach((chainDescription) -> {
            chainDescriptionTransfers.add(getChainTransferCaches().getChainDescriptionTransferCache().getChainDescriptionTransfer(userVisit, chainDescription));
        });

        return chainDescriptionTransfers;
    }

    public void updateChainDescriptionFromValue(ChainDescriptionValue chainDescriptionValue, BasePK updatedBy) {
        if(chainDescriptionValue.hasBeenModified()) {
            var chainDescription = ChainDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     chainDescriptionValue.getPrimaryKey());

            chainDescription.setThruTime(session.START_TIME_LONG);
            chainDescription.store();

            var chain = chainDescription.getChain();
            var language = chainDescription.getLanguage();
            var description = chainDescriptionValue.getDescription();

            chainDescription = ChainDescriptionFactory.getInstance().create(chain, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(chain.getPrimaryKey(), EventTypes.MODIFY, chainDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteChainDescription(ChainDescription chainDescription, BasePK deletedBy) {
        chainDescription.setThruTime(session.START_TIME_LONG);

        sendEvent(chainDescription.getChainPK(), EventTypes.MODIFY, chainDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteChainDescriptionsByChain(Chain chain, BasePK deletedBy) {
        var chainDescriptions = getChainDescriptionsByChainForUpdate(chain);

        chainDescriptions.forEach((chainDescription) -> 
                deleteChainDescription(chainDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Chain Action Sets
    // --------------------------------------------------------------------------------

    public ChainActionSet createChainActionSet(Chain chain, String chainActionSetName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultChainActionSet = getDefaultChainActionSet(chain);
        var defaultFound = defaultChainActionSet != null;

        if(defaultFound && isDefault) {
            var defaultChainActionSetDetailValue = getDefaultChainActionSetDetailValueForUpdate(chain);

            defaultChainActionSetDetailValue.setIsDefault(false);
            updateChainActionSetFromValue(defaultChainActionSetDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var chainActionSet = ChainActionSetFactory.getInstance().create();
        var chainActionSetDetail = ChainActionSetDetailFactory.getInstance().create(session, chainActionSet, chain, chainActionSetName,
                isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        chainActionSet = ChainActionSetFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, chainActionSet.getPrimaryKey());
        chainActionSet.setActiveDetail(chainActionSetDetail);
        chainActionSet.setLastDetail(chainActionSetDetail);
        chainActionSet.store();

        sendEvent(chainActionSet.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return chainActionSet;
    }

    private static final Map<EntityPermission, String> getChainActionSetsByChainQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM chainactionsets, chainactionsetdetails "
                + "WHERE chnactst_activedetailid = chnactstdt_chainactionsetdetailid AND chnactstdt_chn_chainid = ? "
                + "ORDER BY chnactstdt_sortorder, chnactstdt_chainactionsetname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM chainactionsets, chainactionsetdetails "
                + "WHERE chnactst_activedetailid = chnactstdt_chainactionsetdetailid AND chnactstdt_chn_chainid = ? "
                + "FOR UPDATE");
        getChainActionSetsByChainQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ChainActionSet> getChainActionSetsByChain(Chain chain, EntityPermission entityPermission) {
        return ChainActionSetFactory.getInstance().getEntitiesFromQuery(entityPermission, getChainActionSetsByChainQueries,
                chain);
    }

    public List<ChainActionSet> getChainActionSetsByChain(Chain chain) {
        return getChainActionSetsByChain(chain, EntityPermission.READ_ONLY);
    }

    public List<ChainActionSet> getChainActionSetsByChainForUpdate(Chain chain) {
        return getChainActionSetsByChain(chain, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getDefaultChainActionSetQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM chainactionsets, chainactionsetdetails "
                + "WHERE chnactst_activedetailid = chnactstdt_chainactionsetdetailid "
                + "AND chnactstdt_chn_chainid = ? AND chnactstdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM chainactionsets, chainactionsetdetails "
                + "WHERE chnactst_activedetailid = chnactstdt_chainactionsetdetailid "
                + "AND chnactstdt_chn_chainid = ? AND chnactstdt_isdefault = 1 "
                + "FOR UPDATE");
        getDefaultChainActionSetQueries = Collections.unmodifiableMap(queryMap);
    }

    private ChainActionSet getDefaultChainActionSet(Chain chain, EntityPermission entityPermission) {
        return ChainActionSetFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultChainActionSetQueries,
                chain);
    }

    public ChainActionSet getDefaultChainActionSet(Chain chain) {
        return getDefaultChainActionSet(chain, EntityPermission.READ_ONLY);
    }

    public ChainActionSet getDefaultChainActionSetForUpdate(Chain chain) {
        return getDefaultChainActionSet(chain, EntityPermission.READ_WRITE);
    }

    public ChainActionSetDetailValue getDefaultChainActionSetDetailValueForUpdate(Chain chain) {
        return getDefaultChainActionSetForUpdate(chain).getLastDetailForUpdate().getChainActionSetDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getChainActionSetByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM chainactionsets, chainactionsetdetails "
                + "WHERE chnactst_activedetailid = chnactstdt_chainactionsetdetailid "
                + "AND chnactstdt_chn_chainid = ? AND chnactstdt_chainactionsetname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM chainactionsets, chainactionsetdetails "
                + "WHERE chnactst_activedetailid = chnactstdt_chainactionsetdetailid "
                + "AND chnactstdt_chn_chainid = ? AND chnactstdt_chainactionsetname = ? "
                + "FOR UPDATE");
        getChainActionSetByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private ChainActionSet getChainActionSetByName(Chain chain, String chainActionSetName, EntityPermission entityPermission) {
        return ChainActionSetFactory.getInstance().getEntityFromQuery(entityPermission, getChainActionSetByNameQueries,
                chain, chainActionSetName);
    }

    public ChainActionSet getChainActionSetByName(Chain chain, String chainActionSetName) {
        return getChainActionSetByName(chain, chainActionSetName, EntityPermission.READ_ONLY);
    }

    public ChainActionSet getChainActionSetByNameForUpdate(Chain chain, String chainActionSetName) {
        return getChainActionSetByName(chain, chainActionSetName, EntityPermission.READ_WRITE);
    }

    public ChainActionSetDetailValue getChainActionSetDetailValueForUpdate(ChainActionSet chainActionSet) {
        return chainActionSet == null? null: chainActionSet.getLastDetailForUpdate().getChainActionSetDetailValue().clone();
    }

    public ChainActionSetDetailValue getChainActionSetDetailValueByNameForUpdate(Chain chain, String chainActionSetName) {
        return getChainActionSetDetailValueForUpdate(getChainActionSetByNameForUpdate(chain, chainActionSetName));
    }

    public ChainActionSetChoicesBean getChainActionSetChoices(String defaultChainActionSetChoice, Language language, boolean allowNullChoice, Chain chain) {
        var chainActionSets = getChainActionSetsByChain(chain);
        var size = chainActionSets.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultChainActionSetChoice == null) {
                defaultValue = "";
            }
        }

        for(var chainActionSet : chainActionSets) {
            var chainActionSetDetail = chainActionSet.getLastDetail();
            var label = getBestChainActionSetDescription(chainActionSet, language);
            var value = chainActionSetDetail.getChainActionSetName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultChainActionSetChoice != null && defaultChainActionSetChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && chainActionSetDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ChainActionSetChoicesBean(labels, values, defaultValue);
    }

    public ChainActionSetTransfer getChainActionSetTransfer(UserVisit userVisit, ChainActionSet chainActionSet) {
        return getChainTransferCaches().getChainActionSetTransferCache().getChainActionSetTransfer(userVisit, chainActionSet);
    }

    public List<ChainActionSetTransfer> getChainActionSetTransfersByChain(UserVisit userVisit, Chain chain) {
        var chainActionSets = getChainActionSetsByChain(chain);
        List<ChainActionSetTransfer> chainActionSetTransfers = new ArrayList<>(chainActionSets.size());
        var chainActionSetTransferCache = getChainTransferCaches().getChainActionSetTransferCache();

        chainActionSets.forEach((chainActionSet) ->
                chainActionSetTransfers.add(chainActionSetTransferCache.getChainActionSetTransfer(userVisit, chainActionSet))
        );

        return chainActionSetTransfers;
    }

    private void updateChainActionSetFromValue(ChainActionSetDetailValue chainActionSetDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(chainActionSetDetailValue.hasBeenModified()) {
            var chainActionSet = ChainActionSetFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     chainActionSetDetailValue.getChainActionSetPK());
            var chainActionSetDetail = chainActionSet.getActiveDetailForUpdate();

            chainActionSetDetail.setThruTime(session.START_TIME_LONG);
            chainActionSetDetail.store();

            var chainActionSetPK = chainActionSetDetail.getChainActionSetPK();
            var chain = chainActionSetDetail.getChain();
            var chainPK = chain.getPrimaryKey();
            var chainActionSetName = chainActionSetDetailValue.getChainActionSetName();
            var isDefault = chainActionSetDetailValue.getIsDefault();
            var sortOrder = chainActionSetDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultChainActionSet = getDefaultChainActionSet(chain);
                var defaultFound = defaultChainActionSet != null && !defaultChainActionSet.equals(chainActionSet);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultChainActionSetDetailValue = getDefaultChainActionSetDetailValueForUpdate(chain);

                    defaultChainActionSetDetailValue.setIsDefault(false);
                    updateChainActionSetFromValue(defaultChainActionSetDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            chainActionSetDetail = ChainActionSetDetailFactory.getInstance().create(chainActionSetPK, chainPK, chainActionSetName, isDefault, sortOrder,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            chainActionSet.setActiveDetail(chainActionSetDetail);
            chainActionSet.setLastDetail(chainActionSetDetail);

            sendEvent(chainActionSetPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateChainActionSetFromValue(ChainActionSetDetailValue chainActionSetDetailValue, BasePK updatedBy) {
        updateChainActionSetFromValue(chainActionSetDetailValue, true, updatedBy);
    }

    public void deleteChainActionSet(ChainActionSet chainActionSet, BasePK deletedBy) {
        deleteChainInstancesByNextChainActionSet(chainActionSet, deletedBy);
        deleteChainActionsByChainActionSet(chainActionSet, deletedBy);
        deleteChainActionSetDescriptionsByChainActionSet(chainActionSet, deletedBy);

        var chainActionSetDetail = chainActionSet.getLastDetailForUpdate();
        chainActionSetDetail.setThruTime(session.START_TIME_LONG);
        chainActionSet.setActiveDetail(null);
        chainActionSet.store();

        // Check for default, and pick one if necessary
        var chain = chainActionSetDetail.getChain();
        var defaultChainActionSet = getDefaultChainActionSet(chain);
        if(defaultChainActionSet == null) {
            var chainActionSets = getChainActionSetsByChain(chain);

            if(!chainActionSets.isEmpty()) {
                var iter = chainActionSets.iterator();
                if(iter.hasNext()) {
                    defaultChainActionSet = iter.next();
                }
                var chainActionSetDetailValue = Objects.requireNonNull(defaultChainActionSet).getLastDetailForUpdate().getChainActionSetDetailValue().clone();

                chainActionSetDetailValue.setIsDefault(true);
                updateChainActionSetFromValue(chainActionSetDetailValue, false, deletedBy);
            }
        }

        sendEvent(chainActionSet.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteChainActionSets(List<ChainActionSet> chainActionSets, BasePK deletedBy) {
        chainActionSets.forEach((chainActionSet) -> 
                deleteChainActionSet(chainActionSet, deletedBy)
        );
    }

    public void deleteChainActionSetsByChain(Chain chain, BasePK deletedBy) {
        deleteChainActionSets(getChainActionSetsByChain(chain), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Chain Action Set Descriptions
    // --------------------------------------------------------------------------------

    public ChainActionSetDescription createChainActionSetDescription(ChainActionSet chainActionSet, Language language, String description, BasePK createdBy) {
        var chainActionSetDescription = ChainActionSetDescriptionFactory.getInstance().create(chainActionSet,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(chainActionSet.getPrimaryKey(), EventTypes.MODIFY, chainActionSetDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return chainActionSetDescription;
    }

    private static final Map<EntityPermission, String> getChainActionSetDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM chainactionsetdescriptions "
                + "WHERE chnactstd_chnactst_chainactionsetid = ? AND chnactstd_lang_languageid = ? AND chnactstd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM chainactionsetdescriptions "
                + "WHERE chnactstd_chnactst_chainactionsetid = ? AND chnactstd_lang_languageid = ? AND chnactstd_thrutime = ? "
                + "FOR UPDATE");
        getChainActionSetDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private ChainActionSetDescription getChainActionSetDescription(ChainActionSet chainActionSet, Language language, EntityPermission entityPermission) {
        return ChainActionSetDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getChainActionSetDescriptionQueries,
                chainActionSet, language, Session.MAX_TIME);
    }

    public ChainActionSetDescription getChainActionSetDescription(ChainActionSet chainActionSet, Language language) {
        return getChainActionSetDescription(chainActionSet, language, EntityPermission.READ_ONLY);
    }

    public ChainActionSetDescription getChainActionSetDescriptionForUpdate(ChainActionSet chainActionSet, Language language) {
        return getChainActionSetDescription(chainActionSet, language, EntityPermission.READ_WRITE);
    }

    public ChainActionSetDescriptionValue getChainActionSetDescriptionValue(ChainActionSetDescription chainActionSetDescription) {
        return chainActionSetDescription == null? null: chainActionSetDescription.getChainActionSetDescriptionValue().clone();
    }

    public ChainActionSetDescriptionValue getChainActionSetDescriptionValueForUpdate(ChainActionSet chainActionSet, Language language) {
        return getChainActionSetDescriptionValue(getChainActionSetDescriptionForUpdate(chainActionSet, language));
    }

    private static final Map<EntityPermission, String> getChainActionSetDescriptionsByChainActionSetQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM chainactionsetdescriptions, languages "
                + "WHERE chnactstd_chnactst_chainactionsetid = ? AND chnactstd_thrutime = ? AND chnactstd_lang_languageid = lang_languageid "
                + "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM chainactionsetdescriptions "
                + "WHERE chnactstd_chnactst_chainactionsetid = ? AND chnactstd_thrutime = ? "
                + "FOR UPDATE");
        getChainActionSetDescriptionsByChainActionSetQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ChainActionSetDescription> getChainActionSetDescriptionsByChainActionSet(ChainActionSet chainActionSet, EntityPermission entityPermission) {
        return ChainActionSetDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getChainActionSetDescriptionsByChainActionSetQueries,
                chainActionSet, Session.MAX_TIME);
    }

    public List<ChainActionSetDescription> getChainActionSetDescriptionsByChainActionSet(ChainActionSet chainActionSet) {
        return getChainActionSetDescriptionsByChainActionSet(chainActionSet, EntityPermission.READ_ONLY);
    }

    public List<ChainActionSetDescription> getChainActionSetDescriptionsByChainActionSetForUpdate(ChainActionSet chainActionSet) {
        return getChainActionSetDescriptionsByChainActionSet(chainActionSet, EntityPermission.READ_WRITE);
    }

    public String getBestChainActionSetDescription(ChainActionSet chainActionSet, Language language) {
        String description;
        var chainActionSetDescription = getChainActionSetDescription(chainActionSet, language);

        if(chainActionSetDescription == null && !language.getIsDefault()) {
            chainActionSetDescription = getChainActionSetDescription(chainActionSet, partyControl.getDefaultLanguage());
        }

        if(chainActionSetDescription == null) {
            description = chainActionSet.getLastDetail().getChainActionSetName();
        } else {
            description = chainActionSetDescription.getDescription();
        }

        return description;
    }

    public ChainActionSetDescriptionTransfer getChainActionSetDescriptionTransfer(UserVisit userVisit, ChainActionSetDescription chainActionSetDescription) {
        return getChainTransferCaches().getChainActionSetDescriptionTransferCache().getChainActionSetDescriptionTransfer(userVisit, chainActionSetDescription);
    }

    public List<ChainActionSetDescriptionTransfer> getChainActionSetDescriptionTransfersByChainActionSet(UserVisit userVisit, ChainActionSet chainActionSet) {
        var chainActionSetDescriptions = getChainActionSetDescriptionsByChainActionSet(chainActionSet);
        List<ChainActionSetDescriptionTransfer> chainActionSetDescriptionTransfers = new ArrayList<>(chainActionSetDescriptions.size());

        chainActionSetDescriptions.forEach((chainActionSetDescription) -> {
            chainActionSetDescriptionTransfers.add(getChainTransferCaches().getChainActionSetDescriptionTransferCache().getChainActionSetDescriptionTransfer(userVisit, chainActionSetDescription));
        });

        return chainActionSetDescriptionTransfers;
    }

    public void updateChainActionSetDescriptionFromValue(ChainActionSetDescriptionValue chainActionSetDescriptionValue, BasePK updatedBy) {
        if(chainActionSetDescriptionValue.hasBeenModified()) {
            var chainActionSetDescription = ChainActionSetDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     chainActionSetDescriptionValue.getPrimaryKey());

            chainActionSetDescription.setThruTime(session.START_TIME_LONG);
            chainActionSetDescription.store();

            var chainActionSet = chainActionSetDescription.getChainActionSet();
            var language = chainActionSetDescription.getLanguage();
            var description = chainActionSetDescriptionValue.getDescription();

            chainActionSetDescription = ChainActionSetDescriptionFactory.getInstance().create(chainActionSet, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(chainActionSet.getPrimaryKey(), EventTypes.MODIFY, chainActionSetDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteChainActionSetDescription(ChainActionSetDescription chainActionSetDescription, BasePK deletedBy) {
        chainActionSetDescription.setThruTime(session.START_TIME_LONG);

        sendEvent(chainActionSetDescription.getChainActionSetPK(), EventTypes.MODIFY, chainActionSetDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteChainActionSetDescriptionsByChainActionSet(ChainActionSet chainActionSet, BasePK deletedBy) {
        var chainActionSetDescriptions = getChainActionSetDescriptionsByChainActionSetForUpdate(chainActionSet);

        chainActionSetDescriptions.forEach((chainActionSetDescription) -> 
                deleteChainActionSetDescription(chainActionSetDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Chain ActionTypes
    // --------------------------------------------------------------------------------

    public ChainActionType createChainActionType(String chainActionTypeName, Boolean allowMultiple, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultChainActionType = getDefaultChainActionType();
        var defaultFound = defaultChainActionType != null;

        if(defaultFound && isDefault) {
            var defaultChainActionTypeDetailValue = getDefaultChainActionTypeDetailValueForUpdate();

            defaultChainActionTypeDetailValue.setIsDefault(false);
            updateChainActionTypeFromValue(defaultChainActionTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var chainActionType = ChainActionTypeFactory.getInstance().create();
        var chainActionTypeDetail = ChainActionTypeDetailFactory.getInstance().create(chainActionType, chainActionTypeName, allowMultiple,
                isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        chainActionType = ChainActionTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                chainActionType.getPrimaryKey());
        chainActionType.setActiveDetail(chainActionTypeDetail);
        chainActionType.setLastDetail(chainActionTypeDetail);
        chainActionType.store();

        sendEvent(chainActionType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return chainActionType;
    }

    private static final Map<EntityPermission, String> getChainActionTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM chainactiontypes, chainactiontypedetails "
                + "WHERE chnacttyp_activedetailid = chnacttypdt_chainactiontypedetailid AND chnacttypdt_chainactiontypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM chainactiontypes, chainactiontypedetails "
                + "WHERE chnacttyp_activedetailid = chnacttypdt_chainactiontypedetailid AND chnacttypdt_chainactiontypename = ? "
                + "FOR UPDATE");
        getChainActionTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private ChainActionType getChainActionTypeByName(String chainActionTypeName, EntityPermission entityPermission) {
        return ChainActionTypeFactory.getInstance().getEntityFromQuery(entityPermission, getChainActionTypeByNameQueries,
                chainActionTypeName);
    }

    public ChainActionType getChainActionTypeByName(String chainActionTypeName) {
        return getChainActionTypeByName(chainActionTypeName, EntityPermission.READ_ONLY);
    }

    public ChainActionType getChainActionTypeByNameForUpdate(String chainActionTypeName) {
        return getChainActionTypeByName(chainActionTypeName, EntityPermission.READ_WRITE);
    }

    public ChainActionTypeDetailValue getChainActionTypeDetailValueForUpdate(ChainActionType chainActionType) {
        return chainActionType == null? null: chainActionType.getLastDetailForUpdate().getChainActionTypeDetailValue().clone();
    }

    public ChainActionTypeDetailValue getChainActionTypeDetailValueByNameForUpdate(String chainActionTypeName) {
        return getChainActionTypeDetailValueForUpdate(getChainActionTypeByNameForUpdate(chainActionTypeName));
    }

    private static final Map<EntityPermission, String> getDefaultChainActionTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM chainactiontypes, chainactiontypedetails "
                + "WHERE chnacttyp_activedetailid = chnacttypdt_chainactiontypedetailid AND chnacttypdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM chainactiontypes, chainactiontypedetails "
                + "WHERE chnacttyp_activedetailid = chnacttypdt_chainactiontypedetailid AND chnacttypdt_isdefault = 1 "
                + "FOR UPDATE");
        getDefaultChainActionTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private ChainActionType getDefaultChainActionType(EntityPermission entityPermission) {
        return ChainActionTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultChainActionTypeQueries);
    }

    public ChainActionType getDefaultChainActionType() {
        return getDefaultChainActionType(EntityPermission.READ_ONLY);
    }

    public ChainActionType getDefaultChainActionTypeForUpdate() {
        return getDefaultChainActionType(EntityPermission.READ_WRITE);
    }

    public ChainActionTypeDetailValue getDefaultChainActionTypeDetailValueForUpdate() {
        return getDefaultChainActionType(EntityPermission.READ_WRITE).getLastDetailForUpdate().getChainActionTypeDetailValue();
    }

    private static final Map<EntityPermission, String> getChainActionTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM chainactiontypes, chainactiontypedetails "
                + "WHERE chnacttyp_activedetailid = chnacttypdt_chainactiontypedetailid "
                + "ORDER BY chnacttypdt_sortorder, chnacttypdt_chainactiontypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM chainactiontypes, chainactiontypedetails "
                + "WHERE chnacttyp_activedetailid = chnacttypdt_chainactiontypedetailid "
                + "FOR UPDATE");
        getChainActionTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ChainActionType> getChainActionTypes(EntityPermission entityPermission) {
        return ChainActionTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getChainActionTypesQueries);
    }

    public List<ChainActionType> getChainActionTypes() {
        return getChainActionTypes(EntityPermission.READ_ONLY);
    }

    public List<ChainActionType> getChainActionTypesForUpdate() {
        return getChainActionTypes(EntityPermission.READ_WRITE);
    }

    public ChainActionTypeChoicesBean getChainActionTypeChoices(String defaultChainActionTypeChoice, Language language, boolean allowNullChoice) {
        var chainActionTypes = getChainActionTypes();
        var size = chainActionTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultChainActionTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var chainActionType : chainActionTypes) {
            var chainActionTypeDetail = chainActionType.getLastDetail();

            var label = getBestChainActionTypeDescription(chainActionType, language);
            var value = chainActionTypeDetail.getChainActionTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultChainActionTypeChoice != null && defaultChainActionTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && chainActionTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ChainActionTypeChoicesBean(labels, values, defaultValue);
    }

    public ChainActionTypeTransfer getChainActionTypeTransfer(UserVisit userVisit, ChainActionType chainActionType) {
        return getChainTransferCaches().getChainActionTypeTransferCache().getChainActionTypeTransfer(userVisit, chainActionType);
    }

    public List<ChainActionTypeTransfer> getChainActionTypeTransfers(UserVisit userVisit) {
        var chainActionTypes = getChainActionTypes();
        List<ChainActionTypeTransfer> chainActionTypeTransfers = new ArrayList<>(chainActionTypes.size());
        var chainActionTypeTransferCache = getChainTransferCaches().getChainActionTypeTransferCache();

        chainActionTypes.forEach((chainActionType) ->
                chainActionTypeTransfers.add(chainActionTypeTransferCache.getChainActionTypeTransfer(userVisit, chainActionType))
        );

        return chainActionTypeTransfers;
    }

    private void updateChainActionTypeFromValue(ChainActionTypeDetailValue chainActionTypeDetailValue, boolean checkDefault, BasePK updatedBy) {
        var chainActionType = ChainActionTypeFactory.getInstance().getEntityFromPK(session,
                EntityPermission.READ_WRITE, chainActionTypeDetailValue.getChainActionTypePK());
        var chainActionTypeDetail = chainActionType.getActiveDetailForUpdate();

        chainActionTypeDetail.setThruTime(session.START_TIME_LONG);
        chainActionTypeDetail.store();

        var chainActionTypePK = chainActionTypeDetail.getChainActionTypePK();
        var chainActionTypeName = chainActionTypeDetailValue.getChainActionTypeName();
        var allowMultiple = chainActionTypeDetailValue.getAllowMultiple();
        var isDefault = chainActionTypeDetailValue.getIsDefault();
        var sortOrder = chainActionTypeDetailValue.getSortOrder();

        if(checkDefault) {
            var defaultChainActionType = getDefaultChainActionType();
            var defaultFound = defaultChainActionType != null && !defaultChainActionType.equals(chainActionType);

            if(isDefault && defaultFound) {
                // If I'm the default, and a default already existed...
                var defaultChainActionTypeDetailValue = getDefaultChainActionTypeDetailValueForUpdate();

                defaultChainActionTypeDetailValue.setIsDefault(false);
                updateChainActionTypeFromValue(defaultChainActionTypeDetailValue, false, updatedBy);
            } else if(!isDefault && !defaultFound) {
                // If I'm not the default, and no other default exists...
                isDefault = true;
            }
        }

        chainActionTypeDetail = ChainActionTypeDetailFactory.getInstance().create(chainActionTypePK, chainActionTypeName, allowMultiple, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        chainActionType.setActiveDetail(chainActionTypeDetail);
        chainActionType.setLastDetail(chainActionTypeDetail);
        chainActionType.store();

        sendEvent(chainActionTypePK, EventTypes.MODIFY, null, null, updatedBy);
    }

    public void updateChainActionTypeFromValue(ChainActionTypeDetailValue chainActionTypeDetailValue, BasePK updatedBy) {
        updateChainActionTypeFromValue(chainActionTypeDetailValue, true, updatedBy);
    }

    public void deleteChainActionType(ChainActionType chainActionType, BasePK deletedBy) {
        deleteChainActionTypeDescriptionsByChainActionType(chainActionType, deletedBy);

        var chainActionTypeDetail = chainActionType.getLastDetailForUpdate();
        chainActionTypeDetail.setThruTime(session.START_TIME_LONG);
        chainActionType.setActiveDetail(null);
        chainActionType.store();

        // Check for default, and pick one if necessary
        var defaultChainActionType = getDefaultChainActionType();
        if(defaultChainActionType == null) {
            var chainActionTypes = getChainActionTypesForUpdate();

            if(!chainActionTypes.isEmpty()) {
                var iter = chainActionTypes.iterator();
                if(iter.hasNext()) {
                    defaultChainActionType = iter.next();
                }
                var chainActionTypeDetailValue = Objects.requireNonNull(defaultChainActionType).getLastDetailForUpdate().getChainActionTypeDetailValue().clone();

                chainActionTypeDetailValue.setIsDefault(true);
                updateChainActionTypeFromValue(chainActionTypeDetailValue, false, deletedBy);
            }
        }

        sendEvent(chainActionType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Chain ActionType Descriptions
    // --------------------------------------------------------------------------------

    public ChainActionTypeDescription createChainActionTypeDescription(ChainActionType chainActionType, Language language, String description,
            BasePK createdBy) {
        var chainActionTypeDescription = ChainActionTypeDescriptionFactory.getInstance().create(chainActionType,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(chainActionType.getPrimaryKey(), EventTypes.MODIFY, chainActionTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return chainActionTypeDescription;
    }

    private static final Map<EntityPermission, String> getChainActionTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM chainactiontypedescriptions "
                + "WHERE chnacttypd_chnacttyp_chainactiontypeid = ? AND chnacttypd_lang_languageid = ? AND chnacttypd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM chainactiontypedescriptions "
                + "WHERE chnacttypd_chnacttyp_chainactiontypeid = ? AND chnacttypd_lang_languageid = ? AND chnacttypd_thrutime = ? "
                + "FOR UPDATE");
        getChainActionTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private ChainActionTypeDescription getChainActionTypeDescription(ChainActionType chainActionType, Language language, EntityPermission entityPermission) {
        return ChainActionTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getChainActionTypeDescriptionQueries,
                chainActionType, language, Session.MAX_TIME);
    }

    public ChainActionTypeDescription getChainActionTypeDescription(ChainActionType chainActionType, Language language) {
        return getChainActionTypeDescription(chainActionType, language, EntityPermission.READ_ONLY);
    }

    public ChainActionTypeDescription getChainActionTypeDescriptionForUpdate(ChainActionType chainActionType, Language language) {
        return getChainActionTypeDescription(chainActionType, language, EntityPermission.READ_WRITE);
    }

    public ChainActionTypeDescriptionValue getChainActionTypeDescriptionValue(ChainActionTypeDescription chainActionTypeDescription) {
        return chainActionTypeDescription == null? null: chainActionTypeDescription.getChainActionTypeDescriptionValue().clone();
    }

    public ChainActionTypeDescriptionValue getChainActionTypeDescriptionValueForUpdate(ChainActionType chainActionType, Language language) {
        return getChainActionTypeDescriptionValue(getChainActionTypeDescriptionForUpdate(chainActionType, language));
    }

    private static final Map<EntityPermission, String> getChainActionTypeDescriptionsByChainActionTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM chainactiontypedescriptions, languages "
                + "WHERE chnacttypd_chnacttyp_chainactiontypeid = ? AND chnacttypd_thrutime = ? AND chnacttypd_lang_languageid = lang_languageid "
                + "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM chainactiontypedescriptions "
                + "WHERE chnacttypd_chnacttyp_chainactiontypeid = ? AND chnacttypd_thrutime = ? "
                + "FOR UPDATE");
        getChainActionTypeDescriptionsByChainActionTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ChainActionTypeDescription> getChainActionTypeDescriptionsByChainActionType(ChainActionType chainActionType, EntityPermission entityPermission) {
        return ChainActionTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getChainActionTypeDescriptionsByChainActionTypeQueries,
                chainActionType, Session.MAX_TIME);
    }

    public List<ChainActionTypeDescription> getChainActionTypeDescriptionsByChainActionType(ChainActionType chainActionType) {
        return getChainActionTypeDescriptionsByChainActionType(chainActionType, EntityPermission.READ_ONLY);
    }

    public List<ChainActionTypeDescription> getChainActionTypeDescriptionsByChainActionTypeForUpdate(ChainActionType chainActionType) {
        return getChainActionTypeDescriptionsByChainActionType(chainActionType, EntityPermission.READ_WRITE);
    }

    public String getBestChainActionTypeDescription(ChainActionType chainActionType, Language language) {
        String description;
        var chainActionTypeDescription = getChainActionTypeDescription(chainActionType, language);

        if(chainActionTypeDescription == null && !language.getIsDefault()) {
            chainActionTypeDescription = getChainActionTypeDescription(chainActionType, partyControl.getDefaultLanguage());
        }

        if(chainActionTypeDescription == null) {
            description = chainActionType.getLastDetail().getChainActionTypeName();
        } else {
            description = chainActionTypeDescription.getDescription();
        }

        return description;
    }

    public ChainActionTypeDescriptionTransfer getChainActionTypeDescriptionTransfer(UserVisit userVisit, ChainActionTypeDescription chainActionTypeDescription) {
        return getChainTransferCaches().getChainActionTypeDescriptionTransferCache().getChainActionTypeDescriptionTransfer(userVisit, chainActionTypeDescription);
    }

    public List<ChainActionTypeDescriptionTransfer> getChainActionTypeDescriptionTransfersByChainActionType(UserVisit userVisit, ChainActionType chainActionType) {
        var chainActionTypeDescriptions = getChainActionTypeDescriptionsByChainActionType(chainActionType);
        List<ChainActionTypeDescriptionTransfer> chainActionTypeDescriptionTransfers = new ArrayList<>(chainActionTypeDescriptions.size());

        chainActionTypeDescriptions.forEach((chainActionTypeDescription) -> {
            chainActionTypeDescriptionTransfers.add(getChainTransferCaches().getChainActionTypeDescriptionTransferCache().getChainActionTypeDescriptionTransfer(userVisit, chainActionTypeDescription));
        });

        return chainActionTypeDescriptionTransfers;
    }

    public void updateChainActionTypeDescriptionFromValue(ChainActionTypeDescriptionValue chainActionTypeDescriptionValue, BasePK updatedBy) {
        if(chainActionTypeDescriptionValue.hasBeenModified()) {
            var chainActionTypeDescription = ChainActionTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     chainActionTypeDescriptionValue.getPrimaryKey());

            chainActionTypeDescription.setThruTime(session.START_TIME_LONG);
            chainActionTypeDescription.store();

            var chainActionType = chainActionTypeDescription.getChainActionType();
            var language = chainActionTypeDescription.getLanguage();
            var description = chainActionTypeDescriptionValue.getDescription();

            chainActionTypeDescription = ChainActionTypeDescriptionFactory.getInstance().create(chainActionType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(chainActionType.getPrimaryKey(), EventTypes.MODIFY, chainActionTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteChainActionTypeDescription(ChainActionTypeDescription chainActionTypeDescription, BasePK deletedBy) {
        chainActionTypeDescription.setThruTime(session.START_TIME_LONG);

        sendEvent(chainActionTypeDescription.getChainActionTypePK(), EventTypes.MODIFY, chainActionTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteChainActionTypeDescriptionsByChainActionType(ChainActionType chainActionType, BasePK deletedBy) {
        var chainActionTypeDescriptions = getChainActionTypeDescriptionsByChainActionTypeForUpdate(chainActionType);

        chainActionTypeDescriptions.forEach((chainActionTypeDescription) -> 
                deleteChainActionTypeDescription(chainActionTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Chain Action Type Uses
    // --------------------------------------------------------------------------------
    
    public ChainActionTypeUse createChainActionTypeUse(ChainKind chainKind, ChainActionType chainActionType,
            Boolean isDefault) {
        return ChainActionTypeUseFactory.getInstance().create(chainKind, chainActionType, isDefault);
    }
    
    public ChainActionTypeUse getChainActionTypeUse(ChainKind chainKind, ChainActionType chainActionType) {
        ChainActionTypeUse chainActionTypeUse;
        
        try {
            var ps = ChainActionTypeUseFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM chainactiontypeuses " +
                    "WHERE chnacttypu_chnk_chainkindid = ? AND chnacttypu_chnacttyp_chainactiontypeid = ?");
            
            ps.setLong(1, chainKind.getPrimaryKey().getEntityId());
            ps.setLong(2, chainActionType.getPrimaryKey().getEntityId());
            
            chainActionTypeUse = ChainActionTypeUseFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return chainActionTypeUse;
    }
    
    // --------------------------------------------------------------------------------
    //   Chain Actions
    // --------------------------------------------------------------------------------
    
    public ChainAction createChainAction(ChainActionSet chainActionSet, String chainActionName, ChainActionType chainActionType, Integer sortOrder,
            BasePK createdBy) {
        var chainAction = ChainActionFactory.getInstance().create();
        var chainActionDetail = ChainActionDetailFactory.getInstance().create(chainAction, chainActionSet, chainActionName, chainActionType, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        // Convert to R/W
        chainAction = ChainActionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, chainAction.getPrimaryKey());
        chainAction.setActiveDetail(chainActionDetail);
        chainAction.setLastDetail(chainActionDetail);
        chainAction.store();

        sendEvent(chainAction.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return chainAction;
    }

    private static final Map<EntityPermission, String> getChainActionByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM chainactions, chainactiondetails "
                + "WHERE chnact_activedetailid = chnactdt_chainactiondetailid AND chnactdt_chnactst_chainactionsetid = ? AND chnactdt_chainactionname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM chainactions, chainactiondetails "
                + "WHERE chnact_activedetailid = chnactdt_chainactiondetailid AND chnactdt_chnactst_chainactionsetid = ? AND chnactdt_chainactionname = ? "
                + "FOR UPDATE");
        getChainActionByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private ChainAction getChainActionByName(ChainActionSet chainActionSet, String chainActionName, EntityPermission entityPermission) {
        return ChainActionFactory.getInstance().getEntityFromQuery(entityPermission, getChainActionByNameQueries,
                chainActionSet, chainActionName);
    }

    public ChainAction getChainActionByName(ChainActionSet chainActionSet, String chainActionName) {
        return getChainActionByName(chainActionSet, chainActionName, EntityPermission.READ_ONLY);
    }

    public ChainAction getChainActionByNameForUpdate(ChainActionSet chainActionSet, String chainActionName) {
        return getChainActionByName(chainActionSet, chainActionName, EntityPermission.READ_WRITE);
    }

    public ChainActionDetailValue getChainActionDetailValueForUpdate(ChainAction chainAction) {
        return chainAction == null ? null : chainAction.getLastDetailForUpdate().getChainActionDetailValue().clone();
    }

    public ChainActionDetailValue getChainActionDetailValueByNameForUpdate(ChainActionSet chainActionSet, String chainActionName) {
        return getChainActionDetailValueForUpdate(getChainActionByNameForUpdate(chainActionSet, chainActionName));
    }

    private static final Map<EntityPermission, String> getChainActionsByChainActionSetQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM chainactions, chainactiondetails "
                + "WHERE chnact_activedetailid = chnactdt_chainactiondetailid AND chnactdt_chnactst_chainactionsetid = ? "
                + "ORDER BY chnactdt_sortorder, chnactdt_chainactionname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM chainactions, chainactiondetails "
                + "WHERE chnact_activedetailid = chnactdt_chainactiondetailid AND chnactdt_chnactst_chainactionsetid = ? "
                + "FOR UPDATE");
        getChainActionsByChainActionSetQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ChainAction> getChainActionsByChainActionSet(ChainActionSet chainActionSet, EntityPermission entityPermission) {
        return ChainActionFactory.getInstance().getEntitiesFromQuery(entityPermission, getChainActionsByChainActionSetQueries,
                chainActionSet);
    }

    public List<ChainAction> getChainActionsByChainActionSet(ChainActionSet chainActionSet) {
        return getChainActionsByChainActionSet(chainActionSet, EntityPermission.READ_ONLY);
    }

    public List<ChainAction> getChainActionsByChainActionSetForUpdate(ChainActionSet chainActionSet) {
        return getChainActionsByChainActionSet(chainActionSet, EntityPermission.READ_WRITE);
    }

    public ChainActionTransfer getChainActionTransfer(UserVisit userVisit, ChainAction chainAction) {
        return getChainTransferCaches().getChainActionTransferCache().getChainActionTransfer(userVisit, chainAction);
    }

    public List<ChainActionTransfer> getChainActionTransfersByChainActionSet(UserVisit userVisit, ChainActionSet chainActionSet) {
        var chainActions = getChainActionsByChainActionSet(chainActionSet);
        List<ChainActionTransfer> chainActionTransfers = new ArrayList<>(chainActions.size());
        var chainActionTransferCache = getChainTransferCaches().getChainActionTransferCache();

        chainActions.forEach((chainAction) ->
                chainActionTransfers.add(chainActionTransferCache.getChainActionTransfer(userVisit, chainAction))
        );

        return chainActionTransfers;
    }

    public void updateChainActionFromValue(ChainActionDetailValue chainActionDetailValue, BasePK updatedBy) {
        if(chainActionDetailValue.hasBeenModified()) {
            var chainAction = ChainActionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, chainActionDetailValue.getChainActionPK());
            var chainActionDetail = chainAction.getActiveDetailForUpdate();

            chainActionDetail.setThruTime(session.START_TIME_LONG);
            chainActionDetail.store();

            var chainActionPK = chainActionDetail.getChainActionPK(); // Not updated
            var chainActionSet = chainActionDetail.getChainActionSet(); // Not updated
            var chainActionSetPK = chainActionSet.getPrimaryKey(); // Not updated
            var chainActionName = chainActionDetailValue.getChainActionName();
            var chainActionTypePK = chainActionDetail.getChainActionTypePK(); // Not updated
            var sortOrder = chainActionDetailValue.getSortOrder();

            chainActionDetail = ChainActionDetailFactory.getInstance().create(chainActionPK, chainActionSetPK, chainActionName, chainActionTypePK, sortOrder,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            chainAction.setActiveDetail(chainActionDetail);
            chainAction.setLastDetail(chainActionDetail);

            sendEvent(chainActionPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void deleteChainAction(ChainAction chainAction, BasePK deletedBy) {
        var chainActionDetail = chainAction.getLastDetailForUpdate();
        var chainActionTypeName = chainActionDetail.getChainActionType().getLastDetail().getChainActionTypeName();
        
        deleteChainActionDescriptionsByChainAction(chainAction, deletedBy);

        if(chainActionTypeName.equals(ChainConstants.ChainActionType_LETTER)) {
            deleteChainActionLetter(getChainActionLetterForUpdate(chainAction), deletedBy);
        } else if(chainActionTypeName.equals(ChainConstants.ChainActionType_SURVEY)) {
            deleteChainActionSurvey(getChainActionSurveyForUpdate(chainAction), deletedBy);
        } else if(chainActionTypeName.equals(ChainConstants.ChainActionType_CHAIN_ACTION_SET)) {
            deleteChainActionChainActionSet(getChainActionChainActionSetForUpdate(chainAction), deletedBy);
        }
        
        chainActionDetail.setThruTime(session.START_TIME_LONG);
        chainAction.setActiveDetail(null);
        chainAction.store();

        sendEvent(chainAction.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteChainActions(List<ChainAction> chainActions, BasePK deletedBy) {
        chainActions.forEach((chainAction) -> 
                deleteChainAction(chainAction, deletedBy)
        );
    }
    
    public void deleteChainActionsByChainActionSet(ChainActionSet chainActionSet, BasePK deletedBy) {
        deleteChainActions(getChainActionsByChainActionSetForUpdate(chainActionSet), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Chain Action Descriptions
    // --------------------------------------------------------------------------------
    
    public ChainActionDescription createChainActionDescription(ChainAction chainAction, Language language, String description, BasePK createdBy) {
        var chainActionDescription = ChainActionDescriptionFactory.getInstance().create(chainAction,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(chainAction.getPrimaryKey(), EventTypes.MODIFY, chainActionDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return chainActionDescription;
    }

    private static final Map<EntityPermission, String> getChainActionDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM chainactiondescriptions "
                + "WHERE chnactd_chnact_chainactionid = ? AND chnactd_lang_languageid = ? AND chnactd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM chainactiondescriptions "
                + "WHERE chnactd_chnact_chainactionid = ? AND chnactd_lang_languageid = ? AND chnactd_thrutime = ? "
                + "FOR UPDATE");
        getChainActionDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private ChainActionDescription getChainActionDescription(ChainAction chainAction, Language language, EntityPermission entityPermission) {
        return ChainActionDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getChainActionDescriptionQueries,
                chainAction, language, Session.MAX_TIME);
    }

    public ChainActionDescription getChainActionDescription(ChainAction chainAction, Language language) {
        return getChainActionDescription(chainAction, language, EntityPermission.READ_ONLY);
    }

    public ChainActionDescription getChainActionDescriptionForUpdate(ChainAction chainAction, Language language) {
        return getChainActionDescription(chainAction, language, EntityPermission.READ_WRITE);
    }

    public ChainActionDescriptionValue getChainActionDescriptionValue(ChainActionDescription chainActionDescription) {
        return chainActionDescription == null? null: chainActionDescription.getChainActionDescriptionValue().clone();
    }

    public ChainActionDescriptionValue getChainActionDescriptionValueForUpdate(ChainAction chainAction, Language language) {
        return getChainActionDescriptionValue(getChainActionDescriptionForUpdate(chainAction, language));
    }

    private static final Map<EntityPermission, String> getChainActionDescriptionsByChainActionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM chainactiondescriptions, languages "
                + "WHERE chnactd_chnact_chainactionid = ? AND chnactd_thrutime = ? AND chnactd_lang_languageid = lang_languageid "
                + "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM chainactiondescriptions "
                + "WHERE chnactd_chnact_chainactionid = ? AND chnactd_thrutime = ? "
                + "FOR UPDATE");
        getChainActionDescriptionsByChainActionQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ChainActionDescription> getChainActionDescriptionsByChainAction(ChainAction chainAction, EntityPermission entityPermission) {
        return ChainActionDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getChainActionDescriptionsByChainActionQueries,
                chainAction, Session.MAX_TIME);
    }

    public List<ChainActionDescription> getChainActionDescriptionsByChainAction(ChainAction chainAction) {
        return getChainActionDescriptionsByChainAction(chainAction, EntityPermission.READ_ONLY);
    }

    public List<ChainActionDescription> getChainActionDescriptionsByChainActionForUpdate(ChainAction chainAction) {
        return getChainActionDescriptionsByChainAction(chainAction, EntityPermission.READ_WRITE);
    }

    public String getBestChainActionDescription(ChainAction chainAction, Language language) {
        String description;
        var chainActionDescription = getChainActionDescription(chainAction, language);

        if(chainActionDescription == null && !language.getIsDefault()) {
            chainActionDescription = getChainActionDescription(chainAction, partyControl.getDefaultLanguage());
        }

        if(chainActionDescription == null) {
            description = chainAction.getLastDetail().getChainActionName();
        } else {
            description = chainActionDescription.getDescription();
        }

        return description;
    }

    public ChainActionDescriptionTransfer getChainActionDescriptionTransfer(UserVisit userVisit, ChainActionDescription chainActionDescription) {
        return getChainTransferCaches().getChainActionDescriptionTransferCache().getChainActionDescriptionTransfer(userVisit, chainActionDescription);
    }

    public List<ChainActionDescriptionTransfer> getChainActionDescriptionTransfersByChainAction(UserVisit userVisit, ChainAction chainAction) {
        var chainActionDescriptions = getChainActionDescriptionsByChainAction(chainAction);
        List<ChainActionDescriptionTransfer> chainActionDescriptionTransfers = new ArrayList<>(chainActionDescriptions.size());

        chainActionDescriptions.forEach((chainActionDescription) -> {
            chainActionDescriptionTransfers.add(getChainTransferCaches().getChainActionDescriptionTransferCache().getChainActionDescriptionTransfer(userVisit, chainActionDescription));
        });

        return chainActionDescriptionTransfers;
    }

    public void updateChainActionDescriptionFromValue(ChainActionDescriptionValue chainActionDescriptionValue, BasePK updatedBy) {
        if(chainActionDescriptionValue.hasBeenModified()) {
            var chainActionDescription = ChainActionDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     chainActionDescriptionValue.getPrimaryKey());

            chainActionDescription.setThruTime(session.START_TIME_LONG);
            chainActionDescription.store();

            var chainAction = chainActionDescription.getChainAction();
            var language = chainActionDescription.getLanguage();
            var description = chainActionDescriptionValue.getDescription();

            chainActionDescription = ChainActionDescriptionFactory.getInstance().create(chainAction, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(chainAction.getPrimaryKey(), EventTypes.MODIFY, chainActionDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteChainActionDescription(ChainActionDescription chainActionDescription, BasePK deletedBy) {
        chainActionDescription.setThruTime(session.START_TIME_LONG);

        sendEvent(chainActionDescription.getChainActionPK(), EventTypes.MODIFY, chainActionDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteChainActionDescriptionsByChainAction(ChainAction chainAction, BasePK deletedBy) {
        var chainActionDescriptions = getChainActionDescriptionsByChainActionForUpdate(chainAction);

        chainActionDescriptions.forEach((chainActionDescription) -> 
                deleteChainActionDescription(chainActionDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Chain Action Letters
    // --------------------------------------------------------------------------------
    
    public ChainActionLetter createChainActionLetter(ChainAction chainAction, Letter letter, BasePK createdBy) {
        var chainActionLetter = ChainActionLetterFactory.getInstance().create(chainAction, letter, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        sendEvent(chainAction.getPrimaryKey(), EventTypes.MODIFY, chainActionLetter.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return chainActionLetter;
    }

    private ChainActionLetter getChainActionLetter(ChainAction chainAction, EntityPermission entityPermission) {
        ChainActionLetter chainActionLetter;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM chainactionletters " +
                        "WHERE chnactlttr_chnact_chainactionid = ? AND chnactlttr_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM chainactionletters " +
                        "WHERE chnactlttr_chnact_chainactionid = ? AND chnactlttr_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ChainActionLetterFactory.getInstance().prepareStatement(query);

            ps.setLong(1, chainAction.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            chainActionLetter = ChainActionLetterFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch(SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return chainActionLetter;
    }

    public ChainActionLetter getChainActionLetter(ChainAction chainAction) {
        return getChainActionLetter(chainAction, EntityPermission.READ_ONLY);
    }

    public ChainActionLetter getChainActionLetterForUpdate(ChainAction chainAction) {
        return getChainActionLetter(chainAction, EntityPermission.READ_WRITE);
    }

    public ChainActionLetterValue getChainActionLetterValue(ChainActionLetter chainActionLetter) {
        return chainActionLetter == null ? null : chainActionLetter.getChainActionLetterValue().clone();
    }

    public ChainActionLetterValue getChainActionLetterValueForUpdate(ChainAction chainAction) {
        var chainActionLetter = getChainActionLetterForUpdate(chainAction);

        return chainActionLetter == null ? null : chainActionLetter.getChainActionLetterValue().clone();
    }

    public ChainActionLetterTransfer getChainActionLetterTransfer(UserVisit userVisit, ChainAction chainAction) {
        return getChainTransferCaches().getChainActionLetterTransferCache().getChainActionLetterTransfer(userVisit, getChainActionLetter(chainAction));
    }

    public void updateChainActionLetterFromValue(ChainActionLetterValue chainActionLetterValue, BasePK updatedBy) {
        if(chainActionLetterValue.hasBeenModified()) {
            var chainActionLetter = ChainActionLetterFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, chainActionLetterValue.getPrimaryKey());

            chainActionLetter.setThruTime(session.START_TIME_LONG);
            chainActionLetter.store();

            var chainAction = chainActionLetter.getChainAction();
            var letterPK = chainActionLetterValue.getLetterPK();

            chainActionLetter = ChainActionLetterFactory.getInstance().create(chainAction.getPrimaryKey(), letterPK, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            sendEvent(chainAction.getPrimaryKey(), EventTypes.MODIFY, chainActionLetter.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteChainActionLetter(ChainActionLetter chainActionLetter, BasePK deletedBy) {
        chainActionLetter.setThruTime(session.START_TIME_LONG);

        sendEvent(chainActionLetter.getChainActionPK(), EventTypes.MODIFY, chainActionLetter.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Chain Action Surveys
    // --------------------------------------------------------------------------------
    
    public ChainActionSurvey createChainActionSurvey(ChainAction chainAction, Survey survey, BasePK createdBy) {
        var chainActionSurvey = ChainActionSurveyFactory.getInstance().create(chainAction, survey, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        sendEvent(chainAction.getPrimaryKey(), EventTypes.MODIFY, chainActionSurvey.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return chainActionSurvey;
    }

    private ChainActionSurvey getChainActionSurvey(ChainAction chainAction, EntityPermission entityPermission) {
        ChainActionSurvey chainActionSurvey;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM chainactionsurveys " +
                        "WHERE chnactsrvy_chnact_chainactionid = ? AND chnactsrvy_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM chainactionsurveys " +
                        "WHERE chnactsrvy_chnact_chainactionid = ? AND chnactsrvy_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ChainActionSurveyFactory.getInstance().prepareStatement(query);

            ps.setLong(1, chainAction.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            chainActionSurvey = ChainActionSurveyFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch(SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return chainActionSurvey;
    }

    public ChainActionSurvey getChainActionSurvey(ChainAction chainAction) {
        return getChainActionSurvey(chainAction, EntityPermission.READ_ONLY);
    }

    public ChainActionSurvey getChainActionSurveyForUpdate(ChainAction chainAction) {
        return getChainActionSurvey(chainAction, EntityPermission.READ_WRITE);
    }

    public ChainActionSurveyValue getChainActionSurveyValue(ChainActionSurvey chainActionSurvey) {
        return chainActionSurvey == null ? null : chainActionSurvey.getChainActionSurveyValue().clone();
    }

    public ChainActionSurveyValue getChainActionSurveyValueForUpdate(ChainAction chainAction) {
        var chainActionSurvey = getChainActionSurveyForUpdate(chainAction);

        return chainActionSurvey == null ? null : chainActionSurvey.getChainActionSurveyValue().clone();
    }

    public ChainActionSurveyTransfer getChainActionSurveyTransfer(UserVisit userVisit, ChainAction chainAction) {
        return getChainTransferCaches().getChainActionSurveyTransferCache().getChainActionSurveyTransfer(userVisit, getChainActionSurvey(chainAction));
    }

    public void updateChainActionSurveyFromValue(ChainActionSurveyValue chainActionSurveyValue, BasePK updatedBy) {
        if(chainActionSurveyValue.hasBeenModified()) {
            var chainActionSurvey = ChainActionSurveyFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, chainActionSurveyValue.getPrimaryKey());

            chainActionSurvey.setThruTime(session.START_TIME_LONG);
            chainActionSurvey.store();

            var chainAction = chainActionSurvey.getChainAction();
            var surveyPK = chainActionSurveyValue.getSurveyPK();

            chainActionSurvey = ChainActionSurveyFactory.getInstance().create(chainAction.getPrimaryKey(), surveyPK, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            sendEvent(chainAction.getPrimaryKey(), EventTypes.MODIFY, chainActionSurvey.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteChainActionSurvey(ChainActionSurvey chainActionSurvey, BasePK deletedBy) {
        chainActionSurvey.setThruTime(session.START_TIME_LONG);

        sendEvent(chainActionSurvey.getChainActionPK(), EventTypes.MODIFY, chainActionSurvey.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Chain Action Chain Action Sets
    // --------------------------------------------------------------------------------
    
    public ChainActionChainActionSet createChainActionChainActionSet(ChainAction chainAction, ChainActionSet nextChainActionSet, Long delayTime, BasePK createdBy) {
        var chainActionChainActionSet = ChainActionChainActionSetFactory.getInstance().create(chainAction, nextChainActionSet, delayTime, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        sendEvent(chainAction.getPrimaryKey(), EventTypes.MODIFY, chainActionChainActionSet.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return chainActionChainActionSet;
    }

    private ChainActionChainActionSet getChainActionChainActionSet(ChainAction chainAction, EntityPermission entityPermission) {
        ChainActionChainActionSet chainActionChainActionSet;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM chainactionchainactionsets " +
                        "WHERE chnactchnactst_chnact_chainactionid = ? AND chnactchnactst_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM chainactionchainactionsets " +
                        "WHERE chnactchnactst_chnact_chainactionid = ? AND chnactchnactst_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ChainActionChainActionSetFactory.getInstance().prepareStatement(query);

            ps.setLong(1, chainAction.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            chainActionChainActionSet = ChainActionChainActionSetFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch(SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return chainActionChainActionSet;
    }

    public ChainActionChainActionSet getChainActionChainActionSet(ChainAction chainAction) {
        return getChainActionChainActionSet(chainAction, EntityPermission.READ_ONLY);
    }

    public ChainActionChainActionSet getChainActionChainActionSetForUpdate(ChainAction chainAction) {
        return getChainActionChainActionSet(chainAction, EntityPermission.READ_WRITE);
    }

    public ChainActionChainActionSetValue getChainActionChainActionSetValue(ChainActionChainActionSet chainActionChainActionSet) {
        return chainActionChainActionSet == null ? null : chainActionChainActionSet.getChainActionChainActionSetValue().clone();
    }

    public ChainActionChainActionSetValue getChainActionChainActionSetValueForUpdate(ChainAction chainAction) {
        var chainActionChainActionSet = getChainActionChainActionSetForUpdate(chainAction);

        return chainActionChainActionSet == null ? null : chainActionChainActionSet.getChainActionChainActionSetValue().clone();
    }

    public ChainActionChainActionSetTransfer getChainActionChainActionSetTransfer(UserVisit userVisit, ChainAction chainAction) {
        return getChainTransferCaches().getChainActionChainActionSetTransferCache().getChainActionChainActionSetTransfer(userVisit, getChainActionChainActionSet(chainAction));
    }

    public void updateChainActionChainActionSetFromValue(ChainActionChainActionSetValue chainActionChainActionSetValue, BasePK updatedBy) {
        if(chainActionChainActionSetValue.hasBeenModified()) {
            var chainActionChainActionSet = ChainActionChainActionSetFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     chainActionChainActionSetValue.getPrimaryKey());

            chainActionChainActionSet.setThruTime(session.START_TIME_LONG);
            chainActionChainActionSet.store();

            var chainAction = chainActionChainActionSet.getChainAction();
            var nextChainActionSetPK = chainActionChainActionSetValue.getNextChainActionSetPK();
            var delayTime = chainActionChainActionSetValue.getDelayTime();

            chainActionChainActionSet = ChainActionChainActionSetFactory.getInstance().create(chainAction.getPrimaryKey(), nextChainActionSetPK, delayTime, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            sendEvent(chainAction.getPrimaryKey(), EventTypes.MODIFY, chainActionChainActionSet.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteChainActionChainActionSet(ChainActionChainActionSet chainActionChainActionSet, BasePK deletedBy) {
        chainActionChainActionSet.setThruTime(session.START_TIME_LONG);

        sendEvent(chainActionChainActionSet.getChainActionPK(), EventTypes.MODIFY, chainActionChainActionSet.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Chain Instances
    // --------------------------------------------------------------------------------
    
    public ChainInstance createChainInstance(String chainInstanceName, ChainActionSet defaultChainActionSet, BasePK createdBy) {
        var chainInstance = ChainInstanceFactory.getInstance().create();
        var chainInstanceDetail = ChainInstanceDetailFactory.getInstance().create(chainInstance,
                chainInstanceName, defaultChainActionSet.getLastDetail().getChain(), session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        chainInstance = ChainInstanceFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                chainInstance.getPrimaryKey());
        chainInstance.setActiveDetail(chainInstanceDetail);
        chainInstance.setLastDetail(chainInstanceDetail);
        chainInstance.store();
        
        sendEvent(chainInstance.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        createChainInstanceStatus(chainInstance, defaultChainActionSet);
        
        return chainInstance;
    }

    public boolean isChainInstanceUsed(ChainInstance chainInstance) {
        var letterControl = Session.getModelController(LetterControl.class);

        return letterControl.isChainInstanceUsedByQueuedLetters(chainInstance);
    }

    private ChainInstance getChainInstanceByName(String chainInstanceName, EntityPermission entityPermission) {
        ChainInstance chainInstance;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM chaininstances, chaininstancedetails " +
                        "WHERE chni_activedetailid = chnidt_chaininstancedetailid AND chnidt_chaininstancename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM chaininstances, chaininstancedetails " +
                        "WHERE chni_activedetailid = chnidt_chaininstancedetailid AND chnidt_chaininstancename = ? " +
                        "FOR UPDATE";
            }

            var ps = ChainInstanceFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, chainInstanceName);
            
            chainInstance = ChainInstanceFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return chainInstance;
    }
    
    public ChainInstance getChainInstanceByName(String chainInstanceName) {
        return getChainInstanceByName(chainInstanceName, EntityPermission.READ_ONLY);
    }
    
    public ChainInstance getChainInstanceByNameForUpdate(String chainInstanceName) {
        return getChainInstanceByName(chainInstanceName, EntityPermission.READ_WRITE);
    }
    
    public ChainInstanceDetailValue getChainInstanceDetailValueForUpdate(ChainInstance chainInstance) {
        return chainInstance == null? null: chainInstance.getLastDetailForUpdate().getChainInstanceDetailValue().clone();
    }
    
    public ChainInstanceDetailValue getChainInstanceDetailValueByNameForUpdate(String chainInstanceName) {
        return getChainInstanceDetailValueForUpdate(getChainInstanceByNameForUpdate(chainInstanceName));
    }
    
    private List<ChainInstance> getChainInstancesByChain(Chain chain, EntityPermission entityPermission) {
        List<ChainInstance> chainInstances;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM chaininstances, chaininstancedetails " +
                        "WHERE chni_activedetailid = chnidt_chaininstancedetailid AND chnidt_chn_chainid = ? " +
                        "ORDER BY chnidt_chaininstancename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM chaininstances, chaininstancedetails " +
                        "WHERE chni_activedetailid = chnidt_chaininstancedetailid AND chnidt_chn_chainid = ? " +
                        "FOR UPDATE";
            }

            var ps = ChainInstanceFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, chain.getPrimaryKey().getEntityId());
            
            chainInstances = ChainInstanceFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return chainInstances;
    }
    
    public List<ChainInstance> getChainInstancesByChain(Chain chain) {
        return getChainInstancesByChain(chain, EntityPermission.READ_ONLY);
    }
    
    public List<ChainInstance> getChainInstancesByChainForUpdate(Chain chain) {
        return getChainInstancesByChain(chain, EntityPermission.READ_WRITE);
    }
    
    public ChainInstanceTransfer getChainInstanceTransfer(UserVisit userVisit, ChainInstance chainInstance) {
        return getChainTransferCaches().getChainInstanceTransferCache().getChainInstanceTransfer(userVisit, chainInstance);
    }
    
    public List<ChainInstanceTransfer> getChainInstanceTransfers(UserVisit userVisit, Collection<ChainInstance> chainInstances) {
        List<ChainInstanceTransfer> chainInstanceTransfers = new ArrayList<>(chainInstances.size());
        var chainInstanceTransferCache = getChainTransferCaches().getChainInstanceTransferCache();
        
        chainInstances.forEach((chainInstance) ->
                chainInstanceTransfers.add(chainInstanceTransferCache.getChainInstanceTransfer(userVisit, chainInstance))
        );
        
        return chainInstanceTransfers;
    }
    
    public List<ChainInstanceTransfer> getChainInstanceTransfersByChain(UserVisit userVisit, Chain chain) {
        return getChainInstanceTransfers(userVisit, getChainInstancesByChain(chain));
    }
    
    public void deleteChainInstance(ChainInstance chainInstance, BasePK deletedBy) {
        var letterControl = Session.getModelController(LetterControl.class);

        removeChainInstanceStatusByChainInstance(chainInstance);
        letterControl.removedQueuedLettersByChainInstance(chainInstance);
        deleteChainInstanceEntityRolesByChainInstance(chainInstance, deletedBy);

        var chainInstanceDetail = chainInstance.getLastDetailForUpdate();
        chainInstanceDetail.setThruTime(session.START_TIME_LONG);
        chainInstance.setActiveDetail(null);
        chainInstance.store();
        
        sendEvent(chainInstance.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteChainInstances(Collection<ChainInstance> chainInstances, BasePK deletedBy) {
        chainInstances.forEach((chainInstance) -> 
                deleteChainInstance(chainInstance, deletedBy)
        );
    }
    
    public void deleteChainInstancesByChain(Chain chain, BasePK deletedBy) {
        deleteChainInstances(getChainInstancesByChainForUpdate(chain), deletedBy);
    }

    public void deleteChainInstancesByNextChainActionSet(List<ChainInstanceStatus> chainInstanceStatuses, BasePK deletedBy) {
        chainInstanceStatuses.forEach((chainInstanceStatus) -> {
            deleteChainInstance(chainInstanceStatus.getChainInstance(), deletedBy);
        });
    }

    public void deleteChainInstancesByNextChainActionSet(ChainActionSet nextChainActionSet, BasePK deletedBy) {
        deleteChainInstancesByNextChainActionSet(getChainInstanceStatusesByNextChainActionSetForUpdate(nextChainActionSet), deletedBy);
    }

    /** Should be called each time that something that's depending on the ChainInstance being around is deleted or removed.
     * An example (the only example) of this right now is when QueuedLetters are removed.
     * @param chainInstance The ChainInstance to consider deleting.
     * @param deletedBy The PK of the Party that will be responsible for deleting the ChainInstance.
     */
    public void deleteChainInstanceIfUnused(ChainInstance chainInstance, BasePK deletedBy) {
        if(!isChainInstanceUsed(chainInstance)) {
            deleteChainInstance(chainInstance, deletedBy);
        }
    }

    // --------------------------------------------------------------------------------
    //   Chain Instance Statuses
    // --------------------------------------------------------------------------------
    
    public ChainInstanceStatus createChainInstanceStatus(ChainInstance chainInstance, ChainActionSet defaultChainActionSet) {
        return createChainInstanceStatus(chainInstance, defaultChainActionSet, session.START_TIME_LONG);
    }
    
    public ChainInstanceStatus createChainInstanceStatus(ChainInstance chainInstance, ChainActionSet nextChainActionSet, Long nextChainActionSetTime) {
        return ChainInstanceStatusFactory.getInstance().create(chainInstance, nextChainActionSet, nextChainActionSetTime, 0);
    }
    
    private static final Map<EntityPermission, String> getChainInstanceStatusQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM chaininstancestatuses "
                + "WHERE chnist_chni_chaininstanceid = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM chaininstancestatuses "
                + "WHERE chnist_chni_chaininstanceid = ? "
                + "FOR UPDATE");
        getChainInstanceStatusQueries = Collections.unmodifiableMap(queryMap);
    }

    public ChainInstanceStatus getChainInstanceStatus(ChainInstance chainInstance, EntityPermission entityPermission) {
        return ChainInstanceStatusFactory.getInstance().getEntityFromQuery(entityPermission, getChainInstanceStatusQueries,
                chainInstance);
    }

    public ChainInstanceStatus getChainInstanceStatus(ChainInstance chainInstance) {
        return getChainInstanceStatus(chainInstance, EntityPermission.READ_ONLY);
    }

    public ChainInstanceStatus getChainInstanceStatusForUpdate(ChainInstance chainInstance) {
        return getChainInstanceStatus(chainInstance, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getChainInstanceStatusesByNextChainActionSetQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM chaininstancestatuses "
                + "WHERE chnist_nextchainactionsetid = ? "
                + "ORDER BY chnist_nextchainactionsettime");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM chaininstancestatuses "
                + "WHERE chnist_nextchainactionsetid = ? "
                + "FOR UPDATE");
        getChainInstanceStatusesByNextChainActionSetQueries = Collections.unmodifiableMap(queryMap);
    }

    public List<ChainInstanceStatus> getChainInstanceStatusesByNextChainActionSet(ChainActionSet nextChainActionSet, EntityPermission entityPermission) {
        return ChainInstanceStatusFactory.getInstance().getEntitiesFromQuery(entityPermission, getChainInstanceStatusesByNextChainActionSetQueries,
                nextChainActionSet);
    }

    public List<ChainInstanceStatus> getChainInstanceStatusesByNextChainActionSet(ChainActionSet nextChainActionSet) {
        return getChainInstanceStatusesByNextChainActionSet(nextChainActionSet, EntityPermission.READ_ONLY);
    }

    public List<ChainInstanceStatus> getChainInstanceStatusesByNextChainActionSetForUpdate(ChainActionSet nextChainActionSet) {
        return getChainInstanceStatusesByNextChainActionSet(nextChainActionSet, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getChainInstanceStatusesByNextChainActionSetTimeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM chaininstancestatuses "
                + "WHERE chnist_nextchainactionsettime < ? "
                + "ORDER BY chnist_nextchainactionsettime");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM chaininstancestatuses "
                + "WHERE chnist_nextchainactionsettime < ? "
                + "FOR UPDATE");
        getChainInstanceStatusesByNextChainActionSetTimeQueries = Collections.unmodifiableMap(queryMap);
    }

    public List<ChainInstanceStatus> getChainInstanceStatusesByNextChainActionSetTime(Long nextChainActionSetTime, EntityPermission entityPermission) {
        return ChainInstanceStatusFactory.getInstance().getEntitiesFromQuery(entityPermission, getChainInstanceStatusesByNextChainActionSetTimeQueries,
                nextChainActionSetTime);
    }

    public List<ChainInstanceStatus> getChainInstanceStatusesByNextChainActionSetTime(Long nextChainActionSetTime) {
        return getChainInstanceStatusesByNextChainActionSetTime(nextChainActionSetTime, EntityPermission.READ_ONLY);
    }

    public List<ChainInstanceStatus> getChainInstanceStatusesByNextChainActionSetTimeForUpdate(Long nextChainActionSetTime) {
        return getChainInstanceStatusesByNextChainActionSetTime(nextChainActionSetTime, EntityPermission.READ_WRITE);
    }

    public ChainInstanceStatusTransfer getChainInstanceStatusTransfer(UserVisit userVisit, ChainInstanceStatus chainInstanceStatus) {
        return getChainTransferCaches().getChainInstanceStatusTransferCache().getChainInstanceStatusTransfer(userVisit, chainInstanceStatus);
    }

    public ChainInstanceStatusTransfer getChainInstanceStatusTransfer(UserVisit userVisit, ChainInstance chainInstance) {
        var chainInstanceStatus = getChainInstanceStatus(chainInstance);

        return chainInstanceStatus == null ? null : getChainInstanceStatusTransfer(userVisit, chainInstanceStatus);
    }

    public void removeChainInstanceStatusByChainInstance(ChainInstance chainInstance) {
        var chainInstanceStatus = getChainInstanceStatusForUpdate(chainInstance);

        if(chainInstanceStatus != null) {
            chainInstanceStatus.remove();
        }
    }

    // --------------------------------------------------------------------------------
    //   Chain Instance Entity Roles
    // --------------------------------------------------------------------------------
    
    public ChainInstanceEntityRole createChainInstanceEntityRole(ChainInstance chainInstance, ChainEntityRoleType chainEntityRoleType, BasePK pk,
            BasePK createdBy) {
        var entityInstance = getEntityInstanceByBasePK(pk);
        
        return createChainInstanceEntityRole(chainInstance, chainEntityRoleType, entityInstance, createdBy);
    }
    
    public ChainInstanceEntityRole createChainInstanceEntityRole(ChainInstance chainInstance, ChainEntityRoleType chainEntityRoleType,
            EntityInstance entityInstance, BasePK createdBy) {
        var chainInstanceEntityRole = ChainInstanceEntityRoleFactory.getInstance().create(session, chainInstance, chainEntityRoleType,
                entityInstance, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(chainInstance.getPrimaryKey(), EventTypes.MODIFY, chainInstanceEntityRole.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return chainInstanceEntityRole;
    }
    
    public long countChainInstanceEntityRolesByChainEntityRoleType(final ChainEntityRoleType chainEntityRoleType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM chaininstanceentityroles " +
                "WHERE chnier_chnertyp_chainentityroletypeid = ? AND chnier_thrutime = ?",
                chainEntityRoleType, Session.MAX_TIME);
    }

    public long countChainInstanceEntityRolesByEntityInstance(final EntityInstance entityInstance) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM chaininstanceentityroles " +
                "WHERE chnier_eni_entityinstanceid = ? AND chnier_thrutime = ?",
                entityInstance, Session.MAX_TIME);
    }

    public long countChainInstanceEntityRoles(final ChainEntityRoleType chainEntityRoleType, final EntityInstance entityInstance) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM chaininstanceentityroles " +
                "WHERE chnier_chnertyp_chainentityroletypeid = ? AND chnier_eni_entityinstanceid = ? AND chnier_thrutime = ?",
                chainEntityRoleType, entityInstance, Session.MAX_TIME);
    }
    
    public ChainInstanceEntityRole getChainInstanceEntityRole(ChainInstance chainInstance,
            ChainEntityRoleType chainEntityRoleType) {
        ChainInstanceEntityRole chainInstanceEntityRole;
        
        try {
            var ps = ChainInstanceEntityRoleFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM chaininstanceentityroles " +
                    "WHERE chnier_chni_chaininstanceid = ? AND chnier_chnertyp_chainentityroletypeid = ? AND chnier_thrutime = ?");
            
            ps.setLong(1, chainInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, chainEntityRoleType.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            chainInstanceEntityRole = ChainInstanceEntityRoleFactory.getInstance().getEntityFromQuery(session,
                    EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return chainInstanceEntityRole;
    }
    
    private List<ChainInstanceEntityRole> getChainInstanceEntityRoles(final ChainEntityRoleType chainEntityRoleType, final EntityInstance entityInstance, EntityPermission entityPermission) {
        List<ChainInstanceEntityRole> chainInstanceEntityRoles;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM chaininstanceentityroles " +
                        "WHERE chnier_chnertyp_chainentityroletypeid = ? AND chnier_eni_entityinstanceid = ? AND chnier_thrutime = ? " +
                        "ORDER BY chnier_chni_chaininstanceid"; // TODO: this isn't quite right
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM chaininstanceentityroles " +
                        "WHERE chnier_chnertyp_chainentityroletypeid = ? AND chnier_eni_entityinstanceid = ? AND chnier_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ChainInstanceEntityRoleFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, chainEntityRoleType.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            chainInstanceEntityRoles = ChainInstanceEntityRoleFactory.getInstance().getEntitiesFromQuery(session,
                    entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return chainInstanceEntityRoles;
    }
    
    public List<ChainInstanceEntityRole> getChainInstanceEntityRoles(final ChainEntityRoleType chainEntityRoleType, final EntityInstance entityInstance) {
        return getChainInstanceEntityRoles(chainEntityRoleType, entityInstance, EntityPermission.READ_ONLY);
    }
    
    public List<ChainInstanceEntityRole> getChainInstanceEntityRolesForUpdate(final ChainEntityRoleType chainEntityRoleType, final EntityInstance entityInstance) {
        return getChainInstanceEntityRoles(chainEntityRoleType, entityInstance, EntityPermission.READ_WRITE);
    }
    
    private List<ChainInstanceEntityRole> getChainInstanceEntityRolesByChainInstance(ChainInstance chainInstance, EntityPermission entityPermission) {
        List<ChainInstanceEntityRole> chainInstanceEntityRoles;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ "
                        + "FROM chaininstanceentityroles, chainentityroletypes, chainentityroletypedetails "
                        + "WHERE chnier_chni_chaininstanceid = ? AND chnier_thrutime = ? "
                        + "AND chnier_chnertyp_chainentityroletypeid = chnertyp_chainentityroletypeid "
                        + "AND chnertyp_lastdetailid = chnertypdt_chainentityroletypedetailid "
                        + "ORDER BY chnertypdt_sortorder, chnertypdt_chainentityroletypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ "
                        + "FROM chaininstanceentityroles "
                        + "WHERE chnier_chni_chaininstanceid = ? AND chnier_thrutime = ? "
                        + "FOR UPDATE";
            }

            var ps = ChainInstanceEntityRoleFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, chainInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            chainInstanceEntityRoles = ChainInstanceEntityRoleFactory.getInstance().getEntitiesFromQuery(session,
                    entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return chainInstanceEntityRoles;
    }
    
    public List<ChainInstanceEntityRole> getChainInstanceEntityRolesByChainInstance(ChainInstance chainInstance) {
        return getChainInstanceEntityRolesByChainInstance(chainInstance, EntityPermission.READ_ONLY);
    }
    
    public List<ChainInstanceEntityRole> getChainInstanceEntityRolesByChainInstanceForUpdate(ChainInstance chainInstance) {
        return getChainInstanceEntityRolesByChainInstance(chainInstance, EntityPermission.READ_WRITE);
    }
    
    private List<ChainInstanceEntityRole> getChainInstanceEntityRolesByChainEntityRoleType(ChainEntityRoleType chainEntityRoleType, EntityPermission entityPermission) {
        List<ChainInstanceEntityRole> chainInstanceEntityRoles;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM chaininstanceentityroles " +
                        "WHERE chnier_chnertyp_chainentityroletypeid = ? AND chnier_thrutime = ? " +
                        "ORDER BY chnier_chaininstanceentityroleid"; // TODO: this should probably be ordered by something else
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM chaininstanceentityroles " +
                        "WHERE chnier_chnertyp_chainentityroletypeid = ? AND chnier_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ChainInstanceEntityRoleFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, chainEntityRoleType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            chainInstanceEntityRoles = ChainInstanceEntityRoleFactory.getInstance().getEntitiesFromQuery(session, entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return chainInstanceEntityRoles;
    }
    
    public List<ChainInstanceEntityRole> getChainInstanceEntityRolesByChainEntityRoleType(ChainEntityRoleType chainEntityRoleType) {
        return getChainInstanceEntityRolesByChainEntityRoleType(chainEntityRoleType, EntityPermission.READ_ONLY);
    }
    
    public List<ChainInstanceEntityRole> getChainInstanceEntityRolesByChainEntityRoleTypeForUpdate(ChainEntityRoleType chainEntityRoleType) {
        return getChainInstanceEntityRolesByChainEntityRoleType(chainEntityRoleType, EntityPermission.READ_WRITE);
    }
    
    private List<ChainInstanceEntityRole> getChainInstanceEntityRolesByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        List<ChainInstanceEntityRole> chainInstanceEntityRoles;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM chaininstanceentityroles " +
                        "WHERE chnier_eni_entityinstanceid = ? AND chnier_thrutime = ? " +
                        "ORDER BY chnier_chaininstanceentityroleid"; // TODO: this should probably be ordered by something else
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM chaininstanceentityroles " +
                        "WHERE chnier_chni_chaininstanceid = ? AND chnier_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ChainInstanceEntityRoleFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            chainInstanceEntityRoles = ChainInstanceEntityRoleFactory.getInstance().getEntitiesFromQuery(session,
                    entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return chainInstanceEntityRoles;
    }
    
    public List<ChainInstanceEntityRole> getChainInstanceEntityRolesByEntityInstance(EntityInstance entityInstance) {
        return getChainInstanceEntityRolesByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }
    
    public List<ChainInstanceEntityRole> getChainInstanceEntityRolesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getChainInstanceEntityRolesByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    public ChainInstanceEntityRoleTransfer getChainInstanceEntityRoleTransfer(UserVisit userVisit,
            ChainInstanceEntityRole chainInstanceEntityRole) {
        return getChainTransferCaches().getChainInstanceEntityRoleTransferCache().getChainInstanceEntityRoleTransfer(userVisit, chainInstanceEntityRole);
    }
    
    public List<ChainInstanceEntityRoleTransfer> getChainInstanceEntityRoleTransfers(UserVisit userVisit, Collection<ChainInstanceEntityRole> chainInstanceEntityRoles) {
        List<ChainInstanceEntityRoleTransfer> chainInstanceEntityRoleTransfers = new ArrayList<>(chainInstanceEntityRoles.size());
        var chainInstanceEntityRoleTransferCache = getChainTransferCaches().getChainInstanceEntityRoleTransferCache();
        
        chainInstanceEntityRoles.forEach((chainInstanceEntityRole) ->
                chainInstanceEntityRoleTransfers.add(chainInstanceEntityRoleTransferCache.getChainInstanceEntityRoleTransfer(userVisit, chainInstanceEntityRole))
        );
        
        return chainInstanceEntityRoleTransfers;
    }
    
    public List<ChainInstanceEntityRoleTransfer> getChainInstanceEntityRoleTransfersByChainInstance(UserVisit userVisit, ChainInstance chainInstance) {
        return getChainInstanceEntityRoleTransfers(userVisit, getChainInstanceEntityRolesByChainInstance(chainInstance));
    }
    
    public void deleteChainInstanceEntityRole(ChainInstanceEntityRole chainInstanceEntityRole, BasePK deletedBy) {
        chainInstanceEntityRole.setThruTime(session.START_TIME_LONG);
        
        sendEvent(chainInstanceEntityRole.getChainInstancePK(), EventTypes.MODIFY, chainInstanceEntityRole.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteChainInstanceEntityRoles(List<ChainInstanceEntityRole> chainInstanceEntityRoles, BasePK deletedBy) {
        chainInstanceEntityRoles.forEach((chainInstanceEntityRole) -> 
                deleteChainInstanceEntityRole(chainInstanceEntityRole, deletedBy)
        );
    }
    
    public void deleteChainInstanceEntityRolesByChainInstance(ChainInstance chainInstance, BasePK deletedBy) {
        deleteChainInstanceEntityRoles(getChainInstanceEntityRolesByChainInstanceForUpdate(chainInstance), deletedBy);
    }
    
    public void deleteChainInstanceEntityRolesByChainEntityRoleType(ChainEntityRoleType chainEntityRoleType, BasePK deletedBy) {
        deleteChainInstanceEntityRoles(getChainInstanceEntityRolesByChainEntityRoleTypeForUpdate(chainEntityRoleType), deletedBy);
    }

    public void deleteChainInstanceEntityRolesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteChainInstanceEntityRoles(getChainInstanceEntityRolesByEntityInstanceForUpdate(entityInstance), deletedBy);
    }
    
}
