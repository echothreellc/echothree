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

package com.echothree.model.control.returnpolicy.server.control;

import com.echothree.model.control.core.common.EventTypes;
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
import com.echothree.model.control.returnpolicy.server.transfer.ReturnKindDescriptionTransferCache;
import com.echothree.model.control.returnpolicy.server.transfer.ReturnKindTransferCache;
import com.echothree.model.control.returnpolicy.server.transfer.ReturnPolicyReasonTransferCache;
import com.echothree.model.control.returnpolicy.server.transfer.ReturnPolicyTransferCache;
import com.echothree.model.control.returnpolicy.server.transfer.ReturnPolicyTranslationTransferCache;
import com.echothree.model.control.returnpolicy.server.transfer.ReturnReasonDescriptionTransferCache;
import com.echothree.model.control.returnpolicy.server.transfer.ReturnReasonTransferCache;
import com.echothree.model.control.returnpolicy.server.transfer.ReturnReasonTypeTransferCache;
import com.echothree.model.control.returnpolicy.server.transfer.ReturnTypeDescriptionTransferCache;
import com.echothree.model.control.returnpolicy.server.transfer.ReturnTypeShippingMethodTransferCache;
import com.echothree.model.control.returnpolicy.server.transfer.ReturnTypeTransferCache;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.returnpolicy.common.pk.ReturnKindPK;
import com.echothree.model.data.returnpolicy.common.pk.ReturnPolicyPK;
import com.echothree.model.data.returnpolicy.common.pk.ReturnReasonPK;
import com.echothree.model.data.returnpolicy.common.pk.ReturnTypePK;
import com.echothree.model.data.returnpolicy.server.entity.PartyReturnPolicy;
import com.echothree.model.data.returnpolicy.server.entity.ReturnKind;
import com.echothree.model.data.returnpolicy.server.entity.ReturnKindDescription;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicyReason;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicyTranslation;
import com.echothree.model.data.returnpolicy.server.entity.ReturnReason;
import com.echothree.model.data.returnpolicy.server.entity.ReturnReasonDescription;
import com.echothree.model.data.returnpolicy.server.entity.ReturnReasonType;
import com.echothree.model.data.returnpolicy.server.entity.ReturnType;
import com.echothree.model.data.returnpolicy.server.entity.ReturnTypeDescription;
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
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.shipping.server.entity.ShippingMethod;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.party.PartyForeignKeys.PARTIES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.returnpolicy.ReturnPolicyForeignKeys.PARTY_RETURN_POLICIES_PARTY_FK;
import static com.echothree.model.jooq.server.keys.returnpolicy.ReturnPolicyForeignKeys.PARTY_RETURN_POLICIES_RETURN_POLICY_FK;
import static com.echothree.model.jooq.server.keys.returnpolicy.ReturnPolicyForeignKeys.RETURN_KINDS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.returnpolicy.ReturnPolicyForeignKeys.RETURN_KINDS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.returnpolicy.ReturnPolicyForeignKeys.RETURN_KIND_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.returnpolicy.ReturnPolicyForeignKeys.RETURN_POLICIES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.returnpolicy.ReturnPolicyForeignKeys.RETURN_POLICIES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.returnpolicy.ReturnPolicyForeignKeys.RETURN_POLICY_REASONS_RETURN_POLICY_FK;
import static com.echothree.model.jooq.server.keys.returnpolicy.ReturnPolicyForeignKeys.RETURN_POLICY_REASONS_RETURN_REASON_FK;
import static com.echothree.model.jooq.server.keys.returnpolicy.ReturnPolicyForeignKeys.RETURN_POLICY_TRANSLATIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.returnpolicy.ReturnPolicyForeignKeys.RETURN_REASONS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.returnpolicy.ReturnPolicyForeignKeys.RETURN_REASONS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.returnpolicy.ReturnPolicyForeignKeys.RETURN_REASON_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.returnpolicy.ReturnPolicyForeignKeys.RETURN_REASON_TYPES_RETURN_REASON_FK;
import static com.echothree.model.jooq.server.keys.returnpolicy.ReturnPolicyForeignKeys.RETURN_REASON_TYPES_RETURN_TYPE_FK;
import static com.echothree.model.jooq.server.keys.returnpolicy.ReturnPolicyForeignKeys.RETURN_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.returnpolicy.ReturnPolicyForeignKeys.RETURN_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.returnpolicy.ReturnPolicyForeignKeys.RETURN_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.returnpolicy.ReturnPolicyForeignKeys.RETURN_TYPE_DETAILS_RETURN_KIND_FK;
import static com.echothree.model.jooq.server.keys.returnpolicy.ReturnPolicyForeignKeys.RETURN_TYPE_SHIPPING_METHODS_RETURN_TYPE_FK;
import static com.echothree.model.jooq.server.keys.returnpolicy.ReturnPolicyForeignKeys.RETURN_TYPE_SHIPPING_METHODS_SHIPPING_METHOD_FK;
import static com.echothree.model.jooq.server.keys.shipping.ShippingForeignKeys.SHIPPING_METHODS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import static com.echothree.model.jooq.server.tables.party.Parties.Parties;
import static com.echothree.model.jooq.server.tables.party.PartyDetails.PartyDetails;
import static com.echothree.model.jooq.server.tables.returnpolicy.PartyReturnPolicies.PartyReturnPolicies;
import static com.echothree.model.jooq.server.tables.returnpolicy.ReturnKindDescriptions.ReturnKindDescriptions;
import static com.echothree.model.jooq.server.tables.returnpolicy.ReturnKindDetails.ReturnKindDetails;
import static com.echothree.model.jooq.server.tables.returnpolicy.ReturnKinds.ReturnKinds;
import static com.echothree.model.jooq.server.tables.returnpolicy.ReturnPolicies.ReturnPolicies;
import static com.echothree.model.jooq.server.tables.returnpolicy.ReturnPolicyDetails.ReturnPolicyDetails;
import static com.echothree.model.jooq.server.tables.returnpolicy.ReturnPolicyReasons.ReturnPolicyReasons;
import static com.echothree.model.jooq.server.tables.returnpolicy.ReturnPolicyTranslations.ReturnPolicyTranslations;
import static com.echothree.model.jooq.server.tables.returnpolicy.ReturnReasonDescriptions.ReturnReasonDescriptions;
import static com.echothree.model.jooq.server.tables.returnpolicy.ReturnReasonDetails.ReturnReasonDetails;
import static com.echothree.model.jooq.server.tables.returnpolicy.ReturnReasonTypes.ReturnReasonTypes;
import static com.echothree.model.jooq.server.tables.returnpolicy.ReturnReasons.ReturnReasons;
import static com.echothree.model.jooq.server.tables.returnpolicy.ReturnTypeDescriptions.ReturnTypeDescriptions;
import static com.echothree.model.jooq.server.tables.returnpolicy.ReturnTypeDetails.ReturnTypeDetails;
import static com.echothree.model.jooq.server.tables.returnpolicy.ReturnTypeShippingMethods.ReturnTypeShippingMethods;
import static com.echothree.model.jooq.server.tables.returnpolicy.ReturnTypes.ReturnTypes;
import static com.echothree.model.jooq.server.tables.shipping.ShippingMethodDetails.ShippingMethodDetails;
import static com.echothree.model.jooq.server.tables.shipping.ShippingMethods.ShippingMethods;
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
public class ReturnPolicyControl
        extends BaseModelControl {
    
    /** Creates a new instance of ReturnPolicyControl */

    protected ReturnPolicyControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Party Return Policies
    // --------------------------------------------------------------------------------

    @Inject
    private PartyReturnPolicyFactory partyReturnPolicyFactory;

    @Inject
    private PartyReturnPolicyTransferCache partyReturnPolicyTransferCache;

    public PartyReturnPolicy createPartyReturnPolicy(Party party, ReturnPolicy returnPolicy, BasePK createdBy) {
        var partyReturnPolicy = partyReturnPolicyFactory.create(party, returnPolicy, session.getStartTime(), Session.MAX_TIME);

        sendEvent(party.getPrimaryKey(), EventTypes.MODIFY, partyReturnPolicy.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return partyReturnPolicy;
    }

    public long countPartyReturnPoliciesByParty(Party party) {
        return session.getDslContext()
                .selectCount()
                .from(PartyReturnPolicies)
                .where(PartyReturnPolicies.PARTY.eq(party.getPrimaryKey()),
                        PartyReturnPolicies.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPartyReturnPoliciesByReturnPolicy(ReturnPolicy returnPolicy) {
        return session.getDslContext()
                .selectCount()
                .from(PartyReturnPolicies)
                .where(PartyReturnPolicies.RETURN_POLICY.eq(returnPolicy.getPrimaryKey()),
                        PartyReturnPolicies.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPartyReturnPoliciesByPartyAndReturnPolicy(Party party, ReturnPolicy returnPolicy) {
        return session.getDslContext()
                .selectCount()
                .from(PartyReturnPolicies)
                .where(PartyReturnPolicies.PARTY.eq(party.getPrimaryKey()),
                        PartyReturnPolicies.RETURN_POLICY.eq(returnPolicy.getPrimaryKey()),
                        PartyReturnPolicies.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private PartyReturnPolicy getPartyReturnPolicy(Party party, ReturnPolicy returnPolicy, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyReturnPolicies.fields())
                .from(PartyReturnPolicies)
                .where(PartyReturnPolicies.PARTY.eq(party.getPrimaryKey()),
                        PartyReturnPolicies.RETURN_POLICY.eq(returnPolicy.getPrimaryKey()),
                        PartyReturnPolicies.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyReturnPolicyFactory.getEntityFromQuery(entityPermission, query);
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

    private List<PartyReturnPolicy> getPartyReturnPoliciesByReturnPolicy(ReturnPolicy returnPolicy, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyReturnPolicies.fields())
                    .from(PartyReturnPolicies)
                    .join(Parties).onKey(PARTY_RETURN_POLICIES_PARTY_FK)
                    .join(PartyDetails).onKey(PARTIES_LAST_DETAIL_FK)
                    .where(PartyReturnPolicies.RETURN_POLICY.eq(returnPolicy.getPrimaryKey()),
                            PartyReturnPolicies.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(PartyDetails.PARTY_NAME),
                    PartyReturnPolicyFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyReturnPolicies.fields())
                    .from(PartyReturnPolicies)
                    .where(PartyReturnPolicies.RETURN_POLICY.eq(returnPolicy.getPrimaryKey()),
                            PartyReturnPolicies.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return partyReturnPolicyFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PartyReturnPolicy> getPartyReturnPoliciesByReturnPolicy(ReturnPolicy returnPolicy) {
        return getPartyReturnPoliciesByReturnPolicy(returnPolicy, EntityPermission.READ_ONLY);
    }

    public List<PartyReturnPolicy> getPartyReturnPoliciesByReturnPolicyForUpdate(ReturnPolicy returnPolicy) {
        return getPartyReturnPoliciesByReturnPolicy(returnPolicy, EntityPermission.READ_WRITE);
    }

    private List<PartyReturnPolicy> getPartyReturnPoliciesByParty(Party party, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyReturnPolicies.fields())
                    .from(PartyReturnPolicies)
                    .join(ReturnPolicies).onKey(PARTY_RETURN_POLICIES_RETURN_POLICY_FK)
                    .join(ReturnPolicyDetails).onKey(RETURN_POLICIES_LAST_DETAIL_FK)
                    .where(PartyReturnPolicies.PARTY.eq(party.getPrimaryKey()),
                            PartyReturnPolicies.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ReturnPolicyDetails.SORT_ORDER, ReturnPolicyDetails.RETURN_POLICY_NAME),
                    PartyReturnPolicyFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyReturnPolicies.fields())
                    .from(PartyReturnPolicies)
                    .where(PartyReturnPolicies.PARTY.eq(party.getPrimaryKey()),
                            PartyReturnPolicies.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return partyReturnPolicyFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PartyReturnPolicy> getPartyReturnPoliciesByParty(Party party) {
        return getPartyReturnPoliciesByParty(party, EntityPermission.READ_ONLY);
    }

    public List<PartyReturnPolicy> getPartyReturnPoliciesByPartyForUpdate(Party party) {
        return getPartyReturnPoliciesByParty(party, EntityPermission.READ_WRITE);
    }

    public PartyReturnPolicyTransfer getPartyReturnPolicyTransfer(UserVisit userVisit, PartyReturnPolicy partyReturnPolicy) {
        return partyReturnPolicyTransferCache.getPartyReturnPolicyTransfer(userVisit, partyReturnPolicy);
    }

    public List<PartyReturnPolicyTransfer> getPartyReturnPolicyTransfers(UserVisit userVisit, Collection<PartyReturnPolicy> returnPolicies) {
        List<PartyReturnPolicyTransfer> returnPolicyTransfers = new ArrayList<>(returnPolicies.size());

        returnPolicies.forEach((returnPolicy) ->
                returnPolicyTransfers.add(partyReturnPolicyTransferCache.getPartyReturnPolicyTransfer(userVisit, returnPolicy))
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

        partyReturnPolicy.setThruTime(session.getStartTime());

        // Performed manually, since sendEvent doesn't call it for relatedEntityInstances.
        entityInstanceControl.deleteEntityInstanceDependencies(entityInstanceControl.getEntityInstanceByBasePK(partyReturnPolicy.getPrimaryKey()), deletedBy);

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

    @Inject
    protected ReturnKindDetailFactory returnKindDetailFactory;

    @Inject
    private ReturnKindFactory returnKindFactory;

    @Inject
    private ReturnKindTransferCache returnKindTransferCache;

    public ReturnKind createReturnKind(String returnKindName, SequenceType returnSequenceType, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var defaultReturnKind = getDefaultReturnKind();
        var defaultFound = defaultReturnKind != null;
        
        if(defaultFound && isDefault) {
            var defaultReturnKindDetailValue = getDefaultReturnKindDetailValueForUpdate();
            
            defaultReturnKindDetailValue.setIsDefault(false);
            updateReturnKindFromValue(defaultReturnKindDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var returnKind = returnKindFactory.create();
        var returnKindDetail = returnKindDetailFactory.create(returnKind, returnKindName,
                returnSequenceType, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        returnKind = returnKindFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                returnKind.getPrimaryKey());
        returnKind.setActiveDetail(returnKindDetail);
        returnKind.setLastDetail(returnKindDetail);
        returnKind.store();
        
        sendEvent(returnKind.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return returnKind;
    }

    public long countReturnKinds() {
        return session.getDslContext()
                .selectCount()
                .from(ReturnKinds)
                .join(ReturnKindDetails).onKey(RETURN_KINDS_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ReturnKind */
    public ReturnKind getReturnKindByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ReturnKindPK(entityInstance.getEntityUniqueId());

        return returnKindFactory.getEntityFromPK(entityPermission, pk);
    }

    public ReturnKind getReturnKindByEntityInstance(EntityInstance entityInstance) {
        return getReturnKindByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ReturnKind getReturnKindByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getReturnKindByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public ReturnKind getReturnKindByName(String returnKindName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ReturnKinds.fields())
                .from(ReturnKinds)
                .join(ReturnKindDetails).onKey(RETURN_KINDS_ACTIVE_DETAIL_FK)
                .where(ReturnKindDetails.RETURN_KIND_NAME.eq(returnKindName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return returnKindFactory.getEntityFromQuery(entityPermission, query);
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
    
    public ReturnKind getDefaultReturnKind(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ReturnKinds.fields())
                .from(ReturnKinds)
                .join(ReturnKindDetails).onKey(RETURN_KINDS_ACTIVE_DETAIL_FK)
                .where(ReturnKindDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return returnKindFactory.getEntityFromQuery(entityPermission, query);
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
    
    private List<ReturnKind> getReturnKinds(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ReturnKinds.fields())
                .from(ReturnKinds)
                .join(ReturnKindDetails).onKey(RETURN_KINDS_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ReturnKindDetails.SORT_ORDER, ReturnKindDetails.RETURN_KIND_NAME),
                    ReturnKindFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return returnKindFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ReturnKind> getReturnKinds() {
        return getReturnKinds(EntityPermission.READ_ONLY);
    }
    
    public List<ReturnKind> getReturnKindsForUpdate() {
        return getReturnKinds(EntityPermission.READ_WRITE);
    }
    
    public ReturnKindChoicesBean getReturnKindChoices(String defaultReturnKindChoice, Language language, boolean allowNullChoice) {
        var returnKinds = getReturnKinds();
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
            var returnKindDetail = returnKind.getLastDetail();
            
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
        return returnKindTransferCache.getReturnKindTransfer(userVisit, returnKind);
    }

    public List<ReturnKindTransfer> getReturnKindTransfers(UserVisit userVisit, Collection<ReturnKind> returnKinds) {
        var returnKindTransfers = new ArrayList<ReturnKindTransfer>(returnKinds.size());

        returnKinds.forEach((returnKind) ->
                returnKindTransfers.add(returnKindTransferCache.getReturnKindTransfer(userVisit, returnKind))
        );

        return returnKindTransfers;
    }

    public List<ReturnKindTransfer> getReturnKindTransfers(UserVisit userVisit) {
        return getReturnKindTransfers(userVisit, getReturnKinds());
    }

    private void updateReturnKindFromValue(ReturnKindDetailValue returnKindDetailValue, boolean checkDefault, BasePK updatedBy) {
        var returnKind = returnKindFactory.getEntityFromPK(
                EntityPermission.READ_WRITE, returnKindDetailValue.getReturnKindPK());
        var returnKindDetail = returnKind.getActiveDetailForUpdate();
        
        returnKindDetail.setThruTime(session.getStartTime());
        returnKindDetail.store();

        var returnKindPK = returnKindDetail.getReturnKindPK();
        var returnKindName = returnKindDetailValue.getReturnKindName();
        var returnSequenceTypePK = returnKindDetailValue.getReturnSequenceTypePK();
        var isDefault = returnKindDetailValue.getIsDefault();
        var sortOrder = returnKindDetailValue.getSortOrder();
        
        if(checkDefault) {
            var defaultReturnKind = getDefaultReturnKind();
            var defaultFound = defaultReturnKind != null && !defaultReturnKind.equals(returnKind);
            
            if(isDefault && defaultFound) {
                // If I'm the default, and a default already existed...
                var defaultReturnKindDetailValue = getDefaultReturnKindDetailValueForUpdate();
                
                defaultReturnKindDetailValue.setIsDefault(false);
                updateReturnKindFromValue(defaultReturnKindDetailValue, false, updatedBy);
            } else if(!isDefault && !defaultFound) {
                // If I'm not the default, and no other default exists...
                isDefault = true;
            }
        }
        
        returnKindDetail = returnKindDetailFactory.create(returnKindPK, returnKindName, returnSequenceTypePK,
                isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
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

        var returnKindDetail = returnKind.getLastDetailForUpdate();
        returnKindDetail.setThruTime(session.getStartTime());
        returnKind.setActiveDetail(null);
        returnKind.store();
        
        // Check for default, and pick one if necessary
        var defaultReturnKind = getDefaultReturnKind();
        if(defaultReturnKind == null) {
            var returnKinds = getReturnKindsForUpdate();
            
            if(!returnKinds.isEmpty()) {
                var iter = returnKinds.iterator();
                if(iter.hasNext()) {
                    defaultReturnKind = iter.next();
                }
                var returnKindDetailValue = Objects.requireNonNull(defaultReturnKind).getLastDetailForUpdate().getReturnKindDetailValue().clone();
                
                returnKindDetailValue.setIsDefault(true);
                updateReturnKindFromValue(returnKindDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(returnKind.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Return Kind Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    private ReturnKindDescriptionFactory returnKindDescriptionFactory;

    @Inject
    private ReturnKindDescriptionTransferCache returnKindDescriptionTransferCache;

    public ReturnKindDescription createReturnKindDescription(ReturnKind returnKind, Language language, String description,
            BasePK createdBy) {
        var returnKindDescription = returnKindDescriptionFactory.create(returnKind,
                language, description, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(returnKind.getPrimaryKey(), EventTypes.MODIFY, returnKindDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return returnKindDescription;
    }
    
    private ReturnKindDescription getReturnKindDescription(ReturnKind returnKind, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ReturnKindDescriptions.fields())
                .from(ReturnKindDescriptions)
                .where(ReturnKindDescriptions.RETURN_KIND.eq(returnKind.getPrimaryKey()),
                        ReturnKindDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        ReturnKindDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return returnKindDescriptionFactory.getEntityFromQuery(entityPermission, query);
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
    
    private List<ReturnKindDescription> getReturnKindDescriptionsByReturnKind(ReturnKind returnKind, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ReturnKindDescriptions.fields())
                    .from(ReturnKindDescriptions)
                    .join(Languages).onKey(RETURN_KIND_DESCRIPTIONS_LANGUAGE_FK)
                    .where(ReturnKindDescriptions.RETURN_KIND.eq(returnKind.getPrimaryKey()),
                            ReturnKindDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    ReturnKindDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ReturnKindDescriptions.fields())
                    .from(ReturnKindDescriptions)
                    .where(ReturnKindDescriptions.RETURN_KIND.eq(returnKind.getPrimaryKey()),
                            ReturnKindDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return returnKindDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ReturnKindDescription> getReturnKindDescriptionsByReturnKind(ReturnKind returnKind) {
        return getReturnKindDescriptionsByReturnKind(returnKind, EntityPermission.READ_ONLY);
    }
    
    public List<ReturnKindDescription> getReturnKindDescriptionsByReturnKindForUpdate(ReturnKind returnKind) {
        return getReturnKindDescriptionsByReturnKind(returnKind, EntityPermission.READ_WRITE);
    }
    
    public String getBestReturnKindDescription(ReturnKind returnKind, Language language) {
        String description;
        var returnKindDescription = getReturnKindDescription(returnKind, language);
        
        if(returnKindDescription == null && !language.getIsDefault()) {
            returnKindDescription = getReturnKindDescription(returnKind, partyControl.getDefaultLanguage());
        }
        
        if(returnKindDescription == null) {
            description = returnKind.getLastDetail().getReturnKindName();
        } else {
            description = returnKindDescription.getDescription();
        }
        
        return description;
    }
    
    public ReturnKindDescriptionTransfer getReturnKindDescriptionTransfer(UserVisit userVisit, ReturnKindDescription returnKindDescription) {
        return returnKindDescriptionTransferCache.getReturnKindDescriptionTransfer(userVisit, returnKindDescription);
    }
    
    public List<ReturnKindDescriptionTransfer> getReturnKindDescriptionTransfersByReturnKind(UserVisit userVisit, ReturnKind returnKind) {
        var returnKindDescriptions = getReturnKindDescriptionsByReturnKind(returnKind);
        List<ReturnKindDescriptionTransfer> returnKindDescriptionTransfers = new ArrayList<>(returnKindDescriptions.size());
        
        returnKindDescriptions.forEach((returnKindDescription) -> {
            returnKindDescriptionTransfers.add(returnKindDescriptionTransferCache.getReturnKindDescriptionTransfer(userVisit, returnKindDescription));
        });
        
        return returnKindDescriptionTransfers;
    }
    
    public void updateReturnKindDescriptionFromValue(ReturnKindDescriptionValue returnKindDescriptionValue, BasePK updatedBy) {
        if(returnKindDescriptionValue.hasBeenModified()) {
            var returnKindDescription = returnKindDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     returnKindDescriptionValue.getPrimaryKey());
            
            returnKindDescription.setThruTime(session.getStartTime());
            returnKindDescription.store();

            var returnKind = returnKindDescription.getReturnKind();
            var language = returnKindDescription.getLanguage();
            var description = returnKindDescriptionValue.getDescription();
            
            returnKindDescription = returnKindDescriptionFactory.create(returnKind, language, description,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(returnKind.getPrimaryKey(), EventTypes.MODIFY, returnKindDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteReturnKindDescription(ReturnKindDescription returnKindDescription, BasePK deletedBy) {
        returnKindDescription.setThruTime(session.getStartTime());
        
        sendEvent(returnKindDescription.getReturnKindPK(), EventTypes.MODIFY, returnKindDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteReturnKindDescriptionsByReturnKind(ReturnKind returnKind, BasePK deletedBy) {
        var returnKindDescriptions = getReturnKindDescriptionsByReturnKindForUpdate(returnKind);
        
        returnKindDescriptions.forEach((returnKindDescription) -> 
                deleteReturnKindDescription(returnKindDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Return Policies
    // --------------------------------------------------------------------------------

    @Inject
    protected ReturnPolicyDetailFactory returnPolicyDetailFactory;

    @Inject
    private ReturnPolicyFactory returnPolicyFactory;

    @Inject
    private ReturnPolicyTransferCache returnPolicyTransferCache;

    public ReturnPolicy createReturnPolicy(ReturnKind returnKind, String returnPolicyName, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var defaultReturnPolicy = getDefaultReturnPolicy(returnKind);
        var defaultFound = defaultReturnPolicy != null;
        
        if(defaultFound && isDefault) {
            var defaultReturnPolicyDetailValue = getDefaultReturnPolicyDetailValueForUpdate(returnKind);
            
            defaultReturnPolicyDetailValue.setIsDefault(false);
            updateReturnPolicyFromValue(defaultReturnPolicyDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var returnPolicy = returnPolicyFactory.create();
        var returnPolicyDetail = returnPolicyDetailFactory.create(
                returnPolicy, returnKind, returnPolicyName, isDefault, sortOrder, session.getStartTime(),
                Session.MAX_TIME);
        
        // Convert to R/W
        returnPolicy = returnPolicyFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                returnPolicy.getPrimaryKey());
        returnPolicy.setActiveDetail(returnPolicyDetail);
        returnPolicy.setLastDetail(returnPolicyDetail);
        returnPolicy.store();
        
        sendEvent(returnPolicy.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return returnPolicy;
    }

    public long countReturnPoliciesByReturnKind(ReturnKind returnKind) {
        return session.getDslContext()
                .selectCount()
                .from(ReturnPolicies)
                .join(ReturnPolicyDetails).onKey(RETURN_POLICIES_ACTIVE_DETAIL_FK)
                .where(ReturnPolicyDetails.RETURN_KIND.eq(returnKind.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ReturnPolicy */
    public ReturnPolicy getReturnPolicyByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ReturnPolicyPK(entityInstance.getEntityUniqueId());

        return returnPolicyFactory.getEntityFromPK(entityPermission, pk);
    }

    public ReturnPolicy getReturnPolicyByEntityInstance(EntityInstance entityInstance) {
        return getReturnPolicyByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ReturnPolicy getReturnPolicyByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getReturnPolicyByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    private List<ReturnPolicy> getReturnPolicies(ReturnKind returnKind, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ReturnPolicies.fields())
                .from(ReturnPolicies)
                .join(ReturnPolicyDetails).onKey(RETURN_POLICIES_ACTIVE_DETAIL_FK)
                .where(ReturnPolicyDetails.RETURN_KIND.eq(returnKind.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ReturnPolicyDetails.SORT_ORDER, ReturnPolicyDetails.RETURN_POLICY_NAME),
                    ReturnPolicyFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return returnPolicyFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ReturnPolicy> getReturnPolicies(ReturnKind returnKind) {
        return getReturnPolicies(returnKind, EntityPermission.READ_ONLY);
    }
    
    public List<ReturnPolicy> getReturnPoliciesForUpdate(ReturnKind returnKind) {
        return getReturnPolicies(returnKind, EntityPermission.READ_WRITE);
    }
    
    public ReturnPolicy getDefaultReturnPolicy(ReturnKind returnKind, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ReturnPolicies.fields())
                .from(ReturnPolicies)
                .join(ReturnPolicyDetails).onKey(RETURN_POLICIES_ACTIVE_DETAIL_FK)
                .where(ReturnPolicyDetails.RETURN_KIND.eq(returnKind.getPrimaryKey()),
                        ReturnPolicyDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return returnPolicyFactory.getEntityFromQuery(entityPermission, query);
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
        var baseQuery = session.getDslContext()
                .select(ReturnPolicies.fields())
                .from(ReturnPolicies)
                .join(ReturnPolicyDetails).onKey(RETURN_POLICIES_ACTIVE_DETAIL_FK)
                .where(ReturnPolicyDetails.RETURN_KIND.eq(returnKind.getPrimaryKey()),
                        ReturnPolicyDetails.RETURN_POLICY_NAME.eq(returnPolicyName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return returnPolicyFactory.getEntityFromQuery(entityPermission, query);
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
        var returnPolicies = getReturnPolicies(returnKind);
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
            var returnPolicyDetail = returnPolicy.getLastDetail();
            var returnPolicyName = returnPolicyDetail.getReturnPolicyName();
            var returnPolicyTranslation = getBestReturnPolicyTranslation(returnPolicy, language);

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
        return returnPolicyTransferCache.getReturnPolicyTransfer(userVisit, returnPolicy);
    }

    public List<ReturnPolicyTransfer> getReturnPolicyTransfers(UserVisit userVisit, Collection<ReturnPolicy> returnPolicies ) {
        List<ReturnPolicyTransfer> returnPolicyTransfers = new ArrayList<>(returnPolicies.size());

        returnPolicies.forEach((returnPolicy) ->
                returnPolicyTransfers.add(returnPolicyTransferCache.getReturnPolicyTransfer(userVisit, returnPolicy))
        );

        return returnPolicyTransfers;
    }

    public List<ReturnPolicyTransfer> getReturnPolicyTransfersByReturnKind(UserVisit userVisit, ReturnKind returnKind) {
        return getReturnPolicyTransfers(userVisit, getReturnPolicies(returnKind));
    }

    private void updateReturnPolicyFromValue(ReturnPolicyDetailValue returnPolicyDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(returnPolicyDetailValue.hasBeenModified()) {
            var returnPolicy = returnPolicyFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     returnPolicyDetailValue.getReturnPolicyPK());
            var returnPolicyDetail = returnPolicy.getActiveDetailForUpdate();
            
            returnPolicyDetail.setThruTime(session.getStartTime());
            returnPolicyDetail.store();

            var returnPolicyPK = returnPolicyDetail.getReturnPolicyPK();
            var returnKind = returnPolicyDetail.getReturnKind();
            var returnKindPK = returnKind.getPrimaryKey();
            var returnPolicyName = returnPolicyDetailValue.getReturnPolicyName();
            var isDefault = returnPolicyDetailValue.getIsDefault();
            var sortOrder = returnPolicyDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultReturnPolicy = getDefaultReturnPolicy(returnKind);
                var defaultFound = defaultReturnPolicy != null && !defaultReturnPolicy.equals(returnPolicy);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultReturnPolicyDetailValue = getDefaultReturnPolicyDetailValueForUpdate(returnKind);
                    
                    defaultReturnPolicyDetailValue.setIsDefault(false);
                    updateReturnPolicyFromValue(defaultReturnPolicyDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            returnPolicyDetail = returnPolicyDetailFactory.create(returnPolicyPK,
                    returnKindPK, returnPolicyName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
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

        var returnPolicyDetail = returnPolicy.getLastDetailForUpdate();
        returnPolicyDetail.setThruTime(session.getStartTime());
        returnPolicy.setActiveDetail(null);
        returnPolicy.store();
        
        // Check for default, and pick one if necessary
        var returnKind = returnPolicyDetail.getReturnKind();
        var defaultReturnPolicy = getDefaultReturnPolicy(returnKind);
        if(defaultReturnPolicy == null) {
            var returnPolicies = getReturnPoliciesForUpdate(returnKind);
            
            if(!returnPolicies.isEmpty()) {
                var iter = returnPolicies.iterator();
                if(iter.hasNext()) {
                    defaultReturnPolicy = iter.next();
                }
                var returnPolicyDetailValue = Objects.requireNonNull(defaultReturnPolicy).getLastDetailForUpdate().getReturnPolicyDetailValue().clone();
                
                returnPolicyDetailValue.setIsDefault(true);
                updateReturnPolicyFromValue(returnPolicyDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(returnPolicy.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteReturnPoliciesByReturnKind(ReturnKind returnKind, BasePK deletedBy) {
        var returnPolicies = getReturnPoliciesForUpdate(returnKind);
        
        returnPolicies.forEach((returnPolicy) -> 
                deleteReturnPolicy(returnPolicy, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Return Policy Translations
    // --------------------------------------------------------------------------------

    @Inject
    private ReturnPolicyTranslationFactory returnPolicyTranslationFactory;

    @Inject
    private ReturnPolicyTranslationTransferCache returnPolicyTranslationTransferCache;

    public ReturnPolicyTranslation createReturnPolicyTranslation(ReturnPolicy returnPolicy, Language language,
            String description, MimeType overviewMimeType, String overview, BasePK createdBy) {
        var returnPolicyTranslation = returnPolicyTranslationFactory.create(returnPolicy,
                language, description, overviewMimeType, overview, session.getStartTime(), Session.MAX_TIME);

        sendEvent(returnPolicy.getPrimaryKey(), EventTypes.MODIFY, returnPolicyTranslation.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return returnPolicyTranslation;
    }

    private ReturnPolicyTranslation getReturnPolicyTranslation(ReturnPolicy returnPolicy, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ReturnPolicyTranslations.fields())
                .from(ReturnPolicyTranslations)
                .where(ReturnPolicyTranslations.RETURN_POLICY.eq(returnPolicy.getPrimaryKey()),
                        ReturnPolicyTranslations.LANGUAGE.eq(language.getPrimaryKey()),
                        ReturnPolicyTranslations.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return returnPolicyTranslationFactory.getEntityFromQuery(entityPermission, query);
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

    private List<ReturnPolicyTranslation> getReturnPolicyTranslationsByReturnPolicy(ReturnPolicy returnPolicy, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ReturnPolicyTranslations.fields())
                    .from(ReturnPolicyTranslations)
                    .join(Languages).onKey(RETURN_POLICY_TRANSLATIONS_LANGUAGE_FK)
                    .where(ReturnPolicyTranslations.RETURN_POLICY.eq(returnPolicy.getPrimaryKey()),
                            ReturnPolicyTranslations.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    ReturnPolicyTranslationFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ReturnPolicyTranslations.fields())
                    .from(ReturnPolicyTranslations)
                    .where(ReturnPolicyTranslations.RETURN_POLICY.eq(returnPolicy.getPrimaryKey()),
                            ReturnPolicyTranslations.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return returnPolicyTranslationFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ReturnPolicyTranslation> getReturnPolicyTranslationsByReturnPolicy(ReturnPolicy returnPolicy) {
        return getReturnPolicyTranslationsByReturnPolicy(returnPolicy, EntityPermission.READ_ONLY);
    }

    public List<ReturnPolicyTranslation> getReturnPolicyTranslationsByReturnPolicyForUpdate(ReturnPolicy returnPolicy) {
        return getReturnPolicyTranslationsByReturnPolicy(returnPolicy, EntityPermission.READ_WRITE);
    }

    public ReturnPolicyTranslation getBestReturnPolicyTranslation(ReturnPolicy returnPolicy, Language language) {
        var returnPolicyTranslation = getReturnPolicyTranslation(returnPolicy, language);

        if(returnPolicyTranslation == null && !language.getIsDefault()) {
            returnPolicyTranslation = getReturnPolicyTranslation(returnPolicy, partyControl.getDefaultLanguage());
        }

        return returnPolicyTranslation;
    }

    public ReturnPolicyTranslationTransfer getReturnPolicyTranslationTransfer(UserVisit userVisit, ReturnPolicyTranslation returnPolicyTranslation) {
        return returnPolicyTranslationTransferCache.getReturnPolicyTranslationTransfer(userVisit, returnPolicyTranslation);
    }

    public List<ReturnPolicyTranslationTransfer> getReturnPolicyTranslationTransfers(UserVisit userVisit, ReturnPolicy returnPolicy) {
        var returnPolicyTranslations = getReturnPolicyTranslationsByReturnPolicy(returnPolicy);
        List<ReturnPolicyTranslationTransfer> returnPolicyTranslationTransfers = new ArrayList<>(returnPolicyTranslations.size());

        returnPolicyTranslations.forEach((returnPolicyTranslation) ->
                returnPolicyTranslationTransfers.add(returnPolicyTranslationTransferCache.getReturnPolicyTranslationTransfer(userVisit, returnPolicyTranslation))
        );

        return returnPolicyTranslationTransfers;
    }

    public void updateReturnPolicyTranslationFromValue(ReturnPolicyTranslationValue returnPolicyTranslationValue, BasePK updatedBy) {
        if(returnPolicyTranslationValue.hasBeenModified()) {
            var returnPolicyTranslation = returnPolicyTranslationFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    returnPolicyTranslationValue.getPrimaryKey());

            returnPolicyTranslation.setThruTime(session.getStartTime());
            returnPolicyTranslation.store();

            var returnPolicyPK = returnPolicyTranslation.getReturnPolicyPK();
            var languagePK = returnPolicyTranslation.getLanguagePK();
            var description = returnPolicyTranslationValue.getDescription();
            var policyMimeTypePK = returnPolicyTranslationValue.getPolicyMimeTypePK();
            var policy = returnPolicyTranslationValue.getPolicy();

            returnPolicyTranslation = returnPolicyTranslationFactory.create(returnPolicyPK, languagePK, description,
                    policyMimeTypePK, policy, session.getStartTime(), Session.MAX_TIME);

            sendEvent(returnPolicyPK, EventTypes.MODIFY, returnPolicyTranslation.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteReturnPolicyTranslation(ReturnPolicyTranslation returnPolicyTranslation, BasePK deletedBy) {
        returnPolicyTranslation.setThruTime(session.getStartTime());

        sendEvent(returnPolicyTranslation.getReturnPolicyPK(), EventTypes.MODIFY, returnPolicyTranslation.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteReturnPolicyTranslationsByReturnPolicy(ReturnPolicy returnPolicy, BasePK deletedBy) {
        var returnPolicyTranslations = getReturnPolicyTranslationsByReturnPolicyForUpdate(returnPolicy);

        returnPolicyTranslations.forEach((returnPolicyTranslation) -> 
                deleteReturnPolicyTranslation(returnPolicyTranslation, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Return Policy Reasons
    // --------------------------------------------------------------------------------

    @Inject
    private ReturnPolicyReasonFactory returnPolicyReasonFactory;

    @Inject
    private ReturnPolicyReasonTransferCache returnPolicyReasonTransferCache;

    public ReturnPolicyReason createReturnPolicyReason(ReturnPolicy returnPolicy, ReturnReason returnReason, Boolean isDefault,
            Integer sortOrder, BasePK createdBy) {
        var defaultReturnPolicyReason = getDefaultReturnPolicyReason(returnPolicy);
        var defaultFound = defaultReturnPolicyReason != null;
        
        if(defaultFound && isDefault) {
            var defaultReturnPolicyReasonValue = getDefaultReturnPolicyReasonValueForUpdate(returnPolicy);
            
            defaultReturnPolicyReasonValue.setIsDefault(false);
            updateReturnPolicyReasonFromValue(defaultReturnPolicyReasonValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var returnPolicyReason = returnPolicyReasonFactory.create(returnPolicy, returnReason,
                isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(returnPolicy.getPrimaryKey(), EventTypes.MODIFY, returnPolicyReason.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return returnPolicyReason;
    }

    public long countReturnPolicyReasonByReturnPolicy(final ReturnPolicy returnPolicy) {
        return session.getDslContext()
                .selectCount()
                .from(ReturnPolicyReasons)
                .where(ReturnPolicyReasons.RETURN_POLICY.eq(returnPolicy.getPrimaryKey()),
                        ReturnPolicyReasons.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countReturnPolicyReasonByReturnReason(final ReturnReason returnReason) {
        return session.getDslContext()
                .selectCount()
                .from(ReturnPolicyReasons)
                .where(ReturnPolicyReasons.RETURN_REASON.eq(returnReason.getPrimaryKey()),
                        ReturnPolicyReasons.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private ReturnPolicyReason getReturnPolicyReason(ReturnPolicy returnPolicy, ReturnReason returnReason, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ReturnPolicyReasons.fields())
                .from(ReturnPolicyReasons)
                .where(ReturnPolicyReasons.RETURN_POLICY.eq(returnPolicy.getPrimaryKey()),
                        ReturnPolicyReasons.RETURN_REASON.eq(returnReason.getPrimaryKey()),
                        ReturnPolicyReasons.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return returnPolicyReasonFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ReturnPolicyReason getReturnPolicyReason(ReturnPolicy returnPolicy, ReturnReason returnReason) {
        return getReturnPolicyReason(returnPolicy, returnReason, EntityPermission.READ_ONLY);
    }
    
    public ReturnPolicyReason getReturnPolicyReasonForUpdate(ReturnPolicy returnPolicy, ReturnReason returnReason) {
        return getReturnPolicyReason(returnPolicy, returnReason, EntityPermission.READ_WRITE);
    }
    
    public ReturnPolicyReasonValue getReturnPolicyReasonValueForUpdate(ReturnPolicy returnPolicy, ReturnReason returnReason) {
        var returnPolicyReason = getReturnPolicyReasonForUpdate(returnPolicy, returnReason);
        
        return returnPolicyReason == null? null: returnPolicyReason.getReturnPolicyReasonValue().clone();
    }
    
    private ReturnPolicyReason getDefaultReturnPolicyReason(ReturnPolicy returnPolicy, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ReturnPolicyReasons.fields())
                .from(ReturnPolicyReasons)
                .where(ReturnPolicyReasons.RETURN_POLICY.eq(returnPolicy.getPrimaryKey()),
                        ReturnPolicyReasons.IS_DEFAULT.eq(true),
                        ReturnPolicyReasons.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return returnPolicyReasonFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ReturnPolicyReason getDefaultReturnPolicyReason(ReturnPolicy returnPolicy) {
        return getDefaultReturnPolicyReason(returnPolicy, EntityPermission.READ_ONLY);
    }
    
    public ReturnPolicyReason getDefaultReturnPolicyReasonForUpdate(ReturnPolicy returnPolicy) {
        return getDefaultReturnPolicyReason(returnPolicy, EntityPermission.READ_WRITE);
    }
    
    public ReturnPolicyReasonValue getDefaultReturnPolicyReasonValueForUpdate(ReturnPolicy returnPolicy) {
        var returnPolicyReason = getDefaultReturnPolicyReasonForUpdate(returnPolicy);
        
        return returnPolicyReason == null? null: returnPolicyReason.getReturnPolicyReasonValue().clone();
    }
    
    private List<ReturnPolicyReason> getReturnPolicyReasonsByReturnPolicy(ReturnPolicy returnPolicy, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ReturnPolicyReasons.fields())
                    .from(ReturnPolicyReasons)
                    .join(ReturnReasons).onKey(RETURN_POLICY_REASONS_RETURN_REASON_FK)
                    .join(ReturnReasonDetails).onKey(RETURN_REASONS_LAST_DETAIL_FK)
                    .where(ReturnPolicyReasons.RETURN_POLICY.eq(returnPolicy.getPrimaryKey()),
                            ReturnPolicyReasons.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ReturnReasonDetails.SORT_ORDER, ReturnReasonDetails.RETURN_REASON_NAME),
                    ReturnPolicyReasonFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ReturnPolicyReasons.fields())
                    .from(ReturnPolicyReasons)
                    .where(ReturnPolicyReasons.RETURN_POLICY.eq(returnPolicy.getPrimaryKey()),
                            ReturnPolicyReasons.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return returnPolicyReasonFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ReturnPolicyReason> getReturnPolicyReasonsByReturnPolicy(ReturnPolicy returnPolicy) {
        return getReturnPolicyReasonsByReturnPolicy(returnPolicy, EntityPermission.READ_ONLY);
    }
    
    public List<ReturnPolicyReason> getReturnPolicyReasonsByReturnPolicyForUpdate(ReturnPolicy returnPolicy) {
        return getReturnPolicyReasonsByReturnPolicy(returnPolicy, EntityPermission.READ_WRITE);
    }
    
    private List<ReturnPolicyReason> getReturnPolicyReasonsByReturnReason(ReturnReason returnReason, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ReturnPolicyReasons.fields())
                    .from(ReturnPolicyReasons)
                    .join(ReturnPolicies).onKey(RETURN_POLICY_REASONS_RETURN_POLICY_FK)
                    .join(ReturnPolicyDetails).onKey(RETURN_POLICIES_LAST_DETAIL_FK)
                    .where(ReturnPolicyReasons.RETURN_REASON.eq(returnReason.getPrimaryKey()),
                            ReturnPolicyReasons.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ReturnPolicyDetails.SORT_ORDER, ReturnPolicyDetails.RETURN_POLICY_NAME),
                    ReturnPolicyReasonFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ReturnPolicyReasons.fields())
                    .from(ReturnPolicyReasons)
                    .where(ReturnPolicyReasons.RETURN_REASON.eq(returnReason.getPrimaryKey()),
                            ReturnPolicyReasons.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return returnPolicyReasonFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ReturnPolicyReason> getReturnPolicyReasonsByReturnReason(ReturnReason returnReason) {
        return getReturnPolicyReasonsByReturnReason(returnReason, EntityPermission.READ_ONLY);
    }
    
    public List<ReturnPolicyReason> getReturnPolicyReasonsByReturnReasonForUpdate(ReturnReason returnReason) {
        return getReturnPolicyReasonsByReturnReason(returnReason, EntityPermission.READ_WRITE);
    }
    
    public List<ReturnPolicyReasonTransfer> getReturnPolicyReasonTransfers(UserVisit userVisit, Collection<ReturnPolicyReason> returnPolicyReasons) {
        List<ReturnPolicyReasonTransfer> returnPolicyReasonTransfers = new ArrayList<>(returnPolicyReasons.size());
        
        returnPolicyReasons.forEach((returnPolicyReason) ->
                returnPolicyReasonTransfers.add(returnPolicyReasonTransferCache.getReturnPolicyReasonTransfer(userVisit, returnPolicyReason))
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
        return returnPolicyReasonTransferCache.getReturnPolicyReasonTransfer(userVisit, returnPolicyReason);
    }
    
    private void updateReturnPolicyReasonFromValue(ReturnPolicyReasonValue returnPolicyReasonValue, boolean checkDefault, BasePK updatedBy) {
        if(returnPolicyReasonValue.hasBeenModified()) {
            var returnPolicyReason = returnPolicyReasonFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     returnPolicyReasonValue.getPrimaryKey());
            
            returnPolicyReason.setThruTime(session.getStartTime());
            returnPolicyReason.store();

            var returnPolicy = returnPolicyReason.getReturnPolicy(); // Not Updated
            var returnPolicyPK = returnPolicy.getPrimaryKey(); // Not Updated
            var returnReasonPK = returnPolicyReason.getReturnReasonPK(); // Not Updated
            var isDefault = returnPolicyReasonValue.getIsDefault();
            var sortOrder = returnPolicyReasonValue.getSortOrder();
            
            if(checkDefault) {
                var defaultReturnPolicyReason = getDefaultReturnPolicyReason(returnPolicy);
                var defaultFound = defaultReturnPolicyReason != null && !defaultReturnPolicyReason.equals(returnPolicyReason);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultReturnPolicyReasonValue = getDefaultReturnPolicyReasonValueForUpdate(returnPolicy);
                    
                    defaultReturnPolicyReasonValue.setIsDefault(false);
                    updateReturnPolicyReasonFromValue(defaultReturnPolicyReasonValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            returnPolicyReason = returnPolicyReasonFactory.create(returnPolicyPK, returnReasonPK,
                    isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(returnPolicyPK, EventTypes.MODIFY, returnPolicyReason.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void updateReturnPolicyReasonFromValue(ReturnPolicyReasonValue returnPolicyReasonValue, BasePK updatedBy) {
        updateReturnPolicyReasonFromValue(returnPolicyReasonValue, true, updatedBy);
    }
    
    public void deleteReturnPolicyReason(ReturnPolicyReason returnPolicyReason, BasePK deletedBy) {
        returnPolicyReason.setThruTime(session.getStartTime());
        returnPolicyReason.store();
        
        // Check for default, and pick one if necessary
        var returnPolicy = returnPolicyReason.getReturnPolicy();
        var defaultReturnPolicyReason = getDefaultReturnPolicyReason(returnPolicy);
        if(defaultReturnPolicyReason == null) {
            var returnPolicyReasons = getReturnPolicyReasonsByReturnPolicyForUpdate(returnPolicy);
            
            if(!returnPolicyReasons.isEmpty()) {
                var iter = returnPolicyReasons.iterator();
                if(iter.hasNext()) {
                    defaultReturnPolicyReason = iter.next();
                }
                var returnPolicyReasonValue = defaultReturnPolicyReason.getReturnPolicyReasonValue().clone();
                
                returnPolicyReasonValue.setIsDefault(true);
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

    @Inject
    protected ReturnReasonDetailFactory returnReasonDetailFactory;

    @Inject
    private ReturnReasonFactory returnReasonFactory;

    @Inject
    private ReturnReasonTransferCache returnReasonTransferCache;

    public ReturnReason createReturnReason(ReturnKind returnKind, String returnReasonName, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var defaultReturnReason = getDefaultReturnReason(returnKind);
        var defaultFound = defaultReturnReason != null;
        
        if(defaultFound && isDefault) {
            var defaultReturnReasonDetailValue = getDefaultReturnReasonDetailValueForUpdate(returnKind);
            
            defaultReturnReasonDetailValue.setIsDefault(false);
            updateReturnReasonFromValue(defaultReturnReasonDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var returnReason = returnReasonFactory.create();
        var returnReasonDetail = returnReasonDetailFactory.create(
                returnReason, returnKind, returnReasonName, isDefault, sortOrder, session.getStartTime(),
                Session.MAX_TIME);
        
        // Convert to R/W
        returnReason = returnReasonFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                returnReason.getPrimaryKey());
        returnReason.setActiveDetail(returnReasonDetail);
        returnReason.setLastDetail(returnReasonDetail);
        returnReason.store();
        
        sendEvent(returnReason.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return returnReason;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ReturnReason */
    public ReturnReason getReturnReasonByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ReturnReasonPK(entityInstance.getEntityUniqueId());

        return returnReasonFactory.getEntityFromPK(entityPermission, pk);
    }

    public ReturnReason getReturnReasonByEntityInstance(EntityInstance entityInstance) {
        return getReturnReasonByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ReturnReason getReturnReasonByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getReturnReasonByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countReturnReasonsByReturnKind(final ReturnKind returnKind) {
        return session.getDslContext()
                .selectCount()
                .from(ReturnReasons)
                .join(ReturnReasonDetails).onKey(RETURN_REASONS_ACTIVE_DETAIL_FK)
                .where(ReturnReasonDetails.RETURN_KIND.eq(returnKind.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<ReturnReason> getReturnReasons(ReturnKind returnKind, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ReturnReasons.fields())
                .from(ReturnReasons)
                .join(ReturnReasonDetails).onKey(RETURN_REASONS_ACTIVE_DETAIL_FK)
                .where(ReturnReasonDetails.RETURN_KIND.eq(returnKind.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ReturnReasonDetails.SORT_ORDER, ReturnReasonDetails.RETURN_REASON_NAME),
                    ReturnReasonFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return returnReasonFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ReturnReason> getReturnReasons(ReturnKind returnKind) {
        return getReturnReasons(returnKind, EntityPermission.READ_ONLY);
    }
    
    public List<ReturnReason> getReturnReasonsForUpdate(ReturnKind returnKind) {
        return getReturnReasons(returnKind, EntityPermission.READ_WRITE);
    }
    
    private ReturnReason getDefaultReturnReason(ReturnKind returnKind, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ReturnReasons.fields())
                .from(ReturnReasons)
                .join(ReturnReasonDetails).onKey(RETURN_REASONS_ACTIVE_DETAIL_FK)
                .where(ReturnReasonDetails.RETURN_KIND.eq(returnKind.getPrimaryKey()),
                        ReturnReasonDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return returnReasonFactory.getEntityFromQuery(entityPermission, query);
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
        var baseQuery = session.getDslContext()
                .select(ReturnReasons.fields())
                .from(ReturnReasons)
                .join(ReturnReasonDetails).onKey(RETURN_REASONS_ACTIVE_DETAIL_FK)
                .where(ReturnReasonDetails.RETURN_KIND.eq(returnKind.getPrimaryKey()),
                        ReturnReasonDetails.RETURN_REASON_NAME.eq(returnReasonName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return returnReasonFactory.getEntityFromQuery(entityPermission, query);
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
        var returnReasons = getReturnReasons(returnKind);
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
            var returnReasonDetail = returnReason.getLastDetail();
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
        return returnReasonTransferCache.getReturnReasonTransfer(userVisit, returnReason);
    }

    public List<ReturnReasonTransfer> getReturnReasonTransfers(UserVisit userVisit, Collection<ReturnReason> returnReasons) {
        List<ReturnReasonTransfer> returnReasonTransfers = new ArrayList<>(returnReasons.size());

        returnReasons.forEach((returnReason) ->
                returnReasonTransfers.add(returnReasonTransferCache.getReturnReasonTransfer(userVisit, returnReason))
        );

        return returnReasonTransfers;
    }

    public List<ReturnReasonTransfer> getReturnReasonTransfersByReturnKind(UserVisit userVisit, ReturnKind returnKind) {
        return getReturnReasonTransfers(userVisit, getReturnReasons(returnKind));
    }
    
    private void updateReturnReasonFromValue(ReturnReasonDetailValue returnReasonDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(returnReasonDetailValue.hasBeenModified()) {
            var returnReason = returnReasonFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     returnReasonDetailValue.getReturnReasonPK());
            var returnReasonDetail = returnReason.getActiveDetailForUpdate();
            
            returnReasonDetail.setThruTime(session.getStartTime());
            returnReasonDetail.store();

            var returnReasonPK = returnReasonDetail.getReturnReasonPK();
            var returnKind = returnReasonDetail.getReturnKind();
            var returnKindPK = returnKind.getPrimaryKey();
            var returnReasonName = returnReasonDetailValue.getReturnReasonName();
            var isDefault = returnReasonDetailValue.getIsDefault();
            var sortOrder = returnReasonDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultReturnReason = getDefaultReturnReason(returnKind);
                var defaultFound = defaultReturnReason != null && !defaultReturnReason.equals(returnReason);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultReturnReasonDetailValue = getDefaultReturnReasonDetailValueForUpdate(returnKind);
                    
                    defaultReturnReasonDetailValue.setIsDefault(false);
                    updateReturnReasonFromValue(defaultReturnReasonDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            returnReasonDetail = returnReasonDetailFactory.create(returnReasonPK,
                    returnKindPK, returnReasonName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
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

        var returnReasonDetail = returnReason.getLastDetailForUpdate();
        returnReasonDetail.setThruTime(session.getStartTime());
        returnReason.setActiveDetail(null);
        returnReason.store();
        
        // Check for default, and pick one if necessary
        var returnKind = returnReasonDetail.getReturnKind();
        var defaultReturnReason = getDefaultReturnReason(returnKind);
        if(defaultReturnReason == null) {
            var returnReasons = getReturnReasonsForUpdate(returnKind);
            
            if(!returnReasons.isEmpty()) {
                var iter = returnReasons.iterator();
                if(iter.hasNext()) {
                    defaultReturnReason = iter.next();
                }
                var returnReasonDetailValue = Objects.requireNonNull(defaultReturnReason).getLastDetailForUpdate().getReturnReasonDetailValue().clone();
                
                returnReasonDetailValue.setIsDefault(true);
                updateReturnReasonFromValue(returnReasonDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(returnReason.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteReturnReasonsByReturnKind(ReturnKind returnKind, BasePK deletedBy) {
        var returnReasons = getReturnReasonsForUpdate(returnKind);
        
        returnReasons.forEach((returnReason) -> 
                deleteReturnReason(returnReason, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Return Reason Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    private ReturnReasonDescriptionFactory returnReasonDescriptionFactory;

    @Inject
    private ReturnReasonDescriptionTransferCache returnReasonDescriptionTransferCache;

    public ReturnReasonDescription createReturnReasonDescription(ReturnReason returnReason, Language language, String description,
            BasePK createdBy) {
        var returnReasonDescription = returnReasonDescriptionFactory.create(returnReason,
                language, description, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(returnReason.getPrimaryKey(), EventTypes.MODIFY, returnReasonDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return returnReasonDescription;
    }
    
    private ReturnReasonDescription getReturnReasonDescription(ReturnReason returnReason, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ReturnReasonDescriptions.fields())
                .from(ReturnReasonDescriptions)
                .where(ReturnReasonDescriptions.RETURN_REASON.eq(returnReason.getPrimaryKey()),
                        ReturnReasonDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        ReturnReasonDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return returnReasonDescriptionFactory.getEntityFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ReturnReasonDescriptions.fields())
                    .from(ReturnReasonDescriptions)
                    .join(Languages).onKey(RETURN_REASON_DESCRIPTIONS_LANGUAGE_FK)
                    .where(ReturnReasonDescriptions.RETURN_REASON.eq(returnReason.getPrimaryKey()),
                            ReturnReasonDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    ReturnReasonDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ReturnReasonDescriptions.fields())
                    .from(ReturnReasonDescriptions)
                    .where(ReturnReasonDescriptions.RETURN_REASON.eq(returnReason.getPrimaryKey()),
                            ReturnReasonDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return returnReasonDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ReturnReasonDescription> getReturnReasonDescriptionsByReturnReason(ReturnReason returnReason) {
        return getReturnReasonDescriptionsByReturnReason(returnReason, EntityPermission.READ_ONLY);
    }
    
    public List<ReturnReasonDescription> getReturnReasonDescriptionsByReturnReasonForUpdate(ReturnReason returnReason) {
        return getReturnReasonDescriptionsByReturnReason(returnReason, EntityPermission.READ_WRITE);
    }
    
    public String getBestReturnReasonDescription(ReturnReason returnReason, Language language) {
        String description;
        var returnReasonDescription = getReturnReasonDescription(returnReason, language);
        
        if(returnReasonDescription == null && !language.getIsDefault()) {
            returnReasonDescription = getReturnReasonDescription(returnReason, partyControl.getDefaultLanguage());
        }
        
        if(returnReasonDescription == null) {
            description = returnReason.getLastDetail().getReturnReasonName();
        } else {
            description = returnReasonDescription.getDescription();
        }
        
        return description;
    }
    
    public ReturnReasonDescriptionTransfer getReturnReasonDescriptionTransfer(UserVisit userVisit, ReturnReasonDescription returnReasonDescription) {
        return returnReasonDescriptionTransferCache.getReturnReasonDescriptionTransfer(userVisit, returnReasonDescription);
    }
    
    public List<ReturnReasonDescriptionTransfer> getReturnReasonDescriptionTransfersByReturnReason(UserVisit userVisit, ReturnReason returnReason) {
        var returnReasonDescriptions = getReturnReasonDescriptionsByReturnReason(returnReason);
        List<ReturnReasonDescriptionTransfer> returnReasonDescriptionTransfers = new ArrayList<>(returnReasonDescriptions.size());
        
        returnReasonDescriptions.forEach((returnReasonDescription) -> {
            returnReasonDescriptionTransfers.add(returnReasonDescriptionTransferCache.getReturnReasonDescriptionTransfer(userVisit, returnReasonDescription));
        });
        
        return returnReasonDescriptionTransfers;
    }
    
    public void updateReturnReasonDescriptionFromValue(ReturnReasonDescriptionValue returnReasonDescriptionValue, BasePK updatedBy) {
        if(returnReasonDescriptionValue.hasBeenModified()) {
            var returnReasonDescription = returnReasonDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     returnReasonDescriptionValue.getPrimaryKey());
            
            returnReasonDescription.setThruTime(session.getStartTime());
            returnReasonDescription.store();

            var returnReason = returnReasonDescription.getReturnReason();
            var language = returnReasonDescription.getLanguage();
            var description = returnReasonDescriptionValue.getDescription();
            
            returnReasonDescription = returnReasonDescriptionFactory.create(returnReason, language, description,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(returnReason.getPrimaryKey(), EventTypes.MODIFY, returnReasonDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteReturnReasonDescription(ReturnReasonDescription returnReasonDescription, BasePK deletedBy) {
        returnReasonDescription.setThruTime(session.getStartTime());
        
        sendEvent(returnReasonDescription.getReturnReasonPK(), EventTypes.MODIFY, returnReasonDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteReturnReasonDescriptionsByReturnReason(ReturnReason returnReason, BasePK deletedBy) {
        var returnReasonDescriptions = getReturnReasonDescriptionsByReturnReasonForUpdate(returnReason);
        
        returnReasonDescriptions.forEach((returnReasonDescription) -> 
                deleteReturnReasonDescription(returnReasonDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Return Reason Types
    // --------------------------------------------------------------------------------

    @Inject
    private ReturnReasonTypeFactory returnReasonTypeFactory;

    @Inject
    private ReturnReasonTypeTransferCache returnReasonTypeTransferCache;

    public ReturnReasonType createReturnReasonType(ReturnReason returnReason, ReturnType returnType, Boolean isDefault,
            Integer sortOrder, BasePK createdBy) {
        var defaultReturnReasonType = getDefaultReturnReasonType(returnReason);
        var defaultFound = defaultReturnReasonType != null;
        
        if(defaultFound && isDefault) {
            var defaultReturnReasonTypeValue = getDefaultReturnReasonTypeValueForUpdate(returnReason);
            
            defaultReturnReasonTypeValue.setIsDefault(false);
            updateReturnReasonTypeFromValue(defaultReturnReasonTypeValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var returnReasonType = returnReasonTypeFactory.create(returnReason, returnType,
                isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(returnReason.getPrimaryKey(), EventTypes.MODIFY, returnReasonType.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return returnReasonType;
    }

    public long countReturnReasonTypesByReturnReason(final ReturnReason returnReason) {
        return session.getDslContext()
                .selectCount()
                .from(ReturnReasonTypes)
                .where(ReturnReasonTypes.RETURN_REASON.eq(returnReason.getPrimaryKey()),
                        ReturnReasonTypes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countReturnReasonTypesByReturnType(final ReturnType returnType) {
        return session.getDslContext()
                .selectCount()
                .from(ReturnReasonTypes)
                .where(ReturnReasonTypes.RETURN_TYPE.eq(returnType.getPrimaryKey()),
                        ReturnReasonTypes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private ReturnReasonType getReturnReasonType(ReturnReason returnReason, ReturnType returnType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ReturnReasonTypes.fields())
                .from(ReturnReasonTypes)
                .where(ReturnReasonTypes.RETURN_REASON.eq(returnReason.getPrimaryKey()),
                        ReturnReasonTypes.RETURN_TYPE.eq(returnType.getPrimaryKey()),
                        ReturnReasonTypes.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return returnReasonTypeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ReturnReasonType getReturnReasonType(ReturnReason returnReason, ReturnType returnType) {
        return getReturnReasonType(returnReason, returnType, EntityPermission.READ_ONLY);
    }
    
    public ReturnReasonType getReturnReasonTypeForUpdate(ReturnReason returnReason, ReturnType returnType) {
        return getReturnReasonType(returnReason, returnType, EntityPermission.READ_WRITE);
    }
    
    public ReturnReasonTypeValue getReturnReasonTypeValueForUpdate(ReturnReason returnReason, ReturnType returnType) {
        var returnReasonType = getReturnReasonTypeForUpdate(returnReason, returnType);
        
        return returnReasonType == null? null: returnReasonType.getReturnReasonTypeValue().clone();
    }
    
    private ReturnReasonType getDefaultReturnReasonType(ReturnReason returnReason, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ReturnReasonTypes.fields())
                .from(ReturnReasonTypes)
                .where(ReturnReasonTypes.RETURN_REASON.eq(returnReason.getPrimaryKey()),
                        ReturnReasonTypes.IS_DEFAULT.eq(true),
                        ReturnReasonTypes.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return returnReasonTypeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ReturnReasonType getDefaultReturnReasonType(ReturnReason returnReason) {
        return getDefaultReturnReasonType(returnReason, EntityPermission.READ_ONLY);
    }
    
    public ReturnReasonType getDefaultReturnReasonTypeForUpdate(ReturnReason returnReason) {
        return getDefaultReturnReasonType(returnReason, EntityPermission.READ_WRITE);
    }
    
    public ReturnReasonTypeValue getDefaultReturnReasonTypeValueForUpdate(ReturnReason returnReason) {
        var returnReasonType = getDefaultReturnReasonTypeForUpdate(returnReason);
        
        return returnReasonType == null? null: returnReasonType.getReturnReasonTypeValue().clone();
    }
    
    private List<ReturnReasonType> getReturnReasonTypesByReturnReason(ReturnReason returnReason, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ReturnReasonTypes.fields())
                    .from(ReturnReasonTypes)
                    .join(ReturnTypes).onKey(RETURN_REASON_TYPES_RETURN_TYPE_FK)
                    .join(ReturnTypeDetails).onKey(RETURN_TYPES_LAST_DETAIL_FK)
                    .join(ReturnKinds).onKey(RETURN_TYPE_DETAILS_RETURN_KIND_FK)
                    .join(ReturnKindDetails).onKey(RETURN_KINDS_LAST_DETAIL_FK)
                    .where(ReturnReasonTypes.RETURN_REASON.eq(returnReason.getPrimaryKey()),
                            ReturnReasonTypes.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ReturnTypeDetails.SORT_ORDER, ReturnTypeDetails.RETURN_TYPE_NAME, ReturnKindDetails.SORT_ORDER, ReturnKindDetails.RETURN_KIND_NAME),
                    ReturnReasonTypeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ReturnReasonTypes.fields())
                    .from(ReturnReasonTypes)
                    .where(ReturnReasonTypes.RETURN_REASON.eq(returnReason.getPrimaryKey()),
                            ReturnReasonTypes.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return returnReasonTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ReturnReasonType> getReturnReasonTypesByReturnReason(ReturnReason returnReason) {
        return getReturnReasonTypesByReturnReason(returnReason, EntityPermission.READ_ONLY);
    }
    
    public List<ReturnReasonType> getReturnReasonTypesByReturnReasonForUpdate(ReturnReason returnReason) {
        return getReturnReasonTypesByReturnReason(returnReason, EntityPermission.READ_WRITE);
    }
    
    private List<ReturnReasonType> getReturnReasonTypesByReturnType(ReturnType returnType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ReturnReasonTypes.fields())
                    .from(ReturnReasonTypes)
                    .join(ReturnReasons).onKey(RETURN_REASON_TYPES_RETURN_REASON_FK)
                    .join(ReturnReasonDetails).onKey(RETURN_REASONS_LAST_DETAIL_FK)
                    .where(ReturnReasonTypes.RETURN_TYPE.eq(returnType.getPrimaryKey()),
                            ReturnReasonTypes.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ReturnReasonDetails.SORT_ORDER, ReturnReasonDetails.RETURN_REASON_NAME),
                    ReturnReasonTypeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ReturnReasonTypes.fields())
                    .from(ReturnReasonTypes)
                    .where(ReturnReasonTypes.RETURN_TYPE.eq(returnType.getPrimaryKey()),
                            ReturnReasonTypes.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return returnReasonTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ReturnReasonType> getReturnReasonTypesByReturnType(ReturnType returnType) {
        return getReturnReasonTypesByReturnType(returnType, EntityPermission.READ_ONLY);
    }
    
    public List<ReturnReasonType> getReturnReasonTypesByReturnTypeForUpdate(ReturnType returnType) {
        return getReturnReasonTypesByReturnType(returnType, EntityPermission.READ_WRITE);
    }
    
    public List<ReturnReasonTypeTransfer> getReturnReasonTypeTransfers(UserVisit userVisit, Collection<ReturnReasonType> returnReasonTypes) {
        List<ReturnReasonTypeTransfer> returnReasonTypeTransfers = new ArrayList<>(returnReasonTypes.size());
        
        returnReasonTypes.forEach((returnReasonType) ->
                returnReasonTypeTransfers.add(returnReasonTypeTransferCache.getReturnReasonTypeTransfer(userVisit, returnReasonType))
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
        return returnReasonTypeTransferCache.getReturnReasonTypeTransfer(userVisit, returnReasonType);
    }
    
    private void updateReturnReasonTypeFromValue(ReturnReasonTypeValue returnReasonTypeValue, boolean checkDefault, BasePK updatedBy) {
        if(returnReasonTypeValue.hasBeenModified()) {
            var returnReasonType = returnReasonTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     returnReasonTypeValue.getPrimaryKey());
            
            returnReasonType.setThruTime(session.getStartTime());
            returnReasonType.store();

            var returnReason = returnReasonType.getReturnReason(); // Not Updated
            var returnReasonPK = returnReason.getPrimaryKey(); // Not Updated
            var returnTypePK = returnReasonType.getReturnTypePK(); // Not Updated
            var isDefault = returnReasonTypeValue.getIsDefault();
            var sortOrder = returnReasonTypeValue.getSortOrder();
            
            if(checkDefault) {
                var defaultReturnReasonType = getDefaultReturnReasonType(returnReason);
                var defaultFound = defaultReturnReasonType != null && !defaultReturnReasonType.equals(returnReasonType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultReturnReasonTypeValue = getDefaultReturnReasonTypeValueForUpdate(returnReason);
                    
                    defaultReturnReasonTypeValue.setIsDefault(false);
                    updateReturnReasonTypeFromValue(defaultReturnReasonTypeValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            returnReasonType = returnReasonTypeFactory.create(returnReasonPK, returnTypePK,
                    isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(returnReasonPK, EventTypes.MODIFY, returnReasonType.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void updateReturnReasonTypeFromValue(ReturnReasonTypeValue returnReasonTypeValue, BasePK updatedBy) {
        updateReturnReasonTypeFromValue(returnReasonTypeValue, true, updatedBy);
    }
    
    public void deleteReturnReasonType(ReturnReasonType returnReasonType, BasePK deletedBy) {
        returnReasonType.setThruTime(session.getStartTime());
        returnReasonType.store();
        
        // Check for default, and pick one if necessary
        var returnReason = returnReasonType.getReturnReason();
        var defaultReturnReasonType = getDefaultReturnReasonType(returnReason);
        if(defaultReturnReasonType == null) {
            var returnReasonTypes = getReturnReasonTypesByReturnReasonForUpdate(returnReason);
            
            if(!returnReasonTypes.isEmpty()) {
                var iter = returnReasonTypes.iterator();
                if(iter.hasNext()) {
                    defaultReturnReasonType = iter.next();
                }
                var returnReasonTypeValue = defaultReturnReasonType.getReturnReasonTypeValue().clone();
                
                returnReasonTypeValue.setIsDefault(true);
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

    @Inject
    protected ReturnTypeDetailFactory returnTypeDetailFactory;

    @Inject
    private ReturnTypeFactory returnTypeFactory;

    @Inject
    private ReturnTypeTransferCache returnTypeTransferCache;

    public ReturnType createReturnType(ReturnKind returnKind, String returnTypeName, Sequence returnSequence, Boolean isDefault,
            Integer sortOrder, BasePK createdBy) {
        var defaultReturnType = getDefaultReturnType(returnKind);
        var defaultFound = defaultReturnType != null;
        
        if(defaultFound && isDefault) {
            var defaultReturnTypeDetailValue = getDefaultReturnTypeDetailValueForUpdate(returnKind);
            
            defaultReturnTypeDetailValue.setIsDefault(false);
            updateReturnTypeFromValue(defaultReturnTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var returnType = returnTypeFactory.create();
        var returnTypeDetail = returnTypeDetailFactory.create(
                returnType, returnKind, returnTypeName, returnSequence, isDefault, sortOrder, session.getStartTime(),
                Session.MAX_TIME);
        
        // Convert to R/W
        returnType = returnTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                returnType.getPrimaryKey());
        returnType.setActiveDetail(returnTypeDetail);
        returnType.setLastDetail(returnTypeDetail);
        returnType.store();
        
        sendEvent(returnType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return returnType;
    }

    public long countReturnTypesByReturnKind(final ReturnKind returnKind) {
        return session.getDslContext()
                .selectCount()
                .from(ReturnTypes)
                .join(ReturnTypeDetails).onKey(RETURN_TYPES_ACTIVE_DETAIL_FK)
                .where(ReturnTypeDetails.RETURN_KIND.eq(returnKind.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countReturnTypesByReturnSequence(final Sequence returnSequence) {
        return session.getDslContext()
                .selectCount()
                .from(ReturnTypes)
                .join(ReturnTypeDetails).onKey(RETURN_TYPES_ACTIVE_DETAIL_FK)
                .where(ReturnTypeDetails.RETURN_SEQUENCE.eq(returnSequence.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ReturnType */
    public ReturnType getReturnTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ReturnTypePK(entityInstance.getEntityUniqueId());

        return returnTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public ReturnType getReturnTypeByEntityInstance(EntityInstance entityInstance) {
        return getReturnTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ReturnType getReturnTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getReturnTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    private List<ReturnType> getReturnTypes(ReturnKind returnKind, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ReturnTypes.fields())
                .from(ReturnTypes)
                .join(ReturnTypeDetails).onKey(RETURN_TYPES_ACTIVE_DETAIL_FK)
                .where(ReturnTypeDetails.RETURN_KIND.eq(returnKind.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ReturnTypeDetails.SORT_ORDER, ReturnTypeDetails.RETURN_TYPE_NAME),
                    ReturnTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return returnTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ReturnType> getReturnTypes(ReturnKind returnKind) {
        return getReturnTypes(returnKind, EntityPermission.READ_ONLY);
    }
    
    public List<ReturnType> getReturnTypesForUpdate(ReturnKind returnKind) {
        return getReturnTypes(returnKind, EntityPermission.READ_WRITE);
    }
    
    private ReturnType getDefaultReturnType(ReturnKind returnKind, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ReturnTypes.fields())
                .from(ReturnTypes)
                .join(ReturnTypeDetails).onKey(RETURN_TYPES_ACTIVE_DETAIL_FK)
                .where(ReturnTypeDetails.RETURN_KIND.eq(returnKind.getPrimaryKey()),
                        ReturnTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return returnTypeFactory.getEntityFromQuery(entityPermission, query);
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
    
    private ReturnType getReturnTypeByName(ReturnKind returnKind, String returnTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ReturnTypes.fields())
                .from(ReturnTypes)
                .join(ReturnTypeDetails).onKey(RETURN_TYPES_ACTIVE_DETAIL_FK)
                .where(ReturnTypeDetails.RETURN_KIND.eq(returnKind.getPrimaryKey()),
                        ReturnTypeDetails.RETURN_TYPE_NAME.eq(returnTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return returnTypeFactory.getEntityFromQuery(entityPermission, query);
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
        var returnTypes = getReturnTypes(returnKind);
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
            var returnTypeDetail = returnType.getLastDetail();
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
        return returnTypeTransferCache.getReturnTypeTransfer(userVisit, returnType);
    }
    
    public List<ReturnTypeTransfer> getReturnTypeTransfersByReturnKind(UserVisit userVisit, ReturnKind returnKind) {
        var returnTypes = getReturnTypes(returnKind);
        List<ReturnTypeTransfer> returnTypeTransfers = new ArrayList<>(returnTypes.size());
        
        returnTypes.forEach((returnType) ->
                returnTypeTransfers.add(returnTypeTransferCache.getReturnTypeTransfer(userVisit, returnType))
        );
        
        return returnTypeTransfers;
    }
    
    private void updateReturnTypeFromValue(ReturnTypeDetailValue returnTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(returnTypeDetailValue.hasBeenModified()) {
            var returnType = returnTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     returnTypeDetailValue.getReturnTypePK());
            var returnTypeDetail = returnType.getActiveDetailForUpdate();
            
            returnTypeDetail.setThruTime(session.getStartTime());
            returnTypeDetail.store();

            var returnTypePK = returnTypeDetail.getReturnTypePK();
            var returnKind = returnTypeDetail.getReturnKind();
            var returnKindPK = returnKind.getPrimaryKey();
            var returnTypeName = returnTypeDetailValue.getReturnTypeName();
            var returnSequencePK = returnTypeDetailValue.getReturnSequencePK();
            var isDefault = returnTypeDetailValue.getIsDefault();
            var sortOrder = returnTypeDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultReturnType = getDefaultReturnType(returnKind);
                var defaultFound = defaultReturnType != null && !defaultReturnType.equals(returnType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultReturnTypeDetailValue = getDefaultReturnTypeDetailValueForUpdate(returnKind);
                    
                    defaultReturnTypeDetailValue.setIsDefault(false);
                    updateReturnTypeFromValue(defaultReturnTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            returnTypeDetail = returnTypeDetailFactory.create(returnTypePK, returnKindPK, returnTypeName,
                    returnSequencePK, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
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

        var returnTypeDetail = returnType.getLastDetailForUpdate();
        returnTypeDetail.setThruTime(session.getStartTime());
        returnType.setActiveDetail(null);
        returnType.store();
        
        // Check for default, and pick one if necessary
        var returnKind = returnTypeDetail.getReturnKind();
        var defaultReturnType = getDefaultReturnType(returnKind);
        if(defaultReturnType == null) {
            var returnTypes = getReturnTypesForUpdate(returnKind);
            
            if(!returnTypes.isEmpty()) {
                var iter = returnTypes.iterator();
                if(iter.hasNext()) {
                    defaultReturnType = iter.next();
                }
                var returnTypeDetailValue = Objects.requireNonNull(defaultReturnType).getLastDetailForUpdate().getReturnTypeDetailValue().clone();
                
                returnTypeDetailValue.setIsDefault(true);
                updateReturnTypeFromValue(returnTypeDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(returnType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteReturnTypesByReturnKind(ReturnKind returnKind, BasePK deletedBy) {
        var returnTypes = getReturnTypesForUpdate(returnKind);
        
        returnTypes.forEach((returnType) -> 
                deleteReturnType(returnType, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Return Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    private ReturnTypeDescriptionFactory returnTypeDescriptionFactory;

    @Inject
    private ReturnTypeDescriptionTransferCache returnTypeDescriptionTransferCache;

    public ReturnTypeDescription createReturnTypeDescription(ReturnType returnType, Language language, String description,
            BasePK createdBy) {
        var returnTypeDescription = returnTypeDescriptionFactory.create(returnType,
                language, description, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(returnType.getPrimaryKey(), EventTypes.MODIFY, returnTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return returnTypeDescription;
    }
    
    private ReturnTypeDescription getReturnTypeDescription(ReturnType returnType, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ReturnTypeDescriptions.fields())
                .from(ReturnTypeDescriptions)
                .where(ReturnTypeDescriptions.RETURN_TYPE.eq(returnType.getPrimaryKey()),
                        ReturnTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        ReturnTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return returnTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
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
    
    private List<ReturnTypeDescription> getReturnTypeDescriptionsByReturnType(ReturnType returnType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ReturnTypeDescriptions.fields())
                    .from(ReturnTypeDescriptions)
                    .join(Languages).onKey(RETURN_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(ReturnTypeDescriptions.RETURN_TYPE.eq(returnType.getPrimaryKey()),
                            ReturnTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    ReturnTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ReturnTypeDescriptions.fields())
                    .from(ReturnTypeDescriptions)
                    .where(ReturnTypeDescriptions.RETURN_TYPE.eq(returnType.getPrimaryKey()),
                            ReturnTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return returnTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ReturnTypeDescription> getReturnTypeDescriptionsByReturnType(ReturnType returnType) {
        return getReturnTypeDescriptionsByReturnType(returnType, EntityPermission.READ_ONLY);
    }
    
    public List<ReturnTypeDescription> getReturnTypeDescriptionsByReturnTypeForUpdate(ReturnType returnType) {
        return getReturnTypeDescriptionsByReturnType(returnType, EntityPermission.READ_WRITE);
    }
    
    public String getBestReturnTypeDescription(ReturnType returnType, Language language) {
        String description;
        var returnTypeDescription = getReturnTypeDescription(returnType, language);
        
        if(returnTypeDescription == null && !language.getIsDefault()) {
            returnTypeDescription = getReturnTypeDescription(returnType, partyControl.getDefaultLanguage());
        }
        
        if(returnTypeDescription == null) {
            description = returnType.getLastDetail().getReturnTypeName();
        } else {
            description = returnTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public ReturnTypeDescriptionTransfer getReturnTypeDescriptionTransfer(UserVisit userVisit, ReturnTypeDescription returnTypeDescription) {
        return returnTypeDescriptionTransferCache.getReturnTypeDescriptionTransfer(userVisit, returnTypeDescription);
    }
    
    public List<ReturnTypeDescriptionTransfer> getReturnTypeDescriptionTransfersByReturnType(UserVisit userVisit, ReturnType returnType) {
        var returnTypeDescriptions = getReturnTypeDescriptionsByReturnType(returnType);
        List<ReturnTypeDescriptionTransfer> returnTypeDescriptionTransfers = new ArrayList<>(returnTypeDescriptions.size());
        
        returnTypeDescriptions.forEach((returnTypeDescription) -> {
            returnTypeDescriptionTransfers.add(returnTypeDescriptionTransferCache.getReturnTypeDescriptionTransfer(userVisit, returnTypeDescription));
        });
        
        return returnTypeDescriptionTransfers;
    }
    
    public void updateReturnTypeDescriptionFromValue(ReturnTypeDescriptionValue returnTypeDescriptionValue, BasePK updatedBy) {
        if(returnTypeDescriptionValue.hasBeenModified()) {
            var returnTypeDescription = returnTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     returnTypeDescriptionValue.getPrimaryKey());
            
            returnTypeDescription.setThruTime(session.getStartTime());
            returnTypeDescription.store();

            var returnType = returnTypeDescription.getReturnType();
            var language = returnTypeDescription.getLanguage();
            var description = returnTypeDescriptionValue.getDescription();
            
            returnTypeDescription = returnTypeDescriptionFactory.create(returnType, language, description,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(returnType.getPrimaryKey(), EventTypes.MODIFY, returnTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteReturnTypeDescription(ReturnTypeDescription returnTypeDescription, BasePK deletedBy) {
        returnTypeDescription.setThruTime(session.getStartTime());
        
        sendEvent(returnTypeDescription.getReturnTypePK(), EventTypes.MODIFY, returnTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteReturnTypeDescriptionsByReturnType(ReturnType returnType, BasePK deletedBy) {
        var returnTypeDescriptions = getReturnTypeDescriptionsByReturnTypeForUpdate(returnType);
        
        returnTypeDescriptions.forEach((returnTypeDescription) -> 
                deleteReturnTypeDescription(returnTypeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Return Type Shipping Methods
    // --------------------------------------------------------------------------------

    @Inject
    private ReturnTypeShippingMethodFactory returnTypeShippingMethodFactory;

    @Inject
    private ReturnTypeShippingMethodTransferCache returnTypeShippingMethodTransferCache;

    public ReturnTypeShippingMethod createReturnTypeShippingMethod(ReturnType returnType, ShippingMethod shippingMethod, Boolean isDefault,
            Integer sortOrder, BasePK createdBy) {
        var defaultReturnTypeShippingMethod = getDefaultReturnTypeShippingMethod(returnType);
        var defaultFound = defaultReturnTypeShippingMethod != null;
        
        if(defaultFound && isDefault) {
            var defaultReturnTypeShippingMethodValue = getDefaultReturnTypeShippingMethodValueForUpdate(returnType);
            
            defaultReturnTypeShippingMethodValue.setIsDefault(false);
            updateReturnTypeShippingMethodFromValue(defaultReturnTypeShippingMethodValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var returnTypeShippingMethod = returnTypeShippingMethodFactory.create(returnType, shippingMethod,
                isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(returnType.getPrimaryKey(), EventTypes.MODIFY, returnTypeShippingMethod.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return returnTypeShippingMethod;
    }
    
    private ReturnTypeShippingMethod getReturnTypeShippingMethod(ReturnType returnType, ShippingMethod shippingMethod, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ReturnTypeShippingMethods.fields())
                .from(ReturnTypeShippingMethods)
                .where(ReturnTypeShippingMethods.RETURN_TYPE.eq(returnType.getPrimaryKey()),
                        ReturnTypeShippingMethods.SHIPPING_METHOD.eq(shippingMethod.getPrimaryKey()),
                        ReturnTypeShippingMethods.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return returnTypeShippingMethodFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ReturnTypeShippingMethod getReturnTypeShippingMethod(ReturnType returnType, ShippingMethod shippingMethod) {
        return getReturnTypeShippingMethod(returnType, shippingMethod, EntityPermission.READ_ONLY);
    }
    
    public ReturnTypeShippingMethod getReturnTypeShippingMethodForUpdate(ReturnType returnType, ShippingMethod shippingMethod) {
        return getReturnTypeShippingMethod(returnType, shippingMethod, EntityPermission.READ_WRITE);
    }
    
    public ReturnTypeShippingMethodValue getReturnTypeShippingMethodValueForUpdate(ReturnType returnType, ShippingMethod shippingMethod) {
        var returnTypeShippingMethod = getReturnTypeShippingMethodForUpdate(returnType, shippingMethod);
        
        return returnTypeShippingMethod == null? null: returnTypeShippingMethod.getReturnTypeShippingMethodValue().clone();
    }
    
    private ReturnTypeShippingMethod getDefaultReturnTypeShippingMethod(ReturnType returnType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ReturnTypeShippingMethods.fields())
                .from(ReturnTypeShippingMethods)
                .where(ReturnTypeShippingMethods.RETURN_TYPE.eq(returnType.getPrimaryKey()),
                        ReturnTypeShippingMethods.IS_DEFAULT.eq(true),
                        ReturnTypeShippingMethods.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return returnTypeShippingMethodFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ReturnTypeShippingMethod getDefaultReturnTypeShippingMethod(ReturnType returnType) {
        return getDefaultReturnTypeShippingMethod(returnType, EntityPermission.READ_ONLY);
    }
    
    public ReturnTypeShippingMethod getDefaultReturnTypeShippingMethodForUpdate(ReturnType returnType) {
        return getDefaultReturnTypeShippingMethod(returnType, EntityPermission.READ_WRITE);
    }
    
    public ReturnTypeShippingMethodValue getDefaultReturnTypeShippingMethodValueForUpdate(ReturnType returnType) {
        var returnTypeShippingMethod = getDefaultReturnTypeShippingMethodForUpdate(returnType);
        
        return returnTypeShippingMethod == null? null: returnTypeShippingMethod.getReturnTypeShippingMethodValue().clone();
    }
    
    private List<ReturnTypeShippingMethod> getReturnTypeShippingMethodsByReturnType(ReturnType returnType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ReturnTypeShippingMethods.fields())
                    .from(ReturnTypeShippingMethods)
                    .join(ShippingMethods).onKey(RETURN_TYPE_SHIPPING_METHODS_SHIPPING_METHOD_FK)
                    .join(ShippingMethodDetails).onKey(SHIPPING_METHODS_LAST_DETAIL_FK)
                    .where(ReturnTypeShippingMethods.RETURN_TYPE.eq(returnType.getPrimaryKey()),
                            ReturnTypeShippingMethods.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ShippingMethodDetails.SORT_ORDER, ShippingMethodDetails.SHIPPING_METHOD_NAME),
                    ReturnTypeShippingMethodFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ReturnTypeShippingMethods.fields())
                    .from(ReturnTypeShippingMethods)
                    .where(ReturnTypeShippingMethods.RETURN_TYPE.eq(returnType.getPrimaryKey()),
                            ReturnTypeShippingMethods.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return returnTypeShippingMethodFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ReturnTypeShippingMethod> getReturnTypeShippingMethodsByReturnType(ReturnType returnType) {
        return getReturnTypeShippingMethodsByReturnType(returnType, EntityPermission.READ_ONLY);
    }
    
    public List<ReturnTypeShippingMethod> getReturnTypeShippingMethodsByReturnTypeForUpdate(ReturnType returnType) {
        return getReturnTypeShippingMethodsByReturnType(returnType, EntityPermission.READ_WRITE);
    }
    
    private List<ReturnTypeShippingMethod> getReturnTypeShippingMethodsByShippingMethod(ShippingMethod shippingMethod, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ReturnTypeShippingMethods.fields())
                    .from(ReturnTypeShippingMethods)
                    .join(ReturnTypes).onKey(RETURN_TYPE_SHIPPING_METHODS_RETURN_TYPE_FK)
                    .join(ReturnTypeDetails).onKey(RETURN_TYPES_LAST_DETAIL_FK)
                    .join(ReturnKinds).onKey(RETURN_TYPE_DETAILS_RETURN_KIND_FK)
                    .join(ReturnKindDetails).onKey(RETURN_KINDS_LAST_DETAIL_FK)
                    .where(ReturnTypeShippingMethods.SHIPPING_METHOD.eq(shippingMethod.getPrimaryKey()),
                            ReturnTypeShippingMethods.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ReturnTypeDetails.SORT_ORDER, ReturnTypeDetails.RETURN_TYPE_NAME, ReturnKindDetails.SORT_ORDER, ReturnKindDetails.RETURN_KIND_NAME),
                    ReturnTypeShippingMethodFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ReturnTypeShippingMethods.fields())
                    .from(ReturnTypeShippingMethods)
                    .where(ReturnTypeShippingMethods.SHIPPING_METHOD.eq(shippingMethod.getPrimaryKey()),
                            ReturnTypeShippingMethods.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return returnTypeShippingMethodFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ReturnTypeShippingMethod> getReturnTypeShippingMethodsByShippingMethod(ShippingMethod shippingMethod) {
        return getReturnTypeShippingMethodsByShippingMethod(shippingMethod, EntityPermission.READ_ONLY);
    }
    
    public List<ReturnTypeShippingMethod> getReturnTypeShippingMethodsByShippingMethodForUpdate(ShippingMethod shippingMethod) {
        return getReturnTypeShippingMethodsByShippingMethod(shippingMethod, EntityPermission.READ_WRITE);
    }
    
    public long countReturnTypeShippingMethodsByReturnType(ReturnType returnType) {
        return session.getDslContext()
                .selectCount()
                .from(ReturnTypeShippingMethods)
                .where(ReturnTypeShippingMethods.RETURN_TYPE.eq(returnType.getPrimaryKey()),
                        ReturnTypeShippingMethods.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countReturnTypeShippingMethodsByShippingMethod(ShippingMethod shippingMethod) {
        return session.getDslContext()
                .selectCount()
                .from(ReturnTypeShippingMethods)
                .where(ReturnTypeShippingMethods.SHIPPING_METHOD.eq(shippingMethod.getPrimaryKey()),
                        ReturnTypeShippingMethods.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public List<ReturnTypeShippingMethodTransfer> getReturnTypeShippingMethodTransfers(UserVisit userVisit, Collection<ReturnTypeShippingMethod> returnTypeShippingMethods) {
        List<ReturnTypeShippingMethodTransfer> returnTypeShippingMethodTransfers = new ArrayList<>(returnTypeShippingMethods.size());
        
        returnTypeShippingMethods.forEach((returnTypeShippingMethod) ->
                returnTypeShippingMethodTransfers.add(returnTypeShippingMethodTransferCache.getReturnTypeShippingMethodTransfer(userVisit, returnTypeShippingMethod))
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
        return returnTypeShippingMethodTransferCache.getReturnTypeShippingMethodTransfer(userVisit, returnTypeShippingMethod);
    }
    
    private void updateReturnTypeShippingMethodFromValue(ReturnTypeShippingMethodValue returnTypeShippingMethodValue, boolean checkDefault, BasePK updatedBy) {
        if(returnTypeShippingMethodValue.hasBeenModified()) {
            var returnTypeShippingMethod = returnTypeShippingMethodFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     returnTypeShippingMethodValue.getPrimaryKey());
            
            returnTypeShippingMethod.setThruTime(session.getStartTime());
            returnTypeShippingMethod.store();

            var returnType = returnTypeShippingMethod.getReturnType(); // Not Updated
            var returnTypePK = returnType.getPrimaryKey(); // Not Updated
            var shippingMethodPK = returnTypeShippingMethod.getShippingMethodPK(); // Not Updated
            var isDefault = returnTypeShippingMethodValue.getIsDefault();
            var sortOrder = returnTypeShippingMethodValue.getSortOrder();
            
            if(checkDefault) {
                var defaultReturnTypeShippingMethod = getDefaultReturnTypeShippingMethod(returnType);
                var defaultFound = defaultReturnTypeShippingMethod != null && !defaultReturnTypeShippingMethod.equals(returnTypeShippingMethod);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultReturnTypeShippingMethodValue = getDefaultReturnTypeShippingMethodValueForUpdate(returnType);
                    
                    defaultReturnTypeShippingMethodValue.setIsDefault(false);
                    updateReturnTypeShippingMethodFromValue(defaultReturnTypeShippingMethodValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            returnTypeShippingMethod = returnTypeShippingMethodFactory.create(returnTypePK, shippingMethodPK,
                    isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(returnTypePK, EventTypes.MODIFY, returnTypeShippingMethod.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void updateReturnTypeShippingMethodFromValue(ReturnTypeShippingMethodValue returnTypeShippingMethodValue, BasePK updatedBy) {
        updateReturnTypeShippingMethodFromValue(returnTypeShippingMethodValue, true, updatedBy);
    }
    
    public void deleteReturnTypeShippingMethod(ReturnTypeShippingMethod returnTypeShippingMethod, BasePK deletedBy) {
        returnTypeShippingMethod.setThruTime(session.getStartTime());
        returnTypeShippingMethod.store();
        
        // Check for default, and pick one if necessary
        var returnType = returnTypeShippingMethod.getReturnType();
        var defaultReturnTypeShippingMethod = getDefaultReturnTypeShippingMethod(returnType);
        if(defaultReturnTypeShippingMethod == null) {
            var returnTypeShippingMethods = getReturnTypeShippingMethodsByReturnTypeForUpdate(returnType);
            
            if(!returnTypeShippingMethods.isEmpty()) {
                var iter = returnTypeShippingMethods.iterator();
                if(iter.hasNext()) {
                    defaultReturnTypeShippingMethod = iter.next();
                }
                var returnTypeShippingMethodValue = defaultReturnTypeShippingMethod.getReturnTypeShippingMethodValue().clone();
                
                returnTypeShippingMethodValue.setIsDefault(true);
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
