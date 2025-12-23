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

package com.echothree.ui.cli.dataloader.util.data.handler.index;

import com.echothree.control.user.index.common.IndexUtil;
import com.echothree.control.user.index.common.IndexService;
import com.echothree.control.user.index.common.form.IndexFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class IndexHandler
        extends BaseHandler {
    
    IndexService indexService;
    String indexName;
    
    /** Creates a new instance of IndexHandler */
    public IndexHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String indexName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            indexService = IndexUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
        
        this.indexName = indexName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("indexDescription")) {
            var commandForm = IndexFormFactory.getCreateIndexDescriptionForm();

            commandForm.setIndexName(indexName);
            commandForm.set(getAttrsMap(attrs));

            indexService.createIndexDescription(initialDataParser.getUserVisit(), commandForm);
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("index")) {
            initialDataParser.popHandler();
        }
    }
    
}
