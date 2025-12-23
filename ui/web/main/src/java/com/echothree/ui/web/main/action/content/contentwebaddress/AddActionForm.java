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

package com.echothree.ui.web.main.action.content.contentwebaddress;

import com.echothree.control.user.content.common.ContentUtil;
import com.echothree.control.user.content.common.result.GetContentCollectionChoicesResult;
import com.echothree.model.control.content.common.choice.ContentCollectionChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="ContentWebAddressAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private ContentCollectionChoicesBean contentCollectionChoices;
    
    private String contentWebAddressName;
    private String contentCollectionChoice;
    private String description;
    
    private void setupContentCollectionChoices()
            throws NamingException {
        if(contentCollectionChoices == null) {
            var getContentCollectionChoicesForm = ContentUtil.getHome().getGetContentCollectionChoicesForm();

            getContentCollectionChoicesForm.setDefaultContentCollectionChoice(contentCollectionChoice);

            var commandResult = ContentUtil.getHome().getContentCollectionChoices(userVisitPK, getContentCollectionChoicesForm);
            var executionResult = commandResult.getExecutionResult();
            var getContentCollectionChoicesResult = (GetContentCollectionChoicesResult)executionResult.getResult();
            contentCollectionChoices = getContentCollectionChoicesResult.getContentCollectionChoices();

            if(contentCollectionChoice == null)
                contentCollectionChoice = contentCollectionChoices.getDefaultValue();
        }
    }
    
    public void setContentWebAddressName(String contentWebAddressName) {
        this.contentWebAddressName = contentWebAddressName;
    }
    
    public String getContentWebAddressName() {
        return contentWebAddressName;
    }
    
    public List<LabelValueBean> getContentCollectionChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupContentCollectionChoices();
        if(contentCollectionChoices != null)
            choices = convertChoices(contentCollectionChoices);
        
        return choices;
    }
    
    public void setContentCollectionChoice(String contentCollectionChoice) {
        this.contentCollectionChoice = contentCollectionChoice;
    }
    
    public String getContentCollectionChoice()
            throws NamingException {
        setupContentCollectionChoices();
        return contentCollectionChoice;
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
    }
    
}
