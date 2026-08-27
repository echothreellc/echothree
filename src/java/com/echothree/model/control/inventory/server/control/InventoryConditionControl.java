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

package com.echothree.model.control.inventory.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.inventory.common.choice.InventoryConditionChoicesBean;
import com.echothree.model.control.inventory.common.choice.InventoryConditionUseTypeChoicesBean;
import com.echothree.model.control.inventory.common.transfer.InventoryConditionDescriptionTransfer;
import com.echothree.model.control.inventory.common.transfer.InventoryConditionGlAccountTransfer;
import com.echothree.model.control.inventory.common.transfer.InventoryConditionTransfer;
import com.echothree.model.control.inventory.common.transfer.InventoryConditionUseTransfer;
import com.echothree.model.control.inventory.common.transfer.InventoryConditionUseTypeTransfer;
import com.echothree.model.control.inventory.server.transfer.InventoryConditionDescriptionTransferCache;
import com.echothree.model.control.inventory.server.transfer.InventoryConditionGlAccountTransferCache;
import com.echothree.model.control.inventory.server.transfer.InventoryConditionTransferCache;
import com.echothree.model.control.inventory.server.transfer.InventoryConditionUseTransferCache;
import com.echothree.model.control.inventory.server.transfer.InventoryConditionUseTypeTransferCache;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.accounting.server.entity.ItemAccountingCategory;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.inventory.common.pk.InventoryConditionPK;
import com.echothree.model.data.inventory.common.pk.InventoryConditionUseTypePK;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.inventory.server.entity.InventoryConditionDescription;
import com.echothree.model.data.inventory.server.entity.InventoryConditionGlAccount;
import com.echothree.model.data.inventory.server.entity.InventoryConditionUse;
import com.echothree.model.data.inventory.server.entity.InventoryConditionUseType;
import com.echothree.model.data.inventory.server.entity.InventoryConditionUseTypeDescription;
import com.echothree.model.data.inventory.server.factory.InventoryConditionDescriptionFactory;
import com.echothree.model.data.inventory.server.factory.InventoryConditionDetailFactory;
import com.echothree.model.data.inventory.server.factory.InventoryConditionFactory;
import com.echothree.model.data.inventory.server.factory.InventoryConditionGlAccountFactory;
import com.echothree.model.data.inventory.server.factory.InventoryConditionUseFactory;
import com.echothree.model.data.inventory.server.factory.InventoryConditionUseTypeDescriptionFactory;
import com.echothree.model.data.inventory.server.factory.InventoryConditionUseTypeFactory;
import com.echothree.model.data.inventory.server.value.InventoryConditionDescriptionValue;
import com.echothree.model.data.inventory.server.value.InventoryConditionDetailValue;
import com.echothree.model.data.inventory.server.value.InventoryConditionGlAccountValue;
import com.echothree.model.data.inventory.server.value.InventoryConditionUseValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.accounting.AccountingForeignKeys.ITEM_ACCOUNTING_CATEGORIES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.inventory.InventoryForeignKeys.INVENTORY_CONDITIONS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.tables.accounting.ItemAccountingCategories.ItemAccountingCategories;
import static com.echothree.model.jooq.server.tables.accounting.ItemAccountingCategoryDetails.ItemAccountingCategoryDetails;
import static com.echothree.model.jooq.server.tables.inventory.InventoryConditionDescriptions.InventoryConditionDescriptions;
import static com.echothree.model.jooq.server.tables.inventory.InventoryConditionDetails.InventoryConditionDetails;
import static com.echothree.model.jooq.server.tables.inventory.InventoryConditionGlAccounts.InventoryConditionGlAccounts;
import static com.echothree.model.jooq.server.tables.inventory.InventoryConditionUseTypeDescriptions.InventoryConditionUseTypeDescriptions;
import static com.echothree.model.jooq.server.tables.inventory.InventoryConditionUseTypes.InventoryConditionUseTypes;
import static com.echothree.model.jooq.server.tables.inventory.InventoryConditionUses.InventoryConditionUses;
import static com.echothree.model.jooq.server.tables.inventory.InventoryConditions.InventoryConditions;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;
import org.jooq.Condition;

@CommandScope
public class InventoryConditionControl
        extends BaseModelControl {

    @Inject
    protected InventoryLocationControl inventoryLocationControl;

    @Inject
    ItemControl itemControl;

    @Inject
    VendorControl vendorControl;

    @Inject
    InventoryLevelControl inventoryLevelControl;

    @Inject
    InventoryConditionTransferCache inventoryConditionTransferCache;

    @Inject
    InventoryConditionDescriptionTransferCache inventoryConditionDescriptionTransferCache;

    @Inject
    InventoryConditionUseTransferCache inventoryConditionUseTransferCache;

    @Inject
    InventoryConditionUseTypeTransferCache inventoryConditionUseTypeTransferCache;

    @Inject
    InventoryConditionGlAccountTransferCache inventoryConditionGlAccountTransferCache;

    /**
     * Creates a new instance of InventoryConditionControl
     */
    protected InventoryConditionControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Inventory Conditions
    // --------------------------------------------------------------------------------

    @Inject
    protected InventoryConditionFactory inventoryConditionFactory;

    @Inject
    protected InventoryConditionDetailFactory inventoryConditionDetailFactory;

    public InventoryCondition createInventoryCondition(final String inventoryConditionName, Boolean isDefault,
            final Integer sortOrder, final BasePK createdBy) {
        var defaultInventoryCondition = getDefaultInventoryCondition();
        var defaultFound = defaultInventoryCondition != null;

        if(defaultFound && isDefault) {
            var defaultInventoryConditionDetailValue = getDefaultInventoryConditionDetailValueForUpdate();

            defaultInventoryConditionDetailValue.setIsDefault(false);
            updateInventoryConditionFromValue(defaultInventoryConditionDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var inventoryCondition = inventoryConditionFactory.create();
        var inventoryConditionDetail = inventoryConditionDetailFactory.create(
                inventoryCondition, inventoryConditionName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        inventoryCondition = inventoryConditionFactory.getEntityFromPK(EntityPermission.READ_WRITE, inventoryCondition.getPrimaryKey());
        inventoryCondition.setActiveDetail(inventoryConditionDetail);
        inventoryCondition.setLastDetail(inventoryConditionDetail);
        inventoryCondition.store();

        sendEvent(inventoryCondition.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return inventoryCondition;
    }

    /**
     * Assume that the entityInstance passed to this function is a ECHO_THREE.InventoryCondition
     */
    public InventoryCondition getInventoryConditionByEntityInstance(final EntityInstance entityInstance,
            final EntityPermission entityPermission) {
        var pk = new InventoryConditionPK(entityInstance.getEntityUniqueId());

        return inventoryConditionFactory.getEntityFromPK(entityPermission, pk);
    }

    public InventoryCondition getInventoryConditionByEntityInstance(final EntityInstance entityInstance) {
        return getInventoryConditionByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public InventoryCondition getInventoryConditionByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getInventoryConditionByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countInventoryConditions() {
        return session.getDslContext()
                .selectCount()
                .from(InventoryConditions)
                .join(InventoryConditionDetails).onKey(INVENTORY_CONDITIONS_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public InventoryCondition getInventoryConditionByName(final String inventoryConditionName, final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryConditions.fields())
                .from(InventoryConditions)
                .join(InventoryConditionDetails)
                .on(InventoryConditions.INVENTORY_CONDITION.eq(InventoryConditionDetails.INVENTORY_CONDITION))
                .where(InventoryConditionDetails.INVENTORY_CONDITION_NAME.eq(inventoryConditionName),
                        InventoryConditionDetails.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return inventoryConditionFactory.getEntityFromQuery(entityPermission, query);
    }

    public InventoryCondition getInventoryConditionByName(final String inventoryConditionName) {
        return getInventoryConditionByName(inventoryConditionName, EntityPermission.READ_ONLY);
    }

    public InventoryCondition getInventoryConditionByNameForUpdate(final String inventoryConditionName) {
        return getInventoryConditionByName(inventoryConditionName, EntityPermission.READ_WRITE);
    }

    public InventoryConditionDetailValue getInventoryConditionDetailValueForUpdate(final InventoryCondition inventoryCondition) {
        return inventoryCondition == null ? null : inventoryCondition.getLastDetailForUpdate().getInventoryConditionDetailValue().clone();
    }

    public InventoryConditionDetailValue getInventoryConditionDetailValueByNameForUpdate(final String inventoryConditionName) {
        return getInventoryConditionDetailValueForUpdate(getInventoryConditionByNameForUpdate(inventoryConditionName));
    }

    public InventoryCondition getDefaultInventoryCondition(final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryConditions.fields())
                .from(InventoryConditions)
                .join(InventoryConditionDetails)
                .on(InventoryConditions.INVENTORY_CONDITION.eq(InventoryConditionDetails.INVENTORY_CONDITION))
                .where(InventoryConditionDetails.IS_DEFAULT.eq(true), InventoryConditionDetails.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return inventoryConditionFactory.getEntityFromQuery(entityPermission, query);
    }

    public InventoryCondition getDefaultInventoryCondition() {
        return getDefaultInventoryCondition(EntityPermission.READ_ONLY);
    }

    public InventoryCondition getDefaultInventoryConditionForUpdate() {
        return getDefaultInventoryCondition(EntityPermission.READ_WRITE);
    }

    public InventoryConditionDetailValue getDefaultInventoryConditionDetailValueForUpdate() {
        return getDefaultInventoryConditionForUpdate().getLastDetailForUpdate().getInventoryConditionDetailValue().clone();
    }

    private List<InventoryCondition> getInventoryConditions(final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryConditions.fields())
                .from(InventoryConditions)
                .join(InventoryConditionDetails)
                .on(InventoryConditions.INVENTORY_CONDITION.eq(InventoryConditionDetails.INVENTORY_CONDITION))
                .where(InventoryConditionDetails.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(InventoryConditionDetails.SORT_ORDER, InventoryConditionDetails.INVENTORY_CONDITION_NAME),
                    InventoryConditionFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return inventoryConditionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<InventoryCondition> getInventoryConditions() {
        return getInventoryConditions(EntityPermission.READ_ONLY);
    }

    public List<InventoryCondition> getInventoryConditionsForUpdate() {
        return getInventoryConditions(EntityPermission.READ_WRITE);
    }

    public InventoryConditionTransfer getInventoryConditionTransfer(final UserVisit userVisit,
            final InventoryCondition inventoryCondition) {
        return inventoryConditionTransferCache.getTransfer(userVisit, inventoryCondition);
    }

    public List<InventoryConditionTransfer> getInventoryConditionTransfers(final UserVisit userVisit,
            final Collection<InventoryCondition> inventoryConditions) {
        var inventoryConditionTransfers = new ArrayList<InventoryConditionTransfer>(inventoryConditions.size());

        inventoryConditions.forEach((inventoryCondition) ->
                inventoryConditionTransfers.add(inventoryConditionTransferCache.getTransfer(userVisit, inventoryCondition))
        );

        return inventoryConditionTransfers;
    }

    public List<InventoryConditionTransfer> getInventoryConditionTransfers(final UserVisit userVisit) {
        return getInventoryConditionTransfers(userVisit, getInventoryConditions());
    }

    public InventoryConditionChoicesBean getInventoryConditionChoices(final String defaultInventoryConditionChoice,
            final Language language, final boolean allowNullChoice) {
        var inventoryConditions = getInventoryConditions();
        var size = inventoryConditions.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultInventoryConditionChoice == null) {
                defaultValue = "";
            }
        }

        for(var inventoryCondition : inventoryConditions) {
            var inventoryConditionDetail = inventoryCondition.getLastDetail();

            var label = getBestInventoryConditionDescription(inventoryCondition, language);
            var value = inventoryConditionDetail.getInventoryConditionName();

            labels.add(label == null ? value : label);
            values.add(value);

            var usingDefaultChoice = Objects.equals(defaultInventoryConditionChoice, value);
            if(usingDefaultChoice || (defaultValue == null && inventoryConditionDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new InventoryConditionChoicesBean(labels, values, defaultValue);
    }

    public InventoryConditionChoicesBean getInventoryConditionChoicesByInventoryConditionUseType(final String defaultInventoryConditionChoice,
            final Language language, final boolean allowNullChoice, final InventoryConditionUseType inventoryConditionUseType) {
        var inventoryConditionUses = getInventoryConditionUsesByInventoryConditionUseType(inventoryConditionUseType);
        var size = inventoryConditionUses.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultInventoryConditionChoice == null) {
                defaultValue = "";
            }
        }

        for(var inventoryConditionUse : inventoryConditionUses) {
            var inventoryCondition = inventoryConditionUse.getInventoryCondition();
            var inventoryConditionDetail = inventoryCondition.getLastDetail();

            var label = getBestInventoryConditionDescription(inventoryCondition, language);
            var value = inventoryConditionDetail.getInventoryConditionName();

            labels.add(label == null ? value : label);
            values.add(value);

            var usingDefaultChoice = Objects.equals(defaultInventoryConditionChoice, value);
            if(usingDefaultChoice || (defaultValue == null && inventoryConditionUse.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new InventoryConditionChoicesBean(labels, values, defaultValue);
    }

    private void updateInventoryConditionFromValue(final InventoryConditionDetailValue inventoryConditionDetailValue,
            final boolean checkDefault, final BasePK updatedBy) {
        if(inventoryConditionDetailValue.hasBeenModified()) {
            var inventoryCondition = inventoryConditionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    inventoryConditionDetailValue.getInventoryConditionPK());
            var inventoryConditionDetail = inventoryCondition.getActiveDetailForUpdate();

            inventoryConditionDetail.setThruTime(session.getStartTime());
            inventoryConditionDetail.store();

            var inventoryConditionPK = inventoryConditionDetail.getInventoryConditionPK();
            var inventoryConditionName = inventoryConditionDetailValue.getInventoryConditionName();
            var isDefault = inventoryConditionDetailValue.getIsDefault();
            var sortOrder = inventoryConditionDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultInventoryCondition = getDefaultInventoryCondition();
                var defaultFound = defaultInventoryCondition != null && !defaultInventoryCondition.equals(inventoryCondition);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultInventoryConditionDetailValue = getDefaultInventoryConditionDetailValueForUpdate();

                    defaultInventoryConditionDetailValue.setIsDefault(false);
                    updateInventoryConditionFromValue(defaultInventoryConditionDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            inventoryConditionDetail = inventoryConditionDetailFactory.create(inventoryConditionPK,
                    inventoryConditionName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

            inventoryCondition.setActiveDetail(inventoryConditionDetail);
            inventoryCondition.setLastDetail(inventoryConditionDetail);

            sendEvent(inventoryConditionPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateInventoryConditionFromValue(final InventoryConditionDetailValue inventoryConditionDetailValue,
            final BasePK updatedBy) {
        updateInventoryConditionFromValue(inventoryConditionDetailValue, true, updatedBy);
    }

    public void deleteInventoryCondition(final InventoryCondition inventoryCondition, final BasePK deletedBy) {
        deleteInventoryConditionDescriptionsByInventoryCondition(inventoryCondition, deletedBy);
        deleteInventoryConditionGlAccountsByInventoryCondition(inventoryCondition, deletedBy);
        deleteInventoryConditionUseByInventoryCondition(inventoryCondition, deletedBy);
        inventoryLocationControl.deleteInventoryLocationsByInventoryCondition(inventoryCondition, deletedBy);
        inventoryLevelControl.deletePartyInventoryLevelsByInventoryCondition(inventoryCondition, deletedBy);
        itemControl.deleteItemPricesByInventoryCondition(inventoryCondition, deletedBy);
        itemControl.deleteItemKitMembersByInventoryCondition(inventoryCondition, deletedBy);
        itemControl.deleteItemUnitCustomerTypeLimitsByInventoryCondition(inventoryCondition, deletedBy);
        itemControl.deleteItemUnitLimitsByInventoryCondition(inventoryCondition, deletedBy);
        itemControl.deleteItemUnitPriceLimitsByInventoryCondition(inventoryCondition, deletedBy);
        vendorControl.deleteVendorItemCostsByInventoryCondition(inventoryCondition, deletedBy);

        var inventoryConditionDetail = inventoryCondition.getLastDetailForUpdate();
        inventoryConditionDetail.setThruTime(session.getStartTime());
        inventoryConditionDetail.store();
        inventoryCondition.setActiveDetail(null);

        // Check for default, and pick one if necessary
        var defaultInventoryCondition = getDefaultInventoryCondition();
        if(defaultInventoryCondition == null) {
            var inventoryConditions = getInventoryConditionsForUpdate();

            if(!inventoryConditions.isEmpty()) {
                var iter = inventoryConditions.iterator();
                if(iter.hasNext()) {
                    defaultInventoryCondition = iter.next();
                }
                var inventoryConditionDetailValue = Objects.requireNonNull(defaultInventoryCondition).getLastDetailForUpdate().getInventoryConditionDetailValue().clone();

                inventoryConditionDetailValue.setIsDefault(true);
                updateInventoryConditionFromValue(inventoryConditionDetailValue, false, deletedBy);
            }
        }

        sendEvent(inventoryCondition.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Inventory Condition Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected InventoryConditionDescriptionFactory inventoryConditionDescriptionFactory;

    public InventoryConditionDescription createInventoryConditionDescription(final InventoryCondition inventoryCondition,
            final Language language, final String description, final BasePK createdBy) {
        var inventoryConditionDescription = inventoryConditionDescriptionFactory.create(inventoryCondition,
                language, description, session.getStartTime(), Session.MAX_TIME);

        sendEvent(inventoryCondition.getPrimaryKey(), EventTypes.MODIFY, inventoryConditionDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return inventoryConditionDescription;
    }

    private InventoryConditionDescription getInventoryConditionDescription(final InventoryCondition inventoryCondition,
            final Language language, final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryConditionDescriptions.fields())
                .from(InventoryConditionDescriptions)
                .where(InventoryConditionDescriptions.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()),
                        InventoryConditionDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        InventoryConditionDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return inventoryConditionDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }

    public InventoryConditionDescription getInventoryConditionDescription(final InventoryCondition inventoryCondition,
            final Language language) {
        return getInventoryConditionDescription(inventoryCondition, language, EntityPermission.READ_ONLY);
    }

    public InventoryConditionDescription getInventoryConditionDescriptionForUpdate(final InventoryCondition inventoryCondition,
            final Language language) {
        return getInventoryConditionDescription(inventoryCondition, language, EntityPermission.READ_WRITE);
    }

    public InventoryConditionDescriptionValue getInventoryConditionDescriptionValue(final InventoryConditionDescription inventoryConditionDescription) {
        return inventoryConditionDescription == null ? null : inventoryConditionDescription.getInventoryConditionDescriptionValue().clone();
    }

    public InventoryConditionDescriptionValue getInventoryConditionDescriptionValueForUpdate(final InventoryCondition inventoryCondition,
            final Language language) {
        return getInventoryConditionDescriptionValue(getInventoryConditionDescriptionForUpdate(inventoryCondition, language));
    }

    private List<InventoryConditionDescription> getInventoryConditionDescriptionsByInventoryCondition(final InventoryCondition inventoryCondition,
            final EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(InventoryConditionDescriptions.fields())
                    .from(InventoryConditionDescriptions)
                    .join(Languages)
                    .on(InventoryConditionDescriptions.LANGUAGE.eq(Languages.LANGUAGE))
                    .where(InventoryConditionDescriptions.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()),
                            InventoryConditionDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    InventoryConditionDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(InventoryConditionDescriptions.fields())
                    .from(InventoryConditionDescriptions)
                    .where(InventoryConditionDescriptions.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()),
                            InventoryConditionDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return inventoryConditionDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<InventoryConditionDescription> getInventoryConditionDescriptionsByInventoryCondition(final InventoryCondition inventoryCondition) {
        return getInventoryConditionDescriptionsByInventoryCondition(inventoryCondition, EntityPermission.READ_ONLY);
    }

    public List<InventoryConditionDescription> getInventoryConditionDescriptionsByInventoryConditionForUpdate(final InventoryCondition inventoryCondition) {
        return getInventoryConditionDescriptionsByInventoryCondition(inventoryCondition, EntityPermission.READ_WRITE);
    }

    public String getBestInventoryConditionDescription(final InventoryCondition inventoryCondition, final Language language) {
        var inventoryConditionDescription = getInventoryConditionDescription(inventoryCondition, language);
        String description;

        if(inventoryConditionDescription == null && !language.getIsDefault()) {
            inventoryConditionDescription = getInventoryConditionDescription(inventoryCondition, partyControl.getDefaultLanguage());
        }

        if(inventoryConditionDescription == null) {
            description = inventoryCondition.getLastDetail().getInventoryConditionName();
        } else {
            description = inventoryConditionDescription.getDescription();
        }

        return description;
    }

    public InventoryConditionDescriptionTransfer getInventoryConditionDescriptionTransfer(final UserVisit userVisit,
            final InventoryConditionDescription inventoryConditionDescription) {
        return inventoryConditionDescriptionTransferCache.getTransfer(userVisit, inventoryConditionDescription);
    }

    public List<InventoryConditionDescriptionTransfer> getInventoryConditionDescriptionTransfersByInventoryCondition(final UserVisit userVisit,
            final InventoryCondition inventoryCondition) {
        var inventoryConditionDescriptions = getInventoryConditionDescriptionsByInventoryCondition(inventoryCondition);
        var inventoryConditionDescriptionTransfers = new ArrayList<InventoryConditionDescriptionTransfer>(inventoryConditionDescriptions.size());

        inventoryConditionDescriptions.forEach((inventoryConditionDescription) ->
                inventoryConditionDescriptionTransfers.add(inventoryConditionDescriptionTransferCache.getTransfer(userVisit, inventoryConditionDescription))
        );

        return inventoryConditionDescriptionTransfers;
    }

    public void updateInventoryConditionDescriptionFromValue(final InventoryConditionDescriptionValue inventoryConditionDescriptionValue,
            final BasePK updatedBy) {
        if(inventoryConditionDescriptionValue.hasBeenModified()) {
            var inventoryConditionDescription = inventoryConditionDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE, inventoryConditionDescriptionValue.getPrimaryKey());

            inventoryConditionDescription.setThruTime(session.getStartTime());
            inventoryConditionDescription.store();

            var inventoryCondition = inventoryConditionDescription.getInventoryCondition();
            var language = inventoryConditionDescription.getLanguage();
            var description = inventoryConditionDescriptionValue.getDescription();

            inventoryConditionDescription = inventoryConditionDescriptionFactory.create(inventoryCondition, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(inventoryCondition.getPrimaryKey(), EventTypes.MODIFY, inventoryConditionDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteInventoryConditionDescription(final InventoryConditionDescription inventoryConditionDescription, final BasePK deletedBy) {
        inventoryConditionDescription.setThruTime(session.getStartTime());

        sendEvent(inventoryConditionDescription.getInventoryConditionPK(), EventTypes.MODIFY, inventoryConditionDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteInventoryConditionDescriptionsByInventoryCondition(final InventoryCondition inventoryCondition, final BasePK deletedBy) {
        var inventoryConditionDescriptions = getInventoryConditionDescriptionsByInventoryConditionForUpdate(inventoryCondition);

        inventoryConditionDescriptions.forEach((inventoryConditionDescription) -> {
            deleteInventoryConditionDescription(inventoryConditionDescription, deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //   Inventory Condition Use Types
    // --------------------------------------------------------------------------------

    @Inject
    protected InventoryConditionUseTypeFactory inventoryConditionUseTypeFactory;

    public InventoryConditionUseType createInventoryConditionUseType(String inventoryConditionUseTypeName, Boolean isDefault,
            Integer sortOrder) {
        return inventoryConditionUseTypeFactory.create(inventoryConditionUseTypeName, isDefault, sortOrder);
    }

    /**
     * Assume that the entityInstance passed to this function is a ECHO_THREE.InventoryConditionUseType
     */
    public InventoryConditionUseType getInventoryConditionUseTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new InventoryConditionUseTypePK(entityInstance.getEntityUniqueId());

        return inventoryConditionUseTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public InventoryConditionUseType getInventoryConditionUseTypeByEntityInstance(EntityInstance entityInstance) {
        return getInventoryConditionUseTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public InventoryConditionUseType getInventoryConditionUseTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getInventoryConditionUseTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countInventoryConditionUseTypes() {
        return session.getDslContext()
                .selectCount()
                .from(InventoryConditionUseTypes)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public List<InventoryConditionUseType> getInventoryConditionUseTypes() {
        var query = session.getDslContext()
                .select(InventoryConditionUseTypes.fields())
                .from(InventoryConditionUseTypes)
                .orderBy(InventoryConditionUseTypes.INVENTORY_CONDITION_USE_TYPE_NAME);

        var limitedQuery = session.applyLimit(query,
                    InventoryConditionUseTypeFactory.class);

        return inventoryConditionUseTypeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, limitedQuery);
    }

    public InventoryConditionUseType getInventoryConditionUseTypeByName(String inventoryConditionUseTypeName) {
        var query = session.getDslContext()
                .select(InventoryConditionUseTypes.fields())
                .from(InventoryConditionUseTypes)
                .where(InventoryConditionUseTypes.INVENTORY_CONDITION_USE_TYPE_NAME.eq(inventoryConditionUseTypeName));

        return inventoryConditionUseTypeFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }

    public InventoryConditionUseTypeChoicesBean getInventoryConditionUseTypeChoices(String defaultInventoryConditionUseTypeChoice, Language language) {
        var inventoryConditionUseTypes = getInventoryConditionUseTypes();
        var size = inventoryConditionUseTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        Iterator iter = inventoryConditionUseTypes.iterator();

        while(iter.hasNext()) {
            var inventoryConditionUseType = (InventoryConditionUseType)iter.next();

            var label = getBestInventoryConditionUseTypeDescription(inventoryConditionUseType, language);
            var value = inventoryConditionUseType.getInventoryConditionUseTypeName();

            labels.add(label == null ? value : label);
            values.add(value);

            var usingDefaultChoice = defaultInventoryConditionUseTypeChoice != null && defaultInventoryConditionUseTypeChoice.equals(value);
            if(usingDefaultChoice || defaultValue == null) {
                defaultValue = value;
            }
        }

        return new InventoryConditionUseTypeChoicesBean(labels, values, defaultValue);
    }

    public InventoryConditionUseTypeTransfer getInventoryConditionUseTypeTransfer(UserVisit userVisit,
            InventoryConditionUseType inventoryConditionUseType) {
        return inventoryConditionUseTypeTransferCache.getTransfer(userVisit, inventoryConditionUseType);
    }

    public List<InventoryConditionUseTypeTransfer> getInventoryConditionUseTypeTransfers(final UserVisit userVisit,
            final Collection<InventoryConditionUseType> inventoryConditionUseTypes) {
        List<InventoryConditionUseTypeTransfer> inventoryConditionUseTypeTransfers = null;

        if(inventoryConditionUseTypes != null) {
            inventoryConditionUseTypeTransfers = new ArrayList<>(inventoryConditionUseTypes.size());

            for(var inventoryConditionUseType : inventoryConditionUseTypes) {
                inventoryConditionUseTypeTransfers.add(inventoryConditionUseTypeTransferCache.getTransfer(userVisit, inventoryConditionUseType));
            }
        }

        return inventoryConditionUseTypeTransfers;
    }

    public List<InventoryConditionUseTypeTransfer> getInventoryConditionUseTypeTransfers(UserVisit userVisit) {
        return getInventoryConditionUseTypeTransfers(userVisit, getInventoryConditionUseTypes());
    }

    // --------------------------------------------------------------------------------
    //   Inventory Condition Use Type Description
    // --------------------------------------------------------------------------------

    @Inject
    protected InventoryConditionUseTypeDescriptionFactory inventoryConditionUseTypeDescriptionFactory;

    public InventoryConditionUseTypeDescription createInventoryConditionUseTypeDescription(InventoryConditionUseType inventoryConditionUseType, Language language, String description) {
        return inventoryConditionUseTypeDescriptionFactory.create(inventoryConditionUseType, language, description);
    }

    public InventoryConditionUseTypeDescription getInventoryConditionUseTypeDescription(InventoryConditionUseType inventoryConditionUseType, Language language) {
        var query = session.getDslContext()
                .select(InventoryConditionUseTypeDescriptions.fields())
                .from(InventoryConditionUseTypeDescriptions)
                .where(InventoryConditionUseTypeDescriptions.INVENTORY_CONDITION_USE_TYPE.eq(inventoryConditionUseType.getPrimaryKey()),
                        InventoryConditionUseTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()));

        return inventoryConditionUseTypeDescriptionFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }

    public String getBestInventoryConditionUseTypeDescription(InventoryConditionUseType inventoryConditionUseType, Language language) {
        String description;
        var inventoryConditionUseTypeDescription = getInventoryConditionUseTypeDescription(inventoryConditionUseType, language);

        if(inventoryConditionUseTypeDescription == null && !language.getIsDefault()) {
            inventoryConditionUseTypeDescription = getInventoryConditionUseTypeDescription(inventoryConditionUseType, partyControl.getDefaultLanguage());
        }

        if(inventoryConditionUseTypeDescription == null) {
            description = inventoryConditionUseType.getInventoryConditionUseTypeName();
        } else {
            description = inventoryConditionUseTypeDescription.getDescription();
        }

        return description;
    }

    // --------------------------------------------------------------------------------
    //   Inventory Condition Uses
    // --------------------------------------------------------------------------------

    @Inject
    protected InventoryConditionUseFactory inventoryConditionUseFactory;

    public InventoryConditionUse createInventoryConditionUse(InventoryConditionUseType inventoryConditionUseType,
            InventoryCondition inventoryCondition, Boolean isDefault, BasePK createdBy) {
        var defaultInventoryConditionUse = getDefaultInventoryConditionUse(inventoryConditionUseType);
        var defaultFound = defaultInventoryConditionUse != null;

        if(defaultFound && isDefault) {
            var defaultInventoryConditionUseValue = getDefaultInventoryConditionUseValueForUpdate(inventoryConditionUseType);

            defaultInventoryConditionUseValue.setIsDefault(false);
            updateInventoryConditionUseFromValue(defaultInventoryConditionUseValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var inventoryConditionUse = inventoryConditionUseFactory.create(inventoryConditionUseType,
                inventoryCondition, isDefault, session.getStartTime(), Session.MAX_TIME);

        sendEvent(inventoryCondition.getPrimaryKey(), EventTypes.MODIFY, inventoryConditionUse.getPrimaryKey(),
                null, createdBy);

        return inventoryConditionUse;
    }

    public long countInventoryConditionUsesByInventoryConditionUseType(final InventoryConditionUseType inventoryConditionUseType) {
        return session.getDslContext()
                .selectCount()
                .from(InventoryConditionUses)
                .where(InventoryConditionUses.INVENTORY_CONDITION_USE_TYPE.eq(inventoryConditionUseType.getPrimaryKey()),
                        InventoryConditionUses.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countInventoryConditionUsesByInventoryCondition(final InventoryCondition inventoryCondition) {
        return session.getDslContext()
                .selectCount()
                .from(InventoryConditionUses)
                .where(InventoryConditionUses.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()),
                        InventoryConditionUses.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private InventoryConditionUse getInventoryConditionUse(InventoryConditionUseType inventoryConditionUseType,
            InventoryCondition inventoryCondition, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryConditionUses.fields())
                .from(InventoryConditionUses)
                .where(InventoryConditionUses.INVENTORY_CONDITION_USE_TYPE.eq(inventoryConditionUseType.getPrimaryKey()),
                        InventoryConditionUses.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()),
                        InventoryConditionUses.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return inventoryConditionUseFactory.getEntityFromQuery(entityPermission, query);
    }

    public InventoryConditionUse getInventoryConditionUse(InventoryConditionUseType inventoryConditionUseType,
            InventoryCondition inventoryCondition) {
        return getInventoryConditionUse(inventoryConditionUseType, inventoryCondition, EntityPermission.READ_ONLY);
    }

    public InventoryConditionUse getInventoryConditionUseForUpdate(InventoryConditionUseType inventoryConditionUseType,
            InventoryCondition inventoryCondition) {
        return getInventoryConditionUse(inventoryConditionUseType, inventoryCondition, EntityPermission.READ_WRITE);
    }

    public InventoryConditionUseValue getInventoryConditionUseValueForUpdate(InventoryConditionUseType inventoryConditionUseType,
            InventoryCondition inventoryCondition) {
        return getInventoryConditionUseForUpdate(inventoryConditionUseType, inventoryCondition).getInventoryConditionUseValue().clone();
    }

    private InventoryConditionUse getDefaultInventoryConditionUse(InventoryConditionUseType inventoryConditionUseType,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryConditionUses.fields())
                .from(InventoryConditionUses)
                .where(InventoryConditionUses.INVENTORY_CONDITION_USE_TYPE.eq(inventoryConditionUseType.getPrimaryKey()),
                        InventoryConditionUses.IS_DEFAULT.eq(true), InventoryConditionUses.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return inventoryConditionUseFactory.getEntityFromQuery(entityPermission, query);
    }

    public InventoryConditionUse getDefaultInventoryConditionUse(InventoryConditionUseType inventoryConditionUseType) {
        return getDefaultInventoryConditionUse(inventoryConditionUseType, EntityPermission.READ_ONLY);
    }

    public InventoryConditionUse getDefaultInventoryConditionUseForUpdate(InventoryConditionUseType inventoryConditionUseType) {
        return getDefaultInventoryConditionUse(inventoryConditionUseType, EntityPermission.READ_WRITE);
    }

    public InventoryConditionUseValue getDefaultInventoryConditionUseValueForUpdate(InventoryConditionUseType inventoryConditionUseType) {
        return getDefaultInventoryConditionUseForUpdate(inventoryConditionUseType).getInventoryConditionUseValue().clone();
    }

    private List<InventoryConditionUse> getInventoryConditionUsesByInventoryCondition(InventoryCondition inventoryCondition,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(InventoryConditionUses.fields())
                    .from(InventoryConditionUses)
                    .join(InventoryConditionUseTypes)
                    .on(InventoryConditionUses.INVENTORY_CONDITION_USE_TYPE.eq(InventoryConditionUseTypes.INVENTORY_CONDITION_USE_TYPE))
                    .where(InventoryConditionUses.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()),
                            InventoryConditionUses.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(InventoryConditionUseTypes.SORT_ORDER, InventoryConditionUseTypes.INVENTORY_CONDITION_USE_TYPE_NAME),
                    InventoryConditionUseFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(InventoryConditionUses.fields())
                    .from(InventoryConditionUses)
                    .where(InventoryConditionUses.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()),
                            InventoryConditionUses.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return inventoryConditionUseFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<InventoryConditionUse> getInventoryConditionUsesByInventoryCondition(InventoryCondition inventoryCondition) {
        return getInventoryConditionUsesByInventoryCondition(inventoryCondition, EntityPermission.READ_ONLY);
    }

    public List<InventoryConditionUse> getInventoryConditionUsesByInventoryConditionForUpdate(InventoryCondition inventoryCondition) {
        return getInventoryConditionUsesByInventoryCondition(inventoryCondition, EntityPermission.READ_WRITE);
    }

    /**
     * Get a List of InventoryConditionUses when the InventoryConditionUseType is allowed to be used by multiple
     * InventoryConditions.
     */
    private List<InventoryConditionUse> getInventoryConditionUsesByInventoryConditionUseType(InventoryConditionUseType inventoryConditionUseType,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(InventoryConditionUses.fields())
                    .from(InventoryConditionUses)
                    .join(InventoryConditions)
                    .on(InventoryConditionUses.INVENTORY_CONDITION.eq(InventoryConditions.INVENTORY_CONDITION))
                    .join(InventoryConditionDetails).onKey(INVENTORY_CONDITIONS_ACTIVE_DETAIL_FK)
                    .where(InventoryConditionUses.INVENTORY_CONDITION_USE_TYPE.eq(inventoryConditionUseType.getPrimaryKey()),
                            InventoryConditionUses.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(InventoryConditionDetails.SORT_ORDER, InventoryConditionDetails.INVENTORY_CONDITION_NAME),
                    InventoryConditionUseFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(InventoryConditionUses.fields())
                    .from(InventoryConditionUses)
                    .where(InventoryConditionUses.INVENTORY_CONDITION_USE_TYPE.eq(inventoryConditionUseType.getPrimaryKey()),
                            InventoryConditionUses.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return inventoryConditionUseFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<InventoryConditionUse> getInventoryConditionUsesByInventoryConditionUseType(InventoryConditionUseType inventoryConditionUseType) {
        return getInventoryConditionUsesByInventoryConditionUseType(inventoryConditionUseType, EntityPermission.READ_ONLY);
    }

    public List<InventoryConditionUse> getInventoryConditionUsesByInventoryConditionUseTypeForUpdate(InventoryConditionUseType inventoryConditionUseType) {
        return getInventoryConditionUsesByInventoryConditionUseType(inventoryConditionUseType, EntityPermission.READ_WRITE);
    }

    public List<InventoryConditionUseTransfer> getInventoryConditionUseTransfers(final UserVisit userVisit,
            final Collection<InventoryConditionUse> inventoryConditionUses) {
        List<InventoryConditionUseTransfer> inventoryConditionUseTransfers = null;

        if(inventoryConditionUses != null) {

            inventoryConditionUseTransfers = new ArrayList<>(inventoryConditionUses.size());

            for(var inventoryConditionUse : inventoryConditionUses) {
                inventoryConditionUseTransfers.add(inventoryConditionUseTransferCache.getTransfer(userVisit, inventoryConditionUse));
            }
        }
        return inventoryConditionUseTransfers;
    }

    public List<InventoryConditionUseTransfer> getInventoryConditionUseTransfersByInventoryCondition(UserVisit userVisit,
            InventoryCondition inventoryCondition) {
        return getInventoryConditionUseTransfers(userVisit, getInventoryConditionUsesByInventoryCondition(inventoryCondition));
    }

    public List<InventoryConditionUseTransfer> getInventoryConditionUseTransfersByInventoryConditionUseType(UserVisit userVisit,
            InventoryConditionUseType inventoryConditionUseType) {
        return getInventoryConditionUseTransfers(userVisit, getInventoryConditionUsesByInventoryConditionUseType(inventoryConditionUseType));
    }

    private void updateInventoryConditionUseFromValue(InventoryConditionUseValue inventoryConditionUseValue, boolean checkDefault,
            BasePK updatedBy) {
        var inventoryConditionUse = inventoryConditionUseFactory.getEntityFromPK(
                EntityPermission.READ_WRITE, inventoryConditionUseValue.getPrimaryKey());

        inventoryConditionUse.setThruTime(session.getStartTime());
        inventoryConditionUse.store();

        var inventoryConditionUseTypePK = inventoryConditionUse.getInventoryConditionUseTypePK();
        var inventoryConditionUseType = inventoryConditionUse.getInventoryConditionUseType();
        var inventoryConditionPK = inventoryConditionUse.getInventoryConditionPK();
        var isDefault = inventoryConditionUseValue.getIsDefault();

        if(checkDefault) {
            var defaultInventoryConditionUse = getDefaultInventoryConditionUse(inventoryConditionUseType);
            var defaultFound = defaultInventoryConditionUse != null && !defaultInventoryConditionUse.equals(inventoryConditionUse);

            if(isDefault && defaultFound) {
                // If I'm the default, and a default already existed...
                var defaultInventoryConditionUseValue = getDefaultInventoryConditionUseValueForUpdate(inventoryConditionUseType);

                defaultInventoryConditionUseValue.setIsDefault(false);
                updateInventoryConditionUseFromValue(defaultInventoryConditionUseValue, false, updatedBy);
            } else if(!isDefault && !defaultFound) {
                // If I'm not the default, and no other default exists...
                isDefault = true;
            }
        }

        inventoryConditionUse = inventoryConditionUseFactory.create(inventoryConditionUseTypePK,
                inventoryConditionPK, isDefault, session.getStartTime(), Session.MAX_TIME);

        sendEvent(inventoryConditionPK, EventTypes.MODIFY, inventoryConditionUse.getPrimaryKey(), null, updatedBy);
    }

    /**
     * Given an InventoryConditionUseValue, update only the isDefault property.
     */
    public void updateInventoryConditionUseFromValue(InventoryConditionUseValue inventoryConditionUseValue, BasePK updatedBy) {
        updateInventoryConditionUseFromValue(inventoryConditionUseValue, true, updatedBy);
    }

    public void deleteInventoryConditionUse(InventoryConditionUse inventoryConditionUse, BasePK deletedBy) {
        inventoryConditionUse.setThruTime(session.getStartTime());
        inventoryConditionUse.store();

        // Check for default, and pick one if necessary
        var inventoryConditionUseType = inventoryConditionUse.getInventoryConditionUseType();
        var defaultInventoryConditionUse = getDefaultInventoryConditionUse(inventoryConditionUseType);
        if(defaultInventoryConditionUse == null) {
            var inventoryConditionUses = getInventoryConditionUsesByInventoryConditionUseTypeForUpdate(inventoryConditionUseType);

            if(!inventoryConditionUses.isEmpty()) {
                Iterator iter = inventoryConditionUses.iterator();
                if(iter.hasNext()) {
                    defaultInventoryConditionUse = (InventoryConditionUse)iter.next();
                }
                var inventoryConditionUseValue = defaultInventoryConditionUse.getInventoryConditionUseValue().clone();

                inventoryConditionUseValue.setIsDefault(true);
                updateInventoryConditionUseFromValue(inventoryConditionUseValue, false, deletedBy);
            }
        }

        sendEvent(inventoryConditionUse.getInventoryConditionPK(), EventTypes.MODIFY,
                inventoryConditionUse.getPrimaryKey(), null, deletedBy);
    }

    public void deleteInventoryConditionUseByInventoryCondition(InventoryCondition inventoryCondition, BasePK deletedBy) {
        var inventoryConditionUses = getInventoryConditionUsesByInventoryConditionForUpdate(inventoryCondition);

        inventoryConditionUses.forEach((inventoryConditionUse) ->
                deleteInventoryConditionUse(inventoryConditionUse, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Inventory Condition Gl Accounts
    // --------------------------------------------------------------------------------

    @Inject
    protected InventoryConditionGlAccountFactory inventoryConditionGlAccountFactory;

    public InventoryConditionGlAccount createInventoryConditionGlAccount(InventoryCondition inventoryCondition,
            ItemAccountingCategory itemAccountingCategory, GlAccount inventoryGlAccount, GlAccount salesGlAccount,
            GlAccount returnsGlAccount, GlAccount cogsGlAccount, GlAccount returnsCogsGlAccount, BasePK createdBy) {
        var inventoryConditionGlAccount = inventoryConditionGlAccountFactory.create(
                inventoryCondition, itemAccountingCategory, inventoryGlAccount, salesGlAccount, returnsGlAccount, cogsGlAccount,
                returnsCogsGlAccount, session.getStartTime(), Session.MAX_TIME);

        sendEvent(inventoryCondition.getPrimaryKey(), EventTypes.MODIFY, inventoryConditionGlAccount.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return inventoryConditionGlAccount;
    }

    public long countInventoryConditionGlAccountByInventoryCondition(final InventoryCondition inventoryCondition) {
        return countInventoryConditionGlAccounts(InventoryConditionGlAccounts.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()));
    }

    public long countInventoryConditionGlAccountByItemAccountingCategory(final ItemAccountingCategory itemAccountingCategory) {
        return countInventoryConditionGlAccounts(InventoryConditionGlAccounts.ITEM_ACCOUNTING_CATEGORY.eq(itemAccountingCategory.getPrimaryKey()));
    }

    public long countInventoryConditionGlAccountByInventoryGlAccount(final GlAccount inventoryGlAccount) {
        return countInventoryConditionGlAccounts(InventoryConditionGlAccounts.INVENTORY_GL_ACCOUNT.eq(inventoryGlAccount.getPrimaryKey()));
    }

    public long countInventoryConditionGlAccountBySalesGlAccount(final GlAccount salesGlAccount) {
        return countInventoryConditionGlAccounts(InventoryConditionGlAccounts.SALES_GL_ACCOUNT.eq(salesGlAccount.getPrimaryKey()));
    }

    public long countInventoryConditionGlAccountByReturnsGlAccount(final GlAccount returnsGlAccount) {
        return countInventoryConditionGlAccounts(InventoryConditionGlAccounts.RETURNS_GL_ACCOUNT.eq(returnsGlAccount.getPrimaryKey()));
    }

    public long countInventoryConditionGlAccountByCogsGlAccount(final GlAccount cogsGlAccount) {
        return countInventoryConditionGlAccounts(InventoryConditionGlAccounts.COGS_GL_ACCOUNT.eq(cogsGlAccount.getPrimaryKey()));
    }

    public long countInventoryConditionGlAccountByReturnsCogsGlAccount(final GlAccount returnsCogsGlAccount) {
        return countInventoryConditionGlAccounts(InventoryConditionGlAccounts.RETURNS_COGS_GL_ACCOUNT.eq(returnsCogsGlAccount.getPrimaryKey()));
    }

    private long countInventoryConditionGlAccounts(final Condition condition) {
        return session.getDslContext()
                .selectCount()
                .from(InventoryConditionGlAccounts)
                .where(condition, InventoryConditionGlAccounts.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private InventoryConditionGlAccount getInventoryConditionGlAccount(InventoryCondition inventoryCondition,
            ItemAccountingCategory itemAccountingCategory, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryConditionGlAccounts.fields())
                .from(InventoryConditionGlAccounts)
                .where(InventoryConditionGlAccounts.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()),
                        InventoryConditionGlAccounts.ITEM_ACCOUNTING_CATEGORY.eq(itemAccountingCategory.getPrimaryKey()),
                        InventoryConditionGlAccounts.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return inventoryConditionGlAccountFactory.getEntityFromQuery(entityPermission, query);
    }

    public InventoryConditionGlAccount getInventoryConditionGlAccount(InventoryCondition inventoryCondition, ItemAccountingCategory itemAccountingCategory) {
        return getInventoryConditionGlAccount(inventoryCondition, itemAccountingCategory, EntityPermission.READ_ONLY);
    }

    public InventoryConditionGlAccount getInventoryConditionGlAccountForUpdate(InventoryCondition inventoryCondition, ItemAccountingCategory itemAccountingCategory) {
        return getInventoryConditionGlAccount(inventoryCondition, itemAccountingCategory, EntityPermission.READ_WRITE);
    }

    public InventoryConditionGlAccountValue getInventoryConditionGlAccountValueForUpdate(InventoryCondition inventoryCondition, ItemAccountingCategory itemAccountingCategory) {
        return getInventoryConditionGlAccountForUpdate(inventoryCondition, itemAccountingCategory).getInventoryConditionGlAccountValue().clone();
    }

    private List<InventoryConditionGlAccount> getInventoryConditionGlAccountsByInventoryCondition(InventoryCondition inventoryCondition, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(InventoryConditionGlAccounts.fields())
                    .from(InventoryConditionGlAccounts)
                    .join(ItemAccountingCategories)
                    .on(InventoryConditionGlAccounts.ITEM_ACCOUNTING_CATEGORY.eq(ItemAccountingCategories.ITEM_ACCOUNTING_CATEGORY))
                    .join(ItemAccountingCategoryDetails).onKey(ITEM_ACCOUNTING_CATEGORIES_LAST_DETAIL_FK)
                    .where(InventoryConditionGlAccounts.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()),
                            InventoryConditionGlAccounts.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ItemAccountingCategoryDetails.SORT_ORDER, ItemAccountingCategoryDetails.ITEM_ACCOUNTING_CATEGORY_NAME),
                    InventoryConditionGlAccountFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(InventoryConditionGlAccounts.fields())
                    .from(InventoryConditionGlAccounts)
                    .where(InventoryConditionGlAccounts.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()),
                            InventoryConditionGlAccounts.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return inventoryConditionGlAccountFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<InventoryConditionGlAccount> getInventoryConditionGlAccountsByInventoryCondition(InventoryCondition inventoryCondition) {
        return getInventoryConditionGlAccountsByInventoryCondition(inventoryCondition, EntityPermission.READ_ONLY);
    }

    public List<InventoryConditionGlAccount> getInventoryConditionGlAccountsByInventoryConditionForUpdate(InventoryCondition inventoryCondition) {
        return getInventoryConditionGlAccountsByInventoryCondition(inventoryCondition, EntityPermission.READ_WRITE);
    }

    private List<InventoryConditionGlAccount> getInventoryConditionGlAccountsByItemAccountingCategory(ItemAccountingCategory itemAccountingCategory, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(InventoryConditionGlAccounts.fields())
                    .from(InventoryConditionGlAccounts)
                    .join(InventoryConditions)
                    .on(InventoryConditionGlAccounts.INVENTORY_CONDITION.eq(InventoryConditions.INVENTORY_CONDITION))
                    .join(InventoryConditionDetails)
                    .on(InventoryConditions.LAST_DETAIL.eq(InventoryConditionDetails.INVENTORY_CONDITION_DETAIL))
                    .where(InventoryConditionGlAccounts.ITEM_ACCOUNTING_CATEGORY.eq(itemAccountingCategory.getPrimaryKey()),
                            InventoryConditionGlAccounts.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(InventoryConditionDetails.SORT_ORDER, InventoryConditionDetails.INVENTORY_CONDITION_NAME),
                    InventoryConditionGlAccountFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(InventoryConditionGlAccounts.fields())
                    .from(InventoryConditionGlAccounts)
                    .where(InventoryConditionGlAccounts.ITEM_ACCOUNTING_CATEGORY.eq(itemAccountingCategory.getPrimaryKey()),
                            InventoryConditionGlAccounts.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return inventoryConditionGlAccountFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<InventoryConditionGlAccount> getInventoryConditionGlAccountsByItemAccountingCategory(ItemAccountingCategory itemAccountingCategory) {
        return getInventoryConditionGlAccountsByItemAccountingCategory(itemAccountingCategory, EntityPermission.READ_ONLY);
    }

    public List<InventoryConditionGlAccount> getInventoryConditionGlAccountsByItemAccountingCategoryForUpdate(ItemAccountingCategory itemAccountingCategory) {
        return getInventoryConditionGlAccountsByItemAccountingCategory(itemAccountingCategory, EntityPermission.READ_WRITE);
    }

    public void updateInventoryConditionGlAccountFromValue(InventoryConditionGlAccountValue inventoryConditionGlAccountValue, BasePK updatedBy) {
        if(inventoryConditionGlAccountValue.hasBeenModified()) {
            var inventoryConditionGlAccount = inventoryConditionGlAccountFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    inventoryConditionGlAccountValue.getPrimaryKey());

            inventoryConditionGlAccount.setThruTime(session.getStartTime());
            inventoryConditionGlAccount.store();

            var inventoryConditionPK = inventoryConditionGlAccount.getInventoryConditionPK();
            var itemAccountingCategoryPK = inventoryConditionGlAccount.getItemAccountingCategoryPK();
            var inventoryGlAccountPK = inventoryConditionGlAccountValue.getInventoryGlAccountPK();
            var salesGlAccountPK = inventoryConditionGlAccountValue.getSalesGlAccountPK();
            var returnsGlAccountPK = inventoryConditionGlAccountValue.getReturnsGlAccountPK();
            var cogsGlAccountPK = inventoryConditionGlAccountValue.getCogsGlAccountPK();
            var returnsCogsGlAccountPK = inventoryConditionGlAccountValue.getReturnsCogsGlAccountPK();

            inventoryConditionGlAccount = inventoryConditionGlAccountFactory.create(inventoryConditionPK,
                    itemAccountingCategoryPK, inventoryGlAccountPK, salesGlAccountPK, returnsGlAccountPK, cogsGlAccountPK,
                    returnsCogsGlAccountPK, session.getStartTime(), Session.MAX_TIME);

            sendEvent(inventoryConditionPK, EventTypes.MODIFY, inventoryConditionGlAccount.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public InventoryConditionGlAccountTransfer getInventoryConditionGlAccountTransfer(UserVisit userVisit, InventoryConditionGlAccount inventoryConditionGlAccount) {
        return inventoryConditionGlAccount == null ? null : inventoryConditionGlAccountTransferCache.getTransfer(userVisit, inventoryConditionGlAccount);
    }

    public InventoryConditionGlAccountTransfer getInventoryConditionGlAccountTransfer(UserVisit userVisit, InventoryCondition inventoryCondition, ItemAccountingCategory itemAccountingCategory) {
        return getInventoryConditionGlAccountTransfer(userVisit, getInventoryConditionGlAccount(inventoryCondition, itemAccountingCategory));
    }

    public List<InventoryConditionGlAccountTransfer> getInventoryConditionGlAccountTransfersByInventoryCondition(UserVisit userVisit, InventoryCondition inventoryCondition) {
        var inventoryConditionGlAccounts = getInventoryConditionGlAccountsByInventoryCondition(inventoryCondition);
        List<InventoryConditionGlAccountTransfer> inventoryConditionGlAccountTransfers = new ArrayList<>(inventoryConditionGlAccounts.size());

        inventoryConditionGlAccounts.forEach((inventoryConditionGlAccount) ->
                inventoryConditionGlAccountTransfers.add(inventoryConditionGlAccountTransferCache.getTransfer(userVisit, inventoryConditionGlAccount))
        );

        return inventoryConditionGlAccountTransfers;
    }

    public void deleteInventoryConditionGlAccount(InventoryConditionGlAccount inventoryConditionGlAccount, BasePK deletedBy) {
        inventoryConditionGlAccount.setThruTime(session.getStartTime());

        sendEvent(inventoryConditionGlAccount.getInventoryConditionPK(), EventTypes.MODIFY, inventoryConditionGlAccount.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteInventoryConditionGlAccounts(List<InventoryConditionGlAccount> inventoryConditionGlAccounts, BasePK deletedBy) {
        inventoryConditionGlAccounts.forEach((inventoryConditionGlAccount) ->
                deleteInventoryConditionGlAccount(inventoryConditionGlAccount, deletedBy)
        );
    }

    public void deleteInventoryConditionGlAccountsByInventoryCondition(InventoryCondition inventoryCondition, BasePK deletedBy) {
        deleteInventoryConditionGlAccounts(getInventoryConditionGlAccountsByInventoryConditionForUpdate(inventoryCondition), deletedBy);
    }

    public void deleteInventoryConditionGlAccountsByItemAccountingCategory(ItemAccountingCategory itemAccountingCategory, BasePK deletedBy) {
        deleteInventoryConditionGlAccounts(getInventoryConditionGlAccountsByItemAccountingCategoryForUpdate(itemAccountingCategory), deletedBy);
    }

    public void deleteInventoryConditionGlAccountByInventoryConditionAndItemAccountingCategory(InventoryCondition inventoryCondition, ItemAccountingCategory itemAccountingCategory, BasePK deletedBy) {
        var inventoryConditionGlAccount = getInventoryConditionGlAccountForUpdate(inventoryCondition, itemAccountingCategory);

        if(inventoryConditionGlAccount != null) {
            deleteInventoryConditionGlAccount(inventoryConditionGlAccount, deletedBy);
        }
    }

}
