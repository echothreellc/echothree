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

package com.echothree.ui.web.main.action.item.itemdescriptiontypeuse;

import com.echothree.ui.web.main.framework.MainBaseDeleteActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;

@SproutForm(name="ItemDescriptionTypeUseDelete")
public class DeleteActionForm
        extends MainBaseDeleteActionForm {
    
    private String itemDescriptionTypeName;
    private String itemDescriptionTypeUseTypeName;

    /**
     * Returns the itemDescriptionTypeName.
     * @return the itemDescriptionTypeName
     */
    public String getItemDescriptionTypeName() {
        return itemDescriptionTypeName;
    }

    /**
     * Sets the itemDescriptionTypeName.
     * @param itemDescriptionTypeName the itemDescriptionTypeName to set
     */
    public void setItemDescriptionTypeName(String itemDescriptionTypeName) {
        this.itemDescriptionTypeName = itemDescriptionTypeName;
    }

    /**
     * Returns the itemDescriptionTypeUseTypeName.
     * @return the itemDescriptionTypeUseTypeName
     */
    public String getItemDescriptionTypeUseTypeName() {
        return itemDescriptionTypeUseTypeName;
    }

    /**
     * Sets the itemDescriptionTypeUseTypeName.
     * @param itemDescriptionTypeUseTypeName the itemDescriptionTypeUseTypeName to set
     */
    public void setItemDescriptionTypeUseTypeName(String itemDescriptionTypeUseTypeName) {
        this.itemDescriptionTypeUseTypeName = itemDescriptionTypeUseTypeName;
    }
    
}
