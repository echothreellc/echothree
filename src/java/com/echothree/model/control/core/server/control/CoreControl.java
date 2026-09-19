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

package com.echothree.model.control.core.server.control;

import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.common.choice.EntityAttributeTypeChoicesBean;
import com.echothree.model.control.core.common.choice.EntityIntegerRangeChoicesBean;
import com.echothree.model.control.core.common.choice.EntityListItemChoicesBean;
import com.echothree.model.control.core.common.choice.EntityLongRangeChoicesBean;
import com.echothree.model.control.core.common.transfer.EntityAttributeDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.EntityAttributeEntityTypeTransfer;
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
import com.echothree.model.control.core.server.transfer.EntityAttributeDescriptionTransferCache;
import com.echothree.model.control.core.server.transfer.EntityAttributeEntityTypeTransferCache;
import com.echothree.model.control.core.server.transfer.EntityAttributeTransferCache;
import com.echothree.model.control.core.server.transfer.EntityAttributeTypeTransferCache;
import com.echothree.model.control.core.server.transfer.EntityBlobAttributeTransferCache;
import com.echothree.model.control.core.server.transfer.EntityBooleanAttributeTransferCache;
import com.echothree.model.control.core.server.transfer.EntityBooleanDefaultTransferCache;
import com.echothree.model.control.core.server.transfer.EntityClobAttributeTransferCache;
import com.echothree.model.control.core.server.transfer.EntityCollectionAttributeTransferCache;
import com.echothree.model.control.core.server.transfer.EntityDateAttributeTransferCache;
import com.echothree.model.control.core.server.transfer.EntityDateDefaultTransferCache;
import com.echothree.model.control.core.server.transfer.EntityEntityAttributeTransferCache;
import com.echothree.model.control.core.server.transfer.EntityGeoPointAttributeTransferCache;
import com.echothree.model.control.core.server.transfer.EntityGeoPointDefaultTransferCache;
import com.echothree.model.control.core.server.transfer.EntityIntegerAttributeTransferCache;
import com.echothree.model.control.core.server.transfer.EntityIntegerDefaultTransferCache;
import com.echothree.model.control.core.server.transfer.EntityIntegerRangeDescriptionTransferCache;
import com.echothree.model.control.core.server.transfer.EntityIntegerRangeTransferCache;
import com.echothree.model.control.core.server.transfer.EntityListItemAttributeTransferCache;
import com.echothree.model.control.core.server.transfer.EntityListItemDefaultTransferCache;
import com.echothree.model.control.core.server.transfer.EntityListItemDescriptionTransferCache;
import com.echothree.model.control.core.server.transfer.EntityListItemTransferCache;
import com.echothree.model.control.core.server.transfer.EntityLongAttributeTransferCache;
import com.echothree.model.control.core.server.transfer.EntityLongDefaultTransferCache;
import com.echothree.model.control.core.server.transfer.EntityLongRangeDescriptionTransferCache;
import com.echothree.model.control.core.server.transfer.EntityLongRangeTransferCache;
import com.echothree.model.control.core.server.transfer.EntityMultipleListItemAttributeTransferCache;
import com.echothree.model.control.core.server.transfer.EntityMultipleListItemDefaultTransferCache;
import com.echothree.model.control.core.server.transfer.EntityNameAttributeTransferCache;
import com.echothree.model.control.core.server.transfer.EntityStringAttributeTransferCache;
import com.echothree.model.control.core.server.transfer.EntityStringDefaultTransferCache;
import com.echothree.model.control.core.server.transfer.EntityTimeAttributeTransferCache;
import com.echothree.model.control.core.server.transfer.EntityTimeDefaultTransferCache;
import com.echothree.model.data.core.common.pk.EntityAttributePK;
import com.echothree.model.data.core.common.pk.EntityAttributeTypePK;
import com.echothree.model.data.core.common.pk.EntityIntegerRangePK;
import com.echothree.model.data.core.common.pk.EntityListItemPK;
import com.echothree.model.data.core.common.pk.EntityLongRangePK;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityAttributeBlob;
import com.echothree.model.data.core.server.entity.EntityAttributeDescription;
import com.echothree.model.data.core.server.entity.EntityAttributeEntityType;
import com.echothree.model.data.core.server.entity.EntityAttributeGroup;
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
import com.echothree.model.data.core.server.entity.EntityTimeAttribute;
import com.echothree.model.data.core.server.entity.EntityTimeDefault;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.core.server.factory.EntityAttributeBlobFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeDescriptionFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeDetailFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeEntityTypeFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeFactory;
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
import com.echothree.model.data.core.server.factory.EntityEntityAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityGeoPointAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityGeoPointDefaultFactory;
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
import com.echothree.model.data.core.server.value.EntityAttributeBlobValue;
import com.echothree.model.data.core.server.value.EntityAttributeDescriptionValue;
import com.echothree.model.data.core.server.value.EntityAttributeDetailValue;
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
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.Workflow;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.COMPONENT_VENDORS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_ATTRIBUTES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_ATTRIBUTES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_ATTRIBUTE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_ATTRIBUTE_DETAILS_ENTITY_ATTRIBUTE_TYPE_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_ATTRIBUTE_DETAILS_ENTITY_TYPE_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_ATTRIBUTE_ENTITY_ATTRIBUTE_GROUPS_ENTITY_ATTRIBUTE_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_ATTRIBUTE_ENTITY_TYPES_ALLOWED_ENTITY_TYPE_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_ATTRIBUTE_ENTITY_TYPES_ENTITY_ATTRIBUTE_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_ATTRIBUTE_WORKFLOWS_ENTITY_ATTRIBUTE_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_COLLECTION_ATTRIBUTES_ENTITY_INSTANCE_ATTRIBUTE_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_INSTANCES_ENTITY_TYPE_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_INTEGER_RANGES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_INTEGER_RANGE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_LIST_ITEMS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_LIST_ITEMS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_LIST_ITEM_ATTRIBUTES_ENTITY_INSTANCE_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_LIST_ITEM_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_LONG_RANGES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_LONG_RANGE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_MULTIPLE_LIST_ITEM_ATTRIBUTES_ENTITY_INSTANCE_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_MULTIPLE_LIST_ITEM_ATTRIBUTES_ENTITY_LIST_ITEM_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_MULTIPLE_LIST_ITEM_DEFAULTS_ENTITY_ATTRIBUTE_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_MULTIPLE_LIST_ITEM_DEFAULTS_ENTITY_LIST_ITEM_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_NAME_ATTRIBUTES_ENTITY_INSTANCE_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_STRING_DEFAULTS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_TYPE_DETAILS_COMPONENT_VENDOR_FK;
import static com.echothree.model.jooq.server.tables.core.ComponentVendorDetails.ComponentVendorDetails;
import static com.echothree.model.jooq.server.tables.core.ComponentVendors.ComponentVendors;
import static com.echothree.model.jooq.server.tables.core.EntityAttributeBlobs.EntityAttributeBlobs;
import static com.echothree.model.jooq.server.tables.core.EntityAttributeDescriptions.EntityAttributeDescriptions;
import static com.echothree.model.jooq.server.tables.core.EntityAttributeDetails.EntityAttributeDetails;
import static com.echothree.model.jooq.server.tables.core.EntityAttributeEntityAttributeGroups.EntityAttributeEntityAttributeGroups;
import static com.echothree.model.jooq.server.tables.core.EntityAttributeEntityTypes.EntityAttributeEntityTypes;
import static com.echothree.model.jooq.server.tables.core.EntityAttributeIntegers.EntityAttributeIntegers;
import static com.echothree.model.jooq.server.tables.core.EntityAttributeListItems.EntityAttributeListItems;
import static com.echothree.model.jooq.server.tables.core.EntityAttributeLongs.EntityAttributeLongs;
import static com.echothree.model.jooq.server.tables.core.EntityAttributeNumerics.EntityAttributeNumerics;
import static com.echothree.model.jooq.server.tables.core.EntityAttributeStrings.EntityAttributeStrings;
import static com.echothree.model.jooq.server.tables.core.EntityAttributeTypeDescriptions.EntityAttributeTypeDescriptions;
import static com.echothree.model.jooq.server.tables.core.EntityAttributeTypes.EntityAttributeTypes;
import static com.echothree.model.jooq.server.tables.core.EntityAttributeWorkflows.EntityAttributeWorkflows;
import static com.echothree.model.jooq.server.tables.core.EntityAttributes.EntityAttributes;
import static com.echothree.model.jooq.server.tables.core.EntityBlobAttributes.EntityBlobAttributes;
import static com.echothree.model.jooq.server.tables.core.EntityBooleanAttributes.EntityBooleanAttributes;
import static com.echothree.model.jooq.server.tables.core.EntityBooleanDefaults.EntityBooleanDefaults;
import static com.echothree.model.jooq.server.tables.core.EntityClobAttributes.EntityClobAttributes;
import static com.echothree.model.jooq.server.tables.core.EntityCollectionAttributes.EntityCollectionAttributes;
import static com.echothree.model.jooq.server.tables.core.EntityDateAttributes.EntityDateAttributes;
import static com.echothree.model.jooq.server.tables.core.EntityDateDefaults.EntityDateDefaults;
import static com.echothree.model.jooq.server.tables.core.EntityEntityAttributes.EntityEntityAttributes;
import static com.echothree.model.jooq.server.tables.core.EntityGeoPointAttributes.EntityGeoPointAttributes;
import static com.echothree.model.jooq.server.tables.core.EntityGeoPointDefaults.EntityGeoPointDefaults;
import static com.echothree.model.jooq.server.tables.core.EntityInstances.EntityInstances;
import static com.echothree.model.jooq.server.tables.core.EntityIntegerAttributes.EntityIntegerAttributes;
import static com.echothree.model.jooq.server.tables.core.EntityIntegerDefaults.EntityIntegerDefaults;
import static com.echothree.model.jooq.server.tables.core.EntityIntegerRangeDescriptions.EntityIntegerRangeDescriptions;
import static com.echothree.model.jooq.server.tables.core.EntityIntegerRangeDetails.EntityIntegerRangeDetails;
import static com.echothree.model.jooq.server.tables.core.EntityIntegerRanges.EntityIntegerRanges;
import static com.echothree.model.jooq.server.tables.core.EntityListItemAttributes.EntityListItemAttributes;
import static com.echothree.model.jooq.server.tables.core.EntityListItemDefaults.EntityListItemDefaults;
import static com.echothree.model.jooq.server.tables.core.EntityListItemDescriptions.EntityListItemDescriptions;
import static com.echothree.model.jooq.server.tables.core.EntityListItemDetails.EntityListItemDetails;
import static com.echothree.model.jooq.server.tables.core.EntityListItems.EntityListItems;
import static com.echothree.model.jooq.server.tables.core.EntityLongAttributes.EntityLongAttributes;
import static com.echothree.model.jooq.server.tables.core.EntityLongDefaults.EntityLongDefaults;
import static com.echothree.model.jooq.server.tables.core.EntityLongRangeDescriptions.EntityLongRangeDescriptions;
import static com.echothree.model.jooq.server.tables.core.EntityLongRangeDetails.EntityLongRangeDetails;
import static com.echothree.model.jooq.server.tables.core.EntityLongRanges.EntityLongRanges;
import static com.echothree.model.jooq.server.tables.core.EntityMultipleListItemAttributes.EntityMultipleListItemAttributes;
import static com.echothree.model.jooq.server.tables.core.EntityMultipleListItemDefaults.EntityMultipleListItemDefaults;
import static com.echothree.model.jooq.server.tables.core.EntityNameAttributes.EntityNameAttributes;
import static com.echothree.model.jooq.server.tables.core.EntityStringAttributes.EntityStringAttributes;
import static com.echothree.model.jooq.server.tables.core.EntityStringDefaults.EntityStringDefaults;
import static com.echothree.model.jooq.server.tables.core.EntityTimeAttributes.EntityTimeAttributes;
import static com.echothree.model.jooq.server.tables.core.EntityTimeDefaults.EntityTimeDefaults;
import static com.echothree.model.jooq.server.tables.core.EntityTypeDetails.EntityTypeDetails;
import static com.echothree.model.jooq.server.tables.core.EntityTypes.EntityTypes;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;

@CommandScope
public class CoreControl
        extends BaseModelControl {
    
    @Inject
    protected EntityAttributeGroupControl entityAttributeGroupControl;

    /** Creates a new instance of CoreControl */
    protected CoreControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Attribute Types
    // --------------------------------------------------------------------------------

    @Inject
    protected EntityAttributeTypeFactory entityAttributeTypeFactory;

    @Inject
    EntityAttributeTypeTransferCache entityAttributeTypeTransferCache;

    public EntityAttributeType createEntityAttributeType(String entityAttributeTypeName) {
        var entityAttributeType = entityAttributeTypeFactory.create(entityAttributeTypeName);
        
        return entityAttributeType;
    }
    
    /** Assume that the entityInstance passed to this function is a ECHO_THREE.EntityAttributeType */
    public EntityAttributeType getEntityAttributeTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new EntityAttributeTypePK(entityInstance.getEntityUniqueId());

        return entityAttributeTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public EntityAttributeType getEntityAttributeTypeByEntityInstance(EntityInstance entityInstance) {
        return getEntityAttributeTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public EntityAttributeType getEntityAttributeTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getEntityAttributeTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countEntityAttributeTypes() {
        return session.getDslContext()
                .selectCount()
                .from(EntityAttributeTypes)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public EntityAttributeType getEntityAttributeTypeByName(String entityAttributeTypeName) {
        var query = session.getDslContext()
                .select(EntityAttributeTypes.fields())
                .from(EntityAttributeTypes)
                .where(EntityAttributeTypes.ENTITY_ATTRIBUTE_TYPE_NAME.eq(entityAttributeTypeName));
        
        return entityAttributeTypeFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public List<EntityAttributeType> getEntityAttributeTypes() {
        var query = session.applyLimit(session.getDslContext()
                .select(EntityAttributeTypes.fields())
                .from(EntityAttributeTypes)
                .orderBy(EntityAttributeTypes.ENTITY_ATTRIBUTE_TYPE_NAME),
                EntityAttributeTypeFactory.class);
        
        return entityAttributeTypeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public EntityAttributeTypeTransfer getEntityAttributeTypeTransfer(UserVisit userVisit, EntityAttributeType entityAttributeType) {
        return entityAttributeTypeTransferCache.getEntityAttributeTypeTransfer(userVisit, entityAttributeType);
    }
    
    public List<EntityAttributeTypeTransfer> getEntityAttributeTypeTransfers(UserVisit userVisit, Collection<EntityAttributeType> entityAttributeTypes) {
        List<EntityAttributeTypeTransfer> entityAttributeTypeTransfers = null;
        
        if(entityAttributeTypes != null) {
            entityAttributeTypeTransfers = new ArrayList<>(entityAttributeTypes.size());
            
            for(var entityAttributeType : entityAttributeTypes) {
                entityAttributeTypeTransfers.add(entityAttributeTypeTransferCache.getEntityAttributeTypeTransfer(userVisit, entityAttributeType));
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

    @Inject
    protected EntityAttributeTypeDescriptionFactory entityAttributeTypeDescriptionFactory;

    public EntityAttributeTypeDescription createEntityAttributeTypeDescription(EntityAttributeType entityAttributeType, Language language, String description) {
        var entityAttributeTypeDescription = entityAttributeTypeDescriptionFactory.create(entityAttributeType, language, description);
        
        return entityAttributeTypeDescription;
    }
    
    public EntityAttributeTypeDescription getEntityAttributeTypeDescription(EntityAttributeType entityAttributeType, Language language) {
        var query = session.getDslContext()
                .select(EntityAttributeTypeDescriptions.fields())
                .from(EntityAttributeTypeDescriptions)
                .where(EntityAttributeTypeDescriptions.ENTITY_ATTRIBUTE_TYPE.eq(entityAttributeType.getPrimaryKey()),
                        EntityAttributeTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()));
        
        return entityAttributeTypeDescriptionFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public String getBestEntityAttributeTypeDescription(EntityAttributeType entityAttributeType, Language language) {
        String description;
        var entityAttributeTypeDescription = getEntityAttributeTypeDescription(entityAttributeType, language);
        
        if(entityAttributeTypeDescription == null && !language.getIsDefault()) {
            entityAttributeTypeDescription = getEntityAttributeTypeDescription(entityAttributeType, partyControl.getDefaultLanguage());
        }
        
        if(entityAttributeTypeDescription == null) {
            description = entityAttributeType.getEntityAttributeTypeName();
        } else {
            description = entityAttributeTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Attributes
    // --------------------------------------------------------------------------------

    @Inject
    protected EntityAttributeDetailFactory entityAttributeDetailFactory;

    @Inject
    protected EntityAttributeFactory entityAttributeFactory;

    @Inject
    EntityAttributeTransferCache entityAttributeTransferCache;

    public EntityAttribute createEntityAttribute(EntityType entityType, String entityAttributeName,
            EntityAttributeType entityAttributeType, Boolean trackRevisions, Integer sortOrder, BasePK createdBy) {
        var entityAttribute = entityAttributeFactory.create();
        var entityAttributeDetail = entityAttributeDetailFactory.create(entityAttribute, entityType,
                entityAttributeName, entityAttributeType, trackRevisions, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        entityAttribute = entityAttributeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
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

        return entityAttributeFactory.getEntityFromPK(entityPermission, pk);
    }

    public EntityAttribute getEntityAttributeByEntityInstance(EntityInstance entityInstance) {
        return getEntityAttributeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public EntityAttribute getEntityAttributeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getEntityAttributeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    public EntityAttribute getEntityAttributeByPK(EntityAttributePK pk) {
        return entityAttributeFactory.getEntityFromPK(EntityPermission.READ_ONLY, pk);
    }

    public long countEntityAttributesByEntityType(EntityType entityType) {
        return session.getDslContext()
                .selectCount()
                .from(EntityAttributes)
                .join(EntityAttributeDetails).onKey(ENTITY_ATTRIBUTES_ACTIVE_DETAIL_FK)
                .where(EntityAttributeDetails.ENTITY_TYPE.eq(entityType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countEntityAttributesByEntityTypeAndEntityAttributeType(EntityType entityType, EntityAttributeType entityAttributeType) {
        return session.getDslContext()
                .selectCount()
                .from(EntityAttributes)
                .join(EntityAttributeDetails).onKey(ENTITY_ATTRIBUTES_ACTIVE_DETAIL_FK)
                .where(EntityAttributeDetails.ENTITY_TYPE.eq(entityType.getPrimaryKey()),
                        EntityAttributeDetails.ENTITY_ATTRIBUTE_TYPE.eq(entityAttributeType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countEntityAttributesByEntityAttributeGroup(EntityAttributeGroup entityAttributeGroup) {
        return session.getDslContext()
                .selectCount()
                .from(EntityAttributeEntityAttributeGroups)
                .join(EntityAttributes).onKey(ENTITY_ATTRIBUTE_ENTITY_ATTRIBUTE_GROUPS_ENTITY_ATTRIBUTE_FK)
                .join(EntityAttributeDetails).onKey(ENTITY_ATTRIBUTES_LAST_DETAIL_FK)
                .where(EntityAttributeEntityAttributeGroups.ENTITY_ATTRIBUTE_GROUP.eq(entityAttributeGroup.getPrimaryKey()),
                        EntityAttributeEntityAttributeGroups.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countEntityAttributesByEntityTypeAndWorkflow(final EntityType entityType, final Workflow workflow) {
        return session.getDslContext()
                .selectCount()
                .from(EntityAttributes)
                .join(EntityAttributeDetails).onKey(ENTITY_ATTRIBUTES_ACTIVE_DETAIL_FK)
                .join(EntityAttributeTypes).onKey(ENTITY_ATTRIBUTE_DETAILS_ENTITY_ATTRIBUTE_TYPE_FK)
                .join(EntityAttributeWorkflows).onKey(ENTITY_ATTRIBUTE_WORKFLOWS_ENTITY_ATTRIBUTE_FK)
                .where(EntityAttributeWorkflows.THRU_TIME.eq(Session.MAX_TIME),
                        EntityAttributeDetails.ENTITY_TYPE.eq(entityType.getPrimaryKey()),
                        EntityAttributeTypes.ENTITY_ATTRIBUTE_TYPE_NAME.eq(com.echothree.model.control.core.common.EntityAttributeTypes.WORKFLOW.name()),
                        EntityAttributeWorkflows.WORKFLOW.eq(workflow.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public EntityAttribute getEntityAttributeByName(EntityType entityType, String entityAttributeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EntityAttributes.fields())
                .from(EntityAttributes)
                .join(EntityAttributeDetails).onKey(ENTITY_ATTRIBUTES_ACTIVE_DETAIL_FK)
                .where(EntityAttributeDetails.ENTITY_TYPE.eq(entityType.getPrimaryKey()),
                        EntityAttributeDetails.ENTITY_ATTRIBUTE_NAME.eq(entityAttributeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return entityAttributeFactory.getEntityFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(EntityAttributes.fields())
                    .from(EntityAttributes)
                    .join(EntityAttributeDetails).onKey(ENTITY_ATTRIBUTES_ACTIVE_DETAIL_FK)
                    .where(EntityAttributeDetails.ENTITY_TYPE.eq(entityType.getPrimaryKey()))
                    .orderBy(EntityAttributeDetails.SORT_ORDER, EntityAttributeDetails.ENTITY_ATTRIBUTE_NAME),
                    EntityAttributeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(EntityAttributes.fields())
                    .from(EntityAttributes)
                    .join(EntityAttributeDetails).onKey(ENTITY_ATTRIBUTES_ACTIVE_DETAIL_FK)
                    .where(EntityAttributeDetails.ENTITY_TYPE.eq(entityType.getPrimaryKey()))
                    .forUpdate();
        };

        return entityAttributeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<EntityAttribute> getEntityAttributesByEntityType(EntityType entityType) {
        return getEntityAttributesByEntityType(entityType, EntityPermission.READ_ONLY);
    }

    public List<EntityAttribute> getEntityAttributesByEntityTypeForUpdate(EntityType entityType) {
        return getEntityAttributesByEntityType(entityType, EntityPermission.READ_WRITE);
    }

    private List<EntityAttribute> getEntityAttributesByName(String entityAttributeName, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(EntityAttributes.fields())
                    .from(EntityAttributes)
                    .join(EntityAttributeDetails).onKey(ENTITY_ATTRIBUTES_ACTIVE_DETAIL_FK)
                    .join(EntityTypes).onKey(ENTITY_ATTRIBUTE_DETAILS_ENTITY_TYPE_FK)
                    .join(EntityTypeDetails).onKey(ENTITY_TYPES_LAST_DETAIL_FK)
                    .join(ComponentVendors).onKey(ENTITY_TYPE_DETAILS_COMPONENT_VENDOR_FK)
                    .join(ComponentVendorDetails).onKey(COMPONENT_VENDORS_LAST_DETAIL_FK)
                    .where(EntityAttributeDetails.ENTITY_ATTRIBUTE_NAME.eq(entityAttributeName))
                    .orderBy(ComponentVendorDetails.COMPONENT_VENDOR_NAME, EntityTypeDetails.SORT_ORDER, EntityTypeDetails.ENTITY_TYPE_NAME, EntityAttributeDetails.SORT_ORDER, EntityAttributeDetails.ENTITY_ATTRIBUTE_NAME),
                    EntityAttributeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(EntityAttributes.fields())
                    .from(EntityAttributes)
                    .join(EntityAttributeDetails).onKey(ENTITY_ATTRIBUTES_ACTIVE_DETAIL_FK)
                    .where(EntityAttributeDetails.ENTITY_ATTRIBUTE_NAME.eq(entityAttributeName))
                    .forUpdate();
        };

        return entityAttributeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<EntityAttribute> getEntityAttributesByName(String entityAttributeName) {
        return getEntityAttributesByName(entityAttributeName, EntityPermission.READ_ONLY);
    }

    public List<EntityAttribute> getEntityAttributesByNameForUpdate(String entityAttributeName) {
        return getEntityAttributesByName(entityAttributeName, EntityPermission.READ_WRITE);
    }

    private List<EntityAttribute> getEntityAttributesByEntityTypeAndEntityAttributeType(EntityType entityType,
            EntityAttributeType entityAttributeType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(EntityAttributes.fields())
                    .from(EntityAttributes)
                    .join(EntityAttributeDetails).onKey(ENTITY_ATTRIBUTES_ACTIVE_DETAIL_FK)
                    .where(EntityAttributeDetails.ENTITY_TYPE.eq(entityType.getPrimaryKey()),
                            EntityAttributeDetails.ENTITY_ATTRIBUTE_TYPE.eq(entityAttributeType.getPrimaryKey()))
                    .orderBy(EntityAttributeDetails.SORT_ORDER, EntityAttributeDetails.ENTITY_ATTRIBUTE_NAME),
                    EntityAttributeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(EntityAttributes.fields())
                    .from(EntityAttributes)
                    .join(EntityAttributeDetails).onKey(ENTITY_ATTRIBUTES_ACTIVE_DETAIL_FK)
                    .where(EntityAttributeDetails.ENTITY_TYPE.eq(entityType.getPrimaryKey()),
                            EntityAttributeDetails.ENTITY_ATTRIBUTE_TYPE.eq(entityAttributeType.getPrimaryKey()))
                    .forUpdate();
        };

        return entityAttributeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<EntityAttribute> getEntityAttributesByEntityTypeAndEntityAttributeType(EntityType entityType,
            EntityAttributeType entityAttributeType) {
        return getEntityAttributesByEntityTypeAndEntityAttributeType(entityType, entityAttributeType, EntityPermission.READ_ONLY);
    }
    
    public List<EntityAttribute> getEntityAttributesByEntityTypeAndEntityAttributeTypeForUpdate(EntityType entityType,
            EntityAttributeType entityAttributeType) {
        return getEntityAttributesByEntityTypeAndEntityAttributeType(entityType, entityAttributeType, EntityPermission.READ_WRITE);
    }
    
    private List<EntityAttribute> getEntityAttributesByEntityAttributeGroup(EntityAttributeGroup entityAttributeGroup,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(EntityAttributes.fields())
                    .from(EntityAttributes)
                    .join(EntityAttributeEntityAttributeGroups).onKey(ENTITY_ATTRIBUTE_ENTITY_ATTRIBUTE_GROUPS_ENTITY_ATTRIBUTE_FK)
                    .join(EntityAttributeDetails).onKey(ENTITY_ATTRIBUTES_LAST_DETAIL_FK)
                    .where(EntityAttributeEntityAttributeGroups.ENTITY_ATTRIBUTE_GROUP.eq(entityAttributeGroup.getPrimaryKey()),
                            EntityAttributeEntityAttributeGroups.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(EntityAttributeEntityAttributeGroups.SORT_ORDER, EntityAttributeDetails.SORT_ORDER, EntityAttributeDetails.ENTITY_ATTRIBUTE_NAME),
                    EntityAttributeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(EntityAttributes.fields())
                    .from(EntityAttributes)
                    .join(EntityAttributeEntityAttributeGroups).onKey(ENTITY_ATTRIBUTE_ENTITY_ATTRIBUTE_GROUPS_ENTITY_ATTRIBUTE_FK)
                    .join(EntityAttributeDetails).onKey(ENTITY_ATTRIBUTES_LAST_DETAIL_FK)
                    .where(EntityAttributeEntityAttributeGroups.ENTITY_ATTRIBUTE_GROUP.eq(entityAttributeGroup.getPrimaryKey()),
                            EntityAttributeEntityAttributeGroups.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return entityAttributeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<EntityAttribute> getEntityAttributesByEntityAttributeGroup(EntityAttributeGroup entityAttributeGroup) {
        return getEntityAttributesByEntityAttributeGroup(entityAttributeGroup, EntityPermission.READ_ONLY);
    }
    
    public List<EntityAttribute> getEntityAttributesByEntityAttributeGroupForUpdate(EntityAttributeGroup entityAttributeGroup) {
        return getEntityAttributesByEntityAttributeGroup(entityAttributeGroup, EntityPermission.READ_WRITE);
    }
    
    public EntityAttributeTransfer getEntityAttributeTransfer(UserVisit userVisit, EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return entityAttributeTransferCache.getEntityAttributeTransfer(userVisit, entityAttribute, entityInstance);
    }
    
    public List<EntityAttributeTransfer> getEntityAttributeTransfers(UserVisit userVisit, Collection<EntityAttribute> entityAttributes, EntityInstance entityInstance) {
        List<EntityAttributeTransfer> entityAttributeTransfers = new ArrayList<>(entityAttributes.size());
        
        entityAttributes.forEach((entityAttribute) ->
                entityAttributeTransfers.add(entityAttributeTransferCache.getEntityAttributeTransfer(userVisit, entityAttribute, entityInstance))
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
    
    public List<EntityAttributeTransfer> getEntityAttributeTransfersByEntityAttributeGroup(UserVisit userVisit,
            EntityAttributeGroup entityAttributeGroup, EntityInstance entityInstance) {
        return getEntityAttributeTransfers(userVisit, getEntityAttributesByEntityAttributeGroup(entityAttributeGroup),
                entityInstance);
    }
    
    public void updateEntityAttributeFromValue(final EntityAttributeDetailValue entityAttributeDetailValue, final BasePK updatedBy) {
        if(entityAttributeDetailValue.hasBeenModified()) {
            final var entityAttribute = entityAttributeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     entityAttributeDetailValue.getEntityAttributePK());
            var entityAttributeDetail = entityAttribute.getActiveDetailForUpdate();
            
            entityAttributeDetail.setThruTime(session.getStartTime());
            entityAttributeDetail.store();

            final var entityAttributePK = entityAttributeDetail.getEntityAttributePK(); // Not updated
            final var entityTypePK = entityAttributeDetail.getEntityTypePK(); // Not updated
            final var entityAttributeName = entityAttributeDetailValue.getEntityAttributeName();
            final var entityAttributeTypePK = entityAttributeDetail.getEntityAttributeTypePK(); // Not updated
            final var trackRevisions = entityAttributeDetailValue.getTrackRevisions();
            final var sortOrder = entityAttributeDetailValue.getSortOrder();
            
            entityAttributeDetail = entityAttributeDetailFactory.create(entityAttributePK, entityTypePK,
                    entityAttributeName, entityAttributeTypePK, trackRevisions, sortOrder, session.getStartTime(),
                    Session.MAX_TIME);
            
            entityAttribute.setActiveDetail(entityAttributeDetail);
            entityAttribute.setLastDetail(entityAttributeDetail);
            
            sendEvent(entityAttributePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deleteEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        var entityAttributeDetail = entityAttribute.getLastDetailForUpdate();
        var entityAttributeTypeName = entityAttributeDetail.getEntityAttributeType().getEntityAttributeTypeName();
        var entityAttributeType = com.echothree.model.control.core.common.EntityAttributeTypes.valueOf(entityAttributeTypeName);

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
                var entityAttributeWorkflow = getEntityAttributeWorkflow(entityAttribute);

                workflowControl.deleteWorkflowEntityStatusesByWorkflowAndEntityType(entityAttributeWorkflow.getWorkflow(),
                        entityAttributeDetail.getEntityType(), deletedBy);
                deleteEntityAttributeWorkflowByEntityAttribute(entityAttribute, deletedBy);
            }
        }
        
        entityAttributeGroupControl.deleteEntityAttributeEntityAttributeGroupsByEntityAttribute(entityAttribute, deletedBy);
        deleteEntityAttributeDescriptionsByEntityAttribute(entityAttribute, deletedBy);
        
        if(entityAttribute.getEntityPermission().equals(EntityPermission.READ_ONLY)) {
            // Convert to R/W
            entityAttribute = entityAttributeFactory.getEntityFromPK(EntityPermission.READ_WRITE, entityAttribute.getPrimaryKey());
        }
        
        entityAttributeDetail.setThruTime(session.getStartTime());
        entityAttribute.setActiveDetail(null);
        entityAttribute.store();
        
        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteEntityAttributesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        var entityAttributes = getEntityAttributesByEntityType(entityInstance.getEntityType());
        
        entityAttributes.stream().map((entityAttribute) -> entityAttribute.getLastDetailForUpdate().getEntityAttributeType().getEntityAttributeTypeName()).forEach((entityAttributeTypeName) -> {
            if(entityAttributeTypeName.equals(com.echothree.model.control.core.common.EntityAttributeTypes.BOOLEAN.name())) {
                deleteEntityBooleanAttributesByEntityInstance(entityInstance, deletedBy);
            } else if(entityAttributeTypeName.equals(com.echothree.model.control.core.common.EntityAttributeTypes.NAME.name())) {
                deleteEntityNameAttributesByEntityInstance(entityInstance, deletedBy);
            } else if(entityAttributeTypeName.equals(com.echothree.model.control.core.common.EntityAttributeTypes.INTEGER.name())) {
                deleteEntityIntegerAttributesByEntityInstance(entityInstance, deletedBy);
            } else if(entityAttributeTypeName.equals(com.echothree.model.control.core.common.EntityAttributeTypes.LONG.name())) {
                deleteEntityLongAttributesByEntityInstance(entityInstance, deletedBy);
            } else if(entityAttributeTypeName.equals(com.echothree.model.control.core.common.EntityAttributeTypes.STRING.name())) {
                deleteEntityStringAttributesByEntityInstance(entityInstance, deletedBy);
            } else if(entityAttributeTypeName.equals(com.echothree.model.control.core.common.EntityAttributeTypes.GEOPOINT.name())) {
                deleteEntityGeoPointAttributesByEntityInstance(entityInstance, deletedBy);
            } else if(entityAttributeTypeName.equals(com.echothree.model.control.core.common.EntityAttributeTypes.BLOB.name())) {
                deleteEntityBlobAttributesByEntityInstance(entityInstance, deletedBy);
            } else if(entityAttributeTypeName.equals(com.echothree.model.control.core.common.EntityAttributeTypes.CLOB.name())) {
                deleteEntityClobAttributesByEntityInstance(entityInstance, deletedBy);
            } else if(entityAttributeTypeName.equals(com.echothree.model.control.core.common.EntityAttributeTypes.ENTITY.name())) {
                deleteEntityEntityAttributesByEntityInstance(entityInstance, deletedBy);
            } else if(entityAttributeTypeName.equals(com.echothree.model.control.core.common.EntityAttributeTypes.COLLECTION.name())) {
                deleteEntityCollectionAttributesByEntityInstance(entityInstance, deletedBy);
            } else if(entityAttributeTypeName.equals(com.echothree.model.control.core.common.EntityAttributeTypes.DATE.name())) {
                deleteEntityDateAttributesByEntityInstance(entityInstance, deletedBy);
            } else if(entityAttributeTypeName.equals(com.echothree.model.control.core.common.EntityAttributeTypes.TIME.name())) {
                deleteEntityTimeAttributesByEntityInstance(entityInstance, deletedBy);
            } else if(entityAttributeTypeName.equals(com.echothree.model.control.core.common.EntityAttributeTypes.LISTITEM.name())) {
                deleteEntityListItemAttributesByEntityInstance(entityInstance, deletedBy);
            } else if(entityAttributeTypeName.equals(com.echothree.model.control.core.common.EntityAttributeTypes.MULTIPLELISTITEM.name())) {
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

    @Inject
    protected EntityAttributeDescriptionFactory entityAttributeDescriptionFactory;

    @Inject
    EntityAttributeDescriptionTransferCache entityAttributeDescriptionTransferCache;

    public EntityAttributeDescription createEntityAttributeDescription(EntityAttribute entityAttribute, Language language,
            String description, BasePK createdBy) {
        var entityAttributeDescription = entityAttributeDescriptionFactory.create(
                entityAttribute, language, description, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return entityAttributeDescription;
    }
    
    private EntityAttributeDescription getEntityAttributeDescription(EntityAttribute entityAttribute, Language language,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EntityAttributeDescriptions.fields())
                .from(EntityAttributeDescriptions)
                .where(EntityAttributeDescriptions.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityAttributeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        EntityAttributeDescriptions.THRU_TIME.eq(Session.MAX_TIME));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };
            
        return entityAttributeDescriptionFactory.getEntityFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(EntityAttributeDescriptions.fields())
                    .from(EntityAttributeDescriptions)
                    .join(Languages).onKey(ENTITY_ATTRIBUTE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(EntityAttributeDescriptions.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                            EntityAttributeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    EntityAttributeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(EntityAttributeDescriptions.fields())
                    .from(EntityAttributeDescriptions)
                    .where(EntityAttributeDescriptions.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                            EntityAttributeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return entityAttributeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
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
            entityAttributeDescription = getEntityAttributeDescription(entityAttribute, partyControl.getDefaultLanguage());
        }
        
        if(entityAttributeDescription == null) {
            description = entityAttribute.getLastDetail().getEntityAttributeName();
        } else {
            description = entityAttributeDescription.getDescription();
        }
        
        return description;
    }
    
    public EntityAttributeDescriptionTransfer getEntityAttributeDescriptionTransfer(UserVisit userVisit, EntityAttributeDescription entityAttributeDescription, EntityInstance entityInstance) {
        return entityAttributeDescriptionTransferCache.getEntityAttributeDescriptionTransfer(userVisit, entityAttributeDescription, entityInstance);
    }
    
    public List<EntityAttributeDescriptionTransfer> getEntityAttributeDescriptionTransfersByEntityAttribute(UserVisit userVisit,
            EntityAttribute entityAttribute, EntityInstance entityInstance) {
        var entityAttributeDescriptions = getEntityAttributeDescriptionsByEntityAttribute(entityAttribute);
        List<EntityAttributeDescriptionTransfer> entityAttributeDescriptionTransfers = new ArrayList<>(entityAttributeDescriptions.size());
        
        entityAttributeDescriptions.forEach((entityAttributeDescription) ->
                entityAttributeDescriptionTransfers.add(entityAttributeDescriptionTransferCache.getEntityAttributeDescriptionTransfer(userVisit, entityAttributeDescription, entityInstance))
        );
        
        return entityAttributeDescriptionTransfers;
    }
    
    public void updateEntityAttributeDescriptionFromValue(EntityAttributeDescriptionValue entityAttributeDescriptionValue, BasePK updatedBy) {
        if(entityAttributeDescriptionValue.hasBeenModified()) {
            var entityAttributeDescription = entityAttributeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     entityAttributeDescriptionValue.getPrimaryKey());
            
            entityAttributeDescription.setThruTime(session.getStartTime());
            entityAttributeDescription.store();

            var entityAttribute = entityAttributeDescription.getEntityAttribute();
            var language = entityAttributeDescription.getLanguage();
            var description = entityAttributeDescriptionValue.getDescription();
            
            entityAttributeDescription = entityAttributeDescriptionFactory.create(entityAttribute, language,
                    description, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityAttributeDescription(EntityAttributeDescription entityAttributeDescription, BasePK deletedBy) {
        entityAttributeDescription.setThruTime(session.getStartTime());
        
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

    @Inject
    protected EntityAttributeBlobFactory entityAttributeBlobFactory;

    public EntityAttributeBlob createEntityAttributeBlob(EntityAttribute entityAttribute, Boolean checkContentWebAddress,
            BasePK createdBy) {
        var entityAttributeBlob = entityAttributeBlobFactory.create(
                entityAttribute, checkContentWebAddress, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeBlob.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return entityAttributeBlob;
    }
    
    private EntityAttributeBlob getEntityAttributeBlob(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EntityAttributeBlobs.fields())
                .from(EntityAttributeBlobs)
                .where(EntityAttributeBlobs.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityAttributeBlobs.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return entityAttributeBlobFactory.getEntityFromQuery(entityPermission, query);
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
            var entityAttributeBlob = entityAttributeBlobFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     entityAttributeBlobValue.getPrimaryKey());
            
            entityAttributeBlob.setThruTime(session.getStartTime());
            entityAttributeBlob.store();

            var entityAttribute = entityAttributeBlob.getEntityAttribute();
            var checkContentWebAddress = entityAttributeBlobValue.getCheckContentWebAddress();
            
            entityAttributeBlob = entityAttributeBlobFactory.create(entityAttribute, checkContentWebAddress,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeBlob.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityAttributeBlob(EntityAttributeBlob entityAttributeBlob, BasePK deletedBy) {
        entityAttributeBlob.setThruTime(session.getStartTime());
        
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

    @Inject
    protected EntityAttributeStringFactory entityAttributeStringFactory;

    public EntityAttributeString createEntityAttributeString(EntityAttribute entityAttribute, String validationPattern,
            BasePK createdBy) {
        var entityAttributeString = entityAttributeStringFactory.create(
                entityAttribute, validationPattern, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeString.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return entityAttributeString;
    }
    
    private EntityAttributeString getEntityAttributeString(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EntityAttributeStrings.fields())
                .from(EntityAttributeStrings)
                .where(EntityAttributeStrings.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityAttributeStrings.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return entityAttributeStringFactory.getEntityFromQuery(entityPermission, query);
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
            var entityAttributeString = entityAttributeStringFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     entityAttributeStringValue.getPrimaryKey());
            
            entityAttributeString.setThruTime(session.getStartTime());
            entityAttributeString.store();

            var entityAttribute = entityAttributeString.getEntityAttribute();
            var validationPattern = entityAttributeStringValue.getValidationPattern();
            
            entityAttributeString = entityAttributeStringFactory.create(entityAttribute, validationPattern,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeString.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityAttributeString(EntityAttributeString entityAttributeString, BasePK deletedBy) {
        entityAttributeString.setThruTime(session.getStartTime());
        
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

    @Inject
    protected EntityAttributeIntegerFactory entityAttributeIntegerFactory;

    public EntityAttributeInteger createEntityAttributeInteger(EntityAttribute entityAttribute, Integer upperRangeIntegerValue,
            Integer upperLimitIntegerValue, Integer lowerLimitIntegerValue, Integer lowerRangeIntegerValue, BasePK createdBy) {
        var entityAttributeInteger = entityAttributeIntegerFactory.create(
                entityAttribute, upperRangeIntegerValue, upperLimitIntegerValue, lowerLimitIntegerValue, lowerRangeIntegerValue,
                session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeInteger.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return entityAttributeInteger;
    }
    
    private EntityAttributeInteger getEntityAttributeInteger(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EntityAttributeIntegers.fields())
                .from(EntityAttributeIntegers)
                .where(EntityAttributeIntegers.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityAttributeIntegers.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return entityAttributeIntegerFactory.getEntityFromQuery(entityPermission, query);
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
            var entityAttributeInteger = entityAttributeIntegerFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     entityAttributeIntegerValue.getPrimaryKey());
            
            entityAttributeInteger.setThruTime(session.getStartTime());
            entityAttributeInteger.store();

            var entityAttribute = entityAttributeInteger.getEntityAttribute();
            var upperRangeIntegerValue = entityAttributeIntegerValue.getUpperRangeIntegerValue();
            var upperLimitIntegerValue = entityAttributeIntegerValue.getUpperLimitIntegerValue();
            var lowerLimitIntegerValue = entityAttributeIntegerValue.getLowerLimitIntegerValue();
            var lowerRangeIntegerValue = entityAttributeIntegerValue.getLowerRangeIntegerValue();
            
            entityAttributeInteger = entityAttributeIntegerFactory.create(entityAttribute, upperRangeIntegerValue,
                    upperLimitIntegerValue, lowerLimitIntegerValue, lowerRangeIntegerValue, session.getStartTime(),
                    Session.MAX_TIME);
            
            sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeInteger.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityAttributeInteger(EntityAttributeInteger entityAttributeInteger, BasePK deletedBy) {
        entityAttributeInteger.setThruTime(session.getStartTime());
        
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

    @Inject
    protected EntityAttributeLongFactory entityAttributeLongFactory;

    public EntityAttributeLong createEntityAttributeLong(EntityAttribute entityAttribute, Long upperRangeLongValue,
            Long upperLimitLongValue, Long lowerLimitLongValue, Long lowerRangeLongValue, BasePK createdBy) {
        var entityAttributeLong = entityAttributeLongFactory.create(
                entityAttribute, upperRangeLongValue, upperLimitLongValue, lowerLimitLongValue, lowerRangeLongValue,
                session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeLong.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return entityAttributeLong;
    }
    
    private EntityAttributeLong getEntityAttributeLong(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EntityAttributeLongs.fields())
                .from(EntityAttributeLongs)
                .where(EntityAttributeLongs.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityAttributeLongs.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return entityAttributeLongFactory.getEntityFromQuery(entityPermission, query);
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
            var entityAttributeLong = entityAttributeLongFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     entityAttributeLongValue.getPrimaryKey());
            
            entityAttributeLong.setThruTime(session.getStartTime());
            entityAttributeLong.store();

            var entityAttribute = entityAttributeLong.getEntityAttribute();
            var upperRangeLongValue = entityAttributeLongValue.getUpperRangeLongValue();
            var upperLimitLongValue = entityAttributeLongValue.getUpperLimitLongValue();
            var lowerLimitLongValue = entityAttributeLongValue.getLowerLimitLongValue();
            var lowerRangeLongValue = entityAttributeLongValue.getLowerRangeLongValue();
            
            entityAttributeLong = entityAttributeLongFactory.create(entityAttribute, upperRangeLongValue,
                    upperLimitLongValue, lowerLimitLongValue, lowerRangeLongValue, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeLong.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityAttributeLong(EntityAttributeLong entityAttributeLong, BasePK deletedBy) {
        entityAttributeLong.setThruTime(session.getStartTime());
        
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

    @Inject
    protected EntityAttributeNumericFactory entityAttributeNumericFactory;

    public EntityAttributeNumeric createEntityAttributeNumeric(EntityAttribute entityAttribute, UnitOfMeasureType unitOfMeasureType,
            BasePK createdBy) {
        var entityAttributeNumeric = entityAttributeNumericFactory.create(
                entityAttribute, unitOfMeasureType, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeNumeric.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return entityAttributeNumeric;
    }
    
    public long countEntityAttributeNumericsByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType) {
        return session.getDslContext()
                .selectCount()
                .from(EntityAttributeNumerics)
                .where(EntityAttributeNumerics.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                        EntityAttributeNumerics.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private EntityAttributeNumeric getEntityAttributeNumeric(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EntityAttributeNumerics.fields())
                .from(EntityAttributeNumerics)
                .where(EntityAttributeNumerics.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityAttributeNumerics.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return entityAttributeNumericFactory.getEntityFromQuery(entityPermission, query);
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
            var entityAttributeNumeric = entityAttributeNumericFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     entityAttributeNumericValue.getPrimaryKey());
            
            entityAttributeNumeric.setThruTime(session.getStartTime());
            entityAttributeNumeric.store();

            var entityAttributePK = entityAttributeNumeric.getEntityAttributePK();
            var unitOfMeasureTypePK = entityAttributeNumericValue.getUnitOfMeasureTypePK();
            
            entityAttributeNumeric = entityAttributeNumericFactory.create(entityAttributePK, unitOfMeasureTypePK,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(entityAttributePK, EventTypes.MODIFY, entityAttributeNumeric.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityAttributeNumeric(EntityAttributeNumeric entityAttributeNumeric, BasePK deletedBy) {
        entityAttributeNumeric.setThruTime(session.getStartTime());
        
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

    @Inject
    protected EntityAttributeListItemFactory entityAttributeListItemFactory;

    public EntityAttributeListItem createEntityAttributeListItem(EntityAttribute entityAttribute, Sequence entityListItemSequence,
            BasePK createdBy) {
        var entityAttributeListItem = entityAttributeListItemFactory.create(
                entityAttribute, entityListItemSequence, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeListItem.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return entityAttributeListItem;
    }
    
    public long countEntityAttributeListItemsByEntityListItemSequence(Sequence entityListItemSequence) {
        return session.getDslContext()
                .selectCount()
                .from(EntityAttributeListItems)
                .where(EntityAttributeListItems.ENTITY_LIST_ITEM_SEQUENCE.eq(entityListItemSequence.getPrimaryKey()),
                        EntityAttributeListItems.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private EntityAttributeListItem getEntityAttributeListItem(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EntityAttributeListItems.fields())
                .from(EntityAttributeListItems)
                .where(EntityAttributeListItems.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityAttributeListItems.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return entityAttributeListItemFactory.getEntityFromQuery(entityPermission, query);
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
            var entityAttributeListItem = entityAttributeListItemFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     entityAttributeListItemValue.getPrimaryKey());
            
            entityAttributeListItem.setThruTime(session.getStartTime());
            entityAttributeListItem.store();

            var entityAttributePK = entityAttributeListItem.getEntityAttributePK();
            var entityListItemSequencePK = entityAttributeListItemValue.getEntityListItemSequencePK();
            
            entityAttributeListItem = entityAttributeListItemFactory.create(entityAttributePK, entityListItemSequencePK,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(entityAttributePK, EventTypes.MODIFY, entityAttributeListItem.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityAttributeListItem(EntityAttributeListItem entityAttributeListItem, BasePK deletedBy) {
        entityAttributeListItem.setThruTime(session.getStartTime());
        
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

    @Inject
    protected EntityAttributeWorkflowFactory entityAttributeWorkflowFactory;

    public EntityAttributeWorkflow createEntityAttributeWorkflow(EntityAttribute entityAttribute, Workflow workflow,
            BasePK createdBy) {
        var entityAttributeWorkflow = entityAttributeWorkflowFactory.create(
                entityAttribute, workflow, session.getStartTime(), Session.MAX_TIME);

        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeWorkflow.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return entityAttributeWorkflow;
    }

    public long countEntityAttributeWorkflowsByWorkflow(Workflow workflow) {
        return session.getDslContext()
                .selectCount()
                .from(EntityAttributeWorkflows)
                .where(EntityAttributeWorkflows.WORKFLOW.eq(workflow.getPrimaryKey()),
                        EntityAttributeWorkflows.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private EntityAttributeWorkflow getEntityAttributeWorkflow(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EntityAttributeWorkflows.fields())
                .from(EntityAttributeWorkflows)
                .where(EntityAttributeWorkflows.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityAttributeWorkflows.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return entityAttributeWorkflowFactory.getEntityFromQuery(entityPermission, query);
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
            var entityAttributeWorkflow = entityAttributeWorkflowFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    entityAttributeWorkflowValue.getPrimaryKey());

            entityAttributeWorkflow.setThruTime(session.getStartTime());
            entityAttributeWorkflow.store();

            var entityAttributePK = entityAttributeWorkflow.getEntityAttributePK();
            var workflowPK = entityAttributeWorkflowValue.getWorkflowPK();

            entityAttributeWorkflow = entityAttributeWorkflowFactory.create(entityAttributePK, workflowPK,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(entityAttributePK, EventTypes.MODIFY, entityAttributeWorkflow.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteEntityAttributeWorkflow(EntityAttributeWorkflow entityAttributeWorkflow, BasePK deletedBy) {
        entityAttributeWorkflow.setThruTime(session.getStartTime());

        sendEvent(entityAttributeWorkflow.getEntityAttributePK(), EventTypes.MODIFY, entityAttributeWorkflow.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteEntityAttributeWorkflowByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        var entityAttributeWorkflow = getEntityAttributeWorkflowForUpdate(entityAttribute);

        if(entityAttributeWorkflow != null) {
            deleteEntityAttributeWorkflow(entityAttributeWorkflow, deletedBy);
        }
    }

    // --------------------------------------------------------------------------------
    //   Entity List Items
    // --------------------------------------------------------------------------------

    @Inject
    protected EntityListItemDetailFactory entityListItemDetailFactory;

    @Inject
    protected EntityListItemFactory entityListItemFactory;

    @Inject
    EntityListItemTransferCache entityListItemTransferCache;

    public EntityListItem createEntityListItem(EntityAttribute entityAttribute, String entityListItemName, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var defaultEntityListItem = getDefaultEntityListItem(entityAttribute);
        var defaultFound = defaultEntityListItem != null;
        
        if(defaultFound && isDefault) {
            var defaultEntityListItemDetailValue = getDefaultEntityListItemDetailValueForUpdate(entityAttribute);
            
            defaultEntityListItemDetailValue.setIsDefault(false);
            updateEntityListItemFromValue(defaultEntityListItemDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var entityListItem = entityListItemFactory.create();
        var entityListItemDetail = entityListItemDetailFactory.create(entityListItem,
                entityAttribute, entityListItemName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        entityListItem = entityListItemFactory.getEntityFromPK(EntityPermission.READ_WRITE, entityListItem.getPrimaryKey());
        entityListItem.setActiveDetail(entityListItemDetail);
        entityListItem.setLastDetail(entityListItemDetail);
        entityListItem.store();
        
        sendEvent(entityListItem.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return entityListItem;
    }
    
    /** Assume that the entityInstance passed to this function is a ECHO_THREE.EntityListItem */
    public EntityListItem getEntityListItemByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new EntityListItemPK(entityInstance.getEntityUniqueId());

        return entityListItemFactory.getEntityFromPK(entityPermission, pk);
    }

    public EntityListItem getEntityListItemByEntityInstance(EntityInstance entityInstance) {
        return getEntityListItemByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public EntityListItem getEntityListItemByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getEntityListItemByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    public EntityListItem getEntityListItemByPK(EntityListItemPK pk) {
        return entityListItemFactory.getEntityFromPK(EntityPermission.READ_ONLY, pk);
    }
    
    public long countEntityListItems() {
        return session.getDslContext()
                .selectCount()
                .from(EntityListItems)
                .join(EntityListItemDetails).onKey(ENTITY_LIST_ITEMS_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private EntityListItem getDefaultEntityListItem(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EntityListItems.fields())
                .from(EntityListItems)
                .join(EntityListItemDetails).onKey(ENTITY_LIST_ITEMS_ACTIVE_DETAIL_FK)
                .where(EntityListItemDetails.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityListItemDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return entityListItemFactory.getEntityFromQuery(entityPermission, query);
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
        var baseQuery = session.getDslContext()
                .select(EntityListItems.fields())
                .from(EntityListItems)
                .join(EntityListItemDetails).onKey(ENTITY_LIST_ITEMS_ACTIVE_DETAIL_FK)
                .where(EntityListItemDetails.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityListItemDetails.ENTITY_LIST_ITEM_NAME.eq(entityListItemName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return entityListItemFactory.getEntityFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(EntityListItems.fields())
                    .from(EntityListItems)
                    .join(EntityListItemDetails).onKey(ENTITY_LIST_ITEMS_ACTIVE_DETAIL_FK)
                    .where(EntityListItemDetails.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()))
                    .orderBy(EntityListItemDetails.SORT_ORDER, EntityListItemDetails.ENTITY_LIST_ITEM_NAME),
                    EntityListItemFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(EntityListItems.fields())
                    .from(EntityListItems)
                    .join(EntityListItemDetails).onKey(ENTITY_LIST_ITEMS_ACTIVE_DETAIL_FK)
                    .where(EntityListItemDetails.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()))
                    .forUpdate();
        };

        return entityListItemFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<EntityListItem> getEntityListItems(EntityAttribute entityAttribute) {
        return getEntityListItems(entityAttribute, EntityPermission.READ_ONLY);
    }

    public List<EntityListItem> getEntityListItemsForUpdate(EntityAttribute entityAttribute) {
        return getEntityListItems(entityAttribute, EntityPermission.READ_WRITE);
    }

    private List<EntityListItem> getEntityListItemsByName(String entityListItemName, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(EntityListItems.fields())
                    .from(EntityListItems)
                    .join(EntityListItemDetails).onKey(ENTITY_LIST_ITEMS_ACTIVE_DETAIL_FK)
                    .where(EntityListItemDetails.ENTITY_LIST_ITEM_NAME.eq(entityListItemName))
                    .orderBy(EntityListItemDetails.SORT_ORDER, EntityListItemDetails.ENTITY_LIST_ITEM_NAME),
                    EntityListItemFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(EntityListItems.fields())
                    .from(EntityListItems)
                    .join(EntityListItemDetails).onKey(ENTITY_LIST_ITEMS_ACTIVE_DETAIL_FK)
                    .where(EntityListItemDetails.ENTITY_LIST_ITEM_NAME.eq(entityListItemName))
                    .forUpdate();
        };

        return entityListItemFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<EntityListItem> getEntityListItemsByName(String entityListItemName) {
        return getEntityListItemsByName(entityListItemName, EntityPermission.READ_ONLY);
    }

    public List<EntityListItem> getEntityListItemsByNameForUpdate(String entityListItemName) {
        return getEntityListItemsByName(entityListItemName, EntityPermission.READ_WRITE);
    }

    public long countEntityListItems(EntityAttribute entityAttribute) {
        return session.getDslContext()
                .selectCount()
                .from(EntityListItems)
                .join(EntityListItemDetails).onKey(ENTITY_LIST_ITEMS_ACTIVE_DETAIL_FK)
                .where(EntityListItemDetails.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public EntityListItemTransfer getEntityListItemTransfer(UserVisit userVisit, EntityListItem entityListItem, EntityInstance entityInstance) {
        return entityListItemTransferCache.getEntityListItemTransfer(userVisit, entityListItem, entityInstance);
    }
    
    public List<EntityListItemTransfer> getEntityListItemTransfers(UserVisit userVisit, Collection<EntityListItem> entityListItems, EntityInstance entityInstance) {
        List<EntityListItemTransfer> entityListItemTransfers = new ArrayList<>(entityListItems.size());

        entityListItems.forEach((entityListItem) ->
                entityListItemTransfers.add(entityListItemTransferCache.getEntityListItemTransfer(userVisit, entityListItem, entityInstance))
        );

        return entityListItemTransfers;
    }

    public List<EntityListItemTransfer> getEntityListItemTransfersByEntityAttribute(UserVisit userVisit, EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityListItemTransfers(userVisit, getEntityListItems(entityAttribute), entityInstance);
    }

    private void updateEntityListItemFromValue(EntityListItemDetailValue entityListItemDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(entityListItemDetailValue.hasBeenModified()) {
            var entityListItem = entityListItemFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     entityListItemDetailValue.getEntityListItemPK());
            var entityListItemDetail = entityListItem.getActiveDetailForUpdate();
            
            entityListItemDetail.setThruTime(session.getStartTime());
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
                    
                    defaultEntityListItemDetailValue.setIsDefault(false);
                    updateEntityListItemFromValue(defaultEntityListItemDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            entityListItemDetail = entityListItemDetailFactory.create(entityListItemPK,
                    entityAttribute.getPrimaryKey(), entityListItemName, isDefault, sortOrder, session.getStartTime(),
                    Session.MAX_TIME);
            
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
        
        if(entityAttributeTypeName.equals(com.echothree.model.control.core.common.EntityAttributeTypes.LISTITEM.name())) {
            deleteEntityListItemDefaultByEntityListItem(entityListItem, deletedBy);
            deleteEntityListItemAttributesByEntityListItem(entityListItem, deletedBy);
        } else if(entityAttributeTypeName.equals(com.echothree.model.control.core.common.EntityAttributeTypes.MULTIPLELISTITEM.name())) {
            deleteEntityMultipleListItemDefaultsByEntityListItem(entityListItem, deletedBy);
            deleteEntityMultipleListItemAttributesByEntityListItem(entityListItem, deletedBy);
        }
        
        deleteEntityListItemDescriptionsByEntityListItem(entityListItem, deletedBy);
        
        entityListItemDetail.setThruTime(session.getStartTime());
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

                    entityListItemDetailValue.setIsDefault(true);
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

    @Inject
    protected EntityListItemDescriptionFactory entityListItemDescriptionFactory;

    @Inject
    EntityListItemDescriptionTransferCache entityListItemDescriptionTransferCache;

    public EntityListItemDescription createEntityListItemDescription(EntityListItem entityListItem, Language language, String description, BasePK createdBy) {
        var entityListItemDescription = entityListItemDescriptionFactory.create(entityListItem, language, description, session.getStartTime(),
                Session.MAX_TIME);
        
        sendEvent(entityListItem.getPrimaryKey(), EventTypes.MODIFY, entityListItemDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return entityListItemDescription;
    }
    
    private EntityListItemDescription getEntityListItemDescription(EntityListItem entityListItem, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EntityListItemDescriptions.fields())
                .from(EntityListItemDescriptions)
                .where(EntityListItemDescriptions.ENTITY_LIST_ITEM.eq(entityListItem.getPrimaryKey()),
                        EntityListItemDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        EntityListItemDescriptions.THRU_TIME.eq(Session.MAX_TIME));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };
            
        return entityListItemDescriptionFactory.getEntityFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(EntityListItemDescriptions.fields())
                    .from(EntityListItemDescriptions)
                    .join(Languages).onKey(ENTITY_LIST_ITEM_DESCRIPTIONS_LANGUAGE_FK)
                    .where(EntityListItemDescriptions.ENTITY_LIST_ITEM.eq(entityListItem.getPrimaryKey()),
                            EntityListItemDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    EntityListItemDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(EntityListItemDescriptions.fields())
                    .from(EntityListItemDescriptions)
                    .where(EntityListItemDescriptions.ENTITY_LIST_ITEM.eq(entityListItem.getPrimaryKey()),
                            EntityListItemDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return entityListItemDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
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
            entityListItemDescription = getEntityListItemDescription(entityListItem, partyControl.getDefaultLanguage());
        }
        
        if(entityListItemDescription == null) {
            description = entityListItem.getLastDetail().getEntityListItemName();
        } else {
            description = entityListItemDescription.getDescription();
        }
        
        return description;
    }
    
    public EntityListItemDescriptionTransfer getEntityListItemDescriptionTransfer(UserVisit userVisit, EntityListItemDescription entityListItemDescription, EntityInstance entityInstance) {
        return entityListItemDescriptionTransferCache.getEntityListItemDescriptionTransfer(userVisit, entityListItemDescription, entityInstance);
    }
    
    public List<EntityListItemDescriptionTransfer> getEntityListItemDescriptionTransfersByEntityListItem(UserVisit userVisit, EntityListItem entityListItem, EntityInstance entityInstance) {
        var entityListItemDescriptions = getEntityListItemDescriptionsByEntityListItem(entityListItem);
        List<EntityListItemDescriptionTransfer> entityListItemDescriptionTransfers = new ArrayList<>(entityListItemDescriptions.size());
        
        entityListItemDescriptions.forEach((entityListItemDescription) ->
                entityListItemDescriptionTransfers.add(entityListItemDescriptionTransferCache.getEntityListItemDescriptionTransfer(userVisit, entityListItemDescription, entityInstance))
        );
        
        return entityListItemDescriptionTransfers;
    }
    
    public void updateEntityListItemDescriptionFromValue(EntityListItemDescriptionValue entityListItemDescriptionValue, BasePK updatedBy) {
        if(entityListItemDescriptionValue.hasBeenModified()) {
            var entityListItemDescription = entityListItemDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE, entityListItemDescriptionValue.getPrimaryKey());
            
            entityListItemDescription.setThruTime(session.getStartTime());
            entityListItemDescription.store();

            var entityListItem = entityListItemDescription.getEntityListItem();
            var language = entityListItemDescription.getLanguage();
            var description = entityListItemDescriptionValue.getDescription();
            
            entityListItemDescription = entityListItemDescriptionFactory.create(entityListItem, language,
                    description, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(entityListItem.getPrimaryKey(), EventTypes.MODIFY, entityListItemDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityListItemDescription(EntityListItemDescription entityListItemDescription, BasePK deletedBy) {
        entityListItemDescription.setThruTime(session.getStartTime());
        
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

    @Inject
    protected EntityIntegerRangeDetailFactory entityIntegerRangeDetailFactory;

    @Inject
    protected EntityIntegerRangeFactory entityIntegerRangeFactory;

    @Inject
    EntityIntegerRangeTransferCache entityIntegerRangeTransferCache;

    public EntityIntegerRange createEntityIntegerRange(EntityAttribute entityAttribute, String entityIntegerRangeName, Integer minimumIntegerValue,
            Integer maximumIntegerValue, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultEntityIntegerRange = getDefaultEntityIntegerRange(entityAttribute);
        var defaultFound = defaultEntityIntegerRange != null;
        
        if(defaultFound && isDefault) {
            var defaultEntityIntegerRangeDetailValue = getDefaultEntityIntegerRangeDetailValueForUpdate(entityAttribute);
            
            defaultEntityIntegerRangeDetailValue.setIsDefault(false);
            updateEntityIntegerRangeFromValue(defaultEntityIntegerRangeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var entityIntegerRange = entityIntegerRangeFactory.create();
        var entityIntegerRangeDetail = entityIntegerRangeDetailFactory.create(entityIntegerRange, entityAttribute,
                entityIntegerRangeName, minimumIntegerValue, maximumIntegerValue, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        entityIntegerRange = entityIntegerRangeFactory.getEntityFromPK(EntityPermission.READ_WRITE, entityIntegerRange.getPrimaryKey());
        entityIntegerRange.setActiveDetail(entityIntegerRangeDetail);
        entityIntegerRange.setLastDetail(entityIntegerRangeDetail);
        entityIntegerRange.store();
        
        sendEvent(entityIntegerRange.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return entityIntegerRange;
    }
    
    /** Assume that the entityInstance passed to this function is a ECHO_THREE.EntityIntegerRange */
    public EntityIntegerRange getEntityIntegerRangeByEntityInstance(EntityInstance entityInstance) {
        var pk = new EntityIntegerRangePK(entityInstance.getEntityUniqueId());
        var entityIntegerRange = entityIntegerRangeFactory.getEntityFromPK(EntityPermission.READ_ONLY, pk);
        
        return entityIntegerRange;
    }
    
    private EntityIntegerRange getDefaultEntityIntegerRange(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EntityIntegerRanges.fields())
                .from(EntityIntegerRanges)
                .join(EntityIntegerRangeDetails).onKey(ENTITY_INTEGER_RANGES_ACTIVE_DETAIL_FK)
                .where(EntityIntegerRangeDetails.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityIntegerRangeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return entityIntegerRangeFactory.getEntityFromQuery(entityPermission, query);
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
        var baseQuery = session.getDslContext()
                .select(EntityIntegerRanges.fields())
                .from(EntityIntegerRanges)
                .join(EntityIntegerRangeDetails).onKey(ENTITY_INTEGER_RANGES_ACTIVE_DETAIL_FK)
                .where(EntityIntegerRangeDetails.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityIntegerRangeDetails.ENTITY_INTEGER_RANGE_NAME.eq(entityIntegerRangeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return entityIntegerRangeFactory.getEntityFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(EntityIntegerRanges.fields())
                    .from(EntityIntegerRanges)
                    .join(EntityIntegerRangeDetails).onKey(ENTITY_INTEGER_RANGES_ACTIVE_DETAIL_FK)
                    .where(EntityIntegerRangeDetails.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()))
                    .orderBy(EntityIntegerRangeDetails.SORT_ORDER, EntityIntegerRangeDetails.ENTITY_INTEGER_RANGE_NAME),
                    EntityIntegerRangeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(EntityIntegerRanges.fields())
                    .from(EntityIntegerRanges)
                    .join(EntityIntegerRangeDetails).onKey(ENTITY_INTEGER_RANGES_ACTIVE_DETAIL_FK)
                    .where(EntityIntegerRangeDetails.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()))
                    .forUpdate();
        };

        return entityIntegerRangeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<EntityIntegerRange> getEntityIntegerRanges(EntityAttribute entityAttribute) {
        return getEntityIntegerRanges(entityAttribute, EntityPermission.READ_ONLY);
    }
    
    public List<EntityIntegerRange> getEntityIntegerRangesForUpdate(EntityAttribute entityAttribute) {
        return getEntityIntegerRanges(entityAttribute, EntityPermission.READ_WRITE);
    }

    public long countEntityIntegerRanges(EntityAttribute entityAttribute) {
        return session.getDslContext()
                .selectCount()
                .from(EntityIntegerRanges)
                .join(EntityIntegerRangeDetails).onKey(ENTITY_INTEGER_RANGES_ACTIVE_DETAIL_FK)
                .where(EntityIntegerRangeDetails.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public EntityIntegerRangeTransfer getEntityIntegerRangeTransfer(UserVisit userVisit, EntityIntegerRange entityIntegerRange, EntityInstance entityInstance) {
        return entityIntegerRangeTransferCache.getEntityIntegerRangeTransfer(userVisit, entityIntegerRange, entityInstance);
    }
    
    public List<EntityIntegerRangeTransfer> getEntityIntegerRangeTransfers(UserVisit userVisit, Collection<EntityIntegerRange> entityIntegerRanges, EntityInstance entityInstance) {
        List<EntityIntegerRangeTransfer> entityIntegerRangeTransfers = new ArrayList<>(entityIntegerRanges.size());

        entityIntegerRanges.forEach((entityIntegerRange) ->
                entityIntegerRangeTransfers.add(entityIntegerRangeTransferCache.getEntityIntegerRangeTransfer(userVisit, entityIntegerRange, entityInstance))
        );

        return entityIntegerRangeTransfers;
    }

    public List<EntityIntegerRangeTransfer> getEntityIntegerRangeTransfersByEntityAttribute(UserVisit userVisit, EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityIntegerRangeTransfers(userVisit, getEntityIntegerRanges(entityAttribute), entityInstance);
    }

    private void updateEntityIntegerRangeFromValue(EntityIntegerRangeDetailValue entityIntegerRangeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(entityIntegerRangeDetailValue.hasBeenModified()) {
            var entityIntegerRange = entityIntegerRangeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     entityIntegerRangeDetailValue.getEntityIntegerRangePK());
            var entityIntegerRangeDetail = entityIntegerRange.getActiveDetailForUpdate();
            
            entityIntegerRangeDetail.setThruTime(session.getStartTime());
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
                    
                    defaultEntityIntegerRangeDetailValue.setIsDefault(false);
                    updateEntityIntegerRangeFromValue(defaultEntityIntegerRangeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            entityIntegerRangeDetail = entityIntegerRangeDetailFactory.create(entityIntegerRangePK, entityAttribute.getPrimaryKey(), entityIntegerRangeName,
                    minimumIntegerValue, maximumIntegerValue, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
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
        
        entityIntegerRangeDetail.setThruTime(session.getStartTime());
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

                    entityIntegerRangeDetailValue.setIsDefault(true);
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

    @Inject
    protected EntityIntegerRangeDescriptionFactory entityIntegerRangeDescriptionFactory;

    @Inject
    EntityIntegerRangeDescriptionTransferCache entityIntegerRangeDescriptionTransferCache;

    public EntityIntegerRangeDescription createEntityIntegerRangeDescription(EntityIntegerRange entityIntegerRange, Language language, String description, BasePK createdBy) {
        var entityIntegerRangeDescription = entityIntegerRangeDescriptionFactory.create(entityIntegerRange, language, description, session.getStartTime(),
                Session.MAX_TIME);
        
        sendEvent(entityIntegerRange.getPrimaryKey(), EventTypes.MODIFY, entityIntegerRangeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return entityIntegerRangeDescription;
    }
    
    private EntityIntegerRangeDescription getEntityIntegerRangeDescription(EntityIntegerRange entityIntegerRange, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EntityIntegerRangeDescriptions.fields())
                .from(EntityIntegerRangeDescriptions)
                .where(EntityIntegerRangeDescriptions.ENTITY_INTEGER_RANGE.eq(entityIntegerRange.getPrimaryKey()),
                        EntityIntegerRangeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        EntityIntegerRangeDescriptions.THRU_TIME.eq(Session.MAX_TIME));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };
            
        return entityIntegerRangeDescriptionFactory.getEntityFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(EntityIntegerRangeDescriptions.fields())
                    .from(EntityIntegerRangeDescriptions)
                    .join(Languages).onKey(ENTITY_INTEGER_RANGE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(EntityIntegerRangeDescriptions.ENTITY_INTEGER_RANGE.eq(entityIntegerRange.getPrimaryKey()),
                            EntityIntegerRangeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    EntityIntegerRangeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(EntityIntegerRangeDescriptions.fields())
                    .from(EntityIntegerRangeDescriptions)
                    .where(EntityIntegerRangeDescriptions.ENTITY_INTEGER_RANGE.eq(entityIntegerRange.getPrimaryKey()),
                            EntityIntegerRangeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return entityIntegerRangeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
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
            entityIntegerRangeDescription = getEntityIntegerRangeDescription(entityIntegerRange, partyControl.getDefaultLanguage());
        }
        
        if(entityIntegerRangeDescription == null) {
            description = entityIntegerRange.getLastDetail().getEntityIntegerRangeName();
        } else {
            description = entityIntegerRangeDescription.getDescription();
        }
        
        return description;
    }
    
    public EntityIntegerRangeDescriptionTransfer getEntityIntegerRangeDescriptionTransfer(UserVisit userVisit, EntityIntegerRangeDescription entityIntegerRangeDescription, EntityInstance entityInstance) {
        return entityIntegerRangeDescriptionTransferCache.getEntityIntegerRangeDescriptionTransfer(userVisit, entityIntegerRangeDescription, entityInstance);
    }
    
    public List<EntityIntegerRangeDescriptionTransfer> getEntityIntegerRangeDescriptionTransfersByEntityIntegerRange(UserVisit userVisit, EntityIntegerRange entityIntegerRange, EntityInstance entityInstance) {
        var entityIntegerRangeDescriptions = getEntityIntegerRangeDescriptionsByEntityIntegerRange(entityIntegerRange);
        List<EntityIntegerRangeDescriptionTransfer> entityIntegerRangeDescriptionTransfers = new ArrayList<>(entityIntegerRangeDescriptions.size());
        
        entityIntegerRangeDescriptions.forEach((entityIntegerRangeDescription) ->
                entityIntegerRangeDescriptionTransfers.add(entityIntegerRangeDescriptionTransferCache.getEntityIntegerRangeDescriptionTransfer(userVisit, entityIntegerRangeDescription, entityInstance))
        );
        
        return entityIntegerRangeDescriptionTransfers;
    }
    
    public void updateEntityIntegerRangeDescriptionFromValue(EntityIntegerRangeDescriptionValue entityIntegerRangeDescriptionValue, BasePK updatedBy) {
        if(entityIntegerRangeDescriptionValue.hasBeenModified()) {
            var entityIntegerRangeDescription = entityIntegerRangeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE, entityIntegerRangeDescriptionValue.getPrimaryKey());
            
            entityIntegerRangeDescription.setThruTime(session.getStartTime());
            entityIntegerRangeDescription.store();

            var entityIntegerRange = entityIntegerRangeDescription.getEntityIntegerRange();
            var language = entityIntegerRangeDescription.getLanguage();
            var description = entityIntegerRangeDescriptionValue.getDescription();
            
            entityIntegerRangeDescription = entityIntegerRangeDescriptionFactory.create(entityIntegerRange, language,
                    description, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(entityIntegerRange.getPrimaryKey(), EventTypes.MODIFY, entityIntegerRangeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityIntegerRangeDescription(EntityIntegerRangeDescription entityIntegerRangeDescription, BasePK deletedBy) {
        entityIntegerRangeDescription.setThruTime(session.getStartTime());
        
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

    @Inject
    protected EntityLongRangeDetailFactory entityLongRangeDetailFactory;

    @Inject
    protected EntityLongRangeFactory entityLongRangeFactory;

    @Inject
    EntityLongRangeTransferCache entityLongRangeTransferCache;

    public EntityLongRange createEntityLongRange(EntityAttribute entityAttribute, String entityLongRangeName, Long minimumLongValue, Long maximumLongValue,
            Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultEntityLongRange = getDefaultEntityLongRange(entityAttribute);
        var defaultFound = defaultEntityLongRange != null;
        
        if(defaultFound && isDefault) {
            var defaultEntityLongRangeDetailValue = getDefaultEntityLongRangeDetailValueForUpdate(entityAttribute);
            
            defaultEntityLongRangeDetailValue.setIsDefault(false);
            updateEntityLongRangeFromValue(defaultEntityLongRangeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var entityLongRange = entityLongRangeFactory.create();
        var entityLongRangeDetail = entityLongRangeDetailFactory.create(entityLongRange, entityAttribute, entityLongRangeName,
                minimumLongValue, maximumLongValue, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        entityLongRange = entityLongRangeFactory.getEntityFromPK(EntityPermission.READ_WRITE, entityLongRange.getPrimaryKey());
        entityLongRange.setActiveDetail(entityLongRangeDetail);
        entityLongRange.setLastDetail(entityLongRangeDetail);
        entityLongRange.store();
        
        sendEvent(entityLongRange.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return entityLongRange;
    }
    
    /** Assume that the entityInstance passed to this function is a ECHO_THREE.EntityLongRange */
    public EntityLongRange getEntityLongRangeByEntityInstance(EntityInstance entityInstance) {
        var pk = new EntityLongRangePK(entityInstance.getEntityUniqueId());
        var entityLongRange = entityLongRangeFactory.getEntityFromPK(EntityPermission.READ_ONLY, pk);
        
        return entityLongRange;
    }
    
    private EntityLongRange getDefaultEntityLongRange(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EntityLongRanges.fields())
                .from(EntityLongRanges)
                .join(EntityLongRangeDetails).onKey(ENTITY_LONG_RANGES_ACTIVE_DETAIL_FK)
                .where(EntityLongRangeDetails.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityLongRangeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return entityLongRangeFactory.getEntityFromQuery(entityPermission, query);
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
        var baseQuery = session.getDslContext()
                .select(EntityLongRanges.fields())
                .from(EntityLongRanges)
                .join(EntityLongRangeDetails).onKey(ENTITY_LONG_RANGES_ACTIVE_DETAIL_FK)
                .where(EntityLongRangeDetails.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityLongRangeDetails.ENTITY_LONG_RANGE_NAME.eq(entityLongRangeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return entityLongRangeFactory.getEntityFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(EntityLongRanges.fields())
                    .from(EntityLongRanges)
                    .join(EntityLongRangeDetails).onKey(ENTITY_LONG_RANGES_ACTIVE_DETAIL_FK)
                    .where(EntityLongRangeDetails.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()))
                    .orderBy(EntityLongRangeDetails.SORT_ORDER, EntityLongRangeDetails.ENTITY_LONG_RANGE_NAME),
                    EntityLongRangeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(EntityLongRanges.fields())
                    .from(EntityLongRanges)
                    .join(EntityLongRangeDetails).onKey(ENTITY_LONG_RANGES_ACTIVE_DETAIL_FK)
                    .where(EntityLongRangeDetails.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()))
                    .forUpdate();
        };

        return entityLongRangeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<EntityLongRange> getEntityLongRanges(EntityAttribute entityAttribute) {
        return getEntityLongRanges(entityAttribute, EntityPermission.READ_ONLY);
    }
    
    public List<EntityLongRange> getEntityLongRangesForUpdate(EntityAttribute entityAttribute) {
        return getEntityLongRanges(entityAttribute, EntityPermission.READ_WRITE);
    }

    public long countEntityLongRanges(EntityAttribute entityAttribute) {
        return session.getDslContext()
                .selectCount()
                .from(EntityLongRanges)
                .join(EntityLongRangeDetails).onKey(ENTITY_LONG_RANGES_ACTIVE_DETAIL_FK)
                .where(EntityLongRangeDetails.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public EntityLongRangeTransfer getEntityLongRangeTransfer(UserVisit userVisit, EntityLongRange entityLongRange, EntityInstance entityInstance) {
        return entityLongRangeTransferCache.getEntityLongRangeTransfer(userVisit, entityLongRange, entityInstance);
    }

    public List<EntityLongRangeTransfer> getEntityLongRangeTransfers(UserVisit userVisit, Collection<EntityLongRange> entityLongRanges, EntityInstance entityInstance) {
        List<EntityLongRangeTransfer> entityLongRangeTransfers = new ArrayList<>(entityLongRanges.size());

        entityLongRanges.forEach((entityLongRange) ->
                entityLongRangeTransfers.add(entityLongRangeTransferCache.getEntityLongRangeTransfer(userVisit, entityLongRange, entityInstance))
        );

        return entityLongRangeTransfers;
    }

    public List<EntityLongRangeTransfer> getEntityLongRangeTransfersByEntityAttribute(UserVisit userVisit, EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityLongRangeTransfers(userVisit, getEntityLongRanges(entityAttribute), entityInstance);
    }

    private void updateEntityLongRangeFromValue(EntityLongRangeDetailValue entityLongRangeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(entityLongRangeDetailValue.hasBeenModified()) {
            var entityLongRange = entityLongRangeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     entityLongRangeDetailValue.getEntityLongRangePK());
            var entityLongRangeDetail = entityLongRange.getActiveDetailForUpdate();
            
            entityLongRangeDetail.setThruTime(session.getStartTime());
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
                    
                    defaultEntityLongRangeDetailValue.setIsDefault(false);
                    updateEntityLongRangeFromValue(defaultEntityLongRangeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            entityLongRangeDetail = entityLongRangeDetailFactory.create(entityLongRangePK, entityAttribute.getPrimaryKey(), entityLongRangeName,
                    minimumLongValue, maximumLongValue, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
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
        
        entityLongRangeDetail.setThruTime(session.getStartTime());
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

                    entityLongRangeDetailValue.setIsDefault(true);
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

    @Inject
    protected EntityLongRangeDescriptionFactory entityLongRangeDescriptionFactory;

    @Inject
    EntityLongRangeDescriptionTransferCache entityLongRangeDescriptionTransferCache;

    public EntityLongRangeDescription createEntityLongRangeDescription(EntityLongRange entityLongRange, Language language, String description, BasePK createdBy) {
        var entityLongRangeDescription = entityLongRangeDescriptionFactory.create(entityLongRange, language, description, session.getStartTime(),
                Session.MAX_TIME);
        
        sendEvent(entityLongRange.getPrimaryKey(), EventTypes.MODIFY, entityLongRangeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return entityLongRangeDescription;
    }
    
    private EntityLongRangeDescription getEntityLongRangeDescription(EntityLongRange entityLongRange, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EntityLongRangeDescriptions.fields())
                .from(EntityLongRangeDescriptions)
                .where(EntityLongRangeDescriptions.ENTITY_LONG_RANGE.eq(entityLongRange.getPrimaryKey()),
                        EntityLongRangeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        EntityLongRangeDescriptions.THRU_TIME.eq(Session.MAX_TIME));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };
            
        return entityLongRangeDescriptionFactory.getEntityFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(EntityLongRangeDescriptions.fields())
                    .from(EntityLongRangeDescriptions)
                    .join(Languages).onKey(ENTITY_LONG_RANGE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(EntityLongRangeDescriptions.ENTITY_LONG_RANGE.eq(entityLongRange.getPrimaryKey()),
                            EntityLongRangeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    EntityLongRangeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(EntityLongRangeDescriptions.fields())
                    .from(EntityLongRangeDescriptions)
                    .where(EntityLongRangeDescriptions.ENTITY_LONG_RANGE.eq(entityLongRange.getPrimaryKey()),
                            EntityLongRangeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return entityLongRangeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
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
            entityLongRangeDescription = getEntityLongRangeDescription(entityLongRange, partyControl.getDefaultLanguage());
        }
        
        if(entityLongRangeDescription == null) {
            description = entityLongRange.getLastDetail().getEntityLongRangeName();
        } else {
            description = entityLongRangeDescription.getDescription();
        }
        
        return description;
    }
    
    public EntityLongRangeDescriptionTransfer getEntityLongRangeDescriptionTransfer(UserVisit userVisit, EntityLongRangeDescription entityLongRangeDescription, EntityInstance entityInstance) {
        return entityLongRangeDescriptionTransferCache.getEntityLongRangeDescriptionTransfer(userVisit, entityLongRangeDescription, entityInstance);
    }
    
    public List<EntityLongRangeDescriptionTransfer> getEntityLongRangeDescriptionTransfersByEntityLongRange(UserVisit userVisit, EntityLongRange entityLongRange, EntityInstance entityInstance) {
        var entityLongRangeDescriptions = getEntityLongRangeDescriptionsByEntityLongRange(entityLongRange);
        List<EntityLongRangeDescriptionTransfer> entityLongRangeDescriptionTransfers = new ArrayList<>(entityLongRangeDescriptions.size());
        
        entityLongRangeDescriptions.forEach((entityLongRangeDescription) ->
                entityLongRangeDescriptionTransfers.add(entityLongRangeDescriptionTransferCache.getEntityLongRangeDescriptionTransfer(userVisit, entityLongRangeDescription, entityInstance))
        );
        
        return entityLongRangeDescriptionTransfers;
    }
    
    public void updateEntityLongRangeDescriptionFromValue(EntityLongRangeDescriptionValue entityLongRangeDescriptionValue, BasePK updatedBy) {
        if(entityLongRangeDescriptionValue.hasBeenModified()) {
            var entityLongRangeDescription = entityLongRangeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE, entityLongRangeDescriptionValue.getPrimaryKey());
            
            entityLongRangeDescription.setThruTime(session.getStartTime());
            entityLongRangeDescription.store();

            var entityLongRange = entityLongRangeDescription.getEntityLongRange();
            var language = entityLongRangeDescription.getLanguage();
            var description = entityLongRangeDescriptionValue.getDescription();
            
            entityLongRangeDescription = entityLongRangeDescriptionFactory.create(entityLongRange, language,
                    description, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(entityLongRange.getPrimaryKey(), EventTypes.MODIFY, entityLongRangeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityLongRangeDescription(EntityLongRangeDescription entityLongRangeDescription, BasePK deletedBy) {
        entityLongRangeDescription.setThruTime(session.getStartTime());
        
        sendEvent(entityLongRangeDescription.getEntityLongRangePK(), EventTypes.MODIFY, entityLongRangeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteEntityLongRangeDescriptionsByEntityLongRange(EntityLongRange entityLongRange, BasePK deletedBy) {
        var entityLongRangeDescriptions = getEntityLongRangeDescriptionsByEntityLongRangeForUpdate(entityLongRange);
        
        entityLongRangeDescriptions.forEach((entityLongRangeDescription) -> 
                deleteEntityLongRangeDescription(entityLongRangeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Entity Boolean Defaults
    // --------------------------------------------------------------------------------

    @Inject
    protected EntityBooleanDefaultFactory entityBooleanDefaultFactory;

    @Inject
    EntityBooleanDefaultTransferCache entityBooleanDefaultTransferCache;

    public EntityBooleanDefault createEntityBooleanDefault(EntityAttribute entityAttribute, Boolean booleanAttribute,
            BasePK createdBy) {
        var entityBooleanDefault = entityBooleanDefaultFactory.create(entityAttribute,
                booleanAttribute, session.getStartTime(), Session.MAX_TIME);

        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityBooleanDefault.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return entityBooleanDefault;
    }

    public long countEntityBooleanDefaultHistory(EntityAttribute entityAttribute) {
        return session.getDslContext()
                .selectCount()
                .from(EntityBooleanDefaults)
                .where(EntityBooleanDefaults.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public List<EntityBooleanDefault> getEntityBooleanDefaultHistory(EntityAttribute entityAttribute) {
        var query = session.applyLimit(session.getDslContext()
                .select(EntityBooleanDefaults.fields())
                .from(EntityBooleanDefaults)
                .where(EntityBooleanDefaults.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()))
                .orderBy(EntityBooleanDefaults.THRU_TIME),
                EntityBooleanDefaultFactory.class);

        return entityBooleanDefaultFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }

    private EntityBooleanDefault getEntityBooleanDefault(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EntityBooleanDefaults.fields())
                .from(EntityBooleanDefaults)
                .where(EntityBooleanDefaults.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityBooleanDefaults.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return entityBooleanDefaultFactory.getEntityFromQuery(entityPermission, query);
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
        return entityBooleanDefaultTransferCache.getEntityBooleanDefaultTransfer(userVisit, entityBooleanDefault);
    }

    public void updateEntityBooleanDefaultFromValue(EntityBooleanDefaultValue entityBooleanDefaultValue, BasePK updatedBy) {
        if(entityBooleanDefaultValue.hasBeenModified()) {
            var entityBooleanDefault = entityBooleanDefaultFactory.getEntityFromValue(EntityPermission.READ_WRITE, entityBooleanDefaultValue);
            var entityAttribute = entityBooleanDefault.getEntityAttribute();

            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityBooleanDefault.setThruTime(session.getStartTime());
                entityBooleanDefault.store();
            } else {
                entityBooleanDefault.remove();
            }

            entityBooleanDefault = entityBooleanDefaultFactory.create(entityAttribute,
                    entityBooleanDefaultValue.getBooleanAttribute(), session.getStartTime(),
                    Session.MAX_TIME);

            sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityBooleanDefault.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteEntityBooleanDefault(EntityBooleanDefault entityBooleanDefault, BasePK deletedBy) {
        var entityAttribute = entityBooleanDefault.getEntityAttribute();

        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityBooleanDefault.setThruTime(session.getStartTime());
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

    @Inject
    protected EntityBooleanAttributeFactory entityBooleanAttributeFactory;

    @Inject
    EntityBooleanAttributeTransferCache entityBooleanAttributeTransferCache;

    public EntityBooleanAttribute createEntityBooleanAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Boolean booleanAttribute, BasePK createdBy) {
        return createEntityBooleanAttribute(entityAttribute.getPrimaryKey(), entityInstance, booleanAttribute,
                createdBy);
    }

    public EntityBooleanAttribute createEntityBooleanAttribute(EntityAttributePK entityAttribute, EntityInstance entityInstance,
            Boolean booleanAttribute, BasePK createdBy) {
        var entityBooleanAttribute = entityBooleanAttributeFactory.create(entityAttribute,
                entityInstance.getPrimaryKey(), booleanAttribute, session.getStartTime(), Session.MAX_TIME);

        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute, EventTypes.CREATE, createdBy);

        return entityBooleanAttribute;
    }

    public long countEntityBooleanAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return session.getDslContext()
                .selectCount()
                .from(EntityBooleanAttributes)
                .where(EntityBooleanAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityBooleanAttributes.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public List<EntityBooleanAttribute> getEntityBooleanAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        var query = session.applyLimit(session.getDslContext()
                .select(EntityBooleanAttributes.fields())
                .from(EntityBooleanAttributes)
                .where(EntityBooleanAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityBooleanAttributes.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()))
                .orderBy(EntityBooleanAttributes.THRU_TIME),
                EntityBooleanAttributeFactory.class);

        return entityBooleanAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    private EntityBooleanAttribute getEntityBooleanAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EntityBooleanAttributes.fields())
                .from(EntityBooleanAttributes)
                .where(EntityBooleanAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityBooleanAttributes.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                        EntityBooleanAttributes.THRU_TIME.eq(Session.MAX_TIME));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };
            
        return entityBooleanAttributeFactory.getEntityFromQuery(entityPermission, query);
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
        var query = session.getDslContext()
                .select(EntityBooleanAttributes.fields())
                .from(EntityBooleanAttributes)
                .where(EntityBooleanAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityBooleanAttributes.THRU_TIME.eq(Session.MAX_TIME))
                .forUpdate();
        
        return entityBooleanAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, query);
    }
    
    public List<EntityBooleanAttribute> getEntityBooleanAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        var query = session.getDslContext()
                .select(EntityBooleanAttributes.fields())
                .from(EntityBooleanAttributes)
                .where(EntityBooleanAttributes.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                        EntityBooleanAttributes.THRU_TIME.eq(Session.MAX_TIME))
                .forUpdate();
        
        return entityBooleanAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, query);
    }
    
    public EntityBooleanAttributeTransfer getEntityBooleanAttributeTransfer(UserVisit userVisit, EntityBooleanAttribute entityBooleanAttribute, EntityInstance entityInstance) {
        return entityBooleanAttributeTransferCache.getEntityBooleanAttributeTransfer(userVisit, entityBooleanAttribute, entityInstance);
    }
    
    public void updateEntityBooleanAttributeFromValue(EntityBooleanAttributeValue entityBooleanAttributeValue, BasePK updatedBy) {
        if(entityBooleanAttributeValue.hasBeenModified()) {
            var entityBooleanAttribute = entityBooleanAttributeFactory.getEntityFromValue(EntityPermission.READ_WRITE, entityBooleanAttributeValue);
            var entityAttribute = entityBooleanAttribute.getEntityAttribute();
            var entityInstance = entityBooleanAttribute.getEntityInstance();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityBooleanAttribute.setThruTime(session.getStartTime());
                entityBooleanAttribute.store();
            } else {
                entityBooleanAttribute.remove();
            }
            
            entityBooleanAttributeFactory.create(entityAttribute, entityInstance, entityBooleanAttributeValue.getBooleanAttribute(), session.getStartTime(),
                    Session.MAX_TIME);
            
            sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityBooleanAttribute(EntityBooleanAttribute entityBooleanAttribute, BasePK deletedBy) {
        var entityAttribute = entityBooleanAttribute.getEntityAttribute();
        var entityInstance = entityBooleanAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityBooleanAttribute.setThruTime(session.getStartTime());
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

    @Inject
    protected EntityDateDefaultFactory entityDateDefaultFactory;

    @Inject
    EntityDateDefaultTransferCache entityDateDefaultTransferCache;

    public EntityDateDefault createEntityDateDefault(EntityAttribute entityAttribute, Integer dateAttribute,
            BasePK createdBy) {
        var entityDateDefault = entityDateDefaultFactory.create(entityAttribute,
                dateAttribute, session.getStartTime(), Session.MAX_TIME);

        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityDateDefault.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return entityDateDefault;
    }

    public long countEntityDateDefaultHistory(EntityAttribute entityAttribute) {
        return session.getDslContext()
                .selectCount()
                .from(EntityDateDefaults)
                .where(EntityDateDefaults.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public List<EntityDateDefault> getEntityDateDefaultHistory(EntityAttribute entityAttribute) {
        var query = session.applyLimit(session.getDslContext()
                .select(EntityDateDefaults.fields())
                .from(EntityDateDefaults)
                .where(EntityDateDefaults.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()))
                .orderBy(EntityDateDefaults.THRU_TIME),
                EntityDateDefaultFactory.class);

        return entityDateDefaultFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }

    private EntityDateDefault getEntityDateDefault(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EntityDateDefaults.fields())
                .from(EntityDateDefaults)
                .where(EntityDateDefaults.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityDateDefaults.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return entityDateDefaultFactory.getEntityFromQuery(entityPermission, query);
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
        return entityDateDefaultTransferCache.getEntityDateDefaultTransfer(userVisit, entityDateDefault);
    }

    public void updateEntityDateDefaultFromValue(EntityDateDefaultValue entityDateDefaultValue, BasePK updatedBy) {
        if(entityDateDefaultValue.hasBeenModified()) {
            var entityDateDefault = entityDateDefaultFactory.getEntityFromValue(EntityPermission.READ_WRITE, entityDateDefaultValue);
            var entityAttribute = entityDateDefault.getEntityAttribute();

            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityDateDefault.setThruTime(session.getStartTime());
                entityDateDefault.store();
            } else {
                entityDateDefault.remove();
            }

            entityDateDefault = entityDateDefaultFactory.create(entityAttribute,
                    entityDateDefaultValue.getDateAttribute(), session.getStartTime(),
                    Session.MAX_TIME);

            sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityDateDefault.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteEntityDateDefault(EntityDateDefault entityDateDefault, BasePK deletedBy) {
        var entityAttribute = entityDateDefault.getEntityAttribute();

        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityDateDefault.setThruTime(session.getStartTime());
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

    @Inject
    protected EntityDateAttributeFactory entityDateAttributeFactory;

    @Inject
    EntityDateAttributeTransferCache entityDateAttributeTransferCache;

    public EntityDateAttribute createEntityDateAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Integer dateAttribute, BasePK createdBy) {
        return createEntityDateAttribute(entityAttribute.getPrimaryKey(), entityInstance, dateAttribute,
                createdBy);
    }

    public EntityDateAttribute createEntityDateAttribute(EntityAttributePK entityAttribute, EntityInstance entityInstance,
            Integer dateAttribute, BasePK createdBy) {
        var entityDateAttribute = entityDateAttributeFactory.create(entityAttribute,
                entityInstance.getPrimaryKey(), dateAttribute, session.getStartTime(), Session.MAX_TIME);

        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute, EventTypes.CREATE, createdBy);

        return entityDateAttribute;
    }

    public long countEntityDateAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return session.getDslContext()
                .selectCount()
                .from(EntityDateAttributes)
                .where(EntityDateAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityDateAttributes.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public List<EntityDateAttribute> getEntityDateAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        var query = session.applyLimit(session.getDslContext()
                .select(EntityDateAttributes.fields())
                .from(EntityDateAttributes)
                .where(EntityDateAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityDateAttributes.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()))
                .orderBy(EntityDateAttributes.THRU_TIME),
                EntityDateAttributeFactory.class);

        return entityDateAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }

    private EntityDateAttribute getEntityDateAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EntityDateAttributes.fields())
                .from(EntityDateAttributes)
                .where(EntityDateAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityDateAttributes.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                        EntityDateAttributes.THRU_TIME.eq(Session.MAX_TIME));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };
            
        return entityDateAttributeFactory.getEntityFromQuery(entityPermission, query);
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
        var query = session.getDslContext()
                .select(EntityDateAttributes.fields())
                .from(EntityDateAttributes)
                .where(EntityDateAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityDateAttributes.THRU_TIME.eq(Session.MAX_TIME))
                .forUpdate();
        
        return entityDateAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, query);
    }
    
    public List<EntityDateAttribute> getEntityDateAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        var query = session.getDslContext()
                .select(EntityDateAttributes.fields())
                .from(EntityDateAttributes)
                .where(EntityDateAttributes.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                        EntityDateAttributes.THRU_TIME.eq(Session.MAX_TIME))
                .forUpdate();
        
        return entityDateAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, query);
    }
    
    public EntityDateAttributeTransfer getEntityDateAttributeTransfer(UserVisit userVisit, EntityDateAttribute entityDateAttribute, EntityInstance entityInstance) {
        return entityDateAttributeTransferCache.getEntityDateAttributeTransfer(userVisit, entityDateAttribute, entityInstance);
    }
    
    public void updateEntityDateAttributeFromValue(EntityDateAttributeValue entityDateAttributeValue, BasePK updatedBy) {
        if(entityDateAttributeValue.hasBeenModified()) {
            var entityDateAttribute = entityDateAttributeFactory.getEntityFromValue(EntityPermission.READ_WRITE, entityDateAttributeValue);
            var entityAttribute = entityDateAttribute.getEntityAttribute();
            var entityInstance = entityDateAttribute.getEntityInstance();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityDateAttribute.setThruTime(session.getStartTime());
                entityDateAttribute.store();
            } else {
                entityDateAttribute.remove();
            }
            
            entityDateAttributeFactory.create(entityAttribute, entityInstance, entityDateAttributeValue.getDateAttribute(), session.getStartTime(),
                    Session.MAX_TIME);
            
            sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityDateAttribute(EntityDateAttribute entityDateAttribute, BasePK deletedBy) {
        var entityAttribute = entityDateAttribute.getEntityAttribute();
        var entityInstance = entityDateAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityDateAttribute.setThruTime(session.getStartTime());
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

    @Inject
    protected EntityIntegerDefaultFactory entityIntegerDefaultFactory;

    @Inject
    EntityIntegerDefaultTransferCache entityIntegerDefaultTransferCache;

    public EntityIntegerDefault createEntityIntegerDefault(EntityAttribute entityAttribute, Integer integerAttribute,
            BasePK createdBy) {
        var entityIntegerDefault = entityIntegerDefaultFactory.create(entityAttribute,
                integerAttribute, session.getStartTime(), Session.MAX_TIME);

        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityIntegerDefault.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return entityIntegerDefault;
    }

    public long countEntityIntegerDefaultHistory(EntityAttribute entityAttribute) {
        return session.getDslContext()
                .selectCount()
                .from(EntityIntegerDefaults)
                .where(EntityIntegerDefaults.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public List<EntityIntegerDefault> getEntityIntegerDefaultHistory(EntityAttribute entityAttribute) {
        var query = session.applyLimit(session.getDslContext()
                .select(EntityIntegerDefaults.fields())
                .from(EntityIntegerDefaults)
                .where(EntityIntegerDefaults.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()))
                .orderBy(EntityIntegerDefaults.THRU_TIME),
                EntityIntegerDefaultFactory.class);

        return entityIntegerDefaultFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }

    private EntityIntegerDefault getEntityIntegerDefault(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EntityIntegerDefaults.fields())
                .from(EntityIntegerDefaults)
                .where(EntityIntegerDefaults.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityIntegerDefaults.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return entityIntegerDefaultFactory.getEntityFromQuery(entityPermission, query);
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
        return entityIntegerDefaultTransferCache.getEntityIntegerDefaultTransfer(userVisit, entityIntegerDefault);
    }

    public void updateEntityIntegerDefaultFromValue(EntityIntegerDefaultValue entityIntegerDefaultValue, BasePK updatedBy) {
        if(entityIntegerDefaultValue.hasBeenModified()) {
            var entityIntegerDefault = entityIntegerDefaultFactory.getEntityFromValue(EntityPermission.READ_WRITE, entityIntegerDefaultValue);
            var entityAttribute = entityIntegerDefault.getEntityAttribute();

            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityIntegerDefault.setThruTime(session.getStartTime());
                entityIntegerDefault.store();
            } else {
                entityIntegerDefault.remove();
            }

            entityIntegerDefault = entityIntegerDefaultFactory.create(entityAttribute,
                    entityIntegerDefaultValue.getIntegerAttribute(), session.getStartTime(),
                    Session.MAX_TIME);

            sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityIntegerDefault.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteEntityIntegerDefault(EntityIntegerDefault entityIntegerDefault, BasePK deletedBy) {
        var entityAttribute = entityIntegerDefault.getEntityAttribute();

        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityIntegerDefault.setThruTime(session.getStartTime());
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

    @Inject
    protected EntityIntegerAttributeFactory entityIntegerAttributeFactory;

    @Inject
    EntityIntegerAttributeTransferCache entityIntegerAttributeTransferCache;

    public EntityIntegerAttribute createEntityIntegerAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Integer integerAttribute, BasePK createdBy) {
        return createEntityIntegerAttribute(entityAttribute.getPrimaryKey(), entityInstance, integerAttribute,
                createdBy);
    }

    public EntityIntegerAttribute createEntityIntegerAttribute(EntityAttributePK entityAttribute, EntityInstance entityInstance,
            Integer integerAttribute, BasePK createdBy) {
        var entityIntegerAttribute = entityIntegerAttributeFactory.create(entityAttribute,
                entityInstance.getPrimaryKey(), integerAttribute, session.getStartTime(), Session.MAX_TIME);

        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute, EventTypes.CREATE, createdBy);

        return entityIntegerAttribute;
    }
    
    public long countEntityIntegerAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return session.getDslContext()
                .selectCount()
                .from(EntityIntegerAttributes)
                .where(EntityIntegerAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityIntegerAttributes.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public List<EntityIntegerAttribute> getEntityIntegerAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        var query = session.applyLimit(session.getDslContext()
                .select(EntityIntegerAttributes.fields())
                .from(EntityIntegerAttributes)
                .where(EntityIntegerAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityIntegerAttributes.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()))
                .orderBy(EntityIntegerAttributes.THRU_TIME),
                EntityIntegerAttributeFactory.class);

        return entityIntegerAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }

    private EntityIntegerAttribute getEntityIntegerAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EntityIntegerAttributes.fields())
                .from(EntityIntegerAttributes)
                .where(EntityIntegerAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityIntegerAttributes.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                        EntityIntegerAttributes.THRU_TIME.eq(Session.MAX_TIME));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };
            
        return entityIntegerAttributeFactory.getEntityFromQuery(entityPermission, query);
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
        var query = session.getDslContext()
                .select(EntityIntegerAttributes.fields())
                .from(EntityIntegerAttributes)
                .where(EntityIntegerAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityIntegerAttributes.THRU_TIME.eq(Session.MAX_TIME))
                .forUpdate();
        
        return entityIntegerAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, query);
    }
    
    public List<EntityIntegerAttribute> getEntityIntegerAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        var query = session.getDslContext()
                .select(EntityIntegerAttributes.fields())
                .from(EntityIntegerAttributes)
                .where(EntityIntegerAttributes.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                        EntityIntegerAttributes.THRU_TIME.eq(Session.MAX_TIME))
                .forUpdate();
        
        return entityIntegerAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, query);
    }
    
    public EntityIntegerAttributeTransfer getEntityIntegerAttributeTransfer(UserVisit userVisit, EntityIntegerAttribute entityIntegerAttribute, EntityInstance entityInstance) {
        return entityIntegerAttributeTransferCache.getEntityIntegerAttributeTransfer(userVisit, entityIntegerAttribute, entityInstance);
    }
    
    public void updateEntityIntegerAttributeFromValue(EntityIntegerAttributeValue entityIntegerAttributeValue, BasePK updatedBy) {
        if(entityIntegerAttributeValue.hasBeenModified()) {
            var entityIntegerAttribute = entityIntegerAttributeFactory.getEntityFromValue(EntityPermission.READ_WRITE, entityIntegerAttributeValue);
            var entityAttribute = entityIntegerAttribute.getEntityAttribute();
            var entityInstance = entityIntegerAttribute.getEntityInstance();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityIntegerAttribute.setThruTime(session.getStartTime());
                entityIntegerAttribute.store();
            } else {
                entityIntegerAttribute.remove();
            }
            
            entityIntegerAttributeFactory.create(entityAttribute, entityInstance, entityIntegerAttributeValue.getIntegerAttribute(), session.getStartTime(),
                    Session.MAX_TIME);
            
            sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    public void deleteEntityIntegerAttribute(EntityIntegerAttribute entityIntegerAttribute, BasePK deletedBy) {
        var entityAttribute = entityIntegerAttribute.getEntityAttribute();
        var entityInstance = entityIntegerAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityIntegerAttribute.setThruTime(session.getStartTime());
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

    @Inject
    protected EntityListItemDefaultFactory entityListItemDefaultFactory;

    @Inject
    EntityListItemDefaultTransferCache entityListItemDefaultTransferCache;

    public EntityListItemDefault createEntityListItemDefault(EntityAttribute entityAttribute,
            EntityListItem entityListItem, BasePK createdBy) {
        var entityListItemDefault = entityListItemDefaultFactory.create(
                entityAttribute, entityListItem, session.getStartTime(), Session.MAX_TIME);

        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityListItemDefault.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return entityListItemDefault;
    }

    public long countEntityListItemDefaultHistory(EntityAttribute entityAttribute) {
        return session.getDslContext()
                .selectCount()
                .from(EntityListItemDefaults)
                .where(EntityListItemDefaults.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public List<EntityListItemDefault> getEntityListItemDefaultHistory(EntityAttribute entityAttribute) {
        var query = session.applyLimit(session.getDslContext()
                .select(EntityListItemDefaults.fields())
                .from(EntityListItemDefaults)
                .where(EntityListItemDefaults.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()))
                .orderBy(EntityListItemDefaults.THRU_TIME),
                EntityListItemDefaultFactory.class);

        return entityListItemDefaultFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }

    private EntityListItemDefault getEntityListItemDefault(EntityAttribute entityAttribute,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EntityListItemDefaults.fields())
                .from(EntityListItemDefaults)
                .where(EntityListItemDefaults.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityListItemDefaults.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return entityListItemDefaultFactory.getEntityFromQuery(entityPermission, query);
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
        var baseQuery = session.getDslContext()
                .select(EntityListItemDefaults.fields())
                .from(EntityListItemDefaults)
                .where(EntityListItemDefaults.ENTITY_LIST_ITEM.eq(entityListItem.getPrimaryKey()),
                        EntityListItemDefaults.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return entityListItemDefaultFactory.getEntityFromQuery(entityPermission, query);
    }

    public EntityListItemDefault getEntityListItemDefaultByEntityListItem(EntityListItem entityListItem) {
        return getEntityListItemDefaultByEntityListItem(entityListItem, EntityPermission.READ_ONLY);
    }

    public EntityListItemDefault getEntityListItemDefaultByEntityListItemForUpdate(EntityListItem entityListItem) {
        return getEntityListItemDefaultByEntityListItem(entityListItem, EntityPermission.READ_WRITE);
    }

    public EntityListItemDefaultTransfer getEntityListItemDefaultTransfer(UserVisit userVisit, EntityListItemDefault entityListItemDefault) {
        return entityListItemDefaultTransferCache.getEntityListItemDefaultTransfer(userVisit, entityListItemDefault);
    }

    public void updateEntityListItemDefaultFromValue(EntityListItemDefaultValue entityListItemDefaultValue, BasePK updatedBy) {
        if(entityListItemDefaultValue.hasBeenModified()) {
            var entityListItemDefault = entityListItemDefaultFactory.getEntityFromValue(EntityPermission.READ_WRITE, entityListItemDefaultValue);
            var entityAttribute = entityListItemDefault.getEntityAttribute();

            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityListItemDefault.setThruTime(session.getStartTime());
                entityListItemDefault.store();
            } else {
                entityListItemDefault.remove();
            }

            entityListItemDefault = entityListItemDefaultFactory.create(entityAttribute.getPrimaryKey(),
                    entityListItemDefaultValue.getEntityListItemPK(), session.getStartTime(), Session.MAX_TIME);

            sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityListItemDefault.getPrimaryKey(), EventTypes.DELETE, updatedBy);
        }
    }

    public void deleteEntityListItemDefault(EntityListItemDefault entityListItemDefault, BasePK deletedBy) {
        var entityAttribute = entityListItemDefault.getEntityAttribute();

        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityListItemDefault.setThruTime(session.getStartTime());
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

    @Inject
    protected EntityListItemAttributeFactory entityListItemAttributeFactory;

    @Inject
    EntityListItemAttributeTransferCache entityListItemAttributeTransferCache;

    public EntityListItemAttribute createEntityListItemAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityListItem entityListItem, BasePK createdBy) {
        return createEntityListItemAttribute(entityAttribute.getPrimaryKey(), entityInstance, entityListItem, createdBy);
    }

    public EntityListItemAttribute createEntityListItemAttribute(EntityAttributePK entityAttribute, EntityInstance entityInstance,
            EntityListItem entityListItem, BasePK createdBy) {
        var entityListItemAttribute = entityListItemAttributeFactory.create(entityAttribute,
                entityInstance.getPrimaryKey(), entityListItem.getPrimaryKey(), session.getStartTime(),
                Session.MAX_TIME);

        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute, EventTypes.CREATE, createdBy);

        return entityListItemAttribute;
    }

    public long countEntityListItemAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return session.getDslContext()
                .selectCount()
                .from(EntityListItemAttributes)
                .where(EntityListItemAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityListItemAttributes.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public List<EntityListItemAttribute> getEntityListItemAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        var query = session.applyLimit(session.getDslContext()
                .select(EntityListItemAttributes.fields())
                .from(EntityListItemAttributes)
                .where(EntityListItemAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityListItemAttributes.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()))
                .orderBy(EntityListItemAttributes.THRU_TIME),
                EntityListItemAttributeFactory.class);

        return entityListItemAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }

    private EntityListItemAttribute getEntityListItemAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EntityListItemAttributes.fields())
                .from(EntityListItemAttributes)
                .where(EntityListItemAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityListItemAttributes.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                        EntityListItemAttributes.THRU_TIME.eq(Session.MAX_TIME));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };
            
        return entityListItemAttributeFactory.getEntityFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(EntityListItemAttributes.fields())
                    .from(EntityListItemAttributes)
                    .join(EntityInstances).onKey(ENTITY_LIST_ITEM_ATTRIBUTES_ENTITY_INSTANCE_FK)
                    .where(EntityListItemAttributes.ENTITY_LIST_ITEM.eq(entityListItem.getPrimaryKey()),
                            EntityListItemAttributes.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(EntityInstances.ENTITY_UNIQUE),
                    EntityListItemAttributeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(EntityListItemAttributes.fields())
                    .from(EntityListItemAttributes)
                    .where(EntityListItemAttributes.ENTITY_LIST_ITEM.eq(entityListItem.getPrimaryKey()),
                            EntityListItemAttributes.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return entityListItemAttributeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<EntityListItemAttribute> getEntityListItemAttributesByEntityListItem(EntityListItem entityListItem) {
        return getEntityListItemAttributesByEntityListItem(entityListItem, EntityPermission.READ_ONLY);
    }
    
    public List<EntityListItemAttribute> getEntityListItemAttributesByEntityListItemForUpdate(EntityListItem entityListItem) {
        return getEntityListItemAttributesByEntityListItem(entityListItem, EntityPermission.READ_WRITE);
    }
    
    public List<EntityListItemAttribute> getEntityListItemAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        var query = session.getDslContext()
                .select(EntityListItemAttributes.fields())
                .from(EntityListItemAttributes)
                .where(EntityListItemAttributes.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                        EntityListItemAttributes.THRU_TIME.eq(Session.MAX_TIME))
                .forUpdate();
        
        return entityListItemAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, query);
    }
    
    public EntityListItemAttributeTransfer getEntityListItemAttributeTransfer(UserVisit userVisit, EntityListItemAttribute entityListItemAttribute, EntityInstance entityInstance) {
        return entityListItemAttributeTransferCache.getEntityListItemAttributeTransfer(userVisit, entityListItemAttribute, entityInstance);
    }
    
    public void updateEntityListItemAttributeFromValue(EntityListItemAttributeValue entityListItemAttributeValue, BasePK updatedBy) {
        if(entityListItemAttributeValue.hasBeenModified()) {
            var entityListItemAttribute = entityListItemAttributeFactory.getEntityFromValue(EntityPermission.READ_WRITE, entityListItemAttributeValue);
            var entityAttribute = entityListItemAttribute.getEntityAttribute();
            var entityInstance = entityListItemAttribute.getEntityInstance();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityListItemAttribute.setThruTime(session.getStartTime());
                entityListItemAttribute.store();
            } else {
                entityListItemAttribute.remove();
            }
            
            entityListItemAttributeFactory.create(entityAttribute.getPrimaryKey(), entityInstance.getPrimaryKey(), entityListItemAttributeValue.getEntityListItemPK(),
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityListItemAttribute(EntityListItemAttribute entityListItemAttribute, BasePK deletedBy) {
        var entityAttribute = entityListItemAttribute.getEntityAttribute();
        var entityInstance = entityListItemAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityListItemAttribute.setThruTime(session.getStartTime());
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

    @Inject
    protected EntityLongDefaultFactory entityLongDefaultFactory;

    @Inject
    EntityLongDefaultTransferCache entityLongDefaultTransferCache;

    public EntityLongDefault createEntityLongDefault(EntityAttribute entityAttribute, Long longAttribute,
            BasePK createdBy) {
        var entityLongDefault = entityLongDefaultFactory.create(entityAttribute,
                longAttribute, session.getStartTime(), Session.MAX_TIME);

        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityLongDefault.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return entityLongDefault;
    }

    public long countEntityLongDefaultHistory(EntityAttribute entityAttribute) {
        return session.getDslContext()
                .selectCount()
                .from(EntityLongDefaults)
                .where(EntityLongDefaults.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public List<EntityLongDefault> getEntityLongDefaultHistory(EntityAttribute entityAttribute) {
        var query = session.applyLimit(session.getDslContext()
                .select(EntityLongDefaults.fields())
                .from(EntityLongDefaults)
                .where(EntityLongDefaults.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()))
                .orderBy(EntityLongDefaults.THRU_TIME),
                EntityLongDefaultFactory.class);

        return entityLongDefaultFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }

    private EntityLongDefault getEntityLongDefault(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EntityLongDefaults.fields())
                .from(EntityLongDefaults)
                .where(EntityLongDefaults.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityLongDefaults.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return entityLongDefaultFactory.getEntityFromQuery(entityPermission, query);
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
        return entityLongDefaultTransferCache.getEntityLongDefaultTransfer(userVisit, entityLongDefault);
    }

    public void updateEntityLongDefaultFromValue(EntityLongDefaultValue entityLongDefaultValue, BasePK updatedBy) {
        if(entityLongDefaultValue.hasBeenModified()) {
            var entityLongDefault = entityLongDefaultFactory.getEntityFromValue(EntityPermission.READ_WRITE, entityLongDefaultValue);
            var entityAttribute = entityLongDefault.getEntityAttribute();

            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityLongDefault.setThruTime(session.getStartTime());
                entityLongDefault.store();
            } else {
                entityLongDefault.remove();
            }

            entityLongDefault = entityLongDefaultFactory.create(entityAttribute,
                    entityLongDefaultValue.getLongAttribute(), session.getStartTime(),
                    Session.MAX_TIME);

            sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityLongDefault.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteEntityLongDefault(EntityLongDefault entityLongDefault, BasePK deletedBy) {
        var entityAttribute = entityLongDefault.getEntityAttribute();

        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityLongDefault.setThruTime(session.getStartTime());
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

    @Inject
    protected EntityLongAttributeFactory entityLongAttributeFactory;

    @Inject
    EntityLongAttributeTransferCache entityLongAttributeTransferCache;

    public EntityLongAttribute createEntityLongAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Long longAttribute, BasePK createdBy) {
        return createEntityLongAttribute(entityAttribute.getPrimaryKey(), entityInstance, longAttribute,
                createdBy);
    }

    public EntityLongAttribute createEntityLongAttribute(EntityAttributePK entityAttribute, EntityInstance entityInstance,
            Long longAttribute, BasePK createdBy) {
        var entityLongAttribute = entityLongAttributeFactory.create(entityAttribute,
                entityInstance.getPrimaryKey(), longAttribute, session.getStartTime(), Session.MAX_TIME);

        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute, EventTypes.CREATE, createdBy);

        return entityLongAttribute;
    }

    public long countEntityLongAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return session.getDslContext()
                .selectCount()
                .from(EntityLongAttributes)
                .where(EntityLongAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityLongAttributes.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public List<EntityLongAttribute> getEntityLongAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        var query = session.applyLimit(session.getDslContext()
                .select(EntityLongAttributes.fields())
                .from(EntityLongAttributes)
                .where(EntityLongAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityLongAttributes.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()))
                .orderBy(EntityLongAttributes.THRU_TIME),
                EntityLongAttributeFactory.class);

        return entityLongAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }

    private EntityLongAttribute getEntityLongAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EntityLongAttributes.fields())
                .from(EntityLongAttributes)
                .where(EntityLongAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityLongAttributes.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                        EntityLongAttributes.THRU_TIME.eq(Session.MAX_TIME));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };
            
        return entityLongAttributeFactory.getEntityFromQuery(entityPermission, query);
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
        var query = session.getDslContext()
                .select(EntityLongAttributes.fields())
                .from(EntityLongAttributes)
                .where(EntityLongAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityLongAttributes.THRU_TIME.eq(Session.MAX_TIME))
                .forUpdate();
        
        return entityLongAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, query);
    }
    
    public List<EntityLongAttribute> getEntityLongAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        var query = session.getDslContext()
                .select(EntityLongAttributes.fields())
                .from(EntityLongAttributes)
                .where(EntityLongAttributes.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                        EntityLongAttributes.THRU_TIME.eq(Session.MAX_TIME))
                .forUpdate();
        
        return entityLongAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, query);
    }
    
    public EntityLongAttributeTransfer getEntityLongAttributeTransfer(UserVisit userVisit, EntityLongAttribute entityLongAttribute, EntityInstance entityInstance) {
        return entityLongAttributeTransferCache.getEntityLongAttributeTransfer(userVisit, entityLongAttribute, entityInstance);
    }
    
    public void updateEntityLongAttributeFromValue(EntityLongAttributeValue entityLongAttributeValue, BasePK updatedBy) {
        if(entityLongAttributeValue.hasBeenModified()) {
            var entityLongAttribute = entityLongAttributeFactory.getEntityFromValue(EntityPermission.READ_WRITE, entityLongAttributeValue);
            var entityAttribute = entityLongAttribute.getEntityAttribute();
            var entityInstance = entityLongAttribute.getEntityInstance();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityLongAttribute.setThruTime(session.getStartTime());
                entityLongAttribute.store();
            } else {
                entityLongAttribute.remove();
            }
            
            entityLongAttributeFactory.create(entityAttribute, entityInstance, entityLongAttributeValue.getLongAttribute(), session.getStartTime(),
                    Session.MAX_TIME);
            
            sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityLongAttribute(EntityLongAttribute entityLongAttribute, BasePK deletedBy) {
        var entityAttribute = entityLongAttribute.getEntityAttribute();
        var entityInstance = entityLongAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityLongAttribute.setThruTime(session.getStartTime());
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

    @Inject
    protected EntityMultipleListItemDefaultFactory entityMultipleListItemDefaultFactory;

    @Inject
    EntityMultipleListItemDefaultTransferCache entityMultipleListItemDefaultTransferCache;

    public EntityMultipleListItemDefault createEntityMultipleListItemDefault(EntityAttribute entityAttribute,
            EntityListItem entityListItem, BasePK createdBy) {
        var entityMultipleListItemDefault = entityMultipleListItemDefaultFactory.create(
                entityAttribute, entityListItem, session.getStartTime(), Session.MAX_TIME);

        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return entityMultipleListItemDefault;
    }

    public long countEntityMultipleListItemDefaults(EntityAttribute entityAttribute) {
        return session.getDslContext()
                .selectCount()
                .from(EntityMultipleListItemDefaults)
                .where(EntityMultipleListItemDefaults.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityMultipleListItemDefaults.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countEntityMultipleListItemDefaultHistory(EntityAttribute entityAttribute) {
        return session.getDslContext()
                .selectCount()
                .from(EntityMultipleListItemDefaults)
                .where(EntityMultipleListItemDefaults.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public List<EntityMultipleListItemDefault> getEntityMultipleListItemDefaultHistory(EntityAttribute entityAttribute) {
        var query = session.applyLimit(session.getDslContext()
                .select(EntityMultipleListItemDefaults.fields())
                .from(EntityMultipleListItemDefaults)
                .where(EntityMultipleListItemDefaults.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()))
                .orderBy(EntityMultipleListItemDefaults.THRU_TIME),
                EntityMultipleListItemDefaultFactory.class);

        return entityMultipleListItemDefaultFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }

    public List<EntityMultipleListItemDefault> getEntityMultipleListItemDefaults(EntityAttribute entityAttribute) {
        var query = session.applyLimit(session.getDslContext()
                .select(EntityMultipleListItemDefaults.fields())
                .from(EntityMultipleListItemDefaults)
                .join(EntityListItems).onKey(ENTITY_MULTIPLE_LIST_ITEM_DEFAULTS_ENTITY_LIST_ITEM_FK)
                .join(EntityListItemDetails).onKey(ENTITY_LIST_ITEMS_LAST_DETAIL_FK)
                .where(EntityMultipleListItemDefaults.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityMultipleListItemDefaults.THRU_TIME.eq(Session.MAX_TIME))
                .orderBy(EntityListItemDetails.SORT_ORDER, EntityListItemDetails.ENTITY_LIST_ITEM_NAME),
                EntityMultipleListItemDefaultFactory.class);

        return entityMultipleListItemDefaultFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }

    private EntityMultipleListItemDefault getEntityMultipleListItemDefault(EntityAttribute entityAttribute,
            EntityListItem entityListItem, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EntityMultipleListItemDefaults.fields())
                .from(EntityMultipleListItemDefaults)
                .where(EntityMultipleListItemDefaults.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityMultipleListItemDefaults.ENTITY_LIST_ITEM.eq(entityListItem.getPrimaryKey()),
                        EntityMultipleListItemDefaults.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return entityMultipleListItemDefaultFactory.getEntityFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(EntityMultipleListItemDefaults.fields())
                    .from(EntityMultipleListItemDefaults)
                    .join(EntityAttributes).onKey(ENTITY_MULTIPLE_LIST_ITEM_DEFAULTS_ENTITY_ATTRIBUTE_FK)
                    .join(EntityAttributeDetails).onKey(ENTITY_ATTRIBUTES_LAST_DETAIL_FK)
                    .where(EntityMultipleListItemDefaults.ENTITY_LIST_ITEM.eq(entityListItem.getPrimaryKey()),
                            EntityMultipleListItemDefaults.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(EntityAttributeDetails.SORT_ORDER, EntityAttributeDetails.ENTITY_ATTRIBUTE_NAME),
                    EntityMultipleListItemDefaultFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(EntityMultipleListItemDefaults.fields())
                    .from(EntityMultipleListItemDefaults)
                    .where(EntityMultipleListItemDefaults.ENTITY_LIST_ITEM.eq(entityListItem.getPrimaryKey()),
                            EntityMultipleListItemDefaults.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return entityMultipleListItemDefaultFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<EntityMultipleListItemDefault> getEntityMultipleListItemDefaultsByEntityListItem(EntityListItem entityListItem) {
        return getEntityMultipleListItemDefaultsByEntityListItem(entityListItem, EntityPermission.READ_ONLY);
    }

    public List<EntityMultipleListItemDefault> getEntityMultipleListItemDefaultsByEntityListItemForUpdate(EntityListItem entityListItem) {
        return getEntityMultipleListItemDefaultsByEntityListItem(entityListItem, EntityPermission.READ_WRITE);
    }

    public EntityMultipleListItemDefaultTransfer getEntityMultipleListItemDefaultTransfer(UserVisit userVisit, EntityMultipleListItemDefault entityMultipleListItemDefault) {
        return entityMultipleListItemDefaultTransferCache.getEntityMultipleListItemDefaultTransfer(userVisit, entityMultipleListItemDefault);
    }

    public List<EntityMultipleListItemDefaultTransfer> getEntityMultipleListItemDefaultTransfers(UserVisit userVisit, Collection<EntityMultipleListItemDefault> entityMultipleListItemDefaults) {
        List<EntityMultipleListItemDefaultTransfer> entityMultipleListItemDefaultTransfers = new ArrayList<>(entityMultipleListItemDefaults.size());

        entityMultipleListItemDefaults.forEach((entityMultipleListItemDefault) ->
                entityMultipleListItemDefaultTransfers.add(entityMultipleListItemDefaultTransferCache.getEntityMultipleListItemDefaultTransfer(userVisit, entityMultipleListItemDefault))
        );

        return entityMultipleListItemDefaultTransfers;
    }

    public List<EntityMultipleListItemDefaultTransfer> getEntityMultipleListItemDefaultTransfers(UserVisit userVisit, EntityAttribute entityAttribute) {
        return getEntityMultipleListItemDefaultTransfers(userVisit, getEntityMultipleListItemDefaults(entityAttribute));
    }

    public void deleteEntityMultipleListItemDefault(EntityMultipleListItemDefault entityMultipleListItemDefault, BasePK deletedBy) {
        var entityAttribute = entityMultipleListItemDefault.getEntityAttribute();

        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityMultipleListItemDefault.setThruTime(session.getStartTime());
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

    @Inject
    protected EntityMultipleListItemAttributeFactory entityMultipleListItemAttributeFactory;

    @Inject
    EntityMultipleListItemAttributeTransferCache entityMultipleListItemAttributeTransferCache;

    public EntityMultipleListItemAttribute createEntityMultipleListItemAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityListItem entityListItem, BasePK createdBy) {
        return createEntityMultipleListItemAttribute(entityAttribute.getPrimaryKey(), entityInstance, entityListItem, createdBy);
    }

    public EntityMultipleListItemAttribute createEntityMultipleListItemAttribute(EntityAttributePK entityAttribute, EntityInstance entityInstance,
            EntityListItem entityListItem, BasePK createdBy) {
        var entityListItemAttribute = entityMultipleListItemAttributeFactory.create(entityAttribute,
                entityInstance.getPrimaryKey(), entityListItem.getPrimaryKey(), session.getStartTime(),
                Session.MAX_TIME);

        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute, EventTypes.CREATE, createdBy);

        return entityListItemAttribute;
    }

    public long countEntityMultipleListItemAttributes(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return session.getDslContext()
                .selectCount()
                .from(EntityMultipleListItemAttributes)
                .where(EntityMultipleListItemAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityMultipleListItemAttributes.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                        EntityMultipleListItemAttributes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public List<EntityMultipleListItemAttribute> getEntityMultipleListItemAttributes(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        var query = session.applyLimit(session.getDslContext()
                .select(EntityMultipleListItemAttributes.fields())
                .from(EntityMultipleListItemAttributes)
                .join(EntityListItems).onKey(ENTITY_MULTIPLE_LIST_ITEM_ATTRIBUTES_ENTITY_LIST_ITEM_FK)
                .join(EntityListItemDetails).onKey(ENTITY_LIST_ITEMS_LAST_DETAIL_FK)
                .where(EntityMultipleListItemAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityMultipleListItemAttributes.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                        EntityMultipleListItemAttributes.THRU_TIME.eq(Session.MAX_TIME))
                .orderBy(EntityListItemDetails.SORT_ORDER, EntityListItemDetails.ENTITY_LIST_ITEM_NAME),
                EntityMultipleListItemAttributeFactory.class);
        
        return entityMultipleListItemAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    private EntityMultipleListItemAttribute getEntityMultipleListItemAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityListItem entityListItem, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EntityMultipleListItemAttributes.fields())
                .from(EntityMultipleListItemAttributes)
                .where(EntityMultipleListItemAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityMultipleListItemAttributes.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                        EntityMultipleListItemAttributes.ENTITY_LIST_ITEM.eq(entityListItem.getPrimaryKey()),
                        EntityMultipleListItemAttributes.THRU_TIME.eq(Session.MAX_TIME));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };
            
        return entityMultipleListItemAttributeFactory.getEntityFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(EntityMultipleListItemAttributes.fields())
                    .from(EntityMultipleListItemAttributes)
                    .join(EntityInstances).onKey(ENTITY_MULTIPLE_LIST_ITEM_ATTRIBUTES_ENTITY_INSTANCE_FK)
                    .where(EntityMultipleListItemAttributes.ENTITY_LIST_ITEM.eq(entityListItem.getPrimaryKey()),
                            EntityMultipleListItemAttributes.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(EntityInstances.ENTITY_UNIQUE),
                    EntityMultipleListItemAttributeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(EntityMultipleListItemAttributes.fields())
                    .from(EntityMultipleListItemAttributes)
                    .where(EntityMultipleListItemAttributes.ENTITY_LIST_ITEM.eq(entityListItem.getPrimaryKey()),
                            EntityMultipleListItemAttributes.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return entityMultipleListItemAttributeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<EntityMultipleListItemAttribute> getEntityMultipleListItemAttributesByEntityListItem(EntityListItem entityListItem) {
        return getEntityMultipleListItemAttributesByEntityListItem(entityListItem, EntityPermission.READ_ONLY);
    }
    
    public List<EntityMultipleListItemAttribute> getEntityMultipleListItemAttributesByEntityListItemForUpdate(EntityListItem entityListItem) {
        return getEntityMultipleListItemAttributesByEntityListItem(entityListItem, EntityPermission.READ_WRITE);
    }
    
    public List<EntityMultipleListItemAttribute> getEntityMultipleListItemAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        var query = session.getDslContext()
                .select(EntityMultipleListItemAttributes.fields())
                .from(EntityMultipleListItemAttributes)
                .where(EntityMultipleListItemAttributes.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                        EntityMultipleListItemAttributes.THRU_TIME.eq(Session.MAX_TIME))
                .forUpdate();
        
        return entityMultipleListItemAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, query);
    }
    
    public EntityMultipleListItemAttributeTransfer getEntityMultipleListItemAttributeTransfer(UserVisit userVisit, EntityMultipleListItemAttribute entityMultipleListItemAttribute, EntityInstance entityInstance) {
        return entityMultipleListItemAttributeTransferCache.getEntityMultipleListItemAttributeTransfer(userVisit, entityMultipleListItemAttribute, entityInstance);
    }
    
    public List<EntityMultipleListItemAttributeTransfer> getEntityMultipleListItemAttributeTransfers(UserVisit userVisit, Collection<EntityMultipleListItemAttribute> entityMultipleListItemAttributes, EntityInstance entityInstance) {
        List<EntityMultipleListItemAttributeTransfer> entityMultipleListItemAttributeTransfers = new ArrayList<>(entityMultipleListItemAttributes.size());
        
        entityMultipleListItemAttributes.forEach((entityMultipleListItemAttribute) ->
                entityMultipleListItemAttributeTransfers.add(entityMultipleListItemAttributeTransferCache.getEntityMultipleListItemAttributeTransfer(userVisit, entityMultipleListItemAttribute, entityInstance))
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
            entityMultipleListItemAttribute.setThruTime(session.getStartTime());
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

    @Inject
    protected EntityNameAttributeFactory entityNameAttributeFactory;

    @Inject
    EntityNameAttributeTransferCache entityNameAttributeTransferCache;

    public EntityNameAttribute createEntityNameAttribute(EntityAttribute entityAttribute, String nameAttribute,
            EntityInstance entityInstance, BasePK createdBy) {
        var entityNameAttribute = entityNameAttributeFactory.create(entityAttribute,
                nameAttribute, entityInstance, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return entityNameAttribute;
    }

    public long countEntityNameAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return session.getDslContext()
                .selectCount()
                .from(EntityNameAttributes)
                .where(EntityNameAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityNameAttributes.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public List<EntityNameAttribute> getEntityNameAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        var query = session.applyLimit(session.getDslContext()
                .select(EntityNameAttributes.fields())
                .from(EntityNameAttributes)
                .where(EntityNameAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityNameAttributes.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()))
                .orderBy(EntityNameAttributes.THRU_TIME),
                EntityNameAttributeFactory.class);

        return entityNameAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }

    private EntityNameAttribute getEntityNameAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EntityNameAttributes.fields())
                .from(EntityNameAttributes)
                .where(EntityNameAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityNameAttributes.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                        EntityNameAttributes.THRU_TIME.eq(Session.MAX_TIME));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };
            
        return entityNameAttributeFactory.getEntityFromQuery(entityPermission, query);
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
        var query = session.getDslContext()
                .select(EntityNameAttributes.fields())
                .from(EntityNameAttributes)
                .where(EntityNameAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityNameAttributes.THRU_TIME.eq(Session.MAX_TIME))
                .forUpdate();
        
        return entityNameAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, query);
    }
    
    public List<EntityNameAttribute> getEntityNameAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        var query = session.getDslContext()
                .select(EntityNameAttributes.fields())
                .from(EntityNameAttributes)
                .where(EntityNameAttributes.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                        EntityNameAttributes.THRU_TIME.eq(Session.MAX_TIME))
                .forUpdate();
        
        return entityNameAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, query);
    }
    
    public EntityNameAttributeTransfer getEntityNameAttributeTransfer(UserVisit userVisit, EntityNameAttribute entityNameAttribute, EntityInstance entityInstance) {
        return entityNameAttributeTransferCache.getEntityNameAttributeTransfer(userVisit, entityNameAttribute, entityInstance);
    }
    
    public void updateEntityNameAttributeFromValue(EntityNameAttributeValue entityNameAttributeValue, BasePK updatedBy) {
        if(entityNameAttributeValue.hasBeenModified()) {
            var entityNameAttribute = entityNameAttributeFactory.getEntityFromValue(EntityPermission.READ_WRITE, entityNameAttributeValue);
            var entityAttribute = entityNameAttribute.getEntityAttribute();
            var entityInstance = entityNameAttribute.getEntityInstance();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityNameAttribute.setThruTime(session.getStartTime());
                entityNameAttribute.store();
            } else {
                entityNameAttribute.remove();
            }
            
            entityNameAttributeFactory.create(entityAttribute, entityNameAttributeValue.getNameAttribute(), entityInstance, session.getStartTime(),
                    Session.MAX_TIME);
            
            sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityNameAttribute(EntityNameAttribute entityNameAttribute, BasePK deletedBy) {
        var entityAttribute = entityNameAttribute.getEntityAttribute();
        var entityInstance = entityNameAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityNameAttribute.setThruTime(session.getStartTime());
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
        var query = session.applyLimit(session.getDslContext()
                .select(EntityNameAttributes.fields())
                .from(EntityNameAttributes)
                .join(EntityInstances).onKey(ENTITY_NAME_ATTRIBUTES_ENTITY_INSTANCE_FK)
                .join(EntityTypes).onKey(ENTITY_INSTANCES_ENTITY_TYPE_FK)
                .join(EntityTypeDetails).onKey(ENTITY_TYPES_LAST_DETAIL_FK)
                .join(ComponentVendors).onKey(ENTITY_TYPE_DETAILS_COMPONENT_VENDOR_FK)
                .join(ComponentVendorDetails).onKey(COMPONENT_VENDORS_LAST_DETAIL_FK)
                .where(EntityNameAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityNameAttributes.NAME_ATTRIBUTE.eq(nameAttribute),
                        EntityNameAttributes.THRU_TIME.eq(Session.MAX_TIME))
                .orderBy(ComponentVendorDetails.COMPONENT_VENDOR_NAME, EntityTypeDetails.SORT_ORDER,
                        EntityTypeDetails.ENTITY_TYPE_NAME, EntityInstances.ENTITY_UNIQUE),
                EntityNameAttributeFactory.class);

        return entityNameAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }

    // --------------------------------------------------------------------------------
    //   Entity String Defaults
    // --------------------------------------------------------------------------------

    @Inject
    protected EntityStringDefaultFactory entityStringDefaultFactory;

    @Inject
    EntityStringDefaultTransferCache entityStringDefaultTransferCache;

    public EntityStringDefault createEntityStringDefault(EntityAttribute entityAttribute, Language language,
            String stringAttribute, BasePK createdBy) {
        var entityStringDefault = entityStringDefaultFactory.create(entityAttribute, language,
                stringAttribute, session.getStartTime(), Session.MAX_TIME);

        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityStringDefault.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return entityStringDefault;
    }

    public long countEntityStringDefaultHistory(EntityAttribute entityAttribute, Language language) {
        return session.getDslContext()
                .selectCount()
                .from(EntityStringDefaults)
                .where(EntityStringDefaults.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityStringDefaults.LANGUAGE.eq(language.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public List<EntityStringDefault> getEntityStringDefaultHistory(EntityAttribute entityAttribute, Language language) {
        var query = session.applyLimit(session.getDslContext()
                .select(EntityStringDefaults.fields())
                .from(EntityStringDefaults)
                .where(EntityStringDefaults.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityStringDefaults.LANGUAGE.eq(language.getPrimaryKey()))
                .orderBy(EntityStringDefaults.THRU_TIME),
                EntityStringDefaultFactory.class);

        return entityStringDefaultFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }

    private EntityStringDefault getEntityStringDefault(EntityAttribute entityAttribute, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EntityStringDefaults.fields())
                .from(EntityStringDefaults)
                .where(EntityStringDefaults.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityStringDefaults.LANGUAGE.eq(language.getPrimaryKey()),
                        EntityStringDefaults.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return entityStringDefaultFactory.getEntityFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(EntityStringDefaults.fields())
                    .from(EntityStringDefaults)
                    .join(Languages).onKey(ENTITY_STRING_DEFAULTS_LANGUAGE_FK)
                    .where(EntityStringDefaults.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                            EntityStringDefaults.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    EntityStringDefaultFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(EntityStringDefaults.fields())
                    .from(EntityStringDefaults)
                    .where(EntityStringDefaults.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                            EntityStringDefaults.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return entityStringDefaultFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<EntityStringDefault> getEntityStringDefaultsByEntityAttribute(EntityAttribute entityAttribute) {
        return getEntityStringDefaultsByEntityAttribute(entityAttribute, EntityPermission.READ_ONLY);
    }

    public List<EntityStringDefault> getEntityStringDefaultsByEntityAttributeForUpdate(EntityAttribute entityAttribute) {
        return getEntityStringDefaultsByEntityAttribute(entityAttribute, EntityPermission.READ_WRITE);
    }

    public EntityStringDefaultTransfer getEntityStringDefaultTransfer(UserVisit userVisit, EntityStringDefault entityStringDefault) {
        return entityStringDefaultTransferCache.getEntityStringDefaultTransfer(userVisit, entityStringDefault);
    }

    public void updateEntityStringDefaultFromValue(EntityStringDefaultValue entityStringDefaultValue, BasePK updatedBy) {
        if(entityStringDefaultValue.hasBeenModified()) {
            var entityStringDefault = entityStringDefaultFactory.getEntityFromValue(EntityPermission.READ_WRITE, entityStringDefaultValue);
            var entityAttribute = entityStringDefault.getEntityAttribute();
            var language = entityStringDefault.getLanguage();

            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityStringDefault.setThruTime(session.getStartTime());
                entityStringDefault.store();
            } else {
                entityStringDefault.remove();
            }

            entityStringDefault = entityStringDefaultFactory.create(entityAttribute, language,
                    entityStringDefaultValue.getStringAttribute(), session.getStartTime(),
                    Session.MAX_TIME);

            sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityStringDefault.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteEntityStringDefault(EntityStringDefault entityStringDefault, BasePK deletedBy) {
        var entityAttribute = entityStringDefault.getEntityAttribute();

        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityStringDefault.setThruTime(session.getStartTime());
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

    @Inject
    protected EntityStringAttributeFactory entityStringAttributeFactory;

    @Inject
    EntityStringAttributeTransferCache entityStringAttributeTransferCache;

    public EntityStringAttribute createEntityStringAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Language language, String stringAttribute, BasePK createdBy) {
        return createEntityStringAttribute(entityAttribute.getPrimaryKey(), entityInstance, language, stringAttribute,
                createdBy);
    }

    public EntityStringAttribute createEntityStringAttribute(EntityAttributePK entityAttribute, EntityInstance entityInstance,
            Language language, String stringAttribute, BasePK createdBy) {
        var entityStringAttribute = entityStringAttributeFactory.create(entityAttribute,
                entityInstance.getPrimaryKey(), language.getPrimaryKey(), stringAttribute, session.getStartTime(), Session.MAX_TIME);

        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute, EventTypes.CREATE, createdBy);

        return entityStringAttribute;
    }
    
    public long countEntityStringAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Language language) {
        return session.getDslContext()
                .selectCount()
                .from(EntityStringAttributes)
                .where(EntityStringAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityStringAttributes.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                        EntityStringAttributes.LANGUAGE.eq(language.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public List<EntityStringAttribute> getEntityStringAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Language language) {
        var query = session.applyLimit(session.getDslContext()
                .select(EntityStringAttributes.fields())
                .from(EntityStringAttributes)
                .where(EntityStringAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityStringAttributes.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                        EntityStringAttributes.LANGUAGE.eq(language.getPrimaryKey()))
                .orderBy(EntityStringAttributes.THRU_TIME),
                EntityStringAttributeFactory.class);

        return entityStringAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }

    private EntityStringAttribute getEntityStringAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EntityStringAttributes.fields())
                .from(EntityStringAttributes)
                .where(EntityStringAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityStringAttributes.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                        EntityStringAttributes.LANGUAGE.eq(language.getPrimaryKey()),
                        EntityStringAttributes.THRU_TIME.eq(Session.MAX_TIME));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };
            
        return entityStringAttributeFactory.getEntityFromQuery(entityPermission, query);
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
            entityStringAttribute = getEntityStringAttribute(entityAttribute, entityInstance, partyControl.getDefaultLanguage());
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
        var query = session.getDslContext()
                .select(EntityStringAttributes.fields())
                .from(EntityStringAttributes)
                .where(EntityStringAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityStringAttributes.THRU_TIME.eq(Session.MAX_TIME))
                .forUpdate();
        
        return entityStringAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, query);
    }
    
    public List<EntityStringAttribute> getEntityStringAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        var query = session.getDslContext()
                .select(EntityStringAttributes.fields())
                .from(EntityStringAttributes)
                .where(EntityStringAttributes.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                        EntityStringAttributes.THRU_TIME.eq(Session.MAX_TIME))
                .forUpdate();
        
        return entityStringAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, query);
    }
    
    public EntityStringAttributeTransfer getEntityStringAttributeTransfer(UserVisit userVisit, EntityStringAttribute entityStringAttribute, EntityInstance entityInstance) {
        return entityStringAttributeTransferCache.getEntityStringAttributeTransfer(userVisit, entityStringAttribute, entityInstance);
    }
    
    public void updateEntityStringAttributeFromValue(EntityStringAttributeValue entityStringAttributeValue, BasePK updatedBy) {
        if(entityStringAttributeValue.hasBeenModified()) {
            var entityStringAttribute = entityStringAttributeFactory.getEntityFromValue(EntityPermission.READ_WRITE, entityStringAttributeValue);
            var entityAttribute = entityStringAttribute.getEntityAttribute();
            var entityInstance = entityStringAttribute.getEntityInstance();
            var language = entityStringAttribute.getLanguage();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityStringAttribute.setThruTime(session.getStartTime());
                entityStringAttribute.store();
            } else {
                entityStringAttribute.remove();
            }
            
            entityStringAttributeFactory.create(entityAttribute, entityInstance, language, entityStringAttributeValue.getStringAttribute(), session.getStartTime(),
                    Session.MAX_TIME);
            
            sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityStringAttribute(EntityStringAttribute entityStringAttribute, BasePK deletedBy) {
        var entityAttribute = entityStringAttribute.getEntityAttribute();
        var entityInstance = entityStringAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityStringAttribute.setThruTime(session.getStartTime());
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

    @Inject
    protected EntityGeoPointDefaultFactory entityGeoPointDefaultFactory;

    @Inject
    EntityGeoPointDefaultTransferCache entityGeoPointDefaultTransferCache;

    public EntityGeoPointDefault createEntityGeoPointDefault(EntityAttribute entityAttribute, Integer latitude,
            Integer longitude, Long elevation, Long altitude, BasePK createdBy) {
        var entityGeoPointDefault = entityGeoPointDefaultFactory.create(entityAttribute,
                latitude, longitude, elevation, altitude, session.getStartTime(), Session.MAX_TIME);

        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityGeoPointDefault.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return entityGeoPointDefault;
    }

    public long countEntityGeoPointDefaultHistory(EntityAttribute entityAttribute) {
        return session.getDslContext()
                .selectCount()
                .from(EntityGeoPointDefaults)
                .where(EntityGeoPointDefaults.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public List<EntityGeoPointDefault> getEntityGeoPointDefaultHistory(EntityAttribute entityAttribute) {
        var query = session.applyLimit(session.getDslContext()
                .select(EntityGeoPointDefaults.fields())
                .from(EntityGeoPointDefaults)
                .where(EntityGeoPointDefaults.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()))
                .orderBy(EntityGeoPointDefaults.THRU_TIME),
                EntityGeoPointDefaultFactory.class);

        return entityGeoPointDefaultFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }

    private EntityGeoPointDefault getEntityGeoPointDefault(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EntityGeoPointDefaults.fields())
                .from(EntityGeoPointDefaults)
                .where(EntityGeoPointDefaults.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityGeoPointDefaults.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return entityGeoPointDefaultFactory.getEntityFromQuery(entityPermission, query);
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
        return entityGeoPointDefaultTransferCache.getEntityGeoPointDefaultTransfer(userVisit, entityGeoPointDefault);
    }

    public void updateEntityGeoPointDefaultFromValue(EntityGeoPointDefaultValue entityGeoPointDefaultValue, BasePK updatedBy) {
        if(entityGeoPointDefaultValue.hasBeenModified()) {
            var entityGeoPointDefault = entityGeoPointDefaultFactory.getEntityFromValue(EntityPermission.READ_WRITE, entityGeoPointDefaultValue);
            var entityAttribute = entityGeoPointDefault.getEntityAttribute();

            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityGeoPointDefault.setThruTime(session.getStartTime());
                entityGeoPointDefault.store();
            } else {
                entityGeoPointDefault.remove();
            }

            entityGeoPointDefault = entityGeoPointDefaultFactory.create(entityAttribute,
                    entityGeoPointDefaultValue.getLatitude(), entityGeoPointDefaultValue.getLongitude(),
                    entityGeoPointDefaultValue.getElevation(), entityGeoPointDefaultValue.getAltitude(),
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityGeoPointDefault.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteEntityGeoPointDefault(EntityGeoPointDefault entityGeoPointDefault, BasePK deletedBy) {
        var entityAttribute = entityGeoPointDefault.getEntityAttribute();

        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityGeoPointDefault.setThruTime(session.getStartTime());
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

    @Inject
    protected EntityGeoPointAttributeFactory entityGeoPointAttributeFactory;

    @Inject
    EntityGeoPointAttributeTransferCache entityGeoPointAttributeTransferCache;

    public EntityGeoPointAttribute createEntityGeoPointAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Integer latitude, Integer longitude, Long elevation, Long altitude, BasePK createdBy) {
        return createEntityGeoPointAttribute(entityAttribute.getPrimaryKey(), entityInstance, latitude, longitude,
                elevation, altitude, createdBy);
    }

    public EntityGeoPointAttribute createEntityGeoPointAttribute(EntityAttributePK entityAttribute, EntityInstance entityInstance,
            Integer latitude, Integer longitude, Long elevation, Long altitude, BasePK createdBy) {
        var entityGeoPointAttribute = entityGeoPointAttributeFactory.create(entityAttribute,
                entityInstance.getPrimaryKey(), latitude, longitude, elevation, altitude, session.getStartTime(),
                Session.MAX_TIME);

        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute, EventTypes.CREATE, createdBy);

        return entityGeoPointAttribute;
    }

    public long countEntityGeoPointAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return session.getDslContext()
                .selectCount()
                .from(EntityGeoPointAttributes)
                .where(EntityGeoPointAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityGeoPointAttributes.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public List<EntityGeoPointAttribute> getEntityGeoPointAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        var query = session.applyLimit(session.getDslContext()
                .select(EntityGeoPointAttributes.fields())
                .from(EntityGeoPointAttributes)
                .where(EntityGeoPointAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityGeoPointAttributes.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()))
                .orderBy(EntityGeoPointAttributes.THRU_TIME),
                EntityGeoPointAttributeFactory.class);

        return entityGeoPointAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }

    private EntityGeoPointAttribute getEntityGeoPointAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EntityGeoPointAttributes.fields())
                .from(EntityGeoPointAttributes)
                .where(EntityGeoPointAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityGeoPointAttributes.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                        EntityGeoPointAttributes.THRU_TIME.eq(Session.MAX_TIME));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };
            
        return entityGeoPointAttributeFactory.getEntityFromQuery(entityPermission, query);
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
        var query = session.getDslContext()
                .select(EntityGeoPointAttributes.fields())
                .from(EntityGeoPointAttributes)
                .where(EntityGeoPointAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityGeoPointAttributes.THRU_TIME.eq(Session.MAX_TIME))
                .forUpdate();
        
        return entityGeoPointAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, query);
    }
    
    public List<EntityGeoPointAttribute> getEntityGeoPointAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        var query = session.getDslContext()
                .select(EntityGeoPointAttributes.fields())
                .from(EntityGeoPointAttributes)
                .where(EntityGeoPointAttributes.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                        EntityGeoPointAttributes.THRU_TIME.eq(Session.MAX_TIME))
                .forUpdate();
        
        return entityGeoPointAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, query);
    }
    
    public EntityGeoPointAttributeTransfer getEntityGeoPointAttributeTransfer(UserVisit userVisit, EntityGeoPointAttribute entityGeoPointAttribute, EntityInstance entityInstance) {
        return entityGeoPointAttributeTransferCache.getEntityGeoPointAttributeTransfer(userVisit, entityGeoPointAttribute, entityInstance);
    }
    
    public void updateEntityGeoPointAttributeFromValue(EntityGeoPointAttributeValue entityGeoPointAttributeValue, BasePK updatedBy) {
        if(entityGeoPointAttributeValue.hasBeenModified()) {
            var entityGeoPointAttribute = entityGeoPointAttributeFactory.getEntityFromValue(EntityPermission.READ_WRITE, entityGeoPointAttributeValue);
            var entityAttribute = entityGeoPointAttribute.getEntityAttribute();
            var entityInstance = entityGeoPointAttribute.getEntityInstance();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityGeoPointAttribute.setThruTime(session.getStartTime());
                entityGeoPointAttribute.store();
            } else {
                entityGeoPointAttribute.remove();
            }
            
            entityGeoPointAttributeFactory.create(entityAttribute, entityInstance, entityGeoPointAttributeValue.getLatitude(),
                    entityGeoPointAttributeValue.getLongitude(), entityGeoPointAttributeValue.getElevation(), entityGeoPointAttributeValue.getAltitude(),
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityGeoPointAttribute(EntityGeoPointAttribute entityGeoPointAttribute, BasePK deletedBy) {
        var entityAttribute = entityGeoPointAttribute.getEntityAttribute();
        var entityInstance = entityGeoPointAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityGeoPointAttribute.setThruTime(session.getStartTime());
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

    @Inject
    protected EntityTimeDefaultFactory entityTimeDefaultFactory;

    @Inject
    EntityTimeDefaultTransferCache entityTimeDefaultTransferCache;

    public EntityTimeDefault createEntityTimeDefault(EntityAttribute entityAttribute, Long timeAttribute,
            BasePK createdBy) {
        var entityTimeDefault = entityTimeDefaultFactory.create(entityAttribute,
                timeAttribute, session.getStartTime(), Session.MAX_TIME);

        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityTimeDefault.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return entityTimeDefault;
    }

    public long countEntityTimeDefaultHistory(EntityAttribute entityAttribute) {
        return session.getDslContext()
                .selectCount()
                .from(EntityTimeDefaults)
                .where(EntityTimeDefaults.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public List<EntityTimeDefault> getEntityTimeDefaultHistory(EntityAttribute entityAttribute) {
        var query = session.applyLimit(session.getDslContext()
                .select(EntityTimeDefaults.fields())
                .from(EntityTimeDefaults)
                .where(EntityTimeDefaults.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()))
                .orderBy(EntityTimeDefaults.THRU_TIME),
                EntityTimeDefaultFactory.class);

        return entityTimeDefaultFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }

    private EntityTimeDefault getEntityTimeDefault(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EntityTimeDefaults.fields())
                .from(EntityTimeDefaults)
                .where(EntityTimeDefaults.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityTimeDefaults.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return entityTimeDefaultFactory.getEntityFromQuery(entityPermission, query);
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
        return entityTimeDefaultTransferCache.getEntityTimeDefaultTransfer(userVisit, entityTimeDefault);
    }

    public void updateEntityTimeDefaultFromValue(EntityTimeDefaultValue entityTimeDefaultValue, BasePK updatedBy) {
        if(entityTimeDefaultValue.hasBeenModified()) {
            var entityTimeDefault = entityTimeDefaultFactory.getEntityFromValue(EntityPermission.READ_WRITE, entityTimeDefaultValue);
            var entityAttribute = entityTimeDefault.getEntityAttribute();

            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityTimeDefault.setThruTime(session.getStartTime());
                entityTimeDefault.store();
            } else {
                entityTimeDefault.remove();
            }

            entityTimeDefault = entityTimeDefaultFactory.create(entityAttribute,
                    entityTimeDefaultValue.getTimeAttribute(), session.getStartTime(),
                    Session.MAX_TIME);

            sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityTimeDefault.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteEntityTimeDefault(EntityTimeDefault entityTimeDefault, BasePK deletedBy) {
        var entityAttribute = entityTimeDefault.getEntityAttribute();

        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityTimeDefault.setThruTime(session.getStartTime());
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

    @Inject
    protected EntityTimeAttributeFactory entityTimeAttributeFactory;

    @Inject
    EntityTimeAttributeTransferCache entityTimeAttributeTransferCache;

    public EntityTimeAttribute createEntityTimeAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Long timeAttribute, BasePK createdBy) {
        return createEntityTimeAttribute(entityAttribute.getPrimaryKey(), entityInstance, timeAttribute,
                createdBy);
    }

    public EntityTimeAttribute createEntityTimeAttribute(EntityAttributePK entityAttribute, EntityInstance entityInstance,
            Long timeAttribute, BasePK createdBy) {
        var entityTimeAttribute = entityTimeAttributeFactory.create(entityAttribute,
                entityInstance.getPrimaryKey(), timeAttribute, session.getStartTime(), Session.MAX_TIME);

        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute, EventTypes.CREATE, createdBy);

        return entityTimeAttribute;
    }

    public long countEntityTimeAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return session.getDslContext()
                .selectCount()
                .from(EntityTimeAttributes)
                .where(EntityTimeAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityTimeAttributes.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public List<EntityTimeAttribute> getEntityTimeAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        var query = session.applyLimit(session.getDslContext()
                .select(EntityTimeAttributes.fields())
                .from(EntityTimeAttributes)
                .where(EntityTimeAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityTimeAttributes.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()))
                .orderBy(EntityTimeAttributes.THRU_TIME),
                EntityTimeAttributeFactory.class);

        return entityTimeAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }

    private EntityTimeAttribute getEntityTimeAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EntityTimeAttributes.fields())
                .from(EntityTimeAttributes)
                .where(EntityTimeAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityTimeAttributes.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                        EntityTimeAttributes.THRU_TIME.eq(Session.MAX_TIME));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };
            
        return entityTimeAttributeFactory.getEntityFromQuery(entityPermission, query);
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
        var query = session.getDslContext()
                .select(EntityTimeAttributes.fields())
                .from(EntityTimeAttributes)
                .where(EntityTimeAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityTimeAttributes.THRU_TIME.eq(Session.MAX_TIME))
                .forUpdate();
        
        return entityTimeAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, query);
    }
    
    public List<EntityTimeAttribute> getEntityTimeAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        var query = session.getDslContext()
                .select(EntityTimeAttributes.fields())
                .from(EntityTimeAttributes)
                .where(EntityTimeAttributes.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                        EntityTimeAttributes.THRU_TIME.eq(Session.MAX_TIME))
                .forUpdate();
        
        return entityTimeAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, query);
    }
    
    public EntityTimeAttributeTransfer getEntityTimeAttributeTransfer(UserVisit userVisit, EntityTimeAttribute entityTimeAttribute, EntityInstance entityInstance) {
        return entityTimeAttributeTransferCache.getEntityTimeAttributeTransfer(userVisit, entityTimeAttribute, entityInstance);
    }
    
    public void updateEntityTimeAttributeFromValue(EntityTimeAttributeValue entityTimeAttributeValue, BasePK updatedBy) {
        if(entityTimeAttributeValue.hasBeenModified()) {
            var entityTimeAttribute = entityTimeAttributeFactory.getEntityFromValue(EntityPermission.READ_WRITE, entityTimeAttributeValue);
            var entityAttribute = entityTimeAttribute.getEntityAttribute();
            var entityInstance = entityTimeAttribute.getEntityInstance();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityTimeAttribute.setThruTime(session.getStartTime());
                entityTimeAttribute.store();
            } else {
                entityTimeAttribute.remove();
            }
            
            entityTimeAttributeFactory.create(entityAttribute, entityInstance, entityTimeAttributeValue.getTimeAttribute(), session.getStartTime(),
                    Session.MAX_TIME);
            
            sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityTimeAttribute(EntityTimeAttribute entityTimeAttribute, BasePK deletedBy) {
        var entityAttribute = entityTimeAttribute.getEntityAttribute();
        var entityInstance = entityTimeAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityTimeAttribute.setThruTime(session.getStartTime());
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

    @Inject
    protected EntityBlobAttributeFactory entityBlobAttributeFactory;

    @Inject
    EntityBlobAttributeTransferCache entityBlobAttributeTransferCache;

    public EntityBlobAttribute createEntityBlobAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Language language, ByteArray blobAttribute, MimeType mimeType, BasePK createdBy) {
        var entityBlobAttribute = entityBlobAttributeFactory.create(entityAttribute,
                entityInstance, language, blobAttribute, mimeType, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return entityBlobAttribute;
    }
    
    private EntityBlobAttribute getEntityBlobAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EntityBlobAttributes.fields())
                .from(EntityBlobAttributes)
                .where(EntityBlobAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityBlobAttributes.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                        EntityBlobAttributes.LANGUAGE.eq(language.getPrimaryKey()),
                        EntityBlobAttributes.THRU_TIME.eq(Session.MAX_TIME));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };
            
        return entityBlobAttributeFactory.getEntityFromQuery(entityPermission, query);
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
            entityBlobAttribute = getEntityBlobAttribute(entityAttribute, entityInstance, partyControl.getDefaultLanguage());
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
        var query = session.getDslContext()
                .select(EntityBlobAttributes.fields())
                .from(EntityBlobAttributes)
                .where(EntityBlobAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityBlobAttributes.THRU_TIME.eq(Session.MAX_TIME))
                .forUpdate();
        
        return entityBlobAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, query);
    }
    
    public List<EntityBlobAttribute> getEntityBlobAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        var query = session.getDslContext()
                .select(EntityBlobAttributes.fields())
                .from(EntityBlobAttributes)
                .where(EntityBlobAttributes.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                        EntityBlobAttributes.THRU_TIME.eq(Session.MAX_TIME))
                .forUpdate();
        
        return entityBlobAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, query);
    }
    
    public EntityBlobAttributeTransfer getEntityBlobAttributeTransfer(UserVisit userVisit, EntityBlobAttribute entityBlobAttribute, EntityInstance entityInstance) {
        return entityBlobAttributeTransferCache.getEntityBlobAttributeTransfer(userVisit, entityBlobAttribute, entityInstance);
    }
    
    public void updateEntityBlobAttributeFromValue(EntityBlobAttributeValue entityBlobAttributeValue, BasePK updatedBy) {
        if(entityBlobAttributeValue.hasBeenModified()) {
            var entityBlobAttribute = entityBlobAttributeFactory.getEntityFromValue(EntityPermission.READ_WRITE, entityBlobAttributeValue);
            var entityAttribute = entityBlobAttribute.getEntityAttribute();
            var entityInstance = entityBlobAttribute.getEntityInstance();
            var language = entityBlobAttribute.getLanguage();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityBlobAttribute.setThruTime(session.getStartTime());
                entityBlobAttribute.store();
            } else {
                entityBlobAttribute.remove();
            }
            
            entityBlobAttributeFactory.create(entityAttribute.getPrimaryKey(), entityInstance.getPrimaryKey(), language.getPrimaryKey(),
                    entityBlobAttributeValue.getBlobAttribute(), entityBlobAttributeValue.getMimeTypePK(), session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityBlobAttribute(EntityBlobAttribute entityBlobAttribute, BasePK deletedBy) {
        var entityAttribute = entityBlobAttribute.getEntityAttribute();
        var entityInstance = entityBlobAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityBlobAttribute.setThruTime(session.getStartTime());
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

    @Inject
    protected EntityClobAttributeFactory entityClobAttributeFactory;

    @Inject
    EntityClobAttributeTransferCache entityClobAttributeTransferCache;

    public EntityClobAttribute createEntityClobAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Language language, String clobAttribute, MimeType mimeType, BasePK createdBy) {
        var entityClobAttribute = entityClobAttributeFactory.create(entityAttribute,
                entityInstance, language, clobAttribute, mimeType, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return entityClobAttribute;
    }

    public long countEntityClobAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return session.getDslContext()
                .selectCount()
                .from(EntityClobAttributes)
                .where(EntityClobAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityClobAttributes.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public List<EntityClobAttribute> getEntityClobAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        var query = session.applyLimit(session.getDslContext()
                .select(EntityClobAttributes.fields())
                .from(EntityClobAttributes)
                .where(EntityClobAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityClobAttributes.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()))
                .orderBy(EntityClobAttributes.THRU_TIME),
                EntityClobAttributeFactory.class);

        return entityClobAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }

    private EntityClobAttribute getEntityClobAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EntityClobAttributes.fields())
                .from(EntityClobAttributes)
                .where(EntityClobAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityClobAttributes.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                        EntityClobAttributes.LANGUAGE.eq(language.getPrimaryKey()),
                        EntityClobAttributes.THRU_TIME.eq(Session.MAX_TIME));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };
            
        return entityClobAttributeFactory.getEntityFromQuery(entityPermission, query);
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
            entityClobAttribute = getEntityClobAttribute(entityAttribute, entityInstance, partyControl.getDefaultLanguage());
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
        var query = session.getDslContext()
                .select(EntityClobAttributes.fields())
                .from(EntityClobAttributes)
                .where(EntityClobAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityClobAttributes.THRU_TIME.eq(Session.MAX_TIME))
                .forUpdate();
        
        return entityClobAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, query);
    }
    
    public List<EntityClobAttribute> getEntityClobAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        var query = session.getDslContext()
                .select(EntityClobAttributes.fields())
                .from(EntityClobAttributes)
                .where(EntityClobAttributes.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                        EntityClobAttributes.THRU_TIME.eq(Session.MAX_TIME))
                .forUpdate();
        
        return entityClobAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, query);
    }
    
    public EntityClobAttributeTransfer getEntityClobAttributeTransfer(UserVisit userVisit, EntityClobAttribute entityClobAttribute, EntityInstance entityInstance) {
        return entityClobAttributeTransferCache.getEntityClobAttributeTransfer(userVisit, entityClobAttribute, entityInstance);
    }
    
    public void updateEntityClobAttributeFromValue(EntityClobAttributeValue entityClobAttributeValue, BasePK updatedBy) {
        if(entityClobAttributeValue.hasBeenModified()) {
            var entityClobAttribute = entityClobAttributeFactory.getEntityFromValue(EntityPermission.READ_WRITE, entityClobAttributeValue);
            var entityAttribute = entityClobAttribute.getEntityAttribute();
            var entityInstance = entityClobAttribute.getEntityInstance();
            var language = entityClobAttribute.getLanguage();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityClobAttribute.setThruTime(session.getStartTime());
                entityClobAttribute.store();
            } else {
                entityClobAttribute.remove();
            }
            
            entityClobAttributeFactory.create(entityAttribute.getPrimaryKey(), entityInstance.getPrimaryKey(), language.getPrimaryKey(),
                    entityClobAttributeValue.getClobAttribute(), entityClobAttributeValue.getMimeTypePK(), session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityClobAttribute(EntityClobAttribute entityClobAttribute, BasePK deletedBy) {
        var entityAttribute = entityClobAttribute.getEntityAttribute();
        var entityInstance = entityClobAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityClobAttribute.setThruTime(session.getStartTime());
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

    @Inject
    protected EntityAttributeEntityTypeFactory entityAttributeEntityTypeFactory;

    @Inject
    EntityAttributeEntityTypeTransferCache entityAttributeEntityTypeTransferCache;

    public EntityAttributeEntityType createEntityAttributeEntityType(EntityAttribute entityAttribute, EntityType allowedEntityType, BasePK createdBy) {
        var entityAttributeEntityType = entityAttributeEntityTypeFactory.create(entityAttribute, allowedEntityType,
                session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeEntityType.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return entityAttributeEntityType;
    }
    
    public long countEntityAttributeEntityTypesByEntityAttribute(EntityAttribute entityAttribute) {
        return session.getDslContext()
                .selectCount()
                .from(EntityAttributeEntityTypes)
                .where(EntityAttributeEntityTypes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityAttributeEntityTypes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countEntityAttributeEntityTypesByAllowedEntityType(EntityType allowedEntityType) {
        return session.getDslContext()
                .selectCount()
                .from(EntityAttributeEntityTypes)
                .where(EntityAttributeEntityTypes.ALLOWED_ENTITY_TYPE.eq(allowedEntityType.getPrimaryKey()),
                        EntityAttributeEntityTypes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public boolean entityAttributeEntityTypeExists(EntityAttribute entityAttribute, EntityType allowedEntityType) {
        return session.getDslContext()
                .selectCount()
                .from(EntityAttributeEntityTypes)
                .where(EntityAttributeEntityTypes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityAttributeEntityTypes.ALLOWED_ENTITY_TYPE.eq(allowedEntityType.getPrimaryKey()),
                        EntityAttributeEntityTypes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L) == 1L;
    }

    private EntityAttributeEntityType getEntityAttributeEntityType(EntityAttribute entityAttribute, EntityType allowedEntityType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EntityAttributeEntityTypes.fields())
                .from(EntityAttributeEntityTypes)
                .where(EntityAttributeEntityTypes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityAttributeEntityTypes.ALLOWED_ENTITY_TYPE.eq(allowedEntityType.getPrimaryKey()),
                        EntityAttributeEntityTypes.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return entityAttributeEntityTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public EntityAttributeEntityType getEntityAttributeEntityType(EntityAttribute entityAttribute, EntityType allowedEntityType) {
        return getEntityAttributeEntityType(entityAttribute, allowedEntityType, EntityPermission.READ_ONLY);
    }

    public EntityAttributeEntityType getEntityAttributeEntityTypeForUpdate(EntityAttribute entityAttribute, EntityType allowedEntityType) {
        return getEntityAttributeEntityType(entityAttribute, allowedEntityType, EntityPermission.READ_WRITE);
    }

    private List<EntityAttributeEntityType> getEntityAttributeEntityTypesByEntityAttribute(EntityAttribute entityAttribute, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(EntityAttributeEntityTypes.fields())
                    .from(EntityAttributeEntityTypes)
                    .join(EntityTypes).onKey(ENTITY_ATTRIBUTE_ENTITY_TYPES_ALLOWED_ENTITY_TYPE_FK)
                    .join(EntityTypeDetails).onKey(ENTITY_TYPES_LAST_DETAIL_FK)
                    .join(ComponentVendors).onKey(ENTITY_TYPE_DETAILS_COMPONENT_VENDOR_FK)
                    .join(ComponentVendorDetails).onKey(COMPONENT_VENDORS_LAST_DETAIL_FK)
                    .where(EntityAttributeEntityTypes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                            EntityAttributeEntityTypes.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(EntityTypeDetails.SORT_ORDER, EntityTypeDetails.ENTITY_TYPE_NAME, ComponentVendorDetails.COMPONENT_VENDOR_NAME),
                    EntityAttributeEntityTypeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(EntityAttributeEntityTypes.fields())
                    .from(EntityAttributeEntityTypes)
                    .where(EntityAttributeEntityTypes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                            EntityAttributeEntityTypes.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return entityAttributeEntityTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<EntityAttributeEntityType> getEntityAttributeEntityTypesByEntityAttribute(EntityAttribute entityAttribute) {
        return getEntityAttributeEntityTypesByEntityAttribute(entityAttribute, EntityPermission.READ_ONLY);
    }
    
    public List<EntityAttributeEntityType> getEntityAttributeEntityTypesByEntityAttributeForUpdate(EntityAttribute entityAttribute) {
        return getEntityAttributeEntityTypesByEntityAttribute(entityAttribute, EntityPermission.READ_WRITE);
    }
    
    private List<EntityAttributeEntityType> getEntityAttributeEntityTypesByAllowedEntityType(EntityType allowedEntityType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(EntityAttributeEntityTypes.fields())
                    .from(EntityAttributeEntityTypes)
                    .join(EntityAttributes).onKey(ENTITY_ATTRIBUTE_ENTITY_TYPES_ENTITY_ATTRIBUTE_FK)
                    .join(EntityAttributeDetails).onKey(ENTITY_ATTRIBUTES_LAST_DETAIL_FK)
                    .where(EntityAttributeEntityTypes.ALLOWED_ENTITY_TYPE.eq(allowedEntityType.getPrimaryKey()),
                            EntityAttributeEntityTypes.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(EntityAttributeDetails.SORT_ORDER, EntityAttributeDetails.ENTITY_ATTRIBUTE_NAME),
                    EntityAttributeEntityTypeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(EntityAttributeEntityTypes.fields())
                    .from(EntityAttributeEntityTypes)
                    .where(EntityAttributeEntityTypes.ALLOWED_ENTITY_TYPE.eq(allowedEntityType.getPrimaryKey()),
                            EntityAttributeEntityTypes.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return entityAttributeEntityTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<EntityAttributeEntityType> getEntityAttributeEntityTypesByAllowedEntityType(EntityType allowedEntityType) {
        return getEntityAttributeEntityTypesByAllowedEntityType(allowedEntityType, EntityPermission.READ_ONLY);
    }

    public List<EntityAttributeEntityType> getEntityAttributeEntityTypesByAllowedEntityTypeForUpdate(EntityType allowedEntityType) {
        return getEntityAttributeEntityTypesByAllowedEntityType(allowedEntityType, EntityPermission.READ_WRITE);
    }

    public EntityAttributeEntityTypeTransfer getEntityAttributeEntityTypeTransfer(UserVisit userVisit, EntityAttributeEntityType entityAttributeEntityType, EntityInstance entityInstance) {
        return entityAttributeEntityTypeTransferCache.getEntityAttributeEntityTypeTransfer(userVisit, entityAttributeEntityType, entityInstance);
    }
    
    public List<EntityAttributeEntityTypeTransfer> getEntityAttributeEntityTypeTransfers(UserVisit userVisit, Collection<EntityAttributeEntityType> entityAttributeEntityTypes, EntityInstance entityInstance) {
        List<EntityAttributeEntityTypeTransfer> entityAttributeEntityTypeTransfers = new ArrayList<>(entityAttributeEntityTypes.size());

        entityAttributeEntityTypes.forEach((entityAttributeEntityType) ->
                entityAttributeEntityTypeTransfers.add(entityAttributeEntityTypeTransferCache.getEntityAttributeEntityTypeTransfer(userVisit, entityAttributeEntityType, entityInstance))
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
        entityAttributeEntityType.setThruTime(session.getStartTime());
        
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

    @Inject
    protected EntityEntityAttributeFactory entityEntityAttributeFactory;

    @Inject
    EntityEntityAttributeTransferCache entityEntityAttributeTransferCache;

    public EntityEntityAttribute createEntityEntityAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityInstance entityInstanceAttribute, BasePK createdBy) {
        var entityEntityAttribute = entityEntityAttributeFactory.create(entityAttribute, entityInstance,
                entityInstanceAttribute, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return entityEntityAttribute;
    }

    public long countEntityEntityAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return session.getDslContext()
                .selectCount()
                .from(EntityEntityAttributes)
                .where(EntityEntityAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityEntityAttributes.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public List<EntityEntityAttribute> getEntityEntityAttributeHistory(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        var query = session.applyLimit(session.getDslContext()
                .select(EntityEntityAttributes.fields())
                .from(EntityEntityAttributes)
                .where(EntityEntityAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityEntityAttributes.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()))
                .orderBy(EntityEntityAttributes.THRU_TIME),
                EntityEntityAttributeFactory.class);

        return entityEntityAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }

    private EntityEntityAttribute getEntityEntityAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EntityEntityAttributes.fields())
                .from(EntityEntityAttributes)
                .where(EntityEntityAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityEntityAttributes.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                        EntityEntityAttributes.THRU_TIME.eq(Session.MAX_TIME));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };
            
        return entityEntityAttributeFactory.getEntityFromQuery(entityPermission, query);
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
        var query = session.getDslContext()
                .select(EntityEntityAttributes.fields())
                .from(EntityEntityAttributes)
                .where(EntityEntityAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityEntityAttributes.THRU_TIME.eq(Session.MAX_TIME))
                .forUpdate();
        
        return entityEntityAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, query);
    }
    
    public List<EntityEntityAttribute> getEntityEntityAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        var query = session.getDslContext()
                .select(EntityEntityAttributes.fields())
                .from(EntityEntityAttributes)
                .where(EntityEntityAttributes.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                        EntityEntityAttributes.THRU_TIME.eq(Session.MAX_TIME))
                .forUpdate();
        
        return entityEntityAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, query);
    }
    
    public List<EntityEntityAttribute> getEntityEntityAttributesByEntityInstanceAttributeForUpdate(EntityInstance entityInstanceAttribute) {
        var query = session.getDslContext()
                .select(EntityEntityAttributes.fields())
                .from(EntityEntityAttributes)
                .where(EntityEntityAttributes.ENTITY_INSTANCE_ATTRIBUTE.eq(entityInstanceAttribute.getPrimaryKey()),
                        EntityEntityAttributes.THRU_TIME.eq(Session.MAX_TIME))
                .forUpdate();
        
        return entityEntityAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, query);
    }
    
    public EntityEntityAttributeTransfer getEntityEntityAttributeTransfer(UserVisit userVisit, EntityEntityAttribute entityEntityAttribute, EntityInstance entityInstance) {
        return entityEntityAttributeTransferCache.getEntityEntityAttributeTransfer(userVisit, entityEntityAttribute, entityInstance);
    }
    
    public void updateEntityEntityAttributeFromValue(EntityEntityAttributeValue entityEntityAttributeValue, BasePK updatedBy) {
        if(entityEntityAttributeValue.hasBeenModified()) {
            var entityEntityAttribute = entityEntityAttributeFactory.getEntityFromValue(EntityPermission.READ_WRITE, entityEntityAttributeValue);
            var entityAttribute = entityEntityAttribute.getEntityAttribute();
            var entityInstance = entityEntityAttribute.getEntityInstance();
            
            if(entityAttribute.getLastDetail().getTrackRevisions()) {
                entityEntityAttribute.setThruTime(session.getStartTime());
                entityEntityAttribute.store();
            } else {
                entityEntityAttribute.remove();
            }
            
            entityEntityAttributeFactory.create(entityAttribute.getPrimaryKey(), entityInstance.getPrimaryKey(),
                    entityEntityAttributeValue.getEntityInstanceAttributePK(), session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteEntityEntityAttribute(EntityEntityAttribute entityEntityAttribute, BasePK deletedBy) {
        var entityAttribute = entityEntityAttribute.getEntityAttribute();
        var entityInstance = entityEntityAttribute.getEntityInstance();
        
        if(entityAttribute.getLastDetail().getTrackRevisions()) {
            entityEntityAttribute.setThruTime(session.getStartTime());
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

    @Inject
    protected EntityCollectionAttributeFactory entityCollectionAttributeFactory;

    @Inject
    EntityCollectionAttributeTransferCache entityCollectionAttributeTransferCache;

    public EntityCollectionAttribute createEntityCollectionAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityInstance entityInstanceAttribute, BasePK createdBy) {
        var entityCollectionAttribute = entityCollectionAttributeFactory.create(entityAttribute, entityInstance,
                entityInstanceAttribute, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(entityInstance, EventTypes.MODIFY, entityAttribute.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return entityCollectionAttribute;
    }
    
    public List<EntityCollectionAttribute> getEntityCollectionAttributes(EntityAttribute entityAttribute, EntityInstance entityInstance) {
        var query = session.applyLimit(session.getDslContext()
                .select(EntityCollectionAttributes.fields())
                .from(EntityCollectionAttributes)
                .join(EntityInstances).onKey(ENTITY_COLLECTION_ATTRIBUTES_ENTITY_INSTANCE_ATTRIBUTE_FK)
                .join(EntityTypes).onKey(ENTITY_INSTANCES_ENTITY_TYPE_FK)
                .join(EntityTypeDetails).onKey(ENTITY_TYPES_LAST_DETAIL_FK)
                .join(ComponentVendors).onKey(ENTITY_TYPE_DETAILS_COMPONENT_VENDOR_FK)
                .join(ComponentVendorDetails).onKey(COMPONENT_VENDORS_LAST_DETAIL_FK)
                .where(EntityCollectionAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityCollectionAttributes.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                        EntityCollectionAttributes.THRU_TIME.eq(Session.MAX_TIME))
                .orderBy(ComponentVendorDetails.COMPONENT_VENDOR_NAME, EntityTypeDetails.SORT_ORDER, EntityTypeDetails.ENTITY_TYPE_NAME, EntityInstances.ENTITY_UNIQUE),
                EntityCollectionAttributeFactory.class);

        return entityCollectionAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    private EntityCollectionAttribute getEntityCollectionAttribute(EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityInstance entityInstanceAttribute, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EntityCollectionAttributes.fields())
                .from(EntityCollectionAttributes)
                .where(EntityCollectionAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityCollectionAttributes.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                        EntityCollectionAttributes.ENTITY_INSTANCE_ATTRIBUTE.eq(entityInstanceAttribute.getPrimaryKey()),
                        EntityCollectionAttributes.THRU_TIME.eq(Session.MAX_TIME));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };
            
        return entityCollectionAttributeFactory.getEntityFromQuery(entityPermission, query);
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
        var query = session.getDslContext()
                .select(EntityCollectionAttributes.fields())
                .from(EntityCollectionAttributes)
                .where(EntityCollectionAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityCollectionAttributes.THRU_TIME.eq(Session.MAX_TIME))
                .forUpdate();
        
        return entityCollectionAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, query);
    }
    
    public List<EntityCollectionAttribute> getEntityCollectionAttributesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        var query = session.getDslContext()
                .select(EntityCollectionAttributes.fields())
                .from(EntityCollectionAttributes)
                .where(EntityCollectionAttributes.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                        EntityCollectionAttributes.THRU_TIME.eq(Session.MAX_TIME))
                .forUpdate();
        
        return entityCollectionAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, query);
    }
    
    public List<EntityCollectionAttribute> getEntityCollectionAttributesByEntityInstanceAttributeForUpdate(EntityInstance entityInstanceAttribute) {
        var query = session.getDslContext()
                .select(EntityCollectionAttributes.fields())
                .from(EntityCollectionAttributes)
                .where(EntityCollectionAttributes.ENTITY_INSTANCE_ATTRIBUTE.eq(entityInstanceAttribute.getPrimaryKey()),
                        EntityCollectionAttributes.THRU_TIME.eq(Session.MAX_TIME))
                .forUpdate();

        return entityCollectionAttributeFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, query);
    }
    
    public EntityCollectionAttributeTransfer getEntityCollectionAttributeTransfer(UserVisit userVisit, EntityCollectionAttribute entityCollectionAttribute, EntityInstance entityInstance) {
        return entityCollectionAttributeTransferCache.getEntityCollectionAttributeTransfer(userVisit, entityCollectionAttribute, entityInstance);
    }
    
    public List<EntityCollectionAttributeTransfer> getEntityCollectionAttributeTransfers(UserVisit userVisit, Collection<EntityCollectionAttribute> entityCollectionAttributes, EntityInstance entityInstance) {
        List<EntityCollectionAttributeTransfer> entityCollectionAttributeTransfers = new ArrayList<>(entityCollectionAttributes.size());
        
        entityCollectionAttributes.forEach((entityCollectionAttribute) ->
                entityCollectionAttributeTransfers.add(entityCollectionAttributeTransferCache.getEntityCollectionAttributeTransfer(userVisit, entityCollectionAttribute, entityInstance))
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
            entityCollectionAttribute.setThruTime(session.getStartTime());
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

}
