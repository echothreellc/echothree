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
import com.echothree.control.user.purchase.common.result.CreatePurchaseInvoiceLineResult;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.comment.CommentsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.core.EntityAttributesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.tag.EntityTagsHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class PurchaseInvoiceHandler
        extends BaseHandler {
    PurchaseService purchaseService;
    String invoiceName;
    String entityRef;
    
    /** Creates a new instance of PurchaseInvoiceHandler */
    public PurchaseInvoiceHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String invoiceName, String entityRef)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            purchaseService = PurchaseUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
        
        this.invoiceName = invoiceName;
        this.entityRef = entityRef;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("purchaseInvoiceLine")) {
            var form = PurchaseFormFactory.getCreatePurchaseInvoiceLineForm();
            
            form.setInvoiceName(invoiceName);
            form.set(getAttrsMap(attrs));

            var commandResult = purchaseService.createPurchaseInvoiceLine(initialDataParser.getUserVisit(), form);
            
            if(commandResult.hasErrors()) {
                System.err.println(commandResult);
            } else {
                var executionResult = commandResult.getExecutionResult();
                var result = (CreatePurchaseInvoiceLineResult)executionResult.getResult();
                var entityRef = result.getEntityRef();
                
                initialDataParser.pushHandler(new PurchaseInvoiceLineHandler(initialDataParser, this, entityRef));
            }
        } else if(localName.equals("comments")) {
            initialDataParser.pushHandler(new CommentsHandler(initialDataParser, this, entityRef));
        } else if(localName.equals("entityAttributes")) {
            initialDataParser.pushHandler(new EntityAttributesHandler(initialDataParser, this, entityRef));
        } else if(localName.equals("entityTags")) {
            initialDataParser.pushHandler(new EntityTagsHandler(initialDataParser, this, entityRef));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("purchaseInvoice")) {
            initialDataParser.popHandler();
        }
    }
    
}
