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

package com.echothree.ui.cli.dataloader.util.hts;

import com.echothree.control.user.authentication.common.AuthenticationService;
import com.echothree.control.user.authentication.common.AuthenticationUtil;
import com.echothree.control.user.geo.common.GeoService;
import com.echothree.control.user.geo.common.GeoUtil;
import com.echothree.control.user.geo.common.form.GeoFormFactory;
import com.echothree.control.user.geo.common.form.GetCountryForm;
import com.echothree.control.user.geo.common.result.GetCountryResult;
import com.echothree.control.user.item.common.ItemService;
import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.model.control.geo.common.GeoConstants;
import com.echothree.model.control.geo.common.GeoOptions;
import com.echothree.model.control.geo.common.transfer.CountryTransfer;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.naming.NamingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HtsParser {
    
    private String htsDirectory;

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
        boolean result = true;
        
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
    public HtsParser(String htsDirectory) {
        this.htsDirectory = htsDirectory;
    }
    
    public CountryTransfer getCountry(String iso2LetterName) {
        GetCountryForm commandForm = GeoFormFactory.getGetCountryForm();
        CountryTransfer country = null;
        
        commandForm.setIso2Letter(iso2LetterName);
        
        Set<String> options = new HashSet<>();
        options.add(GeoOptions.CountryIncludeAliases);
        commandForm.setOptions(options);

        CommandResult commandResult = getGeoService().getCountry(getUserVisit(), commandForm);
        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetCountryResult getCountryResult = (GetCountryResult)executionResult.getResult();
            country = getCountryResult.getCountry();
        } else {
            logger.error(commandResult.toString());
        }
        
        return country;
    }
    
    Map<String, CountryTransfer> countries = new HashMap<>();
    
    FileFilter countryFileFilter = (File file) -> {
        boolean keepIt = false;
        String name = file.getName();
        
        if(file.isDirectory() && !name.startsWith(".")) {
            CountryTransfer country = getCountry(name.toUpperCase());
            
            if(country != null) {
                countries.put(name, country);
                keepIt = true;
            }
        }
        
        return keepIt;
    };

    public List<File> getCountryDirectories() {
        File directory = new File(htsDirectory);
        List<File> fileList = Collections.emptyList();
        
        if(directory.isDirectory()) {
            File[] files = directory.listFiles(countryFileFilter);
            
            fileList = Arrays.asList(files);
        } else {
            logger.error(htsDirectory + " isn't a directory.");
        }
        
        return fileList;
    }
    
    private static Map<String, HtsCountryParser> htsCountryParsers;

    static {
        Map<String, HtsCountryParser> htsCountryParsersMap = new HashMap<>(1);

        htsCountryParsersMap.put(GeoConstants.CountryName_UNITED_STATES, new HtsUnitedStatesParser());
        htsCountryParsers = Collections.unmodifiableMap(htsCountryParsersMap);
    }
    
    public void execute() {
        if(setup()) {
            List<File> countryDirectories = getCountryDirectories();
            
            countryDirectories.stream().forEach((countryDirectory) -> {
                CountryTransfer country = countries.get(countryDirectory.getName());
                String countryName = country.getGeoCodeAliases().getMap().get(GeoConstants.GeoCodeAliasType_COUNTRY_NAME).getAlias();
                
                try {
                    htsCountryParsers.get(countryName).execute(userVisitPK, geoService, itemService, countryDirectory, country);
                } catch(IOException ioe) {
                    logger.error("An Exception occurred:", ioe);
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
