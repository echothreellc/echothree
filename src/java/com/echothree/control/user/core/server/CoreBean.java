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

package com.echothree.control.user.core.server;

import com.echothree.control.user.core.common.CoreRemote;
import com.echothree.control.user.core.common.form.*;
import com.echothree.control.user.core.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

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
        return new GenerateBaseKeysCommand().run(userVisitPK);
    }
    
    @Override
    public CommandResult loadBaseKeys(UserVisitPK userVisitPK, LoadBaseKeysForm form) {
        return new LoadBaseKeysCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult changeBaseKeys(UserVisitPK userVisitPK, ChangeBaseKeysForm form) {
        return new ChangeBaseKeysCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Base Encryption Keys
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult getBaseEncryptionKey(UserVisitPK userVisitPK, GetBaseEncryptionKeyForm form) {
        return new GetBaseEncryptionKeyCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getBaseEncryptionKeys(UserVisitPK userVisitPK, GetBaseEncryptionKeysForm form) {
        return new GetBaseEncryptionKeysCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getBaseEncryptionKeyStatusChoices(UserVisitPK userVisitPK, GetBaseEncryptionKeyStatusChoicesForm form) {
        return new GetBaseEncryptionKeyStatusChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setBaseEncryptionKeyStatus(UserVisitPK userVisitPK, SetBaseEncryptionKeyStatusForm form) {
        return new SetBaseEncryptionKeyStatusCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Entity Locks
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult lockEntity(UserVisitPK userVisitPK, LockEntityForm form) {
        return new LockEntityCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult unlockEntity(UserVisitPK userVisitPK, UnlockEntityForm form) {
        return new UnlockEntityCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult removedExpiredEntityLocks(UserVisitPK userVisitPK) {
        return new RemovedExpiredEntityLocksCommand().run(userVisitPK);
    }
    
    // -------------------------------------------------------------------------
    //   Component Vendors
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createComponentVendor(UserVisitPK userVisitPK, CreateComponentVendorForm form) {
        return new CreateComponentVendorCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getComponentVendor(UserVisitPK userVisitPK, GetComponentVendorForm form) {
        return new GetComponentVendorCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getComponentVendors(UserVisitPK userVisitPK, GetComponentVendorsForm form) {
        return new GetComponentVendorsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editComponentVendor(UserVisitPK userVisitPK, EditComponentVendorForm form) {
        return new EditComponentVendorCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteComponentVendor(UserVisitPK userVisitPK, DeleteComponentVendorForm form) {
        return new DeleteComponentVendorCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Entity Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityType(UserVisitPK userVisitPK, CreateEntityTypeForm form) {
        return new CreateEntityTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityType(UserVisitPK userVisitPK, GetEntityTypeForm form) {
        return new GetEntityTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityTypes(UserVisitPK userVisitPK, GetEntityTypesForm form) {
        return new GetEntityTypesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editEntityType(UserVisitPK userVisitPK, EditEntityTypeForm form) {
        return new EditEntityTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteEntityType(UserVisitPK userVisitPK, DeleteEntityTypeForm form) {
        return new DeleteEntityTypeCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Commands
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCommand(UserVisitPK userVisitPK, CreateCommandForm form) {
        return new CreateCommandCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCommand(UserVisitPK userVisitPK, GetCommandForm form) {
        return new GetCommandCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCommands(UserVisitPK userVisitPK, GetCommandsForm form) {
        return new GetCommandsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCommand(UserVisitPK userVisitPK, EditCommandForm form) {
        return new EditCommandCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCommand(UserVisitPK userVisitPK, DeleteCommandForm form) {
        return new DeleteCommandCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Command Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCommandDescription(UserVisitPK userVisitPK, CreateCommandDescriptionForm form) {
        return new CreateCommandDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCommandDescription(UserVisitPK userVisitPK, GetCommandDescriptionForm form) {
        return new GetCommandDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCommandDescriptions(UserVisitPK userVisitPK, GetCommandDescriptionsForm form) {
        return new GetCommandDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCommandDescription(UserVisitPK userVisitPK, EditCommandDescriptionForm form) {
        return new EditCommandDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCommandDescription(UserVisitPK userVisitPK, DeleteCommandDescriptionForm form) {
        return new DeleteCommandDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Command Message Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createCommandMessageType(UserVisitPK userVisitPK, CreateCommandMessageTypeForm form) {
        return new CreateCommandMessageTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCommandMessageTypeChoices(UserVisitPK userVisitPK, GetCommandMessageTypeChoicesForm form) {
        return new GetCommandMessageTypeChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCommandMessageType(UserVisitPK userVisitPK, GetCommandMessageTypeForm form) {
        return new GetCommandMessageTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCommandMessageTypes(UserVisitPK userVisitPK, GetCommandMessageTypesForm form) {
        return new GetCommandMessageTypesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultCommandMessageType(UserVisitPK userVisitPK, SetDefaultCommandMessageTypeForm form) {
        return new SetDefaultCommandMessageTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCommandMessageType(UserVisitPK userVisitPK, EditCommandMessageTypeForm form) {
        return new EditCommandMessageTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCommandMessageType(UserVisitPK userVisitPK, DeleteCommandMessageTypeForm form) {
        return new DeleteCommandMessageTypeCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Command Message Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createCommandMessageTypeDescription(UserVisitPK userVisitPK, CreateCommandMessageTypeDescriptionForm form) {
        return new CreateCommandMessageTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCommandMessageTypeDescription(UserVisitPK userVisitPK, GetCommandMessageTypeDescriptionForm form) {
        return new GetCommandMessageTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCommandMessageTypeDescriptions(UserVisitPK userVisitPK, GetCommandMessageTypeDescriptionsForm form) {
        return new GetCommandMessageTypeDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCommandMessageTypeDescription(UserVisitPK userVisitPK, EditCommandMessageTypeDescriptionForm form) {
        return new EditCommandMessageTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCommandMessageTypeDescription(UserVisitPK userVisitPK, DeleteCommandMessageTypeDescriptionForm form) {
        return new DeleteCommandMessageTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Command Messages
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCommandMessage(UserVisitPK userVisitPK, CreateCommandMessageForm form) {
        return new CreateCommandMessageCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getCommandMessage(UserVisitPK userVisitPK, GetCommandMessageForm form) {
        return new GetCommandMessageCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getCommandMessages(UserVisitPK userVisitPK, GetCommandMessagesForm form) {
        return new GetCommandMessagesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editCommandMessage(UserVisitPK userVisitPK, EditCommandMessageForm form) {
        return new EditCommandMessageCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteCommandMessage(UserVisitPK userVisitPK, DeleteCommandMessageForm form) {
        return new DeleteCommandMessageCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Command Message Translations
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createCommandMessageTranslation(UserVisitPK userVisitPK, CreateCommandMessageTranslationForm form) {
        return new CreateCommandMessageTranslationCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getCommandMessageTranslation(UserVisitPK userVisitPK, GetCommandMessageTranslationForm form) {
        return new GetCommandMessageTranslationCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getCommandMessageTranslations(UserVisitPK userVisitPK, GetCommandMessageTranslationsForm form) {
        return new GetCommandMessageTranslationsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editCommandMessageTranslation(UserVisitPK userVisitPK, EditCommandMessageTranslationForm form) {
        return new EditCommandMessageTranslationCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteCommandMessageTranslation(UserVisitPK userVisitPK, DeleteCommandMessageTranslationForm form) {
        return new DeleteCommandMessageTranslationCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Entity Instances
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createEntityInstance(UserVisitPK userVisitPK, CreateEntityInstanceForm form) {
        return new CreateEntityInstanceCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getEntityInstance(UserVisitPK userVisitPK, GetEntityInstanceForm form) {
        return new GetEntityInstanceCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getEntityInstances(UserVisitPK userVisitPK, GetEntityInstancesForm form) {
        return new GetEntityInstancesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteEntityInstance(UserVisitPK userVisitPK, DeleteEntityInstanceForm form) {
        return new DeleteEntityInstanceCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult removeEntityInstance(UserVisitPK userVisitPK, RemoveEntityInstanceForm form) {
        return new RemoveEntityInstanceCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult generateUuid(UserVisitPK userVisitPK, GenerateUuidForm form) {
        return new GenerateUuidCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Event Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEventType(UserVisitPK userVisitPK, CreateEventTypeForm form) {
        return new CreateEventTypeCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Event Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEventTypeDescription(UserVisitPK userVisitPK, CreateEventTypeDescriptionForm form) {
        return new CreateEventTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Event Groups
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult getEventGroup(UserVisitPK userVisitPK, GetEventGroupForm form) {
        return new GetEventGroupCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEventGroups(UserVisitPK userVisitPK, GetEventGroupsForm form) {
        return new GetEventGroupsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEventGroupStatusChoices(UserVisitPK userVisitPK, GetEventGroupStatusChoicesForm form) {
        return new GetEventGroupStatusChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setEventGroupStatus(UserVisitPK userVisitPK, SetEventGroupStatusForm form) {
        return new SetEventGroupStatusCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Events
    // -------------------------------------------------------------------------

    @Override
    public CommandResult sendEvent(UserVisitPK userVisitPK, SendEventForm form) {
        return new SendEventCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getEvents(UserVisitPK userVisitPK, GetEventsForm form) {
        return new GetEventsCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Queued Events
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult processQueuedEvents(UserVisitPK userVisitPK) {
        return new ProcessQueuedEventsCommand().run(userVisitPK);
    }
    
    // -------------------------------------------------------------------------
    //   Components
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createComponent(UserVisitPK userVisitPK, CreateComponentForm form) {
        return new CreateComponentCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Component Stages
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createComponentStage(UserVisitPK userVisitPK, CreateComponentStageForm form) {
        return new CreateComponentStageCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Component Versions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createComponentVersion(UserVisitPK userVisitPK, CreateComponentVersionForm form) {
        return new CreateComponentVersionCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Entity Alias Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createEntityAliasType(UserVisitPK userVisitPK, CreateEntityAliasTypeForm form) {
        return new CreateEntityAliasTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getEntityAliasType(UserVisitPK userVisitPK, GetEntityAliasTypeForm form) {
        return new GetEntityAliasTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getEntityAliasTypes(UserVisitPK userVisitPK, GetEntityAliasTypesForm form) {
        return new GetEntityAliasTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getEntityAliasTypeChoices(UserVisitPK userVisitPK, GetEntityAliasTypeChoicesForm form) {
        return new GetEntityAliasTypeChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultEntityAliasType(UserVisitPK userVisitPK, SetDefaultEntityAliasTypeForm form) {
        return new SetDefaultEntityAliasTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editEntityAliasType(UserVisitPK userVisitPK, EditEntityAliasTypeForm form) {
        return new EditEntityAliasTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteEntityAliasType(UserVisitPK userVisitPK, DeleteEntityAliasTypeForm form) {
        return new DeleteEntityAliasTypeCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Entity Alias Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createEntityAliasTypeDescription(UserVisitPK userVisitPK, CreateEntityAliasTypeDescriptionForm form) {
        return new CreateEntityAliasTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getEntityAliasTypeDescription(UserVisitPK userVisitPK, GetEntityAliasTypeDescriptionForm form) {
        return new GetEntityAliasTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getEntityAliasTypeDescriptions(UserVisitPK userVisitPK, GetEntityAliasTypeDescriptionsForm form) {
        return new GetEntityAliasTypeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editEntityAliasTypeDescription(UserVisitPK userVisitPK, EditEntityAliasTypeDescriptionForm form) {
        return new EditEntityAliasTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteEntityAliasTypeDescription(UserVisitPK userVisitPK, DeleteEntityAliasTypeDescriptionForm form) {
        return new DeleteEntityAliasTypeDescriptionCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Entity Aliases
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createEntityAlias(UserVisitPK userVisitPK, CreateEntityAliasForm form) {
        return new CreateEntityAliasCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getEntityAlias(UserVisitPK userVisitPK, GetEntityAliasForm form) {
        return new GetEntityAliasCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getEntityAliases(UserVisitPK userVisitPK, GetEntityAliasesForm form) {
        return new GetEntityAliasesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editEntityAlias(UserVisitPK userVisitPK, EditEntityAliasForm form) {
        return new EditEntityAliasCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteEntityAlias(UserVisitPK userVisitPK, DeleteEntityAliasForm form) {
        return new DeleteEntityAliasCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Entity Attribute Group Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityAttributeGroupDescription(UserVisitPK userVisitPK, CreateEntityAttributeGroupDescriptionForm form) {
        return new CreateEntityAttributeGroupDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityAttributeGroupDescription(UserVisitPK userVisitPK, GetEntityAttributeGroupDescriptionForm form) {
        return new GetEntityAttributeGroupDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityAttributeGroupDescriptions(UserVisitPK userVisitPK, GetEntityAttributeGroupDescriptionsForm form) {
        return new GetEntityAttributeGroupDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editEntityAttributeGroupDescription(UserVisitPK userVisitPK, EditEntityAttributeGroupDescriptionForm form) {
        return new EditEntityAttributeGroupDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteEntityAttributeGroupDescription(UserVisitPK userVisitPK, DeleteEntityAttributeGroupDescriptionForm form) {
        return new DeleteEntityAttributeGroupDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Entity Attribute Groups
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityAttributeGroup(UserVisitPK userVisitPK, CreateEntityAttributeGroupForm form) {
        return new CreateEntityAttributeGroupCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityAttributeGroup(UserVisitPK userVisitPK, GetEntityAttributeGroupForm form) {
        return new GetEntityAttributeGroupCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityAttributeGroups(UserVisitPK userVisitPK, GetEntityAttributeGroupsForm form) {
        return new GetEntityAttributeGroupsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityAttributeGroupChoices(UserVisitPK userVisitPK, GetEntityAttributeGroupChoicesForm form) {
        return new GetEntityAttributeGroupChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultEntityAttributeGroup(UserVisitPK userVisitPK, SetDefaultEntityAttributeGroupForm form) {
        return new SetDefaultEntityAttributeGroupCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editEntityAttributeGroup(UserVisitPK userVisitPK, EditEntityAttributeGroupForm form) {
        return new EditEntityAttributeGroupCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteEntityAttributeGroup(UserVisitPK userVisitPK, DeleteEntityAttributeGroupForm form) {
        return new DeleteEntityAttributeGroupCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Entity Attribute Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityAttributeDescription(UserVisitPK userVisitPK, CreateEntityAttributeDescriptionForm form) {
        return new CreateEntityAttributeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityAttributeDescription(UserVisitPK userVisitPK, GetEntityAttributeDescriptionForm form) {
        return new GetEntityAttributeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityAttributeDescriptions(UserVisitPK userVisitPK, GetEntityAttributeDescriptionsForm form) {
        return new GetEntityAttributeDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editEntityAttributeDescription(UserVisitPK userVisitPK, EditEntityAttributeDescriptionForm form) {
        return new EditEntityAttributeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteEntityAttributeDescription(UserVisitPK userVisitPK, DeleteEntityAttributeDescriptionForm form) {
        return new DeleteEntityAttributeDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Entity Attributes
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityAttribute(UserVisitPK userVisitPK, CreateEntityAttributeForm form) {
        return new CreateEntityAttributeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityAttribute(UserVisitPK userVisitPK, GetEntityAttributeForm form) {
        return new GetEntityAttributeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityAttributes(UserVisitPK userVisitPK, GetEntityAttributesForm form) {
        return new GetEntityAttributesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editEntityAttribute(UserVisitPK userVisitPK, EditEntityAttributeForm form) {
        return new EditEntityAttributeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteEntityAttribute(UserVisitPK userVisitPK, DeleteEntityAttributeForm form) {
        return new DeleteEntityAttributeCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Entity Attribute Entity Attribute Groups
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityAttributeEntityAttributeGroup(UserVisitPK userVisitPK, CreateEntityAttributeEntityAttributeGroupForm form) {
        return new CreateEntityAttributeEntityAttributeGroupCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityAttributeEntityAttributeGroup(UserVisitPK userVisitPK, GetEntityAttributeEntityAttributeGroupForm form) {
        return new GetEntityAttributeEntityAttributeGroupCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityAttributeEntityAttributeGroups(UserVisitPK userVisitPK, GetEntityAttributeEntityAttributeGroupsForm form) {
        return new GetEntityAttributeEntityAttributeGroupsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editEntityAttributeEntityAttributeGroup(UserVisitPK userVisitPK, EditEntityAttributeEntityAttributeGroupForm form) {
        return new EditEntityAttributeEntityAttributeGroupCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteEntityAttributeEntityAttributeGroup(UserVisitPK userVisitPK, DeleteEntityAttributeEntityAttributeGroupForm form) {
        return new DeleteEntityAttributeEntityAttributeGroupCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Entity Attribute Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityAttributeTypeDescription(UserVisitPK userVisitPK, CreateEntityAttributeTypeDescriptionForm form) {
        return new CreateEntityAttributeTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Entity Attribute Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityAttributeType(UserVisitPK userVisitPK, CreateEntityAttributeTypeForm form) {
        return new CreateEntityAttributeTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityAttributeType(UserVisitPK userVisitPK, GetEntityAttributeTypeForm form) {
        return new GetEntityAttributeTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityAttributeTypes(UserVisitPK userVisitPK, GetEntityAttributeTypesForm form) {
        return new GetEntityAttributeTypesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityAttributeTypeChoices(UserVisitPK userVisitPK, GetEntityAttributeTypeChoicesForm form) {
        return new GetEntityAttributeTypeChoicesCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Entity List Item Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityListItemDescription(UserVisitPK userVisitPK, CreateEntityListItemDescriptionForm form) {
        return new CreateEntityListItemDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityListItemDescription(UserVisitPK userVisitPK, GetEntityListItemDescriptionForm form) {
        return new GetEntityListItemDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityListItemDescriptions(UserVisitPK userVisitPK, GetEntityListItemDescriptionsForm form) {
        return new GetEntityListItemDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editEntityListItemDescription(UserVisitPK userVisitPK, EditEntityListItemDescriptionForm form) {
        return new EditEntityListItemDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteEntityListItemDescription(UserVisitPK userVisitPK, DeleteEntityListItemDescriptionForm form) {
        return new DeleteEntityListItemDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Entity List Items
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityListItem(UserVisitPK userVisitPK, CreateEntityListItemForm form) {
        return new CreateEntityListItemCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityListItem(UserVisitPK userVisitPK, GetEntityListItemForm form) {
        return new GetEntityListItemCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityListItems(UserVisitPK userVisitPK, GetEntityListItemsForm form) {
        return new GetEntityListItemsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityListItemChoices(UserVisitPK userVisitPK, GetEntityListItemChoicesForm form) {
        return new GetEntityListItemChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultEntityListItem(UserVisitPK userVisitPK, SetDefaultEntityListItemForm form) {
        return new SetDefaultEntityListItemCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editEntityListItem(UserVisitPK userVisitPK, EditEntityListItemForm form) {
        return new EditEntityListItemCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteEntityListItem(UserVisitPK userVisitPK, DeleteEntityListItemForm form) {
        return new DeleteEntityListItemCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Entity Integer Range Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityIntegerRangeDescription(UserVisitPK userVisitPK, CreateEntityIntegerRangeDescriptionForm form) {
        return new CreateEntityIntegerRangeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityIntegerRangeDescription(UserVisitPK userVisitPK, GetEntityIntegerRangeDescriptionForm form) {
        return new GetEntityIntegerRangeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityIntegerRangeDescriptions(UserVisitPK userVisitPK, GetEntityIntegerRangeDescriptionsForm form) {
        return new GetEntityIntegerRangeDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editEntityIntegerRangeDescription(UserVisitPK userVisitPK, EditEntityIntegerRangeDescriptionForm form) {
        return new EditEntityIntegerRangeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteEntityIntegerRangeDescription(UserVisitPK userVisitPK, DeleteEntityIntegerRangeDescriptionForm form) {
        return new DeleteEntityIntegerRangeDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Entity Integer Ranges
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityIntegerRange(UserVisitPK userVisitPK, CreateEntityIntegerRangeForm form) {
        return new CreateEntityIntegerRangeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityIntegerRange(UserVisitPK userVisitPK, GetEntityIntegerRangeForm form) {
        return new GetEntityIntegerRangeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityIntegerRanges(UserVisitPK userVisitPK, GetEntityIntegerRangesForm form) {
        return new GetEntityIntegerRangesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityIntegerRangeChoices(UserVisitPK userVisitPK, GetEntityIntegerRangeChoicesForm form) {
        return new GetEntityIntegerRangeChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultEntityIntegerRange(UserVisitPK userVisitPK, SetDefaultEntityIntegerRangeForm form) {
        return new SetDefaultEntityIntegerRangeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editEntityIntegerRange(UserVisitPK userVisitPK, EditEntityIntegerRangeForm form) {
        return new EditEntityIntegerRangeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteEntityIntegerRange(UserVisitPK userVisitPK, DeleteEntityIntegerRangeForm form) {
        return new DeleteEntityIntegerRangeCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Entity Long Range Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityLongRangeDescription(UserVisitPK userVisitPK, CreateEntityLongRangeDescriptionForm form) {
        return new CreateEntityLongRangeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityLongRangeDescription(UserVisitPK userVisitPK, GetEntityLongRangeDescriptionForm form) {
        return new GetEntityLongRangeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityLongRangeDescriptions(UserVisitPK userVisitPK, GetEntityLongRangeDescriptionsForm form) {
        return new GetEntityLongRangeDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editEntityLongRangeDescription(UserVisitPK userVisitPK, EditEntityLongRangeDescriptionForm form) {
        return new EditEntityLongRangeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteEntityLongRangeDescription(UserVisitPK userVisitPK, DeleteEntityLongRangeDescriptionForm form) {
        return new DeleteEntityLongRangeDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Entity Long Ranges
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityLongRange(UserVisitPK userVisitPK, CreateEntityLongRangeForm form) {
        return new CreateEntityLongRangeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityLongRange(UserVisitPK userVisitPK, GetEntityLongRangeForm form) {
        return new GetEntityLongRangeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityLongRanges(UserVisitPK userVisitPK, GetEntityLongRangesForm form) {
        return new GetEntityLongRangesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityLongRangeChoices(UserVisitPK userVisitPK, GetEntityLongRangeChoicesForm form) {
        return new GetEntityLongRangeChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultEntityLongRange(UserVisitPK userVisitPK, SetDefaultEntityLongRangeForm form) {
        return new SetDefaultEntityLongRangeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editEntityLongRange(UserVisitPK userVisitPK, EditEntityLongRangeForm form) {
        return new EditEntityLongRangeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteEntityLongRange(UserVisitPK userVisitPK, DeleteEntityLongRangeForm form) {
        return new DeleteEntityLongRangeCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Entity Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityTypeDescription(UserVisitPK userVisitPK, CreateEntityTypeDescriptionForm form) {
        return new CreateEntityTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityTypeDescription(UserVisitPK userVisitPK, GetEntityTypeDescriptionForm form) {
        return new GetEntityTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityTypeDescriptions(UserVisitPK userVisitPK, GetEntityTypeDescriptionsForm form) {
        return new GetEntityTypeDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editEntityTypeDescription(UserVisitPK userVisitPK, EditEntityTypeDescriptionForm form) {
        return new EditEntityTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteEntityTypeDescription(UserVisitPK userVisitPK, DeleteEntityTypeDescriptionForm form) {
        return new DeleteEntityTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Mime Type Usage Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createMimeTypeUsageType(UserVisitPK userVisitPK, CreateMimeTypeUsageTypeForm form) {
        return new CreateMimeTypeUsageTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getMimeTypeUsageType(UserVisitPK userVisitPK, GetMimeTypeUsageTypeForm form) {
        return new GetMimeTypeUsageTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getMimeTypeUsageTypes(UserVisitPK userVisitPK, GetMimeTypeUsageTypesForm form) {
        return new GetMimeTypeUsageTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getMimeTypeUsageTypeChoices(UserVisitPK userVisitPK, GetMimeTypeUsageTypeChoicesForm form) {
        return new GetMimeTypeUsageTypeChoicesCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Mime Type Usage Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createMimeTypeUsageTypeDescription(UserVisitPK userVisitPK, CreateMimeTypeUsageTypeDescriptionForm form) {
        return new CreateMimeTypeUsageTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Mime Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createMimeType(UserVisitPK userVisitPK, CreateMimeTypeForm form) {
        return new CreateMimeTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getMimeTypes(UserVisitPK userVisitPK, GetMimeTypesForm form) {
        return new GetMimeTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getMimeType(UserVisitPK userVisitPK, GetMimeTypeForm form) {
        return new GetMimeTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getMimeTypeChoices(UserVisitPK userVisitPK, GetMimeTypeChoicesForm form) {
        return new GetMimeTypeChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultMimeType(UserVisitPK userVisitPK, SetDefaultMimeTypeForm form) {
        return new SetDefaultMimeTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editMimeType(UserVisitPK userVisitPK, EditMimeTypeForm form) {
        return new EditMimeTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteMimeType(UserVisitPK userVisitPK, DeleteMimeTypeForm form) {
        return new DeleteMimeTypeCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Mime Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createMimeTypeDescription(UserVisitPK userVisitPK, CreateMimeTypeDescriptionForm form) {
        return new CreateMimeTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getMimeTypeDescriptions(UserVisitPK userVisitPK, GetMimeTypeDescriptionsForm form) {
        return new GetMimeTypeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getMimeTypeDescription(UserVisitPK userVisitPK, GetMimeTypeDescriptionForm form) {
        return new GetMimeTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editMimeTypeDescription(UserVisitPK userVisitPK, EditMimeTypeDescriptionForm form) {
        return new EditMimeTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteMimeTypeDescription(UserVisitPK userVisitPK, DeleteMimeTypeDescriptionForm form) {
        return new DeleteMimeTypeDescriptionCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Mime Type Usages
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createMimeTypeUsage(UserVisitPK userVisitPK, CreateMimeTypeUsageForm form) {
        return new CreateMimeTypeUsageCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getMimeTypeUsages(UserVisitPK userVisitPK, GetMimeTypeUsagesForm form) {
        return new GetMimeTypeUsagesCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Mime Type File Extensions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createMimeTypeFileExtension(UserVisitPK userVisitPK, CreateMimeTypeFileExtensionForm form) {
        return new CreateMimeTypeFileExtensionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getMimeTypeFileExtension(UserVisitPK userVisitPK, GetMimeTypeFileExtensionForm form) {
        return new GetMimeTypeFileExtensionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getMimeTypeFileExtensions(UserVisitPK userVisitPK, GetMimeTypeFileExtensionsForm form) {
        return new GetMimeTypeFileExtensionsCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Protocols
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createProtocol(UserVisitPK userVisitPK, CreateProtocolForm form) {
        return new CreateProtocolCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getProtocolChoices(UserVisitPK userVisitPK, GetProtocolChoicesForm form) {
        return new GetProtocolChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getProtocol(UserVisitPK userVisitPK, GetProtocolForm form) {
        return new GetProtocolCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getProtocols(UserVisitPK userVisitPK, GetProtocolsForm form) {
        return new GetProtocolsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultProtocol(UserVisitPK userVisitPK, SetDefaultProtocolForm form) {
        return new SetDefaultProtocolCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editProtocol(UserVisitPK userVisitPK, EditProtocolForm form) {
        return new EditProtocolCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteProtocol(UserVisitPK userVisitPK, DeleteProtocolForm form) {
        return new DeleteProtocolCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Protocol Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createProtocolDescription(UserVisitPK userVisitPK, CreateProtocolDescriptionForm form) {
        return new CreateProtocolDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getProtocolDescription(UserVisitPK userVisitPK, GetProtocolDescriptionForm form) {
        return new GetProtocolDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getProtocolDescriptions(UserVisitPK userVisitPK, GetProtocolDescriptionsForm form) {
        return new GetProtocolDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editProtocolDescription(UserVisitPK userVisitPK, EditProtocolDescriptionForm form) {
        return new EditProtocolDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteProtocolDescription(UserVisitPK userVisitPK, DeleteProtocolDescriptionForm form) {
        return new DeleteProtocolDescriptionCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Services
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createService(UserVisitPK userVisitPK, CreateServiceForm form) {
        return new CreateServiceCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getServiceChoices(UserVisitPK userVisitPK, GetServiceChoicesForm form) {
        return new GetServiceChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getService(UserVisitPK userVisitPK, GetServiceForm form) {
        return new GetServiceCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getServices(UserVisitPK userVisitPK, GetServicesForm form) {
        return new GetServicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultService(UserVisitPK userVisitPK, SetDefaultServiceForm form) {
        return new SetDefaultServiceCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editService(UserVisitPK userVisitPK, EditServiceForm form) {
        return new EditServiceCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteService(UserVisitPK userVisitPK, DeleteServiceForm form) {
        return new DeleteServiceCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Service Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createServiceDescription(UserVisitPK userVisitPK, CreateServiceDescriptionForm form) {
        return new CreateServiceDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getServiceDescription(UserVisitPK userVisitPK, GetServiceDescriptionForm form) {
        return new GetServiceDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getServiceDescriptions(UserVisitPK userVisitPK, GetServiceDescriptionsForm form) {
        return new GetServiceDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editServiceDescription(UserVisitPK userVisitPK, EditServiceDescriptionForm form) {
        return new EditServiceDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteServiceDescription(UserVisitPK userVisitPK, DeleteServiceDescriptionForm form) {
        return new DeleteServiceDescriptionCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Servers
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createServer(UserVisitPK userVisitPK, CreateServerForm form) {
        return new CreateServerCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getServerChoices(UserVisitPK userVisitPK, GetServerChoicesForm form) {
        return new GetServerChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getServer(UserVisitPK userVisitPK, GetServerForm form) {
        return new GetServerCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getServers(UserVisitPK userVisitPK, GetServersForm form) {
        return new GetServersCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultServer(UserVisitPK userVisitPK, SetDefaultServerForm form) {
        return new SetDefaultServerCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editServer(UserVisitPK userVisitPK, EditServerForm form) {
        return new EditServerCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteServer(UserVisitPK userVisitPK, DeleteServerForm form) {
        return new DeleteServerCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Server Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createServerDescription(UserVisitPK userVisitPK, CreateServerDescriptionForm form) {
        return new CreateServerDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getServerDescription(UserVisitPK userVisitPK, GetServerDescriptionForm form) {
        return new GetServerDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getServerDescriptions(UserVisitPK userVisitPK, GetServerDescriptionsForm form) {
        return new GetServerDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editServerDescription(UserVisitPK userVisitPK, EditServerDescriptionForm form) {
        return new EditServerDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteServerDescription(UserVisitPK userVisitPK, DeleteServerDescriptionForm form) {
        return new DeleteServerDescriptionCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Server Services
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createServerService(UserVisitPK userVisitPK, CreateServerServiceForm form) {
        return new CreateServerServiceCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getServerService(UserVisitPK userVisitPK, GetServerServiceForm form) {
        return new GetServerServiceCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getServerServices(UserVisitPK userVisitPK, GetServerServicesForm form) {
        return new GetServerServicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteServerService(UserVisitPK userVisitPK, DeleteServerServiceForm form) {
        return new DeleteServerServiceCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Entity Boolean Defaults
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createEntityBooleanDefault(UserVisitPK userVisitPK, CreateEntityBooleanDefaultForm form) {
        return new CreateEntityBooleanDefaultCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editEntityBooleanDefault(UserVisitPK userVisitPK, EditEntityBooleanDefaultForm form) {
        return new EditEntityBooleanDefaultCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteEntityBooleanDefault(UserVisitPK userVisitPK, DeleteEntityBooleanDefaultForm form) {
        return new DeleteEntityBooleanDefaultCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Entity Boolean Attributes
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createEntityBooleanAttribute(UserVisitPK userVisitPK, CreateEntityBooleanAttributeForm form) {
        return new CreateEntityBooleanAttributeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editEntityBooleanAttribute(UserVisitPK userVisitPK, EditEntityBooleanAttributeForm form) {
        return new EditEntityBooleanAttributeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteEntityBooleanAttribute(UserVisitPK userVisitPK, DeleteEntityBooleanAttributeForm form) {
        return new DeleteEntityBooleanAttributeCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Entity Integer Defaults
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createEntityIntegerDefault(UserVisitPK userVisitPK, CreateEntityIntegerDefaultForm form) {
        return new CreateEntityIntegerDefaultCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editEntityIntegerDefault(UserVisitPK userVisitPK, EditEntityIntegerDefaultForm form) {
        return new EditEntityIntegerDefaultCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteEntityIntegerDefault(UserVisitPK userVisitPK, DeleteEntityIntegerDefaultForm form) {
        return new DeleteEntityIntegerDefaultCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Entity Integer Attributes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityIntegerAttribute(UserVisitPK userVisitPK, CreateEntityIntegerAttributeForm form) {
        return new CreateEntityIntegerAttributeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editEntityIntegerAttribute(UserVisitPK userVisitPK, EditEntityIntegerAttributeForm form) {
        return new EditEntityIntegerAttributeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteEntityIntegerAttribute(UserVisitPK userVisitPK, DeleteEntityIntegerAttributeForm form) {
        return new DeleteEntityIntegerAttributeCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Entity List Item Defaults
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createEntityListItemDefault(UserVisitPK userVisitPK, CreateEntityListItemDefaultForm form) {
        return new CreateEntityListItemDefaultCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editEntityListItemDefault(UserVisitPK userVisitPK, EditEntityListItemDefaultForm form) {
        return new EditEntityListItemDefaultCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteEntityListItemDefault(UserVisitPK userVisitPK, DeleteEntityListItemDefaultForm form) {
        return new DeleteEntityListItemDefaultCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Entity List Item Attributes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityListItemAttribute(UserVisitPK userVisitPK, CreateEntityListItemAttributeForm form) {
        return new CreateEntityListItemAttributeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editEntityListItemAttribute(UserVisitPK userVisitPK, EditEntityListItemAttributeForm form) {
        return new EditEntityListItemAttributeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteEntityListItemAttribute(UserVisitPK userVisitPK, DeleteEntityListItemAttributeForm form) {
        return new DeleteEntityListItemAttributeCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Entity Long Defaults
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createEntityLongDefault(UserVisitPK userVisitPK, CreateEntityLongDefaultForm form) {
        return new CreateEntityLongDefaultCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editEntityLongDefault(UserVisitPK userVisitPK, EditEntityLongDefaultForm form) {
        return new EditEntityLongDefaultCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteEntityLongDefault(UserVisitPK userVisitPK, DeleteEntityLongDefaultForm form) {
        return new DeleteEntityLongDefaultCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Entity Long Attributes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityLongAttribute(UserVisitPK userVisitPK, CreateEntityLongAttributeForm form) {
        return new CreateEntityLongAttributeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editEntityLongAttribute(UserVisitPK userVisitPK, EditEntityLongAttributeForm form) {
        return new EditEntityLongAttributeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteEntityLongAttribute(UserVisitPK userVisitPK, DeleteEntityLongAttributeForm form) {
        return new DeleteEntityLongAttributeCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Entity Multiple List Item Defaults
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createEntityMultipleListItemDefault(UserVisitPK userVisitPK, CreateEntityMultipleListItemDefaultForm form) {
        return new CreateEntityMultipleListItemDefaultCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteEntityMultipleListItemDefault(UserVisitPK userVisitPK, DeleteEntityMultipleListItemDefaultForm form) {
        return new DeleteEntityMultipleListItemDefaultCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Entity Multiple List Item Attributes
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createEntityMultipleListItemAttribute(UserVisitPK userVisitPK, CreateEntityMultipleListItemAttributeForm form) {
        return new CreateEntityMultipleListItemAttributeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteEntityMultipleListItemAttribute(UserVisitPK userVisitPK, DeleteEntityMultipleListItemAttributeForm form) {
        return new DeleteEntityMultipleListItemAttributeCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Entity Name Attributes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityNameAttribute(UserVisitPK userVisitPK, CreateEntityNameAttributeForm form) {
        return new CreateEntityNameAttributeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editEntityNameAttribute(UserVisitPK userVisitPK, EditEntityNameAttributeForm form) {
        return new EditEntityNameAttributeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteEntityNameAttribute(UserVisitPK userVisitPK, DeleteEntityNameAttributeForm form) {
        return new DeleteEntityNameAttributeCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Entity String Defaults
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createEntityStringDefault(UserVisitPK userVisitPK, CreateEntityStringDefaultForm form) {
        return new CreateEntityStringDefaultCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editEntityStringDefault(UserVisitPK userVisitPK, EditEntityStringDefaultForm form) {
        return new EditEntityStringDefaultCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteEntityStringDefault(UserVisitPK userVisitPK, DeleteEntityStringDefaultForm form) {
        return new DeleteEntityStringDefaultCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Entity String Attributes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityStringAttribute(UserVisitPK userVisitPK, CreateEntityStringAttributeForm form) {
        return new CreateEntityStringAttributeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editEntityStringAttribute(UserVisitPK userVisitPK, EditEntityStringAttributeForm form) {
        return new EditEntityStringAttributeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteEntityStringAttribute(UserVisitPK userVisitPK, DeleteEntityStringAttributeForm form) {
        return new DeleteEntityStringAttributeCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Entity Geo Point Defaults
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createEntityGeoPointDefault(UserVisitPK userVisitPK, CreateEntityGeoPointDefaultForm form) {
        return new CreateEntityGeoPointDefaultCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editEntityGeoPointDefault(UserVisitPK userVisitPK, EditEntityGeoPointDefaultForm form) {
        return new EditEntityGeoPointDefaultCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteEntityGeoPointDefault(UserVisitPK userVisitPK, DeleteEntityGeoPointDefaultForm form) {
        return new DeleteEntityGeoPointDefaultCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Entity Geo Point Attributes
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createEntityGeoPointAttribute(UserVisitPK userVisitPK, CreateEntityGeoPointAttributeForm form) {
        return new CreateEntityGeoPointAttributeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editEntityGeoPointAttribute(UserVisitPK userVisitPK, EditEntityGeoPointAttributeForm form) {
        return new EditEntityGeoPointAttributeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteEntityGeoPointAttribute(UserVisitPK userVisitPK, DeleteEntityGeoPointAttributeForm form) {
        return new DeleteEntityGeoPointAttributeCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Entity Blob Attributes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityBlobAttribute(UserVisitPK userVisitPK, CreateEntityBlobAttributeForm form) {
        return new CreateEntityBlobAttributeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editEntityBlobAttribute(UserVisitPK userVisitPK, EditEntityBlobAttributeForm form) {
        return new EditEntityBlobAttributeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getEntityBlobAttribute(UserVisitPK userVisitPK, GetEntityBlobAttributeForm form) {
        return new GetEntityBlobAttributeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteEntityBlobAttribute(UserVisitPK userVisitPK, DeleteEntityBlobAttributeForm form) {
        return new DeleteEntityBlobAttributeCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Clob Attributes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityClobAttribute(UserVisitPK userVisitPK, CreateEntityClobAttributeForm form) {
        return new CreateEntityClobAttributeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editEntityClobAttribute(UserVisitPK userVisitPK, EditEntityClobAttributeForm form) {
        return new EditEntityClobAttributeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getEntityClobAttribute(UserVisitPK userVisitPK, GetEntityClobAttributeForm form) {
        return new GetEntityClobAttributeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteEntityClobAttribute(UserVisitPK userVisitPK, DeleteEntityClobAttributeForm form) {
        return new DeleteEntityClobAttributeCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Entity Time Defaults
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createEntityTimeDefault(UserVisitPK userVisitPK, CreateEntityTimeDefaultForm form) {
        return new CreateEntityTimeDefaultCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editEntityTimeDefault(UserVisitPK userVisitPK, EditEntityTimeDefaultForm form) {
        return new EditEntityTimeDefaultCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteEntityTimeDefault(UserVisitPK userVisitPK, DeleteEntityTimeDefaultForm form) {
        return new DeleteEntityTimeDefaultCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Entity Time Attributes
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createEntityTimeAttribute(UserVisitPK userVisitPK, CreateEntityTimeAttributeForm form) {
        return new CreateEntityTimeAttributeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editEntityTimeAttribute(UserVisitPK userVisitPK, EditEntityTimeAttributeForm form) {
        return new EditEntityTimeAttributeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteEntityTimeAttribute(UserVisitPK userVisitPK, DeleteEntityTimeAttributeForm form) {
        return new DeleteEntityTimeAttributeCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Entity Date Defaults
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createEntityDateDefault(UserVisitPK userVisitPK, CreateEntityDateDefaultForm form) {
        return new CreateEntityDateDefaultCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editEntityDateDefault(UserVisitPK userVisitPK, EditEntityDateDefaultForm form) {
        return new EditEntityDateDefaultCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteEntityDateDefault(UserVisitPK userVisitPK, DeleteEntityDateDefaultForm form) {
        return new DeleteEntityDateDefaultCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Entity Date Attributes
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createEntityDateAttribute(UserVisitPK userVisitPK, CreateEntityDateAttributeForm form) {
        return new CreateEntityDateAttributeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editEntityDateAttribute(UserVisitPK userVisitPK, EditEntityDateAttributeForm form) {
        return new EditEntityDateAttributeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteEntityDateAttribute(UserVisitPK userVisitPK, DeleteEntityDateAttributeForm form) {
        return new DeleteEntityDateAttributeCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Entity Attribute Entity Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityAttributeEntityType(UserVisitPK userVisitPK, CreateEntityAttributeEntityTypeForm form) {
        return new CreateEntityAttributeEntityTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityAttributeEntityType(UserVisitPK userVisitPK, GetEntityAttributeEntityTypeForm form) {
        return new GetEntityAttributeEntityTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getEntityAttributeEntityTypes(UserVisitPK userVisitPK, GetEntityAttributeEntityTypesForm form) {
        return new GetEntityAttributeEntityTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteEntityAttributeEntityType(UserVisitPK userVisitPK, DeleteEntityAttributeEntityTypeForm form) {
        return new DeleteEntityAttributeEntityTypeCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Entity Entity Attributes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityEntityAttribute(UserVisitPK userVisitPK, CreateEntityEntityAttributeForm form) {
        return new CreateEntityEntityAttributeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editEntityEntityAttribute(UserVisitPK userVisitPK, EditEntityEntityAttributeForm form) {
        return new EditEntityEntityAttributeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteEntityEntityAttribute(UserVisitPK userVisitPK, DeleteEntityEntityAttributeForm form) {
        return new DeleteEntityEntityAttributeCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Collection Attributes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityCollectionAttribute(UserVisitPK userVisitPK, CreateEntityCollectionAttributeForm form) {
        return new CreateEntityCollectionAttributeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteEntityCollectionAttribute(UserVisitPK userVisitPK, DeleteEntityCollectionAttributeForm form) {
        return new DeleteEntityCollectionAttributeCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Entity Workflow Attributes
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createEntityWorkflowAttribute(UserVisitPK userVisitPK, CreateEntityWorkflowAttributeForm form) {
        return new CreateEntityWorkflowAttributeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setEntityWorkflowAttributeStatus(UserVisitPK userVisitPK, SetEntityWorkflowAttributeStatusForm form) {
        return new SetEntityWorkflowAttributeStatusCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteEntityWorkflowAttribute(UserVisitPK userVisitPK, DeleteEntityWorkflowAttributeForm form) {
        return new DeleteEntityWorkflowAttributeCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Cache Entries
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createCacheEntry(UserVisitPK userVisitPK, CreateCacheEntryForm form) {
        return new CreateCacheEntryCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getCacheEntry(UserVisitPK userVisitPK, GetCacheEntryForm form) {
        return new GetCacheEntryCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getCacheEntries(UserVisitPK userVisitPK, GetCacheEntriesForm form) {
        return new GetCacheEntriesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult removeCacheEntry(UserVisitPK userVisitPK, RemoveCacheEntryForm form) {
        return new RemoveCacheEntryCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Cache Entry Dependencies
    // -------------------------------------------------------------------------

    @Override
    public CommandResult getCacheEntryDependencies(UserVisitPK userVisitPK, GetCacheEntryDependenciesForm form) {
        return new GetCacheEntryDependenciesCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Applications
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createApplication(UserVisitPK userVisitPK, CreateApplicationForm form) {
        return new CreateApplicationCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getApplicationChoices(UserVisitPK userVisitPK, GetApplicationChoicesForm form) {
        return new GetApplicationChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getApplication(UserVisitPK userVisitPK, GetApplicationForm form) {
        return new GetApplicationCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getApplications(UserVisitPK userVisitPK, GetApplicationsForm form) {
        return new GetApplicationsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultApplication(UserVisitPK userVisitPK, SetDefaultApplicationForm form) {
        return new SetDefaultApplicationCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editApplication(UserVisitPK userVisitPK, EditApplicationForm form) {
        return new EditApplicationCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteApplication(UserVisitPK userVisitPK, DeleteApplicationForm form) {
        return new DeleteApplicationCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Applications Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createApplicationDescription(UserVisitPK userVisitPK, CreateApplicationDescriptionForm form) {
        return new CreateApplicationDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getApplicationDescription(UserVisitPK userVisitPK, GetApplicationDescriptionForm form) {
        return new GetApplicationDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getApplicationDescriptions(UserVisitPK userVisitPK, GetApplicationDescriptionsForm form) {
        return new GetApplicationDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editApplicationDescription(UserVisitPK userVisitPK, EditApplicationDescriptionForm form) {
        return new EditApplicationDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteApplicationDescription(UserVisitPK userVisitPK, DeleteApplicationDescriptionForm form) {
        return new DeleteApplicationDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Editors
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEditor(UserVisitPK userVisitPK, CreateEditorForm form) {
        return new CreateEditorCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEditorChoices(UserVisitPK userVisitPK, GetEditorChoicesForm form) {
        return new GetEditorChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEditor(UserVisitPK userVisitPK, GetEditorForm form) {
        return new GetEditorCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEditors(UserVisitPK userVisitPK, GetEditorsForm form) {
        return new GetEditorsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultEditor(UserVisitPK userVisitPK, SetDefaultEditorForm form) {
        return new SetDefaultEditorCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editEditor(UserVisitPK userVisitPK, EditEditorForm form) {
        return new EditEditorCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteEditor(UserVisitPK userVisitPK, DeleteEditorForm form) {
        return new DeleteEditorCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Editors Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEditorDescription(UserVisitPK userVisitPK, CreateEditorDescriptionForm form) {
        return new CreateEditorDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEditorDescription(UserVisitPK userVisitPK, GetEditorDescriptionForm form) {
        return new GetEditorDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEditorDescriptions(UserVisitPK userVisitPK, GetEditorDescriptionsForm form) {
        return new GetEditorDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editEditorDescription(UserVisitPK userVisitPK, EditEditorDescriptionForm form) {
        return new EditEditorDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteEditorDescription(UserVisitPK userVisitPK, DeleteEditorDescriptionForm form) {
        return new DeleteEditorDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   ApplicationEditors
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createApplicationEditor(UserVisitPK userVisitPK, CreateApplicationEditorForm form) {
        return new CreateApplicationEditorCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getApplicationEditorChoices(UserVisitPK userVisitPK, GetApplicationEditorChoicesForm form) {
        return new GetApplicationEditorChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getApplicationEditor(UserVisitPK userVisitPK, GetApplicationEditorForm form) {
        return new GetApplicationEditorCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getApplicationEditors(UserVisitPK userVisitPK, GetApplicationEditorsForm form) {
        return new GetApplicationEditorsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultApplicationEditor(UserVisitPK userVisitPK, SetDefaultApplicationEditorForm form) {
        return new SetDefaultApplicationEditorCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editApplicationEditor(UserVisitPK userVisitPK, EditApplicationEditorForm form) {
        return new EditApplicationEditorCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteApplicationEditor(UserVisitPK userVisitPK, DeleteApplicationEditorForm form) {
        return new DeleteApplicationEditorCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Application Editor Uses
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createApplicationEditorUse(UserVisitPK userVisitPK, CreateApplicationEditorUseForm form) {
        return new CreateApplicationEditorUseCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getApplicationEditorUseChoices(UserVisitPK userVisitPK, GetApplicationEditorUseChoicesForm form) {
        return new GetApplicationEditorUseChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getApplicationEditorUse(UserVisitPK userVisitPK, GetApplicationEditorUseForm form) {
        return new GetApplicationEditorUseCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getApplicationEditorUses(UserVisitPK userVisitPK, GetApplicationEditorUsesForm form) {
        return new GetApplicationEditorUsesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultApplicationEditorUse(UserVisitPK userVisitPK, SetDefaultApplicationEditorUseForm form) {
        return new SetDefaultApplicationEditorUseCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editApplicationEditorUse(UserVisitPK userVisitPK, EditApplicationEditorUseForm form) {
        return new EditApplicationEditorUseCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteApplicationEditorUse(UserVisitPK userVisitPK, DeleteApplicationEditorUseForm form) {
        return new DeleteApplicationEditorUseCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Application Editor Use Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createApplicationEditorUseDescription(UserVisitPK userVisitPK, CreateApplicationEditorUseDescriptionForm form) {
        return new CreateApplicationEditorUseDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getApplicationEditorUseDescription(UserVisitPK userVisitPK, GetApplicationEditorUseDescriptionForm form) {
        return new GetApplicationEditorUseDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getApplicationEditorUseDescriptions(UserVisitPK userVisitPK, GetApplicationEditorUseDescriptionsForm form) {
        return new GetApplicationEditorUseDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editApplicationEditorUseDescription(UserVisitPK userVisitPK, EditApplicationEditorUseDescriptionForm form) {
        return new EditApplicationEditorUseDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteApplicationEditorUseDescription(UserVisitPK userVisitPK, DeleteApplicationEditorUseDescriptionForm form) {
        return new DeleteApplicationEditorUseDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Appearances
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createAppearance(UserVisitPK userVisitPK, CreateAppearanceForm form) {
        return new CreateAppearanceCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getAppearanceChoices(UserVisitPK userVisitPK, GetAppearanceChoicesForm form) {
        return new GetAppearanceChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getAppearance(UserVisitPK userVisitPK, GetAppearanceForm form) {
        return new GetAppearanceCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getAppearances(UserVisitPK userVisitPK, GetAppearancesForm form) {
        return new GetAppearancesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultAppearance(UserVisitPK userVisitPK, SetDefaultAppearanceForm form) {
        return new SetDefaultAppearanceCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editAppearance(UserVisitPK userVisitPK, EditAppearanceForm form) {
        return new EditAppearanceCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteAppearance(UserVisitPK userVisitPK, DeleteAppearanceForm form) {
        return new DeleteAppearanceCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Appearances Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createAppearanceDescription(UserVisitPK userVisitPK, CreateAppearanceDescriptionForm form) {
        return new CreateAppearanceDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getAppearanceDescription(UserVisitPK userVisitPK, GetAppearanceDescriptionForm form) {
        return new GetAppearanceDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getAppearanceDescriptions(UserVisitPK userVisitPK, GetAppearanceDescriptionsForm form) {
        return new GetAppearanceDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editAppearanceDescription(UserVisitPK userVisitPK, EditAppearanceDescriptionForm form) {
        return new EditAppearanceDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteAppearanceDescription(UserVisitPK userVisitPK, DeleteAppearanceDescriptionForm form) {
        return new DeleteAppearanceDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Appearances Text Decorations
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createAppearanceTextDecoration(UserVisitPK userVisitPK, CreateAppearanceTextDecorationForm form) {
        return new CreateAppearanceTextDecorationCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getAppearanceTextDecoration(UserVisitPK userVisitPK, GetAppearanceTextDecorationForm form) {
        return new GetAppearanceTextDecorationCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getAppearanceTextDecorations(UserVisitPK userVisitPK, GetAppearanceTextDecorationsForm form) {
        return new GetAppearanceTextDecorationsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteAppearanceTextDecoration(UserVisitPK userVisitPK, DeleteAppearanceTextDecorationForm form) {
        return new DeleteAppearanceTextDecorationCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Appearances Text Transformations
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createAppearanceTextTransformation(UserVisitPK userVisitPK, CreateAppearanceTextTransformationForm form) {
        return new CreateAppearanceTextTransformationCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getAppearanceTextTransformation(UserVisitPK userVisitPK, GetAppearanceTextTransformationForm form) {
        return new GetAppearanceTextTransformationCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getAppearanceTextTransformations(UserVisitPK userVisitPK, GetAppearanceTextTransformationsForm form) {
        return new GetAppearanceTextTransformationsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteAppearanceTextTransformation(UserVisitPK userVisitPK, DeleteAppearanceTextTransformationForm form) {
        return new DeleteAppearanceTextTransformationCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Colors
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createColor(UserVisitPK userVisitPK, CreateColorForm form) {
        return new CreateColorCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getColorChoices(UserVisitPK userVisitPK, GetColorChoicesForm form) {
        return new GetColorChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getColor(UserVisitPK userVisitPK, GetColorForm form) {
        return new GetColorCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getColors(UserVisitPK userVisitPK, GetColorsForm form) {
        return new GetColorsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultColor(UserVisitPK userVisitPK, SetDefaultColorForm form) {
        return new SetDefaultColorCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editColor(UserVisitPK userVisitPK, EditColorForm form) {
        return new EditColorCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteColor(UserVisitPK userVisitPK, DeleteColorForm form) {
        return new DeleteColorCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Color Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createColorDescription(UserVisitPK userVisitPK, CreateColorDescriptionForm form) {
        return new CreateColorDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getColorDescription(UserVisitPK userVisitPK, GetColorDescriptionForm form) {
        return new GetColorDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getColorDescriptions(UserVisitPK userVisitPK, GetColorDescriptionsForm form) {
        return new GetColorDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editColorDescription(UserVisitPK userVisitPK, EditColorDescriptionForm form) {
        return new EditColorDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteColorDescription(UserVisitPK userVisitPK, DeleteColorDescriptionForm form) {
        return new DeleteColorDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Font Styles
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createFontStyle(UserVisitPK userVisitPK, CreateFontStyleForm form) {
        return new CreateFontStyleCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getFontStyleChoices(UserVisitPK userVisitPK, GetFontStyleChoicesForm form) {
        return new GetFontStyleChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getFontStyle(UserVisitPK userVisitPK, GetFontStyleForm form) {
        return new GetFontStyleCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getFontStyles(UserVisitPK userVisitPK, GetFontStylesForm form) {
        return new GetFontStylesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultFontStyle(UserVisitPK userVisitPK, SetDefaultFontStyleForm form) {
        return new SetDefaultFontStyleCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editFontStyle(UserVisitPK userVisitPK, EditFontStyleForm form) {
        return new EditFontStyleCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteFontStyle(UserVisitPK userVisitPK, DeleteFontStyleForm form) {
        return new DeleteFontStyleCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Font Style Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createFontStyleDescription(UserVisitPK userVisitPK, CreateFontStyleDescriptionForm form) {
        return new CreateFontStyleDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getFontStyleDescription(UserVisitPK userVisitPK, GetFontStyleDescriptionForm form) {
        return new GetFontStyleDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getFontStyleDescriptions(UserVisitPK userVisitPK, GetFontStyleDescriptionsForm form) {
        return new GetFontStyleDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editFontStyleDescription(UserVisitPK userVisitPK, EditFontStyleDescriptionForm form) {
        return new EditFontStyleDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteFontStyleDescription(UserVisitPK userVisitPK, DeleteFontStyleDescriptionForm form) {
        return new DeleteFontStyleDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Font Weights
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createFontWeight(UserVisitPK userVisitPK, CreateFontWeightForm form) {
        return new CreateFontWeightCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getFontWeightChoices(UserVisitPK userVisitPK, GetFontWeightChoicesForm form) {
        return new GetFontWeightChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getFontWeight(UserVisitPK userVisitPK, GetFontWeightForm form) {
        return new GetFontWeightCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getFontWeights(UserVisitPK userVisitPK, GetFontWeightsForm form) {
        return new GetFontWeightsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultFontWeight(UserVisitPK userVisitPK, SetDefaultFontWeightForm form) {
        return new SetDefaultFontWeightCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editFontWeight(UserVisitPK userVisitPK, EditFontWeightForm form) {
        return new EditFontWeightCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteFontWeight(UserVisitPK userVisitPK, DeleteFontWeightForm form) {
        return new DeleteFontWeightCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Font Weight Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createFontWeightDescription(UserVisitPK userVisitPK, CreateFontWeightDescriptionForm form) {
        return new CreateFontWeightDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getFontWeightDescription(UserVisitPK userVisitPK, GetFontWeightDescriptionForm form) {
        return new GetFontWeightDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getFontWeightDescriptions(UserVisitPK userVisitPK, GetFontWeightDescriptionsForm form) {
        return new GetFontWeightDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editFontWeightDescription(UserVisitPK userVisitPK, EditFontWeightDescriptionForm form) {
        return new EditFontWeightDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteFontWeightDescription(UserVisitPK userVisitPK, DeleteFontWeightDescriptionForm form) {
        return new DeleteFontWeightDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Text Decorations
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createTextDecoration(UserVisitPK userVisitPK, CreateTextDecorationForm form) {
        return new CreateTextDecorationCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTextDecorationChoices(UserVisitPK userVisitPK, GetTextDecorationChoicesForm form) {
        return new GetTextDecorationChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTextDecoration(UserVisitPK userVisitPK, GetTextDecorationForm form) {
        return new GetTextDecorationCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTextDecorations(UserVisitPK userVisitPK, GetTextDecorationsForm form) {
        return new GetTextDecorationsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultTextDecoration(UserVisitPK userVisitPK, SetDefaultTextDecorationForm form) {
        return new SetDefaultTextDecorationCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editTextDecoration(UserVisitPK userVisitPK, EditTextDecorationForm form) {
        return new EditTextDecorationCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTextDecoration(UserVisitPK userVisitPK, DeleteTextDecorationForm form) {
        return new DeleteTextDecorationCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Text Decoration Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createTextDecorationDescription(UserVisitPK userVisitPK, CreateTextDecorationDescriptionForm form) {
        return new CreateTextDecorationDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTextDecorationDescription(UserVisitPK userVisitPK, GetTextDecorationDescriptionForm form) {
        return new GetTextDecorationDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTextDecorationDescriptions(UserVisitPK userVisitPK, GetTextDecorationDescriptionsForm form) {
        return new GetTextDecorationDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editTextDecorationDescription(UserVisitPK userVisitPK, EditTextDecorationDescriptionForm form) {
        return new EditTextDecorationDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTextDecorationDescription(UserVisitPK userVisitPK, DeleteTextDecorationDescriptionForm form) {
        return new DeleteTextDecorationDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Text Transformations
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createTextTransformation(UserVisitPK userVisitPK, CreateTextTransformationForm form) {
        return new CreateTextTransformationCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTextTransformationChoices(UserVisitPK userVisitPK, GetTextTransformationChoicesForm form) {
        return new GetTextTransformationChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTextTransformation(UserVisitPK userVisitPK, GetTextTransformationForm form) {
        return new GetTextTransformationCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTextTransformations(UserVisitPK userVisitPK, GetTextTransformationsForm form) {
        return new GetTextTransformationsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultTextTransformation(UserVisitPK userVisitPK, SetDefaultTextTransformationForm form) {
        return new SetDefaultTextTransformationCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editTextTransformation(UserVisitPK userVisitPK, EditTextTransformationForm form) {
        return new EditTextTransformationCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTextTransformation(UserVisitPK userVisitPK, DeleteTextTransformationForm form) {
        return new DeleteTextTransformationCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Text Transformation Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createTextTransformationDescription(UserVisitPK userVisitPK, CreateTextTransformationDescriptionForm form) {
        return new CreateTextTransformationDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTextTransformationDescription(UserVisitPK userVisitPK, GetTextTransformationDescriptionForm form) {
        return new GetTextTransformationDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTextTransformationDescriptions(UserVisitPK userVisitPK, GetTextTransformationDescriptionsForm form) {
        return new GetTextTransformationDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editTextTransformationDescription(UserVisitPK userVisitPK, EditTextTransformationDescriptionForm form) {
        return new EditTextTransformationDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTextTransformationDescription(UserVisitPK userVisitPK, DeleteTextTransformationDescriptionForm form) {
        return new DeleteTextTransformationDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Appearances
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityAppearance(UserVisitPK userVisitPK, CreateEntityAppearanceForm form) {
        return new CreateEntityAppearanceCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityAppearance(UserVisitPK userVisitPK, GetEntityAppearanceForm form) {
        return new GetEntityAppearanceCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editEntityAppearance(UserVisitPK userVisitPK, EditEntityAppearanceForm form) {
        return new EditEntityAppearanceCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteEntityAppearance(UserVisitPK userVisitPK, DeleteEntityAppearanceForm form) {
        return new DeleteEntityAppearanceCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Utilities
    // -------------------------------------------------------------------------

    @Override
    public CommandResult encrypt(UserVisitPK userVisitPK, EncryptForm form) {
        return new EncryptCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult decrypt(UserVisitPK userVisitPK, DecryptForm form) {
        return new DecryptCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult validate(UserVisitPK userVisitPK, ValidateForm form) {
        return new ValidateCommand().run(userVisitPK, form);
    }

}
