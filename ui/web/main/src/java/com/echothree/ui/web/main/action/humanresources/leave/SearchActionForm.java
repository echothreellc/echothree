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

package com.echothree.ui.web.main.action.humanresources.leave;

import com.echothree.control.user.employee.common.EmployeeUtil;
import com.echothree.control.user.employee.common.result.GetLeaveReasonChoicesResult;
import com.echothree.control.user.employee.common.result.GetLeaveTypeChoicesResult;
import com.echothree.control.user.workflow.common.WorkflowUtil;
import com.echothree.control.user.workflow.common.result.GetWorkflowStepChoicesResult;
import com.echothree.model.control.employee.common.choice.LeaveReasonChoicesBean;
import com.echothree.model.control.employee.common.choice.LeaveTypeChoicesBean;
import com.echothree.model.control.employee.common.workflow.LeaveStatusConstants;
import com.echothree.model.control.workflow.common.choice.WorkflowStepChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="LeaveSearch")
public class SearchActionForm
        extends BaseActionForm {
    
    private LeaveTypeChoicesBean leaveTypeChoices;
    private LeaveReasonChoicesBean leaveReasonChoices;
    private WorkflowStepChoicesBean leaveStatusChoices;
    
    private String leaveName;
    private String leaveTypeChoice;
    private String leaveReasonChoice;
    private String leaveStatusChoice;
    private String createdSince;
    private String modifiedSince;
    
    private void setupLeaveTypeChoices()
            throws NamingException {
        if(leaveTypeChoices == null) {
            var form = EmployeeUtil.getHome().getGetLeaveTypeChoicesForm();

            form.setDefaultLeaveTypeChoice(leaveTypeChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = EmployeeUtil.getHome().getLeaveTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetLeaveTypeChoicesResult)executionResult.getResult();
            leaveTypeChoices = result.getLeaveTypeChoices();

            if(leaveTypeChoice == null) {
                leaveTypeChoice = leaveTypeChoices.getDefaultValue();
            }
        }
    }
    
    private void setupLeaveReasonChoices()
            throws NamingException {
        if(leaveReasonChoices == null) {
            var form = EmployeeUtil.getHome().getGetLeaveReasonChoicesForm();

            form.setDefaultLeaveReasonChoice(leaveReasonChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = EmployeeUtil.getHome().getLeaveReasonChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetLeaveReasonChoicesResult)executionResult.getResult();
            leaveReasonChoices = result.getLeaveReasonChoices();

            if(leaveReasonChoice == null) {
                leaveReasonChoice = leaveReasonChoices.getDefaultValue();
            }
        }
    }
    
    private void setupLeaveStatusChoices()
            throws NamingException {
        if(leaveStatusChoices == null) {
            var form = WorkflowUtil.getHome().getGetWorkflowStepChoicesForm();

            form.setWorkflowName(LeaveStatusConstants.Workflow_LEAVE_STATUS);
            form.setDefaultWorkflowStepChoice(leaveStatusChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = WorkflowUtil.getHome().getWorkflowStepChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetWorkflowStepChoicesResult)executionResult.getResult();
            leaveStatusChoices = result.getWorkflowStepChoices();

            if(leaveStatusChoice == null) {
                leaveStatusChoice = leaveStatusChoices.getDefaultValue();
            }
        }
    }
    
    public String getLeaveName() {
        return leaveName;
    }
    
    public void setLeaveName(String leaveName) {
        this.leaveName = leaveName;
    }
    
    public String getLeaveTypeChoice()
            throws NamingException {
        setupLeaveTypeChoices();
        return leaveTypeChoice;
    }
    
    public void setLeaveTypeChoice(String leaveTypeChoice) {
        this.leaveTypeChoice = leaveTypeChoice;
    }
    
    public List<LabelValueBean> getLeaveTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupLeaveTypeChoices();
        if(leaveTypeChoices != null)
            choices = convertChoices(leaveTypeChoices);
        
        return choices;
    }
    
    public String getLeaveReasonChoice()
            throws NamingException {
        setupLeaveReasonChoices();
        return leaveReasonChoice;
    }
    
    public void setLeaveReasonChoice(String leaveReasonChoice) {
        this.leaveReasonChoice = leaveReasonChoice;
    }
    
    public List<LabelValueBean> getLeaveReasonChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupLeaveReasonChoices();
        if(leaveReasonChoices != null)
            choices = convertChoices(leaveReasonChoices);
        
        return choices;
    }
    
    public String getLeaveStatusChoice()
            throws NamingException {
        setupLeaveStatusChoices();
        return leaveStatusChoice;
    }
    
    public void setLeaveStatusChoice(String leaveStatusChoice) {
        this.leaveStatusChoice = leaveStatusChoice;
    }
    
    public List<LabelValueBean> getLeaveStatusChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupLeaveStatusChoices();
        if(leaveStatusChoices != null)
            choices = convertChoices(leaveStatusChoices);
        
        return choices;
    }

    public String getCreatedSince() {
        return createdSince;
    }

    public void setCreatedSince(String createdSince) {
        this.createdSince = createdSince;
    }

    public String getModifiedSince() {
        return modifiedSince;
    }

    public void setModifiedSince(String modifiedSince) {
        this.modifiedSince = modifiedSince;
    }
    
}
