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

package com.echothree.model.control.cancellationpolicy.server.transfer;

import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.transfer.BaseTransferCaches;

public class CancellationPolicyTransferCaches
        extends BaseTransferCaches {
    
    protected CancellationPolicyControl cancellationPolicyControl;
    
    protected CancellationPolicyTransferCache cancellationPolicyTransferCache;
    protected CancellationPolicyTranslationTransferCache cancellationPolicyTranslationTransferCache;
    protected PartyCancellationPolicyTransferCache partyCancellationPolicyTransferCache;
    protected CancellationKindDescriptionTransferCache cancellationKindDescriptionTransferCache;
    protected CancellationReasonDescriptionTransferCache cancellationReasonDescriptionTransferCache;
    protected CancellationTypeDescriptionTransferCache cancellationTypeDescriptionTransferCache;
    protected CancellationKindTransferCache cancellationKindTransferCache;
    protected CancellationReasonTransferCache cancellationReasonTransferCache;
    protected CancellationTypeTransferCache cancellationTypeTransferCache;
    protected CancellationPolicyReasonTransferCache cancellationPolicyReasonTransferCache;
    protected CancellationReasonTypeTransferCache cancellationReasonTypeTransferCache;
    
    /** Creates a new instance of CancellationPolicyTransferCaches */
    public CancellationPolicyTransferCaches(CancellationPolicyControl cancellationPolicyControl) {
        super();
        
        this.cancellationPolicyControl = cancellationPolicyControl;
    }
    
    public CancellationPolicyTransferCache getCancellationPolicyTransferCache() {
        if(cancellationPolicyTransferCache == null)
            cancellationPolicyTransferCache = new CancellationPolicyTransferCache(cancellationPolicyControl);
        
        return cancellationPolicyTransferCache;
    }
    
    public CancellationPolicyTranslationTransferCache getCancellationPolicyTranslationTransferCache() {
        if(cancellationPolicyTranslationTransferCache == null)
            cancellationPolicyTranslationTransferCache = new CancellationPolicyTranslationTransferCache(cancellationPolicyControl);
        
        return cancellationPolicyTranslationTransferCache;
    }
    
    public PartyCancellationPolicyTransferCache getPartyCancellationPolicyTransferCache() {
        if(partyCancellationPolicyTransferCache == null)
            partyCancellationPolicyTransferCache = new PartyCancellationPolicyTransferCache(cancellationPolicyControl);
        
        return partyCancellationPolicyTransferCache;
    }
    
    public CancellationKindDescriptionTransferCache getCancellationKindDescriptionTransferCache() {
        if(cancellationKindDescriptionTransferCache == null)
            cancellationKindDescriptionTransferCache = new CancellationKindDescriptionTransferCache(cancellationPolicyControl);
        
        return cancellationKindDescriptionTransferCache;
    }
    
    public CancellationReasonDescriptionTransferCache getCancellationReasonDescriptionTransferCache() {
        if(cancellationReasonDescriptionTransferCache == null)
            cancellationReasonDescriptionTransferCache = new CancellationReasonDescriptionTransferCache(cancellationPolicyControl);
        
        return cancellationReasonDescriptionTransferCache;
    }
    
    public CancellationTypeDescriptionTransferCache getCancellationTypeDescriptionTransferCache() {
        if(cancellationTypeDescriptionTransferCache == null)
            cancellationTypeDescriptionTransferCache = new CancellationTypeDescriptionTransferCache(cancellationPolicyControl);
        
        return cancellationTypeDescriptionTransferCache;
    }
    
    public CancellationKindTransferCache getCancellationKindTransferCache() {
        if(cancellationKindTransferCache == null)
            cancellationKindTransferCache = new CancellationKindTransferCache(cancellationPolicyControl);
        
        return cancellationKindTransferCache;
    }
    
    public CancellationReasonTransferCache getCancellationReasonTransferCache() {
        if(cancellationReasonTransferCache == null)
            cancellationReasonTransferCache = new CancellationReasonTransferCache(cancellationPolicyControl);
        
        return cancellationReasonTransferCache;
    }
    
    public CancellationTypeTransferCache getCancellationTypeTransferCache() {
        if(cancellationTypeTransferCache == null)
            cancellationTypeTransferCache = new CancellationTypeTransferCache(cancellationPolicyControl);
        
        return cancellationTypeTransferCache;
    }
    
    public CancellationPolicyReasonTransferCache getCancellationPolicyReasonTransferCache() {
        if(cancellationPolicyReasonTransferCache == null)
            cancellationPolicyReasonTransferCache = new CancellationPolicyReasonTransferCache(cancellationPolicyControl);
        
        return cancellationPolicyReasonTransferCache;
    }
    
    public CancellationReasonTypeTransferCache getCancellationReasonTypeTransferCache() {
        if(cancellationReasonTypeTransferCache == null)
            cancellationReasonTypeTransferCache = new CancellationReasonTypeTransferCache(cancellationPolicyControl);
        
        return cancellationReasonTypeTransferCache;
    }
    
}
