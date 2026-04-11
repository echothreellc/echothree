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

package com.echothree.control.user.inventory.server.command;

import com.echothree.control.user.inventory.common.edit.InventoryEditFactory;
import com.echothree.control.user.inventory.common.edit.PartyInventoryLevelEdit;
import com.echothree.control.user.inventory.common.result.EditPartyInventoryLevelResult;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.control.user.inventory.common.spec.PartyInventoryLevelSpec;
import com.echothree.control.user.inventory.server.command.common.PartyInventoryLevelUtil;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.uom.server.logic.UnitOfMeasureTypeLogic;
import com.echothree.model.control.uom.server.util.Conversion;
import com.echothree.model.data.inventory.server.entity.PartyInventoryLevel;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditPartyInventoryLevelCommand
        extends BaseAbstractEditCommand<PartyInventoryLevelSpec, PartyInventoryLevelEdit, EditPartyInventoryLevelResult, PartyInventoryLevel, Item> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.PartyInventoryLevel.name(), SecurityRoles.Edit.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CompanyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("WarehouseName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryConditionName", FieldType.ENTITY_NAME, true, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("MinimumInventoryUnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("MinimumInventory", FieldType.UNSIGNED_LONG, false, null, null),
                new FieldDefinition("MaximumInventoryUnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("MaximumInventory", FieldType.UNSIGNED_LONG, false, null, null),
                new FieldDefinition("ReorderQuantityUnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ReorderQuantity", FieldType.UNSIGNED_LONG, false, null, null)
        );
    }

    @Inject
    PartyInventoryLevelUtil partyInventoryLevelUtil;

    /** Creates a new instance of EditPartyInventoryLevelCommand */
    public EditPartyInventoryLevelCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditPartyInventoryLevelResult getResult() {
        return InventoryResultFactory.getEditPartyInventoryLevelResult();
    }

    @Override
    public PartyInventoryLevelEdit getEdit() {
        return InventoryEditFactory.getPartyInventoryLevelEdit();
    }

    UnitOfMeasureKind unitOfMeasureKind;

    @Override
    public PartyInventoryLevel getEntity(EditPartyInventoryLevelResult result) {
        var itemControl = Session.getModelController(ItemControl.class);
        PartyInventoryLevel partyInventoryLevel = null;
        var party = partyInventoryLevelUtil.getParty(this, spec);

        if(party != null) {
            var itemName = spec.getItemName();
            var item = itemControl.getItemByName(itemName);

            if(item != null) {
                var partyTypeName = partyInventoryLevelUtil.getPartyTypeName(party);

                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.UPDATE)) {
                    unitOfMeasureKind = item.getLastDetail().getUnitOfMeasureKind();
                }

                if(partyTypeName.equals(PartyTypes.COMPANY.name())) {
                    if(!party.equals(item.getLastDetail().getCompanyParty())) {
                        addExecutionError(ExecutionErrors.InvalidCompany.name());
                    }
                }

                if(!hasExecutionErrors()) {
                    var inventoryControl = Session.getModelController(InventoryControl.class);
                    var inventoryConditionName = spec.getInventoryConditionName();
                    var inventoryCondition = inventoryControl.getInventoryConditionByName(inventoryConditionName);

                    if(inventoryCondition != null) {
                        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                            partyInventoryLevel = inventoryControl.getPartyInventoryLevel(party, item, inventoryCondition);
                        } else { // EditMode.UPDATE
                            partyInventoryLevel = inventoryControl.getPartyInventoryLevelForUpdate(party, item, inventoryCondition);
                        }

                        if(partyInventoryLevel == null) {
                            addExecutionError(ExecutionErrors.UnknownPartyInventoryLevel.name(), party.getLastDetail().getPartyName(), itemName, inventoryConditionName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownInventoryConditionName.name(), inventoryConditionName);
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownItemName.name(), itemName);
            }
        }

        return partyInventoryLevel;
    }

    @Override
    public Item getLockEntity(PartyInventoryLevel partyInventoryLevel) {
        return partyInventoryLevel.getItem();
    }

    @Override
    public void fillInResult(EditPartyInventoryLevelResult result, PartyInventoryLevel partyInventoryLevel) {
        var inventoryControl = Session.getModelController(InventoryControl.class);

        result.setPartyInventoryLevel(inventoryControl.getPartyInventoryLevelTransfer(getUserVisit(), partyInventoryLevel));
    }

    @Override
    public void doLock(PartyInventoryLevelEdit edit, PartyInventoryLevel partyInventoryLevel) {
        var uomControl = Session.getModelController(UomControl.class);

        minimumInventory = partyInventoryLevel.getMinimumInventory();
        var minimumInventoryConversion = minimumInventory == null ? null : new Conversion(uomControl, unitOfMeasureKind, minimumInventory).convertToHighestUnitOfMeasureType();

        maximumInventory = partyInventoryLevel.getMaximumInventory();
        var maximumInventoryConversion = maximumInventory == null ? null : new Conversion(uomControl, unitOfMeasureKind, maximumInventory).convertToHighestUnitOfMeasureType();

        reorderQuantity = partyInventoryLevel.getReorderQuantity();
        var reorderQuantityConversion = reorderQuantity == null ? null : new Conversion(uomControl, unitOfMeasureKind, reorderQuantity).convertToHighestUnitOfMeasureType();

        edit.setMinimumInventory(minimumInventoryConversion.getQuantity().toString());
        edit.setMinimumInventoryUnitOfMeasureTypeName(minimumInventoryConversion.getUnitOfMeasureType().getLastDetail().getUnitOfMeasureTypeName());
        edit.setMaximumInventory(maximumInventoryConversion.getQuantity().toString());
        edit.setMaximumInventoryUnitOfMeasureTypeName(maximumInventoryConversion.getUnitOfMeasureType().getLastDetail().getUnitOfMeasureTypeName());
        edit.setReorderQuantity(reorderQuantityConversion.getQuantity().toString());
        edit.setReorderQuantityUnitOfMeasureTypeName(reorderQuantityConversion.getUnitOfMeasureType().getLastDetail().getUnitOfMeasureTypeName());
    }

    Long minimumInventory;
    Long maximumInventory;
    Long reorderQuantity;

    @Override
    public void canUpdate(PartyInventoryLevel partyInventoryLevel) {
        var unitOfMeasureTypeLogic = UnitOfMeasureTypeLogic.getInstance();

        minimumInventory = unitOfMeasureTypeLogic.checkUnitOfMeasure(this, unitOfMeasureKind,
                edit.getMinimumInventory(), edit.getMinimumInventoryUnitOfMeasureTypeName(),
                null, ExecutionErrors.MissingRequiredMinimumInventory.name(), null, ExecutionErrors.MissingRequiredMinimumInventoryUnitOfMeasureTypeName.name(),
                null, ExecutionErrors.UnknownMinimumInventoryUnitOfMeasureTypeName.name());

        if(!hasExecutionErrors()) {
            maximumInventory = unitOfMeasureTypeLogic.checkUnitOfMeasure(this, unitOfMeasureKind,
                    edit.getMaximumInventory(), edit.getMaximumInventoryUnitOfMeasureTypeName(),
                    null, ExecutionErrors.MissingRequiredMaximumInventory.name(), null, ExecutionErrors.MissingRequiredMaximumInventoryUnitOfMeasureTypeName.name(),
                    null, ExecutionErrors.UnknownMaximumInventoryUnitOfMeasureTypeName.name());

            if(!hasExecutionErrors()) {
                reorderQuantity = unitOfMeasureTypeLogic.checkUnitOfMeasure(this, unitOfMeasureKind,
                        edit.getReorderQuantity(), edit.getReorderQuantityUnitOfMeasureTypeName(),
                        null, ExecutionErrors.MissingRequiredReorderQuantity.name(), null, ExecutionErrors.MissingRequiredReorderQuantityUnitOfMeasureTypeName.name(),
                        null, ExecutionErrors.UnknownReorderQuantityUnitOfMeasureTypeName.name());
            }
        }
    }

    @Override
    public void doUpdate(PartyInventoryLevel partyInventoryLevel) {
        var inventoryControl = Session.getModelController(InventoryControl.class);
        var partyInventoryLevelValue = inventoryControl.getPartyInventoryLevelValue(partyInventoryLevel);

        partyInventoryLevelValue.setMinimumInventory(minimumInventory);
        partyInventoryLevelValue.setMaximumInventory(maximumInventory);
        partyInventoryLevelValue.setReorderQuantity(reorderQuantity);

        inventoryControl.updatePartyInventoryLevelFromValue(partyInventoryLevelValue, getPartyPK());
    }

}
