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

package com.echothree.model.control.uom.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.inventory.server.control.BucketControl;
import com.echothree.model.control.inventory.server.control.InventoryLocationControl;
import com.echothree.model.control.inventory.server.control.InventoryTransactionLineControl;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.uom.common.choice.UnitOfMeasureChoicesBean;
import com.echothree.model.control.uom.common.choice.UnitOfMeasureKindChoicesBean;
import com.echothree.model.control.uom.common.choice.UnitOfMeasureKindUseTypeChoicesBean;
import com.echothree.model.control.uom.common.choice.UnitOfMeasureTypeChoicesBean;
import com.echothree.model.control.uom.common.transfer.UnitOfMeasureEquivalentTransfer;
import com.echothree.model.control.uom.common.transfer.UnitOfMeasureKindDescriptionTransfer;
import com.echothree.model.control.uom.common.transfer.UnitOfMeasureKindTransfer;
import com.echothree.model.control.uom.common.transfer.UnitOfMeasureKindUseTransfer;
import com.echothree.model.control.uom.common.transfer.UnitOfMeasureKindUseTypeTransfer;
import com.echothree.model.control.uom.common.transfer.UnitOfMeasureTypeDescriptionTransfer;
import com.echothree.model.control.uom.common.transfer.UnitOfMeasureTypeTransfer;
import com.echothree.model.control.uom.common.transfer.UnitOfMeasureTypeVolumeTransfer;
import com.echothree.model.control.uom.common.transfer.UnitOfMeasureTypeWeightTransfer;
import com.echothree.model.control.uom.server.transfer.UnitOfMeasureEquivalentTransferCache;
import com.echothree.model.control.uom.server.transfer.UnitOfMeasureKindDescriptionTransferCache;
import com.echothree.model.control.uom.server.transfer.UnitOfMeasureKindTransferCache;
import com.echothree.model.control.uom.server.transfer.UnitOfMeasureKindUseTransferCache;
import com.echothree.model.control.uom.server.transfer.UnitOfMeasureKindUseTypeTransferCache;
import com.echothree.model.control.uom.server.transfer.UnitOfMeasureTypeDescriptionTransferCache;
import com.echothree.model.control.uom.server.transfer.UnitOfMeasureTypeTransferCache;
import com.echothree.model.control.uom.server.transfer.UnitOfMeasureTypeVolumeTransferCache;
import com.echothree.model.control.uom.server.transfer.UnitOfMeasureTypeWeightTransferCache;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.data.accounting.server.entity.SymbolPosition;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.uom.common.pk.UnitOfMeasureKindPK;
import com.echothree.model.data.uom.common.pk.UnitOfMeasureKindUsePK;
import com.echothree.model.data.uom.common.pk.UnitOfMeasureKindUseTypePK;
import com.echothree.model.data.uom.common.pk.UnitOfMeasureTypePK;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureEquivalent;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindDescription;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindUse;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindUseType;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindUseTypeDescription;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureTypeDescription;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureTypeVolume;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureTypeWeight;
import com.echothree.model.data.uom.server.factory.UnitOfMeasureEquivalentFactory;
import com.echothree.model.data.uom.server.factory.UnitOfMeasureKindDescriptionFactory;
import com.echothree.model.data.uom.server.factory.UnitOfMeasureKindDetailFactory;
import com.echothree.model.data.uom.server.factory.UnitOfMeasureKindFactory;
import com.echothree.model.data.uom.server.factory.UnitOfMeasureKindUseFactory;
import com.echothree.model.data.uom.server.factory.UnitOfMeasureKindUseTypeDescriptionFactory;
import com.echothree.model.data.uom.server.factory.UnitOfMeasureKindUseTypeFactory;
import com.echothree.model.data.uom.server.factory.UnitOfMeasureTypeDescriptionFactory;
import com.echothree.model.data.uom.server.factory.UnitOfMeasureTypeDetailFactory;
import com.echothree.model.data.uom.server.factory.UnitOfMeasureTypeFactory;
import com.echothree.model.data.uom.server.factory.UnitOfMeasureTypeVolumeFactory;
import com.echothree.model.data.uom.server.factory.UnitOfMeasureTypeWeightFactory;
import com.echothree.model.data.uom.server.value.UnitOfMeasureEquivalentValue;
import com.echothree.model.data.uom.server.value.UnitOfMeasureKindDescriptionValue;
import com.echothree.model.data.uom.server.value.UnitOfMeasureKindDetailValue;
import com.echothree.model.data.uom.server.value.UnitOfMeasureKindUseValue;
import com.echothree.model.data.uom.server.value.UnitOfMeasureTypeDescriptionValue;
import com.echothree.model.data.uom.server.value.UnitOfMeasureTypeDetailValue;
import com.echothree.model.data.uom.server.value.UnitOfMeasureTypeVolumeValue;
import com.echothree.model.data.uom.server.value.UnitOfMeasureTypeWeightValue;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.uom.UomForeignKeys.UNIT_OF_MEASURE_EQUIVALENTS_FROM_UNIT_OF_MEASURE_TYPE_FK;
import static com.echothree.model.jooq.server.keys.uom.UomForeignKeys.UNIT_OF_MEASURE_EQUIVALENTS_TO_UNIT_OF_MEASURE_TYPE_FK;
import static com.echothree.model.jooq.server.keys.uom.UomForeignKeys.UNIT_OF_MEASURE_KINDS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.uom.UomForeignKeys.UNIT_OF_MEASURE_KINDS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.uom.UomForeignKeys.UNIT_OF_MEASURE_KIND_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.uom.UomForeignKeys.UNIT_OF_MEASURE_KIND_DETAILS_UNIT_OF_MEASURE_KIND_FK;
import static com.echothree.model.jooq.server.keys.uom.UomForeignKeys.UNIT_OF_MEASURE_KIND_USES_UNIT_OF_MEASURE_KIND_FK;
import static com.echothree.model.jooq.server.keys.uom.UomForeignKeys.UNIT_OF_MEASURE_KIND_USES_UNIT_OF_MEASURE_KIND_USE_TYPE_FK;
import static com.echothree.model.jooq.server.keys.uom.UomForeignKeys.UNIT_OF_MEASURE_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.uom.UomForeignKeys.UNIT_OF_MEASURE_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.uom.UomForeignKeys.UNIT_OF_MEASURE_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.uom.UomForeignKeys.UNIT_OF_MEASURE_TYPE_DETAILS_UNIT_OF_MEASURE_TYPE_FK;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import static com.echothree.model.jooq.server.tables.uom.UnitOfMeasureEquivalents.UnitOfMeasureEquivalents;
import static com.echothree.model.jooq.server.tables.uom.UnitOfMeasureKindDescriptions.UnitOfMeasureKindDescriptions;
import static com.echothree.model.jooq.server.tables.uom.UnitOfMeasureKindDetails.UnitOfMeasureKindDetails;
import static com.echothree.model.jooq.server.tables.uom.UnitOfMeasureKindUseTypeDescriptions.UnitOfMeasureKindUseTypeDescriptions;
import static com.echothree.model.jooq.server.tables.uom.UnitOfMeasureKindUseTypes.UnitOfMeasureKindUseTypes;
import static com.echothree.model.jooq.server.tables.uom.UnitOfMeasureKindUses.UnitOfMeasureKindUses;
import static com.echothree.model.jooq.server.tables.uom.UnitOfMeasureKinds.UnitOfMeasureKinds;
import static com.echothree.model.jooq.server.tables.uom.UnitOfMeasureTypeDescriptions.UnitOfMeasureTypeDescriptions;
import static com.echothree.model.jooq.server.tables.uom.UnitOfMeasureTypeDetails.UnitOfMeasureTypeDetails;
import static com.echothree.model.jooq.server.tables.uom.UnitOfMeasureTypeVolumes.UnitOfMeasureTypeVolumes;
import static com.echothree.model.jooq.server.tables.uom.UnitOfMeasureTypeWeights.UnitOfMeasureTypeWeights;
import static com.echothree.model.jooq.server.tables.uom.UnitOfMeasureTypes.UnitOfMeasureTypes;
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
public class UomControl
        extends BaseModelControl {

    @Inject
    protected BucketControl bucketControl;

    @Inject
    protected InventoryLocationControl inventoryLocationControl;

    @Inject
    protected InventoryTransactionLineControl inventoryTransactionLineControl;

    @Inject
    protected ItemControl itemControl;

    @Inject
    protected VendorControl vendorControl;

    /** Creates a new instance of UomControl */
    protected UomControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Kinds
    // --------------------------------------------------------------------------------

    @Inject
    protected UnitOfMeasureKindDetailFactory unitOfMeasureKindDetailFactory;

    @Inject
    protected UnitOfMeasureKindFactory unitOfMeasureKindFactory;

    @Inject
    private UnitOfMeasureKindTransferCache unitOfMeasureKindTransferCache;

    public UnitOfMeasureKind createUnitOfMeasureKind(String unitOfMeasureKindName, Integer fractionDigits, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var defaultUnitOfMeasureKind = getDefaultUnitOfMeasureKind();
        var defaultFound = defaultUnitOfMeasureKind != null;
        
        if(defaultFound && isDefault) {
            var defaultUnitOfMeasureKindDetailValue = getDefaultUnitOfMeasureKindDetailValueForUpdate();
            
            defaultUnitOfMeasureKindDetailValue.setIsDefault(false);
            updateUnitOfMeasureKindFromValue(defaultUnitOfMeasureKindDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var unitOfMeasureKind = unitOfMeasureKindFactory.create();
        var unitOfMeasureKindDetail = unitOfMeasureKindDetailFactory.create( unitOfMeasureKind, unitOfMeasureKindName,
                fractionDigits, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        unitOfMeasureKind = unitOfMeasureKindFactory.getEntityFromPK(EntityPermission.READ_WRITE, unitOfMeasureKind.getPrimaryKey());
        unitOfMeasureKind.setActiveDetail(unitOfMeasureKindDetail);
        unitOfMeasureKind.setLastDetail(unitOfMeasureKindDetail);
        unitOfMeasureKind.store();
        
        sendEvent(unitOfMeasureKind.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return unitOfMeasureKind;
    }

    public long countUnitOfMeasureKinds() {
        return session.getDslContext()
                .selectCount()
                .from(UnitOfMeasureKinds)
                .join(UnitOfMeasureKindDetails).onKey(UNIT_OF_MEASURE_KINDS_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.UnitOfMeasureKind */
    public UnitOfMeasureKind getUnitOfMeasureKindByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new UnitOfMeasureKindPK(entityInstance.getEntityUniqueId());

        return unitOfMeasureKindFactory.getEntityFromPK(entityPermission, pk);
    }

    public UnitOfMeasureKind getUnitOfMeasureKindByEntityInstance(EntityInstance entityInstance) {
        return getUnitOfMeasureKindByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public UnitOfMeasureKind getUnitOfMeasureKindByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getUnitOfMeasureKindByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    private List<UnitOfMeasureKind> getUnitOfMeasureKinds(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(UnitOfMeasureKinds.fields())
                .from(UnitOfMeasureKinds)
                .join(UnitOfMeasureKindDetails).onKey(UNIT_OF_MEASURE_KINDS_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(UnitOfMeasureKindDetails.SORT_ORDER, UnitOfMeasureKindDetails.UNIT_OF_MEASURE_KIND_NAME),
                    UnitOfMeasureKindFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return unitOfMeasureKindFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<UnitOfMeasureKind> getUnitOfMeasureKinds() {
        return getUnitOfMeasureKinds(EntityPermission.READ_ONLY);
    }
    
    public List<UnitOfMeasureKind> getUnitOfMeasureKindsForUpdate() {
        return getUnitOfMeasureKinds(EntityPermission.READ_WRITE);
    }
    
    public List<UnitOfMeasureKind> getUnitOfMeasureKindsByUnitOfMeasureKindUseType(UnitOfMeasureKindUseType unitOfMeasureKindUseType) {
        var query = session.applyLimit(session.getDslContext()
                .select(UnitOfMeasureKinds.fields())
                .from(UnitOfMeasureKinds)
                .join(UnitOfMeasureKindDetails).onKey(UNIT_OF_MEASURE_KIND_DETAILS_UNIT_OF_MEASURE_KIND_FK)
                .join(UnitOfMeasureKindUses).onKey(UNIT_OF_MEASURE_KIND_USES_UNIT_OF_MEASURE_KIND_FK)
                .where(UnitOfMeasureKindDetails.THRU_TIME.eq(Session.MAX_TIME),
                        UnitOfMeasureKindUses.UNIT_OF_MEASURE_KIND_USE_TYPE.eq(unitOfMeasureKindUseType.getPrimaryKey()),
                        UnitOfMeasureKindUses.THRU_TIME.eq(Session.MAX_TIME))
                .orderBy(UnitOfMeasureKindDetails.SORT_ORDER, UnitOfMeasureKindDetails.UNIT_OF_MEASURE_KIND_NAME),
                UnitOfMeasureKindFactory.class);

        return unitOfMeasureKindFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    private UnitOfMeasureKind getDefaultUnitOfMeasureKind(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(UnitOfMeasureKinds.fields())
                .from(UnitOfMeasureKinds)
                .join(UnitOfMeasureKindDetails).onKey(UNIT_OF_MEASURE_KINDS_ACTIVE_DETAIL_FK)
                .where(UnitOfMeasureKindDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return unitOfMeasureKindFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public UnitOfMeasureKind getDefaultUnitOfMeasureKind() {
        return getDefaultUnitOfMeasureKind(EntityPermission.READ_ONLY);
    }
    
    public UnitOfMeasureKind getDefaultUnitOfMeasureKindForUpdate() {
        return getDefaultUnitOfMeasureKind(EntityPermission.READ_WRITE);
    }
    
    public UnitOfMeasureKindDetailValue getDefaultUnitOfMeasureKindDetailValueForUpdate() {
        return getDefaultUnitOfMeasureKindForUpdate().getLastDetailForUpdate().getUnitOfMeasureKindDetailValue().clone();
    }
    
    public UnitOfMeasureKind getUnitOfMeasureKindByName(String unitOfMeasureKindName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(UnitOfMeasureKinds.fields())
                .from(UnitOfMeasureKinds)
                .join(UnitOfMeasureKindDetails).onKey(UNIT_OF_MEASURE_KIND_DETAILS_UNIT_OF_MEASURE_KIND_FK)
                .where(UnitOfMeasureKindDetails.UNIT_OF_MEASURE_KIND_NAME.eq(unitOfMeasureKindName),
                        UnitOfMeasureKindDetails.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return unitOfMeasureKindFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public UnitOfMeasureKind getUnitOfMeasureKindByName(String unitOfMeasureKindName) {
        return getUnitOfMeasureKindByName(unitOfMeasureKindName, EntityPermission.READ_ONLY);
    }
    
    public UnitOfMeasureKind getUnitOfMeasureKindByNameForUpdate(String unitOfMeasureKindName) {
        return getUnitOfMeasureKindByName(unitOfMeasureKindName, EntityPermission.READ_WRITE);
    }
    
    public UnitOfMeasureKindDetailValue getUnitOfMeasureKindDetailValueForUpdate(UnitOfMeasureKind unitOfMeasureKind) {
        return unitOfMeasureKind == null? null: unitOfMeasureKind.getLastDetailForUpdate().getUnitOfMeasureKindDetailValue().clone();
    }
    
    public UnitOfMeasureKindDetailValue getUnitOfMeasureKindDetailValueByNameForUpdate(String unitOfMeasureKindName) {
        return getUnitOfMeasureKindDetailValueForUpdate(getUnitOfMeasureKindByNameForUpdate(unitOfMeasureKindName));
    }
    
    public UnitOfMeasureKindTransfer getUnitOfMeasureKindTransfer(UserVisit userVisit, UnitOfMeasureKind unitOfMeasureKind) {
        return unitOfMeasureKindTransferCache.getUnitOfMeasureKindTransfer(userVisit, unitOfMeasureKind);
    }
    
    public List<UnitOfMeasureKindTransfer> getUnitOfMeasureKindTransfers(UserVisit userVisit, Collection<UnitOfMeasureKind> unitOfMeasureKinds) {
        List<UnitOfMeasureKindTransfer> unitOfMeasureKindTransfers = new ArrayList<>(unitOfMeasureKinds.size());
        
        unitOfMeasureKinds.forEach((unitOfMeasureKind) ->
                unitOfMeasureKindTransfers.add(unitOfMeasureKindTransferCache.getUnitOfMeasureKindTransfer(userVisit, unitOfMeasureKind))
        );
        
        return unitOfMeasureKindTransfers;
    }
    
    public List<UnitOfMeasureKindTransfer> getUnitOfMeasureKindTransfers(UserVisit userVisit) {
        return getUnitOfMeasureKindTransfers(userVisit, getUnitOfMeasureKinds());
    }
    
    public UnitOfMeasureKindChoicesBean getUnitOfMeasureKindChoices(String defaultUnitOfMeasureKindChoice, Language language,
            boolean allowNullChoice) {
        var unitOfMeasureKinds = getUnitOfMeasureKinds();
        
        return getUnitOfMeasureKindChoices(defaultUnitOfMeasureKindChoice, language, allowNullChoice, unitOfMeasureKinds);
    }
    
    public UnitOfMeasureKindChoicesBean getUnitOfMeasureKindChoicesByUnitOfMeasureKindUseType(String defaultUnitOfMeasureKindChoice, Language language,
            boolean allowNullChoice, UnitOfMeasureKindUseType unitOfMeasureKindUseType) {
        var unitOfMeasureKinds = getUnitOfMeasureKindsByUnitOfMeasureKindUseType(unitOfMeasureKindUseType);
        
        return getUnitOfMeasureKindChoices(defaultUnitOfMeasureKindChoice, language, allowNullChoice, unitOfMeasureKinds);
    }
    
    private UnitOfMeasureKindChoicesBean getUnitOfMeasureKindChoices(final String defaultUnitOfMeasureKindChoice,
            final Language language, final boolean allowNullChoice, final List<UnitOfMeasureKind> unitOfMeasureKinds) {
        var size = unitOfMeasureKinds.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultUnitOfMeasureKindChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var unitOfMeasureKind : unitOfMeasureKinds) {
            var unitOfMeasureKindDetail = unitOfMeasureKind.getLastDetail();
            
            var label = getBestUnitOfMeasureKindDescription(unitOfMeasureKind, language);
            var value = unitOfMeasureKindDetail.getUnitOfMeasureKindName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultUnitOfMeasureKindChoice != null && defaultUnitOfMeasureKindChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && unitOfMeasureKindDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new UnitOfMeasureKindChoicesBean(labels, values, defaultValue);
    }
    
    public UnitOfMeasureChoicesBean getUnitOfMeasureChoicesByUnitOfMeasureKindUseType(String defaultUnitOfMeasureChoice,
            Language language, UnitOfMeasureKindUseType unitOfMeasureKindUseType) {
        var unitOfMeasureKinds = getUnitOfMeasureKindsByUnitOfMeasureKindUseType(unitOfMeasureKindUseType);
        var labels = new ArrayList<String>();
        var values = new ArrayList<String>();
        String defaultValue = null;
        var uomkIter = unitOfMeasureKinds.iterator();
        
        while(uomkIter.hasNext()) {
            var unitOfMeasureKind = uomkIter.next();
            var uomkDescription = getBestUnitOfMeasureKindDescription(unitOfMeasureKind, language);
            var uomkName = unitOfMeasureKind.getLastDetail().getUnitOfMeasureKindName();
            var unitOfMeasureTypes = getUnitOfMeasureTypesByUnitOfMeasureKind(unitOfMeasureKind);
            var uomtIter = unitOfMeasureTypes.iterator();
            
            if(uomkDescription == null) {
                uomkDescription = uomkName;
            }
            
            while(uomtIter.hasNext()) {
                var unitOfMeasureType = uomtIter.next();
                var uomtDescription = getBestSingularUnitOfMeasureTypeDescription(unitOfMeasureType, language);
                var uomtName = unitOfMeasureType.getLastDetail().getUnitOfMeasureTypeName();
                
                if(uomtDescription == null) {
                    uomtDescription = uomtName;
                }
                
                var label = uomkDescription + ", " + uomtDescription;
                var value = uomkName + ':' + uomtName;
                
                labels.add(label == null? value: label);
                values.add(value);
                
                var usingDefaultChoice = Objects.equals(defaultUnitOfMeasureChoice, value);
                if(usingDefaultChoice || defaultValue == null)
                    defaultValue = value;
            }
        }
        
        return new UnitOfMeasureChoicesBean(labels, values, defaultValue);
    }
    
    private void updateUnitOfMeasureKindFromValue(UnitOfMeasureKindDetailValue unitOfMeasureKindDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(unitOfMeasureKindDetailValue.hasBeenModified()) {
            var unitOfMeasureKind = unitOfMeasureKindFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     unitOfMeasureKindDetailValue.getUnitOfMeasureKindPK());
            var unitOfMeasureKindDetail = unitOfMeasureKind.getActiveDetailForUpdate();
            
            unitOfMeasureKindDetail.setThruTime(session.getStartTime());
            unitOfMeasureKindDetail.store();

            var unitOfMeasureKindPK = unitOfMeasureKindDetail.getUnitOfMeasureKindPK();
            var unitOfMeasureKindName = unitOfMeasureKindDetailValue.getUnitOfMeasureKindName();
            var fractionDigits = unitOfMeasureKindDetailValue.getFractionDigits();
            var isDefault = unitOfMeasureKindDetailValue.getIsDefault();
            var sortOrder = unitOfMeasureKindDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultUnitOfMeasureKind = getDefaultUnitOfMeasureKind();
                var defaultFound = defaultUnitOfMeasureKind != null && !defaultUnitOfMeasureKind.equals(unitOfMeasureKind);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultUnitOfMeasureKindDetailValue = getDefaultUnitOfMeasureKindDetailValueForUpdate();
                    
                    defaultUnitOfMeasureKindDetailValue.setIsDefault(false);
                    updateUnitOfMeasureKindFromValue(defaultUnitOfMeasureKindDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            unitOfMeasureKindDetail = unitOfMeasureKindDetailFactory.create(unitOfMeasureKindPK, unitOfMeasureKindName, fractionDigits, isDefault,
                    sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            unitOfMeasureKind.setActiveDetail(unitOfMeasureKindDetail);
            unitOfMeasureKind.setLastDetail(unitOfMeasureKindDetail);
            
            sendEvent(unitOfMeasureKindPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateUnitOfMeasureKindFromValue(UnitOfMeasureKindDetailValue unitOfMeasureKindDetailValue, BasePK updatedBy) {
        updateUnitOfMeasureKindFromValue(unitOfMeasureKindDetailValue, true, updatedBy);
    }
    
    public void deleteUnitOfMeasureKind(UnitOfMeasureKind unitOfMeasureKind, BasePK deletedBy) {
        deleteUnitOfMeasureKindUseByUnitOfMeasureKind(unitOfMeasureKind, deletedBy);
        deleteUnitOfMeasureKindDescriptionsByUnitOfMeasureKind(unitOfMeasureKind, deletedBy);
        deleteUnitOfMeasureTypesByUnitOfMeasureKind(unitOfMeasureKind, deletedBy);

        var unitOfMeasureKindDetail = unitOfMeasureKind.getLastDetailForUpdate();
        unitOfMeasureKindDetail.setThruTime(session.getStartTime());
        unitOfMeasureKindDetail.store();
        unitOfMeasureKind.setActiveDetail(null);
        
        // Check for default, and pick one if necessary
        var defaultUnitOfMeasureKind = getDefaultUnitOfMeasureKind();
        if(defaultUnitOfMeasureKind == null) {
            var unitOfMeasureKinds = getUnitOfMeasureKindsForUpdate();
            
            if(!unitOfMeasureKinds.isEmpty()) {
                var iter = unitOfMeasureKinds.iterator();
                if(iter.hasNext()) {
                    defaultUnitOfMeasureKind = (UnitOfMeasureKind)iter.next();
                }
                var unitOfMeasureKindDetailValue = Objects.requireNonNull(defaultUnitOfMeasureKind).getLastDetailForUpdate().getUnitOfMeasureKindDetailValue().clone();
                
                unitOfMeasureKindDetailValue.setIsDefault(true);
                updateUnitOfMeasureKindFromValue(unitOfMeasureKindDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(unitOfMeasureKind.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Kind Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected UnitOfMeasureKindDescriptionFactory unitOfMeasureKindDescriptionFactory;

    @Inject
    private UnitOfMeasureKindDescriptionTransferCache unitOfMeasureKindDescriptionTransferCache;

    public UnitOfMeasureKindDescription createUnitOfMeasureKindDescription(UnitOfMeasureKind unitOfMeasureKind, Language language, String description, BasePK createdBy) {
        var unitOfMeasureKindDescription = unitOfMeasureKindDescriptionFactory.create(unitOfMeasureKind, language, description,
                session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(unitOfMeasureKind.getPrimaryKey(), EventTypes.MODIFY, unitOfMeasureKindDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return unitOfMeasureKindDescription;
    }
    
    public UnitOfMeasureKindDescription getUnitOfMeasureKindDescription(UnitOfMeasureKind unitOfMeasureKind, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(UnitOfMeasureKindDescriptions.fields())
                .from(UnitOfMeasureKindDescriptions)
                .where(UnitOfMeasureKindDescriptions.UNIT_OF_MEASURE_KIND.eq(unitOfMeasureKind.getPrimaryKey()),
                        UnitOfMeasureKindDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        UnitOfMeasureKindDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return unitOfMeasureKindDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    
    public UnitOfMeasureKindDescription getUnitOfMeasureKindDescription(UnitOfMeasureKind unitOfMeasureKind, Language language) {
        return getUnitOfMeasureKindDescription(unitOfMeasureKind, language, EntityPermission.READ_ONLY);
    }
    
    public UnitOfMeasureKindDescription getUnitOfMeasureKindDescriptionForUpdate(UnitOfMeasureKind unitOfMeasureKind, Language language) {
        return getUnitOfMeasureKindDescription(unitOfMeasureKind, language, EntityPermission.READ_WRITE);
    }
    
    public UnitOfMeasureKindDescriptionValue getUnitOfMeasureKindDescriptionValue(UnitOfMeasureKindDescription unitOfMeasureKindDescription) {
        return unitOfMeasureKindDescription == null? null: unitOfMeasureKindDescription.getUnitOfMeasureKindDescriptionValue().clone();
    }
    
    public UnitOfMeasureKindDescriptionValue getUnitOfMeasureKindDescriptionValueForUpdate(UnitOfMeasureKind unitOfMeasureKind, Language language) {
        return getUnitOfMeasureKindDescriptionValue(getUnitOfMeasureKindDescriptionForUpdate(unitOfMeasureKind, language));
    }
    
    private List<UnitOfMeasureKindDescription> getUnitOfMeasureKindDescriptionsByUnitOfMeasureKind(UnitOfMeasureKind unitOfMeasureKind, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(UnitOfMeasureKindDescriptions.fields())
                    .from(UnitOfMeasureKindDescriptions)
                    .join(Languages).onKey(UNIT_OF_MEASURE_KIND_DESCRIPTIONS_LANGUAGE_FK)
                    .where(UnitOfMeasureKindDescriptions.UNIT_OF_MEASURE_KIND.eq(unitOfMeasureKind.getPrimaryKey()),
                            UnitOfMeasureKindDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    UnitOfMeasureKindDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(UnitOfMeasureKindDescriptions.fields())
                    .from(UnitOfMeasureKindDescriptions)
                    .where(UnitOfMeasureKindDescriptions.UNIT_OF_MEASURE_KIND.eq(unitOfMeasureKind.getPrimaryKey()),
                            UnitOfMeasureKindDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return unitOfMeasureKindDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<UnitOfMeasureKindDescription> getUnitOfMeasureKindDescriptionsByUnitOfMeasureKind(UnitOfMeasureKind unitOfMeasureKind) {
        return getUnitOfMeasureKindDescriptionsByUnitOfMeasureKind(unitOfMeasureKind, EntityPermission.READ_ONLY);
    }
    
    public List<UnitOfMeasureKindDescription> getUnitOfMeasureKindDescriptionsByUnitOfMeasureKindForUpdate(UnitOfMeasureKind unitOfMeasureKind) {
        return getUnitOfMeasureKindDescriptionsByUnitOfMeasureKind(unitOfMeasureKind, EntityPermission.READ_WRITE);
    }
    
    public String getBestUnitOfMeasureKindDescription(UnitOfMeasureKind unitOfMeasureKind, Language language) {
        String description;
        var unitOfMeasureKindDescription = getUnitOfMeasureKindDescription(unitOfMeasureKind, language);
        
        if(unitOfMeasureKindDescription == null && !language.getIsDefault()) {
            unitOfMeasureKindDescription = getUnitOfMeasureKindDescription(unitOfMeasureKind, partyControl.getDefaultLanguage());
        }
        
        if(unitOfMeasureKindDescription == null) {
            description = unitOfMeasureKind.getLastDetail().getUnitOfMeasureKindName();
        } else {
            description = unitOfMeasureKindDescription.getDescription();
        }
        
        return description;
    }
    
    public UnitOfMeasureKindDescriptionTransfer getUnitOfMeasureKindDescriptionTransfer(UserVisit userVisit, UnitOfMeasureKindDescription unitOfMeasureKindDescription) {
        return unitOfMeasureKindDescriptionTransferCache.getUnitOfMeasureKindDescriptionTransfer(userVisit, unitOfMeasureKindDescription);
    }
    
    public List<UnitOfMeasureKindDescriptionTransfer> getUnitOfMeasureKindDescriptionTransfers(UserVisit userVisit, UnitOfMeasureKind unitOfMeasureKind) {
        var unitOfMeasureKindDescriptions = getUnitOfMeasureKindDescriptionsByUnitOfMeasureKind(unitOfMeasureKind);
        List<UnitOfMeasureKindDescriptionTransfer> unitOfMeasureKindDescriptionTransfers = new ArrayList<>(unitOfMeasureKindDescriptions.size());
        
        unitOfMeasureKindDescriptions.forEach((unitOfMeasureKindDescription) ->
                unitOfMeasureKindDescriptionTransfers.add(unitOfMeasureKindDescriptionTransferCache.getUnitOfMeasureKindDescriptionTransfer(userVisit, unitOfMeasureKindDescription))
        );
        
        return unitOfMeasureKindDescriptionTransfers;
    }
    
    public void updateUnitOfMeasureKindDescriptionFromValue(UnitOfMeasureKindDescriptionValue unitOfMeasureKindDescriptionValue, BasePK updatedBy) {
        if(unitOfMeasureKindDescriptionValue.hasBeenModified()) {
            var unitOfMeasureKindDescription = unitOfMeasureKindDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     unitOfMeasureKindDescriptionValue.getPrimaryKey());
            
            unitOfMeasureKindDescription.setThruTime(session.getStartTime());
            unitOfMeasureKindDescription.store();

            var unitOfMeasureKind = unitOfMeasureKindDescription.getUnitOfMeasureKind();
            var language = unitOfMeasureKindDescription.getLanguage();
            var description = unitOfMeasureKindDescriptionValue.getDescription();
            
            unitOfMeasureKindDescription = unitOfMeasureKindDescriptionFactory.create(unitOfMeasureKind,
                    language, description, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(unitOfMeasureKind.getPrimaryKey(), EventTypes.MODIFY, unitOfMeasureKindDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteUnitOfMeasureKindDescription(UnitOfMeasureKindDescription unitOfMeasureKindDescription, BasePK deletedBy) {
        unitOfMeasureKindDescription.setThruTime(session.getStartTime());
        
        sendEvent(unitOfMeasureKindDescription.getUnitOfMeasureKindPK(), EventTypes.MODIFY, unitOfMeasureKindDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteUnitOfMeasureKindDescriptionsByUnitOfMeasureKind(UnitOfMeasureKind unitOfMeasureKind, BasePK deletedBy) {
        var unitOfMeasureKindDescriptions = getUnitOfMeasureKindDescriptionsByUnitOfMeasureKindForUpdate(unitOfMeasureKind);
        
        unitOfMeasureKindDescriptions.forEach((unitOfMeasureKindDescription) -> 
                deleteUnitOfMeasureKindDescription(unitOfMeasureKindDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Types
    // --------------------------------------------------------------------------------

    @Inject
    protected UnitOfMeasureTypeDetailFactory unitOfMeasureTypeDetailFactory;

    @Inject
    protected UnitOfMeasureTypeFactory unitOfMeasureTypeFactory;

    @Inject
    private UnitOfMeasureTypeTransferCache unitOfMeasureTypeTransferCache;

    public UnitOfMeasureType createUnitOfMeasureType(UnitOfMeasureKind unitOfMeasureKind, String unitOfMeasureTypeName,
            SymbolPosition symbolPosition, Boolean suppressSymbolSeparator, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var defaultUnitOfMeasureType = getDefaultUnitOfMeasureType(unitOfMeasureKind);
        var defaultFound = defaultUnitOfMeasureType != null;
        
        if(defaultFound && isDefault) {
            var defaultUnitOfMeasureTypeDetailValue = getDefaultUnitOfMeasureTypeDetailValueForUpdate(unitOfMeasureKind);
            
            defaultUnitOfMeasureTypeDetailValue.setIsDefault(false);
            updateUnitOfMeasureTypeFromValue(defaultUnitOfMeasureTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var unitOfMeasureType = unitOfMeasureTypeFactory.create();
        var unitOfMeasureTypeDetail = unitOfMeasureTypeDetailFactory.create(unitOfMeasureType,
                unitOfMeasureKind, unitOfMeasureTypeName, symbolPosition, suppressSymbolSeparator, isDefault, sortOrder,
                session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        unitOfMeasureType = unitOfMeasureTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE, unitOfMeasureType.getPrimaryKey());
        unitOfMeasureType.setActiveDetail(unitOfMeasureTypeDetail);
        unitOfMeasureType.setLastDetail(unitOfMeasureTypeDetail);
        unitOfMeasureType.store();
        
        sendEvent(unitOfMeasureType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return unitOfMeasureType;
    }
    
    public long countUnitOfMeasureTypesByUnitOfMeasureKind(UnitOfMeasureKind unitOfMeasureKind) {
        return session.getDslContext()
                .selectCount()
                .from(UnitOfMeasureTypes)
                .join(UnitOfMeasureTypeDetails).onKey(UNIT_OF_MEASURE_TYPE_DETAILS_UNIT_OF_MEASURE_TYPE_FK)
                .where(UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_KIND.eq(unitOfMeasureKind.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }
    
    /** Assume that the entityInstance passed to this function is a ECHO_THREE.UnitOfMeasureType */
    public UnitOfMeasureType getUnitOfMeasureTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new UnitOfMeasureTypePK(entityInstance.getEntityUniqueId());

        return unitOfMeasureTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public UnitOfMeasureType getUnitOfMeasureTypeByEntityInstance(EntityInstance entityInstance) {
        return getUnitOfMeasureTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public UnitOfMeasureType getUnitOfMeasureTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getUnitOfMeasureTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    private List<UnitOfMeasureType> getUnitOfMeasureTypesByUnitOfMeasureKind(UnitOfMeasureKind unitOfMeasureKind, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(UnitOfMeasureTypes.fields())
                .from(UnitOfMeasureTypes)
                .join(UnitOfMeasureTypeDetails).onKey(UNIT_OF_MEASURE_TYPE_DETAILS_UNIT_OF_MEASURE_TYPE_FK)
                .where(UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_KIND.eq(unitOfMeasureKind.getPrimaryKey()),
                        UnitOfMeasureTypeDetails.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(UnitOfMeasureTypeDetails.SORT_ORDER, UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_TYPE_NAME),
                    UnitOfMeasureTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return unitOfMeasureTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<UnitOfMeasureType> getUnitOfMeasureTypesByUnitOfMeasureKind(UnitOfMeasureKind unitOfMeasureKind) {
        return getUnitOfMeasureTypesByUnitOfMeasureKind(unitOfMeasureKind, EntityPermission.READ_ONLY);
    }
    
    public List<UnitOfMeasureType> getUnitOfMeasureTypesByUnitOfMeasureKindForUpdate(UnitOfMeasureKind unitOfMeasureKind) {
        return getUnitOfMeasureTypesByUnitOfMeasureKind(unitOfMeasureKind, EntityPermission.READ_WRITE);
    }
    
    private UnitOfMeasureType getDefaultUnitOfMeasureType(UnitOfMeasureKind unitOfMeasureKind, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(UnitOfMeasureTypes.fields())
                .from(UnitOfMeasureTypes)
                .join(UnitOfMeasureTypeDetails).onKey(UNIT_OF_MEASURE_TYPES_ACTIVE_DETAIL_FK)
                .where(UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_KIND.eq(unitOfMeasureKind.getPrimaryKey()),
                        UnitOfMeasureTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return unitOfMeasureTypeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public UnitOfMeasureType getDefaultUnitOfMeasureType(UnitOfMeasureKind unitOfMeasureKind) {
        return getDefaultUnitOfMeasureType(unitOfMeasureKind, EntityPermission.READ_ONLY);
    }
    
    public UnitOfMeasureType getDefaultUnitOfMeasureTypeForUpdate(UnitOfMeasureKind unitOfMeasureKind) {
        return getDefaultUnitOfMeasureType(unitOfMeasureKind, EntityPermission.READ_WRITE);
    }
    
    public UnitOfMeasureTypeDetailValue getDefaultUnitOfMeasureTypeDetailValueForUpdate(UnitOfMeasureKind unitOfMeasureKind) {
        return getDefaultUnitOfMeasureTypeForUpdate(unitOfMeasureKind).getLastDetailForUpdate().getUnitOfMeasureTypeDetailValue().clone();
    }
    
    public UnitOfMeasureType getUnitOfMeasureTypeByName(UnitOfMeasureKind unitOfMeasureKind, String unitOfMeasureTypeName,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(UnitOfMeasureTypes.fields())
                .from(UnitOfMeasureTypes)
                .join(UnitOfMeasureTypeDetails).onKey(UNIT_OF_MEASURE_TYPE_DETAILS_UNIT_OF_MEASURE_TYPE_FK)
                .where(UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_KIND.eq(unitOfMeasureKind.getPrimaryKey()),
                        UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_TYPE_NAME.eq(unitOfMeasureTypeName),
                        UnitOfMeasureTypeDetails.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return unitOfMeasureTypeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public UnitOfMeasureType getUnitOfMeasureTypeByName(UnitOfMeasureKind unitOfMeasureKind, String unitOfMeasureTypeName) {
        return getUnitOfMeasureTypeByName(unitOfMeasureKind, unitOfMeasureTypeName, EntityPermission.READ_ONLY);
    }
    
    public UnitOfMeasureType getUnitOfMeasureTypeByNameForUpdate(UnitOfMeasureKind unitOfMeasureKind, String unitOfMeasureTypeName) {
        return getUnitOfMeasureTypeByName(unitOfMeasureKind, unitOfMeasureTypeName, EntityPermission.READ_WRITE);
    }
    
    public UnitOfMeasureTypeDetailValue getUnitOfMeasureTypeDetailValueForUpdate(UnitOfMeasureType unitOfMeasureType) {
        return unitOfMeasureType == null? null: unitOfMeasureType.getLastDetailForUpdate().getUnitOfMeasureTypeDetailValue().clone();
    }
    
    public UnitOfMeasureTypeDetailValue getUnitOfMeasureTypeDetailValueByNameForUpdate(UnitOfMeasureKind unitOfMeasureKind,
            String unitOfMeasureTypeName) {
        return getUnitOfMeasureTypeDetailValueForUpdate(getUnitOfMeasureTypeByNameForUpdate(unitOfMeasureKind, unitOfMeasureTypeName));
    }
    
    public UnitOfMeasureTypeTransfer getUnitOfMeasureTypeTransfer(UserVisit userVisit, UnitOfMeasureType unitOfMeasureType) {
        return unitOfMeasureTypeTransferCache.getUnitOfMeasureTypeTransfer(userVisit, unitOfMeasureType);
    }
    
    public List<UnitOfMeasureTypeTransfer> getUnitOfMeasureTypeTransfers(UserVisit userVisit, Collection<UnitOfMeasureType> unitOfMeasureTypes) {
        List<UnitOfMeasureTypeTransfer> unitOfMeasureTypeTransfers = new ArrayList<>(unitOfMeasureTypes.size());
        
        unitOfMeasureTypes.forEach((unitOfMeasureType) ->
                unitOfMeasureTypeTransfers.add(unitOfMeasureTypeTransferCache.getUnitOfMeasureTypeTransfer(userVisit, unitOfMeasureType))
        );
        
        return unitOfMeasureTypeTransfers;
    }
    
    public List<UnitOfMeasureTypeTransfer> getUnitOfMeasureTypeTransfersByUnitOfMeasureKind(UserVisit userVisit, UnitOfMeasureKind unitOfMeasureKind) {
        return getUnitOfMeasureTypeTransfers(userVisit, getUnitOfMeasureTypesByUnitOfMeasureKind(unitOfMeasureKind));
    }
    
    public UnitOfMeasureTypeChoicesBean getUnitOfMeasureTypeChoices(String defaultUnitOfMeasureTypeChoice, Language language,
            boolean allowNullChoice, UnitOfMeasureKind unitOfMeasureKind) {
        var unitOfMeasureTypes = getUnitOfMeasureTypesByUnitOfMeasureKind(unitOfMeasureKind);
        var size = unitOfMeasureTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultUnitOfMeasureTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var unitOfMeasureType : unitOfMeasureTypes) {
            var unitOfMeasureTypeDetail = unitOfMeasureType.getLastDetail();
            
            var label = getBestSingularUnitOfMeasureTypeDescription(unitOfMeasureType, language);
            var value = unitOfMeasureTypeDetail.getUnitOfMeasureTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultUnitOfMeasureTypeChoice != null && defaultUnitOfMeasureTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && unitOfMeasureTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new UnitOfMeasureTypeChoicesBean(labels, values, defaultValue);
    }
    
    private void updateUnitOfMeasureTypeFromValue(UnitOfMeasureTypeDetailValue unitOfMeasureTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(unitOfMeasureTypeDetailValue.hasBeenModified()) {
            var unitOfMeasureType = unitOfMeasureTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     unitOfMeasureTypeDetailValue.getUnitOfMeasureTypePK());
            var unitOfMeasureTypeDetail = unitOfMeasureType.getActiveDetailForUpdate();
            
            unitOfMeasureTypeDetail.setThruTime(session.getStartTime());
            unitOfMeasureTypeDetail.store();

            var unitOfMeasureTypePK = unitOfMeasureTypeDetail.getUnitOfMeasureTypePK();
            var unitOfMeasureKindPK = unitOfMeasureTypeDetail.getUnitOfMeasureKindPK();
            var unitOfMeasureKind = unitOfMeasureTypeDetail.getUnitOfMeasureKind();
            var unitOfMeasureTypeName = unitOfMeasureTypeDetailValue.getUnitOfMeasureTypeName();
            var symbolPositionPK = unitOfMeasureTypeDetailValue.getSymbolPositionPK();
            var suppressSymbolSeparator = unitOfMeasureTypeDetailValue.getSuppressSymbolSeparator();
            var isDefault = unitOfMeasureTypeDetailValue.getIsDefault();
            var sortOrder = unitOfMeasureTypeDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultUnitOfMeasureType = getDefaultUnitOfMeasureType(unitOfMeasureKind);
                var defaultFound = defaultUnitOfMeasureType != null && !defaultUnitOfMeasureType.equals(unitOfMeasureType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultUnitOfMeasureTypeDetailValue = getDefaultUnitOfMeasureTypeDetailValueForUpdate(unitOfMeasureKind);
                    
                    defaultUnitOfMeasureTypeDetailValue.setIsDefault(false);
                    updateUnitOfMeasureTypeFromValue(defaultUnitOfMeasureTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            unitOfMeasureTypeDetail = unitOfMeasureTypeDetailFactory.create(unitOfMeasureTypePK, unitOfMeasureKindPK,
                    unitOfMeasureTypeName, symbolPositionPK, suppressSymbolSeparator, isDefault, sortOrder, session.getStartTime(),
                    Session.MAX_TIME);
            
            unitOfMeasureType.setActiveDetail(unitOfMeasureTypeDetail);
            unitOfMeasureType.setLastDetail(unitOfMeasureTypeDetail);
            
            sendEvent(unitOfMeasureTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateUnitOfMeasureTypeFromValue(UnitOfMeasureTypeDetailValue unitOfMeasureTypeDetailValue, BasePK updatedBy) {
        updateUnitOfMeasureTypeFromValue(unitOfMeasureTypeDetailValue, true, updatedBy);
    }
    
    public void deleteUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType, BasePK deletedBy) {
        
        deleteUnitOfMeasureEquivalentsByUnitOfMeasureType(unitOfMeasureType, deletedBy);
        deleteUnitOfMeasureTypeDescriptionsByUnitOfMeasureType(unitOfMeasureType, deletedBy);
        deleteUnitOfMeasureTypeVolumeByUnitOfMeasureType(unitOfMeasureType, deletedBy);
        deleteUnitOfMeasureTypeWeightByUnitOfMeasureType(unitOfMeasureType, deletedBy);
        bucketControl.removePartyBucketsByUnitOfMeasureType(unitOfMeasureType, deletedBy);
        inventoryLocationControl.deleteInventoryLocationsByUnitOfMeasureType(unitOfMeasureType, deletedBy);
        inventoryTransactionLineControl.deleteInventoryTransactionLineSourcesBySourceUnitOfMeasureType(unitOfMeasureType, deletedBy);
        inventoryTransactionLineControl.deleteInventoryTransactionLineDestinationsByDestinationUnitOfMeasureType(unitOfMeasureType, deletedBy);
        itemControl.deleteItemUnitOfMeasureTypesByUnitOfMeasureType(unitOfMeasureType, deletedBy);
        vendorControl.deleteVendorItemCostsByUnitOfMeasureType(unitOfMeasureType, deletedBy);

        var unitOfMeasureTypeDetail = unitOfMeasureType.getLastDetailForUpdate();
        unitOfMeasureTypeDetail.setThruTime(session.getStartTime());
        unitOfMeasureTypeDetail.store();
        unitOfMeasureType.setActiveDetail(null);
        
        // Check for default, and pick one if necessary
        var unitOfMeasureKind = unitOfMeasureTypeDetail.getUnitOfMeasureKind();
        var defaultUnitOfMeasureType = getDefaultUnitOfMeasureType(unitOfMeasureKind);
        if(defaultUnitOfMeasureType == null) {
            var unitOfMeasureTypes = getUnitOfMeasureTypesByUnitOfMeasureKindForUpdate(unitOfMeasureKind);
            
            if(!unitOfMeasureTypes.isEmpty()) {
                var iter = unitOfMeasureTypes.iterator();
                if(iter.hasNext()) {
                    defaultUnitOfMeasureType = (UnitOfMeasureType)iter.next();
                }
                var unitOfMeasureTypeDetailValue = Objects.requireNonNull(defaultUnitOfMeasureType).getLastDetailForUpdate().getUnitOfMeasureTypeDetailValue().clone();
                
                unitOfMeasureTypeDetailValue.setIsDefault(true);
                updateUnitOfMeasureTypeFromValue(unitOfMeasureTypeDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(unitOfMeasureType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteUnitOfMeasureTypesByUnitOfMeasureKind(UnitOfMeasureKind unitOfMeasureKind, BasePK deletedBy) {
        var unitOfMeasureTypes = getUnitOfMeasureTypesByUnitOfMeasureKindForUpdate(unitOfMeasureKind);
        
        unitOfMeasureTypes.forEach((unitOfMeasureType) -> 
                deleteUnitOfMeasureType(unitOfMeasureType, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected UnitOfMeasureTypeDescriptionFactory unitOfMeasureTypeDescriptionFactory;

    @Inject
    private UnitOfMeasureTypeDescriptionTransferCache unitOfMeasureTypeDescriptionTransferCache;

    public UnitOfMeasureTypeDescription createUnitOfMeasureTypeDescription(UnitOfMeasureType unitOfMeasureType, Language language,
            String singularDescription, String pluralDescription, String symbol, BasePK createdBy) {
        var unitOfMeasureTypeDescription = unitOfMeasureTypeDescriptionFactory.create(
                unitOfMeasureType, language, singularDescription, pluralDescription, symbol, session.getStartTime(),
                Session.MAX_TIME);
        
        sendEvent(unitOfMeasureType.getLastDetail().getUnitOfMeasureTypePK(), EventTypes.MODIFY, unitOfMeasureTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return unitOfMeasureTypeDescription;
    }
    
    public UnitOfMeasureTypeDescription getUnitOfMeasureTypeDescription(UnitOfMeasureType unitOfMeasureType, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(UnitOfMeasureTypeDescriptions.fields())
                .from(UnitOfMeasureTypeDescriptions)
                .where(UnitOfMeasureTypeDescriptions.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                        UnitOfMeasureTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        UnitOfMeasureTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return unitOfMeasureTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public UnitOfMeasureTypeDescription getUnitOfMeasureTypeDescription(UnitOfMeasureType unitOfMeasureType, Language language) {
        return getUnitOfMeasureTypeDescription(unitOfMeasureType, language, EntityPermission.READ_ONLY);
    }
    
    public UnitOfMeasureTypeDescription getUnitOfMeasureTypeDescriptionForUpdate(UnitOfMeasureType unitOfMeasureType, Language language) {
        return getUnitOfMeasureTypeDescription(unitOfMeasureType, language, EntityPermission.READ_WRITE);
    }
    
    public UnitOfMeasureTypeDescriptionValue getUnitOfMeasureTypeDescriptionValue(UnitOfMeasureTypeDescription unitOfMeasureTypeDescription) {
        return unitOfMeasureTypeDescription == null? null: unitOfMeasureTypeDescription.getUnitOfMeasureTypeDescriptionValue().clone();
    }
    
    public UnitOfMeasureTypeDescriptionValue getUnitOfMeasureTypeDescriptionValueForUpdate(UnitOfMeasureType unitOfMeasureType, Language language) {
        return getUnitOfMeasureTypeDescriptionValue(getUnitOfMeasureTypeDescriptionForUpdate(unitOfMeasureType, language));
    }
    
    private List<UnitOfMeasureTypeDescription> getUnitOfMeasureTypeDescriptionsByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(UnitOfMeasureTypeDescriptions.fields())
                    .from(UnitOfMeasureTypeDescriptions)
                    .join(Languages).onKey(UNIT_OF_MEASURE_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(UnitOfMeasureTypeDescriptions.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                            UnitOfMeasureTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    UnitOfMeasureTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(UnitOfMeasureTypeDescriptions.fields())
                    .from(UnitOfMeasureTypeDescriptions)
                    .where(UnitOfMeasureTypeDescriptions.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                            UnitOfMeasureTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return unitOfMeasureTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<UnitOfMeasureTypeDescription> getUnitOfMeasureTypeDescriptionsByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType) {
        return getUnitOfMeasureTypeDescriptionsByUnitOfMeasureType(unitOfMeasureType, EntityPermission.READ_ONLY);
    }
    
    public List<UnitOfMeasureTypeDescription> getUnitOfMeasureTypeDescriptionsByUnitOfMeasureTypeForUpdate(UnitOfMeasureType unitOfMeasureType) {
        return getUnitOfMeasureTypeDescriptionsByUnitOfMeasureType(unitOfMeasureType, EntityPermission.READ_WRITE);
    }
    
    public UnitOfMeasureTypeDescription getBestUnitOfMeasureTypeDescription(UnitOfMeasureType unitOfMeasureType, Language language) {
        var unitOfMeasureTypeDescription = getUnitOfMeasureTypeDescription(unitOfMeasureType, language);
        
        if(unitOfMeasureTypeDescription == null && !language.getIsDefault()) {
            unitOfMeasureTypeDescription = getUnitOfMeasureTypeDescription(unitOfMeasureType, partyControl.getDefaultLanguage());
        }
        
        return unitOfMeasureTypeDescription;
    }
    
    public String getBestSingularUnitOfMeasureTypeDescription(UnitOfMeasureType unitOfMeasureType, UnitOfMeasureTypeDescription unitOfMeasureTypeDescription) {
        String description;
        
        if(unitOfMeasureTypeDescription == null) {
            description = unitOfMeasureType.getLastDetail().getUnitOfMeasureTypeName();
        } else {
            description = unitOfMeasureTypeDescription.getSingularDescription();
        }
        
        return description;
    }
    
    public String getBestSingularUnitOfMeasureTypeDescription(UnitOfMeasureType unitOfMeasureType, Language language) {
        return getBestSingularUnitOfMeasureTypeDescription(unitOfMeasureType, getBestUnitOfMeasureTypeDescription(unitOfMeasureType, language));
    }
    
    public String getBestPluralUnitOfMeasureTypeDescription(UnitOfMeasureType unitOfMeasureType, UnitOfMeasureTypeDescription unitOfMeasureTypeDescription) {
        String description;
        
        if(unitOfMeasureTypeDescription == null) {
            description = unitOfMeasureType.getLastDetail().getUnitOfMeasureTypeName();
        } else {
            description = unitOfMeasureTypeDescription.getPluralDescription();
        }
        
        return description;
    }
    
    public String getBestPluralUnitOfMeasureTypeDescription(UnitOfMeasureType unitOfMeasureType, Language language) {
        return getBestPluralUnitOfMeasureTypeDescription(unitOfMeasureType, getBestUnitOfMeasureTypeDescription(unitOfMeasureType, language));
    }
    
    public String getBestUnitOfMeasureTypeDescriptionSymbol(UnitOfMeasureType unitOfMeasureType, UnitOfMeasureTypeDescription unitOfMeasureTypeDescription) {
        String symbol;
        
        if(unitOfMeasureTypeDescription == null) {
            symbol = unitOfMeasureType.getLastDetail().getUnitOfMeasureTypeName();
        } else {
            symbol = unitOfMeasureTypeDescription.getSymbol();
        }
        
        return symbol;
    }
    
    public String getBestUnitOfMeasureTypeDescriptionSymbol(UnitOfMeasureType unitOfMeasureType, Language language) {
        return getBestUnitOfMeasureTypeDescriptionSymbol(unitOfMeasureType, getBestUnitOfMeasureTypeDescription(unitOfMeasureType, language));
    }
    
    public UnitOfMeasureTypeDescriptionTransfer getUnitOfMeasureTypeDescriptionTransfer(UserVisit userVisit, UnitOfMeasureTypeDescription unitOfMeasureTypeDescription) {
        return unitOfMeasureTypeDescriptionTransferCache.getUnitOfMeasureTypeDescriptionTransfer(userVisit, unitOfMeasureTypeDescription);
    }
    
    public List<UnitOfMeasureTypeDescriptionTransfer> getUnitOfMeasureTypeDescriptionTransfers(UserVisit userVisit, UnitOfMeasureType unitOfMeasureType) {
        var unitOfMeasureTypeDescriptions = getUnitOfMeasureTypeDescriptionsByUnitOfMeasureType(unitOfMeasureType);
        List<UnitOfMeasureTypeDescriptionTransfer> unitOfMeasureTypeDescriptionTransfers = new ArrayList<>(unitOfMeasureTypeDescriptions.size());
        
        unitOfMeasureTypeDescriptions.forEach((unitOfMeasureTypeDescription) ->
                unitOfMeasureTypeDescriptionTransfers.add(unitOfMeasureTypeDescriptionTransferCache.getUnitOfMeasureTypeDescriptionTransfer(userVisit, unitOfMeasureTypeDescription))
        );
        
        return unitOfMeasureTypeDescriptionTransfers;
    }
    
    public void updateUnitOfMeasureTypeDescriptionFromValue(UnitOfMeasureTypeDescriptionValue unitOfMeasureTypeDescriptionValue, BasePK updatedBy) {
        if(unitOfMeasureTypeDescriptionValue.hasBeenModified()) {
            var unitOfMeasureTypeDescription = unitOfMeasureTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     unitOfMeasureTypeDescriptionValue.getPrimaryKey());
            
            unitOfMeasureTypeDescription.setThruTime(session.getStartTime());
            unitOfMeasureTypeDescription.store();

            var unitOfMeasureType = unitOfMeasureTypeDescription.getUnitOfMeasureType();
            var language = unitOfMeasureTypeDescription.getLanguage();
            var singularDescription = unitOfMeasureTypeDescriptionValue.getSingularDescription();
            var pluralDescription = unitOfMeasureTypeDescriptionValue.getPluralDescription();
            var symbol = unitOfMeasureTypeDescriptionValue.getSymbol();
            
            unitOfMeasureTypeDescription = unitOfMeasureTypeDescriptionFactory.create(unitOfMeasureType, language,
                    singularDescription, pluralDescription, symbol, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(unitOfMeasureType.getPrimaryKey(), EventTypes.MODIFY, unitOfMeasureTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteUnitOfMeasureTypeDescription(UnitOfMeasureTypeDescription unitOfMeasureTypeDescription, BasePK deletedBy) {
        unitOfMeasureTypeDescription.setThruTime(session.getStartTime());
        
        sendEvent(unitOfMeasureTypeDescription.getUnitOfMeasureTypePK(), EventTypes.MODIFY, unitOfMeasureTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteUnitOfMeasureTypeDescriptionsByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType, BasePK deletedBy) {
        var unitOfMeasureTypeDescriptions = getUnitOfMeasureTypeDescriptionsByUnitOfMeasureTypeForUpdate(unitOfMeasureType);
        
        unitOfMeasureTypeDescriptions.forEach((unitOfMeasureTypeDescription) -> 
                deleteUnitOfMeasureTypeDescription(unitOfMeasureTypeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Type Volumes
    // --------------------------------------------------------------------------------

    @Inject
    protected UnitOfMeasureTypeVolumeFactory unitOfMeasureTypeVolumeFactory;

    @Inject
    private UnitOfMeasureTypeVolumeTransferCache unitOfMeasureTypeVolumeTransferCache;

    public UnitOfMeasureTypeVolume createUnitOfMeasureTypeVolume(UnitOfMeasureType unitOfMeasureType, Long height, Long width,
            Long depth, BasePK createdBy) {
        var unitOfMeasureTypeVolume = unitOfMeasureTypeVolumeFactory.create(
                unitOfMeasureType, height, width, depth, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(unitOfMeasureType.getPrimaryKey(), EventTypes.MODIFY, unitOfMeasureTypeVolume.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return unitOfMeasureTypeVolume;
    }

    public long countUnitOfMeasureTypeVolumesByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType) {
        return session.getDslContext()
                .selectCount()
                .from(UnitOfMeasureTypeVolumes)
                .where(UnitOfMeasureTypeVolumes.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                        UnitOfMeasureTypeVolumes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private UnitOfMeasureTypeVolume getUnitOfMeasureTypeVolume(UnitOfMeasureType unitOfMeasureType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(UnitOfMeasureTypeVolumes.fields())
                .from(UnitOfMeasureTypeVolumes)
                .where(UnitOfMeasureTypeVolumes.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                        UnitOfMeasureTypeVolumes.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return unitOfMeasureTypeVolumeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public UnitOfMeasureTypeVolume getUnitOfMeasureTypeVolume(UnitOfMeasureType unitOfMeasureType) {
        return getUnitOfMeasureTypeVolume(unitOfMeasureType, EntityPermission.READ_ONLY);
    }
    
    public UnitOfMeasureTypeVolume getUnitOfMeasureTypeVolumeForUpdate(UnitOfMeasureType unitOfMeasureType) {
        return getUnitOfMeasureTypeVolume(unitOfMeasureType, EntityPermission.READ_WRITE);
    }

    public UnitOfMeasureTypeVolumeValue getUnitOfMeasureTypeVolumeValue(UnitOfMeasureTypeVolume unitOfMeasureTypeVolume) {
        return unitOfMeasureTypeVolume == null? null: unitOfMeasureTypeVolume.getUnitOfMeasureTypeVolumeValue().clone();
    }

    public UnitOfMeasureTypeVolumeValue getUnitOfMeasureTypeVolumeValueForUpdate(UnitOfMeasureType unitOfMeasureType) {
        return getUnitOfMeasureTypeVolumeValue(getUnitOfMeasureTypeVolumeForUpdate(unitOfMeasureType));
    }
    
    public UnitOfMeasureTypeVolumeTransfer getUnitOfMeasureTypeVolumeTransfer(UserVisit userVisit, UnitOfMeasureTypeVolume unitOfMeasureTypeVolume) {
        return unitOfMeasureTypeVolume == null? null: unitOfMeasureTypeVolumeTransferCache.getUnitOfMeasureTypeVolumeTransfer(userVisit, unitOfMeasureTypeVolume);
    }
    
    public UnitOfMeasureTypeVolumeTransfer getUnitOfMeasureTypeVolumeTransfer(UserVisit userVisit, UnitOfMeasureType unitOfMeasureType) {
        return getUnitOfMeasureTypeVolumeTransfer(userVisit, getUnitOfMeasureTypeVolume(unitOfMeasureType));
    }
    
    public void updateUnitOfMeasureTypeVolumeFromValue(UnitOfMeasureTypeVolumeValue unitOfMeasureTypeVolumeValue, BasePK updatedBy) {
        if(unitOfMeasureTypeVolumeValue.hasBeenModified()) {
            var unitOfMeasureTypeVolume = unitOfMeasureTypeVolumeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     unitOfMeasureTypeVolumeValue.getPrimaryKey());
            
            unitOfMeasureTypeVolume.setThruTime(session.getStartTime());
            unitOfMeasureTypeVolume.store();

            var unitOfMeasureTypePK = unitOfMeasureTypeVolume.getUnitOfMeasureTypePK(); // Not updated
            var height = unitOfMeasureTypeVolumeValue.getHeight();
            var width = unitOfMeasureTypeVolumeValue.getWidth();
            var depth = unitOfMeasureTypeVolumeValue.getDepth();
            
            unitOfMeasureTypeVolume = unitOfMeasureTypeVolumeFactory.create(unitOfMeasureTypePK, height,
                    width, depth, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(unitOfMeasureTypePK, EventTypes.MODIFY, unitOfMeasureTypeVolume.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteUnitOfMeasureTypeVolume(UnitOfMeasureTypeVolume unitOfMeasureTypeVolume, BasePK deletedBy) {
        unitOfMeasureTypeVolume.setThruTime(session.getStartTime());
        
        sendEvent(unitOfMeasureTypeVolume.getUnitOfMeasureTypePK(), EventTypes.MODIFY, unitOfMeasureTypeVolume.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteUnitOfMeasureTypeVolumeByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType, BasePK deletedBy) {
        var unitOfMeasureTypeVolume = getUnitOfMeasureTypeVolumeForUpdate(unitOfMeasureType);
        
        if(unitOfMeasureTypeVolume != null)
            deleteUnitOfMeasureTypeVolume(unitOfMeasureTypeVolume, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Type Weights
    // --------------------------------------------------------------------------------

    @Inject
    protected UnitOfMeasureTypeWeightFactory unitOfMeasureTypeWeightFactory;

    @Inject
    private UnitOfMeasureTypeWeightTransferCache unitOfMeasureTypeWeightTransferCache;

    public UnitOfMeasureTypeWeight createUnitOfMeasureTypeWeight(UnitOfMeasureType unitOfMeasureType, Long weight, BasePK createdBy) {
        var unitOfMeasureTypeWeight = unitOfMeasureTypeWeightFactory.create(unitOfMeasureType, weight,
                session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(unitOfMeasureType.getPrimaryKey(), EventTypes.MODIFY, unitOfMeasureTypeWeight.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return unitOfMeasureTypeWeight;
    }

    public long countUnitOfMeasureTypeWeightsByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType) {
        return session.getDslContext()
                .selectCount()
                .from(UnitOfMeasureTypeWeights)
                .where(UnitOfMeasureTypeWeights.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                        UnitOfMeasureTypeWeights.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private UnitOfMeasureTypeWeight getUnitOfMeasureTypeWeight(UnitOfMeasureType unitOfMeasureType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(UnitOfMeasureTypeWeights.fields())
                .from(UnitOfMeasureTypeWeights)
                .where(UnitOfMeasureTypeWeights.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                        UnitOfMeasureTypeWeights.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return unitOfMeasureTypeWeightFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public UnitOfMeasureTypeWeight getUnitOfMeasureTypeWeight(UnitOfMeasureType unitOfMeasureType) {
        return getUnitOfMeasureTypeWeight(unitOfMeasureType, EntityPermission.READ_ONLY);
    }
    
    public UnitOfMeasureTypeWeight getUnitOfMeasureTypeWeightForUpdate(UnitOfMeasureType unitOfMeasureType) {
        return getUnitOfMeasureTypeWeight(unitOfMeasureType, EntityPermission.READ_WRITE);
    }

    public UnitOfMeasureTypeWeightValue getUnitOfMeasureTypeWeightValue(UnitOfMeasureTypeWeight unitOfMeasureTypeWeight) {
        return unitOfMeasureTypeWeight == null? null: unitOfMeasureTypeWeight.getUnitOfMeasureTypeWeightValue().clone();
    }

    public UnitOfMeasureTypeWeightValue getUnitOfMeasureTypeWeightValueForUpdate(UnitOfMeasureType unitOfMeasureType) {
        return getUnitOfMeasureTypeWeightValue(getUnitOfMeasureTypeWeightForUpdate(unitOfMeasureType));
    }
    
    public UnitOfMeasureTypeWeightTransfer getUnitOfMeasureTypeWeightTransfer(UserVisit userVisit, UnitOfMeasureTypeWeight unitOfMeasureTypeWeight) {
        return unitOfMeasureTypeWeight == null? null: unitOfMeasureTypeWeightTransferCache.getUnitOfMeasureTypeWeightTransfer(userVisit, unitOfMeasureTypeWeight);
    }
    
    public UnitOfMeasureTypeWeightTransfer getUnitOfMeasureTypeWeightTransfer(UserVisit userVisit, UnitOfMeasureType unitOfMeasureType) {
        return getUnitOfMeasureTypeWeightTransfer(userVisit, getUnitOfMeasureTypeWeight(unitOfMeasureType));
    }
    
    public void updateUnitOfMeasureTypeWeightFromValue(UnitOfMeasureTypeWeightValue unitOfMeasureTypeWeightValue, BasePK updatedBy) {
        if(unitOfMeasureTypeWeightValue.hasBeenModified()) {
            var unitOfMeasureTypeWeight = unitOfMeasureTypeWeightFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     unitOfMeasureTypeWeightValue.getPrimaryKey());
            
            unitOfMeasureTypeWeight.setThruTime(session.getStartTime());
            unitOfMeasureTypeWeight.store();

            var unitOfMeasureTypePK = unitOfMeasureTypeWeight.getUnitOfMeasureTypePK(); // Not updated
            var weight = unitOfMeasureTypeWeightValue.getWeight();
            
            unitOfMeasureTypeWeight = unitOfMeasureTypeWeightFactory.create(unitOfMeasureTypePK, weight,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(unitOfMeasureTypePK, EventTypes.MODIFY, unitOfMeasureTypeWeight.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteUnitOfMeasureTypeWeight(UnitOfMeasureTypeWeight unitOfMeasureTypeWeight, BasePK deletedBy) {
        unitOfMeasureTypeWeight.setThruTime(session.getStartTime());
        
        sendEvent(unitOfMeasureTypeWeight.getUnitOfMeasureTypePK(), EventTypes.MODIFY, unitOfMeasureTypeWeight.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteUnitOfMeasureTypeWeightByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType, BasePK deletedBy) {
        var unitOfMeasureTypeWeight = getUnitOfMeasureTypeWeightForUpdate(unitOfMeasureType);
        
        if(unitOfMeasureTypeWeight != null)
            deleteUnitOfMeasureTypeWeight(unitOfMeasureTypeWeight, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Equivalents
    // --------------------------------------------------------------------------------

    @Inject
    protected UnitOfMeasureEquivalentFactory unitOfMeasureEquivalentFactory;

    @Inject
    private UnitOfMeasureEquivalentTransferCache unitOfMeasureEquivalentTransferCache;

    public UnitOfMeasureEquivalent createUnitOfMeasureEquivalent(UnitOfMeasureType fromUnitOfMeasureType,
            UnitOfMeasureType toUnitOfMeasureType, Long toQuantity, BasePK createdBy) {
        var unitOfMeasureEquivalent = unitOfMeasureEquivalentFactory.create(
                fromUnitOfMeasureType, toUnitOfMeasureType, toQuantity, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(fromUnitOfMeasureType.getPrimaryKey(), EventTypes.MODIFY, unitOfMeasureEquivalent.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return unitOfMeasureEquivalent;
    }

    public long countUnitOfMeasureEquivalentsByUnitOfMeasureKind(UnitOfMeasureKind unitOfMeasureKind) {
        return session.getDslContext()
                .selectCount()
                .from(UnitOfMeasureEquivalents)
                .join(UnitOfMeasureTypes).onKey(UNIT_OF_MEASURE_EQUIVALENTS_FROM_UNIT_OF_MEASURE_TYPE_FK)
                .join(UnitOfMeasureTypeDetails).onKey(UNIT_OF_MEASURE_TYPES_ACTIVE_DETAIL_FK)
                .where(UnitOfMeasureEquivalents.THRU_TIME.eq(Session.MAX_TIME),
                        UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_KIND.eq(unitOfMeasureKind.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countUnitOfMeasureEquivalentsByFromUnitOfMeasureType(UnitOfMeasureType fromUnitOfMeasureType) {
        return session.getDslContext()
                .selectCount()
                .from(UnitOfMeasureEquivalents)
                .where(UnitOfMeasureEquivalents.FROM_UNIT_OF_MEASURE_TYPE.eq(fromUnitOfMeasureType.getPrimaryKey()),
                        UnitOfMeasureEquivalents.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countUnitOfMeasureEquivalentsByToUnitOfMeasureType(UnitOfMeasureType toUnitOfMeasureType) {
        return session.getDslContext()
                .selectCount()
                .from(UnitOfMeasureEquivalents)
                .where(UnitOfMeasureEquivalents.TO_UNIT_OF_MEASURE_TYPE.eq(toUnitOfMeasureType.getPrimaryKey()),
                        UnitOfMeasureEquivalents.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public UnitOfMeasureEquivalent getUnitOfMeasureEquivalent(UnitOfMeasureType fromUnitOfMeasureType,
            UnitOfMeasureType toUnitOfMeasureType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(UnitOfMeasureEquivalents.fields())
                .from(UnitOfMeasureEquivalents)
                .where(UnitOfMeasureEquivalents.FROM_UNIT_OF_MEASURE_TYPE.eq(fromUnitOfMeasureType.getPrimaryKey()),
                        UnitOfMeasureEquivalents.TO_UNIT_OF_MEASURE_TYPE.eq(toUnitOfMeasureType.getPrimaryKey()),
                        UnitOfMeasureEquivalents.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return unitOfMeasureEquivalentFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public UnitOfMeasureEquivalent getUnitOfMeasureEquivalent(UnitOfMeasureType fromUnitOfMeasureType,
            UnitOfMeasureType toUnitOfMeasureType) {
        return getUnitOfMeasureEquivalent(fromUnitOfMeasureType, toUnitOfMeasureType, EntityPermission.READ_ONLY);
    }
    
    public UnitOfMeasureEquivalent getUnitOfMeasureEquivalentForUpdate(UnitOfMeasureType fromUnitOfMeasureType,
            UnitOfMeasureType toUnitOfMeasureType) {
        return getUnitOfMeasureEquivalent(fromUnitOfMeasureType, toUnitOfMeasureType, EntityPermission.READ_WRITE);
    }

    public UnitOfMeasureEquivalentValue getUnitOfMeasureEquivalentValue(UnitOfMeasureEquivalent unitOfMeasureEquivalent) {
        return unitOfMeasureEquivalent == null? null: unitOfMeasureEquivalent.getUnitOfMeasureEquivalentValue().clone();
    }

    public UnitOfMeasureEquivalentValue getUnitOfMeasureEquivalentValueForUpdate(UnitOfMeasureType fromUnitOfMeasureType,
            UnitOfMeasureType toUnitOfMeasureType) {
        return getUnitOfMeasureEquivalentValue(getUnitOfMeasureEquivalentForUpdate(fromUnitOfMeasureType,
                toUnitOfMeasureType));
    }
    
    private List<UnitOfMeasureEquivalent> getUnitOfMeasureEquivalentsByUnitOfMeasureKind(UnitOfMeasureKind unitOfMeasureKind,
            EntityPermission entityPermission) {
        var toType = UnitOfMeasureTypes.as("toType");
        var toTypeDetail = UnitOfMeasureTypeDetails.as("toTypeDetail");
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(UnitOfMeasureEquivalents.fields())
                    .from(UnitOfMeasureEquivalents)
                    .join(UnitOfMeasureTypes).onKey(UNIT_OF_MEASURE_EQUIVALENTS_FROM_UNIT_OF_MEASURE_TYPE_FK)
                    .join(UnitOfMeasureTypeDetails).onKey(UNIT_OF_MEASURE_TYPES_LAST_DETAIL_FK)
                    .join(toType).on(toType.UNIT_OF_MEASURE_TYPE.eq(UnitOfMeasureEquivalents.TO_UNIT_OF_MEASURE_TYPE))
                    .join(toTypeDetail).on(toTypeDetail.UNIT_OF_MEASURE_TYPE_DETAIL.eq(toType.LAST_DETAIL))
                    .where(UnitOfMeasureEquivalents.THRU_TIME.eq(Session.MAX_TIME),
                            UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_KIND.eq(unitOfMeasureKind.getPrimaryKey()))
                    .orderBy(UnitOfMeasureTypeDetails.SORT_ORDER, UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_TYPE_NAME,
                            toTypeDetail.SORT_ORDER, toTypeDetail.UNIT_OF_MEASURE_TYPE_NAME),
                    UnitOfMeasureEquivalentFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(UnitOfMeasureEquivalents.fields())
                    .from(UnitOfMeasureEquivalents)
                    .join(UnitOfMeasureTypes).onKey(UNIT_OF_MEASURE_EQUIVALENTS_FROM_UNIT_OF_MEASURE_TYPE_FK)
                    .join(UnitOfMeasureTypeDetails).onKey(UNIT_OF_MEASURE_TYPES_LAST_DETAIL_FK)
                    .where(UnitOfMeasureEquivalents.THRU_TIME.eq(Session.MAX_TIME),
                            UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_KIND.eq(unitOfMeasureKind.getPrimaryKey()))
                    .forUpdate();
        };

        return unitOfMeasureEquivalentFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<UnitOfMeasureEquivalent> getUnitOfMeasureEquivalentsByUnitOfMeasureKind(UnitOfMeasureKind unitOfMeasureKind) {
        return getUnitOfMeasureEquivalentsByUnitOfMeasureKind(unitOfMeasureKind, EntityPermission.READ_ONLY);
    }
    
    public List<UnitOfMeasureEquivalent> getUnitOfMeasureEquivalentsByUnitOfMeasureKindForUpdate(UnitOfMeasureKind unitOfMeasureKind) {
        return getUnitOfMeasureEquivalentsByUnitOfMeasureKind(unitOfMeasureKind, EntityPermission.READ_WRITE);
    }
    
    private List<UnitOfMeasureEquivalent> getUnitOfMeasureEquivalentsByFromUnitOfMeasureType(UnitOfMeasureType fromUnitOfMeasureType,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(UnitOfMeasureEquivalents.fields())
                    .from(UnitOfMeasureEquivalents)
                    .join(UnitOfMeasureTypes).onKey(UNIT_OF_MEASURE_EQUIVALENTS_TO_UNIT_OF_MEASURE_TYPE_FK)
                    .join(UnitOfMeasureTypeDetails).onKey(UNIT_OF_MEASURE_TYPES_LAST_DETAIL_FK)
                    .where(UnitOfMeasureEquivalents.FROM_UNIT_OF_MEASURE_TYPE.eq(fromUnitOfMeasureType.getPrimaryKey()),
                            UnitOfMeasureEquivalents.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(UnitOfMeasureTypeDetails.SORT_ORDER, UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_TYPE_NAME),
                    UnitOfMeasureEquivalentFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(UnitOfMeasureEquivalents.fields())
                    .from(UnitOfMeasureEquivalents)
                    .where(UnitOfMeasureEquivalents.FROM_UNIT_OF_MEASURE_TYPE.eq(fromUnitOfMeasureType.getPrimaryKey()),
                            UnitOfMeasureEquivalents.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return unitOfMeasureEquivalentFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<UnitOfMeasureEquivalent> getUnitOfMeasureEquivalentsByFromUnitOfMeasureType(UnitOfMeasureType fromUnitOfMeasureType) {
        return getUnitOfMeasureEquivalentsByFromUnitOfMeasureType(fromUnitOfMeasureType, EntityPermission.READ_ONLY);
    }
    
    public List<UnitOfMeasureEquivalent> getUnitOfMeasureEquivalentsByFromUnitOfMeasureTypeForUpdate(UnitOfMeasureType fromUnitOfMeasureType) {
        return getUnitOfMeasureEquivalentsByFromUnitOfMeasureType(fromUnitOfMeasureType, EntityPermission.READ_WRITE);
    }
    
    private List<UnitOfMeasureEquivalent> getUnitOfMeasureEquivalentsByToUnitOfMeasureType(UnitOfMeasureType toUnitOfMeasureType,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(UnitOfMeasureEquivalents.fields())
                    .from(UnitOfMeasureEquivalents)
                    .join(UnitOfMeasureTypes).onKey(UNIT_OF_MEASURE_EQUIVALENTS_FROM_UNIT_OF_MEASURE_TYPE_FK)
                    .join(UnitOfMeasureTypeDetails).onKey(UNIT_OF_MEASURE_TYPES_LAST_DETAIL_FK)
                    .where(UnitOfMeasureEquivalents.TO_UNIT_OF_MEASURE_TYPE.eq(toUnitOfMeasureType.getPrimaryKey()),
                            UnitOfMeasureEquivalents.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(UnitOfMeasureTypeDetails.SORT_ORDER, UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_TYPE_NAME),
                    UnitOfMeasureEquivalentFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(UnitOfMeasureEquivalents.fields())
                    .from(UnitOfMeasureEquivalents)
                    .where(UnitOfMeasureEquivalents.TO_UNIT_OF_MEASURE_TYPE.eq(toUnitOfMeasureType.getPrimaryKey()),
                            UnitOfMeasureEquivalents.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return unitOfMeasureEquivalentFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<UnitOfMeasureEquivalent> getUnitOfMeasureEquivalentsByToUnitOfMeasureType(UnitOfMeasureType toUnitOfMeasureType) {
        return getUnitOfMeasureEquivalentsByToUnitOfMeasureType(toUnitOfMeasureType, EntityPermission.READ_ONLY);
    }
    
    public List<UnitOfMeasureEquivalent> getUnitOfMeasureEquivalentsByToUnitOfMeasureTypeForUpdate(UnitOfMeasureType toUnitOfMeasureType) {
        return getUnitOfMeasureEquivalentsByToUnitOfMeasureType(toUnitOfMeasureType, EntityPermission.READ_WRITE);
    }
    
    public UnitOfMeasureEquivalentTransfer getUnitOfMeasureEquivalentTransfer(UserVisit userVisit,
            UnitOfMeasureEquivalent unitOfMeasureEquivalent) {
        return unitOfMeasureEquivalentTransferCache.getUnitOfMeasureEquivalentTransfer(userVisit, unitOfMeasureEquivalent);
    }
    
    public List<UnitOfMeasureEquivalentTransfer> getUnitOfMeasureEquivalentTransfers(final UserVisit userVisit,
            final Collection<UnitOfMeasureEquivalent> unitOfMeasureEquivalents) {
        List<UnitOfMeasureEquivalentTransfer> unitOfMeasureEquivalentTransfers = new ArrayList<>(unitOfMeasureEquivalents.size());
        
        unitOfMeasureEquivalents.forEach((unitOfMeasureEquivalent) ->
                unitOfMeasureEquivalentTransfers.add(unitOfMeasureEquivalentTransferCache.getUnitOfMeasureEquivalentTransfer(userVisit, unitOfMeasureEquivalent))
        );
        
        return unitOfMeasureEquivalentTransfers;
    }
    
    public List<UnitOfMeasureEquivalentTransfer> getUnitOfMeasureEquivalentTransfersByUnitOfMeasureKind(UserVisit userVisit,
            UnitOfMeasureKind unitOfMeasureKind) {
        return getUnitOfMeasureEquivalentTransfers(userVisit, getUnitOfMeasureEquivalentsByUnitOfMeasureKind(unitOfMeasureKind));
    }
    
    public List<UnitOfMeasureEquivalentTransfer> getUnitOfMeasureEquivalentTransfersByFromUnitOfMeasureType(UserVisit userVisit,
            UnitOfMeasureType fromUnitOfMeasureType) {
        return getUnitOfMeasureEquivalentTransfers(userVisit, getUnitOfMeasureEquivalentsByFromUnitOfMeasureType(fromUnitOfMeasureType));
    }
    
    public List<UnitOfMeasureEquivalentTransfer> getUnitOfMeasureEquivalentTransfersByToUnitOfMeasureType(UserVisit userVisit,
            UnitOfMeasureType toUnitOfMeasureType) {
        return getUnitOfMeasureEquivalentTransfers(userVisit, getUnitOfMeasureEquivalentsByToUnitOfMeasureType(toUnitOfMeasureType));
    }
    
    public void updateUnitOfMeasureEquivalentFromValue(UnitOfMeasureEquivalentValue unitOfMeasureEquivalentValue, BasePK updatedBy) {
        if(unitOfMeasureEquivalentValue.hasBeenModified()) {
            var unitOfMeasureEquivalent = unitOfMeasureEquivalentFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     unitOfMeasureEquivalentValue.getPrimaryKey());
            
            unitOfMeasureEquivalent.setThruTime(session.getStartTime());
            unitOfMeasureEquivalent.store();

            var fromUnitOfMeasureTypePK = unitOfMeasureEquivalent.getFromUnitOfMeasureTypePK(); // Not updated
            var toUnitOfMeasureTypePK = unitOfMeasureEquivalent.getToUnitOfMeasureTypePK(); // Not updated
            var toQuantity = unitOfMeasureEquivalentValue.getToQuantity();
            
            unitOfMeasureEquivalent = unitOfMeasureEquivalentFactory.create(fromUnitOfMeasureTypePK,
                    toUnitOfMeasureTypePK, toQuantity, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(fromUnitOfMeasureTypePK, EventTypes.MODIFY, unitOfMeasureEquivalent.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteUnitOfMeasureEquivalent(UnitOfMeasureEquivalent unitOfMeasureEquivalent, BasePK deletedBy) {
        unitOfMeasureEquivalent.setThruTime(session.getStartTime());
        unitOfMeasureEquivalent.store();
        
        sendEvent(unitOfMeasureEquivalent.getFromUnitOfMeasureTypePK(), EventTypes.MODIFY, unitOfMeasureEquivalent.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteUnitOfMeasureEquivalentsByFromUnitOfMeasureType(UnitOfMeasureType fromUnitOfMeasureType, BasePK deletedBy) {
        var unitOfMeasureEquivalents = getUnitOfMeasureEquivalentsByFromUnitOfMeasureTypeForUpdate(fromUnitOfMeasureType);
        
        unitOfMeasureEquivalents.forEach((unitOfMeasureEquivalent) -> 
                deleteUnitOfMeasureEquivalent(unitOfMeasureEquivalent, deletedBy)
        );
    }
    
    public void deleteUnitOfMeasureEquivalentsByToUnitOfMeasureType(UnitOfMeasureType toUnitOfMeasureType, BasePK deletedBy) {
        var unitOfMeasureEquivalents = getUnitOfMeasureEquivalentsByToUnitOfMeasureTypeForUpdate(toUnitOfMeasureType);
        
        unitOfMeasureEquivalents.forEach((unitOfMeasureEquivalent) -> 
                deleteUnitOfMeasureEquivalent(unitOfMeasureEquivalent, deletedBy)
        );
    }
    
    public void deleteUnitOfMeasureEquivalentsByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType, BasePK deletedBy) {
        deleteUnitOfMeasureEquivalentsByFromUnitOfMeasureType(unitOfMeasureType, deletedBy);
        deleteUnitOfMeasureEquivalentsByToUnitOfMeasureType(unitOfMeasureType, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Kind Use Types
    // --------------------------------------------------------------------------------

    @Inject
    protected UnitOfMeasureKindUseTypeFactory unitOfMeasureKindUseTypeFactory;

    @Inject
    private UnitOfMeasureKindUseTypeTransferCache unitOfMeasureKindUseTypeTransferCache;

    public UnitOfMeasureKindUseType createUnitOfMeasureKindUseType(String unitOfMeasureKindUseTypeName, Boolean allowMultiple, Boolean allowFractionDigits,
            Boolean isDefault, Integer sortOrder) {
        return unitOfMeasureKindUseTypeFactory.create(unitOfMeasureKindUseTypeName, allowMultiple, allowFractionDigits, isDefault, sortOrder);
    }

    public long countUnitOfMeasureKindUseTypes() {
        return session.getDslContext()
                .selectCount()
                .from(UnitOfMeasureKindUseTypes)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.UnitOfMeasureKindUseType */
    public UnitOfMeasureKindUseType getUnitOfMeasureKindUseTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new UnitOfMeasureKindUseTypePK(entityInstance.getEntityUniqueId());

        return unitOfMeasureKindUseTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public UnitOfMeasureKindUseType getUnitOfMeasureKindUseTypeByEntityInstance(EntityInstance entityInstance) {
        return getUnitOfMeasureKindUseTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public UnitOfMeasureKindUseType getUnitOfMeasureKindUseTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getUnitOfMeasureKindUseTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    public List<UnitOfMeasureKindUseType> getUnitOfMeasureKindUseTypes() {
        var query = session.applyLimit(session.getDslContext()
                .select(UnitOfMeasureKindUseTypes.fields())
                .from(UnitOfMeasureKindUseTypes)
                .orderBy(UnitOfMeasureKindUseTypes.SORT_ORDER, UnitOfMeasureKindUseTypes.UNIT_OF_MEASURE_KIND_USE_TYPE_NAME),
                UnitOfMeasureKindUseTypeFactory.class);

        return unitOfMeasureKindUseTypeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public UnitOfMeasureKindUseType getUnitOfMeasureKindUseTypeByName(String unitOfMeasureKindUseTypeName) {
        var query = session.getDslContext()
                .select(UnitOfMeasureKindUseTypes.fields())
                .from(UnitOfMeasureKindUseTypes)
                .where(UnitOfMeasureKindUseTypes.UNIT_OF_MEASURE_KIND_USE_TYPE_NAME.eq(unitOfMeasureKindUseTypeName));

        return unitOfMeasureKindUseTypeFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public UnitOfMeasureKindUseTypeChoicesBean getUnitOfMeasureKindUseTypeChoices(String defaultUnitOfMeasureKindUseTypeChoice,
            Language language, boolean allowNullChoice) {
        var unitOfMeasureKindUseTypes = getUnitOfMeasureKindUseTypes();
        var size = unitOfMeasureKindUseTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultUnitOfMeasureKindUseTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var unitOfMeasureKindUseType : unitOfMeasureKindUseTypes) {
            var label = getBestUnitOfMeasureKindUseTypeDescription(unitOfMeasureKindUseType, language);
            var value = unitOfMeasureKindUseType.getUnitOfMeasureKindUseTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultUnitOfMeasureKindUseTypeChoice != null && defaultUnitOfMeasureKindUseTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && unitOfMeasureKindUseType.getIsDefault()))
                defaultValue = value;
        }
        
        return new UnitOfMeasureKindUseTypeChoicesBean(labels, values, defaultValue);
    }
    
    public UnitOfMeasureKindUseTypeTransfer getUnitOfMeasureKindUseTypeTransfer(UserVisit userVisit,
            UnitOfMeasureKindUseType unitOfMeasureKindUseType) {
        return unitOfMeasureKindUseTypeTransferCache.getUnitOfMeasureKindUseTypeTransfer(userVisit, unitOfMeasureKindUseType);
    }
    
    public List<UnitOfMeasureKindUseTypeTransfer> getUnitOfMeasureKindUseTypeTransfers(UserVisit userVisit, Collection<UnitOfMeasureKindUseType> unitOfMeasureKindUseTypes) {
        List<UnitOfMeasureKindUseTypeTransfer> unitOfMeasureKindUseTypeTransfers = new ArrayList<>(unitOfMeasureKindUseTypes.size());
        
        unitOfMeasureKindUseTypes.forEach((unitOfMeasureKindUseType) ->
                unitOfMeasureKindUseTypeTransfers.add(unitOfMeasureKindUseTypeTransferCache.getUnitOfMeasureKindUseTypeTransfer(userVisit, unitOfMeasureKindUseType))
        );
        
        return unitOfMeasureKindUseTypeTransfers;
    }
    
    public List<UnitOfMeasureKindUseTypeTransfer> getUnitOfMeasureKindUseTypeTransfers(UserVisit userVisit) {
        return getUnitOfMeasureKindUseTypeTransfers(userVisit, getUnitOfMeasureKindUseTypes());
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Kind Use Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected UnitOfMeasureKindUseTypeDescriptionFactory unitOfMeasureKindUseTypeDescriptionFactory;

    public UnitOfMeasureKindUseTypeDescription createUnitOfMeasureKindUseTypeDescription(UnitOfMeasureKindUseType unitOfMeasureKindUseType, Language language, String description) {
        return unitOfMeasureKindUseTypeDescriptionFactory.create(unitOfMeasureKindUseType, language, description);
    }
    
    public UnitOfMeasureKindUseTypeDescription getUnitOfMeasureKindUseTypeDescription(UnitOfMeasureKindUseType unitOfMeasureKindUseType, Language language) {
        var query = session.getDslContext()
                .select(UnitOfMeasureKindUseTypeDescriptions.fields())
                .from(UnitOfMeasureKindUseTypeDescriptions)
                .where(UnitOfMeasureKindUseTypeDescriptions.UNIT_OF_MEASURE_KIND_USE_TYPE.eq(unitOfMeasureKindUseType.getPrimaryKey()),
                        UnitOfMeasureKindUseTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()));

        return unitOfMeasureKindUseTypeDescriptionFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public String getBestUnitOfMeasureKindUseTypeDescription(UnitOfMeasureKindUseType unitOfMeasureKindUseType, Language language) {
        String description;
        var unitOfMeasureKindUseTypeDescription = getUnitOfMeasureKindUseTypeDescription(unitOfMeasureKindUseType, language);
        
        if(unitOfMeasureKindUseTypeDescription == null && !language.getIsDefault()) {
            unitOfMeasureKindUseTypeDescription = getUnitOfMeasureKindUseTypeDescription(unitOfMeasureKindUseType, partyControl.getDefaultLanguage());
        }
        
        if(unitOfMeasureKindUseTypeDescription == null) {
            description = unitOfMeasureKindUseType.getUnitOfMeasureKindUseTypeName();
        } else {
            description = unitOfMeasureKindUseTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Kind Uses
    // --------------------------------------------------------------------------------

    @Inject
    protected UnitOfMeasureKindUseFactory unitOfMeasureKindUseFactory;

    @Inject
    private UnitOfMeasureKindUseTransferCache unitOfMeasureKindUseTransferCache;

    public UnitOfMeasureKindUse createUnitOfMeasureKindUse(UnitOfMeasureKindUseType unitOfMeasureKindUseType,
            UnitOfMeasureKind unitOfMeasureKind, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultUnitOfMeasureKindUse = getDefaultUnitOfMeasureKindUse(unitOfMeasureKindUseType);
        var defaultFound = defaultUnitOfMeasureKindUse != null;
        
        if(defaultFound && isDefault) {
            var defaultUnitOfMeasureKindUseValue = getDefaultUnitOfMeasureKindUseValueForUpdate(unitOfMeasureKindUseType);
            
            defaultUnitOfMeasureKindUseValue.setIsDefault(false);
            updateUnitOfMeasureKindUseFromValue(defaultUnitOfMeasureKindUseValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var unitOfMeasureKindUse = unitOfMeasureKindUseFactory.create(
                unitOfMeasureKindUseType, unitOfMeasureKind, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(unitOfMeasureKind.getPrimaryKey(), EventTypes.MODIFY, unitOfMeasureKindUse.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return unitOfMeasureKindUse;
    }
    
    public long countUnitOfMeasureKindUsesByUnitOfMeasureKindUseType(UnitOfMeasureKindUseType unitOfMeasureKindUseType) {
        return session.getDslContext()
                .selectCount()
                .from(UnitOfMeasureKindUses)
                .where(UnitOfMeasureKindUses.UNIT_OF_MEASURE_KIND_USE_TYPE.eq(unitOfMeasureKindUseType.getPrimaryKey()),
                        UnitOfMeasureKindUses.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }
    
    public long countUnitOfMeasureKindUsesByUnitOfMeasureKind(UnitOfMeasureKind unitOfMeasureKind) {
        return session.getDslContext()
                .selectCount()
                .from(UnitOfMeasureKindUses)
                .where(UnitOfMeasureKindUses.UNIT_OF_MEASURE_KIND.eq(unitOfMeasureKind.getPrimaryKey()),
                        UnitOfMeasureKindUses.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }
    
    /** Assume that the entityInstance passed to this function is a ECHO_THREE.UnitOfMeasureKindUse */
    public UnitOfMeasureKindUse getUnitOfMeasureKindUseByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new UnitOfMeasureKindUsePK(entityInstance.getEntityUniqueId());

        return unitOfMeasureKindUseFactory.getEntityFromPK(entityPermission, pk);
    }

    public UnitOfMeasureKindUse getUnitOfMeasureKindUseByEntityInstance(EntityInstance entityInstance) {
        return getUnitOfMeasureKindUseByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public UnitOfMeasureKindUse getUnitOfMeasureKindUseByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getUnitOfMeasureKindUseByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    public UnitOfMeasureKindUse getUnitOfMeasureKindUse(UnitOfMeasureKindUseType unitOfMeasureKindUseType,
            UnitOfMeasureKind unitOfMeasureKind, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(UnitOfMeasureKindUses.fields())
                .from(UnitOfMeasureKindUses)
                .where(UnitOfMeasureKindUses.UNIT_OF_MEASURE_KIND_USE_TYPE.eq(unitOfMeasureKindUseType.getPrimaryKey()),
                        UnitOfMeasureKindUses.UNIT_OF_MEASURE_KIND.eq(unitOfMeasureKind.getPrimaryKey()),
                        UnitOfMeasureKindUses.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return unitOfMeasureKindUseFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public UnitOfMeasureKindUse getUnitOfMeasureKindUse(UnitOfMeasureKindUseType unitOfMeasureKindUseType,
            UnitOfMeasureKind unitOfMeasureKind) {
        return getUnitOfMeasureKindUse(unitOfMeasureKindUseType, unitOfMeasureKind, EntityPermission.READ_ONLY);
    }
    
    public UnitOfMeasureKindUse getUnitOfMeasureKindUseForUpdate(UnitOfMeasureKindUseType unitOfMeasureKindUseType,
            UnitOfMeasureKind unitOfMeasureKind) {
        return getUnitOfMeasureKindUse(unitOfMeasureKindUseType, unitOfMeasureKind, EntityPermission.READ_WRITE);
    }

    public UnitOfMeasureKindUseValue getUnitOfMeasureKindUseValue(UnitOfMeasureKindUse unitOfMeasureKindUse) {
        return unitOfMeasureKindUse == null? null: unitOfMeasureKindUse.getUnitOfMeasureKindUseValue().clone();
    }

    public UnitOfMeasureKindUseValue getUnitOfMeasureKindUseValueForUpdate(UnitOfMeasureKindUseType unitOfMeasureKindUseType,
            UnitOfMeasureKind unitOfMeasureKind) {
        return getUnitOfMeasureKindUseValue(getUnitOfMeasureKindUseForUpdate(unitOfMeasureKindUseType, unitOfMeasureKind));
    }
    
    private UnitOfMeasureKindUse getDefaultUnitOfMeasureKindUse(UnitOfMeasureKindUseType unitOfMeasureKindUseType,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(UnitOfMeasureKindUses.fields())
                .from(UnitOfMeasureKindUses)
                .where(UnitOfMeasureKindUses.UNIT_OF_MEASURE_KIND_USE_TYPE.eq(unitOfMeasureKindUseType.getPrimaryKey()),
                        UnitOfMeasureKindUses.IS_DEFAULT.eq(true),
                        UnitOfMeasureKindUses.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return unitOfMeasureKindUseFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public UnitOfMeasureKindUse getDefaultUnitOfMeasureKindUse(UnitOfMeasureKindUseType unitOfMeasureKindUseType) {
        return getDefaultUnitOfMeasureKindUse(unitOfMeasureKindUseType, EntityPermission.READ_ONLY);
    }
    
    public UnitOfMeasureKindUse getDefaultUnitOfMeasureKindUseForUpdate(UnitOfMeasureKindUseType unitOfMeasureKindUseType) {
        return getDefaultUnitOfMeasureKindUse(unitOfMeasureKindUseType, EntityPermission.READ_WRITE);
    }
    
    public UnitOfMeasureKindUseValue getDefaultUnitOfMeasureKindUseValueForUpdate(UnitOfMeasureKindUseType unitOfMeasureKindUseType) {
        return getDefaultUnitOfMeasureKindUseForUpdate(unitOfMeasureKindUseType).getUnitOfMeasureKindUseValue().clone();
    }
    
    private List<UnitOfMeasureKindUse> getUnitOfMeasureKindUsesByUnitOfMeasureKind(UnitOfMeasureKind unitOfMeasureKind,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(UnitOfMeasureKindUses.fields())
                    .from(UnitOfMeasureKindUses)
                    .join(UnitOfMeasureKindUseTypes).onKey(UNIT_OF_MEASURE_KIND_USES_UNIT_OF_MEASURE_KIND_USE_TYPE_FK)
                    .where(UnitOfMeasureKindUses.UNIT_OF_MEASURE_KIND.eq(unitOfMeasureKind.getPrimaryKey()),
                            UnitOfMeasureKindUses.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(UnitOfMeasureKindUses.SORT_ORDER, UnitOfMeasureKindUseTypes.SORT_ORDER, UnitOfMeasureKindUseTypes.UNIT_OF_MEASURE_KIND_USE_TYPE_NAME),
                    UnitOfMeasureKindUseFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(UnitOfMeasureKindUses.fields())
                    .from(UnitOfMeasureKindUses)
                    .where(UnitOfMeasureKindUses.UNIT_OF_MEASURE_KIND.eq(unitOfMeasureKind.getPrimaryKey()),
                            UnitOfMeasureKindUses.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return unitOfMeasureKindUseFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<UnitOfMeasureKindUse> getUnitOfMeasureKindUsesByUnitOfMeasureKind(UnitOfMeasureKind unitOfMeasureKind) {
        return getUnitOfMeasureKindUsesByUnitOfMeasureKind(unitOfMeasureKind, EntityPermission.READ_ONLY);
    }
    
    public List<UnitOfMeasureKindUse> getUnitOfMeasureKindUsesByUnitOfMeasureKindForUpdate(UnitOfMeasureKind unitOfMeasureKind) {
        return getUnitOfMeasureKindUsesByUnitOfMeasureKind(unitOfMeasureKind, EntityPermission.READ_WRITE);
    }
    
    /** Get a List of UnitOfMeasureKindUses when the UnitOfMeasureKindUseType is allowed to be used by multiple
     * UnitOfMeasureKinds.
     */
    private List<UnitOfMeasureKindUse> getUnitOfMeasureKindUsesByUnitOfMeasureKindUseType(UnitOfMeasureKindUseType unitOfMeasureKindUseType,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(UnitOfMeasureKindUses.fields())
                    .from(UnitOfMeasureKindUses)
                    .join(UnitOfMeasureKinds).onKey(UNIT_OF_MEASURE_KIND_USES_UNIT_OF_MEASURE_KIND_FK)
                    .join(UnitOfMeasureKindDetails).onKey(UNIT_OF_MEASURE_KINDS_LAST_DETAIL_FK)
                    .where(UnitOfMeasureKindUses.UNIT_OF_MEASURE_KIND_USE_TYPE.eq(unitOfMeasureKindUseType.getPrimaryKey()),
                            UnitOfMeasureKindUses.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(UnitOfMeasureKindUses.SORT_ORDER, UnitOfMeasureKindDetails.SORT_ORDER, UnitOfMeasureKindDetails.UNIT_OF_MEASURE_KIND_NAME),
                    UnitOfMeasureKindUseFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(UnitOfMeasureKindUses.fields())
                    .from(UnitOfMeasureKindUses)
                    .where(UnitOfMeasureKindUses.UNIT_OF_MEASURE_KIND_USE_TYPE.eq(unitOfMeasureKindUseType.getPrimaryKey()),
                            UnitOfMeasureKindUses.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return unitOfMeasureKindUseFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<UnitOfMeasureKindUse> getUnitOfMeasureKindUsesByUnitOfMeasureKindUseType(UnitOfMeasureKindUseType unitOfMeasureKindUseType) {
        return getUnitOfMeasureKindUsesByUnitOfMeasureKindUseType(unitOfMeasureKindUseType, EntityPermission.READ_ONLY);
    }
    
    public List<UnitOfMeasureKindUse> getUnitOfMeasureKindUsesByUnitOfMeasureKindUseTypeForUpdate(UnitOfMeasureKindUseType unitOfMeasureKindUseType) {
        return getUnitOfMeasureKindUsesByUnitOfMeasureKindUseType(unitOfMeasureKindUseType, EntityPermission.READ_WRITE);
    }
    
    /** Get a single UnitOfMeasureKindUse when the UnitOfMeasureKindUseType is allowed to be used by only one
     * UnitOfMeasureKind.
     */
    public UnitOfMeasureKindUse getUnitOfMeasureKindUseByUnitOfMeasureKindUseType(UnitOfMeasureKindUseType unitOfMeasureKindUseType) {
        if(unitOfMeasureKindUseType.getAllowMultiple()) {
            throw new IllegalArgumentException();
        }

        var query = session.getDslContext()
                .select(UnitOfMeasureKindUses.fields())
                .from(UnitOfMeasureKindUses)
                .where(UnitOfMeasureKindUses.UNIT_OF_MEASURE_KIND_USE_TYPE.eq(unitOfMeasureKindUseType.getPrimaryKey()),
                        UnitOfMeasureKindUses.THRU_TIME.eq(Session.MAX_TIME));

        return unitOfMeasureKindUseFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public UnitOfMeasureKind getUnitOfMeasureKindByUnitOfMeasureKindUseType(UnitOfMeasureKindUseType unitOfMeasureKindUseType) {
        var unitOfMeasureKindUse = getUnitOfMeasureKindUseByUnitOfMeasureKindUseType(unitOfMeasureKindUseType);
        
        return unitOfMeasureKindUse == null? null: unitOfMeasureKindUse.getUnitOfMeasureKind();
    }
    
    public UnitOfMeasureKind getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(String unitOfMeasureKindUseTypeName) {
        UnitOfMeasureKindUse unitOfMeasureKindUse = null;
        var unitOfMeasureKindUseType = getUnitOfMeasureKindUseTypeByName(unitOfMeasureKindUseTypeName);
        
        if(unitOfMeasureKindUseType != null) {
            unitOfMeasureKindUse = getUnitOfMeasureKindUseByUnitOfMeasureKindUseType(unitOfMeasureKindUseType);
        }
        
        return unitOfMeasureKindUse == null? null: unitOfMeasureKindUse.getUnitOfMeasureKind();
    }
    
    public UnitOfMeasureKindUseTransfer getUnitOfMeasureKindUseTransfer(UserVisit userVisit, UnitOfMeasureKindUse unitOfMeasureKindUse) {
        return unitOfMeasureKindUse == null? null: unitOfMeasureKindUseTransferCache.getUnitOfMeasureKindUseTransfer(userVisit, unitOfMeasureKindUse);
    }
    
    public List<UnitOfMeasureKindUseTransfer> getUnitOfMeasureKindUseTransfers(final UserVisit userVisit,
            final Collection<UnitOfMeasureKindUse> unitOfMeasureKindUses) {
        List<UnitOfMeasureKindUseTransfer> unitOfMeasureKindUseTransfers = new ArrayList<>(unitOfMeasureKindUses.size());
        
        unitOfMeasureKindUses.forEach((unitOfMeasureKindUse) ->
                unitOfMeasureKindUseTransfers.add(unitOfMeasureKindUseTransferCache.getUnitOfMeasureKindUseTransfer(userVisit, unitOfMeasureKindUse))
        );
        
        return unitOfMeasureKindUseTransfers;
    }
    
    public List<UnitOfMeasureKindUseTransfer> getUnitOfMeasureKindUseTransfersByUnitOfMeasureKind(UserVisit userVisit,
            UnitOfMeasureKind unitOfMeasureKind) {
        return getUnitOfMeasureKindUseTransfers(userVisit, getUnitOfMeasureKindUsesByUnitOfMeasureKind(unitOfMeasureKind));
    }
    
    public List<UnitOfMeasureKindUseTransfer> getUnitOfMeasureKindUseTransfersByUnitOfMeasureKindUseType(UserVisit userVisit,
            UnitOfMeasureKindUseType unitOfMeasureKindUseType) {
        return getUnitOfMeasureKindUseTransfers(userVisit, getUnitOfMeasureKindUsesByUnitOfMeasureKindUseType(unitOfMeasureKindUseType));
    }
    
    private void updateUnitOfMeasureKindUseFromValue(UnitOfMeasureKindUseValue unitOfMeasureKindUseValue, boolean checkDefault,
            BasePK updatedBy) {
        if(unitOfMeasureKindUseValue.hasBeenModified()) {
            var unitOfMeasureKindUse = unitOfMeasureKindUseFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     unitOfMeasureKindUseValue.getPrimaryKey());
            
            unitOfMeasureKindUse.setThruTime(session.getStartTime());
            unitOfMeasureKindUse.store();

            var unitOfMeasureKindUseTypePK = unitOfMeasureKindUse.getUnitOfMeasureKindUseTypePK();
            var unitOfMeasureKindUseType = unitOfMeasureKindUse.getUnitOfMeasureKindUseType();
            var unitOfMeasureKindPK = unitOfMeasureKindUse.getUnitOfMeasureKindPK();
            var isDefault = unitOfMeasureKindUseValue.getIsDefault();
            var sortOrder = unitOfMeasureKindUseValue.getSortOrder();
            
            if(checkDefault) {
                var defaultUnitOfMeasureKindUse = getDefaultUnitOfMeasureKindUse(unitOfMeasureKindUseType);
                var defaultFound = defaultUnitOfMeasureKindUse != null && !defaultUnitOfMeasureKindUse.equals(unitOfMeasureKindUse);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultUnitOfMeasureKindUseValue = getDefaultUnitOfMeasureKindUseValueForUpdate(unitOfMeasureKindUseType);
                    
                    defaultUnitOfMeasureKindUseValue.setIsDefault(false);
                    updateUnitOfMeasureKindUseFromValue(defaultUnitOfMeasureKindUseValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            unitOfMeasureKindUse = unitOfMeasureKindUseFactory.create(unitOfMeasureKindUseTypePK,
                    unitOfMeasureKindPK, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(unitOfMeasureKindPK, EventTypes.MODIFY, unitOfMeasureKindUse.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    /** Given a UnitOfMeasureKindUseValue, update only the isDefault property.
     */
    public void updateUnitOfMeasureKindUseFromValue(UnitOfMeasureKindUseValue unitOfMeasureKindUseValue, BasePK updatedBy) {
        updateUnitOfMeasureKindUseFromValue(unitOfMeasureKindUseValue, true, updatedBy);
    }
    
    public void deleteUnitOfMeasureKindUse(UnitOfMeasureKindUse unitOfMeasureKindUse, BasePK deletedBy) {
        unitOfMeasureKindUse.setThruTime(session.getStartTime());
        unitOfMeasureKindUse.store();
        
        // Check for default, and pick one if necessary
        var unitOfMeasureKindUseType = unitOfMeasureKindUse.getUnitOfMeasureKindUseType();
        var defaultUnitOfMeasureKindUse = getDefaultUnitOfMeasureKindUse(unitOfMeasureKindUseType);
        if(defaultUnitOfMeasureKindUse == null) {
            var unitOfMeasureKindUses = getUnitOfMeasureKindUsesByUnitOfMeasureKindUseTypeForUpdate(unitOfMeasureKindUseType);
            
            if(!unitOfMeasureKindUses.isEmpty()) {
                var iter = unitOfMeasureKindUses.iterator();
                if(iter.hasNext()) {
                    defaultUnitOfMeasureKindUse = (UnitOfMeasureKindUse)iter.next();
                }
                var unitOfMeasureKindUseValue = defaultUnitOfMeasureKindUse.getUnitOfMeasureKindUseValue().clone();
                
                unitOfMeasureKindUseValue.setIsDefault(true);
                updateUnitOfMeasureKindUseFromValue(unitOfMeasureKindUseValue, false, deletedBy);
            }
        }
        
        sendEvent(unitOfMeasureKindUse.getUnitOfMeasureKindPK(), EventTypes.MODIFY, unitOfMeasureKindUse.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteUnitOfMeasureKindUseByUnitOfMeasureKind(UnitOfMeasureKind unitOfMeasureKind, BasePK deletedBy) {
        var unitOfMeasureKindUses = getUnitOfMeasureKindUsesByUnitOfMeasureKindForUpdate(unitOfMeasureKind);
        
        unitOfMeasureKindUses.forEach((unitOfMeasureKindUse) -> 
                deleteUnitOfMeasureKindUse(unitOfMeasureKindUse, deletedBy)
        );
    }
    
}
