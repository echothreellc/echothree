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

public class SelectorHandler
        extends BaseHandler {
    SelectorService selectorService;
    String selectorKindName;
    String selectorTypeName;
    String selectorName;
    
    /** Creates a new instance of SelectorHandler */
    public SelectorHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String selectorKindName,
            String selectorTypeName, String selectorName) {
        super(initialDataParser, parentHandler);
        
        try {
            selectorService = SelectorUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.selectorKindName = selectorKindName;
        this.selectorTypeName = selectorTypeName;
        this.selectorName = selectorName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("selectorDescription")) {
            var commandForm = SelectorFormFactory.getCreateSelectorDescriptionForm();
            
            commandForm.setSelectorKindName(selectorKindName);
            commandForm.setSelectorTypeName(selectorTypeName);
            commandForm.setSelectorName(selectorName);
            commandForm.set(getAttrsMap(attrs));
            
            selectorService.createSelectorDescription(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("selectorNode")) {
            var commandForm = SelectorFormFactory.getCreateSelectorNodeForm();
            
            commandForm.setSelectorKindName(selectorKindName);
            commandForm.setSelectorTypeName(selectorTypeName);
            commandForm.setSelectorName(selectorName);
            commandForm.set(getAttrsMap(attrs));
            
            selectorService.createSelectorNode(initialDataParser.getUserVisit(), commandForm);
            
            initialDataParser.pushHandler(new SelectorNodeHandler(initialDataParser, this, selectorKindName, selectorTypeName,
                    selectorName, commandForm.getSelectorNodeName()));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("selector")) {
            initialDataParser.popHandler();
        }
    }
    
}
