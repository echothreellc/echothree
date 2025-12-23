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

public class FilterKindHandler
        extends BaseHandler {

    FilterService filterService;
    String filterKindName;

    /** Creates a new instance of FilterKindHandler */
    public FilterKindHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String filterKindName)
            throws SAXException {
        super(initialDataParser, parentHandler);

        try {
            filterService = FilterUtil.getHome();
        } catch(NamingException ne) {
            throw new SAXException(ne);
        }

        this.filterKindName = filterKindName;
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("filterKindDescription")) {
            var commandForm = FilterFormFactory.getCreateFilterKindDescriptionForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.set(getAttrsMap(attrs));

            filterService.createFilterKindDescription(initialDataParser.getUserVisit(), commandForm);
        } else {
            if(localName.equals("filterType")) {
                var commandForm = FilterFormFactory.getCreateFilterTypeForm();

                commandForm.setFilterKindName(filterKindName);
                commandForm.set(getAttrsMap(attrs));

                filterService.createFilterType(initialDataParser.getUserVisit(), commandForm);

                initialDataParser.pushHandler(new FilterTypeHandler(initialDataParser, this, filterKindName, commandForm.getFilterTypeName()));
            } else {
                if(localName.equals("filterAdjustment")) {
                    var commandForm = FilterFormFactory.getCreateFilterAdjustmentForm();

                    commandForm.setFilterKindName(filterKindName);
                    commandForm.set(getAttrsMap(attrs));

                    filterService.createFilterAdjustment(initialDataParser.getUserVisit(), commandForm);

                    initialDataParser.pushHandler(new FilterAdjustmentHandler(initialDataParser, this, filterKindName,
                            commandForm.getFilterAdjustmentName()));
                }
            }
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("filterKind")) {
            initialDataParser.popHandler();
        }
    }

}
