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
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class RoleTypeHandler
        extends BaseHandler {

    PartyService partyService;
    String roleTypeName;
    
    /** Creates a new instance of RoleTypeHandler */
    public RoleTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String roleTypeName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        partyService = lookupService(PartyService.class, PartyUtil::getHome);
        
        this.roleTypeName = roleTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("roleTypeDescription")) {
            var createRoleTypeDescriptionForm = PartyFormFactory.getCreateRoleTypeDescriptionForm();

            createRoleTypeDescriptionForm.setRoleTypeName(roleTypeName);
            createRoleTypeDescriptionForm.set(getAttrsMap(attrs));

            partyService.createRoleTypeDescription(initialDataParser.getUserVisit(), createRoleTypeDescriptionForm);
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("roleType")) {
            initialDataParser.popHandler();
        }
    }
    
}
