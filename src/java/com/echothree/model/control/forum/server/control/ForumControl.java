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

package com.echothree.model.control.forum.server.control;

import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.common.choice.MimeTypeChoicesBean;
import com.echothree.model.control.core.server.control.MimeTypeControl;
import com.echothree.model.control.forum.common.choice.ForumChoicesBean;
import com.echothree.model.control.forum.common.choice.ForumGroupChoicesBean;
import com.echothree.model.control.forum.common.choice.ForumMessageTypeChoicesBean;
import com.echothree.model.control.forum.common.choice.ForumRoleTypeChoicesBean;
import com.echothree.model.control.forum.common.choice.ForumTypeChoicesBean;
import com.echothree.model.control.forum.common.transfer.ForumDescriptionTransfer;
import com.echothree.model.control.forum.common.transfer.ForumForumThreadTransfer;
import com.echothree.model.control.forum.common.transfer.ForumGroupDescriptionTransfer;
import com.echothree.model.control.forum.common.transfer.ForumGroupForumTransfer;
import com.echothree.model.control.forum.common.transfer.ForumGroupTransfer;
import com.echothree.model.control.forum.common.transfer.ForumMessageAttachmentDescriptionTransfer;
import com.echothree.model.control.forum.common.transfer.ForumMessageAttachmentTransfer;
import com.echothree.model.control.forum.common.transfer.ForumMessagePartTransfer;
import com.echothree.model.control.forum.common.transfer.ForumMessagePartTypeTransfer;
import com.echothree.model.control.forum.common.transfer.ForumMessageRoleTransfer;
import com.echothree.model.control.forum.common.transfer.ForumMessageTransfer;
import com.echothree.model.control.forum.common.transfer.ForumMessageTypePartTypeTransfer;
import com.echothree.model.control.forum.common.transfer.ForumMessageTypeTransfer;
import com.echothree.model.control.forum.common.transfer.ForumMimeTypeTransfer;
import com.echothree.model.control.forum.common.transfer.ForumPartyRoleTransfer;
import com.echothree.model.control.forum.common.transfer.ForumPartyTypeRoleTransfer;
import com.echothree.model.control.forum.common.transfer.ForumRoleTypeTransfer;
import com.echothree.model.control.forum.common.transfer.ForumThreadTransfer;
import com.echothree.model.control.forum.common.transfer.ForumTransfer;
import com.echothree.model.control.forum.common.transfer.ForumTypeTransfer;
import com.echothree.model.control.forum.server.transfer.ForumTransferCaches;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.core.server.entity.MimeTypeUsageType;
import com.echothree.model.data.forum.common.pk.ForumGroupPK;
import com.echothree.model.data.forum.common.pk.ForumMessagePK;
import com.echothree.model.data.forum.common.pk.ForumPK;
import com.echothree.model.data.forum.common.pk.ForumThreadPK;
import com.echothree.model.data.forum.server.entity.Forum;
import com.echothree.model.data.forum.server.entity.ForumBlobMessagePart;
import com.echothree.model.data.forum.server.entity.ForumClobMessagePart;
import com.echothree.model.data.forum.server.entity.ForumDescription;
import com.echothree.model.data.forum.server.entity.ForumForumThread;
import com.echothree.model.data.forum.server.entity.ForumGroup;
import com.echothree.model.data.forum.server.entity.ForumGroupDescription;
import com.echothree.model.data.forum.server.entity.ForumGroupForum;
import com.echothree.model.data.forum.server.entity.ForumMessage;
import com.echothree.model.data.forum.server.entity.ForumMessageAttachment;
import com.echothree.model.data.forum.server.entity.ForumMessageAttachmentDescription;
import com.echothree.model.data.forum.server.entity.ForumMessageBlobAttachment;
import com.echothree.model.data.forum.server.entity.ForumMessageClobAttachment;
import com.echothree.model.data.forum.server.entity.ForumMessagePart;
import com.echothree.model.data.forum.server.entity.ForumMessagePartType;
import com.echothree.model.data.forum.server.entity.ForumMessagePartTypeDescription;
import com.echothree.model.data.forum.server.entity.ForumMessageRole;
import com.echothree.model.data.forum.server.entity.ForumMessageStatus;
import com.echothree.model.data.forum.server.entity.ForumMessageType;
import com.echothree.model.data.forum.server.entity.ForumMessageTypeDescription;
import com.echothree.model.data.forum.server.entity.ForumMessageTypePartType;
import com.echothree.model.data.forum.server.entity.ForumMimeType;
import com.echothree.model.data.forum.server.entity.ForumPartyRole;
import com.echothree.model.data.forum.server.entity.ForumPartyTypeRole;
import com.echothree.model.data.forum.server.entity.ForumRoleType;
import com.echothree.model.data.forum.server.entity.ForumRoleTypeDescription;
import com.echothree.model.data.forum.server.entity.ForumStringMessagePart;
import com.echothree.model.data.forum.server.entity.ForumThread;
import com.echothree.model.data.forum.server.entity.ForumType;
import com.echothree.model.data.forum.server.entity.ForumTypeDescription;
import com.echothree.model.data.forum.server.entity.ForumTypeMessageType;
import com.echothree.model.data.forum.server.factory.ForumBlobMessagePartFactory;
import com.echothree.model.data.forum.server.factory.ForumClobMessagePartFactory;
import com.echothree.model.data.forum.server.factory.ForumDescriptionFactory;
import com.echothree.model.data.forum.server.factory.ForumDetailFactory;
import com.echothree.model.data.forum.server.factory.ForumFactory;
import com.echothree.model.data.forum.server.factory.ForumForumThreadFactory;
import com.echothree.model.data.forum.server.factory.ForumGroupDescriptionFactory;
import com.echothree.model.data.forum.server.factory.ForumGroupDetailFactory;
import com.echothree.model.data.forum.server.factory.ForumGroupFactory;
import com.echothree.model.data.forum.server.factory.ForumGroupForumFactory;
import com.echothree.model.data.forum.server.factory.ForumMessageAttachmentDescriptionFactory;
import com.echothree.model.data.forum.server.factory.ForumMessageAttachmentDetailFactory;
import com.echothree.model.data.forum.server.factory.ForumMessageAttachmentFactory;
import com.echothree.model.data.forum.server.factory.ForumMessageBlobAttachmentFactory;
import com.echothree.model.data.forum.server.factory.ForumMessageClobAttachmentFactory;
import com.echothree.model.data.forum.server.factory.ForumMessageDetailFactory;
import com.echothree.model.data.forum.server.factory.ForumMessageFactory;
import com.echothree.model.data.forum.server.factory.ForumMessagePartDetailFactory;
import com.echothree.model.data.forum.server.factory.ForumMessagePartFactory;
import com.echothree.model.data.forum.server.factory.ForumMessagePartTypeDescriptionFactory;
import com.echothree.model.data.forum.server.factory.ForumMessagePartTypeFactory;
import com.echothree.model.data.forum.server.factory.ForumMessageRoleFactory;
import com.echothree.model.data.forum.server.factory.ForumMessageStatusFactory;
import com.echothree.model.data.forum.server.factory.ForumMessageTypeDescriptionFactory;
import com.echothree.model.data.forum.server.factory.ForumMessageTypeFactory;
import com.echothree.model.data.forum.server.factory.ForumMessageTypePartTypeFactory;
import com.echothree.model.data.forum.server.factory.ForumMimeTypeFactory;
import com.echothree.model.data.forum.server.factory.ForumPartyRoleFactory;
import com.echothree.model.data.forum.server.factory.ForumPartyTypeRoleFactory;
import com.echothree.model.data.forum.server.factory.ForumRoleTypeDescriptionFactory;
import com.echothree.model.data.forum.server.factory.ForumRoleTypeFactory;
import com.echothree.model.data.forum.server.factory.ForumStringMessagePartFactory;
import com.echothree.model.data.forum.server.factory.ForumThreadDetailFactory;
import com.echothree.model.data.forum.server.factory.ForumThreadFactory;
import com.echothree.model.data.forum.server.factory.ForumTypeDescriptionFactory;
import com.echothree.model.data.forum.server.factory.ForumTypeFactory;
import com.echothree.model.data.forum.server.factory.ForumTypeMessageTypeFactory;
import com.echothree.model.data.forum.server.value.ForumBlobMessagePartValue;
import com.echothree.model.data.forum.server.value.ForumClobMessagePartValue;
import com.echothree.model.data.forum.server.value.ForumDescriptionValue;
import com.echothree.model.data.forum.server.value.ForumDetailValue;
import com.echothree.model.data.forum.server.value.ForumForumThreadValue;
import com.echothree.model.data.forum.server.value.ForumGroupDescriptionValue;
import com.echothree.model.data.forum.server.value.ForumGroupDetailValue;
import com.echothree.model.data.forum.server.value.ForumGroupForumValue;
import com.echothree.model.data.forum.server.value.ForumMessageAttachmentDescriptionValue;
import com.echothree.model.data.forum.server.value.ForumMessageAttachmentDetailValue;
import com.echothree.model.data.forum.server.value.ForumMessageBlobAttachmentValue;
import com.echothree.model.data.forum.server.value.ForumMessageClobAttachmentValue;
import com.echothree.model.data.forum.server.value.ForumMessageDetailValue;
import com.echothree.model.data.forum.server.value.ForumMessagePartDetailValue;
import com.echothree.model.data.forum.server.value.ForumMimeTypeValue;
import com.echothree.model.data.forum.server.value.ForumStringMessagePartValue;
import com.echothree.model.data.forum.server.value.ForumThreadDetailValue;
import com.echothree.model.data.icon.server.entity.Icon;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class ForumControl
        extends BaseModelControl {
    
    /** Creates a new instance of ForumControl */
    protected ForumControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Transfer Caches
    // --------------------------------------------------------------------------------
    
    private ForumTransferCaches forumTransferCaches;
    
    public ForumTransferCaches getForumTransferCaches(UserVisit userVisit) {
        if(forumTransferCaches == null) {
            forumTransferCaches = new ForumTransferCaches(userVisit, this);
        }
        
        return forumTransferCaches;
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Groups
    // --------------------------------------------------------------------------------
    
    public ForumGroup createForumGroup(String forumGroupName, Icon icon, Integer sortOrder, BasePK createdBy) {
        var forumGroup = ForumGroupFactory.getInstance().create();
        var forumGroupDetail = ForumGroupDetailFactory.getInstance().create(forumGroup, forumGroupName, icon,
                sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        forumGroup = ForumGroupFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                forumGroup.getPrimaryKey());
        forumGroup.setActiveDetail(forumGroupDetail);
        forumGroup.setLastDetail(forumGroupDetail);
        forumGroup.store();
        
        sendEvent(forumGroup.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return forumGroup;
    }
    
    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ForumGroup */
    public ForumGroup getForumGroupByEntityInstance(EntityInstance entityInstance) {
        var pk = new ForumGroupPK(entityInstance.getEntityUniqueId());
        var forumGroup = ForumGroupFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);

        return forumGroup;
    }

    public long countForumGroups() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM forumgroups, forumgroupdetails " +
                "WHERE frmgrp_activedetailid = frmgrpdt_forumgroupdetailid");
    }

    public List<ForumGroup> getForumGroups() {
        var ps = ForumGroupFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM forumgroups, forumgroupdetails " +
                "WHERE frmgrp_activedetailid = frmgrpdt_forumgroupdetailid " +
                "ORDER BY frmgrpdt_sortorder, frmgrpdt_forumgroupname");
        
        return ForumGroupFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    private ForumGroup getForumGroupByName(String forumGroupName, EntityPermission entityPermission) {
        ForumGroup forumGroup;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forumgroups, forumgroupdetails " +
                        "WHERE frmgrp_activedetailid = frmgrpdt_forumgroupdetailid AND frmgrpdt_forumgroupname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forumgroups, forumgroupdetails " +
                        "WHERE frmgrp_activedetailid = frmgrpdt_forumgroupdetailid AND frmgrpdt_forumgroupname = ? " +
                        "FOR UPDATE";
            }

            var ps = ForumGroupFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, forumGroupName);
            
            forumGroup = ForumGroupFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumGroup;
    }
    
    public ForumGroup getForumGroupByName(String forumGroupName) {
        return getForumGroupByName(forumGroupName, EntityPermission.READ_ONLY);
    }
    
    public ForumGroup getForumGroupByNameForUpdate(String forumGroupName) {
        return getForumGroupByName(forumGroupName, EntityPermission.READ_WRITE);
    }
    
    public ForumGroupDetailValue getForumGroupDetailValueForUpdate(ForumGroup forumGroup) {
        return forumGroup == null? null: forumGroup.getLastDetailForUpdate().getForumGroupDetailValue().clone();
    }
    
    public ForumGroupDetailValue getForumGroupDetailValueByNameForUpdate(String forumGroupName) {
        return getForumGroupDetailValueForUpdate(getForumGroupByNameForUpdate(forumGroupName));
    }
    
    public ForumGroupChoicesBean getForumGroupChoices(String defaultForumGroupChoice, Language language, boolean allowNullChoice) {
        var forumGroups = getForumGroups();
        var size = forumGroups.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultForumGroupChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var forumGroup : forumGroups) {
            var forumGroupDetail = forumGroup.getLastDetail();
            
            var label = getBestForumGroupDescription(forumGroup, language);
            var value = forumGroupDetail.getForumGroupName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultForumGroupChoice != null && defaultForumGroupChoice.equals(value);
            if(usingDefaultChoice || defaultValue == null)
                defaultValue = value;
        }
        
        return new ForumGroupChoicesBean(labels, values, defaultValue);
    }
    
    public ForumGroupTransfer getForumGroupTransfer(UserVisit userVisit, ForumGroup forumGroup) {
        return getForumTransferCaches(userVisit).getForumGroupTransferCache().getForumGroupTransfer(forumGroup);
    }
    
    public List<ForumGroupTransfer> getForumGroupTransfers(UserVisit userVisit, Collection<ForumGroup> forumGroups) {
        List<ForumGroupTransfer> forumGroupTransfers = new ArrayList<>(forumGroups.size());
        var forumGroupTransferCache = getForumTransferCaches(userVisit).getForumGroupTransferCache();
        
        forumGroups.forEach((forumGroup) ->
                forumGroupTransfers.add(forumGroupTransferCache.getForumGroupTransfer(forumGroup))
        );
        
        return forumGroupTransfers;
    }
    
    public List<ForumGroupTransfer> getForumGroupTransfers(UserVisit userVisit) {
        return getForumGroupTransfers(userVisit, getForumGroups());
    }
    
    public void updateForumGroupFromValue(ForumGroupDetailValue forumGroupDetailValue, BasePK updatedBy) {
        if(forumGroupDetailValue.hasBeenModified()) {
            var forumGroup = ForumGroupFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    forumGroupDetailValue.getForumGroupPK());
            var forumGroupDetail = forumGroup.getActiveDetailForUpdate();
            
            forumGroupDetail.setThruTime(session.START_TIME_LONG);
            forumGroupDetail.store();

            var forumGroupPK = forumGroupDetail.getForumGroupPK();
            var forumGroupName = forumGroupDetailValue.getForumGroupName();
            var iconPK = forumGroupDetailValue.getIconPK();
            var sortOrder = forumGroupDetailValue.getSortOrder();
            
            forumGroupDetail = ForumGroupDetailFactory.getInstance().create(forumGroupPK, forumGroupName, iconPK,
                    sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            forumGroup.setActiveDetail(forumGroupDetail);
            forumGroup.setLastDetail(forumGroupDetail);
            
            sendEvent(forumGroupPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deleteForumGroup(ForumGroup forumGroup, BasePK deletedBy) {
        deleteForumGroupDescriptionsByForumGroup(forumGroup, deletedBy);
        deleteForumGroupForumsByForumGroup(forumGroup, deletedBy);

        var forumGroupDetail = forumGroup.getLastDetailForUpdate();
        forumGroupDetail.setThruTime(session.START_TIME_LONG);
        forumGroup.setActiveDetail(null);
        forumGroup.store();
        
        sendEvent(forumGroup.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Group Descriptions
    // --------------------------------------------------------------------------------
    
    public ForumGroupDescription createForumGroupDescription(ForumGroup forumGroup, Language language, String description, BasePK createdBy) {
        var forumGroupDescription = ForumGroupDescriptionFactory.getInstance().create(forumGroup,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(forumGroup.getPrimaryKey(), EventTypes.MODIFY, forumGroupDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return forumGroupDescription;
    }
    
    private ForumGroupDescription getForumGroupDescription(ForumGroup forumGroup, Language language,
            EntityPermission entityPermission) {
        ForumGroupDescription forumGroupDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forumgroupdescriptions " +
                        "WHERE frmgrpd_frmgrp_forumgroupid = ? AND frmgrpd_lang_languageid = ? AND frmgrpd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forumgroupdescriptions " +
                        "WHERE frmgrpd_frmgrp_forumgroupid = ? AND frmgrpd_lang_languageid = ? AND frmgrpd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ForumGroupDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, forumGroup.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            forumGroupDescription = ForumGroupDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumGroupDescription;
    }
    
    public ForumGroupDescription getForumGroupDescription(ForumGroup forumGroup, Language language) {
        return getForumGroupDescription(forumGroup, language, EntityPermission.READ_ONLY);
    }
    
    public ForumGroupDescription getForumGroupDescriptionForUpdate(ForumGroup forumGroup, Language language) {
        return getForumGroupDescription(forumGroup, language, EntityPermission.READ_WRITE);
    }
    
    public ForumGroupDescriptionValue getForumGroupDescriptionValue(ForumGroupDescription forumGroupDescription) {
        return forumGroupDescription == null? null: forumGroupDescription.getForumGroupDescriptionValue().clone();
    }
    
    public ForumGroupDescriptionValue getForumGroupDescriptionValueForUpdate(ForumGroup forumGroup, Language language) {
        return getForumGroupDescriptionValue(getForumGroupDescriptionForUpdate(forumGroup, language));
    }
    
    private List<ForumGroupDescription> getForumGroupDescriptionsByForumGroup(ForumGroup forumGroup,
            EntityPermission entityPermission) {
        List<ForumGroupDescription> forumGroupDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forumgroupdescriptions, languages " +
                        "WHERE frmgrpd_frmgrp_forumgroupid = ? AND frmgrpd_thrutime = ? " +
                        "AND frmgrpd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forumgroupdescriptions " +
                        "WHERE frmgrpd_frmgrp_forumgroupid = ? AND frmgrpd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ForumGroupDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, forumGroup.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            forumGroupDescriptions = ForumGroupDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumGroupDescriptions;
    }
    
    public List<ForumGroupDescription> getForumGroupDescriptionsByForumGroup(ForumGroup forumGroup) {
        return getForumGroupDescriptionsByForumGroup(forumGroup, EntityPermission.READ_ONLY);
    }
    
    public List<ForumGroupDescription> getForumGroupDescriptionsByForumGroupForUpdate(ForumGroup forumGroup) {
        return getForumGroupDescriptionsByForumGroup(forumGroup, EntityPermission.READ_WRITE);
    }
    
    public String getBestForumGroupDescription(ForumGroup forumGroup, Language language) {
        String description;
        var forumGroupDescription = getForumGroupDescription(forumGroup, language);
        
        if(forumGroupDescription == null && !language.getIsDefault()) {
            forumGroupDescription = getForumGroupDescription(forumGroup, partyControl.getDefaultLanguage());
        }
        
        if(forumGroupDescription == null) {
            description = forumGroup.getLastDetail().getForumGroupName();
        } else {
            description = forumGroupDescription.getDescription();
        }
        
        return description;
    }
    
    public ForumGroupDescriptionTransfer getForumGroupDescriptionTransfer(UserVisit userVisit,
            ForumGroupDescription forumGroupDescription) {
        return getForumTransferCaches(userVisit).getForumGroupDescriptionTransferCache().getForumGroupDescriptionTransfer(forumGroupDescription);
    }
    
    public List<ForumGroupDescriptionTransfer> getForumGroupDescriptionTransfersByForumGroup(UserVisit userVisit,
            ForumGroup forumGroup) {
        var forumGroupDescriptions = getForumGroupDescriptionsByForumGroup(forumGroup);
        List<ForumGroupDescriptionTransfer> forumGroupDescriptionTransfers = null;
        
        if(forumGroupDescriptions != null) {
            var forumGroupDescriptionTransferCache = getForumTransferCaches(userVisit).getForumGroupDescriptionTransferCache();
            
            forumGroupDescriptionTransfers = new ArrayList<>(forumGroupDescriptions.size());
            
            for(var forumGroupDescription : forumGroupDescriptions) {
                forumGroupDescriptionTransfers.add(forumGroupDescriptionTransferCache.getForumGroupDescriptionTransfer(forumGroupDescription));
            }
        }
        
        return forumGroupDescriptionTransfers;
    }
    
    public void updateForumGroupDescriptionFromValue(ForumGroupDescriptionValue forumGroupDescriptionValue, BasePK updatedBy) {
        if(forumGroupDescriptionValue.hasBeenModified()) {
            var forumGroupDescription = ForumGroupDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     forumGroupDescriptionValue.getPrimaryKey());
            
            forumGroupDescription.setThruTime(session.START_TIME_LONG);
            forumGroupDescription.store();

            var forumGroup = forumGroupDescription.getForumGroup();
            var language = forumGroupDescription.getLanguage();
            var description = forumGroupDescriptionValue.getDescription();
            
            forumGroupDescription = ForumGroupDescriptionFactory.getInstance().create(forumGroup, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(forumGroup.getPrimaryKey(), EventTypes.MODIFY, forumGroupDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteForumGroupDescription(ForumGroupDescription forumGroupDescription, BasePK deletedBy) {
        forumGroupDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(forumGroupDescription.getForumGroupPK(), EventTypes.MODIFY, forumGroupDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteForumGroupDescriptionsByForumGroup(ForumGroup forumGroup, BasePK deletedBy) {
        var forumGroupDescriptions = getForumGroupDescriptionsByForumGroupForUpdate(forumGroup);
        
        forumGroupDescriptions.forEach((forumGroupDescription) -> 
                deleteForumGroupDescription(forumGroupDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Forums
    // --------------------------------------------------------------------------------
    
    public Forum createForum(String forumName, ForumType forumType, Icon icon, Sequence forumThreadSequence,
            Sequence forumMessageSequence, Integer sortOrder, BasePK createdBy) {
        var forum = ForumFactory.getInstance().create();
        var forumDetail = ForumDetailFactory.getInstance().create(forum, forumName, forumType, icon,
                forumThreadSequence, forumMessageSequence, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        forum = ForumFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                forum.getPrimaryKey());
        forum.setActiveDetail(forumDetail);
        forum.setLastDetail(forumDetail);
        forum.store();
        
        sendEvent(forum.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return forum;
    }
    
    /** Assume that the entityInstance passed to this function is a ECHO_THREE.Forum */
    public Forum getForumByEntityInstance(EntityInstance entityInstance) {
        var pk = new ForumPK(entityInstance.getEntityUniqueId());
        var forum = ForumFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);

        return forum;
    }

    public long countForums() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM forums, forumdetails " +
                "WHERE frm_activedetailid = frmdt_forumdetailid");
    }

    public List<Forum> getForums() {
        var ps = ForumFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM forums, forumdetails " +
                "WHERE frm_activedetailid = frmdt_forumdetailid " +
                "ORDER BY frmdt_sortorder, frmdt_forumname");
        
        return ForumFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    private Forum getForumByName(String forumName, EntityPermission entityPermission) {
        Forum forum;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forums, forumdetails " +
                        "WHERE frm_activedetailid = frmdt_forumdetailid AND frmdt_forumname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forums, forumdetails " +
                        "WHERE frm_activedetailid = frmdt_forumdetailid AND frmdt_forumname = ? " +
                        "FOR UPDATE";
            }

            var ps = ForumFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, forumName);
            
            forum = ForumFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forum;
    }
    
    public Forum getForumByName(String forumName) {
        return getForumByName(forumName, EntityPermission.READ_ONLY);
    }
    
    public Forum getForumByNameForUpdate(String forumName) {
        return getForumByName(forumName, EntityPermission.READ_WRITE);
    }
    
    public ForumDetailValue getForumDetailValueForUpdate(Forum forum) {
        return forum == null? null: forum.getLastDetailForUpdate().getForumDetailValue().clone();
    }
    
    public ForumDetailValue getForumDetailValueByNameForUpdate(String forumName) {
        return getForumDetailValueForUpdate(getForumByNameForUpdate(forumName));
    }
    
    public ForumTransfer getForumTransfer(UserVisit userVisit, Forum forum) {
        return getForumTransferCaches(userVisit).getForumTransferCache().getForumTransfer(forum);
    }
    
    public ForumChoicesBean getForumChoices(String defaultForumChoice, Language language, boolean allowNullChoice) {
        var forums = getForums();
        var size = forums.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultForumChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var forum : forums) {
            var forumDetail = forum.getLastDetail();
            
            var label = getBestForumDescription(forum, language);
            var value = forumDetail.getForumName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultForumChoice != null && defaultForumChoice.equals(value);
            if(usingDefaultChoice || defaultValue == null)
                defaultValue = value;
        }
        
        return new ForumChoicesBean(labels, values, defaultValue);
    }
    
    public List<ForumTransfer> getForumTransfers(final UserVisit userVisit, final List<Forum> forums) {
        List<ForumTransfer> forumTransfers = new ArrayList<>(forums.size());
        var forumTransferCache = getForumTransferCaches(userVisit).getForumTransferCache();
        
        forums.forEach((forum) ->
                forumTransfers.add(forumTransferCache.getForumTransfer(forum))
        );
        
        return forumTransfers;
    }
    
    public List<ForumTransfer> getForumTransfers(UserVisit userVisit) {
        return getForumTransfers(userVisit, getForums());
    }
    
    public void updateForumFromValue(ForumDetailValue forumDetailValue, BasePK updatedBy) {
        if(forumDetailValue.hasBeenModified()) {
            var forum = ForumFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    forumDetailValue.getForumPK());
            var forumDetail = forum.getActiveDetailForUpdate();
            
            forumDetail.setThruTime(session.START_TIME_LONG);
            forumDetail.store();

            var forumPK = forumDetail.getForumPK();
            var forumName = forumDetailValue.getForumName();
            var forumTypePK = forumDetail.getForumTypePK(); // Not updated
            var iconPK = forumDetailValue.getIconPK();
            var forumThreadSequencePK = forumDetailValue.getForumThreadSequencePK();
            var forumMessageSequencePK = forumDetailValue.getForumMessageSequencePK();
            var sortOrder = forumDetailValue.getSortOrder();
            
            forumDetail = ForumDetailFactory.getInstance().create(forumPK, forumName, forumTypePK, iconPK,
                    forumThreadSequencePK, forumMessageSequencePK, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            forum.setActiveDetail(forumDetail);
            forum.setLastDetail(forumDetail);
            
            sendEvent(forumPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void touchForumsByForumThread(final ForumThread forumThread, final BasePK relatedPK, final EventTypes relatedEventType,
            final BasePK touchedBy) {
        var forumForumThreads = getForumForumThreadsByForumThread(forumThread);
        
        forumForumThreads.forEach((forumForumThread) -> {
            sendEvent(forumForumThread.getForum().getPrimaryKey(), EventTypes.TOUCH, relatedPK, relatedEventType, touchedBy);
        });
    }
    
    public void deleteForum(Forum forum, BasePK deletedBy) {
        deleteForumDescriptionsByForum(forum, deletedBy);
        deleteForumGroupForumsByForum(forum, deletedBy);
        deleteForumForumThreadsByForum(forum, deletedBy);

        var forumDetail = forum.getLastDetailForUpdate();
        forumDetail.setThruTime(session.START_TIME_LONG);
        forum.setActiveDetail(null);
        forum.store();
        
        sendEvent(forum.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteForums(List<Forum> forums, BasePK deletedBy) {
        forums.forEach((forum) -> 
                deleteForum(forum, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Descriptions
    // --------------------------------------------------------------------------------
    
    public ForumDescription createForumDescription(Forum forum, Language language, String description, BasePK createdBy) {
        var forumDescription = ForumDescriptionFactory.getInstance().create(forum, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(forum.getPrimaryKey(), EventTypes.MODIFY, forumDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return forumDescription;
    }
    
    private ForumDescription getForumDescription(Forum forum, Language language, EntityPermission entityPermission) {
        ForumDescription forumDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forumdescriptions " +
                        "WHERE frmd_frm_forumid = ? AND frmd_lang_languageid = ? AND frmd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forumdescriptions " +
                        "WHERE frmd_frm_forumid = ? AND frmd_lang_languageid = ? AND frmd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ForumDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, forum.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            forumDescription = ForumDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumDescription;
    }
    
    public ForumDescription getForumDescription(Forum forum, Language language) {
        return getForumDescription(forum, language, EntityPermission.READ_ONLY);
    }
    
    public ForumDescription getForumDescriptionForUpdate(Forum forum, Language language) {
        return getForumDescription(forum, language, EntityPermission.READ_WRITE);
    }
    
    public ForumDescriptionValue getForumDescriptionValue(ForumDescription forumDescription) {
        return forumDescription == null? null: forumDescription.getForumDescriptionValue().clone();
    }
    
    public ForumDescriptionValue getForumDescriptionValueForUpdate(Forum forum, Language language) {
        return getForumDescriptionValue(getForumDescriptionForUpdate(forum, language));
    }
    
    private List<ForumDescription> getForumDescriptionsByForum(Forum forum, EntityPermission entityPermission) {
        List<ForumDescription> forumDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forumdescriptions, languages " +
                        "WHERE frmd_frm_forumid = ? AND frmd_thrutime = ? AND frmd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forumdescriptions " +
                        "WHERE frmd_frm_forumid = ? AND frmd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ForumDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, forum.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            forumDescriptions = ForumDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumDescriptions;
    }
    
    public List<ForumDescription> getForumDescriptionsByForum(Forum forum) {
        return getForumDescriptionsByForum(forum, EntityPermission.READ_ONLY);
    }
    
    public List<ForumDescription> getForumDescriptionsByForumForUpdate(Forum forum) {
        return getForumDescriptionsByForum(forum, EntityPermission.READ_WRITE);
    }
    
    public String getBestForumDescription(Forum forum, Language language) {
        String description;
        var forumDescription = getForumDescription(forum, language);
        
        if(forumDescription == null && !language.getIsDefault()) {
            forumDescription = getForumDescription(forum, partyControl.getDefaultLanguage());
        }
        
        if(forumDescription == null) {
            description = forum.getLastDetail().getForumName();
        } else {
            description = forumDescription.getDescription();
        }
        
        return description;
    }
    
    public ForumDescriptionTransfer getForumDescriptionTransfer(UserVisit userVisit, ForumDescription forumDescription) {
        return getForumTransferCaches(userVisit).getForumDescriptionTransferCache().getForumDescriptionTransfer(forumDescription);
    }
    
    public List<ForumDescriptionTransfer> getForumDescriptionTransfersByForum(UserVisit userVisit, Forum forum) {
        var forumDescriptions = getForumDescriptionsByForum(forum);
        List<ForumDescriptionTransfer> forumDescriptionTransfers = null;
        
        if(forumDescriptions != null) {
            var forumDescriptionTransferCache = getForumTransferCaches(userVisit).getForumDescriptionTransferCache();
            
            forumDescriptionTransfers = new ArrayList<>(forumDescriptions.size());
            
            for(var forumDescription : forumDescriptions) {
                forumDescriptionTransfers.add(forumDescriptionTransferCache.getForumDescriptionTransfer(forumDescription));
            }
        }
        
        return forumDescriptionTransfers;
    }
    
    public void updateForumDescriptionFromValue(ForumDescriptionValue forumDescriptionValue, BasePK updatedBy) {
        if(forumDescriptionValue.hasBeenModified()) {
            var forumDescription = ForumDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     forumDescriptionValue.getPrimaryKey());
            
            forumDescription.setThruTime(session.START_TIME_LONG);
            forumDescription.store();

            var forum = forumDescription.getForum();
            var language = forumDescription.getLanguage();
            var description = forumDescriptionValue.getDescription();
            
            forumDescription = ForumDescriptionFactory.getInstance().create(forum, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(forum.getPrimaryKey(), EventTypes.MODIFY, forumDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteForumDescription(ForumDescription forumDescription, BasePK deletedBy) {
        forumDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(forumDescription.getForumPK(), EventTypes.MODIFY, forumDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteForumDescriptionsByForum(Forum forum, BasePK deletedBy) {
        var forumDescriptions = getForumDescriptionsByForumForUpdate(forum);
        
        forumDescriptions.forEach((forumDescription) -> 
                deleteForumDescription(forumDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Group Forums
    // --------------------------------------------------------------------------------
    
    public ForumGroupForum createForumGroupForum(ForumGroup forumGroup, Forum forum, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var defaultForumGroupForum = getDefaultForumGroupForum(forum);
        var defaultFound = defaultForumGroupForum != null;
        
        if(defaultFound && isDefault) {
            var defaultForumGroupForumValue = getDefaultForumGroupForumValueForUpdate(forum);
            
            defaultForumGroupForumValue.setIsDefault(false);
            updateForumGroupForumFromValue(defaultForumGroupForumValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var forumGroupForum = ForumGroupForumFactory.getInstance().create(forumGroup, forum,
                isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(forumGroup.getPrimaryKey(), EventTypes.MODIFY, forumGroupForum.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return forumGroupForum;
    }
    
    public long countForumGroupForumsByForumGroup(ForumGroup forumGroup) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM forumgroupforums " +
                "WHERE frmgrpfrm_frmgrp_forumgroupid = ?",
                forumGroup);
    }

    private ForumGroupForum getForumGroupForum(ForumGroup forumGroup, Forum forum, EntityPermission entityPermission) {
        ForumGroupForum forumGroupForum;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forumgroupforums " +
                        "WHERE frmgrpfrm_frmgrp_forumgroupid = ? AND frmgrpfrm_frm_forumid = ? AND frmgrpfrm_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forumgroupforums " +
                        "WHERE frmgrpfrm_frmgrp_forumgroupid = ? AND frmgrpfrm_frm_forumid = ? AND frmgrpfrm_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ForumGroupForumFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, forumGroup.getPrimaryKey().getEntityId());
            ps.setLong(2, forum.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            forumGroupForum = ForumGroupForumFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumGroupForum;
    }
    
    public ForumGroupForum getForumGroupForum(ForumGroup forumGroup, Forum forum) {
        return getForumGroupForum(forumGroup, forum, EntityPermission.READ_ONLY);
    }
    
    public ForumGroupForum getForumGroupForumForUpdate(ForumGroup forumGroup, Forum forum) {
        return getForumGroupForum(forumGroup, forum, EntityPermission.READ_WRITE);
    }
    
    public ForumGroupForumValue getForumGroupForumValue(ForumGroupForum forumGroupForum) {
        return forumGroupForum == null? null: forumGroupForum.getForumGroupForumValue().clone();
    }
    
    public ForumGroupForumValue getForumGroupForumValueForUpdate(ForumGroup forumGroup, Forum forum) {
        return getForumGroupForumValue(getForumGroupForumForUpdate(forumGroup, forum));
    }
    
    private ForumGroupForum getDefaultForumGroupForum(Forum forum, EntityPermission entityPermission) {
        ForumGroupForum forumGroupForum;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forumgroupforums " +
                        "WHERE frmgrpfrm_frm_forumid = ? AND frmgrpfrm_isdefault = 1 AND frmgrpfrm_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forumgroupforums " +
                        "WHERE frmgrpfrm_frm_forumid = ? AND frmgrpfrm_isdefault = 1 AND frmgrpfrm_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ForumGroupForumFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, forum.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            forumGroupForum = ForumGroupForumFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumGroupForum;
    }
    
    public ForumGroupForum getDefaultForumGroupForum(Forum forum) {
        return getDefaultForumGroupForum(forum, EntityPermission.READ_ONLY);
    }
    
    public ForumGroupForum getDefaultForumGroupForumForUpdate(Forum forum) {
        return getDefaultForumGroupForum(forum, EntityPermission.READ_WRITE);
    }
    
    public ForumGroupForumValue getDefaultForumGroupForumValueForUpdate(Forum forum) {
        var forumGroupForum = getDefaultForumGroupForumForUpdate(forum);
        
        return forumGroupForum == null? null: forumGroupForum.getForumGroupForumValue().clone();
    }
    
    private List<ForumGroupForum> getForumGroupForumsByForumGroup(ForumGroup forumGroup, EntityPermission entityPermission) {
        List<ForumGroupForum> forumGroupForums;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forumgroupforums, forums, forumdetails " +
                        "WHERE frmgrpfrm_frmgrp_forumgroupid = ? AND frmgrpfrm_thrutime = ? " +
                        "AND frmgrpfrm_frm_forumid = frm_forumid AND frm_lastdetailid = frmdt_forumdetailid " +
                        "ORDER BY frmdt_sortorder, frmdt_forumname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forumgroupforums " +
                        "WHERE frmgrpfrm_frmgrp_forumgroupid = ? AND frmgrpfrm_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ForumGroupForumFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, forumGroup.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            forumGroupForums = ForumGroupForumFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumGroupForums;
    }
    
    public List<ForumGroupForum> getForumGroupForumsByForumGroup(ForumGroup forumGroup) {
        return getForumGroupForumsByForumGroup(forumGroup, EntityPermission.READ_ONLY);
    }
    
    public List<ForumGroupForum> getForumGroupForumsByForumGroupForUpdate(ForumGroup forumGroup) {
        return getForumGroupForumsByForumGroup(forumGroup, EntityPermission.READ_WRITE);
    }
    
    private List<ForumGroupForum> getForumGroupForumsByForum(Forum forum, EntityPermission entityPermission) {
        List<ForumGroupForum> forumGroupForums;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forumgroupforums, forumgroups, forumgroupdetails " +
                        "WHERE frmgrpfrm_frm_forumid = ? AND frmgrpfrm_thrutime = ? " +
                        "AND frmgrpfrm_frmgrp_forumgroupid = frmgrp_forumgroupid AND frmgrp_lastdetailid = frmgrpdt_forumgroupdetailid " +
                        "ORDER BY frmgrpdt_sortorder, frmgrpdt_forumgroupname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forumgroupforums " +
                        "WHERE frmgrpfrm_frm_forumid = ? AND frmgrpfrm_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ForumGroupForumFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, forum.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            forumGroupForums = ForumGroupForumFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumGroupForums;
    }
    
    public List<ForumGroupForum> getForumGroupForumsByForum(Forum forum) {
        return getForumGroupForumsByForum(forum, EntityPermission.READ_ONLY);
    }
    
    public List<ForumGroupForum> getForumGroupForumsByForumForUpdate(Forum forum) {
        return getForumGroupForumsByForum(forum, EntityPermission.READ_WRITE);
    }
    
    public List<ForumGroupForumTransfer> getForumGroupForumTransfers(UserVisit userVisit, Collection<ForumGroupForum> forumGroupForums) {
        List<ForumGroupForumTransfer> forumGroupForumTransfers = new ArrayList<>(forumGroupForums.size());
        var forumGroupForumTransferCache = getForumTransferCaches(userVisit).getForumGroupForumTransferCache();
        
        forumGroupForums.forEach((forumGroupForum) ->
                forumGroupForumTransfers.add(forumGroupForumTransferCache.getForumGroupForumTransfer(forumGroupForum))
        );
        
        return forumGroupForumTransfers;
    }
    
    public List<ForumGroupForumTransfer> getForumGroupForumTransfersByForumGroup(UserVisit userVisit, ForumGroup forumGroup) {
        return getForumGroupForumTransfers(userVisit, getForumGroupForumsByForumGroup(forumGroup));
    }
    
    public List<ForumGroupForumTransfer> getForumGroupForumTransfersByForum(UserVisit userVisit, Forum forum) {
        return getForumGroupForumTransfers(userVisit, getForumGroupForumsByForum(forum));
    }
    
    public ForumGroupForumTransfer getForumGroupForumTransfer(UserVisit userVisit, ForumGroupForum forumGroupForum) {
        return getForumTransferCaches(userVisit).getForumGroupForumTransferCache().getForumGroupForumTransfer(forumGroupForum);
    }
    
    private void updateForumGroupForumFromValue(ForumGroupForumValue forumGroupForumValue, boolean checkDefault, BasePK updatedBy) {
        if(forumGroupForumValue.hasBeenModified()) {
            var forumGroupForum = ForumGroupForumFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     forumGroupForumValue.getPrimaryKey());
            
            forumGroupForum.setThruTime(session.START_TIME_LONG);
            forumGroupForum.store();

            var forumGroupPK = forumGroupForum.getForumGroupPK(); // Not Updated
            var forum = forumGroupForum.getForum(); // Not Updated
            var isDefault = forumGroupForumValue.getIsDefault();
            var sortOrder = forumGroupForumValue.getSortOrder();
            
            if(checkDefault) {
                var defaultForumGroupForum = getDefaultForumGroupForum(forum);
                var defaultFound = defaultForumGroupForum != null && !defaultForumGroupForum.equals(forumGroupForum);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultForumGroupForumValue = getDefaultForumGroupForumValueForUpdate(forum);
                    
                    defaultForumGroupForumValue.setIsDefault(false);
                    updateForumGroupForumFromValue(defaultForumGroupForumValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            forumGroupForum = ForumGroupForumFactory.getInstance().create(forumGroupPK, forum.getPrimaryKey(), isDefault,
                    sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(forumGroupPK, EventTypes.MODIFY, forumGroupForum.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void updateForumGroupForumFromValue(ForumGroupForumValue forumGroupForumValue, BasePK updatedBy) {
        updateForumGroupForumFromValue(forumGroupForumValue, true, updatedBy);
    }
    
    public void deleteForumGroupForum(ForumGroupForum forumGroupForum, BasePK deletedBy) {
        forumGroupForum.setThruTime(session.START_TIME_LONG);
        forumGroupForum.store();
        
        // Check for default, and pick one if necessary
        var forum = forumGroupForum.getForum();
        var defaultForumGroupForum = getDefaultForumGroupForum(forum);
        if(defaultForumGroupForum == null) {
            var forumGroupForums = getForumGroupForumsByForumForUpdate(forum);
            
            if(!forumGroupForums.isEmpty()) {
                var iter = forumGroupForums.iterator();
                if(iter.hasNext()) {
                    defaultForumGroupForum = iter.next();
                }
                var forumGroupForumValue = defaultForumGroupForum.getForumGroupForumValue().clone();
                
                forumGroupForumValue.setIsDefault(true);
                updateForumGroupForumFromValue(forumGroupForumValue, false, deletedBy);
            }
        }
        
        sendEvent(forumGroupForum.getForumGroupPK(), EventTypes.MODIFY, forumGroupForum.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteForumGroupForums(List<ForumGroupForum> forumGroupForums, BasePK deletedBy) {
        forumGroupForums.forEach((forumGroupForum) -> 
                deleteForumGroupForum(forumGroupForum, deletedBy)
        );
    }
    
    public void deleteForumGroupForumsByForumGroup(ForumGroup forumGroup, BasePK deletedBy) {
        deleteForumGroupForums(getForumGroupForumsByForumGroupForUpdate(forumGroup), deletedBy);
    }
    
    public void deleteForumGroupForumsByForum(Forum forum, BasePK deletedBy) {
        deleteForumGroupForums(getForumGroupForumsByForumForUpdate(forum), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Role Types
    // --------------------------------------------------------------------------------
    
    public ForumRoleType createForumRoleType(String forumRoleTypeName, Boolean isDefault, Integer sortOrder) {
        return ForumRoleTypeFactory.getInstance().create(forumRoleTypeName, isDefault, sortOrder);
    }
    
    public ForumRoleType getForumRoleTypeByName(String forumRoleTypeName) {
        ForumRoleType forumRoleType;
        
        try {
            var ps = ForumRoleTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM forumroletypes " +
                    "WHERE frmrtyp_forumroletypename = ?");
            
            ps.setString(1, forumRoleTypeName);
            
            forumRoleType = ForumRoleTypeFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumRoleType;
    }
    
    public List<ForumRoleType> getForumRoleTypes() {
        var ps = ForumRoleTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM forumroletypes " +
                "ORDER BY frmrtyp_sortorder, frmrtyp_forumroletypename");
        
        return ForumRoleTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public ForumRoleTypeChoicesBean getForumRoleTypeChoices(String defaultForumRoleTypeChoice, Language language,
            boolean allowNullChoice) {
        var forumRoleTypes = getForumRoleTypes();
        var size = forumRoleTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultForumRoleTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var forumRoleType : forumRoleTypes) {
            var label = getBestForumRoleTypeDescription(forumRoleType, language);
            var value = forumRoleType.getForumRoleTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultForumRoleTypeChoice != null && defaultForumRoleTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && forumRoleType.getIsDefault()))
                defaultValue = value;
        }
        
        return new ForumRoleTypeChoicesBean(labels, values, defaultValue);
    }
    
    public ForumRoleTypeTransfer getForumRoleTypeTransfer(UserVisit userVisit, ForumRoleType forumRoleType) {
        return getForumTransferCaches(userVisit).getForumRoleTypeTransferCache().getForumRoleTypeTransfer(forumRoleType);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Role Type Descriptions
    // --------------------------------------------------------------------------------
    
    public ForumRoleTypeDescription createForumRoleTypeDescription(ForumRoleType forumRoleType, Language language,
            String description) {
        return ForumRoleTypeDescriptionFactory.getInstance().create(forumRoleType, language, description);
    }
    
    public ForumRoleTypeDescription getForumRoleTypeDescription(ForumRoleType forumRoleType, Language language) {
        ForumRoleTypeDescription forumRoleTypeDescription;
        
        try {
            var ps = ForumRoleTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM forumroletypedescriptions " +
                    "WHERE frmrtypd_frmrtyp_forumroletypeid = ? AND frmrtypd_lang_languageid = ?");
            
            ps.setLong(1, forumRoleType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            forumRoleTypeDescription = ForumRoleTypeDescriptionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY,
                    ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumRoleTypeDescription;
    }
    
    public String getBestForumRoleTypeDescription(ForumRoleType forumRoleType, Language language) {
        String description;
        var forumRoleTypeDescription = getForumRoleTypeDescription(forumRoleType, language);
        
        if(forumRoleTypeDescription == null && !language.getIsDefault()) {
            forumRoleTypeDescription = getForumRoleTypeDescription(forumRoleType, partyControl.getDefaultLanguage());
        }
        
        if(forumRoleTypeDescription == null) {
            description = forumRoleType.getForumRoleTypeName();
        } else {
            description = forumRoleTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Types
    // --------------------------------------------------------------------------------
    
    public ForumType createForumType(String forumTypeName, Boolean isDefault, Integer sortOrder) {
        return ForumTypeFactory.getInstance().create(forumTypeName, isDefault, sortOrder);
    }
    
    public ForumType getForumTypeByName(String forumTypeName) {
        ForumType forumType;
        
        try {
            var ps = ForumTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM forumtypes " +
                    "WHERE frmtyp_forumtypename = ?");
            
            ps.setString(1, forumTypeName);
            
            forumType = ForumTypeFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumType;
    }
    
    public List<ForumType> getForumTypes() {
        var ps = ForumTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM forumtypes " +
                "ORDER BY frmtyp_sortorder, frmtyp_forumtypename");
        
        return ForumTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public ForumTypeChoicesBean getForumTypeChoices(String defaultForumTypeChoice, Language language, boolean allowNullChoice) {
        var forumTypes = getForumTypes();
        var size = forumTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultForumTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var forumType : forumTypes) {
            var label = getBestForumTypeDescription(forumType, language);
            var value = forumType.getForumTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultForumTypeChoice != null && defaultForumTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && forumType.getIsDefault()))
                defaultValue = value;
        }
        
        return new ForumTypeChoicesBean(labels, values, defaultValue);
    }
    
    public ForumTypeTransfer getForumTypeTransfer(UserVisit userVisit, ForumType forumType) {
        return getForumTransferCaches(userVisit).getForumTypeTransferCache().getForumTypeTransfer(forumType);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Type Descriptions
    // --------------------------------------------------------------------------------
    
    public ForumTypeDescription createForumTypeDescription(ForumType forumType, Language language, String description) {
        return ForumTypeDescriptionFactory.getInstance().create(forumType, language, description);
    }
    
    public ForumTypeDescription getForumTypeDescription(ForumType forumType, Language language) {
        ForumTypeDescription forumTypeDescription;
        
        try {
            var ps = ForumTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM forumtypedescriptions " +
                    "WHERE frmtypd_frmtyp_forumtypeid = ? AND frmtypd_lang_languageid = ?");
            
            ps.setLong(1, forumType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            forumTypeDescription = ForumTypeDescriptionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY,
                    ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumTypeDescription;
    }
    
    public String getBestForumTypeDescription(ForumType forumType, Language language) {
        String description;
        var forumTypeDescription = getForumTypeDescription(forumType, language);
        
        if(forumTypeDescription == null && !language.getIsDefault()) {
            forumTypeDescription = getForumTypeDescription(forumType, partyControl.getDefaultLanguage());
        }
        
        if(forumTypeDescription == null) {
            description = forumType.getForumTypeName();
        } else {
            description = forumTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Mime Types
    // --------------------------------------------------------------------------------
    
    public ForumMimeType createForumMimeType(Forum forum, MimeType mimeType, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var defaultForumMimeType = getDefaultForumMimeType(forum);
        var defaultFound = defaultForumMimeType != null;
        
        if(defaultFound && isDefault) {
            var defaultForumMimeTypeValue = getDefaultForumMimeTypeValueForUpdate(forum);
            
            defaultForumMimeTypeValue.setIsDefault(false);
            updateForumMimeTypeFromValue(defaultForumMimeTypeValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var forumMimeType = ForumMimeTypeFactory.getInstance().create(forum, mimeType,
                isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(forum.getPrimaryKey(), EventTypes.MODIFY, forumMimeType.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return forumMimeType;
    }
    
    private ForumMimeType getForumMimeType(Forum forum, MimeType mimeType, EntityPermission entityPermission) {
        ForumMimeType forumMimeType;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forummimetypes " +
                        "WHERE frmmtyp_frm_forumid = ? AND frmmtyp_mtyp_mimetypeid = ? AND frmmtyp_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forummimetypes " +
                        "WHERE frmmtyp_frm_forumid = ? AND frmmtyp_mtyp_mimetypeid = ? AND frmmtyp_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ForumMimeTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, forum.getPrimaryKey().getEntityId());
            ps.setLong(2, mimeType.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            forumMimeType = ForumMimeTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumMimeType;
    }
    
    public ForumMimeType getForumMimeType(Forum forum, MimeType mimeType) {
        return getForumMimeType(forum, mimeType, EntityPermission.READ_ONLY);
    }
    
    public ForumMimeType getForumMimeTypeForUpdate(Forum forum, MimeType mimeType) {
        return getForumMimeType(forum, mimeType, EntityPermission.READ_WRITE);
    }
    
    public ForumMimeTypeValue getForumMimeTypeValue(ForumMimeType forumMimeType) {
        return forumMimeType == null? null: forumMimeType.getForumMimeTypeValue().clone();
    }
    
    public ForumMimeTypeValue getForumMimeTypeValueForUpdate(Forum forum, MimeType mimeType) {
        return getForumMimeTypeValue(getForumMimeTypeForUpdate(forum, mimeType));
    }
    
    private ForumMimeType getDefaultForumMimeType(Forum forum, EntityPermission entityPermission) {
        ForumMimeType forumMimeType;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forummimetypes " +
                        "WHERE frmmtyp_frm_forumid = ? AND frmmtyp_isdefault = 1 AND frmmtyp_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forummimetypes " +
                        "WHERE frmmtyp_frm_forumid = ? AND frmmtyp_isdefault = 1 AND frmmtyp_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ForumMimeTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, forum.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            forumMimeType = ForumMimeTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumMimeType;
    }
    
    public ForumMimeType getDefaultForumMimeType(Forum forum) {
        return getDefaultForumMimeType(forum, EntityPermission.READ_ONLY);
    }
    
    public ForumMimeType getDefaultForumMimeTypeForUpdate(Forum forum) {
        return getDefaultForumMimeType(forum, EntityPermission.READ_WRITE);
    }
    
    public ForumMimeTypeValue getDefaultForumMimeTypeValueForUpdate(Forum forum) {
        var forumMimeType = getDefaultForumMimeTypeForUpdate(forum);
        
        return forumMimeType == null? null: forumMimeType.getForumMimeTypeValue().clone();
    }
    
    private List<ForumMimeType> getForumMimeTypesByForum(Forum forum, EntityPermission entityPermission) {
        List<ForumMimeType> forumMimeTypes;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forummimetypes, mimetypes, mimetypedetails " +
                        "WHERE frmmtyp_frm_forumid = ? AND frmmtyp_thrutime = ? " +
                        "AND frmmtyp_mtyp_mimetypeid = mtyp_mimetypeid AND mtyp_lastdetailid = mtypdt_mimetypedetailid " +
                        "ORDER BY frmmtyp_sortorder, mtypdt_sortorder, mtypdt_mimetypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forummimetypes " +
                        "WHERE frmmtyp_frm_forumid = ? AND frmmtyp_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ForumMimeTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, forum.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            forumMimeTypes = ForumMimeTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumMimeTypes;
    }
    
    public List<ForumMimeType> getForumMimeTypesByForum(Forum forum) {
        return getForumMimeTypesByForum(forum, EntityPermission.READ_ONLY);
    }
    
    public List<ForumMimeType> getForumMimeTypesByForumForUpdate(Forum forum) {
        return getForumMimeTypesByForum(forum, EntityPermission.READ_WRITE);
    }
    
    private List<ForumMimeType> getForumMimeTypesByMimeType(MimeType mimeType, EntityPermission entityPermission) {
        List<ForumMimeType> forumMimeTypes;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forummimetypes, forums, forumdetails " +
                        "WHERE frmmtyp_mtyp_mimetypeid = ? AND frmmtyp_thrutime = ? " +
                        "AND frmmtyp_frm_forumid = frm_forumid AND frm_lastdetailid = frmdt_forumdetailid " +
                        "ORDER BY frmmtyp_sortorder, frmdt_sortorder, frmdt_forumname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forummimetypes " +
                        "WHERE frmmtyp_mtyp_mimetypeid = ? AND frmmtyp_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ForumMimeTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, mimeType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            forumMimeTypes = ForumMimeTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumMimeTypes;
    }
    
    public List<ForumMimeType> getForumMimeTypesByMimeType(MimeType mimeType) {
        return getForumMimeTypesByMimeType(mimeType, EntityPermission.READ_ONLY);
    }
    
    public List<ForumMimeType> getForumMimeTypesByMimeTypeForUpdate(MimeType mimeType) {
        return getForumMimeTypesByMimeType(mimeType, EntityPermission.READ_WRITE);
    }
    
    public MimeTypeChoicesBean getForumMimeTypeChoices(Forum forum, String defaultMimeTypeChoice, Language language,
            boolean allowNullChoice) {
        var mimeTypeControl = Session.getModelController(MimeTypeControl.class);
        var forumMimeTypes = getForumMimeTypesByForum(forum);
        var size = forumMimeTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultMimeTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var forumMimeType : forumMimeTypes) {
            var mimeType = forumMimeType.getMimeType();
            var label = mimeTypeControl.getBestMimeTypeDescription(mimeType, language);
            var value = mimeType.getLastDetail().getMimeTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultMimeTypeChoice != null && defaultMimeTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && forumMimeType.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new MimeTypeChoicesBean(labels, values, defaultValue);
    }
    
    public List<ForumMimeTypeTransfer> getForumMimeTypeTransfers(UserVisit userVisit, Collection<ForumMimeType> forumMimeTypes) {
        List<ForumMimeTypeTransfer> forumMimeTypeTransfers = new ArrayList<>(forumMimeTypes.size());
        var forumMimeTypeTransferCache = getForumTransferCaches(userVisit).getForumMimeTypeTransferCache();
        
        forumMimeTypes.forEach((forumMimeType) ->
                forumMimeTypeTransfers.add(forumMimeTypeTransferCache.getForumMimeTypeTransfer(forumMimeType))
        );
        
        return forumMimeTypeTransfers;
    }
    
    public List<ForumMimeTypeTransfer> getForumMimeTypeTransfersByForum(UserVisit userVisit, Forum forum) {
        return getForumMimeTypeTransfers(userVisit, getForumMimeTypesByForum(forum));
    }
    
    public List<ForumMimeTypeTransfer> getForumMimeTypeTransfersByMimeType(UserVisit userVisit, MimeType mimeType) {
        return getForumMimeTypeTransfers(userVisit, getForumMimeTypesByMimeType(mimeType));
    }
    
    public ForumMimeTypeTransfer getForumMimeTypeTransfer(UserVisit userVisit, ForumMimeType forumMimeType) {
        return getForumTransferCaches(userVisit).getForumMimeTypeTransferCache().getForumMimeTypeTransfer(forumMimeType);
    }
    
    private void updateForumMimeTypeFromValue(ForumMimeTypeValue forumMimeTypeValue, boolean checkDefault, BasePK updatedBy) {
        if(forumMimeTypeValue.hasBeenModified()) {
            var forumMimeType = ForumMimeTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     forumMimeTypeValue.getPrimaryKey());
            
            forumMimeType.setThruTime(session.START_TIME_LONG);
            forumMimeType.store();

            var forum = forumMimeType.getForum(); // Not Updated
            var forumPK = forum.getPrimaryKey(); // Not Updated
            var mimeTypePK = forumMimeType.getMimeTypePK(); // Not Updated
            var isDefault = forumMimeTypeValue.getIsDefault();
            var sortOrder = forumMimeTypeValue.getSortOrder();
            
            if(checkDefault) {
                var defaultForumMimeType = getDefaultForumMimeType(forum);
                var defaultFound = defaultForumMimeType != null && !defaultForumMimeType.equals(forumMimeType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultForumMimeTypeValue = getDefaultForumMimeTypeValueForUpdate(forum);
                    
                    defaultForumMimeTypeValue.setIsDefault(false);
                    updateForumMimeTypeFromValue(defaultForumMimeTypeValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            forumMimeType = ForumMimeTypeFactory.getInstance().create(forumPK, mimeTypePK,
                    isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(forumPK, EventTypes.MODIFY, forumMimeType.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void updateForumMimeTypeFromValue(ForumMimeTypeValue forumMimeTypeValue, BasePK updatedBy) {
        updateForumMimeTypeFromValue(forumMimeTypeValue, true, updatedBy);
    }
    
    public void deleteForumMimeType(ForumMimeType forumMimeType, BasePK deletedBy) {
        forumMimeType.setThruTime(session.START_TIME_LONG);
        forumMimeType.store();
        
        // Check for default, and pick one if necessary
        var forum = forumMimeType.getForum();
        var defaultForumMimeType = getDefaultForumMimeType(forum);
        if(defaultForumMimeType == null) {
            var forumMimeTypes = getForumMimeTypesByForumForUpdate(forum);
            
            if(!forumMimeTypes.isEmpty()) {
                var iter = forumMimeTypes.iterator();
                if(iter.hasNext()) {
                    defaultForumMimeType = iter.next();
                }
                var forumMimeTypeValue = defaultForumMimeType.getForumMimeTypeValue().clone();
                
                forumMimeTypeValue.setIsDefault(true);
                updateForumMimeTypeFromValue(forumMimeTypeValue, false, deletedBy);
            }
        }
        
        sendEvent(forum.getPrimaryKey(), EventTypes.MODIFY, forumMimeType.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteForumMimeTypes(List<ForumMimeType> forumMimeTypes, BasePK deletedBy) {
        forumMimeTypes.forEach((forumMimeType) -> 
                deleteForumMimeType(forumMimeType, deletedBy)
        );
    }
    
    public void deleteForumMimeTypesByForum(Forum forum, BasePK deletedBy) {
        deleteForumMimeTypes(getForumMimeTypesByForumForUpdate(forum), deletedBy);
    }
    
    public void deleteForumMimeTypesByMimeType(MimeType mimeType, BasePK deletedBy) {
        deleteForumMimeTypes(getForumMimeTypesByMimeTypeForUpdate(mimeType), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Party Roles
    // --------------------------------------------------------------------------------
    
    public ForumPartyRole createForumPartyRole(Forum forum, Party party, ForumRoleType forumRoleType, BasePK createdBy) {
        var forumPartyRole = ForumPartyRoleFactory.getInstance().create(forum, party, forumRoleType,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(forum.getPrimaryKey(), EventTypes.MODIFY, forumPartyRole.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return forumPartyRole;
    }
    
    public boolean hasForumPartyRoles(Forum forum, ForumRoleType forumRoleType) {
        return !(session.queryForInteger(
                "SELECT COUNT(*) " +
                "FROM forumpartyroles " +
                "WHERE frmparr_frm_forumid = ? AND frmparr_frmrtyp_forumroletypeid = ? AND frmparr_thrutime = ?",
                forum, forumRoleType, Session.MAX_TIME) == 0);
    }

    public boolean hasForumPartyRole(Forum forum, Party party, ForumRoleType forumRoleType) {
        return !(session.queryForInteger(
                "SELECT COUNT(*) " +
                "FROM forumpartyroles " +
                "WHERE frmparr_frm_forumid = ? AND frmparr_par_partyid = ? AND frmparr_frmrtyp_forumroletypeid = ? AND frmparr_thrutime = ?",
                forum, party, forumRoleType, Session.MAX_TIME) == 0);
    }

    private List<ForumPartyRole> getForumPartyRolesByForum(Forum forum, EntityPermission entityPermission) {
        List<ForumPartyRole> forumPartyRoles;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forumpartyroles, parties, partydetails, forumroletypes " +
                        "WHERE frmparr_frm_forumid = ? AND frmparr_thrutime = ? " +
                        "AND frmparr_par_partyid = par_partyid AND par_lastdetailid = pardt_partydetailid " +
                        "AND frmparr_frmrtyp_forumroletypeid = frmrtyp_forumroletypeid " +
                        "ORDER BY pardt_partyname, frmrtyp_sortorder, frmrtyp_forumroletypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forumpartyroles " +
                        "WHERE frmparr_frm_forumid = ? AND frmparr_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ForumPartyRoleFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, forum.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            forumPartyRoles = ForumPartyRoleFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumPartyRoles;
    }
    
    public List<ForumPartyRole> getForumPartyRolesByForum(Forum forum) {
        return getForumPartyRolesByForum(forum, EntityPermission.READ_ONLY);
    }
    
    public List<ForumPartyRole> getForumPartyRolesByForumForUpdate(Forum forum) {
        return getForumPartyRolesByForum(forum, EntityPermission.READ_WRITE);
    }
    
    private List<ForumPartyRole> getForumPartyRolesByParty(Party party, EntityPermission entityPermission) {
        List<ForumPartyRole> forumPartyRoles;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forumpartyroles, forums, forumdetails, forumroletypes " +
                        "WHERE frmparr_par_partyid = ? AND frmparr_thrutime = ? " +
                        "AND frmparr_frm_forumid = frm_forumid AND frm_lastdetailid = frmdt_forumdetailid " +
                        "AND frmparr_frmrtyp_forumroletypeid = frmrtyp_forumroletypeid " +
                        "ORDER BY frmdt_sortorder, frmdt_forumname, frmrtyp_sortorder, frmrtyp_forumroletypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forumpartyroles " +
                        "WHERE frmparr_par_partyid = ? AND frmparr_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ForumPartyRoleFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, party.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            forumPartyRoles = ForumPartyRoleFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumPartyRoles;
    }
    
    public List<ForumPartyRole> getForumPartyRolesByParty(Party party) {
        return getForumPartyRolesByParty(party, EntityPermission.READ_ONLY);
    }
    
    public List<ForumPartyRole> getForumPartyRolesByPartyForUpdate(Party party) {
        return getForumPartyRolesByParty(party, EntityPermission.READ_WRITE);
    }
    
    private ForumPartyRole getForumPartyRole(Forum forum, Party party, ForumRoleType forumRoleType, EntityPermission entityPermission) {
        ForumPartyRole forumPartyRole;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forumpartyroles " +
                        "WHERE frmparr_frm_forumid = ? AND frmparr_par_partyid = ? AND frmparr_frmrtyp_forumroletypeid = ? " +
                        "AND frmparr_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forumpartyroles " +
                        "WHERE frmparr_frm_forumid = ? AND frmparr_par_partyid = ? AND frmparr_frmrtyp_forumroletypeid = ? " +
                        "AND frmparr_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ForumPartyRoleFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, forum.getPrimaryKey().getEntityId());
            ps.setLong(2, party.getPrimaryKey().getEntityId());
            ps.setLong(3, forumRoleType.getPrimaryKey().getEntityId());
            ps.setLong(4, Session.MAX_TIME);
            
            forumPartyRole = ForumPartyRoleFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumPartyRole;
    }
    
    public ForumPartyRole getForumPartyRole(Forum forum, Party party, ForumRoleType forumRoleType) {
        return getForumPartyRole(forum, party, forumRoleType, EntityPermission.READ_ONLY);
    }
    
    public ForumPartyRole getForumPartyRoleForUpdate(Forum forum, Party party, ForumRoleType forumRoleType) {
        return getForumPartyRole(forum, party, forumRoleType, EntityPermission.READ_WRITE);
    }
    
    public List<ForumPartyRoleTransfer> getForumPartyRoleTransfersByForum(UserVisit userVisit, Forum forum) {
        var forumPartyRoles = getForumPartyRolesByForum(forum);
        List<ForumPartyRoleTransfer> forumPartyRoleTransfers = null;
        
        if(forumPartyRoles != null) {
            var forumPartyRoleTransferCache = getForumTransferCaches(userVisit).getForumPartyRoleTransferCache();
            
            forumPartyRoleTransfers = new ArrayList<>(forumPartyRoles.size());
            
            for(var forumPartyRole : forumPartyRoles) {
                forumPartyRoleTransfers.add(forumPartyRoleTransferCache.getForumPartyRoleTransfer(forumPartyRole));
            }
        }
        
        return forumPartyRoleTransfers;
    }
    
    public ForumPartyRoleTransfer getForumPartyRoleTransfer(UserVisit userVisit, ForumPartyRole forumPartyRole) {
        return getForumTransferCaches(userVisit).getForumPartyRoleTransferCache().getForumPartyRoleTransfer(forumPartyRole);
    }
    
    public void deleteForumPartyRole(ForumPartyRole forumPartyRole, BasePK deletedBy) {
        forumPartyRole.setThruTime(session.START_TIME_LONG);
        
        sendEvent(forumPartyRole.getForumPK(), EventTypes.MODIFY, forumPartyRole.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteForumPartyRolesByForum(Forum forum, BasePK deletedBy) {
        var forumPartyRoles = getForumPartyRolesByForumForUpdate(forum);
        
        forumPartyRoles.forEach((forumPartyRole) -> 
                deleteForumPartyRole(forumPartyRole, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Party Type Roles
    // --------------------------------------------------------------------------------
    
    public ForumPartyTypeRole createForumPartyTypeRole(Forum forum, PartyType partyType, ForumRoleType forumRoleType,
            BasePK createdBy) {
        var forumPartyTypeRole = ForumPartyTypeRoleFactory.getInstance().create(forum, partyType,
                forumRoleType, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(forum.getPrimaryKey(), EventTypes.MODIFY, forumPartyTypeRole.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return forumPartyTypeRole;
    }
    
    public boolean hasForumPartyTypeRoles(Forum forum, ForumRoleType forumRoleType) {
        return !(session.queryForInteger(
                "SELECT COUNT(*) " +
                "FROM forumpartytyperoles " +
                "WHERE frmptypr_frm_forumid = ? AND frmptypr_frmrtyp_forumroletypeid = ? AND frmptypr_thrutime = ?",
                forum, forumRoleType, Session.MAX_TIME) == 0);
    }

    public boolean hasForumPartyTypeRole(Forum forum, PartyType partyType, ForumRoleType forumRoleType) {
        return !(session.queryForInteger(
                "SELECT COUNT(*) " +
                "FROM forumpartytyperoles " +
                "WHERE frmptypr_frm_forumid = ? AND frmptypr_ptyp_partytypeid = ? AND frmptypr_frmrtyp_forumroletypeid = ? AND frmptypr_thrutime = ?",
                forum, partyType, forumRoleType, Session.MAX_TIME) == 0);
    }

    private List<ForumPartyTypeRole> getForumPartyTypeRolesByForum(Forum forum, EntityPermission entityPermission) {
        List<ForumPartyTypeRole> forumPartyTypeRoles;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forumpartytyperoles, partytypes, forumroletypes " +
                        "WHERE frmptypr_frm_forumid = ? AND frmptypr_thrutime = ? " +
                        "AND frmptypr_ptyp_partytypeid = ptyp_partytypeid " +
                        "AND frmptypr_frmrtyp_forumroletypeid = frmrtyp_forumroletypeid " +
                        "ORDER BY ptyp_sortorder, ptyp_partytypename, frmrtyp_sortorder, frmrtyp_forumroletypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forumpartytyperoles " +
                        "WHERE frmptypr_frm_forumid = ? AND frmptypr_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ForumPartyTypeRoleFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, forum.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            forumPartyTypeRoles = ForumPartyTypeRoleFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumPartyTypeRoles;
    }
    
    public List<ForumPartyTypeRole> getForumPartyTypeRolesByForum(Forum forum) {
        return getForumPartyTypeRolesByForum(forum, EntityPermission.READ_ONLY);
    }
    
    public List<ForumPartyTypeRole> getForumPartyTypeRolesByForumForUpdate(Forum forum) {
        return getForumPartyTypeRolesByForum(forum, EntityPermission.READ_WRITE);
    }
    
    private ForumPartyTypeRole getForumPartyTypeRole(Forum forum, PartyType partyType, ForumRoleType forumRoleType, EntityPermission entityPermission) {
        ForumPartyTypeRole forumPartyTypeRole;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forumpartytyperoles " +
                        "WHERE frmptypr_frm_forumid = ? AND frmptypr_ptyp_partytypeid = ? AND frmptypr_frmrtyp_forumroletypeid = ? " +
                        "AND frmptypr_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forumpartytyperoles " +
                        "WHERE frmptypr_frm_forumid = ? AND frmptypr_ptyp_partytypeid = ? AND frmptypr_frmrtyp_forumroletypeid = ? " +
                        "AND frmptypr_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ForumPartyTypeRoleFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, forum.getPrimaryKey().getEntityId());
            ps.setLong(2, partyType.getPrimaryKey().getEntityId());
            ps.setLong(3, forumRoleType.getPrimaryKey().getEntityId());
            ps.setLong(4, Session.MAX_TIME);
            
            forumPartyTypeRole = ForumPartyTypeRoleFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumPartyTypeRole;
    }
    
    public ForumPartyTypeRole getForumPartyTypeRole(Forum forum, PartyType partyType, ForumRoleType forumRoleType) {
        return getForumPartyTypeRole(forum, partyType, forumRoleType, EntityPermission.READ_ONLY);
    }
    
    public ForumPartyTypeRole getForumPartyTypeRoleForUpdate(Forum forum, PartyType partyType, ForumRoleType forumRoleType) {
        return getForumPartyTypeRole(forum, partyType, forumRoleType, EntityPermission.READ_WRITE);
    }
    
    public List<ForumPartyTypeRoleTransfer> getForumPartyTypeRoleTransfersByForum(UserVisit userVisit, Forum forum) {
        var forumPartyTypeRoles = getForumPartyTypeRolesByForum(forum);
        List<ForumPartyTypeRoleTransfer> forumPartyTypeRoleTransfers = null;
        
        if(forumPartyTypeRoles != null) {
            var forumPartyTypeRoleTransferCache = getForumTransferCaches(userVisit).getForumPartyTypeRoleTransferCache();
            
            forumPartyTypeRoleTransfers = new ArrayList<>(forumPartyTypeRoles.size());
            
            for(var forumPartyTypeRole : forumPartyTypeRoles) {
                forumPartyTypeRoleTransfers.add(forumPartyTypeRoleTransferCache.getForumPartyTypeRoleTransfer(forumPartyTypeRole));
            }
        }
        
        return forumPartyTypeRoleTransfers;
    }
    
    public ForumPartyTypeRoleTransfer getForumPartyTypeRoleTransfer(UserVisit userVisit, ForumPartyTypeRole forumPartyTypeRole) {
        return getForumTransferCaches(userVisit).getForumPartyTypeRoleTransferCache().getForumPartyTypeRoleTransfer(forumPartyTypeRole);
    }
    
    public void deleteForumPartyTypeRole(ForumPartyTypeRole forumPartyTypeRole, BasePK deletedBy) {
        forumPartyTypeRole.setThruTime(session.START_TIME_LONG);
        
        sendEvent(forumPartyTypeRole.getForumPK(), EventTypes.MODIFY, forumPartyTypeRole.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteForumPartyTypeRolesByForum(Forum forum, BasePK deletedBy) {
        var forumPartyTypeRoles = getForumPartyTypeRolesByForumForUpdate(forum);
        
        forumPartyTypeRoles.forEach((forumPartyTypeRole) -> 
                deleteForumPartyTypeRole(forumPartyTypeRole, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Type Message Types
    // --------------------------------------------------------------------------------
    
    public ForumTypeMessageType createForumTypeMessageType(ForumType forumType, ForumMessageType forumMessageType,
            Boolean isDefault, Integer sortOrder) {
        return ForumTypeMessageTypeFactory.getInstance().create(forumType, forumMessageType, isDefault, sortOrder);
    }
    
    private List<ForumTypeMessageType> getForumTypeMessageTypesByForumType(ForumType forumType, EntityPermission entityPermission) {
        List<ForumTypeMessageType> forumTypeMessageTypes;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forumtypemessagetypes, forummessagetypes " +
                        "WHERE frmtypmsgtyp_frmtyp_forumtypeid = ? " +
                        "AND frmtypmsgtyp_frmmsgtyp_forummessagetypeid = frmmsgtyp_forummessagetypeid " +
                        "ORDER BY frmtypmsgtyp_sortorder, frmmsgtyp_sortorder, frmmsgtyp_forummessagetypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forumtypemessagetypes " +
                        "WHERE frmtypmsgtyp_frmtyp_forumtypeid = ? " +
                        "FOR UPDATE";
            }

            var ps = ForumTypeMessageTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, forumType.getPrimaryKey().getEntityId());
            
            forumTypeMessageTypes = ForumTypeMessageTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumTypeMessageTypes;
    }
    
    public List<ForumTypeMessageType> getForumTypeMessageTypesByForumType(ForumType forumType) {
        return getForumTypeMessageTypesByForumType(forumType, EntityPermission.READ_ONLY);
    }
    
    public List<ForumTypeMessageType> getForumTypeMessageTypesByForumTypeForUpdate(ForumType forumType) {
        return getForumTypeMessageTypesByForumType(forumType, EntityPermission.READ_WRITE);
    }
    
    private ForumTypeMessageType getForumTypeMessageType(ForumType forumType, ForumMessageType forumMessageType, EntityPermission entityPermission) {
        ForumTypeMessageType forumTypeMessageType;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forumtypemessagetypes " +
                        "WHERE frmtypmsgtyp_frmtyp_forumtypeid = ? AND frmtypmsgtyp_frmmsgtyp_forummessagetypeid = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forumtypemessagetypes " +
                        "WHERE frmtypmsgtyp_frmtyp_forumtypeid = ? AND frmtypmsgtyp_frmmsgtyp_forummessagetypeid = ? " +
                        "FOR UPDATE";
            }

            var ps = ForumTypeMessageTypeFactory.getInstance().prepareStatement(query);

            ps.setLong(1, forumType.getPrimaryKey().getEntityId());
            ps.setLong(2, forumMessageType.getPrimaryKey().getEntityId());

            forumTypeMessageType = ForumTypeMessageTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return forumTypeMessageType;
    }

    public ForumTypeMessageType getForumTypeMessageType(ForumType forumType, ForumMessageType forumMessageType) {
        return getForumTypeMessageType(forumType, forumMessageType, EntityPermission.READ_ONLY);
    }

    public ForumTypeMessageType getForumTypeMessageTypeForUpdate(ForumType forumType, ForumMessageType forumMessageType) {
        return getForumTypeMessageType(forumType, forumMessageType, EntityPermission.READ_WRITE);
    }

    private ForumTypeMessageType getDefaultForumTypeMessageType(ForumType forumType, EntityPermission entityPermission) {
        ForumTypeMessageType forumTypeMessageType;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forumtypemessagetypes " +
                        "WHERE frmtypmsgtyp_frmtyp_forumtypeid = ? AND frmtypmsgtyp_isdefault = 1";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forumtypemessagetypes " +
                        "WHERE frmtypmsgtyp_frmtyp_forumtypeid = ? AND frmtypmsgtyp_isdefault = 1 " +
                        "FOR UPDATE";
            }

            var ps = ForumTypeMessageTypeFactory.getInstance().prepareStatement(query);

            ps.setLong(1, forumType.getPrimaryKey().getEntityId());

            forumTypeMessageType = ForumTypeMessageTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return forumTypeMessageType;
    }

    public ForumTypeMessageType getDefaultForumTypeMessageType(ForumType forumType) {
        return getDefaultForumTypeMessageType(forumType, EntityPermission.READ_ONLY);
    }

    public ForumTypeMessageType getDefaultForumTypeMessageTypeForUpdate(ForumType forumType) {
        return getDefaultForumTypeMessageType(forumType, EntityPermission.READ_WRITE);
    }

    // --------------------------------------------------------------------------------
    //   Forum Forum Threads
    // --------------------------------------------------------------------------------
    
    public ForumForumThread createForumForumThread(Forum forum, ForumThread forumThread, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var defaultForumForumThread = getDefaultForumForumThread(forumThread);
        var defaultFound = defaultForumForumThread != null;
        
        if(defaultFound && isDefault) {
            var defaultForumForumThreadValue = getDefaultForumForumThreadValueForUpdate(forumThread);
            
            defaultForumForumThreadValue.setIsDefault(false);
            updateForumForumThreadFromValue(defaultForumForumThreadValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var forumForumThread = ForumForumThreadFactory.getInstance().create(forum, forumThread,
                isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(forum.getPrimaryKey(), EventTypes.MODIFY, forumForumThread.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return forumForumThread;
    }
    
    private ForumForumThread getForumForumThread(Forum forum, ForumThread forumThread, EntityPermission entityPermission) {
        ForumForumThread forumForumThread;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forumforumthreads " +
                        "WHERE frmfrmthrd_frm_forumid = ? AND frmfrmthrd_frmthrd_forumthreadid = ? AND frmfrmthrd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forumforumthreads " +
                        "WHERE frmfrmthrd_frm_forumid = ? AND frmfrmthrd_frmthrd_forumthreadid = ? AND frmfrmthrd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ForumForumThreadFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, forum.getPrimaryKey().getEntityId());
            ps.setLong(2, forumThread.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            forumForumThread = ForumForumThreadFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumForumThread;
    }
    
    public ForumForumThread getForumForumThread(Forum forum, ForumThread forumThread) {
        return getForumForumThread(forum, forumThread, EntityPermission.READ_ONLY);
    }
    
    public ForumForumThread getForumForumThreadForUpdate(Forum forum, ForumThread forumThread) {
        return getForumForumThread(forum, forumThread, EntityPermission.READ_WRITE);
    }
    
    public ForumForumThreadValue getForumForumThreadValue(ForumForumThread forumForumThread) {
        return forumForumThread == null? null: forumForumThread.getForumForumThreadValue().clone();
    }
    
    public ForumForumThreadValue getForumForumThreadValueForUpdate(Forum forum, ForumThread forumThread) {
        return getForumForumThreadValue(getForumForumThreadForUpdate(forum, forumThread));
    }
    
    private ForumForumThread getDefaultForumForumThread(ForumThread forumThread, EntityPermission entityPermission) {
        ForumForumThread forumForumThread;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forumforumthreads " +
                        "WHERE frmfrmthrd_frmthrd_forumthreadid = ? AND frmfrmthrd_isdefault = 1 AND frmfrmthrd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forumforumthreads " +
                        "WHERE frmfrmthrd_frmthrd_forumthreadid = ? AND frmfrmthrd_isdefault = 1 AND frmfrmthrd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ForumForumThreadFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, forumThread.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            forumForumThread = ForumForumThreadFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumForumThread;
    }
    
    public ForumForumThread getDefaultForumForumThread(ForumThread forumThread) {
        return getDefaultForumForumThread(forumThread, EntityPermission.READ_ONLY);
    }
    
    public ForumForumThread getDefaultForumForumThreadForUpdate(ForumThread forumThread) {
        return getDefaultForumForumThread(forumThread, EntityPermission.READ_WRITE);
    }
    
    public ForumForumThreadValue getDefaultForumForumThreadValueForUpdate(ForumThread forumThread) {
        var forumForumThread = getDefaultForumForumThreadForUpdate(forumThread);
        
        return forumForumThread == null? null: forumForumThread.getForumForumThreadValue().clone();
    }
    
    private List<ForumForumThread> getForumForumThreadsByForum(Forum forum, EntityPermission entityPermission) {
        List<ForumForumThread> forumForumThreads;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forumforumthreads, forumthreads, forumthreaddetails " +
                        "WHERE frmfrmthrd_frm_forumid = ? AND frmfrmthrd_thrutime = ? " +
                        "AND frmfrmthrd_frmthrd_forumthreadid = frmthrd_forumthreadid AND frmthrd_lastdetailid = frmthrddt_forumthreaddetailid " +
                        "ORDER BY frmthrddt_sortorder, frmthrddt_postedtime";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forumforumthreads " +
                        "WHERE frmfrmthrd_frm_forumid = ? AND frmfrmthrd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ForumForumThreadFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, forum.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            forumForumThreads = ForumForumThreadFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumForumThreads;
    }
    
    public List<ForumForumThread> getForumForumThreadsByForum(Forum forum) {
        return getForumForumThreadsByForum(forum, EntityPermission.READ_ONLY);
    }
    
    public List<ForumForumThread> getForumForumThreadsByForumForUpdate(Forum forum) {
        return getForumForumThreadsByForum(forum, EntityPermission.READ_WRITE);
    }
    
    private List<ForumForumThread> getForumForumThreadsByForumThread(ForumThread forumThread, EntityPermission entityPermission) {
        List<ForumForumThread> forumForumThreads;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forumforumthreads, forums, forumdetails " +
                        "WHERE frmfrmthrd_frmthrd_forumthreadid = ? AND frmfrmthrd_thrutime = ? " +
                        "AND frmfrmthrd_frm_forumid = frm_forumid AND frm_lastdetailid = frmdt_forumdetailid " +
                        "ORDER BY frmdt_sortorder, frmdt_forumname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forumforumthreads " +
                        "WHERE frmfrmthrd_frmthrd_forumthreadid = ? AND frmfrmthrd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ForumForumThreadFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, forumThread.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            forumForumThreads = ForumForumThreadFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumForumThreads;
    }
    
    public List<ForumForumThread> getForumForumThreadsByForumThread(ForumThread forumThread) {
        return getForumForumThreadsByForumThread(forumThread, EntityPermission.READ_ONLY);
    }
    
    public List<ForumForumThread> getForumForumThreadsByForumThreadForUpdate(ForumThread forumThread) {
        return getForumForumThreadsByForumThread(forumThread, EntityPermission.READ_WRITE);
    }
    
    public List<ForumForumThreadTransfer> getForumForumThreadTransfers(UserVisit userVisit, Collection<ForumForumThread> forumForumThreads) {
        List<ForumForumThreadTransfer> forumForumThreadTransfers = new ArrayList<>(forumForumThreads.size());
        var forumForumThreadTransferCache = getForumTransferCaches(userVisit).getForumForumThreadTransferCache();
        
        forumForumThreads.forEach((forumForumThread) ->
                forumForumThreadTransfers.add(forumForumThreadTransferCache.getForumForumThreadTransfer(forumForumThread))
        );
        
        return forumForumThreadTransfers;
    }
    
    public List<ForumForumThreadTransfer> getForumForumThreadTransfersByForum(UserVisit userVisit, Forum forum) {
        return getForumForumThreadTransfers(userVisit, getForumForumThreadsByForum(forum));
    }
    
    public List<ForumForumThreadTransfer> getForumForumThreadTransfersByForumThread(UserVisit userVisit, ForumThread forumThread) {
        return getForumForumThreadTransfers(userVisit, getForumForumThreadsByForumThread(forumThread));
    }
    
    public ForumForumThreadTransfer getForumForumThreadTransfer(UserVisit userVisit, ForumForumThread forumForumThread) {
        return getForumTransferCaches(userVisit).getForumForumThreadTransferCache().getForumForumThreadTransfer(forumForumThread);
    }
    
    private void updateForumForumThreadFromValue(ForumForumThreadValue forumForumThreadValue, boolean checkDefault, BasePK updatedBy) {
        if(forumForumThreadValue.hasBeenModified()) {
            var forumForumThread = ForumForumThreadFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     forumForumThreadValue.getPrimaryKey());
            
            forumForumThread.setThruTime(session.START_TIME_LONG);
            forumForumThread.store();

            var forumPK = forumForumThread.getForumPK(); // Not Updated
            var forumThread = forumForumThread.getForumThread(); // Not Updated
            var isDefault = forumForumThreadValue.getIsDefault();
            var sortOrder = forumForumThreadValue.getSortOrder();
            
            if(checkDefault) {
                var defaultForumForumThread = getDefaultForumForumThread(forumThread);
                var defaultFound = defaultForumForumThread != null && !defaultForumForumThread.equals(forumForumThread);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultForumForumThreadValue = getDefaultForumForumThreadValueForUpdate(forumThread);
                    
                    defaultForumForumThreadValue.setIsDefault(false);
                    updateForumForumThreadFromValue(defaultForumForumThreadValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            forumForumThread = ForumForumThreadFactory.getInstance().create(forumPK, forumThread.getPrimaryKey(), isDefault,
                    sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(forumPK, EventTypes.MODIFY, forumForumThread.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void updateForumForumThreadFromValue(ForumForumThreadValue forumForumThreadValue, BasePK updatedBy) {
        updateForumForumThreadFromValue(forumForumThreadValue, true, updatedBy);
    }
    
    private void deleteForumForumThread(ForumForumThread forumForumThread, boolean checkForumThread, BasePK deletedBy) {
        forumForumThread.setThruTime(session.START_TIME_LONG);
        forumForumThread.store();
        
        if(checkForumThread) {
            // Check for default, and pick one if necessary
            var forumThread = forumForumThread.getForumThreadForUpdate();
            var defaultForumForumThread = getDefaultForumForumThread(forumThread);
            if(defaultForumForumThread == null) {
                var forumForumThreads = getForumForumThreadsByForumThreadForUpdate(forumThread);
                
                if(!forumForumThreads.isEmpty()) {
                    var iter = forumForumThreads.iterator();
                    if(iter.hasNext()) {
                        defaultForumForumThread = iter.next();
                    }
                    var forumForumThreadValue = defaultForumForumThread.getForumForumThreadValue().clone();
                    
                    forumForumThreadValue.setIsDefault(true);
                    updateForumForumThreadFromValue(forumForumThreadValue, false, deletedBy);
                } else {
                    deleteForumThread(forumThread, deletedBy);
                }
            }
        }
        
        sendEvent(forumForumThread.getForumPK(), EventTypes.MODIFY, forumForumThread.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteForumForumThread(ForumForumThread forumForumThread, BasePK deletedBy) {
        deleteForumForumThread(forumForumThread, true, deletedBy);
    }
    
    private void deleteForumForumThreads(List<ForumForumThread> forumForumThreads, boolean checkForumThread, BasePK deletedBy) {
        forumForumThreads.forEach((forumForumThread) -> {
            deleteForumForumThread(forumForumThread, checkForumThread, deletedBy);
        });
    }
    
    public void deleteForumForumThreads(List<ForumForumThread> forumForumThreads, BasePK deletedBy) {
        deleteForumForumThreads(forumForumThreads, true, deletedBy);
    }
    
    public void deleteForumForumThreadsByForum(Forum forum, BasePK deletedBy) {
        deleteForumForumThreads(getForumForumThreadsByForumForUpdate(forum), deletedBy);
    }
    
    public void deleteForumForumThreadsByForumThread(ForumThread forumThread, BasePK deletedBy) {
        deleteForumForumThreads(getForumForumThreadsByForumThreadForUpdate(forumThread), false, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Threads
    // --------------------------------------------------------------------------------
    
    public ForumThread createForumThread(Forum forum, Icon icon, Long postedTime, Integer sortOrder, BasePK createdBy) {
        var sequenceControl = Session.getModelController(SequenceControl.class);
        var sequence = forum == null? null: forum.getLastDetail().getForumThreadSequence();
        
        if(sequence == null) {
            sequence = sequenceControl.getDefaultSequenceUsingNames(SequenceTypes.FORUM_THREAD.name());
        }
        
        return createForumThread(SequenceGeneratorLogic.getInstance().getNextSequenceValue(sequence), icon, postedTime, sortOrder, createdBy);
    }
    
    public ForumThread createForumThread(String forumThreadName, Icon icon, Long postedTime, Integer sortOrder, BasePK createdBy) {
        var forumThread = ForumThreadFactory.getInstance().create();
        var forumThreadDetail = ForumThreadDetailFactory.getInstance().create(forumThread, forumThreadName,
                icon, postedTime, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        forumThread = ForumThreadFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                forumThread.getPrimaryKey());
        forumThread.setActiveDetail(forumThreadDetail);
        forumThread.setLastDetail(forumThreadDetail);
        forumThread.store();
        
        sendEvent(forumThread.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return forumThread;
    }
    
    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ForumThread */
    public ForumThread getForumThreadByEntityInstance(EntityInstance entityInstance) {
        var pk = new ForumThreadPK(entityInstance.getEntityUniqueId());
        var forumThread = ForumThreadFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);

        return forumThread;
    }

    private ForumThread getForumThreadByName(String forumThreadName, EntityPermission entityPermission) {
        ForumThread forumThread;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forumthreads, forumthreaddetails " +
                        "WHERE frmthrd_activedetailid = frmthrddt_forumthreaddetailid AND frmthrddt_forumthreadname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forumthreads, forumthreaddetails " +
                        "WHERE frmthrd_activedetailid = frmthrddt_forumthreaddetailid AND frmthrddt_forumthreadname = ? " +
                        "FOR UPDATE";
            }

            var ps = ForumThreadFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, forumThreadName);
            
            forumThread = ForumThreadFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumThread;
    }
    
    public ForumThread getForumThreadByName(String forumThreadName) {
        return getForumThreadByName(forumThreadName, EntityPermission.READ_ONLY);
    }
    
    public ForumThread getForumThreadByNameForUpdate(String forumThreadName) {
        return getForumThreadByName(forumThreadName, EntityPermission.READ_WRITE);
    }
    
    public ForumThreadDetailValue getForumThreadDetailValueForUpdate(ForumThread forumThread) {
        return forumThread == null? null: forumThread.getLastDetailForUpdate().getForumThreadDetailValue().clone();
    }
    
    public ForumThreadDetailValue getForumThreadDetailValueByNameForUpdate(String forumThreadName) {
        return getForumThreadDetailValueForUpdate(getForumThreadByNameForUpdate(forumThreadName));
    }
    
    public long countForumThreadsByForum(Forum forum, boolean includeFutureForumThreads) {
        long count;
        
        if (includeFutureForumThreads) {
            count = session.queryForLong(
                    "SELECT COUNT(*) "
                    + "FROM forumforumthreads "
                    + "WHERE frmfrmthrd_frm_forumid = ? AND frmfrmthrd_thrutime = ?",
                    forum, Session.MAX_TIME);
        } else {
            count = session.queryForLong(
                    "SELECT COUNT(*) "
                    + "FROM forumforumthreads, forumthreads, forumthreaddetails "
                    + "WHERE frmfrmthrd_frm_forumid = ? AND frmfrmthrd_thrutime = ? "
                    + "AND frmfrmthrd_frmthrd_forumthreadid = frmthrd_forumthreadid "
                    + "AND frmthrd_activedetailid = frmthrddt_forumthreaddetailid "
                    + "AND frmthrddt_postedtime <= ?",
                    forum, Session.MAX_TIME, session.START_TIME);
        }

        return count;
    }
    
    private List<ForumThread> getForumThreadsByForum(Forum forum, boolean includeFutureForumThreads, EntityPermission entityPermission) {
        List<ForumThread> forumThreads;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forumforumthreads, forumthreads, forumthreaddetails, componentvendors, componentvendordetails, entitytypes, entitytypedetails, entityinstances, entitytimes " +
                        "WHERE frmfrmthrd_frm_forumid = ? AND frmfrmthrd_thrutime = ? " +
                        "AND frmfrmthrd_frmthrd_forumthreadid = frmthrd_forumthreadid " +
                        "AND frmthrd_activedetailid = frmthrddt_forumthreaddetailid " +
                        "AND cvnd_activedetailid = cvndd_componentvendordetailid AND cvndd_componentvendorname = ? " +
                        "AND ent_activedetailid = entdt_entitytypedetailid AND cvnd_componentvendorid = entdt_cvnd_componentvendorid AND entdt_entitytypename = ? " +
                        "AND ent_entitytypeid = eni_ent_entitytypeid AND frmthrd_forumthreadid = eni_entityuniqueid " +
                        "AND eni_entityinstanceid = etim_eni_entityinstanceid " +
                        (includeFutureForumThreads? "": "AND frmthrddt_postedtime <= ? ") +
                        "ORDER BY frmthrddt_sortorder, frmthrddt_postedtime DESC, etim_createdtime DESC" +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forumforumthreads, forumthreads, forumthreaddetails " +
                        "WHERE frmfrmthrd_frm_forumid = ? AND frmfrmthrd_thrutime = ? " +
                        "AND frmfrmthrd_frmthrd_forumthreadid = frmthrd_forumthreadid " +
                        "AND frmthrd_activedetailid = frmthrddt_forumthreaddetailid " +
                        "FOR UPDATE";
            }

            var ps = ForumThreadFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, forum.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            ps.setString(3, ComponentVendors.ECHO_THREE.name());
            ps.setString(4, EntityTypes.ForumThread.name());
            if(!includeFutureForumThreads) {
                ps.setLong(5, session.START_TIME);
            }
            
            forumThreads = ForumThreadFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumThreads;
    }
    
    public List<ForumThread> getForumThreadsByForum(Forum forum, boolean includeFutureForumThreads) {
        return getForumThreadsByForum(forum, includeFutureForumThreads, EntityPermission.READ_ONLY);
    }
    
    public List<ForumThread> getForumThreadsByForumForUpdate(Forum forum, boolean includeFutureForumThreads) {
        return getForumThreadsByForum(forum, includeFutureForumThreads, EntityPermission.READ_WRITE);
    }
    
    public ForumThreadTransfer getForumThreadTransfer(UserVisit userVisit, ForumThread forumThread) {
        return getForumTransferCaches(userVisit).getForumThreadTransferCache().getForumThreadTransfer(forumThread);
    }
    
    public List<ForumThreadTransfer> getForumThreadTransfers(UserVisit userVisit, Collection<ForumThread> forumThreads) {
        List<ForumThreadTransfer> forumThreadTransfers = new ArrayList<>(forumThreads.size());
        var forumThreadTransferCache = getForumTransferCaches(userVisit).getForumThreadTransferCache();
        
        forumThreads.forEach((forumThread) ->
                forumThreadTransfers.add(forumThreadTransferCache.getForumThreadTransfer(forumThread))
        );
        
        return forumThreadTransfers;
    }
    
    public List<ForumThreadTransfer> getForumThreadTransfersByForum(UserVisit userVisit, Forum forum, boolean includeFutureForumThreads) {
        return getForumThreadTransfers(userVisit, getForumThreadsByForum(forum, includeFutureForumThreads));
    }
    
    public void updateForumThreadFromValue(ForumThreadDetailValue forumThreadDetailValue, BasePK updatedBy) {
        if(forumThreadDetailValue.hasBeenModified()) {
            var forumThread = ForumThreadFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    forumThreadDetailValue.getForumThreadPK());
            var forumThreadDetail = forumThread.getActiveDetailForUpdate();
            
            forumThreadDetail.setThruTime(session.START_TIME_LONG);
            forumThreadDetail.store();

            var forumThreadPK = forumThreadDetail.getForumThreadPK();
            var forumThreadName = forumThreadDetail.getForumThreadName(); // Not updated
            var iconPK = forumThreadDetailValue.getIconPK();
            var postedTime = forumThreadDetailValue.getPostedTime();
            var sortOrder = forumThreadDetailValue.getSortOrder();
            
            forumThreadDetail = ForumThreadDetailFactory.getInstance().create(forumThreadPK, forumThreadName,
                    iconPK, postedTime, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            forumThread.setActiveDetail(forumThreadDetail);
            forumThread.setLastDetail(forumThreadDetail);
            
            sendEvent(forumThreadPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deleteForumThread(ForumThread forumThread, BasePK deletedBy) {
        deleteForumMessagesByForumThread(forumThread, deletedBy);
        deleteForumForumThreadsByForumThread(forumThread, deletedBy);

        var forumThreadDetail = forumThread.getLastDetailForUpdate();
        forumThreadDetail.setThruTime(session.START_TIME_LONG);
        forumThread.setActiveDetail(null);
        forumThread.store();
        
        sendEvent(forumThread.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteForumThreads(List<ForumThread> forumThreads, BasePK deletedBy) {
        forumThreads.forEach((forumThread) -> 
                deleteForumThread(forumThread, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Messages
    // --------------------------------------------------------------------------------
    
    public ForumMessage createForumMessage(ForumThread forumThread, ForumMessageType forumMessageType,
            ForumMessage parentForumMessage, Icon icon, Long postedTime, BasePK createdBy) {
        var sequenceControl = Session.getModelController(SequenceControl.class);
        var forumForumThread = getDefaultForumForumThread(forumThread);
        var forum = forumForumThread == null? null: forumForumThread.getForum();
        var sequence = forum == null? null: forum.getLastDetail().getForumThreadSequence();
        
        if(sequence == null) {
            sequence = sequenceControl.getDefaultSequenceUsingNames(SequenceTypes.FORUM_MESSAGE.name());
        }
        
        return createForumMessage(SequenceGeneratorLogic.getInstance().getNextSequenceValue(sequence), forumThread, forumMessageType, parentForumMessage,
                icon, postedTime, createdBy);
    }
    
    public ForumMessage createForumMessage(String forumMessageName, ForumThread forumThread, ForumMessageType forumMessageType,
            ForumMessage parentForumMessage, Icon icon, Long postedTime, BasePK createdBy) {
        var forumMessage = ForumMessageFactory.getInstance().create();
        var forumMessageDetail = ForumMessageDetailFactory.getInstance().create(forumMessage,
                forumMessageName, forumThread, forumMessageType, parentForumMessage, icon, postedTime, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        // Convert to R/W
        forumMessage = ForumMessageFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                forumMessage.getPrimaryKey());
        forumMessage.setActiveDetail(forumMessageDetail);
        forumMessage.setLastDetail(forumMessageDetail);
        forumMessage.store();

        var forumMessagePK = forumMessage.getPrimaryKey();
        sendEvent(forumMessagePK, EventTypes.CREATE, null, null, createdBy);
        if(parentForumMessage != null) {
            sendEvent(forumThread.getPrimaryKey(), EventTypes.TOUCH, forumMessagePK, EventTypes.CREATE, createdBy);
            touchForumsByForumThread(forumThread, forumMessagePK, EventTypes.CREATE, createdBy);
        }
        
        return forumMessage;
    }
    
    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ForumMessage */
    public ForumMessage getForumMessageByEntityInstance(EntityInstance entityInstance) {
        var pk = new ForumMessagePK(entityInstance.getEntityUniqueId());
        var forumMessage = ForumMessageFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);

        return forumMessage;
    }

    public long countForumMessages() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM forummessages, forummessagedetails " +
                "WHERE frmmsg_activedetailid = frmmsgdt_forummessagedetailid");
    }

    private ForumMessage getForumMessageByName(String forumMessageName, EntityPermission entityPermission) {
        ForumMessage forumMessage;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forummessages, forummessagedetails " +
                        "WHERE frmmsg_activedetailid = frmmsgdt_forummessagedetailid AND frmmsgdt_forummessagename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forummessages, forummessagedetails " +
                        "WHERE frmmsg_activedetailid = frmmsgdt_forummessagedetailid AND frmmsgdt_forummessagename = ? " +
                        "FOR UPDATE";
            }

            var ps = ForumMessageFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, forumMessageName);
            
            forumMessage = ForumMessageFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumMessage;
    }
    
    public ForumMessage getForumMessageByName(String forumMessageName) {
        return getForumMessageByName(forumMessageName, EntityPermission.READ_ONLY);
    }
    
    public ForumMessage getForumMessageByNameForUpdate(String forumMessageName) {
        return getForumMessageByName(forumMessageName, EntityPermission.READ_WRITE);
    }
    
    public ForumMessageDetailValue getForumMessageDetailValueForUpdate(ForumMessage forumMessage) {
        return forumMessage == null? null: forumMessage.getLastDetailForUpdate().getForumMessageDetailValue().clone();
    }
    
    public ForumMessageDetailValue getForumMessageDetailValueByNameForUpdate(String forumMessageName) {
        return getForumMessageDetailValueForUpdate(getForumMessageByNameForUpdate(forumMessageName));
    }
    
    public long countForumMessagesByForumThread(ForumThread forumThread) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM forummessages, forummessagedetails " +
                "WHERE frmmsg_activedetailid = frmmsgdt_forummessagedetailid AND frmmsgdt_frmthrd_forumthreadid = ?",
                forumThread);
    }
    
    private List<ForumMessage> getForumMessagesByForumThread(ForumThread forumThread, EntityPermission entityPermission) {
        List<ForumMessage> forumMessages;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forummessages, forummessagedetails " +
                        "WHERE frmmsg_activedetailid = frmmsgdt_forummessagedetailid AND frmmsgdt_frmthrd_forumthreadid = ? " +
                        "ORDER BY frmmsgdt_postedtime, frmmsgdt_forummessagename " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forummessages, forummessagedetails " +
                        "WHERE frmmsg_activedetailid = frmmsgdt_forummessagedetailid AND frmmsgdt_frmthrd_forumthreadid = ? " +
                        "FOR UPDATE";
            }

            var ps = ForumMessageFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, forumThread.getPrimaryKey().getEntityId());
            
            forumMessages = ForumMessageFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumMessages;
    }
    
    public List<ForumMessage> getForumMessagesByForumThread(ForumThread forumThread) {
        return getForumMessagesByForumThread(forumThread, EntityPermission.READ_ONLY);
    }
    
    public List<ForumMessage> getForumMessagesByForumThreadForUpdate(ForumThread forumThread) {
        return getForumMessagesByForumThread(forumThread, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getForumMessagesByParentForumMessageQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM forummessages, forummessagedetails " +
                "WHERE frmmsg_activedetailid = frmmsgdt_forummessagedetailid AND frmmsgdt_parentforummessageid = ? " +
                "ORDER BY frmmsgdt_sortorder, frmmsgdt_forummessagename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM forummessages, forummessagedetails " +
                "WHERE frmmsg_activedetailid = frmmsgdt_forummessagedetailid AND frmmsgdt_parentforummessageid = ? " +
                "FOR UPDATE");
        getForumMessagesByParentForumMessageQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ForumMessage> getForumMessagesByParentForumMessage(ForumMessage parentForumMessage,
            EntityPermission entityPermission) {
        return ForumMessageFactory.getInstance().getEntitiesFromQuery(entityPermission, getForumMessagesByParentForumMessageQueries,
                parentForumMessage);
    }

    public List<ForumMessage> getForumMessagesByParentForumMessage(ForumMessage parentForumMessage) {
        return getForumMessagesByParentForumMessage(parentForumMessage, EntityPermission.READ_ONLY);
    }

    public List<ForumMessage> getForumMessagesByParentForumMessageForUpdate(ForumMessage parentForumMessage) {
        return getForumMessagesByParentForumMessage(parentForumMessage, EntityPermission.READ_WRITE);
    }

    public ForumMessageTransfer getForumMessageTransfer(UserVisit userVisit, ForumMessage forumMessage) {
        return getForumTransferCaches(userVisit).getForumMessageTransferCache().getForumMessageTransfer(forumMessage);
    }
    
    public List<ForumMessageTransfer> getForumMessageTransfers(UserVisit userVisit, Collection<ForumMessage> forumMessages) {
        List<ForumMessageTransfer> forumMessageTransfers = new ArrayList<>(forumMessages.size());
        var forumMessageTransferCache = getForumTransferCaches(userVisit).getForumMessageTransferCache();
        
        forumMessages.forEach((forumMessage) ->
                forumMessageTransfers.add(forumMessageTransferCache.getForumMessageTransfer(forumMessage))
        );
        
        return forumMessageTransfers;
    }
    
    public List<ForumMessageTransfer> getForumMessageTransfersByForumThread(UserVisit userVisit, ForumThread forumThread) {
        return getForumMessageTransfers(userVisit, getForumMessagesByForumThread(forumThread));
    }
    
    public void updateForumMessageFromValue(ForumMessageDetailValue forumMessageDetailValue, BasePK updatedBy) {
        if(forumMessageDetailValue.hasBeenModified()) {
            var forumMessage = ForumMessageFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    forumMessageDetailValue.getForumMessagePK());
            var forumMessageDetail = forumMessage.getActiveDetailForUpdate();
            
            forumMessageDetail.setThruTime(session.START_TIME_LONG);
            forumMessageDetail.store();

            var forumMessagePK = forumMessageDetail.getForumMessagePK();
            var forumMessageName = forumMessageDetail.getForumMessageName(); // Not updated
            var forumThreadPK = forumMessageDetail.getForumThreadPK(); // Not updated
            var forumMessageTypePK = forumMessageDetail.getForumMessageTypePK(); // Not updated
            var parentForumMessagePK = forumMessageDetail.getParentForumMessagePK(); // Not updated
            var iconPK = forumMessageDetailValue.getIconPK();
            var postedTime = forumMessageDetailValue.getPostedTime();
            
            forumMessageDetail = ForumMessageDetailFactory.getInstance().create(forumMessagePK, forumMessageName,
                    forumThreadPK, forumMessageTypePK, parentForumMessagePK, iconPK, postedTime, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            forumMessage.setActiveDetail(forumMessageDetail);
            forumMessage.setLastDetail(forumMessageDetail);
            
            sendEvent(forumMessagePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deleteForumMessage(ForumMessage forumMessage, BasePK deletedBy) {
        removeForumMessageStatusByForumMessage(forumMessage);
        deleteForumMessagesByParentForumMessage(forumMessage, deletedBy);
        deleteForumMessageRolesByForumMessage(forumMessage, deletedBy);
        deleteForumMessagePartsByForumMessage(forumMessage, deletedBy);
        deleteForumMessageAttachmentsByForumMessage(forumMessage, deletedBy);

        var forumMessageDetail = forumMessage.getLastDetailForUpdate();
        forumMessageDetail.setThruTime(session.START_TIME_LONG);
        forumMessage.setActiveDetail(null);
        forumMessage.store();
        
        sendEvent(forumMessage.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteForumMessages(List<ForumMessage> forumMessages, BasePK deletedBy) {
        forumMessages.forEach((forumMessage) -> 
                deleteForumMessage(forumMessage, deletedBy)
        );
    }

    private void deleteForumMessagesByParentForumMessage(ForumMessage parentForumMessage, BasePK deletedBy) {
        deleteForumMessages(getForumMessagesByParentForumMessageForUpdate(parentForumMessage), deletedBy);
    }

    public void deleteForumMessagesByForumThread(ForumThread forumThread, BasePK deletedBy) {
        deleteForumMessages(getForumMessagesByForumThreadForUpdate(forumThread), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Message Statuses
    // --------------------------------------------------------------------------------

    public ForumMessageStatus createForumMessageStatus(ForumMessage forumMessage) {
        return ForumMessageStatusFactory.getInstance().create(forumMessage, 0);
    }

    private static final Map<EntityPermission, String> getForumMessageStatusQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM forummessagestatuses " +
                "WHERE frmmsgst_frmmsg_forummessageid = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM forummessagestatuses " +
                "WHERE frmmsgst_frmmsg_forummessageid = ? " +
                "FOR UPDATE");
        getForumMessageStatusQueries = Collections.unmodifiableMap(queryMap);
    }

    private ForumMessageStatus getForumMessageStatus(ForumMessage forumMessage, EntityPermission entityPermission) {
        return ForumMessageStatusFactory.getInstance().getEntityFromQuery(entityPermission, getForumMessageStatusQueries,
                forumMessage);
    }

    public ForumMessageStatus getForumMessageStatus(ForumMessage forumMessage) {
        return getForumMessageStatus(forumMessage, EntityPermission.READ_ONLY);
    }

    public ForumMessageStatus getForumMessageStatusForUpdate(ForumMessage forumMessage) {
        return getForumMessageStatus(forumMessage, EntityPermission.READ_WRITE);
    }

    public ForumMessageStatus getOrCreateForumMessageStatusForUpdate(ForumMessage forumMessage) {
        var forumMessageStatus = getForumMessageStatusForUpdate(forumMessage);

        if(forumMessageStatus == null) {
            createForumMessageStatus(forumMessage);
            forumMessageStatus = getForumMessageStatusForUpdate(forumMessage);
        }

        return forumMessageStatus;
    }

    public void removeForumMessageStatusByForumMessage(ForumMessage forumMessage) {
        var forumMessageStatus = getForumMessageStatusForUpdate(forumMessage);

        if(forumMessageStatus != null) {
            forumMessageStatus.remove();
        }
    }

    // --------------------------------------------------------------------------------
    //   Forum Message Attachments
    // --------------------------------------------------------------------------------

    public ForumMessageAttachment createForumMessageAttachment(ForumMessage forumMessage, Integer forumMessageAttachmentSequence, MimeType mimeType,
            BasePK createdBy) {
        var forumMessageAttachment = ForumMessageAttachmentFactory.getInstance().create();
        var forumMessageAttachmentDetail = ForumMessageAttachmentDetailFactory.getInstance().create(forumMessageAttachment,
                forumMessage, forumMessageAttachmentSequence, mimeType, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        forumMessageAttachment = ForumMessageAttachmentFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, forumMessageAttachment.getPrimaryKey());
        forumMessageAttachment.setActiveDetail(forumMessageAttachmentDetail);
        forumMessageAttachment.setLastDetail(forumMessageAttachmentDetail);
        forumMessageAttachment.store();

        sendEvent(forumMessage.getPrimaryKey(), EventTypes.MODIFY, forumMessageAttachment.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return forumMessageAttachment;
    }

    private static final Map<EntityPermission, String> getForumMessageAttachmentBySequenceQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM forummessageattachments, forummessageattachmentdetails "
                + "WHERE frmmsgatt_activedetailid = frmmsgattdt_forummessageattachmentdetailid AND frmmsgattdt_frmmsg_forummessageid = ? "
                + "AND frmmsgattdt_forummessageattachmentsequence = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM forummessageattachments, forummessageattachmentdetails "
                + "WHERE frmmsgatt_activedetailid = frmmsgattdt_forummessageattachmentdetailid AND frmmsgattdt_frmmsg_forummessageid = ? "
                + "AND frmmsgattdt_forummessageattachmentsequence = ? "
                + "FOR UPDATE");
        getForumMessageAttachmentBySequenceQueries = Collections.unmodifiableMap(queryMap);
    }

    private ForumMessageAttachment getForumMessageAttachmentBySequence(ForumMessage forumMessage, Integer forumMessageAttachmentSequence, EntityPermission entityPermission) {
        return ForumMessageAttachmentFactory.getInstance().getEntityFromQuery(entityPermission, getForumMessageAttachmentBySequenceQueries,
                forumMessage, forumMessageAttachmentSequence);
    }

    public ForumMessageAttachment getForumMessageAttachmentBySequence(ForumMessage forumMessage, Integer forumMessageAttachmentSequence) {
        return getForumMessageAttachmentBySequence(forumMessage, forumMessageAttachmentSequence, EntityPermission.READ_ONLY);
    }

    public ForumMessageAttachment getForumMessageAttachmentBySequenceForUpdate(ForumMessage forumMessage, Integer forumMessageAttachmentSequence) {
        return getForumMessageAttachmentBySequence(forumMessage, forumMessageAttachmentSequence, EntityPermission.READ_WRITE);
    }

    public ForumMessageAttachmentDetailValue getForumMessageAttachmentDetailValueForUpdate(ForumMessageAttachment forumMessageAttachment) {
        return forumMessageAttachment == null ? null : forumMessageAttachment.getLastDetailForUpdate().getForumMessageAttachmentDetailValue().clone();
    }

    public ForumMessageAttachmentDetailValue getForumMessageAttachmentDetailValueBySequenceForUpdate(ForumMessage forumMessage, Integer forumMessageAttachmentSequence) {
        return getForumMessageAttachmentDetailValueForUpdate(getForumMessageAttachmentBySequenceForUpdate(forumMessage, forumMessageAttachmentSequence));
    }

    private static final Map<EntityPermission, String> getForumMessageAttachmentByForumMessageQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM forummessageattachments, forummessageattachmentdetails "
                + "WHERE frmmsgatt_activedetailid = frmmsgattdt_forummessageattachmentdetailid AND frmmsgattdt_frmmsg_forummessageid = ? "
                + "ORDER BY frmmsgattdt_forummessageattachmentsequence");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM forummessageattachments, forummessageattachmentdetails "
                + "WHERE frmmsgatt_activedetailid = frmmsgattdt_forummessageattachmentdetailid AND frmmsgattdt_frmmsg_forummessageid = ? "
                + "FOR UPDATE");
        getForumMessageAttachmentByForumMessageQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ForumMessageAttachment> getForumMessageAttachmentsByForumMessage(ForumMessage forumMessage, EntityPermission entityPermission) {
        return ForumMessageAttachmentFactory.getInstance().getEntitiesFromQuery(entityPermission, getForumMessageAttachmentByForumMessageQueries,
                forumMessage);
    }

    public List<ForumMessageAttachment> getForumMessageAttachmentsByForumMessage(ForumMessage forumMessage) {
        return getForumMessageAttachmentsByForumMessage(forumMessage, EntityPermission.READ_ONLY);
    }

    public List<ForumMessageAttachment> getForumMessageAttachmentsByForumMessageForUpdate(ForumMessage forumMessage) {
        return getForumMessageAttachmentsByForumMessage(forumMessage, EntityPermission.READ_WRITE);
    }

    public ForumMessageAttachmentTransfer getForumMessageAttachmentTransfer(UserVisit userVisit, ForumMessageAttachment forumMessageAttachment) {
        return getForumTransferCaches(userVisit).getForumMessageAttachmentTransferCache().getForumMessageAttachmentTransfer(forumMessageAttachment);
    }

    public List<ForumMessageAttachmentTransfer> getForumMessageAttachmentTransfers(UserVisit userVisit, Collection<ForumMessageAttachment> forumMessageAttachments) {
        List<ForumMessageAttachmentTransfer> forumMessageAttachmentTransfers = new ArrayList<>(forumMessageAttachments.size());
        var forumMessageAttachmentTransferCache = getForumTransferCaches(userVisit).getForumMessageAttachmentTransferCache();

        forumMessageAttachments.forEach((forumMessageAttachment) ->
                forumMessageAttachmentTransfers.add(forumMessageAttachmentTransferCache.getForumMessageAttachmentTransfer(forumMessageAttachment))
        );

        return forumMessageAttachmentTransfers;
    }

    public List<ForumMessageAttachmentTransfer> getForumMessageAttachmentTransfersByForumMessage(UserVisit userVisit, ForumMessage forumMessage) {
        return getForumMessageAttachmentTransfers(userVisit, getForumMessageAttachmentsByForumMessage(forumMessage));
    }

    public void updateForumMessageAttachmentFromValue(ForumMessageAttachmentDetailValue forumMessageAttachmentDetailValue, BasePK updatedBy) {
        if(forumMessageAttachmentDetailValue.hasBeenModified()) {
            var forumMessageAttachment = ForumMessageAttachmentFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     forumMessageAttachmentDetailValue.getForumMessageAttachmentPK());
            var forumMessageAttachmentDetail = forumMessageAttachment.getActiveDetailForUpdate();

            forumMessageAttachmentDetail.setThruTime(session.START_TIME_LONG);
            forumMessageAttachmentDetail.store();

            var forumMessageAttachmentPK = forumMessageAttachmentDetail.getForumMessageAttachmentPK(); // Not updated
            var forumMessagePK = forumMessageAttachmentDetail.getForumMessagePK(); // Not updated
            var forumMessageAttachmentSequence = forumMessageAttachmentDetail.getForumMessageAttachmentSequence(); // Not updated
            var mimeTypePK = forumMessageAttachmentDetailValue.getMimeTypePK();

            forumMessageAttachmentDetail = ForumMessageAttachmentDetailFactory.getInstance().create(forumMessageAttachmentPK, forumMessagePK, forumMessageAttachmentSequence,
                    mimeTypePK, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            forumMessageAttachment.setActiveDetail(forumMessageAttachmentDetail);
            forumMessageAttachment.setLastDetail(forumMessageAttachmentDetail);

            sendEvent(forumMessagePK, EventTypes.MODIFY, forumMessageAttachmentPK, EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteForumMessageAttachment(ForumMessageAttachment forumMessageAttachment, BasePK deletedBy) {
        deleteForumMessageAttachmentDescriptionsByForumMessageAttachment(forumMessageAttachment, deletedBy);

        var forumMessageAttachmentDetail = forumMessageAttachment.getLastDetailForUpdate();
        forumMessageAttachmentDetail.setThruTime(session.START_TIME_LONG);
        forumMessageAttachmentDetail.store();
        forumMessageAttachment.setActiveDetail(null);

        var entityAttributeTypeName = forumMessageAttachmentDetail.getMimeType().getLastDetail().getEntityAttributeType().getEntityAttributeTypeName();
        if(entityAttributeTypeName.equals(EntityAttributeTypes.BLOB.name())) {
            deleteForumMessageBlobAttachmentByForumMessageAttachment(forumMessageAttachment, deletedBy);
        } else if(entityAttributeTypeName.equals(EntityAttributeTypes.CLOB.name())) {
            deleteForumMessageClobAttachmentByForumMessageAttachment(forumMessageAttachment, deletedBy);
        }

        sendEvent(forumMessageAttachmentDetail.getForumMessagePK(), EventTypes.MODIFY, forumMessageAttachment.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteForumMessageAttachments(List<ForumMessageAttachment> forumMessageAttachments, BasePK deletedBy) {
        forumMessageAttachments.forEach((forumMessageAttachment) -> 
                deleteForumMessageAttachment(forumMessageAttachment, deletedBy)
        );
    }

    public void deleteForumMessageAttachmentsByForumMessage(ForumMessage forumMessage, BasePK deletedBy) {
        deleteForumMessageAttachments(getForumMessageAttachmentsByForumMessageForUpdate(forumMessage), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   ForumMessageAttachment Utilities
    // --------------------------------------------------------------------------------

    private void verifyForumMessageAttachmentMimeType(ForumMessageAttachment forumMessageAttachment, String entityAttributeTypeName) {
        var forumMessageAttachmentEntityAttributeTypeName = forumMessageAttachment.getLastDetail().getMimeType().getLastDetail().getEntityAttributeType().getEntityAttributeTypeName();

        if(!forumMessageAttachmentEntityAttributeTypeName.equals(entityAttributeTypeName)) {
            throw new IllegalArgumentException("ForumMessageAttachment entityAttributeTypeName is " + forumMessageAttachmentEntityAttributeTypeName + ", expected " + entityAttributeTypeName);
        }
    }

    // --------------------------------------------------------------------------------
    //   Forum Message Attachment Blobs
    // --------------------------------------------------------------------------------

    public ForumMessageBlobAttachment createForumMessageBlobAttachment(ForumMessageAttachment forumMessageAttachment, ByteArray blob, BasePK createdBy) {
        verifyForumMessageAttachmentMimeType(forumMessageAttachment, EntityAttributeTypes.BLOB.name());

        var forumMessageAttachmentBlob = ForumMessageBlobAttachmentFactory.getInstance().create(forumMessageAttachment, blob, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(forumMessageAttachment.getLastDetail().getForumMessagePK(), EventTypes.MODIFY, forumMessageAttachmentBlob.getPrimaryKey(), EventTypes.MODIFY, createdBy);

        return forumMessageAttachmentBlob;
    }

    private static final Map<EntityPermission, String> getForumMessageBlobAttachmentQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM forummessageblobattachments "
                + "WHERE frmmsgbatt_frmmsgatt_forummessageattachmentid = ? AND frmmsgbatt_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM forummessageblobattachments "
                + "WHERE frmmsgbatt_frmmsgatt_forummessageattachmentid = ? AND frmmsgbatt_thrutime = ? "
                + "FOR UPDATE");
        getForumMessageBlobAttachmentQueries = Collections.unmodifiableMap(queryMap);
    }

    private ForumMessageBlobAttachment getForumMessageBlobAttachment(ForumMessageAttachment forumMessageAttachment, EntityPermission entityPermission) {
        return ForumMessageBlobAttachmentFactory.getInstance().getEntityFromQuery(entityPermission, getForumMessageBlobAttachmentQueries,
                forumMessageAttachment, Session.MAX_TIME);
    }

    public ForumMessageBlobAttachment getForumMessageBlobAttachment(ForumMessageAttachment forumMessageAttachment) {
        return getForumMessageBlobAttachment(forumMessageAttachment, EntityPermission.READ_ONLY);
    }

    public ForumMessageBlobAttachment getForumMessageBlobAttachmentForUpdate(ForumMessageAttachment forumMessageAttachment) {
        return getForumMessageBlobAttachment(forumMessageAttachment, EntityPermission.READ_WRITE);
    }

    public ForumMessageBlobAttachmentValue getForumMessageBlobAttachmentValue(ForumMessageBlobAttachment forumMessageAttachmentBlob) {
        return forumMessageAttachmentBlob == null ? null : forumMessageAttachmentBlob.getForumMessageBlobAttachmentValue().clone();
    }

    public ForumMessageBlobAttachmentValue getForumMessageBlobAttachmentValueForUpdate(ForumMessageAttachment forumMessageAttachment) {
        return getForumMessageBlobAttachmentValue(getForumMessageBlobAttachmentForUpdate(forumMessageAttachment));
    }

    public void updateForumMessageBlobAttachmentFromValue(ForumMessageBlobAttachmentValue forumMessageAttachmentBlobValue, BasePK updatedBy) {
        if(forumMessageAttachmentBlobValue.hasBeenModified()) {
            var forumMessageAttachmentBlob = ForumMessageBlobAttachmentFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    forumMessageAttachmentBlobValue.getPrimaryKey());

            forumMessageAttachmentBlob.setThruTime(session.START_TIME_LONG);
            forumMessageAttachmentBlob.store();

            var forumMessageAttachmentPK = forumMessageAttachmentBlob.getForumMessageAttachmentPK(); // Not updated
            var blob = forumMessageAttachmentBlobValue.getBlob();

            forumMessageAttachmentBlob = ForumMessageBlobAttachmentFactory.getInstance().create(forumMessageAttachmentPK, blob, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            sendEvent(forumMessageAttachmentBlob.getForumMessageAttachment().getLastDetail().getForumMessagePK(), EventTypes.MODIFY, forumMessageAttachmentBlob.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteForumMessageBlobAttachment(ForumMessageBlobAttachment forumMessageAttachmentBlob, BasePK deletedBy) {
        forumMessageAttachmentBlob.setThruTime(session.START_TIME_LONG);

        sendEvent(forumMessageAttachmentBlob.getForumMessageAttachment().getLastDetail().getForumMessagePK(), EventTypes.MODIFY, forumMessageAttachmentBlob.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteForumMessageBlobAttachmentByForumMessageAttachment(ForumMessageAttachment forumMessageAttachment, BasePK deletedBy) {
        var forumMessageAttachmentBlob = getForumMessageBlobAttachmentForUpdate(forumMessageAttachment);

        if(forumMessageAttachmentBlob != null) {
            deleteForumMessageBlobAttachment(forumMessageAttachmentBlob, deletedBy);
        }
    }

    // --------------------------------------------------------------------------------
    //   Forum Message Attachment Clobs
    // --------------------------------------------------------------------------------

    public ForumMessageClobAttachment createForumMessageClobAttachment(ForumMessageAttachment forumMessageAttachment, String clob, BasePK createdBy) {
        verifyForumMessageAttachmentMimeType(forumMessageAttachment, EntityAttributeTypes.CLOB.name());

        var forumMessageAttachmentClob = ForumMessageClobAttachmentFactory.getInstance().create(forumMessageAttachment, clob, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(forumMessageAttachment.getLastDetail().getForumMessagePK(), EventTypes.MODIFY, forumMessageAttachmentClob.getPrimaryKey(), EventTypes.MODIFY, createdBy);

        return forumMessageAttachmentClob;
    }

    private static final Map<EntityPermission, String> getForumMessageClobAttachmentQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM forummessageclobattachments "
                + "WHERE frmmsgcatt_frmmsgatt_forummessageattachmentid = ? AND frmmsgcatt_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM forummessageclobattachments "
                + "WHERE frmmsgcatt_frmmsgatt_forummessageattachmentid = ? AND frmmsgcatt_thrutime = ? "
                + "FOR UPDATE");
        getForumMessageClobAttachmentQueries = Collections.unmodifiableMap(queryMap);
    }

    private ForumMessageClobAttachment getForumMessageClobAttachment(ForumMessageAttachment forumMessageAttachment, EntityPermission entityPermission) {
        return ForumMessageClobAttachmentFactory.getInstance().getEntityFromQuery(entityPermission, getForumMessageClobAttachmentQueries,
                forumMessageAttachment, Session.MAX_TIME);
    }

    public ForumMessageClobAttachment getForumMessageClobAttachment(ForumMessageAttachment forumMessageAttachment) {
        return getForumMessageClobAttachment(forumMessageAttachment, EntityPermission.READ_ONLY);
    }

    public ForumMessageClobAttachment getForumMessageClobAttachmentForUpdate(ForumMessageAttachment forumMessageAttachment) {
        return getForumMessageClobAttachment(forumMessageAttachment, EntityPermission.READ_WRITE);
    }

    public ForumMessageClobAttachmentValue getForumMessageClobAttachmentValue(ForumMessageClobAttachment forumMessageAttachmentClob) {
        return forumMessageAttachmentClob == null ? null : forumMessageAttachmentClob.getForumMessageClobAttachmentValue().clone();
    }

    public ForumMessageClobAttachmentValue getForumMessageClobAttachmentValueForUpdate(ForumMessageAttachment forumMessageAttachment) {
        return getForumMessageClobAttachmentValue(getForumMessageClobAttachmentForUpdate(forumMessageAttachment));
    }

    public void updateForumMessageClobAttachmentFromValue(ForumMessageClobAttachmentValue forumMessageAttachmentClobValue, BasePK updatedBy) {
        if(forumMessageAttachmentClobValue.hasBeenModified()) {
            var forumMessageAttachmentClob = ForumMessageClobAttachmentFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    forumMessageAttachmentClobValue.getPrimaryKey());

            forumMessageAttachmentClob.setThruTime(session.START_TIME_LONG);
            forumMessageAttachmentClob.store();

            var forumMessageAttachmentPK = forumMessageAttachmentClob.getForumMessageAttachmentPK(); // Not updated
            var clob = forumMessageAttachmentClobValue.getClob();

            forumMessageAttachmentClob = ForumMessageClobAttachmentFactory.getInstance().create(forumMessageAttachmentPK, clob, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            sendEvent(forumMessageAttachmentClob.getForumMessageAttachment().getLastDetail().getForumMessagePK(), EventTypes.MODIFY, forumMessageAttachmentClob.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteForumMessageClobAttachment(ForumMessageClobAttachment forumMessageAttachmentClob, BasePK deletedBy) {
        forumMessageAttachmentClob.setThruTime(session.START_TIME_LONG);

        sendEvent(forumMessageAttachmentClob.getForumMessageAttachment().getLastDetail().getForumMessagePK(), EventTypes.MODIFY, forumMessageAttachmentClob.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteForumMessageClobAttachmentByForumMessageAttachment(ForumMessageAttachment forumMessageAttachment, BasePK deletedBy) {
        var forumMessageAttachmentClob = getForumMessageClobAttachmentForUpdate(forumMessageAttachment);

        if(forumMessageAttachmentClob != null) {
            deleteForumMessageClobAttachment(forumMessageAttachmentClob, deletedBy);
        }
    }

    // --------------------------------------------------------------------------------
    //   Forum Message Attachment Descriptions
    // --------------------------------------------------------------------------------

    public ForumMessageAttachmentDescription createForumMessageAttachmentDescription(ForumMessageAttachment forumMessageAttachment, Language language, String description, BasePK createdBy) {
        var forumMessageAttachmentDescription = ForumMessageAttachmentDescriptionFactory.getInstance().create(forumMessageAttachment, language,
                description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(forumMessageAttachment.getLastDetail().getForumMessagePK(), EventTypes.MODIFY, forumMessageAttachmentDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return forumMessageAttachmentDescription;
    }

    private static final Map<EntityPermission, String> getForumMessageAttachmentDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM forummessageattachmentdescriptions "
                + "WHERE frmmsgattd_frmmsgatt_forummessageattachmentid = ? AND frmmsgattd_lang_languageid = ? AND frmmsgattd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM forummessageattachmentdescriptions "
                + "WHERE frmmsgattd_frmmsgatt_forummessageattachmentid = ? AND frmmsgattd_lang_languageid = ? AND frmmsgattd_thrutime = ? "
                + "FOR UPDATE");
        getForumMessageAttachmentDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private ForumMessageAttachmentDescription getForumMessageAttachmentDescription(ForumMessageAttachment forumMessageAttachment, Language language, EntityPermission entityPermission) {
        return ForumMessageAttachmentDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getForumMessageAttachmentDescriptionQueries,
                forumMessageAttachment, language, Session.MAX_TIME);
    }

    public ForumMessageAttachmentDescription getForumMessageAttachmentDescription(ForumMessageAttachment forumMessageAttachment, Language language) {
        return getForumMessageAttachmentDescription(forumMessageAttachment, language, EntityPermission.READ_ONLY);
    }

    public ForumMessageAttachmentDescription getForumMessageAttachmentDescriptionForUpdate(ForumMessageAttachment forumMessageAttachment, Language language) {
        return getForumMessageAttachmentDescription(forumMessageAttachment, language, EntityPermission.READ_WRITE);
    }

    public ForumMessageAttachmentDescriptionValue getForumMessageAttachmentDescriptionValue(ForumMessageAttachmentDescription forumMessageAttachmentDescription) {
        return forumMessageAttachmentDescription == null ? null : forumMessageAttachmentDescription.getForumMessageAttachmentDescriptionValue().clone();
    }

    public ForumMessageAttachmentDescriptionValue getForumMessageAttachmentDescriptionValueForUpdate(ForumMessageAttachment forumMessageAttachment, Language language) {
        return getForumMessageAttachmentDescriptionValue(getForumMessageAttachmentDescriptionForUpdate(forumMessageAttachment, language));
    }

    private static final Map<EntityPermission, String> getForumMessageAttachmentDescriptionsByForumMessageAttachmentQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM forummessageattachmentdescriptions, languages "
                + "WHERE frmmsgattd_frmmsgatt_forummessageattachmentid = ? AND frmmsgattd_thrutime = ? AND frmmsgattd_lang_languageid = lang_languageid "
                + "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM forummessageattachmentdescriptions "
                + "WHERE frmmsgattd_frmmsgatt_forummessageattachmentid = ? AND frmmsgattd_thrutime = ? "
                + "FOR UPDATE");
        getForumMessageAttachmentDescriptionsByForumMessageAttachmentQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ForumMessageAttachmentDescription> getForumMessageAttachmentDescriptionsByForumMessageAttachment(ForumMessageAttachment forumMessageAttachment, EntityPermission entityPermission) {
        return ForumMessageAttachmentDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getForumMessageAttachmentDescriptionsByForumMessageAttachmentQueries,
                forumMessageAttachment, Session.MAX_TIME);
    }

    public List<ForumMessageAttachmentDescription> getForumMessageAttachmentDescriptionsByForumMessageAttachment(ForumMessageAttachment forumMessageAttachment) {
        return getForumMessageAttachmentDescriptionsByForumMessageAttachment(forumMessageAttachment, EntityPermission.READ_ONLY);
    }

    public List<ForumMessageAttachmentDescription> getForumMessageAttachmentDescriptionsByForumMessageAttachmentForUpdate(ForumMessageAttachment forumMessageAttachment) {
        return getForumMessageAttachmentDescriptionsByForumMessageAttachment(forumMessageAttachment, EntityPermission.READ_WRITE);
    }

    public String getBestForumMessageAttachmentDescription(ForumMessageAttachment forumMessageAttachment, Language language) {
        String description;
        var forumMessageAttachmentDescription = getForumMessageAttachmentDescription(forumMessageAttachment, language);

        if(forumMessageAttachmentDescription == null && !language.getIsDefault()) {
            forumMessageAttachmentDescription = getForumMessageAttachmentDescription(forumMessageAttachment, partyControl.getDefaultLanguage());
        }

        if(forumMessageAttachmentDescription == null) {
            var forumMessageAttachmentDetail = forumMessageAttachment.getLastDetail();
            
            description = forumMessageAttachmentDetail.getForumMessage().getLastDetail().getForumMessageName() + '-' +
                    forumMessageAttachmentDetail.getForumMessageAttachmentSequence();
        } else {
            description = forumMessageAttachmentDescription.getDescription();
        }

        return description;
    }

    public ForumMessageAttachmentDescriptionTransfer getForumMessageAttachmentDescriptionTransfer(UserVisit userVisit, ForumMessageAttachmentDescription forumMessageAttachmentDescription) {
        return getForumTransferCaches(userVisit).getForumMessageAttachmentDescriptionTransferCache().getForumMessageAttachmentDescriptionTransfer(forumMessageAttachmentDescription);
    }

    public List<ForumMessageAttachmentDescriptionTransfer> getForumMessageAttachmentDescriptionTransfersByForumMessageAttachment(UserVisit userVisit, ForumMessageAttachment forumMessageAttachment) {
        var forumMessageAttachmentDescriptions = getForumMessageAttachmentDescriptionsByForumMessageAttachment(forumMessageAttachment);
        List<ForumMessageAttachmentDescriptionTransfer> forumMessageAttachmentDescriptionTransfers = null;

        if(forumMessageAttachmentDescriptions != null) {
            forumMessageAttachmentDescriptionTransfers = new ArrayList<>(forumMessageAttachmentDescriptions.size());

            for(var forumMessageAttachmentDescription : forumMessageAttachmentDescriptions) {
                forumMessageAttachmentDescriptionTransfers.add(getForumTransferCaches(userVisit).getForumMessageAttachmentDescriptionTransferCache().getForumMessageAttachmentDescriptionTransfer(forumMessageAttachmentDescription));
            }
        }

        return forumMessageAttachmentDescriptionTransfers;
    }

    public void updateForumMessageAttachmentDescriptionFromValue(ForumMessageAttachmentDescriptionValue forumMessageAttachmentDescriptionValue, BasePK updatedBy) {
        if(forumMessageAttachmentDescriptionValue.hasBeenModified()) {
            var forumMessageAttachmentDescription = ForumMessageAttachmentDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, forumMessageAttachmentDescriptionValue.getPrimaryKey());

            forumMessageAttachmentDescription.setThruTime(session.START_TIME_LONG);
            forumMessageAttachmentDescription.store();

            var forumMessageAttachment = forumMessageAttachmentDescription.getForumMessageAttachment();
            var language = forumMessageAttachmentDescription.getLanguage();
            var description = forumMessageAttachmentDescriptionValue.getDescription();

            forumMessageAttachmentDescription = ForumMessageAttachmentDescriptionFactory.getInstance().create(forumMessageAttachment, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(forumMessageAttachment.getLastDetail().getForumMessagePK(), EventTypes.MODIFY, forumMessageAttachmentDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteForumMessageAttachmentDescription(ForumMessageAttachmentDescription forumMessageAttachmentDescription, BasePK deletedBy) {
        forumMessageAttachmentDescription.setThruTime(session.START_TIME_LONG);

        sendEvent(forumMessageAttachmentDescription.getForumMessageAttachment().getLastDetail().getForumMessagePK(), EventTypes.MODIFY, forumMessageAttachmentDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteForumMessageAttachmentDescriptionsByForumMessageAttachment(ForumMessageAttachment forumMessageAttachment, BasePK deletedBy) {
        var forumMessageAttachmentDescriptions = getForumMessageAttachmentDescriptionsByForumMessageAttachmentForUpdate(forumMessageAttachment);

        forumMessageAttachmentDescriptions.forEach((forumMessageAttachmentDescription) -> 
                deleteForumMessageAttachmentDescription(forumMessageAttachmentDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Forum Message Roles
    // --------------------------------------------------------------------------------
    
    public ForumMessageRole createForumMessageRole(ForumMessage forumMessage, ForumRoleType forumRoleType, Party party,
            BasePK createdBy) {
        var forumMessageRole = ForumMessageRoleFactory.getInstance().create(forumMessage, forumRoleType,
                party, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(forumMessage.getPrimaryKey(), EventTypes.MODIFY, forumMessageRole.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return forumMessageRole;
    }
    
    private ForumMessageRole getForumMessageRole(ForumMessage forumMessage, ForumRoleType forumRoleType, Party party, EntityPermission entityPermission) {
        ForumMessageRole forumMessageRole;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forummessageroles " +
                        "WHERE frmmsgr_frmmsg_forummessageid = ? AND frmmsgr_frmrtyp_forumroletypeid = ? AND frmmsgr_par_partyid = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forummessageroles " +
                        "WHERE frmmsgr_frmmsg_forummessageid = ? AND frmmsgr_frmrtyp_forumroletypeid = ? AND frmmsgr_par_partyid = ? " +
                        "FOR UPDATE";
            }

            var ps = ForumMessageRoleFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, forumMessage.getPrimaryKey().getEntityId());
            ps.setLong(2, forumRoleType.getPrimaryKey().getEntityId());
            ps.setLong(3, party.getPrimaryKey().getEntityId());
            ps.setLong(4, Session.MAX_TIME);
            
            forumMessageRole = ForumMessageRoleFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumMessageRole;
    }
    
    public ForumMessageRole getForumMessageRole(ForumMessage forumMessage, ForumRoleType forumRoleType, Party party) {
        return getForumMessageRole(forumMessage, forumRoleType, party, EntityPermission.READ_ONLY);
    }
    
    public ForumMessageRole getForumMessageRoleForUpdate(ForumMessage forumMessage, ForumRoleType forumRoleType, Party party) {
        return getForumMessageRole(forumMessage, forumRoleType, party, EntityPermission.READ_WRITE);
    }
    
    private List<ForumMessageRole> getForumMessageRolesByForumMessage(ForumMessage forumMessage, EntityPermission entityPermission) {
        List<ForumMessageRole> forumMessageRoles;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forummessageroles, forumroletypes, parties, partydetails " +
                        "WHERE frmmsgr_frmmsg_forummessageid = ? AND frmmsgr_thrutime = ? " +
                        "AND frmmsgr_frmrtyp_forumroletypeid = frmrtyp_forumroletypeid " +
                        "AND frmmsgr_par_partyid = par_partyid AND par_lastdetailid = pardt_partydetailid " +
                        "ORDER BY frmrtyp_sortorder, frmrtyp_forumroletypename, pardt_partyname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forummessageroles " +
                        "WHERE frmmsgr_frmmsg_forummessageid = ? AND frmmsgr_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ForumMessageRoleFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, forumMessage.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            forumMessageRoles = ForumMessageRoleFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumMessageRoles;
    }
    
    public List<ForumMessageRole> getForumMessageRolesByForumMessage(ForumMessage forumMessage) {
        return getForumMessageRolesByForumMessage(forumMessage, EntityPermission.READ_ONLY);
    }
    
    public List<ForumMessageRole> getForumMessageRolesByForumMessageForUpdate(ForumMessage forumMessage) {
        return getForumMessageRolesByForumMessage(forumMessage, EntityPermission.READ_WRITE);
    }
    
    public ForumMessageRoleTransfer getForumMessageRoleTransfer(UserVisit userVisit, ForumMessageRole forumMessageRole) {
        return getForumTransferCaches(userVisit).getForumMessageRoleTransferCache().getForumMessageRoleTransfer(forumMessageRole);
    }
    
    public List<ForumMessageRoleTransfer> getForumMessageRoleTransfers(UserVisit userVisit, Collection<ForumMessageRole> forumMessageRoles) {
        List<ForumMessageRoleTransfer> forumMessageRoleTransfers = new ArrayList<>(forumMessageRoles.size());
        var forumMessageRoleTransferCache = getForumTransferCaches(userVisit).getForumMessageRoleTransferCache();
        
        forumMessageRoles.forEach((forumMessageRole) ->
                forumMessageRoleTransfers.add(forumMessageRoleTransferCache.getForumMessageRoleTransfer(forumMessageRole))
        );
        
        return forumMessageRoleTransfers;
    }
    
    public List<ForumMessageRoleTransfer> getForumMessageRoleTransfersByForumMessage(UserVisit userVisit, ForumMessage forumMessage) {
        return getForumMessageRoleTransfers(userVisit, getForumMessageRolesByForumMessage(forumMessage));
    }
    
    public void deleteForumMessageRole(ForumMessageRole forumMessageRole, BasePK deletedBy) {
        forumMessageRole.setThruTime(session.START_TIME_LONG);
        
        sendEvent(forumMessageRole.getForumMessagePK(), EventTypes.MODIFY, forumMessageRole.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteForumMessageRoles(List<ForumMessageRole> forumMessageRoles, BasePK deletedBy) {
        forumMessageRoles.forEach((forumMessageRole) -> 
                deleteForumMessageRole(forumMessageRole, deletedBy)
        );
    }
    
    public void deleteForumMessageRolesByForumMessage(ForumMessage forumMessage, BasePK deletedBy) {
        deleteForumMessageRoles(getForumMessageRolesByForumMessageForUpdate(forumMessage), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Message Parts
    // --------------------------------------------------------------------------------
    
    public ForumMessagePart createForumMessagePart(ForumMessage forumMessage, ForumMessagePartType forumMessagePartType,
            Language language, MimeType mimeType, BasePK createdBy) {

        var forumMessagePart = ForumMessagePartFactory.getInstance().create();
        var forumMessagePartDetail = ForumMessagePartDetailFactory.getInstance().create(session,
                forumMessagePart, forumMessage, forumMessagePartType, language, mimeType, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        // Convert to R/W
        forumMessagePart = ForumMessagePartFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                forumMessagePart.getPrimaryKey());
        forumMessagePart.setActiveDetail(forumMessagePartDetail);
        forumMessagePart.setLastDetail(forumMessagePartDetail);
        forumMessagePart.store();
        
        sendEvent(forumMessage.getPrimaryKey(), EventTypes.MODIFY, forumMessagePart.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return forumMessagePart;
    }
    
    private ForumMessagePart getForumMessagePart(ForumMessage forumMessage, ForumMessagePartType forumMessagePartType,
            Language language, EntityPermission entityPermission) {
        ForumMessagePart forumMessagePart;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forummessageparts, forummessagepartdetails " +
                        "WHERE frmmsgprt_activedetailid = frmmsgprtdt_forummessagepartdetailid " +
                        "AND frmmsgprtdt_frmmsg_forummessageid = ? AND frmmsgprtdt_frmmsgprttyp_forummessageparttypeid = ? AND frmmsgprtdt_lang_languageid = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forummessageparts, forummessagepartdetails " +
                        "WHERE frmmsgprt_activedetailid = frmmsgprtdt_forummessagepartdetailid " +
                        "AND frmmsgprtdt_frmmsg_forummessageid = ? AND frmmsgprtdt_frmmsgprttyp_forummessageparttypeid = ? AND frmmsgprtdt_lang_languageid = ? " +
                        "FOR UPDATE";
            }

            var ps = ForumMessagePartFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, forumMessage.getPrimaryKey().getEntityId());
            ps.setLong(2, forumMessagePartType.getPrimaryKey().getEntityId());
            ps.setLong(3, language.getPrimaryKey().getEntityId());
            
            forumMessagePart = ForumMessagePartFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumMessagePart;
    }
    
    public ForumMessagePart getForumMessagePart(ForumMessage forumMessage, ForumMessagePartType forumMessagePartType,
            Language language) {
        return getForumMessagePart(forumMessage, forumMessagePartType, language, EntityPermission.READ_ONLY);
    }
    
    public ForumMessagePart getForumMessagePartForUpdate(ForumMessage forumMessage, ForumMessagePartType forumMessagePartType,
            Language language) {
        return getForumMessagePart(forumMessage, forumMessagePartType, language, EntityPermission.READ_WRITE);
    }
    
    public ForumMessagePartDetailValue getForumMessagePartDetailValueForUpdate(ForumMessagePart forumMessagePart) {
        return forumMessagePart == null? null: forumMessagePart.getLastDetailForUpdate().getForumMessagePartDetailValue().clone();
    }
    
    public ForumMessagePartDetailValue getForumMessagePartDetailValueForUpdate(ForumMessage forumMessage,
            ForumMessagePartType forumMessagePartType, Language language) {
        return getForumMessagePartDetailValueForUpdate(getForumMessagePartForUpdate(forumMessage, forumMessagePartType, language));
    }
    
    public List<ForumMessagePart> getForumMessagePartsByForumMessageForUpdate(ForumMessage forumMessage) {
        List<ForumMessagePart> forumMessageParts;
        
        try {
            var ps = ForumMessagePartFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM forummessageparts, forummessagepartdetails " +
                    "WHERE frmmsgprt_activedetailid = frmmsgprtdt_forummessagepartdetailid " +
                    "AND frmmsgprtdt_frmmsg_forummessageid = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, forumMessage.getPrimaryKey().getEntityId());
            
            forumMessageParts = ForumMessagePartFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumMessageParts;
    }
    
    public ForumMessagePart getBestForumMessagePart(ForumMessage forumMessage, ForumMessagePartType forumMessagePartType,
            Language language) {
        var forumMessagePart = getForumMessagePart(forumMessage, forumMessagePartType, language);
        
        if(forumMessagePart == null && !language.getIsDefault()) {
            forumMessagePart = getForumMessagePart(forumMessage, forumMessagePartType, partyControl.getDefaultLanguage());
        }
        
        return forumMessagePart;
    }
    
    public ForumMessagePartTransfer getForumMessagePartTransfer(UserVisit userVisit, ForumMessagePart forumMessagePart) {
        return getForumTransferCaches(userVisit).getForumMessagePartTransferCache().getForumMessagePartTransfer(forumMessagePart);
    }
    
    public List<ForumMessagePartTransfer> getForumMessagePartTransfers(UserVisit userVisit, Collection<ForumMessagePart> forumMessageParts) {
        List<ForumMessagePartTransfer> forumMessagePartTransfers = new ArrayList<>(forumMessageParts.size());
        var forumMessagePartTransferCache = getForumTransferCaches(userVisit).getForumMessagePartTransferCache();
        
        forumMessageParts.forEach((forumMessagePart) ->
                forumMessagePartTransfers.add(forumMessagePartTransferCache.getForumMessagePartTransfer(forumMessagePart))
        );
        
        return forumMessagePartTransfers;
    }
    
    public List<ForumMessagePartTransfer> getForumMessagePartTransfersByForumMessageAndLanguage(UserVisit userVisit, ForumMessage forumMessage, Language language) {
        var forumMessageTypePartTypes = getForumMessageTypePartTypesByForumMessageType(forumMessage.getLastDetail().getForumMessageType());
        List<ForumMessagePartTransfer> forumMessagePartTransfers = new ArrayList<>(forumMessageTypePartTypes.size());
        
        forumMessageTypePartTypes.stream().map((forumMessageTypePartType) -> getBestForumMessagePart(forumMessage,
                forumMessageTypePartType.getForumMessagePartType(), language)).filter((forumMessagePart) -> (forumMessagePart != null)).forEach((forumMessagePart) -> {
                    forumMessagePartTransfers.add(getForumMessagePartTransfer(userVisit, forumMessagePart));
                });
        
        return forumMessagePartTransfers;
    }
    
    public void updateForumMessagePartFromValue(ForumMessagePartDetailValue forumMessagePartDetailValue, BasePK updatedBy) {
        if(forumMessagePartDetailValue.hasBeenModified()) {
            var forumMessagePart = ForumMessagePartFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    forumMessagePartDetailValue.getForumMessagePartPK());
            var forumMessagePartDetail = forumMessagePart.getActiveDetailForUpdate();
            
            forumMessagePartDetail.setThruTime(session.START_TIME_LONG);
            forumMessagePartDetail.store();

            var forumMessagePartPK = forumMessagePartDetail.getForumMessagePartPK();
            var forumMessagePK = forumMessagePartDetail.getForumMessagePK(); // Not updated
            var forumMessagePartTypePK = forumMessagePartDetail.getForumMessagePartTypePK(); // Not updated
            var languagePK = forumMessagePartDetail.getLanguagePK(); // Not updated
            var mimeTypePK = forumMessagePartDetailValue.getMimeTypePK();
            
            forumMessagePartDetail = ForumMessagePartDetailFactory.getInstance().create(forumMessagePartPK, forumMessagePK,
                    forumMessagePartTypePK, languagePK, mimeTypePK, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            forumMessagePart.setActiveDetail(forumMessagePartDetail);
            forumMessagePart.setLastDetail(forumMessagePartDetail);
            
            sendEvent(forumMessagePartPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deleteForumMessagePart(ForumMessagePart forumMessagePart, BasePK deletedBy) {
        var forumMessagePartDetail = forumMessagePart.getLastDetailForUpdate();
        var mimeType = forumMessagePartDetail.getMimeType();
        
        if(mimeType == null) {
            deleteForumStringMessagePartByForumMessagePart(forumMessagePart, deletedBy);
        } else {
            var entityAttributeTypeName = mimeType.getLastDetail().getEntityAttributeType().getEntityAttributeTypeName();
            
            if(entityAttributeTypeName.equals(EntityAttributeTypes.BLOB.name())) {
                deleteForumBlobMessagePartByForumMessagePart(forumMessagePart, deletedBy);
            } else if(entityAttributeTypeName.equals(EntityAttributeTypes.CLOB.name())) {
                deleteForumClobMessagePartByForumMessagePart(forumMessagePart, deletedBy);
            }
        }
        
        forumMessagePartDetail.setThruTime(session.START_TIME_LONG);
        forumMessagePart.setActiveDetail(null);
        forumMessagePart.store();
        
        sendEvent(forumMessagePartDetail.getForumMessagePK(), EventTypes.MODIFY, forumMessagePart.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteForumMessageParts(List<ForumMessagePart> forumMessageParts, BasePK deletedBy) {
        forumMessageParts.forEach((forumMessagePart) -> 
                deleteForumMessagePart(forumMessagePart, deletedBy)
        );
    }
    
    public void deleteForumMessagePartsByForumMessage(ForumMessage forumMessage, BasePK deletedBy) {
        deleteForumMessageParts(getForumMessagePartsByForumMessageForUpdate(forumMessage), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum String Message Parts
    // --------------------------------------------------------------------------------
    
    public ForumStringMessagePart createForumStringMessagePart(ForumMessagePart forumMessagePart, String string, BasePK createdBy) {
        var forumStringMessagePart = ForumStringMessagePartFactory.getInstance().create(session,
                forumMessagePart, string, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(forumMessagePart.getLastDetail().getForumMessagePK(), EventTypes.MODIFY, forumStringMessagePart.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return forumStringMessagePart;
    }
    
    private ForumStringMessagePart getForumStringMessagePart(ForumMessagePart forumMessagePart, EntityPermission entityPermission) {
        ForumStringMessagePart forumStringMessagePart;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forumstringmessageparts " +
                        "WHERE frmsmsgprt_frmmsgprt_forummessagepartid = ? AND frmsmsgprt_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forumstringmessageparts " +
                        "WHERE frmsmsgprt_frmmsgprt_forummessagepartid = ? AND frmsmsgprt_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ForumStringMessagePartFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, forumMessagePart.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            forumStringMessagePart = ForumStringMessagePartFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumStringMessagePart;
    }
    
    public ForumStringMessagePart getForumStringMessagePart(ForumMessagePart forumMessagePart) {
        return getForumStringMessagePart(forumMessagePart, EntityPermission.READ_ONLY);
    }
    
    public ForumStringMessagePart getForumStringMessagePartForUpdate(ForumMessagePart forumMessagePart) {
        return getForumStringMessagePart(forumMessagePart, EntityPermission.READ_WRITE);
    }
    
    public ForumStringMessagePartValue getForumStringMessagePartValue(ForumStringMessagePart forumStringMessagePart) {
        return forumStringMessagePart == null? null: forumStringMessagePart.getForumStringMessagePartValue().clone();
    }
    
    public ForumStringMessagePartValue getForumStringMessagePartValueForUpdate(ForumMessagePart forumMessagePart) {
        return getForumStringMessagePartValue(getForumStringMessagePartForUpdate(forumMessagePart));
    }
    
    public void updateForumStringMessagePartFromValue(ForumStringMessagePartValue forumStringMessagePartValue, BasePK updatedBy) {
        if(forumStringMessagePartValue.hasBeenModified()) {
            var forumStringMessagePart = ForumStringMessagePartFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     forumStringMessagePartValue.getPrimaryKey());
            
            forumStringMessagePart.setThruTime(session.START_TIME_LONG);
            forumStringMessagePart.store();

            var forumMessagePartPK = forumStringMessagePart.getForumMessagePartPK(); // Not updated
            var string = forumStringMessagePartValue.getString();
            
            forumStringMessagePart = ForumStringMessagePartFactory.getInstance().create(forumMessagePartPK, string,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(forumStringMessagePart.getForumMessagePart().getLastDetail().getForumMessagePK(),
                    EventTypes.MODIFY, forumStringMessagePart.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteForumStringMessagePart(ForumStringMessagePart forumStringMessagePart, BasePK deletedBy) {
        forumStringMessagePart.setThruTime(session.START_TIME_LONG);
        
        sendEvent(forumStringMessagePart.getForumMessagePart().getLastDetail().getForumMessagePK(),
                EventTypes.MODIFY, forumStringMessagePart.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteForumStringMessagePartByForumMessagePart(ForumMessagePart forumMessagePart, BasePK deletedBy) {
        var forumStringMessagePart = getForumStringMessagePartForUpdate(forumMessagePart);
        
        if(forumStringMessagePart != null) {
            deleteForumStringMessagePart(forumStringMessagePart, deletedBy);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Clob Message Parts
    // --------------------------------------------------------------------------------
    
    public ForumClobMessagePart createForumClobMessagePart(ForumMessagePart forumMessagePart, String clob, BasePK createdBy) {
        var forumClobMessagePart = ForumClobMessagePartFactory.getInstance().create(session,
                forumMessagePart, clob, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(forumMessagePart.getLastDetail().getForumMessagePK(), EventTypes.MODIFY, forumClobMessagePart.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return forumClobMessagePart;
    }
    
    private ForumClobMessagePart getForumClobMessagePart(ForumMessagePart forumMessagePart, EntityPermission entityPermission) {
        ForumClobMessagePart forumClobMessagePart;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forumclobmessageparts " +
                        "WHERE frmcmsgprt_frmmsgprt_forummessagepartid = ? AND frmcmsgprt_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forumclobmessageparts " +
                        "WHERE frmcmsgprt_frmmsgprt_forummessagepartid = ? AND frmcmsgprt_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ForumClobMessagePartFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, forumMessagePart.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            forumClobMessagePart = ForumClobMessagePartFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumClobMessagePart;
    }
    
    public ForumClobMessagePart getForumClobMessagePart(ForumMessagePart forumMessagePart) {
        return getForumClobMessagePart(forumMessagePart, EntityPermission.READ_ONLY);
    }
    
    public ForumClobMessagePart getForumClobMessagePartForUpdate(ForumMessagePart forumMessagePart) {
        return getForumClobMessagePart(forumMessagePart, EntityPermission.READ_WRITE);
    }
    
    public ForumClobMessagePartValue getForumClobMessagePartValue(ForumClobMessagePart forumClobMessagePart) {
        return forumClobMessagePart == null? null: forumClobMessagePart.getForumClobMessagePartValue().clone();
    }
    
    public ForumClobMessagePartValue getForumClobMessagePartValueForUpdate(ForumMessagePart forumMessagePart) {
        return getForumClobMessagePartValue(getForumClobMessagePartForUpdate(forumMessagePart));
    }
    
    public void updateForumClobMessagePartFromValue(ForumClobMessagePartValue forumClobMessagePartValue, BasePK updatedBy) {
        if(forumClobMessagePartValue.hasBeenModified()) {
            var forumClobMessagePart = ForumClobMessagePartFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     forumClobMessagePartValue.getPrimaryKey());
            
            forumClobMessagePart.setThruTime(session.START_TIME_LONG);
            forumClobMessagePart.store();

            var forumMessagePartPK = forumClobMessagePart.getForumMessagePartPK(); // Not updated
            var clob = forumClobMessagePartValue.getClob();
            
            forumClobMessagePart = ForumClobMessagePartFactory.getInstance().create(forumMessagePartPK, clob,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(forumClobMessagePart.getForumMessagePart().getLastDetail().getForumMessagePK(), EventTypes.MODIFY, forumClobMessagePart.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteForumClobMessagePart(ForumClobMessagePart forumClobMessagePart, BasePK deletedBy) {
        forumClobMessagePart.setThruTime(session.START_TIME_LONG);
        
        sendEvent(forumClobMessagePart.getForumMessagePart().getLastDetail().getForumMessagePK(), EventTypes.MODIFY, forumClobMessagePart.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteForumClobMessagePartByForumMessagePart(ForumMessagePart forumMessagePart, BasePK deletedBy) {
        var forumClobMessagePart = getForumClobMessagePartForUpdate(forumMessagePart);
        
        if(forumClobMessagePart != null) {
            deleteForumClobMessagePart(forumClobMessagePart, deletedBy);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Blob Message Parts
    // --------------------------------------------------------------------------------
    
    public ForumBlobMessagePart createForumBlobMessagePart(ForumMessagePart forumMessagePart, ByteArray blob, BasePK createdBy) {
        var forumBlobMessagePart = ForumBlobMessagePartFactory.getInstance().create(session,
                forumMessagePart, blob, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(forumMessagePart.getLastDetail().getForumMessagePK(), EventTypes.MODIFY, forumBlobMessagePart.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return forumBlobMessagePart;
    }
    
    private ForumBlobMessagePart getForumBlobMessagePart(ForumMessagePart forumMessagePart, EntityPermission entityPermission) {
        ForumBlobMessagePart forumBlobMessagePart;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM forumblobmessageparts " +
                        "WHERE frmbmsgprt_frmmsgprt_forummessagepartid = ? AND frmbmsgprt_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM forumblobmessageparts " +
                        "WHERE frmbmsgprt_frmmsgprt_forummessagepartid = ? AND frmbmsgprt_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = ForumBlobMessagePartFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, forumMessagePart.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            forumBlobMessagePart = ForumBlobMessagePartFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumBlobMessagePart;
    }
    
    public ForumBlobMessagePart getForumBlobMessagePart(ForumMessagePart forumMessagePart) {
        return getForumBlobMessagePart(forumMessagePart, EntityPermission.READ_ONLY);
    }
    
    public ForumBlobMessagePart getForumBlobMessagePartForUpdate(ForumMessagePart forumMessagePart) {
        return getForumBlobMessagePart(forumMessagePart, EntityPermission.READ_WRITE);
    }
    
    public ForumBlobMessagePartValue getForumBlobMessagePartValue(ForumBlobMessagePart forumBlobMessagePart) {
        return forumBlobMessagePart == null? null: forumBlobMessagePart.getForumBlobMessagePartValue().clone();
    }
    
    public ForumBlobMessagePartValue getForumBlobMessagePartValueForUpdate(ForumMessagePart forumMessagePart) {
        return getForumBlobMessagePartValue(getForumBlobMessagePartForUpdate(forumMessagePart));
    }
    
    public void updateForumBlobMessagePartFromValue(ForumBlobMessagePartValue forumBlobMessagePartValue, BasePK updatedBy) {
        if(forumBlobMessagePartValue.hasBeenModified()) {
            var forumBlobMessagePart = ForumBlobMessagePartFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     forumBlobMessagePartValue.getPrimaryKey());
            
            forumBlobMessagePart.setThruTime(session.START_TIME_LONG);
            forumBlobMessagePart.store();

            var forumMessagePartPK = forumBlobMessagePart.getForumMessagePartPK(); // Not updated
            var blob = forumBlobMessagePartValue.getBlob();
            
            forumBlobMessagePart = ForumBlobMessagePartFactory.getInstance().create(forumMessagePartPK, blob,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(forumBlobMessagePart.getForumMessagePart().getLastDetail().getForumMessagePK(), EventTypes.MODIFY, forumBlobMessagePart.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteForumBlobMessagePart(ForumBlobMessagePart forumBlobMessagePart, BasePK deletedBy) {
        forumBlobMessagePart.setThruTime(session.START_TIME_LONG);
        
        sendEvent(forumBlobMessagePart.getForumMessagePart().getLastDetail().getForumMessagePK(), EventTypes.MODIFY, forumBlobMessagePart.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteForumBlobMessagePartByForumMessagePart(ForumMessagePart forumMessagePart, BasePK deletedBy) {
        var forumBlobMessagePart = getForumBlobMessagePartForUpdate(forumMessagePart);
        
        if(forumBlobMessagePart != null) {
            deleteForumBlobMessagePart(forumBlobMessagePart, deletedBy);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Message Part Types
    // --------------------------------------------------------------------------------
    
    public ForumMessagePartType createForumMessagePartType(String forumMessagePartTypeName, MimeTypeUsageType mimeTypeUsageType,
            Integer sortOrder) {
        return ForumMessagePartTypeFactory.getInstance().create(forumMessagePartTypeName, mimeTypeUsageType, sortOrder);
    }
    
    public ForumMessagePartType getForumMessagePartTypeByName(String forumMessagePartTypeName) {
        ForumMessagePartType forumMessagePartType;
        
        try {
            var ps = ForumMessagePartTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM forummessageparttypes " +
                    "WHERE frmmsgprttyp_forummessageparttypename = ?");
            
            ps.setString(1, forumMessagePartTypeName);
            
            forumMessagePartType = ForumMessagePartTypeFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY,
                    ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumMessagePartType;
    }
    
    public List<ForumMessagePartType> getForumMessagePartTypes() {
        var ps = ForumMessagePartTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM forummessageparttypes " +
                "ORDER BY frmmsgprttyp_sortorder, frmmsgprttyp_forummessageparttypename");
        
        return ForumMessagePartTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public ForumMessagePartTypeTransfer getForumMessagePartTypeTransfer(UserVisit userVisit, ForumMessagePartType forumMessagePartType) {
        return getForumTransferCaches(userVisit).getForumMessagePartTypeTransferCache().getForumMessagePartTypeTransfer(forumMessagePartType);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Message Part Type Descriptions
    // --------------------------------------------------------------------------------
    
    public ForumMessagePartTypeDescription createForumMessagePartTypeDescription(ForumMessagePartType forumMessagePartType,
            Language language, String description) {
        return ForumMessagePartTypeDescriptionFactory.getInstance().create(forumMessagePartType, language, description);
    }
    
    public ForumMessagePartTypeDescription getForumMessagePartTypeDescription(ForumMessagePartType forumMessagePartType,
            Language language) {
        ForumMessagePartTypeDescription forumMessagePartTypeDescription;
        
        try {
            var ps = ForumMessagePartTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM forummessageparttypedescriptions " +
                    "WHERE frmmsgprttypd_frmmsgprttyp_forummessageparttypeid = ? AND frmmsgprttypd_lang_languageid = ?");
            
            ps.setLong(1, forumMessagePartType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            forumMessagePartTypeDescription = ForumMessagePartTypeDescriptionFactory.getInstance().getEntityFromQuery(session,
                    EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumMessagePartTypeDescription;
    }
    
    public String getBestForumMessagePartTypeDescription(ForumMessagePartType forumMessagePartType, Language language) {
        String description;
        var forumMessagePartTypeDescription = getForumMessagePartTypeDescription(forumMessagePartType,
                language);
        
        if(forumMessagePartTypeDescription == null && !language.getIsDefault()) {
            forumMessagePartTypeDescription = getForumMessagePartTypeDescription(forumMessagePartType,
                    partyControl.getDefaultLanguage());
        }
        
        if(forumMessagePartTypeDescription == null) {
            description = forumMessagePartType.getForumMessagePartTypeName();
        } else {
            description = forumMessagePartTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Message Types
    // --------------------------------------------------------------------------------
    
    public ForumMessageType createForumMessageType(String forumMessageTypeName, Boolean isDefault, Integer sortOrder) {
        return ForumMessageTypeFactory.getInstance().create(forumMessageTypeName, isDefault, sortOrder);
    }
    
    public ForumMessageType getForumMessageTypeByName(String forumMessageTypeName) {
        ForumMessageType forumMessageType;
        
        try {
            var ps = ForumMessageTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM forummessagetypes " +
                    "WHERE frmmsgtyp_forummessagetypename = ?");
            
            ps.setString(1, forumMessageTypeName);
            
            forumMessageType = ForumMessageTypeFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumMessageType;
    }
    
    public List<ForumMessageType> getForumMessageTypes() {
        var ps = ForumMessageTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM forummessagetypes " +
                "ORDER BY frmmsgtyp_sortorder, frmmsgtyp_forummessagetypename");
        
        return ForumMessageTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public ForumMessageTypeChoicesBean getForumMessageTypeChoices(String defaultForumMessageTypeChoice, Language language,
            boolean allowNullChoice) {
        var forumMessageTypes = getForumMessageTypes();
        var size = forumMessageTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultForumMessageTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var forumMessageType : forumMessageTypes) {
            var label = getBestForumMessageTypeDescription(forumMessageType, language);
            var value = forumMessageType.getForumMessageTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultForumMessageTypeChoice != null && defaultForumMessageTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && forumMessageType.getIsDefault()))
                defaultValue = value;
        }
        
        return new ForumMessageTypeChoicesBean(labels, values, defaultValue);
    }
    
    public ForumMessageTypeTransfer getForumMessageTypeTransfer(UserVisit userVisit, ForumMessageType forumMessageType) {
        return getForumTransferCaches(userVisit).getForumMessageTypeTransferCache().getForumMessageTypeTransfer(forumMessageType);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Message Type Descriptions
    // --------------------------------------------------------------------------------
    
    public ForumMessageTypeDescription createForumMessageTypeDescription(ForumMessageType forumMessageType, Language language,
            String description) {
        return ForumMessageTypeDescriptionFactory.getInstance().create(forumMessageType, language, description);
    }
    
    public ForumMessageTypeDescription getForumMessageTypeDescription(ForumMessageType forumMessageType, Language language) {
        ForumMessageTypeDescription forumMessageTypeDescription;
        
        try {
            var ps = ForumMessageTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM forummessagetypedescriptions " +
                    "WHERE frmmsgtypd_frmmsgtyp_forummessagetypeid = ? AND frmmsgtypd_lang_languageid = ?");
            
            ps.setLong(1, forumMessageType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            forumMessageTypeDescription = ForumMessageTypeDescriptionFactory.getInstance().getEntityFromQuery(session,
                    EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumMessageTypeDescription;
    }
    
    public String getBestForumMessageTypeDescription(ForumMessageType forumMessageType, Language language) {
        String description;
        var forumMessageTypeDescription = getForumMessageTypeDescription(forumMessageType, language);
        
        if(forumMessageTypeDescription == null && !language.getIsDefault()) {
            forumMessageTypeDescription = getForumMessageTypeDescription(forumMessageType, partyControl.getDefaultLanguage());
        }
        
        if(forumMessageTypeDescription == null) {
            description = forumMessageType.getForumMessageTypeName();
        } else {
            description = forumMessageTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Message Type Part Types
    // --------------------------------------------------------------------------------
    
    public ForumMessageTypePartType createForumMessageTypePartType(ForumMessageType forumMessageType, Boolean includeInIndex, Boolean indexDefault,
            Integer sortOrder, ForumMessagePartType forumMessagePartType) {
        return ForumMessageTypePartTypeFactory.getInstance().create(forumMessageType, includeInIndex, indexDefault, sortOrder, forumMessagePartType);
    }
    
    public ForumMessageTypePartType getForumMessageTypePartType(ForumMessageType forumMessageType, Integer sortOrder) {
        ForumMessageTypePartType forumMessageTypePartType;
        
        try {
            var ps = ForumMessageTypePartTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM forummessagetypeparttypes " +
                    "WHERE frmmsgtypprttyp_frmmsgtyp_forummessagetypeid = ? AND frmmsgtypprttyp_sortorder = ?");
            
            ps.setLong(1, forumMessageType.getPrimaryKey().getEntityId());
            ps.setInt(2, sortOrder);
            
            forumMessageTypePartType = ForumMessageTypePartTypeFactory.getInstance().getEntityFromQuery(session,
                    EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumMessageTypePartType;
    }
    
    public ForumMessageTypePartType getIndexDefaultForumMessageTypePartType(ForumMessageType forumMessageType) {
        ForumMessageTypePartType forumMessageTypePartType;
        
        try {
            var ps = ForumMessageTypePartTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM forummessagetypeparttypes " +
                    "WHERE frmmsgtypprttyp_frmmsgtyp_forummessagetypeid = ? AND frmmsgtypprttyp_indexdefault = 1");
            
            ps.setLong(1, forumMessageType.getPrimaryKey().getEntityId());
            
            forumMessageTypePartType = ForumMessageTypePartTypeFactory.getInstance().getEntityFromQuery(session, EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumMessageTypePartType;
    }
    
    public List<ForumMessageTypePartType> getForumMessageTypePartTypesByForumMessageType(ForumMessageType forumMessageType) {
        List<ForumMessageTypePartType> forumMessageTypePartTypes;
        
        try {
            var ps = ForumMessageTypePartTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM forummessagetypeparttypes " +
                    "WHERE frmmsgtypprttyp_frmmsgtyp_forummessagetypeid = ? " +
                    "ORDER BY frmmsgtypprttyp_sortorder");
            
            ps.setLong(1, forumMessageType.getPrimaryKey().getEntityId());
            
            forumMessageTypePartTypes = ForumMessageTypePartTypeFactory.getInstance().getEntitiesFromQuery(session, EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumMessageTypePartTypes;
    }
    
    public List<ForumMessageTypePartType> getForumMessageTypePartTypesByForumMessageTypeAndIncludeInIndex(ForumMessageType forumMessageType) {
        List<ForumMessageTypePartType> forumMessageTypePartTypes;
        
        try {
            var ps = ForumMessageTypePartTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM forummessagetypeparttypes " +
                    "WHERE frmmsgtypprttyp_frmmsgtyp_forummessagetypeid = ? AND frmmsgtypprttyp_includeinindex = 1 " +
                    "ORDER BY frmmsgtypprttyp_sortorder");
            
            ps.setLong(1, forumMessageType.getPrimaryKey().getEntityId());
            
            forumMessageTypePartTypes = ForumMessageTypePartTypeFactory.getInstance().getEntitiesFromQuery(session, EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return forumMessageTypePartTypes;
    }
    
    public List<ForumMessageTypePartTypeTransfer> getForumMessageTypePartTypeTransfersByForumMessageType(UserVisit userVisit,
            ForumMessageType forumMessageType) {
        var forumMessageTypePartTypes = getForumMessageTypePartTypesByForumMessageType(forumMessageType);
        List<ForumMessageTypePartTypeTransfer> forumMessageTypePartTypeTransfers = null;
        
        if(forumMessageTypePartTypes != null) {
            var forumMessageTypePartTypeTransferCache = getForumTransferCaches(userVisit).getForumMessageTypePartTypeTransferCache();
            
            forumMessageTypePartTypeTransfers = new ArrayList<>(forumMessageTypePartTypes.size());
            
            for(var forumMessageTypePartType : forumMessageTypePartTypes) {
                forumMessageTypePartTypeTransfers.add(forumMessageTypePartTypeTransferCache.getForumMessageTypePartTypeTransfer(forumMessageTypePartType));
            }
        }
        
        return forumMessageTypePartTypeTransfers;
    }
    
    public ForumMessageTypePartTypeTransfer getForumMessageTypePartTypeTransfer(UserVisit userVisit, ForumMessageTypePartType forumMessageTypePartType) {
        return getForumTransferCaches(userVisit).getForumMessageTypePartTypeTransferCache().getForumMessageTypePartTypeTransfer(forumMessageTypePartType);
    }
    
}
