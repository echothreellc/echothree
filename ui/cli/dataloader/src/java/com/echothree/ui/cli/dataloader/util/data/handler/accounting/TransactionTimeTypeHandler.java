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

package com.echothree.ui.cli.dataloader.util.data.handler.accounting;

import com.echothree.control.user.accounting.common.AccountingService;
import com.echothree.control.user.accounting.common.AccountingUtil;
import com.echothree.control.user.accounting.common.form.AccountingFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class TransactionTimeTypeHandler
        extends BaseHandler {

    AccountingService accountingService;
    String transactionTimeTypeName;
    
    /** Creates a new instance of TransactionTimeTypeHandler */
    public TransactionTimeTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String transactionTimeTypeName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            accountingService = AccountingUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
        
        this.transactionTimeTypeName = transactionTimeTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("transactionTimeTypeDescription")) {
            var commandForm = AccountingFormFactory.getCreateTransactionTimeTypeDescriptionForm();
            
            commandForm.setTransactionTimeTypeName(transactionTimeTypeName);
            commandForm.set(getAttrsMap(attrs));
            
            accountingService.createTransactionTimeTypeDescription(initialDataParser.getUserVisit(), commandForm);
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("transactionTimeType")) {
            initialDataParser.popHandler();
        }
    }
    
}
