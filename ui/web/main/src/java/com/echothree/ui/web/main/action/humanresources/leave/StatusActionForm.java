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

package com.echothree.ui.web.main.action.humanresources.leave;

import com.echothree.control.user.employee.common.EmployeeUtil;
import com.echothree.control.user.employee.common.result.GetLeaveStatusChoicesResult;
import com.echothree.model.control.employee.common.choice.LeaveStatusChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="LeaveStatus")
public class StatusActionForm
        extends BaseActionForm {
    
    private LeaveStatusChoicesBean leaveStatusChoices;
    
    private String forwardKey;
    private String partyName;
    private String leaveName;
    private String leaveStatusChoice;
    
    public void setupLeaveStatusChoices()
            throws NamingException {
        if(leaveStatusChoices == null) {
            var form = EmployeeUtil.getHome().getGetLeaveStatusChoicesForm();

            form.setLeaveName(leaveName);
            form.setDefaultLeaveStatusChoice(leaveStatusChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = EmployeeUtil.getHome().getLeaveStatusChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetLeaveStatusChoicesResult)executionResult.getResult();
            leaveStatusChoices = result.getLeaveStatusChoices();

            if(leaveStatusChoice == null) {
                leaveStatusChoice = leaveStatusChoices.getDefaultValue();
            }
        }
    }

    public String getForwardKey() {
        return forwardKey;
    }
    
    public void setForwardKey(String forwardKey) {
        this.forwardKey = forwardKey;
    }
    
    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getLeaveName() {
        return leaveName;
    }

    public void setLeaveName(String leaveName) {
        this.leaveName = leaveName;
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
