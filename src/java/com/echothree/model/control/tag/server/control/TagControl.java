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

package com.echothree.model.control.tag.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.tag.common.choice.TagChoicesBean;
import com.echothree.model.control.tag.common.choice.TagScopeChoicesBean;
import com.echothree.model.control.tag.common.transfer.EntityTagTransfer;
import com.echothree.model.control.tag.common.transfer.TagScopeDescriptionTransfer;
import com.echothree.model.control.tag.common.transfer.TagScopeEntityTypeTransfer;
import com.echothree.model.control.tag.common.transfer.TagScopeTransfer;
import com.echothree.model.control.tag.common.transfer.TagTransfer;
import com.echothree.model.control.tag.server.transfer.EntityTagTransferCache;
import com.echothree.model.control.tag.server.transfer.TagScopeDescriptionTransferCache;
import com.echothree.model.control.tag.server.transfer.TagScopeEntityTypeTransferCache;
import com.echothree.model.control.tag.server.transfer.TagScopeTransferCache;
import com.echothree.model.control.tag.server.transfer.TagTransferCache;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.tag.common.pk.TagPK;
import com.echothree.model.data.tag.common.pk.TagScopeEntityTypePK;
import com.echothree.model.data.tag.common.pk.TagScopePK;
import com.echothree.model.data.tag.server.entity.EntityTag;
import com.echothree.model.data.tag.server.entity.Tag;
import com.echothree.model.data.tag.server.entity.TagScope;
import com.echothree.model.data.tag.server.entity.TagScopeDescription;
import com.echothree.model.data.tag.server.entity.TagScopeEntityType;
import com.echothree.model.data.tag.server.factory.EntityTagFactory;
import com.echothree.model.data.tag.server.factory.TagDetailFactory;
import com.echothree.model.data.tag.server.factory.TagFactory;
import com.echothree.model.data.tag.server.factory.TagScopeDescriptionFactory;
import com.echothree.model.data.tag.server.factory.TagScopeDetailFactory;
import com.echothree.model.data.tag.server.factory.TagScopeEntityTypeFactory;
import com.echothree.model.data.tag.server.factory.TagScopeFactory;
import com.echothree.model.data.tag.server.value.EntityTagValue;
import com.echothree.model.data.tag.server.value.TagDetailValue;
import com.echothree.model.data.tag.server.value.TagScopeDescriptionValue;
import com.echothree.model.data.tag.server.value.TagScopeDetailValue;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.COMPONENT_VENDORS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_INSTANCES_ENTITY_TYPE_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_TYPE_DETAILS_COMPONENT_VENDOR_FK;
import static com.echothree.model.jooq.server.keys.tag.TagForeignKeys.ENTITY_TAGS_TAGGED_ENTITY_INSTANCE_FK;
import static com.echothree.model.jooq.server.keys.tag.TagForeignKeys.ENTITY_TAGS_TAG_FK;
import static com.echothree.model.jooq.server.keys.tag.TagForeignKeys.TAGS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.tag.TagForeignKeys.TAGS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.tag.TagForeignKeys.TAG_SCOPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.tag.TagForeignKeys.TAG_SCOPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.tag.TagForeignKeys.TAG_SCOPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.tag.TagForeignKeys.TAG_SCOPE_ENTITY_TYPES_ENTITY_TYPE_FK;
import static com.echothree.model.jooq.server.keys.tag.TagForeignKeys.TAG_SCOPE_ENTITY_TYPES_TAG_SCOPE_FK;
import static com.echothree.model.jooq.server.tables.core.ComponentVendorDetails.ComponentVendorDetails;
import static com.echothree.model.jooq.server.tables.core.ComponentVendors.ComponentVendors;
import static com.echothree.model.jooq.server.tables.core.EntityInstances.EntityInstances;
import static com.echothree.model.jooq.server.tables.core.EntityTypeDetails.EntityTypeDetails;
import static com.echothree.model.jooq.server.tables.core.EntityTypes.EntityTypes;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import static com.echothree.model.jooq.server.tables.tag.EntityTags.EntityTags;
import static com.echothree.model.jooq.server.tables.tag.TagDetails.TagDetails;
import static com.echothree.model.jooq.server.tables.tag.TagScopeDescriptions.TagScopeDescriptions;
import static com.echothree.model.jooq.server.tables.tag.TagScopeDetails.TagScopeDetails;
import static com.echothree.model.jooq.server.tables.tag.TagScopeEntityTypes.TagScopeEntityTypes;
import static com.echothree.model.jooq.server.tables.tag.TagScopes.TagScopes;
import static com.echothree.model.jooq.server.tables.tag.Tags.Tags;
import com.echothree.util.common.persistence.BasePK;
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
public class TagControl
        extends BaseModelControl {
    
    /** Creates a new instance of TagControl */
    protected TagControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Tag Scopes
    // --------------------------------------------------------------------------------

    @Inject
    protected TagScopeDetailFactory tagScopeDetailFactory;

    @Inject
    protected TagScopeFactory tagScopeFactory;

    @Inject
    private TagScopeTransferCache tagScopeTransferCache;

    public TagScope createTagScope(String tagScopeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultTagScope = getDefaultTagScope();
        var defaultFound = defaultTagScope != null;
        
        if(defaultFound && isDefault) {
            var defaultTagScopeDetailValue = getDefaultTagScopeDetailValueForUpdate();
            
            defaultTagScopeDetailValue.setIsDefault(false);
            updateTagScopeFromValue(defaultTagScopeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var tagScope = tagScopeFactory.create();
        var tagScopeDetail = tagScopeDetailFactory.create(tagScope,
                tagScopeName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        tagScope = tagScopeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                tagScope.getPrimaryKey());
        tagScope.setActiveDetail(tagScopeDetail);
        tagScope.setLastDetail(tagScopeDetail);
        tagScope.store();
        
        sendEvent(tagScope.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return tagScope;
    }

    public long countTagScopes() {
        return session.getDslContext()
                .selectCount()
                .from(TagScopes)
                .join(TagScopeDetails).onKey(TAG_SCOPES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countTagScopesByEntityType(EntityType entityType) {
        return session.getDslContext()
                .selectCount()
                .from(TagScopes)
                .join(TagScopeDetails).onKey(TAG_SCOPES_ACTIVE_DETAIL_FK)
                .join(TagScopeEntityTypes).onKey(TAG_SCOPE_ENTITY_TYPES_TAG_SCOPE_FK)
                .where(TagScopeEntityTypes.ENTITY_TYPE.eq(entityType.getPrimaryKey()),
                        TagScopeEntityTypes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.TagScope */
    public TagScope getTagScopeByEntityInstance(final EntityInstance entityInstance,
            final EntityPermission entityPermission) {
        var pk = new TagScopePK(entityInstance.getEntityUniqueId());

        return tagScopeFactory.getEntityFromPK(entityPermission, pk);
    }

    public TagScope getTagScopeByEntityInstance(final EntityInstance entityInstance) {
        return getTagScopeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public TagScope getTagScopeByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getTagScopeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    private List<TagScope> getTagScopes(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(TagScopes.fields())
                .from(TagScopes)
                .join(TagScopeDetails).onKey(TAG_SCOPES_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(TagScopeDetails.SORT_ORDER, TagScopeDetails.TAG_SCOPE_NAME),
                    TagScopeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return tagScopeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<TagScope> getTagScopes() {
        return getTagScopes(EntityPermission.READ_ONLY);
    }
    
    public List<TagScope> getTagScopesForUpdate() {
        return getTagScopes(EntityPermission.READ_WRITE);
    }
    
    private List<TagScope> getTagScopesByEntityType(EntityType entityType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(TagScopes.fields())
                .from(TagScopes)
                .join(TagScopeDetails).onKey(TAG_SCOPES_ACTIVE_DETAIL_FK)
                .join(TagScopeEntityTypes).onKey(TAG_SCOPE_ENTITY_TYPES_TAG_SCOPE_FK)
                .where(TagScopeEntityTypes.ENTITY_TYPE.eq(entityType.getPrimaryKey()),
                        TagScopeEntityTypes.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(TagScopeDetails.SORT_ORDER, TagScopeDetails.TAG_SCOPE_NAME),
                    TagScopeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return tagScopeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<TagScope> getTagScopesByEntityType(EntityType entityType) {
        return getTagScopesByEntityType(entityType, EntityPermission.READ_ONLY);
    }
    
    public List<TagScope> getTagScopesByEntityTypeForUpdate(EntityType entityType) {
        return getTagScopesByEntityType(entityType, EntityPermission.READ_WRITE);
    }
    
    public TagScope getDefaultTagScope(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(TagScopes.fields())
                .from(TagScopes)
                .join(TagScopeDetails).onKey(TAG_SCOPES_ACTIVE_DETAIL_FK)
                .where(TagScopeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return tagScopeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public TagScope getDefaultTagScope() {
        return getDefaultTagScope(EntityPermission.READ_ONLY);
    }
    
    public TagScope getDefaultTagScopeForUpdate() {
        return getDefaultTagScope(EntityPermission.READ_WRITE);
    }
    
    public TagScopeDetailValue getDefaultTagScopeDetailValueForUpdate() {
        return getDefaultTagScopeForUpdate().getLastDetailForUpdate().getTagScopeDetailValue().clone();
    }
    
    public TagScope getTagScopeByName(String tagScopeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(TagScopes.fields())
                .from(TagScopes)
                .join(TagScopeDetails).onKey(TAG_SCOPES_ACTIVE_DETAIL_FK)
                .where(TagScopeDetails.TAG_SCOPE_NAME.eq(tagScopeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return tagScopeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public TagScope getTagScopeByName(String tagScopeName) {
        return getTagScopeByName(tagScopeName, EntityPermission.READ_ONLY);
    }
    
    public TagScope getTagScopeByNameForUpdate(String tagScopeName) {
        return getTagScopeByName(tagScopeName, EntityPermission.READ_WRITE);
    }
    
    public TagScopeDetailValue getTagScopeDetailValueForUpdate(TagScope tagScope) {
        return tagScope == null? null: tagScope.getLastDetailForUpdate().getTagScopeDetailValue().clone();
    }
    
    public TagScopeDetailValue getTagScopeDetailValueByNameForUpdate(String tagScopeName) {
        return getTagScopeDetailValueForUpdate(getTagScopeByNameForUpdate(tagScopeName));
    }
    
    public TagScopeChoicesBean getTagScopeChoices(String defaultTagScopeChoice, Language language, boolean allowNullChoice) {
        var tagScopes = getTagScopes();
        var size = tagScopes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultTagScopeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var tagScope : tagScopes) {
            var tagScopeDetail = tagScope.getLastDetail();
            var label = getBestTagScopeDescription(tagScope, language);
            var value = tagScopeDetail.getTagScopeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultTagScopeChoice != null && defaultTagScopeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && tagScopeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new TagScopeChoicesBean(labels, values, defaultValue);
    }
    
    public TagScopeTransfer getTagScopeTransfer(UserVisit userVisit, TagScope tagScope) {
        return tagScopeTransferCache.getTagScopeTransfer(userVisit, tagScope);
    }
    
    public List<TagScopeTransfer> getTagScopeTransfers(UserVisit userVisit, Collection<TagScope> tagScopes) {
        List<TagScopeTransfer> tagScopeTransfers = new ArrayList<>(tagScopes.size());
        
        tagScopes.forEach((tagScope) ->
                tagScopeTransfers.add(tagScopeTransferCache.getTagScopeTransfer(userVisit, tagScope))
        );
        
        return tagScopeTransfers;
    }
    
    public List<TagScopeTransfer> getTagScopeTransfers(UserVisit userVisit) {
        return getTagScopeTransfers(userVisit, getTagScopes());
    }
    
    public List<TagScopeTransfer> getTagScopeTransfersByEntityType(UserVisit userVisit, EntityType entityType) {
        return getTagScopeTransfers(userVisit, getTagScopesByEntityType(entityType));
    }
    
    private void updateTagScopeFromValue(TagScopeDetailValue tagScopeDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(tagScopeDetailValue.hasBeenModified()) {
            var tagScope = tagScopeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     tagScopeDetailValue.getTagScopePK());
            var tagScopeDetail = tagScope.getActiveDetailForUpdate();
            
            tagScopeDetail.setThruTime(session.getStartTime());
            tagScopeDetail.store();

            var tagScopePK = tagScopeDetail.getTagScopePK();
            var tagScopeName = tagScopeDetailValue.getTagScopeName();
            var isDefault = tagScopeDetailValue.getIsDefault();
            var sortOrder = tagScopeDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultTagScope = getDefaultTagScope();
                var defaultFound = defaultTagScope != null && !defaultTagScope.equals(tagScope);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultTagScopeDetailValue = getDefaultTagScopeDetailValueForUpdate();
                    
                    defaultTagScopeDetailValue.setIsDefault(false);
                    updateTagScopeFromValue(defaultTagScopeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            tagScopeDetail = tagScopeDetailFactory.create(tagScopePK, tagScopeName,
                    isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            tagScope.setActiveDetail(tagScopeDetail);
            tagScope.setLastDetail(tagScopeDetail);
            
            sendEvent(tagScopePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateTagScopeFromValue(TagScopeDetailValue tagScopeDetailValue, BasePK updatedBy) {
        updateTagScopeFromValue(tagScopeDetailValue, true, updatedBy);
    }
    
    public void deleteTagScope(TagScope tagScope, BasePK deletedBy) {
        deleteTagScopeDescriptionsByTagScope(tagScope, deletedBy);
        deleteTagScopeEntityTypesByTagScope(tagScope, deletedBy);
        deleteTagsByTagScope(tagScope, deletedBy);

        var tagScopeDetail = tagScope.getLastDetailForUpdate();
        tagScopeDetail.setThruTime(session.getStartTime());
        tagScope.setActiveDetail(null);
        tagScope.store();
        
        // Check for default, and pick one if necessary
        var defaultTagScope = getDefaultTagScope();
        if(defaultTagScope == null) {
            var tagScopes = getTagScopesForUpdate();
            
            if(!tagScopes.isEmpty()) {
                var iter = tagScopes.iterator();
                if(iter.hasNext()) {
                    defaultTagScope = iter.next();
                }
                var tagScopeDetailValue = Objects.requireNonNull(defaultTagScope).getLastDetailForUpdate().getTagScopeDetailValue().clone();
                
                tagScopeDetailValue.setIsDefault(true);
                updateTagScopeFromValue(tagScopeDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(tagScope.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Tag Scope Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected TagScopeDescriptionFactory tagScopeDescriptionFactory;

    @Inject
    private TagScopeDescriptionTransferCache tagScopeDescriptionTransferCache;

    public TagScopeDescription createTagScopeDescription(TagScope tagScope, Language language, String description,
            BasePK createdBy) {
        var tagScopeDescription = tagScopeDescriptionFactory.create(tagScope,
                language, description,
                session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(tagScope.getPrimaryKey(), EventTypes.MODIFY, tagScopeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return tagScopeDescription;
    }
    
    private TagScopeDescription getTagScopeDescription(TagScope tagScope, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(TagScopeDescriptions.fields())
                .from(TagScopeDescriptions)
                .where(TagScopeDescriptions.TAG_SCOPE.eq(tagScope.getPrimaryKey()),
                        TagScopeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        TagScopeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return tagScopeDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public TagScopeDescription getTagScopeDescription(TagScope tagScope, Language language) {
        return getTagScopeDescription(tagScope, language, EntityPermission.READ_ONLY);
    }
    
    public TagScopeDescription getTagScopeDescriptionForUpdate(TagScope tagScope, Language language) {
        return getTagScopeDescription(tagScope, language, EntityPermission.READ_WRITE);
    }
    
    public TagScopeDescriptionValue getTagScopeDescriptionValue(TagScopeDescription tagScopeDescription) {
        return tagScopeDescription == null? null: tagScopeDescription.getTagScopeDescriptionValue().clone();
    }
    
    public TagScopeDescriptionValue getTagScopeDescriptionValueForUpdate(TagScope tagScope, Language language) {
        return getTagScopeDescriptionValue(getTagScopeDescriptionForUpdate(tagScope, language));
    }
    
    private List<TagScopeDescription> getTagScopeDescriptionsByTagScope(TagScope tagScope, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(TagScopeDescriptions.fields())
                    .from(TagScopeDescriptions)
                    .join(Languages).onKey(TAG_SCOPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(TagScopeDescriptions.TAG_SCOPE.eq(tagScope.getPrimaryKey()),
                            TagScopeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    TagScopeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(TagScopeDescriptions.fields())
                    .from(TagScopeDescriptions)
                    .where(TagScopeDescriptions.TAG_SCOPE.eq(tagScope.getPrimaryKey()),
                            TagScopeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return tagScopeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<TagScopeDescription> getTagScopeDescriptionsByTagScope(TagScope tagScope) {
        return getTagScopeDescriptionsByTagScope(tagScope, EntityPermission.READ_ONLY);
    }
    
    public List<TagScopeDescription> getTagScopeDescriptionsByTagScopeForUpdate(TagScope tagScope) {
        return getTagScopeDescriptionsByTagScope(tagScope, EntityPermission.READ_WRITE);
    }
    
    public String getBestTagScopeDescription(TagScope tagScope, Language language) {
        String description;
        var tagScopeDescription = getTagScopeDescription(tagScope, language);
        
        if(tagScopeDescription == null && !language.getIsDefault()) {
            tagScopeDescription = getTagScopeDescription(tagScope, partyControl.getDefaultLanguage());
        }
        
        if(tagScopeDescription == null) {
            description = tagScope.getLastDetail().getTagScopeName();
        } else {
            description = tagScopeDescription.getDescription();
        }
        
        return description;
    }
    
    public TagScopeDescriptionTransfer getTagScopeDescriptionTransfer(UserVisit userVisit, TagScopeDescription tagScopeDescription) {
        return tagScopeDescriptionTransferCache.getTagScopeDescriptionTransfer(userVisit, tagScopeDescription);
    }
    
    public List<TagScopeDescriptionTransfer> getTagScopeDescriptionTransfers(UserVisit userVisit, TagScope tagScope) {
        var tagScopeDescriptions = getTagScopeDescriptionsByTagScope(tagScope);
        List<TagScopeDescriptionTransfer> tagScopeDescriptionTransfers = new ArrayList<>(tagScopeDescriptions.size());
        
        tagScopeDescriptions.forEach((tagScopeDescription) ->
                tagScopeDescriptionTransfers.add(tagScopeDescriptionTransferCache.getTagScopeDescriptionTransfer(userVisit, tagScopeDescription))
        );
        
        return tagScopeDescriptionTransfers;
    }
    
    public void updateTagScopeDescriptionFromValue(TagScopeDescriptionValue tagScopeDescriptionValue, BasePK updatedBy) {
        if(tagScopeDescriptionValue.hasBeenModified()) {
            var tagScopeDescription = tagScopeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     tagScopeDescriptionValue.getPrimaryKey());
            
            tagScopeDescription.setThruTime(session.getStartTime());
            tagScopeDescription.store();

            var tagScope = tagScopeDescription.getTagScope();
            var language = tagScopeDescription.getLanguage();
            var description = tagScopeDescriptionValue.getDescription();
            
            tagScopeDescription = tagScopeDescriptionFactory.create(tagScope, language,
                    description, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(tagScope.getPrimaryKey(), EventTypes.MODIFY, tagScopeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteTagScopeDescription(TagScopeDescription tagScopeDescription, BasePK deletedBy) {
        tagScopeDescription.setThruTime(session.getStartTime());
        
        sendEvent(tagScopeDescription.getTagScopePK(), EventTypes.MODIFY, tagScopeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteTagScopeDescriptionsByTagScope(TagScope tagScope, BasePK deletedBy) {
        var tagScopeDescriptions = getTagScopeDescriptionsByTagScopeForUpdate(tagScope);
        
        tagScopeDescriptions.forEach((tagScopeDescription) -> 
                deleteTagScopeDescription(tagScopeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Tag Scope Entity Types
    // --------------------------------------------------------------------------------

    @Inject
    protected TagScopeEntityTypeFactory tagScopeEntityTypeFactory;

    @Inject
    private TagScopeEntityTypeTransferCache tagScopeEntityTypeTransferCache;

    public TagScopeEntityType createTagScopeEntityType(TagScope tagScope, EntityType entityType, BasePK createdBy) {
        var tagScopeEntityType = tagScopeEntityTypeFactory.create(tagScope, entityType,
                session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(tagScope.getPrimaryKey(), EventTypes.MODIFY, tagScopeEntityType.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return tagScopeEntityType;
    }

    public long countTagScopeEntityTypesByTagScope(TagScope tagScope) {
        return session.getDslContext()
                .selectCount()
                .from(TagScopeEntityTypes)
                .where(TagScopeEntityTypes.TAG_SCOPE.eq(tagScope.getPrimaryKey()),
                        TagScopeEntityTypes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countTagScopeEntityTypesByEntityType(EntityType entityType) {
        return session.getDslContext()
                .selectCount()
                .from(TagScopeEntityTypes)
                .where(TagScopeEntityTypes.ENTITY_TYPE.eq(entityType.getPrimaryKey()),
                        TagScopeEntityTypes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.TagScopeEntityType */
    public TagScopeEntityType getTagScopeEntityTypeByEntityInstance(final EntityInstance entityInstance,
            final EntityPermission entityPermission) {
        var pk = new TagScopeEntityTypePK(entityInstance.getEntityUniqueId());

        return tagScopeEntityTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public TagScopeEntityType getTagScopeEntityTypeByEntityInstance(final EntityInstance entityInstance) {
        return getTagScopeEntityTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public TagScopeEntityType getTagScopeEntityTypeByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getTagScopeEntityTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    private TagScopeEntityType getTagScopeEntityType(TagScope tagScope, EntityType entityType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(TagScopeEntityTypes.fields())
                .from(TagScopeEntityTypes)
                .where(TagScopeEntityTypes.TAG_SCOPE.eq(tagScope.getPrimaryKey()),
                        TagScopeEntityTypes.ENTITY_TYPE.eq(entityType.getPrimaryKey()),
                        TagScopeEntityTypes.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return tagScopeEntityTypeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public TagScopeEntityType getTagScopeEntityType(TagScope tagScope, EntityType entityType) {
        return getTagScopeEntityType(tagScope, entityType, EntityPermission.READ_ONLY);
    }
    
    public TagScopeEntityType getTagScopeEntityTypeForUpdate(TagScope tagScope, EntityType entityType) {
        return getTagScopeEntityType(tagScope, entityType, EntityPermission.READ_WRITE);
    }
    
    private List<TagScopeEntityType> getTagScopeEntityTypesByTagScope(TagScope tagScope, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(TagScopeEntityTypes.fields())
                    .from(TagScopeEntityTypes)
                    .join(EntityTypes).onKey(TAG_SCOPE_ENTITY_TYPES_ENTITY_TYPE_FK)
                    .join(EntityTypeDetails).onKey(ENTITY_TYPES_LAST_DETAIL_FK)
                    .where(TagScopeEntityTypes.TAG_SCOPE.eq(tagScope.getPrimaryKey()),
                            TagScopeEntityTypes.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(EntityTypeDetails.SORT_ORDER, EntityTypeDetails.ENTITY_TYPE_NAME),
                    TagScopeEntityTypeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(TagScopeEntityTypes.fields())
                    .from(TagScopeEntityTypes)
                    .where(TagScopeEntityTypes.TAG_SCOPE.eq(tagScope.getPrimaryKey()),
                            TagScopeEntityTypes.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return tagScopeEntityTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<TagScopeEntityType> getTagScopeEntityTypesByTagScope(TagScope tagScope) {
        return getTagScopeEntityTypesByTagScope(tagScope, EntityPermission.READ_ONLY);
    }
    
    public List<TagScopeEntityType> getTagScopeEntityTypesByTagScopeForUpdate(TagScope tagScope) {
        return getTagScopeEntityTypesByTagScope(tagScope, EntityPermission.READ_WRITE);
    }
    
    private List<TagScopeEntityType> getTagScopeEntityTypesByEntityType(EntityType entityType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(TagScopeEntityTypes.fields())
                    .from(TagScopeEntityTypes)
                    .join(TagScopes).onKey(TAG_SCOPE_ENTITY_TYPES_TAG_SCOPE_FK)
                    .join(TagScopeDetails).onKey(TAG_SCOPES_LAST_DETAIL_FK)
                    .where(TagScopeEntityTypes.ENTITY_TYPE.eq(entityType.getPrimaryKey()),
                            TagScopeEntityTypes.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(TagScopeDetails.SORT_ORDER, TagScopeDetails.TAG_SCOPE_NAME),
                    TagScopeEntityTypeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(TagScopeEntityTypes.fields())
                    .from(TagScopeEntityTypes)
                    .where(TagScopeEntityTypes.ENTITY_TYPE.eq(entityType.getPrimaryKey()),
                            TagScopeEntityTypes.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return tagScopeEntityTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<TagScopeEntityType> getTagScopeEntityTypesByEntityType(EntityType entityType) {
        return getTagScopeEntityTypesByEntityType(entityType, EntityPermission.READ_ONLY);
    }
    
    public List<TagScopeEntityType> getTagScopeEntityTypesByEntityTypeForUpdate(EntityType entityType) {
        return getTagScopeEntityTypesByEntityType(entityType, EntityPermission.READ_WRITE);
    }
    
    public TagScopeEntityTypeTransfer getTagScopeEntityTypeTransfer(UserVisit userVisit, TagScopeEntityType tagScopeEntityType) {
        return tagScopeEntityTypeTransferCache.getTagScopeEntityTypeTransfer(userVisit, tagScopeEntityType);
    }
    
    public List<TagScopeEntityTypeTransfer> getTagScopeEntityTypeTransfers(UserVisit userVisit, Collection<TagScopeEntityType> tagScopeEntityTypes) {
        List<TagScopeEntityTypeTransfer> tagScopeEntityTypeTransfers = new ArrayList<>(tagScopeEntityTypes.size());
        
        tagScopeEntityTypes.forEach((tagScopeEntityType) ->
                tagScopeEntityTypeTransfers.add(tagScopeEntityTypeTransferCache.getTagScopeEntityTypeTransfer(userVisit, tagScopeEntityType))
        );
        
        return tagScopeEntityTypeTransfers;
    }
    
    public List<TagScopeEntityTypeTransfer> getTagScopeEntityTypeTransfersByTagScope(UserVisit userVisit, TagScope tagScope) {
        return getTagScopeEntityTypeTransfers(userVisit, getTagScopeEntityTypesByTagScope(tagScope));
    }
    
    public List<TagScopeEntityTypeTransfer> getTagScopeEntityTypeTransfersByEntityType(UserVisit userVisit, EntityType entityType) {
        return getTagScopeEntityTypeTransfers(userVisit, getTagScopeEntityTypesByEntityType(entityType));
    }
    
    public void deleteTagScopeEntityType(TagScopeEntityType tagScopeEntityType, BasePK deletedBy) {
        tagScopeEntityType.setThruTime(session.getStartTime());
        
        sendEvent(tagScopeEntityType.getTagScopePK(), EventTypes.MODIFY, tagScopeEntityType.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteTagScopeEntityTypes(List<TagScopeEntityType> tagScopeEntityTypes, BasePK deletedBy) {
        tagScopeEntityTypes.forEach((tagScopeEntityType) -> 
                deleteTagScopeEntityType(tagScopeEntityType, deletedBy)
        );
    }
    
    public void deleteTagScopeEntityTypesByTagScope(TagScope tagScope, BasePK deletedBy) {
        deleteTagScopeEntityTypes(getTagScopeEntityTypesByTagScopeForUpdate(tagScope), deletedBy);
    }
    
    public void deleteTagScopeEntityTypesByEntityType(EntityType entityType, BasePK deletedBy) {
        deleteTagScopeEntityTypes(getTagScopeEntityTypesByEntityTypeForUpdate(entityType), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Tags
    // --------------------------------------------------------------------------------

    @Inject
    protected TagDetailFactory tagDetailFactory;

    @Inject
    protected TagFactory tagFactory;

    @Inject
    private TagTransferCache tagTransferCache;

    public Tag createTag(TagScope tagScope, String tagName, BasePK createdBy) {
        var tag = tagFactory.create();
        var tagDetail = tagDetailFactory.create(tag, tagScope, tagName, session.getStartTime(),
                Session.MAX_TIME);
        
        // Convert to R/W
        tag = tagFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                tag.getPrimaryKey());
        tag.setActiveDetail(tagDetail);
        tag.setLastDetail(tagDetail);
        tag.store();
        
        sendEvent(tag.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return tag;
    }

    public long countTagsByTagScope(final TagScope tagScope) {
        return session.getDslContext()
                .selectCount()
                .from(Tags)
                .join(TagDetails).onKey(TAGS_ACTIVE_DETAIL_FK)
                .where(TagDetails.TAG_SCOPE.eq(tagScope.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countTagsByTagScopeAndEntityInstance(final TagScope tagScope, final EntityInstance taggedEntityInstance) {
        return session.getDslContext()
                .selectCount()
                .from(Tags)
                .join(TagDetails).onKey(TAGS_ACTIVE_DETAIL_FK)
                .join(EntityTags).onKey(ENTITY_TAGS_TAG_FK)
                .where(TagDetails.TAG_SCOPE.eq(tagScope.getPrimaryKey()),
                        EntityTags.TAGGED_ENTITY_INSTANCE.eq(taggedEntityInstance.getPrimaryKey()),
                        EntityTags.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.Tag */
    public Tag getTagByEntityInstance(final EntityInstance entityInstance,
            final EntityPermission entityPermission) {
        var pk = new TagPK(entityInstance.getEntityUniqueId());

        return tagFactory.getEntityFromPK(entityPermission, pk);
    }

    public Tag getTagByEntityInstance(final EntityInstance entityInstance) {
        return getTagByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public Tag getTagByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getTagByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    private List<Tag> getTags(TagScope tagScope, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Tags.fields())
                .from(Tags)
                .join(TagDetails).onKey(TAGS_ACTIVE_DETAIL_FK)
                .where(TagDetails.TAG_SCOPE.eq(tagScope.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(TagDetails.TAG_NAME),
                    TagFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return tagFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<Tag> getTags(TagScope tagScope) {
        return getTags(tagScope, EntityPermission.READ_ONLY);
    }
    
    public List<Tag> getTagsForUpdate(TagScope tagScope) {
        return getTags(tagScope, EntityPermission.READ_WRITE);
    }
    
    private List<Tag> getTagsByTagScopeAndEntityInstance(TagScope tagScope, EntityInstance entityInstance,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Tags.fields())
                .from(Tags)
                .join(TagDetails).onKey(TAGS_ACTIVE_DETAIL_FK)
                .join(EntityTags).onKey(ENTITY_TAGS_TAG_FK)
                .where(TagDetails.TAG_SCOPE.eq(tagScope.getPrimaryKey()),
                        EntityTags.TAGGED_ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                        EntityTags.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(TagDetails.TAG_NAME),
                    TagFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return tagFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<Tag> getTagsByTagScopeAndEntityInstance(TagScope tagScope, EntityInstance entityInstance) {
        return getTagsByTagScopeAndEntityInstance(tagScope, entityInstance, EntityPermission.READ_ONLY);
    }
    
    public List<Tag> getTagsByTagScopeAndEntityInstanceForUpdate(TagScope tagScope, EntityInstance entityInstance) {
        return getTagsByTagScopeAndEntityInstance(tagScope, entityInstance, EntityPermission.READ_WRITE);
    }
    
    public Tag getTagByName(TagScope tagScope, String tagName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Tags.fields())
                .from(Tags)
                .join(TagDetails).onKey(TAGS_ACTIVE_DETAIL_FK)
                .where(TagDetails.TAG_SCOPE.eq(tagScope.getPrimaryKey()),
                        TagDetails.TAG_NAME.eq(tagName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return tagFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public Tag getTagByName(TagScope tagScope, String tagName) {
        return getTagByName(tagScope, tagName, EntityPermission.READ_ONLY);
    }
    
    public Tag getTagByNameForUpdate(TagScope tagScope, String tagName) {
        return getTagByName(tagScope, tagName, EntityPermission.READ_WRITE);
    }
    
    public TagDetailValue getTagDetailValueForUpdate(Tag tag) {
        return tag == null? null: tag.getLastDetailForUpdate().getTagDetailValue().clone();
    }
    
    public TagDetailValue getTagDetailValueByNameForUpdate(TagScope tagScope, String tagName) {
        return getTagDetailValueForUpdate(getTagByNameForUpdate(tagScope, tagName));
    }
    
    public TagChoicesBean getTagChoices(String defaultTagChoice, Language language, boolean allowNullChoice, TagScope tagScope) {
        var tags = getTags(tagScope);
        var size = tags.size();
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            values.add("");
            
            if(defaultTagChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var tag : tags) {
            var tagDetail = tag.getLastDetail();
            var value = tagDetail.getTagName();
            
            values.add(value);
            
            var usingDefaultChoice = defaultTagChoice != null && defaultTagChoice.equals(value);
            if(usingDefaultChoice || defaultValue == null) {
                defaultValue = value;
            }
        }
        
        return new TagChoicesBean(values, values, defaultValue);
    }
    
    public TagTransfer getTagTransfer(UserVisit userVisit, Tag tag) {
        return tagTransferCache.getTagTransfer(userVisit, tag);
    }
    
    public List<TagTransfer> getTagTransfers(UserVisit userVisit, Collection<Tag> tags) {
        List<TagTransfer> tagTransfers = new ArrayList<>(tags.size());
        
        tags.forEach((tag) ->
                tagTransfers.add(tagTransferCache.getTagTransfer(userVisit, tag))
        );
        
        return tagTransfers;
    }
    
    public List<TagTransfer> getTagTransfersByTagScope(UserVisit userVisit, TagScope tagScope) {
        return getTagTransfers(userVisit, getTags(tagScope));
    }
    
    public List<TagTransfer> getTagTransfersByTagScopeAndEntityInstance(UserVisit userVisit, TagScope tagScope,
            EntityInstance entityInstance) {
        return getTagTransfers(userVisit, getTagsByTagScopeAndEntityInstance(tagScope, entityInstance));
    }
    
    public void updateTagFromValue(TagDetailValue tagDetailValue, BasePK updatedBy) {
        if(tagDetailValue.hasBeenModified()) {
            var tag = tagFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     tagDetailValue.getTagPK());
            var tagDetail = tag.getActiveDetailForUpdate();
            
            tagDetail.setThruTime(session.getStartTime());
            tagDetail.store();

            var tagPK = tagDetail.getTagPK(); // Not updated
            var tagScopePK = tagDetail.getTagScopePK(); // Not updated
            var tagName = tagDetailValue.getTagName();
            
            tagDetail = tagDetailFactory.create(tagPK, tagScopePK, tagName, session.getStartTime(),
                    Session.MAX_TIME);
            
            tag.setActiveDetail(tagDetail);
            tag.setLastDetail(tagDetail);
            
            sendEvent(tagPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deleteTag(Tag tag, BasePK deletedBy) {
        deleteEntityTagsByTag(tag, deletedBy);

        var tagDetail = tag.getLastDetailForUpdate();
        tagDetail.setThruTime(session.getStartTime());
        tag.setActiveDetail(null);
        tag.store();
        
        sendEvent(tag.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteTags(List<Tag> tags, BasePK deletedBy) {
        tags.forEach((tag) -> 
                deleteTag(tag, deletedBy)
        );
    }
    
    public void deleteTagsByTagScope(TagScope tagScope, BasePK deletedBy) {
        deleteTags(getTagsForUpdate(tagScope), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Tags
    // --------------------------------------------------------------------------------

    @Inject
    protected EntityTagFactory entityTagFactory;

    @Inject
    private EntityTagTransferCache entityTagTransferCache;

    public EntityTag createEntityTag(EntityInstance taggedEntityInstance, Tag tag, BasePK createdBy) {
        var entityTag = entityTagFactory.create(taggedEntityInstance, tag, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(taggedEntityInstance, EventTypes.MODIFY, entityTag.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return entityTag;
    }

    public long countEntityTagsByTag(final Tag tag) {
        return session.getDslContext()
                .selectCount()
                .from(EntityTags)
                .where(EntityTags.TAG.eq(tag.getPrimaryKey()),
                        EntityTags.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countEntityTagsByTaggedEntityInstance(final EntityInstance taggedEntityInstance) {
        return session.getDslContext()
                .selectCount()
                .from(EntityTags)
                .where(EntityTags.TAGGED_ENTITY_INSTANCE.eq(taggedEntityInstance.getPrimaryKey()),
                        EntityTags.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private EntityTag getEntityTag(EntityInstance taggedEntityInstance, Tag tag, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EntityTags.fields())
                .from(EntityTags)
                .where(EntityTags.TAGGED_ENTITY_INSTANCE.eq(taggedEntityInstance.getPrimaryKey()),
                        EntityTags.TAG.eq(tag.getPrimaryKey()),
                        EntityTags.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return entityTagFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public EntityTag getEntityTag(EntityInstance taggedEntityInstance, Tag tag) {
        return getEntityTag(taggedEntityInstance, tag, EntityPermission.READ_ONLY);
    }
    
    public EntityTag getEntityTagForUpdate(EntityInstance taggedEntityInstance, Tag tag) {
        return getEntityTag(taggedEntityInstance, tag, EntityPermission.READ_WRITE);
    }
    
    public EntityTagValue getEntityTagValue(EntityTag entityTag) {
        return entityTag == null? null: entityTag.getEntityTagValue().clone();
    }
    
    public EntityTagValue getEntityTagValueForUpdate(EntityInstance taggedEntityInstance, Tag tag) {
        return getEntityTagValue(getEntityTagForUpdate(taggedEntityInstance, tag));
    }
    
    private List<EntityTag> getEntityTagsByTaggedEntityInstance(EntityInstance taggedEntityInstance, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(EntityTags.fields())
                    .from(EntityTags)
                    .join(Tags).onKey(ENTITY_TAGS_TAG_FK)
                    .join(TagDetails).onKey(TAGS_LAST_DETAIL_FK)
                    .where(EntityTags.TAGGED_ENTITY_INSTANCE.eq(taggedEntityInstance.getPrimaryKey()),
                            EntityTags.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(TagDetails.TAG_NAME),
                    EntityTagFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(EntityTags.fields())
                    .from(EntityTags)
                    .where(EntityTags.TAGGED_ENTITY_INSTANCE.eq(taggedEntityInstance.getPrimaryKey()),
                            EntityTags.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return entityTagFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<EntityTag> getEntityTagsByTaggedEntityInstance(EntityInstance taggedEntityInstance) {
        return getEntityTagsByTaggedEntityInstance(taggedEntityInstance, EntityPermission.READ_ONLY);
    }
    
    public List<EntityTag> getEntityTagsByTaggedEntityInstanceForUpdate(EntityInstance taggedEntityInstance) {
        return getEntityTagsByTaggedEntityInstance(taggedEntityInstance, EntityPermission.READ_WRITE);
    }
    
    private List<EntityTag> getEntityTagsByTag(Tag tag, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(EntityTags.fields())
                    .from(EntityTags)
                    .join(EntityInstances).onKey(ENTITY_TAGS_TAGGED_ENTITY_INSTANCE_FK)
                    .join(EntityTypes).onKey(ENTITY_INSTANCES_ENTITY_TYPE_FK)
                    .join(EntityTypeDetails).onKey(ENTITY_TYPES_LAST_DETAIL_FK)
                    .join(ComponentVendors).onKey(ENTITY_TYPE_DETAILS_COMPONENT_VENDOR_FK)
                    .join(ComponentVendorDetails).onKey(COMPONENT_VENDORS_LAST_DETAIL_FK)
                    .where(EntityTags.TAG.eq(tag.getPrimaryKey()),
                            EntityTags.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ComponentVendorDetails.COMPONENT_VENDOR_NAME, EntityTypeDetails.SORT_ORDER, EntityTypeDetails.ENTITY_TYPE_NAME, EntityInstances.ENTITY_UNIQUE),
                    EntityTagFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(EntityTags.fields())
                    .from(EntityTags)
                    .where(EntityTags.TAG.eq(tag.getPrimaryKey()),
                            EntityTags.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return entityTagFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<EntityTag> getEntityTagsByTag(Tag tag) {
        return getEntityTagsByTag(tag, EntityPermission.READ_ONLY);
    }
    
    public List<EntityTag> getEntityTagsByTagForUpdate(Tag tag) {
        return getEntityTagsByTag(tag, EntityPermission.READ_WRITE);
    }
    
    public EntityTagTransfer getEntityTagTransfer(UserVisit userVisit, EntityTag entityTag) {
        return entityTagTransferCache.getEntityTagTransfer(userVisit, entityTag);
    }
    
    public List<EntityTagTransfer> getEntityTagTransfers(UserVisit userVisit, Collection<EntityTag> entityTags) {
        List<EntityTagTransfer> entityTagTransfers = new ArrayList<>(entityTags.size());
        
        entityTags.forEach((entityTag) ->
                entityTagTransfers.add(entityTagTransferCache.getEntityTagTransfer(userVisit, entityTag))
        );
        
        return entityTagTransfers;
    }
    
    public List<EntityTagTransfer> getEntityTagTransfersByTaggedEntityInstance(UserVisit userVisit, EntityInstance taggedEntityInstance) {
        return getEntityTagTransfers(userVisit, getEntityTagsByTaggedEntityInstance(taggedEntityInstance));
    }
    
    public List<EntityTagTransfer> getEntityTagTransfersByTag(UserVisit userVisit, Tag tag) {
        return getEntityTagTransfers(userVisit, getEntityTagsByTag(tag));
    }
    
    public void deleteEntityTag(EntityTag entityTag, BasePK deletedBy) {
        var taggedEntityInstance = entityTag.getTaggedEntityInstance();

        entityTag.setThruTime(session.getStartTime());
        entityTag.store();
        
        sendEvent(taggedEntityInstance, EventTypes.MODIFY, entityTag.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteEntityTags(List<EntityTag> entityTags, BasePK deletedBy) {
        entityTags.forEach((entityTag) -> 
                deleteEntityTag(entityTag, deletedBy)
        );
    }
    
    public void deleteEntityTagsByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteEntityTags(getEntityTagsByTaggedEntityInstanceForUpdate(entityInstance), deletedBy);
    }
    
    public void deleteEntityTagsByTag(Tag tag, BasePK deletedBy) {
        deleteEntityTags(getEntityTagsByTagForUpdate(tag), deletedBy);
    }
    
}
