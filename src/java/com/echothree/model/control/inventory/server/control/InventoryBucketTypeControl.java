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
import com.echothree.model.control.inventory.common.choice.InventoryBucketTypeChoicesBean;
import com.echothree.model.control.inventory.common.transfer.InventoryBucketTypeDescriptionTransfer;
import com.echothree.model.control.inventory.common.transfer.InventoryBucketTypeTransfer;
import com.echothree.model.control.inventory.server.transfer.InventoryBucketTypeDescriptionTransferCache;
import com.echothree.model.control.inventory.server.transfer.InventoryBucketTypeTransferCache;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.inventory.common.pk.InventoryBucketTypePK;
import com.echothree.model.data.inventory.server.entity.InventoryBucketType;
import com.echothree.model.data.inventory.server.entity.InventoryBucketTypeDescription;
import com.echothree.model.data.inventory.server.factory.InventoryBucketTypeDescriptionFactory;
import com.echothree.model.data.inventory.server.factory.InventoryBucketTypeDetailFactory;
import com.echothree.model.data.inventory.server.factory.InventoryBucketTypeFactory;
import com.echothree.model.data.inventory.server.value.InventoryBucketTypeDescriptionValue;
import com.echothree.model.data.inventory.server.value.InventoryBucketTypeDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.inventory.InventoryForeignKeys.INVENTORY_BUCKET_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.tables.inventory.InventoryBucketTypeDescriptions.InventoryBucketTypeDescriptions;
import static com.echothree.model.jooq.server.tables.inventory.InventoryBucketTypeDetails.InventoryBucketTypeDetails;
import static com.echothree.model.jooq.server.tables.inventory.InventoryBucketTypes.InventoryBucketTypes;
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
public class InventoryBucketTypeControl
        extends BaseModelControl {

    @Inject
    BucketControl bucketControl;

    @Inject
    InventoryBucketTypeTransferCache inventoryBucketTypeTransferCache;

    @Inject
    InventoryBucketTypeDescriptionTransferCache inventoryBucketTypeDescriptionTransferCache;

    /**
     * Creates a new instance of InventoryBucketTypeControl
     */
    protected InventoryBucketTypeControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Inventory Adjustment Types
    // --------------------------------------------------------------------------------

    @Inject
    protected InventoryBucketTypeFactory inventoryBucketTypeFactory;

    @Inject
    protected InventoryBucketTypeDetailFactory inventoryBucketTypeDetailFactory;

    public InventoryBucketType createInventoryBucketType(String inventoryBucketTypeName,
            Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultInventoryBucketType = getDefaultInventoryBucketType();
        var defaultFound = defaultInventoryBucketType != null;

        if(defaultFound && isDefault) {
            var defaultInventoryBucketTypeDetailValue = getDefaultInventoryBucketTypeDetailValueForUpdate();

            defaultInventoryBucketTypeDetailValue.setIsDefault(false);
            updateInventoryBucketTypeFromValue(defaultInventoryBucketTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var inventoryBucketType = inventoryBucketTypeFactory.create();
        var inventoryBucketTypeDetail = inventoryBucketTypeDetailFactory.create(inventoryBucketType,
                inventoryBucketTypeName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        inventoryBucketType = inventoryBucketTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                inventoryBucketType.getPrimaryKey());
        inventoryBucketType.setActiveDetail(inventoryBucketTypeDetail);
        inventoryBucketType.setLastDetail(inventoryBucketTypeDetail);
        inventoryBucketType.store();

        sendEvent(inventoryBucketType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return inventoryBucketType;
    }

    /**
     * Assume that the entityInstance passed to this function is a ECHO_THREE.InventoryBucketType
     */
    public InventoryBucketType getInventoryBucketTypeByEntityInstance(final EntityInstance entityInstance,
            final EntityPermission entityPermission) {
        var pk = new InventoryBucketTypePK(entityInstance.getEntityUniqueId());

        return inventoryBucketTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public InventoryBucketType getInventoryBucketTypeByEntityInstance(final EntityInstance entityInstance) {
        return getInventoryBucketTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public InventoryBucketType getInventoryBucketTypeByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getInventoryBucketTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public InventoryBucketType getInventoryBucketTypeByPK(InventoryBucketTypePK pk) {
        return inventoryBucketTypeFactory.getEntityFromPK(EntityPermission.READ_ONLY, pk);
    }

    public long countInventoryBucketTypes() {
        return session.getDslContext()
                .selectCount()
                .from(InventoryBucketTypes)
                .join(InventoryBucketTypeDetails).onKey(INVENTORY_BUCKET_TYPES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public InventoryBucketType getInventoryBucketTypeByName(final String inventoryBucketTypeName,
            final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryBucketTypes.fields())
                .from(InventoryBucketTypes)
                .join(InventoryBucketTypeDetails).onKey(INVENTORY_BUCKET_TYPES_ACTIVE_DETAIL_FK)
                .where(InventoryBucketTypeDetails.INVENTORY_BUCKET_TYPE_NAME.eq(inventoryBucketTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return inventoryBucketTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public InventoryBucketType getInventoryBucketTypeByName(String inventoryBucketTypeName) {
        return getInventoryBucketTypeByName(inventoryBucketTypeName, EntityPermission.READ_ONLY);
    }

    public InventoryBucketType getInventoryBucketTypeByNameForUpdate(String inventoryBucketTypeName) {
        return getInventoryBucketTypeByName(inventoryBucketTypeName, EntityPermission.READ_WRITE);
    }

    public InventoryBucketTypeDetailValue getInventoryBucketTypeDetailValueForUpdate(InventoryBucketType inventoryBucketType) {
        return inventoryBucketType == null ? null : inventoryBucketType.getLastDetailForUpdate().getInventoryBucketTypeDetailValue().clone();
    }

    public InventoryBucketTypeDetailValue getInventoryBucketTypeDetailValueByNameForUpdate(String inventoryBucketTypeName) {
        return getInventoryBucketTypeDetailValueForUpdate(getInventoryBucketTypeByNameForUpdate(inventoryBucketTypeName));
    }

    public InventoryBucketType getDefaultInventoryBucketType(final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryBucketTypes.fields())
                .from(InventoryBucketTypes)
                .join(InventoryBucketTypeDetails).onKey(INVENTORY_BUCKET_TYPES_ACTIVE_DETAIL_FK)
                .where(InventoryBucketTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return inventoryBucketTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public InventoryBucketType getDefaultInventoryBucketType() {
        return getDefaultInventoryBucketType(EntityPermission.READ_ONLY);
    }

    public InventoryBucketType getDefaultInventoryBucketTypeForUpdate() {
        return getDefaultInventoryBucketType(EntityPermission.READ_WRITE);
    }

    public InventoryBucketTypeDetailValue getDefaultInventoryBucketTypeDetailValueForUpdate() {
        return getDefaultInventoryBucketTypeForUpdate().getLastDetailForUpdate().getInventoryBucketTypeDetailValue().clone();
    }

    private List<InventoryBucketType> getInventoryBucketTypes(final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryBucketTypes.fields())
                .from(InventoryBucketTypes)
                .join(InventoryBucketTypeDetails).onKey(INVENTORY_BUCKET_TYPES_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(InventoryBucketTypeDetails.SORT_ORDER, InventoryBucketTypeDetails.INVENTORY_BUCKET_TYPE_NAME),
                    InventoryBucketTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return inventoryBucketTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<InventoryBucketType> getInventoryBucketTypes() {
        return getInventoryBucketTypes(EntityPermission.READ_ONLY);
    }

    public List<InventoryBucketType> getInventoryBucketTypesForUpdate() {
        return getInventoryBucketTypes(EntityPermission.READ_WRITE);
    }

    public InventoryBucketTypeTransfer getInventoryBucketTypeTransfer(UserVisit userVisit, InventoryBucketType inventoryBucketType) {
        return inventoryBucketTypeTransferCache.getTransfer(userVisit, inventoryBucketType);
    }

    public List<InventoryBucketTypeTransfer> getInventoryBucketTypeTransfers(UserVisit userVisit, Collection<InventoryBucketType> inventoryBucketTypes) {
        List<InventoryBucketTypeTransfer> inventoryBucketTypeTransfers = new ArrayList<>(inventoryBucketTypes.size());

        inventoryBucketTypes.forEach((inventoryBucketType) ->
                inventoryBucketTypeTransfers.add(inventoryBucketTypeTransferCache.getTransfer(userVisit, inventoryBucketType))
        );

        return inventoryBucketTypeTransfers;
    }

    public List<InventoryBucketTypeTransfer> getInventoryBucketTypeTransfers(UserVisit userVisit) {
        return getInventoryBucketTypeTransfers(userVisit, getInventoryBucketTypes());
    }

    public InventoryBucketTypeChoicesBean getInventoryBucketTypeChoices(String defaultInventoryBucketTypeChoice,
            Language language, boolean allowNullChoice) {
        var inventoryBucketTypes = getInventoryBucketTypes();
        var size = inventoryBucketTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultInventoryBucketTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var inventoryBucketType : inventoryBucketTypes) {
            var inventoryBucketTypeDetail = inventoryBucketType.getLastDetail();

            var label = getBestInventoryBucketTypeDescription(inventoryBucketType, language);
            var value = inventoryBucketTypeDetail.getInventoryBucketTypeName();

            labels.add(label == null ? value : label);
            values.add(value);

            var usingDefaultChoice = defaultInventoryBucketTypeChoice != null && defaultInventoryBucketTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && inventoryBucketTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new InventoryBucketTypeChoicesBean(labels, values, defaultValue);
    }

    private void updateInventoryBucketTypeFromValue(InventoryBucketTypeDetailValue inventoryBucketTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(inventoryBucketTypeDetailValue.hasBeenModified()) {
            var inventoryBucketType = inventoryBucketTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    inventoryBucketTypeDetailValue.getInventoryBucketTypePK());
            var inventoryBucketTypeDetail = inventoryBucketType.getActiveDetailForUpdate();

            inventoryBucketTypeDetail.setThruTime(session.getStartTime());
            inventoryBucketTypeDetail.store();

            var inventoryBucketTypePK = inventoryBucketTypeDetail.getInventoryBucketTypePK(); // Not updated
            var inventoryBucketTypeName = inventoryBucketTypeDetailValue.getInventoryBucketTypeName();
            var isDefault = inventoryBucketTypeDetailValue.getIsDefault();
            var sortOrder = inventoryBucketTypeDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultInventoryBucketType = getDefaultInventoryBucketType();
                var defaultFound = defaultInventoryBucketType != null && !defaultInventoryBucketType.equals(inventoryBucketType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultInventoryBucketTypeDetailValue = getDefaultInventoryBucketTypeDetailValueForUpdate();

                    defaultInventoryBucketTypeDetailValue.setIsDefault(false);
                    updateInventoryBucketTypeFromValue(defaultInventoryBucketTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            inventoryBucketTypeDetail = inventoryBucketTypeDetailFactory.create(inventoryBucketTypePK,
                    inventoryBucketTypeName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

            inventoryBucketType.setActiveDetail(inventoryBucketTypeDetail);
            inventoryBucketType.setLastDetail(inventoryBucketTypeDetail);

            sendEvent(inventoryBucketTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateInventoryBucketTypeFromValue(InventoryBucketTypeDetailValue inventoryBucketTypeDetailValue, BasePK updatedBy) {
        updateInventoryBucketTypeFromValue(inventoryBucketTypeDetailValue, true, updatedBy);
    }

    private void deleteInventoryBucketType(InventoryBucketType inventoryBucketType, boolean checkDefault, BasePK deletedBy) {
        var inventoryBucketTypeDetail = inventoryBucketType.getLastDetailForUpdate();

        deleteInventoryBucketTypeDescriptionsByInventoryBucketType(inventoryBucketType, deletedBy);
        // TODO: deleteInventoryTransactionsByInventoryBucketType(inventoryBucketType, deletedBy);
        bucketControl.removePartyBucketsByInventoryBucketType(inventoryBucketType, deletedBy);
        bucketControl.removeInventoryLocationBucketsByInventoryBucketType(inventoryBucketType, deletedBy);

        inventoryBucketTypeDetail.setThruTime(session.getStartTime());
        inventoryBucketType.setActiveDetail(null);
        inventoryBucketType.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var defaultInventoryBucketType = getDefaultInventoryBucketType();
            if(defaultInventoryBucketType == null) {
                var inventoryBucketTypes = getInventoryBucketTypesForUpdate();

                if(!inventoryBucketTypes.isEmpty()) {
                    var iter = inventoryBucketTypes.iterator();
                    if(iter.hasNext()) {
                        defaultInventoryBucketType = iter.next();
                    }
                    var inventoryBucketTypeDetailValue = Objects.requireNonNull(defaultInventoryBucketType).getLastDetailForUpdate().getInventoryBucketTypeDetailValue().clone();

                    inventoryBucketTypeDetailValue.setIsDefault(true);
                    updateInventoryBucketTypeFromValue(inventoryBucketTypeDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(inventoryBucketType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteInventoryBucketType(InventoryBucketType inventoryBucketType, BasePK deletedBy) {
        deleteInventoryBucketType(inventoryBucketType, true, deletedBy);
    }

    private void deleteInventoryBucketTypes(List<InventoryBucketType> inventoryBucketTypes, boolean checkDefault, BasePK deletedBy) {
        inventoryBucketTypes.forEach((inventoryBucketType) -> deleteInventoryBucketType(inventoryBucketType, checkDefault, deletedBy));
    }

    public void deleteInventoryBucketTypes(List<InventoryBucketType> inventoryBucketTypes, BasePK deletedBy) {
        deleteInventoryBucketTypes(inventoryBucketTypes, true, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Inventory Adjustment Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected InventoryBucketTypeDescriptionFactory inventoryBucketTypeDescriptionFactory;

    public InventoryBucketTypeDescription createInventoryBucketTypeDescription(InventoryBucketType inventoryBucketType, Language language, String description, BasePK createdBy) {
        var inventoryBucketTypeDescription = inventoryBucketTypeDescriptionFactory.create(inventoryBucketType, language, description,
                session.getStartTime(), Session.MAX_TIME);

        sendEvent(inventoryBucketType.getPrimaryKey(), EventTypes.MODIFY, inventoryBucketTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return inventoryBucketTypeDescription;
    }

    private InventoryBucketTypeDescription getInventoryBucketTypeDescription(final InventoryBucketType inventoryBucketType,
            final Language language, final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryBucketTypeDescriptions.fields())
                .from(InventoryBucketTypeDescriptions)
                .where(InventoryBucketTypeDescriptions.INVENTORY_BUCKET_TYPE.eq(inventoryBucketType.getPrimaryKey()),
                        InventoryBucketTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        InventoryBucketTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return inventoryBucketTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }

    public InventoryBucketTypeDescription getInventoryBucketTypeDescription(InventoryBucketType inventoryBucketType, Language language) {
        return getInventoryBucketTypeDescription(inventoryBucketType, language, EntityPermission.READ_ONLY);
    }

    public InventoryBucketTypeDescription getInventoryBucketTypeDescriptionForUpdate(InventoryBucketType inventoryBucketType, Language language) {
        return getInventoryBucketTypeDescription(inventoryBucketType, language, EntityPermission.READ_WRITE);
    }

    public InventoryBucketTypeDescriptionValue getInventoryBucketTypeDescriptionValue(InventoryBucketTypeDescription inventoryBucketTypeDescription) {
        return inventoryBucketTypeDescription == null ? null : inventoryBucketTypeDescription.getInventoryBucketTypeDescriptionValue().clone();
    }

    public InventoryBucketTypeDescriptionValue getInventoryBucketTypeDescriptionValueForUpdate(InventoryBucketType inventoryBucketType, Language language) {
        return getInventoryBucketTypeDescriptionValue(getInventoryBucketTypeDescriptionForUpdate(inventoryBucketType, language));
    }

    private List<InventoryBucketTypeDescription> getInventoryBucketTypeDescriptionsByInventoryBucketType(
            final InventoryBucketType inventoryBucketType, final EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(InventoryBucketTypeDescriptions.fields())
                    .from(InventoryBucketTypeDescriptions)
                    .join(Languages)
                    .on(InventoryBucketTypeDescriptions.LANGUAGE.eq(Languages.LANGUAGE))
                    .where(InventoryBucketTypeDescriptions.INVENTORY_BUCKET_TYPE.eq(inventoryBucketType.getPrimaryKey()),
                            InventoryBucketTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    InventoryBucketTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(InventoryBucketTypeDescriptions.fields())
                    .from(InventoryBucketTypeDescriptions)
                    .where(InventoryBucketTypeDescriptions.INVENTORY_BUCKET_TYPE.eq(inventoryBucketType.getPrimaryKey()),
                            InventoryBucketTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return inventoryBucketTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<InventoryBucketTypeDescription> getInventoryBucketTypeDescriptionsByInventoryBucketType(InventoryBucketType inventoryBucketType) {
        return getInventoryBucketTypeDescriptionsByInventoryBucketType(inventoryBucketType, EntityPermission.READ_ONLY);
    }

    public List<InventoryBucketTypeDescription> getInventoryBucketTypeDescriptionsByInventoryBucketTypeForUpdate(InventoryBucketType inventoryBucketType) {
        return getInventoryBucketTypeDescriptionsByInventoryBucketType(inventoryBucketType, EntityPermission.READ_WRITE);
    }

    public String getBestInventoryBucketTypeDescription(InventoryBucketType inventoryBucketType, Language language) {
        String description;
        var inventoryBucketTypeDescription = getInventoryBucketTypeDescription(inventoryBucketType, language);

        if(inventoryBucketTypeDescription == null && !language.getIsDefault()) {
            inventoryBucketTypeDescription = getInventoryBucketTypeDescription(inventoryBucketType, partyControl.getDefaultLanguage());
        }

        if(inventoryBucketTypeDescription == null) {
            description = inventoryBucketType.getLastDetail().getInventoryBucketTypeName();
        } else {
            description = inventoryBucketTypeDescription.getDescription();
        }

        return description;
    }

    public InventoryBucketTypeDescriptionTransfer getInventoryBucketTypeDescriptionTransfer(UserVisit userVisit, InventoryBucketTypeDescription inventoryBucketTypeDescription) {
        return inventoryBucketTypeDescriptionTransferCache.getTransfer(userVisit, inventoryBucketTypeDescription);
    }

    public List<InventoryBucketTypeDescriptionTransfer> getInventoryBucketTypeDescriptionTransfersByInventoryBucketType(UserVisit userVisit, InventoryBucketType inventoryBucketType) {
        var inventoryBucketTypeDescriptions = getInventoryBucketTypeDescriptionsByInventoryBucketType(inventoryBucketType);
        List<InventoryBucketTypeDescriptionTransfer> inventoryBucketTypeDescriptionTransfers = new ArrayList<>(inventoryBucketTypeDescriptions.size());

        inventoryBucketTypeDescriptions.forEach((inventoryBucketTypeDescription) ->
                inventoryBucketTypeDescriptionTransfers.add(inventoryBucketTypeDescriptionTransferCache.getTransfer(userVisit, inventoryBucketTypeDescription))
        );

        return inventoryBucketTypeDescriptionTransfers;
    }

    public void updateInventoryBucketTypeDescriptionFromValue(InventoryBucketTypeDescriptionValue inventoryBucketTypeDescriptionValue, BasePK updatedBy) {
        if(inventoryBucketTypeDescriptionValue.hasBeenModified()) {
            var inventoryBucketTypeDescription = inventoryBucketTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    inventoryBucketTypeDescriptionValue.getPrimaryKey());

            inventoryBucketTypeDescription.setThruTime(session.getStartTime());
            inventoryBucketTypeDescription.store();

            var inventoryBucketType = inventoryBucketTypeDescription.getInventoryBucketType();
            var language = inventoryBucketTypeDescription.getLanguage();
            var description = inventoryBucketTypeDescriptionValue.getDescription();

            inventoryBucketTypeDescription = inventoryBucketTypeDescriptionFactory.create(inventoryBucketType, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(inventoryBucketType.getPrimaryKey(), EventTypes.MODIFY, inventoryBucketTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteInventoryBucketTypeDescription(InventoryBucketTypeDescription inventoryBucketTypeDescription, BasePK deletedBy) {
        inventoryBucketTypeDescription.setThruTime(session.getStartTime());

        sendEvent(inventoryBucketTypeDescription.getInventoryBucketTypePK(), EventTypes.MODIFY, inventoryBucketTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteInventoryBucketTypeDescriptionsByInventoryBucketType(InventoryBucketType inventoryBucketType, BasePK deletedBy) {
        var inventoryBucketTypeDescriptions = getInventoryBucketTypeDescriptionsByInventoryBucketTypeForUpdate(inventoryBucketType);

        inventoryBucketTypeDescriptions.forEach((inventoryBucketTypeDescription) ->
                deleteInventoryBucketTypeDescription(inventoryBucketTypeDescription, deletedBy)
        );
    }

}
