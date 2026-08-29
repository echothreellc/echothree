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
import com.echothree.model.control.inventory.common.choice.InventoryTransactionRoleTypeChoicesBean;
import com.echothree.model.control.inventory.common.transfer.InventoryTransactionRoleTypeDescriptionTransfer;
import com.echothree.model.control.inventory.common.transfer.InventoryTransactionRoleTypeTransfer;
import com.echothree.model.control.inventory.server.transfer.InventoryTransactionRoleTypeDescriptionTransferCache;
import com.echothree.model.control.inventory.server.transfer.InventoryTransactionRoleTypeTransferCache;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.inventory.common.pk.InventoryTransactionRoleTypePK;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionRoleType;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionRoleTypeDescription;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionType;
import com.echothree.model.data.inventory.server.factory.InventoryTransactionRoleTypeDescriptionFactory;
import com.echothree.model.data.inventory.server.factory.InventoryTransactionRoleTypeDetailFactory;
import com.echothree.model.data.inventory.server.factory.InventoryTransactionRoleTypeFactory;
import com.echothree.model.data.inventory.server.value.InventoryTransactionRoleTypeDescriptionValue;
import com.echothree.model.data.inventory.server.value.InventoryTransactionRoleTypeDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.inventory.InventoryForeignKeys.INVENTORY_TRANSACTION_ROLE_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.tables.inventory.InventoryTransactionRoleTypeDescriptions.InventoryTransactionRoleTypeDescriptions;
import static com.echothree.model.jooq.server.tables.inventory.InventoryTransactionRoleTypeDetails.InventoryTransactionRoleTypeDetails;
import static com.echothree.model.jooq.server.tables.inventory.InventoryTransactionRoleTypes.InventoryTransactionRoleTypes;
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
public class InventoryTransactionRoleControl
        extends BaseModelControl {

    /** Creates a new instance of InventoryTransactionRoleControl */
    protected InventoryTransactionRoleControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Inventory Transaction Role Types
    // --------------------------------------------------------------------------------

    @Inject
    InventoryTransactionRoleTypeFactory inventoryTransactionRoleTypeFactory;

    @Inject
    InventoryTransactionRoleTypeDetailFactory inventoryTransactionRoleTypeDetailFactory;

    @Inject
    InventoryTransactionRoleTypeTransferCache inventoryTransactionRoleTypeTransferCache;

    public InventoryTransactionRoleType createInventoryTransactionRoleType(InventoryTransactionType inventoryTransactionType,
            String inventoryTransactionRoleTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultInventoryTransactionRoleType = getDefaultInventoryTransactionRoleType(inventoryTransactionType);
        var defaultFound = defaultInventoryTransactionRoleType != null;

        if(defaultFound && isDefault) {
            var defaultInventoryTransactionRoleTypeDetailValue = getDefaultInventoryTransactionRoleTypeDetailValueForUpdate(inventoryTransactionType);

            defaultInventoryTransactionRoleTypeDetailValue.setIsDefault(false);
            updateInventoryTransactionRoleTypeFromValue(defaultInventoryTransactionRoleTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var inventoryTransactionRoleType = inventoryTransactionRoleTypeFactory.create();
        var inventoryTransactionRoleTypeDetail = inventoryTransactionRoleTypeDetailFactory.create(inventoryTransactionRoleType,
                inventoryTransactionType, inventoryTransactionRoleTypeName, isDefault,
                sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        inventoryTransactionRoleType = inventoryTransactionRoleTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                inventoryTransactionRoleType.getPrimaryKey());
        inventoryTransactionRoleType.setActiveDetail(inventoryTransactionRoleTypeDetail);
        inventoryTransactionRoleType.setLastDetail(inventoryTransactionRoleTypeDetail);
        inventoryTransactionRoleType.store();

        sendEvent(inventoryTransactionRoleType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return inventoryTransactionRoleType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.InventoryTransactionRoleType */
    public InventoryTransactionRoleType getInventoryTransactionRoleTypeByEntityInstance(final EntityInstance entityInstance,
            final EntityPermission entityPermission) {
        var pk = new InventoryTransactionRoleTypePK(entityInstance.getEntityUniqueId());

        return inventoryTransactionRoleTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public InventoryTransactionRoleType getInventoryTransactionRoleTypeByEntityInstance(final EntityInstance entityInstance) {
        return getInventoryTransactionRoleTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public InventoryTransactionRoleType getInventoryTransactionRoleTypeByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getInventoryTransactionRoleTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public InventoryTransactionRoleType getInventoryTransactionRoleTypeByPK(InventoryTransactionRoleTypePK pk) {
        return inventoryTransactionRoleTypeFactory.getEntityFromPK(EntityPermission.READ_ONLY, pk);
    }

    public long countInventoryTransactionRoleTypesByInventoryTransactionType(InventoryTransactionType inventoryTransactionType) {
        return session.getDslContext()
                .selectCount()
                .from(InventoryTransactionRoleTypes)
                .join(InventoryTransactionRoleTypeDetails).onKey(INVENTORY_TRANSACTION_ROLE_TYPES_ACTIVE_DETAIL_FK)
                .where(InventoryTransactionRoleTypeDetails.INVENTORY_TRANSACTION_TYPE.eq(inventoryTransactionType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public InventoryTransactionRoleType getInventoryTransactionRoleTypeByName(InventoryTransactionType inventoryTransactionType,
            String inventoryTransactionRoleTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryTransactionRoleTypes.fields())
                .from(InventoryTransactionRoleTypes)
                .join(InventoryTransactionRoleTypeDetails).onKey(INVENTORY_TRANSACTION_ROLE_TYPES_ACTIVE_DETAIL_FK)
                .where(InventoryTransactionRoleTypeDetails.INVENTORY_TRANSACTION_TYPE.eq(inventoryTransactionType.getPrimaryKey()),
                        InventoryTransactionRoleTypeDetails.INVENTORY_TRANSACTION_ROLE_TYPE_NAME.eq(inventoryTransactionRoleTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return inventoryTransactionRoleTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public InventoryTransactionRoleType getInventoryTransactionRoleTypeByName(InventoryTransactionType inventoryTransactionType,
            String inventoryTransactionRoleTypeName) {
        return getInventoryTransactionRoleTypeByName(inventoryTransactionType, inventoryTransactionRoleTypeName, EntityPermission.READ_ONLY);
    }

    public InventoryTransactionRoleType getInventoryTransactionRoleTypeByNameForUpdate(InventoryTransactionType inventoryTransactionType,
            String inventoryTransactionRoleTypeName) {
        return getInventoryTransactionRoleTypeByName(inventoryTransactionType, inventoryTransactionRoleTypeName, EntityPermission.READ_WRITE);
    }

    public InventoryTransactionRoleTypeDetailValue getInventoryTransactionRoleTypeDetailValueForUpdate(
            InventoryTransactionRoleType inventoryTransactionRoleType) {
        return inventoryTransactionRoleType == null? null: inventoryTransactionRoleType.getLastDetailForUpdate(
                ).getInventoryTransactionRoleTypeDetailValue().clone();
    }

    public InventoryTransactionRoleTypeDetailValue getInventoryTransactionRoleTypeDetailValueByNameForUpdate(
            InventoryTransactionType inventoryTransactionType, String inventoryTransactionRoleTypeName) {
        return getInventoryTransactionRoleTypeDetailValueForUpdate(getInventoryTransactionRoleTypeByNameForUpdate(inventoryTransactionType,
                inventoryTransactionRoleTypeName));
    }

    public InventoryTransactionRoleType getDefaultInventoryTransactionRoleType(InventoryTransactionType inventoryTransactionType,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryTransactionRoleTypes.fields())
                .from(InventoryTransactionRoleTypes)
                .join(InventoryTransactionRoleTypeDetails).onKey(INVENTORY_TRANSACTION_ROLE_TYPES_ACTIVE_DETAIL_FK)
                .where(InventoryTransactionRoleTypeDetails.INVENTORY_TRANSACTION_TYPE.eq(inventoryTransactionType.getPrimaryKey()),
                        InventoryTransactionRoleTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return inventoryTransactionRoleTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public InventoryTransactionRoleType getDefaultInventoryTransactionRoleType(InventoryTransactionType inventoryTransactionType) {
        return getDefaultInventoryTransactionRoleType(inventoryTransactionType, EntityPermission.READ_ONLY);
    }

    public InventoryTransactionRoleType getDefaultInventoryTransactionRoleTypeForUpdate(InventoryTransactionType inventoryTransactionType) {
        return getDefaultInventoryTransactionRoleType(inventoryTransactionType, EntityPermission.READ_WRITE);
    }

    public InventoryTransactionRoleTypeDetailValue getDefaultInventoryTransactionRoleTypeDetailValueForUpdate(
            InventoryTransactionType inventoryTransactionType) {
        return getDefaultInventoryTransactionRoleTypeForUpdate(
                inventoryTransactionType).getLastDetailForUpdate().getInventoryTransactionRoleTypeDetailValue().clone();
    }

    private List<InventoryTransactionRoleType> getInventoryTransactionRoleTypes(InventoryTransactionType inventoryTransactionType,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryTransactionRoleTypes.fields())
                .from(InventoryTransactionRoleTypes)
                .join(InventoryTransactionRoleTypeDetails).onKey(INVENTORY_TRANSACTION_ROLE_TYPES_ACTIVE_DETAIL_FK)
                .where(InventoryTransactionRoleTypeDetails.INVENTORY_TRANSACTION_TYPE.eq(inventoryTransactionType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(InventoryTransactionRoleTypeDetails.SORT_ORDER,
                            InventoryTransactionRoleTypeDetails.INVENTORY_TRANSACTION_ROLE_TYPE_NAME),
                    InventoryTransactionRoleTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return inventoryTransactionRoleTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<InventoryTransactionRoleType> getInventoryTransactionRoleTypes(InventoryTransactionType inventoryTransactionType) {
        return getInventoryTransactionRoleTypes(inventoryTransactionType, EntityPermission.READ_ONLY);
    }

    public List<InventoryTransactionRoleType> getInventoryTransactionRoleTypesByInventoryTransactionType(
            InventoryTransactionType inventoryTransactionType) {
        return getInventoryTransactionRoleTypes(inventoryTransactionType);
    }

    public List<InventoryTransactionRoleType> getInventoryTransactionRoleTypesForUpdate(InventoryTransactionType inventoryTransactionType) {
        return getInventoryTransactionRoleTypes(inventoryTransactionType, EntityPermission.READ_WRITE);
    }

    public InventoryTransactionRoleTypeTransfer getInventoryTransactionRoleTypeTransfer(UserVisit userVisit,
            InventoryTransactionRoleType inventoryTransactionRoleType) {
        return inventoryTransactionRoleTypeTransferCache.getTransfer(userVisit, inventoryTransactionRoleType);
    }

    public List<InventoryTransactionRoleTypeTransfer> getInventoryTransactionRoleTypeTransfers(UserVisit userVisit,
            Collection<InventoryTransactionRoleType> inventoryTransactionRoleTypes) {
        List<InventoryTransactionRoleTypeTransfer> inventoryTransactionRoleTypeTransfers = new ArrayList<>(inventoryTransactionRoleTypes.size());

        inventoryTransactionRoleTypes.forEach((inventoryTransactionRoleType) ->
                inventoryTransactionRoleTypeTransfers.add(inventoryTransactionRoleTypeTransferCache.getTransfer(userVisit,
                        inventoryTransactionRoleType))
        );

        return inventoryTransactionRoleTypeTransfers;
    }

    public List<InventoryTransactionRoleTypeTransfer> getInventoryTransactionRoleTypeTransfers(UserVisit userVisit,
            InventoryTransactionType inventoryTransactionType) {
        return getInventoryTransactionRoleTypeTransfers(userVisit, getInventoryTransactionRoleTypes(inventoryTransactionType));
    }

    public InventoryTransactionRoleTypeChoicesBean getInventoryTransactionRoleTypeChoices(String defaultInventoryTransactionRoleTypeChoice,
            Language language, boolean allowNullChoice,
            InventoryTransactionType inventoryTransactionType) {
        var inventoryTransactionRoleTypes = getInventoryTransactionRoleTypes(inventoryTransactionType);
        var size = inventoryTransactionRoleTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultInventoryTransactionRoleTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var inventoryTransactionRoleType : inventoryTransactionRoleTypes) {
            var inventoryTransactionRoleTypeDetail = inventoryTransactionRoleType.getLastDetail();

            var label = getBestInventoryTransactionRoleTypeDescription(inventoryTransactionRoleType, language);
            var value = inventoryTransactionRoleTypeDetail.getInventoryTransactionRoleTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultInventoryTransactionRoleTypeChoice != null && defaultInventoryTransactionRoleTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && inventoryTransactionRoleTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new InventoryTransactionRoleTypeChoicesBean(labels, values, defaultValue);
    }

    private void updateInventoryTransactionRoleTypeFromValue(InventoryTransactionRoleTypeDetailValue inventoryTransactionRoleTypeDetailValue,
            boolean checkDefault, BasePK updatedBy) {
        if(inventoryTransactionRoleTypeDetailValue.hasBeenModified()) {
            var inventoryTransactionRoleType = inventoryTransactionRoleTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     inventoryTransactionRoleTypeDetailValue.getInventoryTransactionRoleTypePK());
            var inventoryTransactionRoleTypeDetail = inventoryTransactionRoleType.getActiveDetailForUpdate();

            inventoryTransactionRoleTypeDetail.setThruTime(session.getStartTime());
            inventoryTransactionRoleTypeDetail.store();

            var inventoryTransactionType = inventoryTransactionRoleTypeDetail.getInventoryTransactionType(); // Not updated
            var inventoryTransactionTypePK = inventoryTransactionType.getPrimaryKey(); // Not updated
            var inventoryTransactionRoleTypePK = inventoryTransactionRoleTypeDetail.getInventoryTransactionRoleTypePK(); // Not updated
            var inventoryTransactionRoleTypeName = inventoryTransactionRoleTypeDetailValue.getInventoryTransactionRoleTypeName();
            var isDefault = inventoryTransactionRoleTypeDetailValue.getIsDefault();
            var sortOrder = inventoryTransactionRoleTypeDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultInventoryTransactionRoleType = getDefaultInventoryTransactionRoleType(inventoryTransactionType);
                var defaultFound = 
                        defaultInventoryTransactionRoleType != null && !defaultInventoryTransactionRoleType.equals(inventoryTransactionRoleType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultInventoryTransactionRoleTypeDetailValue = 
                            getDefaultInventoryTransactionRoleTypeDetailValueForUpdate(inventoryTransactionType);

                    defaultInventoryTransactionRoleTypeDetailValue.setIsDefault(false);
                    updateInventoryTransactionRoleTypeFromValue(defaultInventoryTransactionRoleTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            inventoryTransactionRoleTypeDetail = inventoryTransactionRoleTypeDetailFactory.create(inventoryTransactionRoleTypePK,
                    inventoryTransactionTypePK, inventoryTransactionRoleTypeName, isDefault, sortOrder,
                    session.getStartTime(), Session.MAX_TIME);

            inventoryTransactionRoleType.setActiveDetail(inventoryTransactionRoleTypeDetail);
            inventoryTransactionRoleType.setLastDetail(inventoryTransactionRoleTypeDetail);

            sendEvent(inventoryTransactionRoleTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateInventoryTransactionRoleTypeFromValue(InventoryTransactionRoleTypeDetailValue inventoryTransactionRoleTypeDetailValue,
            BasePK updatedBy) {
        updateInventoryTransactionRoleTypeFromValue(inventoryTransactionRoleTypeDetailValue, true, updatedBy);
    }

    public void deleteInventoryTransactionRoleType(InventoryTransactionRoleType inventoryTransactionRoleType, BasePK deletedBy) {
        deleteInventoryTransactionRoleTypeDescriptionsByInventoryTransactionRoleType(inventoryTransactionRoleType, deletedBy);

        var inventoryTransactionRoleTypeDetail = inventoryTransactionRoleType.getLastDetailForUpdate();
        inventoryTransactionRoleTypeDetail.setThruTime(session.getStartTime());
        inventoryTransactionRoleType.setActiveDetail(null);
        inventoryTransactionRoleType.store();

        // Check for default, and pick one if necessary
        var inventoryTransactionType = inventoryTransactionRoleTypeDetail.getInventoryTransactionType();
        var defaultInventoryTransactionRoleType = getDefaultInventoryTransactionRoleType(inventoryTransactionType);
        if(defaultInventoryTransactionRoleType == null) {
            var inventoryTransactionRoleTypes = getInventoryTransactionRoleTypesForUpdate(inventoryTransactionType);

            if(!inventoryTransactionRoleTypes.isEmpty()) {
                var iter = inventoryTransactionRoleTypes.iterator();
                if(iter.hasNext()) {
                    defaultInventoryTransactionRoleType = iter.next();
                }
                var inventoryTransactionRoleTypeDetailValue = 
                        Objects.requireNonNull(
                                defaultInventoryTransactionRoleType).getLastDetailForUpdate().getInventoryTransactionRoleTypeDetailValue().clone();

                inventoryTransactionRoleTypeDetailValue.setIsDefault(true);
                updateInventoryTransactionRoleTypeFromValue(inventoryTransactionRoleTypeDetailValue, false, deletedBy);
            }
        }

        sendEvent(inventoryTransactionRoleType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteInventoryTransactionRoleTypesByInventoryTransactionType(InventoryTransactionType inventoryTransactionType, BasePK deletedBy) {
        var inventoryTransactionRoleTypes = getInventoryTransactionRoleTypesForUpdate(inventoryTransactionType);

        inventoryTransactionRoleTypes.forEach(inventoryTransactionRoleType ->
                deleteInventoryTransactionRoleType(inventoryTransactionRoleType, deletedBy));
    }

    // --------------------------------------------------------------------------------
    //   Inventory Transaction Role Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    InventoryTransactionRoleTypeDescriptionFactory inventoryTransactionRoleTypeDescriptionFactory;

    @Inject
    InventoryTransactionRoleTypeDescriptionTransferCache inventoryTransactionRoleTypeDescriptionTransferCache;

    public InventoryTransactionRoleTypeDescription createInventoryTransactionRoleTypeDescription(
            InventoryTransactionRoleType inventoryTransactionRoleType, Language language, String description, BasePK createdBy) {
        var inventoryTransactionRoleTypeDescription = inventoryTransactionRoleTypeDescriptionFactory.create(inventoryTransactionRoleType,
                language, description, session.getStartTime(), Session.MAX_TIME);

        sendEvent(inventoryTransactionRoleType.getPrimaryKey(), EventTypes.MODIFY, inventoryTransactionRoleTypeDescription.getPrimaryKey(),
                EventTypes.CREATE, createdBy);

        return inventoryTransactionRoleTypeDescription;
    }

    public long countInventoryTransactionRoleTypeDescriptionsByInventoryTransactionRoleType(
            InventoryTransactionRoleType inventoryTransactionRoleType) {
        return session.getDslContext()
                .selectCount()
                .from(InventoryTransactionRoleTypeDescriptions)
                .where(InventoryTransactionRoleTypeDescriptions.INVENTORY_TRANSACTION_ROLE_TYPE.eq(inventoryTransactionRoleType.getPrimaryKey()),
                        InventoryTransactionRoleTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countInventoryTransactionRoleTypeDescriptionsByLanguage(Language language) {
        return session.getDslContext()
                .selectCount()
                .from(InventoryTransactionRoleTypeDescriptions)
                .where(InventoryTransactionRoleTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        InventoryTransactionRoleTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private InventoryTransactionRoleTypeDescription getInventoryTransactionRoleTypeDescription(
            InventoryTransactionRoleType inventoryTransactionRoleType, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryTransactionRoleTypeDescriptions.fields())
                .from(InventoryTransactionRoleTypeDescriptions)
                .where(InventoryTransactionRoleTypeDescriptions.INVENTORY_TRANSACTION_ROLE_TYPE.eq(inventoryTransactionRoleType.getPrimaryKey()),
                        InventoryTransactionRoleTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        InventoryTransactionRoleTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return inventoryTransactionRoleTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }

    public InventoryTransactionRoleTypeDescription getInventoryTransactionRoleTypeDescription(
            InventoryTransactionRoleType inventoryTransactionRoleType, Language language) {
        return getInventoryTransactionRoleTypeDescription(inventoryTransactionRoleType, language, EntityPermission.READ_ONLY);
    }

    public InventoryTransactionRoleTypeDescription getInventoryTransactionRoleTypeDescriptionForUpdate(
            InventoryTransactionRoleType inventoryTransactionRoleType, Language language) {
        return getInventoryTransactionRoleTypeDescription(inventoryTransactionRoleType, language, EntityPermission.READ_WRITE);
    }

    public InventoryTransactionRoleTypeDescriptionValue getInventoryTransactionRoleTypeDescriptionValue(
            InventoryTransactionRoleTypeDescription inventoryTransactionRoleTypeDescription) {
        return inventoryTransactionRoleTypeDescription == null ? null
                : inventoryTransactionRoleTypeDescription.getInventoryTransactionRoleTypeDescriptionValue().clone();
    }

    public InventoryTransactionRoleTypeDescriptionValue getInventoryTransactionRoleTypeDescriptionValueForUpdate(
            InventoryTransactionRoleType inventoryTransactionRoleType, Language language) {
        return getInventoryTransactionRoleTypeDescriptionValue(getInventoryTransactionRoleTypeDescriptionForUpdate(inventoryTransactionRoleType,
                language));
    }

    private List<InventoryTransactionRoleTypeDescription> getInventoryTransactionRoleTypeDescriptionsByInventoryTransactionRoleType(
            InventoryTransactionRoleType inventoryTransactionRoleType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(InventoryTransactionRoleTypeDescriptions.fields())
                    .from(InventoryTransactionRoleTypeDescriptions)
                    .join(Languages).on(InventoryTransactionRoleTypeDescriptions.LANGUAGE.eq(Languages.LANGUAGE))
                    .where(InventoryTransactionRoleTypeDescriptions.INVENTORY_TRANSACTION_ROLE_TYPE.eq(inventoryTransactionRoleType.getPrimaryKey()),
                            InventoryTransactionRoleTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    InventoryTransactionRoleTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(InventoryTransactionRoleTypeDescriptions.fields())
                    .from(InventoryTransactionRoleTypeDescriptions)
                    .where(InventoryTransactionRoleTypeDescriptions.INVENTORY_TRANSACTION_ROLE_TYPE.eq(inventoryTransactionRoleType.getPrimaryKey()),
                            InventoryTransactionRoleTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return inventoryTransactionRoleTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<InventoryTransactionRoleTypeDescription> getInventoryTransactionRoleTypeDescriptionsByInventoryTransactionRoleType(
            InventoryTransactionRoleType inventoryTransactionRoleType) {
        return getInventoryTransactionRoleTypeDescriptionsByInventoryTransactionRoleType(inventoryTransactionRoleType, EntityPermission.READ_ONLY);
    }

    public List<InventoryTransactionRoleTypeDescription> getInventoryTransactionRoleTypeDescriptionsByInventoryTransactionRoleTypeForUpdate(
            InventoryTransactionRoleType inventoryTransactionRoleType) {
        return getInventoryTransactionRoleTypeDescriptionsByInventoryTransactionRoleType(inventoryTransactionRoleType, EntityPermission.READ_WRITE);
    }

    public String getBestInventoryTransactionRoleTypeDescription(InventoryTransactionRoleType inventoryTransactionRoleType, Language language) {
        String description;
        var inventoryTransactionRoleTypeDescription = getInventoryTransactionRoleTypeDescription(inventoryTransactionRoleType, language);

        if(inventoryTransactionRoleTypeDescription == null && !language.getIsDefault()) {
            inventoryTransactionRoleTypeDescription = getInventoryTransactionRoleTypeDescription(inventoryTransactionRoleType,
                    partyControl.getDefaultLanguage());
        }

        if(inventoryTransactionRoleTypeDescription == null) {
            description = inventoryTransactionRoleType.getLastDetail().getInventoryTransactionRoleTypeName();
        } else {
            description = inventoryTransactionRoleTypeDescription.getDescription();
        }

        return description;
    }

    public InventoryTransactionRoleTypeDescriptionTransfer getInventoryTransactionRoleTypeDescriptionTransfer(UserVisit userVisit,
            InventoryTransactionRoleTypeDescription inventoryTransactionRoleTypeDescription) {
        return inventoryTransactionRoleTypeDescriptionTransferCache.getTransfer(userVisit, inventoryTransactionRoleTypeDescription);
    }

    public List<InventoryTransactionRoleTypeDescriptionTransfer> getInventoryTransactionRoleTypeDescriptionTransfers(
            UserVisit userVisit, Collection<InventoryTransactionRoleTypeDescription> inventoryTransactionRoleTypeDescriptions) {
        var transfers = new ArrayList<InventoryTransactionRoleTypeDescriptionTransfer>(inventoryTransactionRoleTypeDescriptions.size());

        inventoryTransactionRoleTypeDescriptions.forEach(inventoryTransactionRoleTypeDescription ->
                transfers.add(inventoryTransactionRoleTypeDescriptionTransferCache.getTransfer(userVisit,
                        inventoryTransactionRoleTypeDescription)));

        return transfers;
    }

    public List<InventoryTransactionRoleTypeDescriptionTransfer> getInventoryTransactionRoleTypeDescriptionTransfersByInventoryTransactionRoleType(
            UserVisit userVisit, InventoryTransactionRoleType inventoryTransactionRoleType) {
        return getInventoryTransactionRoleTypeDescriptionTransfers(userVisit,
                getInventoryTransactionRoleTypeDescriptionsByInventoryTransactionRoleType(inventoryTransactionRoleType));
    }

    public void updateInventoryTransactionRoleTypeDescriptionFromValue(
            InventoryTransactionRoleTypeDescriptionValue inventoryTransactionRoleTypeDescriptionValue, BasePK updatedBy) {
        if(inventoryTransactionRoleTypeDescriptionValue.hasBeenModified()) {
            var inventoryTransactionRoleTypeDescription = inventoryTransactionRoleTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    inventoryTransactionRoleTypeDescriptionValue.getPrimaryKey());

            inventoryTransactionRoleTypeDescription.setThruTime(session.getStartTime());
            inventoryTransactionRoleTypeDescription.store();

            var inventoryTransactionRoleType = inventoryTransactionRoleTypeDescription.getInventoryTransactionRoleType();
            var language = inventoryTransactionRoleTypeDescription.getLanguage();
            var description = inventoryTransactionRoleTypeDescriptionValue.getDescription();

            inventoryTransactionRoleTypeDescription = inventoryTransactionRoleTypeDescriptionFactory.create(inventoryTransactionRoleType,
                    language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(inventoryTransactionRoleType.getPrimaryKey(), EventTypes.MODIFY, inventoryTransactionRoleTypeDescription.getPrimaryKey(),
                    EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteInventoryTransactionRoleTypeDescription(InventoryTransactionRoleTypeDescription inventoryTransactionRoleTypeDescription,
            BasePK deletedBy) {
        inventoryTransactionRoleTypeDescription.setThruTime(session.getStartTime());

        sendEvent(inventoryTransactionRoleTypeDescription.getInventoryTransactionRoleTypePK(), EventTypes.MODIFY,
                inventoryTransactionRoleTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteInventoryTransactionRoleTypeDescriptionsByInventoryTransactionRoleType(
            InventoryTransactionRoleType inventoryTransactionRoleType, BasePK deletedBy) {
        var inventoryTransactionRoleTypeDescriptions = 
                getInventoryTransactionRoleTypeDescriptionsByInventoryTransactionRoleTypeForUpdate(inventoryTransactionRoleType);

        inventoryTransactionRoleTypeDescriptions.forEach((inventoryTransactionRoleTypeDescription) -> 
                deleteInventoryTransactionRoleTypeDescription(inventoryTransactionRoleTypeDescription, deletedBy)
        );
    }

}
