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

package com.echothree.model.control.message.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.message.common.choice.MessageChoicesBean;
import com.echothree.model.control.message.common.transfer.EntityMessageTransfer;
import com.echothree.model.control.message.common.transfer.MessageBlobTransfer;
import com.echothree.model.control.message.common.transfer.MessageClobTransfer;
import com.echothree.model.control.message.common.transfer.MessageDescriptionTransfer;
import com.echothree.model.control.message.common.transfer.MessageStringTransfer;
import com.echothree.model.control.message.common.transfer.MessageTransfer;
import com.echothree.model.control.message.common.transfer.MessageTypeDescriptionTransfer;
import com.echothree.model.control.message.common.transfer.MessageTypeTransfer;
import com.echothree.model.control.message.server.transfer.MessageTransferCaches;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.core.server.entity.MimeTypeUsageType;
import com.echothree.model.data.message.server.entity.EntityMessage;
import com.echothree.model.data.message.server.entity.Message;
import com.echothree.model.data.message.server.entity.MessageBlob;
import com.echothree.model.data.message.server.entity.MessageClob;
import com.echothree.model.data.message.server.entity.MessageDescription;
import com.echothree.model.data.message.server.entity.MessageString;
import com.echothree.model.data.message.server.entity.MessageType;
import com.echothree.model.data.message.server.entity.MessageTypeDescription;
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
import com.echothree.model.data.party.server.entity.Language;
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
import java.util.List;
import java.util.Objects;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class MessageControl
        extends BaseModelControl {
    
    /** Creates a new instance of MessageControl */
    protected MessageControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Message Transfer Caches
    // --------------------------------------------------------------------------------
    
    private MessageTransferCaches messageTransferCaches;
    
    public MessageTransferCaches getMessageTransferCaches() {
        if(messageTransferCaches == null) {
            messageTransferCaches = new MessageTransferCaches();
        }
        
        return messageTransferCaches;
    }
    
    // --------------------------------------------------------------------------------
    //   Message Types
    // --------------------------------------------------------------------------------
    
    public MessageType createMessageType(EntityType entityType, String messageTypeName, MimeTypeUsageType mimeTypeUsageType,
            Integer sortOrder, BasePK createdBy) {
        var messageType = MessageTypeFactory.getInstance().create();
        var messageTypeDetail = MessageTypeDetailFactory.getInstance().create(messageType, entityType,
                messageTypeName, mimeTypeUsageType, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        messageType = MessageTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                messageType.getPrimaryKey());
        messageType.setActiveDetail(messageTypeDetail);
        messageType.setLastDetail(messageTypeDetail);
        messageType.store();
        
        sendEvent(messageType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return messageType;
    }
    
    private List<MessageType> getMessageTypes(EntityType entityType, EntityPermission entityPermission) {
        List<MessageType> messageTypes;
        
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

            var ps = MessageTypeFactory.getInstance().prepareStatement(query);
            
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
        MessageType messageType;
        
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

            var ps = MessageTypeFactory.getInstance().prepareStatement(query);
            
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
        return getMessageTransferCaches().getMessageTypeTransferCache().getMessageTypeTransfer(userVisit, messageType);
    }
    
    public List<MessageTypeTransfer> getMessageTypeTransfers(UserVisit userVisit, EntityType entityType) {
        var messageTypes = getMessageTypes(entityType);
        List<MessageTypeTransfer> messageTypeTransfers = new ArrayList<>(messageTypes.size());
        var messageTypeTransferCache = getMessageTransferCaches().getMessageTypeTransferCache();
        
        messageTypes.forEach((messageType) ->
                messageTypeTransfers.add(messageTypeTransferCache.getMessageTypeTransfer(userVisit, messageType))
        );
        
        return messageTypeTransfers;
    }
    
    public void updateMessageTypeFromValue(MessageTypeDetailValue messageTypeDetailValue, BasePK updatedBy) {
        if(messageTypeDetailValue.hasBeenModified()) {
            var messageType = MessageTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     messageTypeDetailValue.getMessageTypePK());
            var messageTypeDetail = messageType.getActiveDetailForUpdate();
            
            messageTypeDetail.setThruTime(session.START_TIME_LONG);
            messageTypeDetail.store();

            var messageTypePK = messageTypeDetail.getMessageTypePK(); // Not updated
            var entityTypePK = messageTypeDetail.getEntityTypePK(); // Not updated
            var messageTypeName = messageTypeDetailValue.getMessageTypeName();
            var mimeTypeUsageTypePK = messageTypeDetail.getMimeTypeUsageTypePK(); // Not updated
            var sortOrder = messageTypeDetailValue.getSortOrder();
            
            messageTypeDetail = MessageTypeDetailFactory.getInstance().create(messageTypePK, entityTypePK, messageTypeName,
                    mimeTypeUsageTypePK, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            messageType.setActiveDetail(messageTypeDetail);
            messageType.setLastDetail(messageTypeDetail);
            
            sendEvent(messageTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deleteMessageType(MessageType messageType, BasePK deletedBy) {
        deleteMessageTypeDescriptionsByMessageType(messageType, deletedBy);

        var messageTypeDetail = messageType.getLastDetailForUpdate();
        messageTypeDetail.setThruTime(session.START_TIME_LONG);
        messageType.setActiveDetail(null);
        messageType.store();
        
        sendEvent(messageType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteMessageTypes(List<MessageType> messageTypes, BasePK deletedBy) {
        messageTypes.forEach((messageType) -> 
                deleteMessageType(messageType, deletedBy)
        );
    }
    
    public void deleteMessageTypesByEntityType(EntityType entityType, BasePK deletedBy) {
        deleteMessageTypes(getMessageTypesForUpdate(entityType), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Message Type Descriptions
    // --------------------------------------------------------------------------------
    
    public MessageTypeDescription createMessageTypeDescription(MessageType messageType, Language language, String description,
            BasePK createdBy) {
        var messageTypeDescription = MessageTypeDescriptionFactory.getInstance().create(messageType,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(messageType.getPrimaryKey(), EventTypes.MODIFY, messageTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return messageTypeDescription;
    }
    
    private MessageTypeDescription getMessageTypeDescription(MessageType messageType, Language language, EntityPermission entityPermission) {
        MessageTypeDescription messageTypeDescription;
        
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

            var ps = MessageTypeDescriptionFactory.getInstance().prepareStatement(query);
            
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
        List<MessageTypeDescription> messageTypeDescriptions;
        
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

            var ps = MessageTypeDescriptionFactory.getInstance().prepareStatement(query);
            
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
        var messageTypeDescription = getMessageTypeDescription(messageType, language);
        
        if(messageTypeDescription == null && !language.getIsDefault()) {
            messageTypeDescription = getMessageTypeDescription(messageType, partyControl.getDefaultLanguage());
        }
        
        if(messageTypeDescription == null) {
            description = messageType.getLastDetail().getMessageTypeName();
        } else {
            description = messageTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public MessageTypeDescriptionTransfer getMessageTypeDescriptionTransfer(UserVisit userVisit, MessageTypeDescription messageTypeDescription) {
        return getMessageTransferCaches().getMessageTypeDescriptionTransferCache().getMessageTypeDescriptionTransfer(userVisit, messageTypeDescription);
    }
    
    public List<MessageTypeDescriptionTransfer> getMessageTypeDescriptionTransfers(UserVisit userVisit, MessageType messageType) {
        var messageTypeDescriptions = getMessageTypeDescriptionsByMessageType(messageType);
        List<MessageTypeDescriptionTransfer> messageTypeDescriptionTransfers = new ArrayList<>(messageTypeDescriptions.size());
        var messageTypeDescriptionTransferCache = getMessageTransferCaches().getMessageTypeDescriptionTransferCache();
        
        messageTypeDescriptions.forEach((messageTypeDescription) ->
                messageTypeDescriptionTransfers.add(messageTypeDescriptionTransferCache.getMessageTypeDescriptionTransfer(userVisit, messageTypeDescription))
        );
        
        return messageTypeDescriptionTransfers;
    }
    
    public void updateMessageTypeDescriptionFromValue(MessageTypeDescriptionValue messageTypeDescriptionValue, BasePK updatedBy) {
        if(messageTypeDescriptionValue.hasBeenModified()) {
            var messageTypeDescription = MessageTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     messageTypeDescriptionValue.getPrimaryKey());
            
            messageTypeDescription.setThruTime(session.START_TIME_LONG);
            messageTypeDescription.store();

            var messageType = messageTypeDescription.getMessageType();
            var language = messageTypeDescription.getLanguage();
            var description = messageTypeDescriptionValue.getDescription();
            
            messageTypeDescription = MessageTypeDescriptionFactory.getInstance().create(messageType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(messageType.getPrimaryKey(), EventTypes.MODIFY, messageTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteMessageTypeDescription(MessageTypeDescription messageTypeDescription, BasePK deletedBy) {
        messageTypeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(messageTypeDescription.getMessageTypePK(), EventTypes.MODIFY, messageTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteMessageTypeDescriptionsByMessageType(MessageType messageType, BasePK deletedBy) {
        var messageTypeDescriptions = getMessageTypeDescriptionsByMessageTypeForUpdate(messageType);
        
        messageTypeDescriptions.forEach((messageTypeDescription) -> 
                deleteMessageTypeDescription(messageTypeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Message Types
    // --------------------------------------------------------------------------------
    
    public Message createMessage(MessageType messageType, String messageName, Boolean includeByDefault, Boolean isDefault,
            Integer sortOrder, BasePK createdBy) {
        var defaultMessage = getDefaultMessage(messageType);
        var defaultFound = defaultMessage != null;
        
        if(defaultFound && isDefault) {
            var defaultMessageDetailValue = getDefaultMessageDetailValueForUpdate(messageType);
            
            defaultMessageDetailValue.setIsDefault(false);
            updateMessageFromValue(defaultMessageDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var message = MessageFactory.getInstance().create();
        var messageDetail = MessageDetailFactory.getInstance().create(message, messageType, messageName,
                includeByDefault, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        message = MessageFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                message.getPrimaryKey());
        message.setActiveDetail(messageDetail);
        message.setLastDetail(messageDetail);
        message.store();
        
        sendEvent(message.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return message;
    }
    
    private List<Message> getMessagesByMessageType(MessageType messageType, EntityPermission entityPermission) {
        List<Message> messages;
        
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

            var ps = MessageFactory.getInstance().prepareStatement(query);
            
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
        Message message;
        
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

            var ps = MessageFactory.getInstance().prepareStatement(query);
            
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
        Message message;
        
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

            var ps = MessageFactory.getInstance().prepareStatement(query);
            
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
        var messages = getMessagesByMessageType(messageType);
        var size = messages.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultMessageChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var message : messages) {
            var messageDetail = message.getLastDetail();
            var label = getBestMessageDescription(message, language);
            var value = messageDetail.getMessageName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultMessageChoice != null && defaultMessageChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && messageDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new MessageChoicesBean(labels, values, defaultValue);
    }
    
    public MessageTransfer getMessageTransfer(UserVisit userVisit, Message message) {
        return getMessageTransferCaches().getMessageTransferCache().getMessageTransfer(userVisit, message);
    }
    
    public List<MessageTransfer> getMessageTransfers(UserVisit userVisit, MessageType messageType) {
        var messages = getMessagesByMessageType(messageType);
        List<MessageTransfer> messageTransfers = new ArrayList<>(messages.size());
        var messageTransferCache = getMessageTransferCaches().getMessageTransferCache();
        
        messages.forEach((message) ->
                messageTransfers.add(messageTransferCache.getMessageTransfer(userVisit, message))
        );
        
        return messageTransfers;
    }
    
    private void updateMessageFromValue(MessageDetailValue messageDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(messageDetailValue.hasBeenModified()) {
            var message = MessageFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    messageDetailValue.getMessagePK());
            var messageDetail = message.getActiveDetailForUpdate();
            
            messageDetail.setThruTime(session.START_TIME_LONG);
            messageDetail.store();

            var messagePK = messageDetail.getMessagePK();
            var messageType = messageDetail.getMessageType();
            var messageTypePK = messageType.getPrimaryKey();
            var messageName = messageDetailValue.getMessageName();
            var includeByDefault = messageDetailValue.getIncludeByDefault();
            var isDefault = messageDetailValue.getIsDefault();
            var sortOrder = messageDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultMessage = getDefaultMessage(messageType);
                var defaultFound = defaultMessage != null && !defaultMessage.equals(message);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultMessageDetailValue = getDefaultMessageDetailValueForUpdate(messageType);
                    
                    defaultMessageDetailValue.setIsDefault(false);
                    updateMessageFromValue(defaultMessageDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            messageDetail = MessageDetailFactory.getInstance().create(messagePK, messageTypePK, messageName,
                    includeByDefault, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            message.setActiveDetail(messageDetail);
            message.setLastDetail(messageDetail);
            
            sendEvent(messagePK, EventTypes.MODIFY, null, null, updatedBy);
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

        var messageDetail = message.getLastDetailForUpdate();
        messageDetail.setThruTime(session.START_TIME_LONG);
        message.setActiveDetail(null);
        message.store();
        
        // Check for default, and pick one if necessary
        var messageType = messageDetail.getMessageType();
        var defaultMessage = getDefaultMessage(messageType);
        if(defaultMessage == null) {
            var messages = getMessagesByMessageTypeForUpdate(messageType);
            
            if(!messages.isEmpty()) {
                var iter = messages.iterator();
                if(iter.hasNext()) {
                    defaultMessage = iter.next();
                }
                var messageDetailValue = Objects.requireNonNull(defaultMessage).getLastDetailForUpdate().getMessageDetailValue().clone();
                
                messageDetailValue.setIsDefault(true);
                updateMessageFromValue(messageDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(message.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteMessages(List<Message> messages, BasePK deletedBy) {
        messages.forEach((message) -> 
                deleteMessage(message, deletedBy)
        );
    }
    
    public void deleteMessagesByMessageType(MessageType messageType, BasePK deletedBy) {
        deleteMessages(getMessagesByMessageTypeForUpdate(messageType), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Message Descriptions
    // --------------------------------------------------------------------------------
    
    public MessageDescription createMessageDescription(Message message, Language language, String description, BasePK createdBy) {
        var messageDescription = MessageDescriptionFactory.getInstance().create(message,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(message.getPrimaryKey(), EventTypes.MODIFY, messageDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return messageDescription;
    }
    
    private MessageDescription getMessageDescription(Message message, Language language, EntityPermission entityPermission) {
        MessageDescription messageDescription;
        
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

            var ps = MessageDescriptionFactory.getInstance().prepareStatement(query);
            
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
        List<MessageDescription> messageDescriptions;
        
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

            var ps = MessageDescriptionFactory.getInstance().prepareStatement(query);
            
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
        var messageDescription = getMessageDescription(message, language);
        
        if(messageDescription == null && !language.getIsDefault()) {
            messageDescription = getMessageDescription(message, partyControl.getDefaultLanguage());
        }
        
        if(messageDescription == null) {
            description = message.getLastDetail().getMessageName();
        } else {
            description = messageDescription.getDescription();
        }
        
        return description;
    }
    
    public MessageDescriptionTransfer getMessageDescriptionTransfer(UserVisit userVisit, MessageDescription messageDescription) {
        return getMessageTransferCaches().getMessageDescriptionTransferCache().getMessageDescriptionTransfer(userVisit, messageDescription);
    }
    
    public List<MessageDescriptionTransfer> getMessageDescriptionTransfers(UserVisit userVisit, Message message) {
        var messageDescriptions = getMessageDescriptionsByMessage(message);
        List<MessageDescriptionTransfer> messageDescriptionTransfers = new ArrayList<>(messageDescriptions.size());
        var messageDescriptionTransferCache = getMessageTransferCaches().getMessageDescriptionTransferCache();
        
        messageDescriptions.forEach((messageDescription) ->
                messageDescriptionTransfers.add(messageDescriptionTransferCache.getMessageDescriptionTransfer(userVisit, messageDescription))
        );
        
        return messageDescriptionTransfers;
    }
    
    public void updateMessageDescriptionFromValue(MessageDescriptionValue messageDescriptionValue, BasePK updatedBy) {
        if(messageDescriptionValue.hasBeenModified()) {
            var messageDescription = MessageDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, messageDescriptionValue.getPrimaryKey());
            
            messageDescription.setThruTime(session.START_TIME_LONG);
            messageDescription.store();

            var message = messageDescription.getMessage();
            var language = messageDescription.getLanguage();
            var description = messageDescriptionValue.getDescription();
            
            messageDescription = MessageDescriptionFactory.getInstance().create(message, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(message.getPrimaryKey(), EventTypes.MODIFY, messageDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteMessageDescription(MessageDescription messageDescription, BasePK deletedBy) {
        messageDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(messageDescription.getMessagePK(), EventTypes.MODIFY, messageDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteMessageDescriptionsByMessage(Message message, BasePK deletedBy) {
        var messageDescriptions = getMessageDescriptionsByMessageForUpdate(message);
        
        messageDescriptions.forEach((messageDescription) -> 
                deleteMessageDescription(messageDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Message Strings
    // --------------------------------------------------------------------------------
    
    public MessageString createMessageString(Message message, Language language, String string, BasePK createdBy) {
        var messageString = MessageStringFactory.getInstance().create(message, language, string,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(messageString.getMessagePK(), EventTypes.MODIFY, messageString.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return messageString;
    }
    
    private List<MessageString> getMessageStringsByMessage(Message message, EntityPermission entityPermission) {
        List<MessageString> messageStrings;
        
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

            var ps = MessageStringFactory.getInstance().prepareStatement(query);
            
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
        MessageString messageString;
        
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

            var ps = MessageStringFactory.getInstance().prepareStatement(query);
            
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
        var messageString = getMessageStringForUpdate(message, language);
        
        return messageString == null? null: getMessageStringValue(messageString);
    }
    
    public List<MessageStringTransfer> getMessageStringTransfers(UserVisit userVisit, Collection<MessageString> messageStrings) {
        List<MessageStringTransfer> messageStringTransfers = new ArrayList<>(messageStrings.size());
        var messageStringTransferCache = getMessageTransferCaches().getMessageStringTransferCache();
        
        messageStrings.forEach((messageString) ->
                messageStringTransfers.add(messageStringTransferCache.getMessageStringTransfer(userVisit, messageString))
        );
        
        return messageStringTransfers;
    }
    
    public List<MessageStringTransfer> getMessageStringTransfersByMessage(UserVisit userVisit, Message message) {
        return getMessageStringTransfers(userVisit, getMessageStringsByMessage(message));
    }
    
    public void updateMessageStringFromValue(MessageStringValue messageStringValue, BasePK updatedBy) {
        if(messageStringValue.hasBeenModified()) {
            var messageString = MessageStringFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    messageStringValue.getPrimaryKey());
            
            messageString.setThruTime(session.START_TIME_LONG);
            messageString.store();

            var messagePK = messageString.getMessagePK(); // Not updated
            var languagePK = messageString.getLanguagePK(); // Not updated
            var string = messageStringValue.getString();
            
            messageString = MessageStringFactory.getInstance().create(messagePK, languagePK, string,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(messagePK, EventTypes.MODIFY, messageString.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteMessageString(MessageString messageString, BasePK deletedBy) {
        messageString.setThruTime(session.START_TIME_LONG);
        
        sendEvent(messageString.getMessagePK(), EventTypes.MODIFY, messageString.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteMessageStrings(List<MessageString> messageStrings, BasePK deletedBy) {
        messageStrings.forEach((messageString) -> 
                deleteMessageString(messageString, deletedBy)
        );
    }
    
    public void deleteMessageStringsByMessage(Message message, BasePK deletedBy) {
        deleteMessageStrings(getMessageStringsByMessageForUpdate(message), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Message Blobs
    // --------------------------------------------------------------------------------
    
    public MessageBlob createMessageBlob(Message message, Language language, MimeType mimeType, ByteArray blob, BasePK createdBy) {
        var messageBlob = MessageBlobFactory.getInstance().create(message, language, mimeType, blob,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(messageBlob.getMessagePK(), EventTypes.MODIFY, messageBlob.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return messageBlob;
    }
    
    private List<MessageBlob> getMessageBlobsByMessage(Message message, EntityPermission entityPermission) {
        List<MessageBlob> messageBlobs;
        
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

            var ps = MessageBlobFactory.getInstance().prepareStatement(query);
            
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
        MessageBlob messageBlob;
        
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

            var ps = MessageBlobFactory.getInstance().prepareStatement(query);
            
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
        var messageBlob = getMessageBlobForUpdate(message, language);
        
        return messageBlob == null? null: getMessageBlobValue(messageBlob);
    }
    
    public List<MessageBlobTransfer> getMessageBlobTransfers(UserVisit userVisit, Collection<MessageBlob> messageBlobs) {
        List<MessageBlobTransfer> messageBlobTransfers = new ArrayList<>(messageBlobs.size());
        var messageBlobTransferCache = getMessageTransferCaches().getMessageBlobTransferCache();
        
        messageBlobs.forEach((messageBlob) ->
                messageBlobTransfers.add(messageBlobTransferCache.getMessageBlobTransfer(userVisit, messageBlob))
        );
        
        return messageBlobTransfers;
    }
    
    public List<MessageBlobTransfer> getMessageBlobTransfersByMessage(UserVisit userVisit, Message message) {
        return getMessageBlobTransfers(userVisit, getMessageBlobsByMessage(message));
    }
    
    public void updateMessageBlobFromValue(MessageBlobValue messageBlobValue, BasePK updatedBy) {
        if(messageBlobValue.hasBeenModified()) {
            var messageBlob = MessageBlobFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    messageBlobValue.getPrimaryKey());
            
            messageBlob.setThruTime(session.START_TIME_LONG);
            messageBlob.store();

            var messagePK = messageBlob.getMessagePK(); // Not updated
            var languagePK = messageBlob.getLanguagePK(); // Not updated
            var mimeTypePK = messageBlobValue.getMimeTypePK();
            var blob = messageBlobValue.getBlob();
            
            messageBlob = MessageBlobFactory.getInstance().create(messagePK, languagePK, mimeTypePK, blob,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(messagePK, EventTypes.MODIFY, messageBlob.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteMessageBlob(MessageBlob messageBlob, BasePK deletedBy) {
        messageBlob.setThruTime(session.START_TIME_LONG);
        
        sendEvent(messageBlob.getMessagePK(), EventTypes.MODIFY, messageBlob.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteMessageBlobs(List<MessageBlob> messageBlobs, BasePK deletedBy) {
        messageBlobs.forEach((messageBlob) -> 
                deleteMessageBlob(messageBlob, deletedBy)
        );
    }
    
    public void deleteMessageBlobsByMessage(Message message, BasePK deletedBy) {
        deleteMessageBlobs(getMessageBlobsByMessageForUpdate(message), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Message Clobs
    // --------------------------------------------------------------------------------
    
    public MessageClob createMessageClob(Message message, Language language, MimeType mimeType, String clob, BasePK createdBy) {
        var messageClob = MessageClobFactory.getInstance().create(message, language, mimeType, clob,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(messageClob.getMessagePK(), EventTypes.MODIFY, messageClob.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return messageClob;
    }
    
    private List<MessageClob> getMessageClobsByMessage(Message message, EntityPermission entityPermission) {
        List<MessageClob> messageClobs;
        
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

            var ps = MessageClobFactory.getInstance().prepareStatement(query);
            
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
        MessageClob messageClob;
        
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

            var ps = MessageClobFactory.getInstance().prepareStatement(query);
            
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
        var messageClob = getMessageClobForUpdate(message, language);
        
        return messageClob == null? null: getMessageClobValue(messageClob);
    }
    
    public List<MessageClobTransfer> getMessageClobTransfers(UserVisit userVisit, Collection<MessageClob> messageClobs) {
        List<MessageClobTransfer> messageClobTransfers = new ArrayList<>(messageClobs.size());
        var messageClobTransferCache = getMessageTransferCaches().getMessageClobTransferCache();
        
        messageClobs.forEach((messageClob) ->
                messageClobTransfers.add(messageClobTransferCache.getMessageClobTransfer(userVisit, messageClob))
        );
        
        return messageClobTransfers;
    }
    
    public List<MessageClobTransfer> getMessageClobTransfersByMessage(UserVisit userVisit, Message message) {
        return getMessageClobTransfers(userVisit, getMessageClobsByMessage(message));
    }
    
    public void updateMessageClobFromValue(MessageClobValue messageClobValue, BasePK updatedBy) {
        if(messageClobValue.hasBeenModified()) {
            var messageClob = MessageClobFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    messageClobValue.getPrimaryKey());
            
            messageClob.setThruTime(session.START_TIME_LONG);
            messageClob.store();

            var messagePK = messageClob.getMessagePK(); // Not updated
            var languagePK = messageClob.getLanguagePK(); // Not updated
            var mimeTypePK = messageClobValue.getMimeTypePK();
            var clob = messageClobValue.getClob();
            
            messageClob = MessageClobFactory.getInstance().create(messagePK, languagePK, mimeTypePK, clob,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(messagePK, EventTypes.MODIFY, messageClob.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteMessageClob(MessageClob messageClob, BasePK deletedBy) {
        messageClob.setThruTime(session.START_TIME_LONG);
        
        sendEvent(messageClob.getMessagePK(), EventTypes.MODIFY, messageClob.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteMessageClobs(List<MessageClob> messageClobs, BasePK deletedBy) {
        messageClobs.forEach((messageClob) -> 
                deleteMessageClob(messageClob, deletedBy)
        );
    }
    
    public void deleteMessageClobsByMessage(Message message, BasePK deletedBy) {
        deleteMessageClobs(getMessageClobsByMessageForUpdate(message), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Message Descriptions
    // --------------------------------------------------------------------------------
    
    public EntityMessage createEntityMessage(EntityInstance entityInstance, Message message, BasePK createdBy) {
        var entityMessage = EntityMessageFactory.getInstance().create(entityInstance, message,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(message.getPrimaryKey(), EventTypes.MODIFY, entityMessage.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return entityMessage;
    }
    
    private EntityMessage getEntityMessage(EntityInstance entityInstance, Message message, EntityPermission entityPermission) {
        EntityMessage entityMessage;
        
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

            var ps = EntityMessageFactory.getInstance().prepareStatement(query);
            
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
        List<EntityMessage> entityMessages;
        
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

            var ps = EntityMessageFactory.getInstance().prepareStatement(query);
            
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
        List<EntityMessage> entityMessages;
        
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

            var ps = EntityMessageFactory.getInstance().prepareStatement(query);
            
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
        return getMessageTransferCaches().getEntityMessageTransferCache().getEntityMessageTransfer(userVisit, entityMessage);
    }
    
    public List<EntityMessageTransfer> getEntityMessageTransfers(UserVisit userVisit, Collection<EntityMessage> entityMessages) {
        List<EntityMessageTransfer> entityMessageTransfers = new ArrayList<>(entityMessages.size());
        var entityMessageTransferCache = getMessageTransferCaches().getEntityMessageTransferCache();
        
        entityMessages.forEach((entityMessage) ->
                entityMessageTransfers.add(entityMessageTransferCache.getEntityMessageTransfer(userVisit, entityMessage))
        );
        
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
        
        sendEvent(entityMessage.getMessagePK(), EventTypes.MODIFY, entityMessage.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteEntityMessages(List<EntityMessage> entityMessages, BasePK deletedBy) {
        entityMessages.forEach((entityMessage) -> 
                deleteEntityMessage(entityMessage, deletedBy)
        );
    }
    
    public void deleteEntityMessagesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteEntityMessages(getEntityMessagesByEntityInstanceForUpdate(entityInstance), deletedBy);
    }
    
    public void deleteEntityMessagesByMessage(Message message, BasePK deletedBy) {
        deleteEntityMessages(getEntityMessagesByMessageForUpdate(message), deletedBy);
    }
    
}
