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

import com.echothree.control.user.item.common.edit.ItemDescriptionTypeUseTypeDescriptionEdit;
import com.echothree.control.user.item.common.edit.ItemEditFactory;
import com.echothree.control.user.item.common.form.EditItemDescriptionTypeUseTypeDescriptionForm;
import com.echothree.control.user.item.common.result.EditItemDescriptionTypeUseTypeDescriptionResult;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.control.user.item.common.spec.ItemDescriptionTypeUseTypeDescriptionSpec;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.item.server.entity.ItemDescriptionTypeUseType;
import com.echothree.model.data.item.server.entity.ItemDescriptionTypeUseTypeDescription;
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
public class EditItemDescriptionTypeUseTypeDescriptionCommand
        extends BaseAbstractEditCommand<ItemDescriptionTypeUseTypeDescriptionSpec, ItemDescriptionTypeUseTypeDescriptionEdit, EditItemDescriptionTypeUseTypeDescriptionResult, ItemDescriptionTypeUseTypeDescription, ItemDescriptionTypeUseType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ItemDescriptionTypeUseType.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemDescriptionTypeUseTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditItemDescriptionTypeUseTypeDescriptionCommand */
    public EditItemDescriptionTypeUseTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditItemDescriptionTypeUseTypeDescriptionResult getResult() {
        return ItemResultFactory.getEditItemDescriptionTypeUseTypeDescriptionResult();
    }

    @Override
    public ItemDescriptionTypeUseTypeDescriptionEdit getEdit() {
        return ItemEditFactory.getItemDescriptionTypeUseTypeDescriptionEdit();
    }

    @Override
    public ItemDescriptionTypeUseTypeDescription getEntity(EditItemDescriptionTypeUseTypeDescriptionResult result) {
        var itemControl = Session.getModelController(ItemControl.class);
        ItemDescriptionTypeUseTypeDescription itemDescriptionTypeUseTypeDescription = null;
        var itemDescriptionTypeUseTypeName = spec.getItemDescriptionTypeUseTypeName();
        var itemDescriptionTypeUseType = itemControl.getItemDescriptionTypeUseTypeByName(itemDescriptionTypeUseTypeName);

        if(itemDescriptionTypeUseType != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    itemDescriptionTypeUseTypeDescription = itemControl.getItemDescriptionTypeUseTypeDescription(itemDescriptionTypeUseType, language);
                } else { // EditMode.UPDATE
                    itemDescriptionTypeUseTypeDescription = itemControl.getItemDescriptionTypeUseTypeDescriptionForUpdate(itemDescriptionTypeUseType, language);
                }

                if(itemDescriptionTypeUseTypeDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownItemDescriptionTypeUseTypeDescription.name(), itemDescriptionTypeUseTypeName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownItemDescriptionTypeUseTypeName.name(), itemDescriptionTypeUseTypeName);
        }

        return itemDescriptionTypeUseTypeDescription;
    }

    @Override
    public ItemDescriptionTypeUseType getLockEntity(ItemDescriptionTypeUseTypeDescription itemDescriptionTypeUseTypeDescription) {
        return itemDescriptionTypeUseTypeDescription.getItemDescriptionTypeUseType();
    }

    @Override
    public void fillInResult(EditItemDescriptionTypeUseTypeDescriptionResult result, ItemDescriptionTypeUseTypeDescription itemDescriptionTypeUseTypeDescription) {
        var itemControl = Session.getModelController(ItemControl.class);

        result.setItemDescriptionTypeUseTypeDescription(itemControl.getItemDescriptionTypeUseTypeDescriptionTransfer(getUserVisit(), itemDescriptionTypeUseTypeDescription));
    }

    @Override
    public void doLock(ItemDescriptionTypeUseTypeDescriptionEdit edit, ItemDescriptionTypeUseTypeDescription itemDescriptionTypeUseTypeDescription) {
        edit.setDescription(itemDescriptionTypeUseTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(ItemDescriptionTypeUseTypeDescription itemDescriptionTypeUseTypeDescription) {
        var itemControl = Session.getModelController(ItemControl.class);
        var itemDescriptionTypeUseTypeDescriptionValue = itemControl.getItemDescriptionTypeUseTypeDescriptionValue(itemDescriptionTypeUseTypeDescription);
        
        itemDescriptionTypeUseTypeDescriptionValue.setDescription(edit.getDescription());
        
        itemControl.updateItemDescriptionTypeUseTypeDescriptionFromValue(itemDescriptionTypeUseTypeDescriptionValue, getPartyPK());
    }

    
}
