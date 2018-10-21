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

package com.echothree.control.user.returnpolicy.server.command;

import com.echothree.control.user.returnpolicy.remote.edit.ReturnPolicyEditFactory;
import com.echothree.control.user.returnpolicy.remote.edit.ReturnTypeShippingMethodEdit;
import com.echothree.control.user.returnpolicy.remote.form.EditReturnTypeShippingMethodForm;
import com.echothree.control.user.returnpolicy.remote.result.EditReturnTypeShippingMethodResult;
import com.echothree.control.user.returnpolicy.remote.result.ReturnPolicyResultFactory;
import com.echothree.control.user.returnpolicy.remote.spec.ReturnTypeShippingMethodSpec;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.returnpolicy.server.ReturnPolicyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.shipping.server.ShippingControl;
import com.echothree.model.data.returnpolicy.server.entity.ReturnKind;
import com.echothree.model.data.returnpolicy.server.entity.ReturnType;
import com.echothree.model.data.returnpolicy.server.entity.ReturnTypeShippingMethod;
import com.echothree.model.data.returnpolicy.server.value.ReturnTypeShippingMethodValue;
import com.echothree.model.data.shipping.server.entity.ShippingMethod;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.remote.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditReturnTypeShippingMethodCommand
        extends BaseEditCommand<ReturnTypeShippingMethodSpec, ReturnTypeShippingMethodEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ReturnTypeShippingMethod.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ReturnKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ReturnTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ShippingMethodName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditReturnTypeShippingMethodCommand */
    public EditReturnTypeShippingMethodCommand(UserVisitPK userVisitPK, EditReturnTypeShippingMethodForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        ReturnPolicyControl returnPolicyControl = (ReturnPolicyControl)Session.getModelController(ReturnPolicyControl.class);
        EditReturnTypeShippingMethodResult result = ReturnPolicyResultFactory.getEditReturnTypeShippingMethodResult();
        String returnKindName = spec.getReturnKindName();
        ReturnKind returnKind = returnPolicyControl.getReturnKindByName(returnKindName);
        
        if(returnKind != null) {
            String returnTypeName = spec.getReturnTypeName();
            ReturnType returnType = returnPolicyControl.getReturnTypeByName(returnKind, returnTypeName);
            
            if(returnType != null) {
                ShippingControl shippingControl = (ShippingControl)Session.getModelController(ShippingControl.class);
                String shippingMethodName = spec.getShippingMethodName();
                ShippingMethod shippingMethod = shippingControl.getShippingMethodByName(shippingMethodName);
                
                if(shippingMethod != null) {
                    if(editMode.equals(EditMode.LOCK)) {
                        ReturnTypeShippingMethod returnTypeShippingMethod = returnPolicyControl.getReturnTypeShippingMethod(returnType, shippingMethod);
                        
                        if(returnTypeShippingMethod != null) {
                            result.setReturnTypeShippingMethod(returnPolicyControl.getReturnTypeShippingMethodTransfer(getUserVisit(), returnTypeShippingMethod));
                            
                            if(lockEntity(returnType)) {
                                ReturnTypeShippingMethodEdit edit = ReturnPolicyEditFactory.getReturnTypeShippingMethodEdit();
                                
                                result.setEdit(edit);
                                edit.setIsDefault(returnTypeShippingMethod.getIsDefault().toString());
                                edit.setSortOrder(returnTypeShippingMethod.getSortOrder().toString());
                                
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockFailed.name());
                            }
                            
                            result.setEntityLock(getEntityLockTransfer(returnTypeShippingMethod));
                        } else {
                            addExecutionError(ExecutionErrors.UnknownReturnTypeShippingMethod.name());
                        }
                    } else if(editMode.equals(EditMode.UPDATE)) {
                        ReturnTypeShippingMethodValue returnTypeShippingMethodValue = returnPolicyControl.getReturnTypeShippingMethodValueForUpdate(returnType, shippingMethod);
                        
                        if(returnTypeShippingMethodValue != null) {
                            if(lockEntityForUpdate(returnType)) {
                                try {
                                    returnTypeShippingMethodValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                                    returnTypeShippingMethodValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                                    
                                    returnPolicyControl.updateReturnTypeShippingMethodFromValue(returnTypeShippingMethodValue, getPartyPK());
                                } finally {
                                    unlockEntity(returnType);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockStale.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownReturnTypeShippingMethod.name());
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownShippingMethodName.name(), shippingMethodName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownReturnTypeName.name(), returnTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownReturnKindName.name(), returnKindName);
        }
        
        return result;
    }
    
}
