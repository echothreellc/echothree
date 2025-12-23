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
import com.google.common.base.Splitter;
import java.util.HashSet;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import org.apache.struts.taglib.TagUtils;

/**
 * Custom tag that iterates the elements of a message collection.
 * It defaults to retrieving the messages from <code>Globals.ERROR_KEY</code>,
 * but if the message attribute is set to true then the messages will be
 * retrieved from <code>Globals.MESSAGE_KEY</code>. This is an alternative
 * to the default <code>ErrorsTag</code>.
 *
 * @since Struts 1.1
 */
public class HasSecurityRoleTag
        extends BaseTag {
    
    /**
     * The key for the execution error that we're going to look for.
     */
    protected String securityRole;
    protected String securityRoles;
    protected String var;
    protected int scope;
    
    private void init() {
        securityRole = null;
        securityRoles = null;
        var = null;
        scope = PageContext.PAGE_SCOPE;
    }
    
    /** Creates a new instance of HasSecurityRoleTag */
    public HasSecurityRoleTag() {
        super();
        init();
    }
    
    @Override
    public void release() {
        super.release();
        init();
    }
    
    public void setSecurityRole(String securityRole) {
        this.securityRole = securityRole;
    }
    
    public void setSecurityRoles(String securityRoles) {
        this.securityRoles = securityRoles;
    }
    
    public void setVar(String var) {
        this.var = var;
    }
    
    public void setScope(String scope) {
        this.scope = translateScope(scope);
    }
    
    /**
     * Construct an iterator for the specified collection, and begin
     * looping through the body once per element.
     *
     * @exception JspException if a JSP exception has occurred
     */
    @Override
    public int doStartTag()
            throws JspException {
        var pageSecurityRoles = (HashSet<String>)pageContext.getAttribute(WebConstants.Attribute_SECURITY_ROLES, PageContext.REQUEST_SCOPE);
        var securityRoleFound = false;
        
        if(pageSecurityRoles != null) {
            if(securityRole != null) {
                if(pageSecurityRoles.contains(securityRole)) {
                    securityRoleFound = true;
                }
            } else if(securityRoles != null) {
                var securityRolesToCheck = Splitter.on(':').trimResults().omitEmptyStrings().splitToList(securityRoles).toArray(new String[0]);
                var securityRolesToCheckLength = securityRolesToCheck.length;
                
                for(var i = 0; i < securityRolesToCheckLength; i++) {
                    if(pageSecurityRoles.contains(securityRolesToCheck[i])) {
                        securityRoleFound = true;
                        break;
                    }
                }
            }
        }
        
        if(var != null) {
            pageContext.setAttribute(var, securityRoleFound, scope);
        }
        
        return securityRoleFound? EVAL_BODY_BUFFERED: SKIP_BODY;
    }
    
    /**
     * Make the next collection element available and loop, or
     * finish the iterations if there are no more elements.
     *
     * @exception JspException if a JSP exception has occurred
     */
    @Override
    public int doAfterBody()
            throws JspException {
        // Render the output from this iteration to the output stream
        if(bodyContent != null) {
            TagUtils.getInstance().writePrevious(pageContext, bodyContent.getString());
            bodyContent.clearBody();
        }
        
        return SKIP_BODY;
    }
    
}
