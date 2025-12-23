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
import com.echothree.control.user.party.common.result.CreateNameSuffixResult;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class NameSuffixesHandler
        extends BaseHandler {
    PartyService partyService;
    
    /** Creates a new instance of NameSuffixesHandler */
    public NameSuffixesHandler(InitialDataParser initialDataParser, BaseHandler parentHandler) {
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
        if(localName.equals("nameSuffix")) {
            String description = null;
            String isDefault = null;
            String sortOrder = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("description"))
                    description = attrs.getValue(i);
                else if(attrs.getQName(i).equals("isDefault"))
                    isDefault = attrs.getValue(i);
                else if(attrs.getQName(i).equals("sortOrder"))
                    sortOrder = attrs.getValue(i);
            }
            
            try {
                if(initialDataParser.getNameSuffixes().get(description) == null) {
                    var form = PartyFormFactory.getCreateNameSuffixForm();
                    
                    form.setDescription(description);
                    form.setIsDefault(isDefault);
                    form.setSortOrder(sortOrder);

                    var commandResult = partyService.createNameSuffix(initialDataParser.getUserVisit(), form);
                    var executionResult = commandResult.getExecutionResult();
                    var result = (CreateNameSuffixResult)executionResult.getResult();
                    
                    initialDataParser.addNameSuffix(result.getNameSuffixId(), description);
                }
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("nameSuffixes")) {
            initialDataParser.popHandler();
        }
    }
    
}
