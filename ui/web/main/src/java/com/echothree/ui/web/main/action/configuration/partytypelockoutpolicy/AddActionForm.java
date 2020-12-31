// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.ui.web.main.action.configuration.partytypelockoutpolicy;

import com.echothree.control.user.uom.common.UomUtil;
import com.echothree.control.user.uom.common.form.GetUnitOfMeasureTypeChoicesForm;
import com.echothree.control.user.uom.common.result.GetUnitOfMeasureTypeChoicesResult;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.common.choice.UnitOfMeasureTypeChoicesBean;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="PartyTypeLockoutPolicyAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private UnitOfMeasureTypeChoicesBean resetFailureCountTimeUnitOfMeasureTypeChoices;
    private UnitOfMeasureTypeChoicesBean lockoutInactiveTimeUnitOfMeasureTypeChoices;
    
    private String partyTypeName;
    private String lockoutFailureCount;
    private String resetFailureCountTime;
    private String resetFailureCountTimeUnitOfMeasureTypeChoice;
    private Boolean manualLockoutReset;
    private String lockoutInactiveTime;
    private String lockoutInactiveTimeUnitOfMeasureTypeChoice;
    
    private void setupResetFailureCountTimeUnitOfMeasureTypeChoices() {
        if(resetFailureCountTimeUnitOfMeasureTypeChoices == null) {
            try {
                GetUnitOfMeasureTypeChoicesForm form = UomUtil.getHome().getGetUnitOfMeasureTypeChoicesForm();
                
                form.setDefaultUnitOfMeasureTypeChoice(resetFailureCountTimeUnitOfMeasureTypeChoice);
                form.setAllowNullChoice(Boolean.TRUE.toString());
                form.setUnitOfMeasureKindUseTypeName(UomConstants.UnitOfMeasureKindUseType_TIME);
                
                CommandResult commandResult = UomUtil.getHome().getUnitOfMeasureTypeChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetUnitOfMeasureTypeChoicesResult getUnitOfMeasureTypeChoicesResult = (GetUnitOfMeasureTypeChoicesResult)executionResult.getResult();
                resetFailureCountTimeUnitOfMeasureTypeChoices = getUnitOfMeasureTypeChoicesResult.getUnitOfMeasureTypeChoices();
                
                if(resetFailureCountTimeUnitOfMeasureTypeChoice == null) {
                    resetFailureCountTimeUnitOfMeasureTypeChoice = resetFailureCountTimeUnitOfMeasureTypeChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, unitOfMeasureTypeChoices remains null, no default
            }
        }
    }
    
    private void setupLockoutInactiveTimeUnitOfMeasureTypeChoices() {
        if(lockoutInactiveTimeUnitOfMeasureTypeChoices == null) {
            try {
                GetUnitOfMeasureTypeChoicesForm form = UomUtil.getHome().getGetUnitOfMeasureTypeChoicesForm();
                
                form.setDefaultUnitOfMeasureTypeChoice(lockoutInactiveTimeUnitOfMeasureTypeChoice);
                form.setAllowNullChoice(Boolean.TRUE.toString());
                form.setUnitOfMeasureKindUseTypeName(UomConstants.UnitOfMeasureKindUseType_TIME);
                
                CommandResult commandResult = UomUtil.getHome().getUnitOfMeasureTypeChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetUnitOfMeasureTypeChoicesResult getUnitOfMeasureTypeChoicesResult = (GetUnitOfMeasureTypeChoicesResult)executionResult.getResult();
                lockoutInactiveTimeUnitOfMeasureTypeChoices = getUnitOfMeasureTypeChoicesResult.getUnitOfMeasureTypeChoices();
                
                if(lockoutInactiveTimeUnitOfMeasureTypeChoice == null) {
                    lockoutInactiveTimeUnitOfMeasureTypeChoice = lockoutInactiveTimeUnitOfMeasureTypeChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, unitOfMeasureTypeChoices remains null, no default
            }
        }
    }
    
    public String getPartyTypeName() {
        return partyTypeName;
    }
    
    public void setPartyTypeName(String partyTypeName) {
        this.partyTypeName = partyTypeName;
    }
    
    public String getLockoutFailureCount() {
        return lockoutFailureCount;
    }
    
    public void setLockoutFailureCount(String lockoutFailureCount) {
        this.lockoutFailureCount = lockoutFailureCount;
    }
    
    public String getResetFailureCountTime() {
        return resetFailureCountTime;
    }
    
    public void setResetFailureCountTime(String resetFailureCountTime) {
        this.resetFailureCountTime = resetFailureCountTime;
    }
    
    public List<LabelValueBean> getResetFailureCountTimeUnitOfMeasureTypeChoices() {
        List<LabelValueBean> choices = null;
        
        setupResetFailureCountTimeUnitOfMeasureTypeChoices();
        if(resetFailureCountTimeUnitOfMeasureTypeChoices != null) {
            choices = convertChoices(resetFailureCountTimeUnitOfMeasureTypeChoices);
        }
        
        return choices;
    }
    
    public String getResetFailureCountTimeUnitOfMeasureTypeChoice() {
        setupResetFailureCountTimeUnitOfMeasureTypeChoices();
        return resetFailureCountTimeUnitOfMeasureTypeChoice;
    }
    
    public void setResetFailureCountTimeUnitOfMeasureTypeChoice(String resetFailureCountTimeUnitOfMeasureTypeChoice) {
        this.resetFailureCountTimeUnitOfMeasureTypeChoice = resetFailureCountTimeUnitOfMeasureTypeChoice;
    }
    
    public Boolean getManualLockoutReset() {
        return manualLockoutReset;
    }
    
    public void setManualLockoutReset(Boolean manualLockoutReset) {
        this.manualLockoutReset = manualLockoutReset;
    }
    
    public String getLockoutInactiveTime() {
        return lockoutInactiveTime;
    }
    
    public void setLockoutInactiveTime(String lockoutInactiveTime) {
        this.lockoutInactiveTime = lockoutInactiveTime;
    }
    
    public List<LabelValueBean> getLockoutInactiveTimeUnitOfMeasureTypeChoices() {
        List<LabelValueBean> choices = null;
        
        setupLockoutInactiveTimeUnitOfMeasureTypeChoices();
        if(lockoutInactiveTimeUnitOfMeasureTypeChoices != null) {
            choices = convertChoices(lockoutInactiveTimeUnitOfMeasureTypeChoices);
        }
        
        return choices;
    }
    
    public String getLockoutInactiveTimeUnitOfMeasureTypeChoice() {
        setupLockoutInactiveTimeUnitOfMeasureTypeChoices();
        return lockoutInactiveTimeUnitOfMeasureTypeChoice;
    }
    
    public void setLockoutInactiveTimeUnitOfMeasureTypeChoice(String lockoutInactiveTimeUnitOfMeasureTypeChoice) {
        this.lockoutInactiveTimeUnitOfMeasureTypeChoice = lockoutInactiveTimeUnitOfMeasureTypeChoice;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        
        manualLockoutReset = Boolean.FALSE;
    }
    
}
