// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

package com.echothree.ui.web.main.action.configuration.job;

import com.echothree.control.user.job.common.JobUtil;
import com.echothree.control.user.job.common.form.GetJobStatusChoicesForm;
import com.echothree.control.user.job.common.result.GetJobStatusChoicesResult;
import com.echothree.model.control.job.common.choice.JobStatusChoicesBean;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="JobStatus")
public class StatusActionForm
        extends BaseActionForm {
    
    private JobStatusChoicesBean jobStatusChoices;
    
    private String jobName;
    private String jobStatusChoice;
    
    public void setupJobStatusChoices() {
        if(jobStatusChoices == null) {
            try {
                GetJobStatusChoicesForm form = JobUtil.getHome().getGetJobStatusChoicesForm();
                
                form.setJobName(jobName);
                form.setDefaultJobStatusChoice(jobStatusChoice);
                form.setAllowNullChoice(Boolean.FALSE.toString());
                
                CommandResult commandResult = JobUtil.getHome().getJobStatusChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetJobStatusChoicesResult getJobStatusChoicesResult = (GetJobStatusChoicesResult)executionResult.getResult();
                jobStatusChoices = getJobStatusChoicesResult.getJobStatusChoices();
                
                if(jobStatusChoice == null)
                    jobStatusChoice = jobStatusChoices.getDefaultValue();
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, jobStatusChoices remains null, no default
            }
        }
    }
    
    public String getJobName() {
        return jobName;
    }
    
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
    
    public String getJobStatusChoice() {
        return jobStatusChoice;
    }
    
    public void setJobStatusChoice(String jobStatusChoice) {
        this.jobStatusChoice = jobStatusChoice;
    }
    
    public List<LabelValueBean> getJobStatusChoices() {
        List<LabelValueBean> choices = null;
        
        setupJobStatusChoices();
        if(jobStatusChoices != null)
            choices = convertChoices(jobStatusChoices);
        
        return choices;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
    }
    
}
