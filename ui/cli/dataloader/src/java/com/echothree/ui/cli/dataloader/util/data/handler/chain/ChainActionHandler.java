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
import com.echothree.control.user.chain.common.spec.ChainActionSpec;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ChainActionHandler
        extends BaseHandler {

    ChainService chainService;
    String chainKindName;
    String chainTypeName;
    String chainName;
    String chainActionSetName;
    String chainActionName;

    /** Creates a new instance of ChainActionHandler */
    public ChainActionHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String chainKindName, String chainTypeName, String chainName, String chainActionSetName,
            String chainActionName)
            throws SAXException {
        super(initialDataParser, parentHandler);

        try {
            chainService = ChainUtil.getHome();
        } catch(NamingException ne) {
            throw new SAXException(ne);
        }

        this.chainKindName = chainKindName;
        this.chainTypeName = chainTypeName;
        this.chainName = chainName;
        this.chainActionSetName = chainActionSetName;
        this.chainActionName = chainActionName;
    }

    public void setupChainActionSpec(ChainActionSpec chainActionSpec) {
        chainActionSpec.setChainKindName(chainKindName);
        chainActionSpec.setChainTypeName(chainTypeName);
        chainActionSpec.setChainName(chainName);
        chainActionSpec.setChainActionSetName(chainActionSetName);
        chainActionSpec.setChainActionName(chainActionName);
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("chainActionDescription")) {
            var commandForm = ChainFormFactory.getCreateChainActionDescriptionForm();

            setupChainActionSpec(commandForm);
            commandForm.set(getAttrsMap(attrs));

            chainService.createChainActionDescription(initialDataParser.getUserVisit(), commandForm);
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("chainAction")) {
            initialDataParser.popHandler();
        }
    }
}