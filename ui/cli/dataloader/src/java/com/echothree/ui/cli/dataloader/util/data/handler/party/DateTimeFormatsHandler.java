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

public class DateTimeFormatsHandler
        extends BaseHandler {
    PartyService partyService;
    
    /** Creates a new instance of DateTimeFormatsHandler */
    public DateTimeFormatsHandler(InitialDataParser initialDataParser, BaseHandler parentHandler) {
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
        if(localName.equals("dateTimeFormat")) {
            String dateTimeFormatName = null;
            String javaShortDateFormat = null;
            String javaAbbrevDateFormat = null;
            String javaAbbrevDateFormatWeekday = null;
            String javaLongDateFormat = null;
            String javaLongDateFormatWeekday = null;
            String javaTimeFormat = null;
            String javaTimeFormatSeconds = null;
            String unixShortDateFormat = null;
            String unixAbbrevDateFormat = null;
            String unixAbbrevDateFormatWeekday = null;
            String unixLongDateFormat = null;
            String unixLongDateFormatWeekday = null;
            String unixTimeFormat = null;
            String unixTimeFormatSeconds = null;
            String shortDateSeparator = null;
            String timeSeparator = null;
            String isDefault = null;
            String sortOrder = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("dateTimeFormatName"))
                    dateTimeFormatName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("javaShortDateFormat"))
                    javaShortDateFormat = attrs.getValue(i);
                else if(attrs.getQName(i).equals("javaAbbrevDateFormat"))
                    javaAbbrevDateFormat = attrs.getValue(i);
                else if(attrs.getQName(i).equals("javaAbbrevDateFormatWeekday"))
                    javaAbbrevDateFormatWeekday = attrs.getValue(i);
                else if(attrs.getQName(i).equals("javaLongDateFormat"))
                    javaLongDateFormat = attrs.getValue(i);
                else if(attrs.getQName(i).equals("javaLongDateFormatWeekday"))
                    javaLongDateFormatWeekday = attrs.getValue(i);
                else if(attrs.getQName(i).equals("javaTimeFormat"))
                    javaTimeFormat = attrs.getValue(i);
                else if(attrs.getQName(i).equals("javaTimeFormatSeconds"))
                    javaTimeFormatSeconds = attrs.getValue(i);
                else if(attrs.getQName(i).equals("unixShortDateFormat"))
                    unixShortDateFormat = attrs.getValue(i);
                else if(attrs.getQName(i).equals("unixAbbrevDateFormat"))
                    unixAbbrevDateFormat = attrs.getValue(i);
                else if(attrs.getQName(i).equals("unixAbbrevDateFormatWeekday"))
                    unixAbbrevDateFormatWeekday = attrs.getValue(i);
                else if(attrs.getQName(i).equals("unixLongDateFormat"))
                    unixLongDateFormat = attrs.getValue(i);
                else if(attrs.getQName(i).equals("unixLongDateFormatWeekday"))
                    unixLongDateFormatWeekday = attrs.getValue(i);
                else if(attrs.getQName(i).equals("unixTimeFormat"))
                    unixTimeFormat = attrs.getValue(i);
                else if(attrs.getQName(i).equals("unixTimeFormatSeconds"))
                    unixTimeFormatSeconds = attrs.getValue(i);
                else if(attrs.getQName(i).equals("shortDateSeparator"))
                    shortDateSeparator = attrs.getValue(i);
                else if(attrs.getQName(i).equals("timeSeparator"))
                    timeSeparator = attrs.getValue(i);
                else if(attrs.getQName(i).equals("isDefault"))
                    isDefault = attrs.getValue(i);
                else if(attrs.getQName(i).equals("sortOrder"))
                    sortOrder = attrs.getValue(i);
            }
            
            try {
                var createDateTimeFormatForm = PartyFormFactory.getCreateDateTimeFormatForm();
                
                createDateTimeFormatForm.setDateTimeFormatName(dateTimeFormatName);
                createDateTimeFormatForm.setJavaShortDateFormat(javaShortDateFormat);
                createDateTimeFormatForm.setJavaAbbrevDateFormat(javaAbbrevDateFormat);
                createDateTimeFormatForm.setJavaAbbrevDateFormatWeekday(javaAbbrevDateFormatWeekday);
                createDateTimeFormatForm.setJavaLongDateFormat(javaLongDateFormat);
                createDateTimeFormatForm.setJavaLongDateFormatWeekday(javaLongDateFormatWeekday);
                createDateTimeFormatForm.setJavaTimeFormat(javaTimeFormat);
                createDateTimeFormatForm.setJavaTimeFormatSeconds(javaTimeFormatSeconds);
                createDateTimeFormatForm.setUnixShortDateFormat(unixShortDateFormat);
                createDateTimeFormatForm.setUnixAbbrevDateFormat(unixAbbrevDateFormat);
                createDateTimeFormatForm.setUnixAbbrevDateFormatWeekday(unixAbbrevDateFormatWeekday);
                createDateTimeFormatForm.setUnixLongDateFormat(unixLongDateFormat);
                createDateTimeFormatForm.setUnixLongDateFormatWeekday(unixLongDateFormatWeekday);
                createDateTimeFormatForm.setUnixTimeFormat(unixTimeFormat);
                createDateTimeFormatForm.setUnixTimeFormatSeconds(unixTimeFormatSeconds);
                createDateTimeFormatForm.setShortDateSeparator(shortDateSeparator);
                createDateTimeFormatForm.setTimeSeparator(timeSeparator);
                createDateTimeFormatForm.setIsDefault(isDefault);
                createDateTimeFormatForm.setSortOrder(sortOrder);
                
                partyService.createDateTimeFormat(initialDataParser.getUserVisit(), createDateTimeFormatForm);
                
                initialDataParser.pushHandler(new DateTimeFormatHandler(initialDataParser, this, dateTimeFormatName));
            } catch (Exception e) {
                throw new SAXException(e);
            }
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
