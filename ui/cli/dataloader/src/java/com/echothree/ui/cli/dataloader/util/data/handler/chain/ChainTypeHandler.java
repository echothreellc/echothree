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
import com.echothree.control.user.letter.common.LetterUtil;
import com.echothree.control.user.letter.common.LetterService;
import com.echothree.control.user.letter.common.form.LetterFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.letter.LetterHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ChainTypeHandler
        extends BaseHandler {

    ChainService chainService;
    LetterService letterService;
    String chainKindName;
    String chainTypeName;

    /** Creates a new instance of ChainTypeHandler */
    public ChainTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String chainKindName, String chainTypeName)
            throws SAXException {
        super(initialDataParser, parentHandler);

        try {
            chainService = ChainUtil.getHome();
            letterService = LetterUtil.getHome();
        } catch(NamingException ne) {
            throw new SAXException(ne);
        }

        this.chainKindName = chainKindName;
        this.chainTypeName = chainTypeName;
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("chainTypeDescription")) {
            var commandForm = ChainFormFactory.getCreateChainTypeDescriptionForm();

            commandForm.setChainKindName(chainKindName);
            commandForm.setChainTypeName(chainTypeName);
            commandForm.set(getAttrsMap(attrs));

            chainService.createChainTypeDescription(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("chainEntityRoleType")) {
            var commandForm = ChainFormFactory.getCreateChainEntityRoleTypeForm();

            commandForm.setChainKindName(chainKindName);
            commandForm.setChainTypeName(chainTypeName);
            commandForm.set(getAttrsMap(attrs));

            chainService.createChainEntityRoleType(initialDataParser.getUserVisit(), commandForm);

            initialDataParser.pushHandler(new ChainEntityRoleTypeHandler(initialDataParser, this, chainKindName, chainTypeName, commandForm.getChainEntityRoleTypeName()));
        } else if(localName.equals("chain")) {
            var commandForm = ChainFormFactory.getCreateChainForm();

            commandForm.setChainKindName(chainKindName);
            commandForm.setChainTypeName(chainTypeName);
            commandForm.set(getAttrsMap(attrs));

            chainService.createChain(initialDataParser.getUserVisit(), commandForm);

            initialDataParser.pushHandler(new ChainHandler(initialDataParser, this, chainKindName, chainTypeName, commandForm.getChainName()));
        } else if(localName.equals("letter")) {
            var commandForm = LetterFormFactory.getCreateLetterForm();

            commandForm.setChainKindName(chainKindName);
            commandForm.setChainTypeName(chainTypeName);
            commandForm.set(getAttrsMap(attrs));

            letterService.createLetter(initialDataParser.getUserVisit(), commandForm);

            initialDataParser.pushHandler(new LetterHandler(initialDataParser, this, chainKindName, chainTypeName, commandForm.getLetterName()));
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("chainType")) {
            initialDataParser.popHandler();
        }
    }
}