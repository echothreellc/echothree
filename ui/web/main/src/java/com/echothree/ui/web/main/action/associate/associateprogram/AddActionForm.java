// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.ui.web.main.action.associate.associateprogram;

import com.echothree.control.user.sequence.common.SequenceUtil;
import com.echothree.control.user.sequence.common.form.GetSequenceChoicesForm;
import com.echothree.control.user.sequence.common.result.GetSequenceChoicesResult;
import com.echothree.model.control.sequence.common.SequenceConstants;
import com.echothree.model.control.sequence.common.choice.SequenceChoicesBean;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="AssociateProgramAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private SequenceChoicesBean associateSequenceChoices;
    private SequenceChoicesBean associatePartyContactMechanismSequenceChoices;
    private SequenceChoicesBean associateReferralSequenceChoices;
    
    private String associateProgramName;
    private String associateSequenceChoice;
    private String associatePartyContactMechanismSequenceChoice;
    private String associateReferralSequenceChoice;
    private String itemIndirectSalePercent;
    private String itemDirectSalePercent;
    private Boolean isDefault;
    private String sortOrder;
    private String description;
    
    public void setAssociateProgramName(String associateProgramName) {
        this.associateProgramName = associateProgramName;
    }
    
    public String getAssociateProgramName() {
        return associateProgramName;
    }
    
    private void setupAssociateSequenceChoices() {
        if(associateSequenceChoices == null) {
            try {
                GetSequenceChoicesForm commandForm = SequenceUtil.getHome().getGetSequenceChoicesForm();
                
                commandForm.setSequenceTypeName(SequenceConstants.SequenceType_ASSOCIATE);
                commandForm.setDefaultSequenceChoice(associateSequenceChoice);
                commandForm.setAllowNullChoice(Boolean.TRUE.toString());
                
                CommandResult commandResult = SequenceUtil.getHome().getSequenceChoices(userVisitPK, commandForm);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetSequenceChoicesResult result = (GetSequenceChoicesResult)executionResult.getResult();
                associateSequenceChoices = result.getSequenceChoices();
                
                if(associateSequenceChoice == null)
                    associateSequenceChoice = associateSequenceChoices.getDefaultValue();
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, associateSequenceChoices remains null, no default
            }
        }
    }
    
    public List getAssociateSequenceChoices() {
        List<LabelValueBean> choices = null;
        
        setupAssociateSequenceChoices();
        if(associateSequenceChoices != null)
            choices = convertChoices(associateSequenceChoices);
        
        return choices;
    }
    
    public void setAssociateSequenceChoice(String associateSequenceChoice) {
        this.associateSequenceChoice = associateSequenceChoice;
    }
    
    public String getAssociateSequenceChoice() {
        setupAssociateSequenceChoices();
        return associateSequenceChoice;
    }
    
    private void setupAssociatePartyContactMechanismSequenceChoices() {
        if(associatePartyContactMechanismSequenceChoices == null) {
            try {
                GetSequenceChoicesForm commandForm = SequenceUtil.getHome().getGetSequenceChoicesForm();
                
                commandForm.setSequenceTypeName(SequenceConstants.SequenceType_ASSOCIATE_PARTY_CONTACT_MECHANISM);
                commandForm.setDefaultSequenceChoice(associatePartyContactMechanismSequenceChoice);
                commandForm.setAllowNullChoice(Boolean.TRUE.toString());
                
                CommandResult commandResult = SequenceUtil.getHome().getSequenceChoices(userVisitPK, commandForm);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetSequenceChoicesResult result = (GetSequenceChoicesResult)executionResult.getResult();
                associatePartyContactMechanismSequenceChoices = result.getSequenceChoices();
                
                if(associatePartyContactMechanismSequenceChoice == null)
                    associatePartyContactMechanismSequenceChoice = associatePartyContactMechanismSequenceChoices.getDefaultValue();
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, associatePartyContactMechanismSequenceChoices remains null, no default
            }
        }
    }
    
    public List getAssociatePartyContactMechanismSequenceChoices() {
        List<LabelValueBean> choices = null;
        
        setupAssociatePartyContactMechanismSequenceChoices();
        if(associatePartyContactMechanismSequenceChoices != null)
            choices = convertChoices(associatePartyContactMechanismSequenceChoices);
        
        return choices;
    }
    
    public void setAssociatePartyContactMechanismSequenceChoice(String associatePartyContactMechanismSequenceChoice) {
        this.associatePartyContactMechanismSequenceChoice = associatePartyContactMechanismSequenceChoice;
    }
    
    public String getAssociatePartyContactMechanismSequenceChoice() {
        setupAssociatePartyContactMechanismSequenceChoices();
        return associatePartyContactMechanismSequenceChoice;
    }
    
    private void setupAssociateReferralSequenceChoices() {
        if(associateReferralSequenceChoices == null) {
            try {
                GetSequenceChoicesForm commandForm = SequenceUtil.getHome().getGetSequenceChoicesForm();
                
                commandForm.setSequenceTypeName(SequenceConstants.SequenceType_ASSOCIATE_REFERRAL);
                commandForm.setDefaultSequenceChoice(associateReferralSequenceChoice);
                commandForm.setAllowNullChoice(Boolean.TRUE.toString());
                
                CommandResult commandResult = SequenceUtil.getHome().getSequenceChoices(userVisitPK, commandForm);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetSequenceChoicesResult result = (GetSequenceChoicesResult)executionResult.getResult();
                associateReferralSequenceChoices = result.getSequenceChoices();
                
                if(associateReferralSequenceChoice == null)
                    associateReferralSequenceChoice = associateReferralSequenceChoices.getDefaultValue();
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, associateReferralSequenceChoices remains null, no default
            }
        }
    }
    
    public List getAssociateReferralSequenceChoices() {
        List<LabelValueBean> choices = null;
        
        setupAssociateReferralSequenceChoices();
        if(associateReferralSequenceChoices != null)
            choices = convertChoices(associateReferralSequenceChoices);
        
        return choices;
    }
    
    public void setAssociateReferralSequenceChoice(String associateReferralSequenceChoice) {
        this.associateReferralSequenceChoice = associateReferralSequenceChoice;
    }
    
    public String getAssociateReferralSequenceChoice() {
        setupAssociateReferralSequenceChoices();
        return associateReferralSequenceChoice;
    }
    
    public String getItemIndirectSalePercent() {
        return itemIndirectSalePercent;
    }
    
    public void setItemIndirectSalePercent(String itemIndirectSalePercent) {
        this.itemIndirectSalePercent = itemIndirectSalePercent;
    }
    
    public String getItemDirectSalePercent() {
        return itemDirectSalePercent;
    }
    
    public void setItemDirectSalePercent(String itemDirectSalePercent) {
        this.itemDirectSalePercent = itemDirectSalePercent;
    }
    
    public Boolean getIsDefault() {
        return isDefault;
    }
    
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
    
    public String getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        
        isDefault = Boolean.FALSE;
    }
    
}
