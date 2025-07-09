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

package com.echothree.ui.web.main.action.forum.forummimetype;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.result.GetMimeTypeChoicesResult;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.core.common.choice.MimeTypeChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="ForumMimeTypeAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private MimeTypeChoicesBean mimeTypeChoices;
    
    private String forumName;
    private String mimeTypeChoice;
    private Boolean isDefault;
    private String sortOrder;
    
    private void setupMimeTypeChoices()
            throws NamingException {
        if(mimeTypeChoices == null) {
            var form = CoreUtil.getHome().getGetMimeTypeChoicesForm();

            form.setMimeTypeUsageTypeName(MimeTypeUsageTypes.TEXT.name());
            form.setDefaultMimeTypeChoice(mimeTypeChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = CoreUtil.getHome().getMimeTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getMimeTypeChoicesResult = (GetMimeTypeChoicesResult)executionResult.getResult();
            mimeTypeChoices = getMimeTypeChoicesResult.getMimeTypeChoices();

            if(mimeTypeChoice == null) {
                mimeTypeChoice = mimeTypeChoices.getDefaultValue();
            }
        }
    }
    
    public String getForumName() {
        return forumName;
    }
    
    public void setForumName(String forumName) {
        this.forumName = forumName;
    }
    
    public String getMimeTypeChoice()
            throws NamingException {
        setupMimeTypeChoices();
        
        return mimeTypeChoice;
    }
    
    public void setMimeTypeChoice(String mimeTypeChoice) {
        this.mimeTypeChoice = mimeTypeChoice;
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
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        
        isDefault = false;
    }
    
}
