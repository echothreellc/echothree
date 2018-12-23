// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.PartyService;
import com.echothree.control.user.party.common.form.CreateCustomerForm;
import com.echothree.control.user.party.common.form.CreateCustomerWithLoginForm;
import com.echothree.control.user.party.common.form.PartyFormFactory;
import com.echothree.control.user.party.common.result.CreateCustomerResult;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class CustomersHandler
        extends BaseHandler {
    PartyService partyService;
    
    /** Creates a new instance of CustomersHandler */
    public CustomersHandler(InitialDataParser initialDataParser, BaseHandler parentHandler) {
        super(initialDataParser, parentHandler);
        
        try {
            partyService = PartyUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("customerWithLogin")) {
            CreateCustomerWithLoginForm form = PartyFormFactory.getCreateCustomerWithLoginForm();
            
            form.set(getAttrsMap(attrs));
            
            CommandResult commandResult = partyService.createCustomerWithLogin(initialDataParser.getUserVisit(), form);
            
            if(!commandResult.hasErrors()) {
                ExecutionResult executionResult = commandResult.getExecutionResult();
                CreateCustomerResult result = (CreateCustomerResult)executionResult.getResult();
                String partyName = result.getPartyName();
                String customerName = result.getCustomerName();
                String entityRef = result.getEntityRef();
                
                initialDataParser.pushHandler(new CustomerHandler(initialDataParser, this, partyName, customerName, entityRef));
            }
        } else if(localName.equals("customer")) {
            CreateCustomerForm form = PartyFormFactory.getCreateCustomerForm();
            
            form.set(getAttrsMap(attrs));
            
            CommandResult commandResult = partyService.createCustomer(initialDataParser.getUserVisit(), form);
            
            if(!commandResult.hasErrors()) {
                ExecutionResult executionResult = commandResult.getExecutionResult();
                CreateCustomerResult result = (CreateCustomerResult)executionResult.getResult();
                String partyName = result.getPartyName();
                String customerName = result.getCustomerName();
                String entityRef = result.getEntityRef();
                
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
