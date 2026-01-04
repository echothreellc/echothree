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

package com.echothree.ui.web.main.action.core.appearance;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.result.GetTextTransformationChoicesResult;
import com.echothree.model.control.core.common.choice.TextTransformationChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="AppearanceTextTransformationAdd")
public class TextTransformationAddActionForm
        extends BaseActionForm {
    
    private TextTransformationChoicesBean textTransformationChoices;
    
    private String appearanceName;
    private String textTransformationChoice;
    
    private void setupTextTransformationChoices()
            throws NamingException {
        if(textTransformationChoices == null) {
            var commandForm = CoreUtil.getHome().getGetTextTransformationChoicesForm();

            commandForm.setDefaultTextTransformationChoice(textTransformationChoice);
            commandForm.setAllowNullChoice(String.valueOf(false));

            var commandResult = CoreUtil.getHome().getTextTransformationChoices(userVisitPK, commandForm);
            var executionResult = commandResult.getExecutionResult();
            var getTextTransformationChoicesResult = (GetTextTransformationChoicesResult)executionResult.getResult();
            textTransformationChoices = getTextTransformationChoicesResult.getTextTransformationChoices();

            if(textTransformationChoice == null) {
                textTransformationChoice = textTransformationChoices.getDefaultValue();
            }
        }
    }
    
    public void setAppearanceName(String appearanceName) {
        this.appearanceName = appearanceName;
    }
    
    public String getAppearanceName() {
        return appearanceName;
    }
    
    public List<LabelValueBean> getTextTransformationChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupTextTransformationChoices();
        if(textTransformationChoices != null) {
            choices = convertChoices(textTransformationChoices);
        }
        
        return choices;
    }
    
    public void setTextTransformationChoice(String textTransformationChoice) {
        this.textTransformationChoice = textTransformationChoice;
    }
    
    public String getTextTransformationChoice()
            throws NamingException {
        setupTextTransformationChoices();
        return textTransformationChoice;
    }
    
}
