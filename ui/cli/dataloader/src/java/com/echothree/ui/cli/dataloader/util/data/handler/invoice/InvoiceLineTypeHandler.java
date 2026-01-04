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

package com.echothree.ui.cli.dataloader.util.data.handler.invoice;

import com.echothree.control.user.invoice.common.InvoiceUtil;
import com.echothree.control.user.invoice.common.InvoiceService;
import com.echothree.control.user.invoice.common.form.InvoiceFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class InvoiceLineTypeHandler
        extends BaseHandler {
    InvoiceService invoiceService;
    String invoiceTypeName;
    String invoiceLineTypeName;
    
    /** Creates a new instance of InvoiceLineTypeHandler */
    public InvoiceLineTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String invoiceTypeName,
            String invoiceLineTypeName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            invoiceService = InvoiceUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
        
        this.invoiceTypeName = invoiceTypeName;
        this.invoiceLineTypeName = invoiceLineTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("invoiceLineTypeDescription")) {
            var commandForm = InvoiceFormFactory.getCreateInvoiceLineTypeDescriptionForm();
            
            commandForm.setInvoiceTypeName(invoiceTypeName);
            commandForm.setInvoiceLineTypeName(invoiceLineTypeName);
            commandForm.set(getAttrsMap(attrs));
            
            checkCommandResult(invoiceService.createInvoiceLineTypeDescription(initialDataParser.getUserVisit(), commandForm));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("invoiceLineType")) {
            initialDataParser.popHandler();
        }
    }
    
}
