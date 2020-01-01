// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.control.user.communication.server;

import com.echothree.control.user.communication.common.CommunicationRemote;
import com.echothree.control.user.communication.common.form.*;
import com.echothree.control.user.communication.server.server.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

@Stateless
public class CommunicationBean
        extends CommunicationFormsImpl
        implements CommunicationRemote, CommunicationLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "CommunicationBean is alive!";
    }
    
    // -------------------------------------------------------------------------
    //   Communication Event Role Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCommunicationEventRoleType(UserVisitPK userVisitPK, CreateCommunicationEventRoleTypeForm form) {
        return new CreateCommunicationEventRoleTypeCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Communication Event Role Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCommunicationEventRoleTypeDescription(UserVisitPK userVisitPK, CreateCommunicationEventRoleTypeDescriptionForm form) {
        return new CreateCommunicationEventRoleTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Communication Event Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCommunicationEventType(UserVisitPK userVisitPK, CreateCommunicationEventTypeForm form) {
        return new CreateCommunicationEventTypeCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Communication Event Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCommunicationEventTypeDescription(UserVisitPK userVisitPK, CreateCommunicationEventTypeDescriptionForm form) {
        return new CreateCommunicationEventTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Communication Event Purposes
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCommunicationEventPurpose(UserVisitPK userVisitPK, CreateCommunicationEventPurposeForm form) {
        return new CreateCommunicationEventPurposeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCommunicationEventPurposes(UserVisitPK userVisitPK, GetCommunicationEventPurposesForm form) {
        return new GetCommunicationEventPurposesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCommunicationEventPurpose(UserVisitPK userVisitPK, GetCommunicationEventPurposeForm form) {
        return new GetCommunicationEventPurposeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCommunicationEventPurposeChoices(UserVisitPK userVisitPK, GetCommunicationEventPurposeChoicesForm form) {
        return new GetCommunicationEventPurposeChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultCommunicationEventPurpose(UserVisitPK userVisitPK, SetDefaultCommunicationEventPurposeForm form) {
        return new SetDefaultCommunicationEventPurposeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editCommunicationEventPurpose(UserVisitPK userVisitPK, EditCommunicationEventPurposeForm form) {
        return new EditCommunicationEventPurposeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteCommunicationEventPurpose(UserVisitPK userVisitPK, DeleteCommunicationEventPurposeForm form) {
        return new DeleteCommunicationEventPurposeCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Communication Event Purpose Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCommunicationEventPurposeDescription(UserVisitPK userVisitPK, CreateCommunicationEventPurposeDescriptionForm form) {
        return new CreateCommunicationEventPurposeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCommunicationEventPurposeDescriptions(UserVisitPK userVisitPK, GetCommunicationEventPurposeDescriptionsForm form) {
        return new GetCommunicationEventPurposeDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editCommunicationEventPurposeDescription(UserVisitPK userVisitPK, EditCommunicationEventPurposeDescriptionForm form) {
        return new EditCommunicationEventPurposeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteCommunicationEventPurposeDescription(UserVisitPK userVisitPK, DeleteCommunicationEventPurposeDescriptionForm form) {
        return new DeleteCommunicationEventPurposeDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Communication Source Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCommunicationSourceType(UserVisitPK userVisitPK, CreateCommunicationSourceTypeForm form) {
        return new CreateCommunicationSourceTypeCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Communication Source Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCommunicationSourceTypeDescription(UserVisitPK userVisitPK, CreateCommunicationSourceTypeDescriptionForm form) {
        return new CreateCommunicationSourceTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Communication Sources
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCommunicationSource(UserVisitPK userVisitPK, CreateCommunicationSourceForm form) {
        return new CreateCommunicationSourceCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCommunicationSources(UserVisitPK userVisitPK, GetCommunicationSourcesForm form) {
        return new GetCommunicationSourcesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCommunicationSource(UserVisitPK userVisitPK, GetCommunicationSourceForm form) {
        return new GetCommunicationSourceCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteCommunicationSource(UserVisitPK userVisitPK, DeleteCommunicationSourceForm form) {
        return new DeleteCommunicationSourceCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Communication Source Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCommunicationSourceDescription(UserVisitPK userVisitPK, CreateCommunicationSourceDescriptionForm form) {
        return new CreateCommunicationSourceDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCommunicationSourceDescriptions(UserVisitPK userVisitPK, GetCommunicationSourceDescriptionsForm form) {
        return new GetCommunicationSourceDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editCommunicationSourceDescription(UserVisitPK userVisitPK, EditCommunicationSourceDescriptionForm form) {
        return new EditCommunicationSourceDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteCommunicationSourceDescription(UserVisitPK userVisitPK, DeleteCommunicationSourceDescriptionForm form) {
        return new DeleteCommunicationSourceDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Communication Events
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCommunicationEvent(UserVisitPK userVisitPK, CreateCommunicationEventForm form) {
        return new CreateCommunicationEventCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCommunicationEvent(UserVisitPK userVisitPK, GetCommunicationEventForm form) {
        return new GetCommunicationEventCommand(userVisitPK, form).run();
    }
    
}
