// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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
import com.echothree.control.user.party.remote.form.GetCompanyChoicesForm;
import com.echothree.control.user.party.remote.form.GetDepartmentChoicesForm;
import com.echothree.control.user.party.remote.form.GetDivisionChoicesForm;
import com.echothree.control.user.party.remote.result.GetCompanyChoicesResult;
import com.echothree.control.user.party.remote.result.GetDepartmentChoicesResult;
import com.echothree.control.user.party.remote.result.GetDivisionChoicesResult;
import com.echothree.model.control.party.remote.choice.CompanyChoicesBean;
import com.echothree.model.control.party.remote.choice.DepartmentChoicesBean;
import com.echothree.model.control.party.remote.choice.DivisionChoicesBean;
import com.echothree.util.remote.command.CommandResult;
import com.echothree.util.remote.command.ExecutionResult;
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
    
    private void setupCompanyChoices() {
        if(companyChoices == null) {
            try {
                GetCompanyChoicesForm commandForm = PartyUtil.getHome().getGetCompanyChoicesForm();

                commandForm.setDefaultCompanyChoice(companyChoice);
                commandForm.setAllowNullChoice(Boolean.FALSE.toString());

                CommandResult commandResult = PartyUtil.getHome().getCompanyChoices(userVisitPK, commandForm);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetCompanyChoicesResult result = (GetCompanyChoicesResult)executionResult.getResult();
                companyChoices = result.getCompanyChoices();

                if(companyChoice == null) {
                    companyChoice = companyChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, companyChoices remains null, no default
            }
        }
    }

    private void setupDivisionChoices() {
        if(divisionChoices == null) {
            try {
                GetDivisionChoicesForm commandForm = PartyUtil.getHome().getGetDivisionChoicesForm();

                commandForm.setCompanyName(getCompanyName());
                commandForm.setDefaultDivisionChoice(divisionChoice);
                commandForm.setAllowNullChoice(Boolean.FALSE.toString());

                CommandResult commandResult = PartyUtil.getHome().getDivisionChoices(userVisitPK, commandForm);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetDivisionChoicesResult result = (GetDivisionChoicesResult)executionResult.getResult();
                divisionChoices = result.getDivisionChoices();

                if(divisionChoice == null) {
                    divisionChoice = divisionChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, divisionChoices remains null, no default
            }
        }
    }

    private void setupDepartmentChoices() {
        if(departmentChoices == null) {
            try {
                GetDepartmentChoicesForm commandForm = PartyUtil.getHome().getGetDepartmentChoicesForm();

                commandForm.setCompanyName(getCompanyName());
                commandForm.setDivisionName(getDivisionName());
                commandForm.setDefaultDepartmentChoice(departmentChoice);
                commandForm.setAllowNullChoice(Boolean.FALSE.toString());

                CommandResult commandResult = PartyUtil.getHome().getDepartmentChoices(userVisitPK, commandForm);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetDepartmentChoicesResult result = (GetDepartmentChoicesResult)executionResult.getResult();
                departmentChoices = result.getDepartmentChoices();

                if(departmentChoice == null) {
                    departmentChoice = departmentChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, departmentChoices remains null, no default
            }
        }
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }
    
    public String getPartyName() {
        return partyName;
    }
    
    public String getCompanyChoice() {
        setupCompanyChoices();

        return companyChoice;
    }

    public void setCompanyChoice(String companyChoice) {
        this.companyChoice = companyChoice;
    }

    public List<LabelValueBean> getCompanyChoices() {
        List<LabelValueBean> choices = null;

        setupCompanyChoices();
        if(companyChoices != null) {
            choices = convertChoices(companyChoices);
        }

        return choices;
    }

    public String getDivisionChoice() {
        setupDivisionChoices();

        return divisionChoice;
    }

    public void setDivisionChoice(String divisionChoice) {
        this.divisionChoice = divisionChoice;
    }

    public List<LabelValueBean> getDivisionChoices() {
        List<LabelValueBean> choices = null;

        setupDivisionChoices();
        if(divisionChoices != null) {
            choices = convertChoices(divisionChoices);
        }

        return choices;
    }

    public String getDepartmentChoice() {
        setupDepartmentChoices();

        return departmentChoice;
    }

    public void setDepartmentChoice(String departmentChoice) {
        this.departmentChoice = departmentChoice;
    }

    public List<LabelValueBean> getDepartmentChoices() {
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
