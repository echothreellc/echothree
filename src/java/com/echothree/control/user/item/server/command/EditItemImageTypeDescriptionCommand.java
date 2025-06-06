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
import com.echothree.control.user.item.common.edit.ItemImageTypeDescriptionEdit;
import com.echothree.control.user.item.common.form.EditItemImageTypeDescriptionForm;
import com.echothree.control.user.item.common.result.EditItemImageTypeDescriptionResult;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.control.user.item.common.spec.ItemImageTypeDescriptionSpec;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.item.server.entity.ItemImageType;
import com.echothree.model.data.item.server.entity.ItemImageTypeDescription;
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

public class EditItemImageTypeDescriptionCommand
        extends BaseAbstractEditCommand<ItemImageTypeDescriptionSpec, ItemImageTypeDescriptionEdit, EditItemImageTypeDescriptionResult, ItemImageTypeDescription, ItemImageType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ItemImageType.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemImageTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditItemImageTypeDescriptionCommand */
    public EditItemImageTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditItemImageTypeDescriptionResult getResult() {
        return ItemResultFactory.getEditItemImageTypeDescriptionResult();
    }

    @Override
    public ItemImageTypeDescriptionEdit getEdit() {
        return ItemEditFactory.getItemImageTypeDescriptionEdit();
    }

    @Override
    public ItemImageTypeDescription getEntity(EditItemImageTypeDescriptionResult result) {
        var itemControl = Session.getModelController(ItemControl.class);
        ItemImageTypeDescription itemImageTypeDescription = null;
        var itemImageTypeName = spec.getItemImageTypeName();
        var itemImageType = itemControl.getItemImageTypeByName(itemImageTypeName);

        if(itemImageType != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    itemImageTypeDescription = itemControl.getItemImageTypeDescription(itemImageType, language);
                } else { // EditMode.UPDATE
                    itemImageTypeDescription = itemControl.getItemImageTypeDescriptionForUpdate(itemImageType, language);
                }

                if(itemImageTypeDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownItemImageTypeDescription.name(), itemImageTypeName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownItemImageTypeName.name(), itemImageTypeName);
        }

        return itemImageTypeDescription;
    }

    @Override
    public ItemImageType getLockEntity(ItemImageTypeDescription itemImageTypeDescription) {
        return itemImageTypeDescription.getItemImageType();
    }

    @Override
    public void fillInResult(EditItemImageTypeDescriptionResult result, ItemImageTypeDescription itemImageTypeDescription) {
        var itemControl = Session.getModelController(ItemControl.class);

        result.setItemImageTypeDescription(itemControl.getItemImageTypeDescriptionTransfer(getUserVisit(), itemImageTypeDescription));
    }

    @Override
    public void doLock(ItemImageTypeDescriptionEdit edit, ItemImageTypeDescription itemImageTypeDescription) {
        edit.setDescription(itemImageTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(ItemImageTypeDescription itemImageTypeDescription) {
        var itemControl = Session.getModelController(ItemControl.class);
        var itemImageTypeDescriptionValue = itemControl.getItemImageTypeDescriptionValue(itemImageTypeDescription);
        
        itemImageTypeDescriptionValue.setDescription(edit.getDescription());
        
        itemControl.updateItemImageTypeDescriptionFromValue(itemImageTypeDescriptionValue, getPartyPK());
    }

    
}
