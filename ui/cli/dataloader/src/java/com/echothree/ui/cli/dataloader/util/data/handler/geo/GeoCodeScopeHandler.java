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
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class GeoCodeScopeHandler
        extends BaseHandler {

    GeoService geoService;
    String geoCodeScopeName;
    
    /** Creates a new instance of GeoCodeScopeHandler */
    public GeoCodeScopeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String geoCodeScopeName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        geoService = lookupService(GeoService.class, GeoUtil::getHome);
        this.geoCodeScopeName = geoCodeScopeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("geoCodeScopeDescription")) {
            var createGeoCodeScopeDescriptionForm = GeoFormFactory.getCreateGeoCodeScopeDescriptionForm();

            createGeoCodeScopeDescriptionForm.setGeoCodeScopeName(geoCodeScopeName);
            createGeoCodeScopeDescriptionForm.set(getAttrsMap(attrs));

            checkCommandResult(createGeoCodeScopeDescriptionForm,
                    geoService.createGeoCodeScopeDescription(initialDataParser.getUserVisit(), createGeoCodeScopeDescriptionForm));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("geoCodeScope")) {
            initialDataParser.popHandler();
        }
    }
    
}
