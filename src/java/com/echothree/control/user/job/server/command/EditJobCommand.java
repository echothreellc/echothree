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

package com.echothree.control.user.job.server.command;

import com.echothree.control.user.job.remote.edit.JobEdit;
import com.echothree.control.user.job.remote.edit.JobEditFactory;
import com.echothree.control.user.job.remote.form.EditJobForm;
import com.echothree.control.user.job.remote.result.EditJobResult;
import com.echothree.control.user.job.remote.result.JobResultFactory;
import com.echothree.control.user.job.remote.spec.JobSpec;
import com.echothree.model.control.job.server.JobControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.job.server.entity.Job;
import com.echothree.model.data.job.server.entity.JobDescription;
import com.echothree.model.data.job.server.entity.JobDetail;
import com.echothree.model.data.job.server.value.JobDescriptionValue;
import com.echothree.model.data.job.server.value.JobDetailValue;
import com.echothree.model.data.party.remote.pk.PartyPK;
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

public class EditJobCommand
        extends BaseEditCommand<JobSpec, JobEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.Job.name(), SecurityRoles.Edit.name())
                    )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("JobName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("JobName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditJobCommand */
    public EditJobCommand(UserVisitPK userVisitPK, EditJobForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        JobControl jobControl = (JobControl)Session.getModelController(JobControl.class);
        EditJobResult result = JobResultFactory.getEditJobResult();
        
        if(editMode.equals(EditMode.LOCK)) {
            String jobName = spec.getJobName();
            Job job = jobControl.getJobByName(jobName);
            
            if(job != null) {
                result.setJob(jobControl.getJobTransfer(getUserVisit(), job));
                
                if(lockEntity(job)) {
                    JobDescription jobDescription = jobControl.getJobDescription(job, getPreferredLanguage());
                    JobEdit edit = JobEditFactory.getJobEdit();
                    JobDetail jobDetail = job.getLastDetail();
                    
                    result.setEdit(edit);
                    edit.setJobName(jobDetail.getJobName());
                    edit.setSortOrder(jobDetail.getSortOrder().toString());
                    
                    if(jobDescription != null)
                        edit.setDescription(jobDescription.getDescription());
                } else {
                    addExecutionError(ExecutionErrors.EntityLockFailed.name());
                }
                
                result.setEntityLock(getEntityLockTransfer(job));
            } else {
                addExecutionError(ExecutionErrors.UnknownJobName.name(), jobName);
            }
        } else if(editMode.equals(EditMode.UPDATE)) {
            String jobName = spec.getJobName();
            Job job = jobControl.getJobByNameForUpdate(jobName);
            
            if(job != null) {
                jobName = edit.getJobName();
                Job duplicateJob = jobControl.getJobByName(jobName);
                
                if(duplicateJob == null || job.equals(duplicateJob)) {
                    if(lockEntityForUpdate(job)) {
                        try {
                            PartyPK partyPK = getPartyPK();
                            JobDetailValue jobDetailValue = jobControl.getJobDetailValueForUpdate(job);
                            JobDescription jobDescription = jobControl.getJobDescriptionForUpdate(job, getPreferredLanguage());
                            String description = edit.getDescription();
                            
                            jobDetailValue.setJobName(edit.getJobName());
                            jobDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                            
                            jobControl.updateJobFromValue(jobDetailValue, partyPK);
                            
                            if(jobDescription == null && description != null) {
                                jobControl.createJobDescription(job, getPreferredLanguage(), description, partyPK);
                            } else if(jobDescription != null && description == null) {
                                jobControl.deleteJobDescription(jobDescription, partyPK);
                            } else if(jobDescription != null && description != null) {
                                JobDescriptionValue jobDescriptionValue = jobControl.getJobDescriptionValue(jobDescription);
                                
                                jobDescriptionValue.setDescription(description);
                                jobControl.updateJobDescriptionFromValue(jobDescriptionValue, partyPK);
                            }
                        } finally {
                            unlockEntity(job);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.EntityLockStale.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.DuplicateJobName.name(), jobName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownJobName.name(), jobName);
            }
            
            if(hasExecutionErrors()) {
                result.setJob(jobControl.getJobTransfer(getUserVisit(), job));
                result.setEntityLock(getEntityLockTransfer(job));
            }
        }
        
        return result;
    }
    
}
