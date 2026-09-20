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

package com.echothree.model.control.selector.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.security.server.control.SecurityControl;
import com.echothree.model.control.selector.common.SelectorNodeTypes;
import com.echothree.model.control.selector.common.choice.SelectorBooleanTypeChoicesBean;
import com.echothree.model.control.selector.common.choice.SelectorChoicesBean;
import com.echothree.model.control.selector.common.choice.SelectorComparisonTypeChoicesBean;
import com.echothree.model.control.selector.common.choice.SelectorKindChoicesBean;
import com.echothree.model.control.selector.common.choice.SelectorNodeChoicesBean;
import com.echothree.model.control.selector.common.choice.SelectorTextSearchTypeChoicesBean;
import com.echothree.model.control.selector.common.choice.SelectorTypeChoicesBean;
import com.echothree.model.control.selector.common.transfer.SelectorDescriptionTransfer;
import com.echothree.model.control.selector.common.transfer.SelectorKindDescriptionTransfer;
import com.echothree.model.control.selector.common.transfer.SelectorKindTransfer;
import com.echothree.model.control.selector.common.transfer.SelectorNodeDescriptionTransfer;
import com.echothree.model.control.selector.common.transfer.SelectorNodeTransfer;
import com.echothree.model.control.selector.common.transfer.SelectorNodeTypeTransfer;
import com.echothree.model.control.selector.common.transfer.SelectorPartyTransfer;
import com.echothree.model.control.selector.common.transfer.SelectorTransfer;
import com.echothree.model.control.selector.common.transfer.SelectorTypeDescriptionTransfer;
import com.echothree.model.control.selector.common.transfer.SelectorTypeTransfer;
import com.echothree.model.control.selector.server.transfer.SelectorDescriptionTransferCache;
import com.echothree.model.control.selector.server.transfer.SelectorKindDescriptionTransferCache;
import com.echothree.model.control.selector.server.transfer.SelectorKindTransferCache;
import com.echothree.model.control.selector.server.transfer.SelectorNodeDescriptionTransferCache;
import com.echothree.model.control.selector.server.transfer.SelectorNodeTransferCache;
import com.echothree.model.control.selector.server.transfer.SelectorNodeTypeTransferCache;
import com.echothree.model.control.selector.server.transfer.SelectorPartyTransferCache;
import com.echothree.model.control.selector.server.transfer.SelectorTransferCache;
import com.echothree.model.control.selector.server.transfer.SelectorTypeDescriptionTransferCache;
import com.echothree.model.control.selector.server.transfer.SelectorTypeTransferCache;
import com.echothree.model.data.accounting.server.entity.ItemAccountingCategory;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityListItem;
import com.echothree.model.data.employee.server.entity.ResponsibilityType;
import com.echothree.model.data.employee.server.entity.SkillType;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.item.server.entity.ItemCategory;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.payment.server.entity.PaymentMethod;
import com.echothree.model.data.payment.server.entity.PaymentProcessor;
import com.echothree.model.data.selector.common.pk.SelectorBooleanTypePK;
import com.echothree.model.data.selector.common.pk.SelectorComparisonTypePK;
import com.echothree.model.data.selector.common.pk.SelectorKindPK;
import com.echothree.model.data.selector.common.pk.SelectorNodePK;
import com.echothree.model.data.selector.common.pk.SelectorNodeTypePK;
import com.echothree.model.data.selector.common.pk.SelectorPK;
import com.echothree.model.data.selector.common.pk.SelectorTextSearchTypePK;
import com.echothree.model.data.selector.common.pk.SelectorTypePK;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.selector.server.entity.SelectorBooleanType;
import com.echothree.model.data.selector.server.entity.SelectorBooleanTypeDescription;
import com.echothree.model.data.selector.server.entity.SelectorComparisonType;
import com.echothree.model.data.selector.server.entity.SelectorComparisonTypeDescription;
import com.echothree.model.data.selector.server.entity.SelectorDescription;
import com.echothree.model.data.selector.server.entity.SelectorKind;
import com.echothree.model.data.selector.server.entity.SelectorKindDescription;
import com.echothree.model.data.selector.server.entity.SelectorNode;
import com.echothree.model.data.selector.server.entity.SelectorNodeBoolean;
import com.echothree.model.data.selector.server.entity.SelectorNodeDescription;
import com.echothree.model.data.selector.server.entity.SelectorNodeEntityListItem;
import com.echothree.model.data.selector.server.entity.SelectorNodeGeoCode;
import com.echothree.model.data.selector.server.entity.SelectorNodeItemAccountingCategory;
import com.echothree.model.data.selector.server.entity.SelectorNodeItemCategory;
import com.echothree.model.data.selector.server.entity.SelectorNodeItemPurchasingCategory;
import com.echothree.model.data.selector.server.entity.SelectorNodePaymentMethod;
import com.echothree.model.data.selector.server.entity.SelectorNodePaymentProcessor;
import com.echothree.model.data.selector.server.entity.SelectorNodeResponsibilityType;
import com.echothree.model.data.selector.server.entity.SelectorNodeSkillType;
import com.echothree.model.data.selector.server.entity.SelectorNodeTrainingClass;
import com.echothree.model.data.selector.server.entity.SelectorNodeType;
import com.echothree.model.data.selector.server.entity.SelectorNodeTypeDescription;
import com.echothree.model.data.selector.server.entity.SelectorNodeTypeUse;
import com.echothree.model.data.selector.server.entity.SelectorNodeWorkflowStep;
import com.echothree.model.data.selector.server.entity.SelectorParty;
import com.echothree.model.data.selector.server.entity.SelectorTextSearchType;
import com.echothree.model.data.selector.server.entity.SelectorTextSearchTypeDescription;
import com.echothree.model.data.selector.server.entity.SelectorTime;
import com.echothree.model.data.selector.server.entity.SelectorType;
import com.echothree.model.data.selector.server.entity.SelectorTypeDescription;
import com.echothree.model.data.selector.server.factory.SelectorBooleanTypeDescriptionFactory;
import com.echothree.model.data.selector.server.factory.SelectorBooleanTypeFactory;
import com.echothree.model.data.selector.server.factory.SelectorComparisonTypeDescriptionFactory;
import com.echothree.model.data.selector.server.factory.SelectorComparisonTypeFactory;
import com.echothree.model.data.selector.server.factory.SelectorDescriptionFactory;
import com.echothree.model.data.selector.server.factory.SelectorDetailFactory;
import com.echothree.model.data.selector.server.factory.SelectorFactory;
import com.echothree.model.data.selector.server.factory.SelectorKindDescriptionFactory;
import com.echothree.model.data.selector.server.factory.SelectorKindDetailFactory;
import com.echothree.model.data.selector.server.factory.SelectorKindFactory;
import com.echothree.model.data.selector.server.factory.SelectorNodeBooleanFactory;
import com.echothree.model.data.selector.server.factory.SelectorNodeDescriptionFactory;
import com.echothree.model.data.selector.server.factory.SelectorNodeDetailFactory;
import com.echothree.model.data.selector.server.factory.SelectorNodeEntityListItemFactory;
import com.echothree.model.data.selector.server.factory.SelectorNodeFactory;
import com.echothree.model.data.selector.server.factory.SelectorNodeGeoCodeFactory;
import com.echothree.model.data.selector.server.factory.SelectorNodeItemAccountingCategoryFactory;
import com.echothree.model.data.selector.server.factory.SelectorNodeItemCategoryFactory;
import com.echothree.model.data.selector.server.factory.SelectorNodeItemPurchasingCategoryFactory;
import com.echothree.model.data.selector.server.factory.SelectorNodePaymentMethodFactory;
import com.echothree.model.data.selector.server.factory.SelectorNodePaymentProcessorFactory;
import com.echothree.model.data.selector.server.factory.SelectorNodeResponsibilityTypeFactory;
import com.echothree.model.data.selector.server.factory.SelectorNodeSkillTypeFactory;
import com.echothree.model.data.selector.server.factory.SelectorNodeTrainingClassFactory;
import com.echothree.model.data.selector.server.factory.SelectorNodeTypeDescriptionFactory;
import com.echothree.model.data.selector.server.factory.SelectorNodeTypeFactory;
import com.echothree.model.data.selector.server.factory.SelectorNodeTypeUseFactory;
import com.echothree.model.data.selector.server.factory.SelectorNodeWorkflowStepFactory;
import com.echothree.model.data.selector.server.factory.SelectorPartyFactory;
import com.echothree.model.data.selector.server.factory.SelectorTextSearchTypeDescriptionFactory;
import com.echothree.model.data.selector.server.factory.SelectorTextSearchTypeFactory;
import com.echothree.model.data.selector.server.factory.SelectorTimeFactory;
import com.echothree.model.data.selector.server.factory.SelectorTypeDescriptionFactory;
import com.echothree.model.data.selector.server.factory.SelectorTypeDetailFactory;
import com.echothree.model.data.selector.server.factory.SelectorTypeFactory;
import com.echothree.model.data.selector.server.value.SelectorDescriptionValue;
import com.echothree.model.data.selector.server.value.SelectorDetailValue;
import com.echothree.model.data.selector.server.value.SelectorKindDescriptionValue;
import com.echothree.model.data.selector.server.value.SelectorKindDetailValue;
import com.echothree.model.data.selector.server.value.SelectorNodeBooleanValue;
import com.echothree.model.data.selector.server.value.SelectorNodeDescriptionValue;
import com.echothree.model.data.selector.server.value.SelectorNodeDetailValue;
import com.echothree.model.data.selector.server.value.SelectorNodeEntityListItemValue;
import com.echothree.model.data.selector.server.value.SelectorNodeGeoCodeValue;
import com.echothree.model.data.selector.server.value.SelectorNodeItemAccountingCategoryValue;
import com.echothree.model.data.selector.server.value.SelectorNodeItemCategoryValue;
import com.echothree.model.data.selector.server.value.SelectorNodeItemPurchasingCategoryValue;
import com.echothree.model.data.selector.server.value.SelectorNodePaymentMethodValue;
import com.echothree.model.data.selector.server.value.SelectorNodePaymentProcessorValue;
import com.echothree.model.data.selector.server.value.SelectorNodeResponsibilityTypeValue;
import com.echothree.model.data.selector.server.value.SelectorNodeSkillTypeValue;
import com.echothree.model.data.selector.server.value.SelectorNodeTrainingClassValue;
import com.echothree.model.data.selector.server.value.SelectorNodeWorkflowStepValue;
import com.echothree.model.data.selector.server.value.SelectorPartyValue;
import com.echothree.model.data.selector.server.value.SelectorTypeDescriptionValue;
import com.echothree.model.data.selector.server.value.SelectorTypeDetailValue;
import com.echothree.model.data.training.server.entity.TrainingClass;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.vendor.server.entity.ItemPurchasingCategory;
import com.echothree.model.data.workflow.server.entity.WorkflowStep;
import static com.echothree.model.jooq.server.keys.party.PartyForeignKeys.PARTIES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.selector.SelectorForeignKeys.SELECTORS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.selector.SelectorForeignKeys.SELECTOR_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.selector.SelectorForeignKeys.SELECTOR_DETAILS_SELECTOR_TYPE_FK;
import static com.echothree.model.jooq.server.keys.selector.SelectorForeignKeys.SELECTOR_KINDS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.selector.SelectorForeignKeys.SELECTOR_KIND_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.selector.SelectorForeignKeys.SELECTOR_NODES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.selector.SelectorForeignKeys.SELECTOR_NODE_BOOLEANS_SELECTOR_NODE_FK;
import static com.echothree.model.jooq.server.keys.selector.SelectorForeignKeys.SELECTOR_NODE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.selector.SelectorForeignKeys.SELECTOR_NODE_ENTITY_LIST_ITEMS_SELECTOR_NODE_FK;
import static com.echothree.model.jooq.server.keys.selector.SelectorForeignKeys.SELECTOR_NODE_GEO_CODES_SELECTOR_NODE_FK;
import static com.echothree.model.jooq.server.keys.selector.SelectorForeignKeys.SELECTOR_NODE_ITEM_ACCOUNTING_CATEGORIES_SELECTOR_NODE_FK;
import static com.echothree.model.jooq.server.keys.selector.SelectorForeignKeys.SELECTOR_NODE_ITEM_CATEGORIES_SELECTOR_NODE_FK;
import static com.echothree.model.jooq.server.keys.selector.SelectorForeignKeys.SELECTOR_NODE_ITEM_PURCHASING_CATEGORIES_SELECTOR_NODE_FK;
import static com.echothree.model.jooq.server.keys.selector.SelectorForeignKeys.SELECTOR_NODE_PAYMENT_METHODS_SELECTOR_NODE_FK;
import static com.echothree.model.jooq.server.keys.selector.SelectorForeignKeys.SELECTOR_NODE_PAYMENT_PROCESSORS_SELECTOR_NODE_FK;
import static com.echothree.model.jooq.server.keys.selector.SelectorForeignKeys.SELECTOR_NODE_RESPONSIBILITY_TYPES_SELECTOR_NODE_FK;
import static com.echothree.model.jooq.server.keys.selector.SelectorForeignKeys.SELECTOR_NODE_SKILL_TYPES_SELECTOR_NODE_FK;
import static com.echothree.model.jooq.server.keys.selector.SelectorForeignKeys.SELECTOR_NODE_TRAINING_CLASSES_SELECTOR_NODE_FK;
import static com.echothree.model.jooq.server.keys.selector.SelectorForeignKeys.SELECTOR_NODE_TYPE_USES_SELECTOR_NODE_TYPE_FK;
import static com.echothree.model.jooq.server.keys.selector.SelectorForeignKeys.SELECTOR_NODE_WORKFLOW_STEPS_SELECTOR_NODE_FK;
import static com.echothree.model.jooq.server.keys.selector.SelectorForeignKeys.SELECTOR_PARTIES_PARTY_FK;
import static com.echothree.model.jooq.server.keys.selector.SelectorForeignKeys.SELECTOR_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.selector.SelectorForeignKeys.SELECTOR_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.selector.SelectorForeignKeys.SELECTOR_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import static com.echothree.model.jooq.server.tables.party.Parties.Parties;
import static com.echothree.model.jooq.server.tables.party.PartyDetails.PartyDetails;
import static com.echothree.model.jooq.server.tables.selector.SelectorBooleanTypeDescriptions.SelectorBooleanTypeDescriptions;
import static com.echothree.model.jooq.server.tables.selector.SelectorBooleanTypes.SelectorBooleanTypes;
import static com.echothree.model.jooq.server.tables.selector.SelectorComparisonTypeDescriptions.SelectorComparisonTypeDescriptions;
import static com.echothree.model.jooq.server.tables.selector.SelectorComparisonTypes.SelectorComparisonTypes;
import static com.echothree.model.jooq.server.tables.selector.SelectorDescriptions.SelectorDescriptions;
import static com.echothree.model.jooq.server.tables.selector.SelectorDetails.SelectorDetails;
import static com.echothree.model.jooq.server.tables.selector.SelectorKindDescriptions.SelectorKindDescriptions;
import static com.echothree.model.jooq.server.tables.selector.SelectorKindDetails.SelectorKindDetails;
import static com.echothree.model.jooq.server.tables.selector.SelectorKinds.SelectorKinds;
import static com.echothree.model.jooq.server.tables.selector.SelectorNodeBooleans.SelectorNodeBooleans;
import static com.echothree.model.jooq.server.tables.selector.SelectorNodeDescriptions.SelectorNodeDescriptions;
import static com.echothree.model.jooq.server.tables.selector.SelectorNodeDetails.SelectorNodeDetails;
import static com.echothree.model.jooq.server.tables.selector.SelectorNodeEntityListItems.SelectorNodeEntityListItems;
import static com.echothree.model.jooq.server.tables.selector.SelectorNodeGeoCodes.SelectorNodeGeoCodes;
import static com.echothree.model.jooq.server.tables.selector.SelectorNodeItemAccountingCategories.SelectorNodeItemAccountingCategories;
import static com.echothree.model.jooq.server.tables.selector.SelectorNodeItemCategories.SelectorNodeItemCategories;
import static com.echothree.model.jooq.server.tables.selector.SelectorNodeItemPurchasingCategories.SelectorNodeItemPurchasingCategories;
import static com.echothree.model.jooq.server.tables.selector.SelectorNodePaymentMethods.SelectorNodePaymentMethods;
import static com.echothree.model.jooq.server.tables.selector.SelectorNodePaymentProcessors.SelectorNodePaymentProcessors;
import static com.echothree.model.jooq.server.tables.selector.SelectorNodeResponsibilityTypes.SelectorNodeResponsibilityTypes;
import static com.echothree.model.jooq.server.tables.selector.SelectorNodeSkillTypes.SelectorNodeSkillTypes;
import static com.echothree.model.jooq.server.tables.selector.SelectorNodeTrainingClasses.SelectorNodeTrainingClasses;
import static com.echothree.model.jooq.server.tables.selector.SelectorNodeTypeDescriptions.SelectorNodeTypeDescriptions;
import static com.echothree.model.jooq.server.tables.selector.SelectorNodeTypeUses.SelectorNodeTypeUses;
import static com.echothree.model.jooq.server.tables.selector.SelectorNodeTypes.SelectorNodeTypes;
import static com.echothree.model.jooq.server.tables.selector.SelectorNodeWorkflowSteps.SelectorNodeWorkflowSteps;
import static com.echothree.model.jooq.server.tables.selector.SelectorNodes.SelectorNodes;
import static com.echothree.model.jooq.server.tables.selector.SelectorParties.SelectorParties;
import static com.echothree.model.jooq.server.tables.selector.SelectorTextSearchTypeDescriptions.SelectorTextSearchTypeDescriptions;
import static com.echothree.model.jooq.server.tables.selector.SelectorTextSearchTypes.SelectorTextSearchTypes;
import static com.echothree.model.jooq.server.tables.selector.SelectorTimes.SelectorTimes;
import static com.echothree.model.jooq.server.tables.selector.SelectorTypeDescriptions.SelectorTypeDescriptions;
import static com.echothree.model.jooq.server.tables.selector.SelectorTypeDetails.SelectorTypeDetails;
import static com.echothree.model.jooq.server.tables.selector.SelectorTypes.SelectorTypes;
import static com.echothree.model.jooq.server.tables.selector.Selectors.Selectors;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;

@CommandScope
public class SelectorControl
        extends BaseModelControl {
    
    @Inject
    protected SecurityControl securityControl;

    /** Creates a new instance of SelectorControl */
    protected SelectorControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Kinds
    // --------------------------------------------------------------------------------

    @Inject
    protected SelectorKindDetailFactory selectorKindDetailFactory;

    @Inject
    protected SelectorKindFactory selectorKindFactory;

    @Inject
    private SelectorKindTransferCache selectorKindTransferCache;

    public SelectorKind createSelectorKind(String selectorKindName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultSelectorKind = getDefaultSelectorKind();
        var defaultFound = defaultSelectorKind != null;

        if(defaultFound && isDefault) {
            var defaultSelectorKindDetailValue = getDefaultSelectorKindDetailValueForUpdate();

            defaultSelectorKindDetailValue.setIsDefault(false);
            updateSelectorKindFromValue(defaultSelectorKindDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var selectorKind = selectorKindFactory.create();
        var selectorKindDetail = selectorKindDetailFactory.create(selectorKind, selectorKindName, isDefault, sortOrder,
                session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        selectorKind = selectorKindFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                selectorKind.getPrimaryKey());
        selectorKind.setActiveDetail(selectorKindDetail);
        selectorKind.setLastDetail(selectorKindDetail);
        selectorKind.store();

        sendEvent(selectorKind.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return selectorKind;
    }

    public long countSelectorKinds() {
        return session.getDslContext()
                .selectCount()
                .from(SelectorKinds)
                .join(SelectorKindDetails).onKey(SELECTOR_KINDS_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.SelectorKind */
    public SelectorKind getSelectorKindByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new SelectorKindPK(entityInstance.getEntityUniqueId());

        return selectorKindFactory.getEntityFromPK(entityPermission, pk);
    }

    public SelectorKind getSelectorKindByEntityInstance(EntityInstance entityInstance) {
        return getSelectorKindByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public SelectorKind getSelectorKindByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getSelectorKindByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public SelectorKind getSelectorKindByName(String selectorKindName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SelectorKinds.fields())
                .from(SelectorKinds)
                .join(SelectorKindDetails).onKey(SELECTOR_KINDS_ACTIVE_DETAIL_FK)
                .where(SelectorKindDetails.SELECTOR_KIND_NAME.eq(selectorKindName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return selectorKindFactory.getEntityFromQuery(entityPermission, query);
    }

    public SelectorKind getSelectorKindByName(String selectorKindName) {
        return getSelectorKindByName(selectorKindName, EntityPermission.READ_ONLY);
    }

    public SelectorKind getSelectorKindByNameForUpdate(String selectorKindName) {
        return getSelectorKindByName(selectorKindName, EntityPermission.READ_WRITE);
    }

    public SelectorKindDetailValue getSelectorKindDetailValueForUpdate(SelectorKind selectorKind) {
        return selectorKind == null? null: selectorKind.getLastDetailForUpdate().getSelectorKindDetailValue().clone();
    }

    public SelectorKindDetailValue getSelectorKindDetailValueByNameForUpdate(String selectorKindName) {
        return getSelectorKindDetailValueForUpdate(getSelectorKindByNameForUpdate(selectorKindName));
    }

    public SelectorKind getDefaultSelectorKind(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SelectorKinds.fields())
                .from(SelectorKinds)
                .join(SelectorKindDetails).onKey(SELECTOR_KINDS_ACTIVE_DETAIL_FK)
                .where(SelectorKindDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return selectorKindFactory.getEntityFromQuery(entityPermission, query);
    }

    public SelectorKind getDefaultSelectorKind() {
        return getDefaultSelectorKind(EntityPermission.READ_ONLY);
    }

    public SelectorKind getDefaultSelectorKindForUpdate() {
        return getDefaultSelectorKind(EntityPermission.READ_WRITE);
    }

    public SelectorKindDetailValue getDefaultSelectorKindDetailValueForUpdate() {
        return getDefaultSelectorKind(EntityPermission.READ_WRITE).getLastDetailForUpdate().getSelectorKindDetailValue();
    }

    private List<SelectorKind> getSelectorKinds(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SelectorKinds.fields())
                .from(SelectorKinds)
                .join(SelectorKindDetails).onKey(SELECTOR_KINDS_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(SelectorKindDetails.SORT_ORDER, SelectorKindDetails.SELECTOR_KIND_NAME),
                    SelectorKindFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return selectorKindFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<SelectorKind> getSelectorKinds() {
        return getSelectorKinds(EntityPermission.READ_ONLY);
    }

    public List<SelectorKind> getSelectorKindsForUpdate() {
        return getSelectorKinds(EntityPermission.READ_WRITE);
    }

    public SelectorKindChoicesBean getSelectorKindChoices(String defaultSelectorKindChoice, Language language, boolean allowNullChoice) {
        var selectorKinds = getSelectorKinds();
        var size = selectorKinds.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultSelectorKindChoice == null) {
                defaultValue = "";
            }
        }

        for(var selectorKind : selectorKinds) {
            var selectorKindDetail = selectorKind.getLastDetail();

            var label = getBestSelectorKindDescription(selectorKind, language);
            var value = selectorKindDetail.getSelectorKindName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultSelectorKindChoice != null && defaultSelectorKindChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && selectorKindDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new SelectorKindChoicesBean(labels, values, defaultValue);
    }

    public SelectorKindTransfer getSelectorKindTransfer(UserVisit userVisit, SelectorKind selectorKind) {
        return selectorKindTransferCache.getSelectorKindTransfer(userVisit, selectorKind);
    }

    public List<SelectorKindTransfer> getSelectorKindTransfers(UserVisit userVisit, Collection<SelectorKind> selectorKinds) {
        List<SelectorKindTransfer> selectorKindTransfers = new ArrayList<>(selectorKinds.size());

        selectorKinds.forEach((selectorKind) ->
                selectorKindTransfers.add(selectorKindTransferCache.getSelectorKindTransfer(userVisit, selectorKind))
        );

        return selectorKindTransfers;
    }

    public List<SelectorKindTransfer> getSelectorKindTransfers(UserVisit userVisit) {
        return getSelectorKindTransfers(userVisit, getSelectorKinds());
    }

    private void updateSelectorKindFromValue(SelectorKindDetailValue selectorKindDetailValue, boolean checkDefault, BasePK updatedBy) {
        var selectorKind = selectorKindFactory.getEntityFromPK(
                EntityPermission.READ_WRITE, selectorKindDetailValue.getSelectorKindPK());
        var selectorKindDetail = selectorKind.getActiveDetailForUpdate();

        selectorKindDetail.setThruTime(session.getStartTime());
        selectorKindDetail.store();

        var selectorKindPK = selectorKindDetail.getSelectorKindPK();
        var selectorKindName = selectorKindDetailValue.getSelectorKindName();
        var isDefault = selectorKindDetailValue.getIsDefault();
        var sortOrder = selectorKindDetailValue.getSortOrder();

        if(checkDefault) {
            var defaultSelectorKind = getDefaultSelectorKind();
            var defaultFound = defaultSelectorKind != null && !defaultSelectorKind.equals(selectorKind);

            if(isDefault && defaultFound) {
                // If I'm the default, and a default already existed...
                var defaultSelectorKindDetailValue = getDefaultSelectorKindDetailValueForUpdate();

                defaultSelectorKindDetailValue.setIsDefault(false);
                updateSelectorKindFromValue(defaultSelectorKindDetailValue, false, updatedBy);
            } else if(!isDefault && !defaultFound) {
                // If I'm not the default, and no other default exists...
                isDefault = true;
            }
        }

        selectorKindDetail = selectorKindDetailFactory.create(selectorKindPK, selectorKindName, isDefault, sortOrder, session.getStartTime(),
                Session.MAX_TIME);

        selectorKind.setActiveDetail(selectorKindDetail);
        selectorKind.setLastDetail(selectorKindDetail);
        selectorKind.store();

        sendEvent(selectorKindPK, EventTypes.MODIFY, null, null, updatedBy);
    }

    public void updateSelectorKindFromValue(SelectorKindDetailValue selectorKindDetailValue, BasePK updatedBy) {
        updateSelectorKindFromValue(selectorKindDetailValue, true, updatedBy);
    }

    public void deleteSelectorKind(SelectorKind selectorKind, BasePK deletedBy) {
        deleteSelectorKindDescriptionsBySelectorKind(selectorKind, deletedBy);

        var selectorKindDetail = selectorKind.getLastDetailForUpdate();
        selectorKindDetail.setThruTime(session.getStartTime());
        selectorKind.setActiveDetail(null);
        selectorKind.store();

        // Check for default, and pick one if necessary
        var defaultSelectorKind = getDefaultSelectorKind();
        if(defaultSelectorKind == null) {
            var selectorKinds = getSelectorKindsForUpdate();

            if(!selectorKinds.isEmpty()) {
                var iter = selectorKinds.iterator();
                if(iter.hasNext()) {
                    defaultSelectorKind = iter.next();
                }
                var selectorKindDetailValue = Objects.requireNonNull(defaultSelectorKind).getLastDetailForUpdate().getSelectorKindDetailValue().clone();

                selectorKindDetailValue.setIsDefault(true);
                updateSelectorKindFromValue(selectorKindDetailValue, false, deletedBy);
            }
        }

        sendEvent(selectorKind.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Selector Kind Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected SelectorKindDescriptionFactory selectorKindDescriptionFactory;

    @Inject
    private SelectorKindDescriptionTransferCache selectorKindDescriptionTransferCache;

    public SelectorKindDescription createSelectorKindDescription(SelectorKind selectorKind, Language language, String description,
            BasePK createdBy) {
        var selectorKindDescription = selectorKindDescriptionFactory.create(selectorKind,
                language, description, session.getStartTime(), Session.MAX_TIME);

        sendEvent(selectorKind.getPrimaryKey(), EventTypes.MODIFY, selectorKindDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return selectorKindDescription;
    }

    private SelectorKindDescription getSelectorKindDescription(SelectorKind selectorKind, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SelectorKindDescriptions.fields())
                .from(SelectorKindDescriptions)
                .where(SelectorKindDescriptions.SELECTOR_KIND.eq(selectorKind.getPrimaryKey()),
                        SelectorKindDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        SelectorKindDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return selectorKindDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }

    public SelectorKindDescription getSelectorKindDescription(SelectorKind selectorKind, Language language) {
        return getSelectorKindDescription(selectorKind, language, EntityPermission.READ_ONLY);
    }

    public SelectorKindDescription getSelectorKindDescriptionForUpdate(SelectorKind selectorKind, Language language) {
        return getSelectorKindDescription(selectorKind, language, EntityPermission.READ_WRITE);
    }

    public SelectorKindDescriptionValue getSelectorKindDescriptionValue(SelectorKindDescription selectorKindDescription) {
        return selectorKindDescription == null? null: selectorKindDescription.getSelectorKindDescriptionValue().clone();
    }

    public SelectorKindDescriptionValue getSelectorKindDescriptionValueForUpdate(SelectorKind selectorKind, Language language) {
        return getSelectorKindDescriptionValue(getSelectorKindDescriptionForUpdate(selectorKind, language));
    }

    private List<SelectorKindDescription> getSelectorKindDescriptionsBySelectorKind(SelectorKind selectorKind, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(SelectorKindDescriptions.fields())
                    .from(SelectorKindDescriptions)
                    .join(Languages).onKey(SELECTOR_KIND_DESCRIPTIONS_LANGUAGE_FK)
                    .where(SelectorKindDescriptions.SELECTOR_KIND.eq(selectorKind.getPrimaryKey()),
                            SelectorKindDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    SelectorKindDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(SelectorKindDescriptions.fields())
                    .from(SelectorKindDescriptions)
                    .where(SelectorKindDescriptions.SELECTOR_KIND.eq(selectorKind.getPrimaryKey()),
                            SelectorKindDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return selectorKindDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<SelectorKindDescription> getSelectorKindDescriptionsBySelectorKind(SelectorKind selectorKind) {
        return getSelectorKindDescriptionsBySelectorKind(selectorKind, EntityPermission.READ_ONLY);
    }

    public List<SelectorKindDescription> getSelectorKindDescriptionsBySelectorKindForUpdate(SelectorKind selectorKind) {
        return getSelectorKindDescriptionsBySelectorKind(selectorKind, EntityPermission.READ_WRITE);
    }

    public String getBestSelectorKindDescription(SelectorKind selectorKind, Language language) {
        String description;
        var selectorKindDescription = getSelectorKindDescription(selectorKind, language);

        if(selectorKindDescription == null && !language.getIsDefault()) {
            selectorKindDescription = getSelectorKindDescription(selectorKind, partyControl.getDefaultLanguage());
        }

        if(selectorKindDescription == null) {
            description = selectorKind.getLastDetail().getSelectorKindName();
        } else {
            description = selectorKindDescription.getDescription();
        }

        return description;
    }

    public SelectorKindDescriptionTransfer getSelectorKindDescriptionTransfer(UserVisit userVisit, SelectorKindDescription selectorKindDescription) {
        return selectorKindDescriptionTransferCache.getSelectorKindDescriptionTransfer(userVisit, selectorKindDescription);
    }

    public List<SelectorKindDescriptionTransfer> getSelectorKindDescriptionTransfersBySelectorKind(UserVisit userVisit, SelectorKind selectorKind) {
        var selectorKindDescriptions = getSelectorKindDescriptionsBySelectorKind(selectorKind);
        List<SelectorKindDescriptionTransfer> selectorKindDescriptionTransfers = new ArrayList<>(selectorKindDescriptions.size());

        selectorKindDescriptions.forEach((selectorKindDescription) -> {
            selectorKindDescriptionTransfers.add(selectorKindDescriptionTransferCache.getSelectorKindDescriptionTransfer(userVisit, selectorKindDescription));
        });

        return selectorKindDescriptionTransfers;
    }

    public void updateSelectorKindDescriptionFromValue(SelectorKindDescriptionValue selectorKindDescriptionValue, BasePK updatedBy) {
        if(selectorKindDescriptionValue.hasBeenModified()) {
            var selectorKindDescription = selectorKindDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     selectorKindDescriptionValue.getPrimaryKey());

            selectorKindDescription.setThruTime(session.getStartTime());
            selectorKindDescription.store();

            var selectorKind = selectorKindDescription.getSelectorKind();
            var language = selectorKindDescription.getLanguage();
            var description = selectorKindDescriptionValue.getDescription();

            selectorKindDescription = selectorKindDescriptionFactory.create(selectorKind, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(selectorKind.getPrimaryKey(), EventTypes.MODIFY, selectorKindDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteSelectorKindDescription(SelectorKindDescription selectorKindDescription, BasePK deletedBy) {
        selectorKindDescription.setThruTime(session.getStartTime());

        sendEvent(selectorKindDescription.getSelectorKindPK(), EventTypes.MODIFY, selectorKindDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteSelectorKindDescriptionsBySelectorKind(SelectorKind selectorKind, BasePK deletedBy) {
        var selectorKindDescriptions = getSelectorKindDescriptionsBySelectorKindForUpdate(selectorKind);

        selectorKindDescriptions.forEach((selectorKindDescription) -> 
                deleteSelectorKindDescription(selectorKindDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Selector Types
    // --------------------------------------------------------------------------------

    @Inject
    protected SelectorTypeDetailFactory selectorTypeDetailFactory;

    @Inject
    protected SelectorTypeFactory selectorTypeFactory;

    @Inject
    private SelectorTypeTransferCache selectorTypeTransferCache;

    public SelectorType createSelectorType(SelectorKind selectorKind, String selectorTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultSelectorType = getDefaultSelectorType(selectorKind);
        var defaultFound = defaultSelectorType != null;

        if(defaultFound && isDefault) {
            var defaultSelectorTypeDetailValue = getDefaultSelectorTypeDetailValueForUpdate(selectorKind);

            defaultSelectorTypeDetailValue.setIsDefault(false);
            updateSelectorTypeFromValue(defaultSelectorTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var selectorType = selectorTypeFactory.create();
        var selectorTypeDetail = selectorTypeDetailFactory.create( selectorType, selectorKind, selectorTypeName, isDefault, sortOrder,
                session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        selectorType = selectorTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                selectorType.getPrimaryKey());
        selectorType.setActiveDetail(selectorTypeDetail);
        selectorType.setLastDetail(selectorTypeDetail);
        selectorType.store();

        sendEvent(selectorType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return selectorType;
    }

    public long countSelectorTypesBySelectorKind(SelectorKind selectorKind) {
        return session.getDslContext()
                .selectCount()
                .from(SelectorTypes)
                .join(SelectorTypeDetails).onKey(SELECTOR_TYPES_ACTIVE_DETAIL_FK)
                .where(SelectorTypeDetails.SELECTOR_KIND.eq(selectorKind.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.SelectorType */
    public SelectorType getSelectorTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new SelectorTypePK(entityInstance.getEntityUniqueId());

        return selectorTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public SelectorType getSelectorTypeByEntityInstance(EntityInstance entityInstance) {
        return getSelectorTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public SelectorType getSelectorTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getSelectorTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    private List<SelectorType> getSelectorTypes(SelectorKind selectorKind, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SelectorTypes.fields())
                .from(SelectorTypes)
                .join(SelectorTypeDetails).onKey(SELECTOR_TYPES_ACTIVE_DETAIL_FK)
                .where(SelectorTypeDetails.SELECTOR_KIND.eq(selectorKind.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(SelectorTypeDetails.SORT_ORDER, SelectorTypeDetails.SELECTOR_TYPE_NAME),
                    SelectorTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return selectorTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<SelectorType> getSelectorTypes(SelectorKind selectorKind) {
        return getSelectorTypes(selectorKind, EntityPermission.READ_ONLY);
    }

    public List<SelectorType> getSelectorTypesForUpdate(SelectorKind selectorKind) {
        return getSelectorTypes(selectorKind, EntityPermission.READ_WRITE);
    }

    public SelectorType getDefaultSelectorType(SelectorKind selectorKind, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SelectorTypes.fields())
                .from(SelectorTypes)
                .join(SelectorTypeDetails).onKey(SELECTOR_TYPES_ACTIVE_DETAIL_FK)
                .where(SelectorTypeDetails.SELECTOR_KIND.eq(selectorKind.getPrimaryKey()),
                        SelectorTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return selectorTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public SelectorType getDefaultSelectorType(SelectorKind selectorKind) {
        return getDefaultSelectorType(selectorKind, EntityPermission.READ_ONLY);
    }

    public SelectorType getDefaultSelectorTypeForUpdate(SelectorKind selectorKind) {
        return getDefaultSelectorType(selectorKind, EntityPermission.READ_WRITE);
    }

    public SelectorTypeDetailValue getDefaultSelectorTypeDetailValueForUpdate(SelectorKind selectorKind) {
        return getDefaultSelectorTypeForUpdate(selectorKind).getLastDetailForUpdate().getSelectorTypeDetailValue().clone();
    }

    public SelectorType getSelectorTypeByName(SelectorKind selectorKind, String selectorTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SelectorTypes.fields())
                .from(SelectorTypes)
                .join(SelectorTypeDetails).onKey(SELECTOR_TYPES_ACTIVE_DETAIL_FK)
                .where(SelectorTypeDetails.SELECTOR_KIND.eq(selectorKind.getPrimaryKey()),
                        SelectorTypeDetails.SELECTOR_TYPE_NAME.eq(selectorTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return selectorTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public SelectorType getSelectorTypeByName(SelectorKind selectorKind, String selectorTypeName) {
        return getSelectorTypeByName(selectorKind, selectorTypeName, EntityPermission.READ_ONLY);
    }

    public SelectorType getSelectorTypeByNameForUpdate(SelectorKind selectorKind, String selectorTypeName) {
        return getSelectorTypeByName(selectorKind, selectorTypeName, EntityPermission.READ_WRITE);
    }

    public SelectorTypeDetailValue getSelectorTypeDetailValueForUpdate(SelectorType selectorType) {
        return selectorType == null? null: selectorType.getLastDetailForUpdate().getSelectorTypeDetailValue().clone();
    }

    public SelectorTypeDetailValue getSelectorTypeDetailValueByNameForUpdate(SelectorKind selectorKind, String selectorTypeName) {
        return getSelectorTypeDetailValueForUpdate(getSelectorTypeByNameForUpdate(selectorKind, selectorTypeName));
    }

    public SelectorTypeChoicesBean getSelectorTypeChoices(String defaultSelectorTypeChoice, Language language,
            boolean allowNullChoice, SelectorKind selectorKind) {
        var selectorTypes = getSelectorTypes(selectorKind);
        var size = selectorTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultSelectorTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var selectorType : selectorTypes) {
            var selectorTypeDetail = selectorType.getLastDetail();
            var label = getBestSelectorTypeDescription(selectorType, language);
            var value = selectorTypeDetail.getSelectorTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultSelectorTypeChoice != null && defaultSelectorTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && selectorTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new SelectorTypeChoicesBean(labels, values, defaultValue);
    }

    public SelectorTypeTransfer getSelectorTypeTransfer(UserVisit userVisit, SelectorType selectorType) {
        return selectorTypeTransferCache.getSelectorTypeTransfer(userVisit, selectorType);
    }

    public List<SelectorTypeTransfer> getSelectorTypeTransfers(UserVisit userVisit, Collection<SelectorType> selectorTypes) {
        List<SelectorTypeTransfer> selectorTypeTransfers = new ArrayList<>(selectorTypes.size());

        selectorTypes.forEach((selectorType) ->
                selectorTypeTransfers.add(selectorTypeTransferCache.getSelectorTypeTransfer(userVisit, selectorType))
        );

        return selectorTypeTransfers;
    }

    public List<SelectorTypeTransfer> getSelectorTypeTransfersBySelectorKind(UserVisit userVisit, SelectorKind selectorKind) {
        return getSelectorTypeTransfers(userVisit, getSelectorTypes(selectorKind));
    }

    private void updateSelectorTypeFromValue(SelectorTypeDetailValue selectorTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(selectorTypeDetailValue.hasBeenModified()) {
            var selectorType = selectorTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     selectorTypeDetailValue.getSelectorTypePK());
            var selectorTypeDetail = selectorType.getActiveDetailForUpdate();

            selectorTypeDetail.setThruTime(session.getStartTime());
            selectorTypeDetail.store();

            var selectorTypePK = selectorTypeDetail.getSelectorTypePK();
            var selectorKind = selectorTypeDetail.getSelectorKind();
            var selectorKindPK = selectorKind.getPrimaryKey();
            var selectorTypeName = selectorTypeDetailValue.getSelectorTypeName();
            var isDefault = selectorTypeDetailValue.getIsDefault();
            var sortOrder = selectorTypeDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultSelectorType = getDefaultSelectorType(selectorKind);
                var defaultFound = defaultSelectorType != null && !defaultSelectorType.equals(selectorType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultSelectorTypeDetailValue = getDefaultSelectorTypeDetailValueForUpdate(selectorKind);

                    defaultSelectorTypeDetailValue.setIsDefault(false);
                    updateSelectorTypeFromValue(defaultSelectorTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            selectorTypeDetail = selectorTypeDetailFactory.create(selectorTypePK, selectorKindPK, selectorTypeName, isDefault, sortOrder,
                    session.getStartTime(), Session.MAX_TIME);

            selectorType.setActiveDetail(selectorTypeDetail);
            selectorType.setLastDetail(selectorTypeDetail);

            sendEvent(selectorTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateSelectorTypeFromValue(SelectorTypeDetailValue selectorTypeDetailValue, BasePK updatedBy) {
        updateSelectorTypeFromValue(selectorTypeDetailValue, true, updatedBy);
    }

    public void deleteSelectorType(SelectorType selectorType, BasePK deletedBy) {
        deleteSelectorTypeDescriptionsBySelectorType(selectorType, deletedBy);
        deleteSelectorsBySelectorType(selectorType, deletedBy);

        var selectorTypeDetail = selectorType.getLastDetailForUpdate();
        selectorTypeDetail.setThruTime(session.getStartTime());
        selectorType.setActiveDetail(null);
        selectorType.store();

        // Check for default, and pick one if necessary
        var selectorKind = selectorTypeDetail.getSelectorKind();
        var defaultSelectorType = getDefaultSelectorType(selectorKind);
        if(defaultSelectorType == null) {
            var selectorTypes = getSelectorTypesForUpdate(selectorKind);

            if(!selectorTypes.isEmpty()) {
                var iter = selectorTypes.iterator();
                if(iter.hasNext()) {
                    defaultSelectorType = iter.next();
                }
                var selectorTypeDetailValue = Objects.requireNonNull(defaultSelectorType).getLastDetailForUpdate().getSelectorTypeDetailValue().clone();

                selectorTypeDetailValue.setIsDefault(true);
                updateSelectorTypeFromValue(selectorTypeDetailValue, false, deletedBy);
            }
        }

        sendEvent(selectorType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteSelectorTypesBySelectorKind(SelectorKind selectorKind, BasePK deletedBy) {
        var selectorTypes = getSelectorTypesForUpdate(selectorKind);

        selectorTypes.forEach((selectorType) -> 
                deleteSelectorType(selectorType, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Selector Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected SelectorTypeDescriptionFactory selectorTypeDescriptionFactory;

    @Inject
    private SelectorTypeDescriptionTransferCache selectorTypeDescriptionTransferCache;

    public SelectorTypeDescription createSelectorTypeDescription(SelectorType selectorType, Language language, String description,
            BasePK createdBy) {
        var selectorTypeDescription = selectorTypeDescriptionFactory.create(selectorType,
                language, description, session.getStartTime(), Session.MAX_TIME);

        sendEvent(selectorType.getPrimaryKey(), EventTypes.MODIFY, selectorTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return selectorTypeDescription;
    }

    private SelectorTypeDescription getSelectorTypeDescription(SelectorType selectorType, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SelectorTypeDescriptions.fields())
                .from(SelectorTypeDescriptions)
                .where(SelectorTypeDescriptions.SELECTOR_TYPE.eq(selectorType.getPrimaryKey()),
                        SelectorTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        SelectorTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return selectorTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }

    public SelectorTypeDescription getSelectorTypeDescription(SelectorType selectorType, Language language) {
        return getSelectorTypeDescription(selectorType, language, EntityPermission.READ_ONLY);
    }

    public SelectorTypeDescription getSelectorTypeDescriptionForUpdate(SelectorType selectorType, Language language) {
        return getSelectorTypeDescription(selectorType, language, EntityPermission.READ_WRITE);
    }

    public SelectorTypeDescriptionValue getSelectorTypeDescriptionValue(SelectorTypeDescription selectorTypeDescription) {
        return selectorTypeDescription == null? null: selectorTypeDescription.getSelectorTypeDescriptionValue().clone();
    }

    public SelectorTypeDescriptionValue getSelectorTypeDescriptionValueForUpdate(SelectorType selectorType, Language language) {
        return getSelectorTypeDescriptionValue(getSelectorTypeDescriptionForUpdate(selectorType, language));
    }

    private List<SelectorTypeDescription> getSelectorTypeDescriptionsBySelectorType(SelectorType selectorType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(SelectorTypeDescriptions.fields())
                    .from(SelectorTypeDescriptions)
                    .join(Languages).onKey(SELECTOR_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(SelectorTypeDescriptions.SELECTOR_TYPE.eq(selectorType.getPrimaryKey()),
                            SelectorTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    SelectorTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(SelectorTypeDescriptions.fields())
                    .from(SelectorTypeDescriptions)
                    .where(SelectorTypeDescriptions.SELECTOR_TYPE.eq(selectorType.getPrimaryKey()),
                            SelectorTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return selectorTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<SelectorTypeDescription> getSelectorTypeDescriptionsBySelectorType(SelectorType selectorType) {
        return getSelectorTypeDescriptionsBySelectorType(selectorType, EntityPermission.READ_ONLY);
    }

    public List<SelectorTypeDescription> getSelectorTypeDescriptionsBySelectorTypeForUpdate(SelectorType selectorType) {
        return getSelectorTypeDescriptionsBySelectorType(selectorType, EntityPermission.READ_WRITE);
    }

    public String getBestSelectorTypeDescription(SelectorType selectorType, Language language) {
        String description;
        var selectorTypeDescription = getSelectorTypeDescription(selectorType, language);

        if(selectorTypeDescription == null && !language.getIsDefault()) {
            selectorTypeDescription = getSelectorTypeDescription(selectorType, partyControl.getDefaultLanguage());
        }

        if(selectorTypeDescription == null) {
            description = selectorType.getLastDetail().getSelectorTypeName();
        } else {
            description = selectorTypeDescription.getDescription();
        }

        return description;
    }

    public SelectorTypeDescriptionTransfer getSelectorTypeDescriptionTransfer(UserVisit userVisit, SelectorTypeDescription selectorTypeDescription) {
        return selectorTypeDescriptionTransferCache.getSelectorTypeDescriptionTransfer(userVisit, selectorTypeDescription);
    }

    public List<SelectorTypeDescriptionTransfer> getSelectorTypeDescriptionTransfersBySelectorType(UserVisit userVisit, SelectorType selectorType) {
        var selectorTypeDescriptions = getSelectorTypeDescriptionsBySelectorType(selectorType);
        List<SelectorTypeDescriptionTransfer> selectorTypeDescriptionTransfers = new ArrayList<>(selectorTypeDescriptions.size());

        selectorTypeDescriptions.forEach((selectorTypeDescription) -> {
            selectorTypeDescriptionTransfers.add(selectorTypeDescriptionTransferCache.getSelectorTypeDescriptionTransfer(userVisit, selectorTypeDescription));
        });

        return selectorTypeDescriptionTransfers;
    }

    public void updateSelectorTypeDescriptionFromValue(SelectorTypeDescriptionValue selectorTypeDescriptionValue, BasePK updatedBy) {
        if(selectorTypeDescriptionValue.hasBeenModified()) {
            var selectorTypeDescription = selectorTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     selectorTypeDescriptionValue.getPrimaryKey());

            selectorTypeDescription.setThruTime(session.getStartTime());
            selectorTypeDescription.store();

            var selectorType = selectorTypeDescription.getSelectorType();
            var language = selectorTypeDescription.getLanguage();
            var description = selectorTypeDescriptionValue.getDescription();

            selectorTypeDescription = selectorTypeDescriptionFactory.create(selectorType, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(selectorType.getPrimaryKey(), EventTypes.MODIFY, selectorTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteSelectorTypeDescription(SelectorTypeDescription selectorTypeDescription, BasePK deletedBy) {
        selectorTypeDescription.setThruTime(session.getStartTime());

        sendEvent(selectorTypeDescription.getSelectorTypePK(), EventTypes.MODIFY, selectorTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteSelectorTypeDescriptionsBySelectorType(SelectorType selectorType, BasePK deletedBy) {
        var selectorTypeDescriptions = getSelectorTypeDescriptionsBySelectorTypeForUpdate(selectorType);

        selectorTypeDescriptions.forEach((selectorTypeDescription) -> 
                deleteSelectorTypeDescription(selectorTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Selector Boolean Types
    // --------------------------------------------------------------------------------

    @Inject
    protected SelectorBooleanTypeFactory selectorBooleanTypeFactory;

    public SelectorBooleanType createSelectorBooleanType(String selectorBooleanTypeName, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var selectorBooleanType = selectorBooleanTypeFactory.create(selectorBooleanTypeName,
                isDefault, sortOrder);

        sendEvent(selectorBooleanType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return selectorBooleanType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.SelectorBooleanType */
    public SelectorBooleanType getSelectorBooleanTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new SelectorBooleanTypePK(entityInstance.getEntityUniqueId());

        return selectorBooleanTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public SelectorBooleanType getSelectorBooleanTypeByEntityInstance(EntityInstance entityInstance) {
        return getSelectorBooleanTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public SelectorBooleanType getSelectorBooleanTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getSelectorBooleanTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countSelectorBooleanTypes() {
        return session.getDslContext()
                .selectCount()
                .from(SelectorBooleanTypes)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public List<SelectorBooleanType> getSelectorBooleanTypes() {
        var query = session.applyLimit(session.getDslContext()
                .select(SelectorBooleanTypes.fields())
                .from(SelectorBooleanTypes)
                .orderBy(SelectorBooleanTypes.SORT_ORDER, SelectorBooleanTypes.SELECTOR_BOOLEAN_TYPE_NAME),
                SelectorBooleanTypeFactory.class);

        return selectorBooleanTypeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public SelectorBooleanType getSelectorBooleanTypeByName(String selectorBooleanTypeName) {
        var query = session.getDslContext()
                .select(SelectorBooleanTypes.fields())
                .from(SelectorBooleanTypes)
                .where(SelectorBooleanTypes.SELECTOR_BOOLEAN_TYPE_NAME.eq(selectorBooleanTypeName));

        return selectorBooleanTypeFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public SelectorBooleanTypeChoicesBean getSelectorBooleanTypeChoices(String defaultSelectorBooleanTypeChoice, Language language,
            boolean allowNullChoice) {
        var selectorBooleanTypes = getSelectorBooleanTypes();
        var size = selectorBooleanTypes.size() + (allowNullChoice? 1: 0);
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultSelectorBooleanTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var selectorBooleanType : selectorBooleanTypes) {
            var label = getBestSelectorBooleanTypeDescription(selectorBooleanType, language);
            var value = selectorBooleanType.getSelectorBooleanTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultSelectorBooleanTypeChoice != null && defaultSelectorBooleanTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && selectorBooleanType.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new SelectorBooleanTypeChoicesBean(labels, values, defaultValue);
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Boolean Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected SelectorBooleanTypeDescriptionFactory selectorBooleanTypeDescriptionFactory;

    public SelectorBooleanTypeDescription createSelectorBooleanTypeDescription(SelectorBooleanType selectorBooleanType,
            Language language, String description, BasePK createdBy) {
        var selectorBooleanTypeDescription = selectorBooleanTypeDescriptionFactory.create(
                selectorBooleanType, language, description);

        sendEvent(selectorBooleanType.getPrimaryKey(), EventTypes.MODIFY, selectorBooleanTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return selectorBooleanTypeDescription;
    }
    
    public SelectorBooleanTypeDescription getSelectorBooleanTypeDescription(SelectorBooleanType selectorBooleanType, Language language) {
        var query = session.getDslContext()
                .select(SelectorBooleanTypeDescriptions.fields())
                .from(SelectorBooleanTypeDescriptions)
                .where(SelectorBooleanTypeDescriptions.SELECTOR_BOOLEAN_TYPE.eq(selectorBooleanType.getPrimaryKey()),
                        SelectorBooleanTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()));

        return selectorBooleanTypeDescriptionFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public String getBestSelectorBooleanTypeDescription(SelectorBooleanType selectorBooleanType, Language language) {
        String description;
        var selectorBooleanTypeDescription = getSelectorBooleanTypeDescription(selectorBooleanType, language);
        
        if(selectorBooleanTypeDescription == null && !language.getIsDefault()) {
            selectorBooleanTypeDescription = getSelectorBooleanTypeDescription(selectorBooleanType, partyControl.getDefaultLanguage());
        }
        
        if(selectorBooleanTypeDescription == null) {
            description = selectorBooleanType.getSelectorBooleanTypeName();
        } else {
            description = selectorBooleanTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Comparison Types
    // --------------------------------------------------------------------------------

    @Inject
    protected SelectorComparisonTypeFactory selectorComparisonTypeFactory;

    public SelectorComparisonType createSelectorComparisonType(String selectorComparisonTypeName, Boolean isDefault,
            Integer sortOrder, BasePK createdBy) {
        var selectorComparisonType = selectorComparisonTypeFactory.create(selectorComparisonTypeName,
                isDefault, sortOrder);

        sendEvent(selectorComparisonType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return selectorComparisonType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.SelectorComparisonType */
    public SelectorComparisonType getSelectorComparisonTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new SelectorComparisonTypePK(entityInstance.getEntityUniqueId());

        return selectorComparisonTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public SelectorComparisonType getSelectorComparisonTypeByEntityInstance(EntityInstance entityInstance) {
        return getSelectorComparisonTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public SelectorComparisonType getSelectorComparisonTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getSelectorComparisonTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countSelectorComparisonTypes() {
        return session.getDslContext()
                .selectCount()
                .from(SelectorComparisonTypes)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public List<SelectorComparisonType> getSelectorComparisonTypes() {
        var query = session.applyLimit(session.getDslContext()
                .select(SelectorComparisonTypes.fields())
                .from(SelectorComparisonTypes)
                .orderBy(SelectorComparisonTypes.SORT_ORDER, SelectorComparisonTypes.SELECTOR_COMPARISON_TYPE_NAME),
                SelectorComparisonTypeFactory.class);

        return selectorComparisonTypeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public SelectorComparisonType getSelectorComparisonTypeByName(String selectorComparisonTypeName) {
        var query = session.getDslContext()
                .select(SelectorComparisonTypes.fields())
                .from(SelectorComparisonTypes)
                .where(SelectorComparisonTypes.SELECTOR_COMPARISON_TYPE_NAME.eq(selectorComparisonTypeName));

        return selectorComparisonTypeFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public SelectorComparisonTypeChoicesBean getSelectorComparisonTypeChoices(String defaultSelectorComparisonTypeChoice,
            Language language, boolean allowNullChoice) {
        var selectorComparisonTypes = getSelectorComparisonTypes();
        var size = selectorComparisonTypes.size() + (allowNullChoice? 1: 0);
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultSelectorComparisonTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var selectorComparisonType : selectorComparisonTypes) {
            var label = getBestSelectorComparisonTypeDescription(selectorComparisonType, language);
            var value = selectorComparisonType.getSelectorComparisonTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultSelectorComparisonTypeChoice != null && defaultSelectorComparisonTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && selectorComparisonType.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new SelectorComparisonTypeChoicesBean(labels, values, defaultValue);
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Comparison Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected SelectorComparisonTypeDescriptionFactory selectorComparisonTypeDescriptionFactory;

    public SelectorComparisonTypeDescription createSelectorComparisonTypeDescription(SelectorComparisonType selectorComparisonType,
            Language language, String description, BasePK createdBy) {
        var selectorComparisonTypeDescription = selectorComparisonTypeDescriptionFactory.create(selectorComparisonType, language, description);

        sendEvent(selectorComparisonType.getPrimaryKey(), EventTypes.MODIFY, selectorComparisonTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return selectorComparisonTypeDescription;
    }
    
    public SelectorComparisonTypeDescription getSelectorComparisonTypeDescription(SelectorComparisonType selectorComparisonType, Language language) {
        var query = session.getDslContext()
                .select(SelectorComparisonTypeDescriptions.fields())
                .from(SelectorComparisonTypeDescriptions)
                .where(SelectorComparisonTypeDescriptions.SELECTOR_COMPARISON_TYPE.eq(selectorComparisonType.getPrimaryKey()),
                        SelectorComparisonTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()));

        return selectorComparisonTypeDescriptionFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public String getBestSelectorComparisonTypeDescription(SelectorComparisonType selectorComparisonType, Language language) {
        String description;
        var selectorComparisonTypeDescription = getSelectorComparisonTypeDescription(selectorComparisonType, language);
        
        if(selectorComparisonTypeDescription == null && !language.getIsDefault()) {
            selectorComparisonTypeDescription = getSelectorComparisonTypeDescription(selectorComparisonType, partyControl.getDefaultLanguage());
        }
        
        if(selectorComparisonTypeDescription == null) {
            description = selectorComparisonType.getSelectorComparisonTypeName();
        } else {
            description = selectorComparisonTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Node Types
    // --------------------------------------------------------------------------------

    @Inject
    protected SelectorNodeTypeFactory selectorNodeTypeFactory;

    @Inject
    private SelectorNodeTypeTransferCache selectorNodeTypeTransferCache;

    public SelectorNodeType createSelectorNodeType(String selectorNodeTypeName, Integer sortOrder, BasePK createdBy) {
        var selectorNodeType = selectorNodeTypeFactory.create(selectorNodeTypeName, sortOrder);

        sendEvent(selectorNodeType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return selectorNodeType;
    }
    
    /** Assume that the entityInstance passed to this function is a ECHO_THREE.SelectorNodeType */
    public SelectorNodeType getSelectorNodeTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new SelectorNodeTypePK(entityInstance.getEntityUniqueId());

        return selectorNodeTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public SelectorNodeType getSelectorNodeTypeByEntityInstance(EntityInstance entityInstance) {
        return getSelectorNodeTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public SelectorNodeType getSelectorNodeTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getSelectorNodeTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countSelectorNodeTypes() {
        return session.getDslContext()
                .selectCount()
                .from(SelectorNodeTypes)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countSelectorNodeTypesBySelectorKind(final SelectorKind selectorKind) {
        return session.getDslContext()
                .selectCount()
                .from(SelectorNodeTypes)
                .join(SelectorNodeTypeUses).onKey(SELECTOR_NODE_TYPE_USES_SELECTOR_NODE_TYPE_FK)
                .where(SelectorNodeTypeUses.SELECTOR_KIND.eq(selectorKind.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public SelectorNodeType getSelectorNodeTypeByName(String selectorNodeTypeName) {
        var query = session.getDslContext()
                .select(SelectorNodeTypes.fields())
                .from(SelectorNodeTypes)
                .where(SelectorNodeTypes.SELECTOR_NODE_TYPE_NAME.eq(selectorNodeTypeName));

        return selectorNodeTypeFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public List<SelectorNodeType> getSelectorNodeTypes() {
        var query = session.applyLimit(session.getDslContext()
                .select(SelectorNodeTypes.fields())
                .from(SelectorNodeTypes)
                .orderBy(SelectorNodeTypes.SORT_ORDER, SelectorNodeTypes.SELECTOR_NODE_TYPE_NAME),
                SelectorNodeTypeFactory.class);

        return selectorNodeTypeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public List<SelectorNodeType> getSelectorNodeTypesBySelectorKind(SelectorKind selectorKind) {
        var query = session.applyLimit(session.getDslContext()
                .select(SelectorNodeTypes.fields())
                .from(SelectorNodeTypes)
                .join(SelectorNodeTypeUses).onKey(SELECTOR_NODE_TYPE_USES_SELECTOR_NODE_TYPE_FK)
                .where(SelectorNodeTypeUses.SELECTOR_KIND.eq(selectorKind.getPrimaryKey()))
                .orderBy(SelectorNodeTypes.SORT_ORDER, SelectorNodeTypes.SELECTOR_NODE_TYPE_NAME),
                SelectorNodeTypeFactory.class);

        return selectorNodeTypeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public SelectorNodeTypeTransfer getSelectorNodeTypeTransfer(UserVisit userVisit, SelectorNodeType selectorNodeType) {
        return selectorNodeTypeTransferCache.getSelectorNodeTypeTransfer(userVisit, selectorNodeType);
    }
    
    public List<SelectorNodeTypeTransfer> getSelectorNodeTypeTransfers(UserVisit userVisit, Collection<SelectorNodeType> selectorNodeTypes) {
        List<SelectorNodeTypeTransfer> selectorNodeTypeTransfers = new ArrayList<>(selectorNodeTypes.size());
        
        selectorNodeTypes.forEach((selectorNodeType) ->
                selectorNodeTypeTransfers.add(selectorNodeTypeTransferCache.getSelectorNodeTypeTransfer(userVisit, selectorNodeType))
        );
        
        return selectorNodeTypeTransfers;
    }
    
    public List<SelectorNodeTypeTransfer> getSelectorNodeTypeTransfers(UserVisit userVisit) {
        return getSelectorNodeTypeTransfers(userVisit, getSelectorNodeTypes());
    }
    
    public List<SelectorNodeTypeTransfer> getSelectorNodeTypeTransfersBySelectorKind(UserVisit userVisit, SelectorKind selectorKind) {
        return getSelectorNodeTypeTransfers(userVisit, getSelectorNodeTypesBySelectorKind(selectorKind));
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Node Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected SelectorNodeTypeDescriptionFactory selectorNodeTypeDescriptionFactory;

    public SelectorNodeTypeDescription createSelectorNodeTypeDescription(SelectorNodeType selectorNodeType, Language language,
            String description, BasePK createdBy) {
        var selectorNodeTypeDescription = selectorNodeTypeDescriptionFactory.create(selectorNodeType, language, description);

        sendEvent(selectorNodeType.getPrimaryKey(), EventTypes.MODIFY, selectorNodeTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return selectorNodeTypeDescription;
    }
    
    public SelectorNodeTypeDescription getSelectorNodeTypeDescription(SelectorNodeType selectorNodeType, Language language) {
        var query = session.getDslContext()
                .select(SelectorNodeTypeDescriptions.fields())
                .from(SelectorNodeTypeDescriptions)
                .where(SelectorNodeTypeDescriptions.SELECTOR_NODE_TYPE.eq(selectorNodeType.getPrimaryKey()),
                        SelectorNodeTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()));

        return selectorNodeTypeDescriptionFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public String getBestSelectorNodeTypeDescription(SelectorNodeType selectorNodeType, Language language) {
        String description;
        var selectorNodeTypeDescription = getSelectorNodeTypeDescription(selectorNodeType, language);
        
        if(selectorNodeTypeDescription == null && !language.getIsDefault()) {
            selectorNodeTypeDescription = getSelectorNodeTypeDescription(selectorNodeType, partyControl.getDefaultLanguage());
        }
        
        if(selectorNodeTypeDescription == null) {
            description = selectorNodeType.getSelectorNodeTypeName();
        } else {
            description = selectorNodeTypeDescription.getDescription();
        }
        
        return description;
    }

    // --------------------------------------------------------------------------------
    //   Selector Node Type Uses
    // --------------------------------------------------------------------------------

    @Inject
    protected SelectorNodeTypeUseFactory selectorNodeTypeUseFactory;

    public SelectorNodeTypeUse createSelectorNodeTypeUse(SelectorKind selectorKind, SelectorNodeType selectorNodeType,
            Boolean isDefault, BasePK createdBy) {
        var selectorNodeTypeUse = selectorNodeTypeUseFactory.create(selectorKind, selectorNodeType, isDefault);

        sendEvent(selectorKind.getPrimaryKey(), EventTypes.CREATE, selectorNodeTypeUse.getPrimaryKey(), EventTypes.MODIFY, createdBy);

        return selectorNodeTypeUse;
    }

    public SelectorNodeTypeUse getSelectorNodeTypeUse(SelectorKind selectorKind, SelectorNodeType selectorNodeType) {
        var query = session.getDslContext()
                .select(SelectorNodeTypeUses.fields())
                .from(SelectorNodeTypeUses)
                .where(SelectorNodeTypeUses.SELECTOR_KIND.eq(selectorKind.getPrimaryKey()),
                        SelectorNodeTypeUses.SELECTOR_NODE_TYPE.eq(selectorNodeType.getPrimaryKey()));

        return selectorNodeTypeUseFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }

    // --------------------------------------------------------------------------------
    //   Selector Text Search Types
    // --------------------------------------------------------------------------------

    @Inject
    protected SelectorTextSearchTypeFactory selectorTextSearchTypeFactory;

    public SelectorTextSearchType createSelectorTextSearchType(String selectorTextSearchTypeName, Boolean isDefault,
            Integer sortOrder, BasePK createdBy) {
        var selectorTextSearchType = selectorTextSearchTypeFactory.create(selectorTextSearchTypeName,
                isDefault, sortOrder);

        sendEvent(selectorTextSearchType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return selectorTextSearchType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.SelectorTextSearchType */
    public SelectorTextSearchType getSelectorTextSearchTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new SelectorTextSearchTypePK(entityInstance.getEntityUniqueId());

        return selectorTextSearchTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public SelectorTextSearchType getSelectorTextSearchTypeByEntityInstance(EntityInstance entityInstance) {
        return getSelectorTextSearchTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public SelectorTextSearchType getSelectorTextSearchTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getSelectorTextSearchTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countSelectorTextSearchTypes() {
        return session.getDslContext()
                .selectCount()
                .from(SelectorTextSearchTypes)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public List<SelectorTextSearchType> getSelectorTextSearchTypes() {
        var query = session.applyLimit(session.getDslContext()
                .select(SelectorTextSearchTypes.fields())
                .from(SelectorTextSearchTypes)
                .orderBy(SelectorTextSearchTypes.SORT_ORDER, SelectorTextSearchTypes.SELECTOR_TEXT_SEARCH_TYPE_NAME),
                SelectorTextSearchTypeFactory.class);

        return selectorTextSearchTypeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public SelectorTextSearchType getSelectorTextSearchTypeByName(String selectorTextSearchTypeName) {
        var query = session.getDslContext()
                .select(SelectorTextSearchTypes.fields())
                .from(SelectorTextSearchTypes)
                .where(SelectorTextSearchTypes.SELECTOR_TEXT_SEARCH_TYPE_NAME.eq(selectorTextSearchTypeName));

        return selectorTextSearchTypeFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public SelectorTextSearchTypeChoicesBean getSelectorTextSearchTypeChoices(String defaultSelectorTextSearchTypeChoice,
            Language language, boolean allowNullChoice) {
        var selectorTextSearchTypes = getSelectorTextSearchTypes();
        var size = selectorTextSearchTypes.size() + (allowNullChoice? 1: 0);
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultSelectorTextSearchTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var selectorTextSearchType : selectorTextSearchTypes) {
            var label = getBestSelectorTextSearchTypeDescription(selectorTextSearchType, language);
            var value = selectorTextSearchType.getSelectorTextSearchTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultSelectorTextSearchTypeChoice != null && defaultSelectorTextSearchTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && selectorTextSearchType.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new SelectorTextSearchTypeChoicesBean(labels, values, defaultValue);
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Text Search Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected SelectorTextSearchTypeDescriptionFactory selectorTextSearchTypeDescriptionFactory;

    public SelectorTextSearchTypeDescription createSelectorTextSearchTypeDescription(SelectorTextSearchType selectorTextSearchType,
            Language language, String description, BasePK createdBy) {
        var selectorTextSearchTypeDescription = selectorTextSearchTypeDescriptionFactory.create(selectorTextSearchType, language, description);

        sendEvent(selectorTextSearchType.getPrimaryKey(), EventTypes.MODIFY, selectorTextSearchTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return selectorTextSearchTypeDescription;
    }
    
    public SelectorTextSearchTypeDescription getSelectorTextSearchTypeDescription(SelectorTextSearchType selectorTextSearchType, Language language) {
        var query = session.getDslContext()
                .select(SelectorTextSearchTypeDescriptions.fields())
                .from(SelectorTextSearchTypeDescriptions)
                .where(SelectorTextSearchTypeDescriptions.SELECTOR_TEXT_SEARCH_TYPE.eq(selectorTextSearchType.getPrimaryKey()),
                        SelectorTextSearchTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()));

        return selectorTextSearchTypeDescriptionFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public String getBestSelectorTextSearchTypeDescription(SelectorTextSearchType selectorTextSearchType, Language language) {
        String description;
        var selectorTextSearchTypeDescription = getSelectorTextSearchTypeDescription(selectorTextSearchType, language);
        
        if(selectorTextSearchTypeDescription == null && !language.getIsDefault()) {
            selectorTextSearchTypeDescription = getSelectorTextSearchTypeDescription(selectorTextSearchType, partyControl.getDefaultLanguage());
        }
        
        if(selectorTextSearchTypeDescription == null) {
            description = selectorTextSearchType.getSelectorTextSearchTypeName();
        } else {
            description = selectorTextSearchTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Selectors
    // --------------------------------------------------------------------------------

    @Inject
    protected SelectorDetailFactory selectorDetailFactory;

    @Inject
    protected SelectorFactory selectorFactory;

    @Inject
    private SelectorTransferCache selectorTransferCache;

    public Selector createSelector(SelectorType selectorType, String selectorName, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var defaultSelector = getDefaultSelector(selectorType);
        var defaultFound = defaultSelector != null;
        
        if(defaultFound && isDefault) {
            var defaultSelectorDetailValue = getDefaultSelectorDetailValueForUpdate(selectorType);
            
            defaultSelectorDetailValue.setIsDefault(false);
            updateSelectorFromValue(defaultSelectorDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var selector = selectorFactory.create();
        var selectorDetail = selectorDetailFactory.create(selector, selectorType, selectorName,
                isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        selector = selectorFactory.getEntityFromPK(EntityPermission.READ_WRITE, selector.getPrimaryKey());
        selector.setActiveDetail(selectorDetail);
        selector.setLastDetail(selectorDetail);
        selector.store();
        
        sendEvent(selector.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return selector;
    }

    public long countSelectorsBySelectorType(SelectorType selectorType) {
        return session.getDslContext()
                .selectCount()
                .from(Selectors)
                .join(SelectorDetails).onKey(SELECTORS_ACTIVE_DETAIL_FK)
                .where(SelectorDetails.SELECTOR_TYPE.eq(selectorType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.Selector */
    public Selector getSelectorByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new SelectorPK(entityInstance.getEntityUniqueId());

        return selectorFactory.getEntityFromPK(entityPermission, pk);
    }

    public Selector getSelectorByEntityInstance(EntityInstance entityInstance) {
        return getSelectorByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public Selector getSelectorByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getSelectorByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public Selector getSelectorByName(SelectorType selectorType, String selectorName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Selectors.fields())
                .from(Selectors)
                .join(SelectorDetails).onKey(SELECTORS_ACTIVE_DETAIL_FK)
                .where(SelectorDetails.SELECTOR_TYPE.eq(selectorType.getPrimaryKey()),
                        SelectorDetails.SELECTOR_NAME.eq(selectorName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return selectorFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public Selector getSelectorByName(SelectorType selectorType, String selectorName) {
        return getSelectorByName(selectorType, selectorName, EntityPermission.READ_ONLY);
    }
    
    public Selector getSelectorByNameForUpdate(SelectorType selectorType, String selectorName) {
        return getSelectorByName(selectorType, selectorName, EntityPermission.READ_WRITE);
    }

    public Selector getSelectorUsingNames(ExecutionErrorAccumulator eea, String selectorKindName, String selectorTypeName, String selectorName,
            String unknownSelectorNameError) {
        var selectorKind = getSelectorKindByName(selectorKindName);
        Selector result = null;

        if(selectorKind != null) {
            var selectorType = getSelectorTypeByName(selectorKind, selectorTypeName);

            if(selectorType != null) {
                result = getSelectorByName(selectorType, selectorName);

                if(result == null) {
                    eea.addExecutionError(unknownSelectorNameError, selectorType, selectorName);
                }
            } else {
                eea.addExecutionError(ExecutionErrors.UnknownSelectorTypeName.name(), selectorKindName, selectorTypeName);
            }
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownSelectorKindName.name(), selectorKindName);
        }

        return result;
    }

    public SelectorDetailValue getSelectorDetailValueForUpdate(Selector selector) {
        return selector == null? null: selector.getLastDetailForUpdate().getSelectorDetailValue().clone();
    }
    
    public SelectorDetailValue getSelectorDetailValueByNameForUpdate(SelectorType selectorType, String selectorName) {
        return getSelectorDetailValueForUpdate(getSelectorByNameForUpdate(selectorType, selectorName));
    }
    
    public Selector getDefaultSelector(SelectorType selectorType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Selectors.fields())
                .from(Selectors)
                .join(SelectorDetails).onKey(SELECTORS_ACTIVE_DETAIL_FK)
                .where(SelectorDetails.SELECTOR_TYPE.eq(selectorType.getPrimaryKey()),
                        SelectorDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return selectorFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public Selector getDefaultSelector(SelectorType selectorType) {
        return getDefaultSelector(selectorType, EntityPermission.READ_ONLY);
    }
    
    public Selector getDefaultSelectorForUpdate(SelectorType selectorType) {
        return getDefaultSelector(selectorType, EntityPermission.READ_WRITE);
    }
    
    public SelectorDetailValue getDefaultSelectorDetailValueForUpdate(SelectorType selectorType) {
        return getDefaultSelectorForUpdate(selectorType).getLastDetailForUpdate().getSelectorDetailValue().clone();
    }
    
    private List<Selector> getSelectorsBySelectorType(SelectorType selectorType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Selectors.fields())
                .from(Selectors)
                .join(SelectorDetails).onKey(SELECTORS_ACTIVE_DETAIL_FK)
                .where(SelectorDetails.SELECTOR_TYPE.eq(selectorType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(SelectorDetails.SORT_ORDER, SelectorDetails.SELECTOR_NAME),
                    SelectorFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return selectorFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<Selector> getSelectorsBySelectorType(SelectorType selectorType) {
        return getSelectorsBySelectorType(selectorType, EntityPermission.READ_ONLY);
    }
    
    public List<Selector> getSelectorsBySelectorTypeForUpdate(SelectorType selectorType) {
        return getSelectorsBySelectorType(selectorType, EntityPermission.READ_WRITE);
    }
    
    public List<Selector> getSelectorsBySelectorKind(SelectorKind selectorKind) {
        var query = session.applyLimit(session.getDslContext()
                .select(Selectors.fields())
                .from(Selectors)
                .join(SelectorDetails).onKey(SELECTORS_ACTIVE_DETAIL_FK)
                .join(SelectorTypes).onKey(SELECTOR_DETAILS_SELECTOR_TYPE_FK)
                .join(SelectorTypeDetails).onKey(SELECTOR_TYPES_LAST_DETAIL_FK)
                .where(SelectorTypeDetails.SELECTOR_KIND.eq(selectorKind.getPrimaryKey()))
                .orderBy(SelectorTypeDetails.SORT_ORDER, SelectorTypeDetails.SELECTOR_TYPE_NAME, SelectorDetails.SORT_ORDER, SelectorDetails.SELECTOR_NAME),
                SelectorFactory.class);

        return selectorFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public SelectorChoicesBean getSelectorChoices(SelectorType selectorType, String defaultSelectorChoice, Language language,
            boolean allowNullChoice) {
        var selectors = getSelectorsBySelectorType(selectorType);
        var size = selectors.size() + (allowNullChoice? 1: 0);
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultSelectorChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var selector : selectors) {
            var selectorDetail = selector.getLastDetail();
            
            var label = getBestSelectorDescription(selector, language);
            var value = selector.getLastDetail().getSelectorName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultSelectorChoice != null && defaultSelectorChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && selectorDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new SelectorChoicesBean(labels, values, defaultValue);
    }
    
    public SelectorTransfer getSelectorTransfer(UserVisit userVisit, Selector selector) {
        return selectorTransferCache.getSelectorTransfer(userVisit, selector);
    }
    
    public List<SelectorTransfer> getSelectorTransfers(UserVisit userVisit, Collection<Selector> selectors) {
        List<SelectorTransfer> selectorTransfers = new ArrayList<>(selectors.size());
        
        selectors.forEach((selector) ->
                selectorTransfers.add(selectorTransferCache.getSelectorTransfer(userVisit, selector))
        );
        
        return selectorTransfers;
    }
    
    public List<SelectorTransfer> getSelectorTransfersBySelectorType(UserVisit userVisit, SelectorType selectorType) {
        return getSelectorTransfers(userVisit, getSelectorsBySelectorType(selectorType));
    }
    
    private void updateSelectorFromValue(SelectorDetailValue selectorDetailValue, boolean checkDefault, BasePK updatedBy) {
        var selector = selectorFactory.getEntityFromPK(
                EntityPermission.READ_WRITE, selectorDetailValue.getSelectorPK());
        var selectorDetail = selector.getActiveDetailForUpdate();
        
        selectorDetail.setThruTime(session.getStartTime());
        selectorDetail.store();

        var selectorPK = selectorDetail.getSelectorPK();
        var selectorType = selectorDetail.getSelectorType(); // Not updated
        var selectorName = selectorDetailValue.getSelectorName();
        var isDefault = selectorDetailValue.getIsDefault();
        var sortOrder = selectorDetailValue.getSortOrder();
        
        if(checkDefault) {
            var defaultSelector = getDefaultSelector(selectorType);
            var defaultFound = defaultSelector != null && !defaultSelector.equals(selector);
            
            if(isDefault && defaultFound) {
                // If I'm the default, and a default already existed...
                var defaultSelectorDetailValue = getDefaultSelectorDetailValueForUpdate(selectorType);
                
                defaultSelectorDetailValue.setIsDefault(false);
                updateSelectorFromValue(defaultSelectorDetailValue, false, updatedBy);
            } else if(!isDefault && !defaultFound) {
                // If I'm not the default, and no other default exists...
                isDefault = true;
            }
        }
        
        selectorDetail = selectorDetailFactory.create(selectorPK, selectorType.getPrimaryKey(),
                selectorName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        selector.setActiveDetail(selectorDetail);
        selector.setLastDetail(selectorDetail);
        selector.store();
        
        sendEvent(selectorPK, EventTypes.MODIFY, null, null, updatedBy);
    }
    
    public void updateSelectorFromValue(SelectorDetailValue selectorDetailValue, BasePK updatedBy) {
        updateSelectorFromValue(selectorDetailValue, true, updatedBy);
    }
    
    public void deleteSelector(Selector selector, BasePK deletedBy) {
        
        securityControl.deleteSecurityRolePartyTypesBySelector(selector, deletedBy);
        deleteSelectorNodesBySelector(selector, deletedBy);
        deleteSelectorDescriptionsBySelector(selector, deletedBy);

        removeSelectorTimeBySelector(selector);

        var selectorDetail = selector.getLastDetailForUpdate();
        selectorDetail.setThruTime(session.getStartTime());
        selector.setActiveDetail(null);
        selector.store();
        
        // Check for default, and pick one if necessary
        var selectorType = selectorDetail.getSelectorType();
        var defaultSelector = getDefaultSelector(selectorType);
        if(defaultSelector == null) {
            var Selectors = getSelectorsBySelectorTypeForUpdate(selectorType);
            
            if(!Selectors.isEmpty()) {
                var iter = Selectors.iterator();
                if(iter.hasNext()) {
                    defaultSelector = iter.next();
                }
                var selectorDetailValue = Objects.requireNonNull(defaultSelector).getLastDetailForUpdate().getSelectorDetailValue().clone();
                
                selectorDetailValue.setIsDefault(true);
                updateSelectorFromValue(selectorDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(selector.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteSelectors(List<Selector> selectors, BasePK deletedBy) {
        selectors.forEach((selector) -> 
                deleteSelector(selector, deletedBy)
        );
    }
    
    public void deleteSelectorsBySelectorType(SelectorType selectorType, BasePK deletedBy) {
        deleteSelectors(getSelectorsBySelectorTypeForUpdate(selectorType), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Selector Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected SelectorDescriptionFactory selectorDescriptionFactory;

    @Inject
    private SelectorDescriptionTransferCache selectorDescriptionTransferCache;

    public SelectorDescription createSelectorDescription(Selector selector, Language language, String description, BasePK createdBy) {
        var selectorDescription = selectorDescriptionFactory.create(selector, language, description, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(selector.getPrimaryKey(), EventTypes.MODIFY, selectorDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return selectorDescription;
    }
    
    private SelectorDescription getSelectorDescription(Selector selector, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SelectorDescriptions.fields())
                .from(SelectorDescriptions)
                .where(SelectorDescriptions.SELECTOR.eq(selector.getPrimaryKey()),
                        SelectorDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        SelectorDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return selectorDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public SelectorDescription getSelectorDescription(Selector selector, Language language) {
        return getSelectorDescription(selector, language, EntityPermission.READ_ONLY);
    }
    
    public SelectorDescription getSelectorDescriptionForUpdate(Selector selector, Language language) {
        return getSelectorDescription(selector, language, EntityPermission.READ_WRITE);
    }
    
    public SelectorDescriptionValue getSelectorDescriptionValue(SelectorDescription selectorDescription) {
        return selectorDescription == null? null: selectorDescription.getSelectorDescriptionValue().clone();
    }
    
    public SelectorDescriptionValue getSelectorDescriptionValueForUpdate(Selector selector, Language language) {
        var selectorDescription = getSelectorDescriptionForUpdate(selector, language);
        
        return selectorDescription == null? null: selectorDescription.getSelectorDescriptionValue().clone();
    }
    
    private List<SelectorDescription> getSelectorDescriptionsBySelector(Selector selector, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(SelectorDescriptions.fields())
                    .from(SelectorDescriptions)
                    .join(Languages).onKey(SELECTOR_DESCRIPTIONS_LANGUAGE_FK)
                    .where(SelectorDescriptions.SELECTOR.eq(selector.getPrimaryKey()),
                            SelectorDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    SelectorDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(SelectorDescriptions.fields())
                    .from(SelectorDescriptions)
                    .where(SelectorDescriptions.SELECTOR.eq(selector.getPrimaryKey()),
                            SelectorDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return selectorDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<SelectorDescription> getSelectorDescriptionsBySelector(Selector selector) {
        return getSelectorDescriptionsBySelector(selector, EntityPermission.READ_ONLY);
    }
    
    public List<SelectorDescription> getSelectorDescriptionsBySelectorForUpdate(Selector selector) {
        return getSelectorDescriptionsBySelector(selector, EntityPermission.READ_WRITE);
    }
    
    public String getBestSelectorDescription(Selector selector, Language language) {
        String description;
        var selectorDescription = getSelectorDescription(selector, language);
        
        if(selectorDescription == null && !language.getIsDefault()) {
            selectorDescription = getSelectorDescription(selector, partyControl.getDefaultLanguage());
        }
        
        if(selectorDescription == null) {
            description = selector.getLastDetail().getSelectorName();
        } else {
            description = selectorDescription.getDescription();
        }
        
        return description;
    }
    
    public SelectorDescriptionTransfer getSelectorDescriptionTransfer(UserVisit userVisit, SelectorDescription selectorDescription) {
        return selectorDescriptionTransferCache.getSelectorDescriptionTransfer(userVisit, selectorDescription);
    }
    
    public List<SelectorDescriptionTransfer> getSelectorDescriptionTransfers(UserVisit userVisit, Selector selector) {
        var selectorDescriptions = getSelectorDescriptionsBySelector(selector);
        List<SelectorDescriptionTransfer> selectorDescriptionTransfers = new ArrayList<>(selectorDescriptions.size());
        
        selectorDescriptions.forEach((selectorDescription) -> {
            selectorDescriptionTransfers.add(selectorDescriptionTransferCache.getSelectorDescriptionTransfer(userVisit, selectorDescription));
        });
        
        return selectorDescriptionTransfers;
    }
    
    public void updateSelectorDescriptionFromValue(SelectorDescriptionValue selectorDescriptionValue, BasePK updatedBy) {
        if(selectorDescriptionValue.hasBeenModified()) {
            var selectorDescription = selectorDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE, selectorDescriptionValue.getPrimaryKey());
            
            selectorDescription.setThruTime(session.getStartTime());
            selectorDescription.store();

            var selector = selectorDescription.getSelector();
            var language = selectorDescription.getLanguage();
            var description = selectorDescriptionValue.getDescription();
            
            selectorDescription = selectorDescriptionFactory.create(selector, language, description, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(selector.getPrimaryKey(), EventTypes.MODIFY, selectorDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteSelectorDescription(SelectorDescription selectorDescription, BasePK deletedBy) {
        selectorDescription.setThruTime(session.getStartTime());
        
        sendEvent(selectorDescription.getSelectorPK(), EventTypes.MODIFY, selectorDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteSelectorDescriptionsBySelector(Selector selector, BasePK deletedBy) {
        var selectorDescriptions = getSelectorDescriptionsBySelectorForUpdate(selector);
        
        selectorDescriptions.forEach((selectorDescription) -> 
                deleteSelectorDescription(selectorDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Times
    // --------------------------------------------------------------------------------

    @Inject
    protected SelectorTimeFactory selectorTimeFactory;

    public SelectorTime createSelectorTime(Selector selector) {
        return selectorTimeFactory.create(selector, null, null, null, null);
    }


    private SelectorTime getSelectorTime(Selector selector, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SelectorTimes.fields())
                .from(SelectorTimes)
                .where(SelectorTimes.SELECTOR.eq(selector.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return selectorTimeFactory.getEntityFromQuery(entityPermission, query);
    }

    public SelectorTime getSelectorTime(Selector selector) {
        return getSelectorTime(selector, EntityPermission.READ_ONLY);
    }

    public SelectorTime getSelectorTimeForUpdate(Selector selector) {
        return getSelectorTime(selector, EntityPermission.READ_WRITE);
    }

    public void removeSelectorTimeBySelector(Selector selector) {
        var selectorTime = getSelectorTimeForUpdate(selector);

        if(selectorTime != null) {
            selectorTime.remove();
        }
    }

    // --------------------------------------------------------------------------------
    //   Selector Nodes
    // --------------------------------------------------------------------------------

    @Inject
    protected SelectorNodeDetailFactory selectorNodeDetailFactory;

    @Inject
    protected SelectorNodeFactory selectorNodeFactory;

    @Inject
    private SelectorNodeTransferCache selectorNodeTransferCache;

    public SelectorNode createSelectorNode(Selector selector, String selectorNodeName, Boolean isRootSelectorNode,
            SelectorNodeType selectorNodeType, Boolean negate, BasePK createdBy) {
        var rootSelectorNode = getRootSelectorNode(selector);
        var rootFound = rootSelectorNode != null;
        
        if(rootFound && isRootSelectorNode) {
            var rootSelectorNodeDetailValue = getRootSelectorNodeDetailValueForUpdate(selector);
            
            rootSelectorNodeDetailValue.setIsRootSelectorNode(false);
            updateSelectorNodeFromValue(rootSelectorNodeDetailValue, false, createdBy);
        } else if(!rootFound) {
            isRootSelectorNode = true;
        }

        var selectorNode = selectorNodeFactory.create();
        var selectorNodeDetail = selectorNodeDetailFactory.create(selectorNode, selector,
                selectorNodeName, isRootSelectorNode, selectorNodeType, negate, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        selectorNode = selectorNodeFactory.getEntityFromPK(EntityPermission.READ_WRITE, selectorNode.getPrimaryKey());
        selectorNode.setActiveDetail(selectorNodeDetail);
        selectorNode.setLastDetail(selectorNodeDetail);
        selectorNode.store();
        
        sendEvent(selector.getPrimaryKey(), EventTypes.MODIFY, selectorNode.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return selectorNode;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.SelectorNode */
    public SelectorNode getSelectorNodeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new SelectorNodePK(entityInstance.getEntityUniqueId());

        return selectorNodeFactory.getEntityFromPK(entityPermission, pk);
    }

    public SelectorNode getSelectorNodeByEntityInstance(EntityInstance entityInstance) {
        return getSelectorNodeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public SelectorNode getSelectorNodeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getSelectorNodeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countSelectorNodesBySelector(final Selector selector) {
        return session.getDslContext()
                .selectCount()
                .from(SelectorNodes)
                .join(SelectorNodeDetails).onKey(SELECTOR_NODES_ACTIVE_DETAIL_FK)
                .where(SelectorNodeDetails.SELECTOR.eq(selector.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countSelectorNodesBySelectorNodeType(final SelectorNodeType selectorNodeType) {
        return session.getDslContext()
                .selectCount()
                .from(SelectorNodes)
                .join(SelectorNodeDetails).onKey(SELECTOR_NODES_ACTIVE_DETAIL_FK)
                .where(SelectorNodeDetails.SELECTOR_NODE_TYPE.eq(selectorNodeType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private SelectorNode getRootSelectorNode(Selector selector, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SelectorNodes.fields())
                .from(SelectorNodes)
                .join(SelectorNodeDetails).onKey(SELECTOR_NODES_ACTIVE_DETAIL_FK)
                .where(SelectorNodeDetails.SELECTOR.eq(selector.getPrimaryKey()),
                        SelectorNodeDetails.IS_ROOT_SELECTOR_NODE.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return selectorNodeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public SelectorNode getRootSelectorNode(Selector selector) {
        return getRootSelectorNode(selector, EntityPermission.READ_ONLY);
    }
    
    public SelectorNode getRootSelectorNodeForUpdate(Selector selector) {
        return getRootSelectorNode(selector, EntityPermission.READ_WRITE);
    }
    
    public SelectorNodeDetailValue getRootSelectorNodeDetailValueForUpdate(Selector selector) {
        return getRootSelectorNodeForUpdate(selector).getLastDetailForUpdate().getSelectorNodeDetailValue().clone();
    }
    
    private SelectorNode getSelectorNodeByName(Selector selector, String selectorNodeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SelectorNodes.fields())
                .from(SelectorNodes)
                .join(SelectorNodeDetails).onKey(SELECTOR_NODES_ACTIVE_DETAIL_FK)
                .where(SelectorNodeDetails.SELECTOR.eq(selector.getPrimaryKey()),
                        SelectorNodeDetails.SELECTOR_NODE_NAME.eq(selectorNodeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return selectorNodeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public SelectorNode getSelectorNodeByName(Selector selector, String selectorNodeName) {
        return getSelectorNodeByName(selector, selectorNodeName, EntityPermission.READ_ONLY);
    }
    
    public SelectorNode getSelectorNodeByNameForUpdate(Selector selector, String selectorNodeName) {
        return getSelectorNodeByName(selector, selectorNodeName, EntityPermission.READ_WRITE);
    }
    
    public SelectorNodeDetailValue getSelectorNodeDetailValueForUpdate(SelectorNode selectorNode) {
        return selectorNode == null? null: selectorNode.getLastDetailForUpdate().getSelectorNodeDetailValue().clone();
    }
    
    public SelectorNodeDetailValue getSelectorNodeDetailValueByNameForUpdate(Selector selector, String selectorNodeName) {
        return getSelectorNodeDetailValueForUpdate(getSelectorNodeByNameForUpdate(selector, selectorNodeName));
    }
    
    private List<SelectorNode> getSelectorNodesBySelector(Selector selector, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SelectorNodes.fields())
                .from(SelectorNodes)
                .join(SelectorNodeDetails).onKey(SELECTOR_NODES_ACTIVE_DETAIL_FK)
                .where(SelectorNodeDetails.SELECTOR.eq(selector.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(SelectorNodeDetails.SELECTOR_NODE_NAME),
                    SelectorNodeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return selectorNodeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<SelectorNode> getSelectorNodesBySelector(Selector selector) {
        return getSelectorNodesBySelector(selector, EntityPermission.READ_ONLY);
    }
    
    public List<SelectorNode> getSelectorNodesBySelectorForUpdate(Selector selector) {
        return getSelectorNodesBySelector(selector, EntityPermission.READ_WRITE);
    }
    
    public SelectorNodeChoicesBean getSelectorNodeChoices(Selector selector, String defaultSelectorNodeChoice, Language language,
            boolean allowNullChoice) {
        var selectorNodes = getSelectorNodesBySelector(selector);
        var size = selectorNodes.size() + (allowNullChoice? 1: 0);
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultSelectorNodeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var selectorNode : selectorNodes) {
            var label = getBestSelectorNodeDescription(selectorNode, language);
            var value = selectorNode.getLastDetail().getSelectorNodeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultSelectorNodeChoice != null && defaultSelectorNodeChoice.equals(value);
            if(usingDefaultChoice || defaultValue == null) {
                defaultValue = value;
            }
        }
        
        return new SelectorNodeChoicesBean(labels, values, defaultValue);
    }
    
    public SelectorNodeTransfer getSelectorNodeTransfer(UserVisit userVisit, SelectorNode selectorNode) {
        return selectorNodeTransferCache.getSelectorNodeTransfer(userVisit, selectorNode);
    }
    
    public List<SelectorNodeTransfer> getSelectorNodeTransfersBySelector(UserVisit userVisit, Selector selector) {
        var selectorNodes = getSelectorNodesBySelector(selector);
        List<SelectorNodeTransfer> selectorNodeTransfers = new ArrayList<>(selectorNodes.size());
        
        selectorNodes.forEach((selectorNode) -> {
            selectorNodeTransfers.add(selectorNodeTransferCache.getSelectorNodeTransfer(userVisit, selectorNode));
        });
        
        return selectorNodeTransfers;
    }
    
    private void updateSelectorNodeFromValue(SelectorNodeDetailValue selectorNodeDetailValue, boolean checkRoot, BasePK updatedBy) {
        if(selectorNodeDetailValue.hasBeenModified()) {
            var selectorNode = selectorNodeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     selectorNodeDetailValue.getSelectorNodePK());
            var selectorNodeDetail = selectorNode.getActiveDetailForUpdate();
            
            selectorNodeDetail.setThruTime(session.getStartTime());
            selectorNodeDetail.store();

            var selectorNodePK = selectorNodeDetail.getSelectorNodePK();
            var selector = selectorNodeDetail.getSelector();
            var selectorPK = selector.getPrimaryKey();
            var selectorNodeName = selectorNodeDetailValue.getSelectorNodeName();
            var selectorNodeTypePK = selectorNodeDetailValue.getSelectorNodeTypePK(); // Not updated
            var isRootSelectorNode = selectorNodeDetailValue.getIsRootSelectorNode();
            var negate = selectorNodeDetailValue.getNegate();
            
            if(checkRoot) {
                var rootSelectorNode = getRootSelectorNode(selector);
                var rootFound = rootSelectorNode != null && !rootSelectorNode.equals(selectorNode);
                
                if(isRootSelectorNode && rootFound) {
                    // If I'm the root, and a root already existed...
                    var rootSelectorNodeDetailValue = getRootSelectorNodeDetailValueForUpdate(selector);
                    
                    rootSelectorNodeDetailValue.setIsRootSelectorNode(false);
                    updateSelectorNodeFromValue(rootSelectorNodeDetailValue, false, updatedBy);
                } else if(!isRootSelectorNode && !rootFound) {
                    // If I'm not the root, and no other root exists...
                    isRootSelectorNode = true;
                }
            }
            
            selectorNodeDetail = selectorNodeDetailFactory.create(selectorNodePK, selector.getPrimaryKey(),
                    selectorNodeName, isRootSelectorNode, selectorNodeTypePK, negate, session.getStartTime(), Session.MAX_TIME);
            
            selectorNode.setActiveDetail(selectorNodeDetail);
            selectorNode.setLastDetail(selectorNodeDetail);
            
            sendEvent(selectorPK, EventTypes.MODIFY, selectorNodePK, EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void updateSelectorNodeFromValue(SelectorNodeDetailValue selectorNodeDetailValue, BasePK updatedBy) {
        updateSelectorNodeFromValue(selectorNodeDetailValue, true, updatedBy);
    }
    
    public void deleteSelectorNode(SelectorNode selectorNode, BasePK deletedBy) {
        var selectorNodeDetail = selectorNode.getLastDetailForUpdate();
        var selectorNodeTypeName = selectorNodeDetail.getSelectorNodeType().getSelectorNodeTypeName();
        
        deleteSelectorNodeDescriptionsBySelectorNode(selectorNode, deletedBy);
        
        if(selectorNodeTypeName.equals(com.echothree.model.control.selector.common.SelectorNodeTypes.BOOLEAN.name())) {
            deleteSelectorNodeBoolean(getSelectorNodeBooleanForUpdate(selectorNode), deletedBy);
        } else if(selectorNodeTypeName.equals(com.echothree.model.control.selector.common.SelectorNodeTypes.WORKFLOW_STEP.name())) {
            deleteSelectorNodeWorkflowStep(getSelectorNodeWorkflowStepForUpdate(selectorNode), deletedBy);
        } else if(selectorNodeTypeName.equals(com.echothree.model.control.selector.common.SelectorNodeTypes.ENTITY_LIST_ITEM.name())) {
            deleteSelectorNodeEntityListItem(getSelectorNodeEntityListItemForUpdate(selectorNode), deletedBy);
        } else if(selectorNodeTypeName.equals(com.echothree.model.control.selector.common.SelectorNodeTypes.RESPONSIBILITY_TYPE.name())) {
            deleteSelectorNodeResponsibilityType(getSelectorNodeResponsibilityTypeForUpdate(selectorNode), deletedBy);
        } else if(selectorNodeTypeName.equals(com.echothree.model.control.selector.common.SelectorNodeTypes.TRAINING_CLASS.name())) {
            deleteSelectorNodeTrainingClass(getSelectorNodeTrainingClassForUpdate(selectorNode), deletedBy);
        } else if(selectorNodeTypeName.equals(com.echothree.model.control.selector.common.SelectorNodeTypes.SKILL_TYPE.name())) {
            deleteSelectorNodeSkillType(getSelectorNodeSkillTypeForUpdate(selectorNode), deletedBy);
        } else if(selectorNodeTypeName.equals(com.echothree.model.control.selector.common.SelectorNodeTypes.ITEM_CATEGORY.name())) {
            deleteSelectorNodeItemCategory(getSelectorNodeItemCategoryForUpdate(selectorNode), deletedBy);
        } else if(selectorNodeTypeName.equals(com.echothree.model.control.selector.common.SelectorNodeTypes.ITEM_ACCOUNTING_CATEGORY.name())) {
            deleteSelectorNodeItemAccountingCategory(getSelectorNodeItemAccountingCategoryForUpdate(selectorNode), deletedBy);
        } else if(selectorNodeTypeName.equals(com.echothree.model.control.selector.common.SelectorNodeTypes.ITEM_PURCHASING_CATEGORY.name())) {
            deleteSelectorNodeItemPurchasingCategory(getSelectorNodeItemPurchasingCategoryForUpdate(selectorNode), deletedBy);
        } else if(selectorNodeTypeName.equals(com.echothree.model.control.selector.common.SelectorNodeTypes.GEO_CODE.name())) {
            deleteSelectorNodeGeoCode(getSelectorNodeGeoCodeForUpdate(selectorNode), deletedBy);
        } else if(selectorNodeTypeName.equals(com.echothree.model.control.selector.common.SelectorNodeTypes.PAYMENT_METHOD.name())) {
            deleteSelectorNodePaymentMethod(getSelectorNodePaymentMethodForUpdate(selectorNode), deletedBy);
        } else if(selectorNodeTypeName.equals(com.echothree.model.control.selector.common.SelectorNodeTypes.PAYMENT_PROCESSOR.name())) {
            deleteSelectorNodePaymentProcessor(getSelectorNodePaymentProcessorForUpdate(selectorNode), deletedBy);
        }
        
        selectorNodeDetail.setThruTime(session.getStartTime());
        selectorNode.setActiveDetail(null);
        selectorNode.store();
        
        // Check for root, and pick one if necessary
        // TODO: this should be smarter, looking for Boolean nodes and finding one that would make a
        // good root.
        var selector = selectorNodeDetail.getSelector();
        var rootSelectorNode = getRootSelectorNode(selector);
        if(rootSelectorNode == null) {
            var SelectorNodes = getSelectorNodesBySelectorForUpdate(selector);
            
            if(!SelectorNodes.isEmpty()) {
                var iter = SelectorNodes.iterator();
                if(iter.hasNext()) {
                    rootSelectorNode = iter.next();
                }
                var selectorNodeDetailValue = Objects.requireNonNull(rootSelectorNode).getLastDetailForUpdate().getSelectorNodeDetailValue().clone();
                
                selectorNodeDetailValue.setIsRootSelectorNode(true);
                updateSelectorNodeFromValue(selectorNodeDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(selectorNodeDetail.getSelector().getPrimaryKey(), EventTypes.MODIFY,
                selectorNode.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteSelectorNodesBySelector(Selector selector, BasePK deletedBy) {
        var selectorNodes = getSelectorNodesBySelectorForUpdate(selector);
        
        selectorNodes.forEach((selectorNode) -> 
                deleteSelectorNode(selectorNode, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Node Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected SelectorNodeDescriptionFactory selectorNodeDescriptionFactory;

    @Inject
    private SelectorNodeDescriptionTransferCache selectorNodeDescriptionTransferCache;

    public SelectorNodeDescription createSelectorNodeDescription(SelectorNode selectorNode, Language language, String description,
            BasePK createdBy) {
        var selectorNodeDescription = selectorNodeDescriptionFactory.create(selectorNode,
                language, description, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(selectorNode.getLastDetail().getSelectorPK(), EventTypes.MODIFY,
                selectorNodeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return selectorNodeDescription;
    }
    
    private SelectorNodeDescription getSelectorNodeDescription(SelectorNode selectorNode, Language language,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SelectorNodeDescriptions.fields())
                .from(SelectorNodeDescriptions)
                .where(SelectorNodeDescriptions.SELECTOR_NODE.eq(selectorNode.getPrimaryKey()),
                        SelectorNodeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        SelectorNodeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return selectorNodeDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public SelectorNodeDescription getSelectorNodeDescription(SelectorNode selectorNode, Language language) {
        return getSelectorNodeDescription(selectorNode, language, EntityPermission.READ_ONLY);
    }
    
    public SelectorNodeDescription getSelectorNodeDescriptionForUpdate(SelectorNode selectorNode, Language language) {
        return getSelectorNodeDescription(selectorNode, language, EntityPermission.READ_WRITE);
    }
    
    public SelectorNodeDescriptionValue getSelectorNodeDescriptionValue(SelectorNodeDescription selectorNodeDescription) {
        return selectorNodeDescription == null? null: selectorNodeDescription.getSelectorNodeDescriptionValue().clone();
    }
    
    public SelectorNodeDescriptionValue getSelectorNodeDescriptionValueForUpdate(SelectorNode selectorNode, Language language) {
        var selectorNodeDescription = getSelectorNodeDescriptionForUpdate(selectorNode, language);
        
        return selectorNodeDescription == null? null: selectorNodeDescription.getSelectorNodeDescriptionValue().clone();
    }
    
    private List<SelectorNodeDescription> getSelectorNodeDescriptionsBySelectorNode(SelectorNode selectorNode, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(SelectorNodeDescriptions.fields())
                    .from(SelectorNodeDescriptions)
                    .join(Languages).onKey(SELECTOR_NODE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(SelectorNodeDescriptions.SELECTOR_NODE.eq(selectorNode.getPrimaryKey()),
                            SelectorNodeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    SelectorNodeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(SelectorNodeDescriptions.fields())
                    .from(SelectorNodeDescriptions)
                    .where(SelectorNodeDescriptions.SELECTOR_NODE.eq(selectorNode.getPrimaryKey()),
                            SelectorNodeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return selectorNodeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<SelectorNodeDescription> getSelectorNodeDescriptionsBySelectorNode(SelectorNode selectorNode) {
        return getSelectorNodeDescriptionsBySelectorNode(selectorNode, EntityPermission.READ_ONLY);
    }
    
    public List<SelectorNodeDescription> getSelectorNodeDescriptionsBySelectorNodeForUpdate(SelectorNode selectorNode) {
        return getSelectorNodeDescriptionsBySelectorNode(selectorNode, EntityPermission.READ_WRITE);
    }
    
    public String getBestSelectorNodeDescription(SelectorNode selectorNode, Language language) {
        String description;
        var selectorNodeDescription = getSelectorNodeDescription(selectorNode, language);
        
        if(selectorNodeDescription == null && !language.getIsDefault()) {
            selectorNodeDescription = getSelectorNodeDescription(selectorNode, partyControl.getDefaultLanguage());
        }
        
        if(selectorNodeDescription == null) {
            description = selectorNode.getLastDetail().getSelectorNodeName();
        } else {
            description = selectorNodeDescription.getDescription();
        }
        
        return description;
    }
    
    public SelectorNodeDescriptionTransfer getSelectorNodeDescriptionTransfer(UserVisit userVisit, SelectorNodeDescription selectorNodeDescription) {
        return selectorNodeDescriptionTransferCache.getSelectorNodeDescriptionTransfer(userVisit, selectorNodeDescription);
    }
    
    public List<SelectorNodeDescriptionTransfer> getSelectorNodeDescriptionTransfers(UserVisit userVisit, SelectorNode selectorNode) {
        var selectorNodeDescriptions = getSelectorNodeDescriptionsBySelectorNode(selectorNode);
        List<SelectorNodeDescriptionTransfer> selectorNodeDescriptionTransfers = null;
        
        if(selectorNodeDescriptions != null) {
            selectorNodeDescriptionTransfers = new ArrayList<>(selectorNodeDescriptions.size());
            
            for(var selectorNodeDescription : selectorNodeDescriptions) {
                selectorNodeDescriptionTransfers.add(selectorNodeDescriptionTransferCache.getSelectorNodeDescriptionTransfer(userVisit, selectorNodeDescription));
            }
        }
        
        return selectorNodeDescriptionTransfers;
    }
    
    public void updateSelectorNodeDescriptionFromValue(SelectorNodeDescriptionValue selectorNodeDescriptionValue, BasePK updatedBy) {
        if(selectorNodeDescriptionValue.hasBeenModified()) {
            var selectorNodeDescription = selectorNodeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE, selectorNodeDescriptionValue.getPrimaryKey());
            
            selectorNodeDescription.setThruTime(session.getStartTime());
            selectorNodeDescription.store();

            var selectorNode = selectorNodeDescription.getSelectorNode();
            var language = selectorNodeDescription.getLanguage();
            var description = selectorNodeDescriptionValue.getDescription();
            
            selectorNodeDescription = selectorNodeDescriptionFactory.create(selectorNode, language, description, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(selectorNode.getLastDetail().getSelector().getPrimaryKey(), EventTypes.MODIFY, selectorNodeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteSelectorNodeDescription(SelectorNodeDescription selectorNodeDescription, BasePK deletedBy) {
        selectorNodeDescription.setThruTime(session.getStartTime());
        
        sendEvent(selectorNodeDescription.getSelectorNode().getLastDetail().getSelector().getPrimaryKey(), EventTypes.MODIFY, selectorNodeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteSelectorNodeDescriptionsBySelectorNode(SelectorNode selectorNode, BasePK deletedBy) {
        var selectorNodeDescriptions = getSelectorNodeDescriptionsBySelectorNodeForUpdate(selectorNode);
        
        selectorNodeDescriptions.forEach((selectorNodeDescription) -> 
                deleteSelectorNodeDescription(selectorNodeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Node Booleans
    // --------------------------------------------------------------------------------

    @Inject
    protected SelectorNodeBooleanFactory selectorNodeBooleanFactory;

    public SelectorNodeBoolean createSelectorNodeBoolean(SelectorNode selectorNode, SelectorBooleanType selectorBooleanType,
            SelectorNode leftSelectorNode, SelectorNode rightSelectorNode, BasePK createdBy) {
        var selectorNodeBoolean = selectorNodeBooleanFactory.create(selectorNode,
                selectorBooleanType, leftSelectorNode, rightSelectorNode, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(selectorNode.getLastDetail().getSelectorPK(), EventTypes.MODIFY,
                selectorNodeBoolean.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return selectorNodeBoolean;
    }

    public long countSelectorNodeBooleansBySelectorBooleanType(final SelectorBooleanType selectorBooleanType) {
        return session.getDslContext()
                .selectCount()
                .from(SelectorNodeBooleans)
                .where(SelectorNodeBooleans.SELECTOR_BOOLEAN_TYPE.eq(selectorBooleanType.getPrimaryKey()),
                        SelectorNodeBooleans.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countSelectorNodeBooleansByLeftSelectorNode(final SelectorNode leftSelectorNode) {
        return session.getDslContext()
                .selectCount()
                .from(SelectorNodeBooleans)
                .where(SelectorNodeBooleans.LEFT_SELECTOR_NODE.eq(leftSelectorNode.getPrimaryKey()),
                        SelectorNodeBooleans.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countSelectorNodeBooleansByRightSelectorNode(final SelectorNode rightSelectorNode) {
        return session.getDslContext()
                .selectCount()
                .from(SelectorNodeBooleans)
                .where(SelectorNodeBooleans.RIGHT_SELECTOR_NODE.eq(rightSelectorNode.getPrimaryKey()),
                        SelectorNodeBooleans.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private SelectorNodeBoolean getSelectorNodeBoolean(SelectorNode selectorNode, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SelectorNodeBooleans.fields())
                .from(SelectorNodeBooleans)
                .where(SelectorNodeBooleans.SELECTOR_NODE.eq(selectorNode.getPrimaryKey()),
                        SelectorNodeBooleans.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return selectorNodeBooleanFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public SelectorNodeBoolean getSelectorNodeBoolean(SelectorNode selectorNode) {
        return getSelectorNodeBoolean(selectorNode, EntityPermission.READ_ONLY);
    }
    
    public SelectorNodeBoolean getSelectorNodeBooleanForUpdate(SelectorNode selectorNode) {
        return getSelectorNodeBoolean(selectorNode, EntityPermission.READ_WRITE);
    }
    
    public SelectorNodeBooleanValue getSelectorNodeBooleanValue(SelectorNodeBoolean selectorNodeBoolean) {
        return selectorNodeBoolean == null? null: selectorNodeBoolean.getSelectorNodeBooleanValue().clone();
    }
    
    public SelectorNodeBooleanValue getSelectorNodeBooleanValueForUpdate(SelectorNode selectorNode) {
        var selectorNodeBoolean = getSelectorNodeBooleanForUpdate(selectorNode);
        
        return selectorNodeBoolean == null? null: selectorNodeBoolean.getSelectorNodeBooleanValue().clone();
    }
    
    private List<SelectorNodeBoolean> getSelectorNodeBooleansBySelector(Selector selector, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SelectorNodeBooleans.fields())
                .from(SelectorNodeBooleans)
                .join(SelectorNodes).onKey(SELECTOR_NODE_BOOLEANS_SELECTOR_NODE_FK)
                .join(SelectorNodeDetails).onKey(SELECTOR_NODES_ACTIVE_DETAIL_FK)
                .where(SelectorNodeDetails.SELECTOR.eq(selector.getPrimaryKey()),
                        SelectorNodeBooleans.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(SelectorNodeDetails.SELECTOR_NODE_NAME),
                    SelectorNodeBooleanFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return selectorNodeBooleanFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<SelectorNodeBoolean> getSelectorNodeBooleansBySelector(Selector selector) {
        return getSelectorNodeBooleansBySelector(selector, EntityPermission.READ_ONLY);
    }
    
    public List<SelectorNodeBoolean> getSelectorNodeBooleansBySelectorForUpdate(Selector selector) {
        return getSelectorNodeBooleansBySelector(selector, EntityPermission.READ_WRITE);
    }
    
    public void updateSelectorNodeBooleanFromValue(SelectorNodeBooleanValue selectorNodeBooleanValue, BasePK updatedBy) {
        if(selectorNodeBooleanValue.hasBeenModified()) {
            var selectorNodeBoolean = selectorNodeBooleanFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     selectorNodeBooleanValue.getPrimaryKey());
            
            selectorNodeBoolean.setThruTime(session.getStartTime());
            selectorNodeBoolean.store();

            var selectorNode = selectorNodeBoolean.getSelectorNode();
            var selectorBooleanTypePK = selectorNodeBooleanValue.getSelectorBooleanTypePK();
            var leftSelectorNodePK = selectorNodeBooleanValue.getLeftSelectorNodePK();
            var rightSelectorNodePK = selectorNodeBooleanValue.getRightSelectorNodePK();
            
            selectorNodeBoolean = selectorNodeBooleanFactory.create(selectorNode.getPrimaryKey(),
                    selectorBooleanTypePK, leftSelectorNodePK, rightSelectorNodePK, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(selectorNode.getLastDetail().getSelectorPK(), EventTypes.MODIFY,
                    selectorNodeBoolean.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteSelectorNodeBoolean(SelectorNodeBoolean selectorNodeBoolean, BasePK deletedBy) {
        selectorNodeBoolean.setThruTime(session.getStartTime());
        
        sendEvent(selectorNodeBoolean.getSelectorNode().getLastDetail().getSelectorPK(),
                EventTypes.MODIFY, selectorNodeBoolean.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Node Workflow Steps
    // --------------------------------------------------------------------------------

    @Inject
    protected SelectorNodeWorkflowStepFactory selectorNodeWorkflowStepFactory;

    public SelectorNodeWorkflowStep createSelectorNodeWorkflowStep(SelectorNode selectorNode, WorkflowStep workflowStep,
            BasePK createdBy) {
        var selectorNodeWorkflowStep = selectorNodeWorkflowStepFactory.create(
                selectorNode, workflowStep, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(selectorNode.getLastDetail().getSelectorPK(), EventTypes.MODIFY,
                selectorNodeWorkflowStep.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return selectorNodeWorkflowStep;
    }

    public long countCampaignsByWorkflowStep(final WorkflowStep workflowStep) {
        return session.getDslContext()
                .selectCount()
                .from(SelectorNodeWorkflowSteps)
                .where(SelectorNodeWorkflowSteps.WORKFLOW_STEP.eq(workflowStep.getPrimaryKey()),
                        SelectorNodeWorkflowSteps.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private SelectorNodeWorkflowStep getSelectorNodeWorkflowStep(SelectorNode selectorNode, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SelectorNodeWorkflowSteps.fields())
                .from(SelectorNodeWorkflowSteps)
                .where(SelectorNodeWorkflowSteps.SELECTOR_NODE.eq(selectorNode.getPrimaryKey()),
                        SelectorNodeWorkflowSteps.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return selectorNodeWorkflowStepFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public SelectorNodeWorkflowStep getSelectorNodeWorkflowStep(SelectorNode selectorNode) {
        return getSelectorNodeWorkflowStep(selectorNode, EntityPermission.READ_ONLY);
    }
    
    public SelectorNodeWorkflowStep getSelectorNodeWorkflowStepForUpdate(SelectorNode selectorNode) {
        return getSelectorNodeWorkflowStep(selectorNode, EntityPermission.READ_WRITE);
    }
    
    public SelectorNodeWorkflowStepValue getSelectorNodeWorkflowStepValue(SelectorNodeWorkflowStep selectorNodeWorkflowStep) {
        return selectorNodeWorkflowStep == null? null: selectorNodeWorkflowStep.getSelectorNodeWorkflowStepValue().clone();
    }
    
    public SelectorNodeWorkflowStepValue getSelectorNodeWorkflowStepValueForUpdate(SelectorNode selectorNode) {
        var selectorNodeWorkflowStep = getSelectorNodeWorkflowStepForUpdate(selectorNode);
        
        return selectorNodeWorkflowStep == null? null: selectorNodeWorkflowStep.getSelectorNodeWorkflowStepValue().clone();
    }
    
    private List<SelectorNodeWorkflowStep> getSelectorNodeWorkflowStepsBySelector(Selector selector, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SelectorNodeWorkflowSteps.fields())
                .from(SelectorNodeWorkflowSteps)
                .join(SelectorNodes).onKey(SELECTOR_NODE_WORKFLOW_STEPS_SELECTOR_NODE_FK)
                .join(SelectorNodeDetails).onKey(SELECTOR_NODES_ACTIVE_DETAIL_FK)
                .where(SelectorNodeDetails.SELECTOR.eq(selector.getPrimaryKey()),
                        SelectorNodeWorkflowSteps.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(SelectorNodeDetails.SELECTOR_NODE_NAME),
                    SelectorNodeWorkflowStepFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return selectorNodeWorkflowStepFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<SelectorNodeWorkflowStep> getSelectorNodeWorkflowStepsBySelector(Selector selector) {
        return getSelectorNodeWorkflowStepsBySelector(selector, EntityPermission.READ_ONLY);
    }
    
    public List<SelectorNodeWorkflowStep> getSelectorNodeWorkflowStepsBySelectorForUpdate(Selector selector) {
        return getSelectorNodeWorkflowStepsBySelector(selector, EntityPermission.READ_WRITE);
    }
    
    public void updateSelectorNodeWorkflowStepFromValue(SelectorNodeWorkflowStepValue selectorNodeWorkflowStepValue, BasePK updatedBy) {
        if(selectorNodeWorkflowStepValue.hasBeenModified()) {
            var selectorNodeWorkflowStep = selectorNodeWorkflowStepFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     selectorNodeWorkflowStepValue.getPrimaryKey());
            
            selectorNodeWorkflowStep.setThruTime(session.getStartTime());
            selectorNodeWorkflowStep.store();

            var selectorNode = selectorNodeWorkflowStep.getSelectorNode();
            var workflowStepPK = selectorNodeWorkflowStepValue.getWorkflowStepPK();
            
            selectorNodeWorkflowStep = selectorNodeWorkflowStepFactory.create(selectorNode.getPrimaryKey(),
                    workflowStepPK, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(selectorNode.getLastDetail().getSelectorPK(), EventTypes.MODIFY,
                    selectorNodeWorkflowStep.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteSelectorNodeWorkflowStep(SelectorNodeWorkflowStep selectorNodeWorkflowStep, BasePK deletedBy) {
        selectorNodeWorkflowStep.setThruTime(session.getStartTime());
        
        sendEvent(selectorNodeWorkflowStep.getSelectorNode().getLastDetail().getSelectorPK(),
                EventTypes.MODIFY, selectorNodeWorkflowStep.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Node Entity List Items
    // --------------------------------------------------------------------------------

    @Inject
    protected SelectorNodeEntityListItemFactory selectorNodeEntityListItemFactory;

    public SelectorNodeEntityListItem createSelectorNodeEntityListItem(SelectorNode selectorNode, EntityListItem entityListItem,
            BasePK createdBy) {
        var selectorNodeEntityListItem = selectorNodeEntityListItemFactory.create(
                selectorNode, entityListItem, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(selectorNode.getLastDetail().getSelectorPK(), EventTypes.MODIFY,
                selectorNodeEntityListItem.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return selectorNodeEntityListItem;
    }

    public long countSelectorNodeEntityListItemsBy(final EntityListItem entityListItem) {
        return session.getDslContext()
                .selectCount()
                .from(SelectorNodeEntityListItems)
                .where(SelectorNodeEntityListItems.ENTITY_LIST_ITEM.eq(entityListItem.getPrimaryKey()),
                        SelectorNodeEntityListItems.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private SelectorNodeEntityListItem getSelectorNodeEntityListItem(SelectorNode selectorNode, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SelectorNodeEntityListItems.fields())
                .from(SelectorNodeEntityListItems)
                .where(SelectorNodeEntityListItems.SELECTOR_NODE.eq(selectorNode.getPrimaryKey()),
                        SelectorNodeEntityListItems.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return selectorNodeEntityListItemFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public SelectorNodeEntityListItem getSelectorNodeEntityListItem(SelectorNode selectorNode) {
        return getSelectorNodeEntityListItem(selectorNode, EntityPermission.READ_ONLY);
    }
    
    public SelectorNodeEntityListItem getSelectorNodeEntityListItemForUpdate(SelectorNode selectorNode) {
        return getSelectorNodeEntityListItem(selectorNode, EntityPermission.READ_WRITE);
    }
    
    public SelectorNodeEntityListItemValue getSelectorNodeEntityListItemValue(SelectorNodeEntityListItem selectorNodeEntityListItem) {
        return selectorNodeEntityListItem == null? null: selectorNodeEntityListItem.getSelectorNodeEntityListItemValue().clone();
    }
    
    public SelectorNodeEntityListItemValue getSelectorNodeEntityListItemValueForUpdate(SelectorNode selectorNode) {
        var selectorNodeEntityListItem = getSelectorNodeEntityListItemForUpdate(selectorNode);
        
        return selectorNodeEntityListItem == null? null: selectorNodeEntityListItem.getSelectorNodeEntityListItemValue().clone();
    }
    
    private List<SelectorNodeEntityListItem> getSelectorNodeEntityListItemsBySelector(Selector selector, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SelectorNodeEntityListItems.fields())
                .from(SelectorNodeEntityListItems)
                .join(SelectorNodes).onKey(SELECTOR_NODE_ENTITY_LIST_ITEMS_SELECTOR_NODE_FK)
                .join(SelectorNodeDetails).onKey(SELECTOR_NODES_ACTIVE_DETAIL_FK)
                .where(SelectorNodeDetails.SELECTOR.eq(selector.getPrimaryKey()),
                        SelectorNodeEntityListItems.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(SelectorNodeDetails.SELECTOR_NODE_NAME),
                    SelectorNodeEntityListItemFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return selectorNodeEntityListItemFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<SelectorNodeEntityListItem> getSelectorNodeEntityListItemsBySelector(Selector selector) {
        return getSelectorNodeEntityListItemsBySelector(selector, EntityPermission.READ_ONLY);
    }
    
    public List<SelectorNodeEntityListItem> getSelectorNodeEntityListItemsBySelectorForUpdate(Selector selector) {
        return getSelectorNodeEntityListItemsBySelector(selector, EntityPermission.READ_WRITE);
    }
    
    public void updateSelectorNodeEntityListItemFromValue(SelectorNodeEntityListItemValue selectorNodeEntityListItemValue, BasePK updatedBy) {
        if(selectorNodeEntityListItemValue.hasBeenModified()) {
            var selectorNodeEntityListItem = selectorNodeEntityListItemFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     selectorNodeEntityListItemValue.getPrimaryKey());
            
            selectorNodeEntityListItem.setThruTime(session.getStartTime());
            selectorNodeEntityListItem.store();

            var selectorNode = selectorNodeEntityListItem.getSelectorNode();
            var entityListItemPK = selectorNodeEntityListItemValue.getEntityListItemPK();
            
            selectorNodeEntityListItem = selectorNodeEntityListItemFactory.create(selectorNode.getPrimaryKey(),
                    entityListItemPK, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(selectorNode.getLastDetail().getSelectorPK(), EventTypes.MODIFY,
                    selectorNodeEntityListItem.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteSelectorNodeEntityListItem(SelectorNodeEntityListItem selectorNodeEntityListItem, BasePK deletedBy) {
        selectorNodeEntityListItem.setThruTime(session.getStartTime());
        
        sendEvent(selectorNodeEntityListItem.getSelectorNode().getLastDetail().getSelectorPK(),
                EventTypes.MODIFY, selectorNodeEntityListItem.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Node Responsibility Types
    // --------------------------------------------------------------------------------

    @Inject
    protected SelectorNodeResponsibilityTypeFactory selectorNodeResponsibilityTypeFactory;

    public SelectorNodeResponsibilityType createSelectorNodeResponsibilityType(SelectorNode selectorNode, ResponsibilityType responsibilityType,
            BasePK createdBy) {
        var selectorNodeResponsibilityType = selectorNodeResponsibilityTypeFactory.create(
                selectorNode, responsibilityType, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(selectorNode.getLastDetail().getSelectorPK(), EventTypes.MODIFY,
                selectorNodeResponsibilityType.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return selectorNodeResponsibilityType;
    }

    public long countSelectorNodeResponsibilityTypesByResponsibilityType(final ResponsibilityType responsibilityType) {
        return session.getDslContext()
                .selectCount()
                .from(SelectorNodeResponsibilityTypes)
                .where(SelectorNodeResponsibilityTypes.RESPONSIBILITY_TYPE.eq(responsibilityType.getPrimaryKey()),
                        SelectorNodeResponsibilityTypes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private SelectorNodeResponsibilityType getSelectorNodeResponsibilityType(SelectorNode selectorNode, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SelectorNodeResponsibilityTypes.fields())
                .from(SelectorNodeResponsibilityTypes)
                .where(SelectorNodeResponsibilityTypes.SELECTOR_NODE.eq(selectorNode.getPrimaryKey()),
                        SelectorNodeResponsibilityTypes.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return selectorNodeResponsibilityTypeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public SelectorNodeResponsibilityType getSelectorNodeResponsibilityType(SelectorNode selectorNode) {
        return getSelectorNodeResponsibilityType(selectorNode, EntityPermission.READ_ONLY);
    }
    
    public SelectorNodeResponsibilityType getSelectorNodeResponsibilityTypeForUpdate(SelectorNode selectorNode) {
        return getSelectorNodeResponsibilityType(selectorNode, EntityPermission.READ_WRITE);
    }
    
    public SelectorNodeResponsibilityTypeValue getSelectorNodeResponsibilityTypeValue(SelectorNodeResponsibilityType selectorNodeResponsibilityType) {
        return selectorNodeResponsibilityType == null? null: selectorNodeResponsibilityType.getSelectorNodeResponsibilityTypeValue().clone();
    }
    
    public SelectorNodeResponsibilityTypeValue getSelectorNodeResponsibilityTypeValueForUpdate(SelectorNode selectorNode) {
        var selectorNodeResponsibilityType = getSelectorNodeResponsibilityTypeForUpdate(selectorNode);
        
        return selectorNodeResponsibilityType == null? null: selectorNodeResponsibilityType.getSelectorNodeResponsibilityTypeValue().clone();
    }
    
    private List<SelectorNodeResponsibilityType> getSelectorNodeResponsibilityTypesBySelector(Selector selector, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SelectorNodeResponsibilityTypes.fields())
                .from(SelectorNodeResponsibilityTypes)
                .join(SelectorNodes).onKey(SELECTOR_NODE_RESPONSIBILITY_TYPES_SELECTOR_NODE_FK)
                .join(SelectorNodeDetails).onKey(SELECTOR_NODES_ACTIVE_DETAIL_FK)
                .where(SelectorNodeDetails.SELECTOR.eq(selector.getPrimaryKey()),
                        SelectorNodeResponsibilityTypes.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(SelectorNodeDetails.SELECTOR_NODE_NAME),
                    SelectorNodeResponsibilityTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return selectorNodeResponsibilityTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<SelectorNodeResponsibilityType> getSelectorNodeResponsibilityTypesBySelector(Selector selector) {
        return getSelectorNodeResponsibilityTypesBySelector(selector, EntityPermission.READ_ONLY);
    }
    
    public List<SelectorNodeResponsibilityType> getSelectorNodeResponsibilityTypesBySelectorForUpdate(Selector selector) {
        return getSelectorNodeResponsibilityTypesBySelector(selector, EntityPermission.READ_WRITE);
    }
    
    public void updateSelectorNodeResponsibilityTypeFromValue(SelectorNodeResponsibilityTypeValue selectorNodeResponsibilityTypeValue, BasePK updatedBy) {
        if(selectorNodeResponsibilityTypeValue.hasBeenModified()) {
            var selectorNodeResponsibilityType = selectorNodeResponsibilityTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     selectorNodeResponsibilityTypeValue.getPrimaryKey());
            
            selectorNodeResponsibilityType.setThruTime(session.getStartTime());
            selectorNodeResponsibilityType.store();

            var selectorNode = selectorNodeResponsibilityType.getSelectorNode();
            var responsibilityTypePK = selectorNodeResponsibilityTypeValue.getResponsibilityTypePK();
            
            selectorNodeResponsibilityType = selectorNodeResponsibilityTypeFactory.create(selectorNode.getPrimaryKey(),
                    responsibilityTypePK, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(selectorNode.getLastDetail().getSelectorPK(), EventTypes.MODIFY,
                    selectorNodeResponsibilityType.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteSelectorNodeResponsibilityType(SelectorNodeResponsibilityType selectorNodeResponsibilityType, BasePK deletedBy) {
        selectorNodeResponsibilityType.setThruTime(session.getStartTime());
        
        sendEvent(selectorNodeResponsibilityType.getSelectorNode().getLastDetail().getSelectorPK(),
                EventTypes.MODIFY, selectorNodeResponsibilityType.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Node Training Classes
    // --------------------------------------------------------------------------------

    @Inject
    protected SelectorNodeTrainingClassFactory selectorNodeTrainingClassFactory;

    public SelectorNodeTrainingClass createSelectorNodeTrainingClass(SelectorNode selectorNode, TrainingClass trainingClass,
            BasePK createdBy) {
        var selectorNodeTrainingClass = selectorNodeTrainingClassFactory.create(
                selectorNode, trainingClass, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(selectorNode.getLastDetail().getSelectorPK(), EventTypes.MODIFY,
                selectorNodeTrainingClass.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return selectorNodeTrainingClass;
    }

    public long countSelectorNodeTrainingClassesByTrainingClass(final TrainingClass trainingClass) {
        return session.getDslContext()
                .selectCount()
                .from(SelectorNodeTrainingClasses)
                .where(SelectorNodeTrainingClasses.TRAINING_CLASS.eq(trainingClass.getPrimaryKey()),
                        SelectorNodeTrainingClasses.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private SelectorNodeTrainingClass getSelectorNodeTrainingClass(SelectorNode selectorNode, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SelectorNodeTrainingClasses.fields())
                .from(SelectorNodeTrainingClasses)
                .where(SelectorNodeTrainingClasses.SELECTOR_NODE.eq(selectorNode.getPrimaryKey()),
                        SelectorNodeTrainingClasses.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return selectorNodeTrainingClassFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public SelectorNodeTrainingClass getSelectorNodeTrainingClass(SelectorNode selectorNode) {
        return getSelectorNodeTrainingClass(selectorNode, EntityPermission.READ_ONLY);
    }
    
    public SelectorNodeTrainingClass getSelectorNodeTrainingClassForUpdate(SelectorNode selectorNode) {
        return getSelectorNodeTrainingClass(selectorNode, EntityPermission.READ_WRITE);
    }
    
    public SelectorNodeTrainingClassValue getSelectorNodeTrainingClassValue(SelectorNodeTrainingClass selectorNodeTrainingClass) {
        return selectorNodeTrainingClass == null? null: selectorNodeTrainingClass.getSelectorNodeTrainingClassValue().clone();
    }
    
    public SelectorNodeTrainingClassValue getSelectorNodeTrainingClassValueForUpdate(SelectorNode selectorNode) {
        var selectorNodeTrainingClass = getSelectorNodeTrainingClassForUpdate(selectorNode);
        
        return selectorNodeTrainingClass == null? null: selectorNodeTrainingClass.getSelectorNodeTrainingClassValue().clone();
    }
    
    private List<SelectorNodeTrainingClass> getSelectorNodeTrainingClassesBySelector(Selector selector, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SelectorNodeTrainingClasses.fields())
                .from(SelectorNodeTrainingClasses)
                .join(SelectorNodes).onKey(SELECTOR_NODE_TRAINING_CLASSES_SELECTOR_NODE_FK)
                .join(SelectorNodeDetails).onKey(SELECTOR_NODES_ACTIVE_DETAIL_FK)
                .where(SelectorNodeDetails.SELECTOR.eq(selector.getPrimaryKey()),
                        SelectorNodeTrainingClasses.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(SelectorNodeDetails.SELECTOR_NODE_NAME),
                    SelectorNodeTrainingClassFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return selectorNodeTrainingClassFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<SelectorNodeTrainingClass> getSelectorNodeTrainingClassesBySelector(Selector selector) {
        return getSelectorNodeTrainingClassesBySelector(selector, EntityPermission.READ_ONLY);
    }
    
    public List<SelectorNodeTrainingClass> getSelectorNodeTrainingClassesBySelectorForUpdate(Selector selector) {
        return getSelectorNodeTrainingClassesBySelector(selector, EntityPermission.READ_WRITE);
    }
    
    public void updateSelectorNodeTrainingClassFromValue(SelectorNodeTrainingClassValue selectorNodeTrainingClassValue, BasePK updatedBy) {
        if(selectorNodeTrainingClassValue.hasBeenModified()) {
            var selectorNodeTrainingClass = selectorNodeTrainingClassFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     selectorNodeTrainingClassValue.getPrimaryKey());
            
            selectorNodeTrainingClass.setThruTime(session.getStartTime());
            selectorNodeTrainingClass.store();

            var selectorNode = selectorNodeTrainingClass.getSelectorNode();
            var trainingClassPK = selectorNodeTrainingClassValue.getTrainingClassPK();
            
            selectorNodeTrainingClass = selectorNodeTrainingClassFactory.create(selectorNode.getPrimaryKey(),
                    trainingClassPK, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(selectorNode.getLastDetail().getSelectorPK(), EventTypes.MODIFY,
                    selectorNodeTrainingClass.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteSelectorNodeTrainingClass(SelectorNodeTrainingClass selectorNodeTrainingClass, BasePK deletedBy) {
        selectorNodeTrainingClass.setThruTime(session.getStartTime());
        
        sendEvent(selectorNodeTrainingClass.getSelectorNode().getLastDetail().getSelectorPK(),
                EventTypes.MODIFY, selectorNodeTrainingClass.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Node Skill Types
    // --------------------------------------------------------------------------------

    @Inject
    protected SelectorNodeSkillTypeFactory selectorNodeSkillTypeFactory;

    public SelectorNodeSkillType createSelectorNodeSkillType(SelectorNode selectorNode, SkillType skillType,
            BasePK createdBy) {
        var selectorNodeSkillType = selectorNodeSkillTypeFactory.create(
                selectorNode, skillType, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(selectorNode.getLastDetail().getSelectorPK(), EventTypes.MODIFY,
                selectorNodeSkillType.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return selectorNodeSkillType;
    }

    public long countSelectorNodeSkillTypesBySkillType(final SkillType skillType) {
        return session.getDslContext()
                .selectCount()
                .from(SelectorNodeSkillTypes)
                .where(SelectorNodeSkillTypes.SKILL_TYPE.eq(skillType.getPrimaryKey()),
                        SelectorNodeSkillTypes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private SelectorNodeSkillType getSelectorNodeSkillType(SelectorNode selectorNode, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SelectorNodeSkillTypes.fields())
                .from(SelectorNodeSkillTypes)
                .where(SelectorNodeSkillTypes.SELECTOR_NODE.eq(selectorNode.getPrimaryKey()),
                        SelectorNodeSkillTypes.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return selectorNodeSkillTypeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public SelectorNodeSkillType getSelectorNodeSkillType(SelectorNode selectorNode) {
        return getSelectorNodeSkillType(selectorNode, EntityPermission.READ_ONLY);
    }
    
    public SelectorNodeSkillType getSelectorNodeSkillTypeForUpdate(SelectorNode selectorNode) {
        return getSelectorNodeSkillType(selectorNode, EntityPermission.READ_WRITE);
    }
    
    public SelectorNodeSkillTypeValue getSelectorNodeSkillTypeValue(SelectorNodeSkillType selectorNodeSkillType) {
        return selectorNodeSkillType == null? null: selectorNodeSkillType.getSelectorNodeSkillTypeValue().clone();
    }
    
    public SelectorNodeSkillTypeValue getSelectorNodeSkillTypeValueForUpdate(SelectorNode selectorNode) {
        var selectorNodeSkillType = getSelectorNodeSkillTypeForUpdate(selectorNode);
        
        return selectorNodeSkillType == null? null: selectorNodeSkillType.getSelectorNodeSkillTypeValue().clone();
    }
    
    private List<SelectorNodeSkillType> getSelectorNodeSkillTypesBySelector(Selector selector, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SelectorNodeSkillTypes.fields())
                .from(SelectorNodeSkillTypes)
                .join(SelectorNodes).onKey(SELECTOR_NODE_SKILL_TYPES_SELECTOR_NODE_FK)
                .join(SelectorNodeDetails).onKey(SELECTOR_NODES_ACTIVE_DETAIL_FK)
                .where(SelectorNodeDetails.SELECTOR.eq(selector.getPrimaryKey()),
                        SelectorNodeSkillTypes.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(SelectorNodeDetails.SELECTOR_NODE_NAME),
                    SelectorNodeSkillTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return selectorNodeSkillTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<SelectorNodeSkillType> getSelectorNodeSkillTypesBySelector(Selector selector) {
        return getSelectorNodeSkillTypesBySelector(selector, EntityPermission.READ_ONLY);
    }
    
    public List<SelectorNodeSkillType> getSelectorNodeSkillTypesBySelectorForUpdate(Selector selector) {
        return getSelectorNodeSkillTypesBySelector(selector, EntityPermission.READ_WRITE);
    }
    
    public void updateSelectorNodeSkillTypeFromValue(SelectorNodeSkillTypeValue selectorNodeSkillTypeValue, BasePK updatedBy) {
        if(selectorNodeSkillTypeValue.hasBeenModified()) {
            var selectorNodeSkillType = selectorNodeSkillTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     selectorNodeSkillTypeValue.getPrimaryKey());
            
            selectorNodeSkillType.setThruTime(session.getStartTime());
            selectorNodeSkillType.store();

            var selectorNode = selectorNodeSkillType.getSelectorNode();
            var skillTypePK = selectorNodeSkillTypeValue.getSkillTypePK();
            
            selectorNodeSkillType = selectorNodeSkillTypeFactory.create(selectorNode.getPrimaryKey(),
                    skillTypePK, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(selectorNode.getLastDetail().getSelectorPK(), EventTypes.MODIFY,
                    selectorNodeSkillType.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteSelectorNodeSkillType(SelectorNodeSkillType selectorNodeSkillType, BasePK deletedBy) {
        selectorNodeSkillType.setThruTime(session.getStartTime());
        
        sendEvent(selectorNodeSkillType.getSelectorNode().getLastDetail().getSelectorPK(),
                EventTypes.MODIFY, selectorNodeSkillType.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Node Item Categories
    // --------------------------------------------------------------------------------

    @Inject
    protected SelectorNodeItemCategoryFactory selectorNodeItemCategoryFactory;

    public SelectorNodeItemCategory createSelectorNodeItemCategory(SelectorNode selectorNode, ItemCategory itemCategory,
            Boolean checkParents, BasePK createdBy) {
        var selectorNodeItemCategory = selectorNodeItemCategoryFactory.create(
                selectorNode, itemCategory, checkParents, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(selectorNode.getLastDetail().getSelectorPK(), EventTypes.MODIFY,
                selectorNodeItemCategory.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return selectorNodeItemCategory;
    }

    public long countSelectorNodeItemCategoriesByItemCategory(final ItemCategory itemCategory) {
        return session.getDslContext()
                .selectCount()
                .from(SelectorNodeItemCategories)
                .where(SelectorNodeItemCategories.ITEM_CATEGORY.eq(itemCategory.getPrimaryKey()),
                        SelectorNodeItemCategories.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private SelectorNodeItemCategory getSelectorNodeItemCategory(SelectorNode selectorNode, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SelectorNodeItemCategories.fields())
                .from(SelectorNodeItemCategories)
                .where(SelectorNodeItemCategories.SELECTOR_NODE.eq(selectorNode.getPrimaryKey()),
                        SelectorNodeItemCategories.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return selectorNodeItemCategoryFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public SelectorNodeItemCategory getSelectorNodeItemCategory(SelectorNode selectorNode) {
        return getSelectorNodeItemCategory(selectorNode,EntityPermission.READ_ONLY);
    }
    
    public SelectorNodeItemCategory getSelectorNodeItemCategoryForUpdate(SelectorNode selectorNode) {
        return getSelectorNodeItemCategory(selectorNode,EntityPermission.READ_WRITE);
    }
    
    public SelectorNodeItemCategoryValue getSelectorNodeItemCategoryValue(SelectorNodeItemCategory selectorNodeItemCategory) {
        return selectorNodeItemCategory == null? null: selectorNodeItemCategory.getSelectorNodeItemCategoryValue().clone();
    }
    
    public SelectorNodeItemCategoryValue getSelectorNodeItemCategoryValueForUpdate(SelectorNode selectorNode) {
        var selectorNodeItemCategory = getSelectorNodeItemCategoryForUpdate(selectorNode);
        
        return selectorNodeItemCategory == null? null: selectorNodeItemCategory.getSelectorNodeItemCategoryValue().clone();
    }
    
    private List<SelectorNodeItemCategory> getSelectorNodeItemCategoriesBySelector(Selector selector, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SelectorNodeItemCategories.fields())
                .from(SelectorNodeItemCategories)
                .join(SelectorNodes).onKey(SELECTOR_NODE_ITEM_CATEGORIES_SELECTOR_NODE_FK)
                .join(SelectorNodeDetails).onKey(SELECTOR_NODES_ACTIVE_DETAIL_FK)
                .where(SelectorNodeDetails.SELECTOR.eq(selector.getPrimaryKey()),
                        SelectorNodeItemCategories.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(SelectorNodeDetails.SELECTOR_NODE_NAME),
                    SelectorNodeItemCategoryFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return selectorNodeItemCategoryFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<SelectorNodeItemCategory> getSelectorNodeItemCategoriesBySelector(Selector selector) {
        return getSelectorNodeItemCategoriesBySelector(selector,EntityPermission.READ_ONLY);
    }
    
    public List<SelectorNodeItemCategory> getSelectorNodeItemCategoriesBySelectorForUpdate(Selector selector) {
        return getSelectorNodeItemCategoriesBySelector(selector,EntityPermission.READ_WRITE);
    }
    
    public void updateSelectorNodeItemCategoryFromValue(SelectorNodeItemCategoryValue selectorNodeItemCategoryValue, BasePK updatedBy) {
        if(selectorNodeItemCategoryValue.hasBeenModified()) {
            var selectorNodeItemCategory = selectorNodeItemCategoryFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     selectorNodeItemCategoryValue.getPrimaryKey());
            
            selectorNodeItemCategory.setThruTime(session.getStartTime());
            selectorNodeItemCategory.store();

            var selectorNode = selectorNodeItemCategory.getSelectorNode();
            var itemCategoryPK = selectorNodeItemCategoryValue.getItemCategoryPK();
            var checkParents = selectorNodeItemCategoryValue.getCheckParents();
            
            selectorNodeItemCategory = selectorNodeItemCategoryFactory.create(selectorNode.getPrimaryKey(),
                    itemCategoryPK, checkParents, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(selectorNode.getLastDetail().getSelectorPK(), EventTypes.MODIFY,
                    selectorNodeItemCategory.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteSelectorNodeItemCategory(SelectorNodeItemCategory selectorNodeItemCategory, BasePK deletedBy) {
        selectorNodeItemCategory.setThruTime(session.getStartTime());
        
        sendEvent(selectorNodeItemCategory.getSelectorNode().getLastDetail().getSelectorPK(),
                EventTypes.MODIFY, selectorNodeItemCategory.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Node Item Accounting Categories
    // --------------------------------------------------------------------------------

    @Inject
    protected SelectorNodeItemAccountingCategoryFactory selectorNodeItemAccountingCategoryFactory;

    public SelectorNodeItemAccountingCategory createSelectorNodeItemAccountingCategory(SelectorNode selectorNode,
            ItemAccountingCategory itemAccountingCategory, Boolean checkParents, BasePK createdBy) {
        var selectorNodeItemAccountingCategory = selectorNodeItemAccountingCategoryFactory.create(
                selectorNode, itemAccountingCategory, checkParents, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(selectorNode.getLastDetail().getSelectorPK(), EventTypes.MODIFY,
                selectorNodeItemAccountingCategory.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return selectorNodeItemAccountingCategory;
    }

    public long countSelectorNodeItemAccountingCategoriesByItemAccountingCategory(final ItemAccountingCategory itemAccountingCategory) {
        return session.getDslContext()
                .selectCount()
                .from(SelectorNodeItemAccountingCategories)
                .where(SelectorNodeItemAccountingCategories.ITEM_ACCOUNTING_CATEGORY.eq(itemAccountingCategory.getPrimaryKey()),
                        SelectorNodeItemAccountingCategories.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private SelectorNodeItemAccountingCategory getSelectorNodeItemAccountingCategory(SelectorNode selectorNode, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SelectorNodeItemAccountingCategories.fields())
                .from(SelectorNodeItemAccountingCategories)
                .where(SelectorNodeItemAccountingCategories.SELECTOR_NODE.eq(selectorNode.getPrimaryKey()),
                        SelectorNodeItemAccountingCategories.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return selectorNodeItemAccountingCategoryFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public SelectorNodeItemAccountingCategory getSelectorNodeItemAccountingCategory(SelectorNode selectorNode) {
        return getSelectorNodeItemAccountingCategory(selectorNode,EntityPermission.READ_ONLY);
    }
    
    public SelectorNodeItemAccountingCategory getSelectorNodeItemAccountingCategoryForUpdate(SelectorNode selectorNode) {
        return getSelectorNodeItemAccountingCategory(selectorNode,EntityPermission.READ_WRITE);
    }
    
    public SelectorNodeItemAccountingCategoryValue getSelectorNodeItemAccountingCategoryValue(SelectorNodeItemAccountingCategory selectorNodeItemAccountingCategory) {
        return selectorNodeItemAccountingCategory == null? null: selectorNodeItemAccountingCategory.getSelectorNodeItemAccountingCategoryValue().clone();
    }
    
    public SelectorNodeItemAccountingCategoryValue getSelectorNodeItemAccountingCategoryValueForUpdate(SelectorNode selectorNode) {
        var selectorNodeItemAccountingCategory = getSelectorNodeItemAccountingCategoryForUpdate(selectorNode);
        
        return selectorNodeItemAccountingCategory == null? null: selectorNodeItemAccountingCategory.getSelectorNodeItemAccountingCategoryValue().clone();
    }
    
    private List<SelectorNodeItemAccountingCategory> getSelectorNodeItemAccountingCategoriesBySelector(Selector selector, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SelectorNodeItemAccountingCategories.fields())
                .from(SelectorNodeItemAccountingCategories)
                .join(SelectorNodes).onKey(SELECTOR_NODE_ITEM_ACCOUNTING_CATEGORIES_SELECTOR_NODE_FK)
                .join(SelectorNodeDetails).onKey(SELECTOR_NODES_ACTIVE_DETAIL_FK)
                .where(SelectorNodeDetails.SELECTOR.eq(selector.getPrimaryKey()),
                        SelectorNodeItemAccountingCategories.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(SelectorNodeDetails.SELECTOR_NODE_NAME),
                    SelectorNodeItemAccountingCategoryFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return selectorNodeItemAccountingCategoryFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<SelectorNodeItemAccountingCategory> getSelectorNodeItemAccountingCategoriesBySelector(Selector selector) {
        return getSelectorNodeItemAccountingCategoriesBySelector(selector,EntityPermission.READ_ONLY);
    }
    
    public List<SelectorNodeItemAccountingCategory> getSelectorNodeItemAccountingCategoriesBySelectorForUpdate(Selector selector) {
        return getSelectorNodeItemAccountingCategoriesBySelector(selector,EntityPermission.READ_WRITE);
    }
    
    public void updateSelectorNodeItemAccountingCategoryFromValue(SelectorNodeItemAccountingCategoryValue selectorNodeItemAccountingCategoryValue,
            BasePK updatedBy) {
        if(selectorNodeItemAccountingCategoryValue.hasBeenModified()) {
            var selectorNodeItemAccountingCategory = selectorNodeItemAccountingCategoryFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     selectorNodeItemAccountingCategoryValue.getPrimaryKey());
            
            selectorNodeItemAccountingCategory.setThruTime(session.getStartTime());
            selectorNodeItemAccountingCategory.store();

            var selectorNode = selectorNodeItemAccountingCategory.getSelectorNode();
            var itemAccountingCategoryPK = selectorNodeItemAccountingCategoryValue.getItemAccountingCategoryPK();
            var checkParents = selectorNodeItemAccountingCategoryValue.getCheckParents();
            
            selectorNodeItemAccountingCategory = selectorNodeItemAccountingCategoryFactory.create(selectorNode.getPrimaryKey(),
                    itemAccountingCategoryPK, checkParents, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(selectorNode.getLastDetail().getSelectorPK(), EventTypes.MODIFY,
                    selectorNodeItemAccountingCategory.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteSelectorNodeItemAccountingCategory(SelectorNodeItemAccountingCategory selectorNodeItemAccountingCategory,
            BasePK deletedBy) {
        selectorNodeItemAccountingCategory.setThruTime(session.getStartTime());
        
        sendEvent(selectorNodeItemAccountingCategory.getSelectorNode().getLastDetail().getSelectorPK(),
                EventTypes.MODIFY, selectorNodeItemAccountingCategory.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Node Item Purchasing Categories
    // --------------------------------------------------------------------------------

    @Inject
    protected SelectorNodeItemPurchasingCategoryFactory selectorNodeItemPurchasingCategoryFactory;

    public SelectorNodeItemPurchasingCategory createSelectorNodeItemPurchasingCategory(SelectorNode selectorNode,
            ItemPurchasingCategory itemPurchasingCategory, Boolean checkParents, BasePK createdBy) {
        var selectorNodeItemPurchasingCategory = selectorNodeItemPurchasingCategoryFactory.create(
                selectorNode, itemPurchasingCategory, checkParents, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(selectorNode.getLastDetail().getSelectorPK(), EventTypes.MODIFY,
                selectorNodeItemPurchasingCategory.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return selectorNodeItemPurchasingCategory;
    }

    public long countSelectorNodeItemPurchasingCategoriesByItemPurchasingCategory(final ItemPurchasingCategory itemPurchasingCategory) {
        return session.getDslContext()
                .selectCount()
                .from(SelectorNodeItemPurchasingCategories)
                .where(SelectorNodeItemPurchasingCategories.ITEM_PURCHASING_CATEGORY.eq(itemPurchasingCategory.getPrimaryKey()),
                        SelectorNodeItemPurchasingCategories.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private SelectorNodeItemPurchasingCategory getSelectorNodeItemPurchasingCategory(SelectorNode selectorNode, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SelectorNodeItemPurchasingCategories.fields())
                .from(SelectorNodeItemPurchasingCategories)
                .where(SelectorNodeItemPurchasingCategories.SELECTOR_NODE.eq(selectorNode.getPrimaryKey()),
                        SelectorNodeItemPurchasingCategories.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return selectorNodeItemPurchasingCategoryFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public SelectorNodeItemPurchasingCategory getSelectorNodeItemPurchasingCategory(SelectorNode selectorNode) {
        return getSelectorNodeItemPurchasingCategory(selectorNode,EntityPermission.READ_ONLY);
    }
    
    public SelectorNodeItemPurchasingCategory getSelectorNodeItemPurchasingCategoryForUpdate(SelectorNode selectorNode) {
        return getSelectorNodeItemPurchasingCategory(selectorNode,EntityPermission.READ_WRITE);
    }
    
    public SelectorNodeItemPurchasingCategoryValue getSelectorNodeItemPurchasingCategoryValue(SelectorNodeItemPurchasingCategory selectorNodeItemPurchasingCategory) {
        return selectorNodeItemPurchasingCategory == null? null: selectorNodeItemPurchasingCategory.getSelectorNodeItemPurchasingCategoryValue().clone();
    }
    
    public SelectorNodeItemPurchasingCategoryValue getSelectorNodeItemPurchasingCategoryValueForUpdate(SelectorNode selectorNode) {
        var selectorNodeItemPurchasingCategory = getSelectorNodeItemPurchasingCategoryForUpdate(selectorNode);
        
        return selectorNodeItemPurchasingCategory == null? null: selectorNodeItemPurchasingCategory.getSelectorNodeItemPurchasingCategoryValue().clone();
    }
    
    private List<SelectorNodeItemPurchasingCategory> getSelectorNodeItemPurchasingCategoriesBySelector(Selector selector, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SelectorNodeItemPurchasingCategories.fields())
                .from(SelectorNodeItemPurchasingCategories)
                .join(SelectorNodes).onKey(SELECTOR_NODE_ITEM_PURCHASING_CATEGORIES_SELECTOR_NODE_FK)
                .join(SelectorNodeDetails).onKey(SELECTOR_NODES_ACTIVE_DETAIL_FK)
                .where(SelectorNodeDetails.SELECTOR.eq(selector.getPrimaryKey()),
                        SelectorNodeItemPurchasingCategories.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(SelectorNodeDetails.SELECTOR_NODE_NAME),
                    SelectorNodeItemPurchasingCategoryFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return selectorNodeItemPurchasingCategoryFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<SelectorNodeItemPurchasingCategory> getSelectorNodeItemPurchasingCategoriesBySelector(Selector selector) {
        return getSelectorNodeItemPurchasingCategoriesBySelector(selector,EntityPermission.READ_ONLY);
    }
    
    public List<SelectorNodeItemPurchasingCategory> getSelectorNodeItemPurchasingCategoriesBySelectorForUpdate(Selector selector) {
        return getSelectorNodeItemPurchasingCategoriesBySelector(selector,EntityPermission.READ_WRITE);
    }
    
    public void updateSelectorNodeItemPurchasingCategoryFromValue(SelectorNodeItemPurchasingCategoryValue selectorNodeItemPurchasingCategoryValue,
            BasePK updatedBy) {
        if(selectorNodeItemPurchasingCategoryValue.hasBeenModified()) {
            var selectorNodeItemPurchasingCategory = selectorNodeItemPurchasingCategoryFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     selectorNodeItemPurchasingCategoryValue.getPrimaryKey());
            
            selectorNodeItemPurchasingCategory.setThruTime(session.getStartTime());
            selectorNodeItemPurchasingCategory.store();

            var selectorNode = selectorNodeItemPurchasingCategory.getSelectorNode();
            var itemPurchasingCategoryPK = selectorNodeItemPurchasingCategoryValue.getItemPurchasingCategoryPK();
            var checkParents = selectorNodeItemPurchasingCategoryValue.getCheckParents();
            
            selectorNodeItemPurchasingCategory = selectorNodeItemPurchasingCategoryFactory.create(selectorNode.getPrimaryKey(),
                    itemPurchasingCategoryPK, checkParents, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(selectorNode.getLastDetail().getSelectorPK(), EventTypes.MODIFY,
                    selectorNodeItemPurchasingCategory.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteSelectorNodeItemPurchasingCategory(SelectorNodeItemPurchasingCategory selectorNodeItemPurchasingCategory,
            BasePK deletedBy) {
        selectorNodeItemPurchasingCategory.setThruTime(session.getStartTime());
        
        sendEvent(selectorNodeItemPurchasingCategory.getSelectorNode().getLastDetail().getSelectorPK(),
                EventTypes.MODIFY, selectorNodeItemPurchasingCategory.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Node Geo Codes
    // --------------------------------------------------------------------------------

    @Inject
    protected SelectorNodeGeoCodeFactory selectorNodeGeoCodeFactory;

    public SelectorNodeGeoCode createSelectorNodeGeoCode(SelectorNode selectorNode, GeoCode geoCode,
            BasePK createdBy) {
        var selectorNodeGeoCode = selectorNodeGeoCodeFactory.create(
                selectorNode, geoCode, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(selectorNode.getLastDetail().getSelectorPK(), EventTypes.MODIFY,
                selectorNodeGeoCode.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return selectorNodeGeoCode;
    }
    
    public long countSelectorNodeGeoCodesByGeoCode(GeoCode geoCode) {
        return session.getDslContext()
                .selectCount()
                .from(SelectorNodeGeoCodes)
                .where(SelectorNodeGeoCodes.GEO_CODE.eq(geoCode.getPrimaryKey()),
                        SelectorNodeGeoCodes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private SelectorNodeGeoCode getSelectorNodeGeoCode(SelectorNode selectorNode, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SelectorNodeGeoCodes.fields())
                .from(SelectorNodeGeoCodes)
                .where(SelectorNodeGeoCodes.SELECTOR_NODE.eq(selectorNode.getPrimaryKey()),
                        SelectorNodeGeoCodes.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return selectorNodeGeoCodeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public SelectorNodeGeoCode getSelectorNodeGeoCode(SelectorNode selectorNode) {
        return getSelectorNodeGeoCode(selectorNode, EntityPermission.READ_ONLY);
    }
    
    public SelectorNodeGeoCode getSelectorNodeGeoCodeForUpdate(SelectorNode selectorNode) {
        return getSelectorNodeGeoCode(selectorNode, EntityPermission.READ_WRITE);
    }
    
    public SelectorNodeGeoCodeValue getSelectorNodeGeoCodeValue(SelectorNodeGeoCode selectorNodeGeoCode) {
        return selectorNodeGeoCode == null? null: selectorNodeGeoCode.getSelectorNodeGeoCodeValue().clone();
    }
    
    public SelectorNodeGeoCodeValue getSelectorNodeGeoCodeValueForUpdate(SelectorNode selectorNode) {
        var selectorNodeGeoCode = getSelectorNodeGeoCodeForUpdate(selectorNode);
        
        return selectorNodeGeoCode == null? null: selectorNodeGeoCode.getSelectorNodeGeoCodeValue().clone();
    }
    
    private List<SelectorNodeGeoCode> getSelectorNodeGeoCodesBySelector(Selector selector, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SelectorNodeGeoCodes.fields())
                .from(SelectorNodeGeoCodes)
                .join(SelectorNodes).onKey(SELECTOR_NODE_GEO_CODES_SELECTOR_NODE_FK)
                .join(SelectorNodeDetails).onKey(SELECTOR_NODES_ACTIVE_DETAIL_FK)
                .where(SelectorNodeDetails.SELECTOR.eq(selector.getPrimaryKey()),
                        SelectorNodeGeoCodes.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(SelectorNodeDetails.SELECTOR_NODE_NAME),
                    SelectorNodeGeoCodeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return selectorNodeGeoCodeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<SelectorNodeGeoCode> getSelectorNodeGeoCodesBySelector(Selector selector) {
        return getSelectorNodeGeoCodesBySelector(selector, EntityPermission.READ_ONLY);
    }
    
    public List<SelectorNodeGeoCode> getSelectorNodeGeoCodesBySelectorForUpdate(Selector selector) {
        return getSelectorNodeGeoCodesBySelector(selector, EntityPermission.READ_WRITE);
    }
    
    public void updateSelectorNodeGeoCodeFromValue(SelectorNodeGeoCodeValue selectorNodeGeoCodeValue, BasePK updatedBy) {
        if(selectorNodeGeoCodeValue.hasBeenModified()) {
            var selectorNodeGeoCode = selectorNodeGeoCodeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     selectorNodeGeoCodeValue.getPrimaryKey());
            
            selectorNodeGeoCode.setThruTime(session.getStartTime());
            selectorNodeGeoCode.store();

            var selectorNode = selectorNodeGeoCode.getSelectorNode();
            var geoCodePK = selectorNodeGeoCodeValue.getGeoCodePK();
            
            selectorNodeGeoCode = selectorNodeGeoCodeFactory.create(selectorNode.getPrimaryKey(),
                    geoCodePK, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(selectorNode.getLastDetail().getSelectorPK(), EventTypes.MODIFY,
                    selectorNodeGeoCode.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteSelectorNodeGeoCode(SelectorNodeGeoCode selectorNodeGeoCode, BasePK deletedBy) {
        selectorNodeGeoCode.setThruTime(session.getStartTime());
        
        sendEvent(selectorNodeGeoCode.getSelectorNode().getLastDetail().getSelectorPK(),
                EventTypes.MODIFY, selectorNodeGeoCode.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Node Payment Methods
    // --------------------------------------------------------------------------------

    @Inject
    protected SelectorNodePaymentMethodFactory selectorNodePaymentMethodFactory;

    public SelectorNodePaymentMethod createSelectorNodePaymentMethod(SelectorNode selectorNode, PaymentMethod paymentMethod,
            BasePK createdBy) {
        var selectorNodePaymentMethod = selectorNodePaymentMethodFactory.create(
                selectorNode, paymentMethod, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(selectorNode.getLastDetail().getSelectorPK(), EventTypes.MODIFY,
                selectorNodePaymentMethod.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return selectorNodePaymentMethod;
    }

    public long countSelectorNodePaymentMethodByPaymentMethod(final PaymentMethod paymentMethod) {
        return session.getDslContext()
                .selectCount()
                .from(SelectorNodePaymentMethods)
                .where(SelectorNodePaymentMethods.PAYMENT_METHOD.eq(paymentMethod.getPrimaryKey()),
                        SelectorNodePaymentMethods.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private SelectorNodePaymentMethod getSelectorNodePaymentMethod(SelectorNode selectorNode, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SelectorNodePaymentMethods.fields())
                .from(SelectorNodePaymentMethods)
                .where(SelectorNodePaymentMethods.SELECTOR_NODE.eq(selectorNode.getPrimaryKey()),
                        SelectorNodePaymentMethods.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return selectorNodePaymentMethodFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public SelectorNodePaymentMethod getSelectorNodePaymentMethod(SelectorNode selectorNode) {
        return getSelectorNodePaymentMethod(selectorNode, EntityPermission.READ_ONLY);
    }
    
    public SelectorNodePaymentMethod getSelectorNodePaymentMethodForUpdate(SelectorNode selectorNode) {
        return getSelectorNodePaymentMethod(selectorNode, EntityPermission.READ_WRITE);
    }
    
    public SelectorNodePaymentMethodValue getSelectorNodePaymentMethodValue(SelectorNodePaymentMethod selectorNodePaymentMethod) {
        return selectorNodePaymentMethod == null? null: selectorNodePaymentMethod.getSelectorNodePaymentMethodValue().clone();
    }
    
    public SelectorNodePaymentMethodValue getSelectorNodePaymentMethodValueForUpdate(SelectorNode selectorNode) {
        var selectorNodePaymentMethod = getSelectorNodePaymentMethodForUpdate(selectorNode);
        
        return selectorNodePaymentMethod == null? null: selectorNodePaymentMethod.getSelectorNodePaymentMethodValue().clone();
    }
    
    private List<SelectorNodePaymentMethod> getSelectorNodePaymentMethodsBySelector(Selector selector, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SelectorNodePaymentMethods.fields())
                .from(SelectorNodePaymentMethods)
                .join(SelectorNodes).onKey(SELECTOR_NODE_PAYMENT_METHODS_SELECTOR_NODE_FK)
                .join(SelectorNodeDetails).onKey(SELECTOR_NODES_ACTIVE_DETAIL_FK)
                .where(SelectorNodeDetails.SELECTOR.eq(selector.getPrimaryKey()),
                        SelectorNodePaymentMethods.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(SelectorNodeDetails.SELECTOR_NODE_NAME),
                    SelectorNodePaymentMethodFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return selectorNodePaymentMethodFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<SelectorNodePaymentMethod> getSelectorNodePaymentMethodsBySelector(Selector selector) {
        return getSelectorNodePaymentMethodsBySelector(selector, EntityPermission.READ_ONLY);
    }
    
    public List<SelectorNodePaymentMethod> getSelectorNodePaymentMethodsBySelectorForUpdate(Selector selector) {
        return getSelectorNodePaymentMethodsBySelector(selector, EntityPermission.READ_WRITE);
    }
    
    public void updateSelectorNodePaymentMethodFromValue(SelectorNodePaymentMethodValue selectorNodePaymentMethodValue, BasePK updatedBy) {
        if(selectorNodePaymentMethodValue.hasBeenModified()) {
            var selectorNodePaymentMethod = selectorNodePaymentMethodFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     selectorNodePaymentMethodValue.getPrimaryKey());
            
            selectorNodePaymentMethod.setThruTime(session.getStartTime());
            selectorNodePaymentMethod.store();

            var selectorNode = selectorNodePaymentMethod.getSelectorNode();
            var paymentMethodPK = selectorNodePaymentMethodValue.getPaymentMethodPK();
            
            selectorNodePaymentMethod = selectorNodePaymentMethodFactory.create(selectorNode.getPrimaryKey(),
                    paymentMethodPK, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(selectorNode.getLastDetail().getSelectorPK(), EventTypes.MODIFY,
                    selectorNodePaymentMethod.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteSelectorNodePaymentMethod(SelectorNodePaymentMethod selectorNodePaymentMethod, BasePK deletedBy) {
        selectorNodePaymentMethod.setThruTime(session.getStartTime());
        
        sendEvent(selectorNodePaymentMethod.getSelectorNode().getLastDetail().getSelectorPK(),
                EventTypes.MODIFY, selectorNodePaymentMethod.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Node Payment Processors
    // --------------------------------------------------------------------------------

    @Inject
    protected SelectorNodePaymentProcessorFactory selectorNodePaymentProcessorFactory;

    public SelectorNodePaymentProcessor createSelectorNodePaymentProcessor(SelectorNode selectorNode, PaymentProcessor paymentProcessor,
            BasePK createdBy) {
        var selectorNodePaymentProcessor = selectorNodePaymentProcessorFactory.create(
                selectorNode, paymentProcessor, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(selectorNode.getLastDetail().getSelectorPK(), EventTypes.MODIFY,
                selectorNodePaymentProcessor.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return selectorNodePaymentProcessor;
    }

    public long countSelectorNodePaymentProcessorByPaymentProcessor(PaymentProcessor paymentProcessor) {
        return session.getDslContext()
                .selectCount()
                .from(SelectorNodePaymentProcessors)
                .where(SelectorNodePaymentProcessors.PAYMENT_PROCESSOR.eq(paymentProcessor.getPrimaryKey()),
                        SelectorNodePaymentProcessors.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private SelectorNodePaymentProcessor getSelectorNodePaymentProcessor(SelectorNode selectorNode, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SelectorNodePaymentProcessors.fields())
                .from(SelectorNodePaymentProcessors)
                .where(SelectorNodePaymentProcessors.SELECTOR_NODE.eq(selectorNode.getPrimaryKey()),
                        SelectorNodePaymentProcessors.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return selectorNodePaymentProcessorFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public SelectorNodePaymentProcessor getSelectorNodePaymentProcessor(SelectorNode selectorNode) {
        return getSelectorNodePaymentProcessor(selectorNode, EntityPermission.READ_ONLY);
    }
    
    public SelectorNodePaymentProcessor getSelectorNodePaymentProcessorForUpdate(SelectorNode selectorNode) {
        return getSelectorNodePaymentProcessor(selectorNode, EntityPermission.READ_WRITE);
    }
    
    public SelectorNodePaymentProcessorValue getSelectorNodePaymentProcessorValue(SelectorNodePaymentProcessor selectorNodePaymentProcessor) {
        return selectorNodePaymentProcessor == null? null: selectorNodePaymentProcessor.getSelectorNodePaymentProcessorValue().clone();
    }
    
    public SelectorNodePaymentProcessorValue getSelectorNodePaymentProcessorValueForUpdate(SelectorNode selectorNode) {
        var selectorNodePaymentProcessor = getSelectorNodePaymentProcessorForUpdate(selectorNode);
        
        return selectorNodePaymentProcessor == null? null: selectorNodePaymentProcessor.getSelectorNodePaymentProcessorValue().clone();
    }
    
    private List<SelectorNodePaymentProcessor> getSelectorNodePaymentProcessorsBySelector(Selector selector, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SelectorNodePaymentProcessors.fields())
                .from(SelectorNodePaymentProcessors)
                .join(SelectorNodes).onKey(SELECTOR_NODE_PAYMENT_PROCESSORS_SELECTOR_NODE_FK)
                .join(SelectorNodeDetails).onKey(SELECTOR_NODES_ACTIVE_DETAIL_FK)
                .where(SelectorNodeDetails.SELECTOR.eq(selector.getPrimaryKey()),
                        SelectorNodePaymentProcessors.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(SelectorNodeDetails.SELECTOR_NODE_NAME),
                    SelectorNodePaymentProcessorFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return selectorNodePaymentProcessorFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<SelectorNodePaymentProcessor> getSelectorNodePaymentProcessorsBySelector(Selector selector) {
        return getSelectorNodePaymentProcessorsBySelector(selector, EntityPermission.READ_ONLY);
    }
    
    public List<SelectorNodePaymentProcessor> getSelectorNodePaymentProcessorsBySelectorForUpdate(Selector selector) {
        return getSelectorNodePaymentProcessorsBySelector(selector, EntityPermission.READ_WRITE);
    }
    
    public void updateSelectorNodePaymentProcessorFromValue(SelectorNodePaymentProcessorValue selectorNodePaymentProcessorValue, BasePK updatedBy) {
        if(selectorNodePaymentProcessorValue.hasBeenModified()) {
            var selectorNodePaymentProcessor = selectorNodePaymentProcessorFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     selectorNodePaymentProcessorValue.getPrimaryKey());
            
            selectorNodePaymentProcessor.setThruTime(session.getStartTime());
            selectorNodePaymentProcessor.store();

            var selectorNode = selectorNodePaymentProcessor.getSelectorNode();
            var paymentProcessorPK = selectorNodePaymentProcessorValue.getPaymentProcessorPK();
            
            selectorNodePaymentProcessor = selectorNodePaymentProcessorFactory.create(selectorNode.getPrimaryKey(),
                    paymentProcessorPK, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(selectorNode.getLastDetail().getSelectorPK(), EventTypes.MODIFY,
                    selectorNodePaymentProcessor.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteSelectorNodePaymentProcessor(SelectorNodePaymentProcessor selectorNodePaymentProcessor, BasePK deletedBy) {
        selectorNodePaymentProcessor.setThruTime(session.getStartTime());
        
        sendEvent(selectorNodePaymentProcessor.getSelectorNode().getLastDetail().getSelectorPK(),
                EventTypes.MODIFY, selectorNodePaymentProcessor.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Parties
    // --------------------------------------------------------------------------------

    @Inject
    protected SelectorPartyFactory selectorPartyFactory;

    @Inject
    private SelectorPartyTransferCache selectorPartyTransferCache;

    public SelectorParty createSelectorParty(Selector selector, Party party, BasePK createdBy) {
        var selectorParty = selectorPartyFactory.create(selector, party, session.getStartTime(),
                Session.MAX_TIME);
        
        return selectorParty;
    }

    public long countSelectorPartiesBySelector(final Selector selector) {
        return session.getDslContext()
                .selectCount()
                .from(SelectorParties)
                .where(SelectorParties.SELECTOR.eq(selector.getPrimaryKey()),
                        SelectorParties.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countSelectorPartiesByParty(final Party party) {
        return session.getDslContext()
                .selectCount()
                .from(SelectorParties)
                .where(SelectorParties.PARTY.eq(party.getPrimaryKey()),
                        SelectorParties.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private SelectorParty getSelectorParty(Selector selector, Party party, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SelectorParties.fields())
                .from(SelectorParties)
                .where(SelectorParties.SELECTOR.eq(selector.getPrimaryKey()),
                        SelectorParties.PARTY.eq(party.getPrimaryKey()),
                        SelectorParties.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return selectorPartyFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public SelectorParty getSelectorParty(Selector selector, Party party) {
        return getSelectorParty(selector, party, EntityPermission.READ_ONLY);
    }
    
    public SelectorParty getSelectorPartyForUpdate(Selector selector, Party party) {
        return getSelectorParty(selector, party, EntityPermission.READ_WRITE);
    }
    
    public SelectorPartyValue getSelectorPartyValueForUpdate(Selector selector, Party party) {
        var selectorParty = getSelectorPartyForUpdate(selector, party);
        
        return selectorParty == null? null: selectorParty.getSelectorPartyValue().clone();
    }
    
    private List<SelectorParty> getSelectorPartiesBySelector(Selector selector, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(SelectorParties.fields())
                    .from(SelectorParties)
                    .join(Parties).onKey(SELECTOR_PARTIES_PARTY_FK)
                    .join(PartyDetails).onKey(PARTIES_LAST_DETAIL_FK)
                    .where(SelectorParties.SELECTOR.eq(selector.getPrimaryKey()),
                            SelectorParties.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(PartyDetails.PARTY_NAME),
                    SelectorPartyFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(SelectorParties.fields())
                    .from(SelectorParties)
                    .where(SelectorParties.SELECTOR.eq(selector.getPrimaryKey()),
                            SelectorParties.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return selectorPartyFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<SelectorParty> getSelectorPartiesBySelector(Selector selector) {
        return getSelectorPartiesBySelector(selector, EntityPermission.READ_ONLY);
    }
    
    public List<SelectorParty> getSelectorPartiesBySelectorForUpdate(Selector selector) {
        return getSelectorPartiesBySelector(selector, EntityPermission.READ_WRITE);
    }
    
    public SelectorPartyTransfer getSelectorPartyTransfer(UserVisit userVisit, SelectorParty selectorParty) {
        return selectorPartyTransferCache.getSelectorPartyTransfer(userVisit, selectorParty);
    }
    
    public List<SelectorPartyTransfer> getSelectorPartyTransfers(UserVisit userVisit, Collection<SelectorParty> selectorParties) {
        List<SelectorPartyTransfer> selectorPartyTransfers = new ArrayList<>(selectorParties.size());
        
        selectorParties.forEach((selectorParty) ->
                selectorPartyTransfers.add(selectorPartyTransferCache.getSelectorPartyTransfer(userVisit, selectorParty))
        );
        
        return selectorPartyTransfers;
    }
    
    public List<SelectorPartyTransfer> getSelectorPartyTransfers(UserVisit userVisit, Selector selector) {
        return getSelectorPartyTransfers(userVisit, getSelectorPartiesBySelector(selector));
    }
    
    public void deleteSelectorParty(SelectorParty selectorParty, BasePK deletedBy) {
        selectorParty.setThruTime(session.getStartTime());
    }
    
    public void deleteSelectorPartiesBySelector(Selector selector, BasePK deletedBy) {
        var selectorParties = getSelectorPartiesBySelectorForUpdate(selector);
        
        selectorParties.forEach((selectorParty) -> 
                deleteSelectorParty(selectorParty, deletedBy)
        );
    }
    
}
