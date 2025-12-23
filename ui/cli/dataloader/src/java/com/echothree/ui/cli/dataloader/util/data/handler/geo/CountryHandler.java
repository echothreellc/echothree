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
import com.echothree.control.user.geo.common.result.CreateStateResult;
import com.echothree.control.user.geo.common.result.CreateZipCodeResult;
import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.ItemService;
import com.echothree.control.user.item.common.form.ItemFormFactory;
import com.echothree.control.user.tax.common.TaxUtil;
import com.echothree.control.user.tax.common.TaxService;
import com.echothree.control.user.tax.common.form.TaxFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.core.EntityAttributesHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class CountryHandler
        extends BaseHandler {
    
    GeoService geoService;
    ItemService itemService;
    TaxService taxService;
    String countryGeoCodeName;
    String countryEntityRef;
    
    /** Creates a new instance of CountryHandler */
    public CountryHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String countryGeoCodeName, String countryEntityRef)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            geoService = GeoUtil.getHome();
            itemService = ItemUtil.getHome();
            taxService = TaxUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
        
        this.countryGeoCodeName = countryGeoCodeName;
        this.countryEntityRef = countryEntityRef;
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
            
            commandForm.setGeoCodeName(countryGeoCodeName);
            commandForm.setLanguageIsoName(languageIsoName);
            commandForm.setDescription(description);
            
            geoService.createGeoCodeDescription(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("geoCodeLanguage")) {
            var commandForm = GeoFormFactory.getCreateGeoCodeLanguageForm();
            
            commandForm.setGeoCodeName(countryGeoCodeName);

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                var attrQName = attrs.getQName(i);
                var attrValue = attrs.getValue(i);
                
                if(attrQName.equals("languageIsoName"))
                    commandForm.setLanguageIsoName(attrValue);
                else if(attrQName.equals("isDefault"))
                    commandForm.setIsDefault(attrValue);
                else if(attrQName.equals("sortOrder"))
                    commandForm.setSortOrder(attrValue);
            }
            
            geoService.createGeoCodeLanguage(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("geoCodeCurrency")) {
            var commandForm = GeoFormFactory.getCreateGeoCodeCurrencyForm();
            
            commandForm.setGeoCodeName(countryGeoCodeName);

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                var attrQName = attrs.getQName(i);
                var attrValue = attrs.getValue(i);
                
                if(attrQName.equals("currencyIsoName"))
                    commandForm.setCurrencyIsoName(attrValue);
                else if(attrQName.equals("isDefault"))
                    commandForm.setIsDefault(attrValue);
                else if(attrQName.equals("sortOrder"))
                    commandForm.setSortOrder(attrValue);
            }
            
            geoService.createGeoCodeCurrency(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("geoCodeTimeZone")) {
            var commandForm = GeoFormFactory.getCreateGeoCodeTimeZoneForm();
            
            commandForm.setGeoCodeName(countryGeoCodeName);

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                var attrQName = attrs.getQName(i);
                var attrValue = attrs.getValue(i);
                
                if(attrQName.equals("javaTimeZoneName"))
                    commandForm.setJavaTimeZoneName(attrValue);
                else if(attrQName.equals("isDefault"))
                    commandForm.setIsDefault(attrValue);
                else if(attrQName.equals("sortOrder"))
                    commandForm.setSortOrder(attrValue);
            }
            
            geoService.createGeoCodeTimeZone(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("geoCodeDateTimeFormat")) {
            var commandForm = GeoFormFactory.getCreateGeoCodeDateTimeFormatForm();
            
            commandForm.setGeoCodeName(countryGeoCodeName);

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                var attrQName = attrs.getQName(i);
                var attrValue = attrs.getValue(i);
                
                if(attrQName.equals("dateTimeFormatName"))
                    commandForm.setDateTimeFormatName(attrValue);
                else if(attrQName.equals("isDefault"))
                    commandForm.setIsDefault(attrValue);
                else if(attrQName.equals("sortOrder"))
                    commandForm.setSortOrder(attrValue);
            }
            
            geoService.createGeoCodeDateTimeFormat(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("zipCode")) {
            var commandForm = GeoFormFactory.getCreateZipCodeForm();
            String zipCodeName = null;
            String isDefault = null;
            String sortOrder = null;
            String description = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("zipCodeName"))
                    zipCodeName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("isDefault"))
                    isDefault = attrs.getValue(i);
                else if(attrs.getQName(i).equals("sortOrder"))
                    sortOrder = attrs.getValue(i);
                else if(attrs.getQName(i).equals("description"))
                    description = attrs.getValue(i);
            }
            
            commandForm.setCountryGeoCodeName(countryGeoCodeName);
            commandForm.setZipCodeName(zipCodeName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = geoService.createZipCode(initialDataParser.getUserVisit(), commandForm);
            var executionResult = commandResult.getExecutionResult();
            var createZipCodeResult = (CreateZipCodeResult)executionResult.getResult();
            var zipCodeGeoCodeName = createZipCodeResult.getGeoCodeName();
            var zipCodeEntityRef = createZipCodeResult.getEntityRef();
            
            initialDataParser.pushHandler(new ZipCodeHandler(initialDataParser, this, zipCodeGeoCodeName, zipCodeEntityRef));
        } if(localName.equals("harmonizedTariffScheduleCode")) {
            var commandForm = ItemFormFactory.getCreateHarmonizedTariffScheduleCodeForm();

            commandForm.setCountryName(countryGeoCodeName);
            commandForm.set(getAttrsMap(attrs));

            itemService.createHarmonizedTariffScheduleCode(initialDataParser.getUserVisit(), commandForm);

            initialDataParser.pushHandler(new HarmonizedTariffScheduleCodeHandler(initialDataParser, this, countryGeoCodeName,
                    commandForm.getHarmonizedTariffScheduleCodeName()));
        } if(localName.equals("taxClassification")) {
            var commandForm = TaxFormFactory.getCreateTaxClassificationForm();

            commandForm.setCountryName(countryGeoCodeName);
            commandForm.set(getAttrsMap(attrs));

            taxService.createTaxClassification(initialDataParser.getUserVisit(), commandForm);

            initialDataParser.pushHandler(new TaxClassificationHandler(initialDataParser, this, countryGeoCodeName,
                    commandForm.getTaxClassificationName()));
        } else if(localName.equals("state")) {
            var commandForm = GeoFormFactory.getCreateStateForm();
            String stateName = null;
            String postal2Letter = null;
            String isDefault = null;
            String sortOrder = null;
            String description = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("stateName"))
                    stateName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("postal2Letter"))
                    postal2Letter = attrs.getValue(i);
                else if(attrs.getQName(i).equals("isDefault"))
                    isDefault = attrs.getValue(i);
                else if(attrs.getQName(i).equals("sortOrder"))
                    sortOrder = attrs.getValue(i);
                else if(attrs.getQName(i).equals("description"))
                    description = attrs.getValue(i);
            }
            
            commandForm.setCountryGeoCodeName(countryGeoCodeName);
            commandForm.setStateName(stateName);
            commandForm.setPostal2Letter(postal2Letter);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            commandForm.setDescription(description);

            var commandResult = geoService.createState(initialDataParser.getUserVisit(), commandForm);
            var executionResult = commandResult.getExecutionResult();
            var createStateResult = (CreateStateResult)executionResult.getResult();
            var stateGeoCodeName = createStateResult.getGeoCodeName();
            var stateEntityRef = createStateResult.getEntityRef();
            
            initialDataParser.pushHandler(new StateHandler(initialDataParser, this, stateGeoCodeName, stateEntityRef));
        } else if(localName.equals("geoCodeTax")) {
            var commandForm = TaxFormFactory.getCreateGeoCodeTaxForm();
            String taxName = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("taxName"))
                    taxName = attrs.getValue(i);
            }
            
            commandForm.setGeoCodeName(countryGeoCodeName);
            commandForm.setTaxName(taxName);
            
            taxService.createGeoCodeTax(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("entityAttributes")) {
            initialDataParser.pushHandler(new EntityAttributesHandler(initialDataParser, this, countryEntityRef));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("country")) {
            initialDataParser.popHandler();
        }
    }
    
}
