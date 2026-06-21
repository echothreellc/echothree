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

package com.echothree.control.user.communication.common;

import com.echothree.control.user.communication.common.form.*;
import com.echothree.control.user.communication.common.result.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface CommunicationService
        extends CommunicationForms {
    
    // -------------------------------------------------------------------------
    //   Communication Event Role Types
    // -------------------------------------------------------------------------
    
    CommandResult<?> createCommunicationEventRoleType(UserVisitPK userVisitPK, CreateCommunicationEventRoleTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Communication Event Role Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createCommunicationEventRoleTypeDescription(UserVisitPK userVisitPK, CreateCommunicationEventRoleTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Communication Event Types
    // -------------------------------------------------------------------------
    
    CommandResult<?> createCommunicationEventType(UserVisitPK userVisitPK, CreateCommunicationEventTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Communication Event Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createCommunicationEventTypeDescription(UserVisitPK userVisitPK, CreateCommunicationEventTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Communication Event Purposes
    // -------------------------------------------------------------------------
    
    CommandResult<?> createCommunicationEventPurpose(UserVisitPK userVisitPK, CreateCommunicationEventPurposeForm form);
    
    CommandResult<GetCommunicationEventPurposesResult> getCommunicationEventPurposes(UserVisitPK userVisitPK, GetCommunicationEventPurposesForm form);
    
    CommandResult<GetCommunicationEventPurposeResult> getCommunicationEventPurpose(UserVisitPK userVisitPK, GetCommunicationEventPurposeForm form);
    
    CommandResult<GetCommunicationEventPurposeChoicesResult> getCommunicationEventPurposeChoices(UserVisitPK userVisitPK, GetCommunicationEventPurposeChoicesForm form);
    
    CommandResult<?> setDefaultCommunicationEventPurpose(UserVisitPK userVisitPK, SetDefaultCommunicationEventPurposeForm form);
    
    CommandResult<EditCommunicationEventPurposeResult> editCommunicationEventPurpose(UserVisitPK userVisitPK, EditCommunicationEventPurposeForm form);
    
    CommandResult<?> deleteCommunicationEventPurpose(UserVisitPK userVisitPK, DeleteCommunicationEventPurposeForm form);
    
    // -------------------------------------------------------------------------
    //   Communication Event Purpose Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createCommunicationEventPurposeDescription(UserVisitPK userVisitPK, CreateCommunicationEventPurposeDescriptionForm form);
    
    CommandResult<GetCommunicationEventPurposeDescriptionsResult> getCommunicationEventPurposeDescriptions(UserVisitPK userVisitPK, GetCommunicationEventPurposeDescriptionsForm form);
    
    CommandResult<EditCommunicationEventPurposeDescriptionResult> editCommunicationEventPurposeDescription(UserVisitPK userVisitPK, EditCommunicationEventPurposeDescriptionForm form);
    
    CommandResult<?> deleteCommunicationEventPurposeDescription(UserVisitPK userVisitPK, DeleteCommunicationEventPurposeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Communication Source Types
    // -------------------------------------------------------------------------
    
    CommandResult<?> createCommunicationSourceType(UserVisitPK userVisitPK, CreateCommunicationSourceTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Communication Source Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createCommunicationSourceTypeDescription(UserVisitPK userVisitPK, CreateCommunicationSourceTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Communication Sources
    // -------------------------------------------------------------------------
    
    CommandResult<?> createCommunicationSource(UserVisitPK userVisitPK, CreateCommunicationSourceForm form);
    
    CommandResult<GetCommunicationSourcesResult> getCommunicationSources(UserVisitPK userVisitPK, GetCommunicationSourcesForm form);
    
    CommandResult<GetCommunicationSourceResult> getCommunicationSource(UserVisitPK userVisitPK, GetCommunicationSourceForm form);
    
    CommandResult<?> deleteCommunicationSource(UserVisitPK userVisitPK, DeleteCommunicationSourceForm form);
    
    // -------------------------------------------------------------------------
    //   Communication Source Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createCommunicationSourceDescription(UserVisitPK userVisitPK, CreateCommunicationSourceDescriptionForm form);
    
    CommandResult<GetCommunicationSourceDescriptionsResult> getCommunicationSourceDescriptions(UserVisitPK userVisitPK, GetCommunicationSourceDescriptionsForm form);
    
    CommandResult<EditCommunicationSourceDescriptionResult> editCommunicationSourceDescription(UserVisitPK userVisitPK, EditCommunicationSourceDescriptionForm form);
    
    CommandResult<?> deleteCommunicationSourceDescription(UserVisitPK userVisitPK, DeleteCommunicationSourceDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Communication Events
    // -------------------------------------------------------------------------
    
    CommandResult<?> createCommunicationEvent(UserVisitPK userVisitPK, CreateCommunicationEventForm form);
    
    CommandResult<GetCommunicationEventResult> getCommunicationEvent(UserVisitPK userVisitPK, GetCommunicationEventForm form);
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
        
}
