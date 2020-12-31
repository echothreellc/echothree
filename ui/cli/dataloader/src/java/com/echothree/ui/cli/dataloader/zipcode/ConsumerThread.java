// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.ui.cli.dataloader.zipcode;

import com.echothree.control.user.geo.common.form.AddCityToCountyForm;
import com.echothree.control.user.geo.common.form.AddZipCodeToCityForm;
import com.echothree.control.user.geo.common.form.CreateCityForm;
import com.echothree.control.user.geo.common.form.CreateCountyForm;
import com.echothree.control.user.geo.common.form.CreateZipCodeForm;
import com.echothree.control.user.geo.common.form.GeoFormFactory;
import com.echothree.control.user.geo.common.form.GetCityForm;
import com.echothree.control.user.geo.common.form.GetCountyForm;
import com.echothree.control.user.geo.common.form.GetStateForm;
import com.echothree.control.user.geo.common.form.GetZipCodeForm;
import com.echothree.control.user.geo.common.result.CreateCityResult;
import com.echothree.control.user.geo.common.result.CreateCountyResult;
import com.echothree.control.user.geo.common.result.CreateZipCodeResult;
import com.echothree.control.user.geo.common.result.GetCityResult;
import com.echothree.control.user.geo.common.result.GetCountyResult;
import com.echothree.control.user.geo.common.result.GetStateResult;
import com.echothree.control.user.geo.common.result.GetZipCodeResult;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.collection.SmartQueue;
import com.echothree.util.common.string.StringUtils;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.util.common.message.Message;
import com.echothree.util.common.message.Messages;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ConsumerThread
        extends Thread {
    
    private SmartQueue queue;
    private ZipCodeParser zipCodeParser;
    private UserVisitPK userVisitPK;
    
    /** Creates a new instance of ConsumerThread */
    public ConsumerThread(SmartQueue queue, ZipCodeParser zipCodeParser) {
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
        String zipCodeName = zipCodeData.getZipCode();
        String zipCodeGeoCodeName = (String)zipCodeParser.getZipCodeGeoCodeNames().get(zipCodeName);
        
        if(zipCodeGeoCodeName == null) {
            GetZipCodeForm getZipCodeForm = GeoFormFactory.getGetZipCodeForm();
            
            getZipCodeForm.setCountryGeoCodeName(zipCodeParser.getCountryGeoCodeName());
            getZipCodeForm.setZipCodeName(zipCodeName);
            
            CommandResult commandResult = zipCodeParser.getGeoService().getZipCode(getUserVisit(), getZipCodeForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetZipCodeResult getZipCodeResult = (GetZipCodeResult)executionResult.getResult();
            var postalCode = getZipCodeResult.getPostalCode();
            zipCodeGeoCodeName = postalCode == null ? null : postalCode.getGeoCodeName();

            if(zipCodeGeoCodeName == null) {
                CreateZipCodeForm createZipCodeForm = GeoFormFactory.getCreateZipCodeForm();
                
                createZipCodeForm.setCountryGeoCodeName(zipCodeParser.getCountryGeoCodeName());
                createZipCodeForm.setZipCodeName(zipCodeName);
                createZipCodeForm.setSortOrder("1");
                createZipCodeForm.setIsDefault(Boolean.FALSE.toString());
                createZipCodeForm.setDescription(zipCodeName);
                
                commandResult = zipCodeParser.getGeoService().createZipCode(getUserVisit(), createZipCodeForm);
                executionResult = commandResult.getExecutionResult();
                CreateZipCodeResult createZipCodeResult = (CreateZipCodeResult)executionResult.getResult();
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
        String description = zipCodeData.getCountyName();
        String countyGeoCodeName = null;
        
        if(description != null) {
            String countyNumber = zipCodeData.getCountyNumber();
            String countyName = StringUtils.getInstance().cleanStringToName(description);
            Map countiesByState = (Map)zipCodeParser.getCountyGeoCodeNames().get(stateGeoCodeName);
            
            if(countiesByState == null) {
                countiesByState = Collections.synchronizedMap(new HashMap());
                zipCodeParser.getCountyGeoCodeNames().put(stateGeoCodeName, countiesByState);
            }
            
            countyGeoCodeName = (String)countiesByState.get(countyName);
            
            if(countyGeoCodeName == null && countyNumber.length() > 0 && countyName.length() > 0) {
                GetCountyForm getCountyForm = GeoFormFactory.getGetCountyForm();
                
                getCountyForm.setStateGeoCodeName(stateGeoCodeName);
                getCountyForm.setCountyName(countyName);
                
                CommandResult commandResult = zipCodeParser.getGeoService().getCounty(getUserVisit(), getCountyForm);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetCountyResult getCountyResult = (GetCountyResult)executionResult.getResult();
                var country = getCountyResult.getCounty();
                countyGeoCodeName = country == null ? null : country.getGeoCodeName();
                
                if(countyGeoCodeName == null) {
                    CreateCountyForm createCountyForm = GeoFormFactory.getCreateCountyForm();
                    
                    createCountyForm.setStateGeoCodeName(stateGeoCodeName);
                    createCountyForm.setCountyName(countyName);
                    createCountyForm.setCountyNumber(countyNumber);
                    createCountyForm.setIsDefault("false");
                    createCountyForm.setSortOrder("1");
                    createCountyForm.setDescription(zipCodeData.getCountyName());
                    
                    commandResult = zipCodeParser.getGeoService().createCounty(getUserVisit(), createCountyForm);
                    executionResult = commandResult.getExecutionResult();
                    CreateCountyResult createCountyResult = (CreateCountyResult)executionResult.getResult();
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
        String description = zipCodeData.getCityStateName();
        String cityName = StringUtils.getInstance().cleanStringToName(description);
        Map citiesByState = (Map)zipCodeParser.getCityGeoCodeNames().get(stateGeoCodeName);
        
        if(citiesByState == null) {
            citiesByState = Collections.synchronizedMap(new HashMap());
            zipCodeParser.getCityGeoCodeNames().put(stateGeoCodeName, citiesByState);
        }
        
        var cityGeoCodeName = (String)citiesByState.get(cityName);
        
        if(cityGeoCodeName == null) {
            GetCityForm getCityForm = GeoFormFactory.getGetCityForm();
            
            getCityForm.setStateGeoCodeName(stateGeoCodeName);
            getCityForm.setCityName(cityName);
            
            CommandResult commandResult = zipCodeParser.getGeoService().getCity(getUserVisit(), getCityForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetCityResult getCityResult = (GetCityResult)executionResult.getResult();
            var city = getCityResult.getCity();
            cityGeoCodeName = city == null ? null : city.getGeoCodeName();
            
            if(cityGeoCodeName == null) {
                CreateCityForm createCityForm = GeoFormFactory.getCreateCityForm();
                
                createCityForm.setStateGeoCodeName(stateGeoCodeName);
                createCityForm.setCityName(cityName);
                createCityForm.setIsDefault("false");
                createCityForm.setSortOrder("1");
                createCityForm.setDescription(description);
                
                commandResult = zipCodeParser.getGeoService().createCity(getUserVisit(), createCityForm);
                executionResult = commandResult.getExecutionResult();
                CreateCityResult createCityResult = (CreateCityResult)executionResult.getResult();
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
        String postal2Letter = zipCodeData.getStateAbbreviation();
        String stateGeoCodeName = (String)zipCodeParser.getStateGeoCodeNames().get(postal2Letter);
        
        if(stateGeoCodeName == null) {
            GetStateForm getStateForm = GeoFormFactory.getGetStateForm();
            
            getStateForm.setCountryGeoCodeName(zipCodeParser.getCountryGeoCodeName());
            getStateForm.setPostal2Letter(postal2Letter);
            
            CommandResult commandResult = zipCodeParser.getGeoService().getState(getUserVisit(), getStateForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetStateResult getStateResult = (GetStateResult)executionResult.getResult();
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
            ArrayList stateZipCodeList = (ArrayList)queue.take();
            
            for(Iterator iter = stateZipCodeList.iterator(); iter.hasNext();) {
                ZipCodeData zipCodeData = (ZipCodeData)iter.next();
                String zipCodeGeoCodeName = loadZipCode(zipCodeData);
                
                if(zipCodeGeoCodeName != null) {
                    String stateGeoCodeName = getStateGeoCodeName(zipCodeData);
                    
                    if(stateGeoCodeName != null) {
                        AddZipCodeToCityForm addZipCodeToCityForm = GeoFormFactory.getAddZipCodeToCityForm();
                        String countyGeoCodeName = loadCounty(stateGeoCodeName, zipCodeData);
                        String cityGeoCodeName = loadCity(stateGeoCodeName, zipCodeData);
                        
                        // TODO: there should be a way to test to see if the relationship between zip code and city exists first
                        addZipCodeToCityForm.setCityGeoCodeName(cityGeoCodeName);
                        addZipCodeToCityForm.setZipCodeGeoCodeName(zipCodeGeoCodeName);
                        CommandResult commandResult = zipCodeParser.getGeoService().addZipCodeToCity(getUserVisit(), addZipCodeToCityForm);
                        ExecutionResult executionResult = commandResult.getExecutionResult();
                        
                        if(executionResult.getHasErrors()) {
                            Messages messages = executionResult.getExecutionErrors();
                            
                            for(Iterator executionErrors = messages.get(Messages.EXECUTION_ERROR); executionErrors.hasNext();) {
                                Message message = (Message)executionErrors.next();
                                
                                if(!message.getKey().equals("AddZipCodeToCity.DuplicateGeoCodeRelationship")) {
                                    zipCodeParser.getLog().error("addZipCodeToCity error: " + message + ", zipCodeData = " + zipCodeData);
                                }
                            }
                        }
                        
                        if(countyGeoCodeName != null) {
                            AddCityToCountyForm addCityToCountyForm = GeoFormFactory.getAddCityToCountyForm();
                            
                            // TODO: there should be a way to test to see if the relationship between city and county exists first
                            addCityToCountyForm.setCityGeoCodeName(cityGeoCodeName);
                            addCityToCountyForm.setCountyGeoCodeName(countyGeoCodeName);
                            commandResult = zipCodeParser.getGeoService().addCityToCounty(getUserVisit(), addCityToCountyForm);
                            executionResult = commandResult.getExecutionResult();
                            
                            if(executionResult.getHasErrors()) {
                                Messages messages = executionResult.getExecutionErrors();
                                
                                for(Iterator executionErrors = messages.get(Messages.EXECUTION_ERROR); executionErrors.hasNext();) {
                                    Message message = (Message)executionErrors.next();
                                    
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
        
        clearUserVisit();
    }
}
