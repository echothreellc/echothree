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

import com.echothree.util.common.string.AmountFormatter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class FormatAmountTag
        extends BaseTag {
    
    protected AmountFormatter amountFormatter;
    protected Integer amount;
    protected String var;
    protected int scope;
    
    private void init() {
        amountFormatter = null;
        amount = null;
        var = null;
        scope = PageContext.PAGE_SCOPE;
    }
    
    /** Creates a new instance of FormatAmountTag */
    public FormatAmountTag() {
        super();
        init();
    }
    
    @Override
    public void release() {
        super.release();
        init();
    }
    
    public void setAmountFormatter(AmountFormatter amountFormatter) {
        this.amountFormatter = amountFormatter;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
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
        pageContext.setAttribute(var, amountFormatter.format(amount), scope);
        
        return SKIP_BODY;
    }
    
}
