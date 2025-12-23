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

public class FilterTypeHandler
        extends BaseHandler {
    FilterService filterService;
    String filterKindName;
    String filterTypeName;
    
    /** Creates a new instance of FilterTypeHandler */
    public FilterTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String filterKindName, String filterTypeName) {
        super(initialDataParser, parentHandler);
        
        try {
            filterService = FilterUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.filterKindName = filterKindName;
        this.filterTypeName = filterTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("filterTypeDescription")) {
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
                var commandForm = FilterFormFactory.getCreateFilterTypeDescriptionForm();
                
                commandForm.setFilterKindName(filterKindName);
                commandForm.setFilterTypeName(filterTypeName);
                commandForm.setLanguageIsoName(languageIsoName);
                commandForm.setDescription(description);
                
                filterService.createFilterTypeDescription(initialDataParser.getUserVisit(), commandForm);
            } catch (Exception e) {
                throw new SAXException(e);
            }
        } else if(localName.equals("filter")) {
            String filterName = null;
            String initialFilterAdjustmentName = null;
            String filterItemSelectorName = null;
            String isDefault = null;
            String sortOrder = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("filterName"))
                    filterName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("initialFilterAdjustmentName"))
                    initialFilterAdjustmentName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("filterItemSelectorName"))
                    filterItemSelectorName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("isDefault"))
                    isDefault = attrs.getValue(i);
                else if(attrs.getQName(i).equals("sortOrder"))
                    sortOrder = attrs.getValue(i);
            }
            
            try {
                var commandForm = FilterFormFactory.getCreateFilterForm();
                
                commandForm.setFilterKindName(filterKindName);
                commandForm.setFilterTypeName(filterTypeName);
                commandForm.setFilterName(filterName);
                commandForm.setInitialFilterAdjustmentName(initialFilterAdjustmentName);
                commandForm.setFilterItemSelectorName(filterItemSelectorName);
                commandForm.setIsDefault(isDefault);
                commandForm.setSortOrder(sortOrder);
                
                filterService.createFilter(initialDataParser.getUserVisit(), commandForm);
                
                initialDataParser.pushHandler(new FilterHandler(initialDataParser, this, filterKindName, filterTypeName, filterName));
            } catch (Exception e) {
                throw new SAXException(e);
            }
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
