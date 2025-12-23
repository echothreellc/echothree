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

package com.echothree.ui.cli.dataloader.util.data.handler.uom;

import com.echothree.control.user.uom.common.UomUtil;
import com.echothree.control.user.uom.common.UomService;
import com.echothree.control.user.uom.common.form.UomFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class UnitOfMeasureKindUseTypesHandler
        extends BaseHandler {
    
    UomService uomService;
    
    /** Creates a new instance of UnitOfMeasureKindUseTypesHandler */
    public UnitOfMeasureKindUseTypesHandler(InitialDataParser initialDataParser, BaseHandler parentHandler)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            uomService = UomUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("unitOfMeasureKindUseType")) {
            var form = UomFormFactory.getCreateUnitOfMeasureKindUseTypeForm();

            form.set(getAttrsMap(attrs));

            checkCommandResult(uomService.createUnitOfMeasureKindUseType(initialDataParser.getUserVisit(), form));

            initialDataParser.pushHandler(new UnitOfMeasureKindUseTypeHandler(initialDataParser, this, form.getUnitOfMeasureKindUseTypeName()));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("unitOfMeasureKindUseTypes")) {
            initialDataParser.popHandler();
        }
    }
    
}
