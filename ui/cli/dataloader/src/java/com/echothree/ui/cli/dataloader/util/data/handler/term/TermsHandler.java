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

package com.echothree.ui.cli.dataloader.util.data.handler.term;

import com.echothree.control.user.term.common.TermUtil;
import com.echothree.control.user.term.common.TermService;
import com.echothree.control.user.term.common.form.TermFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class TermsHandler
        extends BaseHandler {
    TermService termService;
    
    /** Creates a new instance of TermsHandler */
    public TermsHandler(InitialDataParser initialDataParser, BaseHandler parentHandler) {
        super(initialDataParser, parentHandler);
        
        try {
            termService = TermUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("term")) {
            String termName = null;
            String termTypeName = null;
            String isDefault = null;
            String sortOrder = null;
            String netDueDays = null;
            String discountPercentage = null;
            String discountDays = null;
            String netDueDayOfMonth = null;
            String dueNextMonthDays = null;
            String discountBeforeDayOfMonth = null;

            var attrCount = attrs.getLength();
            for(var i = 0; i < attrCount; i++) {
                if(attrs.getQName(i).equals("termName"))
                    termName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("termTypeName"))
                    termTypeName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("isDefault"))
                    isDefault = attrs.getValue(i);
                else if(attrs.getQName(i).equals("sortOrder"))
                    sortOrder = attrs.getValue(i);
                else if(attrs.getQName(i).equals("netDueDays"))
                    netDueDays = attrs.getValue(i);
                else if(attrs.getQName(i).equals("discountPercentage"))
                    discountPercentage = attrs.getValue(i);
                else if(attrs.getQName(i).equals("discountDays"))
                    discountDays = attrs.getValue(i);
                else if(attrs.getQName(i).equals("netDueDayOfMonth"))
                    netDueDayOfMonth = attrs.getValue(i);
                else if(attrs.getQName(i).equals("dueNextMonthDays"))
                    dueNextMonthDays = attrs.getValue(i);
                else if(attrs.getQName(i).equals("discountBeforeDayOfMonth"))
                    discountBeforeDayOfMonth = attrs.getValue(i);
            }
            
            try {
                var commandForm = TermFormFactory.getCreateTermForm();
                
                commandForm.setTermName(termName);
                commandForm.setTermTypeName(termTypeName);
                commandForm.setIsDefault(isDefault);
                commandForm.setSortOrder(sortOrder);
                commandForm.setNetDueDays(netDueDays);
                commandForm.setDiscountPercentage(discountPercentage);
                commandForm.setDiscountDays(discountDays);
                commandForm.setNetDueDayOfMonth(netDueDayOfMonth);
                commandForm.setDueNextMonthDays(dueNextMonthDays);
                commandForm.setDiscountBeforeDayOfMonth(discountBeforeDayOfMonth);
                
                checkCommandResult(termService.createTerm(initialDataParser.getUserVisit(), commandForm));
                
                initialDataParser.pushHandler(new TermHandler(initialDataParser, this, termName));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("terms")) {
            initialDataParser.popHandler();
        }
    }
    
}
