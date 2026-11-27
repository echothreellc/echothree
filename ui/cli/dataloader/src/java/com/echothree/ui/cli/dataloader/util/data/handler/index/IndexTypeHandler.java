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

public class IndexTypeHandler
        extends BaseHandler {
    
    IndexService indexService;
    String indexTypeName;
    
    /** Creates a new instance of IndexTypeHandler */
    public IndexTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String indexTypeName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            indexService = IndexUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
        
        this.indexTypeName = indexTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("indexTypeDescription")) {
            var attrsMap = getAttrsMap(attrs);
            attrsMap.putIfAbsent("IndexTypeName", indexTypeName);

            editOrCreate(attrsMap, IndexSpecFactory.getIndexTypeDescriptionSpec(),
                    IndexFormFactory.getEditIndexTypeDescriptionForm(), indexService::editIndexTypeDescription,
                    IndexFormFactory.getCreateIndexTypeDescriptionForm(), indexService::createIndexTypeDescription,
                    ExecutionErrors.UnknownIndexTypeDescription);
        } else if(localName.equals("indexField")) {
            var attrsMap = getAttrsMap(attrs);
            attrsMap.putIfAbsent("IndexTypeName", indexTypeName);

            var outcome = editOrCreate(attrsMap, IndexSpecFactory.getIndexFieldSpec(),
                    IndexFormFactory.getEditIndexFieldForm(), indexService::editIndexField,
                    IndexFormFactory.getCreateIndexFieldForm(), indexService::createIndexField,
                    ExecutionErrors.UnknownIndexFieldName);

            if(outcome.successful()) {
                var resolvedIndexTypeName = (String)attrsMap.get("IndexTypeName");
                var indexFieldName = (String)attrsMap.get("IndexFieldName");
                initialDataParser.pushHandler(new IndexFieldHandler(initialDataParser, this, resolvedIndexTypeName, indexFieldName));
            } else {
                skipCurrentElement();
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("indexType")) {
            initialDataParser.popHandler();
        }
    }
    
}
