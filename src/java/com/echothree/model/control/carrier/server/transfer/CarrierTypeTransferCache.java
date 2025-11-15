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

import com.echothree.model.control.carrier.common.transfer.CarrierTypeTransfer;
import com.echothree.model.control.carrier.server.control.CarrierControl;
import com.echothree.model.data.carrier.server.entity.CarrierType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class CarrierTypeTransferCache
        extends BaseCarrierTransferCache<CarrierType, CarrierTypeTransfer> {

    CarrierControl carrierControl = Session.getModelController(CarrierControl.class);

    /** Creates a new instance of CarrierTypeTransferCache */
    public CarrierTypeTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }

    public CarrierTypeTransfer getCarrierTypeTransfer(UserVisit userVisit, CarrierType carrierType) {
        var carrierTypeTransfer = get(carrierType);

        if(carrierTypeTransfer == null) {
            var carrierTypeDetail = carrierType.getLastDetail();
            var carrierTypeName = carrierTypeDetail.getCarrierTypeName();
            var isDefault = carrierTypeDetail.getIsDefault();
            var sortOrder = carrierTypeDetail.getSortOrder();
            var description = carrierControl.getBestCarrierTypeDescription(carrierType, getLanguage(userVisit));

            carrierTypeTransfer = new CarrierTypeTransfer(carrierTypeName, isDefault, sortOrder, description);
            put(userVisit, carrierType, carrierTypeTransfer);
        }

        return carrierTypeTransfer;
    }

}
