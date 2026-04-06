// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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

package com.echothree.control.user.vendor.server.command;

import com.echothree.control.user.vendor.common.edit.ItemPurchasingCategoryDescriptionEdit;
import com.echothree.control.user.vendor.common.edit.VendorEditFactory;
import com.echothree.control.user.vendor.common.result.EditItemPurchasingCategoryDescriptionResult;
import com.echothree.control.user.vendor.common.result.VendorResultFactory;
import com.echothree.control.user.vendor.common.spec.ItemPurchasingCategoryDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.data.vendor.server.entity.ItemPurchasingCategory;
import com.echothree.model.data.vendor.server.entity.ItemPurchasingCategoryDescription;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.inject.Inject;
import javax.enterprise.context.Dependent;

@Dependent
public class EditItemPurchasingCategoryDescriptionCommand
        extends BaseAbstractEditCommand<ItemPurchasingCategoryDescriptionSpec, ItemPurchasingCategoryDescriptionEdit, EditItemPurchasingCategoryDescriptionResult, ItemPurchasingCategoryDescription, ItemPurchasingCategory> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                    new SecurityRoleDefinition(SecurityRoleGroups.ItemPurchasingCategory.name(), SecurityRoles.Description.name())
                    ))
                ));
        
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ItemPurchasingCategoryName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                );
    }
    
    /** Creates a new instance of EditItemPurchasingCategoryDescriptionCommand */
    public EditItemPurchasingCategoryDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Inject
    PartyControl partyControl;

    @Inject
    VendorControl vendorControl;

    @Override
    public EditItemPurchasingCategoryDescriptionResult getResult() {
        return VendorResultFactory.getEditItemPurchasingCategoryDescriptionResult();
    }

    @Override
    public ItemPurchasingCategoryDescriptionEdit getEdit() {
        return VendorEditFactory.getItemPurchasingCategoryDescriptionEdit();
    }

    @Override
    public ItemPurchasingCategoryDescription getEntity(EditItemPurchasingCategoryDescriptionResult result) {
        ItemPurchasingCategoryDescription itemPurchasingCategoryDescription = null;
        var itemPurchasingCategoryName = spec.getItemPurchasingCategoryName();
        var itemPurchasingCategory = vendorControl.getItemPurchasingCategoryByName(itemPurchasingCategoryName);

        if(itemPurchasingCategory != null) {
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                itemPurchasingCategoryDescription = vendorControl.getItemPurchasingCategoryDescription(itemPurchasingCategory, language,
                        editModeToEntityPermission(editMode));

                if(itemPurchasingCategoryDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownItemPurchasingCategoryDescription.name(), itemPurchasingCategoryName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownItemPurchasingCategoryName.name(), itemPurchasingCategoryName);
        }

        return itemPurchasingCategoryDescription;
    }

    @Override
    public ItemPurchasingCategory getLockEntity(ItemPurchasingCategoryDescription itemPurchasingCategoryDescription) {
        return itemPurchasingCategoryDescription.getItemPurchasingCategory();
    }

    @Override
    public void fillInResult(EditItemPurchasingCategoryDescriptionResult result, ItemPurchasingCategoryDescription itemPurchasingCategoryDescription) {
        result.setItemPurchasingCategoryDescription(vendorControl.getItemPurchasingCategoryDescriptionTransfer(getUserVisit(), itemPurchasingCategoryDescription));
    }

    @Override
    public void doLock(ItemPurchasingCategoryDescriptionEdit edit, ItemPurchasingCategoryDescription itemPurchasingCategoryDescription) {
        edit.setDescription(itemPurchasingCategoryDescription.getDescription());
    }

    @Override
    public void doUpdate(ItemPurchasingCategoryDescription itemPurchasingCategoryDescription) {
        var itemPurchasingCategoryDescriptionValue = vendorControl.getItemPurchasingCategoryDescriptionValue(itemPurchasingCategoryDescription);

        itemPurchasingCategoryDescriptionValue.setDescription(edit.getDescription());

        vendorControl.updateItemPurchasingCategoryDescriptionFromValue(itemPurchasingCategoryDescriptionValue, getPartyPK());
    }
    
}
