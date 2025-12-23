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

package com.echothree.ui.cli.dataloader.util.data.handler.purchase;

import com.echothree.control.user.purchase.common.PurchaseUtil;
import com.echothree.control.user.purchase.common.PurchaseService;
import com.echothree.control.user.purchase.common.form.PurchaseFormFactory;
import com.echothree.control.user.purchase.common.result.CreatePurchaseInvoiceResult;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class PurchaseInvoicesHandler
        extends BaseHandler {
    PurchaseService purchaseService;
    String vendorName;
    String companyName;
    
    /** Creates a new instance of PurchaseInvoicesHandler */
    public PurchaseInvoicesHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String vendorName, String companyName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            purchaseService = PurchaseUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
        
        this.vendorName = vendorName;
        this.companyName = companyName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("purchaseInvoice")) {
            var form = PurchaseFormFactory.getCreatePurchaseInvoiceForm();
            
            form.setVendorName(vendorName);
            form.setCompanyName(companyName);
            form.set(getAttrsMap(attrs));

            var commandResult = purchaseService.createPurchaseInvoice(initialDataParser.getUserVisit(), form);
            
            if(commandResult.hasErrors()) {
                System.err.println(commandResult);
            } else {
                var executionResult = commandResult.getExecutionResult();
                var result = (CreatePurchaseInvoiceResult)executionResult.getResult();
                var invoiceName = result.getInvoiceName();
                var entityRef = result.getEntityRef();
                
                initialDataParser.pushHandler(new PurchaseInvoiceHandler(initialDataParser, this, invoiceName, entityRef));
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("purchaseInvoices")) {
            initialDataParser.popHandler();
        }
    }
    
}
