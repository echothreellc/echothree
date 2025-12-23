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

package com.echothree.control.user.communication.server;

import com.echothree.control.user.communication.common.CommunicationRemote;
import com.echothree.control.user.communication.common.form.*;
import com.echothree.control.user.communication.server.server.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;

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
        return CDI.current().select(CreateCommunicationEventRoleTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Communication Event Role Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCommunicationEventRoleTypeDescription(UserVisitPK userVisitPK, CreateCommunicationEventRoleTypeDescriptionForm form) {
        return CDI.current().select(CreateCommunicationEventRoleTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Communication Event Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCommunicationEventType(UserVisitPK userVisitPK, CreateCommunicationEventTypeForm form) {
        return CDI.current().select(CreateCommunicationEventTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Communication Event Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCommunicationEventTypeDescription(UserVisitPK userVisitPK, CreateCommunicationEventTypeDescriptionForm form) {
        return CDI.current().select(CreateCommunicationEventTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Communication Event Purposes
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCommunicationEventPurpose(UserVisitPK userVisitPK, CreateCommunicationEventPurposeForm form) {
        return CDI.current().select(CreateCommunicationEventPurposeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCommunicationEventPurposes(UserVisitPK userVisitPK, GetCommunicationEventPurposesForm form) {
        return CDI.current().select(GetCommunicationEventPurposesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCommunicationEventPurpose(UserVisitPK userVisitPK, GetCommunicationEventPurposeForm form) {
        return CDI.current().select(GetCommunicationEventPurposeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCommunicationEventPurposeChoices(UserVisitPK userVisitPK, GetCommunicationEventPurposeChoicesForm form) {
        return CDI.current().select(GetCommunicationEventPurposeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultCommunicationEventPurpose(UserVisitPK userVisitPK, SetDefaultCommunicationEventPurposeForm form) {
        return CDI.current().select(SetDefaultCommunicationEventPurposeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCommunicationEventPurpose(UserVisitPK userVisitPK, EditCommunicationEventPurposeForm form) {
        return CDI.current().select(EditCommunicationEventPurposeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCommunicationEventPurpose(UserVisitPK userVisitPK, DeleteCommunicationEventPurposeForm form) {
        return CDI.current().select(DeleteCommunicationEventPurposeCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Communication Event Purpose Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCommunicationEventPurposeDescription(UserVisitPK userVisitPK, CreateCommunicationEventPurposeDescriptionForm form) {
        return CDI.current().select(CreateCommunicationEventPurposeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCommunicationEventPurposeDescriptions(UserVisitPK userVisitPK, GetCommunicationEventPurposeDescriptionsForm form) {
        return CDI.current().select(GetCommunicationEventPurposeDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCommunicationEventPurposeDescription(UserVisitPK userVisitPK, EditCommunicationEventPurposeDescriptionForm form) {
        return CDI.current().select(EditCommunicationEventPurposeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCommunicationEventPurposeDescription(UserVisitPK userVisitPK, DeleteCommunicationEventPurposeDescriptionForm form) {
        return CDI.current().select(DeleteCommunicationEventPurposeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Communication Source Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCommunicationSourceType(UserVisitPK userVisitPK, CreateCommunicationSourceTypeForm form) {
        return CDI.current().select(CreateCommunicationSourceTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Communication Source Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCommunicationSourceTypeDescription(UserVisitPK userVisitPK, CreateCommunicationSourceTypeDescriptionForm form) {
        return CDI.current().select(CreateCommunicationSourceTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Communication Sources
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCommunicationSource(UserVisitPK userVisitPK, CreateCommunicationSourceForm form) {
        return CDI.current().select(CreateCommunicationSourceCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCommunicationSources(UserVisitPK userVisitPK, GetCommunicationSourcesForm form) {
        return CDI.current().select(GetCommunicationSourcesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCommunicationSource(UserVisitPK userVisitPK, GetCommunicationSourceForm form) {
        return CDI.current().select(GetCommunicationSourceCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCommunicationSource(UserVisitPK userVisitPK, DeleteCommunicationSourceForm form) {
        return CDI.current().select(DeleteCommunicationSourceCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Communication Source Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCommunicationSourceDescription(UserVisitPK userVisitPK, CreateCommunicationSourceDescriptionForm form) {
        return CDI.current().select(CreateCommunicationSourceDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCommunicationSourceDescriptions(UserVisitPK userVisitPK, GetCommunicationSourceDescriptionsForm form) {
        return CDI.current().select(GetCommunicationSourceDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCommunicationSourceDescription(UserVisitPK userVisitPK, EditCommunicationSourceDescriptionForm form) {
        return CDI.current().select(EditCommunicationSourceDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCommunicationSourceDescription(UserVisitPK userVisitPK, DeleteCommunicationSourceDescriptionForm form) {
        return CDI.current().select(DeleteCommunicationSourceDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Communication Events
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCommunicationEvent(UserVisitPK userVisitPK, CreateCommunicationEventForm form) {
        return CDI.current().select(CreateCommunicationEventCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCommunicationEvent(UserVisitPK userVisitPK, GetCommunicationEventForm form) {
        return CDI.current().select(GetCommunicationEventCommand.class).get().run(userVisitPK, form);
    }
    
}
