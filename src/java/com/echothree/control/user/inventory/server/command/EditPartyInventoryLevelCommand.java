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

package com.echothree.control.user.inventory.server.command;

import com.echothree.control.user.inventory.common.edit.InventoryEditFactory;
import com.echothree.control.user.inventory.common.edit.PartyInventoryLevelEdit;
import com.echothree.control.user.inventory.common.form.EditPartyInventoryLevelForm;
import com.echothree.control.user.inventory.common.result.EditPartyInventoryLevelResult;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.control.user.inventory.common.spec.PartyInventoryLevelSpec;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.uom.server.logic.UnitOfMeasureTypeLogic;
import com.echothree.model.control.uom.server.util.Conversion;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.data.inventory.server.entity.PartyInventoryLevel;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditPartyInventoryLevelCommand
        extends BaseAbstractEditCommand<PartyInventoryLevelSpec, PartyInventoryLevelEdit, EditPartyInventoryLevelResult, PartyInventoryLevel, Item> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CompanyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("WarehouseName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryConditionName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("MinimumInventoryUnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("MinimumInventory", FieldType.UNSIGNED_LONG, false, null, null),
                new FieldDefinition("MaximumInventoryUnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("MaximumInventory", FieldType.UNSIGNED_LONG, false, null, null),
                new FieldDefinition("ReorderQuantityUnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ReorderQuantity", FieldType.UNSIGNED_LONG, false, null, null)
                ));
    }

    /** Creates a new instance of EditPartyInventoryLevelCommand */
    public EditPartyInventoryLevelCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditPartyInventoryLevelResult getResult() {
        return InventoryResultFactory.getEditPartyInventoryLevelResult();
    }

    @Override
    public PartyInventoryLevelEdit getEdit() {
        return InventoryEditFactory.getPartyInventoryLevelEdit();
    }

    // TODO: Duplicated from BasePartyInventoryLevelCommand
    protected String getPartyTypeName(final Party party) {
        return party.getLastDetail().getPartyType().getPartyTypeName();
    }

    protected Party getParty(final String partyName, final String companyName, final String warehouseName) {
        Party party = null;

        if(partyName != null || companyName != null) {
            var partyControl = Session.getModelController(PartyControl.class);

            if(partyName != null) {
                party = partyControl.getPartyByName(partyName);

                if(party != null) {
                    var partyTypeName = getPartyTypeName(party);

                    if(!partyTypeName.equals(PartyTypes.COMPANY.name())
                            && !partyTypeName.equals(PartyTypes.WAREHOUSE.name())) {
                        party = null;
                        addExecutionError(ExecutionErrors.InvalidPartyType.name());
                    }
                }  else {
                    addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
                }
            } else if(companyName != null) {
                var partyCompany = partyControl.getPartyCompanyByName(companyName);

                if(partyCompany != null) {
                    party = partyCompany.getParty();
                } else {
                    addExecutionError(ExecutionErrors.UnknownCompanyName.name(), companyName);
                }
            }
        } else if(warehouseName != null) {
            var warehouseControl = Session.getModelController(WarehouseControl.class);
            var warehouse = warehouseControl.getWarehouseByName(warehouseName);

            if(warehouse != null) {
                party = warehouse.getParty();
            } else {
                addExecutionError(ExecutionErrors.UnknownWarehouseName.name(), warehouseName);
            }
        }
        return party;
    }

    protected Party getParty(PartyInventoryLevelSpec spec) {
        var partyName = spec.getPartyName();
        var companyName = spec.getCompanyName();
        var warehouseName = spec.getWarehouseName();
        var parameterCount = (partyName == null ? 0 : 1) + (companyName == null ? 0 : 1) + (warehouseName == null ? 0 : 1);
        Party party = null;

        if(parameterCount == 1) {
            party = getParty(partyName, companyName, warehouseName);
        }  else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }

        return party;
    }

    UnitOfMeasureKind unitOfMeasureKind;

    @Override
    public PartyInventoryLevel getEntity(EditPartyInventoryLevelResult result) {
        var itemControl = Session.getModelController(ItemControl.class);
        PartyInventoryLevel partyInventoryLevel = null;
        var party = getParty(spec);

        if(party != null) {
            var itemName = spec.getItemName();
            var item = itemControl.getItemByName(itemName);

            if(item != null) {
                var partyTypeName = getPartyTypeName(party);

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
