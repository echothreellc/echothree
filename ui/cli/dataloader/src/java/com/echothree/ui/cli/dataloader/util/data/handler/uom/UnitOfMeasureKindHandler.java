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

public class UnitOfMeasureKindHandler
        extends BaseHandler {
    
    UomService uomService;
    String unitOfMeasureKindName;
    
    /** Creates a new instance of UnitOfMeasureKindHandler */
    public UnitOfMeasureKindHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String unitOfMeasureKindName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            uomService = UomUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
        
        this.unitOfMeasureKindName = unitOfMeasureKindName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("unitOfMeasureKindDescription")) {
            var form = UomFormFactory.getCreateUnitOfMeasureKindDescriptionForm();

            form.setUnitOfMeasureKindName(unitOfMeasureKindName);
            form.set(getAttrsMap(attrs));

            checkCommandResult(uomService.createUnitOfMeasureKindDescription(initialDataParser.getUserVisit(), form));
        } else if(localName.equals("unitOfMeasureType")) {
            String unitOfMeasureTypeName = null;
            String doCreate = null;

            var attrCount = attrs.getLength();
            for(var i = 0; i < attrCount; i++) {
                if(attrs.getQName(i).equals("unitOfMeasureTypeName"))
                    unitOfMeasureTypeName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("doCreate"))
                    doCreate = attrs.getValue(i);
            }
            
            if(doCreate == null || doCreate.equalsIgnoreCase("true")) {
                var form = UomFormFactory.getCreateUnitOfMeasureTypeForm();

                form.setUnitOfMeasureKindName(unitOfMeasureKindName);
                form.set(getAttrsMap(attrs));

                checkCommandResult(uomService.createUnitOfMeasureType(initialDataParser.getUserVisit(), form));
            }

            initialDataParser.pushHandler(new UnitOfMeasureTypeHandler(initialDataParser, this, unitOfMeasureKindName, unitOfMeasureTypeName));
        } else if(localName.equals("unitOfMeasureEquivalent")) {
            var form = UomFormFactory.getCreateUnitOfMeasureEquivalentForm();

            form.setUnitOfMeasureKindName(unitOfMeasureKindName);
            form.set(getAttrsMap(attrs));

            checkCommandResult(uomService.createUnitOfMeasureEquivalent(initialDataParser.getUserVisit(), form));
        } else if(localName.equals("unitOfMeasureKindUse")) {
            var form = UomFormFactory.getCreateUnitOfMeasureKindUseForm();

            form.setUnitOfMeasureKindName(unitOfMeasureKindName);
            form.set(getAttrsMap(attrs));

            checkCommandResult(uomService.createUnitOfMeasureKindUse(initialDataParser.getUserVisit(), form));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("unitOfMeasureKind")) {
            initialDataParser.popHandler();
        }
    }
    
}
