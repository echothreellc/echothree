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

package com.echothree.ui.cli.amazon.batch.order.content;

import java.util.HashMap;
import java.util.Map;

public class AmazonOrders {

    private Map<String, AmazonOrder> amazonOrders = new HashMap<>();
    private String batchName;

    /**
     * Returns the amazonOrders.
     * @return the amazonOrders
     */
    public Map<String, AmazonOrder> getAmazonOrders() {
        return amazonOrders;
    }

    /**
     * Sets the amazonOrders.
     * @param amazonOrders the amazonOrders to set
     */
    public void setAmazonOrders(Map<String, AmazonOrder> amazonOrders) {
        this.amazonOrders = amazonOrders;
    }

    /**
     * Returns the batchName.
     * @return the batchName
     */
    public String getBatchName() {
        return batchName;
    }

    /**
     * Sets the batchName.
     * @param batchName the batchName to set
     */
    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

}
