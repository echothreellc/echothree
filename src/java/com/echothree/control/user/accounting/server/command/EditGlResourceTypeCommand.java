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
import com.echothree.control.user.accounting.common.edit.GlResourceTypeEdit;
import com.echothree.control.user.accounting.common.form.EditGlResourceTypeForm;
import com.echothree.control.user.accounting.common.result.AccountingResultFactory;
import com.echothree.control.user.accounting.common.spec.GlResourceTypeSpec;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.party.common.PartyTypes;
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
public class EditGlResourceTypeCommand
        extends BaseEditCommand<GlResourceTypeSpec, GlResourceTypeEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.GlResourceType.name(), SecurityRoles.Edit.name())
                    )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("GlResourceTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("GlResourceTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditGlResourceTypeCommand */
    public EditGlResourceTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var result = AccountingResultFactory.getEditGlResourceTypeResult();
        
        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            var glResourceTypeName = spec.getGlResourceTypeName();
            var glResourceType = accountingControl.getGlResourceTypeByName(glResourceTypeName);
            
            if(glResourceType != null) {
                if(editMode.equals(EditMode.LOCK)) {
                    if(lockEntity(glResourceType)) {
                        var glResourceTypeDescription = accountingControl.getGlResourceTypeDescription(glResourceType, getPreferredLanguage());
                        var edit = AccountingEditFactory.getGlResourceTypeEdit();
                        var glResourceTypeDetail = glResourceType.getLastDetail();

                        result.setGlResourceType(accountingControl.getGlResourceTypeTransfer(getUserVisit(), glResourceType));

                        result.setEdit(edit);
                        edit.setGlResourceTypeName(glResourceTypeDetail.getGlResourceTypeName());
                        edit.setIsDefault(glResourceTypeDetail.getIsDefault().toString());
                        edit.setSortOrder(glResourceTypeDetail.getSortOrder().toString());

                        if(glResourceTypeDescription != null) {
                            edit.setDescription(glResourceTypeDescription.getDescription());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.EntityLockFailed.name());
                    }

                    result.setEntityLock(getEntityLockTransfer(glResourceType));
                } else { // EditMode.ABANDON
                    unlockEntity(glResourceType);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownGlResourceTypeName.name(), glResourceTypeName);
            }
        } else if(editMode.equals(EditMode.UPDATE)) {
            var glResourceTypeName = spec.getGlResourceTypeName();
            var glResourceType = accountingControl.getGlResourceTypeByNameForUpdate(glResourceTypeName);
            
            if(glResourceType != null) {
                glResourceTypeName = edit.getGlResourceTypeName();
                var duplicateGlResourceType = accountingControl.getGlResourceTypeByName(glResourceTypeName);
                
                if(duplicateGlResourceType == null || glResourceType.equals(duplicateGlResourceType)) {
                    if(lockEntityForUpdate(glResourceType)) {
                        try {
                            var partyPK = getPartyPK();
                            var glResourceTypeDetailValue = accountingControl.getGlResourceTypeDetailValueForUpdate(glResourceType);
                            var glResourceTypeDescription = accountingControl.getGlResourceTypeDescriptionForUpdate(glResourceType, getPreferredLanguage());
                            var description = edit.getDescription();
                            
                            glResourceTypeDetailValue.setGlResourceTypeName(edit.getGlResourceTypeName());
                            glResourceTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                            glResourceTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                            
                            accountingControl.updateGlResourceTypeFromValue(glResourceTypeDetailValue, partyPK);
                            
                            if(glResourceTypeDescription == null && description != null) {
                                accountingControl.createGlResourceTypeDescription(glResourceType, getPreferredLanguage(), description, partyPK);
                            } else if(glResourceTypeDescription != null && description == null) {
                                accountingControl.deleteGlResourceTypeDescription(glResourceTypeDescription, partyPK);
                            } else if(glResourceTypeDescription != null && description != null) {
                                var glResourceTypeDescriptionValue = accountingControl.getGlResourceTypeDescriptionValue(glResourceTypeDescription);
                                
                                glResourceTypeDescriptionValue.setDescription(description);
                                accountingControl.updateGlResourceTypeDescriptionFromValue(glResourceTypeDescriptionValue, partyPK);
                            }
                        } finally {
                            unlockEntity(glResourceType);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.EntityLockStale.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.DuplicateGlResourceTypeName.name(), glResourceTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownGlResourceTypeName.name(), glResourceTypeName);
            }
            
            if(hasExecutionErrors()) {
                result.setGlResourceType(accountingControl.getGlResourceTypeTransfer(getUserVisit(), glResourceType));
                result.setEntityLock(getEntityLockTransfer(glResourceType));
            }
        }
        
        return result;
    }
    
}
