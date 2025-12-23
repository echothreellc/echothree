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

package com.echothree.ui.cli.dataloader.util.data.handler.selector;

import com.echothree.control.user.selector.common.SelectorUtil;
import com.echothree.control.user.selector.common.SelectorService;
import com.echothree.control.user.selector.common.form.SelectorFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class SelectorKindsHandler
        extends BaseHandler {
    SelectorService selectorService;
    
    /** Creates a new instance of SelectorKindsHandler */
    public SelectorKindsHandler(InitialDataParser initialDataParser, BaseHandler parentHandler)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            selectorService = SelectorUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("selectorKind")) {
            var commandForm = SelectorFormFactory.getCreateSelectorKindForm();

            commandForm.set(getAttrsMap(attrs));

            selectorService.createSelectorKind(initialDataParser.getUserVisit(), commandForm);

            initialDataParser.pushHandler(new SelectorKindHandler(initialDataParser, this, commandForm.getSelectorKindName()));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("selectorKinds")) {
            initialDataParser.popHandler();
        }
    }
    
}
