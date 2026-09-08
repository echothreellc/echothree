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
import com.echothree.model.control.inventory.common.choice.InventoryTransactionTimeTypeChoicesBean;
import com.echothree.model.control.inventory.common.transfer.InventoryTransactionTimeTypeDescriptionTransfer;
import com.echothree.model.control.inventory.common.transfer.InventoryTransactionTimeTypeTransfer;
import com.echothree.model.control.inventory.server.transfer.InventoryTransactionTimeTypeDescriptionTransferCache;
import com.echothree.model.control.inventory.server.transfer.InventoryTransactionTimeTypeTransferCache;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.inventory.common.pk.InventoryTransactionTimeTypePK;
import com.echothree.model.data.inventory.server.entity.InventoryTransaction;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionTime;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionTimeType;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionTimeTypeDescription;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionType;
import com.echothree.model.data.inventory.server.factory.InventoryTransactionTimeFactory;
import com.echothree.model.data.inventory.server.factory.InventoryTransactionTimeTypeDescriptionFactory;
import com.echothree.model.data.inventory.server.factory.InventoryTransactionTimeTypeDetailFactory;
import com.echothree.model.data.inventory.server.factory.InventoryTransactionTimeTypeFactory;
import com.echothree.model.data.inventory.server.value.InventoryTransactionTimeTypeDescriptionValue;
import com.echothree.model.data.inventory.server.value.InventoryTransactionTimeTypeDetailValue;
import com.echothree.model.data.inventory.server.value.InventoryTransactionTimeValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.inventory.InventoryForeignKeys.INVENTORY_TRANSACTIONS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.inventory.InventoryForeignKeys.INVENTORY_TRANSACTION_TIME_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.inventory.InventoryForeignKeys.INVENTORY_TRANSACTION_TIME_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.tables.inventory.InventoryTransactionDetails.InventoryTransactionDetails;
import static com.echothree.model.jooq.server.tables.inventory.InventoryTransactionTimeTypeDescriptions.InventoryTransactionTimeTypeDescriptions;
import static com.echothree.model.jooq.server.tables.inventory.InventoryTransactionTimeTypeDetails.InventoryTransactionTimeTypeDetails;
import static com.echothree.model.jooq.server.tables.inventory.InventoryTransactionTimeTypes.InventoryTransactionTimeTypes;
import static com.echothree.model.jooq.server.tables.inventory.InventoryTransactionTimes.InventoryTransactionTimes;
import static com.echothree.model.jooq.server.tables.inventory.InventoryTransactions.InventoryTransactions;
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
import org.jooq.Condition;

@CommandScope
public class InventoryTransactionTimeControl
        extends BaseModelControl {

    /** Creates a new instance of InventoryTransactionTimeControl */
    protected InventoryTransactionTimeControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Inventory Transaction Time Types
    // --------------------------------------------------------------------------------

    @Inject
    InventoryTransactionTimeTypeFactory inventoryTransactionTimeTypeFactory;

    @Inject
    InventoryTransactionTimeTypeDetailFactory inventoryTransactionTimeTypeDetailFactory;

    @Inject
    InventoryTransactionTimeTypeTransferCache inventoryTransactionTimeTypeTransferCache;

    public InventoryTransactionTimeType createInventoryTransactionTimeType(InventoryTransactionType inventoryTransactionType,
            String inventoryTransactionTimeTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultInventoryTransactionTimeType = getDefaultInventoryTransactionTimeType(inventoryTransactionType);
        var defaultFound = defaultInventoryTransactionTimeType != null;

        if(defaultFound && isDefault) {
            var defaultInventoryTransactionTimeTypeDetailValue = getDefaultInventoryTransactionTimeTypeDetailValueForUpdate(inventoryTransactionType);

            defaultInventoryTransactionTimeTypeDetailValue.setIsDefault(false);
            updateInventoryTransactionTimeTypeFromValue(defaultInventoryTransactionTimeTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var inventoryTransactionTimeType = inventoryTransactionTimeTypeFactory.create();
        var inventoryTransactionTimeTypeDetail = inventoryTransactionTimeTypeDetailFactory.create(inventoryTransactionTimeType,
                inventoryTransactionType, inventoryTransactionTimeTypeName, isDefault,
                sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        inventoryTransactionTimeType = inventoryTransactionTimeTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                inventoryTransactionTimeType.getPrimaryKey());
        inventoryTransactionTimeType.setActiveDetail(inventoryTransactionTimeTypeDetail);
        inventoryTransactionTimeType.setLastDetail(inventoryTransactionTimeTypeDetail);
        inventoryTransactionTimeType.store();

        sendEvent(inventoryTransactionTimeType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return inventoryTransactionTimeType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.InventoryTransactionTimeType */
    public InventoryTransactionTimeType getInventoryTransactionTimeTypeByEntityInstance(final EntityInstance entityInstance,
            final EntityPermission entityPermission) {
        var pk = new InventoryTransactionTimeTypePK(entityInstance.getEntityUniqueId());

        return inventoryTransactionTimeTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public InventoryTransactionTimeType getInventoryTransactionTimeTypeByEntityInstance(final EntityInstance entityInstance) {
        return getInventoryTransactionTimeTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public InventoryTransactionTimeType getInventoryTransactionTimeTypeByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getInventoryTransactionTimeTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public InventoryTransactionTimeType getInventoryTransactionTimeTypeByPK(InventoryTransactionTimeTypePK pk) {
        return inventoryTransactionTimeTypeFactory.getEntityFromPK(EntityPermission.READ_ONLY, pk);
    }

    public long countInventoryTransactionTimeTypesByInventoryTransactionType(InventoryTransactionType inventoryTransactionType) {
        return session.getDslContext()
                .selectCount()
                .from(InventoryTransactionTimeTypes)
                .join(InventoryTransactionTimeTypeDetails).onKey(INVENTORY_TRANSACTION_TIME_TYPES_ACTIVE_DETAIL_FK)
                .where(InventoryTransactionTimeTypeDetails.INVENTORY_TRANSACTION_TYPE.eq(inventoryTransactionType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public InventoryTransactionTimeType getInventoryTransactionTimeTypeByName(InventoryTransactionType inventoryTransactionType,
            String inventoryTransactionTimeTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryTransactionTimeTypes.fields())
                .from(InventoryTransactionTimeTypes)
                .join(InventoryTransactionTimeTypeDetails).onKey(INVENTORY_TRANSACTION_TIME_TYPES_ACTIVE_DETAIL_FK)
                .where(InventoryTransactionTimeTypeDetails.INVENTORY_TRANSACTION_TYPE.eq(inventoryTransactionType.getPrimaryKey()),
                        InventoryTransactionTimeTypeDetails.INVENTORY_TRANSACTION_TIME_TYPE_NAME.eq(inventoryTransactionTimeTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return inventoryTransactionTimeTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public InventoryTransactionTimeType getInventoryTransactionTimeTypeByName(InventoryTransactionType inventoryTransactionType,
            String inventoryTransactionTimeTypeName) {
        return getInventoryTransactionTimeTypeByName(inventoryTransactionType, inventoryTransactionTimeTypeName, EntityPermission.READ_ONLY);
    }

    public InventoryTransactionTimeType getInventoryTransactionTimeTypeByNameForUpdate(InventoryTransactionType inventoryTransactionType,
            String inventoryTransactionTimeTypeName) {
        return getInventoryTransactionTimeTypeByName(inventoryTransactionType, inventoryTransactionTimeTypeName, EntityPermission.READ_WRITE);
    }

    public InventoryTransactionTimeTypeDetailValue getInventoryTransactionTimeTypeDetailValueForUpdate(
            InventoryTransactionTimeType inventoryTransactionTimeType) {
        return inventoryTransactionTimeType == null? null: inventoryTransactionTimeType.getLastDetailForUpdate(
                ).getInventoryTransactionTimeTypeDetailValue().clone();
    }

    public InventoryTransactionTimeTypeDetailValue getInventoryTransactionTimeTypeDetailValueByNameForUpdate(
            InventoryTransactionType inventoryTransactionType, String inventoryTransactionTimeTypeName) {
        return getInventoryTransactionTimeTypeDetailValueForUpdate(getInventoryTransactionTimeTypeByNameForUpdate(inventoryTransactionType,
                inventoryTransactionTimeTypeName));
    }

    public InventoryTransactionTimeType getDefaultInventoryTransactionTimeType(InventoryTransactionType inventoryTransactionType,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryTransactionTimeTypes.fields())
                .from(InventoryTransactionTimeTypes)
                .join(InventoryTransactionTimeTypeDetails).onKey(INVENTORY_TRANSACTION_TIME_TYPES_ACTIVE_DETAIL_FK)
                .where(InventoryTransactionTimeTypeDetails.INVENTORY_TRANSACTION_TYPE.eq(inventoryTransactionType.getPrimaryKey()),
                        InventoryTransactionTimeTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return inventoryTransactionTimeTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public InventoryTransactionTimeType getDefaultInventoryTransactionTimeType(InventoryTransactionType inventoryTransactionType) {
        return getDefaultInventoryTransactionTimeType(inventoryTransactionType, EntityPermission.READ_ONLY);
    }

    public InventoryTransactionTimeType getDefaultInventoryTransactionTimeTypeForUpdate(InventoryTransactionType inventoryTransactionType) {
        return getDefaultInventoryTransactionTimeType(inventoryTransactionType, EntityPermission.READ_WRITE);
    }

    public InventoryTransactionTimeTypeDetailValue getDefaultInventoryTransactionTimeTypeDetailValueForUpdate(
            InventoryTransactionType inventoryTransactionType) {
        return getDefaultInventoryTransactionTimeTypeForUpdate(
                inventoryTransactionType).getLastDetailForUpdate().getInventoryTransactionTimeTypeDetailValue().clone();
    }

    private List<InventoryTransactionTimeType> getInventoryTransactionTimeTypes(InventoryTransactionType inventoryTransactionType,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryTransactionTimeTypes.fields())
                .from(InventoryTransactionTimeTypes)
                .join(InventoryTransactionTimeTypeDetails).onKey(INVENTORY_TRANSACTION_TIME_TYPES_ACTIVE_DETAIL_FK)
                .where(InventoryTransactionTimeTypeDetails.INVENTORY_TRANSACTION_TYPE.eq(inventoryTransactionType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(InventoryTransactionTimeTypeDetails.SORT_ORDER,
                            InventoryTransactionTimeTypeDetails.INVENTORY_TRANSACTION_TIME_TYPE_NAME),
                    InventoryTransactionTimeTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return inventoryTransactionTimeTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<InventoryTransactionTimeType> getInventoryTransactionTimeTypes(InventoryTransactionType inventoryTransactionType) {
        return getInventoryTransactionTimeTypes(inventoryTransactionType, EntityPermission.READ_ONLY);
    }

    public List<InventoryTransactionTimeType> getInventoryTransactionTimeTypesByInventoryTransactionType(
            InventoryTransactionType inventoryTransactionType) {
        return getInventoryTransactionTimeTypes(inventoryTransactionType);
    }

    public List<InventoryTransactionTimeType> getInventoryTransactionTimeTypesForUpdate(InventoryTransactionType inventoryTransactionType) {
        return getInventoryTransactionTimeTypes(inventoryTransactionType, EntityPermission.READ_WRITE);
    }

    public InventoryTransactionTimeTypeTransfer getInventoryTransactionTimeTypeTransfer(UserVisit userVisit,
            InventoryTransactionTimeType inventoryTransactionTimeType) {
        return inventoryTransactionTimeTypeTransferCache.getTransfer(userVisit, inventoryTransactionTimeType);
    }

    public List<InventoryTransactionTimeTypeTransfer> getInventoryTransactionTimeTypeTransfers(UserVisit userVisit,
            Collection<InventoryTransactionTimeType> inventoryTransactionTimeTypes) {
        List<InventoryTransactionTimeTypeTransfer> inventoryTransactionTimeTypeTransfers = new ArrayList<>(inventoryTransactionTimeTypes.size());

        inventoryTransactionTimeTypes.forEach((inventoryTransactionTimeType) ->
                inventoryTransactionTimeTypeTransfers.add(inventoryTransactionTimeTypeTransferCache.getTransfer(userVisit,
                        inventoryTransactionTimeType))
        );

        return inventoryTransactionTimeTypeTransfers;
    }

    public List<InventoryTransactionTimeTypeTransfer> getInventoryTransactionTimeTypeTransfers(UserVisit userVisit,
            InventoryTransactionType inventoryTransactionType) {
        return getInventoryTransactionTimeTypeTransfers(userVisit, getInventoryTransactionTimeTypes(inventoryTransactionType));
    }

    public InventoryTransactionTimeTypeChoicesBean getInventoryTransactionTimeTypeChoices(String defaultInventoryTransactionTimeTypeChoice,
            Language language, boolean allowNullChoice,
            InventoryTransactionType inventoryTransactionType) {
        var inventoryTransactionTimeTypes = getInventoryTransactionTimeTypes(inventoryTransactionType);
        var size = inventoryTransactionTimeTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultInventoryTransactionTimeTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var inventoryTransactionTimeType : inventoryTransactionTimeTypes) {
            var inventoryTransactionTimeTypeDetail = inventoryTransactionTimeType.getLastDetail();

            var label = getBestInventoryTransactionTimeTypeDescription(inventoryTransactionTimeType, language);
            var value = inventoryTransactionTimeTypeDetail.getInventoryTransactionTimeTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultInventoryTransactionTimeTypeChoice != null && defaultInventoryTransactionTimeTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && inventoryTransactionTimeTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new InventoryTransactionTimeTypeChoicesBean(labels, values, defaultValue);
    }

    private void updateInventoryTransactionTimeTypeFromValue(InventoryTransactionTimeTypeDetailValue inventoryTransactionTimeTypeDetailValue,
            boolean checkDefault, BasePK updatedBy) {
        if(inventoryTransactionTimeTypeDetailValue.hasBeenModified()) {
            var inventoryTransactionTimeType = inventoryTransactionTimeTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     inventoryTransactionTimeTypeDetailValue.getInventoryTransactionTimeTypePK());
            var inventoryTransactionTimeTypeDetail = inventoryTransactionTimeType.getActiveDetailForUpdate();

            inventoryTransactionTimeTypeDetail.setThruTime(session.getStartTime());
            inventoryTransactionTimeTypeDetail.store();

            var inventoryTransactionType = inventoryTransactionTimeTypeDetail.getInventoryTransactionType(); // Not updated
            var inventoryTransactionTypePK = inventoryTransactionType.getPrimaryKey(); // Not updated
            var inventoryTransactionTimeTypePK = inventoryTransactionTimeTypeDetail.getInventoryTransactionTimeTypePK(); // Not updated
            var inventoryTransactionTimeTypeName = inventoryTransactionTimeTypeDetailValue.getInventoryTransactionTimeTypeName();
            var isDefault = inventoryTransactionTimeTypeDetailValue.getIsDefault();
            var sortOrder = inventoryTransactionTimeTypeDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultInventoryTransactionTimeType = getDefaultInventoryTransactionTimeType(inventoryTransactionType);
                var defaultFound = 
                        defaultInventoryTransactionTimeType != null && !defaultInventoryTransactionTimeType.equals(inventoryTransactionTimeType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultInventoryTransactionTimeTypeDetailValue = 
                            getDefaultInventoryTransactionTimeTypeDetailValueForUpdate(inventoryTransactionType);

                    defaultInventoryTransactionTimeTypeDetailValue.setIsDefault(false);
                    updateInventoryTransactionTimeTypeFromValue(defaultInventoryTransactionTimeTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            inventoryTransactionTimeTypeDetail = inventoryTransactionTimeTypeDetailFactory.create(inventoryTransactionTimeTypePK,
                    inventoryTransactionTypePK, inventoryTransactionTimeTypeName, isDefault, sortOrder,
                    session.getStartTime(), Session.MAX_TIME);

            inventoryTransactionTimeType.setActiveDetail(inventoryTransactionTimeTypeDetail);
            inventoryTransactionTimeType.setLastDetail(inventoryTransactionTimeTypeDetail);

            sendEvent(inventoryTransactionTimeTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateInventoryTransactionTimeTypeFromValue(InventoryTransactionTimeTypeDetailValue inventoryTransactionTimeTypeDetailValue,
            BasePK updatedBy) {
        updateInventoryTransactionTimeTypeFromValue(inventoryTransactionTimeTypeDetailValue, true, updatedBy);
    }

    public void deleteInventoryTransactionTimeType(InventoryTransactionTimeType inventoryTransactionTimeType, BasePK deletedBy) {
        var inventoryTransactionTimeTypeDetail = inventoryTransactionTimeType.getLastDetailForUpdate();

        deleteInventoryTransactionTimeTypeDescriptionsByInventoryTransactionTimeType(inventoryTransactionTimeType, deletedBy);
        deleteInventoryTransactionTimesByInventoryTransactionTimeType(inventoryTransactionTimeType, deletedBy);

        inventoryTransactionTimeTypeDetail.setThruTime(session.getStartTime());
        inventoryTransactionTimeType.setActiveDetail(null);
        inventoryTransactionTimeType.store();

        // Check for default, and pick one if necessary
        var inventoryTransactionType = inventoryTransactionTimeTypeDetail.getInventoryTransactionType();
        var defaultInventoryTransactionTimeType = getDefaultInventoryTransactionTimeType(inventoryTransactionType);
        if(defaultInventoryTransactionTimeType == null) {
            var inventoryTransactionTimeTypes = getInventoryTransactionTimeTypesForUpdate(inventoryTransactionType);

            if(!inventoryTransactionTimeTypes.isEmpty()) {
                var iter = inventoryTransactionTimeTypes.iterator();
                if(iter.hasNext()) {
                    defaultInventoryTransactionTimeType = iter.next();
                }
                var inventoryTransactionTimeTypeDetailValue = 
                        Objects.requireNonNull(
                                defaultInventoryTransactionTimeType).getLastDetailForUpdate().getInventoryTransactionTimeTypeDetailValue().clone();

                inventoryTransactionTimeTypeDetailValue.setIsDefault(true);
                updateInventoryTransactionTimeTypeFromValue(inventoryTransactionTimeTypeDetailValue, false, deletedBy);
            }
        }

        sendEvent(inventoryTransactionTimeType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteInventoryTransactionTimeTypesByInventoryTransactionType(InventoryTransactionType inventoryTransactionType, BasePK deletedBy) {
        var inventoryTransactionTimeTypes = getInventoryTransactionTimeTypesForUpdate(inventoryTransactionType);

        inventoryTransactionTimeTypes.forEach(inventoryTransactionTimeType ->
                deleteInventoryTransactionTimeType(inventoryTransactionTimeType, deletedBy));
    }

    // --------------------------------------------------------------------------------
    //   Inventory Transaction Time Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    InventoryTransactionTimeTypeDescriptionFactory inventoryTransactionTimeTypeDescriptionFactory;

    @Inject
    InventoryTransactionTimeTypeDescriptionTransferCache inventoryTransactionTimeTypeDescriptionTransferCache;

    public InventoryTransactionTimeTypeDescription createInventoryTransactionTimeTypeDescription(
            InventoryTransactionTimeType inventoryTransactionTimeType, Language language, String description, BasePK createdBy) {
        var inventoryTransactionTimeTypeDescription = inventoryTransactionTimeTypeDescriptionFactory.create(inventoryTransactionTimeType,
                language, description, session.getStartTime(), Session.MAX_TIME);

        sendEvent(inventoryTransactionTimeType.getPrimaryKey(), EventTypes.MODIFY, inventoryTransactionTimeTypeDescription.getPrimaryKey(),
                EventTypes.CREATE, createdBy);

        return inventoryTransactionTimeTypeDescription;
    }

    public long countInventoryTransactionTimeTypeDescriptionsByInventoryTransactionTimeType(
            InventoryTransactionTimeType inventoryTransactionTimeType) {
        return session.getDslContext()
                .selectCount()
                .from(InventoryTransactionTimeTypeDescriptions)
                .where(InventoryTransactionTimeTypeDescriptions.INVENTORY_TRANSACTION_TIME_TYPE.eq(inventoryTransactionTimeType.getPrimaryKey()),
                        InventoryTransactionTimeTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countInventoryTransactionTimeTypeDescriptionsByLanguage(Language language) {
        return session.getDslContext()
                .selectCount()
                .from(InventoryTransactionTimeTypeDescriptions)
                .where(InventoryTransactionTimeTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        InventoryTransactionTimeTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private InventoryTransactionTimeTypeDescription getInventoryTransactionTimeTypeDescription(
            InventoryTransactionTimeType inventoryTransactionTimeType, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryTransactionTimeTypeDescriptions.fields())
                .from(InventoryTransactionTimeTypeDescriptions)
                .where(InventoryTransactionTimeTypeDescriptions.INVENTORY_TRANSACTION_TIME_TYPE.eq(inventoryTransactionTimeType.getPrimaryKey()),
                        InventoryTransactionTimeTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        InventoryTransactionTimeTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return inventoryTransactionTimeTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }

    public InventoryTransactionTimeTypeDescription getInventoryTransactionTimeTypeDescription(
            InventoryTransactionTimeType inventoryTransactionTimeType, Language language) {
        return getInventoryTransactionTimeTypeDescription(inventoryTransactionTimeType, language, EntityPermission.READ_ONLY);
    }

    public InventoryTransactionTimeTypeDescription getInventoryTransactionTimeTypeDescriptionForUpdate(
            InventoryTransactionTimeType inventoryTransactionTimeType, Language language) {
        return getInventoryTransactionTimeTypeDescription(inventoryTransactionTimeType, language, EntityPermission.READ_WRITE);
    }

    public InventoryTransactionTimeTypeDescriptionValue getInventoryTransactionTimeTypeDescriptionValue(
            InventoryTransactionTimeTypeDescription inventoryTransactionTimeTypeDescription) {
        return inventoryTransactionTimeTypeDescription == null ? null
                : inventoryTransactionTimeTypeDescription.getInventoryTransactionTimeTypeDescriptionValue().clone();
    }

    public InventoryTransactionTimeTypeDescriptionValue getInventoryTransactionTimeTypeDescriptionValueForUpdate(
            InventoryTransactionTimeType inventoryTransactionTimeType, Language language) {
        return getInventoryTransactionTimeTypeDescriptionValue(getInventoryTransactionTimeTypeDescriptionForUpdate(inventoryTransactionTimeType,
                language));
    }

    private List<InventoryTransactionTimeTypeDescription> getInventoryTransactionTimeTypeDescriptionsByInventoryTransactionTimeType(
            InventoryTransactionTimeType inventoryTransactionTimeType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(InventoryTransactionTimeTypeDescriptions.fields())
                    .from(InventoryTransactionTimeTypeDescriptions)
                    .join(Languages).on(InventoryTransactionTimeTypeDescriptions.LANGUAGE.eq(Languages.LANGUAGE))
                    .where(InventoryTransactionTimeTypeDescriptions.INVENTORY_TRANSACTION_TIME_TYPE.eq(inventoryTransactionTimeType.getPrimaryKey()),
                            InventoryTransactionTimeTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    InventoryTransactionTimeTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(InventoryTransactionTimeTypeDescriptions.fields())
                    .from(InventoryTransactionTimeTypeDescriptions)
                    .where(InventoryTransactionTimeTypeDescriptions.INVENTORY_TRANSACTION_TIME_TYPE.eq(inventoryTransactionTimeType.getPrimaryKey()),
                            InventoryTransactionTimeTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return inventoryTransactionTimeTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<InventoryTransactionTimeTypeDescription> getInventoryTransactionTimeTypeDescriptionsByInventoryTransactionTimeType(
            InventoryTransactionTimeType inventoryTransactionTimeType) {
        return getInventoryTransactionTimeTypeDescriptionsByInventoryTransactionTimeType(inventoryTransactionTimeType, EntityPermission.READ_ONLY);
    }

    public List<InventoryTransactionTimeTypeDescription> getInventoryTransactionTimeTypeDescriptionsByInventoryTransactionTimeTypeForUpdate(
            InventoryTransactionTimeType inventoryTransactionTimeType) {
        return getInventoryTransactionTimeTypeDescriptionsByInventoryTransactionTimeType(inventoryTransactionTimeType, EntityPermission.READ_WRITE);
    }

    public String getBestInventoryTransactionTimeTypeDescription(InventoryTransactionTimeType inventoryTransactionTimeType, Language language) {
        String description;
        var inventoryTransactionTimeTypeDescription = getInventoryTransactionTimeTypeDescription(inventoryTransactionTimeType, language);

        if(inventoryTransactionTimeTypeDescription == null && !language.getIsDefault()) {
            inventoryTransactionTimeTypeDescription = getInventoryTransactionTimeTypeDescription(inventoryTransactionTimeType,
                    partyControl.getDefaultLanguage());
        }

        if(inventoryTransactionTimeTypeDescription == null) {
            description = inventoryTransactionTimeType.getLastDetail().getInventoryTransactionTimeTypeName();
        } else {
            description = inventoryTransactionTimeTypeDescription.getDescription();
        }

        return description;
    }

    public InventoryTransactionTimeTypeDescriptionTransfer getInventoryTransactionTimeTypeDescriptionTransfer(UserVisit userVisit,
            InventoryTransactionTimeTypeDescription inventoryTransactionTimeTypeDescription) {
        return inventoryTransactionTimeTypeDescriptionTransferCache.getTransfer(userVisit, inventoryTransactionTimeTypeDescription);
    }

    public List<InventoryTransactionTimeTypeDescriptionTransfer> getInventoryTransactionTimeTypeDescriptionTransfers(
            UserVisit userVisit, Collection<InventoryTransactionTimeTypeDescription> inventoryTransactionTimeTypeDescriptions) {
        var transfers = new ArrayList<InventoryTransactionTimeTypeDescriptionTransfer>(inventoryTransactionTimeTypeDescriptions.size());

        inventoryTransactionTimeTypeDescriptions.forEach(inventoryTransactionTimeTypeDescription ->
                transfers.add(inventoryTransactionTimeTypeDescriptionTransferCache.getTransfer(userVisit,
                        inventoryTransactionTimeTypeDescription)));

        return transfers;
    }

    public List<InventoryTransactionTimeTypeDescriptionTransfer> getInventoryTransactionTimeTypeDescriptionTransfersByInventoryTransactionTimeType(
            UserVisit userVisit, InventoryTransactionTimeType inventoryTransactionTimeType) {
        return getInventoryTransactionTimeTypeDescriptionTransfers(userVisit,
                getInventoryTransactionTimeTypeDescriptionsByInventoryTransactionTimeType(inventoryTransactionTimeType));
    }

    public void updateInventoryTransactionTimeTypeDescriptionFromValue(
            InventoryTransactionTimeTypeDescriptionValue inventoryTransactionTimeTypeDescriptionValue, BasePK updatedBy) {
        if(inventoryTransactionTimeTypeDescriptionValue.hasBeenModified()) {
            var inventoryTransactionTimeTypeDescription = inventoryTransactionTimeTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    inventoryTransactionTimeTypeDescriptionValue.getPrimaryKey());

            inventoryTransactionTimeTypeDescription.setThruTime(session.getStartTime());
            inventoryTransactionTimeTypeDescription.store();

            var inventoryTransactionTimeType = inventoryTransactionTimeTypeDescription.getInventoryTransactionTimeType();
            var language = inventoryTransactionTimeTypeDescription.getLanguage();
            var description = inventoryTransactionTimeTypeDescriptionValue.getDescription();

            inventoryTransactionTimeTypeDescription = inventoryTransactionTimeTypeDescriptionFactory.create(inventoryTransactionTimeType,
                    language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(inventoryTransactionTimeType.getPrimaryKey(), EventTypes.MODIFY, inventoryTransactionTimeTypeDescription.getPrimaryKey(),
                    EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteInventoryTransactionTimeTypeDescription(InventoryTransactionTimeTypeDescription inventoryTransactionTimeTypeDescription,
            BasePK deletedBy) {
        inventoryTransactionTimeTypeDescription.setThruTime(session.getStartTime());

        sendEvent(inventoryTransactionTimeTypeDescription.getInventoryTransactionTimeTypePK(), EventTypes.MODIFY,
                inventoryTransactionTimeTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteInventoryTransactionTimeTypeDescriptionsByInventoryTransactionTimeType(
            InventoryTransactionTimeType inventoryTransactionTimeType, BasePK deletedBy) {
        var inventoryTransactionTimeTypeDescriptions = 
                getInventoryTransactionTimeTypeDescriptionsByInventoryTransactionTimeTypeForUpdate(inventoryTransactionTimeType);

        inventoryTransactionTimeTypeDescriptions.forEach((inventoryTransactionTimeTypeDescription) -> 
                deleteInventoryTransactionTimeTypeDescription(inventoryTransactionTimeTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Inventory Transaction Times
    // --------------------------------------------------------------------------------

    @Inject
    protected InventoryTransactionTimeFactory inventoryTransactionTimeFactory;

    public InventoryTransactionTime createInventoryTransactionTime(InventoryTransaction inventoryTransaction,
            InventoryTransactionTimeType inventoryTransactionTimeType, Long time, BasePK createdBy) {
        var inventoryTransactionTime = inventoryTransactionTimeFactory.create(inventoryTransaction, inventoryTransactionTimeType, time,
                session.getStartTime(), Session.MAX_TIME);

        sendEvent(inventoryTransaction.getPrimaryKey(), EventTypes.MODIFY, inventoryTransactionTime.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return inventoryTransactionTime;
    }

    private long countInventoryTransactionTimes(Condition condition) {
        return session.getDslContext()
                .selectCount()
                .from(InventoryTransactionTimes)
                .where(condition, InventoryTransactionTimes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countInventoryTransactionTimesByInventoryTransaction(InventoryTransaction inventoryTransaction) {
        return countInventoryTransactionTimes(InventoryTransactionTimes.INVENTORY_TRANSACTION.eq(inventoryTransaction.getPrimaryKey()));
    }

    public long countInventoryTransactionTimesByInventoryTransactionTimeType(InventoryTransactionTimeType inventoryTransactionTimeType) {
        return countInventoryTransactionTimes(InventoryTransactionTimes.INVENTORY_TRANSACTION_TIME_TYPE.eq(inventoryTransactionTimeType.getPrimaryKey()));
    }

    public boolean inventoryTransactionTimeExists(InventoryTransaction inventoryTransaction, InventoryTransactionTimeType inventoryTransactionTimeType) {
        return countInventoryTransactionTimes(InventoryTransactionTimes.INVENTORY_TRANSACTION.eq(inventoryTransaction.getPrimaryKey())
                .and(InventoryTransactionTimes.INVENTORY_TRANSACTION_TIME_TYPE.eq(inventoryTransactionTimeType.getPrimaryKey()))) != 0;
    }

    public InventoryTransactionTime getInventoryTransactionTime(InventoryTransaction inventoryTransaction,
            InventoryTransactionTimeType inventoryTransactionTimeType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryTransactionTimes.fields())
                .from(InventoryTransactionTimes)
                .where(InventoryTransactionTimes.INVENTORY_TRANSACTION.eq(inventoryTransaction.getPrimaryKey()),
                        InventoryTransactionTimes.INVENTORY_TRANSACTION_TIME_TYPE.eq(inventoryTransactionTimeType.getPrimaryKey()),
                        InventoryTransactionTimes.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return inventoryTransactionTimeFactory.getEntityFromQuery(entityPermission, query);
    }

    public InventoryTransactionTime getInventoryTransactionTime(InventoryTransaction inventoryTransaction,
            InventoryTransactionTimeType inventoryTransactionTimeType) {
        return getInventoryTransactionTime(inventoryTransaction, inventoryTransactionTimeType, EntityPermission.READ_ONLY);
    }

    public InventoryTransactionTime getInventoryTransactionTimeForUpdate(InventoryTransaction inventoryTransaction,
            InventoryTransactionTimeType inventoryTransactionTimeType) {
        return getInventoryTransactionTime(inventoryTransaction, inventoryTransactionTimeType, EntityPermission.READ_WRITE);
    }

    public InventoryTransactionTimeValue getInventoryTransactionTimeValue(InventoryTransactionTime inventoryTransactionTime) {
        return inventoryTransactionTime == null ? null : inventoryTransactionTime.getInventoryTransactionTimeValue().clone();
    }

    public InventoryTransactionTimeValue getInventoryTransactionTimeValueForUpdate(InventoryTransaction inventoryTransaction,
            InventoryTransactionTimeType inventoryTransactionTimeType) {
        return getInventoryTransactionTimeValue(getInventoryTransactionTimeForUpdate(inventoryTransaction, inventoryTransactionTimeType));
    }

    private List<InventoryTransactionTime> getInventoryTransactionTimesByInventoryTransaction(InventoryTransaction inventoryTransaction,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(InventoryTransactionTimes.fields())
                    .from(InventoryTransactionTimes)
                    .join(InventoryTransactionTimeTypes).on(InventoryTransactionTimes.INVENTORY_TRANSACTION_TIME_TYPE.eq(InventoryTransactionTimeTypes.INVENTORY_TRANSACTION_TIME_TYPE))
                    .join(InventoryTransactionTimeTypeDetails).onKey(INVENTORY_TRANSACTION_TIME_TYPES_LAST_DETAIL_FK)
                    .where(InventoryTransactionTimes.INVENTORY_TRANSACTION.eq(inventoryTransaction.getPrimaryKey()),
                            InventoryTransactionTimes.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(InventoryTransactionTimeTypeDetails.SORT_ORDER, InventoryTransactionTimeTypeDetails.INVENTORY_TRANSACTION_TIME_TYPE_NAME),
                    InventoryTransactionTimeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(InventoryTransactionTimes.fields())
                    .from(InventoryTransactionTimes)
                    .where(InventoryTransactionTimes.INVENTORY_TRANSACTION.eq(inventoryTransaction.getPrimaryKey()),
                            InventoryTransactionTimes.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return inventoryTransactionTimeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<InventoryTransactionTime> getInventoryTransactionTimesByInventoryTransaction(InventoryTransaction inventoryTransaction) {
        return getInventoryTransactionTimesByInventoryTransaction(inventoryTransaction, EntityPermission.READ_ONLY);
    }

    public List<InventoryTransactionTime> getInventoryTransactionTimesByInventoryTransactionForUpdate(InventoryTransaction inventoryTransaction) {
        return getInventoryTransactionTimesByInventoryTransaction(inventoryTransaction, EntityPermission.READ_WRITE);
    }

    private List<InventoryTransactionTime> getInventoryTransactionTimesByInventoryTransactionTimeType(InventoryTransactionTimeType inventoryTransactionTimeType,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(InventoryTransactionTimes.fields())
                    .from(InventoryTransactionTimes)
                    .join(InventoryTransactions).on(InventoryTransactionTimes.INVENTORY_TRANSACTION.eq(InventoryTransactions.INVENTORY_TRANSACTION))
                    .join(InventoryTransactionDetails).onKey(INVENTORY_TRANSACTIONS_LAST_DETAIL_FK)
                    .where(InventoryTransactionTimes.INVENTORY_TRANSACTION_TIME_TYPE.eq(inventoryTransactionTimeType.getPrimaryKey()),
                            InventoryTransactionTimes.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(InventoryTransactionDetails.INVENTORY_TRANSACTION_NAME),
                    InventoryTransactionTimeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(InventoryTransactionTimes.fields())
                    .from(InventoryTransactionTimes)
                    .where(InventoryTransactionTimes.INVENTORY_TRANSACTION_TIME_TYPE.eq(inventoryTransactionTimeType.getPrimaryKey()),
                            InventoryTransactionTimes.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return inventoryTransactionTimeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<InventoryTransactionTime> getInventoryTransactionTimesByInventoryTransactionTimeType(InventoryTransactionTimeType inventoryTransactionTimeType) {
        return getInventoryTransactionTimesByInventoryTransactionTimeType(inventoryTransactionTimeType, EntityPermission.READ_ONLY);
    }

    public List<InventoryTransactionTime> getInventoryTransactionTimesByInventoryTransactionTimeTypeForUpdate(InventoryTransactionTimeType inventoryTransactionTimeType) {
        return getInventoryTransactionTimesByInventoryTransactionTimeType(inventoryTransactionTimeType, EntityPermission.READ_WRITE);
    }

    public void updateInventoryTransactionTimeFromValue(InventoryTransactionTimeValue inventoryTransactionTimeValue, BasePK updatedBy) {
        if(inventoryTransactionTimeValue.hasBeenModified()) {
            var inventoryTransactionTime = inventoryTransactionTimeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    inventoryTransactionTimeValue.getPrimaryKey());

            inventoryTransactionTime.setThruTime(session.getStartTime());
            inventoryTransactionTime.store();

            var inventoryTransactionPK = inventoryTransactionTime.getInventoryTransactionPK(); // Not updated
            var inventoryTransactionTimeTypePK = inventoryTransactionTime.getInventoryTransactionTimeTypePK(); // Not updated
            var time = inventoryTransactionTimeValue.getTime();

            inventoryTransactionTime = inventoryTransactionTimeFactory.create(inventoryTransactionPK, inventoryTransactionTimeTypePK, time,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(inventoryTransactionPK, EventTypes.MODIFY, inventoryTransactionTime.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteInventoryTransactionTime(InventoryTransactionTime inventoryTransactionTime, BasePK deletedBy) {
        inventoryTransactionTime.setThruTime(session.getStartTime());

        sendEvent(inventoryTransactionTime.getInventoryTransactionPK(), EventTypes.MODIFY, inventoryTransactionTime.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteInventoryTransactionTimes(List<InventoryTransactionTime> inventoryTransactionTimes, BasePK deletedBy) {
        inventoryTransactionTimes.forEach(inventoryTransactionTime -> deleteInventoryTransactionTime(inventoryTransactionTime, deletedBy));
    }

    public void deleteInventoryTransactionTimesByInventoryTransaction(InventoryTransaction inventoryTransaction, BasePK deletedBy) {
        deleteInventoryTransactionTimes(getInventoryTransactionTimesByInventoryTransactionForUpdate(inventoryTransaction), deletedBy);
    }

    public void deleteInventoryTransactionTimesByInventoryTransactionTimeType(InventoryTransactionTimeType inventoryTransactionTimeType,
            BasePK deletedBy) {
        deleteInventoryTransactionTimes(getInventoryTransactionTimesByInventoryTransactionTimeTypeForUpdate(inventoryTransactionTimeType), deletedBy);
    }

}
