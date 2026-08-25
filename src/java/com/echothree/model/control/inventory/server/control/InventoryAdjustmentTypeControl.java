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
import com.echothree.model.control.inventory.common.choice.InventoryAdjustmentTypeChoicesBean;
import com.echothree.model.control.inventory.common.transfer.InventoryAdjustmentTypeDescriptionTransfer;
import com.echothree.model.control.inventory.common.transfer.InventoryAdjustmentTypeTransfer;
import com.echothree.model.control.inventory.server.transfer.InventoryAdjustmentTypeDescriptionTransferCache;
import com.echothree.model.control.inventory.server.transfer.InventoryAdjustmentTypeTransferCache;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.inventory.common.pk.InventoryAdjustmentTypePK;
import com.echothree.model.data.inventory.server.entity.InventoryAdjustmentType;
import com.echothree.model.data.inventory.server.entity.InventoryAdjustmentTypeDescription;
import com.echothree.model.data.inventory.server.factory.InventoryAdjustmentTypeDescriptionFactory;
import com.echothree.model.data.inventory.server.factory.InventoryAdjustmentTypeDetailFactory;
import com.echothree.model.data.inventory.server.factory.InventoryAdjustmentTypeFactory;
import com.echothree.model.data.inventory.server.value.InventoryAdjustmentTypeDescriptionValue;
import com.echothree.model.data.inventory.server.value.InventoryAdjustmentTypeDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.inventory.InventoryForeignKeys.INVENTORY_ADJUSTMENT_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.tables.inventory.InventoryAdjustmentTypeDescriptions.InventoryAdjustmentTypeDescriptions;
import static com.echothree.model.jooq.server.tables.inventory.InventoryAdjustmentTypeDetails.InventoryAdjustmentTypeDetails;
import static com.echothree.model.jooq.server.tables.inventory.InventoryAdjustmentTypes.InventoryAdjustmentTypes;
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
public class InventoryAdjustmentTypeControl
        extends BaseModelControl {

    @Inject
    InventoryAdjustmentTypeTransferCache inventoryAdjustmentTypeTransferCache;

    @Inject
    InventoryAdjustmentTypeDescriptionTransferCache inventoryAdjustmentTypeDescriptionTransferCache;

    /**
     * Creates a new instance of InventoryAdjustmentTypeControl
     */
    protected InventoryAdjustmentTypeControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Inventory Adjustment Types
    // --------------------------------------------------------------------------------

    @Inject
    protected InventoryAdjustmentTypeFactory inventoryAdjustmentTypeFactory;

    @Inject
    protected InventoryAdjustmentTypeDetailFactory inventoryAdjustmentTypeDetailFactory;

    public InventoryAdjustmentType createInventoryAdjustmentType(String inventoryAdjustmentTypeName,
            Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultInventoryAdjustmentType = getDefaultInventoryAdjustmentType();
        var defaultFound = defaultInventoryAdjustmentType != null;

        if(defaultFound && isDefault) {
            var defaultInventoryAdjustmentTypeDetailValue = getDefaultInventoryAdjustmentTypeDetailValueForUpdate();

            defaultInventoryAdjustmentTypeDetailValue.setIsDefault(false);
            updateInventoryAdjustmentTypeFromValue(defaultInventoryAdjustmentTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var inventoryAdjustmentType = inventoryAdjustmentTypeFactory.create();
        var inventoryAdjustmentTypeDetail = inventoryAdjustmentTypeDetailFactory.create(inventoryAdjustmentType,
                inventoryAdjustmentTypeName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        inventoryAdjustmentType = inventoryAdjustmentTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                inventoryAdjustmentType.getPrimaryKey());
        inventoryAdjustmentType.setActiveDetail(inventoryAdjustmentTypeDetail);
        inventoryAdjustmentType.setLastDetail(inventoryAdjustmentTypeDetail);
        inventoryAdjustmentType.store();

        sendEvent(inventoryAdjustmentType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return inventoryAdjustmentType;
    }

    /**
     * Assume that the entityInstance passed to this function is a ECHO_THREE.InventoryAdjustmentType
     */
    public InventoryAdjustmentType getInventoryAdjustmentTypeByEntityInstance(final EntityInstance entityInstance,
            final EntityPermission entityPermission) {
        var pk = new InventoryAdjustmentTypePK(entityInstance.getEntityUniqueId());

        return inventoryAdjustmentTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public InventoryAdjustmentType getInventoryAdjustmentTypeByEntityInstance(final EntityInstance entityInstance) {
        return getInventoryAdjustmentTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public InventoryAdjustmentType getInventoryAdjustmentTypeByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getInventoryAdjustmentTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public InventoryAdjustmentType getInventoryAdjustmentTypeByPK(InventoryAdjustmentTypePK pk) {
        return inventoryAdjustmentTypeFactory.getEntityFromPK(EntityPermission.READ_ONLY, pk);
    }

    public long countInventoryAdjustmentTypes() {
        return session.getDslContext()
                .selectCount()
                .from(InventoryAdjustmentTypes)
                .join(InventoryAdjustmentTypeDetails).onKey(INVENTORY_ADJUSTMENT_TYPES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public InventoryAdjustmentType getInventoryAdjustmentTypeByName(final String inventoryAdjustmentTypeName,
            final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryAdjustmentTypes.fields())
                .from(InventoryAdjustmentTypes)
                .join(InventoryAdjustmentTypeDetails).onKey(INVENTORY_ADJUSTMENT_TYPES_ACTIVE_DETAIL_FK)
                .where(InventoryAdjustmentTypeDetails.INVENTORY_ADJUSTMENT_TYPE_NAME.eq(inventoryAdjustmentTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return inventoryAdjustmentTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public InventoryAdjustmentType getInventoryAdjustmentTypeByName(String inventoryAdjustmentTypeName) {
        return getInventoryAdjustmentTypeByName(inventoryAdjustmentTypeName, EntityPermission.READ_ONLY);
    }

    public InventoryAdjustmentType getInventoryAdjustmentTypeByNameForUpdate(String inventoryAdjustmentTypeName) {
        return getInventoryAdjustmentTypeByName(inventoryAdjustmentTypeName, EntityPermission.READ_WRITE);
    }

    public InventoryAdjustmentTypeDetailValue getInventoryAdjustmentTypeDetailValueForUpdate(InventoryAdjustmentType inventoryAdjustmentType) {
        return inventoryAdjustmentType == null ? null : inventoryAdjustmentType.getLastDetailForUpdate().getInventoryAdjustmentTypeDetailValue().clone();
    }

    public InventoryAdjustmentTypeDetailValue getInventoryAdjustmentTypeDetailValueByNameForUpdate(String inventoryAdjustmentTypeName) {
        return getInventoryAdjustmentTypeDetailValueForUpdate(getInventoryAdjustmentTypeByNameForUpdate(inventoryAdjustmentTypeName));
    }

    public InventoryAdjustmentType getDefaultInventoryAdjustmentType(final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryAdjustmentTypes.fields())
                .from(InventoryAdjustmentTypes)
                .join(InventoryAdjustmentTypeDetails).onKey(INVENTORY_ADJUSTMENT_TYPES_ACTIVE_DETAIL_FK)
                .where(InventoryAdjustmentTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return inventoryAdjustmentTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public InventoryAdjustmentType getDefaultInventoryAdjustmentType() {
        return getDefaultInventoryAdjustmentType(EntityPermission.READ_ONLY);
    }

    public InventoryAdjustmentType getDefaultInventoryAdjustmentTypeForUpdate() {
        return getDefaultInventoryAdjustmentType(EntityPermission.READ_WRITE);
    }

    public InventoryAdjustmentTypeDetailValue getDefaultInventoryAdjustmentTypeDetailValueForUpdate() {
        return getDefaultInventoryAdjustmentTypeForUpdate().getLastDetailForUpdate().getInventoryAdjustmentTypeDetailValue().clone();
    }

    private List<InventoryAdjustmentType> getInventoryAdjustmentTypes(final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryAdjustmentTypes.fields())
                .from(InventoryAdjustmentTypes)
                .join(InventoryAdjustmentTypeDetails).onKey(INVENTORY_ADJUSTMENT_TYPES_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(InventoryAdjustmentTypeDetails.SORT_ORDER, InventoryAdjustmentTypeDetails.INVENTORY_ADJUSTMENT_TYPE_NAME),
                    InventoryAdjustmentTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return inventoryAdjustmentTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<InventoryAdjustmentType> getInventoryAdjustmentTypes() {
        return getInventoryAdjustmentTypes(EntityPermission.READ_ONLY);
    }

    public List<InventoryAdjustmentType> getInventoryAdjustmentTypesForUpdate() {
        return getInventoryAdjustmentTypes(EntityPermission.READ_WRITE);
    }

    public InventoryAdjustmentTypeTransfer getInventoryAdjustmentTypeTransfer(UserVisit userVisit, InventoryAdjustmentType inventoryAdjustmentType) {
        return inventoryAdjustmentTypeTransferCache.getTransfer(userVisit, inventoryAdjustmentType);
    }

    public List<InventoryAdjustmentTypeTransfer> getInventoryAdjustmentTypeTransfers(UserVisit userVisit, Collection<InventoryAdjustmentType> inventoryAdjustmentTypes) {
        List<InventoryAdjustmentTypeTransfer> inventoryAdjustmentTypeTransfers = new ArrayList<>(inventoryAdjustmentTypes.size());

        inventoryAdjustmentTypes.forEach((inventoryAdjustmentType) ->
                inventoryAdjustmentTypeTransfers.add(inventoryAdjustmentTypeTransferCache.getTransfer(userVisit, inventoryAdjustmentType))
        );

        return inventoryAdjustmentTypeTransfers;
    }

    public List<InventoryAdjustmentTypeTransfer> getInventoryAdjustmentTypeTransfers(UserVisit userVisit) {
        return getInventoryAdjustmentTypeTransfers(userVisit, getInventoryAdjustmentTypes());
    }

    public InventoryAdjustmentTypeChoicesBean getInventoryAdjustmentTypeChoices(String defaultInventoryAdjustmentTypeChoice,
            Language language, boolean allowNullChoice) {
        var inventoryAdjustmentTypes = getInventoryAdjustmentTypes();
        var size = inventoryAdjustmentTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultInventoryAdjustmentTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var inventoryAdjustmentType : inventoryAdjustmentTypes) {
            var inventoryAdjustmentTypeDetail = inventoryAdjustmentType.getLastDetail();

            var label = getBestInventoryAdjustmentTypeDescription(inventoryAdjustmentType, language);
            var value = inventoryAdjustmentTypeDetail.getInventoryAdjustmentTypeName();

            labels.add(label == null ? value : label);
            values.add(value);

            var usingDefaultChoice = defaultInventoryAdjustmentTypeChoice != null && defaultInventoryAdjustmentTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && inventoryAdjustmentTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new InventoryAdjustmentTypeChoicesBean(labels, values, defaultValue);
    }

    private void updateInventoryAdjustmentTypeFromValue(InventoryAdjustmentTypeDetailValue inventoryAdjustmentTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(inventoryAdjustmentTypeDetailValue.hasBeenModified()) {
            var inventoryAdjustmentType = inventoryAdjustmentTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    inventoryAdjustmentTypeDetailValue.getInventoryAdjustmentTypePK());
            var inventoryAdjustmentTypeDetail = inventoryAdjustmentType.getActiveDetailForUpdate();

            inventoryAdjustmentTypeDetail.setThruTime(session.getStartTime());
            inventoryAdjustmentTypeDetail.store();

            var inventoryAdjustmentTypePK = inventoryAdjustmentTypeDetail.getInventoryAdjustmentTypePK(); // Not updated
            var inventoryAdjustmentTypeName = inventoryAdjustmentTypeDetailValue.getInventoryAdjustmentTypeName();
            var isDefault = inventoryAdjustmentTypeDetailValue.getIsDefault();
            var sortOrder = inventoryAdjustmentTypeDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultInventoryAdjustmentType = getDefaultInventoryAdjustmentType();
                var defaultFound = defaultInventoryAdjustmentType != null && !defaultInventoryAdjustmentType.equals(inventoryAdjustmentType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultInventoryAdjustmentTypeDetailValue = getDefaultInventoryAdjustmentTypeDetailValueForUpdate();

                    defaultInventoryAdjustmentTypeDetailValue.setIsDefault(false);
                    updateInventoryAdjustmentTypeFromValue(defaultInventoryAdjustmentTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            inventoryAdjustmentTypeDetail = inventoryAdjustmentTypeDetailFactory.create(inventoryAdjustmentTypePK,
                    inventoryAdjustmentTypeName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

            inventoryAdjustmentType.setActiveDetail(inventoryAdjustmentTypeDetail);
            inventoryAdjustmentType.setLastDetail(inventoryAdjustmentTypeDetail);

            sendEvent(inventoryAdjustmentTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateInventoryAdjustmentTypeFromValue(InventoryAdjustmentTypeDetailValue inventoryAdjustmentTypeDetailValue, BasePK updatedBy) {
        updateInventoryAdjustmentTypeFromValue(inventoryAdjustmentTypeDetailValue, true, updatedBy);
    }

    private void deleteInventoryAdjustmentType(InventoryAdjustmentType inventoryAdjustmentType, boolean checkDefault, BasePK deletedBy) {
        var inventoryAdjustmentTypeDetail = inventoryAdjustmentType.getLastDetailForUpdate();

        deleteInventoryAdjustmentTypeDescriptionsByInventoryAdjustmentType(inventoryAdjustmentType, deletedBy);
        // TODO: deleteInventoryTransactionsByInventoryAdjustmentType(inventoryAdjustmentType, deletedBy);

        inventoryAdjustmentTypeDetail.setThruTime(session.getStartTime());
        inventoryAdjustmentType.setActiveDetail(null);
        inventoryAdjustmentType.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var defaultInventoryAdjustmentType = getDefaultInventoryAdjustmentType();
            if(defaultInventoryAdjustmentType == null) {
                var inventoryAdjustmentTypes = getInventoryAdjustmentTypesForUpdate();

                if(!inventoryAdjustmentTypes.isEmpty()) {
                    var iter = inventoryAdjustmentTypes.iterator();
                    if(iter.hasNext()) {
                        defaultInventoryAdjustmentType = iter.next();
                    }
                    var inventoryAdjustmentTypeDetailValue = Objects.requireNonNull(defaultInventoryAdjustmentType).getLastDetailForUpdate().getInventoryAdjustmentTypeDetailValue().clone();

                    inventoryAdjustmentTypeDetailValue.setIsDefault(true);
                    updateInventoryAdjustmentTypeFromValue(inventoryAdjustmentTypeDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(inventoryAdjustmentType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteInventoryAdjustmentType(InventoryAdjustmentType inventoryAdjustmentType, BasePK deletedBy) {
        deleteInventoryAdjustmentType(inventoryAdjustmentType, true, deletedBy);
    }

    private void deleteInventoryAdjustmentTypes(List<InventoryAdjustmentType> inventoryAdjustmentTypes, boolean checkDefault, BasePK deletedBy) {
        inventoryAdjustmentTypes.forEach((inventoryAdjustmentType) -> deleteInventoryAdjustmentType(inventoryAdjustmentType, checkDefault, deletedBy));
    }

    public void deleteInventoryAdjustmentTypes(List<InventoryAdjustmentType> inventoryAdjustmentTypes, BasePK deletedBy) {
        deleteInventoryAdjustmentTypes(inventoryAdjustmentTypes, true, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Inventory Adjustment Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected InventoryAdjustmentTypeDescriptionFactory inventoryAdjustmentTypeDescriptionFactory;

    public InventoryAdjustmentTypeDescription createInventoryAdjustmentTypeDescription(InventoryAdjustmentType inventoryAdjustmentType, Language language, String description, BasePK createdBy) {
        var inventoryAdjustmentTypeDescription = inventoryAdjustmentTypeDescriptionFactory.create(inventoryAdjustmentType, language, description,
                session.getStartTime(), Session.MAX_TIME);

        sendEvent(inventoryAdjustmentType.getPrimaryKey(), EventTypes.MODIFY, inventoryAdjustmentTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return inventoryAdjustmentTypeDescription;
    }

    private InventoryAdjustmentTypeDescription getInventoryAdjustmentTypeDescription(final InventoryAdjustmentType inventoryAdjustmentType,
            final Language language, final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryAdjustmentTypeDescriptions.fields())
                .from(InventoryAdjustmentTypeDescriptions)
                .where(InventoryAdjustmentTypeDescriptions.INVENTORY_ADJUSTMENT_TYPE.eq(inventoryAdjustmentType.getPrimaryKey()),
                        InventoryAdjustmentTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        InventoryAdjustmentTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return inventoryAdjustmentTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }

    public InventoryAdjustmentTypeDescription getInventoryAdjustmentTypeDescription(InventoryAdjustmentType inventoryAdjustmentType, Language language) {
        return getInventoryAdjustmentTypeDescription(inventoryAdjustmentType, language, EntityPermission.READ_ONLY);
    }

    public InventoryAdjustmentTypeDescription getInventoryAdjustmentTypeDescriptionForUpdate(InventoryAdjustmentType inventoryAdjustmentType, Language language) {
        return getInventoryAdjustmentTypeDescription(inventoryAdjustmentType, language, EntityPermission.READ_WRITE);
    }

    public InventoryAdjustmentTypeDescriptionValue getInventoryAdjustmentTypeDescriptionValue(InventoryAdjustmentTypeDescription inventoryAdjustmentTypeDescription) {
        return inventoryAdjustmentTypeDescription == null ? null : inventoryAdjustmentTypeDescription.getInventoryAdjustmentTypeDescriptionValue().clone();
    }

    public InventoryAdjustmentTypeDescriptionValue getInventoryAdjustmentTypeDescriptionValueForUpdate(InventoryAdjustmentType inventoryAdjustmentType, Language language) {
        return getInventoryAdjustmentTypeDescriptionValue(getInventoryAdjustmentTypeDescriptionForUpdate(inventoryAdjustmentType, language));
    }

    private List<InventoryAdjustmentTypeDescription> getInventoryAdjustmentTypeDescriptionsByInventoryAdjustmentType(
            final InventoryAdjustmentType inventoryAdjustmentType, final EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(InventoryAdjustmentTypeDescriptions.fields())
                    .from(InventoryAdjustmentTypeDescriptions)
                    .join(Languages)
                    .on(InventoryAdjustmentTypeDescriptions.LANGUAGE.eq(Languages.LANGUAGE))
                    .where(InventoryAdjustmentTypeDescriptions.INVENTORY_ADJUSTMENT_TYPE.eq(inventoryAdjustmentType.getPrimaryKey()),
                            InventoryAdjustmentTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    InventoryAdjustmentTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(InventoryAdjustmentTypeDescriptions.fields())
                    .from(InventoryAdjustmentTypeDescriptions)
                    .where(InventoryAdjustmentTypeDescriptions.INVENTORY_ADJUSTMENT_TYPE.eq(inventoryAdjustmentType.getPrimaryKey()),
                            InventoryAdjustmentTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return inventoryAdjustmentTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<InventoryAdjustmentTypeDescription> getInventoryAdjustmentTypeDescriptionsByInventoryAdjustmentType(InventoryAdjustmentType inventoryAdjustmentType) {
        return getInventoryAdjustmentTypeDescriptionsByInventoryAdjustmentType(inventoryAdjustmentType, EntityPermission.READ_ONLY);
    }

    public List<InventoryAdjustmentTypeDescription> getInventoryAdjustmentTypeDescriptionsByInventoryAdjustmentTypeForUpdate(InventoryAdjustmentType inventoryAdjustmentType) {
        return getInventoryAdjustmentTypeDescriptionsByInventoryAdjustmentType(inventoryAdjustmentType, EntityPermission.READ_WRITE);
    }

    public String getBestInventoryAdjustmentTypeDescription(InventoryAdjustmentType inventoryAdjustmentType, Language language) {
        String description;
        var inventoryAdjustmentTypeDescription = getInventoryAdjustmentTypeDescription(inventoryAdjustmentType, language);

        if(inventoryAdjustmentTypeDescription == null && !language.getIsDefault()) {
            inventoryAdjustmentTypeDescription = getInventoryAdjustmentTypeDescription(inventoryAdjustmentType, partyControl.getDefaultLanguage());
        }

        if(inventoryAdjustmentTypeDescription == null) {
            description = inventoryAdjustmentType.getLastDetail().getInventoryAdjustmentTypeName();
        } else {
            description = inventoryAdjustmentTypeDescription.getDescription();
        }

        return description;
    }

    public InventoryAdjustmentTypeDescriptionTransfer getInventoryAdjustmentTypeDescriptionTransfer(UserVisit userVisit, InventoryAdjustmentTypeDescription inventoryAdjustmentTypeDescription) {
        return inventoryAdjustmentTypeDescriptionTransferCache.getTransfer(userVisit, inventoryAdjustmentTypeDescription);
    }

    public List<InventoryAdjustmentTypeDescriptionTransfer> getInventoryAdjustmentTypeDescriptionTransfersByInventoryAdjustmentType(UserVisit userVisit, InventoryAdjustmentType inventoryAdjustmentType) {
        var inventoryAdjustmentTypeDescriptions = getInventoryAdjustmentTypeDescriptionsByInventoryAdjustmentType(inventoryAdjustmentType);
        List<InventoryAdjustmentTypeDescriptionTransfer> inventoryAdjustmentTypeDescriptionTransfers = new ArrayList<>(inventoryAdjustmentTypeDescriptions.size());

        inventoryAdjustmentTypeDescriptions.forEach((inventoryAdjustmentTypeDescription) ->
                inventoryAdjustmentTypeDescriptionTransfers.add(inventoryAdjustmentTypeDescriptionTransferCache.getTransfer(userVisit, inventoryAdjustmentTypeDescription))
        );

        return inventoryAdjustmentTypeDescriptionTransfers;
    }

    public void updateInventoryAdjustmentTypeDescriptionFromValue(InventoryAdjustmentTypeDescriptionValue inventoryAdjustmentTypeDescriptionValue, BasePK updatedBy) {
        if(inventoryAdjustmentTypeDescriptionValue.hasBeenModified()) {
            var inventoryAdjustmentTypeDescription = inventoryAdjustmentTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    inventoryAdjustmentTypeDescriptionValue.getPrimaryKey());

            inventoryAdjustmentTypeDescription.setThruTime(session.getStartTime());
            inventoryAdjustmentTypeDescription.store();

            var inventoryAdjustmentType = inventoryAdjustmentTypeDescription.getInventoryAdjustmentType();
            var language = inventoryAdjustmentTypeDescription.getLanguage();
            var description = inventoryAdjustmentTypeDescriptionValue.getDescription();

            inventoryAdjustmentTypeDescription = inventoryAdjustmentTypeDescriptionFactory.create(inventoryAdjustmentType, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(inventoryAdjustmentType.getPrimaryKey(), EventTypes.MODIFY, inventoryAdjustmentTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteInventoryAdjustmentTypeDescription(InventoryAdjustmentTypeDescription inventoryAdjustmentTypeDescription, BasePK deletedBy) {
        inventoryAdjustmentTypeDescription.setThruTime(session.getStartTime());

        sendEvent(inventoryAdjustmentTypeDescription.getInventoryAdjustmentTypePK(), EventTypes.MODIFY, inventoryAdjustmentTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteInventoryAdjustmentTypeDescriptionsByInventoryAdjustmentType(InventoryAdjustmentType inventoryAdjustmentType, BasePK deletedBy) {
        var inventoryAdjustmentTypeDescriptions = getInventoryAdjustmentTypeDescriptionsByInventoryAdjustmentTypeForUpdate(inventoryAdjustmentType);

        inventoryAdjustmentTypeDescriptions.forEach((inventoryAdjustmentTypeDescription) ->
                deleteInventoryAdjustmentTypeDescription(inventoryAdjustmentTypeDescription, deletedBy)
        );
    }

}
