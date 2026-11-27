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

import com.echothree.control.user.index.common.IndexService;
import com.echothree.control.user.index.common.IndexUtil;
import com.echothree.control.user.index.common.form.IndexFormFactory;
import com.echothree.control.user.index.common.spec.IndexSpecFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import com.echothree.util.common.message.ExecutionErrors;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class IndexTypesHandler
        extends BaseHandler {
    
    IndexService indexService;
    
    /** Creates a new instance of IndexTypesHandler */
    public IndexTypesHandler(InitialDataParser initialDataParser, BaseHandler parentHandler)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            indexService = IndexUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("indexType")) {
            var attrsMap = getAttrsMap(attrs);
            // The XML loader treats omitted association fields as explicit clears.
            attrsMap.putIfAbsent("ComponentVendorName", null);
            attrsMap.putIfAbsent("EntityTypeName", null);

            var outcome = editOrCreate(attrsMap, IndexSpecFactory.getIndexTypeSpec(),
                    IndexFormFactory.getEditIndexTypeForm(), indexService::editIndexType,
                    IndexFormFactory.getCreateIndexTypeForm(), indexService::createIndexType,
                    ExecutionErrors.UnknownIndexTypeName);

            if(outcome.successful()) {
                var indexTypeName = (String)attrsMap.get("IndexTypeName");
                initialDataParser.pushHandler(new IndexTypeHandler(initialDataParser, this, indexTypeName));
            } else {
                skipCurrentElement();
            }
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("indexTypes")) {
            initialDataParser.popHandler();
        }
    }

}
