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

package com.echothree.ui.web.main.action.configuration.postaladdressline;

import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;

@SproutForm(name="PostalAddressLineAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private String postalAddressFormatName;
    private String postalAddressLineSortOrder;
    private String prefix;
    private Boolean alwaysIncludePrefix;
    private String suffix;
    private Boolean alwaysIncludeSuffix;
    private Boolean collapseIfEmpty;
    
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
    
    public Boolean getCollapseIfEmpty() {
        return collapseIfEmpty;
    }
    
    public void setCollapseIfEmpty(Boolean collapseIfEmpty) {
        this.collapseIfEmpty = collapseIfEmpty;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        
        setAlwaysIncludePrefix(false);
        setAlwaysIncludeSuffix(false);
        setCollapseIfEmpty(false);
    }
    
}
