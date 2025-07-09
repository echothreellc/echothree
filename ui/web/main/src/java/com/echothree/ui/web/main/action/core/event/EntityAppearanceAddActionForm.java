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

package com.echothree.ui.web.main.action.core.event;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.result.GetAppearanceChoicesResult;
import com.echothree.model.control.core.common.choice.AppearanceChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="EntityAppearanceAdd")
public class EntityAppearanceAddActionForm
        extends BaseActionForm {
    
    private AppearanceChoicesBean appearanceChoices;
    
    private String entityRef;
    private String appearanceChoice;
    
    private void setupAppearanceChoices()
            throws NamingException {
        if(appearanceChoices == null) {
            var commandForm = CoreUtil.getHome().getGetAppearanceChoicesForm();
                
                commandForm.setDefaultAppearanceChoice(appearanceChoice);
                commandForm.setAllowNullChoice(String.valueOf(false));

            var commandResult = CoreUtil.getHome().getAppearanceChoices(userVisitPK, commandForm);
            var executionResult = commandResult.getExecutionResult();
            var getAppearanceChoicesResult = (GetAppearanceChoicesResult)executionResult.getResult();
                appearanceChoices = getAppearanceChoicesResult.getAppearanceChoices();
                
                if(appearanceChoice == null)
                    appearanceChoice = appearanceChoices.getDefaultValue();
        }
    }
    
    public String getEntityRef() {
        return entityRef;
    }
    
    public void setEntityRef(String entityRef) {
        this.entityRef = entityRef;
    }
    
    public List<LabelValueBean> getAppearanceChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupAppearanceChoices();
        if(appearanceChoices != null) {
            choices = convertChoices(appearanceChoices);
        }
        
        return choices;
    }
    
    public void setAppearanceChoice(String appearanceChoice) {
        this.appearanceChoice = appearanceChoice;
    }
    
    public String getAppearanceChoice()
            throws NamingException {
        setupAppearanceChoices();
        
        return appearanceChoice;
    }
    
}
