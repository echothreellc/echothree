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

package com.echothree.ui.web.main.action.humanresources.employment;

import com.echothree.control.user.employee.common.EmployeeUtil;
import com.echothree.control.user.employee.common.result.GetTerminationReasonChoicesResult;
import com.echothree.control.user.employee.common.result.GetTerminationTypeChoicesResult;
import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.result.GetCompanyChoicesResult;
import com.echothree.model.control.employee.common.choice.TerminationReasonChoicesBean;
import com.echothree.model.control.employee.common.choice.TerminationTypeChoicesBean;
import com.echothree.model.control.party.common.choice.CompanyChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="EmploymentAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private CompanyChoicesBean companyChoices;
    private TerminationTypeChoicesBean terminationTypeChoices;
    private TerminationReasonChoicesBean terminationReasonChoices;
    
    private String partyName;
    private String employmentName;
    private String companyChoice;
    private String startTime;
    private String endTime;
    private String terminationTypeChoice;
    private String terminationReasonChoice;
    
    public void setupCompanyChoices()
            throws NamingException {
        if(companyChoices == null) {
            var form = PartyUtil.getHome().getGetCompanyChoicesForm();

            form.setDefaultCompanyChoice(companyChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = PartyUtil.getHome().getCompanyChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetCompanyChoicesResult)executionResult.getResult();
            companyChoices = result.getCompanyChoices();

            if(companyChoice == null) {
                companyChoice = companyChoices.getDefaultValue();
            }
        }
    }

    public void setupTerminationTypeChoices()
            throws NamingException {
        if(terminationTypeChoices == null) {
            var form = EmployeeUtil.getHome().getGetTerminationTypeChoicesForm();

            form.setDefaultTerminationTypeChoice(terminationTypeChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = EmployeeUtil.getHome().getTerminationTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetTerminationTypeChoicesResult)executionResult.getResult();
            terminationTypeChoices = result.getTerminationTypeChoices();

            if(terminationTypeChoice == null) {
                terminationTypeChoice = terminationTypeChoices.getDefaultValue();
            }
        }
    }

    public void setupTerminationReasonChoices()
            throws NamingException {
        if(terminationReasonChoices == null) {
            var form = EmployeeUtil.getHome().getGetTerminationReasonChoicesForm();

            form.setDefaultTerminationReasonChoice(terminationReasonChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = EmployeeUtil.getHome().getTerminationReasonChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetTerminationReasonChoicesResult)executionResult.getResult();
            terminationReasonChoices = result.getTerminationReasonChoices();

            if(terminationReasonChoice == null) {
                terminationReasonChoice = terminationReasonChoices.getDefaultValue();
            }
        }
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }
    
    public String getPartyName() {
        return partyName;
    }
    
    public String getEmploymentName() {
        return employmentName;
    }

    public void setEmploymentName(String employmentName) {
        this.employmentName = employmentName;
    }

    public String getCompanyChoice()
            throws NamingException {
        setupCompanyChoices();

        return companyChoice;
    }

    public void setCompanyChoice(String companyChoice) {
        this.companyChoice = companyChoice;
    }

    public List<LabelValueBean> getCompanyChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;

        setupCompanyChoices();
        if(companyChoices != null) {
            choices = convertChoices(companyChoices);
        }

        return choices;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTerminationTypeChoice()
            throws NamingException {
        setupTerminationTypeChoices();

        return terminationTypeChoice;
    }

    public void setTerminationTypeChoice(String terminationTypeChoice) {
        this.terminationTypeChoice = terminationTypeChoice;
    }

    public List<LabelValueBean> getTerminationTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;

        setupTerminationTypeChoices();
        if(terminationTypeChoices != null) {
            choices = convertChoices(terminationTypeChoices);
        }

        return choices;
    }

    public String getTerminationReasonChoice()
            throws NamingException {
        setupTerminationReasonChoices();

        return terminationReasonChoice;
    }

    public void setTerminationReasonChoice(String terminationReasonChoice) {
        this.terminationReasonChoice = terminationReasonChoice;
    }

    public List<LabelValueBean> getTerminationReasonChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;

        setupTerminationReasonChoices();
        if(terminationReasonChoices != null) {
            choices = convertChoices(terminationReasonChoices);
        }

        return choices;
    }

}
