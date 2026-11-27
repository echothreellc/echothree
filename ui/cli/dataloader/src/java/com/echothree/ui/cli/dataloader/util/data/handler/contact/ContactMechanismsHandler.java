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

package com.echothree.ui.cli.dataloader.util.data.handler.contact;

import com.echothree.control.user.contact.common.ContactUtil;
import com.echothree.control.user.contact.common.ContactService;
import com.echothree.control.user.contact.common.form.ContactFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import java.util.Set;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ContactMechanismsHandler
        extends BaseHandler {
    ContactService contactService;
    String partyName;
    
    /** Creates a new instance of ContactMechanismsHandler */
    public ContactMechanismsHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String partyName) {
        super(initialDataParser, parentHandler);
        
        try {
            contactService = ContactUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.partyName = partyName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("contactEmailAddress")) {
            var form = ContactFormFactory.getCreateContactEmailAddressForm();
            
            form.setPartyName(partyName);
            form.set(getAttrsMap(attrs));

            var commandResult = contactService.createContactEmailAddress(initialDataParser.getUserVisit(), form);
            
            if(commandResult.hasErrors()) {
                System.err.println(commandResult);
            } else {
                var executionResult = commandResult.getExecutionResult();
                var result = executionResult.getResult();
                var contactMechanismName = result.getContactMechanismName();
                var entityRef = result.getEntityRef();
                
                initialDataParser.pushHandler(new ContactMechanismHandler(initialDataParser, this, partyName, contactMechanismName, entityRef));
            }
        } else if(localName.equals("contactPostalAddress")) {
            var form = ContactFormFactory.getCreateContactPostalAddressForm();
            
            var personalTitle = attrs.getValue("personalTitle");
            var nameSuffix = attrs.getValue("nameSuffix");

            form.setPartyName(partyName);
            form.setPersonalTitleId(personalTitle == null ? null : initialDataParser.getPersonalTitles().get(personalTitle));
            form.setNameSuffixId(nameSuffix == null ? null : initialDataParser.getNameSuffixes().get(nameSuffix));

            var attrsMap = getAttrsMap(attrs);
            attrsMap.keySet().removeAll(Set.of("PersonalTitle", "PersonalTitleId", "NameSuffix", "NameSuffixId"));
            form.set(attrsMap);

            var commandResult = contactService.createContactPostalAddress(initialDataParser.getUserVisit(), form);
            
            if(commandResult.hasErrors()) {
                System.err.println(commandResult);
            } else {
                var executionResult = commandResult.getExecutionResult();
                var result = executionResult.getResult();
                var contactMechanismName = result.getContactMechanismName();
                var entityRef = result.getEntityRef();
                
                initialDataParser.pushHandler(new ContactMechanismHandler(initialDataParser, this, partyName, contactMechanismName, entityRef));
            }
        } else if(localName.equals("contactTelephone")) {
            var form = ContactFormFactory.getCreateContactTelephoneForm();
            
            form.setPartyName(partyName);
            form.set(getAttrsMap(attrs));

            var commandResult = contactService.createContactTelephone(initialDataParser.getUserVisit(), form);
            
            if(commandResult.hasErrors()) {
                System.err.println(commandResult);
            } else {
                var executionResult = commandResult.getExecutionResult();
                var result = executionResult.getResult();
                var contactMechanismName = result.getContactMechanismName();
                var entityRef = result.getEntityRef();
                
                initialDataParser.pushHandler(new ContactMechanismHandler(initialDataParser, this, partyName, contactMechanismName, entityRef));
            }
        } else if(localName.equals("contactWebAddress")) {
            var form = ContactFormFactory.getCreateContactWebAddressForm();
            
            form.setPartyName(partyName);
            form.set(getAttrsMap(attrs));

            var commandResult = contactService.createContactWebAddress(initialDataParser.getUserVisit(), form);
            
            if(commandResult.hasErrors()) {
                System.err.println(commandResult);
            } else {
                var executionResult = commandResult.getExecutionResult();
                var result = executionResult.getResult();
                var contactMechanismName = result.getContactMechanismName();
                var entityRef = result.getEntityRef();
                
                initialDataParser.pushHandler(new ContactMechanismHandler(initialDataParser, this, partyName, contactMechanismName, entityRef));
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("contactMechanisms")) {
            initialDataParser.popHandler();
        }
    }
    
}
