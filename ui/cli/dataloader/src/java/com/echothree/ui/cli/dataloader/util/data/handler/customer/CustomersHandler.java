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

import com.echothree.control.user.party.common.PartyService;
import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.form.PartyFormFactory;
import com.echothree.control.user.party.common.result.CreateCustomerResult;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class CustomersHandler
        extends BaseHandler {

    PartyService partyService = PartyUtil.getHome();
    
    /** Creates a new instance of CustomersHandler */
    public CustomersHandler(InitialDataParser initialDataParser, BaseHandler parentHandler)
            throws NamingException {
        super(initialDataParser, parentHandler);
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException, NamingException {
        if(localName.equals("customerWithLogin")) {
            var form = PartyFormFactory.getCreateCustomerWithLoginForm();
            
            form.set(getAttrsMap(attrs));

            var commandResult = partyService.createCustomerWithLogin(initialDataParser.getUserVisit(), form);
            
            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (CreateCustomerResult)executionResult.getResult();
                var partyName = result.getPartyName();
                var customerName = result.getCustomerName();
                var entityRef = result.getEntityRef();
                
                initialDataParser.pushHandler(new CustomerHandler(initialDataParser, this, partyName, customerName, entityRef));
            }
        } else if(localName.equals("customer")) {
            var form = PartyFormFactory.getCreateCustomerForm();
            
            form.set(getAttrsMap(attrs));

            var commandResult = partyService.createCustomer(initialDataParser.getUserVisit(), form);
            
            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (CreateCustomerResult)executionResult.getResult();
                var partyName = result.getPartyName();
                var customerName = result.getCustomerName();
                var entityRef = result.getEntityRef();
                
                initialDataParser.pushHandler(new CustomerHandler(initialDataParser, this, partyName, customerName, entityRef));
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("customers")) {
            initialDataParser.popHandler();
        }
    }
    
}
