// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.model.control.uom.server.transfer;

import com.echothree.model.control.uom.common.UomProperties;
import com.echothree.model.control.uom.remote.transfer.UnitOfMeasureKindTransfer;
import com.echothree.model.control.uom.server.UomControl;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.remote.form.TransferProperties;
import java.util.Set;

public class UnitOfMeasureKindTransferCache
        extends BaseUomTransferCache<UnitOfMeasureKind, UnitOfMeasureKindTransfer> {
    
    TransferProperties transferProperties;
    boolean filterUnitOfMeasureKindName;
    boolean filterIsDefault;
    boolean filterFractionDigits;
    boolean filterSortOrder;
    boolean filterDescription;
    boolean filterEntityInstance;
    
    /** Creates a new instance of UnitOfMeasureKindTransferCache */
    public UnitOfMeasureKindTransferCache(UserVisit userVisit, UomControl uomControl) {
        super(userVisit, uomControl);

        transferProperties = session.getTransferProperties();
        if(transferProperties != null) {
            Set<String> properties = transferProperties.getProperties(UnitOfMeasureKindTransfer.class);
            
            if(properties != null) {
                filterUnitOfMeasureKindName = !properties.contains(UomProperties.UNIT_OF_MEASURE_KIND_NAME);
                filterIsDefault = !properties.contains(UomProperties.IS_DEFAULT);
                filterFractionDigits = !properties.contains(UomProperties.FRACTION_DIGITS);
                filterSortOrder = !properties.contains(UomProperties.SORT_ORDER);
                filterDescription = !properties.contains(UomProperties.DESCRIPTION);
                filterEntityInstance = !properties.contains(UomProperties.ENTITY_INSTANCE);
            }
        }
        
        setIncludeEntityInstance(!filterEntityInstance);
    }
    
    public UnitOfMeasureKindTransfer getUnitOfMeasureKindTransfer(UnitOfMeasureKind unitOfMeasureKind) {
        UnitOfMeasureKindTransfer unitOfMeasureKindTransfer = get(unitOfMeasureKind);
        
        if(unitOfMeasureKindTransfer == null) {
            UnitOfMeasureKindDetail unitOfMeasureKindDetail = unitOfMeasureKind.getLastDetail();
            String unitOfMeasureKindName = filterUnitOfMeasureKindName ? null : unitOfMeasureKindDetail.getUnitOfMeasureKindName();
            Integer fractionDigits = filterFractionDigits ? null : unitOfMeasureKindDetail.getFractionDigits();
            Boolean isDefault = filterIsDefault ? null : unitOfMeasureKindDetail.getIsDefault();
            Integer sortOrder = filterSortOrder ? null : unitOfMeasureKindDetail.getSortOrder();
            String description = filterDescription ? null : uomControl.getBestUnitOfMeasureKindDescription(unitOfMeasureKind, getLanguage());
            
            unitOfMeasureKindTransfer = new UnitOfMeasureKindTransfer(unitOfMeasureKindName, fractionDigits, isDefault, sortOrder, description);
            put(unitOfMeasureKind, unitOfMeasureKindTransfer);
        }
        return unitOfMeasureKindTransfer;
    }
    
}
