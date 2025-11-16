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
import javax.enterprise.inject.spi.CDI;

public class CoreTransferCaches
        extends BaseTransferCaches {
    
    protected ComponentVendorTransferCache componentVendorTransferCache;
    protected EntityTypeTransferCache entityTypeTransferCache;
    protected EntityTypeDescriptionTransferCache entityTypeDescriptionTransferCache;
    protected CommandTransferCache commandTransferCache;
    protected CommandDescriptionTransferCache commandDescriptionTransferCache;
    protected CommandMessageTransferCache commandMessageTransferCache;
    protected CommandMessageTranslationTransferCache commandMessageTranslationTransferCache;
    protected EntityAliasTypeTransferCache entityAliasTypeTransferCache;
    protected EntityAliasTypeDescriptionTransferCache entityAliasTypeDescriptionTransferCache;
    protected EntityAliasTransferCache entityAliasTransferCache;
    protected EntityAttributeTransferCache entityAttributeTransferCache;
    protected EntityAttributeDescriptionTransferCache entityAttributeDescriptionTransferCache;
    protected EntityAttributeEntityAttributeGroupTransferCache entityAttributeEntityAttributeGroupTransferCache;
    protected EntityAttributeGroupTransferCache entityAttributeGroupTransferCache;
    protected EntityAttributeGroupDescriptionTransferCache entityAttributeGroupDescriptionTransferCache;
    protected EntityListItemTransferCache entityListItemTransferCache;
    protected EntityListItemDescriptionTransferCache entityListItemDescriptionTransferCache;
    protected EntityIntegerRangeTransferCache entityIntegerRangeTransferCache;
    protected EntityIntegerRangeDescriptionTransferCache entityIntegerRangeDescriptionTransferCache;
    protected EntityLongRangeTransferCache entityLongRangeTransferCache;
    protected EntityLongRangeDescriptionTransferCache entityLongRangeDescriptionTransferCache;
    protected EntityAttributeTypeTransferCache entityAttributeTypeTransferCache;
    protected EventTypeTransferCache eventTypeTransferCache;
    protected MimeTypeTransferCache mimeTypeTransferCache;
    protected MimeTypeDescriptionTransferCache mimeTypeDescriptionTransferCache;
    protected EntityTimeTransferCache entityTimeTransferCache;
    protected EntityVisitTransferCache entityVisitTransferCache;
    protected EntityInstanceTransferCache entityInstanceTransferCache;
    protected EventGroupTransferCache eventGroupTransferCache;
    protected EventTransferCache eventTransferCache;
    protected EntityLockTransferCache entityLockTransferCache;
    protected MimeTypeUsageTypeTransferCache mimeTypeUsageTypeTransferCache;
    protected MimeTypeUsageTransferCache mimeTypeUsageTransferCache;
    protected ServerTransferCache serverTransferCache;
    protected ServerDescriptionTransferCache serverDescriptionTransferCache;
    protected ServerServiceTransferCache serverServiceTransferCache;
    protected MimeTypeFileExtensionTransferCache mimeTypeFileExtensionTransferCache;
    protected EntityBlobAttributeTransferCache entityBlobAttributeTransferCache;
    protected EntityBooleanDefaultTransferCache entityBooleanDefaultTransferCache;
    protected EntityBooleanAttributeTransferCache entityBooleanAttributeTransferCache;
    protected EntityClobAttributeTransferCache entityClobAttributeTransferCache;
    protected EntityDateDefaultTransferCache entityDateDefaultTransferCache;
    protected EntityDateAttributeTransferCache entityDateAttributeTransferCache;
    protected EntityIntegerDefaultTransferCache entityIntegerDefaultTransferCache;
    protected EntityIntegerAttributeTransferCache entityIntegerAttributeTransferCache;
    protected EntityListItemDefaultTransferCache entityListItemDefaultTransferCache;
    protected EntityListItemAttributeTransferCache entityListItemAttributeTransferCache;
    protected EntityLongDefaultTransferCache entityLongDefaultTransferCache;
    protected EntityLongAttributeTransferCache entityLongAttributeTransferCache;
    protected EntityMultipleListItemDefaultTransferCache entityMultipleListItemDefaultTransferCache;
    protected EntityMultipleListItemAttributeTransferCache entityMultipleListItemAttributeTransferCache;
    protected EntityNameAttributeTransferCache entityNameAttributeTransferCache;
    protected EntityGeoPointDefaultTransferCache entityGeoPointDefaultTransferCache;
    protected EntityGeoPointAttributeTransferCache entityGeoPointAttributeTransferCache;
    protected EntityStringDefaultTransferCache entityStringDefaultTransferCache;
    protected EntityStringAttributeTransferCache entityStringAttributeTransferCache;
    protected EntityTimeDefaultTransferCache entityTimeDefaultTransferCache;
    protected EntityTimeAttributeTransferCache entityTimeAttributeTransferCache;
    protected EntityAttributeEntityTypeTransferCache entityAttributeEntityTypeTransferCache;
    protected EntityEntityAttributeTransferCache entityEntityAttributeTransferCache;
    protected EntityCollectionAttributeTransferCache entityCollectionAttributeTransferCache;
    protected CommandMessageTypeTransferCache commandMessageTypeTransferCache;
    protected CommandMessageTypeDescriptionTransferCache commandMessageTypeDescriptionTransferCache;
    protected BaseEncryptionKeyTransferCache baseEncryptionKeyTransferCache;
    protected ProtocolTransferCache protocolTransferCache;
    protected ProtocolDescriptionTransferCache protocolDescriptionTransferCache;
    protected ServiceTransferCache serviceTransferCache;
    protected ServiceDescriptionTransferCache serviceDescriptionTransferCache;
    protected CacheEntryTransferCache cacheEntryTransferCache;
    protected CacheEntryDependencyTransferCache cacheEntryDependencyTransferCache;
    protected ApplicationTransferCache applicationTransferCache;
    protected ApplicationDescriptionTransferCache applicationDescriptionTransferCache;
    protected EditorTransferCache editorTransferCache;
    protected EditorDescriptionTransferCache editorDescriptionTransferCache;
    protected ApplicationEditorTransferCache applicationEditorTransferCache;
    protected ApplicationEditorUseTransferCache applicationEditorUseTransferCache;
    protected ApplicationEditorUseDescriptionTransferCache applicationEditorUseDescriptionTransferCache;
    protected ColorTransferCache colorTransferCache;
    protected ColorDescriptionTransferCache colorDescriptionTransferCache;
    protected FontStyleTransferCache fontStyleTransferCache;
    protected FontStyleDescriptionTransferCache fontStyleDescriptionTransferCache;
    protected FontWeightTransferCache fontWeightTransferCache;
    protected FontWeightDescriptionTransferCache fontWeightDescriptionTransferCache;
    protected TextDecorationTransferCache textDecorationTransferCache;
    protected TextDecorationDescriptionTransferCache textDecorationDescriptionTransferCache;
    protected TextTransformationTransferCache textTransformationTransferCache;
    protected TextTransformationDescriptionTransferCache textTransformationDescriptionTransferCache;
    protected AppearanceTransferCache appearanceTransferCache;
    protected AppearanceDescriptionTransferCache appearanceDescriptionTransferCache;
    protected AppearanceTextDecorationTransferCache appearanceTextDecorationTransferCache;
    protected AppearanceTextTransformationTransferCache appearanceTextTransformationTransferCache;
    protected EntityAppearanceTransferCache entityAppearanceTransferCache;
    
    /** Creates a new instance of CoreTransferCaches */
    public CoreTransferCaches() {
        super();
    }
    
    public ComponentVendorTransferCache getComponentVendorTransferCache() {
        if(componentVendorTransferCache == null)
            componentVendorTransferCache = CDI.current().select(ComponentVendorTransferCache.class).get();

        return componentVendorTransferCache;
    }

    public EntityTypeTransferCache getEntityTypeTransferCache() {
        if(entityTypeTransferCache == null)
            entityTypeTransferCache = CDI.current().select(EntityTypeTransferCache.class).get();
        
        return entityTypeTransferCache;
    }
    
    public EntityTypeDescriptionTransferCache getEntityTypeDescriptionTransferCache() {
        if(entityTypeDescriptionTransferCache == null)
            entityTypeDescriptionTransferCache = CDI.current().select(EntityTypeDescriptionTransferCache.class).get();
        
        return entityTypeDescriptionTransferCache;
    }
    
    public CommandTransferCache getCommandTransferCache() {
        if(commandTransferCache == null)
            commandTransferCache = CDI.current().select(CommandTransferCache.class).get();
        
        return commandTransferCache;
    }
    
    public CommandDescriptionTransferCache getCommandDescriptionTransferCache() {
        if(commandDescriptionTransferCache == null)
            commandDescriptionTransferCache = CDI.current().select(CommandDescriptionTransferCache.class).get();
        
        return commandDescriptionTransferCache;
    }
    
    public CommandMessageTransferCache getCommandMessageTransferCache() {
        if(commandMessageTransferCache == null)
            commandMessageTransferCache = CDI.current().select(CommandMessageTransferCache.class).get();
        
        return commandMessageTransferCache;
    }
    
    public CommandMessageTranslationTransferCache getCommandMessageTranslationTransferCache() {
        if(commandMessageTranslationTransferCache == null)
            commandMessageTranslationTransferCache = CDI.current().select(CommandMessageTranslationTransferCache.class).get();
        
        return commandMessageTranslationTransferCache;
    }

    public EntityAliasTransferCache getEntityAliasTransferCache() {
        if(entityAliasTransferCache == null)
            entityAliasTransferCache = CDI.current().select(EntityAliasTransferCache.class).get();

        return entityAliasTransferCache;
    }

    public EntityAliasTypeTransferCache getEntityAliasTypeTransferCache() {
        if(entityAliasTypeTransferCache == null)
            entityAliasTypeTransferCache = CDI.current().select(EntityAliasTypeTransferCache.class).get();

        return entityAliasTypeTransferCache;
    }

    public EntityAliasTypeDescriptionTransferCache getEntityAliasTypeDescriptionTransferCache() {
        if(entityAliasTypeDescriptionTransferCache == null)
            entityAliasTypeDescriptionTransferCache = CDI.current().select(EntityAliasTypeDescriptionTransferCache.class).get();

        return entityAliasTypeDescriptionTransferCache;
    }

    public EntityAttributeTransferCache getEntityAttributeTransferCache() {
        if(entityAttributeTransferCache == null)
            entityAttributeTransferCache = CDI.current().select(EntityAttributeTransferCache.class).get();

        return entityAttributeTransferCache;
    }

    public EntityAttributeDescriptionTransferCache getEntityAttributeDescriptionTransferCache() {
        if(entityAttributeDescriptionTransferCache == null)
            entityAttributeDescriptionTransferCache = CDI.current().select(EntityAttributeDescriptionTransferCache.class).get();

        return entityAttributeDescriptionTransferCache;
    }

    public EntityAttributeEntityAttributeGroupTransferCache getEntityAttributeEntityAttributeGroupTransferCache() {
        if(entityAttributeEntityAttributeGroupTransferCache == null)
            entityAttributeEntityAttributeGroupTransferCache = CDI.current().select(EntityAttributeEntityAttributeGroupTransferCache.class).get();
        
        return entityAttributeEntityAttributeGroupTransferCache;
    }
    
    public EntityAttributeGroupTransferCache getEntityAttributeGroupTransferCache() {
        if(entityAttributeGroupTransferCache == null)
            entityAttributeGroupTransferCache = CDI.current().select(EntityAttributeGroupTransferCache.class).get();
        
        return entityAttributeGroupTransferCache;
    }
    
    public EntityAttributeGroupDescriptionTransferCache getEntityAttributeGroupDescriptionTransferCache() {
        if(entityAttributeGroupDescriptionTransferCache == null)
            entityAttributeGroupDescriptionTransferCache = CDI.current().select(EntityAttributeGroupDescriptionTransferCache.class).get();
        
        return entityAttributeGroupDescriptionTransferCache;
    }
    
    public EntityListItemTransferCache getEntityListItemTransferCache() {
        if(entityListItemTransferCache == null)
            entityListItemTransferCache = CDI.current().select(EntityListItemTransferCache.class).get();
        
        return entityListItemTransferCache;
    }
    
    public EntityListItemDescriptionTransferCache getEntityListItemDescriptionTransferCache() {
        if(entityListItemDescriptionTransferCache == null)
            entityListItemDescriptionTransferCache = CDI.current().select(EntityListItemDescriptionTransferCache.class).get();
        
        return entityListItemDescriptionTransferCache;
    }
    
    public EntityIntegerRangeTransferCache getEntityIntegerRangeTransferCache() {
        if(entityIntegerRangeTransferCache == null)
            entityIntegerRangeTransferCache = CDI.current().select(EntityIntegerRangeTransferCache.class).get();
        
        return entityIntegerRangeTransferCache;
    }
    
    public EntityIntegerRangeDescriptionTransferCache getEntityIntegerRangeDescriptionTransferCache() {
        if(entityIntegerRangeDescriptionTransferCache == null)
            entityIntegerRangeDescriptionTransferCache = CDI.current().select(EntityIntegerRangeDescriptionTransferCache.class).get();
        
        return entityIntegerRangeDescriptionTransferCache;
    }
    
    public EntityLongRangeTransferCache getEntityLongRangeTransferCache() {
        if(entityLongRangeTransferCache == null)
            entityLongRangeTransferCache = CDI.current().select(EntityLongRangeTransferCache.class).get();
        
        return entityLongRangeTransferCache;
    }
    
    public EntityLongRangeDescriptionTransferCache getEntityLongRangeDescriptionTransferCache() {
        if(entityLongRangeDescriptionTransferCache == null)
            entityLongRangeDescriptionTransferCache = CDI.current().select(EntityLongRangeDescriptionTransferCache.class).get();
        
        return entityLongRangeDescriptionTransferCache;
    }
    
    public EntityAttributeTypeTransferCache getEntityAttributeTypeTransferCache() {
        if(entityAttributeTypeTransferCache == null)
            entityAttributeTypeTransferCache = CDI.current().select(EntityAttributeTypeTransferCache.class).get();
        
        return entityAttributeTypeTransferCache;
    }
    
    public EventTypeTransferCache getEventTypeTransferCache() {
        if(eventTypeTransferCache == null)
            eventTypeTransferCache = CDI.current().select(EventTypeTransferCache.class).get();
        
        return eventTypeTransferCache;
    }
    
    public MimeTypeTransferCache getMimeTypeTransferCache() {
        if(mimeTypeTransferCache == null)
            mimeTypeTransferCache = CDI.current().select(MimeTypeTransferCache.class).get();

        return mimeTypeTransferCache;
    }

    public MimeTypeDescriptionTransferCache getMimeTypeDescriptionTransferCache() {
        if(mimeTypeDescriptionTransferCache == null)
            mimeTypeDescriptionTransferCache = CDI.current().select(MimeTypeDescriptionTransferCache.class).get();

        return mimeTypeDescriptionTransferCache;
    }

    public EntityTimeTransferCache getEntityTimeTransferCache() {
        if(entityTimeTransferCache == null)
            entityTimeTransferCache = CDI.current().select(EntityTimeTransferCache.class).get();

        return entityTimeTransferCache;
    }

    public EntityVisitTransferCache getEntityVisitTransferCache() {
        if(entityVisitTransferCache == null)
            entityVisitTransferCache = CDI.current().select(EntityVisitTransferCache.class).get();

        return entityVisitTransferCache;
    }

    public EntityInstanceTransferCache getEntityInstanceTransferCache() {
        if(entityInstanceTransferCache == null)
            entityInstanceTransferCache = CDI.current().select(EntityInstanceTransferCache.class).get();
        
        return entityInstanceTransferCache;
    }
    
    public EventGroupTransferCache getEventGroupTransferCache() {
        if(eventGroupTransferCache == null)
            eventGroupTransferCache = CDI.current().select(EventGroupTransferCache.class).get();
        
        return eventGroupTransferCache;
    }
    
    public EventTransferCache getEventTransferCache() {
        if(eventTransferCache == null)
            eventTransferCache = CDI.current().select(EventTransferCache.class).get();
        
        return eventTransferCache;
    }
    
    public EntityLockTransferCache getEntityLockTransferCache() {
        if(entityLockTransferCache == null)
            entityLockTransferCache = CDI.current().select(EntityLockTransferCache.class).get();
        
        return entityLockTransferCache;
    }
    
    public MimeTypeUsageTypeTransferCache getMimeTypeUsageTypeTransferCache() {
        if(mimeTypeUsageTypeTransferCache == null)
            mimeTypeUsageTypeTransferCache = CDI.current().select(MimeTypeUsageTypeTransferCache.class).get();
        
        return mimeTypeUsageTypeTransferCache;
    }
    
    public MimeTypeUsageTransferCache getMimeTypeUsageTransferCache() {
        if(mimeTypeUsageTransferCache == null)
            mimeTypeUsageTransferCache = CDI.current().select(MimeTypeUsageTransferCache.class).get();
        
        return mimeTypeUsageTransferCache;
    }
    
    public ServerTransferCache getServerTransferCache() {
        if(serverTransferCache == null)
            serverTransferCache = CDI.current().select(ServerTransferCache.class).get();

        return serverTransferCache;
    }

    public ServerDescriptionTransferCache getServerDescriptionTransferCache() {
        if(serverDescriptionTransferCache == null)
            serverDescriptionTransferCache = CDI.current().select(ServerDescriptionTransferCache.class).get();

        return serverDescriptionTransferCache;
    }

    public ServerServiceTransferCache getServerServiceTransferCache() {
        if(serverServiceTransferCache == null)
            serverServiceTransferCache = CDI.current().select(ServerServiceTransferCache.class).get();

        return serverServiceTransferCache;
    }

    public MimeTypeFileExtensionTransferCache getMimeTypeFileExtensionTransferCache() {
        if(mimeTypeFileExtensionTransferCache == null)
            mimeTypeFileExtensionTransferCache = CDI.current().select(MimeTypeFileExtensionTransferCache.class).get();
        
        return mimeTypeFileExtensionTransferCache;
    }
    
    public EntityBlobAttributeTransferCache getEntityBlobAttributeTransferCache() {
        if(entityBlobAttributeTransferCache == null)
            entityBlobAttributeTransferCache = CDI.current().select(EntityBlobAttributeTransferCache.class).get();
        
        return entityBlobAttributeTransferCache;
    }

    public EntityBooleanDefaultTransferCache getEntityBooleanDefaultTransferCache() {
        if(entityBooleanDefaultTransferCache == null)
            entityBooleanDefaultTransferCache = CDI.current().select(EntityBooleanDefaultTransferCache.class).get();

        return entityBooleanDefaultTransferCache;
    }

    public EntityBooleanAttributeTransferCache getEntityBooleanAttributeTransferCache() {
        if(entityBooleanAttributeTransferCache == null)
            entityBooleanAttributeTransferCache = CDI.current().select(EntityBooleanAttributeTransferCache.class).get();

        return entityBooleanAttributeTransferCache;
    }

    public EntityClobAttributeTransferCache getEntityClobAttributeTransferCache() {
        if(entityClobAttributeTransferCache == null)
            entityClobAttributeTransferCache = CDI.current().select(EntityClobAttributeTransferCache.class).get();
        
        return entityClobAttributeTransferCache;
    }

    public EntityDateDefaultTransferCache getEntityDateDefaultTransferCache() {
        if(entityDateDefaultTransferCache == null)
            entityDateDefaultTransferCache = CDI.current().select(EntityDateDefaultTransferCache.class).get();

        return entityDateDefaultTransferCache;
    }

    public EntityDateAttributeTransferCache getEntityDateAttributeTransferCache() {
        if(entityDateAttributeTransferCache == null)
            entityDateAttributeTransferCache = CDI.current().select(EntityDateAttributeTransferCache.class).get();

        return entityDateAttributeTransferCache;
    }

    public EntityIntegerDefaultTransferCache getEntityIntegerDefaultTransferCache() {
        if(entityIntegerDefaultTransferCache == null)
            entityIntegerDefaultTransferCache = CDI.current().select(EntityIntegerDefaultTransferCache.class).get();

        return entityIntegerDefaultTransferCache;
    }

    public EntityIntegerAttributeTransferCache getEntityIntegerAttributeTransferCache() {
        if(entityIntegerAttributeTransferCache == null)
            entityIntegerAttributeTransferCache = CDI.current().select(EntityIntegerAttributeTransferCache.class).get();
        
        return entityIntegerAttributeTransferCache;
    }

    public EntityListItemDefaultTransferCache getEntityListItemDefaultTransferCache() {
        if(entityListItemDefaultTransferCache == null)
            entityListItemDefaultTransferCache = CDI.current().select(EntityListItemDefaultTransferCache.class).get();

        return entityListItemDefaultTransferCache;
    }

    public EntityListItemAttributeTransferCache getEntityListItemAttributeTransferCache() {
        if(entityListItemAttributeTransferCache == null)
            entityListItemAttributeTransferCache = CDI.current().select(EntityListItemAttributeTransferCache.class).get();

        return entityListItemAttributeTransferCache;
    }

    public EntityLongDefaultTransferCache getEntityLongDefaultTransferCache() {
        if(entityLongDefaultTransferCache == null)
            entityLongDefaultTransferCache = CDI.current().select(EntityLongDefaultTransferCache.class).get();

        return entityLongDefaultTransferCache;
    }

    public EntityLongAttributeTransferCache getEntityLongAttributeTransferCache() {
        if(entityLongAttributeTransferCache == null)
            entityLongAttributeTransferCache = CDI.current().select(EntityLongAttributeTransferCache.class).get();
        
        return entityLongAttributeTransferCache;
    }

    public EntityMultipleListItemDefaultTransferCache getEntityMultipleListItemDefaultTransferCache() {
        if(entityMultipleListItemDefaultTransferCache == null)
            entityMultipleListItemDefaultTransferCache = CDI.current().select(EntityMultipleListItemDefaultTransferCache.class).get();

        return entityMultipleListItemDefaultTransferCache;
    }

    public EntityMultipleListItemAttributeTransferCache getEntityMultipleListItemAttributeTransferCache() {
        if(entityMultipleListItemAttributeTransferCache == null)
            entityMultipleListItemAttributeTransferCache = CDI.current().select(EntityMultipleListItemAttributeTransferCache.class).get();

        return entityMultipleListItemAttributeTransferCache;
    }

    public EntityNameAttributeTransferCache getEntityNameAttributeTransferCache() {
        if(entityNameAttributeTransferCache == null)
            entityNameAttributeTransferCache = CDI.current().select(EntityNameAttributeTransferCache.class).get();
        
        return entityNameAttributeTransferCache;
    }

    public EntityGeoPointDefaultTransferCache getEntityGeoPointDefaultTransferCache() {
        if(entityGeoPointDefaultTransferCache == null)
            entityGeoPointDefaultTransferCache = CDI.current().select(EntityGeoPointDefaultTransferCache.class).get();

        return entityGeoPointDefaultTransferCache;
    }

    public EntityGeoPointAttributeTransferCache getEntityGeoPointAttributeTransferCache() {
        if(entityGeoPointAttributeTransferCache == null)
            entityGeoPointAttributeTransferCache = CDI.current().select(EntityGeoPointAttributeTransferCache.class).get();

        return entityGeoPointAttributeTransferCache;
    }

    public EntityStringDefaultTransferCache getEntityStringDefaultTransferCache() {
        if(entityStringDefaultTransferCache == null)
            entityStringDefaultTransferCache = CDI.current().select(EntityStringDefaultTransferCache.class).get();

        return entityStringDefaultTransferCache;
    }

    public EntityStringAttributeTransferCache getEntityStringAttributeTransferCache() {
        if(entityStringAttributeTransferCache == null)
            entityStringAttributeTransferCache = CDI.current().select(EntityStringAttributeTransferCache.class).get();

        return entityStringAttributeTransferCache;
    }

    public EntityTimeDefaultTransferCache getEntityTimeDefaultTransferCache() {
        if(entityTimeDefaultTransferCache == null)
            entityTimeDefaultTransferCache = CDI.current().select(EntityTimeDefaultTransferCache.class).get();

        return entityTimeDefaultTransferCache;
    }

    public EntityTimeAttributeTransferCache getEntityTimeAttributeTransferCache() {
        if(entityTimeAttributeTransferCache == null)
            entityTimeAttributeTransferCache = CDI.current().select(EntityTimeAttributeTransferCache.class).get();

        return entityTimeAttributeTransferCache;
    }

    public EntityAttributeEntityTypeTransferCache getEntityAttributeEntityTypeTransferCache() {
        if(entityAttributeEntityTypeTransferCache == null)
            entityAttributeEntityTypeTransferCache = CDI.current().select(EntityAttributeEntityTypeTransferCache.class).get();
        
        return entityAttributeEntityTypeTransferCache;
    }
    
    public EntityEntityAttributeTransferCache getEntityEntityAttributeTransferCache() {
        if(entityEntityAttributeTransferCache == null)
            entityEntityAttributeTransferCache = CDI.current().select(EntityEntityAttributeTransferCache.class).get();
        
        return entityEntityAttributeTransferCache;
    }
    
    public EntityCollectionAttributeTransferCache getEntityCollectionAttributeTransferCache() {
        if(entityCollectionAttributeTransferCache == null)
            entityCollectionAttributeTransferCache = CDI.current().select(EntityCollectionAttributeTransferCache.class).get();
        
        return entityCollectionAttributeTransferCache;
    }
    
    public CommandMessageTypeTransferCache getCommandMessageTypeTransferCache() {
        if(commandMessageTypeTransferCache == null)
            commandMessageTypeTransferCache = CDI.current().select(CommandMessageTypeTransferCache.class).get();
        
        return commandMessageTypeTransferCache;
    }
    
    public CommandMessageTypeDescriptionTransferCache getCommandMessageTypeDescriptionTransferCache() {
        if(commandMessageTypeDescriptionTransferCache == null)
            commandMessageTypeDescriptionTransferCache = CDI.current().select(CommandMessageTypeDescriptionTransferCache.class).get();
        
        return commandMessageTypeDescriptionTransferCache;
    }
    
    public BaseEncryptionKeyTransferCache getBaseEncryptionKeyTransferCache() {
        if(baseEncryptionKeyTransferCache == null)
            baseEncryptionKeyTransferCache = CDI.current().select(BaseEncryptionKeyTransferCache.class).get();
        
        return baseEncryptionKeyTransferCache;
    }
    
    public ProtocolTransferCache getProtocolTransferCache() {
        if(protocolTransferCache == null)
            protocolTransferCache = CDI.current().select(ProtocolTransferCache.class).get();

        return protocolTransferCache;
    }

    public ProtocolDescriptionTransferCache getProtocolDescriptionTransferCache() {
        if(protocolDescriptionTransferCache == null)
            protocolDescriptionTransferCache = CDI.current().select(ProtocolDescriptionTransferCache.class).get();

        return protocolDescriptionTransferCache;
    }

    public ServiceTransferCache getServiceTransferCache() {
        if(serviceTransferCache == null)
            serviceTransferCache = CDI.current().select(ServiceTransferCache.class).get();

        return serviceTransferCache;
    }

    public ServiceDescriptionTransferCache getServiceDescriptionTransferCache() {
        if(serviceDescriptionTransferCache == null)
            serviceDescriptionTransferCache = CDI.current().select(ServiceDescriptionTransferCache.class).get();

        return serviceDescriptionTransferCache;
    }

    public CacheEntryTransferCache getCacheEntryTransferCache() {
        if(cacheEntryTransferCache == null)
            cacheEntryTransferCache = CDI.current().select(CacheEntryTransferCache.class).get();

        return cacheEntryTransferCache;
    }

    public CacheEntryDependencyTransferCache getCacheEntryDependencyTransferCache() {
        if(cacheEntryDependencyTransferCache == null)
            cacheEntryDependencyTransferCache = CDI.current().select(CacheEntryDependencyTransferCache.class).get();

        return cacheEntryDependencyTransferCache;
    }

    public ApplicationTransferCache getApplicationTransferCache() {
        if(applicationTransferCache == null)
            applicationTransferCache = CDI.current().select(ApplicationTransferCache.class).get();

        return applicationTransferCache;
    }

    public ApplicationDescriptionTransferCache getApplicationDescriptionTransferCache() {
        if(applicationDescriptionTransferCache == null)
            applicationDescriptionTransferCache = CDI.current().select(ApplicationDescriptionTransferCache.class).get();

        return applicationDescriptionTransferCache;
    }

    public EditorTransferCache getEditorTransferCache() {
        if(editorTransferCache == null)
            editorTransferCache = CDI.current().select(EditorTransferCache.class).get();

        return editorTransferCache;
    }

    public EditorDescriptionTransferCache getEditorDescriptionTransferCache() {
        if(editorDescriptionTransferCache == null)
            editorDescriptionTransferCache = CDI.current().select(EditorDescriptionTransferCache.class).get();

        return editorDescriptionTransferCache;
    }

    public ApplicationEditorTransferCache getApplicationEditorTransferCache() {
        if(applicationEditorTransferCache == null)
            applicationEditorTransferCache = CDI.current().select(ApplicationEditorTransferCache.class).get();

        return applicationEditorTransferCache;
    }

    public ApplicationEditorUseTransferCache getApplicationEditorUseTransferCache() {
        if(applicationEditorUseTransferCache == null)
            applicationEditorUseTransferCache = CDI.current().select(ApplicationEditorUseTransferCache.class).get();

        return applicationEditorUseTransferCache;
    }

    public ApplicationEditorUseDescriptionTransferCache getApplicationEditorUseDescriptionTransferCache() {
        if(applicationEditorUseDescriptionTransferCache == null)
            applicationEditorUseDescriptionTransferCache = CDI.current().select(ApplicationEditorUseDescriptionTransferCache.class).get();

        return applicationEditorUseDescriptionTransferCache;
    }

    public ColorTransferCache getColorTransferCache() {
        if(colorTransferCache == null)
            colorTransferCache = CDI.current().select(ColorTransferCache.class).get();

        return colorTransferCache;
    }

    public ColorDescriptionTransferCache getColorDescriptionTransferCache() {
        if(colorDescriptionTransferCache == null)
            colorDescriptionTransferCache = CDI.current().select(ColorDescriptionTransferCache.class).get();

        return colorDescriptionTransferCache;
    }

    public FontStyleTransferCache getFontStyleTransferCache() {
        if(fontStyleTransferCache == null)
            fontStyleTransferCache = CDI.current().select(FontStyleTransferCache.class).get();

        return fontStyleTransferCache;
    }

    public FontStyleDescriptionTransferCache getFontStyleDescriptionTransferCache() {
        if(fontStyleDescriptionTransferCache == null)
            fontStyleDescriptionTransferCache = CDI.current().select(FontStyleDescriptionTransferCache.class).get();

        return fontStyleDescriptionTransferCache;
    }

    public FontWeightTransferCache getFontWeightTransferCache() {
        if(fontWeightTransferCache == null)
            fontWeightTransferCache = CDI.current().select(FontWeightTransferCache.class).get();

        return fontWeightTransferCache;
    }

    public FontWeightDescriptionTransferCache getFontWeightDescriptionTransferCache() {
        if(fontWeightDescriptionTransferCache == null)
            fontWeightDescriptionTransferCache = CDI.current().select(FontWeightDescriptionTransferCache.class).get();

        return fontWeightDescriptionTransferCache;
    }

    public TextDecorationTransferCache getTextDecorationTransferCache() {
        if(textDecorationTransferCache == null)
            textDecorationTransferCache = CDI.current().select(TextDecorationTransferCache.class).get();

        return textDecorationTransferCache;
    }

    public TextDecorationDescriptionTransferCache getTextDecorationDescriptionTransferCache() {
        if(textDecorationDescriptionTransferCache == null)
            textDecorationDescriptionTransferCache = CDI.current().select(TextDecorationDescriptionTransferCache.class).get();

        return textDecorationDescriptionTransferCache;
    }

    public TextTransformationTransferCache getTextTransformationTransferCache() {
        if(textTransformationTransferCache == null)
            textTransformationTransferCache = CDI.current().select(TextTransformationTransferCache.class).get();

        return textTransformationTransferCache;
    }

    public TextTransformationDescriptionTransferCache getTextTransformationDescriptionTransferCache() {
        if(textTransformationDescriptionTransferCache == null)
            textTransformationDescriptionTransferCache = CDI.current().select(TextTransformationDescriptionTransferCache.class).get();

        return textTransformationDescriptionTransferCache;
    }

    public AppearanceTransferCache getAppearanceTransferCache() {
        if(appearanceTransferCache == null)
            appearanceTransferCache = CDI.current().select(AppearanceTransferCache.class).get();

        return appearanceTransferCache;
    }

    public AppearanceDescriptionTransferCache getAppearanceDescriptionTransferCache() {
        if(appearanceDescriptionTransferCache == null)
            appearanceDescriptionTransferCache = CDI.current().select(AppearanceDescriptionTransferCache.class).get();

        return appearanceDescriptionTransferCache;
    }

    public AppearanceTextDecorationTransferCache getAppearanceTextDecorationTransferCache() {
        if(appearanceTextDecorationTransferCache == null)
            appearanceTextDecorationTransferCache = CDI.current().select(AppearanceTextDecorationTransferCache.class).get();

        return appearanceTextDecorationTransferCache;
    }

    public AppearanceTextTransformationTransferCache getAppearanceTextTransformationTransferCache() {
        if(appearanceTextTransformationTransferCache == null)
            appearanceTextTransformationTransferCache = CDI.current().select(AppearanceTextTransformationTransferCache.class).get();

        return appearanceTextTransformationTransferCache;
    }

    public EntityAppearanceTransferCache getEntityAppearanceTransferCache() {
        if(entityAppearanceTransferCache == null)
            entityAppearanceTransferCache = CDI.current().select(EntityAppearanceTransferCache.class).get();
        
        return entityAppearanceTransferCache;
    }
    
}
