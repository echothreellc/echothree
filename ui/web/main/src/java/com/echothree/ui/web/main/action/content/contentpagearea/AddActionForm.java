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

package com.echothree.ui.web.main.action.content.contentpagearea;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.result.GetMimeTypeChoicesResult;
import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.result.GetLanguageChoicesResult;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.core.common.choice.MimeTypeChoicesBean;
import com.echothree.model.control.party.common.choice.LanguageChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="ContentPageAreaAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private LanguageChoicesBean languageChoices;
    private MimeTypeChoicesBean mimeTypeChoices;
    
    private String contentCollectionName;
    private String contentSectionName;
    private String contentPageName;
    private String sortOrder;
    private String languageChoice;
    private String mimeTypeChoice;
    private String description;
    private String contentPageAreaClob;
    private String contentPageAreaUrl;
    private String parentContentSectionName;
    
    private void setupLanguageChoices()
            throws NamingException {
        if(languageChoices == null) {
            var commandForm = PartyUtil.getHome().getGetLanguageChoicesForm();

            commandForm.setDefaultLanguageChoice(languageChoice);
            commandForm.setAllowNullChoice(String.valueOf(false));

            var commandResult = PartyUtil.getHome().getLanguageChoices(userVisitPK, commandForm);
            var executionResult = commandResult.getExecutionResult();
            var getLanguageChoicesResult = (GetLanguageChoicesResult)executionResult.getResult();
            languageChoices = getLanguageChoicesResult.getLanguageChoices();

            if(languageChoice == null) {
                languageChoice = languageChoices.getDefaultValue();
            }
        }
    }
    
    public List<LabelValueBean> getLanguageChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupLanguageChoices();
        if(languageChoices != null) {
            choices = convertChoices(languageChoices);
        }
        
        return choices;
    }
    
    private void setupMimeTypeChoices()
            throws NamingException {
        if(mimeTypeChoices == null) {
            var commandForm = CoreUtil.getHome().getGetMimeTypeChoicesForm();

            commandForm.setDefaultMimeTypeChoice(mimeTypeChoice);
            commandForm.setAllowNullChoice(String.valueOf(false));
            commandForm.setMimeTypeUsageTypeName(MimeTypeUsageTypes.TEXT.name());

            var commandResult = CoreUtil.getHome().getMimeTypeChoices(userVisitPK, commandForm);
            var executionResult = commandResult.getExecutionResult();
            var getMimeTypeChoicesResult = (GetMimeTypeChoicesResult)executionResult.getResult();
            mimeTypeChoices = getMimeTypeChoicesResult.getMimeTypeChoices();

            if(mimeTypeChoice == null) {
                mimeTypeChoice = mimeTypeChoices.getDefaultValue();
            }
        }
    }
    
    public List<LabelValueBean> getMimeTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupMimeTypeChoices();
        if(mimeTypeChoices != null) {
            choices = convertChoices(mimeTypeChoices);
        }
        
        return choices;
    }
    
    public String getContentCollectionName() {
        return contentCollectionName;
    }
    
    public void setContentCollectionName(String contentCollectionName) {
        this.contentCollectionName = contentCollectionName;
    }
    
    public String getContentSectionName() {
        return contentSectionName;
    }
    
    public void setContentSectionName(String contentSectionName) {
        this.contentSectionName = contentSectionName;
    }
    
    public String getContentPageName() {
        return contentPageName;
    }
    
    public void setContentPageName(String contentPageName) {
        this.contentPageName = contentPageName;
    }
    
    public String getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    public void setLanguageChoice(String languageChoice) {
        this.languageChoice = languageChoice;
    }
    
    public String getLanguageChoice()
            throws NamingException {
        setupLanguageChoices();
        
        return languageChoice;
    }
    
    public void setMimeTypeChoice(String mimeTypeChoice) {
        this.mimeTypeChoice = mimeTypeChoice;
    }
    
    public String getMimeTypeChoice()
            throws NamingException {
        setupMimeTypeChoices();
        
        return mimeTypeChoice;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public String getContentPageAreaClob() {
        return contentPageAreaClob;
    }
    
    public void setContentPageAreaClob(String contentPageAreaClob) {
        this.contentPageAreaClob = contentPageAreaClob;
    }
    
    public String getContentPageAreaUrl() {
        return contentPageAreaUrl;
    }
    
    public void setContentPageAreaUrl(String contentPageAreaUrl) {
        this.contentPageAreaUrl = contentPageAreaUrl;
    }
    
    public String getParentContentSectionName() {
        return parentContentSectionName;
    }
    
    public void setParentContentSectionName(String parentContentSectionName) {
        this.parentContentSectionName = parentContentSectionName;
    }
    
}
