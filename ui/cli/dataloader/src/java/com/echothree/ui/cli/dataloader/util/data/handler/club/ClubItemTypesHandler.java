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

package com.echothree.ui.cli.dataloader.util.data.handler.club;

import com.echothree.control.user.club.common.ClubUtil;
import com.echothree.control.user.club.common.ClubService;
import com.echothree.control.user.club.common.form.ClubFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ClubItemTypesHandler
        extends BaseHandler {
    ClubService clubService;
    
    /** Creates a new instance of ClubItemTypesHandler */
    public ClubItemTypesHandler(InitialDataParser initialDataParser, BaseHandler parentHandler) {
        super(initialDataParser, parentHandler);
        
        try {
            clubService = ClubUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("clubItemType")) {
            String clubItemTypeName = null;
            String isDefault = null;
            String sortOrder = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("clubItemTypeName"))
                    clubItemTypeName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("isDefault"))
                    isDefault = attrs.getValue(i);
                else if(attrs.getQName(i).equals("sortOrder"))
                    sortOrder = attrs.getValue(i);
            }
            
            try {
                var commandForm = ClubFormFactory.getCreateClubItemTypeForm();
                
                commandForm.setClubItemTypeName(clubItemTypeName);
                commandForm.setIsDefault(isDefault);
                commandForm.setSortOrder(sortOrder);
                
                clubService.createClubItemType(initialDataParser.getUserVisit(), commandForm);
                
                initialDataParser.pushHandler(new ClubItemTypeHandler(initialDataParser, this, clubItemTypeName));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("clubItemTypes")) {
            initialDataParser.popHandler();
        }
    }
    
}
