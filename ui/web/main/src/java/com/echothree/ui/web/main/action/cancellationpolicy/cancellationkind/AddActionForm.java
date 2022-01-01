// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.ui.web.main.action.cancellationpolicy.cancellationkind;

import com.echothree.control.user.sequence.common.SequenceUtil;
import com.echothree.control.user.sequence.common.form.GetSequenceTypeChoicesForm;
import com.echothree.control.user.sequence.common.result.GetSequenceTypeChoicesResult;
import com.echothree.model.control.sequence.common.choice.SequenceTypeChoicesBean;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="CancellationKindAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private SequenceTypeChoicesBean cancellationSequenceTypeChoices;
    
    private String cancellationKindName;
    private String cancellationSequenceTypeChoice;
    private Boolean isDefault;
    private String sortOrder;
    private String description;
    
    public void setupCancellationSequenceTypeChoices() {
        if(cancellationSequenceTypeChoices == null) {
            try {
                GetSequenceTypeChoicesForm form = SequenceUtil.getHome().getGetSequenceTypeChoicesForm();
                
                form.setDefaultSequenceTypeChoice(cancellationSequenceTypeChoice);
                form.setAllowNullChoice(Boolean.FALSE.toString());
                
                CommandResult commandResult = SequenceUtil.getHome().getSequenceTypeChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetSequenceTypeChoicesResult result = (GetSequenceTypeChoicesResult)executionResult.getResult();
                cancellationSequenceTypeChoices = result.getSequenceTypeChoices();
                
                if(cancellationSequenceTypeChoice == null)
                    cancellationSequenceTypeChoice = cancellationSequenceTypeChoices.getDefaultValue();
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, cancellationSequenceTypeChoices remains null, no default
            }
        }
    }
    
    public void setCancellationKindName(String cancellationKindName) {
        this.cancellationKindName = cancellationKindName;
    }
    
    public String getCancellationKindName() {
        return cancellationKindName;
    }
    
    public List<LabelValueBean> getCancellationSequenceTypeChoices() {
        List<LabelValueBean> choices = null;
        
        setupCancellationSequenceTypeChoices();
        if(cancellationSequenceTypeChoices != null) {
            choices = convertChoices(cancellationSequenceTypeChoices);
        }
        
        return choices;
    }
    
    public void setCancellationSequenceTypeChoice(String cancellationSequenceTypeChoice) {
        this.cancellationSequenceTypeChoice = cancellationSequenceTypeChoice;
    }
    
    public String getCancellationSequenceTypeChoice() {
        setupCancellationSequenceTypeChoices();
        
        return cancellationSequenceTypeChoice;
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
