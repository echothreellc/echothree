// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

package com.echothree.model.control.vendor.server.indexer;

import com.echothree.model.control.index.common.IndexFields;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.indexer.PartyIndexer;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.data.index.server.entity.Index;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class VendorIndexer
        extends PartyIndexer {
    
    VendorControl vendorControl = Session.getModelController(VendorControl.class);
    
    /** Creates a new instance of VendorIndexer */
    public VendorIndexer(final ExecutionErrorAccumulator eea, final Index index) {
        super(eea, index, PartyTypes.VENDOR.name(), IndexFields.vendorName.name());
    }
    
    @Override
    protected String getEntityNameFromParty(final Party party) {
        var vendor = vendorControl.getVendor(party);

        return vendor == null ? null : vendor.getVendorName();
    }

}
