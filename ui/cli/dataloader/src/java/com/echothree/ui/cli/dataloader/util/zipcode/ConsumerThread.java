// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.ui.cli.dataloader.util.zipcode;

import com.echothree.control.user.geo.common.form.GeoFormFactory;
import com.echothree.control.user.geo.common.result.CreateCityResult;
import com.echothree.control.user.geo.common.result.CreateCountyResult;
import com.echothree.control.user.geo.common.result.CreateZipCodeResult;
import com.echothree.control.user.geo.common.result.GetCityResult;
import com.echothree.control.user.geo.common.result.GetCountyResult;
import com.echothree.control.user.geo.common.result.GetStateResult;
import com.echothree.control.user.geo.common.result.GetZipCodeResult;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.collection.SmartQueue;
import com.echothree.util.common.message.Message;
import com.echothree.util.common.message.Messages;
import com.echothree.util.common.string.StringUtils;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ConsumerThread
        extends Thread {
    
    private final SmartQueue<List<ZipCodeData>> queue;
    private final ZipCodeParser zipCodeParser;
    private UserVisitPK userVisitPK;
    
    /** Creates a new instance of ConsumerThread */
    public ConsumerThread(SmartQueue<List<ZipCodeData>> queue, ZipCodeParser zipCodeParser) {
        this.queue = queue;
        this.zipCodeParser = zipCodeParser;
    }
    
    private UserVisitPK getUserVisit() {
        if(userVisitPK == null) {
            userVisitPK = zipCodeParser.getAuthenticationService().getDataLoaderUserVisit();
        }
        
        return userVisitPK;
    }
    
    private void clearUserVisit() {
        if(userVisitPK != null) {
            zipCodeParser.getAuthenticationService().invalidateUserVisit(userVisitPK);
            
            userVisitPK = null;
        }
    }
    
    private String loadZipCode(ZipCodeData zipCodeData) {
        var zipCodeName = zipCodeData.getZipCode();
        var zipCodeGeoCodeName = (String)zipCodeParser.getZipCodeGeoCodeNames().get(zipCodeName);
        
        if(zipCodeGeoCodeName == null) {
            var getZipCodeForm = GeoFormFactory.getGetZipCodeForm();
            
            getZipCodeForm.setCountryGeoCodeName(zipCodeParser.getCountryGeoCodeName());
            getZipCodeForm.setZipCodeName(zipCodeName);

            var commandResult = zipCodeParser.getGeoService().getZipCode(getUserVisit(), getZipCodeForm);
            var executionResult = commandResult.getExecutionResult();
            var getZipCodeResult = (GetZipCodeResult)executionResult.getResult();
            var postalCode = getZipCodeResult.getPostalCode();
            zipCodeGeoCodeName = postalCode == null ? null : postalCode.getGeoCodeName();

            if(zipCodeGeoCodeName == null) {
                var createZipCodeForm = GeoFormFactory.getCreateZipCodeForm();
                
                createZipCodeForm.setCountryGeoCodeName(zipCodeParser.getCountryGeoCodeName());
                createZipCodeForm.setZipCodeName(zipCodeName);
                createZipCodeForm.setSortOrder("1");
                createZipCodeForm.setIsDefault(String.valueOf(false));
                createZipCodeForm.setDescription(zipCodeName);
                
                commandResult = zipCodeParser.getGeoService().createZipCode(getUserVisit(), createZipCodeForm);
                executionResult = commandResult.getExecutionResult();
                var createZipCodeResult = (CreateZipCodeResult)executionResult.getResult();
                zipCodeGeoCodeName = createZipCodeResult.getGeoCodeName();
            }
            
            if(zipCodeGeoCodeName != null) {
                zipCodeParser.getLog().info("put zipCodeName = \"" + zipCodeName + ",\" zipCodeGeoCodeName = \"" + zipCodeGeoCodeName + "\"");
                zipCodeParser.getZipCodeGeoCodeNames().put(zipCodeName, zipCodeGeoCodeName);
            } else {
                zipCodeParser.getLog().error("Unknown and unable to create zipCodeName = \"" + zipCodeName + "\"");
            }
        }
        
        return zipCodeGeoCodeName;
    }
    
    private String loadCounty(String stateGeoCodeName, ZipCodeData zipCodeData) {
        var description = zipCodeData.getCountyName();
        String countyGeoCodeName = null;
        
        if(description != null) {
            var countyNumber = zipCodeData.getCountyNumber();
            var countyName = StringUtils.getInstance().cleanStringToName(description);
            var countiesByState = (Map)zipCodeParser.getCountyGeoCodeNames().get(stateGeoCodeName);
            
            if(countiesByState == null) {
                countiesByState = Collections.synchronizedMap(new HashMap());
                zipCodeParser.getCountyGeoCodeNames().put(stateGeoCodeName, countiesByState);
            }
            
            countyGeoCodeName = (String)countiesByState.get(countyName);
            
            if(countyGeoCodeName == null && countyNumber.length() > 0 && countyName.length() > 0) {
                var getCountyForm = GeoFormFactory.getGetCountyForm();
                
                getCountyForm.setStateGeoCodeName(stateGeoCodeName);
                getCountyForm.setCountyName(countyName);

                var commandResult = zipCodeParser.getGeoService().getCounty(getUserVisit(), getCountyForm);
                var executionResult = commandResult.getExecutionResult();
                var getCountyResult = (GetCountyResult)executionResult.getResult();
                var country = getCountyResult.getCounty();
                countyGeoCodeName = country == null ? null : country.getGeoCodeName();
                
                if(countyGeoCodeName == null) {
                    var createCountyForm = GeoFormFactory.getCreateCountyForm();
                    
                    createCountyForm.setStateGeoCodeName(stateGeoCodeName);
                    createCountyForm.setCountyName(countyName);
                    createCountyForm.setCountyNumber(countyNumber);
                    createCountyForm.setIsDefault("false");
                    createCountyForm.setSortOrder("1");
                    createCountyForm.setDescription(zipCodeData.getCountyName());
                    
                    commandResult = zipCodeParser.getGeoService().createCounty(getUserVisit(), createCountyForm);
                    executionResult = commandResult.getExecutionResult();
                    var createCountyResult = (CreateCountyResult)executionResult.getResult();
                    countyGeoCodeName = createCountyResult.getGeoCodeName();
                }
                
                if(countyGeoCodeName != null) {
                    zipCodeParser.getLog().info("put stateGeoCodeName = \"" + stateGeoCodeName + ",\" countyName = \"" + countyName + ",\" countyGeoCodeName = \"" + countyGeoCodeName + "\"");
                    countiesByState.put(countyName, countyGeoCodeName);
                } else {
                    zipCodeParser.getLog().error("Unknown and unable to create stateGeoCodeName = \"" + stateGeoCodeName + ",\" countyName = \"" + countyName + "\"");
                }
            }
        }
        
        return countyGeoCodeName;
    }
    
    private String loadCity(String stateGeoCodeName, ZipCodeData zipCodeData) {
        var description = zipCodeData.getCityStateName();
        var cityName = StringUtils.getInstance().cleanStringToName(description);
        var citiesByState = (Map)zipCodeParser.getCityGeoCodeNames().get(stateGeoCodeName);
        
        if(citiesByState == null) {
            citiesByState = Collections.synchronizedMap(new HashMap());
            zipCodeParser.getCityGeoCodeNames().put(stateGeoCodeName, citiesByState);
        }
        
        var cityGeoCodeName = (String)citiesByState.get(cityName);
        
        if(cityGeoCodeName == null) {
            var getCityForm = GeoFormFactory.getGetCityForm();
            
            getCityForm.setStateGeoCodeName(stateGeoCodeName);
            getCityForm.setCityName(cityName);

            var commandResult = zipCodeParser.getGeoService().getCity(getUserVisit(), getCityForm);
            var executionResult = commandResult.getExecutionResult();
            var getCityResult = (GetCityResult)executionResult.getResult();
            var city = getCityResult.getCity();
            cityGeoCodeName = city == null ? null : city.getGeoCodeName();
            
            if(cityGeoCodeName == null) {
                var createCityForm = GeoFormFactory.getCreateCityForm();
                
                createCityForm.setStateGeoCodeName(stateGeoCodeName);
                createCityForm.setCityName(cityName);
                createCityForm.setIsDefault("false");
                createCityForm.setSortOrder("1");
                createCityForm.setDescription(description);
                
                commandResult = zipCodeParser.getGeoService().createCity(getUserVisit(), createCityForm);
                executionResult = commandResult.getExecutionResult();
                var createCityResult = (CreateCityResult)executionResult.getResult();
                cityGeoCodeName = createCityResult.getGeoCodeName();
            }
            
            if(cityGeoCodeName != null) {
                zipCodeParser.getLog().info("put stateGeoCodeName = \"" + stateGeoCodeName + ",\" cityName = \"" + cityName + ",\" cityGeoCodeName = \"" + cityGeoCodeName + "\"");
                citiesByState.put(cityName, cityGeoCodeName);
            } else {
                zipCodeParser.getLog().error("Unknown and unable to create stateGeoCodeName = \"" + stateGeoCodeName + ",\" cityName = \"" + cityName + "\"");
            }
        }
        
        return cityGeoCodeName;
    }
    
    private String getStateGeoCodeName(ZipCodeData zipCodeData) {
        var postal2Letter = zipCodeData.getStateAbbreviation();
        var stateGeoCodeName = (String)zipCodeParser.getStateGeoCodeNames().get(postal2Letter);
        
        if(stateGeoCodeName == null) {
            var getStateForm = GeoFormFactory.getGetStateForm();
            
            getStateForm.setCountryGeoCodeName(zipCodeParser.getCountryGeoCodeName());
            getStateForm.setPostal2Letter(postal2Letter);

            var commandResult = zipCodeParser.getGeoService().getState(getUserVisit(), getStateForm);
            var executionResult = commandResult.getExecutionResult();
            var getStateResult = (GetStateResult)executionResult.getResult();
            var state = getStateResult.getState();
            stateGeoCodeName = state == null ? null : state.getGeoCodeName();
            
            if(stateGeoCodeName != null) {
                zipCodeParser.getLog().info("put postal2Letter = " + postal2Letter + ", stateGeoCodeName = " + stateGeoCodeName);
                zipCodeParser.getStateGeoCodeNames().put(postal2Letter, stateGeoCodeName);
            } else {
                zipCodeParser.getLog().error("Unknown postal2Letter = \"" + postal2Letter + "\"");
            }
        }
        
        return stateGeoCodeName;
    }
    
    @Override
    public void run() {
        while(!queue.isEmpty() || !queue.onEnd()) {
            var stateZipCodeList = queue.take();

            if(stateZipCodeList != null) {
                for(var zipCodeData : stateZipCodeList) {
                    var zipCodeGeoCodeName = loadZipCode(zipCodeData);

                    if(zipCodeGeoCodeName != null) {
                        var stateGeoCodeName = getStateGeoCodeName(zipCodeData);

                        if(stateGeoCodeName != null) {
                            var addZipCodeToCityForm = GeoFormFactory.getAddZipCodeToCityForm();
                            var countyGeoCodeName = loadCounty(stateGeoCodeName, zipCodeData);
                            var cityGeoCodeName = loadCity(stateGeoCodeName, zipCodeData);

                            // TODO: there should be a way to test to see if the relationship between zip code and city exists first
                            addZipCodeToCityForm.setCityGeoCodeName(cityGeoCodeName);
                            addZipCodeToCityForm.setZipCodeGeoCodeName(zipCodeGeoCodeName);
                            var commandResult = zipCodeParser.getGeoService().addZipCodeToCity(getUserVisit(), addZipCodeToCityForm);
                            var executionResult = commandResult.getExecutionResult();

                            if(executionResult.getHasErrors()) {
                                var messages = executionResult.getExecutionErrors();

                                for(Iterator executionErrors = messages.get(Messages.EXECUTION_ERROR); executionErrors.hasNext(); ) {
                                    var message = (Message)executionErrors.next();

                                    if(!message.getKey().equals("AddZipCodeToCity.DuplicateGeoCodeRelationship")) {
                                        zipCodeParser.getLog().error("addZipCodeToCity error: " + message + ", zipCodeData = " + zipCodeData);
                                    }
                                }
                            }

                            if(countyGeoCodeName != null) {
                                var addCityToCountyForm = GeoFormFactory.getAddCityToCountyForm();

                                // TODO: there should be a way to test to see if the relationship between city and county exists first
                                addCityToCountyForm.setCityGeoCodeName(cityGeoCodeName);
                                addCityToCountyForm.setCountyGeoCodeName(countyGeoCodeName);
                                commandResult = zipCodeParser.getGeoService().addCityToCounty(getUserVisit(), addCityToCountyForm);
                                executionResult = commandResult.getExecutionResult();

                                if(executionResult.getHasErrors()) {
                                    var messages = executionResult.getExecutionErrors();

                                    for(Iterator executionErrors = messages.get(Messages.EXECUTION_ERROR); executionErrors.hasNext(); ) {
                                        var message = (Message)executionErrors.next();

                                        if(!message.getKey().equals("AddCityToCounty.DuplicateGeoCodeRelationship")) {
                                            zipCodeParser.getLog().error("addCityToCounty error: " + message + ", zipCodeData = " + zipCodeData);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        
        clearUserVisit();
    }
}
