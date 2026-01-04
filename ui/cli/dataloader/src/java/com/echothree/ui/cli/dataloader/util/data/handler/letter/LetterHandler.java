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

package com.echothree.ui.cli.dataloader.util.data.handler.letter;

import com.echothree.control.user.letter.common.LetterUtil;
import com.echothree.control.user.letter.common.LetterService;
import com.echothree.control.user.letter.common.form.LetterFormFactory;
import com.echothree.control.user.letter.common.spec.LetterSpec;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class LetterHandler
        extends BaseHandler {
    LetterService letterService;
    String chainKindName;
    String chainTypeName;
    String letterName;
    
    /** Creates a new instance of LetterHandler */
    public LetterHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String chainKindName,
            String chainTypeName, String letterName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            letterService = LetterUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
        
        this.chainKindName = chainKindName;
        this.chainTypeName = chainTypeName;
        this.letterName = letterName;
    }
    
    private void setupLetterSpec(LetterSpec letterSpec) {
        letterSpec.setChainKindName(chainKindName);
        letterSpec.setChainTypeName(chainTypeName);
        letterSpec.setLetterName(letterName);
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("letterDescription")) {
            var commandForm = LetterFormFactory.getCreateLetterDescriptionForm();
            
            setupLetterSpec(commandForm);
            commandForm.set(getAttrsMap(attrs));
            
            letterService.createLetterDescription(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("letterContactMechanismPurpose")) {
            var commandForm = LetterFormFactory.getCreateLetterContactMechanismPurposeForm();
            
            setupLetterSpec(commandForm);
            commandForm.set(getAttrsMap(attrs));
            
            letterService.createLetterContactMechanismPurpose(initialDataParser.getUserVisit(), commandForm);
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("letter")) {
            initialDataParser.popHandler();
        }
    }
    
}
