// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

package com.echothree.model.control.returnpolicy.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.returnpolicy.common.choice.ReturnKindChoicesBean;
import com.echothree.model.control.returnpolicy.common.choice.ReturnPolicyChoicesBean;
import com.echothree.model.control.returnpolicy.common.choice.ReturnReasonChoicesBean;
import com.echothree.model.control.returnpolicy.common.choice.ReturnTypeChoicesBean;
import com.echothree.model.control.returnpolicy.common.transfer.PartyReturnPolicyTransfer;
import com.echothree.model.control.returnpolicy.common.transfer.ReturnKindDescriptionTransfer;
import com.echothree.model.control.returnpolicy.common.transfer.ReturnKindTransfer;
import com.echothree.model.control.returnpolicy.common.transfer.ReturnPolicyReasonTransfer;
import com.echothree.model.control.returnpolicy.common.transfer.ReturnPolicyTransfer;
import com.echothree.model.control.returnpolicy.common.transfer.ReturnPolicyTranslationTransfer;
import com.echothree.model.control.returnpolicy.common.transfer.ReturnReasonDescriptionTransfer;
import com.echothree.model.control.returnpolicy.common.transfer.ReturnReasonTransfer;
import com.echothree.model.control.returnpolicy.common.transfer.ReturnReasonTypeTransfer;
import com.echothree.model.control.returnpolicy.common.transfer.ReturnTypeDescriptionTransfer;
import com.echothree.model.control.returnpolicy.common.transfer.ReturnTypeShippingMethodTransfer;
import com.echothree.model.control.returnpolicy.common.transfer.ReturnTypeTransfer;
import com.echothree.model.control.returnpolicy.server.transfer.PartyReturnPolicyTransferCache;
import com.echothree.model.control.returnpolicy.server.transfer.ReturnPolicyReasonTransferCache;
import com.echothree.model.control.returnpolicy.server.transfer.ReturnPolicyTransferCache;
import com.echothree.model.control.returnpolicy.server.transfer.ReturnPolicyTransferCaches;
import com.echothree.model.control.returnpolicy.server.transfer.ReturnPolicyTranslationTransferCache;
import com.echothree.model.control.returnpolicy.server.transfer.ReturnReasonTransferCache;
import com.echothree.model.control.returnpolicy.server.transfer.ReturnReasonTypeTransferCache;
import com.echothree.model.control.returnpolicy.server.transfer.ReturnTypeShippingMethodTransferCache;
import com.echothree.model.control.returnpolicy.server.transfer.ReturnTypeTransferCache;
import com.echothree.model.data.core.common.pk.MimeTypePK;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.party.common.pk.LanguagePK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.returnpolicy.common.pk.ReturnKindPK;
import com.echothree.model.data.returnpolicy.common.pk.ReturnPolicyPK;
import com.echothree.model.data.returnpolicy.common.pk.ReturnReasonPK;
import com.echothree.model.data.returnpolicy.common.pk.ReturnTypePK;
import com.echothree.model.data.returnpolicy.server.entity.PartyReturnPolicy;
import com.echothree.model.data.returnpolicy.server.entity.ReturnKind;
import com.echothree.model.data.returnpolicy.server.entity.ReturnKindDescription;
import com.echothree.model.data.returnpolicy.server.entity.ReturnKindDetail;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicyDetail;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicyReason;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicyTranslation;
import com.echothree.model.data.returnpolicy.server.entity.ReturnReason;
import com.echothree.model.data.returnpolicy.server.entity.ReturnReasonDescription;
import com.echothree.model.data.returnpolicy.server.entity.ReturnReasonDetail;
import com.echothree.model.data.returnpolicy.server.entity.ReturnReasonType;
import com.echothree.model.data.returnpolicy.server.entity.ReturnType;
import com.echothree.model.data.returnpolicy.server.entity.ReturnTypeDescription;
import com.echothree.model.data.returnpolicy.server.entity.ReturnTypeDetail;
import com.echothree.model.data.returnpolicy.server.entity.ReturnTypeShippingMethod;
import com.echothree.model.data.returnpolicy.server.factory.PartyReturnPolicyFactory;
import com.echothree.model.data.returnpolicy.server.factory.ReturnKindDescriptionFactory;
import com.echothree.model.data.returnpolicy.server.factory.ReturnKindDetailFactory;
import com.echothree.model.data.returnpolicy.server.factory.ReturnKindFactory;
import com.echothree.model.data.returnpolicy.server.factory.ReturnPolicyDetailFactory;
import com.echothree.model.data.returnpolicy.server.factory.ReturnPolicyFactory;
import com.echothree.model.data.returnpolicy.server.factory.ReturnPolicyReasonFactory;
import com.echothree.model.data.returnpolicy.server.factory.ReturnPolicyTranslationFactory;
import com.echothree.model.data.returnpolicy.server.factory.ReturnReasonDescriptionFactory;
import com.echothree.model.data.returnpolicy.server.factory.ReturnReasonDetailFactory;
import com.echothree.model.data.returnpolicy.server.factory.ReturnReasonFactory;
import com.echothree.model.data.returnpolicy.server.factory.ReturnReasonTypeFactory;
import com.echothree.model.data.returnpolicy.server.factory.ReturnTypeDescriptionFactory;
import com.echothree.model.data.returnpolicy.server.factory.ReturnTypeDetailFactory;
import com.echothree.model.data.returnpolicy.server.factory.ReturnTypeFactory;
import com.echothree.model.data.returnpolicy.server.factory.ReturnTypeShippingMethodFactory;
import com.echothree.model.data.returnpolicy.server.value.PartyReturnPolicyValue;
import com.echothree.model.data.returnpolicy.server.value.ReturnKindDescriptionValue;
import com.echothree.model.data.returnpolicy.server.value.ReturnKindDetailValue;
import com.echothree.model.data.returnpolicy.server.value.ReturnPolicyDetailValue;
import com.echothree.model.data.returnpolicy.server.value.ReturnPolicyReasonValue;
import com.echothree.model.data.returnpolicy.server.value.ReturnPolicyTranslationValue;
import com.echothree.model.data.returnpolicy.server.value.ReturnReasonDescriptionValue;
import com.echothree.model.data.returnpolicy.server.value.ReturnReasonDetailValue;
import com.echothree.model.data.returnpolicy.server.value.ReturnReasonTypeValue;
import com.echothree.model.data.returnpolicy.server.value.ReturnTypeDescriptionValue;
import com.echothree.model.data.returnpolicy.server.value.ReturnTypeDetailValue;
import com.echothree.model.data.returnpolicy.server.value.ReturnTypeShippingMethodValue;
import com.echothree.model.data.sequence.common.pk.SequencePK;
import com.echothree.model.data.sequence.common.pk.SequenceTypePK;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.shipping.common.pk.ShippingMethodPK;
import com.echothree.model.data.shipping.server.entity.ShippingMethod;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ReturnPolicyControl
        extends BaseModelControl {
    
    /** Creates a new instance of ReturnPolicyControl */
    public ReturnPolicyControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Return Policy Transfer Caches
    // --------------------------------------------------------------------------------
    
    private ReturnPolicyTransferCaches returnPolicyTransferCaches;
    
    public ReturnPolicyTransferCaches getReturnPolicyTransferCaches(UserVisit userVisit) {
        if(returnPolicyTransferCaches == null) {
            returnPolicyTransferCaches = new ReturnPolicyTransferCaches(userVisit, this);
        }
        
        return returnPolicyTransferCaches;
    }
    
    // --------------------------------------------------------------------------------
    //   Party Return Policies
    // --------------------------------------------------------------------------------

    public PartyReturnPolicy createPartyReturnPolicy(Party party, ReturnPolicy returnPolicy, BasePK createdBy) {
        PartyReturnPolicy partyReturnPolicy = PartyReturnPolicyFactory.getInstance().create(party, returnPolicy, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(party.getPrimaryKey(), EventTypes.MODIFY, partyReturnPolicy.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return partyReturnPolicy;
    }

    public long countPartyReturnPoliciesByParty(Party party) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM partyreturnpolicies " +
                "WHERE prtnplcy_par_partyid = ? AND prtnplcy_thrutime = ?",
                party, Session.MAX_TIME);
    }

    public long countPartyReturnPoliciesByReturnPolicy(ReturnPolicy returnPolicy) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM partyreturnpolicies " +
                "WHERE prtnplcy_rtnplcy_returnpolicyid = ? AND prtnplcy_thrutime = ?",
                returnPolicy, Session.MAX_TIME);
    }

    public long countPartyReturnPoliciesByPartyAndReturnPolicy(Party party, ReturnPolicy returnPolicy) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM partyreturnpolicies " +
                "WHERE prtnplcy_par_partyid = ? AND prtnplcy_rtnplcy_returnpolicyid = ? AND prtnplcy_thrutime = ?",
                party, returnPolicy, Session.MAX_TIME);
    }

    private static final Map<EntityPermission, String> getPartyReturnPolicyQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partyreturnpolicies " +
                "WHERE prtnplcy_par_partyid = ? AND prtnplcy_rtnplcy_returnpolicyid = ? AND prtnplcy_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partyreturnpolicies " +
                "WHERE prtnplcy_par_partyid = ? AND prtnplcy_rtnplcy_returnpolicyid = ? AND prtnplcy_thrutime = ? " +
                "FOR UPDATE");
        getPartyReturnPolicyQueries = Collections.unmodifiableMap(queryMap);
    }

    private PartyReturnPolicy getPartyReturnPolicy(Party party, ReturnPolicy returnPolicy, EntityPermission entityPermission) {
        return PartyReturnPolicyFactory.getInstance().getEntityFromQuery(entityPermission, getPartyReturnPolicyQueries,
                party, returnPolicy, Session.MAX_TIME_LONG);
    }

    public PartyReturnPolicy getPartyReturnPolicy(Party party, ReturnPolicy returnPolicy) {
        return getPartyReturnPolicy(party, returnPolicy, EntityPermission.READ_ONLY);
    }

    public PartyReturnPolicy getPartyReturnPolicyForUpdate(Party party, ReturnPolicy returnPolicy) {
        return getPartyReturnPolicy(party, returnPolicy, EntityPermission.READ_WRITE);
    }

    public PartyReturnPolicyValue getPartyReturnPolicyValue(PartyReturnPolicy partyReturnPolicy) {
        return partyReturnPolicy == null? null: partyReturnPolicy.getPartyReturnPolicyValue().clone();
    }

    private static final Map<EntityPermission, String> getPartyReturnPoliciesByReturnPolicyQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partyreturnpolicies, returnpolicies, returnpolicydetails " +
                "WHERE prtnplcy_rtnplcy_returnpolicyid = ? AND prtnplcy_thrutime = ? " +
                "AND prtnplcy_rtnplcy_returnpolicyid = rtnplcy_returnpolicyid AND rtnplcy_lastdetailid = rtnplcydt_returnpolicydetailid " +
                "ORDER BY rtnplcydt_sortorder, rtnplcydt_returnpolicyname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partyreturnpolicies " +
                "WHERE prtnplcy_rtnplcy_returnpolicyid = ? AND prtnplcy_thrutime = ? " +
                "FOR UPDATE");
        getPartyReturnPoliciesByReturnPolicyQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PartyReturnPolicy> getPartyReturnPoliciesByReturnPolicy(ReturnPolicy returnPolicy, EntityPermission entityPermission) {
        return PartyReturnPolicyFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyReturnPoliciesByReturnPolicyQueries,
                returnPolicy, Session.MAX_TIME_LONG);
    }

    public List<PartyReturnPolicy> getPartyReturnPoliciesByReturnPolicy(ReturnPolicy returnPolicy) {
        return getPartyReturnPoliciesByReturnPolicy(returnPolicy, EntityPermission.READ_ONLY);
    }

    public List<PartyReturnPolicy> getPartyReturnPoliciesByReturnPolicyForUpdate(ReturnPolicy returnPolicy) {
        return getPartyReturnPoliciesByReturnPolicy(returnPolicy, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getPartyReturnPoliciesByPartyQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partyreturnpolicies, parties, partydetails " +
                "WHERE prtnplcy_par_partyid = ? AND prtnplcy_thrutime = ? " +
                "AND prtnplcy_par_partyid = par_partyid AND par_lastdetailid = pardt_partydetailid " +
                "ORDER BY pardt_partyname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partyreturnpolicies " +
                "WHERE prtnplcy_par_partyid = ? AND prtnplcy_thrutime = ? " +
                "FOR UPDATE");
        getPartyReturnPoliciesByPartyQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PartyReturnPolicy> getPartyReturnPoliciesByParty(Party party, EntityPermission entityPermission) {
        return PartyReturnPolicyFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyReturnPoliciesByPartyQueries,
                party, Session.MAX_TIME_LONG);
    }

    public List<PartyReturnPolicy> getPartyReturnPoliciesByParty(Party party) {
        return getPartyReturnPoliciesByParty(party, EntityPermission.READ_ONLY);
    }

    public List<PartyReturnPolicy> getPartyReturnPoliciesByPartyForUpdate(Party party) {
        return getPartyReturnPoliciesByParty(party, EntityPermission.READ_WRITE);
    }

    public PartyReturnPolicyTransfer getPartyReturnPolicyTransfer(UserVisit userVisit, PartyReturnPolicy partyReturnPolicy) {
        return getReturnPolicyTransferCaches(userVisit).getPartyReturnPolicyTransferCache().getPartyReturnPolicyTransfer(partyReturnPolicy);
    }

    public List<PartyReturnPolicyTransfer> getPartyReturnPolicyTransfers(UserVisit userVisit, Collection<PartyReturnPolicy> returnPolicies) {
        List<PartyReturnPolicyTransfer> returnPolicyTransfers = new ArrayList<>(returnPolicies.size());
        PartyReturnPolicyTransferCache returnPolicyTransferCache = getReturnPolicyTransferCaches(userVisit).getPartyReturnPolicyTransferCache();

        returnPolicies.forEach((returnPolicy) ->
                returnPolicyTransfers.add(returnPolicyTransferCache.getPartyReturnPolicyTransfer(returnPolicy))
        );

        return returnPolicyTransfers;
    }

    public List<PartyReturnPolicyTransfer> getPartyReturnPolicyTransfersByParty(UserVisit userVisit, Party party) {
        return getPartyReturnPolicyTransfers(userVisit, getPartyReturnPoliciesByParty(party));
    }

    public List<PartyReturnPolicyTransfer> getPartyReturnPolicyTransfersByReturnPolicy(UserVisit userVisit, ReturnPolicy returnPolicy) {
        return getPartyReturnPolicyTransfers(userVisit, getPartyReturnPoliciesByReturnPolicy(returnPolicy));
    }

    public void deletePartyReturnPolicy(PartyReturnPolicy partyReturnPolicy, BasePK deletedBy) {
        var coreControl = Session.getModelController(CoreControl.class);

        partyReturnPolicy.setThruTime(session.START_TIME_LONG);

        // Performed manually, since sendEvent doesn't call it for relatedEntityInstances.
        coreControl.deleteEntityInstanceDependencies(coreControl.getEntityInstanceByBasePK(partyReturnPolicy.getPrimaryKey()), deletedBy);

        sendEvent(partyReturnPolicy.getPartyPK(), EventTypes.MODIFY, partyReturnPolicy.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deletePartyReturnPoliciesByParty(List<PartyReturnPolicy> partyReturnPolicies, BasePK deletedBy) {
        partyReturnPolicies.forEach((partyReturnPolicy) -> 
                deletePartyReturnPolicy(partyReturnPolicy, deletedBy)
        );
    }

    public void deletePartyReturnPoliciesByParty(Party party, BasePK deletedBy) {
        deletePartyReturnPoliciesByParty(getPartyReturnPoliciesByPartyForUpdate(party), deletedBy);
    }

    public void deletePartyReturnPoliciesByReturnPolicy(ReturnPolicy returnPolicy, BasePK deletedBy) {
        deletePartyReturnPoliciesByParty(getPartyReturnPoliciesByReturnPolicyForUpdate(returnPolicy), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Return Kinds
    // --------------------------------------------------------------------------------
    
    public ReturnKind createReturnKind(String returnKindName, SequenceType returnSequenceType, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        ReturnKind defaultReturnKind = getDefaultReturnKind();
        boolean defaultFound = defaultReturnKind != null;
        
        if(defaultFound && isDefault) {
            ReturnKindDetailValue defaultReturnKindDetailValue = getDefaultReturnKindDetailValueForUpdate();
            
            defaultReturnKindDetailValue.setIsDefault(Boolean.FALSE);
            updateReturnKindFromValue(defaultReturnKindDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        ReturnKind returnKind = ReturnKindFactory.getInstance().create();
        ReturnKindDetail returnKindDetail = ReturnKindDetailFactory.getInstance().create(returnKind, returnKindName,
                returnSequenceType, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        returnKind = ReturnKindFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                returnKind.getPrimaryKey());
        returnKind.setActiveDetail(returnKindDetail);
        returnKind.setLastDetail(returnKindDetail);
        returnKind.store();
        
        sendEvent(returnKind.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return returnKind;
    }

    public long countReturnKinds() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM returnkinds, returnkinddetails " +
                "WHERE rtnk_activedetailid = rtnkdt_returnkinddetailid");
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ReturnKind */
    public ReturnKind getReturnKindByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ReturnKindPK(entityInstance.getEntityUniqueId());

        return ReturnKindFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public ReturnKind getReturnKindByEntityInstance(EntityInstance entityInstance) {
        return getReturnKindByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ReturnKind getReturnKindByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getReturnKindByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getReturnKindByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM returnkinds, returnkinddetails "
                + "WHERE rtnk_activedetailid = rtnkdt_returnkinddetailid AND rtnkdt_returnkindname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM returnkinds, returnkinddetails "
                + "WHERE rtnk_activedetailid = rtnkdt_returnkinddetailid AND rtnkdt_returnkindname = ? "
                + "FOR UPDATE");
        getReturnKindByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    public ReturnKind getReturnKindByName(String returnKindName, EntityPermission entityPermission) {
        return ReturnKindFactory.getInstance().getEntityFromQuery(entityPermission, getReturnKindByNameQueries,
                returnKindName);
    }
    
    public ReturnKind getReturnKindByName(String returnKindName) {
        return getReturnKindByName(returnKindName, EntityPermission.READ_ONLY);
    }
    
    public ReturnKind getReturnKindByNameForUpdate(String returnKindName) {
        return getReturnKindByName(returnKindName, EntityPermission.READ_WRITE);
    }
    
    public ReturnKindDetailValue getReturnKindDetailValueForUpdate(ReturnKind returnKind) {
        return returnKind == null? null: returnKind.getLastDetailForUpdate().getReturnKindDetailValue().clone();
    }
    
    public ReturnKindDetailValue getReturnKindDetailValueByNameForUpdate(String returnKindName) {
        return getReturnKindDetailValueForUpdate(getReturnKindByNameForUpdate(returnKindName));
    }
    
    private static final Map<EntityPermission, String> getDefaultReturnKindQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM returnkinds, returnkinddetails "
                + "WHERE rtnk_activedetailid = rtnkdt_returnkinddetailid AND rtnkdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM returnkinds, returnkinddetails "
                + "WHERE rtnk_activedetailid = rtnkdt_returnkinddetailid AND rtnkdt_isdefault = 1 "
                + "FOR UPDATE");
        getDefaultReturnKindQueries = Collections.unmodifiableMap(queryMap);
    }

    public ReturnKind getDefaultReturnKind(EntityPermission entityPermission) {
        return ReturnKindFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultReturnKindQueries);
    }
    
    public ReturnKind getDefaultReturnKind() {
        return getDefaultReturnKind(EntityPermission.READ_ONLY);
    }
    
    public ReturnKind getDefaultReturnKindForUpdate() {
        return getDefaultReturnKind(EntityPermission.READ_WRITE);
    }
    
    public ReturnKindDetailValue getDefaultReturnKindDetailValueForUpdate() {
        return getDefaultReturnKind(EntityPermission.READ_WRITE).getLastDetailForUpdate().getReturnKindDetailValue();
    }
    
    private static final Map<EntityPermission, String> getReturnKindsQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM returnkinds, returnkinddetails "
                + "WHERE rtnk_activedetailid = rtnkdt_returnkinddetailid "
                + "ORDER BY rtnkdt_sortorder, rtnkdt_returnkindname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM returnkinds, returnkinddetails "
                + "WHERE rtnk_activedetailid = rtnkdt_returnkinddetailid "
                + "FOR UPDATE");
        getReturnKindsQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ReturnKind> getReturnKinds(EntityPermission entityPermission) {
        return ReturnKindFactory.getInstance().getEntitiesFromQuery(entityPermission, getReturnKindsQueries);
    }
    
    public List<ReturnKind> getReturnKinds() {
        return getReturnKinds(EntityPermission.READ_ONLY);
    }
    
    public List<ReturnKind> getReturnKindsForUpdate() {
        return getReturnKinds(EntityPermission.READ_WRITE);
    }
    
    public ReturnKindChoicesBean getReturnKindChoices(String defaultReturnKindChoice, Language language, boolean allowNullChoice) {
        List<ReturnKind> returnKinds = getReturnKinds();
        var size = returnKinds.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultReturnKindChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var returnKind : returnKinds) {
            ReturnKindDetail returnKindDetail = returnKind.getLastDetail();
            
            var label = getBestReturnKindDescription(returnKind, language);
            var value = returnKindDetail.getReturnKindName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultReturnKindChoice != null && defaultReturnKindChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && returnKindDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new ReturnKindChoicesBean(labels, values, defaultValue);
    }
    
    public ReturnKindTransfer getReturnKindTransfer(UserVisit userVisit, ReturnKind returnKind) {
        return getReturnPolicyTransferCaches(userVisit).getReturnKindTransferCache().getReturnKindTransfer(returnKind);
    }

    public List<ReturnKindTransfer> getReturnKindTransfers(UserVisit userVisit, Collection<ReturnKind> returnKinds) {
        var returnKindTransfers = new ArrayList<ReturnKindTransfer>(returnKinds.size());
        var returnKindTransferCache = getReturnPolicyTransferCaches(userVisit).getReturnKindTransferCache();

        returnKinds.forEach((returnKind) ->
                returnKindTransfers.add(returnKindTransferCache.getReturnKindTransfer(returnKind))
        );

        return returnKindTransfers;
    }

    public List<ReturnKindTransfer> getReturnKindTransfers(UserVisit userVisit) {
        return getReturnKindTransfers(userVisit, getReturnKinds());
    }

    private void updateReturnKindFromValue(ReturnKindDetailValue returnKindDetailValue, boolean checkDefault, BasePK updatedBy) {
        ReturnKind returnKind = ReturnKindFactory.getInstance().getEntityFromPK(session,
                EntityPermission.READ_WRITE, returnKindDetailValue.getReturnKindPK());
        ReturnKindDetail returnKindDetail = returnKind.getActiveDetailForUpdate();
        
        returnKindDetail.setThruTime(session.START_TIME_LONG);
        returnKindDetail.store();
        
        ReturnKindPK returnKindPK = returnKindDetail.getReturnKindPK();
        String returnKindName = returnKindDetailValue.getReturnKindName();
        SequenceTypePK returnSequenceTypePK = returnKindDetailValue.getReturnSequenceTypePK();
        Boolean isDefault = returnKindDetailValue.getIsDefault();
        Integer sortOrder = returnKindDetailValue.getSortOrder();
        
        if(checkDefault) {
            ReturnKind defaultReturnKind = getDefaultReturnKind();
            boolean defaultFound = defaultReturnKind != null && !defaultReturnKind.equals(returnKind);
            
            if(isDefault && defaultFound) {
                // If I'm the default, and a default already existed...
                ReturnKindDetailValue defaultReturnKindDetailValue = getDefaultReturnKindDetailValueForUpdate();
                
                defaultReturnKindDetailValue.setIsDefault(Boolean.FALSE);
                updateReturnKindFromValue(defaultReturnKindDetailValue, false, updatedBy);
            } else if(!isDefault && !defaultFound) {
                // If I'm not the default, and no other default exists...
                isDefault = Boolean.TRUE;
            }
        }
        
        returnKindDetail = ReturnKindDetailFactory.getInstance().create(returnKindPK, returnKindName, returnSequenceTypePK,
                isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        returnKind.setActiveDetail(returnKindDetail);
        returnKind.setLastDetail(returnKindDetail);
        returnKind.store();
        
        sendEvent(returnKindPK, EventTypes.MODIFY, null, null, updatedBy);
    }
    
    public void updateReturnKindFromValue(ReturnKindDetailValue returnKindDetailValue, BasePK updatedBy) {
        updateReturnKindFromValue(returnKindDetailValue, true, updatedBy);
    }
    
    public void deleteReturnKind(ReturnKind returnKind, BasePK deletedBy) {
        deleteReturnKindDescriptionsByReturnKind(returnKind, deletedBy);
        
        ReturnKindDetail returnKindDetail = returnKind.getLastDetailForUpdate();
        returnKindDetail.setThruTime(session.START_TIME_LONG);
        returnKind.setActiveDetail(null);
        returnKind.store();
        
        // Check for default, and pick one if necessary
        ReturnKind defaultReturnKind = getDefaultReturnKind();
        if(defaultReturnKind == null) {
            List<ReturnKind> returnKinds = getReturnKindsForUpdate();
            
            if(!returnKinds.isEmpty()) {
                Iterator<ReturnKind> iter = returnKinds.iterator();
                if(iter.hasNext()) {
                    defaultReturnKind = iter.next();
                }
                ReturnKindDetailValue returnKindDetailValue = Objects.requireNonNull(defaultReturnKind).getLastDetailForUpdate().getReturnKindDetailValue().clone();
                
                returnKindDetailValue.setIsDefault(Boolean.TRUE);
                updateReturnKindFromValue(returnKindDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(returnKind.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Return Kind Descriptions
    // --------------------------------------------------------------------------------
    
    public ReturnKindDescription createReturnKindDescription(ReturnKind returnKind, Language language, String description,
            BasePK createdBy) {
        ReturnKindDescription returnKindDescription = ReturnKindDescriptionFactory.getInstance().create(returnKind,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(returnKind.getPrimaryKey(), EventTypes.MODIFY, returnKindDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return returnKindDescription;
    }
    
    private static final Map<EntityPermission, String> getReturnKindDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM returnkinddescriptions "
                + "WHERE rtnkd_rtnk_returnkindid = ? AND rtnkd_lang_languageid = ? AND rtnkd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM returnkinddescriptions "
                + "WHERE rtnkd_rtnk_returnkindid = ? AND rtnkd_lang_languageid = ? AND rtnkd_thrutime = ? "
                + "FOR UPDATE");
        getReturnKindDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private ReturnKindDescription getReturnKindDescription(ReturnKind returnKind, Language language, EntityPermission entityPermission) {
        return ReturnKindDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getReturnKindDescriptionQueries,
                returnKind, language, Session.MAX_TIME);
    }
    
    public ReturnKindDescription getReturnKindDescription(ReturnKind returnKind, Language language) {
        return getReturnKindDescription(returnKind, language, EntityPermission.READ_ONLY);
    }
    
    public ReturnKindDescription getReturnKindDescriptionForUpdate(ReturnKind returnKind, Language language) {
        return getReturnKindDescription(returnKind, language, EntityPermission.READ_WRITE);
    }
    
    public ReturnKindDescriptionValue getReturnKindDescriptionValue(ReturnKindDescription returnKindDescription) {
        return returnKindDescription == null? null: returnKindDescription.getReturnKindDescriptionValue().clone();
    }
    
    public ReturnKindDescriptionValue getReturnKindDescriptionValueForUpdate(ReturnKind returnKind, Language language) {
        return getReturnKindDescriptionValue(getReturnKindDescriptionForUpdate(returnKind, language));
    }
    
    private static final Map<EntityPermission, String> getReturnKindDescriptionsByReturnKindQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM returnkinddescriptions, languages "
                + "WHERE rtnkd_rtnk_returnkindid = ? AND rtnkd_thrutime = ? AND rtnkd_lang_languageid = lang_languageid "
                + "ORDER BY lang_sortorder, lang_languageisoname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM returnkinddescriptions "
                + "WHERE rtnkd_rtnk_returnkindid = ? AND rtnkd_thrutime = ? "
                + "FOR UPDATE");
        getReturnKindDescriptionsByReturnKindQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ReturnKindDescription> getReturnKindDescriptionsByReturnKind(ReturnKind returnKind, EntityPermission entityPermission) {
        return ReturnKindDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getReturnKindDescriptionsByReturnKindQueries,
                returnKind, Session.MAX_TIME);
    }
    
    public List<ReturnKindDescription> getReturnKindDescriptionsByReturnKind(ReturnKind returnKind) {
        return getReturnKindDescriptionsByReturnKind(returnKind, EntityPermission.READ_ONLY);
    }
    
    public List<ReturnKindDescription> getReturnKindDescriptionsByReturnKindForUpdate(ReturnKind returnKind) {
        return getReturnKindDescriptionsByReturnKind(returnKind, EntityPermission.READ_WRITE);
    }
    
    public String getBestReturnKindDescription(ReturnKind returnKind, Language language) {
        String description;
        ReturnKindDescription returnKindDescription = getReturnKindDescription(returnKind, language);
        
        if(returnKindDescription == null && !language.getIsDefault()) {
            returnKindDescription = getReturnKindDescription(returnKind, getPartyControl().getDefaultLanguage());
        }
        
        if(returnKindDescription == null) {
            description = returnKind.getLastDetail().getReturnKindName();
        } else {
            description = returnKindDescription.getDescription();
        }
        
        return description;
    }
    
    public ReturnKindDescriptionTransfer getReturnKindDescriptionTransfer(UserVisit userVisit, ReturnKindDescription returnKindDescription) {
        return getReturnPolicyTransferCaches(userVisit).getReturnKindDescriptionTransferCache().getReturnKindDescriptionTransfer(returnKindDescription);
    }
    
    public List<ReturnKindDescriptionTransfer> getReturnKindDescriptionTransfersByReturnKind(UserVisit userVisit, ReturnKind returnKind) {
        List<ReturnKindDescription> returnKindDescriptions = getReturnKindDescriptionsByReturnKind(returnKind);
        List<ReturnKindDescriptionTransfer> returnKindDescriptionTransfers = new ArrayList<>(returnKindDescriptions.size());
        
        returnKindDescriptions.forEach((returnKindDescription) -> {
            returnKindDescriptionTransfers.add(getReturnPolicyTransferCaches(userVisit).getReturnKindDescriptionTransferCache().getReturnKindDescriptionTransfer(returnKindDescription));
        });
        
        return returnKindDescriptionTransfers;
    }
    
    public void updateReturnKindDescriptionFromValue(ReturnKindDescriptionValue returnKindDescriptionValue, BasePK updatedBy) {
        if(returnKindDescriptionValue.hasBeenModified()) {
            ReturnKindDescription returnKindDescription = ReturnKindDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     returnKindDescriptionValue.getPrimaryKey());
            
            returnKindDescription.setThruTime(session.START_TIME_LONG);
            returnKindDescription.store();
            
            ReturnKind returnKind = returnKindDescription.getReturnKind();
            Language language = returnKindDescription.getLanguage();
            String description = returnKindDescriptionValue.getDescription();
            
            returnKindDescription = ReturnKindDescriptionFactory.getInstance().create(returnKind, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(returnKind.getPrimaryKey(), EventTypes.MODIFY, returnKindDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteReturnKindDescription(ReturnKindDescription returnKindDescription, BasePK deletedBy) {
        returnKindDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(returnKindDescription.getReturnKindPK(), EventTypes.MODIFY, returnKindDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteReturnKindDescriptionsByReturnKind(ReturnKind returnKind, BasePK deletedBy) {
        List<ReturnKindDescription> returnKindDescriptions = getReturnKindDescriptionsByReturnKindForUpdate(returnKind);
        
        returnKindDescriptions.forEach((returnKindDescription) -> 
                deleteReturnKindDescription(returnKindDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Return Policies
    // --------------------------------------------------------------------------------
    
    public ReturnPolicy createReturnPolicy(ReturnKind returnKind, String returnPolicyName, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        ReturnPolicy defaultReturnPolicy = getDefaultReturnPolicy(returnKind);
        boolean defaultFound = defaultReturnPolicy != null;
        
        if(defaultFound && isDefault) {
            ReturnPolicyDetailValue defaultReturnPolicyDetailValue = getDefaultReturnPolicyDetailValueForUpdate(returnKind);
            
            defaultReturnPolicyDetailValue.setIsDefault(Boolean.FALSE);
            updateReturnPolicyFromValue(defaultReturnPolicyDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        ReturnPolicy returnPolicy = ReturnPolicyFactory.getInstance().create();
        ReturnPolicyDetail returnPolicyDetail = ReturnPolicyDetailFactory.getInstance().create(session,
                returnPolicy, returnKind, returnPolicyName, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        // Convert to R/W
        returnPolicy = ReturnPolicyFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                returnPolicy.getPrimaryKey());
        returnPolicy.setActiveDetail(returnPolicyDetail);
        returnPolicy.setLastDetail(returnPolicyDetail);
        returnPolicy.store();
        
        sendEvent(returnPolicy.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return returnPolicy;
    }

    public long countReturnPoliciesByReturnKind(ReturnKind returnKind) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM returnpolicies, returnpolicydetails " +
                "WHERE rtnplcy_activedetailid = rtnplcydt_returnpolicydetailid AND rtnplcydt_rtnk_returnkindid = ?",
                returnKind);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ReturnPolicy */
    public ReturnPolicy getReturnPolicyByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ReturnPolicyPK(entityInstance.getEntityUniqueId());

        return ReturnPolicyFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public ReturnPolicy getReturnPolicyByEntityInstance(EntityInstance entityInstance) {
        return getReturnPolicyByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ReturnPolicy getReturnPolicyByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getReturnPolicyByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    private List<ReturnPolicy> getReturnPolicies(ReturnKind returnKind, EntityPermission entityPermission) {
        List<ReturnPolicy> returnPolicies;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM returnpolicies, returnpolicydetails " +
                        "WHERE rtnplcy_activedetailid = rtnplcydt_returnpolicydetailid AND rtnplcydt_rtnk_returnkindid = ? " +
                        "ORDER BY rtnplcydt_sortorder, rtnplcydt_returnpolicyname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM returnpolicies, returnpolicydetails " +
                        "WHERE rtnplcy_activedetailid = rtnplcydt_returnpolicydetailid AND rtnplcydt_rtnk_returnkindid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ReturnPolicyFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, returnKind.getPrimaryKey().getEntityId());
            
            returnPolicies = ReturnPolicyFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return returnPolicies;
    }
    
    public List<ReturnPolicy> getReturnPolicies(ReturnKind returnKind) {
        return getReturnPolicies(returnKind, EntityPermission.READ_ONLY);
    }
    
    public List<ReturnPolicy> getReturnPoliciesForUpdate(ReturnKind returnKind) {
        return getReturnPolicies(returnKind, EntityPermission.READ_WRITE);
    }
    
    public ReturnPolicy getDefaultReturnPolicy(ReturnKind returnKind, EntityPermission entityPermission) {
        ReturnPolicy returnPolicy;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM returnpolicies, returnpolicydetails " +
                        "WHERE rtnplcy_activedetailid = rtnplcydt_returnpolicydetailid " +
                        "AND rtnplcydt_rtnk_returnkindid = ? AND rtnplcydt_isdefault = 1";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM returnpolicies, returnpolicydetails " +
                        "WHERE rtnplcy_activedetailid = rtnplcydt_returnpolicydetailid " +
                        "AND rtnplcydt_rtnk_returnkindid = ? AND rtnplcydt_isdefault = 1 " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ReturnPolicyFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, returnKind.getPrimaryKey().getEntityId());
            
            returnPolicy = ReturnPolicyFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return returnPolicy;
    }
    
    public ReturnPolicy getDefaultReturnPolicy(ReturnKind returnKind) {
        return getDefaultReturnPolicy(returnKind, EntityPermission.READ_ONLY);
    }
    
    public ReturnPolicy getDefaultReturnPolicyForUpdate(ReturnKind returnKind) {
        return getDefaultReturnPolicy(returnKind, EntityPermission.READ_WRITE);
    }
    
    public ReturnPolicyDetailValue getDefaultReturnPolicyDetailValueForUpdate(ReturnKind returnKind) {
        return getDefaultReturnPolicyForUpdate(returnKind).getLastDetailForUpdate().getReturnPolicyDetailValue().clone();
    }
    
    public ReturnPolicy getReturnPolicyByName(ReturnKind returnKind, String returnPolicyName, EntityPermission entityPermission) {
        ReturnPolicy returnPolicy;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM returnpolicies, returnpolicydetails " +
                        "WHERE rtnplcy_activedetailid = rtnplcydt_returnpolicydetailid " +
                        "AND rtnplcydt_rtnk_returnkindid = ? AND rtnplcydt_returnpolicyname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM returnpolicies, returnpolicydetails " +
                        "WHERE rtnplcy_activedetailid = rtnplcydt_returnpolicydetailid " +
                        "AND rtnplcydt_rtnk_returnkindid = ? AND rtnplcydt_returnpolicyname = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ReturnPolicyFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, returnKind.getPrimaryKey().getEntityId());
            ps.setString(2, returnPolicyName);
            
            returnPolicy = ReturnPolicyFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return returnPolicy;
    }
    
    public ReturnPolicy getReturnPolicyByName(ReturnKind returnKind, String returnPolicyName) {
        return getReturnPolicyByName(returnKind, returnPolicyName, EntityPermission.READ_ONLY);
    }
    
    public ReturnPolicy getReturnPolicyByNameForUpdate(ReturnKind returnKind, String returnPolicyName) {
        return getReturnPolicyByName(returnKind, returnPolicyName, EntityPermission.READ_WRITE);
    }
    
    public ReturnPolicyDetailValue getReturnPolicyDetailValueForUpdate(ReturnPolicy returnPolicy) {
        return returnPolicy == null? null: returnPolicy.getLastDetailForUpdate().getReturnPolicyDetailValue().clone();
    }
    
    public ReturnPolicyDetailValue getReturnPolicyDetailValueByNameForUpdate(ReturnKind returnKind, String returnPolicyName) {
        return getReturnPolicyDetailValueForUpdate(getReturnPolicyByNameForUpdate(returnKind, returnPolicyName));
    }
    
    public ReturnPolicyChoicesBean getReturnPolicyChoices(String defaultReturnPolicyChoice, Language language,
            boolean allowNullChoice, ReturnKind returnKind) {
        List<ReturnPolicy> returnPolicies = getReturnPolicies(returnKind);
        var size = returnPolicies.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultReturnPolicyChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var returnPolicy : returnPolicies) {
            ReturnPolicyDetail returnPolicyDetail = returnPolicy.getLastDetail();
            String returnPolicyName = returnPolicyDetail.getReturnPolicyName();
            ReturnPolicyTranslation returnPolicyTranslation = getBestReturnPolicyTranslation(returnPolicy, language);

            var label = returnPolicyTranslation == null ? returnPolicyName : returnPolicyTranslation.getDescription();
            var value = returnPolicyName;
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultReturnPolicyChoice != null && defaultReturnPolicyChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && returnPolicyDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new ReturnPolicyChoicesBean(labels, values, defaultValue);
    }
    
    public ReturnPolicyTransfer getReturnPolicyTransfer(UserVisit userVisit, ReturnPolicy returnPolicy) {
        return getReturnPolicyTransferCaches(userVisit).getReturnPolicyTransferCache().getReturnPolicyTransfer(returnPolicy);
    }

    public List<ReturnPolicyTransfer> getReturnPolicyTransfers(UserVisit userVisit, Collection<ReturnPolicy> returnPolicies ) {
        List<ReturnPolicyTransfer> returnPolicyTransfers = new ArrayList<>(returnPolicies.size());
        ReturnPolicyTransferCache returnPolicyTransferCache = getReturnPolicyTransferCaches(userVisit).getReturnPolicyTransferCache();

        returnPolicies.forEach((returnPolicy) ->
                returnPolicyTransfers.add(returnPolicyTransferCache.getReturnPolicyTransfer(returnPolicy))
        );

        return returnPolicyTransfers;
    }

    public List<ReturnPolicyTransfer> getReturnPolicyTransfersByReturnKind(UserVisit userVisit, ReturnKind returnKind) {
        return getReturnPolicyTransfers(userVisit, getReturnPolicies(returnKind));
    }

    private void updateReturnPolicyFromValue(ReturnPolicyDetailValue returnPolicyDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(returnPolicyDetailValue.hasBeenModified()) {
            ReturnPolicy returnPolicy = ReturnPolicyFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     returnPolicyDetailValue.getReturnPolicyPK());
            ReturnPolicyDetail returnPolicyDetail = returnPolicy.getActiveDetailForUpdate();
            
            returnPolicyDetail.setThruTime(session.START_TIME_LONG);
            returnPolicyDetail.store();
            
            ReturnPolicyPK returnPolicyPK = returnPolicyDetail.getReturnPolicyPK();
            ReturnKind returnKind = returnPolicyDetail.getReturnKind();
            ReturnKindPK returnKindPK = returnKind.getPrimaryKey();
            String returnPolicyName = returnPolicyDetailValue.getReturnPolicyName();
            Boolean isDefault = returnPolicyDetailValue.getIsDefault();
            Integer sortOrder = returnPolicyDetailValue.getSortOrder();
            
            if(checkDefault) {
                ReturnPolicy defaultReturnPolicy = getDefaultReturnPolicy(returnKind);
                boolean defaultFound = defaultReturnPolicy != null && !defaultReturnPolicy.equals(returnPolicy);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    ReturnPolicyDetailValue defaultReturnPolicyDetailValue = getDefaultReturnPolicyDetailValueForUpdate(returnKind);
                    
                    defaultReturnPolicyDetailValue.setIsDefault(Boolean.FALSE);
                    updateReturnPolicyFromValue(defaultReturnPolicyDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            returnPolicyDetail = ReturnPolicyDetailFactory.getInstance().create(returnPolicyPK,
                    returnKindPK, returnPolicyName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            returnPolicy.setActiveDetail(returnPolicyDetail);
            returnPolicy.setLastDetail(returnPolicyDetail);
            
            sendEvent(returnPolicyPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateReturnPolicyFromValue(ReturnPolicyDetailValue returnPolicyDetailValue, BasePK updatedBy) {
        updateReturnPolicyFromValue(returnPolicyDetailValue, true, updatedBy);
    }
    
    public void deleteReturnPolicy(ReturnPolicy returnPolicy, BasePK deletedBy) {
        deletePartyReturnPoliciesByReturnPolicy(returnPolicy, deletedBy);
        deleteReturnPolicyReasonsByReturnPolicy(returnPolicy, deletedBy);
        deleteReturnPolicyTranslationsByReturnPolicy(returnPolicy, deletedBy);
        
        ReturnPolicyDetail returnPolicyDetail = returnPolicy.getLastDetailForUpdate();
        returnPolicyDetail.setThruTime(session.START_TIME_LONG);
        returnPolicy.setActiveDetail(null);
        returnPolicy.store();
        
        // Check for default, and pick one if necessary
        ReturnKind returnKind = returnPolicyDetail.getReturnKind();
        ReturnPolicy defaultReturnPolicy = getDefaultReturnPolicy(returnKind);
        if(defaultReturnPolicy == null) {
            List<ReturnPolicy> returnPolicies = getReturnPoliciesForUpdate(returnKind);
            
            if(!returnPolicies.isEmpty()) {
                Iterator<ReturnPolicy> iter = returnPolicies.iterator();
                if(iter.hasNext()) {
                    defaultReturnPolicy = iter.next();
                }
                ReturnPolicyDetailValue returnPolicyDetailValue = Objects.requireNonNull(defaultReturnPolicy).getLastDetailForUpdate().getReturnPolicyDetailValue().clone();
                
                returnPolicyDetailValue.setIsDefault(Boolean.TRUE);
                updateReturnPolicyFromValue(returnPolicyDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(returnPolicy.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteReturnPoliciesByReturnKind(ReturnKind returnKind, BasePK deletedBy) {
        List<ReturnPolicy> returnPolicies = getReturnPoliciesForUpdate(returnKind);
        
        returnPolicies.forEach((returnPolicy) -> 
                deleteReturnPolicy(returnPolicy, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Return Policy Translations
    // --------------------------------------------------------------------------------

    public ReturnPolicyTranslation createReturnPolicyTranslation(ReturnPolicy returnPolicy, Language language,
            String description, MimeType overviewMimeType, String overview, BasePK createdBy) {
        ReturnPolicyTranslation returnPolicyTranslation = ReturnPolicyTranslationFactory.getInstance().create(returnPolicy,
                language, description, overviewMimeType, overview, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(returnPolicy.getPrimaryKey(), EventTypes.MODIFY, returnPolicyTranslation.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return returnPolicyTranslation;
    }

    private static final Map<EntityPermission, String> getReturnPolicyTranslationQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM returnpolicytranslations " +
                "WHERE rtnplcytr_rtnplcy_returnpolicyid = ? AND rtnplcytr_lang_languageid = ? AND rtnplcytr_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM returnpolicytranslations " +
                "WHERE rtnplcytr_rtnplcy_returnpolicyid = ? AND rtnplcytr_lang_languageid = ? AND rtnplcytr_thrutime = ? " +
                "FOR UPDATE");
        getReturnPolicyTranslationQueries = Collections.unmodifiableMap(queryMap);
    }

    private ReturnPolicyTranslation getReturnPolicyTranslation(ReturnPolicy returnPolicy, Language language, EntityPermission entityPermission) {
        return ReturnPolicyTranslationFactory.getInstance().getEntityFromQuery(entityPermission, getReturnPolicyTranslationQueries, returnPolicy, language,
                Session.MAX_TIME);
    }

    public ReturnPolicyTranslation getReturnPolicyTranslation(ReturnPolicy returnPolicy, Language language) {
        return getReturnPolicyTranslation(returnPolicy, language, EntityPermission.READ_ONLY);
    }

    public ReturnPolicyTranslation getReturnPolicyTranslationForUpdate(ReturnPolicy returnPolicy, Language language) {
        return getReturnPolicyTranslation(returnPolicy, language, EntityPermission.READ_WRITE);
    }

    public ReturnPolicyTranslationValue getReturnPolicyTranslationValue(ReturnPolicyTranslation returnPolicyTranslation) {
        return returnPolicyTranslation == null? null: returnPolicyTranslation.getReturnPolicyTranslationValue().clone();
    }

    public ReturnPolicyTranslationValue getReturnPolicyTranslationValueForUpdate(ReturnPolicy returnPolicy, Language language) {
        return getReturnPolicyTranslationValue(getReturnPolicyTranslationForUpdate(returnPolicy, language));
    }

    private static final Map<EntityPermission, String> getReturnPolicyTranslationsByReturnPolicyQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM returnpolicytranslations, languages " +
                "WHERE rtnplcytr_rtnplcy_returnpolicyid = ? AND rtnplcytr_thrutime = ? AND rtnplcytr_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM returnpolicytranslations " +
                "WHERE rtnplcytr_rtnplcy_returnpolicyid = ? AND rtnplcytr_thrutime = ? " +
                "FOR UPDATE");
        getReturnPolicyTranslationsByReturnPolicyQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ReturnPolicyTranslation> getReturnPolicyTranslationsByReturnPolicy(ReturnPolicy returnPolicy, EntityPermission entityPermission) {
        return ReturnPolicyTranslationFactory.getInstance().getEntitiesFromQuery(entityPermission, getReturnPolicyTranslationsByReturnPolicyQueries,
                returnPolicy, Session.MAX_TIME);
    }

    public List<ReturnPolicyTranslation> getReturnPolicyTranslationsByReturnPolicy(ReturnPolicy returnPolicy) {
        return getReturnPolicyTranslationsByReturnPolicy(returnPolicy, EntityPermission.READ_ONLY);
    }

    public List<ReturnPolicyTranslation> getReturnPolicyTranslationsByReturnPolicyForUpdate(ReturnPolicy returnPolicy) {
        return getReturnPolicyTranslationsByReturnPolicy(returnPolicy, EntityPermission.READ_WRITE);
    }

    public ReturnPolicyTranslation getBestReturnPolicyTranslation(ReturnPolicy returnPolicy, Language language) {
        ReturnPolicyTranslation returnPolicyTranslation = getReturnPolicyTranslation(returnPolicy, language);

        if(returnPolicyTranslation == null && !language.getIsDefault()) {
            returnPolicyTranslation = getReturnPolicyTranslation(returnPolicy, getPartyControl().getDefaultLanguage());
        }

        return returnPolicyTranslation;
    }

    public ReturnPolicyTranslationTransfer getReturnPolicyTranslationTransfer(UserVisit userVisit, ReturnPolicyTranslation returnPolicyTranslation) {
        return getReturnPolicyTransferCaches(userVisit).getReturnPolicyTranslationTransferCache().getReturnPolicyTranslationTransfer(returnPolicyTranslation);
    }

    public List<ReturnPolicyTranslationTransfer> getReturnPolicyTranslationTransfers(UserVisit userVisit, ReturnPolicy returnPolicy) {
        List<ReturnPolicyTranslation> returnPolicyTranslations = getReturnPolicyTranslationsByReturnPolicy(returnPolicy);
        List<ReturnPolicyTranslationTransfer> returnPolicyTranslationTransfers = new ArrayList<>(returnPolicyTranslations.size());
        ReturnPolicyTranslationTransferCache returnPolicyTranslationTransferCache = getReturnPolicyTransferCaches(userVisit).getReturnPolicyTranslationTransferCache();

        returnPolicyTranslations.forEach((returnPolicyTranslation) ->
                returnPolicyTranslationTransfers.add(returnPolicyTranslationTransferCache.getReturnPolicyTranslationTransfer(returnPolicyTranslation))
        );

        return returnPolicyTranslationTransfers;
    }

    public void updateReturnPolicyTranslationFromValue(ReturnPolicyTranslationValue returnPolicyTranslationValue, BasePK updatedBy) {
        if(returnPolicyTranslationValue.hasBeenModified()) {
            ReturnPolicyTranslation returnPolicyTranslation = ReturnPolicyTranslationFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    returnPolicyTranslationValue.getPrimaryKey());

            returnPolicyTranslation.setThruTime(session.START_TIME_LONG);
            returnPolicyTranslation.store();

            ReturnPolicyPK returnPolicyPK = returnPolicyTranslation.getReturnPolicyPK();
            LanguagePK languagePK = returnPolicyTranslation.getLanguagePK();
            String description = returnPolicyTranslationValue.getDescription();
            MimeTypePK policyMimeTypePK = returnPolicyTranslationValue.getPolicyMimeTypePK();
            String policy = returnPolicyTranslationValue.getPolicy();

            returnPolicyTranslation = ReturnPolicyTranslationFactory.getInstance().create(returnPolicyPK, languagePK, description,
                    policyMimeTypePK, policy, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(returnPolicyPK, EventTypes.MODIFY, returnPolicyTranslation.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteReturnPolicyTranslation(ReturnPolicyTranslation returnPolicyTranslation, BasePK deletedBy) {
        returnPolicyTranslation.setThruTime(session.START_TIME_LONG);

        sendEvent(returnPolicyTranslation.getReturnPolicyPK(), EventTypes.MODIFY, returnPolicyTranslation.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteReturnPolicyTranslationsByReturnPolicy(ReturnPolicy returnPolicy, BasePK deletedBy) {
        List<ReturnPolicyTranslation> returnPolicyTranslations = getReturnPolicyTranslationsByReturnPolicyForUpdate(returnPolicy);

        returnPolicyTranslations.forEach((returnPolicyTranslation) -> 
                deleteReturnPolicyTranslation(returnPolicyTranslation, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Return Policy Reasons
    // --------------------------------------------------------------------------------
    
    public ReturnPolicyReason createReturnPolicyReason(ReturnPolicy returnPolicy, ReturnReason returnReason, Boolean isDefault,
            Integer sortOrder, BasePK createdBy) {
        ReturnPolicyReason defaultReturnPolicyReason = getDefaultReturnPolicyReason(returnPolicy);
        boolean defaultFound = defaultReturnPolicyReason != null;
        
        if(defaultFound && isDefault) {
            ReturnPolicyReasonValue defaultReturnPolicyReasonValue = getDefaultReturnPolicyReasonValueForUpdate(returnPolicy);
            
            defaultReturnPolicyReasonValue.setIsDefault(Boolean.FALSE);
            updateReturnPolicyReasonFromValue(defaultReturnPolicyReasonValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        ReturnPolicyReason returnPolicyReason = ReturnPolicyReasonFactory.getInstance().create(returnPolicy, returnReason,
                isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(returnPolicy.getPrimaryKey(), EventTypes.MODIFY, returnPolicyReason.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return returnPolicyReason;
    }
    
    private ReturnPolicyReason getReturnPolicyReason(ReturnPolicy returnPolicy, ReturnReason returnReason, EntityPermission entityPermission) {
        ReturnPolicyReason returnPolicyReason;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM returnpolicyreasons " +
                        "WHERE rtnplcyrsn_rtnplcy_returnpolicyid = ? AND rtnplcyrsn_rtnrsn_returnreasonid = ? AND rtnplcyrsn_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM returnpolicyreasons " +
                        "WHERE rtnplcyrsn_rtnplcy_returnpolicyid = ? AND rtnplcyrsn_rtnrsn_returnreasonid = ? AND rtnplcyrsn_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ReturnPolicyReasonFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, returnPolicy.getPrimaryKey().getEntityId());
            ps.setLong(2, returnReason.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            returnPolicyReason = ReturnPolicyReasonFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return returnPolicyReason;
    }
    
    public ReturnPolicyReason getReturnPolicyReason(ReturnPolicy returnPolicy, ReturnReason returnReason) {
        return getReturnPolicyReason(returnPolicy, returnReason, EntityPermission.READ_ONLY);
    }
    
    public ReturnPolicyReason getReturnPolicyReasonForUpdate(ReturnPolicy returnPolicy, ReturnReason returnReason) {
        return getReturnPolicyReason(returnPolicy, returnReason, EntityPermission.READ_WRITE);
    }
    
    public ReturnPolicyReasonValue getReturnPolicyReasonValueForUpdate(ReturnPolicy returnPolicy, ReturnReason returnReason) {
        ReturnPolicyReason returnPolicyReason = getReturnPolicyReasonForUpdate(returnPolicy, returnReason);
        
        return returnPolicyReason == null? null: returnPolicyReason.getReturnPolicyReasonValue().clone();
    }
    
    private ReturnPolicyReason getDefaultReturnPolicyReason(ReturnPolicy returnPolicy, EntityPermission entityPermission) {
        ReturnPolicyReason returnPolicyReason;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM returnpolicyreasons " +
                        "WHERE rtnplcyrsn_rtnplcy_returnpolicyid = ? AND rtnplcyrsn_isdefault = 1 AND rtnplcyrsn_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM returnpolicyreasons " +
                        "WHERE rtnplcyrsn_rtnplcy_returnpolicyid = ? AND rtnplcyrsn_isdefault = 1 AND rtnplcyrsn_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ReturnPolicyReasonFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, returnPolicy.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            returnPolicyReason = ReturnPolicyReasonFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return returnPolicyReason;
    }
    
    public ReturnPolicyReason getDefaultReturnPolicyReason(ReturnPolicy returnPolicy) {
        return getDefaultReturnPolicyReason(returnPolicy, EntityPermission.READ_ONLY);
    }
    
    public ReturnPolicyReason getDefaultReturnPolicyReasonForUpdate(ReturnPolicy returnPolicy) {
        return getDefaultReturnPolicyReason(returnPolicy, EntityPermission.READ_WRITE);
    }
    
    public ReturnPolicyReasonValue getDefaultReturnPolicyReasonValueForUpdate(ReturnPolicy returnPolicy) {
        ReturnPolicyReason returnPolicyReason = getDefaultReturnPolicyReasonForUpdate(returnPolicy);
        
        return returnPolicyReason == null? null: returnPolicyReason.getReturnPolicyReasonValue().clone();
    }
    
    private List<ReturnPolicyReason> getReturnPolicyReasonsByReturnPolicy(ReturnPolicy returnPolicy, EntityPermission entityPermission) {
        List<ReturnPolicyReason> returnPolicyReasons;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM returnpolicyreasons, returnreasons, returnreasondetails " +
                        "WHERE rtnplcyrsn_rtnplcy_returnpolicyid = ? AND rtnplcyrsn_thrutime = ? " +
                        "AND rtnplcyrsn_rtnrsn_returnreasonid = rtnrsn_returnreasonid AND rtnrsn_lastdetailid = rtnrsndt_returnreasondetailid " +
                        "ORDER BY rtnrsndt_sortorder, rtnrsndt_returnreasonname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM returnpolicyreasons " +
                        "WHERE rtnplcyrsn_rtnplcy_returnpolicyid = ? AND rtnplcyrsn_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ReturnPolicyReasonFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, returnPolicy.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            returnPolicyReasons = ReturnPolicyReasonFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return returnPolicyReasons;
    }
    
    public List<ReturnPolicyReason> getReturnPolicyReasonsByReturnPolicy(ReturnPolicy returnPolicy) {
        return getReturnPolicyReasonsByReturnPolicy(returnPolicy, EntityPermission.READ_ONLY);
    }
    
    public List<ReturnPolicyReason> getReturnPolicyReasonsByReturnPolicyForUpdate(ReturnPolicy returnPolicy) {
        return getReturnPolicyReasonsByReturnPolicy(returnPolicy, EntityPermission.READ_WRITE);
    }
    
    private List<ReturnPolicyReason> getReturnPolicyReasonsByReturnReason(ReturnReason returnReason, EntityPermission entityPermission) {
        List<ReturnPolicyReason> returnPolicyReasons;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM returnpolicyreasons, returnpolicies, returnpolicydetails " +
                        "WHERE rtnplcyrsn_rtnrsn_returnreasonid = ? AND rtnplcyrsn_thrutime = ? " +
                        "AND rtnplcyrsn_rtnplcy_returnpolicyid = rtnplcy_returnpolicyid AND rtnplcy_lastdetailid = rtnplcydt_returnpolicydetailid " +
                        "ORDER BY rtnplcydt_sortorder, rtnplcydt_returnpolicyname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM returnpolicyreasons " +
                        "WHERE rtnplcyrsn_rtnrsn_returnreasonid = ? AND rtnplcyrsn_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ReturnPolicyReasonFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, returnReason.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            returnPolicyReasons = ReturnPolicyReasonFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return returnPolicyReasons;
    }
    
    public List<ReturnPolicyReason> getReturnPolicyReasonsByReturnReason(ReturnReason returnReason) {
        return getReturnPolicyReasonsByReturnReason(returnReason, EntityPermission.READ_ONLY);
    }
    
    public List<ReturnPolicyReason> getReturnPolicyReasonsByReturnReasonForUpdate(ReturnReason returnReason) {
        return getReturnPolicyReasonsByReturnReason(returnReason, EntityPermission.READ_WRITE);
    }
    
    public List<ReturnPolicyReasonTransfer> getReturnPolicyReasonTransfers(UserVisit userVisit, Collection<ReturnPolicyReason> returnPolicyReasons) {
        List<ReturnPolicyReasonTransfer> returnPolicyReasonTransfers = new ArrayList<>(returnPolicyReasons.size());
        ReturnPolicyReasonTransferCache returnPolicyReasonTransferCache = getReturnPolicyTransferCaches(userVisit).getReturnPolicyReasonTransferCache();
        
        returnPolicyReasons.forEach((returnPolicyReason) ->
                returnPolicyReasonTransfers.add(returnPolicyReasonTransferCache.getReturnPolicyReasonTransfer(returnPolicyReason))
        );
        
        return returnPolicyReasonTransfers;
    }
    
    public List<ReturnPolicyReasonTransfer> getReturnPolicyReasonTransfersByReturnPolicy(UserVisit userVisit, ReturnPolicy returnPolicy) {
        return getReturnPolicyReasonTransfers(userVisit, getReturnPolicyReasonsByReturnPolicy(returnPolicy));
    }
    
    public List<ReturnPolicyReasonTransfer> getReturnPolicyReasonTransfersByReturnReason(UserVisit userVisit, ReturnReason returnReason) {
        return getReturnPolicyReasonTransfers(userVisit, getReturnPolicyReasonsByReturnReason(returnReason));
    }
    
    public ReturnPolicyReasonTransfer getReturnPolicyReasonTransfer(UserVisit userVisit, ReturnPolicyReason returnPolicyReason) {
        return getReturnPolicyTransferCaches(userVisit).getReturnPolicyReasonTransferCache().getReturnPolicyReasonTransfer(returnPolicyReason);
    }
    
    private void updateReturnPolicyReasonFromValue(ReturnPolicyReasonValue returnPolicyReasonValue, boolean checkDefault, BasePK updatedBy) {
        if(returnPolicyReasonValue.hasBeenModified()) {
            ReturnPolicyReason returnPolicyReason = ReturnPolicyReasonFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     returnPolicyReasonValue.getPrimaryKey());
            
            returnPolicyReason.setThruTime(session.START_TIME_LONG);
            returnPolicyReason.store();
            
            ReturnPolicy returnPolicy = returnPolicyReason.getReturnPolicy(); // Not Updated
            ReturnPolicyPK returnPolicyPK = returnPolicy.getPrimaryKey(); // Not Updated
            ReturnReasonPK returnReasonPK = returnPolicyReason.getReturnReasonPK(); // Not Updated
            Boolean isDefault = returnPolicyReasonValue.getIsDefault();
            Integer sortOrder = returnPolicyReasonValue.getSortOrder();
            
            if(checkDefault) {
                ReturnPolicyReason defaultReturnPolicyReason = getDefaultReturnPolicyReason(returnPolicy);
                boolean defaultFound = defaultReturnPolicyReason != null && !defaultReturnPolicyReason.equals(returnPolicyReason);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    ReturnPolicyReasonValue defaultReturnPolicyReasonValue = getDefaultReturnPolicyReasonValueForUpdate(returnPolicy);
                    
                    defaultReturnPolicyReasonValue.setIsDefault(Boolean.FALSE);
                    updateReturnPolicyReasonFromValue(defaultReturnPolicyReasonValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            returnPolicyReason = ReturnPolicyReasonFactory.getInstance().create(returnPolicyPK, returnReasonPK,
                    isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(returnPolicyPK, EventTypes.MODIFY, returnPolicyReason.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void updateReturnPolicyReasonFromValue(ReturnPolicyReasonValue returnPolicyReasonValue, BasePK updatedBy) {
        updateReturnPolicyReasonFromValue(returnPolicyReasonValue, true, updatedBy);
    }
    
    public void deleteReturnPolicyReason(ReturnPolicyReason returnPolicyReason, BasePK deletedBy) {
        returnPolicyReason.setThruTime(session.START_TIME_LONG);
        returnPolicyReason.store();
        
        // Check for default, and pick one if necessary
        ReturnPolicy returnPolicy = returnPolicyReason.getReturnPolicy();
        ReturnPolicyReason defaultReturnPolicyReason = getDefaultReturnPolicyReason(returnPolicy);
        if(defaultReturnPolicyReason == null) {
            List<ReturnPolicyReason> returnPolicyReasons = getReturnPolicyReasonsByReturnPolicyForUpdate(returnPolicy);
            
            if(!returnPolicyReasons.isEmpty()) {
                Iterator<ReturnPolicyReason> iter = returnPolicyReasons.iterator();
                if(iter.hasNext()) {
                    defaultReturnPolicyReason = iter.next();
                }
                ReturnPolicyReasonValue returnPolicyReasonValue = defaultReturnPolicyReason.getReturnPolicyReasonValue().clone();
                
                returnPolicyReasonValue.setIsDefault(Boolean.TRUE);
                updateReturnPolicyReasonFromValue(returnPolicyReasonValue, false, deletedBy);
            }
        }
        
        sendEvent(returnPolicy.getPrimaryKey(), EventTypes.MODIFY, returnPolicyReason.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteReturnPolicyReasons(List<ReturnPolicyReason> returnPolicyReasons, BasePK deletedBy) {
        returnPolicyReasons.forEach((returnPolicyReason) -> 
                deleteReturnPolicyReason(returnPolicyReason, deletedBy)
        );
    }
    
    public void deleteReturnPolicyReasonsByReturnPolicy(ReturnPolicy returnPolicy, BasePK deletedBy) {
        deleteReturnPolicyReasons(getReturnPolicyReasonsByReturnPolicyForUpdate(returnPolicy), deletedBy);
    }
    
    public void deleteReturnPolicyReasonsByReturnReason(ReturnReason returnReason, BasePK deletedBy) {
        deleteReturnPolicyReasons(getReturnPolicyReasonsByReturnReasonForUpdate(returnReason), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Return Reasons
    // --------------------------------------------------------------------------------
    
    public ReturnReason createReturnReason(ReturnKind returnKind, String returnReasonName, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        ReturnReason defaultReturnReason = getDefaultReturnReason(returnKind);
        boolean defaultFound = defaultReturnReason != null;
        
        if(defaultFound && isDefault) {
            ReturnReasonDetailValue defaultReturnReasonDetailValue = getDefaultReturnReasonDetailValueForUpdate(returnKind);
            
            defaultReturnReasonDetailValue.setIsDefault(Boolean.FALSE);
            updateReturnReasonFromValue(defaultReturnReasonDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        ReturnReason returnReason = ReturnReasonFactory.getInstance().create();
        ReturnReasonDetail returnReasonDetail = ReturnReasonDetailFactory.getInstance().create(session,
                returnReason, returnKind, returnReasonName, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        // Convert to R/W
        returnReason = ReturnReasonFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                returnReason.getPrimaryKey());
        returnReason.setActiveDetail(returnReasonDetail);
        returnReason.setLastDetail(returnReasonDetail);
        returnReason.store();
        
        sendEvent(returnReason.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return returnReason;
    }
    
    private List<ReturnReason> getReturnReasons(ReturnKind returnKind, EntityPermission entityPermission) {
        List<ReturnReason> returnReasons;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM returnreasons, returnreasondetails " +
                        "WHERE rtnrsn_activedetailid = rtnrsndt_returnreasondetailid AND rtnrsndt_rtnk_returnkindid = ? " +
                        "ORDER BY rtnrsndt_sortorder, rtnrsndt_returnreasonname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM returnreasons, returnreasondetails " +
                        "WHERE rtnrsn_activedetailid = rtnrsndt_returnreasondetailid AND rtnrsndt_rtnk_returnkindid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ReturnReasonFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, returnKind.getPrimaryKey().getEntityId());
            
            returnReasons = ReturnReasonFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return returnReasons;
    }
    
    public List<ReturnReason> getReturnReasons(ReturnKind returnKind) {
        return getReturnReasons(returnKind, EntityPermission.READ_ONLY);
    }
    
    public List<ReturnReason> getReturnReasonsForUpdate(ReturnKind returnKind) {
        return getReturnReasons(returnKind, EntityPermission.READ_WRITE);
    }
    
    private ReturnReason getDefaultReturnReason(ReturnKind returnKind, EntityPermission entityPermission) {
        ReturnReason returnReason;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM returnreasons, returnreasondetails " +
                        "WHERE rtnrsn_activedetailid = rtnrsndt_returnreasondetailid " +
                        "AND rtnrsndt_rtnk_returnkindid = ? AND rtnrsndt_isdefault = 1";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM returnreasons, returnreasondetails " +
                        "WHERE rtnrsn_activedetailid = rtnrsndt_returnreasondetailid " +
                        "AND rtnrsndt_rtnk_returnkindid = ? AND rtnrsndt_isdefault = 1 " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ReturnReasonFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, returnKind.getPrimaryKey().getEntityId());
            
            returnReason = ReturnReasonFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return returnReason;
    }
    
    public ReturnReason getDefaultReturnReason(ReturnKind returnKind) {
        return getDefaultReturnReason(returnKind, EntityPermission.READ_ONLY);
    }
    
    public ReturnReason getDefaultReturnReasonForUpdate(ReturnKind returnKind) {
        return getDefaultReturnReason(returnKind, EntityPermission.READ_WRITE);
    }
    
    public ReturnReasonDetailValue getDefaultReturnReasonDetailValueForUpdate(ReturnKind returnKind) {
        return getDefaultReturnReasonForUpdate(returnKind).getLastDetailForUpdate().getReturnReasonDetailValue().clone();
    }
    
    private ReturnReason getReturnReasonByName(ReturnKind returnKind, String returnReasonName, EntityPermission entityPermission) {
        ReturnReason returnReason;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM returnreasons, returnreasondetails " +
                        "WHERE rtnrsn_activedetailid = rtnrsndt_returnreasondetailid " +
                        "AND rtnrsndt_rtnk_returnkindid = ? AND rtnrsndt_returnreasonname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM returnreasons, returnreasondetails " +
                        "WHERE rtnrsn_activedetailid = rtnrsndt_returnreasondetailid " +
                        "AND rtnrsndt_rtnk_returnkindid = ? AND rtnrsndt_returnreasonname = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ReturnReasonFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, returnKind.getPrimaryKey().getEntityId());
            ps.setString(2, returnReasonName);
            
            returnReason = ReturnReasonFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return returnReason;
    }
    
    public ReturnReason getReturnReasonByName(ReturnKind returnKind, String returnReasonName) {
        return getReturnReasonByName(returnKind, returnReasonName, EntityPermission.READ_ONLY);
    }
    
    public ReturnReason getReturnReasonByNameForUpdate(ReturnKind returnKind, String returnReasonName) {
        return getReturnReasonByName(returnKind, returnReasonName, EntityPermission.READ_WRITE);
    }
    
    public ReturnReasonDetailValue getReturnReasonDetailValueForUpdate(ReturnReason returnReason) {
        return returnReason == null? null: returnReason.getLastDetailForUpdate().getReturnReasonDetailValue().clone();
    }
    
    public ReturnReasonDetailValue getReturnReasonDetailValueByNameForUpdate(ReturnKind returnKind, String returnReasonName) {
        return getReturnReasonDetailValueForUpdate(getReturnReasonByNameForUpdate(returnKind, returnReasonName));
    }
    
    public ReturnReasonChoicesBean getReturnReasonChoices(String defaultReturnReasonChoice, Language language,
            boolean allowNullChoice, ReturnKind returnKind) {
        List<ReturnReason> returnReasons = getReturnReasons(returnKind);
        var size = returnReasons.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultReturnReasonChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var returnReason : returnReasons) {
            ReturnReasonDetail returnReasonDetail = returnReason.getLastDetail();
            var label = getBestReturnReasonDescription(returnReason, language);
            var value = returnReasonDetail.getReturnReasonName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultReturnReasonChoice != null && defaultReturnReasonChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && returnReasonDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new ReturnReasonChoicesBean(labels, values, defaultValue);
    }
    
    public ReturnReasonTransfer getReturnReasonTransfer(UserVisit userVisit, ReturnReason returnReason) {
        return getReturnPolicyTransferCaches(userVisit).getReturnReasonTransferCache().getReturnReasonTransfer(returnReason);
    }
    
    public List<ReturnReasonTransfer> getReturnReasonTransfersByReturnKind(UserVisit userVisit, ReturnKind returnKind) {
        List<ReturnReason> returnReasons = getReturnReasons(returnKind);
        List<ReturnReasonTransfer> returnReasonTransfers = new ArrayList<>(returnReasons.size());
        ReturnReasonTransferCache returnReasonTransferCache = getReturnPolicyTransferCaches(userVisit).getReturnReasonTransferCache();
        
        returnReasons.forEach((returnReason) ->
                returnReasonTransfers.add(returnReasonTransferCache.getReturnReasonTransfer(returnReason))
        );
        
        return returnReasonTransfers;
    }
    
    private void updateReturnReasonFromValue(ReturnReasonDetailValue returnReasonDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(returnReasonDetailValue.hasBeenModified()) {
            ReturnReason returnReason = ReturnReasonFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     returnReasonDetailValue.getReturnReasonPK());
            ReturnReasonDetail returnReasonDetail = returnReason.getActiveDetailForUpdate();
            
            returnReasonDetail.setThruTime(session.START_TIME_LONG);
            returnReasonDetail.store();
            
            ReturnReasonPK returnReasonPK = returnReasonDetail.getReturnReasonPK();
            ReturnKind returnKind = returnReasonDetail.getReturnKind();
            ReturnKindPK returnKindPK = returnKind.getPrimaryKey();
            String returnReasonName = returnReasonDetailValue.getReturnReasonName();
            Boolean isDefault = returnReasonDetailValue.getIsDefault();
            Integer sortOrder = returnReasonDetailValue.getSortOrder();
            
            if(checkDefault) {
                ReturnReason defaultReturnReason = getDefaultReturnReason(returnKind);
                boolean defaultFound = defaultReturnReason != null && !defaultReturnReason.equals(returnReason);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    ReturnReasonDetailValue defaultReturnReasonDetailValue = getDefaultReturnReasonDetailValueForUpdate(returnKind);
                    
                    defaultReturnReasonDetailValue.setIsDefault(Boolean.FALSE);
                    updateReturnReasonFromValue(defaultReturnReasonDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            returnReasonDetail = ReturnReasonDetailFactory.getInstance().create(returnReasonPK,
                    returnKindPK, returnReasonName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            returnReason.setActiveDetail(returnReasonDetail);
            returnReason.setLastDetail(returnReasonDetail);
            
            sendEvent(returnReasonPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateReturnReasonFromValue(ReturnReasonDetailValue returnReasonDetailValue, BasePK updatedBy) {
        updateReturnReasonFromValue(returnReasonDetailValue, true, updatedBy);
    }
    
    public void deleteReturnReason(ReturnReason returnReason, BasePK deletedBy) {
        deleteReturnPolicyReasonsByReturnReason(returnReason, deletedBy);
        deleteReturnReasonTypesByReturnReason(returnReason, deletedBy);
        deleteReturnReasonDescriptionsByReturnReason(returnReason, deletedBy);
        
        ReturnReasonDetail returnReasonDetail = returnReason.getLastDetailForUpdate();
        returnReasonDetail.setThruTime(session.START_TIME_LONG);
        returnReason.setActiveDetail(null);
        returnReason.store();
        
        // Check for default, and pick one if necessary
        ReturnKind returnKind = returnReasonDetail.getReturnKind();
        ReturnReason defaultReturnReason = getDefaultReturnReason(returnKind);
        if(defaultReturnReason == null) {
            List<ReturnReason> returnReasons = getReturnReasonsForUpdate(returnKind);
            
            if(!returnReasons.isEmpty()) {
                Iterator<ReturnReason> iter = returnReasons.iterator();
                if(iter.hasNext()) {
                    defaultReturnReason = iter.next();
                }
                ReturnReasonDetailValue returnReasonDetailValue = Objects.requireNonNull(defaultReturnReason).getLastDetailForUpdate().getReturnReasonDetailValue().clone();
                
                returnReasonDetailValue.setIsDefault(Boolean.TRUE);
                updateReturnReasonFromValue(returnReasonDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(returnReason.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteReturnReasonsByReturnKind(ReturnKind returnKind, BasePK deletedBy) {
        List<ReturnReason> returnReasons = getReturnReasonsForUpdate(returnKind);
        
        returnReasons.forEach((returnReason) -> 
                deleteReturnReason(returnReason, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Return Reason Descriptions
    // --------------------------------------------------------------------------------
    
    public ReturnReasonDescription createReturnReasonDescription(ReturnReason returnReason, Language language, String description,
            BasePK createdBy) {
        ReturnReasonDescription returnReasonDescription = ReturnReasonDescriptionFactory.getInstance().create(returnReason,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(returnReason.getPrimaryKey(), EventTypes.MODIFY, returnReasonDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return returnReasonDescription;
    }
    
    private ReturnReasonDescription getReturnReasonDescription(ReturnReason returnReason, Language language, EntityPermission entityPermission) {
        ReturnReasonDescription returnReasonDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM returnreasondescriptions " +
                        "WHERE rtnrsnd_rtnrsn_returnreasonid = ? AND rtnrsnd_lang_languageid = ? AND rtnrsnd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM returnreasondescriptions " +
                        "WHERE rtnrsnd_rtnrsn_returnreasonid = ? AND rtnrsnd_lang_languageid = ? AND rtnrsnd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ReturnReasonDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, returnReason.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            returnReasonDescription = ReturnReasonDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return returnReasonDescription;
    }
    
    public ReturnReasonDescription getReturnReasonDescription(ReturnReason returnReason, Language language) {
        return getReturnReasonDescription(returnReason, language, EntityPermission.READ_ONLY);
    }
    
    public ReturnReasonDescription getReturnReasonDescriptionForUpdate(ReturnReason returnReason, Language language) {
        return getReturnReasonDescription(returnReason, language, EntityPermission.READ_WRITE);
    }
    
    public ReturnReasonDescriptionValue getReturnReasonDescriptionValue(ReturnReasonDescription returnReasonDescription) {
        return returnReasonDescription == null? null: returnReasonDescription.getReturnReasonDescriptionValue().clone();
    }
    
    public ReturnReasonDescriptionValue getReturnReasonDescriptionValueForUpdate(ReturnReason returnReason, Language language) {
        return getReturnReasonDescriptionValue(getReturnReasonDescriptionForUpdate(returnReason, language));
    }
    
    private List<ReturnReasonDescription> getReturnReasonDescriptionsByReturnReason(ReturnReason returnReason, EntityPermission entityPermission) {
        List<ReturnReasonDescription> returnReasonDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM returnreasondescriptions, languages " +
                        "WHERE rtnrsnd_rtnrsn_returnreasonid = ? AND rtnrsnd_thrutime = ? AND rtnrsnd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM returnreasondescriptions " +
                        "WHERE rtnrsnd_rtnrsn_returnreasonid = ? AND rtnrsnd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ReturnReasonDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, returnReason.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            returnReasonDescriptions = ReturnReasonDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return returnReasonDescriptions;
    }
    
    public List<ReturnReasonDescription> getReturnReasonDescriptionsByReturnReason(ReturnReason returnReason) {
        return getReturnReasonDescriptionsByReturnReason(returnReason, EntityPermission.READ_ONLY);
    }
    
    public List<ReturnReasonDescription> getReturnReasonDescriptionsByReturnReasonForUpdate(ReturnReason returnReason) {
        return getReturnReasonDescriptionsByReturnReason(returnReason, EntityPermission.READ_WRITE);
    }
    
    public String getBestReturnReasonDescription(ReturnReason returnReason, Language language) {
        String description;
        ReturnReasonDescription returnReasonDescription = getReturnReasonDescription(returnReason, language);
        
        if(returnReasonDescription == null && !language.getIsDefault()) {
            returnReasonDescription = getReturnReasonDescription(returnReason, getPartyControl().getDefaultLanguage());
        }
        
        if(returnReasonDescription == null) {
            description = returnReason.getLastDetail().getReturnReasonName();
        } else {
            description = returnReasonDescription.getDescription();
        }
        
        return description;
    }
    
    public ReturnReasonDescriptionTransfer getReturnReasonDescriptionTransfer(UserVisit userVisit, ReturnReasonDescription returnReasonDescription) {
        return getReturnPolicyTransferCaches(userVisit).getReturnReasonDescriptionTransferCache().getReturnReasonDescriptionTransfer(returnReasonDescription);
    }
    
    public List<ReturnReasonDescriptionTransfer> getReturnReasonDescriptionTransfersByReturnReason(UserVisit userVisit, ReturnReason returnReason) {
        List<ReturnReasonDescription> returnReasonDescriptions = getReturnReasonDescriptionsByReturnReason(returnReason);
        List<ReturnReasonDescriptionTransfer> returnReasonDescriptionTransfers = new ArrayList<>(returnReasonDescriptions.size());
        
        returnReasonDescriptions.forEach((returnReasonDescription) -> {
            returnReasonDescriptionTransfers.add(getReturnPolicyTransferCaches(userVisit).getReturnReasonDescriptionTransferCache().getReturnReasonDescriptionTransfer(returnReasonDescription));
        });
        
        return returnReasonDescriptionTransfers;
    }
    
    public void updateReturnReasonDescriptionFromValue(ReturnReasonDescriptionValue returnReasonDescriptionValue, BasePK updatedBy) {
        if(returnReasonDescriptionValue.hasBeenModified()) {
            ReturnReasonDescription returnReasonDescription = ReturnReasonDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     returnReasonDescriptionValue.getPrimaryKey());
            
            returnReasonDescription.setThruTime(session.START_TIME_LONG);
            returnReasonDescription.store();
            
            ReturnReason returnReason = returnReasonDescription.getReturnReason();
            Language language = returnReasonDescription.getLanguage();
            String description = returnReasonDescriptionValue.getDescription();
            
            returnReasonDescription = ReturnReasonDescriptionFactory.getInstance().create(returnReason, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(returnReason.getPrimaryKey(), EventTypes.MODIFY, returnReasonDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteReturnReasonDescription(ReturnReasonDescription returnReasonDescription, BasePK deletedBy) {
        returnReasonDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(returnReasonDescription.getReturnReasonPK(), EventTypes.MODIFY, returnReasonDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteReturnReasonDescriptionsByReturnReason(ReturnReason returnReason, BasePK deletedBy) {
        List<ReturnReasonDescription> returnReasonDescriptions = getReturnReasonDescriptionsByReturnReasonForUpdate(returnReason);
        
        returnReasonDescriptions.forEach((returnReasonDescription) -> 
                deleteReturnReasonDescription(returnReasonDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Return Reason Types
    // --------------------------------------------------------------------------------
    
    public ReturnReasonType createReturnReasonType(ReturnReason returnReason, ReturnType returnType, Boolean isDefault,
            Integer sortOrder, BasePK createdBy) {
        ReturnReasonType defaultReturnReasonType = getDefaultReturnReasonType(returnReason);
        boolean defaultFound = defaultReturnReasonType != null;
        
        if(defaultFound && isDefault) {
            ReturnReasonTypeValue defaultReturnReasonTypeValue = getDefaultReturnReasonTypeValueForUpdate(returnReason);
            
            defaultReturnReasonTypeValue.setIsDefault(Boolean.FALSE);
            updateReturnReasonTypeFromValue(defaultReturnReasonTypeValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        ReturnReasonType returnReasonType = ReturnReasonTypeFactory.getInstance().create(returnReason, returnType,
                isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(returnReason.getPrimaryKey(), EventTypes.MODIFY, returnReasonType.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return returnReasonType;
    }
    
    private ReturnReasonType getReturnReasonType(ReturnReason returnReason, ReturnType returnType, EntityPermission entityPermission) {
        ReturnReasonType returnReasonType;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM returnreasontypes " +
                        "WHERE rtnrsntyp_rtnrsn_returnreasonid = ? AND rtnrsntyp_rtntyp_returntypeid = ? AND rtnrsntyp_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM returnreasontypes " +
                        "WHERE rtnrsntyp_rtnrsn_returnreasonid = ? AND rtnrsntyp_rtntyp_returntypeid = ? AND rtnrsntyp_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ReturnReasonTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, returnReason.getPrimaryKey().getEntityId());
            ps.setLong(2, returnType.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            returnReasonType = ReturnReasonTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return returnReasonType;
    }
    
    public ReturnReasonType getReturnReasonType(ReturnReason returnReason, ReturnType returnType) {
        return getReturnReasonType(returnReason, returnType, EntityPermission.READ_ONLY);
    }
    
    public ReturnReasonType getReturnReasonTypeForUpdate(ReturnReason returnReason, ReturnType returnType) {
        return getReturnReasonType(returnReason, returnType, EntityPermission.READ_WRITE);
    }
    
    public ReturnReasonTypeValue getReturnReasonTypeValueForUpdate(ReturnReason returnReason, ReturnType returnType) {
        ReturnReasonType returnReasonType = getReturnReasonTypeForUpdate(returnReason, returnType);
        
        return returnReasonType == null? null: returnReasonType.getReturnReasonTypeValue().clone();
    }
    
    private ReturnReasonType getDefaultReturnReasonType(ReturnReason returnReason, EntityPermission entityPermission) {
        ReturnReasonType returnReasonType;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM returnreasontypes " +
                        "WHERE rtnrsntyp_rtnrsn_returnreasonid = ? AND rtnrsntyp_isdefault = 1 AND rtnrsntyp_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM returnreasontypes " +
                        "WHERE rtnrsntyp_rtnrsn_returnreasonid = ? AND rtnrsntyp_isdefault = 1 AND rtnrsntyp_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ReturnReasonTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, returnReason.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            returnReasonType = ReturnReasonTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return returnReasonType;
    }
    
    public ReturnReasonType getDefaultReturnReasonType(ReturnReason returnReason) {
        return getDefaultReturnReasonType(returnReason, EntityPermission.READ_ONLY);
    }
    
    public ReturnReasonType getDefaultReturnReasonTypeForUpdate(ReturnReason returnReason) {
        return getDefaultReturnReasonType(returnReason, EntityPermission.READ_WRITE);
    }
    
    public ReturnReasonTypeValue getDefaultReturnReasonTypeValueForUpdate(ReturnReason returnReason) {
        ReturnReasonType returnReasonType = getDefaultReturnReasonTypeForUpdate(returnReason);
        
        return returnReasonType == null? null: returnReasonType.getReturnReasonTypeValue().clone();
    }
    
    private List<ReturnReasonType> getReturnReasonTypesByReturnReason(ReturnReason returnReason, EntityPermission entityPermission) {
        List<ReturnReasonType> returnReasonTypes;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM returnreasontypes, returntypes, returntypedetails, returnkinds, returnkinddetails " +
                        "WHERE rtnrsntyp_rtnrsn_returnreasonid = ? AND rtnrsntyp_thrutime = ? " +
                        "AND rtnrsntyp_rtntyp_returntypeid = rtntyp_returntypeid AND rtntyp_lastdetailid = rtntypdt_returntypedetailid " +
                        "AND rtntypdt_rtnk_returnkindid = rtnk_returnkindid AND rtnk_lastdetailid = rtnkdt_returnkinddetailid " +
                        "ORDER BY rtntypdt_sortorder, rtntypdt_returntypename, rtnkdt_sortorder, rtnkdt_returnkindname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM returnreasontypes " +
                        "WHERE rtnrsntyp_rtnrsn_returnreasonid = ? AND rtnrsntyp_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ReturnReasonTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, returnReason.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            returnReasonTypes = ReturnReasonTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return returnReasonTypes;
    }
    
    public List<ReturnReasonType> getReturnReasonTypesByReturnReason(ReturnReason returnReason) {
        return getReturnReasonTypesByReturnReason(returnReason, EntityPermission.READ_ONLY);
    }
    
    public List<ReturnReasonType> getReturnReasonTypesByReturnReasonForUpdate(ReturnReason returnReason) {
        return getReturnReasonTypesByReturnReason(returnReason, EntityPermission.READ_WRITE);
    }
    
    private List<ReturnReasonType> getReturnReasonTypesByReturnType(ReturnType returnType, EntityPermission entityPermission) {
        List<ReturnReasonType> returnReasonTypes;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM returnreasontypes, returnreasons, returnreasondetails " +
                        "WHERE rtnrsntyp_rtntyp_returntypeid = ? AND rtnrsntyp_thrutime = ? " +
                        "AND rtnrsntyp_rtnrsn_returnreasonid = rtnrsn_returnreasonid AND rtnrsn_lastdetailid = rtnrsndt_returnreasondetailid " +
                        "ORDER BY rtnrsndt_sortorder, rtnrsndt_returnreasonname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM returnreasontypes " +
                        "WHERE rtnrsntyp_rtntyp_returntypeid = ? AND rtnrsntyp_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ReturnReasonTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, returnType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            returnReasonTypes = ReturnReasonTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return returnReasonTypes;
    }
    
    public List<ReturnReasonType> getReturnReasonTypesByReturnType(ReturnType returnType) {
        return getReturnReasonTypesByReturnType(returnType, EntityPermission.READ_ONLY);
    }
    
    public List<ReturnReasonType> getReturnReasonTypesByReturnTypeForUpdate(ReturnType returnType) {
        return getReturnReasonTypesByReturnType(returnType, EntityPermission.READ_WRITE);
    }
    
    public List<ReturnReasonTypeTransfer> getReturnReasonTypeTransfers(UserVisit userVisit, Collection<ReturnReasonType> returnReasonTypes) {
        List<ReturnReasonTypeTransfer> returnReasonTypeTransfers = new ArrayList<>(returnReasonTypes.size());
        ReturnReasonTypeTransferCache returnReasonTypeTransferCache = getReturnPolicyTransferCaches(userVisit).getReturnReasonTypeTransferCache();
        
        returnReasonTypes.forEach((returnReasonType) ->
                returnReasonTypeTransfers.add(returnReasonTypeTransferCache.getReturnReasonTypeTransfer(returnReasonType))
        );
        
        return returnReasonTypeTransfers;
    }
    
    public List<ReturnReasonTypeTransfer> getReturnReasonTypeTransfersByReturnReason(UserVisit userVisit, ReturnReason returnReason) {
        return getReturnReasonTypeTransfers(userVisit, getReturnReasonTypesByReturnReason(returnReason));
    }
    
    public List<ReturnReasonTypeTransfer> getReturnReasonTypeTransfersByReturnType(UserVisit userVisit, ReturnType returnType) {
        return getReturnReasonTypeTransfers(userVisit, getReturnReasonTypesByReturnType(returnType));
    }
    
    public ReturnReasonTypeTransfer getReturnReasonTypeTransfer(UserVisit userVisit, ReturnReasonType returnReasonType) {
        return getReturnPolicyTransferCaches(userVisit).getReturnReasonTypeTransferCache().getReturnReasonTypeTransfer(returnReasonType);
    }
    
    private void updateReturnReasonTypeFromValue(ReturnReasonTypeValue returnReasonTypeValue, boolean checkDefault, BasePK updatedBy) {
        if(returnReasonTypeValue.hasBeenModified()) {
            ReturnReasonType returnReasonType = ReturnReasonTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     returnReasonTypeValue.getPrimaryKey());
            
            returnReasonType.setThruTime(session.START_TIME_LONG);
            returnReasonType.store();
            
            ReturnReason returnReason = returnReasonType.getReturnReason(); // Not Updated
            ReturnReasonPK returnReasonPK = returnReason.getPrimaryKey(); // Not Updated
            ReturnTypePK returnTypePK = returnReasonType.getReturnTypePK(); // Not Updated
            Boolean isDefault = returnReasonTypeValue.getIsDefault();
            Integer sortOrder = returnReasonTypeValue.getSortOrder();
            
            if(checkDefault) {
                ReturnReasonType defaultReturnReasonType = getDefaultReturnReasonType(returnReason);
                boolean defaultFound = defaultReturnReasonType != null && !defaultReturnReasonType.equals(returnReasonType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    ReturnReasonTypeValue defaultReturnReasonTypeValue = getDefaultReturnReasonTypeValueForUpdate(returnReason);
                    
                    defaultReturnReasonTypeValue.setIsDefault(Boolean.FALSE);
                    updateReturnReasonTypeFromValue(defaultReturnReasonTypeValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            returnReasonType = ReturnReasonTypeFactory.getInstance().create(returnReasonPK, returnTypePK,
                    isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(returnReasonPK, EventTypes.MODIFY, returnReasonType.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void updateReturnReasonTypeFromValue(ReturnReasonTypeValue returnReasonTypeValue, BasePK updatedBy) {
        updateReturnReasonTypeFromValue(returnReasonTypeValue, true, updatedBy);
    }
    
    public void deleteReturnReasonType(ReturnReasonType returnReasonType, BasePK deletedBy) {
        returnReasonType.setThruTime(session.START_TIME_LONG);
        returnReasonType.store();
        
        // Check for default, and pick one if necessary
        ReturnReason returnReason = returnReasonType.getReturnReason();
        ReturnReasonType defaultReturnReasonType = getDefaultReturnReasonType(returnReason);
        if(defaultReturnReasonType == null) {
            List<ReturnReasonType> returnReasonTypes = getReturnReasonTypesByReturnReasonForUpdate(returnReason);
            
            if(!returnReasonTypes.isEmpty()) {
                Iterator<ReturnReasonType> iter = returnReasonTypes.iterator();
                if(iter.hasNext()) {
                    defaultReturnReasonType = iter.next();
                }
                ReturnReasonTypeValue returnReasonTypeValue = defaultReturnReasonType.getReturnReasonTypeValue().clone();
                
                returnReasonTypeValue.setIsDefault(Boolean.TRUE);
                updateReturnReasonTypeFromValue(returnReasonTypeValue, false, deletedBy);
            }
        }
        
        sendEvent(returnReason.getPrimaryKey(), EventTypes.MODIFY, returnReasonType.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteReturnReasonTypes(List<ReturnReasonType> returnReasonTypes, BasePK deletedBy) {
        returnReasonTypes.forEach((returnReasonType) -> 
                deleteReturnReasonType(returnReasonType, deletedBy)
        );
    }
    
    public void deleteReturnReasonTypesByReturnReason(ReturnReason returnReason, BasePK deletedBy) {
        deleteReturnReasonTypes(getReturnReasonTypesByReturnReasonForUpdate(returnReason), deletedBy);
    }
    
    public void deleteReturnReasonTypesByReturnType(ReturnType returnType, BasePK deletedBy) {
        deleteReturnReasonTypes(getReturnReasonTypesByReturnTypeForUpdate(returnType), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Return Types
    // --------------------------------------------------------------------------------
    
    public ReturnType createReturnType(ReturnKind returnKind, String returnTypeName, Sequence returnSequence, Boolean isDefault,
            Integer sortOrder, BasePK createdBy) {
        ReturnType defaultReturnType = getDefaultReturnType(returnKind);
        boolean defaultFound = defaultReturnType != null;
        
        if(defaultFound && isDefault) {
            ReturnTypeDetailValue defaultReturnTypeDetailValue = getDefaultReturnTypeDetailValueForUpdate(returnKind);
            
            defaultReturnTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateReturnTypeFromValue(defaultReturnTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        ReturnType returnType = ReturnTypeFactory.getInstance().create();
        ReturnTypeDetail returnTypeDetail = ReturnTypeDetailFactory.getInstance().create(session,
                returnType, returnKind, returnTypeName, returnSequence, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        // Convert to R/W
        returnType = ReturnTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                returnType.getPrimaryKey());
        returnType.setActiveDetail(returnTypeDetail);
        returnType.setLastDetail(returnTypeDetail);
        returnType.store();
        
        sendEvent(returnType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return returnType;
    }

    private static final Map<EntityPermission, String> getReturnTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM returntypes, returntypedetails "
                + "WHERE rtntyp_activedetailid = rtntypdt_returntypedetailid AND rtntypdt_rtnk_returnkindid = ? "
                + "ORDER BY rtntypdt_sortorder, rtntypdt_returntypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM returntypes, returntypedetails "
                + "WHERE rtntyp_activedetailid = rtntypdt_returntypedetailid AND rtntypdt_rtnk_returnkindid = ? "
                + "FOR UPDATE");
        getReturnTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ReturnType> getReturnTypes(ReturnKind returnKind, EntityPermission entityPermission) {
        return ReturnTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getReturnTypesQueries,
                returnKind);
    }
    
    public List<ReturnType> getReturnTypes(ReturnKind returnKind) {
        return getReturnTypes(returnKind, EntityPermission.READ_ONLY);
    }
    
    public List<ReturnType> getReturnTypesForUpdate(ReturnKind returnKind) {
        return getReturnTypes(returnKind, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getDefaultReturnTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM returntypes, returntypedetails "
                + "WHERE rtntyp_activedetailid = rtntypdt_returntypedetailid "
                + "AND rtntypdt_rtnk_returnkindid = ? AND rtntypdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM returntypes, returntypedetails "
                + "WHERE rtntyp_activedetailid = rtntypdt_returntypedetailid "
                + "AND rtntypdt_rtnk_returnkindid = ? AND rtntypdt_isdefault = 1 "
                + "FOR UPDATE");
        getDefaultReturnTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private ReturnType getDefaultReturnType(ReturnKind returnKind, EntityPermission entityPermission) {
        return ReturnTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultReturnTypeQueries,
                returnKind);
    }
    
    public ReturnType getDefaultReturnType(ReturnKind returnKind) {
        return getDefaultReturnType(returnKind, EntityPermission.READ_ONLY);
    }
    
    public ReturnType getDefaultReturnTypeForUpdate(ReturnKind returnKind) {
        return getDefaultReturnType(returnKind, EntityPermission.READ_WRITE);
    }
    
    public ReturnTypeDetailValue getDefaultReturnTypeDetailValueForUpdate(ReturnKind returnKind) {
        return getDefaultReturnTypeForUpdate(returnKind).getLastDetailForUpdate().getReturnTypeDetailValue().clone();
    }
    
    private static final Map<EntityPermission, String> getReturnTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM returntypes, returntypedetails "
                + "WHERE rtntyp_activedetailid = rtntypdt_returntypedetailid "
                + "AND rtntypdt_rtnk_returnkindid = ? AND rtntypdt_returntypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM returntypes, returntypedetails "
                + "WHERE rtntyp_activedetailid = rtntypdt_returntypedetailid "
                + "AND rtntypdt_rtnk_returnkindid = ? AND rtntypdt_returntypename = ? "
                + "FOR UPDATE");
        getReturnTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private ReturnType getReturnTypeByName(ReturnKind returnKind, String returnTypeName, EntityPermission entityPermission) {
        return ReturnTypeFactory.getInstance().getEntityFromQuery(entityPermission, getReturnTypeByNameQueries,
                returnKind, returnTypeName);
    }
    
    public ReturnType getReturnTypeByName(ReturnKind returnKind, String returnTypeName) {
        return getReturnTypeByName(returnKind, returnTypeName, EntityPermission.READ_ONLY);
    }
    
    public ReturnType getReturnTypeByNameForUpdate(ReturnKind returnKind, String returnTypeName) {
        return getReturnTypeByName(returnKind, returnTypeName, EntityPermission.READ_WRITE);
    }
    
    public ReturnTypeDetailValue getReturnTypeDetailValueForUpdate(ReturnType returnType) {
        return returnType == null? null: returnType.getLastDetailForUpdate().getReturnTypeDetailValue().clone();
    }
    
    public ReturnTypeDetailValue getReturnTypeDetailValueByNameForUpdate(ReturnKind returnKind, String returnTypeName) {
        return getReturnTypeDetailValueForUpdate(getReturnTypeByNameForUpdate(returnKind, returnTypeName));
    }
    
    public ReturnTypeChoicesBean getReturnTypeChoices(String defaultReturnTypeChoice, Language language,
            boolean allowNullChoice, ReturnKind returnKind) {
        List<ReturnType> returnTypes = getReturnTypes(returnKind);
        var size = returnTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultReturnTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var returnType : returnTypes) {
            ReturnTypeDetail returnTypeDetail = returnType.getLastDetail();
            var label = getBestReturnTypeDescription(returnType, language);
            var value = returnTypeDetail.getReturnTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultReturnTypeChoice != null && defaultReturnTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && returnTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new ReturnTypeChoicesBean(labels, values, defaultValue);
    }
    
    public ReturnTypeTransfer getReturnTypeTransfer(UserVisit userVisit, ReturnType returnType) {
        return getReturnPolicyTransferCaches(userVisit).getReturnTypeTransferCache().getReturnTypeTransfer(returnType);
    }
    
    public List<ReturnTypeTransfer> getReturnTypeTransfersByReturnKind(UserVisit userVisit, ReturnKind returnKind) {
        List<ReturnType> returnTypes = getReturnTypes(returnKind);
        List<ReturnTypeTransfer> returnTypeTransfers = new ArrayList<>(returnTypes.size());
        ReturnTypeTransferCache returnTypeTransferCache = getReturnPolicyTransferCaches(userVisit).getReturnTypeTransferCache();
        
        returnTypes.forEach((returnType) ->
                returnTypeTransfers.add(returnTypeTransferCache.getReturnTypeTransfer(returnType))
        );
        
        return returnTypeTransfers;
    }
    
    private void updateReturnTypeFromValue(ReturnTypeDetailValue returnTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(returnTypeDetailValue.hasBeenModified()) {
            ReturnType returnType = ReturnTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     returnTypeDetailValue.getReturnTypePK());
            ReturnTypeDetail returnTypeDetail = returnType.getActiveDetailForUpdate();
            
            returnTypeDetail.setThruTime(session.START_TIME_LONG);
            returnTypeDetail.store();
            
            ReturnTypePK returnTypePK = returnTypeDetail.getReturnTypePK();
            ReturnKind returnKind = returnTypeDetail.getReturnKind();
            ReturnKindPK returnKindPK = returnKind.getPrimaryKey();
            String returnTypeName = returnTypeDetailValue.getReturnTypeName();
            SequencePK returnSequencePK = returnTypeDetailValue.getReturnSequencePK();
            Boolean isDefault = returnTypeDetailValue.getIsDefault();
            Integer sortOrder = returnTypeDetailValue.getSortOrder();
            
            if(checkDefault) {
                ReturnType defaultReturnType = getDefaultReturnType(returnKind);
                boolean defaultFound = defaultReturnType != null && !defaultReturnType.equals(returnType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    ReturnTypeDetailValue defaultReturnTypeDetailValue = getDefaultReturnTypeDetailValueForUpdate(returnKind);
                    
                    defaultReturnTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updateReturnTypeFromValue(defaultReturnTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            returnTypeDetail = ReturnTypeDetailFactory.getInstance().create(returnTypePK, returnKindPK, returnTypeName,
                    returnSequencePK, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            returnType.setActiveDetail(returnTypeDetail);
            returnType.setLastDetail(returnTypeDetail);
            
            sendEvent(returnTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateReturnTypeFromValue(ReturnTypeDetailValue returnTypeDetailValue, BasePK updatedBy) {
        updateReturnTypeFromValue(returnTypeDetailValue, true, updatedBy);
    }
    
    public void deleteReturnType(ReturnType returnType, BasePK deletedBy) {
        deleteReturnReasonTypesByReturnType(returnType, deletedBy);
        deleteReturnTypeShippingMethodsByReturnType(returnType, deletedBy);
        deleteReturnTypeDescriptionsByReturnType(returnType, deletedBy);
        
        ReturnTypeDetail returnTypeDetail = returnType.getLastDetailForUpdate();
        returnTypeDetail.setThruTime(session.START_TIME_LONG);
        returnType.setActiveDetail(null);
        returnType.store();
        
        // Check for default, and pick one if necessary
        ReturnKind returnKind = returnTypeDetail.getReturnKind();
        ReturnType defaultReturnType = getDefaultReturnType(returnKind);
        if(defaultReturnType == null) {
            List<ReturnType> returnTypes = getReturnTypesForUpdate(returnKind);
            
            if(!returnTypes.isEmpty()) {
                Iterator<ReturnType> iter = returnTypes.iterator();
                if(iter.hasNext()) {
                    defaultReturnType = iter.next();
                }
                ReturnTypeDetailValue returnTypeDetailValue = Objects.requireNonNull(defaultReturnType).getLastDetailForUpdate().getReturnTypeDetailValue().clone();
                
                returnTypeDetailValue.setIsDefault(Boolean.TRUE);
                updateReturnTypeFromValue(returnTypeDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(returnType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteReturnTypesByReturnKind(ReturnKind returnKind, BasePK deletedBy) {
        List<ReturnType> returnTypes = getReturnTypesForUpdate(returnKind);
        
        returnTypes.forEach((returnType) -> 
                deleteReturnType(returnType, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Return Type Descriptions
    // --------------------------------------------------------------------------------
    
    public ReturnTypeDescription createReturnTypeDescription(ReturnType returnType, Language language, String description,
            BasePK createdBy) {
        ReturnTypeDescription returnTypeDescription = ReturnTypeDescriptionFactory.getInstance().create(returnType,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(returnType.getPrimaryKey(), EventTypes.MODIFY, returnTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return returnTypeDescription;
    }
    
    private static final Map<EntityPermission, String> getReturnTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM returntypedescriptions "
                + "WHERE rtntypd_rtntyp_returntypeid = ? AND rtntypd_lang_languageid = ? AND rtntypd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM returntypedescriptions "
                + "WHERE rtntypd_rtntyp_returntypeid = ? AND rtntypd_lang_languageid = ? AND rtntypd_thrutime = ? "
                + "FOR UPDATE");
        getReturnTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private ReturnTypeDescription getReturnTypeDescription(ReturnType returnType, Language language, EntityPermission entityPermission) {
        return ReturnTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getReturnTypeDescriptionQueries,
                returnType, language, Session.MAX_TIME);
    }
    
    public ReturnTypeDescription getReturnTypeDescription(ReturnType returnType, Language language) {
        return getReturnTypeDescription(returnType, language, EntityPermission.READ_ONLY);
    }
    
    public ReturnTypeDescription getReturnTypeDescriptionForUpdate(ReturnType returnType, Language language) {
        return getReturnTypeDescription(returnType, language, EntityPermission.READ_WRITE);
    }
    
    public ReturnTypeDescriptionValue getReturnTypeDescriptionValue(ReturnTypeDescription returnTypeDescription) {
        return returnTypeDescription == null? null: returnTypeDescription.getReturnTypeDescriptionValue().clone();
    }
    
    public ReturnTypeDescriptionValue getReturnTypeDescriptionValueForUpdate(ReturnType returnType, Language language) {
        return getReturnTypeDescriptionValue(getReturnTypeDescriptionForUpdate(returnType, language));
    }
    
    private static final Map<EntityPermission, String> getReturnTypeDescriptionsByReturnTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM returntypedescriptions, languages "
                + "WHERE rtntypd_rtntyp_returntypeid = ? AND rtntypd_thrutime = ? AND rtntypd_lang_languageid = lang_languageid "
                + "ORDER BY lang_sortorder, lang_languageisoname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM returntypedescriptions "
                + "WHERE rtntypd_rtntyp_returntypeid = ? AND rtntypd_thrutime = ? "
                + "FOR UPDATE");
        getReturnTypeDescriptionsByReturnTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ReturnTypeDescription> getReturnTypeDescriptionsByReturnType(ReturnType returnType, EntityPermission entityPermission) {
        return ReturnTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getReturnTypeDescriptionsByReturnTypeQueries,
                returnType, Session.MAX_TIME);
    }
    
    public List<ReturnTypeDescription> getReturnTypeDescriptionsByReturnType(ReturnType returnType) {
        return getReturnTypeDescriptionsByReturnType(returnType, EntityPermission.READ_ONLY);
    }
    
    public List<ReturnTypeDescription> getReturnTypeDescriptionsByReturnTypeForUpdate(ReturnType returnType) {
        return getReturnTypeDescriptionsByReturnType(returnType, EntityPermission.READ_WRITE);
    }
    
    public String getBestReturnTypeDescription(ReturnType returnType, Language language) {
        String description;
        ReturnTypeDescription returnTypeDescription = getReturnTypeDescription(returnType, language);
        
        if(returnTypeDescription == null && !language.getIsDefault()) {
            returnTypeDescription = getReturnTypeDescription(returnType, getPartyControl().getDefaultLanguage());
        }
        
        if(returnTypeDescription == null) {
            description = returnType.getLastDetail().getReturnTypeName();
        } else {
            description = returnTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public ReturnTypeDescriptionTransfer getReturnTypeDescriptionTransfer(UserVisit userVisit, ReturnTypeDescription returnTypeDescription) {
        return getReturnPolicyTransferCaches(userVisit).getReturnTypeDescriptionTransferCache().getReturnTypeDescriptionTransfer(returnTypeDescription);
    }
    
    public List<ReturnTypeDescriptionTransfer> getReturnTypeDescriptionTransfersByReturnType(UserVisit userVisit, ReturnType returnType) {
        List<ReturnTypeDescription> returnTypeDescriptions = getReturnTypeDescriptionsByReturnType(returnType);
        List<ReturnTypeDescriptionTransfer> returnTypeDescriptionTransfers = new ArrayList<>(returnTypeDescriptions.size());
        
        returnTypeDescriptions.forEach((returnTypeDescription) -> {
            returnTypeDescriptionTransfers.add(getReturnPolicyTransferCaches(userVisit).getReturnTypeDescriptionTransferCache().getReturnTypeDescriptionTransfer(returnTypeDescription));
        });
        
        return returnTypeDescriptionTransfers;
    }
    
    public void updateReturnTypeDescriptionFromValue(ReturnTypeDescriptionValue returnTypeDescriptionValue, BasePK updatedBy) {
        if(returnTypeDescriptionValue.hasBeenModified()) {
            ReturnTypeDescription returnTypeDescription = ReturnTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     returnTypeDescriptionValue.getPrimaryKey());
            
            returnTypeDescription.setThruTime(session.START_TIME_LONG);
            returnTypeDescription.store();
            
            ReturnType returnType = returnTypeDescription.getReturnType();
            Language language = returnTypeDescription.getLanguage();
            String description = returnTypeDescriptionValue.getDescription();
            
            returnTypeDescription = ReturnTypeDescriptionFactory.getInstance().create(returnType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(returnType.getPrimaryKey(), EventTypes.MODIFY, returnTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteReturnTypeDescription(ReturnTypeDescription returnTypeDescription, BasePK deletedBy) {
        returnTypeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(returnTypeDescription.getReturnTypePK(), EventTypes.MODIFY, returnTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteReturnTypeDescriptionsByReturnType(ReturnType returnType, BasePK deletedBy) {
        List<ReturnTypeDescription> returnTypeDescriptions = getReturnTypeDescriptionsByReturnTypeForUpdate(returnType);
        
        returnTypeDescriptions.forEach((returnTypeDescription) -> 
                deleteReturnTypeDescription(returnTypeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Return Type Shipping Methods
    // --------------------------------------------------------------------------------
    
    public ReturnTypeShippingMethod createReturnTypeShippingMethod(ReturnType returnType, ShippingMethod shippingMethod, Boolean isDefault,
            Integer sortOrder, BasePK createdBy) {
        ReturnTypeShippingMethod defaultReturnTypeShippingMethod = getDefaultReturnTypeShippingMethod(returnType);
        boolean defaultFound = defaultReturnTypeShippingMethod != null;
        
        if(defaultFound && isDefault) {
            ReturnTypeShippingMethodValue defaultReturnTypeShippingMethodValue = getDefaultReturnTypeShippingMethodValueForUpdate(returnType);
            
            defaultReturnTypeShippingMethodValue.setIsDefault(Boolean.FALSE);
            updateReturnTypeShippingMethodFromValue(defaultReturnTypeShippingMethodValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        ReturnTypeShippingMethod returnTypeShippingMethod = ReturnTypeShippingMethodFactory.getInstance().create(returnType, shippingMethod,
                isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(returnType.getPrimaryKey(), EventTypes.MODIFY, returnTypeShippingMethod.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return returnTypeShippingMethod;
    }
    
    private ReturnTypeShippingMethod getReturnTypeShippingMethod(ReturnType returnType, ShippingMethod shippingMethod, EntityPermission entityPermission) {
        ReturnTypeShippingMethod returnTypeShippingMethod;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM returntypeshippingmethods " +
                        "WHERE rtntypshm_rtntyp_returntypeid = ? AND rtntypshm_shm_shippingmethodid = ? AND rtntypshm_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM returntypeshippingmethods " +
                        "WHERE rtntypshm_rtntyp_returntypeid = ? AND rtntypshm_shm_shippingmethodid = ? AND rtntypshm_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ReturnTypeShippingMethodFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, returnType.getPrimaryKey().getEntityId());
            ps.setLong(2, shippingMethod.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            returnTypeShippingMethod = ReturnTypeShippingMethodFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return returnTypeShippingMethod;
    }
    
    public ReturnTypeShippingMethod getReturnTypeShippingMethod(ReturnType returnType, ShippingMethod shippingMethod) {
        return getReturnTypeShippingMethod(returnType, shippingMethod, EntityPermission.READ_ONLY);
    }
    
    public ReturnTypeShippingMethod getReturnTypeShippingMethodForUpdate(ReturnType returnType, ShippingMethod shippingMethod) {
        return getReturnTypeShippingMethod(returnType, shippingMethod, EntityPermission.READ_WRITE);
    }
    
    public ReturnTypeShippingMethodValue getReturnTypeShippingMethodValueForUpdate(ReturnType returnType, ShippingMethod shippingMethod) {
        ReturnTypeShippingMethod returnTypeShippingMethod = getReturnTypeShippingMethodForUpdate(returnType, shippingMethod);
        
        return returnTypeShippingMethod == null? null: returnTypeShippingMethod.getReturnTypeShippingMethodValue().clone();
    }
    
    private ReturnTypeShippingMethod getDefaultReturnTypeShippingMethod(ReturnType returnType, EntityPermission entityPermission) {
        ReturnTypeShippingMethod returnTypeShippingMethod;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM returntypeshippingmethods " +
                        "WHERE rtntypshm_rtntyp_returntypeid = ? AND rtntypshm_isdefault = 1 AND rtntypshm_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM returntypeshippingmethods " +
                        "WHERE rtntypshm_rtntyp_returntypeid = ? AND rtntypshm_isdefault = 1 AND rtntypshm_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ReturnTypeShippingMethodFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, returnType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            returnTypeShippingMethod = ReturnTypeShippingMethodFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return returnTypeShippingMethod;
    }
    
    public ReturnTypeShippingMethod getDefaultReturnTypeShippingMethod(ReturnType returnType) {
        return getDefaultReturnTypeShippingMethod(returnType, EntityPermission.READ_ONLY);
    }
    
    public ReturnTypeShippingMethod getDefaultReturnTypeShippingMethodForUpdate(ReturnType returnType) {
        return getDefaultReturnTypeShippingMethod(returnType, EntityPermission.READ_WRITE);
    }
    
    public ReturnTypeShippingMethodValue getDefaultReturnTypeShippingMethodValueForUpdate(ReturnType returnType) {
        ReturnTypeShippingMethod returnTypeShippingMethod = getDefaultReturnTypeShippingMethodForUpdate(returnType);
        
        return returnTypeShippingMethod == null? null: returnTypeShippingMethod.getReturnTypeShippingMethodValue().clone();
    }
    
    private List<ReturnTypeShippingMethod> getReturnTypeShippingMethodsByReturnType(ReturnType returnType, EntityPermission entityPermission) {
        List<ReturnTypeShippingMethod> returnTypeShippingMethods;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM returntypeshippingmethods, shippingmethods, shippingmethoddetails " +
                        "WHERE rtntypshm_rtntyp_returntypeid = ? AND rtntypshm_thrutime = ? " +
                        "AND rtntypshm_shm_shippingmethodid = shm_shippingmethodid AND shm_lastdetailid = shmdt_shippingmethoddetailid " +
                        "ORDER BY shmdt_sortorder, shmdt_shippingmethodname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM returntypeshippingmethods " +
                        "WHERE rtntypshm_rtntyp_returntypeid = ? AND rtntypshm_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ReturnTypeShippingMethodFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, returnType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            returnTypeShippingMethods = ReturnTypeShippingMethodFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return returnTypeShippingMethods;
    }
    
    public List<ReturnTypeShippingMethod> getReturnTypeShippingMethodsByReturnType(ReturnType returnType) {
        return getReturnTypeShippingMethodsByReturnType(returnType, EntityPermission.READ_ONLY);
    }
    
    public List<ReturnTypeShippingMethod> getReturnTypeShippingMethodsByReturnTypeForUpdate(ReturnType returnType) {
        return getReturnTypeShippingMethodsByReturnType(returnType, EntityPermission.READ_WRITE);
    }
    
    private List<ReturnTypeShippingMethod> getReturnTypeShippingMethodsByShippingMethod(ShippingMethod shippingMethod, EntityPermission entityPermission) {
        List<ReturnTypeShippingMethod> returnTypeShippingMethods;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM returntypeshippingmethods, returntypes, returntypedetails, returnkinds, returnkinddetails " +
                        "WHERE rtntypshm_shm_shippingmethodid = ? AND rtntypshm_thrutime = ? " +
                        "AND rtntypshm_rtntyp_returntypeid = rtntyp_returntypeid AND rtntyp_lastdetailid = rtntypdt_returntypedetailid " +
                        "AND rtntypdt_rtnk_returnkindid = rtnk_returnkindid AND rtnk_lastdetailid = rtnkdt_returnkinddetailid " +
                        "ORDER BY rtntypdt_sortorder, rtntypdt_returntypename, rtnkdt_sortorder, rtnkdt_returnkindname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM returntypeshippingmethods " +
                        "WHERE rtntypshm_shm_shippingmethodid = ? AND rtntypshm_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ReturnTypeShippingMethodFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, shippingMethod.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            returnTypeShippingMethods = ReturnTypeShippingMethodFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return returnTypeShippingMethods;
    }
    
    public List<ReturnTypeShippingMethod> getReturnTypeShippingMethodsByShippingMethod(ShippingMethod shippingMethod) {
        return getReturnTypeShippingMethodsByShippingMethod(shippingMethod, EntityPermission.READ_ONLY);
    }
    
    public List<ReturnTypeShippingMethod> getReturnTypeShippingMethodsByShippingMethodForUpdate(ShippingMethod shippingMethod) {
        return getReturnTypeShippingMethodsByShippingMethod(shippingMethod, EntityPermission.READ_WRITE);
    }
    
    public List<ReturnTypeShippingMethodTransfer> getReturnTypeShippingMethodTransfers(UserVisit userVisit, Collection<ReturnTypeShippingMethod> returnTypeShippingMethods) {
        List<ReturnTypeShippingMethodTransfer> returnTypeShippingMethodTransfers = new ArrayList<>(returnTypeShippingMethods.size());
        ReturnTypeShippingMethodTransferCache returnTypeShippingMethodTransferCache = getReturnPolicyTransferCaches(userVisit).getReturnTypeShippingMethodTransferCache();
        
        returnTypeShippingMethods.forEach((returnTypeShippingMethod) ->
                returnTypeShippingMethodTransfers.add(returnTypeShippingMethodTransferCache.getReturnTypeShippingMethodTransfer(returnTypeShippingMethod))
        );
        
        return returnTypeShippingMethodTransfers;
    }
    
    public List<ReturnTypeShippingMethodTransfer> getReturnTypeShippingMethodTransfersByReturnType(UserVisit userVisit, ReturnType returnType) {
        return getReturnTypeShippingMethodTransfers(userVisit, getReturnTypeShippingMethodsByReturnType(returnType));
    }
    
    public List<ReturnTypeShippingMethodTransfer> getReturnTypeShippingMethodTransfersByShippingMethod(UserVisit userVisit, ShippingMethod shippingMethod) {
        return getReturnTypeShippingMethodTransfers(userVisit, getReturnTypeShippingMethodsByShippingMethod(shippingMethod));
    }
    
    public ReturnTypeShippingMethodTransfer getReturnTypeShippingMethodTransfer(UserVisit userVisit, ReturnTypeShippingMethod returnTypeShippingMethod) {
        return getReturnPolicyTransferCaches(userVisit).getReturnTypeShippingMethodTransferCache().getReturnTypeShippingMethodTransfer(returnTypeShippingMethod);
    }
    
    private void updateReturnTypeShippingMethodFromValue(ReturnTypeShippingMethodValue returnTypeShippingMethodValue, boolean checkDefault, BasePK updatedBy) {
        if(returnTypeShippingMethodValue.hasBeenModified()) {
            ReturnTypeShippingMethod returnTypeShippingMethod = ReturnTypeShippingMethodFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     returnTypeShippingMethodValue.getPrimaryKey());
            
            returnTypeShippingMethod.setThruTime(session.START_TIME_LONG);
            returnTypeShippingMethod.store();
            
            ReturnType returnType = returnTypeShippingMethod.getReturnType(); // Not Updated
            ReturnTypePK returnTypePK = returnType.getPrimaryKey(); // Not Updated
            ShippingMethodPK shippingMethodPK = returnTypeShippingMethod.getShippingMethodPK(); // Not Updated
            Boolean isDefault = returnTypeShippingMethodValue.getIsDefault();
            Integer sortOrder = returnTypeShippingMethodValue.getSortOrder();
            
            if(checkDefault) {
                ReturnTypeShippingMethod defaultReturnTypeShippingMethod = getDefaultReturnTypeShippingMethod(returnType);
                boolean defaultFound = defaultReturnTypeShippingMethod != null && !defaultReturnTypeShippingMethod.equals(returnTypeShippingMethod);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    ReturnTypeShippingMethodValue defaultReturnTypeShippingMethodValue = getDefaultReturnTypeShippingMethodValueForUpdate(returnType);
                    
                    defaultReturnTypeShippingMethodValue.setIsDefault(Boolean.FALSE);
                    updateReturnTypeShippingMethodFromValue(defaultReturnTypeShippingMethodValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            returnTypeShippingMethod = ReturnTypeShippingMethodFactory.getInstance().create(returnTypePK, shippingMethodPK,
                    isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(returnTypePK, EventTypes.MODIFY, returnTypeShippingMethod.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void updateReturnTypeShippingMethodFromValue(ReturnTypeShippingMethodValue returnTypeShippingMethodValue, BasePK updatedBy) {
        updateReturnTypeShippingMethodFromValue(returnTypeShippingMethodValue, true, updatedBy);
    }
    
    public void deleteReturnTypeShippingMethod(ReturnTypeShippingMethod returnTypeShippingMethod, BasePK deletedBy) {
        returnTypeShippingMethod.setThruTime(session.START_TIME_LONG);
        returnTypeShippingMethod.store();
        
        // Check for default, and pick one if necessary
        ReturnType returnType = returnTypeShippingMethod.getReturnType();
        ReturnTypeShippingMethod defaultReturnTypeShippingMethod = getDefaultReturnTypeShippingMethod(returnType);
        if(defaultReturnTypeShippingMethod == null) {
            List<ReturnTypeShippingMethod> returnTypeShippingMethods = getReturnTypeShippingMethodsByReturnTypeForUpdate(returnType);
            
            if(!returnTypeShippingMethods.isEmpty()) {
                Iterator<ReturnTypeShippingMethod> iter = returnTypeShippingMethods.iterator();
                if(iter.hasNext()) {
                    defaultReturnTypeShippingMethod = iter.next();
                }
                ReturnTypeShippingMethodValue returnTypeShippingMethodValue = defaultReturnTypeShippingMethod.getReturnTypeShippingMethodValue().clone();
                
                returnTypeShippingMethodValue.setIsDefault(Boolean.TRUE);
                updateReturnTypeShippingMethodFromValue(returnTypeShippingMethodValue, false, deletedBy);
            }
        }
        
        sendEvent(returnType.getPrimaryKey(), EventTypes.MODIFY, returnTypeShippingMethod.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteReturnTypeShippingMethods(List<ReturnTypeShippingMethod> returnTypeShippingMethods, BasePK deletedBy) {
        returnTypeShippingMethods.forEach((returnTypeShippingMethod) -> 
                deleteReturnTypeShippingMethod(returnTypeShippingMethod, deletedBy)
        );
    }
    
    public void deleteReturnTypeShippingMethodsByReturnType(ReturnType returnType, BasePK deletedBy) {
        deleteReturnTypeShippingMethods(getReturnTypeShippingMethodsByReturnTypeForUpdate(returnType), deletedBy);
    }
    
    public void deleteReturnTypeShippingMethodsByShippingMethod(ShippingMethod shippingMethod, BasePK deletedBy) {
        deleteReturnTypeShippingMethods(getReturnTypeShippingMethodsByShippingMethodForUpdate(shippingMethod), deletedBy);
    }
    
}
