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
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class CancellationPolicyTransferCaches
        extends BaseTransferCaches {
    
    @Inject
    CancellationPolicyTransferCache cancellationPolicyTransferCache;
    
    @Inject
    CancellationPolicyTranslationTransferCache cancellationPolicyTranslationTransferCache;
    
    @Inject
    PartyCancellationPolicyTransferCache partyCancellationPolicyTransferCache;
    
    @Inject
    CancellationKindDescriptionTransferCache cancellationKindDescriptionTransferCache;
    
    @Inject
    CancellationReasonDescriptionTransferCache cancellationReasonDescriptionTransferCache;
    
    @Inject
    CancellationTypeDescriptionTransferCache cancellationTypeDescriptionTransferCache;
    
    @Inject
    CancellationKindTransferCache cancellationKindTransferCache;
    
    @Inject
    CancellationReasonTransferCache cancellationReasonTransferCache;
    
    @Inject
    CancellationTypeTransferCache cancellationTypeTransferCache;
    
    @Inject
    CancellationPolicyReasonTransferCache cancellationPolicyReasonTransferCache;
    
    @Inject
    CancellationReasonTypeTransferCache cancellationReasonTypeTransferCache;

    /** Creates a new instance of CancellationPolicyTransferCaches */
    protected CancellationPolicyTransferCaches() {
        super();
    }
    
    public CancellationPolicyTransferCache getCancellationPolicyTransferCache() {
        return cancellationPolicyTransferCache;
    }
    
    public CancellationPolicyTranslationTransferCache getCancellationPolicyTranslationTransferCache() {
        return cancellationPolicyTranslationTransferCache;
    }
    
    public PartyCancellationPolicyTransferCache getPartyCancellationPolicyTransferCache() {
        return partyCancellationPolicyTransferCache;
    }
    
    public CancellationKindDescriptionTransferCache getCancellationKindDescriptionTransferCache() {
        return cancellationKindDescriptionTransferCache;
    }
    
    public CancellationReasonDescriptionTransferCache getCancellationReasonDescriptionTransferCache() {
        return cancellationReasonDescriptionTransferCache;
    }
    
    public CancellationTypeDescriptionTransferCache getCancellationTypeDescriptionTransferCache() {
        return cancellationTypeDescriptionTransferCache;
    }
    
    public CancellationKindTransferCache getCancellationKindTransferCache() {
        return cancellationKindTransferCache;
    }
    
    public CancellationReasonTransferCache getCancellationReasonTransferCache() {
        return cancellationReasonTransferCache;
    }
    
    public CancellationTypeTransferCache getCancellationTypeTransferCache() {
        return cancellationTypeTransferCache;
    }
    
    public CancellationPolicyReasonTransferCache getCancellationPolicyReasonTransferCache() {
        return cancellationPolicyReasonTransferCache;
    }
    
    public CancellationReasonTypeTransferCache getCancellationReasonTypeTransferCache() {
        return cancellationReasonTypeTransferCache;
    }
    
}
