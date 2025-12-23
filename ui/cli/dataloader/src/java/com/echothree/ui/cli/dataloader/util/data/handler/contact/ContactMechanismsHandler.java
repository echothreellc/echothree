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
import com.echothree.control.user.contact.common.result.CreateContactEmailAddressResult;
import com.echothree.control.user.contact.common.result.CreateContactPostalAddressResult;
import com.echothree.control.user.contact.common.result.CreateContactTelephoneResult;
import com.echothree.control.user.contact.common.result.CreateContactWebAddressResult;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
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
            
            String emailAddress = null;
            String allowSolicitation = null;
            String description = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("emailAddress"))
                    emailAddress = attrs.getValue(i);
                else if(attrs.getQName(i).equals("allowSolicitation"))
                    allowSolicitation = attrs.getValue(i);
                else if(attrs.getQName(i).equals("description"))
                    description = attrs.getValue(i);
            }
            
            form.setPartyName(partyName);
            form.setEmailAddress(emailAddress);
            form.setAllowSolicitation(allowSolicitation);
            form.setDescription(description);

            var commandResult = contactService.createContactEmailAddress(initialDataParser.getUserVisit(), form);
            
            if(commandResult.hasErrors()) {
                System.err.println(commandResult);
            } else {
                var executionResult = commandResult.getExecutionResult();
                var result = (CreateContactEmailAddressResult)executionResult.getResult();
                var contactMechanismName = result.getContactMechanismName();
                var entityRef = result.getEntityRef();
                
                initialDataParser.pushHandler(new ContactMechanismHandler(initialDataParser, this, partyName, contactMechanismName, entityRef));
            }
        } else if(localName.equals("contactPostalAddress")) {
            var form = ContactFormFactory.getCreateContactPostalAddressForm();
            
            String personalTitleId = null;
            String firstName = null;
            String middleName = null;
            String lastName = null;
            String nameSuffixId = null;
            String companyName = null;
            String attention = null;
            String address1 = null;
            String address2 = null;
            String address3 = null;
            String city = null;
            String state = null;
            String postalCode = null;
            String countryName = null;
            String isCommercial = null;
            String allowSolicitation = null;
            String description = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("personalTitle")) {
                    var personalTitle = attrs.getValue(i);
                    
                    if(personalTitle != null)
                        personalTitleId = (String)initialDataParser.getPersonalTitles().get(personalTitle);
                } else if(attrs.getQName(i).equals("firstName"))
                    firstName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("middleName"))
                    middleName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("lastName"))
                    lastName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("nameSuffix")) {
                    var nameSuffix = attrs.getValue(i);
                    
                    if(nameSuffix != null)
                        nameSuffixId = (String)initialDataParser.getNameSuffixes().get(nameSuffix);
                } else if(attrs.getQName(i).equals("companyName"))
                    companyName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("attention"))
                    companyName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("address1"))
                    address1 = attrs.getValue(i);
                else if(attrs.getQName(i).equals("address2"))
                    address2 = attrs.getValue(i);
                else if(attrs.getQName(i).equals("address3"))
                    address3 = attrs.getValue(i);
                else if(attrs.getQName(i).equals("city"))
                    city = attrs.getValue(i);
                else if(attrs.getQName(i).equals("state"))
                    state = attrs.getValue(i);
                else if(attrs.getQName(i).equals("postalCode"))
                    postalCode = attrs.getValue(i);
                else if(attrs.getQName(i).equals("countryName"))
                    countryName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("isCommercial"))
                    isCommercial = attrs.getValue(i);
                else if(attrs.getQName(i).equals("allowSolicitation"))
                    allowSolicitation = attrs.getValue(i);
                else if(attrs.getQName(i).equals("description"))
                    description = attrs.getValue(i);
            }
            
            form.setPartyName(partyName);
            form.setPersonalTitleId(personalTitleId);
            form.setFirstName(firstName);
            form.setMiddleName(middleName);
            form.setLastName(lastName);
            form.setNameSuffixId(nameSuffixId);
            form.setCompanyName(companyName);
            form.setAttention(attention);
            form.setAddress1(address1);
            form.setAddress2(address2);
            form.setAddress3(address3);
            form.setCity(city);
            form.setState(state);
            form.setPostalCode(postalCode);
            form.setCountryName(countryName);
            form.setIsCommercial(isCommercial);
            form.setAllowSolicitation(allowSolicitation);
            form.setDescription(description);

            var commandResult = contactService.createContactPostalAddress(initialDataParser.getUserVisit(), form);
            
            if(commandResult.hasErrors()) {
                System.err.println(commandResult);
            } else {
                var executionResult = commandResult.getExecutionResult();
                var result = (CreateContactPostalAddressResult)executionResult.getResult();
                var contactMechanismName = result.getContactMechanismName();
                var entityRef = result.getEntityRef();
                
                initialDataParser.pushHandler(new ContactMechanismHandler(initialDataParser, this, partyName, contactMechanismName, entityRef));
            }
        } else if(localName.equals("contactTelephone")) {
            var form = ContactFormFactory.getCreateContactTelephoneForm();
            
            String countryName = null;
            String areaCode = null;
            String telephoneNumber = null;
            String telephoneExtension = null;
            String allowSolicitation = null;
            String description = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("countryName"))
                    countryName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("areaCode"))
                    areaCode = attrs.getValue(i);
                else if(attrs.getQName(i).equals("telephoneNumber"))
                    telephoneNumber = attrs.getValue(i);
                else if(attrs.getQName(i).equals("telephoneExtension"))
                    telephoneExtension = attrs.getValue(i);
                else if(attrs.getQName(i).equals("allowSolicitation"))
                    allowSolicitation = attrs.getValue(i);
                else if(attrs.getQName(i).equals("description"))
                    description = attrs.getValue(i);
            }
            
            form.setPartyName(partyName);
            form.setCountryName(countryName);
            form.setAreaCode(areaCode);
            form.setTelephoneNumber(telephoneNumber);
            form.setTelephoneExtension(telephoneExtension);
            form.setAllowSolicitation(allowSolicitation);
            form.setDescription(description);

            var commandResult = contactService.createContactTelephone(initialDataParser.getUserVisit(), form);
            
            if(commandResult.hasErrors()) {
                System.err.println(commandResult);
            } else {
                var executionResult = commandResult.getExecutionResult();
                var result = (CreateContactTelephoneResult)executionResult.getResult();
                var contactMechanismName = result.getContactMechanismName();
                var entityRef = result.getEntityRef();
                
                initialDataParser.pushHandler(new ContactMechanismHandler(initialDataParser, this, partyName, contactMechanismName, entityRef));
            }
        } else if(localName.equals("contactWebAddress")) {
            var form = ContactFormFactory.getCreateContactWebAddressForm();
            
            String url = null;
            String description = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("url"))
                    url = attrs.getValue(i);
                else if(attrs.getQName(i).equals("description"))
                    description = attrs.getValue(i);
            }
            
            form.setPartyName(partyName);
            form.setUrl(url);
            form.setDescription(description);

            var commandResult = contactService.createContactWebAddress(initialDataParser.getUserVisit(), form);
            
            if(commandResult.hasErrors()) {
                System.err.println(commandResult);
            } else {
                var executionResult = commandResult.getExecutionResult();
                var result = (CreateContactWebAddressResult)executionResult.getResult();
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
