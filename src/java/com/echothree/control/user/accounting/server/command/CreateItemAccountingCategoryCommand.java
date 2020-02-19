// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.control.user.accounting.common.form.CreateItemAccountingCategoryForm;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.accounting.server.entity.ItemAccountingCategory;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateItemAccountingCategoryCommand
        extends BaseSimpleCommand<CreateItemAccountingCategoryForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ItemAccountingCategory.name(), SecurityRoles.Create.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("ItemAccountingCategoryName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("ParentItemAccountingCategoryName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
            new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
            new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
        ));
    }
    
    /** Creates a new instance of CreateItemAccountingCategoryCommand */
    public CreateItemAccountingCategoryCommand(UserVisitPK userVisitPK, CreateItemAccountingCategoryForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
        String itemAccountingCategoryName = form.getItemAccountingCategoryName();
        ItemAccountingCategory itemAccountingCategory = accountingControl.getItemAccountingCategoryByName(itemAccountingCategoryName);
        
        if(itemAccountingCategory == null) {
            String parentItemAccountingCategoryName = form.getParentItemAccountingCategoryName();
            ItemAccountingCategory parentItemAccountingCategory = null;
            
            if(parentItemAccountingCategoryName != null) {
                parentItemAccountingCategory = accountingControl.getItemAccountingCategoryByName(parentItemAccountingCategoryName);
            }
            
            if(parentItemAccountingCategoryName == null || parentItemAccountingCategory != null) {
                PartyPK partyPK = getPartyPK();
                Boolean isDefault = Boolean.valueOf(form.getIsDefault());
                Integer sortOrder = Integer.valueOf(form.getSortOrder());
                String description = form.getDescription();
                
                itemAccountingCategory = accountingControl.createItemAccountingCategory(itemAccountingCategoryName,
                        parentItemAccountingCategory, null, null, null, null, null, isDefault, sortOrder, partyPK);
                
                if(description != null) {
                    accountingControl.createItemAccountingCategoryDescription(itemAccountingCategory, getPreferredLanguage(),
                            description, partyPK);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownParentItemAccountingCategoryName.name(), parentItemAccountingCategoryName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateItemAccountingCategoryName.name(), itemAccountingCategoryName);
        }
        
        return null;
    }
    
}
