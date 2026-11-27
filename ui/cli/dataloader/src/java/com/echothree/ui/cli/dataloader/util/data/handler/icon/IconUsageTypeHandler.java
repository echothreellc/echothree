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

package com.echothree.ui.cli.dataloader.util.data.handler.icon;

import com.echothree.control.user.icon.common.IconUtil;
import com.echothree.control.user.icon.common.IconService;
import com.echothree.control.user.icon.common.form.IconFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class IconUsageTypeHandler
        extends BaseHandler {

    IconService iconService;
    String iconUsageTypeName;
    
    /** Creates a new instance of IconUsageTypeHandler */
    public IconUsageTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String iconUsageTypeName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        iconService = lookupService(IconService.class, IconUtil::getHome);
        
        this.iconUsageTypeName = iconUsageTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("iconUsageTypeDescription")) {
            var form = IconFormFactory.getCreateIconUsageTypeDescriptionForm();

            form.setIconUsageTypeName(iconUsageTypeName);
            form.set(getAttrsMap(attrs));

            iconService.createIconUsageTypeDescription(initialDataParser.getUserVisit(), form);
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("iconUsageType")) {
            initialDataParser.popHandler();
        }
    }
    
}
