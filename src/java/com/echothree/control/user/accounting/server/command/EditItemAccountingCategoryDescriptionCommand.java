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

package com.echothree.control.user.accounting.server.command;

import com.echothree.control.user.accounting.common.edit.AccountingEditFactory;
import com.echothree.control.user.accounting.common.edit.ItemAccountingCategoryDescriptionEdit;
import com.echothree.control.user.accounting.common.form.EditItemAccountingCategoryDescriptionForm;
import com.echothree.control.user.accounting.common.result.AccountingResultFactory;
import com.echothree.control.user.accounting.common.spec.ItemAccountingCategoryDescriptionSpec;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditItemAccountingCategoryDescriptionCommand
        extends BaseEditCommand<ItemAccountingCategoryDescriptionSpec, ItemAccountingCategoryDescriptionEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ItemAccountingCategory.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemAccountingCategoryName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditItemAccountingCategoryDescriptionCommand */
    public EditItemAccountingCategoryDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var result = AccountingResultFactory.getEditItemAccountingCategoryDescriptionResult();
        var itemAccountingCategoryName = spec.getItemAccountingCategoryName();
        var itemAccountingCategory = accountingControl.getItemAccountingCategoryByName(itemAccountingCategoryName);
        
        if(itemAccountingCategory != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);
            
            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    var itemAccountingCategoryDescription = accountingControl.getItemAccountingCategoryDescription(itemAccountingCategory, language);
                    
                    if(itemAccountingCategoryDescription != null) {
                        if(editMode.equals(EditMode.LOCK)) {
                            result.setItemAccountingCategoryDescription(accountingControl.getItemAccountingCategoryDescriptionTransfer(getUserVisit(), itemAccountingCategoryDescription));

                            if(lockEntity(itemAccountingCategory)) {
                                var edit = AccountingEditFactory.getItemAccountingCategoryDescriptionEdit();

                                result.setEdit(edit);
                                edit.setDescription(itemAccountingCategoryDescription.getDescription());
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockFailed.name());
                            }

                            result.setEntityLock(getEntityLockTransfer(itemAccountingCategory));
                        } else { // EditMode.ABANDON
                            unlockEntity(itemAccountingCategory);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownItemAccountingCategoryDescription.name());
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    var itemAccountingCategoryDescriptionValue = accountingControl.getItemAccountingCategoryDescriptionValueForUpdate(itemAccountingCategory, language);
                    
                    if(itemAccountingCategoryDescriptionValue != null) {
                        if(lockEntityForUpdate(itemAccountingCategory)) {
                            try {
                                var description = edit.getDescription();
                                
                                itemAccountingCategoryDescriptionValue.setDescription(description);
                                
                                accountingControl.updateItemAccountingCategoryDescriptionFromValue(itemAccountingCategoryDescriptionValue, getPartyPK());
                            } finally {
                                unlockEntity(itemAccountingCategory);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownItemAccountingCategoryDescription.name());
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownItemAccountingCategoryName.name(), itemAccountingCategoryName);
        }
        
        return result;
    }
    
}
