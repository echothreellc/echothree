// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

import com.echothree.control.user.item.common.edit.ItemDescriptionTypeUseTypeEdit;
import com.echothree.control.user.item.common.edit.ItemEditFactory;
import com.echothree.control.user.item.common.form.EditItemDescriptionTypeUseTypeForm;
import com.echothree.control.user.item.common.result.EditItemDescriptionTypeUseTypeResult;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.control.user.item.common.spec.ItemDescriptionTypeUseTypeSpec;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.item.server.entity.ItemDescriptionTypeUseType;
import com.echothree.model.data.item.server.entity.ItemDescriptionTypeUseTypeDescription;
import com.echothree.model.data.item.server.entity.ItemDescriptionTypeUseTypeDetail;
import com.echothree.model.data.item.server.value.ItemDescriptionTypeUseTypeDescriptionValue;
import com.echothree.model.data.item.server.value.ItemDescriptionTypeUseTypeDetailValue;
import com.echothree.model.data.party.common.pk.PartyPK;
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

public class EditItemDescriptionTypeUseTypeCommand
        extends BaseAbstractEditCommand<ItemDescriptionTypeUseTypeSpec, ItemDescriptionTypeUseTypeEdit, EditItemDescriptionTypeUseTypeResult, ItemDescriptionTypeUseType, ItemDescriptionTypeUseType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ItemDescriptionTypeUseType.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemDescriptionTypeUseTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemDescriptionTypeUseTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditItemDescriptionTypeUseTypeCommand */
    public EditItemDescriptionTypeUseTypeCommand(UserVisitPK userVisitPK, EditItemDescriptionTypeUseTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditItemDescriptionTypeUseTypeResult getResult() {
        return ItemResultFactory.getEditItemDescriptionTypeUseTypeResult();
    }
    
    @Override
    public ItemDescriptionTypeUseTypeEdit getEdit() {
        return ItemEditFactory.getItemDescriptionTypeUseTypeEdit();
    }
    
    @Override
    public ItemDescriptionTypeUseType getEntity(EditItemDescriptionTypeUseTypeResult result) {
        ItemControl itemControl = (ItemControl)Session.getModelController(ItemControl.class);
        ItemDescriptionTypeUseType itemDescriptionTypeUseType = null;
        String itemDescriptionTypeUseTypeName = spec.getItemDescriptionTypeUseTypeName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            itemDescriptionTypeUseType = itemControl.getItemDescriptionTypeUseTypeByName(itemDescriptionTypeUseTypeName);
        } else { // EditMode.UPDATE
            itemDescriptionTypeUseType = itemControl.getItemDescriptionTypeUseTypeByNameForUpdate(itemDescriptionTypeUseTypeName);
        }

        if(itemDescriptionTypeUseType != null) {
            result.setItemDescriptionTypeUseType(itemControl.getItemDescriptionTypeUseTypeTransfer(getUserVisit(), itemDescriptionTypeUseType));
        } else {
            addExecutionError(ExecutionErrors.UnknownItemDescriptionTypeUseTypeName.name(), itemDescriptionTypeUseTypeName);
        }

        return itemDescriptionTypeUseType;
    }
    
    @Override
    public ItemDescriptionTypeUseType getLockEntity(ItemDescriptionTypeUseType itemDescriptionTypeUseType) {
        return itemDescriptionTypeUseType;
    }
    
    @Override
    public void fillInResult(EditItemDescriptionTypeUseTypeResult result, ItemDescriptionTypeUseType itemDescriptionTypeUseType) {
        ItemControl itemControl = (ItemControl)Session.getModelController(ItemControl.class);
        
        result.setItemDescriptionTypeUseType(itemControl.getItemDescriptionTypeUseTypeTransfer(getUserVisit(), itemDescriptionTypeUseType));
    }
    
    @Override
    public void doLock(ItemDescriptionTypeUseTypeEdit edit, ItemDescriptionTypeUseType itemDescriptionTypeUseType) {
        ItemControl itemControl = (ItemControl)Session.getModelController(ItemControl.class);
        ItemDescriptionTypeUseTypeDescription itemDescriptionTypeUseTypeDescription = itemControl.getItemDescriptionTypeUseTypeDescription(itemDescriptionTypeUseType, getPreferredLanguage());
        ItemDescriptionTypeUseTypeDetail itemDescriptionTypeUseTypeDetail = itemDescriptionTypeUseType.getLastDetail();

        edit.setItemDescriptionTypeUseTypeName(itemDescriptionTypeUseTypeDetail.getItemDescriptionTypeUseTypeName());
        edit.setIsDefault(itemDescriptionTypeUseTypeDetail.getIsDefault().toString());
        edit.setSortOrder(itemDescriptionTypeUseTypeDetail.getSortOrder().toString());

        if(itemDescriptionTypeUseTypeDescription != null) {
            edit.setDescription(itemDescriptionTypeUseTypeDescription.getDescription());
        }
    }
        
    @Override
    public void canUpdate(ItemDescriptionTypeUseType itemDescriptionTypeUseType) {
        ItemControl itemControl = (ItemControl)Session.getModelController(ItemControl.class);
        String itemDescriptionTypeUseTypeName = edit.getItemDescriptionTypeUseTypeName();
        ItemDescriptionTypeUseType duplicateItemDescriptionTypeUseType = itemControl.getItemDescriptionTypeUseTypeByName(itemDescriptionTypeUseTypeName);

        if(duplicateItemDescriptionTypeUseType != null && !itemDescriptionTypeUseType.equals(duplicateItemDescriptionTypeUseType)) {
            addExecutionError(ExecutionErrors.DuplicateItemDescriptionTypeUseTypeName.name(), itemDescriptionTypeUseTypeName);
        }
    }
    
    @Override
    public void doUpdate(ItemDescriptionTypeUseType itemDescriptionTypeUseType) {
        ItemControl itemControl = (ItemControl)Session.getModelController(ItemControl.class);
        PartyPK partyPK = getPartyPK();
        ItemDescriptionTypeUseTypeDetailValue itemDescriptionTypeUseTypeDetailValue = itemControl.getItemDescriptionTypeUseTypeDetailValueForUpdate(itemDescriptionTypeUseType);
        ItemDescriptionTypeUseTypeDescription itemDescriptionTypeUseTypeDescription = itemControl.getItemDescriptionTypeUseTypeDescriptionForUpdate(itemDescriptionTypeUseType, getPreferredLanguage());
        String description = edit.getDescription();

        itemDescriptionTypeUseTypeDetailValue.setItemDescriptionTypeUseTypeName(edit.getItemDescriptionTypeUseTypeName());
        itemDescriptionTypeUseTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        itemDescriptionTypeUseTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        itemControl.updateItemDescriptionTypeUseTypeFromValue(itemDescriptionTypeUseTypeDetailValue, partyPK);

        if(itemDescriptionTypeUseTypeDescription == null && description != null) {
            itemControl.createItemDescriptionTypeUseTypeDescription(itemDescriptionTypeUseType, getPreferredLanguage(), description, partyPK);
        } else if(itemDescriptionTypeUseTypeDescription != null && description == null) {
            itemControl.deleteItemDescriptionTypeUseTypeDescription(itemDescriptionTypeUseTypeDescription, partyPK);
        } else if(itemDescriptionTypeUseTypeDescription != null && description != null) {
            ItemDescriptionTypeUseTypeDescriptionValue itemDescriptionTypeUseTypeDescriptionValue = itemControl.getItemDescriptionTypeUseTypeDescriptionValue(itemDescriptionTypeUseTypeDescription);

            itemDescriptionTypeUseTypeDescriptionValue.setDescription(description);
            itemControl.updateItemDescriptionTypeUseTypeDescriptionFromValue(itemDescriptionTypeUseTypeDescriptionValue, partyPK);
        }
    }
    
}
