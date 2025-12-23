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

package com.echothree.ui.cli.dataloader.util.data.handler.chain;

import com.echothree.control.user.chain.common.ChainUtil;
import com.echothree.control.user.chain.common.ChainService;
import com.echothree.control.user.chain.common.form.ChainFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ChainKindHandler
        extends BaseHandler {

    ChainService chainService;
    String chainKindName;

    /** Creates a new instance of ChainKindHandler */
    public ChainKindHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String chainKindName)
            throws SAXException {
        super(initialDataParser, parentHandler);

        try {
            chainService = ChainUtil.getHome();
        } catch(NamingException ne) {
            throw new SAXException(ne);
        }

        this.chainKindName = chainKindName;
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("chainKindDescription")) {
            var commandForm = ChainFormFactory.getCreateChainKindDescriptionForm();

            commandForm.setChainKindName(chainKindName);
            commandForm.set(getAttrsMap(attrs));

            chainService.createChainKindDescription(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("chainActionTypeUse")) {
            var commandForm = ChainFormFactory.getCreateChainActionTypeUseForm();

            commandForm.setChainKindName(chainKindName);
            commandForm.set(getAttrsMap(attrs));

            chainService.createChainActionTypeUse(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("chainType")) {
            var commandForm = ChainFormFactory.getCreateChainTypeForm();

            commandForm.setChainKindName(chainKindName);
            commandForm.set(getAttrsMap(attrs));

            chainService.createChainType(initialDataParser.getUserVisit(), commandForm);

            initialDataParser.pushHandler(new ChainTypeHandler(initialDataParser, this, chainKindName, commandForm.getChainTypeName()));
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("chainKind")) {
            initialDataParser.popHandler();
        }
    }
}