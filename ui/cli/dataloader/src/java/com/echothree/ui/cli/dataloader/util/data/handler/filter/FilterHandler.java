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

public class FilterHandler
        extends BaseHandler {
    FilterService filterService;
    String filterKindName;
    String filterTypeName;
    String filterName;
    
    /** Creates a new instance of FilterHandler */
    public FilterHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String filterKindName, String filterTypeName, String filterName) {
        super(initialDataParser, parentHandler);
        
        try {
            filterService = FilterUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.filterKindName = filterKindName;
        this.filterTypeName = filterTypeName;
        this.filterName = filterName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("filterDescription")) {
            try {
                var commandForm = FilterFormFactory.getCreateFilterDescriptionForm();
                
                commandForm.setFilterKindName(filterKindName);
                commandForm.setFilterTypeName(filterTypeName);
                commandForm.setFilterName(filterName);
                commandForm.set(getAttrsMap(attrs));
                
                filterService.createFilterDescription(initialDataParser.getUserVisit(), commandForm);
            } catch (Exception e) {
                throw new SAXException(e);
            }
        } else if(localName.equals("filterStep")) {
            try {
                var commandForm = FilterFormFactory.getCreateFilterStepForm();
                
                commandForm.setFilterKindName(filterKindName);
                commandForm.setFilterTypeName(filterTypeName);
                commandForm.setFilterName(filterName);
                commandForm.set(getAttrsMap(attrs));
                
                filterService.createFilterStep(initialDataParser.getUserVisit(), commandForm);
                
                initialDataParser.pushHandler(new FilterStepHandler(initialDataParser, this, filterKindName, filterTypeName,
                        filterName, commandForm.getFilterStepName()));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        } else if(localName.equals("filterEntranceStep")) {
            try {
                var commandForm = FilterFormFactory.getCreateFilterEntranceStepForm();
                
                commandForm.setFilterKindName(filterKindName);
                commandForm.setFilterTypeName(filterTypeName);
                commandForm.setFilterName(filterName);
                commandForm.set(getAttrsMap(attrs));
                
                filterService.createFilterEntranceStep(initialDataParser.getUserVisit(), commandForm);
            } catch (Exception e) {
                throw new SAXException(e);
            }
        } else if(localName.equals("filterStepDestination")) {
            try {
                var commandForm = FilterFormFactory.getCreateFilterStepDestinationForm();
                
                commandForm.setFilterKindName(filterKindName);
                commandForm.setFilterTypeName(filterTypeName);
                commandForm.setFilterName(filterName);
                commandForm.set(getAttrsMap(attrs));
                
                filterService.createFilterStepDestination(initialDataParser.getUserVisit(), commandForm);
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("filter")) {
            initialDataParser.popHandler();
        }
    }
    
}
