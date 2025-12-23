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

public class PaymentProcessorTypeCodeHandler
        extends BaseHandler {

    PaymentService paymentService;

    String paymentProcessorTypeName;
    String paymentProcessorTypeCodeTypeName;
    String paymentProcessorTypeCodeName;

    /** Creates a new instance of PaymentProcessorTypeCodeTypeHandler */
    public PaymentProcessorTypeCodeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler,
            String paymentProcessorTypeName, String paymentProcessorTypeCodeTypeName, String paymentProcessorTypeCodeName)
            throws NamingException {
        super(initialDataParser, parentHandler);
        
        paymentService = PaymentUtil.getHome();

        this.paymentProcessorTypeName = paymentProcessorTypeName;
        this.paymentProcessorTypeCodeTypeName = paymentProcessorTypeCodeTypeName;
        this.paymentProcessorTypeCodeName = paymentProcessorTypeCodeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("paymentProcessorTypeCodeDescription")) {
            var commandForm = PaymentFormFactory.getCreatePaymentProcessorTypeCodeDescriptionForm();

            commandForm.setPaymentProcessorTypeName(paymentProcessorTypeName);
            commandForm.setPaymentProcessorTypeCodeTypeName(paymentProcessorTypeCodeTypeName);
            commandForm.setPaymentProcessorTypeCodeName(paymentProcessorTypeCodeName);
            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(paymentService.createPaymentProcessorTypeCodeDescription(initialDataParser.getUserVisit(), commandForm));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("paymentProcessorTypeCode")) {
            initialDataParser.popHandler();
        }
    }
    
}
