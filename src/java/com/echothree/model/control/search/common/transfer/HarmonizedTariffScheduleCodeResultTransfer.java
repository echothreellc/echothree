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

package com.echothree.model.control.search.common.transfer;

import com.echothree.model.control.item.common.transfer.HarmonizedTariffScheduleCodeTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class HarmonizedTariffScheduleCodeResultTransfer
        extends BaseTransfer {
    
    private String countryGeoCodeName;
    private String harmonizedTariffScheduleCodeName;
    private HarmonizedTariffScheduleCodeTransfer harmonizedTariffScheduleCode;
    
    /** Creates a new instance of HarmonizedTariffScheduleCodeResultTransfer */
    public HarmonizedTariffScheduleCodeResultTransfer(String countryGeoCodeName, String harmonizedTariffScheduleCodeName,
            HarmonizedTariffScheduleCodeTransfer harmonizedTariffScheduleCode) {
        this.countryGeoCodeName = countryGeoCodeName;
        this.harmonizedTariffScheduleCodeName = harmonizedTariffScheduleCodeName;
        this.harmonizedTariffScheduleCode = harmonizedTariffScheduleCode;
    }

    /**
     * @return the countryGeoCodeName
     */
    public String getCountryGeoCodeName() {
        return countryGeoCodeName;
    }

    /**
     * @param countryGeoCodeName the countryGeoCodeName to set
     */
    public void setCountryGeoCodeName(String countryGeoCodeName) {
        this.countryGeoCodeName = countryGeoCodeName;
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
     * @return the harmonizedTariffScheduleCode
     */
    public HarmonizedTariffScheduleCodeTransfer getHarmonizedTariffScheduleCode() {
        return harmonizedTariffScheduleCode;
    }

    /**
     * @param harmonizedTariffScheduleCode the harmonizedTariffScheduleCode to set
     */
    public void setHarmonizedTariffScheduleCode(HarmonizedTariffScheduleCodeTransfer harmonizedTariffScheduleCode) {
        this.harmonizedTariffScheduleCode = harmonizedTariffScheduleCode;
    }

 }
