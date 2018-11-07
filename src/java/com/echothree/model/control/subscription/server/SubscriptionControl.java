// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.model.control.subscription.server;

import com.echothree.model.control.club.server.ClubControl;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.sequence.common.SequenceConstants;
import com.echothree.model.control.sequence.server.SequenceControl;
import com.echothree.model.control.subscription.common.SubscriptionConstants;
import com.echothree.model.control.subscription.common.choice.SubscriptionKindChoicesBean;
import com.echothree.model.control.subscription.common.choice.SubscriptionTypeChoicesBean;
import com.echothree.model.control.subscription.common.transfer.SubscriptionKindDescriptionTransfer;
import com.echothree.model.control.subscription.common.transfer.SubscriptionKindTransfer;
import com.echothree.model.control.subscription.common.transfer.SubscriptionTransfer;
import com.echothree.model.control.subscription.common.transfer.SubscriptionTypeChainTransfer;
import com.echothree.model.control.subscription.common.transfer.SubscriptionTypeDescriptionTransfer;
import com.echothree.model.control.subscription.common.transfer.SubscriptionTypeTransfer;
import com.echothree.model.control.subscription.server.transfer.SubscriptionKindTransferCache;
import com.echothree.model.control.subscription.server.transfer.SubscriptionTransferCache;
import com.echothree.model.control.subscription.server.transfer.SubscriptionTransferCaches;
import com.echothree.model.control.subscription.server.transfer.SubscriptionTypeChainTransferCache;
import com.echothree.model.control.subscription.server.transfer.SubscriptionTypeTransferCache;
import com.echothree.model.data.chain.common.pk.ChainPK;
import com.echothree.model.data.chain.server.entity.Chain;
import com.echothree.model.data.chain.server.entity.ChainType;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.sequence.common.pk.SequencePK;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.subscription.common.pk.SubscriptionKindPK;
import com.echothree.model.data.subscription.common.pk.SubscriptionPK;
import com.echothree.model.data.subscription.common.pk.SubscriptionTypePK;
import com.echothree.model.data.subscription.server.entity.Subscription;
import com.echothree.model.data.subscription.server.entity.SubscriptionDetail;
import com.echothree.model.data.subscription.server.entity.SubscriptionKind;
import com.echothree.model.data.subscription.server.entity.SubscriptionKindDescription;
import com.echothree.model.data.subscription.server.entity.SubscriptionKindDetail;
import com.echothree.model.data.subscription.server.entity.SubscriptionType;
import com.echothree.model.data.subscription.server.entity.SubscriptionTypeChain;
import com.echothree.model.data.subscription.server.entity.SubscriptionTypeDescription;
import com.echothree.model.data.subscription.server.entity.SubscriptionTypeDetail;
import com.echothree.model.data.subscription.server.factory.SubscriptionDetailFactory;
import com.echothree.model.data.subscription.server.factory.SubscriptionFactory;
import com.echothree.model.data.subscription.server.factory.SubscriptionKindDescriptionFactory;
import com.echothree.model.data.subscription.server.factory.SubscriptionKindDetailFactory;
import com.echothree.model.data.subscription.server.factory.SubscriptionKindFactory;
import com.echothree.model.data.subscription.server.factory.SubscriptionTypeChainFactory;
import com.echothree.model.data.subscription.server.factory.SubscriptionTypeDescriptionFactory;
import com.echothree.model.data.subscription.server.factory.SubscriptionTypeDetailFactory;
import com.echothree.model.data.subscription.server.factory.SubscriptionTypeFactory;
import com.echothree.model.data.subscription.server.value.SubscriptionDetailValue;
import com.echothree.model.data.subscription.server.value.SubscriptionKindDescriptionValue;
import com.echothree.model.data.subscription.server.value.SubscriptionKindDetailValue;
import com.echothree.model.data.subscription.server.value.SubscriptionTypeChainValue;
import com.echothree.model.data.subscription.server.value.SubscriptionTypeDescriptionValue;
import com.echothree.model.data.subscription.server.value.SubscriptionTypeDetailValue;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SubscriptionControl
        extends BaseModelControl {
    
    /** Creates a new instance of SubscriptionControl */
    public SubscriptionControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Subscription Transfer Caches
    // --------------------------------------------------------------------------------
    
    private SubscriptionTransferCaches subscriptionTransferCaches = null;
    
    public SubscriptionTransferCaches getSubscriptionTransferCaches(UserVisit userVisit) {
        if(subscriptionTransferCaches == null) {
            subscriptionTransferCaches = new SubscriptionTransferCaches(userVisit, this);
        }
        
        return subscriptionTransferCaches;
    }
    
    // --------------------------------------------------------------------------------
    //   Subscription Kinds
    // --------------------------------------------------------------------------------

    public SubscriptionKind createSubscriptionKind(String subscriptionKindName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        SubscriptionKind defaultSubscriptionKind = getDefaultSubscriptionKind();
        boolean defaultFound = defaultSubscriptionKind != null;

        if(defaultFound && isDefault) {
            SubscriptionKindDetailValue defaultSubscriptionKindDetailValue = getDefaultSubscriptionKindDetailValueForUpdate();

            defaultSubscriptionKindDetailValue.setIsDefault(Boolean.FALSE);
            updateSubscriptionKindFromValue(defaultSubscriptionKindDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        SubscriptionKind subscriptionKind = SubscriptionKindFactory.getInstance().create();
        SubscriptionKindDetail subscriptionKindDetail = SubscriptionKindDetailFactory.getInstance().create(subscriptionKind, subscriptionKindName, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        subscriptionKind = SubscriptionKindFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                subscriptionKind.getPrimaryKey());
        subscriptionKind.setActiveDetail(subscriptionKindDetail);
        subscriptionKind.setLastDetail(subscriptionKindDetail);
        subscriptionKind.store();

        sendEventUsingNames(subscriptionKind.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return subscriptionKind;
    }

    private static final Map<EntityPermission, String> getSubscriptionKindByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM subscriptionkinds, subscriptionkinddetails "
                + "WHERE subscrk_activedetailid = subscrkdt_subscriptionkinddetailid AND subscrkdt_subscriptionkindname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM subscriptionkinds, subscriptionkinddetails "
                + "WHERE subscrk_activedetailid = subscrkdt_subscriptionkinddetailid AND subscrkdt_subscriptionkindname = ? "
                + "FOR UPDATE");
        getSubscriptionKindByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private SubscriptionKind getSubscriptionKindByName(String subscriptionKindName, EntityPermission entityPermission) {
        return SubscriptionKindFactory.getInstance().getEntityFromQuery(entityPermission, getSubscriptionKindByNameQueries,
                subscriptionKindName);
    }

    public SubscriptionKind getSubscriptionKindByName(String subscriptionKindName) {
        return getSubscriptionKindByName(subscriptionKindName, EntityPermission.READ_ONLY);
    }

    public SubscriptionKind getSubscriptionKindByNameForUpdate(String subscriptionKindName) {
        return getSubscriptionKindByName(subscriptionKindName, EntityPermission.READ_WRITE);
    }

    public SubscriptionKindDetailValue getSubscriptionKindDetailValueForUpdate(SubscriptionKind subscriptionKind) {
        return subscriptionKind == null? null: subscriptionKind.getLastDetailForUpdate().getSubscriptionKindDetailValue().clone();
    }

    public SubscriptionKindDetailValue getSubscriptionKindDetailValueByNameForUpdate(String subscriptionKindName) {
        return getSubscriptionKindDetailValueForUpdate(getSubscriptionKindByNameForUpdate(subscriptionKindName));
    }

    private static final Map<EntityPermission, String> getDefaultSubscriptionKindQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM subscriptionkinds, subscriptionkinddetails "
                + "WHERE subscrk_activedetailid = subscrkdt_subscriptionkinddetailid AND subscrkdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM subscriptionkinds, subscriptionkinddetails "
                + "WHERE subscrk_activedetailid = subscrkdt_subscriptionkinddetailid AND subscrkdt_isdefault = 1 "
                + "FOR UPDATE");
        getDefaultSubscriptionKindQueries = Collections.unmodifiableMap(queryMap);
    }

    private SubscriptionKind getDefaultSubscriptionKind(EntityPermission entityPermission) {
        return SubscriptionKindFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultSubscriptionKindQueries);
    }

    public SubscriptionKind getDefaultSubscriptionKind() {
        return getDefaultSubscriptionKind(EntityPermission.READ_ONLY);
    }

    public SubscriptionKind getDefaultSubscriptionKindForUpdate() {
        return getDefaultSubscriptionKind(EntityPermission.READ_WRITE);
    }

    public SubscriptionKindDetailValue getDefaultSubscriptionKindDetailValueForUpdate() {
        return getDefaultSubscriptionKind(EntityPermission.READ_WRITE).getLastDetailForUpdate().getSubscriptionKindDetailValue();
    }

    private static final Map<EntityPermission, String> getSubscriptionKindsQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM subscriptionkinds, subscriptionkinddetails "
                + "WHERE subscrk_activedetailid = subscrkdt_subscriptionkinddetailid "
                + "ORDER BY subscrkdt_sortorder, subscrkdt_subscriptionkindname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM subscriptionkinds, subscriptionkinddetails "
                + "WHERE subscrk_activedetailid = subscrkdt_subscriptionkinddetailid "
                + "FOR UPDATE");
        getSubscriptionKindsQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<SubscriptionKind> getSubscriptionKinds(EntityPermission entityPermission) {
        return SubscriptionKindFactory.getInstance().getEntitiesFromQuery(entityPermission, getSubscriptionKindsQueries);
    }

    public List<SubscriptionKind> getSubscriptionKinds() {
        return getSubscriptionKinds(EntityPermission.READ_ONLY);
    }

    public List<SubscriptionKind> getSubscriptionKindsForUpdate() {
        return getSubscriptionKinds(EntityPermission.READ_WRITE);
    }

    public SubscriptionKindChoicesBean getSubscriptionKindChoices(String defaultSubscriptionKindChoice, Language language, boolean allowNullChoice) {
        List<SubscriptionKind> subscriptionKinds = getSubscriptionKinds();
        int size = subscriptionKinds.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultSubscriptionKindChoice == null) {
                defaultValue = "";
            }
        }

        for(SubscriptionKind subscriptionKind: subscriptionKinds) {
            SubscriptionKindDetail subscriptionKindDetail = subscriptionKind.getLastDetail();

            String label = getBestSubscriptionKindDescription(subscriptionKind, language);
            String value = subscriptionKindDetail.getSubscriptionKindName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultSubscriptionKindChoice == null? false: defaultSubscriptionKindChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && subscriptionKindDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new SubscriptionKindChoicesBean(labels, values, defaultValue);
    }

    public SubscriptionKindTransfer getSubscriptionKindTransfer(UserVisit userVisit, SubscriptionKind subscriptionKind) {
        return getSubscriptionTransferCaches(userVisit).getSubscriptionKindTransferCache().getSubscriptionKindTransfer(subscriptionKind);
    }

    public List<SubscriptionKindTransfer> getSubscriptionKindTransfers(UserVisit userVisit) {
        List<SubscriptionKind> subscriptionKinds = getSubscriptionKinds();
        List<SubscriptionKindTransfer> subscriptionKindTransfers = new ArrayList<>(subscriptionKinds.size());
        SubscriptionKindTransferCache subscriptionKindTransferCache = getSubscriptionTransferCaches(userVisit).getSubscriptionKindTransferCache();

        subscriptionKinds.stream().forEach((subscriptionKind) -> {
            subscriptionKindTransfers.add(subscriptionKindTransferCache.getSubscriptionKindTransfer(subscriptionKind));
        });

        return subscriptionKindTransfers;
    }

    private void updateSubscriptionKindFromValue(SubscriptionKindDetailValue subscriptionKindDetailValue, boolean checkDefault, BasePK updatedBy) {
        SubscriptionKind subscriptionKind = SubscriptionKindFactory.getInstance().getEntityFromPK(session,
                EntityPermission.READ_WRITE, subscriptionKindDetailValue.getSubscriptionKindPK());
        SubscriptionKindDetail subscriptionKindDetail = subscriptionKind.getActiveDetailForUpdate();

        subscriptionKindDetail.setThruTime(session.START_TIME_LONG);
        subscriptionKindDetail.store();

        SubscriptionKindPK subscriptionKindPK = subscriptionKindDetail.getSubscriptionKindPK();
        String subscriptionKindName = subscriptionKindDetailValue.getSubscriptionKindName();
        Boolean isDefault = subscriptionKindDetailValue.getIsDefault();
        Integer sortOrder = subscriptionKindDetailValue.getSortOrder();

        if(checkDefault) {
            SubscriptionKind defaultSubscriptionKind = getDefaultSubscriptionKind();
            boolean defaultFound = defaultSubscriptionKind != null && !defaultSubscriptionKind.equals(subscriptionKind);

            if(isDefault && defaultFound) {
                // If I'm the default, and a default already existed...
                SubscriptionKindDetailValue defaultSubscriptionKindDetailValue = getDefaultSubscriptionKindDetailValueForUpdate();

                defaultSubscriptionKindDetailValue.setIsDefault(Boolean.FALSE);
                updateSubscriptionKindFromValue(defaultSubscriptionKindDetailValue, false, updatedBy);
            } else if(!isDefault && !defaultFound) {
                // If I'm not the default, and no other default exists...
                isDefault = Boolean.TRUE;
            }
        }

        subscriptionKindDetail = SubscriptionKindDetailFactory.getInstance().create(subscriptionKindPK, subscriptionKindName, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        subscriptionKind.setActiveDetail(subscriptionKindDetail);
        subscriptionKind.setLastDetail(subscriptionKindDetail);
        subscriptionKind.store();

        sendEventUsingNames(subscriptionKindPK, EventTypes.MODIFY.name(), null, null, updatedBy);
    }

    public void updateSubscriptionKindFromValue(SubscriptionKindDetailValue subscriptionKindDetailValue, BasePK updatedBy) {
        updateSubscriptionKindFromValue(subscriptionKindDetailValue, true, updatedBy);
    }

    public void deleteSubscriptionKind(SubscriptionKind subscriptionKind, BasePK deletedBy) {
        deleteSubscriptionKindDescriptionsBySubscriptionKind(subscriptionKind, deletedBy);

        SubscriptionKindDetail subscriptionKindDetail = subscriptionKind.getLastDetailForUpdate();
        subscriptionKindDetail.setThruTime(session.START_TIME_LONG);
        subscriptionKind.setActiveDetail(null);
        subscriptionKind.store();

        // Check for default, and pick one if necessary
        SubscriptionKind defaultSubscriptionKind = getDefaultSubscriptionKind();
        if(defaultSubscriptionKind == null) {
            List<SubscriptionKind> subscriptionKinds = getSubscriptionKindsForUpdate();

            if(!subscriptionKinds.isEmpty()) {
                Iterator<SubscriptionKind> iter = subscriptionKinds.iterator();
                if(iter.hasNext()) {
                    defaultSubscriptionKind = iter.next();
                }
                SubscriptionKindDetailValue subscriptionKindDetailValue = defaultSubscriptionKind.getLastDetailForUpdate().getSubscriptionKindDetailValue().clone();

                subscriptionKindDetailValue.setIsDefault(Boolean.TRUE);
                updateSubscriptionKindFromValue(subscriptionKindDetailValue, false, deletedBy);
            }
        }

        sendEventUsingNames(subscriptionKind.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Subscription Kind Descriptions
    // --------------------------------------------------------------------------------

    public SubscriptionKindDescription createSubscriptionKindDescription(SubscriptionKind subscriptionKind, Language language, String description,
            BasePK createdBy) {
        SubscriptionKindDescription subscriptionKindDescription = SubscriptionKindDescriptionFactory.getInstance().create(subscriptionKind,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(subscriptionKind.getPrimaryKey(), EventTypes.MODIFY.name(), subscriptionKindDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return subscriptionKindDescription;
    }

    private static final Map<EntityPermission, String> getSubscriptionKindDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM subscriptionkinddescriptions "
                + "WHERE subscrkd_subscrk_subscriptionkindid = ? AND subscrkd_lang_languageid = ? AND subscrkd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM subscriptionkinddescriptions "
                + "WHERE subscrkd_subscrk_subscriptionkindid = ? AND subscrkd_lang_languageid = ? AND subscrkd_thrutime = ? "
                + "FOR UPDATE");
        getSubscriptionKindDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private SubscriptionKindDescription getSubscriptionKindDescription(SubscriptionKind subscriptionKind, Language language, EntityPermission entityPermission) {
        return SubscriptionKindDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getSubscriptionKindDescriptionQueries,
                subscriptionKind, language, Session.MAX_TIME);
    }

    public SubscriptionKindDescription getSubscriptionKindDescription(SubscriptionKind subscriptionKind, Language language) {
        return getSubscriptionKindDescription(subscriptionKind, language, EntityPermission.READ_ONLY);
    }

    public SubscriptionKindDescription getSubscriptionKindDescriptionForUpdate(SubscriptionKind subscriptionKind, Language language) {
        return getSubscriptionKindDescription(subscriptionKind, language, EntityPermission.READ_WRITE);
    }

    public SubscriptionKindDescriptionValue getSubscriptionKindDescriptionValue(SubscriptionKindDescription subscriptionKindDescription) {
        return subscriptionKindDescription == null? null: subscriptionKindDescription.getSubscriptionKindDescriptionValue().clone();
    }

    public SubscriptionKindDescriptionValue getSubscriptionKindDescriptionValueForUpdate(SubscriptionKind subscriptionKind, Language language) {
        return getSubscriptionKindDescriptionValue(getSubscriptionKindDescriptionForUpdate(subscriptionKind, language));
    }

    private static final Map<EntityPermission, String> getSubscriptionKindDescriptionsBySubscriptionKindQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM subscriptionkinddescriptions, languages "
                + "WHERE subscrkd_subscrk_subscriptionkindid = ? AND subscrkd_thrutime = ? AND subscrkd_lang_languageid = lang_languageid "
                + "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM subscriptionkinddescriptions "
                + "WHERE subscrkd_subscrk_subscriptionkindid = ? AND subscrkd_thrutime = ? "
                + "FOR UPDATE");
        getSubscriptionKindDescriptionsBySubscriptionKindQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<SubscriptionKindDescription> getSubscriptionKindDescriptionsBySubscriptionKind(SubscriptionKind subscriptionKind, EntityPermission entityPermission) {
        return SubscriptionKindDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getSubscriptionKindDescriptionsBySubscriptionKindQueries,
                subscriptionKind, Session.MAX_TIME);
    }

    public List<SubscriptionKindDescription> getSubscriptionKindDescriptionsBySubscriptionKind(SubscriptionKind subscriptionKind) {
        return getSubscriptionKindDescriptionsBySubscriptionKind(subscriptionKind, EntityPermission.READ_ONLY);
    }

    public List<SubscriptionKindDescription> getSubscriptionKindDescriptionsBySubscriptionKindForUpdate(SubscriptionKind subscriptionKind) {
        return getSubscriptionKindDescriptionsBySubscriptionKind(subscriptionKind, EntityPermission.READ_WRITE);
    }

    public String getBestSubscriptionKindDescription(SubscriptionKind subscriptionKind, Language language) {
        String description;
        SubscriptionKindDescription subscriptionKindDescription = getSubscriptionKindDescription(subscriptionKind, language);

        if(subscriptionKindDescription == null && !language.getIsDefault()) {
            subscriptionKindDescription = getSubscriptionKindDescription(subscriptionKind, getPartyControl().getDefaultLanguage());
        }

        if(subscriptionKindDescription == null) {
            description = subscriptionKind.getLastDetail().getSubscriptionKindName();
        } else {
            description = subscriptionKindDescription.getDescription();
        }

        return description;
    }

    public SubscriptionKindDescriptionTransfer getSubscriptionKindDescriptionTransfer(UserVisit userVisit, SubscriptionKindDescription subscriptionKindDescription) {
        return getSubscriptionTransferCaches(userVisit).getSubscriptionKindDescriptionTransferCache().getSubscriptionKindDescriptionTransfer(subscriptionKindDescription);
    }

    public List<SubscriptionKindDescriptionTransfer> getSubscriptionKindDescriptionTransfersBySubscriptionKind(UserVisit userVisit, SubscriptionKind subscriptionKind) {
        List<SubscriptionKindDescription> subscriptionKindDescriptions = getSubscriptionKindDescriptionsBySubscriptionKind(subscriptionKind);
        List<SubscriptionKindDescriptionTransfer> subscriptionKindDescriptionTransfers = new ArrayList<>(subscriptionKindDescriptions.size());

        subscriptionKindDescriptions.stream().forEach((subscriptionKindDescription) -> {
            subscriptionKindDescriptionTransfers.add(getSubscriptionTransferCaches(userVisit).getSubscriptionKindDescriptionTransferCache().getSubscriptionKindDescriptionTransfer(subscriptionKindDescription));
        });

        return subscriptionKindDescriptionTransfers;
    }

    public void updateSubscriptionKindDescriptionFromValue(SubscriptionKindDescriptionValue subscriptionKindDescriptionValue, BasePK updatedBy) {
        if(subscriptionKindDescriptionValue.hasBeenModified()) {
            SubscriptionKindDescription subscriptionKindDescription = SubscriptionKindDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     subscriptionKindDescriptionValue.getPrimaryKey());

            subscriptionKindDescription.setThruTime(session.START_TIME_LONG);
            subscriptionKindDescription.store();

            SubscriptionKind subscriptionKind = subscriptionKindDescription.getSubscriptionKind();
            Language language = subscriptionKindDescription.getLanguage();
            String description = subscriptionKindDescriptionValue.getDescription();

            subscriptionKindDescription = SubscriptionKindDescriptionFactory.getInstance().create(subscriptionKind, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(subscriptionKind.getPrimaryKey(), EventTypes.MODIFY.name(), subscriptionKindDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteSubscriptionKindDescription(SubscriptionKindDescription subscriptionKindDescription, BasePK deletedBy) {
        subscriptionKindDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(subscriptionKindDescription.getSubscriptionKindPK(), EventTypes.MODIFY.name(), subscriptionKindDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteSubscriptionKindDescriptionsBySubscriptionKind(SubscriptionKind subscriptionKind, BasePK deletedBy) {
        List<SubscriptionKindDescription> subscriptionKindDescriptions = getSubscriptionKindDescriptionsBySubscriptionKindForUpdate(subscriptionKind);

        subscriptionKindDescriptions.stream().forEach((subscriptionKindDescription) -> {
            deleteSubscriptionKindDescription(subscriptionKindDescription, deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //   Subscription Types
    // --------------------------------------------------------------------------------
    
    public SubscriptionType createSubscriptionType(SubscriptionKind subscriptionKind, String subscriptionTypeName,
            Sequence subscriptionSequence, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        SubscriptionType defaultSubscriptionType = getDefaultSubscriptionType(subscriptionKind);
        boolean defaultFound = defaultSubscriptionType != null;
        
        if(defaultFound && isDefault) {
            SubscriptionTypeDetailValue defaultSubscriptionTypeDetailValue = getDefaultSubscriptionTypeDetailValueForUpdate(subscriptionKind);
            
            defaultSubscriptionTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateSubscriptionTypeFromValue(defaultSubscriptionTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        SubscriptionType subscriptionType = SubscriptionTypeFactory.getInstance().create();
        SubscriptionTypeDetail subscriptionTypeDetail = SubscriptionTypeDetailFactory.getInstance().create(session,
                subscriptionType, subscriptionKind, subscriptionTypeName, subscriptionSequence, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        subscriptionType = SubscriptionTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                subscriptionType.getPrimaryKey());
        subscriptionType.setActiveDetail(subscriptionTypeDetail);
        subscriptionType.setLastDetail(subscriptionTypeDetail);
        subscriptionType.store();
        
        sendEventUsingNames(subscriptionType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return subscriptionType;
    }
    
    private List<SubscriptionType> getSubscriptionTypesBySubscriptionKind(SubscriptionKind subscriptionKind,
            EntityPermission entityPermission) {
        List<SubscriptionType> subscriptionTypes = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM subscriptiontypes, subscriptiontypedetails " +
                        "WHERE subscrtyp_activedetailid = subscrtypdt_subscriptiontypedetailid " +
                        "AND subscrtypdt_subscrk_subscriptionkindid = ? " +
                        "ORDER BY subscrtypdt_sortorder, subscrtypdt_subscriptiontypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM subscriptiontypes, subscriptiontypedetails " +
                        "WHERE subscrtyp_activedetailid = subscrtypdt_subscriptiontypedetailid " +
                        "AND subscrtypdt_subscrk_subscriptionkindid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SubscriptionTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, subscriptionKind.getPrimaryKey().getEntityId());
            
            subscriptionTypes = SubscriptionTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return subscriptionTypes;
    }
    
    public List<SubscriptionType> getSubscriptionTypesBySubscriptionKind(SubscriptionKind subscriptionKind) {
        return getSubscriptionTypesBySubscriptionKind(subscriptionKind, EntityPermission.READ_ONLY);
    }
    
    public List<SubscriptionType> getSubscriptionTypesBySubscriptionKindForUpdate(SubscriptionKind subscriptionKind) {
        return getSubscriptionTypesBySubscriptionKind(subscriptionKind, EntityPermission.READ_WRITE);
    }
    
    private SubscriptionType getDefaultSubscriptionType(SubscriptionKind subscriptionKind, EntityPermission entityPermission) {
        SubscriptionType subscriptionType = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM subscriptiontypes, subscriptiontypedetails " +
                        "WHERE subscrtyp_activedetailid = subscrtypdt_subscriptiontypedetailid " +
                        "AND subscrtypdt_subscrk_subscriptionkindid = ? AND subscrtypdt_isdefault = 1";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM subscriptiontypes, subscriptiontypedetails " +
                        "WHERE subscrtyp_activedetailid = subscrtypdt_subscriptiontypedetailid " +
                        "AND subscrtypdt_subscrk_subscriptionkindid = ? AND subscrtypdt_isdefault = 1 " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SubscriptionTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, subscriptionKind.getPrimaryKey().getEntityId());
            
            subscriptionType = SubscriptionTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return subscriptionType;
    }
    
    public SubscriptionType getDefaultSubscriptionType(SubscriptionKind subscriptionKind) {
        return getDefaultSubscriptionType(subscriptionKind, EntityPermission.READ_ONLY);
    }
    
    public SubscriptionType getDefaultSubscriptionTypeForUpdate(SubscriptionKind subscriptionKind) {
        return getDefaultSubscriptionType(subscriptionKind, EntityPermission.READ_WRITE);
    }
    
    public SubscriptionTypeDetailValue getDefaultSubscriptionTypeDetailValueForUpdate(SubscriptionKind subscriptionKind) {
        return getDefaultSubscriptionTypeForUpdate(subscriptionKind).getLastDetailForUpdate().getSubscriptionTypeDetailValue().clone();
    }
    
    private SubscriptionType getSubscriptionTypeByName(SubscriptionKind subscriptionKind, String subscriptionTypeName,
            EntityPermission entityPermission) {
        SubscriptionType subscriptionType = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM subscriptiontypes, subscriptiontypedetails " +
                        "WHERE subscrtyp_activedetailid = subscrtypdt_subscriptiontypedetailid " +
                        "AND subscrtypdt_subscrk_subscriptionkindid = ? AND subscrtypdt_subscriptiontypename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM subscriptiontypes, subscriptiontypedetails " +
                        "WHERE subscrtyp_activedetailid = subscrtypdt_subscriptiontypedetailid " +
                        "AND subscrtypdt_subscrk_subscriptionkindid = ? AND subscrtypdt_subscriptiontypename = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SubscriptionTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, subscriptionKind.getPrimaryKey().getEntityId());
            ps.setString(2, subscriptionTypeName);
            
            subscriptionType = SubscriptionTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return subscriptionType;
    }
    
    public SubscriptionType getSubscriptionTypeByName(SubscriptionKind subscriptionKind, String subscriptionTypeName) {
        return getSubscriptionTypeByName(subscriptionKind, subscriptionTypeName, EntityPermission.READ_ONLY);
    }
    
    public SubscriptionType getSubscriptionTypeByNameForUpdate(SubscriptionKind subscriptionKind, String subscriptionTypeName) {
        return getSubscriptionTypeByName(subscriptionKind, subscriptionTypeName, EntityPermission.READ_WRITE);
    }
    
    public SubscriptionTypeDetailValue getSubscriptionTypeDetailValueForUpdate(SubscriptionType subscriptionType) {
        return subscriptionType == null? null: subscriptionType.getLastDetailForUpdate().getSubscriptionTypeDetailValue().clone();
    }
    
    public SubscriptionTypeDetailValue getSubscriptionTypeDetailValueByNameForUpdate(SubscriptionKind subscriptionKind,
            String subscriptionTypeName) {
        return getSubscriptionTypeDetailValueForUpdate(getSubscriptionTypeByNameForUpdate(subscriptionKind, subscriptionTypeName));
    }
    
    public SubscriptionTypeChoicesBean getSubscriptionTypeChoices(String defaultSubscriptionTypeChoice, Language language,
            boolean allowNullChoice, SubscriptionKind subscriptionKind) {
        List<SubscriptionType> subscriptionTypes = getSubscriptionTypesBySubscriptionKind(subscriptionKind);
        int size = subscriptionTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
        }
        
        for(SubscriptionType subscriptionType: subscriptionTypes) {
            SubscriptionTypeDetail subscriptionTypeDetail = subscriptionType.getLastDetail();
            String label = getBestSubscriptionTypeDescription(subscriptionType, language);
            String value = subscriptionTypeDetail.getSubscriptionTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultSubscriptionTypeChoice == null? false: defaultSubscriptionTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && subscriptionTypeDetail.getIsDefault()))
                defaultValue = value;
        }
        
        return new SubscriptionTypeChoicesBean(labels, values, defaultValue);
    }
    
    public SubscriptionTypeTransfer getSubscriptionTypeTransfer(UserVisit userVisit,
            SubscriptionType subscriptionType) {
        return getSubscriptionTransferCaches(userVisit).getSubscriptionTypeTransferCache().getSubscriptionTypeTransfer(subscriptionType);
    }
    
    public List<SubscriptionTypeTransfer> getSubscriptionTypeTransfersBySubscriptionKind(UserVisit userVisit, SubscriptionKind subscriptionKind) {
        List<SubscriptionType> subscriptionTypes = getSubscriptionTypesBySubscriptionKind(subscriptionKind);
        List<SubscriptionTypeTransfer> subscriptionTypeTransfers = new ArrayList<>(subscriptionTypes.size());
        SubscriptionTypeTransferCache subscriptionTypeTransferCache = getSubscriptionTransferCaches(userVisit).getSubscriptionTypeTransferCache();
        
        subscriptionTypes.stream().forEach((subscriptionType) -> {
            subscriptionTypeTransfers.add(subscriptionTypeTransferCache.getSubscriptionTypeTransfer(subscriptionType));
        });
        
        return subscriptionTypeTransfers;
    }
    
    private void updateSubscriptionTypeFromValue(SubscriptionTypeDetailValue subscriptionTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(subscriptionTypeDetailValue.hasBeenModified()) {
            SubscriptionType subscriptionType = SubscriptionTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     subscriptionTypeDetailValue.getSubscriptionTypePK());
            SubscriptionTypeDetail subscriptionTypeDetail = subscriptionType.getActiveDetailForUpdate();
            
            subscriptionTypeDetail.setThruTime(session.START_TIME_LONG);
            subscriptionTypeDetail.store();
            
            SubscriptionTypePK subscriptionTypePK = subscriptionTypeDetail.getSubscriptionTypePK(); // Not updated
            SubscriptionKind subscriptionKind = subscriptionTypeDetail.getSubscriptionKind();
            SubscriptionKindPK subscriptionKindPK = subscriptionKind.getPrimaryKey(); // Not updated
            String subscriptionTypeName = subscriptionTypeDetailValue.getSubscriptionTypeName();
            SequencePK subscriptionSequencePK = subscriptionTypeDetailValue.getSubscriptionSequencePK();
            Boolean isDefault = subscriptionTypeDetailValue.getIsDefault();
            Integer sortOrder = subscriptionTypeDetailValue.getSortOrder();
            
            if(checkDefault) {
                SubscriptionType defaultSubscriptionType = getDefaultSubscriptionType(subscriptionKind);
                boolean defaultFound = defaultSubscriptionType != null && !defaultSubscriptionType.equals(subscriptionType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    SubscriptionTypeDetailValue defaultSubscriptionTypeDetailValue = getDefaultSubscriptionTypeDetailValueForUpdate(subscriptionKind);
                    
                    defaultSubscriptionTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updateSubscriptionTypeFromValue(defaultSubscriptionTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            subscriptionTypeDetail = SubscriptionTypeDetailFactory.getInstance().create(subscriptionTypePK,
                    subscriptionKindPK, subscriptionTypeName, subscriptionSequencePK, isDefault, sortOrder, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            subscriptionType.setActiveDetail(subscriptionTypeDetail);
            subscriptionType.setLastDetail(subscriptionTypeDetail);
            
            sendEventUsingNames(subscriptionTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void updateSubscriptionTypeFromValue(SubscriptionTypeDetailValue customerTypeDetailValue, BasePK updatedBy) {
        updateSubscriptionTypeFromValue(customerTypeDetailValue, true, updatedBy);
    }
    
    public void deleteSubscriptionType(SubscriptionType subscriptionType, BasePK deletedBy) {
        deleteSubscriptionTypeDescriptionsBySubscriptionType(subscriptionType, deletedBy);
        deleteSubscriptionTypeChainsBySubscriptionType(subscriptionType, deletedBy);
        
        SubscriptionTypeDetail subscriptionTypeDetail = subscriptionType.getLastDetailForUpdate();
        
        if(subscriptionTypeDetail.getSubscriptionKind().getLastDetail().getSubscriptionKindName().equals(SubscriptionConstants.SubscriptionKind_CLUB)) {
            ClubControl clubControl = (ClubControl)Session.getModelController(ClubControl.class);
            
            clubControl.deleteClubBySubscriptionType(subscriptionType, deletedBy);
        }
        
        subscriptionTypeDetail.setThruTime(session.START_TIME_LONG);
        subscriptionType.setActiveDetail(null);
        subscriptionType.store();
        
        // Check for default, and pick one if necessary
        SubscriptionKind subscriptionKind = subscriptionTypeDetail.getSubscriptionKind();
        SubscriptionType defaultSubscriptionType = getDefaultSubscriptionType(subscriptionKind);
        if(defaultSubscriptionType == null) {
            List<SubscriptionType> subscriptionTypes = getSubscriptionTypesBySubscriptionKind(subscriptionKind);
            
            if(!subscriptionTypes.isEmpty()) {
                Iterator<SubscriptionType> iter = subscriptionTypes.iterator();
                if(iter.hasNext()) {
                    defaultSubscriptionType = iter.next();
                }
                SubscriptionTypeDetailValue subscriptionTypeDetailValue = defaultSubscriptionType.getLastDetailForUpdate().getSubscriptionTypeDetailValue().clone();
                
                subscriptionTypeDetailValue.setIsDefault(Boolean.TRUE);
                updateSubscriptionTypeFromValue(subscriptionTypeDetailValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(subscriptionType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Subscription Type Descriptions
    // --------------------------------------------------------------------------------
    
    public SubscriptionTypeDescription createSubscriptionTypeDescription(SubscriptionType subscriptionType, Language language,
            String description, BasePK createdBy) {
        SubscriptionTypeDescription subscriptionTypeDescription = SubscriptionTypeDescriptionFactory.getInstance().create(session,
                subscriptionType, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(subscriptionType.getPrimaryKey(), EventTypes.MODIFY.name(),
                subscriptionTypeDescription.getPrimaryKey(), null, createdBy);
        
        return subscriptionTypeDescription;
    }
    
    private SubscriptionTypeDescription getSubscriptionTypeDescription(SubscriptionType subscriptionType, Language language,
            EntityPermission entityPermission) {
        SubscriptionTypeDescription subscriptionTypeDescription = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM subscriptiontypedescriptions " +
                        "WHERE subscrtypd_subscrtyp_subscriptiontypeid = ? AND subscrtypd_lang_languageid = ? " +
                        "AND subscrtypd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM subscriptiontypedescriptions " +
                        "WHERE subscrtypd_subscrtyp_subscriptiontypeid = ? AND subscrtypd_lang_languageid = ? " +
                        "AND subscrtypd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SubscriptionTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, subscriptionType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            subscriptionTypeDescription = SubscriptionTypeDescriptionFactory.getInstance().getEntityFromQuery(session,
                    entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return subscriptionTypeDescription;
    }
    
    public SubscriptionTypeDescription getSubscriptionTypeDescription(SubscriptionType subscriptionType, Language language) {
        return getSubscriptionTypeDescription(subscriptionType, language, EntityPermission.READ_ONLY);
    }
    
    public SubscriptionTypeDescription getSubscriptionTypeDescriptionForUpdate(SubscriptionType subscriptionType,
            Language language) {
        return getSubscriptionTypeDescription(subscriptionType, language, EntityPermission.READ_WRITE);
    }
    
    public SubscriptionTypeDescriptionValue getSubscriptionTypeDescriptionValue(SubscriptionTypeDescription subscriptionTypeDescription) {
        return subscriptionTypeDescription == null? null: subscriptionTypeDescription.getSubscriptionTypeDescriptionValue().clone();
    }
    
    public SubscriptionTypeDescriptionValue getSubscriptionTypeDescriptionValueForUpdate(SubscriptionType subscriptionType,
            Language language) {
        return getSubscriptionTypeDescriptionValue(getSubscriptionTypeDescriptionForUpdate(subscriptionType, language));
    }
    
    private List<SubscriptionTypeDescription> getSubscriptionTypeDescriptionsBySubscriptionType(SubscriptionType subscriptionType,
            EntityPermission entityPermission) {
        List<SubscriptionTypeDescription> subscriptionTypeDescriptions = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM subscriptiontypedescriptions, languages " +
                        "WHERE subscrtypd_subscrtyp_subscriptiontypeid = ? AND subscrtypd_thrutime = ? " +
                        "AND subscrtypd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM subscriptiontypedescriptions " +
                        "WHERE subscrtypd_subscrtyp_subscriptiontypeid = ? AND subscrtypd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SubscriptionTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, subscriptionType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            subscriptionTypeDescriptions = SubscriptionTypeDescriptionFactory.getInstance().getEntitiesFromQuery(session,
                    entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return subscriptionTypeDescriptions;
    }
    
    public List<SubscriptionTypeDescription> getSubscriptionTypeDescriptionsBySubscriptionType(SubscriptionType subscriptionType) {
        return getSubscriptionTypeDescriptionsBySubscriptionType(subscriptionType, EntityPermission.READ_ONLY);
    }
    
    public List<SubscriptionTypeDescription> getSubscriptionTypeDescriptionsBySubscriptionTypeForUpdate(SubscriptionType subscriptionType) {
        return getSubscriptionTypeDescriptionsBySubscriptionType(subscriptionType, EntityPermission.READ_WRITE);
    }
    
    public String getBestSubscriptionTypeDescription(SubscriptionType subscriptionType, Language language) {
        String description;
        SubscriptionTypeDescription subscriptionTypeDescription = getSubscriptionTypeDescription(subscriptionType, language);
        
        if(subscriptionTypeDescription == null && !language.getIsDefault()) {
            subscriptionTypeDescription = getSubscriptionTypeDescription(subscriptionType, getPartyControl().getDefaultLanguage());
        }
        
        if(subscriptionTypeDescription == null) {
            description = subscriptionType.getLastDetail().getSubscriptionTypeName();
        } else {
            description = subscriptionTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public SubscriptionTypeDescriptionTransfer getSubscriptionTypeDescriptionTransfer(UserVisit userVisit,
            SubscriptionTypeDescription subscriptionTypeDescription) {
        return getSubscriptionTransferCaches(userVisit).getSubscriptionTypeDescriptionTransferCache().getSubscriptionTypeDescriptionTransfer(subscriptionTypeDescription);
    }
    
    public List<SubscriptionTypeDescriptionTransfer> getSubscriptionTypeDescriptionTransfersBySubscriptionType(UserVisit userVisit,
            SubscriptionType subscriptionType) {
        List<SubscriptionTypeDescription> subscriptionTypeDescriptions = getSubscriptionTypeDescriptionsBySubscriptionType(subscriptionType);
        List<SubscriptionTypeDescriptionTransfer> subscriptionTypeDescriptionTransfers = null;
        
        if(subscriptionTypeDescriptions != null) {
            subscriptionTypeDescriptionTransfers = new ArrayList<>(subscriptionTypeDescriptions.size());
            
            for(SubscriptionTypeDescription subscriptionTypeDescription: subscriptionTypeDescriptions) {
                subscriptionTypeDescriptionTransfers.add(getSubscriptionTransferCaches(userVisit).getSubscriptionTypeDescriptionTransferCache().getSubscriptionTypeDescriptionTransfer(subscriptionTypeDescription));
            }
        }
        
        return subscriptionTypeDescriptionTransfers;
    }
    
    public void updateSubscriptionTypeDescriptionFromValue(SubscriptionTypeDescriptionValue subscriptionTypeDescriptionValue,
            BasePK updatedBy) {
        if(subscriptionTypeDescriptionValue.hasBeenModified()) {
            SubscriptionTypeDescription subscriptionTypeDescription = SubscriptionTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     subscriptionTypeDescriptionValue.getPrimaryKey());
            
            subscriptionTypeDescription.setThruTime(session.START_TIME_LONG);
            subscriptionTypeDescription.store();
            
            SubscriptionType subscriptionType = subscriptionTypeDescription.getSubscriptionType();
            Language language = subscriptionTypeDescription.getLanguage();
            String description = subscriptionTypeDescriptionValue.getDescription();
            
            subscriptionTypeDescription = SubscriptionTypeDescriptionFactory.getInstance().create(subscriptionType,
                    language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(subscriptionType.getPrimaryKey(), EventTypes.MODIFY.name(), subscriptionTypeDescription.getPrimaryKey(),
                    null, updatedBy);
        }
    }
    
    public void deleteSubscriptionTypeDescription(SubscriptionTypeDescription subscriptionTypeDescription, BasePK deletedBy) {
        subscriptionTypeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(subscriptionTypeDescription.getSubscriptionTypePK(), EventTypes.MODIFY.name(),
                subscriptionTypeDescription.getPrimaryKey(), null, deletedBy);
    }
    
    public void deleteSubscriptionTypeDescriptionsBySubscriptionType(SubscriptionType subscriptionType, BasePK deletedBy) {
        List<SubscriptionTypeDescription> subscriptionTypeDescriptions = getSubscriptionTypeDescriptionsBySubscriptionTypeForUpdate(subscriptionType);
        
        subscriptionTypeDescriptions.stream().forEach((subscriptionTypeDescription) -> {
            deleteSubscriptionTypeDescription(subscriptionTypeDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Subscription Type Chains
    // --------------------------------------------------------------------------------
    
    public SubscriptionTypeChain createSubscriptionTypeChain(SubscriptionType subscriptionType, Chain chain, Long remainingTime,
            BasePK createdBy) {
        SubscriptionTypeChain subscriptionTypeChain = SubscriptionTypeChainFactory.getInstance().create(subscriptionType,
                chain, remainingTime, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(subscriptionType.getPrimaryKey(), EventTypes.MODIFY.name(), subscriptionTypeChain.getPrimaryKey(),
                null, createdBy);
        
        return subscriptionTypeChain;
    }
    
    private SubscriptionTypeChain getSubscriptionTypeChain(SubscriptionType subscriptionType, Chain chain,
            EntityPermission entityPermission) {
        SubscriptionTypeChain subscriptionTypeChain = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM subscriptiontypechains " +
                        "WHERE subscrtypchn_subscrtyp_subscriptiontypeid = ? AND subscrtypchn_chn_chainid = ? " +
                        "AND subscrtypchn_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM subscriptiontypechains " +
                        "WHERE subscrtypchn_subscrtyp_subscriptiontypeid = ? AND subscrtypchn_chn_chainid = ? " +
                        "AND subscrtypchn_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SubscriptionTypeChainFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, subscriptionType.getPrimaryKey().getEntityId());
            ps.setLong(2, chain.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            subscriptionTypeChain = SubscriptionTypeChainFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return subscriptionTypeChain;
    }
    
    public SubscriptionTypeChain getSubscriptionTypeChain(SubscriptionType subscriptionType, Chain chain) {
        return getSubscriptionTypeChain(subscriptionType, chain, EntityPermission.READ_ONLY);
    }
    
    public SubscriptionTypeChain getSubscriptionTypeChainForUpdate( SubscriptionType subscriptionType, Chain chain) {
        return getSubscriptionTypeChain(subscriptionType, chain, EntityPermission.READ_WRITE);
    }
    
    public SubscriptionTypeChainValue getSubscriptionTypeChainValue(SubscriptionTypeChain subscriptionTypeChain) {
        return subscriptionTypeChain == null? null: subscriptionTypeChain.getSubscriptionTypeChainValue().clone();
    }
    
    public SubscriptionTypeChainValue getSubscriptionTypeChainValueForUpdate(SubscriptionType subscriptionType, Chain chain) {
        return getSubscriptionTypeChainValue(getSubscriptionTypeChainForUpdate(subscriptionType, chain));
    }
    
    private List<SubscriptionTypeChain> getSubscriptionTypeChainsBySubscriptionType(SubscriptionType subscriptionType,
            EntityPermission entityPermission) {
        List<SubscriptionTypeChain> subscriptionTypeChains = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM subscriptiontypechains, chains, chaindetails " +
                        "WHERE subscrtypchn_subscrtyp_subscriptiontypeid = ? AND subscrtypchn_thrutime = ? " +
                        "AND subscrtypchn_chn_chainid = chn_chainid AND chn_lastdetailid = chndt_chaindetailid " +
                        "ORDER BY chndt_sortorder, chndt_chainname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM subscriptiontypechains " +
                        "WHERE subscrtypchn_subscrtyp_subscriptiontypeid = ? AND subscrtypchn_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SubscriptionTypeChainFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, subscriptionType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            subscriptionTypeChains = SubscriptionTypeChainFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return subscriptionTypeChains;
    }
    
    public List<SubscriptionTypeChain> getSubscriptionTypeChainsBySubscriptionType(SubscriptionType subscriptionType) {
        return getSubscriptionTypeChainsBySubscriptionType(subscriptionType, EntityPermission.READ_ONLY);
    }
    
    public List<SubscriptionTypeChain> getSubscriptionTypeChainsBySubscriptionTypeForUpdate(SubscriptionType subscriptionType) {
        return getSubscriptionTypeChainsBySubscriptionType(subscriptionType, EntityPermission.READ_WRITE);
    }
    
    private List<SubscriptionTypeChain> getSubscriptionTypeChainsBySubscriptionTypeAndChainType(SubscriptionType subscriptionType, ChainType chainType,
            EntityPermission entityPermission) {
        List<SubscriptionTypeChain> subscriptionTypeChains = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM subscriptiontypechains, chains, chaindetails " +
                        "WHERE subscrtypchn_subscrtyp_subscriptiontypeid = ? AND subscrtypchn_thrutime = ? " +
                        "AND subscrtypchn_chn_chainid = chn_chainid AND chn_lastdetailid = chndt_chaindetailid AND chndt_chntyp_chaintypeid = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM subscriptiontypechains " +
                        "WHERE subscrtypchn_subscrtyp_subscriptiontypeid = ? AND subscrtypchn_thrutime = ? " +
                        "AND subscrtypchn_chn_chainid = chn_chainid AND chn_lastdetailid = chndt_chaindetailid AND chndt_chntyp_chaintypeid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SubscriptionTypeChainFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, subscriptionType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            ps.setLong(3, chainType.getPrimaryKey().getEntityId());
            
            subscriptionTypeChains = SubscriptionTypeChainFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return subscriptionTypeChains;
    }
    
    public List<SubscriptionTypeChain> getSubscriptionTypeChainsBySubscriptionTypeAndChainType(SubscriptionType subscriptionType, ChainType chainType) {
        return getSubscriptionTypeChainsBySubscriptionTypeAndChainType(subscriptionType, chainType, EntityPermission.READ_ONLY);
    }
    
    public List<SubscriptionTypeChain> getSubscriptionTypeChainsForUpdateBySubscriptionTypeAndChainType( SubscriptionType subscriptionType, ChainType chainType) {
        return getSubscriptionTypeChainsBySubscriptionTypeAndChainType(subscriptionType, chainType, EntityPermission.READ_WRITE);
    }
    
    private List<SubscriptionTypeChainTransfer> getSubscriptionTypeChainTransfers(UserVisit userVisit, List<SubscriptionTypeChain> subscriptionTypeChains) {
        List<SubscriptionTypeChainTransfer> subscriptionTypeChainTransfers = new ArrayList<>(subscriptionTypeChains.size());
        SubscriptionTypeChainTransferCache subscriptionTypeChainTransferCache = getSubscriptionTransferCaches(userVisit).getSubscriptionTypeChainTransferCache();
        
        subscriptionTypeChains.stream().forEach((subscriptionTypeChain) -> {
            subscriptionTypeChainTransfers.add(subscriptionTypeChainTransferCache.getSubscriptionTypeChainTransfer(subscriptionTypeChain));
        });
        
        return subscriptionTypeChainTransfers;
    }
    
    public List<SubscriptionTypeChainTransfer> getSubscriptionTypeChainTransfersBySubscriptionType(UserVisit userVisit,
            SubscriptionType subscriptionType) {
        return getSubscriptionTypeChainTransfers(userVisit, getSubscriptionTypeChainsBySubscriptionType(subscriptionType));
    }
    
    public void updateSubscriptionTypeChainFromValue(SubscriptionTypeChainValue subscriptionTypeChainValue, BasePK updatedBy) {
        if(subscriptionTypeChainValue.hasBeenModified()) {
            SubscriptionTypeChain subscriptionTypeChain = SubscriptionTypeChainFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     subscriptionTypeChainValue.getPrimaryKey());
            
            subscriptionTypeChain.setThruTime(session.START_TIME_LONG);
            subscriptionTypeChain.store();
            
            SubscriptionTypePK subscriptionTypePK = subscriptionTypeChain.getSubscriptionTypePK(); // Not updated
            ChainPK chainPK = subscriptionTypeChain.getChainPK(); // Not updated
            Long remainingTime = subscriptionTypeChainValue.getRemainingTime();
            
            subscriptionTypeChain = SubscriptionTypeChainFactory.getInstance().create(subscriptionTypePK, chainPK,
                    remainingTime, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(subscriptionTypeChain.getSubscriptionTypePK(), EventTypes.MODIFY.name(),
                    subscriptionTypeChain.getPrimaryKey(), null, updatedBy);
        }
    }
    
    public void deleteSubscriptionTypeChain(SubscriptionTypeChain subscriptionTypeChain, BasePK deletedBy) {
        // TODO: delete any running chains
        
        subscriptionTypeChain.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(subscriptionTypeChain.getSubscriptionTypePK(), EventTypes.MODIFY.name(),
                subscriptionTypeChain.getPrimaryKey(), null, deletedBy);
    }
    
    public void deleteSubscriptionTypeChainsBySubscriptionType(SubscriptionType subscriptionType, BasePK deletedBy) {
        List<SubscriptionTypeChain> subscriptionTypeChains = getSubscriptionTypeChainsBySubscriptionTypeForUpdate(subscriptionType);
        
        subscriptionTypeChains.stream().forEach((subscriptionTypeChain) -> {
            deleteSubscriptionTypeChain(subscriptionTypeChain, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Subscriptions
    // --------------------------------------------------------------------------------
    
    public Subscription createSubscription(SubscriptionType subscriptionType, Party party, Long startTime, Long endTime,
            BasePK createdBy) {
        Sequence sequence = subscriptionType.getLastDetail().getSubscriptionSequence();
        SequenceControl sequenceControl = (SequenceControl)Session.getModelController(SequenceControl.class);
        
        if(sequence == null) {
            SequenceType sequenceType = sequenceControl.getSequenceTypeByName(SequenceConstants.SequenceType_SUBSCRIPTION);
            sequence = sequenceControl.getDefaultSequence(sequenceType);
        }
        
        Subscription subscription = SubscriptionFactory.getInstance().create();
        SubscriptionDetail subscriptionDetail = SubscriptionDetailFactory.getInstance().create(subscription,
                sequenceControl.getNextSequenceValue(sequence), subscriptionType, party, startTime, endTime,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        subscription = SubscriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                subscription.getPrimaryKey());
        subscription.setActiveDetail(subscriptionDetail);
        subscription.setLastDetail(subscriptionDetail);
        subscription.store();
        
        sendEventUsingNames(subscription.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return subscription;
    }
    
    private Subscription getSubscription(SubscriptionType subscriptionType, Party party, EntityPermission entityPermission) {
        Subscription subscription = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM subscriptions, subscriptiondetails " +
                        "WHERE subscr_activedetailid = subscrdt_subscriptiondetailid AND subscrdt_subscrtyp_subscriptiontypeid = ? " +
                        "AND subscrdt_par_partyid = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM subscriptions, subscriptiondetails " +
                        "WHERE subscr_activedetailid = subscrdt_subscriptiondetailid AND subscrdt_subscrtyp_subscriptiontypeid = ? " +
                        "AND subscrdt_par_partyid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SubscriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, subscriptionType.getPrimaryKey().getEntityId());
            ps.setLong(2, party.getPrimaryKey().getEntityId());
            
            subscription = SubscriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return subscription;
    }
    
    public Subscription getSubscription(SubscriptionType subscriptionType, Party party) {
        return getSubscription(subscriptionType, party, EntityPermission.READ_ONLY);
    }
    
    public Subscription getSubscriptionForUpdate(SubscriptionType subscriptionType, Party party) {
        return getSubscription(subscriptionType, party, EntityPermission.READ_WRITE);
    }
    
    private Subscription getSubscriptionByName(String subscriptionName, EntityPermission entityPermission) {
        Subscription subscription = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM subscriptions, subscriptiondetails " +
                        "WHERE subscr_activedetailid = subscrdt_subscriptiondetailid AND subscrdt_subscriptionname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM subscriptions, subscriptiondetails " +
                        "WHERE subscr_activedetailid = subscrdt_subscriptiondetailid AND subscrdt_subscriptionname = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SubscriptionFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, subscriptionName);
            
            subscription = SubscriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return subscription;
    }
    
    public Subscription getSubscriptionByName(String subscriptionName) {
        return getSubscriptionByName(subscriptionName, EntityPermission.READ_ONLY);
    }
    
    public Subscription getSubscriptionByNameForUpdate(String subscriptionName) {
        return getSubscriptionByName(subscriptionName, EntityPermission.READ_WRITE);
    }
    
    public SubscriptionDetailValue getSubscriptionDetailValueByNameForUpdate(String subscriptionName) {
        return getSubscriptionByNameForUpdate(subscriptionName).getLastDetailForUpdate().getSubscriptionDetailValue().clone();
    }
    
    private List<Subscription> getSubscriptionsBySubscriptionType(SubscriptionType subscriptionType,
            EntityPermission entityPermission) {
        List<Subscription> subscriptions = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM subscriptions, subscriptiondetails " +
                        "WHERE subscr_activedetailid = subscrdt_subscriptiondetailid AND subscrdt_subscrtyp_subscriptiontypeid = ? " +
                        "ORDER BY subscrdt_subscriptionname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM subscriptions, subscriptiondetails " +
                        "WHERE subscr_activedetailid = subscrdt_subscriptiondetailid AND subscrdt_subscrtyp_subscriptiontypeid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SubscriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, subscriptionType.getPrimaryKey().getEntityId());
            
            subscriptions = SubscriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return subscriptions;
    }
    
    public List<Subscription> getSubscriptionsBySubscriptionType(SubscriptionType subscriptionType) {
        return getSubscriptionsBySubscriptionType(subscriptionType, EntityPermission.READ_ONLY);
    }
    
    public List<Subscription> getSubscriptionsBySubscriptionTypeForUpdate(SubscriptionType subscriptionType) {
        return getSubscriptionsBySubscriptionType(subscriptionType, EntityPermission.READ_WRITE);
    }
    
    private List<Subscription> getSubscriptionsByParty(Party party, EntityPermission entityPermission) {
        List<Subscription> subscriptions = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM subscriptions, subscriptiondetails, subscriptiontypes, subscriptiontypedetails " +
                        "WHERE subscr_activedetailid = subscrdt_subscriptiondetailid AND subscrdt_par_partyid = ? " +
                        "AND subscrdt_subscrtyp_subscriptiontypeid = subscrtyp_subscriptiontypeid " +
                        "AND subscrtyp_lastdetailid = subscrtypdt_subscriptiontypedetailid " +
                        "ORDER BY subscrtypdt_sortorder, subscrtypdt_subscriptiontypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM subscriptions, subscriptiondetails " +
                        "WHERE subscr_activedetailid = subscrdt_subscriptiondetailid AND subscrdt_par_partyid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SubscriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, party.getPrimaryKey().getEntityId());
            
            subscriptions = SubscriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return subscriptions;
    }
    
    public List<Subscription> getSubscriptionsByParty(Party party) {
        return getSubscriptionsByParty(party, EntityPermission.READ_ONLY);
    }
    
    public List<Subscription> getSubscriptionsByPartyForUpdate(Party party) {
        return getSubscriptionsByParty(party, EntityPermission.READ_WRITE);
    }
    
    public SubscriptionTransfer getSubscriptionTransfer(UserVisit userVisit, Subscription subscription) {
        return getSubscriptionTransferCaches(userVisit).getSubscriptionTransferCache().getSubscriptionTransfer(subscription);
    }
    
    private List<SubscriptionTransfer> getSubscriptionTransfers(UserVisit userVisit, List<Subscription> subscriptions) {
        List<SubscriptionTransfer> subscriptionTransfers = new ArrayList<>(subscriptions.size());
        SubscriptionTransferCache subscriptionTransferCache = getSubscriptionTransferCaches(userVisit).getSubscriptionTransferCache();
        
        subscriptions.stream().forEach((subscription) -> {
            subscriptionTransfers.add(subscriptionTransferCache.getSubscriptionTransfer(subscription));
        });
        
        return subscriptionTransfers;
    }
    
    public List<SubscriptionTransfer> getSubscriptionTransfersBySubscriptionType(UserVisit userVisit,
            SubscriptionType subscriptionType) {
        return getSubscriptionTransfers(userVisit, getSubscriptionsBySubscriptionType(subscriptionType));
    }
    
    public List<SubscriptionTransfer> getSubscriptionTransfersByParty(UserVisit userVisit, Party party) {
        return getSubscriptionTransfers(userVisit, getSubscriptionsByParty(party));
    }
    
    public void updateSubscriptionFromValue(SubscriptionDetailValue subscriptionDetailValue, BasePK updatedBy) {
        if(subscriptionDetailValue.hasBeenModified()) {
            Subscription subscription = SubscriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     subscriptionDetailValue.getSubscriptionPK());
            SubscriptionDetail subscriptionDetail = subscription.getActiveDetailForUpdate();
            
            subscriptionDetail.setThruTime(session.START_TIME_LONG);
            subscriptionDetail.store();
            
            SubscriptionPK subscriptionPK = subscriptionDetail.getSubscriptionPK(); // Not updated
            String subscriptionName = subscriptionDetailValue.getSubscriptionName();
            SubscriptionTypePK subscriptionTypePK = subscriptionDetail.getSubscriptionTypePK(); // Not updated
            PartyPK partyPK = subscriptionDetail.getPartyPK(); // Not updated
            Long startTime = subscriptionDetailValue.getStartTime();
            Long endTime = subscriptionDetailValue.getEndTime();
            
            subscriptionDetail = SubscriptionDetailFactory.getInstance().create(subscriptionPK, subscriptionName,
                    subscriptionTypePK, partyPK, startTime, endTime, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            subscription.setActiveDetail(subscriptionDetail);
            subscription.setLastDetail(subscriptionDetail);
            
            sendEventUsingNames(subscriptionPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void deleteSubscription(Subscription subscription, BasePK deletedBy) {
        // TODO: Chain cleanup
        
        SubscriptionDetail subscriptionDetail = subscription.getLastDetailForUpdate();
        subscriptionDetail.setThruTime(session.START_TIME_LONG);
        subscription.setActiveDetail(null);
        subscription.store();
        
        sendEventUsingNames(subscription.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    public void deleteSubscriptionsBySubscriptionType(SubscriptionType subscriptionType, BasePK deletedBy) {
        List<Subscription> subscriptions = getSubscriptionsBySubscriptionType(subscriptionType);
        
        subscriptions.stream().forEach((subscription) -> {
            deleteSubscription(subscription, deletedBy);
        });
    }
    
}
