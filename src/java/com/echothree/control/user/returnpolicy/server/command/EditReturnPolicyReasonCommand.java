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

package com.echothree.control.user.returnpolicy.server.command;

import com.echothree.control.user.returnpolicy.common.edit.ReturnPolicyEditFactory;
import com.echothree.control.user.returnpolicy.common.edit.ReturnPolicyReasonEdit;
import com.echothree.control.user.returnpolicy.common.form.EditReturnPolicyReasonForm;
import com.echothree.control.user.returnpolicy.common.result.ReturnPolicyResultFactory;
import com.echothree.control.user.returnpolicy.common.spec.ReturnPolicyReasonSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
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

public class EditReturnPolicyReasonCommand
        extends BaseEditCommand<ReturnPolicyReasonSpec, ReturnPolicyReasonEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ReturnPolicyReason.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ReturnKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ReturnPolicyName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ReturnReasonName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditReturnPolicyReasonCommand */
    public EditReturnPolicyReasonCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var returnPolicyControl = Session.getModelController(ReturnPolicyControl.class);
        var result = ReturnPolicyResultFactory.getEditReturnPolicyReasonResult();
        var returnPolicyName = spec.getReturnPolicyName();
        var returnKindName = spec.getReturnKindName();
        var returnKind = returnPolicyControl.getReturnKindByName(returnKindName);
        
        if(returnKind != null) {
            var returnPolicy = returnPolicyControl.getReturnPolicyByName(returnKind, returnPolicyName);
            
            if(returnPolicy != null) {
                var returnReasonName = spec.getReturnReasonName();
                var returnReason = returnPolicyControl.getReturnReasonByName(returnKind, returnReasonName);
                
                if(returnReason != null) {
                    if(editMode.equals(EditMode.LOCK)) {
                        var returnPolicyReason = returnPolicyControl.getReturnPolicyReason(returnPolicy, returnReason);
                        
                        if(returnPolicyReason != null) {
                            result.setReturnPolicyReason(returnPolicyControl.getReturnPolicyReasonTransfer(getUserVisit(), returnPolicyReason));
                            
                            if(lockEntity(returnPolicy)) {
                                var edit = ReturnPolicyEditFactory.getReturnPolicyReasonEdit();
                                
                                result.setEdit(edit);
                                edit.setIsDefault(returnPolicyReason.getIsDefault().toString());
                                edit.setSortOrder(returnPolicyReason.getSortOrder().toString());
                                
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockFailed.name());
                            }
                            
                            result.setEntityLock(getEntityLockTransfer(returnPolicyReason));
                        } else {
                            addExecutionError(ExecutionErrors.UnknownReturnPolicyReason.name());
                        }
                    } else if(editMode.equals(EditMode.UPDATE)) {
                        var returnPolicyReasonValue = returnPolicyControl.getReturnPolicyReasonValueForUpdate(returnPolicy, returnReason);
                        
                        if(returnPolicyReasonValue != null) {
                            if(lockEntityForUpdate(returnPolicy)) {
                                try {
                                    returnPolicyReasonValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                                    returnPolicyReasonValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                                    
                                    returnPolicyControl.updateReturnPolicyReasonFromValue(returnPolicyReasonValue, getPartyPK());
                                } finally {
                                    unlockEntity(returnPolicy);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockStale.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownReturnPolicyReason.name());
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownReturnReasonName.name(), returnReasonName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownReturnPolicyName.name(), returnPolicyName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownReturnKindName.name(), returnKindName);
        }
        
        return result;
    }
    
}
