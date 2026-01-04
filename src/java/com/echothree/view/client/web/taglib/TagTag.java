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
import com.echothree.view.client.web.taglib.util.HtmlTagHolder;
import java.util.Locale;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class TagTag
        extends BaseTag {
    
    protected String tag;
    protected String action;
    
    private void init() {
        tag = null;
        action = null;
    }

    /** Creates a new instance of TagTag */
    public TagTag() {
        super();
        init();
    }

    @Override
    public void release() {
        super.release();
        init();
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setAction(String action) {
        this.action = action;
    }
    
    @Override
    public int doStartTag()
            throws JspException {
        var currentHtmlFilterHolder = (HtmlFilterHolder)pageContext.getAttribute(WebConstants.Attribute_HTML_FILTER_HOLDER, PageContext.REQUEST_SCOPE);

        if(currentHtmlFilterHolder == null) {
            throw new JspException("tag may only be used inside the body of a htmlFilter tag.");
        } else {
            var htmlTagHolder = new HtmlTagHolder(tag.toLowerCase(Locale.getDefault()), action);
            
            currentHtmlFilterHolder.addHtmlTagHolder(htmlTagHolder);
        }
        
        return EVAL_BODY_INCLUDE;
    }
    
    @Override
    public int doEndTag()
            throws JspException {
        var currentHtmlFilterHolder = (HtmlFilterHolder)pageContext.getAttribute(WebConstants.Attribute_HTML_FILTER_HOLDER, PageContext.REQUEST_SCOPE);

        currentHtmlFilterHolder.clearCurrentHtmlTagHolder();

        return EVAL_PAGE;
    }

}
