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

package com.echothree.model.control.job.server.transfer;

import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.job.common.transfer.JobTransfer;
import com.echothree.model.control.job.server.control.JobControl;
import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.job.common.workflow.JobStatusConstants;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.job.server.entity.Job;
import com.echothree.model.data.job.server.entity.JobDetail;
import com.echothree.model.data.job.server.entity.JobStatus;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class JobTransferCache
        extends BaseJobTransferCache<Job, JobTransfer> {
    
    CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
    PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
    WorkflowControl workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
    
    /** Creates a new instance of JobTransferCache */
    public JobTransferCache(UserVisit userVisit, JobControl jobControl) {
        super(userVisit, jobControl);
        
        setIncludeEntityInstance(true);
    }
    
    public JobTransfer getJobTransfer(Job job) {
        JobTransfer jobTransfer = get(job);
        
        if(jobTransfer == null) {
            JobDetail jobDetail = job.getLastDetail();
            JobStatus jobStatus = jobControl.getJobStatus(job);
            String jobName = jobDetail.getJobName();
            PartyTransfer runAsPartyTransfer = partyControl.getPartyTransfer(userVisit, jobDetail.getRunAsParty());
            Integer sortOrder = jobDetail.getSortOrder();
            String description = jobControl.getBestJobDescription(job, getLanguage());
            Long unformattedLastStartTime = jobStatus.getLastStartTime();
            String lastStartTime = formatTypicalDateTime(unformattedLastStartTime);
            Long unformattedLastEndTime = jobStatus.getLastEndTime();
            String lastEndTime = formatTypicalDateTime(unformattedLastEndTime);

            EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(job.getPrimaryKey());
            WorkflowEntityStatusTransfer jobStatusTransfer = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    JobStatusConstants.Workflow_JOB_STATUS, entityInstance);

            jobTransfer = new JobTransfer(jobName, runAsPartyTransfer, sortOrder, description, jobStatusTransfer, unformattedLastStartTime, lastStartTime,
                    unformattedLastEndTime, lastEndTime);
            put(job, jobTransfer);
        }
        
        return jobTransfer;
    }
    
}
