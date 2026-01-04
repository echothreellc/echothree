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
     * Returns the countryGeoCode.
     * @return the countryGeoCode
     */
    public CountryTransfer getCountryGeoCode() {
        return countryGeoCode;
    }

    /**
     * Sets the countryGeoCode.
     * @param countryGeoCode the countryGeoCode to set
     */
    public void setCountryGeoCode(CountryTransfer countryGeoCode) {
        this.countryGeoCode = countryGeoCode;
    }
    
    /**
     * Returns the harmonizedTariffScheduleCodeName.
     * @return the harmonizedTariffScheduleCodeName
     */
    public String getHarmonizedTariffScheduleCodeName() {
        return harmonizedTariffScheduleCodeName;
    }

    /**
     * Sets the harmonizedTariffScheduleCodeName.
     * @param harmonizedTariffScheduleCodeName the harmonizedTariffScheduleCodeName to set
     */
    public void setHarmonizedTariffScheduleCodeName(String harmonizedTariffScheduleCodeName) {
        this.harmonizedTariffScheduleCodeName = harmonizedTariffScheduleCodeName;
    }

    /**
     * Returns the firstHarmonizedTariffScheduleCodeUnit.
     * @return the firstHarmonizedTariffScheduleCodeUnit
     */
    public HarmonizedTariffScheduleCodeUnitTransfer getFirstHarmonizedTariffScheduleCodeUnit() {
        return firstHarmonizedTariffScheduleCodeUnit;
    }

    /**
     * Sets the firstHarmonizedTariffScheduleCodeUnit.
     * @param firstHarmonizedTariffScheduleCodeUnit the firstHarmonizedTariffScheduleCodeUnit to set
     */
    public void setFirstHarmonizedTariffScheduleCodeUnit(HarmonizedTariffScheduleCodeUnitTransfer firstHarmonizedTariffScheduleCodeUnit) {
        this.firstHarmonizedTariffScheduleCodeUnit = firstHarmonizedTariffScheduleCodeUnit;
    }

    /**
     * Returns the secondHarmonizedTariffScheduleCodeUnit.
     * @return the secondHarmonizedTariffScheduleCodeUnit
     */
    public HarmonizedTariffScheduleCodeUnitTransfer getSecondHarmonizedTariffScheduleCodeUnit() {
        return secondHarmonizedTariffScheduleCodeUnit;
    }

    /**
     * Sets the secondHarmonizedTariffScheduleCodeUnit.
     * @param secondHarmonizedTariffScheduleCodeUnit the secondHarmonizedTariffScheduleCodeUnit to set
     */
    public void setSecondHarmonizedTariffScheduleCodeUnit(HarmonizedTariffScheduleCodeUnitTransfer secondHarmonizedTariffScheduleCodeUnit) {
        this.secondHarmonizedTariffScheduleCodeUnit = secondHarmonizedTariffScheduleCodeUnit;
    }

    /**
     * Returns the isDefault.
     * @return the isDefault
     */
    public Boolean getIsDefault() {
        return isDefault;
    }

    /**
     * Sets the isDefault.
     * @param isDefault the isDefault to set
     */
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    /**
     * Returns the sortOrder.
     * @return the sortOrder
     */
    public Integer getSortOrder() {
        return sortOrder;
    }

    /**
     * Sets the sortOrder.
     * @param sortOrder the sortOrder to set
     */
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * Returns the description.
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the harmonizedTariffScheduleCodeUses.
     * @return the harmonizedTariffScheduleCodeUses
     */
    public MapWrapper<HarmonizedTariffScheduleCodeUseTransfer> getHarmonizedTariffScheduleCodeUses() {
        return harmonizedTariffScheduleCodeUses;
    }

    /**
     * Sets the harmonizedTariffScheduleCodeUses.
     * @param harmonizedTariffScheduleCodeUses the harmonizedTariffScheduleCodeUses to set
     */
    public void setHarmonizedTariffScheduleCodeUses(MapWrapper<HarmonizedTariffScheduleCodeUseTransfer> harmonizedTariffScheduleCodeUses) {
        this.harmonizedTariffScheduleCodeUses = harmonizedTariffScheduleCodeUses;
    }

}
