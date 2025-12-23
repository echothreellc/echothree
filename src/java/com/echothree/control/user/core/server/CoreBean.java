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

package com.echothree.control.user.core.server;

import com.echothree.control.user.core.common.CoreRemote;
import com.echothree.control.user.core.common.form.*;
import com.echothree.control.user.core.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;

@Stateless
public class CoreBean
        extends CoreFormsImpl
        implements CoreRemote, CoreLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "CoreBean is alive!";
    }
    
    // -------------------------------------------------------------------------
    //   Base Keys
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult generateBaseKeys(UserVisitPK userVisitPK) {
        return CDI.current().select(GenerateBaseKeysCommand.class).get().run(userVisitPK);
    }
    
    @Override
    public CommandResult loadBaseKeys(UserVisitPK userVisitPK, LoadBaseKeysForm form) {
        return CDI.current().select(LoadBaseKeysCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult changeBaseKeys(UserVisitPK userVisitPK, ChangeBaseKeysForm form) {
        return CDI.current().select(ChangeBaseKeysCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Base Encryption Keys
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult getBaseEncryptionKey(UserVisitPK userVisitPK, GetBaseEncryptionKeyForm form) {
        return CDI.current().select(GetBaseEncryptionKeyCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getBaseEncryptionKeys(UserVisitPK userVisitPK, GetBaseEncryptionKeysForm form) {
        return CDI.current().select(GetBaseEncryptionKeysCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getBaseEncryptionKeyStatusChoices(UserVisitPK userVisitPK, GetBaseEncryptionKeyStatusChoicesForm form) {
        return CDI.current().select(GetBaseEncryptionKeyStatusChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setBaseEncryptionKeyStatus(UserVisitPK userVisitPK, SetBaseEncryptionKeyStatusForm form) {
        return CDI.current().select(SetBaseEncryptionKeyStatusCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Entity Locks
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult lockEntity(UserVisitPK userVisitPK, LockEntityForm form) {
        return CDI.current().select(LockEntityCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult unlockEntity(UserVisitPK userVisitPK, UnlockEntityForm form) {
        return CDI.current().select(UnlockEntityCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult removedExpiredEntityLocks(UserVisitPK userVisitPK) {
        return CDI.current().select(RemovedExpiredEntityLocksCommand.class).get().run(userVisitPK);
    }
    
    // -------------------------------------------------------------------------
    //   Component Vendors
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createComponentVendor(UserVisitPK userVisitPK, CreateComponentVendorForm form) {
        return CDI.current().select(CreateComponentVendorCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getComponentVendor(UserVisitPK userVisitPK, GetComponentVendorForm form) {
        return CDI.current().select(GetComponentVendorCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getComponentVendors(UserVisitPK userVisitPK, GetComponentVendorsForm form) {
        return CDI.current().select(GetComponentVendorsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editComponentVendor(UserVisitPK userVisitPK, EditComponentVendorForm form) {
        return CDI.current().select(EditComponentVendorCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteComponentVendor(UserVisitPK userVisitPK, DeleteComponentVendorForm form) {
        return CDI.current().select(DeleteComponentVendorCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Entity Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityType(UserVisitPK userVisitPK, CreateEntityTypeForm form) {
        return CDI.current().select(CreateEntityTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityType(UserVisitPK userVisitPK, GetEntityTypeForm form) {
        return CDI.current().select(GetEntityTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityTypes(UserVisitPK userVisitPK, GetEntityTypesForm form) {
        return CDI.current().select(GetEntityTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editEntityType(UserVisitPK userVisitPK, EditEntityTypeForm form) {
        return CDI.current().select(EditEntityTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteEntityType(UserVisitPK userVisitPK, DeleteEntityTypeForm form) {
        return CDI.current().select(DeleteEntityTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Commands
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCommand(UserVisitPK userVisitPK, CreateCommandForm form) {
        return CDI.current().select(CreateCommandCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCommand(UserVisitPK userVisitPK, GetCommandForm form) {
        return CDI.current().select(GetCommandCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCommands(UserVisitPK userVisitPK, GetCommandsForm form) {
        return CDI.current().select(GetCommandsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCommand(UserVisitPK userVisitPK, EditCommandForm form) {
        return CDI.current().select(EditCommandCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCommand(UserVisitPK userVisitPK, DeleteCommandForm form) {
        return CDI.current().select(DeleteCommandCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Command Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCommandDescription(UserVisitPK userVisitPK, CreateCommandDescriptionForm form) {
        return CDI.current().select(CreateCommandDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCommandDescription(UserVisitPK userVisitPK, GetCommandDescriptionForm form) {
        return CDI.current().select(GetCommandDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCommandDescriptions(UserVisitPK userVisitPK, GetCommandDescriptionsForm form) {
        return CDI.current().select(GetCommandDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCommandDescription(UserVisitPK userVisitPK, EditCommandDescriptionForm form) {
        return CDI.current().select(EditCommandDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCommandDescription(UserVisitPK userVisitPK, DeleteCommandDescriptionForm form) {
        return CDI.current().select(DeleteCommandDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Command Message Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createCommandMessageType(UserVisitPK userVisitPK, CreateCommandMessageTypeForm form) {
        return CDI.current().select(CreateCommandMessageTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCommandMessageTypeChoices(UserVisitPK userVisitPK, GetCommandMessageTypeChoicesForm form) {
        return CDI.current().select(GetCommandMessageTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCommandMessageType(UserVisitPK userVisitPK, GetCommandMessageTypeForm form) {
        return CDI.current().select(GetCommandMessageTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCommandMessageTypes(UserVisitPK userVisitPK, GetCommandMessageTypesForm form) {
        return CDI.current().select(GetCommandMessageTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultCommandMessageType(UserVisitPK userVisitPK, SetDefaultCommandMessageTypeForm form) {
        return CDI.current().select(SetDefaultCommandMessageTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCommandMessageType(UserVisitPK userVisitPK, EditCommandMessageTypeForm form) {
        return CDI.current().select(EditCommandMessageTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCommandMessageType(UserVisitPK userVisitPK, DeleteCommandMessageTypeForm form) {
        return CDI.current().select(DeleteCommandMessageTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Command Message Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createCommandMessageTypeDescription(UserVisitPK userVisitPK, CreateCommandMessageTypeDescriptionForm form) {
        return CDI.current().select(CreateCommandMessageTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCommandMessageTypeDescription(UserVisitPK userVisitPK, GetCommandMessageTypeDescriptionForm form) {
        return CDI.current().select(GetCommandMessageTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCommandMessageTypeDescriptions(UserVisitPK userVisitPK, GetCommandMessageTypeDescriptionsForm form) {
        return CDI.current().select(GetCommandMessageTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCommandMessageTypeDescription(UserVisitPK userVisitPK, EditCommandMessageTypeDescriptionForm form) {
        return CDI.current().select(EditCommandMessageTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCommandMessageTypeDescription(UserVisitPK userVisitPK, DeleteCommandMessageTypeDescriptionForm form) {
        return CDI.current().select(DeleteCommandMessageTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Command Messages
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCommandMessage(UserVisitPK userVisitPK, CreateCommandMessageForm form) {
        return CDI.current().select(CreateCommandMessageCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getCommandMessage(UserVisitPK userVisitPK, GetCommandMessageForm form) {
        return CDI.current().select(GetCommandMessageCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getCommandMessages(UserVisitPK userVisitPK, GetCommandMessagesForm form) {
        return CDI.current().select(GetCommandMessagesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editCommandMessage(UserVisitPK userVisitPK, EditCommandMessageForm form) {
        return CDI.current().select(EditCommandMessageCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteCommandMessage(UserVisitPK userVisitPK, DeleteCommandMessageForm form) {
        return CDI.current().select(DeleteCommandMessageCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Command Message Translations
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createCommandMessageTranslation(UserVisitPK userVisitPK, CreateCommandMessageTranslationForm form) {
        return CDI.current().select(CreateCommandMessageTranslationCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getCommandMessageTranslation(UserVisitPK userVisitPK, GetCommandMessageTranslationForm form) {
        return CDI.current().select(GetCommandMessageTranslationCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getCommandMessageTranslations(UserVisitPK userVisitPK, GetCommandMessageTranslationsForm form) {
        return CDI.current().select(GetCommandMessageTranslationsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editCommandMessageTranslation(UserVisitPK userVisitPK, EditCommandMessageTranslationForm form) {
        return CDI.current().select(EditCommandMessageTranslationCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteCommandMessageTranslation(UserVisitPK userVisitPK, DeleteCommandMessageTranslationForm form) {
        return CDI.current().select(DeleteCommandMessageTranslationCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Entity Instances
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createEntityInstance(UserVisitPK userVisitPK, CreateEntityInstanceForm form) {
        return CDI.current().select(CreateEntityInstanceCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getEntityInstance(UserVisitPK userVisitPK, GetEntityInstanceForm form) {
        return CDI.current().select(GetEntityInstanceCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getEntityInstances(UserVisitPK userVisitPK, GetEntityInstancesForm form) {
        return CDI.current().select(GetEntityInstancesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteEntityInstance(UserVisitPK userVisitPK, DeleteEntityInstanceForm form) {
        return CDI.current().select(DeleteEntityInstanceCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult removeEntityInstance(UserVisitPK userVisitPK, RemoveEntityInstanceForm form) {
        return CDI.current().select(RemoveEntityInstanceCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult generateUuid(UserVisitPK userVisitPK, GenerateUuidForm form) {
        return CDI.current().select(GenerateUuidCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Event Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEventType(UserVisitPK userVisitPK, CreateEventTypeForm form) {
        return CDI.current().select(CreateEventTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Event Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEventTypeDescription(UserVisitPK userVisitPK, CreateEventTypeDescriptionForm form) {
        return CDI.current().select(CreateEventTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Event Groups
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult getEventGroup(UserVisitPK userVisitPK, GetEventGroupForm form) {
        return CDI.current().select(GetEventGroupCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEventGroups(UserVisitPK userVisitPK, GetEventGroupsForm form) {
        return CDI.current().select(GetEventGroupsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEventGroupStatusChoices(UserVisitPK userVisitPK, GetEventGroupStatusChoicesForm form) {
        return CDI.current().select(GetEventGroupStatusChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setEventGroupStatus(UserVisitPK userVisitPK, SetEventGroupStatusForm form) {
        return CDI.current().select(SetEventGroupStatusCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Events
    // -------------------------------------------------------------------------

    @Override
    public CommandResult sendEvent(UserVisitPK userVisitPK, SendEventForm form) {
        return CDI.current().select(SendEventCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getEvents(UserVisitPK userVisitPK, GetEventsForm form) {
        return CDI.current().select(GetEventsCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Queued Events
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult processQueuedEvents(UserVisitPK userVisitPK) {
        return CDI.current().select(ProcessQueuedEventsCommand.class).get().run(userVisitPK);
    }
    
    // -------------------------------------------------------------------------
    //   Components
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createComponent(UserVisitPK userVisitPK, CreateComponentForm form) {
        return CDI.current().select(CreateComponentCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Component Stages
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createComponentStage(UserVisitPK userVisitPK, CreateComponentStageForm form) {
        return CDI.current().select(CreateComponentStageCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Component Versions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createComponentVersion(UserVisitPK userVisitPK, CreateComponentVersionForm form) {
        return CDI.current().select(CreateComponentVersionCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Entity Alias Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createEntityAliasType(UserVisitPK userVisitPK, CreateEntityAliasTypeForm form) {
        return CDI.current().select(CreateEntityAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getEntityAliasType(UserVisitPK userVisitPK, GetEntityAliasTypeForm form) {
        return CDI.current().select(GetEntityAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getEntityAliasTypes(UserVisitPK userVisitPK, GetEntityAliasTypesForm form) {
        return CDI.current().select(GetEntityAliasTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getEntityAliasTypeChoices(UserVisitPK userVisitPK, GetEntityAliasTypeChoicesForm form) {
        return CDI.current().select(GetEntityAliasTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultEntityAliasType(UserVisitPK userVisitPK, SetDefaultEntityAliasTypeForm form) {
        return CDI.current().select(SetDefaultEntityAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editEntityAliasType(UserVisitPK userVisitPK, EditEntityAliasTypeForm form) {
        return CDI.current().select(EditEntityAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteEntityAliasType(UserVisitPK userVisitPK, DeleteEntityAliasTypeForm form) {
        return CDI.current().select(DeleteEntityAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Entity Alias Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createEntityAliasTypeDescription(UserVisitPK userVisitPK, CreateEntityAliasTypeDescriptionForm form) {
        return CDI.current().select(CreateEntityAliasTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getEntityAliasTypeDescription(UserVisitPK userVisitPK, GetEntityAliasTypeDescriptionForm form) {
        return CDI.current().select(GetEntityAliasTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getEntityAliasTypeDescriptions(UserVisitPK userVisitPK, GetEntityAliasTypeDescriptionsForm form) {
        return CDI.current().select(GetEntityAliasTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editEntityAliasTypeDescription(UserVisitPK userVisitPK, EditEntityAliasTypeDescriptionForm form) {
        return CDI.current().select(EditEntityAliasTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteEntityAliasTypeDescription(UserVisitPK userVisitPK, DeleteEntityAliasTypeDescriptionForm form) {
        return CDI.current().select(DeleteEntityAliasTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Entity Aliases
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createEntityAlias(UserVisitPK userVisitPK, CreateEntityAliasForm form) {
        return CDI.current().select(CreateEntityAliasCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getEntityAlias(UserVisitPK userVisitPK, GetEntityAliasForm form) {
        return CDI.current().select(GetEntityAliasCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getEntityAliases(UserVisitPK userVisitPK, GetEntityAliasesForm form) {
        return CDI.current().select(GetEntityAliasesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editEntityAlias(UserVisitPK userVisitPK, EditEntityAliasForm form) {
        return CDI.current().select(EditEntityAliasCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteEntityAlias(UserVisitPK userVisitPK, DeleteEntityAliasForm form) {
        return CDI.current().select(DeleteEntityAliasCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Entity Attribute Group Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityAttributeGroupDescription(UserVisitPK userVisitPK, CreateEntityAttributeGroupDescriptionForm form) {
        return CDI.current().select(CreateEntityAttributeGroupDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityAttributeGroupDescription(UserVisitPK userVisitPK, GetEntityAttributeGroupDescriptionForm form) {
        return CDI.current().select(GetEntityAttributeGroupDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityAttributeGroupDescriptions(UserVisitPK userVisitPK, GetEntityAttributeGroupDescriptionsForm form) {
        return CDI.current().select(GetEntityAttributeGroupDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editEntityAttributeGroupDescription(UserVisitPK userVisitPK, EditEntityAttributeGroupDescriptionForm form) {
        return CDI.current().select(EditEntityAttributeGroupDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteEntityAttributeGroupDescription(UserVisitPK userVisitPK, DeleteEntityAttributeGroupDescriptionForm form) {
        return CDI.current().select(DeleteEntityAttributeGroupDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Entity Attribute Groups
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityAttributeGroup(UserVisitPK userVisitPK, CreateEntityAttributeGroupForm form) {
        return CDI.current().select(CreateEntityAttributeGroupCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityAttributeGroup(UserVisitPK userVisitPK, GetEntityAttributeGroupForm form) {
        return CDI.current().select(GetEntityAttributeGroupCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityAttributeGroups(UserVisitPK userVisitPK, GetEntityAttributeGroupsForm form) {
        return CDI.current().select(GetEntityAttributeGroupsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityAttributeGroupChoices(UserVisitPK userVisitPK, GetEntityAttributeGroupChoicesForm form) {
        return CDI.current().select(GetEntityAttributeGroupChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultEntityAttributeGroup(UserVisitPK userVisitPK, SetDefaultEntityAttributeGroupForm form) {
        return CDI.current().select(SetDefaultEntityAttributeGroupCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editEntityAttributeGroup(UserVisitPK userVisitPK, EditEntityAttributeGroupForm form) {
        return CDI.current().select(EditEntityAttributeGroupCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteEntityAttributeGroup(UserVisitPK userVisitPK, DeleteEntityAttributeGroupForm form) {
        return CDI.current().select(DeleteEntityAttributeGroupCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Entity Attribute Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityAttributeDescription(UserVisitPK userVisitPK, CreateEntityAttributeDescriptionForm form) {
        return CDI.current().select(CreateEntityAttributeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityAttributeDescription(UserVisitPK userVisitPK, GetEntityAttributeDescriptionForm form) {
        return CDI.current().select(GetEntityAttributeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityAttributeDescriptions(UserVisitPK userVisitPK, GetEntityAttributeDescriptionsForm form) {
        return CDI.current().select(GetEntityAttributeDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editEntityAttributeDescription(UserVisitPK userVisitPK, EditEntityAttributeDescriptionForm form) {
        return CDI.current().select(EditEntityAttributeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteEntityAttributeDescription(UserVisitPK userVisitPK, DeleteEntityAttributeDescriptionForm form) {
        return CDI.current().select(DeleteEntityAttributeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Entity Attributes
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityAttribute(UserVisitPK userVisitPK, CreateEntityAttributeForm form) {
        return CDI.current().select(CreateEntityAttributeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityAttribute(UserVisitPK userVisitPK, GetEntityAttributeForm form) {
        return CDI.current().select(GetEntityAttributeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityAttributes(UserVisitPK userVisitPK, GetEntityAttributesForm form) {
        return CDI.current().select(GetEntityAttributesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editEntityAttribute(UserVisitPK userVisitPK, EditEntityAttributeForm form) {
        return CDI.current().select(EditEntityAttributeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteEntityAttribute(UserVisitPK userVisitPK, DeleteEntityAttributeForm form) {
        return CDI.current().select(DeleteEntityAttributeCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Entity Attribute Entity Attribute Groups
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityAttributeEntityAttributeGroup(UserVisitPK userVisitPK, CreateEntityAttributeEntityAttributeGroupForm form) {
        return CDI.current().select(CreateEntityAttributeEntityAttributeGroupCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityAttributeEntityAttributeGroup(UserVisitPK userVisitPK, GetEntityAttributeEntityAttributeGroupForm form) {
        return CDI.current().select(GetEntityAttributeEntityAttributeGroupCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityAttributeEntityAttributeGroups(UserVisitPK userVisitPK, GetEntityAttributeEntityAttributeGroupsForm form) {
        return CDI.current().select(GetEntityAttributeEntityAttributeGroupsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editEntityAttributeEntityAttributeGroup(UserVisitPK userVisitPK, EditEntityAttributeEntityAttributeGroupForm form) {
        return CDI.current().select(EditEntityAttributeEntityAttributeGroupCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteEntityAttributeEntityAttributeGroup(UserVisitPK userVisitPK, DeleteEntityAttributeEntityAttributeGroupForm form) {
        return CDI.current().select(DeleteEntityAttributeEntityAttributeGroupCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Entity Attribute Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityAttributeTypeDescription(UserVisitPK userVisitPK, CreateEntityAttributeTypeDescriptionForm form) {
        return CDI.current().select(CreateEntityAttributeTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Entity Attribute Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityAttributeType(UserVisitPK userVisitPK, CreateEntityAttributeTypeForm form) {
        return CDI.current().select(CreateEntityAttributeTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityAttributeType(UserVisitPK userVisitPK, GetEntityAttributeTypeForm form) {
        return CDI.current().select(GetEntityAttributeTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityAttributeTypes(UserVisitPK userVisitPK, GetEntityAttributeTypesForm form) {
        return CDI.current().select(GetEntityAttributeTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityAttributeTypeChoices(UserVisitPK userVisitPK, GetEntityAttributeTypeChoicesForm form) {
        return CDI.current().select(GetEntityAttributeTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Entity List Item Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityListItemDescription(UserVisitPK userVisitPK, CreateEntityListItemDescriptionForm form) {
        return CDI.current().select(CreateEntityListItemDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityListItemDescription(UserVisitPK userVisitPK, GetEntityListItemDescriptionForm form) {
        return CDI.current().select(GetEntityListItemDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityListItemDescriptions(UserVisitPK userVisitPK, GetEntityListItemDescriptionsForm form) {
        return CDI.current().select(GetEntityListItemDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editEntityListItemDescription(UserVisitPK userVisitPK, EditEntityListItemDescriptionForm form) {
        return CDI.current().select(EditEntityListItemDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteEntityListItemDescription(UserVisitPK userVisitPK, DeleteEntityListItemDescriptionForm form) {
        return CDI.current().select(DeleteEntityListItemDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Entity List Items
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityListItem(UserVisitPK userVisitPK, CreateEntityListItemForm form) {
        return CDI.current().select(CreateEntityListItemCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityListItem(UserVisitPK userVisitPK, GetEntityListItemForm form) {
        return CDI.current().select(GetEntityListItemCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityListItems(UserVisitPK userVisitPK, GetEntityListItemsForm form) {
        return CDI.current().select(GetEntityListItemsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityListItemChoices(UserVisitPK userVisitPK, GetEntityListItemChoicesForm form) {
        return CDI.current().select(GetEntityListItemChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultEntityListItem(UserVisitPK userVisitPK, SetDefaultEntityListItemForm form) {
        return CDI.current().select(SetDefaultEntityListItemCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editEntityListItem(UserVisitPK userVisitPK, EditEntityListItemForm form) {
        return CDI.current().select(EditEntityListItemCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteEntityListItem(UserVisitPK userVisitPK, DeleteEntityListItemForm form) {
        return CDI.current().select(DeleteEntityListItemCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Entity Integer Range Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityIntegerRangeDescription(UserVisitPK userVisitPK, CreateEntityIntegerRangeDescriptionForm form) {
        return CDI.current().select(CreateEntityIntegerRangeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityIntegerRangeDescription(UserVisitPK userVisitPK, GetEntityIntegerRangeDescriptionForm form) {
        return CDI.current().select(GetEntityIntegerRangeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityIntegerRangeDescriptions(UserVisitPK userVisitPK, GetEntityIntegerRangeDescriptionsForm form) {
        return CDI.current().select(GetEntityIntegerRangeDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editEntityIntegerRangeDescription(UserVisitPK userVisitPK, EditEntityIntegerRangeDescriptionForm form) {
        return CDI.current().select(EditEntityIntegerRangeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteEntityIntegerRangeDescription(UserVisitPK userVisitPK, DeleteEntityIntegerRangeDescriptionForm form) {
        return CDI.current().select(DeleteEntityIntegerRangeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Entity Integer Ranges
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityIntegerRange(UserVisitPK userVisitPK, CreateEntityIntegerRangeForm form) {
        return CDI.current().select(CreateEntityIntegerRangeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityIntegerRange(UserVisitPK userVisitPK, GetEntityIntegerRangeForm form) {
        return CDI.current().select(GetEntityIntegerRangeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityIntegerRanges(UserVisitPK userVisitPK, GetEntityIntegerRangesForm form) {
        return CDI.current().select(GetEntityIntegerRangesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityIntegerRangeChoices(UserVisitPK userVisitPK, GetEntityIntegerRangeChoicesForm form) {
        return CDI.current().select(GetEntityIntegerRangeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultEntityIntegerRange(UserVisitPK userVisitPK, SetDefaultEntityIntegerRangeForm form) {
        return CDI.current().select(SetDefaultEntityIntegerRangeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editEntityIntegerRange(UserVisitPK userVisitPK, EditEntityIntegerRangeForm form) {
        return CDI.current().select(EditEntityIntegerRangeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteEntityIntegerRange(UserVisitPK userVisitPK, DeleteEntityIntegerRangeForm form) {
        return CDI.current().select(DeleteEntityIntegerRangeCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Entity Long Range Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityLongRangeDescription(UserVisitPK userVisitPK, CreateEntityLongRangeDescriptionForm form) {
        return CDI.current().select(CreateEntityLongRangeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityLongRangeDescription(UserVisitPK userVisitPK, GetEntityLongRangeDescriptionForm form) {
        return CDI.current().select(GetEntityLongRangeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityLongRangeDescriptions(UserVisitPK userVisitPK, GetEntityLongRangeDescriptionsForm form) {
        return CDI.current().select(GetEntityLongRangeDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editEntityLongRangeDescription(UserVisitPK userVisitPK, EditEntityLongRangeDescriptionForm form) {
        return CDI.current().select(EditEntityLongRangeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteEntityLongRangeDescription(UserVisitPK userVisitPK, DeleteEntityLongRangeDescriptionForm form) {
        return CDI.current().select(DeleteEntityLongRangeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Entity Long Ranges
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityLongRange(UserVisitPK userVisitPK, CreateEntityLongRangeForm form) {
        return CDI.current().select(CreateEntityLongRangeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityLongRange(UserVisitPK userVisitPK, GetEntityLongRangeForm form) {
        return CDI.current().select(GetEntityLongRangeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityLongRanges(UserVisitPK userVisitPK, GetEntityLongRangesForm form) {
        return CDI.current().select(GetEntityLongRangesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityLongRangeChoices(UserVisitPK userVisitPK, GetEntityLongRangeChoicesForm form) {
        return CDI.current().select(GetEntityLongRangeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultEntityLongRange(UserVisitPK userVisitPK, SetDefaultEntityLongRangeForm form) {
        return CDI.current().select(SetDefaultEntityLongRangeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editEntityLongRange(UserVisitPK userVisitPK, EditEntityLongRangeForm form) {
        return CDI.current().select(EditEntityLongRangeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteEntityLongRange(UserVisitPK userVisitPK, DeleteEntityLongRangeForm form) {
        return CDI.current().select(DeleteEntityLongRangeCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Entity Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityTypeDescription(UserVisitPK userVisitPK, CreateEntityTypeDescriptionForm form) {
        return CDI.current().select(CreateEntityTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityTypeDescription(UserVisitPK userVisitPK, GetEntityTypeDescriptionForm form) {
        return CDI.current().select(GetEntityTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityTypeDescriptions(UserVisitPK userVisitPK, GetEntityTypeDescriptionsForm form) {
        return CDI.current().select(GetEntityTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editEntityTypeDescription(UserVisitPK userVisitPK, EditEntityTypeDescriptionForm form) {
        return CDI.current().select(EditEntityTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteEntityTypeDescription(UserVisitPK userVisitPK, DeleteEntityTypeDescriptionForm form) {
        return CDI.current().select(DeleteEntityTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Mime Type Usage Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createMimeTypeUsageType(UserVisitPK userVisitPK, CreateMimeTypeUsageTypeForm form) {
        return CDI.current().select(CreateMimeTypeUsageTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getMimeTypeUsageType(UserVisitPK userVisitPK, GetMimeTypeUsageTypeForm form) {
        return CDI.current().select(GetMimeTypeUsageTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getMimeTypeUsageTypes(UserVisitPK userVisitPK, GetMimeTypeUsageTypesForm form) {
        return CDI.current().select(GetMimeTypeUsageTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getMimeTypeUsageTypeChoices(UserVisitPK userVisitPK, GetMimeTypeUsageTypeChoicesForm form) {
        return CDI.current().select(GetMimeTypeUsageTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Mime Type Usage Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createMimeTypeUsageTypeDescription(UserVisitPK userVisitPK, CreateMimeTypeUsageTypeDescriptionForm form) {
        return CDI.current().select(CreateMimeTypeUsageTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Mime Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createMimeType(UserVisitPK userVisitPK, CreateMimeTypeForm form) {
        return CDI.current().select(CreateMimeTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getMimeTypes(UserVisitPK userVisitPK, GetMimeTypesForm form) {
        return CDI.current().select(GetMimeTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getMimeType(UserVisitPK userVisitPK, GetMimeTypeForm form) {
        return CDI.current().select(GetMimeTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getMimeTypeChoices(UserVisitPK userVisitPK, GetMimeTypeChoicesForm form) {
        return CDI.current().select(GetMimeTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultMimeType(UserVisitPK userVisitPK, SetDefaultMimeTypeForm form) {
        return CDI.current().select(SetDefaultMimeTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editMimeType(UserVisitPK userVisitPK, EditMimeTypeForm form) {
        return CDI.current().select(EditMimeTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteMimeType(UserVisitPK userVisitPK, DeleteMimeTypeForm form) {
        return CDI.current().select(DeleteMimeTypeCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Mime Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createMimeTypeDescription(UserVisitPK userVisitPK, CreateMimeTypeDescriptionForm form) {
        return CDI.current().select(CreateMimeTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getMimeTypeDescriptions(UserVisitPK userVisitPK, GetMimeTypeDescriptionsForm form) {
        return CDI.current().select(GetMimeTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getMimeTypeDescription(UserVisitPK userVisitPK, GetMimeTypeDescriptionForm form) {
        return CDI.current().select(GetMimeTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editMimeTypeDescription(UserVisitPK userVisitPK, EditMimeTypeDescriptionForm form) {
        return CDI.current().select(EditMimeTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteMimeTypeDescription(UserVisitPK userVisitPK, DeleteMimeTypeDescriptionForm form) {
        return CDI.current().select(DeleteMimeTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Mime Type Usages
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createMimeTypeUsage(UserVisitPK userVisitPK, CreateMimeTypeUsageForm form) {
        return CDI.current().select(CreateMimeTypeUsageCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getMimeTypeUsages(UserVisitPK userVisitPK, GetMimeTypeUsagesForm form) {
        return CDI.current().select(GetMimeTypeUsagesCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Mime Type File Extensions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createMimeTypeFileExtension(UserVisitPK userVisitPK, CreateMimeTypeFileExtensionForm form) {
        return CDI.current().select(CreateMimeTypeFileExtensionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getMimeTypeFileExtension(UserVisitPK userVisitPK, GetMimeTypeFileExtensionForm form) {
        return CDI.current().select(GetMimeTypeFileExtensionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getMimeTypeFileExtensions(UserVisitPK userVisitPK, GetMimeTypeFileExtensionsForm form) {
        return CDI.current().select(GetMimeTypeFileExtensionsCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Protocols
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createProtocol(UserVisitPK userVisitPK, CreateProtocolForm form) {
        return CDI.current().select(CreateProtocolCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getProtocolChoices(UserVisitPK userVisitPK, GetProtocolChoicesForm form) {
        return CDI.current().select(GetProtocolChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getProtocol(UserVisitPK userVisitPK, GetProtocolForm form) {
        return CDI.current().select(GetProtocolCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getProtocols(UserVisitPK userVisitPK, GetProtocolsForm form) {
        return CDI.current().select(GetProtocolsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultProtocol(UserVisitPK userVisitPK, SetDefaultProtocolForm form) {
        return CDI.current().select(SetDefaultProtocolCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editProtocol(UserVisitPK userVisitPK, EditProtocolForm form) {
        return CDI.current().select(EditProtocolCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteProtocol(UserVisitPK userVisitPK, DeleteProtocolForm form) {
        return CDI.current().select(DeleteProtocolCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Protocol Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createProtocolDescription(UserVisitPK userVisitPK, CreateProtocolDescriptionForm form) {
        return CDI.current().select(CreateProtocolDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getProtocolDescription(UserVisitPK userVisitPK, GetProtocolDescriptionForm form) {
        return CDI.current().select(GetProtocolDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getProtocolDescriptions(UserVisitPK userVisitPK, GetProtocolDescriptionsForm form) {
        return CDI.current().select(GetProtocolDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editProtocolDescription(UserVisitPK userVisitPK, EditProtocolDescriptionForm form) {
        return CDI.current().select(EditProtocolDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteProtocolDescription(UserVisitPK userVisitPK, DeleteProtocolDescriptionForm form) {
        return CDI.current().select(DeleteProtocolDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Services
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createService(UserVisitPK userVisitPK, CreateServiceForm form) {
        return CDI.current().select(CreateServiceCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getServiceChoices(UserVisitPK userVisitPK, GetServiceChoicesForm form) {
        return CDI.current().select(GetServiceChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getService(UserVisitPK userVisitPK, GetServiceForm form) {
        return CDI.current().select(GetServiceCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getServices(UserVisitPK userVisitPK, GetServicesForm form) {
        return CDI.current().select(GetServicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultService(UserVisitPK userVisitPK, SetDefaultServiceForm form) {
        return CDI.current().select(SetDefaultServiceCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editService(UserVisitPK userVisitPK, EditServiceForm form) {
        return CDI.current().select(EditServiceCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteService(UserVisitPK userVisitPK, DeleteServiceForm form) {
        return CDI.current().select(DeleteServiceCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Service Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createServiceDescription(UserVisitPK userVisitPK, CreateServiceDescriptionForm form) {
        return CDI.current().select(CreateServiceDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getServiceDescription(UserVisitPK userVisitPK, GetServiceDescriptionForm form) {
        return CDI.current().select(GetServiceDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getServiceDescriptions(UserVisitPK userVisitPK, GetServiceDescriptionsForm form) {
        return CDI.current().select(GetServiceDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editServiceDescription(UserVisitPK userVisitPK, EditServiceDescriptionForm form) {
        return CDI.current().select(EditServiceDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteServiceDescription(UserVisitPK userVisitPK, DeleteServiceDescriptionForm form) {
        return CDI.current().select(DeleteServiceDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Servers
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createServer(UserVisitPK userVisitPK, CreateServerForm form) {
        return CDI.current().select(CreateServerCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getServerChoices(UserVisitPK userVisitPK, GetServerChoicesForm form) {
        return CDI.current().select(GetServerChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getServer(UserVisitPK userVisitPK, GetServerForm form) {
        return CDI.current().select(GetServerCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getServers(UserVisitPK userVisitPK, GetServersForm form) {
        return CDI.current().select(GetServersCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultServer(UserVisitPK userVisitPK, SetDefaultServerForm form) {
        return CDI.current().select(SetDefaultServerCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editServer(UserVisitPK userVisitPK, EditServerForm form) {
        return CDI.current().select(EditServerCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteServer(UserVisitPK userVisitPK, DeleteServerForm form) {
        return CDI.current().select(DeleteServerCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Server Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createServerDescription(UserVisitPK userVisitPK, CreateServerDescriptionForm form) {
        return CDI.current().select(CreateServerDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getServerDescription(UserVisitPK userVisitPK, GetServerDescriptionForm form) {
        return CDI.current().select(GetServerDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getServerDescriptions(UserVisitPK userVisitPK, GetServerDescriptionsForm form) {
        return CDI.current().select(GetServerDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editServerDescription(UserVisitPK userVisitPK, EditServerDescriptionForm form) {
        return CDI.current().select(EditServerDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteServerDescription(UserVisitPK userVisitPK, DeleteServerDescriptionForm form) {
        return CDI.current().select(DeleteServerDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Server Services
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createServerService(UserVisitPK userVisitPK, CreateServerServiceForm form) {
        return CDI.current().select(CreateServerServiceCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getServerService(UserVisitPK userVisitPK, GetServerServiceForm form) {
        return CDI.current().select(GetServerServiceCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getServerServices(UserVisitPK userVisitPK, GetServerServicesForm form) {
        return CDI.current().select(GetServerServicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteServerService(UserVisitPK userVisitPK, DeleteServerServiceForm form) {
        return CDI.current().select(DeleteServerServiceCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Entity Boolean Defaults
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createEntityBooleanDefault(UserVisitPK userVisitPK, CreateEntityBooleanDefaultForm form) {
        return CDI.current().select(CreateEntityBooleanDefaultCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editEntityBooleanDefault(UserVisitPK userVisitPK, EditEntityBooleanDefaultForm form) {
        return CDI.current().select(EditEntityBooleanDefaultCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteEntityBooleanDefault(UserVisitPK userVisitPK, DeleteEntityBooleanDefaultForm form) {
        return CDI.current().select(DeleteEntityBooleanDefaultCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Entity Boolean Attributes
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createEntityBooleanAttribute(UserVisitPK userVisitPK, CreateEntityBooleanAttributeForm form) {
        return CDI.current().select(CreateEntityBooleanAttributeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editEntityBooleanAttribute(UserVisitPK userVisitPK, EditEntityBooleanAttributeForm form) {
        return CDI.current().select(EditEntityBooleanAttributeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteEntityBooleanAttribute(UserVisitPK userVisitPK, DeleteEntityBooleanAttributeForm form) {
        return CDI.current().select(DeleteEntityBooleanAttributeCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Entity Integer Defaults
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createEntityIntegerDefault(UserVisitPK userVisitPK, CreateEntityIntegerDefaultForm form) {
        return CDI.current().select(CreateEntityIntegerDefaultCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editEntityIntegerDefault(UserVisitPK userVisitPK, EditEntityIntegerDefaultForm form) {
        return CDI.current().select(EditEntityIntegerDefaultCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteEntityIntegerDefault(UserVisitPK userVisitPK, DeleteEntityIntegerDefaultForm form) {
        return CDI.current().select(DeleteEntityIntegerDefaultCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Entity Integer Attributes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityIntegerAttribute(UserVisitPK userVisitPK, CreateEntityIntegerAttributeForm form) {
        return CDI.current().select(CreateEntityIntegerAttributeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editEntityIntegerAttribute(UserVisitPK userVisitPK, EditEntityIntegerAttributeForm form) {
        return CDI.current().select(EditEntityIntegerAttributeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteEntityIntegerAttribute(UserVisitPK userVisitPK, DeleteEntityIntegerAttributeForm form) {
        return CDI.current().select(DeleteEntityIntegerAttributeCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Entity List Item Defaults
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createEntityListItemDefault(UserVisitPK userVisitPK, CreateEntityListItemDefaultForm form) {
        return CDI.current().select(CreateEntityListItemDefaultCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editEntityListItemDefault(UserVisitPK userVisitPK, EditEntityListItemDefaultForm form) {
        return CDI.current().select(EditEntityListItemDefaultCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteEntityListItemDefault(UserVisitPK userVisitPK, DeleteEntityListItemDefaultForm form) {
        return CDI.current().select(DeleteEntityListItemDefaultCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Entity List Item Attributes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityListItemAttribute(UserVisitPK userVisitPK, CreateEntityListItemAttributeForm form) {
        return CDI.current().select(CreateEntityListItemAttributeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editEntityListItemAttribute(UserVisitPK userVisitPK, EditEntityListItemAttributeForm form) {
        return CDI.current().select(EditEntityListItemAttributeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteEntityListItemAttribute(UserVisitPK userVisitPK, DeleteEntityListItemAttributeForm form) {
        return CDI.current().select(DeleteEntityListItemAttributeCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Entity Long Defaults
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createEntityLongDefault(UserVisitPK userVisitPK, CreateEntityLongDefaultForm form) {
        return CDI.current().select(CreateEntityLongDefaultCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editEntityLongDefault(UserVisitPK userVisitPK, EditEntityLongDefaultForm form) {
        return CDI.current().select(EditEntityLongDefaultCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteEntityLongDefault(UserVisitPK userVisitPK, DeleteEntityLongDefaultForm form) {
        return CDI.current().select(DeleteEntityLongDefaultCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Entity Long Attributes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityLongAttribute(UserVisitPK userVisitPK, CreateEntityLongAttributeForm form) {
        return CDI.current().select(CreateEntityLongAttributeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editEntityLongAttribute(UserVisitPK userVisitPK, EditEntityLongAttributeForm form) {
        return CDI.current().select(EditEntityLongAttributeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteEntityLongAttribute(UserVisitPK userVisitPK, DeleteEntityLongAttributeForm form) {
        return CDI.current().select(DeleteEntityLongAttributeCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Entity Multiple List Item Defaults
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createEntityMultipleListItemDefault(UserVisitPK userVisitPK, CreateEntityMultipleListItemDefaultForm form) {
        return CDI.current().select(CreateEntityMultipleListItemDefaultCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteEntityMultipleListItemDefault(UserVisitPK userVisitPK, DeleteEntityMultipleListItemDefaultForm form) {
        return CDI.current().select(DeleteEntityMultipleListItemDefaultCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Entity Multiple List Item Attributes
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createEntityMultipleListItemAttribute(UserVisitPK userVisitPK, CreateEntityMultipleListItemAttributeForm form) {
        return CDI.current().select(CreateEntityMultipleListItemAttributeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteEntityMultipleListItemAttribute(UserVisitPK userVisitPK, DeleteEntityMultipleListItemAttributeForm form) {
        return CDI.current().select(DeleteEntityMultipleListItemAttributeCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Entity Name Attributes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityNameAttribute(UserVisitPK userVisitPK, CreateEntityNameAttributeForm form) {
        return CDI.current().select(CreateEntityNameAttributeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editEntityNameAttribute(UserVisitPK userVisitPK, EditEntityNameAttributeForm form) {
        return CDI.current().select(EditEntityNameAttributeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteEntityNameAttribute(UserVisitPK userVisitPK, DeleteEntityNameAttributeForm form) {
        return CDI.current().select(DeleteEntityNameAttributeCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Entity String Defaults
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createEntityStringDefault(UserVisitPK userVisitPK, CreateEntityStringDefaultForm form) {
        return CDI.current().select(CreateEntityStringDefaultCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editEntityStringDefault(UserVisitPK userVisitPK, EditEntityStringDefaultForm form) {
        return CDI.current().select(EditEntityStringDefaultCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteEntityStringDefault(UserVisitPK userVisitPK, DeleteEntityStringDefaultForm form) {
        return CDI.current().select(DeleteEntityStringDefaultCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Entity String Attributes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityStringAttribute(UserVisitPK userVisitPK, CreateEntityStringAttributeForm form) {
        return CDI.current().select(CreateEntityStringAttributeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editEntityStringAttribute(UserVisitPK userVisitPK, EditEntityStringAttributeForm form) {
        return CDI.current().select(EditEntityStringAttributeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteEntityStringAttribute(UserVisitPK userVisitPK, DeleteEntityStringAttributeForm form) {
        return CDI.current().select(DeleteEntityStringAttributeCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Entity Geo Point Defaults
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createEntityGeoPointDefault(UserVisitPK userVisitPK, CreateEntityGeoPointDefaultForm form) {
        return CDI.current().select(CreateEntityGeoPointDefaultCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editEntityGeoPointDefault(UserVisitPK userVisitPK, EditEntityGeoPointDefaultForm form) {
        return CDI.current().select(EditEntityGeoPointDefaultCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteEntityGeoPointDefault(UserVisitPK userVisitPK, DeleteEntityGeoPointDefaultForm form) {
        return CDI.current().select(DeleteEntityGeoPointDefaultCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Entity Geo Point Attributes
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createEntityGeoPointAttribute(UserVisitPK userVisitPK, CreateEntityGeoPointAttributeForm form) {
        return CDI.current().select(CreateEntityGeoPointAttributeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editEntityGeoPointAttribute(UserVisitPK userVisitPK, EditEntityGeoPointAttributeForm form) {
        return CDI.current().select(EditEntityGeoPointAttributeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteEntityGeoPointAttribute(UserVisitPK userVisitPK, DeleteEntityGeoPointAttributeForm form) {
        return CDI.current().select(DeleteEntityGeoPointAttributeCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Entity Blob Attributes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityBlobAttribute(UserVisitPK userVisitPK, CreateEntityBlobAttributeForm form) {
        return CDI.current().select(CreateEntityBlobAttributeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editEntityBlobAttribute(UserVisitPK userVisitPK, EditEntityBlobAttributeForm form) {
        return CDI.current().select(EditEntityBlobAttributeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getEntityBlobAttribute(UserVisitPK userVisitPK, GetEntityBlobAttributeForm form) {
        return CDI.current().select(GetEntityBlobAttributeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteEntityBlobAttribute(UserVisitPK userVisitPK, DeleteEntityBlobAttributeForm form) {
        return CDI.current().select(DeleteEntityBlobAttributeCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Clob Attributes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityClobAttribute(UserVisitPK userVisitPK, CreateEntityClobAttributeForm form) {
        return CDI.current().select(CreateEntityClobAttributeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editEntityClobAttribute(UserVisitPK userVisitPK, EditEntityClobAttributeForm form) {
        return CDI.current().select(EditEntityClobAttributeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getEntityClobAttribute(UserVisitPK userVisitPK, GetEntityClobAttributeForm form) {
        return CDI.current().select(GetEntityClobAttributeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteEntityClobAttribute(UserVisitPK userVisitPK, DeleteEntityClobAttributeForm form) {
        return CDI.current().select(DeleteEntityClobAttributeCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Entity Time Defaults
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createEntityTimeDefault(UserVisitPK userVisitPK, CreateEntityTimeDefaultForm form) {
        return CDI.current().select(CreateEntityTimeDefaultCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editEntityTimeDefault(UserVisitPK userVisitPK, EditEntityTimeDefaultForm form) {
        return CDI.current().select(EditEntityTimeDefaultCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteEntityTimeDefault(UserVisitPK userVisitPK, DeleteEntityTimeDefaultForm form) {
        return CDI.current().select(DeleteEntityTimeDefaultCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Entity Time Attributes
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createEntityTimeAttribute(UserVisitPK userVisitPK, CreateEntityTimeAttributeForm form) {
        return CDI.current().select(CreateEntityTimeAttributeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editEntityTimeAttribute(UserVisitPK userVisitPK, EditEntityTimeAttributeForm form) {
        return CDI.current().select(EditEntityTimeAttributeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteEntityTimeAttribute(UserVisitPK userVisitPK, DeleteEntityTimeAttributeForm form) {
        return CDI.current().select(DeleteEntityTimeAttributeCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Entity Date Defaults
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createEntityDateDefault(UserVisitPK userVisitPK, CreateEntityDateDefaultForm form) {
        return CDI.current().select(CreateEntityDateDefaultCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editEntityDateDefault(UserVisitPK userVisitPK, EditEntityDateDefaultForm form) {
        return CDI.current().select(EditEntityDateDefaultCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteEntityDateDefault(UserVisitPK userVisitPK, DeleteEntityDateDefaultForm form) {
        return CDI.current().select(DeleteEntityDateDefaultCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Entity Date Attributes
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createEntityDateAttribute(UserVisitPK userVisitPK, CreateEntityDateAttributeForm form) {
        return CDI.current().select(CreateEntityDateAttributeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editEntityDateAttribute(UserVisitPK userVisitPK, EditEntityDateAttributeForm form) {
        return CDI.current().select(EditEntityDateAttributeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteEntityDateAttribute(UserVisitPK userVisitPK, DeleteEntityDateAttributeForm form) {
        return CDI.current().select(DeleteEntityDateAttributeCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Entity Attribute Entity Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityAttributeEntityType(UserVisitPK userVisitPK, CreateEntityAttributeEntityTypeForm form) {
        return CDI.current().select(CreateEntityAttributeEntityTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityAttributeEntityType(UserVisitPK userVisitPK, GetEntityAttributeEntityTypeForm form) {
        return CDI.current().select(GetEntityAttributeEntityTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getEntityAttributeEntityTypes(UserVisitPK userVisitPK, GetEntityAttributeEntityTypesForm form) {
        return CDI.current().select(GetEntityAttributeEntityTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteEntityAttributeEntityType(UserVisitPK userVisitPK, DeleteEntityAttributeEntityTypeForm form) {
        return CDI.current().select(DeleteEntityAttributeEntityTypeCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Entity Entity Attributes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityEntityAttribute(UserVisitPK userVisitPK, CreateEntityEntityAttributeForm form) {
        return CDI.current().select(CreateEntityEntityAttributeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editEntityEntityAttribute(UserVisitPK userVisitPK, EditEntityEntityAttributeForm form) {
        return CDI.current().select(EditEntityEntityAttributeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteEntityEntityAttribute(UserVisitPK userVisitPK, DeleteEntityEntityAttributeForm form) {
        return CDI.current().select(DeleteEntityEntityAttributeCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Collection Attributes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityCollectionAttribute(UserVisitPK userVisitPK, CreateEntityCollectionAttributeForm form) {
        return CDI.current().select(CreateEntityCollectionAttributeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteEntityCollectionAttribute(UserVisitPK userVisitPK, DeleteEntityCollectionAttributeForm form) {
        return CDI.current().select(DeleteEntityCollectionAttributeCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Entity Workflow Attributes
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createEntityWorkflowAttribute(UserVisitPK userVisitPK, CreateEntityWorkflowAttributeForm form) {
        return CDI.current().select(CreateEntityWorkflowAttributeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setEntityWorkflowAttributeStatus(UserVisitPK userVisitPK, SetEntityWorkflowAttributeStatusForm form) {
        return CDI.current().select(SetEntityWorkflowAttributeStatusCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteEntityWorkflowAttribute(UserVisitPK userVisitPK, DeleteEntityWorkflowAttributeForm form) {
        return CDI.current().select(DeleteEntityWorkflowAttributeCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Cache Entries
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createCacheEntry(UserVisitPK userVisitPK, CreateCacheEntryForm form) {
        return CDI.current().select(CreateCacheEntryCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getCacheEntry(UserVisitPK userVisitPK, GetCacheEntryForm form) {
        return CDI.current().select(GetCacheEntryCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getCacheEntries(UserVisitPK userVisitPK, GetCacheEntriesForm form) {
        return CDI.current().select(GetCacheEntriesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult removeCacheEntry(UserVisitPK userVisitPK, RemoveCacheEntryForm form) {
        return CDI.current().select(RemoveCacheEntryCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Cache Entry Dependencies
    // -------------------------------------------------------------------------

    @Override
    public CommandResult getCacheEntryDependencies(UserVisitPK userVisitPK, GetCacheEntryDependenciesForm form) {
        return CDI.current().select(GetCacheEntryDependenciesCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Applications
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createApplication(UserVisitPK userVisitPK, CreateApplicationForm form) {
        return CDI.current().select(CreateApplicationCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getApplicationChoices(UserVisitPK userVisitPK, GetApplicationChoicesForm form) {
        return CDI.current().select(GetApplicationChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getApplication(UserVisitPK userVisitPK, GetApplicationForm form) {
        return CDI.current().select(GetApplicationCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getApplications(UserVisitPK userVisitPK, GetApplicationsForm form) {
        return CDI.current().select(GetApplicationsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultApplication(UserVisitPK userVisitPK, SetDefaultApplicationForm form) {
        return CDI.current().select(SetDefaultApplicationCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editApplication(UserVisitPK userVisitPK, EditApplicationForm form) {
        return CDI.current().select(EditApplicationCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteApplication(UserVisitPK userVisitPK, DeleteApplicationForm form) {
        return CDI.current().select(DeleteApplicationCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Applications Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createApplicationDescription(UserVisitPK userVisitPK, CreateApplicationDescriptionForm form) {
        return CDI.current().select(CreateApplicationDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getApplicationDescription(UserVisitPK userVisitPK, GetApplicationDescriptionForm form) {
        return CDI.current().select(GetApplicationDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getApplicationDescriptions(UserVisitPK userVisitPK, GetApplicationDescriptionsForm form) {
        return CDI.current().select(GetApplicationDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editApplicationDescription(UserVisitPK userVisitPK, EditApplicationDescriptionForm form) {
        return CDI.current().select(EditApplicationDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteApplicationDescription(UserVisitPK userVisitPK, DeleteApplicationDescriptionForm form) {
        return CDI.current().select(DeleteApplicationDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Editors
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEditor(UserVisitPK userVisitPK, CreateEditorForm form) {
        return CDI.current().select(CreateEditorCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEditorChoices(UserVisitPK userVisitPK, GetEditorChoicesForm form) {
        return CDI.current().select(GetEditorChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEditor(UserVisitPK userVisitPK, GetEditorForm form) {
        return CDI.current().select(GetEditorCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEditors(UserVisitPK userVisitPK, GetEditorsForm form) {
        return CDI.current().select(GetEditorsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultEditor(UserVisitPK userVisitPK, SetDefaultEditorForm form) {
        return CDI.current().select(SetDefaultEditorCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editEditor(UserVisitPK userVisitPK, EditEditorForm form) {
        return CDI.current().select(EditEditorCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteEditor(UserVisitPK userVisitPK, DeleteEditorForm form) {
        return CDI.current().select(DeleteEditorCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Editors Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEditorDescription(UserVisitPK userVisitPK, CreateEditorDescriptionForm form) {
        return CDI.current().select(CreateEditorDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEditorDescription(UserVisitPK userVisitPK, GetEditorDescriptionForm form) {
        return CDI.current().select(GetEditorDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEditorDescriptions(UserVisitPK userVisitPK, GetEditorDescriptionsForm form) {
        return CDI.current().select(GetEditorDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editEditorDescription(UserVisitPK userVisitPK, EditEditorDescriptionForm form) {
        return CDI.current().select(EditEditorDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteEditorDescription(UserVisitPK userVisitPK, DeleteEditorDescriptionForm form) {
        return CDI.current().select(DeleteEditorDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   ApplicationEditors
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createApplicationEditor(UserVisitPK userVisitPK, CreateApplicationEditorForm form) {
        return CDI.current().select(CreateApplicationEditorCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getApplicationEditorChoices(UserVisitPK userVisitPK, GetApplicationEditorChoicesForm form) {
        return CDI.current().select(GetApplicationEditorChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getApplicationEditor(UserVisitPK userVisitPK, GetApplicationEditorForm form) {
        return CDI.current().select(GetApplicationEditorCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getApplicationEditors(UserVisitPK userVisitPK, GetApplicationEditorsForm form) {
        return CDI.current().select(GetApplicationEditorsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultApplicationEditor(UserVisitPK userVisitPK, SetDefaultApplicationEditorForm form) {
        return CDI.current().select(SetDefaultApplicationEditorCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editApplicationEditor(UserVisitPK userVisitPK, EditApplicationEditorForm form) {
        return CDI.current().select(EditApplicationEditorCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteApplicationEditor(UserVisitPK userVisitPK, DeleteApplicationEditorForm form) {
        return CDI.current().select(DeleteApplicationEditorCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Application Editor Uses
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createApplicationEditorUse(UserVisitPK userVisitPK, CreateApplicationEditorUseForm form) {
        return CDI.current().select(CreateApplicationEditorUseCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getApplicationEditorUseChoices(UserVisitPK userVisitPK, GetApplicationEditorUseChoicesForm form) {
        return CDI.current().select(GetApplicationEditorUseChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getApplicationEditorUse(UserVisitPK userVisitPK, GetApplicationEditorUseForm form) {
        return CDI.current().select(GetApplicationEditorUseCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getApplicationEditorUses(UserVisitPK userVisitPK, GetApplicationEditorUsesForm form) {
        return CDI.current().select(GetApplicationEditorUsesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultApplicationEditorUse(UserVisitPK userVisitPK, SetDefaultApplicationEditorUseForm form) {
        return CDI.current().select(SetDefaultApplicationEditorUseCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editApplicationEditorUse(UserVisitPK userVisitPK, EditApplicationEditorUseForm form) {
        return CDI.current().select(EditApplicationEditorUseCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteApplicationEditorUse(UserVisitPK userVisitPK, DeleteApplicationEditorUseForm form) {
        return CDI.current().select(DeleteApplicationEditorUseCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Application Editor Use Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createApplicationEditorUseDescription(UserVisitPK userVisitPK, CreateApplicationEditorUseDescriptionForm form) {
        return CDI.current().select(CreateApplicationEditorUseDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getApplicationEditorUseDescription(UserVisitPK userVisitPK, GetApplicationEditorUseDescriptionForm form) {
        return CDI.current().select(GetApplicationEditorUseDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getApplicationEditorUseDescriptions(UserVisitPK userVisitPK, GetApplicationEditorUseDescriptionsForm form) {
        return CDI.current().select(GetApplicationEditorUseDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editApplicationEditorUseDescription(UserVisitPK userVisitPK, EditApplicationEditorUseDescriptionForm form) {
        return CDI.current().select(EditApplicationEditorUseDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteApplicationEditorUseDescription(UserVisitPK userVisitPK, DeleteApplicationEditorUseDescriptionForm form) {
        return CDI.current().select(DeleteApplicationEditorUseDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Appearances
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createAppearance(UserVisitPK userVisitPK, CreateAppearanceForm form) {
        return CDI.current().select(CreateAppearanceCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getAppearanceChoices(UserVisitPK userVisitPK, GetAppearanceChoicesForm form) {
        return CDI.current().select(GetAppearanceChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getAppearance(UserVisitPK userVisitPK, GetAppearanceForm form) {
        return CDI.current().select(GetAppearanceCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getAppearances(UserVisitPK userVisitPK, GetAppearancesForm form) {
        return CDI.current().select(GetAppearancesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultAppearance(UserVisitPK userVisitPK, SetDefaultAppearanceForm form) {
        return CDI.current().select(SetDefaultAppearanceCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editAppearance(UserVisitPK userVisitPK, EditAppearanceForm form) {
        return CDI.current().select(EditAppearanceCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteAppearance(UserVisitPK userVisitPK, DeleteAppearanceForm form) {
        return CDI.current().select(DeleteAppearanceCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Appearances Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createAppearanceDescription(UserVisitPK userVisitPK, CreateAppearanceDescriptionForm form) {
        return CDI.current().select(CreateAppearanceDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getAppearanceDescription(UserVisitPK userVisitPK, GetAppearanceDescriptionForm form) {
        return CDI.current().select(GetAppearanceDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getAppearanceDescriptions(UserVisitPK userVisitPK, GetAppearanceDescriptionsForm form) {
        return CDI.current().select(GetAppearanceDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editAppearanceDescription(UserVisitPK userVisitPK, EditAppearanceDescriptionForm form) {
        return CDI.current().select(EditAppearanceDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteAppearanceDescription(UserVisitPK userVisitPK, DeleteAppearanceDescriptionForm form) {
        return CDI.current().select(DeleteAppearanceDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Appearances Text Decorations
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createAppearanceTextDecoration(UserVisitPK userVisitPK, CreateAppearanceTextDecorationForm form) {
        return CDI.current().select(CreateAppearanceTextDecorationCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getAppearanceTextDecoration(UserVisitPK userVisitPK, GetAppearanceTextDecorationForm form) {
        return CDI.current().select(GetAppearanceTextDecorationCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getAppearanceTextDecorations(UserVisitPK userVisitPK, GetAppearanceTextDecorationsForm form) {
        return CDI.current().select(GetAppearanceTextDecorationsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteAppearanceTextDecoration(UserVisitPK userVisitPK, DeleteAppearanceTextDecorationForm form) {
        return CDI.current().select(DeleteAppearanceTextDecorationCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Appearances Text Transformations
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createAppearanceTextTransformation(UserVisitPK userVisitPK, CreateAppearanceTextTransformationForm form) {
        return CDI.current().select(CreateAppearanceTextTransformationCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getAppearanceTextTransformation(UserVisitPK userVisitPK, GetAppearanceTextTransformationForm form) {
        return CDI.current().select(GetAppearanceTextTransformationCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getAppearanceTextTransformations(UserVisitPK userVisitPK, GetAppearanceTextTransformationsForm form) {
        return CDI.current().select(GetAppearanceTextTransformationsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteAppearanceTextTransformation(UserVisitPK userVisitPK, DeleteAppearanceTextTransformationForm form) {
        return CDI.current().select(DeleteAppearanceTextTransformationCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Colors
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createColor(UserVisitPK userVisitPK, CreateColorForm form) {
        return CDI.current().select(CreateColorCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getColorChoices(UserVisitPK userVisitPK, GetColorChoicesForm form) {
        return CDI.current().select(GetColorChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getColor(UserVisitPK userVisitPK, GetColorForm form) {
        return CDI.current().select(GetColorCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getColors(UserVisitPK userVisitPK, GetColorsForm form) {
        return CDI.current().select(GetColorsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultColor(UserVisitPK userVisitPK, SetDefaultColorForm form) {
        return CDI.current().select(SetDefaultColorCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editColor(UserVisitPK userVisitPK, EditColorForm form) {
        return CDI.current().select(EditColorCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteColor(UserVisitPK userVisitPK, DeleteColorForm form) {
        return CDI.current().select(DeleteColorCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Color Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createColorDescription(UserVisitPK userVisitPK, CreateColorDescriptionForm form) {
        return CDI.current().select(CreateColorDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getColorDescription(UserVisitPK userVisitPK, GetColorDescriptionForm form) {
        return CDI.current().select(GetColorDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getColorDescriptions(UserVisitPK userVisitPK, GetColorDescriptionsForm form) {
        return CDI.current().select(GetColorDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editColorDescription(UserVisitPK userVisitPK, EditColorDescriptionForm form) {
        return CDI.current().select(EditColorDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteColorDescription(UserVisitPK userVisitPK, DeleteColorDescriptionForm form) {
        return CDI.current().select(DeleteColorDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Font Styles
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createFontStyle(UserVisitPK userVisitPK, CreateFontStyleForm form) {
        return CDI.current().select(CreateFontStyleCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getFontStyleChoices(UserVisitPK userVisitPK, GetFontStyleChoicesForm form) {
        return CDI.current().select(GetFontStyleChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getFontStyle(UserVisitPK userVisitPK, GetFontStyleForm form) {
        return CDI.current().select(GetFontStyleCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getFontStyles(UserVisitPK userVisitPK, GetFontStylesForm form) {
        return CDI.current().select(GetFontStylesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultFontStyle(UserVisitPK userVisitPK, SetDefaultFontStyleForm form) {
        return CDI.current().select(SetDefaultFontStyleCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editFontStyle(UserVisitPK userVisitPK, EditFontStyleForm form) {
        return CDI.current().select(EditFontStyleCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteFontStyle(UserVisitPK userVisitPK, DeleteFontStyleForm form) {
        return CDI.current().select(DeleteFontStyleCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Font Style Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createFontStyleDescription(UserVisitPK userVisitPK, CreateFontStyleDescriptionForm form) {
        return CDI.current().select(CreateFontStyleDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getFontStyleDescription(UserVisitPK userVisitPK, GetFontStyleDescriptionForm form) {
        return CDI.current().select(GetFontStyleDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getFontStyleDescriptions(UserVisitPK userVisitPK, GetFontStyleDescriptionsForm form) {
        return CDI.current().select(GetFontStyleDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editFontStyleDescription(UserVisitPK userVisitPK, EditFontStyleDescriptionForm form) {
        return CDI.current().select(EditFontStyleDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteFontStyleDescription(UserVisitPK userVisitPK, DeleteFontStyleDescriptionForm form) {
        return CDI.current().select(DeleteFontStyleDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Font Weights
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createFontWeight(UserVisitPK userVisitPK, CreateFontWeightForm form) {
        return CDI.current().select(CreateFontWeightCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getFontWeightChoices(UserVisitPK userVisitPK, GetFontWeightChoicesForm form) {
        return CDI.current().select(GetFontWeightChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getFontWeight(UserVisitPK userVisitPK, GetFontWeightForm form) {
        return CDI.current().select(GetFontWeightCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getFontWeights(UserVisitPK userVisitPK, GetFontWeightsForm form) {
        return CDI.current().select(GetFontWeightsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultFontWeight(UserVisitPK userVisitPK, SetDefaultFontWeightForm form) {
        return CDI.current().select(SetDefaultFontWeightCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editFontWeight(UserVisitPK userVisitPK, EditFontWeightForm form) {
        return CDI.current().select(EditFontWeightCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteFontWeight(UserVisitPK userVisitPK, DeleteFontWeightForm form) {
        return CDI.current().select(DeleteFontWeightCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Font Weight Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createFontWeightDescription(UserVisitPK userVisitPK, CreateFontWeightDescriptionForm form) {
        return CDI.current().select(CreateFontWeightDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getFontWeightDescription(UserVisitPK userVisitPK, GetFontWeightDescriptionForm form) {
        return CDI.current().select(GetFontWeightDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getFontWeightDescriptions(UserVisitPK userVisitPK, GetFontWeightDescriptionsForm form) {
        return CDI.current().select(GetFontWeightDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editFontWeightDescription(UserVisitPK userVisitPK, EditFontWeightDescriptionForm form) {
        return CDI.current().select(EditFontWeightDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteFontWeightDescription(UserVisitPK userVisitPK, DeleteFontWeightDescriptionForm form) {
        return CDI.current().select(DeleteFontWeightDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Text Decorations
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createTextDecoration(UserVisitPK userVisitPK, CreateTextDecorationForm form) {
        return CDI.current().select(CreateTextDecorationCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTextDecorationChoices(UserVisitPK userVisitPK, GetTextDecorationChoicesForm form) {
        return CDI.current().select(GetTextDecorationChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTextDecoration(UserVisitPK userVisitPK, GetTextDecorationForm form) {
        return CDI.current().select(GetTextDecorationCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTextDecorations(UserVisitPK userVisitPK, GetTextDecorationsForm form) {
        return CDI.current().select(GetTextDecorationsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultTextDecoration(UserVisitPK userVisitPK, SetDefaultTextDecorationForm form) {
        return CDI.current().select(SetDefaultTextDecorationCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editTextDecoration(UserVisitPK userVisitPK, EditTextDecorationForm form) {
        return CDI.current().select(EditTextDecorationCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTextDecoration(UserVisitPK userVisitPK, DeleteTextDecorationForm form) {
        return CDI.current().select(DeleteTextDecorationCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Text Decoration Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createTextDecorationDescription(UserVisitPK userVisitPK, CreateTextDecorationDescriptionForm form) {
        return CDI.current().select(CreateTextDecorationDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTextDecorationDescription(UserVisitPK userVisitPK, GetTextDecorationDescriptionForm form) {
        return CDI.current().select(GetTextDecorationDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTextDecorationDescriptions(UserVisitPK userVisitPK, GetTextDecorationDescriptionsForm form) {
        return CDI.current().select(GetTextDecorationDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editTextDecorationDescription(UserVisitPK userVisitPK, EditTextDecorationDescriptionForm form) {
        return CDI.current().select(EditTextDecorationDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTextDecorationDescription(UserVisitPK userVisitPK, DeleteTextDecorationDescriptionForm form) {
        return CDI.current().select(DeleteTextDecorationDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Text Transformations
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createTextTransformation(UserVisitPK userVisitPK, CreateTextTransformationForm form) {
        return CDI.current().select(CreateTextTransformationCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTextTransformationChoices(UserVisitPK userVisitPK, GetTextTransformationChoicesForm form) {
        return CDI.current().select(GetTextTransformationChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTextTransformation(UserVisitPK userVisitPK, GetTextTransformationForm form) {
        return CDI.current().select(GetTextTransformationCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTextTransformations(UserVisitPK userVisitPK, GetTextTransformationsForm form) {
        return CDI.current().select(GetTextTransformationsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultTextTransformation(UserVisitPK userVisitPK, SetDefaultTextTransformationForm form) {
        return CDI.current().select(SetDefaultTextTransformationCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editTextTransformation(UserVisitPK userVisitPK, EditTextTransformationForm form) {
        return CDI.current().select(EditTextTransformationCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTextTransformation(UserVisitPK userVisitPK, DeleteTextTransformationForm form) {
        return CDI.current().select(DeleteTextTransformationCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Text Transformation Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createTextTransformationDescription(UserVisitPK userVisitPK, CreateTextTransformationDescriptionForm form) {
        return CDI.current().select(CreateTextTransformationDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTextTransformationDescription(UserVisitPK userVisitPK, GetTextTransformationDescriptionForm form) {
        return CDI.current().select(GetTextTransformationDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTextTransformationDescriptions(UserVisitPK userVisitPK, GetTextTransformationDescriptionsForm form) {
        return CDI.current().select(GetTextTransformationDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editTextTransformationDescription(UserVisitPK userVisitPK, EditTextTransformationDescriptionForm form) {
        return CDI.current().select(EditTextTransformationDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTextTransformationDescription(UserVisitPK userVisitPK, DeleteTextTransformationDescriptionForm form) {
        return CDI.current().select(DeleteTextTransformationDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Appearances
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityAppearance(UserVisitPK userVisitPK, CreateEntityAppearanceForm form) {
        return CDI.current().select(CreateEntityAppearanceCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityAppearance(UserVisitPK userVisitPK, GetEntityAppearanceForm form) {
        return CDI.current().select(GetEntityAppearanceCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editEntityAppearance(UserVisitPK userVisitPK, EditEntityAppearanceForm form) {
        return CDI.current().select(EditEntityAppearanceCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteEntityAppearance(UserVisitPK userVisitPK, DeleteEntityAppearanceForm form) {
        return CDI.current().select(DeleteEntityAppearanceCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Utilities
    // -------------------------------------------------------------------------

    @Override
    public CommandResult encrypt(UserVisitPK userVisitPK, EncryptForm form) {
        return CDI.current().select(EncryptCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult decrypt(UserVisitPK userVisitPK, DecryptForm form) {
        return CDI.current().select(DecryptCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult validate(UserVisitPK userVisitPK, ValidateForm form) {
        return CDI.current().select(ValidateCommand.class).get().run(userVisitPK, form);
    }

}
