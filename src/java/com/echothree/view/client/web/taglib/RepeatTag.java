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
import com.echothree.view.client.web.taglib.util.RepeatHolder;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import static javax.servlet.jsp.tagext.Tag.EVAL_PAGE;

public class RepeatTag
        extends BaseTag {
    
    /** Creates a new instance of RepeatTag */
    public RepeatTag() {
        super();
    }
    
    @Override
    public void release() {
        super.release();
    }
    
    @Override
    public int doStartTag()
            throws JspException {
        var repeatHolder = (RepeatHolder)pageContext.getAttribute(WebConstants.Attribute_REPEAT_HOLDER, PageContext.REQUEST_SCOPE);

        pageContext.setAttribute(WebConstants.Attribute_REPEAT_HOLDER, new RepeatHolder(repeatHolder), PageContext.REQUEST_SCOPE);

        return EVAL_BODY_INCLUDE;
    }

    @Override
    public int doAfterBody()
    throws JspException {
        var repeatHolder = (RepeatHolder)pageContext.getAttribute(WebConstants.Attribute_REPEAT_HOLDER, PageContext.REQUEST_SCOPE);
        int result;
        
        if(repeatHolder.isBroken()) {
            pageContext.setAttribute(WebConstants.Attribute_REPEAT_HOLDER, repeatHolder.getPreviousRepeatHolder(), PageContext.REQUEST_SCOPE);
            result = SKIP_BODY;
        } else {
            result = EVAL_BODY_AGAIN;
        }
        
        return result;
    }
    
    @Override
    public int doEndTag()
            throws JspException {
        return EVAL_PAGE;
    }

}
