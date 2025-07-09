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

package com.echothree.ui.web.main.action.core.appearance;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.result.GetTextDecorationChoicesResult;
import com.echothree.model.control.core.common.choice.TextDecorationChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="AppearanceTextDecorationAdd")
public class TextDecorationAddActionForm
        extends BaseActionForm {
    
    private TextDecorationChoicesBean textDecorationChoices;
    
    private String appearanceName;
    private String textDecorationChoice;
    
    private void setupTextDecorationChoices()
            throws NamingException {
        if(textDecorationChoices == null) {
            var commandForm = CoreUtil.getHome().getGetTextDecorationChoicesForm();

            commandForm.setDefaultTextDecorationChoice(textDecorationChoice);
            commandForm.setAllowNullChoice(String.valueOf(false));

            var commandResult = CoreUtil.getHome().getTextDecorationChoices(userVisitPK, commandForm);
            var executionResult = commandResult.getExecutionResult();
            var getTextDecorationChoicesResult = (GetTextDecorationChoicesResult)executionResult.getResult();
            textDecorationChoices = getTextDecorationChoicesResult.getTextDecorationChoices();

            if(textDecorationChoice == null) {
                textDecorationChoice = textDecorationChoices.getDefaultValue();
            }
        }
    }
    
    public void setAppearanceName(String appearanceName) {
        this.appearanceName = appearanceName;
    }
    
    public String getAppearanceName() {
        return appearanceName;
    }
    
    public List<LabelValueBean> getTextDecorationChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupTextDecorationChoices();
        if(textDecorationChoices != null) {
            choices = convertChoices(textDecorationChoices);
        }
        
        return choices;
    }
    
    public void setTextDecorationChoice(String textDecorationChoice) {
        this.textDecorationChoice = textDecorationChoice;
    }
    
    public String getTextDecorationChoice()
            throws NamingException {
        setupTextDecorationChoices();
        return textDecorationChoice;
    }
    
}
