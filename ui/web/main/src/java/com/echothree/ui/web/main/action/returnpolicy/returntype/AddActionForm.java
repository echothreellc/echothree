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

package com.echothree.ui.web.main.action.returnpolicy.returntype;

import com.echothree.control.user.returnpolicy.common.ReturnPolicyUtil;
import com.echothree.control.user.returnpolicy.remote.form.GetReturnKindForm;
import com.echothree.control.user.returnpolicy.remote.result.GetReturnKindResult;
import com.echothree.control.user.sequence.common.SequenceUtil;
import com.echothree.control.user.sequence.remote.form.GetSequenceChoicesForm;
import com.echothree.control.user.sequence.remote.result.GetSequenceChoicesResult;
import com.echothree.model.control.returnpolicy.remote.transfer.ReturnKindTransfer;
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

@SproutForm(name="ReturnTypeAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private SequenceChoicesBean returnSequenceChoices;
    
    private String returnKindName;
    private String returnTypeName;
    private String returnSequenceChoice;
    private Boolean isDefault;
    private String sortOrder;
    private String description;
    
    private String getSequenceTypeName()
            throws NamingException {
        GetReturnKindForm commandForm = ReturnPolicyUtil.getHome().getGetReturnKindForm();
        
        commandForm.setReturnKindName(returnKindName);
        
        CommandResult commandResult = ReturnPolicyUtil.getHome().getReturnKind(userVisitPK, commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetReturnKindResult result = (GetReturnKindResult)executionResult.getResult();
        ReturnKindTransfer returnKindTransfer = result.getReturnKind();
        
        return returnKindTransfer.getReturnSequenceType().getSequenceTypeName();
    }
    
    public void setupReturnSequenceChoices() {
        if(returnSequenceChoices == null) {
            try {
                GetSequenceChoicesForm form = SequenceUtil.getHome().getGetSequenceChoicesForm();
                
                form.setSequenceTypeName(getSequenceTypeName());
                form.setDefaultSequenceChoice(returnSequenceChoice);
                form.setAllowNullChoice(Boolean.TRUE.toString());
                
                CommandResult commandResult = SequenceUtil.getHome().getSequenceChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetSequenceChoicesResult result = (GetSequenceChoicesResult)executionResult.getResult();
                returnSequenceChoices = result.getSequenceChoices();
                
                if(returnSequenceChoice == null)
                    returnSequenceChoice = returnSequenceChoices.getDefaultValue();
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, returnSequenceChoices remains null, no default
            }
        }
    }
    
    public void setReturnKindName(String returnKindName) {
        this.returnKindName = returnKindName;
    }
    
    public String getReturnKindName() {
        return returnKindName;
    }
    
    public void setReturnTypeName(String returnTypeName) {
        this.returnTypeName = returnTypeName;
    }
    
    public String getReturnTypeName() {
        return returnTypeName;
    }
    
    public List<LabelValueBean> getReturnSequenceChoices() {
        List<LabelValueBean> choices = null;
        
        setupReturnSequenceChoices();
        if(returnSequenceChoices != null) {
            choices = convertChoices(returnSequenceChoices);
        }
        
        return choices;
    }
    
    public void setReturnSequenceChoice(String returnSequenceChoice) {
        this.returnSequenceChoice = returnSequenceChoice;
    }
    
    public String getReturnSequenceChoice() {
        setupReturnSequenceChoices();
        
        return returnSequenceChoice;
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
