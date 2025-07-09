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

package com.echothree.ui.web.main.action.humanresources.company;

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.result.GetCompanyChoicesResult;
import com.echothree.control.user.party.common.result.GetDepartmentChoicesResult;
import com.echothree.control.user.party.common.result.GetDivisionChoicesResult;
import com.echothree.model.control.party.common.choice.CompanyChoicesBean;
import com.echothree.model.control.party.common.choice.DepartmentChoicesBean;
import com.echothree.model.control.party.common.choice.DivisionChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="HRCompanyAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private CompanyChoicesBean companyChoices;
    private DivisionChoicesBean divisionChoices;
    private DepartmentChoicesBean departmentChoices;
    
    private String partyName;
    private String companyChoice;
    private String divisionChoice;
    private String departmentChoice;
    private String companyName;
    private String divisionName;
    
    private void setupCompanyChoices()
            throws NamingException {
        if(companyChoices == null) {
            var commandForm = PartyUtil.getHome().getGetCompanyChoicesForm();

            commandForm.setDefaultCompanyChoice(companyChoice);
            commandForm.setAllowNullChoice(String.valueOf(false));

            var commandResult = PartyUtil.getHome().getCompanyChoices(userVisitPK, commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetCompanyChoicesResult)executionResult.getResult();
            companyChoices = result.getCompanyChoices();

            if(companyChoice == null) {
                companyChoice = companyChoices.getDefaultValue();
            }
        }
    }

    private void setupDivisionChoices()
            throws NamingException {
        if(divisionChoices == null) {
            var commandForm = PartyUtil.getHome().getGetDivisionChoicesForm();

            commandForm.setCompanyName(getCompanyName());
            commandForm.setDefaultDivisionChoice(divisionChoice);
            commandForm.setAllowNullChoice(String.valueOf(false));

            var commandResult = PartyUtil.getHome().getDivisionChoices(userVisitPK, commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetDivisionChoicesResult)executionResult.getResult();
            divisionChoices = result.getDivisionChoices();

            if(divisionChoice == null) {
                divisionChoice = divisionChoices.getDefaultValue();
            }
        }
    }

    private void setupDepartmentChoices()
            throws NamingException {
        if(departmentChoices == null) {
            var commandForm = PartyUtil.getHome().getGetDepartmentChoicesForm();

            commandForm.setCompanyName(getCompanyName());
            commandForm.setDivisionName(getDivisionName());
            commandForm.setDefaultDepartmentChoice(departmentChoice);
            commandForm.setAllowNullChoice(String.valueOf(false));

            var commandResult = PartyUtil.getHome().getDepartmentChoices(userVisitPK, commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetDepartmentChoicesResult)executionResult.getResult();
            departmentChoices = result.getDepartmentChoices();

            if(departmentChoice == null) {
                departmentChoice = departmentChoices.getDefaultValue();
            }
        }
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }
    
    public String getPartyName() {
        return partyName;
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

    public String getDivisionChoice()
            throws NamingException {
        setupDivisionChoices();

        return divisionChoice;
    }

    public void setDivisionChoice(String divisionChoice) {
        this.divisionChoice = divisionChoice;
    }

    public List<LabelValueBean> getDivisionChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;

        setupDivisionChoices();
        if(divisionChoices != null) {
            choices = convertChoices(divisionChoices);
        }

        return choices;
    }

    public String getDepartmentChoice()
            throws NamingException {
        setupDepartmentChoices();

        return departmentChoice;
    }

    public void setDepartmentChoice(String departmentChoice) {
        this.departmentChoice = departmentChoice;
    }

    public List<LabelValueBean> getDepartmentChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;

        setupDepartmentChoices();
        if(departmentChoices != null) {
            choices = convertChoices(departmentChoices);
        }

        return choices;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

}
