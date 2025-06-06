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

package com.echothree.control.user.security.server.command;

import com.echothree.control.user.security.common.form.CreatePartySecurityRoleTemplateTrainingClassForm;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.security.server.control.SecurityControl;
import com.echothree.model.control.security.server.logic.PartySecurityRoleTemplateLogic;
import com.echothree.model.control.training.server.control.TrainingControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreatePartySecurityRoleTemplateTrainingClassCommand
        extends BaseSimpleCommand<CreatePartySecurityRoleTemplateTrainingClassForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PartySecurityRoleTemplateTrainingClass.name(), SecurityRoles.Create.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartySecurityRoleTemplateName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TrainingClassName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of CreatePartySecurityRoleTemplateTrainingClassCommand */
    public CreatePartySecurityRoleTemplateTrainingClassCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var securityControl = Session.getModelController(SecurityControl.class);
        var partySecurityRoleTemplateName = form.getPartySecurityRoleTemplateName();
        var partySecurityRoleTemplate = securityControl.getPartySecurityRoleTemplateByName(partySecurityRoleTemplateName);
        
        if(partySecurityRoleTemplate != null) {
            var trainingControl = Session.getModelController(TrainingControl.class);
            var trainingClassName = form.getTrainingClassName();
            var trainingClass = trainingControl.getTrainingClassByName(trainingClassName);
            
            if(trainingClass != null) {
                var partySecurityRoleTemplateTrainingClass = securityControl.getPartySecurityRoleTemplateTrainingClass(partySecurityRoleTemplate, trainingClass);

                if(partySecurityRoleTemplateTrainingClass == null) {
                    var preparedPartySecurityRoleTemplateTrainingClass = PartySecurityRoleTemplateLogic.getInstance().preparePartySecurityRoleTemplateTrainingClass(this,
                        partySecurityRoleTemplate, trainingClass);

                    if(!hasExecutionErrors()) {
                        PartySecurityRoleTemplateLogic.getInstance().createPartySecurityRoleTemplateTrainingClass(session, preparedPartySecurityRoleTemplateTrainingClass, getPartyPK());
                    }
                } else {
                    addExecutionError(ExecutionErrors.DuplicatePartySecurityRoleTemplateTrainingClass.name(), partySecurityRoleTemplateName, trainingClassName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownTrainingClassName.name(), trainingClassName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPartySecurityRoleTemplateName.name(), partySecurityRoleTemplateName);
        }
        
        return null;
    }
    
}
