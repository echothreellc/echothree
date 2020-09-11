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

import com.echothree.control.user.accounting.common.edit.AccountingEditFactory;
import com.echothree.control.user.accounting.common.edit.GlAccountEdit;
import com.echothree.control.user.accounting.common.form.EditGlAccountForm;
import com.echothree.control.user.accounting.common.result.AccountingResultFactory;
import com.echothree.control.user.accounting.common.result.EditGlAccountResult;
import com.echothree.control.user.accounting.common.spec.GlAccountSpec;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.accounting.server.entity.GlAccountCategory;
import com.echothree.model.data.accounting.server.entity.GlAccountClass;
import com.echothree.model.data.accounting.server.entity.GlAccountDescription;
import com.echothree.model.data.accounting.server.entity.GlAccountDetail;
import com.echothree.model.data.accounting.server.entity.GlResourceType;
import com.echothree.model.data.accounting.server.value.GlAccountDescriptionValue;
import com.echothree.model.data.accounting.server.value.GlAccountDetailValue;
import com.echothree.model.data.party.common.pk.PartyPK;
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

public class EditGlAccountCommand
        extends BaseEditCommand<GlAccountSpec, GlAccountEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                new SecurityRoleDefinition(SecurityRoleGroups.GlAccount.name(), SecurityRoles.Edit.name())
                )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("GlAccountName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("GlAccountName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ParentGlAccountName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("GlAccountClassName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("GlAccountCategoryName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("GlResourceTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, false, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditGlAccountCommand */
    public EditGlAccountCommand(UserVisitPK userVisitPK, EditGlAccountForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
        EditGlAccountResult result = AccountingResultFactory.getEditGlAccountResult();
        
        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            String glAccountName = spec.getGlAccountName();
            GlAccount glAccount = accountingControl.getGlAccountByName(glAccountName);
            
            if(glAccount != null) {
                if(editMode.equals(EditMode.LOCK)) {
                    if(lockEntity(glAccount)) {
                        GlAccountDescription glAccountDescription = accountingControl.getGlAccountDescription(glAccount, getPreferredLanguage());
                        GlAccountEdit edit = AccountingEditFactory.getGlAccountEdit();
                        GlAccountDetail glAccountDetail = glAccount.getLastDetail();
                        GlAccount parentGlAccount = glAccountDetail.getParentGlAccount();
                        GlAccountCategory glAccountCategory = glAccountDetail.getGlAccountCategory();
                        Boolean isDefault = glAccountDetail.getIsDefault();

                        result.setGlAccount(accountingControl.getGlAccountTransfer(getUserVisit(), glAccount));
                        sendEventUsingNames(glAccountCategory.getPrimaryKey(), EventTypes.READ.name(), null, null, getPartyPK());

                        result.setEdit(edit);
                        edit.setGlAccountName(glAccountDetail.getGlAccountName());
                        edit.setParentGlAccountName(parentGlAccount == null? null: parentGlAccount.getLastDetail().getGlAccountName());
                        edit.setGlAccountClassName(glAccountDetail.getGlAccountClass().getLastDetail().getGlAccountClassName());
                        edit.setGlAccountCategoryName(glAccountCategory == null? null: glAccountCategory.getLastDetail().getGlAccountCategoryName());
                        edit.setGlResourceTypeName(glAccountDetail.getGlResourceType().getLastDetail().getGlResourceTypeName());
                        edit.setIsDefault(isDefault == null? null: isDefault.toString());

                        if(glAccountDescription != null) {
                            edit.setDescription(glAccountDescription.getDescription());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.EntityLockFailed.name());
                    }

                    result.setEntityLock(getEntityLockTransfer(glAccount));
                } else { // EditMode.ABANDON
                    unlockEntity(glAccount);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownGlAccountName.name(), glAccountName);
            }
        } else if(editMode.equals(EditMode.UPDATE)) {
            String glAccountName = spec.getGlAccountName();
            GlAccount glAccount = accountingControl.getGlAccountByNameForUpdate(glAccountName);
            
            if(glAccount != null) {
                glAccountName = edit.getGlAccountName();
                GlAccount duplicateGlAccount = accountingControl.getGlAccountByName(glAccountName);
                
                if(duplicateGlAccount == null || glAccount.equals(duplicateGlAccount)) {
                    String parentGlAccountName = edit.getParentGlAccountName();
                    GlAccount parentGlAccount = null;
                    
                    if(parentGlAccountName != null) {
                        parentGlAccount = accountingControl.getGlAccountByName(parentGlAccountName);
                    }
                    
                    if(parentGlAccountName == null || parentGlAccount != null) {
                        if(accountingControl.isParentGlAccountSafe(glAccount, parentGlAccount)) {
                            String glAccountClassName = edit.getGlAccountClassName();
                            GlAccountClass glAccountClass = accountingControl.getGlAccountClassByName(glAccountClassName);
                            
                            if(glAccountClass != null) {
                                String glAccountCategoryName = edit.getGlAccountCategoryName();
                                GlAccountCategory glAccountCategory = glAccountCategoryName == null? null: accountingControl.getGlAccountCategoryByName(glAccountCategoryName);
                                
                                if(glAccountCategoryName == null || glAccountCategory != null) {
                                    String glResourceTypeName = edit.getGlResourceTypeName();
                                    GlResourceType glResourceType = accountingControl.getGlResourceTypeByName(glResourceTypeName);
                                    
                                    if(glResourceType != null) {
                                        if(lockEntityForUpdate(glAccount)) {
                                            try {
                                                var partyPK = getPartyPK();
                                                GlAccountDetailValue glAccountDetailValue = accountingControl.getGlAccountDetailValueForUpdate(glAccount);
                                                GlAccountDescription glAccountDescription = accountingControl.getGlAccountDescriptionForUpdate(glAccount, getPreferredLanguage());
                                                Boolean isDefault = glAccountCategory == null? null: Boolean.valueOf(edit.getIsDefault());
                                                String description = edit.getDescription();
                                                
                                                glAccountDetailValue.setGlAccountName(glAccountName);
                                                glAccountDetailValue.setParentGlAccountPK(parentGlAccount == null? null: parentGlAccount.getPrimaryKey());
                                                glAccountDetailValue.setGlAccountClassPK(glAccountClass.getPrimaryKey());
                                                glAccountDetailValue.setGlAccountCategoryPK(glAccountCategory == null? null: glAccountCategory.getPrimaryKey());
                                                glAccountDetailValue.setGlResourceTypePK(glResourceType.getPrimaryKey());
                                                glAccountDetailValue.setIsDefault(isDefault);
                                                
                                                accountingControl.updateGlAccountFromValue(glAccountDetailValue, partyPK);
                                                
                                                if(glAccountDescription == null && description != null) {
                                                    accountingControl.createGlAccountDescription(glAccount, getPreferredLanguage(), description, partyPK);
                                                } else if(glAccountDescription != null && description == null) {
                                                    accountingControl.deleteGlAccountDescription(glAccountDescription, partyPK);
                                                } else if(glAccountDescription != null && description != null) {
                                                    GlAccountDescriptionValue glAccountDescriptionValue = accountingControl.getGlAccountDescriptionValue(glAccountDescription);
                                                    
                                                    glAccountDescriptionValue.setDescription(description);
                                                    accountingControl.updateGlAccountDescriptionFromValue(glAccountDescriptionValue, partyPK);
                                                }
                                            } finally {
                                                unlockEntity(glAccount);
                                            }
                                        } else {
                                            addExecutionError(ExecutionErrors.EntityLockStale.name());
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
                            addExecutionError(ExecutionErrors.InvalidParentGlAccount.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownParentGlAccountName.name(), parentGlAccountName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.DuplicateGlAccountName.name(), glAccountName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownGlAccountName.name(), glAccountName);
            }
            
            if(hasExecutionErrors()) {
                result.setGlAccount(accountingControl.getGlAccountTransfer(getUserVisit(), glAccount));
                result.setEntityLock(getEntityLockTransfer(glAccount));
            }
        }
        
        return result;
    }
    
}
