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
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.context.RequestScoped;

@RequestScoped
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
    protected CancellationPolicyTransferCaches() {
        super();
    }
    
    public CancellationPolicyTransferCache getCancellationPolicyTransferCache() {
        if(cancellationPolicyTransferCache == null)
            cancellationPolicyTransferCache = CDI.current().select(CancellationPolicyTransferCache.class).get();
        
        return cancellationPolicyTransferCache;
    }
    
    public CancellationPolicyTranslationTransferCache getCancellationPolicyTranslationTransferCache() {
        if(cancellationPolicyTranslationTransferCache == null)
            cancellationPolicyTranslationTransferCache = CDI.current().select(CancellationPolicyTranslationTransferCache.class).get();
        
        return cancellationPolicyTranslationTransferCache;
    }
    
    public PartyCancellationPolicyTransferCache getPartyCancellationPolicyTransferCache() {
        if(partyCancellationPolicyTransferCache == null)
            partyCancellationPolicyTransferCache = CDI.current().select(PartyCancellationPolicyTransferCache.class).get();
        
        return partyCancellationPolicyTransferCache;
    }
    
    public CancellationKindDescriptionTransferCache getCancellationKindDescriptionTransferCache() {
        if(cancellationKindDescriptionTransferCache == null)
            cancellationKindDescriptionTransferCache = CDI.current().select(CancellationKindDescriptionTransferCache.class).get();
        
        return cancellationKindDescriptionTransferCache;
    }
    
    public CancellationReasonDescriptionTransferCache getCancellationReasonDescriptionTransferCache() {
        if(cancellationReasonDescriptionTransferCache == null)
            cancellationReasonDescriptionTransferCache = CDI.current().select(CancellationReasonDescriptionTransferCache.class).get();
        
        return cancellationReasonDescriptionTransferCache;
    }
    
    public CancellationTypeDescriptionTransferCache getCancellationTypeDescriptionTransferCache() {
        if(cancellationTypeDescriptionTransferCache == null)
            cancellationTypeDescriptionTransferCache = CDI.current().select(CancellationTypeDescriptionTransferCache.class).get();
        
        return cancellationTypeDescriptionTransferCache;
    }
    
    public CancellationKindTransferCache getCancellationKindTransferCache() {
        if(cancellationKindTransferCache == null)
            cancellationKindTransferCache = CDI.current().select(CancellationKindTransferCache.class).get();
        
        return cancellationKindTransferCache;
    }
    
    public CancellationReasonTransferCache getCancellationReasonTransferCache() {
        if(cancellationReasonTransferCache == null)
            cancellationReasonTransferCache = CDI.current().select(CancellationReasonTransferCache.class).get();
        
        return cancellationReasonTransferCache;
    }
    
    public CancellationTypeTransferCache getCancellationTypeTransferCache() {
        if(cancellationTypeTransferCache == null)
            cancellationTypeTransferCache = CDI.current().select(CancellationTypeTransferCache.class).get();
        
        return cancellationTypeTransferCache;
    }
    
    public CancellationPolicyReasonTransferCache getCancellationPolicyReasonTransferCache() {
        if(cancellationPolicyReasonTransferCache == null)
            cancellationPolicyReasonTransferCache = CDI.current().select(CancellationPolicyReasonTransferCache.class).get();
        
        return cancellationPolicyReasonTransferCache;
    }
    
    public CancellationReasonTypeTransferCache getCancellationReasonTypeTransferCache() {
        if(cancellationReasonTypeTransferCache == null)
            cancellationReasonTypeTransferCache = CDI.current().select(CancellationReasonTypeTransferCache.class).get();
        
        return cancellationReasonTypeTransferCache;
    }
    
}
