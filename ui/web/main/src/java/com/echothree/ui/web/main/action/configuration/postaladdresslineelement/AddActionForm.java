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

package com.echothree.ui.web.main.action.configuration.postaladdresslineelement;

import com.echothree.control.user.contact.common.ContactUtil;
import com.echothree.control.user.contact.common.result.GetPostalAddressElementTypeChoicesResult;
import com.echothree.model.control.contact.common.choice.PostalAddressElementTypeChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="PostalAddressLineElementAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private PostalAddressElementTypeChoicesBean postalAddressElementTypeChoices;
    
    private String postalAddressFormatName;
    private String postalAddressLineSortOrder;
    private String postalAddressLineElementSortOrder;
    private String postalAddressElementTypeChoice;
    private String prefix;
    private Boolean alwaysIncludePrefix;
    private String suffix;
    private Boolean alwaysIncludeSuffix;
    
    public void setupPostalAddressElementTypeChoices()
            throws NamingException {
        if(postalAddressElementTypeChoices == null) {
            var form = ContactUtil.getHome().getGetPostalAddressElementTypeChoicesForm();

            form.setDefaultPostalAddressElementTypeChoice(postalAddressElementTypeChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = ContactUtil.getHome().getPostalAddressElementTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetPostalAddressElementTypeChoicesResult)executionResult.getResult();
            postalAddressElementTypeChoices = result.getPostalAddressElementTypeChoices();

            if(postalAddressElementTypeChoice == null) {
                postalAddressElementTypeChoice = postalAddressElementTypeChoices.getDefaultValue();
            }
        }
    }
    
    public String getPostalAddressFormatName() {
        return postalAddressFormatName;
    }
    
    public void setPostalAddressFormatName(String postalAddressFormatName) {
        this.postalAddressFormatName = postalAddressFormatName;
    }
    
    public String getPostalAddressLineSortOrder() {
        return postalAddressLineSortOrder;
    }
    
    public void setPostalAddressLineSortOrder(String postalAddressLineSortOrder) {
        this.postalAddressLineSortOrder = postalAddressLineSortOrder;
    }
    
    public String getPostalAddressLineElementSortOrder() {
        return postalAddressLineElementSortOrder;
    }
    
    public void setPostalAddressLineElementSortOrder(String postalAddressLineElementSortOrder) {
        this.postalAddressLineElementSortOrder = postalAddressLineElementSortOrder;
    }
    
    public String getPostalAddressElementTypeChoice() {
        return postalAddressElementTypeChoice;
    }
    
    public void setPostalAddressElementTypeChoice(String postalAddressElementTypeChoice) {
        this.postalAddressElementTypeChoice = postalAddressElementTypeChoice;
    }
    
    public List<LabelValueBean> getPostalAddressElementTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupPostalAddressElementTypeChoices();
        if(postalAddressElementTypeChoices != null) {
            choices = convertChoices(postalAddressElementTypeChoices);
        }
        
        return choices;
    }
    
    public String getPrefix() {
        return prefix;
    }
    
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
    
    public Boolean getAlwaysIncludePrefix() {
        return alwaysIncludePrefix;
    }
    
    public void setAlwaysIncludePrefix(Boolean alwaysIncludePrefix) {
        this.alwaysIncludePrefix = alwaysIncludePrefix;
    }
    
    public String getSuffix() {
        return suffix;
    }
    
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
    
    public Boolean getAlwaysIncludeSuffix() {
        return alwaysIncludeSuffix;
    }
    
    public void setAlwaysIncludeSuffix(Boolean alwaysIncludeSuffix) {
        this.alwaysIncludeSuffix = alwaysIncludeSuffix;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        
        setAlwaysIncludePrefix(false);
        setAlwaysIncludeSuffix(false);
    }
    
}
