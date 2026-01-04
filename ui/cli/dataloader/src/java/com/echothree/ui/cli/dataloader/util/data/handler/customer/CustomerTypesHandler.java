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

import com.echothree.control.user.customer.common.CustomerService;
import com.echothree.control.user.customer.common.CustomerUtil;
import com.echothree.control.user.customer.common.form.CustomerFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class CustomerTypesHandler
        extends BaseHandler {

    CustomerService customerService = CustomerUtil.getHome();
    
    /** Creates a new instance of CustomerTypesHandler */
    public CustomerTypesHandler(InitialDataParser initialDataParser, BaseHandler parentHandler)
            throws SAXException, NamingException {
        super(initialDataParser, parentHandler);
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException, NamingException {
        if(localName.equals("customerType")) {
            var commandForm = CustomerFormFactory.getCreateCustomerTypeForm();
            
            commandForm.set(getAttrsMap(attrs));
            
            customerService.createCustomerType(initialDataParser.getUserVisit(), commandForm);
            
            initialDataParser.pushHandler(new CustomerTypeHandler(initialDataParser, this, commandForm.getCustomerTypeName()));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("customerTypes")) {
            initialDataParser.popHandler();
        }
    }
    
}
