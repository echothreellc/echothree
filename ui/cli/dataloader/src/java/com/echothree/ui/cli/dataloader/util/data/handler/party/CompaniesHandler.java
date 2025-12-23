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

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.PartyService;
import com.echothree.control.user.party.common.form.PartyFormFactory;
import com.echothree.control.user.party.common.result.CreateCompanyResult;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class CompaniesHandler
        extends BaseHandler {
    PartyService partyService;
    
    /** Creates a new instance of CompaniesHandler */
    public CompaniesHandler(InitialDataParser initialDataParser, BaseHandler parentHandler) {
        super(initialDataParser, parentHandler);
        
        try {
            partyService = PartyUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("company")) {
            var commandForm = PartyFormFactory.getCreateCompanyForm();
            
            commandForm.set(getAttrsMap(attrs));

            var commandResult = partyService.createCompany(initialDataParser.getUserVisit(), commandForm);
            
            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (CreateCompanyResult)executionResult.getResult();
                var partyName = result.getPartyName();
                var companyName = result.getCompanyName();
                var entityRef = result.getEntityRef();
                
                initialDataParser.pushHandler(new CompanyHandler(initialDataParser, this, partyName, companyName, entityRef));
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("companies")) {
            initialDataParser.popHandler();
        }
    }
    
}
