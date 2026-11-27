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

public class DateTimeFormatsHandler
        extends BaseHandler {

    PartyService partyService;
    
    /** Creates a new instance of DateTimeFormatsHandler */
    public DateTimeFormatsHandler(InitialDataParser initialDataParser, BaseHandler parentHandler)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        partyService = lookupService(PartyService.class, PartyUtil::getHome);
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("dateTimeFormat")) {
            var createDateTimeFormatForm = PartyFormFactory.getCreateDateTimeFormatForm();

            createDateTimeFormatForm.set(getAttrsMap(attrs));

            checkCommandResult(createDateTimeFormatForm,
                    partyService.createDateTimeFormat(initialDataParser.getUserVisit(), createDateTimeFormatForm));

            initialDataParser.pushHandler(new DateTimeFormatHandler(initialDataParser, this, createDateTimeFormatForm.getDateTimeFormatName()));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("dateTimeFormats")) {
            initialDataParser.popHandler();
        }
    }
    
}
