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

package com.echothree.ui.cli.dataloader.util.data.handler.invoice;

import com.echothree.control.user.invoice.common.InvoiceUtil;
import com.echothree.control.user.invoice.common.InvoiceService;
import com.echothree.control.user.invoice.common.form.InvoiceFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class InvoiceTimeTypeHandler
        extends BaseHandler {
    
    InvoiceService invoiceService;
    String invoiceTypeName;
    String invoiceTimeTypeName;
    
    /** Creates a new instance of InvoiceTimeTypeHandler */
    public InvoiceTimeTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String invoiceTypeName, String invoiceTimeTypeName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            invoiceService = InvoiceUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }

        this.invoiceTypeName = invoiceTypeName;
        this.invoiceTimeTypeName = invoiceTimeTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("invoiceTimeTypeDescription")) {
            var commandForm = InvoiceFormFactory.getCreateInvoiceTimeTypeDescriptionForm();
            
            commandForm.setInvoiceTypeName(invoiceTypeName);
            commandForm.setInvoiceTimeTypeName(invoiceTimeTypeName);
            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(invoiceService.createInvoiceTimeTypeDescription(initialDataParser.getUserVisit(), commandForm));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("invoiceTimeType")) {
            initialDataParser.popHandler();
        }
    }
    
}
