// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class PartyTypeUseTypeHandler
        extends BaseHandler {
    PartyService partyService;
    String partyTypeUseTypeName;
    
    /** Creates a new instance of PartyTypeUseTypeHandler */
    public PartyTypeUseTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String partyTypeUseTypeName) {
        super(initialDataParser, parentHandler);
        
        try {
            partyService = PartyUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.partyTypeUseTypeName = partyTypeUseTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("partyTypeUseTypeDescription")) {
            String languageIsoName = null;
            String description = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("languageIsoName"))
                    languageIsoName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("description"))
                    description = attrs.getValue(i);
            }
            
            try {
                var form = PartyFormFactory.getCreatePartyTypeUseTypeDescriptionForm();
                
                form.setPartyTypeUseTypeName(partyTypeUseTypeName);
                form.setLanguageIsoName(languageIsoName);
                form.setDescription(description);
                
                partyService.createPartyTypeUseTypeDescription(initialDataParser.getUserVisit(), form);
            } catch (Exception e) {
                throw new SAXException(e);
            }
        } else if(localName.equals("partyTypeUse")) {
            String partyTypeName = null;
            String isDefault = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("partyTypeName"))
                    partyTypeName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("isDefault"))
                    isDefault = attrs.getValue(i);
            }
            
            try {
                var form = PartyFormFactory.getCreatePartyTypeUseForm();
                
                form.setPartyTypeUseTypeName(partyTypeUseTypeName);
                form.setPartyTypeName(partyTypeName);
                form.setIsDefault(isDefault);
                
                partyService.createPartyTypeUse(initialDataParser.getUserVisit(), form);
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("partyTypeUseType")) {
            initialDataParser.popHandler();
        }
    }
    
}
