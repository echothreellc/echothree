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

package com.echothree.ui.web.main.action.core.baseencryptionkey;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.result.GetBaseEncryptionKeyStatusChoicesResult;
import com.echothree.model.control.core.common.choice.BaseEncryptionKeyStatusChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="BaseEncryptionKeyStatus")
public class StatusActionForm
        extends BaseActionForm {
    
    private BaseEncryptionKeyStatusChoicesBean baseEncryptionKeyStatusChoices;
    
    private String baseEncryptionKeyName;
    private String baseEncryptionKeyStatusChoice;
    
    public void setupBaseEncryptionKeyStatusChoices()
            throws NamingException {
        if(baseEncryptionKeyStatusChoices == null) {
            var form = CoreUtil.getHome().getGetBaseEncryptionKeyStatusChoicesForm();

            form.setBaseEncryptionKeyName(baseEncryptionKeyName);
            form.setDefaultBaseEncryptionKeyStatusChoice(baseEncryptionKeyStatusChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = CoreUtil.getHome().getBaseEncryptionKeyStatusChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetBaseEncryptionKeyStatusChoicesResult)executionResult.getResult();
            baseEncryptionKeyStatusChoices = result.getBaseEncryptionKeyStatusChoices();

            if(baseEncryptionKeyStatusChoice == null) {
                baseEncryptionKeyStatusChoice = baseEncryptionKeyStatusChoices.getDefaultValue();
            }
        }
    }
    
    public String getBaseEncryptionKeyName() {
        return baseEncryptionKeyName;
    }
    
    public void setBaseEncryptionKeyName(String baseEncryptionKeyName) {
        this.baseEncryptionKeyName = baseEncryptionKeyName;
    }
    
    public String getBaseEncryptionKeyStatusChoice()
            throws NamingException {
        setupBaseEncryptionKeyStatusChoices();
        
        return baseEncryptionKeyStatusChoice;
    }
    
    public void setBaseEncryptionKeyStatusChoice(String baseEncryptionKeyStatusChoice) {
        this.baseEncryptionKeyStatusChoice = baseEncryptionKeyStatusChoice;
    }
    
    public List<LabelValueBean> getBaseEncryptionKeyStatusChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupBaseEncryptionKeyStatusChoices();
        if(baseEncryptionKeyStatusChoices != null) {
            choices = convertChoices(baseEncryptionKeyStatusChoices);
        }
        
        return choices;
    }
    
}
