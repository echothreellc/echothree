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

package com.echothree.control.user.employee.server.command;

import com.echothree.control.user.employee.common.edit.EmployeeEditFactory;
import com.echothree.control.user.employee.common.edit.TerminationReasonEdit;
import com.echothree.control.user.employee.common.form.EditTerminationReasonForm;
import com.echothree.control.user.employee.common.result.EmployeeResultFactory;
import com.echothree.control.user.employee.common.spec.TerminationReasonSpec;
import com.echothree.model.control.employee.server.control.EmployeeControl;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class EditTerminationReasonCommand
        extends BaseEditCommand<TerminationReasonSpec, TerminationReasonEdit> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.TerminationReason.name(), SecurityRoles.Edit.name())
                ))
        ));

        List<FieldDefinition> temp = new ArrayList<>(1);
        temp.add(new FieldDefinition("TerminationReasonName", FieldType.ENTITY_NAME, true, null, null));
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
        
        temp = new ArrayList<>(4);
        temp.add(new FieldDefinition("TerminationReasonName", FieldType.ENTITY_NAME, true, null, null));
        temp.add(new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null));
        temp.add(new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null));
        temp.add(new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L));
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
    }
    
    /** Creates a new instance of EditTerminationReasonCommand */
    public EditTerminationReasonCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var employeeControl = Session.getModelController(EmployeeControl.class);
        var result = EmployeeResultFactory.getEditTerminationReasonResult();
        
        if(editMode.equals(EditMode.LOCK)) {
            var terminationReasonName = spec.getTerminationReasonName();
            var terminationReason = employeeControl.getTerminationReasonByName(terminationReasonName);
            
            if(terminationReason != null) {
                result.setTerminationReason(employeeControl.getTerminationReasonTransfer(getUserVisit(), terminationReason));
                
                if(lockEntity(terminationReason)) {
                    var terminationReasonDescription = employeeControl.getTerminationReasonDescription(terminationReason, getPreferredLanguage());
                    var edit = EmployeeEditFactory.getTerminationReasonEdit();
                    var terminationReasonDetail = terminationReason.getLastDetail();
                    
                    result.setEdit(edit);
                    edit.setTerminationReasonName(terminationReasonDetail.getTerminationReasonName());
                    edit.setIsDefault(terminationReasonDetail.getIsDefault().toString());
                    edit.setSortOrder(terminationReasonDetail.getSortOrder().toString());
                    
                    if(terminationReasonDescription != null)
                        edit.setDescription(terminationReasonDescription.getDescription());
                } else {
                    addExecutionError(ExecutionErrors.EntityLockFailed.name());
                }
                
                result.setEntityLock(getEntityLockTransfer(terminationReason));
            } else {
                addExecutionError(ExecutionErrors.UnknownTerminationReasonName.name(), terminationReasonName);
            }
        } else if(editMode.equals(EditMode.UPDATE)) {
            var terminationReasonName = spec.getTerminationReasonName();
            var terminationReason = employeeControl.getTerminationReasonByNameForUpdate(terminationReasonName);
            
            if(terminationReason != null) {
                if(lockEntityForUpdate(terminationReason)) {
                    try {
                        var partyPK = getPartyPK();
                        var terminationReasonDetailValue = employeeControl.getTerminationReasonDetailValueForUpdate(terminationReason);
                        var terminationReasonDescription = employeeControl.getTerminationReasonDescriptionForUpdate(terminationReason, getPreferredLanguage());
                        var description = edit.getDescription();
                        
                        terminationReasonDetailValue.setTerminationReasonName(edit.getTerminationReasonName());
                        terminationReasonDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                        terminationReasonDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                        
                        employeeControl.updateTerminationReasonFromValue(terminationReasonDetailValue, partyPK);
                        
                        if(terminationReasonDescription == null && description != null) {
                            employeeControl.createTerminationReasonDescription(terminationReason, getPreferredLanguage(), description, partyPK);
                        } else if(terminationReasonDescription != null && description == null) {
                            employeeControl.deleteTerminationReasonDescription(terminationReasonDescription, partyPK);
                        } else if(terminationReasonDescription != null && description != null) {
                            var terminationReasonDescriptionValue = employeeControl.getTerminationReasonDescriptionValue(terminationReasonDescription);
                            
                            terminationReasonDescriptionValue.setDescription(description);
                            employeeControl.updateTerminationReasonDescriptionFromValue(terminationReasonDescriptionValue, partyPK);
                        }
                    } finally {
                        unlockEntity(terminationReason);
                    }
                } else {
                    addExecutionError(ExecutionErrors.EntityLockStale.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownTerminationReasonName.name(), terminationReasonName);
            }
        }
        
        return result;
    }
    
}
