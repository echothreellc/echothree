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

import com.echothree.util.common.transfer.BaseTransfer;

public class HarmonizedTariffScheduleCodeUseTransfer
        extends BaseTransfer {
    
    private HarmonizedTariffScheduleCodeTransfer harmonizedTariffScheduleCode;
    private HarmonizedTariffScheduleCodeUseTypeTransfer harmonizedTariffScheduleCodeUseType;
    
    /** Creates a new instance of HarmonizedTariffScheduleCodeUseTransfer */
    public HarmonizedTariffScheduleCodeUseTransfer(HarmonizedTariffScheduleCodeTransfer harmonizedTariffScheduleCode, HarmonizedTariffScheduleCodeUseTypeTransfer harmonizedTariffScheduleCodeUseType) {
        this.harmonizedTariffScheduleCode = harmonizedTariffScheduleCode;
        this.harmonizedTariffScheduleCodeUseType = harmonizedTariffScheduleCodeUseType;
    }

    /**
     * Returns the harmonizedTariffScheduleCode.
     * @return the harmonizedTariffScheduleCode
     */
    public HarmonizedTariffScheduleCodeTransfer getHarmonizedTariffScheduleCode() {
        return harmonizedTariffScheduleCode;
    }

    /**
     * Sets the harmonizedTariffScheduleCode.
     * @param harmonizedTariffScheduleCode the harmonizedTariffScheduleCode to set
     */
    public void setHarmonizedTariffScheduleCode(HarmonizedTariffScheduleCodeTransfer harmonizedTariffScheduleCode) {
        this.harmonizedTariffScheduleCode = harmonizedTariffScheduleCode;
    }

    /**
     * Returns the harmonizedTariffScheduleCodeUseType.
     * @return the harmonizedTariffScheduleCodeUseType
     */
    public HarmonizedTariffScheduleCodeUseTypeTransfer getHarmonizedTariffScheduleCodeUseType() {
        return harmonizedTariffScheduleCodeUseType;
    }

    /**
     * Sets the harmonizedTariffScheduleCodeUseType.
     * @param harmonizedTariffScheduleCodeUseType the harmonizedTariffScheduleCodeUseType to set
     */
    public void setHarmonizedTariffScheduleCodeUseType(HarmonizedTariffScheduleCodeUseTypeTransfer harmonizedTariffScheduleCodeUseType) {
        this.harmonizedTariffScheduleCodeUseType = harmonizedTariffScheduleCodeUseType;
    }

}
