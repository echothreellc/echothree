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
import com.echothree.model.control.selector.server.transfer.SelectorKindTransferCache;
import com.echothree.model.control.selector.server.transfer.SelectorNodeTypeTransferCache;
import com.echothree.model.control.selector.server.transfer.SelectorPartyTransferCache;
import com.echothree.model.control.selector.server.transfer.SelectorTransferCache;
import com.echothree.model.control.selector.server.transfer.SelectorTransferCaches;
import com.echothree.model.control.selector.server.transfer.SelectorTypeTransferCache;
import com.echothree.model.data.accounting.common.pk.ItemAccountingCategoryPK;
import com.echothree.model.data.accounting.server.entity.ItemAccountingCategory;
import com.echothree.model.data.core.common.pk.EntityListItemPK;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityListItem;
import com.echothree.model.data.employee.common.pk.ResponsibilityTypePK;
import com.echothree.model.data.employee.common.pk.SkillTypePK;
import com.echothree.model.data.employee.server.entity.ResponsibilityType;
import com.echothree.model.data.employee.server.entity.SkillType;
import com.echothree.model.data.geo.common.pk.GeoCodePK;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.item.common.pk.ItemCategoryPK;
import com.echothree.model.data.item.server.entity.ItemCategory;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.payment.common.pk.PaymentMethodPK;
import com.echothree.model.data.payment.common.pk.PaymentProcessorPK;
import com.echothree.model.data.payment.server.entity.PaymentMethod;
import com.echothree.model.data.payment.server.entity.PaymentProcessor;
import com.echothree.model.data.selector.common.pk.SelectorBooleanTypePK;
import com.echothree.model.data.selector.common.pk.SelectorKindPK;
import com.echothree.model.data.selector.common.pk.SelectorNodePK;
import com.echothree.model.data.selector.common.pk.SelectorNodeTypePK;
import com.echothree.model.data.selector.common.pk.SelectorPK;
import com.echothree.model.data.selector.common.pk.SelectorTypePK;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.selector.server.entity.SelectorBooleanType;
import com.echothree.model.data.selector.server.entity.SelectorBooleanTypeDescription;
import com.echothree.model.data.selector.server.entity.SelectorComparisonType;
import com.echothree.model.data.selector.server.entity.SelectorComparisonTypeDescription;
import com.echothree.model.data.selector.server.entity.SelectorDescription;
import com.echothree.model.data.selector.server.entity.SelectorDetail;
import com.echothree.model.data.selector.server.entity.SelectorKind;
import com.echothree.model.data.selector.server.entity.SelectorKindDescription;
import com.echothree.model.data.selector.server.entity.SelectorKindDetail;
import com.echothree.model.data.selector.server.entity.SelectorNode;
import com.echothree.model.data.selector.server.entity.SelectorNodeBoolean;
import com.echothree.model.data.selector.server.entity.SelectorNodeDescription;
import com.echothree.model.data.selector.server.entity.SelectorNodeDetail;
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
import com.echothree.model.data.selector.server.entity.SelectorTypeDetail;
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
import com.echothree.model.data.training.common.pk.TrainingClassPK;
import com.echothree.model.data.training.server.entity.TrainingClass;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.vendor.common.pk.ItemPurchasingCategoryPK;
import com.echothree.model.data.vendor.server.entity.ItemPurchasingCategory;
import com.echothree.model.data.workflow.common.pk.WorkflowStepPK;
import com.echothree.model.data.workflow.server.entity.WorkflowStep;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
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

public class SelectorControl
        extends BaseModelControl {
    
    /** Creates a new instance of SelectorControl */
    public SelectorControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Transfer Caches
    // --------------------------------------------------------------------------------
    
    private SelectorTransferCaches selectorTransferCaches;
    
    public SelectorTransferCaches getSelectorTransferCaches(UserVisit userVisit) {
        if(selectorTransferCaches == null) {
            selectorTransferCaches = new SelectorTransferCaches(userVisit, this);
        }
        
        return selectorTransferCaches;
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Kinds
    // --------------------------------------------------------------------------------

    public SelectorKind createSelectorKind(String selectorKindName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        SelectorKind defaultSelectorKind = getDefaultSelectorKind();
        boolean defaultFound = defaultSelectorKind != null;

        if(defaultFound && isDefault) {
            SelectorKindDetailValue defaultSelectorKindDetailValue = getDefaultSelectorKindDetailValueForUpdate();

            defaultSelectorKindDetailValue.setIsDefault(Boolean.FALSE);
            updateSelectorKindFromValue(defaultSelectorKindDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        SelectorKind selectorKind = SelectorKindFactory.getInstance().create();
        SelectorKindDetail selectorKindDetail = SelectorKindDetailFactory.getInstance().create(selectorKind, selectorKindName, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        selectorKind = SelectorKindFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                selectorKind.getPrimaryKey());
        selectorKind.setActiveDetail(selectorKindDetail);
        selectorKind.setLastDetail(selectorKindDetail);
        selectorKind.store();

        sendEventUsingNames(selectorKind.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return selectorKind;
    }

    public long countSelectorKinds() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM selectorkinds, selectorkinddetails " +
                        "WHERE slk_activedetailid = slkdt_selectorkinddetailid");
    }

    /** Assume that the entityInstance passed to this function is a ECHOTHREE.SelectorKind */
    public SelectorKind getSelectorKindByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new SelectorKindPK(entityInstance.getEntityUniqueId());
        var selectorKind = SelectorKindFactory.getInstance().getEntityFromPK(entityPermission, pk);

        return selectorKind;
    }

    public SelectorKind getSelectorKindByEntityInstance(EntityInstance entityInstance) {
        return getSelectorKindByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public SelectorKind getSelectorKindByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getSelectorKindByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getSelectorKindByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM selectorkinds, selectorkinddetails "
                + "WHERE slk_activedetailid = slkdt_selectorkinddetailid AND slkdt_selectorkindname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM selectorkinds, selectorkinddetails "
                + "WHERE slk_activedetailid = slkdt_selectorkinddetailid AND slkdt_selectorkindname = ? "
                + "FOR UPDATE");
        getSelectorKindByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    public SelectorKind getSelectorKindByName(String selectorKindName, EntityPermission entityPermission) {
        return SelectorKindFactory.getInstance().getEntityFromQuery(entityPermission, getSelectorKindByNameQueries,
                selectorKindName);
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

    private static final Map<EntityPermission, String> getDefaultSelectorKindQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM selectorkinds, selectorkinddetails "
                + "WHERE slk_activedetailid = slkdt_selectorkinddetailid AND slkdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM selectorkinds, selectorkinddetails "
                + "WHERE slk_activedetailid = slkdt_selectorkinddetailid AND slkdt_isdefault = 1 "
                + "FOR UPDATE");
        getDefaultSelectorKindQueries = Collections.unmodifiableMap(queryMap);
    }

    public SelectorKind getDefaultSelectorKind(EntityPermission entityPermission) {
        return SelectorKindFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultSelectorKindQueries);
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

    private static final Map<EntityPermission, String> getSelectorKindsQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM selectorkinds, selectorkinddetails "
                + "WHERE slk_activedetailid = slkdt_selectorkinddetailid "
                + "ORDER BY slkdt_sortorder, slkdt_selectorkindname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM selectorkinds, selectorkinddetails "
                + "WHERE slk_activedetailid = slkdt_selectorkinddetailid "
                + "FOR UPDATE");
        getSelectorKindsQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<SelectorKind> getSelectorKinds(EntityPermission entityPermission) {
        return SelectorKindFactory.getInstance().getEntitiesFromQuery(entityPermission, getSelectorKindsQueries);
    }

    public List<SelectorKind> getSelectorKinds() {
        return getSelectorKinds(EntityPermission.READ_ONLY);
    }

    public List<SelectorKind> getSelectorKindsForUpdate() {
        return getSelectorKinds(EntityPermission.READ_WRITE);
    }

    public SelectorKindChoicesBean getSelectorKindChoices(String defaultSelectorKindChoice, Language language, boolean allowNullChoice) {
        List<SelectorKind> selectorKinds = getSelectorKinds();
        int size = selectorKinds.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultSelectorKindChoice == null) {
                defaultValue = "";
            }
        }

        for(var selectorKind : selectorKinds) {
            SelectorKindDetail selectorKindDetail = selectorKind.getLastDetail();

            String label = getBestSelectorKindDescription(selectorKind, language);
            String value = selectorKindDetail.getSelectorKindName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultSelectorKindChoice != null && defaultSelectorKindChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && selectorKindDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new SelectorKindChoicesBean(labels, values, defaultValue);
    }

    public SelectorKindTransfer getSelectorKindTransfer(UserVisit userVisit, SelectorKind selectorKind) {
        return getSelectorTransferCaches(userVisit).getSelectorKindTransferCache().getSelectorKindTransfer(selectorKind);
    }

    public List<SelectorKindTransfer> getSelectorKindTransfers(UserVisit userVisit, Collection<SelectorKind> selectorKinds) {
        List<SelectorKindTransfer> selectorKindTransfers = new ArrayList<>(selectorKinds.size());
        SelectorKindTransferCache selectorKindTransferCache = getSelectorTransferCaches(userVisit).getSelectorKindTransferCache();

        selectorKinds.forEach((selectorKind) ->
                selectorKindTransfers.add(selectorKindTransferCache.getSelectorKindTransfer(selectorKind))
        );

        return selectorKindTransfers;
    }

    public List<SelectorKindTransfer> getSelectorKindTransfers(UserVisit userVisit) {
        return getSelectorKindTransfers(userVisit, getSelectorKinds());
    }

    private void updateSelectorKindFromValue(SelectorKindDetailValue selectorKindDetailValue, boolean checkDefault, BasePK updatedBy) {
        SelectorKind selectorKind = SelectorKindFactory.getInstance().getEntityFromPK(session,
                EntityPermission.READ_WRITE, selectorKindDetailValue.getSelectorKindPK());
        SelectorKindDetail selectorKindDetail = selectorKind.getActiveDetailForUpdate();

        selectorKindDetail.setThruTime(session.START_TIME_LONG);
        selectorKindDetail.store();

        SelectorKindPK selectorKindPK = selectorKindDetail.getSelectorKindPK();
        String selectorKindName = selectorKindDetailValue.getSelectorKindName();
        Boolean isDefault = selectorKindDetailValue.getIsDefault();
        Integer sortOrder = selectorKindDetailValue.getSortOrder();

        if(checkDefault) {
            SelectorKind defaultSelectorKind = getDefaultSelectorKind();
            boolean defaultFound = defaultSelectorKind != null && !defaultSelectorKind.equals(selectorKind);

            if(isDefault && defaultFound) {
                // If I'm the default, and a default already existed...
                SelectorKindDetailValue defaultSelectorKindDetailValue = getDefaultSelectorKindDetailValueForUpdate();

                defaultSelectorKindDetailValue.setIsDefault(Boolean.FALSE);
                updateSelectorKindFromValue(defaultSelectorKindDetailValue, false, updatedBy);
            } else if(!isDefault && !defaultFound) {
                // If I'm not the default, and no other default exists...
                isDefault = Boolean.TRUE;
            }
        }

        selectorKindDetail = SelectorKindDetailFactory.getInstance().create(selectorKindPK, selectorKindName, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        selectorKind.setActiveDetail(selectorKindDetail);
        selectorKind.setLastDetail(selectorKindDetail);
        selectorKind.store();

        sendEventUsingNames(selectorKindPK, EventTypes.MODIFY.name(), null, null, updatedBy);
    }

    public void updateSelectorKindFromValue(SelectorKindDetailValue selectorKindDetailValue, BasePK updatedBy) {
        updateSelectorKindFromValue(selectorKindDetailValue, true, updatedBy);
    }

    public void deleteSelectorKind(SelectorKind selectorKind, BasePK deletedBy) {
        deleteSelectorKindDescriptionsBySelectorKind(selectorKind, deletedBy);

        SelectorKindDetail selectorKindDetail = selectorKind.getLastDetailForUpdate();
        selectorKindDetail.setThruTime(session.START_TIME_LONG);
        selectorKind.setActiveDetail(null);
        selectorKind.store();

        // Check for default, and pick one if necessary
        SelectorKind defaultSelectorKind = getDefaultSelectorKind();
        if(defaultSelectorKind == null) {
            List<SelectorKind> selectorKinds = getSelectorKindsForUpdate();

            if(!selectorKinds.isEmpty()) {
                Iterator<SelectorKind> iter = selectorKinds.iterator();
                if(iter.hasNext()) {
                    defaultSelectorKind = iter.next();
                }
                SelectorKindDetailValue selectorKindDetailValue = Objects.requireNonNull(defaultSelectorKind).getLastDetailForUpdate().getSelectorKindDetailValue().clone();

                selectorKindDetailValue.setIsDefault(Boolean.TRUE);
                updateSelectorKindFromValue(selectorKindDetailValue, false, deletedBy);
            }
        }

        sendEventUsingNames(selectorKind.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Selector Kind Descriptions
    // --------------------------------------------------------------------------------

    public SelectorKindDescription createSelectorKindDescription(SelectorKind selectorKind, Language language, String description,
            BasePK createdBy) {
        SelectorKindDescription selectorKindDescription = SelectorKindDescriptionFactory.getInstance().create(selectorKind,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(selectorKind.getPrimaryKey(), EventTypes.MODIFY.name(), selectorKindDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return selectorKindDescription;
    }

    private static final Map<EntityPermission, String> getSelectorKindDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM selectorkinddescriptions "
                + "WHERE slkd_slk_selectorkindid = ? AND slkd_lang_languageid = ? AND slkd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM selectorkinddescriptions "
                + "WHERE slkd_slk_selectorkindid = ? AND slkd_lang_languageid = ? AND slkd_thrutime = ? "
                + "FOR UPDATE");
        getSelectorKindDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private SelectorKindDescription getSelectorKindDescription(SelectorKind selectorKind, Language language, EntityPermission entityPermission) {
        return SelectorKindDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getSelectorKindDescriptionQueries,
                selectorKind, language, Session.MAX_TIME);
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

    private static final Map<EntityPermission, String> getSelectorKindDescriptionsBySelectorKindQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM selectorkinddescriptions, languages "
                + "WHERE slkd_slk_selectorkindid = ? AND slkd_thrutime = ? AND slkd_lang_languageid = lang_languageid "
                + "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM selectorkinddescriptions "
                + "WHERE slkd_slk_selectorkindid = ? AND slkd_thrutime = ? "
                + "FOR UPDATE");
        getSelectorKindDescriptionsBySelectorKindQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<SelectorKindDescription> getSelectorKindDescriptionsBySelectorKind(SelectorKind selectorKind, EntityPermission entityPermission) {
        return SelectorKindDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getSelectorKindDescriptionsBySelectorKindQueries,
                selectorKind, Session.MAX_TIME);
    }

    public List<SelectorKindDescription> getSelectorKindDescriptionsBySelectorKind(SelectorKind selectorKind) {
        return getSelectorKindDescriptionsBySelectorKind(selectorKind, EntityPermission.READ_ONLY);
    }

    public List<SelectorKindDescription> getSelectorKindDescriptionsBySelectorKindForUpdate(SelectorKind selectorKind) {
        return getSelectorKindDescriptionsBySelectorKind(selectorKind, EntityPermission.READ_WRITE);
    }

    public String getBestSelectorKindDescription(SelectorKind selectorKind, Language language) {
        String description;
        SelectorKindDescription selectorKindDescription = getSelectorKindDescription(selectorKind, language);

        if(selectorKindDescription == null && !language.getIsDefault()) {
            selectorKindDescription = getSelectorKindDescription(selectorKind, getPartyControl().getDefaultLanguage());
        }

        if(selectorKindDescription == null) {
            description = selectorKind.getLastDetail().getSelectorKindName();
        } else {
            description = selectorKindDescription.getDescription();
        }

        return description;
    }

    public SelectorKindDescriptionTransfer getSelectorKindDescriptionTransfer(UserVisit userVisit, SelectorKindDescription selectorKindDescription) {
        return getSelectorTransferCaches(userVisit).getSelectorKindDescriptionTransferCache().getSelectorKindDescriptionTransfer(selectorKindDescription);
    }

    public List<SelectorKindDescriptionTransfer> getSelectorKindDescriptionTransfersBySelectorKind(UserVisit userVisit, SelectorKind selectorKind) {
        List<SelectorKindDescription> selectorKindDescriptions = getSelectorKindDescriptionsBySelectorKind(selectorKind);
        List<SelectorKindDescriptionTransfer> selectorKindDescriptionTransfers = new ArrayList<>(selectorKindDescriptions.size());

        selectorKindDescriptions.forEach((selectorKindDescription) -> {
            selectorKindDescriptionTransfers.add(getSelectorTransferCaches(userVisit).getSelectorKindDescriptionTransferCache().getSelectorKindDescriptionTransfer(selectorKindDescription));
        });

        return selectorKindDescriptionTransfers;
    }

    public void updateSelectorKindDescriptionFromValue(SelectorKindDescriptionValue selectorKindDescriptionValue, BasePK updatedBy) {
        if(selectorKindDescriptionValue.hasBeenModified()) {
            SelectorKindDescription selectorKindDescription = SelectorKindDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     selectorKindDescriptionValue.getPrimaryKey());

            selectorKindDescription.setThruTime(session.START_TIME_LONG);
            selectorKindDescription.store();

            SelectorKind selectorKind = selectorKindDescription.getSelectorKind();
            Language language = selectorKindDescription.getLanguage();
            String description = selectorKindDescriptionValue.getDescription();

            selectorKindDescription = SelectorKindDescriptionFactory.getInstance().create(selectorKind, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(selectorKind.getPrimaryKey(), EventTypes.MODIFY.name(), selectorKindDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteSelectorKindDescription(SelectorKindDescription selectorKindDescription, BasePK deletedBy) {
        selectorKindDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(selectorKindDescription.getSelectorKindPK(), EventTypes.MODIFY.name(), selectorKindDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteSelectorKindDescriptionsBySelectorKind(SelectorKind selectorKind, BasePK deletedBy) {
        List<SelectorKindDescription> selectorKindDescriptions = getSelectorKindDescriptionsBySelectorKindForUpdate(selectorKind);

        selectorKindDescriptions.forEach((selectorKindDescription) -> 
                deleteSelectorKindDescription(selectorKindDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Selector Types
    // --------------------------------------------------------------------------------

    public SelectorType createSelectorType(SelectorKind selectorKind, String selectorTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        SelectorType defaultSelectorType = getDefaultSelectorType(selectorKind);
        boolean defaultFound = defaultSelectorType != null;

        if(defaultFound && isDefault) {
            SelectorTypeDetailValue defaultSelectorTypeDetailValue = getDefaultSelectorTypeDetailValueForUpdate(selectorKind);

            defaultSelectorTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateSelectorTypeFromValue(defaultSelectorTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        SelectorType selectorType = SelectorTypeFactory.getInstance().create();
        SelectorTypeDetail selectorTypeDetail = SelectorTypeDetailFactory.getInstance().create(session, selectorType, selectorKind, selectorTypeName, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        selectorType = SelectorTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                selectorType.getPrimaryKey());
        selectorType.setActiveDetail(selectorTypeDetail);
        selectorType.setLastDetail(selectorTypeDetail);
        selectorType.store();

        sendEventUsingNames(selectorType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return selectorType;
    }

    public long countSelectorTypesBySelectorKind(SelectorKind selectorKind) {
        return session.queryForLong(
                "SELECT _ALL_ "
                + "FROM selectortypes, selectortypedetails "
                + "WHERE slt_activedetailid = sltdt_selectortypedetailid AND sltdt_slk_selectorkindid = ?",
                selectorKind);
    }

    /** Assume that the entityInstance passed to this function is a ECHOTHREE.SelectorType */
    public SelectorType getSelectorTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new SelectorTypePK(entityInstance.getEntityUniqueId());
        var selectorType = SelectorTypeFactory.getInstance().getEntityFromPK(entityPermission, pk);

        return selectorType;
    }

    public SelectorType getSelectorTypeByEntityInstance(EntityInstance entityInstance) {
        return getSelectorTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public SelectorType getSelectorTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getSelectorTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getSelectorTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM selectortypes, selectortypedetails "
                + "WHERE slt_activedetailid = sltdt_selectortypedetailid AND sltdt_slk_selectorkindid = ? "
                + "ORDER BY sltdt_sortorder, sltdt_selectortypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM selectortypes, selectortypedetails "
                + "WHERE slt_activedetailid = sltdt_selectortypedetailid AND sltdt_slk_selectorkindid = ? "
                + "FOR UPDATE");
        getSelectorTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<SelectorType> getSelectorTypes(SelectorKind selectorKind, EntityPermission entityPermission) {
        return SelectorTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getSelectorTypesQueries,
                selectorKind);
    }

    public List<SelectorType> getSelectorTypes(SelectorKind selectorKind) {
        return getSelectorTypes(selectorKind, EntityPermission.READ_ONLY);
    }

    public List<SelectorType> getSelectorTypesForUpdate(SelectorKind selectorKind) {
        return getSelectorTypes(selectorKind, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getDefaultSelectorTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM selectortypes, selectortypedetails "
                + "WHERE slt_activedetailid = sltdt_selectortypedetailid "
                + "AND sltdt_slk_selectorkindid = ? AND sltdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM selectortypes, selectortypedetails "
                + "WHERE slt_activedetailid = sltdt_selectortypedetailid "
                + "AND sltdt_slk_selectorkindid = ? AND sltdt_isdefault = 1 "
                + "FOR UPDATE");
        getDefaultSelectorTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    public SelectorType getDefaultSelectorType(SelectorKind selectorKind, EntityPermission entityPermission) {
        return SelectorTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultSelectorTypeQueries,
                selectorKind);
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

    private static final Map<EntityPermission, String> getSelectorTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM selectortypes, selectortypedetails "
                + "WHERE slt_activedetailid = sltdt_selectortypedetailid "
                + "AND sltdt_slk_selectorkindid = ? AND sltdt_selectortypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM selectortypes, selectortypedetails "
                + "WHERE slt_activedetailid = sltdt_selectortypedetailid "
                + "AND sltdt_slk_selectorkindid = ? AND sltdt_selectortypename = ? "
                + "FOR UPDATE");
        getSelectorTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    public SelectorType getSelectorTypeByName(SelectorKind selectorKind, String selectorTypeName, EntityPermission entityPermission) {
        return SelectorTypeFactory.getInstance().getEntityFromQuery(entityPermission, getSelectorTypeByNameQueries,
                selectorKind, selectorTypeName);
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
        List<SelectorType> selectorTypes = getSelectorTypes(selectorKind);
        int size = selectorTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultSelectorTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var selectorType : selectorTypes) {
            SelectorTypeDetail selectorTypeDetail = selectorType.getLastDetail();
            String label = getBestSelectorTypeDescription(selectorType, language);
            String value = selectorTypeDetail.getSelectorTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultSelectorTypeChoice != null && defaultSelectorTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && selectorTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new SelectorTypeChoicesBean(labels, values, defaultValue);
    }

    public SelectorTypeTransfer getSelectorTypeTransfer(UserVisit userVisit, SelectorType selectorType) {
        return getSelectorTransferCaches(userVisit).getSelectorTypeTransferCache().getSelectorTypeTransfer(selectorType);
    }

    public List<SelectorTypeTransfer> getSelectorTypeTransfers(UserVisit userVisit, Collection<SelectorType> selectorTypes) {
        List<SelectorTypeTransfer> selectorTypeTransfers = new ArrayList<>(selectorTypes.size());
        SelectorTypeTransferCache selectorTypeTransferCache = getSelectorTransferCaches(userVisit).getSelectorTypeTransferCache();

        selectorTypes.forEach((selectorType) ->
                selectorTypeTransfers.add(selectorTypeTransferCache.getSelectorTypeTransfer(selectorType))
        );

        return selectorTypeTransfers;
    }

    public List<SelectorTypeTransfer> getSelectorTypeTransfersBySelectorKind(UserVisit userVisit, SelectorKind selectorKind) {
        return getSelectorTypeTransfers(userVisit, getSelectorTypes(selectorKind));
    }

    private void updateSelectorTypeFromValue(SelectorTypeDetailValue selectorTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(selectorTypeDetailValue.hasBeenModified()) {
            SelectorType selectorType = SelectorTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     selectorTypeDetailValue.getSelectorTypePK());
            SelectorTypeDetail selectorTypeDetail = selectorType.getActiveDetailForUpdate();

            selectorTypeDetail.setThruTime(session.START_TIME_LONG);
            selectorTypeDetail.store();

            SelectorTypePK selectorTypePK = selectorTypeDetail.getSelectorTypePK();
            SelectorKind selectorKind = selectorTypeDetail.getSelectorKind();
            SelectorKindPK selectorKindPK = selectorKind.getPrimaryKey();
            String selectorTypeName = selectorTypeDetailValue.getSelectorTypeName();
            Boolean isDefault = selectorTypeDetailValue.getIsDefault();
            Integer sortOrder = selectorTypeDetailValue.getSortOrder();

            if(checkDefault) {
                SelectorType defaultSelectorType = getDefaultSelectorType(selectorKind);
                boolean defaultFound = defaultSelectorType != null && !defaultSelectorType.equals(selectorType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    SelectorTypeDetailValue defaultSelectorTypeDetailValue = getDefaultSelectorTypeDetailValueForUpdate(selectorKind);

                    defaultSelectorTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updateSelectorTypeFromValue(defaultSelectorTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            selectorTypeDetail = SelectorTypeDetailFactory.getInstance().create(selectorTypePK, selectorKindPK, selectorTypeName, isDefault, sortOrder,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            selectorType.setActiveDetail(selectorTypeDetail);
            selectorType.setLastDetail(selectorTypeDetail);

            sendEventUsingNames(selectorTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void updateSelectorTypeFromValue(SelectorTypeDetailValue selectorTypeDetailValue, BasePK updatedBy) {
        updateSelectorTypeFromValue(selectorTypeDetailValue, true, updatedBy);
    }

    public void deleteSelectorType(SelectorType selectorType, BasePK deletedBy) {
        deleteSelectorTypeDescriptionsBySelectorType(selectorType, deletedBy);
        deleteSelectorsBySelectorType(selectorType, deletedBy);

        SelectorTypeDetail selectorTypeDetail = selectorType.getLastDetailForUpdate();
        selectorTypeDetail.setThruTime(session.START_TIME_LONG);
        selectorType.setActiveDetail(null);
        selectorType.store();

        // Check for default, and pick one if necessary
        SelectorKind selectorKind = selectorTypeDetail.getSelectorKind();
        SelectorType defaultSelectorType = getDefaultSelectorType(selectorKind);
        if(defaultSelectorType == null) {
            List<SelectorType> selectorTypes = getSelectorTypesForUpdate(selectorKind);

            if(!selectorTypes.isEmpty()) {
                Iterator<SelectorType> iter = selectorTypes.iterator();
                if(iter.hasNext()) {
                    defaultSelectorType = iter.next();
                }
                SelectorTypeDetailValue selectorTypeDetailValue = Objects.requireNonNull(defaultSelectorType).getLastDetailForUpdate().getSelectorTypeDetailValue().clone();

                selectorTypeDetailValue.setIsDefault(Boolean.TRUE);
                updateSelectorTypeFromValue(selectorTypeDetailValue, false, deletedBy);
            }
        }

        sendEventUsingNames(selectorType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    public void deleteSelectorTypesBySelectorKind(SelectorKind selectorKind, BasePK deletedBy) {
        List<SelectorType> selectorTypes = getSelectorTypesForUpdate(selectorKind);

        selectorTypes.forEach((selectorType) -> 
                deleteSelectorType(selectorType, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Selector Type Descriptions
    // --------------------------------------------------------------------------------

    public SelectorTypeDescription createSelectorTypeDescription(SelectorType selectorType, Language language, String description,
            BasePK createdBy) {
        SelectorTypeDescription selectorTypeDescription = SelectorTypeDescriptionFactory.getInstance().create(selectorType,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(selectorType.getPrimaryKey(), EventTypes.MODIFY.name(), selectorTypeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return selectorTypeDescription;
    }

    private static final Map<EntityPermission, String> getSelectorTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM selectortypedescriptions "
                + "WHERE sltd_slt_selectortypeid = ? AND sltd_lang_languageid = ? AND sltd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM selectortypedescriptions "
                + "WHERE sltd_slt_selectortypeid = ? AND sltd_lang_languageid = ? AND sltd_thrutime = ? "
                + "FOR UPDATE");
        getSelectorTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private SelectorTypeDescription getSelectorTypeDescription(SelectorType selectorType, Language language, EntityPermission entityPermission) {
        return SelectorTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getSelectorTypeDescriptionQueries,
                selectorType, language, Session.MAX_TIME);
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

    private static final Map<EntityPermission, String> getSelectorTypeDescriptionsBySelectorTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM selectortypedescriptions, languages "
                + "WHERE sltd_slt_selectortypeid = ? AND sltd_thrutime = ? AND sltd_lang_languageid = lang_languageid "
                + "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM selectortypedescriptions "
                + "WHERE sltd_slt_selectortypeid = ? AND sltd_thrutime = ? "
                + "FOR UPDATE");
        getSelectorTypeDescriptionsBySelectorTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<SelectorTypeDescription> getSelectorTypeDescriptionsBySelectorType(SelectorType selectorType, EntityPermission entityPermission) {
        return SelectorTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getSelectorTypeDescriptionsBySelectorTypeQueries,
                selectorType, Session.MAX_TIME);
    }

    public List<SelectorTypeDescription> getSelectorTypeDescriptionsBySelectorType(SelectorType selectorType) {
        return getSelectorTypeDescriptionsBySelectorType(selectorType, EntityPermission.READ_ONLY);
    }

    public List<SelectorTypeDescription> getSelectorTypeDescriptionsBySelectorTypeForUpdate(SelectorType selectorType) {
        return getSelectorTypeDescriptionsBySelectorType(selectorType, EntityPermission.READ_WRITE);
    }

    public String getBestSelectorTypeDescription(SelectorType selectorType, Language language) {
        String description;
        SelectorTypeDescription selectorTypeDescription = getSelectorTypeDescription(selectorType, language);

        if(selectorTypeDescription == null && !language.getIsDefault()) {
            selectorTypeDescription = getSelectorTypeDescription(selectorType, getPartyControl().getDefaultLanguage());
        }

        if(selectorTypeDescription == null) {
            description = selectorType.getLastDetail().getSelectorTypeName();
        } else {
            description = selectorTypeDescription.getDescription();
        }

        return description;
    }

    public SelectorTypeDescriptionTransfer getSelectorTypeDescriptionTransfer(UserVisit userVisit, SelectorTypeDescription selectorTypeDescription) {
        return getSelectorTransferCaches(userVisit).getSelectorTypeDescriptionTransferCache().getSelectorTypeDescriptionTransfer(selectorTypeDescription);
    }

    public List<SelectorTypeDescriptionTransfer> getSelectorTypeDescriptionTransfersBySelectorType(UserVisit userVisit, SelectorType selectorType) {
        List<SelectorTypeDescription> selectorTypeDescriptions = getSelectorTypeDescriptionsBySelectorType(selectorType);
        List<SelectorTypeDescriptionTransfer> selectorTypeDescriptionTransfers = new ArrayList<>(selectorTypeDescriptions.size());

        selectorTypeDescriptions.forEach((selectorTypeDescription) -> {
            selectorTypeDescriptionTransfers.add(getSelectorTransferCaches(userVisit).getSelectorTypeDescriptionTransferCache().getSelectorTypeDescriptionTransfer(selectorTypeDescription));
        });

        return selectorTypeDescriptionTransfers;
    }

    public void updateSelectorTypeDescriptionFromValue(SelectorTypeDescriptionValue selectorTypeDescriptionValue, BasePK updatedBy) {
        if(selectorTypeDescriptionValue.hasBeenModified()) {
            SelectorTypeDescription selectorTypeDescription = SelectorTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     selectorTypeDescriptionValue.getPrimaryKey());

            selectorTypeDescription.setThruTime(session.START_TIME_LONG);
            selectorTypeDescription.store();

            SelectorType selectorType = selectorTypeDescription.getSelectorType();
            Language language = selectorTypeDescription.getLanguage();
            String description = selectorTypeDescriptionValue.getDescription();

            selectorTypeDescription = SelectorTypeDescriptionFactory.getInstance().create(selectorType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(selectorType.getPrimaryKey(), EventTypes.MODIFY.name(), selectorTypeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteSelectorTypeDescription(SelectorTypeDescription selectorTypeDescription, BasePK deletedBy) {
        selectorTypeDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(selectorTypeDescription.getSelectorTypePK(), EventTypes.MODIFY.name(), selectorTypeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteSelectorTypeDescriptionsBySelectorType(SelectorType selectorType, BasePK deletedBy) {
        List<SelectorTypeDescription> selectorTypeDescriptions = getSelectorTypeDescriptionsBySelectorTypeForUpdate(selectorType);

        selectorTypeDescriptions.forEach((selectorTypeDescription) -> 
                deleteSelectorTypeDescription(selectorTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Selector Boolean Types
    // --------------------------------------------------------------------------------
    
    public SelectorBooleanType createSelectorBooleanType(String selectorBooleanTypeName, Boolean isDefault, Integer sortOrder) {
        SelectorBooleanType selectorBooleanType = SelectorBooleanTypeFactory.getInstance().create(selectorBooleanTypeName,
                isDefault, sortOrder);
        
        return selectorBooleanType;
    }
    
    public List<SelectorBooleanType> getSelectorBooleanTypes() {
        List<SelectorBooleanType> selectorBooleanTypes;
        PreparedStatement ps = SelectorBooleanTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM selectorbooleantypes " +
                "ORDER BY slbt_sortorder, slbt_selectorbooleantypename");
        
        selectorBooleanTypes = SelectorBooleanTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
        
        return selectorBooleanTypes;
    }
    
    public SelectorBooleanType getSelectorBooleanTypeByName(String selectorBooleanTypeName) {
        SelectorBooleanType selectorBooleanType;
        
        try {
            PreparedStatement ps = SelectorBooleanTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM selectorbooleantypes " +
                    "WHERE slbt_selectorbooleantypename = ?");
            
            ps.setString(1, selectorBooleanTypeName);
            
            selectorBooleanType = SelectorBooleanTypeFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return selectorBooleanType;
    }
    
    public SelectorBooleanTypeChoicesBean getSelectorBooleanTypeChoices(String defaultSelectorBooleanTypeChoice, Language language,
            boolean allowNullChoice) {
        List<SelectorBooleanType> selectorBooleanTypes = getSelectorBooleanTypes();
        int size = selectorBooleanTypes.size() + (allowNullChoice? 1: 0);
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultSelectorBooleanTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var selectorBooleanType : selectorBooleanTypes) {
            String label = getBestSelectorBooleanTypeDescription(selectorBooleanType, language);
            String value = selectorBooleanType.getSelectorBooleanTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultSelectorBooleanTypeChoice != null && defaultSelectorBooleanTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && selectorBooleanType.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new SelectorBooleanTypeChoicesBean(labels, values, defaultValue);
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Boolean Type Descriptions
    // --------------------------------------------------------------------------------
    
    public SelectorBooleanTypeDescription createSelectorBooleanTypeDescription(SelectorBooleanType selectorBooleanType,
            Language language, String description) {
        SelectorBooleanTypeDescription selectorBooleanTypeDescription = SelectorBooleanTypeDescriptionFactory.getInstance().create(session,
                selectorBooleanType, language, description);
        
        return selectorBooleanTypeDescription;
    }
    
    public SelectorBooleanTypeDescription getSelectorBooleanTypeDescription(SelectorBooleanType selectorBooleanType, Language language) {
        SelectorBooleanTypeDescription selectorBooleanTypeDescription;
        
        try {
            PreparedStatement ps = SelectorBooleanTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM selectorbooleantypedescriptions " +
                    "WHERE slbtd_slbt_selectorbooleantypeid = ? AND slbtd_lang_languageid = ?");
            
            ps.setLong(1, selectorBooleanType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            selectorBooleanTypeDescription = SelectorBooleanTypeDescriptionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return selectorBooleanTypeDescription;
    }
    
    public String getBestSelectorBooleanTypeDescription(SelectorBooleanType selectorBooleanType, Language language) {
        String description;
        SelectorBooleanTypeDescription selectorBooleanTypeDescription = getSelectorBooleanTypeDescription(selectorBooleanType, language);
        
        if(selectorBooleanTypeDescription == null && !language.getIsDefault()) {
            selectorBooleanTypeDescription = getSelectorBooleanTypeDescription(selectorBooleanType, getPartyControl().getDefaultLanguage());
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
    
    public SelectorComparisonType createSelectorComparisonType(String selectorComparisonTypeName, Boolean isDefault,
            Integer sortOrder) {
        SelectorComparisonType selectorComparisonType = SelectorComparisonTypeFactory.getInstance().create(session,
                selectorComparisonTypeName, isDefault, sortOrder);
        
        return selectorComparisonType;
    }
    
    public List<SelectorComparisonType> getSelectorComparisonTypes() {
        PreparedStatement ps = SelectorComparisonTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM selectorcomparisontypes " +
                "ORDER BY slct_sortorder, slct_selectorcomparisontypename");
        
        return SelectorComparisonTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public SelectorComparisonType getSelectorComparisonTypeByName(String selectorComparisonTypeName) {
        SelectorComparisonType selectorComparisonType;
        
        try {
            PreparedStatement ps = SelectorComparisonTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM selectorcomparisontypes " +
                    "WHERE slct_selectorcomparisontypename = ?");
            
            ps.setString(1, selectorComparisonTypeName);
            
            selectorComparisonType = SelectorComparisonTypeFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return selectorComparisonType;
    }
    
    public SelectorComparisonTypeChoicesBean getSelectorComparisonTypeChoices(String defaultSelectorComparisonTypeChoice,
            Language language, boolean allowNullChoice) {
        List<SelectorComparisonType> selectorComparisonTypes = getSelectorComparisonTypes();
        int size = selectorComparisonTypes.size() + (allowNullChoice? 1: 0);
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultSelectorComparisonTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var selectorComparisonType : selectorComparisonTypes) {
            String label = getBestSelectorComparisonTypeDescription(selectorComparisonType, language);
            String value = selectorComparisonType.getSelectorComparisonTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultSelectorComparisonTypeChoice != null && defaultSelectorComparisonTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && selectorComparisonType.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new SelectorComparisonTypeChoicesBean(labels, values, defaultValue);
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Comparison Type Descriptions
    // --------------------------------------------------------------------------------
    
    public SelectorComparisonTypeDescription createSelectorComparisonTypeDescription(SelectorComparisonType selectorComparisonType,
            Language language, String description) {
        SelectorComparisonTypeDescription selectorComparisonTypeDescription = SelectorComparisonTypeDescriptionFactory.getInstance().create(selectorComparisonType, language, description);
        
        return selectorComparisonTypeDescription;
    }
    
    public SelectorComparisonTypeDescription getSelectorComparisonTypeDescription(SelectorComparisonType selectorComparisonType, Language language) {
        SelectorComparisonTypeDescription selectorComparisonTypeDescription;
        
        try {
            PreparedStatement ps = SelectorComparisonTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM selectorcomparisontypedescriptions " +
                    "WHERE slctd_slct_selectorcomparisontypeid = ? AND slctd_lang_languageid = ?");
            
            ps.setLong(1, selectorComparisonType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            selectorComparisonTypeDescription = SelectorComparisonTypeDescriptionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return selectorComparisonTypeDescription;
    }
    
    public String getBestSelectorComparisonTypeDescription(SelectorComparisonType selectorComparisonType, Language language) {
        String description;
        SelectorComparisonTypeDescription selectorComparisonTypeDescription = getSelectorComparisonTypeDescription(selectorComparisonType, language);
        
        if(selectorComparisonTypeDescription == null && !language.getIsDefault()) {
            selectorComparisonTypeDescription = getSelectorComparisonTypeDescription(selectorComparisonType, getPartyControl().getDefaultLanguage());
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
    
    public SelectorNodeType createSelectorNodeType(String selectorNodeTypeName, Integer sortOrder) {
        return SelectorNodeTypeFactory.getInstance().create(selectorNodeTypeName, sortOrder);
    }
    
    /** Assume that the entityInstance passed to this function is a ECHOTHREE.SelectorNodeType */
    public SelectorNodeType getSelectorNodeTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new SelectorNodeTypePK(entityInstance.getEntityUniqueId());

        return SelectorNodeTypeFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public SelectorNodeType getSelectorNodeTypeByEntityInstance(EntityInstance entityInstance) {
        return getSelectorNodeTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public SelectorNodeType getSelectorNodeTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getSelectorNodeTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    public SelectorNodeType getSelectorNodeTypeByName(String selectorNodeTypeName) {
        SelectorNodeType selectorNodeType;
        
        try {
            PreparedStatement ps = SelectorNodeTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM selectornodetypes " +
                    "WHERE slnt_selectornodetypename = ?");
            
            ps.setString(1, selectorNodeTypeName);
            
            selectorNodeType = SelectorNodeTypeFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return selectorNodeType;
    }
    
    public List<SelectorNodeType> getSelectorNodeTypes() {
        PreparedStatement ps = SelectorNodeTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM selectornodetypes " +
                "ORDER BY slnt_sortorder, slnt_selectornodetypename");
        
        return SelectorNodeTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public List<SelectorNodeType> getSelectorNodeTypesBySelectorKind(SelectorKind selectorKind) {
        List<SelectorNodeType> selectorNodeTypes;
        
        try {
            PreparedStatement ps = SelectorNodeTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM selectornodetypes, selectornodetypeuses " +
                    "WHERE slnt_selectornodetypeid = slntu_slnt_selectornodetypeid AND slntu_slk_selectorkindid = ? " +
                    "ORDER BY slnt_sortorder, slnt_selectornodetypename");
            
            ps.setLong(1, selectorKind.getPrimaryKey().getEntityId());
            
            selectorNodeTypes = SelectorNodeTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return selectorNodeTypes;
    }
    
    public SelectorNodeTypeTransfer getSelectorNodeTypeTransfer(UserVisit userVisit, SelectorNodeType selectorNodeType) {
        return getSelectorTransferCaches(userVisit).getSelectorNodeTypeTransferCache().getSelectorNodeTypeTransfer(selectorNodeType);
    }
    
    public List<SelectorNodeTypeTransfer> getSelectorNodeTypeTransfers(UserVisit userVisit, List<SelectorNodeType> selectorNodeTypes) {
        List<SelectorNodeTypeTransfer> selectorNodeTypeTransfers = new ArrayList<>(selectorNodeTypes.size());
        SelectorNodeTypeTransferCache selectorNodeTypeTransferCache = getSelectorTransferCaches(userVisit).getSelectorNodeTypeTransferCache();
        
        selectorNodeTypes.forEach((selectorNodeType) ->
                selectorNodeTypeTransfers.add(selectorNodeTypeTransferCache.getSelectorNodeTypeTransfer(selectorNodeType))
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
    //   Selector Node Type Uses
    // --------------------------------------------------------------------------------
    
    public SelectorNodeTypeUse createSelectorNodeTypeUse(SelectorKind selectorKind, SelectorNodeType selectorNodeType,
            Boolean isDefault) {
        return SelectorNodeTypeUseFactory.getInstance().create(selectorKind, selectorNodeType, isDefault);
    }
    
    public SelectorNodeTypeUse getSelectorNodeTypeUse(SelectorKind selectorKind, SelectorNodeType selectorNodeType) {
        SelectorNodeTypeUse selectorNodeTypeUse;
        
        try {
            PreparedStatement ps = SelectorNodeTypeUseFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM selectornodetypeuses " +
                    "WHERE slntu_slk_selectorkindid = ? AND slntu_slnt_selectornodetypeid = ?");
            
            ps.setLong(1, selectorKind.getPrimaryKey().getEntityId());
            ps.setLong(2, selectorNodeType.getPrimaryKey().getEntityId());
            
            selectorNodeTypeUse = SelectorNodeTypeUseFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return selectorNodeTypeUse;
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Node Type Descriptions
    // --------------------------------------------------------------------------------
    
    public SelectorNodeTypeDescription createSelectorNodeTypeDescription(SelectorNodeType selectorNodeType, Language language, String description) {
        SelectorNodeTypeDescription selectorNodeTypeDescription = SelectorNodeTypeDescriptionFactory.getInstance().create(selectorNodeType, language, description);
        
        return selectorNodeTypeDescription;
    }
    
    public SelectorNodeTypeDescription getSelectorNodeTypeDescription(SelectorNodeType selectorNodeType, Language language) {
        SelectorNodeTypeDescription selectorNodeTypeDescription;
        
        try {
            PreparedStatement ps = SelectorNodeTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM selectornodetypedescriptions " +
                    "WHERE slntd_slnt_selectornodetypeid = ? AND slntd_lang_languageid = ?");
            
            ps.setLong(1, selectorNodeType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            selectorNodeTypeDescription = SelectorNodeTypeDescriptionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return selectorNodeTypeDescription;
    }
    
    public String getBestSelectorNodeTypeDescription(SelectorNodeType selectorNodeType, Language language) {
        String description;
        SelectorNodeTypeDescription selectorNodeTypeDescription = getSelectorNodeTypeDescription(selectorNodeType, language);
        
        if(selectorNodeTypeDescription == null && !language.getIsDefault()) {
            selectorNodeTypeDescription = getSelectorNodeTypeDescription(selectorNodeType, getPartyControl().getDefaultLanguage());
        }
        
        if(selectorNodeTypeDescription == null) {
            description = selectorNodeType.getSelectorNodeTypeName();
        } else {
            description = selectorNodeTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Text Search Types
    // --------------------------------------------------------------------------------
    
    public SelectorTextSearchType createSelectorTextSearchType(String selectorTextSearchTypeName, Boolean isDefault,
            Integer sortOrder) {
        SelectorTextSearchType selectorTextSearchType = SelectorTextSearchTypeFactory.getInstance().create(session,
                selectorTextSearchTypeName, isDefault, sortOrder);
        
        return selectorTextSearchType;
    }
    
    public List<SelectorTextSearchType> getSelectorTextSearchTypes() {
        PreparedStatement ps = SelectorTextSearchTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM selectortextsearchtypes " +
                "ORDER BY sltst_sortorder, sltst_selectortextsearchtypename");
        
        return SelectorTextSearchTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public SelectorTextSearchType getSelectorTextSearchTypeByName(String selectorTextSearchTypeName) {
        SelectorTextSearchType selectorTextSearchType;
        
        try {
            PreparedStatement ps = SelectorTextSearchTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM selectortextsearchtypes " +
                    "WHERE sltst_selectortextsearchtypename = ?");
            
            ps.setString(1, selectorTextSearchTypeName);
            
            selectorTextSearchType = SelectorTextSearchTypeFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return selectorTextSearchType;
    }
    
    public SelectorTextSearchTypeChoicesBean getSelectorTextSearchTypeChoices(String defaultSelectorTextSearchTypeChoice,
            Language language, boolean allowNullChoice) {
        List<SelectorTextSearchType> selectorTextSearchTypes = getSelectorTextSearchTypes();
        int size = selectorTextSearchTypes.size() + (allowNullChoice? 1: 0);
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultSelectorTextSearchTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var selectorTextSearchType : selectorTextSearchTypes) {
            String label = getBestSelectorTextSearchTypeDescription(selectorTextSearchType, language);
            String value = selectorTextSearchType.getSelectorTextSearchTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultSelectorTextSearchTypeChoice != null && defaultSelectorTextSearchTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && selectorTextSearchType.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new SelectorTextSearchTypeChoicesBean(labels, values, defaultValue);
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Text Search Type Descriptions
    // --------------------------------------------------------------------------------
    
    public SelectorTextSearchTypeDescription createSelectorTextSearchTypeDescription(SelectorTextSearchType selectorTextSearchType, Language language, String description) {
        SelectorTextSearchTypeDescription selectorTextSearchTypeDescription = SelectorTextSearchTypeDescriptionFactory.getInstance().create(selectorTextSearchType, language, description);
        
        return selectorTextSearchTypeDescription;
    }
    
    public SelectorTextSearchTypeDescription getSelectorTextSearchTypeDescription(SelectorTextSearchType selectorTextSearchType, Language language) {
        SelectorTextSearchTypeDescription selectorTextSearchTypeDescription;
        
        try {
            PreparedStatement ps = SelectorTextSearchTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM selectortextsearchtypedescriptions " +
                    "WHERE sltstd_sltst_selectortextsearchtypeid = ? AND sltstd_lang_languageid = ?");
            
            ps.setLong(1, selectorTextSearchType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            selectorTextSearchTypeDescription = SelectorTextSearchTypeDescriptionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return selectorTextSearchTypeDescription;
    }
    
    public String getBestSelectorTextSearchTypeDescription(SelectorTextSearchType selectorTextSearchType, Language language) {
        String description;
        SelectorTextSearchTypeDescription selectorTextSearchTypeDescription = getSelectorTextSearchTypeDescription(selectorTextSearchType, language);
        
        if(selectorTextSearchTypeDescription == null && !language.getIsDefault()) {
            selectorTextSearchTypeDescription = getSelectorTextSearchTypeDescription(selectorTextSearchType, getPartyControl().getDefaultLanguage());
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
    
    public Selector createSelector(SelectorType selectorType, String selectorName, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        Selector defaultSelector = getDefaultSelector(selectorType);
        boolean defaultFound = defaultSelector != null;
        
        if(defaultFound && isDefault) {
            SelectorDetailValue defaultSelectorDetailValue = getDefaultSelectorDetailValueForUpdate(selectorType);
            
            defaultSelectorDetailValue.setIsDefault(Boolean.FALSE);
            updateSelectorFromValue(defaultSelectorDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        Selector selector = SelectorFactory.getInstance().create();
        SelectorDetail selectorDetail = SelectorDetailFactory.getInstance().create(selector, selectorType, selectorName,
                isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        selector = SelectorFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, selector.getPrimaryKey());
        selector.setActiveDetail(selectorDetail);
        selector.setLastDetail(selectorDetail);
        selector.store();
        
        sendEventUsingNames(selector.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return selector;
    }
    
    private Selector getSelectorByName(SelectorType selectorType, String selectorName, EntityPermission entityPermission) {
        Selector selector;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM selectors, selectordetails " +
                        "WHERE sl_activedetailid = sldt_selectordetailid AND sldt_slt_selectortypeid = ? AND sldt_selectorname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM selectors, selectordetails " +
                        "WHERE sl_activedetailid = sldt_selectordetailid AND sldt_slt_selectortypeid = ? AND sldt_selectorname = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SelectorFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, selectorType.getPrimaryKey().getEntityId());
            ps.setString(2, selectorName);
            
            selector = SelectorFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return selector;
    }
    
    public Selector getSelectorByName(SelectorType selectorType, String selectorName) {
        return getSelectorByName(selectorType, selectorName, EntityPermission.READ_ONLY);
    }
    
    public Selector getSelectorByNameForUpdate(SelectorType selectorType, String selectorName) {
        return getSelectorByName(selectorType, selectorName, EntityPermission.READ_WRITE);
    }

    public Selector getSelectorUsingNames(ExecutionErrorAccumulator eea, String selectorKindName, String selectorTypeName, String selectorName,
            String unknownSelectorNameError) {
        SelectorKind selectorKind = getSelectorKindByName(selectorKindName);
        Selector result = null;

        if(selectorKind != null) {
            SelectorType selectorType = getSelectorTypeByName(selectorKind, selectorTypeName);

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
    
    private Selector getDefaultSelector(SelectorType selectorType, EntityPermission entityPermission) {
        Selector selector;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM selectors, selectordetails " +
                        "WHERE sl_activedetailid = sldt_selectordetailid AND sldt_slt_selectortypeid = ? AND sldt_isdefault = 1";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM selectors, selectordetails " +
                        "WHERE sl_activedetailid = sldt_selectordetailid AND sldt_slt_selectortypeid = ? AND sldt_isdefault = 1 " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SelectorFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, selectorType.getPrimaryKey().getEntityId());
            
            selector = SelectorFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return selector;
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
        List<Selector> selectors;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM selectors, selectordetails " +
                        "WHERE sl_activedetailid = sldt_selectordetailid AND sldt_slt_selectortypeid = ? " +
                        "ORDER BY sldt_sortorder, sldt_selectorname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM selectors, selectordetails " +
                        "WHERE sl_activedetailid = sldt_selectordetailid AND sldt_slt_selectortypeid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SelectorFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, selectorType.getPrimaryKey().getEntityId());
            
            selectors = SelectorFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return selectors;
    }
    
    public List<Selector> getSelectorsBySelectorType(SelectorType selectorType) {
        return getSelectorsBySelectorType(selectorType, EntityPermission.READ_ONLY);
    }
    
    public List<Selector> getSelectorsBySelectorTypeForUpdate(SelectorType selectorType) {
        return getSelectorsBySelectorType(selectorType, EntityPermission.READ_WRITE);
    }
    
    public List<Selector> getSelectorsBySelectorKind(SelectorKind selectorKind) {
        List<Selector> selectors;
        
        try {
            PreparedStatement ps = SelectorFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ "
                    + "FROM selectors, selectordetails, selectortypes, selectortypedetails "
                    + "WHERE sl_activedetailid = sldt_selectordetailid AND sldt_slt_selectortypeid = slt_selectortypeid "
                    + "AND slt_activedetailid = sltdt_selectortypedetailid "
                    + "AND sltdt_slk_selectorkindid = ? "
                    + "ORDER BY sltdt_sortorder, sltdt_selectortypename, sldt_sortorder, sldt_selectorname");
            
            ps.setLong(1, selectorKind.getPrimaryKey().getEntityId());
            
            selectors = SelectorFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return selectors;
    }
    
    public SelectorChoicesBean getSelectorChoices(SelectorType selectorType, String defaultSelectorChoice, Language language,
            boolean allowNullChoice) {
        List<Selector> selectors = getSelectorsBySelectorType(selectorType);
        int size = selectors.size() + (allowNullChoice? 1: 0);
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultSelectorChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var selector : selectors) {
            SelectorDetail selectorDetail = selector.getLastDetail();
            
            String label = getBestSelectorDescription(selector, language);
            String value = selector.getLastDetail().getSelectorName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultSelectorChoice != null && defaultSelectorChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && selectorDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new SelectorChoicesBean(labels, values, defaultValue);
    }
    
    public SelectorTransfer getSelectorTransfer(UserVisit userVisit, Selector selector) {
        return getSelectorTransferCaches(userVisit).getSelectorTransferCache().getSelectorTransfer(selector);
    }
    
    private List<SelectorTransfer> getSelectorTransfers(UserVisit userVisit, List<Selector> selectors) {
        List<SelectorTransfer> selectorTransfers = new ArrayList<>(selectors.size());
        SelectorTransferCache selectorTransferCache = getSelectorTransferCaches(userVisit).getSelectorTransferCache();
        
        selectors.forEach((selector) ->
                selectorTransfers.add(selectorTransferCache.getSelectorTransfer(selector))
        );
        
        return selectorTransfers;
    }
    
    public List<SelectorTransfer> getSelectorTransfersBySelectorType(UserVisit userVisit, SelectorType selectorType) {
        return getSelectorTransfers(userVisit, getSelectorsBySelectorType(selectorType));
    }
    
    private void updateSelectorFromValue(SelectorDetailValue selectorDetailValue, boolean checkDefault, BasePK updatedBy) {
        Selector selector = SelectorFactory.getInstance().getEntityFromPK(session,
                EntityPermission.READ_WRITE, selectorDetailValue.getSelectorPK());
        SelectorDetail selectorDetail = selector.getActiveDetailForUpdate();
        
        selectorDetail.setThruTime(session.START_TIME_LONG);
        selectorDetail.store();
        
        SelectorPK selectorPK = selectorDetail.getSelectorPK();
        SelectorType selectorType = selectorDetail.getSelectorType(); // Not updated
        String selectorName = selectorDetailValue.getSelectorName();
        Boolean isDefault = selectorDetailValue.getIsDefault();
        Integer sortOrder = selectorDetailValue.getSortOrder();
        
        if(checkDefault) {
            Selector defaultSelector = getDefaultSelector(selectorType);
            boolean defaultFound = defaultSelector != null && !defaultSelector.equals(selector);
            
            if(isDefault && defaultFound) {
                // If I'm the default, and a default already existed...
                SelectorDetailValue defaultSelectorDetailValue = getDefaultSelectorDetailValueForUpdate(selectorType);
                
                defaultSelectorDetailValue.setIsDefault(Boolean.FALSE);
                updateSelectorFromValue(defaultSelectorDetailValue, false, updatedBy);
            } else if(!isDefault && !defaultFound) {
                // If I'm not the default, and no other default exists...
                isDefault = Boolean.TRUE;
            }
        }
        
        selectorDetail = SelectorDetailFactory.getInstance().create(selectorPK, selectorType.getPrimaryKey(),
                selectorName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        selector.setActiveDetail(selectorDetail);
        selector.setLastDetail(selectorDetail);
        selector.store();
        
        sendEventUsingNames(selectorPK, EventTypes.MODIFY.name(), null, null, updatedBy);
    }
    
    public void updateSelectorFromValue(SelectorDetailValue selectorDetailValue, BasePK updatedBy) {
        updateSelectorFromValue(selectorDetailValue, true, updatedBy);
    }
    
    public void deleteSelector(Selector selector, BasePK deletedBy) {
        var securityControl = Session.getModelController(SecurityControl.class);
        
        securityControl.deleteSecurityRolePartyTypesBySelector(selector, deletedBy);
        deleteSelectorNodesBySelector(selector, deletedBy);
        deleteSelectorDescriptionsBySelector(selector, deletedBy);

        removeSelectorTimeBySelector(selector);
        
        SelectorDetail selectorDetail = selector.getLastDetailForUpdate();
        selectorDetail.setThruTime(session.START_TIME_LONG);
        selector.setActiveDetail(null);
        selector.store();
        
        // Check for default, and pick one if necessary
        SelectorType selectorType = selectorDetail.getSelectorType();
        Selector defaultSelector = getDefaultSelector(selectorType);
        if(defaultSelector == null) {
            List<Selector> Selectors = getSelectorsBySelectorTypeForUpdate(selectorType);
            
            if(!Selectors.isEmpty()) {
                Iterator<Selector> iter = Selectors.iterator();
                if(iter.hasNext()) {
                    defaultSelector = iter.next();
                }
                SelectorDetailValue selectorDetailValue = Objects.requireNonNull(defaultSelector).getLastDetailForUpdate().getSelectorDetailValue().clone();
                
                selectorDetailValue.setIsDefault(Boolean.TRUE);
                updateSelectorFromValue(selectorDetailValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(selector.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
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
    
    public SelectorDescription createSelectorDescription(Selector selector, Language language, String description, BasePK createdBy) {
        SelectorDescription selectorDescription = SelectorDescriptionFactory.getInstance().create(selector, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(selector.getPrimaryKey(), EventTypes.MODIFY.name(), selectorDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return selectorDescription;
    }
    
    private SelectorDescription getSelectorDescription(Selector selector, Language language, EntityPermission entityPermission) {
        SelectorDescription selectorDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM selectordescriptions " +
                        "WHERE sld_sl_selectorid = ? AND sld_lang_languageid = ? AND sld_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM selectordescriptions " +
                        "WHERE sld_sl_selectorid = ? AND sld_lang_languageid = ? AND sld_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SelectorDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, selector.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            selectorDescription = SelectorDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return selectorDescription;
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
        SelectorDescription selectorDescription = getSelectorDescriptionForUpdate(selector, language);
        
        return selectorDescription == null? null: selectorDescription.getSelectorDescriptionValue().clone();
    }
    
    private List<SelectorDescription> getSelectorDescriptionsBySelector(Selector selector, EntityPermission entityPermission) {
        List<SelectorDescription> selectorDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM selectordescriptions, languages " +
                        "WHERE sld_sl_selectorid = ? AND sld_thrutime = ? AND sld_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM selectordescriptions " +
                        "WHERE sld_sl_selectorid = ? AND sld_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SelectorDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, selector.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            selectorDescriptions = SelectorDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return selectorDescriptions;
    }
    
    public List<SelectorDescription> getSelectorDescriptionsBySelector(Selector selector) {
        return getSelectorDescriptionsBySelector(selector, EntityPermission.READ_ONLY);
    }
    
    public List<SelectorDescription> getSelectorDescriptionsBySelectorForUpdate(Selector selector) {
        return getSelectorDescriptionsBySelector(selector, EntityPermission.READ_WRITE);
    }
    
    public String getBestSelectorDescription(Selector selector, Language language) {
        String description;
        SelectorDescription selectorDescription = getSelectorDescription(selector, language);
        
        if(selectorDescription == null && !language.getIsDefault()) {
            selectorDescription = getSelectorDescription(selector, getPartyControl().getDefaultLanguage());
        }
        
        if(selectorDescription == null) {
            description = selector.getLastDetail().getSelectorName();
        } else {
            description = selectorDescription.getDescription();
        }
        
        return description;
    }
    
    public SelectorDescriptionTransfer getSelectorDescriptionTransfer(UserVisit userVisit, SelectorDescription selectorDescription) {
        return getSelectorTransferCaches(userVisit).getSelectorDescriptionTransferCache().getSelectorDescriptionTransfer(selectorDescription);
    }
    
    public List<SelectorDescriptionTransfer> getSelectorDescriptionTransfers(UserVisit userVisit, Selector selector) {
        List<SelectorDescription> selectorDescriptions = getSelectorDescriptionsBySelector(selector);
        List<SelectorDescriptionTransfer> selectorDescriptionTransfers = new ArrayList<>(selectorDescriptions.size());
        
        selectorDescriptions.forEach((selectorDescription) -> {
            selectorDescriptionTransfers.add(getSelectorTransferCaches(userVisit).getSelectorDescriptionTransferCache().getSelectorDescriptionTransfer(selectorDescription));
        });
        
        return selectorDescriptionTransfers;
    }
    
    public void updateSelectorDescriptionFromValue(SelectorDescriptionValue selectorDescriptionValue, BasePK updatedBy) {
        if(selectorDescriptionValue.hasBeenModified()) {
            SelectorDescription selectorDescription = SelectorDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, selectorDescriptionValue.getPrimaryKey());
            
            selectorDescription.setThruTime(session.START_TIME_LONG);
            selectorDescription.store();
            
            Selector selector = selectorDescription.getSelector();
            Language language = selectorDescription.getLanguage();
            String description = selectorDescriptionValue.getDescription();
            
            selectorDescription = SelectorDescriptionFactory.getInstance().create(selector, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(selector.getPrimaryKey(), EventTypes.MODIFY.name(), selectorDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteSelectorDescription(SelectorDescription selectorDescription, BasePK deletedBy) {
        selectorDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(selectorDescription.getSelectorPK(), EventTypes.MODIFY.name(), selectorDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
        
    }
    
    public void deleteSelectorDescriptionsBySelector(Selector selector, BasePK deletedBy) {
        List<SelectorDescription> selectorDescriptions = getSelectorDescriptionsBySelectorForUpdate(selector);
        
        selectorDescriptions.forEach((selectorDescription) -> 
                deleteSelectorDescription(selectorDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Times
    // --------------------------------------------------------------------------------
    
    public SelectorTime createSelectorTime(Selector selector) {
        return SelectorTimeFactory.getInstance().create(selector, null, null, null, null);
    }

    private static final Map<EntityPermission, String> getSelectorTimeQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM selectortimes " +
                    "WHERE sltm_sl_selectorid = ?",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " +
                    "FROM selectortimes " +
                    "WHERE sltm_sl_selectorid = ? " +
                    "FOR UPDATE"
    );

    private SelectorTime getSelectorTime(Selector selector, EntityPermission entityPermission) {
        return SelectorTimeFactory.getInstance().getEntityFromQuery(entityPermission, getSelectorTimeQueries, selector);
    }

    public SelectorTime getSelectorTime(Selector selector) {
        return getSelectorTime(selector, EntityPermission.READ_ONLY);
    }

    public SelectorTime getSelectorTimeForUpdate(Selector selector) {
        return getSelectorTime(selector, EntityPermission.READ_WRITE);
    }

    public void removeSelectorTimeBySelector(Selector selector) {
        SelectorTime selectorTime = getSelectorTimeForUpdate(selector);

        if(selectorTime != null) {
            selectorTime.remove();
        }
    }

    // --------------------------------------------------------------------------------
    //   Selector Nodes
    // --------------------------------------------------------------------------------
    
    public SelectorNode createSelectorNode(Selector selector, String selectorNodeName, Boolean isRootSelectorNode,
            SelectorNodeType selectorNodeType, Boolean negate, BasePK createdBy) {
        SelectorNode rootSelectorNode = getRootSelectorNode(selector);
        boolean rootFound = rootSelectorNode != null;
        
        if(rootFound && isRootSelectorNode) {
            SelectorNodeDetailValue rootSelectorNodeDetailValue = getRootSelectorNodeDetailValueForUpdate(selector);
            
            rootSelectorNodeDetailValue.setIsRootSelectorNode(Boolean.FALSE);
            updateSelectorNodeFromValue(rootSelectorNodeDetailValue, false, createdBy);
        } else if(!rootFound) {
            isRootSelectorNode = Boolean.TRUE;
        }
        
        SelectorNode selectorNode = SelectorNodeFactory.getInstance().create();
        SelectorNodeDetail selectorNodeDetail = SelectorNodeDetailFactory.getInstance().create(selectorNode, selector,
                selectorNodeName, isRootSelectorNode, selectorNodeType, negate, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        selectorNode = SelectorNodeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, selectorNode.getPrimaryKey());
        selectorNode.setActiveDetail(selectorNodeDetail);
        selectorNode.setLastDetail(selectorNodeDetail);
        selectorNode.store();
        
        sendEventUsingNames(selector.getPrimaryKey(), EventTypes.MODIFY.name(), selectorNode.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return selectorNode;
    }
    
    private SelectorNode getRootSelectorNode(Selector selector, EntityPermission entityPermission) {
        SelectorNode selectorNode;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornodes, selectornodedetails " +
                        "WHERE slnd_activedetailid = slnddt_selectornodedetailid AND slnddt_sl_selectorid = ? " +
                        "AND slnddt_isrootselectornode = 1";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornodes, selectornodedetails " +
                        "WHERE slnd_activedetailid = slnddt_selectornodedetailid AND slnddt_sl_selectorid = ? " +
                        "AND slnddt_isrootselectornode = 1 " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SelectorNodeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, selector.getPrimaryKey().getEntityId());
            
            selectorNode = SelectorNodeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return selectorNode;
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
        SelectorNode selectorNode;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornodes, selectornodedetails " +
                        "WHERE slnd_activedetailid = slnddt_selectornodedetailid " +
                        "AND slnddt_sl_selectorid = ? AND slnddt_selectornodename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornodes, selectornodedetails " +
                        "WHERE slnd_activedetailid = slnddt_selectornodedetailid " +
                        "AND slnddt_sl_selectorid = ? AND slnddt_selectornodename = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SelectorNodeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, selector.getPrimaryKey().getEntityId());
            ps.setString(2, selectorNodeName);
            
            selectorNode = SelectorNodeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return selectorNode;
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
        List<SelectorNode> selectorNodes;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornodes, selectornodedetails " +
                        "WHERE slnd_activedetailid = slnddt_selectornodedetailid AND slnddt_sl_selectorid = ? " +
                        "ORDER BY slnddt_selectornodename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornodes, selectornodedetails " +
                        "WHERE slnd_activedetailid = slnddt_selectornodedetailid AND slnddt_sl_selectorid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SelectorNodeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, selector.getPrimaryKey().getEntityId());
            
            selectorNodes = SelectorNodeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return selectorNodes;
    }
    
    public List<SelectorNode> getSelectorNodesBySelector(Selector selector) {
        return getSelectorNodesBySelector(selector, EntityPermission.READ_ONLY);
    }
    
    public List<SelectorNode> getSelectorNodesBySelectorForUpdate(Selector selector) {
        return getSelectorNodesBySelector(selector, EntityPermission.READ_WRITE);
    }
    
    public SelectorNodeChoicesBean getSelectorNodeChoices(Selector selector, String defaultSelectorNodeChoice, Language language,
            boolean allowNullChoice) {
        List<SelectorNode> selectorNodes = getSelectorNodesBySelector(selector);
        int size = selectorNodes.size() + (allowNullChoice? 1: 0);
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultSelectorNodeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var selectorNode : selectorNodes) {
            String label = getBestSelectorNodeDescription(selectorNode, language);
            String value = selectorNode.getLastDetail().getSelectorNodeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultSelectorNodeChoice != null && defaultSelectorNodeChoice.equals(value);
            if(usingDefaultChoice || defaultValue == null) {
                defaultValue = value;
            }
        }
        
        return new SelectorNodeChoicesBean(labels, values, defaultValue);
    }
    
    public SelectorNodeTransfer getSelectorNodeTransfer(UserVisit userVisit, SelectorNode selectorNode) {
        return getSelectorTransferCaches(userVisit).getSelectorNodeTransferCache().getSelectorNodeTransfer(selectorNode);
    }
    
    public List<SelectorNodeTransfer> getSelectorNodeTransfersBySelector(UserVisit userVisit, Selector selector) {
        List<SelectorNode> selectorNodes = getSelectorNodesBySelector(selector);
        List<SelectorNodeTransfer> selectorNodeTransfers = new ArrayList<>(selectorNodes.size());
        
        selectorNodes.forEach((selectorNode) -> {
            selectorNodeTransfers.add(getSelectorTransferCaches(userVisit).getSelectorNodeTransferCache().getSelectorNodeTransfer(selectorNode));
        });
        
        return selectorNodeTransfers;
    }
    
    private void updateSelectorNodeFromValue(SelectorNodeDetailValue selectorNodeDetailValue, boolean checkRoot, BasePK updatedBy) {
        if(selectorNodeDetailValue.hasBeenModified()) {
            SelectorNode selectorNode = SelectorNodeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     selectorNodeDetailValue.getSelectorNodePK());
            SelectorNodeDetail selectorNodeDetail = selectorNode.getActiveDetailForUpdate();
            
            selectorNodeDetail.setThruTime(session.START_TIME_LONG);
            selectorNodeDetail.store();
            
            SelectorNodePK selectorNodePK = selectorNodeDetail.getSelectorNodePK();
            Selector selector = selectorNodeDetail.getSelector();
            SelectorPK selectorPK = selector.getPrimaryKey();
            String selectorNodeName = selectorNodeDetailValue.getSelectorNodeName();
            SelectorNodeTypePK selectorNodeTypePK = selectorNodeDetailValue.getSelectorNodeTypePK(); // Not updated
            Boolean isRootSelectorNode = selectorNodeDetailValue.getIsRootSelectorNode();
            Boolean negate = selectorNodeDetailValue.getNegate();
            
            if(checkRoot) {
                SelectorNode rootSelectorNode = getRootSelectorNode(selector);
                boolean rootFound = rootSelectorNode != null && !rootSelectorNode.equals(selectorNode);
                
                if(isRootSelectorNode && rootFound) {
                    // If I'm the root, and a root already existed...
                    SelectorNodeDetailValue rootSelectorNodeDetailValue = getRootSelectorNodeDetailValueForUpdate(selector);
                    
                    rootSelectorNodeDetailValue.setIsRootSelectorNode(Boolean.FALSE);
                    updateSelectorNodeFromValue(rootSelectorNodeDetailValue, false, updatedBy);
                } else if(!isRootSelectorNode && !rootFound) {
                    // If I'm not the root, and no other root exists...
                    isRootSelectorNode = Boolean.TRUE;
                }
            }
            
            selectorNodeDetail = SelectorNodeDetailFactory.getInstance().create(selectorNodePK, selector.getPrimaryKey(),
                    selectorNodeName, isRootSelectorNode, selectorNodeTypePK, negate, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            selectorNode.setActiveDetail(selectorNodeDetail);
            selectorNode.setLastDetail(selectorNodeDetail);
            
            sendEventUsingNames(selectorPK, EventTypes.MODIFY.name(), selectorNodePK, EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void updateSelectorNodeFromValue(SelectorNodeDetailValue selectorNodeDetailValue, BasePK updatedBy) {
        updateSelectorNodeFromValue(selectorNodeDetailValue, true, updatedBy);
    }
    
    public void deleteSelectorNode(SelectorNode selectorNode, BasePK deletedBy) {
        SelectorNodeDetail selectorNodeDetail = selectorNode.getLastDetailForUpdate();
        String selectorNodeTypeName = selectorNodeDetail.getSelectorNodeType().getSelectorNodeTypeName();
        
        deleteSelectorNodeDescriptionsBySelectorNode(selectorNode, deletedBy);
        
        if(selectorNodeTypeName.equals(SelectorNodeTypes.BOOLEAN.name())) {
            deleteSelectorNodeBoolean(getSelectorNodeBooleanForUpdate(selectorNode), deletedBy);
        } else if(selectorNodeTypeName.equals(SelectorNodeTypes.WORKFLOW_STEP.name())) {
            deleteSelectorNodeWorkflowStep(getSelectorNodeWorkflowStepForUpdate(selectorNode), deletedBy);
        } else if(selectorNodeTypeName.equals(SelectorNodeTypes.ENTITY_LIST_ITEM.name())) {
            deleteSelectorNodeEntityListItem(getSelectorNodeEntityListItemForUpdate(selectorNode), deletedBy);
        } else if(selectorNodeTypeName.equals(SelectorNodeTypes.RESPONSIBILITY_TYPE.name())) {
            deleteSelectorNodeResponsibilityType(getSelectorNodeResponsibilityTypeForUpdate(selectorNode), deletedBy);
        } else if(selectorNodeTypeName.equals(SelectorNodeTypes.TRAINING_CLASS.name())) {
            deleteSelectorNodeTrainingClass(getSelectorNodeTrainingClassForUpdate(selectorNode), deletedBy);
        } else if(selectorNodeTypeName.equals(SelectorNodeTypes.SKILL_TYPE.name())) {
            deleteSelectorNodeSkillType(getSelectorNodeSkillTypeForUpdate(selectorNode), deletedBy);
        } else if(selectorNodeTypeName.equals(SelectorNodeTypes.ITEM_CATEGORY.name())) {
            deleteSelectorNodeItemCategory(getSelectorNodeItemCategoryForUpdate(selectorNode), deletedBy);
        } else if(selectorNodeTypeName.equals(SelectorNodeTypes.ITEM_ACCOUNTING_CATEGORY.name())) {
            deleteSelectorNodeItemAccountingCategory(getSelectorNodeItemAccountingCategoryForUpdate(selectorNode), deletedBy);
        } else if(selectorNodeTypeName.equals(SelectorNodeTypes.ITEM_PURCHASING_CATEGORY.name())) {
            deleteSelectorNodeItemPurchasingCategory(getSelectorNodeItemPurchasingCategoryForUpdate(selectorNode), deletedBy);
        } else if(selectorNodeTypeName.equals(SelectorNodeTypes.GEO_CODE.name())) {
            deleteSelectorNodeGeoCode(getSelectorNodeGeoCodeForUpdate(selectorNode), deletedBy);
        } else if(selectorNodeTypeName.equals(SelectorNodeTypes.PAYMENT_METHOD.name())) {
            deleteSelectorNodePaymentMethod(getSelectorNodePaymentMethodForUpdate(selectorNode), deletedBy);
        } else if(selectorNodeTypeName.equals(SelectorNodeTypes.PAYMENT_PROCESSOR.name())) {
            deleteSelectorNodePaymentProcessor(getSelectorNodePaymentProcessorForUpdate(selectorNode), deletedBy);
        }
        
        selectorNodeDetail.setThruTime(session.START_TIME_LONG);
        selectorNode.setActiveDetail(null);
        selectorNode.store();
        
        // Check for root, and pick one if necessary
        // TODO: this should be smarter, looking for Boolean nodes and finding one that would make a
        // good root.
        Selector selector = selectorNodeDetail.getSelector();
        SelectorNode rootSelectorNode = getRootSelectorNode(selector);
        if(rootSelectorNode == null) {
            List<SelectorNode> SelectorNodes = getSelectorNodesBySelectorForUpdate(selector);
            
            if(!SelectorNodes.isEmpty()) {
                Iterator<SelectorNode> iter = SelectorNodes.iterator();
                if(iter.hasNext()) {
                    rootSelectorNode = iter.next();
                }
                SelectorNodeDetailValue selectorNodeDetailValue = Objects.requireNonNull(rootSelectorNode).getLastDetailForUpdate().getSelectorNodeDetailValue().clone();
                
                selectorNodeDetailValue.setIsRootSelectorNode(Boolean.TRUE);
                updateSelectorNodeFromValue(selectorNodeDetailValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(selectorNodeDetail.getSelector().getPrimaryKey(), EventTypes.MODIFY.name(),
                selectorNode.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteSelectorNodesBySelector(Selector selector, BasePK deletedBy) {
        List<SelectorNode> selectorNodes = getSelectorNodesBySelectorForUpdate(selector);
        
        selectorNodes.forEach((selectorNode) -> 
                deleteSelectorNode(selectorNode, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Node Descriptions
    // --------------------------------------------------------------------------------
    
    public SelectorNodeDescription createSelectorNodeDescription(SelectorNode selectorNode, Language language, String description,
            BasePK createdBy) {
        SelectorNodeDescription selectorNodeDescription = SelectorNodeDescriptionFactory.getInstance().create(selectorNode,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(selectorNode.getLastDetail().getSelectorPK(), EventTypes.MODIFY.name(),
                selectorNodeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return selectorNodeDescription;
    }
    
    private SelectorNodeDescription getSelectorNodeDescription(SelectorNode selectorNode, Language language,
            EntityPermission entityPermission) {
        SelectorNodeDescription selectorNodeDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornodedescriptions " +
                        "WHERE slndd_slnd_selectornodeid = ? AND slndd_lang_languageid = ? AND slndd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornodedescriptions " +
                        "WHERE slndd_slnd_selectornodeid = ? AND slndd_lang_languageid = ? AND slndd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SelectorNodeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, selectorNode.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            selectorNodeDescription = SelectorNodeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return selectorNodeDescription;
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
        SelectorNodeDescription selectorNodeDescription = getSelectorNodeDescriptionForUpdate(selectorNode, language);
        
        return selectorNodeDescription == null? null: selectorNodeDescription.getSelectorNodeDescriptionValue().clone();
    }
    
    private List<SelectorNodeDescription> getSelectorNodeDescriptionsBySelectorNode(SelectorNode selectorNode, EntityPermission entityPermission) {
        List<SelectorNodeDescription> selectorNodeDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornodedescriptions, languages " +
                        "WHERE slndd_slnd_selectornodeid = ? AND slndd_thrutime = ? AND slndd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornodedescriptions " +
                        "WHERE slndd_slnd_selectornodeid = ? AND slndd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SelectorNodeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, selectorNode.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            selectorNodeDescriptions = SelectorNodeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return selectorNodeDescriptions;
    }
    
    public List<SelectorNodeDescription> getSelectorNodeDescriptionsBySelectorNode(SelectorNode selectorNode) {
        return getSelectorNodeDescriptionsBySelectorNode(selectorNode, EntityPermission.READ_ONLY);
    }
    
    public List<SelectorNodeDescription> getSelectorNodeDescriptionsBySelectorNodeForUpdate(SelectorNode selectorNode) {
        return getSelectorNodeDescriptionsBySelectorNode(selectorNode, EntityPermission.READ_WRITE);
    }
    
    public String getBestSelectorNodeDescription(SelectorNode selectorNode, Language language) {
        String description;
        SelectorNodeDescription selectorNodeDescription = getSelectorNodeDescription(selectorNode, language);
        
        if(selectorNodeDescription == null && !language.getIsDefault()) {
            selectorNodeDescription = getSelectorNodeDescription(selectorNode, getPartyControl().getDefaultLanguage());
        }
        
        if(selectorNodeDescription == null) {
            description = selectorNode.getLastDetail().getSelectorNodeName();
        } else {
            description = selectorNodeDescription.getDescription();
        }
        
        return description;
    }
    
    public SelectorNodeDescriptionTransfer getSelectorNodeDescriptionTransfer(UserVisit userVisit, SelectorNodeDescription selectorNodeDescription) {
        return getSelectorTransferCaches(userVisit).getSelectorNodeDescriptionTransferCache().getSelectorNodeDescriptionTransfer(selectorNodeDescription);
    }
    
    public List<SelectorNodeDescriptionTransfer> getSelectorNodeDescriptionTransfers(UserVisit userVisit, SelectorNode selectorNode) {
        List<SelectorNodeDescription> selectorNodeDescriptions = getSelectorNodeDescriptionsBySelectorNode(selectorNode);
        List<SelectorNodeDescriptionTransfer> selectorNodeDescriptionTransfers = null;
        
        if(selectorNodeDescriptions != null) {
            selectorNodeDescriptionTransfers = new ArrayList<>(selectorNodeDescriptions.size());
            
            for(var selectorNodeDescription : selectorNodeDescriptions) {
                selectorNodeDescriptionTransfers.add(getSelectorTransferCaches(userVisit).getSelectorNodeDescriptionTransferCache().getSelectorNodeDescriptionTransfer(selectorNodeDescription));
            }
        }
        
        return selectorNodeDescriptionTransfers;
    }
    
    public void updateSelectorNodeDescriptionFromValue(SelectorNodeDescriptionValue selectorNodeDescriptionValue, BasePK updatedBy) {
        if(selectorNodeDescriptionValue.hasBeenModified()) {
            SelectorNodeDescription selectorNodeDescription = SelectorNodeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, selectorNodeDescriptionValue.getPrimaryKey());
            
            selectorNodeDescription.setThruTime(session.START_TIME_LONG);
            selectorNodeDescription.store();
            
            SelectorNode selectorNode = selectorNodeDescription.getSelectorNode();
            Language language = selectorNodeDescription.getLanguage();
            String description = selectorNodeDescriptionValue.getDescription();
            
            selectorNodeDescription = SelectorNodeDescriptionFactory.getInstance().create(selectorNode, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(selectorNode.getLastDetail().getSelector().getPrimaryKey(), EventTypes.MODIFY.name(), selectorNodeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteSelectorNodeDescription(SelectorNodeDescription selectorNodeDescription, BasePK deletedBy) {
        selectorNodeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(selectorNodeDescription.getSelectorNode().getLastDetail().getSelector().getPrimaryKey(), EventTypes.MODIFY.name(), selectorNodeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteSelectorNodeDescriptionsBySelectorNode(SelectorNode selectorNode, BasePK deletedBy) {
        List<SelectorNodeDescription> selectorNodeDescriptions = getSelectorNodeDescriptionsBySelectorNodeForUpdate(selectorNode);
        
        selectorNodeDescriptions.forEach((selectorNodeDescription) -> 
                deleteSelectorNodeDescription(selectorNodeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Node Booleans
    // --------------------------------------------------------------------------------
    
    public SelectorNodeBoolean createSelectorNodeBoolean(SelectorNode selectorNode, SelectorBooleanType selectorBooleanType,
            SelectorNode leftSelectorNode, SelectorNode rightSelectorNode, BasePK createdBy) {
        SelectorNodeBoolean selectorNodeBoolean = SelectorNodeBooleanFactory.getInstance().create(selectorNode,
                selectorBooleanType, leftSelectorNode, rightSelectorNode, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(selectorNode.getLastDetail().getSelectorPK(), EventTypes.MODIFY.name(),
                selectorNodeBoolean.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return selectorNodeBoolean;
    }
    
    private SelectorNodeBoolean getSelectorNodeBoolean(SelectorNode selectorNode, EntityPermission entityPermission) {
        SelectorNodeBoolean selectorNodeBoolean;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornodebooleans " +
                        "WHERE slndbln_slnd_selectornodeid = ? AND slndbln_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornodebooleans " +
                        "WHERE slndbln_slnd_selectornodeid = ? AND slndbln_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SelectorNodeBooleanFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, selectorNode.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            selectorNodeBoolean = SelectorNodeBooleanFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return selectorNodeBoolean;
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
        SelectorNodeBoolean selectorNodeBoolean = getSelectorNodeBooleanForUpdate(selectorNode);
        
        return selectorNodeBoolean == null? null: selectorNodeBoolean.getSelectorNodeBooleanValue().clone();
    }
    
    private List<SelectorNodeBoolean> getSelectorNodeBooleansBySelector(Selector selector, EntityPermission entityPermission) {
        List<SelectorNodeBoolean> selectorNodeBooleans;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornodes, selectornodedetails, selectornodebooleans " +
                        "WHERE slnd_activedetailid = slnddt_selectornodedetailid AND slnddt_sl_selectorid = ? " +
                        "AND slnd_selectornodeid = slndbln_slnd_selectornodeid AND slndbln_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornodes, selectornodedetails, selectornodebooleans " +
                        "WHERE slnd_activedetailid = slnddt_selectornodedetailid AND slnddt_sl_selectorid = ? " +
                        "AND slnd_selectornodeid = slndbln_slnd_selectornodeid AND slndbln_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SelectorNodeBooleanFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, selector.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            selectorNodeBooleans = SelectorNodeBooleanFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return selectorNodeBooleans;
    }
    
    public List<SelectorNodeBoolean> getSelectorNodeBooleansBySelector(Selector selector) {
        return getSelectorNodeBooleansBySelector(selector, EntityPermission.READ_ONLY);
    }
    
    public List<SelectorNodeBoolean> getSelectorNodeBooleansBySelectorForUpdate(Selector selector) {
        return getSelectorNodeBooleansBySelector(selector, EntityPermission.READ_WRITE);
    }
    
    public void updateSelectorNodeBooleanFromValue(SelectorNodeBooleanValue selectorNodeBooleanValue, BasePK updatedBy) {
        if(selectorNodeBooleanValue.hasBeenModified()) {
            SelectorNodeBoolean selectorNodeBoolean = SelectorNodeBooleanFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     selectorNodeBooleanValue.getPrimaryKey());
            
            selectorNodeBoolean.setThruTime(session.START_TIME_LONG);
            selectorNodeBoolean.store();
            
            SelectorNode selectorNode = selectorNodeBoolean.getSelectorNode();
            SelectorBooleanTypePK selectorBooleanTypePK = selectorNodeBooleanValue.getSelectorBooleanTypePK();
            SelectorNodePK leftSelectorNodePK = selectorNodeBooleanValue.getLeftSelectorNodePK();
            SelectorNodePK rightSelectorNodePK = selectorNodeBooleanValue.getRightSelectorNodePK();
            
            selectorNodeBoolean = SelectorNodeBooleanFactory.getInstance().create(selectorNode.getPrimaryKey(),
                    selectorBooleanTypePK, leftSelectorNodePK, rightSelectorNodePK, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(selectorNode.getLastDetail().getSelectorPK(), EventTypes.MODIFY.name(),
                    selectorNodeBoolean.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteSelectorNodeBoolean(SelectorNodeBoolean selectorNodeBoolean, BasePK deletedBy) {
        selectorNodeBoolean.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(selectorNodeBoolean.getSelectorNode().getLastDetail().getSelectorPK(),
                EventTypes.MODIFY.name(), selectorNodeBoolean.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Node Workflow Steps
    // --------------------------------------------------------------------------------
    
    public SelectorNodeWorkflowStep createSelectorNodeWorkflowStep(SelectorNode selectorNode, WorkflowStep workflowStep,
            BasePK createdBy) {
        SelectorNodeWorkflowStep selectorNodeWorkflowStep = SelectorNodeWorkflowStepFactory.getInstance().create(session,
                selectorNode, workflowStep, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(selectorNode.getLastDetail().getSelectorPK(), EventTypes.MODIFY.name(),
                selectorNodeWorkflowStep.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return selectorNodeWorkflowStep;
    }
    
    private SelectorNodeWorkflowStep getSelectorNodeWorkflowStep(SelectorNode selectorNode, EntityPermission entityPermission) {
        SelectorNodeWorkflowStep selectorNodeWorkflowStep;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornodeworkflowsteps " +
                        "WHERE slndws_slnd_selectornodeid = ? AND slndws_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornodeworkflowsteps " +
                        "WHERE slndws_slnd_selectornodeid = ? AND slndws_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SelectorNodeWorkflowStepFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, selectorNode.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            selectorNodeWorkflowStep = SelectorNodeWorkflowStepFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return selectorNodeWorkflowStep;
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
        SelectorNodeWorkflowStep selectorNodeWorkflowStep = getSelectorNodeWorkflowStepForUpdate(selectorNode);
        
        return selectorNodeWorkflowStep == null? null: selectorNodeWorkflowStep.getSelectorNodeWorkflowStepValue().clone();
    }
    
    private List<SelectorNodeWorkflowStep> getSelectorNodeWorkflowStepsBySelector(Selector selector, EntityPermission entityPermission) {
        List<SelectorNodeWorkflowStep> selectorNodeWorkflowSteps;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornodes, selectornodedetails, selectornodeworkflowsteps " +
                        "WHERE slnd_activedetailid = slnddt_selectornodedetailid AND slnddt_sl_selectorid = ? " +
                        "AND slnd_selectornodeid = slndws_slnd_selectornodeid AND slndws_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornodes, selectornodedetails, selectornodeworkflowsteps " +
                        "WHERE slnd_activedetailid = slnddt_selectornodedetailid AND slnddt_sl_selectorid = ? " +
                        "AND slnd_selectornodeid = slndws_slnd_selectornodeid AND slndws_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SelectorNodeWorkflowStepFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, selector.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            selectorNodeWorkflowSteps = SelectorNodeWorkflowStepFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return selectorNodeWorkflowSteps;
    }
    
    public List<SelectorNodeWorkflowStep> getSelectorNodeWorkflowStepsBySelector(Selector selector) {
        return getSelectorNodeWorkflowStepsBySelector(selector, EntityPermission.READ_ONLY);
    }
    
    public List<SelectorNodeWorkflowStep> getSelectorNodeWorkflowStepsBySelectorForUpdate(Selector selector) {
        return getSelectorNodeWorkflowStepsBySelector(selector, EntityPermission.READ_WRITE);
    }
    
    public void updateSelectorNodeWorkflowStepFromValue(SelectorNodeWorkflowStepValue selectorNodeWorkflowStepValue, BasePK updatedBy) {
        if(selectorNodeWorkflowStepValue.hasBeenModified()) {
            SelectorNodeWorkflowStep selectorNodeWorkflowStep = SelectorNodeWorkflowStepFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     selectorNodeWorkflowStepValue.getPrimaryKey());
            
            selectorNodeWorkflowStep.setThruTime(session.START_TIME_LONG);
            selectorNodeWorkflowStep.store();
            
            SelectorNode selectorNode = selectorNodeWorkflowStep.getSelectorNode();
            WorkflowStepPK workflowStepPK = selectorNodeWorkflowStepValue.getWorkflowStepPK();
            
            selectorNodeWorkflowStep = SelectorNodeWorkflowStepFactory.getInstance().create(selectorNode.getPrimaryKey(),
                    workflowStepPK, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(selectorNode.getLastDetail().getSelectorPK(), EventTypes.MODIFY.name(),
                    selectorNodeWorkflowStep.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteSelectorNodeWorkflowStep(SelectorNodeWorkflowStep selectorNodeWorkflowStep, BasePK deletedBy) {
        selectorNodeWorkflowStep.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(selectorNodeWorkflowStep.getSelectorNode().getLastDetail().getSelectorPK(),
                EventTypes.MODIFY.name(), selectorNodeWorkflowStep.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Node Entity List Items
    // --------------------------------------------------------------------------------
    
    public SelectorNodeEntityListItem createSelectorNodeEntityListItem(SelectorNode selectorNode, EntityListItem entityListItem,
            BasePK createdBy) {
        SelectorNodeEntityListItem selectorNodeEntityListItem = SelectorNodeEntityListItemFactory.getInstance().create(session,
                selectorNode, entityListItem, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(selectorNode.getLastDetail().getSelectorPK(), EventTypes.MODIFY.name(),
                selectorNodeEntityListItem.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return selectorNodeEntityListItem;
    }
    
    private SelectorNodeEntityListItem getSelectorNodeEntityListItem(SelectorNode selectorNode, EntityPermission entityPermission) {
        SelectorNodeEntityListItem selectorNodeEntityListItem;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornodeentitylistitems " +
                        "WHERE slndeli_slnd_selectornodeid = ? AND slndeli_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornodeentitylistitems " +
                        "WHERE slndeli_slnd_selectornodeid = ? AND slndeli_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SelectorNodeEntityListItemFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, selectorNode.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            selectorNodeEntityListItem = SelectorNodeEntityListItemFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return selectorNodeEntityListItem;
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
        SelectorNodeEntityListItem selectorNodeEntityListItem = getSelectorNodeEntityListItemForUpdate(selectorNode);
        
        return selectorNodeEntityListItem == null? null: selectorNodeEntityListItem.getSelectorNodeEntityListItemValue().clone();
    }
    
    private List<SelectorNodeEntityListItem> getSelectorNodeEntityListItemsBySelector(Selector selector, EntityPermission entityPermission) {
        List<SelectorNodeEntityListItem> selectorNodeEntityListItems;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornodes, selectornodedetails, selectornodeentitylistitems " +
                        "WHERE slnd_activedetailid = slnddt_selectornodedetailid AND slnddt_sl_selectorid = ? " +
                        "AND slnd_selectornodeid = slndeli_slnd_selectornodeid AND slndeli_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornodes, selectornodedetails, selectornodeentitylistitems " +
                        "WHERE slnd_activedetailid = slnddt_selectornodedetailid AND slnddt_sl_selectorid = ? " +
                        "AND slnd_selectornodeid = slndeli_slnd_selectornodeid AND slndeli_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SelectorNodeEntityListItemFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, selector.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            selectorNodeEntityListItems = SelectorNodeEntityListItemFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return selectorNodeEntityListItems;
    }
    
    public List<SelectorNodeEntityListItem> getSelectorNodeEntityListItemsBySelector(Selector selector) {
        return getSelectorNodeEntityListItemsBySelector(selector, EntityPermission.READ_ONLY);
    }
    
    public List<SelectorNodeEntityListItem> getSelectorNodeEntityListItemsBySelectorForUpdate(Selector selector) {
        return getSelectorNodeEntityListItemsBySelector(selector, EntityPermission.READ_WRITE);
    }
    
    public void updateSelectorNodeEntityListItemFromValue(SelectorNodeEntityListItemValue selectorNodeEntityListItemValue, BasePK updatedBy) {
        if(selectorNodeEntityListItemValue.hasBeenModified()) {
            SelectorNodeEntityListItem selectorNodeEntityListItem = SelectorNodeEntityListItemFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     selectorNodeEntityListItemValue.getPrimaryKey());
            
            selectorNodeEntityListItem.setThruTime(session.START_TIME_LONG);
            selectorNodeEntityListItem.store();
            
            SelectorNode selectorNode = selectorNodeEntityListItem.getSelectorNode();
            EntityListItemPK entityListItemPK = selectorNodeEntityListItemValue.getEntityListItemPK();
            
            selectorNodeEntityListItem = SelectorNodeEntityListItemFactory.getInstance().create(selectorNode.getPrimaryKey(),
                    entityListItemPK, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(selectorNode.getLastDetail().getSelectorPK(), EventTypes.MODIFY.name(),
                    selectorNodeEntityListItem.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteSelectorNodeEntityListItem(SelectorNodeEntityListItem selectorNodeEntityListItem, BasePK deletedBy) {
        selectorNodeEntityListItem.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(selectorNodeEntityListItem.getSelectorNode().getLastDetail().getSelectorPK(),
                EventTypes.MODIFY.name(), selectorNodeEntityListItem.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Node Responsibility Types
    // --------------------------------------------------------------------------------
    
    public SelectorNodeResponsibilityType createSelectorNodeResponsibilityType(SelectorNode selectorNode, ResponsibilityType responsibilityType,
            BasePK createdBy) {
        SelectorNodeResponsibilityType selectorNodeResponsibilityType = SelectorNodeResponsibilityTypeFactory.getInstance().create(session,
                selectorNode, responsibilityType, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(selectorNode.getLastDetail().getSelectorPK(), EventTypes.MODIFY.name(),
                selectorNodeResponsibilityType.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return selectorNodeResponsibilityType;
    }
    
    private SelectorNodeResponsibilityType getSelectorNodeResponsibilityType(SelectorNode selectorNode, EntityPermission entityPermission) {
        SelectorNodeResponsibilityType selectorNodeResponsibilityType;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornoderesponsibilitytypes " +
                        "WHERE slndrsptyp_rsptyp_responsibilitytypeid = ? AND slndrsptyp_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornoderesponsibilitytypes " +
                        "WHERE slndrsptyp_rsptyp_responsibilitytypeid = ? AND slndrsptyp_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SelectorNodeResponsibilityTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, selectorNode.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            selectorNodeResponsibilityType = SelectorNodeResponsibilityTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return selectorNodeResponsibilityType;
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
        SelectorNodeResponsibilityType selectorNodeResponsibilityType = getSelectorNodeResponsibilityTypeForUpdate(selectorNode);
        
        return selectorNodeResponsibilityType == null? null: selectorNodeResponsibilityType.getSelectorNodeResponsibilityTypeValue().clone();
    }
    
    private List<SelectorNodeResponsibilityType> getSelectorNodeResponsibilityTypesBySelector(Selector selector, EntityPermission entityPermission) {
        List<SelectorNodeResponsibilityType> selectorNodeResponsibilityTypes;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornodes, selectornodedetails, selectornoderesponsibilitytypes " +
                        "WHERE slnd_activedetailid = slnddt_selectornodedetailid AND slnddt_sl_selectorid = ? " +
                        "AND slnd_selectornodeid = slndrsptyp_rsptyp_responsibilitytypeid AND slndrsptyp_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornodes, selectornodedetails, selectornoderesponsibilitytypes " +
                        "WHERE slnd_activedetailid = slnddt_selectornodedetailid AND slnddt_sl_selectorid = ? " +
                        "AND slnd_selectornodeid = slndrsptyp_rsptyp_responsibilitytypeid AND slndrsptyp_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SelectorNodeResponsibilityTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, selector.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            selectorNodeResponsibilityTypes = SelectorNodeResponsibilityTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return selectorNodeResponsibilityTypes;
    }
    
    public List<SelectorNodeResponsibilityType> getSelectorNodeResponsibilityTypesBySelector(Selector selector) {
        return getSelectorNodeResponsibilityTypesBySelector(selector, EntityPermission.READ_ONLY);
    }
    
    public List<SelectorNodeResponsibilityType> getSelectorNodeResponsibilityTypesBySelectorForUpdate(Selector selector) {
        return getSelectorNodeResponsibilityTypesBySelector(selector, EntityPermission.READ_WRITE);
    }
    
    public void updateSelectorNodeResponsibilityTypeFromValue(SelectorNodeResponsibilityTypeValue selectorNodeResponsibilityTypeValue, BasePK updatedBy) {
        if(selectorNodeResponsibilityTypeValue.hasBeenModified()) {
            SelectorNodeResponsibilityType selectorNodeResponsibilityType = SelectorNodeResponsibilityTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     selectorNodeResponsibilityTypeValue.getPrimaryKey());
            
            selectorNodeResponsibilityType.setThruTime(session.START_TIME_LONG);
            selectorNodeResponsibilityType.store();
            
            SelectorNode selectorNode = selectorNodeResponsibilityType.getSelectorNode();
            ResponsibilityTypePK responsibilityTypePK = selectorNodeResponsibilityTypeValue.getResponsibilityTypePK();
            
            selectorNodeResponsibilityType = SelectorNodeResponsibilityTypeFactory.getInstance().create(selectorNode.getPrimaryKey(),
                    responsibilityTypePK, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(selectorNode.getLastDetail().getSelectorPK(), EventTypes.MODIFY.name(),
                    selectorNodeResponsibilityType.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteSelectorNodeResponsibilityType(SelectorNodeResponsibilityType selectorNodeResponsibilityType, BasePK deletedBy) {
        selectorNodeResponsibilityType.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(selectorNodeResponsibilityType.getSelectorNode().getLastDetail().getSelectorPK(),
                EventTypes.MODIFY.name(), selectorNodeResponsibilityType.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Node Training Class Types
    // --------------------------------------------------------------------------------
    
    public SelectorNodeTrainingClass createSelectorNodeTrainingClass(SelectorNode selectorNode, TrainingClass trainingClass,
            BasePK createdBy) {
        SelectorNodeTrainingClass selectorNodeTrainingClass = SelectorNodeTrainingClassFactory.getInstance().create(session,
                selectorNode, trainingClass, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(selectorNode.getLastDetail().getSelectorPK(), EventTypes.MODIFY.name(),
                selectorNodeTrainingClass.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return selectorNodeTrainingClass;
    }
    
    private SelectorNodeTrainingClass getSelectorNodeTrainingClass(SelectorNode selectorNode, EntityPermission entityPermission) {
        SelectorNodeTrainingClass selectorNodeTrainingClass;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornodetrainingclasses " +
                        "WHERE slndtrncls_slnd_selectornodeid = ? AND slndtrncls_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornodetrainingclasses " +
                        "WHERE slndtrncls_slnd_selectornodeid = ? AND slndtrncls_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SelectorNodeTrainingClassFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, selectorNode.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            selectorNodeTrainingClass = SelectorNodeTrainingClassFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return selectorNodeTrainingClass;
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
        SelectorNodeTrainingClass selectorNodeTrainingClass = getSelectorNodeTrainingClassForUpdate(selectorNode);
        
        return selectorNodeTrainingClass == null? null: selectorNodeTrainingClass.getSelectorNodeTrainingClassValue().clone();
    }
    
    private List<SelectorNodeTrainingClass> getSelectorNodeTrainingClassesBySelector(Selector selector, EntityPermission entityPermission) {
        List<SelectorNodeTrainingClass> selectorNodeTrainingClasses;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornodes, selectornodedetails, selectornodetrainingclasses " +
                        "WHERE slnd_activedetailid = slnddt_selectornodedetailid AND slnddt_sl_selectorid = ? " +
                        "AND slnd_selectornodeid = slndtrncls_slnd_selectornodeid AND slndtrncls_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornodes, selectornodedetails, selectornodetrainingclasses " +
                        "WHERE slnd_activedetailid = slnddt_selectornodedetailid AND slnddt_sl_selectorid = ? " +
                        "AND slnd_selectornodeid = slndtrncls_slnd_selectornodeid AND slndtrncls_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SelectorNodeTrainingClassFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, selector.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            selectorNodeTrainingClasses = SelectorNodeTrainingClassFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return selectorNodeTrainingClasses;
    }
    
    public List<SelectorNodeTrainingClass> getSelectorNodeTrainingClassesBySelector(Selector selector) {
        return getSelectorNodeTrainingClassesBySelector(selector, EntityPermission.READ_ONLY);
    }
    
    public List<SelectorNodeTrainingClass> getSelectorNodeTrainingClassesBySelectorForUpdate(Selector selector) {
        return getSelectorNodeTrainingClassesBySelector(selector, EntityPermission.READ_WRITE);
    }
    
    public void updateSelectorNodeTrainingClassFromValue(SelectorNodeTrainingClassValue selectorNodeTrainingClassValue, BasePK updatedBy) {
        if(selectorNodeTrainingClassValue.hasBeenModified()) {
            SelectorNodeTrainingClass selectorNodeTrainingClass = SelectorNodeTrainingClassFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     selectorNodeTrainingClassValue.getPrimaryKey());
            
            selectorNodeTrainingClass.setThruTime(session.START_TIME_LONG);
            selectorNodeTrainingClass.store();
            
            SelectorNode selectorNode = selectorNodeTrainingClass.getSelectorNode();
            TrainingClassPK trainingClassPK = selectorNodeTrainingClassValue.getTrainingClassPK();
            
            selectorNodeTrainingClass = SelectorNodeTrainingClassFactory.getInstance().create(selectorNode.getPrimaryKey(),
                    trainingClassPK, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(selectorNode.getLastDetail().getSelectorPK(), EventTypes.MODIFY.name(),
                    selectorNodeTrainingClass.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteSelectorNodeTrainingClass(SelectorNodeTrainingClass selectorNodeTrainingClass, BasePK deletedBy) {
        selectorNodeTrainingClass.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(selectorNodeTrainingClass.getSelectorNode().getLastDetail().getSelectorPK(),
                EventTypes.MODIFY.name(), selectorNodeTrainingClass.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Node Skill Types
    // --------------------------------------------------------------------------------
    
    public SelectorNodeSkillType createSelectorNodeSkillType(SelectorNode selectorNode, SkillType skillType,
            BasePK createdBy) {
        SelectorNodeSkillType selectorNodeSkillType = SelectorNodeSkillTypeFactory.getInstance().create(session,
                selectorNode, skillType, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(selectorNode.getLastDetail().getSelectorPK(), EventTypes.MODIFY.name(),
                selectorNodeSkillType.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return selectorNodeSkillType;
    }
    
    private SelectorNodeSkillType getSelectorNodeSkillType(SelectorNode selectorNode, EntityPermission entityPermission) {
        SelectorNodeSkillType selectorNodeSkillType;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornodeskilltypes " +
                        "WHERE slndskltyp_slnd_selectornodeid = ? AND slndskltyp_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornodeskilltypes " +
                        "WHERE slndskltyp_slnd_selectornodeid = ? AND slndskltyp_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SelectorNodeSkillTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, selectorNode.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            selectorNodeSkillType = SelectorNodeSkillTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return selectorNodeSkillType;
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
        SelectorNodeSkillType selectorNodeSkillType = getSelectorNodeSkillTypeForUpdate(selectorNode);
        
        return selectorNodeSkillType == null? null: selectorNodeSkillType.getSelectorNodeSkillTypeValue().clone();
    }
    
    private List<SelectorNodeSkillType> getSelectorNodeSkillTypesBySelector(Selector selector, EntityPermission entityPermission) {
        List<SelectorNodeSkillType> selectorNodeSkillTypes;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornodes, selectornodedetails, selectornodeskilltypes " +
                        "WHERE slnd_activedetailid = slnddt_selectornodedetailid AND slnddt_sl_selectorid = ? " +
                        "AND slnd_selectornodeid = slndskltyp_slnd_selectornodeid AND slndskltyp_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornodes, selectornodedetails, selectornodeskilltypes " +
                        "WHERE slnd_activedetailid = slnddt_selectornodedetailid AND slnddt_sl_selectorid = ? " +
                        "AND slnd_selectornodeid = slndskltyp_slnd_selectornodeid AND slndskltyp_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SelectorNodeSkillTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, selector.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            selectorNodeSkillTypes = SelectorNodeSkillTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return selectorNodeSkillTypes;
    }
    
    public List<SelectorNodeSkillType> getSelectorNodeSkillTypesBySelector(Selector selector) {
        return getSelectorNodeSkillTypesBySelector(selector, EntityPermission.READ_ONLY);
    }
    
    public List<SelectorNodeSkillType> getSelectorNodeSkillTypesBySelectorForUpdate(Selector selector) {
        return getSelectorNodeSkillTypesBySelector(selector, EntityPermission.READ_WRITE);
    }
    
    public void updateSelectorNodeSkillTypeFromValue(SelectorNodeSkillTypeValue selectorNodeSkillTypeValue, BasePK updatedBy) {
        if(selectorNodeSkillTypeValue.hasBeenModified()) {
            SelectorNodeSkillType selectorNodeSkillType = SelectorNodeSkillTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     selectorNodeSkillTypeValue.getPrimaryKey());
            
            selectorNodeSkillType.setThruTime(session.START_TIME_LONG);
            selectorNodeSkillType.store();
            
            SelectorNode selectorNode = selectorNodeSkillType.getSelectorNode();
            SkillTypePK skillTypePK = selectorNodeSkillTypeValue.getSkillTypePK();
            
            selectorNodeSkillType = SelectorNodeSkillTypeFactory.getInstance().create(selectorNode.getPrimaryKey(),
                    skillTypePK, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(selectorNode.getLastDetail().getSelectorPK(), EventTypes.MODIFY.name(),
                    selectorNodeSkillType.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteSelectorNodeSkillType(SelectorNodeSkillType selectorNodeSkillType, BasePK deletedBy) {
        selectorNodeSkillType.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(selectorNodeSkillType.getSelectorNode().getLastDetail().getSelectorPK(),
                EventTypes.MODIFY.name(), selectorNodeSkillType.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Node Item Categories
    // --------------------------------------------------------------------------------
    
    public SelectorNodeItemCategory createSelectorNodeItemCategory(SelectorNode selectorNode, ItemCategory itemCategory,
            Boolean checkParents, BasePK createdBy) {
        SelectorNodeItemCategory selectorNodeItemCategory = SelectorNodeItemCategoryFactory.getInstance().create(session,
                selectorNode, itemCategory, checkParents, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(selectorNode.getLastDetail().getSelectorPK(), EventTypes.MODIFY.name(),
                selectorNodeItemCategory.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return selectorNodeItemCategory;
    }
    
    private SelectorNodeItemCategory getSelectorNodeItemCategory(SelectorNode selectorNode, EntityPermission entityPermission) {
        SelectorNodeItemCategory selectorNodeItemCategory;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornodeitemcategories " +
                        "WHERE slndic_slnd_selectornodeid = ? AND slndic_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornodeitemcategories " +
                        "WHERE slndic_slnd_selectornodeid = ? AND slndic_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SelectorNodeItemCategoryFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, selectorNode.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            selectorNodeItemCategory = SelectorNodeItemCategoryFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return selectorNodeItemCategory;
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
        SelectorNodeItemCategory selectorNodeItemCategory = getSelectorNodeItemCategoryForUpdate(selectorNode);
        
        return selectorNodeItemCategory == null? null: selectorNodeItemCategory.getSelectorNodeItemCategoryValue().clone();
    }
    
    private List<SelectorNodeItemCategory> getSelectorNodeItemCategoriesBySelector(Selector selector, EntityPermission entityPermission) {
        List<SelectorNodeItemCategory> selectorNodeItemCategories;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornodes, selectornodedetails, selectornodeitemcategories " +
                        "WHERE slnd_activedetailid = slnddt_selectornodedetailid AND slnddt_sl_selectorid = ? " +
                        "AND slnd_selectornodeid = slndic_slnd_selectornodeid AND slndic_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornodes, selectornodedetails, selectornodeitemcategories " +
                        "WHERE slnd_activedetailid = slnddt_selectornodedetailid AND slnddt_sl_selectorid = ? " +
                        "AND slnd_selectornodeid = slndic_slnd_selectornodeid AND slndic_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SelectorNodeItemCategoryFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, selector.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            selectorNodeItemCategories = SelectorNodeItemCategoryFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return selectorNodeItemCategories;
    }
    
    public List<SelectorNodeItemCategory> getSelectorNodeItemCategoriesBySelector(Selector selector) {
        return getSelectorNodeItemCategoriesBySelector(selector,EntityPermission.READ_ONLY);
    }
    
    public List<SelectorNodeItemCategory> getSelectorNodeItemCategoriesBySelectorForUpdate(Selector selector) {
        return getSelectorNodeItemCategoriesBySelector(selector,EntityPermission.READ_WRITE);
    }
    
    public void updateSelectorNodeItemCategoryFromValue(SelectorNodeItemCategoryValue selectorNodeItemCategoryValue, BasePK updatedBy) {
        if(selectorNodeItemCategoryValue.hasBeenModified()) {
            SelectorNodeItemCategory selectorNodeItemCategory = SelectorNodeItemCategoryFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     selectorNodeItemCategoryValue.getPrimaryKey());
            
            selectorNodeItemCategory.setThruTime(session.START_TIME_LONG);
            selectorNodeItemCategory.store();
            
            SelectorNode selectorNode = selectorNodeItemCategory.getSelectorNode();
            ItemCategoryPK itemCategoryPK = selectorNodeItemCategoryValue.getItemCategoryPK();
            Boolean checkParents = selectorNodeItemCategoryValue.getCheckParents();
            
            selectorNodeItemCategory = SelectorNodeItemCategoryFactory.getInstance().create(selectorNode.getPrimaryKey(),
                    itemCategoryPK, checkParents, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(selectorNode.getLastDetail().getSelectorPK(), EventTypes.MODIFY.name(),
                    selectorNodeItemCategory.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteSelectorNodeItemCategory(SelectorNodeItemCategory selectorNodeItemCategory, BasePK deletedBy) {
        selectorNodeItemCategory.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(selectorNodeItemCategory.getSelectorNode().getLastDetail().getSelectorPK(),
                EventTypes.MODIFY.name(), selectorNodeItemCategory.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Node Item Accounting Categories
    // --------------------------------------------------------------------------------
    
    public SelectorNodeItemAccountingCategory createSelectorNodeItemAccountingCategory(SelectorNode selectorNode,
            ItemAccountingCategory itemAccountingCategory, Boolean checkParents, BasePK createdBy) {
        SelectorNodeItemAccountingCategory selectorNodeItemAccountingCategory = SelectorNodeItemAccountingCategoryFactory.getInstance().create(session,
                selectorNode, itemAccountingCategory, checkParents, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(selectorNode.getLastDetail().getSelectorPK(), EventTypes.MODIFY.name(),
                selectorNodeItemAccountingCategory.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return selectorNodeItemAccountingCategory;
    }
    
    private SelectorNodeItemAccountingCategory getSelectorNodeItemAccountingCategory(SelectorNode selectorNode, EntityPermission entityPermission) {
        SelectorNodeItemAccountingCategory selectorNodeItemAccountingCategory;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornodeitemaccountingcategories " +
                        "WHERE slndiactgc_slnd_selectornodeid = ? AND slndiactgc_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornodeitemaccountingcategories " +
                        "WHERE slndiactgc_slnd_selectornodeid = ? AND slndiactgc_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SelectorNodeItemAccountingCategoryFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, selectorNode.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            selectorNodeItemAccountingCategory = SelectorNodeItemAccountingCategoryFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return selectorNodeItemAccountingCategory;
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
        SelectorNodeItemAccountingCategory selectorNodeItemAccountingCategory = getSelectorNodeItemAccountingCategoryForUpdate(selectorNode);
        
        return selectorNodeItemAccountingCategory == null? null: selectorNodeItemAccountingCategory.getSelectorNodeItemAccountingCategoryValue().clone();
    }
    
    private List<SelectorNodeItemAccountingCategory> getSelectorNodeItemAccountingCategoriesBySelector(Selector selector, EntityPermission entityPermission) {
        List<SelectorNodeItemAccountingCategory> selectorNodeItemAccountingCategories;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornodes, selectornodedetails, selectornodeitemaccountingcategories " +
                        "WHERE slnd_activedetailid = slnddt_selectornodedetailid AND slnddt_sl_selectorid = ? " +
                        "AND slnd_selectornodeid = slndiactgc_slnd_selectornodeid AND slndiactgc_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornodes, selectornodedetails, selectornodeitemaccountingcategories " +
                        "WHERE slnd_activedetailid = slnddt_selectornodedetailid AND slnddt_sl_selectorid = ? " +
                        "AND slnd_selectornodeid = slndiactgc_slnd_selectornodeid AND slndiactgc_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SelectorNodeItemAccountingCategoryFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, selector.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            selectorNodeItemAccountingCategories = SelectorNodeItemAccountingCategoryFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return selectorNodeItemAccountingCategories;
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
            SelectorNodeItemAccountingCategory selectorNodeItemAccountingCategory = SelectorNodeItemAccountingCategoryFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     selectorNodeItemAccountingCategoryValue.getPrimaryKey());
            
            selectorNodeItemAccountingCategory.setThruTime(session.START_TIME_LONG);
            selectorNodeItemAccountingCategory.store();
            
            SelectorNode selectorNode = selectorNodeItemAccountingCategory.getSelectorNode();
            ItemAccountingCategoryPK itemAccountingCategoryPK = selectorNodeItemAccountingCategoryValue.getItemAccountingCategoryPK();
            Boolean checkParents = selectorNodeItemAccountingCategoryValue.getCheckParents();
            
            selectorNodeItemAccountingCategory = SelectorNodeItemAccountingCategoryFactory.getInstance().create(selectorNode.getPrimaryKey(),
                    itemAccountingCategoryPK, checkParents, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(selectorNode.getLastDetail().getSelectorPK(), EventTypes.MODIFY.name(),
                    selectorNodeItemAccountingCategory.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteSelectorNodeItemAccountingCategory(SelectorNodeItemAccountingCategory selectorNodeItemAccountingCategory,
            BasePK deletedBy) {
        selectorNodeItemAccountingCategory.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(selectorNodeItemAccountingCategory.getSelectorNode().getLastDetail().getSelectorPK(),
                EventTypes.MODIFY.name(), selectorNodeItemAccountingCategory.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Node Item Purchasing Categories
    // --------------------------------------------------------------------------------
    
    public SelectorNodeItemPurchasingCategory createSelectorNodeItemPurchasingCategory(SelectorNode selectorNode,
            ItemPurchasingCategory itemPurchasingCategory, Boolean checkParents, BasePK createdBy) {
        SelectorNodeItemPurchasingCategory selectorNodeItemPurchasingCategory = SelectorNodeItemPurchasingCategoryFactory.getInstance().create(session,
                selectorNode, itemPurchasingCategory, checkParents, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(selectorNode.getLastDetail().getSelectorPK(), EventTypes.MODIFY.name(),
                selectorNodeItemPurchasingCategory.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return selectorNodeItemPurchasingCategory;
    }
    
    private SelectorNodeItemPurchasingCategory getSelectorNodeItemPurchasingCategory(SelectorNode selectorNode, EntityPermission entityPermission) {
        SelectorNodeItemPurchasingCategory selectorNodeItemPurchasingCategory;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornodeitempurchasingcategories " +
                        "WHERE slndiprchc_slnd_selectornodeid = ? AND slndiprchc_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornodeitempurchasingcategories " +
                        "WHERE slndiprchc_slnd_selectornodeid = ? AND slndiprchc_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SelectorNodeItemPurchasingCategoryFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, selectorNode.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            selectorNodeItemPurchasingCategory = SelectorNodeItemPurchasingCategoryFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return selectorNodeItemPurchasingCategory;
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
        SelectorNodeItemPurchasingCategory selectorNodeItemPurchasingCategory = getSelectorNodeItemPurchasingCategoryForUpdate(selectorNode);
        
        return selectorNodeItemPurchasingCategory == null? null: selectorNodeItemPurchasingCategory.getSelectorNodeItemPurchasingCategoryValue().clone();
    }
    
    private List<SelectorNodeItemPurchasingCategory> getSelectorNodeItemPurchasingCategoriesBySelector(Selector selector, EntityPermission entityPermission) {
        List<SelectorNodeItemPurchasingCategory> selectorNodeItemPurchasingCategories;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornodes, selectornodedetails, selectornodeitempurchasingcategories " +
                        "WHERE slnd_activedetailid = slnddt_selectornodedetailid AND slnddt_sl_selectorid = ? " +
                        "AND slnd_selectornodeid = slndiprchc_slnd_selectornodeid AND slndiprchc_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornodes, selectornodedetails, selectornodeitempurchasingcategories " +
                        "WHERE slnd_activedetailid = slnddt_selectornodedetailid AND slnddt_sl_selectorid = ? " +
                        "AND slnd_selectornodeid = slndiprchc_slnd_selectornodeid AND slndiprchc_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SelectorNodeItemPurchasingCategoryFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, selector.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            selectorNodeItemPurchasingCategories = SelectorNodeItemPurchasingCategoryFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return selectorNodeItemPurchasingCategories;
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
            SelectorNodeItemPurchasingCategory selectorNodeItemPurchasingCategory = SelectorNodeItemPurchasingCategoryFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     selectorNodeItemPurchasingCategoryValue.getPrimaryKey());
            
            selectorNodeItemPurchasingCategory.setThruTime(session.START_TIME_LONG);
            selectorNodeItemPurchasingCategory.store();
            
            SelectorNode selectorNode = selectorNodeItemPurchasingCategory.getSelectorNode();
            ItemPurchasingCategoryPK itemPurchasingCategoryPK = selectorNodeItemPurchasingCategoryValue.getItemPurchasingCategoryPK();
            Boolean checkParents = selectorNodeItemPurchasingCategoryValue.getCheckParents();
            
            selectorNodeItemPurchasingCategory = SelectorNodeItemPurchasingCategoryFactory.getInstance().create(selectorNode.getPrimaryKey(),
                    itemPurchasingCategoryPK, checkParents, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(selectorNode.getLastDetail().getSelectorPK(), EventTypes.MODIFY.name(),
                    selectorNodeItemPurchasingCategory.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteSelectorNodeItemPurchasingCategory(SelectorNodeItemPurchasingCategory selectorNodeItemPurchasingCategory,
            BasePK deletedBy) {
        selectorNodeItemPurchasingCategory.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(selectorNodeItemPurchasingCategory.getSelectorNode().getLastDetail().getSelectorPK(),
                EventTypes.MODIFY.name(), selectorNodeItemPurchasingCategory.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Node Geo Codes
    // --------------------------------------------------------------------------------
    
    public SelectorNodeGeoCode createSelectorNodeGeoCode(SelectorNode selectorNode, GeoCode geoCode,
            BasePK createdBy) {
        SelectorNodeGeoCode selectorNodeGeoCode = SelectorNodeGeoCodeFactory.getInstance().create(session,
                selectorNode, geoCode, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(selectorNode.getLastDetail().getSelectorPK(), EventTypes.MODIFY.name(),
                selectorNodeGeoCode.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return selectorNodeGeoCode;
    }
    
    public long countSelectorNodeGeoCodesByGeoCode(GeoCode geoCode) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM selectornodegeocodes " +
                "WHERE slndgeo_geo_geocodeid = ? AND slndgeo_thrutime = ?",
                geoCode, Session.MAX_TIME_LONG);
    }

    private SelectorNodeGeoCode getSelectorNodeGeoCode(SelectorNode selectorNode, EntityPermission entityPermission) {
        SelectorNodeGeoCode selectorNodeGeoCode;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornodegeocodes " +
                        "WHERE slndgeo_slnd_selectornodeid = ? AND slndgeo_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornodegeocodes " +
                        "WHERE slndgeo_slnd_selectornodeid = ? AND slndgeo_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SelectorNodeGeoCodeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, selectorNode.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            selectorNodeGeoCode = SelectorNodeGeoCodeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return selectorNodeGeoCode;
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
        SelectorNodeGeoCode selectorNodeGeoCode = getSelectorNodeGeoCodeForUpdate(selectorNode);
        
        return selectorNodeGeoCode == null? null: selectorNodeGeoCode.getSelectorNodeGeoCodeValue().clone();
    }
    
    private List<SelectorNodeGeoCode> getSelectorNodeGeoCodesBySelector(Selector selector, EntityPermission entityPermission) {
        List<SelectorNodeGeoCode> selectorNodeGeoCodes;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornodes, selectornodedetails, selectornodegeocodes " +
                        "WHERE slnd_activedetailid = slnddt_selectornodedetailid AND slnddt_sl_selectorid = ? " +
                        "AND slnd_selectornodeid = slndgeo_slnd_selectornodeid AND slndgeo_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornodes, selectornodedetails, selectornodegeocodes " +
                        "WHERE slnd_activedetailid = slnddt_selectornodedetailid AND slnddt_sl_selectorid = ? " +
                        "AND slnd_selectornodeid = slndgeo_slnd_selectornodeid AND slndgeo_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SelectorNodeGeoCodeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, selector.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            selectorNodeGeoCodes = SelectorNodeGeoCodeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return selectorNodeGeoCodes;
    }
    
    public List<SelectorNodeGeoCode> getSelectorNodeGeoCodesBySelector(Selector selector) {
        return getSelectorNodeGeoCodesBySelector(selector, EntityPermission.READ_ONLY);
    }
    
    public List<SelectorNodeGeoCode> getSelectorNodeGeoCodesBySelectorForUpdate(Selector selector) {
        return getSelectorNodeGeoCodesBySelector(selector, EntityPermission.READ_WRITE);
    }
    
    public void updateSelectorNodeGeoCodeFromValue(SelectorNodeGeoCodeValue selectorNodeGeoCodeValue, BasePK updatedBy) {
        if(selectorNodeGeoCodeValue.hasBeenModified()) {
            SelectorNodeGeoCode selectorNodeGeoCode = SelectorNodeGeoCodeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     selectorNodeGeoCodeValue.getPrimaryKey());
            
            selectorNodeGeoCode.setThruTime(session.START_TIME_LONG);
            selectorNodeGeoCode.store();
            
            SelectorNode selectorNode = selectorNodeGeoCode.getSelectorNode();
            GeoCodePK geoCodePK = selectorNodeGeoCodeValue.getGeoCodePK();
            
            selectorNodeGeoCode = SelectorNodeGeoCodeFactory.getInstance().create(selectorNode.getPrimaryKey(),
                    geoCodePK, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(selectorNode.getLastDetail().getSelectorPK(), EventTypes.MODIFY.name(),
                    selectorNodeGeoCode.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteSelectorNodeGeoCode(SelectorNodeGeoCode selectorNodeGeoCode, BasePK deletedBy) {
        selectorNodeGeoCode.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(selectorNodeGeoCode.getSelectorNode().getLastDetail().getSelectorPK(),
                EventTypes.MODIFY.name(), selectorNodeGeoCode.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Node Payment Methods
    // --------------------------------------------------------------------------------
    
    public SelectorNodePaymentMethod createSelectorNodePaymentMethod(SelectorNode selectorNode, PaymentMethod paymentMethod,
            BasePK createdBy) {
        SelectorNodePaymentMethod selectorNodePaymentMethod = SelectorNodePaymentMethodFactory.getInstance().create(session,
                selectorNode, paymentMethod, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(selectorNode.getLastDetail().getSelectorPK(), EventTypes.MODIFY.name(),
                selectorNodePaymentMethod.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return selectorNodePaymentMethod;
    }
    
    private SelectorNodePaymentMethod getSelectorNodePaymentMethod(SelectorNode selectorNode, EntityPermission entityPermission) {
        SelectorNodePaymentMethod selectorNodePaymentMethod;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornodepaymentmethods " +
                        "WHERE slndpm_slnd_selectornodeid = ? AND slndpm_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornodepaymentmethods " +
                        "WHERE slndpm_slnd_selectornodeid = ? AND slndpm_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SelectorNodePaymentMethodFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, selectorNode.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            selectorNodePaymentMethod = SelectorNodePaymentMethodFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return selectorNodePaymentMethod;
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
        SelectorNodePaymentMethod selectorNodePaymentMethod = getSelectorNodePaymentMethodForUpdate(selectorNode);
        
        return selectorNodePaymentMethod == null? null: selectorNodePaymentMethod.getSelectorNodePaymentMethodValue().clone();
    }
    
    private List<SelectorNodePaymentMethod> getSelectorNodePaymentMethodsBySelector(Selector selector, EntityPermission entityPermission) {
        List<SelectorNodePaymentMethod> selectorNodePaymentMethods;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornodes, selectornodedetails, selectornodepaymentmethods " +
                        "WHERE slnd_activedetailid = slnddt_selectornodedetailid AND slnddt_sl_selectorid = ? " +
                        "AND slnd_selectornodeid = slndpm_slnd_selectornodeid AND slndpm_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornodes, selectornodedetails, selectornodepaymentmethods " +
                        "WHERE slnd_activedetailid = slnddt_selectornodedetailid AND slnddt_sl_selectorid = ? " +
                        "AND slnd_selectornodeid = slndpm_slnd_selectornodeid AND slndpm_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SelectorNodePaymentMethodFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, selector.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            selectorNodePaymentMethods = SelectorNodePaymentMethodFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return selectorNodePaymentMethods;
    }
    
    public List<SelectorNodePaymentMethod> getSelectorNodePaymentMethodsBySelector(Selector selector) {
        return getSelectorNodePaymentMethodsBySelector(selector, EntityPermission.READ_ONLY);
    }
    
    public List<SelectorNodePaymentMethod> getSelectorNodePaymentMethodsBySelectorForUpdate(Selector selector) {
        return getSelectorNodePaymentMethodsBySelector(selector, EntityPermission.READ_WRITE);
    }
    
    public void updateSelectorNodePaymentMethodFromValue(SelectorNodePaymentMethodValue selectorNodePaymentMethodValue, BasePK updatedBy) {
        if(selectorNodePaymentMethodValue.hasBeenModified()) {
            SelectorNodePaymentMethod selectorNodePaymentMethod = SelectorNodePaymentMethodFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     selectorNodePaymentMethodValue.getPrimaryKey());
            
            selectorNodePaymentMethod.setThruTime(session.START_TIME_LONG);
            selectorNodePaymentMethod.store();
            
            SelectorNode selectorNode = selectorNodePaymentMethod.getSelectorNode();
            PaymentMethodPK paymentMethodPK = selectorNodePaymentMethodValue.getPaymentMethodPK();
            
            selectorNodePaymentMethod = SelectorNodePaymentMethodFactory.getInstance().create(selectorNode.getPrimaryKey(),
                    paymentMethodPK, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(selectorNode.getLastDetail().getSelectorPK(), EventTypes.MODIFY.name(),
                    selectorNodePaymentMethod.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteSelectorNodePaymentMethod(SelectorNodePaymentMethod selectorNodePaymentMethod, BasePK deletedBy) {
        selectorNodePaymentMethod.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(selectorNodePaymentMethod.getSelectorNode().getLastDetail().getSelectorPK(),
                EventTypes.MODIFY.name(), selectorNodePaymentMethod.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Node Payment Processors
    // --------------------------------------------------------------------------------
    
    public SelectorNodePaymentProcessor createSelectorNodePaymentProcessor(SelectorNode selectorNode, PaymentProcessor paymentProcessor,
            BasePK createdBy) {
        SelectorNodePaymentProcessor selectorNodePaymentProcessor = SelectorNodePaymentProcessorFactory.getInstance().create(session,
                selectorNode, paymentProcessor, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(selectorNode.getLastDetail().getSelectorPK(), EventTypes.MODIFY.name(),
                selectorNodePaymentProcessor.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return selectorNodePaymentProcessor;
    }
    
    private SelectorNodePaymentProcessor getSelectorNodePaymentProcessor(SelectorNode selectorNode, EntityPermission entityPermission) {
        SelectorNodePaymentProcessor selectorNodePaymentProcessor;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornodepaymentprocessors " +
                        "WHERE slndpprc_slnd_selectornodeid = ? AND slndpprc_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornodepaymentprocessors " +
                        "WHERE slndpprc_slnd_selectornodeid = ? AND slndpprc_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SelectorNodePaymentProcessorFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, selectorNode.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            selectorNodePaymentProcessor = SelectorNodePaymentProcessorFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return selectorNodePaymentProcessor;
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
        SelectorNodePaymentProcessor selectorNodePaymentProcessor = getSelectorNodePaymentProcessorForUpdate(selectorNode);
        
        return selectorNodePaymentProcessor == null? null: selectorNodePaymentProcessor.getSelectorNodePaymentProcessorValue().clone();
    }
    
    private List<SelectorNodePaymentProcessor> getSelectorNodePaymentProcessorsBySelector(Selector selector, EntityPermission entityPermission) {
        List<SelectorNodePaymentProcessor> selectorNodePaymentProcessors;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornodes, selectornodedetails, selectornodepaymentprocessors " +
                        "WHERE slnd_activedetailid = slnddt_selectornodedetailid AND slnddt_sl_selectorid = ? " +
                        "AND slnd_selectornodeid = slndpprc_slnd_selectornodeid AND slndpprc_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM selectornodes, selectornodedetails, selectornodepaymentprocessors " +
                        "WHERE slnd_activedetailid = slnddt_selectornodedetailid AND slnddt_sl_selectorid = ? " +
                        "AND slnd_selectornodeid = slndpprc_slnd_selectornodeid AND slndpprc_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SelectorNodePaymentProcessorFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, selector.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            selectorNodePaymentProcessors = SelectorNodePaymentProcessorFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return selectorNodePaymentProcessors;
    }
    
    public List<SelectorNodePaymentProcessor> getSelectorNodePaymentProcessorsBySelector(Selector selector) {
        return getSelectorNodePaymentProcessorsBySelector(selector, EntityPermission.READ_ONLY);
    }
    
    public List<SelectorNodePaymentProcessor> getSelectorNodePaymentProcessorsBySelectorForUpdate(Selector selector) {
        return getSelectorNodePaymentProcessorsBySelector(selector, EntityPermission.READ_WRITE);
    }
    
    public void updateSelectorNodePaymentProcessorFromValue(SelectorNodePaymentProcessorValue selectorNodePaymentProcessorValue, BasePK updatedBy) {
        if(selectorNodePaymentProcessorValue.hasBeenModified()) {
            SelectorNodePaymentProcessor selectorNodePaymentProcessor = SelectorNodePaymentProcessorFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     selectorNodePaymentProcessorValue.getPrimaryKey());
            
            selectorNodePaymentProcessor.setThruTime(session.START_TIME_LONG);
            selectorNodePaymentProcessor.store();
            
            SelectorNode selectorNode = selectorNodePaymentProcessor.getSelectorNode();
            PaymentProcessorPK paymentProcessorPK = selectorNodePaymentProcessorValue.getPaymentProcessorPK();
            
            selectorNodePaymentProcessor = SelectorNodePaymentProcessorFactory.getInstance().create(selectorNode.getPrimaryKey(),
                    paymentProcessorPK, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(selectorNode.getLastDetail().getSelectorPK(), EventTypes.MODIFY.name(),
                    selectorNodePaymentProcessor.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteSelectorNodePaymentProcessor(SelectorNodePaymentProcessor selectorNodePaymentProcessor, BasePK deletedBy) {
        selectorNodePaymentProcessor.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(selectorNodePaymentProcessor.getSelectorNode().getLastDetail().getSelectorPK(),
                EventTypes.MODIFY.name(), selectorNodePaymentProcessor.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Parties
    // --------------------------------------------------------------------------------
    
    public SelectorParty createSelectorParty(Selector selector, Party party, BasePK createdBy) {
        SelectorParty selectorParty = SelectorPartyFactory.getInstance().create(selector, party, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        return selectorParty;
    }
    
    private SelectorParty getSelectorParty(Selector selector, Party party, EntityPermission entityPermission) {
        SelectorParty selectorParty;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM selectorparties " +
                        "WHERE slpar_sl_selectorid = ? AND slpar_par_partyid = ? AND slpar_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM selectorparties " +
                        "WHERE slpar_sl_selectorid = ? AND slpar_par_partyid = ? AND slpar_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SelectorPartyFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, selector.getPrimaryKey().getEntityId());
            ps.setLong(2, party.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            selectorParty = SelectorPartyFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return selectorParty;
    }
    
    public SelectorParty getSelectorParty(Selector selector, Party party) {
        return getSelectorParty(selector, party, EntityPermission.READ_ONLY);
    }
    
    public SelectorParty getSelectorPartyForUpdate(Selector selector, Party party) {
        return getSelectorParty(selector, party, EntityPermission.READ_WRITE);
    }
    
    public SelectorPartyValue getSelectorPartyValueForUpdate(Selector selector, Party party) {
        SelectorParty selectorParty = getSelectorPartyForUpdate(selector, party);
        
        return selectorParty == null? null: selectorParty.getSelectorPartyValue().clone();
    }
    
    private List<SelectorParty> getSelectorPartiesBySelector(Selector selector, EntityPermission entityPermission) {
        List<SelectorParty> selectorParties;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM selectorparties, parties, partydetails " +
                        "WHERE slpar_sl_selectorid = ? AND slpar_thrutime = ? " +
                        "AND slpar_par_partyid = par_partyid AND par_activedetailid = pardt_partydetailid " +
                        "ORDER BY pardt_partyname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM selectorparties " +
                        "WHERE slpar_sl_selectorid = ? AND slpar_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SelectorPartyFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, selector.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            selectorParties = SelectorPartyFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return selectorParties;
    }
    
    public List<SelectorParty> getSelectorPartiesBySelector(Selector selector) {
        return getSelectorPartiesBySelector(selector, EntityPermission.READ_ONLY);
    }
    
    public List<SelectorParty> getSelectorPartiesBySelectorForUpdate(Selector selector) {
        return getSelectorPartiesBySelector(selector, EntityPermission.READ_WRITE);
    }
    
    public SelectorPartyTransfer getSelectorPartyTransfer(UserVisit userVisit, SelectorParty selectorParty) {
        return getSelectorTransferCaches(userVisit).getSelectorPartyTransferCache().getSelectorPartyTransfer(selectorParty);
    }
    
    public List<SelectorPartyTransfer> getSelectorPartyTransfers(UserVisit userVisit, Selector selector) {
        List<SelectorParty> selectorParties = getSelectorPartiesBySelector(selector);
        List<SelectorPartyTransfer> selectorPartyTransfers = new ArrayList<>(selectorParties.size());
        SelectorPartyTransferCache selectorPartyTransferCache = getSelectorTransferCaches(userVisit).getSelectorPartyTransferCache();
        
        selectorParties.forEach((selectorParty) ->
                selectorPartyTransfers.add(selectorPartyTransferCache.getSelectorPartyTransfer(selectorParty))
        );
        
        return selectorPartyTransfers;
    }
    
    public void deleteSelectorParty(SelectorParty selectorParty, BasePK deletedBy) {
        selectorParty.setThruTime(session.START_TIME_LONG);
    }
    
    public void deleteSelectorPartiesBySelector(Selector selector, BasePK deletedBy) {
        List<SelectorParty> selectorParties = getSelectorPartiesBySelectorForUpdate(selector);
        
        selectorParties.forEach((selectorParty) -> 
                deleteSelectorParty(selectorParty, deletedBy)
        );
    }
    
}
