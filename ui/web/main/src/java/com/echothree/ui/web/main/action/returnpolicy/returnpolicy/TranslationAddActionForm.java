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

package com.echothree.ui.web.main.action.returnpolicy.returnpolicy;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.result.GetMimeTypeChoicesResult;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.core.common.choice.MimeTypeChoicesBean;
import com.echothree.view.client.web.struts.BaseLanguageActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="ReturnPolicyTranslationAdd")
public class TranslationAddActionForm
        extends BaseLanguageActionForm {
    
    private MimeTypeChoicesBean policyMimeTypeChoices;

    private String returnKindName;
    private String returnPolicyName;
    private String description;
    private String policyMimeTypeChoice;
    private String policy;
    
     private void setupPolicyMimeTypeChoices() {
        if(policyMimeTypeChoices == null) {
            try {
                var commandForm = CoreUtil.getHome().getGetMimeTypeChoicesForm();

                commandForm.setDefaultMimeTypeChoice(policyMimeTypeChoice);
                commandForm.setAllowNullChoice(String.valueOf(true));
                commandForm.setMimeTypeUsageTypeName(MimeTypeUsageTypes.TEXT.name());

                var commandResult = CoreUtil.getHome().getMimeTypeChoices(userVisitPK, commandForm);
                var executionResult = commandResult.getExecutionResult();
                var result = (GetMimeTypeChoicesResult)executionResult.getResult();
                policyMimeTypeChoices = result.getMimeTypeChoices();

                if(policyMimeTypeChoice == null) {
                    policyMimeTypeChoice = policyMimeTypeChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                // failed, policyMimeTypeChoices remains null, no default
            }
        }
    }

    public void setReturnKindName(String returnKindName) {
        this.returnKindName = returnKindName;
    }
    
    public String getReturnKindName() {
        return returnKindName;
    }
    
    public void setReturnPolicyName(String returnPolicyName) {
        this.returnPolicyName = returnPolicyName;
    }
    
    public String getReturnPolicyName() {
        return returnPolicyName;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public List<LabelValueBean> getPolicyMimeTypeChoices() {
        List<LabelValueBean> choices = null;

        setupPolicyMimeTypeChoices();
        if(policyMimeTypeChoices != null) {
            choices = convertChoices(policyMimeTypeChoices);
        }

        return choices;
    }

    public void setPolicyMimeTypeChoice(String policyMimeTypeChoice) {
        this.policyMimeTypeChoice = policyMimeTypeChoice;
    }

    public String getPolicyMimeTypeChoice() {
        setupPolicyMimeTypeChoices();

        return policyMimeTypeChoice;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
    }
    
}
