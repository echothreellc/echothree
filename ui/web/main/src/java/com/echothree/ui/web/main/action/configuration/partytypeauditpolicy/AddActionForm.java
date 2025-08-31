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

package com.echothree.ui.web.main.action.configuration.partytypeauditpolicy;

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

@SproutForm(name="PartyTypeAuditPolicyAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private UnitOfMeasureTypeChoicesBean retainUserVisitsTimeUnitOfMeasureTypeChoices;
    
    private String partyTypeName;
    private Boolean auditCommands;
    private String retainUserVisitsTime;
    private String retainUserVisitsTimeUnitOfMeasureTypeChoice;
    
    private void setupRetainUserVisitsTimeUnitOfMeasureTypeChoices()
            throws NamingException {
        if(retainUserVisitsTimeUnitOfMeasureTypeChoices == null) {
            var form = UomUtil.getHome().getGetUnitOfMeasureTypeChoicesForm();

            form.setDefaultUnitOfMeasureTypeChoice(retainUserVisitsTimeUnitOfMeasureTypeChoice);
            form.setAllowNullChoice(String.valueOf(true));
            form.setUnitOfMeasureKindUseTypeName(UomConstants.UnitOfMeasureKindUseType_TIME);

            var commandResult = UomUtil.getHome().getUnitOfMeasureTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getUnitOfMeasureTypeChoicesResult = (GetUnitOfMeasureTypeChoicesResult)executionResult.getResult();
            retainUserVisitsTimeUnitOfMeasureTypeChoices = getUnitOfMeasureTypeChoicesResult.getUnitOfMeasureTypeChoices();

            if(retainUserVisitsTimeUnitOfMeasureTypeChoice == null) {
                retainUserVisitsTimeUnitOfMeasureTypeChoice = retainUserVisitsTimeUnitOfMeasureTypeChoices.getDefaultValue();
            }
        }
    }
    
    public String getPartyTypeName() {
        return partyTypeName;
    }
    
    public void setPartyTypeName(String partyTypeName) {
        this.partyTypeName = partyTypeName;
    }
    
    public Boolean getAuditCommands() {
        return auditCommands;
    }

    public void setAuditCommands(Boolean auditCommands) {
        this.auditCommands = auditCommands;
    }
    
    public String getRetainUserVisitsTime() {
        return retainUserVisitsTime;
    }
    
    public void setRetainUserVisitsTime(String retainUserVisitsTime) {
        this.retainUserVisitsTime = retainUserVisitsTime;
    }
    
    public List<LabelValueBean> getRetainUserVisitsTimeUnitOfMeasureTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupRetainUserVisitsTimeUnitOfMeasureTypeChoices();
        if(retainUserVisitsTimeUnitOfMeasureTypeChoices != null) {
            choices = convertChoices(retainUserVisitsTimeUnitOfMeasureTypeChoices);
        }
        
        return choices;
    }
    
    public String getRetainUserVisitsTimeUnitOfMeasureTypeChoice()
            throws NamingException {
        setupRetainUserVisitsTimeUnitOfMeasureTypeChoices();
        return retainUserVisitsTimeUnitOfMeasureTypeChoice;
    }
    
    public void setRetainUserVisitsTimeUnitOfMeasureTypeChoice(String retainUserVisitsTimeUnitOfMeasureTypeChoice) {
        this.retainUserVisitsTimeUnitOfMeasureTypeChoice = retainUserVisitsTimeUnitOfMeasureTypeChoice;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        
        auditCommands = false;
    }

}
