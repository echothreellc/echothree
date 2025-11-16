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

package com.echothree.model.control.core.server.transfer;

import com.echothree.util.server.transfer.BaseTransferCaches;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class CoreTransferCaches
        extends BaseTransferCaches {
    
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

    /** Creates a new instance of CoreTransferCaches */
    protected CoreTransferCaches() {
        super();
    }
    
    public ComponentVendorTransferCache getComponentVendorTransferCache() {
        return componentVendorTransferCache;
    }

    public EntityTypeTransferCache getEntityTypeTransferCache() {
        return entityTypeTransferCache;
    }
    
    public EntityTypeDescriptionTransferCache getEntityTypeDescriptionTransferCache() {
        return entityTypeDescriptionTransferCache;
    }
    
    public CommandTransferCache getCommandTransferCache() {
        return commandTransferCache;
    }
    
    public CommandDescriptionTransferCache getCommandDescriptionTransferCache() {
        return commandDescriptionTransferCache;
    }
    
    public CommandMessageTransferCache getCommandMessageTransferCache() {
        return commandMessageTransferCache;
    }
    
    public CommandMessageTranslationTransferCache getCommandMessageTranslationTransferCache() {
        return commandMessageTranslationTransferCache;
    }

    public EntityAliasTransferCache getEntityAliasTransferCache() {
        return entityAliasTransferCache;
    }

    public EntityAliasTypeTransferCache getEntityAliasTypeTransferCache() {
        return entityAliasTypeTransferCache;
    }

    public EntityAliasTypeDescriptionTransferCache getEntityAliasTypeDescriptionTransferCache() {
        return entityAliasTypeDescriptionTransferCache;
    }

    public EntityAttributeTransferCache getEntityAttributeTransferCache() {
        return entityAttributeTransferCache;
    }

    public EntityAttributeDescriptionTransferCache getEntityAttributeDescriptionTransferCache() {
        return entityAttributeDescriptionTransferCache;
    }

    public EntityAttributeEntityAttributeGroupTransferCache getEntityAttributeEntityAttributeGroupTransferCache() {
        return entityAttributeEntityAttributeGroupTransferCache;
    }
    
    public EntityAttributeGroupTransferCache getEntityAttributeGroupTransferCache() {
        return entityAttributeGroupTransferCache;
    }
    
    public EntityAttributeGroupDescriptionTransferCache getEntityAttributeGroupDescriptionTransferCache() {
        return entityAttributeGroupDescriptionTransferCache;
    }
    
    public EntityListItemTransferCache getEntityListItemTransferCache() {
        return entityListItemTransferCache;
    }
    
    public EntityListItemDescriptionTransferCache getEntityListItemDescriptionTransferCache() {
        return entityListItemDescriptionTransferCache;
    }
    
    public EntityIntegerRangeTransferCache getEntityIntegerRangeTransferCache() {
        return entityIntegerRangeTransferCache;
    }
    
    public EntityIntegerRangeDescriptionTransferCache getEntityIntegerRangeDescriptionTransferCache() {
        return entityIntegerRangeDescriptionTransferCache;
    }
    
    public EntityLongRangeTransferCache getEntityLongRangeTransferCache() {
        return entityLongRangeTransferCache;
    }
    
    public EntityLongRangeDescriptionTransferCache getEntityLongRangeDescriptionTransferCache() {
        return entityLongRangeDescriptionTransferCache;
    }
    
    public EntityAttributeTypeTransferCache getEntityAttributeTypeTransferCache() {
        return entityAttributeTypeTransferCache;
    }
    
    public EventTypeTransferCache getEventTypeTransferCache() {
        return eventTypeTransferCache;
    }
    
    public MimeTypeTransferCache getMimeTypeTransferCache() {
        return mimeTypeTransferCache;
    }

    public MimeTypeDescriptionTransferCache getMimeTypeDescriptionTransferCache() {
        return mimeTypeDescriptionTransferCache;
    }

    public EntityTimeTransferCache getEntityTimeTransferCache() {
        return entityTimeTransferCache;
    }

    public EntityVisitTransferCache getEntityVisitTransferCache() {
        return entityVisitTransferCache;
    }

    public EntityInstanceTransferCache getEntityInstanceTransferCache() {
        return entityInstanceTransferCache;
    }
    
    public EventGroupTransferCache getEventGroupTransferCache() {
        return eventGroupTransferCache;
    }
    
    public EventTransferCache getEventTransferCache() {
        return eventTransferCache;
    }
    
    public EntityLockTransferCache getEntityLockTransferCache() {
        return entityLockTransferCache;
    }
    
    public MimeTypeUsageTypeTransferCache getMimeTypeUsageTypeTransferCache() {
        return mimeTypeUsageTypeTransferCache;
    }
    
    public MimeTypeUsageTransferCache getMimeTypeUsageTransferCache() {
        return mimeTypeUsageTransferCache;
    }
    
    public ServerTransferCache getServerTransferCache() {
        return serverTransferCache;
    }

    public ServerDescriptionTransferCache getServerDescriptionTransferCache() {
        return serverDescriptionTransferCache;
    }

    public ServerServiceTransferCache getServerServiceTransferCache() {
        return serverServiceTransferCache;
    }

    public MimeTypeFileExtensionTransferCache getMimeTypeFileExtensionTransferCache() {
        return mimeTypeFileExtensionTransferCache;
    }
    
    public EntityBlobAttributeTransferCache getEntityBlobAttributeTransferCache() {
        return entityBlobAttributeTransferCache;
    }

    public EntityBooleanDefaultTransferCache getEntityBooleanDefaultTransferCache() {
        return entityBooleanDefaultTransferCache;
    }

    public EntityBooleanAttributeTransferCache getEntityBooleanAttributeTransferCache() {
        return entityBooleanAttributeTransferCache;
    }

    public EntityClobAttributeTransferCache getEntityClobAttributeTransferCache() {
        return entityClobAttributeTransferCache;
    }

    public EntityDateDefaultTransferCache getEntityDateDefaultTransferCache() {
        return entityDateDefaultTransferCache;
    }

    public EntityDateAttributeTransferCache getEntityDateAttributeTransferCache() {
        return entityDateAttributeTransferCache;
    }

    public EntityIntegerDefaultTransferCache getEntityIntegerDefaultTransferCache() {
        return entityIntegerDefaultTransferCache;
    }

    public EntityIntegerAttributeTransferCache getEntityIntegerAttributeTransferCache() {
        return entityIntegerAttributeTransferCache;
    }

    public EntityListItemDefaultTransferCache getEntityListItemDefaultTransferCache() {
        return entityListItemDefaultTransferCache;
    }

    public EntityListItemAttributeTransferCache getEntityListItemAttributeTransferCache() {
        return entityListItemAttributeTransferCache;
    }

    public EntityLongDefaultTransferCache getEntityLongDefaultTransferCache() {
        return entityLongDefaultTransferCache;
    }

    public EntityLongAttributeTransferCache getEntityLongAttributeTransferCache() {
        return entityLongAttributeTransferCache;
    }

    public EntityMultipleListItemDefaultTransferCache getEntityMultipleListItemDefaultTransferCache() {
        return entityMultipleListItemDefaultTransferCache;
    }

    public EntityMultipleListItemAttributeTransferCache getEntityMultipleListItemAttributeTransferCache() {
        return entityMultipleListItemAttributeTransferCache;
    }

    public EntityNameAttributeTransferCache getEntityNameAttributeTransferCache() {
        return entityNameAttributeTransferCache;
    }

    public EntityGeoPointDefaultTransferCache getEntityGeoPointDefaultTransferCache() {
        return entityGeoPointDefaultTransferCache;
    }

    public EntityGeoPointAttributeTransferCache getEntityGeoPointAttributeTransferCache() {
        return entityGeoPointAttributeTransferCache;
    }

    public EntityStringDefaultTransferCache getEntityStringDefaultTransferCache() {
        return entityStringDefaultTransferCache;
    }

    public EntityStringAttributeTransferCache getEntityStringAttributeTransferCache() {
        return entityStringAttributeTransferCache;
    }

    public EntityTimeDefaultTransferCache getEntityTimeDefaultTransferCache() {
        return entityTimeDefaultTransferCache;
    }

    public EntityTimeAttributeTransferCache getEntityTimeAttributeTransferCache() {
        return entityTimeAttributeTransferCache;
    }

    public EntityAttributeEntityTypeTransferCache getEntityAttributeEntityTypeTransferCache() {
        return entityAttributeEntityTypeTransferCache;
    }
    
    public EntityEntityAttributeTransferCache getEntityEntityAttributeTransferCache() {
        return entityEntityAttributeTransferCache;
    }
    
    public EntityCollectionAttributeTransferCache getEntityCollectionAttributeTransferCache() {
        return entityCollectionAttributeTransferCache;
    }
    
    public CommandMessageTypeTransferCache getCommandMessageTypeTransferCache() {
        return commandMessageTypeTransferCache;
    }
    
    public CommandMessageTypeDescriptionTransferCache getCommandMessageTypeDescriptionTransferCache() {
        return commandMessageTypeDescriptionTransferCache;
    }
    
    public BaseEncryptionKeyTransferCache getBaseEncryptionKeyTransferCache() {
        return baseEncryptionKeyTransferCache;
    }
    
    public ProtocolTransferCache getProtocolTransferCache() {
        return protocolTransferCache;
    }

    public ProtocolDescriptionTransferCache getProtocolDescriptionTransferCache() {
        return protocolDescriptionTransferCache;
    }

    public ServiceTransferCache getServiceTransferCache() {
        return serviceTransferCache;
    }

    public ServiceDescriptionTransferCache getServiceDescriptionTransferCache() {
        return serviceDescriptionTransferCache;
    }

    public CacheEntryTransferCache getCacheEntryTransferCache() {
        return cacheEntryTransferCache;
    }

    public CacheEntryDependencyTransferCache getCacheEntryDependencyTransferCache() {
        return cacheEntryDependencyTransferCache;
    }

    public ApplicationTransferCache getApplicationTransferCache() {
        return applicationTransferCache;
    }

    public ApplicationDescriptionTransferCache getApplicationDescriptionTransferCache() {
        return applicationDescriptionTransferCache;
    }

    public EditorTransferCache getEditorTransferCache() {
        return editorTransferCache;
    }

    public EditorDescriptionTransferCache getEditorDescriptionTransferCache() {
        return editorDescriptionTransferCache;
    }

    public ApplicationEditorTransferCache getApplicationEditorTransferCache() {
        return applicationEditorTransferCache;
    }

    public ApplicationEditorUseTransferCache getApplicationEditorUseTransferCache() {
        return applicationEditorUseTransferCache;
    }

    public ApplicationEditorUseDescriptionTransferCache getApplicationEditorUseDescriptionTransferCache() {
        return applicationEditorUseDescriptionTransferCache;
    }

    public ColorTransferCache getColorTransferCache() {
        return colorTransferCache;
    }

    public ColorDescriptionTransferCache getColorDescriptionTransferCache() {
        return colorDescriptionTransferCache;
    }

    public FontStyleTransferCache getFontStyleTransferCache() {
        return fontStyleTransferCache;
    }

    public FontStyleDescriptionTransferCache getFontStyleDescriptionTransferCache() {
        return fontStyleDescriptionTransferCache;
    }

    public FontWeightTransferCache getFontWeightTransferCache() {
        return fontWeightTransferCache;
    }

    public FontWeightDescriptionTransferCache getFontWeightDescriptionTransferCache() {
        return fontWeightDescriptionTransferCache;
    }

    public TextDecorationTransferCache getTextDecorationTransferCache() {
        return textDecorationTransferCache;
    }

    public TextDecorationDescriptionTransferCache getTextDecorationDescriptionTransferCache() {
        return textDecorationDescriptionTransferCache;
    }

    public TextTransformationTransferCache getTextTransformationTransferCache() {
        return textTransformationTransferCache;
    }

    public TextTransformationDescriptionTransferCache getTextTransformationDescriptionTransferCache() {
        return textTransformationDescriptionTransferCache;
    }

    public AppearanceTransferCache getAppearanceTransferCache() {
        return appearanceTransferCache;
    }

    public AppearanceDescriptionTransferCache getAppearanceDescriptionTransferCache() {
        return appearanceDescriptionTransferCache;
    }

    public AppearanceTextDecorationTransferCache getAppearanceTextDecorationTransferCache() {
        return appearanceTextDecorationTransferCache;
    }

    public AppearanceTextTransformationTransferCache getAppearanceTextTransformationTransferCache() {
        return appearanceTextTransformationTransferCache;
    }

    public EntityAppearanceTransferCache getEntityAppearanceTransferCache() {
        return entityAppearanceTransferCache;
    }
    
}
