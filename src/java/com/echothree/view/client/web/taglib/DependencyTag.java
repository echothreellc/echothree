// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
// Copyright 1999-2004 The Apache Software Foundation.
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

package com.echothree.view.client.web.taglib;

import com.echothree.util.common.transfer.BaseTransferUtils;
import com.echothree.util.common.transfer.EntityRefExclusions;
import com.echothree.view.client.web.WebConstants;
import com.echothree.view.client.web.taglib.CacheTag.EntityRefs;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class DependencyTag
        extends BaseTag {
    
    protected Object dependsOn;
    protected String excludedComponentVendorNames;
    protected String excludedEntityTypeNames;
    protected String excludedEntityRefs;
    
    private void init() {
        dependsOn = null;
        excludedComponentVendorNames = null;
        excludedEntityTypeNames = null;
        excludedEntityRefs = null;
    }

    /** Creates a new instance of DependencyTag */
    public DependencyTag() {
        super();
        init();
    }

    @Override
    public void release() {
        super.release();
        init();
    }

    public void setDependsOn(Object dependsOn) {
        this.dependsOn = dependsOn;
    }
    
    public void setExcludedComponentVendorNames(String excludedComponentVendorNames) {
        this.excludedComponentVendorNames = excludedComponentVendorNames;
    }

    public void setExcludedEntityTypeNames(String excludedEntityTypeNames) {
        this.excludedEntityTypeNames = excludedEntityTypeNames;
    }

    public void setExcludedEntityRefs(String excludedEntityRefs) {
        this.excludedEntityRefs = excludedEntityRefs;
    }

    @Override
    public int doStartTag()
            throws JspException {
        var currentEntityRefs = (EntityRefs)pageContext.getAttribute(WebConstants.Attribute_ENTITY_REFS, PageContext.REQUEST_SCOPE);
        
        if(currentEntityRefs == null) {
            throw new JspException("cacheDependency may only be used inside the body of a cache tag.");
        } else {
            var entityRefExclusions = excludedComponentVendorNames == null && excludedEntityTypeNames == null && excludedEntityRefs == null ?
                null : new EntityRefExclusions(excludedComponentVendorNames, excludedEntityTypeNames, excludedEntityRefs);

            // Add to the current one, and all its parents (in the cache of nested cache tags).
            currentEntityRefs.addEntityRefs(BaseTransferUtils.getInstance().getEntityRefs(entityRefExclusions, dependsOn));
        }
        
        return SKIP_BODY;
    }

}
