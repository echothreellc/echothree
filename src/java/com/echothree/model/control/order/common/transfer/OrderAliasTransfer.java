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

public class OrderAliasTransfer
        extends BaseTransfer {
    
    private OrderAliasTypeTransfer orderAliasType;
    private String alias;
    
    /** Creates a new instance of OrderAliasTransfer */
    public OrderAliasTransfer(OrderAliasTypeTransfer orderAliasType, String alias) {
        this.orderAliasType = orderAliasType;
        this.alias = alias;
    }

    public OrderAliasTypeTransfer getOrderAliasType() {
        return orderAliasType;
    }

    public void setOrderAliasType(OrderAliasTypeTransfer orderAliasType) {
        this.orderAliasType = orderAliasType;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

}
