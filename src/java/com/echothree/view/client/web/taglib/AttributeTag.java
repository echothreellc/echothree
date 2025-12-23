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
import com.echothree.view.client.web.taglib.util.HtmlFilterHolder;
import java.util.Locale;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import static javax.servlet.jsp.tagext.Tag.SKIP_BODY;

public class AttributeTag
        extends BaseTag {
    
    protected String attribute;
    
    private void init() {
        attribute = null;
    }

    /** Creates a new instance of AttributeTag */
    public AttributeTag() {
        super();
        init();
    }

    @Override
    public void release() {
        super.release();
        init();
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }
    
    @Override
    public int doStartTag()
            throws JspException {
        var currentHtmlFilterHolder = (HtmlFilterHolder)pageContext.getAttribute(WebConstants.Attribute_HTML_FILTER_HOLDER, PageContext.REQUEST_SCOPE);

        if(currentHtmlFilterHolder.hasCurrentHtmlTagHolder()) {
            currentHtmlFilterHolder.addAttribute(attribute.toLowerCase(Locale.getDefault()));
        } else {
            throw new JspException("attribute may only be used inside the body of a tag tag.");
        }
        
        return SKIP_BODY;
    }

}
