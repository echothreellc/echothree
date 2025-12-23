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

package com.echothree.ui.cli.dataloader.util.data.handler.club;

import com.echothree.control.user.club.common.ClubUtil;
import com.echothree.control.user.club.common.ClubService;
import com.echothree.control.user.club.common.form.ClubFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ClubHandler
        extends BaseHandler {
    ClubService clubService;
    String clubName;
    
    /** Creates a new instance of ClubHandler */
    public ClubHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String clubName) {
        super(initialDataParser, parentHandler);
        
        try {
            clubService = ClubUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.clubName = clubName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        
        if(localName.equals("clubDescription")) {
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
                var commandForm = ClubFormFactory.getCreateClubDescriptionForm();
                
                commandForm.setClubName(clubName);
                commandForm.setLanguageIsoName(languageIsoName);
                commandForm.setDescription(description);
                
                clubService.createClubDescription(initialDataParser.getUserVisit(), commandForm);
            } catch (Exception e) {
                throw new SAXException(e);
            }
        } else if(localName.equals("clubItem")) {
            String clubItemTypeName = null;
            String itemName = null;
            String unitOfMeasureTypeName = null;
            String subscriptionTime = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("clubItemTypeName"))
                    clubItemTypeName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("itemName"))
                    itemName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("unitOfMeasureTypeName"))
                    unitOfMeasureTypeName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("subscriptionTime"))
                    subscriptionTime = attrs.getValue(i);
            }
            
            try {
                var commandForm = ClubFormFactory.getCreateClubItemForm();
                
                commandForm.setClubName(clubName);
                commandForm.setClubItemTypeName(clubItemTypeName);
                commandForm.setItemName(itemName);
                commandForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
                commandForm.setSubscriptionTime(subscriptionTime);
                
                clubService.createClubItem(initialDataParser.getUserVisit(), commandForm);
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("club")) {
            initialDataParser.popHandler();
        }
    }
    
}
