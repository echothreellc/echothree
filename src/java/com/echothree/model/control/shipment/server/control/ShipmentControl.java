// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.sequence.server.entity.SequenceType;
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
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import com.echothree.util.server.cdi.CommandScope;

@CommandScope
public class ShipmentControl
        extends BaseShipmentControl {
    
    /** Creates a new instance of ShipmentControl */
    protected ShipmentControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Shipment Types
    // --------------------------------------------------------------------------------

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

        var shipmentType = ShipmentTypeFactory.getInstance().create();
        var shipmentTypeDetail = ShipmentTypeDetailFactory.getInstance().create(shipmentType, shipmentTypeName, parentShipmentType,
                shipmentSequenceType, shipmentPackageSequenceType, shipmentWorkflow, shipmentWorkflowEntrance, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        // Convert to R/W
        shipmentType = ShipmentTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                shipmentType.getPrimaryKey());
        shipmentType.setActiveDetail(shipmentTypeDetail);
        shipmentType.setLastDetail(shipmentTypeDetail);
        shipmentType.store();

        sendEvent(shipmentType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return shipmentType;
    }

    private static final Map<EntityPermission, String> getShipmentTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM shipmenttypes, shipmenttypedetails " +
                "WHERE shptyp_activedetailid = shptypdt_shipmenttypedetailid " +
                "AND shptypdt_shipmenttypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM shipmenttypes, shipmenttypedetails " +
                "WHERE shptyp_activedetailid = shptypdt_shipmenttypedetailid " +
                "AND shptypdt_shipmenttypename = ? " +
                "FOR UPDATE");
        getShipmentTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private ShipmentType getShipmentTypeByName(String shipmentTypeName, EntityPermission entityPermission) {
        return ShipmentTypeFactory.getInstance().getEntityFromQuery(entityPermission, getShipmentTypeByNameQueries, shipmentTypeName);
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

    private static final Map<EntityPermission, String> getDefaultShipmentTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM shipmenttypes, shipmenttypedetails " +
                "WHERE shptyp_activedetailid = shptypdt_shipmenttypedetailid " +
                "AND shptypdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM shipmenttypes, shipmenttypedetails " +
                "WHERE shptyp_activedetailid = shptypdt_shipmenttypedetailid " +
                "AND shptypdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultShipmentTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private ShipmentType getDefaultShipmentType(EntityPermission entityPermission) {
        return ShipmentTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultShipmentTypeQueries);
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

    private static final Map<EntityPermission, String> getShipmentTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM shipmenttypes, shipmenttypedetails " +
                "WHERE shptyp_activedetailid = shptypdt_shipmenttypedetailid " +
                "ORDER BY shptypdt_sortorder, shptypdt_shipmenttypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM shipmenttypes, shipmenttypedetails " +
                "WHERE shptyp_activedetailid = shptypdt_shipmenttypedetailid " +
                "FOR UPDATE");
        getShipmentTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ShipmentType> getShipmentTypes(EntityPermission entityPermission) {
        return ShipmentTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getShipmentTypesQueries);
    }

    public List<ShipmentType> getShipmentTypes() {
        return getShipmentTypes(EntityPermission.READ_ONLY);
    }

    public List<ShipmentType> getShipmentTypesForUpdate() {
        return getShipmentTypes(EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getShipmentTypesByParentShipmentTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM shipmenttypes, shipmenttypedetails " +
                "WHERE shptyp_activedetailid = shptypdt_shipmenttypedetailid AND shptypdt_parentshipmenttypeid = ? " +
                "ORDER BY shptypdt_sortorder, shptypdt_shipmenttypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM shipmenttypes, shipmenttypedetails " +
                "WHERE shptyp_activedetailid = shptypdt_shipmenttypedetailid AND shptypdt_parentshipmenttypeid = ? " +
                "FOR UPDATE");
        getShipmentTypesByParentShipmentTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ShipmentType> getShipmentTypesByParentShipmentType(ShipmentType parentShipmentType,
            EntityPermission entityPermission) {
        return ShipmentTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getShipmentTypesByParentShipmentTypeQueries,
                parentShipmentType);
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

    public List<ShipmentTypeTransfer> getShipmentTypeTransfers(UserVisit userVisit) {
        var shipmentTypes = getShipmentTypes();
        List<ShipmentTypeTransfer> shipmentTypeTransfers = new ArrayList<>(shipmentTypes.size());

        shipmentTypes.forEach((shipmentType) ->
                shipmentTypeTransfers.add(shipmentTypeTransferCache.getTransfer(userVisit, shipmentType))
        );

        return shipmentTypeTransfers;
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
            var shipmentType = ShipmentTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     shipmentTypeDetailValue.getShipmentTypePK());
            var shipmentTypeDetail = shipmentType.getActiveDetailForUpdate();

            shipmentTypeDetail.setThruTime(session.START_TIME_LONG);
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

            shipmentTypeDetail = ShipmentTypeDetailFactory.getInstance().create(shipmentTypePK, shipmentTypeName, parentShipmentTypePK, shipmentSequenceTypePK,
                    shipmentPackageSequenceTypePK, shipmentWorkflowPK, shipmentWorkflowEntrancePK, isDefault, sortOrder, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

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

        shipmentTypeDetail.setThruTime(session.START_TIME_LONG);
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

    public ShipmentTypeDescription createShipmentTypeDescription(ShipmentType shipmentType, Language language, String description, BasePK createdBy) {
        var shipmentTypeDescription = ShipmentTypeDescriptionFactory.getInstance().create(shipmentType, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(shipmentType.getPrimaryKey(), EventTypes.MODIFY, shipmentTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return shipmentTypeDescription;
    }

    private static final Map<EntityPermission, String> getShipmentTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM shipmenttypedescriptions " +
                "WHERE shptypd_shptyp_shipmenttypeid = ? AND shptypd_lang_languageid = ? AND shptypd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM shipmenttypedescriptions " +
                "WHERE shptypd_shptyp_shipmenttypeid = ? AND shptypd_lang_languageid = ? AND shptypd_thrutime = ? " +
                "FOR UPDATE");
        getShipmentTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private ShipmentTypeDescription getShipmentTypeDescription(ShipmentType shipmentType, Language language, EntityPermission entityPermission) {
        return ShipmentTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getShipmentTypeDescriptionQueries,
                shipmentType, language, Session.MAX_TIME);
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

    private static final Map<EntityPermission, String> getShipmentTypeDescriptionsByShipmentTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM shipmenttypedescriptions, languages " +
                "WHERE shptypd_shptyp_shipmenttypeid = ? AND shptypd_thrutime = ? AND shptypd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM shipmenttypedescriptions " +
                "WHERE shptypd_shptyp_shipmenttypeid = ? AND shptypd_thrutime = ? " +
                "FOR UPDATE");
        getShipmentTypeDescriptionsByShipmentTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ShipmentTypeDescription> getShipmentTypeDescriptionsByShipmentType(ShipmentType shipmentType, EntityPermission entityPermission) {
        return ShipmentTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getShipmentTypeDescriptionsByShipmentTypeQueries,
                shipmentType, Session.MAX_TIME);
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
            var shipmentTypeDescription = ShipmentTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    shipmentTypeDescriptionValue.getPrimaryKey());

            shipmentTypeDescription.setThruTime(session.START_TIME_LONG);
            shipmentTypeDescription.store();

            var shipmentType = shipmentTypeDescription.getShipmentType();
            var language = shipmentTypeDescription.getLanguage();
            var description = shipmentTypeDescriptionValue.getDescription();

            shipmentTypeDescription = ShipmentTypeDescriptionFactory.getInstance().create(shipmentType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(shipmentType.getPrimaryKey(), EventTypes.MODIFY, shipmentTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteShipmentTypeDescription(ShipmentTypeDescription shipmentTypeDescription, BasePK deletedBy) {
        shipmentTypeDescription.setThruTime(session.START_TIME_LONG);

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

        var shipmentTimeType = ShipmentTimeTypeFactory.getInstance().create();
        var shipmentTimeTypeDetail = ShipmentTimeTypeDetailFactory.getInstance().create(shipmentTimeType, shipmentType, shipmentTimeTypeName, isDefault,
                sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        shipmentTimeType = ShipmentTimeTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                shipmentTimeType.getPrimaryKey());
        shipmentTimeType.setActiveDetail(shipmentTimeTypeDetail);
        shipmentTimeType.setLastDetail(shipmentTimeTypeDetail);
        shipmentTimeType.store();

        sendEvent(shipmentTimeType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return shipmentTimeType;
    }

    private static final Map<EntityPermission, String> getShipmentTimeTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM shipmenttimetypes, shipmenttimetypedetails " +
                "WHERE shptimtyp_activedetailid = shptimtypdt_shipmenttimetypedetailid " +
                "AND shptimtypdt_shptyp_shipmenttypeid = ? AND shptimtypdt_shipmenttimetypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM shipmenttimetypes, shipmenttimetypedetails " +
                "WHERE shptimtyp_activedetailid = shptimtypdt_shipmenttimetypedetailid " +
                "AND shptimtypdt_shptyp_shipmenttypeid = ? AND shptimtypdt_shipmenttimetypename = ? " +
                "FOR UPDATE");
        getShipmentTimeTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private ShipmentTimeType getShipmentTimeTypeByName(ShipmentType shipmentType, String shipmentTimeTypeName, EntityPermission entityPermission) {
        return ShipmentTimeTypeFactory.getInstance().getEntityFromQuery(entityPermission, getShipmentTimeTypeByNameQueries,
                shipmentType, shipmentTimeTypeName);
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

    private static final Map<EntityPermission, String> getDefaultShipmentTimeTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM shipmenttimetypes, shipmenttimetypedetails " +
                "WHERE shptimtyp_activedetailid = shptimtypdt_shipmenttimetypedetailid " +
                "AND shptimtypdt_shptyp_shipmenttypeid = ? AND shptimtypdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM shipmenttimetypes, shipmenttimetypedetails " +
                "WHERE shptimtyp_activedetailid = shptimtypdt_shipmenttimetypedetailid " +
                "AND shptimtypdt_shptyp_shipmenttypeid = ? AND shptimtypdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultShipmentTimeTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private ShipmentTimeType getDefaultShipmentTimeType(ShipmentType shipmentType, EntityPermission entityPermission) {
        return ShipmentTimeTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultShipmentTimeTypeQueries,
                shipmentType);
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

    private static final Map<EntityPermission, String> getShipmentTimeTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM shipmenttimetypes, shipmenttimetypedetails " +
                "WHERE shptimtyp_activedetailid = shptimtypdt_shipmenttimetypedetailid " +
                "AND shptimtypdt_shptyp_shipmenttypeid = ? " +
                "ORDER BY shptimtypdt_sortorder, shptimtypdt_shipmenttimetypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM shipmenttimetypes, shipmenttimetypedetails " +
                "WHERE shptimtyp_activedetailid = shptimtypdt_shipmenttimetypedetailid " +
                "AND shptimtypdt_shptyp_shipmenttypeid = ? " +
                "FOR UPDATE");
        getShipmentTimeTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ShipmentTimeType> getShipmentTimeTypes(ShipmentType shipmentType, EntityPermission entityPermission) {
        return ShipmentTimeTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getShipmentTimeTypesQueries,
                shipmentType);
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

    public List<ShipmentTimeTypeTransfer> getShipmentTimeTypeTransfers(UserVisit userVisit, ShipmentType shipmentType) {
        var shipmentTimeTypes = getShipmentTimeTypes(shipmentType);
        List<ShipmentTimeTypeTransfer> shipmentTimeTypeTransfers = new ArrayList<>(shipmentTimeTypes.size());

        shipmentTimeTypes.forEach((shipmentTimeType) ->
                shipmentTimeTypeTransfers.add(shipmentTimeTypeTransferCache.getTransfer(userVisit, shipmentTimeType))
        );

        return shipmentTimeTypeTransfers;
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
            var shipmentTimeType = ShipmentTimeTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     shipmentTimeTypeDetailValue.getShipmentTimeTypePK());
            var shipmentTimeTypeDetail = shipmentTimeType.getActiveDetailForUpdate();

            shipmentTimeTypeDetail.setThruTime(session.START_TIME_LONG);
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

            shipmentTimeTypeDetail = ShipmentTimeTypeDetailFactory.getInstance().create(shipmentTimeTypePK, shipmentTypePK, shipmentTimeTypeName, isDefault, sortOrder,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

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
        shipmentTimeTypeDetail.setThruTime(session.START_TIME_LONG);
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

    public ShipmentTimeTypeDescription createShipmentTimeTypeDescription(ShipmentTimeType shipmentTimeType, Language language, String description, BasePK createdBy) {
        var shipmentTimeTypeDescription = ShipmentTimeTypeDescriptionFactory.getInstance().create(shipmentTimeType, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(shipmentTimeType.getPrimaryKey(), EventTypes.MODIFY, shipmentTimeTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return shipmentTimeTypeDescription;
    }

    private static final Map<EntityPermission, String> getShipmentTimeTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM shipmenttimetypedescriptions " +
                "WHERE shptimtypd_shptimtyp_shipmenttimetypeid = ? AND shptimtypd_lang_languageid = ? AND shptimtypd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM shipmenttimetypedescriptions " +
                "WHERE shptimtypd_shptimtyp_shipmenttimetypeid = ? AND shptimtypd_lang_languageid = ? AND shptimtypd_thrutime = ? " +
                "FOR UPDATE");
        getShipmentTimeTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private ShipmentTimeTypeDescription getShipmentTimeTypeDescription(ShipmentTimeType shipmentTimeType, Language language, EntityPermission entityPermission) {
        return ShipmentTimeTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getShipmentTimeTypeDescriptionQueries,
                shipmentTimeType, language, Session.MAX_TIME);
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

    private static final Map<EntityPermission, String> getShipmentTimeTypeDescriptionsByShipmentTimeTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM shipmenttimetypedescriptions, languages " +
                "WHERE shptimtypd_shptimtyp_shipmenttimetypeid = ? AND shptimtypd_thrutime = ? AND shptimtypd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM shipmenttimetypedescriptions " +
                "WHERE shptimtypd_shptimtyp_shipmenttimetypeid = ? AND shptimtypd_thrutime = ? " +
                "FOR UPDATE");
        getShipmentTimeTypeDescriptionsByShipmentTimeTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ShipmentTimeTypeDescription> getShipmentTimeTypeDescriptionsByShipmentTimeType(ShipmentTimeType shipmentTimeType, EntityPermission entityPermission) {
        return ShipmentTimeTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getShipmentTimeTypeDescriptionsByShipmentTimeTypeQueries,
                shipmentTimeType, Session.MAX_TIME);
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
            var shipmentTimeTypeDescription = ShipmentTimeTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    shipmentTimeTypeDescriptionValue.getPrimaryKey());

            shipmentTimeTypeDescription.setThruTime(session.START_TIME_LONG);
            shipmentTimeTypeDescription.store();

            var shipmentTimeType = shipmentTimeTypeDescription.getShipmentTimeType();
            var language = shipmentTimeTypeDescription.getLanguage();
            var description = shipmentTimeTypeDescriptionValue.getDescription();

            shipmentTimeTypeDescription = ShipmentTimeTypeDescriptionFactory.getInstance().create(shipmentTimeType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(shipmentTimeType.getPrimaryKey(), EventTypes.MODIFY, shipmentTimeTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteShipmentTimeTypeDescription(ShipmentTimeTypeDescription shipmentTimeTypeDescription, BasePK deletedBy) {
        shipmentTimeTypeDescription.setThruTime(session.START_TIME_LONG);

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

        var shipmentTypeShippingMethod = ShipmentTypeShippingMethodFactory.getInstance().create(shipmentType, shippingMethod,
                isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(shippingMethod.getPrimaryKey(), EventTypes.MODIFY, shipmentTypeShippingMethod.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return shipmentTypeShippingMethod;
    }
    
    private ShipmentTypeShippingMethod getShipmentTypeShippingMethod(ShipmentType shipmentType, ShippingMethod shippingMethod, EntityPermission entityPermission) {
        ShipmentTypeShippingMethod shipmentTypeShippingMethod;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM shipmenttypeshippingmethods " +
                        "WHERE shptypshm_shptyp_shipmenttypeid = ? AND shptypshm_shm_shippingmethodid = ? AND shptypshm_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM shipmenttypeshippingmethods " +
                        "WHERE shptypshm_shptyp_shipmenttypeid = ? AND shptypshm_shm_shippingmethodid = ? AND shptypshm_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ShipmentTypeShippingMethodFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, shipmentType.getPrimaryKey().getEntityId());
            ps.setLong(2, shippingMethod.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            shipmentTypeShippingMethod = ShipmentTypeShippingMethodFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return shipmentTypeShippingMethod;
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
        ShipmentTypeShippingMethod shipmentTypeShippingMethod;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM shipmenttypeshippingmethods " +
                        "WHERE shptypshm_shptyp_shipmenttypeid = ? AND shptypshm_isdefault = 1 AND shptypshm_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM shipmenttypeshippingmethods " +
                        "WHERE shptypshm_shptyp_shipmenttypeid = ? AND shptypshm_isdefault = 1 AND shptypshm_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ShipmentTypeShippingMethodFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, shipmentType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            shipmentTypeShippingMethod = ShipmentTypeShippingMethodFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return shipmentTypeShippingMethod;
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
        List<ShipmentTypeShippingMethod> shipmentTypeShippingMethods;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM shipmenttypeshippingmethods, shippingmethods, shippingmethoddetails " +
                        "WHERE shptypshm_shptyp_shipmenttypeid = ? AND shptypshm_thrutime = ? " +
                        "AND shptypshm_shm_shippingmethodid = shm_shippingmethodid AND shm_lastdetailid = shmdt_shippingmethoddetailid " +
                        "ORDER BY shmdt_sortorder, shmdt_shippingmethodname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM shipmenttypeshippingmethods " +
                        "WHERE shptypshm_shptyp_shipmenttypeid = ? AND shptypshm_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ShipmentTypeShippingMethodFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, shipmentType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            shipmentTypeShippingMethods = ShipmentTypeShippingMethodFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return shipmentTypeShippingMethods;
    }
    
    public List<ShipmentTypeShippingMethod> getShipmentTypeShippingMethodsByShipmentType(ShipmentType shipmentType) {
        return getShipmentTypeShippingMethodsByShipmentType(shipmentType, EntityPermission.READ_ONLY);
    }
    
    public List<ShipmentTypeShippingMethod> getShipmentTypeShippingMethodsByShipmentTypeForUpdate(ShipmentType shipmentType) {
        return getShipmentTypeShippingMethodsByShipmentType(shipmentType, EntityPermission.READ_WRITE);
    }
    
    private List<ShipmentTypeShippingMethod> getShipmentTypeShippingMethodsByShippingMethod(ShippingMethod shippingMethod, EntityPermission entityPermission) {
        List<ShipmentTypeShippingMethod> shipmentTypeShippingMethods;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM shipmenttypeshippingmethods, shipmenttypes, shipmenttypedetails, returnkinds, returnkinddetails " +
                        "WHERE shptypshm_shm_shippingmethodid = ? AND shptypshm_thrutime = ? " +
                        "AND shptypshm_shptyp_shipmenttypeid = shptyp_shipmenttypeid AND shptyp_lastdetailid = shptypdt_shipmenttypedetailid " +
                        "AND shptypdt_rtnk_returnkindid = rtnk_returnkindid AND rtnk_lastdetailid = rtnkdt_returnkinddetailid " +
                        "ORDER BY shptypdt_sortorder, shptypdt_shipmenttypename, rtnkdt_sortorder, rtnkdt_returnkindname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM shipmenttypeshippingmethods " +
                        "WHERE shptypshm_shm_shippingmethodid = ? AND shptypshm_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ShipmentTypeShippingMethodFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, shippingMethod.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            shipmentTypeShippingMethods = ShipmentTypeShippingMethodFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return shipmentTypeShippingMethods;
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
            var shipmentTypeShippingMethod = ShipmentTypeShippingMethodFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     shipmentTypeShippingMethodValue.getPrimaryKey());
            
            shipmentTypeShippingMethod.setThruTime(session.START_TIME_LONG);
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
            
            shipmentTypeShippingMethod = ShipmentTypeShippingMethodFactory.getInstance().create(shipmentTypePK, shippingMethodPK,
                    isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(shippingMethodPK, EventTypes.MODIFY, shipmentTypeShippingMethod.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void updateShipmentTypeShippingMethodFromValue(ShipmentTypeShippingMethodValue shipmentTypeShippingMethodValue, BasePK updatedBy) {
        updateShipmentTypeShippingMethodFromValue(shipmentTypeShippingMethodValue, true, updatedBy);
    }
    
    public void deleteShipmentTypeShippingMethod(ShipmentTypeShippingMethod shipmentTypeShippingMethod, BasePK deletedBy) {
        shipmentTypeShippingMethod.setThruTime(session.START_TIME_LONG);
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

    public ShipmentTime createShipmentTime(Shipment shipment, ShipmentTimeType shipmentTimeType, Long time, BasePK createdBy) {
        var shipmentTime = ShipmentTimeFactory.getInstance().create(shipment, shipmentTimeType, time, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(shipment.getPrimaryKey(), EventTypes.MODIFY, shipmentTime.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return shipmentTime;
    }

    public long countShipmentTimesByShipment(Shipment shipment) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM shipmenttimes " +
                "WHERE shptim_shp_shipmentid = ? AND shptim_thrutime = ?",
                shipment, Session.MAX_TIME_LONG);
    }

    public long countShipmentTimesByShipmentTimeType(ShipmentTimeType shipmentTimeType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM shipmenttimes " +
                "WHERE shptim_shptimtyp_shipmenttimetypeid = ? AND shptim_thrutime = ?",
                shipmentTimeType, Session.MAX_TIME_LONG);
    }

    private static final Map<EntityPermission, String> getShipmentTimeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM shipmenttimes " +
                "WHERE shptim_shp_shipmentid = ? AND shptim_shptimtyp_shipmenttimetypeid = ? AND shptim_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM shipmenttimes " +
                "WHERE shptim_shp_shipmentid = ? AND shptim_shptimtyp_shipmenttimetypeid = ? AND shptim_thrutime = ? " +
                "FOR UPDATE");
        getShipmentTimeQueries = Collections.unmodifiableMap(queryMap);
    }

    private ShipmentTime getShipmentTime(Shipment shipment, ShipmentTimeType shipmentTimeType, EntityPermission entityPermission) {
        return ShipmentTimeFactory.getInstance().getEntityFromQuery(entityPermission, getShipmentTimeQueries, shipment, shipmentTimeType, Session.MAX_TIME);
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

    private static final Map<EntityPermission, String> getShipmentTimesByShipmentQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM shipmenttimes, shipmenttimetypes, shipmenttimetypedetails " +
                "WHERE shptim_shp_shipmentid = ? AND shptim_thrutime = ? " +
                "AND shptim_shptimtyp_shipmenttimetypeid = shptimtyp_shipmenttimetypeid AND shptimtyp_activedetailid = shptimtypdt_shipmenttimetypedetailid " +
                "ORDER BY shptimtypdt_sortorder, shptimtypdt_shipmenttimetypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM shipmenttimes " +
                "WHERE shptim_shp_shipmentid = ? AND shptim_thrutime = ? " +
                "FOR UPDATE");
        getShipmentTimesByShipmentQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ShipmentTime> getShipmentTimesByShipment(Shipment shipment, EntityPermission entityPermission) {
        return ShipmentTimeFactory.getInstance().getEntitiesFromQuery(entityPermission, getShipmentTimesByShipmentQueries, shipment, Session.MAX_TIME);
    }

    public List<ShipmentTime> getShipmentTimesByShipment(Shipment shipment) {
        return getShipmentTimesByShipment(shipment, EntityPermission.READ_ONLY);
    }

    public List<ShipmentTime> getShipmentTimesByShipmentForUpdate(Shipment shipment) {
        return getShipmentTimesByShipment(shipment, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getShipmentTimesByShipmentTimeTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM shipmenttimes, shipments, shipmentdetails " +
                "WHERE shptim_shptimtyp_shipmenttimetypeid = ? AND shptim_thrutime = ? " +
                "AND shptim_shp_shipmentid = shptim_shp_shipmentid AND shp_activedetailid = shpdt_shipmentdetailid " +
                "ORDER BY shpdt_shipmentname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM shipmenttimes " +
                "WHERE shptim_shptimtyp_shipmenttimetypeid = ? AND shptim_thrutime = ? " +
                "FOR UPDATE");
        getShipmentTimesByShipmentTimeTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ShipmentTime> getShipmentTimesByShipmentTimeType(ShipmentTimeType shipmentTimeType, EntityPermission entityPermission) {
        return ShipmentTimeFactory.getInstance().getEntitiesFromQuery(entityPermission, getShipmentTimesByShipmentTimeTypeQueries, shipmentTimeType, Session.MAX_TIME);
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
            var shipmentTime = ShipmentTimeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    shipmentTimeValue.getPrimaryKey());

            shipmentTime.setThruTime(session.START_TIME_LONG);
            shipmentTime.store();

            var shipmentPK = shipmentTime.getShipmentPK(); // Not updated
            var shipmentTimeTypePK = shipmentTime.getShipmentTimeTypePK(); // Not updated
            var time = shipmentTimeValue.getTime();

            shipmentTime = ShipmentTimeFactory.getInstance().create(shipmentPK, shipmentTimeTypePK, time, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(shipmentPK, EventTypes.MODIFY, shipmentTime.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteShipmentTime(ShipmentTime shipmentTime, BasePK deletedBy) {
        shipmentTime.setThruTime(session.START_TIME_LONG);

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

        var shipmentAliasType = ShipmentAliasTypeFactory.getInstance().create();
        var shipmentAliasTypeDetail = ShipmentAliasTypeDetailFactory.getInstance().create(shipmentAliasType, shipmentType, shipmentAliasTypeName,
                validationPattern, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        shipmentAliasType = ShipmentAliasTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, shipmentAliasType.getPrimaryKey());
        shipmentAliasType.setActiveDetail(shipmentAliasTypeDetail);
        shipmentAliasType.setLastDetail(shipmentAliasTypeDetail);
        shipmentAliasType.store();

        sendEvent(shipmentAliasType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return shipmentAliasType;
    }

    private static final Map<EntityPermission, String> getShipmentAliasTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM shipmentaliastypes, shipmentaliastypedetails " +
                "WHERE shpaltyp_activedetailid = shpaltypdt_shipmentaliastypedetailid AND shpaltypdt_shptyp_shipmenttypeid = ? " +
                "AND shpaltypdt_shipmentaliastypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM shipmentaliastypes, shipmentaliastypedetails " +
                "WHERE shpaltyp_activedetailid = shpaltypdt_shipmentaliastypedetailid AND shpaltypdt_shptyp_shipmenttypeid = ? " +
                "AND shpaltypdt_shipmentaliastypename = ? " +
                "FOR UPDATE");
        getShipmentAliasTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private ShipmentAliasType getShipmentAliasTypeByName(ShipmentType shipmentType, String shipmentAliasTypeName, EntityPermission entityPermission) {
        return ShipmentAliasTypeFactory.getInstance().getEntityFromQuery(entityPermission, getShipmentAliasTypeByNameQueries,
                shipmentType, shipmentAliasTypeName);
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

    private static final Map<EntityPermission, String> getDefaultShipmentAliasTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM shipmentaliastypes, shipmentaliastypedetails " +
                "WHERE shpaltyp_activedetailid = shpaltypdt_shipmentaliastypedetailid AND shpaltypdt_shptyp_shipmenttypeid = ? " +
                "AND shpaltypdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM shipmentaliastypes, shipmentaliastypedetails " +
                "WHERE shpaltyp_activedetailid = shpaltypdt_shipmentaliastypedetailid AND shpaltypdt_shptyp_shipmenttypeid = ? " +
                "AND shpaltypdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultShipmentAliasTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private ShipmentAliasType getDefaultShipmentAliasType(ShipmentType shipmentType, EntityPermission entityPermission) {
        return ShipmentAliasTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultShipmentAliasTypeQueries, shipmentType);
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

    private static final Map<EntityPermission, String> getShipmentAliasTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM shipmentaliastypes, shipmentaliastypedetails " +
                "WHERE shpaltyp_activedetailid = shpaltypdt_shipmentaliastypedetailid AND shpaltypdt_shptyp_shipmenttypeid = ? " +
                "ORDER BY shpaltypdt_sortorder, shpaltypdt_shipmentaliastypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM shipmentaliastypes, shipmentaliastypedetails " +
                "WHERE shpaltyp_activedetailid = shpaltypdt_shipmentaliastypedetailid AND shpaltypdt_shptyp_shipmenttypeid = ? " +
                "FOR UPDATE");
        getShipmentAliasTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ShipmentAliasType> getShipmentAliasTypes(ShipmentType shipmentType, EntityPermission entityPermission) {
        return ShipmentAliasTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getShipmentAliasTypesQueries, shipmentType);
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

    public List<ShipmentAliasTypeTransfer> getShipmentAliasTypeTransfers(UserVisit userVisit, ShipmentType shipmentType) {
        var shipmentAliasTypes = getShipmentAliasTypes(shipmentType);
        List<ShipmentAliasTypeTransfer> shipmentAliasTypeTransfers = new ArrayList<>(shipmentAliasTypes.size());

        shipmentAliasTypes.forEach((shipmentAliasType) ->
                shipmentAliasTypeTransfers.add(shipmentAliasTypeTransferCache.getTransfer(userVisit, shipmentAliasType))
        );

        return shipmentAliasTypeTransfers;
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
            var shipmentAliasType = ShipmentAliasTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    shipmentAliasTypeDetailValue.getShipmentAliasTypePK());
            var shipmentAliasTypeDetail = shipmentAliasType.getActiveDetailForUpdate();

            shipmentAliasTypeDetail.setThruTime(session.START_TIME_LONG);
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

            shipmentAliasTypeDetail = ShipmentAliasTypeDetailFactory.getInstance().create(shipmentAliasTypePK, shipmentTypePK, shipmentAliasTypeName,
                    validationPattern, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

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
        shipmentAliasTypeDetail.setThruTime(session.START_TIME_LONG);
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

    public ShipmentAliasTypeDescription createShipmentAliasTypeDescription(ShipmentAliasType shipmentAliasType, Language language, String description, BasePK createdBy) {
        var shipmentAliasTypeDescription = ShipmentAliasTypeDescriptionFactory.getInstance().create(shipmentAliasType, language,
                description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(shipmentAliasType.getPrimaryKey(), EventTypes.MODIFY, shipmentAliasTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return shipmentAliasTypeDescription;
    }

    private static final Map<EntityPermission, String> getShipmentAliasTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM shipmentaliastypedescriptions " +
                "WHERE shpaltypd_shpaltyp_shipmentaliastypeid = ? AND shpaltypd_lang_languageid = ? AND shpaltypd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM shipmentaliastypedescriptions " +
                "WHERE shpaltypd_shpaltyp_shipmentaliastypeid = ? AND shpaltypd_lang_languageid = ? AND shpaltypd_thrutime = ? " +
                "FOR UPDATE");
        getShipmentAliasTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private ShipmentAliasTypeDescription getShipmentAliasTypeDescription(ShipmentAliasType shipmentAliasType, Language language, EntityPermission entityPermission) {
        return ShipmentAliasTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getShipmentAliasTypeDescriptionQueries,
                shipmentAliasType, language, Session.MAX_TIME);
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

    private static final Map<EntityPermission, String> getShipmentAliasTypeDescriptionsByShipmentAliasTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM shipmentaliastypedescriptions, languages " +
                "WHERE shpaltypd_shpaltyp_shipmentaliastypeid = ? AND shpaltypd_thrutime = ? AND shpaltypd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM shipmentaliastypedescriptions " +
                "WHERE shpaltypd_shpaltyp_shipmentaliastypeid = ? AND shpaltypd_thrutime = ? " +
                "FOR UPDATE");
        getShipmentAliasTypeDescriptionsByShipmentAliasTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ShipmentAliasTypeDescription> getShipmentAliasTypeDescriptionsByShipmentAliasType(ShipmentAliasType shipmentAliasType, EntityPermission entityPermission) {
        return ShipmentAliasTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getShipmentAliasTypeDescriptionsByShipmentAliasTypeQueries,
                shipmentAliasType, Session.MAX_TIME);
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
            var shipmentAliasTypeDescription = ShipmentAliasTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     shipmentAliasTypeDescriptionValue.getPrimaryKey());

            shipmentAliasTypeDescription.setThruTime(session.START_TIME_LONG);
            shipmentAliasTypeDescription.store();

            var shipmentAliasType = shipmentAliasTypeDescription.getShipmentAliasType();
            var language = shipmentAliasTypeDescription.getLanguage();
            var description = shipmentAliasTypeDescriptionValue.getDescription();

            shipmentAliasTypeDescription = ShipmentAliasTypeDescriptionFactory.getInstance().create(shipmentAliasType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(shipmentAliasType.getPrimaryKey(), EventTypes.MODIFY, shipmentAliasTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteShipmentAliasTypeDescription(ShipmentAliasTypeDescription shipmentAliasTypeDescription, BasePK deletedBy) {
        shipmentAliasTypeDescription.setThruTime(session.START_TIME_LONG);

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

    public ShipmentAlias createShipmentAlias(Shipment shipment, ShipmentAliasType shipmentAliasType, String alias, BasePK createdBy) {
        var shipmentAlias = ShipmentAliasFactory.getInstance().create(shipment, shipmentAliasType, alias, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(shipment.getPrimaryKey(), EventTypes.MODIFY, shipmentAlias.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return shipmentAlias;
    }

    private static final Map<EntityPermission, String> getShipmentAliasQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM shipmentaliases " +
                "WHERE shpal_shp_shipmentid = ? AND shpal_shpaltyp_shipmentaliastypeid = ? AND shpal_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM shipmentaliases " +
                "WHERE shpal_shp_shipmentid = ? AND shpal_shpaltyp_shipmentaliastypeid = ? AND shpal_thrutime = ? " +
                "FOR UPDATE");
        getShipmentAliasQueries = Collections.unmodifiableMap(queryMap);
    }

    private ShipmentAlias getShipmentAlias(Shipment shipment, ShipmentAliasType shipmentAliasType, EntityPermission entityPermission) {
        return ShipmentAliasFactory.getInstance().getEntityFromQuery(entityPermission, getShipmentAliasQueries,
                shipment, shipmentAliasType, Session.MAX_TIME);
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

    private static final Map<EntityPermission, String> getShipmentAliasByAliasQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM shipmentaliases " +
                "WHERE shpal_shpaltyp_shipmentaliastypeid = ? AND shpal_alias = ? AND shpal_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM shipmentaliases " +
                "WHERE shpal_shpaltyp_shipmentaliastypeid = ? AND shpal_alias = ? AND shpal_thrutime = ? " +
                "FOR UPDATE");
        getShipmentAliasByAliasQueries = Collections.unmodifiableMap(queryMap);
    }

    private ShipmentAlias getShipmentAliasByAlias(ShipmentAliasType shipmentAliasType, String alias, EntityPermission entityPermission) {
        return ShipmentAliasFactory.getInstance().getEntityFromQuery(entityPermission, getShipmentAliasByAliasQueries, shipmentAliasType, alias, Session.MAX_TIME);
    }

    public ShipmentAlias getShipmentAliasByAlias(ShipmentAliasType shipmentAliasType, String alias) {
        return getShipmentAliasByAlias(shipmentAliasType, alias, EntityPermission.READ_ONLY);
    }

    public ShipmentAlias getShipmentAliasByAliasForUpdate(ShipmentAliasType shipmentAliasType, String alias) {
        return getShipmentAliasByAlias(shipmentAliasType, alias, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getShipmentAliasesByShipmentQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM shipmentaliases, shipmentaliastypes, shipmentaliastypedetails " +
                "WHERE shpal_shp_shipmentid = ? AND shpal_thrutime = ? " +
                "AND shpal_shpaltyp_shipmentaliastypeid = shpaltyp_shipmentaliastypeid AND shpaltyp_lastdetailid = shpaltypdt_shipmentaliastypedetailid" +
                "ORDER BY shpaltypdt_sortorder, shpaltypdt_shipmentaliastypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM shipmentaliases " +
                "WHERE shpal_shp_shipmentid = ? AND shpal_thrutime = ? " +
                "FOR UPDATE");
        getShipmentAliasesByShipmentQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ShipmentAlias> getShipmentAliasesByShipment(Shipment shipment, EntityPermission entityPermission) {
        return ShipmentAliasFactory.getInstance().getEntitiesFromQuery(entityPermission, getShipmentAliasesByShipmentQueries,
                shipment, Session.MAX_TIME);
    }

    public List<ShipmentAlias> getShipmentAliasesByShipment(Shipment shipment) {
        return getShipmentAliasesByShipment(shipment, EntityPermission.READ_ONLY);
    }

    public List<ShipmentAlias> getShipmentAliasesByShipmentForUpdate(Shipment shipment) {
        return getShipmentAliasesByShipment(shipment, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getShipmentAliasesByShipmentAliasTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM shipmentaliases, shipmentes, shipmentdetails " +
                "WHERE shpal_shpaltyp_shipmentaliastypeid = ? AND shpal_thrutime = ? " +
                "AND shpal_shp_shipmentid = shp_shipmentid AND shp_lastdetailid = shpdt_shipmentdetailid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM shipmentaliases " +
                "WHERE shpal_shpaltyp_shipmentaliastypeid = ? AND shpal_thrutime = ? " +
                "FOR UPDATE");
        getShipmentAliasesByShipmentAliasTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ShipmentAlias> getShipmentAliasesByShipmentAliasType(ShipmentAliasType shipmentAliasType, EntityPermission entityPermission) {
        return ShipmentAliasFactory.getInstance().getEntitiesFromQuery(entityPermission, getShipmentAliasesByShipmentAliasTypeQueries,
                shipmentAliasType, Session.MAX_TIME);
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
        var shipmentaliases = getShipmentAliasesByShipment(shipment);
        List<ShipmentAliasTransfer> shipmentAliasTransfers = new ArrayList<>(shipmentaliases.size());

        shipmentaliases.forEach((shipmentAlias) ->
                shipmentAliasTransfers.add(shipmentAliasTransferCache.getTransfer(userVisit, shipmentAlias))
        );

        return shipmentAliasTransfers;
    }

    public void updateShipmentAliasFromValue(ShipmentAliasValue shipmentAliasValue, BasePK updatedBy) {
        if(shipmentAliasValue.hasBeenModified()) {
            var shipmentAlias = ShipmentAliasFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, shipmentAliasValue.getPrimaryKey());

            shipmentAlias.setThruTime(session.START_TIME_LONG);
            shipmentAlias.store();

            var shipmentPK = shipmentAlias.getShipmentPK();
            var shipmentAliasTypePK = shipmentAlias.getShipmentAliasTypePK();
            var alias  = shipmentAliasValue.getAlias();

            shipmentAlias = ShipmentAliasFactory.getInstance().create(shipmentPK, shipmentAliasTypePK, alias, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(shipmentPK, EventTypes.MODIFY, shipmentAlias.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteShipmentAlias(ShipmentAlias shipmentAlias, BasePK deletedBy) {
        shipmentAlias.setThruTime(session.START_TIME_LONG);

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
