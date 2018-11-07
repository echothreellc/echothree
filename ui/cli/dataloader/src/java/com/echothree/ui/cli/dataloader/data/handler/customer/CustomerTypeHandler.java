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

import com.echothree.control.user.contactlist.common.ContactListUtil;
import com.echothree.control.user.contactlist.common.ContactListService;
import com.echothree.control.user.contactlist.common.form.ContactListFormFactory;
import com.echothree.control.user.contactlist.common.form.CreateCustomerTypeContactListForm;
import com.echothree.control.user.contactlist.common.form.CreateCustomerTypeContactListGroupForm;
import com.echothree.control.user.customer.common.CustomerUtil;
import com.echothree.control.user.customer.common.CustomerService;
import com.echothree.control.user.customer.common.form.CreateCustomerTypeDescriptionForm;
import com.echothree.control.user.customer.common.form.CreateCustomerTypePaymentMethodForm;
import com.echothree.control.user.customer.common.form.CreateCustomerTypeShippingMethodForm;
import com.echothree.control.user.customer.common.form.CustomerFormFactory;
import com.echothree.control.user.offer.common.OfferUtil;
import com.echothree.control.user.offer.common.OfferService;
import com.echothree.control.user.offer.common.form.CreateOfferCustomerTypeForm;
import com.echothree.control.user.offer.common.form.OfferFormFactory;
import com.echothree.control.user.term.common.TermUtil;
import com.echothree.control.user.term.common.TermService;
import com.echothree.control.user.term.common.form.CreateCustomerTypeCreditLimitForm;
import com.echothree.control.user.term.common.form.TermFormFactory;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class CustomerTypeHandler
        extends BaseHandler {
    
    ContactListService contactListService;
    CustomerService customerService;
    OfferService offerService;
    TermService termService;
    
    String customerTypeName;
    
    /** Creates a new instance of CustomerTypeHandler */
    public CustomerTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String customerTypeName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            contactListService = ContactListUtil.getHome();
            customerService = CustomerUtil.getHome();
            offerService = OfferUtil.getHome();
            termService = TermUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
        
        this.customerTypeName = customerTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("customerTypeDescription")) {
            CreateCustomerTypeDescriptionForm commandForm = CustomerFormFactory.getCreateCustomerTypeDescriptionForm();
            
            commandForm.setCustomerTypeName(customerTypeName);
            commandForm.set(getAttrsMap(attrs));
            
            customerService.createCustomerTypeDescription(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("customerTypePaymentMethod")) {
            CreateCustomerTypePaymentMethodForm commandForm = CustomerFormFactory.getCreateCustomerTypePaymentMethodForm();
            
            commandForm.setCustomerTypeName(customerTypeName);
            commandForm.set(getAttrsMap(attrs));
            
            customerService.createCustomerTypePaymentMethod(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("customerTypeShippingMethod")) {
            CreateCustomerTypeShippingMethodForm commandForm = CustomerFormFactory.getCreateCustomerTypeShippingMethodForm();
            
            commandForm.setCustomerTypeName(customerTypeName);
            commandForm.set(getAttrsMap(attrs));
            
            customerService.createCustomerTypeShippingMethod(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("customerTypeCreditLimit")) {
            CreateCustomerTypeCreditLimitForm commandForm = TermFormFactory.getCreateCustomerTypeCreditLimitForm();
            
            commandForm.setCustomerTypeName(customerTypeName);
            commandForm.set(getAttrsMap(attrs));
            
            termService.createCustomerTypeCreditLimit(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("offerCustomerType")) {
            CreateOfferCustomerTypeForm commandForm = OfferFormFactory.getCreateOfferCustomerTypeForm();
            
            commandForm.setCustomerTypeName(customerTypeName);
            commandForm.set(getAttrsMap(attrs));
            
            offerService.createOfferCustomerType(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("customerTypeContactListGroup")) {
            CreateCustomerTypeContactListGroupForm commandForm = ContactListFormFactory.getCreateCustomerTypeContactListGroupForm();

            commandForm.setCustomerTypeName(customerTypeName);
            commandForm.set(getAttrsMap(attrs));

            contactListService.createCustomerTypeContactListGroup(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("customerTypeContactList")) {
            CreateCustomerTypeContactListForm commandForm = ContactListFormFactory.getCreateCustomerTypeContactListForm();

            commandForm.setCustomerTypeName(customerTypeName);
            commandForm.set(getAttrsMap(attrs));

            contactListService.createCustomerTypeContactList(initialDataParser.getUserVisit(), commandForm);
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("customerType")) {
            initialDataParser.popHandler();
        }
    }
    
}
