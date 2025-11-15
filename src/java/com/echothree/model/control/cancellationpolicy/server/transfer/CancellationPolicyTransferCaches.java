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

import com.echothree.util.server.transfer.BaseTransferCaches;

public class CancellationPolicyTransferCaches
        extends BaseTransferCaches {
    
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
    public CancellationPolicyTransferCaches() {
        super();
    }
    
    public CancellationPolicyTransferCache getCancellationPolicyTransferCache() {
        if(cancellationPolicyTransferCache == null)
            cancellationPolicyTransferCache = new CancellationPolicyTransferCache();
        
        return cancellationPolicyTransferCache;
    }
    
    public CancellationPolicyTranslationTransferCache getCancellationPolicyTranslationTransferCache() {
        if(cancellationPolicyTranslationTransferCache == null)
            cancellationPolicyTranslationTransferCache = new CancellationPolicyTranslationTransferCache();
        
        return cancellationPolicyTranslationTransferCache;
    }
    
    public PartyCancellationPolicyTransferCache getPartyCancellationPolicyTransferCache() {
        if(partyCancellationPolicyTransferCache == null)
            partyCancellationPolicyTransferCache = new PartyCancellationPolicyTransferCache();
        
        return partyCancellationPolicyTransferCache;
    }
    
    public CancellationKindDescriptionTransferCache getCancellationKindDescriptionTransferCache() {
        if(cancellationKindDescriptionTransferCache == null)
            cancellationKindDescriptionTransferCache = new CancellationKindDescriptionTransferCache();
        
        return cancellationKindDescriptionTransferCache;
    }
    
    public CancellationReasonDescriptionTransferCache getCancellationReasonDescriptionTransferCache() {
        if(cancellationReasonDescriptionTransferCache == null)
            cancellationReasonDescriptionTransferCache = new CancellationReasonDescriptionTransferCache();
        
        return cancellationReasonDescriptionTransferCache;
    }
    
    public CancellationTypeDescriptionTransferCache getCancellationTypeDescriptionTransferCache() {
        if(cancellationTypeDescriptionTransferCache == null)
            cancellationTypeDescriptionTransferCache = new CancellationTypeDescriptionTransferCache();
        
        return cancellationTypeDescriptionTransferCache;
    }
    
    public CancellationKindTransferCache getCancellationKindTransferCache() {
        if(cancellationKindTransferCache == null)
            cancellationKindTransferCache = new CancellationKindTransferCache();
        
        return cancellationKindTransferCache;
    }
    
    public CancellationReasonTransferCache getCancellationReasonTransferCache() {
        if(cancellationReasonTransferCache == null)
            cancellationReasonTransferCache = new CancellationReasonTransferCache();
        
        return cancellationReasonTransferCache;
    }
    
    public CancellationTypeTransferCache getCancellationTypeTransferCache() {
        if(cancellationTypeTransferCache == null)
            cancellationTypeTransferCache = new CancellationTypeTransferCache();
        
        return cancellationTypeTransferCache;
    }
    
    public CancellationPolicyReasonTransferCache getCancellationPolicyReasonTransferCache() {
        if(cancellationPolicyReasonTransferCache == null)
            cancellationPolicyReasonTransferCache = new CancellationPolicyReasonTransferCache();
        
        return cancellationPolicyReasonTransferCache;
    }
    
    public CancellationReasonTypeTransferCache getCancellationReasonTypeTransferCache() {
        if(cancellationReasonTypeTransferCache == null)
            cancellationReasonTypeTransferCache = new CancellationReasonTypeTransferCache();
        
        return cancellationReasonTypeTransferCache;
    }
    
}
