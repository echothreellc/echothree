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

package com.echothree.model.control.item.common.transfer;

import com.echothree.model.control.geo.common.transfer.CountryTransfer;
import com.echothree.util.common.transfer.BaseTransfer;
import com.echothree.util.common.transfer.MapWrapper;

public class HarmonizedTariffScheduleCodeTransfer
        extends BaseTransfer {
    
    private CountryTransfer countryGeoCode;
    private String harmonizedTariffScheduleCodeName;
    private HarmonizedTariffScheduleCodeUnitTransfer firstHarmonizedTariffScheduleCodeUnit;
    private HarmonizedTariffScheduleCodeUnitTransfer secondHarmonizedTariffScheduleCodeUnit;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    
    private MapWrapper<HarmonizedTariffScheduleCodeUseTransfer> harmonizedTariffScheduleCodeUses; 
    
    /** Creates a new instance of HarmonizedTariffScheduleCodeTransfer */
    public HarmonizedTariffScheduleCodeTransfer(CountryTransfer countryGeoCode, String harmonizedTariffScheduleCodeName,
            HarmonizedTariffScheduleCodeUnitTransfer firstHarmonizedTariffScheduleCodeUnit,
            HarmonizedTariffScheduleCodeUnitTransfer secondHarmonizedTariffScheduleCodeUnit, Boolean isDefault, Integer sortOrder, String description) {
        this.countryGeoCode = countryGeoCode;
        this.harmonizedTariffScheduleCodeName = harmonizedTariffScheduleCodeName;
        this.firstHarmonizedTariffScheduleCodeUnit = firstHarmonizedTariffScheduleCodeUnit;
        this.secondHarmonizedTariffScheduleCodeUnit = secondHarmonizedTariffScheduleCodeUnit;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }

    /**
     * @return the countryGeoCode
     */
    public CountryTransfer getCountryGeoCode() {
        return countryGeoCode;
    }

    /**
     * @param countryGeoCode the countryGeoCode to set
     */
    public void setCountryGeoCode(CountryTransfer countryGeoCode) {
        this.countryGeoCode = countryGeoCode;
    }
    
    /**
     * @return the harmonizedTariffScheduleCodeName
     */
    public String getHarmonizedTariffScheduleCodeName() {
        return harmonizedTariffScheduleCodeName;
    }

    /**
     * @param harmonizedTariffScheduleCodeName the harmonizedTariffScheduleCodeName to set
     */
    public void setHarmonizedTariffScheduleCodeName(String harmonizedTariffScheduleCodeName) {
        this.harmonizedTariffScheduleCodeName = harmonizedTariffScheduleCodeName;
    }

    /**
     * @return the firstHarmonizedTariffScheduleCodeUnit
     */
    public HarmonizedTariffScheduleCodeUnitTransfer getFirstHarmonizedTariffScheduleCodeUnit() {
        return firstHarmonizedTariffScheduleCodeUnit;
    }

    /**
     * @param firstHarmonizedTariffScheduleCodeUnit the firstHarmonizedTariffScheduleCodeUnit to set
     */
    public void setFirstHarmonizedTariffScheduleCodeUnit(HarmonizedTariffScheduleCodeUnitTransfer firstHarmonizedTariffScheduleCodeUnit) {
        this.firstHarmonizedTariffScheduleCodeUnit = firstHarmonizedTariffScheduleCodeUnit;
    }

    /**
     * @return the secondHarmonizedTariffScheduleCodeUnit
     */
    public HarmonizedTariffScheduleCodeUnitTransfer getSecondHarmonizedTariffScheduleCodeUnit() {
        return secondHarmonizedTariffScheduleCodeUnit;
    }

    /**
     * @param secondHarmonizedTariffScheduleCodeUnit the secondHarmonizedTariffScheduleCodeUnit to set
     */
    public void setSecondHarmonizedTariffScheduleCodeUnit(HarmonizedTariffScheduleCodeUnitTransfer secondHarmonizedTariffScheduleCodeUnit) {
        this.secondHarmonizedTariffScheduleCodeUnit = secondHarmonizedTariffScheduleCodeUnit;
    }

    /**
     * @return the isDefault
     */
    public Boolean getIsDefault() {
        return isDefault;
    }

    /**
     * @param isDefault the isDefault to set
     */
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    /**
     * @return the sortOrder
     */
    public Integer getSortOrder() {
        return sortOrder;
    }

    /**
     * @param sortOrder the sortOrder to set
     */
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the harmonizedTariffScheduleCodeUses
     */
    public MapWrapper<HarmonizedTariffScheduleCodeUseTransfer> getHarmonizedTariffScheduleCodeUses() {
        return harmonizedTariffScheduleCodeUses;
    }

    /**
     * @param harmonizedTariffScheduleCodeUses the harmonizedTariffScheduleCodeUses to set
     */
    public void setHarmonizedTariffScheduleCodeUses(MapWrapper<HarmonizedTariffScheduleCodeUseTransfer> harmonizedTariffScheduleCodeUses) {
        this.harmonizedTariffScheduleCodeUses = harmonizedTariffScheduleCodeUses;
    }

}
