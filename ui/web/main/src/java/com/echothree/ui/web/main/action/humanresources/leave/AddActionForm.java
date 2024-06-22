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

package com.echothree.ui.web.main.action.humanresources.leave;

import com.echothree.control.user.employee.common.EmployeeUtil;
import com.echothree.control.user.employee.common.form.GetLeaveReasonChoicesForm;
import com.echothree.control.user.employee.common.form.GetLeaveStatusChoicesForm;
import com.echothree.control.user.employee.common.form.GetLeaveTypeChoicesForm;
import com.echothree.control.user.employee.common.result.GetLeaveReasonChoicesResult;
import com.echothree.control.user.employee.common.result.GetLeaveStatusChoicesResult;
import com.echothree.control.user.employee.common.result.GetLeaveTypeChoicesResult;
import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.form.GetCompanyChoicesForm;
import com.echothree.control.user.party.common.result.GetCompanyChoicesResult;
import com.echothree.control.user.uom.common.UomUtil;
import com.echothree.control.user.uom.common.form.GetUnitOfMeasureTypeChoicesForm;
import com.echothree.control.user.uom.common.result.GetUnitOfMeasureTypeChoicesResult;
import com.echothree.model.control.employee.common.choice.LeaveReasonChoicesBean;
import com.echothree.model.control.employee.common.choice.LeaveStatusChoicesBean;
import com.echothree.model.control.employee.common.choice.LeaveTypeChoicesBean;
import com.echothree.model.control.party.common.choice.CompanyChoicesBean;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.common.choice.UnitOfMeasureTypeChoicesBean;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
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
    
    public void setupCompanyChoices() {
        if(companyChoices == null) {
            try {
                GetCompanyChoicesForm form = PartyUtil.getHome().getGetCompanyChoicesForm();

                form.setDefaultCompanyChoice(companyChoice);
                form.setAllowNullChoice(Boolean.FALSE.toString());

                CommandResult commandResult = PartyUtil.getHome().getCompanyChoices(userVisitPK, form);
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

    public void setupLeaveTypeChoices() {
        if(leaveTypeChoices == null) {
            try {
                GetLeaveTypeChoicesForm form = EmployeeUtil.getHome().getGetLeaveTypeChoicesForm();

                form.setDefaultLeaveTypeChoice(leaveTypeChoice);
                form.setAllowNullChoice(Boolean.FALSE.toString());

                CommandResult commandResult = EmployeeUtil.getHome().getLeaveTypeChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetLeaveTypeChoicesResult result = (GetLeaveTypeChoicesResult)executionResult.getResult();
                leaveTypeChoices = result.getLeaveTypeChoices();

                if(leaveTypeChoice == null) {
                    leaveTypeChoice = leaveTypeChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, leaveTypeChoices remains null, no default
            }
        }
    }

    public void setupLeaveReasonChoices() {
        if(leaveReasonChoices == null) {
            try {
                GetLeaveReasonChoicesForm form = EmployeeUtil.getHome().getGetLeaveReasonChoicesForm();

                form.setDefaultLeaveReasonChoice(leaveReasonChoice);
                form.setAllowNullChoice(Boolean.FALSE.toString());

                CommandResult commandResult = EmployeeUtil.getHome().getLeaveReasonChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetLeaveReasonChoicesResult result = (GetLeaveReasonChoicesResult)executionResult.getResult();
                leaveReasonChoices = result.getLeaveReasonChoices();

                if(leaveReasonChoice == null) {
                    leaveReasonChoice = leaveReasonChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, leaveReasonChoices remains null, no default
            }
        }
    }

    private void setupTotalTimeUnitOfMeasureTypeChoices() {
        if(totalTimeUnitOfMeasureTypeChoices == null) {
            try {
                GetUnitOfMeasureTypeChoicesForm form = UomUtil.getHome().getGetUnitOfMeasureTypeChoicesForm();
                
                form.setDefaultUnitOfMeasureTypeChoice(totalTimeUnitOfMeasureTypeChoice);
                form.setAllowNullChoice(Boolean.TRUE.toString());
                form.setUnitOfMeasureKindUseTypeName(UomConstants.UnitOfMeasureKindUseType_TIME);
                
                CommandResult commandResult = UomUtil.getHome().getUnitOfMeasureTypeChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetUnitOfMeasureTypeChoicesResult getUnitOfMeasureTypeChoicesResult = (GetUnitOfMeasureTypeChoicesResult)executionResult.getResult();
                totalTimeUnitOfMeasureTypeChoices = getUnitOfMeasureTypeChoicesResult.getUnitOfMeasureTypeChoices();
                
                if(totalTimeUnitOfMeasureTypeChoice == null) {
                    totalTimeUnitOfMeasureTypeChoice = totalTimeUnitOfMeasureTypeChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, unitOfMeasureTypeChoices remains null, no default
            }
        }
    }
    
    public void setupLeaveStatusChoices() {
        if(leaveStatusChoices == null) {
            try {
                GetLeaveStatusChoicesForm form = EmployeeUtil.getHome().getGetLeaveStatusChoicesForm();

                form.setDefaultLeaveStatusChoice(leaveStatusChoice);
                form.setAllowNullChoice(Boolean.TRUE.toString());

                CommandResult commandResult = EmployeeUtil.getHome().getLeaveStatusChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetLeaveStatusChoicesResult result = (GetLeaveStatusChoicesResult)executionResult.getResult();
                leaveStatusChoices = result.getLeaveStatusChoices();

                if(leaveStatusChoice == null) {
                    leaveStatusChoice = leaveStatusChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, LeaveStatusChoices remains null, no default
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

    public String getLeaveTypeChoice() {
        setupLeaveTypeChoices();

        return leaveTypeChoice;
    }

    public void setLeaveTypeChoice(String leaveTypeChoice) {
        this.leaveTypeChoice = leaveTypeChoice;
    }

    public List<LabelValueBean> getLeaveTypeChoices() {
        List<LabelValueBean> choices = null;

        setupLeaveTypeChoices();
        if(leaveTypeChoices != null) {
            choices = convertChoices(leaveTypeChoices);
        }

        return choices;
    }

    public String getLeaveReasonChoice() {
        setupLeaveReasonChoices();

        return leaveReasonChoice;
    }

    public void setLeaveReasonChoice(String leaveReasonChoice) {
        this.leaveReasonChoice = leaveReasonChoice;
    }

    public List<LabelValueBean> getLeaveReasonChoices() {
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
    
    public List<LabelValueBean> getTotalTimeUnitOfMeasureTypeChoices() {
        List<LabelValueBean> choices = null;
        
        setupTotalTimeUnitOfMeasureTypeChoices();
        if(totalTimeUnitOfMeasureTypeChoices != null) {
            choices = convertChoices(totalTimeUnitOfMeasureTypeChoices);
        }
        
        return choices;
    }
    
    public String getTotalTimeUnitOfMeasureTypeChoice() {
        setupTotalTimeUnitOfMeasureTypeChoices();
        return totalTimeUnitOfMeasureTypeChoice;
    }
    
    public void setTotalTimeUnitOfMeasureTypeChoice(String totalTimeUnitOfMeasureTypeChoice) {
        this.totalTimeUnitOfMeasureTypeChoice = totalTimeUnitOfMeasureTypeChoice;
    }
    
    public String getLeaveStatusChoice() {
        setupLeaveStatusChoices();

        return leaveStatusChoice;
    }

    public void setLeaveStatusChoice(String leaveStatusChoice) {
        this.leaveStatusChoice = leaveStatusChoice;
    }

    public List<LabelValueBean> getLeaveStatusChoices() {
        List<LabelValueBean> choices = null;

        setupLeaveStatusChoices();
        if(leaveStatusChoices != null) {
            choices = convertChoices(leaveStatusChoices);
        }

        return choices;
    }

}
