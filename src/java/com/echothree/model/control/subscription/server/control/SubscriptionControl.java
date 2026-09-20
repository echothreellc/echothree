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

package com.echothree.model.control.subscription.server.control;

import com.echothree.model.control.club.server.control.ClubControl;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.control.subscription.common.SubscriptionConstants;
import com.echothree.model.control.subscription.common.choice.SubscriptionKindChoicesBean;
import com.echothree.model.control.subscription.common.choice.SubscriptionTypeChoicesBean;
import com.echothree.model.control.subscription.common.transfer.SubscriptionKindDescriptionTransfer;
import com.echothree.model.control.subscription.common.transfer.SubscriptionKindTransfer;
import com.echothree.model.control.subscription.common.transfer.SubscriptionTransfer;
import com.echothree.model.control.subscription.common.transfer.SubscriptionTypeChainTransfer;
import com.echothree.model.control.subscription.common.transfer.SubscriptionTypeDescriptionTransfer;
import com.echothree.model.control.subscription.common.transfer.SubscriptionTypeTransfer;
import com.echothree.model.control.subscription.server.transfer.SubscriptionKindDescriptionTransferCache;
import com.echothree.model.control.subscription.server.transfer.SubscriptionKindTransferCache;
import com.echothree.model.control.subscription.server.transfer.SubscriptionTransferCache;
import com.echothree.model.control.subscription.server.transfer.SubscriptionTypeChainTransferCache;
import com.echothree.model.control.subscription.server.transfer.SubscriptionTypeDescriptionTransferCache;
import com.echothree.model.control.subscription.server.transfer.SubscriptionTypeTransferCache;
import com.echothree.model.data.chain.server.entity.Chain;
import com.echothree.model.data.chain.server.entity.ChainType;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.subscription.common.pk.SubscriptionKindPK;
import com.echothree.model.data.subscription.common.pk.SubscriptionPK;
import com.echothree.model.data.subscription.common.pk.SubscriptionTypePK;
import com.echothree.model.data.subscription.server.entity.Subscription;
import com.echothree.model.data.subscription.server.entity.SubscriptionKind;
import com.echothree.model.data.subscription.server.entity.SubscriptionKindDescription;
import com.echothree.model.data.subscription.server.entity.SubscriptionType;
import com.echothree.model.data.subscription.server.entity.SubscriptionTypeChain;
import com.echothree.model.data.subscription.server.entity.SubscriptionTypeDescription;
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
import static com.echothree.model.jooq.server.keys.chain.ChainForeignKeys.CHAINS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.subscription.SubscriptionForeignKeys.SUBSCRIPTIONS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.subscription.SubscriptionForeignKeys.SUBSCRIPTION_DETAILS_SUBSCRIPTION_TYPE_FK;
import static com.echothree.model.jooq.server.keys.subscription.SubscriptionForeignKeys.SUBSCRIPTION_KINDS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.subscription.SubscriptionForeignKeys.SUBSCRIPTION_KIND_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.subscription.SubscriptionForeignKeys.SUBSCRIPTION_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.subscription.SubscriptionForeignKeys.SUBSCRIPTION_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.subscription.SubscriptionForeignKeys.SUBSCRIPTION_TYPE_CHAINS_CHAIN_FK;
import static com.echothree.model.jooq.server.keys.subscription.SubscriptionForeignKeys.SUBSCRIPTION_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.tables.chain.ChainDetails.ChainDetails;
import static com.echothree.model.jooq.server.tables.chain.Chains.Chains;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import static com.echothree.model.jooq.server.tables.subscription.SubscriptionDetails.SubscriptionDetails;
import static com.echothree.model.jooq.server.tables.subscription.SubscriptionKindDescriptions.SubscriptionKindDescriptions;
import static com.echothree.model.jooq.server.tables.subscription.SubscriptionKindDetails.SubscriptionKindDetails;
import static com.echothree.model.jooq.server.tables.subscription.SubscriptionKinds.SubscriptionKinds;
import static com.echothree.model.jooq.server.tables.subscription.SubscriptionTypeChains.SubscriptionTypeChains;
import static com.echothree.model.jooq.server.tables.subscription.SubscriptionTypeDescriptions.SubscriptionTypeDescriptions;
import static com.echothree.model.jooq.server.tables.subscription.SubscriptionTypeDetails.SubscriptionTypeDetails;
import static com.echothree.model.jooq.server.tables.subscription.SubscriptionTypes.SubscriptionTypes;
import static com.echothree.model.jooq.server.tables.subscription.Subscriptions.Subscriptions;
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
public class SubscriptionControl
        extends BaseModelControl {
    
    @Inject
    protected ClubControl clubControl;

    @Inject
    protected SequenceControl sequenceControl;

    @Inject
    protected SequenceGeneratorLogic sequenceGeneratorLogic;

    /** Creates a new instance of SubscriptionControl */
    protected SubscriptionControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Subscription Kinds
    // --------------------------------------------------------------------------------

    @Inject
    protected SubscriptionKindDetailFactory subscriptionKindDetailFactory;

    @Inject
    protected SubscriptionKindFactory subscriptionKindFactory;

    @Inject
    private SubscriptionKindTransferCache subscriptionKindTransferCache;

    public SubscriptionKind createSubscriptionKind(String subscriptionKindName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultSubscriptionKind = getDefaultSubscriptionKind();
        var defaultFound = defaultSubscriptionKind != null;

        if(defaultFound && isDefault) {
            var defaultSubscriptionKindDetailValue = getDefaultSubscriptionKindDetailValueForUpdate();

            defaultSubscriptionKindDetailValue.setIsDefault(false);
            updateSubscriptionKindFromValue(defaultSubscriptionKindDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var subscriptionKind = subscriptionKindFactory.create();
        var subscriptionKindDetail = subscriptionKindDetailFactory.create(subscriptionKind, subscriptionKindName, isDefault, sortOrder,
                session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        subscriptionKind = subscriptionKindFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                subscriptionKind.getPrimaryKey());
        subscriptionKind.setActiveDetail(subscriptionKindDetail);
        subscriptionKind.setLastDetail(subscriptionKindDetail);
        subscriptionKind.store();

        sendEvent(subscriptionKind.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return subscriptionKind;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.SubscriptionKind */
    public SubscriptionKind getSubscriptionKindByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new SubscriptionKindPK(entityInstance.getEntityUniqueId());

        return subscriptionKindFactory.getEntityFromPK(entityPermission, pk);
    }

    public SubscriptionKind getSubscriptionKindByEntityInstance(EntityInstance entityInstance) {
        return getSubscriptionKindByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public SubscriptionKind getSubscriptionKindByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getSubscriptionKindByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countSubscriptionKinds() {
        return session.getDslContext()
                .selectCount()
                .from(SubscriptionKinds)
                .join(SubscriptionKindDetails).onKey(SUBSCRIPTION_KINDS_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public SubscriptionKind getSubscriptionKindByName(String subscriptionKindName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SubscriptionKinds.fields())
                .from(SubscriptionKinds)
                .join(SubscriptionKindDetails).onKey(SUBSCRIPTION_KINDS_ACTIVE_DETAIL_FK)
                .where(SubscriptionKindDetails.SUBSCRIPTION_KIND_NAME.eq(subscriptionKindName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return subscriptionKindFactory.getEntityFromQuery(entityPermission, query);
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

    public SubscriptionKind getDefaultSubscriptionKind(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SubscriptionKinds.fields())
                .from(SubscriptionKinds)
                .join(SubscriptionKindDetails).onKey(SUBSCRIPTION_KINDS_ACTIVE_DETAIL_FK)
                .where(SubscriptionKindDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return subscriptionKindFactory.getEntityFromQuery(entityPermission, query);
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

    public List<SubscriptionKind> getSubscriptionKinds(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SubscriptionKinds.fields())
                .from(SubscriptionKinds)
                .join(SubscriptionKindDetails).onKey(SUBSCRIPTION_KINDS_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(SubscriptionKindDetails.SORT_ORDER, SubscriptionKindDetails.SUBSCRIPTION_KIND_NAME),
                    SubscriptionKindFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return subscriptionKindFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<SubscriptionKind> getSubscriptionKinds() {
        return getSubscriptionKinds(EntityPermission.READ_ONLY);
    }

    public List<SubscriptionKind> getSubscriptionKindsForUpdate() {
        return getSubscriptionKinds(EntityPermission.READ_WRITE);
    }

    public SubscriptionKindChoicesBean getSubscriptionKindChoices(String defaultSubscriptionKindChoice, Language language, boolean allowNullChoice) {
        var subscriptionKinds = getSubscriptionKinds();
        var size = subscriptionKinds.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultSubscriptionKindChoice == null) {
                defaultValue = "";
            }
        }

        for(var subscriptionKind : subscriptionKinds) {
            var subscriptionKindDetail = subscriptionKind.getLastDetail();

            var label = getBestSubscriptionKindDescription(subscriptionKind, language);
            var value = subscriptionKindDetail.getSubscriptionKindName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultSubscriptionKindChoice != null && defaultSubscriptionKindChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && subscriptionKindDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new SubscriptionKindChoicesBean(labels, values, defaultValue);
    }

    public SubscriptionKindTransfer getSubscriptionKindTransfer(UserVisit userVisit, SubscriptionKind subscriptionKind) {
        return subscriptionKindTransferCache.getSubscriptionKindTransfer(userVisit, subscriptionKind);
    }

    public List<SubscriptionKindTransfer> getSubscriptionKindTransfers(UserVisit userVisit, Collection<SubscriptionKind> subscriptionKinds) {
        var subscriptionKindTransfers = new ArrayList<SubscriptionKindTransfer>(subscriptionKinds.size());

        subscriptionKinds.forEach((subscriptionKind) ->
                subscriptionKindTransfers.add(subscriptionKindTransferCache.getSubscriptionKindTransfer(userVisit, subscriptionKind))
        );

        return subscriptionKindTransfers;
    }

    public List<SubscriptionKindTransfer> getSubscriptionKindTransfers(UserVisit userVisit) {
        return getSubscriptionKindTransfers(userVisit, getSubscriptionKinds());
    }

    private void updateSubscriptionKindFromValue(SubscriptionKindDetailValue subscriptionKindDetailValue, boolean checkDefault, BasePK updatedBy) {
        var subscriptionKind = subscriptionKindFactory.getEntityFromPK(
                EntityPermission.READ_WRITE, subscriptionKindDetailValue.getSubscriptionKindPK());
        var subscriptionKindDetail = subscriptionKind.getActiveDetailForUpdate();

        subscriptionKindDetail.setThruTime(session.getStartTime());
        subscriptionKindDetail.store();

        var subscriptionKindPK = subscriptionKindDetail.getSubscriptionKindPK();
        var subscriptionKindName = subscriptionKindDetailValue.getSubscriptionKindName();
        var isDefault = subscriptionKindDetailValue.getIsDefault();
        var sortOrder = subscriptionKindDetailValue.getSortOrder();

        if(checkDefault) {
            var defaultSubscriptionKind = getDefaultSubscriptionKind();
            var defaultFound = defaultSubscriptionKind != null && !defaultSubscriptionKind.equals(subscriptionKind);

            if(isDefault && defaultFound) {
                // If I'm the default, and a default already existed...
                var defaultSubscriptionKindDetailValue = getDefaultSubscriptionKindDetailValueForUpdate();

                defaultSubscriptionKindDetailValue.setIsDefault(false);
                updateSubscriptionKindFromValue(defaultSubscriptionKindDetailValue, false, updatedBy);
            } else if(!isDefault && !defaultFound) {
                // If I'm not the default, and no other default exists...
                isDefault = true;
            }
        }

        subscriptionKindDetail = subscriptionKindDetailFactory.create(subscriptionKindPK, subscriptionKindName, isDefault, sortOrder, session.getStartTime(),
                Session.MAX_TIME);

        subscriptionKind.setActiveDetail(subscriptionKindDetail);
        subscriptionKind.setLastDetail(subscriptionKindDetail);
        subscriptionKind.store();

        sendEvent(subscriptionKindPK, EventTypes.MODIFY, null, null, updatedBy);
    }

    public void updateSubscriptionKindFromValue(SubscriptionKindDetailValue subscriptionKindDetailValue, BasePK updatedBy) {
        updateSubscriptionKindFromValue(subscriptionKindDetailValue, true, updatedBy);
    }

    public void deleteSubscriptionKind(SubscriptionKind subscriptionKind, BasePK deletedBy) {
        deleteSubscriptionKindDescriptionsBySubscriptionKind(subscriptionKind, deletedBy);

        var subscriptionKindDetail = subscriptionKind.getLastDetailForUpdate();
        subscriptionKindDetail.setThruTime(session.getStartTime());
        subscriptionKind.setActiveDetail(null);
        subscriptionKind.store();

        // Check for default, and pick one if necessary
        var defaultSubscriptionKind = getDefaultSubscriptionKind();
        if(defaultSubscriptionKind == null) {
            var subscriptionKinds = getSubscriptionKindsForUpdate();

            if(!subscriptionKinds.isEmpty()) {
                var iter = subscriptionKinds.iterator();
                if(iter.hasNext()) {
                    defaultSubscriptionKind = iter.next();
                }
                var subscriptionKindDetailValue = Objects.requireNonNull(defaultSubscriptionKind).getLastDetailForUpdate().getSubscriptionKindDetailValue().clone();

                subscriptionKindDetailValue.setIsDefault(true);
                updateSubscriptionKindFromValue(subscriptionKindDetailValue, false, deletedBy);
            }
        }

        sendEvent(subscriptionKind.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Subscription Kind Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected SubscriptionKindDescriptionFactory subscriptionKindDescriptionFactory;

    @Inject
    private SubscriptionKindDescriptionTransferCache subscriptionKindDescriptionTransferCache;

    public SubscriptionKindDescription createSubscriptionKindDescription(SubscriptionKind subscriptionKind, Language language, String description,
            BasePK createdBy) {
        var subscriptionKindDescription = subscriptionKindDescriptionFactory.create(subscriptionKind,
                language, description, session.getStartTime(), Session.MAX_TIME);

        sendEvent(subscriptionKind.getPrimaryKey(), EventTypes.MODIFY, subscriptionKindDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return subscriptionKindDescription;
    }

    private SubscriptionKindDescription getSubscriptionKindDescription(SubscriptionKind subscriptionKind, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SubscriptionKindDescriptions.fields())
                .from(SubscriptionKindDescriptions)
                .where(SubscriptionKindDescriptions.SUBSCRIPTION_KIND.eq(subscriptionKind.getPrimaryKey()),
                        SubscriptionKindDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        SubscriptionKindDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return subscriptionKindDescriptionFactory.getEntityFromQuery(entityPermission, query);
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

    private List<SubscriptionKindDescription> getSubscriptionKindDescriptionsBySubscriptionKind(SubscriptionKind subscriptionKind, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(SubscriptionKindDescriptions.fields())
                    .from(SubscriptionKindDescriptions)
                    .join(Languages).onKey(SUBSCRIPTION_KIND_DESCRIPTIONS_LANGUAGE_FK)
                    .where(SubscriptionKindDescriptions.SUBSCRIPTION_KIND.eq(subscriptionKind.getPrimaryKey()),
                            SubscriptionKindDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    SubscriptionKindDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(SubscriptionKindDescriptions.fields())
                    .from(SubscriptionKindDescriptions)
                    .where(SubscriptionKindDescriptions.SUBSCRIPTION_KIND.eq(subscriptionKind.getPrimaryKey()),
                            SubscriptionKindDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return subscriptionKindDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<SubscriptionKindDescription> getSubscriptionKindDescriptionsBySubscriptionKind(SubscriptionKind subscriptionKind) {
        return getSubscriptionKindDescriptionsBySubscriptionKind(subscriptionKind, EntityPermission.READ_ONLY);
    }

    public List<SubscriptionKindDescription> getSubscriptionKindDescriptionsBySubscriptionKindForUpdate(SubscriptionKind subscriptionKind) {
        return getSubscriptionKindDescriptionsBySubscriptionKind(subscriptionKind, EntityPermission.READ_WRITE);
    }

    public String getBestSubscriptionKindDescription(SubscriptionKind subscriptionKind, Language language) {
        String description;
        var subscriptionKindDescription = getSubscriptionKindDescription(subscriptionKind, language);

        if(subscriptionKindDescription == null && !language.getIsDefault()) {
            subscriptionKindDescription = getSubscriptionKindDescription(subscriptionKind, partyControl.getDefaultLanguage());
        }

        if(subscriptionKindDescription == null) {
            description = subscriptionKind.getLastDetail().getSubscriptionKindName();
        } else {
            description = subscriptionKindDescription.getDescription();
        }

        return description;
    }

    public SubscriptionKindDescriptionTransfer getSubscriptionKindDescriptionTransfer(UserVisit userVisit, SubscriptionKindDescription subscriptionKindDescription) {
        return subscriptionKindDescriptionTransferCache.getSubscriptionKindDescriptionTransfer(userVisit, subscriptionKindDescription);
    }

    public List<SubscriptionKindDescriptionTransfer> getSubscriptionKindDescriptionTransfersBySubscriptionKind(UserVisit userVisit, SubscriptionKind subscriptionKind) {
        var subscriptionKindDescriptions = getSubscriptionKindDescriptionsBySubscriptionKind(subscriptionKind);
        List<SubscriptionKindDescriptionTransfer> subscriptionKindDescriptionTransfers = new ArrayList<>(subscriptionKindDescriptions.size());

        subscriptionKindDescriptions.forEach((subscriptionKindDescription) -> {
            subscriptionKindDescriptionTransfers.add(subscriptionKindDescriptionTransferCache.getSubscriptionKindDescriptionTransfer(userVisit, subscriptionKindDescription));
        });

        return subscriptionKindDescriptionTransfers;
    }

    public void updateSubscriptionKindDescriptionFromValue(SubscriptionKindDescriptionValue subscriptionKindDescriptionValue, BasePK updatedBy) {
        if(subscriptionKindDescriptionValue.hasBeenModified()) {
            var subscriptionKindDescription = subscriptionKindDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     subscriptionKindDescriptionValue.getPrimaryKey());

            subscriptionKindDescription.setThruTime(session.getStartTime());
            subscriptionKindDescription.store();

            var subscriptionKind = subscriptionKindDescription.getSubscriptionKind();
            var language = subscriptionKindDescription.getLanguage();
            var description = subscriptionKindDescriptionValue.getDescription();

            subscriptionKindDescription = subscriptionKindDescriptionFactory.create(subscriptionKind, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(subscriptionKind.getPrimaryKey(), EventTypes.MODIFY, subscriptionKindDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteSubscriptionKindDescription(SubscriptionKindDescription subscriptionKindDescription, BasePK deletedBy) {
        subscriptionKindDescription.setThruTime(session.getStartTime());

        sendEvent(subscriptionKindDescription.getSubscriptionKindPK(), EventTypes.MODIFY, subscriptionKindDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteSubscriptionKindDescriptionsBySubscriptionKind(SubscriptionKind subscriptionKind, BasePK deletedBy) {
        var subscriptionKindDescriptions = getSubscriptionKindDescriptionsBySubscriptionKindForUpdate(subscriptionKind);

        subscriptionKindDescriptions.forEach((subscriptionKindDescription) -> 
                deleteSubscriptionKindDescription(subscriptionKindDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Subscription Types
    // --------------------------------------------------------------------------------

    @Inject
    protected SubscriptionTypeDetailFactory subscriptionTypeDetailFactory;

    @Inject
    protected SubscriptionTypeFactory subscriptionTypeFactory;

    @Inject
    private SubscriptionTypeTransferCache subscriptionTypeTransferCache;

    public SubscriptionType createSubscriptionType(SubscriptionKind subscriptionKind, String subscriptionTypeName,
            Sequence subscriptionSequence, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultSubscriptionType = getDefaultSubscriptionType(subscriptionKind);
        var defaultFound = defaultSubscriptionType != null;
        
        if(defaultFound && isDefault) {
            var defaultSubscriptionTypeDetailValue = getDefaultSubscriptionTypeDetailValueForUpdate(subscriptionKind);
            
            defaultSubscriptionTypeDetailValue.setIsDefault(false);
            updateSubscriptionTypeFromValue(defaultSubscriptionTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var subscriptionType = subscriptionTypeFactory.create();
        var subscriptionTypeDetail = subscriptionTypeDetailFactory.create(
                subscriptionType, subscriptionKind, subscriptionTypeName, subscriptionSequence, isDefault, sortOrder,
                session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        subscriptionType = subscriptionTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                subscriptionType.getPrimaryKey());
        subscriptionType.setActiveDetail(subscriptionTypeDetail);
        subscriptionType.setLastDetail(subscriptionTypeDetail);
        subscriptionType.store();
        
        sendEvent(subscriptionType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return subscriptionType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.SubscriptionType */
    public SubscriptionType getSubscriptionTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new SubscriptionTypePK(entityInstance.getEntityUniqueId());

        return subscriptionTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public SubscriptionType getSubscriptionTypeByEntityInstance(EntityInstance entityInstance) {
        return getSubscriptionTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public SubscriptionType getSubscriptionTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getSubscriptionTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countSubscriptionTypesBySubscriptionKind(final SubscriptionKind subscriptionKind) {
        return session.getDslContext()
                .selectCount()
                .from(SubscriptionTypes)
                .join(SubscriptionTypeDetails).onKey(SUBSCRIPTION_TYPES_ACTIVE_DETAIL_FK)
                .where(SubscriptionTypeDetails.SUBSCRIPTION_KIND.eq(subscriptionKind.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countSubscriptionTypesBySubscriptionSequence(final Sequence subscriptionSequence) {
        return session.getDslContext()
                .selectCount()
                .from(SubscriptionTypes)
                .join(SubscriptionTypeDetails).onKey(SUBSCRIPTION_TYPES_ACTIVE_DETAIL_FK)
                .where(SubscriptionTypeDetails.SUBSCRIPTION_SEQUENCE.eq(subscriptionSequence.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<SubscriptionType> getSubscriptionTypesBySubscriptionKind(SubscriptionKind subscriptionKind,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SubscriptionTypes.fields())
                .from(SubscriptionTypes)
                .join(SubscriptionTypeDetails).onKey(SUBSCRIPTION_TYPES_ACTIVE_DETAIL_FK)
                .where(SubscriptionTypeDetails.SUBSCRIPTION_KIND.eq(subscriptionKind.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(SubscriptionTypeDetails.SORT_ORDER, SubscriptionTypeDetails.SUBSCRIPTION_TYPE_NAME),
                    SubscriptionTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return subscriptionTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<SubscriptionType> getSubscriptionTypesBySubscriptionKind(SubscriptionKind subscriptionKind) {
        return getSubscriptionTypesBySubscriptionKind(subscriptionKind, EntityPermission.READ_ONLY);
    }
    
    public List<SubscriptionType> getSubscriptionTypesBySubscriptionKindForUpdate(SubscriptionKind subscriptionKind) {
        return getSubscriptionTypesBySubscriptionKind(subscriptionKind, EntityPermission.READ_WRITE);
    }
    
    public SubscriptionType getDefaultSubscriptionType(SubscriptionKind subscriptionKind, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SubscriptionTypes.fields())
                .from(SubscriptionTypes)
                .join(SubscriptionTypeDetails).onKey(SUBSCRIPTION_TYPES_ACTIVE_DETAIL_FK)
                .where(SubscriptionTypeDetails.SUBSCRIPTION_KIND.eq(subscriptionKind.getPrimaryKey()),
                        SubscriptionTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return subscriptionTypeFactory.getEntityFromQuery(entityPermission, query);
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
    
    public SubscriptionType getSubscriptionTypeByName(SubscriptionKind subscriptionKind, String subscriptionTypeName,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SubscriptionTypes.fields())
                .from(SubscriptionTypes)
                .join(SubscriptionTypeDetails).onKey(SUBSCRIPTION_TYPES_ACTIVE_DETAIL_FK)
                .where(SubscriptionTypeDetails.SUBSCRIPTION_KIND.eq(subscriptionKind.getPrimaryKey()),
                        SubscriptionTypeDetails.SUBSCRIPTION_TYPE_NAME.eq(subscriptionTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return subscriptionTypeFactory.getEntityFromQuery(entityPermission, query);
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
        var subscriptionTypes = getSubscriptionTypesBySubscriptionKind(subscriptionKind);
        var size = subscriptionTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
        }
        
        for(var subscriptionType : subscriptionTypes) {
            var subscriptionTypeDetail = subscriptionType.getLastDetail();
            var label = getBestSubscriptionTypeDescription(subscriptionType, language);
            var value = subscriptionTypeDetail.getSubscriptionTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultSubscriptionTypeChoice != null && defaultSubscriptionTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && subscriptionTypeDetail.getIsDefault()))
                defaultValue = value;
        }
        
        return new SubscriptionTypeChoicesBean(labels, values, defaultValue);
    }
    
    public SubscriptionTypeTransfer getSubscriptionTypeTransfer(UserVisit userVisit,
            SubscriptionType subscriptionType) {
        return subscriptionTypeTransferCache.getSubscriptionTypeTransfer(userVisit, subscriptionType);
    }
    
    public List<SubscriptionTypeTransfer> getSubscriptionTypeTransfersBySubscriptionKind(UserVisit userVisit, SubscriptionKind subscriptionKind) {
        var subscriptionTypes = getSubscriptionTypesBySubscriptionKind(subscriptionKind);
        List<SubscriptionTypeTransfer> subscriptionTypeTransfers = new ArrayList<>(subscriptionTypes.size());
        
        subscriptionTypes.forEach((subscriptionType) ->
                subscriptionTypeTransfers.add(subscriptionTypeTransferCache.getSubscriptionTypeTransfer(userVisit, subscriptionType))
        );
        
        return subscriptionTypeTransfers;
    }
    
    private void updateSubscriptionTypeFromValue(SubscriptionTypeDetailValue subscriptionTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(subscriptionTypeDetailValue.hasBeenModified()) {
            var subscriptionType = subscriptionTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     subscriptionTypeDetailValue.getSubscriptionTypePK());
            var subscriptionTypeDetail = subscriptionType.getActiveDetailForUpdate();
            
            subscriptionTypeDetail.setThruTime(session.getStartTime());
            subscriptionTypeDetail.store();

            var subscriptionTypePK = subscriptionTypeDetail.getSubscriptionTypePK(); // Not updated
            var subscriptionKind = subscriptionTypeDetail.getSubscriptionKind();
            var subscriptionKindPK = subscriptionKind.getPrimaryKey(); // Not updated
            var subscriptionTypeName = subscriptionTypeDetailValue.getSubscriptionTypeName();
            var subscriptionSequencePK = subscriptionTypeDetailValue.getSubscriptionSequencePK();
            var isDefault = subscriptionTypeDetailValue.getIsDefault();
            var sortOrder = subscriptionTypeDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultSubscriptionType = getDefaultSubscriptionType(subscriptionKind);
                var defaultFound = defaultSubscriptionType != null && !defaultSubscriptionType.equals(subscriptionType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultSubscriptionTypeDetailValue = getDefaultSubscriptionTypeDetailValueForUpdate(subscriptionKind);
                    
                    defaultSubscriptionTypeDetailValue.setIsDefault(false);
                    updateSubscriptionTypeFromValue(defaultSubscriptionTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            subscriptionTypeDetail = subscriptionTypeDetailFactory.create(subscriptionTypePK,
                    subscriptionKindPK, subscriptionTypeName, subscriptionSequencePK, isDefault, sortOrder, session.getStartTime(),
                    Session.MAX_TIME);
            
            subscriptionType.setActiveDetail(subscriptionTypeDetail);
            subscriptionType.setLastDetail(subscriptionTypeDetail);
            
            sendEvent(subscriptionTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateSubscriptionTypeFromValue(SubscriptionTypeDetailValue customerTypeDetailValue, BasePK updatedBy) {
        updateSubscriptionTypeFromValue(customerTypeDetailValue, true, updatedBy);
    }
    
    public void deleteSubscriptionType(SubscriptionType subscriptionType, BasePK deletedBy) {
        deleteSubscriptionTypeDescriptionsBySubscriptionType(subscriptionType, deletedBy);
        deleteSubscriptionTypeChainsBySubscriptionType(subscriptionType, deletedBy);

        var subscriptionTypeDetail = subscriptionType.getLastDetailForUpdate();
        
        if(subscriptionTypeDetail.getSubscriptionKind().getLastDetail().getSubscriptionKindName().equals(SubscriptionConstants.SubscriptionKind_CLUB)) {
            
            clubControl.deleteClubBySubscriptionType(subscriptionType, deletedBy);
        }
        
        subscriptionTypeDetail.setThruTime(session.getStartTime());
        subscriptionType.setActiveDetail(null);
        subscriptionType.store();
        
        // Check for default, and pick one if necessary
        var subscriptionKind = subscriptionTypeDetail.getSubscriptionKind();
        var defaultSubscriptionType = getDefaultSubscriptionType(subscriptionKind);
        if(defaultSubscriptionType == null) {
            var subscriptionTypes = getSubscriptionTypesBySubscriptionKind(subscriptionKind);
            
            if(!subscriptionTypes.isEmpty()) {
                var iter = subscriptionTypes.iterator();
                if(iter.hasNext()) {
                    defaultSubscriptionType = iter.next();
                }
                var subscriptionTypeDetailValue = Objects.requireNonNull(defaultSubscriptionType).getLastDetailForUpdate().getSubscriptionTypeDetailValue().clone();
                
                subscriptionTypeDetailValue.setIsDefault(true);
                updateSubscriptionTypeFromValue(subscriptionTypeDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(subscriptionType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Subscription Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected SubscriptionTypeDescriptionFactory subscriptionTypeDescriptionFactory;

    @Inject
    private SubscriptionTypeDescriptionTransferCache subscriptionTypeDescriptionTransferCache;

    public SubscriptionTypeDescription createSubscriptionTypeDescription(SubscriptionType subscriptionType, Language language,
            String description, BasePK createdBy) {
        var subscriptionTypeDescription = subscriptionTypeDescriptionFactory.create(
                subscriptionType, language, description, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(subscriptionType.getPrimaryKey(), EventTypes.MODIFY,
                subscriptionTypeDescription.getPrimaryKey(), null, createdBy);
        
        return subscriptionTypeDescription;
    }
    
    private SubscriptionTypeDescription getSubscriptionTypeDescription(SubscriptionType subscriptionType, Language language,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SubscriptionTypeDescriptions.fields())
                .from(SubscriptionTypeDescriptions)
                .where(SubscriptionTypeDescriptions.SUBSCRIPTION_TYPE.eq(subscriptionType.getPrimaryKey()),
                        SubscriptionTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        SubscriptionTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return subscriptionTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(SubscriptionTypeDescriptions.fields())
                    .from(SubscriptionTypeDescriptions)
                    .join(Languages).onKey(SUBSCRIPTION_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(SubscriptionTypeDescriptions.SUBSCRIPTION_TYPE.eq(subscriptionType.getPrimaryKey()),
                            SubscriptionTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    SubscriptionTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(SubscriptionTypeDescriptions.fields())
                    .from(SubscriptionTypeDescriptions)
                    .where(SubscriptionTypeDescriptions.SUBSCRIPTION_TYPE.eq(subscriptionType.getPrimaryKey()),
                            SubscriptionTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return subscriptionTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<SubscriptionTypeDescription> getSubscriptionTypeDescriptionsBySubscriptionType(SubscriptionType subscriptionType) {
        return getSubscriptionTypeDescriptionsBySubscriptionType(subscriptionType, EntityPermission.READ_ONLY);
    }
    
    public List<SubscriptionTypeDescription> getSubscriptionTypeDescriptionsBySubscriptionTypeForUpdate(SubscriptionType subscriptionType) {
        return getSubscriptionTypeDescriptionsBySubscriptionType(subscriptionType, EntityPermission.READ_WRITE);
    }
    
    public String getBestSubscriptionTypeDescription(SubscriptionType subscriptionType, Language language) {
        String description;
        var subscriptionTypeDescription = getSubscriptionTypeDescription(subscriptionType, language);
        
        if(subscriptionTypeDescription == null && !language.getIsDefault()) {
            subscriptionTypeDescription = getSubscriptionTypeDescription(subscriptionType, partyControl.getDefaultLanguage());
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
        return subscriptionTypeDescriptionTransferCache.getSubscriptionTypeDescriptionTransfer(userVisit, subscriptionTypeDescription);
    }
    
    public List<SubscriptionTypeDescriptionTransfer> getSubscriptionTypeDescriptionTransfersBySubscriptionType(UserVisit userVisit,
            SubscriptionType subscriptionType) {
        var subscriptionTypeDescriptions = getSubscriptionTypeDescriptionsBySubscriptionType(subscriptionType);
        List<SubscriptionTypeDescriptionTransfer> subscriptionTypeDescriptionTransfers = null;
        
        if(subscriptionTypeDescriptions != null) {
            subscriptionTypeDescriptionTransfers = new ArrayList<>(subscriptionTypeDescriptions.size());
            
            for(var subscriptionTypeDescription : subscriptionTypeDescriptions) {
                subscriptionTypeDescriptionTransfers.add(subscriptionTypeDescriptionTransferCache.getSubscriptionTypeDescriptionTransfer(userVisit, subscriptionTypeDescription));
            }
        }
        
        return subscriptionTypeDescriptionTransfers;
    }
    
    public void updateSubscriptionTypeDescriptionFromValue(SubscriptionTypeDescriptionValue subscriptionTypeDescriptionValue,
            BasePK updatedBy) {
        if(subscriptionTypeDescriptionValue.hasBeenModified()) {
            var subscriptionTypeDescription = subscriptionTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     subscriptionTypeDescriptionValue.getPrimaryKey());
            
            subscriptionTypeDescription.setThruTime(session.getStartTime());
            subscriptionTypeDescription.store();

            var subscriptionType = subscriptionTypeDescription.getSubscriptionType();
            var language = subscriptionTypeDescription.getLanguage();
            var description = subscriptionTypeDescriptionValue.getDescription();
            
            subscriptionTypeDescription = subscriptionTypeDescriptionFactory.create(subscriptionType,
                    language, description, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(subscriptionType.getPrimaryKey(), EventTypes.MODIFY, subscriptionTypeDescription.getPrimaryKey(),
                    null, updatedBy);
        }
    }
    
    public void deleteSubscriptionTypeDescription(SubscriptionTypeDescription subscriptionTypeDescription, BasePK deletedBy) {
        subscriptionTypeDescription.setThruTime(session.getStartTime());
        
        sendEvent(subscriptionTypeDescription.getSubscriptionTypePK(), EventTypes.MODIFY,
                subscriptionTypeDescription.getPrimaryKey(), null, deletedBy);
    }
    
    public void deleteSubscriptionTypeDescriptionsBySubscriptionType(SubscriptionType subscriptionType, BasePK deletedBy) {
        var subscriptionTypeDescriptions = getSubscriptionTypeDescriptionsBySubscriptionTypeForUpdate(subscriptionType);
        
        subscriptionTypeDescriptions.forEach((subscriptionTypeDescription) -> 
                deleteSubscriptionTypeDescription(subscriptionTypeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Subscription Type Chains
    // --------------------------------------------------------------------------------

    @Inject
    protected SubscriptionTypeChainFactory subscriptionTypeChainFactory;

    @Inject
    private SubscriptionTypeChainTransferCache subscriptionTypeChainTransferCache;

    public SubscriptionTypeChain createSubscriptionTypeChain(SubscriptionType subscriptionType, Chain chain, Long remainingTime,
            BasePK createdBy) {
        var subscriptionTypeChain = subscriptionTypeChainFactory.create(subscriptionType,
                chain, remainingTime, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(subscriptionType.getPrimaryKey(), EventTypes.MODIFY, subscriptionTypeChain.getPrimaryKey(),
                null, createdBy);
        
        return subscriptionTypeChain;
    }

    public long countSubscriptionTypeChainsBySubscriptionType(final SubscriptionType subscriptionType) {
        return session.getDslContext()
                .selectCount()
                .from(SubscriptionTypeChains)
                .where(SubscriptionTypeChains.SUBSCRIPTION_TYPE.eq(subscriptionType.getPrimaryKey()),
                        SubscriptionTypeChains.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countSubscriptionTypeChainsByChain(final Chain chain) {
        return session.getDslContext()
                .selectCount()
                .from(SubscriptionTypeChains)
                .where(SubscriptionTypeChains.CHAIN.eq(chain.getPrimaryKey()),
                        SubscriptionTypeChains.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private SubscriptionTypeChain getSubscriptionTypeChain(SubscriptionType subscriptionType, Chain chain,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SubscriptionTypeChains.fields())
                .from(SubscriptionTypeChains)
                .where(SubscriptionTypeChains.SUBSCRIPTION_TYPE.eq(subscriptionType.getPrimaryKey()),
                        SubscriptionTypeChains.CHAIN.eq(chain.getPrimaryKey()),
                        SubscriptionTypeChains.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return subscriptionTypeChainFactory.getEntityFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(SubscriptionTypeChains.fields())
                    .from(SubscriptionTypeChains)
                    .join(Chains).onKey(SUBSCRIPTION_TYPE_CHAINS_CHAIN_FK)
                    .join(ChainDetails).onKey(CHAINS_LAST_DETAIL_FK)
                    .where(SubscriptionTypeChains.SUBSCRIPTION_TYPE.eq(subscriptionType.getPrimaryKey()),
                            SubscriptionTypeChains.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ChainDetails.SORT_ORDER, ChainDetails.CHAIN_NAME),
                    SubscriptionTypeChainFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(SubscriptionTypeChains.fields())
                    .from(SubscriptionTypeChains)
                    .where(SubscriptionTypeChains.SUBSCRIPTION_TYPE.eq(subscriptionType.getPrimaryKey()),
                            SubscriptionTypeChains.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return subscriptionTypeChainFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<SubscriptionTypeChain> getSubscriptionTypeChainsBySubscriptionType(SubscriptionType subscriptionType) {
        return getSubscriptionTypeChainsBySubscriptionType(subscriptionType, EntityPermission.READ_ONLY);
    }
    
    public List<SubscriptionTypeChain> getSubscriptionTypeChainsBySubscriptionTypeForUpdate(SubscriptionType subscriptionType) {
        return getSubscriptionTypeChainsBySubscriptionType(subscriptionType, EntityPermission.READ_WRITE);
    }
    
    private List<SubscriptionTypeChain> getSubscriptionTypeChainsBySubscriptionTypeAndChainType(SubscriptionType subscriptionType, ChainType chainType,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(SubscriptionTypeChains.fields())
                    .from(SubscriptionTypeChains)
                    .join(Chains).onKey(SUBSCRIPTION_TYPE_CHAINS_CHAIN_FK)
                    .join(ChainDetails).onKey(CHAINS_LAST_DETAIL_FK)
                    .where(SubscriptionTypeChains.SUBSCRIPTION_TYPE.eq(subscriptionType.getPrimaryKey()),
                            SubscriptionTypeChains.THRU_TIME.eq(Session.MAX_TIME),
                            ChainDetails.CHAIN_TYPE.eq(chainType.getPrimaryKey()))
                    .orderBy(ChainDetails.SORT_ORDER, ChainDetails.CHAIN_NAME),
                    SubscriptionTypeChainFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(SubscriptionTypeChains.fields())
                    .from(SubscriptionTypeChains)
                    .join(Chains).onKey(SUBSCRIPTION_TYPE_CHAINS_CHAIN_FK)
                    .join(ChainDetails).onKey(CHAINS_LAST_DETAIL_FK)
                    .where(SubscriptionTypeChains.SUBSCRIPTION_TYPE.eq(subscriptionType.getPrimaryKey()),
                            SubscriptionTypeChains.THRU_TIME.eq(Session.MAX_TIME),
                            ChainDetails.CHAIN_TYPE.eq(chainType.getPrimaryKey()))
                    .forUpdate();
        };

        return subscriptionTypeChainFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<SubscriptionTypeChain> getSubscriptionTypeChainsBySubscriptionTypeAndChainType(SubscriptionType subscriptionType, ChainType chainType) {
        return getSubscriptionTypeChainsBySubscriptionTypeAndChainType(subscriptionType, chainType, EntityPermission.READ_ONLY);
    }
    
    public List<SubscriptionTypeChain> getSubscriptionTypeChainsForUpdateBySubscriptionTypeAndChainType( SubscriptionType subscriptionType, ChainType chainType) {
        return getSubscriptionTypeChainsBySubscriptionTypeAndChainType(subscriptionType, chainType, EntityPermission.READ_WRITE);
    }
    
    public List<SubscriptionTypeChainTransfer> getSubscriptionTypeChainTransfers(UserVisit userVisit, Collection<SubscriptionTypeChain> subscriptionTypeChains) {
        List<SubscriptionTypeChainTransfer> subscriptionTypeChainTransfers = new ArrayList<>(subscriptionTypeChains.size());
        
        subscriptionTypeChains.forEach((subscriptionTypeChain) ->
                subscriptionTypeChainTransfers.add(subscriptionTypeChainTransferCache.getSubscriptionTypeChainTransfer(userVisit, subscriptionTypeChain))
        );
        
        return subscriptionTypeChainTransfers;
    }
    
    public List<SubscriptionTypeChainTransfer> getSubscriptionTypeChainTransfersBySubscriptionType(UserVisit userVisit,
            SubscriptionType subscriptionType) {
        return getSubscriptionTypeChainTransfers(userVisit, getSubscriptionTypeChainsBySubscriptionType(subscriptionType));
    }
    
    public void updateSubscriptionTypeChainFromValue(SubscriptionTypeChainValue subscriptionTypeChainValue, BasePK updatedBy) {
        if(subscriptionTypeChainValue.hasBeenModified()) {
            var subscriptionTypeChain = subscriptionTypeChainFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     subscriptionTypeChainValue.getPrimaryKey());
            
            subscriptionTypeChain.setThruTime(session.getStartTime());
            subscriptionTypeChain.store();

            var subscriptionTypePK = subscriptionTypeChain.getSubscriptionTypePK(); // Not updated
            var chainPK = subscriptionTypeChain.getChainPK(); // Not updated
            var remainingTime = subscriptionTypeChainValue.getRemainingTime();
            
            subscriptionTypeChain = subscriptionTypeChainFactory.create(subscriptionTypePK, chainPK,
                    remainingTime, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(subscriptionTypeChain.getSubscriptionTypePK(), EventTypes.MODIFY,
                    subscriptionTypeChain.getPrimaryKey(), null, updatedBy);
        }
    }
    
    public void deleteSubscriptionTypeChain(SubscriptionTypeChain subscriptionTypeChain, BasePK deletedBy) {
        // TODO: delete any running chains
        
        subscriptionTypeChain.setThruTime(session.getStartTime());
        
        sendEvent(subscriptionTypeChain.getSubscriptionTypePK(), EventTypes.MODIFY,
                subscriptionTypeChain.getPrimaryKey(), null, deletedBy);
    }
    
    public void deleteSubscriptionTypeChainsBySubscriptionType(SubscriptionType subscriptionType, BasePK deletedBy) {
        var subscriptionTypeChains = getSubscriptionTypeChainsBySubscriptionTypeForUpdate(subscriptionType);
        
        subscriptionTypeChains.forEach((subscriptionTypeChain) -> 
                deleteSubscriptionTypeChain(subscriptionTypeChain, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Subscriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected SubscriptionDetailFactory subscriptionDetailFactory;

    @Inject
    protected SubscriptionFactory subscriptionFactory;

    @Inject
    private SubscriptionTransferCache subscriptionTransferCache;

    public Subscription createSubscription(SubscriptionType subscriptionType, Party party, Long startTime, Long endTime,
            BasePK createdBy) {
        var sequence = subscriptionType.getLastDetail().getSubscriptionSequence();
        
        if(sequence == null) {
            var sequenceType = sequenceControl.getSequenceTypeByName(com.echothree.model.control.sequence.common.SequenceTypes.SUBSCRIPTION.name());
            sequence = sequenceControl.getDefaultSequence(sequenceType);
        }

        var subscription = subscriptionFactory.create();
        var subscriptionDetail = subscriptionDetailFactory.create(subscription,
                sequenceGeneratorLogic.getNextSequenceValue(sequence), subscriptionType, party, startTime, endTime,
                session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        subscription = subscriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                subscription.getPrimaryKey());
        subscription.setActiveDetail(subscriptionDetail);
        subscription.setLastDetail(subscriptionDetail);
        subscription.store();
        
        sendEvent(subscription.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return subscription;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.Subscription */
    public Subscription getSubscriptionByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new SubscriptionPK(entityInstance.getEntityUniqueId());

        return subscriptionFactory.getEntityFromPK(entityPermission, pk);
    }

    public Subscription getSubscriptionByEntityInstance(EntityInstance entityInstance) {
        return getSubscriptionByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public Subscription getSubscriptionByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getSubscriptionByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countSubscriptions() {
        return session.getDslContext()
                .selectCount()
                .from(Subscriptions)
                .join(SubscriptionDetails).onKey(SUBSCRIPTIONS_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countSubscriptionsBySubscriptionType(final SubscriptionType subscriptionType) {
        return session.getDslContext()
                .selectCount()
                .from(Subscriptions)
                .join(SubscriptionDetails).onKey(SUBSCRIPTIONS_ACTIVE_DETAIL_FK)
                .where(SubscriptionDetails.SUBSCRIPTION_TYPE.eq(subscriptionType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countSubscriptionsByParty(final Party party) {
        return session.getDslContext()
                .selectCount()
                .from(Subscriptions)
                .join(SubscriptionDetails).onKey(SUBSCRIPTIONS_ACTIVE_DETAIL_FK)
                .where(SubscriptionDetails.PARTY.eq(party.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private Subscription getSubscription(SubscriptionType subscriptionType, Party party, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Subscriptions.fields())
                .from(Subscriptions)
                .join(SubscriptionDetails).onKey(SUBSCRIPTIONS_ACTIVE_DETAIL_FK)
                .where(SubscriptionDetails.SUBSCRIPTION_TYPE.eq(subscriptionType.getPrimaryKey()),
                        SubscriptionDetails.PARTY.eq(party.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return subscriptionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public Subscription getSubscription(SubscriptionType subscriptionType, Party party) {
        return getSubscription(subscriptionType, party, EntityPermission.READ_ONLY);
    }
    
    public Subscription getSubscriptionForUpdate(SubscriptionType subscriptionType, Party party) {
        return getSubscription(subscriptionType, party, EntityPermission.READ_WRITE);
    }
    
    public Subscription getSubscriptionByName(String subscriptionName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Subscriptions.fields())
                .from(Subscriptions)
                .join(SubscriptionDetails).onKey(SUBSCRIPTIONS_ACTIVE_DETAIL_FK)
                .where(SubscriptionDetails.SUBSCRIPTION_NAME.eq(subscriptionName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return subscriptionFactory.getEntityFromQuery(entityPermission, query);
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
        var baseQuery = session.getDslContext()
                .select(Subscriptions.fields())
                .from(Subscriptions)
                .join(SubscriptionDetails).onKey(SUBSCRIPTIONS_ACTIVE_DETAIL_FK)
                .where(SubscriptionDetails.SUBSCRIPTION_TYPE.eq(subscriptionType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(SubscriptionDetails.SUBSCRIPTION_NAME),
                    SubscriptionFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return subscriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<Subscription> getSubscriptionsBySubscriptionType(SubscriptionType subscriptionType) {
        return getSubscriptionsBySubscriptionType(subscriptionType, EntityPermission.READ_ONLY);
    }
    
    public List<Subscription> getSubscriptionsBySubscriptionTypeForUpdate(SubscriptionType subscriptionType) {
        return getSubscriptionsBySubscriptionType(subscriptionType, EntityPermission.READ_WRITE);
    }
    
    private List<Subscription> getSubscriptionsByParty(Party party, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(Subscriptions.fields())
                    .from(Subscriptions)
                    .join(SubscriptionDetails).onKey(SUBSCRIPTIONS_ACTIVE_DETAIL_FK)
                    .join(SubscriptionTypes).onKey(SUBSCRIPTION_DETAILS_SUBSCRIPTION_TYPE_FK)
                    .join(SubscriptionTypeDetails).onKey(SUBSCRIPTION_TYPES_LAST_DETAIL_FK)
                    .where(SubscriptionDetails.PARTY.eq(party.getPrimaryKey()))
                    .orderBy(SubscriptionTypeDetails.SORT_ORDER, SubscriptionTypeDetails.SUBSCRIPTION_TYPE_NAME),
                    SubscriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(Subscriptions.fields())
                    .from(Subscriptions)
                    .join(SubscriptionDetails).onKey(SUBSCRIPTIONS_ACTIVE_DETAIL_FK)
                    .where(SubscriptionDetails.PARTY.eq(party.getPrimaryKey()))
                    .forUpdate();
        };

        return subscriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<Subscription> getSubscriptionsByParty(Party party) {
        return getSubscriptionsByParty(party, EntityPermission.READ_ONLY);
    }
    
    public List<Subscription> getSubscriptionsByPartyForUpdate(Party party) {
        return getSubscriptionsByParty(party, EntityPermission.READ_WRITE);
    }
    
    public SubscriptionTransfer getSubscriptionTransfer(UserVisit userVisit, Subscription subscription) {
        return subscriptionTransferCache.getSubscriptionTransfer(userVisit, subscription);
    }
    
    public List<SubscriptionTransfer> getSubscriptionTransfers(UserVisit userVisit, Collection<Subscription> subscriptions) {
        List<SubscriptionTransfer> subscriptionTransfers = new ArrayList<>(subscriptions.size());
        
        subscriptions.forEach((subscription) ->
                subscriptionTransfers.add(subscriptionTransferCache.getSubscriptionTransfer(userVisit, subscription))
        );
        
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
            var subscription = subscriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     subscriptionDetailValue.getSubscriptionPK());
            var subscriptionDetail = subscription.getActiveDetailForUpdate();
            
            subscriptionDetail.setThruTime(session.getStartTime());
            subscriptionDetail.store();

            var subscriptionPK = subscriptionDetail.getSubscriptionPK(); // Not updated
            var subscriptionName = subscriptionDetailValue.getSubscriptionName();
            var subscriptionTypePK = subscriptionDetail.getSubscriptionTypePK(); // Not updated
            var partyPK = subscriptionDetail.getPartyPK(); // Not updated
            var startTime = subscriptionDetailValue.getStartTime();
            var endTime = subscriptionDetailValue.getEndTime();
            
            subscriptionDetail = subscriptionDetailFactory.create(subscriptionPK, subscriptionName,
                    subscriptionTypePK, partyPK, startTime, endTime, session.getStartTime(), Session.MAX_TIME);
            
            subscription.setActiveDetail(subscriptionDetail);
            subscription.setLastDetail(subscriptionDetail);
            
            sendEvent(subscriptionPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deleteSubscription(Subscription subscription, BasePK deletedBy) {
        // TODO: Chain cleanup

        var subscriptionDetail = subscription.getLastDetailForUpdate();
        subscriptionDetail.setThruTime(session.getStartTime());
        subscription.setActiveDetail(null);
        subscription.store();
        
        sendEvent(subscription.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteSubscriptionsBySubscriptionType(SubscriptionType subscriptionType, BasePK deletedBy) {
        var subscriptions = getSubscriptionsBySubscriptionType(subscriptionType);
        
        subscriptions.forEach((subscription) -> 
                deleteSubscription(subscription, deletedBy)
        );
    }
    
}
