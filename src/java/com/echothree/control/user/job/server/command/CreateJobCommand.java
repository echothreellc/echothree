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

package com.echothree.control.user.job.server.command;

import com.echothree.control.user.job.common.form.CreateJobForm;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.job.common.workflow.JobStatusConstants;
import com.echothree.model.control.job.server.control.JobControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class CreateJobCommand
        extends BaseSimpleCommand<CreateJobForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.Job.name(), SecurityRoles.Create.name())
                    )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("JobName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of CreateJobCommand */
    public CreateJobCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var jobControl = Session.getModelController(JobControl.class);
        var jobName = form.getJobName();
        var job = jobControl.getJobByName(jobName);
        
        if(job == null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var runAsParty = partyControl.getPartyByName(jobName);
            
            if(runAsParty == null) {
                var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
                var workflowControl = Session.getModelController(WorkflowControl.class);
                var partyType = partyControl.getPartyTypeByName(PartyTypes.UTILITY.name());
                var sortOrder = Integer.valueOf(form.getSortOrder());
                var description = form.getDescription();
                var createdBy = getPartyPK();
                
                runAsParty = partyControl.createParty(jobName, partyType, null, null, null, null, null);
                job = jobControl.createJob(jobName, runAsParty, sortOrder, createdBy);
                
                if(description != null) {
                    jobControl.createJobDescription(job, getPreferredLanguage(), description, createdBy);
                }

                var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(job.getPrimaryKey());
                workflowControl.addEntityToWorkflowUsingNames(null, JobStatusConstants.Workflow_JOB_STATUS, JobStatusConstants.WorkflowEntrance_NEW_ENABLED, entityInstance, null, null, createdBy);
            } else {
                addExecutionError(ExecutionErrors.DuplicatePartyName.name(), jobName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateJobName.name(), jobName);
        }
        
        return null;
    }
    
}
