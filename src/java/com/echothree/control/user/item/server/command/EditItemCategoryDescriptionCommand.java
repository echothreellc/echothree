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

import com.echothree.control.user.item.remote.edit.ItemCategoryDescriptionEdit;
import com.echothree.control.user.item.remote.edit.ItemEditFactory;
import com.echothree.control.user.item.remote.form.EditItemCategoryDescriptionForm;
import com.echothree.control.user.item.remote.result.EditItemCategoryDescriptionResult;
import com.echothree.control.user.item.remote.result.ItemResultFactory;
import com.echothree.control.user.item.remote.spec.ItemCategoryDescriptionSpec;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.item.server.entity.ItemCategory;
import com.echothree.model.data.item.server.entity.ItemCategoryDescription;
import com.echothree.model.data.item.server.value.ItemCategoryDescriptionValue;
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

public class EditItemCategoryDescriptionCommand
        extends BaseAbstractEditCommand<ItemCategoryDescriptionSpec, ItemCategoryDescriptionEdit, EditItemCategoryDescriptionResult, ItemCategoryDescription, ItemCategory> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ItemCategory.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemCategoryName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditItemCategoryDescriptionCommand */
    public EditItemCategoryDescriptionCommand(UserVisitPK userVisitPK, EditItemCategoryDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditItemCategoryDescriptionResult getResult() {
        return ItemResultFactory.getEditItemCategoryDescriptionResult();
    }

    @Override
    public ItemCategoryDescriptionEdit getEdit() {
        return ItemEditFactory.getItemCategoryDescriptionEdit();
    }

    @Override
    public ItemCategoryDescription getEntity(EditItemCategoryDescriptionResult result) {
        ItemControl itemControl = (ItemControl)Session.getModelController(ItemControl.class);
        ItemCategoryDescription itemCategoryDescription = null;
        String itemCategoryName = spec.getItemCategoryName();
        ItemCategory itemCategory = itemControl.getItemCategoryByName(itemCategoryName);

        if(itemCategory != null) {
            PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String languageIsoName = spec.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    itemCategoryDescription = itemControl.getItemCategoryDescription(itemCategory, language);
                } else { // EditMode.UPDATE
                    itemCategoryDescription = itemControl.getItemCategoryDescriptionForUpdate(itemCategory, language);
                }

                if(itemCategoryDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownItemCategoryDescription.name(), itemCategoryName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownItemCategoryName.name(), itemCategoryName);
        }

        return itemCategoryDescription;
    }

    @Override
    public ItemCategory getLockEntity(ItemCategoryDescription itemCategoryDescription) {
        return itemCategoryDescription.getItemCategory();
    }

    @Override
    public void fillInResult(EditItemCategoryDescriptionResult result, ItemCategoryDescription itemCategoryDescription) {
        ItemControl itemControl = (ItemControl)Session.getModelController(ItemControl.class);

        result.setItemCategoryDescription(itemControl.getItemCategoryDescriptionTransfer(getUserVisit(), itemCategoryDescription));
    }

    @Override
    public void doLock(ItemCategoryDescriptionEdit edit, ItemCategoryDescription itemCategoryDescription) {
        edit.setDescription(itemCategoryDescription.getDescription());
    }

    @Override
    public void doUpdate(ItemCategoryDescription itemCategoryDescription) {
        ItemControl itemControl = (ItemControl)Session.getModelController(ItemControl.class);
        ItemCategoryDescriptionValue itemCategoryDescriptionValue = itemControl.getItemCategoryDescriptionValue(itemCategoryDescription);
        
        itemCategoryDescriptionValue.setDescription(edit.getDescription());
        
        itemControl.updateItemCategoryDescriptionFromValue(itemCategoryDescriptionValue, getPartyPK());
    }

    
}
