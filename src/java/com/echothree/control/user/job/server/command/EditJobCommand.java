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

package com.echothree.control.user.job.server.command;

import com.echothree.control.user.job.common.edit.JobEdit;
import com.echothree.control.user.job.common.edit.JobEditFactory;
import com.echothree.control.user.job.common.result.EditJobResult;
import com.echothree.control.user.job.common.result.JobResultFactory;
import com.echothree.control.user.job.common.spec.JobSpec;
import com.echothree.model.control.job.server.control.JobControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.job.server.entity.Job;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditJobCommand
        extends BaseAbstractEditCommand<JobSpec, JobEdit, EditJobResult, Job, Job> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.Job.name(), SecurityRoles.Edit.name())
                ))
        ));
        
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("JobName", FieldType.ENTITY_NAME, true, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("JobName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }
    
    /** Creates a new instance of EditJobCommand */
    public EditJobCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Inject
    JobControl jobControl;

    @Override
    protected EditJobResult getResult() {
        return JobResultFactory.getEditJobResult();
    }

    @Override
    protected JobEdit getEdit() {
        return JobEditFactory.getJobEdit();
    }

    @Override
    protected Job getEntity(EditJobResult result) {
        Job job;
        var jobName = spec.getJobName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            job = jobControl.getJobByName(jobName);
        } else { // EditMode.UPDATE
            job = jobControl.getJobByNameForUpdate(jobName);
        }

        if(job == null) {
            addExecutionError(ExecutionErrors.UnknownJobName.name(), jobName);
        }

        return job;
    }

    @Override
    protected Job getLockEntity(Job job) {
        return job;
    }

    @Override
    protected void fillInResult(EditJobResult result, Job job) {
        result.setJob(jobControl.getJobTransfer(getUserVisit(), job));
    }

    @Override
    protected void doLock(JobEdit edit, Job job) {
        var jobDescription = jobControl.getJobDescription(job, getPreferredLanguage());
        var jobDetail = job.getLastDetail();

        edit.setJobName(jobDetail.getJobName());
        edit.setSortOrder(jobDetail.getSortOrder().toString());

        if(jobDescription != null) {
            edit.setDescription(jobDescription.getDescription());
        }
    }

    @Override
    protected void canUpdate(Job job) {
        var jobName = edit.getJobName();
        var duplicateJob = jobControl.getJobByName(jobName);

        if(duplicateJob != null && !job.equals(duplicateJob)) {
            addExecutionError(ExecutionErrors.DuplicateJobName.name(), jobName);
        }
    }

    @Override
    protected void doUpdate(Job job) {
        var partyPK = getPartyPK();
        var jobDetailValue = jobControl.getJobDetailValueForUpdate(job);
        var jobDescription = jobControl.getJobDescriptionForUpdate(job, getPreferredLanguage());
        var description = edit.getDescription();

        jobDetailValue.setJobName(edit.getJobName());
        jobDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        jobControl.updateJobFromValue(jobDetailValue, partyPK);

        if(jobDescription == null && description != null) {
            jobControl.createJobDescription(job, getPreferredLanguage(), description, partyPK);
        } else if(jobDescription != null && description == null) {
            jobControl.deleteJobDescription(jobDescription, partyPK);
        } else if(jobDescription != null && description != null) {
            var jobDescriptionValue = jobControl.getJobDescriptionValue(jobDescription);

            jobDescriptionValue.setDescription(description);
            jobControl.updateJobDescriptionFromValue(jobDescriptionValue, partyPK);
        }
    }
    
}
