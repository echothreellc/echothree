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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class LfTag
        extends BaseTag {
    
    protected String var;
    protected int scope;

    private void init() {
        var = null;
        scope = PageContext.PAGE_SCOPE;
    }

    /** Creates a new instance of LfTag */
    public LfTag() {
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

    /**
     * @return <CODE>SKIP_BODY</CODE>
     * @exception JspException if a JSP exception has occurred
     */
    @Override
    public int doStartTag()
            throws JspException {

        pageContext.setAttribute(var, "\n", scope);

        return SKIP_BODY;
    }

}
