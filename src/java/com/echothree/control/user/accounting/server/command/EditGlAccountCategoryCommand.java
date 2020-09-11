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
import com.echothree.control.user.accounting.common.edit.GlAccountCategoryEdit;
import com.echothree.control.user.accounting.common.form.EditGlAccountCategoryForm;
import com.echothree.control.user.accounting.common.result.AccountingResultFactory;
import com.echothree.control.user.accounting.common.result.EditGlAccountCategoryResult;
import com.echothree.control.user.accounting.common.spec.GlAccountCategorySpec;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.accounting.server.entity.GlAccountCategory;
import com.echothree.model.data.accounting.server.entity.GlAccountCategoryDescription;
import com.echothree.model.data.accounting.server.entity.GlAccountCategoryDetail;
import com.echothree.model.data.accounting.server.value.GlAccountCategoryDescriptionValue;
import com.echothree.model.data.accounting.server.value.GlAccountCategoryDetailValue;
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

public class EditGlAccountCategoryCommand
        extends BaseEditCommand<GlAccountCategorySpec, GlAccountCategoryEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.GlAccountCategory.name(), SecurityRoles.Edit.name())
                    )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("GlAccountCategoryName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("GlAccountCategoryName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ParentGlAccountCategoryName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditGlAccountCategoryCommand */
    public EditGlAccountCategoryCommand(UserVisitPK userVisitPK, EditGlAccountCategoryForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
        EditGlAccountCategoryResult result = AccountingResultFactory.getEditGlAccountCategoryResult();
        
        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            String glAccountCategoryName = spec.getGlAccountCategoryName();
            GlAccountCategory glAccountCategory = accountingControl.getGlAccountCategoryByName(glAccountCategoryName);
            
            if(glAccountCategory != null) {
                if(editMode.equals(EditMode.LOCK)) {
                    if(lockEntity(glAccountCategory)) {
                        GlAccountCategoryDescription glAccountCategoryDescription = accountingControl.getGlAccountCategoryDescription(glAccountCategory, getPreferredLanguage());
                        GlAccountCategoryEdit edit = AccountingEditFactory.getGlAccountCategoryEdit();
                        GlAccountCategoryDetail glAccountCategoryDetail = glAccountCategory.getLastDetail();
                        GlAccountCategory parentGlAccountCategory = glAccountCategoryDetail.getParentGlAccountCategory();

                        result.setGlAccountCategory(accountingControl.getGlAccountCategoryTransfer(getUserVisit(), glAccountCategory));
                        sendEventUsingNames(glAccountCategory.getPrimaryKey(), EventTypes.READ.name(), null, null, getPartyPK());

                        result.setEdit(edit);
                        edit.setGlAccountCategoryName(glAccountCategoryDetail.getGlAccountCategoryName());
                        edit.setParentGlAccountCategoryName(parentGlAccountCategory == null? null: parentGlAccountCategory.getLastDetail().getGlAccountCategoryName());
                        edit.setIsDefault(glAccountCategoryDetail.getIsDefault().toString());
                        edit.setSortOrder(glAccountCategoryDetail.getSortOrder().toString());

                        if(glAccountCategoryDescription != null) {
                            edit.setDescription(glAccountCategoryDescription.getDescription());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.EntityLockFailed.name());
                    }

                    result.setEntityLock(getEntityLockTransfer(glAccountCategory));
                } else { // EditMode.ABANDON
                    unlockEntity(glAccountCategory);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownGlAccountCategoryName.name(), glAccountCategoryName);
            }
        } else if(editMode.equals(EditMode.UPDATE)) {
            String glAccountCategoryName = spec.getGlAccountCategoryName();
            GlAccountCategory glAccountCategory = accountingControl.getGlAccountCategoryByNameForUpdate(glAccountCategoryName);
            
            if(glAccountCategory != null) {
                glAccountCategoryName = edit.getGlAccountCategoryName();
                GlAccountCategory duplicateGlAccountCategory = accountingControl.getGlAccountCategoryByName(glAccountCategoryName);
                
                if(duplicateGlAccountCategory == null || glAccountCategory.equals(duplicateGlAccountCategory)) {
                    String parentGlAccountCategoryName = edit.getParentGlAccountCategoryName();
                    GlAccountCategory parentGlAccountCategory = null;
                    
                    if(parentGlAccountCategoryName != null) {
                        parentGlAccountCategory = accountingControl.getGlAccountCategoryByName(parentGlAccountCategoryName);
                    }
                    
                    if(parentGlAccountCategoryName == null || parentGlAccountCategory != null) {
                        if(accountingControl.isParentGlAccountCategorySafe(glAccountCategory, parentGlAccountCategory)) {
                            if(lockEntityForUpdate(glAccountCategory)) {
                                try {
                                    var partyPK = getPartyPK();
                                    GlAccountCategoryDetailValue glAccountCategoryDetailValue = accountingControl.getGlAccountCategoryDetailValueForUpdate(glAccountCategory);
                                    GlAccountCategoryDescription glAccountCategoryDescription = accountingControl.getGlAccountCategoryDescriptionForUpdate(glAccountCategory, getPreferredLanguage());
                                    String description = edit.getDescription();
                                    
                                    glAccountCategoryDetailValue.setGlAccountCategoryName(edit.getGlAccountCategoryName());
                                    glAccountCategoryDetailValue.setParentGlAccountCategoryPK(parentGlAccountCategory == null? null: parentGlAccountCategory.getPrimaryKey());
                                    glAccountCategoryDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                                    glAccountCategoryDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                                    
                                    accountingControl.updateGlAccountCategoryFromValue(glAccountCategoryDetailValue, partyPK);
                                    
                                    if(glAccountCategoryDescription == null && description != null) {
                                        accountingControl.createGlAccountCategoryDescription(glAccountCategory, getPreferredLanguage(), description, partyPK);
                                    } else if(glAccountCategoryDescription != null && description == null) {
                                        accountingControl.deleteGlAccountCategoryDescription(glAccountCategoryDescription, partyPK);
                                    } else if(glAccountCategoryDescription != null && description != null) {
                                        GlAccountCategoryDescriptionValue glAccountCategoryDescriptionValue = accountingControl.getGlAccountCategoryDescriptionValue(glAccountCategoryDescription);
                                        
                                        glAccountCategoryDescriptionValue.setDescription(description);
                                        accountingControl.updateGlAccountCategoryDescriptionFromValue(glAccountCategoryDescriptionValue, partyPK);
                                    }
                                } finally {
                                    unlockEntity(glAccountCategory);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockStale.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.InvalidParentGlAccountCategory.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownParentGlAccountCategoryName.name(), parentGlAccountCategoryName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.DuplicateGlAccountCategoryName.name(), glAccountCategoryName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownGlAccountCategoryName.name(), glAccountCategoryName);
            }
            
            if(hasExecutionErrors()) {
                result.setGlAccountCategory(accountingControl.getGlAccountCategoryTransfer(getUserVisit(), glAccountCategory));
                result.setEntityLock(getEntityLockTransfer(glAccountCategory));
            }
        }
        
        return result;
    }
    
}
