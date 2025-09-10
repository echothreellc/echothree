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
import com.echothree.control.user.item.common.edit.ItemVolumeTypeEdit;
import com.echothree.control.user.item.common.result.EditItemVolumeTypeResult;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.control.user.item.common.spec.ItemVolumeTypeUniversalSpec;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.item.server.logic.ItemVolumeTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.item.server.entity.ItemVolumeType;
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

public class EditItemVolumeTypeCommand
        extends BaseAbstractEditCommand<ItemVolumeTypeUniversalSpec, ItemVolumeTypeEdit, EditItemVolumeTypeResult, ItemVolumeType, ItemVolumeType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ItemVolumeType.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemVolumeTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemVolumeTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditItemVolumeTypeCommand */
    public EditItemVolumeTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditItemVolumeTypeResult getResult() {
        return ItemResultFactory.getEditItemVolumeTypeResult();
    }
    
    @Override
    public ItemVolumeTypeEdit getEdit() {
        return ItemEditFactory.getItemVolumeTypeEdit();
    }
    
    @Override
    public ItemVolumeType getEntity(EditItemVolumeTypeResult result) {
        return ItemVolumeTypeLogic.getInstance().getItemVolumeTypeByUniversalSpec(this,
                spec, false, editModeToEntityPermission(editMode));
    }
    
    @Override
    public ItemVolumeType getLockEntity(ItemVolumeType itemVolumeType) {
        return itemVolumeType;
    }
    
    @Override
    public void fillInResult(EditItemVolumeTypeResult result, ItemVolumeType itemVolumeType) {
        final var itemControl = Session.getModelController(ItemControl.class);
        
        result.setItemVolumeType(itemControl.getItemVolumeTypeTransfer(getUserVisit(), itemVolumeType));
    }
    
    @Override
    public void doLock(ItemVolumeTypeEdit edit, ItemVolumeType itemVolumeType) {
        final var itemControl = Session.getModelController(ItemControl.class);
        final var itemVolumeTypeDescription = itemControl.getItemVolumeTypeDescription(itemVolumeType, getPreferredLanguage());
        final var itemVolumeTypeDetail = itemVolumeType.getLastDetail();
        
        edit.setItemVolumeTypeName(itemVolumeTypeDetail.getItemVolumeTypeName());
        edit.setIsDefault(itemVolumeTypeDetail.getIsDefault().toString());
        edit.setSortOrder(itemVolumeTypeDetail.getSortOrder().toString());

        if(itemVolumeTypeDescription != null) {
            edit.setDescription(itemVolumeTypeDescription.getDescription());
        }
    }
        
    @Override
    public void canUpdate(ItemVolumeType itemVolumeType) {
        final var itemControl = Session.getModelController(ItemControl.class);
        final var itemVolumeTypeName = edit.getItemVolumeTypeName();
        final var duplicateItemVolumeType = itemControl.getItemVolumeTypeByName(itemVolumeTypeName);

        if(duplicateItemVolumeType != null && !itemVolumeType.equals(duplicateItemVolumeType)) {
            addExecutionError(ExecutionErrors.DuplicateItemVolumeTypeName.name(), itemVolumeTypeName);
        }
    }
    
    @Override
    public void doUpdate(ItemVolumeType itemVolumeType) {
        final var itemControl = Session.getModelController(ItemControl.class);
        final var partyPK = getPartyPK();
        final var itemVolumeTypeDetailValue = itemControl.getItemVolumeTypeDetailValueForUpdate(itemVolumeType);
        final var itemVolumeTypeDescription = itemControl.getItemVolumeTypeDescriptionForUpdate(itemVolumeType, getPreferredLanguage());
        final var description = edit.getDescription();

        itemVolumeTypeDetailValue.setItemVolumeTypeName(edit.getItemVolumeTypeName());
        itemVolumeTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        itemVolumeTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        ItemVolumeTypeLogic.getInstance().updateItemVolumeTypeFromValue(session, itemVolumeTypeDetailValue, partyPK);

        if(itemVolumeTypeDescription == null && description != null) {
            itemControl.createItemVolumeTypeDescription(itemVolumeType, getPreferredLanguage(), description, partyPK);
        } else if(itemVolumeTypeDescription != null && description == null) {
            itemControl.deleteItemVolumeTypeDescription(itemVolumeTypeDescription, partyPK);
        } else if(itemVolumeTypeDescription != null && description != null) {
            var itemVolumeTypeDescriptionValue = itemControl.getItemVolumeTypeDescriptionValue(itemVolumeTypeDescription);

            itemVolumeTypeDescriptionValue.setDescription(description);
            itemControl.updateItemVolumeTypeDescriptionFromValue(itemVolumeTypeDescriptionValue, partyPK);
        }
    }
    
}
