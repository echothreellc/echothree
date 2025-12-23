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

package com.echothree.ui.cli.dataloader.util.data.handler.payment;

import com.echothree.control.user.payment.common.PaymentService;
import com.echothree.control.user.payment.common.PaymentUtil;
import com.echothree.control.user.payment.common.form.PaymentFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class PaymentProcessorTypeHandler
        extends BaseHandler {

    PaymentService paymentService;

    String paymentProcessorTypeName;
    
    /** Creates a new instance of PaymentProcessorTypeHandler */
    public PaymentProcessorTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String paymentProcessorTypeName)
            throws NamingException {
        super(initialDataParser, parentHandler);
        
        paymentService = PaymentUtil.getHome();

        this.paymentProcessorTypeName = paymentProcessorTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException, NamingException {
        if(localName.equals("paymentProcessorTypeCodeType")) {
            var commandForm = PaymentFormFactory.getCreatePaymentProcessorTypeCodeTypeForm();

            commandForm.setPaymentProcessorTypeName(paymentProcessorTypeName);
            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(paymentService.createPaymentProcessorTypeCodeType(initialDataParser.getUserVisit(), commandForm));

            initialDataParser.pushHandler(new PaymentProcessorTypeCodeTypeHandler(initialDataParser, this,
                    commandForm.getPaymentProcessorTypeName(), commandForm.getPaymentProcessorTypeCodeTypeName()));
        } else if(localName.equals("paymentProcessorTypeAction")) {
            var commandForm = PaymentFormFactory.getCreatePaymentProcessorTypeActionForm();

            commandForm.setPaymentProcessorTypeName(paymentProcessorTypeName);
            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(paymentService.createPaymentProcessorTypeAction(initialDataParser.getUserVisit(), commandForm));
        } else if(localName.equals("paymentProcessorTypeDescription")) {
            var commandForm = PaymentFormFactory.getCreatePaymentProcessorTypeDescriptionForm();

            commandForm.setPaymentProcessorTypeName(paymentProcessorTypeName);
            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(paymentService.createPaymentProcessorTypeDescription(initialDataParser.getUserVisit(), commandForm));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("paymentProcessorType")) {
            initialDataParser.popHandler();
        }
    }
    
}
