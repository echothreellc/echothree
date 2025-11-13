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

package com.echothree.model.control.job.server.transfer;

import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.job.common.transfer.JobTransfer;
import com.echothree.model.control.job.common.workflow.JobStatusConstants;
import com.echothree.model.control.job.server.control.JobControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.job.server.entity.Job;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class JobTransferCache
        extends BaseJobTransferCache<Job, JobTransfer> {
    
    EntityInstanceControl entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
    PartyControl partyControl = Session.getModelController(PartyControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);
    
    /** Creates a new instance of JobTransferCache */
    public JobTransferCache(JobControl jobControl) {
        super(jobControl);
        
        setIncludeEntityInstance(true);
    }
    
    public JobTransfer getJobTransfer(UserVisit userVisit, Job job) {
        var jobTransfer = get(job);
        
        if(jobTransfer == null) {
            var jobDetail = job.getLastDetail();
            var jobStatus = jobControl.getJobStatus(job);
            var jobName = jobDetail.getJobName();
            var runAsPartyTransfer = partyControl.getPartyTransfer(userVisit, jobDetail.getRunAsParty());
            var sortOrder = jobDetail.getSortOrder();
            var description = jobControl.getBestJobDescription(job, getLanguage(userVisit));
            var unformattedLastStartTime = jobStatus.getLastStartTime();
            var lastStartTime = formatTypicalDateTime(userVisit, unformattedLastStartTime);
            var unformattedLastEndTime = jobStatus.getLastEndTime();
            var lastEndTime = formatTypicalDateTime(userVisit, unformattedLastEndTime);

            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(job.getPrimaryKey());
            var jobStatusTransfer = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    JobStatusConstants.Workflow_JOB_STATUS, entityInstance);

            jobTransfer = new JobTransfer(jobName, runAsPartyTransfer, sortOrder, description, jobStatusTransfer, unformattedLastStartTime, lastStartTime,
                    unformattedLastEndTime, lastEndTime);
            put(userVisit, job, jobTransfer);
        }
        
        return jobTransfer;
    }
    
}
