// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.ui.cli.dataloader.data.handler.payment;

import com.echothree.control.user.payment.common.PaymentService;
import com.echothree.control.user.payment.common.PaymentUtil;
import com.echothree.control.user.payment.common.form.CreatePaymentProcessorForm;
import com.echothree.control.user.payment.common.form.PaymentFormFactory;
import com.echothree.control.user.payment.common.result.CreatePaymentProcessorResult;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class PaymentProcessorsHandler
        extends BaseHandler {
    PaymentService paymentService;
    
    /** Creates a new instance of PaymentProcessorsHandler */
    public PaymentProcessorsHandler(InitialDataParser initialDataParser, BaseHandler parentHandler)
            throws NamingException {
        super(initialDataParser, parentHandler);
        
        paymentService = PaymentUtil.getHome();
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException, NamingException {
        if(localName.equals("paymentProcessor")) {
            CreatePaymentProcessorForm commandForm = PaymentFormFactory.getCreatePaymentProcessorForm();
            
            commandForm.set(getAttrsMap(attrs));
            
            CommandResult commandResult = paymentService.createPaymentProcessor(initialDataParser.getUserVisit(), commandForm);

            if(!commandResult.hasErrors()) {
                ExecutionResult executionResult = commandResult.getExecutionResult();
                CreatePaymentProcessorResult result = (CreatePaymentProcessorResult)executionResult.getResult();
                String entityRef = result.getEntityRef();

                initialDataParser.pushHandler(new PaymentProcessorHandler(initialDataParser, this, commandForm.getPaymentProcessorName(), entityRef));
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("paymentProcessors")) {
            initialDataParser.popHandler();
        }
    }
    
}
