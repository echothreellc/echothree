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
import com.echothree.control.user.tax.common.TaxUtil;
import com.echothree.control.user.tax.common.TaxService;
import com.echothree.control.user.tax.common.form.TaxFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.core.EntityAttributesHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class CityHandler
        extends BaseHandler {

    GeoService geoService;
    TaxService taxService;
    String cityGeoCodeName;
    String cityEntityRef;
    
    /** Creates a new instance of CityHandler */
    public CityHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String cityGeoCodeName, String cityEntityRef)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        geoService = lookupService(GeoService.class, GeoUtil::getHome);
        taxService = lookupService(TaxService.class, TaxUtil::getHome);
        
        this.cityGeoCodeName = cityGeoCodeName;
        this.cityEntityRef = cityEntityRef;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("geoCodeDescription")) {
            var commandForm = GeoFormFactory.getCreateGeoCodeDescriptionForm();
            
            commandForm.setGeoCodeName(cityGeoCodeName);
            commandForm.set(getAttrsMap(attrs));
            
            checkCommandResult(commandForm, geoService.createGeoCodeDescription(initialDataParser.getUserVisit(), commandForm));
        } else if(localName.equals("cityCounty")) {
            var commandForm = GeoFormFactory.getAddCityToCountyForm();
            
            commandForm.setCityGeoCodeName(cityGeoCodeName);
            commandForm.set(getAttrsMap(attrs));
            
            checkCommandResult(commandForm, geoService.addCityToCounty(initialDataParser.getUserVisit(), commandForm));
        } else if(localName.equals("cityZipCode")) {
            var commandForm = GeoFormFactory.getAddZipCodeToCityForm();
            
            commandForm.setCityGeoCodeName(cityGeoCodeName);
            commandForm.set(getAttrsMap(attrs));
            
            checkCommandResult(commandForm, geoService.addZipCodeToCity(initialDataParser.getUserVisit(), commandForm));
        } else if(localName.equals("geoCodeTax")) {
            var commandForm = TaxFormFactory.getCreateGeoCodeTaxForm();
            
            commandForm.setGeoCodeName(cityGeoCodeName);
            commandForm.set(getAttrsMap(attrs));
            
            checkCommandResult(commandForm, taxService.createGeoCodeTax(initialDataParser.getUserVisit(), commandForm));
        } else if(localName.equals("entityAttributes")) {
            initialDataParser.pushHandler(new EntityAttributesHandler(initialDataParser, this, cityEntityRef));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("city")) {
            initialDataParser.popHandler();
        }
    }
    
}
