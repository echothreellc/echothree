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

package com.echothree.control.user.item.server.command;

import com.echothree.control.user.item.common.edit.ItemEditFactory;
import com.echothree.control.user.item.common.edit.ItemUnitOfMeasureTypeEdit;
import com.echothree.control.user.item.common.form.EditItemUnitOfMeasureTypeForm;
import com.echothree.control.user.item.common.result.EditItemUnitOfMeasureTypeResult;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.control.user.item.common.spec.ItemUnitOfMeasureTypeSpec;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemUnitOfMeasureType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditItemUnitOfMeasureTypeCommand
        extends BaseAbstractEditCommand<ItemUnitOfMeasureTypeSpec, ItemUnitOfMeasureTypeEdit, EditItemUnitOfMeasureTypeResult, ItemUnitOfMeasureType, Item> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ItemUnitOfMeasureType.name(), SecurityRoles.Edit.name())
                )))
        )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditItemUnitOfMeasureTypeCommand */
    public EditItemUnitOfMeasureTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditItemUnitOfMeasureTypeResult getResult() {
        return ItemResultFactory.getEditItemUnitOfMeasureTypeResult();
    }

    @Override
    public ItemUnitOfMeasureTypeEdit getEdit() {
        return ItemEditFactory.getItemUnitOfMeasureTypeEdit();
    }

    @Override
    public ItemUnitOfMeasureType getEntity(EditItemUnitOfMeasureTypeResult result) {
        var itemControl = Session.getModelController(ItemControl.class);
        ItemUnitOfMeasureType itemUnitOfMeasureType = null;
        var itemName = spec.getItemName();
        var item = itemControl.getItemByName(itemName);

        if(item != null) {
            var uomControl = Session.getModelController(UomControl.class);
            var unitOfMeasureTypeName = spec.getUnitOfMeasureTypeName();
            var unitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(item.getLastDetail().getUnitOfMeasureKind(), unitOfMeasureTypeName);

            if(unitOfMeasureType != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    itemUnitOfMeasureType = itemControl.getItemUnitOfMeasureType(item, unitOfMeasureType);
                } else { // EditMode.UPDATE
                    itemUnitOfMeasureType = itemControl.getItemUnitOfMeasureTypeForUpdate(item, unitOfMeasureType);
                }

                if(itemUnitOfMeasureType == null) {
                    addExecutionError(ExecutionErrors.UnknownItemUnitOfMeasureType.name(), itemName, unitOfMeasureTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownUnitOfMeasureTypeName.name(), unitOfMeasureTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownItemName.name(), itemName);
        }

        return itemUnitOfMeasureType;
    }

    @Override
    public Item getLockEntity(ItemUnitOfMeasureType itemUnitOfMeasureType) {
        return itemUnitOfMeasureType.getItem();
    }

    @Override
    public void fillInResult(EditItemUnitOfMeasureTypeResult result, ItemUnitOfMeasureType itemUnitOfMeasureType) {
        var itemControl = Session.getModelController(ItemControl.class);

        result.setItemUnitOfMeasureType(itemControl.getItemUnitOfMeasureTypeTransfer(getUserVisit(), itemUnitOfMeasureType));
    }

    @Override
    public void doLock(ItemUnitOfMeasureTypeEdit edit, ItemUnitOfMeasureType itemUnitOfMeasureType) {
        edit.setIsDefault(itemUnitOfMeasureType.getIsDefault().toString());
        edit.setSortOrder(itemUnitOfMeasureType.getSortOrder().toString());
    }

    @Override
    public void doUpdate(ItemUnitOfMeasureType itemUnitOfMeasureType) {
        var itemControl = Session.getModelController(ItemControl.class);
        var itemUnitOfMeasureTypeValue = itemControl.getItemUnitOfMeasureTypeValue(itemUnitOfMeasureType);

        itemUnitOfMeasureTypeValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        itemUnitOfMeasureTypeValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        itemControl.updateItemUnitOfMeasureTypeFromValue(itemUnitOfMeasureTypeValue, getPartyPK());
    }

}
