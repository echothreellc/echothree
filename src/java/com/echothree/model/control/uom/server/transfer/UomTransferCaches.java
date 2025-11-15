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

package com.echothree.model.control.uom.server.transfer;

import com.echothree.util.server.transfer.BaseTransferCaches;

public class UomTransferCaches
        extends BaseTransferCaches {
    
    protected UnitOfMeasureKindTransferCache uomKindTransferCache;
    protected UnitOfMeasureTypeTransferCache uomTypeTransferCache;
    protected UnitOfMeasureKindDescriptionTransferCache uomKindDescriptionTransferCache;
    protected UnitOfMeasureTypeDescriptionTransferCache uomTypeDescriptionTransferCache;
    protected UnitOfMeasureKindUseTransferCache uomKindUseTransferCache;
    protected UnitOfMeasureKindUseTypeTransferCache uomKindUseTypeTransferCache;
    protected UnitOfMeasureEquivalentTransferCache uomEquivalentTransferCache;
    protected UnitOfMeasureTypeVolumeTransferCache uomTypeVolumeTransferCache;
    protected UnitOfMeasureTypeWeightTransferCache uomTypeWeightTransferCache;
    
    /** Creates a new instance of UomTransferCaches */
    public UomTransferCaches() {
        super();
    }
    
    public UnitOfMeasureKindTransferCache getUnitOfMeasureKindTransferCache() {
        if(uomKindTransferCache == null)
            uomKindTransferCache = new UnitOfMeasureKindTransferCache();
        
        return uomKindTransferCache;
    }
    
    public UnitOfMeasureTypeTransferCache getUnitOfMeasureTypeTransferCache() {
        if(uomTypeTransferCache == null)
            uomTypeTransferCache = new UnitOfMeasureTypeTransferCache();
        
        return uomTypeTransferCache;
    }
    
    public UnitOfMeasureKindDescriptionTransferCache getUnitOfMeasureKindDescriptionTransferCache() {
        if(uomKindDescriptionTransferCache == null)
            uomKindDescriptionTransferCache = new UnitOfMeasureKindDescriptionTransferCache();
        
        return uomKindDescriptionTransferCache;
    }
    
    public UnitOfMeasureTypeDescriptionTransferCache getUnitOfMeasureTypeDescriptionTransferCache() {
        if(uomTypeDescriptionTransferCache == null)
            uomTypeDescriptionTransferCache = new UnitOfMeasureTypeDescriptionTransferCache();
        
        return uomTypeDescriptionTransferCache;
    }
    
    public UnitOfMeasureKindUseTransferCache getUnitOfMeasureKindUseTransferCache() {
        if(uomKindUseTransferCache == null)
            uomKindUseTransferCache = new UnitOfMeasureKindUseTransferCache();
        
        return uomKindUseTransferCache;
    }
    
    public UnitOfMeasureKindUseTypeTransferCache getUnitOfMeasureKindUseTypeTransferCache() {
        if(uomKindUseTypeTransferCache == null)
            uomKindUseTypeTransferCache = new UnitOfMeasureKindUseTypeTransferCache();
        
        return uomKindUseTypeTransferCache;
    }
    
    public UnitOfMeasureEquivalentTransferCache getUnitOfMeasureEquivalentTransferCache() {
        if(uomEquivalentTransferCache == null)
            uomEquivalentTransferCache = new UnitOfMeasureEquivalentTransferCache();
        
        return uomEquivalentTransferCache;
    }
    
    public UnitOfMeasureTypeVolumeTransferCache getUnitOfMeasureTypeVolumeTransferCache() {
        if(uomTypeVolumeTransferCache == null)
            uomTypeVolumeTransferCache = new UnitOfMeasureTypeVolumeTransferCache();
        
        return uomTypeVolumeTransferCache;
    }
    
    public UnitOfMeasureTypeWeightTransferCache getUnitOfMeasureTypeWeightTransferCache() {
        if(uomTypeWeightTransferCache == null)
            uomTypeWeightTransferCache = new UnitOfMeasureTypeWeightTransferCache();
        
        return uomTypeWeightTransferCache;
    }
    
}
