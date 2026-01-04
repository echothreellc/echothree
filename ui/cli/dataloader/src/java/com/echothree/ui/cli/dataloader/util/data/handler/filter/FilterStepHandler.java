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

public class FilterStepHandler
        extends BaseHandler {
    FilterService filterService;
    String filterKindName;
    String filterTypeName;
    String filterName;
    String filterStepName;
    
    /** Creates a new instance of FilterStepHandler */
    public FilterStepHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String filterKindName, String filterTypeName, String filterName, String filterStepName) {
        super(initialDataParser, parentHandler);
        
        try {
            filterService = FilterUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.filterKindName = filterKindName;
        this.filterTypeName = filterTypeName;
        this.filterName = filterName;
        this.filterStepName = filterStepName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("filterStepDescription")) {
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
                var commandForm = FilterFormFactory.getCreateFilterStepDescriptionForm();
                
                commandForm.setFilterKindName(filterKindName);
                commandForm.setFilterTypeName(filterTypeName);
                commandForm.setFilterName(filterName);
                commandForm.setFilterStepName(filterStepName);
                commandForm.setLanguageIsoName(languageIsoName);
                commandForm.setDescription(description);
                
                filterService.createFilterStepDescription(initialDataParser.getUserVisit(), commandForm);
            } catch (Exception e) {
                throw new SAXException(e);
            }
        } else if(localName.equals("filterStepElement")) {
            String filterStepElementName = null;
            String filterItemSelectorName = null;
            String filterAdjustmentName = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("filterStepElementName"))
                    filterStepElementName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("filterItemSelectorName"))
                    filterItemSelectorName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("filterAdjustmentName"))
                    filterAdjustmentName = attrs.getValue(i);
            }
            
            try {
                var commandForm = FilterFormFactory.getCreateFilterStepElementForm();
                
                commandForm.setFilterKindName(filterKindName);
                commandForm.setFilterTypeName(filterTypeName);
                commandForm.setFilterName(filterName);
                commandForm.setFilterStepName(filterStepName);
                commandForm.setFilterStepElementName(filterStepElementName);
                commandForm.setFilterItemSelectorName(filterItemSelectorName);
                commandForm.setFilterAdjustmentName(filterAdjustmentName);
                
                filterService.createFilterStepElement(initialDataParser.getUserVisit(), commandForm);
                
                initialDataParser.pushHandler(new FilterStepElementHandler(initialDataParser, this, filterKindName, filterTypeName, filterName, filterStepName, filterStepElementName));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("filterStep")) {
            initialDataParser.popHandler();
        }
    }
    
}
