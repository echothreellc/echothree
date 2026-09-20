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

package com.echothree.model.control.vendor.server.control;


import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.search.common.SearchOptions;
import static com.echothree.model.control.search.server.control.SearchControl.ENI_ENTITYUNIQUEID_COLUMN_INDEX;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.control.vendor.common.choice.ItemPurchasingCategoryChoicesBean;
import com.echothree.model.control.vendor.common.choice.VendorItemStatusChoicesBean;
import com.echothree.model.control.vendor.common.choice.VendorStatusChoicesBean;
import com.echothree.model.control.vendor.common.choice.VendorTypeChoicesBean;
import com.echothree.model.control.vendor.common.transfer.ItemPurchasingCategoryDescriptionTransfer;
import com.echothree.model.control.vendor.common.transfer.ItemPurchasingCategoryTransfer;
import com.echothree.model.control.vendor.common.transfer.VendorItemCostTransfer;
import com.echothree.model.control.vendor.common.transfer.VendorItemTransfer;
import com.echothree.model.control.vendor.common.transfer.VendorResultTransfer;
import com.echothree.model.control.vendor.common.transfer.VendorTransfer;
import com.echothree.model.control.vendor.common.transfer.VendorTypeDescriptionTransfer;
import com.echothree.model.control.vendor.common.transfer.VendorTypeTransfer;
import com.echothree.model.control.vendor.common.workflow.VendorItemStatusConstants;
import com.echothree.model.control.vendor.common.workflow.VendorStatusConstants;
import com.echothree.model.control.vendor.server.graphql.VendorObject;
import com.echothree.model.control.vendor.server.transfer.ItemPurchasingCategoryDescriptionTransferCache;
import com.echothree.model.control.vendor.server.transfer.ItemPurchasingCategoryTransferCache;
import com.echothree.model.control.vendor.server.transfer.VendorItemCostTransferCache;
import com.echothree.model.control.vendor.server.transfer.VendorItemTransferCache;
import com.echothree.model.control.vendor.server.transfer.VendorTransferCache;
import com.echothree.model.control.vendor.server.transfer.VendorTypeDescriptionTransferCache;
import com.echothree.model.control.vendor.server.transfer.VendorTypeTransferCache;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.filter.server.entity.Filter;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemAliasType;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.search.server.entity.UserVisitSearch;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.shipment.server.entity.FreeOnBoard;
import com.echothree.model.data.term.server.entity.Term;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.vendor.common.pk.ItemPurchasingCategoryPK;
import com.echothree.model.data.vendor.common.pk.VendorItemCostPK;
import com.echothree.model.data.vendor.common.pk.VendorItemPK;
import com.echothree.model.data.vendor.common.pk.VendorTypePK;
import com.echothree.model.data.vendor.server.entity.ItemPurchasingCategory;
import com.echothree.model.data.vendor.server.entity.ItemPurchasingCategoryDescription;
import com.echothree.model.data.vendor.server.entity.Vendor;
import com.echothree.model.data.vendor.server.entity.VendorItem;
import com.echothree.model.data.vendor.server.entity.VendorItemCost;
import com.echothree.model.data.vendor.server.entity.VendorType;
import com.echothree.model.data.vendor.server.entity.VendorTypeDescription;
import com.echothree.model.data.vendor.server.factory.ItemPurchasingCategoryDescriptionFactory;
import com.echothree.model.data.vendor.server.factory.ItemPurchasingCategoryDetailFactory;
import com.echothree.model.data.vendor.server.factory.ItemPurchasingCategoryFactory;
import com.echothree.model.data.vendor.server.factory.VendorFactory;
import com.echothree.model.data.vendor.server.factory.VendorItemCostFactory;
import com.echothree.model.data.vendor.server.factory.VendorItemDetailFactory;
import com.echothree.model.data.vendor.server.factory.VendorItemFactory;
import com.echothree.model.data.vendor.server.factory.VendorTypeDescriptionFactory;
import com.echothree.model.data.vendor.server.factory.VendorTypeDetailFactory;
import com.echothree.model.data.vendor.server.factory.VendorTypeFactory;
import com.echothree.model.data.vendor.server.value.ItemPurchasingCategoryDescriptionValue;
import com.echothree.model.data.vendor.server.value.ItemPurchasingCategoryDetailValue;
import com.echothree.model.data.vendor.server.value.VendorItemCostValue;
import com.echothree.model.data.vendor.server.value.VendorItemDetailValue;
import com.echothree.model.data.vendor.server.value.VendorTypeDescriptionValue;
import com.echothree.model.data.vendor.server.value.VendorTypeDetailValue;
import com.echothree.model.data.vendor.server.value.VendorValue;
import static com.echothree.model.jooq.server.keys.inventory.InventoryForeignKeys.INVENTORY_CONDITIONS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEMS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.uom.UomForeignKeys.UNIT_OF_MEASURE_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.vendor.VendorForeignKeys.ITEM_PURCHASING_CATEGORIES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.vendor.VendorForeignKeys.ITEM_PURCHASING_CATEGORY_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.vendor.VendorForeignKeys.VENDOR_ITEMS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.vendor.VendorForeignKeys.VENDOR_ITEM_COSTS_INVENTORY_CONDITION_FK;
import static com.echothree.model.jooq.server.keys.vendor.VendorForeignKeys.VENDOR_ITEM_COSTS_UNIT_OF_MEASURE_TYPE_FK;
import static com.echothree.model.jooq.server.keys.vendor.VendorForeignKeys.VENDOR_ITEM_COSTS_VENDOR_ITEM_FK;
import static com.echothree.model.jooq.server.keys.vendor.VendorForeignKeys.VENDOR_ITEM_DETAILS_ITEM_FK;
import static com.echothree.model.jooq.server.keys.vendor.VendorForeignKeys.VENDOR_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.vendor.VendorForeignKeys.VENDOR_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.tables.inventory.InventoryConditionDetails.InventoryConditionDetails;
import static com.echothree.model.jooq.server.tables.inventory.InventoryConditions.InventoryConditions;
import static com.echothree.model.jooq.server.tables.item.ItemDetails.ItemDetails;
import static com.echothree.model.jooq.server.tables.item.Items.Items;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import static com.echothree.model.jooq.server.tables.uom.UnitOfMeasureTypeDetails.UnitOfMeasureTypeDetails;
import static com.echothree.model.jooq.server.tables.uom.UnitOfMeasureTypes.UnitOfMeasureTypes;
import static com.echothree.model.jooq.server.tables.vendor.ItemPurchasingCategories.ItemPurchasingCategories;
import static com.echothree.model.jooq.server.tables.vendor.ItemPurchasingCategoryDescriptions.ItemPurchasingCategoryDescriptions;
import static com.echothree.model.jooq.server.tables.vendor.ItemPurchasingCategoryDetails.ItemPurchasingCategoryDetails;
import static com.echothree.model.jooq.server.tables.vendor.VendorItemCosts.VendorItemCosts;
import static com.echothree.model.jooq.server.tables.vendor.VendorItemDetails.VendorItemDetails;
import static com.echothree.model.jooq.server.tables.vendor.VendorItems.VendorItems;
import static com.echothree.model.jooq.server.tables.vendor.VendorTypeDescriptions.VendorTypeDescriptions;
import static com.echothree.model.jooq.server.tables.vendor.VendorTypeDetails.VendorTypeDetails;
import static com.echothree.model.jooq.server.tables.vendor.VendorTypes.VendorTypes;
import static com.echothree.model.jooq.server.tables.vendor.Vendors.Vendors;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.inject.Inject;

@CommandScope
public class VendorControl
        extends BaseModelControl {
    
    @Inject
    protected SearchControl searchControl;

    /** Creates a new instance of VendorControl */
    protected VendorControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Vendor Types
    // --------------------------------------------------------------------------------

    @Inject
    protected VendorTypeDetailFactory vendorTypeDetailFactory;

    @Inject
    protected VendorTypeFactory vendorTypeFactory;

    @Inject
    VendorTypeTransferCache vendorTypeTransferCache;

    public VendorType createVendorType(String vendorTypeName, Term defaultTerm, FreeOnBoard defaultFreeOnBoard,
            CancellationPolicy defaultCancellationPolicy, ReturnPolicy defaultReturnPolicy,
            GlAccount defaultApGlAccount, Boolean defaultHoldUntilComplete, Boolean defaultAllowBackorders, Boolean defaultAllowSubstitutions,
            Boolean defaultAllowCombiningShipments, Boolean defaultRequireReference, Boolean defaultAllowReferenceDuplicates,
            String defaultReferenceValidationPattern, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultVendorType = getDefaultVendorType();
        var defaultFound = defaultVendorType != null;
        
        if(defaultFound && isDefault) {
            var defaultVendorTypeDetailValue = getDefaultVendorTypeDetailValueForUpdate();
            
            defaultVendorTypeDetailValue.setIsDefault(false);
            updateVendorTypeFromValue(defaultVendorTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var vendorType = vendorTypeFactory.create();
        var vendorTypeDetail = vendorTypeDetailFactory.create(vendorType, vendorTypeName, defaultTerm,
                defaultFreeOnBoard, defaultCancellationPolicy, defaultReturnPolicy, defaultApGlAccount,
                defaultHoldUntilComplete, defaultAllowBackorders, defaultAllowSubstitutions, defaultAllowCombiningShipments,
                defaultRequireReference, defaultAllowReferenceDuplicates, defaultReferenceValidationPattern, isDefault,
                sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        vendorType = vendorTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                vendorType.getPrimaryKey());
        vendorType.setActiveDetail(vendorTypeDetail);
        vendorType.setLastDetail(vendorTypeDetail);
        vendorType.store();
        
        sendEvent(vendorType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return vendorType;
    }

    public long countVendorTypes() {
        return session.getDslContext()
                .selectCount()
                .from(VendorTypes)
                .join(VendorTypeDetails).onKey(VENDOR_TYPES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.VendorType */
    public VendorType getVendorTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new VendorTypePK(entityInstance.getEntityUniqueId());

        return vendorTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public VendorType getVendorTypeByEntityInstance(EntityInstance entityInstance) {
        return getVendorTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public VendorType getVendorTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getVendorTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    private List<VendorType> getVendorTypes(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(VendorTypes.fields())
                .from(VendorTypes)
                .join(VendorTypeDetails).onKey(VENDOR_TYPES_ACTIVE_DETAIL_FK);
        
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(VendorTypeDetails.SORT_ORDER, VendorTypeDetails.VENDOR_TYPE_NAME),
                    VendorTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return vendorTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<VendorType> getVendorTypes() {
        return getVendorTypes(EntityPermission.READ_ONLY);
    }
    
    public List<VendorType> getVendorTypesForUpdate() {
        return getVendorTypes(EntityPermission.READ_WRITE);
    }
    
    public VendorType getDefaultVendorType(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(VendorTypes.fields())
                .from(VendorTypes)
                .join(VendorTypeDetails).onKey(VENDOR_TYPES_ACTIVE_DETAIL_FK)
                .where(VendorTypeDetails.IS_DEFAULT.eq(true));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return vendorTypeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public VendorType getDefaultVendorType() {
        return getDefaultVendorType(EntityPermission.READ_ONLY);
    }
    
    public VendorType getDefaultVendorTypeForUpdate() {
        return getDefaultVendorType(EntityPermission.READ_WRITE);
    }
    
    public VendorTypeDetailValue getDefaultVendorTypeDetailValueForUpdate() {
        return getDefaultVendorTypeForUpdate().getLastDetailForUpdate().getVendorTypeDetailValue().clone();
    }
    
    public VendorType getVendorTypeByName(String vendorTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(VendorTypes.fields())
                .from(VendorTypes)
                .join(VendorTypeDetails).onKey(VENDOR_TYPES_ACTIVE_DETAIL_FK)
                .where(VendorTypeDetails.VENDOR_TYPE_NAME.eq(vendorTypeName));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };
            
        return vendorTypeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public VendorType getVendorTypeByName(String vendorTypeName) {
        return getVendorTypeByName(vendorTypeName, EntityPermission.READ_ONLY);
    }
    
    public VendorType getVendorTypeByNameForUpdate(String vendorTypeName) {
        return getVendorTypeByName(vendorTypeName, EntityPermission.READ_WRITE);
    }
    
    public VendorTypeDetailValue getVendorTypeDetailValueForUpdate(VendorType vendorType) {
        return vendorType == null? null: vendorType.getLastDetailForUpdate().getVendorTypeDetailValue().clone();
    }
    
    public VendorTypeDetailValue getVendorTypeDetailValueByNameForUpdate(String vendorTypeName) {
        return getVendorTypeDetailValueForUpdate(getVendorTypeByNameForUpdate(vendorTypeName));
    }
    
    public VendorTypeChoicesBean getVendorTypeChoices(String defaultVendorTypeChoice, Language language,
            boolean allowNullChoice) {
        var vendorTypes = getVendorTypes();
        var size = vendorTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultVendorTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var vendorType : vendorTypes) {
            var vendorTypeDetail = vendorType.getLastDetail();
            var label = getBestVendorTypeDescription(vendorType, language);
            var value = vendorTypeDetail.getVendorTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultVendorTypeChoice != null && defaultVendorTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && vendorTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new VendorTypeChoicesBean(labels, values, defaultValue);
    }
    
    public VendorTypeTransfer getVendorTypeTransfer(UserVisit userVisit, VendorType vendorType) {
        return vendorTypeTransferCache.getVendorTypeTransfer(userVisit, vendorType);
    }

    public List<VendorTypeTransfer> getVendorTypeTransfers(UserVisit userVisit, Collection<VendorType> vendorTypes) {
        List<VendorTypeTransfer> vendorTypeTransfers = null;

        if(vendorTypes != null) {

            vendorTypeTransfers = new ArrayList<>(vendorTypes.size());

            for(var vendorType : vendorTypes) {
                vendorTypeTransfers.add(vendorTypeTransferCache.getVendorTypeTransfer(userVisit, vendorType));
            }
        }

        return vendorTypeTransfers;
    }

    public List<VendorTypeTransfer> getVendorTypeTransfers(UserVisit userVisit) {
        return getVendorTypeTransfers(userVisit, getVendorTypes());
    }

    private void updateVendorTypeFromValue(VendorTypeDetailValue vendorTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(vendorTypeDetailValue.hasBeenModified()) {
            var vendorType = vendorTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     vendorTypeDetailValue.getVendorTypePK());
            var vendorTypeDetail = vendorType.getActiveDetailForUpdate();
            
            vendorTypeDetail.setThruTime(session.getStartTime());
            vendorTypeDetail.store();

            var vendorTypePK = vendorTypeDetail.getVendorTypePK();
            var vendorTypeName = vendorTypeDetailValue.getVendorTypeName();
            var termPK = vendorTypeDetailValue.getDefaultTermPK();
            var defaultFreeOnBoardPK = vendorTypeDetailValue.getDefaultFreeOnBoardPK();
            var defaultCancellationPolicyPK = vendorTypeDetailValue.getDefaultCancellationPolicyPK();
            var defaultReturnPolicyPK = vendorTypeDetailValue.getDefaultReturnPolicyPK();
            var defaultApGlAccountPK = vendorTypeDetailValue.getDefaultApGlAccountPK();
            var defaultHoldUntilComplete = vendorTypeDetailValue.getDefaultHoldUntilComplete();
            var defaultAllowBackorders = vendorTypeDetailValue.getDefaultAllowBackorders();
            var defaultAllowSubstitutions = vendorTypeDetailValue.getDefaultAllowSubstitutions();
            var defaultAllowCombiningShipments = vendorTypeDetailValue.getDefaultAllowCombiningShipments();
            var defaultRequireReference = vendorTypeDetailValue.getDefaultRequireReference();
            var defaultAllowReferenceDuplicates = vendorTypeDetailValue.getDefaultAllowReferenceDuplicates();
            var defaultReferenceValidationPattern = vendorTypeDetailValue.getDefaultReferenceValidationPattern();
            var isDefault = vendorTypeDetailValue.getIsDefault();
            var sortOrder = vendorTypeDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultVendorType = getDefaultVendorType();
                var defaultFound = defaultVendorType != null && !defaultVendorType.equals(vendorType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultVendorTypeDetailValue = getDefaultVendorTypeDetailValueForUpdate();
                    
                    defaultVendorTypeDetailValue.setIsDefault(false);
                    updateVendorTypeFromValue(defaultVendorTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            vendorTypeDetail = vendorTypeDetailFactory.create(vendorTypePK, vendorTypeName, termPK,
                    defaultFreeOnBoardPK, defaultCancellationPolicyPK, defaultReturnPolicyPK, defaultApGlAccountPK,
                    defaultHoldUntilComplete, defaultAllowBackorders, defaultAllowSubstitutions, defaultAllowCombiningShipments,
                    defaultRequireReference, defaultAllowReferenceDuplicates, defaultReferenceValidationPattern, isDefault,
                    sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            vendorType.setActiveDetail(vendorTypeDetail);
            vendorType.setLastDetail(vendorTypeDetail);
            
            sendEvent(vendorTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateVendorTypeFromValue(VendorTypeDetailValue vendorTypeDetailValue, BasePK updatedBy) {
        updateVendorTypeFromValue(vendorTypeDetailValue, true, updatedBy);
    }
    
    public void deleteVendorType(VendorType vendorType, BasePK deletedBy) {
        deleteVendorTypeDescriptionsByVendorType(vendorType, deletedBy);

        var vendorTypeDetail = vendorType.getLastDetailForUpdate();
        vendorTypeDetail.setThruTime(session.getStartTime());
        vendorType.setActiveDetail(null);
        vendorType.store();
        
        // Check for default, and pick one if necessary
        var defaultVendorType = getDefaultVendorType();
        if(defaultVendorType == null) {
            var vendorTypes = getVendorTypesForUpdate();
            
            if(!vendorTypes.isEmpty()) {
                var iter = vendorTypes.iterator();
                if(iter.hasNext()) {
                    defaultVendorType = iter.next();
                }
                var vendorTypeDetailValue = Objects.requireNonNull(defaultVendorType).getLastDetailForUpdate().getVendorTypeDetailValue().clone();
                
                vendorTypeDetailValue.setIsDefault(true);
                updateVendorTypeFromValue(vendorTypeDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(vendorType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Vendor Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected VendorTypeDescriptionFactory vendorTypeDescriptionFactory;

    @Inject
    VendorTypeDescriptionTransferCache vendorTypeDescriptionTransferCache;

    public VendorTypeDescription createVendorTypeDescription(VendorType vendorType, Language language, String description,
            BasePK createdBy) {
        var vendorTypeDescription = vendorTypeDescriptionFactory.create(vendorType,
                language, description,
                session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(vendorType.getPrimaryKey(), EventTypes.MODIFY, vendorTypeDescription.getPrimaryKey(),
                null, createdBy);
        
        return vendorTypeDescription;
    }
    
    private VendorTypeDescription getVendorTypeDescription(VendorType vendorType, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(VendorTypeDescriptions.fields())
                .from(VendorTypeDescriptions)
                .where(VendorTypeDescriptions.VENDOR_TYPE.eq(vendorType.getPrimaryKey()),
                        VendorTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        VendorTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };
            
        return vendorTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public VendorTypeDescription getVendorTypeDescription(VendorType vendorType, Language language) {
        return getVendorTypeDescription(vendorType, language, EntityPermission.READ_ONLY);
    }
    
    public VendorTypeDescription getVendorTypeDescriptionForUpdate(VendorType vendorType, Language language) {
        return getVendorTypeDescription(vendorType, language, EntityPermission.READ_WRITE);
    }
    
    public VendorTypeDescriptionValue getVendorTypeDescriptionValue(VendorTypeDescription vendorTypeDescription) {
        return vendorTypeDescription == null? null: vendorTypeDescription.getVendorTypeDescriptionValue().clone();
    }
    
    public VendorTypeDescriptionValue getVendorTypeDescriptionValueForUpdate(VendorType vendorType, Language language) {
        return getVendorTypeDescriptionValue(getVendorTypeDescriptionForUpdate(vendorType, language));
    }
    
    private List<VendorTypeDescription> getVendorTypeDescriptionsByVendorType(VendorType vendorType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(VendorTypeDescriptions.fields())
                    .from(VendorTypeDescriptions)
                    .join(Languages).onKey(VENDOR_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(VendorTypeDescriptions.VENDOR_TYPE.eq(vendorType.getPrimaryKey()),
                            VendorTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    VendorTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(VendorTypeDescriptions.fields())
                    .from(VendorTypeDescriptions)
                    .where(VendorTypeDescriptions.VENDOR_TYPE.eq(vendorType.getPrimaryKey()),
                            VendorTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return vendorTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<VendorTypeDescription> getVendorTypeDescriptionsByVendorType(VendorType vendorType) {
        return getVendorTypeDescriptionsByVendorType(vendorType, EntityPermission.READ_ONLY);
    }
    
    public List<VendorTypeDescription> getVendorTypeDescriptionsByVendorTypeForUpdate(VendorType vendorType) {
        return getVendorTypeDescriptionsByVendorType(vendorType, EntityPermission.READ_WRITE);
    }
    
    public String getBestVendorTypeDescription(VendorType vendorType, Language language) {
        String description;
        var vendorTypeDescription = getVendorTypeDescription(vendorType, language);
        
        if(vendorTypeDescription == null && !language.getIsDefault()) {
            vendorTypeDescription = getVendorTypeDescription(vendorType, partyControl.getDefaultLanguage());
        }
        
        if(vendorTypeDescription == null) {
            description = vendorType.getLastDetail().getVendorTypeName();
        } else {
            description = vendorTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public VendorTypeDescriptionTransfer getVendorTypeDescriptionTransfer(UserVisit userVisit, VendorTypeDescription vendorTypeDescription) {
        return vendorTypeDescriptionTransferCache.getVendorTypeDescriptionTransfer(userVisit, vendorTypeDescription);
    }
    
    public List<VendorTypeDescriptionTransfer> getVendorTypeDescriptionTransfers(UserVisit userVisit, VendorType vendorType) {
        var vendorTypeDescriptions = getVendorTypeDescriptionsByVendorType(vendorType);
        List<VendorTypeDescriptionTransfer> vendorTypeDescriptionTransfers = null;
        
        if(vendorTypeDescriptions != null) {
            
            vendorTypeDescriptionTransfers = new ArrayList<>(vendorTypeDescriptions.size());
            
            for(var vendorTypeDescription : vendorTypeDescriptions) {
                vendorTypeDescriptionTransfers.add(vendorTypeDescriptionTransferCache.getVendorTypeDescriptionTransfer(userVisit, vendorTypeDescription));
            }
        }
        
        return vendorTypeDescriptionTransfers;
    }
    
    public void updateVendorTypeDescriptionFromValue(VendorTypeDescriptionValue vendorTypeDescriptionValue, BasePK updatedBy) {
        if(vendorTypeDescriptionValue.hasBeenModified()) {
            var vendorTypeDescription = vendorTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     vendorTypeDescriptionValue.getPrimaryKey());
            
            vendorTypeDescription.setThruTime(session.getStartTime());
            vendorTypeDescription.store();

            var vendorType = vendorTypeDescription.getVendorType();
            var language = vendorTypeDescription.getLanguage();
            var description = vendorTypeDescriptionValue.getDescription();
            
            vendorTypeDescription = vendorTypeDescriptionFactory.create(vendorType, language,
                    description, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(vendorType.getPrimaryKey(), EventTypes.MODIFY, vendorTypeDescription.getPrimaryKey(),
                    null, updatedBy);
        }
    }
    
    public void deleteVendorTypeDescription(VendorTypeDescription vendorTypeDescription, BasePK deletedBy) {
        vendorTypeDescription.setThruTime(session.getStartTime());
        
        sendEvent(vendorTypeDescription.getVendorTypePK(), EventTypes.MODIFY,
                vendorTypeDescription.getPrimaryKey(), null, deletedBy);
    }
    
    public void deleteVendorTypeDescriptionsByVendorType(VendorType vendorType, BasePK deletedBy) {
        var vendorTypeDescriptions = getVendorTypeDescriptionsByVendorTypeForUpdate(vendorType);
        
        vendorTypeDescriptions.forEach((vendorTypeDescription) -> 
                deleteVendorTypeDescription(vendorTypeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Vendors
    // --------------------------------------------------------------------------------

    @Inject
    protected VendorFactory vendorFactory;

    @Inject
    VendorTransferCache vendorTransferCache;

    public Vendor createVendor(Party party, String vendorName, VendorType vendorType, Integer minimumPurchaseOrderLines,
            Integer maximumPurchaseOrderLines, Long minimumPurchaseOrderAmount, Long maximumPurchaseOrderAmount,
            Boolean useItemPurchasingCategories, ItemAliasType defaultItemAliasType, CancellationPolicy cancellationPolicy,
            ReturnPolicy returnPolicy, GlAccount apGlAccount, Boolean holdUntilComplete, Boolean allowBackorders,
            Boolean allowSubstitutions, Boolean allowCombiningShipments, Boolean requireReference, Boolean allowReferenceDuplicates,
            String referenceValidationPattern, Selector vendorItemSelector, Filter vendorItemCostFilter, BasePK createdBy) {
        var vendor = vendorFactory.create(party, vendorName, vendorType, minimumPurchaseOrderLines,
                maximumPurchaseOrderLines, minimumPurchaseOrderAmount, maximumPurchaseOrderAmount,
                useItemPurchasingCategories, defaultItemAliasType, cancellationPolicy, returnPolicy,
                apGlAccount, holdUntilComplete, allowBackorders, allowSubstitutions, allowCombiningShipments,
                requireReference, allowReferenceDuplicates, referenceValidationPattern, vendorItemSelector,
                vendorItemCostFilter, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(party.getPrimaryKey(), EventTypes.MODIFY, vendor.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return vendor;
    }

    public long countVendors() {
        return session.getDslContext()
                .selectCount()
                .from(Vendors)
                .where(Vendors.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countVendorByFilter(Filter filter) {
        return session.getDslContext()
                .selectCount()
                .from(Vendors)
                .where(Vendors.VENDOR_ITEM_COST_FILTER.eq(filter.getPrimaryKey()),
                        Vendors.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<Vendor> getVendors(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Vendors.fields())
                .from(Vendors)
                .where(Vendors.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(Vendors.VENDOR_NAME),
                    VendorFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return vendorFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<Vendor> getVendors() {
        return getVendors(EntityPermission.READ_ONLY);
    }

    public List<Vendor> getVendorsForUpdate() {
        return getVendors(EntityPermission.READ_WRITE);
    }

    public Vendor getVendor(Party party, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Vendors.fields())
                .from(Vendors)
                .where(Vendors.PARTY.eq(party.getPrimaryKey()),
                        Vendors.THRU_TIME.eq(Session.MAX_TIME));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };
            
        return vendorFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public Vendor getVendor(Party party) {
        return getVendor(party, EntityPermission.READ_ONLY);
    }
    
    public Vendor getVendorForUpdate(Party party) {
        return getVendor(party, EntityPermission.READ_WRITE);
    }
    
    public Vendor getVendorByName(String vendorName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Vendors.fields())
                .from(Vendors)
                .where(Vendors.VENDOR_NAME.eq(vendorName),
                        Vendors.THRU_TIME.eq(Session.MAX_TIME));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };
            
        return vendorFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public Vendor getVendorByName(String vendorName) {
        return getVendorByName(vendorName, EntityPermission.READ_ONLY);
    }
    
    public Vendor getVendorByNameForUpdate(String vendorName) {
        return getVendorByName(vendorName, EntityPermission.READ_WRITE);
    }
    
    private List<Vendor> getVendorsByDefaultItemAliasType(ItemAliasType defaultItemAliasType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Vendors.fields())
                .from(Vendors)
                .where(Vendors.DEFAULT_ITEM_ALIAS_TYPE.eq(defaultItemAliasType.getPrimaryKey()),
                        Vendors.THRU_TIME.eq(Session.MAX_TIME));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(Vendors.VENDOR_NAME),
                    VendorFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };
            
        return vendorFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<Vendor> getVendorsByDefaultItemAliasType(ItemAliasType defaultItemAliasType) {
        return getVendorsByDefaultItemAliasType(defaultItemAliasType, EntityPermission.READ_ONLY);
    }
    
    public List<Vendor> getVendorsByDefaultItemAliasTypeForUpdate(ItemAliasType defaultItemAliasType) {
        return getVendorsByDefaultItemAliasType(defaultItemAliasType, EntityPermission.READ_WRITE);
    }
    
    public VendorValue getVendorValue(Vendor vendor) {
        return vendor == null? null: vendor.getVendorValue().clone();
    }
    
    public VendorValue getVendorValueByNameForUpdate(String vendorName) {
        return getVendorValue(getVendorByNameForUpdate(vendorName));
    }
    
    public VendorStatusChoicesBean getVendorStatusChoices(String defaultVendorStatusChoice, Language language,
            boolean allowNullChoice, Party vendorParty, PartyPK partyPK) {
        var vendorStatusChoicesBean = new VendorStatusChoicesBean();

        if(vendorParty == null) {
            workflowControl.getWorkflowEntranceChoices(vendorStatusChoicesBean, defaultVendorStatusChoice, language, allowNullChoice,
                    workflowControl.getWorkflowByName(VendorStatusConstants.Workflow_VENDOR_STATUS), partyPK);
        } else {
            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(vendorParty.getPrimaryKey());
            var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(VendorStatusConstants.Workflow_VENDOR_STATUS,
                    entityInstance);

            workflowControl.getWorkflowDestinationChoices(vendorStatusChoicesBean, defaultVendorStatusChoice, language, allowNullChoice,
                    workflowEntityStatus.getWorkflowStep(), partyPK);
        }

        return vendorStatusChoicesBean;
    }

    public void setVendorStatus(ExecutionErrorAccumulator eea, Party party, String vendorStatusChoice, PartyPK modifiedBy) {
        var entityInstance = getEntityInstanceByBaseEntity(party);
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(VendorStatusConstants.Workflow_VENDOR_STATUS,
                entityInstance);
        var workflowDestination = vendorStatusChoice == null? null:
            workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), vendorStatusChoice);

        if(workflowDestination != null || vendorStatusChoice == null) {
            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, null, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownVendorStatusChoice.name(), vendorStatusChoice);
        }
    }

    public VendorTransfer getVendorTransfer(UserVisit userVisit, Vendor vendor) {
        return vendorTransferCache.getTransfer(userVisit, vendor);
    }
    
    public VendorTransfer getVendorTransfer(UserVisit userVisit, Party party) {
        return vendorTransferCache.getTransfer(userVisit, party);
    }

    public List<VendorTransfer> getVendorTransfers(UserVisit userVisit, Collection<Vendor> vendors) {
        var vendorTransfers = new ArrayList<VendorTransfer>(vendors.size());

        vendors.forEach((vendor) ->
                vendorTransfers.add(vendorTransferCache.getTransfer(userVisit, vendor))
        );

        return vendorTransfers;
    }

    public List<VendorTransfer> getVendorTransfers(UserVisit userVisit) {
        return getVendorTransfers(userVisit, getVendors());
    }

    public void updateVendorFromValue(VendorValue vendorValue, BasePK updatedBy) {
        if(vendorValue.hasBeenModified()) {
            var vendor = vendorFactory.getEntityFromPK(EntityPermission.READ_WRITE, vendorValue.getPrimaryKey());
            
            vendor.setThruTime(session.getStartTime());
            vendor.store();

            var partyPK = vendor.getPartyPK(); // Not updated
            var vendorName = vendorValue.getVendorName();
            var vendorTypePK = vendorValue.getVendorTypePK();
            var minimumPurchaseOrderLines = vendorValue.getMinimumPurchaseOrderLines();
            var maximumPurchaseOrderLines = vendorValue.getMaximumPurchaseOrderLines();
            var minimumPurchaseOrderAmount = vendorValue.getMinimumPurchaseOrderAmount();
            var maximumPurchaseOrderAmount = vendorValue.getMaximumPurchaseOrderAmount();
            var useItemPurchasingCategories = vendorValue.getUseItemPurchasingCategories();
            var defaultItemAliasTypePK = vendorValue.getDefaultItemAliasTypePK();
            var cancellationPolicyPK = vendorValue.getCancellationPolicyPK();
            var returnPolicyPK = vendorValue.getReturnPolicyPK();
            var apGlAccountPK = vendorValue.getApGlAccountPK();
            var holdUntilComplete = vendorValue.getHoldUntilComplete();
            var allowBackorders = vendorValue.getAllowBackorders();
            var allowSubstitutions = vendorValue.getAllowSubstitutions();
            var allowCombiningShipments = vendorValue.getAllowCombiningShipments();
            var requireReference = vendorValue.getRequireReference();
            var allowReferenceDuplicates = vendorValue.getAllowReferenceDuplicates();
            var referenceValidationPattern = vendorValue.getReferenceValidationPattern();
            var vendorItemSelectorPK = vendorValue.getVendorItemSelectorPK();
            var vendorItemCostFilterPK = vendorValue.getVendorItemCostFilterPK();
            
            vendor = vendorFactory.create(partyPK, vendorName, vendorTypePK, minimumPurchaseOrderLines, maximumPurchaseOrderLines,
                    minimumPurchaseOrderAmount, maximumPurchaseOrderAmount, useItemPurchasingCategories, defaultItemAliasTypePK, cancellationPolicyPK,
                    returnPolicyPK, apGlAccountPK, holdUntilComplete, allowBackorders, allowSubstitutions, allowCombiningShipments, requireReference,
                    allowReferenceDuplicates, referenceValidationPattern, vendorItemSelectorPK, vendorItemCostFilterPK, session.getStartTime(),
                    Session.MAX_TIME);
            
            sendEvent(partyPK, EventTypes.MODIFY, vendor.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Vendor Items
    // --------------------------------------------------------------------------------

    @Inject
    protected VendorItemDetailFactory vendorItemDetailFactory;

    @Inject
    protected VendorItemFactory vendorItemFactory;

    @Inject
    VendorItemTransferCache vendorItemTransferCache;

    public VendorItem createVendorItem(Item item, Party vendorParty, String vendorItemName, String description, Integer priority,
            CancellationPolicy cancellationPolicy, ReturnPolicy returnPolicy, BasePK createdBy) {
        var vendorItem = vendorItemFactory.create();
        var vendorItemDetail = vendorItemDetailFactory.create(vendorItem, item, vendorParty,
                vendorItemName, description, priority, cancellationPolicy, returnPolicy, session.getStartTime(),
                Session.MAX_TIME);
        
        // Convert to R/W
        vendorItem = vendorItemFactory.getEntityFromPK(EntityPermission.READ_WRITE, vendorItem.getPrimaryKey());
        vendorItem.setActiveDetail(vendorItemDetail);
        vendorItem.setLastDetail(vendorItemDetail);
        vendorItem.store();
        
        sendEvent(vendorItem.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return vendorItem;
    }

    public long countVendorItemsByItem(Item item) {
        return session.getDslContext()
                .selectCount()
                .from(VendorItems)
                .join(VendorItemDetails).onKey(VENDOR_ITEMS_ACTIVE_DETAIL_FK)
                .where(VendorItemDetails.ITEM.eq(item.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countVendorItemsByVendorParty(Party vendorParty) {
        return session.getDslContext()
                .selectCount()
                .from(VendorItems)
                .join(VendorItemDetails).onKey(VENDOR_ITEMS_ACTIVE_DETAIL_FK)
                .where(VendorItemDetails.VENDOR_PARTY.eq(vendorParty.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countVendorItemsByCancellationPolicy(CancellationPolicy cancellationPolicy) {
        return session.getDslContext()
                .selectCount()
                .from(VendorItems)
                .join(VendorItemDetails).onKey(VENDOR_ITEMS_ACTIVE_DETAIL_FK)
                .where(VendorItemDetails.CANCELLATION_POLICY.eq(cancellationPolicy.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countVendorItemsByReturnPolicy(ReturnPolicy returnPolicy) {
        return session.getDslContext()
                .selectCount()
                .from(VendorItems)
                .join(VendorItemDetails).onKey(VENDOR_ITEMS_ACTIVE_DETAIL_FK)
                .where(VendorItemDetails.RETURN_POLICY.eq(returnPolicy.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.VendorItem */
    public VendorItem getVendorItemByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new VendorItemPK(entityInstance.getEntityUniqueId());

        return vendorItemFactory.getEntityFromPK(entityPermission, pk);
    }

    public VendorItem getVendorItemByEntityInstance(EntityInstance entityInstance) {
        return getVendorItemByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public VendorItem getVendorItemByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getVendorItemByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    public VendorItem getVendorItemByVendorPartyAndVendorItemName(Party vendorParty, String vendorItemName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(VendorItems.fields())
                .from(VendorItems)
                .join(VendorItemDetails).onKey(VENDOR_ITEMS_ACTIVE_DETAIL_FK)
                .where(VendorItemDetails.VENDOR_PARTY.eq(vendorParty.getPrimaryKey()),
                        VendorItemDetails.VENDOR_ITEM_NAME.eq(vendorItemName));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };
            
        return vendorItemFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public VendorItem getVendorItemByVendorPartyAndVendorItemName(Party vendorParty, String vendorItemName) {
        return getVendorItemByVendorPartyAndVendorItemName(vendorParty, vendorItemName, EntityPermission.READ_ONLY);
    }
    
    public VendorItem getVendorItemByVendorPartyAndVendorItemNameForUpdate(Party vendorParty, String vendorItemName) {
        return getVendorItemByVendorPartyAndVendorItemName(vendorParty, vendorItemName, EntityPermission.READ_WRITE);
    }
    
    public VendorItemDetailValue getVendorItemDetailValueForUpdate(VendorItem vendorItem) {
        return vendorItem == null? null: vendorItem.getLastDetailForUpdate().getVendorItemDetailValue().clone();
    }
    
    public VendorItemDetailValue getVendorItemDetailValueByVendorPartyAndVendorItemNameForUpdate(Party vendorParty, String vendorItemName) {
        return getVendorItemDetailValueForUpdate(getVendorItemByVendorPartyAndVendorItemNameForUpdate(vendorParty, vendorItemName));
    }
    
    private List<VendorItem> getVendorItemsByItem(Item item, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(VendorItems.fields())
                .from(VendorItems)
                .join(VendorItemDetails).onKey(VENDOR_ITEMS_ACTIVE_DETAIL_FK)
                .join(Vendors).on(VendorItemDetails.VENDOR_PARTY.eq(Vendors.PARTY))
                .where(VendorItemDetails.ITEM.eq(item.getPrimaryKey()),
                        Vendors.THRU_TIME.eq(Session.MAX_TIME));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(Vendors.VENDOR_NAME),
                    VendorItemFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };
            
        return vendorItemFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<VendorItem> getVendorItemsByItem(Item item) {
        return getVendorItemsByItem(item, EntityPermission.READ_ONLY);
    }
    
    public List<VendorItem> getVendorItemsByItemForUpdate(Item item) {
        return getVendorItemsByItem(item, EntityPermission.READ_WRITE);
    }
    
    private List<VendorItem> getVendorItemsByVendorParty(Party vendorParty, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(VendorItems.fields())
                .from(VendorItems)
                .join(VendorItemDetails).onKey(VENDOR_ITEMS_ACTIVE_DETAIL_FK)
                .join(Items).onKey(VENDOR_ITEM_DETAILS_ITEM_FK)
                .join(ItemDetails).onKey(ITEMS_LAST_DETAIL_FK)
                .where(VendorItemDetails.VENDOR_PARTY.eq(vendorParty.getPrimaryKey()));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ItemDetails.ITEM_NAME),
                    VendorItemFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };
            
        return vendorItemFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<VendorItem> getVendorItemsByVendorParty(Party vendorParty) {
        return getVendorItemsByVendorParty(vendorParty, EntityPermission.READ_ONLY);
    }
    
    public List<VendorItem> getVendorItemsByVendorPartyForUpdate(Party vendorParty) {
        return getVendorItemsByVendorParty(vendorParty, EntityPermission.READ_WRITE);
    }
    
    private List<VendorItem> getVendorItemsByVendorItemName(String vendorItemName, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(VendorItems.fields())
                    .from(VendorItems)
                    .join(VendorItemDetails).onKey(VENDOR_ITEMS_ACTIVE_DETAIL_FK)
                    .join(Vendors).on(VendorItemDetails.VENDOR_PARTY.eq(Vendors.PARTY))
                    .where(VendorItemDetails.VENDOR_ITEM_NAME.eq(vendorItemName),
                            Vendors.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Vendors.VENDOR_NAME),
                    VendorItemFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(VendorItems.fields())
                    .from(VendorItems)
                    .join(VendorItemDetails).onKey(VENDOR_ITEMS_ACTIVE_DETAIL_FK)
                    .where(VendorItemDetails.VENDOR_ITEM_NAME.eq(vendorItemName))
                    .forUpdate();
        };

        return vendorItemFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<VendorItem> getVendorItemsByVendorItemName(String vendorItemName) {
        return getVendorItemsByVendorItemName(vendorItemName, EntityPermission.READ_ONLY);
    }
    
    public List<VendorItem> getVendorItemsByVendorItemNameForUpdate(String vendorItemName) {
        return getVendorItemsByVendorItemName(vendorItemName, EntityPermission.READ_WRITE);
    }
    
    public VendorItemStatusChoicesBean getVendorItemStatusChoices(String defaultVendorItemStatusChoice, Language language,
            boolean allowNullChoice, VendorItem vendorItem, PartyPK partyPK) {
        var vendorItemStatusChoicesBean = new VendorItemStatusChoicesBean();

        if(vendorItem == null) {
            workflowControl.getWorkflowEntranceChoices(vendorItemStatusChoicesBean, defaultVendorItemStatusChoice, language, allowNullChoice,
                    workflowControl.getWorkflowByName(VendorItemStatusConstants.Workflow_VENDOR_ITEM_STATUS), partyPK);
        } else {
            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(vendorItem.getPrimaryKey());
            var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(VendorItemStatusConstants.Workflow_VENDOR_ITEM_STATUS,
                    entityInstance);

            workflowControl.getWorkflowDestinationChoices(vendorItemStatusChoicesBean, defaultVendorItemStatusChoice, language, allowNullChoice,
                    workflowEntityStatus.getWorkflowStep(), partyPK);
        }

        return vendorItemStatusChoicesBean;
    }

    public void setVendorItemStatus(ExecutionErrorAccumulator eea, VendorItem vendorItem, String vendorItemStatusChoice, PartyPK modifiedBy) {
        var entityInstance = getEntityInstanceByBaseEntity(vendorItem);
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(VendorItemStatusConstants.Workflow_VENDOR_ITEM_STATUS,
                entityInstance);
        var workflowDestination = vendorItemStatusChoice == null? null:
            workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), vendorItemStatusChoice);

        if(workflowDestination != null || vendorItemStatusChoice == null) {
            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, null, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownVendorItemStatusChoice.name(), vendorItemStatusChoice);
        }
    }

    public VendorItemTransfer getVendorItemTransfer(UserVisit userVisit, VendorItem vendorItem) {
        return vendorItemTransferCache.getVendorItemTransfer(userVisit, vendorItem);
    }
    
    public List<VendorItemTransfer> getVendorItemTransfers(UserVisit userVisit, Collection<VendorItem> vendorItems) {
        List<VendorItemTransfer> vendorItemTransfers = new ArrayList<>(vendorItems.size());
        
        vendorItems.forEach((vendorItem) ->
                vendorItemTransfers.add(vendorItemTransferCache.getVendorItemTransfer(userVisit, vendorItem))
        );
        
        return vendorItemTransfers;
    }
    
    public List<VendorItemTransfer> getVendorItemTransfersByItem(UserVisit userVisit, Item item) {
        return getVendorItemTransfers(userVisit, getVendorItemsByItem(item));
    }
    
    public List<VendorItemTransfer> getVendorItemTransfersByVendorParty(UserVisit userVisit, Party vendorParty) {
        return getVendorItemTransfers(userVisit, getVendorItemsByVendorParty(vendorParty));
    }
    
    public void updateVendorItemFromValue(VendorItemDetailValue vendorItemDetailValue, BasePK updatedBy) {
        if(vendorItemDetailValue.hasBeenModified()) {
            var vendorItem = vendorItemFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     vendorItemDetailValue.getVendorItemPK());
            var vendorItemDetail = vendorItem.getActiveDetailForUpdate();
            
            vendorItemDetail.setThruTime(session.getStartTime());
            vendorItemDetail.store();

            var vendorItemPK = vendorItemDetail.getVendorItemPK(); // Not updated
            var itemPK = vendorItemDetail.getItemPK(); // Not updated
            var vendorPartyPK = vendorItemDetail.getVendorPartyPK(); // Not updated
            var vendorItemName = vendorItemDetailValue.getVendorItemName();
            var description = vendorItemDetailValue.getDescription();
            var priority = vendorItemDetailValue.getPriority();
            var cancellationPolicyPK = vendorItemDetailValue.getCancellationPolicyPK();
            var returnPolicyPK = vendorItemDetailValue.getReturnPolicyPK();
            
            vendorItemDetail = vendorItemDetailFactory.create(vendorItemPK, itemPK, vendorPartyPK,
                    vendorItemName, description, priority, cancellationPolicyPK, returnPolicyPK, session.getStartTime(),
                    Session.MAX_TIME);
            
            vendorItem.setActiveDetail(vendorItemDetail);
            vendorItem.setLastDetail(vendorItemDetail);
            
            sendEvent(vendorItemPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deleteVendorItem(VendorItem vendorItem, BasePK deletedBy) {
        deleteVendorItemCostsByVendorItem(vendorItem, deletedBy);

        var vendorItemDetail = vendorItem.getLastDetailForUpdate();
        vendorItemDetail.setThruTime(session.getStartTime());
        vendorItem.setActiveDetail(null);
        vendorItem.store();
        
        sendEvent(vendorItem.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteVendorItems(List<VendorItem> vendorItems, BasePK deletedBy) {
        vendorItems.forEach((vendorItem) -> 
                deleteVendorItem(vendorItem, deletedBy)
        );
    }
    
    public void deleteVendorItemsByItem(Item item, BasePK deletedBy) {
        deleteVendorItems(getVendorItemsByItemForUpdate(item), deletedBy);
    }
    
    public void deleteVendorItemsByVendorParty(Party vendorParty, BasePK deletedBy) {
        deleteVendorItems(getVendorItemsByVendorPartyForUpdate(vendorParty), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Vendor Item Costs
    // --------------------------------------------------------------------------------

    @Inject
    protected VendorItemCostFactory vendorItemCostFactory;

    @Inject
    VendorItemCostTransferCache vendorItemCostTransferCache;

    public VendorItemCost createVendorItemCost(VendorItem vendorItem, InventoryCondition inventoryCondition,
            UnitOfMeasureType unitOfMeasureType, Long unitCost, BasePK createdBy) {
        var vendorItemCost = vendorItemCostFactory.create(vendorItem, inventoryCondition,
                unitOfMeasureType, unitCost, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(vendorItem.getPrimaryKey(), EventTypes.MODIFY, vendorItemCost.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return vendorItemCost;
    }

    public long countVendorItemCostsByVendorItem(VendorItem vendorItem) {
        return session.getDslContext()
                .selectCount()
                .from(VendorItemCosts)
                .where(VendorItemCosts.VENDOR_ITEM.eq(vendorItem.getPrimaryKey()),
                        VendorItemCosts.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countVendorItemCostsByInventoryCondition(InventoryCondition inventoryCondition) {
        return session.getDslContext()
                .selectCount()
                .from(VendorItemCosts)
                .where(VendorItemCosts.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()),
                        VendorItemCosts.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countVendorItemCostsByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType) {
        return session.getDslContext()
                .selectCount()
                .from(VendorItemCosts)
                .where(VendorItemCosts.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                        VendorItemCosts.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.VendorItemCost */
    public VendorItemCost getVendorItemCostByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new VendorItemCostPK(entityInstance.getEntityUniqueId());

        return vendorItemCostFactory.getEntityFromPK(entityPermission, pk);
    }

    public VendorItemCost getVendorItemCostByEntityInstance(EntityInstance entityInstance) {
        return getVendorItemCostByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public VendorItemCost getVendorItemCostByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getVendorItemCostByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public VendorItemCost getVendorItemCost(VendorItem vendorItem, InventoryCondition inventoryCondition,
            UnitOfMeasureType unitOfMeasureType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(VendorItemCosts.fields())
                .from(VendorItemCosts)
                .where(VendorItemCosts.VENDOR_ITEM.eq(vendorItem.getPrimaryKey()),
                        VendorItemCosts.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()),
                        VendorItemCosts.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                        VendorItemCosts.THRU_TIME.eq(Session.MAX_TIME));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };
            
        return vendorItemCostFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public VendorItemCost getVendorItemCost(VendorItem vendorItem, InventoryCondition inventoryCondition,
            UnitOfMeasureType unitOfMeasureType) {
        return getVendorItemCost(vendorItem, inventoryCondition, unitOfMeasureType, EntityPermission.READ_ONLY);
    }
    
    public VendorItemCost getVendorItemCostForUpdate(VendorItem vendorItem, InventoryCondition inventoryCondition,
            UnitOfMeasureType unitOfMeasureType) {
        return getVendorItemCost(vendorItem, inventoryCondition, unitOfMeasureType, EntityPermission.READ_WRITE);
    }
    
    public VendorItemCostValue getVendorItemCostValue(VendorItemCost vendorItemCost) {
        return vendorItemCost == null? null: vendorItemCost.getVendorItemCostValue().clone();
    }
    
    public VendorItemCostValue getVendorItemCostValueForUpdate(VendorItem vendorItem, InventoryCondition inventoryCondition,
            UnitOfMeasureType unitOfMeasureType) {
        return getVendorItemCostValue(getVendorItemCostForUpdate(vendorItem, inventoryCondition, unitOfMeasureType));
    }
    
    private List<VendorItemCost> getVendorItemCostsByVendorItem(VendorItem vendorItem, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(VendorItemCosts.fields())
                .from(VendorItemCosts)
                .join(InventoryConditions).onKey(VENDOR_ITEM_COSTS_INVENTORY_CONDITION_FK)
                .join(InventoryConditionDetails).onKey(INVENTORY_CONDITIONS_LAST_DETAIL_FK)
                .join(UnitOfMeasureTypes).onKey(VENDOR_ITEM_COSTS_UNIT_OF_MEASURE_TYPE_FK)
                .join(UnitOfMeasureTypeDetails).onKey(UNIT_OF_MEASURE_TYPES_LAST_DETAIL_FK)
                .where(VendorItemCosts.VENDOR_ITEM.eq(vendorItem.getPrimaryKey()),
                        VendorItemCosts.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(InventoryConditionDetails.SORT_ORDER, InventoryConditionDetails.INVENTORY_CONDITION_NAME, UnitOfMeasureTypeDetails.SORT_ORDER, UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_TYPE_NAME),
                    VendorItemCostFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return vendorItemCostFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<VendorItemCost> getVendorItemCostsByVendorItem(VendorItem vendorItem) {
        return getVendorItemCostsByVendorItem(vendorItem, EntityPermission.READ_ONLY);
    }
    
    public List<VendorItemCost> getVendorItemCostsByVendorItemForUpdate(VendorItem vendorItem) {
        return getVendorItemCostsByVendorItem(vendorItem, EntityPermission.READ_WRITE);
    }
    
    public List<VendorItemCost> getVendorItemCostsByInventoryConditionForUpdate(InventoryCondition inventoryCondition) {
        var query = session.getDslContext()
                .select(VendorItemCosts.fields())
                .from(VendorItemCosts)
                .where(VendorItemCosts.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()),
                        VendorItemCosts.THRU_TIME.eq(Session.MAX_TIME))
                .forUpdate();
        
        return vendorItemCostFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, query);
    }
    
    public List<VendorItemCost> getVendorItemCostsByUnitOfMeasureTypeForUpdate(UnitOfMeasureType unitOfMeasureType) {
        var query = session.getDslContext()
                .select(VendorItemCosts.fields())
                .from(VendorItemCosts)
                .where(VendorItemCosts.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                        VendorItemCosts.THRU_TIME.eq(Session.MAX_TIME))
                .forUpdate();
        
        return vendorItemCostFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, query);
    }
    
    public List<VendorItemCost> getVendorItemCostsByItemAndUnitOfMeasureTypeForUpdate(Item item, UnitOfMeasureType unitOfMeasureType) {
        var query = session.getDslContext()
                .select(VendorItemCosts.fields())
                .from(VendorItemCosts)
                .join(VendorItems).onKey(VENDOR_ITEM_COSTS_VENDOR_ITEM_FK)
                .join(VendorItemDetails).onKey(VENDOR_ITEMS_ACTIVE_DETAIL_FK)
                .where(VendorItemDetails.ITEM.eq(item.getPrimaryKey()),
                        VendorItemCosts.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                        VendorItemCosts.THRU_TIME.eq(Session.MAX_TIME))
                .forUpdate();
        
        return vendorItemCostFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, query);
    }
    
    public VendorItemCostTransfer getVendorItemCostTransfer(UserVisit userVisit, VendorItemCost vendorItemCost) {
        return vendorItemCostTransferCache.getVendorItemCostTransfer(userVisit, vendorItemCost);
    }
    
    public List<VendorItemCostTransfer> getVendorItemCostTransfers(UserVisit userVisit, Collection<VendorItemCost> vendorItemCosts) {
        List<VendorItemCostTransfer> vendorItemCostTransfers = new ArrayList<>(vendorItemCosts.size());
        
        for(var vendorItemCost : vendorItemCosts) {
            vendorItemCostTransfers.add(vendorItemCostTransferCache.getVendorItemCostTransfer(userVisit, vendorItemCost));
        }
        
        return vendorItemCostTransfers;
    }
    
    public List<VendorItemCostTransfer> getVendorItemCostTransfersByVendorItem(UserVisit userVisit, VendorItem vendorItem) {
        return getVendorItemCostTransfers(userVisit, getVendorItemCostsByVendorItem(vendorItem));
    }
    
    public void updateVendorItemCostFromValue(VendorItemCostValue vendorItemCostValue, BasePK updatedBy) {
        if(vendorItemCostValue.hasBeenModified()) {
            var vendorItemCost = vendorItemCostFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     vendorItemCostValue.getPrimaryKey());
            
            vendorItemCost.setThruTime(session.getStartTime());
            vendorItemCost.store();

            var vendorItemPK = vendorItemCost.getVendorItemPK();
            var inventoryConditionPK = vendorItemCost.getInventoryConditionPK();
            var unitOfMeasureTypePK = vendorItemCost.getUnitOfMeasureTypePK();
            var unitCost = vendorItemCostValue.getUnitCost();
            
            vendorItemCost = vendorItemCostFactory.create(vendorItemPK, inventoryConditionPK,
                    unitOfMeasureTypePK, unitCost, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(vendorItemPK, EventTypes.MODIFY, vendorItemCost.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteVendorItemCost(VendorItemCost vendorItemCost, BasePK deletedBy) {
        vendorItemCost.setThruTime(session.getStartTime());
        
        sendEvent(vendorItemCost.getVendorItemPK(), EventTypes.MODIFY, vendorItemCost.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteVendorItemCosts(List<VendorItemCost> vendorItemCosts, BasePK deletedBy) {
        vendorItemCosts.forEach((vendorItemCost) -> 
                deleteVendorItemCost(vendorItemCost, deletedBy)
        );
    }
    
    public void deleteVendorItemCostsByVendorItem(VendorItem vendorItem, BasePK deletedBy) {
        deleteVendorItemCosts(getVendorItemCostsByVendorItemForUpdate(vendorItem), deletedBy);
    }
    
    public void deleteVendorItemCostsByInventoryCondition(InventoryCondition inventoryCondition, BasePK deletedBy) {
        deleteVendorItemCosts(getVendorItemCostsByInventoryConditionForUpdate(inventoryCondition), deletedBy);
    }
    
    public void deleteVendorItemCostsByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType, BasePK deletedBy) {
        deleteVendorItemCosts(getVendorItemCostsByUnitOfMeasureTypeForUpdate(unitOfMeasureType), deletedBy);
    }
    
    public void deleteVendorItemCostsByItemAndUnitOfMeasureType(Item item, UnitOfMeasureType unitOfMeasureType, BasePK deletedBy) {
        deleteVendorItemCosts(getVendorItemCostsByItemAndUnitOfMeasureTypeForUpdate(item, unitOfMeasureType), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Item Purchasing Categories
    // --------------------------------------------------------------------------------

    @Inject
    protected ItemPurchasingCategoryDetailFactory itemPurchasingCategoryDetailFactory;

    @Inject
    protected ItemPurchasingCategoryFactory itemPurchasingCategoryFactory;

    @Inject
    ItemPurchasingCategoryTransferCache itemPurchasingCategoryTransferCache;

    public ItemPurchasingCategory createItemPurchasingCategory(String itemPurchasingCategoryName,
            ItemPurchasingCategory parentItemPurchasingCategory, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultItemPurchasingCategory = getDefaultItemPurchasingCategory();
        var defaultFound = defaultItemPurchasingCategory != null;
        
        if(defaultFound && isDefault) {
            var defaultItemPurchasingCategoryDetailValue = getDefaultItemPurchasingCategoryDetailValueForUpdate();
            
            defaultItemPurchasingCategoryDetailValue.setIsDefault(false);
            updateItemPurchasingCategoryFromValue(defaultItemPurchasingCategoryDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var itemPurchasingCategory = itemPurchasingCategoryFactory.create();
        var itemPurchasingCategoryDetail = itemPurchasingCategoryDetailFactory.create(
                itemPurchasingCategory, itemPurchasingCategoryName, parentItemPurchasingCategory, isDefault,
                sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        itemPurchasingCategory = itemPurchasingCategoryFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                itemPurchasingCategory.getPrimaryKey());
        itemPurchasingCategory.setActiveDetail(itemPurchasingCategoryDetail);
        itemPurchasingCategory.setLastDetail(itemPurchasingCategoryDetail);
        itemPurchasingCategory.store();
        
        sendEvent(itemPurchasingCategory.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return itemPurchasingCategory;
    }

    public long countItemPurchasingCategories() {
        return session.getDslContext()
                .selectCount()
                .from(ItemPurchasingCategories)
                .join(ItemPurchasingCategoryDetails).onKey(ITEM_PURCHASING_CATEGORIES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ItemPurchasingCategory */
    public ItemPurchasingCategory getItemPurchasingCategoryByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ItemPurchasingCategoryPK(entityInstance.getEntityUniqueId());

        return itemPurchasingCategoryFactory.getEntityFromPK(entityPermission, pk);
    }

    public ItemPurchasingCategory getItemPurchasingCategoryByEntityInstance(EntityInstance entityInstance) {
        return getItemPurchasingCategoryByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ItemPurchasingCategory getItemPurchasingCategoryByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getItemPurchasingCategoryByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public ItemPurchasingCategory getItemPurchasingCategoryByName(String itemPurchasingCategoryName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemPurchasingCategories.fields())
                .from(ItemPurchasingCategories)
                .join(ItemPurchasingCategoryDetails).onKey(ITEM_PURCHASING_CATEGORIES_ACTIVE_DETAIL_FK)
                .where(ItemPurchasingCategoryDetails.ITEM_PURCHASING_CATEGORY_NAME.eq(itemPurchasingCategoryName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemPurchasingCategoryFactory.getEntityFromQuery(entityPermission, query);
    }

    public ItemPurchasingCategory getItemPurchasingCategoryByName(String itemPurchasingCategoryName) {
        return getItemPurchasingCategoryByName(itemPurchasingCategoryName, EntityPermission.READ_ONLY);
    }

    public ItemPurchasingCategory getItemPurchasingCategoryByNameForUpdate(String itemPurchasingCategoryName) {
        return getItemPurchasingCategoryByName(itemPurchasingCategoryName, EntityPermission.READ_WRITE);
    }

    public ItemPurchasingCategoryDetailValue getItemPurchasingCategoryDetailValueForUpdate(ItemPurchasingCategory itemPurchasingCategory) {
        return itemPurchasingCategory == null? null: itemPurchasingCategory.getLastDetailForUpdate().getItemPurchasingCategoryDetailValue().clone();
    }

    public ItemPurchasingCategoryDetailValue getItemPurchasingCategoryDetailValueByNameForUpdate(String itemPurchasingCategoryName) {
        return getItemPurchasingCategoryDetailValueForUpdate(getItemPurchasingCategoryByNameForUpdate(itemPurchasingCategoryName));
    }

    public ItemPurchasingCategory getDefaultItemPurchasingCategory(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemPurchasingCategories.fields())
                .from(ItemPurchasingCategories)
                .join(ItemPurchasingCategoryDetails).onKey(ITEM_PURCHASING_CATEGORIES_ACTIVE_DETAIL_FK)
                .where(ItemPurchasingCategoryDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemPurchasingCategoryFactory.getEntityFromQuery(entityPermission, query);
    }

    public ItemPurchasingCategory getDefaultItemPurchasingCategory() {
        return getDefaultItemPurchasingCategory(EntityPermission.READ_ONLY);
    }

    public ItemPurchasingCategory getDefaultItemPurchasingCategoryForUpdate() {
        return getDefaultItemPurchasingCategory(EntityPermission.READ_WRITE);
    }

    public ItemPurchasingCategoryDetailValue getDefaultItemPurchasingCategoryDetailValueForUpdate() {
        return getDefaultItemPurchasingCategoryForUpdate().getLastDetailForUpdate().getItemPurchasingCategoryDetailValue().clone();
    }

    private List<ItemPurchasingCategory> getItemPurchasingCategories(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemPurchasingCategories.fields())
                .from(ItemPurchasingCategories)
                .join(ItemPurchasingCategoryDetails).onKey(ITEM_PURCHASING_CATEGORIES_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ItemPurchasingCategoryDetails.SORT_ORDER, ItemPurchasingCategoryDetails.ITEM_PURCHASING_CATEGORY_NAME),
                    ItemPurchasingCategoryFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemPurchasingCategoryFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ItemPurchasingCategory> getItemPurchasingCategories() {
        return getItemPurchasingCategories(EntityPermission.READ_ONLY);
    }

    public List<ItemPurchasingCategory> getItemPurchasingCategoriesForUpdate() {
        return getItemPurchasingCategories(EntityPermission.READ_WRITE);
    }

    private List<ItemPurchasingCategory> getItemPurchasingCategoriesByParentItemPurchasingCategory(ItemPurchasingCategory parentItemPurchasingCategory,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemPurchasingCategories.fields())
                .from(ItemPurchasingCategories)
                .join(ItemPurchasingCategoryDetails).onKey(ITEM_PURCHASING_CATEGORIES_ACTIVE_DETAIL_FK)
                .where(ItemPurchasingCategoryDetails.PARENT_ITEM_PURCHASING_CATEGORY.eq(parentItemPurchasingCategory.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ItemPurchasingCategoryDetails.SORT_ORDER, ItemPurchasingCategoryDetails.ITEM_PURCHASING_CATEGORY_NAME),
                    ItemPurchasingCategoryFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return itemPurchasingCategoryFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ItemPurchasingCategory> getItemPurchasingCategoriesByParentItemPurchasingCategory(ItemPurchasingCategory parentItemPurchasingCategory) {
        return getItemPurchasingCategoriesByParentItemPurchasingCategory(parentItemPurchasingCategory, EntityPermission.READ_ONLY);
    }

    public List<ItemPurchasingCategory> getItemPurchasingCategoriesByParentItemPurchasingCategoryForUpdate(ItemPurchasingCategory parentItemPurchasingCategory) {
        return getItemPurchasingCategoriesByParentItemPurchasingCategory(parentItemPurchasingCategory, EntityPermission.READ_WRITE);
    }

    public ItemPurchasingCategoryTransfer getItemPurchasingCategoryTransfer(UserVisit userVisit, ItemPurchasingCategory itemPurchasingCategory) {
        return itemPurchasingCategoryTransferCache.getItemPurchasingCategoryTransfer(userVisit, itemPurchasingCategory);
    }

    public List<ItemPurchasingCategoryTransfer> getItemPurchasingCategoryTransfers(UserVisit userVisit, Collection<ItemPurchasingCategory> itemPurchasingCategories) {
        var itemPurchasingCategoryTransfers = new ArrayList<ItemPurchasingCategoryTransfer>(itemPurchasingCategories.size());

        itemPurchasingCategories.forEach((itemPurchasingCategory) ->
                itemPurchasingCategoryTransfers.add(itemPurchasingCategoryTransferCache.getItemPurchasingCategoryTransfer(userVisit, itemPurchasingCategory))
        );

        return itemPurchasingCategoryTransfers;
    }

    public List<ItemPurchasingCategoryTransfer> getItemPurchasingCategoryTransfers(UserVisit userVisit) {
        return getItemPurchasingCategoryTransfers(userVisit, getItemPurchasingCategories());
    }

    public ItemPurchasingCategoryChoicesBean getItemPurchasingCategoryChoices(String defaultItemPurchasingCategoryChoice,
            Language language, boolean allowNullChoice) {
        var itemPurchasingCategories = getItemPurchasingCategories();
        var size = itemPurchasingCategories.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultItemPurchasingCategoryChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var itemPurchasingCategory : itemPurchasingCategories) {
            var itemPurchasingCategoryDetail = itemPurchasingCategory.getLastDetail();
            
            var label = getBestItemPurchasingCategoryDescription(itemPurchasingCategory, language);
            var value = itemPurchasingCategoryDetail.getItemPurchasingCategoryName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultItemPurchasingCategoryChoice != null && defaultItemPurchasingCategoryChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && itemPurchasingCategoryDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new ItemPurchasingCategoryChoicesBean(labels, values, defaultValue);
    }
    
    public boolean isParentItemPurchasingCategorySafe(ItemPurchasingCategory itemPurchasingCategory,
            ItemPurchasingCategory parentItemPurchasingCategory) {
        var safe = true;
        
        if(parentItemPurchasingCategory != null) {
            Set<ItemPurchasingCategory> parentItemPurchasingCategories = new HashSet<>();
            
            parentItemPurchasingCategories.add(itemPurchasingCategory);
            do {
                if(parentItemPurchasingCategories.contains(parentItemPurchasingCategory)) {
                    safe = false;
                    break;
                }
                
                parentItemPurchasingCategories.add(parentItemPurchasingCategory);
                parentItemPurchasingCategory = parentItemPurchasingCategory.getLastDetail().getParentItemPurchasingCategory();
            } while(parentItemPurchasingCategory != null);
        }
        
        return safe;
    }
    
    private void updateItemPurchasingCategoryFromValue(ItemPurchasingCategoryDetailValue itemPurchasingCategoryDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(itemPurchasingCategoryDetailValue.hasBeenModified()) {
            var itemPurchasingCategory = itemPurchasingCategoryFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     itemPurchasingCategoryDetailValue.getItemPurchasingCategoryPK());
            var itemPurchasingCategoryDetail = itemPurchasingCategory.getActiveDetailForUpdate();
            
            itemPurchasingCategoryDetail.setThruTime(session.getStartTime());
            itemPurchasingCategoryDetail.store();

            var itemPurchasingCategoryPK = itemPurchasingCategoryDetail.getItemPurchasingCategoryPK();
            var itemPurchasingCategoryName = itemPurchasingCategoryDetailValue.getItemPurchasingCategoryName();
            var parentItemPurchasingCategoryPK = itemPurchasingCategoryDetailValue.getParentItemPurchasingCategoryPK();
            var isDefault = itemPurchasingCategoryDetailValue.getIsDefault();
            var sortOrder = itemPurchasingCategoryDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultItemPurchasingCategory = getDefaultItemPurchasingCategory();
                var defaultFound = defaultItemPurchasingCategory != null && !defaultItemPurchasingCategory.equals(itemPurchasingCategory);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultItemPurchasingCategoryDetailValue = getDefaultItemPurchasingCategoryDetailValueForUpdate();
                    
                    defaultItemPurchasingCategoryDetailValue.setIsDefault(false);
                    updateItemPurchasingCategoryFromValue(defaultItemPurchasingCategoryDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            itemPurchasingCategoryDetail = itemPurchasingCategoryDetailFactory.create(itemPurchasingCategoryPK,
                    itemPurchasingCategoryName, parentItemPurchasingCategoryPK, isDefault, sortOrder, session.getStartTime(),
                    Session.MAX_TIME);
            
            itemPurchasingCategory.setActiveDetail(itemPurchasingCategoryDetail);
            itemPurchasingCategory.setLastDetail(itemPurchasingCategoryDetail);
            
            sendEvent(itemPurchasingCategoryPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateItemPurchasingCategoryFromValue(ItemPurchasingCategoryDetailValue itemPurchasingCategoryDetailValue, BasePK updatedBy) {
        updateItemPurchasingCategoryFromValue(itemPurchasingCategoryDetailValue, true, updatedBy);
    }
    
    private void deleteItemPurchasingCategory(ItemPurchasingCategory itemPurchasingCategory, boolean checkDefault, BasePK deletedBy) {
        var itemPurchasingCategoryDetail = itemPurchasingCategory.getLastDetailForUpdate();

        deleteItemPurchasingCategoriesByParentItemPurchasingCategory(itemPurchasingCategory, deletedBy);
        deleteItemPurchasingCategoryDescriptionsByItemPurchasingCategory(itemPurchasingCategory, deletedBy);
        
        itemPurchasingCategoryDetail.setThruTime(session.getStartTime());
        itemPurchasingCategory.setActiveDetail(null);
        itemPurchasingCategory.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var defaultItemPurchasingCategory = getDefaultItemPurchasingCategory();

            if(defaultItemPurchasingCategory == null) {
                var itemPurchasingCategories = getItemPurchasingCategoriesForUpdate();

                if(!itemPurchasingCategories.isEmpty()) {
                    var iter = itemPurchasingCategories.iterator();
                    if(iter.hasNext()) {
                        defaultItemPurchasingCategory = iter.next();
                    }
                    var itemPurchasingCategoryDetailValue = Objects.requireNonNull(defaultItemPurchasingCategory).getLastDetailForUpdate().getItemPurchasingCategoryDetailValue().clone();

                    itemPurchasingCategoryDetailValue.setIsDefault(true);
                    updateItemPurchasingCategoryFromValue(itemPurchasingCategoryDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(itemPurchasingCategory.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteItemPurchasingCategory(ItemPurchasingCategory itemPurchasingCategory, BasePK deletedBy) {
        deleteItemPurchasingCategory(itemPurchasingCategory, true, deletedBy);
    }

    private void deleteItemPurchasingCategories(List<ItemPurchasingCategory> itemPurchasingCategories, boolean checkDefault, BasePK deletedBy) {
        itemPurchasingCategories.forEach((itemPurchasingCategory) -> deleteItemPurchasingCategory(itemPurchasingCategory, checkDefault, deletedBy));
    }

    public void deleteItemPurchasingCategories(List<ItemPurchasingCategory> itemPurchasingCategories, BasePK deletedBy) {
        deleteItemPurchasingCategories(itemPurchasingCategories, true, deletedBy);
    }

    private void deleteItemPurchasingCategoriesByParentItemPurchasingCategory(ItemPurchasingCategory parentItemPurchasingCategory, BasePK deletedBy) {
        deleteItemPurchasingCategories(getItemPurchasingCategoriesByParentItemPurchasingCategoryForUpdate(parentItemPurchasingCategory), false, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Item Purchasing Category Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected ItemPurchasingCategoryDescriptionFactory itemPurchasingCategoryDescriptionFactory;

    @Inject
    ItemPurchasingCategoryDescriptionTransferCache itemPurchasingCategoryDescriptionTransferCache;

    public ItemPurchasingCategoryDescription createItemPurchasingCategoryDescription(ItemPurchasingCategory itemPurchasingCategory, Language language, String description, BasePK createdBy) {
        var itemPurchasingCategoryDescription = itemPurchasingCategoryDescriptionFactory.create(itemPurchasingCategory, language, description, session.getStartTime(),
                Session.MAX_TIME);
        
        sendEvent(itemPurchasingCategory.getPrimaryKey(), EventTypes.MODIFY, itemPurchasingCategoryDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return itemPurchasingCategoryDescription;
    }
    
    public ItemPurchasingCategoryDescription getItemPurchasingCategoryDescription(ItemPurchasingCategory itemPurchasingCategory, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ItemPurchasingCategoryDescriptions.fields())
                .from(ItemPurchasingCategoryDescriptions)
                .where(ItemPurchasingCategoryDescriptions.ITEM_PURCHASING_CATEGORY.eq(itemPurchasingCategory.getPrimaryKey()),
                        ItemPurchasingCategoryDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        ItemPurchasingCategoryDescriptions.THRU_TIME.eq(Session.MAX_TIME));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };
            
        return itemPurchasingCategoryDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ItemPurchasingCategoryDescription getItemPurchasingCategoryDescription(ItemPurchasingCategory itemPurchasingCategory, Language language) {
        return getItemPurchasingCategoryDescription(itemPurchasingCategory, language, EntityPermission.READ_ONLY);
    }
    
    public ItemPurchasingCategoryDescription getItemPurchasingCategoryDescriptionForUpdate(ItemPurchasingCategory itemPurchasingCategory, Language language) {
        return getItemPurchasingCategoryDescription(itemPurchasingCategory, language, EntityPermission.READ_WRITE);
    }
    
    public ItemPurchasingCategoryDescriptionValue getItemPurchasingCategoryDescriptionValue(ItemPurchasingCategoryDescription itemPurchasingCategoryDescription) {
        return itemPurchasingCategoryDescription == null? null: itemPurchasingCategoryDescription.getItemPurchasingCategoryDescriptionValue().clone();
    }
    
    public ItemPurchasingCategoryDescriptionValue getItemPurchasingCategoryDescriptionValueForUpdate(ItemPurchasingCategory itemPurchasingCategory, Language language) {
        return getItemPurchasingCategoryDescriptionValue(getItemPurchasingCategoryDescriptionForUpdate(itemPurchasingCategory, language));
    }
    
    private List<ItemPurchasingCategoryDescription> getItemPurchasingCategoryDescriptionsByItemPurchasingCategory(ItemPurchasingCategory itemPurchasingCategory, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ItemPurchasingCategoryDescriptions.fields())
                    .from(ItemPurchasingCategoryDescriptions)
                    .join(Languages).onKey(ITEM_PURCHASING_CATEGORY_DESCRIPTIONS_LANGUAGE_FK)
                    .where(ItemPurchasingCategoryDescriptions.ITEM_PURCHASING_CATEGORY.eq(itemPurchasingCategory.getPrimaryKey()),
                            ItemPurchasingCategoryDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    ItemPurchasingCategoryDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ItemPurchasingCategoryDescriptions.fields())
                    .from(ItemPurchasingCategoryDescriptions)
                    .where(ItemPurchasingCategoryDescriptions.ITEM_PURCHASING_CATEGORY.eq(itemPurchasingCategory.getPrimaryKey()),
                            ItemPurchasingCategoryDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return itemPurchasingCategoryDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ItemPurchasingCategoryDescription> getItemPurchasingCategoryDescriptionsByItemPurchasingCategory(ItemPurchasingCategory itemPurchasingCategory) {
        return getItemPurchasingCategoryDescriptionsByItemPurchasingCategory(itemPurchasingCategory, EntityPermission.READ_ONLY);
    }
    
    public List<ItemPurchasingCategoryDescription> getItemPurchasingCategoryDescriptionsByItemPurchasingCategoryForUpdate(ItemPurchasingCategory itemPurchasingCategory) {
        return getItemPurchasingCategoryDescriptionsByItemPurchasingCategory(itemPurchasingCategory, EntityPermission.READ_WRITE);
    }
    
    public String getBestItemPurchasingCategoryDescription(ItemPurchasingCategory itemPurchasingCategory, Language language) {
        String description;
        var itemPurchasingCategoryDescription = getItemPurchasingCategoryDescription(itemPurchasingCategory, language);
        
        if(itemPurchasingCategoryDescription == null && !language.getIsDefault()) {
            itemPurchasingCategoryDescription = getItemPurchasingCategoryDescription(itemPurchasingCategory, partyControl.getDefaultLanguage());
        }
        
        if(itemPurchasingCategoryDescription == null) {
            description = itemPurchasingCategory.getLastDetail().getItemPurchasingCategoryName();
        } else {
            description = itemPurchasingCategoryDescription.getDescription();
        }
        
        return description;
    }
    
    public ItemPurchasingCategoryDescriptionTransfer getItemPurchasingCategoryDescriptionTransfer(UserVisit userVisit, ItemPurchasingCategoryDescription itemPurchasingCategoryDescription) {
        return itemPurchasingCategoryDescriptionTransferCache.getItemPurchasingCategoryDescriptionTransfer(userVisit, itemPurchasingCategoryDescription);
    }
    
    public List<ItemPurchasingCategoryDescriptionTransfer> getItemPurchasingCategoryDescriptionTransfersByItemPurchasingCategory(UserVisit userVisit, ItemPurchasingCategory itemPurchasingCategory) {
        var itemPurchasingCategoryDescriptions = getItemPurchasingCategoryDescriptionsByItemPurchasingCategory(itemPurchasingCategory);
        List<ItemPurchasingCategoryDescriptionTransfer> itemPurchasingCategoryDescriptionTransfers = new ArrayList<>(itemPurchasingCategoryDescriptions.size());
        
        itemPurchasingCategoryDescriptions.forEach((itemPurchasingCategoryDescription) ->
                itemPurchasingCategoryDescriptionTransfers.add(itemPurchasingCategoryDescriptionTransferCache.getItemPurchasingCategoryDescriptionTransfer(userVisit, itemPurchasingCategoryDescription))
        );
        
        return itemPurchasingCategoryDescriptionTransfers;
    }
    
    public void updateItemPurchasingCategoryDescriptionFromValue(ItemPurchasingCategoryDescriptionValue itemPurchasingCategoryDescriptionValue, BasePK updatedBy) {
        if(itemPurchasingCategoryDescriptionValue.hasBeenModified()) {
            var itemPurchasingCategoryDescription = itemPurchasingCategoryDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE, itemPurchasingCategoryDescriptionValue.getPrimaryKey());
            
            itemPurchasingCategoryDescription.setThruTime(session.getStartTime());
            itemPurchasingCategoryDescription.store();

            var itemPurchasingCategory = itemPurchasingCategoryDescription.getItemPurchasingCategory();
            var language = itemPurchasingCategoryDescription.getLanguage();
            var description = itemPurchasingCategoryDescriptionValue.getDescription();
            
            itemPurchasingCategoryDescription = itemPurchasingCategoryDescriptionFactory.create(itemPurchasingCategory, language, description,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(itemPurchasingCategory.getPrimaryKey(), EventTypes.MODIFY, itemPurchasingCategoryDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteItemPurchasingCategoryDescription(ItemPurchasingCategoryDescription itemPurchasingCategoryDescription, BasePK deletedBy) {
        itemPurchasingCategoryDescription.setThruTime(session.getStartTime());
        
        sendEvent(itemPurchasingCategoryDescription.getItemPurchasingCategoryPK(), EventTypes.MODIFY, itemPurchasingCategoryDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteItemPurchasingCategoryDescriptionsByItemPurchasingCategory(ItemPurchasingCategory itemPurchasingCategory, BasePK deletedBy) {
        var itemPurchasingCategoryDescriptions = getItemPurchasingCategoryDescriptionsByItemPurchasingCategoryForUpdate(itemPurchasingCategory);
        
        itemPurchasingCategoryDescriptions.forEach((itemPurchasingCategoryDescription) -> 
                deleteItemPurchasingCategoryDescription(itemPurchasingCategoryDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Vendor Searches
    // --------------------------------------------------------------------------------

    public List<VendorResultTransfer> getVendorResultTransfers(UserVisit userVisit, UserVisitSearch userVisitSearch) {
        var vendorResultTransfers = new ArrayList<VendorResultTransfer>();
        var includeVendor = false;

        var options = session.getOptions();
        if(options != null) {
            includeVendor = options.contains(SearchOptions.VendorResultIncludeVendor);
        }

        try (var rs = searchControl.getUserVisitSearchResultSet(userVisitSearch)) {
            var vendorControl = this;

            while(rs.next()) {
                var party = partyControl.getPartyByPK(new PartyPK(rs.getLong(ENI_ENTITYUNIQUEID_COLUMN_INDEX)));

                vendorResultTransfers.add(new VendorResultTransfer(party.getLastDetail().getPartyName(),
                        includeVendor ? vendorControl.getVendorTransfer(userVisit, party) : null));
            }
        } catch(SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return vendorResultTransfers;
    }

    public List<VendorObject> getVendorObjectsFromUserVisitSearch(UserVisitSearch userVisitSearch) {
        var vendorObjects = new ArrayList<VendorObject>();

        try (var rs = searchControl.getUserVisitSearchResultSet(userVisitSearch)) {
            while(rs.next()) {
                var party = partyControl.getPartyByPK(new PartyPK(rs.getLong(ENI_ENTITYUNIQUEID_COLUMN_INDEX)));

                vendorObjects.add(new VendorObject(party));
            }
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return vendorObjects;
    }

}
