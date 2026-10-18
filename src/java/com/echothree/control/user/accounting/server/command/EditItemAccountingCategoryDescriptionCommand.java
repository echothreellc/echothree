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

package com.echothree.control.user.accounting.server.command;

import com.echothree.control.user.accounting.common.edit.AccountingEditFactory;
import com.echothree.control.user.accounting.common.edit.ItemAccountingCategoryDescriptionEdit;
import com.echothree.control.user.accounting.common.result.AccountingResultFactory;
import com.echothree.control.user.accounting.common.result.EditItemAccountingCategoryDescriptionResult;
import com.echothree.control.user.accounting.common.spec.ItemAccountingCategoryDescriptionSpec;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.accounting.server.logic.ItemAccountingCategoryLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.accounting.server.entity.ItemAccountingCategory;
import com.echothree.model.data.accounting.server.entity.ItemAccountingCategoryDescription;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditItemAccountingCategoryDescriptionCommand
        extends BaseAbstractEditCommand<ItemAccountingCategoryDescriptionSpec, ItemAccountingCategoryDescriptionEdit, EditItemAccountingCategoryDescriptionResult, ItemAccountingCategoryDescription, ItemAccountingCategory> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.ItemAccountingCategory.name(), SecurityRoles.Description.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ItemAccountingCategoryName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    AccountingControl accountingControl;

    @Inject
    ItemAccountingCategoryLogic itemAccountingCategoryLogic;

    @Inject
    LanguageLogic languageLogic;

    /** Creates a new instance of EditItemAccountingCategoryDescriptionCommand */
    public EditItemAccountingCategoryDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditItemAccountingCategoryDescriptionResult getResult() {
        return AccountingResultFactory.getEditItemAccountingCategoryDescriptionResult();
    }

    @Override
    public ItemAccountingCategoryDescriptionEdit getEdit() {
        return AccountingEditFactory.getItemAccountingCategoryDescriptionEdit();
    }

    @Override
    public ItemAccountingCategoryDescription getEntity(EditItemAccountingCategoryDescriptionResult result) {
        ItemAccountingCategoryDescription itemAccountingCategoryDescription = null;
        var itemAccountingCategoryName = spec.getItemAccountingCategoryName();
        var itemAccountingCategory = itemAccountingCategoryLogic.getItemAccountingCategoryByName(this, itemAccountingCategoryName);

        if(!hasExecutionErrors()) {
            var languageIsoName = spec.getLanguageIsoName();
            var language = languageLogic.getLanguageByName(this, languageIsoName);

            if(!hasExecutionErrors()) {
                itemAccountingCategoryDescription = accountingControl.getItemAccountingCategoryDescription(itemAccountingCategory, language,
                        editModeToEntityPermission(editMode));

                if(itemAccountingCategoryDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownItemAccountingCategoryDescription.name(), itemAccountingCategoryName, languageIsoName);
                }
            }
        }

        return itemAccountingCategoryDescription;
    }

    @Override
    public ItemAccountingCategory getLockEntity(ItemAccountingCategoryDescription itemAccountingCategoryDescription) {
        return itemAccountingCategoryDescription.getItemAccountingCategory();
    }

    @Override
    public void fillInResult(EditItemAccountingCategoryDescriptionResult result, ItemAccountingCategoryDescription itemAccountingCategoryDescription) {
        result.setItemAccountingCategoryDescription(accountingControl.getItemAccountingCategoryDescriptionTransfer(getUserVisit(), itemAccountingCategoryDescription));
    }

    @Override
    public void doLock(ItemAccountingCategoryDescriptionEdit edit, ItemAccountingCategoryDescription itemAccountingCategoryDescription) {
        edit.setDescription(itemAccountingCategoryDescription.getDescription());
    }

    @Override
    public void doUpdate(ItemAccountingCategoryDescription itemAccountingCategoryDescription) {
        var itemAccountingCategoryDescriptionValue = accountingControl.getItemAccountingCategoryDescriptionValue(itemAccountingCategoryDescription);

        itemAccountingCategoryDescriptionValue.setDescription(edit.getDescription());

        accountingControl.updateItemAccountingCategoryDescriptionFromValue(itemAccountingCategoryDescriptionValue, getPartyPK());
    }

}
