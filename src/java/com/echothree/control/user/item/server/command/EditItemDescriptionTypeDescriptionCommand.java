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

import com.echothree.control.user.item.common.edit.ItemDescriptionTypeDescriptionEdit;
import com.echothree.control.user.item.common.edit.ItemEditFactory;
import com.echothree.control.user.item.common.form.EditItemDescriptionTypeDescriptionForm;
import com.echothree.control.user.item.common.result.EditItemDescriptionTypeDescriptionResult;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.control.user.item.common.spec.ItemDescriptionTypeDescriptionSpec;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.item.server.entity.ItemDescriptionType;
import com.echothree.model.data.item.server.entity.ItemDescriptionTypeDescription;
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
import javax.enterprise.context.Dependent;

@Dependent
public class EditItemDescriptionTypeDescriptionCommand
        extends BaseAbstractEditCommand<ItemDescriptionTypeDescriptionSpec, ItemDescriptionTypeDescriptionEdit, EditItemDescriptionTypeDescriptionResult, ItemDescriptionTypeDescription, ItemDescriptionType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ItemDescriptionType.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemDescriptionTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditItemDescriptionTypeDescriptionCommand */
    public EditItemDescriptionTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
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
        var itemControl = Session.getModelController(ItemControl.class);
        ItemDescriptionTypeDescription itemDescriptionTypeDescription = null;
        var itemDescriptionTypeName = spec.getItemDescriptionTypeName();
        var itemDescriptionType = itemControl.getItemDescriptionTypeByName(itemDescriptionTypeName);

        if(itemDescriptionType != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

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
        var itemControl = Session.getModelController(ItemControl.class);

        result.setItemDescriptionTypeDescription(itemControl.getItemDescriptionTypeDescriptionTransfer(getUserVisit(), itemDescriptionTypeDescription));
    }

    @Override
    public void doLock(ItemDescriptionTypeDescriptionEdit edit, ItemDescriptionTypeDescription itemDescriptionTypeDescription) {
        edit.setDescription(itemDescriptionTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(ItemDescriptionTypeDescription itemDescriptionTypeDescription) {
        var itemControl = Session.getModelController(ItemControl.class);
        var itemDescriptionTypeDescriptionValue = itemControl.getItemDescriptionTypeDescriptionValue(itemDescriptionTypeDescription);
        
        itemDescriptionTypeDescriptionValue.setDescription(edit.getDescription());
        
        itemControl.updateItemDescriptionTypeDescriptionFromValue(itemDescriptionTypeDescriptionValue, getPartyPK());
    }

    
}
