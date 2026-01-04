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
import com.echothree.control.user.geo.common.result.CreateCountryResult;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class CountriesHandler
        extends BaseHandler {
    GeoService geoService;
    
    /** Creates a new instance of CountriesHandler */
    public CountriesHandler(InitialDataParser initialDataParser, BaseHandler parentHandler) {
        super(initialDataParser, parentHandler);
        
        try {
            geoService = GeoUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("country")) {
            String countryName = null;
            String iso3Number = null;
            String iso3Letter = null;
            String iso2Letter = null;
            String telephoneCode = null;
            String areaCodePattern = null;
            String areaCodeRequired = null;
            String areaCodeExample = null;
            String telephoneNumberPattern = null;
            String telephoneNumberExample = null;
            String postalAddressFormatName = null;
            String cityRequired = null;
            String cityGeoCodeRequired = null;
            String stateRequired = null;
            String stateGeoCodeRequired = null;
            String postalCodePattern = null;
            String postalCodeRequired = null;
            String postalCodeGeoCodeRequired = null;
            String postalCodeLength = null;
            String postalCodeGeoCodeLength = null;
            String postalCodeExample = null;
            String isDefault = null;
            String sortOrder = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("countryName"))
                    countryName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("iso3Number"))
                    iso3Number = attrs.getValue(i);
                else if(attrs.getQName(i).equals("iso3Letter"))
                    iso3Letter = attrs.getValue(i);
                else if(attrs.getQName(i).equals("iso2Letter"))
                    iso2Letter = attrs.getValue(i);
                else if(attrs.getQName(i).equals("telephoneCode"))
                    telephoneCode = attrs.getValue(i);
                else if(attrs.getQName(i).equals("areaCodePattern"))
                    areaCodePattern = attrs.getValue(i);
                else if(attrs.getQName(i).equals("areaCodeRequired"))
                    areaCodeRequired = attrs.getValue(i);
                else if(attrs.getQName(i).equals("areaCodeExample"))
                    areaCodeExample = attrs.getValue(i);
                else if(attrs.getQName(i).equals("telephoneNumberPattern"))
                    telephoneNumberPattern = attrs.getValue(i);
                else if(attrs.getQName(i).equals("telephoneNumberExample"))
                    telephoneNumberExample = attrs.getValue(i);
                else if(attrs.getQName(i).equals("postalAddressFormatName"))
                    postalAddressFormatName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("cityRequired"))
                    cityRequired = attrs.getValue(i);
                else if(attrs.getQName(i).equals("cityGeoCodeRequired"))
                    cityGeoCodeRequired = attrs.getValue(i);
                else if(attrs.getQName(i).equals("stateRequired"))
                    stateRequired = attrs.getValue(i);
                else if(attrs.getQName(i).equals("stateGeoCodeRequired"))
                    stateGeoCodeRequired = attrs.getValue(i);
                else if(attrs.getQName(i).equals("postalCodePattern"))
                    postalCodePattern = attrs.getValue(i);
                else if(attrs.getQName(i).equals("postalCodeRequired"))
                    postalCodeRequired = attrs.getValue(i);
                else if(attrs.getQName(i).equals("postalCodeGeoCodeRequired"))
                    postalCodeGeoCodeRequired = attrs.getValue(i);
                else if(attrs.getQName(i).equals("postalCodeLength"))
                    postalCodeLength = attrs.getValue(i);
                else if(attrs.getQName(i).equals("postalCodeGeoCodeLength"))
                    postalCodeGeoCodeLength = attrs.getValue(i);
                else if(attrs.getQName(i).equals("postalCodeExample"))
                    postalCodeExample = attrs.getValue(i);
                else if(attrs.getQName(i).equals("isDefault"))
                    isDefault = attrs.getValue(i);
                else if(attrs.getQName(i).equals("sortOrder"))
                    sortOrder = attrs.getValue(i);
            }
            
            try {
                var form = GeoFormFactory.getCreateCountryForm();
                
                form.setCountryName(countryName);
                form.setIso3Number(iso3Number);
                form.setIso3Letter(iso3Letter);
                form.setIso2Letter(iso2Letter);
                form.setTelephoneCode(telephoneCode);
                form.setAreaCodePattern(areaCodePattern);
                form.setAreaCodeRequired(areaCodeRequired);
                form.setAreaCodeExample(areaCodeExample);
                form.setTelephoneNumberPattern(telephoneNumberPattern);
                form.setTelephoneNumberExample(telephoneNumberExample);
                form.setPostalAddressFormatName(postalAddressFormatName);
                form.setCityRequired(cityRequired);
                form.setCityGeoCodeRequired(cityGeoCodeRequired);
                form.setStateRequired(stateRequired);
                form.setStateGeoCodeRequired(stateGeoCodeRequired);
                form.setPostalCodePattern(postalCodePattern);
                form.setPostalCodeRequired(postalCodeRequired);
                form.setPostalCodeGeoCodeRequired(postalCodeGeoCodeRequired);
                form.setPostalCodeLength(postalCodeLength);
                form.setPostalCodeGeoCodeLength(postalCodeGeoCodeLength);
                form.setPostalCodeExample(postalCodeExample);
                form.setIsDefault(isDefault);
                form.setSortOrder(sortOrder);

                var commandResult = geoService.createCountry(initialDataParser.getUserVisit(), form);
                var executionResult = commandResult.getExecutionResult();
                var createCountryResult = (CreateCountryResult)executionResult.getResult();
                var countryGeoCodeName = createCountryResult.getGeoCodeName();
                var countryEntityRef = createCountryResult.getEntityRef();
                
                initialDataParser.pushHandler(new CountryHandler(initialDataParser, this, countryGeoCodeName, countryEntityRef));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("countries")) {
            initialDataParser.popHandler();
        }
    }
    
}
