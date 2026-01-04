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
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class PartySecurityRoleTemplateHandler
        extends BaseHandler {
    
    SecurityService securityService;
    
    String partySecurityRoleTemplateName;
    
    /** Creates a new instance of PartySecurityRoleTemplateHandler */
    public PartySecurityRoleTemplateHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String partySecurityRoleTemplateName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            securityService = SecurityUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
        
        this.partySecurityRoleTemplateName = partySecurityRoleTemplateName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("partySecurityRoleTemplateDescription")) {
            var commandForm = SecurityFormFactory.getCreatePartySecurityRoleTemplateDescriptionForm();

            commandForm.setPartySecurityRoleTemplateName(partySecurityRoleTemplateName);
            commandForm.set(getAttrsMap(attrs));

            securityService.createPartySecurityRoleTemplateDescription(initialDataParser.getUserVisit(), commandForm);
        } if(localName.equals("partySecurityRoleTemplateRole")) {
            var commandForm = SecurityFormFactory.getCreatePartySecurityRoleTemplateRoleForm();

            commandForm.setPartySecurityRoleTemplateName(partySecurityRoleTemplateName);
            commandForm.set(getAttrsMap(attrs));

            securityService.createPartySecurityRoleTemplateRole(initialDataParser.getUserVisit(), commandForm);
        } if(localName.equals("partySecurityRoleTemplateTrainingClass")) {
            var commandForm = SecurityFormFactory.getCreatePartySecurityRoleTemplateTrainingClassForm();

            commandForm.setPartySecurityRoleTemplateName(partySecurityRoleTemplateName);
            commandForm.set(getAttrsMap(attrs));

            securityService.createPartySecurityRoleTemplateTrainingClass(initialDataParser.getUserVisit(), commandForm);
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("partySecurityRoleTemplate")) {
            initialDataParser.popHandler();
        }
    }
    
}
