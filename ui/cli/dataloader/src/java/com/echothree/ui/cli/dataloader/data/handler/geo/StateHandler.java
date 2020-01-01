// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.ui.cli.dataloader.data.handler.geo;

import com.echothree.control.user.geo.common.GeoUtil;
import com.echothree.control.user.geo.common.GeoService;
import com.echothree.control.user.geo.common.form.CreateCityForm;
import com.echothree.control.user.geo.common.form.CreateCountyForm;
import com.echothree.control.user.geo.common.form.CreateGeoCodeDescriptionForm;
import com.echothree.control.user.geo.common.form.GeoFormFactory;
import com.echothree.control.user.geo.common.result.CreateCityResult;
import com.echothree.control.user.geo.common.result.CreateCountyResult;
import com.echothree.control.user.tax.common.TaxUtil;
import com.echothree.control.user.tax.common.TaxService;
import com.echothree.control.user.tax.common.form.CreateGeoCodeTaxForm;
import com.echothree.control.user.tax.common.form.TaxFormFactory;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import com.echothree.ui.cli.dataloader.data.handler.core.EntityAttributesHandler;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
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
            CreateGeoCodeDescriptionForm commandForm = GeoFormFactory.getCreateGeoCodeDescriptionForm();
            String languageIsoName = null;
            String description = null;
            
            int count = attrs.getLength();
            for(int i = 0; i < count; i++) {
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
            CreateCountyForm commandForm = GeoFormFactory.getCreateCountyForm();
            String countyName = null;
            String countyNumber = null;
            String isDefault = null;
            String sortOrder = null;
            
            int count = attrs.getLength();
            for(int i = 0; i < count; i++) {
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
            
            CommandResult commandResult = geoService.createCounty(initialDataParser.getUserVisit(), commandForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            CreateCountyResult createCountyResult = (CreateCountyResult)executionResult.getResult();
            String countyGeoCodeName = createCountyResult.getGeoCodeName();
            String countyEntityRef = createCountyResult.getEntityRef();
            
            initialDataParser.pushHandler(new CountyHandler(initialDataParser, this, countyGeoCodeName, countyEntityRef));
        } else if(localName.equals("city")) {
            CreateCityForm commandForm = GeoFormFactory.getCreateCityForm();
            String cityName = null;
            String isDefault = null;
            String sortOrder = null;
            
            int count = attrs.getLength();
            for(int i = 0; i < count; i++) {
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
            
            CommandResult commandResult = geoService.createCity(initialDataParser.getUserVisit(), commandForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            CreateCityResult createCityResult = (CreateCityResult)executionResult.getResult();
            String cityGeoCodeName = createCityResult.getGeoCodeName();
            String cityEntityRef = createCityResult.getEntityRef();
            
            initialDataParser.pushHandler(new CityHandler(initialDataParser, this, cityGeoCodeName, cityEntityRef));
        } else if(localName.equals("geoCodeTax")) {
            CreateGeoCodeTaxForm commandForm = TaxFormFactory.getCreateGeoCodeTaxForm();
            String taxName = null;
            
            int count = attrs.getLength();
            for(int i = 0; i < count; i++) {
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
