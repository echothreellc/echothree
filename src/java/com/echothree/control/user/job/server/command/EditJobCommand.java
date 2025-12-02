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

import com.echothree.control.user.job.common.edit.JobEdit;
import com.echothree.control.user.job.common.edit.JobEditFactory;
import com.echothree.control.user.job.common.form.EditJobForm;
import com.echothree.control.user.job.common.result.JobResultFactory;
import com.echothree.control.user.job.common.spec.JobSpec;
import com.echothree.model.control.job.server.control.JobControl;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class EditJobCommand
        extends BaseEditCommand<JobSpec, JobEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.Job.name(), SecurityRoles.Edit.name())
                    )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("JobName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("JobName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditJobCommand */
    public EditJobCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var jobControl = Session.getModelController(JobControl.class);
        var result = JobResultFactory.getEditJobResult();
        
        if(editMode.equals(EditMode.LOCK)) {
            var jobName = spec.getJobName();
            var job = jobControl.getJobByName(jobName);
            
            if(job != null) {
                result.setJob(jobControl.getJobTransfer(getUserVisit(), job));
                
                if(lockEntity(job)) {
                    var jobDescription = jobControl.getJobDescription(job, getPreferredLanguage());
                    var edit = JobEditFactory.getJobEdit();
                    var jobDetail = job.getLastDetail();
                    
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
            var jobName = spec.getJobName();
            var job = jobControl.getJobByNameForUpdate(jobName);
            
            if(job != null) {
                jobName = edit.getJobName();
                var duplicateJob = jobControl.getJobByName(jobName);
                
                if(duplicateJob == null || job.equals(duplicateJob)) {
                    if(lockEntityForUpdate(job)) {
                        try {
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
