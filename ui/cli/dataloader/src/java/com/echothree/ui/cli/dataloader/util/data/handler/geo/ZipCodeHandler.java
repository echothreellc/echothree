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
import com.echothree.ui.cli.dataloader.util.data.handler.core.EntityAttributesHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ZipCodeHandler
        extends BaseHandler {

    GeoService geoService;
    String zipCodeGeoCodeName;
    String zipCodeEntityRef;
    
    /** Creates a new instance of ZipCodeHandler */
    public ZipCodeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String zipCodeGeoCodeName, String zipCodeEntityRef)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        geoService = lookupService(GeoService.class, GeoUtil::getHome);
        
        this.zipCodeGeoCodeName = zipCodeGeoCodeName;
        this.zipCodeEntityRef = zipCodeEntityRef;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("geoCodeDescription")) {
            var commandForm = GeoFormFactory.getCreateGeoCodeDescriptionForm();

            commandForm.setGeoCodeName(zipCodeGeoCodeName);
            commandForm.set(getAttrsMap(attrs));

            geoService.createGeoCodeDescription(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("entityAttributes")) {
            initialDataParser.pushHandler(new EntityAttributesHandler(initialDataParser, this, zipCodeEntityRef));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("zipCode")) {
            initialDataParser.popHandler();
        }
    }
    
}
