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

package com.echothree.model.control.carrier.server.transfer;

import com.echothree.util.server.transfer.BaseTransferCaches;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class CarrierTransferCaches
        extends BaseTransferCaches {
    
    CarrierTransferCache carrierTransferCache;
    CarrierTypeTransferCache carrierTypeTransferCache;
    CarrierTypeDescriptionTransferCache carrierTypeDescriptionTransferCache;
    CarrierServiceDescriptionTransferCache carrierServiceDescriptionTransferCache;
    CarrierServiceTransferCache carrierServiceTransferCache;
    CarrierOptionDescriptionTransferCache carrierOptionDescriptionTransferCache;
    CarrierOptionTransferCache carrierOptionTransferCache;
    PartyCarrierTransferCache partyCarrierTransferCache;
    PartyCarrierAccountTransferCache partyCarrierAccountTransferCache;
    CarrierServiceOptionTransferCache carrierServiceOptionTransferCache;
    
    /** Creates a new instance of CarrierTransferCaches */
    protected CarrierTransferCaches() {
        super();
    }
    
    public CarrierTransferCache getCarrierTransferCache() {
        return carrierTransferCache;
    }
    
    public CarrierTypeTransferCache getCarrierTypeTransferCache() {
        return carrierTypeTransferCache;
    }
    
    public CarrierTypeDescriptionTransferCache getCarrierTypeDescriptionTransferCache() {
        return carrierTypeDescriptionTransferCache;
    }

    public CarrierServiceDescriptionTransferCache getCarrierServiceDescriptionTransferCache() {
        return carrierServiceDescriptionTransferCache;
    }

    public CarrierServiceTransferCache getCarrierServiceTransferCache() {
        return carrierServiceTransferCache;
    }
    
    public CarrierOptionDescriptionTransferCache getCarrierOptionDescriptionTransferCache() {
        return carrierOptionDescriptionTransferCache;
    }
    
    public CarrierOptionTransferCache getCarrierOptionTransferCache() {
        return carrierOptionTransferCache;
    }
    
    public PartyCarrierTransferCache getPartyCarrierTransferCache() {
        return partyCarrierTransferCache;
    }

    public PartyCarrierAccountTransferCache getPartyCarrierAccountTransferCache() {
        return partyCarrierAccountTransferCache;
    }

    public CarrierServiceOptionTransferCache getCarrierServiceOptionTransferCache() {
        return carrierServiceOptionTransferCache;
    }
    
}
