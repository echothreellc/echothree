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

import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.form.BaseForm;
import com.echothree.view.client.web.WebConstants;
import com.google.common.base.Splitter;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BaseTag
        extends BodyTagSupport {
    
    private static final String REQUEST = "request";
    private static final String SESSION = "session";
    private static final String APPLICATION = "application";
    
    private Log log = null;
    
    protected UserVisitPK getUserVisitPK() throws JspException {
        var httpSession = pageContext.getSession();
        UserVisitPK userVisitPK;
        
        if(httpSession != null) {
            userVisitPK = (UserVisitPK)httpSession.getAttribute(WebConstants.Session_USER_VISIT);
            
            if(userVisitPK == null) {
                throw new JspException("UserVisit doesn't exist within the HttpSession, include userVisit tag at start of the start of the page");
            }
        } else {
            throw new JspException("HttpSession doesn't exist, include userVisit tag at start of the start of the page");
        }
        
        return userVisitPK;
    }
    
    protected Log getLog() {
        if(log == null) {
            log = LogFactory.getLog(this.getClass());
        }
        
        return log;
    }
    
   /*
    * Converts the given string description of a scope to the corresponding
    * PageContext constant.
    *
    * The validity of the given scope has already been checked by the
    * appropriate TLV.
    *
    * @param scope String description of scope
    *
    * @return PageContext constant corresponding to given scope description
    */
    public static int translateScope(String scope) {
        var ret = PageContext.PAGE_SCOPE; // default
        
        if (REQUEST.equalsIgnoreCase(scope))
            ret = PageContext.REQUEST_SCOPE;
        else if (SESSION.equalsIgnoreCase(scope))
            ret = PageContext.SESSION_SCOPE;
        else if (APPLICATION.equalsIgnoreCase(scope))
            ret = PageContext.APPLICATION_SCOPE;
        
        return ret;
    }
    
    public static void setOptions(String options, Set<String> defaultOptions, BaseForm commandForm) {
        Set<String> commandOptions;
        
        if(options == null) {
            commandOptions = defaultOptions;
        } else {
            var splitOptions = Splitter.on(':').trimResults().omitEmptyStrings().splitToList(options).toArray(new String[0]);
            var splitOptionsLength = splitOptions.length;

            commandOptions = new HashSet<>(splitOptionsLength);
            
            for(var i = 0; i < splitOptionsLength; i++) {
                commandOptions.add(splitOptions[i]);
            }
        }
        
        commandForm.setOptions(commandOptions);
    }
    
}
