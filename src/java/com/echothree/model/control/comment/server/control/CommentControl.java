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

package com.echothree.model.control.comment.server.control;

import com.echothree.model.control.comment.common.choice.CommentStatusChoicesBean;
import com.echothree.model.control.comment.common.transfer.CommentTransfer;
import com.echothree.model.control.comment.common.transfer.CommentTypeDescriptionTransfer;
import com.echothree.model.control.comment.common.transfer.CommentTypeTransfer;
import com.echothree.model.control.comment.common.transfer.CommentUsageTransfer;
import com.echothree.model.control.comment.common.transfer.CommentUsageTypeDescriptionTransfer;
import com.echothree.model.control.comment.common.transfer.CommentUsageTypeTransfer;
import com.echothree.model.control.comment.server.transfer.CommentTransferCache;
import com.echothree.model.control.comment.server.transfer.CommentTypeDescriptionTransferCache;
import com.echothree.model.control.comment.server.transfer.CommentTypeTransferCache;
import com.echothree.model.control.comment.server.transfer.CommentUsageTransferCache;
import com.echothree.model.control.comment.server.transfer.CommentUsageTypeDescriptionTransferCache;
import com.echothree.model.control.comment.server.transfer.CommentUsageTypeTransferCache;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.data.comment.common.pk.CommentTypePK;
import com.echothree.model.data.comment.common.pk.CommentUsageTypePK;
import com.echothree.model.data.comment.server.entity.Comment;
import com.echothree.model.data.comment.server.entity.CommentBlob;
import com.echothree.model.data.comment.server.entity.CommentClob;
import com.echothree.model.data.comment.server.entity.CommentDetail;
import com.echothree.model.data.comment.server.entity.CommentString;
import com.echothree.model.data.comment.server.entity.CommentType;
import com.echothree.model.data.comment.server.entity.CommentTypeDescription;
import com.echothree.model.data.comment.server.entity.CommentUsage;
import com.echothree.model.data.comment.server.entity.CommentUsageType;
import com.echothree.model.data.comment.server.entity.CommentUsageTypeDescription;
import com.echothree.model.data.comment.server.factory.CommentBlobFactory;
import com.echothree.model.data.comment.server.factory.CommentClobFactory;
import com.echothree.model.data.comment.server.factory.CommentDetailFactory;
import com.echothree.model.data.comment.server.factory.CommentFactory;
import com.echothree.model.data.comment.server.factory.CommentStringFactory;
import com.echothree.model.data.comment.server.factory.CommentTypeDescriptionFactory;
import com.echothree.model.data.comment.server.factory.CommentTypeDetailFactory;
import com.echothree.model.data.comment.server.factory.CommentTypeFactory;
import com.echothree.model.data.comment.server.factory.CommentUsageFactory;
import com.echothree.model.data.comment.server.factory.CommentUsageTypeDescriptionFactory;
import com.echothree.model.data.comment.server.factory.CommentUsageTypeDetailFactory;
import com.echothree.model.data.comment.server.factory.CommentUsageTypeFactory;
import com.echothree.model.data.comment.server.value.CommentBlobValue;
import com.echothree.model.data.comment.server.value.CommentClobValue;
import com.echothree.model.data.comment.server.value.CommentDetailValue;
import com.echothree.model.data.comment.server.value.CommentStringValue;
import com.echothree.model.data.comment.server.value.CommentTypeDescriptionValue;
import com.echothree.model.data.comment.server.value.CommentTypeDetailValue;
import com.echothree.model.data.comment.server.value.CommentUsageTypeDescriptionValue;
import com.echothree.model.data.comment.server.value.CommentUsageTypeDetailValue;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.core.server.entity.MimeTypeUsageType;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import static com.echothree.model.jooq.server.keys.comment.CommentForeignKeys.COMMENTS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.comment.CommentForeignKeys.COMMENTS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.comment.CommentForeignKeys.COMMENT_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.comment.CommentForeignKeys.COMMENT_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.comment.CommentForeignKeys.COMMENT_USAGES_COMMENT_FK;
import static com.echothree.model.jooq.server.keys.comment.CommentForeignKeys.COMMENT_USAGES_COMMENT_USAGE_TYPE_FK;
import static com.echothree.model.jooq.server.keys.comment.CommentForeignKeys.COMMENT_USAGE_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.comment.CommentForeignKeys.COMMENT_USAGE_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.comment.CommentForeignKeys.COMMENT_USAGE_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.tables.comment.CommentBlobs.CommentBlobs;
import static com.echothree.model.jooq.server.tables.comment.CommentClobs.CommentClobs;
import static com.echothree.model.jooq.server.tables.comment.CommentDetails.CommentDetails;
import static com.echothree.model.jooq.server.tables.comment.CommentStrings.CommentStrings;
import static com.echothree.model.jooq.server.tables.comment.CommentTypeDescriptions.CommentTypeDescriptions;
import static com.echothree.model.jooq.server.tables.comment.CommentTypeDetails.CommentTypeDetails;
import static com.echothree.model.jooq.server.tables.comment.CommentTypes.CommentTypes;
import static com.echothree.model.jooq.server.tables.comment.CommentUsageTypeDescriptions.CommentUsageTypeDescriptions;
import static com.echothree.model.jooq.server.tables.comment.CommentUsageTypeDetails.CommentUsageTypeDetails;
import static com.echothree.model.jooq.server.tables.comment.CommentUsageTypes.CommentUsageTypes;
import static com.echothree.model.jooq.server.tables.comment.CommentUsages.CommentUsages;
import static com.echothree.model.jooq.server.tables.comment.Comments.Comments;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;

@CommandScope
public class CommentControl
        extends BaseModelControl {
    
    /** Creates a new instance of CommentControl */

    protected CommentControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Comment Types
    // --------------------------------------------------------------------------------

    @Inject
    protected CommentTypeDetailFactory commentTypeDetailFactory;

    @Inject
    protected CommentTypeFactory commentTypeFactory;

    @Inject
    CommentTypeTransferCache commentTypeTransferCache;

    public CommentType createCommentType(EntityType entityType, String commentTypeName, Sequence commentSequence,
            WorkflowEntrance workflowEntrance, MimeTypeUsageType mimeTypeUsageType, Integer sortOrder, BasePK createdBy) {
        var commentType = commentTypeFactory.create();
        var commentTypeDetail = commentTypeDetailFactory.create(commentType, entityType,
                commentTypeName, commentSequence, workflowEntrance, mimeTypeUsageType, sortOrder, session.getStartTime(),
                Session.MAX_TIME);
        
        // Convert to R/W
        commentType = commentTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                commentType.getPrimaryKey());
        commentType.setActiveDetail(commentTypeDetail);
        commentType.setLastDetail(commentTypeDetail);
        commentType.store();
        
        sendEvent(commentType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return commentType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.CommentType */
    public CommentType getCommentTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new CommentTypePK(entityInstance.getEntityUniqueId());

        return commentTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public CommentType getCommentTypeByEntityInstance(EntityInstance entityInstance) {
        return getCommentTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public CommentType getCommentTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getCommentTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countCommentTypes(final EntityType entityType) {
        return session.getDslContext()
                .selectCount()
                .from(CommentTypes)
                .join(CommentTypeDetails).onKey(COMMENT_TYPES_ACTIVE_DETAIL_FK)
                .where(CommentTypeDetails.ENTITY_TYPE.eq(entityType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<CommentType> getCommentTypes(EntityType entityType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CommentTypes.fields())
                .from(CommentTypes)
                .join(CommentTypeDetails).onKey(COMMENT_TYPES_ACTIVE_DETAIL_FK)
                .where(CommentTypeDetails.ENTITY_TYPE.eq(entityType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(CommentTypeDetails.SORT_ORDER, CommentTypeDetails.COMMENT_TYPE_NAME),
                    CommentTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return commentTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<CommentType> getCommentTypes(EntityType entityType) {
        return getCommentTypes(entityType, EntityPermission.READ_ONLY);
    }
    
    public List<CommentType> getCommentTypesForUpdate(EntityType entityType) {
        return getCommentTypes(entityType, EntityPermission.READ_WRITE);
    }
    
    private CommentType getCommentTypeByName(EntityType entityType, String commentTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CommentTypes.fields())
                .from(CommentTypes)
                .join(CommentTypeDetails).onKey(COMMENT_TYPES_ACTIVE_DETAIL_FK)
                .where(CommentTypeDetails.ENTITY_TYPE.eq(entityType.getPrimaryKey()),
                        CommentTypeDetails.COMMENT_TYPE_NAME.eq(commentTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return commentTypeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public CommentType getCommentTypeByName(EntityType entityType, String commentTypeName) {
        return getCommentTypeByName(entityType, commentTypeName, EntityPermission.READ_ONLY);
    }
    
    public CommentType getCommentTypeByNameForUpdate(EntityType entityType, String commentTypeName) {
        return getCommentTypeByName(entityType, commentTypeName, EntityPermission.READ_WRITE);
    }
    
    public CommentTypeDetailValue getCommentTypeDetailValueForUpdate(CommentType commentType) {
        return commentType == null? null: commentType.getLastDetailForUpdate().getCommentTypeDetailValue().clone();
    }
    
    public CommentTypeDetailValue getCommentTypeDetailValueByNameForUpdate(EntityType entityType, String commentTypeName) {
        return getCommentTypeDetailValueForUpdate(getCommentTypeByNameForUpdate(entityType, commentTypeName));
    }
    
    public CommentTypeTransfer getCommentTypeTransfer(UserVisit userVisit, CommentType commentType) {
        return commentTypeTransferCache.getCommentTypeTransfer(userVisit, commentType);
    }
    
    public List<CommentTypeTransfer> getCommentTypeTransfers(UserVisit userVisit, Collection<CommentType> commentTypes) {
        List<CommentTypeTransfer> commentTypeTransfers = new ArrayList<>(commentTypes.size());

        commentTypes.forEach((commentType) ->
                commentTypeTransfers.add(commentTypeTransferCache.getCommentTypeTransfer(userVisit, commentType))
        );

        return commentTypeTransfers;
    }

    public List<CommentTypeTransfer> getCommentTypeTransfers(UserVisit userVisit, EntityType entityType) {
        return getCommentTypeTransfers(userVisit, getCommentTypes(entityType));
    }
    
    public void updateCommentTypeFromValue(CommentTypeDetailValue commentTypeDetailValue, BasePK updatedBy) {
        if(commentTypeDetailValue.hasBeenModified()) {
            var commentType = commentTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     commentTypeDetailValue.getCommentTypePK());
            var commentTypeDetail = commentType.getActiveDetailForUpdate();
            
            commentTypeDetail.setThruTime(session.getStartTime());
            commentTypeDetail.store();

            var commentTypePK = commentTypeDetail.getCommentTypePK(); // Not updated
            var entityTypePK = commentTypeDetail.getEntityTypePK(); // Not updated
            var commentTypeName = commentTypeDetailValue.getCommentTypeName();
            var commentSequencePK = commentTypeDetailValue.getCommentSequencePK();
            var workflowEntrancePK = commentTypeDetailValue.getWorkflowEntrancePK();
            var mimeTypeUsageTypePK = commentTypeDetail.getMimeTypeUsageTypePK(); // Not updated
            var sortOrder = commentTypeDetailValue.getSortOrder();
            
            commentTypeDetail = commentTypeDetailFactory.create(commentTypePK, entityTypePK, commentTypeName,
                    commentSequencePK, workflowEntrancePK, mimeTypeUsageTypePK, sortOrder, session.getStartTime(),
                    Session.MAX_TIME);
            
            commentType.setActiveDetail(commentTypeDetail);
            commentType.setLastDetail(commentTypeDetail);
            
            sendEvent(commentTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deleteCommentType(CommentType commentType, BasePK deletedBy) {
        deleteCommentTypeDescriptionsByCommentType(commentType, deletedBy);
        deleteCommentUsageTypesByCommentType(commentType, deletedBy);
        deleteCommentsByCommentType(commentType, deletedBy);

        var commentTypeDetail = commentType.getLastDetailForUpdate();
        commentTypeDetail.setThruTime(session.getStartTime());
        commentType.setActiveDetail(null);
        commentType.store();
        
        sendEvent(commentType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteCommentTypes(List<CommentType> commentTypes, BasePK deletedBy) {
        commentTypes.forEach((commentType) -> 
                deleteCommentType(commentType, deletedBy)
        );
    }
    
    public void deleteCommentTypesByEntityType(EntityType entityType, BasePK deletedBy) {
        deleteCommentTypes(getCommentTypesForUpdate(entityType), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Comment Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected CommentTypeDescriptionFactory commentTypeDescriptionFactory;

    @Inject
    CommentTypeDescriptionTransferCache commentTypeDescriptionTransferCache;

    public CommentTypeDescription createCommentTypeDescription(CommentType commentType, Language language, String description,
            BasePK createdBy) {
        var commentTypeDescription = commentTypeDescriptionFactory.create(commentType,
                language, description, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(commentType.getPrimaryKey(), EventTypes.MODIFY, commentTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return commentTypeDescription;
    }
    
    private CommentTypeDescription getCommentTypeDescription(CommentType commentType, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CommentTypeDescriptions.fields())
                .from(CommentTypeDescriptions)
                .where(CommentTypeDescriptions.COMMENT_TYPE.eq(commentType.getPrimaryKey()),
                        CommentTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        CommentTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return commentTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public CommentTypeDescription getCommentTypeDescription(CommentType commentType, Language language) {
        return getCommentTypeDescription(commentType, language, EntityPermission.READ_ONLY);
    }
    
    public CommentTypeDescription getCommentTypeDescriptionForUpdate(CommentType commentType, Language language) {
        return getCommentTypeDescription(commentType, language, EntityPermission.READ_WRITE);
    }
    
    public CommentTypeDescriptionValue getCommentTypeDescriptionValue(CommentTypeDescription commentTypeDescription) {
        return commentTypeDescription == null? null: commentTypeDescription.getCommentTypeDescriptionValue().clone();
    }
    
    public CommentTypeDescriptionValue getCommentTypeDescriptionValueForUpdate(CommentType commentType, Language language) {
        return getCommentTypeDescriptionValue(getCommentTypeDescriptionForUpdate(commentType, language));
    }
    
    private List<CommentTypeDescription> getCommentTypeDescriptionsByCommentType(CommentType commentType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(CommentTypeDescriptions.fields())
                    .from(CommentTypeDescriptions)
                    .join(Languages).onKey(COMMENT_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(CommentTypeDescriptions.COMMENT_TYPE.eq(commentType.getPrimaryKey()),
                            CommentTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    CommentTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(CommentTypeDescriptions.fields())
                    .from(CommentTypeDescriptions)
                    .where(CommentTypeDescriptions.COMMENT_TYPE.eq(commentType.getPrimaryKey()),
                            CommentTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return commentTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<CommentTypeDescription> getCommentTypeDescriptionsByCommentType(CommentType commentType) {
        return getCommentTypeDescriptionsByCommentType(commentType, EntityPermission.READ_ONLY);
    }
    
    public List<CommentTypeDescription> getCommentTypeDescriptionsByCommentTypeForUpdate(CommentType commentType) {
        return getCommentTypeDescriptionsByCommentType(commentType, EntityPermission.READ_WRITE);
    }
    
    public String getBestCommentTypeDescription(CommentType commentType, Language language) {
        String description;
        var commentTypeDescription = getCommentTypeDescription(commentType, language);
        
        if(commentTypeDescription == null && !language.getIsDefault()) {
            commentTypeDescription = getCommentTypeDescription(commentType, partyControl.getDefaultLanguage());
        }
        
        if(commentTypeDescription == null) {
            description = commentType.getLastDetail().getCommentTypeName();
        } else {
            description = commentTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public CommentTypeDescriptionTransfer getCommentTypeDescriptionTransfer(UserVisit userVisit, CommentTypeDescription commentTypeDescription) {
        return commentTypeDescriptionTransferCache.getCommentTypeDescriptionTransfer(userVisit, commentTypeDescription);
    }
    
    public List<CommentTypeDescriptionTransfer> getCommentTypeDescriptionTransfers(UserVisit userVisit, CommentType commentType) {
        var commentTypeDescriptions = getCommentTypeDescriptionsByCommentType(commentType);
        List<CommentTypeDescriptionTransfer> commentTypeDescriptionTransfers = new ArrayList<>(commentTypeDescriptions.size());
        
        commentTypeDescriptions.forEach((commentTypeDescription) ->
                commentTypeDescriptionTransfers.add(commentTypeDescriptionTransferCache.getCommentTypeDescriptionTransfer(userVisit, commentTypeDescription))
        );
        
        return commentTypeDescriptionTransfers;
    }
    
    public void updateCommentTypeDescriptionFromValue(CommentTypeDescriptionValue commentTypeDescriptionValue, BasePK updatedBy) {
        if(commentTypeDescriptionValue.hasBeenModified()) {
            var commentTypeDescription = commentTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE, commentTypeDescriptionValue.getPrimaryKey());
            
            commentTypeDescription.setThruTime(session.getStartTime());
            commentTypeDescription.store();

            var commentType = commentTypeDescription.getCommentType();
            var language = commentTypeDescription.getLanguage();
            var description = commentTypeDescriptionValue.getDescription();
            
            commentTypeDescription = commentTypeDescriptionFactory.create(commentType, language, description, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(commentType.getPrimaryKey(), EventTypes.MODIFY, commentTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteCommentTypeDescription(CommentTypeDescription commentTypeDescription, BasePK deletedBy) {
        commentTypeDescription.setThruTime(session.getStartTime());
        
        sendEvent(commentTypeDescription.getCommentTypePK(), EventTypes.MODIFY, commentTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteCommentTypeDescriptionsByCommentType(CommentType commentType, BasePK deletedBy) {
        var commentTypeDescriptions = getCommentTypeDescriptionsByCommentTypeForUpdate(commentType);
        
        commentTypeDescriptions.forEach((commentTypeDescription) -> 
                deleteCommentTypeDescription(commentTypeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Comment Usage Types
    // --------------------------------------------------------------------------------

    @Inject
    protected CommentUsageTypeDetailFactory commentUsageTypeDetailFactory;

    @Inject
    protected CommentUsageTypeFactory commentUsageTypeFactory;

    @Inject
    CommentUsageTypeTransferCache commentUsageTypeTransferCache;

    public CommentUsageType createCommentUsageType(CommentType commentType, String commentUsageTypeName, Boolean selectedByDefault,
            Integer sortOrder, BasePK createdBy) {
        var commentUsageType = commentUsageTypeFactory.create();
        var commentUsageTypeDetail = commentUsageTypeDetailFactory.create(
                commentUsageType, commentType, commentUsageTypeName, selectedByDefault, sortOrder, session.getStartTime(),
                Session.MAX_TIME);
        
        // Convert to R/W
        commentUsageType = commentUsageTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                commentUsageType.getPrimaryKey());
        commentUsageType.setActiveDetail(commentUsageTypeDetail);
        commentUsageType.setLastDetail(commentUsageTypeDetail);
        commentUsageType.store();
        
        sendEvent(commentUsageType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return commentUsageType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.CommentUsageType */
    public CommentUsageType getCommentUsageTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new CommentUsageTypePK(entityInstance.getEntityUniqueId());

        return commentUsageTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public CommentUsageType getCommentUsageTypeByEntityInstance(EntityInstance entityInstance) {
        return getCommentUsageTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public CommentUsageType getCommentUsageTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getCommentUsageTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countCommentUsageTypesByCommentType(final CommentType commentType) {
        return session.getDslContext()
                .selectCount()
                .from(CommentUsageTypes)
                .join(CommentUsageTypeDetails).onKey(COMMENT_USAGE_TYPES_ACTIVE_DETAIL_FK)
                .where(CommentUsageTypeDetails.COMMENT_TYPE.eq(commentType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<CommentUsageType> getCommentUsageTypes(CommentType commentType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CommentUsageTypes.fields())
                .from(CommentUsageTypes)
                .join(CommentUsageTypeDetails).onKey(COMMENT_USAGE_TYPES_ACTIVE_DETAIL_FK)
                .where(CommentUsageTypeDetails.COMMENT_TYPE.eq(commentType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(CommentUsageTypeDetails.SORT_ORDER, CommentUsageTypeDetails.COMMENT_USAGE_TYPE_NAME),
                    CommentUsageTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return commentUsageTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<CommentUsageType> getCommentUsageTypes(CommentType commentType) {
        return getCommentUsageTypes(commentType, EntityPermission.READ_ONLY);
    }
    
    public List<CommentUsageType> getCommentUsageTypesForUpdate(CommentType commentType) {
        return getCommentUsageTypes(commentType, EntityPermission.READ_WRITE);
    }
    
    private CommentUsageType getCommentUsageTypeByName(CommentType commentType, String commentUsageTypeName,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CommentUsageTypes.fields())
                .from(CommentUsageTypes)
                .join(CommentUsageTypeDetails).onKey(COMMENT_USAGE_TYPES_ACTIVE_DETAIL_FK)
                .where(CommentUsageTypeDetails.COMMENT_TYPE.eq(commentType.getPrimaryKey()),
                        CommentUsageTypeDetails.COMMENT_USAGE_TYPE_NAME.eq(commentUsageTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return commentUsageTypeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public CommentUsageType getCommentUsageTypeByName(CommentType commentType, String commentUsageTypeName) {
        return getCommentUsageTypeByName(commentType, commentUsageTypeName, EntityPermission.READ_ONLY);
    }
    
    public CommentUsageType getCommentUsageTypeByNameForUpdate(CommentType commentType, String commentUsageTypeName) {
        return getCommentUsageTypeByName(commentType, commentUsageTypeName, EntityPermission.READ_WRITE);
    }
    
    public CommentUsageTypeDetailValue getCommentUsageTypeDetailValueForUpdate(CommentUsageType commentUsageType) {
        return commentUsageType == null? null: commentUsageType.getLastDetailForUpdate().getCommentUsageTypeDetailValue().clone();
    }
    
    public CommentUsageTypeDetailValue getCommentUsageTypeDetailValueByNameForUpdate(CommentType commentType, String commentUsageTypeName) {
        return getCommentUsageTypeDetailValueForUpdate(getCommentUsageTypeByNameForUpdate(commentType, commentUsageTypeName));
    }
    
    public CommentUsageTypeTransfer getCommentUsageTypeTransfer(UserVisit userVisit, CommentUsageType commentUsageType) {
        return commentUsageTypeTransferCache.getCommentUsageTypeTransfer(userVisit, commentUsageType);
    }
    
    public List<CommentUsageTypeTransfer> getCommentUsageTypeTransfers(UserVisit userVisit, Collection<CommentUsageType> commentUsageTypes) {
        List<CommentUsageTypeTransfer> commentUsageTypeTransfers = new ArrayList<>(commentUsageTypes.size());

        commentUsageTypes.forEach((commentUsageType) ->
                commentUsageTypeTransfers.add(commentUsageTypeTransferCache.getCommentUsageTypeTransfer(userVisit, commentUsageType))
        );

        return commentUsageTypeTransfers;
    }

    public List<CommentUsageTypeTransfer> getCommentUsageTypeTransfers(UserVisit userVisit, CommentType commentType) {
        return getCommentUsageTypeTransfers(userVisit, getCommentUsageTypes(commentType));
    }
    
    public void updateCommentUsageTypeFromValue(CommentUsageTypeDetailValue commentUsageTypeDetailValue, BasePK updatedBy) {
        if(commentUsageTypeDetailValue.hasBeenModified()) {
            var commentUsageType = commentUsageTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     commentUsageTypeDetailValue.getCommentUsageTypePK());
            var commentUsageTypeDetail = commentUsageType.getActiveDetailForUpdate();
            
            commentUsageTypeDetail.setThruTime(session.getStartTime());
            commentUsageTypeDetail.store();

            var commentUsageTypePK = commentUsageTypeDetail.getCommentUsageTypePK(); // Not updated
            var commentTypePK = commentUsageTypeDetail.getCommentTypePK(); // Not updated
            var commentUsageTypeName = commentUsageTypeDetailValue.getCommentUsageTypeName();
            var selectedByDefault = commentUsageTypeDetailValue.getSelectedByDefault();
            var sortOrder = commentUsageTypeDetailValue.getSortOrder();
            
            commentUsageTypeDetail = commentUsageTypeDetailFactory.create(commentUsageTypePK, commentTypePK,
                    commentUsageTypeName, selectedByDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            commentUsageType.setActiveDetail(commentUsageTypeDetail);
            commentUsageType.setLastDetail(commentUsageTypeDetail);
            
            sendEvent(commentUsageTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deleteCommentUsageType(CommentUsageType commentUsageType, BasePK deletedBy) {
        deleteCommentUsageTypeDescriptionsByCommentUsageType(commentUsageType, deletedBy);
        deleteCommentUsagesByCommentUsageType(commentUsageType, deletedBy);

        var commentUsageTypeDetail = commentUsageType.getLastDetailForUpdate();
        commentUsageTypeDetail.setThruTime(session.getStartTime());
        commentUsageType.setActiveDetail(null);
        commentUsageType.store();
        
        sendEvent(commentUsageType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteCommentUsageTypes(List<CommentUsageType> commentUsageTypes, BasePK deletedBy) {
        commentUsageTypes.forEach((commentUsageType) -> 
                deleteCommentUsageType(commentUsageType, deletedBy)
        );
    }
    
    public void deleteCommentUsageTypesByCommentType(CommentType commentType, BasePK deletedBy) {
        deleteCommentUsageTypes(getCommentUsageTypesForUpdate(commentType), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Comment Usage Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected CommentUsageTypeDescriptionFactory commentUsageTypeDescriptionFactory;

    @Inject
    CommentUsageTypeDescriptionTransferCache commentUsageTypeDescriptionTransferCache;

    public CommentUsageTypeDescription createCommentUsageTypeDescription(CommentUsageType commentUsageType, Language language,
            String description, BasePK createdBy) {
        var commentUsageTypeDescription = commentUsageTypeDescriptionFactory.create(
                commentUsageType, language, description, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(commentUsageType.getPrimaryKey(), EventTypes.MODIFY, commentUsageTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return commentUsageTypeDescription;
    }
    
    private CommentUsageTypeDescription getCommentUsageTypeDescription(CommentUsageType commentUsageType, Language language,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CommentUsageTypeDescriptions.fields())
                .from(CommentUsageTypeDescriptions)
                .where(CommentUsageTypeDescriptions.COMMENT_USAGE_TYPE.eq(commentUsageType.getPrimaryKey()),
                        CommentUsageTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        CommentUsageTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return commentUsageTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public CommentUsageTypeDescription getCommentUsageTypeDescription(CommentUsageType commentUsageType, Language language) {
        return getCommentUsageTypeDescription(commentUsageType, language, EntityPermission.READ_ONLY);
    }
    
    public CommentUsageTypeDescription getCommentUsageTypeDescriptionForUpdate(CommentUsageType commentUsageType, Language language) {
        return getCommentUsageTypeDescription(commentUsageType, language, EntityPermission.READ_WRITE);
    }
    
    public CommentUsageTypeDescriptionValue getCommentUsageTypeDescriptionValue(CommentUsageTypeDescription commentUsageTypeDescription) {
        return commentUsageTypeDescription == null? null: commentUsageTypeDescription.getCommentUsageTypeDescriptionValue().clone();
    }
    
    public CommentUsageTypeDescriptionValue getCommentUsageTypeDescriptionValueForUpdate(CommentUsageType commentUsageType, Language language) {
        return getCommentUsageTypeDescriptionValue(getCommentUsageTypeDescriptionForUpdate(commentUsageType, language));
    }
    
    private List<CommentUsageTypeDescription> getCommentUsageTypeDescriptionsByCommentUsageType(CommentUsageType commentUsageType,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(CommentUsageTypeDescriptions.fields())
                    .from(CommentUsageTypeDescriptions)
                    .join(Languages).onKey(COMMENT_USAGE_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(CommentUsageTypeDescriptions.COMMENT_USAGE_TYPE.eq(commentUsageType.getPrimaryKey()),
                            CommentUsageTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    CommentUsageTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(CommentUsageTypeDescriptions.fields())
                    .from(CommentUsageTypeDescriptions)
                    .where(CommentUsageTypeDescriptions.COMMENT_USAGE_TYPE.eq(commentUsageType.getPrimaryKey()),
                            CommentUsageTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return commentUsageTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<CommentUsageTypeDescription> getCommentUsageTypeDescriptionsByCommentUsageType(CommentUsageType commentUsageType) {
        return getCommentUsageTypeDescriptionsByCommentUsageType(commentUsageType, EntityPermission.READ_ONLY);
    }
    
    public List<CommentUsageTypeDescription> getCommentUsageTypeDescriptionsByCommentUsageTypeForUpdate(CommentUsageType commentUsageType) {
        return getCommentUsageTypeDescriptionsByCommentUsageType(commentUsageType, EntityPermission.READ_WRITE);
    }
    
    public String getBestCommentUsageTypeDescription(CommentUsageType commentUsageType, Language language) {
        String description;
        var commentUsageTypeDescription = getCommentUsageTypeDescription(commentUsageType, language);
        
        if(commentUsageTypeDescription == null && !language.getIsDefault()) {
            commentUsageTypeDescription = getCommentUsageTypeDescription(commentUsageType, partyControl.getDefaultLanguage());
        }
        
        if(commentUsageTypeDescription == null) {
            description = commentUsageType.getLastDetail().getCommentUsageTypeName();
        } else {
            description = commentUsageTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public CommentUsageTypeDescriptionTransfer getCommentUsageTypeDescriptionTransfer(UserVisit userVisit, CommentUsageTypeDescription commentUsageTypeDescription) {
        return commentUsageTypeDescriptionTransferCache.getCommentUsageTypeDescriptionTransfer(userVisit, commentUsageTypeDescription);
    }
    
    public List<CommentUsageTypeDescriptionTransfer> getCommentUsageTypeDescriptionTransfers(UserVisit userVisit, CommentUsageType commentUsageType) {
        var commentUsageTypeDescriptions = getCommentUsageTypeDescriptionsByCommentUsageType(commentUsageType);
        List<CommentUsageTypeDescriptionTransfer> commentUsageTypeDescriptionTransfers = new ArrayList<>(commentUsageTypeDescriptions.size());
        
        commentUsageTypeDescriptions.forEach((commentUsageTypeDescription) ->
                commentUsageTypeDescriptionTransfers.add(commentUsageTypeDescriptionTransferCache.getCommentUsageTypeDescriptionTransfer(userVisit, commentUsageTypeDescription))
        );
        
        return commentUsageTypeDescriptionTransfers;
    }
    
    public void updateCommentUsageTypeDescriptionFromValue(CommentUsageTypeDescriptionValue commentUsageTypeDescriptionValue, BasePK updatedBy) {
        if(commentUsageTypeDescriptionValue.hasBeenModified()) {
            var commentUsageTypeDescription = commentUsageTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     commentUsageTypeDescriptionValue.getPrimaryKey());
            
            commentUsageTypeDescription.setThruTime(session.getStartTime());
            commentUsageTypeDescription.store();

            var commentUsageType = commentUsageTypeDescription.getCommentUsageType();
            var language = commentUsageTypeDescription.getLanguage();
            var description = commentUsageTypeDescriptionValue.getDescription();
            
            commentUsageTypeDescription = commentUsageTypeDescriptionFactory.create(commentUsageType,
                    language, description, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(commentUsageType.getPrimaryKey(), EventTypes.MODIFY, commentUsageTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteCommentUsageTypeDescription(CommentUsageTypeDescription commentUsageTypeDescription, BasePK deletedBy) {
        commentUsageTypeDescription.setThruTime(session.getStartTime());
        
        sendEvent(commentUsageTypeDescription.getCommentUsageTypePK(), EventTypes.MODIFY, commentUsageTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteCommentUsageTypeDescriptionsByCommentUsageType(CommentUsageType commentUsageType, BasePK deletedBy) {
        var commentUsageTypeDescriptions = getCommentUsageTypeDescriptionsByCommentUsageTypeForUpdate(commentUsageType);
        
        commentUsageTypeDescriptions.forEach((commentUsageTypeDescription) -> 
                deleteCommentUsageTypeDescription(commentUsageTypeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Comments
    // --------------------------------------------------------------------------------

    @Inject
    protected CommentDetailFactory commentDetailFactory;

    @Inject
    protected CommentFactory commentFactory;

    @Inject
    CommentTransferCache commentTransferCache;

    public Comment createComment(String commentName, CommentType commentType, EntityInstance commentedEntityInstance,
            EntityInstance commentedByEntityInstance, Language language, String description, MimeType mimeType, BasePK createdBy) {
        var comment = commentFactory.create();
        var commentDetail = commentDetailFactory.create(comment, commentName, commentType,
                commentedEntityInstance, commentedByEntityInstance, language, description, mimeType, session.getStartTime(),
                Session.MAX_TIME);
        
        // Convert to R/W
        comment = commentFactory.getEntityFromPK(EntityPermission.READ_WRITE, comment.getPrimaryKey());
        comment.setActiveDetail(commentDetail);
        comment.setLastDetail(commentDetail);
        comment.store();
        
        sendEvent(comment.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        sendEvent(commentedEntityInstance, EventTypes.TOUCH, comment.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return comment;
    }
    
    private Comment getCommentByName(String commentName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Comments.fields())
                .from(Comments)
                .join(CommentDetails).onKey(COMMENTS_ACTIVE_DETAIL_FK)
                .where(CommentDetails.COMMENT_NAME.eq(commentName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return commentFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public Comment getCommentByName(String commentName) {
        return getCommentByName(commentName, EntityPermission.READ_ONLY);
    }
    
    public Comment getCommentByNameForUpdate(String commentName) {
        return getCommentByName(commentName, EntityPermission.READ_WRITE);
    }
    
    public CommentDetailValue getCommentDetailValue(CommentDetail commentDetail) {
        return commentDetail == null? null: commentDetail.getCommentDetailValue().clone();
    }
    
    public CommentDetailValue getCommentDetailValueForUpdate(Comment comment) {
        return comment == null? null: comment.getLastDetailForUpdate().getCommentDetailValue().clone();
    }
    
    public CommentDetailValue getCommentDetailValueByNameForUpdate(String commentName) {
        return getCommentDetailValueForUpdate(getCommentByNameForUpdate(commentName));
    }
    
    private List<Comment> getCommentsByCommentedEntityInstance(EntityInstance commentedEntityInstance, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Comments.fields())
                .from(Comments)
                .join(CommentDetails).onKey(COMMENTS_ACTIVE_DETAIL_FK)
                .where(CommentDetails.COMMENTED_ENTITY_INSTANCE.eq(commentedEntityInstance.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(CommentDetails.COMMENT_NAME),
                    CommentFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return commentFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<Comment> getCommentsByCommentedEntityInstance(EntityInstance commentedEntityInstance) {
        return getCommentsByCommentedEntityInstance(commentedEntityInstance, EntityPermission.READ_ONLY);
    }
    
    public List<Comment> getCommentsByCommentedEntityInstanceForUpdate(EntityInstance commentedEntityInstance) {
        return getCommentsByCommentedEntityInstance(commentedEntityInstance, EntityPermission.READ_WRITE);
    }
    
    private List<Comment> getCommentsByCommentedByEntityInstance(EntityInstance commentedByEntityInstance, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Comments.fields())
                .from(Comments)
                .join(CommentDetails).onKey(COMMENTS_ACTIVE_DETAIL_FK)
                .where(CommentDetails.COMMENTED_BY_ENTITY_INSTANCE.eq(commentedByEntityInstance.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(CommentDetails.COMMENT_NAME),
                    CommentFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return commentFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<Comment> getCommentsByCommentedByEntityInstance(EntityInstance commentedByEntityInstance) {
        return getCommentsByCommentedByEntityInstance(commentedByEntityInstance, EntityPermission.READ_ONLY);
    }
    
    public List<Comment> getCommentsByCommentedByEntityInstanceForUpdate(EntityInstance commentedByEntityInstance) {
        return getCommentsByCommentedByEntityInstance(commentedByEntityInstance, EntityPermission.READ_WRITE);
    }
    
    private List<Comment> getCommentsByCommentType(CommentType commentType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Comments.fields())
                .from(Comments)
                .join(CommentDetails).onKey(COMMENTS_ACTIVE_DETAIL_FK)
                .where(CommentDetails.COMMENT_TYPE.eq(commentType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(CommentDetails.COMMENT_NAME),
                    CommentFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return commentFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<Comment> getCommentsByCommentType(CommentType commentType) {
        return getCommentsByCommentType(commentType, EntityPermission.READ_ONLY);
    }
    
    public List<Comment> getCommentsByCommentTypeForUpdate(CommentType commentType) {
        return getCommentsByCommentType(commentType, EntityPermission.READ_WRITE);
    }
    
    public List<Comment> getCommentsByCommentedEntityInstanceAndCommentType(EntityInstance commentedEntityInstance,
            CommentType commentType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Comments.fields())
                .from(Comments)
                .join(CommentDetails).onKey(COMMENTS_ACTIVE_DETAIL_FK)
                .where(CommentDetails.COMMENTED_ENTITY_INSTANCE.eq(commentedEntityInstance.getPrimaryKey()),
                        CommentDetails.COMMENT_TYPE.eq(commentType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(CommentDetails.COMMENT_NAME),
                    CommentFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return commentFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<Comment> getCommentsByCommentedEntityInstanceAndCommentType(EntityInstance commentedEntityInstance,
            CommentType commentType) {
        return getCommentsByCommentedEntityInstanceAndCommentType(commentedEntityInstance, commentType, EntityPermission.READ_ONLY);
    }
    
    public List<Comment> getCommentsByCommentedEntityInstanceAndCommentTypeForUpdate(EntityInstance commentedEntityInstance,
            CommentType commentType) {
        return getCommentsByCommentedEntityInstanceAndCommentType(commentedEntityInstance, commentType, EntityPermission.READ_WRITE);
    }
    
    public CommentTransfer getCommentTransfer(UserVisit userVisit, Comment comment) {
        return commentTransferCache.getCommentTransfer(userVisit, comment);
    }
    
    public List<CommentTransfer> getCommentTransfers(UserVisit userVisit, Collection<Comment> comments) {
        List<CommentTransfer> commentTransfers = new ArrayList<>(comments.size());
        
        comments.forEach((comment) ->
                commentTransfers.add(commentTransferCache.getCommentTransfer(userVisit, comment))
        );
        
        return commentTransfers;
    }
    
    public List<CommentTransfer> getCommentTransfersByCommentedEntityInstance(UserVisit userVisit, EntityInstance commentedEntityInstance) {
        return getCommentTransfers(userVisit, getCommentsByCommentedEntityInstance(commentedEntityInstance));
    }
    
    public List<CommentTransfer> getCommentTransfersByCommentedEntityInstanceAndCommentType(UserVisit userVisit, EntityInstance commentedEntityInstance,
            CommentType commentType) {
        return getCommentTransfers(userVisit, getCommentsByCommentedEntityInstanceAndCommentType(commentedEntityInstance, commentType));
    }
    
    public void updateCommentFromValue(CommentDetailValue commentDetailValue,  BasePK updatedBy) {
        if(commentDetailValue.hasBeenModified()) {
            var comment = commentFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     commentDetailValue.getCommentPK());
            var commentDetail = comment.getActiveDetailForUpdate();
            
            commentDetail.setThruTime(session.getStartTime());
            commentDetail.store();

            var commentPK = commentDetail.getCommentPK(); // Not updated
            var commentName = commentDetailValue.getCommentName();
            var commentTypePK = commentDetail.getCommentTypePK(); // Not updated
            var commentedEntityInstancePK = commentDetail.getCommentedEntityInstancePK(); // Not updated
            var commentedByEntityInstancePK = commentDetail.getCommentedByEntityInstancePK(); // Not updated
            var languagePK = commentDetailValue.getLanguagePK();
            var description = commentDetailValue.getDescription();
            var mimeTypePK = commentDetailValue.getMimeTypePK();
            
            commentDetail = commentDetailFactory.create(commentPK, commentName, commentTypePK,
                    commentedEntityInstancePK, commentedByEntityInstancePK, languagePK, description, mimeTypePK,
                    session.getStartTime(), Session.MAX_TIME);
            
            comment.setActiveDetail(commentDetail);
            comment.setLastDetail(commentDetail);
            
            sendEvent(comment.getPrimaryKey(), EventTypes.MODIFY, null, null, updatedBy);
            sendEvent(commentDetail.getCommentedEntityInstance(), EventTypes.TOUCH, comment.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public CommentStatusChoicesBean getCommentStatusChoices(ExecutionErrorAccumulator eea, String defaultCommentStatusChoice, Language language,
            boolean allowNullChoice, CommentType commentType, Comment comment, PartyPK partyPK) {
        CommentStatusChoicesBean commentStatusChoicesBean = null;
        
        if(commentType == null) {
            commentType = comment.getLastDetail().getCommentType();
        }

        var workflowEntrance = commentType.getLastDetail().getWorkflowEntrance();
        if(workflowEntrance != null) {
            var workflow = workflowEntrance.getLastDetail().getWorkflow();
        
            commentStatusChoicesBean = new CommentStatusChoicesBean();
            
            if(comment == null) {
                workflowControl.getWorkflowEntranceChoices(commentStatusChoicesBean, defaultCommentStatusChoice, language, allowNullChoice, workflow, partyPK);
            } else {
                var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(comment.getPrimaryKey());
                var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstance(workflow, entityInstance);

                workflowControl.getWorkflowDestinationChoices(commentStatusChoicesBean, defaultCommentStatusChoice, language, allowNullChoice,
                        workflowEntityStatus.getWorkflowStep(), partyPK);
            }
        } else {
            eea.addExecutionError(ExecutionErrors.InvalidCommentType.name(), commentType.getLastDetail().getCommentTypeName());
        }
        
        return commentStatusChoicesBean;
    }
    
    public void setCommentStatus(ExecutionErrorAccumulator eea, Comment comment, String commentStatusChoice, PartyPK modifiedBy) {
        var commentTypeDetail = comment.getLastDetail().getCommentType().getLastDetail();
        var workflowEntrance = commentTypeDetail.getWorkflowEntrance();
        
        if(workflowEntrance != null) {
            var workflow = workflowEntrance.getLastDetail().getWorkflow();
            var entityInstance = getEntityInstanceByBaseEntity(comment);
            var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdate(workflow, entityInstance);
            var workflowDestination = commentStatusChoice == null ? null
                    : workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), commentStatusChoice);

            if(workflowDestination != null || commentStatusChoice == null) {
                workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, null, modifiedBy);
                
                if(!eea.hasExecutionErrors()) {
                    sendEvent(comment.getLastDetail().getCommentedEntityInstance(), EventTypes.TOUCH, comment.getPrimaryKey(), EventTypes.MODIFY, modifiedBy);
                }
            } else {
                eea.addExecutionError(ExecutionErrors.UnknownWorkflowDestinationName.name(), workflow.getLastDetail().getWorkflowName(),
                        workflowEntityStatus.getWorkflowStep().getLastDetail().getWorkflowStepName(), commentStatusChoice);
            }
        } else {
            eea.addExecutionError(ExecutionErrors.InvalidCommentType.name(), commentTypeDetail.getCommentTypeName());
        }
    }
    
    public void deleteComment(Comment comment, BasePK deletedBy) {
        deleteCommentUsagesByComment(comment, deletedBy);

        var commentString = getCommentStringForUpdate(comment);
        if(commentString != null) {
            deleteCommentString(commentString, deletedBy);
        }

        var commentBlob = getCommentBlobForUpdate(comment);
        if(commentBlob != null) {
            deleteCommentBlob(commentBlob, deletedBy);
        }

        var commentClob = getCommentClobForUpdate(comment);
        if(commentClob != null) {
            deleteCommentClob(commentClob, deletedBy);
        }

        var commentDetail = comment.getLastDetailForUpdate();
        commentDetail.setThruTime(session.getStartTime());
        commentDetail.store();
        comment.setActiveDetail(null);
        
        sendEvent(comment.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
        sendEvent(commentDetail.getCommentedEntityInstance(), EventTypes.TOUCH, comment.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteComments(List<Comment> comments, BasePK deletedBy) {
        comments.forEach((comment) -> 
                deleteComment(comment, deletedBy)
        );
    }
    
    public void deleteCommentsByCommentedEntityInstance(EntityInstance ratedEntityInstance, BasePK deletedBy) {
        deleteComments(getCommentsByCommentedEntityInstanceForUpdate(ratedEntityInstance),  deletedBy);
    }
    
    public void deleteCommentsByCommentedByEntityInstance(EntityInstance ratedEntityInstance, BasePK deletedBy) {
        deleteComments(getCommentsByCommentedByEntityInstanceForUpdate(ratedEntityInstance),  deletedBy);
    }
    
    public void deleteCommentsByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteCommentsByCommentedEntityInstance(entityInstance, deletedBy);
        deleteCommentsByCommentedByEntityInstance(entityInstance, deletedBy);
    }
    
    public void deleteCommentsByCommentType(CommentType commentType, BasePK deletedBy) {
        deleteComments(getCommentsByCommentTypeForUpdate(commentType),  deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Comment Strings
    // --------------------------------------------------------------------------------

    @Inject
    protected CommentStringFactory commentStringFactory;

    public CommentString createCommentString(Comment comment, String string, BasePK createdBy) {
        var commentString = commentStringFactory.create(comment, string, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(comment.getPrimaryKey(), EventTypes.MODIFY, commentString.getPrimaryKey(), EventTypes.CREATE, createdBy);
        sendEvent(comment.getLastDetail().getCommentedEntityInstance(), EventTypes.TOUCH, comment.getPrimaryKey(), EventTypes.MODIFY, createdBy);
        
        return commentString;
    }
    
    private CommentString getCommentString(Comment comment, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CommentStrings.fields())
                .from(CommentStrings)
                .where(CommentStrings.COMMENT.eq(comment.getPrimaryKey()),
                        CommentStrings.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return commentStringFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public CommentString getCommentString(Comment comment) {
        return getCommentString(comment, EntityPermission.READ_ONLY);
    }
    
    public CommentString getCommentStringForUpdate(Comment comment) {
        return getCommentString(comment, EntityPermission.READ_WRITE);
    }
    
    public CommentStringValue getCommentStringValue(CommentString commentString) {
        return commentString == null? null: commentString.getCommentStringValue().clone();
    }
    
    public CommentStringValue getCommentStringValueForUpdate(Comment comment) {
        var commentString = getCommentStringForUpdate(comment);
        
        return commentString == null? null: getCommentStringValue(commentString);
    }
    
    public void updateCommentStringFromValue(CommentStringValue commentStringValue, BasePK updatedBy) {
        if(commentStringValue.hasBeenModified()) {
            var commentString = commentStringFactory.getEntityFromPK(EntityPermission.READ_WRITE, commentStringValue.getPrimaryKey());
            
            commentString.setThruTime(session.getStartTime());
            commentString.store();

            var commentPK = commentString.getCommentPK(); // Not updated
            var string = commentStringValue.getString();
            
            commentString = commentStringFactory.create(commentPK, string, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(commentPK, EventTypes.MODIFY, commentString.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
            sendEvent(commentString.getComment().getLastDetail().getCommentedEntityInstance(), EventTypes.TOUCH, commentString.getCommentPK(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteCommentString(CommentString commentString, BasePK deletedBy) {
        commentString.setThruTime(session.getStartTime());
        
        sendEvent(commentString.getCommentPK(), EventTypes.MODIFY, commentString.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        sendEvent(commentString.getComment().getLastDetail().getCommentedEntityInstance(), EventTypes.TOUCH, commentString.getCommentPK(), EventTypes.MODIFY, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Comment Blobs
    // --------------------------------------------------------------------------------

    @Inject
    protected CommentBlobFactory commentBlobFactory;

    public CommentBlob createCommentBlob(Comment comment, ByteArray blob, BasePK createdBy) {
        var commentBlob = commentBlobFactory.create(comment, blob, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(comment.getPrimaryKey(), EventTypes.MODIFY, commentBlob.getPrimaryKey(), EventTypes.CREATE, createdBy);
        sendEvent(comment.getLastDetail().getCommentedEntityInstance(), EventTypes.TOUCH, comment.getPrimaryKey(), EventTypes.MODIFY, createdBy);
        
        return commentBlob;
    }
    
    private CommentBlob getCommentBlob(Comment comment, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CommentBlobs.fields())
                .from(CommentBlobs)
                .where(CommentBlobs.COMMENT.eq(comment.getPrimaryKey()),
                        CommentBlobs.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return commentBlobFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public CommentBlob getCommentBlob(Comment comment) {
        return getCommentBlob(comment, EntityPermission.READ_ONLY);
    }
    
    public CommentBlob getCommentBlobForUpdate(Comment comment) {
        return getCommentBlob(comment, EntityPermission.READ_WRITE);
    }
    
    public CommentBlobValue getCommentBlobValue(CommentBlob commentBlob) {
        return commentBlob == null? null: commentBlob.getCommentBlobValue().clone();
    }
    
    public CommentBlobValue getCommentBlobValueForUpdate(Comment comment) {
        var commentBlob = getCommentBlobForUpdate(comment);
        
        return commentBlob == null? null: getCommentBlobValue(commentBlob);
    }
    
    public void updateCommentBlobFromValue(CommentBlobValue commentBlobValue, BasePK updatedBy) {
        if(commentBlobValue.hasBeenModified()) {
            var commentBlob = commentBlobFactory.getEntityFromPK(EntityPermission.READ_WRITE, commentBlobValue.getPrimaryKey());
            
            commentBlob.setThruTime(session.getStartTime());
            commentBlob.store();

            var commentPK = commentBlob.getCommentPK(); // Not updated
            var blob = commentBlobValue.getBlob();
            
            commentBlob = commentBlobFactory.create(commentPK, blob, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(commentPK, EventTypes.MODIFY, commentBlob.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
            sendEvent(commentBlob.getComment().getLastDetail().getCommentedEntityInstance(), EventTypes.TOUCH, commentBlob.getCommentPK(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteCommentBlob(CommentBlob commentBlob, BasePK deletedBy) {
        commentBlob.setThruTime(session.getStartTime());
        
        sendEvent(commentBlob.getCommentPK(), EventTypes.MODIFY, commentBlob.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        sendEvent(commentBlob.getComment().getLastDetail().getCommentedEntityInstance(), EventTypes.TOUCH, commentBlob.getCommentPK(), EventTypes.MODIFY, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Comment Clobs
    // --------------------------------------------------------------------------------

    @Inject
    protected CommentClobFactory commentClobFactory;

    public CommentClob createCommentClob(Comment comment, String clob, BasePK createdBy) {
        var commentClob = commentClobFactory.create(comment, clob, session.getStartTime(),
                Session.MAX_TIME);
        
        sendEvent(comment.getPrimaryKey(), EventTypes.MODIFY, commentClob.getPrimaryKey(), EventTypes.CREATE, createdBy);
        sendEvent(comment.getLastDetail().getCommentedEntityInstance(), EventTypes.TOUCH, comment.getPrimaryKey(), EventTypes.MODIFY, createdBy);
        
        return commentClob;
    }
    
    private CommentClob getCommentClob(Comment comment, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CommentClobs.fields())
                .from(CommentClobs)
                .where(CommentClobs.COMMENT.eq(comment.getPrimaryKey()),
                        CommentClobs.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return commentClobFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public CommentClob getCommentClob(Comment comment) {
        return getCommentClob(comment, EntityPermission.READ_ONLY);
    }
    
    public CommentClob getCommentClobForUpdate(Comment comment) {
        return getCommentClob(comment, EntityPermission.READ_WRITE);
    }
    
    public CommentClobValue getCommentClobValue(CommentClob commentClob) {
        return commentClob == null? null: commentClob.getCommentClobValue().clone();
    }
    
    public CommentClobValue getCommentClobValueForUpdate(Comment comment) {
        var commentClob = getCommentClobForUpdate(comment);
        
        return commentClob == null? null: getCommentClobValue(commentClob);
    }
    
    public void updateCommentClobFromValue(CommentClobValue commentClobValue, BasePK updatedBy) {
        if(commentClobValue.hasBeenModified()) {
            var commentClob = commentClobFactory.getEntityFromPK(EntityPermission.READ_WRITE, commentClobValue.getPrimaryKey());

            commentClob.setThruTime(session.getStartTime());
            commentClob.store();

            var commentPK = commentClob.getCommentPK(); // Not updated
            var clob = commentClobValue.getClob();

            commentClob = commentClobFactory.create(commentPK, clob, session.getStartTime(), Session.MAX_TIME);

            sendEvent(commentPK, EventTypes.MODIFY, commentClob.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
            sendEvent(commentClob.getComment().getLastDetail().getCommentedEntityInstance(), EventTypes.TOUCH, commentClob.getCommentPK(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteCommentClob(CommentClob commentClob, BasePK deletedBy) {
        commentClob.setThruTime(session.getStartTime());
        
        sendEvent(commentClob.getCommentPK(), EventTypes.MODIFY, commentClob.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        sendEvent(commentClob.getComment().getLastDetail().getCommentedEntityInstance(), EventTypes.TOUCH, commentClob.getCommentPK(), EventTypes.MODIFY, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Comment Usages
    // --------------------------------------------------------------------------------

    @Inject
    protected CommentUsageFactory commentUsageFactory;

    @Inject
    CommentUsageTransferCache commentUsageTransferCache;

    public CommentUsage createCommentUsage(Comment comment, CommentUsageType commentUsageType, BasePK createdBy) {
        var commentUsage = commentUsageFactory.create(comment, commentUsageType,
                session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(comment.getPrimaryKey(), EventTypes.MODIFY, commentUsage.getPrimaryKey(), EventTypes.CREATE, createdBy);
        sendEvent(comment.getLastDetail().getCommentedEntityInstance(), EventTypes.TOUCH, comment.getPrimaryKey(), EventTypes.MODIFY, createdBy);
        
        return commentUsage;
    }
    
    private CommentUsage getCommentUsage(Comment comment, CommentUsageType commentUsageType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CommentUsages.fields())
                .from(CommentUsages)
                .where(CommentUsages.COMMENT.eq(comment.getPrimaryKey()),
                        CommentUsages.COMMENT_USAGE_TYPE.eq(commentUsageType.getPrimaryKey()),
                        CommentUsages.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return commentUsageFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public CommentUsage getCommentUsage(Comment comment, CommentUsageType commentUsageType) {
        return getCommentUsage(comment, commentUsageType, EntityPermission.READ_ONLY);
    }
    
    public CommentUsage getCommentUsageForUpdate(Comment comment, CommentUsageType commentUsageType) {
        return getCommentUsage(comment, commentUsageType, EntityPermission.READ_WRITE);
    }
    
    private List<CommentUsage> getCommentUsagesByComment(Comment comment, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(CommentUsages.fields())
                    .from(CommentUsages)
                    .join(CommentUsageTypes).onKey(COMMENT_USAGES_COMMENT_USAGE_TYPE_FK)
                    .join(CommentUsageTypeDetails).onKey(COMMENT_USAGE_TYPES_LAST_DETAIL_FK)
                    .where(CommentUsages.COMMENT.eq(comment.getPrimaryKey()),
                            CommentUsages.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(CommentUsageTypeDetails.SORT_ORDER, CommentUsageTypeDetails.COMMENT_USAGE_TYPE_NAME),
                    CommentUsageFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(CommentUsages.fields())
                    .from(CommentUsages)
                    .where(CommentUsages.COMMENT.eq(comment.getPrimaryKey()),
                            CommentUsages.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return commentUsageFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<CommentUsage> getCommentUsagesByComment(Comment comment) {
        return getCommentUsagesByComment(comment, EntityPermission.READ_ONLY);
    }
    
    public List<CommentUsage> getCommentUsagesByCommentForUpdate(Comment comment) {
        return getCommentUsagesByComment(comment, EntityPermission.READ_WRITE);
    }
    
    private List<CommentUsage> getCommentUsagesByCommentUsageType(CommentUsageType commentUsageType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(CommentUsages.fields())
                    .from(CommentUsages)
                    .join(Comments).onKey(COMMENT_USAGES_COMMENT_FK)
                    .join(CommentDetails).onKey(COMMENTS_LAST_DETAIL_FK)
                    .where(CommentUsages.COMMENT_USAGE_TYPE.eq(commentUsageType.getPrimaryKey()),
                            CommentUsages.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(CommentDetails.COMMENT_NAME),
                    CommentUsageFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(CommentUsages.fields())
                    .from(CommentUsages)
                    .where(CommentUsages.COMMENT_USAGE_TYPE.eq(commentUsageType.getPrimaryKey()),
                            CommentUsages.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return commentUsageFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<CommentUsage> getCommentUsagesByCommentUsageType(CommentUsageType commentUsageType) {
        return getCommentUsagesByCommentUsageType(commentUsageType, EntityPermission.READ_ONLY);
    }
    
    public List<CommentUsage> getCommentUsagesByCommentUsageTypeForUpdate(CommentUsageType commentUsageType) {
        return getCommentUsagesByCommentUsageType(commentUsageType, EntityPermission.READ_WRITE);
    }
    
    public CommentUsageTransfer getCommentUsageTransfer(UserVisit userVisit, CommentUsage commentUsage) {
        return commentUsageTransferCache.getCommentUsageTransfer(userVisit, commentUsage);
    }
    
    public List<CommentUsageTransfer> getCommentUsageTransfersByComment(UserVisit userVisit, Comment comment) {
        var entities = getCommentUsagesByComment(comment);
        List<CommentUsageTransfer> transfers = new ArrayList<>(entities.size());

        entities.forEach((entity) ->
                transfers.add(commentUsageTransferCache.getCommentUsageTransfer(userVisit, entity))
        );
        
        return transfers;
    }
    
    public void deleteCommentUsage(CommentUsage commentUsage, BasePK deletedBy) {
        commentUsage.setThruTime(session.getStartTime());
        commentUsage.store();
        
        sendEvent(commentUsage.getCommentPK(), EventTypes.MODIFY, commentUsage.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        sendEvent(commentUsage.getComment().getLastDetail().getCommentedEntityInstance(), EventTypes.TOUCH, commentUsage.getCommentPK(), EventTypes.MODIFY, deletedBy);
    }
    
    public void deleteCommentUsages(List<CommentUsage> commentUsages, BasePK deletedBy) {
        commentUsages.forEach((commentUsage) -> 
                deleteCommentUsage(commentUsage, deletedBy)
        );
    }
    
    public void deleteCommentUsagesByComment(Comment comment, BasePK deletedBy) {
        deleteCommentUsages(getCommentUsagesByCommentForUpdate(comment), deletedBy);
    }
    
    public void deleteCommentUsagesByCommentUsageType(CommentUsageType commentUsageType, BasePK deletedBy) {
        deleteCommentUsages(getCommentUsagesByCommentUsageTypeForUpdate(commentUsageType), deletedBy);
    }
    
}
