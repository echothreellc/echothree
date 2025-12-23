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

package com.echothree.ui.web.main.action.core.entitytag;

import com.echothree.ui.web.main.framework.MainBaseDeleteActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;

@SproutForm(name="EntityTagDelete")
public class DeleteActionForm
        extends MainBaseDeleteActionForm {
    
    private String tagScopeName;
    private String entityRef;
    private String returnUrl;
    private String tagName;

    /**
     * Returns the tagScopeName.
     * @return the tagScopeName
     */
    public String getTagScopeName() {
        return tagScopeName;
    }

    /**
     * Sets the tagScopeName.
     * @param tagScopeName the tagScopeName to set
     */
    public void setTagScopeName(String tagScopeName) {
        this.tagScopeName = tagScopeName;
    }

    /**
     * Returns the entityRef.
     * @return the entityRef
     */
    public String getEntityRef() {
        return entityRef;
    }

    /**
     * Sets the entityRef.
     * @param entityRef the entityRef to set
     */
    public void setEntityRef(String entityRef) {
        this.entityRef = entityRef;
    }

    /**
     * Returns the returnUrl.
     * @return the returnUrl
     */
    public String getReturnUrl() {
        return returnUrl;
    }

    /**
     * Sets the returnUrl.
     * @param returnUrl the returnUrl to set
     */
    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    /**
     * Returns the tagName.
     * @return the tagName
     */
    public String getTagName() {
        return tagName;
    }

    /**
     * Sets the tagName.
     * @param tagName the tagName to set
     */
    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
    
}
