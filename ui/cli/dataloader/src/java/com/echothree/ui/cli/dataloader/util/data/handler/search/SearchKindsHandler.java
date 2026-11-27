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

package com.echothree.ui.cli.dataloader.util.data.handler.search;

import com.echothree.control.user.search.common.SearchService;
import com.echothree.control.user.search.common.SearchUtil;
import com.echothree.control.user.search.common.form.SearchFormFactory;
import com.echothree.control.user.search.common.spec.SearchSpecFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import com.echothree.util.common.message.ExecutionErrors;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class SearchKindsHandler
        extends BaseHandler {

    SearchService searchService;

    /** Creates a new instance of SearchKindsHandler */
    public SearchKindsHandler(InitialDataParser initialDataParser, BaseHandler parentHandler)
            throws SAXException {
        super(initialDataParser, parentHandler);

        try {
            searchService = SearchUtil.getHome();
        } catch(NamingException ne) {
            throw new SAXException(ne);
        }
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("searchKind")) {
            var attrsMap = getAttrsMap(attrs);

            var outcome = editOrCreate(attrsMap, SearchSpecFactory.getSearchKindSpec(),
                    SearchFormFactory.getEditSearchKindForm(), searchService::editSearchKind,
                    SearchFormFactory.getCreateSearchKindForm(), searchService::createSearchKind,
                    ExecutionErrors.UnknownSearchKindName);

            if(outcome.successful()) {
                var searchKindName = (String)attrsMap.get("SearchKindName");
                initialDataParser.pushHandler(new SearchKindHandler(initialDataParser, this, searchKindName));
            } else {
                skipCurrentElement();
            }
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("searchKinds")) {
            initialDataParser.popHandler();
        }
    }
}