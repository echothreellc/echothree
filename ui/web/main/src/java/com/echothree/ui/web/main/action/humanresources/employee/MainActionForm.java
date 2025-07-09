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

package com.echothree.ui.web.main.action.humanresources.employee;

import com.echothree.control.user.workflow.common.WorkflowUtil;
import com.echothree.control.user.workflow.common.result.GetWorkflowStepChoicesResult;
import com.echothree.model.control.employee.common.workflow.EmployeeAvailabilityConstants;
import com.echothree.model.control.employee.common.workflow.EmployeeStatusConstants;
import com.echothree.model.control.workflow.common.choice.WorkflowStepChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="EmployeeMain")
public class MainActionForm
        extends BaseActionForm {
    
    private WorkflowStepChoicesBean employeeStatusChoices;
    private WorkflowStepChoicesBean employeeAvailabilityChoices;
    
    private String employeeName;
    private String partyName;
    private String firstName;
    private Boolean firstNameSoundex;
    private String middleName;
    private Boolean middleNameSoundex;
    private String lastName;
    private Boolean lastNameSoundex;
    private String employeeStatusChoice;
    private String employeeAvailabilityChoice;
    private String createdSince;
    private String modifiedSince;
    
    private void setupEmployeeStatusChoices()
            throws NamingException {
        if(employeeStatusChoices == null) {
            var form = WorkflowUtil.getHome().getGetWorkflowStepChoicesForm();

            form.setWorkflowName(EmployeeStatusConstants.Workflow_EMPLOYEE_STATUS);
            form.setDefaultWorkflowStepChoice(employeeStatusChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = WorkflowUtil.getHome().getWorkflowStepChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetWorkflowStepChoicesResult)executionResult.getResult();
            employeeStatusChoices = result.getWorkflowStepChoices();

            if(employeeStatusChoice == null) {
                employeeStatusChoice = EmployeeStatusConstants.WorkflowStep_ACTIVE;
            }
        }
    }
    
    private void setupEmployeeAvailabilityChoices()
            throws NamingException {
        if(employeeAvailabilityChoices == null) {
            var form = WorkflowUtil.getHome().getGetWorkflowStepChoicesForm();

            form.setWorkflowName(EmployeeAvailabilityConstants.Workflow_EMPLOYEE_AVAILABILITY);
            form.setDefaultWorkflowStepChoice(employeeAvailabilityChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = WorkflowUtil.getHome().getWorkflowStepChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetWorkflowStepChoicesResult)executionResult.getResult();
            employeeAvailabilityChoices = result.getWorkflowStepChoices();
        }
    }
    
    public String getEmployeeName() {
        return employeeName;
    }
    
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
    
    public String getPartyName() {
        return partyName;
    }
    
    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public Boolean getFirstNameSoundex() {
        return firstNameSoundex;
    }
    
    public void setFirstNameSoundex(Boolean firstNameSoundex) {
        this.firstNameSoundex = firstNameSoundex;
    }
    
    public String getMiddleName() {
        return middleName;
    }
    
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    
    public Boolean getMiddleNameSoundex() {
        return middleNameSoundex;
    }
    
    public void setMiddleNameSoundex(Boolean middleNameSoundex) {
        this.middleNameSoundex = middleNameSoundex;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public Boolean getLastNameSoundex() {
        return lastNameSoundex;
    }
    
    public void setLastNameSoundex(Boolean lastNameSoundex) {
        this.lastNameSoundex = lastNameSoundex;
    }
    
    public String getEmployeeStatusChoice()
            throws NamingException {
        setupEmployeeStatusChoices();
        return employeeStatusChoice;
    }
    
    public void setEmployeeStatusChoice(String employeeStatusChoice) {
        this.employeeStatusChoice = employeeStatusChoice;
    }
    
    public List<LabelValueBean> getEmployeeStatusChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupEmployeeStatusChoices();
        if(employeeStatusChoices != null) {
            choices = convertChoices(employeeStatusChoices);
        }

        return choices;
    }
    
    public String getEmployeeAvailabilityChoice()
            throws NamingException {
        setupEmployeeAvailabilityChoices();
        return employeeAvailabilityChoice;
    }
    
    public void setEmployeeAvailabilityChoice(String employeeAvailabilityChoice) {
        this.employeeAvailabilityChoice = employeeAvailabilityChoice;
    }
    
    public List<LabelValueBean> getEmployeeAvailabilityChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupEmployeeAvailabilityChoices();
        if(employeeAvailabilityChoices != null) {
            choices = convertChoices(employeeAvailabilityChoices);
        }

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
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        
        setFirstNameSoundex(false);
        setMiddleNameSoundex(false);
        setLastNameSoundex(false);
    }
    
}
