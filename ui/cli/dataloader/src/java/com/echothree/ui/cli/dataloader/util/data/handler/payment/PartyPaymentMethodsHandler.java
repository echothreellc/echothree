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

import com.echothree.control.user.payment.common.PaymentUtil;
import com.echothree.control.user.payment.common.PaymentService;
import com.echothree.control.user.payment.common.form.PaymentFormFactory;
import com.echothree.control.user.payment.common.result.CreatePartyPaymentMethodResult;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class PartyPaymentMethodsHandler
        extends BaseHandler {
    PaymentService paymentService;
    String partyName;
    String contactMechanismName;
    
    /** Creates a new instance of PartyPaymentMethodsHandler */
    public PartyPaymentMethodsHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String partyName,
            String contactMechanismName) {
        super(initialDataParser, parentHandler);
        
        try {
            paymentService = PaymentUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.partyName = partyName;
        this.contactMechanismName = contactMechanismName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("partyPaymentMethod")) {
            var commandForm = PaymentFormFactory.getCreatePartyPaymentMethodForm();
            
            commandForm.setPartyName(partyName);
            commandForm.setBillingContactMechanismName(contactMechanismName);
            commandForm.set(getAttrsMap(attrs));

            var commandResult = paymentService.createPartyPaymentMethod(initialDataParser.getUserVisit(), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (CreatePartyPaymentMethodResult)executionResult.getResult();
                var entityRef = result.getEntityRef();

                initialDataParser.pushHandler(new PartyPaymentMethodHandler(initialDataParser, this, entityRef));
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("partyPaymentMethods")) {
            initialDataParser.popHandler();
        }
    }
    
}
