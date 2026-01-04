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

package com.echothree.model.control.picklist.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class PicklistTimeTransfer
        extends BaseTransfer {

    private PicklistTimeTypeTransfer picklistTimeType;
    private Long unformattedTime;
    private String time;
    
    /** Creates a new instance of PicklistTimeTransfer */
    public PicklistTimeTransfer(PicklistTimeTypeTransfer picklistTimeType, Long unformattedTime, String time) {
        this.picklistTimeType = picklistTimeType;
        this.unformattedTime = unformattedTime;
        this.time = time;
    }

    /**
     * Returns the picklistTimeType.
     * @return the picklistTimeType
     */
    public PicklistTimeTypeTransfer getPicklistTimeType() {
        return picklistTimeType;
    }

    /**
     * Sets the picklistTimeType.
     * @param picklistTimeType the picklistTimeType to set
     */
    public void setPicklistTimeType(PicklistTimeTypeTransfer picklistTimeType) {
        this.picklistTimeType = picklistTimeType;
    }

    /**
     * Returns the unformattedTime.
     * @return the unformattedTime
     */
    public Long getUnformattedTime() {
        return unformattedTime;
    }

    /**
     * Sets the unformattedTime.
     * @param unformattedTime the unformattedTime to set
     */
    public void setUnformattedTime(Long unformattedTime) {
        this.unformattedTime = unformattedTime;
    }

    /**
     * Returns the time.
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * Sets the time.
     * @param time the time to set
     */
    public void setTime(String time) {
        this.time = time;
    }

}
