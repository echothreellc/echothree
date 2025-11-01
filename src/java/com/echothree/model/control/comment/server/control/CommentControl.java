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

package com.echothree.model.control.comment.server.control;

import com.echothree.model.control.comment.common.choice.CommentStatusChoicesBean;
import com.echothree.model.control.comment.common.transfer.CommentTransfer;
import com.echothree.model.control.comment.common.transfer.CommentTypeDescriptionTransfer;
import com.echothree.model.control.comment.common.transfer.CommentTypeTransfer;
import com.echothree.model.control.comment.common.transfer.CommentUsageTransfer;
import com.echothree.model.control.comment.common.transfer.CommentUsageTypeDescriptionTransfer;
import com.echothree.model.control.comment.common.transfer.CommentUsageTypeTransfer;
import com.echothree.model.control.comment.server.transfer.CommentTransferCaches;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
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
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class CommentControl
        extends BaseModelControl {
    
    /** Creates a new instance of CommentControl */
    protected CommentControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Comment Transfer Caches
    // --------------------------------------------------------------------------------
    
    private CommentTransferCaches commentTransferCaches;
    
    public CommentTransferCaches getCommentTransferCaches(UserVisit userVisit) {
        if(commentTransferCaches == null) {
            commentTransferCaches = new CommentTransferCaches(userVisit, this);
        }
        
        return commentTransferCaches;
    }
    
    // --------------------------------------------------------------------------------
    //   Comment Types
    // --------------------------------------------------------------------------------
    
    public CommentType createCommentType(EntityType entityType, String commentTypeName, Sequence commentSequence,
            WorkflowEntrance workflowEntrance, MimeTypeUsageType mimeTypeUsageType, Integer sortOrder, BasePK createdBy) {
        var commentType = CommentTypeFactory.getInstance().create();
        var commentTypeDetail = CommentTypeDetailFactory.getInstance().create(commentType, entityType,
                commentTypeName, commentSequence, workflowEntrance, mimeTypeUsageType, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        // Convert to R/W
        commentType = CommentTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                commentType.getPrimaryKey());
        commentType.setActiveDetail(commentTypeDetail);
        commentType.setLastDetail(commentTypeDetail);
        commentType.store();
        
        sendEvent(commentType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return commentType;
    }
    
    private List<CommentType> getCommentTypes(EntityType entityType, EntityPermission entityPermission) {
        List<CommentType> commentTypes;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM commenttypes, commenttypedetails " +
                        "WHERE cmnttyp_activedetailid = cmnttypdt_commenttypedetailid AND cmnttypdt_ent_entitytypeid = ? " +
                        "ORDER BY cmnttypdt_sortorder, cmnttypdt_commenttypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM commenttypes, commenttypedetails " +
                        "WHERE cmnttyp_activedetailid = cmnttypdt_commenttypedetailid AND cmnttypdt_ent_entitytypeid = ? " +
                        "FOR UPDATE";
            }

            var ps = CommentTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityType.getPrimaryKey().getEntityId());
            
            commentTypes = CommentTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return commentTypes;
    }
    
    public List<CommentType> getCommentTypes(EntityType entityType) {
        return getCommentTypes(entityType, EntityPermission.READ_ONLY);
    }
    
    public List<CommentType> getCommentTypesForUpdate(EntityType entityType) {
        return getCommentTypes(entityType, EntityPermission.READ_WRITE);
    }
    
    private CommentType getCommentTypeByName(EntityType entityType, String commentTypeName, EntityPermission entityPermission) {
        CommentType commentType;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM commenttypes, commenttypedetails " +
                        "WHERE cmnttyp_activedetailid = cmnttypdt_commenttypedetailid AND cmnttypdt_ent_entitytypeid = ? " +
                        "AND cmnttypdt_commenttypename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM commenttypes, commenttypedetails " +
                        "WHERE cmnttyp_activedetailid = cmnttypdt_commenttypedetailid AND cmnttypdt_ent_entitytypeid = ? " +
                        "AND cmnttypdt_commenttypename = ? " +
                        "FOR UPDATE";
            }

            var ps = CommentTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityType.getPrimaryKey().getEntityId());
            ps.setString(2, commentTypeName);
            
            commentType = CommentTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return commentType;
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
        return getCommentTransferCaches(userVisit).getCommentTypeTransferCache().getCommentTypeTransfer(commentType);
    }
    
    public List<CommentTypeTransfer> getCommentTypeTransfers(UserVisit userVisit, EntityType entityType) {
        var commentTypes = getCommentTypes(entityType);
        List<CommentTypeTransfer> commentTypeTransfers = new ArrayList<>(commentTypes.size());
        var commentTypeTransferCache = getCommentTransferCaches(userVisit).getCommentTypeTransferCache();
        
        commentTypes.forEach((commentType) ->
                commentTypeTransfers.add(commentTypeTransferCache.getCommentTypeTransfer(commentType))
        );
        
        return commentTypeTransfers;
    }
    
    public void updateCommentTypeFromValue(CommentTypeDetailValue commentTypeDetailValue, BasePK updatedBy) {
        if(commentTypeDetailValue.hasBeenModified()) {
            var commentType = CommentTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     commentTypeDetailValue.getCommentTypePK());
            var commentTypeDetail = commentType.getActiveDetailForUpdate();
            
            commentTypeDetail.setThruTime(session.START_TIME_LONG);
            commentTypeDetail.store();

            var commentTypePK = commentTypeDetail.getCommentTypePK(); // Not updated
            var entityTypePK = commentTypeDetail.getEntityTypePK(); // Not updated
            var commentTypeName = commentTypeDetailValue.getCommentTypeName();
            var commentSequencePK = commentTypeDetailValue.getCommentSequencePK();
            var workflowEntrancePK = commentTypeDetailValue.getWorkflowEntrancePK();
            var mimeTypeUsageTypePK = commentTypeDetail.getMimeTypeUsageTypePK(); // Not updated
            var sortOrder = commentTypeDetailValue.getSortOrder();
            
            commentTypeDetail = CommentTypeDetailFactory.getInstance().create(commentTypePK, entityTypePK, commentTypeName,
                    commentSequencePK, workflowEntrancePK, mimeTypeUsageTypePK, sortOrder, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
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
        commentTypeDetail.setThruTime(session.START_TIME_LONG);
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
    
    public CommentTypeDescription createCommentTypeDescription(CommentType commentType, Language language, String description,
            BasePK createdBy) {
        var commentTypeDescription = CommentTypeDescriptionFactory.getInstance().create(commentType,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(commentType.getPrimaryKey(), EventTypes.MODIFY, commentTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return commentTypeDescription;
    }
    
    private CommentTypeDescription getCommentTypeDescription(CommentType commentType, Language language, EntityPermission entityPermission) {
        CommentTypeDescription commentTypeDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM commenttypedescriptions " +
                        "WHERE cmnttypd_cmnttyp_commenttypeid = ? AND cmnttypd_lang_languageid = ? AND cmnttypd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM commenttypedescriptions " +
                        "WHERE cmnttypd_cmnttyp_commenttypeid = ? AND cmnttypd_lang_languageid = ? AND cmnttypd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = CommentTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, commentType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            commentTypeDescription = CommentTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return commentTypeDescription;
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
        List<CommentTypeDescription> commentTypeDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM commenttypedescriptions, languages " +
                        "WHERE cmnttypd_cmnttyp_commenttypeid = ? AND cmnttypd_thrutime = ? AND cmnttypd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM commenttypedescriptions " +
                        "WHERE cmnttypd_cmnttyp_commenttypeid = ? AND cmnttypd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = CommentTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, commentType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            commentTypeDescriptions = CommentTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return commentTypeDescriptions;
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
            commentTypeDescription = getCommentTypeDescription(commentType, getPartyControl().getDefaultLanguage());
        }
        
        if(commentTypeDescription == null) {
            description = commentType.getLastDetail().getCommentTypeName();
        } else {
            description = commentTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public CommentTypeDescriptionTransfer getCommentTypeDescriptionTransfer(UserVisit userVisit, CommentTypeDescription commentTypeDescription) {
        return getCommentTransferCaches(userVisit).getCommentTypeDescriptionTransferCache().getCommentTypeDescriptionTransfer(commentTypeDescription);
    }
    
    public List<CommentTypeDescriptionTransfer> getCommentTypeDescriptionTransfers(UserVisit userVisit, CommentType commentType) {
        var commentTypeDescriptions = getCommentTypeDescriptionsByCommentType(commentType);
        List<CommentTypeDescriptionTransfer> commentTypeDescriptionTransfers = new ArrayList<>(commentTypeDescriptions.size());
        var commentTypeDescriptionTransferCache = getCommentTransferCaches(userVisit).getCommentTypeDescriptionTransferCache();
        
        commentTypeDescriptions.forEach((commentTypeDescription) ->
                commentTypeDescriptionTransfers.add(commentTypeDescriptionTransferCache.getCommentTypeDescriptionTransfer(commentTypeDescription))
        );
        
        return commentTypeDescriptionTransfers;
    }
    
    public void updateCommentTypeDescriptionFromValue(CommentTypeDescriptionValue commentTypeDescriptionValue, BasePK updatedBy) {
        if(commentTypeDescriptionValue.hasBeenModified()) {
            var commentTypeDescription = CommentTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, commentTypeDescriptionValue.getPrimaryKey());
            
            commentTypeDescription.setThruTime(session.START_TIME_LONG);
            commentTypeDescription.store();

            var commentType = commentTypeDescription.getCommentType();
            var language = commentTypeDescription.getLanguage();
            var description = commentTypeDescriptionValue.getDescription();
            
            commentTypeDescription = CommentTypeDescriptionFactory.getInstance().create(commentType, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(commentType.getPrimaryKey(), EventTypes.MODIFY, commentTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteCommentTypeDescription(CommentTypeDescription commentTypeDescription, BasePK deletedBy) {
        commentTypeDescription.setThruTime(session.START_TIME_LONG);
        
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
    
    public CommentUsageType createCommentUsageType(CommentType commentType, String commentUsageTypeName, Boolean selectedByDefault,
            Integer sortOrder, BasePK createdBy) {
        var commentUsageType = CommentUsageTypeFactory.getInstance().create();
        var commentUsageTypeDetail = CommentUsageTypeDetailFactory.getInstance().create(session,
                commentUsageType, commentType, commentUsageTypeName, selectedByDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        // Convert to R/W
        commentUsageType = CommentUsageTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                commentUsageType.getPrimaryKey());
        commentUsageType.setActiveDetail(commentUsageTypeDetail);
        commentUsageType.setLastDetail(commentUsageTypeDetail);
        commentUsageType.store();
        
        sendEvent(commentUsageType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return commentUsageType;
    }
    
    private List<CommentUsageType> getCommentUsageTypes(CommentType commentType, EntityPermission entityPermission) {
        List<CommentUsageType> commentUsageTypes;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM commentusagetypes, commentusagetypedetails " +
                        "WHERE cmntutyp_activedetailid = cmntutypdt_commentusagetypedetailid " +
                        "AND cmntutypdt_cmnttyp_commenttypeid = ? " +
                        "ORDER BY cmntutypdt_sortorder, cmntutypdt_commentusagetypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM commentusagetypes, commentusagetypedetails " +
                        "WHERE cmntutyp_activedetailid = cmntutypdt_commentusagetypedetailid " +
                        "AND cmntutypdt_cmnttyp_commenttypeid = ? " +
                        "FOR UPDATE";
            }

            var ps = CommentUsageTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, commentType.getPrimaryKey().getEntityId());
            
            commentUsageTypes = CommentUsageTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return commentUsageTypes;
    }
    
    public List<CommentUsageType> getCommentUsageTypes(CommentType commentType) {
        return getCommentUsageTypes(commentType, EntityPermission.READ_ONLY);
    }
    
    public List<CommentUsageType> getCommentUsageTypesForUpdate(CommentType commentType) {
        return getCommentUsageTypes(commentType, EntityPermission.READ_WRITE);
    }
    
    private CommentUsageType getCommentUsageTypeByName(CommentType commentType, String commentUsageTypeName,
            EntityPermission entityPermission) {
        CommentUsageType commentUsageType;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM commentusagetypes, commentusagetypedetails " +
                        "WHERE cmntutyp_activedetailid = cmntutypdt_commentusagetypedetailid " +
                        "AND cmntutypdt_cmnttyp_commenttypeid = ? AND cmntutypdt_commentusagetypename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM commentusagetypes, commentusagetypedetails " +
                        "WHERE cmntutyp_activedetailid = cmntutypdt_commentusagetypedetailid " +
                        "AND cmntutypdt_cmnttyp_commenttypeid = ? AND cmntutypdt_commentusagetypename = ? " +
                        "FOR UPDATE";
            }

            var ps = CommentUsageTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, commentType.getPrimaryKey().getEntityId());
            ps.setString(2, commentUsageTypeName);
            
            commentUsageType = CommentUsageTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return commentUsageType;
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
        return getCommentTransferCaches(userVisit).getCommentUsageTypeTransferCache().getCommentUsageTypeTransfer(commentUsageType);
    }
    
    public List<CommentUsageTypeTransfer> getCommentUsageTypeTransfers(UserVisit userVisit, CommentType commentType) {
        var commentUsageTypes = getCommentUsageTypes(commentType);
        List<CommentUsageTypeTransfer> commentUsageTypeTransfers = new ArrayList<>(commentUsageTypes.size());
        var commentUsageTypeTransferCache = getCommentTransferCaches(userVisit).getCommentUsageTypeTransferCache();
        
        commentUsageTypes.forEach((commentUsageType) ->
                commentUsageTypeTransfers.add(commentUsageTypeTransferCache.getCommentUsageTypeTransfer(commentUsageType))
        );
        
        return commentUsageTypeTransfers;
    }
    
    public void updateCommentUsageTypeFromValue(CommentUsageTypeDetailValue commentUsageTypeDetailValue, BasePK updatedBy) {
        if(commentUsageTypeDetailValue.hasBeenModified()) {
            var commentUsageType = CommentUsageTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     commentUsageTypeDetailValue.getCommentUsageTypePK());
            var commentUsageTypeDetail = commentUsageType.getActiveDetailForUpdate();
            
            commentUsageTypeDetail.setThruTime(session.START_TIME_LONG);
            commentUsageTypeDetail.store();

            var commentUsageTypePK = commentUsageTypeDetail.getCommentUsageTypePK(); // Not updated
            var commentTypePK = commentUsageTypeDetail.getCommentTypePK(); // Not updated
            var commentUsageTypeName = commentUsageTypeDetailValue.getCommentUsageTypeName();
            var selectedByDefault = commentUsageTypeDetailValue.getSelectedByDefault();
            var sortOrder = commentUsageTypeDetailValue.getSortOrder();
            
            commentUsageTypeDetail = CommentUsageTypeDetailFactory.getInstance().create(commentUsageTypePK, commentTypePK,
                    commentUsageTypeName, selectedByDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            commentUsageType.setActiveDetail(commentUsageTypeDetail);
            commentUsageType.setLastDetail(commentUsageTypeDetail);
            
            sendEvent(commentUsageTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deleteCommentUsageType(CommentUsageType commentUsageType, BasePK deletedBy) {
        deleteCommentUsageTypeDescriptionsByCommentUsageType(commentUsageType, deletedBy);
        deleteCommentUsagesByCommentUsageType(commentUsageType, deletedBy);

        var commentUsageTypeDetail = commentUsageType.getLastDetailForUpdate();
        commentUsageTypeDetail.setThruTime(session.START_TIME_LONG);
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
    
    public CommentUsageTypeDescription createCommentUsageTypeDescription(CommentUsageType commentUsageType, Language language,
            String description, BasePK createdBy) {
        var commentUsageTypeDescription = CommentUsageTypeDescriptionFactory.getInstance().create(session,
                commentUsageType, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(commentUsageType.getPrimaryKey(), EventTypes.MODIFY, commentUsageTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return commentUsageTypeDescription;
    }
    
    private CommentUsageTypeDescription getCommentUsageTypeDescription(CommentUsageType commentUsageType, Language language,
            EntityPermission entityPermission) {
        CommentUsageTypeDescription commentUsageTypeDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM commentusagetypedescriptions " +
                        "WHERE cmntutypd_cmntutyp_commentusagetypeid = ? AND cmntutypd_lang_languageid = ? AND cmntutypd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM commentusagetypedescriptions " +
                        "WHERE cmntutypd_cmntutyp_commentusagetypeid = ? AND cmntutypd_lang_languageid = ? AND cmntutypd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = CommentUsageTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, commentUsageType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            commentUsageTypeDescription = CommentUsageTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return commentUsageTypeDescription;
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
        List<CommentUsageTypeDescription> commentUsageTypeDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM commentusagetypedescriptions, languages " +
                        "WHERE cmntutypd_cmntutyp_commentusagetypeid = ? AND cmntutypd_thrutime = ? AND cmntutypd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM commentusagetypedescriptions " +
                        "WHERE cmntutypd_cmntutyp_commentusagetypeid = ? AND cmntutypd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = CommentUsageTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, commentUsageType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            commentUsageTypeDescriptions = CommentUsageTypeDescriptionFactory.getInstance().getEntitiesFromQuery(session,
                    entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return commentUsageTypeDescriptions;
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
            commentUsageTypeDescription = getCommentUsageTypeDescription(commentUsageType, getPartyControl().getDefaultLanguage());
        }
        
        if(commentUsageTypeDescription == null) {
            description = commentUsageType.getLastDetail().getCommentUsageTypeName();
        } else {
            description = commentUsageTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public CommentUsageTypeDescriptionTransfer getCommentUsageTypeDescriptionTransfer(UserVisit userVisit, CommentUsageTypeDescription commentUsageTypeDescription) {
        return getCommentTransferCaches(userVisit).getCommentUsageTypeDescriptionTransferCache().getCommentUsageTypeDescriptionTransfer(commentUsageTypeDescription);
    }
    
    public List<CommentUsageTypeDescriptionTransfer> getCommentUsageTypeDescriptionTransfers(UserVisit userVisit, CommentUsageType commentUsageType) {
        var commentUsageTypeDescriptions = getCommentUsageTypeDescriptionsByCommentUsageType(commentUsageType);
        List<CommentUsageTypeDescriptionTransfer> commentUsageTypeDescriptionTransfers = new ArrayList<>(commentUsageTypeDescriptions.size());
        var commentUsageTypeDescriptionTransferCache = getCommentTransferCaches(userVisit).getCommentUsageTypeDescriptionTransferCache();
        
        commentUsageTypeDescriptions.forEach((commentUsageTypeDescription) ->
                commentUsageTypeDescriptionTransfers.add(commentUsageTypeDescriptionTransferCache.getCommentUsageTypeDescriptionTransfer(commentUsageTypeDescription))
        );
        
        return commentUsageTypeDescriptionTransfers;
    }
    
    public void updateCommentUsageTypeDescriptionFromValue(CommentUsageTypeDescriptionValue commentUsageTypeDescriptionValue, BasePK updatedBy) {
        if(commentUsageTypeDescriptionValue.hasBeenModified()) {
            var commentUsageTypeDescription = CommentUsageTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     commentUsageTypeDescriptionValue.getPrimaryKey());
            
            commentUsageTypeDescription.setThruTime(session.START_TIME_LONG);
            commentUsageTypeDescription.store();

            var commentUsageType = commentUsageTypeDescription.getCommentUsageType();
            var language = commentUsageTypeDescription.getLanguage();
            var description = commentUsageTypeDescriptionValue.getDescription();
            
            commentUsageTypeDescription = CommentUsageTypeDescriptionFactory.getInstance().create(commentUsageType,
                    language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(commentUsageType.getPrimaryKey(), EventTypes.MODIFY, commentUsageTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteCommentUsageTypeDescription(CommentUsageTypeDescription commentUsageTypeDescription, BasePK deletedBy) {
        commentUsageTypeDescription.setThruTime(session.START_TIME_LONG);
        
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
    
    public Comment createComment(String commentName, CommentType commentType, EntityInstance commentedEntityInstance,
            EntityInstance commentedByEntityInstance, Language language, String description, MimeType mimeType, BasePK createdBy) {
        var comment = CommentFactory.getInstance().create();
        var commentDetail = CommentDetailFactory.getInstance().create(comment, commentName, commentType,
                commentedEntityInstance, commentedByEntityInstance, language, description, mimeType, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        // Convert to R/W
        comment = CommentFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, comment.getPrimaryKey());
        comment.setActiveDetail(commentDetail);
        comment.setLastDetail(commentDetail);
        comment.store();
        
        sendEvent(comment.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        sendEvent(commentedEntityInstance, EventTypes.TOUCH, comment.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return comment;
    }
    
    private Comment getCommentByName(String commentName, EntityPermission entityPermission) {
        Comment comment;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM comments, commentdetails " +
                        "WHERE cmnt_activedetailid = cmntdt_commentdetailid AND cmntdt_commentname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM comments, commentdetails " +
                        "WHERE cmnt_activedetailid = cmntdt_commentdetailid AND cmntdt_commentname = ? " +
                        "FOR UPDATE";
            }

            var ps = CommentFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, commentName);
            
            comment = CommentFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return comment;
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
        List<Comment> comments;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM comments, commentdetails " +
                        "WHERE cmnt_activedetailid = cmntdt_commentdetailid AND cmntdt_commentedentityinstanceid = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM comments, commentdetails " +
                        "WHERE cmnt_activedetailid = cmntdt_commentdetailid AND cmntdt_commentedentityinstanceid = ? " +
                        "FOR UPDATE";
            }

            var ps = CommentFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, commentedEntityInstance.getPrimaryKey().getEntityId());
            
            comments = CommentFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return comments;
    }
    
    public List<Comment> getCommentsByCommentedEntityInstance(EntityInstance commentedEntityInstance) {
        return getCommentsByCommentedEntityInstance(commentedEntityInstance, EntityPermission.READ_ONLY);
    }
    
    public List<Comment> getCommentsByCommentedEntityInstanceForUpdate(EntityInstance commentedEntityInstance) {
        return getCommentsByCommentedEntityInstance(commentedEntityInstance, EntityPermission.READ_WRITE);
    }
    
    private List<Comment> getCommentsByCommentedByEntityInstance(EntityInstance commentedByEntityInstance, EntityPermission entityPermission) {
        List<Comment> comments;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM comments, commentdetails " +
                        "WHERE cmnt_activedetailid = cmntdt_commentdetailid AND cmntdt_commentedbyentityinstanceid = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM comments, commentdetails " +
                        "WHERE cmnt_activedetailid = cmntdt_commentdetailid AND cmntdt_commentedbyentityinstanceid = ? " +
                        "FOR UPDATE";
            }

            var ps = CommentFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, commentedByEntityInstance.getPrimaryKey().getEntityId());
            
            comments = CommentFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return comments;
    }
    
    public List<Comment> getCommentsByCommentedByEntityInstance(EntityInstance commentedByEntityInstance) {
        return getCommentsByCommentedByEntityInstance(commentedByEntityInstance, EntityPermission.READ_ONLY);
    }
    
    public List<Comment> getCommentsByCommentedByEntityInstanceForUpdate(EntityInstance commentedByEntityInstance) {
        return getCommentsByCommentedByEntityInstance(commentedByEntityInstance, EntityPermission.READ_WRITE);
    }
    
    private List<Comment> getCommentsByCommentType(CommentType commentType, EntityPermission entityPermission) {
        List<Comment> comments;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM comments, commentdetails " +
                        "WHERE cmnt_activedetailid = cmntdt_commentdetailid AND cmntdt_cmnttyp_commenttypeid = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM comments, commentdetails " +
                        "WHERE cmnt_activedetailid = cmntdt_commentdetailid AND cmntdt_cmnttyp_commenttypeid = ? " +
                        "FOR UPDATE";
            }

            var ps = CommentFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, commentType.getPrimaryKey().getEntityId());
            
            comments = CommentFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return comments;
    }
    
    public List<Comment> getCommentsByCommentType(CommentType commentType) {
        return getCommentsByCommentType(commentType, EntityPermission.READ_ONLY);
    }
    
    public List<Comment> getCommentsByCommentTypeForUpdate(CommentType commentType) {
        return getCommentsByCommentType(commentType, EntityPermission.READ_WRITE);
    }
    
    public List<Comment> getCommentsByCommentedEntityInstanceAndCommentType(EntityInstance commentedEntityInstance,
            CommentType commentType, EntityPermission entityPermission) {
        List<Comment> comments;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM comments, commentdetails " +
                        "WHERE cmnt_activedetailid = cmntdt_commentdetailid " +
                        "AND cmntdt_commentedentityinstanceid = ? AND cmntdt_cmnttyp_commenttypeid = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM comments, commentdetails " +
                        "WHERE cmnt_activedetailid = cmntdt_commentdetailid " +
                        "AND cmntdt_commentedentityinstanceid = ? AND cmntdt_cmnttyp_commenttypeid = ? " +
                        "FOR UPDATE";
            }

            var ps = CommentFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, commentedEntityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, commentType.getPrimaryKey().getEntityId());
            
            comments = CommentFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return comments;
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
        return getCommentTransferCaches(userVisit).getCommentTransferCache().getCommentTransfer(comment);
    }
    
    public List<CommentTransfer> getCommentTransfers(UserVisit userVisit, Collection<Comment> comments) {
        List<CommentTransfer> commentTransfers = new ArrayList<>(comments.size());
        var commentTransferCache = getCommentTransferCaches(userVisit).getCommentTransferCache();
        
        comments.forEach((comment) ->
                commentTransfers.add(commentTransferCache.getCommentTransfer(comment))
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
            var comment = CommentFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     commentDetailValue.getCommentPK());
            var commentDetail = comment.getActiveDetailForUpdate();
            
            commentDetail.setThruTime(session.START_TIME_LONG);
            commentDetail.store();

            var commentPK = commentDetail.getCommentPK(); // Not updated
            var commentName = commentDetailValue.getCommentName();
            var commentTypePK = commentDetail.getCommentTypePK(); // Not updated
            var commentedEntityInstancePK = commentDetail.getCommentedEntityInstancePK(); // Not updated
            var commentedByEntityInstancePK = commentDetail.getCommentedByEntityInstancePK(); // Not updated
            var languagePK = commentDetailValue.getLanguagePK();
            var description = commentDetailValue.getDescription();
            var mimeTypePK = commentDetailValue.getMimeTypePK();
            
            commentDetail = CommentDetailFactory.getInstance().create(commentPK, commentName, commentTypePK,
                    commentedEntityInstancePK, commentedByEntityInstancePK, languagePK, description, mimeTypePK,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
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
            var workflowControl = getWorkflowControl();
            var workflow = workflowEntrance.getLastDetail().getWorkflow();
        
            commentStatusChoicesBean = new CommentStatusChoicesBean();
            
            if(comment == null) {
                workflowControl.getWorkflowEntranceChoices(commentStatusChoicesBean, defaultCommentStatusChoice, language, allowNullChoice, workflow, partyPK);
            } else {
                var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
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
            var workflowControl = getWorkflowControl();
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
        commentDetail.setThruTime(session.START_TIME_LONG);
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
    
    public CommentString createCommentString(Comment comment, String string, BasePK createdBy) {
        var commentString = CommentStringFactory.getInstance().create(comment, string, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(comment.getPrimaryKey(), EventTypes.MODIFY, commentString.getPrimaryKey(), EventTypes.CREATE, createdBy);
        sendEvent(comment.getLastDetail().getCommentedEntityInstance(), EventTypes.TOUCH, comment.getPrimaryKey(), EventTypes.MODIFY, createdBy);
        
        return commentString;
    }
    
    private CommentString getCommentString(Comment comment, EntityPermission entityPermission) {
        CommentString commentString;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM commentstrings " +
                        "WHERE cmnts_cmnt_commentid = ? AND cmnts_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM commentstrings " +
                        "WHERE cmnts_cmnt_commentid = ? AND cmnts_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = CommentStringFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, comment.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            commentString = CommentStringFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return commentString;
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
            var commentString = CommentStringFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, commentStringValue.getPrimaryKey());
            
            commentString.setThruTime(session.START_TIME_LONG);
            commentString.store();

            var commentPK = commentString.getCommentPK(); // Not updated
            var string = commentStringValue.getString();
            
            commentString = CommentStringFactory.getInstance().create(commentPK, string, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(commentPK, EventTypes.MODIFY, commentString.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
            sendEvent(commentString.getComment().getLastDetail().getCommentedEntityInstance(), EventTypes.TOUCH, commentString.getCommentPK(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteCommentString(CommentString commentString, BasePK deletedBy) {
        commentString.setThruTime(session.START_TIME_LONG);
        
        sendEvent(commentString.getCommentPK(), EventTypes.MODIFY, commentString.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        sendEvent(commentString.getComment().getLastDetail().getCommentedEntityInstance(), EventTypes.TOUCH, commentString.getCommentPK(), EventTypes.MODIFY, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Comment Blobs
    // --------------------------------------------------------------------------------
    
    public CommentBlob createCommentBlob(Comment comment, ByteArray blob, BasePK createdBy) {
        var commentBlob = CommentBlobFactory.getInstance().create(comment, blob, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(comment.getPrimaryKey(), EventTypes.MODIFY, commentBlob.getPrimaryKey(), EventTypes.CREATE, createdBy);
        sendEvent(comment.getLastDetail().getCommentedEntityInstance(), EventTypes.TOUCH, comment.getPrimaryKey(), EventTypes.MODIFY, createdBy);
        
        return commentBlob;
    }
    
    private CommentBlob getCommentBlob(Comment comment, EntityPermission entityPermission) {
        CommentBlob commentBlob;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM commentblobs " +
                        "WHERE cmntb_cmnt_commentid = ? AND cmntb_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM commentblobs " +
                        "WHERE cmntb_cmnt_commentid = ? AND cmntb_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = CommentBlobFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, comment.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            commentBlob = CommentBlobFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return commentBlob;
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
            var commentBlob = CommentBlobFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, commentBlobValue.getPrimaryKey());
            
            commentBlob.setThruTime(session.START_TIME_LONG);
            commentBlob.store();

            var commentPK = commentBlob.getCommentPK(); // Not updated
            var blob = commentBlobValue.getBlob();
            
            commentBlob = CommentBlobFactory.getInstance().create(commentPK, blob, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(commentPK, EventTypes.MODIFY, commentBlob.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
            sendEvent(commentBlob.getComment().getLastDetail().getCommentedEntityInstance(), EventTypes.TOUCH, commentBlob.getCommentPK(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteCommentBlob(CommentBlob commentBlob, BasePK deletedBy) {
        commentBlob.setThruTime(session.START_TIME_LONG);
        
        sendEvent(commentBlob.getCommentPK(), EventTypes.MODIFY, commentBlob.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        sendEvent(commentBlob.getComment().getLastDetail().getCommentedEntityInstance(), EventTypes.TOUCH, commentBlob.getCommentPK(), EventTypes.MODIFY, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Comment Clobs
    // --------------------------------------------------------------------------------
    
    public CommentClob createCommentClob(Comment comment, String clob, BasePK createdBy) {
        var commentClob = CommentClobFactory.getInstance().create(comment, clob, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEvent(comment.getPrimaryKey(), EventTypes.MODIFY, commentClob.getPrimaryKey(), EventTypes.CREATE, createdBy);
        sendEvent(comment.getLastDetail().getCommentedEntityInstance(), EventTypes.TOUCH, comment.getPrimaryKey(), EventTypes.MODIFY, createdBy);
        
        return commentClob;
    }
    
    private CommentClob getCommentClob(Comment comment, EntityPermission entityPermission) {
        CommentClob commentClob;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM commentclobs " +
                        "WHERE cmntc_cmnt_commentid = ? AND cmntc_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM commentclobs " +
                        "WHERE cmntc_cmnt_commentid = ? AND cmntc_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = CommentClobFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, comment.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            commentClob = CommentClobFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return commentClob;
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
            var commentClob = CommentClobFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, commentClobValue.getPrimaryKey());

            commentClob.setThruTime(session.START_TIME_LONG);
            commentClob.store();

            var commentPK = commentClob.getCommentPK(); // Not updated
            var clob = commentClobValue.getClob();

            commentClob = CommentClobFactory.getInstance().create(commentPK, clob, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(commentPK, EventTypes.MODIFY, commentClob.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
            sendEvent(commentClob.getComment().getLastDetail().getCommentedEntityInstance(), EventTypes.TOUCH, commentClob.getCommentPK(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteCommentClob(CommentClob commentClob, BasePK deletedBy) {
        commentClob.setThruTime(session.START_TIME_LONG);
        
        sendEvent(commentClob.getCommentPK(), EventTypes.MODIFY, commentClob.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        sendEvent(commentClob.getComment().getLastDetail().getCommentedEntityInstance(), EventTypes.TOUCH, commentClob.getCommentPK(), EventTypes.MODIFY, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Comment Usages
    // --------------------------------------------------------------------------------
    
    public CommentUsage createCommentUsage(Comment comment, CommentUsageType commentUsageType, BasePK createdBy) {
        var commentUsage = CommentUsageFactory.getInstance().create(comment, commentUsageType,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(comment.getPrimaryKey(), EventTypes.MODIFY, commentUsage.getPrimaryKey(), EventTypes.CREATE, createdBy);
        sendEvent(comment.getLastDetail().getCommentedEntityInstance(), EventTypes.TOUCH, comment.getPrimaryKey(), EventTypes.MODIFY, createdBy);
        
        return commentUsage;
    }
    
    private CommentUsage getCommentUsage(Comment comment, CommentUsageType commentUsageType, EntityPermission entityPermission) {
        CommentUsage commentUsage;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM commentusages " +
                        "WHERE cmntu_cmnt_commentid = ? AND cmntu_cmntutyp_commentusagetypeid = ? AND cmntu_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM commentusages " +
                        "WHERE cmntu_cmnt_commentid = ? AND cmntu_cmntutyp_commentusagetypeid = ? AND cmntu_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = CommentUsageFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, comment.getPrimaryKey().getEntityId());
            ps.setLong(2, commentUsageType.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            commentUsage = CommentUsageFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return commentUsage;
    }
    
    public CommentUsage getCommentUsage(Comment comment, CommentUsageType commentUsageType) {
        return getCommentUsage(comment, commentUsageType, EntityPermission.READ_ONLY);
    }
    
    public CommentUsage getCommentUsageForUpdate(Comment comment, CommentUsageType commentUsageType) {
        return getCommentUsage(comment, commentUsageType, EntityPermission.READ_WRITE);
    }
    
    private List<CommentUsage> getCommentUsagesByComment(Comment comment, EntityPermission entityPermission) {
        List<CommentUsage> comments;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM commentusages, commentusagetypes, commentusagetypedetails " +
                        "WHERE cmntu_cmnt_commentid = ? AND cmntu_thrutime = ? " +
                        "AND cmntu_cmntutyp_commentusagetypeid = cmntutyp_commentusagetypeid " +
                        "AND cmntutyp_lastdetailid = cmntutypdt_commentusagetypedetailid " +
                        "ORDER BY cmntutypdt_sortorder, cmntutypdt_commentusagetypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM commentusages " +
                        "WHERE cmntu_cmnt_commentid = ? AND cmntu_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = CommentUsageFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, comment.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            comments = CommentUsageFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return comments;
    }
    
    public List<CommentUsage> getCommentUsagesByComment(Comment comment) {
        return getCommentUsagesByComment(comment, EntityPermission.READ_ONLY);
    }
    
    public List<CommentUsage> getCommentUsagesByCommentForUpdate(Comment comment) {
        return getCommentUsagesByComment(comment, EntityPermission.READ_WRITE);
    }
    
    private List<CommentUsage> getCommentUsagesByCommentUsageType(CommentUsageType commentUsageType, EntityPermission entityPermission) {
        List<CommentUsage> comments;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM commentusages, comments, commentdetails " +
                        "WHERE cmntu_cmntutyp_commentusagetypeid = ? AND cmntu_thrutime = ? " +
                        "AND cmntu_cmnt_commentid = cmnt_commentid " +
                        "AND cmnt_lastdetailid = cmntdt_commentdetailid " +
                        "ORDER BY cmntdt_commentname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM commentusages " +
                        "WHERE cmntu_cmntutyp_commentusagetypeid = ? AND cmntu_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = CommentUsageFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, commentUsageType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            comments = CommentUsageFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return comments;
    }
    
    public List<CommentUsage> getCommentUsagesByCommentUsageType(CommentUsageType commentUsageType) {
        return getCommentUsagesByCommentUsageType(commentUsageType, EntityPermission.READ_ONLY);
    }
    
    public List<CommentUsage> getCommentUsagesByCommentUsageTypeForUpdate(CommentUsageType commentUsageType) {
        return getCommentUsagesByCommentUsageType(commentUsageType, EntityPermission.READ_WRITE);
    }
    
    public CommentUsageTransfer getCommentUsageTransfer(UserVisit userVisit, CommentUsage commentUsage) {
        return getCommentTransferCaches(userVisit).getCommentUsageTransferCache().getCommentUsageTransfer(commentUsage);
    }
    
    public List<CommentUsageTransfer> getCommentUsageTransfersByComment(UserVisit userVisit, Comment comment) {
        var entities = getCommentUsagesByComment(comment);
        List<CommentUsageTransfer> transfers = new ArrayList<>(entities.size());
        var cache = getCommentTransferCaches(userVisit).getCommentUsageTransferCache();
        
        entities.forEach((entity) ->
                transfers.add(cache.getCommentUsageTransfer(entity))
        );
        
        return transfers;
    }
    
    public void deleteCommentUsage(CommentUsage commentUsage, BasePK deletedBy) {
        commentUsage.setThruTime(session.START_TIME_LONG);
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
