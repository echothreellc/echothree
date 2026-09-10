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
import com.echothree.model.data.inventory.server.entity.InventoryTransaction;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionLine;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionLineRole;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionRole;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionRoleType;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionRoleTypeDescription;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionType;
import com.echothree.model.data.inventory.server.factory.InventoryTransactionLineRoleFactory;
import com.echothree.model.data.inventory.server.factory.InventoryTransactionRoleFactory;
import com.echothree.model.data.inventory.server.factory.InventoryTransactionRoleTypeDescriptionFactory;
import com.echothree.model.data.inventory.server.factory.InventoryTransactionRoleTypeDetailFactory;
import com.echothree.model.data.inventory.server.factory.InventoryTransactionRoleTypeFactory;
import com.echothree.model.data.inventory.server.value.InventoryTransactionLineRoleValue;
import com.echothree.model.data.inventory.server.value.InventoryTransactionRoleTypeDescriptionValue;
import com.echothree.model.data.inventory.server.value.InventoryTransactionRoleTypeDetailValue;
import com.echothree.model.data.inventory.server.value.InventoryTransactionRoleValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.inventory.InventoryForeignKeys.INVENTORY_TRANSACTIONS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.inventory.InventoryForeignKeys.INVENTORY_TRANSACTION_LINES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.inventory.InventoryForeignKeys.INVENTORY_TRANSACTION_ROLE_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.inventory.InventoryForeignKeys.INVENTORY_TRANSACTION_ROLE_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.inventory.InventoryForeignKeys.INVENTORY_TRANSACTION_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.party.PartyForeignKeys.PARTIES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.tables.inventory.InventoryTransactionDetails.InventoryTransactionDetails;
import static com.echothree.model.jooq.server.tables.inventory.InventoryTransactionLineDetails.InventoryTransactionLineDetails;
import static com.echothree.model.jooq.server.tables.inventory.InventoryTransactionLineRoles.InventoryTransactionLineRoles;
import static com.echothree.model.jooq.server.tables.inventory.InventoryTransactionLines.InventoryTransactionLines;
import static com.echothree.model.jooq.server.tables.inventory.InventoryTransactionRoleTypeDescriptions.InventoryTransactionRoleTypeDescriptions;
import static com.echothree.model.jooq.server.tables.inventory.InventoryTransactionRoleTypeDetails.InventoryTransactionRoleTypeDetails;
import static com.echothree.model.jooq.server.tables.inventory.InventoryTransactionRoleTypes.InventoryTransactionRoleTypes;
import static com.echothree.model.jooq.server.tables.inventory.InventoryTransactionRoles.InventoryTransactionRoles;
import static com.echothree.model.jooq.server.tables.inventory.InventoryTransactionTypeDetails.InventoryTransactionTypeDetails;
import static com.echothree.model.jooq.server.tables.inventory.InventoryTransactionTypes.InventoryTransactionTypes;
import static com.echothree.model.jooq.server.tables.inventory.InventoryTransactions.InventoryTransactions;
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
import org.jooq.Condition;

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
        var inventoryTransactionRoleTypeDetail = inventoryTransactionRoleType.getLastDetailForUpdate();

        deleteInventoryTransactionRoleTypeDescriptionsByInventoryTransactionRoleType(inventoryTransactionRoleType, deletedBy);
        deleteInventoryTransactionRolesByInventoryTransactionRoleType(inventoryTransactionRoleType, deletedBy);
        deleteInventoryTransactionLineRolesByInventoryTransactionRoleType(inventoryTransactionRoleType, deletedBy);

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

    // --------------------------------------------------------------------------------
    //   Inventory Transaction Roles
    // --------------------------------------------------------------------------------

    @Inject
    protected InventoryTransactionRoleFactory inventoryTransactionRoleFactory;

    public InventoryTransactionRole createInventoryTransactionRole(InventoryTransaction inventoryTransaction,
            InventoryTransactionRoleType inventoryTransactionRoleType, Party party, BasePK createdBy) {
        var inventoryTransactionRole = inventoryTransactionRoleFactory.create(inventoryTransaction, inventoryTransactionRoleType, party,
                session.getStartTime(), Session.MAX_TIME);

        sendEvent(inventoryTransaction.getPrimaryKey(), EventTypes.MODIFY, inventoryTransactionRole.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return inventoryTransactionRole;
    }

    private long countInventoryTransactionRoles(Condition condition) {
        return session.getDslContext()
                .selectCount()
                .from(InventoryTransactionRoles)
                .where(condition, InventoryTransactionRoles.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countInventoryTransactionRolesByInventoryTransaction(InventoryTransaction inventoryTransaction) {
        return countInventoryTransactionRoles(InventoryTransactionRoles.INVENTORY_TRANSACTION.eq(inventoryTransaction.getPrimaryKey()));
    }

    public long countInventoryTransactionRolesByInventoryTransactionRoleType(InventoryTransactionRoleType inventoryTransactionRoleType) {
        return countInventoryTransactionRoles(InventoryTransactionRoles.INVENTORY_TRANSACTION_ROLE_TYPE.eq(inventoryTransactionRoleType.getPrimaryKey()));
    }

    public long countInventoryTransactionRolesByParty(Party party) {
        return countInventoryTransactionRoles(InventoryTransactionRoles.PARTY.eq(party.getPrimaryKey()));
    }

    public boolean inventoryTransactionRoleExists(InventoryTransaction inventoryTransaction, InventoryTransactionRoleType inventoryTransactionRoleType,
            Party party) {
        return countInventoryTransactionRoles(InventoryTransactionRoles.INVENTORY_TRANSACTION.eq(inventoryTransaction.getPrimaryKey())
                .and(InventoryTransactionRoles.INVENTORY_TRANSACTION_ROLE_TYPE.eq(inventoryTransactionRoleType.getPrimaryKey()))
                .and(InventoryTransactionRoles.PARTY.eq(party.getPrimaryKey()))) != 0;
    }

    public InventoryTransactionRole getInventoryTransactionRole(InventoryTransaction inventoryTransaction,
            InventoryTransactionRoleType inventoryTransactionRoleType, Party party, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryTransactionRoles.fields())
                .from(InventoryTransactionRoles)
                .where(InventoryTransactionRoles.INVENTORY_TRANSACTION.eq(inventoryTransaction.getPrimaryKey()),
                        InventoryTransactionRoles.INVENTORY_TRANSACTION_ROLE_TYPE.eq(inventoryTransactionRoleType.getPrimaryKey()),
                        InventoryTransactionRoles.PARTY.eq(party.getPrimaryKey()),
                        InventoryTransactionRoles.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return inventoryTransactionRoleFactory.getEntityFromQuery(entityPermission, query);
    }

    public InventoryTransactionRole getInventoryTransactionRole(InventoryTransaction inventoryTransaction,
            InventoryTransactionRoleType inventoryTransactionRoleType, Party party) {
        return getInventoryTransactionRole(inventoryTransaction, inventoryTransactionRoleType, party, EntityPermission.READ_ONLY);
    }

    public InventoryTransactionRole getInventoryTransactionRoleForUpdate(InventoryTransaction inventoryTransaction,
            InventoryTransactionRoleType inventoryTransactionRoleType, Party party) {
        return getInventoryTransactionRole(inventoryTransaction, inventoryTransactionRoleType, party, EntityPermission.READ_WRITE);
    }

    public InventoryTransactionRoleValue getInventoryTransactionRoleValue(InventoryTransactionRole inventoryTransactionRole) {
        return inventoryTransactionRole == null ? null : inventoryTransactionRole.getInventoryTransactionRoleValue().clone();
    }

    public InventoryTransactionRoleValue getInventoryTransactionRoleValueForUpdate(InventoryTransaction inventoryTransaction,
            InventoryTransactionRoleType inventoryTransactionRoleType, Party party) {
        return getInventoryTransactionRoleValue(getInventoryTransactionRoleForUpdate(inventoryTransaction, inventoryTransactionRoleType, party));
    }

    private List<InventoryTransactionRole> getInventoryTransactionRolesByInventoryTransaction(InventoryTransaction inventoryTransaction,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(InventoryTransactionRoles.fields())
                    .from(InventoryTransactionRoles)
                    .join(InventoryTransactionRoleTypes).on(InventoryTransactionRoles.INVENTORY_TRANSACTION_ROLE_TYPE.eq(InventoryTransactionRoleTypes.INVENTORY_TRANSACTION_ROLE_TYPE))
                    .join(InventoryTransactionRoleTypeDetails).onKey(INVENTORY_TRANSACTION_ROLE_TYPES_LAST_DETAIL_FK)
                    .join(Parties).on(InventoryTransactionRoles.PARTY.eq(Parties.PARTY))
                    .join(PartyDetails).onKey(PARTIES_LAST_DETAIL_FK)
                    .where(InventoryTransactionRoles.INVENTORY_TRANSACTION.eq(inventoryTransaction.getPrimaryKey()),
                            InventoryTransactionRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(InventoryTransactionRoleTypeDetails.SORT_ORDER, InventoryTransactionRoleTypeDetails.INVENTORY_TRANSACTION_ROLE_TYPE_NAME,
                            PartyDetails.PARTY_NAME),
                    InventoryTransactionRoleFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(InventoryTransactionRoles.fields())
                    .from(InventoryTransactionRoles)
                    .where(InventoryTransactionRoles.INVENTORY_TRANSACTION.eq(inventoryTransaction.getPrimaryKey()),
                            InventoryTransactionRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return inventoryTransactionRoleFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<InventoryTransactionRole> getInventoryTransactionRolesByInventoryTransaction(InventoryTransaction inventoryTransaction) {
        return getInventoryTransactionRolesByInventoryTransaction(inventoryTransaction, EntityPermission.READ_ONLY);
    }

    public List<InventoryTransactionRole> getInventoryTransactionRolesByInventoryTransactionForUpdate(InventoryTransaction inventoryTransaction) {
        return getInventoryTransactionRolesByInventoryTransaction(inventoryTransaction, EntityPermission.READ_WRITE);
    }

    private List<InventoryTransactionRole> getInventoryTransactionRolesByInventoryTransactionRoleType(InventoryTransactionRoleType inventoryTransactionRoleType,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(InventoryTransactionRoles.fields())
                    .from(InventoryTransactionRoles)
                    .join(InventoryTransactions).on(InventoryTransactionRoles.INVENTORY_TRANSACTION.eq(InventoryTransactions.INVENTORY_TRANSACTION))
                    .join(InventoryTransactionDetails).onKey(INVENTORY_TRANSACTIONS_LAST_DETAIL_FK)
                    .join(Parties).on(InventoryTransactionRoles.PARTY.eq(Parties.PARTY))
                    .join(PartyDetails).onKey(PARTIES_LAST_DETAIL_FK)
                    .where(InventoryTransactionRoles.INVENTORY_TRANSACTION_ROLE_TYPE.eq(inventoryTransactionRoleType.getPrimaryKey()),
                            InventoryTransactionRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(InventoryTransactionDetails.INVENTORY_TRANSACTION_NAME, PartyDetails.PARTY_NAME),
                    InventoryTransactionRoleFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(InventoryTransactionRoles.fields())
                    .from(InventoryTransactionRoles)
                    .where(InventoryTransactionRoles.INVENTORY_TRANSACTION_ROLE_TYPE.eq(inventoryTransactionRoleType.getPrimaryKey()),
                            InventoryTransactionRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return inventoryTransactionRoleFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<InventoryTransactionRole> getInventoryTransactionRolesByInventoryTransactionRoleType(InventoryTransactionRoleType inventoryTransactionRoleType) {
        return getInventoryTransactionRolesByInventoryTransactionRoleType(inventoryTransactionRoleType, EntityPermission.READ_ONLY);
    }

    public List<InventoryTransactionRole> getInventoryTransactionRolesByInventoryTransactionRoleTypeForUpdate(InventoryTransactionRoleType inventoryTransactionRoleType) {
        return getInventoryTransactionRolesByInventoryTransactionRoleType(inventoryTransactionRoleType, EntityPermission.READ_WRITE);
    }

    private List<InventoryTransactionRole> getInventoryTransactionRolesByParty(Party party, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(InventoryTransactionRoles.fields())
                    .from(InventoryTransactionRoles)
                    .join(InventoryTransactions).on(InventoryTransactionRoles.INVENTORY_TRANSACTION.eq(InventoryTransactions.INVENTORY_TRANSACTION))
                    .join(InventoryTransactionDetails).onKey(INVENTORY_TRANSACTIONS_LAST_DETAIL_FK)
                    .join(InventoryTransactionTypes).on(InventoryTransactionDetails.INVENTORY_TRANSACTION_TYPE.eq(InventoryTransactionTypes.INVENTORY_TRANSACTION_TYPE))
                    .join(InventoryTransactionTypeDetails).onKey(INVENTORY_TRANSACTION_TYPES_LAST_DETAIL_FK)
                    .join(InventoryTransactionRoleTypes).on(InventoryTransactionRoles.INVENTORY_TRANSACTION_ROLE_TYPE.eq(InventoryTransactionRoleTypes.INVENTORY_TRANSACTION_ROLE_TYPE))
                    .join(InventoryTransactionRoleTypeDetails).onKey(INVENTORY_TRANSACTION_ROLE_TYPES_LAST_DETAIL_FK)
                    .where(InventoryTransactionRoles.PARTY.eq(party.getPrimaryKey()),
                            InventoryTransactionRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(InventoryTransactionTypeDetails.SORT_ORDER, InventoryTransactionTypeDetails.INVENTORY_TRANSACTION_TYPE_NAME,
                            InventoryTransactionDetails.INVENTORY_TRANSACTION_NAME,
                            InventoryTransactionRoleTypeDetails.SORT_ORDER, InventoryTransactionRoleTypeDetails.INVENTORY_TRANSACTION_ROLE_TYPE_NAME),
                    InventoryTransactionRoleFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(InventoryTransactionRoles.fields())
                    .from(InventoryTransactionRoles)
                    .where(InventoryTransactionRoles.PARTY.eq(party.getPrimaryKey()),
                            InventoryTransactionRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return inventoryTransactionRoleFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<InventoryTransactionRole> getInventoryTransactionRolesByParty(Party party) {
        return getInventoryTransactionRolesByParty(party, EntityPermission.READ_ONLY);
    }

    public List<InventoryTransactionRole> getInventoryTransactionRolesByPartyForUpdate(Party party) {
        return getInventoryTransactionRolesByParty(party, EntityPermission.READ_WRITE);
    }

    public void deleteInventoryTransactionRole(InventoryTransactionRole inventoryTransactionRole, BasePK deletedBy) {
        inventoryTransactionRole.setThruTime(session.getStartTime());

        sendEvent(inventoryTransactionRole.getInventoryTransactionPK(), EventTypes.MODIFY, inventoryTransactionRole.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteInventoryTransactionRoles(List<InventoryTransactionRole> inventoryTransactionRoles, BasePK deletedBy) {
        inventoryTransactionRoles.forEach(inventoryTransactionRole -> deleteInventoryTransactionRole(inventoryTransactionRole, deletedBy));
    }

    public void deleteInventoryTransactionRolesByInventoryTransaction(InventoryTransaction inventoryTransaction, BasePK deletedBy) {
        deleteInventoryTransactionRoles(getInventoryTransactionRolesByInventoryTransactionForUpdate(inventoryTransaction), deletedBy);
    }

    public void deleteInventoryTransactionRolesByInventoryTransactionRoleType(InventoryTransactionRoleType inventoryTransactionRoleType,
            BasePK deletedBy) {
        deleteInventoryTransactionRoles(getInventoryTransactionRolesByInventoryTransactionRoleTypeForUpdate(inventoryTransactionRoleType), deletedBy);
    }

    public void deleteInventoryTransactionRolesByParty(Party party, BasePK deletedBy) {
        deleteInventoryTransactionRoles(getInventoryTransactionRolesByPartyForUpdate(party), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Inventory Transaction Line Roles
    // --------------------------------------------------------------------------------

    @Inject
    protected InventoryTransactionLineRoleFactory inventoryTransactionLineRoleFactory;

    public InventoryTransactionLineRole createInventoryTransactionLineRole(InventoryTransactionLine inventoryTransactionLine,
            InventoryTransactionRoleType inventoryTransactionRoleType, Party party, BasePK createdBy) {
        var inventoryTransactionLineRole = inventoryTransactionLineRoleFactory.create(inventoryTransactionLine, inventoryTransactionRoleType, party,
                session.getStartTime(), Session.MAX_TIME);

        sendEvent(inventoryTransactionLine.getPrimaryKey(), EventTypes.MODIFY, inventoryTransactionLineRole.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return inventoryTransactionLineRole;
    }

    private long countInventoryTransactionLineRoles(Condition condition) {
        return session.getDslContext()
                .selectCount()
                .from(InventoryTransactionLineRoles)
                .where(condition, InventoryTransactionLineRoles.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countInventoryTransactionLineRolesByInventoryTransactionLine(InventoryTransactionLine inventoryTransactionLine) {
        return countInventoryTransactionLineRoles(InventoryTransactionLineRoles.INVENTORY_TRANSACTION_LINE.eq(inventoryTransactionLine.getPrimaryKey()));
    }

    public long countInventoryTransactionLineRolesByInventoryTransactionRoleType(InventoryTransactionRoleType inventoryTransactionRoleType) {
        return countInventoryTransactionLineRoles(InventoryTransactionLineRoles.INVENTORY_TRANSACTION_ROLE_TYPE.eq(inventoryTransactionRoleType.getPrimaryKey()));
    }

    public long countInventoryTransactionLineRolesByParty(Party party) {
        return countInventoryTransactionLineRoles(InventoryTransactionLineRoles.PARTY.eq(party.getPrimaryKey()));
    }

    public boolean inventoryTransactionLineRoleExists(InventoryTransactionLine inventoryTransactionLine, InventoryTransactionRoleType inventoryTransactionRoleType,
            Party party) {
        return countInventoryTransactionLineRoles(InventoryTransactionLineRoles.INVENTORY_TRANSACTION_LINE.eq(inventoryTransactionLine.getPrimaryKey())
                .and(InventoryTransactionLineRoles.INVENTORY_TRANSACTION_ROLE_TYPE.eq(inventoryTransactionRoleType.getPrimaryKey()))
                .and(InventoryTransactionLineRoles.PARTY.eq(party.getPrimaryKey()))) != 0;
    }

    public InventoryTransactionLineRole getInventoryTransactionLineRole(InventoryTransactionLine inventoryTransactionLine,
            InventoryTransactionRoleType inventoryTransactionRoleType, Party party, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryTransactionLineRoles.fields())
                .from(InventoryTransactionLineRoles)
                .where(InventoryTransactionLineRoles.INVENTORY_TRANSACTION_LINE.eq(inventoryTransactionLine.getPrimaryKey()),
                        InventoryTransactionLineRoles.INVENTORY_TRANSACTION_ROLE_TYPE.eq(inventoryTransactionRoleType.getPrimaryKey()),
                        InventoryTransactionLineRoles.PARTY.eq(party.getPrimaryKey()),
                        InventoryTransactionLineRoles.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return inventoryTransactionLineRoleFactory.getEntityFromQuery(entityPermission, query);
    }

    public InventoryTransactionLineRole getInventoryTransactionLineRole(InventoryTransactionLine inventoryTransactionLine,
            InventoryTransactionRoleType inventoryTransactionRoleType, Party party) {
        return getInventoryTransactionLineRole(inventoryTransactionLine, inventoryTransactionRoleType, party, EntityPermission.READ_ONLY);
    }

    public InventoryTransactionLineRole getInventoryTransactionLineRoleForUpdate(InventoryTransactionLine inventoryTransactionLine,
            InventoryTransactionRoleType inventoryTransactionRoleType, Party party) {
        return getInventoryTransactionLineRole(inventoryTransactionLine, inventoryTransactionRoleType, party, EntityPermission.READ_WRITE);
    }

    public InventoryTransactionLineRoleValue getInventoryTransactionLineRoleValue(InventoryTransactionLineRole inventoryTransactionLineRole) {
        return inventoryTransactionLineRole == null ? null : inventoryTransactionLineRole.getInventoryTransactionLineRoleValue().clone();
    }

    public InventoryTransactionLineRoleValue getInventoryTransactionLineRoleValueForUpdate(InventoryTransactionLine inventoryTransactionLine,
            InventoryTransactionRoleType inventoryTransactionRoleType, Party party) {
        return getInventoryTransactionLineRoleValue(getInventoryTransactionLineRoleForUpdate(inventoryTransactionLine, inventoryTransactionRoleType, party));
    }

    private List<InventoryTransactionLineRole> getInventoryTransactionLineRolesByInventoryTransactionLine(InventoryTransactionLine inventoryTransactionLine,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(InventoryTransactionLineRoles.fields())
                    .from(InventoryTransactionLineRoles)
                    .join(InventoryTransactionRoleTypes).on(InventoryTransactionLineRoles.INVENTORY_TRANSACTION_ROLE_TYPE.eq(InventoryTransactionRoleTypes.INVENTORY_TRANSACTION_ROLE_TYPE))
                    .join(InventoryTransactionRoleTypeDetails).onKey(INVENTORY_TRANSACTION_ROLE_TYPES_LAST_DETAIL_FK)
                    .join(Parties).on(InventoryTransactionLineRoles.PARTY.eq(Parties.PARTY))
                    .join(PartyDetails).onKey(PARTIES_LAST_DETAIL_FK)
                    .where(InventoryTransactionLineRoles.INVENTORY_TRANSACTION_LINE.eq(inventoryTransactionLine.getPrimaryKey()),
                            InventoryTransactionLineRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(InventoryTransactionRoleTypeDetails.SORT_ORDER, InventoryTransactionRoleTypeDetails.INVENTORY_TRANSACTION_ROLE_TYPE_NAME,
                            PartyDetails.PARTY_NAME),
                    InventoryTransactionLineRoleFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(InventoryTransactionLineRoles.fields())
                    .from(InventoryTransactionLineRoles)
                    .where(InventoryTransactionLineRoles.INVENTORY_TRANSACTION_LINE.eq(inventoryTransactionLine.getPrimaryKey()),
                            InventoryTransactionLineRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return inventoryTransactionLineRoleFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<InventoryTransactionLineRole> getInventoryTransactionLineRolesByInventoryTransactionLine(InventoryTransactionLine inventoryTransactionLine) {
        return getInventoryTransactionLineRolesByInventoryTransactionLine(inventoryTransactionLine, EntityPermission.READ_ONLY);
    }

    public List<InventoryTransactionLineRole> getInventoryTransactionLineRolesByInventoryTransactionLineForUpdate(InventoryTransactionLine inventoryTransactionLine) {
        return getInventoryTransactionLineRolesByInventoryTransactionLine(inventoryTransactionLine, EntityPermission.READ_WRITE);
    }

    private List<InventoryTransactionLineRole> getInventoryTransactionLineRolesByInventoryTransactionRoleType(InventoryTransactionRoleType inventoryTransactionRoleType,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(InventoryTransactionLineRoles.fields())
                    .from(InventoryTransactionLineRoles)
                    .join(InventoryTransactionLines).on(InventoryTransactionLineRoles.INVENTORY_TRANSACTION_LINE.eq(InventoryTransactionLines.INVENTORY_TRANSACTION_LINE))
                    .join(InventoryTransactionLineDetails).onKey(INVENTORY_TRANSACTION_LINES_LAST_DETAIL_FK)
                    .join(InventoryTransactions).on(InventoryTransactionLineDetails.INVENTORY_TRANSACTION.eq(InventoryTransactions.INVENTORY_TRANSACTION))
                    .join(InventoryTransactionDetails).onKey(INVENTORY_TRANSACTIONS_LAST_DETAIL_FK)
                    .join(Parties).on(InventoryTransactionLineRoles.PARTY.eq(Parties.PARTY))
                    .join(PartyDetails).onKey(PARTIES_LAST_DETAIL_FK)
                    .where(InventoryTransactionLineRoles.INVENTORY_TRANSACTION_ROLE_TYPE.eq(inventoryTransactionRoleType.getPrimaryKey()),
                            InventoryTransactionLineRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(InventoryTransactionDetails.INVENTORY_TRANSACTION_NAME, InventoryTransactionLineDetails.INVENTORY_TRANSACTION_LINE_SEQUENCE, PartyDetails.PARTY_NAME),
                    InventoryTransactionLineRoleFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(InventoryTransactionLineRoles.fields())
                    .from(InventoryTransactionLineRoles)
                    .where(InventoryTransactionLineRoles.INVENTORY_TRANSACTION_ROLE_TYPE.eq(inventoryTransactionRoleType.getPrimaryKey()),
                            InventoryTransactionLineRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return inventoryTransactionLineRoleFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<InventoryTransactionLineRole> getInventoryTransactionLineRolesByInventoryTransactionRoleType(InventoryTransactionRoleType inventoryTransactionRoleType) {
        return getInventoryTransactionLineRolesByInventoryTransactionRoleType(inventoryTransactionRoleType, EntityPermission.READ_ONLY);
    }

    public List<InventoryTransactionLineRole> getInventoryTransactionLineRolesByInventoryTransactionRoleTypeForUpdate(InventoryTransactionRoleType inventoryTransactionRoleType) {
        return getInventoryTransactionLineRolesByInventoryTransactionRoleType(inventoryTransactionRoleType, EntityPermission.READ_WRITE);
    }

    private List<InventoryTransactionLineRole> getInventoryTransactionLineRolesByParty(Party party, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(InventoryTransactionLineRoles.fields())
                    .from(InventoryTransactionLineRoles)
                    .join(InventoryTransactionLines).on(InventoryTransactionLineRoles.INVENTORY_TRANSACTION_LINE.eq(InventoryTransactionLines.INVENTORY_TRANSACTION_LINE))
                    .join(InventoryTransactionLineDetails).onKey(INVENTORY_TRANSACTION_LINES_LAST_DETAIL_FK)
                    .join(InventoryTransactions).on(InventoryTransactionLineDetails.INVENTORY_TRANSACTION.eq(InventoryTransactions.INVENTORY_TRANSACTION))
                    .join(InventoryTransactionDetails).onKey(INVENTORY_TRANSACTIONS_LAST_DETAIL_FK)
                    .join(InventoryTransactionTypes).on(InventoryTransactionDetails.INVENTORY_TRANSACTION_TYPE.eq(InventoryTransactionTypes.INVENTORY_TRANSACTION_TYPE))
                    .join(InventoryTransactionTypeDetails).onKey(INVENTORY_TRANSACTION_TYPES_LAST_DETAIL_FK)
                    .join(InventoryTransactionRoleTypes).on(InventoryTransactionLineRoles.INVENTORY_TRANSACTION_ROLE_TYPE.eq(InventoryTransactionRoleTypes.INVENTORY_TRANSACTION_ROLE_TYPE))
                    .join(InventoryTransactionRoleTypeDetails).onKey(INVENTORY_TRANSACTION_ROLE_TYPES_LAST_DETAIL_FK)
                    .where(InventoryTransactionLineRoles.PARTY.eq(party.getPrimaryKey()),
                            InventoryTransactionLineRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(InventoryTransactionTypeDetails.SORT_ORDER, InventoryTransactionTypeDetails.INVENTORY_TRANSACTION_TYPE_NAME,
                            InventoryTransactionDetails.INVENTORY_TRANSACTION_NAME, InventoryTransactionLineDetails.INVENTORY_TRANSACTION_LINE_SEQUENCE,
                            InventoryTransactionRoleTypeDetails.SORT_ORDER, InventoryTransactionRoleTypeDetails.INVENTORY_TRANSACTION_ROLE_TYPE_NAME),
                    InventoryTransactionLineRoleFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(InventoryTransactionLineRoles.fields())
                    .from(InventoryTransactionLineRoles)
                    .where(InventoryTransactionLineRoles.PARTY.eq(party.getPrimaryKey()),
                            InventoryTransactionLineRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return inventoryTransactionLineRoleFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<InventoryTransactionLineRole> getInventoryTransactionLineRolesByParty(Party party) {
        return getInventoryTransactionLineRolesByParty(party, EntityPermission.READ_ONLY);
    }

    public List<InventoryTransactionLineRole> getInventoryTransactionLineRolesByPartyForUpdate(Party party) {
        return getInventoryTransactionLineRolesByParty(party, EntityPermission.READ_WRITE);
    }

    public void deleteInventoryTransactionLineRole(InventoryTransactionLineRole inventoryTransactionLineRole, BasePK deletedBy) {
        inventoryTransactionLineRole.setThruTime(session.getStartTime());

        sendEvent(inventoryTransactionLineRole.getInventoryTransactionLinePK(), EventTypes.MODIFY, inventoryTransactionLineRole.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteInventoryTransactionLineRoles(List<InventoryTransactionLineRole> inventoryTransactionLineRoles, BasePK deletedBy) {
        inventoryTransactionLineRoles.forEach(inventoryTransactionLineRole -> deleteInventoryTransactionLineRole(inventoryTransactionLineRole, deletedBy));
    }

    public void deleteInventoryTransactionLineRolesByInventoryTransactionLine(InventoryTransactionLine inventoryTransactionLine, BasePK deletedBy) {
        deleteInventoryTransactionLineRoles(getInventoryTransactionLineRolesByInventoryTransactionLineForUpdate(inventoryTransactionLine), deletedBy);
    }

    public void deleteInventoryTransactionLineRolesByInventoryTransactionRoleType(InventoryTransactionRoleType inventoryTransactionRoleType,
            BasePK deletedBy) {
        deleteInventoryTransactionLineRoles(getInventoryTransactionLineRolesByInventoryTransactionRoleTypeForUpdate(inventoryTransactionRoleType), deletedBy);
    }

    public void deleteInventoryTransactionLineRolesByParty(Party party, BasePK deletedBy) {
        deleteInventoryTransactionLineRoles(getInventoryTransactionLineRolesByPartyForUpdate(party), deletedBy);
    }

}
