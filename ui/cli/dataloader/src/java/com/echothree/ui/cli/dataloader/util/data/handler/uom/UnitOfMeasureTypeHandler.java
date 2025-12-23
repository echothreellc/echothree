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

public class UnitOfMeasureTypeHandler
        extends BaseHandler {
    
    UomService uomService;
    String unitOfMeasureKindName;
    String unitOfMeasureTypeName;
    
    /** Creates a new instance of UnitOfMeasureTypeHandler */
    public UnitOfMeasureTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String unitOfMeasureKindName, String unitOfMeasureTypeName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            uomService = UomUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
        
        this.unitOfMeasureKindName = unitOfMeasureKindName;
        this.unitOfMeasureTypeName = unitOfMeasureTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("unitOfMeasureTypeDescription")) {
            var form = UomFormFactory.getCreateUnitOfMeasureTypeDescriptionForm();

            form.setUnitOfMeasureKindName(unitOfMeasureKindName);
            form.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
            form.set(getAttrsMap(attrs));

            checkCommandResult(uomService.createUnitOfMeasureTypeDescription(initialDataParser.getUserVisit(), form));
        } else if(localName.equals("unitOfMeasureTypeVolume")) {
            var form = UomFormFactory.getCreateUnitOfMeasureTypeVolumeForm();

            form.setUnitOfMeasureKindName(unitOfMeasureKindName);
            form.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
            form.set(getAttrsMap(attrs));

            uomService.createUnitOfMeasureTypeVolume(initialDataParser.getUserVisit(), form);
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("unitOfMeasureType")) {
            initialDataParser.popHandler();
        }
    }
    
}
