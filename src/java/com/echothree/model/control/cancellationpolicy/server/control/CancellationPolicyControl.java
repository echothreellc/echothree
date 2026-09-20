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

package com.echothree.model.control.cancellationpolicy.server.control;

import com.echothree.model.control.cancellationpolicy.common.choice.CancellationKindChoicesBean;
import com.echothree.model.control.cancellationpolicy.common.choice.CancellationPolicyChoicesBean;
import com.echothree.model.control.cancellationpolicy.common.choice.CancellationReasonChoicesBean;
import com.echothree.model.control.cancellationpolicy.common.choice.CancellationTypeChoicesBean;
import com.echothree.model.control.cancellationpolicy.common.transfer.CancellationKindDescriptionTransfer;
import com.echothree.model.control.cancellationpolicy.common.transfer.CancellationKindTransfer;
import com.echothree.model.control.cancellationpolicy.common.transfer.CancellationPolicyReasonTransfer;
import com.echothree.model.control.cancellationpolicy.common.transfer.CancellationPolicyTransfer;
import com.echothree.model.control.cancellationpolicy.common.transfer.CancellationPolicyTranslationTransfer;
import com.echothree.model.control.cancellationpolicy.common.transfer.CancellationReasonDescriptionTransfer;
import com.echothree.model.control.cancellationpolicy.common.transfer.CancellationReasonTransfer;
import com.echothree.model.control.cancellationpolicy.common.transfer.CancellationReasonTypeTransfer;
import com.echothree.model.control.cancellationpolicy.common.transfer.CancellationTypeDescriptionTransfer;
import com.echothree.model.control.cancellationpolicy.common.transfer.CancellationTypeTransfer;
import com.echothree.model.control.cancellationpolicy.common.transfer.PartyCancellationPolicyTransfer;
import com.echothree.model.control.cancellationpolicy.server.transfer.CancellationKindDescriptionTransferCache;
import com.echothree.model.control.cancellationpolicy.server.transfer.CancellationKindTransferCache;
import com.echothree.model.control.cancellationpolicy.server.transfer.CancellationPolicyReasonTransferCache;
import com.echothree.model.control.cancellationpolicy.server.transfer.CancellationPolicyTransferCache;
import com.echothree.model.control.cancellationpolicy.server.transfer.CancellationPolicyTranslationTransferCache;
import com.echothree.model.control.cancellationpolicy.server.transfer.CancellationReasonDescriptionTransferCache;
import com.echothree.model.control.cancellationpolicy.server.transfer.CancellationReasonTransferCache;
import com.echothree.model.control.cancellationpolicy.server.transfer.CancellationReasonTypeTransferCache;
import com.echothree.model.control.cancellationpolicy.server.transfer.CancellationTypeDescriptionTransferCache;
import com.echothree.model.control.cancellationpolicy.server.transfer.CancellationTypeTransferCache;
import com.echothree.model.control.cancellationpolicy.server.transfer.PartyCancellationPolicyTransferCache;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.data.cancellationpolicy.common.pk.CancellationKindPK;
import com.echothree.model.data.cancellationpolicy.common.pk.CancellationPolicyPK;
import com.echothree.model.data.cancellationpolicy.common.pk.CancellationReasonPK;
import com.echothree.model.data.cancellationpolicy.common.pk.CancellationTypePK;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationKind;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationKindDescription;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicyReason;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicyTranslation;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationReason;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationReasonDescription;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationReasonType;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationType;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationTypeDescription;
import com.echothree.model.data.cancellationpolicy.server.entity.PartyCancellationPolicy;
import com.echothree.model.data.cancellationpolicy.server.factory.CancellationKindDescriptionFactory;
import com.echothree.model.data.cancellationpolicy.server.factory.CancellationKindDetailFactory;
import com.echothree.model.data.cancellationpolicy.server.factory.CancellationKindFactory;
import com.echothree.model.data.cancellationpolicy.server.factory.CancellationPolicyDetailFactory;
import com.echothree.model.data.cancellationpolicy.server.factory.CancellationPolicyFactory;
import com.echothree.model.data.cancellationpolicy.server.factory.CancellationPolicyReasonFactory;
import com.echothree.model.data.cancellationpolicy.server.factory.CancellationPolicyTranslationFactory;
import com.echothree.model.data.cancellationpolicy.server.factory.CancellationReasonDescriptionFactory;
import com.echothree.model.data.cancellationpolicy.server.factory.CancellationReasonDetailFactory;
import com.echothree.model.data.cancellationpolicy.server.factory.CancellationReasonFactory;
import com.echothree.model.data.cancellationpolicy.server.factory.CancellationReasonTypeFactory;
import com.echothree.model.data.cancellationpolicy.server.factory.CancellationTypeDescriptionFactory;
import com.echothree.model.data.cancellationpolicy.server.factory.CancellationTypeDetailFactory;
import com.echothree.model.data.cancellationpolicy.server.factory.CancellationTypeFactory;
import com.echothree.model.data.cancellationpolicy.server.factory.PartyCancellationPolicyFactory;
import com.echothree.model.data.cancellationpolicy.server.value.CancellationKindDescriptionValue;
import com.echothree.model.data.cancellationpolicy.server.value.CancellationKindDetailValue;
import com.echothree.model.data.cancellationpolicy.server.value.CancellationPolicyDetailValue;
import com.echothree.model.data.cancellationpolicy.server.value.CancellationPolicyReasonValue;
import com.echothree.model.data.cancellationpolicy.server.value.CancellationPolicyTranslationValue;
import com.echothree.model.data.cancellationpolicy.server.value.CancellationReasonDescriptionValue;
import com.echothree.model.data.cancellationpolicy.server.value.CancellationReasonDetailValue;
import com.echothree.model.data.cancellationpolicy.server.value.CancellationReasonTypeValue;
import com.echothree.model.data.cancellationpolicy.server.value.CancellationTypeDescriptionValue;
import com.echothree.model.data.cancellationpolicy.server.value.CancellationTypeDetailValue;
import com.echothree.model.data.cancellationpolicy.server.value.PartyCancellationPolicyValue;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.cancellationpolicy.CancellationPolicyForeignKeys.CANCELLATION_KINDS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.cancellationpolicy.CancellationPolicyForeignKeys.CANCELLATION_KINDS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.cancellationpolicy.CancellationPolicyForeignKeys.CANCELLATION_KIND_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.cancellationpolicy.CancellationPolicyForeignKeys.CANCELLATION_POLICIES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.cancellationpolicy.CancellationPolicyForeignKeys.CANCELLATION_POLICIES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.cancellationpolicy.CancellationPolicyForeignKeys.CANCELLATION_POLICY_REASONS_CANCELLATION_POLICY_FK;
import static com.echothree.model.jooq.server.keys.cancellationpolicy.CancellationPolicyForeignKeys.CANCELLATION_POLICY_REASONS_CANCELLATION_REASON_FK;
import static com.echothree.model.jooq.server.keys.cancellationpolicy.CancellationPolicyForeignKeys.CANCELLATION_POLICY_TRANSLATIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.cancellationpolicy.CancellationPolicyForeignKeys.CANCELLATION_REASONS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.cancellationpolicy.CancellationPolicyForeignKeys.CANCELLATION_REASONS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.cancellationpolicy.CancellationPolicyForeignKeys.CANCELLATION_REASON_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.cancellationpolicy.CancellationPolicyForeignKeys.CANCELLATION_REASON_TYPES_CANCELLATION_REASON_FK;
import static com.echothree.model.jooq.server.keys.cancellationpolicy.CancellationPolicyForeignKeys.CANCELLATION_REASON_TYPES_CANCELLATION_TYPE_FK;
import static com.echothree.model.jooq.server.keys.cancellationpolicy.CancellationPolicyForeignKeys.CANCELLATION_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.cancellationpolicy.CancellationPolicyForeignKeys.CANCELLATION_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.cancellationpolicy.CancellationPolicyForeignKeys.CANCELLATION_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.cancellationpolicy.CancellationPolicyForeignKeys.CANCELLATION_TYPE_DETAILS_CANCELLATION_KIND_FK;
import static com.echothree.model.jooq.server.keys.cancellationpolicy.CancellationPolicyForeignKeys.PARTY_CANCELLATION_POLICIES_CANCELLATION_POLICY_FK;
import static com.echothree.model.jooq.server.keys.cancellationpolicy.CancellationPolicyForeignKeys.PARTY_CANCELLATION_POLICIES_PARTY_FK;
import static com.echothree.model.jooq.server.keys.party.PartyForeignKeys.PARTIES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.tables.cancellationpolicy.CancellationKindDescriptions.CancellationKindDescriptions;
import static com.echothree.model.jooq.server.tables.cancellationpolicy.CancellationKindDetails.CancellationKindDetails;
import static com.echothree.model.jooq.server.tables.cancellationpolicy.CancellationKinds.CancellationKinds;
import static com.echothree.model.jooq.server.tables.cancellationpolicy.CancellationPolicies.CancellationPolicies;
import static com.echothree.model.jooq.server.tables.cancellationpolicy.CancellationPolicyDetails.CancellationPolicyDetails;
import static com.echothree.model.jooq.server.tables.cancellationpolicy.CancellationPolicyReasons.CancellationPolicyReasons;
import static com.echothree.model.jooq.server.tables.cancellationpolicy.CancellationPolicyTranslations.CancellationPolicyTranslations;
import static com.echothree.model.jooq.server.tables.cancellationpolicy.CancellationReasonDescriptions.CancellationReasonDescriptions;
import static com.echothree.model.jooq.server.tables.cancellationpolicy.CancellationReasonDetails.CancellationReasonDetails;
import static com.echothree.model.jooq.server.tables.cancellationpolicy.CancellationReasonTypes.CancellationReasonTypes;
import static com.echothree.model.jooq.server.tables.cancellationpolicy.CancellationReasons.CancellationReasons;
import static com.echothree.model.jooq.server.tables.cancellationpolicy.CancellationTypeDescriptions.CancellationTypeDescriptions;
import static com.echothree.model.jooq.server.tables.cancellationpolicy.CancellationTypeDetails.CancellationTypeDetails;
import static com.echothree.model.jooq.server.tables.cancellationpolicy.CancellationTypes.CancellationTypes;
import static com.echothree.model.jooq.server.tables.cancellationpolicy.PartyCancellationPolicies.PartyCancellationPolicies;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import static com.echothree.model.jooq.server.tables.party.Parties.Parties;
import static com.echothree.model.jooq.server.tables.party.PartyDetails.PartyDetails;
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
public class CancellationPolicyControl
        extends BaseModelControl {
    
    /** Creates a new instance of CancellationPolicyControl */

    protected CancellationPolicyControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Party Cancellation Policies
    // --------------------------------------------------------------------------------

    @Inject
    protected PartyCancellationPolicyFactory partyCancellationPolicyFactory;

    @Inject
    PartyCancellationPolicyTransferCache partyCancellationPolicyTransferCache;

    public PartyCancellationPolicy createPartyCancellationPolicy(Party party, CancellationPolicy cancellationPolicy, BasePK createdBy) {
        var partyCancellationPolicy = partyCancellationPolicyFactory.create(party, cancellationPolicy,
                session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(party.getPrimaryKey(), EventTypes.MODIFY, partyCancellationPolicy.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return partyCancellationPolicy;
    }
    
    public long countPartyCancellationPoliciesByParty(Party party) {
        return session.getDslContext()
                .selectCount()
                .from(PartyCancellationPolicies)
                .where(PartyCancellationPolicies.PARTY.eq(party.getPrimaryKey()),
                        PartyCancellationPolicies.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPartyCancellationPoliciesByCancellationPolicy(CancellationPolicy cancellationPolicy) {
        return session.getDslContext()
                .selectCount()
                .from(PartyCancellationPolicies)
                .where(PartyCancellationPolicies.CANCELLATION_POLICY.eq(cancellationPolicy.getPrimaryKey()),
                        PartyCancellationPolicies.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPartyCancellationPoliciesByPartyAndCancellationPolicy(Party party, CancellationPolicy cancellationPolicy) {
        return session.getDslContext()
                .selectCount()
                .from(PartyCancellationPolicies)
                .where(PartyCancellationPolicies.PARTY.eq(party.getPrimaryKey()),
                        PartyCancellationPolicies.CANCELLATION_POLICY.eq(cancellationPolicy.getPrimaryKey()),
                        PartyCancellationPolicies.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private PartyCancellationPolicy getPartyCancellationPolicy(Party party, CancellationPolicy cancellationPolicy, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyCancellationPolicies.fields())
                .from(PartyCancellationPolicies)
                .where(PartyCancellationPolicies.PARTY.eq(party.getPrimaryKey()),
                        PartyCancellationPolicies.CANCELLATION_POLICY.eq(cancellationPolicy.getPrimaryKey()),
                        PartyCancellationPolicies.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyCancellationPolicyFactory.getEntityFromQuery(entityPermission, query);
    }

    public PartyCancellationPolicy getPartyCancellationPolicy(Party party, CancellationPolicy cancellationPolicy) {
        return getPartyCancellationPolicy(party, cancellationPolicy, EntityPermission.READ_ONLY);
    }
    
    public PartyCancellationPolicy getPartyCancellationPolicyForUpdate(Party party, CancellationPolicy cancellationPolicy) {
        return getPartyCancellationPolicy(party, cancellationPolicy, EntityPermission.READ_WRITE);
    }
    
    public PartyCancellationPolicyValue getPartyCancellationPolicyValue(PartyCancellationPolicy partyCancellationPolicy) {
        return partyCancellationPolicy == null? null: partyCancellationPolicy.getPartyCancellationPolicyValue().clone();
    }

    private List<PartyCancellationPolicy> getPartyCancellationPoliciesByCancellationPolicy(CancellationPolicy cancellationPolicy, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyCancellationPolicies.fields())
                    .from(PartyCancellationPolicies)
                    .join(CancellationPolicies).onKey(PARTY_CANCELLATION_POLICIES_CANCELLATION_POLICY_FK)
                    .join(CancellationPolicyDetails).onKey(CANCELLATION_POLICIES_LAST_DETAIL_FK)
                    .where(PartyCancellationPolicies.CANCELLATION_POLICY.eq(cancellationPolicy.getPrimaryKey()),
                            PartyCancellationPolicies.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(CancellationPolicyDetails.SORT_ORDER, CancellationPolicyDetails.CANCELLATION_POLICY_NAME),
                    PartyCancellationPolicyFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyCancellationPolicies.fields())
                    .from(PartyCancellationPolicies)
                    .where(PartyCancellationPolicies.CANCELLATION_POLICY.eq(cancellationPolicy.getPrimaryKey()),
                            PartyCancellationPolicies.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return partyCancellationPolicyFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PartyCancellationPolicy> getPartyCancellationPoliciesByCancellationPolicy(CancellationPolicy cancellationPolicy) {
        return getPartyCancellationPoliciesByCancellationPolicy(cancellationPolicy, EntityPermission.READ_ONLY);
    }

    public List<PartyCancellationPolicy> getPartyCancellationPoliciesByCancellationPolicyForUpdate(CancellationPolicy cancellationPolicy) {
        return getPartyCancellationPoliciesByCancellationPolicy(cancellationPolicy, EntityPermission.READ_WRITE);
    }

    private List<PartyCancellationPolicy> getPartyCancellationPoliciesByParty(Party party, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyCancellationPolicies.fields())
                    .from(PartyCancellationPolicies)
                    .join(Parties).onKey(PARTY_CANCELLATION_POLICIES_PARTY_FK)
                    .join(PartyDetails).onKey(PARTIES_LAST_DETAIL_FK)
                    .where(PartyCancellationPolicies.PARTY.eq(party.getPrimaryKey()),
                            PartyCancellationPolicies.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(PartyDetails.PARTY_NAME),
                    PartyCancellationPolicyFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyCancellationPolicies.fields())
                    .from(PartyCancellationPolicies)
                    .where(PartyCancellationPolicies.PARTY.eq(party.getPrimaryKey()),
                            PartyCancellationPolicies.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return partyCancellationPolicyFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PartyCancellationPolicy> getPartyCancellationPoliciesByParty(Party party) {
        return getPartyCancellationPoliciesByParty(party, EntityPermission.READ_ONLY);
    }

    public List<PartyCancellationPolicy> getPartyCancellationPoliciesByPartyForUpdate(Party party) {
        return getPartyCancellationPoliciesByParty(party, EntityPermission.READ_WRITE);
    }

    public PartyCancellationPolicyTransfer getPartyCancellationPolicyTransfer(UserVisit userVisit, PartyCancellationPolicy partyCancellationPolicy) {
        return partyCancellationPolicyTransferCache.getPartyCancellationPolicyTransfer(userVisit, partyCancellationPolicy);
    }
    
    public List<PartyCancellationPolicyTransfer> getPartyCancellationPolicyTransfers(UserVisit userVisit, Collection<PartyCancellationPolicy> cancellationPolicies) {
        List<PartyCancellationPolicyTransfer> cancellationPolicyTransfers = new ArrayList<>(cancellationPolicies.size());

        cancellationPolicies.forEach((cancellationPolicy) ->
                cancellationPolicyTransfers.add(partyCancellationPolicyTransferCache.getPartyCancellationPolicyTransfer(userVisit, cancellationPolicy))
        );

        return cancellationPolicyTransfers;
    }

    public List<PartyCancellationPolicyTransfer> getPartyCancellationPolicyTransfersByParty(UserVisit userVisit, Party party) {
        return getPartyCancellationPolicyTransfers(userVisit, getPartyCancellationPoliciesByParty(party));
    }

    public List<PartyCancellationPolicyTransfer> getPartyCancellationPolicyTransfersByCancellationPolicy(UserVisit userVisit, CancellationPolicy cancellationPolicy) {
        return getPartyCancellationPolicyTransfers(userVisit, getPartyCancellationPoliciesByCancellationPolicy(cancellationPolicy));
    }

    public void deletePartyCancellationPolicy(PartyCancellationPolicy partyCancellationPolicy, BasePK deletedBy) {

        partyCancellationPolicy.setThruTime(session.getStartTime());

        // Performed manually, since sendEvent doesn't call it for relatedEntityInstances.
        entityInstanceControl.deleteEntityInstanceDependencies(entityInstanceControl.getEntityInstanceByBasePK(partyCancellationPolicy.getPrimaryKey()), deletedBy);
        
        sendEvent(partyCancellationPolicy.getPartyPK(), EventTypes.MODIFY, partyCancellationPolicy.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deletePartyCancellationPoliciesByParty(List<PartyCancellationPolicy> partyCancellationPolicies, BasePK deletedBy) {
        partyCancellationPolicies.forEach((partyCancellationPolicy) -> 
                deletePartyCancellationPolicy(partyCancellationPolicy, deletedBy)
        );
    }

    public void deletePartyCancellationPoliciesByParty(Party party, BasePK deletedBy) {
        deletePartyCancellationPoliciesByParty(getPartyCancellationPoliciesByPartyForUpdate(party), deletedBy);
    }

    public void deletePartyCancellationPoliciesByCancellationPolicy(CancellationPolicy cancellationPolicy, BasePK deletedBy) {
        deletePartyCancellationPoliciesByParty(getPartyCancellationPoliciesByCancellationPolicyForUpdate(cancellationPolicy), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Cancellation Kinds
    // --------------------------------------------------------------------------------

    @Inject
    protected CancellationKindDetailFactory cancellationKindDetailFactory;

    @Inject
    protected CancellationKindFactory cancellationKindFactory;

    @Inject
    CancellationKindTransferCache cancellationKindTransferCache;

    public CancellationKind createCancellationKind(String cancellationKindName, SequenceType cancellationSequenceType, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var defaultCancellationKind = getDefaultCancellationKind();
        var defaultFound = defaultCancellationKind != null;
        
        if(defaultFound && isDefault) {
            var defaultCancellationKindDetailValue = getDefaultCancellationKindDetailValueForUpdate();
            
            defaultCancellationKindDetailValue.setIsDefault(false);
            updateCancellationKindFromValue(defaultCancellationKindDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var cancellationKind = cancellationKindFactory.create();
        var cancellationKindDetail = cancellationKindDetailFactory.create(cancellationKind, cancellationKindName,
                cancellationSequenceType, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        cancellationKind = cancellationKindFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                cancellationKind.getPrimaryKey());
        cancellationKind.setActiveDetail(cancellationKindDetail);
        cancellationKind.setLastDetail(cancellationKindDetail);
        cancellationKind.store();
        
        sendEvent(cancellationKind.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return cancellationKind;
    }

    public long countCancellationKinds() {
        return session.getDslContext()
                .selectCount()
                .from(CancellationKinds)
                .join(CancellationKindDetails).onKey(CANCELLATION_KINDS_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.CancellationKind */
    public CancellationKind getCancellationKindByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new CancellationKindPK(entityInstance.getEntityUniqueId());

        return cancellationKindFactory.getEntityFromPK(entityPermission, pk);
    }

    public CancellationKind getCancellationKindByEntityInstance(EntityInstance entityInstance) {
        return getCancellationKindByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public CancellationKind getCancellationKindByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getCancellationKindByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public CancellationKind getCancellationKindByName(String cancellationKindName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CancellationKinds.fields())
                .from(CancellationKinds)
                .join(CancellationKindDetails).onKey(CANCELLATION_KINDS_ACTIVE_DETAIL_FK)
                .where(CancellationKindDetails.CANCELLATION_KIND_NAME.eq(cancellationKindName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return cancellationKindFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public CancellationKind getCancellationKindByName(String cancellationKindName) {
        return getCancellationKindByName(cancellationKindName, EntityPermission.READ_ONLY);
    }
    
    public CancellationKind getCancellationKindByNameForUpdate(String cancellationKindName) {
        return getCancellationKindByName(cancellationKindName, EntityPermission.READ_WRITE);
    }
    
    public CancellationKindDetailValue getCancellationKindDetailValueForUpdate(CancellationKind cancellationKind) {
        return cancellationKind == null? null: cancellationKind.getLastDetailForUpdate().getCancellationKindDetailValue().clone();
    }
    
    public CancellationKindDetailValue getCancellationKindDetailValueByNameForUpdate(String cancellationKindName) {
        return getCancellationKindDetailValueForUpdate(getCancellationKindByNameForUpdate(cancellationKindName));
    }
    
    public CancellationKind getDefaultCancellationKind(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CancellationKinds.fields())
                .from(CancellationKinds)
                .join(CancellationKindDetails).onKey(CANCELLATION_KINDS_ACTIVE_DETAIL_FK)
                .where(CancellationKindDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return cancellationKindFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public CancellationKind getDefaultCancellationKind() {
        return getDefaultCancellationKind(EntityPermission.READ_ONLY);
    }
    
    public CancellationKind getDefaultCancellationKindForUpdate() {
        return getDefaultCancellationKind(EntityPermission.READ_WRITE);
    }
    
    public CancellationKindDetailValue getDefaultCancellationKindDetailValueForUpdate() {
        return getDefaultCancellationKind(EntityPermission.READ_WRITE).getLastDetailForUpdate().getCancellationKindDetailValue();
    }
    
    private List<CancellationKind> getCancellationKinds(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CancellationKinds.fields())
                .from(CancellationKinds)
                .join(CancellationKindDetails).onKey(CANCELLATION_KINDS_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(CancellationKindDetails.SORT_ORDER, CancellationKindDetails.CANCELLATION_KIND_NAME),
                    CancellationKindFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return cancellationKindFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<CancellationKind> getCancellationKinds() {
        return getCancellationKinds(EntityPermission.READ_ONLY);
    }
    
    public List<CancellationKind> getCancellationKindsForUpdate() {
        return getCancellationKinds(EntityPermission.READ_WRITE);
    }
    
    public CancellationKindChoicesBean getCancellationKindChoices(String defaultCancellationKindChoice, Language language, boolean allowNullChoice) {
        var cancellationKinds = getCancellationKinds();
        var size = cancellationKinds.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultCancellationKindChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var cancellationKind : cancellationKinds) {
            var cancellationKindDetail = cancellationKind.getLastDetail();
            
            var label = getBestCancellationKindDescription(cancellationKind, language);
            var value = cancellationKindDetail.getCancellationKindName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultCancellationKindChoice != null && defaultCancellationKindChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && cancellationKindDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new CancellationKindChoicesBean(labels, values, defaultValue);
    }
    
    public CancellationKindTransfer getCancellationKindTransfer(UserVisit userVisit, CancellationKind cancellationKind) {
        return cancellationKindTransferCache.getCancellationKindTransfer(userVisit, cancellationKind);
    }

    public List<CancellationKindTransfer> getCancellationKindTransfers(UserVisit userVisit, Collection<CancellationKind> cancellationKinds) {
        var cancellationKindTransfers = new ArrayList<CancellationKindTransfer>(cancellationKinds.size());

        cancellationKinds.forEach((cancellationKind) ->
                cancellationKindTransfers.add(cancellationKindTransferCache.getCancellationKindTransfer(userVisit, cancellationKind))
        );

        return cancellationKindTransfers;
    }

    public List<CancellationKindTransfer> getCancellationKindTransfers(UserVisit userVisit) {
        return getCancellationKindTransfers(userVisit, getCancellationKinds());
    }

    private void updateCancellationKindFromValue(CancellationKindDetailValue cancellationKindDetailValue, boolean checkDefault, BasePK updatedBy) {
        var cancellationKind = cancellationKindFactory.getEntityFromPK(
                EntityPermission.READ_WRITE, cancellationKindDetailValue.getCancellationKindPK());
        var cancellationKindDetail = cancellationKind.getActiveDetailForUpdate();
        
        cancellationKindDetail.setThruTime(session.getStartTime());
        cancellationKindDetail.store();

        var cancellationKindPK = cancellationKindDetail.getCancellationKindPK();
        var cancellationKindName = cancellationKindDetailValue.getCancellationKindName();
        var cancellationSequenceTypePK = cancellationKindDetailValue.getCancellationSequenceTypePK();
        var isDefault = cancellationKindDetailValue.getIsDefault();
        var sortOrder = cancellationKindDetailValue.getSortOrder();
        
        if(checkDefault) {
            var defaultCancellationKind = getDefaultCancellationKind();
            var defaultFound = defaultCancellationKind != null && !defaultCancellationKind.equals(cancellationKind);
            
            if(isDefault && defaultFound) {
                // If I'm the default, and a default already existed...
                var defaultCancellationKindDetailValue = getDefaultCancellationKindDetailValueForUpdate();
                
                defaultCancellationKindDetailValue.setIsDefault(false);
                updateCancellationKindFromValue(defaultCancellationKindDetailValue, false, updatedBy);
            } else if(!isDefault && !defaultFound) {
                // If I'm not the default, and no other default exists...
                isDefault = true;
            }
        }
        
        cancellationKindDetail = cancellationKindDetailFactory.create(cancellationKindPK, cancellationKindName, cancellationSequenceTypePK,
                isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        cancellationKind.setActiveDetail(cancellationKindDetail);
        cancellationKind.setLastDetail(cancellationKindDetail);
        cancellationKind.store();
        
        sendEvent(cancellationKindPK, EventTypes.MODIFY, null, null, updatedBy);
    }
    
    public void updateCancellationKindFromValue(CancellationKindDetailValue cancellationKindDetailValue, BasePK updatedBy) {
        updateCancellationKindFromValue(cancellationKindDetailValue, true, updatedBy);
    }
    
    public void deleteCancellationKind(CancellationKind cancellationKind, BasePK deletedBy) {
        deleteCancellationKindDescriptionsByCancellationKind(cancellationKind, deletedBy);

        var cancellationKindDetail = cancellationKind.getLastDetailForUpdate();
        cancellationKindDetail.setThruTime(session.getStartTime());
        cancellationKind.setActiveDetail(null);
        cancellationKind.store();
        
        // Check for default, and pick one if necessary
        var defaultCancellationKind = getDefaultCancellationKind();
        if(defaultCancellationKind == null) {
            var cancellationKinds = getCancellationKindsForUpdate();
            
            if(!cancellationKinds.isEmpty()) {
                var iter = cancellationKinds.iterator();
                if(iter.hasNext()) {
                    defaultCancellationKind = iter.next();
                }
                var cancellationKindDetailValue = Objects.requireNonNull(defaultCancellationKind).getLastDetailForUpdate().getCancellationKindDetailValue().clone();
                
                cancellationKindDetailValue.setIsDefault(true);
                updateCancellationKindFromValue(cancellationKindDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(cancellationKind.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Cancellation Kind Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected CancellationKindDescriptionFactory cancellationKindDescriptionFactory;

    @Inject
    CancellationKindDescriptionTransferCache cancellationKindDescriptionTransferCache;

    public CancellationKindDescription createCancellationKindDescription(CancellationKind cancellationKind, Language language, String description,
            BasePK createdBy) {
        var cancellationKindDescription = cancellationKindDescriptionFactory.create(cancellationKind,
                language, description, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(cancellationKind.getPrimaryKey(), EventTypes.MODIFY, cancellationKindDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return cancellationKindDescription;
    }
    
    public CancellationKindDescription getCancellationKindDescription(CancellationKind cancellationKind, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CancellationKindDescriptions.fields())
                .from(CancellationKindDescriptions)
                .where(CancellationKindDescriptions.CANCELLATION_KIND.eq(cancellationKind.getPrimaryKey()),
                        CancellationKindDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        CancellationKindDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return cancellationKindDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public CancellationKindDescription getCancellationKindDescription(CancellationKind cancellationKind, Language language) {
        return getCancellationKindDescription(cancellationKind, language, EntityPermission.READ_ONLY);
    }
    
    public CancellationKindDescription getCancellationKindDescriptionForUpdate(CancellationKind cancellationKind, Language language) {
        return getCancellationKindDescription(cancellationKind, language, EntityPermission.READ_WRITE);
    }
    
    public CancellationKindDescriptionValue getCancellationKindDescriptionValue(CancellationKindDescription cancellationKindDescription) {
        return cancellationKindDescription == null? null: cancellationKindDescription.getCancellationKindDescriptionValue().clone();
    }
    
    public CancellationKindDescriptionValue getCancellationKindDescriptionValueForUpdate(CancellationKind cancellationKind, Language language) {
        return getCancellationKindDescriptionValue(getCancellationKindDescriptionForUpdate(cancellationKind, language));
    }
    
    private List<CancellationKindDescription> getCancellationKindDescriptionsByCancellationKind(CancellationKind cancellationKind, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(CancellationKindDescriptions.fields())
                    .from(CancellationKindDescriptions)
                    .join(Languages).onKey(CANCELLATION_KIND_DESCRIPTIONS_LANGUAGE_FK)
                    .where(CancellationKindDescriptions.CANCELLATION_KIND.eq(cancellationKind.getPrimaryKey()),
                            CancellationKindDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    CancellationKindDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(CancellationKindDescriptions.fields())
                    .from(CancellationKindDescriptions)
                    .where(CancellationKindDescriptions.CANCELLATION_KIND.eq(cancellationKind.getPrimaryKey()),
                            CancellationKindDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return cancellationKindDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<CancellationKindDescription> getCancellationKindDescriptionsByCancellationKind(CancellationKind cancellationKind) {
        return getCancellationKindDescriptionsByCancellationKind(cancellationKind, EntityPermission.READ_ONLY);
    }
    
    public List<CancellationKindDescription> getCancellationKindDescriptionsByCancellationKindForUpdate(CancellationKind cancellationKind) {
        return getCancellationKindDescriptionsByCancellationKind(cancellationKind, EntityPermission.READ_WRITE);
    }
    
    public String getBestCancellationKindDescription(CancellationKind cancellationKind, Language language) {
        String description;
        var cancellationKindDescription = getCancellationKindDescription(cancellationKind, language);
        
        if(cancellationKindDescription == null && !language.getIsDefault()) {
            cancellationKindDescription = getCancellationKindDescription(cancellationKind, partyControl.getDefaultLanguage());
        }
        
        if(cancellationKindDescription == null) {
            description = cancellationKind.getLastDetail().getCancellationKindName();
        } else {
            description = cancellationKindDescription.getDescription();
        }
        
        return description;
    }
    
    public CancellationKindDescriptionTransfer getCancellationKindDescriptionTransfer(UserVisit userVisit, CancellationKindDescription cancellationKindDescription) {
        return cancellationKindDescriptionTransferCache.getCancellationKindDescriptionTransfer(userVisit, cancellationKindDescription);
    }
    
    public List<CancellationKindDescriptionTransfer> getCancellationKindDescriptionTransfersByCancellationKind(UserVisit userVisit, CancellationKind cancellationKind) {
        var cancellationKindDescriptions = getCancellationKindDescriptionsByCancellationKind(cancellationKind);
        List<CancellationKindDescriptionTransfer> cancellationKindDescriptionTransfers = new ArrayList<>(cancellationKindDescriptions.size());
        
        cancellationKindDescriptions.forEach((cancellationKindDescription) -> {
            cancellationKindDescriptionTransfers.add(cancellationKindDescriptionTransferCache.getCancellationKindDescriptionTransfer(userVisit, cancellationKindDescription));
        });
        
        return cancellationKindDescriptionTransfers;
    }
    
    public void updateCancellationKindDescriptionFromValue(CancellationKindDescriptionValue cancellationKindDescriptionValue, BasePK updatedBy) {
        if(cancellationKindDescriptionValue.hasBeenModified()) {
            var cancellationKindDescription = cancellationKindDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     cancellationKindDescriptionValue.getPrimaryKey());
            
            cancellationKindDescription.setThruTime(session.getStartTime());
            cancellationKindDescription.store();

            var cancellationKind = cancellationKindDescription.getCancellationKind();
            var language = cancellationKindDescription.getLanguage();
            var description = cancellationKindDescriptionValue.getDescription();
            
            cancellationKindDescription = cancellationKindDescriptionFactory.create(cancellationKind, language, description,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(cancellationKind.getPrimaryKey(), EventTypes.MODIFY, cancellationKindDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteCancellationKindDescription(CancellationKindDescription cancellationKindDescription, BasePK deletedBy) {
        cancellationKindDescription.setThruTime(session.getStartTime());
        
        sendEvent(cancellationKindDescription.getCancellationKindPK(), EventTypes.MODIFY, cancellationKindDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteCancellationKindDescriptionsByCancellationKind(CancellationKind cancellationKind, BasePK deletedBy) {
        var cancellationKindDescriptions = getCancellationKindDescriptionsByCancellationKindForUpdate(cancellationKind);
        
        cancellationKindDescriptions.forEach((cancellationKindDescription) -> 
                deleteCancellationKindDescription(cancellationKindDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Cancellation Policies
    // --------------------------------------------------------------------------------

    @Inject
    protected CancellationPolicyDetailFactory cancellationPolicyDetailFactory;

    @Inject
    protected CancellationPolicyFactory cancellationPolicyFactory;

    @Inject
    CancellationPolicyTransferCache cancellationPolicyTransferCache;

    public CancellationPolicy createCancellationPolicy(CancellationKind cancellationKind, String cancellationPolicyName, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var defaultCancellationPolicy = getDefaultCancellationPolicy(cancellationKind);
        var defaultFound = defaultCancellationPolicy != null;
        
        if(defaultFound && isDefault) {
            var defaultCancellationPolicyDetailValue = getDefaultCancellationPolicyDetailValueForUpdate(cancellationKind);
            
            defaultCancellationPolicyDetailValue.setIsDefault(false);
            updateCancellationPolicyFromValue(defaultCancellationPolicyDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var cancellationPolicy = cancellationPolicyFactory.create();
        var cancellationPolicyDetail = cancellationPolicyDetailFactory.create(
                cancellationPolicy, cancellationKind, cancellationPolicyName, isDefault, sortOrder, session.getStartTime(),
                Session.MAX_TIME);
        
        // Convert to R/W
        cancellationPolicy = cancellationPolicyFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                cancellationPolicy.getPrimaryKey());
        cancellationPolicy.setActiveDetail(cancellationPolicyDetail);
        cancellationPolicy.setLastDetail(cancellationPolicyDetail);
        cancellationPolicy.store();
        
        sendEvent(cancellationPolicy.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return cancellationPolicy;
    }

    public long countCancellationPoliciesByCancellationKind(CancellationKind cancellationKind) {
        return session.getDslContext()
                .selectCount()
                .from(CancellationPolicies)
                .join(CancellationPolicyDetails).onKey(CANCELLATION_POLICIES_ACTIVE_DETAIL_FK)
                .where(CancellationPolicyDetails.CANCELLATION_KIND.eq(cancellationKind.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.CancellationPolicy */
    public CancellationPolicy getCancellationPolicyByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new CancellationPolicyPK(entityInstance.getEntityUniqueId());

        return cancellationPolicyFactory.getEntityFromPK(entityPermission, pk);
    }

    public CancellationPolicy getCancellationPolicyByEntityInstance(EntityInstance entityInstance) {
        return getCancellationPolicyByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public CancellationPolicy getCancellationPolicyByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getCancellationPolicyByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    private List<CancellationPolicy> getCancellationPolicies(CancellationKind cancellationKind, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CancellationPolicies.fields())
                .from(CancellationPolicies)
                .join(CancellationPolicyDetails).onKey(CANCELLATION_POLICIES_ACTIVE_DETAIL_FK)
                .where(CancellationPolicyDetails.CANCELLATION_KIND.eq(cancellationKind.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(CancellationPolicyDetails.SORT_ORDER, CancellationPolicyDetails.CANCELLATION_POLICY_NAME),
                    CancellationPolicyFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return cancellationPolicyFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<CancellationPolicy> getCancellationPolicies(CancellationKind cancellationKind) {
        return getCancellationPolicies(cancellationKind, EntityPermission.READ_ONLY);
    }
    
    public List<CancellationPolicy> getCancellationPoliciesForUpdate(CancellationKind cancellationKind) {
        return getCancellationPolicies(cancellationKind, EntityPermission.READ_WRITE);
    }
    
    public CancellationPolicy getDefaultCancellationPolicy(CancellationKind cancellationKind, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CancellationPolicies.fields())
                .from(CancellationPolicies)
                .join(CancellationPolicyDetails).onKey(CANCELLATION_POLICIES_ACTIVE_DETAIL_FK)
                .where(CancellationPolicyDetails.CANCELLATION_KIND.eq(cancellationKind.getPrimaryKey()),
                        CancellationPolicyDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return cancellationPolicyFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public CancellationPolicy getDefaultCancellationPolicy(CancellationKind cancellationKind) {
        return getDefaultCancellationPolicy(cancellationKind, EntityPermission.READ_ONLY);
    }
    
    public CancellationPolicy getDefaultCancellationPolicyForUpdate(CancellationKind cancellationKind) {
        return getDefaultCancellationPolicy(cancellationKind, EntityPermission.READ_WRITE);
    }
    
    public CancellationPolicyDetailValue getDefaultCancellationPolicyDetailValueForUpdate(CancellationKind cancellationKind) {
        return getDefaultCancellationPolicyForUpdate(cancellationKind).getLastDetailForUpdate().getCancellationPolicyDetailValue().clone();
    }
    
    public CancellationPolicy getCancellationPolicyByName(CancellationKind cancellationKind, String cancellationPolicyName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CancellationPolicies.fields())
                .from(CancellationPolicies)
                .join(CancellationPolicyDetails).onKey(CANCELLATION_POLICIES_ACTIVE_DETAIL_FK)
                .where(CancellationPolicyDetails.CANCELLATION_KIND.eq(cancellationKind.getPrimaryKey()),
                        CancellationPolicyDetails.CANCELLATION_POLICY_NAME.eq(cancellationPolicyName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return cancellationPolicyFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public CancellationPolicy getCancellationPolicyByName(CancellationKind cancellationKind, String cancellationPolicyName) {
        return getCancellationPolicyByName(cancellationKind, cancellationPolicyName, EntityPermission.READ_ONLY);
    }
    
    public CancellationPolicy getCancellationPolicyByNameForUpdate(CancellationKind cancellationKind, String cancellationPolicyName) {
        return getCancellationPolicyByName(cancellationKind, cancellationPolicyName, EntityPermission.READ_WRITE);
    }
    
    public CancellationPolicyDetailValue getCancellationPolicyDetailValueForUpdate(CancellationPolicy cancellationPolicy) {
        return cancellationPolicy == null? null: cancellationPolicy.getLastDetailForUpdate().getCancellationPolicyDetailValue().clone();
    }
    
    public CancellationPolicyDetailValue getCancellationPolicyDetailValueByNameForUpdate(CancellationKind cancellationKind, String cancellationPolicyName) {
        return getCancellationPolicyDetailValueForUpdate(getCancellationPolicyByNameForUpdate(cancellationKind, cancellationPolicyName));
    }
    
    public CancellationPolicyChoicesBean getCancellationPolicyChoices(String defaultCancellationPolicyChoice, Language language,
            boolean allowNullChoice, CancellationKind cancellationKind) {
        var cancellationPolicies = getCancellationPolicies(cancellationKind);
        var size = cancellationPolicies.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultCancellationPolicyChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var cancellationPolicy : cancellationPolicies) {
            var cancellationPolicyDetail = cancellationPolicy.getLastDetail();
            var cancellationPolicyName = cancellationPolicyDetail.getCancellationPolicyName();
            var cancellationPolicyTranslation = getBestCancellationPolicyTranslation(cancellationPolicy, language);

            var label = cancellationPolicyTranslation == null ? cancellationPolicyName : cancellationPolicyTranslation.getDescription();
            var value = cancellationPolicyName;
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultCancellationPolicyChoice != null && defaultCancellationPolicyChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && cancellationPolicyDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new CancellationPolicyChoicesBean(labels, values, defaultValue);
    }
    
    public CancellationPolicyTransfer getCancellationPolicyTransfer(UserVisit userVisit, CancellationPolicy cancellationPolicy) {
        return cancellationPolicyTransferCache.getCancellationPolicyTransfer(userVisit, cancellationPolicy);
    }

    public List<CancellationPolicyTransfer> getCancellationPolicyTransfers(UserVisit userVisit, Collection<CancellationPolicy> cancellationPolicies) {
        List<CancellationPolicyTransfer> cancellationPolicyTransfers = new ArrayList<>(cancellationPolicies.size());

        cancellationPolicies.forEach((cancellationPolicy) ->
                cancellationPolicyTransfers.add(cancellationPolicyTransferCache.getCancellationPolicyTransfer(userVisit, cancellationPolicy))
        );

        return cancellationPolicyTransfers;
    }

    public List<CancellationPolicyTransfer> getCancellationPolicyTransfersByCancellationKind(UserVisit userVisit, CancellationKind cancellationKind) {
        return getCancellationPolicyTransfers(userVisit, getCancellationPolicies(cancellationKind));
    }

    private void updateCancellationPolicyFromValue(CancellationPolicyDetailValue cancellationPolicyDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(cancellationPolicyDetailValue.hasBeenModified()) {
            var cancellationPolicy = cancellationPolicyFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     cancellationPolicyDetailValue.getCancellationPolicyPK());
            var cancellationPolicyDetail = cancellationPolicy.getActiveDetailForUpdate();
            
            cancellationPolicyDetail.setThruTime(session.getStartTime());
            cancellationPolicyDetail.store();

            var cancellationPolicyPK = cancellationPolicyDetail.getCancellationPolicyPK();
            var cancellationKind = cancellationPolicyDetail.getCancellationKind();
            var cancellationKindPK = cancellationKind.getPrimaryKey();
            var cancellationPolicyName = cancellationPolicyDetailValue.getCancellationPolicyName();
            var isDefault = cancellationPolicyDetailValue.getIsDefault();
            var sortOrder = cancellationPolicyDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultCancellationPolicy = getDefaultCancellationPolicy(cancellationKind);
                var defaultFound = defaultCancellationPolicy != null && !defaultCancellationPolicy.equals(cancellationPolicy);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultCancellationPolicyDetailValue = getDefaultCancellationPolicyDetailValueForUpdate(cancellationKind);
                    
                    defaultCancellationPolicyDetailValue.setIsDefault(false);
                    updateCancellationPolicyFromValue(defaultCancellationPolicyDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            cancellationPolicyDetail = cancellationPolicyDetailFactory.create(cancellationPolicyPK,
                    cancellationKindPK, cancellationPolicyName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            cancellationPolicy.setActiveDetail(cancellationPolicyDetail);
            cancellationPolicy.setLastDetail(cancellationPolicyDetail);
            
            sendEvent(cancellationPolicyPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateCancellationPolicyFromValue(CancellationPolicyDetailValue cancellationPolicyDetailValue, BasePK updatedBy) {
        updateCancellationPolicyFromValue(cancellationPolicyDetailValue, true, updatedBy);
    }
    
    public void deleteCancellationPolicy(CancellationPolicy cancellationPolicy, BasePK deletedBy) {
        deletePartyCancellationPoliciesByCancellationPolicy(cancellationPolicy, deletedBy);
        deleteCancellationPolicyReasonsByCancellationPolicy(cancellationPolicy, deletedBy);
        deleteCancellationPolicyTranslationsByCancellationPolicy(cancellationPolicy, deletedBy);

        var cancellationPolicyDetail = cancellationPolicy.getLastDetailForUpdate();
        cancellationPolicyDetail.setThruTime(session.getStartTime());
        cancellationPolicy.setActiveDetail(null);
        cancellationPolicy.store();
        
        // Check for default, and pick one if necessary
        var cancellationKind = cancellationPolicyDetail.getCancellationKind();
        var defaultCancellationPolicy = getDefaultCancellationPolicy(cancellationKind);
        if(defaultCancellationPolicy == null) {
            var cancellationPolicies = getCancellationPoliciesForUpdate(cancellationKind);
            
            if(!cancellationPolicies.isEmpty()) {
                var iter = cancellationPolicies.iterator();
                if(iter.hasNext()) {
                    defaultCancellationPolicy = iter.next();
                }
                var cancellationPolicyDetailValue = Objects.requireNonNull(defaultCancellationPolicy).getLastDetailForUpdate().getCancellationPolicyDetailValue().clone();
                
                cancellationPolicyDetailValue.setIsDefault(true);
                updateCancellationPolicyFromValue(cancellationPolicyDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(cancellationPolicy.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteCancellationPoliciesByCancellationKind(CancellationKind cancellationKind, BasePK deletedBy) {
        var cancellationPolicies = getCancellationPoliciesForUpdate(cancellationKind);
        
        cancellationPolicies.forEach((cancellationPolicy) -> 
                deleteCancellationPolicy(cancellationPolicy, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Cancellation Policy Translations
    // --------------------------------------------------------------------------------

    @Inject
    protected CancellationPolicyTranslationFactory cancellationPolicyTranslationFactory;

    @Inject
    CancellationPolicyTranslationTransferCache cancellationPolicyTranslationTransferCache;

    public CancellationPolicyTranslation createCancellationPolicyTranslation(CancellationPolicy cancellationPolicy, Language language,
            String description, MimeType policyMimeType, String policy, BasePK createdBy) {
        var cancellationPolicyTranslation = cancellationPolicyTranslationFactory.create(cancellationPolicy,
                language, description, policyMimeType, policy, session.getStartTime(), Session.MAX_TIME);

        sendEvent(cancellationPolicy.getPrimaryKey(), EventTypes.MODIFY, cancellationPolicyTranslation.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return cancellationPolicyTranslation;
    }

    private CancellationPolicyTranslation getCancellationPolicyTranslation(CancellationPolicy cancellationPolicy, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CancellationPolicyTranslations.fields())
                .from(CancellationPolicyTranslations)
                .where(CancellationPolicyTranslations.CANCELLATION_POLICY.eq(cancellationPolicy.getPrimaryKey()),
                        CancellationPolicyTranslations.LANGUAGE.eq(language.getPrimaryKey()),
                        CancellationPolicyTranslations.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return cancellationPolicyTranslationFactory.getEntityFromQuery(entityPermission, query);
    }

    public CancellationPolicyTranslation getCancellationPolicyTranslation(CancellationPolicy cancellationPolicy, Language language) {
        return getCancellationPolicyTranslation(cancellationPolicy, language, EntityPermission.READ_ONLY);
    }

    public CancellationPolicyTranslation getCancellationPolicyTranslationForUpdate(CancellationPolicy cancellationPolicy, Language language) {
        return getCancellationPolicyTranslation(cancellationPolicy, language, EntityPermission.READ_WRITE);
    }

    public CancellationPolicyTranslationValue getCancellationPolicyTranslationValue(CancellationPolicyTranslation cancellationPolicyTranslation) {
        return cancellationPolicyTranslation == null? null: cancellationPolicyTranslation.getCancellationPolicyTranslationValue().clone();
    }

    public CancellationPolicyTranslationValue getCancellationPolicyTranslationValueForUpdate(CancellationPolicy cancellationPolicy, Language language) {
        return getCancellationPolicyTranslationValue(getCancellationPolicyTranslationForUpdate(cancellationPolicy, language));
    }

    private List<CancellationPolicyTranslation> getCancellationPolicyTranslationsByCancellationPolicy(CancellationPolicy cancellationPolicy, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(CancellationPolicyTranslations.fields())
                    .from(CancellationPolicyTranslations)
                    .join(Languages).onKey(CANCELLATION_POLICY_TRANSLATIONS_LANGUAGE_FK)
                    .where(CancellationPolicyTranslations.CANCELLATION_POLICY.eq(cancellationPolicy.getPrimaryKey()),
                            CancellationPolicyTranslations.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    CancellationPolicyTranslationFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(CancellationPolicyTranslations.fields())
                    .from(CancellationPolicyTranslations)
                    .where(CancellationPolicyTranslations.CANCELLATION_POLICY.eq(cancellationPolicy.getPrimaryKey()),
                            CancellationPolicyTranslations.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return cancellationPolicyTranslationFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<CancellationPolicyTranslation> getCancellationPolicyTranslationsByCancellationPolicy(CancellationPolicy cancellationPolicy) {
        return getCancellationPolicyTranslationsByCancellationPolicy(cancellationPolicy, EntityPermission.READ_ONLY);
    }

    public List<CancellationPolicyTranslation> getCancellationPolicyTranslationsByCancellationPolicyForUpdate(CancellationPolicy cancellationPolicy) {
        return getCancellationPolicyTranslationsByCancellationPolicy(cancellationPolicy, EntityPermission.READ_WRITE);
    }

    public CancellationPolicyTranslation getBestCancellationPolicyTranslation(CancellationPolicy cancellationPolicy, Language language) {
        var cancellationPolicyTranslation = getCancellationPolicyTranslation(cancellationPolicy, language);

        if(cancellationPolicyTranslation == null && !language.getIsDefault()) {
            cancellationPolicyTranslation = getCancellationPolicyTranslation(cancellationPolicy, partyControl.getDefaultLanguage());
        }

        return cancellationPolicyTranslation;
    }

    public CancellationPolicyTranslationTransfer getCancellationPolicyTranslationTransfer(UserVisit userVisit, CancellationPolicyTranslation cancellationPolicyTranslation) {
        return cancellationPolicyTranslationTransferCache.getCancellationPolicyTranslationTransfer(userVisit, cancellationPolicyTranslation);
    }

    public List<CancellationPolicyTranslationTransfer> getCancellationPolicyTranslationTransfers(UserVisit userVisit, CancellationPolicy cancellationPolicy) {
        var cancellationPolicyTranslations = getCancellationPolicyTranslationsByCancellationPolicy(cancellationPolicy);
        List<CancellationPolicyTranslationTransfer> cancellationPolicyTranslationTransfers = new ArrayList<>(cancellationPolicyTranslations.size());

        cancellationPolicyTranslations.forEach((cancellationPolicyTranslation) ->
                cancellationPolicyTranslationTransfers.add(cancellationPolicyTranslationTransferCache.getCancellationPolicyTranslationTransfer(userVisit, cancellationPolicyTranslation))
        );

        return cancellationPolicyTranslationTransfers;
    }

    public void updateCancellationPolicyTranslationFromValue(CancellationPolicyTranslationValue cancellationPolicyTranslationValue, BasePK updatedBy) {
        if(cancellationPolicyTranslationValue.hasBeenModified()) {
            var cancellationPolicyTranslation = cancellationPolicyTranslationFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    cancellationPolicyTranslationValue.getPrimaryKey());

            cancellationPolicyTranslation.setThruTime(session.getStartTime());
            cancellationPolicyTranslation.store();

            var cancellationPolicyPK = cancellationPolicyTranslation.getCancellationPolicyPK();
            var languagePK = cancellationPolicyTranslation.getLanguagePK();
            var description = cancellationPolicyTranslationValue.getDescription();
            var policyMimeTypePK = cancellationPolicyTranslationValue.getPolicyMimeTypePK();
            var policy = cancellationPolicyTranslationValue.getPolicy();

            cancellationPolicyTranslation = cancellationPolicyTranslationFactory.create(cancellationPolicyPK, languagePK, description,
                    policyMimeTypePK, policy, session.getStartTime(), Session.MAX_TIME);

            sendEvent(cancellationPolicyPK, EventTypes.MODIFY, cancellationPolicyTranslation.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteCancellationPolicyTranslation(CancellationPolicyTranslation cancellationPolicyTranslation, BasePK deletedBy) {
        cancellationPolicyTranslation.setThruTime(session.getStartTime());

        sendEvent(cancellationPolicyTranslation.getCancellationPolicyPK(), EventTypes.MODIFY, cancellationPolicyTranslation.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteCancellationPolicyTranslationsByCancellationPolicy(CancellationPolicy cancellationPolicy, BasePK deletedBy) {
        var cancellationPolicyTranslations = getCancellationPolicyTranslationsByCancellationPolicyForUpdate(cancellationPolicy);

        cancellationPolicyTranslations.forEach((cancellationPolicyTranslation) -> 
                deleteCancellationPolicyTranslation(cancellationPolicyTranslation, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Cancellation Policy Reasons
    // --------------------------------------------------------------------------------

    @Inject
    protected CancellationPolicyReasonFactory cancellationPolicyReasonFactory;

    @Inject
    CancellationPolicyReasonTransferCache cancellationPolicyReasonTransferCache;

    public CancellationPolicyReason createCancellationPolicyReason(CancellationPolicy cancellationPolicy, CancellationReason cancellationReason, Boolean isDefault,
            Integer sortOrder, BasePK createdBy) {
        var defaultCancellationPolicyReason = getDefaultCancellationPolicyReason(cancellationPolicy);
        var defaultFound = defaultCancellationPolicyReason != null;
        
        if(defaultFound && isDefault) {
            var defaultCancellationPolicyReasonValue = getDefaultCancellationPolicyReasonValueForUpdate(cancellationPolicy);
            
            defaultCancellationPolicyReasonValue.setIsDefault(false);
            updateCancellationPolicyReasonFromValue(defaultCancellationPolicyReasonValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var cancellationPolicyReason = cancellationPolicyReasonFactory.create(cancellationPolicy, cancellationReason,
                isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(cancellationPolicy.getPrimaryKey(), EventTypes.MODIFY, cancellationPolicyReason.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return cancellationPolicyReason;
    }

    public long countCancellationPolicyReasonsByCancellationPolicy(final CancellationPolicy cancellationPolicy) {
        return session.getDslContext()
                .selectCount()
                .from(CancellationPolicyReasons)
                .where(CancellationPolicyReasons.CANCELLATION_POLICY.eq(cancellationPolicy.getPrimaryKey()),
                        CancellationPolicyReasons.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countCancellationPolicyReasonsByCancellationReason(final CancellationReason cancellationReason) {
        return session.getDslContext()
                .selectCount()
                .from(CancellationPolicyReasons)
                .where(CancellationPolicyReasons.CANCELLATION_REASON.eq(cancellationReason.getPrimaryKey()),
                        CancellationPolicyReasons.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public CancellationPolicyReason getCancellationPolicyReason(CancellationPolicy cancellationPolicy, CancellationReason cancellationReason, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CancellationPolicyReasons.fields())
                .from(CancellationPolicyReasons)
                .where(CancellationPolicyReasons.CANCELLATION_POLICY.eq(cancellationPolicy.getPrimaryKey()),
                        CancellationPolicyReasons.CANCELLATION_REASON.eq(cancellationReason.getPrimaryKey()),
                        CancellationPolicyReasons.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return cancellationPolicyReasonFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public CancellationPolicyReason getCancellationPolicyReason(CancellationPolicy cancellationPolicy, CancellationReason cancellationReason) {
        return getCancellationPolicyReason(cancellationPolicy, cancellationReason, EntityPermission.READ_ONLY);
    }
    
    public CancellationPolicyReason getCancellationPolicyReasonForUpdate(CancellationPolicy cancellationPolicy, CancellationReason cancellationReason) {
        return getCancellationPolicyReason(cancellationPolicy, cancellationReason, EntityPermission.READ_WRITE);
    }
    
    public CancellationPolicyReasonValue getCancellationPolicyReasonValueForUpdate(CancellationPolicy cancellationPolicy, CancellationReason cancellationReason) {
        var cancellationPolicyReason = getCancellationPolicyReasonForUpdate(cancellationPolicy, cancellationReason);
        
        return cancellationPolicyReason == null? null: cancellationPolicyReason.getCancellationPolicyReasonValue().clone();
    }
    
    private CancellationPolicyReason getDefaultCancellationPolicyReason(CancellationPolicy cancellationPolicy, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CancellationPolicyReasons.fields())
                .from(CancellationPolicyReasons)
                .where(CancellationPolicyReasons.CANCELLATION_POLICY.eq(cancellationPolicy.getPrimaryKey()),
                        CancellationPolicyReasons.IS_DEFAULT.eq(true),
                        CancellationPolicyReasons.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return cancellationPolicyReasonFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public CancellationPolicyReason getDefaultCancellationPolicyReason(CancellationPolicy cancellationPolicy) {
        return getDefaultCancellationPolicyReason(cancellationPolicy, EntityPermission.READ_ONLY);
    }
    
    public CancellationPolicyReason getDefaultCancellationPolicyReasonForUpdate(CancellationPolicy cancellationPolicy) {
        return getDefaultCancellationPolicyReason(cancellationPolicy, EntityPermission.READ_WRITE);
    }
    
    public CancellationPolicyReasonValue getDefaultCancellationPolicyReasonValueForUpdate(CancellationPolicy cancellationPolicy) {
        var cancellationPolicyReason = getDefaultCancellationPolicyReasonForUpdate(cancellationPolicy);
        
        return cancellationPolicyReason == null? null: cancellationPolicyReason.getCancellationPolicyReasonValue().clone();
    }
    
    private List<CancellationPolicyReason> getCancellationPolicyReasonsByCancellationPolicy(CancellationPolicy cancellationPolicy, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(CancellationPolicyReasons.fields())
                    .from(CancellationPolicyReasons)
                    .join(CancellationReasons).onKey(CANCELLATION_POLICY_REASONS_CANCELLATION_REASON_FK)
                    .join(CancellationReasonDetails).onKey(CANCELLATION_REASONS_LAST_DETAIL_FK)
                    .where(CancellationPolicyReasons.CANCELLATION_POLICY.eq(cancellationPolicy.getPrimaryKey()),
                            CancellationPolicyReasons.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(CancellationReasonDetails.SORT_ORDER, CancellationReasonDetails.CANCELLATION_REASON_NAME),
                    CancellationPolicyReasonFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(CancellationPolicyReasons.fields())
                    .from(CancellationPolicyReasons)
                    .where(CancellationPolicyReasons.CANCELLATION_POLICY.eq(cancellationPolicy.getPrimaryKey()),
                            CancellationPolicyReasons.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return cancellationPolicyReasonFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<CancellationPolicyReason> getCancellationPolicyReasonsByCancellationPolicy(CancellationPolicy cancellationPolicy) {
        return getCancellationPolicyReasonsByCancellationPolicy(cancellationPolicy, EntityPermission.READ_ONLY);
    }
    
    public List<CancellationPolicyReason> getCancellationPolicyReasonsByCancellationPolicyForUpdate(CancellationPolicy cancellationPolicy) {
        return getCancellationPolicyReasonsByCancellationPolicy(cancellationPolicy, EntityPermission.READ_WRITE);
    }
    
    private List<CancellationPolicyReason> getCancellationPolicyReasonsByCancellationReason(CancellationReason cancellationReason, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(CancellationPolicyReasons.fields())
                    .from(CancellationPolicyReasons)
                    .join(CancellationPolicies).onKey(CANCELLATION_POLICY_REASONS_CANCELLATION_POLICY_FK)
                    .join(CancellationPolicyDetails).onKey(CANCELLATION_POLICIES_LAST_DETAIL_FK)
                    .where(CancellationPolicyReasons.CANCELLATION_REASON.eq(cancellationReason.getPrimaryKey()),
                            CancellationPolicyReasons.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(CancellationPolicyDetails.SORT_ORDER, CancellationPolicyDetails.CANCELLATION_POLICY_NAME),
                    CancellationPolicyReasonFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(CancellationPolicyReasons.fields())
                    .from(CancellationPolicyReasons)
                    .where(CancellationPolicyReasons.CANCELLATION_REASON.eq(cancellationReason.getPrimaryKey()),
                            CancellationPolicyReasons.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return cancellationPolicyReasonFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<CancellationPolicyReason> getCancellationPolicyReasonsByCancellationReason(CancellationReason cancellationReason) {
        return getCancellationPolicyReasonsByCancellationReason(cancellationReason, EntityPermission.READ_ONLY);
    }
    
    public List<CancellationPolicyReason> getCancellationPolicyReasonsByCancellationReasonForUpdate(CancellationReason cancellationReason) {
        return getCancellationPolicyReasonsByCancellationReason(cancellationReason, EntityPermission.READ_WRITE);
    }
    
    public List<CancellationPolicyReasonTransfer> getCancellationPolicyReasonTransfers(UserVisit userVisit, Collection<CancellationPolicyReason> cancellationPolicyReasons) {
        List<CancellationPolicyReasonTransfer> cancellationPolicyReasonTransfers = new ArrayList<>(cancellationPolicyReasons.size());
        
        cancellationPolicyReasons.forEach((cancellationPolicyReason) ->
                cancellationPolicyReasonTransfers.add(cancellationPolicyReasonTransferCache.getCancellationPolicyReasonTransfer(userVisit, cancellationPolicyReason))
        );
        
        return cancellationPolicyReasonTransfers;
    }
    
    public List<CancellationPolicyReasonTransfer> getCancellationPolicyReasonTransfersByCancellationPolicy(UserVisit userVisit, CancellationPolicy cancellationPolicy) {
        return getCancellationPolicyReasonTransfers(userVisit, getCancellationPolicyReasonsByCancellationPolicy(cancellationPolicy));
    }
    
    public List<CancellationPolicyReasonTransfer> getCancellationPolicyReasonTransfersByCancellationReason(UserVisit userVisit, CancellationReason cancellationReason) {
        return getCancellationPolicyReasonTransfers(userVisit, getCancellationPolicyReasonsByCancellationReason(cancellationReason));
    }
    
    public CancellationPolicyReasonTransfer getCancellationPolicyReasonTransfer(UserVisit userVisit, CancellationPolicyReason cancellationPolicyReason) {
        return cancellationPolicyReasonTransferCache.getCancellationPolicyReasonTransfer(userVisit, cancellationPolicyReason);
    }
    
    private void updateCancellationPolicyReasonFromValue(CancellationPolicyReasonValue cancellationPolicyReasonValue, boolean checkDefault, BasePK updatedBy) {
        if(cancellationPolicyReasonValue.hasBeenModified()) {
            var cancellationPolicyReason = cancellationPolicyReasonFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     cancellationPolicyReasonValue.getPrimaryKey());
            
            cancellationPolicyReason.setThruTime(session.getStartTime());
            cancellationPolicyReason.store();

            var cancellationPolicy = cancellationPolicyReason.getCancellationPolicy(); // Not Updated
            var cancellationPolicyPK = cancellationPolicy.getPrimaryKey(); // Not Updated
            var cancellationReasonPK = cancellationPolicyReason.getCancellationReasonPK(); // Not Updated
            var isDefault = cancellationPolicyReasonValue.getIsDefault();
            var sortOrder = cancellationPolicyReasonValue.getSortOrder();
            
            if(checkDefault) {
                var defaultCancellationPolicyReason = getDefaultCancellationPolicyReason(cancellationPolicy);
                var defaultFound = defaultCancellationPolicyReason != null && !defaultCancellationPolicyReason.equals(cancellationPolicyReason);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultCancellationPolicyReasonValue = getDefaultCancellationPolicyReasonValueForUpdate(cancellationPolicy);
                    
                    defaultCancellationPolicyReasonValue.setIsDefault(false);
                    updateCancellationPolicyReasonFromValue(defaultCancellationPolicyReasonValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            cancellationPolicyReason = cancellationPolicyReasonFactory.create(cancellationPolicyPK, cancellationReasonPK,
                    isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(cancellationPolicyPK, EventTypes.MODIFY, cancellationPolicyReason.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void updateCancellationPolicyReasonFromValue(CancellationPolicyReasonValue cancellationPolicyReasonValue, BasePK updatedBy) {
        updateCancellationPolicyReasonFromValue(cancellationPolicyReasonValue, true, updatedBy);
    }
    
    public void deleteCancellationPolicyReason(CancellationPolicyReason cancellationPolicyReason, BasePK deletedBy) {
        cancellationPolicyReason.setThruTime(session.getStartTime());
        cancellationPolicyReason.store();
        
        // Check for default, and pick one if necessary
        var cancellationPolicy = cancellationPolicyReason.getCancellationPolicy();
        var defaultCancellationPolicyReason = getDefaultCancellationPolicyReason(cancellationPolicy);
        if(defaultCancellationPolicyReason == null) {
            var cancellationPolicyReasons = getCancellationPolicyReasonsByCancellationPolicyForUpdate(cancellationPolicy);
            
            if(!cancellationPolicyReasons.isEmpty()) {
                var iter = cancellationPolicyReasons.iterator();
                if(iter.hasNext()) {
                    defaultCancellationPolicyReason = iter.next();
                }
                var cancellationPolicyReasonValue = defaultCancellationPolicyReason.getCancellationPolicyReasonValue().clone();
                
                cancellationPolicyReasonValue.setIsDefault(true);
                updateCancellationPolicyReasonFromValue(cancellationPolicyReasonValue, false, deletedBy);
            }
        }
        
        sendEvent(cancellationPolicy.getPrimaryKey(), EventTypes.MODIFY, cancellationPolicyReason.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteCancellationPolicyReasons(List<CancellationPolicyReason> cancellationPolicyReasons, BasePK deletedBy) {
        cancellationPolicyReasons.forEach((cancellationPolicyReason) -> 
                deleteCancellationPolicyReason(cancellationPolicyReason, deletedBy)
        );
    }
    
    public void deleteCancellationPolicyReasonsByCancellationPolicy(CancellationPolicy cancellationPolicy, BasePK deletedBy) {
        deleteCancellationPolicyReasons(getCancellationPolicyReasonsByCancellationPolicyForUpdate(cancellationPolicy), deletedBy);
    }
    
    public void deleteCancellationPolicyReasonsByCancellationReason(CancellationReason cancellationReason, BasePK deletedBy) {
        deleteCancellationPolicyReasons(getCancellationPolicyReasonsByCancellationReasonForUpdate(cancellationReason), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Cancellation Reasons
    // --------------------------------------------------------------------------------

    @Inject
    protected CancellationReasonDetailFactory cancellationReasonDetailFactory;

    @Inject
    protected CancellationReasonFactory cancellationReasonFactory;

    @Inject
    CancellationReasonTransferCache cancellationReasonTransferCache;

    public CancellationReason createCancellationReason(CancellationKind cancellationKind, String cancellationReasonName, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var defaultCancellationReason = getDefaultCancellationReason(cancellationKind);
        var defaultFound = defaultCancellationReason != null;
        
        if(defaultFound && isDefault) {
            var defaultCancellationReasonDetailValue = getDefaultCancellationReasonDetailValueForUpdate(cancellationKind);
            
            defaultCancellationReasonDetailValue.setIsDefault(false);
            updateCancellationReasonFromValue(defaultCancellationReasonDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var cancellationReason = cancellationReasonFactory.create();
        var cancellationReasonDetail = cancellationReasonDetailFactory.create(
                cancellationReason, cancellationKind, cancellationReasonName, isDefault, sortOrder, session.getStartTime(),
                Session.MAX_TIME);
        
        // Convert to R/W
        cancellationReason = cancellationReasonFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                cancellationReason.getPrimaryKey());
        cancellationReason.setActiveDetail(cancellationReasonDetail);
        cancellationReason.setLastDetail(cancellationReasonDetail);
        cancellationReason.store();
        
        sendEvent(cancellationReason.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return cancellationReason;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.CancellationReason */
    public CancellationReason getCancellationReasonByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new CancellationReasonPK(entityInstance.getEntityUniqueId());

        return cancellationReasonFactory.getEntityFromPK(entityPermission, pk);
    }

    public CancellationReason getCancellationReasonByEntityInstance(EntityInstance entityInstance) {
        return getCancellationReasonByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public CancellationReason getCancellationReasonByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getCancellationReasonByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countCancellationReasonsByCancellationKind(final CancellationKind cancellationKind) {
        return session.getDslContext()
                .selectCount()
                .from(CancellationReasons)
                .join(CancellationReasonDetails).onKey(CANCELLATION_REASONS_ACTIVE_DETAIL_FK)
                .where(CancellationReasonDetails.CANCELLATION_KIND.eq(cancellationKind.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<CancellationReason> getCancellationReasons(CancellationKind cancellationKind, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CancellationReasons.fields())
                .from(CancellationReasons)
                .join(CancellationReasonDetails).onKey(CANCELLATION_REASONS_ACTIVE_DETAIL_FK)
                .where(CancellationReasonDetails.CANCELLATION_KIND.eq(cancellationKind.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(CancellationReasonDetails.SORT_ORDER, CancellationReasonDetails.CANCELLATION_REASON_NAME),
                    CancellationReasonFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return cancellationReasonFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<CancellationReason> getCancellationReasons(CancellationKind cancellationKind) {
        return getCancellationReasons(cancellationKind, EntityPermission.READ_ONLY);
    }
    
    public List<CancellationReason> getCancellationReasonsForUpdate(CancellationKind cancellationKind) {
        return getCancellationReasons(cancellationKind, EntityPermission.READ_WRITE);
    }
    
    private CancellationReason getDefaultCancellationReason(CancellationKind cancellationKind, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CancellationReasons.fields())
                .from(CancellationReasons)
                .join(CancellationReasonDetails).onKey(CANCELLATION_REASONS_ACTIVE_DETAIL_FK)
                .where(CancellationReasonDetails.CANCELLATION_KIND.eq(cancellationKind.getPrimaryKey()),
                        CancellationReasonDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return cancellationReasonFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public CancellationReason getDefaultCancellationReason(CancellationKind cancellationKind) {
        return getDefaultCancellationReason(cancellationKind, EntityPermission.READ_ONLY);
    }
    
    public CancellationReason getDefaultCancellationReasonForUpdate(CancellationKind cancellationKind) {
        return getDefaultCancellationReason(cancellationKind, EntityPermission.READ_WRITE);
    }
    
    public CancellationReasonDetailValue getDefaultCancellationReasonDetailValueForUpdate(CancellationKind cancellationKind) {
        return getDefaultCancellationReasonForUpdate(cancellationKind).getLastDetailForUpdate().getCancellationReasonDetailValue().clone();
    }
    
    public CancellationReason getCancellationReasonByName(CancellationKind cancellationKind, String cancellationReasonName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CancellationReasons.fields())
                .from(CancellationReasons)
                .join(CancellationReasonDetails).onKey(CANCELLATION_REASONS_ACTIVE_DETAIL_FK)
                .where(CancellationReasonDetails.CANCELLATION_KIND.eq(cancellationKind.getPrimaryKey()),
                        CancellationReasonDetails.CANCELLATION_REASON_NAME.eq(cancellationReasonName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return cancellationReasonFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public CancellationReason getCancellationReasonByName(CancellationKind cancellationKind, String cancellationReasonName) {
        return getCancellationReasonByName(cancellationKind, cancellationReasonName, EntityPermission.READ_ONLY);
    }
    
    public CancellationReason getCancellationReasonByNameForUpdate(CancellationKind cancellationKind, String cancellationReasonName) {
        return getCancellationReasonByName(cancellationKind, cancellationReasonName, EntityPermission.READ_WRITE);
    }
    
    public CancellationReasonDetailValue getCancellationReasonDetailValueForUpdate(CancellationReason cancellationReason) {
        return cancellationReason == null? null: cancellationReason.getLastDetailForUpdate().getCancellationReasonDetailValue().clone();
    }
    
    public CancellationReasonDetailValue getCancellationReasonDetailValueByNameForUpdate(CancellationKind cancellationKind, String cancellationReasonName) {
        return getCancellationReasonDetailValueForUpdate(getCancellationReasonByNameForUpdate(cancellationKind, cancellationReasonName));
    }
    
    public CancellationReasonChoicesBean getCancellationReasonChoices(String defaultCancellationReasonChoice, Language language,
            boolean allowNullChoice, CancellationKind cancellationKind) {
        var cancellationReasons = getCancellationReasons(cancellationKind);
        var size = cancellationReasons.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultCancellationReasonChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var cancellationReason : cancellationReasons) {
            var cancellationReasonDetail = cancellationReason.getLastDetail();
            var label = getBestCancellationReasonDescription(cancellationReason, language);
            var value = cancellationReasonDetail.getCancellationReasonName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultCancellationReasonChoice != null && defaultCancellationReasonChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && cancellationReasonDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new CancellationReasonChoicesBean(labels, values, defaultValue);
    }
    
    public CancellationReasonTransfer getCancellationReasonTransfer(UserVisit userVisit, CancellationReason cancellationReason) {
        return cancellationReasonTransferCache.getCancellationReasonTransfer(userVisit, cancellationReason);
    }
    
    public List<CancellationReasonTransfer> getCancellationReasonTransfers(UserVisit userVisit, Collection<CancellationReason> cancellationReasons) {
        List<CancellationReasonTransfer> cancellationReasonTransfers = new ArrayList<>(cancellationReasons.size());
        
        cancellationReasons.forEach((cancellationReason) ->
                cancellationReasonTransfers.add(cancellationReasonTransferCache.getCancellationReasonTransfer(userVisit, cancellationReason))
        );
        
        return cancellationReasonTransfers;
    }
    
    public List<CancellationReasonTransfer> getCancellationReasonTransfersByCancellationKind(UserVisit userVisit, CancellationKind cancellationKind) {
        return getCancellationReasonTransfers(userVisit, getCancellationReasons(cancellationKind));
    }
    
    private void updateCancellationReasonFromValue(CancellationReasonDetailValue cancellationReasonDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(cancellationReasonDetailValue.hasBeenModified()) {
            var cancellationReason = cancellationReasonFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     cancellationReasonDetailValue.getCancellationReasonPK());
            var cancellationReasonDetail = cancellationReason.getActiveDetailForUpdate();
            
            cancellationReasonDetail.setThruTime(session.getStartTime());
            cancellationReasonDetail.store();

            var cancellationReasonPK = cancellationReasonDetail.getCancellationReasonPK();
            var cancellationKind = cancellationReasonDetail.getCancellationKind();
            var cancellationKindPK = cancellationKind.getPrimaryKey();
            var cancellationReasonName = cancellationReasonDetailValue.getCancellationReasonName();
            var isDefault = cancellationReasonDetailValue.getIsDefault();
            var sortOrder = cancellationReasonDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultCancellationReason = getDefaultCancellationReason(cancellationKind);
                var defaultFound = defaultCancellationReason != null && !defaultCancellationReason.equals(cancellationReason);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultCancellationReasonDetailValue = getDefaultCancellationReasonDetailValueForUpdate(cancellationKind);
                    
                    defaultCancellationReasonDetailValue.setIsDefault(false);
                    updateCancellationReasonFromValue(defaultCancellationReasonDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            cancellationReasonDetail = cancellationReasonDetailFactory.create(cancellationReasonPK,
                    cancellationKindPK, cancellationReasonName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            cancellationReason.setActiveDetail(cancellationReasonDetail);
            cancellationReason.setLastDetail(cancellationReasonDetail);
            
            sendEvent(cancellationReasonPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateCancellationReasonFromValue(CancellationReasonDetailValue cancellationReasonDetailValue, BasePK updatedBy) {
        updateCancellationReasonFromValue(cancellationReasonDetailValue, true, updatedBy);
    }
    
    public void deleteCancellationReason(CancellationReason cancellationReason, BasePK deletedBy) {
        deleteCancellationPolicyReasonsByCancellationReason(cancellationReason, deletedBy);
        deleteCancellationReasonTypesByCancellationReason(cancellationReason, deletedBy);
        deleteCancellationReasonDescriptionsByCancellationReason(cancellationReason, deletedBy);

        var cancellationReasonDetail = cancellationReason.getLastDetailForUpdate();
        cancellationReasonDetail.setThruTime(session.getStartTime());
        cancellationReason.setActiveDetail(null);
        cancellationReason.store();
        
        // Check for default, and pick one if necessary
        var cancellationKind = cancellationReasonDetail.getCancellationKind();
        var defaultCancellationReason = getDefaultCancellationReason(cancellationKind);
        if(defaultCancellationReason == null) {
            var cancellationReasons = getCancellationReasonsForUpdate(cancellationKind);
            
            if(!cancellationReasons.isEmpty()) {
                var iter = cancellationReasons.iterator();
                if(iter.hasNext()) {
                    defaultCancellationReason = iter.next();
                }
                var cancellationReasonDetailValue = Objects.requireNonNull(defaultCancellationReason).getLastDetailForUpdate().getCancellationReasonDetailValue().clone();
                
                cancellationReasonDetailValue.setIsDefault(true);
                updateCancellationReasonFromValue(cancellationReasonDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(cancellationReason.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteCancellationReasonsByCancellationKind(CancellationKind cancellationKind, BasePK deletedBy) {
        var cancellationReasons = getCancellationReasonsForUpdate(cancellationKind);
        
        cancellationReasons.forEach((cancellationReason) -> 
                deleteCancellationReason(cancellationReason, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Cancellation Reason Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected CancellationReasonDescriptionFactory cancellationReasonDescriptionFactory;

    @Inject
    CancellationReasonDescriptionTransferCache cancellationReasonDescriptionTransferCache;

    public CancellationReasonDescription createCancellationReasonDescription(CancellationReason cancellationReason, Language language, String description,
            BasePK createdBy) {
        var cancellationReasonDescription = cancellationReasonDescriptionFactory.create(cancellationReason,
                language, description, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(cancellationReason.getPrimaryKey(), EventTypes.MODIFY, cancellationReasonDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return cancellationReasonDescription;
    }
    
    public CancellationReasonDescription getCancellationReasonDescription(CancellationReason cancellationReason, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CancellationReasonDescriptions.fields())
                .from(CancellationReasonDescriptions)
                .where(CancellationReasonDescriptions.CANCELLATION_REASON.eq(cancellationReason.getPrimaryKey()),
                        CancellationReasonDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        CancellationReasonDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return cancellationReasonDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public CancellationReasonDescription getCancellationReasonDescription(CancellationReason cancellationReason, Language language) {
        return getCancellationReasonDescription(cancellationReason, language, EntityPermission.READ_ONLY);
    }
    
    public CancellationReasonDescription getCancellationReasonDescriptionForUpdate(CancellationReason cancellationReason, Language language) {
        return getCancellationReasonDescription(cancellationReason, language, EntityPermission.READ_WRITE);
    }
    
    public CancellationReasonDescriptionValue getCancellationReasonDescriptionValue(CancellationReasonDescription cancellationReasonDescription) {
        return cancellationReasonDescription == null? null: cancellationReasonDescription.getCancellationReasonDescriptionValue().clone();
    }
    
    public CancellationReasonDescriptionValue getCancellationReasonDescriptionValueForUpdate(CancellationReason cancellationReason, Language language) {
        return getCancellationReasonDescriptionValue(getCancellationReasonDescriptionForUpdate(cancellationReason, language));
    }
    
    private List<CancellationReasonDescription> getCancellationReasonDescriptionsByCancellationReason(CancellationReason cancellationReason, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(CancellationReasonDescriptions.fields())
                    .from(CancellationReasonDescriptions)
                    .join(Languages).onKey(CANCELLATION_REASON_DESCRIPTIONS_LANGUAGE_FK)
                    .where(CancellationReasonDescriptions.CANCELLATION_REASON.eq(cancellationReason.getPrimaryKey()),
                            CancellationReasonDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    CancellationReasonDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(CancellationReasonDescriptions.fields())
                    .from(CancellationReasonDescriptions)
                    .where(CancellationReasonDescriptions.CANCELLATION_REASON.eq(cancellationReason.getPrimaryKey()),
                            CancellationReasonDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return cancellationReasonDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<CancellationReasonDescription> getCancellationReasonDescriptionsByCancellationReason(CancellationReason cancellationReason) {
        return getCancellationReasonDescriptionsByCancellationReason(cancellationReason, EntityPermission.READ_ONLY);
    }
    
    public List<CancellationReasonDescription> getCancellationReasonDescriptionsByCancellationReasonForUpdate(CancellationReason cancellationReason) {
        return getCancellationReasonDescriptionsByCancellationReason(cancellationReason, EntityPermission.READ_WRITE);
    }
    
    public String getBestCancellationReasonDescription(CancellationReason cancellationReason, Language language) {
        String description;
        var cancellationReasonDescription = getCancellationReasonDescription(cancellationReason, language);
        
        if(cancellationReasonDescription == null && !language.getIsDefault()) {
            cancellationReasonDescription = getCancellationReasonDescription(cancellationReason, partyControl.getDefaultLanguage());
        }
        
        if(cancellationReasonDescription == null) {
            description = cancellationReason.getLastDetail().getCancellationReasonName();
        } else {
            description = cancellationReasonDescription.getDescription();
        }
        
        return description;
    }
    
    public CancellationReasonDescriptionTransfer getCancellationReasonDescriptionTransfer(UserVisit userVisit, CancellationReasonDescription cancellationReasonDescription) {
        return cancellationReasonDescriptionTransferCache.getCancellationReasonDescriptionTransfer(userVisit, cancellationReasonDescription);
    }
    
    public List<CancellationReasonDescriptionTransfer> getCancellationReasonDescriptionTransfersByCancellationReason(UserVisit userVisit, CancellationReason cancellationReason) {
        var cancellationReasonDescriptions = getCancellationReasonDescriptionsByCancellationReason(cancellationReason);
        List<CancellationReasonDescriptionTransfer> cancellationReasonDescriptionTransfers = new ArrayList<>(cancellationReasonDescriptions.size());
        
        cancellationReasonDescriptions.forEach((cancellationReasonDescription) -> {
            cancellationReasonDescriptionTransfers.add(cancellationReasonDescriptionTransferCache.getCancellationReasonDescriptionTransfer(userVisit, cancellationReasonDescription));
        });
        
        return cancellationReasonDescriptionTransfers;
    }
    
    public void updateCancellationReasonDescriptionFromValue(CancellationReasonDescriptionValue cancellationReasonDescriptionValue, BasePK updatedBy) {
        if(cancellationReasonDescriptionValue.hasBeenModified()) {
            var cancellationReasonDescription = cancellationReasonDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     cancellationReasonDescriptionValue.getPrimaryKey());
            
            cancellationReasonDescription.setThruTime(session.getStartTime());
            cancellationReasonDescription.store();

            var cancellationReason = cancellationReasonDescription.getCancellationReason();
            var language = cancellationReasonDescription.getLanguage();
            var description = cancellationReasonDescriptionValue.getDescription();
            
            cancellationReasonDescription = cancellationReasonDescriptionFactory.create(cancellationReason, language, description,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(cancellationReason.getPrimaryKey(), EventTypes.MODIFY, cancellationReasonDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteCancellationReasonDescription(CancellationReasonDescription cancellationReasonDescription, BasePK deletedBy) {
        cancellationReasonDescription.setThruTime(session.getStartTime());
        
        sendEvent(cancellationReasonDescription.getCancellationReasonPK(), EventTypes.MODIFY, cancellationReasonDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteCancellationReasonDescriptionsByCancellationReason(CancellationReason cancellationReason, BasePK deletedBy) {
        var cancellationReasonDescriptions = getCancellationReasonDescriptionsByCancellationReasonForUpdate(cancellationReason);
        
        cancellationReasonDescriptions.forEach((cancellationReasonDescription) -> 
                deleteCancellationReasonDescription(cancellationReasonDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Cancellation Reason Types
    // --------------------------------------------------------------------------------

    @Inject
    protected CancellationReasonTypeFactory cancellationReasonTypeFactory;

    @Inject
    CancellationReasonTypeTransferCache cancellationReasonTypeTransferCache;

    public CancellationReasonType createCancellationReasonType(CancellationReason cancellationReason, CancellationType cancellationType, Boolean isDefault,
            Integer sortOrder, BasePK createdBy) {
        var defaultCancellationReasonType = getDefaultCancellationReasonType(cancellationReason);
        var defaultFound = defaultCancellationReasonType != null;
        
        if(defaultFound && isDefault) {
            var defaultCancellationReasonTypeValue = getDefaultCancellationReasonTypeValueForUpdate(cancellationReason);
            
            defaultCancellationReasonTypeValue.setIsDefault(false);
            updateCancellationReasonTypeFromValue(defaultCancellationReasonTypeValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var cancellationReasonType = cancellationReasonTypeFactory.create(cancellationReason, cancellationType,
                isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(cancellationReason.getPrimaryKey(), EventTypes.MODIFY, cancellationReasonType.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return cancellationReasonType;
    }

    public long countCancellationReasonTypesByCancellationReason(final CancellationReason cancellationReason) {
        return session.getDslContext()
                .selectCount()
                .from(CancellationReasonTypes)
                .where(CancellationReasonTypes.CANCELLATION_REASON.eq(cancellationReason.getPrimaryKey()),
                        CancellationReasonTypes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countCancellationReasonTypesByCancellationType(final CancellationType cancellationType) {
        return session.getDslContext()
                .selectCount()
                .from(CancellationReasonTypes)
                .where(CancellationReasonTypes.CANCELLATION_TYPE.eq(cancellationType.getPrimaryKey()),
                        CancellationReasonTypes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public CancellationReasonType getCancellationReasonType(CancellationReason cancellationReason, CancellationType cancellationType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CancellationReasonTypes.fields())
                .from(CancellationReasonTypes)
                .where(CancellationReasonTypes.CANCELLATION_REASON.eq(cancellationReason.getPrimaryKey()),
                        CancellationReasonTypes.CANCELLATION_TYPE.eq(cancellationType.getPrimaryKey()),
                        CancellationReasonTypes.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return cancellationReasonTypeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public CancellationReasonType getCancellationReasonType(CancellationReason cancellationReason, CancellationType cancellationType) {
        return getCancellationReasonType(cancellationReason, cancellationType, EntityPermission.READ_ONLY);
    }
    
    public CancellationReasonType getCancellationReasonTypeForUpdate(CancellationReason cancellationReason, CancellationType cancellationType) {
        return getCancellationReasonType(cancellationReason, cancellationType, EntityPermission.READ_WRITE);
    }
    
    public CancellationReasonTypeValue getCancellationReasonTypeValueForUpdate(CancellationReason cancellationReason, CancellationType cancellationType) {
        var cancellationReasonType = getCancellationReasonTypeForUpdate(cancellationReason, cancellationType);
        
        return cancellationReasonType == null? null: cancellationReasonType.getCancellationReasonTypeValue().clone();
    }
    
    private CancellationReasonType getDefaultCancellationReasonType(CancellationReason cancellationReason, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CancellationReasonTypes.fields())
                .from(CancellationReasonTypes)
                .where(CancellationReasonTypes.CANCELLATION_REASON.eq(cancellationReason.getPrimaryKey()),
                        CancellationReasonTypes.IS_DEFAULT.eq(true),
                        CancellationReasonTypes.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return cancellationReasonTypeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public CancellationReasonType getDefaultCancellationReasonType(CancellationReason cancellationReason) {
        return getDefaultCancellationReasonType(cancellationReason, EntityPermission.READ_ONLY);
    }
    
    public CancellationReasonType getDefaultCancellationReasonTypeForUpdate(CancellationReason cancellationReason) {
        return getDefaultCancellationReasonType(cancellationReason, EntityPermission.READ_WRITE);
    }
    
    public CancellationReasonTypeValue getDefaultCancellationReasonTypeValueForUpdate(CancellationReason cancellationReason) {
        var cancellationReasonType = getDefaultCancellationReasonTypeForUpdate(cancellationReason);
        
        return cancellationReasonType == null? null: cancellationReasonType.getCancellationReasonTypeValue().clone();
    }
    
    private List<CancellationReasonType> getCancellationReasonTypesByCancellationReason(CancellationReason cancellationReason, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(CancellationReasonTypes.fields())
                    .from(CancellationReasonTypes)
                    .join(CancellationTypes).onKey(CANCELLATION_REASON_TYPES_CANCELLATION_TYPE_FK)
                    .join(CancellationTypeDetails).onKey(CANCELLATION_TYPES_LAST_DETAIL_FK)
                    .join(CancellationKinds).onKey(CANCELLATION_TYPE_DETAILS_CANCELLATION_KIND_FK)
                    .join(CancellationKindDetails).onKey(CANCELLATION_KINDS_LAST_DETAIL_FK)
                    .where(CancellationReasonTypes.CANCELLATION_REASON.eq(cancellationReason.getPrimaryKey()),
                            CancellationReasonTypes.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(CancellationTypeDetails.SORT_ORDER, CancellationTypeDetails.CANCELLATION_TYPE_NAME, CancellationKindDetails.SORT_ORDER, CancellationKindDetails.CANCELLATION_KIND_NAME),
                    CancellationReasonTypeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(CancellationReasonTypes.fields())
                    .from(CancellationReasonTypes)
                    .where(CancellationReasonTypes.CANCELLATION_REASON.eq(cancellationReason.getPrimaryKey()),
                            CancellationReasonTypes.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return cancellationReasonTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<CancellationReasonType> getCancellationReasonTypesByCancellationReason(CancellationReason cancellationReason) {
        return getCancellationReasonTypesByCancellationReason(cancellationReason, EntityPermission.READ_ONLY);
    }
    
    public List<CancellationReasonType> getCancellationReasonTypesByCancellationReasonForUpdate(CancellationReason cancellationReason) {
        return getCancellationReasonTypesByCancellationReason(cancellationReason, EntityPermission.READ_WRITE);
    }
    
    private List<CancellationReasonType> getCancellationReasonTypesByCancellationType(CancellationType cancellationType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(CancellationReasonTypes.fields())
                    .from(CancellationReasonTypes)
                    .join(CancellationReasons).onKey(CANCELLATION_REASON_TYPES_CANCELLATION_REASON_FK)
                    .join(CancellationReasonDetails).onKey(CANCELLATION_REASONS_LAST_DETAIL_FK)
                    .where(CancellationReasonTypes.CANCELLATION_TYPE.eq(cancellationType.getPrimaryKey()),
                            CancellationReasonTypes.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(CancellationReasonDetails.SORT_ORDER, CancellationReasonDetails.CANCELLATION_REASON_NAME),
                    CancellationReasonTypeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(CancellationReasonTypes.fields())
                    .from(CancellationReasonTypes)
                    .where(CancellationReasonTypes.CANCELLATION_TYPE.eq(cancellationType.getPrimaryKey()),
                            CancellationReasonTypes.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return cancellationReasonTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<CancellationReasonType> getCancellationReasonTypesByCancellationType(CancellationType cancellationType) {
        return getCancellationReasonTypesByCancellationType(cancellationType, EntityPermission.READ_ONLY);
    }
    
    public List<CancellationReasonType> getCancellationReasonTypesByCancellationTypeForUpdate(CancellationType cancellationType) {
        return getCancellationReasonTypesByCancellationType(cancellationType, EntityPermission.READ_WRITE);
    }
    
    public List<CancellationReasonTypeTransfer> getCancellationReasonTypeTransfers(UserVisit userVisit, Collection<CancellationReasonType> cancellationReasonTypes) {
        List<CancellationReasonTypeTransfer> cancellationReasonTypeTransfers = new ArrayList<>(cancellationReasonTypes.size());
        
        cancellationReasonTypes.forEach((cancellationReasonType) ->
                cancellationReasonTypeTransfers.add(cancellationReasonTypeTransferCache.getCancellationReasonTypeTransfer(userVisit, cancellationReasonType))
        );
        
        return cancellationReasonTypeTransfers;
    }
    
    public List<CancellationReasonTypeTransfer> getCancellationReasonTypeTransfersByCancellationReason(UserVisit userVisit, CancellationReason cancellationReason) {
        return getCancellationReasonTypeTransfers(userVisit, getCancellationReasonTypesByCancellationReason(cancellationReason));
    }
    
    public List<CancellationReasonTypeTransfer> getCancellationReasonTypeTransfersByCancellationType(UserVisit userVisit, CancellationType cancellationType) {
        return getCancellationReasonTypeTransfers(userVisit, getCancellationReasonTypesByCancellationType(cancellationType));
    }
    
    public CancellationReasonTypeTransfer getCancellationReasonTypeTransfer(UserVisit userVisit, CancellationReasonType cancellationReasonType) {
        return cancellationReasonTypeTransferCache.getCancellationReasonTypeTransfer(userVisit, cancellationReasonType);
    }
    
    private void updateCancellationReasonTypeFromValue(CancellationReasonTypeValue cancellationReasonTypeValue, boolean checkDefault, BasePK updatedBy) {
        if(cancellationReasonTypeValue.hasBeenModified()) {
            var cancellationReasonType = cancellationReasonTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     cancellationReasonTypeValue.getPrimaryKey());
            
            cancellationReasonType.setThruTime(session.getStartTime());
            cancellationReasonType.store();

            var cancellationReason = cancellationReasonType.getCancellationReason(); // Not Updated
            var cancellationReasonPK = cancellationReason.getPrimaryKey(); // Not Updated
            var cancellationTypePK = cancellationReasonType.getCancellationTypePK(); // Not Updated
            var isDefault = cancellationReasonTypeValue.getIsDefault();
            var sortOrder = cancellationReasonTypeValue.getSortOrder();
            
            if(checkDefault) {
                var defaultCancellationReasonType = getDefaultCancellationReasonType(cancellationReason);
                var defaultFound = defaultCancellationReasonType != null && !defaultCancellationReasonType.equals(cancellationReasonType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultCancellationReasonTypeValue = getDefaultCancellationReasonTypeValueForUpdate(cancellationReason);
                    
                    defaultCancellationReasonTypeValue.setIsDefault(false);
                    updateCancellationReasonTypeFromValue(defaultCancellationReasonTypeValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            cancellationReasonType = cancellationReasonTypeFactory.create(cancellationReasonPK, cancellationTypePK,
                    isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(cancellationReasonPK, EventTypes.MODIFY, cancellationReasonType.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void updateCancellationReasonTypeFromValue(CancellationReasonTypeValue cancellationReasonTypeValue, BasePK updatedBy) {
        updateCancellationReasonTypeFromValue(cancellationReasonTypeValue, true, updatedBy);
    }
    
    public void deleteCancellationReasonType(CancellationReasonType cancellationReasonType, BasePK deletedBy) {
        cancellationReasonType.setThruTime(session.getStartTime());
        cancellationReasonType.store();
        
        // Check for default, and pick one if necessary
        var cancellationReason = cancellationReasonType.getCancellationReason();
        var defaultCancellationReasonType = getDefaultCancellationReasonType(cancellationReason);
        if(defaultCancellationReasonType == null) {
            var cancellationReasonTypes = getCancellationReasonTypesByCancellationReasonForUpdate(cancellationReason);
            
            if(!cancellationReasonTypes.isEmpty()) {
                var iter = cancellationReasonTypes.iterator();
                if(iter.hasNext()) {
                    defaultCancellationReasonType = iter.next();
                }
                var cancellationReasonTypeValue = defaultCancellationReasonType.getCancellationReasonTypeValue().clone();
                
                cancellationReasonTypeValue.setIsDefault(true);
                updateCancellationReasonTypeFromValue(cancellationReasonTypeValue, false, deletedBy);
            }
        }
        
        sendEvent(cancellationReason.getPrimaryKey(), EventTypes.MODIFY, cancellationReasonType.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteCancellationReasonTypes(List<CancellationReasonType> cancellationReasonTypes, BasePK deletedBy) {
        cancellationReasonTypes.forEach((cancellationReasonType) -> 
                deleteCancellationReasonType(cancellationReasonType, deletedBy)
        );
    }
    
    public void deleteCancellationReasonTypesByCancellationReason(CancellationReason cancellationReason, BasePK deletedBy) {
        deleteCancellationReasonTypes(getCancellationReasonTypesByCancellationReasonForUpdate(cancellationReason), deletedBy);
    }
    
    public void deleteCancellationReasonTypesByCancellationType(CancellationType cancellationType, BasePK deletedBy) {
        deleteCancellationReasonTypes(getCancellationReasonTypesByCancellationTypeForUpdate(cancellationType), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Cancellation Types
    // --------------------------------------------------------------------------------

    @Inject
    protected CancellationTypeDetailFactory cancellationTypeDetailFactory;

    @Inject
    protected CancellationTypeFactory cancellationTypeFactory;

    @Inject
    CancellationTypeTransferCache cancellationTypeTransferCache;

    public CancellationType createCancellationType(CancellationKind cancellationKind, String cancellationTypeName, Sequence cancellationSequence, Boolean isDefault,
            Integer sortOrder, BasePK createdBy) {
        var defaultCancellationType = getDefaultCancellationType(cancellationKind);
        var defaultFound = defaultCancellationType != null;
        
        if(defaultFound && isDefault) {
            var defaultCancellationTypeDetailValue = getDefaultCancellationTypeDetailValueForUpdate(cancellationKind);
            
            defaultCancellationTypeDetailValue.setIsDefault(false);
            updateCancellationTypeFromValue(defaultCancellationTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var cancellationType = cancellationTypeFactory.create();
        var cancellationTypeDetail = cancellationTypeDetailFactory.create(
                cancellationType, cancellationKind, cancellationTypeName, cancellationSequence, isDefault, sortOrder, session.getStartTime(),
                Session.MAX_TIME);
        
        // Convert to R/W
        cancellationType = cancellationTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                cancellationType.getPrimaryKey());
        cancellationType.setActiveDetail(cancellationTypeDetail);
        cancellationType.setLastDetail(cancellationTypeDetail);
        cancellationType.store();
        
        sendEvent(cancellationType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return cancellationType;
    }

    public long countCancellationTypesByCancellationKind(final CancellationKind cancellationKind) {
        return session.getDslContext()
                .selectCount()
                .from(CancellationTypes)
                .join(CancellationTypeDetails).onKey(CANCELLATION_TYPES_ACTIVE_DETAIL_FK)
                .where(CancellationTypeDetails.CANCELLATION_KIND.eq(cancellationKind.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countCancellationTypesByCancellationSequence(final Sequence cancellationSequence) {
        return session.getDslContext()
                .selectCount()
                .from(CancellationTypes)
                .join(CancellationTypeDetails).onKey(CANCELLATION_TYPES_ACTIVE_DETAIL_FK)
                .where(CancellationTypeDetails.CANCELLATION_SEQUENCE.eq(cancellationSequence.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.CancellationType */
    public CancellationType getCancellationTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new CancellationTypePK(entityInstance.getEntityUniqueId());

        return cancellationTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public CancellationType getCancellationTypeByEntityInstance(EntityInstance entityInstance) {
        return getCancellationTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public CancellationType getCancellationTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getCancellationTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    private List<CancellationType> getCancellationTypes(CancellationKind cancellationKind, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CancellationTypes.fields())
                .from(CancellationTypes)
                .join(CancellationTypeDetails).onKey(CANCELLATION_TYPES_ACTIVE_DETAIL_FK)
                .where(CancellationTypeDetails.CANCELLATION_KIND.eq(cancellationKind.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(CancellationTypeDetails.SORT_ORDER, CancellationTypeDetails.CANCELLATION_TYPE_NAME),
                    CancellationTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return cancellationTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<CancellationType> getCancellationTypes(CancellationKind cancellationKind) {
        return getCancellationTypes(cancellationKind, EntityPermission.READ_ONLY);
    }
    
    public List<CancellationType> getCancellationTypesForUpdate(CancellationKind cancellationKind) {
        return getCancellationTypes(cancellationKind, EntityPermission.READ_WRITE);
    }
    
    private CancellationType getDefaultCancellationType(CancellationKind cancellationKind, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CancellationTypes.fields())
                .from(CancellationTypes)
                .join(CancellationTypeDetails).onKey(CANCELLATION_TYPES_ACTIVE_DETAIL_FK)
                .where(CancellationTypeDetails.CANCELLATION_KIND.eq(cancellationKind.getPrimaryKey()),
                        CancellationTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return cancellationTypeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public CancellationType getDefaultCancellationType(CancellationKind cancellationKind) {
        return getDefaultCancellationType(cancellationKind, EntityPermission.READ_ONLY);
    }
    
    public CancellationType getDefaultCancellationTypeForUpdate(CancellationKind cancellationKind) {
        return getDefaultCancellationType(cancellationKind, EntityPermission.READ_WRITE);
    }
    
    public CancellationTypeDetailValue getDefaultCancellationTypeDetailValueForUpdate(CancellationKind cancellationKind) {
        return getDefaultCancellationTypeForUpdate(cancellationKind).getLastDetailForUpdate().getCancellationTypeDetailValue().clone();
    }
    
    public CancellationType getCancellationTypeByName(CancellationKind cancellationKind, String cancellationTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CancellationTypes.fields())
                .from(CancellationTypes)
                .join(CancellationTypeDetails).onKey(CANCELLATION_TYPES_ACTIVE_DETAIL_FK)
                .where(CancellationTypeDetails.CANCELLATION_KIND.eq(cancellationKind.getPrimaryKey()),
                        CancellationTypeDetails.CANCELLATION_TYPE_NAME.eq(cancellationTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return cancellationTypeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public CancellationType getCancellationTypeByName(CancellationKind cancellationKind, String cancellationTypeName) {
        return getCancellationTypeByName(cancellationKind, cancellationTypeName, EntityPermission.READ_ONLY);
    }
    
    public CancellationType getCancellationTypeByNameForUpdate(CancellationKind cancellationKind, String cancellationTypeName) {
        return getCancellationTypeByName(cancellationKind, cancellationTypeName, EntityPermission.READ_WRITE);
    }
    
    public CancellationTypeDetailValue getCancellationTypeDetailValueForUpdate(CancellationType cancellationType) {
        return cancellationType == null? null: cancellationType.getLastDetailForUpdate().getCancellationTypeDetailValue().clone();
    }
    
    public CancellationTypeDetailValue getCancellationTypeDetailValueByNameForUpdate(CancellationKind cancellationKind, String cancellationTypeName) {
        return getCancellationTypeDetailValueForUpdate(getCancellationTypeByNameForUpdate(cancellationKind, cancellationTypeName));
    }
    
    public CancellationTypeChoicesBean getCancellationTypeChoices(String defaultCancellationTypeChoice, Language language,
            boolean allowNullChoice, CancellationKind cancellationKind) {
        var cancellationTypes = getCancellationTypes(cancellationKind);
        var size = cancellationTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultCancellationTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var cancellationType : cancellationTypes) {
            var cancellationTypeDetail = cancellationType.getLastDetail();
            var label = getBestCancellationTypeDescription(cancellationType, language);
            var value = cancellationTypeDetail.getCancellationTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultCancellationTypeChoice != null && defaultCancellationTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && cancellationTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new CancellationTypeChoicesBean(labels, values, defaultValue);
    }
    
    public CancellationTypeTransfer getCancellationTypeTransfer(UserVisit userVisit, CancellationType cancellationType) {
        return cancellationTypeTransferCache.getCancellationTypeTransfer(userVisit, cancellationType);
    }
    
    public List<CancellationTypeTransfer> getCancellationTypeTransfersByCancellationKind(UserVisit userVisit, CancellationKind cancellationKind) {
        var cancellationTypes = getCancellationTypes(cancellationKind);
        List<CancellationTypeTransfer> cancellationTypeTransfers = new ArrayList<>(cancellationTypes.size());
        
        cancellationTypes.forEach((cancellationType) ->
                cancellationTypeTransfers.add(cancellationTypeTransferCache.getCancellationTypeTransfer(userVisit, cancellationType))
        );
        
        return cancellationTypeTransfers;
    }
    
    private void updateCancellationTypeFromValue(CancellationTypeDetailValue cancellationTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(cancellationTypeDetailValue.hasBeenModified()) {
            var cancellationType = cancellationTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     cancellationTypeDetailValue.getCancellationTypePK());
            var cancellationTypeDetail = cancellationType.getActiveDetailForUpdate();
            
            cancellationTypeDetail.setThruTime(session.getStartTime());
            cancellationTypeDetail.store();

            var cancellationTypePK = cancellationTypeDetail.getCancellationTypePK();
            var cancellationKind = cancellationTypeDetail.getCancellationKind();
            var cancellationKindPK = cancellationKind.getPrimaryKey();
            var cancellationTypeName = cancellationTypeDetailValue.getCancellationTypeName();
            var cancellationSequencePK = cancellationTypeDetailValue.getCancellationSequencePK();
            var isDefault = cancellationTypeDetailValue.getIsDefault();
            var sortOrder = cancellationTypeDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultCancellationType = getDefaultCancellationType(cancellationKind);
                var defaultFound = defaultCancellationType != null && !defaultCancellationType.equals(cancellationType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultCancellationTypeDetailValue = getDefaultCancellationTypeDetailValueForUpdate(cancellationKind);
                    
                    defaultCancellationTypeDetailValue.setIsDefault(false);
                    updateCancellationTypeFromValue(defaultCancellationTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            cancellationTypeDetail = cancellationTypeDetailFactory.create(cancellationTypePK, cancellationKindPK, cancellationTypeName,
                    cancellationSequencePK, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            cancellationType.setActiveDetail(cancellationTypeDetail);
            cancellationType.setLastDetail(cancellationTypeDetail);
            
            sendEvent(cancellationTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateCancellationTypeFromValue(CancellationTypeDetailValue cancellationTypeDetailValue, BasePK updatedBy) {
        updateCancellationTypeFromValue(cancellationTypeDetailValue, true, updatedBy);
    }
    
    public void deleteCancellationType(CancellationType cancellationType, BasePK deletedBy) {
        deleteCancellationReasonTypesByCancellationType(cancellationType, deletedBy);
        deleteCancellationTypeDescriptionsByCancellationType(cancellationType, deletedBy);

        var cancellationTypeDetail = cancellationType.getLastDetailForUpdate();
        cancellationTypeDetail.setThruTime(session.getStartTime());
        cancellationType.setActiveDetail(null);
        cancellationType.store();
        
        // Check for default, and pick one if necessary
        var cancellationKind = cancellationTypeDetail.getCancellationKind();
        var defaultCancellationType = getDefaultCancellationType(cancellationKind);
        if(defaultCancellationType == null) {
            var cancellationTypes = getCancellationTypesForUpdate(cancellationKind);
            
            if(!cancellationTypes.isEmpty()) {
                var iter = cancellationTypes.iterator();
                if(iter.hasNext()) {
                    defaultCancellationType = iter.next();
                }
                var cancellationTypeDetailValue = Objects.requireNonNull(defaultCancellationType).getLastDetailForUpdate().getCancellationTypeDetailValue().clone();
                
                cancellationTypeDetailValue.setIsDefault(true);
                updateCancellationTypeFromValue(cancellationTypeDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(cancellationType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteCancellationTypesByCancellationKind(CancellationKind cancellationKind, BasePK deletedBy) {
        var cancellationTypes = getCancellationTypesForUpdate(cancellationKind);
        
        cancellationTypes.forEach((cancellationType) -> 
                deleteCancellationType(cancellationType, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Cancellation Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected CancellationTypeDescriptionFactory cancellationTypeDescriptionFactory;

    @Inject
    CancellationTypeDescriptionTransferCache cancellationTypeDescriptionTransferCache;

    public CancellationTypeDescription createCancellationTypeDescription(CancellationType cancellationType, Language language, String description,
            BasePK createdBy) {
        var cancellationTypeDescription = cancellationTypeDescriptionFactory.create(cancellationType,
                language, description, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(cancellationType.getPrimaryKey(), EventTypes.MODIFY, cancellationTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return cancellationTypeDescription;
    }
    
    public CancellationTypeDescription getCancellationTypeDescription(CancellationType cancellationType, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CancellationTypeDescriptions.fields())
                .from(CancellationTypeDescriptions)
                .where(CancellationTypeDescriptions.CANCELLATION_TYPE.eq(cancellationType.getPrimaryKey()),
                        CancellationTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        CancellationTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return cancellationTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public CancellationTypeDescription getCancellationTypeDescription(CancellationType cancellationType, Language language) {
        return getCancellationTypeDescription(cancellationType, language, EntityPermission.READ_ONLY);
    }
    
    public CancellationTypeDescription getCancellationTypeDescriptionForUpdate(CancellationType cancellationType, Language language) {
        return getCancellationTypeDescription(cancellationType, language, EntityPermission.READ_WRITE);
    }
    
    public CancellationTypeDescriptionValue getCancellationTypeDescriptionValue(CancellationTypeDescription cancellationTypeDescription) {
        return cancellationTypeDescription == null? null: cancellationTypeDescription.getCancellationTypeDescriptionValue().clone();
    }
    
    public CancellationTypeDescriptionValue getCancellationTypeDescriptionValueForUpdate(CancellationType cancellationType, Language language) {
        return getCancellationTypeDescriptionValue(getCancellationTypeDescriptionForUpdate(cancellationType, language));
    }
    
    private List<CancellationTypeDescription> getCancellationTypeDescriptionsByCancellationType(CancellationType cancellationType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(CancellationTypeDescriptions.fields())
                    .from(CancellationTypeDescriptions)
                    .join(Languages).onKey(CANCELLATION_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(CancellationTypeDescriptions.CANCELLATION_TYPE.eq(cancellationType.getPrimaryKey()),
                            CancellationTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    CancellationTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(CancellationTypeDescriptions.fields())
                    .from(CancellationTypeDescriptions)
                    .where(CancellationTypeDescriptions.CANCELLATION_TYPE.eq(cancellationType.getPrimaryKey()),
                            CancellationTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return cancellationTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<CancellationTypeDescription> getCancellationTypeDescriptionsByCancellationType(CancellationType cancellationType) {
        return getCancellationTypeDescriptionsByCancellationType(cancellationType, EntityPermission.READ_ONLY);
    }
    
    public List<CancellationTypeDescription> getCancellationTypeDescriptionsByCancellationTypeForUpdate(CancellationType cancellationType) {
        return getCancellationTypeDescriptionsByCancellationType(cancellationType, EntityPermission.READ_WRITE);
    }
    
    public String getBestCancellationTypeDescription(CancellationType cancellationType, Language language) {
        String description;
        var cancellationTypeDescription = getCancellationTypeDescription(cancellationType, language);
        
        if(cancellationTypeDescription == null && !language.getIsDefault()) {
            cancellationTypeDescription = getCancellationTypeDescription(cancellationType, partyControl.getDefaultLanguage());
        }
        
        if(cancellationTypeDescription == null) {
            description = cancellationType.getLastDetail().getCancellationTypeName();
        } else {
            description = cancellationTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public CancellationTypeDescriptionTransfer getCancellationTypeDescriptionTransfer(UserVisit userVisit, CancellationTypeDescription cancellationTypeDescription) {
        return cancellationTypeDescriptionTransferCache.getCancellationTypeDescriptionTransfer(userVisit, cancellationTypeDescription);
    }
    
    public List<CancellationTypeDescriptionTransfer> getCancellationTypeDescriptionTransfersByCancellationType(UserVisit userVisit, CancellationType cancellationType) {
        var cancellationTypeDescriptions = getCancellationTypeDescriptionsByCancellationType(cancellationType);
        List<CancellationTypeDescriptionTransfer> cancellationTypeDescriptionTransfers = new ArrayList<>(cancellationTypeDescriptions.size());
        
        cancellationTypeDescriptions.forEach((cancellationTypeDescription) -> {
            cancellationTypeDescriptionTransfers.add(cancellationTypeDescriptionTransferCache.getCancellationTypeDescriptionTransfer(userVisit, cancellationTypeDescription));
        });
        
        return cancellationTypeDescriptionTransfers;
    }
    
    public void updateCancellationTypeDescriptionFromValue(CancellationTypeDescriptionValue cancellationTypeDescriptionValue, BasePK updatedBy) {
        if(cancellationTypeDescriptionValue.hasBeenModified()) {
            var cancellationTypeDescription = cancellationTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     cancellationTypeDescriptionValue.getPrimaryKey());
            
            cancellationTypeDescription.setThruTime(session.getStartTime());
            cancellationTypeDescription.store();

            var cancellationType = cancellationTypeDescription.getCancellationType();
            var language = cancellationTypeDescription.getLanguage();
            var description = cancellationTypeDescriptionValue.getDescription();
            
            cancellationTypeDescription = cancellationTypeDescriptionFactory.create(cancellationType, language, description,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(cancellationType.getPrimaryKey(), EventTypes.MODIFY, cancellationTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteCancellationTypeDescription(CancellationTypeDescription cancellationTypeDescription, BasePK deletedBy) {
        cancellationTypeDescription.setThruTime(session.getStartTime());
        
        sendEvent(cancellationTypeDescription.getCancellationTypePK(), EventTypes.MODIFY, cancellationTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteCancellationTypeDescriptionsByCancellationType(CancellationType cancellationType, BasePK deletedBy) {
        var cancellationTypeDescriptions = getCancellationTypeDescriptionsByCancellationTypeForUpdate(cancellationType);
        
        cancellationTypeDescriptions.forEach((cancellationTypeDescription) -> 
                deleteCancellationTypeDescription(cancellationTypeDescription, deletedBy)
        );
    }
    
}
