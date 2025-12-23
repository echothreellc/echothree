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

import com.echothree.view.client.web.WebConstants;
import com.echothree.view.client.web.taglib.util.TransferPropertiesHolder;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class ClassTag
        extends BaseTag {
    
    protected String className;
    
    private void init() {
        className = null;
    }

    /** Creates a new instance of ClassTag */
    public ClassTag() {
        super();
        init();
    }

    @Override
    public void release() {
        super.release();
        init();
    }

    public void setClassName(String className) {
        this.className = className;
    }
    
    @Override
    public int doStartTag()
            throws JspException {
        var currentTransferPropertiesHolder = (TransferPropertiesHolder)pageContext.getAttribute(WebConstants.Attribute_TRANSFER_PROPERTIES_HOLDER, PageContext.REQUEST_SCOPE);

        if(currentTransferPropertiesHolder == null) {
            throw new JspException("class may only be used inside the body of a transferProperties tag.");
        } else {
            Class clazz;

            try {
                clazz = Class.forName(className);
            } catch(ClassNotFoundException cnfe) {
                throw new JspException(cnfe);
            }

            currentTransferPropertiesHolder.setClazz(clazz);
        }
        
        return EVAL_BODY_INCLUDE;
    }
    
    @Override
    public int doEndTag()
            throws JspException {
        var currentTransferPropertiesHolder = (TransferPropertiesHolder)pageContext.getAttribute(WebConstants.Attribute_TRANSFER_PROPERTIES_HOLDER, PageContext.REQUEST_SCOPE);

        currentTransferPropertiesHolder.setClazz(null);
        
        return EVAL_PAGE;
    }

}
