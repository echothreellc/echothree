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

public class OrderRoleTypeTransfer
        extends BaseTransfer {

    private String orderRoleTypeName;
    private Integer sortOrder;
    private String description;

    /** Creates a new instance of OrderRoleTypeTransfer */
    public OrderRoleTypeTransfer(String orderRoleTypeName, Integer sortOrder, String description) {
        this.orderRoleTypeName = orderRoleTypeName;
        this.sortOrder = sortOrder;
        this.description = description;
    }

    /**
     * Returns the orderRoleTypeName.
     * @return the orderRoleTypeName
     */
    public String getOrderRoleTypeName() {
        return orderRoleTypeName;
    }

    /**
     * Sets the orderRoleTypeName.
     * @param orderRoleTypeName the orderRoleTypeName to set
     */
    public void setOrderRoleTypeName(String orderRoleTypeName) {
        this.orderRoleTypeName = orderRoleTypeName;
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

}
