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

package com.echothree.model.control.communication.server.transfer;

import com.echothree.util.server.transfer.BaseTransferCaches;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class CommunicationTransferCaches
        extends BaseTransferCaches {
    
    @Inject
    CommunicationEventRoleTypeTransferCache communicationEventRoleTypeTransferCache;
    
    @Inject
    CommunicationEventTypeTransferCache communicationEventTypeTransferCache;
    
    @Inject
    CommunicationEventPurposeTransferCache communicationEventPurposeTransferCache;
    
    @Inject
    CommunicationEventPurposeDescriptionTransferCache communicationEventPurposeDescriptionTransferCache;
    
    @Inject
    CommunicationEventTransferCache communicationEventTransferCache;
    
    @Inject
    CommunicationEventRoleTransferCache communicationEventRoleTransferCache;
    
    @Inject
    CommunicationSourceTypeTransferCache communicationSourceTypeTransferCache;
    
    @Inject
    CommunicationSourceTransferCache communicationSourceTransferCache;
    
    @Inject
    CommunicationSourceDescriptionTransferCache communicationSourceDescriptionTransferCache;
    
    @Inject
    CommunicationEmailSourceTransferCache communicationEmailSourceTransferCache;

    /** Creates a new instance of CommunicationTransferCaches */
    protected CommunicationTransferCaches() {
        super();
    }
    
    public CommunicationEventRoleTypeTransferCache getCommunicationEventRoleTypeTransferCache() {
        return communicationEventRoleTypeTransferCache;
    }
    
    public CommunicationEventTypeTransferCache getCommunicationEventTypeTransferCache() {
        return communicationEventTypeTransferCache;
    }
    
    public CommunicationEventPurposeTransferCache getCommunicationEventPurposeTransferCache() {
        return communicationEventPurposeTransferCache;
    }
    
    public CommunicationEventPurposeDescriptionTransferCache getCommunicationEventPurposeDescriptionTransferCache() {
        return communicationEventPurposeDescriptionTransferCache;
    }
    
    public CommunicationEventTransferCache getCommunicationEventTransferCache() {
        return communicationEventTransferCache;
    }
    
    public CommunicationEventRoleTransferCache getCommunicationEventRoleTransferCache() {
        return communicationEventRoleTransferCache;
    }
    
    public CommunicationSourceTypeTransferCache getCommunicationSourceTypeTransferCache() {
        return communicationSourceTypeTransferCache;
    }
    
    public CommunicationSourceTransferCache getCommunicationSourceTransferCache() {
        return communicationSourceTransferCache;
    }
    
    public CommunicationSourceDescriptionTransferCache getCommunicationSourceDescriptionTransferCache() {
        return communicationSourceDescriptionTransferCache;
    }
    
    public CommunicationEmailSourceTransferCache getCommunicationEmailSourceTransferCache() {
        return communicationEmailSourceTransferCache;
    }
    
}
