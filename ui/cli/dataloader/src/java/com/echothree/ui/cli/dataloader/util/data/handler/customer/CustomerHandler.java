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

package com.echothree.ui.cli.dataloader.util.data.handler.customer;

import com.echothree.control.user.associate.common.AssociateService;
import com.echothree.control.user.associate.common.AssociateUtil;
import com.echothree.control.user.associate.common.form.AssociateFormFactory;
import com.echothree.control.user.party.common.PartyService;
import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.form.PartyFormFactory;
import com.echothree.control.user.subscription.common.SubscriptionService;
import com.echothree.control.user.subscription.common.SubscriptionUtil;
import com.echothree.control.user.subscription.common.form.SubscriptionFormFactory;
import com.echothree.control.user.term.common.TermService;
import com.echothree.control.user.term.common.TermUtil;
import com.echothree.control.user.term.common.form.TermFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.associate.AssociateHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.comment.CommentsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.contact.ContactMechanismsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.core.EntityAttributesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.tag.EntityTagsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.wishlist.WishlistsHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class CustomerHandler
        extends BaseHandler {

    AssociateService associateService = AssociateUtil.getHome();
    PartyService partyService = PartyUtil.getHome();
    SubscriptionService subscriptionService = SubscriptionUtil.getHome();
    TermService termService = TermUtil.getHome();

    String partyName;
    String customerName;
    String entityRef;
    
    /** Creates a new instance of CustomerHandler */
    public CustomerHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String partyName, String customerName, String entityRef)
            throws NamingException {
        super(initialDataParser, parentHandler);

        this.partyName = partyName;
        this.customerName = customerName;
        this.entityRef = entityRef;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("partyAlias")) {
            var commandForm = PartyFormFactory.getCreatePartyAliasForm();

            commandForm.setPartyName(partyName);
            commandForm.set(getAttrsMap(attrs));

            partyService.createPartyAlias(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("partyCreditLimit")) {
            var commandForm = TermFormFactory.getCreatePartyCreditLimitForm();
            
            commandForm.setPartyName(partyName);
            commandForm.set(getAttrsMap(attrs));
            
            termService.createPartyCreditLimit(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("subscription")) {
            var commandForm = SubscriptionFormFactory.getCreateSubscriptionForm();
            
            commandForm.setPartyName(partyName);
            commandForm.set(getAttrsMap(attrs));
            
            subscriptionService.createSubscription(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("associate")) {
            var commandForm = AssociateFormFactory.getCreateAssociateForm();
            
            commandForm.setPartyName(partyName);
            commandForm.set(getAttrsMap(attrs));
            
            associateService.createAssociate(initialDataParser.getUserVisit(), commandForm);

            initialDataParser.pushHandler(new AssociateHandler(initialDataParser, this, commandForm.getAssociateProgramName(),commandForm.getAssociateName()));
        } else if(localName.equals("profile")) {
            var commandForm = PartyFormFactory.getCreateProfileForm();
            
            commandForm.setPartyName(partyName);
            commandForm.set(getAttrsMap(attrs));
            
            partyService.createProfile(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("wishlists")) {
            initialDataParser.pushHandler(new WishlistsHandler(initialDataParser, this, partyName));
        } else if(localName.equals("contactMechanisms")) {
            initialDataParser.pushHandler(new ContactMechanismsHandler(initialDataParser, this, partyName));
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
        if(localName.equals("customer") || localName.equals("customerWithLogin")) {
            initialDataParser.popHandler();
        }
    }
    
}
