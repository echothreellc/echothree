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
import com.echothree.control.user.accounting.common.edit.GlAccountEdit;
import com.echothree.control.user.accounting.common.form.EditGlAccountForm;
import com.echothree.control.user.accounting.common.result.AccountingResultFactory;
import com.echothree.control.user.accounting.common.spec.GlAccountUniversalSpec;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.accounting.server.logic.GlAccountLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class EditGlAccountCommand
        extends BaseEditCommand<GlAccountUniversalSpec, GlAccountEdit> {
    
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
                new FieldDefinition("GlAccountName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("GlAccountName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ParentGlAccountName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("GlAccountClassName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("GlAccountCategoryName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("GlResourceTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, false, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditGlAccountCommand */
    public EditGlAccountCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var result = AccountingResultFactory.getEditGlAccountResult();
        
        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            var glAccount = GlAccountLogic.getInstance().getGlAccountByUniversalSpec(this, spec, false, null);

            if(!hasExecutionErrors()) {
                if(editMode.equals(EditMode.LOCK)) {
                    if(lockEntity(glAccount)) {
                        var glAccountDescription = accountingControl.getGlAccountDescription(glAccount, getPreferredLanguage());
                        var edit = AccountingEditFactory.getGlAccountEdit();
                        var glAccountDetail = glAccount.getLastDetail();
                        var parentGlAccount = glAccountDetail.getParentGlAccount();
                        var glAccountCategory = glAccountDetail.getGlAccountCategory();
                        var isDefault = glAccountDetail.getIsDefault();

                        result.setGlAccount(accountingControl.getGlAccountTransfer(getUserVisit(), glAccount));

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
            }
        } else if(editMode.equals(EditMode.UPDATE)) {
            var glAccount = GlAccountLogic.getInstance().getGlAccountByUniversalSpecForUpdate(this, spec, false, null);

            if(!hasExecutionErrors()) {
                var glAccountName = edit.getGlAccountName();
                var duplicateGlAccount = accountingControl.getGlAccountByName(glAccountName);
                
                if(duplicateGlAccount == null || glAccount.equals(duplicateGlAccount)) {
                    var parentGlAccountName = edit.getParentGlAccountName();
                    GlAccount parentGlAccount = null;
                    
                    if(parentGlAccountName != null) {
                        parentGlAccount = accountingControl.getGlAccountByName(parentGlAccountName);
                    }
                    
                    if(parentGlAccountName == null || parentGlAccount != null) {
                        if(accountingControl.isParentGlAccountSafe(glAccount, parentGlAccount)) {
                            var glAccountClassName = edit.getGlAccountClassName();
                            var glAccountClass = accountingControl.getGlAccountClassByName(glAccountClassName);
                            
                            if(glAccountClass != null) {
                                var glAccountCategoryName = edit.getGlAccountCategoryName();
                                var glAccountCategory = glAccountCategoryName == null? null: accountingControl.getGlAccountCategoryByName(glAccountCategoryName);
                                
                                if(glAccountCategoryName == null || glAccountCategory != null) {
                                    var glResourceTypeName = edit.getGlResourceTypeName();
                                    var glResourceType = accountingControl.getGlResourceTypeByName(glResourceTypeName);
                                    
                                    if(glResourceType != null) {
                                        if(lockEntityForUpdate(glAccount)) {
                                            try {
                                                var partyPK = getPartyPK();
                                                var glAccountDetailValue = accountingControl.getGlAccountDetailValueForUpdate(glAccount);
                                                var glAccountDescription = accountingControl.getGlAccountDescriptionForUpdate(glAccount, getPreferredLanguage());
                                                var isDefault = glAccountCategory == null? null: Boolean.valueOf(edit.getIsDefault());
                                                var description = edit.getDescription();
                                                
                                                glAccountDetailValue.setGlAccountName(glAccountName);
                                                glAccountDetailValue.setParentGlAccountPK(parentGlAccount == null? null: parentGlAccount.getPrimaryKey());
                                                glAccountDetailValue.setGlAccountClassPK(glAccountClass.getPrimaryKey());
                                                glAccountDetailValue.setGlAccountCategoryPK(glAccountCategory == null? null: glAccountCategory.getPrimaryKey());
                                                glAccountDetailValue.setGlResourceTypePK(glResourceType.getPrimaryKey());
                                                glAccountDetailValue.setIsDefault(isDefault);
                                                
                                                GlAccountLogic.getInstance().updateGlAccountFromValue(glAccountDetailValue, partyPK);
                                                
                                                if(glAccountDescription == null && description != null) {
                                                    accountingControl.createGlAccountDescription(glAccount, getPreferredLanguage(), description, partyPK);
                                                } else if(glAccountDescription != null && description == null) {
                                                    accountingControl.deleteGlAccountDescription(glAccountDescription, partyPK);
                                                } else if(glAccountDescription != null && description != null) {
                                                    var glAccountDescriptionValue = accountingControl.getGlAccountDescriptionValue(glAccountDescription);
                                                    
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
            }
            
            if(hasExecutionErrors()) {
                result.setGlAccount(accountingControl.getGlAccountTransfer(getUserVisit(), glAccount));
                result.setEntityLock(getEntityLockTransfer(glAccount));
            }
        }
        
        return result;
    }
    
}
