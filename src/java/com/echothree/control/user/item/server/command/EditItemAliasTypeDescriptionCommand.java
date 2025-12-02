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

import com.echothree.control.user.item.common.edit.ItemAliasTypeDescriptionEdit;
import com.echothree.control.user.item.common.edit.ItemEditFactory;
import com.echothree.control.user.item.common.form.EditItemAliasTypeDescriptionForm;
import com.echothree.control.user.item.common.result.EditItemAliasTypeDescriptionResult;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.control.user.item.common.spec.ItemAliasTypeDescriptionSpec;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.item.server.entity.ItemAliasType;
import com.echothree.model.data.item.server.entity.ItemAliasTypeDescription;
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
public class EditItemAliasTypeDescriptionCommand
        extends BaseAbstractEditCommand<ItemAliasTypeDescriptionSpec, ItemAliasTypeDescriptionEdit, EditItemAliasTypeDescriptionResult, ItemAliasTypeDescription, ItemAliasType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ItemAliasType.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemAliasTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditItemAliasTypeDescriptionCommand */
    public EditItemAliasTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditItemAliasTypeDescriptionResult getResult() {
        return ItemResultFactory.getEditItemAliasTypeDescriptionResult();
    }

    @Override
    public ItemAliasTypeDescriptionEdit getEdit() {
        return ItemEditFactory.getItemAliasTypeDescriptionEdit();
    }

    @Override
    public ItemAliasTypeDescription getEntity(EditItemAliasTypeDescriptionResult result) {
        var itemControl = Session.getModelController(ItemControl.class);
        ItemAliasTypeDescription itemAliasTypeDescription = null;
        var itemAliasTypeName = spec.getItemAliasTypeName();
        var itemAliasType = itemControl.getItemAliasTypeByName(itemAliasTypeName);

        if(itemAliasType != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    itemAliasTypeDescription = itemControl.getItemAliasTypeDescription(itemAliasType, language);
                } else { // EditMode.UPDATE
                    itemAliasTypeDescription = itemControl.getItemAliasTypeDescriptionForUpdate(itemAliasType, language);
                }

                if(itemAliasTypeDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownItemAliasTypeDescription.name(), itemAliasTypeName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownItemAliasTypeName.name(), itemAliasTypeName);
        }

        return itemAliasTypeDescription;
    }

    @Override
    public ItemAliasType getLockEntity(ItemAliasTypeDescription itemAliasTypeDescription) {
        return itemAliasTypeDescription.getItemAliasType();
    }

    @Override
    public void fillInResult(EditItemAliasTypeDescriptionResult result, ItemAliasTypeDescription itemAliasTypeDescription) {
        var itemControl = Session.getModelController(ItemControl.class);

        result.setItemAliasTypeDescription(itemControl.getItemAliasTypeDescriptionTransfer(getUserVisit(), itemAliasTypeDescription));
    }

    @Override
    public void doLock(ItemAliasTypeDescriptionEdit edit, ItemAliasTypeDescription itemAliasTypeDescription) {
        edit.setDescription(itemAliasTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(ItemAliasTypeDescription itemAliasTypeDescription) {
        var itemControl = Session.getModelController(ItemControl.class);
        var itemAliasTypeDescriptionValue = itemControl.getItemAliasTypeDescriptionValue(itemAliasTypeDescription);
        
        itemAliasTypeDescriptionValue.setDescription(edit.getDescription());
        
        itemControl.updateItemAliasTypeDescriptionFromValue(itemAliasTypeDescriptionValue, getPartyPK());
    }

    
}
