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

package com.echothree.control.user.core.common;

import com.echothree.control.user.core.common.form.*;
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
    
    CommandResult generateBaseKeys(UserVisitPK userVisitPK);
    
    CommandResult loadBaseKeys(UserVisitPK userVisitPK, LoadBaseKeysForm form);
    
    CommandResult changeBaseKeys(UserVisitPK userVisitPK, ChangeBaseKeysForm form);
    
    // -------------------------------------------------------------------------
    //   Base Encryption Keys
    // -------------------------------------------------------------------------
    
    CommandResult getBaseEncryptionKey(UserVisitPK userVisitPK, GetBaseEncryptionKeyForm form);
    
    CommandResult getBaseEncryptionKeys(UserVisitPK userVisitPK, GetBaseEncryptionKeysForm form);
    
    CommandResult getBaseEncryptionKeyStatusChoices(UserVisitPK userVisitPK, GetBaseEncryptionKeyStatusChoicesForm form);
    
    CommandResult setBaseEncryptionKeyStatus(UserVisitPK userVisitPK, SetBaseEncryptionKeyStatusForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Locks
    // -------------------------------------------------------------------------
    
    CommandResult lockEntity(UserVisitPK userVisitPK, LockEntityForm form);
    
    CommandResult unlockEntity(UserVisitPK userVisitPK, UnlockEntityForm form);
    
    CommandResult removedExpiredEntityLocks(UserVisitPK userVisitPK);
    
    // -------------------------------------------------------------------------
    //   Component Vendors
    // -------------------------------------------------------------------------
    
    CommandResult createComponentVendor(UserVisitPK userVisitPK, CreateComponentVendorForm form);
    
    CommandResult getComponentVendor(UserVisitPK userVisitPK, GetComponentVendorForm form);
    
    CommandResult getComponentVendors(UserVisitPK userVisitPK, GetComponentVendorsForm form);
    
    CommandResult editComponentVendor(UserVisitPK userVisitPK, EditComponentVendorForm form);
    
    CommandResult deleteComponentVendor(UserVisitPK userVisitPK, DeleteComponentVendorForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Types
    // -------------------------------------------------------------------------
    
    CommandResult createEntityType(UserVisitPK userVisitPK, CreateEntityTypeForm form);
    
    CommandResult getEntityType(UserVisitPK userVisitPK, GetEntityTypeForm form);
    
    CommandResult getEntityTypes(UserVisitPK userVisitPK, GetEntityTypesForm form);
    
    CommandResult editEntityType(UserVisitPK userVisitPK, EditEntityTypeForm form);
    
    CommandResult deleteEntityType(UserVisitPK userVisitPK, DeleteEntityTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Commands
    // -------------------------------------------------------------------------
    
    CommandResult createCommand(UserVisitPK userVisitPK, CreateCommandForm form);
    
    CommandResult getCommand(UserVisitPK userVisitPK, GetCommandForm form);
    
    CommandResult getCommands(UserVisitPK userVisitPK, GetCommandsForm form);
    
    CommandResult editCommand(UserVisitPK userVisitPK, EditCommandForm form);
    
    CommandResult deleteCommand(UserVisitPK userVisitPK, DeleteCommandForm form);
    
    // -------------------------------------------------------------------------
    //   Command Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createCommandDescription(UserVisitPK userVisitPK, CreateCommandDescriptionForm form);
    
    CommandResult getCommandDescription(UserVisitPK userVisitPK, GetCommandDescriptionForm form);
    
    CommandResult getCommandDescriptions(UserVisitPK userVisitPK, GetCommandDescriptionsForm form);
    
    CommandResult editCommandDescription(UserVisitPK userVisitPK, EditCommandDescriptionForm form);
    
    CommandResult deleteCommandDescription(UserVisitPK userVisitPK, DeleteCommandDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Command Message Types
    // --------------------------------------------------------------------------------
    
    CommandResult createCommandMessageType(UserVisitPK userVisitPK, CreateCommandMessageTypeForm form);
    
    CommandResult getCommandMessageTypeChoices(UserVisitPK userVisitPK, GetCommandMessageTypeChoicesForm form);
    
    CommandResult getCommandMessageType(UserVisitPK userVisitPK, GetCommandMessageTypeForm form);
    
    CommandResult getCommandMessageTypes(UserVisitPK userVisitPK, GetCommandMessageTypesForm form);
    
    CommandResult setDefaultCommandMessageType(UserVisitPK userVisitPK, SetDefaultCommandMessageTypeForm form);
    
    CommandResult editCommandMessageType(UserVisitPK userVisitPK, EditCommandMessageTypeForm form);
    
    CommandResult deleteCommandMessageType(UserVisitPK userVisitPK, DeleteCommandMessageTypeForm form);
    
    // --------------------------------------------------------------------------------
    //   Command Message Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createCommandMessageTypeDescription(UserVisitPK userVisitPK, CreateCommandMessageTypeDescriptionForm form);
    
    CommandResult getCommandMessageTypeDescription(UserVisitPK userVisitPK, GetCommandMessageTypeDescriptionForm form);
    
    CommandResult getCommandMessageTypeDescriptions(UserVisitPK userVisitPK, GetCommandMessageTypeDescriptionsForm form);
    
    CommandResult editCommandMessageTypeDescription(UserVisitPK userVisitPK, EditCommandMessageTypeDescriptionForm form);
    
    CommandResult deleteCommandMessageTypeDescription(UserVisitPK userVisitPK, DeleteCommandMessageTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Command Messages
    // -------------------------------------------------------------------------
    
    CommandResult createCommandMessage(UserVisitPK userVisitPK, CreateCommandMessageForm form);
    
    CommandResult getCommandMessage(UserVisitPK userVisitPK, GetCommandMessageForm form);

    CommandResult getCommandMessages(UserVisitPK userVisitPK, GetCommandMessagesForm form);

    CommandResult editCommandMessage(UserVisitPK userVisitPK, EditCommandMessageForm form);

    CommandResult deleteCommandMessage(UserVisitPK userVisitPK, DeleteCommandMessageForm form);

    // -------------------------------------------------------------------------
    //   Command Message Translations
    // -------------------------------------------------------------------------

    CommandResult createCommandMessageTranslation(UserVisitPK userVisitPK, CreateCommandMessageTranslationForm form);

    CommandResult getCommandMessageTranslation(UserVisitPK userVisitPK, GetCommandMessageTranslationForm form);

    CommandResult getCommandMessageTranslations(UserVisitPK userVisitPK, GetCommandMessageTranslationsForm form);

    CommandResult editCommandMessageTranslation(UserVisitPK userVisitPK, EditCommandMessageTranslationForm form);

    CommandResult deleteCommandMessageTranslation(UserVisitPK userVisitPK, DeleteCommandMessageTranslationForm form);

    // -------------------------------------------------------------------------
    //   Entity Instances
    // -------------------------------------------------------------------------

    CommandResult createEntityInstance(UserVisitPK userVisitPK, CreateEntityInstanceForm form);

    CommandResult getEntityInstance(UserVisitPK userVisitPK, GetEntityInstanceForm form);

    CommandResult getEntityInstances(UserVisitPK userVisitPK, GetEntityInstancesForm form);

    CommandResult deleteEntityInstance(UserVisitPK userVisitPK, DeleteEntityInstanceForm form);

    CommandResult removeEntityInstance(UserVisitPK userVisitPK, RemoveEntityInstanceForm form);

    CommandResult generateUuid(UserVisitPK userVisitPK, GenerateUuidForm form);

    // -------------------------------------------------------------------------
    //   Event Types
    // -------------------------------------------------------------------------
    
    CommandResult createEventType(UserVisitPK userVisitPK, CreateEventTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Event Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createEventTypeDescription(UserVisitPK userVisitPK, CreateEventTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Event Groups
    // --------------------------------------------------------------------------------
    
    CommandResult getEventGroup(UserVisitPK userVisitPK, GetEventGroupForm form);
    
    CommandResult getEventGroups(UserVisitPK userVisitPK, GetEventGroupsForm form);
    
    CommandResult getEventGroupStatusChoices(UserVisitPK userVisitPK, GetEventGroupStatusChoicesForm form);
    
    CommandResult setEventGroupStatus(UserVisitPK userVisitPK, SetEventGroupStatusForm form);
    
    // -------------------------------------------------------------------------
    //   Events
    // -------------------------------------------------------------------------

    CommandResult sendEvent(UserVisitPK userVisitPK, SendEventForm form);

    CommandResult getEvents(UserVisitPK userVisitPK, GetEventsForm form);
    
    // -------------------------------------------------------------------------
    //   Queued Events
    // -------------------------------------------------------------------------
    
    CommandResult processQueuedEvents(UserVisitPK userVisitPK);
    
    // -------------------------------------------------------------------------
    //   Components
    // -------------------------------------------------------------------------
    
    CommandResult createComponent(UserVisitPK userVisitPK, CreateComponentForm form);
    
    // -------------------------------------------------------------------------
    //   Component Stages
    // -------------------------------------------------------------------------
    
    CommandResult createComponentStage(UserVisitPK userVisitPK, CreateComponentStageForm form);
    
    // -------------------------------------------------------------------------
    //   Component Versions
    // -------------------------------------------------------------------------
    
    CommandResult createComponentVersion(UserVisitPK userVisitPK, CreateComponentVersionForm form);

    // -------------------------------------------------------------------------
    //   Entity Alias Types
    // -------------------------------------------------------------------------

    CommandResult createEntityAliasType(UserVisitPK userVisitPK, CreateEntityAliasTypeForm form);

    CommandResult getEntityAliasType(UserVisitPK userVisitPK, GetEntityAliasTypeForm form);

    CommandResult getEntityAliasTypes(UserVisitPK userVisitPK, GetEntityAliasTypesForm form);

    CommandResult getEntityAliasTypeChoices(UserVisitPK userVisitPK, GetEntityAliasTypeChoicesForm form);

    CommandResult setDefaultEntityAliasType(UserVisitPK userVisitPK, SetDefaultEntityAliasTypeForm form);

    CommandResult editEntityAliasType(UserVisitPK userVisitPK, EditEntityAliasTypeForm form);

    CommandResult deleteEntityAliasType(UserVisitPK userVisitPK, DeleteEntityAliasTypeForm form);

    // -------------------------------------------------------------------------
    //   Entity Alias Type Descriptions
    // -------------------------------------------------------------------------

    CommandResult createEntityAliasTypeDescription(UserVisitPK userVisitPK, CreateEntityAliasTypeDescriptionForm form);

    CommandResult getEntityAliasTypeDescription(UserVisitPK userVisitPK, GetEntityAliasTypeDescriptionForm form);

    CommandResult getEntityAliasTypeDescriptions(UserVisitPK userVisitPK, GetEntityAliasTypeDescriptionsForm form);

    CommandResult editEntityAliasTypeDescription(UserVisitPK userVisitPK, EditEntityAliasTypeDescriptionForm form);

    CommandResult deleteEntityAliasTypeDescription(UserVisitPK userVisitPK, DeleteEntityAliasTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Entity Aliases
    // --------------------------------------------------------------------------------

    CommandResult createEntityAlias(UserVisitPK userVisitPK, CreateEntityAliasForm form);

    CommandResult getEntityAlias(UserVisitPK userVisitPK, GetEntityAliasForm form);

    CommandResult getEntityAliases(UserVisitPK userVisitPK, GetEntityAliasesForm form);

    CommandResult editEntityAlias(UserVisitPK userVisitPK, EditEntityAliasForm form);

    CommandResult deleteEntityAlias(UserVisitPK userVisitPK, DeleteEntityAliasForm form);

    // -------------------------------------------------------------------------
    //   Entity Attribute Groups
    // -------------------------------------------------------------------------
    
    CommandResult createEntityAttributeGroup(UserVisitPK userVisitPK, CreateEntityAttributeGroupForm form);
    
    CommandResult getEntityAttributeGroup(UserVisitPK userVisitPK, GetEntityAttributeGroupForm form);
    
    CommandResult getEntityAttributeGroups(UserVisitPK userVisitPK, GetEntityAttributeGroupsForm form);
    
    CommandResult getEntityAttributeGroupChoices(UserVisitPK userVisitPK, GetEntityAttributeGroupChoicesForm form);
    
    CommandResult setDefaultEntityAttributeGroup(UserVisitPK userVisitPK, SetDefaultEntityAttributeGroupForm form);
    
    CommandResult editEntityAttributeGroup(UserVisitPK userVisitPK, EditEntityAttributeGroupForm form);
    
    CommandResult deleteEntityAttributeGroup(UserVisitPK userVisitPK, DeleteEntityAttributeGroupForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Attribute Group Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createEntityAttributeGroupDescription(UserVisitPK userVisitPK, CreateEntityAttributeGroupDescriptionForm form);
    
    CommandResult getEntityAttributeGroupDescription(UserVisitPK userVisitPK, GetEntityAttributeGroupDescriptionForm form);
    
    CommandResult getEntityAttributeGroupDescriptions(UserVisitPK userVisitPK, GetEntityAttributeGroupDescriptionsForm form);
    
    CommandResult editEntityAttributeGroupDescription(UserVisitPK userVisitPK, EditEntityAttributeGroupDescriptionForm form);
    
    CommandResult deleteEntityAttributeGroupDescription(UserVisitPK userVisitPK, DeleteEntityAttributeGroupDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Attribute Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createEntityAttributeDescription(UserVisitPK userVisitPK, CreateEntityAttributeDescriptionForm form);
    
    CommandResult getEntityAttributeDescription(UserVisitPK userVisitPK, GetEntityAttributeDescriptionForm form);
    
    CommandResult getEntityAttributeDescriptions(UserVisitPK userVisitPK, GetEntityAttributeDescriptionsForm form);
    
    CommandResult editEntityAttributeDescription(UserVisitPK userVisitPK, EditEntityAttributeDescriptionForm form);
    
    CommandResult deleteEntityAttributeDescription(UserVisitPK userVisitPK, DeleteEntityAttributeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Attributes
    // -------------------------------------------------------------------------
    
    CommandResult createEntityAttribute(UserVisitPK userVisitPK, CreateEntityAttributeForm form);
    
    CommandResult getEntityAttribute(UserVisitPK userVisitPK, GetEntityAttributeForm form);
    
    CommandResult getEntityAttributes(UserVisitPK userVisitPK, GetEntityAttributesForm form);
    
    CommandResult editEntityAttribute(UserVisitPK userVisitPK, EditEntityAttributeForm form);
    
    CommandResult deleteEntityAttribute(UserVisitPK userVisitPK, DeleteEntityAttributeForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Attribute Entity Attribute Groups
    // -------------------------------------------------------------------------
    
    CommandResult createEntityAttributeEntityAttributeGroup(UserVisitPK userVisitPK, CreateEntityAttributeEntityAttributeGroupForm form);
    
    CommandResult getEntityAttributeEntityAttributeGroup(UserVisitPK userVisitPK, GetEntityAttributeEntityAttributeGroupForm form);
    
    CommandResult getEntityAttributeEntityAttributeGroups(UserVisitPK userVisitPK, GetEntityAttributeEntityAttributeGroupsForm form);
    
    CommandResult editEntityAttributeEntityAttributeGroup(UserVisitPK userVisitPK, EditEntityAttributeEntityAttributeGroupForm form);
    
    CommandResult deleteEntityAttributeEntityAttributeGroup(UserVisitPK userVisitPK, DeleteEntityAttributeEntityAttributeGroupForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Attribute Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createEntityAttributeTypeDescription(UserVisitPK userVisitPK, CreateEntityAttributeTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Attribute Types
    // -------------------------------------------------------------------------
    
    CommandResult createEntityAttributeType(UserVisitPK userVisitPK, CreateEntityAttributeTypeForm form);
    
    CommandResult getEntityAttributeType(UserVisitPK userVisitPK, GetEntityAttributeTypeForm form);
    
    CommandResult getEntityAttributeTypes(UserVisitPK userVisitPK, GetEntityAttributeTypesForm form);
    
    CommandResult getEntityAttributeTypeChoices(UserVisitPK userVisitPK, GetEntityAttributeTypeChoicesForm form);
    
    // -------------------------------------------------------------------------
    //   Entity List Item Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createEntityListItemDescription(UserVisitPK userVisitPK, CreateEntityListItemDescriptionForm form);
    
    CommandResult getEntityListItemDescription(UserVisitPK userVisitPK, GetEntityListItemDescriptionForm form);
    
    CommandResult getEntityListItemDescriptions(UserVisitPK userVisitPK, GetEntityListItemDescriptionsForm form);
    
    CommandResult editEntityListItemDescription(UserVisitPK userVisitPK, EditEntityListItemDescriptionForm form);
    
    CommandResult deleteEntityListItemDescription(UserVisitPK userVisitPK, DeleteEntityListItemDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Entity List Items
    // -------------------------------------------------------------------------
    
    CommandResult createEntityListItem(UserVisitPK userVisitPK, CreateEntityListItemForm form);
    
    CommandResult getEntityListItem(UserVisitPK userVisitPK, GetEntityListItemForm form);
    
    CommandResult getEntityListItems(UserVisitPK userVisitPK, GetEntityListItemsForm form);
    
    CommandResult getEntityListItemChoices(UserVisitPK userVisitPK, GetEntityListItemChoicesForm form);
    
    CommandResult setDefaultEntityListItem(UserVisitPK userVisitPK, SetDefaultEntityListItemForm form);
    
    CommandResult editEntityListItem(UserVisitPK userVisitPK, EditEntityListItemForm form);
    
    CommandResult deleteEntityListItem(UserVisitPK userVisitPK, DeleteEntityListItemForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Integer Range Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createEntityIntegerRangeDescription(UserVisitPK userVisitPK, CreateEntityIntegerRangeDescriptionForm form);
    
    CommandResult getEntityIntegerRangeDescription(UserVisitPK userVisitPK, GetEntityIntegerRangeDescriptionForm form);
    
    CommandResult getEntityIntegerRangeDescriptions(UserVisitPK userVisitPK, GetEntityIntegerRangeDescriptionsForm form);
    
    CommandResult editEntityIntegerRangeDescription(UserVisitPK userVisitPK, EditEntityIntegerRangeDescriptionForm form);
    
    CommandResult deleteEntityIntegerRangeDescription(UserVisitPK userVisitPK, DeleteEntityIntegerRangeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Integer Ranges
    // -------------------------------------------------------------------------
    
    CommandResult createEntityIntegerRange(UserVisitPK userVisitPK, CreateEntityIntegerRangeForm form);
    
    CommandResult getEntityIntegerRange(UserVisitPK userVisitPK, GetEntityIntegerRangeForm form);
    
    CommandResult getEntityIntegerRanges(UserVisitPK userVisitPK, GetEntityIntegerRangesForm form);
    
    CommandResult getEntityIntegerRangeChoices(UserVisitPK userVisitPK, GetEntityIntegerRangeChoicesForm form);
    
    CommandResult setDefaultEntityIntegerRange(UserVisitPK userVisitPK, SetDefaultEntityIntegerRangeForm form);
    
    CommandResult editEntityIntegerRange(UserVisitPK userVisitPK, EditEntityIntegerRangeForm form);
    
    CommandResult deleteEntityIntegerRange(UserVisitPK userVisitPK, DeleteEntityIntegerRangeForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Long Range Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createEntityLongRangeDescription(UserVisitPK userVisitPK, CreateEntityLongRangeDescriptionForm form);
    
    CommandResult getEntityLongRangeDescription(UserVisitPK userVisitPK, GetEntityLongRangeDescriptionForm form);
    
    CommandResult getEntityLongRangeDescriptions(UserVisitPK userVisitPK, GetEntityLongRangeDescriptionsForm form);
    
    CommandResult editEntityLongRangeDescription(UserVisitPK userVisitPK, EditEntityLongRangeDescriptionForm form);
    
    CommandResult deleteEntityLongRangeDescription(UserVisitPK userVisitPK, DeleteEntityLongRangeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Long Ranges
    // -------------------------------------------------------------------------
    
    CommandResult createEntityLongRange(UserVisitPK userVisitPK, CreateEntityLongRangeForm form);
    
    CommandResult getEntityLongRange(UserVisitPK userVisitPK, GetEntityLongRangeForm form);
    
    CommandResult getEntityLongRanges(UserVisitPK userVisitPK, GetEntityLongRangesForm form);
    
    CommandResult getEntityLongRangeChoices(UserVisitPK userVisitPK, GetEntityLongRangeChoicesForm form);
    
    CommandResult setDefaultEntityLongRange(UserVisitPK userVisitPK, SetDefaultEntityLongRangeForm form);
    
    CommandResult editEntityLongRange(UserVisitPK userVisitPK, EditEntityLongRangeForm form);
    
    CommandResult deleteEntityLongRange(UserVisitPK userVisitPK, DeleteEntityLongRangeForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createEntityTypeDescription(UserVisitPK userVisitPK, CreateEntityTypeDescriptionForm form);
    
    CommandResult getEntityTypeDescription(UserVisitPK userVisitPK, GetEntityTypeDescriptionForm form);
    
    CommandResult getEntityTypeDescriptions(UserVisitPK userVisitPK, GetEntityTypeDescriptionsForm form);
    
    CommandResult editEntityTypeDescription(UserVisitPK userVisitPK, EditEntityTypeDescriptionForm form);
    
    CommandResult deleteEntityTypeDescription(UserVisitPK userVisitPK, DeleteEntityTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Mime Type Usage Types
    // --------------------------------------------------------------------------------
    
    CommandResult createMimeTypeUsageType(UserVisitPK userVisitPK, CreateMimeTypeUsageTypeForm form);
    
    CommandResult getMimeTypeUsageType(UserVisitPK userVisitPK, GetMimeTypeUsageTypeForm form);

    CommandResult getMimeTypeUsageTypes(UserVisitPK userVisitPK, GetMimeTypeUsageTypesForm form);

    CommandResult getMimeTypeUsageTypeChoices(UserVisitPK userVisitPK, GetMimeTypeUsageTypeChoicesForm form);
    
    // --------------------------------------------------------------------------------
    //   Mime Type Usage Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createMimeTypeUsageTypeDescription(UserVisitPK userVisitPK, CreateMimeTypeUsageTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Mime Types
    // -------------------------------------------------------------------------

    CommandResult createMimeType(UserVisitPK userVisitPK, CreateMimeTypeForm form);

    CommandResult getMimeTypes(UserVisitPK userVisitPK, GetMimeTypesForm form);

    CommandResult getMimeType(UserVisitPK userVisitPK, GetMimeTypeForm form);

    CommandResult getMimeTypeChoices(UserVisitPK userVisitPK, GetMimeTypeChoicesForm form);

    CommandResult setDefaultMimeType(UserVisitPK userVisitPK, SetDefaultMimeTypeForm form);

    CommandResult editMimeType(UserVisitPK userVisitPK, EditMimeTypeForm form);

    CommandResult deleteMimeType(UserVisitPK userVisitPK, DeleteMimeTypeForm form);

    // -------------------------------------------------------------------------
    //   Mime Type Descriptions
    // -------------------------------------------------------------------------

    CommandResult createMimeTypeDescription(UserVisitPK userVisitPK, CreateMimeTypeDescriptionForm form);

    CommandResult getMimeTypeDescription(UserVisitPK userVisitPK, GetMimeTypeDescriptionForm form);

    CommandResult getMimeTypeDescriptions(UserVisitPK userVisitPK, GetMimeTypeDescriptionsForm form);

    CommandResult editMimeTypeDescription(UserVisitPK userVisitPK, EditMimeTypeDescriptionForm form);

    CommandResult deleteMimeTypeDescription(UserVisitPK userVisitPK, DeleteMimeTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Mime Type Usages
    // --------------------------------------------------------------------------------
    
    CommandResult createMimeTypeUsage(UserVisitPK userVisitPK, CreateMimeTypeUsageForm form);
    
    CommandResult getMimeTypeUsages(UserVisitPK userVisitPK, GetMimeTypeUsagesForm form);
    
    // --------------------------------------------------------------------------------
    //   Mime Type File Extensions
    // --------------------------------------------------------------------------------
    
    CommandResult createMimeTypeFileExtension(UserVisitPK userVisitPK, CreateMimeTypeFileExtensionForm form);
    
    CommandResult getMimeTypeFileExtension(UserVisitPK userVisitPK, GetMimeTypeFileExtensionForm form);
    
    CommandResult getMimeTypeFileExtensions(UserVisitPK userVisitPK, GetMimeTypeFileExtensionsForm form);
    
    // --------------------------------------------------------------------------------
    //   Protocols
    // --------------------------------------------------------------------------------

    CommandResult createProtocol(UserVisitPK userVisitPK, CreateProtocolForm form);

    CommandResult getProtocolChoices(UserVisitPK userVisitPK, GetProtocolChoicesForm form);

    CommandResult getProtocol(UserVisitPK userVisitPK, GetProtocolForm form);

    CommandResult getProtocols(UserVisitPK userVisitPK, GetProtocolsForm form);

    CommandResult setDefaultProtocol(UserVisitPK userVisitPK, SetDefaultProtocolForm form);

    CommandResult editProtocol(UserVisitPK userVisitPK, EditProtocolForm form);

    CommandResult deleteProtocol(UserVisitPK userVisitPK, DeleteProtocolForm form);

    // --------------------------------------------------------------------------------
    //   Protocol Descriptions
    // --------------------------------------------------------------------------------

    CommandResult createProtocolDescription(UserVisitPK userVisitPK, CreateProtocolDescriptionForm form);

    CommandResult getProtocolDescription(UserVisitPK userVisitPK, GetProtocolDescriptionForm form);

    CommandResult getProtocolDescriptions(UserVisitPK userVisitPK, GetProtocolDescriptionsForm form);

    CommandResult editProtocolDescription(UserVisitPK userVisitPK, EditProtocolDescriptionForm form);

    CommandResult deleteProtocolDescription(UserVisitPK userVisitPK, DeleteProtocolDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Services
    // --------------------------------------------------------------------------------

    CommandResult createService(UserVisitPK userVisitPK, CreateServiceForm form);

    CommandResult getServiceChoices(UserVisitPK userVisitPK, GetServiceChoicesForm form);

    CommandResult getService(UserVisitPK userVisitPK, GetServiceForm form);

    CommandResult getServices(UserVisitPK userVisitPK, GetServicesForm form);

    CommandResult setDefaultService(UserVisitPK userVisitPK, SetDefaultServiceForm form);

    CommandResult editService(UserVisitPK userVisitPK, EditServiceForm form);

    CommandResult deleteService(UserVisitPK userVisitPK, DeleteServiceForm form);

    // --------------------------------------------------------------------------------
    //   Service Descriptions
    // --------------------------------------------------------------------------------

    CommandResult createServiceDescription(UserVisitPK userVisitPK, CreateServiceDescriptionForm form);

    CommandResult getServiceDescription(UserVisitPK userVisitPK, GetServiceDescriptionForm form);

    CommandResult getServiceDescriptions(UserVisitPK userVisitPK, GetServiceDescriptionsForm form);

    CommandResult editServiceDescription(UserVisitPK userVisitPK, EditServiceDescriptionForm form);

    CommandResult deleteServiceDescription(UserVisitPK userVisitPK, DeleteServiceDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Servers
    // --------------------------------------------------------------------------------

    CommandResult createServer(UserVisitPK userVisitPK, CreateServerForm form);

    CommandResult getServerChoices(UserVisitPK userVisitPK, GetServerChoicesForm form);

    CommandResult getServer(UserVisitPK userVisitPK, GetServerForm form);

    CommandResult getServers(UserVisitPK userVisitPK, GetServersForm form);

    CommandResult setDefaultServer(UserVisitPK userVisitPK, SetDefaultServerForm form);

    CommandResult editServer(UserVisitPK userVisitPK, EditServerForm form);

    CommandResult deleteServer(UserVisitPK userVisitPK, DeleteServerForm form);

    // --------------------------------------------------------------------------------
    //   Server Descriptions
    // --------------------------------------------------------------------------------

    CommandResult createServerDescription(UserVisitPK userVisitPK, CreateServerDescriptionForm form);

    CommandResult getServerDescription(UserVisitPK userVisitPK, GetServerDescriptionForm form);

    CommandResult getServerDescriptions(UserVisitPK userVisitPK, GetServerDescriptionsForm form);

    CommandResult editServerDescription(UserVisitPK userVisitPK, EditServerDescriptionForm form);

    CommandResult deleteServerDescription(UserVisitPK userVisitPK, DeleteServerDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Server Services
    // --------------------------------------------------------------------------------

    CommandResult createServerService(UserVisitPK userVisitPK, CreateServerServiceForm form);

    CommandResult getServerService(UserVisitPK userVisitPK, GetServerServiceForm form);

    CommandResult getServerServices(UserVisitPK userVisitPK, GetServerServicesForm form);

    CommandResult deleteServerService(UserVisitPK userVisitPK, DeleteServerServiceForm form);

    // --------------------------------------------------------------------------------
    //   Entity Boolean Defaults
    // --------------------------------------------------------------------------------

    CommandResult createEntityBooleanDefault(UserVisitPK userVisitPK, CreateEntityBooleanDefaultForm form);

    CommandResult editEntityBooleanDefault(UserVisitPK userVisitPK, EditEntityBooleanDefaultForm form);

    CommandResult deleteEntityBooleanDefault(UserVisitPK userVisitPK, DeleteEntityBooleanDefaultForm form);

    // --------------------------------------------------------------------------------
    //   Entity Boolean Attributes
    // --------------------------------------------------------------------------------

    CommandResult createEntityBooleanAttribute(UserVisitPK userVisitPK, CreateEntityBooleanAttributeForm form);

    CommandResult editEntityBooleanAttribute(UserVisitPK userVisitPK, EditEntityBooleanAttributeForm form);

    CommandResult deleteEntityBooleanAttribute(UserVisitPK userVisitPK, DeleteEntityBooleanAttributeForm form);

    // --------------------------------------------------------------------------------
    //   Entity Integer Defaults
    // --------------------------------------------------------------------------------

    CommandResult createEntityIntegerDefault(UserVisitPK userVisitPK, CreateEntityIntegerDefaultForm form);

    CommandResult editEntityIntegerDefault(UserVisitPK userVisitPK, EditEntityIntegerDefaultForm form);

    CommandResult deleteEntityIntegerDefault(UserVisitPK userVisitPK, DeleteEntityIntegerDefaultForm form);

    // --------------------------------------------------------------------------------
    //   Entity Integer Attributes
    // --------------------------------------------------------------------------------
    
    CommandResult createEntityIntegerAttribute(UserVisitPK userVisitPK, CreateEntityIntegerAttributeForm form);
    
    CommandResult editEntityIntegerAttribute(UserVisitPK userVisitPK, EditEntityIntegerAttributeForm form);
    
    CommandResult deleteEntityIntegerAttribute(UserVisitPK userVisitPK, DeleteEntityIntegerAttributeForm form);

    // --------------------------------------------------------------------------------
    //   Entity List Item Defaults
    // --------------------------------------------------------------------------------

    CommandResult createEntityListItemDefault(UserVisitPK userVisitPK, CreateEntityListItemDefaultForm form);

    CommandResult editEntityListItemDefault(UserVisitPK userVisitPK, EditEntityListItemDefaultForm form);

    CommandResult deleteEntityListItemDefault(UserVisitPK userVisitPK, DeleteEntityListItemDefaultForm form);

    // --------------------------------------------------------------------------------
    //   Entity List Item Attributes
    // --------------------------------------------------------------------------------
    
    CommandResult createEntityListItemAttribute(UserVisitPK userVisitPK, CreateEntityListItemAttributeForm form);
    
    CommandResult editEntityListItemAttribute(UserVisitPK userVisitPK, EditEntityListItemAttributeForm form);
    
    CommandResult deleteEntityListItemAttribute(UserVisitPK userVisitPK, DeleteEntityListItemAttributeForm form);

    // --------------------------------------------------------------------------------
    //   Entity Long Defaults
    // --------------------------------------------------------------------------------

    CommandResult createEntityLongDefault(UserVisitPK userVisitPK, CreateEntityLongDefaultForm form);

    CommandResult editEntityLongDefault(UserVisitPK userVisitPK, EditEntityLongDefaultForm form);

    CommandResult deleteEntityLongDefault(UserVisitPK userVisitPK, DeleteEntityLongDefaultForm form);

    // --------------------------------------------------------------------------------
    //   Entity Long Attributes
    // --------------------------------------------------------------------------------
    
    CommandResult createEntityLongAttribute(UserVisitPK userVisitPK, CreateEntityLongAttributeForm form);
    
    CommandResult editEntityLongAttribute(UserVisitPK userVisitPK, EditEntityLongAttributeForm form);
    
    CommandResult deleteEntityLongAttribute(UserVisitPK userVisitPK, DeleteEntityLongAttributeForm form);

    // --------------------------------------------------------------------------------
    //   Entity Multiple List Item Defaults
    // --------------------------------------------------------------------------------

    CommandResult createEntityMultipleListItemDefault(UserVisitPK userVisitPK, CreateEntityMultipleListItemDefaultForm form);

    CommandResult deleteEntityMultipleListItemDefault(UserVisitPK userVisitPK, DeleteEntityMultipleListItemDefaultForm form);

    // --------------------------------------------------------------------------------
    //   Entity Multiple List Item Attributes
    // --------------------------------------------------------------------------------

    CommandResult createEntityMultipleListItemAttribute(UserVisitPK userVisitPK, CreateEntityMultipleListItemAttributeForm form);

    CommandResult deleteEntityMultipleListItemAttribute(UserVisitPK userVisitPK, DeleteEntityMultipleListItemAttributeForm form);

    // --------------------------------------------------------------------------------
    //   Entity Name Attributes
    // --------------------------------------------------------------------------------
    
    CommandResult createEntityNameAttribute(UserVisitPK userVisitPK, CreateEntityNameAttributeForm form);
    
    CommandResult editEntityNameAttribute(UserVisitPK userVisitPK, EditEntityNameAttributeForm form);
    
    CommandResult deleteEntityNameAttribute(UserVisitPK userVisitPK, DeleteEntityNameAttributeForm form);

    // --------------------------------------------------------------------------------
    //   Entity String Defaults
    // --------------------------------------------------------------------------------

    CommandResult createEntityStringDefault(UserVisitPK userVisitPK, CreateEntityStringDefaultForm form);

    CommandResult editEntityStringDefault(UserVisitPK userVisitPK, EditEntityStringDefaultForm form);

    CommandResult deleteEntityStringDefault(UserVisitPK userVisitPK, DeleteEntityStringDefaultForm form);

    // --------------------------------------------------------------------------------
    //   Entity String Attributes
    // --------------------------------------------------------------------------------
    
    CommandResult createEntityStringAttribute(UserVisitPK userVisitPK, CreateEntityStringAttributeForm form);
    
    CommandResult editEntityStringAttribute(UserVisitPK userVisitPK, EditEntityStringAttributeForm form);
    
    CommandResult deleteEntityStringAttribute(UserVisitPK userVisitPK, DeleteEntityStringAttributeForm form);

    // --------------------------------------------------------------------------------
    //   Entity Geo Point Defaults
    // --------------------------------------------------------------------------------

    CommandResult createEntityGeoPointDefault(UserVisitPK userVisitPK, CreateEntityGeoPointDefaultForm form);

    CommandResult editEntityGeoPointDefault(UserVisitPK userVisitPK, EditEntityGeoPointDefaultForm form);

    CommandResult deleteEntityGeoPointDefault(UserVisitPK userVisitPK, DeleteEntityGeoPointDefaultForm form);

    // --------------------------------------------------------------------------------
    //   Entity Geo Point Attributes
    // --------------------------------------------------------------------------------

    CommandResult createEntityGeoPointAttribute(UserVisitPK userVisitPK, CreateEntityGeoPointAttributeForm form);

    CommandResult editEntityGeoPointAttribute(UserVisitPK userVisitPK, EditEntityGeoPointAttributeForm form);

    CommandResult deleteEntityGeoPointAttribute(UserVisitPK userVisitPK, DeleteEntityGeoPointAttributeForm form);

    // --------------------------------------------------------------------------------
    //   Entity Blob Attributes
    // --------------------------------------------------------------------------------
    
    CommandResult createEntityBlobAttribute(UserVisitPK userVisitPK, CreateEntityBlobAttributeForm form);

    CommandResult editEntityBlobAttribute(UserVisitPK userVisitPK, EditEntityBlobAttributeForm form);

    CommandResult getEntityBlobAttribute(UserVisitPK userVisitPK, GetEntityBlobAttributeForm form);

    CommandResult deleteEntityBlobAttribute(UserVisitPK userVisitPK, DeleteEntityBlobAttributeForm form);
    
    // --------------------------------------------------------------------------------
    //   Entity Clob Attributes
    // --------------------------------------------------------------------------------
    
    CommandResult createEntityClobAttribute(UserVisitPK userVisitPK, CreateEntityClobAttributeForm form);

    CommandResult editEntityClobAttribute(UserVisitPK userVisitPK, EditEntityClobAttributeForm form);

    CommandResult getEntityClobAttribute(UserVisitPK userVisitPK, GetEntityClobAttributeForm form);

    CommandResult deleteEntityClobAttribute(UserVisitPK userVisitPK, DeleteEntityClobAttributeForm form);

    // --------------------------------------------------------------------------------
    //   Entity Time Defaults
    // --------------------------------------------------------------------------------

    CommandResult createEntityTimeDefault(UserVisitPK userVisitPK, CreateEntityTimeDefaultForm form);

    CommandResult editEntityTimeDefault(UserVisitPK userVisitPK, EditEntityTimeDefaultForm form);

    CommandResult deleteEntityTimeDefault(UserVisitPK userVisitPK, DeleteEntityTimeDefaultForm form);

    // --------------------------------------------------------------------------------
    //   Entity Time Attributes
    // --------------------------------------------------------------------------------

    CommandResult createEntityTimeAttribute(UserVisitPK userVisitPK, CreateEntityTimeAttributeForm form);

    CommandResult editEntityTimeAttribute(UserVisitPK userVisitPK, EditEntityTimeAttributeForm form);

    CommandResult deleteEntityTimeAttribute(UserVisitPK userVisitPK, DeleteEntityTimeAttributeForm form);

    // --------------------------------------------------------------------------------
    //   Entity Date Defaults
    // --------------------------------------------------------------------------------

    CommandResult createEntityDateDefault(UserVisitPK userVisitPK, CreateEntityDateDefaultForm form);

    CommandResult editEntityDateDefault(UserVisitPK userVisitPK, EditEntityDateDefaultForm form);

    CommandResult deleteEntityDateDefault(UserVisitPK userVisitPK, DeleteEntityDateDefaultForm form);

    // --------------------------------------------------------------------------------
    //   Entity Date Attributes
    // --------------------------------------------------------------------------------

    CommandResult createEntityDateAttribute(UserVisitPK userVisitPK, CreateEntityDateAttributeForm form);

    CommandResult editEntityDateAttribute(UserVisitPK userVisitPK, EditEntityDateAttributeForm form);

    CommandResult deleteEntityDateAttribute(UserVisitPK userVisitPK, DeleteEntityDateAttributeForm form);

    // --------------------------------------------------------------------------------
    //   Entity Attribute Entity Types
    // --------------------------------------------------------------------------------
    
    CommandResult createEntityAttributeEntityType(UserVisitPK userVisitPK, CreateEntityAttributeEntityTypeForm form);

    CommandResult getEntityAttributeEntityType(UserVisitPK userVisitPK, GetEntityAttributeEntityTypeForm form);

    CommandResult getEntityAttributeEntityTypes(UserVisitPK userVisitPK, GetEntityAttributeEntityTypesForm form);
    
    CommandResult deleteEntityAttributeEntityType(UserVisitPK userVisitPK, DeleteEntityAttributeEntityTypeForm form);

    // --------------------------------------------------------------------------------
    //   Entity Entity Attributes
    // --------------------------------------------------------------------------------
    
    CommandResult createEntityEntityAttribute(UserVisitPK userVisitPK, CreateEntityEntityAttributeForm form);
    
    CommandResult editEntityEntityAttribute(UserVisitPK userVisitPK, EditEntityEntityAttributeForm form);
    
    CommandResult deleteEntityEntityAttribute(UserVisitPK userVisitPK, DeleteEntityEntityAttributeForm form);

    // --------------------------------------------------------------------------------
    //   Entity Collection Attributes
    // --------------------------------------------------------------------------------

    CommandResult createEntityCollectionAttribute(UserVisitPK userVisitPK, CreateEntityCollectionAttributeForm form);

    CommandResult deleteEntityCollectionAttribute(UserVisitPK userVisitPK, DeleteEntityCollectionAttributeForm form);

    // --------------------------------------------------------------------------------
    //   Entity Workflow Attributes
    // --------------------------------------------------------------------------------

    CommandResult createEntityWorkflowAttribute(UserVisitPK userVisitPK, CreateEntityWorkflowAttributeForm form);

    CommandResult setEntityWorkflowAttributeStatus(UserVisitPK userVisitPK, SetEntityWorkflowAttributeStatusForm form);

    CommandResult deleteEntityWorkflowAttribute(UserVisitPK userVisitPK, DeleteEntityWorkflowAttributeForm form);

    // -------------------------------------------------------------------------
    //   Cache Entries
    // -------------------------------------------------------------------------

    CommandResult createCacheEntry(UserVisitPK userVisitPK, CreateCacheEntryForm form);

    CommandResult getCacheEntry(UserVisitPK userVisitPK, GetCacheEntryForm form);

    CommandResult getCacheEntries(UserVisitPK userVisitPK, GetCacheEntriesForm form);

    CommandResult removeCacheEntry(UserVisitPK userVisitPK, RemoveCacheEntryForm form);

    // -------------------------------------------------------------------------
    //   Cache Entry Dependencies
    // -------------------------------------------------------------------------

    CommandResult getCacheEntryDependencies(UserVisitPK userVisitPK, GetCacheEntryDependenciesForm form);

    // --------------------------------------------------------------------------------
    //   Applications
    // --------------------------------------------------------------------------------
    
    CommandResult createApplication(UserVisitPK userVisitPK, CreateApplicationForm form);
    
    CommandResult getApplicationChoices(UserVisitPK userVisitPK, GetApplicationChoicesForm form);
    
    CommandResult getApplication(UserVisitPK userVisitPK, GetApplicationForm form);
    
    CommandResult getApplications(UserVisitPK userVisitPK, GetApplicationsForm form);
    
    CommandResult setDefaultApplication(UserVisitPK userVisitPK, SetDefaultApplicationForm form);
    
    CommandResult editApplication(UserVisitPK userVisitPK, EditApplicationForm form);
    
    CommandResult deleteApplication(UserVisitPK userVisitPK, DeleteApplicationForm form);
    
    // --------------------------------------------------------------------------------
    //   Application Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createApplicationDescription(UserVisitPK userVisitPK, CreateApplicationDescriptionForm form);
    
    CommandResult getApplicationDescription(UserVisitPK userVisitPK, GetApplicationDescriptionForm form);
    
    CommandResult getApplicationDescriptions(UserVisitPK userVisitPK, GetApplicationDescriptionsForm form);
    
    CommandResult editApplicationDescription(UserVisitPK userVisitPK, EditApplicationDescriptionForm form);
    
    CommandResult deleteApplicationDescription(UserVisitPK userVisitPK, DeleteApplicationDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Editors
    // --------------------------------------------------------------------------------
    
    CommandResult createEditor(UserVisitPK userVisitPK, CreateEditorForm form);
    
    CommandResult getEditorChoices(UserVisitPK userVisitPK, GetEditorChoicesForm form);
    
    CommandResult getEditor(UserVisitPK userVisitPK, GetEditorForm form);
    
    CommandResult getEditors(UserVisitPK userVisitPK, GetEditorsForm form);
    
    CommandResult setDefaultEditor(UserVisitPK userVisitPK, SetDefaultEditorForm form);
    
    CommandResult editEditor(UserVisitPK userVisitPK, EditEditorForm form);
    
    CommandResult deleteEditor(UserVisitPK userVisitPK, DeleteEditorForm form);
    
    // --------------------------------------------------------------------------------
    //   Editor Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createEditorDescription(UserVisitPK userVisitPK, CreateEditorDescriptionForm form);
    
    CommandResult getEditorDescription(UserVisitPK userVisitPK, GetEditorDescriptionForm form);
    
    CommandResult getEditorDescriptions(UserVisitPK userVisitPK, GetEditorDescriptionsForm form);
    
    CommandResult editEditorDescription(UserVisitPK userVisitPK, EditEditorDescriptionForm form);
    
    CommandResult deleteEditorDescription(UserVisitPK userVisitPK, DeleteEditorDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Application Editors
    // --------------------------------------------------------------------------------
    
    CommandResult createApplicationEditor(UserVisitPK userVisitPK, CreateApplicationEditorForm form);
    
    CommandResult getApplicationEditorChoices(UserVisitPK userVisitPK, GetApplicationEditorChoicesForm form);
    
    CommandResult getApplicationEditor(UserVisitPK userVisitPK, GetApplicationEditorForm form);
    
    CommandResult getApplicationEditors(UserVisitPK userVisitPK, GetApplicationEditorsForm form);
    
    CommandResult setDefaultApplicationEditor(UserVisitPK userVisitPK, SetDefaultApplicationEditorForm form);
    
    CommandResult editApplicationEditor(UserVisitPK userVisitPK, EditApplicationEditorForm form);
    
    CommandResult deleteApplicationEditor(UserVisitPK userVisitPK, DeleteApplicationEditorForm form);
    
    // --------------------------------------------------------------------------------
    //   Application Editor Uses
    // --------------------------------------------------------------------------------
    
    CommandResult createApplicationEditorUse(UserVisitPK userVisitPK, CreateApplicationEditorUseForm form);
    
    CommandResult getApplicationEditorUseChoices(UserVisitPK userVisitPK, GetApplicationEditorUseChoicesForm form);
    
    CommandResult getApplicationEditorUse(UserVisitPK userVisitPK, GetApplicationEditorUseForm form);
    
    CommandResult getApplicationEditorUses(UserVisitPK userVisitPK, GetApplicationEditorUsesForm form);
    
    CommandResult setDefaultApplicationEditorUse(UserVisitPK userVisitPK, SetDefaultApplicationEditorUseForm form);
    
    CommandResult editApplicationEditorUse(UserVisitPK userVisitPK, EditApplicationEditorUseForm form);
    
    CommandResult deleteApplicationEditorUse(UserVisitPK userVisitPK, DeleteApplicationEditorUseForm form);
    
    // --------------------------------------------------------------------------------
    //   Application Editor Use Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createApplicationEditorUseDescription(UserVisitPK userVisitPK, CreateApplicationEditorUseDescriptionForm form);
    
    CommandResult getApplicationEditorUseDescription(UserVisitPK userVisitPK, GetApplicationEditorUseDescriptionForm form);
    
    CommandResult getApplicationEditorUseDescriptions(UserVisitPK userVisitPK, GetApplicationEditorUseDescriptionsForm form);
    
    CommandResult editApplicationEditorUseDescription(UserVisitPK userVisitPK, EditApplicationEditorUseDescriptionForm form);
    
    CommandResult deleteApplicationEditorUseDescription(UserVisitPK userVisitPK, DeleteApplicationEditorUseDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Appearances
    // --------------------------------------------------------------------------------
    
    CommandResult createAppearance(UserVisitPK userVisitPK, CreateAppearanceForm form);
    
    CommandResult getAppearanceChoices(UserVisitPK userVisitPK, GetAppearanceChoicesForm form);
    
    CommandResult getAppearance(UserVisitPK userVisitPK, GetAppearanceForm form);
    
    CommandResult getAppearances(UserVisitPK userVisitPK, GetAppearancesForm form);
    
    CommandResult setDefaultAppearance(UserVisitPK userVisitPK, SetDefaultAppearanceForm form);
    
    CommandResult editAppearance(UserVisitPK userVisitPK, EditAppearanceForm form);
    
    CommandResult deleteAppearance(UserVisitPK userVisitPK, DeleteAppearanceForm form);
    
    // --------------------------------------------------------------------------------
    //   Appearance Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createAppearanceDescription(UserVisitPK userVisitPK, CreateAppearanceDescriptionForm form);
    
    CommandResult getAppearanceDescription(UserVisitPK userVisitPK, GetAppearanceDescriptionForm form);
    
    CommandResult getAppearanceDescriptions(UserVisitPK userVisitPK, GetAppearanceDescriptionsForm form);
    
    CommandResult editAppearanceDescription(UserVisitPK userVisitPK, EditAppearanceDescriptionForm form);
    
    CommandResult deleteAppearanceDescription(UserVisitPK userVisitPK, DeleteAppearanceDescriptionForm form);
    
     // --------------------------------------------------------------------------------
    //   Appearance Text Decorations
    // --------------------------------------------------------------------------------
    
    CommandResult createAppearanceTextDecoration(UserVisitPK userVisitPK, CreateAppearanceTextDecorationForm form);
    
    CommandResult getAppearanceTextDecoration(UserVisitPK userVisitPK, GetAppearanceTextDecorationForm form);
    
    CommandResult getAppearanceTextDecorations(UserVisitPK userVisitPK, GetAppearanceTextDecorationsForm form);
    
    CommandResult deleteAppearanceTextDecoration(UserVisitPK userVisitPK, DeleteAppearanceTextDecorationForm form);
    
     // --------------------------------------------------------------------------------
    //   Appearance Text Transformations
    // --------------------------------------------------------------------------------
    
    CommandResult createAppearanceTextTransformation(UserVisitPK userVisitPK, CreateAppearanceTextTransformationForm form);
    
    CommandResult getAppearanceTextTransformation(UserVisitPK userVisitPK, GetAppearanceTextTransformationForm form);
    
    CommandResult getAppearanceTextTransformations(UserVisitPK userVisitPK, GetAppearanceTextTransformationsForm form);
    
    CommandResult deleteAppearanceTextTransformation(UserVisitPK userVisitPK, DeleteAppearanceTextTransformationForm form);
    
   // --------------------------------------------------------------------------------
    //   Colors
    // --------------------------------------------------------------------------------
    
    CommandResult createColor(UserVisitPK userVisitPK, CreateColorForm form);
    
    CommandResult getColorChoices(UserVisitPK userVisitPK, GetColorChoicesForm form);
    
    CommandResult getColor(UserVisitPK userVisitPK, GetColorForm form);
    
    CommandResult getColors(UserVisitPK userVisitPK, GetColorsForm form);
    
    CommandResult setDefaultColor(UserVisitPK userVisitPK, SetDefaultColorForm form);
    
    CommandResult editColor(UserVisitPK userVisitPK, EditColorForm form);
    
    CommandResult deleteColor(UserVisitPK userVisitPK, DeleteColorForm form);
    
    // --------------------------------------------------------------------------------
    //   Color Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createColorDescription(UserVisitPK userVisitPK, CreateColorDescriptionForm form);
    
    CommandResult getColorDescription(UserVisitPK userVisitPK, GetColorDescriptionForm form);
    
    CommandResult getColorDescriptions(UserVisitPK userVisitPK, GetColorDescriptionsForm form);
    
    CommandResult editColorDescription(UserVisitPK userVisitPK, EditColorDescriptionForm form);
    
    CommandResult deleteColorDescription(UserVisitPK userVisitPK, DeleteColorDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Font Styles
    // --------------------------------------------------------------------------------
    
    CommandResult createFontStyle(UserVisitPK userVisitPK, CreateFontStyleForm form);
    
    CommandResult getFontStyleChoices(UserVisitPK userVisitPK, GetFontStyleChoicesForm form);
    
    CommandResult getFontStyle(UserVisitPK userVisitPK, GetFontStyleForm form);
    
    CommandResult getFontStyles(UserVisitPK userVisitPK, GetFontStylesForm form);
    
    CommandResult setDefaultFontStyle(UserVisitPK userVisitPK, SetDefaultFontStyleForm form);
    
    CommandResult editFontStyle(UserVisitPK userVisitPK, EditFontStyleForm form);
    
    CommandResult deleteFontStyle(UserVisitPK userVisitPK, DeleteFontStyleForm form);
    
    // --------------------------------------------------------------------------------
    //   Font Style Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createFontStyleDescription(UserVisitPK userVisitPK, CreateFontStyleDescriptionForm form);
    
    CommandResult getFontStyleDescription(UserVisitPK userVisitPK, GetFontStyleDescriptionForm form);
    
    CommandResult getFontStyleDescriptions(UserVisitPK userVisitPK, GetFontStyleDescriptionsForm form);
    
    CommandResult editFontStyleDescription(UserVisitPK userVisitPK, EditFontStyleDescriptionForm form);
    
    CommandResult deleteFontStyleDescription(UserVisitPK userVisitPK, DeleteFontStyleDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Font Weights
    // --------------------------------------------------------------------------------
    
    CommandResult createFontWeight(UserVisitPK userVisitPK, CreateFontWeightForm form);
    
    CommandResult getFontWeightChoices(UserVisitPK userVisitPK, GetFontWeightChoicesForm form);
    
    CommandResult getFontWeight(UserVisitPK userVisitPK, GetFontWeightForm form);
    
    CommandResult getFontWeights(UserVisitPK userVisitPK, GetFontWeightsForm form);
    
    CommandResult setDefaultFontWeight(UserVisitPK userVisitPK, SetDefaultFontWeightForm form);
    
    CommandResult editFontWeight(UserVisitPK userVisitPK, EditFontWeightForm form);
    
    CommandResult deleteFontWeight(UserVisitPK userVisitPK, DeleteFontWeightForm form);
    
    // --------------------------------------------------------------------------------
    //   Font Weight Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createFontWeightDescription(UserVisitPK userVisitPK, CreateFontWeightDescriptionForm form);
    
    CommandResult getFontWeightDescription(UserVisitPK userVisitPK, GetFontWeightDescriptionForm form);
    
    CommandResult getFontWeightDescriptions(UserVisitPK userVisitPK, GetFontWeightDescriptionsForm form);
    
    CommandResult editFontWeightDescription(UserVisitPK userVisitPK, EditFontWeightDescriptionForm form);
    
    CommandResult deleteFontWeightDescription(UserVisitPK userVisitPK, DeleteFontWeightDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Font Decorations
    // --------------------------------------------------------------------------------
    
    CommandResult createTextDecoration(UserVisitPK userVisitPK, CreateTextDecorationForm form);
    
    CommandResult getTextDecorationChoices(UserVisitPK userVisitPK, GetTextDecorationChoicesForm form);
    
    CommandResult getTextDecoration(UserVisitPK userVisitPK, GetTextDecorationForm form);
    
    CommandResult getTextDecorations(UserVisitPK userVisitPK, GetTextDecorationsForm form);
    
    CommandResult setDefaultTextDecoration(UserVisitPK userVisitPK, SetDefaultTextDecorationForm form);
    
    CommandResult editTextDecoration(UserVisitPK userVisitPK, EditTextDecorationForm form);
    
    CommandResult deleteTextDecoration(UserVisitPK userVisitPK, DeleteTextDecorationForm form);
    
    // --------------------------------------------------------------------------------
    //   Font Decoration Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createTextDecorationDescription(UserVisitPK userVisitPK, CreateTextDecorationDescriptionForm form);
    
    CommandResult getTextDecorationDescription(UserVisitPK userVisitPK, GetTextDecorationDescriptionForm form);
    
    CommandResult getTextDecorationDescriptions(UserVisitPK userVisitPK, GetTextDecorationDescriptionsForm form);
    
    CommandResult editTextDecorationDescription(UserVisitPK userVisitPK, EditTextDecorationDescriptionForm form);
    
    CommandResult deleteTextDecorationDescription(UserVisitPK userVisitPK, DeleteTextDecorationDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Font Transformations
    // --------------------------------------------------------------------------------
    
    CommandResult createTextTransformation(UserVisitPK userVisitPK, CreateTextTransformationForm form);
    
    CommandResult getTextTransformationChoices(UserVisitPK userVisitPK, GetTextTransformationChoicesForm form);
    
    CommandResult getTextTransformation(UserVisitPK userVisitPK, GetTextTransformationForm form);
    
    CommandResult getTextTransformations(UserVisitPK userVisitPK, GetTextTransformationsForm form);
    
    CommandResult setDefaultTextTransformation(UserVisitPK userVisitPK, SetDefaultTextTransformationForm form);
    
    CommandResult editTextTransformation(UserVisitPK userVisitPK, EditTextTransformationForm form);
    
    CommandResult deleteTextTransformation(UserVisitPK userVisitPK, DeleteTextTransformationForm form);
    
    // --------------------------------------------------------------------------------
    //   Font Transformation Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createTextTransformationDescription(UserVisitPK userVisitPK, CreateTextTransformationDescriptionForm form);
    
    CommandResult getTextTransformationDescription(UserVisitPK userVisitPK, GetTextTransformationDescriptionForm form);
    
    CommandResult getTextTransformationDescriptions(UserVisitPK userVisitPK, GetTextTransformationDescriptionsForm form);
    
    CommandResult editTextTransformationDescription(UserVisitPK userVisitPK, EditTextTransformationDescriptionForm form);
    
    CommandResult deleteTextTransformationDescription(UserVisitPK userVisitPK, DeleteTextTransformationDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Entity Appearances
    // --------------------------------------------------------------------------------
    
    CommandResult createEntityAppearance(UserVisitPK userVisitPK, CreateEntityAppearanceForm form);
    
    CommandResult getEntityAppearance(UserVisitPK userVisitPK, GetEntityAppearanceForm form);
    
    CommandResult editEntityAppearance(UserVisitPK userVisitPK, EditEntityAppearanceForm form);
    
    CommandResult deleteEntityAppearance(UserVisitPK userVisitPK, DeleteEntityAppearanceForm form);
    
    // -------------------------------------------------------------------------
    //   Utilities
    // -------------------------------------------------------------------------

    CommandResult encrypt(UserVisitPK userVisitPK, EncryptForm form);

    CommandResult decrypt(UserVisitPK userVisitPK, DecryptForm form);

    CommandResult validate(UserVisitPK userVisit, ValidateForm form);

}
