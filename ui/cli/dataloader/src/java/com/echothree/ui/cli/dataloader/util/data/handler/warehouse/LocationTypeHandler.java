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

package com.echothree.ui.cli.dataloader.util.data.handler.warehouse;

import com.echothree.control.user.warehouse.common.WarehouseUtil;
import com.echothree.control.user.warehouse.common.WarehouseService;
import com.echothree.control.user.warehouse.common.form.WarehouseFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class LocationTypeHandler
        extends BaseHandler {
    WarehouseService warehouseService;
    String warehouseName;
    String locationTypeName;
    
    /** Creates a new instance of LocationTypeHandler */
    public LocationTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String warehouseName,
    String locationTypeName) {
        super(initialDataParser, parentHandler);
        
        try {
            warehouseService = WarehouseUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.warehouseName = warehouseName;
        this.locationTypeName = locationTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("locationTypeDescription")) {
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
                var form = WarehouseFormFactory.getCreateLocationTypeDescriptionForm();
                
                form.setWarehouseName(warehouseName);
                form.setLocationTypeName(locationTypeName);
                form.setLanguageIsoName(languageIsoName);
                form.setDescription(description);
                
                warehouseService.createLocationTypeDescription(initialDataParser.getUserVisit(), form);
            } catch (Exception e) {
                throw new SAXException(e);
            }
        } else if(localName.equals("locationNameElement")) {
            String locationNameElementName = null;
            String offset = null;
            String length = null;
            String validationPattern = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("locationNameElementName"))
                    locationNameElementName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("offset"))
                    offset = attrs.getValue(i);
                else if(attrs.getQName(i).equals("length"))
                    length = attrs.getValue(i);
                else if(attrs.getQName(i).equals("validationPattern"))
                    validationPattern = attrs.getValue(i);
            }
            
            try {
                var form = WarehouseFormFactory.getCreateLocationNameElementForm();
                
                form.setWarehouseName(warehouseName);
                form.setLocationTypeName(locationTypeName);
                form.setLocationNameElementName(locationNameElementName);
                form.setOffset(offset);
                form.setLength(length);
                form.setValidationPattern(validationPattern);
                
                warehouseService.createLocationNameElement(initialDataParser.getUserVisit(), form);
                
                initialDataParser.pushHandler(new LocationNameElementHandler(initialDataParser, this, warehouseName,
                locationTypeName, locationNameElementName));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("locationType")) {
            initialDataParser.popHandler();
        }
    }
    
}
