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

import com.echothree.control.user.item.common.edit.ItemVolumeTypeDescriptionEdit;
import com.echothree.control.user.item.common.edit.ItemEditFactory;
import com.echothree.control.user.item.common.form.EditItemVolumeTypeDescriptionForm;
import com.echothree.control.user.item.common.result.EditItemVolumeTypeDescriptionResult;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.control.user.item.common.spec.ItemVolumeTypeDescriptionSpec;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.item.server.entity.ItemVolumeType;
import com.echothree.model.data.item.server.entity.ItemVolumeTypeDescription;
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
public class EditItemVolumeTypeDescriptionCommand
        extends BaseAbstractEditCommand<ItemVolumeTypeDescriptionSpec, ItemVolumeTypeDescriptionEdit, EditItemVolumeTypeDescriptionResult, ItemVolumeTypeDescription, ItemVolumeType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ItemVolumeType.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemVolumeTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditItemVolumeTypeDescriptionCommand */
    public EditItemVolumeTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditItemVolumeTypeDescriptionResult getResult() {
        return ItemResultFactory.getEditItemVolumeTypeDescriptionResult();
    }

    @Override
    public ItemVolumeTypeDescriptionEdit getEdit() {
        return ItemEditFactory.getItemVolumeTypeDescriptionEdit();
    }

    @Override
    public ItemVolumeTypeDescription getEntity(EditItemVolumeTypeDescriptionResult result) {
        var itemControl = Session.getModelController(ItemControl.class);
        ItemVolumeTypeDescription itemVolumeTypeDescription = null;
        var itemVolumeTypeName = spec.getItemVolumeTypeName();
        var itemVolumeType = itemControl.getItemVolumeTypeByName(itemVolumeTypeName);

        if(itemVolumeType != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    itemVolumeTypeDescription = itemControl.getItemVolumeTypeDescription(itemVolumeType, language);
                } else { // EditMode.UPDATE
                    itemVolumeTypeDescription = itemControl.getItemVolumeTypeDescriptionForUpdate(itemVolumeType, language);
                }

                if(itemVolumeTypeDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownItemVolumeTypeDescription.name(), itemVolumeTypeName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownItemVolumeTypeName.name(), itemVolumeTypeName);
        }

        return itemVolumeTypeDescription;
    }

    @Override
    public ItemVolumeType getLockEntity(ItemVolumeTypeDescription itemVolumeTypeDescription) {
        return itemVolumeTypeDescription.getItemVolumeType();
    }

    @Override
    public void fillInResult(EditItemVolumeTypeDescriptionResult result, ItemVolumeTypeDescription itemVolumeTypeDescription) {
        var itemControl = Session.getModelController(ItemControl.class);

        result.setItemVolumeTypeDescription(itemControl.getItemVolumeTypeDescriptionTransfer(getUserVisit(), itemVolumeTypeDescription));
    }

    @Override
    public void doLock(ItemVolumeTypeDescriptionEdit edit, ItemVolumeTypeDescription itemVolumeTypeDescription) {
        edit.setDescription(itemVolumeTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(ItemVolumeTypeDescription itemVolumeTypeDescription) {
        var itemControl = Session.getModelController(ItemControl.class);
        var itemVolumeTypeDescriptionValue = itemControl.getItemVolumeTypeDescriptionValue(itemVolumeTypeDescription);
        
        itemVolumeTypeDescriptionValue.setDescription(edit.getDescription());
        
        itemControl.updateItemVolumeTypeDescriptionFromValue(itemVolumeTypeDescriptionValue, getPartyPK());
    }

    
}
