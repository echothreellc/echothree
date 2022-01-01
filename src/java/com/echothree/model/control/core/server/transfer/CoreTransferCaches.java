// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.transfer.BaseTransferCaches;

public class CoreTransferCaches
        extends BaseTransferCaches {
    
    protected CoreControl coreControl;
    
    protected ComponentVendorTransferCache componentVendorTransferCache;
    protected EntityTypeTransferCache entityTypeTransferCache;
    protected EntityTypeDescriptionTransferCache entityTypeDescriptionTransferCache;
    protected CommandTransferCache commandTransferCache;
    protected CommandDescriptionTransferCache commandDescriptionTransferCache;
    protected CommandMessageTransferCache commandMessageTransferCache;
    protected CommandMessageTranslationTransferCache commandMessageTranslationTransferCache;
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
    protected EntityBooleanAttributeTransferCache entityBooleanAttributeTransferCache;
    protected EntityClobAttributeTransferCache entityClobAttributeTransferCache;
    protected EntityDateAttributeTransferCache entityDateAttributeTransferCache;
    protected EntityIntegerAttributeTransferCache entityIntegerAttributeTransferCache;
    protected EntityListItemAttributeTransferCache entityListItemAttributeTransferCache;
    protected EntityLongAttributeTransferCache entityLongAttributeTransferCache;
    protected EntityMultipleListItemAttributeTransferCache entityMultipleListItemAttributeTransferCache;
    protected EntityNameAttributeTransferCache entityNameAttributeTransferCache;
    protected EntityGeoPointAttributeTransferCache entityGeoPointAttributeTransferCache;
    protected EntityStringAttributeTransferCache entityStringAttributeTransferCache;
    protected EntityTimeAttributeTransferCache entityTimeAttributeTransferCache;
    protected EntityAttributeEntityTypeTransferCache entityAttributeEntityTypeTransferCache;
    protected EntityEntityAttributeTransferCache entityEntityAttributeTransferCache;
    protected EntityCollectionAttributeTransferCache entityCollectionAttributeTransferCache;
    protected CommandMessageTypeTransferCache commandMessageTypeTransferCache;
    protected CommandMessageTypeDescriptionTransferCache commandMessageTypeDescriptionTransferCache;
    protected BaseEncryptionKeyTransferCache baseEncryptionKeyTransferCache;
    protected PartyEntityTypeTransferCache partyEntityTypeTransferCache;
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
    public CoreTransferCaches(UserVisit userVisit, CoreControl coreControl) {
        super(userVisit);
        
        this.coreControl = coreControl;
    }
    
    public ComponentVendorTransferCache getComponentVendorTransferCache() {
        if(componentVendorTransferCache == null)
            componentVendorTransferCache = new ComponentVendorTransferCache(userVisit, coreControl);

        return componentVendorTransferCache;
    }

    public EntityTypeTransferCache getEntityTypeTransferCache() {
        if(entityTypeTransferCache == null)
            entityTypeTransferCache = new EntityTypeTransferCache(userVisit, coreControl);
        
        return entityTypeTransferCache;
    }
    
    public EntityTypeDescriptionTransferCache getEntityTypeDescriptionTransferCache() {
        if(entityTypeDescriptionTransferCache == null)
            entityTypeDescriptionTransferCache = new EntityTypeDescriptionTransferCache(userVisit, coreControl);
        
        return entityTypeDescriptionTransferCache;
    }
    
    public CommandTransferCache getCommandTransferCache() {
        if(commandTransferCache == null)
            commandTransferCache = new CommandTransferCache(userVisit, coreControl);
        
        return commandTransferCache;
    }
    
    public CommandDescriptionTransferCache getCommandDescriptionTransferCache() {
        if(commandDescriptionTransferCache == null)
            commandDescriptionTransferCache = new CommandDescriptionTransferCache(userVisit, coreControl);
        
        return commandDescriptionTransferCache;
    }
    
    public CommandMessageTransferCache getCommandMessageTransferCache() {
        if(commandMessageTransferCache == null)
            commandMessageTransferCache = new CommandMessageTransferCache(userVisit, coreControl);
        
        return commandMessageTransferCache;
    }
    
    public CommandMessageTranslationTransferCache getCommandMessageTranslationTransferCache() {
        if(commandMessageTranslationTransferCache == null)
            commandMessageTranslationTransferCache = new CommandMessageTranslationTransferCache(userVisit, coreControl);
        
        return commandMessageTranslationTransferCache;
    }
    
    public EntityAttributeTransferCache getEntityAttributeTransferCache() {
        if(entityAttributeTransferCache == null)
            entityAttributeTransferCache = new EntityAttributeTransferCache(userVisit, coreControl);
        
        return entityAttributeTransferCache;
    }
    
    public EntityAttributeDescriptionTransferCache getEntityAttributeDescriptionTransferCache() {
        if(entityAttributeDescriptionTransferCache == null)
            entityAttributeDescriptionTransferCache = new EntityAttributeDescriptionTransferCache(userVisit, coreControl);
        
        return entityAttributeDescriptionTransferCache;
    }
    
    public EntityAttributeEntityAttributeGroupTransferCache getEntityAttributeEntityAttributeGroupTransferCache() {
        if(entityAttributeEntityAttributeGroupTransferCache == null)
            entityAttributeEntityAttributeGroupTransferCache = new EntityAttributeEntityAttributeGroupTransferCache(userVisit, coreControl);
        
        return entityAttributeEntityAttributeGroupTransferCache;
    }
    
    public EntityAttributeGroupTransferCache getEntityAttributeGroupTransferCache() {
        if(entityAttributeGroupTransferCache == null)
            entityAttributeGroupTransferCache = new EntityAttributeGroupTransferCache(userVisit, coreControl);
        
        return entityAttributeGroupTransferCache;
    }
    
    public EntityAttributeGroupDescriptionTransferCache getEntityAttributeGroupDescriptionTransferCache() {
        if(entityAttributeGroupDescriptionTransferCache == null)
            entityAttributeGroupDescriptionTransferCache = new EntityAttributeGroupDescriptionTransferCache(userVisit, coreControl);
        
        return entityAttributeGroupDescriptionTransferCache;
    }
    
    public EntityListItemTransferCache getEntityListItemTransferCache() {
        if(entityListItemTransferCache == null)
            entityListItemTransferCache = new EntityListItemTransferCache(userVisit, coreControl);
        
        return entityListItemTransferCache;
    }
    
    public EntityListItemDescriptionTransferCache getEntityListItemDescriptionTransferCache() {
        if(entityListItemDescriptionTransferCache == null)
            entityListItemDescriptionTransferCache = new EntityListItemDescriptionTransferCache(userVisit, coreControl);
        
        return entityListItemDescriptionTransferCache;
    }
    
    public EntityIntegerRangeTransferCache getEntityIntegerRangeTransferCache() {
        if(entityIntegerRangeTransferCache == null)
            entityIntegerRangeTransferCache = new EntityIntegerRangeTransferCache(userVisit, coreControl);
        
        return entityIntegerRangeTransferCache;
    }
    
    public EntityIntegerRangeDescriptionTransferCache getEntityIntegerRangeDescriptionTransferCache() {
        if(entityIntegerRangeDescriptionTransferCache == null)
            entityIntegerRangeDescriptionTransferCache = new EntityIntegerRangeDescriptionTransferCache(userVisit, coreControl);
        
        return entityIntegerRangeDescriptionTransferCache;
    }
    
    public EntityLongRangeTransferCache getEntityLongRangeTransferCache() {
        if(entityLongRangeTransferCache == null)
            entityLongRangeTransferCache = new EntityLongRangeTransferCache(userVisit, coreControl);
        
        return entityLongRangeTransferCache;
    }
    
    public EntityLongRangeDescriptionTransferCache getEntityLongRangeDescriptionTransferCache() {
        if(entityLongRangeDescriptionTransferCache == null)
            entityLongRangeDescriptionTransferCache = new EntityLongRangeDescriptionTransferCache(userVisit, coreControl);
        
        return entityLongRangeDescriptionTransferCache;
    }
    
    public EntityAttributeTypeTransferCache getEntityAttributeTypeTransferCache() {
        if(entityAttributeTypeTransferCache == null)
            entityAttributeTypeTransferCache = new EntityAttributeTypeTransferCache(userVisit, coreControl);
        
        return entityAttributeTypeTransferCache;
    }
    
    public EventTypeTransferCache getEventTypeTransferCache() {
        if(eventTypeTransferCache == null)
            eventTypeTransferCache = new EventTypeTransferCache(userVisit, coreControl);
        
        return eventTypeTransferCache;
    }
    
    public MimeTypeTransferCache getMimeTypeTransferCache() {
        if(mimeTypeTransferCache == null)
            mimeTypeTransferCache = new MimeTypeTransferCache(userVisit, coreControl);

        return mimeTypeTransferCache;
    }

    public MimeTypeDescriptionTransferCache getMimeTypeDescriptionTransferCache() {
        if(mimeTypeDescriptionTransferCache == null)
            mimeTypeDescriptionTransferCache = new MimeTypeDescriptionTransferCache(userVisit, coreControl);

        return mimeTypeDescriptionTransferCache;
    }

    public EntityTimeTransferCache getEntityTimeTransferCache() {
        if(entityTimeTransferCache == null)
            entityTimeTransferCache = new EntityTimeTransferCache(userVisit, coreControl);
        
        return entityTimeTransferCache;
    }
    
    public EntityInstanceTransferCache getEntityInstanceTransferCache() {
        if(entityInstanceTransferCache == null)
            entityInstanceTransferCache = new EntityInstanceTransferCache(userVisit, coreControl);
        
        return entityInstanceTransferCache;
    }
    
    public EventGroupTransferCache getEventGroupTransferCache() {
        if(eventGroupTransferCache == null)
            eventGroupTransferCache = new EventGroupTransferCache(userVisit, coreControl);
        
        return eventGroupTransferCache;
    }
    
    public EventTransferCache getEventTransferCache() {
        if(eventTransferCache == null)
            eventTransferCache = new EventTransferCache(userVisit, coreControl);
        
        return eventTransferCache;
    }
    
    public EntityLockTransferCache getEntityLockTransferCache() {
        if(entityLockTransferCache == null)
            entityLockTransferCache = new EntityLockTransferCache(userVisit, coreControl);
        
        return entityLockTransferCache;
    }
    
    public MimeTypeUsageTypeTransferCache getMimeTypeUsageTypeTransferCache() {
        if(mimeTypeUsageTypeTransferCache == null)
            mimeTypeUsageTypeTransferCache = new MimeTypeUsageTypeTransferCache(userVisit, coreControl);
        
        return mimeTypeUsageTypeTransferCache;
    }
    
    public MimeTypeUsageTransferCache getMimeTypeUsageTransferCache() {
        if(mimeTypeUsageTransferCache == null)
            mimeTypeUsageTransferCache = new MimeTypeUsageTransferCache(userVisit, coreControl);
        
        return mimeTypeUsageTransferCache;
    }
    
    public ServerTransferCache getServerTransferCache() {
        if(serverTransferCache == null)
            serverTransferCache = new ServerTransferCache(userVisit, coreControl);

        return serverTransferCache;
    }

    public ServerDescriptionTransferCache getServerDescriptionTransferCache() {
        if(serverDescriptionTransferCache == null)
            serverDescriptionTransferCache = new ServerDescriptionTransferCache(userVisit, coreControl);

        return serverDescriptionTransferCache;
    }

    public ServerServiceTransferCache getServerServiceTransferCache() {
        if(serverServiceTransferCache == null)
            serverServiceTransferCache = new ServerServiceTransferCache(userVisit, coreControl);

        return serverServiceTransferCache;
    }

    public MimeTypeFileExtensionTransferCache getMimeTypeFileExtensionTransferCache() {
        if(mimeTypeFileExtensionTransferCache == null)
            mimeTypeFileExtensionTransferCache = new MimeTypeFileExtensionTransferCache(userVisit, coreControl);
        
        return mimeTypeFileExtensionTransferCache;
    }
    
    public EntityBlobAttributeTransferCache getEntityBlobAttributeTransferCache() {
        if(entityBlobAttributeTransferCache == null)
            entityBlobAttributeTransferCache = new EntityBlobAttributeTransferCache(userVisit, coreControl);
        
        return entityBlobAttributeTransferCache;
    }
    
    public EntityBooleanAttributeTransferCache getEntityBooleanAttributeTransferCache() {
        if(entityBooleanAttributeTransferCache == null)
            entityBooleanAttributeTransferCache = new EntityBooleanAttributeTransferCache(userVisit, coreControl);
        
        return entityBooleanAttributeTransferCache;
    }
    
    public EntityClobAttributeTransferCache getEntityClobAttributeTransferCache() {
        if(entityClobAttributeTransferCache == null)
            entityClobAttributeTransferCache = new EntityClobAttributeTransferCache(userVisit, coreControl);
        
        return entityClobAttributeTransferCache;
    }
    
    public EntityDateAttributeTransferCache getEntityDateAttributeTransferCache() {
        if(entityDateAttributeTransferCache == null)
            entityDateAttributeTransferCache = new EntityDateAttributeTransferCache(userVisit, coreControl);
        
        return entityDateAttributeTransferCache;
    }
    
    public EntityIntegerAttributeTransferCache getEntityIntegerAttributeTransferCache() {
        if(entityIntegerAttributeTransferCache == null)
            entityIntegerAttributeTransferCache = new EntityIntegerAttributeTransferCache(userVisit, coreControl);
        
        return entityIntegerAttributeTransferCache;
    }
    
    public EntityListItemAttributeTransferCache getEntityListItemAttributeTransferCache() {
        if(entityListItemAttributeTransferCache == null)
            entityListItemAttributeTransferCache = new EntityListItemAttributeTransferCache(userVisit, coreControl);
        
        return entityListItemAttributeTransferCache;
    }
    
    public EntityLongAttributeTransferCache getEntityLongAttributeTransferCache() {
        if(entityLongAttributeTransferCache == null)
            entityLongAttributeTransferCache = new EntityLongAttributeTransferCache(userVisit, coreControl);
        
        return entityLongAttributeTransferCache;
    }
    
    public EntityMultipleListItemAttributeTransferCache getEntityMultipleListItemAttributeTransferCache() {
        if(entityMultipleListItemAttributeTransferCache == null)
            entityMultipleListItemAttributeTransferCache = new EntityMultipleListItemAttributeTransferCache(userVisit, coreControl);
        
        return entityMultipleListItemAttributeTransferCache;
    }
    
    public EntityNameAttributeTransferCache getEntityNameAttributeTransferCache() {
        if(entityNameAttributeTransferCache == null)
            entityNameAttributeTransferCache = new EntityNameAttributeTransferCache(userVisit, coreControl);
        
        return entityNameAttributeTransferCache;
    }
    
    public EntityGeoPointAttributeTransferCache getEntityGeoPointAttributeTransferCache() {
        if(entityGeoPointAttributeTransferCache == null)
            entityGeoPointAttributeTransferCache = new EntityGeoPointAttributeTransferCache(userVisit, coreControl);
        
        return entityGeoPointAttributeTransferCache;
    }
    
    public EntityStringAttributeTransferCache getEntityStringAttributeTransferCache() {
        if(entityStringAttributeTransferCache == null)
            entityStringAttributeTransferCache = new EntityStringAttributeTransferCache(userVisit, coreControl);
        
        return entityStringAttributeTransferCache;
    }
    
    public EntityTimeAttributeTransferCache getEntityTimeAttributeTransferCache() {
        if(entityTimeAttributeTransferCache == null)
            entityTimeAttributeTransferCache = new EntityTimeAttributeTransferCache(userVisit, coreControl);
        
        return entityTimeAttributeTransferCache;
    }
    
    public EntityAttributeEntityTypeTransferCache getEntityAttributeEntityTypeTransferCache() {
        if(entityAttributeEntityTypeTransferCache == null)
            entityAttributeEntityTypeTransferCache = new EntityAttributeEntityTypeTransferCache(userVisit, coreControl);
        
        return entityAttributeEntityTypeTransferCache;
    }
    
    public EntityEntityAttributeTransferCache getEntityEntityAttributeTransferCache() {
        if(entityEntityAttributeTransferCache == null)
            entityEntityAttributeTransferCache = new EntityEntityAttributeTransferCache(userVisit, coreControl);
        
        return entityEntityAttributeTransferCache;
    }
    
    public EntityCollectionAttributeTransferCache getEntityCollectionAttributeTransferCache() {
        if(entityCollectionAttributeTransferCache == null)
            entityCollectionAttributeTransferCache = new EntityCollectionAttributeTransferCache(userVisit, coreControl);
        
        return entityCollectionAttributeTransferCache;
    }
    
    public CommandMessageTypeTransferCache getCommandMessageTypeTransferCache() {
        if(commandMessageTypeTransferCache == null)
            commandMessageTypeTransferCache = new CommandMessageTypeTransferCache(userVisit, coreControl);
        
        return commandMessageTypeTransferCache;
    }
    
    public CommandMessageTypeDescriptionTransferCache getCommandMessageTypeDescriptionTransferCache() {
        if(commandMessageTypeDescriptionTransferCache == null)
            commandMessageTypeDescriptionTransferCache = new CommandMessageTypeDescriptionTransferCache(userVisit, coreControl);
        
        return commandMessageTypeDescriptionTransferCache;
    }
    
    public BaseEncryptionKeyTransferCache getBaseEncryptionKeyTransferCache() {
        if(baseEncryptionKeyTransferCache == null)
            baseEncryptionKeyTransferCache = new BaseEncryptionKeyTransferCache(userVisit, coreControl);
        
        return baseEncryptionKeyTransferCache;
    }
    
    public PartyEntityTypeTransferCache getPartyEntityTypeTransferCache() {
        if(partyEntityTypeTransferCache == null)
            partyEntityTypeTransferCache = new PartyEntityTypeTransferCache(userVisit, coreControl);
        
        return partyEntityTypeTransferCache;
    }
    
    public ProtocolTransferCache getProtocolTransferCache() {
        if(protocolTransferCache == null)
            protocolTransferCache = new ProtocolTransferCache(userVisit, coreControl);

        return protocolTransferCache;
    }

    public ProtocolDescriptionTransferCache getProtocolDescriptionTransferCache() {
        if(protocolDescriptionTransferCache == null)
            protocolDescriptionTransferCache = new ProtocolDescriptionTransferCache(userVisit, coreControl);

        return protocolDescriptionTransferCache;
    }

    public ServiceTransferCache getServiceTransferCache() {
        if(serviceTransferCache == null)
            serviceTransferCache = new ServiceTransferCache(userVisit, coreControl);

        return serviceTransferCache;
    }

    public ServiceDescriptionTransferCache getServiceDescriptionTransferCache() {
        if(serviceDescriptionTransferCache == null)
            serviceDescriptionTransferCache = new ServiceDescriptionTransferCache(userVisit, coreControl);

        return serviceDescriptionTransferCache;
    }

    public CacheEntryTransferCache getCacheEntryTransferCache() {
        if(cacheEntryTransferCache == null)
            cacheEntryTransferCache = new CacheEntryTransferCache(userVisit, coreControl);

        return cacheEntryTransferCache;
    }

    public CacheEntryDependencyTransferCache getCacheEntryDependencyTransferCache() {
        if(cacheEntryDependencyTransferCache == null)
            cacheEntryDependencyTransferCache = new CacheEntryDependencyTransferCache(userVisit, coreControl);

        return cacheEntryDependencyTransferCache;
    }

    public ApplicationTransferCache getApplicationTransferCache() {
        if(applicationTransferCache == null)
            applicationTransferCache = new ApplicationTransferCache(userVisit, coreControl);

        return applicationTransferCache;
    }

    public ApplicationDescriptionTransferCache getApplicationDescriptionTransferCache() {
        if(applicationDescriptionTransferCache == null)
            applicationDescriptionTransferCache = new ApplicationDescriptionTransferCache(userVisit, coreControl);

        return applicationDescriptionTransferCache;
    }

    public EditorTransferCache getEditorTransferCache() {
        if(editorTransferCache == null)
            editorTransferCache = new EditorTransferCache(userVisit, coreControl);

        return editorTransferCache;
    }

    public EditorDescriptionTransferCache getEditorDescriptionTransferCache() {
        if(editorDescriptionTransferCache == null)
            editorDescriptionTransferCache = new EditorDescriptionTransferCache(userVisit, coreControl);

        return editorDescriptionTransferCache;
    }

    public ApplicationEditorTransferCache getApplicationEditorTransferCache() {
        if(applicationEditorTransferCache == null)
            applicationEditorTransferCache = new ApplicationEditorTransferCache(userVisit, coreControl);

        return applicationEditorTransferCache;
    }

    public ApplicationEditorUseTransferCache getApplicationEditorUseTransferCache() {
        if(applicationEditorUseTransferCache == null)
            applicationEditorUseTransferCache = new ApplicationEditorUseTransferCache(userVisit, coreControl);

        return applicationEditorUseTransferCache;
    }

    public ApplicationEditorUseDescriptionTransferCache getApplicationEditorUseDescriptionTransferCache() {
        if(applicationEditorUseDescriptionTransferCache == null)
            applicationEditorUseDescriptionTransferCache = new ApplicationEditorUseDescriptionTransferCache(userVisit, coreControl);

        return applicationEditorUseDescriptionTransferCache;
    }

    public PartyApplicationEditorUseTransferCache getPartyApplicationEditorUseTransferCache() {
        if(partyApplicationEditorUseTransferCache == null)
            partyApplicationEditorUseTransferCache = new PartyApplicationEditorUseTransferCache(userVisit, coreControl);

        return partyApplicationEditorUseTransferCache;
    }

    public ColorTransferCache getColorTransferCache() {
        if(colorTransferCache == null)
            colorTransferCache = new ColorTransferCache(userVisit, coreControl);

        return colorTransferCache;
    }

    public ColorDescriptionTransferCache getColorDescriptionTransferCache() {
        if(colorDescriptionTransferCache == null)
            colorDescriptionTransferCache = new ColorDescriptionTransferCache(userVisit, coreControl);

        return colorDescriptionTransferCache;
    }

    public FontStyleTransferCache getFontStyleTransferCache() {
        if(fontStyleTransferCache == null)
            fontStyleTransferCache = new FontStyleTransferCache(userVisit, coreControl);

        return fontStyleTransferCache;
    }

    public FontStyleDescriptionTransferCache getFontStyleDescriptionTransferCache() {
        if(fontStyleDescriptionTransferCache == null)
            fontStyleDescriptionTransferCache = new FontStyleDescriptionTransferCache(userVisit, coreControl);

        return fontStyleDescriptionTransferCache;
    }

    public FontWeightTransferCache getFontWeightTransferCache() {
        if(fontWeightTransferCache == null)
            fontWeightTransferCache = new FontWeightTransferCache(userVisit, coreControl);

        return fontWeightTransferCache;
    }

    public FontWeightDescriptionTransferCache getFontWeightDescriptionTransferCache() {
        if(fontWeightDescriptionTransferCache == null)
            fontWeightDescriptionTransferCache = new FontWeightDescriptionTransferCache(userVisit, coreControl);

        return fontWeightDescriptionTransferCache;
    }

    public TextDecorationTransferCache getTextDecorationTransferCache() {
        if(textDecorationTransferCache == null)
            textDecorationTransferCache = new TextDecorationTransferCache(userVisit, coreControl);

        return textDecorationTransferCache;
    }

    public TextDecorationDescriptionTransferCache getTextDecorationDescriptionTransferCache() {
        if(textDecorationDescriptionTransferCache == null)
            textDecorationDescriptionTransferCache = new TextDecorationDescriptionTransferCache(userVisit, coreControl);

        return textDecorationDescriptionTransferCache;
    }

    public TextTransformationTransferCache getTextTransformationTransferCache() {
        if(textTransformationTransferCache == null)
            textTransformationTransferCache = new TextTransformationTransferCache(userVisit, coreControl);

        return textTransformationTransferCache;
    }

    public TextTransformationDescriptionTransferCache getTextTransformationDescriptionTransferCache() {
        if(textTransformationDescriptionTransferCache == null)
            textTransformationDescriptionTransferCache = new TextTransformationDescriptionTransferCache(userVisit, coreControl);

        return textTransformationDescriptionTransferCache;
    }

    public AppearanceTransferCache getAppearanceTransferCache() {
        if(appearanceTransferCache == null)
            appearanceTransferCache = new AppearanceTransferCache(userVisit, coreControl);

        return appearanceTransferCache;
    }

    public AppearanceDescriptionTransferCache getAppearanceDescriptionTransferCache() {
        if(appearanceDescriptionTransferCache == null)
            appearanceDescriptionTransferCache = new AppearanceDescriptionTransferCache(userVisit, coreControl);

        return appearanceDescriptionTransferCache;
    }

    public AppearanceTextDecorationTransferCache getAppearanceTextDecorationTransferCache() {
        if(appearanceTextDecorationTransferCache == null)
            appearanceTextDecorationTransferCache = new AppearanceTextDecorationTransferCache(userVisit, coreControl);

        return appearanceTextDecorationTransferCache;
    }

    public AppearanceTextTransformationTransferCache getAppearanceTextTransformationTransferCache() {
        if(appearanceTextTransformationTransferCache == null)
            appearanceTextTransformationTransferCache = new AppearanceTextTransformationTransferCache(userVisit, coreControl);

        return appearanceTextTransformationTransferCache;
    }

    public EntityAppearanceTransferCache getEntityAppearanceTransferCache() {
        if(entityAppearanceTransferCache == null)
            entityAppearanceTransferCache = new EntityAppearanceTransferCache(userVisit, coreControl);
        
        return entityAppearanceTransferCache;
    }
    
}
