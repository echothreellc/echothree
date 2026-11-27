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

public class SecurityRoleHandler
        extends BaseHandler {
    
    SecurityService securityService;

    String securityRoleGroupName;
    String securityRoleName;
    
    /** Creates a new instance of SecurityRoleHandler */
    public SecurityRoleHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String securityRoleGroupName, String securityRoleName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            securityService = SecurityUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
        
        this.securityRoleGroupName = securityRoleGroupName;
        this.securityRoleName = securityRoleName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("securityRoleDescription")) {
            var attrsMap = getAttrsMap(attrs);
            attrsMap.putIfAbsent("SecurityRoleGroupName", securityRoleGroupName);
            attrsMap.putIfAbsent("SecurityRoleName", securityRoleName);

            editOrCreate(attrsMap, SecuritySpecFactory.getSecurityRoleDescriptionSpec(),
                    SecurityFormFactory.getEditSecurityRoleDescriptionForm(), securityService::editSecurityRoleDescription,
                    SecurityFormFactory.getCreateSecurityRoleDescriptionForm(), securityService::createSecurityRoleDescription,
                    ExecutionErrors.UnknownSecurityRoleDescription);
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("securityRole")) {
            initialDataParser.popHandler();
        }
    }
    
}
