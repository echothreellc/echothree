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
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class RoleTypesHandler
        extends BaseHandler {
    PartyService partyService;
    
    /** Creates a new instance of RoleTypesHandler */
    public RoleTypesHandler(InitialDataParser initialDataParser, BaseHandler parentHandler) {
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
        if(localName.equals("roleType")) {
            String roleTypeName = null;
            String parentRoleTypeName = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("roleTypeName"))
                    roleTypeName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("parentRoleTypeName"))
                    parentRoleTypeName = attrs.getValue(i);
            }
            
            try {
                var createRoleTypeForm = PartyFormFactory.getCreateRoleTypeForm();
                
                createRoleTypeForm.setRoleTypeName(roleTypeName);
                createRoleTypeForm.setParentRoleTypeName(parentRoleTypeName);
                
                partyService.createRoleType(initialDataParser.getUserVisit(), createRoleTypeForm);
            } catch (Exception e) {
                throw new SAXException(e);
            }
            
            initialDataParser.pushHandler(new RoleTypeHandler(initialDataParser, this, roleTypeName));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("roleTypes")) {
            initialDataParser.popHandler();
        }
    }
    
}
