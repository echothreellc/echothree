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

package com.echothree.model.control.term.server.transfer;

import com.echothree.util.server.transfer.BaseTransferCaches;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class TermTransferCaches
        extends BaseTransferCaches {
    
    protected TermTypeTransferCache termTypeTransferCache;
    protected TermTransferCache termTransferCache;
    protected TermDescriptionTransferCache termDescriptionTransferCache;
    protected PartyTermTransferCache partyTermTransferCache;
    protected CustomerTypeCreditLimitTransferCache customerTypeCreditLimitTransferCache;
    protected PartyCreditLimitTransferCache partyCreditLimitTransferCache;
    
    /** Creates a new instance of TermTransferCaches */
    protected TermTransferCaches() {
        super();
    }
    
    public TermTypeTransferCache getTermTypeTransferCache() {
        if(termTypeTransferCache == null)
            termTypeTransferCache = CDI.current().select(TermTypeTransferCache.class).get();
        
        return termTypeTransferCache;
    }
    
    public TermTransferCache getTermTransferCache() {
        if(termTransferCache == null)
            termTransferCache = CDI.current().select(TermTransferCache.class).get();
        
        return termTransferCache;
    }
    
    public TermDescriptionTransferCache getTermDescriptionTransferCache() {
        if(termDescriptionTransferCache == null)
            termDescriptionTransferCache = CDI.current().select(TermDescriptionTransferCache.class).get();
        
        return termDescriptionTransferCache;
    }
    
    public PartyTermTransferCache getPartyTermTransferCache() {
        if(partyTermTransferCache == null)
            partyTermTransferCache = CDI.current().select(PartyTermTransferCache.class).get();
        
        return partyTermTransferCache;
    }
    
    public CustomerTypeCreditLimitTransferCache getCustomerTypeCreditLimitTransferCache() {
        if(customerTypeCreditLimitTransferCache == null)
            customerTypeCreditLimitTransferCache = CDI.current().select(CustomerTypeCreditLimitTransferCache.class).get();
        
        return customerTypeCreditLimitTransferCache;
    }
    
    public PartyCreditLimitTransferCache getPartyCreditLimitTransferCache() {
        if(partyCreditLimitTransferCache == null)
            partyCreditLimitTransferCache = CDI.current().select(PartyCreditLimitTransferCache.class).get();
        
        return partyCreditLimitTransferCache;
    }
    
}
