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

package com.echothree.ui.web.main.action.content.contentsection;

import com.echothree.control.user.content.common.ContentUtil;
import com.echothree.control.user.content.common.result.GetContentSectionChoicesResult;
import com.echothree.model.control.content.common.choice.ContentSectionChoicesBean;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="ContentSectionEdit")
public class EditActionForm
        extends AddActionForm {
    
    protected ContentSectionChoicesBean parentContentSectionChoices = null;
    
    protected String originalContentSectionName = null;
    protected String parentContentSectionChoice = null;
    
    private void setupParentContentSectionChoices()
            throws NamingException {
        if(parentContentSectionChoices == null) {
            var form = ContentUtil.getHome().getGetContentSectionChoicesForm();

            form.setContentCollectionName(contentCollectionName);
            form.setDefaultContentSectionChoice(parentContentSectionChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = ContentUtil.getHome().getContentSectionChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getContentSectionChoicesResult = (GetContentSectionChoicesResult)executionResult.getResult();
            parentContentSectionChoices = getContentSectionChoicesResult.getContentSectionChoices();

            if(parentContentSectionChoice == null)
                parentContentSectionChoice = parentContentSectionChoices.getDefaultValue();
        }
    }
    
    public String getOriginalContentSectionName() {
        return originalContentSectionName;
    }
    
    public void setOriginalContentSectionName(String originalContentSectionName) {
        this.originalContentSectionName = originalContentSectionName;
    }
    
    public String getParentContentSectionChoice() {
        return parentContentSectionChoice;
    }
    
    public void setParentContentSectionChoice(String parentContentSectionChoice) {
        this.parentContentSectionChoice = parentContentSectionChoice;
    }
    
    public List<LabelValueBean> getParentContentSectionChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupParentContentSectionChoices();
        if(parentContentSectionChoices != null)
            choices = convertChoices(parentContentSectionChoices);
        
        return choices;
    }
    
}
