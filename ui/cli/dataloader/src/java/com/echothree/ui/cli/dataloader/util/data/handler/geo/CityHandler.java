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
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class CityHandler
        extends BaseHandler {
    GeoService geoService;
    TaxService taxService;
    String cityGeoCodeName;
    String cityEntityRef;
    
    /** Creates a new instance of CityHandler */
    public CityHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String cityGeoCodeName, String cityEntityRef) {
        super(initialDataParser, parentHandler);
        
        try {
            geoService = GeoUtil.getHome();
            taxService = TaxUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.cityGeoCodeName = cityGeoCodeName;
        this.cityEntityRef = cityEntityRef;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("geoCodeDescription")) {
            var commandForm = GeoFormFactory.getCreateGeoCodeDescriptionForm();
            String languageIsoName = null;
            String description = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("languageIsoName"))
                    languageIsoName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("description"))
                    description = attrs.getValue(i);
            }
            
            commandForm.setGeoCodeName(cityGeoCodeName);
            commandForm.setLanguageIsoName(languageIsoName);
            commandForm.setDescription(description);
            
            geoService.createGeoCodeDescription(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("cityCounty")) {
            var commandForm = GeoFormFactory.getAddCityToCountyForm();
            String countyName = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("countyName"))
                    countyName = attrs.getValue(i);
            }
            
            commandForm.setCityGeoCodeName(cityGeoCodeName);
            commandForm.setCountyName(countyName);
            
            geoService.addCityToCounty(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("cityZipCode")) {
            var commandForm = GeoFormFactory.getAddZipCodeToCityForm();
            String zipCodeName = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("zipCodeName"))
                    zipCodeName = attrs.getValue(i);
            }
            
            commandForm.setCityGeoCodeName(cityGeoCodeName);
            commandForm.setZipCodeName(zipCodeName);
            
            geoService.addZipCodeToCity(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("geoCodeTax")) {
            var commandForm = TaxFormFactory.getCreateGeoCodeTaxForm();
            String taxName = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("taxName"))
                    taxName = attrs.getValue(i);
            }
            
            commandForm.setGeoCodeName(cityGeoCodeName);
            commandForm.setTaxName(taxName);
            
            taxService.createGeoCodeTax(initialDataParser.getUserVisit(), commandForm);
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
