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
import com.echothree.control.user.item.common.edit.ItemWeightTypeEdit;
import com.echothree.control.user.item.common.result.EditItemWeightTypeResult;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.control.user.item.common.spec.ItemWeightTypeUniversalSpec;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.item.server.logic.ItemWeightTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.item.server.entity.ItemWeightType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditItemWeightTypeCommand
        extends BaseAbstractEditCommand<ItemWeightTypeUniversalSpec, ItemWeightTypeEdit, EditItemWeightTypeResult, ItemWeightType, ItemWeightType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ItemWeightType.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemWeightTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemWeightTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditItemWeightTypeCommand */
    public EditItemWeightTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditItemWeightTypeResult getResult() {
        return ItemResultFactory.getEditItemWeightTypeResult();
    }
    
    @Override
    public ItemWeightTypeEdit getEdit() {
        return ItemEditFactory.getItemWeightTypeEdit();
    }
    
    @Override
    public ItemWeightType getEntity(EditItemWeightTypeResult result) {
        return ItemWeightTypeLogic.getInstance().getItemWeightTypeByUniversalSpec(this,
                spec, false, editModeToEntityPermission(editMode));
    }
    
    @Override
    public ItemWeightType getLockEntity(ItemWeightType itemWeightType) {
        return itemWeightType;
    }
    
    @Override
    public void fillInResult(EditItemWeightTypeResult result, ItemWeightType itemWeightType) {
        final var itemControl = Session.getModelController(ItemControl.class);
        
        result.setItemWeightType(itemControl.getItemWeightTypeTransfer(getUserVisit(), itemWeightType));
    }
    
    @Override
    public void doLock(ItemWeightTypeEdit edit, ItemWeightType itemWeightType) {
        final var itemControl = Session.getModelController(ItemControl.class);
        final var itemWeightTypeDescription = itemControl.getItemWeightTypeDescription(itemWeightType, getPreferredLanguage());
        final var itemWeightTypeDetail = itemWeightType.getLastDetail();
        
        edit.setItemWeightTypeName(itemWeightTypeDetail.getItemWeightTypeName());
        edit.setIsDefault(itemWeightTypeDetail.getIsDefault().toString());
        edit.setSortOrder(itemWeightTypeDetail.getSortOrder().toString());

        if(itemWeightTypeDescription != null) {
            edit.setDescription(itemWeightTypeDescription.getDescription());
        }
    }
        
    @Override
    public void canUpdate(ItemWeightType itemWeightType) {
        final var itemControl = Session.getModelController(ItemControl.class);
        final var itemWeightTypeName = edit.getItemWeightTypeName();
        final var duplicateItemWeightType = itemControl.getItemWeightTypeByName(itemWeightTypeName);

        if(duplicateItemWeightType != null && !itemWeightType.equals(duplicateItemWeightType)) {
            addExecutionError(ExecutionErrors.DuplicateItemWeightTypeName.name(), itemWeightTypeName);
        }
    }
    
    @Override
    public void doUpdate(ItemWeightType itemWeightType) {
        final var itemControl = Session.getModelController(ItemControl.class);
        final var partyPK = getPartyPK();
        final var itemWeightTypeDetailValue = itemControl.getItemWeightTypeDetailValueForUpdate(itemWeightType);
        final var itemWeightTypeDescription = itemControl.getItemWeightTypeDescriptionForUpdate(itemWeightType, getPreferredLanguage());
        final var description = edit.getDescription();

        itemWeightTypeDetailValue.setItemWeightTypeName(edit.getItemWeightTypeName());
        itemWeightTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        itemWeightTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        ItemWeightTypeLogic.getInstance().updateItemWeightTypeFromValue(session, itemWeightTypeDetailValue, partyPK);

        if(itemWeightTypeDescription == null && description != null) {
            itemControl.createItemWeightTypeDescription(itemWeightType, getPreferredLanguage(), description, partyPK);
        } else if(itemWeightTypeDescription != null && description == null) {
            itemControl.deleteItemWeightTypeDescription(itemWeightTypeDescription, partyPK);
        } else if(itemWeightTypeDescription != null && description != null) {
            var itemWeightTypeDescriptionValue = itemControl.getItemWeightTypeDescriptionValue(itemWeightTypeDescription);

            itemWeightTypeDescriptionValue.setDescription(description);
            itemControl.updateItemWeightTypeDescriptionFromValue(itemWeightTypeDescriptionValue, partyPK);
        }
    }
    
}
