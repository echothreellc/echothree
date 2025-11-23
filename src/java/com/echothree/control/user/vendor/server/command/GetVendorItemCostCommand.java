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

package com.echothree.control.user.vendor.server.command;

import com.echothree.control.user.vendor.common.form.GetVendorItemCostForm;
import com.echothree.control.user.vendor.common.result.VendorResultFactory;
import com.echothree.model.control.inventory.common.InventoryConstants;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.vendor.server.entity.Vendor;
import com.echothree.model.data.vendor.server.entity.VendorItemCost;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GetVendorItemCostCommand
        extends BaseSingleEntityCommand<VendorItemCost, GetVendorItemCostForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.VendorItemCost.name(), SecurityRoles.Review.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("VendorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("VendorItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryConditionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }

    /** Creates a new instance of GetVendorItemCostCommand */
    public GetVendorItemCostCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }

    @Override
    protected VendorItemCost getEntity() {
        var vendorName = form.getVendorName();
        var partyName = form.getPartyName();
        var parameterCount = (vendorName == null ? 0 : 1) + (partyName == null ? 0 : 1);
        VendorItemCost entity = null;

        if(parameterCount == 1) {
            var vendorControl = Session.getModelController(VendorControl.class);
            Vendor vendor = null;

            if(vendorName != null) {
                vendor = vendorControl.getVendorByName(vendorName);

                if(vendor == null) {
                    addExecutionError(ExecutionErrors.UnknownVendorName.name(), vendorName);
                }
            } else if(partyName != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                var party = partyControl.getPartyByName(partyName);

                if(party != null) {
                    if(party.getLastDetail().getPartyType().getPartyTypeName().equals(PartyTypes.VENDOR.name())) {
                        vendor = vendorControl.getVendor(party);
                    } else {
                        addExecutionError(ExecutionErrors.InvalidPartyType.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
                }
            }

            if(vendor != null) {
                var vendorParty = vendor.getParty();
                var vendorItemName = form.getVendorItemName();
                var vendorItem = vendorControl.getVendorItemByVendorPartyAndVendorItemName(vendorParty, vendorItemName);

                if(vendorItem != null) {
                    var inventoryControl = Session.getModelController(InventoryControl.class);
                    var inventoryConditionName = form.getInventoryConditionName();
                    var inventoryCondition = inventoryControl.getInventoryConditionByName(inventoryConditionName);

                    if(inventoryCondition != null) {
                        var inventoryConditionUseType = inventoryControl.getInventoryConditionUseTypeByName(InventoryConstants.InventoryConditionUseType_PURCHASE_ORDER);
                        var inventoryConditionUse = inventoryControl.getInventoryConditionUse(inventoryConditionUseType,
                                inventoryCondition);

                        if(inventoryConditionUse != null) {
                            var uomControl = Session.getModelController(UomControl.class);
                            var unitOfMeasureKind = vendorItem.getLastDetail().getItem().getLastDetail().getUnitOfMeasureKind();
                            var unitOfMeasureTypeName = form.getUnitOfMeasureTypeName();
                            var unitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(unitOfMeasureKind, unitOfMeasureTypeName);

                            if(unitOfMeasureType != null) {
                                entity = vendorControl.getVendorItemCost(vendorItem, inventoryCondition, unitOfMeasureType);

                                if(entity == null) {
                                    addExecutionError(ExecutionErrors.UnknownVendorItemCost.name(),
                                            vendor.getVendorName(), vendorItem.getLastDetail().getVendorItemName(),
                                            inventoryCondition.getLastDetail().getInventoryConditionName(),
                                            unitOfMeasureType.getLastDetail().getUnitOfMeasureTypeName());
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownUnitOfMeasureTypeName.name(), unitOfMeasureTypeName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.InvalidInventoryCondition.name(), inventoryCondition.getLastDetail().getInventoryConditionName());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownInventoryConditionName.name(), inventoryConditionName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownVendorItemName.name(), vendor.getVendorName(), vendorItemName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }

        return entity;
    }

    @Override
    protected BaseResult getResult(VendorItemCost entity) {
        var vendorControl = Session.getModelController(VendorControl.class);
        var result = VendorResultFactory.getGetVendorItemCostResult();

        if(entity != null) {
            result.setVendorItemCost(vendorControl.getVendorItemCostTransfer(getUserVisit(), entity));
        }

        return result;
    }

}
