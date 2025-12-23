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
import com.echothree.control.user.party.common.result.GetEmployeeAvailabilityChoicesResult;
import com.echothree.model.control.employee.common.choice.EmployeeAvailabilityChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="HREmployeeAvailability")
public class AvailabilityActionForm
        extends BaseActionForm {
    
    private EmployeeAvailabilityChoicesBean employeeAvailabilityChoices;
    
    private String returnUrl;
    private String employeeName;
    private String employeeAvailabilityChoice;
    
    public void setupEmployeeAvailabilityChoices()
            throws NamingException {
        if(employeeAvailabilityChoices == null) {
            var form = PartyUtil.getHome().getGetEmployeeAvailabilityChoicesForm();

            form.setEmployeeName(employeeName);
            form.setDefaultEmployeeAvailabilityChoice(employeeAvailabilityChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = PartyUtil.getHome().getEmployeeAvailabilityChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetEmployeeAvailabilityChoicesResult)executionResult.getResult();
            employeeAvailabilityChoices = result.getEmployeeAvailabilityChoices();

            if(employeeAvailabilityChoice == null)
                employeeAvailabilityChoice = employeeAvailabilityChoices.getDefaultValue();
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
    
    public String getEmployeeAvailabilityChoice() {
        return employeeAvailabilityChoice;
    }
    
    public void setEmployeeAvailabilityChoice(String employeeAvailabilityChoice) {
        this.employeeAvailabilityChoice = employeeAvailabilityChoice;
    }
    
    public List<LabelValueBean> getEmployeeAvailabilityChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupEmployeeAvailabilityChoices();
        if(employeeAvailabilityChoices != null)
            choices = convertChoices(employeeAvailabilityChoices);
        
        return choices;
    }
    
}
