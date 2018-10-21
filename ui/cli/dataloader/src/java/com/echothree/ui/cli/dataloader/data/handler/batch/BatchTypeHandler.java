// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.ui.cli.dataloader.data.handler.batch;

import com.echothree.control.user.batch.common.BatchUtil;
import com.echothree.control.user.batch.remote.BatchService;
import com.echothree.control.user.batch.remote.form.BatchFormFactory;
import com.echothree.control.user.batch.remote.form.CreateBatchTypeDescriptionForm;
import com.echothree.control.user.batch.remote.form.CreateBatchTypeEntityTypeForm;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class BatchTypeHandler
        extends BaseHandler {
    BatchService batchService;
    String batchTypeName;
    
    /** Creates a new instance of BatchTypeHandler */
    public BatchTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String batchTypeName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            batchService = BatchUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
        
        this.batchTypeName = batchTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("batchTypeDescription")) {
            CreateBatchTypeDescriptionForm commandForm = BatchFormFactory.getCreateBatchTypeDescriptionForm();
            
            commandForm.setBatchTypeName(batchTypeName);
            commandForm.set(getAttrsMap(attrs));
            
            batchService.createBatchTypeDescription(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("batchTypeEntityType")) {
            CreateBatchTypeEntityTypeForm commandForm = BatchFormFactory.getCreateBatchTypeEntityTypeForm();

            commandForm.setBatchTypeName(batchTypeName);
            commandForm.set(getAttrsMap(attrs));

            batchService.createBatchTypeEntityType(initialDataParser.getUserVisit(), commandForm);
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("batchType")) {
            initialDataParser.popHandler();
        }
    }
    
}
