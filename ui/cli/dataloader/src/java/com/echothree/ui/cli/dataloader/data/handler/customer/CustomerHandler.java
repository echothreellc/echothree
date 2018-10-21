// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.ui.cli.dataloader.data.handler.customer;

import com.echothree.control.user.associate.common.AssociateUtil;
import com.echothree.control.user.associate.remote.AssociateService;
import com.echothree.control.user.associate.remote.form.AssociateFormFactory;
import com.echothree.control.user.associate.remote.form.CreateAssociateForm;
import com.echothree.control.user.customer.common.CustomerUtil;
import com.echothree.control.user.customer.remote.CustomerService;
import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.remote.PartyService;
import com.echothree.control.user.party.remote.form.CreatePartyAliasForm;
import com.echothree.control.user.party.remote.form.CreateProfileForm;
import com.echothree.control.user.party.remote.form.PartyFormFactory;
import com.echothree.control.user.subscription.common.SubscriptionUtil;
import com.echothree.control.user.subscription.remote.SubscriptionService;
import com.echothree.control.user.subscription.remote.form.CreateSubscriptionForm;
import com.echothree.control.user.subscription.remote.form.SubscriptionFormFactory;
import com.echothree.control.user.term.common.TermUtil;
import com.echothree.control.user.term.remote.TermService;
import com.echothree.control.user.term.remote.form.CreatePartyCreditLimitForm;
import com.echothree.control.user.term.remote.form.TermFormFactory;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import com.echothree.ui.cli.dataloader.data.handler.associate.AssociateHandler;
import com.echothree.ui.cli.dataloader.data.handler.comment.CommentsHandler;
import com.echothree.ui.cli.dataloader.data.handler.contact.ContactMechanismsHandler;
import com.echothree.ui.cli.dataloader.data.handler.core.EntityAttributesHandler;
import com.echothree.ui.cli.dataloader.data.handler.tag.EntityTagsHandler;
import com.echothree.ui.cli.dataloader.data.handler.wishlist.WishlistsHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class CustomerHandler
        extends BaseHandler {
    AssociateService associateService;
    CustomerService customerService;
    PartyService partyService;
    SubscriptionService subscriptionService;
    TermService termService;
    String partyName;
    String customerName;
    String entityRef;
    
    /** Creates a new instance of CustomerHandler */
    public CustomerHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String partyName, String customerName, String entityRef)
            throws SAXException{
        super(initialDataParser, parentHandler);
        
        try {
            associateService = AssociateUtil.getHome();
            customerService = CustomerUtil.getHome();
            partyService = PartyUtil.getHome();
            subscriptionService = SubscriptionUtil.getHome();
            termService = TermUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
        
        this.partyName = partyName;
        this.customerName = customerName;
        this.entityRef = entityRef;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("partyAlias")) {
            CreatePartyAliasForm commandForm = PartyFormFactory.getCreatePartyAliasForm();

            commandForm.setPartyName(partyName);
            commandForm.set(getAttrsMap(attrs));

            partyService.createPartyAlias(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("partyCreditLimit")) {
            CreatePartyCreditLimitForm commandForm = TermFormFactory.getCreatePartyCreditLimitForm();
            
            commandForm.setPartyName(partyName);
            commandForm.set(getAttrsMap(attrs));
            
            termService.createPartyCreditLimit(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("subscription")) {
            CreateSubscriptionForm commandForm = SubscriptionFormFactory.getCreateSubscriptionForm();
            
            commandForm.setPartyName(partyName);
            commandForm.set(getAttrsMap(attrs));
            
            subscriptionService.createSubscription(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("associate")) {
            CreateAssociateForm commandForm = AssociateFormFactory.getCreateAssociateForm();
            
            commandForm.setPartyName(partyName);
            commandForm.set(getAttrsMap(attrs));
            
            associateService.createAssociate(initialDataParser.getUserVisit(), commandForm);

            initialDataParser.pushHandler(new AssociateHandler(initialDataParser, this, commandForm.getAssociateProgramName(),commandForm.getAssociateName()));
        } else if(localName.equals("profile")) {
            CreateProfileForm commandForm = PartyFormFactory.getCreateProfileForm();
            
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
