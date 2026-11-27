// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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

package com.echothree.ui.cli.dataloader.util.data.handler.security;

import com.echothree.control.user.security.common.SecurityService;
import com.echothree.control.user.security.common.SecurityUtil;
import com.echothree.control.user.security.common.form.SecurityFormFactory;
import com.echothree.control.user.security.common.spec.SecuritySpecFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import com.echothree.util.common.message.ExecutionErrors;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class SecurityRoleGroupHandler
        extends BaseHandler {
    
    SecurityService securityService;

    String securityRoleGroupName;
    
    /** Creates a new instance of SecurityRoleGroupHandler */
    public SecurityRoleGroupHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String securityRoleGroupName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            securityService = SecurityUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
        
        this.securityRoleGroupName = securityRoleGroupName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("securityRoleGroupDescription")) {
            var attrsMap = getAttrsMap(attrs);
            attrsMap.putIfAbsent("SecurityRoleGroupName", securityRoleGroupName);

            editOrCreate(attrsMap, SecuritySpecFactory.getSecurityRoleGroupDescriptionSpec(),
                    SecurityFormFactory.getEditSecurityRoleGroupDescriptionForm(), securityService::editSecurityRoleGroupDescription,
                    SecurityFormFactory.getCreateSecurityRoleGroupDescriptionForm(), securityService::createSecurityRoleGroupDescription,
                    ExecutionErrors.UnknownSecurityRoleGroupDescription);
        } else if(localName.equals("securityRole")) {
            var attrsMap = getAttrsMap(attrs);
            attrsMap.putIfAbsent("SecurityRoleGroupName", securityRoleGroupName);

            var outcome = editOrCreate(attrsMap, SecuritySpecFactory.getSecurityRoleSpec(),
                    SecurityFormFactory.getEditSecurityRoleForm(), securityService::editSecurityRole,
                    SecurityFormFactory.getCreateSecurityRoleForm(), securityService::createSecurityRole,
                    ExecutionErrors.UnknownSecurityRoleName);

            if(outcome.successful()) {
                var resolvedSecurityRoleGroupName = (String)attrsMap.get("SecurityRoleGroupName");
                var securityRoleName = (String)attrsMap.get("SecurityRoleName");
                initialDataParser.pushHandler(new SecurityRoleHandler(initialDataParser, this, resolvedSecurityRoleGroupName, securityRoleName));
            } else {
                skipCurrentElement();
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("securityRoleGroup")) {
            initialDataParser.popHandler();
        }
    }
    
}
