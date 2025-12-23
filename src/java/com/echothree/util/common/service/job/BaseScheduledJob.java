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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class BaseScheduledJob {

    private Log log = null;
    protected String jobName;
    protected UserVisitPK userVisitPK;

    protected BaseScheduledJob(String jobName) {
        this.jobName = jobName;
    }

    protected Log getLog() {
        if(log == null) {
            log = LogFactory.getLog(this.getClass());
        }

        return log;
    }

    private void startJob(UserVisitPK userVisitPK)
            throws NamingException {
        var commandForm = JobUtil.getHome().getStartJobForm();

        commandForm.setJobName(jobName);

        JobUtil.getHome().startJob(userVisitPK, commandForm);
    }

    private void endJob(UserVisitPK userVisitPK)
            throws NamingException {
        var commandForm = JobUtil.getHome().getEndJobForm();

        commandForm.setJobName(jobName);

        JobUtil.getHome().endJob(userVisitPK, commandForm);
    }

    public void executeJob()
            throws NamingException {
        var commandForm = AuthenticationUtil.getHome().getGetJobUserVisitForm();

        commandForm.setJobName(jobName);

        var commandResult = AuthenticationUtil.getHome().getJobUserVisit(commandForm);

        if(commandResult.hasErrors()) {
            getLog().error(commandResult);
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
