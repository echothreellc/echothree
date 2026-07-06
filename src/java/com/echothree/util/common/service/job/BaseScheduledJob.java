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

package com.echothree.util.common.service.job;

import com.echothree.control.user.authentication.common.AuthenticationUtil;
import com.echothree.control.user.authentication.common.result.GetJobUserVisitResult;
import com.echothree.control.user.job.common.JobUtil;
import com.echothree.model.control.job.common.workflow.JobStatusConstants;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import javax.naming.NamingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseScheduledJob {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    protected String jobName;

    protected UserVisitPK userVisitPK;
    protected long startTime;

    protected BaseScheduledJob(String jobName) {
        this.jobName = jobName;
    }

    private void startJob(UserVisitPK userVisitPK)
            throws NamingException {
        var commandForm = JobUtil.getHome().getStartJobForm();

        commandForm.setJobName(jobName);

        log.debug("Starting job: {}", jobName);
        startTime = System.currentTimeMillis();
        JobUtil.getHome().startJob(userVisitPK, commandForm);
    }

    private void endJob(UserVisitPK userVisitPK)
            throws NamingException {
        var commandForm = JobUtil.getHome().getEndJobForm();

        commandForm.setJobName(jobName);

        log.debug("Ending job: {}", jobName);
        JobUtil.getHome().endJob(userVisitPK, commandForm);
        log.info("Completed job: {}, elapsed: {}ms", jobName, System.currentTimeMillis() - startTime);
    }

    public void executeJob()
            throws NamingException {
        var commandForm = AuthenticationUtil.getHome().getGetJobUserVisitForm();

        commandForm.setJobName(jobName);

        var commandResult = AuthenticationUtil.getHome().getJobUserVisit(commandForm);

        if(commandResult.hasErrors()) {
            log.error(commandResult.toString());
        } else {
            var executionResult = commandResult.getExecutionResult();
            var getJobUserVisitResult = (GetJobUserVisitResult)executionResult.getResult();

            userVisitPK = getJobUserVisitResult.getUserVisitPK();

            if(userVisitPK != null) {
                if(getJobUserVisitResult.getJob().getJobStatus().getWorkflowStep().getWorkflowStepName().equals(JobStatusConstants.WorkflowStep_ENABLED)) {
                    startJob(userVisitPK);
                    execute();
                    endJob(userVisitPK);
                }

                AuthenticationUtil.getHome().invalidateUserVisit(userVisitPK);
            }
        }
    }

    public abstract void execute()
            throws NamingException;

}
