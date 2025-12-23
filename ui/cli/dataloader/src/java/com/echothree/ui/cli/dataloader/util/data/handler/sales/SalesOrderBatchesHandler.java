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

package com.echothree.ui.cli.dataloader.util.data.handler.sales;

import com.echothree.control.user.sales.common.SalesUtil;
import com.echothree.control.user.sales.common.SalesService;
import com.echothree.control.user.sales.common.form.SalesFormFactory;
import com.echothree.control.user.sales.common.result.CreateSalesOrderBatchResult;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class SalesOrderBatchesHandler
        extends BaseHandler {

    SalesService salesService;
    
    /** Creates a new instance of SalesOrderBatchesHandler */
    public SalesOrderBatchesHandler(InitialDataParser initialDataParser, BaseHandler parentHandler) {
        super(initialDataParser, parentHandler);
        
        try {
            salesService = SalesUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("salesOrderBatch")) {
            var commandForm = SalesFormFactory.getCreateSalesOrderBatchForm();

            commandForm.set(getAttrsMap(attrs));

            var commandResult = salesService.createSalesOrderBatch(initialDataParser.getUserVisit(), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (CreateSalesOrderBatchResult)executionResult.getResult();
                var batchName = result.getBatchName();

                // TODO
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("salesOrderBatches")) {
            initialDataParser.popHandler();
        }
    }
    
}
