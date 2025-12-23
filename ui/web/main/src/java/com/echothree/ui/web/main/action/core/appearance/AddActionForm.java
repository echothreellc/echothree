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
import com.echothree.control.user.core.common.result.GetColorChoicesResult;
import com.echothree.control.user.core.common.result.GetFontStyleChoicesResult;
import com.echothree.control.user.core.common.result.GetFontWeightChoicesResult;
import com.echothree.model.control.core.common.choice.ColorChoicesBean;
import com.echothree.model.control.core.common.choice.FontStyleChoicesBean;
import com.echothree.model.control.core.common.choice.FontWeightChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="AppearanceAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private ColorChoicesBean textColorChoices;
    private ColorChoicesBean backgroundColorChoices;
    private FontStyleChoicesBean fontStyleChoices;
    private FontWeightChoicesBean fontWeightChoices;
    
    private String appearanceName;
    private String textColorChoice;
    private String backgroundColorChoice;
    private String fontStyleChoice;
    private String fontWeightChoice;
    private Boolean isDefault;
    private String sortOrder;
    private String description;
    
    private void setupTextColorChoices()
            throws NamingException {
        if(textColorChoices == null) {
            var commandForm = CoreUtil.getHome().getGetColorChoicesForm();

            commandForm.setDefaultColorChoice(textColorChoice);
            commandForm.setAllowNullChoice(String.valueOf(true));

            var commandResult = CoreUtil.getHome().getColorChoices(userVisitPK, commandForm);
            var executionResult = commandResult.getExecutionResult();
            var getColorChoicesResult = (GetColorChoicesResult)executionResult.getResult();
            textColorChoices = getColorChoicesResult.getColorChoices();

            if(textColorChoice == null) {
                textColorChoice = textColorChoices.getDefaultValue();
            }
        }
    }
    
    private void setupBackgroundColorChoices()
            throws NamingException {
        if(backgroundColorChoices == null) {
            var commandForm = CoreUtil.getHome().getGetColorChoicesForm();

            commandForm.setDefaultColorChoice(backgroundColorChoice);
            commandForm.setAllowNullChoice(String.valueOf(true));

            var commandResult = CoreUtil.getHome().getColorChoices(userVisitPK, commandForm);
            var executionResult = commandResult.getExecutionResult();
            var getColorChoicesResult = (GetColorChoicesResult)executionResult.getResult();
            backgroundColorChoices = getColorChoicesResult.getColorChoices();

            if(backgroundColorChoice == null) {
                backgroundColorChoice = backgroundColorChoices.getDefaultValue();
            }
        }
    }
    
    private void setupFontStyleChoices()
            throws NamingException {
        if(fontStyleChoices == null) {
            var commandForm = CoreUtil.getHome().getGetFontStyleChoicesForm();

            commandForm.setDefaultFontStyleChoice(fontStyleChoice);
            commandForm.setAllowNullChoice(String.valueOf(true));

            var commandResult = CoreUtil.getHome().getFontStyleChoices(userVisitPK, commandForm);
            var executionResult = commandResult.getExecutionResult();
            var getFontStyleChoicesResult = (GetFontStyleChoicesResult)executionResult.getResult();
            fontStyleChoices = getFontStyleChoicesResult.getFontStyleChoices();

            if(fontStyleChoice == null) {
                fontStyleChoice = fontStyleChoices.getDefaultValue();
            }
        }
    }
    
    private void setupFontWeightChoices()
            throws NamingException {
        if(fontWeightChoices == null) {
            var commandForm = CoreUtil.getHome().getGetFontWeightChoicesForm();

            commandForm.setDefaultFontWeightChoice(fontWeightChoice);
            commandForm.setAllowNullChoice(String.valueOf(true));

            var commandResult = CoreUtil.getHome().getFontWeightChoices(userVisitPK, commandForm);
            var executionResult = commandResult.getExecutionResult();
            var getFontWeightChoicesResult = (GetFontWeightChoicesResult)executionResult.getResult();
            fontWeightChoices = getFontWeightChoicesResult.getFontWeightChoices();

            if(fontWeightChoice == null) {
                fontWeightChoice = fontWeightChoices.getDefaultValue();
            }
        }
    }
    
    public void setAppearanceName(String appearanceName) {
        this.appearanceName = appearanceName;
    }
    
    public String getAppearanceName() {
        return appearanceName;
    }
    
    public List<LabelValueBean> getTextColorChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupTextColorChoices();
        if(textColorChoices != null) {
            choices = convertChoices(textColorChoices);
        }
        
        return choices;
    }
    
    public void setTextColorChoice(String textColorChoice) {
        this.textColorChoice = textColorChoice;
    }
    
    public String getTextColorChoice()
            throws NamingException {
        setupTextColorChoices();
        return textColorChoice;
    }
    
    public List<LabelValueBean> getBackgroundColorChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupBackgroundColorChoices();
        if(backgroundColorChoices != null) {
            choices = convertChoices(backgroundColorChoices);
        }
        
        return choices;
    }
    
    public void setBackgroundColorChoice(String backgroundColorChoice) {
        this.backgroundColorChoice = backgroundColorChoice;
    }
    
    public String getBackgroundColorChoice()
            throws NamingException {
        setupBackgroundColorChoices();
        return backgroundColorChoice;
    }
    
    public List<LabelValueBean> getFontStyleChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupFontStyleChoices();
        if(fontStyleChoices != null) {
            choices = convertChoices(fontStyleChoices);
        }
        
        return choices;
    }
    
    public void setFontStyleChoice(String fontStyleChoice) {
        this.fontStyleChoice = fontStyleChoice;
    }
    
    public String getFontStyleChoice()
            throws NamingException {
        setupFontStyleChoices();
        return fontStyleChoice;
    }
    
    public List<LabelValueBean> getFontWeightChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupFontWeightChoices();
        if(fontWeightChoices != null) {
            choices = convertChoices(fontWeightChoices);
        }
        
        return choices;
    }
    
    public void setFontWeightChoice(String fontWeightChoice) {
        this.fontWeightChoice = fontWeightChoice;
    }
    
    public String getFontWeightChoice()
            throws NamingException {
        setupFontWeightChoices();
        return fontWeightChoice;
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
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        
        isDefault = false;
    }

}
