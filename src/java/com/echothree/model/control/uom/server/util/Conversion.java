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

package com.echothree.model.control.uom.server.util;

import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.util.server.string.UnitOfMeasureUtils;

public class Conversion {
    
    private UomControl uomControl;
    private UnitOfMeasureKind unitOfMeasureKind;
    private UnitOfMeasureType unitOfMeasureType;
    private long quantity;
    
    /** Creates a new instance of Conversion */
    public Conversion(UomControl uomControl, UnitOfMeasureKind unitOfMeasureKind, Long quantity) {
        this.uomControl = uomControl;
        this.unitOfMeasureKind = unitOfMeasureKind;
        this.quantity = quantity;
    }
    
    /** Creates a new instance of Conversion */
    public Conversion(UomControl uomControl, UnitOfMeasureType unitOfMeasureType, Long quantity) {
        this.uomControl = uomControl;
        this.unitOfMeasureType = unitOfMeasureType;
        this.quantity = quantity;
    }
    
    
    private void convertToHighestUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType, long measure) {
        var unitOfMeasureEquivalents = uomControl.getUnitOfMeasureEquivalentsByToUnitOfMeasureType(unitOfMeasureType);
        var attemptUnitOfMeasureType = unitOfMeasureType;
        var attemptMeasure = measure;
        
        if(!unitOfMeasureEquivalents.isEmpty()) {
            for(var unitOfMeasureEquivalent : unitOfMeasureEquivalents) {
                long toQuantity = unitOfMeasureEquivalent.getToQuantity();
                
                // If there's no remainder, then check to see if its lower than the existing attemptMeasure
                if(measure % toQuantity == 0) {
                    var trialMeasure = measure / toQuantity;
                    
                    if(trialMeasure < attemptMeasure) {
                        attemptUnitOfMeasureType = unitOfMeasureEquivalent.getFromUnitOfMeasureType();
                        attemptMeasure = trialMeasure;
                    }
                }
            }
            
            if(attemptMeasure != measure) {
                convertToHighestUnitOfMeasureType(attemptUnitOfMeasureType, attemptMeasure);
            }
        }
        
        if(this.unitOfMeasureType == null) {
            this.unitOfMeasureType = attemptUnitOfMeasureType;
            quantity = attemptMeasure;
        }
    }
    
    public Conversion convertToHighestUnitOfMeasureType() {
        convertToHighestUnitOfMeasureType(UnitOfMeasureUtils.getInstance().getLowestUnitOfMeasureType(uomControl, unitOfMeasureKind), quantity);
        
        return this;
    }
    
    public Conversion convertToLowestUnitOfMeasureType() {
        var unitOfMeasureEquivalents = uomControl.getUnitOfMeasureEquivalentsByFromUnitOfMeasureType(unitOfMeasureType);
        
        while(!unitOfMeasureEquivalents.isEmpty()) {
            var iter = unitOfMeasureEquivalents.iterator();
            var unitOfMeasureEquivalent = iter.next();
            
            unitOfMeasureType = unitOfMeasureEquivalent.getToUnitOfMeasureType();
            quantity = quantity * unitOfMeasureEquivalent.getToQuantity();
            
            unitOfMeasureEquivalents = uomControl.getUnitOfMeasureEquivalentsByFromUnitOfMeasureType(unitOfMeasureType);
        }
        
        return this;
    }
    
    public UnitOfMeasureKind getUnitOfMeasureKind() {
        return unitOfMeasureKind;
    }
    
    public void setUnitOfMeasureKind(UnitOfMeasureKind unitOfMeasureKind) {
        this.unitOfMeasureKind = unitOfMeasureKind;
    }
    
    public UnitOfMeasureType getUnitOfMeasureType() {
        return unitOfMeasureType;
    }
    
    public void setUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType) {
        this.unitOfMeasureType = unitOfMeasureType;
    }
    
    public Long getQuantity() {
        return quantity;
    }
    
    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
    
}
