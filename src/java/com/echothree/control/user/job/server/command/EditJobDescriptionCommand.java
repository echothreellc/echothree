// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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
import com.echothree.control.user.job.common.form.EditJobDescriptionForm;
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
import com.echothree.model.data.job.server.value.JobDescriptionValue;
import com.echothree.model.data.party.server.entity.Language;
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

public class EditJobDescriptionCommand
        extends BaseEditCommand<JobDescriptionSpec, JobDescriptionEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Job.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("JobName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditJobDescriptionCommand */
    public EditJobDescriptionCommand(UserVisitPK userVisitPK, EditJobDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var jobControl = (JobControl)Session.getModelController(JobControl.class);
        EditJobDescriptionResult result = JobResultFactory.getEditJobDescriptionResult();
        String jobName = spec.getJobName();
        Job job = jobControl.getJobByName(jobName);
        
        if(job != null) {
            var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String languageIsoName = spec.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);
            
            if(language != null) {
                if(editMode.equals(EditMode.LOCK)) {
                    JobDescription jobDescription = jobControl.getJobDescription(job, language);
                    
                    if(jobDescription != null) {
                        result.setJobDescription(jobControl.getJobDescriptionTransfer(getUserVisit(), jobDescription));
                        
                        if(lockEntity(job)) {
                            JobDescriptionEdit edit = JobEditFactory.getJobDescriptionEdit();
                            
                            result.setEdit(edit);
                            edit.setDescription(jobDescription.getDescription());
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                        }
                        
                        result.setEntityLock(getEntityLockTransfer(job));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownJobDescription.name());
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    JobDescriptionValue jobDescriptionValue = jobControl.getJobDescriptionValueForUpdate(job, language);
                    
                    if(jobDescriptionValue != null) {
                        if(lockEntityForUpdate(job)) {
                            try {
                                String description = edit.getDescription();
                                
                                jobDescriptionValue.setDescription(description);
                                
                                jobControl.updateJobDescriptionFromValue(jobDescriptionValue, getPartyPK());
                            } finally {
                                unlockEntity(job);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownJobDescription.name());
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownJobName.name(), jobName);
        }
        
        return result;
    }
    
}
