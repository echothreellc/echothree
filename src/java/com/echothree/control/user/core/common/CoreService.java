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

package com.echothree.control.user.core.common;

import com.echothree.control.user.core.common.form.*;
import com.echothree.control.user.core.common.result.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface CoreService
        extends CoreForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // -------------------------------------------------------------------------
    //   Base Keys
    // -------------------------------------------------------------------------
    
    CommandResult<GenerateBaseKeysResult> generateBaseKeys(UserVisitPK userVisitPK);
    
    CommandResult<?> loadBaseKeys(UserVisitPK userVisitPK, LoadBaseKeysForm form);
    
    CommandResult<ChangeBaseKeysResult> changeBaseKeys(UserVisitPK userVisitPK, ChangeBaseKeysForm form);
    
    // -------------------------------------------------------------------------
    //   Base Encryption Keys
    // -------------------------------------------------------------------------
    
    CommandResult<GetBaseEncryptionKeyResult> getBaseEncryptionKey(UserVisitPK userVisitPK, GetBaseEncryptionKeyForm form);
    
    CommandResult<GetBaseEncryptionKeysResult> getBaseEncryptionKeys(UserVisitPK userVisitPK, GetBaseEncryptionKeysForm form);
    
    CommandResult<GetBaseEncryptionKeyStatusChoicesResult> getBaseEncryptionKeyStatusChoices(UserVisitPK userVisitPK, GetBaseEncryptionKeyStatusChoicesForm form);
    
    CommandResult<?> setBaseEncryptionKeyStatus(UserVisitPK userVisitPK, SetBaseEncryptionKeyStatusForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Locks
    // -------------------------------------------------------------------------
    
    CommandResult<?> lockEntity(UserVisitPK userVisitPK, LockEntityForm form);
    
    CommandResult<?> unlockEntity(UserVisitPK userVisitPK, UnlockEntityForm form);
    
    CommandResult<?> removedExpiredEntityLocks(UserVisitPK userVisitPK);
    
    // -------------------------------------------------------------------------
    //   Component Vendors
    // -------------------------------------------------------------------------
    
    CommandResult<CreateComponentVendorResult> createComponentVendor(UserVisitPK userVisitPK, CreateComponentVendorForm form);
    
    CommandResult<GetComponentVendorResult> getComponentVendor(UserVisitPK userVisitPK, GetComponentVendorForm form);
    
    CommandResult<GetComponentVendorsResult> getComponentVendors(UserVisitPK userVisitPK, GetComponentVendorsForm form);
    
    CommandResult<EditComponentVendorResult> editComponentVendor(UserVisitPK userVisitPK, EditComponentVendorForm form);
    
    CommandResult<?> deleteComponentVendor(UserVisitPK userVisitPK, DeleteComponentVendorForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Types
    // -------------------------------------------------------------------------
    
    CommandResult<CreateEntityTypeResult> createEntityType(UserVisitPK userVisitPK, CreateEntityTypeForm form);
    
    CommandResult<GetEntityTypeResult> getEntityType(UserVisitPK userVisitPK, GetEntityTypeForm form);
    
    CommandResult<GetEntityTypesResult> getEntityTypes(UserVisitPK userVisitPK, GetEntityTypesForm form);
    
    CommandResult<EditEntityTypeResult> editEntityType(UserVisitPK userVisitPK, EditEntityTypeForm form);
    
    CommandResult<?> deleteEntityType(UserVisitPK userVisitPK, DeleteEntityTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Commands
    // -------------------------------------------------------------------------
    
    CommandResult<?> createCommand(UserVisitPK userVisitPK, CreateCommandForm form);
    
    CommandResult<GetCommandResult> getCommand(UserVisitPK userVisitPK, GetCommandForm form);
    
    CommandResult<GetCommandsResult> getCommands(UserVisitPK userVisitPK, GetCommandsForm form);
    
    CommandResult<EditCommandResult> editCommand(UserVisitPK userVisitPK, EditCommandForm form);
    
    CommandResult<?> deleteCommand(UserVisitPK userVisitPK, DeleteCommandForm form);
    
    // -------------------------------------------------------------------------
    //   Command Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createCommandDescription(UserVisitPK userVisitPK, CreateCommandDescriptionForm form);
    
    CommandResult<GetCommandDescriptionResult> getCommandDescription(UserVisitPK userVisitPK, GetCommandDescriptionForm form);
    
    CommandResult<GetCommandDescriptionsResult> getCommandDescriptions(UserVisitPK userVisitPK, GetCommandDescriptionsForm form);
    
    CommandResult<EditCommandDescriptionResult> editCommandDescription(UserVisitPK userVisitPK, EditCommandDescriptionForm form);
    
    CommandResult<?> deleteCommandDescription(UserVisitPK userVisitPK, DeleteCommandDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Command Message Types
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createCommandMessageType(UserVisitPK userVisitPK, CreateCommandMessageTypeForm form);
    
    CommandResult<GetCommandMessageTypeChoicesResult> getCommandMessageTypeChoices(UserVisitPK userVisitPK, GetCommandMessageTypeChoicesForm form);
    
    CommandResult<GetCommandMessageTypeResult> getCommandMessageType(UserVisitPK userVisitPK, GetCommandMessageTypeForm form);
    
    CommandResult<GetCommandMessageTypesResult> getCommandMessageTypes(UserVisitPK userVisitPK, GetCommandMessageTypesForm form);
    
    CommandResult<?> setDefaultCommandMessageType(UserVisitPK userVisitPK, SetDefaultCommandMessageTypeForm form);
    
    CommandResult<EditCommandMessageTypeResult> editCommandMessageType(UserVisitPK userVisitPK, EditCommandMessageTypeForm form);
    
    CommandResult<?> deleteCommandMessageType(UserVisitPK userVisitPK, DeleteCommandMessageTypeForm form);
    
    // --------------------------------------------------------------------------------
    //   Command Message Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createCommandMessageTypeDescription(UserVisitPK userVisitPK, CreateCommandMessageTypeDescriptionForm form);
    
    CommandResult<GetCommandMessageTypeDescriptionResult> getCommandMessageTypeDescription(UserVisitPK userVisitPK, GetCommandMessageTypeDescriptionForm form);
    
    CommandResult<GetCommandMessageTypeDescriptionsResult> getCommandMessageTypeDescriptions(UserVisitPK userVisitPK, GetCommandMessageTypeDescriptionsForm form);
    
    CommandResult<EditCommandMessageTypeDescriptionResult> editCommandMessageTypeDescription(UserVisitPK userVisitPK, EditCommandMessageTypeDescriptionForm form);
    
    CommandResult<?> deleteCommandMessageTypeDescription(UserVisitPK userVisitPK, DeleteCommandMessageTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Command Messages
    // -------------------------------------------------------------------------
    
    CommandResult<?> createCommandMessage(UserVisitPK userVisitPK, CreateCommandMessageForm form);
    
    CommandResult<GetCommandMessageResult> getCommandMessage(UserVisitPK userVisitPK, GetCommandMessageForm form);

    CommandResult<GetCommandMessagesResult> getCommandMessages(UserVisitPK userVisitPK, GetCommandMessagesForm form);

    CommandResult<EditCommandMessageResult> editCommandMessage(UserVisitPK userVisitPK, EditCommandMessageForm form);

    CommandResult<?> deleteCommandMessage(UserVisitPK userVisitPK, DeleteCommandMessageForm form);

    // -------------------------------------------------------------------------
    //   Command Message Translations
    // -------------------------------------------------------------------------

    CommandResult<?> createCommandMessageTranslation(UserVisitPK userVisitPK, CreateCommandMessageTranslationForm form);

    CommandResult<GetCommandMessageTranslationResult> getCommandMessageTranslation(UserVisitPK userVisitPK, GetCommandMessageTranslationForm form);

    CommandResult<GetCommandMessageTranslationsResult> getCommandMessageTranslations(UserVisitPK userVisitPK, GetCommandMessageTranslationsForm form);

    CommandResult<EditCommandMessageTranslationResult> editCommandMessageTranslation(UserVisitPK userVisitPK, EditCommandMessageTranslationForm form);

    CommandResult<?> deleteCommandMessageTranslation(UserVisitPK userVisitPK, DeleteCommandMessageTranslationForm form);

    // -------------------------------------------------------------------------
    //   Entity Instances
    // -------------------------------------------------------------------------

    CommandResult<CreateEntityInstanceResult> createEntityInstance(UserVisitPK userVisitPK, CreateEntityInstanceForm form);

    CommandResult<GetEntityInstanceResult> getEntityInstance(UserVisitPK userVisitPK, GetEntityInstanceForm form);

    CommandResult<GetEntityInstancesResult> getEntityInstances(UserVisitPK userVisitPK, GetEntityInstancesForm form);

    CommandResult<?> deleteEntityInstance(UserVisitPK userVisitPK, DeleteEntityInstanceForm form);

    CommandResult<?> removeEntityInstance(UserVisitPK userVisitPK, RemoveEntityInstanceForm form);

    CommandResult<GenerateUuidResult> generateUuid(UserVisitPK userVisitPK, GenerateUuidForm form);

    // -------------------------------------------------------------------------
    //   Event Types
    // -------------------------------------------------------------------------
    
    CommandResult<?> createEventType(UserVisitPK userVisitPK, CreateEventTypeForm form);

    CommandResult<GetEventTypeResult> getEventType(UserVisitPK userVisitPK, GetEventTypeForm form);

    CommandResult<GetEventTypesResult> getEventTypes(UserVisitPK userVisitPK, GetEventTypesForm form);

    CommandResult<GetEventTypeChoicesResult> getEventTypeChoices(UserVisitPK userVisitPK, GetEventTypeChoicesForm form);

    // -------------------------------------------------------------------------
    //   Event Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createEventTypeDescription(UserVisitPK userVisitPK, CreateEventTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Event Groups
    // --------------------------------------------------------------------------------
    
    CommandResult<GetEventGroupResult> getEventGroup(UserVisitPK userVisitPK, GetEventGroupForm form);
    
    CommandResult<GetEventGroupsResult> getEventGroups(UserVisitPK userVisitPK, GetEventGroupsForm form);
    
    CommandResult<GetEventGroupStatusChoicesResult> getEventGroupStatusChoices(UserVisitPK userVisitPK, GetEventGroupStatusChoicesForm form);
    
    CommandResult<?> setEventGroupStatus(UserVisitPK userVisitPK, SetEventGroupStatusForm form);
    
    // -------------------------------------------------------------------------
    //   Events
    // -------------------------------------------------------------------------

    CommandResult<?> sendEvent(UserVisitPK userVisitPK, SendEventForm form);

    CommandResult<GetEventsResult> getEvents(UserVisitPK userVisitPK, GetEventsForm form);
    
    // -------------------------------------------------------------------------
    //   Queued Events
    // -------------------------------------------------------------------------
    
    CommandResult<?> processQueuedEvents(UserVisitPK userVisitPK);
    
    // -------------------------------------------------------------------------
    //   Components
    // -------------------------------------------------------------------------
    
    CommandResult<?> createComponent(UserVisitPK userVisitPK, CreateComponentForm form);
    
    // -------------------------------------------------------------------------
    //   Component Stages
    // -------------------------------------------------------------------------
    
    CommandResult<?> createComponentStage(UserVisitPK userVisitPK, CreateComponentStageForm form);
    
    // -------------------------------------------------------------------------
    //   Component Versions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createComponentVersion(UserVisitPK userVisitPK, CreateComponentVersionForm form);

    // -------------------------------------------------------------------------
    //   Entity Alias Types
    // -------------------------------------------------------------------------

    CommandResult<CreateEntityAliasTypeResult> createEntityAliasType(UserVisitPK userVisitPK, CreateEntityAliasTypeForm form);

    CommandResult<GetEntityAliasTypeResult> getEntityAliasType(UserVisitPK userVisitPK, GetEntityAliasTypeForm form);

    CommandResult<GetEntityAliasTypesResult> getEntityAliasTypes(UserVisitPK userVisitPK, GetEntityAliasTypesForm form);

    CommandResult<GetEntityAliasTypeChoicesResult> getEntityAliasTypeChoices(UserVisitPK userVisitPK, GetEntityAliasTypeChoicesForm form);

    CommandResult<?> setDefaultEntityAliasType(UserVisitPK userVisitPK, SetDefaultEntityAliasTypeForm form);

    CommandResult<EditEntityAliasTypeResult> editEntityAliasType(UserVisitPK userVisitPK, EditEntityAliasTypeForm form);

    CommandResult<?> deleteEntityAliasType(UserVisitPK userVisitPK, DeleteEntityAliasTypeForm form);

    // -------------------------------------------------------------------------
    //   Entity Alias Type Descriptions
    // -------------------------------------------------------------------------

    CommandResult<?> createEntityAliasTypeDescription(UserVisitPK userVisitPK, CreateEntityAliasTypeDescriptionForm form);

    CommandResult<GetEntityAliasTypeDescriptionResult> getEntityAliasTypeDescription(UserVisitPK userVisitPK, GetEntityAliasTypeDescriptionForm form);

    CommandResult<GetEntityAliasTypeDescriptionsResult> getEntityAliasTypeDescriptions(UserVisitPK userVisitPK, GetEntityAliasTypeDescriptionsForm form);

    CommandResult<EditEntityAliasTypeDescriptionResult> editEntityAliasTypeDescription(UserVisitPK userVisitPK, EditEntityAliasTypeDescriptionForm form);

    CommandResult<?> deleteEntityAliasTypeDescription(UserVisitPK userVisitPK, DeleteEntityAliasTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Entity Aliases
    // --------------------------------------------------------------------------------

    CommandResult<?> createEntityAlias(UserVisitPK userVisitPK, CreateEntityAliasForm form);

    CommandResult<GetEntityAliasResult> getEntityAlias(UserVisitPK userVisitPK, GetEntityAliasForm form);

    CommandResult<GetEntityAliasesResult> getEntityAliases(UserVisitPK userVisitPK, GetEntityAliasesForm form);

    CommandResult<EditEntityAliasResult> editEntityAlias(UserVisitPK userVisitPK, EditEntityAliasForm form);

    CommandResult<?> deleteEntityAlias(UserVisitPK userVisitPK, DeleteEntityAliasForm form);

    // -------------------------------------------------------------------------
    //   Entity Attribute Groups
    // -------------------------------------------------------------------------
    
    CommandResult<CreateEntityAttributeGroupResult> createEntityAttributeGroup(UserVisitPK userVisitPK, CreateEntityAttributeGroupForm form);
    
    CommandResult<GetEntityAttributeGroupResult> getEntityAttributeGroup(UserVisitPK userVisitPK, GetEntityAttributeGroupForm form);
    
    CommandResult<GetEntityAttributeGroupsResult> getEntityAttributeGroups(UserVisitPK userVisitPK, GetEntityAttributeGroupsForm form);
    
    CommandResult<GetEntityAttributeGroupChoicesResult> getEntityAttributeGroupChoices(UserVisitPK userVisitPK, GetEntityAttributeGroupChoicesForm form);
    
    CommandResult<?> setDefaultEntityAttributeGroup(UserVisitPK userVisitPK, SetDefaultEntityAttributeGroupForm form);
    
    CommandResult<EditEntityAttributeGroupResult> editEntityAttributeGroup(UserVisitPK userVisitPK, EditEntityAttributeGroupForm form);
    
    CommandResult<?> deleteEntityAttributeGroup(UserVisitPK userVisitPK, DeleteEntityAttributeGroupForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Attribute Group Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createEntityAttributeGroupDescription(UserVisitPK userVisitPK, CreateEntityAttributeGroupDescriptionForm form);
    
    CommandResult<GetEntityAttributeGroupDescriptionResult> getEntityAttributeGroupDescription(UserVisitPK userVisitPK, GetEntityAttributeGroupDescriptionForm form);
    
    CommandResult<GetEntityAttributeGroupDescriptionsResult> getEntityAttributeGroupDescriptions(UserVisitPK userVisitPK, GetEntityAttributeGroupDescriptionsForm form);
    
    CommandResult<EditEntityAttributeGroupDescriptionResult> editEntityAttributeGroupDescription(UserVisitPK userVisitPK, EditEntityAttributeGroupDescriptionForm form);
    
    CommandResult<?> deleteEntityAttributeGroupDescription(UserVisitPK userVisitPK, DeleteEntityAttributeGroupDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Attribute Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createEntityAttributeDescription(UserVisitPK userVisitPK, CreateEntityAttributeDescriptionForm form);
    
    CommandResult<GetEntityAttributeDescriptionResult> getEntityAttributeDescription(UserVisitPK userVisitPK, GetEntityAttributeDescriptionForm form);
    
    CommandResult<GetEntityAttributeDescriptionsResult> getEntityAttributeDescriptions(UserVisitPK userVisitPK, GetEntityAttributeDescriptionsForm form);
    
    CommandResult<EditEntityAttributeDescriptionResult> editEntityAttributeDescription(UserVisitPK userVisitPK, EditEntityAttributeDescriptionForm form);
    
    CommandResult<?> deleteEntityAttributeDescription(UserVisitPK userVisitPK, DeleteEntityAttributeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Attributes
    // -------------------------------------------------------------------------
    
    CommandResult<CreateEntityAttributeResult> createEntityAttribute(UserVisitPK userVisitPK, CreateEntityAttributeForm form);
    
    CommandResult<GetEntityAttributeResult> getEntityAttribute(UserVisitPK userVisitPK, GetEntityAttributeForm form);
    
    CommandResult<GetEntityAttributesResult> getEntityAttributes(UserVisitPK userVisitPK, GetEntityAttributesForm form);
    
    CommandResult<EditEntityAttributeResult> editEntityAttribute(UserVisitPK userVisitPK, EditEntityAttributeForm form);
    
    CommandResult<?> deleteEntityAttribute(UserVisitPK userVisitPK, DeleteEntityAttributeForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Attribute Entity Attribute Groups
    // -------------------------------------------------------------------------
    
    CommandResult<?> createEntityAttributeEntityAttributeGroup(UserVisitPK userVisitPK, CreateEntityAttributeEntityAttributeGroupForm form);
    
    CommandResult<GetEntityAttributeEntityAttributeGroupResult> getEntityAttributeEntityAttributeGroup(UserVisitPK userVisitPK, GetEntityAttributeEntityAttributeGroupForm form);
    
    CommandResult<GetEntityAttributeEntityAttributeGroupsResult> getEntityAttributeEntityAttributeGroups(UserVisitPK userVisitPK, GetEntityAttributeEntityAttributeGroupsForm form);
    
    CommandResult<EditEntityAttributeEntityAttributeGroupResult> editEntityAttributeEntityAttributeGroup(UserVisitPK userVisitPK, EditEntityAttributeEntityAttributeGroupForm form);
    
    CommandResult<?> deleteEntityAttributeEntityAttributeGroup(UserVisitPK userVisitPK, DeleteEntityAttributeEntityAttributeGroupForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Attribute Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createEntityAttributeTypeDescription(UserVisitPK userVisitPK, CreateEntityAttributeTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Attribute Types
    // -------------------------------------------------------------------------
    
    CommandResult<?> createEntityAttributeType(UserVisitPK userVisitPK, CreateEntityAttributeTypeForm form);
    
    CommandResult<GetEntityAttributeTypeResult> getEntityAttributeType(UserVisitPK userVisitPK, GetEntityAttributeTypeForm form);
    
    CommandResult<GetEntityAttributeTypesResult> getEntityAttributeTypes(UserVisitPK userVisitPK, GetEntityAttributeTypesForm form);
    
    CommandResult<GetEntityAttributeTypeChoicesResult> getEntityAttributeTypeChoices(UserVisitPK userVisitPK, GetEntityAttributeTypeChoicesForm form);
    
    // -------------------------------------------------------------------------
    //   Entity List Item Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createEntityListItemDescription(UserVisitPK userVisitPK, CreateEntityListItemDescriptionForm form);
    
    CommandResult<GetEntityListItemDescriptionResult> getEntityListItemDescription(UserVisitPK userVisitPK, GetEntityListItemDescriptionForm form);
    
    CommandResult<GetEntityListItemDescriptionsResult> getEntityListItemDescriptions(UserVisitPK userVisitPK, GetEntityListItemDescriptionsForm form);
    
    CommandResult<EditEntityListItemDescriptionResult> editEntityListItemDescription(UserVisitPK userVisitPK, EditEntityListItemDescriptionForm form);
    
    CommandResult<?> deleteEntityListItemDescription(UserVisitPK userVisitPK, DeleteEntityListItemDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Entity List Items
    // -------------------------------------------------------------------------
    
    CommandResult<CreateEntityListItemResult> createEntityListItem(UserVisitPK userVisitPK, CreateEntityListItemForm form);
    
    CommandResult<GetEntityListItemResult> getEntityListItem(UserVisitPK userVisitPK, GetEntityListItemForm form);
    
    CommandResult<GetEntityListItemsResult> getEntityListItems(UserVisitPK userVisitPK, GetEntityListItemsForm form);
    
    CommandResult<GetEntityListItemChoicesResult> getEntityListItemChoices(UserVisitPK userVisitPK, GetEntityListItemChoicesForm form);
    
    CommandResult<?> setDefaultEntityListItem(UserVisitPK userVisitPK, SetDefaultEntityListItemForm form);
    
    CommandResult<EditEntityListItemResult> editEntityListItem(UserVisitPK userVisitPK, EditEntityListItemForm form);
    
    CommandResult<?> deleteEntityListItem(UserVisitPK userVisitPK, DeleteEntityListItemForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Integer Range Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createEntityIntegerRangeDescription(UserVisitPK userVisitPK, CreateEntityIntegerRangeDescriptionForm form);
    
    CommandResult<GetEntityIntegerRangeDescriptionResult> getEntityIntegerRangeDescription(UserVisitPK userVisitPK, GetEntityIntegerRangeDescriptionForm form);
    
    CommandResult<GetEntityIntegerRangeDescriptionsResult> getEntityIntegerRangeDescriptions(UserVisitPK userVisitPK, GetEntityIntegerRangeDescriptionsForm form);
    
    CommandResult<EditEntityIntegerRangeDescriptionResult> editEntityIntegerRangeDescription(UserVisitPK userVisitPK, EditEntityIntegerRangeDescriptionForm form);
    
    CommandResult<?> deleteEntityIntegerRangeDescription(UserVisitPK userVisitPK, DeleteEntityIntegerRangeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Integer Ranges
    // -------------------------------------------------------------------------
    
    CommandResult<?> createEntityIntegerRange(UserVisitPK userVisitPK, CreateEntityIntegerRangeForm form);
    
    CommandResult<GetEntityIntegerRangeResult> getEntityIntegerRange(UserVisitPK userVisitPK, GetEntityIntegerRangeForm form);
    
    CommandResult<GetEntityIntegerRangesResult> getEntityIntegerRanges(UserVisitPK userVisitPK, GetEntityIntegerRangesForm form);
    
    CommandResult<GetEntityIntegerRangeChoicesResult> getEntityIntegerRangeChoices(UserVisitPK userVisitPK, GetEntityIntegerRangeChoicesForm form);
    
    CommandResult<?> setDefaultEntityIntegerRange(UserVisitPK userVisitPK, SetDefaultEntityIntegerRangeForm form);
    
    CommandResult<EditEntityIntegerRangeResult> editEntityIntegerRange(UserVisitPK userVisitPK, EditEntityIntegerRangeForm form);
    
    CommandResult<?> deleteEntityIntegerRange(UserVisitPK userVisitPK, DeleteEntityIntegerRangeForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Long Range Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createEntityLongRangeDescription(UserVisitPK userVisitPK, CreateEntityLongRangeDescriptionForm form);
    
    CommandResult<GetEntityLongRangeDescriptionResult> getEntityLongRangeDescription(UserVisitPK userVisitPK, GetEntityLongRangeDescriptionForm form);
    
    CommandResult<GetEntityLongRangeDescriptionsResult> getEntityLongRangeDescriptions(UserVisitPK userVisitPK, GetEntityLongRangeDescriptionsForm form);
    
    CommandResult<EditEntityLongRangeDescriptionResult> editEntityLongRangeDescription(UserVisitPK userVisitPK, EditEntityLongRangeDescriptionForm form);
    
    CommandResult<?> deleteEntityLongRangeDescription(UserVisitPK userVisitPK, DeleteEntityLongRangeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Long Ranges
    // -------------------------------------------------------------------------
    
    CommandResult<?> createEntityLongRange(UserVisitPK userVisitPK, CreateEntityLongRangeForm form);
    
    CommandResult<GetEntityLongRangeResult> getEntityLongRange(UserVisitPK userVisitPK, GetEntityLongRangeForm form);
    
    CommandResult<GetEntityLongRangesResult> getEntityLongRanges(UserVisitPK userVisitPK, GetEntityLongRangesForm form);
    
    CommandResult<GetEntityLongRangeChoicesResult> getEntityLongRangeChoices(UserVisitPK userVisitPK, GetEntityLongRangeChoicesForm form);
    
    CommandResult<?> setDefaultEntityLongRange(UserVisitPK userVisitPK, SetDefaultEntityLongRangeForm form);
    
    CommandResult<EditEntityLongRangeResult> editEntityLongRange(UserVisitPK userVisitPK, EditEntityLongRangeForm form);
    
    CommandResult<?> deleteEntityLongRange(UserVisitPK userVisitPK, DeleteEntityLongRangeForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createEntityTypeDescription(UserVisitPK userVisitPK, CreateEntityTypeDescriptionForm form);
    
    CommandResult<GetEntityTypeDescriptionResult> getEntityTypeDescription(UserVisitPK userVisitPK, GetEntityTypeDescriptionForm form);
    
    CommandResult<GetEntityTypeDescriptionsResult> getEntityTypeDescriptions(UserVisitPK userVisitPK, GetEntityTypeDescriptionsForm form);
    
    CommandResult<EditEntityTypeDescriptionResult> editEntityTypeDescription(UserVisitPK userVisitPK, EditEntityTypeDescriptionForm form);
    
    CommandResult<?> deleteEntityTypeDescription(UserVisitPK userVisitPK, DeleteEntityTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Mime Type Usage Types
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createMimeTypeUsageType(UserVisitPK userVisitPK, CreateMimeTypeUsageTypeForm form);
    
    CommandResult<GetMimeTypeUsageTypeResult> getMimeTypeUsageType(UserVisitPK userVisitPK, GetMimeTypeUsageTypeForm form);

    CommandResult<GetMimeTypeUsageTypesResult> getMimeTypeUsageTypes(UserVisitPK userVisitPK, GetMimeTypeUsageTypesForm form);

    CommandResult<GetMimeTypeUsageTypeChoicesResult> getMimeTypeUsageTypeChoices(UserVisitPK userVisitPK, GetMimeTypeUsageTypeChoicesForm form);
    
    // --------------------------------------------------------------------------------
    //   Mime Type Usage Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createMimeTypeUsageTypeDescription(UserVisitPK userVisitPK, CreateMimeTypeUsageTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Mime Types
    // -------------------------------------------------------------------------

    CommandResult<?> createMimeType(UserVisitPK userVisitPK, CreateMimeTypeForm form);

    CommandResult<GetMimeTypesResult> getMimeTypes(UserVisitPK userVisitPK, GetMimeTypesForm form);

    CommandResult<GetMimeTypeResult> getMimeType(UserVisitPK userVisitPK, GetMimeTypeForm form);

    CommandResult<GetMimeTypeChoicesResult> getMimeTypeChoices(UserVisitPK userVisitPK, GetMimeTypeChoicesForm form);

    CommandResult<?> setDefaultMimeType(UserVisitPK userVisitPK, SetDefaultMimeTypeForm form);

    CommandResult<EditMimeTypeResult> editMimeType(UserVisitPK userVisitPK, EditMimeTypeForm form);

    CommandResult<?> deleteMimeType(UserVisitPK userVisitPK, DeleteMimeTypeForm form);

    // -------------------------------------------------------------------------
    //   Mime Type Descriptions
    // -------------------------------------------------------------------------

    CommandResult<?> createMimeTypeDescription(UserVisitPK userVisitPK, CreateMimeTypeDescriptionForm form);

    CommandResult<GetMimeTypeDescriptionResult> getMimeTypeDescription(UserVisitPK userVisitPK, GetMimeTypeDescriptionForm form);

    CommandResult<GetMimeTypeDescriptionsResult> getMimeTypeDescriptions(UserVisitPK userVisitPK, GetMimeTypeDescriptionsForm form);

    CommandResult<EditMimeTypeDescriptionResult> editMimeTypeDescription(UserVisitPK userVisitPK, EditMimeTypeDescriptionForm form);

    CommandResult<?> deleteMimeTypeDescription(UserVisitPK userVisitPK, DeleteMimeTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Mime Type Usages
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createMimeTypeUsage(UserVisitPK userVisitPK, CreateMimeTypeUsageForm form);
    
    CommandResult<GetMimeTypeUsagesResult> getMimeTypeUsages(UserVisitPK userVisitPK, GetMimeTypeUsagesForm form);
    
    // --------------------------------------------------------------------------------
    //   Mime Type File Extensions
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createMimeTypeFileExtension(UserVisitPK userVisitPK, CreateMimeTypeFileExtensionForm form);
    
    CommandResult<GetMimeTypeFileExtensionResult> getMimeTypeFileExtension(UserVisitPK userVisitPK, GetMimeTypeFileExtensionForm form);
    
    CommandResult<GetMimeTypeFileExtensionsResult> getMimeTypeFileExtensions(UserVisitPK userVisitPK, GetMimeTypeFileExtensionsForm form);
    
    // --------------------------------------------------------------------------------
    //   Protocols
    // --------------------------------------------------------------------------------

    CommandResult<?> createProtocol(UserVisitPK userVisitPK, CreateProtocolForm form);

    CommandResult<GetProtocolChoicesResult> getProtocolChoices(UserVisitPK userVisitPK, GetProtocolChoicesForm form);

    CommandResult<GetProtocolResult> getProtocol(UserVisitPK userVisitPK, GetProtocolForm form);

    CommandResult<GetProtocolsResult> getProtocols(UserVisitPK userVisitPK, GetProtocolsForm form);

    CommandResult<?> setDefaultProtocol(UserVisitPK userVisitPK, SetDefaultProtocolForm form);

    CommandResult<EditProtocolResult> editProtocol(UserVisitPK userVisitPK, EditProtocolForm form);

    CommandResult<?> deleteProtocol(UserVisitPK userVisitPK, DeleteProtocolForm form);

    // --------------------------------------------------------------------------------
    //   Protocol Descriptions
    // --------------------------------------------------------------------------------

    CommandResult<?> createProtocolDescription(UserVisitPK userVisitPK, CreateProtocolDescriptionForm form);

    CommandResult<GetProtocolDescriptionResult> getProtocolDescription(UserVisitPK userVisitPK, GetProtocolDescriptionForm form);

    CommandResult<GetProtocolDescriptionsResult> getProtocolDescriptions(UserVisitPK userVisitPK, GetProtocolDescriptionsForm form);

    CommandResult<EditProtocolDescriptionResult> editProtocolDescription(UserVisitPK userVisitPK, EditProtocolDescriptionForm form);

    CommandResult<?> deleteProtocolDescription(UserVisitPK userVisitPK, DeleteProtocolDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Services
    // --------------------------------------------------------------------------------

    CommandResult<?> createService(UserVisitPK userVisitPK, CreateServiceForm form);

    CommandResult<GetServiceChoicesResult> getServiceChoices(UserVisitPK userVisitPK, GetServiceChoicesForm form);

    CommandResult<GetServiceResult> getService(UserVisitPK userVisitPK, GetServiceForm form);

    CommandResult<GetServicesResult> getServices(UserVisitPK userVisitPK, GetServicesForm form);

    CommandResult<?> setDefaultService(UserVisitPK userVisitPK, SetDefaultServiceForm form);

    CommandResult<EditServiceResult> editService(UserVisitPK userVisitPK, EditServiceForm form);

    CommandResult<?> deleteService(UserVisitPK userVisitPK, DeleteServiceForm form);

    // --------------------------------------------------------------------------------
    //   Service Descriptions
    // --------------------------------------------------------------------------------

    CommandResult<?> createServiceDescription(UserVisitPK userVisitPK, CreateServiceDescriptionForm form);

    CommandResult<GetServiceDescriptionResult> getServiceDescription(UserVisitPK userVisitPK, GetServiceDescriptionForm form);

    CommandResult<GetServiceDescriptionsResult> getServiceDescriptions(UserVisitPK userVisitPK, GetServiceDescriptionsForm form);

    CommandResult<EditServiceDescriptionResult> editServiceDescription(UserVisitPK userVisitPK, EditServiceDescriptionForm form);

    CommandResult<?> deleteServiceDescription(UserVisitPK userVisitPK, DeleteServiceDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Servers
    // --------------------------------------------------------------------------------

    CommandResult<?> createServer(UserVisitPK userVisitPK, CreateServerForm form);

    CommandResult<GetServerChoicesResult> getServerChoices(UserVisitPK userVisitPK, GetServerChoicesForm form);

    CommandResult<GetServerResult> getServer(UserVisitPK userVisitPK, GetServerForm form);

    CommandResult<GetServersResult> getServers(UserVisitPK userVisitPK, GetServersForm form);

    CommandResult<?> setDefaultServer(UserVisitPK userVisitPK, SetDefaultServerForm form);

    CommandResult<EditServerResult> editServer(UserVisitPK userVisitPK, EditServerForm form);

    CommandResult<?> deleteServer(UserVisitPK userVisitPK, DeleteServerForm form);

    // --------------------------------------------------------------------------------
    //   Server Descriptions
    // --------------------------------------------------------------------------------

    CommandResult<?> createServerDescription(UserVisitPK userVisitPK, CreateServerDescriptionForm form);

    CommandResult<GetServerDescriptionResult> getServerDescription(UserVisitPK userVisitPK, GetServerDescriptionForm form);

    CommandResult<GetServerDescriptionsResult> getServerDescriptions(UserVisitPK userVisitPK, GetServerDescriptionsForm form);

    CommandResult<EditServerDescriptionResult> editServerDescription(UserVisitPK userVisitPK, EditServerDescriptionForm form);

    CommandResult<?> deleteServerDescription(UserVisitPK userVisitPK, DeleteServerDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Server Services
    // --------------------------------------------------------------------------------

    CommandResult<?> createServerService(UserVisitPK userVisitPK, CreateServerServiceForm form);

    CommandResult<GetServerServiceResult> getServerService(UserVisitPK userVisitPK, GetServerServiceForm form);

    CommandResult<GetServerServicesResult> getServerServices(UserVisitPK userVisitPK, GetServerServicesForm form);

    CommandResult<?> deleteServerService(UserVisitPK userVisitPK, DeleteServerServiceForm form);

    // --------------------------------------------------------------------------------
    //   Entity Boolean Defaults
    // --------------------------------------------------------------------------------

    CommandResult<?> createEntityBooleanDefault(UserVisitPK userVisitPK, CreateEntityBooleanDefaultForm form);

    CommandResult<EditEntityBooleanDefaultResult> editEntityBooleanDefault(UserVisitPK userVisitPK, EditEntityBooleanDefaultForm form);

    CommandResult<?> deleteEntityBooleanDefault(UserVisitPK userVisitPK, DeleteEntityBooleanDefaultForm form);

    // --------------------------------------------------------------------------------
    //   Entity Boolean Attributes
    // --------------------------------------------------------------------------------

    CommandResult<?> createEntityBooleanAttribute(UserVisitPK userVisitPK, CreateEntityBooleanAttributeForm form);

    CommandResult<EditEntityBooleanAttributeResult> editEntityBooleanAttribute(UserVisitPK userVisitPK, EditEntityBooleanAttributeForm form);

    CommandResult<?> deleteEntityBooleanAttribute(UserVisitPK userVisitPK, DeleteEntityBooleanAttributeForm form);

    // --------------------------------------------------------------------------------
    //   Entity Integer Defaults
    // --------------------------------------------------------------------------------

    CommandResult<?> createEntityIntegerDefault(UserVisitPK userVisitPK, CreateEntityIntegerDefaultForm form);

    CommandResult<EditEntityIntegerDefaultResult> editEntityIntegerDefault(UserVisitPK userVisitPK, EditEntityIntegerDefaultForm form);

    CommandResult<?> deleteEntityIntegerDefault(UserVisitPK userVisitPK, DeleteEntityIntegerDefaultForm form);

    // --------------------------------------------------------------------------------
    //   Entity Integer Attributes
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createEntityIntegerAttribute(UserVisitPK userVisitPK, CreateEntityIntegerAttributeForm form);
    
    CommandResult<EditEntityIntegerAttributeResult> editEntityIntegerAttribute(UserVisitPK userVisitPK, EditEntityIntegerAttributeForm form);
    
    CommandResult<?> deleteEntityIntegerAttribute(UserVisitPK userVisitPK, DeleteEntityIntegerAttributeForm form);

    // --------------------------------------------------------------------------------
    //   Entity List Item Defaults
    // --------------------------------------------------------------------------------

    CommandResult<?> createEntityListItemDefault(UserVisitPK userVisitPK, CreateEntityListItemDefaultForm form);

    CommandResult<EditEntityListItemDefaultResult> editEntityListItemDefault(UserVisitPK userVisitPK, EditEntityListItemDefaultForm form);

    CommandResult<?> deleteEntityListItemDefault(UserVisitPK userVisitPK, DeleteEntityListItemDefaultForm form);

    // --------------------------------------------------------------------------------
    //   Entity List Item Attributes
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createEntityListItemAttribute(UserVisitPK userVisitPK, CreateEntityListItemAttributeForm form);
    
    CommandResult<EditEntityListItemAttributeResult> editEntityListItemAttribute(UserVisitPK userVisitPK, EditEntityListItemAttributeForm form);
    
    CommandResult<?> deleteEntityListItemAttribute(UserVisitPK userVisitPK, DeleteEntityListItemAttributeForm form);

    // --------------------------------------------------------------------------------
    //   Entity Long Defaults
    // --------------------------------------------------------------------------------

    CommandResult<?> createEntityLongDefault(UserVisitPK userVisitPK, CreateEntityLongDefaultForm form);

    CommandResult<EditEntityLongDefaultResult> editEntityLongDefault(UserVisitPK userVisitPK, EditEntityLongDefaultForm form);

    CommandResult<?> deleteEntityLongDefault(UserVisitPK userVisitPK, DeleteEntityLongDefaultForm form);

    // --------------------------------------------------------------------------------
    //   Entity Long Attributes
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createEntityLongAttribute(UserVisitPK userVisitPK, CreateEntityLongAttributeForm form);
    
    CommandResult<EditEntityLongAttributeResult> editEntityLongAttribute(UserVisitPK userVisitPK, EditEntityLongAttributeForm form);
    
    CommandResult<?> deleteEntityLongAttribute(UserVisitPK userVisitPK, DeleteEntityLongAttributeForm form);

    // --------------------------------------------------------------------------------
    //   Entity Multiple List Item Defaults
    // --------------------------------------------------------------------------------

    CommandResult<?> createEntityMultipleListItemDefault(UserVisitPK userVisitPK, CreateEntityMultipleListItemDefaultForm form);

    CommandResult<?> deleteEntityMultipleListItemDefault(UserVisitPK userVisitPK, DeleteEntityMultipleListItemDefaultForm form);

    // --------------------------------------------------------------------------------
    //   Entity Multiple List Item Attributes
    // --------------------------------------------------------------------------------

    CommandResult<?> createEntityMultipleListItemAttribute(UserVisitPK userVisitPK, CreateEntityMultipleListItemAttributeForm form);

    CommandResult<?> deleteEntityMultipleListItemAttribute(UserVisitPK userVisitPK, DeleteEntityMultipleListItemAttributeForm form);

    // --------------------------------------------------------------------------------
    //   Entity Name Attributes
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createEntityNameAttribute(UserVisitPK userVisitPK, CreateEntityNameAttributeForm form);
    
    CommandResult<EditEntityNameAttributeResult> editEntityNameAttribute(UserVisitPK userVisitPK, EditEntityNameAttributeForm form);
    
    CommandResult<?> deleteEntityNameAttribute(UserVisitPK userVisitPK, DeleteEntityNameAttributeForm form);

    // --------------------------------------------------------------------------------
    //   Entity String Defaults
    // --------------------------------------------------------------------------------

    CommandResult<?> createEntityStringDefault(UserVisitPK userVisitPK, CreateEntityStringDefaultForm form);

    CommandResult<EditEntityStringDefaultResult> editEntityStringDefault(UserVisitPK userVisitPK, EditEntityStringDefaultForm form);

    CommandResult<?> deleteEntityStringDefault(UserVisitPK userVisitPK, DeleteEntityStringDefaultForm form);

    // --------------------------------------------------------------------------------
    //   Entity String Attributes
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createEntityStringAttribute(UserVisitPK userVisitPK, CreateEntityStringAttributeForm form);
    
    CommandResult<EditEntityStringAttributeResult> editEntityStringAttribute(UserVisitPK userVisitPK, EditEntityStringAttributeForm form);
    
    CommandResult<?> deleteEntityStringAttribute(UserVisitPK userVisitPK, DeleteEntityStringAttributeForm form);

    // --------------------------------------------------------------------------------
    //   Entity Geo Point Defaults
    // --------------------------------------------------------------------------------

    CommandResult<?> createEntityGeoPointDefault(UserVisitPK userVisitPK, CreateEntityGeoPointDefaultForm form);

    CommandResult<EditEntityGeoPointDefaultResult> editEntityGeoPointDefault(UserVisitPK userVisitPK, EditEntityGeoPointDefaultForm form);

    CommandResult<?> deleteEntityGeoPointDefault(UserVisitPK userVisitPK, DeleteEntityGeoPointDefaultForm form);

    // --------------------------------------------------------------------------------
    //   Entity Geo Point Attributes
    // --------------------------------------------------------------------------------

    CommandResult<?> createEntityGeoPointAttribute(UserVisitPK userVisitPK, CreateEntityGeoPointAttributeForm form);

    CommandResult<EditEntityGeoPointAttributeResult> editEntityGeoPointAttribute(UserVisitPK userVisitPK, EditEntityGeoPointAttributeForm form);

    CommandResult<?> deleteEntityGeoPointAttribute(UserVisitPK userVisitPK, DeleteEntityGeoPointAttributeForm form);

    // --------------------------------------------------------------------------------
    //   Entity Blob Attributes
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createEntityBlobAttribute(UserVisitPK userVisitPK, CreateEntityBlobAttributeForm form);

    CommandResult<EditEntityBlobAttributeResult> editEntityBlobAttribute(UserVisitPK userVisitPK, EditEntityBlobAttributeForm form);

    CommandResult<GetEntityBlobAttributeResult> getEntityBlobAttribute(UserVisitPK userVisitPK, GetEntityBlobAttributeForm form);

    CommandResult<?> deleteEntityBlobAttribute(UserVisitPK userVisitPK, DeleteEntityBlobAttributeForm form);
    
    // --------------------------------------------------------------------------------
    //   Entity Clob Attributes
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createEntityClobAttribute(UserVisitPK userVisitPK, CreateEntityClobAttributeForm form);

    CommandResult<EditEntityClobAttributeResult> editEntityClobAttribute(UserVisitPK userVisitPK, EditEntityClobAttributeForm form);

    CommandResult<GetEntityClobAttributeResult> getEntityClobAttribute(UserVisitPK userVisitPK, GetEntityClobAttributeForm form);

    CommandResult<?> deleteEntityClobAttribute(UserVisitPK userVisitPK, DeleteEntityClobAttributeForm form);

    // --------------------------------------------------------------------------------
    //   Entity Time Defaults
    // --------------------------------------------------------------------------------

    CommandResult<?> createEntityTimeDefault(UserVisitPK userVisitPK, CreateEntityTimeDefaultForm form);

    CommandResult<EditEntityTimeDefaultResult> editEntityTimeDefault(UserVisitPK userVisitPK, EditEntityTimeDefaultForm form);

    CommandResult<?> deleteEntityTimeDefault(UserVisitPK userVisitPK, DeleteEntityTimeDefaultForm form);

    // --------------------------------------------------------------------------------
    //   Entity Time Attributes
    // --------------------------------------------------------------------------------

    CommandResult<?> createEntityTimeAttribute(UserVisitPK userVisitPK, CreateEntityTimeAttributeForm form);

    CommandResult<EditEntityTimeAttributeResult> editEntityTimeAttribute(UserVisitPK userVisitPK, EditEntityTimeAttributeForm form);

    CommandResult<?> deleteEntityTimeAttribute(UserVisitPK userVisitPK, DeleteEntityTimeAttributeForm form);

    // --------------------------------------------------------------------------------
    //   Entity Date Defaults
    // --------------------------------------------------------------------------------

    CommandResult<?> createEntityDateDefault(UserVisitPK userVisitPK, CreateEntityDateDefaultForm form);

    CommandResult<EditEntityDateDefaultResult> editEntityDateDefault(UserVisitPK userVisitPK, EditEntityDateDefaultForm form);

    CommandResult<?> deleteEntityDateDefault(UserVisitPK userVisitPK, DeleteEntityDateDefaultForm form);

    // --------------------------------------------------------------------------------
    //   Entity Date Attributes
    // --------------------------------------------------------------------------------

    CommandResult<?> createEntityDateAttribute(UserVisitPK userVisitPK, CreateEntityDateAttributeForm form);

    CommandResult<EditEntityDateAttributeResult> editEntityDateAttribute(UserVisitPK userVisitPK, EditEntityDateAttributeForm form);

    CommandResult<?> deleteEntityDateAttribute(UserVisitPK userVisitPK, DeleteEntityDateAttributeForm form);

    // --------------------------------------------------------------------------------
    //   Entity Attribute Entity Types
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createEntityAttributeEntityType(UserVisitPK userVisitPK, CreateEntityAttributeEntityTypeForm form);

    CommandResult<GetEntityAttributeEntityTypeResult> getEntityAttributeEntityType(UserVisitPK userVisitPK, GetEntityAttributeEntityTypeForm form);

    CommandResult<GetEntityAttributeEntityTypesResult> getEntityAttributeEntityTypes(UserVisitPK userVisitPK, GetEntityAttributeEntityTypesForm form);
    
    CommandResult<?> deleteEntityAttributeEntityType(UserVisitPK userVisitPK, DeleteEntityAttributeEntityTypeForm form);

    // --------------------------------------------------------------------------------
    //   Entity Entity Attributes
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createEntityEntityAttribute(UserVisitPK userVisitPK, CreateEntityEntityAttributeForm form);
    
    CommandResult<EditEntityEntityAttributeResult> editEntityEntityAttribute(UserVisitPK userVisitPK, EditEntityEntityAttributeForm form);
    
    CommandResult<?> deleteEntityEntityAttribute(UserVisitPK userVisitPK, DeleteEntityEntityAttributeForm form);

    // --------------------------------------------------------------------------------
    //   Entity Collection Attributes
    // --------------------------------------------------------------------------------

    CommandResult<?> createEntityCollectionAttribute(UserVisitPK userVisitPK, CreateEntityCollectionAttributeForm form);

    CommandResult<?> deleteEntityCollectionAttribute(UserVisitPK userVisitPK, DeleteEntityCollectionAttributeForm form);

    // --------------------------------------------------------------------------------
    //   Entity Workflow Attributes
    // --------------------------------------------------------------------------------

    CommandResult<?> createEntityWorkflowAttribute(UserVisitPK userVisitPK, CreateEntityWorkflowAttributeForm form);

    CommandResult<?> setEntityWorkflowAttributeStatus(UserVisitPK userVisitPK, SetEntityWorkflowAttributeStatusForm form);

    CommandResult<?> deleteEntityWorkflowAttribute(UserVisitPK userVisitPK, DeleteEntityWorkflowAttributeForm form);

    // -------------------------------------------------------------------------
    //   Cache Entries
    // -------------------------------------------------------------------------

    CommandResult<?> createCacheEntry(UserVisitPK userVisitPK, CreateCacheEntryForm form);

    CommandResult<GetCacheEntryResult> getCacheEntry(UserVisitPK userVisitPK, GetCacheEntryForm form);

    CommandResult<GetCacheEntriesResult> getCacheEntries(UserVisitPK userVisitPK, GetCacheEntriesForm form);

    CommandResult<?> removeCacheEntry(UserVisitPK userVisitPK, RemoveCacheEntryForm form);

    // -------------------------------------------------------------------------
    //   Cache Entry Dependencies
    // -------------------------------------------------------------------------

    CommandResult<GetCacheEntryDependenciesResult> getCacheEntryDependencies(UserVisitPK userVisitPK, GetCacheEntryDependenciesForm form);

    // --------------------------------------------------------------------------------
    //   Applications
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createApplication(UserVisitPK userVisitPK, CreateApplicationForm form);
    
    CommandResult<GetApplicationChoicesResult> getApplicationChoices(UserVisitPK userVisitPK, GetApplicationChoicesForm form);
    
    CommandResult<GetApplicationResult> getApplication(UserVisitPK userVisitPK, GetApplicationForm form);
    
    CommandResult<GetApplicationsResult> getApplications(UserVisitPK userVisitPK, GetApplicationsForm form);
    
    CommandResult<?> setDefaultApplication(UserVisitPK userVisitPK, SetDefaultApplicationForm form);
    
    CommandResult<EditApplicationResult> editApplication(UserVisitPK userVisitPK, EditApplicationForm form);
    
    CommandResult<?> deleteApplication(UserVisitPK userVisitPK, DeleteApplicationForm form);
    
    // --------------------------------------------------------------------------------
    //   Application Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createApplicationDescription(UserVisitPK userVisitPK, CreateApplicationDescriptionForm form);
    
    CommandResult<GetApplicationDescriptionResult> getApplicationDescription(UserVisitPK userVisitPK, GetApplicationDescriptionForm form);
    
    CommandResult<GetApplicationDescriptionsResult> getApplicationDescriptions(UserVisitPK userVisitPK, GetApplicationDescriptionsForm form);
    
    CommandResult<EditApplicationDescriptionResult> editApplicationDescription(UserVisitPK userVisitPK, EditApplicationDescriptionForm form);
    
    CommandResult<?> deleteApplicationDescription(UserVisitPK userVisitPK, DeleteApplicationDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Editors
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createEditor(UserVisitPK userVisitPK, CreateEditorForm form);
    
    CommandResult<GetEditorChoicesResult> getEditorChoices(UserVisitPK userVisitPK, GetEditorChoicesForm form);
    
    CommandResult<GetEditorResult> getEditor(UserVisitPK userVisitPK, GetEditorForm form);
    
    CommandResult<GetEditorsResult> getEditors(UserVisitPK userVisitPK, GetEditorsForm form);
    
    CommandResult<?> setDefaultEditor(UserVisitPK userVisitPK, SetDefaultEditorForm form);
    
    CommandResult<EditEditorResult> editEditor(UserVisitPK userVisitPK, EditEditorForm form);
    
    CommandResult<?> deleteEditor(UserVisitPK userVisitPK, DeleteEditorForm form);
    
    // --------------------------------------------------------------------------------
    //   Editor Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createEditorDescription(UserVisitPK userVisitPK, CreateEditorDescriptionForm form);
    
    CommandResult<GetEditorDescriptionResult> getEditorDescription(UserVisitPK userVisitPK, GetEditorDescriptionForm form);
    
    CommandResult<GetEditorDescriptionsResult> getEditorDescriptions(UserVisitPK userVisitPK, GetEditorDescriptionsForm form);
    
    CommandResult<EditEditorDescriptionResult> editEditorDescription(UserVisitPK userVisitPK, EditEditorDescriptionForm form);
    
    CommandResult<?> deleteEditorDescription(UserVisitPK userVisitPK, DeleteEditorDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Application Editors
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createApplicationEditor(UserVisitPK userVisitPK, CreateApplicationEditorForm form);
    
    CommandResult<GetApplicationEditorChoicesResult> getApplicationEditorChoices(UserVisitPK userVisitPK, GetApplicationEditorChoicesForm form);
    
    CommandResult<GetApplicationEditorResult> getApplicationEditor(UserVisitPK userVisitPK, GetApplicationEditorForm form);
    
    CommandResult<GetApplicationEditorsResult> getApplicationEditors(UserVisitPK userVisitPK, GetApplicationEditorsForm form);
    
    CommandResult<?> setDefaultApplicationEditor(UserVisitPK userVisitPK, SetDefaultApplicationEditorForm form);
    
    CommandResult<EditApplicationEditorResult> editApplicationEditor(UserVisitPK userVisitPK, EditApplicationEditorForm form);
    
    CommandResult<?> deleteApplicationEditor(UserVisitPK userVisitPK, DeleteApplicationEditorForm form);
    
    // --------------------------------------------------------------------------------
    //   Application Editor Uses
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createApplicationEditorUse(UserVisitPK userVisitPK, CreateApplicationEditorUseForm form);
    
    CommandResult<GetApplicationEditorUseChoicesResult> getApplicationEditorUseChoices(UserVisitPK userVisitPK, GetApplicationEditorUseChoicesForm form);
    
    CommandResult<GetApplicationEditorUseResult> getApplicationEditorUse(UserVisitPK userVisitPK, GetApplicationEditorUseForm form);
    
    CommandResult<GetApplicationEditorUsesResult> getApplicationEditorUses(UserVisitPK userVisitPK, GetApplicationEditorUsesForm form);
    
    CommandResult<?> setDefaultApplicationEditorUse(UserVisitPK userVisitPK, SetDefaultApplicationEditorUseForm form);
    
    CommandResult<EditApplicationEditorUseResult> editApplicationEditorUse(UserVisitPK userVisitPK, EditApplicationEditorUseForm form);
    
    CommandResult<?> deleteApplicationEditorUse(UserVisitPK userVisitPK, DeleteApplicationEditorUseForm form);
    
    // --------------------------------------------------------------------------------
    //   Application Editor Use Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createApplicationEditorUseDescription(UserVisitPK userVisitPK, CreateApplicationEditorUseDescriptionForm form);
    
    CommandResult<GetApplicationEditorUseDescriptionResult> getApplicationEditorUseDescription(UserVisitPK userVisitPK, GetApplicationEditorUseDescriptionForm form);
    
    CommandResult<GetApplicationEditorUseDescriptionsResult> getApplicationEditorUseDescriptions(UserVisitPK userVisitPK, GetApplicationEditorUseDescriptionsForm form);
    
    CommandResult<EditApplicationEditorUseDescriptionResult> editApplicationEditorUseDescription(UserVisitPK userVisitPK, EditApplicationEditorUseDescriptionForm form);
    
    CommandResult<?> deleteApplicationEditorUseDescription(UserVisitPK userVisitPK, DeleteApplicationEditorUseDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Appearances
    // --------------------------------------------------------------------------------
    
    CommandResult<CreateAppearanceResult> createAppearance(UserVisitPK userVisitPK, CreateAppearanceForm form);
    
    CommandResult<GetAppearanceChoicesResult> getAppearanceChoices(UserVisitPK userVisitPK, GetAppearanceChoicesForm form);
    
    CommandResult<GetAppearanceResult> getAppearance(UserVisitPK userVisitPK, GetAppearanceForm form);
    
    CommandResult<GetAppearancesResult> getAppearances(UserVisitPK userVisitPK, GetAppearancesForm form);
    
    CommandResult<?> setDefaultAppearance(UserVisitPK userVisitPK, SetDefaultAppearanceForm form);
    
    CommandResult<EditAppearanceResult> editAppearance(UserVisitPK userVisitPK, EditAppearanceForm form);
    
    CommandResult<?> deleteAppearance(UserVisitPK userVisitPK, DeleteAppearanceForm form);
    
    // --------------------------------------------------------------------------------
    //   Appearance Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createAppearanceDescription(UserVisitPK userVisitPK, CreateAppearanceDescriptionForm form);
    
    CommandResult<GetAppearanceDescriptionResult> getAppearanceDescription(UserVisitPK userVisitPK, GetAppearanceDescriptionForm form);
    
    CommandResult<GetAppearanceDescriptionsResult> getAppearanceDescriptions(UserVisitPK userVisitPK, GetAppearanceDescriptionsForm form);
    
    CommandResult<EditAppearanceDescriptionResult> editAppearanceDescription(UserVisitPK userVisitPK, EditAppearanceDescriptionForm form);
    
    CommandResult<?> deleteAppearanceDescription(UserVisitPK userVisitPK, DeleteAppearanceDescriptionForm form);
    
     // --------------------------------------------------------------------------------
    //   Appearance Text Decorations
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createAppearanceTextDecoration(UserVisitPK userVisitPK, CreateAppearanceTextDecorationForm form);
    
    CommandResult<GetAppearanceTextDecorationResult> getAppearanceTextDecoration(UserVisitPK userVisitPK, GetAppearanceTextDecorationForm form);
    
    CommandResult<GetAppearanceTextDecorationsResult> getAppearanceTextDecorations(UserVisitPK userVisitPK, GetAppearanceTextDecorationsForm form);
    
    CommandResult<?> deleteAppearanceTextDecoration(UserVisitPK userVisitPK, DeleteAppearanceTextDecorationForm form);
    
     // --------------------------------------------------------------------------------
    //   Appearance Text Transformations
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createAppearanceTextTransformation(UserVisitPK userVisitPK, CreateAppearanceTextTransformationForm form);
    
    CommandResult<GetAppearanceTextTransformationResult> getAppearanceTextTransformation(UserVisitPK userVisitPK, GetAppearanceTextTransformationForm form);
    
    CommandResult<GetAppearanceTextTransformationsResult> getAppearanceTextTransformations(UserVisitPK userVisitPK, GetAppearanceTextTransformationsForm form);
    
    CommandResult<?> deleteAppearanceTextTransformation(UserVisitPK userVisitPK, DeleteAppearanceTextTransformationForm form);
    
   // --------------------------------------------------------------------------------
    //   Colors
    // --------------------------------------------------------------------------------
    
    CommandResult<CreateColorResult> createColor(UserVisitPK userVisitPK, CreateColorForm form);
    
    CommandResult<GetColorChoicesResult> getColorChoices(UserVisitPK userVisitPK, GetColorChoicesForm form);
    
    CommandResult<GetColorResult> getColor(UserVisitPK userVisitPK, GetColorForm form);
    
    CommandResult<GetColorsResult> getColors(UserVisitPK userVisitPK, GetColorsForm form);
    
    CommandResult<?> setDefaultColor(UserVisitPK userVisitPK, SetDefaultColorForm form);
    
    CommandResult<EditColorResult> editColor(UserVisitPK userVisitPK, EditColorForm form);
    
    CommandResult<?> deleteColor(UserVisitPK userVisitPK, DeleteColorForm form);
    
    // --------------------------------------------------------------------------------
    //   Color Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createColorDescription(UserVisitPK userVisitPK, CreateColorDescriptionForm form);
    
    CommandResult<GetColorDescriptionResult> getColorDescription(UserVisitPK userVisitPK, GetColorDescriptionForm form);
    
    CommandResult<GetColorDescriptionsResult> getColorDescriptions(UserVisitPK userVisitPK, GetColorDescriptionsForm form);
    
    CommandResult<EditColorDescriptionResult> editColorDescription(UserVisitPK userVisitPK, EditColorDescriptionForm form);
    
    CommandResult<?> deleteColorDescription(UserVisitPK userVisitPK, DeleteColorDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Font Styles
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createFontStyle(UserVisitPK userVisitPK, CreateFontStyleForm form);
    
    CommandResult<GetFontStyleChoicesResult> getFontStyleChoices(UserVisitPK userVisitPK, GetFontStyleChoicesForm form);
    
    CommandResult<GetFontStyleResult> getFontStyle(UserVisitPK userVisitPK, GetFontStyleForm form);
    
    CommandResult<GetFontStylesResult> getFontStyles(UserVisitPK userVisitPK, GetFontStylesForm form);
    
    CommandResult<?> setDefaultFontStyle(UserVisitPK userVisitPK, SetDefaultFontStyleForm form);
    
    CommandResult<EditFontStyleResult> editFontStyle(UserVisitPK userVisitPK, EditFontStyleForm form);
    
    CommandResult<?> deleteFontStyle(UserVisitPK userVisitPK, DeleteFontStyleForm form);
    
    // --------------------------------------------------------------------------------
    //   Font Style Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createFontStyleDescription(UserVisitPK userVisitPK, CreateFontStyleDescriptionForm form);
    
    CommandResult<GetFontStyleDescriptionResult> getFontStyleDescription(UserVisitPK userVisitPK, GetFontStyleDescriptionForm form);
    
    CommandResult<GetFontStyleDescriptionsResult> getFontStyleDescriptions(UserVisitPK userVisitPK, GetFontStyleDescriptionsForm form);
    
    CommandResult<EditFontStyleDescriptionResult> editFontStyleDescription(UserVisitPK userVisitPK, EditFontStyleDescriptionForm form);
    
    CommandResult<?> deleteFontStyleDescription(UserVisitPK userVisitPK, DeleteFontStyleDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Font Weights
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createFontWeight(UserVisitPK userVisitPK, CreateFontWeightForm form);
    
    CommandResult<GetFontWeightChoicesResult> getFontWeightChoices(UserVisitPK userVisitPK, GetFontWeightChoicesForm form);
    
    CommandResult<GetFontWeightResult> getFontWeight(UserVisitPK userVisitPK, GetFontWeightForm form);
    
    CommandResult<GetFontWeightsResult> getFontWeights(UserVisitPK userVisitPK, GetFontWeightsForm form);
    
    CommandResult<?> setDefaultFontWeight(UserVisitPK userVisitPK, SetDefaultFontWeightForm form);
    
    CommandResult<EditFontWeightResult> editFontWeight(UserVisitPK userVisitPK, EditFontWeightForm form);
    
    CommandResult<?> deleteFontWeight(UserVisitPK userVisitPK, DeleteFontWeightForm form);
    
    // --------------------------------------------------------------------------------
    //   Font Weight Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createFontWeightDescription(UserVisitPK userVisitPK, CreateFontWeightDescriptionForm form);
    
    CommandResult<GetFontWeightDescriptionResult> getFontWeightDescription(UserVisitPK userVisitPK, GetFontWeightDescriptionForm form);
    
    CommandResult<GetFontWeightDescriptionsResult> getFontWeightDescriptions(UserVisitPK userVisitPK, GetFontWeightDescriptionsForm form);
    
    CommandResult<EditFontWeightDescriptionResult> editFontWeightDescription(UserVisitPK userVisitPK, EditFontWeightDescriptionForm form);
    
    CommandResult<?> deleteFontWeightDescription(UserVisitPK userVisitPK, DeleteFontWeightDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Font Decorations
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createTextDecoration(UserVisitPK userVisitPK, CreateTextDecorationForm form);
    
    CommandResult<GetTextDecorationChoicesResult> getTextDecorationChoices(UserVisitPK userVisitPK, GetTextDecorationChoicesForm form);
    
    CommandResult<GetTextDecorationResult> getTextDecoration(UserVisitPK userVisitPK, GetTextDecorationForm form);
    
    CommandResult<GetTextDecorationsResult> getTextDecorations(UserVisitPK userVisitPK, GetTextDecorationsForm form);
    
    CommandResult<?> setDefaultTextDecoration(UserVisitPK userVisitPK, SetDefaultTextDecorationForm form);
    
    CommandResult<EditTextDecorationResult> editTextDecoration(UserVisitPK userVisitPK, EditTextDecorationForm form);
    
    CommandResult<?> deleteTextDecoration(UserVisitPK userVisitPK, DeleteTextDecorationForm form);
    
    // --------------------------------------------------------------------------------
    //   Font Decoration Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createTextDecorationDescription(UserVisitPK userVisitPK, CreateTextDecorationDescriptionForm form);
    
    CommandResult<GetTextDecorationDescriptionResult> getTextDecorationDescription(UserVisitPK userVisitPK, GetTextDecorationDescriptionForm form);
    
    CommandResult<GetTextDecorationDescriptionsResult> getTextDecorationDescriptions(UserVisitPK userVisitPK, GetTextDecorationDescriptionsForm form);
    
    CommandResult<EditTextDecorationDescriptionResult> editTextDecorationDescription(UserVisitPK userVisitPK, EditTextDecorationDescriptionForm form);
    
    CommandResult<?> deleteTextDecorationDescription(UserVisitPK userVisitPK, DeleteTextDecorationDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Font Transformations
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createTextTransformation(UserVisitPK userVisitPK, CreateTextTransformationForm form);
    
    CommandResult<GetTextTransformationChoicesResult> getTextTransformationChoices(UserVisitPK userVisitPK, GetTextTransformationChoicesForm form);
    
    CommandResult<GetTextTransformationResult> getTextTransformation(UserVisitPK userVisitPK, GetTextTransformationForm form);
    
    CommandResult<GetTextTransformationsResult> getTextTransformations(UserVisitPK userVisitPK, GetTextTransformationsForm form);
    
    CommandResult<?> setDefaultTextTransformation(UserVisitPK userVisitPK, SetDefaultTextTransformationForm form);
    
    CommandResult<EditTextTransformationResult> editTextTransformation(UserVisitPK userVisitPK, EditTextTransformationForm form);
    
    CommandResult<?> deleteTextTransformation(UserVisitPK userVisitPK, DeleteTextTransformationForm form);
    
    // --------------------------------------------------------------------------------
    //   Font Transformation Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createTextTransformationDescription(UserVisitPK userVisitPK, CreateTextTransformationDescriptionForm form);
    
    CommandResult<GetTextTransformationDescriptionResult> getTextTransformationDescription(UserVisitPK userVisitPK, GetTextTransformationDescriptionForm form);
    
    CommandResult<GetTextTransformationDescriptionsResult> getTextTransformationDescriptions(UserVisitPK userVisitPK, GetTextTransformationDescriptionsForm form);
    
    CommandResult<EditTextTransformationDescriptionResult> editTextTransformationDescription(UserVisitPK userVisitPK, EditTextTransformationDescriptionForm form);
    
    CommandResult<?> deleteTextTransformationDescription(UserVisitPK userVisitPK, DeleteTextTransformationDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Entity Appearances
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createEntityAppearance(UserVisitPK userVisitPK, CreateEntityAppearanceForm form);
    
    CommandResult<GetEntityAppearanceResult> getEntityAppearance(UserVisitPK userVisitPK, GetEntityAppearanceForm form);
    
    CommandResult<EditEntityAppearanceResult> editEntityAppearance(UserVisitPK userVisitPK, EditEntityAppearanceForm form);
    
    CommandResult<?> deleteEntityAppearance(UserVisitPK userVisitPK, DeleteEntityAppearanceForm form);
    
    // -------------------------------------------------------------------------
    //   Utilities
    // -------------------------------------------------------------------------

    CommandResult<EncryptResult> encrypt(UserVisitPK userVisitPK, EncryptForm form);

    CommandResult<DecryptResult> decrypt(UserVisitPK userVisitPK, DecryptForm form);

    CommandResult<ValidateResult> validate(UserVisitPK userVisit, ValidateForm form);

}
