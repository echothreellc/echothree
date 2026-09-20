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

package com.echothree.model.control.shipment.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.shipment.common.choice.ShipmentAliasTypeChoicesBean;
import com.echothree.model.control.shipment.common.choice.ShipmentTimeTypeChoicesBean;
import com.echothree.model.control.shipment.common.choice.ShipmentTypeChoicesBean;
import com.echothree.model.control.shipment.common.transfer.ShipmentAliasTransfer;
import com.echothree.model.control.shipment.common.transfer.ShipmentAliasTypeDescriptionTransfer;
import com.echothree.model.control.shipment.common.transfer.ShipmentAliasTypeTransfer;
import com.echothree.model.control.shipment.common.transfer.ShipmentTimeTransfer;
import com.echothree.model.control.shipment.common.transfer.ShipmentTimeTypeDescriptionTransfer;
import com.echothree.model.control.shipment.common.transfer.ShipmentTimeTypeTransfer;
import com.echothree.model.control.shipment.common.transfer.ShipmentTransfer;
import com.echothree.model.control.shipment.common.transfer.ShipmentTypeDescriptionTransfer;
import com.echothree.model.control.shipment.common.transfer.ShipmentTypeShippingMethodTransfer;
import com.echothree.model.control.shipment.common.transfer.ShipmentTypeTransfer;
import com.echothree.model.control.shipment.server.transfer.ShipmentAliasTransferCache;
import com.echothree.model.control.shipment.server.transfer.ShipmentAliasTypeDescriptionTransferCache;
import com.echothree.model.control.shipment.server.transfer.ShipmentAliasTypeTransferCache;
import com.echothree.model.control.shipment.server.transfer.ShipmentTimeTransferCache;
import com.echothree.model.control.shipment.server.transfer.ShipmentTimeTypeDescriptionTransferCache;
import com.echothree.model.control.shipment.server.transfer.ShipmentTimeTypeTransferCache;
import com.echothree.model.control.shipment.server.transfer.ShipmentTypeDescriptionTransferCache;
import com.echothree.model.control.shipment.server.transfer.ShipmentTypeShippingMethodTransferCache;
import com.echothree.model.control.shipment.server.transfer.ShipmentTypeTransferCache;
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.shipment.common.pk.ShipmentAliasTypePK;
import com.echothree.model.data.shipment.common.pk.ShipmentTimeTypePK;
import com.echothree.model.data.shipment.common.pk.ShipmentTypePK;
import com.echothree.model.data.shipment.server.entity.Shipment;
import com.echothree.model.data.shipment.server.entity.ShipmentAlias;
import com.echothree.model.data.shipment.server.entity.ShipmentAliasType;
import com.echothree.model.data.shipment.server.entity.ShipmentAliasTypeDescription;
import com.echothree.model.data.shipment.server.entity.ShipmentTime;
import com.echothree.model.data.shipment.server.entity.ShipmentTimeType;
import com.echothree.model.data.shipment.server.entity.ShipmentTimeTypeDescription;
import com.echothree.model.data.shipment.server.entity.ShipmentType;
import com.echothree.model.data.shipment.server.entity.ShipmentTypeDescription;
import com.echothree.model.data.shipment.server.entity.ShipmentTypeShippingMethod;
import com.echothree.model.data.shipment.server.factory.ShipmentAliasFactory;
import com.echothree.model.data.shipment.server.factory.ShipmentAliasTypeDescriptionFactory;
import com.echothree.model.data.shipment.server.factory.ShipmentAliasTypeDetailFactory;
import com.echothree.model.data.shipment.server.factory.ShipmentAliasTypeFactory;
import com.echothree.model.data.shipment.server.factory.ShipmentTimeFactory;
import com.echothree.model.data.shipment.server.factory.ShipmentTimeTypeDescriptionFactory;
import com.echothree.model.data.shipment.server.factory.ShipmentTimeTypeDetailFactory;
import com.echothree.model.data.shipment.server.factory.ShipmentTimeTypeFactory;
import com.echothree.model.data.shipment.server.factory.ShipmentTypeDescriptionFactory;
import com.echothree.model.data.shipment.server.factory.ShipmentTypeDetailFactory;
import com.echothree.model.data.shipment.server.factory.ShipmentTypeFactory;
import com.echothree.model.data.shipment.server.factory.ShipmentTypeShippingMethodFactory;
import com.echothree.model.data.shipment.server.value.ShipmentAliasTypeDescriptionValue;
import com.echothree.model.data.shipment.server.value.ShipmentAliasTypeDetailValue;
import com.echothree.model.data.shipment.server.value.ShipmentAliasValue;
import com.echothree.model.data.shipment.server.value.ShipmentTimeTypeDescriptionValue;
import com.echothree.model.data.shipment.server.value.ShipmentTimeTypeDetailValue;
import com.echothree.model.data.shipment.server.value.ShipmentTimeValue;
import com.echothree.model.data.shipment.server.value.ShipmentTypeDescriptionValue;
import com.echothree.model.data.shipment.server.value.ShipmentTypeDetailValue;
import com.echothree.model.data.shipment.server.value.ShipmentTypeShippingMethodValue;
import com.echothree.model.data.shipping.server.entity.ShippingMethod;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import static com.echothree.model.jooq.server.keys.shipment.ShipmentForeignKeys.SHIPMENTS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.shipment.ShipmentForeignKeys.SHIPMENT_ALIASES_SHIPMENT_ALIAS_TYPE_FK;
import static com.echothree.model.jooq.server.keys.shipment.ShipmentForeignKeys.SHIPMENT_ALIASES_SHIPMENT_FK;
import static com.echothree.model.jooq.server.keys.shipment.ShipmentForeignKeys.SHIPMENT_ALIAS_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.shipment.ShipmentForeignKeys.SHIPMENT_ALIAS_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.shipment.ShipmentForeignKeys.SHIPMENT_ALIAS_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.shipment.ShipmentForeignKeys.SHIPMENT_TIMES_SHIPMENT_FK;
import static com.echothree.model.jooq.server.keys.shipment.ShipmentForeignKeys.SHIPMENT_TIMES_SHIPMENT_TIME_TYPE_FK;
import static com.echothree.model.jooq.server.keys.shipment.ShipmentForeignKeys.SHIPMENT_TIME_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.shipment.ShipmentForeignKeys.SHIPMENT_TIME_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.shipment.ShipmentForeignKeys.SHIPMENT_TIME_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.shipment.ShipmentForeignKeys.SHIPMENT_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.shipment.ShipmentForeignKeys.SHIPMENT_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.shipment.ShipmentForeignKeys.SHIPMENT_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.shipment.ShipmentForeignKeys.SHIPMENT_TYPE_SHIPPING_METHODS_SHIPMENT_TYPE_FK;
import static com.echothree.model.jooq.server.keys.shipment.ShipmentForeignKeys.SHIPMENT_TYPE_SHIPPING_METHODS_SHIPPING_METHOD_FK;
import static com.echothree.model.jooq.server.keys.shipping.ShippingForeignKeys.SHIPPING_METHODS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import static com.echothree.model.jooq.server.tables.shipment.ShipmentAliasTypeDescriptions.ShipmentAliasTypeDescriptions;
import static com.echothree.model.jooq.server.tables.shipment.ShipmentAliasTypeDetails.ShipmentAliasTypeDetails;
import static com.echothree.model.jooq.server.tables.shipment.ShipmentAliasTypes.ShipmentAliasTypes;
import static com.echothree.model.jooq.server.tables.shipment.ShipmentAliases.ShipmentAliases;
import static com.echothree.model.jooq.server.tables.shipment.ShipmentDetails.ShipmentDetails;
import static com.echothree.model.jooq.server.tables.shipment.ShipmentTimeTypeDescriptions.ShipmentTimeTypeDescriptions;
import static com.echothree.model.jooq.server.tables.shipment.ShipmentTimeTypeDetails.ShipmentTimeTypeDetails;
import static com.echothree.model.jooq.server.tables.shipment.ShipmentTimeTypes.ShipmentTimeTypes;
import static com.echothree.model.jooq.server.tables.shipment.ShipmentTimes.ShipmentTimes;
import static com.echothree.model.jooq.server.tables.shipment.ShipmentTypeDescriptions.ShipmentTypeDescriptions;
import static com.echothree.model.jooq.server.tables.shipment.ShipmentTypeDetails.ShipmentTypeDetails;
import static com.echothree.model.jooq.server.tables.shipment.ShipmentTypeShippingMethods.ShipmentTypeShippingMethods;
import static com.echothree.model.jooq.server.tables.shipment.ShipmentTypes.ShipmentTypes;
import static com.echothree.model.jooq.server.tables.shipment.Shipments.Shipments;
import static com.echothree.model.jooq.server.tables.shipping.ShippingMethodDetails.ShippingMethodDetails;
import static com.echothree.model.jooq.server.tables.shipping.ShippingMethods.ShippingMethods;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.inject.Inject;

@CommandScope
public class ShipmentControl
        extends BaseModelControl {
    
    /** Creates a new instance of ShipmentControl */
    protected ShipmentControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Shipment Types
    // --------------------------------------------------------------------------------

    @Inject
    protected ShipmentTypeDetailFactory shipmentTypeDetailFactory;

    @Inject
    protected ShipmentTypeFactory shipmentTypeFactory;

    @Inject
    private ShipmentTypeTransferCache shipmentTypeTransferCache;

    public ShipmentType createShipmentType(String shipmentTypeName, ShipmentType parentShipmentType, SequenceType shipmentSequenceType,
            SequenceType shipmentPackageSequenceType, Workflow shipmentWorkflow, WorkflowEntrance shipmentWorkflowEntrance, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var defaultShipmentType = getDefaultShipmentType();
        var defaultFound = defaultShipmentType != null;

        if(defaultFound && isDefault) {
            var defaultShipmentTypeDetailValue = getDefaultShipmentTypeDetailValueForUpdate();

            defaultShipmentTypeDetailValue.setIsDefault(false);
            updateShipmentTypeFromValue(defaultShipmentTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var shipmentType = shipmentTypeFactory.create();
        var shipmentTypeDetail = shipmentTypeDetailFactory.create(shipmentType, shipmentTypeName, parentShipmentType,
                shipmentSequenceType, shipmentPackageSequenceType, shipmentWorkflow, shipmentWorkflowEntrance, isDefault, sortOrder, session.getStartTime(),
                Session.MAX_TIME);

        // Convert to R/W
        shipmentType = shipmentTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                shipmentType.getPrimaryKey());
        shipmentType.setActiveDetail(shipmentTypeDetail);
        shipmentType.setLastDetail(shipmentTypeDetail);
        shipmentType.store();

        sendEvent(shipmentType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return shipmentType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ShipmentType */
    public ShipmentType getShipmentTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ShipmentTypePK(entityInstance.getEntityUniqueId());

        return shipmentTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public ShipmentType getShipmentTypeByEntityInstance(EntityInstance entityInstance) {
        return getShipmentTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ShipmentType getShipmentTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getShipmentTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countShipmentTypes() {
        return session.getDslContext()
                .selectCount()
                .from(ShipmentTypes)
                .join(ShipmentTypeDetails).onKey(SHIPMENT_TYPES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countShipmentTypesByParentShipmentType(final ShipmentType parentShipmentType) {
        return session.getDslContext()
                .selectCount()
                .from(ShipmentTypes)
                .join(ShipmentTypeDetails).onKey(SHIPMENT_TYPES_ACTIVE_DETAIL_FK)
                .where(ShipmentTypeDetails.PARENT_SHIPMENT_TYPE.eq(parentShipmentType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countShipmentTypesByShipmentSequenceType(final SequenceType shipmentSequenceType) {
        return session.getDslContext()
                .selectCount()
                .from(ShipmentTypes)
                .join(ShipmentTypeDetails).onKey(SHIPMENT_TYPES_ACTIVE_DETAIL_FK)
                .where(ShipmentTypeDetails.SHIPMENT_SEQUENCE_TYPE.eq(shipmentSequenceType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countShipmentTypesByShipmentPackageSequenceType(final SequenceType shipmentPackageSequenceType) {
        return session.getDslContext()
                .selectCount()
                .from(ShipmentTypes)
                .join(ShipmentTypeDetails).onKey(SHIPMENT_TYPES_ACTIVE_DETAIL_FK)
                .where(ShipmentTypeDetails.SHIPMENT_PACKAGE_SEQUENCE_TYPE.eq(shipmentPackageSequenceType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countShipmentTypesByShipmentWorkflow(final Workflow shipmentWorkflow) {
        return session.getDslContext()
                .selectCount()
                .from(ShipmentTypes)
                .join(ShipmentTypeDetails).onKey(SHIPMENT_TYPES_ACTIVE_DETAIL_FK)
                .where(ShipmentTypeDetails.SHIPMENT_WORKFLOW.eq(shipmentWorkflow.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countShipmentTypesByShipmentWorkflowEntrance(final WorkflowEntrance shipmentWorkflowEntrance) {
        return session.getDslContext()
                .selectCount()
                .from(ShipmentTypes)
                .join(ShipmentTypeDetails).onKey(SHIPMENT_TYPES_ACTIVE_DETAIL_FK)
                .where(ShipmentTypeDetails.SHIPMENT_WORKFLOW_ENTRANCE.eq(shipmentWorkflowEntrance.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private ShipmentType getShipmentTypeByName(String shipmentTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ShipmentTypes.fields())
                .from(ShipmentTypes)
                .join(ShipmentTypeDetails).onKey(SHIPMENT_TYPES_ACTIVE_DETAIL_FK)
                .where(ShipmentTypeDetails.SHIPMENT_TYPE_NAME.eq(shipmentTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return shipmentTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public ShipmentType getShipmentTypeByName(String shipmentTypeName) {
        return getShipmentTypeByName(shipmentTypeName, EntityPermission.READ_ONLY);
    }

    public ShipmentType getShipmentTypeByNameForUpdate(String shipmentTypeName) {
        return getShipmentTypeByName(shipmentTypeName, EntityPermission.READ_WRITE);
    }

    public ShipmentTypeDetailValue getShipmentTypeDetailValueForUpdate(ShipmentType shipmentType) {
        return shipmentType == null? null: shipmentType.getLastDetailForUpdate().getShipmentTypeDetailValue().clone();
    }

    public ShipmentTypeDetailValue getShipmentTypeDetailValueByNameForUpdate(String shipmentTypeName) {
        return getShipmentTypeDetailValueForUpdate(getShipmentTypeByNameForUpdate(shipmentTypeName));
    }

    private ShipmentType getDefaultShipmentType(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ShipmentTypes.fields())
                .from(ShipmentTypes)
                .join(ShipmentTypeDetails).onKey(SHIPMENT_TYPES_ACTIVE_DETAIL_FK)
                .where(ShipmentTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return shipmentTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public ShipmentType getDefaultShipmentType() {
        return getDefaultShipmentType(EntityPermission.READ_ONLY);
    }

    public ShipmentType getDefaultShipmentTypeForUpdate() {
        return getDefaultShipmentType(EntityPermission.READ_WRITE);
    }

    public ShipmentTypeDetailValue getDefaultShipmentTypeDetailValueForUpdate() {
        return getDefaultShipmentTypeForUpdate().getLastDetailForUpdate().getShipmentTypeDetailValue().clone();
    }

    private List<ShipmentType> getShipmentTypes(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ShipmentTypes.fields())
                .from(ShipmentTypes)
                .join(ShipmentTypeDetails).onKey(SHIPMENT_TYPES_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ShipmentTypeDetails.SORT_ORDER, ShipmentTypeDetails.SHIPMENT_TYPE_NAME),
                    ShipmentTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return shipmentTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ShipmentType> getShipmentTypes() {
        return getShipmentTypes(EntityPermission.READ_ONLY);
    }

    public List<ShipmentType> getShipmentTypesForUpdate() {
        return getShipmentTypes(EntityPermission.READ_WRITE);
    }

    private List<ShipmentType> getShipmentTypesByParentShipmentType(ShipmentType parentShipmentType,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ShipmentTypes.fields())
                .from(ShipmentTypes)
                .join(ShipmentTypeDetails).onKey(SHIPMENT_TYPES_ACTIVE_DETAIL_FK)
                .where(ShipmentTypeDetails.PARENT_SHIPMENT_TYPE.eq(parentShipmentType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ShipmentTypeDetails.SORT_ORDER, ShipmentTypeDetails.SHIPMENT_TYPE_NAME),
                    ShipmentTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return shipmentTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ShipmentType> getShipmentTypesByParentShipmentType(ShipmentType parentShipmentType) {
        return getShipmentTypesByParentShipmentType(parentShipmentType, EntityPermission.READ_ONLY);
    }

    public List<ShipmentType> getShipmentTypesByParentShipmentTypeForUpdate(ShipmentType parentShipmentType) {
        return getShipmentTypesByParentShipmentType(parentShipmentType, EntityPermission.READ_WRITE);
    }

    public ShipmentTypeTransfer getShipmentTypeTransfer(UserVisit userVisit, ShipmentType shipmentType) {
        return shipmentTypeTransferCache.getTransfer(userVisit, shipmentType);
    }

    public List<ShipmentTypeTransfer> getShipmentTypeTransfers(UserVisit userVisit, Collection<ShipmentType> shipmentTypes) {
        List<ShipmentTypeTransfer> shipmentTypeTransfers = new ArrayList<>(shipmentTypes.size());

        shipmentTypes.forEach((shipmentType) ->
                shipmentTypeTransfers.add(shipmentTypeTransferCache.getTransfer(userVisit, shipmentType))
        );

        return shipmentTypeTransfers;
    }

    public List<ShipmentTypeTransfer> getShipmentTypeTransfers(UserVisit userVisit) {
        return getShipmentTypeTransfers(userVisit, getShipmentTypes());
    }

    public ShipmentTypeChoicesBean getShipmentTypeChoices(String defaultShipmentTypeChoice,
            Language language, boolean allowNullChoice) {
        var shipmentTypes = getShipmentTypes();
        var size = shipmentTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultShipmentTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var shipmentType : shipmentTypes) {
            var shipmentTypeDetail = shipmentType.getLastDetail();

            var label = getBestShipmentTypeDescription(shipmentType, language);
            var value = shipmentTypeDetail.getShipmentTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultShipmentTypeChoice != null && defaultShipmentTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && shipmentTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ShipmentTypeChoicesBean(labels, values, defaultValue);
    }

    public boolean isParentShipmentTypeSafe(ShipmentType shipmentType,
            ShipmentType parentShipmentType) {
        var safe = true;

        if(parentShipmentType != null) {
            Set<ShipmentType> parentShipmentTypes = new HashSet<>();

            parentShipmentTypes.add(shipmentType);
            do {
                if(parentShipmentTypes.contains(parentShipmentType)) {
                    safe = false;
                    break;
                }

                parentShipmentTypes.add(parentShipmentType);
                parentShipmentType = parentShipmentType.getLastDetail().getParentShipmentType();
            } while(parentShipmentType != null);
        }

        return safe;
    }

    private void updateShipmentTypeFromValue(ShipmentTypeDetailValue shipmentTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(shipmentTypeDetailValue.hasBeenModified()) {
            var shipmentType = shipmentTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     shipmentTypeDetailValue.getShipmentTypePK());
            var shipmentTypeDetail = shipmentType.getActiveDetailForUpdate();

            shipmentTypeDetail.setThruTime(session.getStartTime());
            shipmentTypeDetail.store();

            var shipmentTypePK = shipmentTypeDetail.getShipmentTypePK(); // Not updated
            var shipmentTypeName = shipmentTypeDetailValue.getShipmentTypeName();
            var parentShipmentTypePK = shipmentTypeDetailValue.getParentShipmentTypePK();
            var shipmentSequenceTypePK = shipmentTypeDetailValue.getShipmentSequenceTypePK();
            var shipmentPackageSequenceTypePK = shipmentTypeDetailValue.getShipmentPackageSequenceTypePK();
            var shipmentWorkflowPK = shipmentTypeDetailValue.getShipmentWorkflowPK();
            var shipmentWorkflowEntrancePK = shipmentTypeDetailValue.getShipmentWorkflowEntrancePK();
            var isDefault = shipmentTypeDetailValue.getIsDefault();
            var sortOrder = shipmentTypeDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultShipmentType = getDefaultShipmentType();
                var defaultFound = defaultShipmentType != null && !defaultShipmentType.equals(shipmentType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultShipmentTypeDetailValue = getDefaultShipmentTypeDetailValueForUpdate();

                    defaultShipmentTypeDetailValue.setIsDefault(false);
                    updateShipmentTypeFromValue(defaultShipmentTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            shipmentTypeDetail = shipmentTypeDetailFactory.create(shipmentTypePK, shipmentTypeName, parentShipmentTypePK, shipmentSequenceTypePK,
                    shipmentPackageSequenceTypePK, shipmentWorkflowPK, shipmentWorkflowEntrancePK, isDefault, sortOrder, session.getStartTime(),
                    Session.MAX_TIME);

            shipmentType.setActiveDetail(shipmentTypeDetail);
            shipmentType.setLastDetail(shipmentTypeDetail);

            sendEvent(shipmentTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateShipmentTypeFromValue(ShipmentTypeDetailValue shipmentTypeDetailValue, BasePK updatedBy) {
        updateShipmentTypeFromValue(shipmentTypeDetailValue, true, updatedBy);
    }

    private void deleteShipmentType(ShipmentType shipmentType, boolean checkDefault, BasePK deletedBy) {
        var shipmentTypeDetail = shipmentType.getLastDetailForUpdate();

        deleteShipmentTypesByParentShipmentType(shipmentType, deletedBy);
        deleteShipmentTypeDescriptionsByShipmentType(shipmentType, deletedBy);
        deleteShipmentAliasTypesByShipmentType(shipmentType, deletedBy);
        // TODO: deleteShipmentsByShipmentType(shipmentType, deletedBy);

        shipmentTypeDetail.setThruTime(session.getStartTime());
        shipmentType.setActiveDetail(null);
        shipmentType.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var defaultShipmentType = getDefaultShipmentType();

            if(defaultShipmentType == null) {
                var shipmentTypes = getShipmentTypesForUpdate();

                if(!shipmentTypes.isEmpty()) {
                    var iter = shipmentTypes.iterator();
                    if(iter.hasNext()) {
                        defaultShipmentType = iter.next();
                    }
                    var shipmentTypeDetailValue = Objects.requireNonNull(defaultShipmentType).getLastDetailForUpdate().getShipmentTypeDetailValue().clone();

                    shipmentTypeDetailValue.setIsDefault(true);
                    updateShipmentTypeFromValue(shipmentTypeDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(shipmentType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteShipmentType(ShipmentType shipmentType, BasePK deletedBy) {
        deleteShipmentType(shipmentType, true, deletedBy);
    }

    private void deleteShipmentTypes(List<ShipmentType> shipmentTypes, boolean checkDefault, BasePK deletedBy) {
        shipmentTypes.forEach((shipmentType) -> deleteShipmentType(shipmentType, checkDefault, deletedBy));
    }

    public void deleteShipmentTypes(List<ShipmentType> shipmentTypes, BasePK deletedBy) {
        deleteShipmentTypes(shipmentTypes, true, deletedBy);
    }

    private void deleteShipmentTypesByParentShipmentType(ShipmentType parentShipmentType, BasePK deletedBy) {
        deleteShipmentTypes(getShipmentTypesByParentShipmentTypeForUpdate(parentShipmentType), false, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Shipment Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected ShipmentTypeDescriptionFactory shipmentTypeDescriptionFactory;

    @Inject
    private ShipmentTypeDescriptionTransferCache shipmentTypeDescriptionTransferCache;

    public ShipmentTypeDescription createShipmentTypeDescription(ShipmentType shipmentType, Language language, String description, BasePK createdBy) {
        var shipmentTypeDescription = shipmentTypeDescriptionFactory.create(shipmentType, language, description,
                session.getStartTime(), Session.MAX_TIME);

        sendEvent(shipmentType.getPrimaryKey(), EventTypes.MODIFY, shipmentTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return shipmentTypeDescription;
    }

    private ShipmentTypeDescription getShipmentTypeDescription(ShipmentType shipmentType, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ShipmentTypeDescriptions.fields())
                .from(ShipmentTypeDescriptions)
                .where(ShipmentTypeDescriptions.SHIPMENT_TYPE.eq(shipmentType.getPrimaryKey()),
                        ShipmentTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        ShipmentTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return shipmentTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }

    public ShipmentTypeDescription getShipmentTypeDescription(ShipmentType shipmentType, Language language) {
        return getShipmentTypeDescription(shipmentType, language, EntityPermission.READ_ONLY);
    }

    public ShipmentTypeDescription getShipmentTypeDescriptionForUpdate(ShipmentType shipmentType, Language language) {
        return getShipmentTypeDescription(shipmentType, language, EntityPermission.READ_WRITE);
    }

    public ShipmentTypeDescriptionValue getShipmentTypeDescriptionValue(ShipmentTypeDescription shipmentTypeDescription) {
        return shipmentTypeDescription == null? null: shipmentTypeDescription.getShipmentTypeDescriptionValue().clone();
    }

    public ShipmentTypeDescriptionValue getShipmentTypeDescriptionValueForUpdate(ShipmentType shipmentType, Language language) {
        return getShipmentTypeDescriptionValue(getShipmentTypeDescriptionForUpdate(shipmentType, language));
    }

    private List<ShipmentTypeDescription> getShipmentTypeDescriptionsByShipmentType(ShipmentType shipmentType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ShipmentTypeDescriptions.fields())
                    .from(ShipmentTypeDescriptions)
                    .join(Languages).onKey(SHIPMENT_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(ShipmentTypeDescriptions.SHIPMENT_TYPE.eq(shipmentType.getPrimaryKey()),
                            ShipmentTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    ShipmentTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ShipmentTypeDescriptions.fields())
                    .from(ShipmentTypeDescriptions)
                    .where(ShipmentTypeDescriptions.SHIPMENT_TYPE.eq(shipmentType.getPrimaryKey()),
                            ShipmentTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return shipmentTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ShipmentTypeDescription> getShipmentTypeDescriptionsByShipmentType(ShipmentType shipmentType) {
        return getShipmentTypeDescriptionsByShipmentType(shipmentType, EntityPermission.READ_ONLY);
    }

    public List<ShipmentTypeDescription> getShipmentTypeDescriptionsByShipmentTypeForUpdate(ShipmentType shipmentType) {
        return getShipmentTypeDescriptionsByShipmentType(shipmentType, EntityPermission.READ_WRITE);
    }

    public String getBestShipmentTypeDescription(ShipmentType shipmentType, Language language) {
        String description;
        var shipmentTypeDescription = getShipmentTypeDescription(shipmentType, language);

        if(shipmentTypeDescription == null && !language.getIsDefault()) {
            shipmentTypeDescription = getShipmentTypeDescription(shipmentType, partyControl.getDefaultLanguage());
        }

        if(shipmentTypeDescription == null) {
            description = shipmentType.getLastDetail().getShipmentTypeName();
        } else {
            description = shipmentTypeDescription.getDescription();
        }

        return description;
    }

    public ShipmentTypeDescriptionTransfer getShipmentTypeDescriptionTransfer(UserVisit userVisit, ShipmentTypeDescription shipmentTypeDescription) {
        return shipmentTypeDescriptionTransferCache.getTransfer(userVisit, shipmentTypeDescription);
    }

    public List<ShipmentTypeDescriptionTransfer> getShipmentTypeDescriptionTransfersByShipmentType(UserVisit userVisit, ShipmentType shipmentType) {
        var shipmentTypeDescriptions = getShipmentTypeDescriptionsByShipmentType(shipmentType);
        List<ShipmentTypeDescriptionTransfer> shipmentTypeDescriptionTransfers = new ArrayList<>(shipmentTypeDescriptions.size());

        shipmentTypeDescriptions.forEach((shipmentTypeDescription) ->
                shipmentTypeDescriptionTransfers.add(shipmentTypeDescriptionTransferCache.getTransfer(userVisit, shipmentTypeDescription))
        );

        return shipmentTypeDescriptionTransfers;
    }

    public void updateShipmentTypeDescriptionFromValue(ShipmentTypeDescriptionValue shipmentTypeDescriptionValue, BasePK updatedBy) {
        if(shipmentTypeDescriptionValue.hasBeenModified()) {
            var shipmentTypeDescription = shipmentTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    shipmentTypeDescriptionValue.getPrimaryKey());

            shipmentTypeDescription.setThruTime(session.getStartTime());
            shipmentTypeDescription.store();

            var shipmentType = shipmentTypeDescription.getShipmentType();
            var language = shipmentTypeDescription.getLanguage();
            var description = shipmentTypeDescriptionValue.getDescription();

            shipmentTypeDescription = shipmentTypeDescriptionFactory.create(shipmentType, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(shipmentType.getPrimaryKey(), EventTypes.MODIFY, shipmentTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteShipmentTypeDescription(ShipmentTypeDescription shipmentTypeDescription, BasePK deletedBy) {
        shipmentTypeDescription.setThruTime(session.getStartTime());

        sendEvent(shipmentTypeDescription.getShipmentTypePK(), EventTypes.MODIFY, shipmentTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteShipmentTypeDescriptionsByShipmentType(ShipmentType shipmentType, BasePK deletedBy) {
        var shipmentTypeDescriptions = getShipmentTypeDescriptionsByShipmentTypeForUpdate(shipmentType);

        shipmentTypeDescriptions.forEach((shipmentTypeDescription) -> 
                deleteShipmentTypeDescription(shipmentTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Shipment Time Types
    // --------------------------------------------------------------------------------

    @Inject
    protected ShipmentTimeTypeDetailFactory shipmentTimeTypeDetailFactory;

    @Inject
    protected ShipmentTimeTypeFactory shipmentTimeTypeFactory;

    @Inject
    private ShipmentTimeTypeTransferCache shipmentTimeTypeTransferCache;

    public ShipmentTimeType createShipmentTimeType(ShipmentType shipmentType, String shipmentTimeTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultShipmentTimeType = getDefaultShipmentTimeType(shipmentType);
        var defaultFound = defaultShipmentTimeType != null;

        if(defaultFound && isDefault) {
            var defaultShipmentTimeTypeDetailValue = getDefaultShipmentTimeTypeDetailValueForUpdate(shipmentType);

            defaultShipmentTimeTypeDetailValue.setIsDefault(false);
            updateShipmentTimeTypeFromValue(defaultShipmentTimeTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var shipmentTimeType = shipmentTimeTypeFactory.create();
        var shipmentTimeTypeDetail = shipmentTimeTypeDetailFactory.create(shipmentTimeType, shipmentType, shipmentTimeTypeName, isDefault,
                sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        shipmentTimeType = shipmentTimeTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                shipmentTimeType.getPrimaryKey());
        shipmentTimeType.setActiveDetail(shipmentTimeTypeDetail);
        shipmentTimeType.setLastDetail(shipmentTimeTypeDetail);
        shipmentTimeType.store();

        sendEvent(shipmentTimeType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return shipmentTimeType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ShipmentTimeType */
    public ShipmentTimeType getShipmentTimeTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ShipmentTimeTypePK(entityInstance.getEntityUniqueId());

        return shipmentTimeTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public ShipmentTimeType getShipmentTimeTypeByEntityInstance(EntityInstance entityInstance) {
        return getShipmentTimeTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ShipmentTimeType getShipmentTimeTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getShipmentTimeTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countShipmentTimeTypesByShipmentType(ShipmentType shipmentType) {
        return session.getDslContext()
                .selectCount()
                .from(ShipmentTimeTypes)
                .join(ShipmentTimeTypeDetails).onKey(SHIPMENT_TIME_TYPES_ACTIVE_DETAIL_FK)
                .where(ShipmentTimeTypeDetails.SHIPMENT_TYPE.eq(shipmentType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private ShipmentTimeType getShipmentTimeTypeByName(ShipmentType shipmentType, String shipmentTimeTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ShipmentTimeTypes.fields())
                .from(ShipmentTimeTypes)
                .join(ShipmentTimeTypeDetails).onKey(SHIPMENT_TIME_TYPES_ACTIVE_DETAIL_FK)
                .where(ShipmentTimeTypeDetails.SHIPMENT_TYPE.eq(shipmentType.getPrimaryKey()),
                        ShipmentTimeTypeDetails.SHIPMENT_TIME_TYPE_NAME.eq(shipmentTimeTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return shipmentTimeTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public ShipmentTimeType getShipmentTimeTypeByName(ShipmentType shipmentType, String shipmentTimeTypeName) {
        return getShipmentTimeTypeByName(shipmentType, shipmentTimeTypeName, EntityPermission.READ_ONLY);
    }

    public ShipmentTimeType getShipmentTimeTypeByNameForUpdate(ShipmentType shipmentType, String shipmentTimeTypeName) {
        return getShipmentTimeTypeByName(shipmentType, shipmentTimeTypeName, EntityPermission.READ_WRITE);
    }

    public ShipmentTimeTypeDetailValue getShipmentTimeTypeDetailValueForUpdate(ShipmentTimeType shipmentTimeType) {
        return shipmentTimeType == null? null: shipmentTimeType.getLastDetailForUpdate().getShipmentTimeTypeDetailValue().clone();
    }

    public ShipmentTimeTypeDetailValue getShipmentTimeTypeDetailValueByNameForUpdate(ShipmentType shipmentType, String shipmentTimeTypeName) {
        return getShipmentTimeTypeDetailValueForUpdate(getShipmentTimeTypeByNameForUpdate(shipmentType, shipmentTimeTypeName));
    }

    private ShipmentTimeType getDefaultShipmentTimeType(ShipmentType shipmentType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ShipmentTimeTypes.fields())
                .from(ShipmentTimeTypes)
                .join(ShipmentTimeTypeDetails).onKey(SHIPMENT_TIME_TYPES_ACTIVE_DETAIL_FK)
                .where(ShipmentTimeTypeDetails.SHIPMENT_TYPE.eq(shipmentType.getPrimaryKey()),
                        ShipmentTimeTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return shipmentTimeTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public ShipmentTimeType getDefaultShipmentTimeType(ShipmentType shipmentType) {
        return getDefaultShipmentTimeType(shipmentType, EntityPermission.READ_ONLY);
    }

    public ShipmentTimeType getDefaultShipmentTimeTypeForUpdate(ShipmentType shipmentType) {
        return getDefaultShipmentTimeType(shipmentType, EntityPermission.READ_WRITE);
    }

    public ShipmentTimeTypeDetailValue getDefaultShipmentTimeTypeDetailValueForUpdate(ShipmentType shipmentType) {
        return getDefaultShipmentTimeTypeForUpdate(shipmentType).getLastDetailForUpdate().getShipmentTimeTypeDetailValue().clone();
    }

    private List<ShipmentTimeType> getShipmentTimeTypes(ShipmentType shipmentType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ShipmentTimeTypes.fields())
                .from(ShipmentTimeTypes)
                .join(ShipmentTimeTypeDetails).onKey(SHIPMENT_TIME_TYPES_ACTIVE_DETAIL_FK)
                .where(ShipmentTimeTypeDetails.SHIPMENT_TYPE.eq(shipmentType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ShipmentTimeTypeDetails.SORT_ORDER, ShipmentTimeTypeDetails.SHIPMENT_TIME_TYPE_NAME),
                    ShipmentTimeTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return shipmentTimeTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ShipmentTimeType> getShipmentTimeTypes(ShipmentType shipmentType) {
        return getShipmentTimeTypes(shipmentType, EntityPermission.READ_ONLY);
    }

    public List<ShipmentTimeType> getShipmentTimeTypesForUpdate(ShipmentType shipmentType) {
        return getShipmentTimeTypes(shipmentType, EntityPermission.READ_WRITE);
    }

    public ShipmentTimeTypeTransfer getShipmentTimeTypeTransfer(UserVisit userVisit, ShipmentTimeType shipmentTimeType) {
        return shipmentTimeTypeTransferCache.getTransfer(userVisit, shipmentTimeType);
    }

    public List<ShipmentTimeTypeTransfer> getShipmentTimeTypeTransfers(UserVisit userVisit, Collection<ShipmentTimeType> shipmentTimeTypes) {
        var shipmentTimeTypeTransfers = new ArrayList<ShipmentTimeTypeTransfer>(shipmentTimeTypes.size());

        shipmentTimeTypes.forEach(shipmentTimeType ->
                shipmentTimeTypeTransfers.add(shipmentTimeTypeTransferCache.getTransfer(userVisit, shipmentTimeType))
        );

        return shipmentTimeTypeTransfers;
    }

    public List<ShipmentTimeTypeTransfer> getShipmentTimeTypeTransfers(UserVisit userVisit, ShipmentType shipmentType) {
        return getShipmentTimeTypeTransfers(userVisit, getShipmentTimeTypes(shipmentType));
    }

    public ShipmentTimeTypeChoicesBean getShipmentTimeTypeChoices(String defaultShipmentTimeTypeChoice, Language language, boolean allowNullChoice,
            ShipmentType shipmentType) {
        var shipmentTimeTypes = getShipmentTimeTypes(shipmentType);
        var size = shipmentTimeTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultShipmentTimeTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var shipmentTimeType : shipmentTimeTypes) {
            var shipmentTimeTypeDetail = shipmentTimeType.getLastDetail();

            var label = getBestShipmentTimeTypeDescription(shipmentTimeType, language);
            var value = shipmentTimeTypeDetail.getShipmentTimeTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultShipmentTimeTypeChoice != null && defaultShipmentTimeTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && shipmentTimeTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ShipmentTimeTypeChoicesBean(labels, values, defaultValue);
    }

    private void updateShipmentTimeTypeFromValue(ShipmentTimeTypeDetailValue shipmentTimeTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(shipmentTimeTypeDetailValue.hasBeenModified()) {
            var shipmentTimeType = shipmentTimeTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     shipmentTimeTypeDetailValue.getShipmentTimeTypePK());
            var shipmentTimeTypeDetail = shipmentTimeType.getActiveDetailForUpdate();

            shipmentTimeTypeDetail.setThruTime(session.getStartTime());
            shipmentTimeTypeDetail.store();

            var shipmentType = shipmentTimeTypeDetail.getShipmentType(); // Not updated
            var shipmentTypePK = shipmentType.getPrimaryKey(); // Not updated
            var shipmentTimeTypePK = shipmentTimeTypeDetail.getShipmentTimeTypePK(); // Not updated
            var shipmentTimeTypeName = shipmentTimeTypeDetailValue.getShipmentTimeTypeName();
            var isDefault = shipmentTimeTypeDetailValue.getIsDefault();
            var sortOrder = shipmentTimeTypeDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultShipmentTimeType = getDefaultShipmentTimeType(shipmentType);
                var defaultFound = defaultShipmentTimeType != null && !defaultShipmentTimeType.equals(shipmentTimeType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultShipmentTimeTypeDetailValue = getDefaultShipmentTimeTypeDetailValueForUpdate(shipmentType);

                    defaultShipmentTimeTypeDetailValue.setIsDefault(false);
                    updateShipmentTimeTypeFromValue(defaultShipmentTimeTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            shipmentTimeTypeDetail = shipmentTimeTypeDetailFactory.create(shipmentTimeTypePK, shipmentTypePK, shipmentTimeTypeName, isDefault, sortOrder,
                    session.getStartTime(), Session.MAX_TIME);

            shipmentTimeType.setActiveDetail(shipmentTimeTypeDetail);
            shipmentTimeType.setLastDetail(shipmentTimeTypeDetail);

            sendEvent(shipmentTimeTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateShipmentTimeTypeFromValue(ShipmentTimeTypeDetailValue shipmentTimeTypeDetailValue, BasePK updatedBy) {
        updateShipmentTimeTypeFromValue(shipmentTimeTypeDetailValue, true, updatedBy);
    }

    public void deleteShipmentTimeType(ShipmentTimeType shipmentTimeType, BasePK deletedBy) {
        deleteShipmentTimesByShipmentTimeType(shipmentTimeType, deletedBy);
        deleteShipmentTimeTypeDescriptionsByShipmentTimeType(shipmentTimeType, deletedBy);

        var shipmentTimeTypeDetail = shipmentTimeType.getLastDetailForUpdate();
        shipmentTimeTypeDetail.setThruTime(session.getStartTime());
        shipmentTimeType.setActiveDetail(null);
        shipmentTimeType.store();

        // Check for default, and pick one if necessary
        var shipmentType = shipmentTimeTypeDetail.getShipmentType();
        var defaultShipmentTimeType = getDefaultShipmentTimeType(shipmentType);
        if(defaultShipmentTimeType == null) {
            var shipmentTimeTypes = getShipmentTimeTypesForUpdate(shipmentType);

            if(!shipmentTimeTypes.isEmpty()) {
                var iter = shipmentTimeTypes.iterator();
                if(iter.hasNext()) {
                    defaultShipmentTimeType = iter.next();
                }
                var shipmentTimeTypeDetailValue = Objects.requireNonNull(defaultShipmentTimeType).getLastDetailForUpdate().getShipmentTimeTypeDetailValue().clone();

                shipmentTimeTypeDetailValue.setIsDefault(true);
                updateShipmentTimeTypeFromValue(shipmentTimeTypeDetailValue, false, deletedBy);
            }
        }

        sendEvent(shipmentTimeType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Shipment Time Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected ShipmentTimeTypeDescriptionFactory shipmentTimeTypeDescriptionFactory;

    @Inject
    private ShipmentTimeTypeDescriptionTransferCache shipmentTimeTypeDescriptionTransferCache;

    public ShipmentTimeTypeDescription createShipmentTimeTypeDescription(ShipmentTimeType shipmentTimeType, Language language, String description, BasePK createdBy) {
        var shipmentTimeTypeDescription = shipmentTimeTypeDescriptionFactory.create(shipmentTimeType, language, description,
                session.getStartTime(), Session.MAX_TIME);

        sendEvent(shipmentTimeType.getPrimaryKey(), EventTypes.MODIFY, shipmentTimeTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return shipmentTimeTypeDescription;
    }

    private ShipmentTimeTypeDescription getShipmentTimeTypeDescription(ShipmentTimeType shipmentTimeType, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ShipmentTimeTypeDescriptions.fields())
                .from(ShipmentTimeTypeDescriptions)
                .where(ShipmentTimeTypeDescriptions.SHIPMENT_TIME_TYPE.eq(shipmentTimeType.getPrimaryKey()),
                        ShipmentTimeTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        ShipmentTimeTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return shipmentTimeTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }

    public ShipmentTimeTypeDescription getShipmentTimeTypeDescription(ShipmentTimeType shipmentTimeType, Language language) {
        return getShipmentTimeTypeDescription(shipmentTimeType, language, EntityPermission.READ_ONLY);
    }

    public ShipmentTimeTypeDescription getShipmentTimeTypeDescriptionForUpdate(ShipmentTimeType shipmentTimeType, Language language) {
        return getShipmentTimeTypeDescription(shipmentTimeType, language, EntityPermission.READ_WRITE);
    }

    public ShipmentTimeTypeDescriptionValue getShipmentTimeTypeDescriptionValue(ShipmentTimeTypeDescription shipmentTimeTypeDescription) {
        return shipmentTimeTypeDescription == null? null: shipmentTimeTypeDescription.getShipmentTimeTypeDescriptionValue().clone();
    }

    public ShipmentTimeTypeDescriptionValue getShipmentTimeTypeDescriptionValueForUpdate(ShipmentTimeType shipmentTimeType, Language language) {
        return getShipmentTimeTypeDescriptionValue(getShipmentTimeTypeDescriptionForUpdate(shipmentTimeType, language));
    }

    private List<ShipmentTimeTypeDescription> getShipmentTimeTypeDescriptionsByShipmentTimeType(ShipmentTimeType shipmentTimeType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ShipmentTimeTypeDescriptions.fields())
                    .from(ShipmentTimeTypeDescriptions)
                    .join(Languages).onKey(SHIPMENT_TIME_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(ShipmentTimeTypeDescriptions.SHIPMENT_TIME_TYPE.eq(shipmentTimeType.getPrimaryKey()),
                            ShipmentTimeTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    ShipmentTimeTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ShipmentTimeTypeDescriptions.fields())
                    .from(ShipmentTimeTypeDescriptions)
                    .where(ShipmentTimeTypeDescriptions.SHIPMENT_TIME_TYPE.eq(shipmentTimeType.getPrimaryKey()),
                            ShipmentTimeTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return shipmentTimeTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ShipmentTimeTypeDescription> getShipmentTimeTypeDescriptionsByShipmentTimeType(ShipmentTimeType shipmentTimeType) {
        return getShipmentTimeTypeDescriptionsByShipmentTimeType(shipmentTimeType, EntityPermission.READ_ONLY);
    }

    public List<ShipmentTimeTypeDescription> getShipmentTimeTypeDescriptionsByShipmentTimeTypeForUpdate(ShipmentTimeType shipmentTimeType) {
        return getShipmentTimeTypeDescriptionsByShipmentTimeType(shipmentTimeType, EntityPermission.READ_WRITE);
    }

    public String getBestShipmentTimeTypeDescription(ShipmentTimeType shipmentTimeType, Language language) {
        String description;
        var shipmentTimeTypeDescription = getShipmentTimeTypeDescription(shipmentTimeType, language);

        if(shipmentTimeTypeDescription == null && !language.getIsDefault()) {
            shipmentTimeTypeDescription = getShipmentTimeTypeDescription(shipmentTimeType, partyControl.getDefaultLanguage());
        }

        if(shipmentTimeTypeDescription == null) {
            description = shipmentTimeType.getLastDetail().getShipmentTimeTypeName();
        } else {
            description = shipmentTimeTypeDescription.getDescription();
        }

        return description;
    }

    public ShipmentTimeTypeDescriptionTransfer getShipmentTimeTypeDescriptionTransfer(UserVisit userVisit, ShipmentTimeTypeDescription shipmentTimeTypeDescription) {
        return shipmentTimeTypeDescriptionTransferCache.getTransfer(userVisit, shipmentTimeTypeDescription);
    }

    public List<ShipmentTimeTypeDescriptionTransfer> getShipmentTimeTypeDescriptionTransfersByShipmentTimeType(UserVisit userVisit, ShipmentTimeType shipmentTimeType) {
        var shipmentTimeTypeDescriptions = getShipmentTimeTypeDescriptionsByShipmentTimeType(shipmentTimeType);
        List<ShipmentTimeTypeDescriptionTransfer> shipmentTimeTypeDescriptionTransfers = new ArrayList<>(shipmentTimeTypeDescriptions.size());

        shipmentTimeTypeDescriptions.forEach((shipmentTimeTypeDescription) ->
                shipmentTimeTypeDescriptionTransfers.add(shipmentTimeTypeDescriptionTransferCache.getTransfer(userVisit, shipmentTimeTypeDescription))
        );

        return shipmentTimeTypeDescriptionTransfers;
    }

    public void updateShipmentTimeTypeDescriptionFromValue(ShipmentTimeTypeDescriptionValue shipmentTimeTypeDescriptionValue, BasePK updatedBy) {
        if(shipmentTimeTypeDescriptionValue.hasBeenModified()) {
            var shipmentTimeTypeDescription = shipmentTimeTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    shipmentTimeTypeDescriptionValue.getPrimaryKey());

            shipmentTimeTypeDescription.setThruTime(session.getStartTime());
            shipmentTimeTypeDescription.store();

            var shipmentTimeType = shipmentTimeTypeDescription.getShipmentTimeType();
            var language = shipmentTimeTypeDescription.getLanguage();
            var description = shipmentTimeTypeDescriptionValue.getDescription();

            shipmentTimeTypeDescription = shipmentTimeTypeDescriptionFactory.create(shipmentTimeType, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(shipmentTimeType.getPrimaryKey(), EventTypes.MODIFY, shipmentTimeTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteShipmentTimeTypeDescription(ShipmentTimeTypeDescription shipmentTimeTypeDescription, BasePK deletedBy) {
        shipmentTimeTypeDescription.setThruTime(session.getStartTime());

        sendEvent(shipmentTimeTypeDescription.getShipmentTimeTypePK(), EventTypes.MODIFY, shipmentTimeTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteShipmentTimeTypeDescriptionsByShipmentTimeType(ShipmentTimeType shipmentTimeType, BasePK deletedBy) {
        var shipmentTimeTypeDescriptions = getShipmentTimeTypeDescriptionsByShipmentTimeTypeForUpdate(shipmentTimeType);

        shipmentTimeTypeDescriptions.forEach((shipmentTimeTypeDescription) -> 
                deleteShipmentTimeTypeDescription(shipmentTimeTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Shipment Type Shipping Methods
    // --------------------------------------------------------------------------------

    @Inject
    protected ShipmentTypeShippingMethodFactory shipmentTypeShippingMethodFactory;

    @Inject
    private ShipmentTypeShippingMethodTransferCache shipmentTypeShippingMethodTransferCache;

    public ShipmentTypeShippingMethod createShipmentTypeShippingMethod(ShipmentType shipmentType, ShippingMethod shippingMethod, Boolean isDefault,
            Integer sortOrder, BasePK createdBy) {
        var defaultShipmentTypeShippingMethod = getDefaultShipmentTypeShippingMethod(shipmentType);
        var defaultFound = defaultShipmentTypeShippingMethod != null;
        
        if(defaultFound && isDefault) {
            var defaultShipmentTypeShippingMethodValue = getDefaultShipmentTypeShippingMethodValueForUpdate(shipmentType);
            
            defaultShipmentTypeShippingMethodValue.setIsDefault(false);
            updateShipmentTypeShippingMethodFromValue(defaultShipmentTypeShippingMethodValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var shipmentTypeShippingMethod = shipmentTypeShippingMethodFactory.create(shipmentType, shippingMethod,
                isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(shippingMethod.getPrimaryKey(), EventTypes.MODIFY, shipmentTypeShippingMethod.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return shipmentTypeShippingMethod;
    }

    public long countShipmentTypeShippingMethodsByShipmentType(final ShipmentType shipmentType) {
        return session.getDslContext()
                .selectCount()
                .from(ShipmentTypeShippingMethods)
                .where(ShipmentTypeShippingMethods.SHIPMENT_TYPE.eq(shipmentType.getPrimaryKey()),
                        ShipmentTypeShippingMethods.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countShipmentTypeShippingMethodsByShippingMethod(final ShippingMethod shippingMethod) {
        return session.getDslContext()
                .selectCount()
                .from(ShipmentTypeShippingMethods)
                .where(ShipmentTypeShippingMethods.SHIPPING_METHOD.eq(shippingMethod.getPrimaryKey()),
                        ShipmentTypeShippingMethods.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private ShipmentTypeShippingMethod getShipmentTypeShippingMethod(ShipmentType shipmentType, ShippingMethod shippingMethod, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ShipmentTypeShippingMethods.fields())
                .from(ShipmentTypeShippingMethods)
                .where(ShipmentTypeShippingMethods.SHIPMENT_TYPE.eq(shipmentType.getPrimaryKey()),
                        ShipmentTypeShippingMethods.SHIPPING_METHOD.eq(shippingMethod.getPrimaryKey()),
                        ShipmentTypeShippingMethods.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return shipmentTypeShippingMethodFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ShipmentTypeShippingMethod getShipmentTypeShippingMethod(ShipmentType shipmentType, ShippingMethod shippingMethod) {
        return getShipmentTypeShippingMethod(shipmentType, shippingMethod, EntityPermission.READ_ONLY);
    }
    
    public ShipmentTypeShippingMethod getShipmentTypeShippingMethodForUpdate(ShipmentType shipmentType, ShippingMethod shippingMethod) {
        return getShipmentTypeShippingMethod(shipmentType, shippingMethod, EntityPermission.READ_WRITE);
    }
    
    public ShipmentTypeShippingMethodValue getShipmentTypeShippingMethodValueForUpdate(ShipmentType shipmentType, ShippingMethod shippingMethod) {
        var shipmentTypeShippingMethod = getShipmentTypeShippingMethodForUpdate(shipmentType, shippingMethod);
        
        return shipmentTypeShippingMethod == null? null: shipmentTypeShippingMethod.getShipmentTypeShippingMethodValue().clone();
    }
    
    private ShipmentTypeShippingMethod getDefaultShipmentTypeShippingMethod(ShipmentType shipmentType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ShipmentTypeShippingMethods.fields())
                .from(ShipmentTypeShippingMethods)
                .where(ShipmentTypeShippingMethods.SHIPMENT_TYPE.eq(shipmentType.getPrimaryKey()),
                        ShipmentTypeShippingMethods.IS_DEFAULT.eq(true),
                        ShipmentTypeShippingMethods.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return shipmentTypeShippingMethodFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ShipmentTypeShippingMethod getDefaultShipmentTypeShippingMethod(ShipmentType shipmentType) {
        return getDefaultShipmentTypeShippingMethod(shipmentType, EntityPermission.READ_ONLY);
    }
    
    public ShipmentTypeShippingMethod getDefaultShipmentTypeShippingMethodForUpdate(ShipmentType shipmentType) {
        return getDefaultShipmentTypeShippingMethod(shipmentType, EntityPermission.READ_WRITE);
    }
    
    public ShipmentTypeShippingMethodValue getDefaultShipmentTypeShippingMethodValueForUpdate(ShipmentType shipmentType) {
        var shipmentTypeShippingMethod = getDefaultShipmentTypeShippingMethodForUpdate(shipmentType);
        
        return shipmentTypeShippingMethod == null? null: shipmentTypeShippingMethod.getShipmentTypeShippingMethodValue().clone();
    }
    
    private List<ShipmentTypeShippingMethod> getShipmentTypeShippingMethodsByShipmentType(ShipmentType shipmentType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ShipmentTypeShippingMethods.fields())
                    .from(ShipmentTypeShippingMethods)
                    .join(ShippingMethods).onKey(SHIPMENT_TYPE_SHIPPING_METHODS_SHIPPING_METHOD_FK)
                    .join(ShippingMethodDetails).onKey(SHIPPING_METHODS_LAST_DETAIL_FK)
                    .where(ShipmentTypeShippingMethods.SHIPMENT_TYPE.eq(shipmentType.getPrimaryKey()),
                            ShipmentTypeShippingMethods.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ShippingMethodDetails.SORT_ORDER, ShippingMethodDetails.SHIPPING_METHOD_NAME),
                    ShipmentTypeShippingMethodFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ShipmentTypeShippingMethods.fields())
                    .from(ShipmentTypeShippingMethods)
                    .where(ShipmentTypeShippingMethods.SHIPMENT_TYPE.eq(shipmentType.getPrimaryKey()),
                            ShipmentTypeShippingMethods.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return shipmentTypeShippingMethodFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ShipmentTypeShippingMethod> getShipmentTypeShippingMethodsByShipmentType(ShipmentType shipmentType) {
        return getShipmentTypeShippingMethodsByShipmentType(shipmentType, EntityPermission.READ_ONLY);
    }
    
    public List<ShipmentTypeShippingMethod> getShipmentTypeShippingMethodsByShipmentTypeForUpdate(ShipmentType shipmentType) {
        return getShipmentTypeShippingMethodsByShipmentType(shipmentType, EntityPermission.READ_WRITE);
    }
    
    private List<ShipmentTypeShippingMethod> getShipmentTypeShippingMethodsByShippingMethod(ShippingMethod shippingMethod, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ShipmentTypeShippingMethods.fields())
                    .from(ShipmentTypeShippingMethods)
                    .join(ShipmentTypes).onKey(SHIPMENT_TYPE_SHIPPING_METHODS_SHIPMENT_TYPE_FK)
                    .join(ShipmentTypeDetails).onKey(SHIPMENT_TYPES_LAST_DETAIL_FK)
                    .where(ShipmentTypeShippingMethods.SHIPPING_METHOD.eq(shippingMethod.getPrimaryKey()),
                            ShipmentTypeShippingMethods.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ShipmentTypeDetails.SORT_ORDER, ShipmentTypeDetails.SHIPMENT_TYPE_NAME),
                    ShipmentTypeShippingMethodFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ShipmentTypeShippingMethods.fields())
                    .from(ShipmentTypeShippingMethods)
                    .where(ShipmentTypeShippingMethods.SHIPPING_METHOD.eq(shippingMethod.getPrimaryKey()),
                            ShipmentTypeShippingMethods.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return shipmentTypeShippingMethodFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ShipmentTypeShippingMethod> getShipmentTypeShippingMethodsByShippingMethod(ShippingMethod shippingMethod) {
        return getShipmentTypeShippingMethodsByShippingMethod(shippingMethod, EntityPermission.READ_ONLY);
    }
    
    public List<ShipmentTypeShippingMethod> getShipmentTypeShippingMethodsByShippingMethodForUpdate(ShippingMethod shippingMethod) {
        return getShipmentTypeShippingMethodsByShippingMethod(shippingMethod, EntityPermission.READ_WRITE);
    }
    
    public List<ShipmentTypeShippingMethodTransfer> getShipmentTypeShippingMethodTransfers(UserVisit userVisit, Collection<ShipmentTypeShippingMethod> shipmentTypeShippingMethods) {
        List<ShipmentTypeShippingMethodTransfer> shipmentTypeShippingMethodTransfers = new ArrayList<>(shipmentTypeShippingMethods.size());
        
        shipmentTypeShippingMethods.forEach((shipmentTypeShippingMethod) ->
                shipmentTypeShippingMethodTransfers.add(shipmentTypeShippingMethodTransferCache.getTransfer(userVisit, shipmentTypeShippingMethod))
        );
        
        return shipmentTypeShippingMethodTransfers;
    }
    
    public List<ShipmentTypeShippingMethodTransfer> getShipmentTypeShippingMethodTransfersByShipmentType(UserVisit userVisit, ShipmentType shipmentType) {
        return getShipmentTypeShippingMethodTransfers(userVisit, getShipmentTypeShippingMethodsByShipmentType(shipmentType));
    }
    
    public List<ShipmentTypeShippingMethodTransfer> getShipmentTypeShippingMethodTransfersByShippingMethod(UserVisit userVisit, ShippingMethod shippingMethod) {
        return getShipmentTypeShippingMethodTransfers(userVisit, getShipmentTypeShippingMethodsByShippingMethod(shippingMethod));
    }
    
    public ShipmentTypeShippingMethodTransfer getShipmentTypeShippingMethodTransfer(UserVisit userVisit, ShipmentTypeShippingMethod shipmentTypeShippingMethod) {
        return shipmentTypeShippingMethodTransferCache.getTransfer(userVisit, shipmentTypeShippingMethod);
    }
    
    private void updateShipmentTypeShippingMethodFromValue(ShipmentTypeShippingMethodValue shipmentTypeShippingMethodValue, boolean checkDefault, BasePK updatedBy) {
        if(shipmentTypeShippingMethodValue.hasBeenModified()) {
            var shipmentTypeShippingMethod = shipmentTypeShippingMethodFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     shipmentTypeShippingMethodValue.getPrimaryKey());
            
            shipmentTypeShippingMethod.setThruTime(session.getStartTime());
            shipmentTypeShippingMethod.store();

            var shipmentType = shipmentTypeShippingMethod.getShipmentType(); // Not Updated
            var shipmentTypePK = shipmentType.getPrimaryKey(); // Not Updated
            var shippingMethodPK = shipmentTypeShippingMethod.getShippingMethodPK(); // Not Updated
            var isDefault = shipmentTypeShippingMethodValue.getIsDefault();
            var sortOrder = shipmentTypeShippingMethodValue.getSortOrder();
            
            if(checkDefault) {
                var defaultShipmentTypeShippingMethod = getDefaultShipmentTypeShippingMethod(shipmentType);
                var defaultFound = defaultShipmentTypeShippingMethod != null && !defaultShipmentTypeShippingMethod.equals(shipmentTypeShippingMethod);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultShipmentTypeShippingMethodValue = getDefaultShipmentTypeShippingMethodValueForUpdate(shipmentType);
                    
                    defaultShipmentTypeShippingMethodValue.setIsDefault(false);
                    updateShipmentTypeShippingMethodFromValue(defaultShipmentTypeShippingMethodValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            shipmentTypeShippingMethod = shipmentTypeShippingMethodFactory.create(shipmentTypePK, shippingMethodPK,
                    isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(shippingMethodPK, EventTypes.MODIFY, shipmentTypeShippingMethod.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void updateShipmentTypeShippingMethodFromValue(ShipmentTypeShippingMethodValue shipmentTypeShippingMethodValue, BasePK updatedBy) {
        updateShipmentTypeShippingMethodFromValue(shipmentTypeShippingMethodValue, true, updatedBy);
    }
    
    public void deleteShipmentTypeShippingMethod(ShipmentTypeShippingMethod shipmentTypeShippingMethod, BasePK deletedBy) {
        shipmentTypeShippingMethod.setThruTime(session.getStartTime());
        shipmentTypeShippingMethod.store();
        
        // Check for default, and pick one if necessary
        var shipmentType = shipmentTypeShippingMethod.getShipmentType();
        var defaultShipmentTypeShippingMethod = getDefaultShipmentTypeShippingMethod(shipmentType);
        if(defaultShipmentTypeShippingMethod == null) {
            var shipmentTypeShippingMethods = getShipmentTypeShippingMethodsByShipmentTypeForUpdate(shipmentType);
            
            if(!shipmentTypeShippingMethods.isEmpty()) {
                var iter = shipmentTypeShippingMethods.iterator();
                if(iter.hasNext()) {
                    defaultShipmentTypeShippingMethod = iter.next();
                }
                var shipmentTypeShippingMethodValue = defaultShipmentTypeShippingMethod.getShipmentTypeShippingMethodValue().clone();
                
                shipmentTypeShippingMethodValue.setIsDefault(true);
                updateShipmentTypeShippingMethodFromValue(shipmentTypeShippingMethodValue, false, deletedBy);
            }
        }
        
        sendEvent(shipmentTypeShippingMethod.getShippingMethodPK(), EventTypes.MODIFY, shipmentTypeShippingMethod.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteShipmentTypeShippingMethods(List<ShipmentTypeShippingMethod> shipmentTypeShippingMethods, BasePK deletedBy) {
        shipmentTypeShippingMethods.forEach((shipmentTypeShippingMethod) -> 
                deleteShipmentTypeShippingMethod(shipmentTypeShippingMethod, deletedBy)
        );
    }
    
    public void deleteShipmentTypeShippingMethodsByShipmentType(ShipmentType shipmentType, BasePK deletedBy) {
        deleteShipmentTypeShippingMethods(getShipmentTypeShippingMethodsByShipmentTypeForUpdate(shipmentType), deletedBy);
    }
    
    public void deleteShipmentTypeShippingMethodsByShippingMethod(ShippingMethod shippingMethod, BasePK deletedBy) {
        deleteShipmentTypeShippingMethods(getShipmentTypeShippingMethodsByShippingMethodForUpdate(shippingMethod), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Shipment Times
    // --------------------------------------------------------------------------------

    @Inject
    protected ShipmentTimeFactory shipmentTimeFactory;

    @Inject
    private ShipmentTimeTransferCache shipmentTimeTransferCache;

    public ShipmentTime createShipmentTime(Shipment shipment, ShipmentTimeType shipmentTimeType, Long time, BasePK createdBy) {
        var shipmentTime = shipmentTimeFactory.create(shipment, shipmentTimeType, time, session.getStartTime(), Session.MAX_TIME);

        sendEvent(shipment.getPrimaryKey(), EventTypes.MODIFY, shipmentTime.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return shipmentTime;
    }

    public long countShipmentTimesByShipment(Shipment shipment) {
        return session.getDslContext()
                .selectCount()
                .from(ShipmentTimes)
                .where(ShipmentTimes.SHIPMENT.eq(shipment.getPrimaryKey()),
                        ShipmentTimes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countShipmentTimesByShipmentTimeType(ShipmentTimeType shipmentTimeType) {
        return session.getDslContext()
                .selectCount()
                .from(ShipmentTimes)
                .where(ShipmentTimes.SHIPMENT_TIME_TYPE.eq(shipmentTimeType.getPrimaryKey()),
                        ShipmentTimes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private ShipmentTime getShipmentTime(Shipment shipment, ShipmentTimeType shipmentTimeType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ShipmentTimes.fields())
                .from(ShipmentTimes)
                .where(ShipmentTimes.SHIPMENT.eq(shipment.getPrimaryKey()),
                        ShipmentTimes.SHIPMENT_TIME_TYPE.eq(shipmentTimeType.getPrimaryKey()),
                        ShipmentTimes.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return shipmentTimeFactory.getEntityFromQuery(entityPermission, query);
    }

    public ShipmentTime getShipmentTime(Shipment shipment, ShipmentTimeType shipmentTimeType) {
        return getShipmentTime(shipment, shipmentTimeType, EntityPermission.READ_ONLY);
    }

    public ShipmentTime getShipmentTimeForUpdate(Shipment shipment, ShipmentTimeType shipmentTimeType) {
        return getShipmentTime(shipment, shipmentTimeType, EntityPermission.READ_WRITE);
    }

    public ShipmentTimeValue getShipmentTimeValue(ShipmentTime shipmentTime) {
        return shipmentTime == null? null: shipmentTime.getShipmentTimeValue().clone();
    }

    public ShipmentTimeValue getShipmentTimeValueForUpdate(Shipment shipment, ShipmentTimeType shipmentTimeType) {
        return getShipmentTimeValue(getShipmentTimeForUpdate(shipment, shipmentTimeType));
    }

    private List<ShipmentTime> getShipmentTimesByShipment(Shipment shipment, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ShipmentTimes.fields())
                    .from(ShipmentTimes)
                    .join(ShipmentTimeTypes).onKey(SHIPMENT_TIMES_SHIPMENT_TIME_TYPE_FK)
                    .join(ShipmentTimeTypeDetails).onKey(SHIPMENT_TIME_TYPES_LAST_DETAIL_FK)
                    .where(ShipmentTimes.SHIPMENT.eq(shipment.getPrimaryKey()),
                            ShipmentTimes.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ShipmentTimeTypeDetails.SORT_ORDER, ShipmentTimeTypeDetails.SHIPMENT_TIME_TYPE_NAME),
                    ShipmentTimeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ShipmentTimes.fields())
                    .from(ShipmentTimes)
                    .where(ShipmentTimes.SHIPMENT.eq(shipment.getPrimaryKey()),
                            ShipmentTimes.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return shipmentTimeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ShipmentTime> getShipmentTimesByShipment(Shipment shipment) {
        return getShipmentTimesByShipment(shipment, EntityPermission.READ_ONLY);
    }

    public List<ShipmentTime> getShipmentTimesByShipmentForUpdate(Shipment shipment) {
        return getShipmentTimesByShipment(shipment, EntityPermission.READ_WRITE);
    }

    private List<ShipmentTime> getShipmentTimesByShipmentTimeType(ShipmentTimeType shipmentTimeType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ShipmentTimes.fields())
                    .from(ShipmentTimes)
                    .join(Shipments).onKey(SHIPMENT_TIMES_SHIPMENT_FK)
                    .join(ShipmentDetails).onKey(SHIPMENTS_LAST_DETAIL_FK)
                    .where(ShipmentTimes.SHIPMENT_TIME_TYPE.eq(shipmentTimeType.getPrimaryKey()),
                            ShipmentTimes.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ShipmentDetails.SHIPMENT_NAME),
                    ShipmentTimeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ShipmentTimes.fields())
                    .from(ShipmentTimes)
                    .where(ShipmentTimes.SHIPMENT_TIME_TYPE.eq(shipmentTimeType.getPrimaryKey()),
                            ShipmentTimes.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return shipmentTimeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ShipmentTime> getShipmentTimesByShipmentTimeType(ShipmentTimeType shipmentTimeType) {
        return getShipmentTimesByShipmentTimeType(shipmentTimeType, EntityPermission.READ_ONLY);
    }

    public List<ShipmentTime> getShipmentTimesByShipmentTimeTypeForUpdate(ShipmentTimeType shipmentTimeType) {
        return getShipmentTimesByShipmentTimeType(shipmentTimeType, EntityPermission.READ_WRITE);
    }

    public ShipmentTimeTransfer getShipmentTimeTransfer(UserVisit userVisit, ShipmentTime shipmentTime) {
        return shipmentTimeTransferCache.getTransfer(userVisit, shipmentTime);
    }

    public List<ShipmentTimeTransfer> getShipmentTimeTransfers(UserVisit userVisit, Collection<ShipmentTime> shipmentTimes) {
        List<ShipmentTimeTransfer> shipmentTimeTransfers = new ArrayList<>(shipmentTimes.size());

        shipmentTimes.forEach((shipmentTime) ->
                shipmentTimeTransfers.add(shipmentTimeTransferCache.getTransfer(userVisit, shipmentTime))
        );

        return shipmentTimeTransfers;
    }

    public List<ShipmentTimeTransfer> getShipmentTimeTransfersByShipment(UserVisit userVisit, Shipment shipment) {
        return getShipmentTimeTransfers(userVisit, getShipmentTimesByShipment(shipment));
    }

    public List<ShipmentTimeTransfer> getShipmentTimeTransfersByShipmentTimeType(UserVisit userVisit, ShipmentTimeType shipmentTimeType) {
        return getShipmentTimeTransfers(userVisit, getShipmentTimesByShipmentTimeType(shipmentTimeType));
    }

    public void updateShipmentTimeFromValue(ShipmentTimeValue shipmentTimeValue, BasePK updatedBy) {
        if(shipmentTimeValue.hasBeenModified()) {
            var shipmentTime = shipmentTimeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    shipmentTimeValue.getPrimaryKey());

            shipmentTime.setThruTime(session.getStartTime());
            shipmentTime.store();

            var shipmentPK = shipmentTime.getShipmentPK(); // Not updated
            var shipmentTimeTypePK = shipmentTime.getShipmentTimeTypePK(); // Not updated
            var time = shipmentTimeValue.getTime();

            shipmentTime = shipmentTimeFactory.create(shipmentPK, shipmentTimeTypePK, time, session.getStartTime(), Session.MAX_TIME);

            sendEvent(shipmentPK, EventTypes.MODIFY, shipmentTime.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteShipmentTime(ShipmentTime shipmentTime, BasePK deletedBy) {
        shipmentTime.setThruTime(session.getStartTime());

        sendEvent(shipmentTime.getShipmentTimeTypePK(), EventTypes.MODIFY, shipmentTime.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteShipmentTimes(List<ShipmentTime> shipmentTimes, BasePK deletedBy) {
        shipmentTimes.forEach((shipmentTime) -> 
                deleteShipmentTime(shipmentTime, deletedBy)
        );
    }

    public void deleteShipmentTimesByShipment(Shipment shipment, BasePK deletedBy) {
        deleteShipmentTimes(getShipmentTimesByShipmentForUpdate(shipment), deletedBy);
    }

    public void deleteShipmentTimesByShipmentTimeType(ShipmentTimeType shipmentTimeType, BasePK deletedBy) {
        deleteShipmentTimes(getShipmentTimesByShipmentTimeTypeForUpdate(shipmentTimeType), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Shipment Alias Types
    // --------------------------------------------------------------------------------

    @Inject
    protected ShipmentAliasTypeDetailFactory shipmentAliasTypeDetailFactory;

    @Inject
    protected ShipmentAliasTypeFactory shipmentAliasTypeFactory;

    @Inject
    private ShipmentAliasTypeTransferCache shipmentAliasTypeTransferCache;

    public ShipmentAliasType createShipmentAliasType(ShipmentType shipmentType, String shipmentAliasTypeName, String validationPattern, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var defaultShipmentAliasType = getDefaultShipmentAliasType(shipmentType);
        var defaultFound = defaultShipmentAliasType != null;

        if(defaultFound && isDefault) {
            var defaultShipmentAliasTypeDetailValue = getDefaultShipmentAliasTypeDetailValueForUpdate(shipmentType);

            defaultShipmentAliasTypeDetailValue.setIsDefault(false);
            updateShipmentAliasTypeFromValue(defaultShipmentAliasTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var shipmentAliasType = shipmentAliasTypeFactory.create();
        var shipmentAliasTypeDetail = shipmentAliasTypeDetailFactory.create(shipmentAliasType, shipmentType, shipmentAliasTypeName,
                validationPattern, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        shipmentAliasType = shipmentAliasTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE, shipmentAliasType.getPrimaryKey());
        shipmentAliasType.setActiveDetail(shipmentAliasTypeDetail);
        shipmentAliasType.setLastDetail(shipmentAliasTypeDetail);
        shipmentAliasType.store();

        sendEvent(shipmentAliasType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return shipmentAliasType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ShipmentAliasType */
    public ShipmentAliasType getShipmentAliasTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ShipmentAliasTypePK(entityInstance.getEntityUniqueId());

        return shipmentAliasTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public ShipmentAliasType getShipmentAliasTypeByEntityInstance(EntityInstance entityInstance) {
        return getShipmentAliasTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ShipmentAliasType getShipmentAliasTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getShipmentAliasTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countShipmentAliasTypesByShipmentType(final ShipmentType shipmentType) {
        return session.getDslContext()
                .selectCount()
                .from(ShipmentAliasTypes)
                .join(ShipmentAliasTypeDetails).onKey(SHIPMENT_ALIAS_TYPES_ACTIVE_DETAIL_FK)
                .where(ShipmentAliasTypeDetails.SHIPMENT_TYPE.eq(shipmentType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private ShipmentAliasType getShipmentAliasTypeByName(ShipmentType shipmentType, String shipmentAliasTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ShipmentAliasTypes.fields())
                .from(ShipmentAliasTypes)
                .join(ShipmentAliasTypeDetails).onKey(SHIPMENT_ALIAS_TYPES_ACTIVE_DETAIL_FK)
                .where(ShipmentAliasTypeDetails.SHIPMENT_TYPE.eq(shipmentType.getPrimaryKey()),
                        ShipmentAliasTypeDetails.SHIPMENT_ALIAS_TYPE_NAME.eq(shipmentAliasTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return shipmentAliasTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public ShipmentAliasType getShipmentAliasTypeByName(ShipmentType shipmentType, String shipmentAliasTypeName) {
        return getShipmentAliasTypeByName(shipmentType, shipmentAliasTypeName, EntityPermission.READ_ONLY);
    }

    public ShipmentAliasType getShipmentAliasTypeByNameForUpdate(ShipmentType shipmentType, String shipmentAliasTypeName) {
        return getShipmentAliasTypeByName(shipmentType, shipmentAliasTypeName, EntityPermission.READ_WRITE);
    }

    public ShipmentAliasTypeDetailValue getShipmentAliasTypeDetailValueForUpdate(ShipmentAliasType shipmentAliasType) {
        return shipmentAliasType == null? null: shipmentAliasType.getLastDetailForUpdate().getShipmentAliasTypeDetailValue().clone();
    }

    public ShipmentAliasTypeDetailValue getShipmentAliasTypeDetailValueByNameForUpdate(ShipmentType shipmentType,
            String shipmentAliasTypeName) {
        return getShipmentAliasTypeDetailValueForUpdate(getShipmentAliasTypeByNameForUpdate(shipmentType, shipmentAliasTypeName));
    }

    private ShipmentAliasType getDefaultShipmentAliasType(ShipmentType shipmentType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ShipmentAliasTypes.fields())
                .from(ShipmentAliasTypes)
                .join(ShipmentAliasTypeDetails).onKey(SHIPMENT_ALIAS_TYPES_ACTIVE_DETAIL_FK)
                .where(ShipmentAliasTypeDetails.SHIPMENT_TYPE.eq(shipmentType.getPrimaryKey()),
                        ShipmentAliasTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return shipmentAliasTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public ShipmentAliasType getDefaultShipmentAliasType(ShipmentType shipmentType) {
        return getDefaultShipmentAliasType(shipmentType, EntityPermission.READ_ONLY);
    }

    public ShipmentAliasType getDefaultShipmentAliasTypeForUpdate(ShipmentType shipmentType) {
        return getDefaultShipmentAliasType(shipmentType, EntityPermission.READ_WRITE);
    }

    public ShipmentAliasTypeDetailValue getDefaultShipmentAliasTypeDetailValueForUpdate(ShipmentType shipmentType) {
        return getDefaultShipmentAliasTypeForUpdate(shipmentType).getLastDetailForUpdate().getShipmentAliasTypeDetailValue().clone();
    }

    private List<ShipmentAliasType> getShipmentAliasTypes(ShipmentType shipmentType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ShipmentAliasTypes.fields())
                .from(ShipmentAliasTypes)
                .join(ShipmentAliasTypeDetails).onKey(SHIPMENT_ALIAS_TYPES_ACTIVE_DETAIL_FK)
                .where(ShipmentAliasTypeDetails.SHIPMENT_TYPE.eq(shipmentType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ShipmentAliasTypeDetails.SORT_ORDER, ShipmentAliasTypeDetails.SHIPMENT_ALIAS_TYPE_NAME),
                    ShipmentAliasTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return shipmentAliasTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ShipmentAliasType> getShipmentAliasTypes(ShipmentType shipmentType) {
        return getShipmentAliasTypes(shipmentType, EntityPermission.READ_ONLY);
    }

    public List<ShipmentAliasType> getShipmentAliasTypesForUpdate(ShipmentType shipmentType) {
        return getShipmentAliasTypes(shipmentType, EntityPermission.READ_WRITE);
    }

    public ShipmentAliasTypeTransfer getShipmentAliasTypeTransfer(UserVisit userVisit, ShipmentAliasType shipmentAliasType) {
        return shipmentAliasTypeTransferCache.getTransfer(userVisit, shipmentAliasType);
    }

    public List<ShipmentAliasTypeTransfer> getShipmentAliasTypeTransfers(UserVisit userVisit, Collection<ShipmentAliasType> shipmentAliasTypes) {
        List<ShipmentAliasTypeTransfer> shipmentAliasTypeTransfers = new ArrayList<>(shipmentAliasTypes.size());

        shipmentAliasTypes.forEach((shipmentAliasType) ->
                shipmentAliasTypeTransfers.add(shipmentAliasTypeTransferCache.getTransfer(userVisit, shipmentAliasType))
        );

        return shipmentAliasTypeTransfers;
    }

    public List<ShipmentAliasTypeTransfer> getShipmentAliasTypeTransfers(UserVisit userVisit, ShipmentType shipmentType) {
        return getShipmentAliasTypeTransfers(userVisit, getShipmentAliasTypes(shipmentType));
    }

    public ShipmentAliasTypeChoicesBean getShipmentAliasTypeChoices(String defaultShipmentAliasTypeChoice, Language language,
            boolean allowNullChoice, ShipmentType shipmentType) {
        var shipmentAliasTypes = getShipmentAliasTypes(shipmentType);
        var size = shipmentAliasTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultShipmentAliasTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var shipmentAliasType : shipmentAliasTypes) {
            var shipmentAliasTypeDetail = shipmentAliasType.getLastDetail();

            var label = getBestShipmentAliasTypeDescription(shipmentAliasType, language);
            var value = shipmentAliasTypeDetail.getShipmentAliasTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultShipmentAliasTypeChoice != null && defaultShipmentAliasTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && shipmentAliasTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ShipmentAliasTypeChoicesBean(labels, values, defaultValue);
    }

    private void updateShipmentAliasTypeFromValue(ShipmentAliasTypeDetailValue shipmentAliasTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(shipmentAliasTypeDetailValue.hasBeenModified()) {
            var shipmentAliasType = shipmentAliasTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    shipmentAliasTypeDetailValue.getShipmentAliasTypePK());
            var shipmentAliasTypeDetail = shipmentAliasType.getActiveDetailForUpdate();

            shipmentAliasTypeDetail.setThruTime(session.getStartTime());
            shipmentAliasTypeDetail.store();

            var shipmentAliasTypePK = shipmentAliasTypeDetail.getShipmentAliasTypePK();
            var shipmentType = shipmentAliasTypeDetail.getShipmentType();
            var shipmentTypePK = shipmentType.getPrimaryKey();
            var shipmentAliasTypeName = shipmentAliasTypeDetailValue.getShipmentAliasTypeName();
            var validationPattern = shipmentAliasTypeDetailValue.getValidationPattern();
            var isDefault = shipmentAliasTypeDetailValue.getIsDefault();
            var sortOrder = shipmentAliasTypeDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultShipmentAliasType = getDefaultShipmentAliasType(shipmentType);
                var defaultFound = defaultShipmentAliasType != null && !defaultShipmentAliasType.equals(shipmentAliasType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultShipmentAliasTypeDetailValue = getDefaultShipmentAliasTypeDetailValueForUpdate(shipmentType);

                    defaultShipmentAliasTypeDetailValue.setIsDefault(false);
                    updateShipmentAliasTypeFromValue(defaultShipmentAliasTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            shipmentAliasTypeDetail = shipmentAliasTypeDetailFactory.create(shipmentAliasTypePK, shipmentTypePK, shipmentAliasTypeName,
                    validationPattern, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

            shipmentAliasType.setActiveDetail(shipmentAliasTypeDetail);
            shipmentAliasType.setLastDetail(shipmentAliasTypeDetail);

            sendEvent(shipmentAliasTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateShipmentAliasTypeFromValue(ShipmentAliasTypeDetailValue shipmentAliasTypeDetailValue, BasePK updatedBy) {
        updateShipmentAliasTypeFromValue(shipmentAliasTypeDetailValue, true, updatedBy);
    }

    public void deleteShipmentAliasType(ShipmentAliasType shipmentAliasType, BasePK deletedBy) {
        deleteShipmentAliasesByShipmentAliasType(shipmentAliasType, deletedBy);
        deleteShipmentAliasTypeDescriptionsByShipmentAliasType(shipmentAliasType, deletedBy);

        var shipmentAliasTypeDetail = shipmentAliasType.getLastDetailForUpdate();
        shipmentAliasTypeDetail.setThruTime(session.getStartTime());
        shipmentAliasType.setActiveDetail(null);
        shipmentAliasType.store();

        // Check for default, and pick one if necessary
        var shipmentType = shipmentAliasTypeDetail.getShipmentType();
        var defaultShipmentAliasType = getDefaultShipmentAliasType(shipmentType);
        if(defaultShipmentAliasType == null) {
            var shipmentAliasTypes = getShipmentAliasTypesForUpdate(shipmentType);

            if(!shipmentAliasTypes.isEmpty()) {
                var iter = shipmentAliasTypes.iterator();
                if(iter.hasNext()) {
                    defaultShipmentAliasType = iter.next();
                }
                var shipmentAliasTypeDetailValue = Objects.requireNonNull(defaultShipmentAliasType).getLastDetailForUpdate().getShipmentAliasTypeDetailValue().clone();

                shipmentAliasTypeDetailValue.setIsDefault(true);
                updateShipmentAliasTypeFromValue(shipmentAliasTypeDetailValue, false, deletedBy);
            }
        }

        sendEvent(shipmentAliasType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteShipmentAliasTypes(List<ShipmentAliasType> shipmentAliasTypes, BasePK deletedBy) {
        shipmentAliasTypes.forEach((shipmentAliasType) -> 
                deleteShipmentAliasType(shipmentAliasType, deletedBy)
        );
    }

    public void deleteShipmentAliasTypesByShipmentType(ShipmentType shipmentType, BasePK deletedBy) {
        deleteShipmentAliasTypes(getShipmentAliasTypesForUpdate(shipmentType), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Shipment Alias Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected ShipmentAliasTypeDescriptionFactory shipmentAliasTypeDescriptionFactory;

    @Inject
    private ShipmentAliasTypeDescriptionTransferCache shipmentAliasTypeDescriptionTransferCache;

    public ShipmentAliasTypeDescription createShipmentAliasTypeDescription(ShipmentAliasType shipmentAliasType, Language language, String description, BasePK createdBy) {
        var shipmentAliasTypeDescription = shipmentAliasTypeDescriptionFactory.create(shipmentAliasType, language,
                description, session.getStartTime(), Session.MAX_TIME);

        sendEvent(shipmentAliasType.getPrimaryKey(), EventTypes.MODIFY, shipmentAliasTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return shipmentAliasTypeDescription;
    }

    private ShipmentAliasTypeDescription getShipmentAliasTypeDescription(ShipmentAliasType shipmentAliasType, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ShipmentAliasTypeDescriptions.fields())
                .from(ShipmentAliasTypeDescriptions)
                .where(ShipmentAliasTypeDescriptions.SHIPMENT_ALIAS_TYPE.eq(shipmentAliasType.getPrimaryKey()),
                        ShipmentAliasTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        ShipmentAliasTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return shipmentAliasTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }

    public ShipmentAliasTypeDescription getShipmentAliasTypeDescription(ShipmentAliasType shipmentAliasType, Language language) {
        return getShipmentAliasTypeDescription(shipmentAliasType, language, EntityPermission.READ_ONLY);
    }

    public ShipmentAliasTypeDescription getShipmentAliasTypeDescriptionForUpdate(ShipmentAliasType shipmentAliasType, Language language) {
        return getShipmentAliasTypeDescription(shipmentAliasType, language, EntityPermission.READ_WRITE);
    }

    public ShipmentAliasTypeDescriptionValue getShipmentAliasTypeDescriptionValue(ShipmentAliasTypeDescription shipmentAliasTypeDescription) {
        return shipmentAliasTypeDescription == null? null: shipmentAliasTypeDescription.getShipmentAliasTypeDescriptionValue().clone();
    }

    public ShipmentAliasTypeDescriptionValue getShipmentAliasTypeDescriptionValueForUpdate(ShipmentAliasType shipmentAliasType, Language language) {
        return getShipmentAliasTypeDescriptionValue(getShipmentAliasTypeDescriptionForUpdate(shipmentAliasType, language));
    }

    private List<ShipmentAliasTypeDescription> getShipmentAliasTypeDescriptionsByShipmentAliasType(ShipmentAliasType shipmentAliasType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ShipmentAliasTypeDescriptions.fields())
                    .from(ShipmentAliasTypeDescriptions)
                    .join(Languages).onKey(SHIPMENT_ALIAS_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(ShipmentAliasTypeDescriptions.SHIPMENT_ALIAS_TYPE.eq(shipmentAliasType.getPrimaryKey()),
                            ShipmentAliasTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    ShipmentAliasTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ShipmentAliasTypeDescriptions.fields())
                    .from(ShipmentAliasTypeDescriptions)
                    .where(ShipmentAliasTypeDescriptions.SHIPMENT_ALIAS_TYPE.eq(shipmentAliasType.getPrimaryKey()),
                            ShipmentAliasTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return shipmentAliasTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ShipmentAliasTypeDescription> getShipmentAliasTypeDescriptionsByShipmentAliasType(ShipmentAliasType shipmentAliasType) {
        return getShipmentAliasTypeDescriptionsByShipmentAliasType(shipmentAliasType, EntityPermission.READ_ONLY);
    }

    public List<ShipmentAliasTypeDescription> getShipmentAliasTypeDescriptionsByShipmentAliasTypeForUpdate(ShipmentAliasType shipmentAliasType) {
        return getShipmentAliasTypeDescriptionsByShipmentAliasType(shipmentAliasType, EntityPermission.READ_WRITE);
    }

    public String getBestShipmentAliasTypeDescription(ShipmentAliasType shipmentAliasType, Language language) {
        String description;
        var shipmentAliasTypeDescription = getShipmentAliasTypeDescription(shipmentAliasType, language);

        if(shipmentAliasTypeDescription == null && !language.getIsDefault()) {
            shipmentAliasTypeDescription = getShipmentAliasTypeDescription(shipmentAliasType, partyControl.getDefaultLanguage());
        }

        if(shipmentAliasTypeDescription == null) {
            description = shipmentAliasType.getLastDetail().getShipmentAliasTypeName();
        } else {
            description = shipmentAliasTypeDescription.getDescription();
        }

        return description;
    }

    public ShipmentAliasTypeDescriptionTransfer getShipmentAliasTypeDescriptionTransfer(UserVisit userVisit, ShipmentAliasTypeDescription shipmentAliasTypeDescription) {
        return shipmentAliasTypeDescriptionTransferCache.getTransfer(userVisit, shipmentAliasTypeDescription);
    }

    public List<ShipmentAliasTypeDescriptionTransfer> getShipmentAliasTypeDescriptionTransfersByShipmentAliasType(UserVisit userVisit, ShipmentAliasType shipmentAliasType) {
        var shipmentAliasTypeDescriptions = getShipmentAliasTypeDescriptionsByShipmentAliasType(shipmentAliasType);
        List<ShipmentAliasTypeDescriptionTransfer> shipmentAliasTypeDescriptionTransfers = new ArrayList<>(shipmentAliasTypeDescriptions.size());

        shipmentAliasTypeDescriptions.forEach((shipmentAliasTypeDescription) ->
                shipmentAliasTypeDescriptionTransfers.add(shipmentAliasTypeDescriptionTransferCache.getTransfer(userVisit, shipmentAliasTypeDescription))
        );

        return shipmentAliasTypeDescriptionTransfers;
    }

    public void updateShipmentAliasTypeDescriptionFromValue(ShipmentAliasTypeDescriptionValue shipmentAliasTypeDescriptionValue, BasePK updatedBy) {
        if(shipmentAliasTypeDescriptionValue.hasBeenModified()) {
            var shipmentAliasTypeDescription = shipmentAliasTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     shipmentAliasTypeDescriptionValue.getPrimaryKey());

            shipmentAliasTypeDescription.setThruTime(session.getStartTime());
            shipmentAliasTypeDescription.store();

            var shipmentAliasType = shipmentAliasTypeDescription.getShipmentAliasType();
            var language = shipmentAliasTypeDescription.getLanguage();
            var description = shipmentAliasTypeDescriptionValue.getDescription();

            shipmentAliasTypeDescription = shipmentAliasTypeDescriptionFactory.create(shipmentAliasType, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(shipmentAliasType.getPrimaryKey(), EventTypes.MODIFY, shipmentAliasTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteShipmentAliasTypeDescription(ShipmentAliasTypeDescription shipmentAliasTypeDescription, BasePK deletedBy) {
        shipmentAliasTypeDescription.setThruTime(session.getStartTime());

        sendEvent(shipmentAliasTypeDescription.getShipmentAliasTypePK(), EventTypes.MODIFY, shipmentAliasTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteShipmentAliasTypeDescriptionsByShipmentAliasType(ShipmentAliasType shipmentAliasType, BasePK deletedBy) {
        var shipmentAliasTypeDescriptions = getShipmentAliasTypeDescriptionsByShipmentAliasTypeForUpdate(shipmentAliasType);

        shipmentAliasTypeDescriptions.forEach((shipmentAliasTypeDescription) -> 
                deleteShipmentAliasTypeDescription(shipmentAliasTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Shipment Aliases
    // --------------------------------------------------------------------------------

    @Inject
    protected ShipmentAliasFactory shipmentAliasFactory;

    @Inject
    private ShipmentAliasTransferCache shipmentAliasTransferCache;

    public ShipmentAlias createShipmentAlias(Shipment shipment, ShipmentAliasType shipmentAliasType, String alias, BasePK createdBy) {
        var shipmentAlias = shipmentAliasFactory.create(shipment, shipmentAliasType, alias, session.getStartTime(), Session.MAX_TIME);

        sendEvent(shipment.getPrimaryKey(), EventTypes.MODIFY, shipmentAlias.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return shipmentAlias;
    }

    public long countShipmentAliasesByShipment(final Shipment shipment) {
        return session.getDslContext()
                .selectCount()
                .from(ShipmentAliases)
                .where(ShipmentAliases.SHIPMENT.eq(shipment.getPrimaryKey()),
                        ShipmentAliases.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countShipmentAliasesByShipmentAliasType(final ShipmentAliasType shipmentAliasType) {
        return session.getDslContext()
                .selectCount()
                .from(ShipmentAliases)
                .where(ShipmentAliases.SHIPMENT_ALIAS_TYPE.eq(shipmentAliasType.getPrimaryKey()),
                        ShipmentAliases.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private ShipmentAlias getShipmentAlias(Shipment shipment, ShipmentAliasType shipmentAliasType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ShipmentAliases.fields())
                .from(ShipmentAliases)
                .where(ShipmentAliases.SHIPMENT.eq(shipment.getPrimaryKey()),
                        ShipmentAliases.SHIPMENT_ALIAS_TYPE.eq(shipmentAliasType.getPrimaryKey()),
                        ShipmentAliases.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return shipmentAliasFactory.getEntityFromQuery(entityPermission, query);
    }

    public ShipmentAlias getShipmentAlias(Shipment shipment, ShipmentAliasType shipmentAliasType) {
        return getShipmentAlias(shipment, shipmentAliasType, EntityPermission.READ_ONLY);
    }

    public ShipmentAlias getShipmentAliasForUpdate(Shipment shipment, ShipmentAliasType shipmentAliasType) {
        return getShipmentAlias(shipment, shipmentAliasType, EntityPermission.READ_WRITE);
    }

    public ShipmentAliasValue getShipmentAliasValue(ShipmentAlias shipmentAlias) {
        return shipmentAlias == null? null: shipmentAlias.getShipmentAliasValue().clone();
    }

    public ShipmentAliasValue getShipmentAliasValueForUpdate(Shipment shipment, ShipmentAliasType shipmentAliasType) {
        return getShipmentAliasValue(getShipmentAliasForUpdate(shipment, shipmentAliasType));
    }

    private ShipmentAlias getShipmentAliasByAlias(ShipmentAliasType shipmentAliasType, String alias, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ShipmentAliases.fields())
                .from(ShipmentAliases)
                .where(ShipmentAliases.SHIPMENT_ALIAS_TYPE.eq(shipmentAliasType.getPrimaryKey()),
                        ShipmentAliases.ALIAS.eq(alias),
                        ShipmentAliases.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return shipmentAliasFactory.getEntityFromQuery(entityPermission, query);
    }

    public ShipmentAlias getShipmentAliasByAlias(ShipmentAliasType shipmentAliasType, String alias) {
        return getShipmentAliasByAlias(shipmentAliasType, alias, EntityPermission.READ_ONLY);
    }

    public ShipmentAlias getShipmentAliasByAliasForUpdate(ShipmentAliasType shipmentAliasType, String alias) {
        return getShipmentAliasByAlias(shipmentAliasType, alias, EntityPermission.READ_WRITE);
    }

    private List<ShipmentAlias> getShipmentAliasesByShipment(Shipment shipment, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ShipmentAliases.fields())
                    .from(ShipmentAliases)
                    .join(ShipmentAliasTypes).onKey(SHIPMENT_ALIASES_SHIPMENT_ALIAS_TYPE_FK)
                    .join(ShipmentAliasTypeDetails).onKey(SHIPMENT_ALIAS_TYPES_LAST_DETAIL_FK)
                    .where(ShipmentAliases.SHIPMENT.eq(shipment.getPrimaryKey()),
                            ShipmentAliases.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ShipmentAliasTypeDetails.SORT_ORDER, ShipmentAliasTypeDetails.SHIPMENT_ALIAS_TYPE_NAME),
                    ShipmentAliasFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ShipmentAliases.fields())
                    .from(ShipmentAliases)
                    .where(ShipmentAliases.SHIPMENT.eq(shipment.getPrimaryKey()),
                            ShipmentAliases.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return shipmentAliasFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ShipmentAlias> getShipmentAliasesByShipment(Shipment shipment) {
        return getShipmentAliasesByShipment(shipment, EntityPermission.READ_ONLY);
    }

    public List<ShipmentAlias> getShipmentAliasesByShipmentForUpdate(Shipment shipment) {
        return getShipmentAliasesByShipment(shipment, EntityPermission.READ_WRITE);
    }

    private List<ShipmentAlias> getShipmentAliasesByShipmentAliasType(ShipmentAliasType shipmentAliasType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ShipmentAliases.fields())
                    .from(ShipmentAliases)
                    .join(Shipments).onKey(SHIPMENT_ALIASES_SHIPMENT_FK)
                    .join(ShipmentDetails).onKey(SHIPMENTS_LAST_DETAIL_FK)
                    .where(ShipmentAliases.SHIPMENT_ALIAS_TYPE.eq(shipmentAliasType.getPrimaryKey()),
                            ShipmentAliases.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ShipmentDetails.SHIPMENT_NAME),
                    ShipmentAliasFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ShipmentAliases.fields())
                    .from(ShipmentAliases)
                    .where(ShipmentAliases.SHIPMENT_ALIAS_TYPE.eq(shipmentAliasType.getPrimaryKey()),
                            ShipmentAliases.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return shipmentAliasFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ShipmentAlias> getShipmentAliasesByShipmentAliasType(ShipmentAliasType shipmentAliasType) {
        return getShipmentAliasesByShipmentAliasType(shipmentAliasType, EntityPermission.READ_ONLY);
    }

    public List<ShipmentAlias> getShipmentAliasesByShipmentAliasTypeForUpdate(ShipmentAliasType shipmentAliasType) {
        return getShipmentAliasesByShipmentAliasType(shipmentAliasType, EntityPermission.READ_WRITE);
    }

    public ShipmentAliasTransfer getShipmentAliasTransfer(UserVisit userVisit, ShipmentAlias shipmentAlias) {
        return shipmentAliasTransferCache.getTransfer(userVisit, shipmentAlias);
    }

    public List<ShipmentAliasTransfer> getShipmentAliasTransfersByShipment(UserVisit userVisit, Shipment shipment) {
        return getShipmentAliasTransfers(userVisit, getShipmentAliasesByShipment(shipment));
    }

    public List<ShipmentAliasTransfer> getShipmentAliasTransfers(UserVisit userVisit, Collection<ShipmentAlias> shipmentAliases) {
        List<ShipmentAliasTransfer> shipmentAliasTransfers = new ArrayList<>(shipmentAliases.size());

        shipmentAliases.forEach((shipmentAlias) ->
                shipmentAliasTransfers.add(getShipmentAliasTransfer(userVisit, shipmentAlias))
        );

        return shipmentAliasTransfers;
    }

    public void updateShipmentAliasFromValue(ShipmentAliasValue shipmentAliasValue, BasePK updatedBy) {
        if(shipmentAliasValue.hasBeenModified()) {
            var shipmentAlias = shipmentAliasFactory.getEntityFromPK(EntityPermission.READ_WRITE, shipmentAliasValue.getPrimaryKey());

            shipmentAlias.setThruTime(session.getStartTime());
            shipmentAlias.store();

            var shipmentPK = shipmentAlias.getShipmentPK();
            var shipmentAliasTypePK = shipmentAlias.getShipmentAliasTypePK();
            var alias  = shipmentAliasValue.getAlias();

            shipmentAlias = shipmentAliasFactory.create(shipmentPK, shipmentAliasTypePK, alias, session.getStartTime(), Session.MAX_TIME);

            sendEvent(shipmentPK, EventTypes.MODIFY, shipmentAlias.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteShipmentAlias(ShipmentAlias shipmentAlias, BasePK deletedBy) {
        shipmentAlias.setThruTime(session.getStartTime());

        sendEvent(shipmentAlias.getShipmentPK(), EventTypes.MODIFY, shipmentAlias.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteShipmentAliasesByShipmentAliasType(ShipmentAliasType shipmentAliasType, BasePK deletedBy) {
        var shipmentaliases = getShipmentAliasesByShipmentAliasTypeForUpdate(shipmentAliasType);

        shipmentaliases.forEach((shipmentAlias) -> 
                deleteShipmentAlias(shipmentAlias, deletedBy)
        );
    }

    public void deleteShipmentAliasesByShipment(Shipment shipment, BasePK deletedBy) {
        var shipmentaliases = getShipmentAliasesByShipmentForUpdate(shipment);

        shipmentaliases.forEach((shipmentAlias) -> 
                deleteShipmentAlias(shipmentAlias, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Shipments
    // --------------------------------------------------------------------------------

    public Shipment getShipmentByName(ShipmentType shipmentType, String shipmentName) {
        // TODO
        return null;
    }

    public ShipmentTransfer getShipmentTransfer(UserVisit userVisit, Shipment shipment) {
        // TODO
        return null;
    }

    public void deleteShipmentsByPartyContactMechanism(PartyContactMechanism partyContactMechanism, BasePK deletedBy) {
        // TODO
    }
    
}
