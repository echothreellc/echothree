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

package com.echothree.ui.web.main.action.inventory.allocationpriority;

import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;

@SproutForm(name="AllocationPriorityEdit")
public class EditActionForm
        extends BaseActionForm {
    
    private String originalAllocationPriorityName;
    private String allocationPriorityName;
    private String priority;
    private Boolean isDefault;
    private String sortOrder;
    private String description;
    
    public String getOriginalAllocationPriorityName() {
        return originalAllocationPriorityName;
    }
    
    public void setOriginalAllocationPriorityName(String originalAllocationPriorityName) {
        this.originalAllocationPriorityName = originalAllocationPriorityName;
    }
    
    public void setAllocationPriorityName(String allocationPriorityName) {
        this.allocationPriorityName = allocationPriorityName;
    }
    
    public String getAllocationPriorityName() {
        return allocationPriorityName;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
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
