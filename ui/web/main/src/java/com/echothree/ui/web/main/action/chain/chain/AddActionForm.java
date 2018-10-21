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

package com.echothree.ui.web.main.action.chain.chain;

import com.echothree.control.user.sequence.common.SequenceUtil;
import com.echothree.control.user.sequence.remote.form.GetSequenceChoicesForm;
import com.echothree.control.user.sequence.remote.result.GetSequenceChoicesResult;
import com.echothree.model.control.sequence.common.SequenceConstants;
import com.echothree.model.control.sequence.remote.choice.SequenceChoicesBean;
import com.echothree.util.remote.command.CommandResult;
import com.echothree.util.remote.command.ExecutionResult;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="ChainAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private SequenceChoicesBean chainInstanceSequenceChoices;
    
    private String chainKindName;
    private String chainTypeName;
    private String chainName;
    private String chainInstanceSequenceChoice;
    private Boolean isDefault;
    private String sortOrder;
    private String description;
    
    private void setupChainInstanceSequenceChoices() {
        if(chainInstanceSequenceChoices == null) {
            try {
                GetSequenceChoicesForm commandForm = SequenceUtil.getHome().getGetSequenceChoicesForm();
                
                commandForm.setSequenceTypeName(SequenceConstants.SequenceType_CHAIN_INSTANCE);
                commandForm.setDefaultSequenceChoice(chainInstanceSequenceChoice);
                commandForm.setAllowNullChoice(Boolean.TRUE.toString());
                
                CommandResult commandResult = SequenceUtil.getHome().getSequenceChoices(userVisitPK, commandForm);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetSequenceChoicesResult result = (GetSequenceChoicesResult)executionResult.getResult();
                chainInstanceSequenceChoices = result.getSequenceChoices();
                
                if(chainInstanceSequenceChoice == null) {
                    chainInstanceSequenceChoice = chainInstanceSequenceChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, chainInstanceSequenceChoices remains null, no default
            }
        }
    }
    
    public void setChainKindName(String chainKindName) {
        this.chainKindName = chainKindName;
    }
    
    public String getChainKindName() {
        return chainKindName;
    }
    
    public void setChainTypeName(String chainTypeName) {
        this.chainTypeName = chainTypeName;
    }
    
    public String getChainTypeName() {
        return chainTypeName;
    }
    
    public void setChainName(String chainName) {
        this.chainName = chainName;
    }
    
    public String getChainName() {
        return chainName;
    }
    
    public List<LabelValueBean> getChainInstanceSequenceChoices() {
        List<LabelValueBean> choices = null;
        
        setupChainInstanceSequenceChoices();
        if(chainInstanceSequenceChoices != null) {
            choices = convertChoices(chainInstanceSequenceChoices);
        }
        
        return choices;
    }
    
    public void setChainInstanceSequenceChoice(String chainInstanceSequenceChoice) {
        this.chainInstanceSequenceChoice = chainInstanceSequenceChoice;
    }
    
    public String getChainInstanceSequenceChoice() {
        setupChainInstanceSequenceChoices();
        
        return chainInstanceSequenceChoice;
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
