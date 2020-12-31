// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.control.user.job.server;

import com.echothree.control.user.job.common.JobRemote;
import com.echothree.control.user.job.common.form.*;
import com.echothree.control.user.job.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

@Stateless
public class JobBean
        extends JobFormsImpl
        implements JobRemote, JobLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "JobBean is alive!";
    }
    
    // --------------------------------------------------------------------------------
    //   Jobs
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createJob(UserVisitPK userVisitPK, CreateJobForm form) {
        return new CreateJobCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getJob(UserVisitPK userVisitPK, GetJobForm form) {
        return new GetJobCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getJobs(UserVisitPK userVisitPK, GetJobsForm form) {
        return new GetJobsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getJobStatusChoices(UserVisitPK userVisitPK, GetJobStatusChoicesForm form) {
        return new GetJobStatusChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setJobStatus(UserVisitPK userVisitPK, SetJobStatusForm form) {
        return new SetJobStatusCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editJob(UserVisitPK userVisitPK, EditJobForm form) {
        return new EditJobCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteJob(UserVisitPK userVisitPK, DeleteJobForm form) {
        return new DeleteJobCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult startJob(UserVisitPK userVisitPK, StartJobForm form) {
        return new StartJobCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult endJob(UserVisitPK userVisitPK, EndJobForm form) {
        return new EndJobCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Job Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createJobDescription(UserVisitPK userVisitPK, CreateJobDescriptionForm form) {
        return new CreateJobDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getJobDescriptions(UserVisitPK userVisitPK, GetJobDescriptionsForm form) {
        return new GetJobDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editJobDescription(UserVisitPK userVisitPK, EditJobDescriptionForm form) {
        return new EditJobDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteJobDescription(UserVisitPK userVisitPK, DeleteJobDescriptionForm form) {
        return new DeleteJobDescriptionCommand(userVisitPK, form).run();
    }
    
}
