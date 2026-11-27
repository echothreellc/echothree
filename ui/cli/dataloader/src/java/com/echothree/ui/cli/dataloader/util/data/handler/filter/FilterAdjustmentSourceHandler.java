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
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class FilterAdjustmentSourceHandler
        extends BaseHandler {

    FilterService filterService;
    String filterAdjustmentSourceName;
    
    /** Creates a new instance of FilterAdjustmentSourceHandler */
    public FilterAdjustmentSourceHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String filterAdjustmentSourceName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        filterService = lookupService(FilterService.class, FilterUtil::getHome);
        
        this.filterAdjustmentSourceName = filterAdjustmentSourceName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("filterAdjustmentSourceDescription")) {
            var commandForm = FilterFormFactory.getCreateFilterAdjustmentSourceDescriptionForm();

            commandForm.setFilterAdjustmentSourceName(filterAdjustmentSourceName);
            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(commandForm,
                    filterService.createFilterAdjustmentSourceDescription(initialDataParser.getUserVisit(), commandForm));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("filterAdjustmentSource")) {
            initialDataParser.popHandler();
        }
    }
    
}
