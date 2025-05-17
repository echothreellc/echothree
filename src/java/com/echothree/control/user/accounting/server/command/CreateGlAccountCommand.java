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

import com.echothree.control.user.accounting.common.form.CreateGlAccountForm;
import com.echothree.control.user.accounting.common.result.AccountingResultFactory;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.accounting.server.logic.GlAccountLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.List;

public class CreateGlAccountCommand
        extends BaseSimpleCommand<CreateGlAccountForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.GlAccount.name(), SecurityRoles.Create.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("GlAccountName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ParentGlAccountName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("GlAccountTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("GlAccountClassName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("GlAccountCategoryName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("GlResourceTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CurrencyIsoName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, false, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }
    
    /** Creates a new instance of CreateGlAccountCommand */
    public CreateGlAccountCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = AccountingResultFactory.getCreateGlAccountResult();
        var accountingControl = Session.getModelController(AccountingControl.class);
        GlAccount glAccount = null;
        var parentGlAccountName = form.getParentGlAccountName();
        GlAccount parentGlAccount = null;

        if(parentGlAccountName != null) {
            parentGlAccount = accountingControl.getGlAccountByName(parentGlAccountName);
        }

        if(parentGlAccountName == null || parentGlAccount != null) {
            var glAccountTypeName = form.getGlAccountTypeName();
            var glAccountType = accountingControl.getGlAccountTypeByName(glAccountTypeName);

            if(glAccountType != null) {
                var glAccountClassName = form.getGlAccountClassName();
                var glAccountClass = accountingControl.getGlAccountClassByName(glAccountClassName);

                if(glAccountClass != null) {
                    var glAccountCategoryName = form.getGlAccountCategoryName();
                    var glAccountCategory = glAccountCategoryName == null? null: accountingControl.getGlAccountCategoryByName(glAccountCategoryName);

                    if(glAccountCategoryName == null || glAccountCategory != null) {
                        var glResourceTypeName = form.getGlResourceTypeName();
                        var glResourceType = accountingControl.getGlResourceTypeByName(glResourceTypeName);

                        if(glResourceType != null) {
                            var currencyIsoName = form.getCurrencyIsoName();
                            var currency = accountingControl.getCurrencyByIsoName(currencyIsoName);

                            if(currency != null) {
                                var glAccountName = form.getGlAccountName();
                                var isDefault = glAccountCategory == null? null: Boolean.valueOf(form.getIsDefault());
                                var description = form.getDescription();
                                var partyPK = getPartyPK();

                                glAccount = GlAccountLogic.getInstance().createGlAccount(this, glAccountName, parentGlAccount,
                                        glAccountType, glAccountClass, glAccountCategory, glResourceType, currency, isDefault,
                                        getPreferredLanguage(), description, partyPK);
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

        if(glAccount != null) {
            result.setGlAccountName(glAccount.getLastDetail().getGlAccountName());
            result.setEntityRef(glAccount.getPrimaryKey().getEntityRef());
        }

        return result;
    }
    
}
