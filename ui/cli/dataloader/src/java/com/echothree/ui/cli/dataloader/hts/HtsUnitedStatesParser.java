// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

import com.echothree.control.user.geo.common.GeoService;
import com.echothree.control.user.item.common.ItemService;
import com.echothree.model.control.core.common.MimeTypes;
import com.echothree.model.control.geo.common.transfer.CountryTransfer;
import com.echothree.model.control.item.common.ItemConstants;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.string.StringUtils;
import static com.google.common.base.Charsets.UTF_8;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HtsUnitedStatesParser
        extends HtsCountryParser<HtsUnitedStatesCode> {
    
    private final Log log = LogFactory.getLog(this.getClass());
    
    @Override
    public void execute(UserVisitPK userVisitPK, GeoService geoService, ItemService itemService, File htsDirectory, CountryTransfer country)
            throws IOException {
        super.execute(userVisitPK, geoService, itemService, htsDirectory, country);
        
        Map<String, HtsUnitedStatesCode> importCodes = readCodes(new File(htsDirectory.getAbsolutePath() + File.separator + "impaes.txt"));
        log.info(importCodes.size() + " import codes");
        Map<String, HtsUnitedStatesCode> exportCodes = readCodes(new File(htsDirectory.getAbsolutePath() + File.separator + "expaes.txt"));
        log.info(exportCodes.size() + " export codes");
        
        determineAndApplyChanges(importCodes, exportCodes);
    }
    
    
    private Map<String, HtsUnitedStatesCode> readCodes(File codes)
            throws IOException {
        Map<String, HtsUnitedStatesCode> htsUnitedStatesCodes = new HashMap<>();
        
        try (BufferedReader in = Files.newBufferedReader(codes.toPath(), UTF_8)) {
            for(String codeLine = in.readLine(); codeLine != null; codeLine = in.readLine()) {
                HtsUnitedStatesCode husc = new HtsUnitedStatesCode(codeLine);
                
                htsUnitedStatesCodes.put(husc.getCommodity(), husc);
            }
        }
        
        return htsUnitedStatesCodes;
    }
    
    @Override
    public Set<String> getHarmonizedTariffScheduleCodeUses(Map<String, HtsUnitedStatesCode> importCodes, Map<String, HtsUnitedStatesCode> exportCodes, HtsUnitedStatesCode htsc) {
        Set<String> harmonizedTariffScheduleCodeUseNames = new HashSet<>();
        String harmonizedTariffScheduleCodeName = htsc.getCommodity();
        
        if(importCodes.containsKey(harmonizedTariffScheduleCodeName)) {
            harmonizedTariffScheduleCodeUseNames.add(ItemConstants.HarmonizedTariffScheduleCodeUseType_IMPORT);
        }
        
        if(exportCodes.containsKey(harmonizedTariffScheduleCodeName)) {
            harmonizedTariffScheduleCodeUseNames.add(ItemConstants.HarmonizedTariffScheduleCodeUseType_EXPORT);
        }
        
        return harmonizedTariffScheduleCodeUseNames;
    }
    
    @Override
    public String getHarmonizedTariffScheduleCodeName(HtsUnitedStatesCode htsc) {
        return htsc.getCommodity();
    }
    
    @Override
    public String getFirstHarmonizedTariffScheduleCodeUnitName(HtsUnitedStatesCode htsc) {
        return htsc.getQuantity1();
    }
    
    @Override
    public String getSecondHarmonizedTariffScheduleCodeUnitName(HtsUnitedStatesCode htsc) {
        return htsc.getQuantity2();
    }
    
    @Override
    public String getDescription(HtsUnitedStatesCode htsc) {
        return StringUtils.getInstance().left(htsc.getDescrip1(), 80);
    }
    
    @Override
    public String getOverviewMimeTypeName(HtsUnitedStatesCode htsc) {
        return MimeTypes.TEXT_PLAIN.mimeTypeName();
    }
    
    @Override
    public String getOverview(HtsUnitedStatesCode htsc) {
        return htsc.getDescrip1();
    }
    
}
