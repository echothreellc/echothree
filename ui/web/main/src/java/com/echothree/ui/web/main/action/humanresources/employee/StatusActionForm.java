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

package com.echothree.ui.web.main.action.humanresources.employee;

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.result.GetEmployeeStatusChoicesResult;
import com.echothree.model.control.employee.common.choice.EmployeeStatusChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="HREmployeeStatus")
public class StatusActionForm
        extends BaseActionForm {
    
    private EmployeeStatusChoicesBean employeeStatusChoices;
    
    private String returnUrl;
    private String employeeName;
    private String employeeStatusChoice;
    
    public void setupEmployeeStatusChoices()
            throws NamingException {
        if(employeeStatusChoices == null) {
            var form = PartyUtil.getHome().getGetEmployeeStatusChoicesForm();

            form.setEmployeeName(employeeName);
            form.setDefaultEmployeeStatusChoice(employeeStatusChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = PartyUtil.getHome().getEmployeeStatusChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetEmployeeStatusChoicesResult)executionResult.getResult();
            employeeStatusChoices = result.getEmployeeStatusChoices();

            if(employeeStatusChoice == null)
                employeeStatusChoice = employeeStatusChoices.getDefaultValue();
        }
    }
    
    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getEmployeeName() {
        return employeeName;
    }
    
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
    
    public String getEmployeeStatusChoice() {
        return employeeStatusChoice;
    }
    
    public void setEmployeeStatusChoice(String employeeStatusChoice) {
        this.employeeStatusChoice = employeeStatusChoice;
    }
    
    public List<LabelValueBean> getEmployeeStatusChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupEmployeeStatusChoices();
        if(employeeStatusChoices != null)
            choices = convertChoices(employeeStatusChoices);
        
        return choices;
    }
    
}
