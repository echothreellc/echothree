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
import com.echothree.util.common.command.VoidResult;

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
    
    CommandResult<VoidResult> loadBaseKeys(UserVisitPK userVisitPK, LoadBaseKeysForm form);
    
    CommandResult<ChangeBaseKeysResult> changeBaseKeys(UserVisitPK userVisitPK, ChangeBaseKeysForm form);
    
    // -------------------------------------------------------------------------
    //   Base Encryption Keys
    // -------------------------------------------------------------------------
    
    CommandResult<GetBaseEncryptionKeyResult> getBaseEncryptionKey(UserVisitPK userVisitPK, GetBaseEncryptionKeyForm form);
    
    CommandResult<GetBaseEncryptionKeysResult> getBaseEncryptionKeys(UserVisitPK userVisitPK, GetBaseEncryptionKeysForm form);
    
    CommandResult<GetBaseEncryptionKeyStatusChoicesResult> getBaseEncryptionKeyStatusChoices(UserVisitPK userVisitPK, GetBaseEncryptionKeyStatusChoicesForm form);
    
    CommandResult<VoidResult> setBaseEncryptionKeyStatus(UserVisitPK userVisitPK, SetBaseEncryptionKeyStatusForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Locks
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> lockEntity(UserVisitPK userVisitPK, LockEntityForm form);
    
    CommandResult<VoidResult> unlockEntity(UserVisitPK userVisitPK, UnlockEntityForm form);
    
    CommandResult<VoidResult> removedExpiredEntityLocks(UserVisitPK userVisitPK);
    
    // -------------------------------------------------------------------------
    //   Component Vendors
    // -------------------------------------------------------------------------
    
    CommandResult<CreateComponentVendorResult> createComponentVendor(UserVisitPK userVisitPK, CreateComponentVendorForm form);
    
    CommandResult<GetComponentVendorResult> getComponentVendor(UserVisitPK userVisitPK, GetComponentVendorForm form);
    
    CommandResult<GetComponentVendorsResult> getComponentVendors(UserVisitPK userVisitPK, GetComponentVendorsForm form);
    
    CommandResult<EditComponentVendorResult> editComponentVendor(UserVisitPK userVisitPK, EditComponentVendorForm form);
    
    CommandResult<VoidResult> deleteComponentVendor(UserVisitPK userVisitPK, DeleteComponentVendorForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Types
    // -------------------------------------------------------------------------
    
    CommandResult<CreateEntityTypeResult> createEntityType(UserVisitPK userVisitPK, CreateEntityTypeForm form);
    
    CommandResult<GetEntityTypeResult> getEntityType(UserVisitPK userVisitPK, GetEntityTypeForm form);
    
    CommandResult<GetEntityTypesResult> getEntityTypes(UserVisitPK userVisitPK, GetEntityTypesForm form);
    
    CommandResult<EditEntityTypeResult> editEntityType(UserVisitPK userVisitPK, EditEntityTypeForm form);
    
    CommandResult<VoidResult> deleteEntityType(UserVisitPK userVisitPK, DeleteEntityTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Commands
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createCommand(UserVisitPK userVisitPK, CreateCommandForm form);
    
    CommandResult<GetCommandResult> getCommand(UserVisitPK userVisitPK, GetCommandForm form);
    
    CommandResult<GetCommandsResult> getCommands(UserVisitPK userVisitPK, GetCommandsForm form);
    
    CommandResult<EditCommandResult> editCommand(UserVisitPK userVisitPK, EditCommandForm form);
    
    CommandResult<VoidResult> deleteCommand(UserVisitPK userVisitPK, DeleteCommandForm form);
    
    // -------------------------------------------------------------------------
    //   Command Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createCommandDescription(UserVisitPK userVisitPK, CreateCommandDescriptionForm form);
    
    CommandResult<GetCommandDescriptionResult> getCommandDescription(UserVisitPK userVisitPK, GetCommandDescriptionForm form);
    
    CommandResult<GetCommandDescriptionsResult> getCommandDescriptions(UserVisitPK userVisitPK, GetCommandDescriptionsForm form);
    
    CommandResult<EditCommandDescriptionResult> editCommandDescription(UserVisitPK userVisitPK, EditCommandDescriptionForm form);
    
    CommandResult<VoidResult> deleteCommandDescription(UserVisitPK userVisitPK, DeleteCommandDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Command Message Types
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createCommandMessageType(UserVisitPK userVisitPK, CreateCommandMessageTypeForm form);
    
    CommandResult<GetCommandMessageTypeChoicesResult> getCommandMessageTypeChoices(UserVisitPK userVisitPK, GetCommandMessageTypeChoicesForm form);
    
    CommandResult<GetCommandMessageTypeResult> getCommandMessageType(UserVisitPK userVisitPK, GetCommandMessageTypeForm form);
    
    CommandResult<GetCommandMessageTypesResult> getCommandMessageTypes(UserVisitPK userVisitPK, GetCommandMessageTypesForm form);
    
    CommandResult<VoidResult> setDefaultCommandMessageType(UserVisitPK userVisitPK, SetDefaultCommandMessageTypeForm form);
    
    CommandResult<EditCommandMessageTypeResult> editCommandMessageType(UserVisitPK userVisitPK, EditCommandMessageTypeForm form);
    
    CommandResult<VoidResult> deleteCommandMessageType(UserVisitPK userVisitPK, DeleteCommandMessageTypeForm form);
    
    // --------------------------------------------------------------------------------
    //   Command Message Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createCommandMessageTypeDescription(UserVisitPK userVisitPK, CreateCommandMessageTypeDescriptionForm form);
    
    CommandResult<GetCommandMessageTypeDescriptionResult> getCommandMessageTypeDescription(UserVisitPK userVisitPK, GetCommandMessageTypeDescriptionForm form);
    
    CommandResult<GetCommandMessageTypeDescriptionsResult> getCommandMessageTypeDescriptions(UserVisitPK userVisitPK, GetCommandMessageTypeDescriptionsForm form);
    
    CommandResult<EditCommandMessageTypeDescriptionResult> editCommandMessageTypeDescription(UserVisitPK userVisitPK, EditCommandMessageTypeDescriptionForm form);
    
    CommandResult<VoidResult> deleteCommandMessageTypeDescription(UserVisitPK userVisitPK, DeleteCommandMessageTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Command Messages
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createCommandMessage(UserVisitPK userVisitPK, CreateCommandMessageForm form);
    
    CommandResult<GetCommandMessageResult> getCommandMessage(UserVisitPK userVisitPK, GetCommandMessageForm form);

    CommandResult<GetCommandMessagesResult> getCommandMessages(UserVisitPK userVisitPK, GetCommandMessagesForm form);

    CommandResult<EditCommandMessageResult> editCommandMessage(UserVisitPK userVisitPK, EditCommandMessageForm form);

    CommandResult<VoidResult> deleteCommandMessage(UserVisitPK userVisitPK, DeleteCommandMessageForm form);

    // -------------------------------------------------------------------------
    //   Command Message Translations
    // -------------------------------------------------------------------------

    CommandResult<VoidResult> createCommandMessageTranslation(UserVisitPK userVisitPK, CreateCommandMessageTranslationForm form);

    CommandResult<GetCommandMessageTranslationResult> getCommandMessageTranslation(UserVisitPK userVisitPK, GetCommandMessageTranslationForm form);

    CommandResult<GetCommandMessageTranslationsResult> getCommandMessageTranslations(UserVisitPK userVisitPK, GetCommandMessageTranslationsForm form);

    CommandResult<EditCommandMessageTranslationResult> editCommandMessageTranslation(UserVisitPK userVisitPK, EditCommandMessageTranslationForm form);

    CommandResult<VoidResult> deleteCommandMessageTranslation(UserVisitPK userVisitPK, DeleteCommandMessageTranslationForm form);

    // -------------------------------------------------------------------------
    //   Entity Instances
    // -------------------------------------------------------------------------

    CommandResult<CreateEntityInstanceResult> createEntityInstance(UserVisitPK userVisitPK, CreateEntityInstanceForm form);

    CommandResult<GetEntityInstanceResult> getEntityInstance(UserVisitPK userVisitPK, GetEntityInstanceForm form);

    CommandResult<GetEntityInstancesResult> getEntityInstances(UserVisitPK userVisitPK, GetEntityInstancesForm form);

    CommandResult<VoidResult> deleteEntityInstance(UserVisitPK userVisitPK, DeleteEntityInstanceForm form);

    CommandResult<VoidResult> removeEntityInstance(UserVisitPK userVisitPK, RemoveEntityInstanceForm form);

    CommandResult<GenerateUuidResult> generateUuid(UserVisitPK userVisitPK, GenerateUuidForm form);

    // -------------------------------------------------------------------------
    //   Event Types
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createEventType(UserVisitPK userVisitPK, CreateEventTypeForm form);

    CommandResult<GetEventTypeResult> getEventType(UserVisitPK userVisitPK, GetEventTypeForm form);

    CommandResult<GetEventTypesResult> getEventTypes(UserVisitPK userVisitPK, GetEventTypesForm form);

    CommandResult<GetEventTypeChoicesResult> getEventTypeChoices(UserVisitPK userVisitPK, GetEventTypeChoicesForm form);

    // -------------------------------------------------------------------------
    //   Event Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createEventTypeDescription(UserVisitPK userVisitPK, CreateEventTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Event Groups
    // --------------------------------------------------------------------------------
    
    CommandResult<GetEventGroupResult> getEventGroup(UserVisitPK userVisitPK, GetEventGroupForm form);
    
    CommandResult<GetEventGroupsResult> getEventGroups(UserVisitPK userVisitPK, GetEventGroupsForm form);
    
    CommandResult<GetEventGroupStatusChoicesResult> getEventGroupStatusChoices(UserVisitPK userVisitPK, GetEventGroupStatusChoicesForm form);
    
    CommandResult<VoidResult> setEventGroupStatus(UserVisitPK userVisitPK, SetEventGroupStatusForm form);
    
    // -------------------------------------------------------------------------
    //   Events
    // -------------------------------------------------------------------------

    CommandResult<VoidResult> sendEvent(UserVisitPK userVisitPK, SendEventForm form);

    CommandResult<GetEventsResult> getEvents(UserVisitPK userVisitPK, GetEventsForm form);
    
    // -------------------------------------------------------------------------
    //   Queued Events
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> processQueuedEvents(UserVisitPK userVisitPK);
    
    // -------------------------------------------------------------------------
    //   Components
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createComponent(UserVisitPK userVisitPK, CreateComponentForm form);
    
    // -------------------------------------------------------------------------
    //   Component Stages
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createComponentStage(UserVisitPK userVisitPK, CreateComponentStageForm form);
    
    // -------------------------------------------------------------------------
    //   Component Versions
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createComponentVersion(UserVisitPK userVisitPK, CreateComponentVersionForm form);

    // -------------------------------------------------------------------------
    //   Entity Alias Types
    // -------------------------------------------------------------------------

    CommandResult<CreateEntityAliasTypeResult> createEntityAliasType(UserVisitPK userVisitPK, CreateEntityAliasTypeForm form);

    CommandResult<GetEntityAliasTypeResult> getEntityAliasType(UserVisitPK userVisitPK, GetEntityAliasTypeForm form);

    CommandResult<GetEntityAliasTypesResult> getEntityAliasTypes(UserVisitPK userVisitPK, GetEntityAliasTypesForm form);

    CommandResult<GetEntityAliasTypeChoicesResult> getEntityAliasTypeChoices(UserVisitPK userVisitPK, GetEntityAliasTypeChoicesForm form);

    CommandResult<VoidResult> setDefaultEntityAliasType(UserVisitPK userVisitPK, SetDefaultEntityAliasTypeForm form);

    CommandResult<EditEntityAliasTypeResult> editEntityAliasType(UserVisitPK userVisitPK, EditEntityAliasTypeForm form);

    CommandResult<VoidResult> deleteEntityAliasType(UserVisitPK userVisitPK, DeleteEntityAliasTypeForm form);

    // -------------------------------------------------------------------------
    //   Entity Alias Type Descriptions
    // -------------------------------------------------------------------------

    CommandResult<VoidResult> createEntityAliasTypeDescription(UserVisitPK userVisitPK, CreateEntityAliasTypeDescriptionForm form);

    CommandResult<GetEntityAliasTypeDescriptionResult> getEntityAliasTypeDescription(UserVisitPK userVisitPK, GetEntityAliasTypeDescriptionForm form);

    CommandResult<GetEntityAliasTypeDescriptionsResult> getEntityAliasTypeDescriptions(UserVisitPK userVisitPK, GetEntityAliasTypeDescriptionsForm form);

    CommandResult<EditEntityAliasTypeDescriptionResult> editEntityAliasTypeDescription(UserVisitPK userVisitPK, EditEntityAliasTypeDescriptionForm form);

    CommandResult<VoidResult> deleteEntityAliasTypeDescription(UserVisitPK userVisitPK, DeleteEntityAliasTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Entity Aliases
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createEntityAlias(UserVisitPK userVisitPK, CreateEntityAliasForm form);

    CommandResult<GetEntityAliasResult> getEntityAlias(UserVisitPK userVisitPK, GetEntityAliasForm form);

    CommandResult<GetEntityAliasesResult> getEntityAliases(UserVisitPK userVisitPK, GetEntityAliasesForm form);

    CommandResult<EditEntityAliasResult> editEntityAlias(UserVisitPK userVisitPK, EditEntityAliasForm form);

    CommandResult<VoidResult> deleteEntityAlias(UserVisitPK userVisitPK, DeleteEntityAliasForm form);

    // -------------------------------------------------------------------------
    //   Entity Attribute Groups
    // -------------------------------------------------------------------------
    
    CommandResult<CreateEntityAttributeGroupResult> createEntityAttributeGroup(UserVisitPK userVisitPK, CreateEntityAttributeGroupForm form);
    
    CommandResult<GetEntityAttributeGroupResult> getEntityAttributeGroup(UserVisitPK userVisitPK, GetEntityAttributeGroupForm form);
    
    CommandResult<GetEntityAttributeGroupsResult> getEntityAttributeGroups(UserVisitPK userVisitPK, GetEntityAttributeGroupsForm form);
    
    CommandResult<GetEntityAttributeGroupChoicesResult> getEntityAttributeGroupChoices(UserVisitPK userVisitPK, GetEntityAttributeGroupChoicesForm form);
    
    CommandResult<VoidResult> setDefaultEntityAttributeGroup(UserVisitPK userVisitPK, SetDefaultEntityAttributeGroupForm form);
    
    CommandResult<EditEntityAttributeGroupResult> editEntityAttributeGroup(UserVisitPK userVisitPK, EditEntityAttributeGroupForm form);
    
    CommandResult<VoidResult> deleteEntityAttributeGroup(UserVisitPK userVisitPK, DeleteEntityAttributeGroupForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Attribute Group Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createEntityAttributeGroupDescription(UserVisitPK userVisitPK, CreateEntityAttributeGroupDescriptionForm form);
    
    CommandResult<GetEntityAttributeGroupDescriptionResult> getEntityAttributeGroupDescription(UserVisitPK userVisitPK, GetEntityAttributeGroupDescriptionForm form);
    
    CommandResult<GetEntityAttributeGroupDescriptionsResult> getEntityAttributeGroupDescriptions(UserVisitPK userVisitPK, GetEntityAttributeGroupDescriptionsForm form);
    
    CommandResult<EditEntityAttributeGroupDescriptionResult> editEntityAttributeGroupDescription(UserVisitPK userVisitPK, EditEntityAttributeGroupDescriptionForm form);
    
    CommandResult<VoidResult> deleteEntityAttributeGroupDescription(UserVisitPK userVisitPK, DeleteEntityAttributeGroupDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Attribute Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createEntityAttributeDescription(UserVisitPK userVisitPK, CreateEntityAttributeDescriptionForm form);
    
    CommandResult<GetEntityAttributeDescriptionResult> getEntityAttributeDescription(UserVisitPK userVisitPK, GetEntityAttributeDescriptionForm form);
    
    CommandResult<GetEntityAttributeDescriptionsResult> getEntityAttributeDescriptions(UserVisitPK userVisitPK, GetEntityAttributeDescriptionsForm form);
    
    CommandResult<EditEntityAttributeDescriptionResult> editEntityAttributeDescription(UserVisitPK userVisitPK, EditEntityAttributeDescriptionForm form);
    
    CommandResult<VoidResult> deleteEntityAttributeDescription(UserVisitPK userVisitPK, DeleteEntityAttributeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Attributes
    // -------------------------------------------------------------------------
    
    CommandResult<CreateEntityAttributeResult> createEntityAttribute(UserVisitPK userVisitPK, CreateEntityAttributeForm form);
    
    CommandResult<GetEntityAttributeResult> getEntityAttribute(UserVisitPK userVisitPK, GetEntityAttributeForm form);
    
    CommandResult<GetEntityAttributesResult> getEntityAttributes(UserVisitPK userVisitPK, GetEntityAttributesForm form);
    
    CommandResult<EditEntityAttributeResult> editEntityAttribute(UserVisitPK userVisitPK, EditEntityAttributeForm form);
    
    CommandResult<VoidResult> deleteEntityAttribute(UserVisitPK userVisitPK, DeleteEntityAttributeForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Attribute Entity Attribute Groups
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createEntityAttributeEntityAttributeGroup(UserVisitPK userVisitPK, CreateEntityAttributeEntityAttributeGroupForm form);
    
    CommandResult<GetEntityAttributeEntityAttributeGroupResult> getEntityAttributeEntityAttributeGroup(UserVisitPK userVisitPK, GetEntityAttributeEntityAttributeGroupForm form);
    
    CommandResult<GetEntityAttributeEntityAttributeGroupsResult> getEntityAttributeEntityAttributeGroups(UserVisitPK userVisitPK, GetEntityAttributeEntityAttributeGroupsForm form);
    
    CommandResult<EditEntityAttributeEntityAttributeGroupResult> editEntityAttributeEntityAttributeGroup(UserVisitPK userVisitPK, EditEntityAttributeEntityAttributeGroupForm form);
    
    CommandResult<VoidResult> deleteEntityAttributeEntityAttributeGroup(UserVisitPK userVisitPK, DeleteEntityAttributeEntityAttributeGroupForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Attribute Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createEntityAttributeTypeDescription(UserVisitPK userVisitPK, CreateEntityAttributeTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Attribute Types
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createEntityAttributeType(UserVisitPK userVisitPK, CreateEntityAttributeTypeForm form);
    
    CommandResult<GetEntityAttributeTypeResult> getEntityAttributeType(UserVisitPK userVisitPK, GetEntityAttributeTypeForm form);
    
    CommandResult<GetEntityAttributeTypesResult> getEntityAttributeTypes(UserVisitPK userVisitPK, GetEntityAttributeTypesForm form);
    
    CommandResult<GetEntityAttributeTypeChoicesResult> getEntityAttributeTypeChoices(UserVisitPK userVisitPK, GetEntityAttributeTypeChoicesForm form);
    
    // -------------------------------------------------------------------------
    //   Entity List Item Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createEntityListItemDescription(UserVisitPK userVisitPK, CreateEntityListItemDescriptionForm form);
    
    CommandResult<GetEntityListItemDescriptionResult> getEntityListItemDescription(UserVisitPK userVisitPK, GetEntityListItemDescriptionForm form);
    
    CommandResult<GetEntityListItemDescriptionsResult> getEntityListItemDescriptions(UserVisitPK userVisitPK, GetEntityListItemDescriptionsForm form);
    
    CommandResult<EditEntityListItemDescriptionResult> editEntityListItemDescription(UserVisitPK userVisitPK, EditEntityListItemDescriptionForm form);
    
    CommandResult<VoidResult> deleteEntityListItemDescription(UserVisitPK userVisitPK, DeleteEntityListItemDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Entity List Items
    // -------------------------------------------------------------------------
    
    CommandResult<CreateEntityListItemResult> createEntityListItem(UserVisitPK userVisitPK, CreateEntityListItemForm form);
    
    CommandResult<GetEntityListItemResult> getEntityListItem(UserVisitPK userVisitPK, GetEntityListItemForm form);
    
    CommandResult<GetEntityListItemsResult> getEntityListItems(UserVisitPK userVisitPK, GetEntityListItemsForm form);
    
    CommandResult<GetEntityListItemChoicesResult> getEntityListItemChoices(UserVisitPK userVisitPK, GetEntityListItemChoicesForm form);
    
    CommandResult<VoidResult> setDefaultEntityListItem(UserVisitPK userVisitPK, SetDefaultEntityListItemForm form);
    
    CommandResult<EditEntityListItemResult> editEntityListItem(UserVisitPK userVisitPK, EditEntityListItemForm form);
    
    CommandResult<VoidResult> deleteEntityListItem(UserVisitPK userVisitPK, DeleteEntityListItemForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Integer Range Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createEntityIntegerRangeDescription(UserVisitPK userVisitPK, CreateEntityIntegerRangeDescriptionForm form);
    
    CommandResult<GetEntityIntegerRangeDescriptionResult> getEntityIntegerRangeDescription(UserVisitPK userVisitPK, GetEntityIntegerRangeDescriptionForm form);
    
    CommandResult<GetEntityIntegerRangeDescriptionsResult> getEntityIntegerRangeDescriptions(UserVisitPK userVisitPK, GetEntityIntegerRangeDescriptionsForm form);
    
    CommandResult<EditEntityIntegerRangeDescriptionResult> editEntityIntegerRangeDescription(UserVisitPK userVisitPK, EditEntityIntegerRangeDescriptionForm form);
    
    CommandResult<VoidResult> deleteEntityIntegerRangeDescription(UserVisitPK userVisitPK, DeleteEntityIntegerRangeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Integer Ranges
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createEntityIntegerRange(UserVisitPK userVisitPK, CreateEntityIntegerRangeForm form);
    
    CommandResult<GetEntityIntegerRangeResult> getEntityIntegerRange(UserVisitPK userVisitPK, GetEntityIntegerRangeForm form);
    
    CommandResult<GetEntityIntegerRangesResult> getEntityIntegerRanges(UserVisitPK userVisitPK, GetEntityIntegerRangesForm form);
    
    CommandResult<GetEntityIntegerRangeChoicesResult> getEntityIntegerRangeChoices(UserVisitPK userVisitPK, GetEntityIntegerRangeChoicesForm form);
    
    CommandResult<VoidResult> setDefaultEntityIntegerRange(UserVisitPK userVisitPK, SetDefaultEntityIntegerRangeForm form);
    
    CommandResult<EditEntityIntegerRangeResult> editEntityIntegerRange(UserVisitPK userVisitPK, EditEntityIntegerRangeForm form);
    
    CommandResult<VoidResult> deleteEntityIntegerRange(UserVisitPK userVisitPK, DeleteEntityIntegerRangeForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Long Range Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createEntityLongRangeDescription(UserVisitPK userVisitPK, CreateEntityLongRangeDescriptionForm form);
    
    CommandResult<GetEntityLongRangeDescriptionResult> getEntityLongRangeDescription(UserVisitPK userVisitPK, GetEntityLongRangeDescriptionForm form);
    
    CommandResult<GetEntityLongRangeDescriptionsResult> getEntityLongRangeDescriptions(UserVisitPK userVisitPK, GetEntityLongRangeDescriptionsForm form);
    
    CommandResult<EditEntityLongRangeDescriptionResult> editEntityLongRangeDescription(UserVisitPK userVisitPK, EditEntityLongRangeDescriptionForm form);
    
    CommandResult<VoidResult> deleteEntityLongRangeDescription(UserVisitPK userVisitPK, DeleteEntityLongRangeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Long Ranges
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createEntityLongRange(UserVisitPK userVisitPK, CreateEntityLongRangeForm form);
    
    CommandResult<GetEntityLongRangeResult> getEntityLongRange(UserVisitPK userVisitPK, GetEntityLongRangeForm form);
    
    CommandResult<GetEntityLongRangesResult> getEntityLongRanges(UserVisitPK userVisitPK, GetEntityLongRangesForm form);
    
    CommandResult<GetEntityLongRangeChoicesResult> getEntityLongRangeChoices(UserVisitPK userVisitPK, GetEntityLongRangeChoicesForm form);
    
    CommandResult<VoidResult> setDefaultEntityLongRange(UserVisitPK userVisitPK, SetDefaultEntityLongRangeForm form);
    
    CommandResult<EditEntityLongRangeResult> editEntityLongRange(UserVisitPK userVisitPK, EditEntityLongRangeForm form);
    
    CommandResult<VoidResult> deleteEntityLongRange(UserVisitPK userVisitPK, DeleteEntityLongRangeForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createEntityTypeDescription(UserVisitPK userVisitPK, CreateEntityTypeDescriptionForm form);
    
    CommandResult<GetEntityTypeDescriptionResult> getEntityTypeDescription(UserVisitPK userVisitPK, GetEntityTypeDescriptionForm form);
    
    CommandResult<GetEntityTypeDescriptionsResult> getEntityTypeDescriptions(UserVisitPK userVisitPK, GetEntityTypeDescriptionsForm form);
    
    CommandResult<EditEntityTypeDescriptionResult> editEntityTypeDescription(UserVisitPK userVisitPK, EditEntityTypeDescriptionForm form);
    
    CommandResult<VoidResult> deleteEntityTypeDescription(UserVisitPK userVisitPK, DeleteEntityTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Mime Type Usage Types
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createMimeTypeUsageType(UserVisitPK userVisitPK, CreateMimeTypeUsageTypeForm form);
    
    CommandResult<GetMimeTypeUsageTypeResult> getMimeTypeUsageType(UserVisitPK userVisitPK, GetMimeTypeUsageTypeForm form);

    CommandResult<GetMimeTypeUsageTypesResult> getMimeTypeUsageTypes(UserVisitPK userVisitPK, GetMimeTypeUsageTypesForm form);

    CommandResult<GetMimeTypeUsageTypeChoicesResult> getMimeTypeUsageTypeChoices(UserVisitPK userVisitPK, GetMimeTypeUsageTypeChoicesForm form);
    
    // --------------------------------------------------------------------------------
    //   Mime Type Usage Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createMimeTypeUsageTypeDescription(UserVisitPK userVisitPK, CreateMimeTypeUsageTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Mime Types
    // -------------------------------------------------------------------------

    CommandResult<VoidResult> createMimeType(UserVisitPK userVisitPK, CreateMimeTypeForm form);

    CommandResult<GetMimeTypesResult> getMimeTypes(UserVisitPK userVisitPK, GetMimeTypesForm form);

    CommandResult<GetMimeTypeResult> getMimeType(UserVisitPK userVisitPK, GetMimeTypeForm form);

    CommandResult<GetMimeTypeChoicesResult> getMimeTypeChoices(UserVisitPK userVisitPK, GetMimeTypeChoicesForm form);

    CommandResult<VoidResult> setDefaultMimeType(UserVisitPK userVisitPK, SetDefaultMimeTypeForm form);

    CommandResult<EditMimeTypeResult> editMimeType(UserVisitPK userVisitPK, EditMimeTypeForm form);

    CommandResult<VoidResult> deleteMimeType(UserVisitPK userVisitPK, DeleteMimeTypeForm form);

    // -------------------------------------------------------------------------
    //   Mime Type Descriptions
    // -------------------------------------------------------------------------

    CommandResult<VoidResult> createMimeTypeDescription(UserVisitPK userVisitPK, CreateMimeTypeDescriptionForm form);

    CommandResult<GetMimeTypeDescriptionResult> getMimeTypeDescription(UserVisitPK userVisitPK, GetMimeTypeDescriptionForm form);

    CommandResult<GetMimeTypeDescriptionsResult> getMimeTypeDescriptions(UserVisitPK userVisitPK, GetMimeTypeDescriptionsForm form);

    CommandResult<EditMimeTypeDescriptionResult> editMimeTypeDescription(UserVisitPK userVisitPK, EditMimeTypeDescriptionForm form);

    CommandResult<VoidResult> deleteMimeTypeDescription(UserVisitPK userVisitPK, DeleteMimeTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Mime Type Usages
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createMimeTypeUsage(UserVisitPK userVisitPK, CreateMimeTypeUsageForm form);
    
    CommandResult<GetMimeTypeUsagesResult> getMimeTypeUsages(UserVisitPK userVisitPK, GetMimeTypeUsagesForm form);
    
    // --------------------------------------------------------------------------------
    //   Mime Type File Extensions
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createMimeTypeFileExtension(UserVisitPK userVisitPK, CreateMimeTypeFileExtensionForm form);
    
    CommandResult<GetMimeTypeFileExtensionResult> getMimeTypeFileExtension(UserVisitPK userVisitPK, GetMimeTypeFileExtensionForm form);
    
    CommandResult<GetMimeTypeFileExtensionsResult> getMimeTypeFileExtensions(UserVisitPK userVisitPK, GetMimeTypeFileExtensionsForm form);
    
    // --------------------------------------------------------------------------------
    //   Protocols
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createProtocol(UserVisitPK userVisitPK, CreateProtocolForm form);

    CommandResult<GetProtocolChoicesResult> getProtocolChoices(UserVisitPK userVisitPK, GetProtocolChoicesForm form);

    CommandResult<GetProtocolResult> getProtocol(UserVisitPK userVisitPK, GetProtocolForm form);

    CommandResult<GetProtocolsResult> getProtocols(UserVisitPK userVisitPK, GetProtocolsForm form);

    CommandResult<VoidResult> setDefaultProtocol(UserVisitPK userVisitPK, SetDefaultProtocolForm form);

    CommandResult<EditProtocolResult> editProtocol(UserVisitPK userVisitPK, EditProtocolForm form);

    CommandResult<VoidResult> deleteProtocol(UserVisitPK userVisitPK, DeleteProtocolForm form);

    // --------------------------------------------------------------------------------
    //   Protocol Descriptions
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createProtocolDescription(UserVisitPK userVisitPK, CreateProtocolDescriptionForm form);

    CommandResult<GetProtocolDescriptionResult> getProtocolDescription(UserVisitPK userVisitPK, GetProtocolDescriptionForm form);

    CommandResult<GetProtocolDescriptionsResult> getProtocolDescriptions(UserVisitPK userVisitPK, GetProtocolDescriptionsForm form);

    CommandResult<EditProtocolDescriptionResult> editProtocolDescription(UserVisitPK userVisitPK, EditProtocolDescriptionForm form);

    CommandResult<VoidResult> deleteProtocolDescription(UserVisitPK userVisitPK, DeleteProtocolDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Services
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createService(UserVisitPK userVisitPK, CreateServiceForm form);

    CommandResult<GetServiceChoicesResult> getServiceChoices(UserVisitPK userVisitPK, GetServiceChoicesForm form);

    CommandResult<GetServiceResult> getService(UserVisitPK userVisitPK, GetServiceForm form);

    CommandResult<GetServicesResult> getServices(UserVisitPK userVisitPK, GetServicesForm form);

    CommandResult<VoidResult> setDefaultService(UserVisitPK userVisitPK, SetDefaultServiceForm form);

    CommandResult<EditServiceResult> editService(UserVisitPK userVisitPK, EditServiceForm form);

    CommandResult<VoidResult> deleteService(UserVisitPK userVisitPK, DeleteServiceForm form);

    // --------------------------------------------------------------------------------
    //   Service Descriptions
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createServiceDescription(UserVisitPK userVisitPK, CreateServiceDescriptionForm form);

    CommandResult<GetServiceDescriptionResult> getServiceDescription(UserVisitPK userVisitPK, GetServiceDescriptionForm form);

    CommandResult<GetServiceDescriptionsResult> getServiceDescriptions(UserVisitPK userVisitPK, GetServiceDescriptionsForm form);

    CommandResult<EditServiceDescriptionResult> editServiceDescription(UserVisitPK userVisitPK, EditServiceDescriptionForm form);

    CommandResult<VoidResult> deleteServiceDescription(UserVisitPK userVisitPK, DeleteServiceDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Servers
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createServer(UserVisitPK userVisitPK, CreateServerForm form);

    CommandResult<GetServerChoicesResult> getServerChoices(UserVisitPK userVisitPK, GetServerChoicesForm form);

    CommandResult<GetServerResult> getServer(UserVisitPK userVisitPK, GetServerForm form);

    CommandResult<GetServersResult> getServers(UserVisitPK userVisitPK, GetServersForm form);

    CommandResult<VoidResult> setDefaultServer(UserVisitPK userVisitPK, SetDefaultServerForm form);

    CommandResult<EditServerResult> editServer(UserVisitPK userVisitPK, EditServerForm form);

    CommandResult<VoidResult> deleteServer(UserVisitPK userVisitPK, DeleteServerForm form);

    // --------------------------------------------------------------------------------
    //   Server Descriptions
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createServerDescription(UserVisitPK userVisitPK, CreateServerDescriptionForm form);

    CommandResult<GetServerDescriptionResult> getServerDescription(UserVisitPK userVisitPK, GetServerDescriptionForm form);

    CommandResult<GetServerDescriptionsResult> getServerDescriptions(UserVisitPK userVisitPK, GetServerDescriptionsForm form);

    CommandResult<EditServerDescriptionResult> editServerDescription(UserVisitPK userVisitPK, EditServerDescriptionForm form);

    CommandResult<VoidResult> deleteServerDescription(UserVisitPK userVisitPK, DeleteServerDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Server Services
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createServerService(UserVisitPK userVisitPK, CreateServerServiceForm form);

    CommandResult<GetServerServiceResult> getServerService(UserVisitPK userVisitPK, GetServerServiceForm form);

    CommandResult<GetServerServicesResult> getServerServices(UserVisitPK userVisitPK, GetServerServicesForm form);

    CommandResult<VoidResult> deleteServerService(UserVisitPK userVisitPK, DeleteServerServiceForm form);

    // --------------------------------------------------------------------------------
    //   Entity Boolean Defaults
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createEntityBooleanDefault(UserVisitPK userVisitPK, CreateEntityBooleanDefaultForm form);

    CommandResult<EditEntityBooleanDefaultResult> editEntityBooleanDefault(UserVisitPK userVisitPK, EditEntityBooleanDefaultForm form);

    CommandResult<VoidResult> deleteEntityBooleanDefault(UserVisitPK userVisitPK, DeleteEntityBooleanDefaultForm form);

    // --------------------------------------------------------------------------------
    //   Entity Boolean Attributes
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createEntityBooleanAttribute(UserVisitPK userVisitPK, CreateEntityBooleanAttributeForm form);

    CommandResult<EditEntityBooleanAttributeResult> editEntityBooleanAttribute(UserVisitPK userVisitPK, EditEntityBooleanAttributeForm form);

    CommandResult<VoidResult> deleteEntityBooleanAttribute(UserVisitPK userVisitPK, DeleteEntityBooleanAttributeForm form);

    // --------------------------------------------------------------------------------
    //   Entity Integer Defaults
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createEntityIntegerDefault(UserVisitPK userVisitPK, CreateEntityIntegerDefaultForm form);

    CommandResult<EditEntityIntegerDefaultResult> editEntityIntegerDefault(UserVisitPK userVisitPK, EditEntityIntegerDefaultForm form);

    CommandResult<VoidResult> deleteEntityIntegerDefault(UserVisitPK userVisitPK, DeleteEntityIntegerDefaultForm form);

    // --------------------------------------------------------------------------------
    //   Entity Integer Attributes
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createEntityIntegerAttribute(UserVisitPK userVisitPK, CreateEntityIntegerAttributeForm form);
    
    CommandResult<EditEntityIntegerAttributeResult> editEntityIntegerAttribute(UserVisitPK userVisitPK, EditEntityIntegerAttributeForm form);
    
    CommandResult<VoidResult> deleteEntityIntegerAttribute(UserVisitPK userVisitPK, DeleteEntityIntegerAttributeForm form);

    // --------------------------------------------------------------------------------
    //   Entity List Item Defaults
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createEntityListItemDefault(UserVisitPK userVisitPK, CreateEntityListItemDefaultForm form);

    CommandResult<EditEntityListItemDefaultResult> editEntityListItemDefault(UserVisitPK userVisitPK, EditEntityListItemDefaultForm form);

    CommandResult<VoidResult> deleteEntityListItemDefault(UserVisitPK userVisitPK, DeleteEntityListItemDefaultForm form);

    // --------------------------------------------------------------------------------
    //   Entity List Item Attributes
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createEntityListItemAttribute(UserVisitPK userVisitPK, CreateEntityListItemAttributeForm form);
    
    CommandResult<EditEntityListItemAttributeResult> editEntityListItemAttribute(UserVisitPK userVisitPK, EditEntityListItemAttributeForm form);
    
    CommandResult<VoidResult> deleteEntityListItemAttribute(UserVisitPK userVisitPK, DeleteEntityListItemAttributeForm form);

    // --------------------------------------------------------------------------------
    //   Entity Long Defaults
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createEntityLongDefault(UserVisitPK userVisitPK, CreateEntityLongDefaultForm form);

    CommandResult<EditEntityLongDefaultResult> editEntityLongDefault(UserVisitPK userVisitPK, EditEntityLongDefaultForm form);

    CommandResult<VoidResult> deleteEntityLongDefault(UserVisitPK userVisitPK, DeleteEntityLongDefaultForm form);

    // --------------------------------------------------------------------------------
    //   Entity Long Attributes
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createEntityLongAttribute(UserVisitPK userVisitPK, CreateEntityLongAttributeForm form);
    
    CommandResult<EditEntityLongAttributeResult> editEntityLongAttribute(UserVisitPK userVisitPK, EditEntityLongAttributeForm form);
    
    CommandResult<VoidResult> deleteEntityLongAttribute(UserVisitPK userVisitPK, DeleteEntityLongAttributeForm form);

    // --------------------------------------------------------------------------------
    //   Entity Multiple List Item Defaults
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createEntityMultipleListItemDefault(UserVisitPK userVisitPK, CreateEntityMultipleListItemDefaultForm form);

    CommandResult<VoidResult> deleteEntityMultipleListItemDefault(UserVisitPK userVisitPK, DeleteEntityMultipleListItemDefaultForm form);

    // --------------------------------------------------------------------------------
    //   Entity Multiple List Item Attributes
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createEntityMultipleListItemAttribute(UserVisitPK userVisitPK, CreateEntityMultipleListItemAttributeForm form);

    CommandResult<VoidResult> deleteEntityMultipleListItemAttribute(UserVisitPK userVisitPK, DeleteEntityMultipleListItemAttributeForm form);

    // --------------------------------------------------------------------------------
    //   Entity Name Attributes
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createEntityNameAttribute(UserVisitPK userVisitPK, CreateEntityNameAttributeForm form);
    
    CommandResult<EditEntityNameAttributeResult> editEntityNameAttribute(UserVisitPK userVisitPK, EditEntityNameAttributeForm form);
    
    CommandResult<VoidResult> deleteEntityNameAttribute(UserVisitPK userVisitPK, DeleteEntityNameAttributeForm form);

    // --------------------------------------------------------------------------------
    //   Entity String Defaults
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createEntityStringDefault(UserVisitPK userVisitPK, CreateEntityStringDefaultForm form);

    CommandResult<EditEntityStringDefaultResult> editEntityStringDefault(UserVisitPK userVisitPK, EditEntityStringDefaultForm form);

    CommandResult<VoidResult> deleteEntityStringDefault(UserVisitPK userVisitPK, DeleteEntityStringDefaultForm form);

    // --------------------------------------------------------------------------------
    //   Entity String Attributes
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createEntityStringAttribute(UserVisitPK userVisitPK, CreateEntityStringAttributeForm form);
    
    CommandResult<EditEntityStringAttributeResult> editEntityStringAttribute(UserVisitPK userVisitPK, EditEntityStringAttributeForm form);
    
    CommandResult<VoidResult> deleteEntityStringAttribute(UserVisitPK userVisitPK, DeleteEntityStringAttributeForm form);

    // --------------------------------------------------------------------------------
    //   Entity Geo Point Defaults
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createEntityGeoPointDefault(UserVisitPK userVisitPK, CreateEntityGeoPointDefaultForm form);

    CommandResult<EditEntityGeoPointDefaultResult> editEntityGeoPointDefault(UserVisitPK userVisitPK, EditEntityGeoPointDefaultForm form);

    CommandResult<VoidResult> deleteEntityGeoPointDefault(UserVisitPK userVisitPK, DeleteEntityGeoPointDefaultForm form);

    // --------------------------------------------------------------------------------
    //   Entity Geo Point Attributes
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createEntityGeoPointAttribute(UserVisitPK userVisitPK, CreateEntityGeoPointAttributeForm form);

    CommandResult<EditEntityGeoPointAttributeResult> editEntityGeoPointAttribute(UserVisitPK userVisitPK, EditEntityGeoPointAttributeForm form);

    CommandResult<VoidResult> deleteEntityGeoPointAttribute(UserVisitPK userVisitPK, DeleteEntityGeoPointAttributeForm form);

    // --------------------------------------------------------------------------------
    //   Entity Blob Attributes
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createEntityBlobAttribute(UserVisitPK userVisitPK, CreateEntityBlobAttributeForm form);

    CommandResult<EditEntityBlobAttributeResult> editEntityBlobAttribute(UserVisitPK userVisitPK, EditEntityBlobAttributeForm form);

    CommandResult<GetEntityBlobAttributeResult> getEntityBlobAttribute(UserVisitPK userVisitPK, GetEntityBlobAttributeForm form);

    CommandResult<VoidResult> deleteEntityBlobAttribute(UserVisitPK userVisitPK, DeleteEntityBlobAttributeForm form);
    
    // --------------------------------------------------------------------------------
    //   Entity Clob Attributes
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createEntityClobAttribute(UserVisitPK userVisitPK, CreateEntityClobAttributeForm form);

    CommandResult<EditEntityClobAttributeResult> editEntityClobAttribute(UserVisitPK userVisitPK, EditEntityClobAttributeForm form);

    CommandResult<GetEntityClobAttributeResult> getEntityClobAttribute(UserVisitPK userVisitPK, GetEntityClobAttributeForm form);

    CommandResult<VoidResult> deleteEntityClobAttribute(UserVisitPK userVisitPK, DeleteEntityClobAttributeForm form);

    // --------------------------------------------------------------------------------
    //   Entity Time Defaults
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createEntityTimeDefault(UserVisitPK userVisitPK, CreateEntityTimeDefaultForm form);

    CommandResult<EditEntityTimeDefaultResult> editEntityTimeDefault(UserVisitPK userVisitPK, EditEntityTimeDefaultForm form);

    CommandResult<VoidResult> deleteEntityTimeDefault(UserVisitPK userVisitPK, DeleteEntityTimeDefaultForm form);

    // --------------------------------------------------------------------------------
    //   Entity Time Attributes
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createEntityTimeAttribute(UserVisitPK userVisitPK, CreateEntityTimeAttributeForm form);

    CommandResult<EditEntityTimeAttributeResult> editEntityTimeAttribute(UserVisitPK userVisitPK, EditEntityTimeAttributeForm form);

    CommandResult<VoidResult> deleteEntityTimeAttribute(UserVisitPK userVisitPK, DeleteEntityTimeAttributeForm form);

    // --------------------------------------------------------------------------------
    //   Entity Date Defaults
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createEntityDateDefault(UserVisitPK userVisitPK, CreateEntityDateDefaultForm form);

    CommandResult<EditEntityDateDefaultResult> editEntityDateDefault(UserVisitPK userVisitPK, EditEntityDateDefaultForm form);

    CommandResult<VoidResult> deleteEntityDateDefault(UserVisitPK userVisitPK, DeleteEntityDateDefaultForm form);

    // --------------------------------------------------------------------------------
    //   Entity Date Attributes
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createEntityDateAttribute(UserVisitPK userVisitPK, CreateEntityDateAttributeForm form);

    CommandResult<EditEntityDateAttributeResult> editEntityDateAttribute(UserVisitPK userVisitPK, EditEntityDateAttributeForm form);

    CommandResult<VoidResult> deleteEntityDateAttribute(UserVisitPK userVisitPK, DeleteEntityDateAttributeForm form);

    // --------------------------------------------------------------------------------
    //   Entity Attribute Entity Types
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createEntityAttributeEntityType(UserVisitPK userVisitPK, CreateEntityAttributeEntityTypeForm form);

    CommandResult<GetEntityAttributeEntityTypeResult> getEntityAttributeEntityType(UserVisitPK userVisitPK, GetEntityAttributeEntityTypeForm form);

    CommandResult<GetEntityAttributeEntityTypesResult> getEntityAttributeEntityTypes(UserVisitPK userVisitPK, GetEntityAttributeEntityTypesForm form);
    
    CommandResult<VoidResult> deleteEntityAttributeEntityType(UserVisitPK userVisitPK, DeleteEntityAttributeEntityTypeForm form);

    // --------------------------------------------------------------------------------
    //   Entity Entity Attributes
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createEntityEntityAttribute(UserVisitPK userVisitPK, CreateEntityEntityAttributeForm form);
    
    CommandResult<EditEntityEntityAttributeResult> editEntityEntityAttribute(UserVisitPK userVisitPK, EditEntityEntityAttributeForm form);
    
    CommandResult<VoidResult> deleteEntityEntityAttribute(UserVisitPK userVisitPK, DeleteEntityEntityAttributeForm form);

    // --------------------------------------------------------------------------------
    //   Entity Collection Attributes
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createEntityCollectionAttribute(UserVisitPK userVisitPK, CreateEntityCollectionAttributeForm form);

    CommandResult<VoidResult> deleteEntityCollectionAttribute(UserVisitPK userVisitPK, DeleteEntityCollectionAttributeForm form);

    // --------------------------------------------------------------------------------
    //   Entity Workflow Attributes
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createEntityWorkflowAttribute(UserVisitPK userVisitPK, CreateEntityWorkflowAttributeForm form);

    CommandResult<VoidResult> setEntityWorkflowAttributeStatus(UserVisitPK userVisitPK, SetEntityWorkflowAttributeStatusForm form);

    CommandResult<VoidResult> deleteEntityWorkflowAttribute(UserVisitPK userVisitPK, DeleteEntityWorkflowAttributeForm form);

    // -------------------------------------------------------------------------
    //   Cache Entries
    // -------------------------------------------------------------------------

    CommandResult<VoidResult> createCacheEntry(UserVisitPK userVisitPK, CreateCacheEntryForm form);

    CommandResult<GetCacheEntryResult> getCacheEntry(UserVisitPK userVisitPK, GetCacheEntryForm form);

    CommandResult<GetCacheEntriesResult> getCacheEntries(UserVisitPK userVisitPK, GetCacheEntriesForm form);

    CommandResult<VoidResult> removeCacheEntry(UserVisitPK userVisitPK, RemoveCacheEntryForm form);

    // -------------------------------------------------------------------------
    //   Cache Entry Dependencies
    // -------------------------------------------------------------------------

    CommandResult<GetCacheEntryDependenciesResult> getCacheEntryDependencies(UserVisitPK userVisitPK, GetCacheEntryDependenciesForm form);

    // --------------------------------------------------------------------------------
    //   Applications
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createApplication(UserVisitPK userVisitPK, CreateApplicationForm form);
    
    CommandResult<GetApplicationChoicesResult> getApplicationChoices(UserVisitPK userVisitPK, GetApplicationChoicesForm form);
    
    CommandResult<GetApplicationResult> getApplication(UserVisitPK userVisitPK, GetApplicationForm form);
    
    CommandResult<GetApplicationsResult> getApplications(UserVisitPK userVisitPK, GetApplicationsForm form);
    
    CommandResult<VoidResult> setDefaultApplication(UserVisitPK userVisitPK, SetDefaultApplicationForm form);
    
    CommandResult<EditApplicationResult> editApplication(UserVisitPK userVisitPK, EditApplicationForm form);
    
    CommandResult<VoidResult> deleteApplication(UserVisitPK userVisitPK, DeleteApplicationForm form);
    
    // --------------------------------------------------------------------------------
    //   Application Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createApplicationDescription(UserVisitPK userVisitPK, CreateApplicationDescriptionForm form);
    
    CommandResult<GetApplicationDescriptionResult> getApplicationDescription(UserVisitPK userVisitPK, GetApplicationDescriptionForm form);
    
    CommandResult<GetApplicationDescriptionsResult> getApplicationDescriptions(UserVisitPK userVisitPK, GetApplicationDescriptionsForm form);
    
    CommandResult<EditApplicationDescriptionResult> editApplicationDescription(UserVisitPK userVisitPK, EditApplicationDescriptionForm form);
    
    CommandResult<VoidResult> deleteApplicationDescription(UserVisitPK userVisitPK, DeleteApplicationDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Editors
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createEditor(UserVisitPK userVisitPK, CreateEditorForm form);
    
    CommandResult<GetEditorChoicesResult> getEditorChoices(UserVisitPK userVisitPK, GetEditorChoicesForm form);
    
    CommandResult<GetEditorResult> getEditor(UserVisitPK userVisitPK, GetEditorForm form);
    
    CommandResult<GetEditorsResult> getEditors(UserVisitPK userVisitPK, GetEditorsForm form);
    
    CommandResult<VoidResult> setDefaultEditor(UserVisitPK userVisitPK, SetDefaultEditorForm form);
    
    CommandResult<EditEditorResult> editEditor(UserVisitPK userVisitPK, EditEditorForm form);
    
    CommandResult<VoidResult> deleteEditor(UserVisitPK userVisitPK, DeleteEditorForm form);
    
    // --------------------------------------------------------------------------------
    //   Editor Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createEditorDescription(UserVisitPK userVisitPK, CreateEditorDescriptionForm form);
    
    CommandResult<GetEditorDescriptionResult> getEditorDescription(UserVisitPK userVisitPK, GetEditorDescriptionForm form);
    
    CommandResult<GetEditorDescriptionsResult> getEditorDescriptions(UserVisitPK userVisitPK, GetEditorDescriptionsForm form);
    
    CommandResult<EditEditorDescriptionResult> editEditorDescription(UserVisitPK userVisitPK, EditEditorDescriptionForm form);
    
    CommandResult<VoidResult> deleteEditorDescription(UserVisitPK userVisitPK, DeleteEditorDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Application Editors
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createApplicationEditor(UserVisitPK userVisitPK, CreateApplicationEditorForm form);
    
    CommandResult<GetApplicationEditorChoicesResult> getApplicationEditorChoices(UserVisitPK userVisitPK, GetApplicationEditorChoicesForm form);
    
    CommandResult<GetApplicationEditorResult> getApplicationEditor(UserVisitPK userVisitPK, GetApplicationEditorForm form);
    
    CommandResult<GetApplicationEditorsResult> getApplicationEditors(UserVisitPK userVisitPK, GetApplicationEditorsForm form);
    
    CommandResult<VoidResult> setDefaultApplicationEditor(UserVisitPK userVisitPK, SetDefaultApplicationEditorForm form);
    
    CommandResult<EditApplicationEditorResult> editApplicationEditor(UserVisitPK userVisitPK, EditApplicationEditorForm form);
    
    CommandResult<VoidResult> deleteApplicationEditor(UserVisitPK userVisitPK, DeleteApplicationEditorForm form);
    
    // --------------------------------------------------------------------------------
    //   Application Editor Uses
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createApplicationEditorUse(UserVisitPK userVisitPK, CreateApplicationEditorUseForm form);
    
    CommandResult<GetApplicationEditorUseChoicesResult> getApplicationEditorUseChoices(UserVisitPK userVisitPK, GetApplicationEditorUseChoicesForm form);
    
    CommandResult<GetApplicationEditorUseResult> getApplicationEditorUse(UserVisitPK userVisitPK, GetApplicationEditorUseForm form);
    
    CommandResult<GetApplicationEditorUsesResult> getApplicationEditorUses(UserVisitPK userVisitPK, GetApplicationEditorUsesForm form);
    
    CommandResult<VoidResult> setDefaultApplicationEditorUse(UserVisitPK userVisitPK, SetDefaultApplicationEditorUseForm form);
    
    CommandResult<EditApplicationEditorUseResult> editApplicationEditorUse(UserVisitPK userVisitPK, EditApplicationEditorUseForm form);
    
    CommandResult<VoidResult> deleteApplicationEditorUse(UserVisitPK userVisitPK, DeleteApplicationEditorUseForm form);
    
    // --------------------------------------------------------------------------------
    //   Application Editor Use Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createApplicationEditorUseDescription(UserVisitPK userVisitPK, CreateApplicationEditorUseDescriptionForm form);
    
    CommandResult<GetApplicationEditorUseDescriptionResult> getApplicationEditorUseDescription(UserVisitPK userVisitPK, GetApplicationEditorUseDescriptionForm form);
    
    CommandResult<GetApplicationEditorUseDescriptionsResult> getApplicationEditorUseDescriptions(UserVisitPK userVisitPK, GetApplicationEditorUseDescriptionsForm form);
    
    CommandResult<EditApplicationEditorUseDescriptionResult> editApplicationEditorUseDescription(UserVisitPK userVisitPK, EditApplicationEditorUseDescriptionForm form);
    
    CommandResult<VoidResult> deleteApplicationEditorUseDescription(UserVisitPK userVisitPK, DeleteApplicationEditorUseDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Appearances
    // --------------------------------------------------------------------------------
    
    CommandResult<CreateAppearanceResult> createAppearance(UserVisitPK userVisitPK, CreateAppearanceForm form);
    
    CommandResult<GetAppearanceChoicesResult> getAppearanceChoices(UserVisitPK userVisitPK, GetAppearanceChoicesForm form);
    
    CommandResult<GetAppearanceResult> getAppearance(UserVisitPK userVisitPK, GetAppearanceForm form);
    
    CommandResult<GetAppearancesResult> getAppearances(UserVisitPK userVisitPK, GetAppearancesForm form);
    
    CommandResult<VoidResult> setDefaultAppearance(UserVisitPK userVisitPK, SetDefaultAppearanceForm form);
    
    CommandResult<EditAppearanceResult> editAppearance(UserVisitPK userVisitPK, EditAppearanceForm form);
    
    CommandResult<VoidResult> deleteAppearance(UserVisitPK userVisitPK, DeleteAppearanceForm form);
    
    // --------------------------------------------------------------------------------
    //   Appearance Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createAppearanceDescription(UserVisitPK userVisitPK, CreateAppearanceDescriptionForm form);
    
    CommandResult<GetAppearanceDescriptionResult> getAppearanceDescription(UserVisitPK userVisitPK, GetAppearanceDescriptionForm form);
    
    CommandResult<GetAppearanceDescriptionsResult> getAppearanceDescriptions(UserVisitPK userVisitPK, GetAppearanceDescriptionsForm form);
    
    CommandResult<EditAppearanceDescriptionResult> editAppearanceDescription(UserVisitPK userVisitPK, EditAppearanceDescriptionForm form);
    
    CommandResult<VoidResult> deleteAppearanceDescription(UserVisitPK userVisitPK, DeleteAppearanceDescriptionForm form);
    
     // --------------------------------------------------------------------------------
    //   Appearance Text Decorations
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createAppearanceTextDecoration(UserVisitPK userVisitPK, CreateAppearanceTextDecorationForm form);
    
    CommandResult<GetAppearanceTextDecorationResult> getAppearanceTextDecoration(UserVisitPK userVisitPK, GetAppearanceTextDecorationForm form);
    
    CommandResult<GetAppearanceTextDecorationsResult> getAppearanceTextDecorations(UserVisitPK userVisitPK, GetAppearanceTextDecorationsForm form);
    
    CommandResult<VoidResult> deleteAppearanceTextDecoration(UserVisitPK userVisitPK, DeleteAppearanceTextDecorationForm form);
    
     // --------------------------------------------------------------------------------
    //   Appearance Text Transformations
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createAppearanceTextTransformation(UserVisitPK userVisitPK, CreateAppearanceTextTransformationForm form);
    
    CommandResult<GetAppearanceTextTransformationResult> getAppearanceTextTransformation(UserVisitPK userVisitPK, GetAppearanceTextTransformationForm form);
    
    CommandResult<GetAppearanceTextTransformationsResult> getAppearanceTextTransformations(UserVisitPK userVisitPK, GetAppearanceTextTransformationsForm form);
    
    CommandResult<VoidResult> deleteAppearanceTextTransformation(UserVisitPK userVisitPK, DeleteAppearanceTextTransformationForm form);
    
   // --------------------------------------------------------------------------------
    //   Colors
    // --------------------------------------------------------------------------------
    
    CommandResult<CreateColorResult> createColor(UserVisitPK userVisitPK, CreateColorForm form);
    
    CommandResult<GetColorChoicesResult> getColorChoices(UserVisitPK userVisitPK, GetColorChoicesForm form);
    
    CommandResult<GetColorResult> getColor(UserVisitPK userVisitPK, GetColorForm form);
    
    CommandResult<GetColorsResult> getColors(UserVisitPK userVisitPK, GetColorsForm form);
    
    CommandResult<VoidResult> setDefaultColor(UserVisitPK userVisitPK, SetDefaultColorForm form);
    
    CommandResult<EditColorResult> editColor(UserVisitPK userVisitPK, EditColorForm form);
    
    CommandResult<VoidResult> deleteColor(UserVisitPK userVisitPK, DeleteColorForm form);
    
    // --------------------------------------------------------------------------------
    //   Color Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createColorDescription(UserVisitPK userVisitPK, CreateColorDescriptionForm form);
    
    CommandResult<GetColorDescriptionResult> getColorDescription(UserVisitPK userVisitPK, GetColorDescriptionForm form);
    
    CommandResult<GetColorDescriptionsResult> getColorDescriptions(UserVisitPK userVisitPK, GetColorDescriptionsForm form);
    
    CommandResult<EditColorDescriptionResult> editColorDescription(UserVisitPK userVisitPK, EditColorDescriptionForm form);
    
    CommandResult<VoidResult> deleteColorDescription(UserVisitPK userVisitPK, DeleteColorDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Font Styles
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createFontStyle(UserVisitPK userVisitPK, CreateFontStyleForm form);
    
    CommandResult<GetFontStyleChoicesResult> getFontStyleChoices(UserVisitPK userVisitPK, GetFontStyleChoicesForm form);
    
    CommandResult<GetFontStyleResult> getFontStyle(UserVisitPK userVisitPK, GetFontStyleForm form);
    
    CommandResult<GetFontStylesResult> getFontStyles(UserVisitPK userVisitPK, GetFontStylesForm form);
    
    CommandResult<VoidResult> setDefaultFontStyle(UserVisitPK userVisitPK, SetDefaultFontStyleForm form);
    
    CommandResult<EditFontStyleResult> editFontStyle(UserVisitPK userVisitPK, EditFontStyleForm form);
    
    CommandResult<VoidResult> deleteFontStyle(UserVisitPK userVisitPK, DeleteFontStyleForm form);
    
    // --------------------------------------------------------------------------------
    //   Font Style Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createFontStyleDescription(UserVisitPK userVisitPK, CreateFontStyleDescriptionForm form);
    
    CommandResult<GetFontStyleDescriptionResult> getFontStyleDescription(UserVisitPK userVisitPK, GetFontStyleDescriptionForm form);
    
    CommandResult<GetFontStyleDescriptionsResult> getFontStyleDescriptions(UserVisitPK userVisitPK, GetFontStyleDescriptionsForm form);
    
    CommandResult<EditFontStyleDescriptionResult> editFontStyleDescription(UserVisitPK userVisitPK, EditFontStyleDescriptionForm form);
    
    CommandResult<VoidResult> deleteFontStyleDescription(UserVisitPK userVisitPK, DeleteFontStyleDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Font Weights
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createFontWeight(UserVisitPK userVisitPK, CreateFontWeightForm form);
    
    CommandResult<GetFontWeightChoicesResult> getFontWeightChoices(UserVisitPK userVisitPK, GetFontWeightChoicesForm form);
    
    CommandResult<GetFontWeightResult> getFontWeight(UserVisitPK userVisitPK, GetFontWeightForm form);
    
    CommandResult<GetFontWeightsResult> getFontWeights(UserVisitPK userVisitPK, GetFontWeightsForm form);
    
    CommandResult<VoidResult> setDefaultFontWeight(UserVisitPK userVisitPK, SetDefaultFontWeightForm form);
    
    CommandResult<EditFontWeightResult> editFontWeight(UserVisitPK userVisitPK, EditFontWeightForm form);
    
    CommandResult<VoidResult> deleteFontWeight(UserVisitPK userVisitPK, DeleteFontWeightForm form);
    
    // --------------------------------------------------------------------------------
    //   Font Weight Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createFontWeightDescription(UserVisitPK userVisitPK, CreateFontWeightDescriptionForm form);
    
    CommandResult<GetFontWeightDescriptionResult> getFontWeightDescription(UserVisitPK userVisitPK, GetFontWeightDescriptionForm form);
    
    CommandResult<GetFontWeightDescriptionsResult> getFontWeightDescriptions(UserVisitPK userVisitPK, GetFontWeightDescriptionsForm form);
    
    CommandResult<EditFontWeightDescriptionResult> editFontWeightDescription(UserVisitPK userVisitPK, EditFontWeightDescriptionForm form);
    
    CommandResult<VoidResult> deleteFontWeightDescription(UserVisitPK userVisitPK, DeleteFontWeightDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Font Decorations
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createTextDecoration(UserVisitPK userVisitPK, CreateTextDecorationForm form);
    
    CommandResult<GetTextDecorationChoicesResult> getTextDecorationChoices(UserVisitPK userVisitPK, GetTextDecorationChoicesForm form);
    
    CommandResult<GetTextDecorationResult> getTextDecoration(UserVisitPK userVisitPK, GetTextDecorationForm form);
    
    CommandResult<GetTextDecorationsResult> getTextDecorations(UserVisitPK userVisitPK, GetTextDecorationsForm form);
    
    CommandResult<VoidResult> setDefaultTextDecoration(UserVisitPK userVisitPK, SetDefaultTextDecorationForm form);
    
    CommandResult<EditTextDecorationResult> editTextDecoration(UserVisitPK userVisitPK, EditTextDecorationForm form);
    
    CommandResult<VoidResult> deleteTextDecoration(UserVisitPK userVisitPK, DeleteTextDecorationForm form);
    
    // --------------------------------------------------------------------------------
    //   Font Decoration Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createTextDecorationDescription(UserVisitPK userVisitPK, CreateTextDecorationDescriptionForm form);
    
    CommandResult<GetTextDecorationDescriptionResult> getTextDecorationDescription(UserVisitPK userVisitPK, GetTextDecorationDescriptionForm form);
    
    CommandResult<GetTextDecorationDescriptionsResult> getTextDecorationDescriptions(UserVisitPK userVisitPK, GetTextDecorationDescriptionsForm form);
    
    CommandResult<EditTextDecorationDescriptionResult> editTextDecorationDescription(UserVisitPK userVisitPK, EditTextDecorationDescriptionForm form);
    
    CommandResult<VoidResult> deleteTextDecorationDescription(UserVisitPK userVisitPK, DeleteTextDecorationDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Font Transformations
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createTextTransformation(UserVisitPK userVisitPK, CreateTextTransformationForm form);
    
    CommandResult<GetTextTransformationChoicesResult> getTextTransformationChoices(UserVisitPK userVisitPK, GetTextTransformationChoicesForm form);
    
    CommandResult<GetTextTransformationResult> getTextTransformation(UserVisitPK userVisitPK, GetTextTransformationForm form);
    
    CommandResult<GetTextTransformationsResult> getTextTransformations(UserVisitPK userVisitPK, GetTextTransformationsForm form);
    
    CommandResult<VoidResult> setDefaultTextTransformation(UserVisitPK userVisitPK, SetDefaultTextTransformationForm form);
    
    CommandResult<EditTextTransformationResult> editTextTransformation(UserVisitPK userVisitPK, EditTextTransformationForm form);
    
    CommandResult<VoidResult> deleteTextTransformation(UserVisitPK userVisitPK, DeleteTextTransformationForm form);
    
    // --------------------------------------------------------------------------------
    //   Font Transformation Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createTextTransformationDescription(UserVisitPK userVisitPK, CreateTextTransformationDescriptionForm form);
    
    CommandResult<GetTextTransformationDescriptionResult> getTextTransformationDescription(UserVisitPK userVisitPK, GetTextTransformationDescriptionForm form);
    
    CommandResult<GetTextTransformationDescriptionsResult> getTextTransformationDescriptions(UserVisitPK userVisitPK, GetTextTransformationDescriptionsForm form);
    
    CommandResult<EditTextTransformationDescriptionResult> editTextTransformationDescription(UserVisitPK userVisitPK, EditTextTransformationDescriptionForm form);
    
    CommandResult<VoidResult> deleteTextTransformationDescription(UserVisitPK userVisitPK, DeleteTextTransformationDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Entity Appearances
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createEntityAppearance(UserVisitPK userVisitPK, CreateEntityAppearanceForm form);
    
    CommandResult<GetEntityAppearanceResult> getEntityAppearance(UserVisitPK userVisitPK, GetEntityAppearanceForm form);
    
    CommandResult<EditEntityAppearanceResult> editEntityAppearance(UserVisitPK userVisitPK, EditEntityAppearanceForm form);
    
    CommandResult<VoidResult> deleteEntityAppearance(UserVisitPK userVisitPK, DeleteEntityAppearanceForm form);
    
    // -------------------------------------------------------------------------
    //   Utilities
    // -------------------------------------------------------------------------

    CommandResult<EncryptResult> encrypt(UserVisitPK userVisitPK, EncryptForm form);

    CommandResult<DecryptResult> decrypt(UserVisitPK userVisitPK, DecryptForm form);

    CommandResult<ValidateResult> validate(UserVisitPK userVisit, ValidateForm form);

}
