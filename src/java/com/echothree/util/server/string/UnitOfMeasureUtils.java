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

package com.echothree.util.server.string;

import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class UnitOfMeasureUtils {
    
    private UnitOfMeasureUtils() {
        super();
    }
    
    private static class UnitOfMeasureUtilsHolder {
        static UnitOfMeasureUtils instance = new UnitOfMeasureUtils();
    }
    
    public static UnitOfMeasureUtils getInstance() {
        return UnitOfMeasureUtilsHolder.instance;
    }
    
    private void appendMeasure(UomControl uomControl, Language language, final StringBuilder builder, final UnitOfMeasureType unitOfMeasureType, final long measure) {
        if(builder.length() > 0)
            builder.append(", ");
        
        builder.append(measure).append(' ');
        if(measure == 1) {
            builder.append(uomControl.getBestSingularUnitOfMeasureTypeDescription(unitOfMeasureType, language));
        } else {
            builder.append(uomControl.getBestPluralUnitOfMeasureTypeDescription(unitOfMeasureType, language));
        }
    }
    
    private StringBuilder formatUnitOfMeasure(UomControl uomControl, Language language, StringBuilder builder, UnitOfMeasureType unitOfMeasureType, long measure) {
        var unitOfMeasureEquivalents = uomControl.getUnitOfMeasureEquivalentsByToUnitOfMeasureType(unitOfMeasureType);
        var appended = false;
        
        for(var unitOfMeasureEquivalent : unitOfMeasureEquivalents) {
            long toQuantity = unitOfMeasureEquivalent.getToQuantity();
            
            if(measure > toQuantity) {
                var equivMeasure = measure / toQuantity;
                var remainMeasure = measure % toQuantity;
                
                formatUnitOfMeasure(uomControl, language, builder, unitOfMeasureEquivalent.getFromUnitOfMeasureType(), equivMeasure);
                
                if(remainMeasure != 0) {
                    appendMeasure(uomControl, language, builder, unitOfMeasureType, remainMeasure);
                }
                
                appended = true;
                break;
            }
        }
        
        if(!appended && measure != 0) {
            appendMeasure(uomControl, language, builder, unitOfMeasureType, measure);
        }
        
        return builder;
    }
    
    private UnitOfMeasureType getLowestUnitOfMeasureType(UomControl uomControl, UnitOfMeasureType unitOfMeasureType) {
        var unitOfMeasureEquivalents = uomControl.getUnitOfMeasureEquivalentsByFromUnitOfMeasureType(unitOfMeasureType);
        var iter = unitOfMeasureEquivalents.iterator();
        
        if(iter.hasNext()) {
            var unitOfMeasureEquivalent = iter.next();
            
            unitOfMeasureType = unitOfMeasureEquivalent.getToUnitOfMeasureType();
        }
        
        return unitOfMeasureType;
    }
    
    // TODO: This should have a cache
    public UnitOfMeasureType getLowestUnitOfMeasureType(UomControl uomControl, UnitOfMeasureKind unitOfMeasureKind) {
        var unitOfMeasureTypes = uomControl.getUnitOfMeasureTypesByUnitOfMeasureKind(unitOfMeasureKind);
        var iter = unitOfMeasureTypes.iterator();
        UnitOfMeasureType unitOfMeasureType = null;
        
        if(iter.hasNext()) {
            unitOfMeasureType = getLowestUnitOfMeasureType(uomControl, iter.next());
        }
        
        return unitOfMeasureType;
    }
    
    public String formatUnitOfMeasure(UserVisit userVisit, UnitOfMeasureKind unitOfMeasureKind, Long measure) {
        String result = null;
        
        if(measure != null) {
            var uomControl = Session.getModelController(UomControl.class);
            var userControl = Session.getModelController(UserControl.class);
            var language = userControl.getPreferredLanguageFromUserVisit(userVisit);
            var unitOfMeasureType = getLowestUnitOfMeasureType(uomControl, unitOfMeasureKind);
            
            result = formatUnitOfMeasure(uomControl, language, new StringBuilder(), unitOfMeasureType, measure).toString();
        }
        
        return result;
    }
    
}
