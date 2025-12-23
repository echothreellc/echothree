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

package com.echothree.ui.cli.dataloader.util.data.handler.geo;

import com.echothree.control.user.geo.common.GeoUtil;
import com.echothree.control.user.geo.common.GeoService;
import com.echothree.control.user.geo.common.form.GeoFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class GeoCodeTypeHandler
        extends BaseHandler {
    GeoService geoService;
    String geoCodeTypeName;
    
    /** Creates a new instance of GeoCodeTypeHandler */
    public GeoCodeTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String geoCodeTypeName) {
        super(initialDataParser, parentHandler);
        
        try {
            geoService = GeoUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        this.geoCodeTypeName = geoCodeTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("geoCodeTypeDescription")) {
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
                var form = GeoFormFactory.getCreateGeoCodeTypeDescriptionForm();
                
                form.setGeoCodeTypeName(geoCodeTypeName);
                form.setLanguageIsoName(languageIsoName);
                form.setDescription(description);
                
                geoService.createGeoCodeTypeDescription(initialDataParser.getUserVisit(), form);
            } catch (Exception e) {
                throw new SAXException(e);
            }
        } else if(localName.equals("geoCodeAliasType")) {
            String geoCodeAliasTypeName = null;
            String validationPattern = null;
            String isRequired = null;
            String isDefault = null;
            String sortOrder = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("geoCodeAliasTypeName"))
                    geoCodeAliasTypeName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("validationPattern"))
                    validationPattern = attrs.getValue(i);
                else if(attrs.getQName(i).equals("isRequired"))
                    isRequired = attrs.getValue(i);
                else if(attrs.getQName(i).equals("isDefault"))
                    isDefault = attrs.getValue(i);
                else if(attrs.getQName(i).equals("sortOrder"))
                    sortOrder = attrs.getValue(i);
            }
            
            try {
                var form = GeoFormFactory.getCreateGeoCodeAliasTypeForm();
                
                form.setGeoCodeTypeName(geoCodeTypeName);
                form.setGeoCodeAliasTypeName(geoCodeAliasTypeName);
                form.setValidationPattern(validationPattern);
                form.setIsRequired(isRequired);
                form.setIsDefault(isDefault);
                form.setSortOrder(sortOrder);
                
                geoService.createGeoCodeAliasType(initialDataParser.getUserVisit(), form);
                
                initialDataParser.pushHandler(new GeoCodeAliasTypeHandler(initialDataParser, this, geoCodeTypeName,
                        geoCodeAliasTypeName));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("geoCodeType")) {
            initialDataParser.popHandler();
        }
    }
    
}
