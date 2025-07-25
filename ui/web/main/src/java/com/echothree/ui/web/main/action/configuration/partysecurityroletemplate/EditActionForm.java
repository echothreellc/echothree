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

package com.echothree.ui.web.main.action.configuration.partysecurityroletemplate;

import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;

@SproutForm(name="PartySecurityRoleTemplateEdit")
public class EditActionForm
        extends BaseActionForm {
    
    private String originalPartySecurityRoleTemplateName;
    private String partySecurityRoleTemplateName;
    private Boolean isDefault;
    private String sortOrder;
    private String description;
    
    public String getOriginalPartySecurityRoleTemplateName() {
        return originalPartySecurityRoleTemplateName;
    }
    
    public void setOriginalPartySecurityRoleTemplateName(String originalPartySecurityRoleTemplateName) {
        this.originalPartySecurityRoleTemplateName = originalPartySecurityRoleTemplateName;
    }
    
    public void setPartySecurityRoleTemplateName(String partySecurityRoleTemplateName) {
        this.partySecurityRoleTemplateName = partySecurityRoleTemplateName;
    }
    
    public String getPartySecurityRoleTemplateName() {
        return partySecurityRoleTemplateName;
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
