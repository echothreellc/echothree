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

package com.echothree.ui.cli.dataloader.util.data.handler.party;

import com.echothree.control.user.letter.common.LetterUtil;
import com.echothree.control.user.letter.common.LetterService;
import com.echothree.control.user.letter.common.form.LetterFormFactory;
import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.PartyService;
import com.echothree.control.user.party.common.form.PartyFormFactory;
import com.echothree.control.user.party.common.result.CreateDivisionResult;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.comment.CommentsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.contact.ContactMechanismsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.core.EntityAttributesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.document.PartyDocumentsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.letter.LetterSourceHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class CompanyHandler
        extends BaseHandler {
    LetterService letterService;
    PartyService partyService;
    String partyName;
    String companyName;
    String entityRef;
    
    /** Creates a new instance of CompanyHandler */
    public CompanyHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String partyName, String companyName,
            String entityRef)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            letterService = LetterUtil.getHome();
            partyService = PartyUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
        
        this.partyName = partyName;
        this.companyName = companyName;
        this.entityRef = entityRef;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("division")) {
            var form = PartyFormFactory.getCreateDivisionForm();
            
            form.setCompanyName(companyName);
            form.set(getAttrsMap(attrs));

            var commandResult = partyService.createDivision(initialDataParser.getUserVisit(), form);
            
            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (CreateDivisionResult)executionResult.getResult();
                var partyName = result.getPartyName();
                var entityRef = result.getEntityRef();
                
                initialDataParser.pushHandler(new DivisionHandler(initialDataParser, this, partyName, companyName,
                        form.getDivisionName(), entityRef));
            }
        } else if(localName.equals("letterSource")) {
            var commandForm = LetterFormFactory.getCreateLetterSourceForm();
            
            commandForm.setCompanyName(companyName);
            commandForm.set(getAttrsMap(attrs));
            
            letterService.createLetterSource(initialDataParser.getUserVisit(), commandForm);
            
            initialDataParser.pushHandler(new LetterSourceHandler(initialDataParser, this, commandForm.getLetterSourceName()));
        } else if(localName.equals("contactMechanisms")) {
            initialDataParser.pushHandler(new ContactMechanismsHandler(initialDataParser, this, partyName));
        } else if(localName.equals("partyDocuments")) {
            initialDataParser.pushHandler(new PartyDocumentsHandler(initialDataParser, this, partyName));
        } else if(localName.equals("entityAttributes")) {
            initialDataParser.pushHandler(new EntityAttributesHandler(initialDataParser, this, entityRef));
        } else if(localName.equals("comments")) {
            initialDataParser.pushHandler(new CommentsHandler(initialDataParser, this, entityRef));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("company")) {
            initialDataParser.popHandler();
        }
    }
    
}
