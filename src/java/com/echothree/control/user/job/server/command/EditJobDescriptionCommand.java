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

import com.echothree.control.user.job.common.edit.JobDescriptionEdit;
import com.echothree.control.user.job.common.edit.JobEditFactory;
import com.echothree.control.user.job.common.result.EditJobDescriptionResult;
import com.echothree.control.user.job.common.result.JobResultFactory;
import com.echothree.control.user.job.common.spec.JobDescriptionSpec;
import com.echothree.model.control.job.server.control.JobControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.job.server.entity.Job;
import com.echothree.model.data.job.server.entity.JobDescription;
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
public class EditJobDescriptionCommand
        extends BaseAbstractEditCommand<JobDescriptionSpec, JobDescriptionEdit, EditJobDescriptionResult, JobDescription, Job> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.Job.name(), SecurityRoles.Description.name())
                ))
        ));
        
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("JobName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }
    
    /** Creates a new instance of EditJobDescriptionCommand */
    public EditJobDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Inject
    JobControl jobControl;

    @Inject
    PartyControl partyControl;

    @Override
    protected EditJobDescriptionResult getResult() {
        return JobResultFactory.getEditJobDescriptionResult();
    }

    @Override
    protected JobDescriptionEdit getEdit() {
        return JobEditFactory.getJobDescriptionEdit();
    }

    @Override
    protected JobDescription getEntity(EditJobDescriptionResult result) {
        JobDescription jobDescription = null;
        var jobName = spec.getJobName();
        var job = jobControl.getJobByName(jobName);

        if(job != null) {
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    jobDescription = jobControl.getJobDescription(job, language);
                } else { // EditMode.UPDATE
                    jobDescription = jobControl.getJobDescriptionForUpdate(job, language);
                }

                if(jobDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownJobDescription.name(), jobName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownJobName.name(), jobName);
        }

        return jobDescription;
    }

    @Override
    protected Job getLockEntity(JobDescription jobDescription) {
        return jobDescription.getJob();
    }

    @Override
    protected void fillInResult(EditJobDescriptionResult result, JobDescription jobDescription) {
        result.setJobDescription(jobControl.getJobDescriptionTransfer(getUserVisit(), jobDescription));
    }

    @Override
    protected void doLock(JobDescriptionEdit edit, JobDescription jobDescription) {
        edit.setDescription(jobDescription.getDescription());
    }

    @Override
    protected void doUpdate(JobDescription jobDescription) {
        var jobDescriptionValue = jobControl.getJobDescriptionValue(jobDescription);

        jobDescriptionValue.setDescription(edit.getDescription());

        jobControl.updateJobDescriptionFromValue(jobDescriptionValue, getPartyPK());
    }
    
}
