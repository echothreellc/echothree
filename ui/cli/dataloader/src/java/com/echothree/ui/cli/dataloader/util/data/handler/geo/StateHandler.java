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
import com.echothree.control.user.geo.common.result.CreateCityResult;
import com.echothree.control.user.geo.common.result.CreateCountyResult;
import com.echothree.control.user.tax.common.TaxUtil;
import com.echothree.control.user.tax.common.TaxService;
import com.echothree.control.user.tax.common.form.TaxFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.core.EntityAttributesHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class StateHandler
        extends BaseHandler {
    GeoService geoService;
    TaxService taxService;
    String stateGeoCodeName;
    String stateEntityRef;
    
    /** Creates a new instance of StateHandler */
    public StateHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String stateGeoCodeName, String stateEntityRef) {
        super(initialDataParser, parentHandler);
        
        try {
            geoService = GeoUtil.getHome();
            taxService = TaxUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.stateGeoCodeName = stateGeoCodeName;
        this.stateEntityRef = stateEntityRef;
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
            
            commandForm.setGeoCodeName(stateGeoCodeName);
            commandForm.setLanguageIsoName(languageIsoName);
            commandForm.setDescription(description);
            
            geoService.createGeoCodeDescription(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("county")) {
            var commandForm = GeoFormFactory.getCreateCountyForm();
            String countyName = null;
            String countyNumber = null;
            String isDefault = null;
            String sortOrder = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("countyName"))
                    countyName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("countyNumber"))
                    countyNumber = attrs.getValue(i);
                else if(attrs.getQName(i).equals("isDefault"))
                    isDefault = attrs.getValue(i);
                else if(attrs.getQName(i).equals("sortOrder"))
                    sortOrder = attrs.getValue(i);
            }
            
            commandForm.setStateGeoCodeName(stateGeoCodeName);
            commandForm.setCountyName(countyName);
            commandForm.setCountyNumber(countyNumber);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);

            var commandResult = geoService.createCounty(initialDataParser.getUserVisit(), commandForm);
            var executionResult = commandResult.getExecutionResult();
            var createCountyResult = (CreateCountyResult)executionResult.getResult();
            var countyGeoCodeName = createCountyResult.getGeoCodeName();
            var countyEntityRef = createCountyResult.getEntityRef();
            
            initialDataParser.pushHandler(new CountyHandler(initialDataParser, this, countyGeoCodeName, countyEntityRef));
        } else if(localName.equals("city")) {
            var commandForm = GeoFormFactory.getCreateCityForm();
            String cityName = null;
            String isDefault = null;
            String sortOrder = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("cityName"))
                    cityName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("isDefault"))
                    isDefault = attrs.getValue(i);
                else if(attrs.getQName(i).equals("sortOrder"))
                    sortOrder = attrs.getValue(i);
            }
            
            commandForm.setStateGeoCodeName(stateGeoCodeName);
            commandForm.setCityName(cityName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);

            var commandResult = geoService.createCity(initialDataParser.getUserVisit(), commandForm);
            var executionResult = commandResult.getExecutionResult();
            var createCityResult = (CreateCityResult)executionResult.getResult();
            var cityGeoCodeName = createCityResult.getGeoCodeName();
            var cityEntityRef = createCityResult.getEntityRef();
            
            initialDataParser.pushHandler(new CityHandler(initialDataParser, this, cityGeoCodeName, cityEntityRef));
        } else if(localName.equals("geoCodeTax")) {
            var commandForm = TaxFormFactory.getCreateGeoCodeTaxForm();
            String taxName = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("taxName"))
                    taxName = attrs.getValue(i);
            }
            
            commandForm.setGeoCodeName(stateGeoCodeName);
            commandForm.setTaxName(taxName);
            
            taxService.createGeoCodeTax(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("entityAttributes")) {
            initialDataParser.pushHandler(new EntityAttributesHandler(initialDataParser, this, stateEntityRef));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("state")) {
            initialDataParser.popHandler();
        }
    }
    
}
