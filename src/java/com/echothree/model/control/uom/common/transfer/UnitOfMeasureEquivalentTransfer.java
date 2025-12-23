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

package com.echothree.model.control.uom.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class UnitOfMeasureEquivalentTransfer
        extends BaseTransfer {
    
    private UnitOfMeasureTypeTransfer fromUnitOfMeasureType;
    private UnitOfMeasureTypeTransfer toUnitOfMeasureType;
    private Long toQuantity;
    
    /** Creates a new instance of UnitOfMeasureEquivalentTransfer */
    public UnitOfMeasureEquivalentTransfer(UnitOfMeasureTypeTransfer fromUnitOfMeasureType, UnitOfMeasureTypeTransfer toUnitOfMeasureType, Long toQuantity) {
        this.fromUnitOfMeasureType = fromUnitOfMeasureType;
        this.toUnitOfMeasureType = toUnitOfMeasureType;
        this.toQuantity = toQuantity;
    }
    
    public UnitOfMeasureTypeTransfer getFromUnitOfMeasureType() {
        return fromUnitOfMeasureType;
    }
    
    public void setFromUnitOfMeasureType(UnitOfMeasureTypeTransfer fromUnitOfMeasureType) {
        this.fromUnitOfMeasureType = fromUnitOfMeasureType;
    }
    
    public UnitOfMeasureTypeTransfer getToUnitOfMeasureType() {
        return toUnitOfMeasureType;
    }
    
    public void setToUnitOfMeasureType(UnitOfMeasureTypeTransfer toUnitOfMeasureType) {
        this.toUnitOfMeasureType = toUnitOfMeasureType;
    }
    
    public Long getToQuantity() {
        return toQuantity;
    }
    
    public void setToQuantity(Long toQuantity) {
        this.toQuantity = toQuantity;
    }
    
}
