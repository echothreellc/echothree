// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.model.control.communication.server.control;

import com.echothree.model.control.communication.common.choice.CommunicationEventPurposeChoicesBean;
import com.echothree.model.control.communication.common.transfer.CommunicationEmailSourceTransfer;
import com.echothree.model.control.communication.common.transfer.CommunicationEventPurposeDescriptionTransfer;
import com.echothree.model.control.communication.common.transfer.CommunicationEventPurposeTransfer;
import com.echothree.model.control.communication.common.transfer.CommunicationEventRoleTransfer;
import com.echothree.model.control.communication.common.transfer.CommunicationEventRoleTypeTransfer;
import com.echothree.model.control.communication.common.transfer.CommunicationEventTransfer;
import com.echothree.model.control.communication.common.transfer.CommunicationEventTypeTransfer;
import com.echothree.model.control.communication.common.transfer.CommunicationSourceDescriptionTransfer;
import com.echothree.model.control.communication.common.transfer.CommunicationSourceTransfer;
import com.echothree.model.control.communication.common.transfer.CommunicationSourceTypeTransfer;
import com.echothree.model.control.communication.server.transfer.CommunicationEventPurposeDescriptionTransferCache;
import com.echothree.model.control.communication.server.transfer.CommunicationEventPurposeTransferCache;
import com.echothree.model.control.communication.server.transfer.CommunicationEventTransferCache;
import com.echothree.model.control.communication.server.transfer.CommunicationSourceDescriptionTransferCache;
import com.echothree.model.control.communication.server.transfer.CommunicationSourceTransferCache;
import com.echothree.model.control.communication.server.transfer.CommunicationTransferCaches;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.data.communication.common.pk.CommunicationEventPK;
import com.echothree.model.data.communication.common.pk.CommunicationEventPurposePK;
import com.echothree.model.data.communication.common.pk.CommunicationSourcePK;
import com.echothree.model.data.communication.common.pk.CommunicationSourceTypePK;
import com.echothree.model.data.communication.server.entity.CommunicationEmailSource;
import com.echothree.model.data.communication.server.entity.CommunicationEvent;
import com.echothree.model.data.communication.server.entity.CommunicationEventDetail;
import com.echothree.model.data.communication.server.entity.CommunicationEventPurpose;
import com.echothree.model.data.communication.server.entity.CommunicationEventPurposeDescription;
import com.echothree.model.data.communication.server.entity.CommunicationEventPurposeDetail;
import com.echothree.model.data.communication.server.entity.CommunicationEventRole;
import com.echothree.model.data.communication.server.entity.CommunicationEventRoleType;
import com.echothree.model.data.communication.server.entity.CommunicationEventRoleTypeDescription;
import com.echothree.model.data.communication.server.entity.CommunicationEventType;
import com.echothree.model.data.communication.server.entity.CommunicationEventTypeDescription;
import com.echothree.model.data.communication.server.entity.CommunicationSource;
import com.echothree.model.data.communication.server.entity.CommunicationSourceDescription;
import com.echothree.model.data.communication.server.entity.CommunicationSourceDetail;
import com.echothree.model.data.communication.server.entity.CommunicationSourceType;
import com.echothree.model.data.communication.server.entity.CommunicationSourceTypeDescription;
import com.echothree.model.data.communication.server.factory.CommunicationEmailSourceFactory;
import com.echothree.model.data.communication.server.factory.CommunicationEventDetailFactory;
import com.echothree.model.data.communication.server.factory.CommunicationEventFactory;
import com.echothree.model.data.communication.server.factory.CommunicationEventPurposeDescriptionFactory;
import com.echothree.model.data.communication.server.factory.CommunicationEventPurposeDetailFactory;
import com.echothree.model.data.communication.server.factory.CommunicationEventPurposeFactory;
import com.echothree.model.data.communication.server.factory.CommunicationEventRoleFactory;
import com.echothree.model.data.communication.server.factory.CommunicationEventRoleTypeDescriptionFactory;
import com.echothree.model.data.communication.server.factory.CommunicationEventRoleTypeFactory;
import com.echothree.model.data.communication.server.factory.CommunicationEventTypeDescriptionFactory;
import com.echothree.model.data.communication.server.factory.CommunicationEventTypeFactory;
import com.echothree.model.data.communication.server.factory.CommunicationSourceDescriptionFactory;
import com.echothree.model.data.communication.server.factory.CommunicationSourceDetailFactory;
import com.echothree.model.data.communication.server.factory.CommunicationSourceFactory;
import com.echothree.model.data.communication.server.factory.CommunicationSourceTypeDescriptionFactory;
import com.echothree.model.data.communication.server.factory.CommunicationSourceTypeFactory;
import com.echothree.model.data.communication.server.value.CommunicationEmailSourceValue;
import com.echothree.model.data.communication.server.value.CommunicationEventDetailValue;
import com.echothree.model.data.communication.server.value.CommunicationEventPurposeDescriptionValue;
import com.echothree.model.data.communication.server.value.CommunicationEventPurposeDetailValue;
import com.echothree.model.data.communication.server.value.CommunicationSourceDescriptionValue;
import com.echothree.model.data.communication.server.value.CommunicationSourceDetailValue;
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.model.data.core.common.pk.ServerPK;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.Server;
import com.echothree.model.data.document.server.entity.Document;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.selector.common.pk.SelectorPK;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workeffort.common.pk.WorkEffortScopePK;
import com.echothree.model.data.workeffort.server.entity.WorkEffortScope;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EncryptionUtils;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CommunicationControl
        extends BaseModelControl {
    
    /** Creates a new instance of CommunicationControl */
    public CommunicationControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Communication Transfer Caches
    // --------------------------------------------------------------------------------
    
    private CommunicationTransferCaches communicationTransferCaches;
    
    public CommunicationTransferCaches getCommunicationTransferCaches(UserVisit userVisit) {
        if(communicationTransferCaches == null) {
            communicationTransferCaches = new CommunicationTransferCaches(userVisit, this);
        }
        
        return communicationTransferCaches;
    }
    
    // --------------------------------------------------------------------------------
    //   Communication Event Purposes
    // --------------------------------------------------------------------------------
    
    public CommunicationEventPurpose createCommunicationEventPurpose(String communicationEventPurposeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        CommunicationEventPurpose defaultCommunicationEventPurpose = getDefaultCommunicationEventPurpose();
        boolean defaultFound = defaultCommunicationEventPurpose != null;
        
        if(defaultFound && isDefault) {
            CommunicationEventPurposeDetailValue defaultCommunicationEventPurposeDetailValue = getDefaultCommunicationEventPurposeDetailValueForUpdate();
            
            defaultCommunicationEventPurposeDetailValue.setIsDefault(Boolean.FALSE);
            updateCommunicationEventPurposeFromValue(defaultCommunicationEventPurposeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        CommunicationEventPurpose communicationEventPurpose = CommunicationEventPurposeFactory.getInstance().create();
        CommunicationEventPurposeDetail communicationEventPurposeDetail = CommunicationEventPurposeDetailFactory.getInstance().create(session,
                communicationEventPurpose, communicationEventPurposeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        communicationEventPurpose = CommunicationEventPurposeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, communicationEventPurpose.getPrimaryKey());
        communicationEventPurpose.setActiveDetail(communicationEventPurposeDetail);
        communicationEventPurpose.setLastDetail(communicationEventPurposeDetail);
        communicationEventPurpose.store();
        
        sendEventUsingNames(communicationEventPurpose.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return communicationEventPurpose;
    }
    
    private CommunicationEventPurpose getCommunicationEventPurposeByName(String communicationEventPurposeName, EntityPermission entityPermission) {
        CommunicationEventPurpose communicationEventPurpose;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM communicationeventpurposes, communicationeventpurposedetails " +
                        "WHERE cmmnevpr_activedetailid = cmmnevprdt_communicationeventpurposedetailid AND cmmnevprdt_communicationeventpurposename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM communicationeventpurposes, communicationeventpurposedetails " +
                        "WHERE cmmnevpr_activedetailid = cmmnevprdt_communicationeventpurposedetailid AND cmmnevprdt_communicationeventpurposename = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = CommunicationEventPurposeFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, communicationEventPurposeName);
            
            communicationEventPurpose = CommunicationEventPurposeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return communicationEventPurpose;
    }
    
    public CommunicationEventPurpose getCommunicationEventPurposeByName(String communicationEventPurposeName) {
        return getCommunicationEventPurposeByName(communicationEventPurposeName, EntityPermission.READ_ONLY);
    }
    
    public CommunicationEventPurpose getCommunicationEventPurposeByNameForUpdate(String communicationEventPurposeName) {
        return getCommunicationEventPurposeByName(communicationEventPurposeName, EntityPermission.READ_WRITE);
    }
    
    public CommunicationEventPurposeDetailValue getCommunicationEventPurposeDetailValueForUpdate(CommunicationEventPurpose communicationEventPurpose) {
        return communicationEventPurpose == null? null: communicationEventPurpose.getLastDetailForUpdate().getCommunicationEventPurposeDetailValue().clone();
    }
    
    public CommunicationEventPurposeDetailValue getCommunicationEventPurposeDetailValueByNameForUpdate(String communicationEventPurposeName) {
        return getCommunicationEventPurposeDetailValueForUpdate(getCommunicationEventPurposeByNameForUpdate(communicationEventPurposeName));
    }
    
    private CommunicationEventPurpose getDefaultCommunicationEventPurpose(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM communicationeventpurposes, communicationeventpurposedetails " +
                    "WHERE cmmnevpr_activedetailid = cmmnevprdt_communicationeventpurposedetailid AND cmmnevprdt_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM communicationeventpurposes, communicationeventpurposedetails " +
                    "WHERE cmmnevpr_activedetailid = cmmnevprdt_communicationeventpurposedetailid AND cmmnevprdt_isdefault = 1 " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = CommunicationEventPurposeFactory.getInstance().prepareStatement(query);
        
        return CommunicationEventPurposeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
    }
    
    public CommunicationEventPurpose getDefaultCommunicationEventPurpose() {
        return getDefaultCommunicationEventPurpose(EntityPermission.READ_ONLY);
    }
    
    public CommunicationEventPurpose getDefaultCommunicationEventPurposeForUpdate() {
        return getDefaultCommunicationEventPurpose(EntityPermission.READ_WRITE);
    }
    
    public CommunicationEventPurposeDetailValue getDefaultCommunicationEventPurposeDetailValueForUpdate() {
        return getDefaultCommunicationEventPurposeForUpdate().getLastDetailForUpdate().getCommunicationEventPurposeDetailValue().clone();
    }
    
    private List<CommunicationEventPurpose> getCommunicationEventPurposes(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM communicationeventpurposes, communicationeventpurposedetails " +
                    "WHERE cmmnevpr_activedetailid = cmmnevprdt_communicationeventpurposedetailid " +
                    "ORDER BY cmmnevprdt_sortorder, cmmnevprdt_communicationeventpurposename";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM communicationeventpurposes, communicationeventpurposedetails " +
                    "WHERE cmmnevpr_activedetailid = cmmnevprdt_communicationeventpurposedetailid " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = CommunicationEventPurposeFactory.getInstance().prepareStatement(query);
        
        return CommunicationEventPurposeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<CommunicationEventPurpose> getCommunicationEventPurposes() {
        return getCommunicationEventPurposes(EntityPermission.READ_ONLY);
    }
    
    public List<CommunicationEventPurpose> getCommunicationEventPurposesForUpdate() {
        return getCommunicationEventPurposes(EntityPermission.READ_WRITE);
    }
    
    public CommunicationEventPurposeTransfer getCommunicationEventPurposeTransfer(UserVisit userVisit, CommunicationEventPurpose communicationEventPurpose) {
        return getCommunicationTransferCaches(userVisit).getCommunicationEventPurposeTransferCache().getCommunicationEventPurposeTransfer(communicationEventPurpose);
    }
    
    public List<CommunicationEventPurposeTransfer> getCommunicationEventPurposeTransfers(UserVisit userVisit) {
        List<CommunicationEventPurpose> communicationEventPurposes = getCommunicationEventPurposes();
        List<CommunicationEventPurposeTransfer> communicationEventPurposeTransfers = new ArrayList<>(communicationEventPurposes.size());
        CommunicationEventPurposeTransferCache communicationEventPurposeTransferCache = getCommunicationTransferCaches(userVisit).getCommunicationEventPurposeTransferCache();
        
        communicationEventPurposes.forEach((communicationEventPurpose) ->
                communicationEventPurposeTransfers.add(communicationEventPurposeTransferCache.getCommunicationEventPurposeTransfer(communicationEventPurpose))
        );
        
        return communicationEventPurposeTransfers;
    }
    
    public CommunicationEventPurposeChoicesBean getCommunicationEventPurposeChoices(String defaultCommunicationEventPurposeChoice, Language language,
            boolean allowNullChoice) {
        List<CommunicationEventPurpose> communicationEventPurposes = getCommunicationEventPurposes();
        int size = communicationEventPurposes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultCommunicationEventPurposeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(CommunicationEventPurpose communicationEventPurpose: communicationEventPurposes) {
            CommunicationEventPurposeDetail communicationEventPurposeDetail = communicationEventPurpose.getLastDetail();
            
            String label = getBestCommunicationEventPurposeDescription(communicationEventPurpose, language);
            String value = communicationEventPurposeDetail.getCommunicationEventPurposeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultCommunicationEventPurposeChoice != null && defaultCommunicationEventPurposeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && communicationEventPurposeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new CommunicationEventPurposeChoicesBean(labels, values, defaultValue);
    }
    
    private void updateCommunicationEventPurposeFromValue(CommunicationEventPurposeDetailValue communicationEventPurposeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(communicationEventPurposeDetailValue.hasBeenModified()) {
            CommunicationEventPurpose communicationEventPurpose = CommunicationEventPurposeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     communicationEventPurposeDetailValue.getCommunicationEventPurposePK());
            CommunicationEventPurposeDetail communicationEventPurposeDetail = communicationEventPurpose.getActiveDetailForUpdate();
            
            communicationEventPurposeDetail.setThruTime(session.START_TIME_LONG);
            communicationEventPurposeDetail.store();
            
            CommunicationEventPurposePK communicationEventPurposePK = communicationEventPurposeDetail.getCommunicationEventPurposePK();
            String communicationEventPurposeName = communicationEventPurposeDetailValue.getCommunicationEventPurposeName();
            Boolean isDefault = communicationEventPurposeDetailValue.getIsDefault();
            Integer sortOrder = communicationEventPurposeDetailValue.getSortOrder();
            
            if(checkDefault) {
                CommunicationEventPurpose defaultCommunicationEventPurpose = getDefaultCommunicationEventPurpose();
                boolean defaultFound = defaultCommunicationEventPurpose != null && !defaultCommunicationEventPurpose.equals(communicationEventPurpose);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    CommunicationEventPurposeDetailValue defaultCommunicationEventPurposeDetailValue = getDefaultCommunicationEventPurposeDetailValueForUpdate();
                    
                    defaultCommunicationEventPurposeDetailValue.setIsDefault(Boolean.FALSE);
                    updateCommunicationEventPurposeFromValue(defaultCommunicationEventPurposeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            communicationEventPurposeDetail = CommunicationEventPurposeDetailFactory.getInstance().create(communicationEventPurposePK,
                    communicationEventPurposeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            communicationEventPurpose.setActiveDetail(communicationEventPurposeDetail);
            communicationEventPurpose.setLastDetail(communicationEventPurposeDetail);
            
            sendEventUsingNames(communicationEventPurposePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void updateCommunicationEventPurposeFromValue(CommunicationEventPurposeDetailValue communicationEventPurposeDetailValue, BasePK updatedBy) {
        updateCommunicationEventPurposeFromValue(communicationEventPurposeDetailValue, true, updatedBy);
    }
    
    public void deleteCommunicationEventPurpose(CommunicationEventPurpose communicationEventPurpose, BasePK deletedBy) {
        deleteCommunicationEventPurposeDescriptionsByCommunicationEventPurpose(communicationEventPurpose, deletedBy);
        
        CommunicationEventPurposeDetail communicationEventPurposeDetail = communicationEventPurpose.getLastDetailForUpdate();
        communicationEventPurposeDetail.setThruTime(session.START_TIME_LONG);
        communicationEventPurpose.setActiveDetail(null);
        communicationEventPurpose.store();
        
        // Check for default, and pick one if necessary
        CommunicationEventPurpose defaultCommunicationEventPurpose = getDefaultCommunicationEventPurpose();
        if(defaultCommunicationEventPurpose == null) {
            List<CommunicationEventPurpose> communicationEventPurposes = getCommunicationEventPurposesForUpdate();
            
            if(!communicationEventPurposes.isEmpty()) {
                Iterator iter = communicationEventPurposes.iterator();
                if(iter.hasNext()) {
                    defaultCommunicationEventPurpose = (CommunicationEventPurpose)iter.next();
                }
                CommunicationEventPurposeDetailValue communicationEventPurposeDetailValue = defaultCommunicationEventPurpose.getLastDetailForUpdate().getCommunicationEventPurposeDetailValue().clone();
                
                communicationEventPurposeDetailValue.setIsDefault(Boolean.TRUE);
                updateCommunicationEventPurposeFromValue(communicationEventPurposeDetailValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(communicationEventPurpose.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Communication Event Purpose Descriptions
    // --------------------------------------------------------------------------------
    
    public CommunicationEventPurposeDescription createCommunicationEventPurposeDescription(CommunicationEventPurpose communicationEventPurpose,
            Language language, String description, BasePK createdBy) {
        CommunicationEventPurposeDescription communicationEventPurposeDescription = CommunicationEventPurposeDescriptionFactory.getInstance().create(session,
                communicationEventPurpose, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(communicationEventPurpose.getPrimaryKey(), EventTypes.MODIFY.name(), communicationEventPurposeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return communicationEventPurposeDescription;
    }
    
    private CommunicationEventPurposeDescription getCommunicationEventPurposeDescription(CommunicationEventPurpose communicationEventPurpose,
            Language language, EntityPermission entityPermission) {
        CommunicationEventPurposeDescription communicationEventPurposeDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM communicationeventpurposedescriptions " +
                        "WHERE cmmnevprd_cmmnevpr_communicationeventpurposeid = ? AND cmmnevprd_lang_languageid = ? AND cmmnevprd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM communicationeventpurposedescriptions " +
                        "WHERE cmmnevprd_cmmnevpr_communicationeventpurposeid = ? AND cmmnevprd_lang_languageid = ? AND cmmnevprd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = CommunicationEventPurposeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, communicationEventPurpose.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            communicationEventPurposeDescription = CommunicationEventPurposeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return communicationEventPurposeDescription;
    }
    
    public CommunicationEventPurposeDescription getCommunicationEventPurposeDescription(CommunicationEventPurpose communicationEventPurpose, Language language) {
        return getCommunicationEventPurposeDescription(communicationEventPurpose, language, EntityPermission.READ_ONLY);
    }
    
    public CommunicationEventPurposeDescription getCommunicationEventPurposeDescriptionForUpdate(CommunicationEventPurpose communicationEventPurpose, Language language) {
        return getCommunicationEventPurposeDescription(communicationEventPurpose, language, EntityPermission.READ_WRITE);
    }
    
    public CommunicationEventPurposeDescriptionValue getCommunicationEventPurposeDescriptionValue(CommunicationEventPurposeDescription communicationEventPurposeDescription) {
        return communicationEventPurposeDescription == null? null: communicationEventPurposeDescription.getCommunicationEventPurposeDescriptionValue().clone();
    }
    
    public CommunicationEventPurposeDescriptionValue getCommunicationEventPurposeDescriptionValueForUpdate(CommunicationEventPurpose communicationEventPurpose, Language language) {
        return getCommunicationEventPurposeDescriptionValue(getCommunicationEventPurposeDescriptionForUpdate(communicationEventPurpose, language));
    }
    
    private List<CommunicationEventPurposeDescription> getCommunicationEventPurposeDescriptionsByCommunicationEventPurpose(CommunicationEventPurpose communicationEventPurpose, EntityPermission entityPermission) {
        List<CommunicationEventPurposeDescription> communicationEventPurposeDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM communicationeventpurposedescriptions, languages " +
                        "WHERE cmmnevprd_cmmnevpr_communicationeventpurposeid = ? AND cmmnevprd_thrutime = ? AND cmmnevprd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM communicationeventpurposedescriptions " +
                        "WHERE cmmnevprd_cmmnevpr_communicationeventpurposeid = ? AND cmmnevprd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = CommunicationEventPurposeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, communicationEventPurpose.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            communicationEventPurposeDescriptions = CommunicationEventPurposeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return communicationEventPurposeDescriptions;
    }
    
    public List<CommunicationEventPurposeDescription> getCommunicationEventPurposeDescriptionsByCommunicationEventPurpose(CommunicationEventPurpose communicationEventPurpose) {
        return getCommunicationEventPurposeDescriptionsByCommunicationEventPurpose(communicationEventPurpose, EntityPermission.READ_ONLY);
    }
    
    public List<CommunicationEventPurposeDescription> getCommunicationEventPurposeDescriptionsByCommunicationEventPurposeForUpdate(CommunicationEventPurpose communicationEventPurpose) {
        return getCommunicationEventPurposeDescriptionsByCommunicationEventPurpose(communicationEventPurpose, EntityPermission.READ_WRITE);
    }
    
    public String getBestCommunicationEventPurposeDescription(CommunicationEventPurpose communicationEventPurpose, Language language) {
        String description;
        CommunicationEventPurposeDescription communicationEventPurposeDescription = getCommunicationEventPurposeDescription(communicationEventPurpose, language);
        
        if(communicationEventPurposeDescription == null && !language.getIsDefault()) {
            communicationEventPurposeDescription = getCommunicationEventPurposeDescription(communicationEventPurpose, getPartyControl().getDefaultLanguage());
        }
        
        if(communicationEventPurposeDescription == null) {
            description = communicationEventPurpose.getLastDetail().getCommunicationEventPurposeName();
        } else {
            description = communicationEventPurposeDescription.getDescription();
        }
        
        return description;
    }
    
    public CommunicationEventPurposeDescriptionTransfer getCommunicationEventPurposeDescriptionTransfer(UserVisit userVisit, CommunicationEventPurposeDescription communicationEventPurposeDescription) {
        return getCommunicationTransferCaches(userVisit).getCommunicationEventPurposeDescriptionTransferCache().getCommunicationEventPurposeDescriptionTransfer(communicationEventPurposeDescription);
    }
    
    public List<CommunicationEventPurposeDescriptionTransfer> getCommunicationEventPurposeDescriptionTransfers(UserVisit userVisit, CommunicationEventPurpose communicationEventPurpose) {
        List<CommunicationEventPurposeDescription> communicationEventPurposeDescriptions = getCommunicationEventPurposeDescriptionsByCommunicationEventPurpose(communicationEventPurpose);
        List<CommunicationEventPurposeDescriptionTransfer> communicationEventPurposeDescriptionTransfers = new ArrayList<>(communicationEventPurposeDescriptions.size());
        CommunicationEventPurposeDescriptionTransferCache communicationEventPurposeDescriptionTransferCache = getCommunicationTransferCaches(userVisit).getCommunicationEventPurposeDescriptionTransferCache();
        
        communicationEventPurposeDescriptions.forEach((communicationEventPurposeDescription) ->
                communicationEventPurposeDescriptionTransfers.add(communicationEventPurposeDescriptionTransferCache.getCommunicationEventPurposeDescriptionTransfer(communicationEventPurposeDescription))
        );
        
        return communicationEventPurposeDescriptionTransfers;
    }
    
    public void updateCommunicationEventPurposeDescriptionFromValue(CommunicationEventPurposeDescriptionValue communicationEventPurposeDescriptionValue, BasePK updatedBy) {
        if(communicationEventPurposeDescriptionValue.hasBeenModified()) {
            CommunicationEventPurposeDescription communicationEventPurposeDescription = CommunicationEventPurposeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, communicationEventPurposeDescriptionValue.getPrimaryKey());
            
            communicationEventPurposeDescription.setThruTime(session.START_TIME_LONG);
            communicationEventPurposeDescription.store();
            
            CommunicationEventPurpose communicationEventPurpose = communicationEventPurposeDescription.getCommunicationEventPurpose();
            Language language = communicationEventPurposeDescription.getLanguage();
            String description = communicationEventPurposeDescriptionValue.getDescription();
            
            communicationEventPurposeDescription = CommunicationEventPurposeDescriptionFactory.getInstance().create(communicationEventPurpose, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(communicationEventPurpose.getPrimaryKey(), EventTypes.MODIFY.name(), communicationEventPurposeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteCommunicationEventPurposeDescription(CommunicationEventPurposeDescription communicationEventPurposeDescription, BasePK deletedBy) {
        communicationEventPurposeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(communicationEventPurposeDescription.getCommunicationEventPurposePK(), EventTypes.MODIFY.name(), communicationEventPurposeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
        
    }
    
    public void deleteCommunicationEventPurposeDescriptionsByCommunicationEventPurpose(CommunicationEventPurpose communicationEventPurpose, BasePK deletedBy) {
        List<CommunicationEventPurposeDescription> communicationEventPurposeDescriptions = getCommunicationEventPurposeDescriptionsByCommunicationEventPurposeForUpdate(communicationEventPurpose);
        
        communicationEventPurposeDescriptions.stream().forEach((communicationEventPurposeDescription) -> {
            deleteCommunicationEventPurposeDescription(communicationEventPurposeDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Communication Event Role Types
    // --------------------------------------------------------------------------------
    
    public CommunicationEventRoleType createCommunicationEventRoleType(String communicationEventRoleTypeName, Integer sortOrder) {
        return CommunicationEventRoleTypeFactory.getInstance().create(communicationEventRoleTypeName, sortOrder);
    }
    
    public CommunicationEventRoleType getCommunicationEventRoleTypeByName(String communicationEventRoleTypeName) {
        CommunicationEventRoleType communicationEventRoleType;
        
        try {
            PreparedStatement ps = CommunicationEventRoleTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM communicationeventroletypes " +
                    "WHERE cmmnevrtyp_communicationeventroletypename = ?");
            
            ps.setString(1, communicationEventRoleTypeName);
            
            communicationEventRoleType = CommunicationEventRoleTypeFactory.getInstance().getEntityFromQuery(session,
                    EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return communicationEventRoleType;
    }
    
    public List<CommunicationEventRoleType> getCommunicationEventRoleTypes() {
        PreparedStatement ps = CommunicationEventRoleTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM communicationeventroletypes " +
                "ORDER BY cmmnevrtyp_sortorder, cmmnevrtyp_communicationeventroletypename");
        
        return CommunicationEventRoleTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public CommunicationEventRoleTypeTransfer getCommunicationEventRoleTypeTransfer(UserVisit userVisit,
            CommunicationEventRoleType communicationEventRoleType) {
        return getCommunicationTransferCaches(userVisit).getCommunicationEventRoleTypeTransferCache().getCommunicationEventRoleTypeTransfer(communicationEventRoleType);
    }
    
    // --------------------------------------------------------------------------------
    //   Communication Event Role Type Descriptions
    // --------------------------------------------------------------------------------
    
    public CommunicationEventRoleTypeDescription createCommunicationEventRoleTypeDescription(CommunicationEventRoleType communicationEventRoleType,
            Language language, String description) {
        return CommunicationEventRoleTypeDescriptionFactory.getInstance().create(communicationEventRoleType, language, description);
    }
    
    public CommunicationEventRoleTypeDescription getCommunicationEventRoleTypeDescription(CommunicationEventRoleType communicationEventRoleType,
            Language language) {
        CommunicationEventRoleTypeDescription communicationEventRoleTypeDescription;
        
        try {
            PreparedStatement ps = CommunicationEventRoleTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM communicationeventroletypedescriptions " +
                    "WHERE cmmnevrtypd_cmmnevrtyp_communicationeventroletypeid = ? AND cmmnevrtypd_lang_languageid = ?");
            
            ps.setLong(1, communicationEventRoleType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            communicationEventRoleTypeDescription = CommunicationEventRoleTypeDescriptionFactory.getInstance().getEntityFromQuery(session,
                    EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return communicationEventRoleTypeDescription;
    }
    
    public String getBestCommunicationEventRoleTypeDescription(CommunicationEventRoleType communicationEventRoleType, Language language) {
        String description;
        CommunicationEventRoleTypeDescription communicationEventRoleTypeDescription = getCommunicationEventRoleTypeDescription(communicationEventRoleType, language);
        
        if(communicationEventRoleTypeDescription == null && !language.getIsDefault()) {
            communicationEventRoleTypeDescription = getCommunicationEventRoleTypeDescription(communicationEventRoleType, getPartyControl().getDefaultLanguage());
        }
        
        if(communicationEventRoleTypeDescription == null) {
            description = communicationEventRoleType.getCommunicationEventRoleTypeName();
        } else {
            description = communicationEventRoleTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Communication Event Types
    // --------------------------------------------------------------------------------
    
    public CommunicationEventType createCommunicationEventType(String communicationEventTypeName, Boolean isDefault,
            Integer sortOrder) {
        return CommunicationEventTypeFactory.getInstance().create(communicationEventTypeName, isDefault, sortOrder);
    }
    
    public CommunicationEventType getCommunicationEventTypeByName(String communicationEventTypeName) {
        CommunicationEventType communicationEventType;
        
        try {
            PreparedStatement ps = CommunicationEventTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM communicationeventtypes " +
                    "WHERE cmmnevtyp_communicationeventtypename = ?");
            
            ps.setString(1, communicationEventTypeName);
            
            communicationEventType = CommunicationEventTypeFactory.getInstance().getEntityFromQuery(session,
                    EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return communicationEventType;
    }
    
    public List<CommunicationEventType> getCommunicationEventTypes() {
        PreparedStatement ps = CommunicationEventTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM communicationeventtypes " +
                "ORDER BY cmmnevtyp_sortorder, cmmnevtyp_communicationeventtypename");
        
        return CommunicationEventTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public CommunicationEventTypeTransfer getCommunicationEventTypeTransfer(UserVisit userVisit,
            CommunicationEventType communicationEventType) {
        return getCommunicationTransferCaches(userVisit).getCommunicationEventTypeTransferCache().getCommunicationEventTypeTransfer(communicationEventType);
    }
    
    // --------------------------------------------------------------------------------
    //   Communication Event Type Descriptions
    // --------------------------------------------------------------------------------
    
    public CommunicationEventTypeDescription createCommunicationEventTypeDescription(CommunicationEventType communicationEventType,
            Language language, String description) {
        return CommunicationEventTypeDescriptionFactory.getInstance().create(communicationEventType, language, description);
    }
    
    public CommunicationEventTypeDescription getCommunicationEventTypeDescription(CommunicationEventType communicationEventType,
            Language language) {
        CommunicationEventTypeDescription communicationEventTypeDescription;
        
        try {
            PreparedStatement ps = CommunicationEventTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM communicationeventtypedescriptions " +
                    "WHERE cmmnevtypd_cmmnevtyp_communicationeventtypeid = ? AND cmmnevtypd_lang_languageid = ?");
            
            ps.setLong(1, communicationEventType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            communicationEventTypeDescription = CommunicationEventTypeDescriptionFactory.getInstance().getEntityFromQuery(session,
                    EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return communicationEventTypeDescription;
    }
    
    public String getBestCommunicationEventTypeDescription(CommunicationEventType communicationEventType, Language language) {
        String description;
        CommunicationEventTypeDescription communicationEventTypeDescription = getCommunicationEventTypeDescription(communicationEventType, language);
        
        if(communicationEventTypeDescription == null && !language.getIsDefault()) {
            communicationEventTypeDescription = getCommunicationEventTypeDescription(communicationEventType, getPartyControl().getDefaultLanguage());
        }
        
        if(communicationEventTypeDescription == null) {
            description = communicationEventType.getCommunicationEventTypeName();
        } else {
            description = communicationEventTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Communication Events
    // --------------------------------------------------------------------------------
    
    public CommunicationEvent createCommunicationEvent(CommunicationEventType communicationEventType,
            CommunicationSource communicationSource, CommunicationEventPurpose communicationEventPurpose,
            CommunicationEvent originalCommunicationEvent, CommunicationEvent parentCommunicationEvent,
            PartyContactMechanism partyContactMechanism, Document document, BasePK createdBy) {
        var sequenceControl = (SequenceControl)Session.getModelController(SequenceControl.class);
        Sequence sequence = sequenceControl.getDefaultSequence(sequenceControl.getSequenceTypeByName(SequenceTypes.COMMUNICATION_EVENT.name()));
        String communicationEventName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(sequence);
        
        return createCommunicationEvent(communicationEventName, communicationEventType, communicationSource, communicationEventPurpose,
                originalCommunicationEvent, parentCommunicationEvent, partyContactMechanism, document, createdBy);
    }
    
    public CommunicationEvent createCommunicationEvent(String communicationEventName, CommunicationEventType communicationEventType,
            CommunicationSource communicationSource, CommunicationEventPurpose communicationEventPurpose,
            CommunicationEvent originalCommunicationEvent, CommunicationEvent parentCommunicationEvent,
            PartyContactMechanism partyContactMechanism, Document document, BasePK createdBy) {
        CommunicationEvent communicationEvent = CommunicationEventFactory.getInstance().create();
        CommunicationEventDetail communicationEventDetail = CommunicationEventDetailFactory.getInstance().create(session,
                communicationEvent, communicationEventName, communicationEventType, communicationSource, communicationEventPurpose,
                originalCommunicationEvent, parentCommunicationEvent, partyContactMechanism, document,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        communicationEvent = CommunicationEventFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                communicationEvent.getPrimaryKey());
        communicationEvent.setActiveDetail(communicationEventDetail);
        communicationEvent.setLastDetail(communicationEventDetail);
        communicationEvent.store();
        
        sendEventUsingNames(communicationEvent.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return communicationEvent;
    }
    
    /** Assume that the entityInstance passed to this function is a ECHOTHREE.CommunicationEvent */
    public CommunicationEvent getCommunicationEventByEntityInstance(EntityInstance entityInstance) {
        CommunicationEventPK pk = new CommunicationEventPK(entityInstance.getEntityUniqueId());
        CommunicationEvent communicationEvent = CommunicationEventFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);

        return communicationEvent;
    }

    private List<CommunicationEvent> getCommunicationEventsByParty(Party party, EntityPermission entityPermission) {
        List<CommunicationEvent> communicationEvents = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM communicationeventroles, communicationevents, communicationeventdetails " +
                        "WHERE cmmnevr_par_partyid = ? AND cmmnevr_thrutime = ? " +
                        "AND cmmnev_activedetailid = cmmnevdt_communicationeventdetailid " +
                        "AND cmmnevr_cmmnev_communicationeventid = cmmnev_communicationeventid " +
                        "ORDER BY cmmnevdt_communicationeventname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM communicationeventroles, communicationevents " +
                        "WHERE cmmnevr_par_partyid = ? AND cmmnevr_thrutime = ? " +
                        "AND cmmnevr_cmmnev_communicationeventid = cmmnev_communicationeventid " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = CommunicationEventFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, party.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            communicationEvents = CommunicationEventFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return communicationEvents;
    }
    
    public List<CommunicationEvent> getCommunicationEventsByParty(Party party) {
        return getCommunicationEventsByParty(party, EntityPermission.READ_ONLY);
    }
    
    public List<CommunicationEvent> getCommunicationEventsByPartyForUpdate(Party party) {
        return getCommunicationEventsByParty(party, EntityPermission.READ_WRITE);
    }
    
    public long countCommunicationEventsByParty(Party party) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM communicationeventroles " +
                "WHERE cmmnevr_par_partyid = ? AND cmmnevr_thrutime = ?",
                party, Session.MAX_TIME);
    }

    private CommunicationEvent getCommunicationEventByName(String communicationEventName, EntityPermission entityPermission) {
        CommunicationEvent communicationEvent;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM communicationevents, communicationeventdetails " +
                        "WHERE cmmnev_activedetailid = cmmnevdt_communicationeventdetailid AND cmmnevdt_communicationeventname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM communicationevents, communicationeventdetails " +
                        "WHERE cmmnev_activedetailid = cmmnevdt_communicationeventdetailid AND cmmnevdt_communicationeventname = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = CommunicationEventFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, communicationEventName);
            
            communicationEvent = CommunicationEventFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return communicationEvent;
    }
    
    public CommunicationEvent getCommunicationEventByName(String communicationEventName) {
        return getCommunicationEventByName(communicationEventName, EntityPermission.READ_ONLY);
    }
    
    public CommunicationEvent getCommunicationEventByNameForUpdate(String communicationEventName) {
        return getCommunicationEventByName(communicationEventName, EntityPermission.READ_WRITE);
    }
    
    public CommunicationEventDetailValue getCommunicationEventDetailValueForUpdate(CommunicationEvent communicationEvent) {
        return communicationEvent == null? null: communicationEvent.getLastDetailForUpdate().getCommunicationEventDetailValue().clone();
    }
    
    public CommunicationEventDetailValue getCommunicationEventDetailValueByNameForUpdate(String communicationEventName) {
        return getCommunicationEventDetailValueForUpdate(getCommunicationEventByNameForUpdate(communicationEventName));
    }
    
    public CommunicationEventTransfer getCommunicationEventTransfer(UserVisit userVisit, CommunicationEvent communicationEvent) {
        return getCommunicationTransferCaches(userVisit).getCommunicationEventTransferCache().getCommunicationEventTransfer(communicationEvent);
    }
    
    public List<CommunicationEventTransfer> getCommunicationEventTransfers(UserVisit userVisit, List<CommunicationEvent> communicationEvents) {
        List<CommunicationEventTransfer> communicationEventTransfers = new ArrayList<>(communicationEvents.size());
        CommunicationEventTransferCache communicationEventTransferCache = getCommunicationTransferCaches(userVisit).getCommunicationEventTransferCache();
        
        communicationEvents.forEach((communicationEvent) ->
                communicationEventTransfers.add(communicationEventTransferCache.getCommunicationEventTransfer(communicationEvent))
        );
        
        return communicationEventTransfers;
    }
    
    public List<CommunicationEventTransfer> getCommunicationEventTransfersByParty(UserVisit userVisit, Party party) {
        return getCommunicationEventTransfers(userVisit, getCommunicationEventsByParty(party));
    }
    
    public void deleteCommunicationEventsByPartyContactMechanism(PartyContactMechanism partyContactMechanism, BasePK deletedBy) {
        // TODO
    }
    
    // --------------------------------------------------------------------------------
    //   Communication Event Roles
    // --------------------------------------------------------------------------------
    
    public CommunicationEventRole createCommunicationEventRole(CommunicationEvent communicationEvent, Party party,
            CommunicationEventRoleType communicationEventRoleType, BasePK createdBy) {
        CommunicationEventRole communicationEventRole = CommunicationEventRoleFactory.getInstance().create(session,
                communicationEvent, party, communicationEventRoleType, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(communicationEvent.getPrimaryKey(), EventTypes.MODIFY.name(),
                communicationEventRole.getPrimaryKey(), null, createdBy);
        
        return communicationEventRole;
    }
    
    private CommunicationEventRole getCommunicationEventRole(CommunicationEvent communicationEvent, Party party,
            CommunicationEventRoleType communicationEventRoleType, EntityPermission entityPermission) {
        CommunicationEventRole communicationEventRole;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM communicationeventroles " +
                        "WHERE cmmnevr_cmmnev_communicationeventid = ? AND cmmnevr_par_partyid = ? " +
                        "AND cmmnevr_cmmnevrtyp_communicationeventroletypeid = ? AND cmmnevr_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM communicationeventroles " +
                        "WHERE cmmnevr_cmmnev_communicationeventid = ? AND cmmnevr_par_partyid = ? " +
                        "AND cmmnevr_cmmnevrtyp_communicationeventroletypeid = ? AND cmmnevr_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = CommunicationEventRoleFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, communicationEvent.getPrimaryKey().getEntityId());
            ps.setLong(2, party.getPrimaryKey().getEntityId());
            ps.setLong(3, communicationEventRoleType.getPrimaryKey().getEntityId());
            ps.setLong(4, Session.MAX_TIME);
            
            communicationEventRole = CommunicationEventRoleFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return communicationEventRole;
    }
    
    public CommunicationEventRole getCommunicationEventRole(CommunicationEvent communicationEvent, Party party,
            CommunicationEventRoleType communicationEventRoleType) {
        return getCommunicationEventRole(communicationEvent, party, communicationEventRoleType, EntityPermission.READ_ONLY);
    }
    
    public CommunicationEventRole getCommunicationEventRoleForUpdate(CommunicationEvent communicationEvent, Party party,
            CommunicationEventRoleType communicationEventRoleType) {
        return getCommunicationEventRole(communicationEvent, party, communicationEventRoleType, EntityPermission.READ_WRITE);
    }
    
    private List<CommunicationEventRole> getCommunicationEventRolesByCommunicationEvent(CommunicationEvent communicationEvent,
            EntityPermission entityPermission) {
        List<CommunicationEventRole> communicationEventRoles = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM communicationeventroles, communicationeventroletypes, parties, partydetails " +
                        "WHERE cmmnevr_cmmnev_communicationeventid = ? AND cmmnevr_par_partyid = ? " +
                        "AND cmmnevr_cmmnevrtyp_communicationeventroletypeid = ? AND cmmnevr_thrutime = ? " +
                        "AND cmmnevr_par_partyid = par_partyid AND par_lastdetailid = pardt_partydetailid " +
                        "AND cmmnevr_cmmnevrtyp_communicationeventroletypeid = cmmnevrtyp_communicationeventroletypeid " +
                        "ORDER BY cmmnevrtyp_sortorder, cmmnevrtyp_communicationeventroletypename, pardt_partyname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM communicationeventroles " +
                        "WHERE cmmnevr_cmmnev_communicationeventid = ? AND cmmnevr_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = CommunicationEventRoleFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, communicationEvent.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            communicationEventRoles = CommunicationEventRoleFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return communicationEventRoles;
    }
    
    public List<CommunicationEventRole> getCommunicationEventRole(CommunicationEvent communicationEvent) {
        return getCommunicationEventRolesByCommunicationEvent(communicationEvent, EntityPermission.READ_ONLY);
    }
    
    public List<CommunicationEventRole> getCommunicationEventRoleForUpdate(CommunicationEvent communicationEvent) {
        return getCommunicationEventRolesByCommunicationEvent(communicationEvent, EntityPermission.READ_WRITE);
    }
    
    private List<CommunicationEventRole> getCommunicationEventRolesByPartyAndCommunicationEventRoleType(Party party,
            CommunicationEventRoleType communicationEventRoleType, EntityPermission entityPermission) {
        List<CommunicationEventRole> communicationEventRoles = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM communicationeventroles, communicationevents, communicationeventdetails " +
                        "WHERE cmmnevr_par_partyid = ? AND cmmnevr_par_partyid = ? " +
                        "AND cmmnevr_cmmnevrtyp_communicationeventroletypeid = ? AND cmmnevr_thrutime = ? " +
                        "AND cmmnevr_cmmnev_communicationeventid = cmmnev_communicationeventid " +
                        "AND cmmnev_lastdetailid = cmmnevdt_communicationeventdetailid " +
                        "ORDER BY cmmnevdt_communicationeventname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM communicationeventroles " +
                        "WHERE cmmnevr_par_partyid = ? AND cmmnevr_par_partyid = ? " +
                        "AND cmmnevr_cmmnevrtyp_communicationeventroletypeid = ? AND cmmnevr_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = CommunicationEventRoleFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, party.getPrimaryKey().getEntityId());
            ps.setLong(2, communicationEventRoleType.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            communicationEventRoles = CommunicationEventRoleFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return communicationEventRoles;
    }
    
    public List<CommunicationEventRole> getCommunicationEventRole(Party party,
            CommunicationEventRoleType communicationEventRoleType) {
        return getCommunicationEventRolesByPartyAndCommunicationEventRoleType(party, communicationEventRoleType,
                EntityPermission.READ_ONLY);
    }
    
    public List<CommunicationEventRole> getCommunicationEventRoleForUpdate(Party party,
            CommunicationEventRoleType communicationEventRoleType) {
        return getCommunicationEventRolesByPartyAndCommunicationEventRoleType(party, communicationEventRoleType,
                EntityPermission.READ_WRITE);
    }
    
    public CommunicationEventRoleTransfer getCommunicationEventRoleTransfer(UserVisit userVisit,
            CommunicationEventRole communicationEventRole) {
        return getCommunicationTransferCaches(userVisit).getCommunicationEventRoleTransferCache().getCommunicationEventRoleTransfer(communicationEventRole);
    }
    
    public void deleteCommunicationEventRole(CommunicationEventRole communicationEventRole, BasePK deletedBy) {
        communicationEventRole.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(communicationEventRole.getCommunicationEventPK(), EventTypes.MODIFY.name(),
                communicationEventRole.getPrimaryKey(), null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Communication Source Types
    // --------------------------------------------------------------------------------
    
    public CommunicationSourceType createCommunicationSourceType(String communicationSourceTypeName, Boolean isDefault,
            Integer sortOrder) {
        return CommunicationSourceTypeFactory.getInstance().create(communicationSourceTypeName, isDefault, sortOrder);
    }
    
    public CommunicationSourceType getCommunicationSourceTypeByName(String communicationSourceTypeName) {
        CommunicationSourceType communicationSourceType;
        
        try {
            PreparedStatement ps = CommunicationSourceTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM communicationsourcetypes " +
                    "WHERE cmmnsrctyp_communicationsourcetypename = ?");
            
            ps.setString(1, communicationSourceTypeName);
            
            communicationSourceType = CommunicationSourceTypeFactory.getInstance().getEntityFromQuery(session,
                    EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return communicationSourceType;
    }
    
    public List<CommunicationSourceType> getCommunicationSourceTypes() {
        PreparedStatement ps = CommunicationSourceTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM communicationsourcetypes " +
                "ORDER BY cmmnsrctyp_sortorder, cmmnsrctyp_communicationsourcetypename");
        
        return CommunicationSourceTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public CommunicationSourceTypeTransfer getCommunicationSourceTypeTransfer(UserVisit userVisit,
            CommunicationSourceType communicationSourceType) {
        return getCommunicationTransferCaches(userVisit).getCommunicationSourceTypeTransferCache().getCommunicationSourceTypeTransfer(communicationSourceType);
    }
    
    // --------------------------------------------------------------------------------
    //   Communication Source Type Descriptions
    // --------------------------------------------------------------------------------
    
    public CommunicationSourceTypeDescription createCommunicationSourceTypeDescription(CommunicationSourceType communicationSourceType,
            Language language, String description) {
        return CommunicationSourceTypeDescriptionFactory.getInstance().create(communicationSourceType, language, description);
    }
    
    public CommunicationSourceTypeDescription getCommunicationSourceTypeDescription(CommunicationSourceType communicationSourceType,
            Language language) {
        CommunicationSourceTypeDescription communicationSourceTypeDescription;
        
        try {
            PreparedStatement ps = CommunicationSourceTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM communicationsourcetypedescriptions " +
                    "WHERE cmmnsrctypd_cmmnsrctyp_communicationsourcetypeid = ? AND cmmnsrctypd_lang_languageid = ?");
            
            ps.setLong(1, communicationSourceType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            communicationSourceTypeDescription = CommunicationSourceTypeDescriptionFactory.getInstance().getEntityFromQuery(session,
                    EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return communicationSourceTypeDescription;
    }
    
    public String getBestCommunicationSourceTypeDescription(CommunicationSourceType communicationSourceType, Language language) {
        String description;
        CommunicationSourceTypeDescription communicationSourceTypeDescription = getCommunicationSourceTypeDescription(communicationSourceType, language);
        
        if(communicationSourceTypeDescription == null && !language.getIsDefault()) {
            communicationSourceTypeDescription = getCommunicationSourceTypeDescription(communicationSourceType, getPartyControl().getDefaultLanguage());
        }
        
        if(communicationSourceTypeDescription == null) {
            description = communicationSourceType.getCommunicationSourceTypeName();
        } else {
            description = communicationSourceTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Communication Sources
    // --------------------------------------------------------------------------------
    
    public CommunicationSource createCommunicationSource(String communicationSourceName, CommunicationSourceType communicationSourceType,
            Integer sortOrder, BasePK createdBy) {
        CommunicationSource communicationSource = CommunicationSourceFactory.getInstance().create();
        CommunicationSourceDetail communicationSourceDetail = CommunicationSourceDetailFactory.getInstance().create(session,
                communicationSource, communicationSourceName, communicationSourceType, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        // Convert to R/W
        communicationSource = CommunicationSourceFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, communicationSource.getPrimaryKey());
        communicationSource.setActiveDetail(communicationSourceDetail);
        communicationSource.setLastDetail(communicationSourceDetail);
        communicationSource.store();
        
        sendEventUsingNames(communicationSource.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return communicationSource;
    }
    
    private CommunicationSource getCommunicationSourceByName(String communicationSourceName, EntityPermission entityPermission) {
        CommunicationSource communicationSource;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM communicationsources, communicationsourcedetails " +
                        "WHERE cmmnsrc_activedetailid = cmmnsrcdt_communicationsourcedetailid AND cmmnsrcdt_communicationsourcename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM communicationsources, communicationsourcedetails " +
                        "WHERE cmmnsrc_activedetailid = cmmnsrcdt_communicationsourcedetailid AND cmmnsrcdt_communicationsourcename = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = CommunicationSourceFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, communicationSourceName);
            
            communicationSource = CommunicationSourceFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return communicationSource;
    }
    
    public CommunicationSource getCommunicationSourceByName(String communicationSourceName) {
        return getCommunicationSourceByName(communicationSourceName, EntityPermission.READ_ONLY);
    }
    
    public CommunicationSource getCommunicationSourceByNameForUpdate(String communicationSourceName) {
        return getCommunicationSourceByName(communicationSourceName, EntityPermission.READ_WRITE);
    }
    
    public CommunicationSourceDetailValue getCommunicationSourceDetailValueForUpdate(CommunicationSource communicationSource) {
        return communicationSource == null? null: communicationSource.getLastDetailForUpdate().getCommunicationSourceDetailValue().clone();
    }
    
    public CommunicationSourceDetailValue getCommunicationSourceDetailValueByNameForUpdate(String communicationSourceName) {
        return getCommunicationSourceDetailValueForUpdate(getCommunicationSourceByNameForUpdate(communicationSourceName));
    }
    
    private List<CommunicationSource> getCommunicationSources(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM communicationsources, communicationsourcedetails " +
                    "WHERE cmmnsrc_activedetailid = cmmnsrcdt_communicationsourcedetailid " +
                    "ORDER BY cmmnsrcdt_sortorder, cmmnsrcdt_communicationsourcename";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM communicationsources, communicationsourcedetails " +
                    "WHERE cmmnsrc_activedetailid = cmmnsrcdt_communicationsourcedetailid " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = CommunicationSourceFactory.getInstance().prepareStatement(query);
        
        return CommunicationSourceFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<CommunicationSource> getCommunicationSources() {
        return getCommunicationSources(EntityPermission.READ_ONLY);
    }
    
    public List<CommunicationSource> getCommunicationSourcesForUpdate() {
        return getCommunicationSources(EntityPermission.READ_WRITE);
    }
    
    private List<CommunicationSource> getCommunicationSourcesByCommunicationSourceType(CommunicationSourceType communicationSourceType,
            EntityPermission entityPermission) {
        List<CommunicationSource> communicationSources = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM communicationsources, communicationsourcedetails " +
                        "WHERE cmmnsrc_activedetailid = cmmnsrcdt_communicationsourcedetailid AND cmmnsrcdt_cmmnsrctyp_communicationsourcetypeid = ? " +
                        "ORDER BY cmmnsrcdt_sortorder, cmmnsrcdt_communicationsourcename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM communicationsources, communicationsourcedetails " +
                        "WHERE cmmnsrc_activedetailid = cmmnsrcdt_communicationsourcedetailid AND cmmnsrcdt_cmmnsrctyp_communicationsourcetypeid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = CommunicationSourceFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, communicationSourceType.getPrimaryKey().getEntityId());
            
            communicationSources = CommunicationSourceFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return communicationSources;
    }
    
    public List<CommunicationSource> getCommunicationSourcesByCommunicationSourceType(CommunicationSourceType communicationSourceType) {
        return getCommunicationSourcesByCommunicationSourceType(communicationSourceType, EntityPermission.READ_ONLY);
    }
    
    public List<CommunicationSource> getCommunicationSourcesByCommunicationSourceTypeForUpdate(CommunicationSourceType communicationSourceType) {
        return getCommunicationSourcesByCommunicationSourceType(communicationSourceType, EntityPermission.READ_WRITE);
    }
    
    public CommunicationSourceTransfer getCommunicationSourceTransfer(UserVisit userVisit, CommunicationSource communicationSource) {
        return getCommunicationTransferCaches(userVisit).getCommunicationSourceTransferCache().getCommunicationSourceTransfer(communicationSource);
    }
    
    public List<CommunicationSourceTransfer> getCommunicationSourceTransfers(UserVisit userVisit, List<CommunicationSource> communicationSources) {
        List<CommunicationSourceTransfer> communicationSourceTransfers = new ArrayList<>(communicationSources.size());
        CommunicationSourceTransferCache communicationSourceTransferCache = getCommunicationTransferCaches(userVisit).getCommunicationSourceTransferCache();
        
        communicationSources.forEach((communicationSource) ->
                communicationSourceTransfers.add(communicationSourceTransferCache.getCommunicationSourceTransfer(communicationSource))
        );
        
        return communicationSourceTransfers;
    }
    
    public List<CommunicationSourceTransfer> getCommunicationSourceTransfers(UserVisit userVisit) {
        return getCommunicationSourceTransfers(userVisit, getCommunicationSources());
    }
    
    public List<CommunicationSourceTransfer> getCommunicationSourceTransfersByCommunicationSourceType(UserVisit userVisit,
            CommunicationSourceType communicationSourceType) {
        return getCommunicationSourceTransfers(userVisit, getCommunicationSourcesByCommunicationSourceType(communicationSourceType));
    }
    
    public void updateCommunicationSourceFromValue(CommunicationSourceDetailValue communicationSourceDetailValue, BasePK updatedBy) {
        if(communicationSourceDetailValue.hasBeenModified()) {
            CommunicationSource communicationSource = CommunicationSourceFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     communicationSourceDetailValue.getCommunicationSourcePK());
            CommunicationSourceDetail communicationSourceDetail = communicationSource.getActiveDetailForUpdate();
            
            communicationSourceDetail.setThruTime(session.START_TIME_LONG);
            communicationSourceDetail.store();
            
            CommunicationSourcePK communicationSourcePK = communicationSourceDetail.getCommunicationSourcePK();
            String communicationSourceName = communicationSourceDetailValue.getCommunicationSourceName();
            CommunicationSourceTypePK communicationSourceTypePK = communicationSourceDetail.getCommunicationSourceTypePK(); // Not updated
            Integer sortOrder = communicationSourceDetailValue.getSortOrder();
            
            communicationSourceDetail = CommunicationSourceDetailFactory.getInstance().create(communicationSourcePK,
                    communicationSourceName, communicationSourceTypePK, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            communicationSource.setActiveDetail(communicationSourceDetail);
            communicationSource.setLastDetail(communicationSourceDetail);
            
            sendEventUsingNames(communicationSourcePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void deleteCommunicationSource(CommunicationSource communicationSource, BasePK deletedBy) {
        deleteCommunicationSourceDescriptionsByCommunicationSource(communicationSource, deletedBy);
        deleteCommunicationEmailSourceByCommunicationSource(communicationSource, deletedBy);
        
        CommunicationSourceDetail communicationSourceDetail = communicationSource.getLastDetailForUpdate();
        communicationSourceDetail.setThruTime(session.START_TIME_LONG);
        communicationSource.setActiveDetail(null);
        communicationSource.store();
        
        sendEventUsingNames(communicationSource.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Communication Source Descriptions
    // --------------------------------------------------------------------------------
    
    public CommunicationSourceDescription createCommunicationSourceDescription(CommunicationSource communicationSource,
            Language language, String description, BasePK createdBy) {
        CommunicationSourceDescription communicationSourceDescription = CommunicationSourceDescriptionFactory.getInstance().create(session,
                communicationSource, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(communicationSource.getPrimaryKey(), EventTypes.MODIFY.name(), communicationSourceDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return communicationSourceDescription;
    }
    
    private CommunicationSourceDescription getCommunicationSourceDescription(CommunicationSource communicationSource,
            Language language, EntityPermission entityPermission) {
        CommunicationSourceDescription communicationSourceDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM communicationsourcedescriptions " +
                        "WHERE cmmnsrcd_cmmnsrc_communicationsourceid = ? AND cmmnsrcd_lang_languageid = ? AND cmmnsrcd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM communicationsourcedescriptions " +
                        "WHERE cmmnsrcd_cmmnsrc_communicationsourceid = ? AND cmmnsrcd_lang_languageid = ? AND cmmnsrcd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = CommunicationSourceDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, communicationSource.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            communicationSourceDescription = CommunicationSourceDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return communicationSourceDescription;
    }
    
    public CommunicationSourceDescription getCommunicationSourceDescription(CommunicationSource communicationSource, Language language) {
        return getCommunicationSourceDescription(communicationSource, language, EntityPermission.READ_ONLY);
    }
    
    public CommunicationSourceDescription getCommunicationSourceDescriptionForUpdate(CommunicationSource communicationSource, Language language) {
        return getCommunicationSourceDescription(communicationSource, language, EntityPermission.READ_WRITE);
    }
    
    public CommunicationSourceDescriptionValue getCommunicationSourceDescriptionValue(CommunicationSourceDescription communicationSourceDescription) {
        return communicationSourceDescription == null? null: communicationSourceDescription.getCommunicationSourceDescriptionValue().clone();
    }
    
    public CommunicationSourceDescriptionValue getCommunicationSourceDescriptionValueForUpdate(CommunicationSource communicationSource, Language language) {
        return getCommunicationSourceDescriptionValue(getCommunicationSourceDescriptionForUpdate(communicationSource, language));
    }
    
    private List<CommunicationSourceDescription> getCommunicationSourceDescriptionsByCommunicationSource(CommunicationSource communicationSource, EntityPermission entityPermission) {
        List<CommunicationSourceDescription> communicationSourceDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM communicationsourcedescriptions, languages " +
                        "WHERE cmmnsrcd_cmmnsrc_communicationsourceid = ? AND cmmnsrcd_thrutime = ? AND cmmnsrcd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM communicationsourcedescriptions " +
                        "WHERE cmmnsrcd_cmmnsrc_communicationsourceid = ? AND cmmnsrcd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = CommunicationSourceDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, communicationSource.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            communicationSourceDescriptions = CommunicationSourceDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return communicationSourceDescriptions;
    }
    
    public List<CommunicationSourceDescription> getCommunicationSourceDescriptionsByCommunicationSource(CommunicationSource communicationSource) {
        return getCommunicationSourceDescriptionsByCommunicationSource(communicationSource, EntityPermission.READ_ONLY);
    }
    
    public List<CommunicationSourceDescription> getCommunicationSourceDescriptionsByCommunicationSourceForUpdate(CommunicationSource communicationSource) {
        return getCommunicationSourceDescriptionsByCommunicationSource(communicationSource, EntityPermission.READ_WRITE);
    }
    
    public String getBestCommunicationSourceDescription(CommunicationSource communicationSource, Language language) {
        String description;
        CommunicationSourceDescription communicationSourceDescription = getCommunicationSourceDescription(communicationSource, language);
        
        if(communicationSourceDescription == null && !language.getIsDefault()) {
            communicationSourceDescription = getCommunicationSourceDescription(communicationSource, getPartyControl().getDefaultLanguage());
        }
        
        if(communicationSourceDescription == null) {
            description = communicationSource.getLastDetail().getCommunicationSourceName();
        } else {
            description = communicationSourceDescription.getDescription();
        }
        
        return description;
    }
    
    public CommunicationSourceDescriptionTransfer getCommunicationSourceDescriptionTransfer(UserVisit userVisit, CommunicationSourceDescription communicationSourceDescription) {
        return getCommunicationTransferCaches(userVisit).getCommunicationSourceDescriptionTransferCache().getCommunicationSourceDescriptionTransfer(communicationSourceDescription);
    }
    
    public List<CommunicationSourceDescriptionTransfer> getCommunicationSourceDescriptionTransfers(UserVisit userVisit, CommunicationSource communicationSource) {
        List<CommunicationSourceDescription> communicationSourceDescriptions = getCommunicationSourceDescriptionsByCommunicationSource(communicationSource);
        List<CommunicationSourceDescriptionTransfer> communicationSourceDescriptionTransfers = new ArrayList<>(communicationSourceDescriptions.size());
        CommunicationSourceDescriptionTransferCache communicationSourceDescriptionTransferCache = getCommunicationTransferCaches(userVisit).getCommunicationSourceDescriptionTransferCache();
        
        communicationSourceDescriptions.forEach((communicationSourceDescription) ->
                communicationSourceDescriptionTransfers.add(communicationSourceDescriptionTransferCache.getCommunicationSourceDescriptionTransfer(communicationSourceDescription))
        );
        
        return communicationSourceDescriptionTransfers;
    }
    
    public void updateCommunicationSourceDescriptionFromValue(CommunicationSourceDescriptionValue communicationSourceDescriptionValue, BasePK updatedBy) {
        if(communicationSourceDescriptionValue.hasBeenModified()) {
            CommunicationSourceDescription communicationSourceDescription = CommunicationSourceDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, communicationSourceDescriptionValue.getPrimaryKey());
            
            communicationSourceDescription.setThruTime(session.START_TIME_LONG);
            communicationSourceDescription.store();
            
            CommunicationSource communicationSource = communicationSourceDescription.getCommunicationSource();
            Language language = communicationSourceDescription.getLanguage();
            String description = communicationSourceDescriptionValue.getDescription();
            
            communicationSourceDescription = CommunicationSourceDescriptionFactory.getInstance().create(communicationSource, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(communicationSource.getPrimaryKey(), EventTypes.MODIFY.name(), communicationSourceDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteCommunicationSourceDescription(CommunicationSourceDescription communicationSourceDescription, BasePK deletedBy) {
        communicationSourceDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(communicationSourceDescription.getCommunicationSourcePK(), EventTypes.MODIFY.name(), communicationSourceDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
        
    }
    
    public void deleteCommunicationSourceDescriptionsByCommunicationSource(CommunicationSource communicationSource, BasePK deletedBy) {
        List<CommunicationSourceDescription> communicationSourceDescriptions = getCommunicationSourceDescriptionsByCommunicationSourceForUpdate(communicationSource);
        
        communicationSourceDescriptions.stream().forEach((communicationSourceDescription) -> {
            deleteCommunicationSourceDescription(communicationSourceDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Communication Email Sources
    // --------------------------------------------------------------------------------
    
    public CommunicationEmailSource createCommunicationEmailSource(CommunicationSource communicationSource, Server server,
            String username, String password, WorkEffortScope receiveWorkEffortScope, WorkEffortScope sendWorkEffortScope,
            Selector reviewEmployeeSelector, BasePK createdBy) {
        CommunicationEmailSource communicationEmailSource = CommunicationEmailSourceFactory.getInstance().create(session,
                communicationSource, server, username, encodeCommunicationEmailSourcePassword(password), receiveWorkEffortScope,
                sendWorkEffortScope, reviewEmployeeSelector, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(communicationSource.getPrimaryKey(), EventTypes.MODIFY.name(), communicationEmailSource.getPrimaryKey(), null, createdBy);
        
        return communicationEmailSource;
    }
    
    private CommunicationEmailSource getCommunicationEmailSource(CommunicationSource communicationSource, EntityPermission entityPermission) {
        CommunicationEmailSource communicationEmailSource;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM communicationemailsources " +
                        "WHERE cmmnesrc_cmmnsrc_communicationsourceid = ? AND cmmnesrc_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM communicationemailsources " +
                        "WHERE cmmnesrc_cmmnsrc_communicationsourceid = ? AND cmmnesrc_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = CommunicationEmailSourceFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, communicationSource.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            communicationEmailSource = CommunicationEmailSourceFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return communicationEmailSource;
    }
    
    public CommunicationEmailSource getCommunicationEmailSource(CommunicationSource communicationSource) {
        return getCommunicationEmailSource(communicationSource, EntityPermission.READ_ONLY);
    }
    
    public CommunicationEmailSource getCommunicationEmailSourceForUpdate(CommunicationSource communicationSource) {
        return getCommunicationEmailSource(communicationSource, EntityPermission.READ_WRITE);
    }
    
    public CommunicationEmailSourceValue getCommunicationEmailSourceValue(CommunicationEmailSource communicationEmailSource) {
        return communicationEmailSource == null? null: communicationEmailSource.getCommunicationEmailSourceValue().clone();
    }
    
    public CommunicationEmailSourceValue getCommunicationEmailSourceValueForUpdate(CommunicationSource communicationSource) {
        return getCommunicationEmailSourceValue(getCommunicationEmailSourceForUpdate(communicationSource));
    }
    
    public CommunicationEmailSourceTransfer getCommunicationEmailSourceTransfer(UserVisit userVisit, CommunicationEmailSource communicationEmailSource) {
        return getCommunicationTransferCaches(userVisit).getCommunicationEmailSourceTransferCache().getCommunicationEmailSourceTransfer(communicationEmailSource);
    }
    
    public String encodeCommunicationEmailSourcePassword(String password) {
        return EncryptionUtils.getInstance().encrypt(CommunicationEmailSourceFactory.getInstance().getEntityTypeName(),
                CommunicationEmailSourceFactory.CMMNESRC_PASSWORD, password);
    }
    
    public String decodeCommunicationEmailSourcePassword(CommunicationEmailSource communicationEmailSource) {
        return EncryptionUtils.getInstance().decrypt(CommunicationEmailSourceFactory.getInstance().getEntityTypeName(),
                CommunicationEmailSourceFactory.CMMNESRC_PASSWORD, communicationEmailSource.getPassword());
    }
    
    public void updateCommunicationEmailSourceFromValue(CommunicationEmailSourceValue communicationEmailSourceValue, BasePK updatedBy) {
        if(communicationEmailSourceValue.hasBeenModified()) {
            CommunicationEmailSource communicationEmailSource = CommunicationEmailSourceFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     communicationEmailSourceValue.getPrimaryKey());
            
            communicationEmailSource.setThruTime(session.START_TIME_LONG);
            communicationEmailSource.store();
            
            CommunicationSource communicationSource = communicationEmailSource.getCommunicationSource();
            ServerPK serverPK = communicationEmailSourceValue.getServerPK();
            String username = communicationEmailSourceValue.getUsername();
            String password = communicationEmailSourceValue.getPassword();
            WorkEffortScopePK receiveWorkEffortScopePK = communicationEmailSourceValue.getReceiveWorkEffortScopePK();
            WorkEffortScopePK sendWorkEffortScopePK = communicationEmailSourceValue.getSendWorkEffortScopePK();
            SelectorPK reviewEmployeeSelectorPK = communicationEmailSourceValue.getReviewEmployeeSelectorPK();
            
            communicationEmailSource = CommunicationEmailSourceFactory.getInstance().create(session,
                    communicationSource.getPrimaryKey(), serverPK, username, password, receiveWorkEffortScopePK,
                    sendWorkEffortScopePK, reviewEmployeeSelectorPK, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(communicationSource.getPrimaryKey(), EventTypes.MODIFY.name(),
                    communicationEmailSource.getPrimaryKey(), null, updatedBy);
        }
    }
    
    public void deleteCommunicationEmailSource(CommunicationEmailSource communicationEmailSource, BasePK deletedBy) {
        communicationEmailSource.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(communicationEmailSource.getCommunicationSource().getPrimaryKey(), EventTypes.MODIFY.name(),
                communicationEmailSource.getPrimaryKey(), null, deletedBy);
    }
    
    public void deleteCommunicationEmailSourceByCommunicationSource(CommunicationSource communicationSource, BasePK deletedBy) {
        CommunicationEmailSource communicationEmailSource = getCommunicationEmailSourceForUpdate(communicationSource);
        
        if(communicationEmailSource != null) {
            deleteCommunicationEmailSource(communicationEmailSource, deletedBy);
        }
    }
    
}
