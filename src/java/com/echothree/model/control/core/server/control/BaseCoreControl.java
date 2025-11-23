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

import com.echothree.model.control.core.server.transfer.AppearanceDescriptionTransferCache;
import com.echothree.model.control.core.server.transfer.AppearanceTextDecorationTransferCache;
import com.echothree.model.control.core.server.transfer.AppearanceTextTransformationTransferCache;
import com.echothree.model.control.core.server.transfer.AppearanceTransferCache;
import com.echothree.model.control.core.server.transfer.ApplicationDescriptionTransferCache;
import com.echothree.model.control.core.server.transfer.ApplicationEditorTransferCache;
import com.echothree.model.control.core.server.transfer.ApplicationEditorUseDescriptionTransferCache;
import com.echothree.model.control.core.server.transfer.ApplicationEditorUseTransferCache;
import com.echothree.model.control.core.server.transfer.ApplicationTransferCache;
import com.echothree.model.control.core.server.transfer.BaseEncryptionKeyTransferCache;
import com.echothree.model.control.core.server.transfer.CacheEntryDependencyTransferCache;
import com.echothree.model.control.core.server.transfer.CacheEntryTransferCache;
import com.echothree.model.control.core.server.transfer.ColorDescriptionTransferCache;
import com.echothree.model.control.core.server.transfer.ColorTransferCache;
import com.echothree.model.control.core.server.transfer.CommandDescriptionTransferCache;
import com.echothree.model.control.core.server.transfer.CommandMessageTransferCache;
import com.echothree.model.control.core.server.transfer.CommandMessageTranslationTransferCache;
import com.echothree.model.control.core.server.transfer.CommandMessageTypeDescriptionTransferCache;
import com.echothree.model.control.core.server.transfer.CommandMessageTypeTransferCache;
import com.echothree.model.control.core.server.transfer.CommandTransferCache;
import com.echothree.model.control.core.server.transfer.ComponentVendorTransferCache;
import com.echothree.model.control.core.server.transfer.EditorDescriptionTransferCache;
import com.echothree.model.control.core.server.transfer.EditorTransferCache;
import com.echothree.model.control.core.server.transfer.EntityAliasTransferCache;
import com.echothree.model.control.core.server.transfer.EntityAliasTypeDescriptionTransferCache;
import com.echothree.model.control.core.server.transfer.EntityAliasTypeTransferCache;
import com.echothree.model.control.core.server.transfer.EntityAppearanceTransferCache;
import com.echothree.model.control.core.server.transfer.EntityAttributeDescriptionTransferCache;
import com.echothree.model.control.core.server.transfer.EntityAttributeEntityAttributeGroupTransferCache;
import com.echothree.model.control.core.server.transfer.EntityAttributeEntityTypeTransferCache;
import com.echothree.model.control.core.server.transfer.EntityAttributeGroupDescriptionTransferCache;
import com.echothree.model.control.core.server.transfer.EntityAttributeGroupTransferCache;
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
import com.echothree.model.control.core.server.transfer.EntityInstanceTransferCache;
import com.echothree.model.control.core.server.transfer.EntityIntegerAttributeTransferCache;
import com.echothree.model.control.core.server.transfer.EntityIntegerDefaultTransferCache;
import com.echothree.model.control.core.server.transfer.EntityIntegerRangeDescriptionTransferCache;
import com.echothree.model.control.core.server.transfer.EntityIntegerRangeTransferCache;
import com.echothree.model.control.core.server.transfer.EntityListItemAttributeTransferCache;
import com.echothree.model.control.core.server.transfer.EntityListItemDefaultTransferCache;
import com.echothree.model.control.core.server.transfer.EntityListItemDescriptionTransferCache;
import com.echothree.model.control.core.server.transfer.EntityListItemTransferCache;
import com.echothree.model.control.core.server.transfer.EntityLockTransferCache;
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
import com.echothree.model.control.core.server.transfer.EntityTimeTransferCache;
import com.echothree.model.control.core.server.transfer.EntityTypeDescriptionTransferCache;
import com.echothree.model.control.core.server.transfer.EntityTypeTransferCache;
import com.echothree.model.control.core.server.transfer.EntityVisitTransferCache;
import com.echothree.model.control.core.server.transfer.EventGroupTransferCache;
import com.echothree.model.control.core.server.transfer.EventTransferCache;
import com.echothree.model.control.core.server.transfer.EventTypeTransferCache;
import com.echothree.model.control.core.server.transfer.FontStyleDescriptionTransferCache;
import com.echothree.model.control.core.server.transfer.FontStyleTransferCache;
import com.echothree.model.control.core.server.transfer.FontWeightDescriptionTransferCache;
import com.echothree.model.control.core.server.transfer.FontWeightTransferCache;
import com.echothree.model.control.core.server.transfer.MimeTypeDescriptionTransferCache;
import com.echothree.model.control.core.server.transfer.MimeTypeFileExtensionTransferCache;
import com.echothree.model.control.core.server.transfer.MimeTypeTransferCache;
import com.echothree.model.control.core.server.transfer.MimeTypeUsageTransferCache;
import com.echothree.model.control.core.server.transfer.MimeTypeUsageTypeTransferCache;
import com.echothree.model.control.core.server.transfer.ProtocolDescriptionTransferCache;
import com.echothree.model.control.core.server.transfer.ProtocolTransferCache;
import com.echothree.model.control.core.server.transfer.ServerDescriptionTransferCache;
import com.echothree.model.control.core.server.transfer.ServerServiceTransferCache;
import com.echothree.model.control.core.server.transfer.ServerTransferCache;
import com.echothree.model.control.core.server.transfer.ServiceDescriptionTransferCache;
import com.echothree.model.control.core.server.transfer.ServiceTransferCache;
import com.echothree.model.control.core.server.transfer.TextDecorationDescriptionTransferCache;
import com.echothree.model.control.core.server.transfer.TextDecorationTransferCache;
import com.echothree.model.control.core.server.transfer.TextTransformationDescriptionTransferCache;
import com.echothree.model.control.core.server.transfer.TextTransformationTransferCache;
import com.echothree.util.server.control.BaseModelControl;
import javax.inject.Inject;

public abstract class BaseCoreControl
        extends BaseModelControl {

    /** Creates a new instance of BaseCoreControl */
    protected BaseCoreControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Core Transfer Caches
    // --------------------------------------------------------------------------------

    @Inject
    ComponentVendorTransferCache componentVendorTransferCache;

    @Inject
    EntityTypeTransferCache entityTypeTransferCache;

    @Inject
    EntityTypeDescriptionTransferCache entityTypeDescriptionTransferCache;

    @Inject
    CommandTransferCache commandTransferCache;

    @Inject
    CommandDescriptionTransferCache commandDescriptionTransferCache;

    @Inject
    CommandMessageTransferCache commandMessageTransferCache;

    @Inject
    CommandMessageTranslationTransferCache commandMessageTranslationTransferCache;

    @Inject
    EntityAliasTypeTransferCache entityAliasTypeTransferCache;

    @Inject
    EntityAliasTypeDescriptionTransferCache entityAliasTypeDescriptionTransferCache;

    @Inject
    EntityAliasTransferCache entityAliasTransferCache;

    @Inject
    EntityAttributeTransferCache entityAttributeTransferCache;

    @Inject
    EntityAttributeDescriptionTransferCache entityAttributeDescriptionTransferCache;

    @Inject
    EntityAttributeEntityAttributeGroupTransferCache entityAttributeEntityAttributeGroupTransferCache;

    @Inject
    EntityAttributeGroupTransferCache entityAttributeGroupTransferCache;

    @Inject
    EntityAttributeGroupDescriptionTransferCache entityAttributeGroupDescriptionTransferCache;

    @Inject
    EntityListItemTransferCache entityListItemTransferCache;

    @Inject
    EntityListItemDescriptionTransferCache entityListItemDescriptionTransferCache;

    @Inject
    EntityIntegerRangeTransferCache entityIntegerRangeTransferCache;

    @Inject
    EntityIntegerRangeDescriptionTransferCache entityIntegerRangeDescriptionTransferCache;

    @Inject
    EntityLongRangeTransferCache entityLongRangeTransferCache;

    @Inject
    EntityLongRangeDescriptionTransferCache entityLongRangeDescriptionTransferCache;

    @Inject
    EntityAttributeTypeTransferCache entityAttributeTypeTransferCache;

    @Inject
    EventTypeTransferCache eventTypeTransferCache;

    @Inject
    MimeTypeTransferCache mimeTypeTransferCache;

    @Inject
    MimeTypeDescriptionTransferCache mimeTypeDescriptionTransferCache;

    @Inject
    EntityTimeTransferCache entityTimeTransferCache;

    @Inject
    EntityVisitTransferCache entityVisitTransferCache;

    @Inject
    EntityInstanceTransferCache entityInstanceTransferCache;

    @Inject
    EventGroupTransferCache eventGroupTransferCache;

    @Inject
    EventTransferCache eventTransferCache;

    @Inject
    EntityLockTransferCache entityLockTransferCache;

    @Inject
    MimeTypeUsageTypeTransferCache mimeTypeUsageTypeTransferCache;

    @Inject
    MimeTypeUsageTransferCache mimeTypeUsageTransferCache;

    @Inject
    ServerTransferCache serverTransferCache;

    @Inject
    ServerDescriptionTransferCache serverDescriptionTransferCache;

    @Inject
    ServerServiceTransferCache serverServiceTransferCache;

    @Inject
    MimeTypeFileExtensionTransferCache mimeTypeFileExtensionTransferCache;

    @Inject
    EntityBlobAttributeTransferCache entityBlobAttributeTransferCache;

    @Inject
    EntityBooleanDefaultTransferCache entityBooleanDefaultTransferCache;

    @Inject
    EntityBooleanAttributeTransferCache entityBooleanAttributeTransferCache;

    @Inject
    EntityClobAttributeTransferCache entityClobAttributeTransferCache;

    @Inject
    EntityDateDefaultTransferCache entityDateDefaultTransferCache;

    @Inject
    EntityDateAttributeTransferCache entityDateAttributeTransferCache;

    @Inject
    EntityIntegerDefaultTransferCache entityIntegerDefaultTransferCache;

    @Inject
    EntityIntegerAttributeTransferCache entityIntegerAttributeTransferCache;

    @Inject
    EntityListItemDefaultTransferCache entityListItemDefaultTransferCache;

    @Inject
    EntityListItemAttributeTransferCache entityListItemAttributeTransferCache;

    @Inject
    EntityLongDefaultTransferCache entityLongDefaultTransferCache;

    @Inject
    EntityLongAttributeTransferCache entityLongAttributeTransferCache;

    @Inject
    EntityMultipleListItemDefaultTransferCache entityMultipleListItemDefaultTransferCache;

    @Inject
    EntityMultipleListItemAttributeTransferCache entityMultipleListItemAttributeTransferCache;

    @Inject
    EntityNameAttributeTransferCache entityNameAttributeTransferCache;

    @Inject
    EntityGeoPointDefaultTransferCache entityGeoPointDefaultTransferCache;

    @Inject
    EntityGeoPointAttributeTransferCache entityGeoPointAttributeTransferCache;

    @Inject
    EntityStringDefaultTransferCache entityStringDefaultTransferCache;

    @Inject
    EntityStringAttributeTransferCache entityStringAttributeTransferCache;

    @Inject
    EntityTimeDefaultTransferCache entityTimeDefaultTransferCache;

    @Inject
    EntityTimeAttributeTransferCache entityTimeAttributeTransferCache;

    @Inject
    EntityAttributeEntityTypeTransferCache entityAttributeEntityTypeTransferCache;

    @Inject
    EntityEntityAttributeTransferCache entityEntityAttributeTransferCache;

    @Inject
    EntityCollectionAttributeTransferCache entityCollectionAttributeTransferCache;

    @Inject
    CommandMessageTypeTransferCache commandMessageTypeTransferCache;

    @Inject
    CommandMessageTypeDescriptionTransferCache commandMessageTypeDescriptionTransferCache;

    @Inject
    BaseEncryptionKeyTransferCache baseEncryptionKeyTransferCache;

    @Inject
    ProtocolTransferCache protocolTransferCache;

    @Inject
    ProtocolDescriptionTransferCache protocolDescriptionTransferCache;

    @Inject
    ServiceTransferCache serviceTransferCache;

    @Inject
    ServiceDescriptionTransferCache serviceDescriptionTransferCache;

    @Inject
    CacheEntryTransferCache cacheEntryTransferCache;

    @Inject
    CacheEntryDependencyTransferCache cacheEntryDependencyTransferCache;

    @Inject
    ApplicationTransferCache applicationTransferCache;

    @Inject
    ApplicationDescriptionTransferCache applicationDescriptionTransferCache;

    @Inject
    EditorTransferCache editorTransferCache;

    @Inject
    EditorDescriptionTransferCache editorDescriptionTransferCache;

    @Inject
    ApplicationEditorTransferCache applicationEditorTransferCache;

    @Inject
    ApplicationEditorUseTransferCache applicationEditorUseTransferCache;

    @Inject
    ApplicationEditorUseDescriptionTransferCache applicationEditorUseDescriptionTransferCache;

    @Inject
    ColorTransferCache colorTransferCache;

    @Inject
    ColorDescriptionTransferCache colorDescriptionTransferCache;

    @Inject
    FontStyleTransferCache fontStyleTransferCache;

    @Inject
    FontStyleDescriptionTransferCache fontStyleDescriptionTransferCache;

    @Inject
    FontWeightTransferCache fontWeightTransferCache;

    @Inject
    FontWeightDescriptionTransferCache fontWeightDescriptionTransferCache;

    @Inject
    TextDecorationTransferCache textDecorationTransferCache;

    @Inject
    TextDecorationDescriptionTransferCache textDecorationDescriptionTransferCache;

    @Inject
    TextTransformationTransferCache textTransformationTransferCache;

    @Inject
    TextTransformationDescriptionTransferCache textTransformationDescriptionTransferCache;

    @Inject
    AppearanceTransferCache appearanceTransferCache;

    @Inject
    AppearanceDescriptionTransferCache appearanceDescriptionTransferCache;

    @Inject
    AppearanceTextDecorationTransferCache appearanceTextDecorationTransferCache;

    @Inject
    AppearanceTextTransformationTransferCache appearanceTextTransformationTransferCache;

    @Inject
    EntityAppearanceTransferCache entityAppearanceTransferCache;

 }
