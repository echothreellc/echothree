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

package com.echothree.model.control.order.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class OrderLineTimeTransfer
        extends BaseTransfer {

    private OrderTimeTypeTransfer orderTimeType;
    private Long unformattedTime;
    private String time;
    
    /** Creates a new instance of OrderLineTimeTransfer */
    public OrderLineTimeTransfer(OrderTimeTypeTransfer orderTimeType, Long unformattedTime, String time) {
        this.orderTimeType = orderTimeType;
        this.unformattedTime = unformattedTime;
        this.time = time;
    }

    /**
     * Returns the orderTimeType.
     * @return the orderTimeType
     */
    public OrderTimeTypeTransfer getOrderTimeType() {
        return orderTimeType;
    }

    /**
     * Sets the orderTimeType.
     * @param orderTimeType the orderTimeType to set
     */
    public void setOrderTimeType(OrderTimeTypeTransfer orderTimeType) {
        this.orderTimeType = orderTimeType;
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
