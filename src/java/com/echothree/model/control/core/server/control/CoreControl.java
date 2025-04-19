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

package com.echothree.model.control.core.server.control;

import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.associate.server.control.AssociateControl;
import com.echothree.model.control.batch.server.control.BatchControl;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.comment.server.control.CommentControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.common.choice.BaseEncryptionKeyStatusChoicesBean;
import com.echothree.model.control.core.common.choice.EntityAliasTypeChoicesBean;
import com.echothree.model.control.core.common.choice.EntityAttributeGroupChoicesBean;
import com.echothree.model.control.core.common.choice.EntityAttributeTypeChoicesBean;
import com.echothree.model.control.core.common.choice.EntityIntegerRangeChoicesBean;
import com.echothree.model.control.core.common.choice.EntityListItemChoicesBean;
import com.echothree.model.control.core.common.choice.EntityLongRangeChoicesBean;
import com.echothree.model.control.core.common.choice.EventGroupStatusChoicesBean;
import com.echothree.model.control.core.common.choice.MimeTypeChoicesBean;
import com.echothree.model.control.core.common.choice.MimeTypeUsageTypeChoicesBean;
import com.echothree.model.control.core.common.choice.ProtocolChoicesBean;
import com.echothree.model.control.core.common.choice.ServerChoicesBean;
import com.echothree.model.control.core.common.choice.ServiceChoicesBean;
import com.echothree.model.control.core.common.transfer.BaseEncryptionKeyTransfer;
import com.echothree.model.control.core.common.transfer.CacheEntryDependencyTransfer;
import com.echothree.model.control.core.common.transfer.CacheEntryTransfer;
import com.echothree.model.control.core.common.transfer.EntityAliasTransfer;
import com.echothree.model.control.core.common.transfer.EntityAliasTypeDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.EntityAliasTypeTransfer;
import com.echothree.model.control.core.common.transfer.EntityAttributeDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.EntityAttributeEntityAttributeGroupTransfer;
import com.echothree.model.control.core.common.transfer.EntityAttributeEntityTypeTransfer;
import com.echothree.model.control.core.common.transfer.EntityAttributeGroupDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.EntityAttributeGroupTransfer;
import com.echothree.model.control.core.common.transfer.EntityAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityAttributeTypeTransfer;
import com.echothree.model.control.core.common.transfer.EntityBlobAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityBooleanAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityBooleanDefaultTransfer;
import com.echothree.model.control.core.common.transfer.EntityClobAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityCollectionAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityDateAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityDateDefaultTransfer;
import com.echothree.model.control.core.common.transfer.EntityEntityAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityGeoPointAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityGeoPointDefaultTransfer;
import com.echothree.model.control.core.common.transfer.EntityInstanceTransfer;
import com.echothree.model.control.core.common.transfer.EntityIntegerAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityIntegerDefaultTransfer;
import com.echothree.model.control.core.common.transfer.EntityIntegerRangeDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.EntityIntegerRangeTransfer;
import com.echothree.model.control.core.common.transfer.EntityListItemAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityListItemDefaultTransfer;
import com.echothree.model.control.core.common.transfer.EntityListItemDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.EntityListItemTransfer;
import com.echothree.model.control.core.common.transfer.EntityLongAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityLongDefaultTransfer;
import com.echothree.model.control.core.common.transfer.EntityLongRangeDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.EntityLongRangeTransfer;
import com.echothree.model.control.core.common.transfer.EntityMultipleListItemAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityMultipleListItemDefaultTransfer;
import com.echothree.model.control.core.common.transfer.EntityNameAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityStringAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityStringDefaultTransfer;
import com.echothree.model.control.core.common.transfer.EntityTimeAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityTimeDefaultTransfer;
import com.echothree.model.control.core.common.transfer.EntityTimeTransfer;
import com.echothree.model.control.core.common.transfer.EntityVisitTransfer;
import com.echothree.model.control.core.common.transfer.EventGroupTransfer;
import com.echothree.model.control.core.common.transfer.EventTransfer;
import com.echothree.model.control.core.common.transfer.EventTypeTransfer;
import com.echothree.model.control.core.common.transfer.MimeTypeDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.MimeTypeFileExtensionTransfer;
import com.echothree.model.control.core.common.transfer.MimeTypeTransfer;
import com.echothree.model.control.core.common.transfer.MimeTypeUsageTransfer;
import com.echothree.model.control.core.common.transfer.MimeTypeUsageTypeTransfer;
import com.echothree.model.control.core.common.transfer.PartyEntityTypeTransfer;
import com.echothree.model.control.core.common.transfer.ProtocolDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.ProtocolTransfer;
import com.echothree.model.control.core.common.transfer.ServerDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.ServerServiceTransfer;
import com.echothree.model.control.core.common.transfer.ServerTransfer;
import com.echothree.model.control.core.common.transfer.ServiceDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.ServiceTransfer;
import static com.echothree.model.control.core.common.workflow.BaseEncryptionKeyStatusConstants.WorkflowDestination_BASE_ENCRYPTION_KEY_STATUS_ACTIVE_TO_INACTIVE;
import static com.echothree.model.control.core.common.workflow.BaseEncryptionKeyStatusConstants.WorkflowStep_BASE_ENCRYPTION_KEY_STATUS_ACTIVE;
import static com.echothree.model.control.core.common.workflow.BaseEncryptionKeyStatusConstants.Workflow_BASE_ENCRYPTION_KEY_STATUS;
import static com.echothree.model.control.core.common.workflow.EventGroupStatusConstants.WorkflowStep_EVENT_GROUP_STATUS_ACTIVE;
import static com.echothree.model.control.core.common.workflow.EventGroupStatusConstants.Workflow_EVENT_GROUP_STATUS;
import com.echothree.model.control.core.server.CoreDebugFlags;
import com.echothree.model.control.core.server.database.EntityInstancePKsByEntityTypeWithNullDeletedTimeQuery;
import com.echothree.model.control.core.server.eventbus.SentEvent;
import com.echothree.model.control.core.server.eventbus.SentEventEventBus;
import com.echothree.model.control.index.server.control.IndexControl;
import com.echothree.model.control.message.server.control.MessageControl;
import com.echothree.model.control.queue.common.QueueTypes;
import com.echothree.model.control.queue.common.exception.UnknownQueueTypeNameException;
import com.echothree.model.control.queue.server.logic.QueuedEntityLogic;
import com.echothree.model.control.rating.server.control.RatingControl;
import com.echothree.model.control.scale.server.control.ScaleControl;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.control.security.server.control.SecurityControl;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.control.tag.server.control.TagControl;
import com.echothree.model.control.workeffort.server.control.WorkEffortControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.chain.server.entity.ChainInstance;
import com.echothree.model.data.core.common.pk.CacheEntryPK;
import com.echothree.model.data.core.common.pk.EntityAliasTypePK;
import com.echothree.model.data.core.common.pk.EntityAttributeGroupPK;
import com.echothree.model.data.core.common.pk.EntityAttributePK;
import com.echothree.model.data.core.common.pk.EntityAttributeTypePK;
import com.echothree.model.data.core.common.pk.EntityInstancePK;
import com.echothree.model.data.core.common.pk.EntityIntegerRangePK;
import com.echothree.model.data.core.common.pk.EntityListItemPK;
import com.echothree.model.data.core.common.pk.EntityLongRangePK;
import com.echothree.model.data.core.common.pk.MimeTypePK;
import com.echothree.model.data.core.server.entity.BaseEncryptionKey;
import com.echothree.model.data.core.server.entity.CacheBlobEntry;
import com.echothree.model.data.core.server.entity.CacheClobEntry;
import com.echothree.model.data.core.server.entity.CacheEntry;
import com.echothree.model.data.core.server.entity.CacheEntryDependency;
import com.echothree.model.data.core.server.entity.Component;
import com.echothree.model.data.core.server.entity.ComponentStage;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.ComponentVersion;
import com.echothree.model.data.core.server.entity.EntityAlias;
import com.echothree.model.data.core.server.entity.EntityAliasType;
import com.echothree.model.data.core.server.entity.EntityAliasTypeDescription;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityAttributeBlob;
import com.echothree.model.data.core.server.entity.EntityAttributeDescription;
import com.echothree.model.data.core.server.entity.EntityAttributeEntityAttributeGroup;
import com.echothree.model.data.core.server.entity.EntityAttributeEntityType;
import com.echothree.model.data.core.server.entity.EntityAttributeGroup;
import com.echothree.model.data.core.server.entity.EntityAttributeGroupDescription;
import com.echothree.model.data.core.server.entity.EntityAttributeInteger;
import com.echothree.model.data.core.server.entity.EntityAttributeListItem;
import com.echothree.model.data.core.server.entity.EntityAttributeLong;
import com.echothree.model.data.core.server.entity.EntityAttributeNumeric;
import com.echothree.model.data.core.server.entity.EntityAttributeString;
import com.echothree.model.data.core.server.entity.EntityAttributeType;
import com.echothree.model.data.core.server.entity.EntityAttributeTypeDescription;
import com.echothree.model.data.core.server.entity.EntityAttributeWorkflow;
import com.echothree.model.data.core.server.entity.EntityBlobAttribute;
import com.echothree.model.data.core.server.entity.EntityBooleanAttribute;
import com.echothree.model.data.core.server.entity.EntityBooleanDefault;
import com.echothree.model.data.core.server.entity.EntityClobAttribute;
import com.echothree.model.data.core.server.entity.EntityCollectionAttribute;
import com.echothree.model.data.core.server.entity.EntityDateAttribute;
import com.echothree.model.data.core.server.entity.EntityDateDefault;
import com.echothree.model.data.core.server.entity.EntityEncryptionKey;
import com.echothree.model.data.core.server.entity.EntityEntityAttribute;
import com.echothree.model.data.core.server.entity.EntityGeoPointAttribute;
import com.echothree.model.data.core.server.entity.EntityGeoPointDefault;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityIntegerAttribute;
import com.echothree.model.data.core.server.entity.EntityIntegerDefault;
import com.echothree.model.data.core.server.entity.EntityIntegerRange;
import com.echothree.model.data.core.server.entity.EntityIntegerRangeDescription;
import com.echothree.model.data.core.server.entity.EntityListItem;
import com.echothree.model.data.core.server.entity.EntityListItemAttribute;
import com.echothree.model.data.core.server.entity.EntityListItemDefault;
import com.echothree.model.data.core.server.entity.EntityListItemDescription;
import com.echothree.model.data.core.server.entity.EntityLongAttribute;
import com.echothree.model.data.core.server.entity.EntityLongDefault;
import com.echothree.model.data.core.server.entity.EntityLongRange;
import com.echothree.model.data.core.server.entity.EntityLongRangeDescription;
import com.echothree.model.data.core.server.entity.EntityMultipleListItemAttribute;
import com.echothree.model.data.core.server.entity.EntityMultipleListItemDefault;
import com.echothree.model.data.core.server.entity.EntityNameAttribute;
import com.echothree.model.data.core.server.entity.EntityStringAttribute;
import com.echothree.model.data.core.server.entity.EntityStringDefault;
import com.echothree.model.data.core.server.entity.EntityTime;
import com.echothree.model.data.core.server.entity.EntityTimeAttribute;
import com.echothree.model.data.core.server.entity.EntityTimeDefault;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.core.server.entity.EntityVisit;
import com.echothree.model.data.core.server.entity.Event;
import com.echothree.model.data.core.server.entity.EventGroup;
import com.echothree.model.data.core.server.entity.EventSubscriber;
import com.echothree.model.data.core.server.entity.EventSubscriberEntityInstance;
import com.echothree.model.data.core.server.entity.EventSubscriberEntityType;
import com.echothree.model.data.core.server.entity.EventSubscriberEventType;
import com.echothree.model.data.core.server.entity.EventType;
import com.echothree.model.data.core.server.entity.EventTypeDescription;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.core.server.entity.MimeTypeDescription;
import com.echothree.model.data.core.server.entity.MimeTypeFileExtension;
import com.echothree.model.data.core.server.entity.MimeTypeUsage;
import com.echothree.model.data.core.server.entity.MimeTypeUsageType;
import com.echothree.model.data.core.server.entity.MimeTypeUsageTypeDescription;
import com.echothree.model.data.core.server.entity.PartyEntityType;
import com.echothree.model.data.core.server.entity.Protocol;
import com.echothree.model.data.core.server.entity.ProtocolDescription;
import com.echothree.model.data.core.server.entity.QueuedEvent;
import com.echothree.model.data.core.server.entity.QueuedSubscriberEvent;
import com.echothree.model.data.core.server.entity.Server;
import com.echothree.model.data.core.server.entity.ServerDescription;
import com.echothree.model.data.core.server.entity.ServerService;
import com.echothree.model.data.core.server.entity.Service;
import com.echothree.model.data.core.server.entity.ServiceDescription;
import com.echothree.model.data.core.server.factory.BaseEncryptionKeyFactory;
import com.echothree.model.data.core.server.factory.CacheBlobEntryFactory;
import com.echothree.model.data.core.server.factory.CacheClobEntryFactory;
import com.echothree.model.data.core.server.factory.CacheEntryDependencyFactory;
import com.echothree.model.data.core.server.factory.CacheEntryFactory;
import com.echothree.model.data.core.server.factory.ComponentDetailFactory;
import com.echothree.model.data.core.server.factory.ComponentFactory;
import com.echothree.model.data.core.server.factory.ComponentStageFactory;
import com.echothree.model.data.core.server.factory.ComponentVersionFactory;
import com.echothree.model.data.core.server.factory.EntityAliasFactory;
import com.echothree.model.data.core.server.factory.EntityAliasTypeDescriptionFactory;
import com.echothree.model.data.core.server.factory.EntityAliasTypeDetailFactory;
import com.echothree.model.data.core.server.factory.EntityAliasTypeFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeBlobFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeDescriptionFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeDetailFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeEntityAttributeGroupFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeEntityTypeFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeGroupDescriptionFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeGroupDetailFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeGroupFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeIntegerFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeListItemFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeLongFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeNumericFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeStringFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeTypeDescriptionFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeTypeFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeWorkflowFactory;
import com.echothree.model.data.core.server.factory.EntityBlobAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityBooleanAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityBooleanDefaultFactory;
import com.echothree.model.data.core.server.factory.EntityClobAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityCollectionAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityDateAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityDateDefaultFactory;
import com.echothree.model.data.core.server.factory.EntityEncryptionKeyFactory;
import com.echothree.model.data.core.server.factory.EntityEntityAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityGeoPointAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityGeoPointDefaultFactory;
import com.echothree.model.data.core.server.factory.EntityInstanceFactory;
import com.echothree.model.data.core.server.factory.EntityIntegerAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityIntegerDefaultFactory;
import com.echothree.model.data.core.server.factory.EntityIntegerRangeDescriptionFactory;
import com.echothree.model.data.core.server.factory.EntityIntegerRangeDetailFactory;
import com.echothree.model.data.core.server.factory.EntityIntegerRangeFactory;
import com.echothree.model.data.core.server.factory.EntityListItemAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityListItemDefaultFactory;
import com.echothree.model.data.core.server.factory.EntityListItemDescriptionFactory;
import com.echothree.model.data.core.server.factory.EntityListItemDetailFactory;
import com.echothree.model.data.core.server.factory.EntityListItemFactory;
import com.echothree.model.data.core.server.factory.EntityLongAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityLongDefaultFactory;
import com.echothree.model.data.core.server.factory.EntityLongRangeDescriptionFactory;
import com.echothree.model.data.core.server.factory.EntityLongRangeDetailFactory;
import com.echothree.model.data.core.server.factory.EntityLongRangeFactory;
import com.echothree.model.data.core.server.factory.EntityMultipleListItemAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityMultipleListItemDefaultFactory;
import com.echothree.model.data.core.server.factory.EntityNameAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityStringAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityStringDefaultFactory;
import com.echothree.model.data.core.server.factory.EntityTimeAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityTimeDefaultFactory;
import com.echothree.model.data.core.server.factory.EntityTimeFactory;
import com.echothree.model.data.core.server.factory.EntityVisitFactory;
import com.echothree.model.data.core.server.factory.EventFactory;
import com.echothree.model.data.core.server.factory.EventGroupDetailFactory;
import com.echothree.model.data.core.server.factory.EventGroupFactory;
import com.echothree.model.data.core.server.factory.EventSubscriberDetailFactory;
import com.echothree.model.data.core.server.factory.EventSubscriberEntityInstanceFactory;
import com.echothree.model.data.core.server.factory.EventSubscriberEntityTypeFactory;
import com.echothree.model.data.core.server.factory.EventSubscriberEventTypeFactory;
import com.echothree.model.data.core.server.factory.EventSubscriberFactory;
import com.echothree.model.data.core.server.factory.EventTypeDescriptionFactory;
import com.echothree.model.data.core.server.factory.EventTypeFactory;
import com.echothree.model.data.core.server.factory.MimeTypeDescriptionFactory;
import com.echothree.model.data.core.server.factory.MimeTypeDetailFactory;
import com.echothree.model.data.core.server.factory.MimeTypeFactory;
import com.echothree.model.data.core.server.factory.MimeTypeFileExtensionFactory;
import com.echothree.model.data.core.server.factory.MimeTypeUsageFactory;
import com.echothree.model.data.core.server.factory.MimeTypeUsageTypeDescriptionFactory;
import com.echothree.model.data.core.server.factory.MimeTypeUsageTypeFactory;
import com.echothree.model.data.core.server.factory.PartyEntityTypeFactory;
import com.echothree.model.data.core.server.factory.ProtocolDescriptionFactory;
import com.echothree.model.data.core.server.factory.ProtocolDetailFactory;
import com.echothree.model.data.core.server.factory.ProtocolFactory;
import com.echothree.model.data.core.server.factory.QueuedEventFactory;
import com.echothree.model.data.core.server.factory.QueuedSubscriberEventFactory;
import com.echothree.model.data.core.server.factory.ServerDescriptionFactory;
import com.echothree.model.data.core.server.factory.ServerDetailFactory;
import com.echothree.model.data.core.server.factory.ServerFactory;
import com.echothree.model.data.core.server.factory.ServerServiceFactory;
import com.echothree.model.data.core.server.factory.ServiceDescriptionFactory;
import com.echothree.model.data.core.server.factory.ServiceDetailFactory;
import com.echothree.model.data.core.server.factory.ServiceFactory;
import com.echothree.model.data.core.server.value.CacheEntryDependencyValue;
import com.echothree.model.data.core.server.value.EntityAliasTypeDescriptionValue;
import com.echothree.model.data.core.server.value.EntityAliasTypeDetailValue;
import com.echothree.model.data.core.server.value.EntityAliasValue;
import com.echothree.model.data.core.server.value.EntityAttributeBlobValue;
import com.echothree.model.data.core.server.value.EntityAttributeDescriptionValue;
import com.echothree.model.data.core.server.value.EntityAttributeDetailValue;
import com.echothree.model.data.core.server.value.EntityAttributeEntityAttributeGroupValue;
import com.echothree.model.data.core.server.value.EntityAttributeGroupDescriptionValue;
import com.echothree.model.data.core.server.value.EntityAttributeGroupDetailValue;
import com.echothree.model.data.core.server.value.EntityAttributeIntegerValue;
import com.echothree.model.data.core.server.value.EntityAttributeListItemValue;
import com.echothree.model.data.core.server.value.EntityAttributeLongValue;
import com.echothree.model.data.core.server.value.EntityAttributeNumericValue;
import com.echothree.model.data.core.server.value.EntityAttributeStringValue;
import com.echothree.model.data.core.server.value.EntityAttributeWorkflowValue;
import com.echothree.model.data.core.server.value.EntityBlobAttributeValue;
import com.echothree.model.data.core.server.value.EntityBooleanAttributeValue;
import com.echothree.model.data.core.server.value.EntityBooleanDefaultValue;
import com.echothree.model.data.core.server.value.EntityClobAttributeValue;
import com.echothree.model.data.core.server.value.EntityDateAttributeValue;
import com.echothree.model.data.core.server.value.EntityDateDefaultValue;
import com.echothree.model.data.core.server.value.EntityEntityAttributeValue;
import com.echothree.model.data.core.server.value.EntityGeoPointAttributeValue;
import com.echothree.model.data.core.server.value.EntityGeoPointDefaultValue;
import com.echothree.model.data.core.server.value.EntityIntegerAttributeValue;
import com.echothree.model.data.core.server.value.EntityIntegerDefaultValue;
import com.echothree.model.data.core.server.value.EntityIntegerRangeDescriptionValue;
import com.echothree.model.data.core.server.value.EntityIntegerRangeDetailValue;
import com.echothree.model.data.core.server.value.EntityListItemAttributeValue;
import com.echothree.model.data.core.server.value.EntityListItemDefaultValue;
import com.echothree.model.data.core.server.value.EntityListItemDescriptionValue;
import com.echothree.model.data.core.server.value.EntityListItemDetailValue;
import com.echothree.model.data.core.server.value.EntityLongAttributeValue;
import com.echothree.model.data.core.server.value.EntityLongDefaultValue;
import com.echothree.model.data.core.server.value.EntityLongRangeDescriptionValue;
import com.echothree.model.data.core.server.value.EntityLongRangeDetailValue;
import com.echothree.model.data.core.server.value.EntityNameAttributeValue;
import com.echothree.model.data.core.server.value.EntityStringAttributeValue;
import com.echothree.model.data.core.server.value.EntityStringDefaultValue;
import com.echothree.model.data.core.server.value.EntityTimeAttributeValue;
import com.echothree.model.data.core.server.value.EntityTimeDefaultValue;
import com.echothree.model.data.core.server.value.EventGroupDetailValue;
import com.echothree.model.data.core.server.value.MimeTypeDescriptionValue;
import com.echothree.model.data.core.server.value.MimeTypeDetailValue;
import com.echothree.model.data.core.server.value.PartyEntityTypeValue;
import com.echothree.model.data.core.server.value.ProtocolDescriptionValue;
import com.echothree.model.data.core.server.value.ProtocolDetailValue;
import com.echothree.model.data.core.server.value.ServerDescriptionValue;
import com.echothree.model.data.core.server.value.ServerDetailValue;
import com.echothree.model.data.core.server.value.ServerServiceValue;
import com.echothree.model.data.core.server.value.ServiceDescriptionValue;
import com.echothree.model.data.core.server.value.ServiceDetailValue;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BaseKey;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.util.server.kafka.EventTopic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.BaseEntity;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.persistence.Sha1Utils;
import com.echothree.util.server.string.EntityInstanceUtils;
import com.echothree.util.server.string.UuidUtils;
import com.google.common.base.Splitter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class CoreControl
        extends BaseCoreControl {
    
    /** Creates a new instance of CoreControl */
    public CoreControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Instances
    // --------------------------------------------------------------------------------

    public EntityInstance createEntityInstance(EntityType entityType, Long entityUniqueId) {
        return EntityInstanceFactory.getInstance().create(entityType, entityUniqueId, null);
    }

    public EntityInstance createEntityAttributeDefaults(EntityInstance entityInstance, BasePK createdBy) {
        var entityAttributes = getEntityAttributesByEntityType(entityInstance.getEntityType());

        entityAttributes.forEach(entityAttribute -> {
            var entityAttributeTypeName = entityAttribute.getLastDetail().getEntityAttributeType().getEntityAttributeTypeName();
            var entityAttributeType = EntityAttributeTypes.valueOf(entityAttributeTypeName);

            switch(entityAttributeType) {
                case BOOLEAN -> {
                    var entityBooleanDefault = getEntityBooleanDefault(entityAttribute);

                    if(entityBooleanDefault != null) {
                        createEntityBooleanAttribute(entityAttribute, entityInstance, entityBooleanDefault.getBooleanAttribute(), createdBy);
                    }
                }
                case DATE -> {
                    var entityDateDefault = getEntityDateDefault(entityAttribute);

                    if(entityDateDefault != null) {
                        createEntityDateAttribute(entityAttribute, entityInstance, entityDateDefault.getDateAttribute(), createdBy);
                    }
                }
                case GEOPOINT -> {
                    var entityGeoPointDefault = getEntityGeoPointDefault(entityAttribute);

                    if(entityGeoPointDefault != null) {
                        createEntityGeoPointAttribute(entityAttribute, entityInstance, entityGeoPointDefault.getLatitude(),
                                entityGeoPointDefault.getLongitude(), entityGeoPointDefault.getElevation(),
                                entityGeoPointDefault.getAltitude(), createdBy);
                    }
                }
                case INTEGER -> {
                    var entityIntegerDefault = getEntityIntegerDefault(entityAttribute);

                    if(entityIntegerDefault != null) {
                        createEntityIntegerAttribute(entityAttribute, entityInstance, entityIntegerDefault.getIntegerAttribute(), createdBy);
                    }
                }
                case LISTITEM -> {
                    var entityListItemDefault = getEntityListItemDefault(entityAttribute);

                    if(entityListItemDefault != null) {
                        createEntityListItemAttribute(entityAttribute, entityInstance, entityListItemDefault.getEntityListItem(), createdBy);
                    }
                }
                case LONG -> {
                    var entityLongDefault = getEntityLongDefault(entityAttribute);

                    if(entityLongDefault != null) {
                        createEntityLongAttribute(entityAttribute, entityInstance, entityLongDefault.getLongAttribute(), createdBy);
                    }
                }
                case MULTIPLELISTITEM ->
                        getEntityMultipleListItemDefaults(entityAttribute).forEach((entityMultipleListItemDefault) ->
                                createEntityListItemAttribute(entityAttribute, entityInstance, entityMultipleListItemDefault.getEntityListItem(), createdBy));
                case STRING -> {
                    var entityStringDefaults = getEntityStringDefaultsByEntityAttribute(entityAttribute);

                    entityStringDefaults.forEach(entityStringDefault ->
                            createEntityStringAttribute(entityAttribute, entityInstance, entityStringDefault.getLanguage(),
                                    entityStringDefault.getStringAttribute(), createdBy));
                }
                case TIME -> {
                    var entityTimeDefault = getEntityTimeDefault(entityAttribute);

                    if(entityTimeDefault != null) {
                        createEntityTimeAttribute(entityAttribute, entityInstance, entityTimeDefault.getTimeAttribute(), createdBy);
                    }
                }
                default -> {}
            }
        });

        return entityInstance;
    }

    public boolean verifyEntityInstance(final EntityInstance entityInstance, final String componentVendorName, final String entityTypeName) {
        var result = true;
        var entityTypeDetail = entityInstance.getEntityType().getLastDetail();
        
        if(entityTypeDetail.getEntityTypeName().equals(entityTypeName)) {
            if(!entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName().equals(componentVendorName)) {
                result = false;
            }
        } else {
            result = false;
        }
        
        return result;
    }

    public long countEntityInstances() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM entityinstances");
    }

    public long countEntityInstancesByEntityType(EntityType entityType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM entityinstances " +
                "WHERE eni_ent_entitytypeid = ?",
                entityType);
    }

    public List<EntityInstance> getEntityInstancesByEntityType(EntityType entityType) {
        List<EntityInstance> entityInstances;
        
        try {
            var ps = EntityInstanceFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entityinstances " +
                    "WHERE eni_ent_entitytypeid = ? " +
                    "ORDER BY eni_entityuniqueid " +
                    "_LIMIT_");
            
            ps.setLong(1, entityType.getPrimaryKey().getEntityId());
            
            entityInstances = EntityInstanceFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityInstances;
    }
    
    private EntityInstance getEntityInstance(EntityType entityType, Long entityUniqueId, EntityPermission entityPermission) {
        EntityInstance entityInstance;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityinstances " +
                        "WHERE eni_ent_entitytypeid = ? AND eni_entityuniqueid = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityinstances " +
                        "WHERE eni_ent_entitytypeid = ? AND eni_entityuniqueid = ? " +
                        "FOR UPDATE";
            }

            var ps = EntityInstanceFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityType.getPrimaryKey().getEntityId());
            ps.setLong(2, entityUniqueId);
            
            entityInstance = EntityInstanceFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityInstance;
    }
    
    public EntityInstance getEntityInstance(EntityType entityType, Long entityUniqueId) {
        return getEntityInstance(entityType, entityUniqueId, EntityPermission.READ_ONLY);
    }
    
    public EntityInstance getEntityInstanceForUpdate(EntityType entityType, Long entityUniqueId) {
        return getEntityInstance(entityType, entityUniqueId, EntityPermission.READ_WRITE);
    }

    private EntityInstance getEntityInstanceByUuid(String uuid, EntityPermission entityPermission) {
        EntityInstance entityInstance;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityinstances " +
                        "WHERE eni_uuid = UUID_TO_BIN(?)";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityinstances " +
                        "WHERE eni_uuid = UUID_TO_BIN(?) " +
                        "FOR UPDATE";
            }

            var ps = EntityInstanceFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, uuid);
            
            entityInstance = EntityInstanceFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityInstance;
    }
    
    public EntityInstance getEntityInstanceByUuid(String uuid) {
        return getEntityInstanceByUuid(uuid, EntityPermission.READ_ONLY);
    }
    
    public EntityInstance getEntityInstanceByUuidForUpdate(String uuid) {
        return getEntityInstanceByUuid(uuid, EntityPermission.READ_WRITE);
    }
    
    public EntityInstance ensureUuidForEntityInstance(EntityInstance entityInstance, boolean forceRegeneration) {
        var uuid = entityInstance.getUuid();
        
        if(uuid == null || forceRegeneration) {
            // Convert to READ_WRITE if necessary...
            if(entityInstance.getEntityPermission().equals(EntityPermission.READ_ONLY)) {
                entityInstance = EntityInstanceFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, entityInstance.getPrimaryKey());
            }
            
            // Keep generating UUIDs until a unique one is found...
            EntityInstance duplicateEntityInstance;
            do {
                uuid = UuidUtils.getInstance().generateUuid(entityInstance);
                duplicateEntityInstance = getEntityInstanceByUuid(uuid);
            } while(duplicateEntityInstance != null);
            
            // Store it immediately in order to decrease the odds of another thread choosing the same UUID...
            entityInstance.setUuid(uuid);
            entityInstance.store();
        }
        
        return entityInstance;
    }
    
    public EntityInstanceTransfer getEntityInstanceTransfer(UserVisit userVisit, EntityInstance entityInstance, boolean includeEntityAppearance,
            boolean includeEntityVisit, boolean includeNames, boolean includeUuid) {
        return getCoreTransferCaches(userVisit).getEntityInstanceTransferCache().getEntityInstanceTransfer(entityInstance, includeEntityAppearance,
                includeEntityVisit, includeNames, includeUuid);
    }
    
    public EntityInstanceTransfer getEntityInstanceTransfer(UserVisit userVisit, BaseEntity baseEntity, boolean includeEntityAppearance,
            boolean includeEntityVisit, boolean includeNames, boolean includeUuid) {
        return getEntityInstanceTransfer(userVisit, getEntityInstanceByBasePK(baseEntity.getPrimaryKey()), includeEntityAppearance,
                includeEntityVisit, includeNames, includeUuid);
    }

    public List<EntityInstanceTransfer> getEntityInstanceTransfers(UserVisit userVisit, Collection<EntityInstance> entityInstances,
            boolean includeEntityAppearance, boolean includeEntityVisit, boolean includeNames, boolean includeUuid) {
        var entityInstanceTransfers = new ArrayList<EntityInstanceTransfer>(entityInstances.size());
        var entityInstanceTransferCache = getCoreTransferCaches(userVisit).getEntityInstanceTransferCache();

        entityInstances.forEach((entityInstance) ->
                entityInstanceTransfers.add(entityInstanceTransferCache.getEntityInstanceTransfer(entityInstance,
                        includeEntityAppearance, includeEntityVisit, includeNames, includeUuid))
        );

        return entityInstanceTransfers;
    }

    public List<EntityInstanceTransfer> getEntityInstanceTransfersByEntityType(UserVisit userVisit, EntityType entityType,
            boolean includeEntityAppearance, boolean includeEntityVisit, boolean includeNames, boolean includeUuid) {
        return getEntityInstanceTransfers(userVisit, getEntityInstancesByEntityType(entityType), includeEntityAppearance,
                includeEntityVisit, includeNames, includeUuid);
    }

    /** Gets an EntityInstance for BasePK, creating it if necessary. Overrides function from BaseModelControl.
     * Some errors from this function are normal during the initial load of data into the database.
     */
    private EntityInstance getEntityInstanceByBasePK(BasePK pk, EntityPermission entityPermission) {
        EntityInstance entityInstance = null;
        
        if(CoreDebugFlags.LogEntityInstanceResolution) {
            getLog().info(">>> getEntityInstanceByBasePK(pk=" + pk + ")");
        }
        
        if(pk != null) {
            var componentControl = Session.getModelController(ComponentControl.class);
            var componentVendorName = pk.getComponentVendorName();
            var componentVendor = componentControl.getComponentVendorByNameFromCache(componentVendorName);
            
            if(CoreDebugFlags.LogEntityInstanceResolution) {
                getLog().info("--- componentVendor = " + componentVendor);
            }
            
            if(componentVendor != null) {
                var entityTypeControl = Session.getModelController(EntityTypeControl.class);
                var entityTypeName = pk.getEntityTypeName();
                var entityType = entityTypeControl.getEntityTypeByNameFromCache(componentVendor, entityTypeName);
                
                if(CoreDebugFlags.LogEntityInstanceResolution) {
                    getLog().info("--- entityType = " + entityType);
                }
                
                if(entityType != null) {
                    var entityUniqueId = pk.getEntityId();
                    
                    if(CoreDebugFlags.LogEntityInstanceResolution) {
                        getLog().info("--- entityUniqueId = " + entityUniqueId);
                    }
                    
                    entityInstance = getEntityInstance(entityType, entityUniqueId, entityPermission);
                    if(entityInstance == null) {
                        entityInstance = createEntityInstance(entityType, entityUniqueId);
                        
                        if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                            // Convert to R/W
                            entityInstance = EntityInstanceFactory.getInstance().getEntityFromPK(session,
                                    EntityPermission.READ_WRITE, entityInstance.getPrimaryKey());
                        }
                    }
                    
                    if(CoreDebugFlags.LogEntityInstanceResolution) {
                        getLog().info("--- entityInstance = " + entityInstance);
                    }
                } else if(CoreDebugFlags.LogUnresolvedEntityInstances) {
                    getLog().error("getEntityInstanceByBasePK: unknown entityTypeName \"" + componentVendorName + "." + entityTypeName + "\"");
                }
            } else if(CoreDebugFlags.LogUnresolvedEntityInstances) {
                getLog().error("getEntityInstanceByBasePK: unknown componentVendorName \"" + componentVendorName + "\"");
            }
        } else if(CoreDebugFlags.LogUnresolvedEntityInstances) {
            getLog().error("getEntityInstanceByBasePK: PK was null");
        }
        
        if(CoreDebugFlags.LogEntityInstanceResolution) {
            getLog().info("<<< entityInstance=" + entityInstance);
        }
        
        return entityInstance;
    }
    
    @Override
    public EntityInstance getEntityInstanceByBasePK(BasePK pk) {
        return getEntityInstanceByBasePK(pk, EntityPermission.READ_ONLY);
    }
    
    public EntityInstance getEntityInstanceByBasePKForUpdate(BasePK pk) {
        return getEntityInstanceByBasePK(pk, EntityPermission.READ_WRITE);
    }
    
    /** This function handles data passed back from a client. Because of this, missing entity instances
     * are not automatically created if they do not exist. Do not trust what the user is telling us.
     */
    private EntityInstance getEntityInstanceByEntityRef(String entityRef, EntityPermission entityPermission) {
        EntityInstance entityInstance = null;
        
        if(entityRef != null) {
            var entityRefParts = Splitter.on('.').trimResults().omitEmptyStrings().splitToList(entityRef).toArray(new String[0]);
            
            if(entityRefParts.length == 3) {
                var componentControl = Session.getModelController(ComponentControl.class);
                var componentVendorName = entityRefParts[0];
                var componentVendor = componentControl.getComponentVendorByNameFromCache(componentVendorName);
                
                if(componentVendor != null) {
                    var entityTypeControl = Session.getModelController(EntityTypeControl.class);
                    var entityTypeName = entityRefParts[1];
                    var entityType = entityTypeControl.getEntityTypeByNameFromCache(componentVendor, entityTypeName);
                    
                    if(entityType != null) {
                        var entityUniqueId = Long.valueOf(entityRefParts[2]);
                        
                        entityInstance = getEntityInstance(entityType, entityUniqueId, entityPermission);
                        
                        if(CoreDebugFlags.LogUnresolvedEntityInstances && entityInstance == null) {
                            getLog().error("getEntityInstanceByEntityRef: unknown entityUniqueId \"" + componentVendorName + "." + entityTypeName + "." + entityUniqueId + "\"");
                        }
                    } else if(CoreDebugFlags.LogUnresolvedEntityInstances) {
                        getLog().error("getEntityInstanceByEntityRef: unknown entityTypeName \"" + componentVendorName + "." + entityTypeName + "\"");
                    }
                } else if(CoreDebugFlags.LogUnresolvedEntityInstances) {
                    getLog().error("getEntityInstanceByEntityRef: unknown componentVendorName \"" + componentVendorName + "\"");
                }
            } else if(CoreDebugFlags.LogUnresolvedEntityInstances) {
                getLog().error("getEntityInstanceByEntityRef: entityRef not valid");
            }
        } else if(CoreDebugFlags.LogUnresolvedEntityInstances) {
            getLog().error("getEntityInstanceByEntityRef: entityRef was null");
        }
        
        return entityInstance;
    }
    
    public EntityInstance getEntityInstanceByEntityRef(String entityRef) {
        return getEntityInstanceByEntityRef(entityRef, EntityPermission.READ_ONLY);
    }
    
    public EntityInstance getEntityInstanceByEntityRefForUpdate(String entityRef) {
        return getEntityInstanceByEntityRef(entityRef, EntityPermission.READ_WRITE);
    }

    public EntityInstance getEntityInstanceByPK(EntityInstancePK entityInstancePK) {
        return EntityInstanceFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, entityInstancePK);
    }

    /** This function is a little odd. It doesn't actually delete the Entity Instance, rather, it cleans up all the
     * entities scattered through several components that depend on them.
     */
    public void deleteEntityInstanceDependencies(EntityInstance entityInstance, BasePK deletedBy) {
        var appearanceControl = Session.getModelController(AppearanceControl.class);
        var chainControl = Session.getModelController(ChainControl.class);
        var searchControl = Session.getModelController(SearchControl.class);
        var securityControl = Session.getModelController(SecurityControl.class);

        Session.getModelController(AccountingControl.class).deleteTransactionEntityRolesByEntityInstance(entityInstance, deletedBy);
        Session.getModelController(AssociateControl.class).deleteAssociateReferralsByTargetEntityInstance(entityInstance, deletedBy);
        Session.getModelController(BatchControl.class).deleteBatchEntitiesByEntityInstance(entityInstance, deletedBy);
        Session.getModelController(CommentControl.class).deleteCommentsByEntityInstance(entityInstance, deletedBy);
        Session.getModelController(MessageControl.class).deleteEntityMessagesByEntityInstance(entityInstance, deletedBy);
        Session.getModelController(RatingControl.class).deleteRatingsByEntityInstance(entityInstance, deletedBy);
        searchControl.removeSearchResultsByEntityInstance(entityInstance);
        searchControl.removeCachedExecutedSearchResultsByEntityInstance(entityInstance);
        searchControl.deleteSearchResultActionsByEntityInstance(entityInstance, deletedBy);
        securityControl.deletePartyEntitySecurityRolesByEntityInstance(entityInstance, deletedBy);
        Session.getModelController(TagControl.class).deleteEntityTagsByEntityInstance(entityInstance, deletedBy);
        getWorkflowControl().deleteWorkflowEntityStatusesByEntityInstance(entityInstance, deletedBy);
        Session.getModelController(WorkEffortControl.class).deleteWorkEffortsByOwningEntityInstance(entityInstance, deletedBy);

        // If an EntityInstance is in a role for a ChainInstance, then that ChainInstance should be deleted. Because an individual
        // EntityInstance may be in more than one role, the list of ChainInstances needs to be deduplicated.
        Set<ChainInstance> chainInstances = new HashSet<>();
        chainControl.getChainInstanceEntityRolesByEntityInstanceForUpdate(entityInstance).forEach((chainInstanceEntityRole) ->
                chainInstances.add(chainInstanceEntityRole.getChainInstanceForUpdate())
        );
        chainControl.deleteChainInstances(chainInstances, deletedBy);

        deleteEntityAliasesByEntityInstance(entityInstance, deletedBy);
        deleteEntityAttributesByEntityInstance(entityInstance, deletedBy);
        appearanceControl.deleteEntityAppearancesByEntityInstance(entityInstance, deletedBy);
    }

    public void deleteEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        // sendEvent(...) handles calling back to deleteEntityInstanceDependencies(...)
        sendEvent(entityInstance, EventTypes.DELETE, (EntityInstance)null, null, deletedBy);
    }

    public void deleteEntityInstancesByEntityTypeWithNullDeletedTime(final EntityType entityType, final BasePK deletedBy) {
        for(var entityInstanceResult : new EntityInstancePKsByEntityTypeWithNullDeletedTimeQuery().execute(entityType)) {
            deleteEntityInstance(EntityInstanceFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    entityInstanceResult.getEntityInstancePK()), deletedBy);
        }
    }

    public void removeEntityInstance(EntityInstance entityInstance) {
        entityInstance.remove();
    }
    
    public void removeEntityInstanceByBasePK(BasePK pk) {
        removeEntityInstance(getEntityInstanceByBasePKForUpdate(pk));
    }
    
    public void removeEntityInstanceByEntityRef(String entityRef) {
        removeEntityInstance(getEntityInstanceByEntityRefForUpdate(entityRef));
    }
    
    // --------------------------------------------------------------------------------
    //   Event Types
    // --------------------------------------------------------------------------------
    
    public EventType createEventType(String eventTypeName) {
        var eventType = EventTypeFactory.getInstance().create(eventTypeName);
        
        return eventType;
    }
    
    public EventType getEventTypeByName(String eventTypeName) {
        EventType eventType;
        
        try {
            var ps = EventTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM eventtypes " +
                    "WHERE evty_eventtypename = ?");
            
            ps.setString(1, eventTypeName);
            
            eventType = EventTypeFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return eventType;
    }

    private final Map<EventTypes, EventType> eventTypeCache = new HashMap<>();

    public EventType getEventTypeByEventTypesFromCache(EventTypes eventTypeEnum) {
        var eventType = eventTypeCache.get(eventTypeEnum);

        if(eventType == null) {
            eventType = getEventTypeByName(eventTypeEnum.name());

            if(eventType != null) {
                eventTypeCache.put(eventTypeEnum, eventType);
            }
        }

        return eventType;
    }

    public List<EventType> getEventTypes() {
        var ps = EventTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM eventtypes " +
                "ORDER BY evty_eventtypename");
        
        return EventTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public EventTypeTransfer getEventTypeTransfer(UserVisit userVisit, EventType eventType) {
        return getCoreTransferCaches(userVisit).getEventTypeTransferCache().getEventTypeTransfer(eventType);
    }
    
    // --------------------------------------------------------------------------------
    //   Event Type Descriptions
    // --------------------------------------------------------------------------------
    
    public EventTypeDescription createEventTypeDescription(EventType eventType, Language language, String description) {
        var eventTypeDescription = EventTypeDescriptionFactory.getInstance().create(eventType, language, description);
        
        return eventTypeDescription;
    }
    
    public EventTypeDescription getEventTypeDescription(EventType eventType, Language language) {
        EventTypeDescription eventTypeDescription;
        
        try {
            var ps = EventTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM eventtypedescriptions " +
                    "WHERE evtyd_evty_eventtypeid = ? AND evtyd_lang_languageid = ?");
            
            ps.setLong(1, eventType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            eventTypeDescription = EventTypeDescriptionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return eventTypeDescription;
    }
    
    public String getBestEventTypeDescription(EventType eventType, Language language) {
        String description;
        var eventTypeDescription = getEventTypeDescription(eventType, language);
        
        if(eventTypeDescription == null && !language.getIsDefault()) {
            eventTypeDescription = getEventTypeDescription(eventType, getPartyControl().getDefaultLanguage());
        }
        
        if(eventTypeDescription == null) {
            description = eventType.getEventTypeName();
        } else {
            description = eventTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Component Stages
    // --------------------------------------------------------------------------------
    
    public ComponentStage createComponentStage(String componentStageName, String description, Integer relativeAge) {
        var componentStage = ComponentStageFactory.getInstance().create(componentStageName, description, relativeAge);
        
        return componentStage;
    }
    
    public ComponentStage getComponentStageByName(String componentStageName) {
        ComponentStage componentStage;
        
        try {
            var ps = ComponentStageFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM componentstages " +
                    "WHERE cstg_componentstagename = ?");
            
            ps.setString(1, componentStageName);
            
            componentStage = ComponentStageFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return componentStage;
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Attribute Types
    // --------------------------------------------------------------------------------
    
    public EntityAttributeType createEntityAttributeType(String entityAttributeTypeName) {
        var entityAttributeType = EntityAttributeTypeFactory.getInstance().create(entityAttributeTypeName);
        
        return entityAttributeType;
    }
    
    /** Assume that the entityInstance passed to this function is a ECHO_THREE.EntityAttributeType */
    public EntityAttributeType getEntityAttributeTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new EntityAttributeTypePK(entityInstance.getEntityUniqueId());

        return EntityAttributeTypeFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public EntityAttributeType getEntityAttributeTypeByEntityInstance(EntityInstance entityInstance) {
        return getEntityAttributeTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public EntityAttributeType getEntityAttributeTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getEntityAttributeTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countEntityAttributeTypes() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM entityattributetypes ");
    }

    public EntityAttributeType getEntityAttributeTypeByName(String entityAttributeTypeName) {
        EntityAttributeType entityAttributeType;
        
        try {
            var ps = EntityAttributeTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entityattributetypes " +
                    "WHERE enat_entityattributetypename = ?");
            
            ps.setString(1, entityAttributeTypeName);
            
            entityAttributeType = EntityAttributeTypeFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityAttributeType;
    }
    
    public List<EntityAttributeType> getEntityAttributeTypes() {
        var ps = EntityAttributeTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM entityattributetypes " +
                "ORDER BY enat_entityattributetypename " +
                "_LIMIT_");
        
        return EntityAttributeTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public EntityAttributeTypeTransfer getEntityAttributeTypeTransfer(UserVisit userVisit, EntityAttributeType entityAttributeType) {
        return getCoreTransferCaches(userVisit).getEntityAttributeTypeTransferCache().getEntityAttributeTypeTransfer(entityAttributeType);
    }
    
    public List<EntityAttributeTypeTransfer> getEntityAttributeTypeTransfers(UserVisit userVisit, Collection<EntityAttributeType> entityAttributeTypes) {
        List<EntityAttributeTypeTransfer> entityAttributeTypeTransfers = null;
        
        if(entityAttributeTypes != null) {
            entityAttributeTypeTransfers = new ArrayList<>(entityAttributeTypes.size());
            
            for(var entityAttributeType : entityAttributeTypes) {
                entityAttributeTypeTransfers.add(getCoreTransferCaches(userVisit).getEntityAttributeTypeTransferCache().getEntityAttributeTypeTransfer(entityAttributeType));
            }
        }
        
        return entityAttributeTypeTransfers;
    }
    
    public List<EntityAttributeTypeTransfer> getEntityAttributeTypeTransfers(UserVisit userVisit) {
        return getEntityAttributeTypeTransfers(userVisit, getEntityAttributeTypes());
    }
    
    public EntityAttributeTypeChoicesBean getEntityAttributeTypeChoices(String defaultEntityAttributeTypeChoice, Language language) {
        var entityAttributeTypes = getEntityAttributeTypes();
        var size = entityAttributeTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        for(var entityAttributeType : entityAttributeTypes) {
            var label = getBestEntityAttributeTypeDescription(entityAttributeType, language);
            var value = entityAttributeType.getEntityAttributeTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultEntityAttributeTypeChoice != null && defaultEntityAttributeTypeChoice.equals(value);
            if(usingDefaultChoice || defaultValue == null) {
                defaultValue = value;
            }
        }
        
        return new EntityAttributeTypeChoicesBean(labels, values, defaultValue);
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Attribute Type Descriptions
    // --------------------------------------------------------------------------------
    
    public EntityAttributeTypeDescription createEntityAttributeTypeDescription(EntityAttributeType entityAttributeType, Language language, String description) {
        var entityAttributeTypeDescription = EntityAttributeTypeDescriptionFactory.getInstance().create(entityAttributeType, language, description);
        
        return entityAttributeTypeDescription;
    }
    
    public EntityAttributeTypeDescription getEntityAttributeTypeDescription(EntityAttributeType entityAttributeType, Language language) {
        EntityAttributeTypeDescription entityAttributeTypeDescription;
        
        try {
            var ps = EntityAttributeTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entityattributetypedescriptions " +
                    "WHERE enatd_enat_entityattributetypeid = ? AND enatd_lang_languageid = ?");
            
            ps.setLong(1, entityAttributeType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            entityAttributeTypeDescription = EntityAttributeTypeDescriptionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityAttributeTypeDescription;
    }
    
    public String getBestEntityAttributeTypeDescription(EntityAttributeType entityAttributeType, Language language) {
        String description;
        var entityAttributeTypeDescription = getEntityAttributeTypeDescription(entityAttributeType, language);
        
        if(entityAttributeTypeDescription == null && !language.getIsDefault()) {
            entityAttributeTypeDescription = getEntityAttributeTypeDescription(entityAttributeType, getPartyControl().getDefaultLanguage());
        }
        
        if(entityAttributeTypeDescription == null) {
            description = entityAttributeType.getEntityAttributeTypeName();
        } else {
            description = entityAttributeTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Times
    // --------------------------------------------------------------------------------
    
    public EntityTime createEntityTime(EntityInstance entityInstance, Long createdTime, Long modifiedTime, Long deletedTime) {
        return EntityTimeFactory.getInstance().create(entityInstance, createdTime, modifiedTime, deletedTime);
        
    }
    
    public EntityTime getEntityTime(EntityInstance entityInstance) {
        EntityTime entityTime;
        
        try {
            var ps = EntityTimeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitytimes " +
                    "WHERE etim_eni_entityinstanceid = ?");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            
            entityTime = EntityTimeFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityTime;
    }
    
    public EntityTime getEntityTimeForUpdate(EntityInstance entityInstance) {
        EntityTime entityTime;
        
        try {
            var ps = EntityTimeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitytimes " +
                    "WHERE etim_eni_entityinstanceid = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            
            entityTime = EntityTimeFactory.getInstance().getEntityFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityTime;
    }
    
    public static final Integer negativeOne = -1;
    
    public static final String selectEntityTimesByEntityType = "SELECT _ALL_ " +
            "FROM entitytimes, entityinstances, entitytypes " +
            "WHERE ent_entitytypeid = ? AND ent_entitytypeid = eni_ent_entitytypeid AND eni_entityinstanceid = etim_eni_entityinstanceid " +
            "ORDER BY etim_createdtime";
    
    public static final String selectEntityTimesByEntityTypeWithLimit = "SELECT _ALL_ " +
            "FROM entitytimes, entityinstances, entitytypes " +
            "WHERE ent_entitytypeid = ? AND ent_entitytypeid = eni_ent_entitytypeid AND eni_entityinstanceid = etim_eni_entityinstanceid " +
            "ORDER BY etim_createdtime " +
            "LIMIT ?";
    
    public List<EntityTime> getEntityTimesByEntityType(EntityType entityType) {
        return getEntityTimesByEntityTypeWithLimit(entityType, negativeOne);
    }
    
    public List<EntityTime> getEntityTimesByEntityTypeWithLimit(EntityType entityType, Integer limit) {
        List<EntityTime> entityTimes;
        
        try {
            int intLimit = limit;
            var ps = EntityTimeFactory.getInstance().prepareStatement(
                    intLimit == -1? selectEntityTimesByEntityType: selectEntityTimesByEntityTypeWithLimit);
            
            ps.setLong(1, entityType.getPrimaryKey().getEntityId());
            if(intLimit != -1) {
                ps.setInt(2, intLimit);
            }
            
            entityTimes = EntityTimeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityTimes;
    }
    
    public static final String selectEntityTimesByEntityTypeCreatedAfter = "SELECT _ALL_ " +
            "FROM entitytimes, entityinstances, entitytypes " +
            "WHERE ent_entitytypeid = ? AND ent_entitytypeid = eni_ent_entitytypeid AND eni_entityinstanceid = etim_eni_entityinstanceid " +
            "AND etim_createdtime IS NOT NULL AND etim_createdtime > ? " +
            "ORDER BY etim_createdtime";
    public static final String selectEntityTimesByEntityTypeCreatedAfterWithLimit = "SELECT _ALL_ " +
            "FROM entitytimes, entityinstances, entitytypes " +
            "WHERE ent_entitytypeid = ? AND ent_entitytypeid = eni_ent_entitytypeid AND eni_entityinstanceid = etim_eni_entityinstanceid " +
            "AND etim_createdtime IS NOT NULL AND etim_createdtime > ? " +
            "ORDER BY etim_createdtime " +
            "LIMIT ?";
    
    public List<EntityTime> getEntityTimesByEntityTypeCreatedAfter(EntityType entityType, Long createdTime) {
        return getEntityTimesByEntityTypeCreatedAfterWithLimit(entityType, createdTime, negativeOne);
    }
    
    public List<EntityTime> getEntityTimesByEntityTypeCreatedAfterWithLimit(EntityType entityType, Long createdTime, Integer limit) {
        List<EntityTime> entityTimes;
        
        try {
            int intLimit = limit;
            var ps = EntityTimeFactory.getInstance().prepareStatement(
                    intLimit == -1? selectEntityTimesByEntityTypeCreatedAfter: selectEntityTimesByEntityTypeCreatedAfterWithLimit);
            
            ps.setLong(1, entityType.getPrimaryKey().getEntityId());
            ps.setLong(2, createdTime);
            if(intLimit != -1) {
                ps.setInt(3, intLimit);
            }
            
            entityTimes = EntityTimeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityTimes;
    }
    
    public static final String selectEntityTimesByEntityTypeModifiedAfter = "SELECT _ALL_ " +
            "FROM entitytimes, entityinstances, entitytypes " +
            "WHERE ent_entitytypeid = ? AND ent_entitytypeid = eni_ent_entitytypeid AND eni_entityinstanceid = etim_eni_entityinstanceid " +
            "AND etim_modifiedtime IS NOT NULL AND etim_modifiedtime > ? " +
            "ORDER BY etim_modifiedtime";
    public static final String selectEntityTimesByEntityTypeModifiedAfterWithLimit = "SELECT _ALL_ " +
            "FROM entitytimes, entityinstances, entitytypes " +
            "WHERE ent_entitytypeid = ? AND ent_entitytypeid = eni_ent_entitytypeid AND eni_entityinstanceid = etim_eni_entityinstanceid " +
            "AND etim_modifiedtime IS NOT NULL AND etim_modifiedtime > ? " +
            "ORDER BY etim_modifiedtime " +
            "LIMIT ?";
    
    public List<EntityTime> getEntityTimesByEntityTypeModifiedAfter(EntityType entityType, Long modifiedTime) {
        return getEntityTimesByEntityTypeModifiedAfterWithLimit(entityType, modifiedTime, negativeOne);
    }
    
    public List<EntityTime> getEntityTimesByEntityTypeModifiedAfterWithLimit(EntityType entityType, Long modifiedTime, Integer limit) {
        List<EntityTime> entityTimes;
        
        try {
            int intLimit = limit;
            var ps = EntityTimeFactory.getInstance().prepareStatement(
                    intLimit == -1? selectEntityTimesByEntityTypeModifiedAfter: selectEntityTimesByEntityTypeModifiedAfterWithLimit);
            
            ps.setLong(1, entityType.getPrimaryKey().getEntityId());
            ps.setLong(2, modifiedTime);
            if(intLimit != -1) {
                ps.setInt(3, intLimit);
            }
            
            entityTimes = EntityTimeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityTimes;
    }
    
    public static final String selectEntityTimesByEntityTypeDeletedAfter = "SELECT _ALL_ " +
            "FROM entitytimes, entityinstances, entitytypes " +
            "WHERE ent_entitytypeid = ? AND ent_entitytypeid = eni_ent_entitytypeid AND eni_entityinstanceid = etim_eni_entityinstanceid " +
            "AND etim_deletedtime IS NOT NULL AND etim_deletedtime > ? " +
            "ORDER BY etim_deletedtime";
    public static final String selectEntityTimesByEntityTypeDeletedAfterWithLimit = "SELECT _ALL_ " +
            "FROM entitytimes, entityinstances, entitytypes " +
            "WHERE ent_entitytypeid = ? AND ent_entitytypeid = eni_ent_entitytypeid AND eni_entityinstanceid = etim_eni_entityinstanceid " +
            "AND etim_deletedtime IS NOT NULL AND etim_deletedtime > ? " +
            "ORDER BY etim_deletedtime " +
            "LIMIT ?";
    
    public List<EntityTime> getEntityTimesByEntityTypeDeletedAfter(EntityType entityType, Long deletedTime) {
        return getEntityTimesByEntityTypeDeletedAfterWithLimit(entityType, deletedTime, negativeOne);
    }
    
    public List<EntityTime> getEntityTimesByEntityTypeDeletedAfterWithLimit(EntityType entityType, Long deletedTime, Integer limit) {
        List<EntityTime> entityTimes;
        
        try {
            int intLimit = limit;
            var ps = EntityTimeFactory.getInstance().prepareStatement(
                    intLimit == -1? selectEntityTimesByEntityTypeDeletedAfter: selectEntityTimesByEntityTypeDeletedAfterWithLimit);
            
            ps.setLong(1, entityType.getPrimaryKey().getEntityId());
            ps.setLong(2, deletedTime);
            if(intLimit != -1) {
                ps.setInt(3, intLimit);
            }
            
            entityTimes = EntityTimeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityTimes;
    }
    
    public EntityTimeTransfer getEntityTimeTransfer(UserVisit userVisit, EntityTime entityTime) {
        return getCoreTransferCaches(userVisit).getEntityTimeTransferCache().getEntityTimeTransfer(entityTime);
    }
    
    // --------------------------------------------------------------------------------
    //   Event Groups
    // --------------------------------------------------------------------------------
    
    boolean alreadyCreatingEventGroup = false;
    
    public EventGroup getActiveEventGroup(BasePK createdBy) {
        EventGroup eventGroup = null;
        
        if(!alreadyCreatingEventGroup) {
            var workflowStep = getWorkflowControl().getWorkflowStepUsingNames(Workflow_EVENT_GROUP_STATUS,
                    WorkflowStep_EVENT_GROUP_STATUS_ACTIVE);

            if(workflowStep != null) {
                List<EventGroup> eventGroups;

                try {
                    var ps = EventGroupFactory.getInstance().prepareStatement(
                            "SELECT _ALL_ " +
                            "FROM componentvendors, componentvendordetails, entitytypes, entitytypedetails, entityinstances, " +
                            "eventgroups, eventgroupdetails, workflowentitystatuses, entitytimes " +
                            "WHERE evgrp_activedetailid = evgrpdt_eventgroupdetailid " +
                            "AND cvnd_activedetailid = cvndd_componentvendordetailid AND cvndd_componentvendorname = ? " +
                            "AND ent_activedetailid = entdt_entitytypedetailid " +
                            "AND cvnd_componentvendorid = entdt_cvnd_componentvendorid " +
                            "AND entdt_entitytypename = ? " +
                            "AND ent_entitytypeid = eni_ent_entitytypeid AND evgrp_eventgroupid = eni_entityuniqueid " +
                            "AND eni_entityinstanceid = wkfles_eni_entityinstanceid AND wkfles_wkfls_workflowstepid = ? AND wkfles_thrutime = ? " +
                            "AND eni_entityinstanceid = etim_eni_entityinstanceid " +
                            "ORDER BY etim_createdtime DESC");

                    ps.setString(1, ComponentVendors.ECHO_THREE.name());
                    ps.setString(2, EntityTypes.EventGroup.name());
                    ps.setLong(3, workflowStep.getPrimaryKey().getEntityId());
                    ps.setLong(4, Session.MAX_TIME);

                    eventGroups = EventGroupFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
                } catch (SQLException se) {
                    throw new PersistenceDatabaseException(se);
                }

                if(eventGroups != null && !eventGroups.isEmpty()) {
                    eventGroup = eventGroups.iterator().next();
                } else {
                    alreadyCreatingEventGroup = true;
                    eventGroup = createEventGroup(createdBy);
                    alreadyCreatingEventGroup = false;
                }
            }
        }
        
        return eventGroup;
    }
    
    public EventGroup createEventGroup(BasePK createdBy) {
        var sequenceControl = Session.getModelController(SequenceControl.class);
        var workflowControl = getWorkflowControl();
        EventGroup eventGroup = null;
        var workflow = workflowControl.getWorkflowByName(Workflow_EVENT_GROUP_STATUS);
        
        if(workflow != null) {
            var workflowEntrance = workflowControl.getDefaultWorkflowEntrance(workflow);
            
            if(workflowEntrance != null && (workflowControl.countWorkflowEntranceStepsByWorkflowEntrance(workflowEntrance) > 0)) {
                var sequence = sequenceControl.getDefaultSequenceUsingNames(SequenceTypes.EVENT_GROUP.name());
                var eventGroupName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(sequence);
                
                eventGroup = createEventGroup(eventGroupName, createdBy);

                var entityInstance = getEntityInstanceByBaseEntity(eventGroup);
                getWorkflowControl().addEntityToWorkflow(workflowEntrance, entityInstance, null, null, createdBy);
            }
        }
        
        return eventGroup;
    }
    
    public EventGroup createEventGroup(String eventGroupName, BasePK createdBy) {
        var eventGroup = EventGroupFactory.getInstance().create();
        var eventGroupDetail = EventGroupDetailFactory.getInstance().create(eventGroup, eventGroupName,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        eventGroup = EventGroupFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                eventGroup.getPrimaryKey());
        eventGroup.setActiveDetail(eventGroupDetail);
        eventGroup.setLastDetail(eventGroupDetail);
        eventGroup.store();
        
        sendEvent(eventGroup.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return eventGroup;
    }
    
    public EventGroupDetailValue getEventGroupDetailValueForUpdate(EventGroup eventGroup) {
        return eventGroup.getLastDetailForUpdate().getEventGroupDetailValue().clone();
    }
    
    private EventGroup getEventGroupByName(String eventGroupName, EntityPermission entityPermission) {
        EventGroup eventGroup;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM eventgroups, eventgroupdetails " +
                        "WHERE evgrp_activedetailid = evgrpdt_eventgroupdetailid AND evgrpdt_eventgroupname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM eventgroups, eventgroupdetails " +
                        "WHERE evgrp_activedetailid = evgrpdt_eventgroupdetailid AND evgrpdt_eventgroupname = ? " +
                        "FOR UPDATE";
            }

            var ps = EventGroupFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, eventGroupName);
            
            eventGroup = EventGroupFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return eventGroup;
    }
    
    public EventGroup getEventGroupByName(String eventGroupName) {
        return getEventGroupByName(eventGroupName, EntityPermission.READ_ONLY);
    }
    
    public EventGroup getEventGroupByNameForUpdate(String eventGroupName) {
        return getEventGroupByName(eventGroupName, EntityPermission.READ_WRITE);
    }
    
    public EventGroupDetailValue getEventGroupDetailValueByNameForUpdate(String eventGroupName) {
        return getEventGroupDetailValueForUpdate(getEventGroupByNameForUpdate(eventGroupName));
    }
    
    private List<EventGroup> getEventGroups(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM eventgroups, eventgroupdetails " +
                    "WHERE evgrp_activedetailid = evgrpdt_eventgroupdetailid " +
                    "ORDER BY evgrpdt_eventgroupname";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM eventgroups, eventgroupdetails " +
                    "WHERE evgrp_activedetailid = evgrpdt_eventgroupdetailid " +
                    "FOR UPDATE";
        }

        var ps = EventGroupFactory.getInstance().prepareStatement(query);
        
        return EventGroupFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<EventGroup> getEventGroups() {
        return getEventGroups(EntityPermission.READ_ONLY);
    }
    
    public List<EventGroup> getEventGroupsForUpdate() {
        return getEventGroups(EntityPermission.READ_WRITE);
    }
    
    public EventGroupTransfer getEventGroupTransfer(UserVisit userVisit, EventGroup eventGroup) {
        return getCoreTransferCaches(userVisit).getEventGroupTransferCache().getEventGroupTransfer(eventGroup);
    }
    
    public List<EventGroupTransfer> getEventGroupTransfers(UserVisit userVisit, Collection<EventGroup> eventGroups) {
        List<EventGroupTransfer> eventGroupTransfers = new ArrayList<>(eventGroups.size());
        var eventGroupTransferCache = getCoreTransferCaches(userVisit).getEventGroupTransferCache();
        
        eventGroups.forEach((eventGroup) ->
                eventGroupTransfers.add(eventGroupTransferCache.getEventGroupTransfer(eventGroup))
        );
        
        return eventGroupTransfers;
    }
    
    public List<EventGroupTransfer> getEventGroupTransfers(UserVisit userVisit) {
        return getEventGroupTransfers(userVisit, getEventGroups());
    }
    
    public EventGroupStatusChoicesBean getEventGroupStatusChoices(String defaultEventGroupStatusChoice, Language language, boolean allowNullChoice,
            EventGroup eventGroup, PartyPK partyPK) {
        var workflowControl = getWorkflowControl();
        var eventGroupStatusChoicesBean = new EventGroupStatusChoicesBean();
        
        if(eventGroup == null) {
            workflowControl.getWorkflowEntranceChoices(eventGroupStatusChoicesBean, defaultEventGroupStatusChoice, language, allowNullChoice,
                    workflowControl.getWorkflowByName(Workflow_EVENT_GROUP_STATUS), partyPK);
        } else {
            var entityInstance = getCoreControl().getEntityInstanceByBasePK(eventGroup.getPrimaryKey());
            var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(Workflow_EVENT_GROUP_STATUS,
                    entityInstance);
            
            workflowControl.getWorkflowDestinationChoices(eventGroupStatusChoicesBean, defaultEventGroupStatusChoice, language, allowNullChoice,
                    workflowEntityStatus.getWorkflowStep(), partyPK);
        }
        
        return eventGroupStatusChoicesBean;
    }
    
    public void setEventGroupStatus(ExecutionErrorAccumulator eea, EventGroup eventGroup, String eventGroupStatusChoice, PartyPK modifiedBy) {
        var workflowControl = getWorkflowControl();
        var entityInstance = getEntityInstanceByBaseEntity(eventGroup);
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(Workflow_EVENT_GROUP_STATUS,
                entityInstance);
        var workflowDestination = eventGroupStatusChoice == null? null:
            workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), eventGroupStatusChoice);
        
        if(workflowDestination != null || eventGroupStatusChoice == null) {
            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, null, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownEventGroupStatusChoice.name(), eventGroupStatusChoice);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Events
    // --------------------------------------------------------------------------------
    
    public Event createEvent(EventGroup eventGroup, Long eventTime, EntityInstance entityInstance, EventType eventType,
            EntityInstance relatedEntityInstance, EventType relatedEventType, EntityInstance createdBy) {
        var eventTimeSequence = session.getNextEventTimeSequence(entityInstance.getPrimaryKey());

        return EventFactory.getInstance().create(eventGroup, eventTime, eventTimeSequence, entityInstance, eventType, relatedEntityInstance, relatedEventType,
                createdBy);
    }
    
    public long countEventsByEventGroup(EventGroup eventGroup) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM events " +
                "WHERE ev_evgrp_eventgroupid = ?",
                eventGroup);
    }

    public long countEventsByEntityInstance(EntityInstance entityInstance) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM events " +
                "WHERE ev_eni_entityinstanceid = ?",
                entityInstance);
    }

    public long countEventsByEventType(EventType eventType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM events " +
                "WHERE ev_evty_eventtypeid = ?",
                eventType);
    }

    public long countEventsByCreatedBy(EntityInstance createdBy) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM events " +
                "WHERE ev_createdbyid = ?",
                createdBy);
    }

    private static final Map<EntityPermission, String> getEventsByEventGroupQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM events "
                + "WHERE ev_evgrp_eventgroupid = ? "
                + "ORDER BY ev_eventtime, ev_eventtimesequence "
                + "_LIMIT_");
        getEventsByEventGroupQueries = Collections.unmodifiableMap(queryMap);
    }

    public List<Event> getEventsByEventGroup(EventGroup eventGroup) {
        return EventFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getEventsByEventGroupQueries,
                eventGroup);
    }
    
    private static final Map<EntityPermission, String> getEventsByEntityInstanceQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM events "
                + "WHERE ev_eni_entityinstanceid = ? "
                + "ORDER BY ev_eventtime, ev_eventtimesequence "
                + "_LIMIT_");
        getEventsByEntityInstanceQueries = Collections.unmodifiableMap(queryMap);
    }

    public List<Event> getEventsByEntityInstance(EntityInstance entityInstance) {
        return EventFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getEventsByEntityInstanceQueries,
                entityInstance);
    }
    
    private static final Map<EntityPermission, String> getEventsByEventTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM events "
                + "WHERE ev_eni_entityinstanceid = ? "
                + "ORDER BY ev_eventtime, ev_eventtimesequence "
                + "_LIMIT_");
        getEventsByEventTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    public List<Event> getEventsByEventType(EventType eventType) {
        return EventFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getEventsByEventTypeQueries,
                eventType);
    }
    
    private static final Map<EntityPermission, String> getEventsByCreatedByQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM events "
                + "WHERE ev_createdbyid = ? "
                + "ORDER BY ev_eventtime, ev_eventtimesequence "
                + "_LIMIT_");
        getEventsByCreatedByQueries = Collections.unmodifiableMap(queryMap);
    }

    public List<Event> getEventsByCreatedBy(EntityInstance createdBy) {
        return EventFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getEventsByCreatedByQueries,
                createdBy);
    }
    
    private static final Map<EntityPermission, String> getEventsByEntityInstanceAndEventTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM events "
                + "WHERE ev_eni_entityinstanceid = ? AND ev_evty_eventtypeid = ? "
                + "ORDER BY ev_eventtime "
                + "FOR UPDATE");
        getEventsByEntityInstanceAndEventTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    public List<Event> getEventsByEntityInstanceAndEventTypeForUpdate(EntityInstance entityInstance, EventType eventType) {
        return EventFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, getEventsByEntityInstanceAndEventTypeQueries,
                entityInstance, eventType);
    }
    
    public List<EventTransfer> getEventTransfers(UserVisit userVisit, Collection<Event> events) {
        List<EventTransfer> eventTransfers = new ArrayList<>(events.size());
        var eventTransferCache = getCoreTransferCaches(userVisit).getEventTransferCache();
        
        events.forEach((event) ->
                eventTransfers.add(eventTransferCache.getEventTransfer(event))
        );
        
        return eventTransfers;
    }
    
    public List<EventTransfer> getEventTransfersByEntityInstance(UserVisit userVisit, EventGroup eventGroup) {
        return getEventTransfers(userVisit, getEventsByEventGroup(eventGroup));
    }
    
    public List<EventTransfer> getEventTransfersByEntityInstance(UserVisit userVisit, EntityInstance entityInstance) {
        return getEventTransfers(userVisit, getEventsByEntityInstance(entityInstance));
    }
    
    public List<EventTransfer> getEventTransfersByEntityInstance(UserVisit userVisit, EventType eventType) {
        return getEventTransfers(userVisit, getEventsByEventType(eventType));
    }
    
    public List<EventTransfer> getEventTransfersByCreatedBy(UserVisit userVisit, EntityInstance createdBy) {
        return getEventTransfers(userVisit, getEventsByCreatedBy(createdBy));
    }
    
    public void removeEvent(Event event) {
        event.remove();
    }
    
    // --------------------------------------------------------------------------------
    //   Queued Events
    // --------------------------------------------------------------------------------
    
    public QueuedEvent createQueuedEvent(Event event) {
        return QueuedEventFactory.getInstance().create(event);
    }
    
    public List<QueuedEvent> getQueuedEventsForUpdate() {
        var ps = QueuedEventFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM queuedevents " +
                "FOR UPDATE");

        return QueuedEventFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
    }
    
    public void removeQueuedEvent(QueuedEvent queuedEvent) {
        queuedEvent.remove();
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Visits
    // --------------------------------------------------------------------------------
    
    public EntityVisit createEntityVisit(final EntityInstance entityInstance, final EntityInstance visitedEntityInstance) {
        return EntityVisitFactory.getInstance().create(entityInstance, visitedEntityInstance, session.START_TIME_LONG);
    }

    private static final Map<EntityPermission, String> getEntityVisitQueries;

    static {
        getEntityVisitQueries = Map.of(
                EntityPermission.READ_ONLY, """
                        SELECT _ALL_
                        FROM entityvisits
                        WHERE evis_eni_entityinstanceid = ? AND evis_visitedentityinstanceid = ?
                        """,
                EntityPermission.READ_WRITE, """
                        SELECT _ALL_
                        FROM entityvisits
                        WHERE evis_eni_entityinstanceid = ? AND evis_visitedentityinstanceid = ?
                        FOR UPDATE
                        """);
    }

    private EntityVisit getEntityVisit(final EntityInstance entityInstance, final EntityInstance visitedEntityInstance,
            final EntityPermission entityPermission) {
        return EntityVisitFactory.getInstance().getEntityFromQuery(entityPermission, getEntityVisitQueries, entityInstance, visitedEntityInstance);
    }

    public EntityVisit getEntityVisit(final EntityInstance entityInstance, final EntityInstance visitedEntityInstance) {
        return getEntityVisit(entityInstance, visitedEntityInstance, EntityPermission.READ_ONLY);
    }

    public EntityVisit getEntityVisitForUpdate(final EntityInstance entityInstance, final EntityInstance visitedEntityInstance) {
        return getEntityVisit(entityInstance, visitedEntityInstance, EntityPermission.READ_WRITE);
    }

    public EntityVisitTransfer getEntityVisitTransfer(UserVisit userVisit, EntityVisit entityVisit) {
        return getCoreTransferCaches(userVisit).getEntityVisitTransferCache().getEntityVisitTransfer(entityVisit);
    }
    
    // --------------------------------------------------------------------------------
    //   Cache Entries
    // --------------------------------------------------------------------------------

    public CacheEntry createCacheEntry(String cacheEntryKey, MimeType mimeType, Long createdTime, Long validUntilTime, String clob, ByteArray blob,
            Set<String> entityRefs) {
        var cacheEntry = createCacheEntry(cacheEntryKey, mimeType, createdTime, validUntilTime);
        var entityAttributeTypeName = mimeType.getLastDetail().getEntityAttributeType().getEntityAttributeTypeName();

        if(entityAttributeTypeName.equals(EntityAttributeTypes.CLOB.name())) {
            createCacheClobEntry(cacheEntry, clob);
        } else if(entityAttributeTypeName.equals(EntityAttributeTypes.BLOB.name())) {
            createCacheBlobEntry(cacheEntry, blob);
        }

        if(entityRefs != null) {
            createCacheEntryDependencies(cacheEntry, entityRefs);
        }

        return cacheEntry;
    }

    public CacheEntry createCacheEntry(String cacheEntryKey, MimeType mimeType, Long createdTime, Long validUntilTime) {
        return CacheEntryFactory.getInstance().create(cacheEntryKey, mimeType, createdTime, validUntilTime);
    }

    public long countCacheEntries() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM cacheentries");
    }

    private static final Map<EntityPermission, String> getCacheEntryByCacheEntryKeyQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM cacheentries " +
                "WHERE cent_cacheentrykey = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM cacheentries " +
                "WHERE cent_cacheentrykey = ? " +
                "FOR UPDATE");
        getCacheEntryByCacheEntryKeyQueries = Collections.unmodifiableMap(queryMap);
    }

    private CacheEntry getCacheEntryByCacheEntryKey(String cacheEntryKey, EntityPermission entityPermission) {
        return CacheEntryFactory.getInstance().getEntityFromQuery(entityPermission, getCacheEntryByCacheEntryKeyQueries,
                cacheEntryKey);
    }

    public CacheEntry getCacheEntryByCacheEntryKey(String cacheEntryKey) {
        return getCacheEntryByCacheEntryKey(cacheEntryKey, EntityPermission.READ_ONLY);
    }

    public CacheEntry getCacheEntryByCacheEntryKeyForUpdate(String cacheEntryKey) {
        return getCacheEntryByCacheEntryKey(cacheEntryKey, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getCacheEntriesByCacheEntryKeyQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM cacheentries " +
                "ORDER BY cent_cacheentrykey " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM cacheentries " +
                "FOR UPDATE");
        getCacheEntriesByCacheEntryKeyQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<CacheEntry> getCacheEntries(EntityPermission entityPermission) {
        return CacheEntryFactory.getInstance().getEntitiesFromQuery(entityPermission, getCacheEntriesByCacheEntryKeyQueries);
    }

    public List<CacheEntry> getCacheEntries() {
        return getCacheEntries(EntityPermission.READ_ONLY);
    }

    public List<CacheEntry> getCacheEntriesForUpdate() {
        return getCacheEntries(EntityPermission.READ_WRITE);
    }

    public CacheEntryTransfer getCacheEntryTransfer(UserVisit userVisit, CacheEntry cacheEntry) {
        return getCoreTransferCaches(userVisit).getCacheEntryTransferCache().getCacheEntryTransfer(cacheEntry);
    }

    public CacheEntryTransfer getCacheEntryTransferByCacheEntryKey(UserVisit userVisit, String cacheEntryKey) {
        var cacheEntry = getCacheEntryByCacheEntryKey(cacheEntryKey);
        CacheEntryTransfer cacheEntryTransfer = null;

        if(cacheEntry != null) {
            var validUntilTime = cacheEntry.getValidUntilTime();
            
            if(validUntilTime != null && validUntilTime < session.START_TIME) {
                removeCacheEntry(cacheEntry);
            } else {
                cacheEntryTransfer = getCacheEntryTransfer(userVisit, cacheEntry);
            }
        }

        return cacheEntryTransfer;
    }

    public List<CacheEntryTransfer> getCacheEntryTransfers(UserVisit userVisit, Collection<CacheEntry> cacheEntries) {
        List<CacheEntryTransfer> cacheEntryTransfers = new ArrayList<>(cacheEntries.size());
        var cacheEntryTransferCache = getCoreTransferCaches(userVisit).getCacheEntryTransferCache();

        cacheEntries.forEach((cacheEntry) ->
                cacheEntryTransfers.add(cacheEntryTransferCache.getCacheEntryTransfer(cacheEntry))
        );

        return cacheEntryTransfers;
    }

    public List<CacheEntryTransfer> getCacheEntryTransfers(UserVisit userVisit) {
        return getCacheEntryTransfers(userVisit, getCacheEntries());
    }

    public void removeCacheEntry(CacheEntry cacheEntry) {
        cacheEntry.remove();
    }
    
    public void removeCacheEntries(List<CacheEntry> cacheEntries) {
        cacheEntries.forEach(this::removeCacheEntry);
    }
    
    public void removeCacheEntries() {
        removeCacheEntries(getCacheEntriesForUpdate());
    }

    private List<CacheEntryPK> getCacheEntryPKsByEntityInstance(final EntityInstance entityInstance) {
        final var instance = CacheEntryFactory.getInstance();
        
        return instance.getPKsFromQueryAsList(
                instance.prepareStatement(
                        "SELECT _PK_ "
                        + "FROM cacheentrydependencies, cacheentries "
                        + "WHERE centd_eni_entityinstanceid = ? "
                        + "AND centd_cent_cacheentryid = cent_cacheentryid"),
                entityInstance);
    }

    public void removeCacheEntriesByEntityInstance(final EntityInstance entityInstance) {
        CacheEntryFactory.getInstance().remove(getCacheEntryPKsByEntityInstance(entityInstance));
    }

    // --------------------------------------------------------------------------------
    //   Cache Clob Entries
    // --------------------------------------------------------------------------------

    public CacheClobEntry createCacheClobEntry(CacheEntry cacheEntry, String clob) {
        return CacheClobEntryFactory.getInstance().create(cacheEntry, clob);
    }

    private static final Map<EntityPermission, String> getCacheClobEntryByCacheEntryQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM cacheclobentries " +
                "WHERE ccent_cent_cacheentryid = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM cacheclobentries " +
                "WHERE ccent_cent_cacheentryid = ? " +
                "FOR UPDATE");
        getCacheClobEntryByCacheEntryQueries = Collections.unmodifiableMap(queryMap);
    }

    private CacheClobEntry getCacheClobEntryByCacheEntry(CacheEntry cacheEntry, EntityPermission entityPermission) {
        return CacheClobEntryFactory.getInstance().getEntityFromQuery(entityPermission, getCacheClobEntryByCacheEntryQueries,
                cacheEntry);
    }

    public CacheClobEntry getCacheClobEntryByCacheEntry(CacheEntry cacheEntry) {
        return getCacheClobEntryByCacheEntry(cacheEntry, EntityPermission.READ_ONLY);
    }

    public CacheClobEntry getCacheClobEntryByCacheEntryForUpdate(CacheEntry cacheEntry) {
        return getCacheClobEntryByCacheEntry(cacheEntry, EntityPermission.READ_WRITE);
    }

    // --------------------------------------------------------------------------------
    //   Cache Blob Entries
    // --------------------------------------------------------------------------------

    public CacheBlobEntry createCacheBlobEntry(CacheEntry cacheEntry, ByteArray blob) {
        return CacheBlobEntryFactory.getInstance().create(cacheEntry, blob);
    }

    private static final Map<EntityPermission, String> getCacheBlobEntryByCacheEntryQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM cacheblobentries " +
                "WHERE ccent_cent_cacheentryid = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM cacheblobentries " +
                "WHERE ccent_cent_cacheentryid = ? " +
                "FOR UPDATE");
        getCacheBlobEntryByCacheEntryQueries = Collections.unmodifiableMap(queryMap);
    }

    private CacheBlobEntry getCacheBlobEntryByCacheEntry(CacheEntry cacheEntry, EntityPermission entityPermission) {
        return CacheBlobEntryFactory.getInstance().getEntityFromQuery(entityPermission, getCacheBlobEntryByCacheEntryQueries,
                cacheEntry);
    }

    public CacheBlobEntry getCacheBlobEntryByCacheEntry(CacheEntry cacheEntry) {
        return getCacheBlobEntryByCacheEntry(cacheEntry, EntityPermission.READ_ONLY);
    }

    public CacheBlobEntry getCacheBlobEntryByCacheEntryForUpdate(CacheEntry cacheEntry) {
        return getCacheBlobEntryByCacheEntry(cacheEntry, EntityPermission.READ_WRITE);
    }

    // --------------------------------------------------------------------------------
    //   Cache Entry Dependencies
    // --------------------------------------------------------------------------------
    
    public void createCacheEntryDependencies(CacheEntry cacheEntry, Set<String> entityRefs) {
        List<CacheEntryDependencyValue> cacheEntryDependencyValues = new ArrayList<>(entityRefs.size());

        for(var entityRef : entityRefs) {
            var entityInstance = getEntityInstanceByEntityRef(entityRef);

            if(entityInstance != null) {
                cacheEntryDependencyValues.add(new CacheEntryDependencyValue(cacheEntry.getPrimaryKey(), entityInstance.getPrimaryKey()));
            }
        }
        
        CacheEntryDependencyFactory.getInstance().create(cacheEntryDependencyValues);
    }

    public CacheEntryDependency createCacheEntryDependency(CacheEntry cacheEntry, EntityInstance entityInstance) {
        return CacheEntryDependencyFactory.getInstance().create(cacheEntry, entityInstance);
    }

    private static final Map<EntityPermission, String> getCacheEntryDependenciesByEntityInstanceQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM cacheentrydependencies, cacheentries "
                + "WHERE centd_eni_entityinstanceid = ? "
                + "AND centd_cent_cacheentryid = cent_cacheentryid "
                + "ORDER BY cent_cacheentrykey");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM cacheentrydependencies "
                + "WHERE centd_eni_entityinstanceid = ? "
                + "FOR UPDATE");
        getCacheEntryDependenciesByEntityInstanceQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<CacheEntryDependency> getCacheEntryDependenciesByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        return CacheEntryDependencyFactory.getInstance().getEntitiesFromQuery(entityPermission, getCacheEntryDependenciesByEntityInstanceQueries,
                entityInstance);
    }

    public List<CacheEntryDependency> getCacheEntryDependenciesByEntityInstance(EntityInstance entityInstance) {
        return getCacheEntryDependenciesByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public List<CacheEntryDependency> getCacheEntryDependenciesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getCacheEntryDependenciesByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getCacheEntryDependenciesByCacheEntryQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM cacheentrydependencies, entityinstances, entitytypes, entitytypedetails, componentvendors, componentvendordetails "
                + "WHERE centd_cent_cacheentryid = ? "
                + "AND centd_eni_entityinstanceid = eni_entityinstanceid "
                + "AND eni_ent_entitytypeid = ent_entitytypeid AND ent_lastdetailid = entdt_entitytypedetailid "
                + "AND entdt_cvnd_componentvendorid = cvnd_componentvendorid AND cvnd_lastdetailid = cvndd_componentvendordetailid "
                + "ORDER BY cvndd_componentvendorname, entdt_sortorder, entdt_entitytypename, eni_entityuniqueid");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM cacheentrydependencies "
                + "WHERE centd_cent_cacheentryid = ? "
                + "FOR UPDATE");
        getCacheEntryDependenciesByCacheEntryQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<CacheEntryDependency> getCacheEntryDependenciesByCacheEntry(CacheEntry cacheEntry, EntityPermission entityPermission) {
        return CacheEntryDependencyFactory.getInstance().getEntitiesFromQuery(entityPermission, getCacheEntryDependenciesByCacheEntryQueries,
                cacheEntry);
    }

    public List<CacheEntryDependency> getCacheEntryDependenciesByCacheEntry(CacheEntry cacheEntry) {
        return getCacheEntryDependenciesByCacheEntry(cacheEntry, EntityPermission.READ_ONLY);
    }

    public List<CacheEntryDependency> getCacheEntryDependenciesByCacheEntryForUpdate(CacheEntry cacheEntry) {
        return getCacheEntryDependenciesByCacheEntry(cacheEntry, EntityPermission.READ_WRITE);
    }

    public CacheEntryDependencyTransfer getCacheEntryDependencyTransfer(UserVisit userVisit, CacheEntryDependency cacheEntryDependency) {
        return getCoreTransferCaches(userVisit).getCacheEntryDependencyTransferCache().getCacheEntryDependencyTransfer(cacheEntryDependency);
    }

    public List<CacheEntryDependencyTransfer> getCacheEntryDependencyTransfers(UserVisit userVisit, Collection<CacheEntryDependency> cacheEntries) {
        List<CacheEntryDependencyTransfer> cacheEntryDependencyTransfers = new ArrayList<>(cacheEntries.size());
        var cacheEntryDependencyTransferCache = getCoreTransferCaches(userVisit).getCacheEntryDependencyTransferCache();

        cacheEntries.forEach((cacheEntryDependency) ->
                cacheEntryDependencyTransfers.add(cacheEntryDependencyTransferCache.getCacheEntryDependencyTransfer(cacheEntryDependency))
        );

        return cacheEntryDependencyTransfers;
    }

    public List<CacheEntryDependencyTransfer> getCacheEntryDependencyTransfersByCacheEntry(UserVisit userVisit, CacheEntry cacheEntry) {
        return getCacheEntryDependencyTransfers(userVisit, getCacheEntryDependenciesByCacheEntry(cacheEntry));
    }

    // --------------------------------------------------------------------------------
    //   Entity Alias Types
    // --------------------------------------------------------------------------------

    public EntityAliasType createEntityAliasType(EntityType entityType, String entityAliasTypeName,
            String validationPattern, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultEntityAliasType = getDefaultEntityAliasType(entityType);
        var defaultFound = defaultEntityAliasType != null;

        if(defaultFound && isDefault) {
            var defaultEntityAliasTypeDetailValue = getDefaultEntityAliasTypeDetailValueForUpdate(entityType);

            defaultEntityAliasTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateEntityAliasTypeFromValue(defaultEntityAliasTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        var entityAliasType = EntityAliasTypeFactory.getInstance().create();
        var entityAliasTypeDetail = EntityAliasTypeDetailFactory.getInstance().create(entityAliasType, entityType,
                entityAliasTypeName, validationPattern, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        entityAliasType = EntityAliasTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                entityAliasType.getPrimaryKey());
        entityAliasType.setActiveDetail(entityAliasTypeDetail);
        entityAliasType.setLastDetail(entityAliasTypeDetail);
        entityAliasType.store();

        sendEvent(entityAliasType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return entityAliasType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.EntityAliasType */
    public EntityAliasType getEntityAliasTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new EntityAliasTypePK(entityInstance.getEntityUniqueId());

        return EntityAliasTypeFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public EntityAliasType getEntityAliasTypeByEntityInstance(EntityInstance entityInstance) {
        return getEntityAliasTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public EntityAliasType getEntityAliasTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getEntityAliasTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public EntityAliasType getEntityAliasTypeByPK(EntityAliasTypePK pk) {
        return EntityAliasTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);
    }

    public long countEntityAliasTypesByEntityType(EntityType entityType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM entityaliastypes, entityaliastypedetails " +
                        "WHERE eniat_activedetailid = eniatdt_entityaliastypedetailid AND eniatdt_ent_entitytypeid = ?",
                entityType);
    }

    public EntityAliasType getEntityAliasTypeByName(EntityType entityType, String entityAliasTypeName, EntityPermission entityPermission) {
        EntityAliasType entityAliasType;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityaliastypes, entityaliastypedetails " +
                        "WHERE eniat_activedetailid = eniatdt_entityaliastypedetailid " +
                        "AND eniatdt_ent_entitytypeid = ? AND eniatdt_entityaliastypename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityaliastypes, entityaliastypedetails " +
                        "WHERE eniat_activedetailid = eniatdt_entityaliastypedetailid " +
                        "AND eniatdt_ent_entitytypeid = ? AND eniatdt_entityaliastypename = ? " +
                        "FOR UPDATE";
            }

            var ps = EntityAliasTypeFactory.getInstance().prepareStatement(query);

            ps.setLong(1, entityType.getPrimaryKey().getEntityId());
            ps.setString(2, entityAliasTypeName);

            entityAliasType = EntityAliasTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityAliasType;
    }

    public EntityAliasType getEntityAliasTypeByName(EntityType entityType, String entityAliasTypeName) {
        return getEntityAliasTypeByName(entityType, entityAliasTypeName, EntityPermission.READ_ONLY);
    }

    public EntityAliasType getEntityAliasTypeByNameForUpdate(EntityType entityType, String entityAliasTypeName) {
        return getEntityAliasTypeByName(entityType, entityAliasTypeName, EntityPermission.READ_WRITE);
    }

    public EntityAliasTypeDetailValue getEntityAliasTypeDetailValueForUpdate(EntityAliasType entityAliasType) {
        return entityAliasType == null? null: entityAliasType.getLastDetailForUpdate().getEntityAliasTypeDetailValue().clone();
    }

    public EntityAliasTypeDetailValue getEntityAliasTypeDetailValueByNameForUpdate(EntityType entityType, String entityAliasTypeName) {
        return getEntityAliasTypeDetailValueForUpdate(getEntityAliasTypeByNameForUpdate(entityType, entityAliasTypeName));
    }

    private static final Map<EntityPermission, String> getDefaultEntityAliasTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM entityaliastypes, entityaliastypedetails " +
                        "WHERE eniat_activedetailid = eniatdt_entityaliastypedetailid " +
                        "AND eniatdt_ent_entitytypeid = ? " +
                        "AND eniatdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM entityaliastypes, entityaliastypedetails " +
                        "WHERE eniat_activedetailid = eniatdt_entityaliastypedetailid " +
                        "AND eniatdt_ent_entitytypeid = ? " +
                        "AND eniatdt_isdefault = 1 " +
                        "FOR UPDATE");
        getDefaultEntityAliasTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private EntityAliasType getDefaultEntityAliasType(EntityPermission entityPermission, EntityType entityType) {
        return EntityAliasTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultEntityAliasTypeQueries,
                entityType);
    }

    public EntityAliasType getDefaultEntityAliasType(EntityType entityType) {
        return getDefaultEntityAliasType(EntityPermission.READ_ONLY, entityType);
    }

    public EntityAliasType getDefaultEntityAliasTypeForUpdate(EntityType entityType) {
        return getDefaultEntityAliasType(EntityPermission.READ_WRITE, entityType);
    }

    public EntityAliasTypeDetailValue getDefaultEntityAliasTypeDetailValueForUpdate(EntityType entityType) {
        return getDefaultEntityAliasTypeForUpdate(entityType).getLastDetailForUpdate().getEntityAliasTypeDetailValue().clone();
    }

    private List<EntityAliasType> getEntityAliasTypesByEntityType(EntityType entityType, EntityPermission entityPermission) {
        List<EntityAliasType> entityAliasTypes;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityaliastypes, entityaliastypedetails " +
                        "WHERE eniat_activedetailid = eniatdt_entityaliastypedetailid " +
                        "AND eniatdt_ent_entitytypeid = ? " +
                        "ORDER BY eniatdt_sortorder, eniatdt_entityaliastypename " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityaliastypes, entityaliastypedetails " +
                        "WHERE eniat_activedetailid = eniatdt_entityaliastypedetailid " +
                        "AND eniatdt_ent_entitytypeid = ? " +
                        "FOR UPDATE";
            }

            var ps = EntityAliasTypeFactory.getInstance().prepareStatement(query);

            ps.setLong(1, entityType.getPrimaryKey().getEntityId());

            entityAliasTypes = EntityAliasTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityAliasTypes;
    }

    public List<EntityAliasType> getEntityAliasTypesByEntityType(EntityType entityType) {
        return getEntityAliasTypesByEntityType(entityType, EntityPermission.READ_ONLY);
    }

    public List<EntityAliasType> getEntityAliasTypesByEntityTypeForUpdate(EntityType entityType) {
        return getEntityAliasTypesByEntityType(entityType, EntityPermission.READ_WRITE);
    }

    public EntityAliasTypeTransfer getEntityAliasTypeTransfer(UserVisit userVisit, EntityAliasType entityAliasType, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityAliasTypeTransferCache().getEntityAliasTypeTransfer(entityAliasType, entityInstance);
    }

    public List<EntityAliasTypeTransfer> getEntityAliasTypeTransfers(UserVisit userVisit, Collection<EntityAliasType> entityAliasTypes, EntityInstance entityInstance) {
        List<EntityAliasTypeTransfer> entityAliasTypeTransfers = new ArrayList<>(entityAliasTypes.size());
        var entityAliasTypeTransferCache = getCoreTransferCaches(userVisit).getEntityAliasTypeTransferCache();

        entityAliasTypes.forEach((entityAliasType) ->
                entityAliasTypeTransfers.add(entityAliasTypeTransferCache.getEntityAliasTypeTransfer(entityAliasType, entityInstance))
        );

        return entityAliasTypeTransfers;
    }

    public List<EntityAliasTypeTransfer> getEntityAliasTypeTransfersByEntityType(UserVisit userVisit, EntityType entityType, EntityInstance entityInstance) {
        return getEntityAliasTypeTransfers(userVisit, getEntityAliasTypesByEntityType(entityType), entityInstance);
    }

    private void updateEntityAliasTypeFromValue(EntityAliasTypeDetailValue entityAliasTypeDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(entityAliasTypeDetailValue.hasBeenModified()) {
            var entityAliasType = EntityAliasTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    entityAliasTypeDetailValue.getEntityAliasTypePK());
            var entityAliasTypeDetail = entityAliasType.getActiveDetailForUpdate();

            entityAliasTypeDetail.setThruTime(session.START_TIME_LONG);
            entityAliasTypeDetail.store();

            var entityTypePK = entityAliasTypeDetail.getEntityTypePK();
            var entityAliasTypePK = entityAliasTypeDetail.getEntityAliasTypePK(); // Not updated
            var entityAliasTypeName = entityAliasTypeDetailValue.getEntityAliasTypeName();
            var validationPattern = entityAliasTypeDetailValue.getValidationPattern();
            var isDefault = entityAliasTypeDetailValue.getIsDefault();
            var sortOrder = entityAliasTypeDetailValue.getSortOrder();

            if(checkDefault) {
                var entityType = entityAliasTypeDetail.getEntityType();
                var defaultEntityAliasType = getDefaultEntityAliasType(entityType);
                var defaultFound = defaultEntityAliasType != null && !defaultEntityAliasType.equals(entityAliasType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultEntityAliasTypeDetailValue = getDefaultEntityAliasTypeDetailValueForUpdate(entityType);

                    defaultEntityAliasTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updateEntityAliasTypeFromValue(defaultEntityAliasTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            entityAliasTypeDetail = EntityAliasTypeDetailFactory.getInstance().create(entityAliasTypePK, entityTypePK,
                    entityAliasTypeName, validationPattern, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            entityAliasType.setActiveDetail(entityAliasTypeDetail);
            entityAliasType.setLastDetail(entityAliasTypeDetail);

            sendEvent(entityAliasTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateEntityAliasTypeFromValue(EntityAliasTypeDetailValue entityAliasTypeDetailValue, BasePK updatedBy) {
        updateEntityAliasTypeFromValue(entityAliasTypeDetailValue, true, updatedBy);
    }

    public EntityAliasTypeChoicesBean getEntityAliasTypeChoices(String defaultEntityAliasTypeChoice, Language language,
            boolean allowNullChoice, EntityType entityType) {
        var entityAliasTypes = getEntityAliasTypesByEntityType(entityType);
        var size = entityAliasTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultEntityAliasTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var entityAliasType : entityAliasTypes) {
            var entityAliasTypeDetail = entityAliasType.getLastDetail();
            var label = getBestEntityAliasTypeDescription(entityAliasType, language);
            var value = entityAliasTypeDetail.getEntityAliasTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultEntityAliasTypeChoice != null && defaultEntityAliasTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && entityAliasTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new EntityAliasTypeChoicesBean(labels, values, defaultValue);
    }

    private void deleteEntityAliasType(EntityAliasType entityAliasType, boolean checkDefault, BasePK deletedBy) {
        var entityAliasTypeDetail = entityAliasType.getLastDetailForUpdate();

        deleteEntityAliasesByEntityAliasType(entityAliasType, deletedBy);
        deleteEntityAliasTypeDescriptionsByEntityAliasType(entityAliasType, deletedBy);

        entityAliasTypeDetail.setThruTime(session.START_TIME_LONG);
        entityAliasType.setActiveDetail(null);
        entityAliasType.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var defaultEntityAliasType = getDefaultEntityAliasType(entityAliasTypeDetail.getEntityType());

            if(defaultEntityAliasType == null) {
                var entityType = entityAliasTypeDetail.getEntityType();
                var entityAliasTypes = getEntityAliasTypesByEntityTypeForUpdate(entityType);

                if(!entityAliasTypes.isEmpty()) {
                    var iter = entityAliasTypes.iterator();
                    if(iter.hasNext()) {
                        defaultEntityAliasType = iter.next();
                    }
                    var entityAliasTypeDetailValue = Objects.requireNonNull(defaultEntityAliasType).getLastDetailForUpdate().getEntityAliasTypeDetailValue().clone();

                    entityAliasTypeDetailValue.setIsDefault(Boolean.TRUE);
                    updateEntityAliasTypeFromValue(entityAliasTypeDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(entityAliasType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteEntityAliasType(EntityAliasType entityAliasType, BasePK deletedBy) {
        deleteEntityAliasType(entityAliasType, true, deletedBy);
    }

    private void deleteEntityAliasTypes(List<EntityAliasType> entityAliasTypes, boolean checkDefault, BasePK deletedBy) {
        entityAliasTypes.forEach((entityAliasType) -> deleteEntityAliasType(entityAliasType, checkDefault, deletedBy));
    }

    public void deleteEntityAliasTypesByEntityType(EntityType entityType, BasePK deletedBy) {
        deleteEntityAliasTypes(getEntityAliasTypesByEntityTypeForUpdate(entityType), false, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Entity Alias Type Descriptions
    // --------------------------------------------------------------------------------

    public EntityAliasTypeDescription createEntityAliasTypeDescription(EntityAliasType entityAliasType, Language language,
            String description, BasePK createdBy) {
        var entityAliasTypeDescription = EntityAliasTypeDescriptionFactory.getInstance().create(session,
                entityAliasType, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(entityAliasType.getPrimaryKey(), EventTypes.MODIFY, entityAliasTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return entityAliasTypeDescription;
    }

    private EntityAliasTypeDescription getEntityAliasTypeDescription(EntityAliasType entityAliasType, Language language,
            EntityPermission entityPermission) {
        EntityAliasTypeDescription entityAliasTypeDescription;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityaliastypedescriptions " +
                        "WHERE eniatd_eniat_entityaliastypeid = ? AND eniatd_lang_languageid = ? AND eniatd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityaliastypedescriptions " +
                        "WHERE eniatd_eniat_entityaliastypeid = ? AND eniatd_lang_languageid = ? AND eniatd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = EntityAliasTypeDescriptionFactory.getInstance().prepareStatement(query);

            ps.setLong(1, entityAliasType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);

            entityAliasTypeDescription = EntityAliasTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityAliasTypeDescription;
    }

    public EntityAliasTypeDescription getEntityAliasTypeDescription(EntityAliasType entityAliasType, Language language) {
        return getEntityAliasTypeDescription(entityAliasType, language, EntityPermission.READ_ONLY);
    }

    public EntityAliasTypeDescription getEntityAliasTypeDescriptionForUpdate(EntityAliasType entityAliasType, Language language) {
        return getEntityAliasTypeDescription(entityAliasType, language, EntityPermission.READ_WRITE);
    }

    public EntityAliasTypeDescriptionValue getEntityAliasTypeDescriptionValue(EntityAliasTypeDescription entityAliasTypeDescription) {
        return entityAliasTypeDescription == null? null: entityAliasTypeDescription.getEntityAliasTypeDescriptionValue().clone();
    }

    public EntityAliasTypeDescriptionValue getEntityAliasTypeDescriptionValueForUpdate(EntityAliasType entityAliasType, Language language) {
        return getEntityAliasTypeDescriptionValue(getEntityAliasTypeDescriptionForUpdate(entityAliasType, language));
    }

    private List<EntityAliasTypeDescription> getEntityAliasTypeDescriptionsByEntityAliasType(EntityAliasType entityAliasType,
            EntityPermission entityPermission) {
        List<EntityAliasTypeDescription> entityAliasTypeDescriptions;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityaliastypedescriptions, languages " +
                        "WHERE eniatd_eniat_entityaliastypeid = ? AND eniatd_thrutime = ? AND eniatd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityaliastypedescriptions " +
                        "WHERE eniatd_eniat_entityaliastypeid = ? AND eniatd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = EntityAliasTypeDescriptionFactory.getInstance().prepareStatement(query);

            ps.setLong(1, entityAliasType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            entityAliasTypeDescriptions = EntityAliasTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityAliasTypeDescriptions;
    }

    public List<EntityAliasTypeDescription> getEntityAliasTypeDescriptionsByEntityAliasType(EntityAliasType entityAliasType) {
        return getEntityAliasTypeDescriptionsByEntityAliasType(entityAliasType, EntityPermission.READ_ONLY);
    }

    public List<EntityAliasTypeDescription> getEntityAliasTypeDescriptionsByEntityAliasTypeForUpdate(EntityAliasType entityAliasType) {
        return getEntityAliasTypeDescriptionsByEntityAliasType(entityAliasType, EntityPermission.READ_WRITE);
    }

    public String getBestEntityAliasTypeDescription(EntityAliasType entityAliasType, Language language) {
        String description;
        var entityAliasTypeDescription = getEntityAliasTypeDescription(entityAliasType, language);

        if(entityAliasTypeDescription == null && !language.getIsDefault()) {
            entityAliasTypeDescription = getEntityAliasTypeDescription(entityAliasType, getPartyControl().getDefaultLanguage());
        }

        if(entityAliasTypeDescription == null) {
            description = entityAliasType.getLastDetail().getEntityAliasTypeName();
        } else {
            description = entityAliasTypeDescription.getDescription();
        }

        return description;
    }

    public EntityAliasTypeDescriptionTransfer getEntityAliasTypeDescriptionTransfer(UserVisit userVisit, EntityAliasTypeDescription entityAliasTypeDescription, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityAliasTypeDescriptionTransferCache().getEntityAliasTypeDescriptionTransfer(entityAliasTypeDescription, entityInstance);
    }

    public List<EntityAliasTypeDescriptionTransfer> getEntityAliasTypeDescriptionTransfersByEntityAliasType(UserVisit userVisit,
            EntityAliasType entityAliasType, EntityInstance entityInstance) {
        var entityAliasTypeDescriptions = getEntityAliasTypeDescriptionsByEntityAliasType(entityAliasType);
        List<EntityAliasTypeDescriptionTransfer> entityAliasTypeDescriptionTransfers = new ArrayList<>(entityAliasTypeDescriptions.size());
        var entityAliasTypeDescriptionTransferCache = getCoreTransferCaches(userVisit).getEntityAliasTypeDescriptionTransferCache();

        entityAliasTypeDescriptions.forEach((entityAliasTypeDescription) ->
                entityAliasTypeDescriptionTransfers.add(entityAliasTypeDescriptionTransferCache.getEntityAliasTypeDescriptionTransfer(entityAliasTypeDescription, entityInstance))
        );

        return entityAliasTypeDescriptionTransfers;
    }

    public void updateEntityAliasTypeDescriptionFromValue(EntityAliasTypeDescriptionValue entityAliasTypeDescriptionValue, BasePK updatedBy) {
        if(entityAliasTypeDescriptionValue.hasBeenModified()) {
            var entityAliasTypeDescription = EntityAliasTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    entityAliasTypeDescriptionValue.getPrimaryKey());

            entityAliasTypeDescription.setThruTime(session.START_TIME_LONG);
            entityAliasTypeDescription.store();

            var entityAliasType = entityAliasTypeDescription.getEntityAliasType();
            var language = entityAliasTypeDescription.getLanguage();
            var description = entityAliasTypeDescriptionValue.getDescription();

            entityAliasTypeDescription = EntityAliasTypeDescriptionFactory.getInstance().create(entityAliasType, language,
                    description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(entityAliasType.getPrimaryKey(), EventTypes.MODIFY, entityAliasTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteEntityAliasTypeDescription(EntityAliasTypeDescription entityAliasTypeDescription, BasePK deletedBy) {
        entityAliasTypeDescription.setThruTime(session.START_TIME_LONG);

        sendEvent(entityAliasTypeDescription.getEntityAliasTypePK(), EventTypes.MODIFY, entityAliasTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteEntityAliasTypeDescriptionsByEntityAliasType(EntityAliasType entityAliasType, BasePK deletedBy) {
        var entityAliasTypeDescriptions = getEntityAliasTypeDescriptionsByEntityAliasTypeForUpdate(entityAliasType);

        entityAliasTypeDescriptions.forEach((entityAliasTypeDescription) ->
                deleteEntityAliasTypeDescription(entityAliasTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Entity Aliases
    // --------------------------------------------------------------------------------

    public EntityAlias createEntityAlias(EntityInstance entityInstance, EntityAliasType entityAliasType, String alias,
            BasePK createdBy) {
        var entityAlias = EntityAliasFactory.getInstance().create(entityInstance, entityAliasType,
                alias, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(entityInstance, EventTypes.MODIFY, entityAliasType.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return entityAlias;
    }

    public long countEntityAliasesByEntityInstance(EntityInstance entityInstance) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM entityaliases " +
                "WHERE enial_eni_entityinstanceid = ? AND enial_thrutime = ?",
                entityInstance, Session.MAX_TIME);
    }

    public long countEntityAliasesByEntityAliasType(EntityAliasType entityAliasType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM entityaliases " +
                "WHERE enial_eniat_entityaliastypeid = ? AND enial_thrutime = ?",
                entityAliasType, Session.MAX_TIME);
    }

    public long countEntityAliasHistory(EntityInstance entityInstance, EntityAliasType entityAliasType) {
        return session.queryForLong("""
                    SELECT COUNT(*)
                    FROM entityaliases
                    WHERE enial_eni_entityinstanceid = ? AND enial_eniat_entityaliastypeid = ?
                    """, entityInstance, entityAliasType);
    }

    private static final Map<EntityPermission, String> getEntityAliasHistoryQueries;

    static {
        var queryMap = Map.of(
                EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM entityaliases
                WHERE enial_eni_entityinstanceid = ? AND enial_eniat_entityaliastypeid = ?
                ORDER BY enial_thrutime
                _LIMIT_
                """);
        getEntityAliasHistoryQueries = Collections.unmodifiableMap(queryMap);
    }

    public List<EntityAlias> getEntityAliasHistory(EntityInstance entityInstance, EntityAliasType entityAliasType) {
        return EntityAliasFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityAliasHistoryQueries,
                entityInstance, entityAliasType);
    }
    
    private EntityAlias getEntityAlias(EntityInstance entityInstance, EntityAliasType entityAliasType,
            EntityPermission entityPermission) {
        EntityAlias entityAlias;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityaliases " +
                        "WHERE enial_eni_entityinstanceid = ? AND enial_eniat_entityaliastypeid = ? AND enial_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityaliases " +
                        "WHERE enial_eni_entityinstanceid = ? AND enial_eniat_entityaliastypeid = ? AND enial_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = EntityAliasFactory.getInstance().prepareStatement(query);

            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, entityAliasType.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);

            entityAlias = EntityAliasFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityAlias;
    }

    public EntityAlias getEntityAlias(EntityInstance entityInstance, EntityAliasType entityAliasType) {
        return getEntityAlias(entityInstance, entityAliasType, EntityPermission.READ_ONLY);
    }

    public EntityAlias getEntityAliasForUpdate(EntityInstance entityInstance, EntityAliasType entityAliasType) {
        return getEntityAlias(entityInstance, entityAliasType, EntityPermission.READ_WRITE);
    }

    public EntityAliasValue getEntityAliasValueForUpdate(EntityAlias entityAlias) {
        return entityAlias == null ? null : entityAlias.getEntityAliasValue().clone();
    }

    public EntityAliasValue getEntityAliasValueForUpdate(EntityInstance entityInstance, EntityAliasType entityAliasType) {
        return getEntityAliasValueForUpdate(getEntityAliasForUpdate(entityInstance, entityAliasType));
    }

    private List<EntityAlias> getEntityAliasesByEntityAliasType(EntityAliasType entityAliasType, EntityPermission entityPermission) {
        List<EntityAlias> entityAliases;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityaliases " +
                        "WHERE enial_eniat_entityaliastypeid = ? AND enial_thrutime = ? " +
                        "ORDER BY enial_alias " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityaliases " +
                        "WHERE enial_eniat_entityaliastypeid = ? AND enial_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = EntityAliasFactory.getInstance().prepareStatement(query);

            ps.setLong(1, entityAliasType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            entityAliases = EntityAliasFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityAliases;
    }

    public List<EntityAlias> getEntityAliasesByEntityAliasType(EntityAliasType entityAliasType) {
        return getEntityAliasesByEntityAliasType(entityAliasType, EntityPermission.READ_ONLY);
    }

    public List<EntityAlias> getEntityAliasesByEntityAliasTypeForUpdate(EntityAliasType entityAliasType) {
        return getEntityAliasesByEntityAliasType(entityAliasType, EntityPermission.READ_WRITE);
    }

    private List<EntityAlias> getEntityAliasesByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        List<EntityAlias> entityAliases;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityaliases " +
                        "WHERE enial_eni_entityinstanceid = ? AND enial_thrutime = ? " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityaliases " +
                        "WHERE enial_eni_entityinstanceid = ? AND enial_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = EntityAliasFactory.getInstance().prepareStatement(query);

            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            entityAliases = EntityAliasFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityAliases;
    }
    public List<EntityAlias> getEntityAliasesByEntityInstance(EntityInstance entityInstance) {
        return getEntityAliasesByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public List<EntityAlias> getEntityAliasesByEntityInstanceForUpdate(EntityInstance entityInstance){
        return getEntityAliasesByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public EntityAliasTransfer getEntityAliasTransfer(UserVisit userVisit, EntityAlias entityAlias) {
        return getCoreTransferCaches(userVisit).getEntityAliasTransferCache().getEntityAliasTransfer(entityAlias);
    }

    public List<EntityAliasTransfer> getEntityAliasTransfers(UserVisit userVisit, Collection<EntityAlias> entityAliases) {
        var entityAliasTransfers = new ArrayList<EntityAliasTransfer>(entityAliases.size());
        var entityAliasTransferCache = getCoreTransferCaches(userVisit).getEntityAliasTransferCache();

        entityAliases.forEach((entityAlias) ->
                entityAliasTransfers.add(entityAliasTransferCache.getEntityAliasTransfer(entityAlias))
        );

        return entityAliasTransfers;
    }

    public void updateEntityAliasFromValue(EntityAliasValue entityAliasValue, BasePK updatedBy) {
        if(entityAliasValue.hasBeenModified()) {
            var entityAlias = EntityAliasFactory.getInstance().getEntityFromValue(session, EntityPermission.READ_WRITE, entityAliasValue);
            var entityAliasType = entityAlias.getEntityAliasType();
            var entityInstance = entityAlias.getEntityInstance();

            entityAlias.setThruTime(session.START_TIME_LONG);
            entityAlias.store();

            EntityAliasFactory.getInstance().create(entityInstance, entityAliasType, entityAliasValue.getAlias(), session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            sendEvent(entityInstance, EventTypes.MODIFY, entityAliasType.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteEntityAlias(EntityAlias entityAlias, BasePK deletedBy) {
        var entityAliasType = entityAlias.getEntityAliasType();
        var entityInstance = entityAlias.getEntityInstance();

        entityAlias.setThruTime(session.START_TIME_LONG);

        sendEvent(entityInstance, EventTypes.MODIFY, entityAliasType.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteEntityAliases(List<EntityAlias> entityAliases, BasePK deletedBy) {
        entityAliases.forEach((entityAlias) ->
                deleteEntityAlias(entityAlias, deletedBy)
        );
    }

    public void deleteEntityAliasesByEntityAliasType(EntityAliasType entityAliasType, BasePK deletedBy) {
        deleteEntityAliases(getEntityAliasesByEntityAliasTypeForUpdate(entityAliasType), deletedBy);
    }

    public void deleteEntityAliasesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteEntityAliases(getEntityAliasesByEntityInstanceForUpdate(entityInstance), deletedBy);
    }

    public EntityAlias getEntityAliasByEntityAliasTypeAndAlias(EntityAliasType entityAliasType, String alias) {
        EntityAlias entityAlias;

        try {
            var ps = EntityAliasFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entityaliases " +
                    "WHERE enial_eniat_entityaliastypeid = ? AND enial_alias = ? AND enial_thrutime = ?");

            ps.setLong(1, entityAliasType.getPrimaryKey().getEntityId());
            ps.setString(2, alias);
            ps.setLong(3, Session.MAX_TIME);

            entityAlias = EntityAliasFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityAlias;
    }

    // --------------------------------------------------------------------------------
    //   Entity Attribute Groups
    // --------------------------------------------------------------------------------
    
    public EntityAttributeGroup createEntityAttributeGroup(String entityAttributeGroupName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultEntityAttributeGroup = getDefaultEntityAttributeGroup();
        var defaultFound = defaultEntityAttributeGroup != null;
        
        if(defaultFound && isDefault) {
            var defaultEntityAttributeGroupDetailValue = getDefaultEntityAttributeGroupDetailValueForUpdate();
            
            defaultEntityAttributeGroupDetailValue.setIsDefault(Boolean.FALSE);
            updateEntityAttributeGroupFromValue(defaultEntityAttributeGroupDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        var entityAttributeGroup = EntityAttributeGroupFactory.getInstance().create();
        var entityAttributeGroupDetail = EntityAttributeGroupDetailFactory.getInstance().create(entityAttributeGroup,
                entityAttributeGroupName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        entityAttributeGroup = EntityAttributeGroupFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                entityAttributeGroup.getPrimaryKey());
        entityAttributeGroup.setActiveDetail(entityAttributeGroupDetail);
        entityAttributeGroup.setLastDetail(entityAttributeGroupDetail);
        entityAttributeGroup.store();
        
        sendEvent(entityAttributeGroup.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return entityAttributeGroup;
    }
    
    /** Assume that the entityInstance passed to this function is a ECHO_THREE.EntityAttributeGroup */
    public EntityAttributeGroup getEntityAttributeGroupByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new EntityAttributeGroupPK(entityInstance.getEntityUniqueId());

        return EntityAttributeGroupFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public EntityAttributeGroup getEntityAttributeGroupByEntityInstance(EntityInstance entityInstance) {
        return getEntityAttributeGroupByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public EntityAttributeGroup getEntityAttributeGroupByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getEntityAttributeGroupByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countEntityAttributeGroups() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM entityattributegroups, entityattributegroupdetails " +
                "WHERE enagp_activedetailid = enagpdt_entityattributegroupdetailid");
    }

    public long countEntityAttributeGroupsByEntityType(EntityType entityType) {
        var count = session.queryForLong("""
                                SELECT COUNT(*)
                                FROM (
                                    SELECT COUNT(*)
                                    FROM entityattributegroups
                                    JOIN entityattributegroupdetails ON enagp_lastdetailid = enagpdt_entityattributegroupdetailid
                                    JOIN entityattributeentityattributegroups ON enagp_entityattributegroupid = enaenagp_enagp_entityattributegroupid AND enaenagp_thrutime = ?
                                    JOIN entityattributes ON enaenagp_ena_entityattributeid = ena_entityattributeid
                                    JOIN entityattributedetails ON ena_lastdetailid = enadt_entityattributedetailid
                                    WHERE enadt_ent_entitytypeid = ?
                                    GROUP BY enagp_entityattributegroupid
                                ) AS entityattributegroups
                                """, Session.MAX_TIME, entityType);

        return count == null ? 0L : count;
    }

    private List<EntityAttributeGroup> getEntityAttributeGroups(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM entityattributegroups, entityattributegroupdetails " +
                    "WHERE enagp_activedetailid = enagpdt_entityattributegroupdetailid " +
                    "ORDER BY enagpdt_sortorder, enagpdt_entityattributegroupname " +
                    "_LIMIT_";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM entityattributegroups, entityattributegroupdetails " +
                    "WHERE enagp_activedetailid = enagpdt_entityattributegroupdetailid " +
                    "FOR UPDATE";
        }

        var ps = EntityAttributeGroupFactory.getInstance().prepareStatement(query);
        
        return EntityAttributeGroupFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<EntityAttributeGroup> getEntityAttributeGroups() {
        return getEntityAttributeGroups(EntityPermission.READ_ONLY);
    }
    
    public List<EntityAttributeGroup> getEntityAttributeGroupsForUpdate() {
        return getEntityAttributeGroups(EntityPermission.READ_WRITE);
    }
    
    private List<EntityAttributeGroup> getEntityAttributeGroupsByEntityType(EntityType entityType, EntityPermission entityPermission) {
        List<EntityAttributeGroup> entityAttributeGroups;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ "
                        + "FROM entityattributegroups, entityattributegroupdetails, entityattributeentityattributegroups, entityattributes, entityattributedetails "
                        + "WHERE enagp_lastdetailid = enagpdt_entityattributegroupdetailid "
                        + "AND enagp_entityattributegroupid = enaenagp_enagp_entityattributegroupid AND enaenagp_ena_entityattributeid = ena_entityattributeid AND enaenagp_thrutime = ? "
                        + "AND ena_lastdetailid = enadt_entityattributedetailid AND enadt_ent_entitytypeid = ? "
                        + "GROUP BY enagp_entityattributegroupid "
                        + "ORDER BY enagpdt_sortorder, enagpdt_entityattributegroupname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ "
                        + "FROM entityattributegroups, entityattributegroupdetails, entityattributeentityattributegroups, entityattributes, entityattributedetails "
                        + "WHERE enagp_lastdetailid = enagpdt_entityattributegroupdetailid "
                        + "AND enagp_entityattributegroupid = enaenagp_enagp_entityattributegroupid AND enaenagp_ena_entityattributeid = ena_entityattributeid AND enaenagp_thrutime = ? "
                        + "AND ena_lastdetailid = enadt_entityattributedetailid AND enadt_ent_entitytypeid = ? "
                        + "GROUP BY enagp_entityattributegroupid "
                        + "FOR UPDATE";
            }

            var ps = EntityAttributeGroupFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, Session.MAX_TIME);
            ps.setLong(2, entityType.getPrimaryKey().getEntityId());
            
            entityAttributeGroups = EntityAttributeGroupFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityAttributeGroups;
    }
    
    public List<EntityAttributeGroup> getEntityAttributeGroupsByEntityType(EntityType entityType) {
        return getEntityAttributeGroupsByEntityType(entityType, EntityPermission.READ_ONLY);
    }
    
    public List<EntityAttributeGroup> getEntityAttributeGroupsByEntityTypeForUpdate(EntityType entityType) {
        return getEntityAttributeGroupsByEntityType(entityType, EntityPermission.READ_WRITE);
    }
    
    private EntityAttributeGroup getDefaultEntityAttributeGroup(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM entityattributegroups, entityattributegroupdetails " +
                    "WHERE enagp_activedetailid = enagpdt_entityattributegroupdetailid AND enagpdt_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM entityattributegroups, entityattributegroupdetails " +
                    "WHERE enagp_activedetailid = enagpdt_entityattributegroupdetailid AND enagpdt_isdefault = 1 " +
                    "FOR UPDATE";
        }

        var ps = EntityAttributeGroupFactory.getInstance().prepareStatement(query);
        
        return EntityAttributeGroupFactory.getInstance().getEntityFromQuery(entityPermission, ps);
    }
    
    public EntityAttributeGroup getDefaultEntityAttributeGroup() {
        return getDefaultEntityAttributeGroup(EntityPermission.READ_ONLY);
    }
    
    public EntityAttributeGroup getDefaultEntityAttributeGroupForUpdate() {
        return getDefaultEntityAttributeGroup(EntityPermission.READ_WRITE);
    }
    
    public EntityAttributeGroupDetailValue getDefaultEntityAttributeGroupDetailValueForUpdate() {
        return getDefaultEntityAttributeGroupForUpdate().getLastDetailForUpdate().getEntityAttributeGroupDetailValue().clone();
    }
    
    public EntityAttributeGroup getEntityAttributeGroupByName(String entityAttributeGroupName, EntityPermission entityPermission) {
        EntityAttributeGroup entityAttributeGroup;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityattributegroups, entityattributegroupdetails " +
                        "WHERE enagp_activedetailid = enagpdt_entityattributegroupdetailid AND enagpdt_entityattributegroupname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityattributegroups, entityattributegroupdetails " +
                        "WHERE enagp_activedetailid = enagpdt_entityattributegroupdetailid AND enagpdt_entityattributegroupname = ? " +
                        "FOR UPDATE";
            }

            var ps = EntityAttributeGroupFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, entityAttributeGroupName);
            
            entityAttributeGroup = EntityAttributeGroupFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityAttributeGroup;
    }
    
    public EntityAttributeGroup getEntityAttributeGroupByName(String entityAttributeGroupName) {
        return getEntityAttributeGroupByName(entityAttributeGroupName, EntityPermission.READ_ONLY);
    }
    
    public EntityAttributeGroup getEntityAttributeGroupByNameForUpdate(String entityAttributeGroupName) {
        return getEntityAttributeGroupByName(entityAttributeGroupName, EntityPermission.READ_WRITE);
    }
    
    public EntityAttributeGroupDetailValue getEntityAttributeGroupDetailValueForUpdate(EntityAttributeGroup entityAttributeGroup) {
        return entityAttributeGroup == null? null: entityAttributeGroup.getLastDetailForUpdate().getEntityAttributeGroupDetailValue().clone();
    }
    
    public EntityAttributeGroupDetailValue getEntityAttributeGroupDetailValueByNameForUpdate(String entityAttributeGroupName) {
        return getEntityAttributeGroupDetailValueForUpdate(getEntityAttributeGroupByNameForUpdate(entityAttributeGroupName));
    }
    
    public EntityAttributeGroupChoicesBean getEntityAttributeGroupChoices(String defaultEntityAttributeGroupChoice, Language language,
            boolean allowNullChoice) {
        var entityAttributeGroups = getEntityAttributeGroups();
        var size = entityAttributeGroups.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultEntityAttributeGroupChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var entityAttributeGroup : entityAttributeGroups) {
            var entityAttributeGroupDetail = entityAttributeGroup.getLastDetail();
            var label = getBestEntityAttributeGroupDescription(entityAttributeGroup, language);
            var value = entityAttributeGroupDetail.getEntityAttributeGroupName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultEntityAttributeGroupChoice != null && defaultEntityAttributeGroupChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && entityAttributeGroupDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new EntityAttributeGroupChoicesBean(labels, values, defaultValue);
    }
    
    public EntityAttributeGroupTransfer getEntityAttributeGroupTransfer(UserVisit userVisit, EntityAttributeGroup entityAttributeGroup, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityAttributeGroupTransferCache().getEntityAttributeGroupTransfer(entityAttributeGroup, entityInstance);
    }
    
    public List<EntityAttributeGroupTransfer> getEntityAttributeGroupTransfers(UserVisit userVisit, Collection<EntityAttributeGroup> entityAttributeGroups, EntityInstance entityInstance) {
        List<EntityAttributeGroupTransfer> entityAttributeGroupTransfers = new ArrayList<>(entityAttributeGroups.size());
        var entityAttributeGroupTransferCache = getCoreTransferCaches(userVisit).getEntityAttributeGroupTransferCache();
        
        entityAttributeGroups.forEach((entityAttributeGroup) ->
                entityAttributeGroupTransfers.add(entityAttributeGroupTransferCache.getEntityAttributeGroupTransfer(entityAttributeGroup, entityInstance))
        );
        
        return entityAttributeGroupTransfers;
    }
    
    public List<EntityAttributeGroupTransfer> getEntityAttributeGroupTransfers(UserVisit userVisit, EntityInstance entityInstance) {
        return getEntityAttributeGroupTransfers(userVisit, getEntityAttributeGroups(), entityInstance);
    }
    
    public List<EntityAttributeGroupTransfer> getEntityAttributeGroupTransfersByEntityType(UserVisit userVisit, EntityType entityType, EntityInstance entityInstance) {
        return getEntityAttributeGroupTransfers(userVisit, getEntityAttributeGroupsByEntityType(entityType), entityInstance);
    }
    
    private void updateEntityAttributeGroupFromValue(EntityAttributeGroupDetailValue entityAttributeGroupDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(entityAttributeGroupDetailValue.hasBeenModified()) {
            var entityAttributeGroup = EntityAttributeGroupFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     entityAttributeGroupDetailValue.getEntityAttributeGroupPK());
            var entityAttributeGroupDetail = entityAttributeGroup.getActiveDetailForUpdate();
            
            entityAttributeGroupDetail.setThruTime(session.START_TIME_LONG);
            entityAttributeGroupDetail.store();

            var entityAttributeGroupPK = entityAttributeGroupDetail.getEntityAttributeGroupPK();
            var entityAttributeGroupName = entityAttributeGroupDetailValue.getEntityAttributeGroupName();
            var isDefault = entityAttributeGroupDetailValue.getIsDefault();
            var sortOrder = entityAttributeGroupDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultEntityAttributeGroup = getDefaultEntityAttributeGroup();
                var defaultFound = defaultEntityAttributeGroup != null && !defaultEntityAttributeGroup.equals(entityAttributeGroup);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultEntityAttributeGroupDetailValue = getDefaultEntityAttributeGroupDetailValueForUpdate();
                    
                    defaultEntityAttributeGroupDetailValue.setIsDefault(Boolean.FALSE);
                    updateEntityAttributeGroupFromValue(defaultEntityAttributeGroupDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            entityAttributeGroupDetail = EntityAttributeGroupDetailFactory.getInstance().create(entityAttributeGroupPK, entityAttributeGroupName,
                    isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            entityAttributeGroup.setActiveDetail(entityAttributeGroupDetail);
            entityAttributeGroup.setLastDetail(entityAttributeGroupDetail);
            
            sendEvent(entityAttributeGroupPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateEntityAttributeGroupFromValue(EntityAttributeGroupDetailValue entityAttributeGroupDetailValue, BasePK updatedBy) {
        updateEntityAttributeGroupFromValue(entityAttributeGroupDetailValue, true, updatedBy);
    }

    public EntityAttributeGroup getEntityAttributeGroupByPK(EntityAttributeGroupPK pk) {
        return EntityAttributeGroupFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);
    }

    public void deleteEntityAttributeGroup(EntityAttributeGroup entityAttributeGroup, BasePK deletedBy) {
        deleteEntityAttributeEntityAttributeGroupsByEntityAttributeGroup(entityAttributeGroup, deletedBy);
        deleteEntityAttributeGroupDescriptionsByEntityAttributeGroup(entityAttributeGroup, deletedBy);

        var entityAttributeGroupDetail = entityAttributeGroup.getLastDetailForUpdate();
        entityAttributeGroupDetail.setThruTime(session.START_TIME_LONG);
        entityAttributeGroup.setActiveDetail(null);
        entityAttributeGroup.store();
        
        // Check for default, and pick one if necessary
        var defaultEntityAttributeGroup = getDefaultEntityAttributeGroup();
        if(defaultEntityAttributeGroup == null) {
            var entityAttributeGroups = getEntityAttributeGroupsForUpdate();
            
            if(!entityAttributeGroups.isEmpty()) {
                var iter = entityAttributeGroups.iterator();
                if(iter.hasNext()) {
                    defaultEntityAttributeGroup = iter.next();
                }
                var entityAttributeGroupDetailValue = Objects.requireNonNull(defaultEntityAttributeGroup).getLastDetailForUpdate().getEntityAttributeGroupDetailValue().clone();
                
                entityAttributeGroupDetailValue.setIsDefault(Boolean.TRUE);
                updateEntityAttributeGroupFromValue(entityAttributeGroupDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(entityAttributeGroup.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Attribute Group Descriptions
    // --------------------------------------------------------------------------------
    
    public EntityAttributeGroupDescription createEntityAttributeGroupDescription(EntityAttributeGroup entityAttributeGroup, Language language, String description,
            BasePK createdBy) {
        var entityAttributeGroupDescription = EntityAttributeGroupDescriptionFactory.getInstance().create(entityAttributeGroup,
                language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(entityAttributeGroup.getPrimaryKey(), EventTypes.MODIFY, entityAttributeGroupDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return entityAttributeGroupDescription;
    }
    
    private EntityAttributeGroupDescription getEntityAttributeGroupDescription(EntityAttributeGroup entityAttributeGroup, Language language, EntityPermission entityPermission) {
        EntityAttributeGroupDescription entityAttributeGroupDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityattributegroupdescriptions " +
                        "WHERE enagpd_enagp_entityattributegroupid = ? AND enagpd_lang_languageid = ? AND enagpd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityattributegroupdescriptions " +
                        "WHERE enagpd_enagp_entityattributegroupid = ? AND enagpd_lang_languageid = ? AND enagpd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = EntityAttributeGroupDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttributeGroup.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityAttributeGroupDescription = EntityAttributeGroupDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityAttributeGroupDescription;
    }
    
    public EntityAttributeGroupDescription getEntityAttributeGroupDescription(EntityAttributeGroup entityAttributeGroup, Language language) {
        return getEntityAttributeGroupDescription(entityAttributeGroup, language, EntityPermission.READ_ONLY);
    }
    
    public EntityAttributeGroupDescription getEntityAttributeGroupDescriptionForUpdate(EntityAttributeGroup entityAttributeGroup, Language language) {
        return getEntityAttributeGroupDescription(entityAttributeGroup, language, EntityPermission.READ_WRITE);
    }
    
    public EntityAttributeGroupDescriptionValue getEntityAttributeGroupDescriptionValue(EntityAttributeGroupDescription entityAttributeGroupDescription) {
        return entityAttributeGroupDescription == null? null: entityAttributeGroupDescription.getEntityAttributeGroupDescriptionValue().clone();
    }
    
    public EntityAttributeGroupDescriptionValue getEntityAttributeGroupDescriptionValueForUpdate(EntityAttributeGroup entityAttributeGroup, Language language) {
        return getEntityAttributeGroupDescriptionValue(getEntityAttributeGroupDescriptionForUpdate(entityAttributeGroup, language));
    }
    
    private List<EntityAttributeGroupDescription> getEntityAttributeGroupDescriptionsByEntityAttributeGroup(EntityAttributeGroup entityAttributeGroup, EntityPermission entityPermission) {
        List<EntityAttributeGroupDescription> entityAttributeGroupDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityattributegroupdescriptions, languages " +
                        "WHERE enagpd_enagp_entityattributegroupid = ? AND enagpd_thrutime = ? " +
                        "AND enagpd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityattributegroupdescriptions " +
                        "WHERE enagpd_enagp_entityattributegroupid = ? AND enagpd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = EntityAttributeGroupDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttributeGroup.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityAttributeGroupDescriptions = EntityAttributeGroupDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityAttributeGroupDescriptions;
    }
    
    public List<EntityAttributeGroupDescription> getEntityAttributeGroupDescriptionsByEntityAttributeGroup(EntityAttributeGroup entityAttributeGroup) {
        return getEntityAttributeGroupDescriptionsByEntityAttributeGroup(entityAttributeGroup, EntityPermission.READ_ONLY);
    }
    
    public List<EntityAttributeGroupDescription> getEntityAttributeGroupDescriptionsByEntityAttributeGroupForUpdate(EntityAttributeGroup entityAttributeGroup) {
        return getEntityAttributeGroupDescriptionsByEntityAttributeGroup(entityAttributeGroup, EntityPermission.READ_WRITE);
    }
    
    public String getBestEntityAttributeGroupDescription(EntityAttributeGroup entityAttributeGroup, Language language) {
        String description;
        var entityAttributeGroupDescription = getEntityAttributeGroupDescription(entityAttributeGroup, language);
        
        if(entityAttributeGroupDescription == null && !language.getIsDefault()) {
            entityAttributeGroupDescription = getEntityAttributeGroupDescription(entityAttributeGroup, getPartyControl().getDefaultLanguage());
        }
        
        if(entityAttributeGroupDescription == null) {
            description = entityAttributeGroup.getLastDetail().getEntityAttributeGroupName();
        } else {
            description = entityAttributeGroupDescription.getDescription();
        }
        
        return description;
    }
    
    public EntityAttributeGroupDescriptionTransfer getEntityAttributeGroupDescriptionTransfer(UserVisit userVisit, EntityAttributeGroupDescription entityAttributeGroupDescription, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityAttributeGroupDescriptionTransferCache().getEntityAttributeGroupDescriptionTransfer(entityAttributeGroupDescription, entityInstance);
    }
    
    public List<EntityAttributeGroupDescriptionTransfer> getEntityAttributeGroupDescriptionTransfers(UserVisit userVisit, EntityAttributeGroup entityAttributeGroup, EntityInstance entityInstance) {
        var entityAttributeGroupDescriptions = getEntityAttributeGroupDescriptionsByEntityAttributeGroup(entityAttributeGroup);
        List<EntityAttributeGroupDescriptionTransfer> entityAttributeGroupDescriptionTransfers = new ArrayList<>(entityAttributeGroupDescriptions.size());
        var entityAttributeGroupDescriptionTransferCache = getCoreTransferCaches(userVisit).getEntityAttributeGroupDescriptionTransferCache();
        
        entityAttributeGroupDescriptions.forEach((entityAttributeGroupDescription) ->
                entityAttributeGroupDescriptionTransfers.add(entityAttributeGroupDescriptionTransferCache.getEntityAttributeGroupDescriptionTransfer(entityAttributeGroupDescription, entityInstance))
        );
        
        return entityAttributeGroupDescriptionTransfers;
    }
    
    public void updateEntityAttributeGroupDescriptionFromValue(EntityAttributeGroupDescriptionValue entityAttributeGroupDescriptionValue, BasePK updatedBy) {
        if(entityAttributeGroupDescriptionValue.hasBeenModified()) {
            var entityAttributeGroupDescription = EntityAttributeGroupDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     entityAttributeGroupDescriptionValue.getPrimaryKey());
            
            entityAttributeGroupDescription.setThruTime(session.START_TIME_LONG);
            entityAttributeGroupDescription.store();

            var entityAttributeGroup = entityAttributeGroupDescription.getEntityAttributeGroup();
            var language = entityAttributeGroupDescription.getLanguage();
            var description = entityAttributeGroupDescriptionValue.getDescription();
            
            entityAttributeGroupDescription = EntityAttributeGroupDescriptionFactory.getInstance().create(entityAttributeGroup, language,
                    description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(entityAttributeGroup.getPrimaryKey(), EventTypes.MODIFY, entityAttributeGroupDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityAttributeGroupDescription(EntityAttributeGroupDescription entityAttributeGroupDescription, BasePK deletedBy) {
        entityAttributeGroupDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(entityAttributeGroupDescription.getEntityAttributeGroupPK(), EventTypes.MODIFY, entityAttributeGroupDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteEntityAttributeGroupDescriptionsByEntityAttributeGroup(EntityAttributeGroup entityAttributeGroup, BasePK deletedBy) {
        var entityAttributeGroupDescriptions = getEntityAttributeGroupDescriptionsByEntityAttributeGroupForUpdate(entityAttributeGroup);
        
        entityAttributeGroupDescriptions.forEach((entityAttributeGroupDescription) -> 
                deleteEntityAttributeGroupDescription(entityAttributeGroupDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Attributes
    // --------------------------------------------------------------------------------
    
    public EntityAttribute createEntityAttribute(EntityType entityType, String entityAttributeName,
            EntityAttributeType entityAttributeType, Boolean trackRevisions, Integer sortOrder, BasePK createdBy) {
        var entityAttribute = EntityAttributeFactory.getInstance().create();
        var entityAttributeDetail = EntityAttributeDetailFactory.getInstance().create(entityAttribute, entityType,
                entityAttributeName, entityAttributeType, trackRevisions, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        entityAttribute = EntityAttributeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                entityAttribute.getPrimaryKey());
        entityAttribute.setActiveDetail(entityAttributeDetail);
        entityAttribute.setLastDetail(entityAttributeDetail);
        entityAttribute.store();
        
        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return entityAttribute;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.EntityAttribute */
    public EntityAttribute getEntityAttributeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new EntityAttributePK(entityInstance.getEntityUniqueId());

        return EntityAttributeFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public EntityAttribute getEntityAttributeByEntityInstance(EntityInstance entityInstance) {
        return getEntityAttributeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public EntityAttribute getEntityAttributeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getEntityAttributeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    public EntityAttribute getEntityAttributeByPK(EntityAttributePK pk) {
        return EntityAttributeFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);
    }

    public long countEntityAttributesByEntityType(EntityType entityType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM entityattributes, entityattributedetails " +
                "WHERE ena_activedetailid = enadt_entityattributedetailid AND enadt_ent_entitytypeid = ?",
                entityType);
    }

    public long countEntityAttributesByEntityTypeAndEntityAttributeType(EntityType entityType, EntityAttributeType entityAttributeType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM entityattributes, entityattributedetails " +
                "WHERE ena_activedetailid = enadt_entityattributedetailid " +
                "AND enadt_ent_entitytypeid = ? AND enadt_enat_entityattributetypeid = ?",
                entityType, entityAttributeType);
    }

    public long countEntityAttributesByEntityAttributeGroupAndEntityType(EntityAttributeGroup entityAttributeGroup, EntityType entityType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM entityattributeentityattributegroups, entityattributes, entityattributedetails " +
                        "WHERE enaenagp_enagp_entityattributegroupid = ? AND enaenagp_thrutime = ? " +
                        "AND enaenagp_ena_entityattributeid = ena_entityattributeid " +
                        "AND ena_lastdetailid = enadt_entityattributedetailid AND enadt_ent_entitytypeid = ?",
                entityAttributeGroup, Session.MAX_TIME, entityType);
    }

    public long countEntityAttributesByEntityTypeAndWorkflow(final EntityType entityType, final Workflow workflow) {
        return session.queryForLong("""
                        SELECT COUNT(*)
                        FROM entityattributes
                        JOIN entityattributedetails ON ena_activedetailid = enadt_entityattributedetailid
                        JOIN entityattributetypes ON enadt_enat_entityattributetypeid = enat_entityattributetypeid
                        JOIN entityattributeworkflows ON ena_entityattributeid = enawkfl_ena_entityattributeid AND enawkfl_thrutime = ?
                        WHERE enadt_ent_entitytypeid = ? AND enat_entityattributetypename = ? AND enawkfl_wkfl_workflowid = ?
                        """, Session.MAX_TIME, entityType, EntityAttributeTypes.WORKFLOW.name(), workflow);
    }

    public EntityAttribute getEntityAttributeByName(EntityType entityType, String entityAttributeName, EntityPermission entityPermission) {
        EntityAttribute entityAttribute;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityattributes, entityattributedetails " +
                        "WHERE ena_activedetailid = enadt_entityattributedetailid " +
                        "AND enadt_ent_entitytypeid = ? AND enadt_entityattributename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityattributes, entityattributedetails " +
                        "WHERE ena_activedetailid = enadt_entityattributedetailid " +
                        "AND enadt_ent_entitytypeid = ? AND enadt_entityattributename = ? " +
                        "FOR UPDATE";
            }

            var ps = EntityAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityType.getPrimaryKey().getEntityId());
            ps.setString(2, entityAttributeName);
            
            entityAttribute = EntityAttributeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityAttribute;
    }
    
    public EntityAttribute getEntityAttributeByName(EntityType entityType, String entityAttributeName) {
        return getEntityAttributeByName(entityType, entityAttributeName, EntityPermission.READ_ONLY);
    }
    
    public EntityAttribute getEntityAttributeByNameForUpdate(EntityType entityType, String entityAttributeName) {
        return getEntityAttributeByName(entityType, entityAttributeName, EntityPermission.READ_WRITE);
    }
    
    public EntityAttributeDetailValue getEntityAttributeDetailValueForUpdate(EntityAttribute entityAttribute) {
        return entityAttribute == null? null: entityAttribute.getLastDetailForUpdate().getEntityAttributeDetailValue().clone();
    }
    
    public EntityAttributeDetailValue getEntityAttributeDetailValueByNameForUpdate(EntityType entityType, String entityAttributeName) {
        return getEntityAttributeDetailValueForUpdate(getEntityAttributeByNameForUpdate(entityType, entityAttributeName));
    }
    
    private List<EntityAttribute> getEntityAttributesByEntityType(EntityType entityType, EntityPermission entityPermission) {
        List<EntityAttribute> entityAttributes;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityattributes, entityattributedetails " +
                        "WHERE ena_activedetailid = enadt_entityattributedetailid " +
                        "AND enadt_ent_entitytypeid = ? " +
                        "ORDER BY enadt_sortorder, enadt_entityattributename " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityattributes, entityattributedetails " +
                        "WHERE ena_activedetailid = enadt_entityattributedetailid " +
                        "AND enadt_ent_entitytypeid = ? " +
                        "FOR UPDATE";
            }

            var ps = EntityAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityType.getPrimaryKey().getEntityId());
            
            entityAttributes = EntityAttributeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityAttributes;
    }
    
    public List<EntityAttribute> getEntityAttributesByEntityType(EntityType entityType) {
        return getEntityAttributesByEntityType(entityType, EntityPermission.READ_ONLY);
    }
    
    public List<EntityAttribute> getEntityAttributesByEntityTypeForUpdate(EntityType entityType) {
        return getEntityAttributesByEntityType(entityType, EntityPermission.READ_WRITE);
    }
    
    private List<EntityAttribute> getEntityAttributesByEntityTypeAndEntityAttributeType(EntityType entityType,
            EntityAttributeType entityAttributeType, EntityPermission entityPermission) {
        List<EntityAttribute> entityAttributes;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityattributes, entityattributedetails " +
                        "WHERE ena_activedetailid = enadt_entityattributedetailid " +
                        "AND enadt_ent_entitytypeid = ? AND enadt_enat_entityattributetypeid = ? " +
                        "ORDER BY enadt_sortorder, enadt_entityattributename " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityattributes, entityattributedetails " +
                        "WHERE ena_activedetailid = enadt_entityattributedetailid " +
                        "AND enadt_ent_entitytypeid = ? AND enadt_enat_entityattributetypeid = ? " +
                        "FOR UPDATE";
            }

            var ps = EntityAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityType.getPrimaryKey().getEntityId());
            ps.setLong(2, entityAttributeType.getPrimaryKey().getEntityId());
            
            entityAttributes = EntityAttributeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityAttributes;
    }
    
    public List<EntityAttribute> getEntityAttributesByEntityTypeAndEntityAttributeType(EntityType entityType,
            EntityAttributeType entityAttributeType) {
        return getEntityAttributesByEntityTypeAndEntityAttributeType(entityType, entityAttributeType, EntityPermission.READ_ONLY);
    }
    
    public List<EntityAttribute> getEntityAttributesByEntityTypeAndEntityAttributeTypeForUpdate(EntityType entityType,
            EntityAttributeType entityAttributeType) {
        return getEntityAttributesByEntityTypeAndEntityAttributeType(entityType, entityAttributeType, EntityPermission.READ_WRITE);
    }
    
    private List<EntityAttribute> getEntityAttributesByEntityAttributeGroupAndEntityType(EntityAttributeGroup entityAttributeGroup, EntityType entityType,
            EntityPermission entityPermission) {
        List<EntityAttribute> entityAttributes;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ "
                        + "FROM entityattributeentityattributegroups, entityattributes, entityattributedetails "
                        + "WHERE enaenagp_enagp_entityattributegroupid = ? AND enaenagp_thrutime = ? "
                        + "AND enaenagp_ena_entityattributeid = ena_entityattributeid "
                        + "AND ena_lastdetailid = enadt_entityattributedetailid AND enadt_ent_entitytypeid = ? "
                        + "ORDER BY enaenagp_sortorder, enadt_sortorder, enadt_entityattributename " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ "
                        + "FROM entityattributeentityattributegroups, entityattributes, entityattributedetails "
                        + "WHERE enaenagp_enagp_entityattributegroupid = ? AND enaenagp_thrutime = ? "
                        + "AND enaenagp_ena_entityattributeid = ena_entityattributeid "
                        + "AND ena_lastdetailid = enadt_entityattributedetailid AND enadt_ent_entitytypeid = ? "
                        + "FOR UPDATE";
            }

            var ps = EntityAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttributeGroup.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            ps.setLong(3, entityType.getPrimaryKey().getEntityId());
            
            entityAttributes = EntityAttributeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityAttributes;
    }
    
    public List<EntityAttribute> getEntityAttributesByEntityAttributeGroupAndEntityType(EntityAttributeGroup entityAttributeGroup, EntityType entityType) {
        return getEntityAttributesByEntityAttributeGroupAndEntityType(entityAttributeGroup, entityType, EntityPermission.READ_ONLY);
    }
    
    public List<EntityAttribute> getEntityAttributesByEntityAttributeGroupAndEntityTypeForUpdate(EntityAttributeGroup entityAttributeGroup, EntityType entityType) {
        return getEntityAttributesByEntityAttributeGroupAndEntityType(entityAttributeGroup, entityType, EntityPermission.READ_WRITE);
    }
    
    public EntityAttributeTransfer getEntityAttributeTransfer(UserVisit userVisit, EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityAttributeTransferCache().getEntityAttributeTransfer(entityAttribute, entityInstance);
    }
    
    public List<EntityAttributeTransfer> getEntityAttributeTransfers(UserVisit userVisit, Collection<EntityAttribute> entityAttributes, EntityInstance entityInstance) {
        List<EntityAttributeTransfer> entityAttributeTransfers = new ArrayList<>(entityAttributes.size());
        var entityAttributeTransferCache = getCoreTransferCaches(userVisit).getEntityAttributeTransferCache();
        
        entityAttributes.forEach((entityAttribute) ->
                entityAttributeTransfers.add(entityAttributeTransferCache.getEntityAttributeTransfer(entityAttribute, entityInstance))
        );
        
        return entityAttributeTransfers;
    }
    
    public List<EntityAttributeTransfer> getEntityAttributeTransfersByEntityType(UserVisit userVisit, EntityType entityType, EntityInstance entityInstance) {
        return getEntityAttributeTransfers(userVisit, getEntityAttributesByEntityType(entityType), entityInstance);
    }
    
    public List<EntityAttributeTransfer> getEntityAttributeTransfersByEntityTypeAndEntityAttributeType(UserVisit userVisit, EntityType entityType,
            EntityAttributeType entityAttributeType, EntityInstance entityInstance) {
        return getEntityAttributeTransfers(userVisit, getEntityAttributesByEntityTypeAndEntityAttributeType(entityType, entityAttributeType), entityInstance);
    }
    
    public List<EntityAttributeTransfer> getEntityAttributeTransfersByEntityAttributeGroupAndEntityType(UserVisit userVisit,
            EntityAttributeGroup entityAttributeGroup, EntityType entityType, EntityInstance entityInstance) {
        return getEntityAttributeTransfers(userVisit, getEntityAttributesByEntityAttributeGroupAndEntityType(entityAttributeGroup, entityType),
                entityInstance);
    }
    
    public void updateEntityAttributeFromValue(final EntityAttributeDetailValue entityAttributeDetailValue, final BasePK updatedBy) {
        if(entityAttributeDetailValue.hasBeenModified()) {
            final var entityAttribute = EntityAttributeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     entityAttributeDetailValue.getEntityAttributePK());
            var entityAttributeDetail = entityAttribute.getActiveDetailForUpdate();
            
            entityAttributeDetail.setThruTime(session.START_TIME_LONG);
            entityAttributeDetail.store();

            final var entityAttributePK = entityAttributeDetail.getEntityAttributePK(); // Not updated
            final var entityTypePK = entityAttributeDetail.getEntityTypePK(); // Not updated
            final var entityAttributeName = entityAttributeDetailValue.getEntityAttributeName();
            final var entityAttributeTypePK = entityAttributeDetail.getEntityAttributeTypePK(); // Not updated
            final var trackRevisions = entityAttributeDetailValue.getTrackRevisions();
            final var sortOrder = entityAttributeDetailValue.getSortOrder();
            
            entityAttributeDetail = EntityAttributeDetailFactory.getInstance().create(entityAttributePK, entityTypePK,
                    entityAttributeName, entityAttributeTypePK, trackRevisions, sortOrder, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            entityAttribute.setActiveDetail(entityAttributeDetail);
            entityAttribute.setLastDetail(entityAttributeDetail);
            
            sendEvent(entityAttributePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deleteEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        var entityAttributeDetail = entityAttribute.getLastDetailForUpdate();
        var entityAttributeTypeName = entityAttributeDetail.getEntityAttributeType().getEntityAttributeTypeName();
        var entityAttributeType = EntityAttributeTypes.valueOf(entityAttributeTypeName);

        switch(entityAttributeType) {
            case BOOLEAN -> {
                deleteEntityBooleanDefaultByEntityAttribute(entityAttribute, deletedBy);
                deleteEntityBooleanAttributesByEntityAttribute(entityAttribute, deletedBy);
            }
            case NAME -> deleteEntityNameAttributesByEntityAttribute(entityAttribute, deletedBy);
            case INTEGER, LONG -> {
                deleteEntityAttributeNumericByEntityAttribute(entityAttribute, deletedBy);

                switch(entityAttributeType) {
                    case INTEGER:
                        deleteEntityAttributeIntegerByEntityAttribute(entityAttribute, deletedBy);
                        deleteEntityIntegerRangesByEntityAttribute(entityAttribute, deletedBy);
                        deleteEntityIntegerDefaultByEntityAttribute(entityAttribute, deletedBy);
                        deleteEntityIntegerAttributesByEntityAttribute(entityAttribute, deletedBy);
                        break;
                    case LONG:
                        deleteEntityAttributeLongByEntityAttribute(entityAttribute, deletedBy);
                        deleteEntityLongRangesByEntityAttribute(entityAttribute, deletedBy);
                        deleteEntityLongDefaultByEntityAttribute(entityAttribute, deletedBy);
                        deleteEntityLongAttributesByEntityAttribute(entityAttribute, deletedBy);
                        break;
                    default:
                        break;
                }
            }
            case STRING -> {
                deleteEntityAttributeStringByEntityAttribute(entityAttribute, deletedBy);
                deleteEntityStringDefaultByEntityAttribute(entityAttribute, deletedBy);
                deleteEntityStringAttributesByEntityAttribute(entityAttribute, deletedBy);
            }
            case GEOPOINT -> {
                deleteEntityGeoPointDefaultByEntityAttribute(entityAttribute, deletedBy);
                deleteEntityGeoPointAttributesByEntityAttribute(entityAttribute, deletedBy);
            }
            case BLOB -> {
                deleteEntityAttributeBlobByEntityAttribute(entityAttribute, deletedBy);
                deleteEntityBlobAttributesByEntityAttribute(entityAttribute, deletedBy);
            }
            case CLOB -> deleteEntityClobAttributesByEntityAttribute(entityAttribute, deletedBy);
            case DATE -> {
                deleteEntityDateDefaultByEntityAttribute(entityAttribute, deletedBy);
                deleteEntityDateAttributesByEntityAttribute(entityAttribute, deletedBy);
            }
            case TIME -> {
                deleteEntityTimeDefaultByEntityAttribute(entityAttribute, deletedBy);
                deleteEntityTimeAttributesByEntityAttribute(entityAttribute, deletedBy);
            }
            case LISTITEM, MULTIPLELISTITEM -> {
                deleteEntityAttributeListItemByEntityAttribute(entityAttribute, deletedBy);
                deleteEntityListItemsByEntityAttribute(entityAttribute, deletedBy); // Default deletion handled here
            }
            case ENTITY, COLLECTION -> {
                deleteEntityAttributeEntityTypesByEntityAttribute(entityAttribute, deletedBy);

                switch(entityAttributeType) {
                    case ENTITY:
                        deleteEntityEntityAttributesByEntityAttribute(entityAttribute, deletedBy);
                        break;
                    case COLLECTION:
                        deleteEntityCollectionAttributesByEntityAttribute(entityAttribute, deletedBy);
                        break;
                    default:
                        break;
                }
            }
            case WORKFLOW -> {
                var workflowControl = Session.getModelController(WorkflowControl.class);
                var entityAttributeWorkflow = getEntityAttributeWorkflow(entityAttribute);

                workflowControl.deleteWorkflowEntityStatusesByWorkflowAndEntityType(entityAttributeWorkflow.getWorkflow(),
                        entityAttributeDetail.getEntityType(), deletedBy);
                deleteEntityAttributeWorkflowByEntityAttribute(entityAttribute, deletedBy);
            }
        }
        
        deleteEntityAttributeEntityAttributeGroupsByEntityAttribute(entityAttribute, deletedBy);
        deleteEntityAttributeDescriptionsByEntityAttribute(entityAttribute, deletedBy);
        
        if(entityAttribute.getEntityPermission().equals(EntityPermission.READ_ONLY)) {
            // Convert to R/W
            entityAttribute = EntityAttributeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, entityAttribute.getPrimaryKey());
        }
        
        entityAttributeDetail.setThruTime(session.START_TIME_LONG);
        entityAttribute.setActiveDetail(null);
        entityAttribute.store();
        
        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteEntityAttributesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        var entityAttributes = getEntityAttributesByEntityType(entityInstance.getEntityType());
        
        entityAttributes.stream().map((entityAttribute) -> entityAttribute.getLastDetailForUpdate().getEntityAttributeType().getEntityAttributeTypeName()).forEach((entityAttributeTypeName) -> {
            if(entityAttributeTypeName.equals(EntityAttributeTypes.BOOLEAN.name())) {
                deleteEntityBooleanAttributesByEntityInstance(entityInstance, deletedBy);
            } else if(entityAttributeTypeName.equals(EntityAttributeTypes.NAME.name())) {
                deleteEntityNameAttributesByEntityInstance(entityInstance, deletedBy);
            } else if(entityAttributeTypeName.equals(EntityAttributeTypes.INTEGER.name())) {
                deleteEntityIntegerAttributesByEntityInstance(entityInstance, deletedBy);
            } else if(entityAttributeTypeName.equals(EntityAttributeTypes.LONG.name())) {
                deleteEntityLongAttributesByEntityInstance(entityInstance, deletedBy);
            } else if(entityAttributeTypeName.equals(EntityAttributeTypes.STRING.name())) {
                deleteEntityStringAttributesByEntityInstance(entityInstance, deletedBy);
            } else if(entityAttributeTypeName.equals(EntityAttributeTypes.GEOPOINT.name())) {
                deleteEntityGeoPointAttributesByEntityInstance(entityInstance, deletedBy);
            } else if(entityAttributeTypeName.equals(EntityAttributeTypes.BLOB.name())) {
                deleteEntityBlobAttributesByEntityInstance(entityInstance, deletedBy);
            } else if(entityAttributeTypeName.equals(EntityAttributeTypes.CLOB.name())) {
                deleteEntityClobAttributesByEntityInstance(entityInstance, deletedBy);
            } else if(entityAttributeTypeName.equals(EntityAttributeTypes.ENTITY.name())) {
                deleteEntityEntityAttributesByEntityInstance(entityInstance, deletedBy);
            } else if(entityAttributeTypeName.equals(EntityAttributeTypes.COLLECTION.name())) {
                deleteEntityCollectionAttributesByEntityInstance(entityInstance, deletedBy);
            } else if(entityAttributeTypeName.equals(EntityAttributeTypes.DATE.name())) {
                deleteEntityDateAttributesByEntityInstance(entityInstance, deletedBy);
            } else if(entityAttributeTypeName.equals(EntityAttributeTypes.TIME.name())) {
                deleteEntityTimeAttributesByEntityInstance(entityInstance, deletedBy);
            } else if(entityAttributeTypeName.equals(EntityAttributeTypes.LISTITEM.name())) {
                deleteEntityListItemAttributesByEntityInstance(entityInstance, deletedBy);
            } else if(entityAttributeTypeName.equals(EntityAttributeTypes.MULTIPLELISTITEM.name())) {
                deleteEntityMultipleListItemAttributesByEntityInstance(entityInstance, deletedBy);
            }
        });
    }
    
    public void deleteEntityAttributes(List<EntityAttribute> entityAttributes, BasePK deletedBy) {
        entityAttributes.forEach((entityAttribute) -> 
                deleteEntityAttribute(entityAttribute, deletedBy)
        );
    }
    
    public void deleteEntityAttributesByEntityType(EntityType entityType, BasePK deletedBy) {
        deleteEntityAttributes(getEntityAttributesByEntityTypeForUpdate(entityType), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Attribute Descriptions
    // --------------------------------------------------------------------------------
    
    public EntityAttributeDescription createEntityAttributeDescription(EntityAttribute entityAttribute, Language language,
            String description, BasePK createdBy) {
        var entityAttributeDescription = EntityAttributeDescriptionFactory.getInstance().create(session,
                entityAttribute, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return entityAttributeDescription;
    }
    
    private EntityAttributeDescription getEntityAttributeDescription(EntityAttribute entityAttribute, Language language,
            EntityPermission entityPermission) {
        EntityAttributeDescription entityAttributeDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityattributedescriptions " +
                        "WHERE enad_ena_entityattributeid = ? AND enad_lang_languageid = ? AND enad_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityattributedescriptions " +
                        "WHERE enad_ena_entityattributeid = ? AND enad_lang_languageid = ? AND enad_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = EntityAttributeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityAttributeDescription = EntityAttributeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityAttributeDescription;
    }
    
    public EntityAttributeDescription getEntityAttributeDescription(EntityAttribute entityAttribute, Language language) {
        return getEntityAttributeDescription(entityAttribute, language, EntityPermission.READ_ONLY);
    }
    
    public EntityAttributeDescription getEntityAttributeDescriptionForUpdate(EntityAttribute entityAttribute, Language language) {
        return getEntityAttributeDescription(entityAttribute, language, EntityPermission.READ_WRITE);
    }
    
    public EntityAttributeDescriptionValue getEntityAttributeDescriptionValue(EntityAttributeDescription entityAttributeDescription) {
        return entityAttributeDescription == null? null: entityAttributeDescription.getEntityAttributeDescriptionValue().clone();
    }
    
    public EntityAttributeDescriptionValue getEntityAttributeDescriptionValueForUpdate(EntityAttribute entityAttribute, Language language) {
        return getEntityAttributeDescriptionValue(getEntityAttributeDescriptionForUpdate(entityAttribute, language));
    }
    
    private List<EntityAttributeDescription> getEntityAttributeDescriptionsByEntityAttribute(EntityAttribute entityAttribute,
            EntityPermission entityPermission) {
        List<EntityAttributeDescription> entityAttributeDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityattributedescriptions, languages " +
                        "WHERE enad_ena_entityattributeid = ? AND enad_thrutime = ? AND enad_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityattributedescriptions " +
                        "WHERE enad_ena_entityattributeid = ? AND enad_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = EntityAttributeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityAttributeDescriptions = EntityAttributeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityAttributeDescriptions;
    }
    
    public List<EntityAttributeDescription> getEntityAttributeDescriptionsByEntityAttribute(EntityAttribute entityAttribute) {
        return getEntityAttributeDescriptionsByEntityAttribute(entityAttribute, EntityPermission.READ_ONLY);
    }
    
    public List<EntityAttributeDescription> getEntityAttributeDescriptionsByEntityAttributeForUpdate(EntityAttribute entityAttribute) {
        return getEntityAttributeDescriptionsByEntityAttribute(entityAttribute, EntityPermission.READ_WRITE);
    }
    
    public String getBestEntityAttributeDescription(EntityAttribute entityAttribute, Language language) {
        String description;
        var entityAttributeDescription = getEntityAttributeDescription(entityAttribute, language);
        
        if(entityAttributeDescription == null && !language.getIsDefault()) {
            entityAttributeDescription = getEntityAttributeDescription(entityAttribute, getPartyControl().getDefaultLanguage());
        }
        
        if(entityAttributeDescription == null) {
            description = entityAttribute.getLastDetail().getEntityAttributeName();
        } else {
            description = entityAttributeDescription.getDescription();
        }
        
        return description;
    }
    
    public EntityAttributeDescriptionTransfer getEntityAttributeDescriptionTransfer(UserVisit userVisit, EntityAttributeDescription entityAttributeDescription, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityAttributeDescriptionTransferCache().getEntityAttributeDescriptionTransfer(entityAttributeDescription, entityInstance);
    }
    
    public List<EntityAttributeDescriptionTransfer> getEntityAttributeDescriptionTransfersByEntityAttribute(UserVisit userVisit,
            EntityAttribute entityAttribute, EntityInstance entityInstance) {
        var entityAttributeDescriptions = getEntityAttributeDescriptionsByEntityAttribute(entityAttribute);
        List<EntityAttributeDescriptionTransfer> entityAttributeDescriptionTransfers = new ArrayList<>(entityAttributeDescriptions.size());
        var entityAttributeDescriptionTransferCache = getCoreTransferCaches(userVisit).getEntityAttributeDescriptionTransferCache();
        
        entityAttributeDescriptions.forEach((entityAttributeDescription) ->
                entityAttributeDescriptionTransfers.add(entityAttributeDescriptionTransferCache.getEntityAttributeDescriptionTransfer(entityAttributeDescription, entityInstance))
        );
        
        return entityAttributeDescriptionTransfers;
    }
    
    public void updateEntityAttributeDescriptionFromValue(EntityAttributeDescriptionValue entityAttributeDescriptionValue, BasePK updatedBy) {
        if(entityAttributeDescriptionValue.hasBeenModified()) {
            var entityAttributeDescription = EntityAttributeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     entityAttributeDescriptionValue.getPrimaryKey());
            
            entityAttributeDescription.setThruTime(session.START_TIME_LONG);
            entityAttributeDescription.store();

            var entityAttribute = entityAttributeDescription.getEntityAttribute();
            var language = entityAttributeDescription.getLanguage();
            var description = entityAttributeDescriptionValue.getDescription();
            
            entityAttributeDescription = EntityAttributeDescriptionFactory.getInstance().create(entityAttribute, language,
                    description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityAttributeDescription(EntityAttributeDescription entityAttributeDescription, BasePK deletedBy) {
        entityAttributeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(entityAttributeDescription.getEntityAttributePK(), EventTypes.MODIFY, entityAttributeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteEntityAttributeDescriptionsByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        var entityAttributeDescriptions = getEntityAttributeDescriptionsByEntityAttributeForUpdate(entityAttribute);
        
        entityAttributeDescriptions.forEach((entityAttributeDescription) -> 
                deleteEntityAttributeDescription(entityAttributeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Attribute Blobs
    // --------------------------------------------------------------------------------
    
    public EntityAttributeBlob createEntityAttributeBlob(EntityAttribute entityAttribute, Boolean checkContentWebAddress,
            BasePK createdBy) {
        var entityAttributeBlob = EntityAttributeBlobFactory.getInstance().create(session,
                entityAttribute, checkContentWebAddress, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeBlob.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return entityAttributeBlob;
    }
    
    private static final Map<EntityPermission, String> getEntityAttributeBlobQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM entityattributeblobs "
                + "WHERE enab_ena_entityattributeid = ? AND enab_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM entityattributeblobs "
                + "WHERE enab_ena_entityattributeid = ? AND enab_thrutime = ? "
                + "FOR UPDATE");
        getEntityAttributeBlobQueries = Collections.unmodifiableMap(queryMap);
    }

    private EntityAttributeBlob getEntityAttributeBlob(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        return EntityAttributeBlobFactory.getInstance().getEntityFromQuery(entityPermission, getEntityAttributeBlobQueries,
                entityAttribute, Session.MAX_TIME_LONG);
    }
    
    public EntityAttributeBlob getEntityAttributeBlob(EntityAttribute entityAttribute) {
        return getEntityAttributeBlob(entityAttribute, EntityPermission.READ_ONLY);
    }
    
    public EntityAttributeBlob getEntityAttributeBlobForUpdate(EntityAttribute entityAttribute) {
        return getEntityAttributeBlob(entityAttribute, EntityPermission.READ_WRITE);
    }
    
    public EntityAttributeBlobValue getEntityAttributeBlobValue(EntityAttributeBlob entityAttributeBlob) {
        return entityAttributeBlob == null? null: entityAttributeBlob.getEntityAttributeBlobValue().clone();
    }
    
    public EntityAttributeBlobValue getEntityAttributeBlobValueForUpdate(EntityAttribute entityAttribute) {
        return getEntityAttributeBlobValue(getEntityAttributeBlobForUpdate(entityAttribute));
    }
    
    public void updateEntityAttributeBlobFromValue(EntityAttributeBlobValue entityAttributeBlobValue, BasePK updatedBy) {
        if(entityAttributeBlobValue.hasBeenModified()) {
            var entityAttributeBlob = EntityAttributeBlobFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     entityAttributeBlobValue.getPrimaryKey());
            
            entityAttributeBlob.setThruTime(session.START_TIME_LONG);
            entityAttributeBlob.store();

            var entityAttribute = entityAttributeBlob.getEntityAttribute();
            var checkContentWebAddress = entityAttributeBlobValue.getCheckContentWebAddress();
            
            entityAttributeBlob = EntityAttributeBlobFactory.getInstance().create(entityAttribute, checkContentWebAddress,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeBlob.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityAttributeBlob(EntityAttributeBlob entityAttributeBlob, BasePK deletedBy) {
        entityAttributeBlob.setThruTime(session.START_TIME_LONG);
        
        sendEvent(entityAttributeBlob.getEntityAttributePK(), EventTypes.MODIFY, entityAttributeBlob.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteEntityAttributeBlobByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        var entityAttributeBlob = getEntityAttributeBlobForUpdate(entityAttribute);
        
        if(entityAttributeBlob != null) {
            deleteEntityAttributeBlob(entityAttributeBlob, deletedBy);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Attribute Strings
    // --------------------------------------------------------------------------------
    
    public EntityAttributeString createEntityAttributeString(EntityAttribute entityAttribute, String validationPattern,
            BasePK createdBy) {
        var entityAttributeString = EntityAttributeStringFactory.getInstance().create(session,
                entityAttribute, validationPattern, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeString.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return entityAttributeString;
    }
    
    private static final Map<EntityPermission, String> getEntityAttributeStringQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM entityattributestrings "
                + "WHERE enas_ena_entityattributeid = ? AND enas_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM entityattributestrings "
                + "WHERE enas_ena_entityattributeid = ? AND enas_thrutime = ? "
                + "FOR UPDATE");
        getEntityAttributeStringQueries = Collections.unmodifiableMap(queryMap);
    }

    private EntityAttributeString getEntityAttributeString(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        return EntityAttributeStringFactory.getInstance().getEntityFromQuery(entityPermission, getEntityAttributeStringQueries,
                entityAttribute, Session.MAX_TIME_LONG);
    }
    
    public EntityAttributeString getEntityAttributeString(EntityAttribute entityAttribute) {
        return getEntityAttributeString(entityAttribute, EntityPermission.READ_ONLY);
    }
    
    public EntityAttributeString getEntityAttributeStringForUpdate(EntityAttribute entityAttribute) {
        return getEntityAttributeString(entityAttribute, EntityPermission.READ_WRITE);
    }
    
    public EntityAttributeStringValue getEntityAttributeStringValue(EntityAttributeString entityAttributeString) {
        return entityAttributeString == null? null: entityAttributeString.getEntityAttributeStringValue().clone();
    }
    
    public EntityAttributeStringValue getEntityAttributeStringValueForUpdate(EntityAttribute entityAttribute) {
        return getEntityAttributeStringValue(getEntityAttributeStringForUpdate(entityAttribute));
    }
    
    public void updateEntityAttributeStringFromValue(EntityAttributeStringValue entityAttributeStringValue, BasePK updatedBy) {
        if(entityAttributeStringValue.hasBeenModified()) {
            var entityAttributeString = EntityAttributeStringFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     entityAttributeStringValue.getPrimaryKey());
            
            entityAttributeString.setThruTime(session.START_TIME_LONG);
            entityAttributeString.store();

            var entityAttribute = entityAttributeString.getEntityAttribute();
            var validationPattern = entityAttributeStringValue.getValidationPattern();
            
            entityAttributeString = EntityAttributeStringFactory.getInstance().create(entityAttribute, validationPattern,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeString.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityAttributeString(EntityAttributeString entityAttributeString, BasePK deletedBy) {
        entityAttributeString.setThruTime(session.START_TIME_LONG);
        
        sendEvent(entityAttributeString.getEntityAttributePK(), EventTypes.MODIFY, entityAttributeString.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteEntityAttributeStringByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        var entityAttributeString = getEntityAttributeStringForUpdate(entityAttribute);
        
        if(entityAttributeString != null) {
            deleteEntityAttributeString(entityAttributeString, deletedBy);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Attribute Integers
    // --------------------------------------------------------------------------------
    
    public EntityAttributeInteger createEntityAttributeInteger(EntityAttribute entityAttribute, Integer upperRangeIntegerValue,
            Integer upperLimitIntegerValue, Integer lowerLimitIntegerValue, Integer lowerRangeIntegerValue, BasePK createdBy) {
        var entityAttributeInteger = EntityAttributeIntegerFactory.getInstance().create(session,
                entityAttribute, upperRangeIntegerValue, upperLimitIntegerValue, lowerLimitIntegerValue, lowerRangeIntegerValue,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeInteger.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return entityAttributeInteger;
    }
    
    private static final Map<EntityPermission, String> getEntityAttributeIntegerQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM entityattributeintegers "
                + "WHERE enai_ena_entityattributeid = ? AND enai_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM entityattributeintegers "
                + "WHERE enai_ena_entityattributeid = ? AND enai_thrutime = ? "
                + "FOR UPDATE");
        getEntityAttributeIntegerQueries = Collections.unmodifiableMap(queryMap);
    }

    private EntityAttributeInteger getEntityAttributeInteger(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        return EntityAttributeIntegerFactory.getInstance().getEntityFromQuery(entityPermission, getEntityAttributeIntegerQueries,
                entityAttribute, Session.MAX_TIME_LONG);
    }
    
    public EntityAttributeInteger getEntityAttributeInteger(EntityAttribute entityAttribute) {
        return getEntityAttributeInteger(entityAttribute, EntityPermission.READ_ONLY);
    }
    
    public EntityAttributeInteger getEntityAttributeIntegerForUpdate(EntityAttribute entityAttribute) {
        return getEntityAttributeInteger(entityAttribute, EntityPermission.READ_WRITE);
    }
    
    public EntityAttributeIntegerValue getEntityAttributeIntegerValue(EntityAttributeInteger entityAttributeInteger) {
        return entityAttributeInteger == null? null: entityAttributeInteger.getEntityAttributeIntegerValue().clone();
    }
    
    public EntityAttributeIntegerValue getEntityAttributeIntegerValueForUpdate(EntityAttribute entityAttribute) {
        return getEntityAttributeIntegerValue(getEntityAttributeIntegerForUpdate(entityAttribute));
    }
    
    public void updateEntityAttributeIntegerFromValue(EntityAttributeIntegerValue entityAttributeIntegerValue, BasePK updatedBy) {
        if(entityAttributeIntegerValue.hasBeenModified()) {
            var entityAttributeInteger = EntityAttributeIntegerFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     entityAttributeIntegerValue.getPrimaryKey());
            
            entityAttributeInteger.setThruTime(session.START_TIME_LONG);
            entityAttributeInteger.store();

            var entityAttribute = entityAttributeInteger.getEntityAttribute();
            var upperRangeIntegerValue = entityAttributeIntegerValue.getUpperRangeIntegerValue();
            var upperLimitIntegerValue = entityAttributeIntegerValue.getUpperLimitIntegerValue();
            var lowerLimitIntegerValue = entityAttributeIntegerValue.getLowerLimitIntegerValue();
            var lowerRangeIntegerValue = entityAttributeIntegerValue.getLowerRangeIntegerValue();
            
            entityAttributeInteger = EntityAttributeIntegerFactory.getInstance().create(entityAttribute, upperRangeIntegerValue,
                    upperLimitIntegerValue, lowerLimitIntegerValue, lowerRangeIntegerValue, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeInteger.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityAttributeInteger(EntityAttributeInteger entityAttributeInteger, BasePK deletedBy) {
        entityAttributeInteger.setThruTime(session.START_TIME_LONG);
        
        sendEvent(entityAttributeInteger.getEntityAttributePK(), EventTypes.MODIFY, entityAttributeInteger.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteEntityAttributeIntegerByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        var entityAttributeInteger = getEntityAttributeIntegerForUpdate(entityAttribute);
        
        if(entityAttributeInteger != null) {
            deleteEntityAttributeInteger(entityAttributeInteger, deletedBy);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Attribute Longs
    // --------------------------------------------------------------------------------
    
    public EntityAttributeLong createEntityAttributeLong(EntityAttribute entityAttribute, Long upperRangeLongValue,
            Long upperLimitLongValue, Long lowerLimitLongValue, Long lowerRangeLongValue, BasePK createdBy) {
        var entityAttributeLong = EntityAttributeLongFactory.getInstance().create(session,
                entityAttribute, upperRangeLongValue, upperLimitLongValue, lowerLimitLongValue, lowerRangeLongValue,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeLong.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return entityAttributeLong;
    }
    
    private static final Map<EntityPermission, String> getEntityAttributeLongQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM entityattributelongs "
                + "WHERE enal_ena_entityattributeid = ? AND enal_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM entityattributelongs "
                + "WHERE enal_ena_entityattributeid = ? AND enal_thrutime = ? "
                + "FOR UPDATE");
        getEntityAttributeLongQueries = Collections.unmodifiableMap(queryMap);
    }

    private EntityAttributeLong getEntityAttributeLong(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        return EntityAttributeLongFactory.getInstance().getEntityFromQuery(entityPermission, getEntityAttributeLongQueries,
                entityAttribute, Session.MAX_TIME_LONG);
    }
    
    public EntityAttributeLong getEntityAttributeLong(EntityAttribute entityAttribute) {
        return getEntityAttributeLong(entityAttribute, EntityPermission.READ_ONLY);
    }
    
    public EntityAttributeLong getEntityAttributeLongForUpdate(EntityAttribute entityAttribute) {
        return getEntityAttributeLong(entityAttribute, EntityPermission.READ_WRITE);
    }
    
    public EntityAttributeLongValue getEntityAttributeLongValue(EntityAttributeLong entityAttributeLong) {
        return entityAttributeLong == null? null: entityAttributeLong.getEntityAttributeLongValue().clone();
    }
    
    public EntityAttributeLongValue getEntityAttributeLongValueForUpdate(EntityAttribute entityAttribute) {
        return getEntityAttributeLongValue(getEntityAttributeLongForUpdate(entityAttribute));
    }
    
    public void updateEntityAttributeLongFromValue(EntityAttributeLongValue entityAttributeLongValue, BasePK updatedBy) {
        if(entityAttributeLongValue.hasBeenModified()) {
            var entityAttributeLong = EntityAttributeLongFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     entityAttributeLongValue.getPrimaryKey());
            
            entityAttributeLong.setThruTime(session.START_TIME_LONG);
            entityAttributeLong.store();

            var entityAttribute = entityAttributeLong.getEntityAttribute();
            var upperRangeLongValue = entityAttributeLongValue.getUpperRangeLongValue();
            var upperLimitLongValue = entityAttributeLongValue.getUpperLimitLongValue();
            var lowerLimitLongValue = entityAttributeLongValue.getLowerLimitLongValue();
            var lowerRangeLongValue = entityAttributeLongValue.getLowerRangeLongValue();
            
            entityAttributeLong = EntityAttributeLongFactory.getInstance().create(entityAttribute, upperRangeLongValue,
                    upperLimitLongValue, lowerLimitLongValue, lowerRangeLongValue, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeLong.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityAttributeLong(EntityAttributeLong entityAttributeLong, BasePK deletedBy) {
        entityAttributeLong.setThruTime(session.START_TIME_LONG);
        
        sendEvent(entityAttributeLong.getEntityAttributePK(), EventTypes.MODIFY, entityAttributeLong.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteEntityAttributeLongByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        var entityAttributeLong = getEntityAttributeLongForUpdate(entityAttribute);
        
        if(entityAttributeLong != null) {
            deleteEntityAttributeLong(entityAttributeLong, deletedBy);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Attribute Numerics
    // --------------------------------------------------------------------------------
    
    public EntityAttributeNumeric createEntityAttributeNumeric(EntityAttribute entityAttribute, UnitOfMeasureType unitOfMeasureType,
            BasePK createdBy) {
        var entityAttributeNumeric = EntityAttributeNumericFactory.getInstance().create(session,
                entityAttribute, unitOfMeasureType, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeNumeric.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return entityAttributeNumeric;
    }
    
    public long countEntityAttributeNumericsByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM entityattributenumerics " +
                "WHERE enan_uomt_unitofmeasuretypeid = ? AND enan_thrutime = ?",
                unitOfMeasureType, Session.MAX_TIME_LONG);
    }

    private static final Map<EntityPermission, String> getEntityAttributeNumericQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM entityattributenumerics "
                + "WHERE enan_ena_entityattributeid = ? AND enan_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM entityattributenumerics "
                + "WHERE enan_ena_entityattributeid = ? AND enan_thrutime = ? "
                + "FOR UPDATE");
        getEntityAttributeNumericQueries = Collections.unmodifiableMap(queryMap);
    }

    private EntityAttributeNumeric getEntityAttributeNumeric(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        return EntityAttributeNumericFactory.getInstance().getEntityFromQuery(entityPermission, getEntityAttributeNumericQueries,
                entityAttribute, Session.MAX_TIME_LONG);
    }
    
    public EntityAttributeNumeric getEntityAttributeNumeric(EntityAttribute entityAttribute) {
        return getEntityAttributeNumeric(entityAttribute, EntityPermission.READ_ONLY);
    }
    
    public EntityAttributeNumeric getEntityAttributeNumericForUpdate(EntityAttribute entityAttribute) {
        return getEntityAttributeNumeric(entityAttribute, EntityPermission.READ_WRITE);
    }
    
    public EntityAttributeNumericValue getEntityAttributeNumericValue(EntityAttributeNumeric entityAttributeNumeric) {
        return entityAttributeNumeric == null? null: entityAttributeNumeric.getEntityAttributeNumericValue().clone();
    }
    
    public EntityAttributeNumericValue getEntityAttributeNumericValueForUpdate(EntityAttribute entityAttribute) {
        return getEntityAttributeNumericValue(getEntityAttributeNumericForUpdate(entityAttribute));
    }
    
    public void updateEntityAttributeNumericFromValue(EntityAttributeNumericValue entityAttributeNumericValue, BasePK updatedBy) {
        if(entityAttributeNumericValue.hasBeenModified()) {
            var entityAttributeNumeric = EntityAttributeNumericFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     entityAttributeNumericValue.getPrimaryKey());
            
            entityAttributeNumeric.setThruTime(session.START_TIME_LONG);
            entityAttributeNumeric.store();

            var entityAttributePK = entityAttributeNumeric.getEntityAttributePK();
            var unitOfMeasureTypePK = entityAttributeNumericValue.getUnitOfMeasureTypePK();
            
            entityAttributeNumeric = EntityAttributeNumericFactory.getInstance().create(entityAttributePK, unitOfMeasureTypePK,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(entityAttributePK, EventTypes.MODIFY, entityAttributeNumeric.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityAttributeNumeric(EntityAttributeNumeric entityAttributeNumeric, BasePK deletedBy) {
        entityAttributeNumeric.setThruTime(session.START_TIME_LONG);
        
        sendEvent(entityAttributeNumeric.getEntityAttributePK(), EventTypes.MODIFY, entityAttributeNumeric.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteEntityAttributeNumericByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        var entityAttributeNumeric = getEntityAttributeNumericForUpdate(entityAttribute);
        
        if(entityAttributeNumeric != null) {
            deleteEntityAttributeNumeric(entityAttributeNumeric, deletedBy);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Attribute List Items
    // --------------------------------------------------------------------------------
    
    public EntityAttributeListItem createEntityAttributeListItem(EntityAttribute entityAttribute, Sequence entityListItemSequence,
            BasePK createdBy) {
        var entityAttributeListItem = EntityAttributeListItemFactory.getInstance().create(session,
                entityAttribute, entityListItemSequence, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeListItem.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return entityAttributeListItem;
    }
    
    public long countEntityAttributeListItemsByEntityListItemSequence(Sequence entityListItemSequence) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM entityattributelistitems " +
                "WHERE enali_entitylistitemsequenceid = ? AND enali_thrutime = ?",
                entityListItemSequence, Session.MAX_TIME_LONG);
    }

    private static final Map<EntityPermission, String> getEntityAttributeListItemQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM entityattributelistitems "
                + "WHERE enali_entitylistitemsequenceid = ? AND enali_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM entityattributelistitems "
                + "WHERE enali_entitylistitemsequenceid = ? AND enali_thrutime = ? "
                + "FOR UPDATE");
        getEntityAttributeListItemQueries = Collections.unmodifiableMap(queryMap);
    }

    private EntityAttributeListItem getEntityAttributeListItem(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        return EntityAttributeListItemFactory.getInstance().getEntityFromQuery(entityPermission, getEntityAttributeListItemQueries,
                entityAttribute, Session.MAX_TIME_LONG);
    }
    
    public EntityAttributeListItem getEntityAttributeListItem(EntityAttribute entityAttribute) {
        return getEntityAttributeListItem(entityAttribute, EntityPermission.READ_ONLY);
    }
    
    public EntityAttributeListItem getEntityAttributeListItemForUpdate(EntityAttribute entityAttribute) {
        return getEntityAttributeListItem(entityAttribute, EntityPermission.READ_WRITE);
    }
    
    public EntityAttributeListItemValue getEntityAttributeListItemValue(EntityAttributeListItem entityAttributeListItem) {
        return entityAttributeListItem == null? null: entityAttributeListItem.getEntityAttributeListItemValue().clone();
    }
    
    public EntityAttributeListItemValue getEntityAttributeListItemValueForUpdate(EntityAttribute entityAttribute) {
        return getEntityAttributeListItemValue(getEntityAttributeListItemForUpdate(entityAttribute));
    }
    
    public void updateEntityAttributeListItemFromValue(EntityAttributeListItemValue entityAttributeListItemValue, BasePK updatedBy) {
        if(entityAttributeListItemValue.hasBeenModified()) {
            var entityAttributeListItem = EntityAttributeListItemFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     entityAttributeListItemValue.getPrimaryKey());
            
            entityAttributeListItem.setThruTime(session.START_TIME_LONG);
            entityAttributeListItem.store();

            var entityAttributePK = entityAttributeListItem.getEntityAttributePK();
            var entityListItemSequencePK = entityAttributeListItemValue.getEntityListItemSequencePK();
            
            entityAttributeListItem = EntityAttributeListItemFactory.getInstance().create(entityAttributePK, entityListItemSequencePK,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(entityAttributePK, EventTypes.MODIFY, entityAttributeListItem.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityAttributeListItem(EntityAttributeListItem entityAttributeListItem, BasePK deletedBy) {
        entityAttributeListItem.setThruTime(session.START_TIME_LONG);
        
        sendEvent(entityAttributeListItem.getEntityAttributePK(), EventTypes.MODIFY, entityAttributeListItem.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteEntityAttributeListItemByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        var entityAttributeListItem = getEntityAttributeListItemForUpdate(entityAttribute);
        
        if(entityAttributeListItem != null) {
            deleteEntityAttributeListItem(entityAttributeListItem, deletedBy);
        }
    }

    // --------------------------------------------------------------------------------
    //   Entity Attribute Workflows
    // --------------------------------------------------------------------------------

    public EntityAttributeWorkflow createEntityAttributeWorkflow(EntityAttribute entityAttribute, Workflow workflow,
            BasePK createdBy) {
        var entityAttributeWorkflow = EntityAttributeWorkflowFactory.getInstance().create(session,
                entityAttribute, workflow, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeWorkflow.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return entityAttributeWorkflow;
    }

    public long countEntityAttributeWorkflowsByWorkflow(Workflow workflow) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM entityattributeworkflows " +
                        "WHERE enawkfl_wkfl_workflowid = ? AND enawkfl_thrutime = ?",
                workflow, Session.MAX_TIME_LONG);
    }

    private static final Map<EntityPermission, String> getEntityAttributeWorkflowQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                        + "FROM entityattributeworkflows "
                        + "WHERE enawkfl_ena_entityattributeid = ? AND enawkfl_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                        + "FROM entityattributeworkflows "
                        + "WHERE enawkfl_ena_entityattributeid = ? AND enawkfl_thrutime = ? "
                        + "FOR UPDATE");
        getEntityAttributeWorkflowQueries = Collections.unmodifiableMap(queryMap);
    }

    private EntityAttributeWorkflow getEntityAttributeWorkflow(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        return EntityAttributeWorkflowFactory.getInstance().getEntityFromQuery(entityPermission, getEntityAttributeWorkflowQueries,
                entityAttribute, Session.MAX_TIME_LONG);
    }

    public EntityAttributeWorkflow getEntityAttributeWorkflow(EntityAttribute entityAttribute) {
        return getEntityAttributeWorkflow(entityAttribute, EntityPermission.READ_ONLY);
    }

    public EntityAttributeWorkflow getEntityAttributeWorkflowForUpdate(EntityAttribute entityAttribute) {
        return getEntityAttributeWorkflow(entityAttribute, EntityPermission.READ_WRITE);
    }

    public EntityAttributeWorkflowValue getEntityAttributeWorkflowValue(EntityAttributeWorkflow entityAttributeWorkflow) {
        return entityAttributeWorkflow == null? null: entityAttributeWorkflow.getEntityAttributeWorkflowValue().clone();
    }

    public EntityAttributeWorkflowValue getEntityAttributeWorkflowValueForUpdate(EntityAttribute entityAttribute) {
        return getEntityAttributeWorkflowValue(getEntityAttributeWorkflowForUpdate(entityAttribute));
    }

    public void updateEntityAttributeWorkflowFromValue(EntityAttributeWorkflowValue entityAttributeWorkflowValue, BasePK updatedBy) {
        if(entityAttributeWorkflowValue.hasBeenModified()) {
            var entityAttributeWorkflow = EntityAttributeWorkflowFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    entityAttributeWorkflowValue.getPrimaryKey());

            entityAttributeWorkflow.setThruTime(session.START_TIME_LONG);
            entityAttributeWorkflow.store();

            var entityAttributePK = entityAttributeWorkflow.getEntityAttributePK();
            var workflowPK = entityAttributeWorkflowValue.getWorkflowPK();

            entityAttributeWorkflow = EntityAttributeWorkflowFactory.getInstance().create(entityAttributePK, workflowPK,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(entityAttributePK, EventTypes.MODIFY, entityAttributeWorkflow.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteEntityAttributeWorkflow(EntityAttributeWorkflow entityAttributeWorkflow, BasePK deletedBy) {
        entityAttributeWorkflow.setThruTime(session.START_TIME_LONG);

        sendEvent(entityAttributeWorkflow.getEntityAttributePK(), EventTypes.MODIFY, entityAttributeWorkflow.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteEntityAttributeWorkflowByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        var entityAttributeWorkflow = getEntityAttributeWorkflowForUpdate(entityAttribute);

        if(entityAttributeWorkflow != null) {
            deleteEntityAttributeWorkflow(entityAttributeWorkflow, deletedBy);
        }
    }

    // --------------------------------------------------------------------------------
    //   Entity Attribute Entity Attribute Groups
    // --------------------------------------------------------------------------------
    
    public EntityAttributeEntityAttributeGroup createEntityAttributeEntityAttributeGroup(EntityAttribute entityAttribute,
            EntityAttributeGroup entityAttributeGroup, Integer sortOrder, BasePK createdBy) {
        var entityAttributeEntityAttributeGroup = EntityAttributeEntityAttributeGroupFactory.getInstance().create(session,
                entityAttribute, entityAttributeGroup, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeEntityAttributeGroup.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return entityAttributeEntityAttributeGroup;
    }

    public long countEntityAttributeEntityAttributeGroupsByEntityAttribute(EntityAttribute entityAttribute) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM entityattributeentityattributegroups " +
                "WHERE enaenagp_ena_entityattributeid = ? AND enaenagp_thrutime = ?",
                entityAttribute, Session.MAX_TIME_LONG);
    }

    public long countEntityAttributeEntityAttributeGroupsByEntityAttributeGroup(EntityAttributeGroup entityAttributeGroup) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM entityattributeentityattributegroups " +
                "WHERE enaenagp_enagp_entityattributegroupid = ? AND enaenagp_thrutime = ?",
                entityAttributeGroup, Session.MAX_TIME_LONG);
    }

    private EntityAttributeEntityAttributeGroup getEntityAttributeEntityAttributeGroup(EntityAttribute entityAttribute,
            EntityAttributeGroup entityAttributeGroup, EntityPermission entityPermission) {
        EntityAttributeEntityAttributeGroup entityAttributeEntityAttributeGroup;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityattributeentityattributegroups " +
                        "WHERE enaenagp_ena_entityattributeid = ? AND enaenagp_enagp_entityattributegroupid = ? AND enaenagp_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityattributeentityattributegroups " +
                        "WHERE enaenagp_ena_entityattributeid = ? AND enaenagp_enagp_entityattributegroupid = ? AND enaenagp_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = EntityAttributeEntityAttributeGroupFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityAttributeGroup.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityAttributeEntityAttributeGroup = EntityAttributeEntityAttributeGroupFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityAttributeEntityAttributeGroup;
    }
    
    public EntityAttributeEntityAttributeGroup getEntityAttributeEntityAttributeGroup(EntityAttribute entityAttribute, EntityAttributeGroup entityAttributeGroup) {
        return getEntityAttributeEntityAttributeGroup(entityAttribute, entityAttributeGroup, EntityPermission.READ_ONLY);
    }
    
    public EntityAttributeEntityAttributeGroup getEntityAttributeEntityAttributeGroupForUpdate(EntityAttribute entityAttribute, EntityAttributeGroup entityAttributeGroup) {
        return getEntityAttributeEntityAttributeGroup(entityAttribute, entityAttributeGroup, EntityPermission.READ_WRITE);
    }
    
    public EntityAttributeEntityAttributeGroupValue getEntityAttributeEntityAttributeGroupValue(EntityAttributeEntityAttributeGroup entityAttributeEntityAttributeGroup) {
        return entityAttributeEntityAttributeGroup == null? null: entityAttributeEntityAttributeGroup.getEntityAttributeEntityAttributeGroupValue().clone();
    }
    
    public EntityAttributeEntityAttributeGroupValue getEntityAttributeEntityAttributeGroupValueForUpdate(EntityAttribute entityAttribute, EntityAttributeGroup entityAttributeGroup) {
        return getEntityAttributeEntityAttributeGroupValue(getEntityAttributeEntityAttributeGroupForUpdate(entityAttribute, entityAttributeGroup));
    }
    
    public List<EntityAttributeEntityAttributeGroup> getEntityAttributeEntityAttributeGroupsByEntityAttribute(EntityAttribute entityAttribute,
            EntityPermission entityPermission) {
        List<EntityAttributeEntityAttributeGroup> entityAttributeEntityAttributeGroups;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ "
                        + "FROM entityattributeentityattributegroups, entityattributegroups, entityattributegroupdetails "
                        + "WHERE enaenagp_ena_entityattributeid = ? AND enaenagp_thrutime = ? "
                        + "AND enaenagp_enagp_entityattributegroupid = enagp_entityattributegroupid AND enagp_lastdetailid = enagpdt_entityattributegroupdetailid "
                        + "ORDER BY enaenagp_sortorder, enagpdt_sortorder, enagpdt_entityattributegroupname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ "
                        + "FROM entityattributeentityattributegroups "
                        + "WHERE enaenagp_ena_entityattributeid = ? AND enaenagp_thrutime = ? "
                        + "FOR UPDATE";
            }

            var ps = EntityAttributeEntityAttributeGroupFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityAttributeEntityAttributeGroups = EntityAttributeEntityAttributeGroupFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityAttributeEntityAttributeGroups;
    }
    
    public List<EntityAttributeEntityAttributeGroup> getEntityAttributeEntityAttributeGroupsByEntityAttribute(EntityAttribute entityAttribute) {
        return getEntityAttributeEntityAttributeGroupsByEntityAttribute(entityAttribute, EntityPermission.READ_ONLY);
    }
    
    public List<EntityAttributeEntityAttributeGroup> getEntityAttributeEntityAttributeGroupsByEntityAttributeForUpdate(EntityAttribute entityAttribute) {
        return getEntityAttributeEntityAttributeGroupsByEntityAttribute(entityAttribute, EntityPermission.READ_WRITE);
    }
    
    public List<EntityAttributeEntityAttributeGroup> getEntityAttributeEntityAttributeGroupsByEntityAttributeGroup(EntityAttributeGroup entityAttributeGroup,
            EntityPermission entityPermission) {
        List<EntityAttributeEntityAttributeGroup> entityAttributeEntityAttributeGroups;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ "
                        + "FROM entityattributeentityattributegroups, entityattributegroups, entityattributegroupdetails "
                        + "WHERE enaenagp_enagp_entityattributegroupid = ? AND enaenagp_thrutime = ? "
                        + "AND enaenagp_enagp_entityattributegroupid = enagp_entityattributegroupid AND enagp_lastdetailid = enagpdt_entityattributegroupdetailid "
                        + "ORDER BY enaenagp_sortorder, enagpdt_sortorder, enagpdt_entityattributegroupname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ "
                        + "FROM entityattributeentityattributegroups "
                        + "WHERE enaenagp_enagp_entityattributegroupid = ? AND enaenagp_thrutime = ? "
                        + "FOR UPDATE";
            }

            var ps = EntityAttributeEntityAttributeGroupFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttributeGroup.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityAttributeEntityAttributeGroups = EntityAttributeEntityAttributeGroupFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityAttributeEntityAttributeGroups;
    }
    
    public List<EntityAttributeEntityAttributeGroup> getEntityAttributeEntityAttributeGroupsByEntityAttributeGroup(EntityAttributeGroup entityAttributeGroup) {
        return getEntityAttributeEntityAttributeGroupsByEntityAttributeGroup(entityAttributeGroup, EntityPermission.READ_ONLY);
    }
    
    public List<EntityAttributeEntityAttributeGroup> getEntityAttributeEntityAttributeGroupsByEntityAttributeGroupForUpdate(EntityAttributeGroup entityAttributeGroup) {
        return getEntityAttributeEntityAttributeGroupsByEntityAttributeGroup(entityAttributeGroup, EntityPermission.READ_WRITE);
    }
    
    public EntityAttributeEntityAttributeGroupTransfer getEntityAttributeEntityAttributeGroupTransfer(UserVisit userVisit, EntityAttributeEntityAttributeGroup entityAttributeEntityAttributeGroup, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityAttributeEntityAttributeGroupTransferCache().getEntityAttributeEntityAttributeGroupTransfer(entityAttributeEntityAttributeGroup, entityInstance);
    }
    
    public List<EntityAttributeEntityAttributeGroupTransfer> getEntityAttributeEntityAttributeGroupTransfers(UserVisit userVisit,
            Collection<EntityAttributeEntityAttributeGroup> entityAttributeEntityAttributeGroups, EntityInstance entityInstance) {
        List<EntityAttributeEntityAttributeGroupTransfer> entityAttributeEntityAttributeGroupTransfers = new ArrayList<>(entityAttributeEntityAttributeGroups.size());
        var entityAttributeEntityAttributeGroupTransferCache = getCoreTransferCaches(userVisit).getEntityAttributeEntityAttributeGroupTransferCache();
        
        entityAttributeEntityAttributeGroups.forEach((entityAttributeEntityAttributeGroup) ->
                entityAttributeEntityAttributeGroupTransfers.add(entityAttributeEntityAttributeGroupTransferCache.getEntityAttributeEntityAttributeGroupTransfer(entityAttributeEntityAttributeGroup, entityInstance))
        );
        
        return entityAttributeEntityAttributeGroupTransfers;
    }
    
    public List<EntityAttributeEntityAttributeGroupTransfer> getEntityAttributeEntityAttributeGroupTransfersByEntityAttribute(UserVisit userVisit,
            EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityAttributeEntityAttributeGroupTransfers(userVisit, getEntityAttributeEntityAttributeGroupsByEntityAttribute(entityAttribute), entityInstance);
    }
    
    public List<EntityAttributeEntityAttributeGroupTransfer> getEntityAttributeEntityAttributeGroupTransfersByEntityAttributeGroup(UserVisit userVisit,
            EntityAttributeGroup entityAttributeGroup, EntityInstance entityInstance) {
        return getEntityAttributeEntityAttributeGroupTransfers(userVisit, getEntityAttributeEntityAttributeGroupsByEntityAttributeGroup(entityAttributeGroup), entityInstance);
    }
    
    public void updateEntityAttributeEntityAttributeGroupFromValue(EntityAttributeEntityAttributeGroupValue entityAttributeEntityAttributeGroupValue, BasePK updatedBy) {
        if(entityAttributeEntityAttributeGroupValue.hasBeenModified()) {
            var entityAttributeEntityAttributeGroup = EntityAttributeEntityAttributeGroupFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     entityAttributeEntityAttributeGroupValue.getPrimaryKey());
            
            entityAttributeEntityAttributeGroup.setThruTime(session.START_TIME_LONG);
            entityAttributeEntityAttributeGroup.store();

            var entityAttribute = entityAttributeEntityAttributeGroup.getEntityAttribute();
            var entityAttributeGroup = entityAttributeEntityAttributeGroup.getEntityAttributeGroup();
            var sortOrder = entityAttributeEntityAttributeGroupValue.getSortOrder();
            
            entityAttributeEntityAttributeGroup = EntityAttributeEntityAttributeGroupFactory.getInstance().create(entityAttribute, entityAttributeGroup,
                    sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeEntityAttributeGroup.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityAttributeEntityAttributeGroup(EntityAttributeEntityAttributeGroup entityAttributeEntityAttributeGroup, BasePK deletedBy) {
        entityAttributeEntityAttributeGroup.setThruTime(session.START_TIME_LONG);
        
        sendEvent(entityAttributeEntityAttributeGroup.getEntityAttributePK(), EventTypes.MODIFY, entityAttributeEntityAttributeGroup.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteEntityAttributeEntityAttributeGroupsByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        var entityAttributeEntityAttributeGroups = getEntityAttributeEntityAttributeGroupsByEntityAttributeForUpdate(entityAttribute);
        
        entityAttributeEntityAttributeGroups.forEach((entityAttributeEntityAttributeGroup) -> 
                deleteEntityAttributeEntityAttributeGroup(entityAttributeEntityAttributeGroup, deletedBy)
        );
    }
    
    public void deleteEntityAttributeEntityAttributeGroupsByEntityAttributeGroup(EntityAttributeGroup entityAttributeGroup, BasePK deletedBy) {
        var entityAttributeEntityAttributeGroups = getEntityAttributeEntityAttributeGroupsByEntityAttributeGroupForUpdate(entityAttributeGroup);
        
        entityAttributeEntityAttributeGroups.forEach((entityAttributeEntityAttributeGroup) -> 
                deleteEntityAttributeEntityAttributeGroup(entityAttributeEntityAttributeGroup, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Entity List Items
    // --------------------------------------------------------------------------------
    
    public EntityListItem createEntityListItem(EntityAttribute entityAttribute, String entityListItemName, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var defaultEntityListItem = getDefaultEntityListItem(entityAttribute);
        var defaultFound = defaultEntityListItem != null;
        
        if(defaultFound && isDefault) {
            var defaultEntityListItemDetailValue = getDefaultEntityListItemDetailValueForUpdate(entityAttribute);
            
            defaultEntityListItemDetailValue.setIsDefault(Boolean.FALSE);
            updateEntityListItemFromValue(defaultEntityListItemDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        var entityListItem = EntityListItemFactory.getInstance().create();
        var entityListItemDetail = EntityListItemDetailFactory.getInstance().create(entityListItem,
                entityAttribute, entityListItemName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        entityListItem = EntityListItemFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, entityListItem.getPrimaryKey());
        entityListItem.setActiveDetail(entityListItemDetail);
        entityListItem.setLastDetail(entityListItemDetail);
        entityListItem.store();
        
        sendEvent(entityListItem.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return entityListItem;
    }
    
    /** Assume that the entityInstance passed to this function is a ECHO_THREE.EntityListItem */
    public EntityListItem getEntityListItemByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new EntityListItemPK(entityInstance.getEntityUniqueId());

        return EntityListItemFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public EntityListItem getEntityListItemByEntityInstance(EntityInstance entityInstance) {
        return getEntityListItemByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public EntityListItem getEntityListItemByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getEntityListItemByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    public EntityListItem getEntityListItemByPK(EntityListItemPK pk) {
        return EntityListItemFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);
    }
    
    public long countEntityListItems() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM entitylistitems, entitylistitemdetails " +
                "WHERE eli_activedetailid = elidt_entitylistitemdetailid");
    }

    private EntityListItem getDefaultEntityListItem(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        EntityListItem entityListItem;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylistitems, entitylistitemdetails " +
                        "WHERE eli_activedetailid = elidt_entitylistitemdetailid " +
                        "AND elidt_ena_entityattributeid = ? AND elidt_isdefault = 1";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylistitems, entitylistitemdetails " +
                        "WHERE eli_activedetailid = elidt_entitylistitemdetailid " +
                        "AND elidt_ena_entityattributeid = ? AND elidt_isdefault = 1 " +
                        "FOR UPDATE";
            }

            var ps = EntityListItemFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            
            entityListItem = EntityListItemFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityListItem;
    }
    
    public EntityListItem getDefaultEntityListItem(EntityAttribute entityAttribute) {
        return getDefaultEntityListItem(entityAttribute, EntityPermission.READ_ONLY);
    }
    
    public EntityListItem getDefaultEntityListItemForUpdate(EntityAttribute entityAttribute) {
        return getDefaultEntityListItem(entityAttribute, EntityPermission.READ_WRITE);
    }
    
    public EntityListItemDetailValue getDefaultEntityListItemDetailValueForUpdate(EntityAttribute entityAttribute) {
        return getDefaultEntityListItemForUpdate(entityAttribute).getLastDetailForUpdate().getEntityListItemDetailValue().clone();
    }
    
    public EntityListItem getEntityListItemByName(EntityAttribute entityAttribute, String entityListItemName, EntityPermission entityPermission) {
        EntityListItem entityListItem;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylistitems, entitylistitemdetails " +
                        "WHERE eli_activedetailid = elidt_entitylistitemdetailid " +
                        "AND elidt_ena_entityattributeid = ? AND elidt_entitylistitemname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylistitems, entitylistitemdetails " +
                        "WHERE eli_activedetailid = elidt_entitylistitemdetailid " +
                        "AND elidt_ena_entityattributeid = ? AND elidt_entitylistitemname = ? " +
                        "FOR UPDATE";
            }

            var ps = EntityListItemFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setString(2, entityListItemName);
            
            entityListItem = EntityListItemFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityListItem;
    }
    
    public EntityListItem getEntityListItemByName(EntityAttribute entityAttribute, String entityListItemName) {
        return getEntityListItemByName(entityAttribute, entityListItemName, EntityPermission.READ_ONLY);
    }
    
    public EntityListItem getEntityListItemByNameForUpdate(EntityAttribute entityAttribute, String entityListItemName) {
        return getEntityListItemByName(entityAttribute, entityListItemName, EntityPermission.READ_WRITE);
    }
    
    public EntityListItemDetailValue getEntityListItemDetailValueForUpdate(EntityListItem entityListItem) {
        return entityListItem == null? null: entityListItem.getLastDetailForUpdate().getEntityListItemDetailValue().clone();
    }
    
    public EntityListItemDetailValue getEntityListItemDetailValueByNameForUpdate(EntityAttribute entityAttribute, String entityListItemName) {
        return getEntityListItemDetailValueForUpdate(getEntityListItemByNameForUpdate(entityAttribute, entityListItemName));
    }
    
    private List<EntityListItem> getEntityListItems(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        List<EntityListItem> entityListItems;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylistitems, entitylistitemdetails " +
                        "WHERE eli_activedetailid = elidt_entitylistitemdetailid AND elidt_ena_entityattributeid = ? " +
                        "ORDER BY elidt_sortorder, elidt_entitylistitemname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylistitems, entitylistitemdetails " +
                        "WHERE eli_activedetailid = elidt_entitylistitemdetailid AND elidt_ena_entityattributeid = ? " +
                        "FOR UPDATE";
            }

            var ps = EntityListItemFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            
            entityListItems = EntityListItemFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityListItems;
    }
    
    public List<EntityListItem> getEntityListItems(EntityAttribute entityAttribute) {
        return getEntityListItems(entityAttribute, EntityPermission.READ_ONLY);
    }
    
    public List<EntityListItem> getEntityListItemsForUpdate(EntityAttribute entityAttribute) {
        return getEntityListItems(entityAttribute, EntityPermission.READ_WRITE);
    }

    public long countEntityListItems(EntityAttribute entityAttribute) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM entitylistitems, entitylistitemdetails " +
                "WHERE eli_activedetailid = elidt_entitylistitemdetailid AND elidt_ena_entityattributeid = ?",
                entityAttribute);
    }

    public EntityListItemTransfer getEntityListItemTransfer(UserVisit userVisit, EntityListItem entityListItem, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityListItemTransferCache().getEntityListItemTransfer(entityListItem, entityInstance);
    }
    
    public List<EntityListItemTransfer> getEntityListItemTransfers(UserVisit userVisit, Collection<EntityListItem> entityListItems, EntityInstance entityInstance) {
        List<EntityListItemTransfer> entityListItemTransfers = new ArrayList<>(entityListItems.size());
        var entityListItemTransferCache = getCoreTransferCaches(userVisit).getEntityListItemTransferCache();

        entityListItems.forEach((entityListItem) ->
                entityListItemTransfers.add(entityListItemTransferCache.getEntityListItemTransfer(entityListItem, entityInstance))
        );

        return entityListItemTransfers;
    }

    public List<EntityListItemTransfer> getEntityListItemTransfersByEntityAttribute(UserVisit userVisit, EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityListItemTransfers(userVisit, getEntityListItems(entityAttribute), entityInstance);
    }

    private void updateEntityListItemFromValue(EntityListItemDetailValue entityListItemDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(entityListItemDetailValue.hasBeenModified()) {
            var entityListItem = EntityListItemFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     entityListItemDetailValue.getEntityListItemPK());
            var entityListItemDetail = entityListItem.getActiveDetailForUpdate();
            
            entityListItemDetail.setThruTime(session.START_TIME_LONG);
            entityListItemDetail.store();

            var entityListItemPK = entityListItemDetail.getEntityListItemPK(); // Not updated
            var entityAttribute = entityListItemDetail.getEntityAttribute(); // Not updated
            var entityListItemName = entityListItemDetailValue.getEntityListItemName();
            var isDefault = entityListItemDetailValue.getIsDefault();
            var sortOrder = entityListItemDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultEntityListItem = getDefaultEntityListItem(entityAttribute);
                var defaultFound = defaultEntityListItem != null && !defaultEntityListItem.equals(entityListItem);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultEntityListItemDetailValue = getDefaultEntityListItemDetailValueForUpdate(entityAttribute);
                    
                    defaultEntityListItemDetailValue.setIsDefault(Boolean.FALSE);
                    updateEntityListItemFromValue(defaultEntityListItemDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            entityListItemDetail = EntityListItemDetailFactory.getInstance().create(entityListItemPK,
                    entityAttribute.getPrimaryKey(), entityListItemName, isDefault, sortOrder, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            entityListItem.setActiveDetail(entityListItemDetail);
            entityListItem.setLastDetail(entityListItemDetail);
            
            sendEvent(entityListItemPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateEntityListItemFromValue(EntityListItemDetailValue entityListItemDetailValue, BasePK updatedBy) {
        updateEntityListItemFromValue(entityListItemDetailValue, true, updatedBy);
    }
    
    public EntityListItemChoicesBean getEntityListItemChoices(String defaultEntityListItemChoice, Language language,
            boolean allowNullChoice, EntityAttribute entityAttribute) {
        var entityListItems = getEntityListItems(entityAttribute);
        var size = entityListItems.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultEntityListItemChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var entityListItem : entityListItems) {
            var entityListItemDetail = entityListItem.getLastDetail();
            var label = getBestEntityListItemDescription(entityListItem, language);
            var value = entityListItemDetail.getEntityListItemName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultEntityListItemChoice != null && defaultEntityListItemChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && entityListItemDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new EntityListItemChoicesBean(labels, values, defaultValue);
    }
    
    private void deleteEntityListItem(EntityListItem entityListItem, boolean checkDefault, BasePK deletedBy) {
        var entityListItemDetail = entityListItem.getLastDetailForUpdate();
        var entityAttributeTypeName = entityListItemDetail.getEntityAttribute().getLastDetail().getEntityAttributeType().getEntityAttributeTypeName();
        
        if(entityAttributeTypeName.equals(EntityAttributeTypes.LISTITEM.name())) {
            deleteEntityListItemDefaultByEntityListItem(entityListItem, deletedBy);
            deleteEntityListItemAttributesByEntityListItem(entityListItem, deletedBy);
        } else if(entityAttributeTypeName.equals(EntityAttributeTypes.MULTIPLELISTITEM.name())) {
            deleteEntityMultipleListItemDefaultsByEntityListItem(entityListItem, deletedBy);
            deleteEntityMultipleListItemAttributesByEntityListItem(entityListItem, deletedBy);
        }
        
        deleteEntityListItemDescriptionsByEntityListItem(entityListItem, deletedBy);
        
        entityListItemDetail.setThruTime(session.START_TIME_LONG);
        entityListItem.setActiveDetail(null);
        entityListItem.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var entityAttribute = entityListItemDetail.getEntityAttribute();
            var defaultEntityListItem = getDefaultEntityListItem(entityAttribute);
            if(defaultEntityListItem == null) {
                var entityListItems = getEntityListItemsForUpdate(entityAttribute);

                if(!entityListItems.isEmpty()) {
                    var iter = entityListItems.iterator();
                    if(iter.hasNext()) {
                        defaultEntityListItem = iter.next();
                    }
                    var entityListItemDetailValue = Objects.requireNonNull(defaultEntityListItem).getLastDetailForUpdate().getEntityListItemDetailValue().clone();

                    entityListItemDetailValue.setIsDefault(Boolean.TRUE);
                    updateEntityListItemFromValue(entityListItemDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(entityListItem.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteEntityListItem(EntityListItem entityListItem, BasePK deletedBy) {
        deleteEntityListItem(entityListItem, true, deletedBy);
    }

    public void deleteEntityListItemsByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        var entityListItems = getEntityListItemsForUpdate(entityAttribute);
        
        entityListItems.forEach((entityListItem) ->
                deleteEntityListItem(entityListItem, false, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Entity List Item Descriptions
    // --------------------------------------------------------------------------------
    
    public EntityListItemDescription createEntityListItemDescription(EntityListItem entityListItem, Language language, String description, BasePK createdBy) {
        var entityListItemDescription = EntityListItemDescriptionFactory.getInstance().create(entityListItem, language, description, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEvent(entityListItem.getPrimaryKey(), EventTypes.MODIFY, entityListItemDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return entityListItemDescription;
    }
    
    private EntityListItemDescription getEntityListItemDescription(EntityListItem entityListItem, Language language, EntityPermission entityPermission) {
        EntityListItemDescription entityListItemDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylistitemdescriptions " +
                        "WHERE elid_eli_entitylistitemid = ? AND elid_lang_languageid = ? AND elid_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylistitemdescriptions " +
                        "WHERE elid_eli_entitylistitemid = ? AND elid_lang_languageid = ? AND elid_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = EntityListItemDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityListItem.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityListItemDescription = EntityListItemDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityListItemDescription;
    }
    
    public EntityListItemDescription getEntityListItemDescription(EntityListItem entityListItem, Language language) {
        return getEntityListItemDescription(entityListItem, language, EntityPermission.READ_ONLY);
    }
    
    public EntityListItemDescription getEntityListItemDescriptionForUpdate(EntityListItem entityListItem, Language language) {
        return getEntityListItemDescription(entityListItem, language, EntityPermission.READ_WRITE);
    }
    
    public EntityListItemDescriptionValue getEntityListItemDescriptionValue(EntityListItemDescription entityListItemDescription) {
        return entityListItemDescription == null? null: entityListItemDescription.getEntityListItemDescriptionValue().clone();
    }
    
    public EntityListItemDescriptionValue getEntityListItemDescriptionValueForUpdate(EntityListItem entityListItem, Language language) {
        return getEntityListItemDescriptionValue(getEntityListItemDescriptionForUpdate(entityListItem, language));
    }
    
    private List<EntityListItemDescription> getEntityListItemDescriptionsByEntityListItem(EntityListItem entityListItem, EntityPermission entityPermission) {
        List<EntityListItemDescription> entityListItemDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylistitemdescriptions, languages " +
                        "WHERE elid_eli_entitylistitemid = ? AND elid_thrutime = ? AND elid_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylistitemdescriptions " +
                        "WHERE elid_eli_entitylistitemid = ? AND elid_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = EntityListItemDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityListItem.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityListItemDescriptions = EntityListItemDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityListItemDescriptions;
    }
    
    public List<EntityListItemDescription> getEntityListItemDescriptionsByEntityListItem(EntityListItem entityListItem) {
        return getEntityListItemDescriptionsByEntityListItem(entityListItem, EntityPermission.READ_ONLY);
    }
    
    public List<EntityListItemDescription> getEntityListItemDescriptionsByEntityListItemForUpdate(EntityListItem entityListItem) {
        return getEntityListItemDescriptionsByEntityListItem(entityListItem, EntityPermission.READ_WRITE);
    }
    
    public String getBestEntityListItemDescription(EntityListItem entityListItem, Language language) {
        String description;
        var entityListItemDescription = getEntityListItemDescription(entityListItem, language);
        
        if(entityListItemDescription == null && !language.getIsDefault()) {
            entityListItemDescription = getEntityListItemDescription(entityListItem, getPartyControl().getDefaultLanguage());
        }
        
        if(entityListItemDescription == null) {
            description = entityListItem.getLastDetail().getEntityListItemName();
        } else {
            description = entityListItemDescription.getDescription();
        }
        
        return description;
    }
    
    public EntityListItemDescriptionTransfer getEntityListItemDescriptionTransfer(UserVisit userVisit, EntityListItemDescription entityListItemDescription, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityListItemDescriptionTransferCache().getEntityListItemDescriptionTransfer(entityListItemDescription, entityInstance);
    }
    
    public List<EntityListItemDescriptionTransfer> getEntityListItemDescriptionTransfersByEntityListItem(UserVisit userVisit, EntityListItem entityListItem, EntityInstance entityInstance) {
        var entityListItemDescriptions = getEntityListItemDescriptionsByEntityListItem(entityListItem);
        List<EntityListItemDescriptionTransfer> entityListItemDescriptionTransfers = new ArrayList<>(entityListItemDescriptions.size());
        var entityListItemDescriptionTransferCache = getCoreTransferCaches(userVisit).getEntityListItemDescriptionTransferCache();
        
        entityListItemDescriptions.forEach((entityListItemDescription) ->
                entityListItemDescriptionTransfers.add(entityListItemDescriptionTransferCache.getEntityListItemDescriptionTransfer(entityListItemDescription, entityInstance))
        );
        
        return entityListItemDescriptionTransfers;
    }
    
    public void updateEntityListItemDescriptionFromValue(EntityListItemDescriptionValue entityListItemDescriptionValue, BasePK updatedBy) {
        if(entityListItemDescriptionValue.hasBeenModified()) {
            var entityListItemDescription = EntityListItemDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, entityListItemDescriptionValue.getPrimaryKey());
            
            entityListItemDescription.setThruTime(session.START_TIME_LONG);
            entityListItemDescription.store();

            var entityListItem = entityListItemDescription.getEntityListItem();
            var language = entityListItemDescription.getLanguage();
            var description = entityListItemDescriptionValue.getDescription();
            
            entityListItemDescription = EntityListItemDescriptionFactory.getInstance().create(entityListItem, language,
                    description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(entityListItem.getPrimaryKey(), EventTypes.MODIFY, entityListItemDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityListItemDescription(EntityListItemDescription entityListItemDescription, BasePK deletedBy) {
        entityListItemDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(entityListItemDescription.getEntityListItemPK(), EventTypes.MODIFY, entityListItemDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteEntityListItemDescriptionsByEntityListItem(EntityListItem entityListItem, BasePK deletedBy) {
        var entityListItemDescriptions = getEntityListItemDescriptionsByEntityListItemForUpdate(entityListItem);
        
        entityListItemDescriptions.forEach((entityListItemDescription) -> 
                deleteEntityListItemDescription(entityListItemDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Integer Ranges
    // --------------------------------------------------------------------------------
    
    public EntityIntegerRange createEntityIntegerRange(EntityAttribute entityAttribute, String entityIntegerRangeName, Integer minimumIntegerValue,
            Integer maximumIntegerValue, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultEntityIntegerRange = getDefaultEntityIntegerRange(entityAttribute);
        var defaultFound = defaultEntityIntegerRange != null;
        
        if(defaultFound && isDefault) {
            var defaultEntityIntegerRangeDetailValue = getDefaultEntityIntegerRangeDetailValueForUpdate(entityAttribute);
            
            defaultEntityIntegerRangeDetailValue.setIsDefault(Boolean.FALSE);
            updateEntityIntegerRangeFromValue(defaultEntityIntegerRangeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        var entityIntegerRange = EntityIntegerRangeFactory.getInstance().create();
        var entityIntegerRangeDetail = EntityIntegerRangeDetailFactory.getInstance().create(entityIntegerRange, entityAttribute,
                entityIntegerRangeName, minimumIntegerValue, maximumIntegerValue, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        entityIntegerRange = EntityIntegerRangeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, entityIntegerRange.getPrimaryKey());
        entityIntegerRange.setActiveDetail(entityIntegerRangeDetail);
        entityIntegerRange.setLastDetail(entityIntegerRangeDetail);
        entityIntegerRange.store();
        
        sendEvent(entityIntegerRange.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return entityIntegerRange;
    }
    
    /** Assume that the entityInstance passed to this function is a ECHO_THREE.EntityIntegerRange */
    public EntityIntegerRange getEntityIntegerRangeByEntityInstance(EntityInstance entityInstance) {
        var pk = new EntityIntegerRangePK(entityInstance.getEntityUniqueId());
        var entityIntegerRange = EntityIntegerRangeFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);
        
        return entityIntegerRange;
    }
    
    private EntityIntegerRange getDefaultEntityIntegerRange(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        EntityIntegerRange entityIntegerRange;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityintegerranges, entityintegerrangedetails " +
                        "WHERE enir_activedetailid = enirdt_entityintegerrangedetailid " +
                        "AND enirdt_ena_entityattributeid = ? AND enirdt_isdefault = 1";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityintegerranges, entityintegerrangedetails " +
                        "WHERE enir_activedetailid = enirdt_entityintegerrangedetailid " +
                        "AND enirdt_ena_entityattributeid = ? AND enirdt_isdefault = 1 " +
                        "FOR UPDATE";
            }

            var ps = EntityIntegerRangeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            
            entityIntegerRange = EntityIntegerRangeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityIntegerRange;
    }
    
    public EntityIntegerRange getDefaultEntityIntegerRange(EntityAttribute entityAttribute) {
        return getDefaultEntityIntegerRange(entityAttribute, EntityPermission.READ_ONLY);
    }
    
    public EntityIntegerRange getDefaultEntityIntegerRangeForUpdate(EntityAttribute entityAttribute) {
        return getDefaultEntityIntegerRange(entityAttribute, EntityPermission.READ_WRITE);
    }
    
    public EntityIntegerRangeDetailValue getDefaultEntityIntegerRangeDetailValueForUpdate(EntityAttribute entityAttribute) {
        return getDefaultEntityIntegerRangeForUpdate(entityAttribute).getLastDetailForUpdate().getEntityIntegerRangeDetailValue().clone();
    }
    
    private EntityIntegerRange getEntityIntegerRangeByName(EntityAttribute entityAttribute, String entityIntegerRangeName, EntityPermission entityPermission) {
        EntityIntegerRange entityIntegerRange;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityintegerranges, entityintegerrangedetails " +
                        "WHERE enir_activedetailid = enirdt_entityintegerrangedetailid " +
                        "AND enirdt_ena_entityattributeid = ? AND enirdt_entityintegerrangename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityintegerranges, entityintegerrangedetails " +
                        "WHERE enir_activedetailid = enirdt_entityintegerrangedetailid " +
                        "AND enirdt_ena_entityattributeid = ? AND enirdt_entityintegerrangename = ? " +
                        "FOR UPDATE";
            }

            var ps = EntityIntegerRangeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setString(2, entityIntegerRangeName);
            
            entityIntegerRange = EntityIntegerRangeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityIntegerRange;
    }
    
    public EntityIntegerRange getEntityIntegerRangeByName(EntityAttribute entityAttribute, String entityIntegerRangeName) {
        return getEntityIntegerRangeByName(entityAttribute, entityIntegerRangeName, EntityPermission.READ_ONLY);
    }
    
    public EntityIntegerRange getEntityIntegerRangeByNameForUpdate(EntityAttribute entityAttribute, String entityIntegerRangeName) {
        return getEntityIntegerRangeByName(entityAttribute, entityIntegerRangeName, EntityPermission.READ_WRITE);
    }
    
    public EntityIntegerRangeDetailValue getEntityIntegerRangeDetailValueForUpdate(EntityIntegerRange entityIntegerRange) {
        return entityIntegerRange == null? null: entityIntegerRange.getLastDetailForUpdate().getEntityIntegerRangeDetailValue().clone();
    }
    
    public EntityIntegerRangeDetailValue getEntityIntegerRangeDetailValueByNameForUpdate(EntityAttribute entityAttribute, String entityIntegerRangeName) {
        return getEntityIntegerRangeDetailValueForUpdate(getEntityIntegerRangeByNameForUpdate(entityAttribute, entityIntegerRangeName));
    }
    
    private List<EntityIntegerRange> getEntityIntegerRanges(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        List<EntityIntegerRange> entityIntegerRanges;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityintegerranges, entityintegerrangedetails " +
                        "WHERE enir_activedetailid = enirdt_entityintegerrangedetailid AND enirdt_ena_entityattributeid = ? " +
                        "ORDER BY enirdt_sortorder, enirdt_entityintegerrangename " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityintegerranges, entityintegerrangedetails " +
                        "WHERE enir_activedetailid = enirdt_entityintegerrangedetailid AND enirdt_ena_entityattributeid = ? " +
                        "FOR UPDATE";
            }

            var ps = EntityIntegerRangeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            
            entityIntegerRanges = EntityIntegerRangeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityIntegerRanges;
    }
    
    public List<EntityIntegerRange> getEntityIntegerRanges(EntityAttribute entityAttribute) {
        return getEntityIntegerRanges(entityAttribute, EntityPermission.READ_ONLY);
    }
    
    public List<EntityIntegerRange> getEntityIntegerRangesForUpdate(EntityAttribute entityAttribute) {
        return getEntityIntegerRanges(entityAttribute, EntityPermission.READ_WRITE);
    }

    public long countEntityIntegerRanges(EntityAttribute entityAttribute) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM entityintegerranges, entityintegerrangedetails " +
                "WHERE enir_activedetailid = enirdt_entityintegerrangedetailid AND enirdt_ena_entityattributeid = ?",
                entityAttribute);
    }

    public EntityIntegerRangeTransfer getEntityIntegerRangeTransfer(UserVisit userVisit, EntityIntegerRange entityIntegerRange, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityIntegerRangeTransferCache().getEntityIntegerRangeTransfer(entityIntegerRange, entityInstance);
    }
    
    public List<EntityIntegerRangeTransfer> getEntityIntegerRangeTransfers(UserVisit userVisit, Collection<EntityIntegerRange> entityIntegerRanges, EntityInstance entityInstance) {
        List<EntityIntegerRangeTransfer> entityIntegerRangeTransfers = new ArrayList<>(entityIntegerRanges.size());
        var entityIntegerRangeTransferCache = getCoreTransferCaches(userVisit).getEntityIntegerRangeTransferCache();

        entityIntegerRanges.forEach((entityIntegerRange) ->
                entityIntegerRangeTransfers.add(entityIntegerRangeTransferCache.getEntityIntegerRangeTransfer(entityIntegerRange, entityInstance))
        );

        return entityIntegerRangeTransfers;
    }

    public List<EntityIntegerRangeTransfer> getEntityIntegerRangeTransfersByEntityAttribute(UserVisit userVisit, EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityIntegerRangeTransfers(userVisit, getEntityIntegerRanges(entityAttribute), entityInstance);
    }

    private void updateEntityIntegerRangeFromValue(EntityIntegerRangeDetailValue entityIntegerRangeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(entityIntegerRangeDetailValue.hasBeenModified()) {
            var entityIntegerRange = EntityIntegerRangeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     entityIntegerRangeDetailValue.getEntityIntegerRangePK());
            var entityIntegerRangeDetail = entityIntegerRange.getActiveDetailForUpdate();
            
            entityIntegerRangeDetail.setThruTime(session.START_TIME_LONG);
            entityIntegerRangeDetail.store();

            var entityIntegerRangePK = entityIntegerRangeDetail.getEntityIntegerRangePK(); // Not updated
            var entityAttribute = entityIntegerRangeDetail.getEntityAttribute(); // Not updated
            var entityIntegerRangeName = entityIntegerRangeDetailValue.getEntityIntegerRangeName();
            var minimumIntegerValue = entityIntegerRangeDetailValue.getMinimumIntegerValue();
            var maximumIntegerValue = entityIntegerRangeDetailValue.getMaximumIntegerValue();
            var isDefault = entityIntegerRangeDetailValue.getIsDefault();
            var sortOrder = entityIntegerRangeDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultEntityIntegerRange = getDefaultEntityIntegerRange(entityAttribute);
                var defaultFound = defaultEntityIntegerRange != null && !defaultEntityIntegerRange.equals(entityIntegerRange);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultEntityIntegerRangeDetailValue = getDefaultEntityIntegerRangeDetailValueForUpdate(entityAttribute);
                    
                    defaultEntityIntegerRangeDetailValue.setIsDefault(Boolean.FALSE);
                    updateEntityIntegerRangeFromValue(defaultEntityIntegerRangeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            entityIntegerRangeDetail = EntityIntegerRangeDetailFactory.getInstance().create(entityIntegerRangePK, entityAttribute.getPrimaryKey(), entityIntegerRangeName,
                    minimumIntegerValue, maximumIntegerValue, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            entityIntegerRange.setActiveDetail(entityIntegerRangeDetail);
            entityIntegerRange.setLastDetail(entityIntegerRangeDetail);
            
            sendEvent(entityIntegerRangePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateEntityIntegerRangeFromValue(EntityIntegerRangeDetailValue entityIntegerRangeDetailValue, BasePK updatedBy) {
        updateEntityIntegerRangeFromValue(entityIntegerRangeDetailValue, true, updatedBy);
    }
    
    public EntityIntegerRangeChoicesBean getEntityIntegerRangeChoices(String defaultEntityIntegerRangeChoice, Language language,
            boolean allowNullChoice, EntityAttribute entityAttribute) {
        var entityIntegerRanges = getEntityIntegerRanges(entityAttribute);
        var size = entityIntegerRanges.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultEntityIntegerRangeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var entityIntegerRange : entityIntegerRanges) {
            var entityIntegerRangeDetail = entityIntegerRange.getLastDetail();
            var label = getBestEntityIntegerRangeDescription(entityIntegerRange, language);
            var value = entityIntegerRangeDetail.getEntityIntegerRangeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultEntityIntegerRangeChoice != null && defaultEntityIntegerRangeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && entityIntegerRangeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new EntityIntegerRangeChoicesBean(labels, values, defaultValue);
    }
    
    private void deleteEntityIntegerRange(EntityIntegerRange entityIntegerRange, boolean checkDefault, BasePK deletedBy) {
        var entityIntegerRangeDetail = entityIntegerRange.getLastDetailForUpdate();
        
        deleteEntityIntegerRangeDescriptionsByEntityIntegerRange(entityIntegerRange, deletedBy);
        
        entityIntegerRangeDetail.setThruTime(session.START_TIME_LONG);
        entityIntegerRange.setActiveDetail(null);
        entityIntegerRange.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var entityAttribute = entityIntegerRangeDetail.getEntityAttribute();
            var defaultEntityIntegerRange = getDefaultEntityIntegerRange(entityAttribute);
            if(defaultEntityIntegerRange == null) {
                var entityIntegerRanges = getEntityIntegerRangesForUpdate(entityAttribute);

                if(!entityIntegerRanges.isEmpty()) {
                    var iter = entityIntegerRanges.iterator();
                    if(iter.hasNext()) {
                        defaultEntityIntegerRange = iter.next();
                    }
                    var entityIntegerRangeDetailValue = Objects.requireNonNull(defaultEntityIntegerRange).getLastDetailForUpdate().getEntityIntegerRangeDetailValue().clone();

                    entityIntegerRangeDetailValue.setIsDefault(Boolean.TRUE);
                    updateEntityIntegerRangeFromValue(entityIntegerRangeDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(entityIntegerRange.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteEntityIntegerRange(EntityIntegerRange entityIntegerRange, BasePK deletedBy) {
        deleteEntityIntegerRange(entityIntegerRange, true, deletedBy);
    }

    public void deleteEntityIntegerRangesByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        var entityIntegerRanges = getEntityIntegerRangesForUpdate(entityAttribute);
        
        entityIntegerRanges.forEach((entityIntegerRange) ->
                deleteEntityIntegerRange(entityIntegerRange, false, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Integer Range Descriptions
    // --------------------------------------------------------------------------------
    
    public EntityIntegerRangeDescription createEntityIntegerRangeDescription(EntityIntegerRange entityIntegerRange, Language language, String description, BasePK createdBy) {
        var entityIntegerRangeDescription = EntityIntegerRangeDescriptionFactory.getInstance().create(entityIntegerRange, language, description, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEvent(entityIntegerRange.getPrimaryKey(), EventTypes.MODIFY, entityIntegerRangeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return entityIntegerRangeDescription;
    }
    
    private EntityIntegerRangeDescription getEntityIntegerRangeDescription(EntityIntegerRange entityIntegerRange, Language language, EntityPermission entityPermission) {
        EntityIntegerRangeDescription entityIntegerRangeDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityintegerrangedescriptions " +
                        "WHERE enird_enir_entityintegerrangeid = ? AND enird_lang_languageid = ? AND enird_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityintegerrangedescriptions " +
                        "WHERE enird_enir_entityintegerrangeid = ? AND enird_lang_languageid = ? AND enird_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = EntityIntegerRangeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityIntegerRange.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityIntegerRangeDescription = EntityIntegerRangeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityIntegerRangeDescription;
    }
    
    public EntityIntegerRangeDescription getEntityIntegerRangeDescription(EntityIntegerRange entityIntegerRange, Language language) {
        return getEntityIntegerRangeDescription(entityIntegerRange, language, EntityPermission.READ_ONLY);
    }
    
    public EntityIntegerRangeDescription getEntityIntegerRangeDescriptionForUpdate(EntityIntegerRange entityIntegerRange, Language language) {
        return getEntityIntegerRangeDescription(entityIntegerRange, language, EntityPermission.READ_WRITE);
    }
    
    public EntityIntegerRangeDescriptionValue getEntityIntegerRangeDescriptionValue(EntityIntegerRangeDescription entityIntegerRangeDescription) {
        return entityIntegerRangeDescription == null? null: entityIntegerRangeDescription.getEntityIntegerRangeDescriptionValue().clone();
    }
    
    public EntityIntegerRangeDescriptionValue getEntityIntegerRangeDescriptionValueForUpdate(EntityIntegerRange entityIntegerRange, Language language) {
        return getEntityIntegerRangeDescriptionValue(getEntityIntegerRangeDescriptionForUpdate(entityIntegerRange, language));
    }
    
    private List<EntityIntegerRangeDescription> getEntityIntegerRangeDescriptionsByEntityIntegerRange(EntityIntegerRange entityIntegerRange, EntityPermission entityPermission) {
        List<EntityIntegerRangeDescription> entityIntegerRangeDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityintegerrangedescriptions, languages " +
                        "WHERE enird_enir_entityintegerrangeid = ? AND enird_thrutime = ? AND enird_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityintegerrangedescriptions " +
                        "WHERE enird_enir_entityintegerrangeid = ? AND enird_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = EntityIntegerRangeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityIntegerRange.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityIntegerRangeDescriptions = EntityIntegerRangeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityIntegerRangeDescriptions;
    }
    
    public List<EntityIntegerRangeDescription> getEntityIntegerRangeDescriptionsByEntityIntegerRange(EntityIntegerRange entityIntegerRange) {
        return getEntityIntegerRangeDescriptionsByEntityIntegerRange(entityIntegerRange, EntityPermission.READ_ONLY);
    }
    
    public List<EntityIntegerRangeDescription> getEntityIntegerRangeDescriptionsByEntityIntegerRangeForUpdate(EntityIntegerRange entityIntegerRange) {
        return getEntityIntegerRangeDescriptionsByEntityIntegerRange(entityIntegerRange, EntityPermission.READ_WRITE);
    }
    
    public String getBestEntityIntegerRangeDescription(EntityIntegerRange entityIntegerRange, Language language) {
        String description;
        var entityIntegerRangeDescription = getEntityIntegerRangeDescription(entityIntegerRange, language);
        
        if(entityIntegerRangeDescription == null && !language.getIsDefault()) {
            entityIntegerRangeDescription = getEntityIntegerRangeDescription(entityIntegerRange, getPartyControl().getDefaultLanguage());
        }
        
        if(entityIntegerRangeDescription == null) {
            description = entityIntegerRange.getLastDetail().getEntityIntegerRangeName();
        } else {
            description = entityIntegerRangeDescription.getDescription();
        }
        
        return description;
    }
    
    public EntityIntegerRangeDescriptionTransfer getEntityIntegerRangeDescriptionTransfer(UserVisit userVisit, EntityIntegerRangeDescription entityIntegerRangeDescription, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityIntegerRangeDescriptionTransferCache().getEntityIntegerRangeDescriptionTransfer(entityIntegerRangeDescription, entityInstance);
    }
    
    public List<EntityIntegerRangeDescriptionTransfer> getEntityIntegerRangeDescriptionTransfersByEntityIntegerRange(UserVisit userVisit, EntityIntegerRange entityIntegerRange, EntityInstance entityInstance) {
        var entityIntegerRangeDescriptions = getEntityIntegerRangeDescriptionsByEntityIntegerRange(entityIntegerRange);
        List<EntityIntegerRangeDescriptionTransfer> entityIntegerRangeDescriptionTransfers = new ArrayList<>(entityIntegerRangeDescriptions.size());
        var entityIntegerRangeDescriptionTransferCache = getCoreTransferCaches(userVisit).getEntityIntegerRangeDescriptionTransferCache();
        
        entityIntegerRangeDescriptions.forEach((entityIntegerRangeDescription) ->
                entityIntegerRangeDescriptionTransfers.add(entityIntegerRangeDescriptionTransferCache.getEntityIntegerRangeDescriptionTransfer(entityIntegerRangeDescription, entityInstance))
        );
        
        return entityIntegerRangeDescriptionTransfers;
    }
    
    public void updateEntityIntegerRangeDescriptionFromValue(EntityIntegerRangeDescriptionValue entityIntegerRangeDescriptionValue, BasePK updatedBy) {
        if(entityIntegerRangeDescriptionValue.hasBeenModified()) {
            var entityIntegerRangeDescription = EntityIntegerRangeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, entityIntegerRangeDescriptionValue.getPrimaryKey());
            
            entityIntegerRangeDescription.setThruTime(session.START_TIME_LONG);
            entityIntegerRangeDescription.store();

            var entityIntegerRange = entityIntegerRangeDescription.getEntityIntegerRange();
            var language = entityIntegerRangeDescription.getLanguage();
            var description = entityIntegerRangeDescriptionValue.getDescription();
            
            entityIntegerRangeDescription = EntityIntegerRangeDescriptionFactory.getInstance().create(entityIntegerRange, language,
                    description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(entityIntegerRange.getPrimaryKey(), EventTypes.MODIFY, entityIntegerRangeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityIntegerRangeDescription(EntityIntegerRangeDescription entityIntegerRangeDescription, BasePK deletedBy) {
        entityIntegerRangeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(entityIntegerRangeDescription.getEntityIntegerRangePK(), EventTypes.MODIFY, entityIntegerRangeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteEntityIntegerRangeDescriptionsByEntityIntegerRange(EntityIntegerRange entityIntegerRange, BasePK deletedBy) {
        var entityIntegerRangeDescriptions = getEntityIntegerRangeDescriptionsByEntityIntegerRangeForUpdate(entityIntegerRange);
        
        entityIntegerRangeDescriptions.forEach((entityIntegerRangeDescription) -> 
                deleteEntityIntegerRangeDescription(entityIntegerRangeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Long Ranges
    // --------------------------------------------------------------------------------
    
    public EntityLongRange createEntityLongRange(EntityAttribute entityAttribute, String entityLongRangeName, Long minimumLongValue, Long maximumLongValue,
            Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultEntityLongRange = getDefaultEntityLongRange(entityAttribute);
        var defaultFound = defaultEntityLongRange != null;
        
        if(defaultFound && isDefault) {
            var defaultEntityLongRangeDetailValue = getDefaultEntityLongRangeDetailValueForUpdate(entityAttribute);
            
            defaultEntityLongRangeDetailValue.setIsDefault(Boolean.FALSE);
            updateEntityLongRangeFromValue(defaultEntityLongRangeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        var entityLongRange = EntityLongRangeFactory.getInstance().create();
        var entityLongRangeDetail = EntityLongRangeDetailFactory.getInstance().create(entityLongRange, entityAttribute, entityLongRangeName,
                minimumLongValue, maximumLongValue, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        entityLongRange = EntityLongRangeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, entityLongRange.getPrimaryKey());
        entityLongRange.setActiveDetail(entityLongRangeDetail);
        entityLongRange.setLastDetail(entityLongRangeDetail);
        entityLongRange.store();
        
        sendEvent(entityLongRange.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return entityLongRange;
    }
    
    /** Assume that the entityInstance passed to this function is a ECHO_THREE.EntityLongRange */
    public EntityLongRange getEntityLongRangeByEntityInstance(EntityInstance entityInstance) {
        var pk = new EntityLongRangePK(entityInstance.getEntityUniqueId());
        var entityLongRange = EntityLongRangeFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);
        
        return entityLongRange;
    }
    
    private EntityLongRange getDefaultEntityLongRange(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        EntityLongRange entityLongRange;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylongranges, entitylongrangedetails " +
                        "WHERE enlr_activedetailid = enlrdt_entitylongrangedetailid " +
                        "AND enlrdt_ena_entityattributeid = ? AND enlrdt_isdefault = 1";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylongranges, entitylongrangedetails " +
                        "WHERE enlr_activedetailid = enlrdt_entitylongrangedetailid " +
                        "AND enlrdt_ena_entityattributeid = ? AND enlrdt_isdefault = 1 " +
                        "FOR UPDATE";
            }

            var ps = EntityLongRangeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            
            entityLongRange = EntityLongRangeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityLongRange;
    }
    
    public EntityLongRange getDefaultEntityLongRange(EntityAttribute entityAttribute) {
        return getDefaultEntityLongRange(entityAttribute, EntityPermission.READ_ONLY);
    }
    
    public EntityLongRange getDefaultEntityLongRangeForUpdate(EntityAttribute entityAttribute) {
        return getDefaultEntityLongRange(entityAttribute, EntityPermission.READ_WRITE);
    }
    
    public EntityLongRangeDetailValue getDefaultEntityLongRangeDetailValueForUpdate(EntityAttribute entityAttribute) {
        return getDefaultEntityLongRangeForUpdate(entityAttribute).getLastDetailForUpdate().getEntityLongRangeDetailValue().clone();
    }
    
    private EntityLongRange getEntityLongRangeByName(EntityAttribute entityAttribute, String entityLongRangeName, EntityPermission entityPermission) {
        EntityLongRange entityLongRange;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylongranges, entitylongrangedetails " +
                        "WHERE enlr_activedetailid = enlrdt_entitylongrangedetailid " +
                        "AND enlrdt_ena_entityattributeid = ? AND enlrdt_entitylongrangename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylongranges, entitylongrangedetails " +
                        "WHERE enlr_activedetailid = enlrdt_entitylongrangedetailid " +
                        "AND enlrdt_ena_entityattributeid = ? AND enlrdt_entitylongrangename = ? " +
                        "FOR UPDATE";
            }

            var ps = EntityLongRangeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setString(2, entityLongRangeName);
            
            entityLongRange = EntityLongRangeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityLongRange;
    }
    
    public EntityLongRange getEntityLongRangeByName(EntityAttribute entityAttribute, String entityLongRangeName) {
        return getEntityLongRangeByName(entityAttribute, entityLongRangeName, EntityPermission.READ_ONLY);
    }
    
    public EntityLongRange getEntityLongRangeByNameForUpdate(EntityAttribute entityAttribute, String entityLongRangeName) {
        return getEntityLongRangeByName(entityAttribute, entityLongRangeName, EntityPermission.READ_WRITE);
    }
    
    public EntityLongRangeDetailValue getEntityLongRangeDetailValueForUpdate(EntityLongRange entityLongRange) {
        return entityLongRange == null? null: entityLongRange.getLastDetailForUpdate().getEntityLongRangeDetailValue().clone();
    }
    
    public EntityLongRangeDetailValue getEntityLongRangeDetailValueByNameForUpdate(EntityAttribute entityAttribute, String entityLongRangeName) {
        return getEntityLongRangeDetailValueForUpdate(getEntityLongRangeByNameForUpdate(entityAttribute, entityLongRangeName));
    }
    
    private List<EntityLongRange> getEntityLongRanges(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        List<EntityLongRange> entityLongRanges;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylongranges, entitylongrangedetails " +
                        "WHERE enlr_activedetailid = enlrdt_entitylongrangedetailid AND enlrdt_ena_entityattributeid = ? " +
                        "ORDER BY enlrdt_sortorder, enlrdt_entitylongrangename " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylongranges, entitylongrangedetails " +
                        "WHERE enlr_activedetailid = enlrdt_entitylongrangedetailid AND enlrdt_ena_entityattributeid = ? " +
                        "FOR UPDATE";
            }

            var ps = EntityLongRangeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            
            entityLongRanges = EntityLongRangeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityLongRanges;
    }
    
    public List<EntityLongRange> getEntityLongRanges(EntityAttribute entityAttribute) {
        return getEntityLongRanges(entityAttribute, EntityPermission.READ_ONLY);
    }
    
    public List<EntityLongRange> getEntityLongRangesForUpdate(EntityAttribute entityAttribute) {
        return getEntityLongRanges(entityAttribute, EntityPermission.READ_WRITE);
    }

    public long countEntityLongRanges(EntityAttribute entityAttribute) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM entitylongranges, entitylongrangedetails " +
                "WHERE enlr_activedetailid = enlrdt_entitylongrangedetailid AND enlrdt_ena_entityattributeid = ?",
                entityAttribute);
    }

    public EntityLongRangeTransfer getEntityLongRangeTransfer(UserVisit userVisit, EntityLongRange entityLongRange, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityLongRangeTransferCache().getEntityLongRangeTransfer(entityLongRange, entityInstance);
    }

    public List<EntityLongRangeTransfer> getEntityLongRangeTransfers(UserVisit userVisit, Collection<EntityLongRange> entityLongRanges, EntityInstance entityInstance) {
        List<EntityLongRangeTransfer> entityLongRangeTransfers = new ArrayList<>(entityLongRanges.size());
        var entityLongRangeTransferCache = getCoreTransferCaches(userVisit).getEntityLongRangeTransferCache();

        entityLongRanges.forEach((entityLongRange) ->
                entityLongRangeTransfers.add(entityLongRangeTransferCache.getEntityLongRangeTransfer(entityLongRange, entityInstance))
        );

        return entityLongRangeTransfers;
    }

    public List<EntityLongRangeTransfer> getEntityLongRangeTransfersByEntityAttribute(UserVisit userVisit, EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityLongRangeTransfers(userVisit, getEntityLongRanges(entityAttribute), entityInstance);
    }

    private void updateEntityLongRangeFromValue(EntityLongRangeDetailValue entityLongRangeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(entityLongRangeDetailValue.hasBeenModified()) {
            var entityLongRange = EntityLongRangeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     entityLongRangeDetailValue.getEntityLongRangePK());
            var entityLongRangeDetail = entityLongRange.getActiveDetailForUpdate();
            
            entityLongRangeDetail.setThruTime(session.START_TIME_LONG);
            entityLongRangeDetail.store();

            var entityLongRangePK = entityLongRangeDetail.getEntityLongRangePK(); // Not updated
            var entityAttribute = entityLongRangeDetail.getEntityAttribute(); // Not updated
            var entityLongRangeName = entityLongRangeDetailValue.getEntityLongRangeName();
            var minimumLongValue = entityLongRangeDetailValue.getMinimumLongValue();
            var maximumLongValue = entityLongRangeDetailValue.getMaximumLongValue();
            var isDefault = entityLongRangeDetailValue.getIsDefault();
            var sortOrder = entityLongRangeDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultEntityLongRange = getDefaultEntityLongRange(entityAttribute);
                var defaultFound = defaultEntityLongRange != null && !defaultEntityLongRange.equals(entityLongRange);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultEntityLongRangeDetailValue = getDefaultEntityLongRangeDetailValueForUpdate(entityAttribute);
                    
                    defaultEntityLongRangeDetailValue.setIsDefault(Boolean.FALSE);
                    updateEntityLongRangeFromValue(defaultEntityLongRangeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            entityLongRangeDetail = EntityLongRangeDetailFactory.getInstance().create(entityLongRangePK, entityAttribute.getPrimaryKey(), entityLongRangeName,
                    minimumLongValue, maximumLongValue, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            entityLongRange.setActiveDetail(entityLongRangeDetail);
            entityLongRange.setLastDetail(entityLongRangeDetail);
            
            sendEvent(entityLongRangePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateEntityLongRangeFromValue(EntityLongRangeDetailValue entityLongRangeDetailValue, BasePK updatedBy) {
        updateEntityLongRangeFromValue(entityLongRangeDetailValue, true, updatedBy);
    }
    
    public EntityLongRangeChoicesBean getEntityLongRangeChoices(String defaultEntityLongRangeChoice, Language language,
            boolean allowNullChoice, EntityAttribute entityAttribute) {
        var entityLongRanges = getEntityLongRanges(entityAttribute);
        var size = entityLongRanges.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultEntityLongRangeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var entityLongRange : entityLongRanges) {
            var entityLongRangeDetail = entityLongRange.getLastDetail();
            var label = getBestEntityLongRangeDescription(entityLongRange, language);
            var value = entityLongRangeDetail.getEntityLongRangeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultEntityLongRangeChoice != null && defaultEntityLongRangeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && entityLongRangeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new EntityLongRangeChoicesBean(labels, values, defaultValue);
    }
    
    private void deleteEntityLongRange(EntityLongRange entityLongRange, boolean checkDefault, BasePK deletedBy) {
        var entityLongRangeDetail = entityLongRange.getLastDetailForUpdate();
        
        deleteEntityLongRangeDescriptionsByEntityLongRange(entityLongRange, deletedBy);
        
        entityLongRangeDetail.setThruTime(session.START_TIME_LONG);
        entityLongRange.setActiveDetail(null);
        entityLongRange.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var entityAttribute = entityLongRangeDetail.getEntityAttribute();
            var defaultEntityLongRange = getDefaultEntityLongRange(entityAttribute);
            if(defaultEntityLongRange == null) {
                var entityLongRanges = getEntityLongRangesForUpdate(entityAttribute);

                if(!entityLongRanges.isEmpty()) {
                    var iter = entityLongRanges.iterator();
                    if(iter.hasNext()) {
                        defaultEntityLongRange = iter.next();
                    }
                    var entityLongRangeDetailValue = Objects.requireNonNull(defaultEntityLongRange).getLastDetailForUpdate().getEntityLongRangeDetailValue().clone();

                    entityLongRangeDetailValue.setIsDefault(Boolean.TRUE);
                    updateEntityLongRangeFromValue(entityLongRangeDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(entityLongRange.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteEntityLongRange(EntityLongRange entityLongRange, BasePK deletedBy) {
        deleteEntityLongRange(entityLongRange, true, deletedBy);
    }

    public void deleteEntityLongRangesByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        var entityLongRanges = getEntityLongRangesForUpdate(entityAttribute);
        
        entityLongRanges.forEach((entityLongRange) ->
                deleteEntityLongRange(entityLongRange, false, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Long Range Descriptions
    // --------------------------------------------------------------------------------
    
    public EntityLongRangeDescription createEntityLongRangeDescription(EntityLongRange entityLongRange, Language language, String description, BasePK createdBy) {
        var entityLongRangeDescription = EntityLongRangeDescriptionFactory.getInstance().create(entityLongRange, language, description, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEvent(entityLongRange.getPrimaryKey(), EventTypes.MODIFY, entityLongRangeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return entityLongRangeDescription;
    }
    
    private EntityLongRangeDescription getEntityLongRangeDescription(EntityLongRange entityLongRange, Language language, EntityPermission entityPermission) {
        EntityLongRangeDescription entityLongRangeDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylongrangedescriptions " +
                        "WHERE enlrd_enlr_entitylongrangeid = ? AND enlrd_lang_languageid = ? AND enlrd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylongrangedescriptions " +
                        "WHERE enlrd_enlr_entitylongrangeid = ? AND enlrd_lang_languageid = ? AND enlrd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = EntityLongRangeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityLongRange.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityLongRangeDescription = EntityLongRangeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityLongRangeDescription;
    }
    
    public EntityLongRangeDescription getEntityLongRangeDescription(EntityLongRange entityLongRange, Language language) {
        return getEntityLongRangeDescription(entityLongRange, language, EntityPermission.READ_ONLY);
    }
    
    public EntityLongRangeDescription getEntityLongRangeDescriptionForUpdate(EntityLongRange entityLongRange, Language language) {
        return getEntityLongRangeDescription(entityLongRange, language, EntityPermission.READ_WRITE);
    }
    
    public EntityLongRangeDescriptionValue getEntityLongRangeDescriptionValue(EntityLongRangeDescription entityLongRangeDescription) {
        return entityLongRangeDescription == null? null: entityLongRangeDescription.getEntityLongRangeDescriptionValue().clone();
    }
    
    public EntityLongRangeDescriptionValue getEntityLongRangeDescriptionValueForUpdate(EntityLongRange entityLongRange, Language language) {
        return getEntityLongRangeDescriptionValue(getEntityLongRangeDescriptionForUpdate(entityLongRange, language));
    }
    
    private List<EntityLongRangeDescription> getEntityLongRangeDescriptionsByEntityLongRange(EntityLongRange entityLongRange, EntityPermission entityPermission) {
        List<EntityLongRangeDescription> entityLongRangeDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylongrangedescriptions, languages " +
                        "WHERE enlrd_enlr_entitylongrangeid = ? AND enlrd_thrutime = ? AND enlrd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylongrangedescriptions " +
                        "WHERE enlrd_enlr_entitylongrangeid = ? AND enlrd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = EntityLongRangeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityLongRange.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityLongRangeDescriptions = EntityLongRangeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityLongRangeDescriptions;
    }
    
    public List<EntityLongRangeDescription> getEntityLongRangeDescriptionsByEntityLongRange(EntityLongRange entityLongRange) {
        return getEntityLongRangeDescriptionsByEntityLongRange(entityLongRange, EntityPermission.READ_ONLY);
    }
    
    public List<EntityLongRangeDescription> getEntityLongRangeDescriptionsByEntityLongRangeForUpdate(EntityLongRange entityLongRange) {
        return getEntityLongRangeDescriptionsByEntityLongRange(entityLongRange, EntityPermission.READ_WRITE);
    }
    
    public String getBestEntityLongRangeDescription(EntityLongRange entityLongRange, Language language) {
        String description;
        var entityLongRangeDescription = getEntityLongRangeDescription(entityLongRange, language);
        
        if(entityLongRangeDescription == null && !language.getIsDefault()) {
            entityLongRangeDescription = getEntityLongRangeDescription(entityLongRange, getPartyControl().getDefaultLanguage());
        }
        
        if(entityLongRangeDescription == null) {
            description = entityLongRange.getLastDetail().getEntityLongRangeName();
        } else {
            description = entityLongRangeDescription.getDescription();
        }
        
        return description;
    }
    
    public EntityLongRangeDescriptionTransfer getEntityLongRangeDescriptionTransfer(UserVisit userVisit, EntityLongRangeDescription entityLongRangeDescription, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityLongRangeDescriptionTransferCache().getEntityLongRangeDescriptionTransfer(entityLongRangeDescription, entityInstance);
    }
    
    public List<EntityLongRangeDescriptionTransfer> getEntityLongRangeDescriptionTransfersByEntityLongRange(UserVisit userVisit, EntityLongRange entityLongRange, EntityInstance entityInstance) {
        var entityLongRangeDescriptions = getEntityLongRangeDescriptionsByEntityLongRange(entityLongRange);
        List<EntityLongRangeDescriptionTransfer> entityLongRangeDescriptionTransfers = new ArrayList<>(entityLongRangeDescriptions.size());
        var entityLongRangeDescriptionTransferCache = getCoreTransferCaches(userVisit).getEntityLongRangeDescriptionTransferCache();
        
        entityLongRangeDescriptions.forEach((entityLongRangeDescription) ->
                entityLongRangeDescriptionTransfers.add(entityLongRangeDescriptionTransferCache.getEntityLongRangeDescriptionTransfer(entityLongRangeDescription, entityInstance))
        );
        
        return entityLongRangeDescriptionTransfers;
    }
    
    public void updateEntityLongRangeDescriptionFromValue(EntityLongRangeDescriptionValue entityLongRangeDescriptionValue, BasePK updatedBy) {
        if(entityLongRangeDescriptionValue.hasBeenModified()) {
            var entityLongRangeDescription = EntityLongRangeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, entityLongRangeDescriptionValue.getPrimaryKey());
            
            entityLongRangeDescription.setThruTime(session.START_TIME_LONG);
            entityLongRangeDescription.store();

            var entityLongRange = entityLongRangeDescription.getEntityLongRange();
            var language = entityLongRangeDescription.getLanguage();
            var description = entityLongRangeDescriptionValue.getDescription();
            
            entityLongRangeDescription = EntityLongRangeDescriptionFactory.getInstance().create(entityLongRange, language,
                    description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(entityLongRange.getPrimaryKey(), EventTypes.MODIFY, entityLongRangeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityLongRangeDescription(EntityLongRangeDescription entityLongRangeDescription, BasePK deletedBy) {
        entityLongRangeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(entityLongRangeDescription.getEntityLongRangePK(), EventTypes.MODIFY, entityLongRangeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteEntityLongRangeDescriptionsByEntityLongRange(EntityLongRange entityLongRange, BasePK deletedBy) {
        var entityLongRangeDescriptions = getEntityLongRangeDescriptionsByEntityLongRangeForUpdate(entityLongRange);
        
        entityLongRangeDescriptions.forEach((entityLongRangeDescription) -> 
                deleteEntityLongRangeDescription(entityLongRangeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Components
    // --------------------------------------------------------------------------------
    
    public Component createComponent(ComponentVendor componentVendor, String componentName, String description, BasePK createdBy) {
        var component = ComponentFactory.getInstance().create();
        var componentDetail = ComponentDetailFactory.getInstance().create(componentVendor, component,
                componentName, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        component = ComponentFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, component.getPrimaryKey());
        component.setActiveDetail(componentDetail);
        component.setLastDetail(componentDetail);
        component.store();
        
        return component;
    }
    
    public Component getComponentByName(ComponentVendor componentVendor, String componentName) {
        Component component;
        
        try {
            var ps = ComponentFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM components, componentdetails " +
                    "WHERE cpnt_componentid = cpntd_cpnt_componentid AND cpntd_cvnd_componentvendorid = ? " +
                    "AND cpntd_componentname = ? AND cpntd_thrutime = ?");
            
            ps.setLong(1, componentVendor.getPrimaryKey().getEntityId());
            ps.setString(2, componentName);
            ps.setLong(3, Session.MAX_TIME);
            
            component = ComponentFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return component;
    }
    
    // --------------------------------------------------------------------------------
    //   Component Versions
    // --------------------------------------------------------------------------------
    
    public ComponentVersion createComponentVersion(Component component, Integer majorRevision, Integer minorRevision,
            ComponentStage componentStage, Integer buildNumber,
            BasePK createdBy) {
        
        return ComponentVersionFactory.getInstance().create(component, majorRevision, minorRevision, componentStage,
                buildNumber, session.START_TIME_LONG, Session.MAX_TIME_LONG);
    }
    
    public ComponentVersion getComponentVersion(Component component) {
        ComponentVersion componentVersion;
        
        try {
            var ps = ComponentVersionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM componentversions " +
                    "WHERE cvrs_cpnt_componentid = ? AND cvrs_thrutime = ?");
            
            ps.setLong(1, component.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            componentVersion = ComponentVersionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return componentVersion;
    }
    
    // --------------------------------------------------------------------------------
    //   Mime Type Usage Types
    // --------------------------------------------------------------------------------
    
    public MimeTypeUsageType createMimeTypeUsageType(String mimeTypeUsageTypeName, Boolean isDefault, Integer sortOrder) {
        return MimeTypeUsageTypeFactory.getInstance().create(mimeTypeUsageTypeName, isDefault, sortOrder);
    }
    
    public List<MimeTypeUsageType> getMimeTypeUsageTypes() {
        var ps = MimeTypeUsageTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM mimetypeusagetypes " +
                "ORDER BY mtyput_sortorder, mtyput_mimetypeusagetypename");
        
        return MimeTypeUsageTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public MimeTypeUsageType getMimeTypeUsageTypeByName(String mimeTypeUsageTypeName) {
        MimeTypeUsageType mimeTypeUsageType;
        
        try {
            var ps = MimeTypeUsageTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM mimetypeusagetypes " +
                    "WHERE mtyput_mimetypeusagetypename = ?");
            
            ps.setString(1, mimeTypeUsageTypeName);
            
            mimeTypeUsageType = MimeTypeUsageTypeFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return mimeTypeUsageType;
    }
    
    public MimeTypeUsageTypeChoicesBean getMimeTypeUsageTypeChoices(String defaultMimeTypeUsageTypeChoice, Language language,
            boolean allowNullChoice) {
        var mimeTypeUsageTypes = getMimeTypeUsageTypes();
        var size = mimeTypeUsageTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultMimeTypeUsageTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var mimeTypeUsageType : mimeTypeUsageTypes) {
            var label = getBestMimeTypeUsageTypeDescription(mimeTypeUsageType, language);
            var value = mimeTypeUsageType.getMimeTypeUsageTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultMimeTypeUsageTypeChoice != null && defaultMimeTypeUsageTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && mimeTypeUsageType.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new MimeTypeUsageTypeChoicesBean(labels, values, defaultValue);
    }
    
    public MimeTypeUsageTypeTransfer getMimeTypeUsageTypeTransfer(UserVisit userVisit, MimeTypeUsageType mimeTypeUsageType) {
        return getCoreTransferCaches(userVisit).getMimeTypeUsageTypeTransferCache().getMimeTypeUsageTypeTransfer(mimeTypeUsageType);
    }

    public List<MimeTypeUsageTypeTransfer> getMimeTypeUsageTypeTransfers(UserVisit userVisit, Collection<MimeTypeUsageType> mimeTypeUsageTypes) {
        List<MimeTypeUsageTypeTransfer> mimeTypeUsageTypeTransfers = new ArrayList<>(mimeTypeUsageTypes.size());
        var mimeTypeUsageTypeTransferCache = getCoreTransferCaches(userVisit).getMimeTypeUsageTypeTransferCache();

        mimeTypeUsageTypes.forEach((mimeTypeUsageType) ->
                mimeTypeUsageTypeTransfers.add(mimeTypeUsageTypeTransferCache.getMimeTypeUsageTypeTransfer(mimeTypeUsageType))
        );

        return mimeTypeUsageTypeTransfers;
    }

    public List<MimeTypeUsageTypeTransfer> getMimeTypeUsageTypeTransfers(UserVisit userVisit) {
        return getMimeTypeUsageTypeTransfers(userVisit, getMimeTypeUsageTypes());
    }

    // --------------------------------------------------------------------------------
    //   Mime Type Usage Type Descriptions
    // --------------------------------------------------------------------------------
    
    public MimeTypeUsageTypeDescription createMimeTypeUsageTypeDescription(MimeTypeUsageType mimeTypeUsageType, Language language, String description) {
        return MimeTypeUsageTypeDescriptionFactory.getInstance().create(mimeTypeUsageType, language, description);
    }
    
    public MimeTypeUsageTypeDescription getMimeTypeUsageTypeDescription(MimeTypeUsageType mimeTypeUsageType, Language language) {
        MimeTypeUsageTypeDescription mimeTypeUsageTypeDescription;
        
        try {
            var ps = MimeTypeUsageTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM mimetypeusagetypedescriptions " +
                    "WHERE mtyputd_mtyput_mimetypeusagetypeid = ? AND mtyputd_lang_languageid = ?");
            
            ps.setLong(1, mimeTypeUsageType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            mimeTypeUsageTypeDescription = MimeTypeUsageTypeDescriptionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return mimeTypeUsageTypeDescription;
    }
    
    public String getBestMimeTypeUsageTypeDescription(MimeTypeUsageType mimeTypeUsageType, Language language) {
        String description;
        var mimeTypeUsageTypeDescription = getMimeTypeUsageTypeDescription(mimeTypeUsageType, language);
        
        if(mimeTypeUsageTypeDescription == null && !language.getIsDefault()) {
            mimeTypeUsageTypeDescription = getMimeTypeUsageTypeDescription(mimeTypeUsageType, getPartyControl().getDefaultLanguage());
        }
        
        if(mimeTypeUsageTypeDescription == null) {
            description = mimeTypeUsageType.getMimeTypeUsageTypeName();
        } else {
            description = mimeTypeUsageTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Mime Types
    // --------------------------------------------------------------------------------

    public MimeType createMimeType(String mimeTypeName, EntityAttributeType entityAttributeType, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultMimeType = getDefaultMimeType();
        var defaultFound = defaultMimeType != null;

        if(defaultFound && isDefault) {
            var defaultMimeTypeDetailValue = getDefaultMimeTypeDetailValueForUpdate();

            defaultMimeTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateMimeTypeFromValue(defaultMimeTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        var mimeType = MimeTypeFactory.getInstance().create();
        var mimeTypeDetail = MimeTypeDetailFactory.getInstance().create(mimeType, mimeTypeName, entityAttributeType, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        mimeType = MimeTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                mimeType.getPrimaryKey());
        mimeType.setActiveDetail(mimeTypeDetail);
        mimeType.setLastDetail(mimeTypeDetail);
        mimeType.store();

        sendEvent(mimeType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return mimeType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.MimeType */
    public MimeType getMimeTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new MimeTypePK(entityInstance.getEntityUniqueId());

        return MimeTypeFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public MimeType getMimeTypeByEntityInstance(EntityInstance entityInstance) {
        return getMimeTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public MimeType getMimeTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getMimeTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countMimeTypes() {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM mimetypes
                JOIN mimetypedetails ON mtyp_activedetailid = mtypdt_mimetypedetailid
                """);
    }

    public long countMimeTypesByMimeTypeUsageType(MimeTypeUsageType mimeTypeUsageType) {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM mimetypes
                JOIN mimetypedetails ON mtyp_activedetailid = mtypdt_mimetypedetailid
                JOIN mimetypeusages ON mtyp_mimetypeid = mtypu_mtyp_mimetypeid
                WHERE mtypu_mtyput_mimetypeusagetypeid = ?
                """, mimeTypeUsageType);
    }

    private static final Map<EntityPermission, String> getMimeTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM mimetypes, mimetypedetails " +
                "WHERE mtyp_activedetailid = mtypdt_mimetypedetailid " +
                "AND mtypdt_mimetypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM mimetypes, mimetypedetails " +
                "WHERE mtyp_activedetailid = mtypdt_mimetypedetailid " +
                "AND mtypdt_mimetypename = ? " +
                "FOR UPDATE");
        getMimeTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private MimeType getMimeTypeByName(String mimeTypeName, EntityPermission entityPermission) {
        return MimeTypeFactory.getInstance().getEntityFromQuery(entityPermission, getMimeTypeByNameQueries, mimeTypeName);
    }

    public MimeType getMimeTypeByName(String mimeTypeName) {
        return getMimeTypeByName(mimeTypeName, EntityPermission.READ_ONLY);
    }

    public MimeType getMimeTypeByNameForUpdate(String mimeTypeName) {
        return getMimeTypeByName(mimeTypeName, EntityPermission.READ_WRITE);
    }

    public MimeTypeDetailValue getMimeTypeDetailValueForUpdate(MimeType mimeType) {
        return mimeType == null? null: mimeType.getLastDetailForUpdate().getMimeTypeDetailValue().clone();
    }

    public MimeTypeDetailValue getMimeTypeDetailValueByNameForUpdate(String mimeTypeName) {
        return getMimeTypeDetailValueForUpdate(getMimeTypeByNameForUpdate(mimeTypeName));
    }

    private static final Map<EntityPermission, String> getDefaultMimeTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM mimetypes, mimetypedetails "
                + "WHERE mtyp_activedetailid = mtypdt_mimetypedetailid "
                + "AND mtypdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM mimetypes, mimetypedetails "
                + "WHERE mtyp_activedetailid = mtypdt_mimetypedetailid "
                + "AND mtypdt_isdefault = 1 "
                + "FOR UPDATE");
        getDefaultMimeTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private MimeType getDefaultMimeType(EntityPermission entityPermission) {
        return MimeTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultMimeTypeQueries);
    }

    public MimeType getDefaultMimeType() {
        return getDefaultMimeType(EntityPermission.READ_ONLY);
    }

    public MimeType getDefaultMimeTypeForUpdate() {
        return getDefaultMimeType(EntityPermission.READ_WRITE);
    }

    public MimeTypeDetailValue getDefaultMimeTypeDetailValueForUpdate() {
        return getDefaultMimeTypeForUpdate().getLastDetailForUpdate().getMimeTypeDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getMimeTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM mimetypes, mimetypedetails "
                + "WHERE mtyp_activedetailid = mtypdt_mimetypedetailid "
                + "ORDER BY mtypdt_sortorder, mtypdt_mimetypename "
                + "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM mimetypes, mimetypedetails "
                + "WHERE mtyp_activedetailid = mtypdt_mimetypedetailid "
                + "FOR UPDATE");
        getMimeTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<MimeType> getMimeTypes(EntityPermission entityPermission) {
        return MimeTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getMimeTypesQueries);
    }

    public List<MimeType> getMimeTypes() {
        return getMimeTypes(EntityPermission.READ_ONLY);
    }

    public List<MimeType> getMimeTypesForUpdate() {
        return getMimeTypes(EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getMimeTypesByEntityAttributeTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM mimetypes, mimetypedetails "
                + "WHERE mtyp_activedetailid = mtypdt_mimetypedetailid "
                + "AND mtypdt_enat_entityattributetypeid = ? "
                + "ORDER BY mtypdt_sortorder, mtypdt_mimetypename "
                + "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM mimetypes, mimetypedetails "
                + "WHERE mtyp_activedetailid = mtypdt_mimetypedetailid "
                + "AND mtypdt_enat_entityattributetypeid = ?"
                + "FOR UPDATE");
        getMimeTypesByEntityAttributeTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<MimeType> getMimeTypesByEntityAttributeType(EntityAttributeType entityAttributeType, EntityPermission entityPermission) {
        return MimeTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getMimeTypesByEntityAttributeTypeQueries,
                entityAttributeType);
    }

    public List<MimeType> getMimeTypesByEntityAttributeType(EntityAttributeType entityAttributeType) {
        return getMimeTypesByEntityAttributeType(entityAttributeType, EntityPermission.READ_ONLY);
    }

    public List<MimeType> getMimeTypesByEntityAttributeTypeForUpdate(EntityAttributeType entityAttributeType) {
        return getMimeTypesByEntityAttributeType(entityAttributeType, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getMimeTypesByMimeTypeUsageTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM mimetypes, mimetypedetails, mimetypeusages "
                + "WHERE mtyp_activedetailid = mtypdt_mimetypedetailid "
                + "AND mtyp_mimetypeid = mtypu_mtyp_mimetypeid AND mtypu_mtyput_mimetypeusagetypeid = ? "
                + "ORDER BY mtypdt_sortorder, mtypdt_mimetypename "
                + "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM mimetypes, mimetypedetails, mimetypeusages "
                + "WHERE mtyp_activedetailid = mtypdt_mimetypedetailid "
                + "AND mtyp_mimetypeid = mtypu_mtyp_mimetypeid AND mtypu_mtyput_mimetypeusagetypeid = ? "
                + "FOR UPDATE");
        getMimeTypesByMimeTypeUsageTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<MimeType> getMimeTypesByMimeTypeUsageType(MimeTypeUsageType mimeTypeUsageType, EntityPermission entityPermission) {
        return MimeTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getMimeTypesByMimeTypeUsageTypeQueries,
                mimeTypeUsageType);
    }

    public List<MimeType> getMimeTypesByMimeTypeUsageType(MimeTypeUsageType mimeTypeUsageType) {
        return getMimeTypesByMimeTypeUsageType(mimeTypeUsageType, EntityPermission.READ_ONLY);
    }

    public List<MimeType> getMimeTypesByMimeTypeUsageTypeForUpdate(MimeTypeUsageType mimeTypeUsageType) {
        return getMimeTypesByMimeTypeUsageType(mimeTypeUsageType, EntityPermission.READ_WRITE);
    }

    public MimeTypeTransfer getMimeTypeTransfer(UserVisit userVisit, MimeType mimeType) {
        return getCoreTransferCaches(userVisit).getMimeTypeTransferCache().getMimeTypeTransfer(mimeType);
    }

    public List<MimeTypeTransfer> getMimeTypeTransfers(UserVisit userVisit, Collection<MimeType> mimeTypes) {
        List<MimeTypeTransfer> mimeTypeTransfers = new ArrayList<>(mimeTypes.size());
        var mimeTypeTransferCache = getCoreTransferCaches(userVisit).getMimeTypeTransferCache();

        mimeTypes.forEach((mimeType) ->
                mimeTypeTransfers.add(mimeTypeTransferCache.getMimeTypeTransfer(mimeType))
        );

        return mimeTypeTransfers;
    }

    public List<MimeTypeTransfer> getMimeTypeTransfers(UserVisit userVisit) {
        return getMimeTypeTransfers(userVisit, getMimeTypes());
    }

    public List<MimeTypeTransfer> getMimeTypeTransfersByEntityAttributeType(UserVisit userVisit,
            EntityAttributeType entityAttributeType) {
        return getMimeTypeTransfers(userVisit, getMimeTypesByEntityAttributeType(entityAttributeType));
    }

    public List<MimeTypeTransfer> getMimeTypeTransfersByMimeTypeUsageType(UserVisit userVisit,
            MimeTypeUsageType mimeTypeUsageType) {
        return getMimeTypeTransfers(userVisit, getMimeTypesByMimeTypeUsageType(mimeTypeUsageType));
    }

    public MimeTypeChoicesBean getMimeTypeChoices(String defaultMimeTypeChoice, Language language, boolean allowNullChoice) {
        var mimeTypes = getMimeTypes();
        var size = mimeTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultMimeTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var mimeType : mimeTypes) {
            var mimeTypeDetail = mimeType.getLastDetail();

            var label = getBestMimeTypeDescription(mimeType, language);
            var value = mimeTypeDetail.getMimeTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultMimeTypeChoice != null && defaultMimeTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && mimeTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new MimeTypeChoicesBean(labels, values, defaultValue);
    }

    public MimeTypeChoicesBean getMimeTypeChoices(MimeTypeUsageType mimeTypeUsageType, String defaultMimeTypeChoice, Language language,
            boolean allowNullChoice) {
        var mimeTypes = getMimeTypesByMimeTypeUsageType(mimeTypeUsageType);
        var size = mimeTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultMimeTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var mimeType : mimeTypes) {
            var mimeTypeDetail = mimeType.getLastDetail();

            var label = getBestMimeTypeDescription(mimeType, language);
            var value = mimeTypeDetail.getMimeTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultMimeTypeChoice != null && defaultMimeTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && mimeTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new MimeTypeChoicesBean(labels, values, defaultValue);
    }

    private void updateMimeTypeFromValue(MimeTypeDetailValue mimeTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(mimeTypeDetailValue.hasBeenModified()) {
            var mimeType = MimeTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     mimeTypeDetailValue.getMimeTypePK());
            var mimeTypeDetail = mimeType.getActiveDetailForUpdate();

            mimeTypeDetail.setThruTime(session.START_TIME_LONG);
            mimeTypeDetail.store();

            var mimeTypePK = mimeTypeDetail.getMimeTypePK(); // Not updated
            var mimeTypeName = mimeTypeDetailValue.getMimeTypeName();
            var entityAttributeTypePK = mimeTypeDetail.getEntityAttributeTypePK(); // Not updated
            var isDefault = mimeTypeDetailValue.getIsDefault();
            var sortOrder = mimeTypeDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultMimeType = getDefaultMimeType();
                var defaultFound = defaultMimeType != null && !defaultMimeType.equals(mimeType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultMimeTypeDetailValue = getDefaultMimeTypeDetailValueForUpdate();

                    defaultMimeTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updateMimeTypeFromValue(defaultMimeTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            mimeTypeDetail = MimeTypeDetailFactory.getInstance().create(mimeTypePK, mimeTypeName, entityAttributeTypePK, isDefault, sortOrder,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            mimeType.setActiveDetail(mimeTypeDetail);
            mimeType.setLastDetail(mimeTypeDetail);

            sendEvent(mimeTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateMimeTypeFromValue(MimeTypeDetailValue mimeTypeDetailValue, BasePK updatedBy) {
        updateMimeTypeFromValue(mimeTypeDetailValue, true, updatedBy);
    }

    public void deleteMimeType(MimeType mimeType, BasePK deletedBy) {
        deleteMimeTypeDescriptionsByMimeType(mimeType, deletedBy);

        var mimeTypeDetail = mimeType.getLastDetailForUpdate();
        mimeTypeDetail.setThruTime(session.START_TIME_LONG);
        mimeType.setActiveDetail(null);
        mimeType.store();

        // Check for default, and pick one if necessary
        var defaultMimeType = getDefaultMimeType();
        if(defaultMimeType == null) {
            var mimeTypes = getMimeTypesForUpdate();

            if(!mimeTypes.isEmpty()) {
                var iter = mimeTypes.iterator();
                if(iter.hasNext()) {
                    defaultMimeType = iter.next();
                }
                var mimeTypeDetailValue = Objects.requireNonNull(defaultMimeType).getLastDetailForUpdate().getMimeTypeDetailValue().clone();

                mimeTypeDetailValue.setIsDefault(Boolean.TRUE);
                updateMimeTypeFromValue(mimeTypeDetailValue, false, deletedBy);
            }
        }

        sendEvent(mimeType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Mime Type Descriptions
    // --------------------------------------------------------------------------------

    public MimeTypeDescription createMimeTypeDescription(MimeType mimeType,
            Language language, String description, BasePK createdBy) {
        var mimeTypeDescription = MimeTypeDescriptionFactory.getInstance().create(mimeType,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(mimeType.getPrimaryKey(), EventTypes.MODIFY, mimeTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return mimeTypeDescription;
    }

    private static final Map<EntityPermission, String> getMimeTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM mimetypedescriptions "
                + "WHERE mtypd_mtyp_mimetypeid = ? AND mtypd_lang_languageid = ? AND mtypd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM mimetypedescriptions "
                + "WHERE mtypd_mtyp_mimetypeid = ? AND mtypd_lang_languageid = ? AND mtypd_thrutime = ? "
                + "FOR UPDATE");
        getMimeTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private MimeTypeDescription getMimeTypeDescription(MimeType mimeType,
            Language language, EntityPermission entityPermission) {
        return MimeTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getMimeTypeDescriptionQueries,
                mimeType, language, Session.MAX_TIME);
    }

    public MimeTypeDescription getMimeTypeDescription(MimeType mimeType, Language language) {
        return getMimeTypeDescription(mimeType, language, EntityPermission.READ_ONLY);
    }

    public MimeTypeDescription getMimeTypeDescriptionForUpdate(MimeType mimeType, Language language) {
        return getMimeTypeDescription(mimeType, language, EntityPermission.READ_WRITE);
    }

    public MimeTypeDescriptionValue getMimeTypeDescriptionValue(MimeTypeDescription mimeTypeDescription) {
        return mimeTypeDescription == null? null: mimeTypeDescription.getMimeTypeDescriptionValue().clone();
    }

    public MimeTypeDescriptionValue getMimeTypeDescriptionValueForUpdate(MimeType mimeType, Language language) {
        return getMimeTypeDescriptionValue(getMimeTypeDescriptionForUpdate(mimeType, language));
    }

    private static final Map<EntityPermission, String> getMimeTypeDescriptionsByMimeTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM mimetypedescriptions, languages "
                + "WHERE mtypd_mtyp_mimetypeid = ? AND mtypd_thrutime = ? AND mtypd_lang_languageid = lang_languageid "
                + "ORDER BY lang_sortorder, lang_languageisoname "
                + "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM mimetypedescriptions "
                + "WHERE mtypd_mtyp_mimetypeid = ? AND mtypd_thrutime = ? "
                + "FOR UPDATE");
        getMimeTypeDescriptionsByMimeTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<MimeTypeDescription> getMimeTypeDescriptionsByMimeType(MimeType mimeType,
            EntityPermission entityPermission) {
        return MimeTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getMimeTypeDescriptionsByMimeTypeQueries,
                mimeType, Session.MAX_TIME);
    }

    public List<MimeTypeDescription> getMimeTypeDescriptionsByMimeType(MimeType mimeType) {
        return getMimeTypeDescriptionsByMimeType(mimeType, EntityPermission.READ_ONLY);
    }

    public List<MimeTypeDescription> getMimeTypeDescriptionsByMimeTypeForUpdate(MimeType mimeType) {
        return getMimeTypeDescriptionsByMimeType(mimeType, EntityPermission.READ_WRITE);
    }

    public String getBestMimeTypeDescription(MimeType mimeType, Language language) {
        String description;
        var mimeTypeDescription = getMimeTypeDescription(mimeType, language);

        if(mimeTypeDescription == null && !language.getIsDefault()) {
            mimeTypeDescription = getMimeTypeDescription(mimeType, getPartyControl().getDefaultLanguage());
        }

        if(mimeTypeDescription == null) {
            description = mimeType.getLastDetail().getMimeTypeName();
        } else {
            description = mimeTypeDescription.getDescription();
        }

        return description;
    }

    public MimeTypeDescriptionTransfer getMimeTypeDescriptionTransfer(UserVisit userVisit, MimeTypeDescription mimeTypeDescription) {
        return getCoreTransferCaches(userVisit).getMimeTypeDescriptionTransferCache().getMimeTypeDescriptionTransfer(mimeTypeDescription);
    }

    public List<MimeTypeDescriptionTransfer> getMimeTypeDescriptionTransfersByMimeType(UserVisit userVisit, MimeType mimeType) {
        var mimeTypeDescriptions = getMimeTypeDescriptionsByMimeType(mimeType);
        List<MimeTypeDescriptionTransfer> mimeTypeDescriptionTransfers = new ArrayList<>(mimeTypeDescriptions.size());
        var mimeTypeDescriptionTransferCache = getCoreTransferCaches(userVisit).getMimeTypeDescriptionTransferCache();

        mimeTypeDescriptions.forEach((mimeTypeDescription) ->
                mimeTypeDescriptionTransfers.add(mimeTypeDescriptionTransferCache.getMimeTypeDescriptionTransfer(mimeTypeDescription))
        );

        return mimeTypeDescriptionTransfers;
    }

    public void updateMimeTypeDescriptionFromValue(MimeTypeDescriptionValue mimeTypeDescriptionValue, BasePK updatedBy) {
        if(mimeTypeDescriptionValue.hasBeenModified()) {
            var mimeTypeDescription = MimeTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    mimeTypeDescriptionValue.getPrimaryKey());

            mimeTypeDescription.setThruTime(session.START_TIME_LONG);
            mimeTypeDescription.store();

            var mimeType = mimeTypeDescription.getMimeType();
            var language = mimeTypeDescription.getLanguage();
            var description = mimeTypeDescriptionValue.getDescription();

            mimeTypeDescription = MimeTypeDescriptionFactory.getInstance().create(mimeType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(mimeType.getPrimaryKey(), EventTypes.MODIFY, mimeTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteMimeTypeDescription(MimeTypeDescription mimeTypeDescription, BasePK deletedBy) {
        mimeTypeDescription.setThruTime(session.START_TIME_LONG);

        sendEvent(mimeTypeDescription.getMimeTypePK(), EventTypes.MODIFY, mimeTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteMimeTypeDescriptionsByMimeType(MimeType mimeType, BasePK deletedBy) {
        var mimeTypeDescriptions = getMimeTypeDescriptionsByMimeTypeForUpdate(mimeType);

        mimeTypeDescriptions.forEach((mimeTypeDescription) -> 
                deleteMimeTypeDescription(mimeTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Mime Type Usages
    // --------------------------------------------------------------------------------
    
    public MimeTypeUsage createMimeTypeUsage(MimeType mimeType, MimeTypeUsageType mimeTypeUsageType) {
        return MimeTypeUsageFactory.getInstance().create(mimeType, mimeTypeUsageType);
    }
    
    public MimeTypeUsage getMimeTypeUsage(MimeType mimeType, MimeTypeUsageType mimeTypeUsageType) {
        MimeTypeUsage mimeTypeUsage;
        
        try {
            var ps = MimeTypeUsageFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM mimetypeusages " +
                    "WHERE mtypu_mtyp_mimetypeid = ? AND mtypu_mtyput_mimetypeusagetypeid = ?");
            
            ps.setLong(1, mimeType.getPrimaryKey().getEntityId());
            ps.setLong(2, mimeTypeUsageType.getPrimaryKey().getEntityId());
            
            mimeTypeUsage = MimeTypeUsageFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return mimeTypeUsage;
    }
    
    public List<MimeTypeUsage> getMimeTypeUsagesByMimeType(MimeType mimeType) {
        List<MimeTypeUsage> mimeTypeUsages;
        
        try {
            var ps = MimeTypeUsageFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ "
                    + "FROM mimetypeusages, mimetypes, mimetypedetails "
                    + "WHERE mtypu_mtyp_mimetypeid = ? "
                    + "AND mtypu_mtyp_mimetypeid = mtyp_mimetypeid AND mtyp_lastdetailid = mtypdt_mimetypedetailid "
                    + "ORDER BY mtypdt_sortorder, mtypdt_mimetypename");
            
            ps.setLong(1, mimeType.getPrimaryKey().getEntityId());
            
            mimeTypeUsages = MimeTypeUsageFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return mimeTypeUsages;
    }
    
    public MimeTypeUsageTransfer getMimeTypeUsageTransfer(UserVisit userVisit, MimeTypeUsage mimeTypeUsage) {
        return getCoreTransferCaches(userVisit).getMimeTypeUsageTransferCache().getMimeTypeUsageTransfer(mimeTypeUsage);
    }
    
    public List<MimeTypeUsageTransfer> getMimeTypeUsageTransfersByMimeType(UserVisit userVisit, Collection<MimeTypeUsage> mimeTypeUsages) {
        List<MimeTypeUsageTransfer> mimeTypeUsageTransfers = new ArrayList<>(mimeTypeUsages.size());
        var mimeTypeUsageTransferCache = getCoreTransferCaches(userVisit).getMimeTypeUsageTransferCache();
        
        mimeTypeUsages.forEach((mimeTypeUsage) ->
                mimeTypeUsageTransfers.add(mimeTypeUsageTransferCache.getMimeTypeUsageTransfer(mimeTypeUsage))
        );
        
        return mimeTypeUsageTransfers;
    }
    
    public List<MimeTypeUsageTransfer> getMimeTypeUsageTransfersByMimeType(UserVisit userVisit, MimeType mimeType) {
        return getMimeTypeUsageTransfersByMimeType(userVisit, getMimeTypeUsagesByMimeType(mimeType));
    }
    
    // --------------------------------------------------------------------------------
    //   Mime Type File Extensions
    // --------------------------------------------------------------------------------
    
    public MimeTypeFileExtension createMimeTypeFileExtension(MimeType mimeType, String fileExtension, Boolean isDefault) {
        return MimeTypeFileExtensionFactory.getInstance().create(mimeType, fileExtension, isDefault);
    }
    
    public MimeTypeFileExtension getDefaultMimeTypeFileExtension(MimeType mimeType) {
        MimeTypeFileExtension mimeTypeFileExtension;
        
        try {
            var ps = MimeTypeFileExtensionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM mimetypefileextensions " +
                    "WHERE mtypfe_mtyp_mimetypeid = ? AND mtypfe_isdefault = 1");
            
            ps.setLong(1, mimeType.getPrimaryKey().getEntityId());
            
            mimeTypeFileExtension = MimeTypeFileExtensionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return mimeTypeFileExtension;
    }
    
    public MimeTypeFileExtension getMimeTypeFileExtension(String fileExtension) {
        MimeTypeFileExtension mimeTypeFileExtension;
        
        try {
            var ps = MimeTypeFileExtensionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM mimetypefileextensions " +
                    "WHERE mtypfe_fileextension = ?");
            
            ps.setString(1, fileExtension);
            
            mimeTypeFileExtension = MimeTypeFileExtensionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return mimeTypeFileExtension;
    }
    
    public List<MimeTypeFileExtension> getMimeTypeFileExtensions() {
        var ps = MimeTypeFileExtensionFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM mimetypefileextensions " +
                "ORDER BY mtypfe_fileextension");
        
        return MimeTypeFileExtensionFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public List<MimeTypeFileExtension> getMimeTypeFileExtensionsByMimeType(MimeType mimeType) {
        var ps = MimeTypeFileExtensionFactory.getInstance().prepareStatement(
                "SELECT _ALL_ "
                + "FROM mimetypefileextensions "
                + "WHERE mtypfe_mtyp_mimetypeid = ? "
                + "ORDER BY mtypfe_fileextension");
        
        return MimeTypeFileExtensionFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps,
                mimeType);
    }
    
    public MimeTypeFileExtensionTransfer getMimeTypeFileExtensionTransfer(UserVisit userVisit, MimeTypeFileExtension mimeTypeFileExtension) {
        return getCoreTransferCaches(userVisit).getMimeTypeFileExtensionTransferCache().getMimeTypeFileExtensionTransfer(mimeTypeFileExtension);
    }
    
    public List<MimeTypeFileExtensionTransfer> getMimeTypeFileExtensionTransfers(UserVisit userVisit, Collection<MimeTypeFileExtension> mimeTypeFileExtensions) {
        List<MimeTypeFileExtensionTransfer> mimeTypeFileExtensionTransfers = new ArrayList<>(mimeTypeFileExtensions.size());
        var mimeTypeFileExtensionTransferCache = getCoreTransferCaches(userVisit).getMimeTypeFileExtensionTransferCache();
        
        mimeTypeFileExtensions.forEach((mimeTypeFileExtension) ->
                mimeTypeFileExtensionTransfers.add(mimeTypeFileExtensionTransferCache.getMimeTypeFileExtensionTransfer(mimeTypeFileExtension))
        );
        
        return mimeTypeFileExtensionTransfers;
    }
    
    public List<MimeTypeFileExtensionTransfer> getMimeTypeFileExtensionTransfers(UserVisit userVisit) {
        return getMimeTypeFileExtensionTransfers(userVisit, getMimeTypeFileExtensions());
    }
    
    public List<MimeTypeFileExtensionTransfer> getMimeTypeFileExtensionTransfersByMimeType(UserVisit userVisit, MimeType mimeType) {
        return getMimeTypeFileExtensionTransfers(userVisit, getMimeTypeFileExtensionsByMimeType(mimeType));
    }
    
    // --------------------------------------------------------------------------------
    //   Protocols
    // --------------------------------------------------------------------------------

    public Protocol createProtocol(String protocolName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultProtocol = getDefaultProtocol();
        var defaultFound = defaultProtocol != null;

        if(defaultFound && isDefault) {
            var defaultProtocolDetailValue = getDefaultProtocolDetailValueForUpdate();

            defaultProtocolDetailValue.setIsDefault(Boolean.FALSE);
            updateProtocolFromValue(defaultProtocolDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        var protocol = ProtocolFactory.getInstance().create();
        var protocolDetail = ProtocolDetailFactory.getInstance().create(protocol, protocolName, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        // Convert to R/W
        protocol = ProtocolFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                protocol.getPrimaryKey());
        protocol.setActiveDetail(protocolDetail);
        protocol.setLastDetail(protocolDetail);
        protocol.store();

        sendEvent(protocol.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return protocol;
    }

    private static final Map<EntityPermission, String> getProtocolByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM protocols, protocoldetails " +
                "WHERE prot_activedetailid = protdt_protocoldetailid " +
                "AND protdt_protocolname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM protocols, protocoldetails " +
                "WHERE prot_activedetailid = protdt_protocoldetailid " +
                "AND protdt_protocolname = ? " +
                "FOR UPDATE");
        getProtocolByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private Protocol getProtocolByName(String protocolName, EntityPermission entityPermission) {
        return ProtocolFactory.getInstance().getEntityFromQuery(entityPermission, getProtocolByNameQueries, protocolName);
    }

    public Protocol getProtocolByName(String protocolName) {
        return getProtocolByName(protocolName, EntityPermission.READ_ONLY);
    }

    public Protocol getProtocolByNameForUpdate(String protocolName) {
        return getProtocolByName(protocolName, EntityPermission.READ_WRITE);
    }

    public ProtocolDetailValue getProtocolDetailValueForUpdate(Protocol protocol) {
        return protocol == null? null: protocol.getLastDetailForUpdate().getProtocolDetailValue().clone();
    }

    public ProtocolDetailValue getProtocolDetailValueByNameForUpdate(String protocolName) {
        return getProtocolDetailValueForUpdate(getProtocolByNameForUpdate(protocolName));
    }

    private static final Map<EntityPermission, String> getDefaultProtocolQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM protocols, protocoldetails " +
                "WHERE prot_activedetailid = protdt_protocoldetailid " +
                "AND protdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM protocols, protocoldetails " +
                "WHERE prot_activedetailid = protdt_protocoldetailid " +
                "AND protdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultProtocolQueries = Collections.unmodifiableMap(queryMap);
    }

    private Protocol getDefaultProtocol(EntityPermission entityPermission) {
        return ProtocolFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultProtocolQueries);
    }

    public Protocol getDefaultProtocol() {
        return getDefaultProtocol(EntityPermission.READ_ONLY);
    }

    public Protocol getDefaultProtocolForUpdate() {
        return getDefaultProtocol(EntityPermission.READ_WRITE);
    }

    public ProtocolDetailValue getDefaultProtocolDetailValueForUpdate() {
        return getDefaultProtocolForUpdate().getLastDetailForUpdate().getProtocolDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getProtocolsQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM protocols, protocoldetails " +
                "WHERE prot_activedetailid = protdt_protocoldetailid " +
                "ORDER BY protdt_sortorder, protdt_protocolname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM protocols, protocoldetails " +
                "WHERE prot_activedetailid = protdt_protocoldetailid " +
                "FOR UPDATE");
        getProtocolsQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<Protocol> getProtocols(EntityPermission entityPermission) {
        return ProtocolFactory.getInstance().getEntitiesFromQuery(entityPermission, getProtocolsQueries);
    }

    public List<Protocol> getProtocols() {
        return getProtocols(EntityPermission.READ_ONLY);
    }

    public List<Protocol> getProtocolsForUpdate() {
        return getProtocols(EntityPermission.READ_WRITE);
    }

    public ProtocolTransfer getProtocolTransfer(UserVisit userVisit, Protocol protocol) {
        return getCoreTransferCaches(userVisit).getProtocolTransferCache().getProtocolTransfer(protocol);
    }

    public List<ProtocolTransfer> getProtocolTransfers(UserVisit userVisit) {
        var protocols = getProtocols();
        List<ProtocolTransfer> protocolTransfers = new ArrayList<>(protocols.size());
        var protocolTransferCache = getCoreTransferCaches(userVisit).getProtocolTransferCache();

        protocols.forEach((protocol) ->
                protocolTransfers.add(protocolTransferCache.getProtocolTransfer(protocol))
        );

        return protocolTransfers;
    }

    public ProtocolChoicesBean getProtocolChoices(String defaultProtocolChoice, Language language, boolean allowNullChoice) {
        var protocols = getProtocols();
        var size = protocols.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultProtocolChoice == null) {
                defaultValue = "";
            }
        }

        for(var protocol : protocols) {
            var protocolDetail = protocol.getLastDetail();

            var label = getBestProtocolDescription(protocol, language);
            var value = protocolDetail.getProtocolName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultProtocolChoice != null && defaultProtocolChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && protocolDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ProtocolChoicesBean(labels, values, defaultValue);
    }

    private void updateProtocolFromValue(ProtocolDetailValue protocolDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(protocolDetailValue.hasBeenModified()) {
            var protocol = ProtocolFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     protocolDetailValue.getProtocolPK());
            var protocolDetail = protocol.getActiveDetailForUpdate();

            protocolDetail.setThruTime(session.START_TIME_LONG);
            protocolDetail.store();

            var protocolPK = protocolDetail.getProtocolPK(); // Not updated
            var protocolName = protocolDetailValue.getProtocolName();
            var isDefault = protocolDetailValue.getIsDefault();
            var sortOrder = protocolDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultProtocol = getDefaultProtocol();
                var defaultFound = defaultProtocol != null && !defaultProtocol.equals(protocol);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultProtocolDetailValue = getDefaultProtocolDetailValueForUpdate();

                    defaultProtocolDetailValue.setIsDefault(Boolean.FALSE);
                    updateProtocolFromValue(defaultProtocolDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            protocolDetail = ProtocolDetailFactory.getInstance().create(protocolPK, protocolName, isDefault, sortOrder, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            protocol.setActiveDetail(protocolDetail);
            protocol.setLastDetail(protocolDetail);

            sendEvent(protocolPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateProtocolFromValue(ProtocolDetailValue protocolDetailValue, BasePK updatedBy) {
        updateProtocolFromValue(protocolDetailValue, true, updatedBy);
    }

    private void deleteProtocol(Protocol protocol, boolean checkDefault, BasePK deletedBy) {
        var protocolDetail = protocol.getLastDetailForUpdate();

        deleteServicesByProtocol(protocol, deletedBy);
        deleteProtocolDescriptionsByProtocol(protocol, deletedBy);

        protocolDetail.setThruTime(session.START_TIME_LONG);
        protocol.setActiveDetail(null);
        protocol.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var defaultProtocol = getDefaultProtocol();

            if(defaultProtocol == null) {
                var protocols = getProtocolsForUpdate();

                if(!protocols.isEmpty()) {
                    var iter = protocols.iterator();
                    if(iter.hasNext()) {
                        defaultProtocol = iter.next();
                    }
                    var protocolDetailValue = Objects.requireNonNull(defaultProtocol).getLastDetailForUpdate().getProtocolDetailValue().clone();

                    protocolDetailValue.setIsDefault(Boolean.TRUE);
                    updateProtocolFromValue(protocolDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(protocol.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteProtocol(Protocol protocol, BasePK deletedBy) {
        deleteProtocol(protocol, true, deletedBy);
    }

    private void deleteProtocols(List<Protocol> protocols, boolean checkDefault, BasePK deletedBy) {
        protocols.forEach((protocol) -> deleteProtocol(protocol, checkDefault, deletedBy));
    }

    public void deleteProtocols(List<Protocol> protocols, BasePK deletedBy) {
        deleteProtocols(protocols, true, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Protocol Descriptions
    // --------------------------------------------------------------------------------

    public ProtocolDescription createProtocolDescription(Protocol protocol, Language language, String description, BasePK createdBy) {
        var protocolDescription = ProtocolDescriptionFactory.getInstance().create(protocol, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(protocol.getPrimaryKey(), EventTypes.MODIFY, protocolDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return protocolDescription;
    }

    private static final Map<EntityPermission, String> getProtocolDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM protocoldescriptions " +
                "WHERE protd_prot_protocolid = ? AND protd_lang_languageid = ? AND protd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM protocoldescriptions " +
                "WHERE protd_prot_protocolid = ? AND protd_lang_languageid = ? AND protd_thrutime = ? " +
                "FOR UPDATE");
        getProtocolDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private ProtocolDescription getProtocolDescription(Protocol protocol, Language language, EntityPermission entityPermission) {
        return ProtocolDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getProtocolDescriptionQueries,
                protocol, language, Session.MAX_TIME);
    }

    public ProtocolDescription getProtocolDescription(Protocol protocol, Language language) {
        return getProtocolDescription(protocol, language, EntityPermission.READ_ONLY);
    }

    public ProtocolDescription getProtocolDescriptionForUpdate(Protocol protocol, Language language) {
        return getProtocolDescription(protocol, language, EntityPermission.READ_WRITE);
    }

    public ProtocolDescriptionValue getProtocolDescriptionValue(ProtocolDescription protocolDescription) {
        return protocolDescription == null? null: protocolDescription.getProtocolDescriptionValue().clone();
    }

    public ProtocolDescriptionValue getProtocolDescriptionValueForUpdate(Protocol protocol, Language language) {
        return getProtocolDescriptionValue(getProtocolDescriptionForUpdate(protocol, language));
    }

    private static final Map<EntityPermission, String> getProtocolDescriptionsByProtocolQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM protocoldescriptions, languages " +
                "WHERE protd_prot_protocolid = ? AND protd_thrutime = ? AND protd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM protocoldescriptions " +
                "WHERE protd_prot_protocolid = ? AND protd_thrutime = ? " +
                "FOR UPDATE");
        getProtocolDescriptionsByProtocolQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ProtocolDescription> getProtocolDescriptionsByProtocol(Protocol protocol, EntityPermission entityPermission) {
        return ProtocolDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getProtocolDescriptionsByProtocolQueries,
                protocol, Session.MAX_TIME);
    }

    public List<ProtocolDescription> getProtocolDescriptionsByProtocol(Protocol protocol) {
        return getProtocolDescriptionsByProtocol(protocol, EntityPermission.READ_ONLY);
    }

    public List<ProtocolDescription> getProtocolDescriptionsByProtocolForUpdate(Protocol protocol) {
        return getProtocolDescriptionsByProtocol(protocol, EntityPermission.READ_WRITE);
    }

    public String getBestProtocolDescription(Protocol protocol, Language language) {
        String description;
        var protocolDescription = getProtocolDescription(protocol, language);

        if(protocolDescription == null && !language.getIsDefault()) {
            protocolDescription = getProtocolDescription(protocol, getPartyControl().getDefaultLanguage());
        }

        if(protocolDescription == null) {
            description = protocol.getLastDetail().getProtocolName();
        } else {
            description = protocolDescription.getDescription();
        }

        return description;
    }

    public ProtocolDescriptionTransfer getProtocolDescriptionTransfer(UserVisit userVisit, ProtocolDescription protocolDescription) {
        return getCoreTransferCaches(userVisit).getProtocolDescriptionTransferCache().getProtocolDescriptionTransfer(protocolDescription);
    }

    public List<ProtocolDescriptionTransfer> getProtocolDescriptionTransfersByProtocol(UserVisit userVisit, Protocol protocol) {
        var protocolDescriptions = getProtocolDescriptionsByProtocol(protocol);
        List<ProtocolDescriptionTransfer> protocolDescriptionTransfers = new ArrayList<>(protocolDescriptions.size());
        var protocolDescriptionTransferCache = getCoreTransferCaches(userVisit).getProtocolDescriptionTransferCache();

        protocolDescriptions.forEach((protocolDescription) ->
                protocolDescriptionTransfers.add(protocolDescriptionTransferCache.getProtocolDescriptionTransfer(protocolDescription))
        );

        return protocolDescriptionTransfers;
    }

    public void updateProtocolDescriptionFromValue(ProtocolDescriptionValue protocolDescriptionValue, BasePK updatedBy) {
        if(protocolDescriptionValue.hasBeenModified()) {
            var protocolDescription = ProtocolDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    protocolDescriptionValue.getPrimaryKey());

            protocolDescription.setThruTime(session.START_TIME_LONG);
            protocolDescription.store();

            var protocol = protocolDescription.getProtocol();
            var language = protocolDescription.getLanguage();
            var description = protocolDescriptionValue.getDescription();

            protocolDescription = ProtocolDescriptionFactory.getInstance().create(protocol, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(protocol.getPrimaryKey(), EventTypes.MODIFY, protocolDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteProtocolDescription(ProtocolDescription protocolDescription, BasePK deletedBy) {
        protocolDescription.setThruTime(session.START_TIME_LONG);

        sendEvent(protocolDescription.getProtocolPK(), EventTypes.MODIFY, protocolDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteProtocolDescriptionsByProtocol(Protocol protocol, BasePK deletedBy) {
        var protocolDescriptions = getProtocolDescriptionsByProtocolForUpdate(protocol);

        protocolDescriptions.forEach((protocolDescription) -> 
                deleteProtocolDescription(protocolDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Services
    // --------------------------------------------------------------------------------

    public Service createService(String serviceName, Integer port, Protocol protocol, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultService = getDefaultService();
        var defaultFound = defaultService != null;

        if(defaultFound && isDefault) {
            var defaultServiceDetailValue = getDefaultServiceDetailValueForUpdate();

            defaultServiceDetailValue.setIsDefault(Boolean.FALSE);
            updateServiceFromValue(defaultServiceDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        var service = ServiceFactory.getInstance().create();
        var serviceDetail = ServiceDetailFactory.getInstance().create(service, serviceName, port, protocol, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        service = ServiceFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                service.getPrimaryKey());
        service.setActiveDetail(serviceDetail);
        service.setLastDetail(serviceDetail);
        service.store();

        sendEvent(service.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return service;
    }

    private static final Map<EntityPermission, String> getServiceByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM services, servicedetails " +
                "WHERE srv_activedetailid = srvdt_servicedetailid " +
                "AND srvdt_servicename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM services, servicedetails " +
                "WHERE srv_activedetailid = srvdt_servicedetailid " +
                "AND srvdt_servicename = ? " +
                "FOR UPDATE");
        getServiceByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private Service getServiceByName(String serviceName, EntityPermission entityPermission) {
        return ServiceFactory.getInstance().getEntityFromQuery(entityPermission, getServiceByNameQueries, serviceName);
    }

    public Service getServiceByName(String serviceName) {
        return getServiceByName(serviceName, EntityPermission.READ_ONLY);
    }

    public Service getServiceByNameForUpdate(String serviceName) {
        return getServiceByName(serviceName, EntityPermission.READ_WRITE);
    }

    public ServiceDetailValue getServiceDetailValueForUpdate(Service service) {
        return service == null? null: service.getLastDetailForUpdate().getServiceDetailValue().clone();
    }

    public ServiceDetailValue getServiceDetailValueByNameForUpdate(String serviceName) {
        return getServiceDetailValueForUpdate(getServiceByNameForUpdate(serviceName));
    }

    private static final Map<EntityPermission, String> getDefaultServiceQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM services, servicedetails " +
                "WHERE srv_activedetailid = srvdt_servicedetailid " +
                "AND srvdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM services, servicedetails " +
                "WHERE srv_activedetailid = srvdt_servicedetailid " +
                "AND srvdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultServiceQueries = Collections.unmodifiableMap(queryMap);
    }

    private Service getDefaultService(EntityPermission entityPermission) {
        return ServiceFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultServiceQueries);
    }

    public Service getDefaultService() {
        return getDefaultService(EntityPermission.READ_ONLY);
    }

    public Service getDefaultServiceForUpdate() {
        return getDefaultService(EntityPermission.READ_WRITE);
    }

    public ServiceDetailValue getDefaultServiceDetailValueForUpdate() {
        return getDefaultServiceForUpdate().getLastDetailForUpdate().getServiceDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getServicesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM services, servicedetails " +
                "WHERE srv_activedetailid = srvdt_servicedetailid " +
                "ORDER BY srvdt_sortorder, srvdt_servicename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM services, servicedetails " +
                "WHERE srv_activedetailid = srvdt_servicedetailid " +
                "FOR UPDATE");
        getServicesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<Service> getServices(EntityPermission entityPermission) {
        return ServiceFactory.getInstance().getEntitiesFromQuery(entityPermission, getServicesQueries);
    }

    public List<Service> getServices() {
        return getServices(EntityPermission.READ_ONLY);
    }

    public List<Service> getServicesForUpdate() {
        return getServices(EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getServicesByProtocolQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM services, servicedetails " +
                "WHERE srv_activedetailid = srvdt_servicedetailid " +
                "AND srvdt_prot_protocolid = ? " +
                "ORDER BY srvdt_sortorder, srvdt_servicename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM services, servicedetails " +
                "WHERE srv_activedetailid = srvdt_servicedetailid " +
                "AND srvdt_prot_protocolid = ? " +
                "FOR UPDATE");
        getServicesByProtocolQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<Service> getServicesByProtocol(Protocol protocol, EntityPermission entityPermission) {
        return ServiceFactory.getInstance().getEntitiesFromQuery(entityPermission, getServicesByProtocolQueries,
                protocol);
    }

    public List<Service> getServicesByProtocol(Protocol protocol) {
        return getServicesByProtocol(protocol, EntityPermission.READ_ONLY);
    }

    public List<Service> getServicesByProtocolForUpdate(Protocol protocol) {
        return getServicesByProtocol(protocol, EntityPermission.READ_WRITE);
    }

    public ServiceTransfer getServiceTransfer(UserVisit userVisit, Service service) {
        return getCoreTransferCaches(userVisit).getServiceTransferCache().getServiceTransfer(service);
    }

    public List<ServiceTransfer> getServiceTransfers(UserVisit userVisit) {
        var services = getServices();
        List<ServiceTransfer> serviceTransfers = new ArrayList<>(services.size());
        var serviceTransferCache = getCoreTransferCaches(userVisit).getServiceTransferCache();

        services.forEach((service) ->
                serviceTransfers.add(serviceTransferCache.getServiceTransfer(service))
        );

        return serviceTransfers;
    }

    public ServiceChoicesBean getServiceChoices(String defaultServiceChoice, Language language, boolean allowNullChoice) {
        var services = getServices();
        var size = services.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultServiceChoice == null) {
                defaultValue = "";
            }
        }

        for(var service : services) {
            var serviceDetail = service.getLastDetail();

            var label = getBestServiceDescription(service, language);
            var value = serviceDetail.getServiceName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultServiceChoice != null && defaultServiceChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && serviceDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ServiceChoicesBean(labels, values, defaultValue);
    }

    private void updateServiceFromValue(ServiceDetailValue serviceDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(serviceDetailValue.hasBeenModified()) {
            var service = ServiceFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     serviceDetailValue.getServicePK());
            var serviceDetail = service.getActiveDetailForUpdate();

            serviceDetail.setThruTime(session.START_TIME_LONG);
            serviceDetail.store();

            var servicePK = serviceDetail.getServicePK(); // Not updated
            var serviceName = serviceDetailValue.getServiceName();
            var port = serviceDetailValue.getPort();
            var protocolPK = serviceDetailValue.getProtocolPK();
            var isDefault = serviceDetailValue.getIsDefault();
            var sortOrder = serviceDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultService = getDefaultService();
                var defaultFound = defaultService != null && !defaultService.equals(service);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultServiceDetailValue = getDefaultServiceDetailValueForUpdate();

                    defaultServiceDetailValue.setIsDefault(Boolean.FALSE);
                    updateServiceFromValue(defaultServiceDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            serviceDetail = ServiceDetailFactory.getInstance().create(servicePK, serviceName, port, protocolPK, isDefault, sortOrder, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            service.setActiveDetail(serviceDetail);
            service.setLastDetail(serviceDetail);

            sendEvent(servicePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateServiceFromValue(ServiceDetailValue serviceDetailValue, BasePK updatedBy) {
        updateServiceFromValue(serviceDetailValue, true, updatedBy);
    }

    private void deleteService(Service service, boolean checkDefault, BasePK deletedBy) {
        var serviceDetail = service.getLastDetailForUpdate();

        deleteServerServicesByService(service, deletedBy);
        deleteServiceDescriptionsByService(service, deletedBy);

        serviceDetail.setThruTime(session.START_TIME_LONG);
        service.setActiveDetail(null);
        service.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var defaultService = getDefaultService();

            if(defaultService == null) {
                var services = getServicesForUpdate();

                if(!services.isEmpty()) {
                    var iter = services.iterator();
                    if(iter.hasNext()) {
                        defaultService = iter.next();
                    }
                    var serviceDetailValue = Objects.requireNonNull(defaultService).getLastDetailForUpdate().getServiceDetailValue().clone();

                    serviceDetailValue.setIsDefault(Boolean.TRUE);
                    updateServiceFromValue(serviceDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(service.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteService(Service service, BasePK deletedBy) {
        deleteService(service, true, deletedBy);
    }

    private void deleteServices(List<Service> services, boolean checkDefault, BasePK deletedBy) {
        services.forEach((service) -> deleteService(service, checkDefault, deletedBy));
    }

    public void deleteServices(List<Service> services, BasePK deletedBy) {
        deleteServices(services, true, deletedBy);
    }

    public void deleteServicesByProtocol(Protocol protocol, BasePK deletedBy) {
        deleteServices(getServicesByProtocol(protocol), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Service Descriptions
    // --------------------------------------------------------------------------------

    public ServiceDescription createServiceDescription(Service service, Language language, String description, BasePK createdBy) {
        var serviceDescription = ServiceDescriptionFactory.getInstance().create(service, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(service.getPrimaryKey(), EventTypes.MODIFY, serviceDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return serviceDescription;
    }

    private static final Map<EntityPermission, String> getServiceDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM servicedescriptions " +
                "WHERE srvd_srv_serviceid = ? AND srvd_lang_languageid = ? AND srvd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM servicedescriptions " +
                "WHERE srvd_srv_serviceid = ? AND srvd_lang_languageid = ? AND srvd_thrutime = ? " +
                "FOR UPDATE");
        getServiceDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private ServiceDescription getServiceDescription(Service service, Language language, EntityPermission entityPermission) {
        return ServiceDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getServiceDescriptionQueries,
                service, language, Session.MAX_TIME);
    }

    public ServiceDescription getServiceDescription(Service service, Language language) {
        return getServiceDescription(service, language, EntityPermission.READ_ONLY);
    }

    public ServiceDescription getServiceDescriptionForUpdate(Service service, Language language) {
        return getServiceDescription(service, language, EntityPermission.READ_WRITE);
    }

    public ServiceDescriptionValue getServiceDescriptionValue(ServiceDescription serviceDescription) {
        return serviceDescription == null? null: serviceDescription.getServiceDescriptionValue().clone();
    }

    public ServiceDescriptionValue getServiceDescriptionValueForUpdate(Service service, Language language) {
        return getServiceDescriptionValue(getServiceDescriptionForUpdate(service, language));
    }

    private static final Map<EntityPermission, String> getServiceDescriptionsByServiceQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM servicedescriptions, languages " +
                "WHERE srvd_srv_serviceid = ? AND srvd_thrutime = ? AND srvd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM servicedescriptions " +
                "WHERE srvd_srv_serviceid = ? AND srvd_thrutime = ? " +
                "FOR UPDATE");
        getServiceDescriptionsByServiceQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ServiceDescription> getServiceDescriptionsByService(Service service, EntityPermission entityPermission) {
        return ServiceDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getServiceDescriptionsByServiceQueries,
                service, Session.MAX_TIME);
    }

    public List<ServiceDescription> getServiceDescriptionsByService(Service service) {
        return getServiceDescriptionsByService(service, EntityPermission.READ_ONLY);
    }

    public List<ServiceDescription> getServiceDescriptionsByServiceForUpdate(Service service) {
        return getServiceDescriptionsByService(service, EntityPermission.READ_WRITE);
    }

    public String getBestServiceDescription(Service service, Language language) {
        String description;
        var serviceDescription = getServiceDescription(service, language);

        if(serviceDescription == null && !language.getIsDefault()) {
            serviceDescription = getServiceDescription(service, getPartyControl().getDefaultLanguage());
        }

        if(serviceDescription == null) {
            description = service.getLastDetail().getServiceName();
        } else {
            description = serviceDescription.getDescription();
        }

        return description;
    }

    public ServiceDescriptionTransfer getServiceDescriptionTransfer(UserVisit userVisit, ServiceDescription serviceDescription) {
        return getCoreTransferCaches(userVisit).getServiceDescriptionTransferCache().getServiceDescriptionTransfer(serviceDescription);
    }

    public List<ServiceDescriptionTransfer> getServiceDescriptionTransfersByService(UserVisit userVisit, Service service) {
        var serviceDescriptions = getServiceDescriptionsByService(service);
        List<ServiceDescriptionTransfer> serviceDescriptionTransfers = new ArrayList<>(serviceDescriptions.size());
        var serviceDescriptionTransferCache = getCoreTransferCaches(userVisit).getServiceDescriptionTransferCache();

        serviceDescriptions.forEach((serviceDescription) ->
                serviceDescriptionTransfers.add(serviceDescriptionTransferCache.getServiceDescriptionTransfer(serviceDescription))
        );

        return serviceDescriptionTransfers;
    }

    public void updateServiceDescriptionFromValue(ServiceDescriptionValue serviceDescriptionValue, BasePK updatedBy) {
        if(serviceDescriptionValue.hasBeenModified()) {
            var serviceDescription = ServiceDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    serviceDescriptionValue.getPrimaryKey());

            serviceDescription.setThruTime(session.START_TIME_LONG);
            serviceDescription.store();

            var service = serviceDescription.getService();
            var language = serviceDescription.getLanguage();
            var description = serviceDescriptionValue.getDescription();

            serviceDescription = ServiceDescriptionFactory.getInstance().create(service, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(service.getPrimaryKey(), EventTypes.MODIFY, serviceDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteServiceDescription(ServiceDescription serviceDescription, BasePK deletedBy) {
        serviceDescription.setThruTime(session.START_TIME_LONG);

        sendEvent(serviceDescription.getServicePK(), EventTypes.MODIFY, serviceDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteServiceDescriptionsByService(Service service, BasePK deletedBy) {
        var serviceDescriptions = getServiceDescriptionsByServiceForUpdate(service);

        serviceDescriptions.forEach((serviceDescription) -> 
                deleteServiceDescription(serviceDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Servers
    // --------------------------------------------------------------------------------

    public Server createServer(String serverName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultServer = getDefaultServer();
        var defaultFound = defaultServer != null;

        if(defaultFound && isDefault) {
            var defaultServerDetailValue = getDefaultServerDetailValueForUpdate();

            defaultServerDetailValue.setIsDefault(Boolean.FALSE);
            updateServerFromValue(defaultServerDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        var server = ServerFactory.getInstance().create();
        var serverDetail = ServerDetailFactory.getInstance().create(server, serverName, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        // Convert to R/W
        server = ServerFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, server.getPrimaryKey());
        server.setActiveDetail(serverDetail);
        server.setLastDetail(serverDetail);
        server.store();

        sendEvent(server.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return server;
    }

    private static final Map<EntityPermission, String> getServerByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM servers, serverdetails " +
                "WHERE serv_activedetailid = servdt_serverdetailid " +
                "AND servdt_servername = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM servers, serverdetails " +
                "WHERE serv_activedetailid = servdt_serverdetailid " +
                "AND servdt_servername = ? " +
                "FOR UPDATE");
        getServerByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private Server getServerByName(String serverName, EntityPermission entityPermission) {
        return ServerFactory.getInstance().getEntityFromQuery(entityPermission, getServerByNameQueries, serverName);
    }

    public Server getServerByName(String serverName) {
        return getServerByName(serverName, EntityPermission.READ_ONLY);
    }

    public Server getServerByNameForUpdate(String serverName) {
        return getServerByName(serverName, EntityPermission.READ_WRITE);
    }

    public ServerDetailValue getServerDetailValueForUpdate(Server server) {
        return server == null? null: server.getLastDetailForUpdate().getServerDetailValue().clone();
    }

    public ServerDetailValue getServerDetailValueByNameForUpdate(String serverName) {
        return getServerDetailValueForUpdate(getServerByNameForUpdate(serverName));
    }

    private static final Map<EntityPermission, String> getDefaultServerQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM servers, serverdetails " +
                "WHERE serv_activedetailid = servdt_serverdetailid " +
                "AND servdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM servers, serverdetails " +
                "WHERE serv_activedetailid = servdt_serverdetailid " +
                "AND servdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultServerQueries = Collections.unmodifiableMap(queryMap);
    }

    private Server getDefaultServer(EntityPermission entityPermission) {
        return ServerFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultServerQueries);
    }

    public Server getDefaultServer() {
        return getDefaultServer(EntityPermission.READ_ONLY);
    }

    public Server getDefaultServerForUpdate() {
        return getDefaultServer(EntityPermission.READ_WRITE);
    }

    public ServerDetailValue getDefaultServerDetailValueForUpdate() {
        return getDefaultServerForUpdate().getLastDetailForUpdate().getServerDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getServersQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM servers, serverdetails " +
                "WHERE serv_activedetailid = servdt_serverdetailid " +
                "ORDER BY servdt_sortorder, servdt_servername " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM servers, serverdetails " +
                "WHERE serv_activedetailid = servdt_serverdetailid " +
                "FOR UPDATE");
        getServersQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<Server> getServers(EntityPermission entityPermission) {
        return ServerFactory.getInstance().getEntitiesFromQuery(entityPermission, getServersQueries);
    }

    public List<Server> getServers() {
        return getServers(EntityPermission.READ_ONLY);
    }

    public List<Server> getServersForUpdate() {
        return getServers(EntityPermission.READ_WRITE);
    }

    public ServerTransfer getServerTransfer(UserVisit userVisit, Server server) {
        return getCoreTransferCaches(userVisit).getServerTransferCache().getServerTransfer(server);
    }

    public List<ServerTransfer> getServerTransfers(UserVisit userVisit) {
        var servers = getServers();
        List<ServerTransfer> serverTransfers = new ArrayList<>(servers.size());
        var serverTransferCache = getCoreTransferCaches(userVisit).getServerTransferCache();

        servers.forEach((server) ->
                serverTransfers.add(serverTransferCache.getServerTransfer(server))
        );

        return serverTransfers;
    }

    public ServerChoicesBean getServerChoices(String defaultServerChoice, Language language, boolean allowNullChoice) {
        var servers = getServers();
        var size = servers.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultServerChoice == null) {
                defaultValue = "";
            }
        }

        for(var server : servers) {
            var serverDetail = server.getLastDetail();

            var label = getBestServerDescription(server, language);
            var value = serverDetail.getServerName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultServerChoice != null && defaultServerChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && serverDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ServerChoicesBean(labels, values, defaultValue);
    }

    private void updateServerFromValue(ServerDetailValue serverDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(serverDetailValue.hasBeenModified()) {
            var server = ServerFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     serverDetailValue.getServerPK());
            var serverDetail = server.getActiveDetailForUpdate();

            serverDetail.setThruTime(session.START_TIME_LONG);
            serverDetail.store();

            var serverPK = serverDetail.getServerPK(); // Not updated
            var serverName = serverDetailValue.getServerName();
            var isDefault = serverDetailValue.getIsDefault();
            var sortOrder = serverDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultServer = getDefaultServer();
                var defaultFound = defaultServer != null && !defaultServer.equals(server);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultServerDetailValue = getDefaultServerDetailValueForUpdate();

                    defaultServerDetailValue.setIsDefault(Boolean.FALSE);
                    updateServerFromValue(defaultServerDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            serverDetail = ServerDetailFactory.getInstance().create(serverPK, serverName, isDefault, sortOrder, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            server.setActiveDetail(serverDetail);
            server.setLastDetail(serverDetail);

            sendEvent(serverPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateServerFromValue(ServerDetailValue serverDetailValue, BasePK updatedBy) {
        updateServerFromValue(serverDetailValue, true, updatedBy);
    }

    private void deleteServer(Server server, boolean checkDefault, BasePK deletedBy) {
        var serverDetail = server.getLastDetailForUpdate();

        deleteServerServicesByServer(server, deletedBy);
        deleteServerDescriptionsByServer(server, deletedBy);

        serverDetail.setThruTime(session.START_TIME_LONG);
        server.setActiveDetail(null);
        server.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var defaultServer = getDefaultServer();

            if(defaultServer == null) {
                var servers = getServersForUpdate();

                if(!servers.isEmpty()) {
                    var iter = servers.iterator();
                    if(iter.hasNext()) {
                        defaultServer = iter.next();
                    }
                    var serverDetailValue = Objects.requireNonNull(defaultServer).getLastDetailForUpdate().getServerDetailValue().clone();

                    serverDetailValue.setIsDefault(Boolean.TRUE);
                    updateServerFromValue(serverDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(server.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteServer(Server server, BasePK deletedBy) {
        deleteServer(server, true, deletedBy);
    }

    private void deleteServers(List<Server> servers, boolean checkDefault, BasePK deletedBy) {
        servers.forEach((server) -> deleteServer(server, checkDefault, deletedBy));
    }

    public void deleteServers(List<Server> servers, BasePK deletedBy) {
        deleteServers(servers, true, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Server Descriptions
    // --------------------------------------------------------------------------------

    public ServerDescription createServerDescription(Server server, Language language, String description, BasePK createdBy) {
        var serverDescription = ServerDescriptionFactory.getInstance().create(server, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(server.getPrimaryKey(), EventTypes.MODIFY, serverDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return serverDescription;
    }

    private static final Map<EntityPermission, String> getServerDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM serverdescriptions " +
                "WHERE servd_serv_serverid = ? AND servd_lang_languageid = ? AND servd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM serverdescriptions " +
                "WHERE servd_serv_serverid = ? AND servd_lang_languageid = ? AND servd_thrutime = ? " +
                "FOR UPDATE");
        getServerDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private ServerDescription getServerDescription(Server server, Language language, EntityPermission entityPermission) {
        return ServerDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getServerDescriptionQueries,
                server, language, Session.MAX_TIME);
    }

    public ServerDescription getServerDescription(Server server, Language language) {
        return getServerDescription(server, language, EntityPermission.READ_ONLY);
    }

    public ServerDescription getServerDescriptionForUpdate(Server server, Language language) {
        return getServerDescription(server, language, EntityPermission.READ_WRITE);
    }

    public ServerDescriptionValue getServerDescriptionValue(ServerDescription serverDescription) {
        return serverDescription == null? null: serverDescription.getServerDescriptionValue().clone();
    }

    public ServerDescriptionValue getServerDescriptionValueForUpdate(Server server, Language language) {
        return getServerDescriptionValue(getServerDescriptionForUpdate(server, language));
    }

    private static final Map<EntityPermission, String> getServerDescriptionsByServerQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM serverdescriptions, languages " +
                "WHERE servd_serv_serverid = ? AND servd_thrutime = ? AND servd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM serverdescriptions " +
                "WHERE servd_serv_serverid = ? AND servd_thrutime = ? " +
                "FOR UPDATE");
        getServerDescriptionsByServerQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ServerDescription> getServerDescriptionsByServer(Server server, EntityPermission entityPermission) {
        return ServerDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getServerDescriptionsByServerQueries,
                server, Session.MAX_TIME);
    }

    public List<ServerDescription> getServerDescriptionsByServer(Server server) {
        return getServerDescriptionsByServer(server, EntityPermission.READ_ONLY);
    }

    public List<ServerDescription> getServerDescriptionsByServerForUpdate(Server server) {
        return getServerDescriptionsByServer(server, EntityPermission.READ_WRITE);
    }

    public String getBestServerDescription(Server server, Language language) {
        String description;
        var serverDescription = getServerDescription(server, language);

        if(serverDescription == null && !language.getIsDefault()) {
            serverDescription = getServerDescription(server, getPartyControl().getDefaultLanguage());
        }

        if(serverDescription == null) {
            description = server.getLastDetail().getServerName();
        } else {
            description = serverDescription.getDescription();
        }

        return description;
    }

    public ServerDescriptionTransfer getServerDescriptionTransfer(UserVisit userVisit, ServerDescription serverDescription) {
        return getCoreTransferCaches(userVisit).getServerDescriptionTransferCache().getServerDescriptionTransfer(serverDescription);
    }

    public List<ServerDescriptionTransfer> getServerDescriptionTransfersByServer(UserVisit userVisit, Server server) {
        var serverDescriptions = getServerDescriptionsByServer(server);
        List<ServerDescriptionTransfer> serverDescriptionTransfers = new ArrayList<>(serverDescriptions.size());
        var serverDescriptionTransferCache = getCoreTransferCaches(userVisit).getServerDescriptionTransferCache();

        serverDescriptions.forEach((serverDescription) ->
                serverDescriptionTransfers.add(serverDescriptionTransferCache.getServerDescriptionTransfer(serverDescription))
        );

        return serverDescriptionTransfers;
    }

    public void updateServerDescriptionFromValue(ServerDescriptionValue serverDescriptionValue, BasePK updatedBy) {
        if(serverDescriptionValue.hasBeenModified()) {
            var serverDescription = ServerDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    serverDescriptionValue.getPrimaryKey());

            serverDescription.setThruTime(session.START_TIME_LONG);
            serverDescription.store();

            var server = serverDescription.getServer();
            var language = serverDescription.getLanguage();
            var description = serverDescriptionValue.getDescription();

            serverDescription = ServerDescriptionFactory.getInstance().create(server, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(server.getPrimaryKey(), EventTypes.MODIFY, serverDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteServerDescription(ServerDescription serverDescription, BasePK deletedBy) {
        serverDescription.setThruTime(session.START_TIME_LONG);

        sendEvent(serverDescription.getServerPK(), EventTypes.MODIFY, serverDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteServerDescriptionsByServer(Server server, BasePK deletedBy) {
        var serverDescriptions = getServerDescriptionsByServerForUpdate(server);

        serverDescriptions.forEach((serverDescription) -> 
                deleteServerDescription(serverDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Server Services
    // --------------------------------------------------------------------------------

    public ServerService createServerService(Server server, Service service, BasePK createdBy) {
        var serverService = ServerServiceFactory.getInstance().create(server, service, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(server.getPrimaryKey(), EventTypes.MODIFY, serverService.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return serverService;
    }

    private static final Map<EntityPermission, String> getServerServiceQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM serverservices " +
                "WHERE servsrv_serv_serverid = ? AND servsrv_srv_serviceid = ? AND servsrv_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM serverservices " +
                "WHERE servsrv_serv_serverid = ? AND servsrv_srv_serviceid = ? AND servsrv_thrutime = ? " +
                "FOR UPDATE");
        getServerServiceQueries = Collections.unmodifiableMap(queryMap);
    }

    private ServerService getServerService(Server server, Service service, EntityPermission entityPermission) {
        return ServerServiceFactory.getInstance().getEntityFromQuery(entityPermission, getServerServiceQueries,
                server, service, Session.MAX_TIME);
    }

    public ServerService getServerService(Server server, Service service) {
        return getServerService(server, service, EntityPermission.READ_ONLY);
    }

    public ServerService getServerServiceForUpdate(Server server, Service service) {
        return getServerService(server, service, EntityPermission.READ_WRITE);
    }

    public ServerServiceValue getServerServiceValue(ServerService serverService) {
        return serverService == null? null: serverService.getServerServiceValue().clone();
    }

    public ServerServiceValue getServerServiceValueForUpdate(Server server, Service service) {
        return getServerServiceValue(getServerServiceForUpdate(server, service));
    }

    private static final Map<EntityPermission, String> getServerServicesByServerQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM serverservices, services, servicedetails " +
                "WHERE servsrv_serv_serverid = ? AND servsrv_thrutime = ? " +
                "AND servsrv_srv_serviceid = srv_serviceid AND srv_lastdetailid = srvdt_servicedetailid " +
                "ORDER BY srvdt_sortorder, srvdt_servicename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM serverservices " +
                "WHERE servsrv_serv_serverid = ? AND servsrv_thrutime = ? " +
                "FOR UPDATE");
        getServerServicesByServerQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ServerService> getServerServicesByServer(Server server, EntityPermission entityPermission) {
        return ServerServiceFactory.getInstance().getEntitiesFromQuery(entityPermission, getServerServicesByServerQueries,
                server, Session.MAX_TIME);
    }

    public List<ServerService> getServerServicesByServer(Server server) {
        return getServerServicesByServer(server, EntityPermission.READ_ONLY);
    }

    public List<ServerService> getServerServicesByServerForUpdate(Server server) {
        return getServerServicesByServer(server, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getServerServicesByServiceQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM serverservices, services, servicedetails " +
                "WHERE servsrv_srv_serviceid = ? AND servsrv_thrutime = ? " +
                "AND servsrv_serv_serverid = serv_serverid AND serv_lastdetailid = servdt_serverdetailid " +
                "ORDER BY servdt_sortorder, servdt_servername");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM serverservices " +
                "WHERE servsrv_srv_serviceid = ? AND servsrv_thrutime = ? " +
                "FOR UPDATE");
        getServerServicesByServiceQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ServerService> getServerServicesByService(Service service, EntityPermission entityPermission) {
        return ServerServiceFactory.getInstance().getEntitiesFromQuery(entityPermission, getServerServicesByServiceQueries,
                service, Session.MAX_TIME);
    }

    public List<ServerService> getServerServicesByService(Service service) {
        return getServerServicesByService(service, EntityPermission.READ_ONLY);
    }

    public List<ServerService> getServerServicesByServiceForUpdate(Service service) {
        return getServerServicesByService(service, EntityPermission.READ_WRITE);
    }

    public ServerServiceTransfer getServerServiceTransfer(UserVisit userVisit, ServerService serverService) {
        return getCoreTransferCaches(userVisit).getServerServiceTransferCache().getServerServiceTransfer(serverService);
    }

    public List<ServerServiceTransfer> getServerServiceTransfersByServer(UserVisit userVisit, Server server) {
        var serverServices = getServerServicesByServer(server);
        List<ServerServiceTransfer> serverServiceTransfers = new ArrayList<>(serverServices.size());
        var serverServiceTransferCache = getCoreTransferCaches(userVisit).getServerServiceTransferCache();

        serverServices.forEach((serverService) ->
                serverServiceTransfers.add(serverServiceTransferCache.getServerServiceTransfer(serverService))
        );

        return serverServiceTransfers;
    }

    public void deleteServerService(ServerService serverService, BasePK deletedBy) {
        var scaleControl = Session.getModelController(ScaleControl.class);
        
        scaleControl.deleteScalesByServerService(serverService, deletedBy);

        serverService.setThruTime(session.START_TIME_LONG);

        sendEvent(serverService.getServerPK(), EventTypes.MODIFY, serverService.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteServerServices(List<ServerService> serverServices, BasePK deletedBy) {
        serverServices.forEach((serverService) -> 
                deleteServerService(serverService, deletedBy)
        );
    }

    public void deleteServerServicesByServer(Server server, BasePK deletedBy) {
        deleteServerServices(getServerServicesByServerForUpdate(server), deletedBy);
    }

    public void deleteServerServicesByService(Service service, BasePK deletedBy) {
        deleteServerServices(getServerServicesByServiceForUpdate(service), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Entity Boolean Defaults
    // --------------------------------------------------------------------------------

    public EntityBooleanDefault createEntityBooleanDefault(EntityAttribute entityAttribute, Boolean booleanAttribute,
            BasePK createdBy) {
        var entityBooleanDefault = EntityBooleanDefaultFactory.getInstance().create(entityAttribute,
                booleanAttribute, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityBooleanDefault.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return entityBooleanDefault;
    }

    public long countEntityBooleanDefaultHistory(EntityAttribute entityAttribute) {
        return session.queryForLong("""
                    SELECT COUNT(*)
                    FROM entitybooleandefaults
                    WHERE enbldef_ena_entityattributeid = ?
                    """, entityAttribute);
    }

    private static final Map<EntityPermission, String> getEntityBooleanDefaultHistoryQueries;

    static {
        getEntityBooleanDefaultHistoryQueries = Map.of(
                EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM entitybooleandefaults
                WHERE enbldef_ena_entityattributeid = ?
                ORDER BY enbldef_thrutime
                _LIMIT_
                """);
    }

    public List<EntityBooleanDefault> getEntityBooleanDefaultHistory(EntityAttribute entityAttribute) {
        return EntityBooleanDefaultFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityBooleanDefaultHistoryQueries,
                entityAttribute);
    }

    private EntityBooleanDefault getEntityBooleanDefault(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        EntityBooleanDefault entityBooleanDefault;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitybooleandefaults " +
                        "WHERE enbldef_ena_entityattributeid = ? AND enbldef_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitybooleandefaults " +
                        "WHERE enbldef_ena_entityattributeid = ? AND enbldef_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = EntityBooleanDefaultFactory.getInstance().prepareStatement(query);

            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            entityBooleanDefault = EntityBooleanDefaultFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityBooleanDefault;
    }

    public EntityBooleanDefault getEntityBooleanDefault(EntityAttribute entityAttribute) {
        return getEntityBooleanDefault(entityAttribute, EntityPermission.READ_ONLY);
    }

    public EntityBooleanDefault getEntityBooleanDefaultForUpdate(EntityAttribute entityAttribute) {
        return getEntityBooleanDefault(entityAttribute, EntityPermission.READ_WRITE);
    }

    public EntityBooleanDefaultValue getEntityBooleanDefaultValueForUpdate(EntityBooleanDefault entityBooleanDefault) {
        return entityBooleanDefault == null? null: entityBooleanDefault.getEntityBooleanDefaultValue().clone();
    }

    public EntityBooleanDefaultValue getEntityBooleanDefaultValueForUpdate(EntityAttribute entityAttribute) {
        return getEntityBooleanDefaultValueForUpdate(getEntityBooleanDefaultForUpdate(entityAttribute));
    }

    public EntityBooleanDefaultTransfer getEntityBooleanDefaultTransfer(UserVisit userVisit, EntityBooleanDefault entityBooleanDefault) {
        return getCoreTransferCaches(userVisit).getEntityBooleanDefaultTransferCache().getEntityBooleanDefaultTransfer(entityBooleanDefault);
    }

    public void updateEntityBooleanDefaultFromValue(EntityBooleanDefaultValue entityBooleanDefaultValue, BasePK updatedBy) {
        if(entityBooleanDefaultValue.hasBeenModified()) {
            var entityBooleanDefault = EntityBooleanDefaultFactory.getInstance().getEntityFromValue(session, EntityPermission.READ_WRITE, entityBooleanDefaultValue);
            var entityAttribute = entityBooleanDefault.getEntityAttribute();

            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityBooleanDefault.setThruTime(session.START_TIME_LONG);
                entityBooleanDefault.store();
            } else {
                entityBooleanDefault.remove();
            }

            entityBooleanDefault = EntityBooleanDefaultFactory.getInstance().create(entityAttribute,
                    entityBooleanDefaultValue.getBooleanAttribute(), session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityBooleanDefault.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteEntityBooleanDefault(EntityBooleanDefault entityBooleanDefault, BasePK deletedBy) {
        var entityAttribute = entityBooleanDefault.getEntityAttribute();

        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityBooleanDefault.setThruTime(session.START_TIME_LONG);
        } else {
            entityBooleanDefault.remove();
        }

        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityBooleanDefault.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteEntityBooleanDefaultByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        var entityBooleanDefault = getEntityBooleanDefaultForUpdate(entityAttribute);

        if(entityBooleanDefault != null) {
            deleteEntityBooleanDefault(entityBooleanDefault, deletedBy);
        }
    }

    // --------------------------------------------------------------------------------
    //   Entity Boolean Attributes
    // --------------------------------------------------------------------------------

    public EntityBooleanAttribute createEntityBooleanAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Boolean booleanAttribute, BasePK createdBy) {
        return createEntityBooleanAttribute(entityAttribute.getPrimaryKey(), entityInstance, booleanAttribute,
                createdBy);
    }

    public EntityBooleanAttribute createEntityBooleanAttribute(EntityAttributePK entityAttribute, EntityInstance entityInstance,
            Boolean booleanAttribute, BasePK createdBy) {
        var entityBooleanAttribute = EntityBooleanAttributeFactory.getInstance().create(entityAttribute,
                entityInstance.getPrimaryKey(), booleanAttribute, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute, EventTypes.CREATE, createdBy);

        return entityBooleanAttribute;
    }

    public long countEntityBooleanAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return session.queryForLong("""
                    SELECT COUNT(*)
                    FROM entitybooleanattributes
                    WHERE enbla_ena_entityattributeid = ? AND enbla_eni_entityinstanceid = ?
                    """, entityAttribute, entityInstance);
    }

    private static final Map<EntityPermission, String> getEntityBooleanAttributeHistoryQueries;

    static {
        var queryMap = Map.of(
                EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM entitybooleanattributes
                WHERE enbla_ena_entityattributeid = ? AND enbla_eni_entityinstanceid = ?
                ORDER BY enbla_thrutime
                _LIMIT_
                """);
        getEntityBooleanAttributeHistoryQueries = Collections.unmodifiableMap(queryMap);
    }

    public List<EntityBooleanAttribute> getEntityBooleanAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return EntityBooleanAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityBooleanAttributeHistoryQueries,
                entityAttribute, entityInstance);
    }
    
    private EntityBooleanAttribute getEntityBooleanAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityPermission entityPermission) {
        EntityBooleanAttribute entityBooleanAttribute;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitybooleanattributes " +
                        "WHERE enbla_ena_entityattributeid = ? AND enbla_eni_entityinstanceid = ? AND enbla_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitybooleanattributes " +
                        "WHERE enbla_ena_entityattributeid = ? AND enbla_eni_entityinstanceid = ? AND enbla_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = EntityBooleanAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityBooleanAttribute = EntityBooleanAttributeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityBooleanAttribute;
    }
    
    public EntityBooleanAttribute getEntityBooleanAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityBooleanAttribute(entityAttribute, entityInstance, EntityPermission.READ_ONLY);
    }
    
    public EntityBooleanAttribute getEntityBooleanAttributeForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityBooleanAttribute(entityAttribute, entityInstance, EntityPermission.READ_WRITE);
    }
    
    public EntityBooleanAttributeValue getEntityBooleanAttributeValueForUpdate(EntityBooleanAttribute entityBooleanAttribute) {
        return entityBooleanAttribute == null? null: entityBooleanAttribute.getEntityBooleanAttributeValue().clone();
    }
    
    public EntityBooleanAttributeValue getEntityBooleanAttributeValueForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityBooleanAttributeValueForUpdate(getEntityBooleanAttributeForUpdate(entityAttribute, entityInstance));
    }
    
    public List<EntityBooleanAttribute> getEntityBooleanAttributesByEntityAttributeForUpdate(EntityAttribute entityAttribute) {
        List<EntityBooleanAttribute> entityBooleanAttributes;
        
        try {
            var ps = EntityBooleanAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitybooleanattributes " +
                    "WHERE enbla_ena_entityattributeid = ? AND enbla_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityBooleanAttributes = EntityBooleanAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityBooleanAttributes;
    }
    
    public List<EntityBooleanAttribute> getEntityBooleanAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        List<EntityBooleanAttribute> entityBooleanAttributes;
        
        try {
            var ps = EntityBooleanAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitybooleanattributes " +
                    "WHERE enbla_eni_entityinstanceid = ? AND enbla_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityBooleanAttributes = EntityBooleanAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityBooleanAttributes;
    }
    
    public EntityBooleanAttributeTransfer getEntityBooleanAttributeTransfer(UserVisit userVisit, EntityBooleanAttribute entityBooleanAttribute, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityBooleanAttributeTransferCache().getEntityBooleanAttributeTransfer(entityBooleanAttribute, entityInstance);
    }
    
    public void updateEntityBooleanAttributeFromValue(EntityBooleanAttributeValue entityBooleanAttributeValue, BasePK updatedBy) {
        if(entityBooleanAttributeValue.hasBeenModified()) {
            var entityBooleanAttribute = EntityBooleanAttributeFactory.getInstance().getEntityFromValue(session, EntityPermission.READ_WRITE, entityBooleanAttributeValue);
            var entityAttribute = entityBooleanAttribute.getEntityAttribute();
            var entityInstance = entityBooleanAttribute.getEntityInstance();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityBooleanAttribute.setThruTime(session.START_TIME_LONG);
                entityBooleanAttribute.store();
            } else {
                entityBooleanAttribute.remove();
            }
            
            EntityBooleanAttributeFactory.getInstance().create(entityAttribute, entityInstance, entityBooleanAttributeValue.getBooleanAttribute(), session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityBooleanAttribute(EntityBooleanAttribute entityBooleanAttribute, BasePK deletedBy) {
        var entityAttribute = entityBooleanAttribute.getEntityAttribute();
        var entityInstance = entityBooleanAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityBooleanAttribute.setThruTime(session.START_TIME_LONG);
        } else {
            entityBooleanAttribute.remove();
        }
        
        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteEntityBooleanAttributes(List<EntityBooleanAttribute> entityBooleanAttributes, BasePK deletedBy) {
        entityBooleanAttributes.forEach((entityBooleanAttribute) -> 
                deleteEntityBooleanAttribute(entityBooleanAttribute, deletedBy)
        );
    }
    
    public void deleteEntityBooleanAttributesByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        deleteEntityBooleanAttributes(getEntityBooleanAttributesByEntityAttributeForUpdate(entityAttribute), deletedBy);
    }
    
    public void deleteEntityBooleanAttributesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteEntityBooleanAttributes(getEntityBooleanAttributesByEntityInstanceForUpdate(entityInstance), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Entity Date Defaults
    // --------------------------------------------------------------------------------

    public EntityDateDefault createEntityDateDefault(EntityAttribute entityAttribute, Integer dateAttribute,
            BasePK createdBy) {
        var entityDateDefault = EntityDateDefaultFactory.getInstance().create(entityAttribute,
                dateAttribute, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityDateDefault.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return entityDateDefault;
    }

    public long countEntityDateDefaultHistory(EntityAttribute entityAttribute) {
        return session.queryForLong("""
                    SELECT COUNT(*)
                    FROM entitydatedefaults
                    WHERE enddef_thrutime = ?
                    """, entityAttribute);
    }

    private static final Map<EntityPermission, String> getEntityDateDefaultHistoryQueries;

    static {
        getEntityDateDefaultHistoryQueries = Map.of(
                EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM entitydatedefaults
                WHERE enddef_ena_entityattributeid = ?
                ORDER BY enddef_thrutime
                _LIMIT_
                """);
    }

    public List<EntityDateDefault> getEntityDateDefaultHistory(EntityAttribute entityAttribute) {
        return EntityDateDefaultFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityDateDefaultHistoryQueries,
                entityAttribute);
    }

    private EntityDateDefault getEntityDateDefault(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        EntityDateDefault entityDateDefault;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitydatedefaults " +
                        "WHERE enddef_ena_entityattributeid = ? AND enddef_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitydatedefaults " +
                        "WHERE enddef_ena_entityattributeid = ? AND enddef_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = EntityDateDefaultFactory.getInstance().prepareStatement(query);

            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            entityDateDefault = EntityDateDefaultFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityDateDefault;
    }

    public EntityDateDefault getEntityDateDefault(EntityAttribute entityAttribute) {
        return getEntityDateDefault(entityAttribute, EntityPermission.READ_ONLY);
    }

    public EntityDateDefault getEntityDateDefaultForUpdate(EntityAttribute entityAttribute) {
        return getEntityDateDefault(entityAttribute, EntityPermission.READ_WRITE);
    }

    public EntityDateDefaultValue getEntityDateDefaultValueForUpdate(EntityDateDefault entityDateDefault) {
        return entityDateDefault == null? null: entityDateDefault.getEntityDateDefaultValue().clone();
    }

    public EntityDateDefaultValue getEntityDateDefaultValueForUpdate(EntityAttribute entityAttribute) {
        return getEntityDateDefaultValueForUpdate(getEntityDateDefaultForUpdate(entityAttribute));
    }

    public EntityDateDefaultTransfer getEntityDateDefaultTransfer(UserVisit userVisit, EntityDateDefault entityDateDefault) {
        return getCoreTransferCaches(userVisit).getEntityDateDefaultTransferCache().getEntityDateDefaultTransfer(entityDateDefault);
    }

    public void updateEntityDateDefaultFromValue(EntityDateDefaultValue entityDateDefaultValue, BasePK updatedBy) {
        if(entityDateDefaultValue.hasBeenModified()) {
            var entityDateDefault = EntityDateDefaultFactory.getInstance().getEntityFromValue(session, EntityPermission.READ_WRITE, entityDateDefaultValue);
            var entityAttribute = entityDateDefault.getEntityAttribute();

            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityDateDefault.setThruTime(session.START_TIME_LONG);
                entityDateDefault.store();
            } else {
                entityDateDefault.remove();
            }

            entityDateDefault = EntityDateDefaultFactory.getInstance().create(entityAttribute,
                    entityDateDefaultValue.getDateAttribute(), session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityDateDefault.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteEntityDateDefault(EntityDateDefault entityDateDefault, BasePK deletedBy) {
        var entityAttribute = entityDateDefault.getEntityAttribute();

        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityDateDefault.setThruTime(session.START_TIME_LONG);
        } else {
            entityDateDefault.remove();
        }

        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityDateDefault.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteEntityDateDefaultByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        var entityDateDefault = getEntityDateDefaultForUpdate(entityAttribute);

        if(entityDateDefault != null) {
            deleteEntityDateDefault(entityDateDefault, deletedBy);
        }
    }

    // --------------------------------------------------------------------------------
    //   Entity Date Attributes
    // --------------------------------------------------------------------------------

    public EntityDateAttribute createEntityDateAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Integer dateAttribute, BasePK createdBy) {
        return createEntityDateAttribute(entityAttribute.getPrimaryKey(), entityInstance, dateAttribute,
                createdBy);
    }

    public EntityDateAttribute createEntityDateAttribute(EntityAttributePK entityAttribute, EntityInstance entityInstance,
            Integer dateAttribute, BasePK createdBy) {
        var entityDateAttribute = EntityDateAttributeFactory.getInstance().create(entityAttribute,
                entityInstance.getPrimaryKey(), dateAttribute, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute, EventTypes.CREATE, createdBy);

        return entityDateAttribute;
    }

    public long countEntityDateAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return session.queryForLong("""
                    SELECT COUNT(*)
                    FROM entitydateattributes
                    WHERE enda_ena_entityattributeid = ? AND enda_eni_entityinstanceid = ?
                    """, entityAttribute, entityInstance);
    }

    private static final Map<EntityPermission, String> getEntityDateAttributeHistoryQueries;

    static {
        var queryMap = Map.of(
                EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM entitydateattributes
                WHERE enda_ena_entityattributeid = ? AND enda_eni_entityinstanceid = ?
                ORDER BY enda_thrutime
                _LIMIT_
                """);
        getEntityDateAttributeHistoryQueries = Collections.unmodifiableMap(queryMap);
    }

    public List<EntityDateAttribute> getEntityDateAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return EntityDateAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityDateAttributeHistoryQueries,
                entityAttribute, entityInstance);
    }

    private EntityDateAttribute getEntityDateAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance, EntityPermission entityPermission) {
        EntityDateAttribute entityDateAttribute;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitydateattributes " +
                        "WHERE enda_ena_entityattributeid = ? AND enda_eni_entityinstanceid = ? AND enda_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitydateattributes " +
                        "WHERE enda_ena_entityattributeid = ? AND enda_eni_entityinstanceid = ? AND enda_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = EntityDateAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityDateAttribute = EntityDateAttributeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityDateAttribute;
    }
    
    public EntityDateAttribute getEntityDateAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityDateAttribute(entityAttribute, entityInstance, EntityPermission.READ_ONLY);
    }
    
    public EntityDateAttribute getEntityDateAttributeForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityDateAttribute(entityAttribute, entityInstance, EntityPermission.READ_WRITE);
    }
    
    public EntityDateAttributeValue getEntityDateAttributeValueForUpdate(EntityDateAttribute entityDateAttribute) {
        return entityDateAttribute == null? null: entityDateAttribute.getEntityDateAttributeValue().clone();
    }
    
    public EntityDateAttributeValue getEntityDateAttributeValueForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityDateAttributeValueForUpdate(getEntityDateAttributeForUpdate(entityAttribute, entityInstance));
    }
    
    public List<EntityDateAttribute> getEntityDateAttributesByEntityAttributeForUpdate(EntityAttribute entityAttribute) {
        List<EntityDateAttribute> entityDateAttributes;
        
        try {
            var ps = EntityDateAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitydateattributes " +
                    "WHERE enda_ena_entityattributeid = ? AND enda_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityDateAttributes = EntityDateAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityDateAttributes;
    }
    
    public List<EntityDateAttribute> getEntityDateAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        List<EntityDateAttribute> entityDateAttributes;
        
        try {
            var ps = EntityDateAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitydateattributes " +
                    "WHERE enda_eni_entityinstanceid = ? AND enda_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityDateAttributes = EntityDateAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityDateAttributes;
    }
    
    public EntityDateAttributeTransfer getEntityDateAttributeTransfer(UserVisit userVisit, EntityDateAttribute entityDateAttribute, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityDateAttributeTransferCache().getEntityDateAttributeTransfer(entityDateAttribute, entityInstance);
    }
    
    public void updateEntityDateAttributeFromValue(EntityDateAttributeValue entityDateAttributeValue, BasePK updatedBy) {
        if(entityDateAttributeValue.hasBeenModified()) {
            var entityDateAttribute = EntityDateAttributeFactory.getInstance().getEntityFromValue(session, EntityPermission.READ_WRITE, entityDateAttributeValue);
            var entityAttribute = entityDateAttribute.getEntityAttribute();
            var entityInstance = entityDateAttribute.getEntityInstance();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityDateAttribute.setThruTime(session.START_TIME_LONG);
                entityDateAttribute.store();
            } else {
                entityDateAttribute.remove();
            }
            
            EntityDateAttributeFactory.getInstance().create(entityAttribute, entityInstance, entityDateAttributeValue.getDateAttribute(), session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityDateAttribute(EntityDateAttribute entityDateAttribute, BasePK deletedBy) {
        var entityAttribute = entityDateAttribute.getEntityAttribute();
        var entityInstance = entityDateAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityDateAttribute.setThruTime(session.START_TIME_LONG);
        } else {
            entityDateAttribute.remove();
        }
        
        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteEntityDateAttributes(List<EntityDateAttribute> entityDateAttributes, BasePK deletedBy) {
        entityDateAttributes.forEach((entityDateAttribute) -> 
                deleteEntityDateAttribute(entityDateAttribute, deletedBy)
        );
    }
    
    public void deleteEntityDateAttributesByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        deleteEntityDateAttributes(getEntityDateAttributesByEntityAttributeForUpdate(entityAttribute), deletedBy);
    }
    
    public void deleteEntityDateAttributesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteEntityDateAttributes(getEntityDateAttributesByEntityInstanceForUpdate(entityInstance), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Entity Integer Defaults
    // --------------------------------------------------------------------------------

    public EntityIntegerDefault createEntityIntegerDefault(EntityAttribute entityAttribute, Integer integerAttribute,
            BasePK createdBy) {
        var entityIntegerDefault = EntityIntegerDefaultFactory.getInstance().create(entityAttribute,
                integerAttribute, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityIntegerDefault.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return entityIntegerDefault;
    }

    public long countEntityIntegerDefaultHistory(EntityAttribute entityAttribute) {
        return session.queryForLong("""
                    SELECT COUNT(*)
                    FROM entityintegerdefaults
                    WHERE enidef_thrutime = ?
                    """, entityAttribute);
    }

    private static final Map<EntityPermission, String> getEntityIntegerDefaultHistoryQueries;

    static {
        getEntityIntegerDefaultHistoryQueries = Map.of(
                EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM entityintegerdefaults
                WHERE enidef_ena_entityattributeid = ?
                ORDER BY enidef_thrutime
                _LIMIT_
                """);
    }

    public List<EntityIntegerDefault> getEntityIntegerDefaultHistory(EntityAttribute entityAttribute) {
        return EntityIntegerDefaultFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityIntegerDefaultHistoryQueries,
                entityAttribute);
    }

    private EntityIntegerDefault getEntityIntegerDefault(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        EntityIntegerDefault entityIntegerDefault;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityintegerdefaults " +
                        "WHERE enidef_ena_entityattributeid = ? AND enidef_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityintegerdefaults " +
                        "WHERE enidef_ena_entityattributeid = ? AND enidef_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = EntityIntegerDefaultFactory.getInstance().prepareStatement(query);

            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            entityIntegerDefault = EntityIntegerDefaultFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityIntegerDefault;
    }

    public EntityIntegerDefault getEntityIntegerDefault(EntityAttribute entityAttribute) {
        return getEntityIntegerDefault(entityAttribute, EntityPermission.READ_ONLY);
    }

    public EntityIntegerDefault getEntityIntegerDefaultForUpdate(EntityAttribute entityAttribute) {
        return getEntityIntegerDefault(entityAttribute, EntityPermission.READ_WRITE);
    }

    public EntityIntegerDefaultValue getEntityIntegerDefaultValueForUpdate(EntityIntegerDefault entityIntegerDefault) {
        return entityIntegerDefault == null? null: entityIntegerDefault.getEntityIntegerDefaultValue().clone();
    }

    public EntityIntegerDefaultValue getEntityIntegerDefaultValueForUpdate(EntityAttribute entityAttribute) {
        return getEntityIntegerDefaultValueForUpdate(getEntityIntegerDefaultForUpdate(entityAttribute));
    }

    public EntityIntegerDefaultTransfer getEntityIntegerDefaultTransfer(UserVisit userVisit, EntityIntegerDefault entityIntegerDefault) {
        return getCoreTransferCaches(userVisit).getEntityIntegerDefaultTransferCache().getEntityIntegerDefaultTransfer(entityIntegerDefault);
    }

    public void updateEntityIntegerDefaultFromValue(EntityIntegerDefaultValue entityIntegerDefaultValue, BasePK updatedBy) {
        if(entityIntegerDefaultValue.hasBeenModified()) {
            var entityIntegerDefault = EntityIntegerDefaultFactory.getInstance().getEntityFromValue(session, EntityPermission.READ_WRITE, entityIntegerDefaultValue);
            var entityAttribute = entityIntegerDefault.getEntityAttribute();

            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityIntegerDefault.setThruTime(session.START_TIME_LONG);
                entityIntegerDefault.store();
            } else {
                entityIntegerDefault.remove();
            }

            entityIntegerDefault = EntityIntegerDefaultFactory.getInstance().create(entityAttribute,
                    entityIntegerDefaultValue.getIntegerAttribute(), session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityIntegerDefault.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteEntityIntegerDefault(EntityIntegerDefault entityIntegerDefault, BasePK deletedBy) {
        var entityAttribute = entityIntegerDefault.getEntityAttribute();

        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityIntegerDefault.setThruTime(session.START_TIME_LONG);
        } else {
            entityIntegerDefault.remove();
        }

        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityIntegerDefault.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteEntityIntegerDefaultByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        var entityIntegerDefault = getEntityIntegerDefaultForUpdate(entityAttribute);

        if(entityIntegerDefault != null) {
            deleteEntityIntegerDefault(entityIntegerDefault, deletedBy);
        }
    }

    // --------------------------------------------------------------------------------
    //   Entity Integer Attributes
    // --------------------------------------------------------------------------------

    public EntityIntegerAttribute createEntityIntegerAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Integer integerAttribute, BasePK createdBy) {
        return createEntityIntegerAttribute(entityAttribute.getPrimaryKey(), entityInstance, integerAttribute,
                createdBy);
    }

    public EntityIntegerAttribute createEntityIntegerAttribute(EntityAttributePK entityAttribute, EntityInstance entityInstance,
            Integer integerAttribute, BasePK createdBy) {
        var entityIntegerAttribute = EntityIntegerAttributeFactory.getInstance().create(entityAttribute,
                entityInstance.getPrimaryKey(), integerAttribute, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute, EventTypes.CREATE, createdBy);

        return entityIntegerAttribute;
    }
    
    public long countEntityIntegerAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return session.queryForLong("""
                    SELECT COUNT(*)
                    FROM entityintegerattributes
                    WHERE enia_ena_entityattributeid = ? AND enia_eni_entityinstanceid = ?
                    """, entityAttribute, entityInstance);
    }

    private static final Map<EntityPermission, String> getEntityIntegerAttributeHistoryQueries;

    static {
        var queryMap = Map.of(
                EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM entityintegerattributes
                WHERE enia_ena_entityattributeid = ? AND enia_eni_entityinstanceid = ?
                ORDER BY enia_thrutime
                _LIMIT_
                """);
        getEntityIntegerAttributeHistoryQueries = Collections.unmodifiableMap(queryMap);
    }

    public List<EntityIntegerAttribute> getEntityIntegerAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return EntityIntegerAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityIntegerAttributeHistoryQueries,
                entityAttribute, entityInstance);
    }

    private EntityIntegerAttribute getEntityIntegerAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityPermission entityPermission) {
        EntityIntegerAttribute entityIntegerAttribute;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityintegerattributes " +
                        "WHERE enia_ena_entityattributeid = ? AND enia_eni_entityinstanceid = ? AND enia_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityintegerattributes " +
                        "WHERE enia_ena_entityattributeid = ? AND enia_eni_entityinstanceid = ? AND enia_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = EntityIntegerAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityIntegerAttribute = EntityIntegerAttributeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityIntegerAttribute;
    }
    
    public EntityIntegerAttribute getEntityIntegerAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityIntegerAttribute(entityAttribute, entityInstance, EntityPermission.READ_ONLY);
    }
    
    public EntityIntegerAttribute getEntityIntegerAttributeForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityIntegerAttribute(entityAttribute, entityInstance, EntityPermission.READ_WRITE);
    }
    
    public EntityIntegerAttributeValue getEntityIntegerAttributeValueForUpdate(EntityIntegerAttribute entityIntegerAttribute) {
        return entityIntegerAttribute == null? null: entityIntegerAttribute.getEntityIntegerAttributeValue().clone();
    }
    
    public EntityIntegerAttributeValue getEntityIntegerAttributeValueForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityIntegerAttributeValueForUpdate(getEntityIntegerAttributeForUpdate(entityAttribute, entityInstance));
    }
    
    public List<EntityIntegerAttribute> getEntityIntegerAttributesByEntityAttributeForUpdate(EntityAttribute entityAttribute) {
        List<EntityIntegerAttribute> entityIntegerAttributes;
        
        try {
            var ps = EntityIntegerAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entityintegerattributes " +
                    "WHERE enia_ena_entityattributeid = ? AND enia_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityIntegerAttributes = EntityIntegerAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityIntegerAttributes;
    }
    
    public List<EntityIntegerAttribute> getEntityIntegerAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        List<EntityIntegerAttribute> entityIntegerAttributes;
        
        try {
            var ps = EntityIntegerAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entityintegerattributes " +
                    "WHERE enia_eni_entityinstanceid = ? AND enia_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityIntegerAttributes = EntityIntegerAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityIntegerAttributes;
    }
    
    public EntityIntegerAttributeTransfer getEntityIntegerAttributeTransfer(UserVisit userVisit, EntityIntegerAttribute entityIntegerAttribute, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityIntegerAttributeTransferCache().getEntityIntegerAttributeTransfer(entityIntegerAttribute, entityInstance);
    }
    
    public void updateEntityIntegerAttributeFromValue(EntityIntegerAttributeValue entityIntegerAttributeValue, BasePK updatedBy) {
        if(entityIntegerAttributeValue.hasBeenModified()) {
            var entityIntegerAttribute = EntityIntegerAttributeFactory.getInstance().getEntityFromValue(session, EntityPermission.READ_WRITE, entityIntegerAttributeValue);
            var entityAttribute = entityIntegerAttribute.getEntityAttribute();
            var entityInstance = entityIntegerAttribute.getEntityInstance();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityIntegerAttribute.setThruTime(session.START_TIME_LONG);
                entityIntegerAttribute.store();
            } else {
                entityIntegerAttribute.remove();
            }
            
            EntityIntegerAttributeFactory.getInstance().create(entityAttribute, entityInstance, entityIntegerAttributeValue.getIntegerAttribute(), session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    public void deleteEntityIntegerAttribute(EntityIntegerAttribute entityIntegerAttribute, BasePK deletedBy) {
        var entityAttribute = entityIntegerAttribute.getEntityAttribute();
        var entityInstance = entityIntegerAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityIntegerAttribute.setThruTime(session.START_TIME_LONG);
        } else {
            entityIntegerAttribute.remove();
        }
        
        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteEntityIntegerAttributes(List<EntityIntegerAttribute> entityIntegerAttributes, BasePK deletedBy) {
        entityIntegerAttributes.forEach((entityIntegerAttribute) -> 
                deleteEntityIntegerAttribute(entityIntegerAttribute, deletedBy)
        );
    }
    
    public void deleteEntityIntegerAttributesByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        deleteEntityIntegerAttributes(getEntityIntegerAttributesByEntityAttributeForUpdate(entityAttribute), deletedBy);
    }
    
    public void deleteEntityIntegerAttributesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteEntityIntegerAttributes(getEntityIntegerAttributesByEntityInstanceForUpdate(entityInstance), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Entity List Item Defaults
    // --------------------------------------------------------------------------------

    public EntityListItemDefault createEntityListItemDefault(EntityAttribute entityAttribute,
            EntityListItem entityListItem, BasePK createdBy) {
        var entityListItemDefault = EntityListItemDefaultFactory.getInstance().create(session,
                entityAttribute, entityListItem, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityListItemDefault.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return entityListItemDefault;
    }

    public long countEntityListItemDefaultHistory(EntityAttribute entityAttribute) {
        return session.queryForLong("""
                    SELECT COUNT(*)
                    FROM entitylistitemdefaults
                    WHERE eladef_ena_entityattributeid = ?
                    """, entityAttribute);
    }

    private static final Map<EntityPermission, String> getEntityListItemDefaultHistoryQueries;

    static {
        getEntityListItemDefaultHistoryQueries = Map.of(
                EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM entitylistitemdefaults
                WHERE eladef_ena_entityattributeid = ?
                ORDER BY eladef_thrutime
                _LIMIT_
                """);
    }

    public List<EntityListItemDefault> getEntityListItemDefaultHistory(EntityAttribute entityAttribute) {
        return EntityListItemDefaultFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityListItemDefaultHistoryQueries,
                entityAttribute);
    }

    private EntityListItemDefault getEntityListItemDefault(EntityAttribute entityAttribute,
            EntityPermission entityPermission) {
        EntityListItemDefault entityListItemDefault;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylistitemdefaults " +
                        "WHERE eladef_ena_entityattributeid = ? AND eladef_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylistitemdefaults " +
                        "WHERE eladef_ena_entityattributeid = ? AND eladef_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = EntityListItemDefaultFactory.getInstance().prepareStatement(query);

            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            entityListItemDefault = EntityListItemDefaultFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityListItemDefault;
    }

    public EntityListItemDefault getEntityListItemDefault(EntityAttribute entityAttribute) {
        return getEntityListItemDefault(entityAttribute, EntityPermission.READ_ONLY);
    }

    public EntityListItemDefault getEntityListItemDefaultForUpdate(EntityAttribute entityAttribute) {
        return getEntityListItemDefault(entityAttribute, EntityPermission.READ_WRITE);
    }

    public EntityListItemDefaultValue getEntityListItemDefaultValueForUpdate(EntityListItemDefault entityListItemDefault) {
        return entityListItemDefault == null? null: entityListItemDefault.getEntityListItemDefaultValue().clone();
    }

    public EntityListItemDefaultValue getEntityListItemDefaultValueForUpdate(EntityAttribute entityAttribute) {
        return getEntityListItemDefaultValueForUpdate(getEntityListItemDefaultForUpdate(entityAttribute));
    }

    private EntityListItemDefault getEntityListItemDefaultByEntityListItem(EntityListItem entityListItem, EntityPermission entityPermission) {
        EntityListItemDefault entityListItemDefault;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ "
                        + "FROM entitylistitemdefaults "
                        + "WHERE eladef_eli_entitylistitemid = ? AND eladef_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ "
                        + "FROM entitylistitemdefaults "
                        + "WHERE eladef_eli_entitylistitemid = ? AND eladef_thrutime = ? "
                        + "FOR UPDATE";
            }

            var ps = EntityListItemDefaultFactory.getInstance().prepareStatement(query);

            ps.setLong(1, entityListItem.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            entityListItemDefault = EntityListItemDefaultFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityListItemDefault;
    }

    public EntityListItemDefault getEntityListItemDefaultByEntityListItem(EntityListItem entityListItem) {
        return getEntityListItemDefaultByEntityListItem(entityListItem, EntityPermission.READ_ONLY);
    }

    public EntityListItemDefault getEntityListItemDefaultByEntityListItemForUpdate(EntityListItem entityListItem) {
        return getEntityListItemDefaultByEntityListItem(entityListItem, EntityPermission.READ_WRITE);
    }

    public EntityListItemDefaultTransfer getEntityListItemDefaultTransfer(UserVisit userVisit, EntityListItemDefault entityListItemDefault) {
        return getCoreTransferCaches(userVisit).getEntityListItemDefaultTransferCache().getEntityListItemDefaultTransfer(entityListItemDefault);
    }

    public void updateEntityListItemDefaultFromValue(EntityListItemDefaultValue entityListItemDefaultValue, BasePK updatedBy) {
        if(entityListItemDefaultValue.hasBeenModified()) {
            var entityListItemDefault = EntityListItemDefaultFactory.getInstance().getEntityFromValue(session, EntityPermission.READ_WRITE, entityListItemDefaultValue);
            var entityAttribute = entityListItemDefault.getEntityAttribute();

            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityListItemDefault.setThruTime(session.START_TIME_LONG);
                entityListItemDefault.store();
            } else {
                entityListItemDefault.remove();
            }

            entityListItemDefault = EntityListItemDefaultFactory.getInstance().create(entityAttribute.getPrimaryKey(),
                    entityListItemDefaultValue.getEntityListItemPK(), session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityListItemDefault.getPrimaryKey(), EventTypes.DELETE, updatedBy);
        }
    }

    public void deleteEntityListItemDefault(EntityListItemDefault entityListItemDefault, BasePK deletedBy) {
        var entityAttribute = entityListItemDefault.getEntityAttribute();

        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityListItemDefault.setThruTime(session.START_TIME_LONG);
        } else {
            entityListItemDefault.remove();
        }

        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityListItemDefault.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteEntityListItemDefaultByEntityListItem(EntityListItem entityListItem, BasePK deletedBy) {
        var deleteEntityListItemDefault = getEntityListItemDefaultByEntityListItemForUpdate(entityListItem);

        if(deleteEntityListItemDefault != null) {
            deleteEntityListItemDefault(deleteEntityListItemDefault, deletedBy);
        }
    }

    // --------------------------------------------------------------------------------
    //   Entity List Item Attributes
    // --------------------------------------------------------------------------------

    public EntityListItemAttribute createEntityListItemAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityListItem entityListItem, BasePK createdBy) {
        return createEntityListItemAttribute(entityAttribute.getPrimaryKey(), entityInstance, entityListItem, createdBy);
    }

    public EntityListItemAttribute createEntityListItemAttribute(EntityAttributePK entityAttribute, EntityInstance entityInstance,
            EntityListItem entityListItem, BasePK createdBy) {
        var entityListItemAttribute = EntityListItemAttributeFactory.getInstance().create(entityAttribute,
                entityInstance.getPrimaryKey(), entityListItem.getPrimaryKey(), session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute, EventTypes.CREATE, createdBy);

        return entityListItemAttribute;
    }

    public long countEntityListItemAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return session.queryForLong("""
                    SELECT COUNT(*)
                    FROM entitylistitemattributes
                    WHERE ela_ena_entityattributeid = ? AND ela_eni_entityinstanceid = ?
                    """, entityAttribute, entityInstance);
    }

    private static final Map<EntityPermission, String> getEntityListItemAttributeHistoryQueries;

    static {
        var queryMap = Map.of(
                EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM entitylistitemattributes
                WHERE ela_ena_entityattributeid = ? AND ela_eni_entityinstanceid = ?
                ORDER BY ela_thrutime
                _LIMIT_
                """);
        getEntityListItemAttributeHistoryQueries = Collections.unmodifiableMap(queryMap);
    }

    public List<EntityListItemAttribute> getEntityListItemAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return EntityListItemAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityListItemAttributeHistoryQueries,
                entityAttribute, entityInstance);
    }

    private EntityListItemAttribute getEntityListItemAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityPermission entityPermission) {
        EntityListItemAttribute entityListItemAttribute;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylistitemattributes " +
                        "WHERE ela_ena_entityattributeid = ? AND ela_eni_entityinstanceid = ? AND ela_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylistitemattributes " +
                        "WHERE ela_ena_entityattributeid = ? AND ela_eni_entityinstanceid = ? AND ela_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = EntityListItemAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityListItemAttribute = EntityListItemAttributeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityListItemAttribute;
    }
    
    public EntityListItemAttribute getEntityListItemAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityListItemAttribute(entityAttribute, entityInstance, EntityPermission.READ_ONLY);
    }
    
    public EntityListItemAttribute getEntityListItemAttributeForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityListItemAttribute(entityAttribute, entityInstance, EntityPermission.READ_WRITE);
    }
    
    public EntityListItemAttributeValue getEntityListItemAttributeValueForUpdate(EntityListItemAttribute entityListItemAttribute) {
        return entityListItemAttribute == null? null: entityListItemAttribute.getEntityListItemAttributeValue().clone();
    }
    
    public EntityListItemAttributeValue getEntityListItemAttributeValueForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityListItemAttributeValueForUpdate(getEntityListItemAttributeForUpdate(entityAttribute, entityInstance));
    }
    
    private List<EntityListItemAttribute> getEntityListItemAttributesByEntityListItem(EntityListItem entityListItem, EntityPermission entityPermission) {
        List<EntityListItemAttribute> entityListItemAttributes;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ "
                        + "FROM entitylistitemattributes, entityinstances "
                        + "WHERE ela_eli_entitylistitemid = ? AND ela_thrutime = ? "
                        + "AND ela_eni_entityinstanceid = eni_entityinstanceid "
                        + "ORDER BY eni_entityuniqueid";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ "
                        + "FROM entitylistitemattributes "
                        + "WHERE ela_eli_entitylistitemid = ? AND ela_thrutime = ? "
                        + "FOR UPDATE";
            }

            var ps = EntityListItemAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityListItem.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityListItemAttributes = EntityListItemAttributeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityListItemAttributes;
    }
    
    public List<EntityListItemAttribute> getEntityListItemAttributesByEntityListItem(EntityListItem entityListItem) {
        return getEntityListItemAttributesByEntityListItem(entityListItem, EntityPermission.READ_ONLY);
    }
    
    public List<EntityListItemAttribute> getEntityListItemAttributesByEntityListItemForUpdate(EntityListItem entityListItem) {
        return getEntityListItemAttributesByEntityListItem(entityListItem, EntityPermission.READ_WRITE);
    }
    
    public List<EntityListItemAttribute> getEntityListItemAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        List<EntityListItemAttribute> entityListItemAttributes;
        
        try {
            var ps = EntityListItemAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitylistitemattributes " +
                    "WHERE ela_eni_entityinstanceid = ? AND ela_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityListItemAttributes = EntityListItemAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityListItemAttributes;
    }
    
    public EntityListItemAttributeTransfer getEntityListItemAttributeTransfer(UserVisit userVisit, EntityListItemAttribute entityListItemAttribute, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityListItemAttributeTransferCache().getEntityListItemAttributeTransfer(entityListItemAttribute, entityInstance);
    }
    
    public void updateEntityListItemAttributeFromValue(EntityListItemAttributeValue entityListItemAttributeValue, BasePK updatedBy) {
        if(entityListItemAttributeValue.hasBeenModified()) {
            var entityListItemAttribute = EntityListItemAttributeFactory.getInstance().getEntityFromValue(session, EntityPermission.READ_WRITE, entityListItemAttributeValue);
            var entityAttribute = entityListItemAttribute.getEntityAttribute();
            var entityInstance = entityListItemAttribute.getEntityInstance();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityListItemAttribute.setThruTime(session.START_TIME_LONG);
                entityListItemAttribute.store();
            } else {
                entityListItemAttribute.remove();
            }
            
            EntityListItemAttributeFactory.getInstance().create(entityAttribute.getPrimaryKey(), entityInstance.getPrimaryKey(), entityListItemAttributeValue.getEntityListItemPK(),
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityListItemAttribute(EntityListItemAttribute entityListItemAttribute, BasePK deletedBy) {
        var entityAttribute = entityListItemAttribute.getEntityAttribute();
        var entityInstance = entityListItemAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityListItemAttribute.setThruTime(session.START_TIME_LONG);
        } else {
            entityListItemAttribute.remove();
        }
        
        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteEntityListItemAttributes(List<EntityListItemAttribute> entityListItemAttributes, BasePK deletedBy) {
        entityListItemAttributes.forEach((entityListItemAttribute) -> 
                deleteEntityListItemAttribute(entityListItemAttribute, deletedBy)
        );
    }
    
    public void deleteEntityListItemAttributesByEntityListItem(EntityListItem entityListItem, BasePK deletedBy) {
        deleteEntityListItemAttributes(getEntityListItemAttributesByEntityListItemForUpdate(entityListItem), deletedBy);
    }
    
    public void deleteEntityListItemAttributesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteEntityListItemAttributes(getEntityListItemAttributesByEntityInstanceForUpdate(entityInstance), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Entity Long Defaults
    // --------------------------------------------------------------------------------

    public EntityLongDefault createEntityLongDefault(EntityAttribute entityAttribute, Long longAttribute,
            BasePK createdBy) {
        var entityLongDefault = EntityLongDefaultFactory.getInstance().create(entityAttribute,
                longAttribute, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityLongDefault.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return entityLongDefault;
    }

    public long countEntityLongDefaultHistory(EntityAttribute entityAttribute) {
        return session.queryForLong("""
                    SELECT COUNT(*)
                    FROM entitylongdefaults
                    WHERE enldef_thrutime = ?
                    """, entityAttribute);
    }

    private static final Map<EntityPermission, String> getEntityLongDefaultHistoryQueries;

    static {
        getEntityLongDefaultHistoryQueries = Map.of(
                EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM entitylongdefaults
                WHERE enldef_ena_entityattributeid = ?
                ORDER BY enldef_thrutime
                _LIMIT_
                """);
    }

    public List<EntityLongDefault> getEntityLongDefaultHistory(EntityAttribute entityAttribute) {
        return EntityLongDefaultFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityLongDefaultHistoryQueries,
                entityAttribute);
    }

    private EntityLongDefault getEntityLongDefault(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        EntityLongDefault entityLongDefault;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylongdefaults " +
                        "WHERE enldef_ena_entityattributeid = ? AND enldef_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylongdefaults " +
                        "WHERE enldef_ena_entityattributeid = ? AND enldef_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = EntityLongDefaultFactory.getInstance().prepareStatement(query);

            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            entityLongDefault = EntityLongDefaultFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityLongDefault;
    }

    public EntityLongDefault getEntityLongDefault(EntityAttribute entityAttribute) {
        return getEntityLongDefault(entityAttribute, EntityPermission.READ_ONLY);
    }

    public EntityLongDefault getEntityLongDefaultForUpdate(EntityAttribute entityAttribute) {
        return getEntityLongDefault(entityAttribute, EntityPermission.READ_WRITE);
    }

    public EntityLongDefaultValue getEntityLongDefaultValueForUpdate(EntityLongDefault entityLongDefault) {
        return entityLongDefault == null? null: entityLongDefault.getEntityLongDefaultValue().clone();
    }

    public EntityLongDefaultValue getEntityLongDefaultValueForUpdate(EntityAttribute entityAttribute) {
        return getEntityLongDefaultValueForUpdate(getEntityLongDefaultForUpdate(entityAttribute));
    }

    public EntityLongDefaultTransfer getEntityLongDefaultTransfer(UserVisit userVisit, EntityLongDefault entityLongDefault) {
        return getCoreTransferCaches(userVisit).getEntityLongDefaultTransferCache().getEntityLongDefaultTransfer(entityLongDefault);
    }

    public void updateEntityLongDefaultFromValue(EntityLongDefaultValue entityLongDefaultValue, BasePK updatedBy) {
        if(entityLongDefaultValue.hasBeenModified()) {
            var entityLongDefault = EntityLongDefaultFactory.getInstance().getEntityFromValue(session, EntityPermission.READ_WRITE, entityLongDefaultValue);
            var entityAttribute = entityLongDefault.getEntityAttribute();

            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityLongDefault.setThruTime(session.START_TIME_LONG);
                entityLongDefault.store();
            } else {
                entityLongDefault.remove();
            }

            entityLongDefault = EntityLongDefaultFactory.getInstance().create(entityAttribute,
                    entityLongDefaultValue.getLongAttribute(), session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityLongDefault.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteEntityLongDefault(EntityLongDefault entityLongDefault, BasePK deletedBy) {
        var entityAttribute = entityLongDefault.getEntityAttribute();

        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityLongDefault.setThruTime(session.START_TIME_LONG);
        } else {
            entityLongDefault.remove();
        }

        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityLongDefault.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteEntityLongDefaultByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        var entityLongDefault = getEntityLongDefaultForUpdate(entityAttribute);

        if(entityLongDefault != null) {
            deleteEntityLongDefault(entityLongDefault, deletedBy);
        }
    }

    // --------------------------------------------------------------------------------
    //   Entity Long Attributes
    // --------------------------------------------------------------------------------

    public EntityLongAttribute createEntityLongAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Long longAttribute, BasePK createdBy) {
        return createEntityLongAttribute(entityAttribute.getPrimaryKey(), entityInstance, longAttribute,
                createdBy);
    }

    public EntityLongAttribute createEntityLongAttribute(EntityAttributePK entityAttribute, EntityInstance entityInstance,
            Long longAttribute, BasePK createdBy) {
        var entityLongAttribute = EntityLongAttributeFactory.getInstance().create(entityAttribute,
                entityInstance.getPrimaryKey(), longAttribute, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute, EventTypes.CREATE, createdBy);

        return entityLongAttribute;
    }

    public long countEntityLongAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return session.queryForLong("""
                    SELECT COUNT(*)
                    FROM entitylongattributes
                    WHERE enla_ena_entityattributeid = ? AND enla_eni_entityinstanceid = ?
                    """, entityAttribute, entityInstance);
    }

    private static final Map<EntityPermission, String> getEntityLongAttributeHistoryQueries;

    static {
        var queryMap = Map.of(
                EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM entitylongattributes
                WHERE enla_ena_entityattributeid = ? AND enla_eni_entityinstanceid = ?
                ORDER BY enla_thrutime
                _LIMIT_
                """);
        getEntityLongAttributeHistoryQueries = Collections.unmodifiableMap(queryMap);
    }

    public List<EntityLongAttribute> getEntityLongAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return EntityLongAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityLongAttributeHistoryQueries,
                entityAttribute, entityInstance);
    }

    private EntityLongAttribute getEntityLongAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityPermission entityPermission) {
        EntityLongAttribute entityLongAttribute;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylongattributes " +
                        "WHERE enla_ena_entityattributeid = ? AND enla_eni_entityinstanceid = ? AND enla_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitylongattributes " +
                        "WHERE enla_ena_entityattributeid = ? AND enla_eni_entityinstanceid = ? AND enla_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = EntityLongAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityLongAttribute = EntityLongAttributeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityLongAttribute;
    }
    
    public EntityLongAttribute getEntityLongAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityLongAttribute(entityAttribute, entityInstance, EntityPermission.READ_ONLY);
    }
    
    public EntityLongAttribute getEntityLongAttributeForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityLongAttribute(entityAttribute, entityInstance, EntityPermission.READ_WRITE);
    }
    
    public EntityLongAttributeValue getEntityLongAttributeValueForUpdate(EntityLongAttribute entityLongAttribute) {
        return entityLongAttribute == null? null: entityLongAttribute.getEntityLongAttributeValue().clone();
    }
    
    public EntityLongAttributeValue getEntityLongAttributeValueForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityLongAttributeValueForUpdate(getEntityLongAttributeForUpdate(entityAttribute, entityInstance));
    }
    
    public List<EntityLongAttribute> getEntityLongAttributesByEntityAttributeForUpdate(EntityAttribute entityAttribute) {
        List<EntityLongAttribute> entityLongAttributes;
        
        try {
            var ps = EntityLongAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitylongattributes " +
                    "WHERE enla_ena_entityattributeid = ? AND enla_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityLongAttributes = EntityLongAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityLongAttributes;
    }
    
    public List<EntityLongAttribute> getEntityLongAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        List<EntityLongAttribute> entityLongAttributes;
        
        try {
            var ps = EntityLongAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitylongattributes " +
                    "WHERE enla_eni_entityinstanceid = ? AND enla_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityLongAttributes = EntityLongAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityLongAttributes;
    }
    
    public EntityLongAttributeTransfer getEntityLongAttributeTransfer(UserVisit userVisit, EntityLongAttribute entityLongAttribute, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityLongAttributeTransferCache().getEntityLongAttributeTransfer(entityLongAttribute, entityInstance);
    }
    
    public void updateEntityLongAttributeFromValue(EntityLongAttributeValue entityLongAttributeValue, BasePK updatedBy) {
        if(entityLongAttributeValue.hasBeenModified()) {
            var entityLongAttribute = EntityLongAttributeFactory.getInstance().getEntityFromValue(session, EntityPermission.READ_WRITE, entityLongAttributeValue);
            var entityAttribute = entityLongAttribute.getEntityAttribute();
            var entityInstance = entityLongAttribute.getEntityInstance();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityLongAttribute.setThruTime(session.START_TIME_LONG);
                entityLongAttribute.store();
            } else {
                entityLongAttribute.remove();
            }
            
            EntityLongAttributeFactory.getInstance().create(entityAttribute, entityInstance, entityLongAttributeValue.getLongAttribute(), session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityLongAttribute(EntityLongAttribute entityLongAttribute, BasePK deletedBy) {
        var entityAttribute = entityLongAttribute.getEntityAttribute();
        var entityInstance = entityLongAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityLongAttribute.setThruTime(session.START_TIME_LONG);
        } else {
            entityLongAttribute.remove();
        }
        
        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteEntityLongAttributes(List<EntityLongAttribute> entityLongAttributes, BasePK deletedBy) {
        entityLongAttributes.forEach((entityLongAttribute) -> 
                deleteEntityLongAttribute(entityLongAttribute, deletedBy)
        );
    }
    
    public void deleteEntityLongAttributesByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        deleteEntityLongAttributes(getEntityLongAttributesByEntityAttributeForUpdate(entityAttribute), deletedBy);
    }
    
    public void deleteEntityLongAttributesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteEntityLongAttributes(getEntityLongAttributesByEntityInstanceForUpdate(entityInstance), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Entity Multiple List Item Defaults
    // --------------------------------------------------------------------------------

    public EntityMultipleListItemDefault createEntityMultipleListItemDefault(EntityAttribute entityAttribute,
            EntityListItem entityListItem, BasePK createdBy) {
        var entityMultipleListItemDefault = EntityMultipleListItemDefaultFactory.getInstance().create(session,
                entityAttribute, entityListItem, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return entityMultipleListItemDefault;
    }

    public long countEntityMultipleListItemDefaults(EntityAttribute entityAttribute) {
        return session.queryForLong("""
                    SELECT COUNT(*)
                    FROM entitymultiplelistitemdefaults
                    WHERE emlidef_ena_entityattributeid = ? AND emlidef_thrutime = ?
                    """, entityAttribute, Session.MAX_TIME);
    }

    public long countEntityMultipleListItemDefaultHistory(EntityAttribute entityAttribute) {
        return session.queryForLong("""
                    SELECT COUNT(*)
                    FROM entitymultiplelistitemdefaults
                    WHERE emlidef_ena_entityattributeid = ?
                    """, entityAttribute);
    }

    private static final Map<EntityPermission, String> getEntityMultipleListItemDefaultHistoryQueries;

    static {
        getEntityMultipleListItemDefaultHistoryQueries = Map.of(
                EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM entitymultiplelistitemdefaults
                WHERE emlidef_ena_entityattributeid = ?
                ORDER BY emlidef_thrutime
                _LIMIT_
                """);
    }

    public List<EntityMultipleListItemDefault> getEntityMultipleListItemDefaultHistory(EntityAttribute entityAttribute) {
        return EntityMultipleListItemDefaultFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityMultipleListItemDefaultHistoryQueries,
                entityAttribute);
    }

    public List<EntityMultipleListItemDefault> getEntityMultipleListItemDefaults(EntityAttribute entityAttribute) {
        List<EntityMultipleListItemDefault> entityMultipleListItemDefaults;

        try {
            var ps = EntityMultipleListItemDefaultFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ "
                            + "FROM entitymultiplelistitemdefaults, entitylistitems, entitylistitemdetails "
                            + "WHERE emlidef_ena_entityattributeid = ? AND emlidef_thrutime = ? "
                            + "AND emlidef_eli_entitylistitemid = eli_entitylistitemid AND eli_lastdetailid = elidt_entitylistitemdetailid "
                            + "ORDER BY elidt_sortorder, elidt_entitylistitemname");

            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            entityMultipleListItemDefaults = EntityMultipleListItemDefaultFactory.getInstance().getEntitiesFromQuery(session,
                    EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityMultipleListItemDefaults;
    }

    private EntityMultipleListItemDefault getEntityMultipleListItemDefault(EntityAttribute entityAttribute,
            EntityListItem entityListItem, EntityPermission entityPermission) {
        EntityMultipleListItemDefault entityMultipleListItemDefault;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitymultiplelistitemdefaults " +
                        "WHERE emlidef_ena_entityattributeid = ? AND emlidef_eli_entitylistitemid = ? " +
                        "AND emlidef_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitymultiplelistitemdefaults " +
                        "WHERE emlidef_ena_entityattributeid = ? AND emlidef_eli_entitylistitemid = ? " +
                        "AND emlidef_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = EntityMultipleListItemDefaultFactory.getInstance().prepareStatement(query);

            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityListItem.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);

            entityMultipleListItemDefault = EntityMultipleListItemDefaultFactory.getInstance().getEntityFromQuery(session,
                    entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityMultipleListItemDefault;
    }

    public EntityMultipleListItemDefault getEntityMultipleListItemDefault(EntityAttribute entityAttribute,
            EntityListItem entityListItem) {
        return getEntityMultipleListItemDefault(entityAttribute, entityListItem, EntityPermission.READ_ONLY);
    }

    public EntityMultipleListItemDefault getEntityMultipleListItemDefaultForUpdate(EntityAttribute entityAttribute,
            EntityListItem entityListItem) {
        return getEntityMultipleListItemDefault(entityAttribute, entityListItem, EntityPermission.READ_WRITE);
    }

    private List<EntityMultipleListItemDefault> getEntityMultipleListItemDefaultsByEntityListItem(EntityListItem entityListItem, EntityPermission entityPermission) {
        List<EntityMultipleListItemDefault> entityMultipleListItemDefaults;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ "
                        + "FROM entitymultiplelistitemdefaults "
                        + "WHERE emlidef_eli_entitylistitemid = ? AND emlidef_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ "
                        + "FROM entitymultiplelistitemdefaults "
                        + "WHERE emlidef_eli_entitylistitemid = ? AND emlidef_thrutime = ? "
                        + "FOR UPDATE";
            }

            var ps = EntityMultipleListItemDefaultFactory.getInstance().prepareStatement(query);

            ps.setLong(1, entityListItem.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            entityMultipleListItemDefaults = EntityMultipleListItemDefaultFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityMultipleListItemDefaults;
    }

    public List<EntityMultipleListItemDefault> getEntityMultipleListItemDefaultsByEntityListItem(EntityListItem entityListItem) {
        return getEntityMultipleListItemDefaultsByEntityListItem(entityListItem, EntityPermission.READ_ONLY);
    }

    public List<EntityMultipleListItemDefault> getEntityMultipleListItemDefaultsByEntityListItemForUpdate(EntityListItem entityListItem) {
        return getEntityMultipleListItemDefaultsByEntityListItem(entityListItem, EntityPermission.READ_WRITE);
    }

    public EntityMultipleListItemDefaultTransfer getEntityMultipleListItemDefaultTransfer(UserVisit userVisit, EntityMultipleListItemDefault entityMultipleListItemDefault) {
        return getCoreTransferCaches(userVisit).getEntityMultipleListItemDefaultTransferCache().getEntityMultipleListItemDefaultTransfer(entityMultipleListItemDefault);
    }

    public List<EntityMultipleListItemDefaultTransfer> getEntityMultipleListItemDefaultTransfers(UserVisit userVisit, Collection<EntityMultipleListItemDefault> entityMultipleListItemDefaults) {
        List<EntityMultipleListItemDefaultTransfer> entityMultipleListItemDefaultTransfers = new ArrayList<>(entityMultipleListItemDefaults.size());
        var entityMultipleListItemDefaultTransferCache = getCoreTransferCaches(userVisit).getEntityMultipleListItemDefaultTransferCache();

        entityMultipleListItemDefaults.forEach((entityMultipleListItemDefault) ->
                entityMultipleListItemDefaultTransfers.add(entityMultipleListItemDefaultTransferCache.getEntityMultipleListItemDefaultTransfer(entityMultipleListItemDefault))
        );

        return entityMultipleListItemDefaultTransfers;
    }

    public List<EntityMultipleListItemDefaultTransfer> getEntityMultipleListItemDefaultTransfers(UserVisit userVisit, EntityAttribute entityAttribute) {
        return getEntityMultipleListItemDefaultTransfers(userVisit, getEntityMultipleListItemDefaults(entityAttribute));
    }

    public void deleteEntityMultipleListItemDefault(EntityMultipleListItemDefault entityMultipleListItemDefault, BasePK deletedBy) {
        var entityAttribute = entityMultipleListItemDefault.getEntityAttribute();

        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityMultipleListItemDefault.setThruTime(session.START_TIME_LONG);
        } else {
            entityMultipleListItemDefault.remove();
        }

        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteEntityMultipleListItemDefaults(List<EntityMultipleListItemDefault> entityMultipleListItemDefaults, BasePK deletedBy) {
        entityMultipleListItemDefaults.forEach((entityMultipleListItemDefault) ->
                deleteEntityMultipleListItemDefault(entityMultipleListItemDefault, deletedBy)
        );
    }

    public void deleteEntityMultipleListItemDefaultsByEntityListItem(EntityListItem entityListItem, BasePK deletedBy) {
        deleteEntityMultipleListItemDefaults(getEntityMultipleListItemDefaultsByEntityListItemForUpdate(entityListItem), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Entity Multiple List Item Attributes
    // --------------------------------------------------------------------------------

    public EntityMultipleListItemAttribute createEntityMultipleListItemAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityListItem entityListItem, BasePK createdBy) {
        return createEntityMultipleListItemAttribute(entityAttribute.getPrimaryKey(), entityInstance, entityListItem, createdBy);
    }

    public EntityMultipleListItemAttribute createEntityMultipleListItemAttribute(EntityAttributePK entityAttribute, EntityInstance entityInstance,
            EntityListItem entityListItem, BasePK createdBy) {
        var entityListItemAttribute = EntityMultipleListItemAttributeFactory.getInstance().create(entityAttribute,
                entityInstance.getPrimaryKey(), entityListItem.getPrimaryKey(), session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute, EventTypes.CREATE, createdBy);

        return entityListItemAttribute;
    }

    public long countEntityMultipleListItemAttributes(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return session.queryForLong("""
                    SELECT COUNT(*)
                    FROM entitymultiplelistitemattributes
                    WHERE emlia_ena_entityattributeid = ? AND emlia_eni_entityinstanceid = ? AND emlia_thrutime = ?
                    """, entityAttribute, entityInstance, Session.MAX_TIME);
    }

    public List<EntityMultipleListItemAttribute> getEntityMultipleListItemAttributes(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        List<EntityMultipleListItemAttribute> entityMultipleListItemAttributes;
        
        try {
            var ps = EntityMultipleListItemAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ "
                    + "FROM entitymultiplelistitemattributes, entitylistitems, entitylistitemdetails "
                    + "WHERE emlia_ena_entityattributeid = ? AND emlia_eni_entityinstanceid = ? AND emlia_thrutime = ? "
                    + "AND emlia_eli_entitylistitemid = eli_entitylistitemid AND eli_lastdetailid = elidt_entitylistitemdetailid "
                    + "ORDER BY elidt_sortorder, elidt_entitylistitemname");
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityMultipleListItemAttributes = EntityMultipleListItemAttributeFactory.getInstance().getEntitiesFromQuery(session,
                    EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityMultipleListItemAttributes;
    }
    
    private EntityMultipleListItemAttribute getEntityMultipleListItemAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityListItem entityListItem, EntityPermission entityPermission) {
        EntityMultipleListItemAttribute entityMultipleListItemAttribute;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitymultiplelistitemattributes " +
                        "WHERE emlia_ena_entityattributeid = ? AND emlia_eni_entityinstanceid = ? AND emlia_eli_entitylistitemid = ? " +
                        "AND emlia_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitymultiplelistitemattributes " +
                        "WHERE emlia_ena_entityattributeid = ? AND emlia_eni_entityinstanceid = ? AND emlia_eli_entitylistitemid = ? " +
                        "AND emlia_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = EntityMultipleListItemAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, entityListItem.getPrimaryKey().getEntityId());
            ps.setLong(4, Session.MAX_TIME);
            
            entityMultipleListItemAttribute = EntityMultipleListItemAttributeFactory.getInstance().getEntityFromQuery(session,
                    entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityMultipleListItemAttribute;
    }
    
    public EntityMultipleListItemAttribute getEntityMultipleListItemAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityListItem entityListItem) {
        return getEntityMultipleListItemAttribute(entityAttribute, entityInstance, entityListItem, EntityPermission.READ_ONLY);
    }
    
    public EntityMultipleListItemAttribute getEntityMultipleListItemAttributeForUpdate(EntityAttribute entityAttribute,
            EntityInstance entityInstance, EntityListItem entityListItem) {
        return getEntityMultipleListItemAttribute(entityAttribute, entityInstance, entityListItem, EntityPermission.READ_WRITE);
    }
    
    private List<EntityMultipleListItemAttribute> getEntityMultipleListItemAttributesByEntityListItem(EntityListItem entityListItem, EntityPermission entityPermission) {
        List<EntityMultipleListItemAttribute> entityMultipleListItemAttributes;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ "
                        + "FROM entitymultiplelistitemattributes, entityinstances "
                        + "WHERE emlia_eli_entitylistitemid = ? AND emlia_thrutime = ? "
                        + "AND emlia_eni_entityinstanceid = eni_entityinstanceid "
                        + "ORDER BY eni_entityuniqueid";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ "
                        + "FROM entitymultiplelistitemattributes "
                        + "WHERE emlia_eli_entitylistitemid = ? AND emlia_thrutime = ? "
                        + "FOR UPDATE";
            }

            var ps = EntityMultipleListItemAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityListItem.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityMultipleListItemAttributes = EntityMultipleListItemAttributeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityMultipleListItemAttributes;
    }
    
    public List<EntityMultipleListItemAttribute> getEntityMultipleListItemAttributesByEntityListItem(EntityListItem entityListItem) {
        return getEntityMultipleListItemAttributesByEntityListItem(entityListItem, EntityPermission.READ_ONLY);
    }
    
    public List<EntityMultipleListItemAttribute> getEntityMultipleListItemAttributesByEntityListItemForUpdate(EntityListItem entityListItem) {
        return getEntityMultipleListItemAttributesByEntityListItem(entityListItem, EntityPermission.READ_WRITE);
    }
    
    public List<EntityMultipleListItemAttribute> getEntityMultipleListItemAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        List<EntityMultipleListItemAttribute> entityMultipleListItemAttributes;
        
        try {
            var ps = EntityMultipleListItemAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitymultiplelistitemattributes " +
                    "WHERE emlia_eni_entityinstanceid = ? AND emlia_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityMultipleListItemAttributes = EntityMultipleListItemAttributeFactory.getInstance().getEntitiesFromQuery(session,
                    EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityMultipleListItemAttributes;
    }
    
    public EntityMultipleListItemAttributeTransfer getEntityMultipleListItemAttributeTransfer(UserVisit userVisit, EntityMultipleListItemAttribute entityMultipleListItemAttribute, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityMultipleListItemAttributeTransferCache().getEntityMultipleListItemAttributeTransfer(entityMultipleListItemAttribute, entityInstance);
    }
    
    public List<EntityMultipleListItemAttributeTransfer> getEntityMultipleListItemAttributeTransfers(UserVisit userVisit, Collection<EntityMultipleListItemAttribute> entityMultipleListItemAttributes, EntityInstance entityInstance) {
        List<EntityMultipleListItemAttributeTransfer> entityMultipleListItemAttributeTransfers = new ArrayList<>(entityMultipleListItemAttributes.size());
        var entityMultipleListItemAttributeTransferCache = getCoreTransferCaches(userVisit).getEntityMultipleListItemAttributeTransferCache();
        
        entityMultipleListItemAttributes.forEach((entityMultipleListItemAttribute) ->
                entityMultipleListItemAttributeTransfers.add(entityMultipleListItemAttributeTransferCache.getEntityMultipleListItemAttributeTransfer(entityMultipleListItemAttribute, entityInstance))
        );
        
        return entityMultipleListItemAttributeTransfers;
    }
    
    public List<EntityMultipleListItemAttributeTransfer> getEntityMultipleListItemAttributeTransfers(UserVisit userVisit, EntityAttribute entityAttribute,
            EntityInstance entityInstance) {
        return getEntityMultipleListItemAttributeTransfers(userVisit, getEntityMultipleListItemAttributes(entityAttribute, entityInstance), entityInstance);
    }
    
    public void deleteEntityMultipleListItemAttribute(EntityMultipleListItemAttribute entityMultipleListItemAttribute, BasePK deletedBy) {
        var entityAttribute = entityMultipleListItemAttribute.getEntityAttribute();
        var entityInstance = entityMultipleListItemAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityMultipleListItemAttribute.setThruTime(session.START_TIME_LONG);
        } else {
            entityMultipleListItemAttribute.remove();
        }
        
        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteEntityMultipleListItemAttributes(List<EntityMultipleListItemAttribute> entityMultipleListItemAttributes, BasePK deletedBy) {
        entityMultipleListItemAttributes.forEach((entityMultipleListItemAttribute) -> 
                deleteEntityMultipleListItemAttribute(entityMultipleListItemAttribute, deletedBy)
        );
    }
    
    public void deleteEntityMultipleListItemAttributesByEntityListItem(EntityListItem entityListItem, BasePK deletedBy) {
        deleteEntityMultipleListItemAttributes(getEntityMultipleListItemAttributesByEntityListItemForUpdate(entityListItem), deletedBy);
    }
    
    public void deleteEntityMultipleListItemAttributesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteEntityMultipleListItemAttributes(getEntityMultipleListItemAttributesByEntityInstanceForUpdate(entityInstance), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Name Attributes
    // --------------------------------------------------------------------------------
    
    public EntityNameAttribute createEntityNameAttribute(EntityAttribute entityAttribute, String nameAttribute,
            EntityInstance entityInstance, BasePK createdBy) {
        var entityNameAttribute = EntityNameAttributeFactory.getInstance().create(entityAttribute,
                nameAttribute, entityInstance, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return entityNameAttribute;
    }

    public long countEntityNameAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return session.queryForLong("""
                    SELECT COUNT(*)
                    FROM entitynameattributes
                    WHERE enna_ena_entityattributeid = ? AND enna_eni_entityinstanceid = ?
                    """, entityAttribute, entityInstance);
    }

    private static final Map<EntityPermission, String> getEntityNameAttributeHistoryQueries;

    static {
        var queryMap = Map.of(
                EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM entitynameattributes
                WHERE enna_ena_entityattributeid = ? AND enna_eni_entityinstanceid = ?
                ORDER BY enna_thrutime
                _LIMIT_
                """);
        getEntityNameAttributeHistoryQueries = Collections.unmodifiableMap(queryMap);
    }

    public List<EntityNameAttribute> getEntityNameAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return EntityNameAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityNameAttributeHistoryQueries,
                entityAttribute, entityInstance);
    }

    private EntityNameAttribute getEntityNameAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityPermission entityPermission) {
        EntityNameAttribute entityNameAttribute;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitynameattributes " +
                        "WHERE enna_ena_entityattributeid = ? AND enna_eni_entityinstanceid = ? AND enna_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitynameattributes " +
                        "WHERE enna_ena_entityattributeid = ? AND enna_eni_entityinstanceid = ? AND enna_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = EntityNameAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityNameAttribute = EntityNameAttributeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityNameAttribute;
    }
    
    public EntityNameAttribute getEntityNameAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityNameAttribute(entityAttribute, entityInstance, EntityPermission.READ_ONLY);
    }
    
    public EntityNameAttribute getEntityNameAttributeForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityNameAttribute(entityAttribute, entityInstance, EntityPermission.READ_WRITE);
    }
    
    public EntityNameAttributeValue getEntityNameAttributeValueForUpdate(EntityNameAttribute entityNameAttribute) {
        return entityNameAttribute == null? null: entityNameAttribute.getEntityNameAttributeValue().clone();
    }
    
    public EntityNameAttributeValue getEntityNameAttributeValueForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityNameAttributeValueForUpdate(getEntityNameAttributeForUpdate(entityAttribute, entityInstance));
    }
    
    public List<EntityNameAttribute> getEntityNameAttributesByEntityAttributeForUpdate(EntityAttribute entityAttribute) {
        List<EntityNameAttribute> entityNameAttributes;
        
        try {
            var ps = EntityNameAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitynameattributes " +
                    "WHERE enna_ena_entityattributeid = ? AND enna_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityNameAttributes = EntityNameAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityNameAttributes;
    }
    
    public List<EntityNameAttribute> getEntityNameAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        List<EntityNameAttribute> entityNameAttributes;
        
        try {
            var ps = EntityNameAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitynameattributes " +
                    "WHERE enna_eni_entityinstanceid = ? AND enna_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityNameAttributes = EntityNameAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityNameAttributes;
    }
    
    public EntityNameAttributeTransfer getEntityNameAttributeTransfer(UserVisit userVisit, EntityNameAttribute entityNameAttribute, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityNameAttributeTransferCache().getEntityNameAttributeTransfer(entityNameAttribute, entityInstance);
    }
    
    public void updateEntityNameAttributeFromValue(EntityNameAttributeValue entityNameAttributeValue, BasePK updatedBy) {
        if(entityNameAttributeValue.hasBeenModified()) {
            var entityNameAttribute = EntityNameAttributeFactory.getInstance().getEntityFromValue(session, EntityPermission.READ_WRITE, entityNameAttributeValue);
            var entityAttribute = entityNameAttribute.getEntityAttribute();
            var entityInstance = entityNameAttribute.getEntityInstance();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityNameAttribute.setThruTime(session.START_TIME_LONG);
                entityNameAttribute.store();
            } else {
                entityNameAttribute.remove();
            }
            
            EntityNameAttributeFactory.getInstance().create(entityAttribute, entityNameAttributeValue.getNameAttribute(), entityInstance, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityNameAttribute(EntityNameAttribute entityNameAttribute, BasePK deletedBy) {
        var entityAttribute = entityNameAttribute.getEntityAttribute();
        var entityInstance = entityNameAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityNameAttribute.setThruTime(session.START_TIME_LONG);
        } else {
            entityNameAttribute.remove();
        }
        
        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteEntityNameAttributes(List<EntityNameAttribute> entityNameAttributes, BasePK deletedBy) {
        entityNameAttributes.forEach((entityNameAttribute) -> 
                deleteEntityNameAttribute(entityNameAttribute, deletedBy)
        );
    }
    
    public void deleteEntityNameAttributesByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        deleteEntityNameAttributes(getEntityNameAttributesByEntityAttributeForUpdate(entityAttribute), deletedBy);
    }
    
    public void deleteEntityNameAttributesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteEntityNameAttributes(getEntityNameAttributesByEntityInstanceForUpdate(entityInstance), deletedBy);
    }
    
    public List<EntityNameAttribute> getEntityNameAttributesByName(EntityAttribute entityAttribute, String nameAttribute) {
        List<EntityNameAttribute> entityNameAttributes;
        
        try {
            var ps = EntityNameAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitynameattributes " +
                    "WHERE enna_ena_entityattributeid = ? AND enna_nameattribute = ? AND enna_thrutime = ?");
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setString(2, nameAttribute);
            ps.setLong(3, Session.MAX_TIME);
            
            entityNameAttributes = EntityNameAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityNameAttributes;
    }

    // --------------------------------------------------------------------------------
    //   Entity String Defaults
    // --------------------------------------------------------------------------------

    public EntityStringDefault createEntityStringDefault(EntityAttribute entityAttribute, Language language,
            String stringAttribute, BasePK createdBy) {
        var entityStringDefault = EntityStringDefaultFactory.getInstance().create(entityAttribute, language,
                stringAttribute, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityStringDefault.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return entityStringDefault;
    }

    public long countEntityStringDefaultHistory(EntityAttribute entityAttribute, Language language) {
        return session.queryForLong("""
                    SELECT COUNT(*)
                    FROM entitystringdefaults
                    WHERE ensdef_ena_entityattributeid = ? AND ensdef_lang_languageid = ?
                    """, entityAttribute, language);
    }

    private static final Map<EntityPermission, String> getEntityStringDefaultHistoryQueries;

    static {
        getEntityStringDefaultHistoryQueries = Map.of(
                EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM entitystringdefaults
                WHERE ensdef_ena_entityattributeid = ? AND ensdef_lang_languageid = ?
                ORDER BY ensdef_thrutime
                _LIMIT_
                """);
    }

    public List<EntityStringDefault> getEntityStringDefaultHistory(EntityAttribute entityAttribute, Language language) {
        return EntityStringDefaultFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityStringDefaultHistoryQueries,
                entityAttribute, language);
    }

    private EntityStringDefault getEntityStringDefault(EntityAttribute entityAttribute, Language language, EntityPermission entityPermission) {
        EntityStringDefault entityStringDefault;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitystringdefaults " +
                        "WHERE ensdef_ena_entityattributeid = ? AND ensdef_lang_languageid = ? AND ensdef_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitystringdefaults " +
                        "WHERE ensdef_ena_entityattributeid = ? AND ensdef_lang_languageid = ? AND ensdef_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = EntityStringDefaultFactory.getInstance().prepareStatement(query);

            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);

            entityStringDefault = EntityStringDefaultFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityStringDefault;
    }

    public EntityStringDefault getEntityStringDefault(EntityAttribute entityAttribute, Language language) {
        return getEntityStringDefault(entityAttribute, language, EntityPermission.READ_ONLY);
    }

    public EntityStringDefault getEntityStringDefaultForUpdate(EntityAttribute entityAttribute, Language language) {
        return getEntityStringDefault(entityAttribute, language, EntityPermission.READ_WRITE);
    }

    public EntityStringDefaultValue getEntityStringDefaultValueForUpdate(EntityStringDefault entityStringDefault) {
        return entityStringDefault == null? null: entityStringDefault.getEntityStringDefaultValue().clone();
    }

    public EntityStringDefaultValue getEntityStringDefaultValueForUpdate(EntityAttribute entityAttribute, Language language) {
        return getEntityStringDefaultValueForUpdate(getEntityStringDefaultForUpdate(entityAttribute, language));
    }

    public List<EntityStringDefault> getEntityStringDefaultsByEntityAttribute(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        List<EntityStringDefault> entityStringDefaults;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitystringdefaults " +
                        "JOIN languages ON ensdef_lang_languageid = lang_languageid " +
                        "WHERE ensdef_ena_entityattributeid = ? AND ensdef_thrutime = ? " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitystringdefaults " +
                        "WHERE ensdef_ena_entityattributeid = ? AND ensdef_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = EntityStringDefaultFactory.getInstance().prepareStatement(query);

            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            entityStringDefaults = EntityStringDefaultFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityStringDefaults;
    }

    public List<EntityStringDefault> getEntityStringDefaultsByEntityAttribute(EntityAttribute entityAttribute) {
        return getEntityStringDefaultsByEntityAttribute(entityAttribute, EntityPermission.READ_ONLY);
    }

    public List<EntityStringDefault> getEntityStringDefaultsByEntityAttributeForUpdate(EntityAttribute entityAttribute) {
        return getEntityStringDefaultsByEntityAttribute(entityAttribute, EntityPermission.READ_ONLY);
    }


    public EntityStringDefaultTransfer getEntityStringDefaultTransfer(UserVisit userVisit, EntityStringDefault entityStringDefault) {
        return getCoreTransferCaches(userVisit).getEntityStringDefaultTransferCache().getEntityStringDefaultTransfer(entityStringDefault);
    }

    public void updateEntityStringDefaultFromValue(EntityStringDefaultValue entityStringDefaultValue, BasePK updatedBy) {
        if(entityStringDefaultValue.hasBeenModified()) {
            var entityStringDefault = EntityStringDefaultFactory.getInstance().getEntityFromValue(session, EntityPermission.READ_WRITE, entityStringDefaultValue);
            var entityAttribute = entityStringDefault.getEntityAttribute();
            var language = entityStringDefault.getLanguage();

            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityStringDefault.setThruTime(session.START_TIME_LONG);
                entityStringDefault.store();
            } else {
                entityStringDefault.remove();
            }

            entityStringDefault = EntityStringDefaultFactory.getInstance().create(entityAttribute, language,
                    entityStringDefaultValue.getStringAttribute(), session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityStringDefault.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteEntityStringDefault(EntityStringDefault entityStringDefault, BasePK deletedBy) {
        var entityAttribute = entityStringDefault.getEntityAttribute();

        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityStringDefault.setThruTime(session.START_TIME_LONG);
        } else {
            entityStringDefault.remove();
        }

        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityStringDefault.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteEntityStringDefaults(Collection<EntityStringDefault> entityStringDefaults, BasePK deletedBy) {
        entityStringDefaults.forEach((entityStringDefault) ->
                deleteEntityStringDefault(entityStringDefault, deletedBy));
    }

    public void deleteEntityStringDefaultByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        deleteEntityStringDefaults(getEntityStringDefaultsByEntityAttributeForUpdate(entityAttribute), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Entity String Attributes
    // --------------------------------------------------------------------------------

    public EntityStringAttribute createEntityStringAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Language language, String stringAttribute, BasePK createdBy) {
        return createEntityStringAttribute(entityAttribute.getPrimaryKey(), entityInstance, language, stringAttribute,
                createdBy);
    }

    public EntityStringAttribute createEntityStringAttribute(EntityAttributePK entityAttribute, EntityInstance entityInstance,
            Language language, String stringAttribute, BasePK createdBy) {
        var entityStringAttribute = EntityStringAttributeFactory.getInstance().create(entityAttribute,
                entityInstance.getPrimaryKey(), language.getPrimaryKey(), stringAttribute, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute, EventTypes.CREATE, createdBy);

        return entityStringAttribute;
    }
    
    public long countEntityStringAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Language language) {
        return session.queryForLong("""
                    SELECT COUNT(*)
                    FROM entitystringattributes
                    WHERE ensa_ena_entityattributeid = ? AND ensa_eni_entityinstanceid = ? AND ensa_lang_languageid = ?
                    """, entityAttribute, entityInstance, language);
    }

    private static final Map<EntityPermission, String> getEntityStringAttributeHistoryQueries;

    static {
        getEntityStringAttributeHistoryQueries = Map.of(
                EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM entitystringattributes
                WHERE ensa_ena_entityattributeid = ? AND ensa_eni_entityinstanceid = ? AND ensa_lang_languageid = ?
                ORDER BY ensa_thrutime
                _LIMIT_
                """);
    }

    public List<EntityStringAttribute> getEntityStringAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Language language) {
        return EntityStringAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityStringAttributeHistoryQueries,
                entityAttribute, entityInstance, language);
    }

    private EntityStringAttribute getEntityStringAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Language language, EntityPermission entityPermission) {
        EntityStringAttribute entityStringAttribute;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitystringattributes " +
                        "WHERE ensa_ena_entityattributeid = ? AND ensa_eni_entityinstanceid = ? AND ensa_lang_languageid = ? AND ensa_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitystringattributes " +
                        "WHERE ensa_ena_entityattributeid = ? AND ensa_eni_entityinstanceid = ? AND ensa_lang_languageid = ? AND ensa_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = EntityStringAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, language.getPrimaryKey().getEntityId());
            ps.setLong(4, Session.MAX_TIME);
            
            entityStringAttribute = EntityStringAttributeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityStringAttribute;
    }
    
    public EntityStringAttribute getEntityStringAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance, Language language) {
        return getEntityStringAttribute(entityAttribute, entityInstance, language, EntityPermission.READ_ONLY);
    }
    
    public EntityStringAttribute getEntityStringAttributeForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance, Language language) {
        return getEntityStringAttribute(entityAttribute, entityInstance, language, EntityPermission.READ_WRITE);
    }
    
    public EntityStringAttribute getBestEntityStringAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance, Language language) {
        var entityStringAttribute = getEntityStringAttribute(entityAttribute, entityInstance, language);
        
        if(entityStringAttribute == null && !language.getIsDefault()) {
            entityStringAttribute = getEntityStringAttribute(entityAttribute, entityInstance, getPartyControl().getDefaultLanguage());
        }
        
        return entityStringAttribute;
    }
    
    public EntityStringAttributeValue getEntityStringAttributeValueForUpdate(EntityStringAttribute entityStringAttribute) {
        return entityStringAttribute == null? null: entityStringAttribute.getEntityStringAttributeValue().clone();
    }
    
    public EntityStringAttributeValue getEntityStringAttributeValueForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance, Language language) {
        return getEntityStringAttributeValueForUpdate(getEntityStringAttributeForUpdate(entityAttribute, entityInstance, language));
    }
    
    public List<EntityStringAttribute> getEntityStringAttributesByEntityAttributeForUpdate(EntityAttribute entityAttribute) {
        List<EntityStringAttribute> entityStringAttributes;
        
        try {
            var ps = EntityStringAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitystringattributes " +
                    "WHERE ensa_ena_entityattributeid = ? AND ensa_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityStringAttributes = EntityStringAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityStringAttributes;
    }
    
    public List<EntityStringAttribute> getEntityStringAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        List<EntityStringAttribute> entityStringAttributes;
        
        try {
            var ps = EntityStringAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitystringattributes " +
                    "WHERE ensa_eni_entityinstanceid = ? AND ensa_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityStringAttributes = EntityStringAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityStringAttributes;
    }
    
    public EntityStringAttributeTransfer getEntityStringAttributeTransfer(UserVisit userVisit, EntityStringAttribute entityStringAttribute, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityStringAttributeTransferCache().getEntityStringAttributeTransfer(entityStringAttribute, entityInstance);
    }
    
    public void updateEntityStringAttributeFromValue(EntityStringAttributeValue entityStringAttributeValue, BasePK updatedBy) {
        if(entityStringAttributeValue.hasBeenModified()) {
            var entityStringAttribute = EntityStringAttributeFactory.getInstance().getEntityFromValue(session, EntityPermission.READ_WRITE, entityStringAttributeValue);
            var entityAttribute = entityStringAttribute.getEntityAttribute();
            var entityInstance = entityStringAttribute.getEntityInstance();
            var language = entityStringAttribute.getLanguage();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityStringAttribute.setThruTime(session.START_TIME_LONG);
                entityStringAttribute.store();
            } else {
                entityStringAttribute.remove();
            }
            
            EntityStringAttributeFactory.getInstance().create(entityAttribute, entityInstance, language, entityStringAttributeValue.getStringAttribute(), session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityStringAttribute(EntityStringAttribute entityStringAttribute, BasePK deletedBy) {
        var entityAttribute = entityStringAttribute.getEntityAttribute();
        var entityInstance = entityStringAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityStringAttribute.setThruTime(session.START_TIME_LONG);
        } else {
            entityStringAttribute.remove();
        }
        
        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteEntityStringAttributes(List<EntityStringAttribute> entityStringAttributes, BasePK deletedBy) {
        entityStringAttributes.forEach((entityStringAttribute) -> 
                deleteEntityStringAttribute(entityStringAttribute, deletedBy)
        );
    }
    
    public void deleteEntityStringAttributesByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        deleteEntityStringAttributes(getEntityStringAttributesByEntityAttributeForUpdate(entityAttribute), deletedBy);
    }
    
    public void deleteEntityStringAttributesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteEntityStringAttributes(getEntityStringAttributesByEntityInstanceForUpdate(entityInstance), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Entity Geo Point Defaults
    // --------------------------------------------------------------------------------

    public EntityGeoPointDefault createEntityGeoPointDefault(EntityAttribute entityAttribute, Integer latitude,
            Integer longitude, Long elevation, Long altitude, BasePK createdBy) {
        var entityGeoPointDefault = EntityGeoPointDefaultFactory.getInstance().create(entityAttribute,
                latitude, longitude, elevation, altitude, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityGeoPointDefault.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return entityGeoPointDefault;
    }

    public long countEntityGeoPointDefaultHistory(EntityAttribute entityAttribute) {
        return session.queryForLong("""
                    SELECT COUNT(*)
                    FROM entitygeopointdefaults
                    WHERE engeopntdef_thrutime = ?
                    """, entityAttribute);
    }

    private static final Map<EntityPermission, String> getEntityGeoPointDefaultHistoryQueries;

    static {
        getEntityGeoPointDefaultHistoryQueries = Map.of(
                EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM entitygeopointdefaults
                WHERE engeopntdef_ena_entityattributeid = ?
                ORDER BY engeopntdef_thrutime
                _LIMIT_
                """);
    }

    public List<EntityGeoPointDefault> getEntityGeoPointDefaultHistory(EntityAttribute entityAttribute) {
        return EntityGeoPointDefaultFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityGeoPointDefaultHistoryQueries,
                entityAttribute);
    }

    private EntityGeoPointDefault getEntityGeoPointDefault(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        EntityGeoPointDefault entityGeoPointDefault;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitygeopointdefaults " +
                        "WHERE engeopntdef_ena_entityattributeid = ? AND engeopntdef_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitygeopointdefaults " +
                        "WHERE engeopntdef_ena_entityattributeid = ? AND engeopntdef_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = EntityGeoPointDefaultFactory.getInstance().prepareStatement(query);

            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            entityGeoPointDefault = EntityGeoPointDefaultFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityGeoPointDefault;
    }

    public EntityGeoPointDefault getEntityGeoPointDefault(EntityAttribute entityAttribute) {
        return getEntityGeoPointDefault(entityAttribute, EntityPermission.READ_ONLY);
    }

    public EntityGeoPointDefault getEntityGeoPointDefaultForUpdate(EntityAttribute entityAttribute) {
        return getEntityGeoPointDefault(entityAttribute, EntityPermission.READ_WRITE);
    }

    public EntityGeoPointDefaultValue getEntityGeoPointDefaultValueForUpdate(EntityGeoPointDefault entityGeoPointDefault) {
        return entityGeoPointDefault == null? null: entityGeoPointDefault.getEntityGeoPointDefaultValue().clone();
    }

    public EntityGeoPointDefaultValue getEntityGeoPointDefaultValueForUpdate(EntityAttribute entityAttribute) {
        return getEntityGeoPointDefaultValueForUpdate(getEntityGeoPointDefaultForUpdate(entityAttribute));
    }

    public EntityGeoPointDefaultTransfer getEntityGeoPointDefaultTransfer(UserVisit userVisit, EntityGeoPointDefault entityGeoPointDefault) {
        return getCoreTransferCaches(userVisit).getEntityGeoPointDefaultTransferCache().getEntityGeoPointDefaultTransfer(entityGeoPointDefault);
    }

    public void updateEntityGeoPointDefaultFromValue(EntityGeoPointDefaultValue entityGeoPointDefaultValue, BasePK updatedBy) {
        if(entityGeoPointDefaultValue.hasBeenModified()) {
            var entityGeoPointDefault = EntityGeoPointDefaultFactory.getInstance().getEntityFromValue(session, EntityPermission.READ_WRITE, entityGeoPointDefaultValue);
            var entityAttribute = entityGeoPointDefault.getEntityAttribute();

            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityGeoPointDefault.setThruTime(session.START_TIME_LONG);
                entityGeoPointDefault.store();
            } else {
                entityGeoPointDefault.remove();
            }

            entityGeoPointDefault = EntityGeoPointDefaultFactory.getInstance().create(entityAttribute,
                    entityGeoPointDefaultValue.getLatitude(), entityGeoPointDefaultValue.getLongitude(),
                    entityGeoPointDefaultValue.getElevation(), entityGeoPointDefaultValue.getAltitude(),
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityGeoPointDefault.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteEntityGeoPointDefault(EntityGeoPointDefault entityGeoPointDefault, BasePK deletedBy) {
        var entityAttribute = entityGeoPointDefault.getEntityAttribute();

        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityGeoPointDefault.setThruTime(session.START_TIME_LONG);
        } else {
            entityGeoPointDefault.remove();
        }

        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityGeoPointDefault.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteEntityGeoPointDefaultByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        var entityGeoPointDefault = getEntityGeoPointDefaultForUpdate(entityAttribute);

        if(entityGeoPointDefault != null) {
            deleteEntityGeoPointDefault(entityGeoPointDefault, deletedBy);
        }
    }

    // --------------------------------------------------------------------------------
    //   Entity Geo Point Attributes
    // --------------------------------------------------------------------------------

    public EntityGeoPointAttribute createEntityGeoPointAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Integer latitude, Integer longitude, Long elevation, Long altitude, BasePK createdBy) {
        return createEntityGeoPointAttribute(entityAttribute.getPrimaryKey(), entityInstance, latitude, longitude,
                elevation, altitude, createdBy);
    }

    public EntityGeoPointAttribute createEntityGeoPointAttribute(EntityAttributePK entityAttribute, EntityInstance entityInstance,
            Integer latitude, Integer longitude, Long elevation, Long altitude, BasePK createdBy) {
        var entityGeoPointAttribute = EntityGeoPointAttributeFactory.getInstance().create(entityAttribute,
                entityInstance.getPrimaryKey(), latitude, longitude, elevation, altitude, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute, EventTypes.CREATE, createdBy);

        return entityGeoPointAttribute;
    }

    public long countEntityGeoPointAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return session.queryForLong("""
                    SELECT COUNT(*)
                    FROM entitygeopointattributes
                    WHERE engeopnta_ena_entityattributeid = ? AND engeopnta_eni_entityinstanceid = ?
                    """, entityAttribute, entityInstance);
    }

    private static final Map<EntityPermission, String> getEntityGeoPointAttributeHistoryQueries;

    static {
        var queryMap = Map.of(
                EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM entitygeopointattributes
                WHERE engeopnta_ena_entityattributeid = ? AND engeopnta_eni_entityinstanceid = ?
                ORDER BY engeopnta_thrutime
                _LIMIT_
                """);
        getEntityGeoPointAttributeHistoryQueries = Collections.unmodifiableMap(queryMap);
    }

    public List<EntityGeoPointAttribute> getEntityGeoPointAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return EntityGeoPointAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityGeoPointAttributeHistoryQueries,
                entityAttribute, entityInstance);
    }

    private EntityGeoPointAttribute getEntityGeoPointAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityPermission entityPermission) {
        EntityGeoPointAttribute entityGeoPointAttribute;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitygeopointattributes " +
                        "WHERE engeopnta_ena_entityattributeid = ? AND engeopnta_eni_entityinstanceid = ? AND engeopnta_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitygeopointattributes " +
                        "WHERE engeopnta_ena_entityattributeid = ? AND engeopnta_eni_entityinstanceid = ? AND engeopnta_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = EntityGeoPointAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityGeoPointAttribute = EntityGeoPointAttributeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityGeoPointAttribute;
    }
    
    public EntityGeoPointAttribute getEntityGeoPointAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityGeoPointAttribute(entityAttribute, entityInstance, EntityPermission.READ_ONLY);
    }
    
    public EntityGeoPointAttribute getEntityGeoPointAttributeForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityGeoPointAttribute(entityAttribute, entityInstance, EntityPermission.READ_WRITE);
    }
    
    public EntityGeoPointAttributeValue getEntityGeoPointAttributeValueForUpdate(EntityGeoPointAttribute entityGeoPointAttribute) {
        return entityGeoPointAttribute == null? null: entityGeoPointAttribute.getEntityGeoPointAttributeValue().clone();
    }
    
    public EntityGeoPointAttributeValue getEntityGeoPointAttributeValueForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityGeoPointAttributeValueForUpdate(getEntityGeoPointAttributeForUpdate(entityAttribute, entityInstance));
    }
    
    public List<EntityGeoPointAttribute> getEntityGeoPointAttributesByEntityAttributeForUpdate(EntityAttribute entityAttribute) {
        List<EntityGeoPointAttribute> entityGeoPointAttributes;
        
        try {
            var ps = EntityGeoPointAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitygeopointattributes " +
                    "WHERE engeopnta_ena_entityattributeid = ? AND engeopnta_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityGeoPointAttributes = EntityGeoPointAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityGeoPointAttributes;
    }
    
    public List<EntityGeoPointAttribute> getEntityGeoPointAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        List<EntityGeoPointAttribute> entityGeoPointAttributes;
        
        try {
            var ps = EntityGeoPointAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitygeopointattributes " +
                    "WHERE engeopnta_eni_entityinstanceid = ? AND engeopnta_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityGeoPointAttributes = EntityGeoPointAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityGeoPointAttributes;
    }
    
    public EntityGeoPointAttributeTransfer getEntityGeoPointAttributeTransfer(UserVisit userVisit, EntityGeoPointAttribute entityGeoPointAttribute, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityGeoPointAttributeTransferCache().getEntityGeoPointAttributeTransfer(entityGeoPointAttribute, entityInstance);
    }
    
    public void updateEntityGeoPointAttributeFromValue(EntityGeoPointAttributeValue entityGeoPointAttributeValue, BasePK updatedBy) {
        if(entityGeoPointAttributeValue.hasBeenModified()) {
            var entityGeoPointAttribute = EntityGeoPointAttributeFactory.getInstance().getEntityFromValue(session, EntityPermission.READ_WRITE, entityGeoPointAttributeValue);
            var entityAttribute = entityGeoPointAttribute.getEntityAttribute();
            var entityInstance = entityGeoPointAttribute.getEntityInstance();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityGeoPointAttribute.setThruTime(session.START_TIME_LONG);
                entityGeoPointAttribute.store();
            } else {
                entityGeoPointAttribute.remove();
            }
            
            EntityGeoPointAttributeFactory.getInstance().create(entityAttribute, entityInstance, entityGeoPointAttributeValue.getLatitude(),
                    entityGeoPointAttributeValue.getLongitude(), entityGeoPointAttributeValue.getElevation(), entityGeoPointAttributeValue.getAltitude(),
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityGeoPointAttribute(EntityGeoPointAttribute entityGeoPointAttribute, BasePK deletedBy) {
        var entityAttribute = entityGeoPointAttribute.getEntityAttribute();
        var entityInstance = entityGeoPointAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityGeoPointAttribute.setThruTime(session.START_TIME_LONG);
        } else {
            entityGeoPointAttribute.remove();
        }
        
        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteEntityGeoPointAttributes(List<EntityGeoPointAttribute> entityGeoPointAttributes, BasePK deletedBy) {
        entityGeoPointAttributes.forEach((entityGeoPointAttribute) -> 
                deleteEntityGeoPointAttribute(entityGeoPointAttribute, deletedBy)
        );
    }
    
    public void deleteEntityGeoPointAttributesByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        deleteEntityGeoPointAttributes(getEntityGeoPointAttributesByEntityAttributeForUpdate(entityAttribute), deletedBy);
    }
    
    public void deleteEntityGeoPointAttributesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteEntityGeoPointAttributes(getEntityGeoPointAttributesByEntityInstanceForUpdate(entityInstance), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Entity Time Defaults
    // --------------------------------------------------------------------------------

    public EntityTimeDefault createEntityTimeDefault(EntityAttribute entityAttribute, Long timeAttribute,
            BasePK createdBy) {
        var entityTimeDefault = EntityTimeDefaultFactory.getInstance().create(entityAttribute,
                timeAttribute, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityTimeDefault.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return entityTimeDefault;
    }

    public long countEntityTimeDefaultHistory(EntityAttribute entityAttribute) {
        return session.queryForLong("""
                    SELECT COUNT(*)
                    FROM entitytimedefaults
                    WHERE entdef_thrutime = ?
                    """, entityAttribute);
    }

    private static final Map<EntityPermission, String> getEntityTimeDefaultHistoryQueries;

    static {
        getEntityTimeDefaultHistoryQueries = Map.of(
                EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM entitytimedefaults
                WHERE entdef_ena_entityattributeid = ?
                ORDER BY entdef_thrutime
                _LIMIT_
                """);
    }

    public List<EntityTimeDefault> getEntityTimeDefaultHistory(EntityAttribute entityAttribute) {
        return EntityTimeDefaultFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityTimeDefaultHistoryQueries,
                entityAttribute);
    }

    private EntityTimeDefault getEntityTimeDefault(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        EntityTimeDefault entityTimeDefault;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitytimedefaults " +
                        "WHERE entdef_ena_entityattributeid = ? AND entdef_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitytimedefaults " +
                        "WHERE entdef_ena_entityattributeid = ? AND entdef_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = EntityTimeDefaultFactory.getInstance().prepareStatement(query);

            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            entityTimeDefault = EntityTimeDefaultFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityTimeDefault;
    }

    public EntityTimeDefault getEntityTimeDefault(EntityAttribute entityAttribute) {
        return getEntityTimeDefault(entityAttribute, EntityPermission.READ_ONLY);
    }

    public EntityTimeDefault getEntityTimeDefaultForUpdate(EntityAttribute entityAttribute) {
        return getEntityTimeDefault(entityAttribute, EntityPermission.READ_WRITE);
    }

    public EntityTimeDefaultValue getEntityTimeDefaultValueForUpdate(EntityTimeDefault entityTimeDefault) {
        return entityTimeDefault == null? null: entityTimeDefault.getEntityTimeDefaultValue().clone();
    }

    public EntityTimeDefaultValue getEntityTimeDefaultValueForUpdate(EntityAttribute entityAttribute) {
        return getEntityTimeDefaultValueForUpdate(getEntityTimeDefaultForUpdate(entityAttribute));
    }

    public EntityTimeDefaultTransfer getEntityTimeDefaultTransfer(UserVisit userVisit, EntityTimeDefault entityTimeDefault) {
        return getCoreTransferCaches(userVisit).getEntityTimeDefaultTransferCache().getEntityTimeDefaultTransfer(entityTimeDefault);
    }

    public void updateEntityTimeDefaultFromValue(EntityTimeDefaultValue entityTimeDefaultValue, BasePK updatedBy) {
        if(entityTimeDefaultValue.hasBeenModified()) {
            var entityTimeDefault = EntityTimeDefaultFactory.getInstance().getEntityFromValue(session, EntityPermission.READ_WRITE, entityTimeDefaultValue);
            var entityAttribute = entityTimeDefault.getEntityAttribute();

            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityTimeDefault.setThruTime(session.START_TIME_LONG);
                entityTimeDefault.store();
            } else {
                entityTimeDefault.remove();
            }

            entityTimeDefault = EntityTimeDefaultFactory.getInstance().create(entityAttribute,
                    entityTimeDefaultValue.getTimeAttribute(), session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityTimeDefault.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteEntityTimeDefault(EntityTimeDefault entityTimeDefault, BasePK deletedBy) {
        var entityAttribute = entityTimeDefault.getEntityAttribute();

        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityTimeDefault.setThruTime(session.START_TIME_LONG);
        } else {
            entityTimeDefault.remove();
        }

        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityTimeDefault.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteEntityTimeDefaultByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        var entityTimeDefault = getEntityTimeDefaultForUpdate(entityAttribute);

        if(entityTimeDefault != null) {
            deleteEntityTimeDefault(entityTimeDefault, deletedBy);
        }
    }

    // --------------------------------------------------------------------------------
    //   Entity Time Attributes
    // --------------------------------------------------------------------------------

    public EntityTimeAttribute createEntityTimeAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Long timeAttribute, BasePK createdBy) {
        return createEntityTimeAttribute(entityAttribute.getPrimaryKey(), entityInstance, timeAttribute,
                createdBy);
    }

    public EntityTimeAttribute createEntityTimeAttribute(EntityAttributePK entityAttribute, EntityInstance entityInstance,
            Long timeAttribute, BasePK createdBy) {
        var entityTimeAttribute = EntityTimeAttributeFactory.getInstance().create(entityAttribute,
                entityInstance.getPrimaryKey(), timeAttribute, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute, EventTypes.CREATE, createdBy);

        return entityTimeAttribute;
    }

    public long countEntityTimeAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return session.queryForLong("""
                    SELECT COUNT(*)
                    FROM entitytimeattributes
                    WHERE enta_ena_entityattributeid = ? AND enta_eni_entityinstanceid = ?
                    """, entityAttribute, entityInstance);
    }

    private static final Map<EntityPermission, String> getEntityTimeAttributeHistoryQueries;

    static {
        var queryMap = Map.of(
                EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM entitytimeattributes
                WHERE enta_ena_entityattributeid = ? AND enta_eni_entityinstanceid = ?
                ORDER BY enta_thrutime
                _LIMIT_
                """);
        getEntityTimeAttributeHistoryQueries = Collections.unmodifiableMap(queryMap);
    }

    public List<EntityTimeAttribute> getEntityTimeAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return EntityTimeAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityTimeAttributeHistoryQueries,
                entityAttribute, entityInstance);
    }

    private EntityTimeAttribute getEntityTimeAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance, EntityPermission entityPermission) {
        EntityTimeAttribute entityTimeAttribute;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitytimeattributes " +
                        "WHERE enta_ena_entityattributeid = ? AND enta_eni_entityinstanceid = ? AND enta_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitytimeattributes " +
                        "WHERE enta_ena_entityattributeid = ? AND enta_eni_entityinstanceid = ? AND enta_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = EntityTimeAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityTimeAttribute = EntityTimeAttributeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityTimeAttribute;
    }
    
    public EntityTimeAttribute getEntityTimeAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityTimeAttribute(entityAttribute, entityInstance, EntityPermission.READ_ONLY);
    }
    
    public EntityTimeAttribute getEntityTimeAttributeForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityTimeAttribute(entityAttribute, entityInstance, EntityPermission.READ_WRITE);
    }
    
    public EntityTimeAttributeValue getEntityTimeAttributeValueForUpdate(EntityTimeAttribute entityTimeAttribute) {
        return entityTimeAttribute == null? null: entityTimeAttribute.getEntityTimeAttributeValue().clone();
    }
    
    public EntityTimeAttributeValue getEntityTimeAttributeValueForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityTimeAttributeValueForUpdate(getEntityTimeAttributeForUpdate(entityAttribute, entityInstance));
    }
    
    public List<EntityTimeAttribute> getEntityTimeAttributesByEntityAttributeForUpdate(EntityAttribute entityAttribute) {
        List<EntityTimeAttribute> entityTimeAttributes;
        
        try {
            var ps = EntityTimeAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitytimeattributes " +
                    "WHERE enta_ena_entityattributeid = ? AND enta_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityTimeAttributes = EntityTimeAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityTimeAttributes;
    }
    
    public List<EntityTimeAttribute> getEntityTimeAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        List<EntityTimeAttribute> entityTimeAttributes;
        
        try {
            var ps = EntityTimeAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitytimeattributes " +
                    "WHERE enta_eni_entityinstanceid = ? AND enta_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityTimeAttributes = EntityTimeAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityTimeAttributes;
    }
    
    public EntityTimeAttributeTransfer getEntityTimeAttributeTransfer(UserVisit userVisit, EntityTimeAttribute entityTimeAttribute, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityTimeAttributeTransferCache().getEntityTimeAttributeTransfer(entityTimeAttribute, entityInstance);
    }
    
    public void updateEntityTimeAttributeFromValue(EntityTimeAttributeValue entityTimeAttributeValue, BasePK updatedBy) {
        if(entityTimeAttributeValue.hasBeenModified()) {
            var entityTimeAttribute = EntityTimeAttributeFactory.getInstance().getEntityFromValue(session, EntityPermission.READ_WRITE, entityTimeAttributeValue);
            var entityAttribute = entityTimeAttribute.getEntityAttribute();
            var entityInstance = entityTimeAttribute.getEntityInstance();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityTimeAttribute.setThruTime(session.START_TIME_LONG);
                entityTimeAttribute.store();
            } else {
                entityTimeAttribute.remove();
            }
            
            EntityTimeAttributeFactory.getInstance().create(entityAttribute, entityInstance, entityTimeAttributeValue.getTimeAttribute(), session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityTimeAttribute(EntityTimeAttribute entityTimeAttribute, BasePK deletedBy) {
        var entityAttribute = entityTimeAttribute.getEntityAttribute();
        var entityInstance = entityTimeAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityTimeAttribute.setThruTime(session.START_TIME_LONG);
        } else {
            entityTimeAttribute.remove();
        }
        
        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteEntityTimeAttributes(List<EntityTimeAttribute> entityTimeAttributes, BasePK deletedBy) {
        entityTimeAttributes.forEach((entityTimeAttribute) -> 
                deleteEntityTimeAttribute(entityTimeAttribute, deletedBy)
        );
    }
    
    public void deleteEntityTimeAttributesByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        deleteEntityTimeAttributes(getEntityTimeAttributesByEntityAttributeForUpdate(entityAttribute), deletedBy);
    }
    
    public void deleteEntityTimeAttributesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteEntityTimeAttributes(getEntityTimeAttributesByEntityInstanceForUpdate(entityInstance), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Blob Attributes
    // --------------------------------------------------------------------------------
    
    public EntityBlobAttribute createEntityBlobAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Language language, ByteArray blobAttribute, MimeType mimeType, BasePK createdBy) {
        var entityBlobAttribute = EntityBlobAttributeFactory.getInstance().create(entityAttribute,
                entityInstance, language, blobAttribute, mimeType, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return entityBlobAttribute;
    }
    
    private EntityBlobAttribute getEntityBlobAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Language language, EntityPermission entityPermission) {
        EntityBlobAttribute entityBlobAttribute;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityblobattributes " +
                        "WHERE enba_ena_entityattributeid = ? AND enba_eni_entityinstanceid = ? AND enba_lang_languageid = ? " +
                        "AND enba_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityblobattributes " +
                        "WHERE enba_ena_entityattributeid = ? AND enba_eni_entityinstanceid = ? AND enba_lang_languageid = ? " +
                        "AND enba_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = EntityBlobAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, language.getPrimaryKey().getEntityId());
            ps.setLong(4, Session.MAX_TIME);
            
            entityBlobAttribute = EntityBlobAttributeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityBlobAttribute;
    }
    
    public EntityBlobAttribute getEntityBlobAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance, Language language) {
        return getEntityBlobAttribute(entityAttribute, entityInstance, language, EntityPermission.READ_ONLY);
    }
    
    public EntityBlobAttribute getEntityBlobAttributeForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance, Language language) {
        return getEntityBlobAttribute(entityAttribute, entityInstance, language, EntityPermission.READ_WRITE);
    }
    
    public EntityBlobAttribute getBestEntityBlobAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance, Language language) {
        var entityBlobAttribute = getEntityBlobAttribute(entityAttribute, entityInstance, language);
        
        if(entityBlobAttribute == null && !language.getIsDefault()) {
            entityBlobAttribute = getEntityBlobAttribute(entityAttribute, entityInstance, getPartyControl().getDefaultLanguage());
        }
        
        return entityBlobAttribute;
    }
    
    public EntityBlobAttributeValue getEntityBlobAttributeValueForUpdate(EntityBlobAttribute entityBlobAttribute) {
        return entityBlobAttribute == null? null: entityBlobAttribute.getEntityBlobAttributeValue().clone();
    }
    
    public EntityBlobAttributeValue getEntityBlobAttributeValueForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance, Language language) {
        return getEntityBlobAttributeValueForUpdate(getEntityBlobAttributeForUpdate(entityAttribute, entityInstance, language));
    }
    
    public List<EntityBlobAttribute> getEntityBlobAttributesByEntityAttributeForUpdate(EntityAttribute entityAttribute) {
        List<EntityBlobAttribute> entityBlobAttributes;
        
        try {
            var ps = EntityBlobAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entityblobattributes " +
                    "WHERE enba_ena_entityattributeid = ? AND enba_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityBlobAttributes = EntityBlobAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityBlobAttributes;
    }
    
    public List<EntityBlobAttribute> getEntityBlobAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        List<EntityBlobAttribute> entityBlobAttributes;
        
        try {
            var ps = EntityBlobAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entityblobattributes " +
                    "WHERE enba_eni_entityinstanceid = ? AND enba_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityBlobAttributes = EntityBlobAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityBlobAttributes;
    }
    
    public EntityBlobAttributeTransfer getEntityBlobAttributeTransfer(UserVisit userVisit, EntityBlobAttribute entityBlobAttribute, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityBlobAttributeTransferCache().getEntityBlobAttributeTransfer(entityBlobAttribute, entityInstance);
    }
    
    public void updateEntityBlobAttributeFromValue(EntityBlobAttributeValue entityBlobAttributeValue, BasePK updatedBy) {
        if(entityBlobAttributeValue.hasBeenModified()) {
            var entityBlobAttribute = EntityBlobAttributeFactory.getInstance().getEntityFromValue(session, EntityPermission.READ_WRITE, entityBlobAttributeValue);
            var entityAttribute = entityBlobAttribute.getEntityAttribute();
            var entityInstance = entityBlobAttribute.getEntityInstance();
            var language = entityBlobAttribute.getLanguage();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityBlobAttribute.setThruTime(session.START_TIME_LONG);
                entityBlobAttribute.store();
            } else {
                entityBlobAttribute.remove();
            }
            
            EntityBlobAttributeFactory.getInstance().create(entityAttribute.getPrimaryKey(), entityInstance.getPrimaryKey(), language.getPrimaryKey(),
                    entityBlobAttributeValue.getBlobAttribute(), entityBlobAttributeValue.getMimeTypePK(), session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityBlobAttribute(EntityBlobAttribute entityBlobAttribute, BasePK deletedBy) {
        var entityAttribute = entityBlobAttribute.getEntityAttribute();
        var entityInstance = entityBlobAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityBlobAttribute.setThruTime(session.START_TIME_LONG);
        } else {
            entityBlobAttribute.remove();
        }
        
        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteEntityBlobAttributes(List<EntityBlobAttribute> entityBlobAttributes, BasePK deletedBy) {
        entityBlobAttributes.forEach((entityBlobAttribute) -> 
                deleteEntityBlobAttribute(entityBlobAttribute, deletedBy)
        );
    }
    
    public void deleteEntityBlobAttributesByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        deleteEntityBlobAttributes(getEntityBlobAttributesByEntityAttributeForUpdate(entityAttribute), deletedBy);
    }
    
    public void deleteEntityBlobAttributesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteEntityBlobAttributes(getEntityBlobAttributesByEntityInstanceForUpdate(entityInstance), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Clob Attributes
    // --------------------------------------------------------------------------------
    
    public EntityClobAttribute createEntityClobAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Language language, String clobAttribute, MimeType mimeType, BasePK createdBy) {
        var entityClobAttribute = EntityClobAttributeFactory.getInstance().create(entityAttribute,
                entityInstance, language, clobAttribute, mimeType, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return entityClobAttribute;
    }

    public long countEntityClobAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return session.queryForLong("""
                    SELECT COUNT(*)
                    FROM entityclobattributes
                    WHERE enca_ena_entityattributeid = ? AND enca_eni_entityinstanceid = ?
                    """, entityAttribute, entityInstance);
    }

    private static final Map<EntityPermission, String> getEntityClobAttributeHistoryQueries;

    static {
        var queryMap = Map.of(
                EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM entityclobattributes
                WHERE enca_ena_entityattributeid = ? AND enca_eni_entityinstanceid = ?
                ORDER BY enca_thrutime
                _LIMIT_
                """);
        getEntityClobAttributeHistoryQueries = Collections.unmodifiableMap(queryMap);
    }

    public List<EntityClobAttribute> getEntityClobAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return EntityClobAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityClobAttributeHistoryQueries,
                entityAttribute, entityInstance);
    }

    private EntityClobAttribute getEntityClobAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Language language, EntityPermission entityPermission) {
        EntityClobAttribute entityClobAttribute;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityclobattributes " +
                        "WHERE enca_ena_entityattributeid = ? AND enca_eni_entityinstanceid = ? AND enca_lang_languageid = ? " +
                        "AND enca_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityclobattributes " +
                        "WHERE enca_ena_entityattributeid = ? AND enca_eni_entityinstanceid = ? AND enca_lang_languageid = ? " +
                        "AND enca_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = EntityClobAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, language.getPrimaryKey().getEntityId());
            ps.setLong(4, Session.MAX_TIME);
            
            entityClobAttribute = EntityClobAttributeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityClobAttribute;
    }
    
    public EntityClobAttribute getEntityClobAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance, Language language) {
        return getEntityClobAttribute(entityAttribute, entityInstance, language, EntityPermission.READ_ONLY);
    }
    
    public EntityClobAttribute getEntityClobAttributeForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance, Language language) {
        return getEntityClobAttribute(entityAttribute, entityInstance, language, EntityPermission.READ_WRITE);
    }
    
    public EntityClobAttribute getBestEntityClobAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance, Language language) {
        var entityClobAttribute = getEntityClobAttribute(entityAttribute, entityInstance, language);
        
        if(entityClobAttribute == null && !language.getIsDefault()) {
            entityClobAttribute = getEntityClobAttribute(entityAttribute, entityInstance, getPartyControl().getDefaultLanguage());
        }
        
        return entityClobAttribute;
    }
    
    public EntityClobAttributeValue getEntityClobAttributeValueForUpdate(EntityClobAttribute entityClobAttribute) {
        return entityClobAttribute == null? null: entityClobAttribute.getEntityClobAttributeValue().clone();
    }
    
    public EntityClobAttributeValue getEntityClobAttributeValueForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance, Language language) {
        return getEntityClobAttributeValueForUpdate(getEntityClobAttributeForUpdate(entityAttribute, entityInstance, language));
    }
    
    public List<EntityClobAttribute> getEntityClobAttributesByEntityAttributeForUpdate(EntityAttribute entityAttribute) {
        List<EntityClobAttribute> entityClobAttributes;
        
        try {
            var ps = EntityClobAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entityclobattributes " +
                    "WHERE enca_ena_entityattributeid = ? AND enca_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityClobAttributes = EntityClobAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityClobAttributes;
    }
    
    public List<EntityClobAttribute> getEntityClobAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        List<EntityClobAttribute> entityClobAttributes;
        
        try {
            var ps = EntityClobAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entityclobattributes " +
                    "WHERE enca_eni_entityinstanceid = ? AND enca_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityClobAttributes = EntityClobAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityClobAttributes;
    }
    
    public EntityClobAttributeTransfer getEntityClobAttributeTransfer(UserVisit userVisit, EntityClobAttribute entityClobAttribute, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityClobAttributeTransferCache().getEntityClobAttributeTransfer(entityClobAttribute, entityInstance);
    }
    
    public void updateEntityClobAttributeFromValue(EntityClobAttributeValue entityClobAttributeValue, BasePK updatedBy) {
        if(entityClobAttributeValue.hasBeenModified()) {
            var entityClobAttribute = EntityClobAttributeFactory.getInstance().getEntityFromValue(session, EntityPermission.READ_WRITE, entityClobAttributeValue);
            var entityAttribute = entityClobAttribute.getEntityAttribute();
            var entityInstance = entityClobAttribute.getEntityInstance();
            var language = entityClobAttribute.getLanguage();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityClobAttribute.setThruTime(session.START_TIME_LONG);
                entityClobAttribute.store();
            } else {
                entityClobAttribute.remove();
            }
            
            EntityClobAttributeFactory.getInstance().create(entityAttribute.getPrimaryKey(), entityInstance.getPrimaryKey(), language.getPrimaryKey(),
                    entityClobAttributeValue.getClobAttribute(), entityClobAttributeValue.getMimeTypePK(), session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityClobAttribute(EntityClobAttribute entityClobAttribute, BasePK deletedBy) {
        var entityAttribute = entityClobAttribute.getEntityAttribute();
        var entityInstance = entityClobAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityClobAttribute.setThruTime(session.START_TIME_LONG);
        } else {
            entityClobAttribute.remove();
        }
        
        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteEntityClobAttributes(List<EntityClobAttribute> entityClobAttributes, BasePK deletedBy) {
        entityClobAttributes.forEach((entityClobAttribute) -> 
                deleteEntityClobAttribute(entityClobAttribute, deletedBy)
        );
    }
    
    public void deleteEntityClobAttributesByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        deleteEntityClobAttributes(getEntityClobAttributesByEntityAttributeForUpdate(entityAttribute), deletedBy);
    }
    
    public void deleteEntityClobAttributesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteEntityClobAttributes(getEntityClobAttributesByEntityInstanceForUpdate(entityInstance), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Attribute Entity Types
    // --------------------------------------------------------------------------------
    
    public EntityAttributeEntityType createEntityAttributeEntityType(EntityAttribute entityAttribute, EntityType allowedEntityType, BasePK createdBy) {
        var entityAttributeEntityType = EntityAttributeEntityTypeFactory.getInstance().create(entityAttribute, allowedEntityType,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeEntityType.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return entityAttributeEntityType;
    }
    
    public long countEntityAttributeEntityTypesByEntityAttribute(EntityAttribute entityAttribute) {
        return session.queryForLong(
                "SELECT COUNT(*) "
                + "FROM entityattributeentitytypes "
                + "WHERE enaent_ena_entityattributeid = ? AND enaent_thrutime = ?",
                entityAttribute, Session.MAX_TIME);
    }

    public long countEntityAttributeEntityTypesByAllowedEntityType(EntityType allowedEntityType) {
        return session.queryForLong(
                "SELECT COUNT(*) "
                + "FROM entityattributeentitytypes "
                + "WHERE enaent_allowedentitytypeid = ? AND enaent_thrutime = ?",
                allowedEntityType, Session.MAX_TIME);
    }

    public boolean entityAttributeEntityTypeExists(EntityAttribute entityAttribute, EntityType allowedEntityType) {
        return 1 == session.queryForLong(
                "SELECT COUNT(*) "
                + "FROM entityattributeentitytypes "
                + "WHERE enaent_ena_entityattributeid = ? AND enaent_allowedentitytypeid = ? AND enaent_thrutime = ?",
                entityAttribute, allowedEntityType, Session.MAX_TIME);
    }

    private static final Map<EntityPermission, String> getEntityAttributeEntityTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM entityattributeentitytypes "
                + "WHERE enaent_ena_entityattributeid = ? AND enaent_allowedentitytypeid = ? AND enaent_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM entityattributeentitytypes "
                + "WHERE enaent_ena_entityattributeid = ? AND enaent_allowedentitytypeid = ? AND enaent_thrutime = ? "
                + "FOR UPDATE");
        getEntityAttributeEntityTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private EntityAttributeEntityType getEntityAttributeEntityType(EntityAttribute entityAttribute, EntityType allowedEntityType, EntityPermission entityPermission) {
        return EntityAttributeEntityTypeFactory.getInstance().getEntityFromQuery(entityPermission, getEntityAttributeEntityTypeQueries,
                entityAttribute, allowedEntityType, Session.MAX_TIME_LONG);
    }

    public EntityAttributeEntityType getEntityAttributeEntityType(EntityAttribute entityAttribute, EntityType allowedEntityType) {
        return getEntityAttributeEntityType(entityAttribute, allowedEntityType, EntityPermission.READ_ONLY);
    }

    public EntityAttributeEntityType getEntityAttributeEntityTypeForUpdate(EntityAttribute entityAttribute, EntityType allowedEntityType) {
        return getEntityAttributeEntityType(entityAttribute, allowedEntityType, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getEntityAttributeEntityTypesByEntityAttributeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM entityattributeentitytypes, entitytypes, entitytypedetails, componentvendors, componentvendordetails "
                + "WHERE enaent_ena_entityattributeid = ? AND enaent_thrutime = ? "
                + "AND enaent_allowedentitytypeid = ent_entitytypeid AND ent_lastdetailid = entdt_entitytypedetailid "
                + "AND entdt_cvnd_componentvendorid = cvnd_componentvendorid AND cvnd_lastdetailid = cvndd_componentvendordetailid "
                + "ORDER BY entdt_sortorder, entdt_entitytypename, cvndd_componentvendorname "
                + "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM entityattributeentitytypes "
                + "WHERE enaent_ena_entityattributeid = ? AND enaent_thrutime = ? "
                + "FOR UPDATE");
        getEntityAttributeEntityTypesByEntityAttributeQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<EntityAttributeEntityType> getEntityAttributeEntityTypesByEntityAttribute(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        return EntityAttributeEntityTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getEntityAttributeEntityTypesByEntityAttributeQueries,
                entityAttribute, Session.MAX_TIME_LONG);
    }
    
    public List<EntityAttributeEntityType> getEntityAttributeEntityTypesByEntityAttribute(EntityAttribute entityAttribute) {
        return getEntityAttributeEntityTypesByEntityAttribute(entityAttribute, EntityPermission.READ_ONLY);
    }
    
    public List<EntityAttributeEntityType> getEntityAttributeEntityTypesByEntityAttributeForUpdate(EntityAttribute entityAttribute) {
        return getEntityAttributeEntityTypesByEntityAttribute(entityAttribute, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getEntityAttributeEntityTypesByAllowedEntityTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM entityattributeentitytypes, entityattributes, entityattributedetails "
                + "WHERE enaent_allowedentitytypeid = ? AND enaent_thrutime = ? "
                + "AND enaent_ena_entityattributeid = ena_entityattributeid AND ena_lastdetailid = enadt_entityAttributedetailid "
                + "ORDER BY enadt_sortorder, enadt_entityAttributename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM entityattributeentitytypes "
                + "WHERE enaent_ena_entityattributeid = ? AND enaent_thrutime = ? "
                + "FOR UPDATE");
        getEntityAttributeEntityTypesByAllowedEntityTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<EntityAttributeEntityType> getEntityAttributeEntityTypesByAllowedEntityType(EntityType allowedEntityType, EntityPermission entityPermission) {
        return EntityAttributeEntityTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getEntityAttributeEntityTypesByAllowedEntityTypeQueries,
                allowedEntityType, Session.MAX_TIME_LONG);
    }

    public List<EntityAttributeEntityType> getEntityAttributeEntityTypesByAllowedEntityType(EntityType allowedEntityType) {
        return getEntityAttributeEntityTypesByAllowedEntityType(allowedEntityType, EntityPermission.READ_ONLY);
    }

    public List<EntityAttributeEntityType> getEntityAttributeEntityTypesByAllowedEntityTypeForUpdate(EntityType allowedEntityType) {
        return getEntityAttributeEntityTypesByAllowedEntityType(allowedEntityType, EntityPermission.READ_WRITE);
    }

    public EntityAttributeEntityTypeTransfer getEntityAttributeEntityTypeTransfer(UserVisit userVisit, EntityAttributeEntityType entityAttributeEntityType, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityAttributeEntityTypeTransferCache().getEntityAttributeEntityTypeTransfer(entityAttributeEntityType, entityInstance);
    }
    
    public List<EntityAttributeEntityTypeTransfer> getEntityAttributeEntityTypeTransfers(UserVisit userVisit, Collection<EntityAttributeEntityType> entityAttributeEntityTypes, EntityInstance entityInstance) {
        List<EntityAttributeEntityTypeTransfer> entityAttributeEntityTypeTransfers = new ArrayList<>(entityAttributeEntityTypes.size());
        var entityAttributeEntityTypeTransferCache = getCoreTransferCaches(userVisit).getEntityAttributeEntityTypeTransferCache();

        entityAttributeEntityTypes.forEach((entityAttributeEntityType) ->
                entityAttributeEntityTypeTransfers.add(entityAttributeEntityTypeTransferCache.getEntityAttributeEntityTypeTransfer(entityAttributeEntityType, entityInstance))
        );

        return entityAttributeEntityTypeTransfers;
    }

    public List<EntityAttributeEntityTypeTransfer> getEntityAttributeEntityTypeTransfersByEntityAttribute(UserVisit userVisit, EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityAttributeEntityTypeTransfers(userVisit, getEntityAttributeEntityTypesByEntityAttribute(entityAttribute), entityInstance);
    }

    public List<EntityAttributeEntityTypeTransfer> getEntityAttributeEntityTypeTransfersByAllowedEntityType(UserVisit userVisit, EntityType allowedEntityType, EntityInstance entityInstance) {
        return getEntityAttributeEntityTypeTransfers(userVisit, getEntityAttributeEntityTypesByAllowedEntityType(allowedEntityType), entityInstance);
    }

    public void deleteEntityAttributeEntityType(EntityAttributeEntityType entityAttributeEntityType, BasePK deletedBy) {
        entityAttributeEntityType.setThruTime(session.START_TIME_LONG);
        
        sendEvent(entityAttributeEntityType.getEntityAttributePK(), EventTypes.MODIFY, entityAttributeEntityType.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteEntityAttributeEntityTypes(List<EntityAttributeEntityType> entityAttributeEntityTypes, BasePK deletedBy) {
        entityAttributeEntityTypes.forEach((entityAttributeEntityType) -> 
                deleteEntityAttributeEntityType(entityAttributeEntityType, deletedBy)
        );
    }
    
    public void deleteEntityAttributeEntityTypesByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        deleteEntityAttributeEntityTypes(getEntityAttributeEntityTypesByEntityAttributeForUpdate(entityAttribute),  deletedBy);
    }

    public void deleteEntityAttributeEntityTypesByAllowedEntityType(EntityType allowedEntityType, BasePK deletedBy) {
        deleteEntityAttributeEntityTypes(getEntityAttributeEntityTypesByAllowedEntityTypeForUpdate(allowedEntityType),  deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Entity Entity Attributes
    // --------------------------------------------------------------------------------
    
    public EntityEntityAttribute createEntityEntityAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityInstance entityInstanceAttribute, BasePK createdBy) {
        var entityEntityAttribute = EntityEntityAttributeFactory.getInstance().create(entityAttribute, entityInstance,
                entityInstanceAttribute, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return entityEntityAttribute;
    }

    public long countEntityEntityAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return session.queryForLong("""
                    SELECT COUNT(*)
                    FROM entityentityattributes
                    WHERE eea_ena_entityattributeid = ? AND eea_eni_entityinstanceid = ?
                    """, entityAttribute, entityInstance);
    }

    private static final Map<EntityPermission, String> getEntityEntityAttributeHistoryQueries;

    static {
        var queryMap = Map.of(
                EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM entityentityattributes
                WHERE eea_ena_entityattributeid = ? AND eea_eni_entityinstanceid = ?
                ORDER BY eea_thrutime
                _LIMIT_
                """);
        getEntityEntityAttributeHistoryQueries = Collections.unmodifiableMap(queryMap);
    }

    public List<EntityEntityAttribute> getEntityEntityAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return EntityEntityAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getEntityEntityAttributeHistoryQueries,
                entityAttribute, entityInstance);
    }

    private EntityEntityAttribute getEntityEntityAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance, EntityPermission entityPermission) {
        EntityEntityAttribute entityEntityAttribute;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityentityattributes " +
                        "WHERE eea_ena_entityattributeid = ? AND eea_eni_entityinstanceid = ? " +
                        "AND eea_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityentityattributes " +
                        "WHERE eea_ena_entityattributeid = ? AND eea_eni_entityinstanceid = ? " +
                        "AND eea_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = EntityEntityAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityEntityAttribute = EntityEntityAttributeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityEntityAttribute;
    }
    
    public EntityEntityAttribute getEntityEntityAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityEntityAttribute(entityAttribute, entityInstance, EntityPermission.READ_ONLY);
    }
    
    public EntityEntityAttribute getEntityEntityAttributeForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityEntityAttribute(entityAttribute, entityInstance, EntityPermission.READ_WRITE);
    }
    
    public EntityEntityAttributeValue getEntityEntityAttributeValueForUpdate(EntityEntityAttribute entityEntityAttribute) {
        return entityEntityAttribute == null? null: entityEntityAttribute.getEntityEntityAttributeValue().clone();
    }
    
    public EntityEntityAttributeValue getEntityEntityAttributeValueForUpdate(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityEntityAttributeValueForUpdate(getEntityEntityAttributeForUpdate(entityAttribute, entityInstance));
    }
    
    public List<EntityEntityAttribute> getEntityEntityAttributesByEntityAttributeForUpdate(EntityAttribute entityAttribute) {
        List<EntityEntityAttribute> entityEntityAttributes;
        
        try {
            var ps = EntityEntityAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entityentityattributes " +
                    "WHERE eea_eni_entityinstanceid = ? AND eea_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityEntityAttributes = EntityEntityAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityEntityAttributes;
    }
    
    public List<EntityEntityAttribute> getEntityEntityAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        List<EntityEntityAttribute> entityEntityAttributes;
        
        try {
            var ps = EntityEntityAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entityentityattributes " +
                    "WHERE eea_eni_entityinstanceid = ? AND eea_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityEntityAttributes = EntityEntityAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityEntityAttributes;
    }
    
    public List<EntityEntityAttribute> getEntityEntityAttributesByEntityInstanceAttributeForUpdate(EntityInstance entityInstanceAttribute) {
        List<EntityEntityAttribute> entityEntityAttributes;
        
        try {
            var ps = EntityEntityAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entityentityattributes " +
                    "WHERE eea_entityinstanceattributeid = ? AND eea_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstanceAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityEntityAttributes = EntityEntityAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityEntityAttributes;
    }
    
    public EntityEntityAttributeTransfer getEntityEntityAttributeTransfer(UserVisit userVisit, EntityEntityAttribute entityEntityAttribute, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityEntityAttributeTransferCache().getEntityEntityAttributeTransfer(entityEntityAttribute, entityInstance);
    }
    
    public void updateEntityEntityAttributeFromValue(EntityEntityAttributeValue entityEntityAttributeValue, BasePK updatedBy) {
        if(entityEntityAttributeValue.hasBeenModified()) {
            var entityEntityAttribute = EntityEntityAttributeFactory.getInstance().getEntityFromValue(session, EntityPermission.READ_WRITE, entityEntityAttributeValue);
            var entityAttribute = entityEntityAttribute.getEntityAttribute();
            var entityInstance = entityEntityAttribute.getEntityInstance();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityEntityAttribute.setThruTime(session.START_TIME_LONG);
                entityEntityAttribute.store();
            } else {
                entityEntityAttribute.remove();
            }
            
            EntityEntityAttributeFactory.getInstance().create(entityAttribute.getPrimaryKey(), entityInstance.getPrimaryKey(),
                    entityEntityAttributeValue.getEntityInstanceAttributePK(), session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityEntityAttribute(EntityEntityAttribute entityEntityAttribute, BasePK deletedBy) {
        var entityAttribute = entityEntityAttribute.getEntityAttribute();
        var entityInstance = entityEntityAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityEntityAttribute.setThruTime(session.START_TIME_LONG);
        } else {
            entityEntityAttribute.remove();
        }
        
        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteEntityEntityAttributes(List<EntityEntityAttribute> entityEntityAttributes, BasePK deletedBy) {
        entityEntityAttributes.forEach((entityEntityAttribute) -> 
                deleteEntityEntityAttribute(entityEntityAttribute, deletedBy)
        );
    }
    
    public void deleteEntityEntityAttributesByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        deleteEntityEntityAttributes(getEntityEntityAttributesByEntityAttributeForUpdate(entityAttribute), deletedBy);
    }
    
    public void deleteEntityEntityAttributesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteEntityEntityAttributes(getEntityEntityAttributesByEntityInstanceForUpdate(entityInstance), deletedBy);
        deleteEntityEntityAttributes(getEntityEntityAttributesByEntityInstanceAttributeForUpdate(entityInstance), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Collection Attributes
    // --------------------------------------------------------------------------------
    
    public EntityCollectionAttribute createEntityCollectionAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityInstance entityInstanceAttribute, BasePK createdBy) {
        var entityCollectionAttribute = EntityCollectionAttributeFactory.getInstance().create(entityAttribute, entityInstance,
                entityInstanceAttribute, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return entityCollectionAttribute;
    }
    
    public List<EntityCollectionAttribute> getEntityCollectionAttributes(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        List<EntityCollectionAttribute> entityCollectionAttributes;
        
        try {
            var ps = EntityCollectionAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ "
                    + "FROM entitycollectionattributes, entityinstances, entitytypes, entitytypedetails, componentvendors, componentvendordetails "
                    + "WHERE eca_ena_entityattributeid = ? AND eca_eni_entityinstanceid = ? AND eca_thrutime = ? "
                    + "AND eca_entityinstanceattributeid = eni_entityinstanceid "
                    + "AND eni_ent_entitytypeid = ent_entitytypeid AND ent_lastdetailid = entdt_entitytypedetailid "
                    + "AND entdt_cvnd_componentvendorid = cvnd_componentvendorid AND cvnd_lastdetailid = cvndd_componentvendordetailid "
                    + "ORDER BY cvndd_componentvendorname, entdt_sortorder, entdt_entitytypename, eni_entityuniqueid");

            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityCollectionAttributes = EntityCollectionAttributeFactory.getInstance().getEntitiesFromQuery(session,
                    EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityCollectionAttributes;
    }
    
    private EntityCollectionAttribute getEntityCollectionAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityInstance entityInstanceAttribute, EntityPermission entityPermission) {
        EntityCollectionAttribute entityCollectionAttribute;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitycollectionattributes " +
                        "WHERE eca_ena_entityattributeid = ? AND eca_eni_entityinstanceid = ? AND eca_entityinstanceattributeid = ? " +
                        "AND eca_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitycollectionattributes " +
                        "WHERE eca_ena_entityattributeid = ? AND eca_eni_entityinstanceid = ? AND eca_entityinstanceattributeid = ? " +
                        "AND eca_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = EntityCollectionAttributeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, entityInstanceAttribute.getPrimaryKey().getEntityId());
            ps.setLong(4, Session.MAX_TIME);
            
            entityCollectionAttribute = EntityCollectionAttributeFactory.getInstance().getEntityFromQuery(session,
                    entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityCollectionAttribute;
    }
    
    public EntityCollectionAttribute getEntityCollectionAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityInstance entityInstanceAttribute) {
        return getEntityCollectionAttribute(entityAttribute, entityInstance, entityInstanceAttribute, EntityPermission.READ_ONLY);
    }
    
    public EntityCollectionAttribute getEntityCollectionAttributeForUpdate(EntityAttribute entityAttribute,
            EntityInstance entityInstance, EntityInstance entityInstanceAttribute) {
        return getEntityCollectionAttribute(entityAttribute, entityInstance, entityInstanceAttribute, EntityPermission.READ_WRITE);
    }
    
    public List<EntityCollectionAttribute> getEntityCollectionAttributesByEntityAttributeForUpdate(EntityAttribute entityAttribute) {
        List<EntityCollectionAttribute> entityCollectionAttributes;
        
        try {
            var ps = EntityCollectionAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitycollectionattributes " +
                    "WHERE eca_eni_entityinstanceid = ? AND eca_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityCollectionAttributes = EntityCollectionAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityCollectionAttributes;
    }
    
    public List<EntityCollectionAttribute> getEntityCollectionAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        List<EntityCollectionAttribute> entityCollectionAttributes;
        
        try {
            var ps = EntityCollectionAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitycollectionattributes " +
                    "WHERE eca_eni_entityinstanceid = ? AND eca_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityCollectionAttributes = EntityCollectionAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityCollectionAttributes;
    }
    
    public List<EntityCollectionAttribute> getEntityCollectionAttributesByEntityInstanceAttributeForUpdate(EntityInstance entityInstanceAttribute) {
        List<EntityCollectionAttribute> entityCollectionAttributes;
        
        try {
            var ps = EntityCollectionAttributeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM entitycollectionattributes " +
                    "WHERE eca_entityinstanceattributeid = ? AND eca_thrutime = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, entityInstanceAttribute.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityCollectionAttributes = EntityCollectionAttributeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityCollectionAttributes;
    }
    
    public EntityCollectionAttributeTransfer getEntityCollectionAttributeTransfer(UserVisit userVisit, EntityCollectionAttribute entityCollectionAttribute, EntityInstance entityInstance) {
        return getCoreTransferCaches(userVisit).getEntityCollectionAttributeTransferCache().getEntityCollectionAttributeTransfer(entityCollectionAttribute, entityInstance);
    }
    
    public List<EntityCollectionAttributeTransfer> getEntityCollectionAttributeTransfers(UserVisit userVisit, Collection<EntityCollectionAttribute> entityCollectionAttributes, EntityInstance entityInstance) {
        List<EntityCollectionAttributeTransfer> entityCollectionAttributeTransfers = new ArrayList<>(entityCollectionAttributes.size());
        var entityCollectionAttributeTransferCache = getCoreTransferCaches(userVisit).getEntityCollectionAttributeTransferCache();
        
        entityCollectionAttributes.forEach((entityCollectionAttribute) ->
                entityCollectionAttributeTransfers.add(entityCollectionAttributeTransferCache.getEntityCollectionAttributeTransfer(entityCollectionAttribute, entityInstance))
        );
        
        return entityCollectionAttributeTransfers;
    }
    
    public List<EntityCollectionAttributeTransfer> getEntityCollectionAttributeTransfers(UserVisit userVisit, EntityAttribute entityAttribute,
            EntityInstance entityInstance) {
        return getEntityCollectionAttributeTransfers(userVisit, getEntityCollectionAttributes(entityAttribute, entityInstance), entityInstance);
    }
    
    public void deleteEntityCollectionAttribute(EntityCollectionAttribute entityCollectionAttribute, BasePK deletedBy) {
        var entityAttribute = entityCollectionAttribute.getEntityAttribute();
        var entityInstance = entityCollectionAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityCollectionAttribute.setThruTime(session.START_TIME_LONG);
        } else {
            entityCollectionAttribute.remove();
        }
        
        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteEntityCollectionAttributes(List<EntityCollectionAttribute> entityCollectionAttributes, BasePK deletedBy) {
        entityCollectionAttributes.forEach((entityCollectionAttribute) -> 
                deleteEntityCollectionAttribute(entityCollectionAttribute, deletedBy)
        );
    }
    
    public void deleteEntityCollectionAttributesByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        deleteEntityCollectionAttributes(getEntityCollectionAttributesByEntityAttributeForUpdate(entityAttribute), deletedBy);
    }
    
    public void deleteEntityCollectionAttributesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteEntityCollectionAttributes(getEntityCollectionAttributesByEntityInstanceForUpdate(entityInstance), deletedBy);
        deleteEntityCollectionAttributes(getEntityCollectionAttributesByEntityInstanceAttributeForUpdate(entityInstance), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Base Encryption Keys
    // --------------------------------------------------------------------------------
    
    public BaseEncryptionKey createBaseEncryptionKey(ExecutionErrorAccumulator eea, BaseKey baseKey1, BaseKey baseKey2, PartyPK createdBy) {
        var activeBaseEncryptionKey = getActiveBaseEncryptionKey();
        BaseEncryptionKey baseEncryptionKey = null;
        
        if(activeBaseEncryptionKey != null) {
            setBaseEncryptionKeyStatus(eea, activeBaseEncryptionKey, WorkflowDestination_BASE_ENCRYPTION_KEY_STATUS_ACTIVE_TO_INACTIVE, createdBy);
        }
        
        if(!eea.hasExecutionErrors()) {
            var sequenceControl = Session.getModelController(SequenceControl.class);
            var sequence = sequenceControl.getDefaultSequenceUsingNames(SequenceTypes.BASE_ENCRYPTION_KEY.name());
            var baseEncryptionKeyName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(sequence);
            var sha1Hash = Sha1Utils.getInstance().encode(baseKey1, baseKey2);
            baseEncryptionKey = createBaseEncryptionKey(baseEncryptionKeyName, sha1Hash, createdBy);

            var entityInstance = getEntityInstanceByBaseEntity(baseEncryptionKey);
            getWorkflowControl().addEntityToWorkflowUsingNames(null, Workflow_BASE_ENCRYPTION_KEY_STATUS, entityInstance, null, null,createdBy);
        }
        
        return baseEncryptionKey;
    }
    
    public BaseEncryptionKey createBaseEncryptionKey(String baseEncryptionKeyName, String sha1Hash, BasePK createdBy) {
        var baseEncryptionKey = BaseEncryptionKeyFactory.getInstance().create(baseEncryptionKeyName,
                sha1Hash);
        
        sendEvent(baseEncryptionKey.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return baseEncryptionKey;
    }
    
    private List<BaseEncryptionKey> getBaseEncryptionKeys(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM baseencryptionkeys " +
                    "ORDER BY bek_baseencryptionkeyname";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM baseencryptionkeys " +
                    "FOR UPDATE";
        }

        var ps = BaseEncryptionKeyFactory.getInstance().prepareStatement(query);
        
        return BaseEncryptionKeyFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<BaseEncryptionKey> getBaseEncryptionKeys() {
        return getBaseEncryptionKeys(EntityPermission.READ_ONLY);
    }
    
    public List<BaseEncryptionKey> getBaseEncryptionKeysForUpdate() {
        return getBaseEncryptionKeys(EntityPermission.READ_WRITE);
    }
    
    private BaseEncryptionKey getActiveBaseEncryptionKey(EntityPermission entityPermission) {
        var workflowStep = getWorkflowControl().getWorkflowStepUsingNames(Workflow_BASE_ENCRYPTION_KEY_STATUS,
                WorkflowStep_BASE_ENCRYPTION_KEY_STATUS_ACTIVE);
        BaseEncryptionKey baseEncryptionKey = null;
        
        if(workflowStep != null) {
            List<BaseEncryptionKey> baseEncryptionKeys;
            
            try {
                var query = new StringBuilder("SELECT _ALL_ " +
                        "FROM componentvendors, componentvendordetails, entitytypes, entitytypedetails, entityinstances, " +
                        "baseencryptionkeys, workflowentitystatuses, entitytimes " +
                        "WHERE cvnd_activedetailid = cvndd_componentvendordetailid AND cvndd_componentvendorname = ? " +
                        "AND ent_activedetailid = entdt_entitytypedetailid " +
                        "AND cvnd_componentvendorid = entdt_cvnd_componentvendorid " +
                        "AND entdt_entitytypename = ? " +
                        "AND ent_entitytypeid = eni_ent_entitytypeid AND bek_baseencryptionkeyid = eni_entityuniqueid " +
                        "AND eni_entityinstanceid = wkfles_eni_entityinstanceid AND wkfles_wkfls_workflowstepid = ? AND wkfles_thrutime = ? " +
                        "AND eni_entityinstanceid = etim_eni_entityinstanceid ");
                
                if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                    query.append("ORDER BY etim_createdtime DESC");
                } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                    query.append("FOR UPDATE");
                }

                var ps = BaseEncryptionKeyFactory.getInstance().prepareStatement(query.toString());
                
                ps.setString(1, ComponentVendors.ECHO_THREE.name());
                ps.setString(2, EntityTypes.BaseEncryptionKey.name());
                ps.setLong(3, workflowStep.getPrimaryKey().getEntityId());
                ps.setLong(4, Session.MAX_TIME);
                
                baseEncryptionKeys = BaseEncryptionKeyFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
            } catch (SQLException se) {
                throw new PersistenceDatabaseException(se);
            }
            
            if(!baseEncryptionKeys.isEmpty()) {
                baseEncryptionKey = baseEncryptionKeys.iterator().next();
            }
        }
        
        return baseEncryptionKey;
    }
    
    public BaseEncryptionKey getActiveBaseEncryptionKey() {
        return getActiveBaseEncryptionKey(EntityPermission.READ_ONLY);
    }
    
    public BaseEncryptionKey getActiveBaseEncryptionKeyForUpdate() {
        return getActiveBaseEncryptionKey(EntityPermission.READ_WRITE);
    }
    
    private BaseEncryptionKey getBaseEncryptionKeyByName(String baseEncryptionKeyName, EntityPermission entityPermission) {
        BaseEncryptionKey baseEncryptionKey;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM baseencryptionkeys " +
                        "WHERE bek_baseencryptionkeyname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM baseencryptionkeys " +
                        "WHERE bek_baseencryptionkeyname = ? " +
                        "FOR UPDATE";
            }

            var ps = BaseEncryptionKeyFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, baseEncryptionKeyName);
            
            baseEncryptionKey = BaseEncryptionKeyFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return baseEncryptionKey;
    }
    
    public BaseEncryptionKey getBaseEncryptionKeyByName(String baseEncryptionKeyName) {
        return getBaseEncryptionKeyByName(baseEncryptionKeyName, EntityPermission.READ_ONLY);
    }
    
    public BaseEncryptionKey getBaseEncryptionKeyByNameForUpdate(String baseEncryptionKeyName) {
        return getBaseEncryptionKeyByName(baseEncryptionKeyName, EntityPermission.READ_WRITE);
    }
    
    private BaseEncryptionKey getBaseEncryptionKeyBySha1Hash(String sha1Hash, EntityPermission entityPermission) {
        BaseEncryptionKey baseEncryptionKey;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM baseencryptionkeys " +
                        "WHERE bek_sha1hash = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM baseencryptionkeys " +
                        "WHERE bek_sha1hash = ? " +
                        "FOR UPDATE";
            }

            var ps = BaseEncryptionKeyFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, sha1Hash);
            
            baseEncryptionKey = BaseEncryptionKeyFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return baseEncryptionKey;
    }
    
    public BaseEncryptionKey getBaseEncryptionKeyBySha1Hash(String sha1Hash) {
        return getBaseEncryptionKeyBySha1Hash(sha1Hash, EntityPermission.READ_ONLY);
    }
    
    public BaseEncryptionKey getBaseEncryptionKeyBySha1HashForUpdate(String sha1Hash) {
        return getBaseEncryptionKeyBySha1Hash(sha1Hash, EntityPermission.READ_WRITE);
    }
    
    public BaseEncryptionKeyTransfer getBaseEncryptionKeyTransfer(UserVisit userVisit, BaseEncryptionKey baseEncryptionKey) {
        return getCoreTransferCaches(userVisit).getBaseEncryptionKeyTransferCache().getBaseEncryptionKeyTransfer(baseEncryptionKey);
    }
    
    public BaseEncryptionKeyTransfer getActiveBaseEncryptionKeyTransfer(UserVisit userVisit) {
        return getCoreTransferCaches(userVisit).getBaseEncryptionKeyTransferCache().getBaseEncryptionKeyTransfer(getActiveBaseEncryptionKey());
    }
    
    public List<BaseEncryptionKeyTransfer> getBaseEncryptionKeyTransfers(UserVisit userVisit) {
        var baseEncryptionKeys = getBaseEncryptionKeys();
        List<BaseEncryptionKeyTransfer> baseEncryptionKeyTransfers = new ArrayList<>(baseEncryptionKeys.size());
        var baseEncryptionKeyTransferCache = getCoreTransferCaches(userVisit).getBaseEncryptionKeyTransferCache();
        
        baseEncryptionKeys.forEach((baseEncryptionKey) ->
                baseEncryptionKeyTransfers.add(baseEncryptionKeyTransferCache.getBaseEncryptionKeyTransfer(baseEncryptionKey))
        );
        
        return baseEncryptionKeyTransfers;
    }
    
    public BaseEncryptionKeyStatusChoicesBean getBaseEncryptionKeyStatusChoices(String defaultBaseEncryptionKeyStatusChoice,
            Language language, boolean allowNullChoice, BaseEncryptionKey baseEncryptionKey, PartyPK partyPK) {
        var workflowControl = getWorkflowControl();
        var baseEncryptionKeyStatusChoicesBean = new BaseEncryptionKeyStatusChoicesBean();
        
        if(baseEncryptionKey == null) {
            workflowControl.getWorkflowEntranceChoices(baseEncryptionKeyStatusChoicesBean, defaultBaseEncryptionKeyStatusChoice, language, allowNullChoice,
                    workflowControl.getWorkflowByName(Workflow_BASE_ENCRYPTION_KEY_STATUS), partyPK);
        } else {
            var entityInstance = getCoreControl().getEntityInstanceByBasePK(baseEncryptionKey.getPrimaryKey());
            var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(Workflow_BASE_ENCRYPTION_KEY_STATUS,
                    entityInstance);
            
            workflowControl.getWorkflowDestinationChoices(baseEncryptionKeyStatusChoicesBean, defaultBaseEncryptionKeyStatusChoice, language, allowNullChoice,
                    workflowEntityStatus.getWorkflowStep(), partyPK);
        }
        
        return baseEncryptionKeyStatusChoicesBean;
    }
    
    public void setBaseEncryptionKeyStatus(ExecutionErrorAccumulator eea, BaseEncryptionKey baseEncryptionKey, String baseEncryptionKeyStatusChoice, PartyPK modifiedBy) {
        var workflowControl = getWorkflowControl();
        var entityInstance = getEntityInstanceByBaseEntity(baseEncryptionKey);
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(Workflow_BASE_ENCRYPTION_KEY_STATUS,
                entityInstance);
        var workflowDestination = baseEncryptionKeyStatusChoice == null? null:
            workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), baseEncryptionKeyStatusChoice);
        
        if(workflowDestination != null || baseEncryptionKeyStatusChoice == null) {
            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, null, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownBaseEncryptionKeyStatusChoice.name(), baseEncryptionKeyStatusChoice);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Encryption Keys
    // --------------------------------------------------------------------------------
    
    public EntityEncryptionKey createEntityEncryptionKey(String entityEncryptionKeyName, Boolean isExternal, String secretKey, String initializationVector) {
        return EntityEncryptionKeyFactory.getInstance().create(entityEncryptionKeyName, isExternal, secretKey, initializationVector);
    }
    
    private EntityEncryptionKey getEntityEncryptionKeyByName(String entityEncryptionKeyName, EntityPermission entityPermission) {
        EntityEncryptionKey entityEncryptionKey;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entityencryptionkeys " +
                        "WHERE eek_entityencryptionkeyname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entityencryptionkeys " +
                        "WHERE eek_entityencryptionkeyname = ? " +
                        "FOR UPDATE";
            }

            var ps = EntityEncryptionKeyFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, entityEncryptionKeyName);
            
            entityEncryptionKey = EntityEncryptionKeyFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityEncryptionKey;
    }
    
    public EntityEncryptionKey getEntityEncryptionKeyByName(String entityEncryptionKeyName) {
        return getEntityEncryptionKeyByName(entityEncryptionKeyName, EntityPermission.READ_ONLY);
    }
    
    public EntityEncryptionKey getEntityEncryptionKeyByNameForUpdate(String entityEncryptionKeyName) {
        return getEntityEncryptionKeyByName(entityEncryptionKeyName, EntityPermission.READ_WRITE);
    }
    
    public List<EntityEncryptionKey> getEntityEncryptionKeysForUpdate() {
        var ps = EntityEncryptionKeyFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM entityencryptionkeys " +
                "FOR UPDATE");
        
        return EntityEncryptionKeyFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
    }
    
    public long countEntityEncryptionKeys() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM entityencryptionkeys");
    }
    
    // --------------------------------------------------------------------------------
    //   Event Subscribers
    // --------------------------------------------------------------------------------
    
    public EventSubscriber createEventSubscriber(EntityInstance entityInstance, String description, Integer sortOrder,
            BasePK createdBy) {
        var sequenceControl = Session.getModelController(SequenceControl.class);
        var sequenceType = sequenceControl.getSequenceTypeByName(SequenceTypes.EVENT_SUBSCRIBER.name());
        var sequence = sequenceControl.getDefaultSequence(sequenceType);
        var eventSubscriberName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(sequence);
        
        return createEventSubscriber(eventSubscriberName, entityInstance, description, sortOrder, createdBy);
    }
    
    public EventSubscriber createEventSubscriber(String eventSubscriberName, EntityInstance entityInstance, String description,
            Integer sortOrder, BasePK createdBy) {
        var eventSubscriber = EventSubscriberFactory.getInstance().create();
        var eventSubscriberDetail = EventSubscriberDetailFactory.getInstance().create(session,
                eventSubscriber, eventSubscriberName, entityInstance, description, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        // Convert to R/W
        eventSubscriber = EventSubscriberFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                eventSubscriber.getPrimaryKey());
        eventSubscriber.setActiveDetail(eventSubscriberDetail);
        eventSubscriber.setLastDetail(eventSubscriberDetail);
        eventSubscriber.store();
        
        sendEvent(eventSubscriber.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return eventSubscriber;
    }
    
    private EventSubscriber getEventSubscriberByName(String eventSubscriberName, EntityPermission entityPermission) {
        EventSubscriber eventSubscriber;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM eventsubscribers, eventsubscriberdetails " +
                        "WHERE evs_activedetailid = evsdt_eventsubscriberdetailid AND evsdt_eventsubscribername = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM eventsubscribers, eventsubscriberdetails " +
                        "WHERE evs_activedetailid = evsdt_eventsubscriberdetailid AND evsdt_eventsubscribername = ? " +
                        "FOR UPDATE";
            }

            var ps = EventSubscriberFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, eventSubscriberName);
            
            eventSubscriber = EventSubscriberFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return eventSubscriber;
    }
    
    public EventSubscriber getEventSubscriberByName(String eventSubscriberName) {
        return getEventSubscriberByName(eventSubscriberName, EntityPermission.READ_ONLY);
    }
    
    public EventSubscriber getEventSubscriberByNameForUpdate(String eventSubscriberName) {
        return getEventSubscriberByName(eventSubscriberName, EntityPermission.READ_WRITE);
    }
    
    private List<EventSubscriber> getEventSubscribersByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        List<EventSubscriber> eventSubscribers;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM eventsubscribers, eventsubscriberdetails " +
                        "WHERE evs_activedetailid = evsdt_eventsubscriberdetailid AND evsdt_eni_entityinstanceid = ? " +
                        "ORDER BY evsdt_sortorder, evsdt_eventsubscribername";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM eventsubscribers, eventsubscriberdetails " +
                        "WHERE evs_activedetailid = evsdt_eventsubscriberdetailid AND evsdt_eni_entityinstanceid = ? " +
                        "FOR UPDATE";
            }

            var ps = EventSubscriberFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            
            eventSubscribers = EventSubscriberFactory.getInstance().getEntitiesFromQuery(entityPermission,
                    ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return eventSubscribers;
    }
    
    public List<EventSubscriber> getEventSubscribersByEntityInstance(EntityInstance entityInstance) {
        return getEventSubscribersByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }
    
    public List<EventSubscriber> getEventSubscribersByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getEventSubscribersByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
//    public EventSubscriberTransfer getEventSubscriberTransfer(UserVisit userVisit, EventSubscriber eventSubscriber) {
//        return getPaymentTransferCaches(userVisit).getEventSubscriberTransferCache().getEventSubscriberTransfer(eventSubscriber);
//    }
//    
//    public List<EventSubscriberTransfer> getEventSubscriberTransfersByEntityInstance(UserVisit userVisit, EntityInstance entityInstance) {
//        List<EventSubscriber> eventSubscribers = getEventSubscribersByEntityInstance(entityInstance);
//        List<EventSubscriberTransfer> eventSubscriberTransfers = new ArrayList<EventSubscriberTransfer>(eventSubscribers.size());
//        EventSubscriberTransferCache eventSubscriberTransferCache = getPaymentTransferCaches(userVisit).getEventSubscriberTransferCache();
//        
//        for(var eventSubscriber : eventSubscribers) {
//            eventSubscriberTransfers.add(eventSubscriberTransferCache.getEventSubscriberTransfer(eventSubscriber));
//        }
//        
//        return eventSubscriberTransfers;
//    }
    
    public void deleteEventSubscriber(EventSubscriber eventSubscriber, BasePK deletedBy) {
        removeQueuedSubscriberEventsByEventSubscriber(eventSubscriber);

        var eventSubscriberDetail = eventSubscriber.getLastDetailForUpdate();
        eventSubscriberDetail.setThruTime(session.START_TIME_LONG);
        eventSubscriber.setActiveDetail(null);
        eventSubscriber.store();
        
        sendEvent(eventSubscriber.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteEventSubscribers(List<EventSubscriber> eventSubscribers, BasePK deletedBy) {
        eventSubscribers.forEach((eventSubscriber) -> 
                deleteEventSubscriber(eventSubscriber, deletedBy)
        );
    }
    
    public void deleteEventSubscribersByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteEventSubscribers(getEventSubscribersByEntityInstanceForUpdate(entityInstance), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Queued Events
    // --------------------------------------------------------------------------------
    
    public QueuedSubscriberEvent createQueuedSubscriberEvent(EventSubscriber eventSubscriber, Event event) {
        return QueuedSubscriberEventFactory.getInstance().create(eventSubscriber, event);
    }
    
    private List<QueuedSubscriberEvent> getQueuedSubscriberEventsByEventSubscriber(EventSubscriber eventSubscriber,
            EntityPermission entityPermission) {
        List<QueuedSubscriberEvent> queuedSubscriberEvents;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM queuedsubscriberevents, events " +
                        "WHERE qsev_evs_eventsubscriberid = ? AND qsev_ev_eventid = ev_eventid " +
                        "ORDER BY ev_eventtime";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM queuedsubscriberevents " +
                        "WHERE qsev_evs_eventsubscriberid = ? " +
                        "FOR UPDATE";
            }

            var ps = QueuedSubscriberEventFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, eventSubscriber.getPrimaryKey().getEntityId());
            
            queuedSubscriberEvents = QueuedSubscriberEventFactory.getInstance().getEntitiesFromQuery(entityPermission,
                    ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return queuedSubscriberEvents;
    }
    
    public List<QueuedSubscriberEvent> getQueuedSubscriberEventsByEventSubscriber(EventSubscriber eventSubscriber) {
        return getQueuedSubscriberEventsByEventSubscriber(eventSubscriber, EntityPermission.READ_ONLY);
    }
    
    public List<QueuedSubscriberEvent> getQueuedSubscriberEventsByEventSubscriberForUpdate(EventSubscriber eventSubscriber) {
        return getQueuedSubscriberEventsByEventSubscriber(eventSubscriber, EntityPermission.READ_WRITE);
    }
    
    public void removeQueuedSubscriberEvent(QueuedSubscriberEvent queuedSubscriberEvent) {
        queuedSubscriberEvent.remove();
    }
    
    public void removeQueuedSubscriberEvents(List<QueuedSubscriberEvent> queuedSubscriberEvents) {
        queuedSubscriberEvents.forEach(this::removeQueuedSubscriberEvent);
    }
    
    public void removeQueuedSubscriberEventsByEventSubscriber(EventSubscriber eventSubscriber) {
        removeQueuedSubscriberEvents(getQueuedSubscriberEventsByEventSubscriberForUpdate(eventSubscriber));
    }
    
    // --------------------------------------------------------------------------------
    //   Event Subscriber Event Types
    // --------------------------------------------------------------------------------
    
    public EventSubscriberEventType createEventSubscriberEventType(EventSubscriber eventSubscriber, EventType eventType,
            BasePK createdBy) {
        var eventSubscriberEventType = EventSubscriberEventTypeFactory.getInstance().create(session,
                eventSubscriber, eventType, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(eventSubscriber.getPrimaryKey(), EventTypes.MODIFY, eventSubscriberEventType.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return eventSubscriberEventType;
    }
    
    private List<EventSubscriberEventType> getEventSubscriberEventTypes(EventType eventType, EntityPermission entityPermission) {
        List<EventSubscriberEventType> eventSubscriberEventTypes;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM eventsubscribereventtypes, eventsubscribers, eventsubscriberdetails " +
                        "WHERE evsevt_evty_eventtypeid = ? AND evsevt_thrutime = ? " +
                        "AND evsevt_evs_eventsubscriberid = evs_eventsubscriberid AND evs_lastdetailid = evsdt_eventsubscriberdetailid " +
                        "ORDER BY evsdt_eventsubscribername";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM eventsubscribereventtypes " +
                        "WHERE evsevt_evty_eventtypeid = ? AND evsevt_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = EventSubscriberEventTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, eventType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            eventSubscriberEventTypes = EventSubscriberEventTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return eventSubscriberEventTypes;
    }
    
    public List<EventSubscriberEventType> getEventSubscriberEventTypes(EventType eventType) {
        return getEventSubscriberEventTypes(eventType, EntityPermission.READ_ONLY);
    }
    
    public List<EventSubscriberEventType> getEventSubscriberEventTypesForUpdate(EventType eventType) {
        return getEventSubscriberEventTypes(eventType, EntityPermission.READ_WRITE);
    }
    
    // --------------------------------------------------------------------------------
    //   Event Subscriber Entity Types
    // --------------------------------------------------------------------------------
    
    public EventSubscriberEntityType createEventSubscriberEntityType(EventSubscriber eventSubscriber, EntityType entityType,
            EventType eventType, BasePK createdBy) {
        var eventSubscriberEntityType = EventSubscriberEntityTypeFactory.getInstance().create(session,
                eventSubscriber, entityType, eventType, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(eventSubscriber.getPrimaryKey(), EventTypes.MODIFY, eventSubscriberEntityType.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return eventSubscriberEntityType;
    }
    
    private List<EventSubscriberEntityType> getEventSubscriberEntityTypes(EntityType entityType, EventType eventType, 
            EntityPermission entityPermission) {
        List<EventSubscriberEntityType> eventSubscriberEntityTypes;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM eventsubscriberentitytypes, eventsubscribers, eventsubscriberdetails " +
                        "WHERE evset_ent_entitytypeid = ? AND evset_evty_eventtypeid = ? AND evset_thrutime = ? " +
                        "AND evset_evs_eventsubscriberid = evs_eventsubscriberid AND evs_lastdetailid = evsdt_eventsubscriberdetailid " +
                        "ORDER BY evsdt_eventsubscribername";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM eventsubscriberentitytypes " +
                        "WHERE evset_ent_entitytypeid = ? AND evset_evty_eventtypeid = ? AND evset_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = EventSubscriberEntityTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityType.getPrimaryKey().getEntityId());
            ps.setLong(2, eventType.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            eventSubscriberEntityTypes = EventSubscriberEntityTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return eventSubscriberEntityTypes;
    }
    
    public List<EventSubscriberEntityType> getEventSubscriberEntityTypes(EntityType entityType, EventType eventType) {
        return getEventSubscriberEntityTypes(entityType, eventType, EntityPermission.READ_ONLY);
    }
    
    public List<EventSubscriberEntityType> getEventSubscriberEntityTypesForUpdate(EntityType entityType, EventType eventType) {
        return getEventSubscriberEntityTypes(entityType, eventType, EntityPermission.READ_WRITE);
    }
    
    // --------------------------------------------------------------------------------
    //   Event Subscriber Entity Instances
    // --------------------------------------------------------------------------------
    
    public EventSubscriberEntityInstance createEventSubscriberEntityInstance(EventSubscriber eventSubscriber,
            EntityInstance entityInstance, EventType eventType, BasePK createdBy) {
        var eventSubscriberEntityInstance = EventSubscriberEntityInstanceFactory.getInstance().create(session,
                eventSubscriber, entityInstance, eventType, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(eventSubscriber.getPrimaryKey(), EventTypes.MODIFY, eventSubscriberEntityInstance.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return eventSubscriberEntityInstance;
    }
    
    private List<EventSubscriberEntityInstance> getEventSubscriberEntityInstances(EntityInstance entityInstance, 
            EventType eventType, EntityPermission entityPermission) {
        List<EventSubscriberEntityInstance> eventSubscriberEntityInstances;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM eventsubscriberentityinstances, eventsubscribers, eventsubscriberdetails " +
                        "WHERE evsei_eni_entityinstanceid = ? AND evsei_evty_eventtypeid = ? AND evsei_thrutime = ? " +
                        "AND evsei_evs_eventsubscriberid = evs_eventsubscriberid AND evs_lastdetailid = evsdt_eventsubscriberdetailid " +
                        "ORDER BY evsdt_eventsubscribername";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM eventsubscriberentityinstances " +
                        "WHERE evsei_eni_entityinstanceid = ? AND evsei_evty_eventtypeid = ? AND evsei_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = EventSubscriberEntityInstanceFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, eventType.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            eventSubscriberEntityInstances = EventSubscriberEntityInstanceFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return eventSubscriberEntityInstances;
    }
    
    public List<EventSubscriberEntityInstance> getEventSubscriberEntityInstances(EntityInstance entityInstance, EventType eventType) {
        return getEventSubscriberEntityInstances(entityInstance, eventType, EntityPermission.READ_ONLY);
    }
    
    public List<EventSubscriberEntityInstance> getEventSubscriberEntityInstancesForUpdate(EntityInstance entityInstance, EventType eventType) {
        return getEventSubscriberEntityInstances(entityInstance, eventType, EntityPermission.READ_WRITE);
    }
    
    // --------------------------------------------------------------------------------
    //   Utilities
    // --------------------------------------------------------------------------------
    
    private void queueEntityInstanceToIndexing(final EntityInstance entityInstance) {
        var indexControl = Session.getModelController(IndexControl.class);

        if(indexControl.isEntityTypeUsedByIndexTypes(entityInstance.getEntityType())) {
            try {
                QueuedEntityLogic.getInstance().createQueuedEntityUsingNames(null, QueueTypes.INDEXING.name(), entityInstance);
            } catch(UnknownQueueTypeNameException uqtne) {
                // This will be thrown early in the setup process because the QueueType has not yet been created.
                // Log as an informational message, but otherwise ignore it.
                getLog().info(uqtne.getMessage());
            }
        }
    }

    private EntityTime createOrGetEntityTime(final EntityInstance entityInstance, final Long eventTime) {
        var entityTime = getEntityTimeForUpdate(entityInstance);

        // This is why we don't call setCreatedTime(...) on CREATE events - it's already taken care of here.
        if(entityTime == null) {
            // Initially created read-only, convert to read/write. If we're in sendEvent(...),
            // we need an EntityTime. If there wasn't one previously, we do the best we can,
            // using the eventTime as its createdTime.
            createEntityTime(entityInstance, eventTime, null, null);
            entityTime = getEntityTimeForUpdate(entityInstance);
        }

        return entityTime;
    }

    private void pruneEvents(final EntityInstance entityInstance, final EventType eventType, final Integer maximumHistory) {
        final var events = getEventsByEntityInstanceAndEventTypeForUpdate(entityInstance, eventType);
        final var eventCountToRemove = events.size() - maximumHistory;

        if(eventCountToRemove > 0) {
            final var i = events.iterator();

            for(var j = 0; j < eventCountToRemove; j++) {
                removeEvent(i.next());
            }
        }
    }

    @Override
    public Event sendEvent(final EntityInstance entityInstance, final EventTypes eventTypeEnum, final EntityInstance relatedEntityInstance,
            final EventTypes relatedEventTypeEnum, final BasePK createdByBasePK) {
        final var eventType = getEventTypeByEventTypesFromCache(eventTypeEnum);
        final var relatedEventType = relatedEventTypeEnum == null ? null : getEventTypeByEventTypesFromCache(relatedEventTypeEnum);
        final var createdByEntityInstance = createdByBasePK == null ? null : getEntityInstanceByBasePK(createdByBasePK);
        Event event = null;

        if(CoreDebugFlags.LogSentEvents) {
            var entityInstanceUtils = EntityInstanceUtils.getInstance();

            getLog().info("entityInstance = " + entityInstanceUtils.getEntityRefByEntityInstance(entityInstance)
                    + ", eventType = " + eventType.getEventTypeName()
                    + ", relatedEntityInstance = " + entityInstanceUtils.getEntityRefByEntityInstance(relatedEntityInstance)
                    + ", relatedEventType = " + (relatedEventType == null ? "(null)" : relatedEventType.getEventTypeName())
                    + ", createdByEntityInstance = " + entityInstanceUtils.getEntityRefByEntityInstance(createdByEntityInstance));
        }

        final var eventTime = session.START_TIME_LONG;
        final var entityTime = createOrGetEntityTime(entityInstance, eventTime);
        var shouldClearCache = false;
        var shouldQueueEntityInstanceToIndexing = false;
        var shouldUpdateVisitedTime = false;
        var shouldQueueEventToSubscribers = false;
        var shouldCreateEntityAttributeDefaults = false;
        Integer maximumHistory = null;

        switch(eventTypeEnum) {
            case CREATE -> {
                shouldCreateEntityAttributeDefaults = true;
                shouldQueueEntityInstanceToIndexing = true;
                shouldQueueEventToSubscribers = true;
            }
            case READ -> {
                shouldUpdateVisitedTime = true;
                shouldQueueEventToSubscribers = true;
            }
            case TOUCH -> {
                entityTime.setModifiedTime(eventTime);
                shouldClearCache = true;
                shouldQueueEntityInstanceToIndexing = true;
                maximumHistory = 5;
            }
            case MODIFY -> {
                entityTime.setModifiedTime(eventTime);
                shouldClearCache = true;
                shouldQueueEntityInstanceToIndexing = true;
                shouldQueueEventToSubscribers = true;
            }
            case DELETE -> {
                deleteEntityInstanceDependencies(entityInstance, createdByBasePK);
                entityTime.setDeletedTime(eventTime);
                shouldClearCache = true;
                shouldQueueEntityInstanceToIndexing = true;
                shouldQueueEventToSubscribers = true;
            }
        }

        // Support for tracking the last time an EntityInstance (just as an Employee Party) has visited a given
        // EntityInstance. If the EntityInstance has not been modified since the last visit, the READ event caused
        // by the visit will be suppressed vs. being recorded.
        var shouldSuppressEvent = false;
        if(shouldUpdateVisitedTime && createdByEntityInstance != null) {
            final var entityVisit = getEntityVisitForUpdate(createdByEntityInstance, entityInstance);

            if(entityVisit == null) {
                createEntityVisit(createdByEntityInstance, entityInstance);
            } else {
                var modifiedTime = entityTime.getModifiedTime();

                // Prefer the real modified time, but if that's null (meaning it has never been modified),
                // fall back to the created time.
                if(modifiedTime == null) {
                    modifiedTime = entityTime.getCreatedTime();
                }

                // There is a possible override for suppressing events in here by EntityType. This was added to ensure
                // that full auditing is available for some EntityTypes such as PartyPaymentMethod.
                if(entityVisit.getVisitedTime() >= modifiedTime && !entityInstance.getEntityType().getLastDetail().getKeepAllHistory()) {
                    shouldSuppressEvent = true;
                }

                entityVisit.setVisitedTime(eventTime);
            }
        }

        if(!shouldSuppressEvent) {
            final var eventGroup = createdByBasePK == null ? null : getActiveEventGroup(createdByBasePK);

            event = createEvent(eventGroup, eventTime, entityInstance, eventType, relatedEntityInstance, relatedEventType,
                    createdByEntityInstance);

            if(shouldQueueEventToSubscribers) {
                createQueuedEvent(event);
            }
            
            if(maximumHistory != null) {
                pruneEvents(entityInstance, eventType, maximumHistory);
            }

            EventTopic.getInstance().sendEvent(event);

            SentEventEventBus.eventBus.post(new SentEvent(event));
        }

        if(shouldClearCache) {
            removeCacheEntriesByEntityInstance(entityInstance);
        }

        if(shouldQueueEntityInstanceToIndexing) {
            queueEntityInstanceToIndexing(entityInstance);
        }

        if(shouldCreateEntityAttributeDefaults) {
            createEntityAttributeDefaults(entityInstance, createdByBasePK);
        }

        return event;
    }

    @Override
    public Event sendEvent(final BasePK basePK, final EventTypes eventType, final BasePK relatedBasePK,
            final EventTypes relatedEventType, final BasePK createdByBasePK) {
        var entityInstance = getEntityInstanceByBasePK(basePK);
        Event event = null;

        if(entityInstance == null) {
            getLog().error("sendEventUsingNames: getEntityInstanceByBasePK failed on " + basePK.toString());
        } else {
            var relatedEntityInstance = relatedBasePK == null ? null : getEntityInstanceByBasePK(relatedBasePK);
            
            event = sendEvent(entityInstance, eventType, relatedEntityInstance, relatedEventType, createdByBasePK);
        }
        
        return event;
    }
    
    @Override
    public Event sendEvent(final EntityInstance entityInstance, final EventTypes eventType, final BasePK relatedBasePK,
            final EventTypes relatedEventType, final BasePK createdByBasePK) {
        var relatedEntityInstance = relatedBasePK == null ? null : getEntityInstanceByBasePK(relatedBasePK);
        
        return sendEvent(entityInstance, eventType, relatedEntityInstance, relatedEventType, createdByBasePK);
    }
    
    // --------------------------------------------------------------------------------
    //   Party Entity Types
    // --------------------------------------------------------------------------------
    
    public PartyEntityType createPartyEntityType(Party party, EntityType entityType, Boolean confirmDelete, BasePK createdBy) {
        var partyEntityType = PartyEntityTypeFactory.getInstance().create(party, entityType, confirmDelete, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(party.getPrimaryKey(), EventTypes.MODIFY, partyEntityType.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return partyEntityType;
    }

    private PartyEntityType getPartyEntityType(Party party, EntityType entityType, EntityPermission entityPermission) {
        PartyEntityType partyEntityType;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partyentitytypes " +
                        "WHERE pent_par_partyid = ? AND pent_ent_entitytypeid = ? AND pent_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partyentitytypes " +
                        "WHERE pent_par_partyid = ? AND pent_ent_entitytypeid = ? AND pent_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = PartyEntityTypeFactory.getInstance().prepareStatement(query);

            ps.setLong(1, party.getPrimaryKey().getEntityId());
            ps.setLong(2, entityType.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);

            partyEntityType = PartyEntityTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch(SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return partyEntityType;
    }

    public PartyEntityType getPartyEntityType(Party party, EntityType entityType) {
        return getPartyEntityType(party, entityType, EntityPermission.READ_ONLY);
    }

    public PartyEntityType getPartyEntityTypeForUpdate(Party party, EntityType entityType) {
        return getPartyEntityType(party, entityType, EntityPermission.READ_WRITE);
    }

    public PartyEntityTypeValue getPartyEntityTypeValue(PartyEntityType partyEntityType) {
        return partyEntityType == null ? null : partyEntityType.getPartyEntityTypeValue().clone();
    }

    public PartyEntityTypeValue getPartyEntityTypeValueForUpdate(Party party, EntityType entityType) {
        return getPartyEntityTypeValue(getPartyEntityTypeForUpdate(party, entityType));
    }

    private List<PartyEntityType> getPartyEntityTypesByParty(Party party, EntityPermission entityPermission) {
        List<PartyEntityType> partyEntityTypes;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partyentitytypes, entitytypes, entitytypedetails, componentvendors, componentvendordetails " +
                        "WHERE pent_par_partyid = ? AND pent_thrutime = ? " +
                        "AND pent_ent_entitytypeid = ent_entitytypeid AND ent_lastdetailid = entdt_entitytypedetailid " +
                        "AND entdt_cvnd_componentvendorid = cvnd_componentvendorid AND cvnd_lastdetailid = cvndd_componentvendordetailid " +
                        "ORDER BY entdt_sortorder, entdt_entitytypename, cvndd_componentvendorname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partyentitytypes " +
                        "WHERE pent_par_partyid = ? AND pent_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = PartyEntityTypeFactory.getInstance().prepareStatement(query);

            ps.setLong(1, party.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            partyEntityTypes = PartyEntityTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch(SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return partyEntityTypes;
    }

    public List<PartyEntityType> getPartyEntityTypesByParty(Party party) {
        return getPartyEntityTypesByParty(party, EntityPermission.READ_ONLY);
    }

    public List<PartyEntityType> getPartyEntityTypesByPartyForUpdate(Party party) {
        return getPartyEntityTypesByParty(party, EntityPermission.READ_WRITE);
    }

    public PartyEntityTypeTransfer getPartyEntityTypeTransfer(UserVisit userVisit, PartyEntityType partyEntityType) {
        return getCoreTransferCaches(userVisit).getPartyEntityTypeTransferCache().getPartyEntityTypeTransfer(partyEntityType);
    }

    public List<PartyEntityTypeTransfer> getPartyEntityTypeTransfersByParty(UserVisit userVisit, Party party) {
        var partyEntityTypes = getPartyEntityTypesByParty(party);
        List<PartyEntityTypeTransfer> partyEntityTypeTransfers = new ArrayList<>(partyEntityTypes.size());
        var partyEntityTypeTransferCache = getCoreTransferCaches(userVisit).getPartyEntityTypeTransferCache();

        partyEntityTypes.forEach((partyEntityType) ->
                partyEntityTypeTransfers.add(partyEntityTypeTransferCache.getPartyEntityTypeTransfer(partyEntityType))
        );

        return partyEntityTypeTransfers;
    }

    public void updatePartyEntityTypeFromValue(PartyEntityTypeValue partyEntityTypeValue, BasePK updatedBy) {
        if(partyEntityTypeValue.hasBeenModified()) {
            var partyEntityType = PartyEntityTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, partyEntityTypeValue.getPrimaryKey());

            partyEntityType.setThruTime(session.START_TIME_LONG);
            partyEntityType.store();

            var partyPK = partyEntityType.getPartyPK(); // Not updated
            var entityTypePK = partyEntityType.getEntityTypePK(); // Not updated
            var confirmDelete = partyEntityTypeValue.getConfirmDelete();

            partyEntityType = PartyEntityTypeFactory.getInstance().create(partyPK, entityTypePK, confirmDelete, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(partyPK, EventTypes.MODIFY, partyEntityType.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deletePartyEntityType(PartyEntityType partyEntityType, BasePK deletedBy) {
        partyEntityType.setThruTime(session.START_TIME_LONG);

        sendEvent(partyEntityType.getPartyPK(), EventTypes.MODIFY, partyEntityType.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deletePartyEntityTypesByParty(Party party, BasePK deletedBy) {
        getPartyEntityTypesByPartyForUpdate(party).forEach((partyEntityType) ->
                deletePartyEntityType(partyEntityType, deletedBy)
        );
    }
    
}
