// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

import com.echothree.control.user.job.common.form.StartJobForm;
import com.echothree.model.control.job.server.JobControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.data.job.server.entity.Job;
import com.echothree.model.data.job.server.entity.JobStatus;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StartJobCommand
        extends BaseSimpleCommand<StartJobForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null)
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("JobName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of StartJobCommand */
    public StartJobCommand(UserVisitPK userVisitPK, StartJobForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        JobControl jobControl = (JobControl)Session.getModelController(JobControl.class);
        String jobName = form.getJobName();
        Job job = jobControl.getJobByName(jobName);
        
        if(job != null) {
            JobStatus jobStatus = jobControl.getJobStatusForUpdate(job);
            
            jobStatus.setLastStartTime(session.START_TIME);
            jobStatus.setLastEndTime(null);
        } else {
            addExecutionError(ExecutionErrors.UnknownJobName.name(), jobName);
        }
        
        return null;
    }
    
}
