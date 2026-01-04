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

package com.echothree.ui.web.main.action.configuration.partytypepasswordstringpolicy;

import com.echothree.control.user.uom.common.UomUtil;
import com.echothree.control.user.uom.common.result.GetUnitOfMeasureTypeChoicesResult;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.common.choice.UnitOfMeasureTypeChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="PartyTypePasswordStringPolicyAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private UnitOfMeasureTypeChoicesBean minimumPasswordLifetimeUnitOfMeasureTypeChoices;
    private UnitOfMeasureTypeChoicesBean maximumPasswordLifetimeUnitOfMeasureTypeChoices;
    private UnitOfMeasureTypeChoicesBean expirationWarningTimeUnitOfMeasureTypeChoices;
    
    private String partyTypeName;
    private Boolean forceChangeAfterCreate;
    private Boolean forceChangeAfterReset;
    private Boolean allowChange;
    private String passwordHistory;
    private String minimumPasswordLifetime;
    private String minimumPasswordLifetimeUnitOfMeasureTypeChoice;
    private String maximumPasswordLifetime;
    private String maximumPasswordLifetimeUnitOfMeasureTypeChoice;
    private String expirationWarningTime;
    private String expirationWarningTimeUnitOfMeasureTypeChoice;
    private String expiredLoginsPermitted;
    private String minimumLength;
    private String maximumLength;
    private String requiredDigitCount;
    private String requiredLetterCount;
    private String requiredUpperCaseCount;
    private String requiredLowerCaseCount;
    private String maximumRepeated;
    private String minimumCharacterTypes;
    
    private void setupMinimumPasswordLifetimeUnitOfMeasureTypeChoices()
            throws NamingException {
        if(minimumPasswordLifetimeUnitOfMeasureTypeChoices == null) {
            var form = UomUtil.getHome().getGetUnitOfMeasureTypeChoicesForm();

            form.setDefaultUnitOfMeasureTypeChoice(minimumPasswordLifetimeUnitOfMeasureTypeChoice);
            form.setAllowNullChoice(String.valueOf(true));
            form.setUnitOfMeasureKindUseTypeName(UomConstants.UnitOfMeasureKindUseType_TIME);

            var commandResult = UomUtil.getHome().getUnitOfMeasureTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getUnitOfMeasureTypeChoicesResult = (GetUnitOfMeasureTypeChoicesResult)executionResult.getResult();
            minimumPasswordLifetimeUnitOfMeasureTypeChoices = getUnitOfMeasureTypeChoicesResult.getUnitOfMeasureTypeChoices();

            if(minimumPasswordLifetimeUnitOfMeasureTypeChoice == null) {
                minimumPasswordLifetimeUnitOfMeasureTypeChoice = minimumPasswordLifetimeUnitOfMeasureTypeChoices.getDefaultValue();
            }
        }
    }
    
    private void setupMaximumPasswordLifetimeUnitOfMeasureTypeChoices()
            throws NamingException {
        if(maximumPasswordLifetimeUnitOfMeasureTypeChoices == null) {
            var form = UomUtil.getHome().getGetUnitOfMeasureTypeChoicesForm();

            form.setDefaultUnitOfMeasureTypeChoice(maximumPasswordLifetimeUnitOfMeasureTypeChoice);
            form.setAllowNullChoice(String.valueOf(true));
            form.setUnitOfMeasureKindUseTypeName(UomConstants.UnitOfMeasureKindUseType_TIME);

            var commandResult = UomUtil.getHome().getUnitOfMeasureTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getUnitOfMeasureTypeChoicesResult = (GetUnitOfMeasureTypeChoicesResult)executionResult.getResult();
            maximumPasswordLifetimeUnitOfMeasureTypeChoices = getUnitOfMeasureTypeChoicesResult.getUnitOfMeasureTypeChoices();

            if(maximumPasswordLifetimeUnitOfMeasureTypeChoice == null) {
                maximumPasswordLifetimeUnitOfMeasureTypeChoice = maximumPasswordLifetimeUnitOfMeasureTypeChoices.getDefaultValue();
            }
        }
    }
    
    private void setupExpirationWarningTimeUnitOfMeasureTypeChoices()
            throws NamingException {
        if(expirationWarningTimeUnitOfMeasureTypeChoices == null) {
            var form = UomUtil.getHome().getGetUnitOfMeasureTypeChoicesForm();

            form.setDefaultUnitOfMeasureTypeChoice(expirationWarningTimeUnitOfMeasureTypeChoice);
            form.setAllowNullChoice(String.valueOf(true));
            form.setUnitOfMeasureKindUseTypeName(UomConstants.UnitOfMeasureKindUseType_TIME);

            var commandResult = UomUtil.getHome().getUnitOfMeasureTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getUnitOfMeasureTypeChoicesResult = (GetUnitOfMeasureTypeChoicesResult)executionResult.getResult();
            expirationWarningTimeUnitOfMeasureTypeChoices = getUnitOfMeasureTypeChoicesResult.getUnitOfMeasureTypeChoices();

            if(expirationWarningTimeUnitOfMeasureTypeChoice == null) {
                expirationWarningTimeUnitOfMeasureTypeChoice = expirationWarningTimeUnitOfMeasureTypeChoices.getDefaultValue();
            }
        }
    }
    
    public String getPartyTypeName() {
        return partyTypeName;
    }
    
    public void setPartyTypeName(String partyTypeName) {
        this.partyTypeName = partyTypeName;
    }
    
    public Boolean getForceChangeAfterCreate() {
        return forceChangeAfterCreate;
    }

    public void setForceChangeAfterCreate(Boolean forceChangeAfterCreate) {
        this.forceChangeAfterCreate = forceChangeAfterCreate;
    }

    public Boolean getForceChangeAfterReset() {
        return forceChangeAfterReset;
    }

    public void setForceChangeAfterReset(Boolean forceChangeAfterReset) {
        this.forceChangeAfterReset = forceChangeAfterReset;
    }

    public Boolean getAllowChange() {
        return allowChange;
    }
    
    public void setAllowChange(Boolean allowChange) {
        this.allowChange = allowChange;
    }
    
    public String getPasswordHistory() {
        return passwordHistory;
    }
    
    public void setPasswordHistory(String passwordHistory) {
        this.passwordHistory = passwordHistory;
    }
    
    public String getMinimumPasswordLifetime() {
        return minimumPasswordLifetime;
    }
    
    public void setMinimumPasswordLifetime(String minimumPasswordLifetime) {
        this.minimumPasswordLifetime = minimumPasswordLifetime;
    }
    
    public List<LabelValueBean> getMinimumPasswordLifetimeUnitOfMeasureTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupMinimumPasswordLifetimeUnitOfMeasureTypeChoices();
        if(minimumPasswordLifetimeUnitOfMeasureTypeChoices != null) {
            choices = convertChoices(minimumPasswordLifetimeUnitOfMeasureTypeChoices);
        }
        
        return choices;
    }
    
    public String getMinimumPasswordLifetimeUnitOfMeasureTypeChoice()
            throws NamingException {
        setupMinimumPasswordLifetimeUnitOfMeasureTypeChoices();
        
        return minimumPasswordLifetimeUnitOfMeasureTypeChoice;
    }
    
    public void setMinimumPasswordLifetimeUnitOfMeasureTypeChoice(String minimumPasswordLifetimeUnitOfMeasureTypeChoice) {
        this.minimumPasswordLifetimeUnitOfMeasureTypeChoice = minimumPasswordLifetimeUnitOfMeasureTypeChoice;
    }
    
    public String getMaximumPasswordLifetime() {
        return maximumPasswordLifetime;
    }
    
    public void setMaximumPasswordLifetime(String maximumPasswordLifetime) {
        this.maximumPasswordLifetime = maximumPasswordLifetime;
    }
    
    public List<LabelValueBean> getMaximumPasswordLifetimeUnitOfMeasureTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupMaximumPasswordLifetimeUnitOfMeasureTypeChoices();
        if(maximumPasswordLifetimeUnitOfMeasureTypeChoices != null) {
            choices = convertChoices(maximumPasswordLifetimeUnitOfMeasureTypeChoices);
        }
        
        return choices;
    }
    
    public String getMaximumPasswordLifetimeUnitOfMeasureTypeChoice()
            throws NamingException {
        setupMaximumPasswordLifetimeUnitOfMeasureTypeChoices();
        
        return maximumPasswordLifetimeUnitOfMeasureTypeChoice;
    }
    
    public void setMaximumPasswordLifetimeUnitOfMeasureTypeChoice(String maximumPasswordLifetimeUnitOfMeasureTypeChoice) {
        this.maximumPasswordLifetimeUnitOfMeasureTypeChoice = maximumPasswordLifetimeUnitOfMeasureTypeChoice;
    }
    
    public String getExpirationWarningTime() {
        return expirationWarningTime;
    }
    
    public void setExpirationWarningTime(String expirationWarningTime) {
        this.expirationWarningTime = expirationWarningTime;
    }
    
    public List<LabelValueBean> getExpirationWarningTimeUnitOfMeasureTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupExpirationWarningTimeUnitOfMeasureTypeChoices();
        if(expirationWarningTimeUnitOfMeasureTypeChoices != null) {
            choices = convertChoices(expirationWarningTimeUnitOfMeasureTypeChoices);
        }
        
        return choices;
    }
    
    public String getExpirationWarningTimeUnitOfMeasureTypeChoice()
            throws NamingException {
        setupExpirationWarningTimeUnitOfMeasureTypeChoices();
        return expirationWarningTimeUnitOfMeasureTypeChoice;
    }
    
    public void setExpirationWarningTimeUnitOfMeasureTypeChoice(String expirationWarningTimeUnitOfMeasureTypeChoice) {
        this.expirationWarningTimeUnitOfMeasureTypeChoice = expirationWarningTimeUnitOfMeasureTypeChoice;
    }
    
    public String getExpiredLoginsPermitted() {
        return expiredLoginsPermitted;
    }
    
    public void setExpiredLoginsPermitted(String expiredLoginsPermitted) {
        this.expiredLoginsPermitted = expiredLoginsPermitted;
    }
    
    public String getMinimumLength() {
        return minimumLength;
    }
    
    public void setMinimumLength(String minimumLength) {
        this.minimumLength = minimumLength;
    }
    
    public String getMaximumLength() {
        return maximumLength;
    }
    
    public void setMaximumLength(String maximumLength) {
        this.maximumLength = maximumLength;
    }
    
    public String getRequiredDigitCount() {
        return requiredDigitCount;
    }
    
    public void setRequiredDigitCount(String requiredDigitCount) {
        this.requiredDigitCount = requiredDigitCount;
    }
    
    public String getRequiredLetterCount() {
        return requiredLetterCount;
    }
    
    public void setRequiredLetterCount(String requiredLetterCount) {
        this.requiredLetterCount = requiredLetterCount;
    }
    
    public String getRequiredUpperCaseCount() {
        return requiredUpperCaseCount;
    }
    
    public void setRequiredUpperCaseCount(String requiredUpperCaseCount) {
        this.requiredUpperCaseCount = requiredUpperCaseCount;
    }
    
    public String getRequiredLowerCaseCount() {
        return requiredLowerCaseCount;
    }
    
    public void setRequiredLowerCaseCount(String requiredLowerCaseCount) {
        this.requiredLowerCaseCount = requiredLowerCaseCount;
    }
    
    public String getMaximumRepeated() {
        return maximumRepeated;
    }
    
    public void setMaximumRepeated(String maximumRepeated) {
        this.maximumRepeated = maximumRepeated;
    }
    
    public String getMinimumCharacterTypes() {
        return minimumCharacterTypes;
    }
    
    public void setMinimumCharacterTypes(String minimumCharacterTypes) {
        this.minimumCharacterTypes = minimumCharacterTypes;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        
        forceChangeAfterCreate = false;
        forceChangeAfterReset = false;
        allowChange = false;
    }
    
}
