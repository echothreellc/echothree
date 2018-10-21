// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.ui.cli.dataloader.hts;

import com.echothree.control.user.authentication.common.AuthenticationUtil;
import com.echothree.control.user.authentication.remote.AuthenticationService;
import com.echothree.control.user.geo.common.GeoUtil;
import com.echothree.control.user.geo.remote.GeoService;
import com.echothree.control.user.geo.remote.form.GeoFormFactory;
import com.echothree.control.user.geo.remote.form.GetCountryForm;
import com.echothree.control.user.geo.remote.result.GetCountryResult;
import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.remote.ItemService;
import com.echothree.model.control.geo.common.GeoConstants;
import com.echothree.model.control.geo.common.GeoOptions;
import com.echothree.model.control.geo.remote.transfer.CountryTransfer;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.remote.command.CommandResult;
import com.echothree.util.remote.command.ExecutionResult;
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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HtsParser {
    
    private String htsDirectory;
    
    private Log log = LogFactory.getLog(this.getClass());
    
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
            log.error(commandResult);
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
            log.error(htsDirectory + " isn't a directory.");
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
                    log.error(ioe);
                }
            });
            
            teardown();
        }
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
