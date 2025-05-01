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
    protected PartyApplicationEditorUseTransferCache partyApplicationEditorUseTransferCache;
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
    public CoreTransferCaches(UserVisit userVisit) {
        super(userVisit);
    }
    
    public ComponentVendorTransferCache getComponentVendorTransferCache() {
        if(componentVendorTransferCache == null)
            componentVendorTransferCache = new ComponentVendorTransferCache(userVisit);

        return componentVendorTransferCache;
    }

    public EntityTypeTransferCache getEntityTypeTransferCache() {
        if(entityTypeTransferCache == null)
            entityTypeTransferCache = new EntityTypeTransferCache(userVisit);
        
        return entityTypeTransferCache;
    }
    
    public EntityTypeDescriptionTransferCache getEntityTypeDescriptionTransferCache() {
        if(entityTypeDescriptionTransferCache == null)
            entityTypeDescriptionTransferCache = new EntityTypeDescriptionTransferCache(userVisit);
        
        return entityTypeDescriptionTransferCache;
    }
    
    public CommandTransferCache getCommandTransferCache() {
        if(commandTransferCache == null)
            commandTransferCache = new CommandTransferCache(userVisit);
        
        return commandTransferCache;
    }
    
    public CommandDescriptionTransferCache getCommandDescriptionTransferCache() {
        if(commandDescriptionTransferCache == null)
            commandDescriptionTransferCache = new CommandDescriptionTransferCache(userVisit);
        
        return commandDescriptionTransferCache;
    }
    
    public CommandMessageTransferCache getCommandMessageTransferCache() {
        if(commandMessageTransferCache == null)
            commandMessageTransferCache = new CommandMessageTransferCache(userVisit);
        
        return commandMessageTransferCache;
    }
    
    public CommandMessageTranslationTransferCache getCommandMessageTranslationTransferCache() {
        if(commandMessageTranslationTransferCache == null)
            commandMessageTranslationTransferCache = new CommandMessageTranslationTransferCache(userVisit);
        
        return commandMessageTranslationTransferCache;
    }

    public EntityAliasTransferCache getEntityAliasTransferCache() {
        if(entityAliasTransferCache == null)
            entityAliasTransferCache = new EntityAliasTransferCache(userVisit);

        return entityAliasTransferCache;
    }

    public EntityAliasTypeTransferCache getEntityAliasTypeTransferCache() {
        if(entityAliasTypeTransferCache == null)
            entityAliasTypeTransferCache = new EntityAliasTypeTransferCache(userVisit);

        return entityAliasTypeTransferCache;
    }

    public EntityAliasTypeDescriptionTransferCache getEntityAliasTypeDescriptionTransferCache() {
        if(entityAliasTypeDescriptionTransferCache == null)
            entityAliasTypeDescriptionTransferCache = new EntityAliasTypeDescriptionTransferCache(userVisit);

        return entityAliasTypeDescriptionTransferCache;
    }

    public EntityAttributeTransferCache getEntityAttributeTransferCache() {
        if(entityAttributeTransferCache == null)
            entityAttributeTransferCache = new EntityAttributeTransferCache(userVisit);

        return entityAttributeTransferCache;
    }

    public EntityAttributeDescriptionTransferCache getEntityAttributeDescriptionTransferCache() {
        if(entityAttributeDescriptionTransferCache == null)
            entityAttributeDescriptionTransferCache = new EntityAttributeDescriptionTransferCache(userVisit);

        return entityAttributeDescriptionTransferCache;
    }

    public EntityAttributeEntityAttributeGroupTransferCache getEntityAttributeEntityAttributeGroupTransferCache() {
        if(entityAttributeEntityAttributeGroupTransferCache == null)
            entityAttributeEntityAttributeGroupTransferCache = new EntityAttributeEntityAttributeGroupTransferCache(userVisit);
        
        return entityAttributeEntityAttributeGroupTransferCache;
    }
    
    public EntityAttributeGroupTransferCache getEntityAttributeGroupTransferCache() {
        if(entityAttributeGroupTransferCache == null)
            entityAttributeGroupTransferCache = new EntityAttributeGroupTransferCache(userVisit);
        
        return entityAttributeGroupTransferCache;
    }
    
    public EntityAttributeGroupDescriptionTransferCache getEntityAttributeGroupDescriptionTransferCache() {
        if(entityAttributeGroupDescriptionTransferCache == null)
            entityAttributeGroupDescriptionTransferCache = new EntityAttributeGroupDescriptionTransferCache(userVisit);
        
        return entityAttributeGroupDescriptionTransferCache;
    }
    
    public EntityListItemTransferCache getEntityListItemTransferCache() {
        if(entityListItemTransferCache == null)
            entityListItemTransferCache = new EntityListItemTransferCache(userVisit);
        
        return entityListItemTransferCache;
    }
    
    public EntityListItemDescriptionTransferCache getEntityListItemDescriptionTransferCache() {
        if(entityListItemDescriptionTransferCache == null)
            entityListItemDescriptionTransferCache = new EntityListItemDescriptionTransferCache(userVisit);
        
        return entityListItemDescriptionTransferCache;
    }
    
    public EntityIntegerRangeTransferCache getEntityIntegerRangeTransferCache() {
        if(entityIntegerRangeTransferCache == null)
            entityIntegerRangeTransferCache = new EntityIntegerRangeTransferCache(userVisit);
        
        return entityIntegerRangeTransferCache;
    }
    
    public EntityIntegerRangeDescriptionTransferCache getEntityIntegerRangeDescriptionTransferCache() {
        if(entityIntegerRangeDescriptionTransferCache == null)
            entityIntegerRangeDescriptionTransferCache = new EntityIntegerRangeDescriptionTransferCache(userVisit);
        
        return entityIntegerRangeDescriptionTransferCache;
    }
    
    public EntityLongRangeTransferCache getEntityLongRangeTransferCache() {
        if(entityLongRangeTransferCache == null)
            entityLongRangeTransferCache = new EntityLongRangeTransferCache(userVisit);
        
        return entityLongRangeTransferCache;
    }
    
    public EntityLongRangeDescriptionTransferCache getEntityLongRangeDescriptionTransferCache() {
        if(entityLongRangeDescriptionTransferCache == null)
            entityLongRangeDescriptionTransferCache = new EntityLongRangeDescriptionTransferCache(userVisit);
        
        return entityLongRangeDescriptionTransferCache;
    }
    
    public EntityAttributeTypeTransferCache getEntityAttributeTypeTransferCache() {
        if(entityAttributeTypeTransferCache == null)
            entityAttributeTypeTransferCache = new EntityAttributeTypeTransferCache(userVisit);
        
        return entityAttributeTypeTransferCache;
    }
    
    public EventTypeTransferCache getEventTypeTransferCache() {
        if(eventTypeTransferCache == null)
            eventTypeTransferCache = new EventTypeTransferCache(userVisit);
        
        return eventTypeTransferCache;
    }
    
    public MimeTypeTransferCache getMimeTypeTransferCache() {
        if(mimeTypeTransferCache == null)
            mimeTypeTransferCache = new MimeTypeTransferCache(userVisit);

        return mimeTypeTransferCache;
    }

    public MimeTypeDescriptionTransferCache getMimeTypeDescriptionTransferCache() {
        if(mimeTypeDescriptionTransferCache == null)
            mimeTypeDescriptionTransferCache = new MimeTypeDescriptionTransferCache(userVisit);

        return mimeTypeDescriptionTransferCache;
    }

    public EntityTimeTransferCache getEntityTimeTransferCache() {
        if(entityTimeTransferCache == null)
            entityTimeTransferCache = new EntityTimeTransferCache(userVisit);

        return entityTimeTransferCache;
    }

    public EntityVisitTransferCache getEntityVisitTransferCache() {
        if(entityVisitTransferCache == null)
            entityVisitTransferCache = new EntityVisitTransferCache(userVisit);

        return entityVisitTransferCache;
    }

    public EntityInstanceTransferCache getEntityInstanceTransferCache() {
        if(entityInstanceTransferCache == null)
            entityInstanceTransferCache = new EntityInstanceTransferCache(userVisit);
        
        return entityInstanceTransferCache;
    }
    
    public EventGroupTransferCache getEventGroupTransferCache() {
        if(eventGroupTransferCache == null)
            eventGroupTransferCache = new EventGroupTransferCache(userVisit);
        
        return eventGroupTransferCache;
    }
    
    public EventTransferCache getEventTransferCache() {
        if(eventTransferCache == null)
            eventTransferCache = new EventTransferCache(userVisit);
        
        return eventTransferCache;
    }
    
    public EntityLockTransferCache getEntityLockTransferCache() {
        if(entityLockTransferCache == null)
            entityLockTransferCache = new EntityLockTransferCache(userVisit);
        
        return entityLockTransferCache;
    }
    
    public MimeTypeUsageTypeTransferCache getMimeTypeUsageTypeTransferCache() {
        if(mimeTypeUsageTypeTransferCache == null)
            mimeTypeUsageTypeTransferCache = new MimeTypeUsageTypeTransferCache(userVisit);
        
        return mimeTypeUsageTypeTransferCache;
    }
    
    public MimeTypeUsageTransferCache getMimeTypeUsageTransferCache() {
        if(mimeTypeUsageTransferCache == null)
            mimeTypeUsageTransferCache = new MimeTypeUsageTransferCache(userVisit);
        
        return mimeTypeUsageTransferCache;
    }
    
    public ServerTransferCache getServerTransferCache() {
        if(serverTransferCache == null)
            serverTransferCache = new ServerTransferCache(userVisit);

        return serverTransferCache;
    }

    public ServerDescriptionTransferCache getServerDescriptionTransferCache() {
        if(serverDescriptionTransferCache == null)
            serverDescriptionTransferCache = new ServerDescriptionTransferCache(userVisit);

        return serverDescriptionTransferCache;
    }

    public ServerServiceTransferCache getServerServiceTransferCache() {
        if(serverServiceTransferCache == null)
            serverServiceTransferCache = new ServerServiceTransferCache(userVisit);

        return serverServiceTransferCache;
    }

    public MimeTypeFileExtensionTransferCache getMimeTypeFileExtensionTransferCache() {
        if(mimeTypeFileExtensionTransferCache == null)
            mimeTypeFileExtensionTransferCache = new MimeTypeFileExtensionTransferCache(userVisit);
        
        return mimeTypeFileExtensionTransferCache;
    }
    
    public EntityBlobAttributeTransferCache getEntityBlobAttributeTransferCache() {
        if(entityBlobAttributeTransferCache == null)
            entityBlobAttributeTransferCache = new EntityBlobAttributeTransferCache(userVisit);
        
        return entityBlobAttributeTransferCache;
    }

    public EntityBooleanDefaultTransferCache getEntityBooleanDefaultTransferCache() {
        if(entityBooleanDefaultTransferCache == null)
            entityBooleanDefaultTransferCache = new EntityBooleanDefaultTransferCache(userVisit);

        return entityBooleanDefaultTransferCache;
    }

    public EntityBooleanAttributeTransferCache getEntityBooleanAttributeTransferCache() {
        if(entityBooleanAttributeTransferCache == null)
            entityBooleanAttributeTransferCache = new EntityBooleanAttributeTransferCache(userVisit);

        return entityBooleanAttributeTransferCache;
    }

    public EntityClobAttributeTransferCache getEntityClobAttributeTransferCache() {
        if(entityClobAttributeTransferCache == null)
            entityClobAttributeTransferCache = new EntityClobAttributeTransferCache(userVisit);
        
        return entityClobAttributeTransferCache;
    }

    public EntityDateDefaultTransferCache getEntityDateDefaultTransferCache() {
        if(entityDateDefaultTransferCache == null)
            entityDateDefaultTransferCache = new EntityDateDefaultTransferCache(userVisit);

        return entityDateDefaultTransferCache;
    }

    public EntityDateAttributeTransferCache getEntityDateAttributeTransferCache() {
        if(entityDateAttributeTransferCache == null)
            entityDateAttributeTransferCache = new EntityDateAttributeTransferCache(userVisit);

        return entityDateAttributeTransferCache;
    }

    public EntityIntegerDefaultTransferCache getEntityIntegerDefaultTransferCache() {
        if(entityIntegerDefaultTransferCache == null)
            entityIntegerDefaultTransferCache = new EntityIntegerDefaultTransferCache(userVisit);

        return entityIntegerDefaultTransferCache;
    }

    public EntityIntegerAttributeTransferCache getEntityIntegerAttributeTransferCache() {
        if(entityIntegerAttributeTransferCache == null)
            entityIntegerAttributeTransferCache = new EntityIntegerAttributeTransferCache(userVisit);
        
        return entityIntegerAttributeTransferCache;
    }

    public EntityListItemDefaultTransferCache getEntityListItemDefaultTransferCache() {
        if(entityListItemDefaultTransferCache == null)
            entityListItemDefaultTransferCache = new EntityListItemDefaultTransferCache(userVisit);

        return entityListItemDefaultTransferCache;
    }

    public EntityListItemAttributeTransferCache getEntityListItemAttributeTransferCache() {
        if(entityListItemAttributeTransferCache == null)
            entityListItemAttributeTransferCache = new EntityListItemAttributeTransferCache(userVisit);

        return entityListItemAttributeTransferCache;
    }

    public EntityLongDefaultTransferCache getEntityLongDefaultTransferCache() {
        if(entityLongDefaultTransferCache == null)
            entityLongDefaultTransferCache = new EntityLongDefaultTransferCache(userVisit);

        return entityLongDefaultTransferCache;
    }

    public EntityLongAttributeTransferCache getEntityLongAttributeTransferCache() {
        if(entityLongAttributeTransferCache == null)
            entityLongAttributeTransferCache = new EntityLongAttributeTransferCache(userVisit);
        
        return entityLongAttributeTransferCache;
    }

    public EntityMultipleListItemDefaultTransferCache getEntityMultipleListItemDefaultTransferCache() {
        if(entityMultipleListItemDefaultTransferCache == null)
            entityMultipleListItemDefaultTransferCache = new EntityMultipleListItemDefaultTransferCache(userVisit);

        return entityMultipleListItemDefaultTransferCache;
    }

    public EntityMultipleListItemAttributeTransferCache getEntityMultipleListItemAttributeTransferCache() {
        if(entityMultipleListItemAttributeTransferCache == null)
            entityMultipleListItemAttributeTransferCache = new EntityMultipleListItemAttributeTransferCache(userVisit);

        return entityMultipleListItemAttributeTransferCache;
    }

    public EntityNameAttributeTransferCache getEntityNameAttributeTransferCache() {
        if(entityNameAttributeTransferCache == null)
            entityNameAttributeTransferCache = new EntityNameAttributeTransferCache(userVisit);
        
        return entityNameAttributeTransferCache;
    }

    public EntityGeoPointDefaultTransferCache getEntityGeoPointDefaultTransferCache() {
        if(entityGeoPointDefaultTransferCache == null)
            entityGeoPointDefaultTransferCache = new EntityGeoPointDefaultTransferCache(userVisit);

        return entityGeoPointDefaultTransferCache;
    }

    public EntityGeoPointAttributeTransferCache getEntityGeoPointAttributeTransferCache() {
        if(entityGeoPointAttributeTransferCache == null)
            entityGeoPointAttributeTransferCache = new EntityGeoPointAttributeTransferCache(userVisit);

        return entityGeoPointAttributeTransferCache;
    }

    public EntityStringDefaultTransferCache getEntityStringDefaultTransferCache() {
        if(entityStringDefaultTransferCache == null)
            entityStringDefaultTransferCache = new EntityStringDefaultTransferCache(userVisit);

        return entityStringDefaultTransferCache;
    }

    public EntityStringAttributeTransferCache getEntityStringAttributeTransferCache() {
        if(entityStringAttributeTransferCache == null)
            entityStringAttributeTransferCache = new EntityStringAttributeTransferCache(userVisit);

        return entityStringAttributeTransferCache;
    }

    public EntityTimeDefaultTransferCache getEntityTimeDefaultTransferCache() {
        if(entityTimeDefaultTransferCache == null)
            entityTimeDefaultTransferCache = new EntityTimeDefaultTransferCache(userVisit);

        return entityTimeDefaultTransferCache;
    }

    public EntityTimeAttributeTransferCache getEntityTimeAttributeTransferCache() {
        if(entityTimeAttributeTransferCache == null)
            entityTimeAttributeTransferCache = new EntityTimeAttributeTransferCache(userVisit);

        return entityTimeAttributeTransferCache;
    }

    public EntityAttributeEntityTypeTransferCache getEntityAttributeEntityTypeTransferCache() {
        if(entityAttributeEntityTypeTransferCache == null)
            entityAttributeEntityTypeTransferCache = new EntityAttributeEntityTypeTransferCache(userVisit);
        
        return entityAttributeEntityTypeTransferCache;
    }
    
    public EntityEntityAttributeTransferCache getEntityEntityAttributeTransferCache() {
        if(entityEntityAttributeTransferCache == null)
            entityEntityAttributeTransferCache = new EntityEntityAttributeTransferCache(userVisit);
        
        return entityEntityAttributeTransferCache;
    }
    
    public EntityCollectionAttributeTransferCache getEntityCollectionAttributeTransferCache() {
        if(entityCollectionAttributeTransferCache == null)
            entityCollectionAttributeTransferCache = new EntityCollectionAttributeTransferCache(userVisit);
        
        return entityCollectionAttributeTransferCache;
    }
    
    public CommandMessageTypeTransferCache getCommandMessageTypeTransferCache() {
        if(commandMessageTypeTransferCache == null)
            commandMessageTypeTransferCache = new CommandMessageTypeTransferCache(userVisit);
        
        return commandMessageTypeTransferCache;
    }
    
    public CommandMessageTypeDescriptionTransferCache getCommandMessageTypeDescriptionTransferCache() {
        if(commandMessageTypeDescriptionTransferCache == null)
            commandMessageTypeDescriptionTransferCache = new CommandMessageTypeDescriptionTransferCache(userVisit);
        
        return commandMessageTypeDescriptionTransferCache;
    }
    
    public BaseEncryptionKeyTransferCache getBaseEncryptionKeyTransferCache() {
        if(baseEncryptionKeyTransferCache == null)
            baseEncryptionKeyTransferCache = new BaseEncryptionKeyTransferCache(userVisit);
        
        return baseEncryptionKeyTransferCache;
    }
    
    public ProtocolTransferCache getProtocolTransferCache() {
        if(protocolTransferCache == null)
            protocolTransferCache = new ProtocolTransferCache(userVisit);

        return protocolTransferCache;
    }

    public ProtocolDescriptionTransferCache getProtocolDescriptionTransferCache() {
        if(protocolDescriptionTransferCache == null)
            protocolDescriptionTransferCache = new ProtocolDescriptionTransferCache(userVisit);

        return protocolDescriptionTransferCache;
    }

    public ServiceTransferCache getServiceTransferCache() {
        if(serviceTransferCache == null)
            serviceTransferCache = new ServiceTransferCache(userVisit);

        return serviceTransferCache;
    }

    public ServiceDescriptionTransferCache getServiceDescriptionTransferCache() {
        if(serviceDescriptionTransferCache == null)
            serviceDescriptionTransferCache = new ServiceDescriptionTransferCache(userVisit);

        return serviceDescriptionTransferCache;
    }

    public CacheEntryTransferCache getCacheEntryTransferCache() {
        if(cacheEntryTransferCache == null)
            cacheEntryTransferCache = new CacheEntryTransferCache(userVisit);

        return cacheEntryTransferCache;
    }

    public CacheEntryDependencyTransferCache getCacheEntryDependencyTransferCache() {
        if(cacheEntryDependencyTransferCache == null)
            cacheEntryDependencyTransferCache = new CacheEntryDependencyTransferCache(userVisit);

        return cacheEntryDependencyTransferCache;
    }

    public ApplicationTransferCache getApplicationTransferCache() {
        if(applicationTransferCache == null)
            applicationTransferCache = new ApplicationTransferCache(userVisit);

        return applicationTransferCache;
    }

    public ApplicationDescriptionTransferCache getApplicationDescriptionTransferCache() {
        if(applicationDescriptionTransferCache == null)
            applicationDescriptionTransferCache = new ApplicationDescriptionTransferCache(userVisit);

        return applicationDescriptionTransferCache;
    }

    public EditorTransferCache getEditorTransferCache() {
        if(editorTransferCache == null)
            editorTransferCache = new EditorTransferCache(userVisit);

        return editorTransferCache;
    }

    public EditorDescriptionTransferCache getEditorDescriptionTransferCache() {
        if(editorDescriptionTransferCache == null)
            editorDescriptionTransferCache = new EditorDescriptionTransferCache(userVisit);

        return editorDescriptionTransferCache;
    }

    public ApplicationEditorTransferCache getApplicationEditorTransferCache() {
        if(applicationEditorTransferCache == null)
            applicationEditorTransferCache = new ApplicationEditorTransferCache(userVisit);

        return applicationEditorTransferCache;
    }

    public ApplicationEditorUseTransferCache getApplicationEditorUseTransferCache() {
        if(applicationEditorUseTransferCache == null)
            applicationEditorUseTransferCache = new ApplicationEditorUseTransferCache(userVisit);

        return applicationEditorUseTransferCache;
    }

    public ApplicationEditorUseDescriptionTransferCache getApplicationEditorUseDescriptionTransferCache() {
        if(applicationEditorUseDescriptionTransferCache == null)
            applicationEditorUseDescriptionTransferCache = new ApplicationEditorUseDescriptionTransferCache(userVisit);

        return applicationEditorUseDescriptionTransferCache;
    }

    public PartyApplicationEditorUseTransferCache getPartyApplicationEditorUseTransferCache() {
        if(partyApplicationEditorUseTransferCache == null)
            partyApplicationEditorUseTransferCache = new PartyApplicationEditorUseTransferCache(userVisit);

        return partyApplicationEditorUseTransferCache;
    }

    public ColorTransferCache getColorTransferCache() {
        if(colorTransferCache == null)
            colorTransferCache = new ColorTransferCache(userVisit);

        return colorTransferCache;
    }

    public ColorDescriptionTransferCache getColorDescriptionTransferCache() {
        if(colorDescriptionTransferCache == null)
            colorDescriptionTransferCache = new ColorDescriptionTransferCache(userVisit);

        return colorDescriptionTransferCache;
    }

    public FontStyleTransferCache getFontStyleTransferCache() {
        if(fontStyleTransferCache == null)
            fontStyleTransferCache = new FontStyleTransferCache(userVisit);

        return fontStyleTransferCache;
    }

    public FontStyleDescriptionTransferCache getFontStyleDescriptionTransferCache() {
        if(fontStyleDescriptionTransferCache == null)
            fontStyleDescriptionTransferCache = new FontStyleDescriptionTransferCache(userVisit);

        return fontStyleDescriptionTransferCache;
    }

    public FontWeightTransferCache getFontWeightTransferCache() {
        if(fontWeightTransferCache == null)
            fontWeightTransferCache = new FontWeightTransferCache(userVisit);

        return fontWeightTransferCache;
    }

    public FontWeightDescriptionTransferCache getFontWeightDescriptionTransferCache() {
        if(fontWeightDescriptionTransferCache == null)
            fontWeightDescriptionTransferCache = new FontWeightDescriptionTransferCache(userVisit);

        return fontWeightDescriptionTransferCache;
    }

    public TextDecorationTransferCache getTextDecorationTransferCache() {
        if(textDecorationTransferCache == null)
            textDecorationTransferCache = new TextDecorationTransferCache(userVisit);

        return textDecorationTransferCache;
    }

    public TextDecorationDescriptionTransferCache getTextDecorationDescriptionTransferCache() {
        if(textDecorationDescriptionTransferCache == null)
            textDecorationDescriptionTransferCache = new TextDecorationDescriptionTransferCache(userVisit);

        return textDecorationDescriptionTransferCache;
    }

    public TextTransformationTransferCache getTextTransformationTransferCache() {
        if(textTransformationTransferCache == null)
            textTransformationTransferCache = new TextTransformationTransferCache(userVisit);

        return textTransformationTransferCache;
    }

    public TextTransformationDescriptionTransferCache getTextTransformationDescriptionTransferCache() {
        if(textTransformationDescriptionTransferCache == null)
            textTransformationDescriptionTransferCache = new TextTransformationDescriptionTransferCache(userVisit);

        return textTransformationDescriptionTransferCache;
    }

    public AppearanceTransferCache getAppearanceTransferCache() {
        if(appearanceTransferCache == null)
            appearanceTransferCache = new AppearanceTransferCache(userVisit);

        return appearanceTransferCache;
    }

    public AppearanceDescriptionTransferCache getAppearanceDescriptionTransferCache() {
        if(appearanceDescriptionTransferCache == null)
            appearanceDescriptionTransferCache = new AppearanceDescriptionTransferCache(userVisit);

        return appearanceDescriptionTransferCache;
    }

    public AppearanceTextDecorationTransferCache getAppearanceTextDecorationTransferCache() {
        if(appearanceTextDecorationTransferCache == null)
            appearanceTextDecorationTransferCache = new AppearanceTextDecorationTransferCache(userVisit);

        return appearanceTextDecorationTransferCache;
    }

    public AppearanceTextTransformationTransferCache getAppearanceTextTransformationTransferCache() {
        if(appearanceTextTransformationTransferCache == null)
            appearanceTextTransformationTransferCache = new AppearanceTextTransformationTransferCache(userVisit);

        return appearanceTextTransformationTransferCache;
    }

    public EntityAppearanceTransferCache getEntityAppearanceTransferCache() {
        if(entityAppearanceTransferCache == null)
            entityAppearanceTransferCache = new EntityAppearanceTransferCache(userVisit);
        
        return entityAppearanceTransferCache;
    }
    
}
