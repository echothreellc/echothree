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
import com.echothree.control.user.accounting.common.edit.GlAccountClassEdit;
import com.echothree.control.user.accounting.common.form.EditGlAccountClassForm;
import com.echothree.control.user.accounting.common.result.AccountingResultFactory;
import com.echothree.control.user.accounting.common.spec.GlAccountClassSpec;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.accounting.server.entity.GlAccountClass;
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

public class EditGlAccountClassCommand
        extends BaseEditCommand<GlAccountClassSpec, GlAccountClassEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.GlAccountClass.name(), SecurityRoles.Edit.name())
                    )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("GlAccountClassName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("GlAccountClassName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ParentGlAccountClassName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditGlAccountClassCommand */
    public EditGlAccountClassCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var result = AccountingResultFactory.getEditGlAccountClassResult();
        
        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            var glAccountClassName = spec.getGlAccountClassName();
            var glAccountClass = accountingControl.getGlAccountClassByName(glAccountClassName);
            
            if(glAccountClass != null) {
                if(editMode.equals(EditMode.LOCK)) {
                    if(lockEntity(glAccountClass)) {
                        var glAccountClassDescription = accountingControl.getGlAccountClassDescription(glAccountClass, getPreferredLanguage());
                        var edit = AccountingEditFactory.getGlAccountClassEdit();
                        var glAccountClassDetail = glAccountClass.getLastDetail();
                        var parentGlAccountClass = glAccountClassDetail.getParentGlAccountClass();

                        result.setGlAccountClass(accountingControl.getGlAccountClassTransfer(getUserVisit(), glAccountClass));

                        result.setEdit(edit);
                        edit.setGlAccountClassName(glAccountClassDetail.getGlAccountClassName());
                        edit.setParentGlAccountClassName(parentGlAccountClass == null? null: parentGlAccountClass.getLastDetail().getGlAccountClassName());
                        edit.setIsDefault(glAccountClassDetail.getIsDefault().toString());
                        edit.setSortOrder(glAccountClassDetail.getSortOrder().toString());

                        if(glAccountClassDescription != null) {
                            edit.setDescription(glAccountClassDescription.getDescription());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.EntityLockFailed.name());
                    }

                    result.setEntityLock(getEntityLockTransfer(glAccountClass));
                } else { // EditMode.ABANDON
                    unlockEntity(glAccountClass);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownGlAccountClassName.name(), glAccountClassName);
            }
        } else if(editMode.equals(EditMode.UPDATE)) {
            var glAccountClassName = spec.getGlAccountClassName();
            var glAccountClass = accountingControl.getGlAccountClassByNameForUpdate(glAccountClassName);
            
            if(glAccountClass != null) {
                glAccountClassName = edit.getGlAccountClassName();
                var duplicateGlAccountClass = accountingControl.getGlAccountClassByName(glAccountClassName);
                
                if(duplicateGlAccountClass == null || glAccountClass.equals(duplicateGlAccountClass)) {
                    var parentGlAccountClassName = edit.getParentGlAccountClassName();
                    GlAccountClass parentGlAccountClass = null;
                    
                    if(parentGlAccountClassName != null) {
                        parentGlAccountClass = accountingControl.getGlAccountClassByName(parentGlAccountClassName);
                    }
                    
                    if(parentGlAccountClassName == null || parentGlAccountClass != null) {
                        if(accountingControl.isParentGlAccountClassSafe(glAccountClass, parentGlAccountClass)) {
                            if(lockEntityForUpdate(glAccountClass)) {
                                try {
                                    var partyPK = getPartyPK();
                                    var glAccountClassDetailValue = accountingControl.getGlAccountClassDetailValueForUpdate(glAccountClass);
                                    var glAccountClassDescription = accountingControl.getGlAccountClassDescriptionForUpdate(glAccountClass, getPreferredLanguage());
                                    var description = edit.getDescription();
                                    
                                    glAccountClassDetailValue.setGlAccountClassName(edit.getGlAccountClassName());
                                    glAccountClassDetailValue.setParentGlAccountClassPK(parentGlAccountClass == null? null: parentGlAccountClass.getPrimaryKey());
                                    glAccountClassDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                                    glAccountClassDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                                    
                                    accountingControl.updateGlAccountClassFromValue(glAccountClassDetailValue, partyPK);
                                    
                                    if(glAccountClassDescription == null && description != null) {
                                        accountingControl.createGlAccountClassDescription(glAccountClass, getPreferredLanguage(), description, partyPK);
                                    } else if(glAccountClassDescription != null && description == null) {
                                        accountingControl.deleteGlAccountClassDescription(glAccountClassDescription, partyPK);
                                    } else if(glAccountClassDescription != null && description != null) {
                                        var glAccountClassDescriptionValue = accountingControl.getGlAccountClassDescriptionValue(glAccountClassDescription);
                                        
                                        glAccountClassDescriptionValue.setDescription(description);
                                        accountingControl.updateGlAccountClassDescriptionFromValue(glAccountClassDescriptionValue, partyPK);
                                    }
                                } finally {
                                    unlockEntity(glAccountClass);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockStale.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.InvalidParentGlAccountClass.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownParentGlAccountClassName.name(), parentGlAccountClassName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.DuplicateGlAccountClassName.name(), glAccountClassName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownGlAccountClassName.name(), glAccountClassName);
            }
            
            if(hasExecutionErrors()) {
                result.setGlAccountClass(accountingControl.getGlAccountClassTransfer(getUserVisit(), glAccountClass));
                result.setEntityLock(getEntityLockTransfer(glAccountClass));
            }
        }
        
        return result;
    }
    
}
