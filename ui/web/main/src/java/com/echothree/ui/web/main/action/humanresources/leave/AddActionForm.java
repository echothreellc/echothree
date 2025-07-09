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
import com.echothree.control.user.employee.common.result.GetLeaveStatusChoicesResult;
import com.echothree.control.user.employee.common.result.GetLeaveTypeChoicesResult;
import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.result.GetCompanyChoicesResult;
import com.echothree.control.user.uom.common.UomUtil;
import com.echothree.control.user.uom.common.result.GetUnitOfMeasureTypeChoicesResult;
import com.echothree.model.control.employee.common.choice.LeaveReasonChoicesBean;
import com.echothree.model.control.employee.common.choice.LeaveStatusChoicesBean;
import com.echothree.model.control.employee.common.choice.LeaveTypeChoicesBean;
import com.echothree.model.control.party.common.choice.CompanyChoicesBean;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.common.choice.UnitOfMeasureTypeChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="LeaveAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private CompanyChoicesBean companyChoices;
    private LeaveTypeChoicesBean leaveTypeChoices;
    private LeaveReasonChoicesBean leaveReasonChoices;
    private UnitOfMeasureTypeChoicesBean totalTimeUnitOfMeasureTypeChoices;
    private LeaveStatusChoicesBean leaveStatusChoices;
    
    private String partyName;
    private String leaveName;
    private String companyChoice;
    private String leaveTypeChoice;
    private String leaveReasonChoice;
    private String startTime;
    private String endTime;
    private String totalTime;
    private String totalTimeUnitOfMeasureTypeChoice;
    private String leaveStatusChoice;
    
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

    public void setupLeaveTypeChoices()
            throws NamingException {
        if(leaveTypeChoices == null) {
            var form = EmployeeUtil.getHome().getGetLeaveTypeChoicesForm();

            form.setDefaultLeaveTypeChoice(leaveTypeChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = EmployeeUtil.getHome().getLeaveTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetLeaveTypeChoicesResult)executionResult.getResult();
            leaveTypeChoices = result.getLeaveTypeChoices();

            if(leaveTypeChoice == null) {
                leaveTypeChoice = leaveTypeChoices.getDefaultValue();
            }
        }
    }

    public void setupLeaveReasonChoices()
            throws NamingException {
        if(leaveReasonChoices == null) {
            var form = EmployeeUtil.getHome().getGetLeaveReasonChoicesForm();

            form.setDefaultLeaveReasonChoice(leaveReasonChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = EmployeeUtil.getHome().getLeaveReasonChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetLeaveReasonChoicesResult)executionResult.getResult();
            leaveReasonChoices = result.getLeaveReasonChoices();

            if(leaveReasonChoice == null) {
                leaveReasonChoice = leaveReasonChoices.getDefaultValue();
            }
        }
    }

    private void setupTotalTimeUnitOfMeasureTypeChoices()
            throws NamingException {
        if(totalTimeUnitOfMeasureTypeChoices == null) {
            var form = UomUtil.getHome().getGetUnitOfMeasureTypeChoicesForm();

            form.setDefaultUnitOfMeasureTypeChoice(totalTimeUnitOfMeasureTypeChoice);
            form.setAllowNullChoice(String.valueOf(true));
            form.setUnitOfMeasureKindUseTypeName(UomConstants.UnitOfMeasureKindUseType_TIME);

            var commandResult = UomUtil.getHome().getUnitOfMeasureTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getUnitOfMeasureTypeChoicesResult = (GetUnitOfMeasureTypeChoicesResult)executionResult.getResult();
            totalTimeUnitOfMeasureTypeChoices = getUnitOfMeasureTypeChoicesResult.getUnitOfMeasureTypeChoices();

            if(totalTimeUnitOfMeasureTypeChoice == null) {
                totalTimeUnitOfMeasureTypeChoice = totalTimeUnitOfMeasureTypeChoices.getDefaultValue();
            }
        }
    }
    
    public void setupLeaveStatusChoices()
            throws NamingException {
        if(leaveStatusChoices == null) {
            var form = EmployeeUtil.getHome().getGetLeaveStatusChoicesForm();

            form.setDefaultLeaveStatusChoice(leaveStatusChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = EmployeeUtil.getHome().getLeaveStatusChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetLeaveStatusChoicesResult)executionResult.getResult();
            leaveStatusChoices = result.getLeaveStatusChoices();

            if(leaveStatusChoice == null) {
                leaveStatusChoice = leaveStatusChoices.getDefaultValue();
            }
        }
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }
    
    public String getPartyName() {
        return partyName;
    }
    
    public String getLeaveName() {
        return leaveName;
    }

    public void setLeaveName(String leaveName) {
        this.leaveName = leaveName;
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
        if(leaveTypeChoices != null) {
            choices = convertChoices(leaveTypeChoices);
        }

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
        if(leaveReasonChoices != null) {
            choices = convertChoices(leaveReasonChoices);
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

    public String getTotalTime() {
        return totalTime;
    }
    
    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }
    
    public List<LabelValueBean> getTotalTimeUnitOfMeasureTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupTotalTimeUnitOfMeasureTypeChoices();
        if(totalTimeUnitOfMeasureTypeChoices != null) {
            choices = convertChoices(totalTimeUnitOfMeasureTypeChoices);
        }
        
        return choices;
    }
    
    public String getTotalTimeUnitOfMeasureTypeChoice()
            throws NamingException {
        setupTotalTimeUnitOfMeasureTypeChoices();
        return totalTimeUnitOfMeasureTypeChoice;
    }
    
    public void setTotalTimeUnitOfMeasureTypeChoice(String totalTimeUnitOfMeasureTypeChoice) {
        this.totalTimeUnitOfMeasureTypeChoice = totalTimeUnitOfMeasureTypeChoice;
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
        if(leaveStatusChoices != null) {
            choices = convertChoices(leaveStatusChoices);
        }

        return choices;
    }

}
