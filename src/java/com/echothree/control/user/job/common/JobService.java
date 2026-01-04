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

package com.echothree.control.user.job.common;

import com.echothree.control.user.job.common.form.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface JobService
        extends JobForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // --------------------------------------------------------------------------------
    //   Jobs
    // --------------------------------------------------------------------------------
    
    CommandResult createJob(UserVisitPK userVisitPK, CreateJobForm form);
    
    CommandResult getJob(UserVisitPK userVisitPK, GetJobForm form);
    
    CommandResult getJobs(UserVisitPK userVisitPK, GetJobsForm form);
    
    CommandResult getJobStatusChoices(UserVisitPK userVisitPK, GetJobStatusChoicesForm form);
    
    CommandResult setJobStatus(UserVisitPK userVisitPK, SetJobStatusForm form);
    
    CommandResult editJob(UserVisitPK userVisitPK, EditJobForm form);
    
    CommandResult deleteJob(UserVisitPK userVisitPK, DeleteJobForm form);
    
    CommandResult startJob(UserVisitPK userVisitPK, StartJobForm form);
    
    CommandResult endJob(UserVisitPK userVisitPK, EndJobForm form);
    
    // --------------------------------------------------------------------------------
    //   Job Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createJobDescription(UserVisitPK userVisitPK, CreateJobDescriptionForm form);
    
    CommandResult getJobDescriptions(UserVisitPK userVisitPK, GetJobDescriptionsForm form);
    
    CommandResult editJobDescription(UserVisitPK userVisitPK, EditJobDescriptionForm form);
    
    CommandResult deleteJobDescription(UserVisitPK userVisitPK, DeleteJobDescriptionForm form);
    
}
