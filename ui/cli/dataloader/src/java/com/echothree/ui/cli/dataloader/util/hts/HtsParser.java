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

package com.echothree.ui.cli.dataloader.util.hts;

import com.echothree.control.user.authentication.common.AuthenticationService;
import com.echothree.control.user.authentication.common.AuthenticationUtil;
import com.echothree.control.user.geo.common.GeoService;
import com.echothree.control.user.geo.common.GeoUtil;
import com.echothree.control.user.geo.common.form.GeoFormFactory;
import com.echothree.control.user.geo.common.result.GetCountryResult;
import com.echothree.control.user.item.common.ItemService;
import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.model.control.geo.common.Countries;
import com.echothree.model.control.geo.common.GeoCodeAliasTypes;
import com.echothree.model.control.geo.common.GeoOptions;
import com.echothree.model.control.geo.common.transfer.CountryTransfer;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.naming.NamingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HtsParser {
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    private AuthenticationService authenticationService;
    private GeoService geoService;
    private ItemService itemService;
    private UserVisitPK userVisitPK;
    
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
    
    public boolean setup() {
        var result = true;
        
        try {
            setAuthenticationService(AuthenticationUtil.getHome());
            setGeoService(GeoUtil.getHome());
            setItemService(ItemUtil.getHome());
        } catch (NamingException ne) {
            result = false;
        }
        
        return result;
    }
    
    public void teardown() {
        clearUserVisit();
        setItemService(null);
        setAuthenticationService(null);
    }
    
    /** Creates a new instance of HtsParser */
    public HtsParser() {
    }
    
    public CountryTransfer getCountry(String iso2LetterName) {
        var commandForm = GeoFormFactory.getGetCountryForm();
        CountryTransfer country = null;

        commandForm.setIso2Letter(iso2LetterName);

        var options = new HashSet<String>();
        options.add(GeoOptions.CountryIncludeAliases);
        commandForm.setOptions(options);

        var commandResult = getGeoService().getCountry(getUserVisit(), commandForm);
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var getCountryResult = (GetCountryResult)executionResult.getResult();

            country = getCountryResult.getCountry();
        } else {
            logger.error(commandResult.toString());
        }
        
        return country;
    }
    
    public List<String> getCountryList() {
        return List.of(
                "us"
        );
    }
    
    private static Map<String, HtsCountryParser> htsCountryParsers;

    static {
        htsCountryParsers = Map.of(
                Countries.UNITED_STATES.name(), new HtsUnitedStatesParser()
        );
    }
    
    public void execute() {
        if(setup()) {
            var countryList = getCountryList();
            
            countryList.forEach((countryDirectory) -> {
                var country = getCountry(countryDirectory.toUpperCase(Locale.getDefault()));

                if(country != null) {
                    var countryName = country.getGeoCodeAliases().getMap().get(GeoCodeAliasTypes.COUNTRY_NAME.name()).getAlias();

                    try {
                        htsCountryParsers.get(countryName).execute(userVisitPK, geoService, itemService, country);
                    } catch(IOException ioe) {
                        logger.error("An Exception occurred:", ioe);
                    }
                }
            });
            
            teardown();
        }
    }
    
    public Logger getLog() {
        return logger;
    }
    
    public void setLog(Logger logger) {
        this.logger = logger;
    }
    
    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }
    
    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
    
    public GeoService getGeoService() {
        return geoService;
    }
    
    public void setGeoService(GeoService geoService) {
        this.geoService = geoService;
    }
    
    public ItemService getItemService() {
        return itemService;
    }
    
    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }
    
}
