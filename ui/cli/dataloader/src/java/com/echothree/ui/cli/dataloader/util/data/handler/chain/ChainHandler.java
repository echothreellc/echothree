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
import com.echothree.control.user.chain.common.spec.ChainSpec;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ChainHandler
        extends BaseHandler {

    ChainService chainService;
    String chainKindName;
    String chainTypeName;
    String chainName;

    /** Creates a new instance of ChainHandler */
    public ChainHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String chainKindName, String chainTypeName, String chainName)
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
    }

    public void setupChainSpec(ChainSpec chainSpec) {
        chainSpec.setChainKindName(chainKindName);
        chainSpec.setChainTypeName(chainTypeName);
        chainSpec.setChainName(chainName);
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("chainDescription")) {
            var commandForm = ChainFormFactory.getCreateChainDescriptionForm();

            setupChainSpec(commandForm);
            commandForm.set(getAttrsMap(attrs));

            chainService.createChainDescription(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("chainActionSet")) {
            var commandForm = ChainFormFactory.getCreateChainActionSetForm();

            setupChainSpec(commandForm);
            commandForm.set(getAttrsMap(attrs));

            chainService.createChainActionSet(initialDataParser.getUserVisit(), commandForm);

            initialDataParser.pushHandler(new ChainActionSetHandler(initialDataParser, this, chainKindName, chainTypeName, chainName, commandForm.getChainActionSetName()));
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("chain")) {
            initialDataParser.popHandler();
        }
    }
}