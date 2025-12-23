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
import com.echothree.ui.cli.dataloader.util.data.handler.comment.CommentsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.core.EntityAttributesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.payment.PartyPaymentMethodsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.tag.EntityTagsHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ContactMechanismHandler
        extends BaseHandler {
    ContactService contactService;
    String partyName;
    String contactMechanismName;
    String entityRef;
    
    /** Creates a new instance of ContactMechanismHandler */
    public ContactMechanismHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String partyName,
            String contactMechanismName, String entityRef) {
        super(initialDataParser, parentHandler);
        
        try {
            contactService = ContactUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.partyName = partyName;
        this.contactMechanismName = contactMechanismName;
        this.entityRef = entityRef;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("partyContactMechanismPurpose")) {
            var form = ContactFormFactory.getCreatePartyContactMechanismPurposeForm();
            String contactMechanismPurposeName = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("contactMechanismPurposeName"))
                    contactMechanismPurposeName = attrs.getValue(i);
            }
            
            form.setPartyName(partyName);
            form.setContactMechanismName(contactMechanismName);
            form.setContactMechanismPurposeName(contactMechanismPurposeName);
            
            contactService.createPartyContactMechanismPurpose(initialDataParser.getUserVisit(), form);
        } else if(localName.equals("contactMechanismAlias")) {
            var form = ContactFormFactory.getCreateContactMechanismAliasForm();
            String contactMechanismAliasTypeName = null;
            String alias = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("contactMechanismAliasTypeName"))
                    contactMechanismAliasTypeName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("alias"))
                    alias = attrs.getValue(i);
            }
            
            form.setContactMechanismName(contactMechanismName);
            form.setContactMechanismAliasTypeName(contactMechanismAliasTypeName);
            form.setAlias(alias);
            
            contactService.createContactMechanismAlias(initialDataParser.getUserVisit(), form);
        } else if(localName.equals("partyContactMechanismAlias")) {
            var form = ContactFormFactory.getCreatePartyContactMechanismAliasForm();
            String contactMechanismAliasTypeName = null;
            String alias = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("contactMechanismAliasTypeName"))
                    contactMechanismAliasTypeName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("alias"))
                    alias = attrs.getValue(i);
            }
            
            form.setPartyName(partyName);
            form.setContactMechanismName(contactMechanismName);
            form.setContactMechanismAliasTypeName(contactMechanismAliasTypeName);
            form.setAlias(alias);
            
            contactService.createPartyContactMechanismAlias(initialDataParser.getUserVisit(), form);
        } else if(localName.equals("partyPaymentMethods")) {
            initialDataParser.pushHandler(new PartyPaymentMethodsHandler(initialDataParser, this, partyName, contactMechanismName));
        } else if(localName.equals("comments")) {
            initialDataParser.pushHandler(new CommentsHandler(initialDataParser, this, entityRef));
        } else if(localName.equals("entityAttributes")) {
            initialDataParser.pushHandler(new EntityAttributesHandler(initialDataParser, this, entityRef));
        } else if(localName.equals("entityTags")) {
            initialDataParser.pushHandler(new EntityTagsHandler(initialDataParser, this, entityRef));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("contactEmailAddress") || localName.equals("contactPostalAddress")
                || localName.equals("contactTelephone") || localName.equals("contactWebAddress")) {
            initialDataParser.popHandler();
        }
    }
    
}
