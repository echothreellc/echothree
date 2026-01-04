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

package com.echothree.ui.cli.dataloader.util.zipcode;

import com.echothree.control.user.authentication.common.AuthenticationUtil;
import com.echothree.control.user.authentication.common.AuthenticationService;
import com.echothree.control.user.geo.common.GeoUtil;
import com.echothree.control.user.geo.common.GeoService;
import com.echothree.control.user.geo.common.form.GeoFormFactory;
import com.echothree.control.user.geo.common.result.GetCountryResult;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.collection.SmartQueue;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ZipCodeParser {
    
    private Log log = LogFactory.getLog(this.getClass());
    
    private AuthenticationService authenticationService = null;
    private GeoService geoService = null;
    private UserVisitPK userVisitPK = null;
    
    private String countryGeoCodeName = null;
    
    private Map cityGeoCodeNames = Collections.synchronizedMap(new HashMap());
    private Map countyGeoCodeNames = Collections.synchronizedMap(new HashMap());
    private Map stateGeoCodeNames = Collections.synchronizedMap(new HashMap(75));
    private Map zipCodeGeoCodeNames = Collections.synchronizedMap(new HashMap(45000));
    
    private UserVisitPK getUserVisit() {
        if(userVisitPK == null) {
            userVisitPK = getAuthenticationService().getDataLoaderUserVisit();
        }
        
        return userVisitPK;
    }
    
    private void clearUserVisit() {
        if(userVisitPK != null) {
            getAuthenticationService().invalidateUserVisit(userVisitPK);
            
            userVisitPK = null;
        }
    }
    
    public void setupCountryGeoCodeName() {
        var getCountryForm = GeoFormFactory.getGetCountryForm();
        
        getCountryForm.setIso2Letter("US");

        var commandResult = getGeoService().getCountry(getUserVisit(), getCountryForm);
        var executionResult = commandResult.getExecutionResult();
        var getCountryResult = (GetCountryResult)executionResult.getResult();
        setCountryGeoCodeName(getCountryResult.getCountry().getGeoCodeName());
    }
    
    public boolean setup() {
        var result = true;
        
        try {
            setAuthenticationService(AuthenticationUtil.getHome());
            setGeoService(GeoUtil.getHome());
        } catch (NamingException ne) {
            result = false;
        }
        
        setupCountryGeoCodeName();
        
        return result;
    }
    
    public void teardown() {
        clearUserVisit();
        setGeoService(null);
        setAuthenticationService(null);
    }
    
    /** Creates a new instance of ZipCodeParser */
    public ZipCodeParser() {
    }
    
    public void execute()
        throws InterruptedException {
        if(setup()) {
            var queue = new SmartQueue<List<ZipCodeData>>(false, false, 8, null);
            var prod = new ProducerThread(queue);
            var cons1 = new ConsumerThread(queue, this);
            var cons2 = new ConsumerThread(queue, this);
            var cons3 = new ConsumerThread(queue, this);
            var cons4 = new ConsumerThread(queue, this);
            
            prod.setName("Producer");
            cons1.setName("Consumer 1");
            cons2.setName("Consumer 2");
            cons3.setName("Consumer 3");
            cons4.setName("Consumer 4");
            
            prod.start();
            cons1.start();
            cons2.start();
            cons3.start();
            cons4.start();
            
            cons1.join();
            cons2.join();
            cons3.join();
            cons4.join();
            
            teardown();
        }
    }
    
    public GeoService getGeoService() {
        return geoService;
    }
    
    public void setGeoService(GeoService geoService) {
        this.geoService = geoService;
    }
    
    public String getCountryGeoCodeName() {
        return countryGeoCodeName;
    }
    
    public void setCountryGeoCodeName(String countryGeoCodeName) {
        this.countryGeoCodeName = countryGeoCodeName;
    }
    
    public Map getCityGeoCodeNames() {
        return cityGeoCodeNames;
    }
    
    public void setCityGeoCodeNames(Map cityGeoCodeNames) {
        this.cityGeoCodeNames = cityGeoCodeNames;
    }
    
    public Map getCountyGeoCodeNames() {
        return countyGeoCodeNames;
    }
    
    public void setCountyGeoCodeNames(Map countyGeoCodeNames) {
        this.countyGeoCodeNames = countyGeoCodeNames;
    }
    
    public Map getStateGeoCodeNames() {
        return stateGeoCodeNames;
    }
    
    public void setStateGeoCodeNames(Map stateGeoCodeNames) {
        this.stateGeoCodeNames = stateGeoCodeNames;
    }
    
    public Map getZipCodeGeoCodeNames() {
        return zipCodeGeoCodeNames;
    }
    
    public void setZipCodeGeoCodeNames(Map zipCodeGeoCodeNames) {
        this.zipCodeGeoCodeNames = zipCodeGeoCodeNames;
    }
    
    public Log getLog() {
        return log;
    }
    
    public void setLog(Log log) {
        this.log = log;
    }
    
    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }
    
    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
    
}
