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
import com.echothree.model.control.inventory.common.choice.InventoryDispositionAdjustmentChoicesBean;
import com.echothree.model.control.inventory.common.transfer.InventoryDispositionAdjustmentDescriptionTransfer;
import com.echothree.model.control.inventory.common.transfer.InventoryDispositionAdjustmentTransfer;
import com.echothree.model.control.inventory.server.transfer.InventoryDispositionAdjustmentDescriptionTransferCache;
import com.echothree.model.control.inventory.server.transfer.InventoryDispositionAdjustmentTransferCache;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.inventory.common.pk.InventoryDispositionAdjustmentPK;
import com.echothree.model.data.inventory.server.entity.InventoryDispositionAdjustment;
import com.echothree.model.data.inventory.server.entity.InventoryDispositionAdjustmentDescription;
import com.echothree.model.data.inventory.server.entity.InventoryDisposition;
import com.echothree.model.data.inventory.server.entity.InventoryAdjustmentType;
import com.echothree.model.data.inventory.server.entity.InventoryBucketType;
import com.echothree.model.data.inventory.server.factory.InventoryDispositionAdjustmentDescriptionFactory;
import com.echothree.model.data.inventory.server.factory.InventoryDispositionAdjustmentDetailFactory;
import com.echothree.model.data.inventory.server.factory.InventoryDispositionAdjustmentFactory;
import com.echothree.model.data.inventory.server.value.InventoryDispositionAdjustmentDescriptionValue;
import com.echothree.model.data.inventory.server.value.InventoryDispositionAdjustmentDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.inventory.InventoryForeignKeys.INVENTORY_DISPOSITION_ADJUSTMENTS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.tables.inventory.InventoryDispositionAdjustmentDescriptions.InventoryDispositionAdjustmentDescriptions;
import static com.echothree.model.jooq.server.tables.inventory.InventoryDispositionAdjustmentDetails.InventoryDispositionAdjustmentDetails;
import static com.echothree.model.jooq.server.tables.inventory.InventoryDispositionAdjustments.InventoryDispositionAdjustments;
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
public class InventoryDispositionAdjustmentControl
        extends BaseModelControl {

    /** Creates a new instance of InventoryDispositionAdjustmentControl */
    protected InventoryDispositionAdjustmentControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Inventory Disposition Adjustments
    // --------------------------------------------------------------------------------

    @Inject
    InventoryDispositionAdjustmentFactory inventoryDispositionAdjustmentFactory;

    @Inject
    InventoryDispositionAdjustmentDetailFactory inventoryDispositionAdjustmentDetailFactory;

    @Inject
    InventoryDispositionAdjustmentTransferCache inventoryDispositionAdjustmentTransferCache;

    public InventoryDispositionAdjustment createInventoryDispositionAdjustment(InventoryDisposition inventoryDisposition,
            String inventoryDispositionAdjustmentName, InventoryAdjustmentType inventoryAdjustmentType,
            InventoryBucketType inventoryBucketType, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultInventoryDispositionAdjustment = getDefaultInventoryDispositionAdjustment(inventoryDisposition);
        var defaultFound = defaultInventoryDispositionAdjustment != null;

        if(defaultFound && isDefault) {
            var defaultInventoryDispositionAdjustmentDetailValue = getDefaultInventoryDispositionAdjustmentDetailValueForUpdate(inventoryDisposition);

            defaultInventoryDispositionAdjustmentDetailValue.setIsDefault(false);
            updateInventoryDispositionAdjustmentFromValue(defaultInventoryDispositionAdjustmentDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var inventoryDispositionAdjustment = inventoryDispositionAdjustmentFactory.create();
        var inventoryDispositionAdjustmentDetail = inventoryDispositionAdjustmentDetailFactory.create(inventoryDispositionAdjustment,
                inventoryDisposition, inventoryDispositionAdjustmentName, inventoryAdjustmentType, inventoryBucketType, isDefault,
                sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        inventoryDispositionAdjustment = inventoryDispositionAdjustmentFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                inventoryDispositionAdjustment.getPrimaryKey());
        inventoryDispositionAdjustment.setActiveDetail(inventoryDispositionAdjustmentDetail);
        inventoryDispositionAdjustment.setLastDetail(inventoryDispositionAdjustmentDetail);
        inventoryDispositionAdjustment.store();

        sendEvent(inventoryDispositionAdjustment.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return inventoryDispositionAdjustment;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.InventoryDispositionAdjustment */
    public InventoryDispositionAdjustment getInventoryDispositionAdjustmentByEntityInstance(final EntityInstance entityInstance,
            final EntityPermission entityPermission) {
        var pk = new InventoryDispositionAdjustmentPK(entityInstance.getEntityUniqueId());

        return inventoryDispositionAdjustmentFactory.getEntityFromPK(entityPermission, pk);
    }

    public InventoryDispositionAdjustment getInventoryDispositionAdjustmentByEntityInstance(final EntityInstance entityInstance) {
        return getInventoryDispositionAdjustmentByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public InventoryDispositionAdjustment getInventoryDispositionAdjustmentByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getInventoryDispositionAdjustmentByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public InventoryDispositionAdjustment getInventoryDispositionAdjustmentByPK(InventoryDispositionAdjustmentPK pk) {
        return inventoryDispositionAdjustmentFactory.getEntityFromPK(EntityPermission.READ_ONLY, pk);
    }

    public long countInventoryDispositionAdjustmentsByInventoryDisposition(InventoryDisposition inventoryDisposition) {
        return session.getDslContext()
                .selectCount()
                .from(InventoryDispositionAdjustments)
                .join(InventoryDispositionAdjustmentDetails).onKey(INVENTORY_DISPOSITION_ADJUSTMENTS_ACTIVE_DETAIL_FK)
                .where(InventoryDispositionAdjustmentDetails.INVENTORY_DISPOSITION.eq(inventoryDisposition.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countInventoryDispositionAdjustmentsByInventoryAdjustmentType(InventoryAdjustmentType inventoryAdjustmentType) {
        return session.getDslContext().selectCount().from(InventoryDispositionAdjustments)
                .join(InventoryDispositionAdjustmentDetails).onKey(INVENTORY_DISPOSITION_ADJUSTMENTS_ACTIVE_DETAIL_FK)
                .where(InventoryDispositionAdjustmentDetails.INVENTORY_ADJUSTMENT_TYPE.eq(inventoryAdjustmentType.getPrimaryKey()))
                .fetchOptional(0, Long.class).orElse(0L);
    }

    public long countInventoryDispositionAdjustmentsByInventoryBucketType(InventoryBucketType inventoryBucketType) {
        return session.getDslContext().selectCount().from(InventoryDispositionAdjustments)
                .join(InventoryDispositionAdjustmentDetails).onKey(INVENTORY_DISPOSITION_ADJUSTMENTS_ACTIVE_DETAIL_FK)
                .where(InventoryDispositionAdjustmentDetails.INVENTORY_BUCKET_TYPE.eq(inventoryBucketType.getPrimaryKey()))
                .fetchOptional(0, Long.class).orElse(0L);
    }

    public InventoryDispositionAdjustment getInventoryDispositionAdjustmentByName(InventoryDisposition inventoryDisposition,
            String inventoryDispositionAdjustmentName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryDispositionAdjustments.fields())
                .from(InventoryDispositionAdjustments)
                .join(InventoryDispositionAdjustmentDetails).onKey(INVENTORY_DISPOSITION_ADJUSTMENTS_ACTIVE_DETAIL_FK)
                .where(InventoryDispositionAdjustmentDetails.INVENTORY_DISPOSITION.eq(inventoryDisposition.getPrimaryKey()),
                        InventoryDispositionAdjustmentDetails.INVENTORY_DISPOSITION_ADJUSTMENT_NAME.eq(inventoryDispositionAdjustmentName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return inventoryDispositionAdjustmentFactory.getEntityFromQuery(entityPermission, query);
    }

    public InventoryDispositionAdjustment getInventoryDispositionAdjustmentByName(InventoryDisposition inventoryDisposition,
            String inventoryDispositionAdjustmentName) {
        return getInventoryDispositionAdjustmentByName(inventoryDisposition, inventoryDispositionAdjustmentName, EntityPermission.READ_ONLY);
    }

    public InventoryDispositionAdjustment getInventoryDispositionAdjustmentByNameForUpdate(InventoryDisposition inventoryDisposition,
            String inventoryDispositionAdjustmentName) {
        return getInventoryDispositionAdjustmentByName(inventoryDisposition, inventoryDispositionAdjustmentName, EntityPermission.READ_WRITE);
    }

    public InventoryDispositionAdjustmentDetailValue getInventoryDispositionAdjustmentDetailValueForUpdate(
            InventoryDispositionAdjustment inventoryDispositionAdjustment) {
        return inventoryDispositionAdjustment == null? null: inventoryDispositionAdjustment.getLastDetailForUpdate(
                ).getInventoryDispositionAdjustmentDetailValue().clone();
    }

    public InventoryDispositionAdjustmentDetailValue getInventoryDispositionAdjustmentDetailValueByNameForUpdate(
            InventoryDisposition inventoryDisposition, String inventoryDispositionAdjustmentName) {
        return getInventoryDispositionAdjustmentDetailValueForUpdate(getInventoryDispositionAdjustmentByNameForUpdate(inventoryDisposition,
                inventoryDispositionAdjustmentName));
    }

    public InventoryDispositionAdjustment getDefaultInventoryDispositionAdjustment(InventoryDisposition inventoryDisposition,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryDispositionAdjustments.fields())
                .from(InventoryDispositionAdjustments)
                .join(InventoryDispositionAdjustmentDetails).onKey(INVENTORY_DISPOSITION_ADJUSTMENTS_ACTIVE_DETAIL_FK)
                .where(InventoryDispositionAdjustmentDetails.INVENTORY_DISPOSITION.eq(inventoryDisposition.getPrimaryKey()),
                        InventoryDispositionAdjustmentDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return inventoryDispositionAdjustmentFactory.getEntityFromQuery(entityPermission, query);
    }

    public InventoryDispositionAdjustment getDefaultInventoryDispositionAdjustment(InventoryDisposition inventoryDisposition) {
        return getDefaultInventoryDispositionAdjustment(inventoryDisposition, EntityPermission.READ_ONLY);
    }

    public InventoryDispositionAdjustment getDefaultInventoryDispositionAdjustmentForUpdate(InventoryDisposition inventoryDisposition) {
        return getDefaultInventoryDispositionAdjustment(inventoryDisposition, EntityPermission.READ_WRITE);
    }

    public InventoryDispositionAdjustmentDetailValue getDefaultInventoryDispositionAdjustmentDetailValueForUpdate(
            InventoryDisposition inventoryDisposition) {
        return getDefaultInventoryDispositionAdjustmentForUpdate(
                inventoryDisposition).getLastDetailForUpdate().getInventoryDispositionAdjustmentDetailValue().clone();
    }

    private List<InventoryDispositionAdjustment> getInventoryDispositionAdjustments(InventoryDisposition inventoryDisposition,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryDispositionAdjustments.fields())
                .from(InventoryDispositionAdjustments)
                .join(InventoryDispositionAdjustmentDetails).onKey(INVENTORY_DISPOSITION_ADJUSTMENTS_ACTIVE_DETAIL_FK)
                .where(InventoryDispositionAdjustmentDetails.INVENTORY_DISPOSITION.eq(inventoryDisposition.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(InventoryDispositionAdjustmentDetails.SORT_ORDER,
                            InventoryDispositionAdjustmentDetails.INVENTORY_DISPOSITION_ADJUSTMENT_NAME),
                    InventoryDispositionAdjustmentFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return inventoryDispositionAdjustmentFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<InventoryDispositionAdjustment> getInventoryDispositionAdjustments(InventoryDisposition inventoryDisposition) {
        return getInventoryDispositionAdjustments(inventoryDisposition, EntityPermission.READ_ONLY);
    }

    public List<InventoryDispositionAdjustment> getInventoryDispositionAdjustmentsByInventoryDisposition(
            InventoryDisposition inventoryDisposition) {
        return getInventoryDispositionAdjustments(inventoryDisposition);
    }

    public List<InventoryDispositionAdjustment> getInventoryDispositionAdjustmentsForUpdate(InventoryDisposition inventoryDisposition) {
        return getInventoryDispositionAdjustments(inventoryDisposition, EntityPermission.READ_WRITE);
    }

    private List<InventoryDispositionAdjustment> getInventoryDispositionAdjustmentsByCondition(
            org.jooq.Condition condition, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext().select(InventoryDispositionAdjustments.fields())
                .from(InventoryDispositionAdjustments)
                .join(InventoryDispositionAdjustmentDetails).onKey(INVENTORY_DISPOSITION_ADJUSTMENTS_ACTIVE_DETAIL_FK)
                .where(condition);
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery.orderBy(InventoryDispositionAdjustmentDetails.SORT_ORDER,
                    InventoryDispositionAdjustmentDetails.INVENTORY_DISPOSITION_ADJUSTMENT_NAME),
                    InventoryDispositionAdjustmentFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };
        return inventoryDispositionAdjustmentFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<InventoryDispositionAdjustment> getInventoryDispositionAdjustmentsByInventoryAdjustmentType(
            InventoryAdjustmentType inventoryAdjustmentType) {
        return getInventoryDispositionAdjustmentsByCondition(
                InventoryDispositionAdjustmentDetails.INVENTORY_ADJUSTMENT_TYPE.eq(inventoryAdjustmentType.getPrimaryKey()),
                EntityPermission.READ_ONLY);
    }

    public List<InventoryDispositionAdjustment> getInventoryDispositionAdjustmentsByInventoryBucketType(
            InventoryBucketType inventoryBucketType) {
        return getInventoryDispositionAdjustmentsByCondition(
                InventoryDispositionAdjustmentDetails.INVENTORY_BUCKET_TYPE.eq(inventoryBucketType.getPrimaryKey()),
                EntityPermission.READ_ONLY);
    }

    public InventoryDispositionAdjustmentTransfer getInventoryDispositionAdjustmentTransfer(UserVisit userVisit,
            InventoryDispositionAdjustment inventoryDispositionAdjustment) {
        return inventoryDispositionAdjustmentTransferCache.getTransfer(userVisit, inventoryDispositionAdjustment);
    }

    public List<InventoryDispositionAdjustmentTransfer> getInventoryDispositionAdjustmentTransfers(UserVisit userVisit,
            Collection<InventoryDispositionAdjustment> inventoryDispositionAdjustmentAdjustments) {
        List<InventoryDispositionAdjustmentTransfer> inventoryDispositionAdjustmentTransfers =
                new ArrayList<>(inventoryDispositionAdjustmentAdjustments.size());

        inventoryDispositionAdjustmentAdjustments.forEach((inventoryDispositionAdjustment) ->
                inventoryDispositionAdjustmentTransfers.add(inventoryDispositionAdjustmentTransferCache.getTransfer(userVisit,
                        inventoryDispositionAdjustment))
        );

        return inventoryDispositionAdjustmentTransfers;
    }

    public List<InventoryDispositionAdjustmentTransfer> getInventoryDispositionAdjustmentTransfers(UserVisit userVisit,
            InventoryDisposition inventoryDisposition) {
        return getInventoryDispositionAdjustmentTransfers(userVisit, getInventoryDispositionAdjustments(inventoryDisposition));
    }

    public InventoryDispositionAdjustmentChoicesBean getInventoryDispositionAdjustmentChoices(String defaultInventoryDispositionAdjustmentChoice,
            Language language, boolean allowNullChoice,
            InventoryDisposition inventoryDisposition) {
        var inventoryDispositionAdjustmentAdjustments = getInventoryDispositionAdjustments(inventoryDisposition);
        var size = inventoryDispositionAdjustmentAdjustments.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultInventoryDispositionAdjustmentChoice == null) {
                defaultValue = "";
            }
        }

        for(var inventoryDispositionAdjustment : inventoryDispositionAdjustmentAdjustments) {
            var inventoryDispositionAdjustmentDetail = inventoryDispositionAdjustment.getLastDetail();

            var label = getBestInventoryDispositionAdjustmentDescription(inventoryDispositionAdjustment, language);
            var value = inventoryDispositionAdjustmentDetail.getInventoryDispositionAdjustmentName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultInventoryDispositionAdjustmentChoice != null && defaultInventoryDispositionAdjustmentChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && inventoryDispositionAdjustmentDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new InventoryDispositionAdjustmentChoicesBean(labels, values, defaultValue);
    }

    private void updateInventoryDispositionAdjustmentFromValue(InventoryDispositionAdjustmentDetailValue inventoryDispositionAdjustmentDetailValue,
            boolean checkDefault, BasePK updatedBy) {
        if(inventoryDispositionAdjustmentDetailValue.hasBeenModified()) {
            var inventoryDispositionAdjustment = inventoryDispositionAdjustmentFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     inventoryDispositionAdjustmentDetailValue.getInventoryDispositionAdjustmentPK());
            var inventoryDispositionAdjustmentDetail = inventoryDispositionAdjustment.getActiveDetailForUpdate();

            inventoryDispositionAdjustmentDetail.setThruTime(session.getStartTime());
            inventoryDispositionAdjustmentDetail.store();

            var inventoryDisposition = inventoryDispositionAdjustmentDetail.getInventoryDisposition(); // Not updated
            var inventoryDispositionPK = inventoryDisposition.getPrimaryKey(); // Not updated
            var inventoryDispositionAdjustmentPK = inventoryDispositionAdjustmentDetail.getInventoryDispositionAdjustmentPK(); // Not updated
            var inventoryDispositionAdjustmentName = inventoryDispositionAdjustmentDetailValue.getInventoryDispositionAdjustmentName();
            var inventoryAdjustmentTypePK = inventoryDispositionAdjustmentDetailValue.getInventoryAdjustmentTypePK();
            var inventoryBucketTypePK = inventoryDispositionAdjustmentDetailValue.getInventoryBucketTypePK();
            var isDefault = inventoryDispositionAdjustmentDetailValue.getIsDefault();
            var sortOrder = inventoryDispositionAdjustmentDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultInventoryDispositionAdjustment = getDefaultInventoryDispositionAdjustment(inventoryDisposition);
                var defaultFound = 
                        defaultInventoryDispositionAdjustment != null && !defaultInventoryDispositionAdjustment.equals(inventoryDispositionAdjustment);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultInventoryDispositionAdjustmentDetailValue = 
                            getDefaultInventoryDispositionAdjustmentDetailValueForUpdate(inventoryDisposition);

                    defaultInventoryDispositionAdjustmentDetailValue.setIsDefault(false);
                    updateInventoryDispositionAdjustmentFromValue(defaultInventoryDispositionAdjustmentDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            inventoryDispositionAdjustmentDetail = inventoryDispositionAdjustmentDetailFactory.create(inventoryDispositionAdjustmentPK,
                    inventoryDispositionPK, inventoryDispositionAdjustmentName, inventoryAdjustmentTypePK, inventoryBucketTypePK,
                    isDefault, sortOrder,
                    session.getStartTime(), Session.MAX_TIME);

            inventoryDispositionAdjustment.setActiveDetail(inventoryDispositionAdjustmentDetail);
            inventoryDispositionAdjustment.setLastDetail(inventoryDispositionAdjustmentDetail);

            sendEvent(inventoryDispositionAdjustmentPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateInventoryDispositionAdjustmentFromValue(InventoryDispositionAdjustmentDetailValue inventoryDispositionAdjustmentDetailValue,
            BasePK updatedBy) {
        updateInventoryDispositionAdjustmentFromValue(inventoryDispositionAdjustmentDetailValue, true, updatedBy);
    }

    public void deleteInventoryDispositionAdjustment(InventoryDispositionAdjustment inventoryDispositionAdjustment, BasePK deletedBy) {
        var inventoryDispositionAdjustmentDetail = inventoryDispositionAdjustment.getLastDetailForUpdate();

        deleteInventoryDispositionAdjustmentDescriptionsByInventoryDispositionAdjustment(inventoryDispositionAdjustment, deletedBy);
        inventoryDispositionAdjustmentDetail.setThruTime(session.getStartTime());
        inventoryDispositionAdjustment.setActiveDetail(null);
        inventoryDispositionAdjustment.store();

        // Check for default, and pick one if necessary
        var inventoryDisposition = inventoryDispositionAdjustmentDetail.getInventoryDisposition();
        var defaultInventoryDispositionAdjustment = getDefaultInventoryDispositionAdjustment(inventoryDisposition);
        if(defaultInventoryDispositionAdjustment == null) {
            var inventoryDispositionAdjustmentAdjustments = getInventoryDispositionAdjustmentsForUpdate(inventoryDisposition);

            if(!inventoryDispositionAdjustmentAdjustments.isEmpty()) {
                var iter = inventoryDispositionAdjustmentAdjustments.iterator();
                if(iter.hasNext()) {
                    defaultInventoryDispositionAdjustment = iter.next();
                }
                var inventoryDispositionAdjustmentDetailValue = 
                        Objects.requireNonNull(
                                defaultInventoryDispositionAdjustment).getLastDetailForUpdate().getInventoryDispositionAdjustmentDetailValue().clone();

                inventoryDispositionAdjustmentDetailValue.setIsDefault(true);
                updateInventoryDispositionAdjustmentFromValue(inventoryDispositionAdjustmentDetailValue, false, deletedBy);
            }
        }

        sendEvent(inventoryDispositionAdjustment.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteInventoryDispositionAdjustmentsByInventoryDisposition(InventoryDisposition inventoryDisposition, BasePK deletedBy) {
        var inventoryDispositionAdjustments = getInventoryDispositionAdjustmentsForUpdate(inventoryDisposition);

        inventoryDispositionAdjustments.forEach(inventoryDispositionAdjustment ->
                deleteInventoryDispositionAdjustment(inventoryDispositionAdjustment, deletedBy));
    }

    public void deleteInventoryDispositionAdjustmentsByInventoryAdjustmentType(InventoryAdjustmentType inventoryAdjustmentType,
            BasePK deletedBy) {
        getInventoryDispositionAdjustmentsByCondition(
                InventoryDispositionAdjustmentDetails.INVENTORY_ADJUSTMENT_TYPE.eq(inventoryAdjustmentType.getPrimaryKey()),
                EntityPermission.READ_WRITE).forEach(adjustment -> deleteInventoryDispositionAdjustment(adjustment, deletedBy));
    }

    public void deleteInventoryDispositionAdjustmentsByInventoryBucketType(InventoryBucketType inventoryBucketType, BasePK deletedBy) {
        getInventoryDispositionAdjustmentsByCondition(
                InventoryDispositionAdjustmentDetails.INVENTORY_BUCKET_TYPE.eq(inventoryBucketType.getPrimaryKey()),
                EntityPermission.READ_WRITE).forEach(adjustment -> deleteInventoryDispositionAdjustment(adjustment, deletedBy));
    }

    // --------------------------------------------------------------------------------
    //   Inventory Disposition Adjustment Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    InventoryDispositionAdjustmentDescriptionFactory inventoryDispositionAdjustmentDescriptionFactory;

    @Inject
    InventoryDispositionAdjustmentDescriptionTransferCache inventoryDispositionAdjustmentDescriptionTransferCache;

    public InventoryDispositionAdjustmentDescription createInventoryDispositionAdjustmentDescription(
            InventoryDispositionAdjustment inventoryDispositionAdjustment, Language language, String description, BasePK createdBy) {
        var inventoryDispositionAdjustmentDescription = inventoryDispositionAdjustmentDescriptionFactory.create(inventoryDispositionAdjustment,
                language, description, session.getStartTime(), Session.MAX_TIME);

        sendEvent(inventoryDispositionAdjustment.getPrimaryKey(), EventTypes.MODIFY, inventoryDispositionAdjustmentDescription.getPrimaryKey(),
                EventTypes.CREATE, createdBy);

        return inventoryDispositionAdjustmentDescription;
    }

    public long countInventoryDispositionAdjustmentDescriptionsByInventoryDispositionAdjustment(
            InventoryDispositionAdjustment inventoryDispositionAdjustment) {
        return session.getDslContext()
                .selectCount()
                .from(InventoryDispositionAdjustmentDescriptions)
                .where(InventoryDispositionAdjustmentDescriptions.INVENTORY_DISPOSITION_ADJUSTMENT.eq(inventoryDispositionAdjustment.getPrimaryKey()),
                        InventoryDispositionAdjustmentDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countInventoryDispositionAdjustmentDescriptionsByLanguage(Language language) {
        return session.getDslContext()
                .selectCount()
                .from(InventoryDispositionAdjustmentDescriptions)
                .where(InventoryDispositionAdjustmentDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        InventoryDispositionAdjustmentDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private InventoryDispositionAdjustmentDescription getInventoryDispositionAdjustmentDescription(
            InventoryDispositionAdjustment inventoryDispositionAdjustment, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryDispositionAdjustmentDescriptions.fields())
                .from(InventoryDispositionAdjustmentDescriptions)
                .where(InventoryDispositionAdjustmentDescriptions.INVENTORY_DISPOSITION_ADJUSTMENT.eq(inventoryDispositionAdjustment.getPrimaryKey()),
                        InventoryDispositionAdjustmentDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        InventoryDispositionAdjustmentDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return inventoryDispositionAdjustmentDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }

    public InventoryDispositionAdjustmentDescription getInventoryDispositionAdjustmentDescription(
            InventoryDispositionAdjustment inventoryDispositionAdjustment, Language language) {
        return getInventoryDispositionAdjustmentDescription(inventoryDispositionAdjustment, language, EntityPermission.READ_ONLY);
    }

    public InventoryDispositionAdjustmentDescription getInventoryDispositionAdjustmentDescriptionForUpdate(
            InventoryDispositionAdjustment inventoryDispositionAdjustment, Language language) {
        return getInventoryDispositionAdjustmentDescription(inventoryDispositionAdjustment, language, EntityPermission.READ_WRITE);
    }

    public InventoryDispositionAdjustmentDescriptionValue getInventoryDispositionAdjustmentDescriptionValue(
            InventoryDispositionAdjustmentDescription inventoryDispositionAdjustmentDescription) {
        return inventoryDispositionAdjustmentDescription == null ? null
                : inventoryDispositionAdjustmentDescription.getInventoryDispositionAdjustmentDescriptionValue().clone();
    }

    public InventoryDispositionAdjustmentDescriptionValue getInventoryDispositionAdjustmentDescriptionValueForUpdate(
            InventoryDispositionAdjustment inventoryDispositionAdjustment, Language language) {
        return getInventoryDispositionAdjustmentDescriptionValue(getInventoryDispositionAdjustmentDescriptionForUpdate(inventoryDispositionAdjustment,
                language));
    }

    private List<InventoryDispositionAdjustmentDescription> getInventoryDispositionAdjustmentDescriptionsByInventoryDispositionAdjustment(
            InventoryDispositionAdjustment inventoryDispositionAdjustment, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(InventoryDispositionAdjustmentDescriptions.fields())
                    .from(InventoryDispositionAdjustmentDescriptions)
                    .join(Languages).on(InventoryDispositionAdjustmentDescriptions.LANGUAGE.eq(Languages.LANGUAGE))
                    .where(InventoryDispositionAdjustmentDescriptions.INVENTORY_DISPOSITION_ADJUSTMENT.eq(inventoryDispositionAdjustment.getPrimaryKey()),
                            InventoryDispositionAdjustmentDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    InventoryDispositionAdjustmentDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(InventoryDispositionAdjustmentDescriptions.fields())
                    .from(InventoryDispositionAdjustmentDescriptions)
                    .where(InventoryDispositionAdjustmentDescriptions.INVENTORY_DISPOSITION_ADJUSTMENT.eq(inventoryDispositionAdjustment.getPrimaryKey()),
                            InventoryDispositionAdjustmentDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return inventoryDispositionAdjustmentDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<InventoryDispositionAdjustmentDescription> getInventoryDispositionAdjustmentDescriptionsByInventoryDispositionAdjustment(
            InventoryDispositionAdjustment inventoryDispositionAdjustment) {
        return getInventoryDispositionAdjustmentDescriptionsByInventoryDispositionAdjustment(inventoryDispositionAdjustment, EntityPermission.READ_ONLY);
    }

    public List<InventoryDispositionAdjustmentDescription> getInventoryDispositionAdjustmentDescriptionsByInventoryDispositionAdjustmentForUpdate(
            InventoryDispositionAdjustment inventoryDispositionAdjustment) {
        return getInventoryDispositionAdjustmentDescriptionsByInventoryDispositionAdjustment(inventoryDispositionAdjustment, EntityPermission.READ_WRITE);
    }

    public String getBestInventoryDispositionAdjustmentDescription(InventoryDispositionAdjustment inventoryDispositionAdjustment, Language language) {
        String description;
        var inventoryDispositionAdjustmentDescription = getInventoryDispositionAdjustmentDescription(inventoryDispositionAdjustment, language);

        if(inventoryDispositionAdjustmentDescription == null && !language.getIsDefault()) {
            inventoryDispositionAdjustmentDescription = getInventoryDispositionAdjustmentDescription(inventoryDispositionAdjustment,
                    partyControl.getDefaultLanguage());
        }

        if(inventoryDispositionAdjustmentDescription == null) {
            description = inventoryDispositionAdjustment.getLastDetail().getInventoryDispositionAdjustmentName();
        } else {
            description = inventoryDispositionAdjustmentDescription.getDescription();
        }

        return description;
    }

    public InventoryDispositionAdjustmentDescriptionTransfer getInventoryDispositionAdjustmentDescriptionTransfer(UserVisit userVisit,
            InventoryDispositionAdjustmentDescription inventoryDispositionAdjustmentDescription) {
        return inventoryDispositionAdjustmentDescriptionTransferCache.getTransfer(userVisit, inventoryDispositionAdjustmentDescription);
    }

    public List<InventoryDispositionAdjustmentDescriptionTransfer> getInventoryDispositionAdjustmentDescriptionTransfers(
            UserVisit userVisit, Collection<InventoryDispositionAdjustmentDescription> inventoryDispositionAdjustmentDescriptions) {
        var transfers = new ArrayList<InventoryDispositionAdjustmentDescriptionTransfer>(inventoryDispositionAdjustmentDescriptions.size());

        inventoryDispositionAdjustmentDescriptions.forEach(inventoryDispositionAdjustmentDescription ->
                transfers.add(inventoryDispositionAdjustmentDescriptionTransferCache.getTransfer(userVisit,
                        inventoryDispositionAdjustmentDescription)));

        return transfers;
    }

    public List<InventoryDispositionAdjustmentDescriptionTransfer> getInventoryDispositionAdjustmentDescriptionTransfersByInventoryDispositionAdjustment(
            UserVisit userVisit, InventoryDispositionAdjustment inventoryDispositionAdjustment) {
        return getInventoryDispositionAdjustmentDescriptionTransfers(userVisit,
                getInventoryDispositionAdjustmentDescriptionsByInventoryDispositionAdjustment(inventoryDispositionAdjustment));
    }

    public void updateInventoryDispositionAdjustmentDescriptionFromValue(
            InventoryDispositionAdjustmentDescriptionValue inventoryDispositionAdjustmentDescriptionValue, BasePK updatedBy) {
        if(inventoryDispositionAdjustmentDescriptionValue.hasBeenModified()) {
            var inventoryDispositionAdjustmentDescription = inventoryDispositionAdjustmentDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    inventoryDispositionAdjustmentDescriptionValue.getPrimaryKey());

            inventoryDispositionAdjustmentDescription.setThruTime(session.getStartTime());
            inventoryDispositionAdjustmentDescription.store();

            var inventoryDispositionAdjustment = inventoryDispositionAdjustmentDescription.getInventoryDispositionAdjustment();
            var language = inventoryDispositionAdjustmentDescription.getLanguage();
            var description = inventoryDispositionAdjustmentDescriptionValue.getDescription();

            inventoryDispositionAdjustmentDescription = inventoryDispositionAdjustmentDescriptionFactory.create(inventoryDispositionAdjustment,
                    language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(inventoryDispositionAdjustment.getPrimaryKey(), EventTypes.MODIFY, inventoryDispositionAdjustmentDescription.getPrimaryKey(),
                    EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteInventoryDispositionAdjustmentDescription(InventoryDispositionAdjustmentDescription inventoryDispositionAdjustmentDescription,
            BasePK deletedBy) {
        inventoryDispositionAdjustmentDescription.setThruTime(session.getStartTime());

        sendEvent(inventoryDispositionAdjustmentDescription.getInventoryDispositionAdjustmentPK(), EventTypes.MODIFY,
                inventoryDispositionAdjustmentDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteInventoryDispositionAdjustmentDescriptionsByInventoryDispositionAdjustment(
            InventoryDispositionAdjustment inventoryDispositionAdjustment, BasePK deletedBy) {
        var inventoryDispositionAdjustmentDescriptions = 
                getInventoryDispositionAdjustmentDescriptionsByInventoryDispositionAdjustmentForUpdate(inventoryDispositionAdjustment);

        inventoryDispositionAdjustmentDescriptions.forEach((inventoryDispositionAdjustmentDescription) -> 
                deleteInventoryDispositionAdjustmentDescription(inventoryDispositionAdjustmentDescription, deletedBy)
        );
    }

}
