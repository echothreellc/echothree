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

import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.transfer.BaseTransferCaches;

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
            componentVendorTransferCache = new ComponentVendorTransferCache();

        return componentVendorTransferCache;
    }

    public EntityTypeTransferCache getEntityTypeTransferCache() {
        if(entityTypeTransferCache == null)
            entityTypeTransferCache = new EntityTypeTransferCache();
        
        return entityTypeTransferCache;
    }
    
    public EntityTypeDescriptionTransferCache getEntityTypeDescriptionTransferCache() {
        if(entityTypeDescriptionTransferCache == null)
            entityTypeDescriptionTransferCache = new EntityTypeDescriptionTransferCache();
        
        return entityTypeDescriptionTransferCache;
    }
    
    public CommandTransferCache getCommandTransferCache() {
        if(commandTransferCache == null)
            commandTransferCache = new CommandTransferCache();
        
        return commandTransferCache;
    }
    
    public CommandDescriptionTransferCache getCommandDescriptionTransferCache() {
        if(commandDescriptionTransferCache == null)
            commandDescriptionTransferCache = new CommandDescriptionTransferCache();
        
        return commandDescriptionTransferCache;
    }
    
    public CommandMessageTransferCache getCommandMessageTransferCache() {
        if(commandMessageTransferCache == null)
            commandMessageTransferCache = new CommandMessageTransferCache();
        
        return commandMessageTransferCache;
    }
    
    public CommandMessageTranslationTransferCache getCommandMessageTranslationTransferCache() {
        if(commandMessageTranslationTransferCache == null)
            commandMessageTranslationTransferCache = new CommandMessageTranslationTransferCache();
        
        return commandMessageTranslationTransferCache;
    }

    public EntityAliasTransferCache getEntityAliasTransferCache() {
        if(entityAliasTransferCache == null)
            entityAliasTransferCache = new EntityAliasTransferCache();

        return entityAliasTransferCache;
    }

    public EntityAliasTypeTransferCache getEntityAliasTypeTransferCache() {
        if(entityAliasTypeTransferCache == null)
            entityAliasTypeTransferCache = new EntityAliasTypeTransferCache();

        return entityAliasTypeTransferCache;
    }

    public EntityAliasTypeDescriptionTransferCache getEntityAliasTypeDescriptionTransferCache() {
        if(entityAliasTypeDescriptionTransferCache == null)
            entityAliasTypeDescriptionTransferCache = new EntityAliasTypeDescriptionTransferCache();

        return entityAliasTypeDescriptionTransferCache;
    }

    public EntityAttributeTransferCache getEntityAttributeTransferCache() {
        if(entityAttributeTransferCache == null)
            entityAttributeTransferCache = new EntityAttributeTransferCache();

        return entityAttributeTransferCache;
    }

    public EntityAttributeDescriptionTransferCache getEntityAttributeDescriptionTransferCache() {
        if(entityAttributeDescriptionTransferCache == null)
            entityAttributeDescriptionTransferCache = new EntityAttributeDescriptionTransferCache();

        return entityAttributeDescriptionTransferCache;
    }

    public EntityAttributeEntityAttributeGroupTransferCache getEntityAttributeEntityAttributeGroupTransferCache() {
        if(entityAttributeEntityAttributeGroupTransferCache == null)
            entityAttributeEntityAttributeGroupTransferCache = new EntityAttributeEntityAttributeGroupTransferCache();
        
        return entityAttributeEntityAttributeGroupTransferCache;
    }
    
    public EntityAttributeGroupTransferCache getEntityAttributeGroupTransferCache() {
        if(entityAttributeGroupTransferCache == null)
            entityAttributeGroupTransferCache = new EntityAttributeGroupTransferCache();
        
        return entityAttributeGroupTransferCache;
    }
    
    public EntityAttributeGroupDescriptionTransferCache getEntityAttributeGroupDescriptionTransferCache() {
        if(entityAttributeGroupDescriptionTransferCache == null)
            entityAttributeGroupDescriptionTransferCache = new EntityAttributeGroupDescriptionTransferCache();
        
        return entityAttributeGroupDescriptionTransferCache;
    }
    
    public EntityListItemTransferCache getEntityListItemTransferCache() {
        if(entityListItemTransferCache == null)
            entityListItemTransferCache = new EntityListItemTransferCache();
        
        return entityListItemTransferCache;
    }
    
    public EntityListItemDescriptionTransferCache getEntityListItemDescriptionTransferCache() {
        if(entityListItemDescriptionTransferCache == null)
            entityListItemDescriptionTransferCache = new EntityListItemDescriptionTransferCache();
        
        return entityListItemDescriptionTransferCache;
    }
    
    public EntityIntegerRangeTransferCache getEntityIntegerRangeTransferCache() {
        if(entityIntegerRangeTransferCache == null)
            entityIntegerRangeTransferCache = new EntityIntegerRangeTransferCache();
        
        return entityIntegerRangeTransferCache;
    }
    
    public EntityIntegerRangeDescriptionTransferCache getEntityIntegerRangeDescriptionTransferCache() {
        if(entityIntegerRangeDescriptionTransferCache == null)
            entityIntegerRangeDescriptionTransferCache = new EntityIntegerRangeDescriptionTransferCache();
        
        return entityIntegerRangeDescriptionTransferCache;
    }
    
    public EntityLongRangeTransferCache getEntityLongRangeTransferCache() {
        if(entityLongRangeTransferCache == null)
            entityLongRangeTransferCache = new EntityLongRangeTransferCache();
        
        return entityLongRangeTransferCache;
    }
    
    public EntityLongRangeDescriptionTransferCache getEntityLongRangeDescriptionTransferCache() {
        if(entityLongRangeDescriptionTransferCache == null)
            entityLongRangeDescriptionTransferCache = new EntityLongRangeDescriptionTransferCache();
        
        return entityLongRangeDescriptionTransferCache;
    }
    
    public EntityAttributeTypeTransferCache getEntityAttributeTypeTransferCache() {
        if(entityAttributeTypeTransferCache == null)
            entityAttributeTypeTransferCache = new EntityAttributeTypeTransferCache();
        
        return entityAttributeTypeTransferCache;
    }
    
    public EventTypeTransferCache getEventTypeTransferCache() {
        if(eventTypeTransferCache == null)
            eventTypeTransferCache = new EventTypeTransferCache();
        
        return eventTypeTransferCache;
    }
    
    public MimeTypeTransferCache getMimeTypeTransferCache() {
        if(mimeTypeTransferCache == null)
            mimeTypeTransferCache = new MimeTypeTransferCache();

        return mimeTypeTransferCache;
    }

    public MimeTypeDescriptionTransferCache getMimeTypeDescriptionTransferCache() {
        if(mimeTypeDescriptionTransferCache == null)
            mimeTypeDescriptionTransferCache = new MimeTypeDescriptionTransferCache();

        return mimeTypeDescriptionTransferCache;
    }

    public EntityTimeTransferCache getEntityTimeTransferCache() {
        if(entityTimeTransferCache == null)
            entityTimeTransferCache = new EntityTimeTransferCache();

        return entityTimeTransferCache;
    }

    public EntityVisitTransferCache getEntityVisitTransferCache() {
        if(entityVisitTransferCache == null)
            entityVisitTransferCache = new EntityVisitTransferCache();

        return entityVisitTransferCache;
    }

    public EntityInstanceTransferCache getEntityInstanceTransferCache() {
        if(entityInstanceTransferCache == null)
            entityInstanceTransferCache = new EntityInstanceTransferCache();
        
        return entityInstanceTransferCache;
    }
    
    public EventGroupTransferCache getEventGroupTransferCache() {
        if(eventGroupTransferCache == null)
            eventGroupTransferCache = new EventGroupTransferCache();
        
        return eventGroupTransferCache;
    }
    
    public EventTransferCache getEventTransferCache() {
        if(eventTransferCache == null)
            eventTransferCache = new EventTransferCache();
        
        return eventTransferCache;
    }
    
    public EntityLockTransferCache getEntityLockTransferCache() {
        if(entityLockTransferCache == null)
            entityLockTransferCache = new EntityLockTransferCache();
        
        return entityLockTransferCache;
    }
    
    public MimeTypeUsageTypeTransferCache getMimeTypeUsageTypeTransferCache() {
        if(mimeTypeUsageTypeTransferCache == null)
            mimeTypeUsageTypeTransferCache = new MimeTypeUsageTypeTransferCache();
        
        return mimeTypeUsageTypeTransferCache;
    }
    
    public MimeTypeUsageTransferCache getMimeTypeUsageTransferCache() {
        if(mimeTypeUsageTransferCache == null)
            mimeTypeUsageTransferCache = new MimeTypeUsageTransferCache();
        
        return mimeTypeUsageTransferCache;
    }
    
    public ServerTransferCache getServerTransferCache() {
        if(serverTransferCache == null)
            serverTransferCache = new ServerTransferCache();

        return serverTransferCache;
    }

    public ServerDescriptionTransferCache getServerDescriptionTransferCache() {
        if(serverDescriptionTransferCache == null)
            serverDescriptionTransferCache = new ServerDescriptionTransferCache();

        return serverDescriptionTransferCache;
    }

    public ServerServiceTransferCache getServerServiceTransferCache() {
        if(serverServiceTransferCache == null)
            serverServiceTransferCache = new ServerServiceTransferCache();

        return serverServiceTransferCache;
    }

    public MimeTypeFileExtensionTransferCache getMimeTypeFileExtensionTransferCache() {
        if(mimeTypeFileExtensionTransferCache == null)
            mimeTypeFileExtensionTransferCache = new MimeTypeFileExtensionTransferCache();
        
        return mimeTypeFileExtensionTransferCache;
    }
    
    public EntityBlobAttributeTransferCache getEntityBlobAttributeTransferCache() {
        if(entityBlobAttributeTransferCache == null)
            entityBlobAttributeTransferCache = new EntityBlobAttributeTransferCache();
        
        return entityBlobAttributeTransferCache;
    }

    public EntityBooleanDefaultTransferCache getEntityBooleanDefaultTransferCache() {
        if(entityBooleanDefaultTransferCache == null)
            entityBooleanDefaultTransferCache = new EntityBooleanDefaultTransferCache();

        return entityBooleanDefaultTransferCache;
    }

    public EntityBooleanAttributeTransferCache getEntityBooleanAttributeTransferCache() {
        if(entityBooleanAttributeTransferCache == null)
            entityBooleanAttributeTransferCache = new EntityBooleanAttributeTransferCache();

        return entityBooleanAttributeTransferCache;
    }

    public EntityClobAttributeTransferCache getEntityClobAttributeTransferCache() {
        if(entityClobAttributeTransferCache == null)
            entityClobAttributeTransferCache = new EntityClobAttributeTransferCache();
        
        return entityClobAttributeTransferCache;
    }

    public EntityDateDefaultTransferCache getEntityDateDefaultTransferCache() {
        if(entityDateDefaultTransferCache == null)
            entityDateDefaultTransferCache = new EntityDateDefaultTransferCache();

        return entityDateDefaultTransferCache;
    }

    public EntityDateAttributeTransferCache getEntityDateAttributeTransferCache() {
        if(entityDateAttributeTransferCache == null)
            entityDateAttributeTransferCache = new EntityDateAttributeTransferCache();

        return entityDateAttributeTransferCache;
    }

    public EntityIntegerDefaultTransferCache getEntityIntegerDefaultTransferCache() {
        if(entityIntegerDefaultTransferCache == null)
            entityIntegerDefaultTransferCache = new EntityIntegerDefaultTransferCache();

        return entityIntegerDefaultTransferCache;
    }

    public EntityIntegerAttributeTransferCache getEntityIntegerAttributeTransferCache() {
        if(entityIntegerAttributeTransferCache == null)
            entityIntegerAttributeTransferCache = new EntityIntegerAttributeTransferCache();
        
        return entityIntegerAttributeTransferCache;
    }

    public EntityListItemDefaultTransferCache getEntityListItemDefaultTransferCache() {
        if(entityListItemDefaultTransferCache == null)
            entityListItemDefaultTransferCache = new EntityListItemDefaultTransferCache();

        return entityListItemDefaultTransferCache;
    }

    public EntityListItemAttributeTransferCache getEntityListItemAttributeTransferCache() {
        if(entityListItemAttributeTransferCache == null)
            entityListItemAttributeTransferCache = new EntityListItemAttributeTransferCache();

        return entityListItemAttributeTransferCache;
    }

    public EntityLongDefaultTransferCache getEntityLongDefaultTransferCache() {
        if(entityLongDefaultTransferCache == null)
            entityLongDefaultTransferCache = new EntityLongDefaultTransferCache();

        return entityLongDefaultTransferCache;
    }

    public EntityLongAttributeTransferCache getEntityLongAttributeTransferCache() {
        if(entityLongAttributeTransferCache == null)
            entityLongAttributeTransferCache = new EntityLongAttributeTransferCache();
        
        return entityLongAttributeTransferCache;
    }

    public EntityMultipleListItemDefaultTransferCache getEntityMultipleListItemDefaultTransferCache() {
        if(entityMultipleListItemDefaultTransferCache == null)
            entityMultipleListItemDefaultTransferCache = new EntityMultipleListItemDefaultTransferCache();

        return entityMultipleListItemDefaultTransferCache;
    }

    public EntityMultipleListItemAttributeTransferCache getEntityMultipleListItemAttributeTransferCache() {
        if(entityMultipleListItemAttributeTransferCache == null)
            entityMultipleListItemAttributeTransferCache = new EntityMultipleListItemAttributeTransferCache();

        return entityMultipleListItemAttributeTransferCache;
    }

    public EntityNameAttributeTransferCache getEntityNameAttributeTransferCache() {
        if(entityNameAttributeTransferCache == null)
            entityNameAttributeTransferCache = new EntityNameAttributeTransferCache();
        
        return entityNameAttributeTransferCache;
    }

    public EntityGeoPointDefaultTransferCache getEntityGeoPointDefaultTransferCache() {
        if(entityGeoPointDefaultTransferCache == null)
            entityGeoPointDefaultTransferCache = new EntityGeoPointDefaultTransferCache();

        return entityGeoPointDefaultTransferCache;
    }

    public EntityGeoPointAttributeTransferCache getEntityGeoPointAttributeTransferCache() {
        if(entityGeoPointAttributeTransferCache == null)
            entityGeoPointAttributeTransferCache = new EntityGeoPointAttributeTransferCache();

        return entityGeoPointAttributeTransferCache;
    }

    public EntityStringDefaultTransferCache getEntityStringDefaultTransferCache() {
        if(entityStringDefaultTransferCache == null)
            entityStringDefaultTransferCache = new EntityStringDefaultTransferCache();

        return entityStringDefaultTransferCache;
    }

    public EntityStringAttributeTransferCache getEntityStringAttributeTransferCache() {
        if(entityStringAttributeTransferCache == null)
            entityStringAttributeTransferCache = new EntityStringAttributeTransferCache();

        return entityStringAttributeTransferCache;
    }

    public EntityTimeDefaultTransferCache getEntityTimeDefaultTransferCache() {
        if(entityTimeDefaultTransferCache == null)
            entityTimeDefaultTransferCache = new EntityTimeDefaultTransferCache();

        return entityTimeDefaultTransferCache;
    }

    public EntityTimeAttributeTransferCache getEntityTimeAttributeTransferCache() {
        if(entityTimeAttributeTransferCache == null)
            entityTimeAttributeTransferCache = new EntityTimeAttributeTransferCache();

        return entityTimeAttributeTransferCache;
    }

    public EntityAttributeEntityTypeTransferCache getEntityAttributeEntityTypeTransferCache() {
        if(entityAttributeEntityTypeTransferCache == null)
            entityAttributeEntityTypeTransferCache = new EntityAttributeEntityTypeTransferCache();
        
        return entityAttributeEntityTypeTransferCache;
    }
    
    public EntityEntityAttributeTransferCache getEntityEntityAttributeTransferCache() {
        if(entityEntityAttributeTransferCache == null)
            entityEntityAttributeTransferCache = new EntityEntityAttributeTransferCache();
        
        return entityEntityAttributeTransferCache;
    }
    
    public EntityCollectionAttributeTransferCache getEntityCollectionAttributeTransferCache() {
        if(entityCollectionAttributeTransferCache == null)
            entityCollectionAttributeTransferCache = new EntityCollectionAttributeTransferCache();
        
        return entityCollectionAttributeTransferCache;
    }
    
    public CommandMessageTypeTransferCache getCommandMessageTypeTransferCache() {
        if(commandMessageTypeTransferCache == null)
            commandMessageTypeTransferCache = new CommandMessageTypeTransferCache();
        
        return commandMessageTypeTransferCache;
    }
    
    public CommandMessageTypeDescriptionTransferCache getCommandMessageTypeDescriptionTransferCache() {
        if(commandMessageTypeDescriptionTransferCache == null)
            commandMessageTypeDescriptionTransferCache = new CommandMessageTypeDescriptionTransferCache();
        
        return commandMessageTypeDescriptionTransferCache;
    }
    
    public BaseEncryptionKeyTransferCache getBaseEncryptionKeyTransferCache() {
        if(baseEncryptionKeyTransferCache == null)
            baseEncryptionKeyTransferCache = new BaseEncryptionKeyTransferCache();
        
        return baseEncryptionKeyTransferCache;
    }
    
    public ProtocolTransferCache getProtocolTransferCache() {
        if(protocolTransferCache == null)
            protocolTransferCache = new ProtocolTransferCache();

        return protocolTransferCache;
    }

    public ProtocolDescriptionTransferCache getProtocolDescriptionTransferCache() {
        if(protocolDescriptionTransferCache == null)
            protocolDescriptionTransferCache = new ProtocolDescriptionTransferCache();

        return protocolDescriptionTransferCache;
    }

    public ServiceTransferCache getServiceTransferCache() {
        if(serviceTransferCache == null)
            serviceTransferCache = new ServiceTransferCache();

        return serviceTransferCache;
    }

    public ServiceDescriptionTransferCache getServiceDescriptionTransferCache() {
        if(serviceDescriptionTransferCache == null)
            serviceDescriptionTransferCache = new ServiceDescriptionTransferCache();

        return serviceDescriptionTransferCache;
    }

    public CacheEntryTransferCache getCacheEntryTransferCache() {
        if(cacheEntryTransferCache == null)
            cacheEntryTransferCache = new CacheEntryTransferCache();

        return cacheEntryTransferCache;
    }

    public CacheEntryDependencyTransferCache getCacheEntryDependencyTransferCache() {
        if(cacheEntryDependencyTransferCache == null)
            cacheEntryDependencyTransferCache = new CacheEntryDependencyTransferCache();

        return cacheEntryDependencyTransferCache;
    }

    public ApplicationTransferCache getApplicationTransferCache() {
        if(applicationTransferCache == null)
            applicationTransferCache = new ApplicationTransferCache();

        return applicationTransferCache;
    }

    public ApplicationDescriptionTransferCache getApplicationDescriptionTransferCache() {
        if(applicationDescriptionTransferCache == null)
            applicationDescriptionTransferCache = new ApplicationDescriptionTransferCache();

        return applicationDescriptionTransferCache;
    }

    public EditorTransferCache getEditorTransferCache() {
        if(editorTransferCache == null)
            editorTransferCache = new EditorTransferCache();

        return editorTransferCache;
    }

    public EditorDescriptionTransferCache getEditorDescriptionTransferCache() {
        if(editorDescriptionTransferCache == null)
            editorDescriptionTransferCache = new EditorDescriptionTransferCache();

        return editorDescriptionTransferCache;
    }

    public ApplicationEditorTransferCache getApplicationEditorTransferCache() {
        if(applicationEditorTransferCache == null)
            applicationEditorTransferCache = new ApplicationEditorTransferCache();

        return applicationEditorTransferCache;
    }

    public ApplicationEditorUseTransferCache getApplicationEditorUseTransferCache() {
        if(applicationEditorUseTransferCache == null)
            applicationEditorUseTransferCache = new ApplicationEditorUseTransferCache();

        return applicationEditorUseTransferCache;
    }

    public ApplicationEditorUseDescriptionTransferCache getApplicationEditorUseDescriptionTransferCache() {
        if(applicationEditorUseDescriptionTransferCache == null)
            applicationEditorUseDescriptionTransferCache = new ApplicationEditorUseDescriptionTransferCache();

        return applicationEditorUseDescriptionTransferCache;
    }

    public ColorTransferCache getColorTransferCache() {
        if(colorTransferCache == null)
            colorTransferCache = new ColorTransferCache();

        return colorTransferCache;
    }

    public ColorDescriptionTransferCache getColorDescriptionTransferCache() {
        if(colorDescriptionTransferCache == null)
            colorDescriptionTransferCache = new ColorDescriptionTransferCache();

        return colorDescriptionTransferCache;
    }

    public FontStyleTransferCache getFontStyleTransferCache() {
        if(fontStyleTransferCache == null)
            fontStyleTransferCache = new FontStyleTransferCache();

        return fontStyleTransferCache;
    }

    public FontStyleDescriptionTransferCache getFontStyleDescriptionTransferCache() {
        if(fontStyleDescriptionTransferCache == null)
            fontStyleDescriptionTransferCache = new FontStyleDescriptionTransferCache();

        return fontStyleDescriptionTransferCache;
    }

    public FontWeightTransferCache getFontWeightTransferCache() {
        if(fontWeightTransferCache == null)
            fontWeightTransferCache = new FontWeightTransferCache();

        return fontWeightTransferCache;
    }

    public FontWeightDescriptionTransferCache getFontWeightDescriptionTransferCache() {
        if(fontWeightDescriptionTransferCache == null)
            fontWeightDescriptionTransferCache = new FontWeightDescriptionTransferCache();

        return fontWeightDescriptionTransferCache;
    }

    public TextDecorationTransferCache getTextDecorationTransferCache() {
        if(textDecorationTransferCache == null)
            textDecorationTransferCache = new TextDecorationTransferCache();

        return textDecorationTransferCache;
    }

    public TextDecorationDescriptionTransferCache getTextDecorationDescriptionTransferCache() {
        if(textDecorationDescriptionTransferCache == null)
            textDecorationDescriptionTransferCache = new TextDecorationDescriptionTransferCache();

        return textDecorationDescriptionTransferCache;
    }

    public TextTransformationTransferCache getTextTransformationTransferCache() {
        if(textTransformationTransferCache == null)
            textTransformationTransferCache = new TextTransformationTransferCache();

        return textTransformationTransferCache;
    }

    public TextTransformationDescriptionTransferCache getTextTransformationDescriptionTransferCache() {
        if(textTransformationDescriptionTransferCache == null)
            textTransformationDescriptionTransferCache = new TextTransformationDescriptionTransferCache();

        return textTransformationDescriptionTransferCache;
    }

    public AppearanceTransferCache getAppearanceTransferCache() {
        if(appearanceTransferCache == null)
            appearanceTransferCache = new AppearanceTransferCache();

        return appearanceTransferCache;
    }

    public AppearanceDescriptionTransferCache getAppearanceDescriptionTransferCache() {
        if(appearanceDescriptionTransferCache == null)
            appearanceDescriptionTransferCache = new AppearanceDescriptionTransferCache();

        return appearanceDescriptionTransferCache;
    }

    public AppearanceTextDecorationTransferCache getAppearanceTextDecorationTransferCache() {
        if(appearanceTextDecorationTransferCache == null)
            appearanceTextDecorationTransferCache = new AppearanceTextDecorationTransferCache();

        return appearanceTextDecorationTransferCache;
    }

    public AppearanceTextTransformationTransferCache getAppearanceTextTransformationTransferCache() {
        if(appearanceTextTransformationTransferCache == null)
            appearanceTextTransformationTransferCache = new AppearanceTextTransformationTransferCache();

        return appearanceTextTransformationTransferCache;
    }

    public EntityAppearanceTransferCache getEntityAppearanceTransferCache() {
        if(entityAppearanceTransferCache == null)
            entityAppearanceTransferCache = new EntityAppearanceTransferCache();
        
        return entityAppearanceTransferCache;
    }
    
}
