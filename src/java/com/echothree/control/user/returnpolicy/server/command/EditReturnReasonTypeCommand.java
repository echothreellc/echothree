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
import com.echothree.control.user.returnpolicy.common.edit.ReturnReasonTypeEdit;
import com.echothree.control.user.returnpolicy.common.form.EditReturnReasonTypeForm;
import com.echothree.control.user.returnpolicy.common.result.ReturnPolicyResultFactory;
import com.echothree.control.user.returnpolicy.common.spec.ReturnReasonTypeSpec;
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

public class EditReturnReasonTypeCommand
        extends BaseEditCommand<ReturnReasonTypeSpec, ReturnReasonTypeEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ReturnReasonType.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ReturnKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ReturnReasonName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ReturnTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditReturnReasonTypeCommand */
    public EditReturnReasonTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var returnPolicyControl = Session.getModelController(ReturnPolicyControl.class);
        var result = ReturnPolicyResultFactory.getEditReturnReasonTypeResult();
        var returnKindName = spec.getReturnKindName();
        var returnKind = returnPolicyControl.getReturnKindByName(returnKindName);
        
        if(returnKind != null) {
            var returnReasonName = spec.getReturnReasonName();
            var returnReason = returnPolicyControl.getReturnReasonByName(returnKind, returnReasonName);
            
            if(returnReason != null) {
                var returnTypeName = spec.getReturnTypeName();
                var returnType = returnPolicyControl.getReturnTypeByName(returnKind, returnTypeName);
                
                if(returnType != null) {
                    if(editMode.equals(EditMode.LOCK)) {
                        var returnReasonType = returnPolicyControl.getReturnReasonType(returnReason, returnType);
                        
                        if(returnReasonType != null) {
                            result.setReturnReasonType(returnPolicyControl.getReturnReasonTypeTransfer(getUserVisit(), returnReasonType));
                            
                            if(lockEntity(returnReason)) {
                                var edit = ReturnPolicyEditFactory.getReturnReasonTypeEdit();
                                
                                result.setEdit(edit);
                                edit.setIsDefault(returnReasonType.getIsDefault().toString());
                                edit.setSortOrder(returnReasonType.getSortOrder().toString());
                                
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockFailed.name());
                            }
                            
                            result.setEntityLock(getEntityLockTransfer(returnReasonType));
                        } else {
                            addExecutionError(ExecutionErrors.UnknownReturnReasonType.name());
                        }
                    } else if(editMode.equals(EditMode.UPDATE)) {
                        var returnReasonTypeValue = returnPolicyControl.getReturnReasonTypeValueForUpdate(returnReason, returnType);
                        
                        if(returnReasonTypeValue != null) {
                            if(lockEntityForUpdate(returnReason)) {
                                try {
                                    returnReasonTypeValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                                    returnReasonTypeValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                                    
                                    returnPolicyControl.updateReturnReasonTypeFromValue(returnReasonTypeValue, getPartyPK());
                                } finally {
                                    unlockEntity(returnReason);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockStale.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownReturnReasonType.name());
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownReturnTypeName.name(), returnTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownReturnReasonName.name(), returnReasonName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownReturnKindName.name(), returnKindName);
        }
        
        return result;
    }
    
}
