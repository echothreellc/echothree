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

package com.echothree.model.control.uom.server.transfer;

import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.uom.common.UomOptions;
import com.echothree.model.control.uom.common.UomProperties;
import com.echothree.model.control.uom.common.transfer.UnitOfMeasureTypeTransfer;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class UnitOfMeasureTypeTransferCache
        extends BaseUomTransferCache<UnitOfMeasureType, UnitOfMeasureTypeTransfer> {
    
    AccountingControl accountingControl = Session.getModelController(AccountingControl.class);
    UomControl uomControl = Session.getModelController(UomControl.class);

    boolean includeVolume;
    boolean includeWeight;
    
    TransferProperties transferProperties;
    boolean filterUnitOfMeasureKind;
    boolean filterUnitOfMeasureTypeName;
    boolean filterSymbolPosition;
    boolean filterSuppressSymbolSeparator;
    boolean filterIsDefault;
    boolean filterSortOrder;
    boolean filterDescription;
    boolean filterEntityInstance;
    
    /** Creates a new instance of UnitOfMeasureTypeTransferCache */
    protected UnitOfMeasureTypeTransferCache() {
        super();
        
        var options = session.getOptions();
        if(options != null) {
            includeVolume = options.contains(UomOptions.UnitOfMeasureTypeIncludeVolume);
            includeWeight = options.contains(UomOptions.UnitOfMeasureTypeIncludeWeight);
        }

        transferProperties = session.getTransferProperties();
        if(transferProperties != null) {
            var properties = transferProperties.getProperties(UnitOfMeasureTypeTransfer.class);
            
            if(properties != null) {
                filterUnitOfMeasureKind = !properties.contains(UomProperties.UNIT_OF_MEASURE_KIND);
                filterUnitOfMeasureTypeName = !properties.contains(UomProperties.UNIT_OF_MEASURE_TYPE_NAME);
                filterSymbolPosition = !properties.contains(UomProperties.SYMBOL_POSITION);
                filterSuppressSymbolSeparator = !properties.contains(UomProperties.SUPPRESS_SYMBOL_SEPARATOR);
                filterIsDefault = !properties.contains(UomProperties.IS_DEFAULT);
                filterSortOrder = !properties.contains(UomProperties.SORT_ORDER);
                filterDescription = !properties.contains(UomProperties.DESCRIPTION);
                filterEntityInstance = !properties.contains(UomProperties.ENTITY_INSTANCE);
            }
        }
        
        setIncludeEntityInstance(!filterEntityInstance);
    }
    
    public UnitOfMeasureTypeTransfer getUnitOfMeasureTypeTransfer(UserVisit userVisit, UnitOfMeasureType unitOfMeasureType) {
        var unitOfMeasureTypeTransfer = get(unitOfMeasureType);
        
        if(unitOfMeasureTypeTransfer == null) {
            var unitOfMeasureTypeDetail = unitOfMeasureType.getLastDetail();
            var unitOfMeasureKind = filterUnitOfMeasureKind ? null : uomControl.getUnitOfMeasureKindTransfer(userVisit, unitOfMeasureTypeDetail.getUnitOfMeasureKind());
            var unitOfMeasureTypeName = filterUnitOfMeasureTypeName ? null : unitOfMeasureTypeDetail.getUnitOfMeasureTypeName();
            var symbolPosition = filterSymbolPosition ? null : accountingControl.getSymbolPositionTransfer(userVisit, unitOfMeasureTypeDetail.getSymbolPosition());
            var suppressSymbolSeparator = filterSuppressSymbolSeparator ? null : unitOfMeasureTypeDetail.getSuppressSymbolSeparator();
            var isDefault = filterIsDefault ? null : unitOfMeasureTypeDetail.getIsDefault();
            var sortOrder = filterSortOrder ? null : unitOfMeasureTypeDetail.getSortOrder();
            var description = filterDescription ? null : uomControl.getBestSingularUnitOfMeasureTypeDescription(unitOfMeasureType, getLanguage(userVisit));
            
            unitOfMeasureTypeTransfer = new UnitOfMeasureTypeTransfer(unitOfMeasureKind, unitOfMeasureTypeName, symbolPosition,
                    suppressSymbolSeparator, isDefault, sortOrder, description);
            put(userVisit, unitOfMeasureType, unitOfMeasureTypeTransfer);
            
            if(includeVolume) {
                var unitOfMeasureTypeVolume = uomControl.getUnitOfMeasureTypeVolume(unitOfMeasureType);
                
                if(unitOfMeasureTypeVolume != null) {
                    unitOfMeasureTypeTransfer.setUnitOfMeasureTypeVolume(uomControl.getUnitOfMeasureTypeVolumeTransfer(userVisit, unitOfMeasureTypeVolume));
                }
            }
            
            if(includeWeight) {
                var unitOfMeasureTypeWeight = uomControl.getUnitOfMeasureTypeWeight(unitOfMeasureType);
                
                if(unitOfMeasureTypeWeight != null) {
                    unitOfMeasureTypeTransfer.setUnitOfMeasureTypeWeight(uomControl.getUnitOfMeasureTypeWeightTransfer(userVisit, unitOfMeasureTypeWeight));
                }
            }
        }
        return unitOfMeasureTypeTransfer;
    }
    
}
