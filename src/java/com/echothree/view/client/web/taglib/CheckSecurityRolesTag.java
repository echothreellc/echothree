// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

import com.echothree.control.user.security.common.SecurityUtil;
import com.echothree.control.user.security.common.form.CheckSecurityRolesForm;
import com.echothree.control.user.security.common.result.CheckSecurityRolesResult;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.WebConstants;
import com.google.common.base.Splitter;
import java.util.HashSet;
import javax.naming.NamingException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class CheckSecurityRolesTag
        extends BaseTag {
    
    protected String securityRoles;
    protected boolean logErrors;
    
    private void init() {
        securityRoles = null;
        logErrors = true;
    }
    
    /** Creates a new instance of CheckSecurityRolesTag */
    public CheckSecurityRolesTag() {
        super();
        init();
    }
    
    @Override
    public void release() {
        super.release();
        init();
    }
    
    public void setSecurityRoles(String securityRoles) {
        this.securityRoles = securityRoles;
    }
    
    public void setLogErrors(Boolean logErrors) {
        this.logErrors = logErrors;
    }
    
    @Override
    public int doStartTag()
            throws JspException {
        String newSecurityRoles = null;
        HashSet<String> pageSecurityRoles = (HashSet<String>)pageContext.getAttribute(WebConstants.Attribute_SECURITY_ROLES, PageContext.REQUEST_SCOPE);
        
        try {
            CheckSecurityRolesForm commandForm = SecurityUtil.getHome().getCheckSecurityRolesForm();

            commandForm.setSecurityRoles(securityRoles);

            CommandResult commandResult = SecurityUtil.getHome().checkSecurityRoles(getUserVisitPK(), commandForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            CheckSecurityRolesResult checkSecurityRolesResult = (CheckSecurityRolesResult)executionResult.getResult();
            
            if(commandResult.hasErrors()) {
                if(logErrors) {
                    getLog().error(commandResult);
                }
            }
            
            newSecurityRoles = checkSecurityRolesResult.getSecurityRoles();
        } catch(NamingException ne) {
            throw new JspException(ne);
        }
        
        if(newSecurityRoles != null) {
            String []newSecurityRolesArray = Splitter.on(':').trimResults().omitEmptyStrings().splitToList(newSecurityRoles).toArray(new String[0]);
            int newSecurityRolesArrayLength = newSecurityRolesArray.length;
            
            if(pageSecurityRoles == null) {
                pageSecurityRoles = new HashSet<>(newSecurityRolesArrayLength);
            }
            
            for(int i = 0; i < newSecurityRolesArrayLength; i++) {
                pageSecurityRoles.add(newSecurityRolesArray[i]);
            }
        }
        
        pageContext.setAttribute(WebConstants.Attribute_SECURITY_ROLES, pageSecurityRoles, PageContext.REQUEST_SCOPE);
        
        return SKIP_BODY;
    }
    
}
