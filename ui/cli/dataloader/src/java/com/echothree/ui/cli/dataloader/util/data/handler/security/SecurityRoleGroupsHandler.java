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

public class SecurityRoleGroupsHandler
        extends BaseHandler {
    
    SecurityService securityService;
    
    /** Creates a new instance of SecurityRoleGroupsHandler */
    public SecurityRoleGroupsHandler(InitialDataParser initialDataParser, BaseHandler parentHandler)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            securityService = SecurityUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("securityRoleGroup")) {
            var attrsMap = getAttrsMap(attrs);
            // An omitted parent moves the group to ROOT.
            attrsMap.putIfAbsent("ParentSecurityRoleGroupName", null);

            var outcome = editOrCreate(attrsMap, SecuritySpecFactory.getSecurityRoleGroupSpec(),
                    SecurityFormFactory.getEditSecurityRoleGroupForm(), securityService::editSecurityRoleGroup,
                    SecurityFormFactory.getCreateSecurityRoleGroupForm(), securityService::createSecurityRoleGroup,
                    ExecutionErrors.UnknownSecurityRoleGroupName);

            if(outcome.successful()) {
                var securityRoleGroupName = (String)attrsMap.get("SecurityRoleGroupName");
                initialDataParser.pushHandler(new SecurityRoleGroupHandler(initialDataParser, this, securityRoleGroupName));
            } else {
                skipCurrentElement();
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("securityRoleGroups")) {
            initialDataParser.popHandler();
        }
    }
    
}
