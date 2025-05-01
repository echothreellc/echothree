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
        return new GenerateBaseKeysCommand(userVisitPK).run();
    }
    
    @Override
    public CommandResult loadBaseKeys(UserVisitPK userVisitPK, LoadBaseKeysForm form) {
        return new LoadBaseKeysCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult changeBaseKeys(UserVisitPK userVisitPK, ChangeBaseKeysForm form) {
        return new ChangeBaseKeysCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Base Encryption Keys
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult getBaseEncryptionKey(UserVisitPK userVisitPK, GetBaseEncryptionKeyForm form) {
        return new GetBaseEncryptionKeyCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getBaseEncryptionKeys(UserVisitPK userVisitPK, GetBaseEncryptionKeysForm form) {
        return new GetBaseEncryptionKeysCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getBaseEncryptionKeyStatusChoices(UserVisitPK userVisitPK, GetBaseEncryptionKeyStatusChoicesForm form) {
        return new GetBaseEncryptionKeyStatusChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setBaseEncryptionKeyStatus(UserVisitPK userVisitPK, SetBaseEncryptionKeyStatusForm form) {
        return new SetBaseEncryptionKeyStatusCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Entity Locks
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult lockEntity(UserVisitPK userVisitPK, LockEntityForm form) {
        return new LockEntityCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult unlockEntity(UserVisitPK userVisitPK, UnlockEntityForm form) {
        return new UnlockEntityCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult removedExpiredEntityLocks(UserVisitPK userVisitPK) {
        return new RemovedExpiredEntityLocksCommand(userVisitPK).run();
    }
    
    // -------------------------------------------------------------------------
    //   Component Vendors
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createComponentVendor(UserVisitPK userVisitPK, CreateComponentVendorForm form) {
        return new CreateComponentVendorCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getComponentVendor(UserVisitPK userVisitPK, GetComponentVendorForm form) {
        return new GetComponentVendorCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getComponentVendors(UserVisitPK userVisitPK, GetComponentVendorsForm form) {
        return new GetComponentVendorsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editComponentVendor(UserVisitPK userVisitPK, EditComponentVendorForm form) {
        return new EditComponentVendorCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteComponentVendor(UserVisitPK userVisitPK, DeleteComponentVendorForm form) {
        return new DeleteComponentVendorCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Entity Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityType(UserVisitPK userVisitPK, CreateEntityTypeForm form) {
        return new CreateEntityTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityType(UserVisitPK userVisitPK, GetEntityTypeForm form) {
        return new GetEntityTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityTypes(UserVisitPK userVisitPK, GetEntityTypesForm form) {
        return new GetEntityTypesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editEntityType(UserVisitPK userVisitPK, EditEntityTypeForm form) {
        return new EditEntityTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteEntityType(UserVisitPK userVisitPK, DeleteEntityTypeForm form) {
        return new DeleteEntityTypeCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Commands
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCommand(UserVisitPK userVisitPK, CreateCommandForm form) {
        return new CreateCommandCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCommand(UserVisitPK userVisitPK, GetCommandForm form) {
        return new GetCommandCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCommands(UserVisitPK userVisitPK, GetCommandsForm form) {
        return new GetCommandsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editCommand(UserVisitPK userVisitPK, EditCommandForm form) {
        return new EditCommandCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteCommand(UserVisitPK userVisitPK, DeleteCommandForm form) {
        return new DeleteCommandCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Command Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCommandDescription(UserVisitPK userVisitPK, CreateCommandDescriptionForm form) {
        return new CreateCommandDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCommandDescription(UserVisitPK userVisitPK, GetCommandDescriptionForm form) {
        return new GetCommandDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCommandDescriptions(UserVisitPK userVisitPK, GetCommandDescriptionsForm form) {
        return new GetCommandDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editCommandDescription(UserVisitPK userVisitPK, EditCommandDescriptionForm form) {
        return new EditCommandDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteCommandDescription(UserVisitPK userVisitPK, DeleteCommandDescriptionForm form) {
        return new DeleteCommandDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Command Message Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createCommandMessageType(UserVisitPK userVisitPK, CreateCommandMessageTypeForm form) {
        return new CreateCommandMessageTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCommandMessageTypeChoices(UserVisitPK userVisitPK, GetCommandMessageTypeChoicesForm form) {
        return new GetCommandMessageTypeChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCommandMessageType(UserVisitPK userVisitPK, GetCommandMessageTypeForm form) {
        return new GetCommandMessageTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCommandMessageTypes(UserVisitPK userVisitPK, GetCommandMessageTypesForm form) {
        return new GetCommandMessageTypesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultCommandMessageType(UserVisitPK userVisitPK, SetDefaultCommandMessageTypeForm form) {
        return new SetDefaultCommandMessageTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editCommandMessageType(UserVisitPK userVisitPK, EditCommandMessageTypeForm form) {
        return new EditCommandMessageTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteCommandMessageType(UserVisitPK userVisitPK, DeleteCommandMessageTypeForm form) {
        return new DeleteCommandMessageTypeCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Command Message Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createCommandMessageTypeDescription(UserVisitPK userVisitPK, CreateCommandMessageTypeDescriptionForm form) {
        return new CreateCommandMessageTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCommandMessageTypeDescription(UserVisitPK userVisitPK, GetCommandMessageTypeDescriptionForm form) {
        return new GetCommandMessageTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCommandMessageTypeDescriptions(UserVisitPK userVisitPK, GetCommandMessageTypeDescriptionsForm form) {
        return new GetCommandMessageTypeDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editCommandMessageTypeDescription(UserVisitPK userVisitPK, EditCommandMessageTypeDescriptionForm form) {
        return new EditCommandMessageTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteCommandMessageTypeDescription(UserVisitPK userVisitPK, DeleteCommandMessageTypeDescriptionForm form) {
        return new DeleteCommandMessageTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Command Messages
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCommandMessage(UserVisitPK userVisitPK, CreateCommandMessageForm form) {
        return new CreateCommandMessageCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getCommandMessage(UserVisitPK userVisitPK, GetCommandMessageForm form) {
        return new GetCommandMessageCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getCommandMessages(UserVisitPK userVisitPK, GetCommandMessagesForm form) {
        return new GetCommandMessagesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editCommandMessage(UserVisitPK userVisitPK, EditCommandMessageForm form) {
        return new EditCommandMessageCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteCommandMessage(UserVisitPK userVisitPK, DeleteCommandMessageForm form) {
        return new DeleteCommandMessageCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Command Message Translations
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createCommandMessageTranslation(UserVisitPK userVisitPK, CreateCommandMessageTranslationForm form) {
        return new CreateCommandMessageTranslationCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getCommandMessageTranslation(UserVisitPK userVisitPK, GetCommandMessageTranslationForm form) {
        return new GetCommandMessageTranslationCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getCommandMessageTranslations(UserVisitPK userVisitPK, GetCommandMessageTranslationsForm form) {
        return new GetCommandMessageTranslationsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editCommandMessageTranslation(UserVisitPK userVisitPK, EditCommandMessageTranslationForm form) {
        return new EditCommandMessageTranslationCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteCommandMessageTranslation(UserVisitPK userVisitPK, DeleteCommandMessageTranslationForm form) {
        return new DeleteCommandMessageTranslationCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Entity Instances
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createEntityInstance(UserVisitPK userVisitPK, CreateEntityInstanceForm form) {
        return new CreateEntityInstanceCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getEntityInstance(UserVisitPK userVisitPK, GetEntityInstanceForm form) {
        return new GetEntityInstanceCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getEntityInstances(UserVisitPK userVisitPK, GetEntityInstancesForm form) {
        return new GetEntityInstancesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteEntityInstance(UserVisitPK userVisitPK, DeleteEntityInstanceForm form) {
        return new DeleteEntityInstanceCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult removeEntityInstance(UserVisitPK userVisitPK, RemoveEntityInstanceForm form) {
        return new RemoveEntityInstanceCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult generateUuid(UserVisitPK userVisitPK, GenerateUuidForm form) {
        return new GenerateUuidCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Event Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEventType(UserVisitPK userVisitPK, CreateEventTypeForm form) {
        return new CreateEventTypeCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Event Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEventTypeDescription(UserVisitPK userVisitPK, CreateEventTypeDescriptionForm form) {
        return new CreateEventTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Event Groups
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult getEventGroup(UserVisitPK userVisitPK, GetEventGroupForm form) {
        return new GetEventGroupCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEventGroups(UserVisitPK userVisitPK, GetEventGroupsForm form) {
        return new GetEventGroupsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEventGroupStatusChoices(UserVisitPK userVisitPK, GetEventGroupStatusChoicesForm form) {
        return new GetEventGroupStatusChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setEventGroupStatus(UserVisitPK userVisitPK, SetEventGroupStatusForm form) {
        return new SetEventGroupStatusCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Events
    // -------------------------------------------------------------------------

    @Override
    public CommandResult sendEvent(UserVisitPK userVisitPK, SendEventForm form) {
        return new SendEventCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getEvents(UserVisitPK userVisitPK, GetEventsForm form) {
        return new GetEventsCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Queued Events
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult processQueuedEvents(UserVisitPK userVisitPK) {
        return new ProcessQueuedEventsCommand(userVisitPK).run();
    }
    
    // -------------------------------------------------------------------------
    //   Components
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createComponent(UserVisitPK userVisitPK, CreateComponentForm form) {
        return new CreateComponentCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Component Stages
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createComponentStage(UserVisitPK userVisitPK, CreateComponentStageForm form) {
        return new CreateComponentStageCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Component Versions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createComponentVersion(UserVisitPK userVisitPK, CreateComponentVersionForm form) {
        return new CreateComponentVersionCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Entity Alias Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createEntityAliasType(UserVisitPK userVisitPK, CreateEntityAliasTypeForm form) {
        return new CreateEntityAliasTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getEntityAliasType(UserVisitPK userVisitPK, GetEntityAliasTypeForm form) {
        return new GetEntityAliasTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getEntityAliasTypes(UserVisitPK userVisitPK, GetEntityAliasTypesForm form) {
        return new GetEntityAliasTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getEntityAliasTypeChoices(UserVisitPK userVisitPK, GetEntityAliasTypeChoicesForm form) {
        return new GetEntityAliasTypeChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultEntityAliasType(UserVisitPK userVisitPK, SetDefaultEntityAliasTypeForm form) {
        return new SetDefaultEntityAliasTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editEntityAliasType(UserVisitPK userVisitPK, EditEntityAliasTypeForm form) {
        return new EditEntityAliasTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteEntityAliasType(UserVisitPK userVisitPK, DeleteEntityAliasTypeForm form) {
        return new DeleteEntityAliasTypeCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Entity Alias Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createEntityAliasTypeDescription(UserVisitPK userVisitPK, CreateEntityAliasTypeDescriptionForm form) {
        return new CreateEntityAliasTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getEntityAliasTypeDescription(UserVisitPK userVisitPK, GetEntityAliasTypeDescriptionForm form) {
        return new GetEntityAliasTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getEntityAliasTypeDescriptions(UserVisitPK userVisitPK, GetEntityAliasTypeDescriptionsForm form) {
        return new GetEntityAliasTypeDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editEntityAliasTypeDescription(UserVisitPK userVisitPK, EditEntityAliasTypeDescriptionForm form) {
        return new EditEntityAliasTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteEntityAliasTypeDescription(UserVisitPK userVisitPK, DeleteEntityAliasTypeDescriptionForm form) {
        return new DeleteEntityAliasTypeDescriptionCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Entity Aliases
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createEntityAlias(UserVisitPK userVisitPK, CreateEntityAliasForm form) {
        return new CreateEntityAliasCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getEntityAlias(UserVisitPK userVisitPK, GetEntityAliasForm form) {
        return new GetEntityAliasCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getEntityAliases(UserVisitPK userVisitPK, GetEntityAliasesForm form) {
        return new GetEntityAliasesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editEntityAlias(UserVisitPK userVisitPK, EditEntityAliasForm form) {
        return new EditEntityAliasCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteEntityAlias(UserVisitPK userVisitPK, DeleteEntityAliasForm form) {
        return new DeleteEntityAliasCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Entity Attribute Group Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityAttributeGroupDescription(UserVisitPK userVisitPK, CreateEntityAttributeGroupDescriptionForm form) {
        return new CreateEntityAttributeGroupDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityAttributeGroupDescription(UserVisitPK userVisitPK, GetEntityAttributeGroupDescriptionForm form) {
        return new GetEntityAttributeGroupDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityAttributeGroupDescriptions(UserVisitPK userVisitPK, GetEntityAttributeGroupDescriptionsForm form) {
        return new GetEntityAttributeGroupDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editEntityAttributeGroupDescription(UserVisitPK userVisitPK, EditEntityAttributeGroupDescriptionForm form) {
        return new EditEntityAttributeGroupDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteEntityAttributeGroupDescription(UserVisitPK userVisitPK, DeleteEntityAttributeGroupDescriptionForm form) {
        return new DeleteEntityAttributeGroupDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Entity Attribute Groups
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityAttributeGroup(UserVisitPK userVisitPK, CreateEntityAttributeGroupForm form) {
        return new CreateEntityAttributeGroupCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityAttributeGroup(UserVisitPK userVisitPK, GetEntityAttributeGroupForm form) {
        return new GetEntityAttributeGroupCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityAttributeGroups(UserVisitPK userVisitPK, GetEntityAttributeGroupsForm form) {
        return new GetEntityAttributeGroupsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityAttributeGroupChoices(UserVisitPK userVisitPK, GetEntityAttributeGroupChoicesForm form) {
        return new GetEntityAttributeGroupChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultEntityAttributeGroup(UserVisitPK userVisitPK, SetDefaultEntityAttributeGroupForm form) {
        return new SetDefaultEntityAttributeGroupCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editEntityAttributeGroup(UserVisitPK userVisitPK, EditEntityAttributeGroupForm form) {
        return new EditEntityAttributeGroupCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteEntityAttributeGroup(UserVisitPK userVisitPK, DeleteEntityAttributeGroupForm form) {
        return new DeleteEntityAttributeGroupCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Entity Attribute Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityAttributeDescription(UserVisitPK userVisitPK, CreateEntityAttributeDescriptionForm form) {
        return new CreateEntityAttributeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityAttributeDescription(UserVisitPK userVisitPK, GetEntityAttributeDescriptionForm form) {
        return new GetEntityAttributeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityAttributeDescriptions(UserVisitPK userVisitPK, GetEntityAttributeDescriptionsForm form) {
        return new GetEntityAttributeDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editEntityAttributeDescription(UserVisitPK userVisitPK, EditEntityAttributeDescriptionForm form) {
        return new EditEntityAttributeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteEntityAttributeDescription(UserVisitPK userVisitPK, DeleteEntityAttributeDescriptionForm form) {
        return new DeleteEntityAttributeDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Entity Attributes
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityAttribute(UserVisitPK userVisitPK, CreateEntityAttributeForm form) {
        return new CreateEntityAttributeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityAttribute(UserVisitPK userVisitPK, GetEntityAttributeForm form) {
        return new GetEntityAttributeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityAttributes(UserVisitPK userVisitPK, GetEntityAttributesForm form) {
        return new GetEntityAttributesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editEntityAttribute(UserVisitPK userVisitPK, EditEntityAttributeForm form) {
        return new EditEntityAttributeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteEntityAttribute(UserVisitPK userVisitPK, DeleteEntityAttributeForm form) {
        return new DeleteEntityAttributeCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Entity Attribute Entity Attribute Groups
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityAttributeEntityAttributeGroup(UserVisitPK userVisitPK, CreateEntityAttributeEntityAttributeGroupForm form) {
        return new CreateEntityAttributeEntityAttributeGroupCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityAttributeEntityAttributeGroup(UserVisitPK userVisitPK, GetEntityAttributeEntityAttributeGroupForm form) {
        return new GetEntityAttributeEntityAttributeGroupCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityAttributeEntityAttributeGroups(UserVisitPK userVisitPK, GetEntityAttributeEntityAttributeGroupsForm form) {
        return new GetEntityAttributeEntityAttributeGroupsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editEntityAttributeEntityAttributeGroup(UserVisitPK userVisitPK, EditEntityAttributeEntityAttributeGroupForm form) {
        return new EditEntityAttributeEntityAttributeGroupCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteEntityAttributeEntityAttributeGroup(UserVisitPK userVisitPK, DeleteEntityAttributeEntityAttributeGroupForm form) {
        return new DeleteEntityAttributeEntityAttributeGroupCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Entity Attribute Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityAttributeTypeDescription(UserVisitPK userVisitPK, CreateEntityAttributeTypeDescriptionForm form) {
        return new CreateEntityAttributeTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Entity Attribute Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityAttributeType(UserVisitPK userVisitPK, CreateEntityAttributeTypeForm form) {
        return new CreateEntityAttributeTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityAttributeType(UserVisitPK userVisitPK, GetEntityAttributeTypeForm form) {
        return new GetEntityAttributeTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityAttributeTypes(UserVisitPK userVisitPK, GetEntityAttributeTypesForm form) {
        return new GetEntityAttributeTypesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityAttributeTypeChoices(UserVisitPK userVisitPK, GetEntityAttributeTypeChoicesForm form) {
        return new GetEntityAttributeTypeChoicesCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Entity List Item Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityListItemDescription(UserVisitPK userVisitPK, CreateEntityListItemDescriptionForm form) {
        return new CreateEntityListItemDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityListItemDescription(UserVisitPK userVisitPK, GetEntityListItemDescriptionForm form) {
        return new GetEntityListItemDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityListItemDescriptions(UserVisitPK userVisitPK, GetEntityListItemDescriptionsForm form) {
        return new GetEntityListItemDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editEntityListItemDescription(UserVisitPK userVisitPK, EditEntityListItemDescriptionForm form) {
        return new EditEntityListItemDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteEntityListItemDescription(UserVisitPK userVisitPK, DeleteEntityListItemDescriptionForm form) {
        return new DeleteEntityListItemDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Entity List Items
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityListItem(UserVisitPK userVisitPK, CreateEntityListItemForm form) {
        return new CreateEntityListItemCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityListItem(UserVisitPK userVisitPK, GetEntityListItemForm form) {
        return new GetEntityListItemCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityListItems(UserVisitPK userVisitPK, GetEntityListItemsForm form) {
        return new GetEntityListItemsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityListItemChoices(UserVisitPK userVisitPK, GetEntityListItemChoicesForm form) {
        return new GetEntityListItemChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultEntityListItem(UserVisitPK userVisitPK, SetDefaultEntityListItemForm form) {
        return new SetDefaultEntityListItemCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editEntityListItem(UserVisitPK userVisitPK, EditEntityListItemForm form) {
        return new EditEntityListItemCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteEntityListItem(UserVisitPK userVisitPK, DeleteEntityListItemForm form) {
        return new DeleteEntityListItemCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Entity Integer Range Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityIntegerRangeDescription(UserVisitPK userVisitPK, CreateEntityIntegerRangeDescriptionForm form) {
        return new CreateEntityIntegerRangeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityIntegerRangeDescription(UserVisitPK userVisitPK, GetEntityIntegerRangeDescriptionForm form) {
        return new GetEntityIntegerRangeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityIntegerRangeDescriptions(UserVisitPK userVisitPK, GetEntityIntegerRangeDescriptionsForm form) {
        return new GetEntityIntegerRangeDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editEntityIntegerRangeDescription(UserVisitPK userVisitPK, EditEntityIntegerRangeDescriptionForm form) {
        return new EditEntityIntegerRangeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteEntityIntegerRangeDescription(UserVisitPK userVisitPK, DeleteEntityIntegerRangeDescriptionForm form) {
        return new DeleteEntityIntegerRangeDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Entity Integer Ranges
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityIntegerRange(UserVisitPK userVisitPK, CreateEntityIntegerRangeForm form) {
        return new CreateEntityIntegerRangeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityIntegerRange(UserVisitPK userVisitPK, GetEntityIntegerRangeForm form) {
        return new GetEntityIntegerRangeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityIntegerRanges(UserVisitPK userVisitPK, GetEntityIntegerRangesForm form) {
        return new GetEntityIntegerRangesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityIntegerRangeChoices(UserVisitPK userVisitPK, GetEntityIntegerRangeChoicesForm form) {
        return new GetEntityIntegerRangeChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultEntityIntegerRange(UserVisitPK userVisitPK, SetDefaultEntityIntegerRangeForm form) {
        return new SetDefaultEntityIntegerRangeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editEntityIntegerRange(UserVisitPK userVisitPK, EditEntityIntegerRangeForm form) {
        return new EditEntityIntegerRangeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteEntityIntegerRange(UserVisitPK userVisitPK, DeleteEntityIntegerRangeForm form) {
        return new DeleteEntityIntegerRangeCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Entity Long Range Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityLongRangeDescription(UserVisitPK userVisitPK, CreateEntityLongRangeDescriptionForm form) {
        return new CreateEntityLongRangeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityLongRangeDescription(UserVisitPK userVisitPK, GetEntityLongRangeDescriptionForm form) {
        return new GetEntityLongRangeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityLongRangeDescriptions(UserVisitPK userVisitPK, GetEntityLongRangeDescriptionsForm form) {
        return new GetEntityLongRangeDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editEntityLongRangeDescription(UserVisitPK userVisitPK, EditEntityLongRangeDescriptionForm form) {
        return new EditEntityLongRangeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteEntityLongRangeDescription(UserVisitPK userVisitPK, DeleteEntityLongRangeDescriptionForm form) {
        return new DeleteEntityLongRangeDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Entity Long Ranges
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityLongRange(UserVisitPK userVisitPK, CreateEntityLongRangeForm form) {
        return new CreateEntityLongRangeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityLongRange(UserVisitPK userVisitPK, GetEntityLongRangeForm form) {
        return new GetEntityLongRangeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityLongRanges(UserVisitPK userVisitPK, GetEntityLongRangesForm form) {
        return new GetEntityLongRangesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityLongRangeChoices(UserVisitPK userVisitPK, GetEntityLongRangeChoicesForm form) {
        return new GetEntityLongRangeChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultEntityLongRange(UserVisitPK userVisitPK, SetDefaultEntityLongRangeForm form) {
        return new SetDefaultEntityLongRangeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editEntityLongRange(UserVisitPK userVisitPK, EditEntityLongRangeForm form) {
        return new EditEntityLongRangeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteEntityLongRange(UserVisitPK userVisitPK, DeleteEntityLongRangeForm form) {
        return new DeleteEntityLongRangeCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Entity Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityTypeDescription(UserVisitPK userVisitPK, CreateEntityTypeDescriptionForm form) {
        return new CreateEntityTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityTypeDescription(UserVisitPK userVisitPK, GetEntityTypeDescriptionForm form) {
        return new GetEntityTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityTypeDescriptions(UserVisitPK userVisitPK, GetEntityTypeDescriptionsForm form) {
        return new GetEntityTypeDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editEntityTypeDescription(UserVisitPK userVisitPK, EditEntityTypeDescriptionForm form) {
        return new EditEntityTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteEntityTypeDescription(UserVisitPK userVisitPK, DeleteEntityTypeDescriptionForm form) {
        return new DeleteEntityTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Mime Type Usage Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createMimeTypeUsageType(UserVisitPK userVisitPK, CreateMimeTypeUsageTypeForm form) {
        return new CreateMimeTypeUsageTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getMimeTypeUsageType(UserVisitPK userVisitPK, GetMimeTypeUsageTypeForm form) {
        return new GetMimeTypeUsageTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getMimeTypeUsageTypes(UserVisitPK userVisitPK, GetMimeTypeUsageTypesForm form) {
        return new GetMimeTypeUsageTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getMimeTypeUsageTypeChoices(UserVisitPK userVisitPK, GetMimeTypeUsageTypeChoicesForm form) {
        return new GetMimeTypeUsageTypeChoicesCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Mime Type Usage Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createMimeTypeUsageTypeDescription(UserVisitPK userVisitPK, CreateMimeTypeUsageTypeDescriptionForm form) {
        return new CreateMimeTypeUsageTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Mime Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createMimeType(UserVisitPK userVisitPK, CreateMimeTypeForm form) {
        return new CreateMimeTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getMimeTypes(UserVisitPK userVisitPK, GetMimeTypesForm form) {
        return new GetMimeTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getMimeType(UserVisitPK userVisitPK, GetMimeTypeForm form) {
        return new GetMimeTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getMimeTypeChoices(UserVisitPK userVisitPK, GetMimeTypeChoicesForm form) {
        return new GetMimeTypeChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultMimeType(UserVisitPK userVisitPK, SetDefaultMimeTypeForm form) {
        return new SetDefaultMimeTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editMimeType(UserVisitPK userVisitPK, EditMimeTypeForm form) {
        return new EditMimeTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteMimeType(UserVisitPK userVisitPK, DeleteMimeTypeForm form) {
        return new DeleteMimeTypeCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Mime Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createMimeTypeDescription(UserVisitPK userVisitPK, CreateMimeTypeDescriptionForm form) {
        return new CreateMimeTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getMimeTypeDescriptions(UserVisitPK userVisitPK, GetMimeTypeDescriptionsForm form) {
        return new GetMimeTypeDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getMimeTypeDescription(UserVisitPK userVisitPK, GetMimeTypeDescriptionForm form) {
        return new GetMimeTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editMimeTypeDescription(UserVisitPK userVisitPK, EditMimeTypeDescriptionForm form) {
        return new EditMimeTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteMimeTypeDescription(UserVisitPK userVisitPK, DeleteMimeTypeDescriptionForm form) {
        return new DeleteMimeTypeDescriptionCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Mime Type Usages
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createMimeTypeUsage(UserVisitPK userVisitPK, CreateMimeTypeUsageForm form) {
        return new CreateMimeTypeUsageCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getMimeTypeUsages(UserVisitPK userVisitPK, GetMimeTypeUsagesForm form) {
        return new GetMimeTypeUsagesCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Mime Type File Extensions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createMimeTypeFileExtension(UserVisitPK userVisitPK, CreateMimeTypeFileExtensionForm form) {
        return new CreateMimeTypeFileExtensionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getMimeTypeFileExtension(UserVisitPK userVisitPK, GetMimeTypeFileExtensionForm form) {
        return new GetMimeTypeFileExtensionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getMimeTypeFileExtensions(UserVisitPK userVisitPK, GetMimeTypeFileExtensionsForm form) {
        return new GetMimeTypeFileExtensionsCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Protocols
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createProtocol(UserVisitPK userVisitPK, CreateProtocolForm form) {
        return new CreateProtocolCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getProtocolChoices(UserVisitPK userVisitPK, GetProtocolChoicesForm form) {
        return new GetProtocolChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getProtocol(UserVisitPK userVisitPK, GetProtocolForm form) {
        return new GetProtocolCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getProtocols(UserVisitPK userVisitPK, GetProtocolsForm form) {
        return new GetProtocolsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultProtocol(UserVisitPK userVisitPK, SetDefaultProtocolForm form) {
        return new SetDefaultProtocolCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editProtocol(UserVisitPK userVisitPK, EditProtocolForm form) {
        return new EditProtocolCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteProtocol(UserVisitPK userVisitPK, DeleteProtocolForm form) {
        return new DeleteProtocolCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Protocol Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createProtocolDescription(UserVisitPK userVisitPK, CreateProtocolDescriptionForm form) {
        return new CreateProtocolDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getProtocolDescription(UserVisitPK userVisitPK, GetProtocolDescriptionForm form) {
        return new GetProtocolDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getProtocolDescriptions(UserVisitPK userVisitPK, GetProtocolDescriptionsForm form) {
        return new GetProtocolDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editProtocolDescription(UserVisitPK userVisitPK, EditProtocolDescriptionForm form) {
        return new EditProtocolDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteProtocolDescription(UserVisitPK userVisitPK, DeleteProtocolDescriptionForm form) {
        return new DeleteProtocolDescriptionCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Services
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createService(UserVisitPK userVisitPK, CreateServiceForm form) {
        return new CreateServiceCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getServiceChoices(UserVisitPK userVisitPK, GetServiceChoicesForm form) {
        return new GetServiceChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getService(UserVisitPK userVisitPK, GetServiceForm form) {
        return new GetServiceCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getServices(UserVisitPK userVisitPK, GetServicesForm form) {
        return new GetServicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultService(UserVisitPK userVisitPK, SetDefaultServiceForm form) {
        return new SetDefaultServiceCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editService(UserVisitPK userVisitPK, EditServiceForm form) {
        return new EditServiceCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteService(UserVisitPK userVisitPK, DeleteServiceForm form) {
        return new DeleteServiceCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Service Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createServiceDescription(UserVisitPK userVisitPK, CreateServiceDescriptionForm form) {
        return new CreateServiceDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getServiceDescription(UserVisitPK userVisitPK, GetServiceDescriptionForm form) {
        return new GetServiceDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getServiceDescriptions(UserVisitPK userVisitPK, GetServiceDescriptionsForm form) {
        return new GetServiceDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editServiceDescription(UserVisitPK userVisitPK, EditServiceDescriptionForm form) {
        return new EditServiceDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteServiceDescription(UserVisitPK userVisitPK, DeleteServiceDescriptionForm form) {
        return new DeleteServiceDescriptionCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Servers
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createServer(UserVisitPK userVisitPK, CreateServerForm form) {
        return new CreateServerCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getServerChoices(UserVisitPK userVisitPK, GetServerChoicesForm form) {
        return new GetServerChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getServer(UserVisitPK userVisitPK, GetServerForm form) {
        return new GetServerCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getServers(UserVisitPK userVisitPK, GetServersForm form) {
        return new GetServersCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultServer(UserVisitPK userVisitPK, SetDefaultServerForm form) {
        return new SetDefaultServerCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editServer(UserVisitPK userVisitPK, EditServerForm form) {
        return new EditServerCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteServer(UserVisitPK userVisitPK, DeleteServerForm form) {
        return new DeleteServerCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Server Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createServerDescription(UserVisitPK userVisitPK, CreateServerDescriptionForm form) {
        return new CreateServerDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getServerDescription(UserVisitPK userVisitPK, GetServerDescriptionForm form) {
        return new GetServerDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getServerDescriptions(UserVisitPK userVisitPK, GetServerDescriptionsForm form) {
        return new GetServerDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editServerDescription(UserVisitPK userVisitPK, EditServerDescriptionForm form) {
        return new EditServerDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteServerDescription(UserVisitPK userVisitPK, DeleteServerDescriptionForm form) {
        return new DeleteServerDescriptionCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Server Services
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createServerService(UserVisitPK userVisitPK, CreateServerServiceForm form) {
        return new CreateServerServiceCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getServerService(UserVisitPK userVisitPK, GetServerServiceForm form) {
        return new GetServerServiceCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getServerServices(UserVisitPK userVisitPK, GetServerServicesForm form) {
        return new GetServerServicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteServerService(UserVisitPK userVisitPK, DeleteServerServiceForm form) {
        return new DeleteServerServiceCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Entity Boolean Defaults
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createEntityBooleanDefault(UserVisitPK userVisitPK, CreateEntityBooleanDefaultForm form) {
        return new CreateEntityBooleanDefaultCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editEntityBooleanDefault(UserVisitPK userVisitPK, EditEntityBooleanDefaultForm form) {
        return new EditEntityBooleanDefaultCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteEntityBooleanDefault(UserVisitPK userVisitPK, DeleteEntityBooleanDefaultForm form) {
        return new DeleteEntityBooleanDefaultCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Entity Boolean Attributes
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createEntityBooleanAttribute(UserVisitPK userVisitPK, CreateEntityBooleanAttributeForm form) {
        return new CreateEntityBooleanAttributeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editEntityBooleanAttribute(UserVisitPK userVisitPK, EditEntityBooleanAttributeForm form) {
        return new EditEntityBooleanAttributeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteEntityBooleanAttribute(UserVisitPK userVisitPK, DeleteEntityBooleanAttributeForm form) {
        return new DeleteEntityBooleanAttributeCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Entity Integer Defaults
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createEntityIntegerDefault(UserVisitPK userVisitPK, CreateEntityIntegerDefaultForm form) {
        return new CreateEntityIntegerDefaultCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editEntityIntegerDefault(UserVisitPK userVisitPK, EditEntityIntegerDefaultForm form) {
        return new EditEntityIntegerDefaultCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteEntityIntegerDefault(UserVisitPK userVisitPK, DeleteEntityIntegerDefaultForm form) {
        return new DeleteEntityIntegerDefaultCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Entity Integer Attributes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityIntegerAttribute(UserVisitPK userVisitPK, CreateEntityIntegerAttributeForm form) {
        return new CreateEntityIntegerAttributeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editEntityIntegerAttribute(UserVisitPK userVisitPK, EditEntityIntegerAttributeForm form) {
        return new EditEntityIntegerAttributeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteEntityIntegerAttribute(UserVisitPK userVisitPK, DeleteEntityIntegerAttributeForm form) {
        return new DeleteEntityIntegerAttributeCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Entity List Item Defaults
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createEntityListItemDefault(UserVisitPK userVisitPK, CreateEntityListItemDefaultForm form) {
        return new CreateEntityListItemDefaultCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editEntityListItemDefault(UserVisitPK userVisitPK, EditEntityListItemDefaultForm form) {
        return new EditEntityListItemDefaultCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteEntityListItemDefault(UserVisitPK userVisitPK, DeleteEntityListItemDefaultForm form) {
        return new DeleteEntityListItemDefaultCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Entity List Item Attributes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityListItemAttribute(UserVisitPK userVisitPK, CreateEntityListItemAttributeForm form) {
        return new CreateEntityListItemAttributeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editEntityListItemAttribute(UserVisitPK userVisitPK, EditEntityListItemAttributeForm form) {
        return new EditEntityListItemAttributeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteEntityListItemAttribute(UserVisitPK userVisitPK, DeleteEntityListItemAttributeForm form) {
        return new DeleteEntityListItemAttributeCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Entity Long Defaults
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createEntityLongDefault(UserVisitPK userVisitPK, CreateEntityLongDefaultForm form) {
        return new CreateEntityLongDefaultCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editEntityLongDefault(UserVisitPK userVisitPK, EditEntityLongDefaultForm form) {
        return new EditEntityLongDefaultCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteEntityLongDefault(UserVisitPK userVisitPK, DeleteEntityLongDefaultForm form) {
        return new DeleteEntityLongDefaultCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Entity Long Attributes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityLongAttribute(UserVisitPK userVisitPK, CreateEntityLongAttributeForm form) {
        return new CreateEntityLongAttributeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editEntityLongAttribute(UserVisitPK userVisitPK, EditEntityLongAttributeForm form) {
        return new EditEntityLongAttributeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteEntityLongAttribute(UserVisitPK userVisitPK, DeleteEntityLongAttributeForm form) {
        return new DeleteEntityLongAttributeCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Entity Multiple List Item Defaults
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createEntityMultipleListItemDefault(UserVisitPK userVisitPK, CreateEntityMultipleListItemDefaultForm form) {
        return new CreateEntityMultipleListItemDefaultCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteEntityMultipleListItemDefault(UserVisitPK userVisitPK, DeleteEntityMultipleListItemDefaultForm form) {
        return new DeleteEntityMultipleListItemDefaultCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Entity Multiple List Item Attributes
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createEntityMultipleListItemAttribute(UserVisitPK userVisitPK, CreateEntityMultipleListItemAttributeForm form) {
        return new CreateEntityMultipleListItemAttributeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteEntityMultipleListItemAttribute(UserVisitPK userVisitPK, DeleteEntityMultipleListItemAttributeForm form) {
        return new DeleteEntityMultipleListItemAttributeCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Entity Name Attributes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityNameAttribute(UserVisitPK userVisitPK, CreateEntityNameAttributeForm form) {
        return new CreateEntityNameAttributeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editEntityNameAttribute(UserVisitPK userVisitPK, EditEntityNameAttributeForm form) {
        return new EditEntityNameAttributeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteEntityNameAttribute(UserVisitPK userVisitPK, DeleteEntityNameAttributeForm form) {
        return new DeleteEntityNameAttributeCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Entity String Defaults
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createEntityStringDefault(UserVisitPK userVisitPK, CreateEntityStringDefaultForm form) {
        return new CreateEntityStringDefaultCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editEntityStringDefault(UserVisitPK userVisitPK, EditEntityStringDefaultForm form) {
        return new EditEntityStringDefaultCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteEntityStringDefault(UserVisitPK userVisitPK, DeleteEntityStringDefaultForm form) {
        return new DeleteEntityStringDefaultCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Entity String Attributes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityStringAttribute(UserVisitPK userVisitPK, CreateEntityStringAttributeForm form) {
        return new CreateEntityStringAttributeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editEntityStringAttribute(UserVisitPK userVisitPK, EditEntityStringAttributeForm form) {
        return new EditEntityStringAttributeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteEntityStringAttribute(UserVisitPK userVisitPK, DeleteEntityStringAttributeForm form) {
        return new DeleteEntityStringAttributeCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Entity Geo Point Defaults
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createEntityGeoPointDefault(UserVisitPK userVisitPK, CreateEntityGeoPointDefaultForm form) {
        return new CreateEntityGeoPointDefaultCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editEntityGeoPointDefault(UserVisitPK userVisitPK, EditEntityGeoPointDefaultForm form) {
        return new EditEntityGeoPointDefaultCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteEntityGeoPointDefault(UserVisitPK userVisitPK, DeleteEntityGeoPointDefaultForm form) {
        return new DeleteEntityGeoPointDefaultCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Entity Geo Point Attributes
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createEntityGeoPointAttribute(UserVisitPK userVisitPK, CreateEntityGeoPointAttributeForm form) {
        return new CreateEntityGeoPointAttributeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editEntityGeoPointAttribute(UserVisitPK userVisitPK, EditEntityGeoPointAttributeForm form) {
        return new EditEntityGeoPointAttributeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteEntityGeoPointAttribute(UserVisitPK userVisitPK, DeleteEntityGeoPointAttributeForm form) {
        return new DeleteEntityGeoPointAttributeCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Entity Blob Attributes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityBlobAttribute(UserVisitPK userVisitPK, CreateEntityBlobAttributeForm form) {
        return new CreateEntityBlobAttributeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editEntityBlobAttribute(UserVisitPK userVisitPK, EditEntityBlobAttributeForm form) {
        return new EditEntityBlobAttributeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getEntityBlobAttribute(UserVisitPK userVisitPK, GetEntityBlobAttributeForm form) {
        return new GetEntityBlobAttributeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteEntityBlobAttribute(UserVisitPK userVisitPK, DeleteEntityBlobAttributeForm form) {
        return new DeleteEntityBlobAttributeCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Clob Attributes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityClobAttribute(UserVisitPK userVisitPK, CreateEntityClobAttributeForm form) {
        return new CreateEntityClobAttributeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editEntityClobAttribute(UserVisitPK userVisitPK, EditEntityClobAttributeForm form) {
        return new EditEntityClobAttributeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getEntityClobAttribute(UserVisitPK userVisitPK, GetEntityClobAttributeForm form) {
        return new GetEntityClobAttributeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteEntityClobAttribute(UserVisitPK userVisitPK, DeleteEntityClobAttributeForm form) {
        return new DeleteEntityClobAttributeCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Entity Time Defaults
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createEntityTimeDefault(UserVisitPK userVisitPK, CreateEntityTimeDefaultForm form) {
        return new CreateEntityTimeDefaultCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editEntityTimeDefault(UserVisitPK userVisitPK, EditEntityTimeDefaultForm form) {
        return new EditEntityTimeDefaultCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteEntityTimeDefault(UserVisitPK userVisitPK, DeleteEntityTimeDefaultForm form) {
        return new DeleteEntityTimeDefaultCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Entity Time Attributes
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createEntityTimeAttribute(UserVisitPK userVisitPK, CreateEntityTimeAttributeForm form) {
        return new CreateEntityTimeAttributeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editEntityTimeAttribute(UserVisitPK userVisitPK, EditEntityTimeAttributeForm form) {
        return new EditEntityTimeAttributeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteEntityTimeAttribute(UserVisitPK userVisitPK, DeleteEntityTimeAttributeForm form) {
        return new DeleteEntityTimeAttributeCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Entity Date Defaults
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createEntityDateDefault(UserVisitPK userVisitPK, CreateEntityDateDefaultForm form) {
        return new CreateEntityDateDefaultCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editEntityDateDefault(UserVisitPK userVisitPK, EditEntityDateDefaultForm form) {
        return new EditEntityDateDefaultCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteEntityDateDefault(UserVisitPK userVisitPK, DeleteEntityDateDefaultForm form) {
        return new DeleteEntityDateDefaultCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Entity Date Attributes
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createEntityDateAttribute(UserVisitPK userVisitPK, CreateEntityDateAttributeForm form) {
        return new CreateEntityDateAttributeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editEntityDateAttribute(UserVisitPK userVisitPK, EditEntityDateAttributeForm form) {
        return new EditEntityDateAttributeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteEntityDateAttribute(UserVisitPK userVisitPK, DeleteEntityDateAttributeForm form) {
        return new DeleteEntityDateAttributeCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Entity Attribute Entity Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityAttributeEntityType(UserVisitPK userVisitPK, CreateEntityAttributeEntityTypeForm form) {
        return new CreateEntityAttributeEntityTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityAttributeEntityType(UserVisitPK userVisitPK, GetEntityAttributeEntityTypeForm form) {
        return new GetEntityAttributeEntityTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getEntityAttributeEntityTypes(UserVisitPK userVisitPK, GetEntityAttributeEntityTypesForm form) {
        return new GetEntityAttributeEntityTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteEntityAttributeEntityType(UserVisitPK userVisitPK, DeleteEntityAttributeEntityTypeForm form) {
        return new DeleteEntityAttributeEntityTypeCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Entity Entity Attributes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityEntityAttribute(UserVisitPK userVisitPK, CreateEntityEntityAttributeForm form) {
        return new CreateEntityEntityAttributeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editEntityEntityAttribute(UserVisitPK userVisitPK, EditEntityEntityAttributeForm form) {
        return new EditEntityEntityAttributeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteEntityEntityAttribute(UserVisitPK userVisitPK, DeleteEntityEntityAttributeForm form) {
        return new DeleteEntityEntityAttributeCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Collection Attributes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityCollectionAttribute(UserVisitPK userVisitPK, CreateEntityCollectionAttributeForm form) {
        return new CreateEntityCollectionAttributeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteEntityCollectionAttribute(UserVisitPK userVisitPK, DeleteEntityCollectionAttributeForm form) {
        return new DeleteEntityCollectionAttributeCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Entity Workflow Attributes
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createEntityWorkflowAttribute(UserVisitPK userVisitPK, CreateEntityWorkflowAttributeForm form) {
        return new CreateEntityWorkflowAttributeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setEntityWorkflowAttributeStatus(UserVisitPK userVisitPK, SetEntityWorkflowAttributeStatusForm form) {
        return new SetEntityWorkflowAttributeStatusCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteEntityWorkflowAttribute(UserVisitPK userVisitPK, DeleteEntityWorkflowAttributeForm form) {
        return new DeleteEntityWorkflowAttributeCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Cache Entries
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createCacheEntry(UserVisitPK userVisitPK, CreateCacheEntryForm form) {
        return new CreateCacheEntryCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getCacheEntry(UserVisitPK userVisitPK, GetCacheEntryForm form) {
        return new GetCacheEntryCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getCacheEntries(UserVisitPK userVisitPK, GetCacheEntriesForm form) {
        return new GetCacheEntriesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult removeCacheEntry(UserVisitPK userVisitPK, RemoveCacheEntryForm form) {
        return new RemoveCacheEntryCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Cache Entry Dependencies
    // -------------------------------------------------------------------------

    @Override
    public CommandResult getCacheEntryDependencies(UserVisitPK userVisitPK, GetCacheEntryDependenciesForm form) {
        return new GetCacheEntryDependenciesCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Applications
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createApplication(UserVisitPK userVisitPK, CreateApplicationForm form) {
        return new CreateApplicationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getApplicationChoices(UserVisitPK userVisitPK, GetApplicationChoicesForm form) {
        return new GetApplicationChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getApplication(UserVisitPK userVisitPK, GetApplicationForm form) {
        return new GetApplicationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getApplications(UserVisitPK userVisitPK, GetApplicationsForm form) {
        return new GetApplicationsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultApplication(UserVisitPK userVisitPK, SetDefaultApplicationForm form) {
        return new SetDefaultApplicationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editApplication(UserVisitPK userVisitPK, EditApplicationForm form) {
        return new EditApplicationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteApplication(UserVisitPK userVisitPK, DeleteApplicationForm form) {
        return new DeleteApplicationCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Applications Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createApplicationDescription(UserVisitPK userVisitPK, CreateApplicationDescriptionForm form) {
        return new CreateApplicationDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getApplicationDescription(UserVisitPK userVisitPK, GetApplicationDescriptionForm form) {
        return new GetApplicationDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getApplicationDescriptions(UserVisitPK userVisitPK, GetApplicationDescriptionsForm form) {
        return new GetApplicationDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editApplicationDescription(UserVisitPK userVisitPK, EditApplicationDescriptionForm form) {
        return new EditApplicationDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteApplicationDescription(UserVisitPK userVisitPK, DeleteApplicationDescriptionForm form) {
        return new DeleteApplicationDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Editors
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEditor(UserVisitPK userVisitPK, CreateEditorForm form) {
        return new CreateEditorCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEditorChoices(UserVisitPK userVisitPK, GetEditorChoicesForm form) {
        return new GetEditorChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEditor(UserVisitPK userVisitPK, GetEditorForm form) {
        return new GetEditorCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEditors(UserVisitPK userVisitPK, GetEditorsForm form) {
        return new GetEditorsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultEditor(UserVisitPK userVisitPK, SetDefaultEditorForm form) {
        return new SetDefaultEditorCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editEditor(UserVisitPK userVisitPK, EditEditorForm form) {
        return new EditEditorCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteEditor(UserVisitPK userVisitPK, DeleteEditorForm form) {
        return new DeleteEditorCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Editors Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEditorDescription(UserVisitPK userVisitPK, CreateEditorDescriptionForm form) {
        return new CreateEditorDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEditorDescription(UserVisitPK userVisitPK, GetEditorDescriptionForm form) {
        return new GetEditorDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEditorDescriptions(UserVisitPK userVisitPK, GetEditorDescriptionsForm form) {
        return new GetEditorDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editEditorDescription(UserVisitPK userVisitPK, EditEditorDescriptionForm form) {
        return new EditEditorDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteEditorDescription(UserVisitPK userVisitPK, DeleteEditorDescriptionForm form) {
        return new DeleteEditorDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   ApplicationEditors
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createApplicationEditor(UserVisitPK userVisitPK, CreateApplicationEditorForm form) {
        return new CreateApplicationEditorCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getApplicationEditorChoices(UserVisitPK userVisitPK, GetApplicationEditorChoicesForm form) {
        return new GetApplicationEditorChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getApplicationEditor(UserVisitPK userVisitPK, GetApplicationEditorForm form) {
        return new GetApplicationEditorCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getApplicationEditors(UserVisitPK userVisitPK, GetApplicationEditorsForm form) {
        return new GetApplicationEditorsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultApplicationEditor(UserVisitPK userVisitPK, SetDefaultApplicationEditorForm form) {
        return new SetDefaultApplicationEditorCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editApplicationEditor(UserVisitPK userVisitPK, EditApplicationEditorForm form) {
        return new EditApplicationEditorCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteApplicationEditor(UserVisitPK userVisitPK, DeleteApplicationEditorForm form) {
        return new DeleteApplicationEditorCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Application Editor Uses
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createApplicationEditorUse(UserVisitPK userVisitPK, CreateApplicationEditorUseForm form) {
        return new CreateApplicationEditorUseCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getApplicationEditorUseChoices(UserVisitPK userVisitPK, GetApplicationEditorUseChoicesForm form) {
        return new GetApplicationEditorUseChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getApplicationEditorUse(UserVisitPK userVisitPK, GetApplicationEditorUseForm form) {
        return new GetApplicationEditorUseCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getApplicationEditorUses(UserVisitPK userVisitPK, GetApplicationEditorUsesForm form) {
        return new GetApplicationEditorUsesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultApplicationEditorUse(UserVisitPK userVisitPK, SetDefaultApplicationEditorUseForm form) {
        return new SetDefaultApplicationEditorUseCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editApplicationEditorUse(UserVisitPK userVisitPK, EditApplicationEditorUseForm form) {
        return new EditApplicationEditorUseCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteApplicationEditorUse(UserVisitPK userVisitPK, DeleteApplicationEditorUseForm form) {
        return new DeleteApplicationEditorUseCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Application Editor Use Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createApplicationEditorUseDescription(UserVisitPK userVisitPK, CreateApplicationEditorUseDescriptionForm form) {
        return new CreateApplicationEditorUseDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getApplicationEditorUseDescription(UserVisitPK userVisitPK, GetApplicationEditorUseDescriptionForm form) {
        return new GetApplicationEditorUseDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getApplicationEditorUseDescriptions(UserVisitPK userVisitPK, GetApplicationEditorUseDescriptionsForm form) {
        return new GetApplicationEditorUseDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editApplicationEditorUseDescription(UserVisitPK userVisitPK, EditApplicationEditorUseDescriptionForm form) {
        return new EditApplicationEditorUseDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteApplicationEditorUseDescription(UserVisitPK userVisitPK, DeleteApplicationEditorUseDescriptionForm form) {
        return new DeleteApplicationEditorUseDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Party Application Editor Uses
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyApplicationEditorUse(UserVisitPK userVisitPK, CreatePartyApplicationEditorUseForm form) {
        return new CreatePartyApplicationEditorUseCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getPartyApplicationEditorUse(UserVisitPK userVisitPK, GetPartyApplicationEditorUseForm form) {
        return new GetPartyApplicationEditorUseCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getPartyApplicationEditorUses(UserVisitPK userVisitPK, GetPartyApplicationEditorUsesForm form) {
        return new GetPartyApplicationEditorUsesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editPartyApplicationEditorUse(UserVisitPK userVisitPK, EditPartyApplicationEditorUseForm form) {
        return new EditPartyApplicationEditorUseCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deletePartyApplicationEditorUse(UserVisitPK userVisitPK, DeletePartyApplicationEditorUseForm form) {
        return new DeletePartyApplicationEditorUseCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Appearances
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createAppearance(UserVisitPK userVisitPK, CreateAppearanceForm form) {
        return new CreateAppearanceCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getAppearanceChoices(UserVisitPK userVisitPK, GetAppearanceChoicesForm form) {
        return new GetAppearanceChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getAppearance(UserVisitPK userVisitPK, GetAppearanceForm form) {
        return new GetAppearanceCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getAppearances(UserVisitPK userVisitPK, GetAppearancesForm form) {
        return new GetAppearancesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultAppearance(UserVisitPK userVisitPK, SetDefaultAppearanceForm form) {
        return new SetDefaultAppearanceCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editAppearance(UserVisitPK userVisitPK, EditAppearanceForm form) {
        return new EditAppearanceCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteAppearance(UserVisitPK userVisitPK, DeleteAppearanceForm form) {
        return new DeleteAppearanceCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Appearances Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createAppearanceDescription(UserVisitPK userVisitPK, CreateAppearanceDescriptionForm form) {
        return new CreateAppearanceDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getAppearanceDescription(UserVisitPK userVisitPK, GetAppearanceDescriptionForm form) {
        return new GetAppearanceDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getAppearanceDescriptions(UserVisitPK userVisitPK, GetAppearanceDescriptionsForm form) {
        return new GetAppearanceDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editAppearanceDescription(UserVisitPK userVisitPK, EditAppearanceDescriptionForm form) {
        return new EditAppearanceDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteAppearanceDescription(UserVisitPK userVisitPK, DeleteAppearanceDescriptionForm form) {
        return new DeleteAppearanceDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Appearances Text Decorations
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createAppearanceTextDecoration(UserVisitPK userVisitPK, CreateAppearanceTextDecorationForm form) {
        return new CreateAppearanceTextDecorationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getAppearanceTextDecoration(UserVisitPK userVisitPK, GetAppearanceTextDecorationForm form) {
        return new GetAppearanceTextDecorationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getAppearanceTextDecorations(UserVisitPK userVisitPK, GetAppearanceTextDecorationsForm form) {
        return new GetAppearanceTextDecorationsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteAppearanceTextDecoration(UserVisitPK userVisitPK, DeleteAppearanceTextDecorationForm form) {
        return new DeleteAppearanceTextDecorationCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Appearances Text Transformations
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createAppearanceTextTransformation(UserVisitPK userVisitPK, CreateAppearanceTextTransformationForm form) {
        return new CreateAppearanceTextTransformationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getAppearanceTextTransformation(UserVisitPK userVisitPK, GetAppearanceTextTransformationForm form) {
        return new GetAppearanceTextTransformationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getAppearanceTextTransformations(UserVisitPK userVisitPK, GetAppearanceTextTransformationsForm form) {
        return new GetAppearanceTextTransformationsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteAppearanceTextTransformation(UserVisitPK userVisitPK, DeleteAppearanceTextTransformationForm form) {
        return new DeleteAppearanceTextTransformationCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Colors
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createColor(UserVisitPK userVisitPK, CreateColorForm form) {
        return new CreateColorCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getColorChoices(UserVisitPK userVisitPK, GetColorChoicesForm form) {
        return new GetColorChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getColor(UserVisitPK userVisitPK, GetColorForm form) {
        return new GetColorCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getColors(UserVisitPK userVisitPK, GetColorsForm form) {
        return new GetColorsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultColor(UserVisitPK userVisitPK, SetDefaultColorForm form) {
        return new SetDefaultColorCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editColor(UserVisitPK userVisitPK, EditColorForm form) {
        return new EditColorCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteColor(UserVisitPK userVisitPK, DeleteColorForm form) {
        return new DeleteColorCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Color Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createColorDescription(UserVisitPK userVisitPK, CreateColorDescriptionForm form) {
        return new CreateColorDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getColorDescription(UserVisitPK userVisitPK, GetColorDescriptionForm form) {
        return new GetColorDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getColorDescriptions(UserVisitPK userVisitPK, GetColorDescriptionsForm form) {
        return new GetColorDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editColorDescription(UserVisitPK userVisitPK, EditColorDescriptionForm form) {
        return new EditColorDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteColorDescription(UserVisitPK userVisitPK, DeleteColorDescriptionForm form) {
        return new DeleteColorDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Font Styles
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createFontStyle(UserVisitPK userVisitPK, CreateFontStyleForm form) {
        return new CreateFontStyleCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getFontStyleChoices(UserVisitPK userVisitPK, GetFontStyleChoicesForm form) {
        return new GetFontStyleChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getFontStyle(UserVisitPK userVisitPK, GetFontStyleForm form) {
        return new GetFontStyleCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getFontStyles(UserVisitPK userVisitPK, GetFontStylesForm form) {
        return new GetFontStylesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultFontStyle(UserVisitPK userVisitPK, SetDefaultFontStyleForm form) {
        return new SetDefaultFontStyleCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editFontStyle(UserVisitPK userVisitPK, EditFontStyleForm form) {
        return new EditFontStyleCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteFontStyle(UserVisitPK userVisitPK, DeleteFontStyleForm form) {
        return new DeleteFontStyleCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Font Style Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createFontStyleDescription(UserVisitPK userVisitPK, CreateFontStyleDescriptionForm form) {
        return new CreateFontStyleDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getFontStyleDescription(UserVisitPK userVisitPK, GetFontStyleDescriptionForm form) {
        return new GetFontStyleDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getFontStyleDescriptions(UserVisitPK userVisitPK, GetFontStyleDescriptionsForm form) {
        return new GetFontStyleDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editFontStyleDescription(UserVisitPK userVisitPK, EditFontStyleDescriptionForm form) {
        return new EditFontStyleDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteFontStyleDescription(UserVisitPK userVisitPK, DeleteFontStyleDescriptionForm form) {
        return new DeleteFontStyleDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Font Weights
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createFontWeight(UserVisitPK userVisitPK, CreateFontWeightForm form) {
        return new CreateFontWeightCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getFontWeightChoices(UserVisitPK userVisitPK, GetFontWeightChoicesForm form) {
        return new GetFontWeightChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getFontWeight(UserVisitPK userVisitPK, GetFontWeightForm form) {
        return new GetFontWeightCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getFontWeights(UserVisitPK userVisitPK, GetFontWeightsForm form) {
        return new GetFontWeightsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultFontWeight(UserVisitPK userVisitPK, SetDefaultFontWeightForm form) {
        return new SetDefaultFontWeightCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editFontWeight(UserVisitPK userVisitPK, EditFontWeightForm form) {
        return new EditFontWeightCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteFontWeight(UserVisitPK userVisitPK, DeleteFontWeightForm form) {
        return new DeleteFontWeightCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Font Weight Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createFontWeightDescription(UserVisitPK userVisitPK, CreateFontWeightDescriptionForm form) {
        return new CreateFontWeightDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getFontWeightDescription(UserVisitPK userVisitPK, GetFontWeightDescriptionForm form) {
        return new GetFontWeightDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getFontWeightDescriptions(UserVisitPK userVisitPK, GetFontWeightDescriptionsForm form) {
        return new GetFontWeightDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editFontWeightDescription(UserVisitPK userVisitPK, EditFontWeightDescriptionForm form) {
        return new EditFontWeightDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteFontWeightDescription(UserVisitPK userVisitPK, DeleteFontWeightDescriptionForm form) {
        return new DeleteFontWeightDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Text Decorations
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createTextDecoration(UserVisitPK userVisitPK, CreateTextDecorationForm form) {
        return new CreateTextDecorationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTextDecorationChoices(UserVisitPK userVisitPK, GetTextDecorationChoicesForm form) {
        return new GetTextDecorationChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTextDecoration(UserVisitPK userVisitPK, GetTextDecorationForm form) {
        return new GetTextDecorationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTextDecorations(UserVisitPK userVisitPK, GetTextDecorationsForm form) {
        return new GetTextDecorationsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultTextDecoration(UserVisitPK userVisitPK, SetDefaultTextDecorationForm form) {
        return new SetDefaultTextDecorationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editTextDecoration(UserVisitPK userVisitPK, EditTextDecorationForm form) {
        return new EditTextDecorationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteTextDecoration(UserVisitPK userVisitPK, DeleteTextDecorationForm form) {
        return new DeleteTextDecorationCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Text Decoration Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createTextDecorationDescription(UserVisitPK userVisitPK, CreateTextDecorationDescriptionForm form) {
        return new CreateTextDecorationDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTextDecorationDescription(UserVisitPK userVisitPK, GetTextDecorationDescriptionForm form) {
        return new GetTextDecorationDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTextDecorationDescriptions(UserVisitPK userVisitPK, GetTextDecorationDescriptionsForm form) {
        return new GetTextDecorationDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editTextDecorationDescription(UserVisitPK userVisitPK, EditTextDecorationDescriptionForm form) {
        return new EditTextDecorationDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteTextDecorationDescription(UserVisitPK userVisitPK, DeleteTextDecorationDescriptionForm form) {
        return new DeleteTextDecorationDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Text Transformations
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createTextTransformation(UserVisitPK userVisitPK, CreateTextTransformationForm form) {
        return new CreateTextTransformationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTextTransformationChoices(UserVisitPK userVisitPK, GetTextTransformationChoicesForm form) {
        return new GetTextTransformationChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTextTransformation(UserVisitPK userVisitPK, GetTextTransformationForm form) {
        return new GetTextTransformationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTextTransformations(UserVisitPK userVisitPK, GetTextTransformationsForm form) {
        return new GetTextTransformationsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultTextTransformation(UserVisitPK userVisitPK, SetDefaultTextTransformationForm form) {
        return new SetDefaultTextTransformationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editTextTransformation(UserVisitPK userVisitPK, EditTextTransformationForm form) {
        return new EditTextTransformationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteTextTransformation(UserVisitPK userVisitPK, DeleteTextTransformationForm form) {
        return new DeleteTextTransformationCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Text Transformation Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createTextTransformationDescription(UserVisitPK userVisitPK, CreateTextTransformationDescriptionForm form) {
        return new CreateTextTransformationDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTextTransformationDescription(UserVisitPK userVisitPK, GetTextTransformationDescriptionForm form) {
        return new GetTextTransformationDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTextTransformationDescriptions(UserVisitPK userVisitPK, GetTextTransformationDescriptionsForm form) {
        return new GetTextTransformationDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editTextTransformationDescription(UserVisitPK userVisitPK, EditTextTransformationDescriptionForm form) {
        return new EditTextTransformationDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteTextTransformationDescription(UserVisitPK userVisitPK, DeleteTextTransformationDescriptionForm form) {
        return new DeleteTextTransformationDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Appearances
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityAppearance(UserVisitPK userVisitPK, CreateEntityAppearanceForm form) {
        return new CreateEntityAppearanceCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityAppearance(UserVisitPK userVisitPK, GetEntityAppearanceForm form) {
        return new GetEntityAppearanceCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editEntityAppearance(UserVisitPK userVisitPK, EditEntityAppearanceForm form) {
        return new EditEntityAppearanceCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteEntityAppearance(UserVisitPK userVisitPK, DeleteEntityAppearanceForm form) {
        return new DeleteEntityAppearanceCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Utilities
    // -------------------------------------------------------------------------

    @Override
    public CommandResult encrypt(UserVisitPK userVisitPK, EncryptForm form) {
        return new EncryptCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult decrypt(UserVisitPK userVisitPK, DecryptForm form) {
        return new DecryptCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult validate(UserVisitPK userVisitPK, ValidateForm form) {
        return new ValidateCommand(userVisitPK, form).run();
    }

}
