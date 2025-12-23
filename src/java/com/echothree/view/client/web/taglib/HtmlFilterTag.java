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
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class HtmlFilterTag
        extends BaseTag {
    
    protected String var;
    protected int scope;
    
    private void init() {
        var = null;
        scope = PageContext.PAGE_SCOPE;
    }
    
    /** Creates a new instance of HtmlFilterTag */
    public HtmlFilterTag() {
        super();
        init();
    }
    
    @Override
    public void release() {
        super.release();
        init();
    }
    
    public void setVar(String var) {
        this.var = var;
    }
    
    public void setScope(String scope) {
        this.scope = translateScope(scope);
    }
    
    @Override
    public int doStartTag()
            throws JspException {
        var htmlFilterHolder = (HtmlFilterHolder)pageContext.getAttribute(WebConstants.Attribute_HTML_FILTER_HOLDER, PageContext.REQUEST_SCOPE);

        pageContext.setAttribute(WebConstants.Attribute_HTML_FILTER_HOLDER, new HtmlFilterHolder(htmlFilterHolder), PageContext.REQUEST_SCOPE);

        return EVAL_BODY_INCLUDE;
    }

    @Override
    public int doEndTag()
            throws JspException {
        var currentHtmlFilterHolder = (HtmlFilterHolder)pageContext.getAttribute(WebConstants.Attribute_HTML_FILTER_HOLDER, PageContext.REQUEST_SCOPE);

        pageContext.setAttribute(WebConstants.Attribute_HTML_FILTER_HOLDER, currentHtmlFilterHolder.getPreviousHtmlFilterHolder(), PageContext.REQUEST_SCOPE);
        pageContext.setAttribute(var, currentHtmlFilterHolder.getElementRemover(), scope);
        
        return EVAL_PAGE;
    }

}
