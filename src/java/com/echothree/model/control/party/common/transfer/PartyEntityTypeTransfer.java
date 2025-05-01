// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.model.control.party.common.transfer;

import com.echothree.model.control.core.common.transfer.EntityTypeTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class PartyEntityTypeTransfer
        extends BaseTransfer {

    private PartyTransfer party;
    private EntityTypeTransfer entityType;
    private Boolean confirmDelete;

    /** Creates a new instance of PartyEntityTypeTransfer */
    public PartyEntityTypeTransfer(PartyTransfer party, EntityTypeTransfer entityType, Boolean confirmDelete) {
        this.party = party;
        this.entityType = entityType;
        this.confirmDelete = confirmDelete;
    }

    public PartyTransfer getParty() {
        return party;
    }

    public void setParty(PartyTransfer party) {
        this.party = party;
    }

    public EntityTypeTransfer getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityTypeTransfer entityType) {
        this.entityType = entityType;
    }

    public Boolean getConfirmDelete() {
        return confirmDelete;
    }

    public void setConfirmDelete(Boolean confirmDelete) {
        this.confirmDelete = confirmDelete;
    }

}
