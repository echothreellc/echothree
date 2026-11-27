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

package com.echothree.ui.cli.dataloader.util.data.handler.filter;

import com.echothree.control.user.filter.common.FilterUtil;
import com.echothree.control.user.filter.common.FilterService;
import com.echothree.control.user.filter.common.form.FilterFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class FilterAdjustmentHandler
        extends BaseHandler {
    FilterService filterService;
    String filterKindName;
    String filterAdjustmentName;
    
    /** Creates a new instance of FilterAdjustmentHandler */
    public FilterAdjustmentHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String filterKindName, String filterAdjustmentName) {
        super(initialDataParser, parentHandler);
        
        try {
            filterService = FilterUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.filterKindName = filterKindName;
        this.filterAdjustmentName = filterAdjustmentName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("filterAdjustmentDescription")) {
            var commandForm = FilterFormFactory.getCreateFilterAdjustmentDescriptionForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setFilterAdjustmentName(filterAdjustmentName);
            commandForm.set(getAttrsMap(attrs));

            filterService.createFilterAdjustmentDescription(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("filterAdjustmentAmount")) {
            var commandForm = FilterFormFactory.getCreateFilterAdjustmentAmountForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setFilterAdjustmentName(filterAdjustmentName);
            commandForm.set(getAttrsMap(attrs));

            filterService.createFilterAdjustmentAmount(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("filterAdjustmentFixedAmount")) {
            var commandForm = FilterFormFactory.getCreateFilterAdjustmentFixedAmountForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setFilterAdjustmentName(filterAdjustmentName);
            commandForm.set(getAttrsMap(attrs));

            filterService.createFilterAdjustmentFixedAmount(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("filterAdjustmentPercent")) {
            var commandForm = FilterFormFactory.getCreateFilterAdjustmentPercentForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setFilterAdjustmentName(filterAdjustmentName);
            commandForm.set(getAttrsMap(attrs));

            filterService.createFilterAdjustmentPercent(initialDataParser.getUserVisit(), commandForm);
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("filterAdjustment")) {
            initialDataParser.popHandler();
        }
    }
    
}
