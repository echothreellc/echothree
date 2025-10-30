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

import com.echothree.control.user.item.common.edit.ItemWeightTypeDescriptionEdit;
import com.echothree.control.user.item.common.edit.ItemEditFactory;
import com.echothree.control.user.item.common.form.EditItemWeightTypeDescriptionForm;
import com.echothree.control.user.item.common.result.EditItemWeightTypeDescriptionResult;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.control.user.item.common.spec.ItemWeightTypeDescriptionSpec;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.item.server.entity.ItemWeightType;
import com.echothree.model.data.item.server.entity.ItemWeightTypeDescription;
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
public class EditItemWeightTypeDescriptionCommand
        extends BaseAbstractEditCommand<ItemWeightTypeDescriptionSpec, ItemWeightTypeDescriptionEdit, EditItemWeightTypeDescriptionResult, ItemWeightTypeDescription, ItemWeightType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ItemWeightType.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemWeightTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditItemWeightTypeDescriptionCommand */
    public EditItemWeightTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditItemWeightTypeDescriptionResult getResult() {
        return ItemResultFactory.getEditItemWeightTypeDescriptionResult();
    }

    @Override
    public ItemWeightTypeDescriptionEdit getEdit() {
        return ItemEditFactory.getItemWeightTypeDescriptionEdit();
    }

    @Override
    public ItemWeightTypeDescription getEntity(EditItemWeightTypeDescriptionResult result) {
        var itemControl = Session.getModelController(ItemControl.class);
        ItemWeightTypeDescription itemWeightTypeDescription = null;
        var itemWeightTypeName = spec.getItemWeightTypeName();
        var itemWeightType = itemControl.getItemWeightTypeByName(itemWeightTypeName);

        if(itemWeightType != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    itemWeightTypeDescription = itemControl.getItemWeightTypeDescription(itemWeightType, language);
                } else { // EditMode.UPDATE
                    itemWeightTypeDescription = itemControl.getItemWeightTypeDescriptionForUpdate(itemWeightType, language);
                }

                if(itemWeightTypeDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownItemWeightTypeDescription.name(), itemWeightTypeName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownItemWeightTypeName.name(), itemWeightTypeName);
        }

        return itemWeightTypeDescription;
    }

    @Override
    public ItemWeightType getLockEntity(ItemWeightTypeDescription itemWeightTypeDescription) {
        return itemWeightTypeDescription.getItemWeightType();
    }

    @Override
    public void fillInResult(EditItemWeightTypeDescriptionResult result, ItemWeightTypeDescription itemWeightTypeDescription) {
        var itemControl = Session.getModelController(ItemControl.class);

        result.setItemWeightTypeDescription(itemControl.getItemWeightTypeDescriptionTransfer(getUserVisit(), itemWeightTypeDescription));
    }

    @Override
    public void doLock(ItemWeightTypeDescriptionEdit edit, ItemWeightTypeDescription itemWeightTypeDescription) {
        edit.setDescription(itemWeightTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(ItemWeightTypeDescription itemWeightTypeDescription) {
        var itemControl = Session.getModelController(ItemControl.class);
        var itemWeightTypeDescriptionValue = itemControl.getItemWeightTypeDescriptionValue(itemWeightTypeDescription);
        
        itemWeightTypeDescriptionValue.setDescription(edit.getDescription());
        
        itemControl.updateItemWeightTypeDescriptionFromValue(itemWeightTypeDescriptionValue, getPartyPK());
    }

    
}
