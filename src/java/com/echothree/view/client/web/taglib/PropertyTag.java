// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

import com.echothree.view.client.web.WebConstants;
import com.echothree.view.client.web.taglib.util.TransferPropertiesHolder;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class PropertyTag
        extends BaseTag {
    
    protected String property;
    
    private void init() {
        property = null;
    }

    /** Creates a new instance of PropertyTag */
    public PropertyTag() {
        super();
        init();
    }

    @Override
    public void release() {
        super.release();
        init();
    }

    public void setProperty(String property) {
        this.property = property;
    }

    @Override
    public int doStartTag()
            throws JspException {
        var currentTransferPropertiesHolder = (TransferPropertiesHolder)pageContext.getAttribute(WebConstants.Attribute_TRANSFER_PROPERTIES_HOLDER, PageContext.REQUEST_SCOPE);
        var clazz = currentTransferPropertiesHolder == null ? null : currentTransferPropertiesHolder.getClazz();

        if(clazz == null) {
            throw new JspException("property may only be used inside the body of a class tag.");
        } else {
            // Add to the current one and all its parents.
            for(var transferPropertiesHolder = currentTransferPropertiesHolder; transferPropertiesHolder != null ; transferPropertiesHolder = transferPropertiesHolder.getPreviousTransferPropertiesHolder()) {
                currentTransferPropertiesHolder.getTransferProperties().addClassAndProperty(clazz, property);
            }
        }
        
        return SKIP_BODY;
    }

}
