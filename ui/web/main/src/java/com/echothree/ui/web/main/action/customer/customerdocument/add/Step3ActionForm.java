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

package com.echothree.ui.web.main.action.customer.customerdocument.add;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.result.GetMimeTypeChoicesResult;
import com.echothree.model.control.core.common.choice.MimeTypeChoicesBean;
import com.echothree.view.client.web.struts.BaseLanguageActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="CustomerDocumentAdd")
public class Step3ActionForm
        extends BaseLanguageActionForm {
    
    private MimeTypeChoicesBean mimeTypeChoices;

    private String partyName;
    private String documentTypeName;
    private String mimeTypeChoice;
    private Boolean isDefault;
    private String sortOrder;
    private String description;
    private String clob;
    private FormFile blob;
    
    private void setupMimeTypeChoices() {
        if(mimeTypeChoices == null) {
            try {
                var commandForm = CoreUtil.getHome().getGetMimeTypeChoicesForm();

                commandForm.setDefaultMimeTypeChoice(mimeTypeChoice);
                commandForm.setAllowNullChoice(String.valueOf(false));
                commandForm.setDocumentTypeName(documentTypeName);

                var commandResult = CoreUtil.getHome().getMimeTypeChoices(userVisitPK, commandForm);
                var executionResult = commandResult.getExecutionResult();
                var result = (GetMimeTypeChoicesResult)executionResult.getResult();
                mimeTypeChoices = result.getMimeTypeChoices();

                if(mimeTypeChoice == null) {
                    mimeTypeChoice = mimeTypeChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                // failed, mimeTypeChoices remains null, no default
            }
        }
    }

    public String getPartyName() {
        return partyName;
    }
    
    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }
    
    public String getDocumentTypeName() {
        return documentTypeName;
    }
    
    public void setDocumentTypeName(String documentTypeName) {
        this.documentTypeName = documentTypeName;
    }
    
    public List<LabelValueBean> getMimeTypeChoices() {
        List<LabelValueBean> choices = null;

        setupMimeTypeChoices();
        if(mimeTypeChoices != null) {
            choices = convertChoices(mimeTypeChoices);
        }

        return choices;
    }

    public void setMimeTypeChoice(String mimeTypeChoice) {
        this.mimeTypeChoice = mimeTypeChoice;
    }

    public String getMimeTypeChoice() {
        setupMimeTypeChoices();

        return mimeTypeChoice;
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

    public String getClob() {
        return clob;
    }
    
    public void setClob(String clob) {
        this.clob = clob;
    }
    
    public FormFile getBlob() {
        return blob;
    }
    
    public void setBlob(FormFile blob) {
        this.blob = blob;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);

        isDefault = false;
    }
    
}
