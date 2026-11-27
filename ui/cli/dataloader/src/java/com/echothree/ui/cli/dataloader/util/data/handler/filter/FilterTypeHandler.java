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

public class FilterTypeHandler
        extends BaseHandler {

    FilterService filterService;
    String filterKindName;
    String filterTypeName;
    
    /** Creates a new instance of FilterTypeHandler */
    public FilterTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String filterKindName, String filterTypeName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        filterService = lookupService(FilterService.class, FilterUtil::getHome);
        
        this.filterKindName = filterKindName;
        this.filterTypeName = filterTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("filterTypeDescription")) {
            var commandForm = FilterFormFactory.getCreateFilterTypeDescriptionForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setFilterTypeName(filterTypeName);
            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(commandForm, filterService.createFilterTypeDescription(initialDataParser.getUserVisit(), commandForm));
        } else if(localName.equals("filter")) {
            var commandForm = FilterFormFactory.getCreateFilterForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setFilterTypeName(filterTypeName);
            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(commandForm, filterService.createFilter(initialDataParser.getUserVisit(), commandForm));

            initialDataParser.pushHandler(new FilterHandler(initialDataParser, this, filterKindName, filterTypeName,
                    commandForm.getFilterName()));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("filterType")) {
            initialDataParser.popHandler();
        }
    }
    
}
