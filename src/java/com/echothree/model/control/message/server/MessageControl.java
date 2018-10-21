// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.model.control.message.server;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.message.remote.choice.MessageChoicesBean;
import com.echothree.model.control.message.remote.transfer.EntityMessageTransfer;
import com.echothree.model.control.message.remote.transfer.MessageBlobTransfer;
import com.echothree.model.control.message.remote.transfer.MessageClobTransfer;
import com.echothree.model.control.message.remote.transfer.MessageDescriptionTransfer;
import com.echothree.model.control.message.remote.transfer.MessageStringTransfer;
import com.echothree.model.control.message.remote.transfer.MessageTransfer;
import com.echothree.model.control.message.remote.transfer.MessageTypeDescriptionTransfer;
import com.echothree.model.control.message.remote.transfer.MessageTypeTransfer;
import com.echothree.model.control.message.server.transfer.EntityMessageTransferCache;
import com.echothree.model.control.message.server.transfer.MessageBlobTransferCache;
import com.echothree.model.control.message.server.transfer.MessageClobTransferCache;
import com.echothree.model.control.message.server.transfer.MessageDescriptionTransferCache;
import com.echothree.model.control.message.server.transfer.MessageStringTransferCache;
import com.echothree.model.control.message.server.transfer.MessageTransferCache;
import com.echothree.model.control.message.server.transfer.MessageTransferCaches;
import com.echothree.model.control.message.server.transfer.MessageTypeDescriptionTransferCache;
import com.echothree.model.control.message.server.transfer.MessageTypeTransferCache;
import com.echothree.model.data.core.remote.pk.EntityTypePK;
import com.echothree.model.data.core.remote.pk.MimeTypePK;
import com.echothree.model.data.core.remote.pk.MimeTypeUsageTypePK;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.core.server.entity.MimeTypeUsageType;
import com.echothree.model.data.message.remote.pk.MessagePK;
import com.echothree.model.data.message.remote.pk.MessageTypePK;
import com.echothree.model.data.message.server.entity.EntityMessage;
import com.echothree.model.data.message.server.entity.Message;
import com.echothree.model.data.message.server.entity.MessageBlob;
import com.echothree.model.data.message.server.entity.MessageClob;
import com.echothree.model.data.message.server.entity.MessageDescription;
import com.echothree.model.data.message.server.entity.MessageDetail;
import com.echothree.model.data.message.server.entity.MessageString;
import com.echothree.model.data.message.server.entity.MessageType;
import com.echothree.model.data.message.server.entity.MessageTypeDescription;
import com.echothree.model.data.message.server.entity.MessageTypeDetail;
import com.echothree.model.data.message.server.factory.EntityMessageFactory;
import com.echothree.model.data.message.server.factory.MessageBlobFactory;
import com.echothree.model.data.message.server.factory.MessageClobFactory;
import com.echothree.model.data.message.server.factory.MessageDescriptionFactory;
import com.echothree.model.data.message.server.factory.MessageDetailFactory;
import com.echothree.model.data.message.server.factory.MessageFactory;
import com.echothree.model.data.message.server.factory.MessageStringFactory;
import com.echothree.model.data.message.server.factory.MessageTypeDescriptionFactory;
import com.echothree.model.data.message.server.factory.MessageTypeDetailFactory;
import com.echothree.model.data.message.server.factory.MessageTypeFactory;
import com.echothree.model.data.message.server.value.MessageBlobValue;
import com.echothree.model.data.message.server.value.MessageClobValue;
import com.echothree.model.data.message.server.value.MessageDescriptionValue;
import com.echothree.model.data.message.server.value.MessageDetailValue;
import com.echothree.model.data.message.server.value.MessageStringValue;
import com.echothree.model.data.message.server.value.MessageTypeDescriptionValue;
import com.echothree.model.data.message.server.value.MessageTypeDetailValue;
import com.echothree.model.data.party.remote.pk.LanguagePK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.remote.persistence.BasePK;
import com.echothree.util.remote.persistence.type.ByteArray;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MessageControl
        extends BaseModelControl {
    
    /** Creates a new instance of MessageControl */
    public MessageControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Message Transfer Caches
    // --------------------------------------------------------------------------------
    
    private MessageTransferCaches messageTransferCaches = null;
    
    public MessageTransferCaches getMessageTransferCaches(UserVisit userVisit) {
        if(messageTransferCaches == null) {
            messageTransferCaches = new MessageTransferCaches(userVisit, this);
        }
        
        return messageTransferCaches;
    }
    
    // --------------------------------------------------------------------------------
    //   Message Types
    // --------------------------------------------------------------------------------
    
    public MessageType createMessageType(EntityType entityType, String messageTypeName, MimeTypeUsageType mimeTypeUsageType,
            Integer sortOrder, BasePK createdBy) {
        MessageType messageType = MessageTypeFactory.getInstance().create();
        MessageTypeDetail messageTypeDetail = MessageTypeDetailFactory.getInstance().create(messageType, entityType,
                messageTypeName, mimeTypeUsageType, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        messageType = MessageTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                messageType.getPrimaryKey());
        messageType.setActiveDetail(messageTypeDetail);
        messageType.setLastDetail(messageTypeDetail);
        messageType.store();
        
        sendEventUsingNames(messageType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return messageType;
    }
    
    private List<MessageType> getMessageTypes(EntityType entityType, EntityPermission entityPermission) {
        List<MessageType> messageTypes = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM messagetypes, messagetypedetails " +
                        "WHERE mssgtyp_activedetailid = mssgtypdt_messagetypedetailid AND mssgtypdt_ent_entitytypeid = ? " +
                        "ORDER BY mssgtypdt_sortorder, mssgtypdt_messagetypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM messagetypes, messagetypedetails " +
                        "WHERE mssgtyp_activedetailid = mssgtypdt_messagetypedetailid AND mssgtypdt_ent_entitytypeid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = MessageTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityType.getPrimaryKey().getEntityId());
            
            messageTypes = MessageTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return messageTypes;
    }
    
    public List<MessageType> getMessageTypes(EntityType entityType) {
        return getMessageTypes(entityType, EntityPermission.READ_ONLY);
    }
    
    public List<MessageType> getMessageTypesForUpdate(EntityType entityType) {
        return getMessageTypes(entityType, EntityPermission.READ_WRITE);
    }
    
    private MessageType getMessageTypeByName(EntityType entityType, String messageTypeName, EntityPermission entityPermission) {
        MessageType messageType = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM messagetypes, messagetypedetails " +
                        "WHERE mssgtyp_activedetailid = mssgtypdt_messagetypedetailid AND mssgtypdt_ent_entitytypeid = ? " +
                        "AND mssgtypdt_messagetypename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM messagetypes, messagetypedetails " +
                        "WHERE mssgtyp_activedetailid = mssgtypdt_messagetypedetailid AND mssgtypdt_ent_entitytypeid = ? " +
                        "AND mssgtypdt_messagetypename = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = MessageTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityType.getPrimaryKey().getEntityId());
            ps.setString(2, messageTypeName);
            
            messageType = MessageTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return messageType;
    }
    
    public MessageType getMessageTypeByName(EntityType entityType, String messageTypeName) {
        return getMessageTypeByName(entityType, messageTypeName, EntityPermission.READ_ONLY);
    }
    
    public MessageType getMessageTypeByNameForUpdate(EntityType entityType, String messageTypeName) {
        return getMessageTypeByName(entityType, messageTypeName, EntityPermission.READ_WRITE);
    }
    
    public MessageTypeDetailValue getMessageTypeDetailValueForUpdate(MessageType messageType) {
        return messageType == null? null: messageType.getLastDetailForUpdate().getMessageTypeDetailValue().clone();
    }
    
    public MessageTypeDetailValue getMessageTypeDetailValueByNameForUpdate(EntityType entityType, String messageTypeName) {
        return getMessageTypeDetailValueForUpdate(getMessageTypeByNameForUpdate(entityType, messageTypeName));
    }
    
    public MessageTypeTransfer getMessageTypeTransfer(UserVisit userVisit, MessageType messageType) {
        return getMessageTransferCaches(userVisit).getMessageTypeTransferCache().getMessageTypeTransfer(messageType);
    }
    
    public List<MessageTypeTransfer> getMessageTypeTransfers(UserVisit userVisit, EntityType entityType) {
        List<MessageType> messageTypes = getMessageTypes(entityType);
        List<MessageTypeTransfer> messageTypeTransfers = new ArrayList<>(messageTypes.size());
        MessageTypeTransferCache MessageTypeTransferCache = getMessageTransferCaches(userVisit).getMessageTypeTransferCache();
        
        messageTypes.stream().forEach((messageType) -> {
            messageTypeTransfers.add(MessageTypeTransferCache.getMessageTypeTransfer(messageType));
        });
        
        return messageTypeTransfers;
    }
    
    public void updateMessageTypeFromValue(MessageTypeDetailValue messageTypeDetailValue, BasePK updatedBy) {
        if(messageTypeDetailValue.hasBeenModified()) {
            MessageType messageType = MessageTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     messageTypeDetailValue.getMessageTypePK());
            MessageTypeDetail messageTypeDetail = messageType.getActiveDetailForUpdate();
            
            messageTypeDetail.setThruTime(session.START_TIME_LONG);
            messageTypeDetail.store();
            
            MessageTypePK messageTypePK = messageTypeDetail.getMessageTypePK(); // Not updated
            EntityTypePK entityTypePK = messageTypeDetail.getEntityTypePK(); // Not updated
            String messageTypeName = messageTypeDetailValue.getMessageTypeName();
            MimeTypeUsageTypePK mimeTypeUsageTypePK = messageTypeDetail.getMimeTypeUsageTypePK(); // Not updated
            Integer sortOrder = messageTypeDetailValue.getSortOrder();
            
            messageTypeDetail = MessageTypeDetailFactory.getInstance().create(messageTypePK, entityTypePK, messageTypeName,
                    mimeTypeUsageTypePK, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            messageType.setActiveDetail(messageTypeDetail);
            messageType.setLastDetail(messageTypeDetail);
            
            sendEventUsingNames(messageTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void deleteMessageType(MessageType messageType, BasePK deletedBy) {
        deleteMessageTypeDescriptionsByMessageType(messageType, deletedBy);
        
        MessageTypeDetail messageTypeDetail = messageType.getLastDetailForUpdate();
        messageTypeDetail.setThruTime(session.START_TIME_LONG);
        messageType.setActiveDetail(null);
        messageType.store();
        
        sendEventUsingNames(messageType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    public void deleteMessageTypes(List<MessageType> messageTypes, BasePK deletedBy) {
        messageTypes.stream().forEach((messageType) -> {
            deleteMessageType(messageType, deletedBy);
        });
    }
    
    public void deleteMessageTypesByEntityType(EntityType entityType, BasePK deletedBy) {
        deleteMessageTypes(getMessageTypesForUpdate(entityType), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Message Type Descriptions
    // --------------------------------------------------------------------------------
    
    public MessageTypeDescription createMessageTypeDescription(MessageType messageType, Language language, String description,
            BasePK createdBy) {
        MessageTypeDescription messageTypeDescription = MessageTypeDescriptionFactory.getInstance().create(messageType,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(messageType.getPrimaryKey(), EventTypes.MODIFY.name(), messageTypeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return messageTypeDescription;
    }
    
    private MessageTypeDescription getMessageTypeDescription(MessageType messageType, Language language, EntityPermission entityPermission) {
        MessageTypeDescription messageTypeDescription = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM messagetypedescriptions " +
                        "WHERE mssgtypd_mssgtyp_messagetypeid = ? AND mssgtypd_lang_languageid = ? AND mssgtypd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM messagetypedescriptions " +
                        "WHERE mssgtypd_mssgtyp_messagetypeid = ? AND mssgtypd_lang_languageid = ? AND mssgtypd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = MessageTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, messageType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            messageTypeDescription = MessageTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return messageTypeDescription;
    }
    
    public MessageTypeDescription getMessageTypeDescription(MessageType messageType, Language language) {
        return getMessageTypeDescription(messageType, language, EntityPermission.READ_ONLY);
    }
    
    public MessageTypeDescription getMessageTypeDescriptionForUpdate(MessageType messageType, Language language) {
        return getMessageTypeDescription(messageType, language, EntityPermission.READ_WRITE);
    }
    
    public MessageTypeDescriptionValue getMessageTypeDescriptionValue(MessageTypeDescription messageTypeDescription) {
        return messageTypeDescription == null? null: messageTypeDescription.getMessageTypeDescriptionValue().clone();
    }
    
    public MessageTypeDescriptionValue getMessageTypeDescriptionValueForUpdate(MessageType messageType, Language language) {
        return getMessageTypeDescriptionValue(getMessageTypeDescriptionForUpdate(messageType, language));
    }
    
    private List<MessageTypeDescription> getMessageTypeDescriptionsByMessageType(MessageType messageType, EntityPermission entityPermission) {
        List<MessageTypeDescription> messageTypeDescriptions = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM messagetypedescriptions, languages " +
                        "WHERE mssgtypd_mssgtyp_messagetypeid = ? AND mssgtypd_thrutime = ? AND mssgtypd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM messagetypedescriptions " +
                        "WHERE mssgtypd_mssgtyp_messagetypeid = ? AND mssgtypd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = MessageTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, messageType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            messageTypeDescriptions = MessageTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return messageTypeDescriptions;
    }
    
    public List<MessageTypeDescription> getMessageTypeDescriptionsByMessageType(MessageType messageType) {
        return getMessageTypeDescriptionsByMessageType(messageType, EntityPermission.READ_ONLY);
    }
    
    public List<MessageTypeDescription> getMessageTypeDescriptionsByMessageTypeForUpdate(MessageType messageType) {
        return getMessageTypeDescriptionsByMessageType(messageType, EntityPermission.READ_WRITE);
    }
    
    public String getBestMessageTypeDescription(MessageType messageType, Language language) {
        String description;
        MessageTypeDescription messageTypeDescription = getMessageTypeDescription(messageType, language);
        
        if(messageTypeDescription == null && !language.getIsDefault()) {
            messageTypeDescription = getMessageTypeDescription(messageType, getPartyControl().getDefaultLanguage());
        }
        
        if(messageTypeDescription == null) {
            description = messageType.getLastDetail().getMessageTypeName();
        } else {
            description = messageTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public MessageTypeDescriptionTransfer getMessageTypeDescriptionTransfer(UserVisit userVisit, MessageTypeDescription messageTypeDescription) {
        return getMessageTransferCaches(userVisit).getMessageTypeDescriptionTransferCache().getMessageTypeDescriptionTransfer(messageTypeDescription);
    }
    
    public List<MessageTypeDescriptionTransfer> getMessageTypeDescriptionTransfers(UserVisit userVisit, MessageType messageType) {
        List<MessageTypeDescription> messageTypeDescriptions = getMessageTypeDescriptionsByMessageType(messageType);
        List<MessageTypeDescriptionTransfer> messageTypeDescriptionTransfers = new ArrayList<>(messageTypeDescriptions.size());
        MessageTypeDescriptionTransferCache messageTypeDescriptionTransferCache = getMessageTransferCaches(userVisit).getMessageTypeDescriptionTransferCache();
        
        messageTypeDescriptions.stream().forEach((messageTypeDescription) -> {
            messageTypeDescriptionTransfers.add(messageTypeDescriptionTransferCache.getMessageTypeDescriptionTransfer(messageTypeDescription));
        });
        
        return messageTypeDescriptionTransfers;
    }
    
    public void updateMessageTypeDescriptionFromValue(MessageTypeDescriptionValue messageTypeDescriptionValue, BasePK updatedBy) {
        if(messageTypeDescriptionValue.hasBeenModified()) {
            MessageTypeDescription messageTypeDescription = MessageTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     messageTypeDescriptionValue.getPrimaryKey());
            
            messageTypeDescription.setThruTime(session.START_TIME_LONG);
            messageTypeDescription.store();
            
            MessageType messageType = messageTypeDescription.getMessageType();
            Language language = messageTypeDescription.getLanguage();
            String description = messageTypeDescriptionValue.getDescription();
            
            messageTypeDescription = MessageTypeDescriptionFactory.getInstance().create(messageType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(messageType.getPrimaryKey(), EventTypes.MODIFY.name(), messageTypeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteMessageTypeDescription(MessageTypeDescription messageTypeDescription, BasePK deletedBy) {
        messageTypeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(messageTypeDescription.getMessageTypePK(), EventTypes.MODIFY.name(), messageTypeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
        
    }
    
    public void deleteMessageTypeDescriptionsByMessageType(MessageType messageType, BasePK deletedBy) {
        List<MessageTypeDescription> messageTypeDescriptions = getMessageTypeDescriptionsByMessageTypeForUpdate(messageType);
        
        messageTypeDescriptions.stream().forEach((messageTypeDescription) -> {
            deleteMessageTypeDescription(messageTypeDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Message Types
    // --------------------------------------------------------------------------------
    
    public Message createMessage(MessageType messageType, String messageName, Boolean includeByDefault, Boolean isDefault,
            Integer sortOrder, BasePK createdBy) {
        Message defaultMessage = getDefaultMessage(messageType);
        boolean defaultFound = defaultMessage != null;
        
        if(defaultFound && isDefault) {
            MessageDetailValue defaultMessageDetailValue = getDefaultMessageDetailValueForUpdate(messageType);
            
            defaultMessageDetailValue.setIsDefault(Boolean.FALSE);
            updateMessageFromValue(defaultMessageDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        Message message = MessageFactory.getInstance().create();
        MessageDetail messageDetail = MessageDetailFactory.getInstance().create(message, messageType, messageName,
                includeByDefault, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        message = MessageFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                message.getPrimaryKey());
        message.setActiveDetail(messageDetail);
        message.setLastDetail(messageDetail);
        message.store();
        
        sendEventUsingNames(message.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return message;
    }
    
    private List<Message> getMessagesByMessageType(MessageType messageType, EntityPermission entityPermission) {
        List<Message> messages = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM messages, messagedetails " +
                        "WHERE mssg_activedetailid = mssgdt_messagedetailid AND mssgdt_mssgtyp_messagetypeid = ? " +
                        "ORDER BY mssgdt_sortorder, mssgdt_messagename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM messages, messagedetails " +
                        "WHERE mssg_activedetailid = mssgdt_messagedetailid AND mssgdt_mssgtyp_messagetypeid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = MessageFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, messageType.getPrimaryKey().getEntityId());
            
            messages = MessageFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return messages;
    }
    
    public List<Message> getMessagesByMessageType(MessageType messageType) {
        return getMessagesByMessageType(messageType, EntityPermission.READ_ONLY);
    }
    
    public List<Message> getMessagesByMessageTypeForUpdate(MessageType messageType) {
        return getMessagesByMessageType(messageType, EntityPermission.READ_WRITE);
    }
    
    private Message getDefaultMessage(MessageType messageType, EntityPermission entityPermission) {
        Message message = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM messages, messagedetails " +
                        "WHERE mssg_activedetailid = mssgdt_messagedetailid " +
                        "AND mssgdt_mssgtyp_messagetypeid = ? AND mssgdt_isdefault = 1";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM messages, messagedetails " +
                        "WHERE mssg_activedetailid = mssgdt_messagedetailid " +
                        "AND mssgdt_mssgtyp_messagetypeid = ? AND mssgdt_isdefault = 1 " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = MessageFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, messageType.getPrimaryKey().getEntityId());
            
            message = MessageFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return message;
    }
    
    public Message getDefaultMessage(MessageType messageType) {
        return getDefaultMessage(messageType, EntityPermission.READ_ONLY);
    }
    
    public Message getDefaultMessageForUpdate(MessageType messageType) {
        return getDefaultMessage(messageType, EntityPermission.READ_WRITE);
    }
    
    public MessageDetailValue getDefaultMessageDetailValueForUpdate(MessageType messageType) {
        return getDefaultMessageForUpdate(messageType).getLastDetailForUpdate().getMessageDetailValue().clone();
    }
    
    private Message getMessageByName(MessageType messageType, String messageName, EntityPermission entityPermission) {
        Message message = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM messages, messagedetails " +
                        "WHERE mssg_activedetailid = mssgdt_messagedetailid AND mssgdt_mssgtyp_messagetypeid = ? " +
                        "AND mssgdt_messagename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM messages, messagedetails " +
                        "WHERE mssg_activedetailid = mssgdt_messagedetailid AND mssgdt_mssgtyp_messagetypeid = ? " +
                        "AND mssgdt_messagename = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = MessageFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, messageType.getPrimaryKey().getEntityId());
            ps.setString(2, messageName);
            
            message = MessageFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return message;
    }
    
    public Message getMessageByName(MessageType messageType, String messageName) {
        return getMessageByName(messageType, messageName, EntityPermission.READ_ONLY);
    }
    
    public Message getMessageByNameForUpdate(MessageType messageType, String messageName) {
        return getMessageByName(messageType, messageName, EntityPermission.READ_WRITE);
    }
    
    public MessageDetailValue getMessageDetailValueForUpdate(Message message) {
        return message == null? null: message.getLastDetailForUpdate().getMessageDetailValue().clone();
    }
    
    public MessageDetailValue getMessageDetailValueByNameForUpdate(MessageType messageType, String messageName) {
        return getMessageDetailValueForUpdate(getMessageByNameForUpdate(messageType, messageName));
    }
    
    public MessageChoicesBean getMessageChoices(String defaultMessageChoice, Language language,
            boolean allowNullChoice, MessageType messageType) {
        List<Message> messages = getMessagesByMessageType(messageType);
        int size = messages.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultMessageChoice == null) {
                defaultValue = "";
            }
        }
        
        for(Message message: messages) {
            MessageDetail messageDetail = message.getLastDetail();
            String label = getBestMessageDescription(message, language);
            String value = messageDetail.getMessageName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultMessageChoice == null? false: defaultMessageChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && messageDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new MessageChoicesBean(labels, values, defaultValue);
    }
    
    public MessageTransfer getMessageTransfer(UserVisit userVisit, Message message) {
        return getMessageTransferCaches(userVisit).getMessageTransferCache().getMessageTransfer(message);
    }
    
    public List<MessageTransfer> getMessageTransfers(UserVisit userVisit, MessageType messageType) {
        List<Message> messages = getMessagesByMessageType(messageType);
        List<MessageTransfer> messageTransfers = new ArrayList<>(messages.size());
        MessageTransferCache MessageTransferCache = getMessageTransferCaches(userVisit).getMessageTransferCache();
        
        messages.stream().forEach((message) -> {
            messageTransfers.add(MessageTransferCache.getMessageTransfer(message));
        });
        
        return messageTransfers;
    }
    
    private void updateMessageFromValue(MessageDetailValue messageDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(messageDetailValue.hasBeenModified()) {
            Message message = MessageFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    messageDetailValue.getMessagePK());
            MessageDetail messageDetail = message.getActiveDetailForUpdate();
            
            messageDetail.setThruTime(session.START_TIME_LONG);
            messageDetail.store();
            
            MessagePK messagePK = messageDetail.getMessagePK();
            MessageType messageType = messageDetail.getMessageType();
            MessageTypePK messageTypePK = messageType.getPrimaryKey();
            String messageName = messageDetailValue.getMessageName();
            Boolean includeByDefault = messageDetailValue.getIncludeByDefault();
            Boolean isDefault = messageDetailValue.getIsDefault();
            Integer sortOrder = messageDetailValue.getSortOrder();
            
            if(checkDefault) {
                Message defaultMessage = getDefaultMessage(messageType);
                boolean defaultFound = defaultMessage != null && !defaultMessage.equals(message);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    MessageDetailValue defaultMessageDetailValue = getDefaultMessageDetailValueForUpdate(messageType);
                    
                    defaultMessageDetailValue.setIsDefault(Boolean.FALSE);
                    updateMessageFromValue(defaultMessageDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            messageDetail = MessageDetailFactory.getInstance().create(messagePK, messageTypePK, messageName,
                    includeByDefault, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            message.setActiveDetail(messageDetail);
            message.setLastDetail(messageDetail);
            
            sendEventUsingNames(messagePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void updateMessageFromValue(MessageDetailValue messageDetailValue, BasePK updatedBy) {
        updateMessageFromValue(messageDetailValue, true, updatedBy);
    }
    
    public void deleteMessage(Message message, BasePK deletedBy) {
        deleteEntityMessagesByMessage(message, deletedBy);
        deleteMessageDescriptionsByMessage(message, deletedBy);
        deleteMessageStringsByMessage(message, deletedBy);
        deleteMessageBlobsByMessage(message, deletedBy);
        deleteMessageClobsByMessage(message, deletedBy);

        MessageDetail messageDetail = message.getLastDetailForUpdate();
        messageDetail.setThruTime(session.START_TIME_LONG);
        message.setActiveDetail(null);
        message.store();
        
        // Check for default, and pick one if necessary
        MessageType messageType = messageDetail.getMessageType();
        Message defaultMessage = getDefaultMessage(messageType);
        if(defaultMessage == null) {
            List<Message> messages = getMessagesByMessageTypeForUpdate(messageType);
            
            if(!messages.isEmpty()) {
                Iterator<Message> iter = messages.iterator();
                if(iter.hasNext()) {
                    defaultMessage = iter.next();
                }
                MessageDetailValue messageDetailValue = defaultMessage.getLastDetailForUpdate().getMessageDetailValue().clone();
                
                messageDetailValue.setIsDefault(Boolean.TRUE);
                updateMessageFromValue(messageDetailValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(message.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    public void deleteMessages(List<Message> messages, BasePK deletedBy) {
        messages.stream().forEach((message) -> {
            deleteMessage(message, deletedBy);
        });
    }
    
    public void deleteMessagesByMessageType(MessageType messageType, BasePK deletedBy) {
        deleteMessages(getMessagesByMessageTypeForUpdate(messageType), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Message Descriptions
    // --------------------------------------------------------------------------------
    
    public MessageDescription createMessageDescription(Message message, Language language, String description, BasePK createdBy) {
        MessageDescription messageDescription = MessageDescriptionFactory.getInstance().create(message,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(message.getPrimaryKey(), EventTypes.MODIFY.name(), messageDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return messageDescription;
    }
    
    private MessageDescription getMessageDescription(Message message, Language language, EntityPermission entityPermission) {
        MessageDescription messageDescription = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM messagedescriptions " +
                        "WHERE mssgd_mssg_messageid = ? AND mssgd_lang_languageid = ? AND mssgd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM messagedescriptions " +
                        "WHERE mssgd_mssg_messageid = ? AND mssgd_lang_languageid = ? AND mssgd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = MessageDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, message.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            messageDescription = MessageDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return messageDescription;
    }
    
    public MessageDescription getMessageDescription(Message message, Language language) {
        return getMessageDescription(message, language, EntityPermission.READ_ONLY);
    }
    
    public MessageDescription getMessageDescriptionForUpdate(Message message, Language language) {
        return getMessageDescription(message, language, EntityPermission.READ_WRITE);
    }
    
    public MessageDescriptionValue getMessageDescriptionValue(MessageDescription messageDescription) {
        return messageDescription == null? null: messageDescription.getMessageDescriptionValue().clone();
    }
    
    public MessageDescriptionValue getMessageDescriptionValueForUpdate(Message message, Language language) {
        return getMessageDescriptionValue(getMessageDescriptionForUpdate(message, language));
    }
    
    private List<MessageDescription> getMessageDescriptionsByMessage(Message message, EntityPermission entityPermission) {
        List<MessageDescription> messageDescriptions = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM messagedescriptions, languages " +
                        "WHERE mssgd_mssg_messageid = ? AND mssgd_thrutime = ? AND mssgd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM messagedescriptions " +
                        "WHERE mssgd_mssg_messageid = ? AND mssgd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = MessageDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, message.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            messageDescriptions = MessageDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return messageDescriptions;
    }
    
    public List<MessageDescription> getMessageDescriptionsByMessage(Message message) {
        return getMessageDescriptionsByMessage(message, EntityPermission.READ_ONLY);
    }
    
    public List<MessageDescription> getMessageDescriptionsByMessageForUpdate(Message message) {
        return getMessageDescriptionsByMessage(message, EntityPermission.READ_WRITE);
    }
    
    public String getBestMessageDescription(Message message, Language language) {
        String description;
        MessageDescription messageDescription = getMessageDescription(message, language);
        
        if(messageDescription == null && !language.getIsDefault()) {
            messageDescription = getMessageDescription(message, getPartyControl().getDefaultLanguage());
        }
        
        if(messageDescription == null) {
            description = message.getLastDetail().getMessageName();
        } else {
            description = messageDescription.getDescription();
        }
        
        return description;
    }
    
    public MessageDescriptionTransfer getMessageDescriptionTransfer(UserVisit userVisit, MessageDescription messageDescription) {
        return getMessageTransferCaches(userVisit).getMessageDescriptionTransferCache().getMessageDescriptionTransfer(messageDescription);
    }
    
    public List<MessageDescriptionTransfer> getMessageDescriptionTransfers(UserVisit userVisit, Message message) {
        List<MessageDescription> messageDescriptions = getMessageDescriptionsByMessage(message);
        List<MessageDescriptionTransfer> messageDescriptionTransfers = new ArrayList<>(messageDescriptions.size());
        MessageDescriptionTransferCache messageDescriptionTransferCache = getMessageTransferCaches(userVisit).getMessageDescriptionTransferCache();
        
        messageDescriptions.stream().forEach((messageDescription) -> {
            messageDescriptionTransfers.add(messageDescriptionTransferCache.getMessageDescriptionTransfer(messageDescription));
        });
        
        return messageDescriptionTransfers;
    }
    
    public void updateMessageDescriptionFromValue(MessageDescriptionValue messageDescriptionValue, BasePK updatedBy) {
        if(messageDescriptionValue.hasBeenModified()) {
            MessageDescription messageDescription = MessageDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, messageDescriptionValue.getPrimaryKey());
            
            messageDescription.setThruTime(session.START_TIME_LONG);
            messageDescription.store();
            
            Message message = messageDescription.getMessage();
            Language language = messageDescription.getLanguage();
            String description = messageDescriptionValue.getDescription();
            
            messageDescription = MessageDescriptionFactory.getInstance().create(message, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(message.getPrimaryKey(), EventTypes.MODIFY.name(), messageDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteMessageDescription(MessageDescription messageDescription, BasePK deletedBy) {
        messageDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(messageDescription.getMessagePK(), EventTypes.MODIFY.name(), messageDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
        
    }
    
    public void deleteMessageDescriptionsByMessage(Message message, BasePK deletedBy) {
        List<MessageDescription> messageDescriptions = getMessageDescriptionsByMessageForUpdate(message);
        
        messageDescriptions.stream().forEach((messageDescription) -> {
            deleteMessageDescription(messageDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Message Strings
    // --------------------------------------------------------------------------------
    
    public MessageString createMessageString(Message message, Language language, String string, BasePK createdBy) {
        MessageString messageString = MessageStringFactory.getInstance().create(message, language, string,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(messageString.getMessagePK(), EventTypes.MODIFY.name(), messageString.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return messageString;
    }
    
    private List<MessageString> getMessageStringsByMessage(Message message, EntityPermission entityPermission) {
        List<MessageString> messageStrings = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM messagestrings, languages " +
                        "WHERE mssgs_mssg_messageid = ? AND mssgs_thrutime = ? " +
                        "AND mssgs_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM messagestrings " +
                        "WHERE mssgs_mssg_messageid = ? AND mssgs_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = MessageStringFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, message.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            messageStrings = MessageStringFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return messageStrings;
    }
    
    public List<MessageString> getMessageStringsByMessage(Message message) {
        return getMessageStringsByMessage(message, EntityPermission.READ_ONLY);
    }
    
    public List<MessageString> getMessageStringsByMessageForUpdate(Message message) {
        return getMessageStringsByMessage(message, EntityPermission.READ_WRITE);
    }
    
    private MessageString getMessageString(Message message, Language language, EntityPermission entityPermission) {
        MessageString messageString = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM messagestrings " +
                        "WHERE mssgs_mssg_messageid = ? AND mssgs_lang_languageid = ? AND mssgs_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM messagestrings " +
                        "WHERE mssgs_mssg_messageid = ? AND mssgs_lang_languageid = ? AND mssgs_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = MessageStringFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, message.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            messageString = MessageStringFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return messageString;
    }
    
    public MessageString getMessageString(Message message, Language language) {
        return getMessageString(message, language, EntityPermission.READ_ONLY);
    }
    
    public MessageString getMessageStringForUpdate(Message message, Language language) {
        return getMessageString(message, language, EntityPermission.READ_WRITE);
    }
    
    public MessageStringValue getMessageStringValue(MessageString messageString) {
        return messageString == null? null: messageString.getMessageStringValue().clone();
    }
    
    public MessageStringValue getMessageStringValueForUpdate(Message message, Language language) {
        MessageString messageString = getMessageStringForUpdate(message, language);
        
        return messageString == null? null: getMessageStringValue(messageString);
    }
    
    public List<MessageStringTransfer> getMessageStringTransfers(UserVisit userVisit, List<MessageString> messageStrings) {
        List<MessageStringTransfer> messageStringTransfers = new ArrayList<>(messageStrings.size());
        MessageStringTransferCache MessageStringTransferCache = getMessageTransferCaches(userVisit).getMessageStringTransferCache();
        
        messageStrings.stream().forEach((messageString) -> {
            messageStringTransfers.add(MessageStringTransferCache.getMessageStringTransfer(messageString));
        });
        
        return messageStringTransfers;
    }
    
    public List<MessageStringTransfer> getMessageStringTransfersByMessage(UserVisit userVisit, Message message) {
        return getMessageStringTransfers(userVisit, getMessageStringsByMessage(message));
    }
    
    public void updateMessageStringFromValue(MessageStringValue messageStringValue, BasePK updatedBy) {
        if(messageStringValue.hasBeenModified()) {
            MessageString messageString = MessageStringFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    messageStringValue.getPrimaryKey());
            
            messageString.setThruTime(session.START_TIME_LONG);
            messageString.store();
            
            MessagePK messagePK = messageString.getMessagePK(); // Not updated
            LanguagePK languagePK = messageString.getLanguagePK(); // Not updated
            String string = messageStringValue.getString();
            
            messageString = MessageStringFactory.getInstance().create(messagePK, languagePK, string,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(messagePK, EventTypes.MODIFY.name(), messageString.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteMessageString(MessageString messageString, BasePK deletedBy) {
        messageString.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(messageString.getMessagePK(), EventTypes.MODIFY.name(), messageString.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteMessageStrings(List<MessageString> messageStrings, BasePK deletedBy) {
        messageStrings.stream().forEach((messageString) -> {
            deleteMessageString(messageString, deletedBy);
        });
    }
    
    public void deleteMessageStringsByMessage(Message message, BasePK deletedBy) {
        deleteMessageStrings(getMessageStringsByMessageForUpdate(message), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Message Blobs
    // --------------------------------------------------------------------------------
    
    public MessageBlob createMessageBlob(Message message, Language language, MimeType mimeType, ByteArray blob, BasePK createdBy) {
        MessageBlob messageBlob = MessageBlobFactory.getInstance().create(message, language, mimeType, blob,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(messageBlob.getMessagePK(), EventTypes.MODIFY.name(), messageBlob.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return messageBlob;
    }
    
    private List<MessageBlob> getMessageBlobsByMessage(Message message, EntityPermission entityPermission) {
        List<MessageBlob> messageBlobs = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM messageblobs, languages " +
                        "WHERE mssgb_mssg_messageid = ? AND mssgb_thrutime = ? " +
                        "AND mssgb_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM messageblobs " +
                        "WHERE mssgb_mssg_messageid = ? AND mssgb_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = MessageBlobFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, message.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            messageBlobs = MessageBlobFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return messageBlobs;
    }
    
    public List<MessageBlob> getMessageBlobsByMessage(Message message) {
        return getMessageBlobsByMessage(message, EntityPermission.READ_ONLY);
    }
    
    public List<MessageBlob> getMessageBlobsByMessageForUpdate(Message message) {
        return getMessageBlobsByMessage(message, EntityPermission.READ_WRITE);
    }
    
    private MessageBlob getMessageBlob(Message message, Language language, EntityPermission entityPermission) {
        MessageBlob messageBlob = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM messageblobs " +
                        "WHERE mssgb_mssg_messageid = ? AND mssgb_lang_languageid = ? AND mssgb_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM messageblobs " +
                        "WHERE mssgb_mssg_messageid = ? AND mssgb_lang_languageid = ? AND mssgb_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = MessageBlobFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, message.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            messageBlob = MessageBlobFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return messageBlob;
    }
    
    public MessageBlob getMessageBlob(Message message, Language language) {
        return getMessageBlob(message, language, EntityPermission.READ_ONLY);
    }
    
    public MessageBlob getMessageBlobForUpdate(Message message, Language language) {
        return getMessageBlob(message, language, EntityPermission.READ_WRITE);
    }
    
    public MessageBlobValue getMessageBlobValue(MessageBlob messageBlob) {
        return messageBlob == null? null: messageBlob.getMessageBlobValue().clone();
    }
    
    public MessageBlobValue getMessageBlobValueForUpdate(Message message, Language language) {
        MessageBlob messageBlob = getMessageBlobForUpdate(message, language);
        
        return messageBlob == null? null: getMessageBlobValue(messageBlob);
    }
    
    public List<MessageBlobTransfer> getMessageBlobTransfers(UserVisit userVisit, List<MessageBlob> messageBlobs) {
        List<MessageBlobTransfer> messageBlobTransfers = new ArrayList<>(messageBlobs.size());
        MessageBlobTransferCache MessageBlobTransferCache = getMessageTransferCaches(userVisit).getMessageBlobTransferCache();
        
        messageBlobs.stream().forEach((messageBlob) -> {
            messageBlobTransfers.add(MessageBlobTransferCache.getMessageBlobTransfer(messageBlob));
        });
        
        return messageBlobTransfers;
    }
    
    public List<MessageBlobTransfer> getMessageBlobTransfersByMessage(UserVisit userVisit, Message message) {
        return getMessageBlobTransfers(userVisit, getMessageBlobsByMessage(message));
    }
    
    public void updateMessageBlobFromValue(MessageBlobValue messageBlobValue, BasePK updatedBy) {
        if(messageBlobValue.hasBeenModified()) {
            MessageBlob messageBlob = MessageBlobFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    messageBlobValue.getPrimaryKey());
            
            messageBlob.setThruTime(session.START_TIME_LONG);
            messageBlob.store();
            
            MessagePK messagePK = messageBlob.getMessagePK(); // Not updated
            LanguagePK languagePK = messageBlob.getLanguagePK(); // Not updated
            MimeTypePK mimeTypePK = messageBlobValue.getMimeTypePK();
            ByteArray blob = messageBlobValue.getBlob();
            
            messageBlob = MessageBlobFactory.getInstance().create(messagePK, languagePK, mimeTypePK, blob,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(messagePK, EventTypes.MODIFY.name(), messageBlob.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteMessageBlob(MessageBlob messageBlob, BasePK deletedBy) {
        messageBlob.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(messageBlob.getMessagePK(), EventTypes.MODIFY.name(), messageBlob.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteMessageBlobs(List<MessageBlob> messageBlobs, BasePK deletedBy) {
        messageBlobs.stream().forEach((messageBlob) -> {
            deleteMessageBlob(messageBlob, deletedBy);
        });
    }
    
    public void deleteMessageBlobsByMessage(Message message, BasePK deletedBy) {
        deleteMessageBlobs(getMessageBlobsByMessageForUpdate(message), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Message Clobs
    // --------------------------------------------------------------------------------
    
    public MessageClob createMessageClob(Message message, Language language, MimeType mimeType, String clob, BasePK createdBy) {
        MessageClob messageClob = MessageClobFactory.getInstance().create(message, language, mimeType, clob,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(messageClob.getMessagePK(), EventTypes.MODIFY.name(), messageClob.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return messageClob;
    }
    
    private List<MessageClob> getMessageClobsByMessage(Message message, EntityPermission entityPermission) {
        List<MessageClob> messageClobs = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM messageclobs, languages " +
                        "WHERE mssgc_mssg_messageid = ? AND mssgc_thrutime = ? " +
                        "AND mssgc_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM messageclobs " +
                        "WHERE mssgc_mssg_messageid = ? AND mssgc_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = MessageClobFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, message.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            messageClobs = MessageClobFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return messageClobs;
    }
    
    public List<MessageClob> getMessageClobsByMessage(Message message) {
        return getMessageClobsByMessage(message, EntityPermission.READ_ONLY);
    }
    
    public List<MessageClob> getMessageClobsByMessageForUpdate(Message message) {
        return getMessageClobsByMessage(message, EntityPermission.READ_WRITE);
    }
    
    private MessageClob getMessageClob(Message message, Language language, EntityPermission entityPermission) {
        MessageClob messageClob = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM messageclobs " +
                        "WHERE mssgc_mssg_messageid = ? AND mssgc_lang_languageid = ? AND mssgc_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM messageclobs " +
                        "WHERE mssgc_mssg_messageid = ? AND mssgc_lang_languageid = ? AND mssgc_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = MessageClobFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, message.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            messageClob = MessageClobFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return messageClob;
    }
    
    public MessageClob getMessageClob(Message message, Language language) {
        return getMessageClob(message, language, EntityPermission.READ_ONLY);
    }
    
    public MessageClob getMessageClobForUpdate(Message message, Language language) {
        return getMessageClob(message, language, EntityPermission.READ_WRITE);
    }
    
    public MessageClobValue getMessageClobValue(MessageClob messageClob) {
        return messageClob == null? null: messageClob.getMessageClobValue().clone();
    }
    
    public MessageClobValue getMessageClobValueForUpdate(Message message, Language language) {
        MessageClob messageClob = getMessageClobForUpdate(message, language);
        
        return messageClob == null? null: getMessageClobValue(messageClob);
    }
    
    public List<MessageClobTransfer> getMessageClobTransfers(UserVisit userVisit, List<MessageClob> messageClobs) {
        List<MessageClobTransfer> messageClobTransfers = new ArrayList<>(messageClobs.size());
        MessageClobTransferCache MessageClobTransferCache = getMessageTransferCaches(userVisit).getMessageClobTransferCache();
        
        messageClobs.stream().forEach((messageClob) -> {
            messageClobTransfers.add(MessageClobTransferCache.getMessageClobTransfer(messageClob));
        });
        
        return messageClobTransfers;
    }
    
    public List<MessageClobTransfer> getMessageClobTransfersByMessage(UserVisit userVisit, Message message) {
        return getMessageClobTransfers(userVisit, getMessageClobsByMessage(message));
    }
    
    public void updateMessageClobFromValue(MessageClobValue messageClobValue, BasePK updatedBy) {
        if(messageClobValue.hasBeenModified()) {
            MessageClob messageClob = MessageClobFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    messageClobValue.getPrimaryKey());
            
            messageClob.setThruTime(session.START_TIME_LONG);
            messageClob.store();
            
            MessagePK messagePK = messageClob.getMessagePK(); // Not updated
            LanguagePK languagePK = messageClob.getLanguagePK(); // Not updated
            MimeTypePK mimeTypePK = messageClobValue.getMimeTypePK();
            String clob = messageClobValue.getClob();
            
            messageClob = MessageClobFactory.getInstance().create(messagePK, languagePK, mimeTypePK, clob,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(messagePK, EventTypes.MODIFY.name(), messageClob.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteMessageClob(MessageClob messageClob, BasePK deletedBy) {
        messageClob.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(messageClob.getMessagePK(), EventTypes.MODIFY.name(), messageClob.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteMessageClobs(List<MessageClob> messageClobs, BasePK deletedBy) {
        messageClobs.stream().forEach((messageClob) -> {
            deleteMessageClob(messageClob, deletedBy);
        });
    }
    
    public void deleteMessageClobsByMessage(Message message, BasePK deletedBy) {
        deleteMessageClobs(getMessageClobsByMessageForUpdate(message), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Message Descriptions
    // --------------------------------------------------------------------------------
    
    public EntityMessage createEntityMessage(EntityInstance entityInstance, Message message, BasePK createdBy) {
        EntityMessage entityMessage = EntityMessageFactory.getInstance().create(entityInstance, message,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(message.getPrimaryKey(), EventTypes.MODIFY.name(), entityMessage.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return entityMessage;
    }
    
    private EntityMessage getEntityMessage(EntityInstance entityInstance, Message message, EntityPermission entityPermission) {
        EntityMessage entityMessage = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitymessages " +
                        "WHERE emssg_eni_entityinstanceid = ? AND emssg_mssg_messageid = ? AND emssg_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitymessages " +
                        "WHERE emssg_eni_entityinstanceid = ? AND emssg_mssg_messageid = ? AND emssg_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityMessageFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, message.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            entityMessage = EntityMessageFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityMessage;
    }
    
    public EntityMessage getEntityMessage(EntityInstance entityInstance, Message message) {
        return getEntityMessage(entityInstance, message, EntityPermission.READ_ONLY);
    }
    
    public EntityMessage getEntityMessageForUpdate(EntityInstance entityInstance, Message message) {
        return getEntityMessage(entityInstance, message, EntityPermission.READ_WRITE);
    }
    
    private List<EntityMessage> getEntityMessagesByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        List<EntityMessage> entityMessages = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitymessages, messages, messagedetails " +
                        "WHERE emssg_eni_entityinstanceid = ? AND emssg_thrutime = ? " +
                        "AND emssg_mssg_messageid = mssg_messageid AND mssg_lastdetailid = mssgdt_messagedetailid " +
                        "ORDER BY mssgdt_sortorder, mssgdt_messagename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitymessages " +
                        "WHERE emssg_eni_entityinstanceid = ? AND emssg_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityMessageFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityMessages = EntityMessageFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityMessages;
    }
    
    public List<EntityMessage> getEntityMessagesByEntityInstance(EntityInstance entityInstance) {
        return getEntityMessagesByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }
    
    public List<EntityMessage> getEntityMessagesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getEntityMessagesByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    private List<EntityMessage> getEntityMessagesByMessage(Message message, EntityPermission entityPermission) {
        List<EntityMessage> entityMessages = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM entitymessages, entityinstances " +
                        "WHERE emssg_mssg_messageid = ? AND emssg_thrutime = ? AND emssg_eni_entityinstanceid = eni_entityinstanceid " +
                        "ORDER BY eni_entityuniqueid";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM entitymessages " +
                        "WHERE emssg_mssg_messageid = ? AND emssg_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = EntityMessageFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, message.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            entityMessages = EntityMessageFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return entityMessages;
    }
    
    public List<EntityMessage> getEntityMessagesByMessage(Message message) {
        return getEntityMessagesByMessage(message, EntityPermission.READ_ONLY);
    }
    
    public List<EntityMessage> getEntityMessagesByMessageForUpdate(Message message) {
        return getEntityMessagesByMessage(message, EntityPermission.READ_WRITE);
    }
    
    public EntityMessageTransfer getEntityMessageTransfer(UserVisit userVisit, EntityMessage entityMessage) {
        return getMessageTransferCaches(userVisit).getEntityMessageTransferCache().getEntityMessageTransfer(entityMessage);
    }
    
    public List<EntityMessageTransfer> getEntityMessageTransfers(UserVisit userVisit, List<EntityMessage> entityMessages) {
        List<EntityMessageTransfer> entityMessageTransfers = new ArrayList<>(entityMessages.size());
        EntityMessageTransferCache entityMessageTransferCache = getMessageTransferCaches(userVisit).getEntityMessageTransferCache();
        
        entityMessages.stream().forEach((entityMessage) -> {
            entityMessageTransfers.add(entityMessageTransferCache.getEntityMessageTransfer(entityMessage));
        });
        
        return entityMessageTransfers;
    }
    
    public List<EntityMessageTransfer> getEntityMessageTransfersByEntityInstance(UserVisit userVisit, EntityInstance entityInstance) {
        return getEntityMessageTransfers(userVisit, getEntityMessagesByEntityInstance(entityInstance));
    }
    
    public List<EntityMessageTransfer> getEntityMessageTransfersByMessage(UserVisit userVisit, Message message) {
        return getEntityMessageTransfers(userVisit, getEntityMessagesByMessage(message));
    }
    
    public void deleteEntityMessage(EntityMessage entityMessage, BasePK deletedBy) {
        entityMessage.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(entityMessage.getMessagePK(), EventTypes.MODIFY.name(), entityMessage.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
        
    }
    
    public void deleteEntityMessages(List<EntityMessage> entityMessages, BasePK deletedBy) {
        entityMessages.stream().forEach((entityMessage) -> {
            deleteEntityMessage(entityMessage, deletedBy);
        });
    }
    
    public void deleteEntityMessagesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteEntityMessages(getEntityMessagesByEntityInstanceForUpdate(entityInstance), deletedBy);
    }
    
    public void deleteEntityMessagesByMessage(Message message, BasePK deletedBy) {
        deleteEntityMessages(getEntityMessagesByMessageForUpdate(message), deletedBy);
    }
    
}
