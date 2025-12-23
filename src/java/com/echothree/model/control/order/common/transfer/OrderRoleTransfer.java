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

import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class OrderRoleTransfer
        extends BaseTransfer {

    private PartyTransfer party;
    private OrderRoleTypeTransfer orderRoleType;

    /** Creates a new instance of OrderRoleTransfer */
    public OrderRoleTransfer(PartyTransfer party, OrderRoleTypeTransfer orderRoleType) {
        this.party = party;
        this.orderRoleType = orderRoleType;
    }

    /**
     * Returns the party.
     * @return the party
     */
    public PartyTransfer getParty() {
        return party;
    }

    /**
     * Sets the party.
     * @param party the party to set
     */
    public void setParty(PartyTransfer party) {
        this.party = party;
    }

    /**
     * Returns the orderRoleType.
     * @return the orderRoleType
     */
    public OrderRoleTypeTransfer getOrderRoleType() {
        return orderRoleType;
    }

    /**
     * Sets the orderRoleType.
     * @param orderRoleType the orderRoleType to set
     */
    public void setOrderRoleType(OrderRoleTypeTransfer orderRoleType) {
        this.orderRoleType = orderRoleType;
    }

}
