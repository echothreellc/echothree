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

package com.echothree.control.user.accounting.server.command;

import com.echothree.control.user.accounting.common.form.CreateGlAccountForm;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.accounting.server.entity.GlAccountCategory;
import com.echothree.model.data.accounting.server.entity.GlAccountClass;
import com.echothree.model.data.accounting.server.entity.GlAccountType;
import com.echothree.model.data.accounting.server.entity.GlResourceType;
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

public class CreateGlAccountCommand
        extends BaseSimpleCommand<CreateGlAccountForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                new SecurityRoleDefinition(SecurityRoleGroups.GlAccount.name(), SecurityRoles.Create.name())
                )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("GlAccountName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ParentGlAccountName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("GlAccountTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("GlAccountClassName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("GlAccountCategoryName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("GlResourceTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CurrencyIsoName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, false, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of CreateGlAccountCommand */
    public CreateGlAccountCommand(UserVisitPK userVisitPK, CreateGlAccountForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        AccountingControl accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
        String glAccountName = form.getGlAccountName();
        GlAccount glAccount = accountingControl.getGlAccountByName(glAccountName);
        
        if(glAccount == null) {
            String parentGlAccountName = form.getParentGlAccountName();
            GlAccount parentGlAccount = null;
            
            if(parentGlAccountName != null) {
                parentGlAccount = accountingControl.getGlAccountByName(parentGlAccountName);
            }
            
            if(parentGlAccountName == null || parentGlAccount != null) {
                String glAccountTypeName = form.getGlAccountTypeName();
                GlAccountType glAccountType = accountingControl.getGlAccountTypeByName(glAccountTypeName);
                
                if(glAccountType != null) {
                    String glAccountClassName = form.getGlAccountClassName();
                    GlAccountClass glAccountClass = accountingControl.getGlAccountClassByName(glAccountClassName);
                    
                    if(glAccountClass != null) {
                        String glAccountCategoryName = form.getGlAccountCategoryName();
                        GlAccountCategory glAccountCategory = glAccountCategoryName == null? null: accountingControl.getGlAccountCategoryByName(glAccountCategoryName);
                        
                        if(glAccountCategoryName == null || glAccountCategory != null) {
                            String glResourceTypeName = form.getGlResourceTypeName();
                            GlResourceType glResourceType = accountingControl.getGlResourceTypeByName(glResourceTypeName);
                            
                            if(glResourceType != null) {
                                String currencyIsoName = form.getCurrencyIsoName();
                                Currency currency = accountingControl.getCurrencyByIsoName(currencyIsoName);
                                
                                if(currency != null) {
                                    PartyPK partyPK = getPartyPK();
                                    Boolean isDefault = glAccountCategory == null? null: Boolean.valueOf(form.getIsDefault());
                                    String description = form.getDescription();
                                    
                                    glAccount = accountingControl.createGlAccount(glAccountName, parentGlAccount, glAccountType,
                                            glAccountClass, glAccountCategory, glResourceType, currency, isDefault, partyPK);
                                    
                                    if(description != null) {
                                        accountingControl.createGlAccountDescription(glAccount, getPreferredLanguage(), description,
                                                partyPK);
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownCurrencyIsoName.name(), currencyIsoName);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownGlResourceTypeName.name(), glResourceTypeName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownGlAccountCategoryName.name(), glAccountCategoryName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownGlAccountClassName.name(), glAccountClassName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownGlAccountTypeName.name(), glAccountTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownParentGlAccountName.name(), parentGlAccountName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateGlAccountName.name(), glAccountName);
        }
        
        return null;
    }
    
}
