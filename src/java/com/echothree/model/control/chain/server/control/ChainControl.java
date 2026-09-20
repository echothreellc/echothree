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

package com.echothree.model.control.chain.server.control;

import com.echothree.model.control.chain.common.ChainActionTypes;
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
import com.echothree.model.control.chain.server.transfer.ChainActionChainActionSetTransferCache;
import com.echothree.model.control.chain.server.transfer.ChainActionDescriptionTransferCache;
import com.echothree.model.control.chain.server.transfer.ChainActionLetterTransferCache;
import com.echothree.model.control.chain.server.transfer.ChainActionSetDescriptionTransferCache;
import com.echothree.model.control.chain.server.transfer.ChainActionSetTransferCache;
import com.echothree.model.control.chain.server.transfer.ChainActionSurveyTransferCache;
import com.echothree.model.control.chain.server.transfer.ChainActionTransferCache;
import com.echothree.model.control.chain.server.transfer.ChainActionTypeDescriptionTransferCache;
import com.echothree.model.control.chain.server.transfer.ChainActionTypeTransferCache;
import com.echothree.model.control.chain.server.transfer.ChainActionTypeUseTransferCache;
import com.echothree.model.control.chain.server.transfer.ChainDescriptionTransferCache;
import com.echothree.model.control.chain.server.transfer.ChainEntityRoleTypeDescriptionTransferCache;
import com.echothree.model.control.chain.server.transfer.ChainEntityRoleTypeTransferCache;
import com.echothree.model.control.chain.server.transfer.ChainInstanceEntityRoleTransferCache;
import com.echothree.model.control.chain.server.transfer.ChainInstanceStatusTransferCache;
import com.echothree.model.control.chain.server.transfer.ChainInstanceTransferCache;
import com.echothree.model.control.chain.server.transfer.ChainKindDescriptionTransferCache;
import com.echothree.model.control.chain.server.transfer.ChainKindTransferCache;
import com.echothree.model.control.chain.server.transfer.ChainTransferCache;
import com.echothree.model.control.chain.server.transfer.ChainTypeDescriptionTransferCache;
import com.echothree.model.control.chain.server.transfer.ChainTypeTransferCache;
import com.echothree.model.control.contactlist.server.control.ContactListControl;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.letter.server.control.LetterControl;
import com.echothree.model.control.offer.server.control.OfferControl;
import com.echothree.model.data.chain.common.pk.ChainActionPK;
import com.echothree.model.data.chain.common.pk.ChainActionSetPK;
import com.echothree.model.data.chain.common.pk.ChainActionTypePK;
import com.echothree.model.data.chain.common.pk.ChainEntityRoleTypePK;
import com.echothree.model.data.chain.common.pk.ChainInstancePK;
import com.echothree.model.data.chain.common.pk.ChainKindPK;
import com.echothree.model.data.chain.common.pk.ChainPK;
import com.echothree.model.data.chain.common.pk.ChainTypePK;
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
import static com.echothree.model.jooq.server.keys.chain.ChainForeignKeys.CHAINS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.chain.ChainForeignKeys.CHAIN_ACTIONS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.chain.ChainForeignKeys.CHAIN_ACTION_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.chain.ChainForeignKeys.CHAIN_ACTION_SETS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.chain.ChainForeignKeys.CHAIN_ACTION_SET_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.chain.ChainForeignKeys.CHAIN_ACTION_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.chain.ChainForeignKeys.CHAIN_ACTION_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.chain.ChainForeignKeys.CHAIN_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.chain.ChainForeignKeys.CHAIN_DETAILS_CHAIN_TYPE_FK;
import static com.echothree.model.jooq.server.keys.chain.ChainForeignKeys.CHAIN_ENTITY_ROLE_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.chain.ChainForeignKeys.CHAIN_ENTITY_ROLE_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.chain.ChainForeignKeys.CHAIN_ENTITY_ROLE_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.chain.ChainForeignKeys.CHAIN_INSTANCES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.chain.ChainForeignKeys.CHAIN_INSTANCE_ENTITY_ROLES_CHAIN_ENTITY_ROLE_TYPE_FK;
import static com.echothree.model.jooq.server.keys.chain.ChainForeignKeys.CHAIN_KINDS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.chain.ChainForeignKeys.CHAIN_KINDS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.chain.ChainForeignKeys.CHAIN_KIND_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.chain.ChainForeignKeys.CHAIN_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.chain.ChainForeignKeys.CHAIN_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.chain.ChainForeignKeys.CHAIN_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.chain.ChainForeignKeys.CHAIN_TYPE_DETAILS_CHAIN_KIND_FK;
import static com.echothree.model.jooq.server.tables.chain.ChainActionChainActionSets.ChainActionChainActionSets;
import static com.echothree.model.jooq.server.tables.chain.ChainActionDescriptions.ChainActionDescriptions;
import static com.echothree.model.jooq.server.tables.chain.ChainActionDetails.ChainActionDetails;
import static com.echothree.model.jooq.server.tables.chain.ChainActionLetters.ChainActionLetters;
import static com.echothree.model.jooq.server.tables.chain.ChainActionSetDescriptions.ChainActionSetDescriptions;
import static com.echothree.model.jooq.server.tables.chain.ChainActionSetDetails.ChainActionSetDetails;
import static com.echothree.model.jooq.server.tables.chain.ChainActionSets.ChainActionSets;
import static com.echothree.model.jooq.server.tables.chain.ChainActionSurveys.ChainActionSurveys;
import static com.echothree.model.jooq.server.tables.chain.ChainActionTypeDescriptions.ChainActionTypeDescriptions;
import static com.echothree.model.jooq.server.tables.chain.ChainActionTypeDetails.ChainActionTypeDetails;
import static com.echothree.model.jooq.server.tables.chain.ChainActionTypeUses.ChainActionTypeUses;
import static com.echothree.model.jooq.server.tables.chain.ChainActionTypes.ChainActionTypes;
import static com.echothree.model.jooq.server.tables.chain.ChainActions.ChainActions;
import static com.echothree.model.jooq.server.tables.chain.ChainDescriptions.ChainDescriptions;
import static com.echothree.model.jooq.server.tables.chain.ChainDetails.ChainDetails;
import static com.echothree.model.jooq.server.tables.chain.ChainEntityRoleTypeDescriptions.ChainEntityRoleTypeDescriptions;
import static com.echothree.model.jooq.server.tables.chain.ChainEntityRoleTypeDetails.ChainEntityRoleTypeDetails;
import static com.echothree.model.jooq.server.tables.chain.ChainEntityRoleTypes.ChainEntityRoleTypes;
import static com.echothree.model.jooq.server.tables.chain.ChainInstanceDetails.ChainInstanceDetails;
import static com.echothree.model.jooq.server.tables.chain.ChainInstanceEntityRoles.ChainInstanceEntityRoles;
import static com.echothree.model.jooq.server.tables.chain.ChainInstanceStatuses.ChainInstanceStatuses;
import static com.echothree.model.jooq.server.tables.chain.ChainInstances.ChainInstances;
import static com.echothree.model.jooq.server.tables.chain.ChainKindDescriptions.ChainKindDescriptions;
import static com.echothree.model.jooq.server.tables.chain.ChainKindDetails.ChainKindDetails;
import static com.echothree.model.jooq.server.tables.chain.ChainKinds.ChainKinds;
import static com.echothree.model.jooq.server.tables.chain.ChainTypeDescriptions.ChainTypeDescriptions;
import static com.echothree.model.jooq.server.tables.chain.ChainTypeDetails.ChainTypeDetails;
import static com.echothree.model.jooq.server.tables.chain.ChainTypes.ChainTypes;
import static com.echothree.model.jooq.server.tables.chain.Chains.Chains;
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
public class ChainControl
        extends BaseModelControl {
    
    @Inject
    protected ContactListControl contactListControl;

    @Inject
    protected LetterControl letterControl;

    @Inject
    protected OfferControl offerControl;

    /** Creates a new instance of ChainControl */
    protected ChainControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Chain Kinds
    // --------------------------------------------------------------------------------

    @Inject
    protected ChainKindDetailFactory chainKindDetailFactory;

    @Inject
    protected ChainKindFactory chainKindFactory;

    @Inject
    ChainKindTransferCache chainKindTransferCache;

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

        var chainKind = chainKindFactory.create();
        var chainKindDetail = chainKindDetailFactory.create(chainKind, chainKindName, isDefault, sortOrder,
                session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        chainKind = chainKindFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                chainKind.getPrimaryKey());
        chainKind.setActiveDetail(chainKindDetail);
        chainKind.setLastDetail(chainKindDetail);
        chainKind.store();

        sendEvent(chainKind.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return chainKind;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ChainKind */
    public ChainKind getChainKindByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ChainKindPK(entityInstance.getEntityUniqueId());

        return chainKindFactory.getEntityFromPK(entityPermission, pk);
    }

    public ChainKind getChainKindByEntityInstance(EntityInstance entityInstance) {
        return getChainKindByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ChainKind getChainKindByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getChainKindByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countChainKinds() {
        return session.getDslContext()
                .selectCount()
                .from(ChainKinds)
                .join(ChainKindDetails).onKey(CHAIN_KINDS_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public ChainKind getChainKindByName(String chainKindName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ChainKinds.fields())
                .from(ChainKinds)
                .join(ChainKindDetails).onKey(CHAIN_KINDS_ACTIVE_DETAIL_FK)
                .where(ChainKindDetails.CHAIN_KIND_NAME.eq(chainKindName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return chainKindFactory.getEntityFromQuery(entityPermission, query);
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

    public ChainKind getDefaultChainKind(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ChainKinds.fields())
                .from(ChainKinds)
                .join(ChainKindDetails).onKey(CHAIN_KINDS_ACTIVE_DETAIL_FK)
                .where(ChainKindDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return chainKindFactory.getEntityFromQuery(entityPermission, query);
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

    private List<ChainKind> getChainKinds(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ChainKinds.fields())
                .from(ChainKinds)
                .join(ChainKindDetails).onKey(CHAIN_KINDS_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ChainKindDetails.SORT_ORDER, ChainKindDetails.CHAIN_KIND_NAME),
                    ChainKindFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return chainKindFactory.getEntitiesFromQuery(entityPermission, query);
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
        return chainKindTransferCache.getChainKindTransfer(userVisit, chainKind);
    }

    public List<ChainKindTransfer> getChainKindTransfers(UserVisit userVisit, Collection<ChainKind> chainKinds) {
        List<ChainKindTransfer> chainKindTransfers = new ArrayList<>(chainKinds.size());

        chainKinds.forEach((chainKind) ->
                chainKindTransfers.add(chainKindTransferCache.getChainKindTransfer(userVisit, chainKind))
        );

        return chainKindTransfers;
    }

    public List<ChainKindTransfer> getChainKindTransfers(UserVisit userVisit) {
        return getChainKindTransfers(userVisit, getChainKinds());
    }

    private void updateChainKindFromValue(ChainKindDetailValue chainKindDetailValue, boolean checkDefault, BasePK updatedBy) {
        var chainKind = chainKindFactory.getEntityFromPK(
                EntityPermission.READ_WRITE, chainKindDetailValue.getChainKindPK());
        var chainKindDetail = chainKind.getActiveDetailForUpdate();

        chainKindDetail.setThruTime(session.getStartTime());
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

        chainKindDetail = chainKindDetailFactory.create(chainKindPK, chainKindName, isDefault, sortOrder, session.getStartTime(),
                Session.MAX_TIME);

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
        chainKindDetail.setThruTime(session.getStartTime());
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

    @Inject
    protected ChainKindDescriptionFactory chainKindDescriptionFactory;

    @Inject
    ChainKindDescriptionTransferCache chainKindDescriptionTransferCache;

    public ChainKindDescription createChainKindDescription(ChainKind chainKind, Language language, String description,
            BasePK createdBy) {
        var chainKindDescription = chainKindDescriptionFactory.create(chainKind,
                language, description, session.getStartTime(), Session.MAX_TIME);

        sendEvent(chainKind.getPrimaryKey(), EventTypes.MODIFY, chainKindDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return chainKindDescription;
    }

    private ChainKindDescription getChainKindDescription(ChainKind chainKind, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ChainKindDescriptions.fields())
                .from(ChainKindDescriptions)
                .where(ChainKindDescriptions.CHAIN_KIND.eq(chainKind.getPrimaryKey()),
                        ChainKindDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        ChainKindDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return chainKindDescriptionFactory.getEntityFromQuery(entityPermission, query);
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

    private List<ChainKindDescription> getChainKindDescriptionsByChainKind(ChainKind chainKind, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ChainKindDescriptions.fields())
                    .from(ChainKindDescriptions)
                    .join(Languages).onKey(CHAIN_KIND_DESCRIPTIONS_LANGUAGE_FK)
                    .where(ChainKindDescriptions.CHAIN_KIND.eq(chainKind.getPrimaryKey()),
                            ChainKindDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    ChainKindDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ChainKindDescriptions.fields())
                    .from(ChainKindDescriptions)
                    .where(ChainKindDescriptions.CHAIN_KIND.eq(chainKind.getPrimaryKey()),
                            ChainKindDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return chainKindDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
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
        return chainKindDescriptionTransferCache.getChainKindDescriptionTransfer(userVisit, chainKindDescription);
    }

    public List<ChainKindDescriptionTransfer> getChainKindDescriptionTransfersByChainKind(UserVisit userVisit, ChainKind chainKind) {
        var chainKindDescriptions = getChainKindDescriptionsByChainKind(chainKind);
        List<ChainKindDescriptionTransfer> chainKindDescriptionTransfers = new ArrayList<>(chainKindDescriptions.size());

        chainKindDescriptions.forEach((chainKindDescription) -> {
            chainKindDescriptionTransfers.add(chainKindDescriptionTransferCache.getChainKindDescriptionTransfer(userVisit, chainKindDescription));
        });

        return chainKindDescriptionTransfers;
    }

    public void updateChainKindDescriptionFromValue(ChainKindDescriptionValue chainKindDescriptionValue, BasePK updatedBy) {
        if(chainKindDescriptionValue.hasBeenModified()) {
            var chainKindDescription = chainKindDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     chainKindDescriptionValue.getPrimaryKey());

            chainKindDescription.setThruTime(session.getStartTime());
            chainKindDescription.store();

            var chainKind = chainKindDescription.getChainKind();
            var language = chainKindDescription.getLanguage();
            var description = chainKindDescriptionValue.getDescription();

            chainKindDescription = chainKindDescriptionFactory.create(chainKind, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(chainKind.getPrimaryKey(), EventTypes.MODIFY, chainKindDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteChainKindDescription(ChainKindDescription chainKindDescription, BasePK deletedBy) {
        chainKindDescription.setThruTime(session.getStartTime());

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

    @Inject
    protected ChainTypeDetailFactory chainTypeDetailFactory;

    @Inject
    protected ChainTypeFactory chainTypeFactory;

    @Inject
    ChainTypeTransferCache chainTypeTransferCache;

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

        var chainType = chainTypeFactory.create();
        var chainTypeDetail = chainTypeDetailFactory.create( chainType, chainKind, chainTypeName, isDefault, sortOrder,
                session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        chainType = chainTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                chainType.getPrimaryKey());
        chainType.setActiveDetail(chainTypeDetail);
        chainType.setLastDetail(chainTypeDetail);
        chainType.store();

        sendEvent(chainType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return chainType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ChainType */
    public ChainType getChainTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ChainTypePK(entityInstance.getEntityUniqueId());

        return chainTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public ChainType getChainTypeByEntityInstance(EntityInstance entityInstance) {
        return getChainTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ChainType getChainTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getChainTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countChainTypesByChainKind(final ChainKind chainKind) {
        return session.getDslContext()
                .selectCount()
                .from(ChainTypes)
                .join(ChainTypeDetails).onKey(CHAIN_TYPES_ACTIVE_DETAIL_FK)
                .where(ChainTypeDetails.CHAIN_KIND.eq(chainKind.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<ChainType> getChainTypesByChainKind(ChainKind chainKind, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ChainTypes.fields())
                .from(ChainTypes)
                .join(ChainTypeDetails).onKey(CHAIN_TYPES_ACTIVE_DETAIL_FK)
                .where(ChainTypeDetails.CHAIN_KIND.eq(chainKind.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ChainTypeDetails.SORT_ORDER, ChainTypeDetails.CHAIN_TYPE_NAME),
                    ChainTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return chainTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ChainType> getChainTypesByChainKind(ChainKind chainKind) {
        return getChainTypesByChainKind(chainKind, EntityPermission.READ_ONLY);
    }

    public List<ChainType> getChainTypesByChainKindForUpdate(ChainKind chainKind) {
        return getChainTypesByChainKind(chainKind, EntityPermission.READ_WRITE);
    }

    private List<ChainType> getChainTypesByEntityType(EntityType entityType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ChainTypes.fields())
                    .from(ChainTypes)
                    .join(ChainTypeDetails).onKey(CHAIN_TYPES_ACTIVE_DETAIL_FK)
                    .join(ChainKinds).onKey(CHAIN_TYPE_DETAILS_CHAIN_KIND_FK)
                    .join(ChainKindDetails).onKey(CHAIN_KINDS_LAST_DETAIL_FK)
                    .where(ChainEntityRoleTypeDetails.ENTITY_TYPE.eq(entityType.getPrimaryKey()))
                    .orderBy(ChainKindDetails.SORT_ORDER, ChainKindDetails.CHAIN_KIND_NAME, ChainTypeDetails.SORT_ORDER, ChainTypeDetails.CHAIN_TYPE_NAME),
                    ChainTypeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ChainTypes.fields())
                    .from(ChainTypes)
                    .join(ChainTypeDetails).onKey(CHAIN_TYPES_ACTIVE_DETAIL_FK)
                    .where(ChainEntityRoleTypeDetails.ENTITY_TYPE.eq(entityType.getPrimaryKey()))
                    .forUpdate();
        };

        return chainTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ChainType> getChainTypesByEntityType(EntityType entityType) {
        return getChainTypesByEntityType(entityType, EntityPermission.READ_ONLY);
    }

    public List<ChainType> getChainTypesByEntityTypeForUpdate(EntityType entityType) {
        return getChainTypesByEntityType(entityType, EntityPermission.READ_WRITE);
    }

    public ChainType getDefaultChainType(ChainKind chainKind, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ChainTypes.fields())
                .from(ChainTypes)
                .join(ChainTypeDetails).onKey(CHAIN_TYPES_ACTIVE_DETAIL_FK)
                .where(ChainTypeDetails.CHAIN_KIND.eq(chainKind.getPrimaryKey()),
                        ChainTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return chainTypeFactory.getEntityFromQuery(entityPermission, query);
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

    public ChainType getChainTypeByName(ChainKind chainKind, String chainTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ChainTypes.fields())
                .from(ChainTypes)
                .join(ChainTypeDetails).onKey(CHAIN_TYPES_ACTIVE_DETAIL_FK)
                .where(ChainTypeDetails.CHAIN_KIND.eq(chainKind.getPrimaryKey()),
                        ChainTypeDetails.CHAIN_TYPE_NAME.eq(chainTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return chainTypeFactory.getEntityFromQuery(entityPermission, query);
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
        return chainTypeTransferCache.getChainTypeTransfer(userVisit, chainType);
    }

    public List<ChainTypeTransfer> getChainTypeTransfersByChainKind(UserVisit userVisit, ChainKind chainKind) {
        return getChainTypeTransfers(userVisit, getChainTypesByChainKind(chainKind));
    }

    public List<ChainTypeTransfer> getChainTypeTransfers(UserVisit userVisit, Collection<ChainType> chainTypes) {
        List<ChainTypeTransfer> chainTypeTransfers = new ArrayList<>(chainTypes.size());

        chainTypes.forEach((chainType) ->
                chainTypeTransfers.add(chainTypeTransferCache.getChainTypeTransfer(userVisit, chainType))
        );

        return chainTypeTransfers;
    }

    private void updateChainTypeFromValue(ChainTypeDetailValue chainTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(chainTypeDetailValue.hasBeenModified()) {
            var chainType = chainTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     chainTypeDetailValue.getChainTypePK());
            var chainTypeDetail = chainType.getActiveDetailForUpdate();

            chainTypeDetail.setThruTime(session.getStartTime());
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

            chainTypeDetail = chainTypeDetailFactory.create(chainTypePK, chainKindPK, chainTypeName, isDefault, sortOrder,
                    session.getStartTime(), Session.MAX_TIME);

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
        chainTypeDetail.setThruTime(session.getStartTime());
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

    @Inject
    protected ChainTypeDescriptionFactory chainTypeDescriptionFactory;

    @Inject
    ChainTypeDescriptionTransferCache chainTypeDescriptionTransferCache;

    public ChainTypeDescription createChainTypeDescription(ChainType chainType, Language language, String description,
            BasePK createdBy) {
        var chainTypeDescription = chainTypeDescriptionFactory.create(chainType,
                language, description, session.getStartTime(), Session.MAX_TIME);

        sendEvent(chainType.getPrimaryKey(), EventTypes.MODIFY, chainTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return chainTypeDescription;
    }

    private ChainTypeDescription getChainTypeDescription(ChainType chainType, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ChainTypeDescriptions.fields())
                .from(ChainTypeDescriptions)
                .where(ChainTypeDescriptions.CHAIN_TYPE.eq(chainType.getPrimaryKey()),
                        ChainTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        ChainTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return chainTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
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

    private List<ChainTypeDescription> getChainTypeDescriptionsByChainType(ChainType chainType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ChainTypeDescriptions.fields())
                    .from(ChainTypeDescriptions)
                    .join(Languages).onKey(CHAIN_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(ChainTypeDescriptions.CHAIN_TYPE.eq(chainType.getPrimaryKey()),
                            ChainTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    ChainTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ChainTypeDescriptions.fields())
                    .from(ChainTypeDescriptions)
                    .where(ChainTypeDescriptions.CHAIN_TYPE.eq(chainType.getPrimaryKey()),
                            ChainTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return chainTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
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
        return chainTypeDescriptionTransferCache.getChainTypeDescriptionTransfer(userVisit, chainTypeDescription);
    }

    public List<ChainTypeDescriptionTransfer> getChainTypeDescriptionTransfersByChainType(UserVisit userVisit, ChainType chainType) {
        var chainTypeDescriptions = getChainTypeDescriptionsByChainType(chainType);
        List<ChainTypeDescriptionTransfer> chainTypeDescriptionTransfers = new ArrayList<>(chainTypeDescriptions.size());

        chainTypeDescriptions.forEach((chainTypeDescription) -> {
            chainTypeDescriptionTransfers.add(chainTypeDescriptionTransferCache.getChainTypeDescriptionTransfer(userVisit, chainTypeDescription));
        });

        return chainTypeDescriptionTransfers;
    }

    public void updateChainTypeDescriptionFromValue(ChainTypeDescriptionValue chainTypeDescriptionValue, BasePK updatedBy) {
        if(chainTypeDescriptionValue.hasBeenModified()) {
            var chainTypeDescription = chainTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     chainTypeDescriptionValue.getPrimaryKey());

            chainTypeDescription.setThruTime(session.getStartTime());
            chainTypeDescription.store();

            var chainType = chainTypeDescription.getChainType();
            var language = chainTypeDescription.getLanguage();
            var description = chainTypeDescriptionValue.getDescription();

            chainTypeDescription = chainTypeDescriptionFactory.create(chainType, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(chainType.getPrimaryKey(), EventTypes.MODIFY, chainTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteChainTypeDescription(ChainTypeDescription chainTypeDescription, BasePK deletedBy) {
        chainTypeDescription.setThruTime(session.getStartTime());

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

    @Inject
    protected ChainEntityRoleTypeDetailFactory chainEntityRoleTypeDetailFactory;

    @Inject
    protected ChainEntityRoleTypeFactory chainEntityRoleTypeFactory;

    @Inject
    ChainEntityRoleTypeTransferCache chainEntityRoleTypeTransferCache;

    public ChainEntityRoleType createChainEntityRoleType(ChainType chainType, String chainEntityRoleTypeName, EntityType entityType, Integer sortOrder,
            BasePK createdBy) {
        var chainEntityRoleType = chainEntityRoleTypeFactory.create();
        var chainEntityRoleTypeDetail = chainEntityRoleTypeDetailFactory.create( chainEntityRoleType, chainType,
                chainEntityRoleTypeName, entityType, sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        chainEntityRoleType = chainEntityRoleTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                chainEntityRoleType.getPrimaryKey());
        chainEntityRoleType.setActiveDetail(chainEntityRoleTypeDetail);
        chainEntityRoleType.setLastDetail(chainEntityRoleTypeDetail);
        chainEntityRoleType.store();

        sendEvent(chainEntityRoleType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return chainEntityRoleType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ChainEntityRoleType */
    public ChainEntityRoleType getChainEntityRoleTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ChainEntityRoleTypePK(entityInstance.getEntityUniqueId());

        return chainEntityRoleTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public ChainEntityRoleType getChainEntityRoleTypeByEntityInstance(EntityInstance entityInstance) {
        return getChainEntityRoleTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ChainEntityRoleType getChainEntityRoleTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getChainEntityRoleTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countChainEntityRoleTypesByChainType(final ChainType chainType) {
        return session.getDslContext()
                .selectCount()
                .from(ChainEntityRoleTypes)
                .join(ChainEntityRoleTypeDetails).onKey(CHAIN_ENTITY_ROLE_TYPES_ACTIVE_DETAIL_FK)
                .where(ChainEntityRoleTypeDetails.CHAIN_TYPE.eq(chainType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countChainEntityRoleTypesByEntityType(final EntityType entityType) {
        return session.getDslContext()
                .selectCount()
                .from(ChainEntityRoleTypes)
                .join(ChainEntityRoleTypeDetails).onKey(CHAIN_ENTITY_ROLE_TYPES_ACTIVE_DETAIL_FK)
                .where(ChainEntityRoleTypeDetails.ENTITY_TYPE.eq(entityType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<ChainEntityRoleType> getChainEntityRoleTypes(ChainType chainType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ChainEntityRoleTypes.fields())
                .from(ChainEntityRoleTypes)
                .join(ChainEntityRoleTypeDetails).onKey(CHAIN_ENTITY_ROLE_TYPES_ACTIVE_DETAIL_FK)
                .where(ChainEntityRoleTypeDetails.CHAIN_TYPE.eq(chainType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ChainEntityRoleTypeDetails.SORT_ORDER, ChainEntityRoleTypeDetails.CHAIN_ENTITY_ROLE_TYPE_NAME),
                    ChainEntityRoleTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return chainEntityRoleTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ChainEntityRoleType> getChainEntityRoleTypes(ChainType chainType) {
        return getChainEntityRoleTypes(chainType, EntityPermission.READ_ONLY);
    }

    public List<ChainEntityRoleType> getChainEntityRoleTypesForUpdate(ChainType chainType) {
        return getChainEntityRoleTypes(chainType, EntityPermission.READ_WRITE);
    }

    public ChainEntityRoleType getChainEntityRoleTypeByName(ChainType chainType, String chainEntityRoleTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ChainEntityRoleTypes.fields())
                .from(ChainEntityRoleTypes)
                .join(ChainEntityRoleTypeDetails).onKey(CHAIN_ENTITY_ROLE_TYPES_ACTIVE_DETAIL_FK)
                .where(ChainEntityRoleTypeDetails.CHAIN_TYPE.eq(chainType.getPrimaryKey()),
                        ChainEntityRoleTypeDetails.CHAIN_ENTITY_ROLE_TYPE_NAME.eq(chainEntityRoleTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return chainEntityRoleTypeFactory.getEntityFromQuery(entityPermission, query);
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
        return chainEntityRoleTypeTransferCache.getChainEntityRoleTypeTransfer(userVisit, chainEntityRoleType);
    }

    public List<ChainEntityRoleTypeTransfer> getChainEntityRoleTypeTransfersByChainType(UserVisit userVisit, ChainType chainType) {
        var chainEntityRoleTypes = getChainEntityRoleTypes(chainType);
        List<ChainEntityRoleTypeTransfer> chainEntityRoleTypeTransfers = new ArrayList<>(chainEntityRoleTypes.size());

        chainEntityRoleTypes.forEach((chainEntityRoleType) ->
                chainEntityRoleTypeTransfers.add(chainEntityRoleTypeTransferCache.getChainEntityRoleTypeTransfer(userVisit, chainEntityRoleType))
        );

        return chainEntityRoleTypeTransfers;
    }

    public void updateChainEntityRoleTypeFromValue(ChainEntityRoleTypeDetailValue chainEntityRoleTypeDetailValue, BasePK updatedBy) {
        if(chainEntityRoleTypeDetailValue.hasBeenModified()) {
            var chainEntityRoleType = chainEntityRoleTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     chainEntityRoleTypeDetailValue.getChainEntityRoleTypePK());
            var chainEntityRoleTypeDetail = chainEntityRoleType.getActiveDetailForUpdate();

            chainEntityRoleTypeDetail.setThruTime(session.getStartTime());
            chainEntityRoleTypeDetail.store();

            var chainEntityRoleTypePK = chainEntityRoleTypeDetail.getChainEntityRoleTypePK();
            var chainType = chainEntityRoleTypeDetail.getChainType();
            var chainTypePK = chainType.getPrimaryKey();
            var chainEntityRoleTypeName = chainEntityRoleTypeDetailValue.getChainEntityRoleTypeName();
            var entityTypePK = chainEntityRoleTypeDetailValue.getEntityTypePK();
            var sortOrder = chainEntityRoleTypeDetailValue.getSortOrder();

            chainEntityRoleTypeDetail = chainEntityRoleTypeDetailFactory.create(chainEntityRoleTypePK, chainTypePK, chainEntityRoleTypeName,
                    entityTypePK, sortOrder, session.getStartTime(), Session.MAX_TIME);

            chainEntityRoleType.setActiveDetail(chainEntityRoleTypeDetail);
            chainEntityRoleType.setLastDetail(chainEntityRoleTypeDetail);

            sendEvent(chainEntityRoleTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void deleteChainEntityRoleType(ChainEntityRoleType chainEntityRoleType, BasePK deletedBy) {
        deleteChainInstanceEntityRolesByChainEntityRoleType(chainEntityRoleType, deletedBy);
        deleteChainEntityRoleTypeDescriptionsByChainEntityRoleType(chainEntityRoleType, deletedBy);

        var chainEntityRoleTypeDetail = chainEntityRoleType.getLastDetailForUpdate();
        chainEntityRoleTypeDetail.setThruTime(session.getStartTime());
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

    @Inject
    protected ChainEntityRoleTypeDescriptionFactory chainEntityRoleTypeDescriptionFactory;

    @Inject
    ChainEntityRoleTypeDescriptionTransferCache chainEntityRoleTypeDescriptionTransferCache;

    public ChainEntityRoleTypeDescription createChainEntityRoleTypeDescription(ChainEntityRoleType chainEntityRoleType, Language language, String description,
            BasePK createdBy) {
        var chainEntityRoleTypeDescription = chainEntityRoleTypeDescriptionFactory.create(chainEntityRoleType,
                language, description, session.getStartTime(), Session.MAX_TIME);

        sendEvent(chainEntityRoleType.getPrimaryKey(), EventTypes.MODIFY, chainEntityRoleTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return chainEntityRoleTypeDescription;
    }

    private ChainEntityRoleTypeDescription getChainEntityRoleTypeDescription(ChainEntityRoleType chainEntityRoleType, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ChainEntityRoleTypeDescriptions.fields())
                .from(ChainEntityRoleTypeDescriptions)
                .where(ChainEntityRoleTypeDescriptions.CHAIN_ENTITY_ROLE_TYPE.eq(chainEntityRoleType.getPrimaryKey()),
                        ChainEntityRoleTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        ChainEntityRoleTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return chainEntityRoleTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
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

    private List<ChainEntityRoleTypeDescription> getChainEntityRoleTypeDescriptionsByChainEntityRoleType(ChainEntityRoleType chainEntityRoleType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ChainEntityRoleTypeDescriptions.fields())
                    .from(ChainEntityRoleTypeDescriptions)
                    .join(Languages).onKey(CHAIN_ENTITY_ROLE_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(ChainEntityRoleTypeDescriptions.CHAIN_ENTITY_ROLE_TYPE.eq(chainEntityRoleType.getPrimaryKey()),
                            ChainEntityRoleTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    ChainEntityRoleTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ChainEntityRoleTypeDescriptions.fields())
                    .from(ChainEntityRoleTypeDescriptions)
                    .where(ChainEntityRoleTypeDescriptions.CHAIN_ENTITY_ROLE_TYPE.eq(chainEntityRoleType.getPrimaryKey()),
                            ChainEntityRoleTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return chainEntityRoleTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
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
        return chainEntityRoleTypeDescriptionTransferCache.getChainEntityRoleTypeDescriptionTransfer(userVisit, chainEntityRoleTypeDescription);
    }

    public List<ChainEntityRoleTypeDescriptionTransfer> getChainEntityRoleTypeDescriptionTransfersByChainEntityRoleType(UserVisit userVisit, ChainEntityRoleType chainEntityRoleType) {
        var chainEntityRoleTypeDescriptions = getChainEntityRoleTypeDescriptionsByChainEntityRoleType(chainEntityRoleType);
        List<ChainEntityRoleTypeDescriptionTransfer> chainEntityRoleTypeDescriptionTransfers = new ArrayList<>(chainEntityRoleTypeDescriptions.size());

        chainEntityRoleTypeDescriptions.forEach((chainEntityRoleTypeDescription) -> {
            chainEntityRoleTypeDescriptionTransfers.add(chainEntityRoleTypeDescriptionTransferCache.getChainEntityRoleTypeDescriptionTransfer(userVisit, chainEntityRoleTypeDescription));
        });

        return chainEntityRoleTypeDescriptionTransfers;
    }

    public void updateChainEntityRoleTypeDescriptionFromValue(ChainEntityRoleTypeDescriptionValue chainEntityRoleTypeDescriptionValue, BasePK updatedBy) {
        if(chainEntityRoleTypeDescriptionValue.hasBeenModified()) {
            var chainEntityRoleTypeDescription = chainEntityRoleTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     chainEntityRoleTypeDescriptionValue.getPrimaryKey());

            chainEntityRoleTypeDescription.setThruTime(session.getStartTime());
            chainEntityRoleTypeDescription.store();

            var chainEntityRoleType = chainEntityRoleTypeDescription.getChainEntityRoleType();
            var language = chainEntityRoleTypeDescription.getLanguage();
            var description = chainEntityRoleTypeDescriptionValue.getDescription();

            chainEntityRoleTypeDescription = chainEntityRoleTypeDescriptionFactory.create(chainEntityRoleType, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(chainEntityRoleType.getPrimaryKey(), EventTypes.MODIFY, chainEntityRoleTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteChainEntityRoleTypeDescription(ChainEntityRoleTypeDescription chainEntityRoleTypeDescription, BasePK deletedBy) {
        chainEntityRoleTypeDescription.setThruTime(session.getStartTime());

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

    @Inject
    protected ChainDetailFactory chainDetailFactory;

    @Inject
    protected ChainFactory chainFactory;

    @Inject
    ChainTransferCache chainTransferCache;

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

        var chain = chainFactory.create();
        var chainDetail = chainDetailFactory.create( chain, chainType, chainName, chainInstanceSequence, isDefault, sortOrder,
                session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        chain = chainFactory.getEntityFromPK(EntityPermission.READ_WRITE, chain.getPrimaryKey());
        chain.setActiveDetail(chainDetail);
        chain.setLastDetail(chainDetail);
        chain.store();

        sendEvent(chain.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return chain;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.Chain */
    public Chain getChainByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ChainPK(entityInstance.getEntityUniqueId());

        return chainFactory.getEntityFromPK(entityPermission, pk);
    }

    public Chain getChainByEntityInstance(EntityInstance entityInstance) {
        return getChainByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public Chain getChainByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getChainByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countChainsByChainType(final ChainType chainType) {
        return session.getDslContext()
                .selectCount()
                .from(Chains)
                .join(ChainDetails).onKey(CHAINS_ACTIVE_DETAIL_FK)
                .where(ChainDetails.CHAIN_TYPE.eq(chainType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countChainsByChainInstanceSequence(final Sequence chainInstanceSequence) {
        return session.getDslContext()
                .selectCount()
                .from(Chains)
                .join(ChainDetails).onKey(CHAINS_ACTIVE_DETAIL_FK)
                .where(ChainDetails.CHAIN_INSTANCE_SEQUENCE.eq(chainInstanceSequence.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<Chain> getChainsByChainType(ChainType chainType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Chains.fields())
                .from(Chains)
                .join(ChainDetails).onKey(CHAINS_ACTIVE_DETAIL_FK)
                .where(ChainDetails.CHAIN_TYPE.eq(chainType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ChainDetails.SORT_ORDER, ChainDetails.CHAIN_NAME),
                    ChainFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return chainFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<Chain> getChainsByChainType(ChainType chainType) {
        return getChainsByChainType(chainType, EntityPermission.READ_ONLY);
    }

    public List<Chain> getChainsByChainTypeForUpdate(ChainType chainType) {
        return getChainsByChainType(chainType, EntityPermission.READ_WRITE);
    }

    private List<Chain> getChainsByChainInstanceSequence(Sequence chainInstanceSequence, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(Chains.fields())
                    .from(Chains)
                    .join(ChainDetails).onKey(CHAINS_ACTIVE_DETAIL_FK)
                    .join(ChainTypes).onKey(CHAIN_DETAILS_CHAIN_TYPE_FK)
                    .join(ChainTypeDetails).onKey(CHAIN_TYPES_LAST_DETAIL_FK)
                    .join(ChainKinds).onKey(CHAIN_TYPE_DETAILS_CHAIN_KIND_FK)
                    .join(ChainKindDetails).onKey(CHAIN_KINDS_LAST_DETAIL_FK)
                    .where(ChainDetails.CHAIN_INSTANCE_SEQUENCE.eq(chainInstanceSequence.getPrimaryKey()))
                    .orderBy(ChainKindDetails.SORT_ORDER, ChainKindDetails.CHAIN_KIND_NAME, ChainTypeDetails.SORT_ORDER, ChainTypeDetails.CHAIN_TYPE_NAME, ChainDetails.SORT_ORDER, ChainDetails.CHAIN_NAME),
                    ChainFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(Chains.fields())
                    .from(Chains)
                    .join(ChainDetails).onKey(CHAINS_ACTIVE_DETAIL_FK)
                    .where(ChainDetails.CHAIN_INSTANCE_SEQUENCE.eq(chainInstanceSequence.getPrimaryKey()))
                    .forUpdate();
        };

        return chainFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<Chain> getChainsByChainInstanceSequence(Sequence chainInstanceSequence) {
        return getChainsByChainInstanceSequence(chainInstanceSequence, EntityPermission.READ_ONLY);
    }

    public List<Chain> getChainsByChainInstanceSequenceForUpdate(Sequence chainInstanceSequence) {
        return getChainsByChainInstanceSequence(chainInstanceSequence, EntityPermission.READ_WRITE);
    }

    public Chain getDefaultChain(ChainType chainType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Chains.fields())
                .from(Chains)
                .join(ChainDetails).onKey(CHAINS_ACTIVE_DETAIL_FK)
                .where(ChainDetails.CHAIN_TYPE.eq(chainType.getPrimaryKey()),
                        ChainDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return chainFactory.getEntityFromQuery(entityPermission, query);
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

    public Chain getChainByName(ChainType chainType, String chainName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Chains.fields())
                .from(Chains)
                .join(ChainDetails).onKey(CHAINS_ACTIVE_DETAIL_FK)
                .where(ChainDetails.CHAIN_TYPE.eq(chainType.getPrimaryKey()),
                        ChainDetails.CHAIN_NAME.eq(chainName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return chainFactory.getEntityFromQuery(entityPermission, query);
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
        return chainTransferCache.getChainTransfer(userVisit, chain);
    }

    public List<ChainTransfer> getChainTransfersByChainType(UserVisit userVisit, ChainType chainType) {
        var chains = getChainsByChainType(chainType);
        List<ChainTransfer> chainTransfers = new ArrayList<>(chains.size());

        chains.forEach((chain) ->
                chainTransfers.add(chainTransferCache.getChainTransfer(userVisit, chain))
        );

        return chainTransfers;
    }

    private void updateChainFromValue(ChainDetailValue chainDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(chainDetailValue.hasBeenModified()) {
            var chain = chainFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     chainDetailValue.getChainPK());
            var chainDetail = chain.getActiveDetailForUpdate();

            chainDetail.setThruTime(session.getStartTime());
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

            chainDetail = chainDetailFactory.create(chainPK, chainTypePK, chainName, chainInstanceSequencePK, isDefault, sortOrder,
                    session.getStartTime(), Session.MAX_TIME);

            chain.setActiveDetail(chainDetail);
            chain.setLastDetail(chainDetail);

            sendEvent(chainPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateChainFromValue(ChainDetailValue chainDetailValue, BasePK updatedBy) {
        updateChainFromValue(chainDetailValue, true, updatedBy);
    }

    public void deleteChain(Chain chain, BasePK deletedBy) {
        
        contactListControl.deleteContactListTypesByChain(chain, deletedBy);
        offerControl.deleteOfferChainTypesByChain(chain, deletedBy);
        deleteChainInstancesByChain(chain, deletedBy);
        deleteChainActionSetsByChain(chain, deletedBy);
        deleteChainDescriptionsByChain(chain, deletedBy);

        var chainDetail = chain.getLastDetailForUpdate();
        chainDetail.setThruTime(session.getStartTime());
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

    @Inject
    protected ChainDescriptionFactory chainDescriptionFactory;

    @Inject
    ChainDescriptionTransferCache chainDescriptionTransferCache;

    public ChainDescription createChainDescription(Chain chain, Language language, String description,
            BasePK createdBy) {
        var chainDescription = chainDescriptionFactory.create(chain,
                language, description, session.getStartTime(), Session.MAX_TIME);

        sendEvent(chain.getPrimaryKey(), EventTypes.MODIFY, chainDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return chainDescription;
    }

    private ChainDescription getChainDescription(Chain chain, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ChainDescriptions.fields())
                .from(ChainDescriptions)
                .where(ChainDescriptions.CHAIN.eq(chain.getPrimaryKey()),
                        ChainDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        ChainDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return chainDescriptionFactory.getEntityFromQuery(entityPermission, query);
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

    private List<ChainDescription> getChainDescriptionsByChain(Chain chain, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ChainDescriptions.fields())
                    .from(ChainDescriptions)
                    .join(Languages).onKey(CHAIN_DESCRIPTIONS_LANGUAGE_FK)
                    .where(ChainDescriptions.CHAIN.eq(chain.getPrimaryKey()),
                            ChainDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    ChainDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ChainDescriptions.fields())
                    .from(ChainDescriptions)
                    .where(ChainDescriptions.CHAIN.eq(chain.getPrimaryKey()),
                            ChainDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return chainDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
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
        return chainDescriptionTransferCache.getChainDescriptionTransfer(userVisit, chainDescription);
    }

    public List<ChainDescriptionTransfer> getChainDescriptionTransfersByChain(UserVisit userVisit, Chain chain) {
        var chainDescriptions = getChainDescriptionsByChain(chain);
        List<ChainDescriptionTransfer> chainDescriptionTransfers = new ArrayList<>(chainDescriptions.size());

        chainDescriptions.forEach((chainDescription) -> {
            chainDescriptionTransfers.add(chainDescriptionTransferCache.getChainDescriptionTransfer(userVisit, chainDescription));
        });

        return chainDescriptionTransfers;
    }

    public void updateChainDescriptionFromValue(ChainDescriptionValue chainDescriptionValue, BasePK updatedBy) {
        if(chainDescriptionValue.hasBeenModified()) {
            var chainDescription = chainDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     chainDescriptionValue.getPrimaryKey());

            chainDescription.setThruTime(session.getStartTime());
            chainDescription.store();

            var chain = chainDescription.getChain();
            var language = chainDescription.getLanguage();
            var description = chainDescriptionValue.getDescription();

            chainDescription = chainDescriptionFactory.create(chain, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(chain.getPrimaryKey(), EventTypes.MODIFY, chainDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteChainDescription(ChainDescription chainDescription, BasePK deletedBy) {
        chainDescription.setThruTime(session.getStartTime());

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

    @Inject
    protected ChainActionSetDetailFactory chainActionSetDetailFactory;

    @Inject
    protected ChainActionSetFactory chainActionSetFactory;

    @Inject
    ChainActionSetTransferCache chainActionSetTransferCache;

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

        var chainActionSet = chainActionSetFactory.create();
        var chainActionSetDetail = chainActionSetDetailFactory.create( chainActionSet, chain, chainActionSetName,
                isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        chainActionSet = chainActionSetFactory.getEntityFromPK(EntityPermission.READ_WRITE, chainActionSet.getPrimaryKey());
        chainActionSet.setActiveDetail(chainActionSetDetail);
        chainActionSet.setLastDetail(chainActionSetDetail);
        chainActionSet.store();

        sendEvent(chainActionSet.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return chainActionSet;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ChainActionSet */
    public ChainActionSet getChainActionSetByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ChainActionSetPK(entityInstance.getEntityUniqueId());

        return chainActionSetFactory.getEntityFromPK(entityPermission, pk);
    }

    public ChainActionSet getChainActionSetByEntityInstance(EntityInstance entityInstance) {
        return getChainActionSetByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ChainActionSet getChainActionSetByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getChainActionSetByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countChainActionSetsByChain(final Chain chain) {
        return session.getDslContext()
                .selectCount()
                .from(ChainActionSets)
                .join(ChainActionSetDetails).onKey(CHAIN_ACTION_SETS_ACTIVE_DETAIL_FK)
                .where(ChainActionSetDetails.CHAIN.eq(chain.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<ChainActionSet> getChainActionSetsByChain(Chain chain, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ChainActionSets.fields())
                .from(ChainActionSets)
                .join(ChainActionSetDetails).onKey(CHAIN_ACTION_SETS_ACTIVE_DETAIL_FK)
                .where(ChainActionSetDetails.CHAIN.eq(chain.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ChainActionSetDetails.SORT_ORDER, ChainActionSetDetails.CHAIN_ACTION_SET_NAME),
                    ChainActionSetFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return chainActionSetFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ChainActionSet> getChainActionSetsByChain(Chain chain) {
        return getChainActionSetsByChain(chain, EntityPermission.READ_ONLY);
    }

    public List<ChainActionSet> getChainActionSetsByChainForUpdate(Chain chain) {
        return getChainActionSetsByChain(chain, EntityPermission.READ_WRITE);
    }

    public ChainActionSet getDefaultChainActionSet(Chain chain, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ChainActionSets.fields())
                .from(ChainActionSets)
                .join(ChainActionSetDetails).onKey(CHAIN_ACTION_SETS_ACTIVE_DETAIL_FK)
                .where(ChainActionSetDetails.CHAIN.eq(chain.getPrimaryKey()),
                        ChainActionSetDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return chainActionSetFactory.getEntityFromQuery(entityPermission, query);
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

    public ChainActionSet getChainActionSetByName(Chain chain, String chainActionSetName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ChainActionSets.fields())
                .from(ChainActionSets)
                .join(ChainActionSetDetails).onKey(CHAIN_ACTION_SETS_ACTIVE_DETAIL_FK)
                .where(ChainActionSetDetails.CHAIN.eq(chain.getPrimaryKey()),
                        ChainActionSetDetails.CHAIN_ACTION_SET_NAME.eq(chainActionSetName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return chainActionSetFactory.getEntityFromQuery(entityPermission, query);
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
        return chainActionSetTransferCache.getChainActionSetTransfer(userVisit, chainActionSet);
    }

    public List<ChainActionSetTransfer> getChainActionSetTransfersByChain(UserVisit userVisit, Chain chain) {
        var chainActionSets = getChainActionSetsByChain(chain);
        List<ChainActionSetTransfer> chainActionSetTransfers = new ArrayList<>(chainActionSets.size());

        chainActionSets.forEach((chainActionSet) ->
                chainActionSetTransfers.add(chainActionSetTransferCache.getChainActionSetTransfer(userVisit, chainActionSet))
        );

        return chainActionSetTransfers;
    }

    private void updateChainActionSetFromValue(ChainActionSetDetailValue chainActionSetDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(chainActionSetDetailValue.hasBeenModified()) {
            var chainActionSet = chainActionSetFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     chainActionSetDetailValue.getChainActionSetPK());
            var chainActionSetDetail = chainActionSet.getActiveDetailForUpdate();

            chainActionSetDetail.setThruTime(session.getStartTime());
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

            chainActionSetDetail = chainActionSetDetailFactory.create(chainActionSetPK, chainPK, chainActionSetName, isDefault, sortOrder,
                    session.getStartTime(), Session.MAX_TIME);

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
        chainActionSetDetail.setThruTime(session.getStartTime());
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

    @Inject
    protected ChainActionSetDescriptionFactory chainActionSetDescriptionFactory;

    @Inject
    ChainActionSetDescriptionTransferCache chainActionSetDescriptionTransferCache;

    public ChainActionSetDescription createChainActionSetDescription(ChainActionSet chainActionSet, Language language, String description, BasePK createdBy) {
        var chainActionSetDescription = chainActionSetDescriptionFactory.create(chainActionSet,
                language, description, session.getStartTime(), Session.MAX_TIME);

        sendEvent(chainActionSet.getPrimaryKey(), EventTypes.MODIFY, chainActionSetDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return chainActionSetDescription;
    }

    private ChainActionSetDescription getChainActionSetDescription(ChainActionSet chainActionSet, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ChainActionSetDescriptions.fields())
                .from(ChainActionSetDescriptions)
                .where(ChainActionSetDescriptions.CHAIN_ACTION_SET.eq(chainActionSet.getPrimaryKey()),
                        ChainActionSetDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        ChainActionSetDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return chainActionSetDescriptionFactory.getEntityFromQuery(entityPermission, query);
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

    private List<ChainActionSetDescription> getChainActionSetDescriptionsByChainActionSet(ChainActionSet chainActionSet, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ChainActionSetDescriptions.fields())
                    .from(ChainActionSetDescriptions)
                    .join(Languages).onKey(CHAIN_ACTION_SET_DESCRIPTIONS_LANGUAGE_FK)
                    .where(ChainActionSetDescriptions.CHAIN_ACTION_SET.eq(chainActionSet.getPrimaryKey()),
                            ChainActionSetDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    ChainActionSetDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ChainActionSetDescriptions.fields())
                    .from(ChainActionSetDescriptions)
                    .where(ChainActionSetDescriptions.CHAIN_ACTION_SET.eq(chainActionSet.getPrimaryKey()),
                            ChainActionSetDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return chainActionSetDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
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
        return chainActionSetDescriptionTransferCache.getChainActionSetDescriptionTransfer(userVisit, chainActionSetDescription);
    }

    public List<ChainActionSetDescriptionTransfer> getChainActionSetDescriptionTransfersByChainActionSet(UserVisit userVisit, ChainActionSet chainActionSet) {
        var chainActionSetDescriptions = getChainActionSetDescriptionsByChainActionSet(chainActionSet);
        List<ChainActionSetDescriptionTransfer> chainActionSetDescriptionTransfers = new ArrayList<>(chainActionSetDescriptions.size());

        chainActionSetDescriptions.forEach((chainActionSetDescription) -> {
            chainActionSetDescriptionTransfers.add(chainActionSetDescriptionTransferCache.getChainActionSetDescriptionTransfer(userVisit, chainActionSetDescription));
        });

        return chainActionSetDescriptionTransfers;
    }

    public void updateChainActionSetDescriptionFromValue(ChainActionSetDescriptionValue chainActionSetDescriptionValue, BasePK updatedBy) {
        if(chainActionSetDescriptionValue.hasBeenModified()) {
            var chainActionSetDescription = chainActionSetDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     chainActionSetDescriptionValue.getPrimaryKey());

            chainActionSetDescription.setThruTime(session.getStartTime());
            chainActionSetDescription.store();

            var chainActionSet = chainActionSetDescription.getChainActionSet();
            var language = chainActionSetDescription.getLanguage();
            var description = chainActionSetDescriptionValue.getDescription();

            chainActionSetDescription = chainActionSetDescriptionFactory.create(chainActionSet, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(chainActionSet.getPrimaryKey(), EventTypes.MODIFY, chainActionSetDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteChainActionSetDescription(ChainActionSetDescription chainActionSetDescription, BasePK deletedBy) {
        chainActionSetDescription.setThruTime(session.getStartTime());

        sendEvent(chainActionSetDescription.getChainActionSetPK(), EventTypes.MODIFY, chainActionSetDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteChainActionSetDescriptionsByChainActionSet(ChainActionSet chainActionSet, BasePK deletedBy) {
        var chainActionSetDescriptions = getChainActionSetDescriptionsByChainActionSetForUpdate(chainActionSet);

        chainActionSetDescriptions.forEach((chainActionSetDescription) -> 
                deleteChainActionSetDescription(chainActionSetDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Chain Action Types
    // --------------------------------------------------------------------------------

    @Inject
    protected ChainActionTypeDetailFactory chainActionTypeDetailFactory;

    @Inject
    protected ChainActionTypeFactory chainActionTypeFactory;

    @Inject
    ChainActionTypeTransferCache chainActionTypeTransferCache;

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

        var chainActionType = chainActionTypeFactory.create();
        var chainActionTypeDetail = chainActionTypeDetailFactory.create(chainActionType, chainActionTypeName, allowMultiple,
                isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        chainActionType = chainActionTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                chainActionType.getPrimaryKey());
        chainActionType.setActiveDetail(chainActionTypeDetail);
        chainActionType.setLastDetail(chainActionTypeDetail);
        chainActionType.store();

        sendEvent(chainActionType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return chainActionType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ChainActionType */
    public ChainActionType getChainActionTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ChainActionTypePK(entityInstance.getEntityUniqueId());

        return chainActionTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public ChainActionType getChainActionTypeByEntityInstance(EntityInstance entityInstance) {
        return getChainActionTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ChainActionType getChainActionTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getChainActionTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countChainActionTypes() {
        return session.getDslContext()
                .selectCount()
                .from(ChainActionTypes)
                .join(ChainActionTypeDetails).onKey(CHAIN_ACTION_TYPES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public ChainActionType getChainActionTypeByName(String chainActionTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ChainActionTypes.fields())
                .from(ChainActionTypes)
                .join(ChainActionTypeDetails).onKey(CHAIN_ACTION_TYPES_ACTIVE_DETAIL_FK)
                .where(ChainActionTypeDetails.CHAIN_ACTION_TYPE_NAME.eq(chainActionTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return chainActionTypeFactory.getEntityFromQuery(entityPermission, query);
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

    public ChainActionType getDefaultChainActionType(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ChainActionTypes.fields())
                .from(ChainActionTypes)
                .join(ChainActionTypeDetails).onKey(CHAIN_ACTION_TYPES_ACTIVE_DETAIL_FK)
                .where(ChainActionTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return chainActionTypeFactory.getEntityFromQuery(entityPermission, query);
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

    private List<ChainActionType> getChainActionTypes(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ChainActionTypes.fields())
                .from(ChainActionTypes)
                .join(ChainActionTypeDetails).onKey(CHAIN_ACTION_TYPES_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ChainActionTypeDetails.SORT_ORDER, ChainActionTypeDetails.CHAIN_ACTION_TYPE_NAME),
                    ChainActionTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return chainActionTypeFactory.getEntitiesFromQuery(entityPermission, query);
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
        return chainActionTypeTransferCache.getChainActionTypeTransfer(userVisit, chainActionType);
    }

    public List<ChainActionTypeTransfer> getChainActionTypeTransfers(UserVisit userVisit, Collection<ChainActionType> chainActionTypes) {
        List<ChainActionTypeTransfer> chainActionTypeTransfers = new ArrayList<>(chainActionTypes.size());

        chainActionTypes.forEach((chainActionType) ->
                chainActionTypeTransfers.add(chainActionTypeTransferCache.getChainActionTypeTransfer(userVisit, chainActionType))
        );

        return chainActionTypeTransfers;
    }

    public List<ChainActionTypeTransfer> getChainActionTypeTransfers(UserVisit userVisit) {
        return getChainActionTypeTransfers(userVisit, getChainActionTypes());
    }

    private void updateChainActionTypeFromValue(ChainActionTypeDetailValue chainActionTypeDetailValue, boolean checkDefault, BasePK updatedBy) {
        var chainActionType = chainActionTypeFactory.getEntityFromPK(
                EntityPermission.READ_WRITE, chainActionTypeDetailValue.getChainActionTypePK());
        var chainActionTypeDetail = chainActionType.getActiveDetailForUpdate();

        chainActionTypeDetail.setThruTime(session.getStartTime());
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

        chainActionTypeDetail = chainActionTypeDetailFactory.create(chainActionTypePK, chainActionTypeName, allowMultiple, isDefault, sortOrder,
                session.getStartTime(), Session.MAX_TIME);

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
        chainActionTypeDetail.setThruTime(session.getStartTime());
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
    //   Chain Action Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected ChainActionTypeDescriptionFactory chainActionTypeDescriptionFactory;

    @Inject
    ChainActionTypeDescriptionTransferCache chainActionTypeDescriptionTransferCache;

    public ChainActionTypeDescription createChainActionTypeDescription(ChainActionType chainActionType, Language language, String description,
            BasePK createdBy) {
        var chainActionTypeDescription = chainActionTypeDescriptionFactory.create(chainActionType,
                language, description, session.getStartTime(), Session.MAX_TIME);

        sendEvent(chainActionType.getPrimaryKey(), EventTypes.MODIFY, chainActionTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return chainActionTypeDescription;
    }

    private ChainActionTypeDescription getChainActionTypeDescription(ChainActionType chainActionType, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ChainActionTypeDescriptions.fields())
                .from(ChainActionTypeDescriptions)
                .where(ChainActionTypeDescriptions.CHAIN_ACTION_TYPE.eq(chainActionType.getPrimaryKey()),
                        ChainActionTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        ChainActionTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return chainActionTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
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

    private List<ChainActionTypeDescription> getChainActionTypeDescriptionsByChainActionType(ChainActionType chainActionType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ChainActionTypeDescriptions.fields())
                    .from(ChainActionTypeDescriptions)
                    .join(Languages).onKey(CHAIN_ACTION_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(ChainActionTypeDescriptions.CHAIN_ACTION_TYPE.eq(chainActionType.getPrimaryKey()),
                            ChainActionTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    ChainActionTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ChainActionTypeDescriptions.fields())
                    .from(ChainActionTypeDescriptions)
                    .where(ChainActionTypeDescriptions.CHAIN_ACTION_TYPE.eq(chainActionType.getPrimaryKey()),
                            ChainActionTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return chainActionTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
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
        return chainActionTypeDescriptionTransferCache.getChainActionTypeDescriptionTransfer(userVisit, chainActionTypeDescription);
    }

    public List<ChainActionTypeDescriptionTransfer> getChainActionTypeDescriptionTransfersByChainActionType(UserVisit userVisit, ChainActionType chainActionType) {
        var chainActionTypeDescriptions = getChainActionTypeDescriptionsByChainActionType(chainActionType);
        List<ChainActionTypeDescriptionTransfer> chainActionTypeDescriptionTransfers = new ArrayList<>(chainActionTypeDescriptions.size());

        chainActionTypeDescriptions.forEach((chainActionTypeDescription) -> {
            chainActionTypeDescriptionTransfers.add(chainActionTypeDescriptionTransferCache.getChainActionTypeDescriptionTransfer(userVisit, chainActionTypeDescription));
        });

        return chainActionTypeDescriptionTransfers;
    }

    public void updateChainActionTypeDescriptionFromValue(ChainActionTypeDescriptionValue chainActionTypeDescriptionValue, BasePK updatedBy) {
        if(chainActionTypeDescriptionValue.hasBeenModified()) {
            var chainActionTypeDescription = chainActionTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     chainActionTypeDescriptionValue.getPrimaryKey());

            chainActionTypeDescription.setThruTime(session.getStartTime());
            chainActionTypeDescription.store();

            var chainActionType = chainActionTypeDescription.getChainActionType();
            var language = chainActionTypeDescription.getLanguage();
            var description = chainActionTypeDescriptionValue.getDescription();

            chainActionTypeDescription = chainActionTypeDescriptionFactory.create(chainActionType, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(chainActionType.getPrimaryKey(), EventTypes.MODIFY, chainActionTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteChainActionTypeDescription(ChainActionTypeDescription chainActionTypeDescription, BasePK deletedBy) {
        chainActionTypeDescription.setThruTime(session.getStartTime());

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

    @Inject
    protected ChainActionTypeUseFactory chainActionTypeUseFactory;

    @Inject
    ChainActionTypeUseTransferCache chainActionTypeUseTransferCache;

    public ChainActionTypeUse createChainActionTypeUse(ChainKind chainKind, ChainActionType chainActionType,
            Boolean isDefault) {
        return chainActionTypeUseFactory.create(chainKind, chainActionType, isDefault);
    }

    public long countChainActionTypeUsesByChainKind(final ChainKind chainKind) {
        return session.getDslContext()
                .selectCount()
                .from(ChainActionTypeUses)
                .where(ChainActionTypeUses.CHAIN_KIND.eq(chainKind.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countChainActionTypeUsesByChainActionType(final ChainActionType chainActionType) {
        return session.getDslContext()
                .selectCount()
                .from(ChainActionTypeUses)
                .where(ChainActionTypeUses.CHAIN_ACTION_TYPE.eq(chainActionType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public ChainActionTypeUse getChainActionTypeUse(ChainKind chainKind, ChainActionType chainActionType) {
        var query = session.getDslContext()
                .select(ChainActionTypeUses.fields())
                .from(ChainActionTypeUses)
                .where(ChainActionTypeUses.CHAIN_KIND.eq(chainKind.getPrimaryKey()),
                        ChainActionTypeUses.CHAIN_ACTION_TYPE.eq(chainActionType.getPrimaryKey()));

        return chainActionTypeUseFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    // --------------------------------------------------------------------------------
    //   Chain Actions
    // --------------------------------------------------------------------------------

    @Inject
    protected ChainActionDetailFactory chainActionDetailFactory;

    @Inject
    protected ChainActionFactory chainActionFactory;

    @Inject
    ChainActionTransferCache chainActionTransferCache;

    public ChainAction createChainAction(ChainActionSet chainActionSet, String chainActionName, ChainActionType chainActionType, Integer sortOrder,
            BasePK createdBy) {
        var chainAction = chainActionFactory.create();
        var chainActionDetail = chainActionDetailFactory.create(chainAction, chainActionSet, chainActionName, chainActionType, sortOrder, session.getStartTime(),
                Session.MAX_TIME);

        // Convert to R/W
        chainAction = chainActionFactory.getEntityFromPK(EntityPermission.READ_WRITE, chainAction.getPrimaryKey());
        chainAction.setActiveDetail(chainActionDetail);
        chainAction.setLastDetail(chainActionDetail);
        chainAction.store();

        sendEvent(chainAction.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return chainAction;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ChainAction */
    public ChainAction getChainActionByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ChainActionPK(entityInstance.getEntityUniqueId());

        return chainActionFactory.getEntityFromPK(entityPermission, pk);
    }

    public ChainAction getChainActionByEntityInstance(EntityInstance entityInstance) {
        return getChainActionByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ChainAction getChainActionByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getChainActionByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countChainActionsByChainActionSet(final ChainActionSet chainActionSet) {
        return session.getDslContext()
                .selectCount()
                .from(ChainActions)
                .join(ChainActionDetails).onKey(CHAIN_ACTIONS_ACTIVE_DETAIL_FK)
                .where(ChainActionDetails.CHAIN_ACTION_SET.eq(chainActionSet.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countChainActionsByChainActionType(final ChainActionType chainActionType) {
        return session.getDslContext()
                .selectCount()
                .from(ChainActions)
                .join(ChainActionDetails).onKey(CHAIN_ACTIONS_ACTIVE_DETAIL_FK)
                .where(ChainActionDetails.CHAIN_ACTION_TYPE.eq(chainActionType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private ChainAction getChainActionByName(ChainActionSet chainActionSet, String chainActionName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ChainActions.fields())
                .from(ChainActions)
                .join(ChainActionDetails).onKey(CHAIN_ACTIONS_ACTIVE_DETAIL_FK)
                .where(ChainActionDetails.CHAIN_ACTION_SET.eq(chainActionSet.getPrimaryKey()),
                        ChainActionDetails.CHAIN_ACTION_NAME.eq(chainActionName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return chainActionFactory.getEntityFromQuery(entityPermission, query);
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

    private List<ChainAction> getChainActionsByChainActionSet(ChainActionSet chainActionSet, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ChainActions.fields())
                .from(ChainActions)
                .join(ChainActionDetails).onKey(CHAIN_ACTIONS_ACTIVE_DETAIL_FK)
                .where(ChainActionDetails.CHAIN_ACTION_SET.eq(chainActionSet.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ChainActionDetails.SORT_ORDER, ChainActionDetails.CHAIN_ACTION_NAME),
                    ChainActionFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return chainActionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ChainAction> getChainActionsByChainActionSet(ChainActionSet chainActionSet) {
        return getChainActionsByChainActionSet(chainActionSet, EntityPermission.READ_ONLY);
    }

    public List<ChainAction> getChainActionsByChainActionSetForUpdate(ChainActionSet chainActionSet) {
        return getChainActionsByChainActionSet(chainActionSet, EntityPermission.READ_WRITE);
    }

    public ChainActionTransfer getChainActionTransfer(UserVisit userVisit, ChainAction chainAction) {
        return chainActionTransferCache.getChainActionTransfer(userVisit, chainAction);
    }

    public List<ChainActionTransfer> getChainActionTransfersByChainActionSet(UserVisit userVisit, ChainActionSet chainActionSet) {
        var chainActions = getChainActionsByChainActionSet(chainActionSet);
        List<ChainActionTransfer> chainActionTransfers = new ArrayList<>(chainActions.size());

        chainActions.forEach((chainAction) ->
                chainActionTransfers.add(chainActionTransferCache.getChainActionTransfer(userVisit, chainAction))
        );

        return chainActionTransfers;
    }

    public void updateChainActionFromValue(ChainActionDetailValue chainActionDetailValue, BasePK updatedBy) {
        if(chainActionDetailValue.hasBeenModified()) {
            var chainAction = chainActionFactory.getEntityFromPK(EntityPermission.READ_WRITE, chainActionDetailValue.getChainActionPK());
            var chainActionDetail = chainAction.getActiveDetailForUpdate();

            chainActionDetail.setThruTime(session.getStartTime());
            chainActionDetail.store();

            var chainActionPK = chainActionDetail.getChainActionPK(); // Not updated
            var chainActionSet = chainActionDetail.getChainActionSet(); // Not updated
            var chainActionSetPK = chainActionSet.getPrimaryKey(); // Not updated
            var chainActionName = chainActionDetailValue.getChainActionName();
            var chainActionTypePK = chainActionDetail.getChainActionTypePK(); // Not updated
            var sortOrder = chainActionDetailValue.getSortOrder();

            chainActionDetail = chainActionDetailFactory.create(chainActionPK, chainActionSetPK, chainActionName, chainActionTypePK, sortOrder,
                    session.getStartTime(), Session.MAX_TIME);

            chainAction.setActiveDetail(chainActionDetail);
            chainAction.setLastDetail(chainActionDetail);

            sendEvent(chainActionPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void deleteChainAction(ChainAction chainAction, BasePK deletedBy) {
        var chainActionDetail = chainAction.getLastDetailForUpdate();
        var chainActionTypeName = chainActionDetail.getChainActionType().getLastDetail().getChainActionTypeName();
        
        deleteChainActionDescriptionsByChainAction(chainAction, deletedBy);

        if(chainActionTypeName.equals(com.echothree.model.control.chain.common.ChainActionTypes.LETTER.name())) {
            deleteChainActionLetter(getChainActionLetterForUpdate(chainAction), deletedBy);
        } else if(chainActionTypeName.equals(com.echothree.model.control.chain.common.ChainActionTypes.SURVEY.name())) {
            deleteChainActionSurvey(getChainActionSurveyForUpdate(chainAction), deletedBy);
        } else if(chainActionTypeName.equals(com.echothree.model.control.chain.common.ChainActionTypes.CHAIN_ACTION_SET.name())) {
            deleteChainActionChainActionSet(getChainActionChainActionSetForUpdate(chainAction), deletedBy);
        }
        
        chainActionDetail.setThruTime(session.getStartTime());
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

    @Inject
    protected ChainActionDescriptionFactory chainActionDescriptionFactory;

    @Inject
    ChainActionDescriptionTransferCache chainActionDescriptionTransferCache;

    public ChainActionDescription createChainActionDescription(ChainAction chainAction, Language language, String description, BasePK createdBy) {
        var chainActionDescription = chainActionDescriptionFactory.create(chainAction,
                language, description, session.getStartTime(), Session.MAX_TIME);

        sendEvent(chainAction.getPrimaryKey(), EventTypes.MODIFY, chainActionDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return chainActionDescription;
    }

    private ChainActionDescription getChainActionDescription(ChainAction chainAction, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ChainActionDescriptions.fields())
                .from(ChainActionDescriptions)
                .where(ChainActionDescriptions.CHAIN_ACTION.eq(chainAction.getPrimaryKey()),
                        ChainActionDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        ChainActionDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return chainActionDescriptionFactory.getEntityFromQuery(entityPermission, query);
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

    private List<ChainActionDescription> getChainActionDescriptionsByChainAction(ChainAction chainAction, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ChainActionDescriptions.fields())
                    .from(ChainActionDescriptions)
                    .join(Languages).onKey(CHAIN_ACTION_DESCRIPTIONS_LANGUAGE_FK)
                    .where(ChainActionDescriptions.CHAIN_ACTION.eq(chainAction.getPrimaryKey()),
                            ChainActionDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    ChainActionDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ChainActionDescriptions.fields())
                    .from(ChainActionDescriptions)
                    .where(ChainActionDescriptions.CHAIN_ACTION.eq(chainAction.getPrimaryKey()),
                            ChainActionDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return chainActionDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
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
        return chainActionDescriptionTransferCache.getChainActionDescriptionTransfer(userVisit, chainActionDescription);
    }

    public List<ChainActionDescriptionTransfer> getChainActionDescriptionTransfersByChainAction(UserVisit userVisit, ChainAction chainAction) {
        var chainActionDescriptions = getChainActionDescriptionsByChainAction(chainAction);
        List<ChainActionDescriptionTransfer> chainActionDescriptionTransfers = new ArrayList<>(chainActionDescriptions.size());

        chainActionDescriptions.forEach((chainActionDescription) -> {
            chainActionDescriptionTransfers.add(chainActionDescriptionTransferCache.getChainActionDescriptionTransfer(userVisit, chainActionDescription));
        });

        return chainActionDescriptionTransfers;
    }

    public void updateChainActionDescriptionFromValue(ChainActionDescriptionValue chainActionDescriptionValue, BasePK updatedBy) {
        if(chainActionDescriptionValue.hasBeenModified()) {
            var chainActionDescription = chainActionDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     chainActionDescriptionValue.getPrimaryKey());

            chainActionDescription.setThruTime(session.getStartTime());
            chainActionDescription.store();

            var chainAction = chainActionDescription.getChainAction();
            var language = chainActionDescription.getLanguage();
            var description = chainActionDescriptionValue.getDescription();

            chainActionDescription = chainActionDescriptionFactory.create(chainAction, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(chainAction.getPrimaryKey(), EventTypes.MODIFY, chainActionDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteChainActionDescription(ChainActionDescription chainActionDescription, BasePK deletedBy) {
        chainActionDescription.setThruTime(session.getStartTime());

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

    @Inject
    protected ChainActionLetterFactory chainActionLetterFactory;

    @Inject
    ChainActionLetterTransferCache chainActionLetterTransferCache;

    public ChainActionLetter createChainActionLetter(ChainAction chainAction, Letter letter, BasePK createdBy) {
        var chainActionLetter = chainActionLetterFactory.create(chainAction, letter, session.getStartTime(),
                Session.MAX_TIME);

        sendEvent(chainAction.getPrimaryKey(), EventTypes.MODIFY, chainActionLetter.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return chainActionLetter;
    }

    public long countChainActionSetsByChainAction(final ChainAction chainAction) {
        return session.getDslContext()
                .selectCount()
                .from(ChainActionLetters)
                .where(ChainActionLetters.CHAIN_ACTION.eq(chainAction.getPrimaryKey()),
                        ChainActionLetters.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countChainActionSetsByLetter(final Letter letter) {
        return session.getDslContext()
                .selectCount()
                .from(ChainActionLetters)
                .where(ChainActionLetters.LETTER.eq(letter.getPrimaryKey()),
                        ChainActionLetters.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private ChainActionLetter getChainActionLetter(ChainAction chainAction, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ChainActionLetters.fields())
                .from(ChainActionLetters)
                .where(ChainActionLetters.CHAIN_ACTION.eq(chainAction.getPrimaryKey()),
                        ChainActionLetters.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return chainActionLetterFactory.getEntityFromQuery(entityPermission, query);
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
        return chainActionLetterTransferCache.getChainActionLetterTransfer(userVisit, getChainActionLetter(chainAction));
    }

    public void updateChainActionLetterFromValue(ChainActionLetterValue chainActionLetterValue, BasePK updatedBy) {
        if(chainActionLetterValue.hasBeenModified()) {
            var chainActionLetter = chainActionLetterFactory.getEntityFromPK(EntityPermission.READ_WRITE, chainActionLetterValue.getPrimaryKey());

            chainActionLetter.setThruTime(session.getStartTime());
            chainActionLetter.store();

            var chainAction = chainActionLetter.getChainAction();
            var letterPK = chainActionLetterValue.getLetterPK();

            chainActionLetter = chainActionLetterFactory.create(chainAction.getPrimaryKey(), letterPK, session.getStartTime(),
                    Session.MAX_TIME);

            sendEvent(chainAction.getPrimaryKey(), EventTypes.MODIFY, chainActionLetter.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteChainActionLetter(ChainActionLetter chainActionLetter, BasePK deletedBy) {
        chainActionLetter.setThruTime(session.getStartTime());

        sendEvent(chainActionLetter.getChainActionPK(), EventTypes.MODIFY, chainActionLetter.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Chain Action Surveys
    // --------------------------------------------------------------------------------

    @Inject
    protected ChainActionSurveyFactory chainActionSurveyFactory;

    @Inject
    ChainActionSurveyTransferCache chainActionSurveyTransferCache;

    public ChainActionSurvey createChainActionSurvey(ChainAction chainAction, Survey survey, BasePK createdBy) {
        var chainActionSurvey = chainActionSurveyFactory.create(chainAction, survey, session.getStartTime(),
                Session.MAX_TIME);

        sendEvent(chainAction.getPrimaryKey(), EventTypes.MODIFY, chainActionSurvey.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return chainActionSurvey;
    }

    public long countChainActionSurverysByChainAction(final ChainAction chainAction) {
        return session.getDslContext()
                .selectCount()
                .from(ChainActionSurveys)
                .where(ChainActionSurveys.CHAIN_ACTION.eq(chainAction.getPrimaryKey()),
                        ChainActionSurveys.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countChainActionSurverysBySurvey(final Survey survey) {
        return session.getDslContext()
                .selectCount()
                .from(ChainActionSurveys)
                .where(ChainActionSurveys.SURVEY.eq(survey.getPrimaryKey()),
                        ChainActionSurveys.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private ChainActionSurvey getChainActionSurvey(ChainAction chainAction, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ChainActionSurveys.fields())
                .from(ChainActionSurveys)
                .where(ChainActionSurveys.CHAIN_ACTION.eq(chainAction.getPrimaryKey()),
                        ChainActionSurveys.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return chainActionSurveyFactory.getEntityFromQuery(entityPermission, query);
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
        return chainActionSurveyTransferCache.getChainActionSurveyTransfer(userVisit, getChainActionSurvey(chainAction));
    }

    public void updateChainActionSurveyFromValue(ChainActionSurveyValue chainActionSurveyValue, BasePK updatedBy) {
        if(chainActionSurveyValue.hasBeenModified()) {
            var chainActionSurvey = chainActionSurveyFactory.getEntityFromPK(EntityPermission.READ_WRITE, chainActionSurveyValue.getPrimaryKey());

            chainActionSurvey.setThruTime(session.getStartTime());
            chainActionSurvey.store();

            var chainAction = chainActionSurvey.getChainAction();
            var surveyPK = chainActionSurveyValue.getSurveyPK();

            chainActionSurvey = chainActionSurveyFactory.create(chainAction.getPrimaryKey(), surveyPK, session.getStartTime(),
                    Session.MAX_TIME);

            sendEvent(chainAction.getPrimaryKey(), EventTypes.MODIFY, chainActionSurvey.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteChainActionSurvey(ChainActionSurvey chainActionSurvey, BasePK deletedBy) {
        chainActionSurvey.setThruTime(session.getStartTime());

        sendEvent(chainActionSurvey.getChainActionPK(), EventTypes.MODIFY, chainActionSurvey.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Chain Action Chain Action Sets
    // --------------------------------------------------------------------------------

    @Inject
    protected ChainActionChainActionSetFactory chainActionChainActionSetFactory;

    @Inject
    ChainActionChainActionSetTransferCache chainActionChainActionSetTransferCache;

    public ChainActionChainActionSet createChainActionChainActionSet(ChainAction chainAction, ChainActionSet nextChainActionSet, Long delayTime, BasePK createdBy) {
        var chainActionChainActionSet = chainActionChainActionSetFactory.create(chainAction, nextChainActionSet, delayTime, session.getStartTime(),
                Session.MAX_TIME);

        sendEvent(chainAction.getPrimaryKey(), EventTypes.MODIFY, chainActionChainActionSet.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return chainActionChainActionSet;
    }

    public long countChainActionChainActionSetsByChainAction(final ChainAction chainAction) {
        return session.getDslContext()
                .selectCount()
                .from(ChainActionChainActionSets)
                .where(ChainActionChainActionSets.CHAIN_ACTION.eq(chainAction.getPrimaryKey()),
                        ChainActionChainActionSets.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countChainActionChainActionSetsByNextChainAction(final ChainActionSet nextChainActionSet) {
        return session.getDslContext()
                .selectCount()
                .from(ChainActionChainActionSets)
                .where(ChainActionChainActionSets.NEXT_CHAIN_ACTION_SET.eq(nextChainActionSet.getPrimaryKey()),
                        ChainActionChainActionSets.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private ChainActionChainActionSet getChainActionChainActionSet(ChainAction chainAction, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ChainActionChainActionSets.fields())
                .from(ChainActionChainActionSets)
                .where(ChainActionChainActionSets.CHAIN_ACTION.eq(chainAction.getPrimaryKey()),
                        ChainActionChainActionSets.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return chainActionChainActionSetFactory.getEntityFromQuery(entityPermission, query);
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
        return chainActionChainActionSetTransferCache.getChainActionChainActionSetTransfer(userVisit, getChainActionChainActionSet(chainAction));
    }

    public void updateChainActionChainActionSetFromValue(ChainActionChainActionSetValue chainActionChainActionSetValue, BasePK updatedBy) {
        if(chainActionChainActionSetValue.hasBeenModified()) {
            var chainActionChainActionSet = chainActionChainActionSetFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     chainActionChainActionSetValue.getPrimaryKey());

            chainActionChainActionSet.setThruTime(session.getStartTime());
            chainActionChainActionSet.store();

            var chainAction = chainActionChainActionSet.getChainAction();
            var nextChainActionSetPK = chainActionChainActionSetValue.getNextChainActionSetPK();
            var delayTime = chainActionChainActionSetValue.getDelayTime();

            chainActionChainActionSet = chainActionChainActionSetFactory.create(chainAction.getPrimaryKey(), nextChainActionSetPK, delayTime, session.getStartTime(),
                    Session.MAX_TIME);

            sendEvent(chainAction.getPrimaryKey(), EventTypes.MODIFY, chainActionChainActionSet.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteChainActionChainActionSet(ChainActionChainActionSet chainActionChainActionSet, BasePK deletedBy) {
        chainActionChainActionSet.setThruTime(session.getStartTime());

        sendEvent(chainActionChainActionSet.getChainActionPK(), EventTypes.MODIFY, chainActionChainActionSet.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Chain Instances
    // --------------------------------------------------------------------------------

    @Inject
    protected ChainInstanceDetailFactory chainInstanceDetailFactory;

    @Inject
    protected ChainInstanceFactory chainInstanceFactory;

    @Inject
    ChainInstanceTransferCache chainInstanceTransferCache;

    public ChainInstance createChainInstance(String chainInstanceName, ChainActionSet defaultChainActionSet, BasePK createdBy) {
        var chainInstance = chainInstanceFactory.create();
        var chainInstanceDetail = chainInstanceDetailFactory.create(chainInstance,
                chainInstanceName, defaultChainActionSet.getLastDetail().getChain(), session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        chainInstance = chainInstanceFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                chainInstance.getPrimaryKey());
        chainInstance.setActiveDetail(chainInstanceDetail);
        chainInstance.setLastDetail(chainInstanceDetail);
        chainInstance.store();
        
        sendEvent(chainInstance.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        createChainInstanceStatus(chainInstance, defaultChainActionSet);
        
        return chainInstance;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ChainInstance */
    public ChainInstance getChainInstanceByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ChainInstancePK(entityInstance.getEntityUniqueId());

        return chainInstanceFactory.getEntityFromPK(entityPermission, pk);
    }

    public ChainInstance getChainInstanceByEntityInstance(EntityInstance entityInstance) {
        return getChainInstanceByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ChainInstance getChainInstanceByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getChainInstanceByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countChainInstancesByChain(final Chain chain) {
        return session.getDslContext()
                .selectCount()
                .from(ChainInstances)
                .join(ChainInstanceDetails).onKey(CHAIN_INSTANCES_ACTIVE_DETAIL_FK)
                .where(ChainInstanceDetails.CHAIN.eq(chain.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public boolean isChainInstanceUsed(ChainInstance chainInstance) {

        return letterControl.isChainInstanceUsedByQueuedLetters(chainInstance);
    }

    public ChainInstance getChainInstanceByName(String chainInstanceName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ChainInstances.fields())
                .from(ChainInstances)
                .join(ChainInstanceDetails).onKey(CHAIN_INSTANCES_ACTIVE_DETAIL_FK)
                .where(ChainInstanceDetails.CHAIN_INSTANCE_NAME.eq(chainInstanceName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return chainInstanceFactory.getEntityFromQuery(entityPermission, query);
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
        var baseQuery = session.getDslContext()
                .select(ChainInstances.fields())
                .from(ChainInstances)
                .join(ChainInstanceDetails).onKey(CHAIN_INSTANCES_ACTIVE_DETAIL_FK)
                .where(ChainInstanceDetails.CHAIN.eq(chain.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ChainInstanceDetails.CHAIN_INSTANCE_NAME),
                    ChainInstanceFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return chainInstanceFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ChainInstance> getChainInstancesByChain(Chain chain) {
        return getChainInstancesByChain(chain, EntityPermission.READ_ONLY);
    }
    
    public List<ChainInstance> getChainInstancesByChainForUpdate(Chain chain) {
        return getChainInstancesByChain(chain, EntityPermission.READ_WRITE);
    }
    
    public ChainInstanceTransfer getChainInstanceTransfer(UserVisit userVisit, ChainInstance chainInstance) {
        return chainInstanceTransferCache.getChainInstanceTransfer(userVisit, chainInstance);
    }
    
    public List<ChainInstanceTransfer> getChainInstanceTransfers(UserVisit userVisit, Collection<ChainInstance> chainInstances) {
        List<ChainInstanceTransfer> chainInstanceTransfers = new ArrayList<>(chainInstances.size());
        
        chainInstances.forEach((chainInstance) ->
                chainInstanceTransfers.add(chainInstanceTransferCache.getChainInstanceTransfer(userVisit, chainInstance))
        );
        
        return chainInstanceTransfers;
    }
    
    public List<ChainInstanceTransfer> getChainInstanceTransfersByChain(UserVisit userVisit, Chain chain) {
        return getChainInstanceTransfers(userVisit, getChainInstancesByChain(chain));
    }
    
    public void deleteChainInstance(ChainInstance chainInstance, BasePK deletedBy) {

        removeChainInstanceStatusByChainInstance(chainInstance);
        letterControl.removedQueuedLettersByChainInstance(chainInstance);
        deleteChainInstanceEntityRolesByChainInstance(chainInstance, deletedBy);

        var chainInstanceDetail = chainInstance.getLastDetailForUpdate();
        chainInstanceDetail.setThruTime(session.getStartTime());
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

    @Inject
    protected ChainInstanceStatusFactory chainInstanceStatusFactory;

    @Inject
    ChainInstanceStatusTransferCache chainInstanceStatusTransferCache;

    public ChainInstanceStatus createChainInstanceStatus(ChainInstance chainInstance, ChainActionSet defaultChainActionSet) {
        return createChainInstanceStatus(chainInstance, defaultChainActionSet, session.getStartTime());
    }
    
    public ChainInstanceStatus createChainInstanceStatus(ChainInstance chainInstance, ChainActionSet nextChainActionSet, Long nextChainActionSetTime) {
        return chainInstanceStatusFactory.create(chainInstance, nextChainActionSet, nextChainActionSetTime, 0);
    }
    
    public ChainInstanceStatus getChainInstanceStatus(ChainInstance chainInstance, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ChainInstanceStatuses.fields())
                .from(ChainInstanceStatuses)
                .where(ChainInstanceStatuses.CHAIN_INSTANCE.eq(chainInstance.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return chainInstanceStatusFactory.getEntityFromQuery(entityPermission, query);
    }

    public ChainInstanceStatus getChainInstanceStatus(ChainInstance chainInstance) {
        return getChainInstanceStatus(chainInstance, EntityPermission.READ_ONLY);
    }

    public ChainInstanceStatus getChainInstanceStatusForUpdate(ChainInstance chainInstance) {
        return getChainInstanceStatus(chainInstance, EntityPermission.READ_WRITE);
    }

    public List<ChainInstanceStatus> getChainInstanceStatusesByNextChainActionSet(ChainActionSet nextChainActionSet, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ChainInstanceStatuses.fields())
                .from(ChainInstanceStatuses)
                .where(ChainInstanceStatuses.NEXT_CHAIN_ACTION_SET.eq(nextChainActionSet.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ChainInstanceStatuses.NEXT_CHAIN_ACTION_SET_TIME),
                    ChainInstanceStatusFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return chainInstanceStatusFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ChainInstanceStatus> getChainInstanceStatusesByNextChainActionSet(ChainActionSet nextChainActionSet) {
        return getChainInstanceStatusesByNextChainActionSet(nextChainActionSet, EntityPermission.READ_ONLY);
    }

    public List<ChainInstanceStatus> getChainInstanceStatusesByNextChainActionSetForUpdate(ChainActionSet nextChainActionSet) {
        return getChainInstanceStatusesByNextChainActionSet(nextChainActionSet, EntityPermission.READ_WRITE);
    }

    public List<ChainInstanceStatus> getChainInstanceStatusesByNextChainActionSetTime(Long nextChainActionSetTime, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ChainInstanceStatuses.fields())
                .from(ChainInstanceStatuses)
                .where(ChainInstanceStatuses.NEXT_CHAIN_ACTION_SET_TIME.lt(nextChainActionSetTime));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ChainInstanceStatuses.NEXT_CHAIN_ACTION_SET_TIME),
                    ChainInstanceStatusFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return chainInstanceStatusFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ChainInstanceStatus> getChainInstanceStatusesByNextChainActionSetTime(Long nextChainActionSetTime) {
        return getChainInstanceStatusesByNextChainActionSetTime(nextChainActionSetTime, EntityPermission.READ_ONLY);
    }

    public List<ChainInstanceStatus> getChainInstanceStatusesByNextChainActionSetTimeForUpdate(Long nextChainActionSetTime) {
        return getChainInstanceStatusesByNextChainActionSetTime(nextChainActionSetTime, EntityPermission.READ_WRITE);
    }

    public ChainInstanceStatusTransfer getChainInstanceStatusTransfer(UserVisit userVisit, ChainInstanceStatus chainInstanceStatus) {
        return chainInstanceStatusTransferCache.getChainInstanceStatusTransfer(userVisit, chainInstanceStatus);
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

    @Inject
    protected ChainInstanceEntityRoleFactory chainInstanceEntityRoleFactory;

    @Inject
    ChainInstanceEntityRoleTransferCache chainInstanceEntityRoleTransferCache;

    public ChainInstanceEntityRole createChainInstanceEntityRole(ChainInstance chainInstance, ChainEntityRoleType chainEntityRoleType, BasePK pk,
            BasePK createdBy) {
        var entityInstance = getEntityInstanceByBasePK(pk);
        
        return createChainInstanceEntityRole(chainInstance, chainEntityRoleType, entityInstance, createdBy);
    }
    
    public ChainInstanceEntityRole createChainInstanceEntityRole(ChainInstance chainInstance, ChainEntityRoleType chainEntityRoleType,
            EntityInstance entityInstance, BasePK createdBy) {
        var chainInstanceEntityRole = chainInstanceEntityRoleFactory.create( chainInstance, chainEntityRoleType,
                entityInstance, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(chainInstance.getPrimaryKey(), EventTypes.MODIFY, chainInstanceEntityRole.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return chainInstanceEntityRole;
    }
    
    public long countChainInstanceEntityRolesByChainEntityRoleType(final ChainEntityRoleType chainEntityRoleType) {
        return session.getDslContext()
                .selectCount()
                .from(ChainInstanceEntityRoles)
                .where(ChainInstanceEntityRoles.CHAIN_ENTITY_ROLE_TYPE.eq(chainEntityRoleType.getPrimaryKey()),
                        ChainInstanceEntityRoles.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countChainInstanceEntityRolesByEntityInstance(final EntityInstance entityInstance) {
        return session.getDslContext()
                .selectCount()
                .from(ChainInstanceEntityRoles)
                .where(ChainInstanceEntityRoles.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                        ChainInstanceEntityRoles.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countChainInstanceEntityRoles(final ChainEntityRoleType chainEntityRoleType, final EntityInstance entityInstance) {
        return session.getDslContext()
                .selectCount()
                .from(ChainInstanceEntityRoles)
                .where(ChainInstanceEntityRoles.CHAIN_ENTITY_ROLE_TYPE.eq(chainEntityRoleType.getPrimaryKey()),
                        ChainInstanceEntityRoles.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                        ChainInstanceEntityRoles.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }
    
    public ChainInstanceEntityRole getChainInstanceEntityRole(ChainInstance chainInstance,
            ChainEntityRoleType chainEntityRoleType) {
        var query = session.getDslContext()
                .select(ChainInstanceEntityRoles.fields())
                .from(ChainInstanceEntityRoles)
                .where(ChainInstanceEntityRoles.CHAIN_INSTANCE.eq(chainInstance.getPrimaryKey()),
                        ChainInstanceEntityRoles.CHAIN_ENTITY_ROLE_TYPE.eq(chainEntityRoleType.getPrimaryKey()),
                        ChainInstanceEntityRoles.THRU_TIME.eq(Session.MAX_TIME));

        return chainInstanceEntityRoleFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    private List<ChainInstanceEntityRole> getChainInstanceEntityRoles(final ChainEntityRoleType chainEntityRoleType, final EntityInstance entityInstance, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ChainInstanceEntityRoles.fields())
                .from(ChainInstanceEntityRoles)
                .where(ChainInstanceEntityRoles.CHAIN_ENTITY_ROLE_TYPE.eq(chainEntityRoleType.getPrimaryKey()),
                        ChainInstanceEntityRoles.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                        ChainInstanceEntityRoles.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ChainInstanceEntityRoles.CHAIN_INSTANCE),
                    ChainInstanceEntityRoleFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return chainInstanceEntityRoleFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ChainInstanceEntityRole> getChainInstanceEntityRoles(final ChainEntityRoleType chainEntityRoleType, final EntityInstance entityInstance) {
        return getChainInstanceEntityRoles(chainEntityRoleType, entityInstance, EntityPermission.READ_ONLY);
    }
    
    public List<ChainInstanceEntityRole> getChainInstanceEntityRolesForUpdate(final ChainEntityRoleType chainEntityRoleType, final EntityInstance entityInstance) {
        return getChainInstanceEntityRoles(chainEntityRoleType, entityInstance, EntityPermission.READ_WRITE);
    }
    
    private List<ChainInstanceEntityRole> getChainInstanceEntityRolesByChainInstance(ChainInstance chainInstance, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ChainInstanceEntityRoles.fields())
                    .from(ChainInstanceEntityRoles)
                    .join(ChainEntityRoleTypes).onKey(CHAIN_INSTANCE_ENTITY_ROLES_CHAIN_ENTITY_ROLE_TYPE_FK)
                    .join(ChainEntityRoleTypeDetails).onKey(CHAIN_ENTITY_ROLE_TYPES_LAST_DETAIL_FK)
                    .where(ChainInstanceEntityRoles.CHAIN_INSTANCE.eq(chainInstance.getPrimaryKey()),
                            ChainInstanceEntityRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ChainEntityRoleTypeDetails.SORT_ORDER, ChainEntityRoleTypeDetails.CHAIN_ENTITY_ROLE_TYPE_NAME),
                    ChainInstanceEntityRoleFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ChainInstanceEntityRoles.fields())
                    .from(ChainInstanceEntityRoles)
                    .where(ChainInstanceEntityRoles.CHAIN_INSTANCE.eq(chainInstance.getPrimaryKey()),
                            ChainInstanceEntityRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return chainInstanceEntityRoleFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ChainInstanceEntityRole> getChainInstanceEntityRolesByChainInstance(ChainInstance chainInstance) {
        return getChainInstanceEntityRolesByChainInstance(chainInstance, EntityPermission.READ_ONLY);
    }
    
    public List<ChainInstanceEntityRole> getChainInstanceEntityRolesByChainInstanceForUpdate(ChainInstance chainInstance) {
        return getChainInstanceEntityRolesByChainInstance(chainInstance, EntityPermission.READ_WRITE);
    }
    
    private List<ChainInstanceEntityRole> getChainInstanceEntityRolesByChainEntityRoleType(ChainEntityRoleType chainEntityRoleType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ChainInstanceEntityRoles.fields())
                .from(ChainInstanceEntityRoles)
                .where(ChainInstanceEntityRoles.CHAIN_ENTITY_ROLE_TYPE.eq(chainEntityRoleType.getPrimaryKey()),
                        ChainInstanceEntityRoles.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ChainInstanceEntityRoles.CHAIN_INSTANCE_ENTITY_ROLE),
                    ChainInstanceEntityRoleFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return chainInstanceEntityRoleFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ChainInstanceEntityRole> getChainInstanceEntityRolesByChainEntityRoleType(ChainEntityRoleType chainEntityRoleType) {
        return getChainInstanceEntityRolesByChainEntityRoleType(chainEntityRoleType, EntityPermission.READ_ONLY);
    }
    
    public List<ChainInstanceEntityRole> getChainInstanceEntityRolesByChainEntityRoleTypeForUpdate(ChainEntityRoleType chainEntityRoleType) {
        return getChainInstanceEntityRolesByChainEntityRoleType(chainEntityRoleType, EntityPermission.READ_WRITE);
    }
    
    private List<ChainInstanceEntityRole> getChainInstanceEntityRolesByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ChainInstanceEntityRoles.fields())
                    .from(ChainInstanceEntityRoles)
                    .where(ChainInstanceEntityRoles.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                            ChainInstanceEntityRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ChainInstanceEntityRoles.CHAIN_INSTANCE_ENTITY_ROLE),
                    ChainInstanceEntityRoleFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ChainInstanceEntityRoles.fields())
                    .from(ChainInstanceEntityRoles)
                    .where(ChainInstanceEntityRoles.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                            ChainInstanceEntityRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return chainInstanceEntityRoleFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ChainInstanceEntityRole> getChainInstanceEntityRolesByEntityInstance(EntityInstance entityInstance) {
        return getChainInstanceEntityRolesByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }
    
    public List<ChainInstanceEntityRole> getChainInstanceEntityRolesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getChainInstanceEntityRolesByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    public ChainInstanceEntityRoleTransfer getChainInstanceEntityRoleTransfer(UserVisit userVisit,
            ChainInstanceEntityRole chainInstanceEntityRole) {
        return chainInstanceEntityRoleTransferCache.getChainInstanceEntityRoleTransfer(userVisit, chainInstanceEntityRole);
    }
    
    public List<ChainInstanceEntityRoleTransfer> getChainInstanceEntityRoleTransfers(UserVisit userVisit, Collection<ChainInstanceEntityRole> chainInstanceEntityRoles) {
        List<ChainInstanceEntityRoleTransfer> chainInstanceEntityRoleTransfers = new ArrayList<>(chainInstanceEntityRoles.size());
        
        chainInstanceEntityRoles.forEach((chainInstanceEntityRole) ->
                chainInstanceEntityRoleTransfers.add(chainInstanceEntityRoleTransferCache.getChainInstanceEntityRoleTransfer(userVisit, chainInstanceEntityRole))
        );
        
        return chainInstanceEntityRoleTransfers;
    }
    
    public List<ChainInstanceEntityRoleTransfer> getChainInstanceEntityRoleTransfersByChainInstance(UserVisit userVisit, ChainInstance chainInstance) {
        return getChainInstanceEntityRoleTransfers(userVisit, getChainInstanceEntityRolesByChainInstance(chainInstance));
    }
    
    public void deleteChainInstanceEntityRole(ChainInstanceEntityRole chainInstanceEntityRole, BasePK deletedBy) {
        chainInstanceEntityRole.setThruTime(session.getStartTime());
        
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
