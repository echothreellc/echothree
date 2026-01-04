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
import com.echothree.control.user.employee.common.edit.TerminationReasonDescriptionEdit;
import com.echothree.control.user.employee.common.form.EditTerminationReasonDescriptionForm;
import com.echothree.control.user.employee.common.result.EmployeeResultFactory;
import com.echothree.control.user.employee.common.spec.TerminationReasonDescriptionSpec;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
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
public class EditTerminationReasonDescriptionCommand
        extends BaseEditCommand<TerminationReasonDescriptionSpec, TerminationReasonDescriptionEdit> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.TerminationReason.name(), SecurityRoles.Description.name())
                ))
        ));

        List<FieldDefinition> temp = new ArrayList<>(2);
        temp.add(new FieldDefinition("TerminationReasonName", FieldType.ENTITY_NAME, true, null, null));
        temp.add(new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null));
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
        
        temp = new ArrayList<>(1);
        temp.add(new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L));
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
    }
    
    /** Creates a new instance of EditTerminationReasonDescriptionCommand */
    public EditTerminationReasonDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var employeeControl = Session.getModelController(EmployeeControl.class);
        var result = EmployeeResultFactory.getEditTerminationReasonDescriptionResult();
        var terminationReasonName = spec.getTerminationReasonName();
        var terminationReason = employeeControl.getTerminationReasonByName(terminationReasonName);
        
        if(terminationReason != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);
            
            if(language != null) {
                if(editMode.equals(EditMode.LOCK)) {
                    var terminationReasonDescription = employeeControl.getTerminationReasonDescription(terminationReason, language);
                    
                    if(terminationReasonDescription != null) {
                        result.setTerminationReasonDescription(employeeControl.getTerminationReasonDescriptionTransfer(getUserVisit(), terminationReasonDescription));
                        
                        if(lockEntity(terminationReason)) {
                            var edit = EmployeeEditFactory.getTerminationReasonDescriptionEdit();
                            
                            result.setEdit(edit);
                            edit.setDescription(terminationReasonDescription.getDescription());
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                        }
                        
                        result.setEntityLock(getEntityLockTransfer(terminationReason));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownTerminationReasonDescription.name());
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    var terminationReasonDescriptionValue = employeeControl.getTerminationReasonDescriptionValueForUpdate(terminationReason, language);
                    
                    if(terminationReasonDescriptionValue != null) {
                        if(lockEntityForUpdate(terminationReason)) {
                            try {
                                var description = edit.getDescription();
                                
                                terminationReasonDescriptionValue.setDescription(description);
                                
                                employeeControl.updateTerminationReasonDescriptionFromValue(terminationReasonDescriptionValue, getPartyPK());
                            } finally {
                                unlockEntity(terminationReason);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownTerminationReasonDescription.name());
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownTerminationReasonName.name(), terminationReasonName);
        }
        
        return result;
    }
    
}
