// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.ui.cli.dataloader.util.data.handler.payment;

import com.echothree.control.user.payment.common.PaymentUtil;
import com.echothree.control.user.payment.common.PaymentService;
import com.echothree.control.user.payment.common.form.PaymentFormFactory;
import com.echothree.control.user.payment.common.result.CreatePaymentMethodResult;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class PaymentMethodsHandler
        extends BaseHandler {
    PaymentService paymentService;
    
    /** Creates a new instance of PaymentMethodsHandler */
    public PaymentMethodsHandler(InitialDataParser initialDataParser, BaseHandler parentHandler) {
        super(initialDataParser, parentHandler);
        
        try {
            paymentService = PaymentUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("paymentMethod")) {
            var commandForm = PaymentFormFactory.getCreatePaymentMethodForm();
            
            commandForm.set(getAttrsMap(attrs));

            var commandResult = paymentService.createPaymentMethod(initialDataParser.getUserVisit(), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (CreatePaymentMethodResult)executionResult.getResult();
                var entityRef = result.getEntityRef();

                initialDataParser.pushHandler(new PaymentMethodHandler(initialDataParser, this, commandForm.getPaymentMethodName(), entityRef));
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("paymentMethods")) {
            initialDataParser.popHandler();
        }
    }
    
}
