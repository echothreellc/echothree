// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.control.user.item.remote.edit.ItemDescriptionTypeDescriptionEdit;
import com.echothree.control.user.item.remote.edit.ItemEditFactory;
import com.echothree.control.user.item.remote.form.EditItemDescriptionTypeDescriptionForm;
import com.echothree.control.user.item.remote.result.EditItemDescriptionTypeDescriptionResult;
import com.echothree.control.user.item.remote.result.ItemResultFactory;
import com.echothree.control.user.item.remote.spec.ItemDescriptionTypeDescriptionSpec;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.item.server.entity.ItemDescriptionType;
import com.echothree.model.data.item.server.entity.ItemDescriptionTypeDescription;
import com.echothree.model.data.item.server.value.ItemDescriptionTypeDescriptionValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditItemDescriptionTypeDescriptionCommand
        extends BaseAbstractEditCommand<ItemDescriptionTypeDescriptionSpec, ItemDescriptionTypeDescriptionEdit, EditItemDescriptionTypeDescriptionResult, ItemDescriptionTypeDescription, ItemDescriptionType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ItemDescriptionType.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemDescriptionTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditItemDescriptionTypeDescriptionCommand */
    public EditItemDescriptionTypeDescriptionCommand(UserVisitPK userVisitPK, EditItemDescriptionTypeDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditItemDescriptionTypeDescriptionResult getResult() {
        return ItemResultFactory.getEditItemDescriptionTypeDescriptionResult();
    }

    @Override
    public ItemDescriptionTypeDescriptionEdit getEdit() {
        return ItemEditFactory.getItemDescriptionTypeDescriptionEdit();
    }

    @Override
    public ItemDescriptionTypeDescription getEntity(EditItemDescriptionTypeDescriptionResult result) {
        ItemControl itemControl = (ItemControl)Session.getModelController(ItemControl.class);
        ItemDescriptionTypeDescription itemDescriptionTypeDescription = null;
        String itemDescriptionTypeName = spec.getItemDescriptionTypeName();
        ItemDescriptionType itemDescriptionType = itemControl.getItemDescriptionTypeByName(itemDescriptionTypeName);

        if(itemDescriptionType != null) {
            PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String languageIsoName = spec.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    itemDescriptionTypeDescription = itemControl.getItemDescriptionTypeDescription(itemDescriptionType, language);
                } else { // EditMode.UPDATE
                    itemDescriptionTypeDescription = itemControl.getItemDescriptionTypeDescriptionForUpdate(itemDescriptionType, language);
                }

                if(itemDescriptionTypeDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownItemDescriptionTypeDescription.name(), itemDescriptionTypeName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownItemDescriptionTypeName.name(), itemDescriptionTypeName);
        }

        return itemDescriptionTypeDescription;
    }

    @Override
    public ItemDescriptionType getLockEntity(ItemDescriptionTypeDescription itemDescriptionTypeDescription) {
        return itemDescriptionTypeDescription.getItemDescriptionType();
    }

    @Override
    public void fillInResult(EditItemDescriptionTypeDescriptionResult result, ItemDescriptionTypeDescription itemDescriptionTypeDescription) {
        ItemControl itemControl = (ItemControl)Session.getModelController(ItemControl.class);

        result.setItemDescriptionTypeDescription(itemControl.getItemDescriptionTypeDescriptionTransfer(getUserVisit(), itemDescriptionTypeDescription));
    }

    @Override
    public void doLock(ItemDescriptionTypeDescriptionEdit edit, ItemDescriptionTypeDescription itemDescriptionTypeDescription) {
        edit.setDescription(itemDescriptionTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(ItemDescriptionTypeDescription itemDescriptionTypeDescription) {
        ItemControl itemControl = (ItemControl)Session.getModelController(ItemControl.class);
        ItemDescriptionTypeDescriptionValue itemDescriptionTypeDescriptionValue = itemControl.getItemDescriptionTypeDescriptionValue(itemDescriptionTypeDescription);
        
        itemDescriptionTypeDescriptionValue.setDescription(edit.getDescription());
        
        itemControl.updateItemDescriptionTypeDescriptionFromValue(itemDescriptionTypeDescriptionValue, getPartyPK());
    }

    
}
