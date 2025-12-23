// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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
import com.echothree.control.user.returnpolicy.common.edit.ReturnReasonEdit;
import com.echothree.control.user.returnpolicy.common.form.EditReturnReasonForm;
import com.echothree.control.user.returnpolicy.common.result.ReturnPolicyResultFactory;
import com.echothree.control.user.returnpolicy.common.spec.ReturnReasonSpec;
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
import javax.enterprise.context.Dependent;

@Dependent
public class EditReturnReasonCommand
        extends BaseEditCommand<ReturnReasonSpec, ReturnReasonEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ReturnReason.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ReturnKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ReturnReasonName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ReturnReasonName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditReturnReasonCommand */
    public EditReturnReasonCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var returnPolicyControl = Session.getModelController(ReturnPolicyControl.class);
        var result = ReturnPolicyResultFactory.getEditReturnReasonResult();
        var returnKindName = spec.getReturnKindName();
        var returnKind = returnPolicyControl.getReturnKindByName(returnKindName);
        
        if(returnKind != null) {
            if(editMode.equals(EditMode.LOCK)) {
                var returnReasonName = spec.getReturnReasonName();
                var returnReason = returnPolicyControl.getReturnReasonByName(returnKind, returnReasonName);
                
                if(returnReason != null) {
                    result.setReturnReason(returnPolicyControl.getReturnReasonTransfer(getUserVisit(), returnReason));
                    
                    if(lockEntity(returnReason)) {
                        var returnReasonDescription = returnPolicyControl.getReturnReasonDescription(returnReason, getPreferredLanguage());
                        var edit = ReturnPolicyEditFactory.getReturnReasonEdit();
                        var returnReasonDetail = returnReason.getLastDetail();
                        
                        result.setEdit(edit);
                        edit.setReturnReasonName(returnReasonDetail.getReturnReasonName());
                        edit.setIsDefault(returnReasonDetail.getIsDefault().toString());
                        edit.setSortOrder(returnReasonDetail.getSortOrder().toString());
                        
                        if(returnReasonDescription != null)
                            edit.setDescription(returnReasonDescription.getDescription());
                    } else {
                        addExecutionError(ExecutionErrors.EntityLockFailed.name());
                    }
                    
                    result.setEntityLock(getEntityLockTransfer(returnReason));
                } else {
                    addExecutionError(ExecutionErrors.UnknownReturnReasonName.name(), returnReasonName);
                }
            } else if(editMode.equals(EditMode.UPDATE)) {
                var returnReasonName = spec.getReturnReasonName();
                var returnReason = returnPolicyControl.getReturnReasonByNameForUpdate(returnKind, returnReasonName);
                
                if(returnReason != null) {
                    returnReasonName = edit.getReturnReasonName();
                    var duplicateReturnReason = returnPolicyControl.getReturnReasonByName(returnKind, returnReasonName);
                    
                    if(duplicateReturnReason == null || returnReason.equals(duplicateReturnReason)) {
                        if(lockEntityForUpdate(returnReason)) {
                            try {
                                var partyPK = getPartyPK();
                                var returnReasonDetailValue = returnPolicyControl.getReturnReasonDetailValueForUpdate(returnReason);
                                var returnReasonDescription = returnPolicyControl.getReturnReasonDescriptionForUpdate(returnReason, getPreferredLanguage());
                                var description = edit.getDescription();
                                
                                returnReasonDetailValue.setReturnReasonName(edit.getReturnReasonName());
                                returnReasonDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                                returnReasonDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                                
                                returnPolicyControl.updateReturnReasonFromValue(returnReasonDetailValue, partyPK);
                                
                                if(returnReasonDescription == null && description != null) {
                                    returnPolicyControl.createReturnReasonDescription(returnReason, getPreferredLanguage(), description, partyPK);
                                } else if(returnReasonDescription != null && description == null) {
                                    returnPolicyControl.deleteReturnReasonDescription(returnReasonDescription, partyPK);
                                } else if(returnReasonDescription != null && description != null) {
                                    var returnReasonDescriptionValue = returnPolicyControl.getReturnReasonDescriptionValue(returnReasonDescription);
                                    
                                    returnReasonDescriptionValue.setDescription(description);
                                    returnPolicyControl.updateReturnReasonDescriptionFromValue(returnReasonDescriptionValue, partyPK);
                                }
                            } finally {
                                unlockEntity(returnReason);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.DuplicateReturnReasonName.name(), returnReasonName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownReturnReasonName.name(), returnReasonName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownReturnKindName.name(), returnKindName);
        }
        
        return result;
    }
    
}
