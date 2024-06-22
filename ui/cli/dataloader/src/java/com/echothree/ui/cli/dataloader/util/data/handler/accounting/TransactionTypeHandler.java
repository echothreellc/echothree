// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

import com.echothree.control.user.accounting.common.AccountingUtil;
import com.echothree.control.user.accounting.common.AccountingService;
import com.echothree.control.user.accounting.common.form.AccountingFormFactory;
import com.echothree.control.user.accounting.common.form.CreateTransactionEntityRoleTypeForm;
import com.echothree.control.user.accounting.common.form.CreateTransactionGlAccountCategoryForm;
import com.echothree.control.user.accounting.common.form.CreateTransactionTypeDescriptionForm;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class TransactionTypeHandler
        extends BaseHandler {
    AccountingService accountingService;
    String transactionTypeName;
    
    /** Creates a new instance of TransactionTypeHandler */
    public TransactionTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String transactionTypeName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            accountingService = AccountingUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
        
        this.transactionTypeName = transactionTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("transactionTypeDescription")) {
            CreateTransactionTypeDescriptionForm commandForm = AccountingFormFactory.getCreateTransactionTypeDescriptionForm();
            
            commandForm.setTransactionTypeName(transactionTypeName);
            commandForm.set(getAttrsMap(attrs));
            
            accountingService.createTransactionTypeDescription(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("transactionGlAccountCategory")) {
            CreateTransactionGlAccountCategoryForm commandForm = AccountingFormFactory.getCreateTransactionGlAccountCategoryForm();
            
            commandForm.setTransactionTypeName(transactionTypeName);
            commandForm.set(getAttrsMap(attrs));
            
            accountingService.createTransactionGlAccountCategory(initialDataParser.getUserVisit(), commandForm);
            
            initialDataParser.pushHandler(new TransactionGlAccountCategoryHandler(initialDataParser, this, transactionTypeName,
                    commandForm.getTransactionGlAccountCategoryName()));
        } else if(localName.equals("transactionEntityRoleType")) {
            CreateTransactionEntityRoleTypeForm commandForm = AccountingFormFactory.getCreateTransactionEntityRoleTypeForm();
            
            commandForm.setTransactionTypeName(transactionTypeName);
            commandForm.set(getAttrsMap(attrs));
            
            accountingService.createTransactionEntityRoleType(initialDataParser.getUserVisit(), commandForm);
            
            initialDataParser.pushHandler(new TransactionEntityRoleTypeHandler(initialDataParser, this,
                    transactionTypeName, commandForm.getTransactionEntityRoleTypeName()));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("transactionType")) {
            initialDataParser.popHandler();
        }
    }
    
}
