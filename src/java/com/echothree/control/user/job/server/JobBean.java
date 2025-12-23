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

package com.echothree.control.user.job.server;

import com.echothree.control.user.job.common.JobRemote;
import com.echothree.control.user.job.common.form.*;
import com.echothree.control.user.job.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;

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
        return CDI.current().select(CreateJobCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getJob(UserVisitPK userVisitPK, GetJobForm form) {
        return CDI.current().select(GetJobCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getJobs(UserVisitPK userVisitPK, GetJobsForm form) {
        return CDI.current().select(GetJobsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getJobStatusChoices(UserVisitPK userVisitPK, GetJobStatusChoicesForm form) {
        return CDI.current().select(GetJobStatusChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setJobStatus(UserVisitPK userVisitPK, SetJobStatusForm form) {
        return CDI.current().select(SetJobStatusCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editJob(UserVisitPK userVisitPK, EditJobForm form) {
        return CDI.current().select(EditJobCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteJob(UserVisitPK userVisitPK, DeleteJobForm form) {
        return CDI.current().select(DeleteJobCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult startJob(UserVisitPK userVisitPK, StartJobForm form) {
        return CDI.current().select(StartJobCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult endJob(UserVisitPK userVisitPK, EndJobForm form) {
        return CDI.current().select(EndJobCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Job Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createJobDescription(UserVisitPK userVisitPK, CreateJobDescriptionForm form) {
        return CDI.current().select(CreateJobDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getJobDescriptions(UserVisitPK userVisitPK, GetJobDescriptionsForm form) {
        return CDI.current().select(GetJobDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editJobDescription(UserVisitPK userVisitPK, EditJobDescriptionForm form) {
        return CDI.current().select(EditJobDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteJobDescription(UserVisitPK userVisitPK, DeleteJobDescriptionForm form) {
        return CDI.current().select(DeleteJobDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
}
