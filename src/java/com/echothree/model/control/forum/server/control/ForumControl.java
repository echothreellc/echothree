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
import com.echothree.model.control.forum.server.transfer.ForumDescriptionTransferCache;
import com.echothree.model.control.forum.server.transfer.ForumForumThreadTransferCache;
import com.echothree.model.control.forum.server.transfer.ForumGroupDescriptionTransferCache;
import com.echothree.model.control.forum.server.transfer.ForumGroupForumTransferCache;
import com.echothree.model.control.forum.server.transfer.ForumGroupTransferCache;
import com.echothree.model.control.forum.server.transfer.ForumMessageAttachmentDescriptionTransferCache;
import com.echothree.model.control.forum.server.transfer.ForumMessageAttachmentTransferCache;
import com.echothree.model.control.forum.server.transfer.ForumMessagePartTransferCache;
import com.echothree.model.control.forum.server.transfer.ForumMessagePartTypeTransferCache;
import com.echothree.model.control.forum.server.transfer.ForumMessageRoleTransferCache;
import com.echothree.model.control.forum.server.transfer.ForumMessageTransferCache;
import com.echothree.model.control.forum.server.transfer.ForumMessageTypePartTypeTransferCache;
import com.echothree.model.control.forum.server.transfer.ForumMessageTypeTransferCache;
import com.echothree.model.control.forum.server.transfer.ForumMimeTypeTransferCache;
import com.echothree.model.control.forum.server.transfer.ForumPartyRoleTransferCache;
import com.echothree.model.control.forum.server.transfer.ForumPartyTypeRoleTransferCache;
import com.echothree.model.control.forum.server.transfer.ForumRoleTypeTransferCache;
import com.echothree.model.control.forum.server.transfer.ForumThreadTransferCache;
import com.echothree.model.control.forum.server.transfer.ForumTransferCache;
import com.echothree.model.control.forum.server.transfer.ForumTypeTransferCache;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.core.server.entity.MimeTypeUsageType;
import com.echothree.model.data.forum.common.pk.ForumGroupPK;
import com.echothree.model.data.forum.common.pk.ForumMessageAttachmentPK;
import com.echothree.model.data.forum.common.pk.ForumMessagePK;
import com.echothree.model.data.forum.common.pk.ForumMessagePartPK;
import com.echothree.model.data.forum.common.pk.ForumMessagePartTypePK;
import com.echothree.model.data.forum.common.pk.ForumMessageTypePK;
import com.echothree.model.data.forum.common.pk.ForumPK;
import com.echothree.model.data.forum.common.pk.ForumRoleTypePK;
import com.echothree.model.data.forum.common.pk.ForumThreadPK;
import com.echothree.model.data.forum.common.pk.ForumTypePK;
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
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.COMPONENT_VENDORS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_INSTANCES_ENTITY_TYPE_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_TIMES_ENTITY_INSTANCE_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_TYPE_DETAILS_COMPONENT_VENDOR_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.MIME_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.forum.ForumForeignKeys.FORUMS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.forum.ForumForeignKeys.FORUMS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.forum.ForumForeignKeys.FORUM_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.forum.ForumForeignKeys.FORUM_FORUM_THREADS_FORUM_FK;
import static com.echothree.model.jooq.server.keys.forum.ForumForeignKeys.FORUM_FORUM_THREADS_FORUM_THREAD_FK;
import static com.echothree.model.jooq.server.keys.forum.ForumForeignKeys.FORUM_GROUPS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.forum.ForumForeignKeys.FORUM_GROUPS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.forum.ForumForeignKeys.FORUM_GROUP_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.forum.ForumForeignKeys.FORUM_GROUP_FORUMS_FORUM_FK;
import static com.echothree.model.jooq.server.keys.forum.ForumForeignKeys.FORUM_GROUP_FORUMS_FORUM_GROUP_FK;
import static com.echothree.model.jooq.server.keys.forum.ForumForeignKeys.FORUM_MESSAGES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.forum.ForumForeignKeys.FORUM_MESSAGE_ATTACHMENTS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.forum.ForumForeignKeys.FORUM_MESSAGE_ATTACHMENT_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.forum.ForumForeignKeys.FORUM_MESSAGE_PARTS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.forum.ForumForeignKeys.FORUM_MESSAGE_ROLES_FORUM_ROLE_TYPE_FK;
import static com.echothree.model.jooq.server.keys.forum.ForumForeignKeys.FORUM_MESSAGE_ROLES_PARTY_FK;
import static com.echothree.model.jooq.server.keys.forum.ForumForeignKeys.FORUM_MIME_TYPES_FORUM_FK;
import static com.echothree.model.jooq.server.keys.forum.ForumForeignKeys.FORUM_MIME_TYPES_MIME_TYPE_FK;
import static com.echothree.model.jooq.server.keys.forum.ForumForeignKeys.FORUM_PARTY_ROLES_FORUM_FK;
import static com.echothree.model.jooq.server.keys.forum.ForumForeignKeys.FORUM_PARTY_ROLES_FORUM_ROLE_TYPE_FK;
import static com.echothree.model.jooq.server.keys.forum.ForumForeignKeys.FORUM_PARTY_ROLES_PARTY_FK;
import static com.echothree.model.jooq.server.keys.forum.ForumForeignKeys.FORUM_PARTY_TYPE_ROLES_FORUM_ROLE_TYPE_FK;
import static com.echothree.model.jooq.server.keys.forum.ForumForeignKeys.FORUM_PARTY_TYPE_ROLES_PARTY_TYPE_FK;
import static com.echothree.model.jooq.server.keys.forum.ForumForeignKeys.FORUM_THREADS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.forum.ForumForeignKeys.FORUM_THREADS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.forum.ForumForeignKeys.FORUM_TYPE_MESSAGE_TYPES_FORUM_MESSAGE_TYPE_FK;
import static com.echothree.model.jooq.server.keys.party.PartyForeignKeys.PARTIES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.tables.core.ComponentVendorDetails.ComponentVendorDetails;
import static com.echothree.model.jooq.server.tables.core.ComponentVendors.ComponentVendors;
import static com.echothree.model.jooq.server.tables.core.EntityInstances.EntityInstances;
import static com.echothree.model.jooq.server.tables.core.EntityTimes.EntityTimes;
import static com.echothree.model.jooq.server.tables.core.EntityTypeDetails.EntityTypeDetails;
import static com.echothree.model.jooq.server.tables.core.EntityTypes.EntityTypes;
import static com.echothree.model.jooq.server.tables.core.MimeTypeDetails.MimeTypeDetails;
import static com.echothree.model.jooq.server.tables.core.MimeTypes.MimeTypes;
import static com.echothree.model.jooq.server.tables.forum.ForumBlobMessageParts.ForumBlobMessageParts;
import static com.echothree.model.jooq.server.tables.forum.ForumClobMessageParts.ForumClobMessageParts;
import static com.echothree.model.jooq.server.tables.forum.ForumDescriptions.ForumDescriptions;
import static com.echothree.model.jooq.server.tables.forum.ForumDetails.ForumDetails;
import static com.echothree.model.jooq.server.tables.forum.ForumForumThreads.ForumForumThreads;
import static com.echothree.model.jooq.server.tables.forum.ForumGroupDescriptions.ForumGroupDescriptions;
import static com.echothree.model.jooq.server.tables.forum.ForumGroupDetails.ForumGroupDetails;
import static com.echothree.model.jooq.server.tables.forum.ForumGroupForums.ForumGroupForums;
import static com.echothree.model.jooq.server.tables.forum.ForumGroups.ForumGroups;
import static com.echothree.model.jooq.server.tables.forum.ForumMessageAttachmentDescriptions.ForumMessageAttachmentDescriptions;
import static com.echothree.model.jooq.server.tables.forum.ForumMessageAttachmentDetails.ForumMessageAttachmentDetails;
import static com.echothree.model.jooq.server.tables.forum.ForumMessageAttachments.ForumMessageAttachments;
import static com.echothree.model.jooq.server.tables.forum.ForumMessageBlobAttachments.ForumMessageBlobAttachments;
import static com.echothree.model.jooq.server.tables.forum.ForumMessageClobAttachments.ForumMessageClobAttachments;
import static com.echothree.model.jooq.server.tables.forum.ForumMessageDetails.ForumMessageDetails;
import static com.echothree.model.jooq.server.tables.forum.ForumMessagePartDetails.ForumMessagePartDetails;
import static com.echothree.model.jooq.server.tables.forum.ForumMessagePartTypeDescriptions.ForumMessagePartTypeDescriptions;
import static com.echothree.model.jooq.server.tables.forum.ForumMessagePartTypes.ForumMessagePartTypes;
import static com.echothree.model.jooq.server.tables.forum.ForumMessageParts.ForumMessageParts;
import static com.echothree.model.jooq.server.tables.forum.ForumMessageRoles.ForumMessageRoles;
import static com.echothree.model.jooq.server.tables.forum.ForumMessageStatuses.ForumMessageStatuses;
import static com.echothree.model.jooq.server.tables.forum.ForumMessageTypeDescriptions.ForumMessageTypeDescriptions;
import static com.echothree.model.jooq.server.tables.forum.ForumMessageTypePartTypes.ForumMessageTypePartTypes;
import static com.echothree.model.jooq.server.tables.forum.ForumMessageTypes.ForumMessageTypes;
import static com.echothree.model.jooq.server.tables.forum.ForumMessages.ForumMessages;
import static com.echothree.model.jooq.server.tables.forum.ForumMimeTypes.ForumMimeTypes;
import static com.echothree.model.jooq.server.tables.forum.ForumPartyRoles.ForumPartyRoles;
import static com.echothree.model.jooq.server.tables.forum.ForumPartyTypeRoles.ForumPartyTypeRoles;
import static com.echothree.model.jooq.server.tables.forum.ForumRoleTypeDescriptions.ForumRoleTypeDescriptions;
import static com.echothree.model.jooq.server.tables.forum.ForumRoleTypes.ForumRoleTypes;
import static com.echothree.model.jooq.server.tables.forum.ForumStringMessageParts.ForumStringMessageParts;
import static com.echothree.model.jooq.server.tables.forum.ForumThreadDetails.ForumThreadDetails;
import static com.echothree.model.jooq.server.tables.forum.ForumThreads.ForumThreads;
import static com.echothree.model.jooq.server.tables.forum.ForumTypeDescriptions.ForumTypeDescriptions;
import static com.echothree.model.jooq.server.tables.forum.ForumTypeMessageTypes.ForumTypeMessageTypes;
import static com.echothree.model.jooq.server.tables.forum.ForumTypes.ForumTypes;
import static com.echothree.model.jooq.server.tables.forum.Forums.Forums;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import static com.echothree.model.jooq.server.tables.party.Parties.Parties;
import static com.echothree.model.jooq.server.tables.party.PartyDetails.PartyDetails;
import static com.echothree.model.jooq.server.tables.party.PartyTypes.PartyTypes;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;

@CommandScope
public class ForumControl
        extends BaseModelControl {
    
    @Inject
    protected MimeTypeControl mimeTypeControl;

    @Inject
    protected SequenceControl sequenceControl;

    @Inject
    protected SequenceGeneratorLogic sequenceGeneratorLogic;

    /** Creates a new instance of ForumControl */
    protected ForumControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Groups
    // --------------------------------------------------------------------------------

    @Inject
    protected ForumGroupDetailFactory forumGroupDetailFactory;

    @Inject
    protected ForumGroupFactory forumGroupFactory;

    @Inject
    ForumGroupTransferCache forumGroupTransferCache;

    public ForumGroup createForumGroup(String forumGroupName, Icon icon, Integer sortOrder, BasePK createdBy) {
        var forumGroup = forumGroupFactory.create();
        var forumGroupDetail = forumGroupDetailFactory.create(forumGroup, forumGroupName, icon,
                sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        forumGroup = forumGroupFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                forumGroup.getPrimaryKey());
        forumGroup.setActiveDetail(forumGroupDetail);
        forumGroup.setLastDetail(forumGroupDetail);
        forumGroup.store();
        
        sendEvent(forumGroup.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return forumGroup;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ForumGroup */
    public ForumGroup getForumGroupByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ForumGroupPK(entityInstance.getEntityUniqueId());

        return forumGroupFactory.getEntityFromPK(entityPermission, pk);
    }

    public ForumGroup getForumGroupByEntityInstance(EntityInstance entityInstance) {
        return getForumGroupByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ForumGroup getForumGroupByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getForumGroupByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countForumGroups() {
        return session.getDslContext()
                .selectCount()
                .from(ForumGroups)
                .join(ForumGroupDetails).onKey(FORUM_GROUPS_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public List<ForumGroup> getForumGroups() {
        var query = session.applyLimit(session.getDslContext()
                .select(ForumGroups.fields())
                .from(ForumGroups)
                .join(ForumGroupDetails).onKey(FORUM_GROUPS_ACTIVE_DETAIL_FK)
                .orderBy(ForumGroupDetails.SORT_ORDER, ForumGroupDetails.FORUM_GROUP_NAME),
                ForumGroupFactory.class);

        return forumGroupFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public ForumGroup getForumGroupByName(String forumGroupName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ForumGroups.fields())
                .from(ForumGroups)
                .join(ForumGroupDetails).onKey(FORUM_GROUPS_ACTIVE_DETAIL_FK)
                .where(ForumGroupDetails.FORUM_GROUP_NAME.eq(forumGroupName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return forumGroupFactory.getEntityFromQuery(entityPermission, query);
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
        return forumGroupTransferCache.getForumGroupTransfer(userVisit, forumGroup);
    }
    
    public List<ForumGroupTransfer> getForumGroupTransfers(UserVisit userVisit, Collection<ForumGroup> forumGroups) {
        List<ForumGroupTransfer> forumGroupTransfers = new ArrayList<>(forumGroups.size());
        
        forumGroups.forEach((forumGroup) ->
                forumGroupTransfers.add(forumGroupTransferCache.getForumGroupTransfer(userVisit, forumGroup))
        );
        
        return forumGroupTransfers;
    }
    
    public List<ForumGroupTransfer> getForumGroupTransfers(UserVisit userVisit) {
        return getForumGroupTransfers(userVisit, getForumGroups());
    }
    
    public void updateForumGroupFromValue(ForumGroupDetailValue forumGroupDetailValue, BasePK updatedBy) {
        if(forumGroupDetailValue.hasBeenModified()) {
            var forumGroup = forumGroupFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    forumGroupDetailValue.getForumGroupPK());
            var forumGroupDetail = forumGroup.getActiveDetailForUpdate();
            
            forumGroupDetail.setThruTime(session.getStartTime());
            forumGroupDetail.store();

            var forumGroupPK = forumGroupDetail.getForumGroupPK();
            var forumGroupName = forumGroupDetailValue.getForumGroupName();
            var iconPK = forumGroupDetailValue.getIconPK();
            var sortOrder = forumGroupDetailValue.getSortOrder();
            
            forumGroupDetail = forumGroupDetailFactory.create(forumGroupPK, forumGroupName, iconPK,
                    sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            forumGroup.setActiveDetail(forumGroupDetail);
            forumGroup.setLastDetail(forumGroupDetail);
            
            sendEvent(forumGroupPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deleteForumGroup(ForumGroup forumGroup, BasePK deletedBy) {
        deleteForumGroupDescriptionsByForumGroup(forumGroup, deletedBy);
        deleteForumGroupForumsByForumGroup(forumGroup, deletedBy);

        var forumGroupDetail = forumGroup.getLastDetailForUpdate();
        forumGroupDetail.setThruTime(session.getStartTime());
        forumGroup.setActiveDetail(null);
        forumGroup.store();
        
        sendEvent(forumGroup.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Group Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected ForumGroupDescriptionFactory forumGroupDescriptionFactory;

    @Inject
    ForumGroupDescriptionTransferCache forumGroupDescriptionTransferCache;

    public ForumGroupDescription createForumGroupDescription(ForumGroup forumGroup, Language language, String description, BasePK createdBy) {
        var forumGroupDescription = forumGroupDescriptionFactory.create(forumGroup,
                language, description, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(forumGroup.getPrimaryKey(), EventTypes.MODIFY, forumGroupDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return forumGroupDescription;
    }
    
    private ForumGroupDescription getForumGroupDescription(ForumGroup forumGroup, Language language,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ForumGroupDescriptions.fields())
                .from(ForumGroupDescriptions)
                .where(ForumGroupDescriptions.FORUM_GROUP.eq(forumGroup.getPrimaryKey()),
                        ForumGroupDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        ForumGroupDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return forumGroupDescriptionFactory.getEntityFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ForumGroupDescriptions.fields())
                    .from(ForumGroupDescriptions)
                    .join(Languages).onKey(FORUM_GROUP_DESCRIPTIONS_LANGUAGE_FK)
                    .where(ForumGroupDescriptions.FORUM_GROUP.eq(forumGroup.getPrimaryKey()),
                            ForumGroupDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    ForumGroupDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ForumGroupDescriptions.fields())
                    .from(ForumGroupDescriptions)
                    .where(ForumGroupDescriptions.FORUM_GROUP.eq(forumGroup.getPrimaryKey()),
                            ForumGroupDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return forumGroupDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
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
        return forumGroupDescriptionTransferCache.getForumGroupDescriptionTransfer(userVisit, forumGroupDescription);
    }
    
    public List<ForumGroupDescriptionTransfer> getForumGroupDescriptionTransfersByForumGroup(UserVisit userVisit,
            ForumGroup forumGroup) {
        var forumGroupDescriptions = getForumGroupDescriptionsByForumGroup(forumGroup);
        List<ForumGroupDescriptionTransfer> forumGroupDescriptionTransfers = null;
        
        if(forumGroupDescriptions != null) {
            
            forumGroupDescriptionTransfers = new ArrayList<>(forumGroupDescriptions.size());
            
            for(var forumGroupDescription : forumGroupDescriptions) {
                forumGroupDescriptionTransfers.add(forumGroupDescriptionTransferCache.getForumGroupDescriptionTransfer(userVisit, forumGroupDescription));
            }
        }
        
        return forumGroupDescriptionTransfers;
    }
    
    public void updateForumGroupDescriptionFromValue(ForumGroupDescriptionValue forumGroupDescriptionValue, BasePK updatedBy) {
        if(forumGroupDescriptionValue.hasBeenModified()) {
            var forumGroupDescription = forumGroupDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     forumGroupDescriptionValue.getPrimaryKey());
            
            forumGroupDescription.setThruTime(session.getStartTime());
            forumGroupDescription.store();

            var forumGroup = forumGroupDescription.getForumGroup();
            var language = forumGroupDescription.getLanguage();
            var description = forumGroupDescriptionValue.getDescription();
            
            forumGroupDescription = forumGroupDescriptionFactory.create(forumGroup, language, description,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(forumGroup.getPrimaryKey(), EventTypes.MODIFY, forumGroupDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteForumGroupDescription(ForumGroupDescription forumGroupDescription, BasePK deletedBy) {
        forumGroupDescription.setThruTime(session.getStartTime());
        
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

    @Inject
    protected ForumDetailFactory forumDetailFactory;

    @Inject
    protected ForumFactory forumFactory;

    @Inject
    ForumTransferCache forumTransferCache;

    public Forum createForum(String forumName, ForumType forumType, Icon icon, Sequence forumThreadSequence,
            Sequence forumMessageSequence, Integer sortOrder, BasePK createdBy) {
        var forum = forumFactory.create();
        var forumDetail = forumDetailFactory.create(forum, forumName, forumType, icon,
                forumThreadSequence, forumMessageSequence, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        forum = forumFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                forum.getPrimaryKey());
        forum.setActiveDetail(forumDetail);
        forum.setLastDetail(forumDetail);
        forum.store();
        
        sendEvent(forum.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return forum;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.Forum */
    public Forum getForumByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ForumPK(entityInstance.getEntityUniqueId());

        return forumFactory.getEntityFromPK(entityPermission, pk);
    }

    public Forum getForumByEntityInstance(EntityInstance entityInstance) {
        return getForumByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public Forum getForumByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getForumByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countForums() {
        return session.getDslContext()
                .selectCount()
                .from(Forums)
                .join(ForumDetails).onKey(FORUMS_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public List<Forum> getForums() {
        var query = session.applyLimit(session.getDslContext()
                .select(Forums.fields())
                .from(Forums)
                .join(ForumDetails).onKey(FORUMS_ACTIVE_DETAIL_FK)
                .orderBy(ForumDetails.SORT_ORDER, ForumDetails.FORUM_NAME),
                ForumFactory.class);

        return forumFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public Forum getForumByName(String forumName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Forums.fields())
                .from(Forums)
                .join(ForumDetails).onKey(FORUMS_ACTIVE_DETAIL_FK)
                .where(ForumDetails.FORUM_NAME.eq(forumName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return forumFactory.getEntityFromQuery(entityPermission, query);
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
        return forumTransferCache.getForumTransfer(userVisit, forum);
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
        
        forums.forEach((forum) ->
                forumTransfers.add(forumTransferCache.getForumTransfer(userVisit, forum))
        );
        
        return forumTransfers;
    }
    
    public List<ForumTransfer> getForumTransfers(UserVisit userVisit) {
        return getForumTransfers(userVisit, getForums());
    }
    
    public void updateForumFromValue(ForumDetailValue forumDetailValue, BasePK updatedBy) {
        if(forumDetailValue.hasBeenModified()) {
            var forum = forumFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    forumDetailValue.getForumPK());
            var forumDetail = forum.getActiveDetailForUpdate();
            
            forumDetail.setThruTime(session.getStartTime());
            forumDetail.store();

            var forumPK = forumDetail.getForumPK();
            var forumName = forumDetailValue.getForumName();
            var forumTypePK = forumDetail.getForumTypePK(); // Not updated
            var iconPK = forumDetailValue.getIconPK();
            var forumThreadSequencePK = forumDetailValue.getForumThreadSequencePK();
            var forumMessageSequencePK = forumDetailValue.getForumMessageSequencePK();
            var sortOrder = forumDetailValue.getSortOrder();
            
            forumDetail = forumDetailFactory.create(forumPK, forumName, forumTypePK, iconPK,
                    forumThreadSequencePK, forumMessageSequencePK, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
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
        forumDetail.setThruTime(session.getStartTime());
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

    @Inject
    protected ForumDescriptionFactory forumDescriptionFactory;

    @Inject
    ForumDescriptionTransferCache forumDescriptionTransferCache;

    public ForumDescription createForumDescription(Forum forum, Language language, String description, BasePK createdBy) {
        var forumDescription = forumDescriptionFactory.create(forum, language, description,
                session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(forum.getPrimaryKey(), EventTypes.MODIFY, forumDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return forumDescription;
    }
    
    private ForumDescription getForumDescription(Forum forum, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ForumDescriptions.fields())
                .from(ForumDescriptions)
                .where(ForumDescriptions.FORUM.eq(forum.getPrimaryKey()),
                        ForumDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        ForumDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return forumDescriptionFactory.getEntityFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ForumDescriptions.fields())
                    .from(ForumDescriptions)
                    .join(Languages).onKey(FORUM_DESCRIPTIONS_LANGUAGE_FK)
                    .where(ForumDescriptions.FORUM.eq(forum.getPrimaryKey()),
                            ForumDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    ForumDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ForumDescriptions.fields())
                    .from(ForumDescriptions)
                    .where(ForumDescriptions.FORUM.eq(forum.getPrimaryKey()),
                            ForumDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return forumDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
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
        return forumDescriptionTransferCache.getForumDescriptionTransfer(userVisit, forumDescription);
    }
    
    public List<ForumDescriptionTransfer> getForumDescriptionTransfersByForum(UserVisit userVisit, Forum forum) {
        var forumDescriptions = getForumDescriptionsByForum(forum);
        List<ForumDescriptionTransfer> forumDescriptionTransfers = null;
        
        if(forumDescriptions != null) {
            
            forumDescriptionTransfers = new ArrayList<>(forumDescriptions.size());
            
            for(var forumDescription : forumDescriptions) {
                forumDescriptionTransfers.add(forumDescriptionTransferCache.getForumDescriptionTransfer(userVisit, forumDescription));
            }
        }
        
        return forumDescriptionTransfers;
    }
    
    public void updateForumDescriptionFromValue(ForumDescriptionValue forumDescriptionValue, BasePK updatedBy) {
        if(forumDescriptionValue.hasBeenModified()) {
            var forumDescription = forumDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     forumDescriptionValue.getPrimaryKey());
            
            forumDescription.setThruTime(session.getStartTime());
            forumDescription.store();

            var forum = forumDescription.getForum();
            var language = forumDescription.getLanguage();
            var description = forumDescriptionValue.getDescription();
            
            forumDescription = forumDescriptionFactory.create(forum, language, description,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(forum.getPrimaryKey(), EventTypes.MODIFY, forumDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteForumDescription(ForumDescription forumDescription, BasePK deletedBy) {
        forumDescription.setThruTime(session.getStartTime());
        
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

    @Inject
    protected ForumGroupForumFactory forumGroupForumFactory;

    @Inject
    ForumGroupForumTransferCache forumGroupForumTransferCache;

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

        var forumGroupForum = forumGroupForumFactory.create(forumGroup, forum,
                isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(forumGroup.getPrimaryKey(), EventTypes.MODIFY, forumGroupForum.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return forumGroupForum;
    }

    public long countForumGroupForumsByForumGroup(ForumGroup forumGroup) {
        return session.getDslContext()
                .selectCount()
                .from(ForumGroupForums)
                .where(ForumGroupForums.FORUM_GROUP.eq(forumGroup.getPrimaryKey()),
                        ForumGroupForums.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countForumGroupForumsByForum(Forum forum) {
        return session.getDslContext()
                .selectCount()
                .from(ForumGroupForums)
                .where(ForumGroupForums.FORUM.eq(forum.getPrimaryKey()),
                        ForumGroupForums.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private ForumGroupForum getForumGroupForum(ForumGroup forumGroup, Forum forum, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ForumGroupForums.fields())
                .from(ForumGroupForums)
                .where(ForumGroupForums.FORUM_GROUP.eq(forumGroup.getPrimaryKey()),
                        ForumGroupForums.FORUM.eq(forum.getPrimaryKey()),
                        ForumGroupForums.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return forumGroupForumFactory.getEntityFromQuery(entityPermission, query);
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
        var baseQuery = session.getDslContext()
                .select(ForumGroupForums.fields())
                .from(ForumGroupForums)
                .where(ForumGroupForums.FORUM.eq(forum.getPrimaryKey()),
                        ForumGroupForums.IS_DEFAULT.eq(true),
                        ForumGroupForums.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return forumGroupForumFactory.getEntityFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ForumGroupForums.fields())
                    .from(ForumGroupForums)
                    .join(Forums).onKey(FORUM_GROUP_FORUMS_FORUM_FK)
                    .join(ForumDetails).onKey(FORUMS_LAST_DETAIL_FK)
                    .where(ForumGroupForums.FORUM_GROUP.eq(forumGroup.getPrimaryKey()),
                            ForumGroupForums.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ForumDetails.SORT_ORDER, ForumDetails.FORUM_NAME),
                    ForumGroupForumFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ForumGroupForums.fields())
                    .from(ForumGroupForums)
                    .where(ForumGroupForums.FORUM_GROUP.eq(forumGroup.getPrimaryKey()),
                            ForumGroupForums.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return forumGroupForumFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ForumGroupForum> getForumGroupForumsByForumGroup(ForumGroup forumGroup) {
        return getForumGroupForumsByForumGroup(forumGroup, EntityPermission.READ_ONLY);
    }
    
    public List<ForumGroupForum> getForumGroupForumsByForumGroupForUpdate(ForumGroup forumGroup) {
        return getForumGroupForumsByForumGroup(forumGroup, EntityPermission.READ_WRITE);
    }
    
    private List<ForumGroupForum> getForumGroupForumsByForum(Forum forum, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ForumGroupForums.fields())
                    .from(ForumGroupForums)
                    .join(ForumGroups).onKey(FORUM_GROUP_FORUMS_FORUM_GROUP_FK)
                    .join(ForumGroupDetails).onKey(FORUM_GROUPS_LAST_DETAIL_FK)
                    .where(ForumGroupForums.FORUM.eq(forum.getPrimaryKey()),
                            ForumGroupForums.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ForumGroupDetails.SORT_ORDER, ForumGroupDetails.FORUM_GROUP_NAME),
                    ForumGroupForumFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ForumGroupForums.fields())
                    .from(ForumGroupForums)
                    .where(ForumGroupForums.FORUM.eq(forum.getPrimaryKey()),
                            ForumGroupForums.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return forumGroupForumFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ForumGroupForum> getForumGroupForumsByForum(Forum forum) {
        return getForumGroupForumsByForum(forum, EntityPermission.READ_ONLY);
    }
    
    public List<ForumGroupForum> getForumGroupForumsByForumForUpdate(Forum forum) {
        return getForumGroupForumsByForum(forum, EntityPermission.READ_WRITE);
    }
    
    public List<ForumGroupForumTransfer> getForumGroupForumTransfers(UserVisit userVisit, Collection<ForumGroupForum> forumGroupForums) {
        List<ForumGroupForumTransfer> forumGroupForumTransfers = new ArrayList<>(forumGroupForums.size());
        
        forumGroupForums.forEach((forumGroupForum) ->
                forumGroupForumTransfers.add(forumGroupForumTransferCache.getForumGroupForumTransfer(userVisit, forumGroupForum))
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
        return forumGroupForumTransferCache.getForumGroupForumTransfer(userVisit, forumGroupForum);
    }
    
    private void updateForumGroupForumFromValue(ForumGroupForumValue forumGroupForumValue, boolean checkDefault, BasePK updatedBy) {
        if(forumGroupForumValue.hasBeenModified()) {
            var forumGroupForum = forumGroupForumFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     forumGroupForumValue.getPrimaryKey());
            
            forumGroupForum.setThruTime(session.getStartTime());
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
            
            forumGroupForum = forumGroupForumFactory.create(forumGroupPK, forum.getPrimaryKey(), isDefault,
                    sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(forumGroupPK, EventTypes.MODIFY, forumGroupForum.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void updateForumGroupForumFromValue(ForumGroupForumValue forumGroupForumValue, BasePK updatedBy) {
        updateForumGroupForumFromValue(forumGroupForumValue, true, updatedBy);
    }
    
    public void deleteForumGroupForum(ForumGroupForum forumGroupForum, BasePK deletedBy) {
        forumGroupForum.setThruTime(session.getStartTime());
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

    @Inject
    protected ForumRoleTypeFactory forumRoleTypeFactory;

    @Inject
    ForumRoleTypeTransferCache forumRoleTypeTransferCache;

    public ForumRoleType createForumRoleType(String forumRoleTypeName, Boolean isDefault, Integer sortOrder) {
        return forumRoleTypeFactory.create(forumRoleTypeName, isDefault, sortOrder);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ForumRoleType */
    public ForumRoleType getForumRoleTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ForumRoleTypePK(entityInstance.getEntityUniqueId());

        return forumRoleTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public ForumRoleType getForumRoleTypeByEntityInstance(EntityInstance entityInstance) {
        return getForumRoleTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ForumRoleType getForumRoleTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getForumRoleTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countForumRoleTypes() {
        return session.getDslContext()
                .selectCount()
                .from(ForumRoleTypes)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public ForumRoleType getForumRoleTypeByName(String forumRoleTypeName) {
        var query = session.getDslContext()
                .select(ForumRoleTypes.fields())
                .from(ForumRoleTypes)
                .where(ForumRoleTypes.FORUM_ROLE_TYPE_NAME.eq(forumRoleTypeName));

        return forumRoleTypeFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public List<ForumRoleType> getForumRoleTypes() {
        var query = session.applyLimit(session.getDslContext()
                .select(ForumRoleTypes.fields())
                .from(ForumRoleTypes)
                .orderBy(ForumRoleTypes.SORT_ORDER, ForumRoleTypes.FORUM_ROLE_TYPE_NAME),
                ForumRoleTypeFactory.class);

        return forumRoleTypeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
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
        return forumRoleTypeTransferCache.getForumRoleTypeTransfer(userVisit, forumRoleType);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Role Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected ForumRoleTypeDescriptionFactory forumRoleTypeDescriptionFactory;

    public ForumRoleTypeDescription createForumRoleTypeDescription(ForumRoleType forumRoleType, Language language,
            String description) {
        return forumRoleTypeDescriptionFactory.create(forumRoleType, language, description);
    }
    
    public ForumRoleTypeDescription getForumRoleTypeDescription(ForumRoleType forumRoleType, Language language) {
        var query = session.getDslContext()
                .select(ForumRoleTypeDescriptions.fields())
                .from(ForumRoleTypeDescriptions)
                .where(ForumRoleTypeDescriptions.FORUM_ROLE_TYPE.eq(forumRoleType.getPrimaryKey()),
                        ForumRoleTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()));

        return forumRoleTypeDescriptionFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
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

    @Inject
    protected ForumTypeFactory forumTypeFactory;

    @Inject
    ForumTypeTransferCache forumTypeTransferCache;

    public ForumType createForumType(String forumTypeName, Boolean isDefault, Integer sortOrder) {
        return forumTypeFactory.create(forumTypeName, isDefault, sortOrder);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ForumType */
    public ForumType getForumTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ForumTypePK(entityInstance.getEntityUniqueId());

        return forumTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public ForumType getForumTypeByEntityInstance(EntityInstance entityInstance) {
        return getForumTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ForumType getForumTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getForumTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countForumTypes() {
        return session.getDslContext()
                .selectCount()
                .from(ForumTypes)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public ForumType getForumTypeByName(String forumTypeName) {
        var query = session.getDslContext()
                .select(ForumTypes.fields())
                .from(ForumTypes)
                .where(ForumTypes.FORUM_TYPE_NAME.eq(forumTypeName));

        return forumTypeFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public List<ForumType> getForumTypes() {
        var query = session.applyLimit(session.getDslContext()
                .select(ForumTypes.fields())
                .from(ForumTypes)
                .orderBy(ForumTypes.SORT_ORDER, ForumTypes.FORUM_TYPE_NAME),
                ForumTypeFactory.class);

        return forumTypeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
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
        return forumTypeTransferCache.getForumTypeTransfer(userVisit, forumType);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected ForumTypeDescriptionFactory forumTypeDescriptionFactory;

    public ForumTypeDescription createForumTypeDescription(ForumType forumType, Language language, String description) {
        return forumTypeDescriptionFactory.create(forumType, language, description);
    }
    
    public ForumTypeDescription getForumTypeDescription(ForumType forumType, Language language) {
        var query = session.getDslContext()
                .select(ForumTypeDescriptions.fields())
                .from(ForumTypeDescriptions)
                .where(ForumTypeDescriptions.FORUM_TYPE.eq(forumType.getPrimaryKey()),
                        ForumTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()));

        return forumTypeDescriptionFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
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

    @Inject
    protected ForumMimeTypeFactory forumMimeTypeFactory;

    @Inject
    ForumMimeTypeTransferCache forumMimeTypeTransferCache;

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

        var forumMimeType = forumMimeTypeFactory.create(forum, mimeType,
                isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(forum.getPrimaryKey(), EventTypes.MODIFY, forumMimeType.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return forumMimeType;
    }

    public long countForumMimeTypeByForum(final Forum forum) {
        return session.getDslContext()
                .selectCount()
                .from(ForumMimeTypes)
                .where(ForumMimeTypes.FORUM.eq(forum.getPrimaryKey()),
                        ForumMimeTypes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countForumMimeTypeByMimeType(final MimeType mimeType) {
        return session.getDslContext()
                .selectCount()
                .from(ForumMimeTypes)
                .where(ForumMimeTypes.MIME_TYPE.eq(mimeType.getPrimaryKey()),
                        ForumMimeTypes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private ForumMimeType getForumMimeType(Forum forum, MimeType mimeType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ForumMimeTypes.fields())
                .from(ForumMimeTypes)
                .where(ForumMimeTypes.FORUM.eq(forum.getPrimaryKey()),
                        ForumMimeTypes.MIME_TYPE.eq(mimeType.getPrimaryKey()),
                        ForumMimeTypes.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return forumMimeTypeFactory.getEntityFromQuery(entityPermission, query);
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
        var baseQuery = session.getDslContext()
                .select(ForumMimeTypes.fields())
                .from(ForumMimeTypes)
                .where(ForumMimeTypes.FORUM.eq(forum.getPrimaryKey()),
                        ForumMimeTypes.IS_DEFAULT.eq(true),
                        ForumMimeTypes.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return forumMimeTypeFactory.getEntityFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ForumMimeTypes.fields())
                    .from(ForumMimeTypes)
                    .join(MimeTypes).onKey(FORUM_MIME_TYPES_MIME_TYPE_FK)
                    .join(MimeTypeDetails).onKey(MIME_TYPES_LAST_DETAIL_FK)
                    .where(ForumMimeTypes.FORUM.eq(forum.getPrimaryKey()),
                            ForumMimeTypes.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ForumMimeTypes.SORT_ORDER, MimeTypeDetails.SORT_ORDER, MimeTypeDetails.MIME_TYPE_NAME),
                    ForumMimeTypeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ForumMimeTypes.fields())
                    .from(ForumMimeTypes)
                    .where(ForumMimeTypes.FORUM.eq(forum.getPrimaryKey()),
                            ForumMimeTypes.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return forumMimeTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ForumMimeType> getForumMimeTypesByForum(Forum forum) {
        return getForumMimeTypesByForum(forum, EntityPermission.READ_ONLY);
    }
    
    public List<ForumMimeType> getForumMimeTypesByForumForUpdate(Forum forum) {
        return getForumMimeTypesByForum(forum, EntityPermission.READ_WRITE);
    }
    
    private List<ForumMimeType> getForumMimeTypesByMimeType(MimeType mimeType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ForumMimeTypes.fields())
                    .from(ForumMimeTypes)
                    .join(Forums).onKey(FORUM_MIME_TYPES_FORUM_FK)
                    .join(ForumDetails).onKey(FORUMS_LAST_DETAIL_FK)
                    .where(ForumMimeTypes.MIME_TYPE.eq(mimeType.getPrimaryKey()),
                            ForumMimeTypes.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ForumMimeTypes.SORT_ORDER, ForumDetails.SORT_ORDER, ForumDetails.FORUM_NAME),
                    ForumMimeTypeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ForumMimeTypes.fields())
                    .from(ForumMimeTypes)
                    .where(ForumMimeTypes.MIME_TYPE.eq(mimeType.getPrimaryKey()),
                            ForumMimeTypes.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return forumMimeTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ForumMimeType> getForumMimeTypesByMimeType(MimeType mimeType) {
        return getForumMimeTypesByMimeType(mimeType, EntityPermission.READ_ONLY);
    }
    
    public List<ForumMimeType> getForumMimeTypesByMimeTypeForUpdate(MimeType mimeType) {
        return getForumMimeTypesByMimeType(mimeType, EntityPermission.READ_WRITE);
    }
    
    public MimeTypeChoicesBean getForumMimeTypeChoices(Forum forum, String defaultMimeTypeChoice, Language language,
            boolean allowNullChoice) {
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
        
        forumMimeTypes.forEach((forumMimeType) ->
                forumMimeTypeTransfers.add(forumMimeTypeTransferCache.getForumMimeTypeTransfer(userVisit, forumMimeType))
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
        return forumMimeTypeTransferCache.getForumMimeTypeTransfer(userVisit, forumMimeType);
    }
    
    private void updateForumMimeTypeFromValue(ForumMimeTypeValue forumMimeTypeValue, boolean checkDefault, BasePK updatedBy) {
        if(forumMimeTypeValue.hasBeenModified()) {
            var forumMimeType = forumMimeTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     forumMimeTypeValue.getPrimaryKey());
            
            forumMimeType.setThruTime(session.getStartTime());
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
            
            forumMimeType = forumMimeTypeFactory.create(forumPK, mimeTypePK,
                    isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(forumPK, EventTypes.MODIFY, forumMimeType.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void updateForumMimeTypeFromValue(ForumMimeTypeValue forumMimeTypeValue, BasePK updatedBy) {
        updateForumMimeTypeFromValue(forumMimeTypeValue, true, updatedBy);
    }
    
    public void deleteForumMimeType(ForumMimeType forumMimeType, BasePK deletedBy) {
        forumMimeType.setThruTime(session.getStartTime());
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

    @Inject
    protected ForumPartyRoleFactory forumPartyRoleFactory;

    @Inject
    ForumPartyRoleTransferCache forumPartyRoleTransferCache;

    public ForumPartyRole createForumPartyRole(Forum forum, Party party, ForumRoleType forumRoleType, BasePK createdBy) {
        var forumPartyRole = forumPartyRoleFactory.create(forum, party, forumRoleType,
                session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(forum.getPrimaryKey(), EventTypes.MODIFY, forumPartyRole.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return forumPartyRole;
    }

    public long countForumPartyRoleByForum(Forum forum) {
        return session.getDslContext()
                .selectCount()
                .from(ForumPartyRoles)
                .where(ForumPartyRoles.FORUM.eq(forum.getPrimaryKey()),
                        ForumPartyRoles.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countForumPartyRoleByParty(Party party) {
        return session.getDslContext()
                .selectCount()
                .from(ForumPartyRoles)
                .where(ForumPartyRoles.PARTY.eq(party.getPrimaryKey()),
                        ForumPartyRoles.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countForumPartyRoleByForumRoleType(ForumRoleType forumRoleType) {
        return session.getDslContext()
                .selectCount()
                .from(ForumPartyRoles)
                .where(ForumPartyRoles.FORUM_ROLE_TYPE.eq(forumRoleType.getPrimaryKey()),
                        ForumPartyRoles.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public boolean hasForumPartyRoles(Forum forum, ForumRoleType forumRoleType) {
        return session.getDslContext()
                .selectCount()
                .from(ForumPartyRoles)
                .where(ForumPartyRoles.FORUM.eq(forum.getPrimaryKey()),
                        ForumPartyRoles.FORUM_ROLE_TYPE.eq(forumRoleType.getPrimaryKey()),
                        ForumPartyRoles.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L) != 0L;
    }

    public boolean hasForumPartyRole(Forum forum, Party party, ForumRoleType forumRoleType) {
        return session.getDslContext()
                .selectCount()
                .from(ForumPartyRoles)
                .where(ForumPartyRoles.FORUM.eq(forum.getPrimaryKey()),
                        ForumPartyRoles.PARTY.eq(party.getPrimaryKey()),
                        ForumPartyRoles.FORUM_ROLE_TYPE.eq(forumRoleType.getPrimaryKey()),
                        ForumPartyRoles.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L) != 0L;
    }

    private List<ForumPartyRole> getForumPartyRolesByForum(Forum forum, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ForumPartyRoles.fields())
                    .from(ForumPartyRoles)
                    .join(Parties).onKey(FORUM_PARTY_ROLES_PARTY_FK)
                    .join(PartyDetails).onKey(PARTIES_LAST_DETAIL_FK)
                    .join(ForumRoleTypes).onKey(FORUM_PARTY_ROLES_FORUM_ROLE_TYPE_FK)
                    .where(ForumPartyRoles.FORUM.eq(forum.getPrimaryKey()),
                            ForumPartyRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(PartyDetails.PARTY_NAME, ForumRoleTypes.SORT_ORDER, ForumRoleTypes.FORUM_ROLE_TYPE_NAME),
                    ForumPartyRoleFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ForumPartyRoles.fields())
                    .from(ForumPartyRoles)
                    .where(ForumPartyRoles.FORUM.eq(forum.getPrimaryKey()),
                            ForumPartyRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return forumPartyRoleFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ForumPartyRole> getForumPartyRolesByForum(Forum forum) {
        return getForumPartyRolesByForum(forum, EntityPermission.READ_ONLY);
    }
    
    public List<ForumPartyRole> getForumPartyRolesByForumForUpdate(Forum forum) {
        return getForumPartyRolesByForum(forum, EntityPermission.READ_WRITE);
    }
    
    private List<ForumPartyRole> getForumPartyRolesByParty(Party party, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ForumPartyRoles.fields())
                    .from(ForumPartyRoles)
                    .join(Forums).onKey(FORUM_PARTY_ROLES_FORUM_FK)
                    .join(ForumDetails).onKey(FORUMS_LAST_DETAIL_FK)
                    .join(ForumRoleTypes).onKey(FORUM_PARTY_ROLES_FORUM_ROLE_TYPE_FK)
                    .where(ForumPartyRoles.PARTY.eq(party.getPrimaryKey()),
                            ForumPartyRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ForumDetails.SORT_ORDER, ForumDetails.FORUM_NAME, ForumRoleTypes.SORT_ORDER, ForumRoleTypes.FORUM_ROLE_TYPE_NAME),
                    ForumPartyRoleFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ForumPartyRoles.fields())
                    .from(ForumPartyRoles)
                    .where(ForumPartyRoles.PARTY.eq(party.getPrimaryKey()),
                            ForumPartyRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return forumPartyRoleFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ForumPartyRole> getForumPartyRolesByParty(Party party) {
        return getForumPartyRolesByParty(party, EntityPermission.READ_ONLY);
    }
    
    public List<ForumPartyRole> getForumPartyRolesByPartyForUpdate(Party party) {
        return getForumPartyRolesByParty(party, EntityPermission.READ_WRITE);
    }
    
    private ForumPartyRole getForumPartyRole(Forum forum, Party party, ForumRoleType forumRoleType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ForumPartyRoles.fields())
                .from(ForumPartyRoles)
                .where(ForumPartyRoles.FORUM.eq(forum.getPrimaryKey()),
                        ForumPartyRoles.PARTY.eq(party.getPrimaryKey()),
                        ForumPartyRoles.FORUM_ROLE_TYPE.eq(forumRoleType.getPrimaryKey()),
                        ForumPartyRoles.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return forumPartyRoleFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ForumPartyRole getForumPartyRole(Forum forum, Party party, ForumRoleType forumRoleType) {
        return getForumPartyRole(forum, party, forumRoleType, EntityPermission.READ_ONLY);
    }
    
    public ForumPartyRole getForumPartyRoleForUpdate(Forum forum, Party party, ForumRoleType forumRoleType) {
        return getForumPartyRole(forum, party, forumRoleType, EntityPermission.READ_WRITE);
    }
    
    public List<ForumPartyRoleTransfer> getForumPartyRoleTransfers(UserVisit userVisit, Collection<ForumPartyRole> forumPartyRoles) {
        List<ForumPartyRoleTransfer> forumPartyRoleTransfers = null;

        if(forumPartyRoles != null) {
            forumPartyRoleTransfers = new ArrayList<>(forumPartyRoles.size());

            for(var forumPartyRole : forumPartyRoles) {
                forumPartyRoleTransfers.add(forumPartyRoleTransferCache.getForumPartyRoleTransfer(userVisit, forumPartyRole));
            }
        }

        return forumPartyRoleTransfers;
    }

    public List<ForumPartyRoleTransfer> getForumPartyRoleTransfersByForum(UserVisit userVisit, Forum forum) {
        return getForumPartyRoleTransfers(userVisit, getForumPartyRolesByForum(forum));
    }
    
    public ForumPartyRoleTransfer getForumPartyRoleTransfer(UserVisit userVisit, ForumPartyRole forumPartyRole) {
        return forumPartyRoleTransferCache.getForumPartyRoleTransfer(userVisit, forumPartyRole);
    }
    
    public void deleteForumPartyRole(ForumPartyRole forumPartyRole, BasePK deletedBy) {
        forumPartyRole.setThruTime(session.getStartTime());
        
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

    @Inject
    protected ForumPartyTypeRoleFactory forumPartyTypeRoleFactory;

    @Inject
    ForumPartyTypeRoleTransferCache forumPartyTypeRoleTransferCache;

    public ForumPartyTypeRole createForumPartyTypeRole(Forum forum, PartyType partyType, ForumRoleType forumRoleType,
            BasePK createdBy) {
        var forumPartyTypeRole = forumPartyTypeRoleFactory.create(forum, partyType,
                forumRoleType, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(forum.getPrimaryKey(), EventTypes.MODIFY, forumPartyTypeRole.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return forumPartyTypeRole;
    }

    public long countForumPartyTypeRolesByForum(Forum forum) {
        return session.getDslContext()
                .selectCount()
                .from(ForumPartyTypeRoles)
                .where(ForumPartyTypeRoles.FORUM.eq(forum.getPrimaryKey()),
                        ForumPartyTypeRoles.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countForumPartyTypeRolesByPartyType(PartyType partyType) {
        return session.getDslContext()
                .selectCount()
                .from(ForumPartyTypeRoles)
                .where(ForumPartyTypeRoles.PARTY_TYPE.eq(partyType.getPrimaryKey()),
                        ForumPartyTypeRoles.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countForumPartyTypeRolesByForumRoleType(ForumRoleType forumRoleType) {
        return session.getDslContext()
                .selectCount()
                .from(ForumPartyTypeRoles)
                .where(ForumPartyTypeRoles.FORUM_ROLE_TYPE.eq(forumRoleType.getPrimaryKey()),
                        ForumPartyTypeRoles.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public boolean hasForumPartyTypeRoles(Forum forum, ForumRoleType forumRoleType) {
        return session.getDslContext()
                .selectCount()
                .from(ForumPartyTypeRoles)
                .where(ForumPartyTypeRoles.FORUM.eq(forum.getPrimaryKey()),
                        ForumPartyTypeRoles.FORUM_ROLE_TYPE.eq(forumRoleType.getPrimaryKey()),
                        ForumPartyTypeRoles.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L) != 0L;
    }

    public boolean hasForumPartyTypeRole(Forum forum, PartyType partyType, ForumRoleType forumRoleType) {
        return session.getDslContext()
                .selectCount()
                .from(ForumPartyTypeRoles)
                .where(ForumPartyTypeRoles.FORUM.eq(forum.getPrimaryKey()),
                        ForumPartyTypeRoles.PARTY_TYPE.eq(partyType.getPrimaryKey()),
                        ForumPartyTypeRoles.FORUM_ROLE_TYPE.eq(forumRoleType.getPrimaryKey()),
                        ForumPartyTypeRoles.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L) != 0L;
    }

    private List<ForumPartyTypeRole> getForumPartyTypeRolesByForum(Forum forum, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ForumPartyTypeRoles.fields())
                    .from(ForumPartyTypeRoles)
                    .join(PartyTypes).onKey(FORUM_PARTY_TYPE_ROLES_PARTY_TYPE_FK)
                    .join(ForumRoleTypes).onKey(FORUM_PARTY_TYPE_ROLES_FORUM_ROLE_TYPE_FK)
                    .where(ForumPartyTypeRoles.FORUM.eq(forum.getPrimaryKey()),
                            ForumPartyTypeRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(PartyTypes.SORT_ORDER, PartyTypes.PARTY_TYPE_NAME, ForumRoleTypes.SORT_ORDER, ForumRoleTypes.FORUM_ROLE_TYPE_NAME),
                    ForumPartyTypeRoleFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ForumPartyTypeRoles.fields())
                    .from(ForumPartyTypeRoles)
                    .where(ForumPartyTypeRoles.FORUM.eq(forum.getPrimaryKey()),
                            ForumPartyTypeRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return forumPartyTypeRoleFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ForumPartyTypeRole> getForumPartyTypeRolesByForum(Forum forum) {
        return getForumPartyTypeRolesByForum(forum, EntityPermission.READ_ONLY);
    }
    
    public List<ForumPartyTypeRole> getForumPartyTypeRolesByForumForUpdate(Forum forum) {
        return getForumPartyTypeRolesByForum(forum, EntityPermission.READ_WRITE);
    }
    
    private ForumPartyTypeRole getForumPartyTypeRole(Forum forum, PartyType partyType, ForumRoleType forumRoleType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ForumPartyTypeRoles.fields())
                .from(ForumPartyTypeRoles)
                .where(ForumPartyTypeRoles.FORUM.eq(forum.getPrimaryKey()),
                        ForumPartyTypeRoles.PARTY_TYPE.eq(partyType.getPrimaryKey()),
                        ForumPartyTypeRoles.FORUM_ROLE_TYPE.eq(forumRoleType.getPrimaryKey()),
                        ForumPartyTypeRoles.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return forumPartyTypeRoleFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ForumPartyTypeRole getForumPartyTypeRole(Forum forum, PartyType partyType, ForumRoleType forumRoleType) {
        return getForumPartyTypeRole(forum, partyType, forumRoleType, EntityPermission.READ_ONLY);
    }
    
    public ForumPartyTypeRole getForumPartyTypeRoleForUpdate(Forum forum, PartyType partyType, ForumRoleType forumRoleType) {
        return getForumPartyTypeRole(forum, partyType, forumRoleType, EntityPermission.READ_WRITE);
    }
    
    public List<ForumPartyTypeRoleTransfer> getForumPartyTypeRoleTransfers(UserVisit userVisit, Collection<ForumPartyTypeRole> forumPartyTypeRoles) {
        List<ForumPartyTypeRoleTransfer> forumPartyTypeRoleTransfers = null;

        if(forumPartyTypeRoles != null) {
            forumPartyTypeRoleTransfers = new ArrayList<>(forumPartyTypeRoles.size());

            for(var forumPartyTypeRole : forumPartyTypeRoles) {
                forumPartyTypeRoleTransfers.add(forumPartyTypeRoleTransferCache.getForumPartyTypeRoleTransfer(userVisit, forumPartyTypeRole));
            }
        }

        return forumPartyTypeRoleTransfers;
    }

    public List<ForumPartyTypeRoleTransfer> getForumPartyTypeRoleTransfersByForum(UserVisit userVisit, Forum forum) {
        return getForumPartyTypeRoleTransfers(userVisit, getForumPartyTypeRolesByForum(forum));
    }
    
    public ForumPartyTypeRoleTransfer getForumPartyTypeRoleTransfer(UserVisit userVisit, ForumPartyTypeRole forumPartyTypeRole) {
        return forumPartyTypeRoleTransferCache.getForumPartyTypeRoleTransfer(userVisit, forumPartyTypeRole);
    }
    
    public void deleteForumPartyTypeRole(ForumPartyTypeRole forumPartyTypeRole, BasePK deletedBy) {
        forumPartyTypeRole.setThruTime(session.getStartTime());
        
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

    @Inject
    protected ForumTypeMessageTypeFactory forumTypeMessageTypeFactory;

    public ForumTypeMessageType createForumTypeMessageType(ForumType forumType, ForumMessageType forumMessageType,
            Boolean isDefault, Integer sortOrder) {
        return forumTypeMessageTypeFactory.create(forumType, forumMessageType, isDefault, sortOrder);
    }

    public long countForumTypeMessageTypeBy(final ForumType forumType) {
        return session.getDslContext()
                .selectCount()
                .from(ForumTypeMessageTypes)
                .where(ForumTypeMessageTypes.FORUM_TYPE.eq(forumType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countForumTypeMessageTypeBy(final ForumMessageType forumMessageType) {
        return session.getDslContext()
                .selectCount()
                .from(ForumTypeMessageTypes)
                .where(ForumTypeMessageTypes.FORUM_MESSAGE_TYPE.eq(forumMessageType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<ForumTypeMessageType> getForumTypeMessageTypesByForumType(ForumType forumType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ForumTypeMessageTypes.fields())
                    .from(ForumTypeMessageTypes)
                    .join(ForumMessageTypes).onKey(FORUM_TYPE_MESSAGE_TYPES_FORUM_MESSAGE_TYPE_FK)
                    .where(ForumTypeMessageTypes.FORUM_TYPE.eq(forumType.getPrimaryKey()))
                    .orderBy(ForumTypeMessageTypes.SORT_ORDER, ForumMessageTypes.SORT_ORDER, ForumMessageTypes.FORUM_MESSAGE_TYPE_NAME),
                    ForumTypeMessageTypeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ForumTypeMessageTypes.fields())
                    .from(ForumTypeMessageTypes)
                    .where(ForumTypeMessageTypes.FORUM_TYPE.eq(forumType.getPrimaryKey()))
                    .forUpdate();
        };

        return forumTypeMessageTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ForumTypeMessageType> getForumTypeMessageTypesByForumType(ForumType forumType) {
        return getForumTypeMessageTypesByForumType(forumType, EntityPermission.READ_ONLY);
    }
    
    public List<ForumTypeMessageType> getForumTypeMessageTypesByForumTypeForUpdate(ForumType forumType) {
        return getForumTypeMessageTypesByForumType(forumType, EntityPermission.READ_WRITE);
    }
    
    private ForumTypeMessageType getForumTypeMessageType(ForumType forumType, ForumMessageType forumMessageType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ForumTypeMessageTypes.fields())
                .from(ForumTypeMessageTypes)
                .where(ForumTypeMessageTypes.FORUM_TYPE.eq(forumType.getPrimaryKey()),
                        ForumTypeMessageTypes.FORUM_MESSAGE_TYPE.eq(forumMessageType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return forumTypeMessageTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public ForumTypeMessageType getForumTypeMessageType(ForumType forumType, ForumMessageType forumMessageType) {
        return getForumTypeMessageType(forumType, forumMessageType, EntityPermission.READ_ONLY);
    }

    public ForumTypeMessageType getForumTypeMessageTypeForUpdate(ForumType forumType, ForumMessageType forumMessageType) {
        return getForumTypeMessageType(forumType, forumMessageType, EntityPermission.READ_WRITE);
    }

    private ForumTypeMessageType getDefaultForumTypeMessageType(ForumType forumType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ForumTypeMessageTypes.fields())
                .from(ForumTypeMessageTypes)
                .where(ForumTypeMessageTypes.FORUM_TYPE.eq(forumType.getPrimaryKey()),
                        ForumTypeMessageTypes.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return forumTypeMessageTypeFactory.getEntityFromQuery(entityPermission, query);
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

    @Inject
    protected ForumForumThreadFactory forumForumThreadFactory;

    @Inject
    ForumForumThreadTransferCache forumForumThreadTransferCache;

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

        var forumForumThread = forumForumThreadFactory.create(forum, forumThread,
                isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(forum.getPrimaryKey(), EventTypes.MODIFY, forumForumThread.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return forumForumThread;
    }

    public long countForumForumThreadsByForum(final Forum forum) {
        return session.getDslContext()
                .selectCount()
                .from(ForumForumThreads)
                .where(ForumForumThreads.FORUM.eq(forum.getPrimaryKey()),
                        ForumForumThreads.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countForumForumThreadsByForumThread(final ForumThread forumThread) {
        return session.getDslContext()
                .selectCount()
                .from(ForumForumThreads)
                .where(ForumForumThreads.FORUM_THREAD.eq(forumThread.getPrimaryKey()),
                        ForumForumThreads.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private ForumForumThread getForumForumThread(Forum forum, ForumThread forumThread, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ForumForumThreads.fields())
                .from(ForumForumThreads)
                .where(ForumForumThreads.FORUM.eq(forum.getPrimaryKey()),
                        ForumForumThreads.FORUM_THREAD.eq(forumThread.getPrimaryKey()),
                        ForumForumThreads.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return forumForumThreadFactory.getEntityFromQuery(entityPermission, query);
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
        var baseQuery = session.getDslContext()
                .select(ForumForumThreads.fields())
                .from(ForumForumThreads)
                .where(ForumForumThreads.FORUM_THREAD.eq(forumThread.getPrimaryKey()),
                        ForumForumThreads.IS_DEFAULT.eq(true),
                        ForumForumThreads.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return forumForumThreadFactory.getEntityFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ForumForumThreads.fields())
                    .from(ForumForumThreads)
                    .join(ForumThreads).onKey(FORUM_FORUM_THREADS_FORUM_THREAD_FK)
                    .join(ForumThreadDetails).onKey(FORUM_THREADS_LAST_DETAIL_FK)
                    .where(ForumForumThreads.FORUM.eq(forum.getPrimaryKey()),
                            ForumForumThreads.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ForumThreadDetails.SORT_ORDER, ForumThreadDetails.POSTED_TIME),
                    ForumForumThreadFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ForumForumThreads.fields())
                    .from(ForumForumThreads)
                    .where(ForumForumThreads.FORUM.eq(forum.getPrimaryKey()),
                            ForumForumThreads.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return forumForumThreadFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ForumForumThread> getForumForumThreadsByForum(Forum forum) {
        return getForumForumThreadsByForum(forum, EntityPermission.READ_ONLY);
    }
    
    public List<ForumForumThread> getForumForumThreadsByForumForUpdate(Forum forum) {
        return getForumForumThreadsByForum(forum, EntityPermission.READ_WRITE);
    }
    
    private List<ForumForumThread> getForumForumThreadsByForumThread(ForumThread forumThread, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ForumForumThreads.fields())
                    .from(ForumForumThreads)
                    .join(Forums).onKey(FORUM_FORUM_THREADS_FORUM_FK)
                    .join(ForumDetails).onKey(FORUMS_LAST_DETAIL_FK)
                    .where(ForumForumThreads.FORUM_THREAD.eq(forumThread.getPrimaryKey()),
                            ForumForumThreads.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ForumDetails.SORT_ORDER, ForumDetails.FORUM_NAME),
                    ForumForumThreadFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ForumForumThreads.fields())
                    .from(ForumForumThreads)
                    .where(ForumForumThreads.FORUM_THREAD.eq(forumThread.getPrimaryKey()),
                            ForumForumThreads.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return forumForumThreadFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ForumForumThread> getForumForumThreadsByForumThread(ForumThread forumThread) {
        return getForumForumThreadsByForumThread(forumThread, EntityPermission.READ_ONLY);
    }
    
    public List<ForumForumThread> getForumForumThreadsByForumThreadForUpdate(ForumThread forumThread) {
        return getForumForumThreadsByForumThread(forumThread, EntityPermission.READ_WRITE);
    }

    public List<ForumForumThreadTransfer> getForumForumThreadTransfers(UserVisit userVisit, Collection<ForumForumThread> forumForumThreads) {
        List<ForumForumThreadTransfer> forumForumThreadTransfers = new ArrayList<>(forumForumThreads.size());
        
        forumForumThreads.forEach((forumForumThread) ->
                forumForumThreadTransfers.add(forumForumThreadTransferCache.getForumForumThreadTransfer(userVisit, forumForumThread))
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
        return forumForumThreadTransferCache.getForumForumThreadTransfer(userVisit, forumForumThread);
    }
    
    private void updateForumForumThreadFromValue(ForumForumThreadValue forumForumThreadValue, boolean checkDefault, BasePK updatedBy) {
        if(forumForumThreadValue.hasBeenModified()) {
            var forumForumThread = forumForumThreadFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     forumForumThreadValue.getPrimaryKey());
            
            forumForumThread.setThruTime(session.getStartTime());
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
            
            forumForumThread = forumForumThreadFactory.create(forumPK, forumThread.getPrimaryKey(), isDefault,
                    sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(forumPK, EventTypes.MODIFY, forumForumThread.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void updateForumForumThreadFromValue(ForumForumThreadValue forumForumThreadValue, BasePK updatedBy) {
        updateForumForumThreadFromValue(forumForumThreadValue, true, updatedBy);
    }
    
    private void deleteForumForumThread(ForumForumThread forumForumThread, boolean checkForumThread, BasePK deletedBy) {
        forumForumThread.setThruTime(session.getStartTime());
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

    @Inject
    protected ForumThreadDetailFactory forumThreadDetailFactory;

    @Inject
    protected ForumThreadFactory forumThreadFactory;

    @Inject
    ForumThreadTransferCache forumThreadTransferCache;

    public ForumThread createForumThread(Forum forum, Icon icon, Long postedTime, Integer sortOrder, BasePK createdBy) {
        var sequence = forum == null? null: forum.getLastDetail().getForumThreadSequence();
        
        if(sequence == null) {
            sequence = sequenceControl.getDefaultSequenceUsingNames(SequenceTypes.FORUM_THREAD.name());
        }
        
        return createForumThread(sequenceGeneratorLogic.getNextSequenceValue(sequence), icon, postedTime, sortOrder, createdBy);
    }
    
    public ForumThread createForumThread(String forumThreadName, Icon icon, Long postedTime, Integer sortOrder, BasePK createdBy) {
        var forumThread = forumThreadFactory.create();
        var forumThreadDetail = forumThreadDetailFactory.create(forumThread, forumThreadName,
                icon, postedTime, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        forumThread = forumThreadFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                forumThread.getPrimaryKey());
        forumThread.setActiveDetail(forumThreadDetail);
        forumThread.setLastDetail(forumThreadDetail);
        forumThread.store();
        
        sendEvent(forumThread.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return forumThread;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ForumThread */
    public ForumThread getForumThreadByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ForumThreadPK(entityInstance.getEntityUniqueId());

        return forumThreadFactory.getEntityFromPK(entityPermission, pk);
    }

    public ForumThread getForumThreadByEntityInstance(EntityInstance entityInstance) {
        return getForumThreadByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ForumThread getForumThreadByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getForumThreadByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countForumThreads() {
        return session.getDslContext()
                .selectCount()
                .from(ForumThreads)
                .join(ForumThreadDetails).onKey(FORUM_THREADS_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countForumThreadsByIcon(final Icon icon) {
        return session.getDslContext()
                .selectCount()
                .from(ForumThreads)
                .join(ForumThreadDetails).onKey(FORUM_THREADS_ACTIVE_DETAIL_FK)
                .where(ForumThreadDetails.ICON.eq(icon.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public ForumThread getForumThreadByName(String forumThreadName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ForumThreads.fields())
                .from(ForumThreads)
                .join(ForumThreadDetails).onKey(FORUM_THREADS_ACTIVE_DETAIL_FK)
                .where(ForumThreadDetails.FORUM_THREAD_NAME.eq(forumThreadName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return forumThreadFactory.getEntityFromQuery(entityPermission, query);
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
        var condition = ForumForumThreads.FORUM.eq(forum.getPrimaryKey())
                .and(ForumForumThreads.THRU_TIME.eq(Session.MAX_TIME));

        if(!includeFutureForumThreads) {
            condition = condition.and(ForumThreadDetails.POSTED_TIME.le(session.getStartTime()));
        }

        return session.getDslContext()
                .selectCount()
                .from(ForumForumThreads)
                .join(ForumThreads).onKey(FORUM_FORUM_THREADS_FORUM_THREAD_FK)
                .join(ForumThreadDetails).onKey(FORUM_THREADS_ACTIVE_DETAIL_FK)
                .where(condition)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }
    
    private List<ForumThread> getForumThreadsByForum(Forum forum, boolean includeFutureForumThreads, EntityPermission entityPermission) {
        var condition = ForumForumThreads.FORUM.eq(forum.getPrimaryKey())
                .and(ForumForumThreads.THRU_TIME.eq(Session.MAX_TIME));

        if(!includeFutureForumThreads) {
            condition = condition.and(ForumThreadDetails.POSTED_TIME.le(session.getStartTime()));
        }

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ForumThreads.fields())
                    .from(ForumThreads)
                    .join(ForumForumThreads).onKey(FORUM_FORUM_THREADS_FORUM_THREAD_FK)
                    .join(ForumThreadDetails).onKey(FORUM_THREADS_ACTIVE_DETAIL_FK)
                    .join(EntityInstances).on(ForumThreads.FORUM_THREAD.cast(Long.class).eq(EntityInstances.ENTITY_UNIQUE))
                    .join(EntityTypes).onKey(ENTITY_INSTANCES_ENTITY_TYPE_FK)
                    .join(EntityTypeDetails).onKey(ENTITY_TYPES_ACTIVE_DETAIL_FK)
                    .join(ComponentVendors).onKey(ENTITY_TYPE_DETAILS_COMPONENT_VENDOR_FK)
                    .join(ComponentVendorDetails).onKey(COMPONENT_VENDORS_ACTIVE_DETAIL_FK)
                    .join(EntityTimes).onKey(ENTITY_TIMES_ENTITY_INSTANCE_FK)
                    .where(condition,
                            ComponentVendorDetails.COMPONENT_VENDOR_NAME.eq(com.echothree.model.control.core.common.ComponentVendors.ECHO_THREE.name()),
                            EntityTypeDetails.ENTITY_TYPE_NAME.eq(com.echothree.model.control.core.common.EntityTypes.ForumThread.name()))
                    .orderBy(ForumThreadDetails.SORT_ORDER, ForumThreadDetails.POSTED_TIME.desc(), EntityTimes.CREATED_TIME.desc()),
                    ForumThreadFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ForumThreads.fields())
                    .from(ForumThreads)
                    .join(ForumForumThreads).onKey(FORUM_FORUM_THREADS_FORUM_THREAD_FK)
                    .join(ForumThreadDetails).onKey(FORUM_THREADS_ACTIVE_DETAIL_FK)
                    .where(condition)
                    .forUpdate();
        };

        return forumThreadFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ForumThread> getForumThreadsByForum(Forum forum, boolean includeFutureForumThreads) {
        return getForumThreadsByForum(forum, includeFutureForumThreads, EntityPermission.READ_ONLY);
    }
    
    public List<ForumThread> getForumThreadsByForumForUpdate(Forum forum, boolean includeFutureForumThreads) {
        return getForumThreadsByForum(forum, includeFutureForumThreads, EntityPermission.READ_WRITE);
    }
    
    public ForumThreadTransfer getForumThreadTransfer(UserVisit userVisit, ForumThread forumThread) {
        return forumThreadTransferCache.getForumThreadTransfer(userVisit, forumThread);
    }
    
    public List<ForumThreadTransfer> getForumThreadTransfers(UserVisit userVisit, Collection<ForumThread> forumThreads) {
        List<ForumThreadTransfer> forumThreadTransfers = new ArrayList<>(forumThreads.size());
        
        forumThreads.forEach((forumThread) ->
                forumThreadTransfers.add(forumThreadTransferCache.getForumThreadTransfer(userVisit, forumThread))
        );
        
        return forumThreadTransfers;
    }
    
    public List<ForumThreadTransfer> getForumThreadTransfersByForum(UserVisit userVisit, Forum forum, boolean includeFutureForumThreads) {
        return getForumThreadTransfers(userVisit, getForumThreadsByForum(forum, includeFutureForumThreads));
    }
    
    public void updateForumThreadFromValue(ForumThreadDetailValue forumThreadDetailValue, BasePK updatedBy) {
        if(forumThreadDetailValue.hasBeenModified()) {
            var forumThread = forumThreadFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    forumThreadDetailValue.getForumThreadPK());
            var forumThreadDetail = forumThread.getActiveDetailForUpdate();
            
            forumThreadDetail.setThruTime(session.getStartTime());
            forumThreadDetail.store();

            var forumThreadPK = forumThreadDetail.getForumThreadPK();
            var forumThreadName = forumThreadDetail.getForumThreadName(); // Not updated
            var iconPK = forumThreadDetailValue.getIconPK();
            var postedTime = forumThreadDetailValue.getPostedTime();
            var sortOrder = forumThreadDetailValue.getSortOrder();
            
            forumThreadDetail = forumThreadDetailFactory.create(forumThreadPK, forumThreadName,
                    iconPK, postedTime, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            forumThread.setActiveDetail(forumThreadDetail);
            forumThread.setLastDetail(forumThreadDetail);
            
            sendEvent(forumThreadPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deleteForumThread(ForumThread forumThread, BasePK deletedBy) {
        deleteForumMessagesByForumThread(forumThread, deletedBy);
        deleteForumForumThreadsByForumThread(forumThread, deletedBy);

        var forumThreadDetail = forumThread.getLastDetailForUpdate();
        forumThreadDetail.setThruTime(session.getStartTime());
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

    @Inject
    protected ForumMessageDetailFactory forumMessageDetailFactory;

    @Inject
    protected ForumMessageFactory forumMessageFactory;

    @Inject
    ForumMessageTransferCache forumMessageTransferCache;

    public ForumMessage createForumMessage(ForumThread forumThread, ForumMessageType forumMessageType,
            ForumMessage parentForumMessage, Icon icon, Long postedTime, BasePK createdBy) {
        var forumForumThread = getDefaultForumForumThread(forumThread);
        var forum = forumForumThread == null? null: forumForumThread.getForum();
        var sequence = forum == null? null: forum.getLastDetail().getForumThreadSequence();
        
        if(sequence == null) {
            sequence = sequenceControl.getDefaultSequenceUsingNames(SequenceTypes.FORUM_MESSAGE.name());
        }
        
        return createForumMessage(sequenceGeneratorLogic.getNextSequenceValue(sequence), forumThread, forumMessageType, parentForumMessage,
                icon, postedTime, createdBy);
    }
    
    public ForumMessage createForumMessage(String forumMessageName, ForumThread forumThread, ForumMessageType forumMessageType,
            ForumMessage parentForumMessage, Icon icon, Long postedTime, BasePK createdBy) {
        var forumMessage = forumMessageFactory.create();
        var forumMessageDetail = forumMessageDetailFactory.create(forumMessage,
                forumMessageName, forumThread, forumMessageType, parentForumMessage, icon, postedTime, session.getStartTime(),
                Session.MAX_TIME);
        
        // Convert to R/W
        forumMessage = forumMessageFactory.getEntityFromPK(EntityPermission.READ_WRITE,
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
    public ForumMessage getForumMessageByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ForumMessagePK(entityInstance.getEntityUniqueId());

        return forumMessageFactory.getEntityFromPK(entityPermission, pk);
    }

    public ForumMessage getForumMessageByEntityInstance(EntityInstance entityInstance) {
        return getForumMessageByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ForumMessage getForumMessageByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getForumMessageByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countForumMessages() {
        return session.getDslContext()
                .selectCount()
                .from(ForumMessages)
                .join(ForumMessageDetails).onKey(FORUM_MESSAGES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countForumMessagesByForumThread(ForumThread forumThread) {
        return session.getDslContext()
                .selectCount()
                .from(ForumMessages)
                .join(ForumMessageDetails).onKey(FORUM_MESSAGES_ACTIVE_DETAIL_FK)
                .where(ForumMessageDetails.FORUM_THREAD.eq(forumThread.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countForumMessagesByForumMessageType(ForumMessageType forumMessageType) {
        return session.getDslContext()
                .selectCount()
                .from(ForumMessages)
                .join(ForumMessageDetails).onKey(FORUM_MESSAGES_ACTIVE_DETAIL_FK)
                .where(ForumMessageDetails.FORUM_MESSAGE_TYPE.eq(forumMessageType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countForumMessagesByParentForumMessage(ForumMessage parentForumMessage) {
        return session.getDslContext()
                .selectCount()
                .from(ForumMessages)
                .join(ForumMessageDetails).onKey(FORUM_MESSAGES_ACTIVE_DETAIL_FK)
                .where(ForumMessageDetails.PARENT_FORUM_MESSAGE.eq(parentForumMessage.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countForumMessagesByIcon(Icon icon) {
        return session.getDslContext()
                .selectCount()
                .from(ForumMessages)
                .join(ForumMessageDetails).onKey(FORUM_MESSAGES_ACTIVE_DETAIL_FK)
                .where(ForumMessageDetails.ICON.eq(icon.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public ForumMessage getForumMessageByName(String forumMessageName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ForumMessages.fields())
                .from(ForumMessages)
                .join(ForumMessageDetails).onKey(FORUM_MESSAGES_ACTIVE_DETAIL_FK)
                .where(ForumMessageDetails.FORUM_MESSAGE_NAME.eq(forumMessageName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return forumMessageFactory.getEntityFromQuery(entityPermission, query);
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
    
    private List<ForumMessage> getForumMessagesByForumThread(ForumThread forumThread, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ForumMessages.fields())
                .from(ForumMessages)
                .join(ForumMessageDetails).onKey(FORUM_MESSAGES_ACTIVE_DETAIL_FK)
                .where(ForumMessageDetails.FORUM_THREAD.eq(forumThread.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ForumMessageDetails.POSTED_TIME, ForumMessageDetails.FORUM_MESSAGE_NAME),
                    ForumMessageFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return forumMessageFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ForumMessage> getForumMessagesByForumThread(ForumThread forumThread) {
        return getForumMessagesByForumThread(forumThread, EntityPermission.READ_ONLY);
    }
    
    public List<ForumMessage> getForumMessagesByForumThreadForUpdate(ForumThread forumThread) {
        return getForumMessagesByForumThread(forumThread, EntityPermission.READ_WRITE);
    }
    
    private List<ForumMessage> getForumMessagesByParentForumMessage(ForumMessage parentForumMessage,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ForumMessages.fields())
                .from(ForumMessages)
                .join(ForumMessageDetails).onKey(FORUM_MESSAGES_ACTIVE_DETAIL_FK)
                .where(ForumMessageDetails.PARENT_FORUM_MESSAGE.eq(parentForumMessage.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ForumMessageDetails.POSTED_TIME, ForumMessageDetails.FORUM_MESSAGE_NAME),
                    ForumMessageFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return forumMessageFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ForumMessage> getForumMessagesByParentForumMessage(ForumMessage parentForumMessage) {
        return getForumMessagesByParentForumMessage(parentForumMessage, EntityPermission.READ_ONLY);
    }

    public List<ForumMessage> getForumMessagesByParentForumMessageForUpdate(ForumMessage parentForumMessage) {
        return getForumMessagesByParentForumMessage(parentForumMessage, EntityPermission.READ_WRITE);
    }

    public ForumMessageTransfer getForumMessageTransfer(UserVisit userVisit, ForumMessage forumMessage) {
        return forumMessageTransferCache.getForumMessageTransfer(userVisit, forumMessage);
    }
    
    public List<ForumMessageTransfer> getForumMessageTransfers(UserVisit userVisit, Collection<ForumMessage> forumMessages) {
        List<ForumMessageTransfer> forumMessageTransfers = new ArrayList<>(forumMessages.size());
        
        forumMessages.forEach((forumMessage) ->
                forumMessageTransfers.add(forumMessageTransferCache.getForumMessageTransfer(userVisit, forumMessage))
        );
        
        return forumMessageTransfers;
    }
    
    public List<ForumMessageTransfer> getForumMessageTransfersByForumThread(UserVisit userVisit, ForumThread forumThread) {
        return getForumMessageTransfers(userVisit, getForumMessagesByForumThread(forumThread));
    }
    
    public void updateForumMessageFromValue(ForumMessageDetailValue forumMessageDetailValue, BasePK updatedBy) {
        if(forumMessageDetailValue.hasBeenModified()) {
            var forumMessage = forumMessageFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    forumMessageDetailValue.getForumMessagePK());
            var forumMessageDetail = forumMessage.getActiveDetailForUpdate();
            
            forumMessageDetail.setThruTime(session.getStartTime());
            forumMessageDetail.store();

            var forumMessagePK = forumMessageDetail.getForumMessagePK();
            var forumMessageName = forumMessageDetail.getForumMessageName(); // Not updated
            var forumThreadPK = forumMessageDetail.getForumThreadPK(); // Not updated
            var forumMessageTypePK = forumMessageDetail.getForumMessageTypePK(); // Not updated
            var parentForumMessagePK = forumMessageDetail.getParentForumMessagePK(); // Not updated
            var iconPK = forumMessageDetailValue.getIconPK();
            var postedTime = forumMessageDetailValue.getPostedTime();
            
            forumMessageDetail = forumMessageDetailFactory.create(forumMessagePK, forumMessageName,
                    forumThreadPK, forumMessageTypePK, parentForumMessagePK, iconPK, postedTime, session.getStartTime(),
                    Session.MAX_TIME);
            
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
        forumMessageDetail.setThruTime(session.getStartTime());
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

    @Inject
    protected ForumMessageStatusFactory forumMessageStatusFactory;

    public ForumMessageStatus createForumMessageStatus(ForumMessage forumMessage) {
        return forumMessageStatusFactory.create(forumMessage, 0);
    }

    private ForumMessageStatus getForumMessageStatus(ForumMessage forumMessage, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ForumMessageStatuses.fields())
                .from(ForumMessageStatuses)
                .where(ForumMessageStatuses.FORUM_MESSAGE.eq(forumMessage.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return forumMessageStatusFactory.getEntityFromQuery(entityPermission, query);
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

    @Inject
    protected ForumMessageAttachmentDetailFactory forumMessageAttachmentDetailFactory;

    @Inject
    protected ForumMessageAttachmentFactory forumMessageAttachmentFactory;

    @Inject
    ForumMessageAttachmentTransferCache forumMessageAttachmentTransferCache;

    public ForumMessageAttachment createForumMessageAttachment(ForumMessage forumMessage, Integer forumMessageAttachmentSequence, MimeType mimeType,
            BasePK createdBy) {
        var forumMessageAttachment = forumMessageAttachmentFactory.create();
        var forumMessageAttachmentDetail = forumMessageAttachmentDetailFactory.create(forumMessageAttachment,
                forumMessage, forumMessageAttachmentSequence, mimeType, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        forumMessageAttachment = forumMessageAttachmentFactory.getEntityFromPK(EntityPermission.READ_WRITE, forumMessageAttachment.getPrimaryKey());
        forumMessageAttachment.setActiveDetail(forumMessageAttachmentDetail);
        forumMessageAttachment.setLastDetail(forumMessageAttachmentDetail);
        forumMessageAttachment.store();

        sendEvent(forumMessage.getPrimaryKey(), EventTypes.MODIFY, forumMessageAttachment.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return forumMessageAttachment;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ForumMessageAttachment */
    public ForumMessageAttachment getForumMessageAttachmentByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ForumMessageAttachmentPK(entityInstance.getEntityUniqueId());

        return forumMessageAttachmentFactory.getEntityFromPK(entityPermission, pk);
    }

    public ForumMessageAttachment getForumMessageAttachmentByEntityInstance(EntityInstance entityInstance) {
        return getForumMessageAttachmentByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ForumMessageAttachment getForumMessageAttachmentByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getForumMessageAttachmentByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countForumMessageAttachmentsByForumMessage(ForumMessage forumMessage) {
        return session.getDslContext()
                .selectCount()
                .from(ForumMessageAttachments)
                .join(ForumMessageAttachmentDetails).onKey(FORUM_MESSAGE_ATTACHMENTS_ACTIVE_DETAIL_FK)
                .where(ForumMessageAttachmentDetails.FORUM_MESSAGE.eq(forumMessage.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countForumMessageAttachmentsByMimeType(MimeType mimeType) {
        return session.getDslContext()
                .selectCount()
                .from(ForumMessageAttachments)
                .join(ForumMessageAttachmentDetails).onKey(FORUM_MESSAGE_ATTACHMENTS_ACTIVE_DETAIL_FK)
                .where(ForumMessageAttachmentDetails.MIME_TYPE.eq(mimeType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private ForumMessageAttachment getForumMessageAttachmentBySequence(ForumMessage forumMessage, Integer forumMessageAttachmentSequence, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ForumMessageAttachments.fields())
                .from(ForumMessageAttachments)
                .join(ForumMessageAttachmentDetails).onKey(FORUM_MESSAGE_ATTACHMENTS_ACTIVE_DETAIL_FK)
                .where(ForumMessageAttachmentDetails.FORUM_MESSAGE.eq(forumMessage.getPrimaryKey()),
                        ForumMessageAttachmentDetails.FORUM_MESSAGE_ATTACHMENT_SEQUENCE.eq(forumMessageAttachmentSequence));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return forumMessageAttachmentFactory.getEntityFromQuery(entityPermission, query);
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

    private List<ForumMessageAttachment> getForumMessageAttachmentsByForumMessage(ForumMessage forumMessage, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ForumMessageAttachments.fields())
                .from(ForumMessageAttachments)
                .join(ForumMessageAttachmentDetails).onKey(FORUM_MESSAGE_ATTACHMENTS_ACTIVE_DETAIL_FK)
                .where(ForumMessageAttachmentDetails.FORUM_MESSAGE.eq(forumMessage.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(ForumMessageAttachmentDetails.FORUM_MESSAGE_ATTACHMENT_SEQUENCE),
                    ForumMessageAttachmentFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return forumMessageAttachmentFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<ForumMessageAttachment> getForumMessageAttachmentsByForumMessage(ForumMessage forumMessage) {
        return getForumMessageAttachmentsByForumMessage(forumMessage, EntityPermission.READ_ONLY);
    }

    public List<ForumMessageAttachment> getForumMessageAttachmentsByForumMessageForUpdate(ForumMessage forumMessage) {
        return getForumMessageAttachmentsByForumMessage(forumMessage, EntityPermission.READ_WRITE);
    }

    public ForumMessageAttachmentTransfer getForumMessageAttachmentTransfer(UserVisit userVisit, ForumMessageAttachment forumMessageAttachment) {
        return forumMessageAttachmentTransferCache.getForumMessageAttachmentTransfer(userVisit, forumMessageAttachment);
    }

    public List<ForumMessageAttachmentTransfer> getForumMessageAttachmentTransfers(UserVisit userVisit, Collection<ForumMessageAttachment> forumMessageAttachments) {
        List<ForumMessageAttachmentTransfer> forumMessageAttachmentTransfers = new ArrayList<>(forumMessageAttachments.size());

        forumMessageAttachments.forEach((forumMessageAttachment) ->
                forumMessageAttachmentTransfers.add(forumMessageAttachmentTransferCache.getForumMessageAttachmentTransfer(userVisit, forumMessageAttachment))
        );

        return forumMessageAttachmentTransfers;
    }

    public List<ForumMessageAttachmentTransfer> getForumMessageAttachmentTransfersByForumMessage(UserVisit userVisit, ForumMessage forumMessage) {
        return getForumMessageAttachmentTransfers(userVisit, getForumMessageAttachmentsByForumMessage(forumMessage));
    }

    public void updateForumMessageAttachmentFromValue(ForumMessageAttachmentDetailValue forumMessageAttachmentDetailValue, BasePK updatedBy) {
        if(forumMessageAttachmentDetailValue.hasBeenModified()) {
            var forumMessageAttachment = forumMessageAttachmentFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     forumMessageAttachmentDetailValue.getForumMessageAttachmentPK());
            var forumMessageAttachmentDetail = forumMessageAttachment.getActiveDetailForUpdate();

            forumMessageAttachmentDetail.setThruTime(session.getStartTime());
            forumMessageAttachmentDetail.store();

            var forumMessageAttachmentPK = forumMessageAttachmentDetail.getForumMessageAttachmentPK(); // Not updated
            var forumMessagePK = forumMessageAttachmentDetail.getForumMessagePK(); // Not updated
            var forumMessageAttachmentSequence = forumMessageAttachmentDetail.getForumMessageAttachmentSequence(); // Not updated
            var mimeTypePK = forumMessageAttachmentDetailValue.getMimeTypePK();

            forumMessageAttachmentDetail = forumMessageAttachmentDetailFactory.create(forumMessageAttachmentPK, forumMessagePK, forumMessageAttachmentSequence,
                    mimeTypePK, session.getStartTime(), Session.MAX_TIME);

            forumMessageAttachment.setActiveDetail(forumMessageAttachmentDetail);
            forumMessageAttachment.setLastDetail(forumMessageAttachmentDetail);

            sendEvent(forumMessagePK, EventTypes.MODIFY, forumMessageAttachmentPK, EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteForumMessageAttachment(ForumMessageAttachment forumMessageAttachment, BasePK deletedBy) {
        deleteForumMessageAttachmentDescriptionsByForumMessageAttachment(forumMessageAttachment, deletedBy);

        var forumMessageAttachmentDetail = forumMessageAttachment.getLastDetailForUpdate();
        forumMessageAttachmentDetail.setThruTime(session.getStartTime());
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
    //   Forum Mssage Attachment Utilities
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

    @Inject
    protected ForumMessageBlobAttachmentFactory forumMessageBlobAttachmentFactory;

    public ForumMessageBlobAttachment createForumMessageBlobAttachment(ForumMessageAttachment forumMessageAttachment, ByteArray blob, BasePK createdBy) {
        verifyForumMessageAttachmentMimeType(forumMessageAttachment, EntityAttributeTypes.BLOB.name());

        var forumMessageAttachmentBlob = forumMessageBlobAttachmentFactory.create(forumMessageAttachment, blob, session.getStartTime(), Session.MAX_TIME);

        sendEvent(forumMessageAttachment.getLastDetail().getForumMessagePK(), EventTypes.MODIFY, forumMessageAttachmentBlob.getPrimaryKey(), EventTypes.MODIFY, createdBy);

        return forumMessageAttachmentBlob;
    }

    private ForumMessageBlobAttachment getForumMessageBlobAttachment(ForumMessageAttachment forumMessageAttachment, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ForumMessageBlobAttachments.fields())
                .from(ForumMessageBlobAttachments)
                .where(ForumMessageBlobAttachments.FORUM_MESSAGE_ATTACHMENT.eq(forumMessageAttachment.getPrimaryKey()),
                        ForumMessageBlobAttachments.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return forumMessageBlobAttachmentFactory.getEntityFromQuery(entityPermission, query);
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
            var forumMessageAttachmentBlob = forumMessageBlobAttachmentFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    forumMessageAttachmentBlobValue.getPrimaryKey());

            forumMessageAttachmentBlob.setThruTime(session.getStartTime());
            forumMessageAttachmentBlob.store();

            var forumMessageAttachmentPK = forumMessageAttachmentBlob.getForumMessageAttachmentPK(); // Not updated
            var blob = forumMessageAttachmentBlobValue.getBlob();

            forumMessageAttachmentBlob = forumMessageBlobAttachmentFactory.create(forumMessageAttachmentPK, blob, session.getStartTime(),
                    Session.MAX_TIME);

            sendEvent(forumMessageAttachmentBlob.getForumMessageAttachment().getLastDetail().getForumMessagePK(), EventTypes.MODIFY, forumMessageAttachmentBlob.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteForumMessageBlobAttachment(ForumMessageBlobAttachment forumMessageAttachmentBlob, BasePK deletedBy) {
        forumMessageAttachmentBlob.setThruTime(session.getStartTime());

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

    @Inject
    protected ForumMessageClobAttachmentFactory forumMessageClobAttachmentFactory;

    public ForumMessageClobAttachment createForumMessageClobAttachment(ForumMessageAttachment forumMessageAttachment, String clob, BasePK createdBy) {
        verifyForumMessageAttachmentMimeType(forumMessageAttachment, EntityAttributeTypes.CLOB.name());

        var forumMessageAttachmentClob = forumMessageClobAttachmentFactory.create(forumMessageAttachment, clob, session.getStartTime(), Session.MAX_TIME);

        sendEvent(forumMessageAttachment.getLastDetail().getForumMessagePK(), EventTypes.MODIFY, forumMessageAttachmentClob.getPrimaryKey(), EventTypes.MODIFY, createdBy);

        return forumMessageAttachmentClob;
    }

    private ForumMessageClobAttachment getForumMessageClobAttachment(ForumMessageAttachment forumMessageAttachment, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ForumMessageClobAttachments.fields())
                .from(ForumMessageClobAttachments)
                .where(ForumMessageClobAttachments.FORUM_MESSAGE_ATTACHMENT.eq(forumMessageAttachment.getPrimaryKey()),
                        ForumMessageClobAttachments.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return forumMessageClobAttachmentFactory.getEntityFromQuery(entityPermission, query);
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
            var forumMessageAttachmentClob = forumMessageClobAttachmentFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    forumMessageAttachmentClobValue.getPrimaryKey());

            forumMessageAttachmentClob.setThruTime(session.getStartTime());
            forumMessageAttachmentClob.store();

            var forumMessageAttachmentPK = forumMessageAttachmentClob.getForumMessageAttachmentPK(); // Not updated
            var clob = forumMessageAttachmentClobValue.getClob();

            forumMessageAttachmentClob = forumMessageClobAttachmentFactory.create(forumMessageAttachmentPK, clob, session.getStartTime(),
                    Session.MAX_TIME);

            sendEvent(forumMessageAttachmentClob.getForumMessageAttachment().getLastDetail().getForumMessagePK(), EventTypes.MODIFY, forumMessageAttachmentClob.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteForumMessageClobAttachment(ForumMessageClobAttachment forumMessageAttachmentClob, BasePK deletedBy) {
        forumMessageAttachmentClob.setThruTime(session.getStartTime());

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

    @Inject
    protected ForumMessageAttachmentDescriptionFactory forumMessageAttachmentDescriptionFactory;

    @Inject
    ForumMessageAttachmentDescriptionTransferCache forumMessageAttachmentDescriptionTransferCache;

    public ForumMessageAttachmentDescription createForumMessageAttachmentDescription(ForumMessageAttachment forumMessageAttachment, Language language, String description, BasePK createdBy) {
        var forumMessageAttachmentDescription = forumMessageAttachmentDescriptionFactory.create(forumMessageAttachment, language,
                description, session.getStartTime(), Session.MAX_TIME);

        sendEvent(forumMessageAttachment.getLastDetail().getForumMessagePK(), EventTypes.MODIFY, forumMessageAttachmentDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return forumMessageAttachmentDescription;
    }

    private ForumMessageAttachmentDescription getForumMessageAttachmentDescription(ForumMessageAttachment forumMessageAttachment, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ForumMessageAttachmentDescriptions.fields())
                .from(ForumMessageAttachmentDescriptions)
                .where(ForumMessageAttachmentDescriptions.FORUM_MESSAGE_ATTACHMENT.eq(forumMessageAttachment.getPrimaryKey()),
                        ForumMessageAttachmentDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        ForumMessageAttachmentDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return forumMessageAttachmentDescriptionFactory.getEntityFromQuery(entityPermission, query);
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

    private List<ForumMessageAttachmentDescription> getForumMessageAttachmentDescriptionsByForumMessageAttachment(ForumMessageAttachment forumMessageAttachment, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ForumMessageAttachmentDescriptions.fields())
                    .from(ForumMessageAttachmentDescriptions)
                    .join(Languages).onKey(FORUM_MESSAGE_ATTACHMENT_DESCRIPTIONS_LANGUAGE_FK)
                    .where(ForumMessageAttachmentDescriptions.FORUM_MESSAGE_ATTACHMENT.eq(forumMessageAttachment.getPrimaryKey()),
                            ForumMessageAttachmentDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    ForumMessageAttachmentDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ForumMessageAttachmentDescriptions.fields())
                    .from(ForumMessageAttachmentDescriptions)
                    .where(ForumMessageAttachmentDescriptions.FORUM_MESSAGE_ATTACHMENT.eq(forumMessageAttachment.getPrimaryKey()),
                            ForumMessageAttachmentDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return forumMessageAttachmentDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
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
        return forumMessageAttachmentDescriptionTransferCache.getForumMessageAttachmentDescriptionTransfer(userVisit, forumMessageAttachmentDescription);
    }

    public List<ForumMessageAttachmentDescriptionTransfer> getForumMessageAttachmentDescriptionTransfersByForumMessageAttachment(UserVisit userVisit, ForumMessageAttachment forumMessageAttachment) {
        var forumMessageAttachmentDescriptions = getForumMessageAttachmentDescriptionsByForumMessageAttachment(forumMessageAttachment);
        List<ForumMessageAttachmentDescriptionTransfer> forumMessageAttachmentDescriptionTransfers = null;

        if(forumMessageAttachmentDescriptions != null) {
            forumMessageAttachmentDescriptionTransfers = new ArrayList<>(forumMessageAttachmentDescriptions.size());

            for(var forumMessageAttachmentDescription : forumMessageAttachmentDescriptions) {
                forumMessageAttachmentDescriptionTransfers.add(forumMessageAttachmentDescriptionTransferCache.getForumMessageAttachmentDescriptionTransfer(userVisit, forumMessageAttachmentDescription));
            }
        }

        return forumMessageAttachmentDescriptionTransfers;
    }

    public void updateForumMessageAttachmentDescriptionFromValue(ForumMessageAttachmentDescriptionValue forumMessageAttachmentDescriptionValue, BasePK updatedBy) {
        if(forumMessageAttachmentDescriptionValue.hasBeenModified()) {
            var forumMessageAttachmentDescription = forumMessageAttachmentDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE, forumMessageAttachmentDescriptionValue.getPrimaryKey());

            forumMessageAttachmentDescription.setThruTime(session.getStartTime());
            forumMessageAttachmentDescription.store();

            var forumMessageAttachment = forumMessageAttachmentDescription.getForumMessageAttachment();
            var language = forumMessageAttachmentDescription.getLanguage();
            var description = forumMessageAttachmentDescriptionValue.getDescription();

            forumMessageAttachmentDescription = forumMessageAttachmentDescriptionFactory.create(forumMessageAttachment, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(forumMessageAttachment.getLastDetail().getForumMessagePK(), EventTypes.MODIFY, forumMessageAttachmentDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteForumMessageAttachmentDescription(ForumMessageAttachmentDescription forumMessageAttachmentDescription, BasePK deletedBy) {
        forumMessageAttachmentDescription.setThruTime(session.getStartTime());

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

    @Inject
    protected ForumMessageRoleFactory forumMessageRoleFactory;

    @Inject
    ForumMessageRoleTransferCache forumMessageRoleTransferCache;

    public ForumMessageRole createForumMessageRole(ForumMessage forumMessage, ForumRoleType forumRoleType, Party party,
            BasePK createdBy) {
        var forumMessageRole = forumMessageRoleFactory.create(forumMessage, forumRoleType,
                party, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(forumMessage.getPrimaryKey(), EventTypes.MODIFY, forumMessageRole.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return forumMessageRole;
    }

    public long countForumMessageRolesByForumMessage(final ForumMessage forumMessage) {
        return session.getDslContext()
                .selectCount()
                .from(ForumMessageRoles)
                .where(ForumMessageRoles.FORUM_MESSAGE.eq(forumMessage.getPrimaryKey()),
                        ForumMessageRoles.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countForumMessageRolesByForumRoleType(final ForumRoleType forumRoleType) {
        return session.getDslContext()
                .selectCount()
                .from(ForumMessageRoles)
                .where(ForumMessageRoles.FORUM_ROLE_TYPE.eq(forumRoleType.getPrimaryKey()),
                        ForumMessageRoles.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countForumMessageRolesByParty(final Party party) {
        return session.getDslContext()
                .selectCount()
                .from(ForumMessageRoles)
                .where(ForumMessageRoles.PARTY.eq(party.getPrimaryKey()),
                        ForumMessageRoles.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private ForumMessageRole getForumMessageRole(ForumMessage forumMessage, ForumRoleType forumRoleType, Party party, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ForumMessageRoles.fields())
                .from(ForumMessageRoles)
                .where(ForumMessageRoles.FORUM_MESSAGE.eq(forumMessage.getPrimaryKey()),
                        ForumMessageRoles.FORUM_ROLE_TYPE.eq(forumRoleType.getPrimaryKey()),
                        ForumMessageRoles.PARTY.eq(party.getPrimaryKey()),
                        ForumMessageRoles.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return forumMessageRoleFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public ForumMessageRole getForumMessageRole(ForumMessage forumMessage, ForumRoleType forumRoleType, Party party) {
        return getForumMessageRole(forumMessage, forumRoleType, party, EntityPermission.READ_ONLY);
    }
    
    public ForumMessageRole getForumMessageRoleForUpdate(ForumMessage forumMessage, ForumRoleType forumRoleType, Party party) {
        return getForumMessageRole(forumMessage, forumRoleType, party, EntityPermission.READ_WRITE);
    }
    
    private List<ForumMessageRole> getForumMessageRolesByForumMessage(ForumMessage forumMessage, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ForumMessageRoles.fields())
                    .from(ForumMessageRoles)
                    .join(ForumRoleTypes).onKey(FORUM_MESSAGE_ROLES_FORUM_ROLE_TYPE_FK)
                    .join(Parties).onKey(FORUM_MESSAGE_ROLES_PARTY_FK)
                    .join(PartyDetails).onKey(PARTIES_LAST_DETAIL_FK)
                    .where(ForumMessageRoles.FORUM_MESSAGE.eq(forumMessage.getPrimaryKey()),
                            ForumMessageRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ForumRoleTypes.SORT_ORDER, ForumRoleTypes.FORUM_ROLE_TYPE_NAME, PartyDetails.PARTY_NAME),
                    ForumMessageRoleFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ForumMessageRoles.fields())
                    .from(ForumMessageRoles)
                    .where(ForumMessageRoles.FORUM_MESSAGE.eq(forumMessage.getPrimaryKey()),
                            ForumMessageRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return forumMessageRoleFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<ForumMessageRole> getForumMessageRolesByForumMessage(ForumMessage forumMessage) {
        return getForumMessageRolesByForumMessage(forumMessage, EntityPermission.READ_ONLY);
    }
    
    public List<ForumMessageRole> getForumMessageRolesByForumMessageForUpdate(ForumMessage forumMessage) {
        return getForumMessageRolesByForumMessage(forumMessage, EntityPermission.READ_WRITE);
    }
    
    public ForumMessageRoleTransfer getForumMessageRoleTransfer(UserVisit userVisit, ForumMessageRole forumMessageRole) {
        return forumMessageRoleTransferCache.getForumMessageRoleTransfer(userVisit, forumMessageRole);
    }
    
    public List<ForumMessageRoleTransfer> getForumMessageRoleTransfers(UserVisit userVisit, Collection<ForumMessageRole> forumMessageRoles) {
        List<ForumMessageRoleTransfer> forumMessageRoleTransfers = new ArrayList<>(forumMessageRoles.size());
        
        forumMessageRoles.forEach((forumMessageRole) ->
                forumMessageRoleTransfers.add(forumMessageRoleTransferCache.getForumMessageRoleTransfer(userVisit, forumMessageRole))
        );
        
        return forumMessageRoleTransfers;
    }
    
    public List<ForumMessageRoleTransfer> getForumMessageRoleTransfersByForumMessage(UserVisit userVisit, ForumMessage forumMessage) {
        return getForumMessageRoleTransfers(userVisit, getForumMessageRolesByForumMessage(forumMessage));
    }
    
    public void deleteForumMessageRole(ForumMessageRole forumMessageRole, BasePK deletedBy) {
        forumMessageRole.setThruTime(session.getStartTime());
        
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

    @Inject
    protected ForumMessagePartDetailFactory forumMessagePartDetailFactory;

    @Inject
    protected ForumMessagePartFactory forumMessagePartFactory;

    @Inject
    ForumMessagePartTransferCache forumMessagePartTransferCache;

    public ForumMessagePart createForumMessagePart(ForumMessage forumMessage, ForumMessagePartType forumMessagePartType,
            Language language, MimeType mimeType, BasePK createdBy) {

        var forumMessagePart = forumMessagePartFactory.create();
        var forumMessagePartDetail = forumMessagePartDetailFactory.create(
                forumMessagePart, forumMessage, forumMessagePartType, language, mimeType, session.getStartTime(),
                Session.MAX_TIME);
        
        // Convert to R/W
        forumMessagePart = forumMessagePartFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                forumMessagePart.getPrimaryKey());
        forumMessagePart.setActiveDetail(forumMessagePartDetail);
        forumMessagePart.setLastDetail(forumMessagePartDetail);
        forumMessagePart.store();
        
        sendEvent(forumMessage.getPrimaryKey(), EventTypes.MODIFY, forumMessagePart.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return forumMessagePart;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ForumMessagePart */
    public ForumMessagePart getForumMessagePartByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ForumMessagePartPK(entityInstance.getEntityUniqueId());

        return forumMessagePartFactory.getEntityFromPK(entityPermission, pk);
    }

    public ForumMessagePart getForumMessagePartByEntityInstance(EntityInstance entityInstance) {
        return getForumMessagePartByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ForumMessagePart getForumMessagePartByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getForumMessagePartByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countForumMessagePartsByForumMessage(final ForumMessage forumMessage) {
        return session.getDslContext()
                .selectCount()
                .from(ForumMessageParts)
                .join(ForumMessagePartDetails).onKey(FORUM_MESSAGE_PARTS_ACTIVE_DETAIL_FK)
                .where(ForumMessagePartDetails.FORUM_MESSAGE.eq(forumMessage.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countForumMessagePartsByForumMessagePartType(final ForumMessagePartType forumMessagePartType) {
        return session.getDslContext()
                .selectCount()
                .from(ForumMessageParts)
                .join(ForumMessagePartDetails).onKey(FORUM_MESSAGE_PARTS_ACTIVE_DETAIL_FK)
                .where(ForumMessagePartDetails.FORUM_MESSAGE_PART_TYPE.eq(forumMessagePartType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countForumMessagePartsByLanguage(final Language language) {
        return session.getDslContext()
                .selectCount()
                .from(ForumMessageParts)
                .join(ForumMessagePartDetails).onKey(FORUM_MESSAGE_PARTS_ACTIVE_DETAIL_FK)
                .where(ForumMessagePartDetails.LANGUAGE.eq(language.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countForumMessagePartsByMimeType(final MimeType mimeType) {
        return session.getDslContext()
                .selectCount()
                .from(ForumMessageParts)
                .join(ForumMessagePartDetails).onKey(FORUM_MESSAGE_PARTS_ACTIVE_DETAIL_FK)
                .where(ForumMessagePartDetails.MIME_TYPE.eq(mimeType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private ForumMessagePart getForumMessagePart(ForumMessage forumMessage, ForumMessagePartType forumMessagePartType,
            Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ForumMessageParts.fields())
                .from(ForumMessageParts)
                .join(ForumMessagePartDetails).onKey(FORUM_MESSAGE_PARTS_ACTIVE_DETAIL_FK)
                .where(ForumMessagePartDetails.FORUM_MESSAGE.eq(forumMessage.getPrimaryKey()),
                        ForumMessagePartDetails.FORUM_MESSAGE_PART_TYPE.eq(forumMessagePartType.getPrimaryKey()),
                        ForumMessagePartDetails.LANGUAGE.eq(language.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return forumMessagePartFactory.getEntityFromQuery(entityPermission, query);
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
        var query = session.getDslContext()
                .select(ForumMessageParts.fields())
                .from(ForumMessageParts)
                .join(ForumMessagePartDetails).onKey(FORUM_MESSAGE_PARTS_ACTIVE_DETAIL_FK)
                .where(ForumMessagePartDetails.FORUM_MESSAGE.eq(forumMessage.getPrimaryKey()))
                .forUpdate();

        return forumMessagePartFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, query);
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
        return forumMessagePartTransferCache.getForumMessagePartTransfer(userVisit, forumMessagePart);
    }
    
    public List<ForumMessagePartTransfer> getForumMessagePartTransfers(UserVisit userVisit, Collection<ForumMessagePart> forumMessageParts) {
        List<ForumMessagePartTransfer> forumMessagePartTransfers = new ArrayList<>(forumMessageParts.size());
        
        forumMessageParts.forEach((forumMessagePart) ->
                forumMessagePartTransfers.add(forumMessagePartTransferCache.getForumMessagePartTransfer(userVisit, forumMessagePart))
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
            var forumMessagePart = forumMessagePartFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    forumMessagePartDetailValue.getForumMessagePartPK());
            var forumMessagePartDetail = forumMessagePart.getActiveDetailForUpdate();
            
            forumMessagePartDetail.setThruTime(session.getStartTime());
            forumMessagePartDetail.store();

            var forumMessagePartPK = forumMessagePartDetail.getForumMessagePartPK();
            var forumMessagePK = forumMessagePartDetail.getForumMessagePK(); // Not updated
            var forumMessagePartTypePK = forumMessagePartDetail.getForumMessagePartTypePK(); // Not updated
            var languagePK = forumMessagePartDetail.getLanguagePK(); // Not updated
            var mimeTypePK = forumMessagePartDetailValue.getMimeTypePK();
            
            forumMessagePartDetail = forumMessagePartDetailFactory.create(forumMessagePartPK, forumMessagePK,
                    forumMessagePartTypePK, languagePK, mimeTypePK, session.getStartTime(), Session.MAX_TIME);
            
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
        
        forumMessagePartDetail.setThruTime(session.getStartTime());
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

    @Inject
    protected ForumStringMessagePartFactory forumStringMessagePartFactory;

    public ForumStringMessagePart createForumStringMessagePart(ForumMessagePart forumMessagePart, String string, BasePK createdBy) {
        var forumStringMessagePart = forumStringMessagePartFactory.create(
                forumMessagePart, string, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(forumMessagePart.getLastDetail().getForumMessagePK(), EventTypes.MODIFY, forumStringMessagePart.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return forumStringMessagePart;
    }
    
    private ForumStringMessagePart getForumStringMessagePart(ForumMessagePart forumMessagePart, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ForumStringMessageParts.fields())
                .from(ForumStringMessageParts)
                .where(ForumStringMessageParts.FORUM_MESSAGE_PART.eq(forumMessagePart.getPrimaryKey()),
                        ForumStringMessageParts.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return forumStringMessagePartFactory.getEntityFromQuery(entityPermission, query);
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
            var forumStringMessagePart = forumStringMessagePartFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     forumStringMessagePartValue.getPrimaryKey());
            
            forumStringMessagePart.setThruTime(session.getStartTime());
            forumStringMessagePart.store();

            var forumMessagePartPK = forumStringMessagePart.getForumMessagePartPK(); // Not updated
            var string = forumStringMessagePartValue.getString();
            
            forumStringMessagePart = forumStringMessagePartFactory.create(forumMessagePartPK, string,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(forumStringMessagePart.getForumMessagePart().getLastDetail().getForumMessagePK(),
                    EventTypes.MODIFY, forumStringMessagePart.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteForumStringMessagePart(ForumStringMessagePart forumStringMessagePart, BasePK deletedBy) {
        forumStringMessagePart.setThruTime(session.getStartTime());
        
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

    @Inject
    protected ForumClobMessagePartFactory forumClobMessagePartFactory;

    public ForumClobMessagePart createForumClobMessagePart(ForumMessagePart forumMessagePart, String clob, BasePK createdBy) {
        var forumClobMessagePart = forumClobMessagePartFactory.create(
                forumMessagePart, clob, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(forumMessagePart.getLastDetail().getForumMessagePK(), EventTypes.MODIFY, forumClobMessagePart.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return forumClobMessagePart;
    }
    
    private ForumClobMessagePart getForumClobMessagePart(ForumMessagePart forumMessagePart, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ForumClobMessageParts.fields())
                .from(ForumClobMessageParts)
                .where(ForumClobMessageParts.FORUM_MESSAGE_PART.eq(forumMessagePart.getPrimaryKey()),
                        ForumClobMessageParts.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return forumClobMessagePartFactory.getEntityFromQuery(entityPermission, query);
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
            var forumClobMessagePart = forumClobMessagePartFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     forumClobMessagePartValue.getPrimaryKey());
            
            forumClobMessagePart.setThruTime(session.getStartTime());
            forumClobMessagePart.store();

            var forumMessagePartPK = forumClobMessagePart.getForumMessagePartPK(); // Not updated
            var clob = forumClobMessagePartValue.getClob();
            
            forumClobMessagePart = forumClobMessagePartFactory.create(forumMessagePartPK, clob,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(forumClobMessagePart.getForumMessagePart().getLastDetail().getForumMessagePK(), EventTypes.MODIFY, forumClobMessagePart.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteForumClobMessagePart(ForumClobMessagePart forumClobMessagePart, BasePK deletedBy) {
        forumClobMessagePart.setThruTime(session.getStartTime());
        
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

    @Inject
    protected ForumBlobMessagePartFactory forumBlobMessagePartFactory;

    public ForumBlobMessagePart createForumBlobMessagePart(ForumMessagePart forumMessagePart, ByteArray blob, BasePK createdBy) {
        var forumBlobMessagePart = forumBlobMessagePartFactory.create(
                forumMessagePart, blob, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(forumMessagePart.getLastDetail().getForumMessagePK(), EventTypes.MODIFY, forumBlobMessagePart.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return forumBlobMessagePart;
    }
    
    private ForumBlobMessagePart getForumBlobMessagePart(ForumMessagePart forumMessagePart, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ForumBlobMessageParts.fields())
                .from(ForumBlobMessageParts)
                .where(ForumBlobMessageParts.FORUM_MESSAGE_PART.eq(forumMessagePart.getPrimaryKey()),
                        ForumBlobMessageParts.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return forumBlobMessagePartFactory.getEntityFromQuery(entityPermission, query);
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
            var forumBlobMessagePart = forumBlobMessagePartFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     forumBlobMessagePartValue.getPrimaryKey());
            
            forumBlobMessagePart.setThruTime(session.getStartTime());
            forumBlobMessagePart.store();

            var forumMessagePartPK = forumBlobMessagePart.getForumMessagePartPK(); // Not updated
            var blob = forumBlobMessagePartValue.getBlob();
            
            forumBlobMessagePart = forumBlobMessagePartFactory.create(forumMessagePartPK, blob,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(forumBlobMessagePart.getForumMessagePart().getLastDetail().getForumMessagePK(), EventTypes.MODIFY, forumBlobMessagePart.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteForumBlobMessagePart(ForumBlobMessagePart forumBlobMessagePart, BasePK deletedBy) {
        forumBlobMessagePart.setThruTime(session.getStartTime());
        
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

    @Inject
    protected ForumMessagePartTypeFactory forumMessagePartTypeFactory;

    @Inject
    ForumMessagePartTypeTransferCache forumMessagePartTypeTransferCache;

    public ForumMessagePartType createForumMessagePartType(String forumMessagePartTypeName, MimeTypeUsageType mimeTypeUsageType,
            Integer sortOrder) {
        return forumMessagePartTypeFactory.create(forumMessagePartTypeName, mimeTypeUsageType, sortOrder);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ForumMessagePartType */
    public ForumMessagePartType getForumMessagePartTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ForumMessagePartTypePK(entityInstance.getEntityUniqueId());

        return forumMessagePartTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public ForumMessagePartType getForumMessagePartTypeByEntityInstance(EntityInstance entityInstance) {
        return getForumMessagePartTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ForumMessagePartType getForumMessagePartTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getForumMessagePartTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countForumMessagePartTypes() {
        return session.getDslContext()
                .selectCount()
                .from(ForumMessagePartTypes)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public ForumMessagePartType getForumMessagePartTypeByName(String forumMessagePartTypeName) {
        var query = session.getDslContext()
                .select(ForumMessagePartTypes.fields())
                .from(ForumMessagePartTypes)
                .where(ForumMessagePartTypes.FORUM_MESSAGE_PART_TYPE_NAME.eq(forumMessagePartTypeName));

        return forumMessagePartTypeFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public List<ForumMessagePartType> getForumMessagePartTypes() {
        var query = session.applyLimit(session.getDslContext()
                .select(ForumMessagePartTypes.fields())
                .from(ForumMessagePartTypes)
                .orderBy(ForumMessagePartTypes.SORT_ORDER, ForumMessagePartTypes.FORUM_MESSAGE_PART_TYPE_NAME),
                ForumMessagePartTypeFactory.class);

        return forumMessagePartTypeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public ForumMessagePartTypeTransfer getForumMessagePartTypeTransfer(UserVisit userVisit, ForumMessagePartType forumMessagePartType) {
        return forumMessagePartTypeTransferCache.getForumMessagePartTypeTransfer(userVisit, forumMessagePartType);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Message Part Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected ForumMessagePartTypeDescriptionFactory forumMessagePartTypeDescriptionFactory;

    public ForumMessagePartTypeDescription createForumMessagePartTypeDescription(ForumMessagePartType forumMessagePartType,
            Language language, String description) {
        return forumMessagePartTypeDescriptionFactory.create(forumMessagePartType, language, description);
    }
    
    public ForumMessagePartTypeDescription getForumMessagePartTypeDescription(ForumMessagePartType forumMessagePartType,
            Language language) {
        var query = session.getDslContext()
                .select(ForumMessagePartTypeDescriptions.fields())
                .from(ForumMessagePartTypeDescriptions)
                .where(ForumMessagePartTypeDescriptions.FORUM_MESSAGE_PART_TYPE.eq(forumMessagePartType.getPrimaryKey()),
                        ForumMessagePartTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()));

        return forumMessagePartTypeDescriptionFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
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

    @Inject
    protected ForumMessageTypeFactory forumMessageTypeFactory;

    @Inject
    ForumMessageTypeTransferCache forumMessageTypeTransferCache;

    public ForumMessageType createForumMessageType(String forumMessageTypeName, Boolean isDefault, Integer sortOrder) {
        return forumMessageTypeFactory.create(forumMessageTypeName, isDefault, sortOrder);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ForumMessageType */
    public ForumMessageType getForumMessageTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ForumMessageTypePK(entityInstance.getEntityUniqueId());

        return forumMessageTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public ForumMessageType getForumMessageTypeByEntityInstance(EntityInstance entityInstance) {
        return getForumMessageTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ForumMessageType getForumMessageTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getForumMessageTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countForumMessageTypes() {
        return session.getDslContext()
                .selectCount()
                .from(ForumMessageTypes)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public ForumMessageType getForumMessageTypeByName(String forumMessageTypeName) {
        var query = session.getDslContext()
                .select(ForumMessageTypes.fields())
                .from(ForumMessageTypes)
                .where(ForumMessageTypes.FORUM_MESSAGE_TYPE_NAME.eq(forumMessageTypeName));

        return forumMessageTypeFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public List<ForumMessageType> getForumMessageTypes() {
        var query = session.applyLimit(session.getDslContext()
                .select(ForumMessageTypes.fields())
                .from(ForumMessageTypes)
                .orderBy(ForumMessageTypes.SORT_ORDER, ForumMessageTypes.FORUM_MESSAGE_TYPE_NAME),
                ForumMessageTypeFactory.class);

        return forumMessageTypeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
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
        return forumMessageTypeTransferCache.getForumMessageTypeTransfer(userVisit, forumMessageType);
    }
    
    // --------------------------------------------------------------------------------
    //   Forum Message Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected ForumMessageTypeDescriptionFactory forumMessageTypeDescriptionFactory;

    public ForumMessageTypeDescription createForumMessageTypeDescription(ForumMessageType forumMessageType, Language language,
            String description) {
        return forumMessageTypeDescriptionFactory.create(forumMessageType, language, description);
    }
    
    public ForumMessageTypeDescription getForumMessageTypeDescription(ForumMessageType forumMessageType, Language language) {
        var query = session.getDslContext()
                .select(ForumMessageTypeDescriptions.fields())
                .from(ForumMessageTypeDescriptions)
                .where(ForumMessageTypeDescriptions.FORUM_MESSAGE_TYPE.eq(forumMessageType.getPrimaryKey()),
                        ForumMessageTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()));

        return forumMessageTypeDescriptionFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
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

    @Inject
    protected ForumMessageTypePartTypeFactory forumMessageTypePartTypeFactory;

    @Inject
    ForumMessageTypePartTypeTransferCache forumMessageTypePartTypeTransferCache;

    public ForumMessageTypePartType createForumMessageTypePartType(ForumMessageType forumMessageType, Boolean includeInIndex, Boolean indexDefault,
            Integer sortOrder, ForumMessagePartType forumMessagePartType) {
        return forumMessageTypePartTypeFactory.create(forumMessageType, includeInIndex, indexDefault, sortOrder, forumMessagePartType);
    }
    
    public ForumMessageTypePartType getForumMessageTypePartType(ForumMessageType forumMessageType, Integer sortOrder) {
        var query = session.getDslContext()
                .select(ForumMessageTypePartTypes.fields())
                .from(ForumMessageTypePartTypes)
                .where(ForumMessageTypePartTypes.FORUM_MESSAGE_TYPE.eq(forumMessageType.getPrimaryKey()),
                        ForumMessageTypePartTypes.SORT_ORDER.eq(sortOrder));

        return forumMessageTypePartTypeFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public ForumMessageTypePartType getIndexDefaultForumMessageTypePartType(ForumMessageType forumMessageType) {
        var query = session.getDslContext()
                .select(ForumMessageTypePartTypes.fields())
                .from(ForumMessageTypePartTypes)
                .where(ForumMessageTypePartTypes.FORUM_MESSAGE_TYPE.eq(forumMessageType.getPrimaryKey()),
                        ForumMessageTypePartTypes.INDEX_DEFAULT.eq(true));

        return forumMessageTypePartTypeFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public List<ForumMessageTypePartType> getForumMessageTypePartTypesByForumMessageType(ForumMessageType forumMessageType) {
        var query = session.applyLimit(session.getDslContext()
                .select(ForumMessageTypePartTypes.fields())
                .from(ForumMessageTypePartTypes)
                .where(ForumMessageTypePartTypes.FORUM_MESSAGE_TYPE.eq(forumMessageType.getPrimaryKey()))
                .orderBy(ForumMessageTypePartTypes.SORT_ORDER),
                ForumMessageTypePartTypeFactory.class);

        return forumMessageTypePartTypeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public List<ForumMessageTypePartType> getForumMessageTypePartTypesByForumMessageTypeAndIncludeInIndex(ForumMessageType forumMessageType) {
        var query = session.applyLimit(session.getDslContext()
                .select(ForumMessageTypePartTypes.fields())
                .from(ForumMessageTypePartTypes)
                .where(ForumMessageTypePartTypes.FORUM_MESSAGE_TYPE.eq(forumMessageType.getPrimaryKey()),
                        ForumMessageTypePartTypes.INCLUDE_IN_INDEX.eq(true))
                .orderBy(ForumMessageTypePartTypes.SORT_ORDER),
                ForumMessageTypePartTypeFactory.class);

        return forumMessageTypePartTypeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public List<ForumMessageTypePartTypeTransfer> getForumMessageTypePartTypeTransfersByForumMessageType(UserVisit userVisit,
            ForumMessageType forumMessageType) {
        var forumMessageTypePartTypes = getForumMessageTypePartTypesByForumMessageType(forumMessageType);
        List<ForumMessageTypePartTypeTransfer> forumMessageTypePartTypeTransfers = null;
        
        if(forumMessageTypePartTypes != null) {
            
            forumMessageTypePartTypeTransfers = new ArrayList<>(forumMessageTypePartTypes.size());
            
            for(var forumMessageTypePartType : forumMessageTypePartTypes) {
                forumMessageTypePartTypeTransfers.add(forumMessageTypePartTypeTransferCache.getForumMessageTypePartTypeTransfer(userVisit, forumMessageTypePartType));
            }
        }
        
        return forumMessageTypePartTypeTransfers;
    }
    
    public ForumMessageTypePartTypeTransfer getForumMessageTypePartTypeTransfer(UserVisit userVisit, ForumMessageTypePartType forumMessageTypePartType) {
        return forumMessageTypePartTypeTransferCache.getForumMessageTypePartTypeTransfer(userVisit, forumMessageTypePartType);
    }
    
}
