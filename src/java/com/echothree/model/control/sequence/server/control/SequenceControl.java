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

package com.echothree.model.control.sequence.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.offer.server.control.OfferUseControl;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.common.choice.SequenceChecksumTypeChoicesBean;
import com.echothree.model.control.sequence.common.choice.SequenceChoicesBean;
import com.echothree.model.control.sequence.common.choice.SequenceEncoderTypeChoicesBean;
import com.echothree.model.control.sequence.common.choice.SequenceTypeChoicesBean;
import com.echothree.model.control.sequence.common.transfer.SequenceChecksumTypeTransfer;
import com.echothree.model.control.sequence.common.transfer.SequenceDescriptionTransfer;
import com.echothree.model.control.sequence.common.transfer.SequenceEncoderTypeTransfer;
import com.echothree.model.control.sequence.common.transfer.SequenceTransfer;
import com.echothree.model.control.sequence.common.transfer.SequenceTypeDescriptionTransfer;
import com.echothree.model.control.sequence.common.transfer.SequenceTypeTransfer;
import com.echothree.model.control.sequence.server.transfer.SequenceTransferCaches;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.sequence.common.pk.SequencePK;
import com.echothree.model.data.sequence.common.pk.SequenceTypePK;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.sequence.server.entity.SequenceChecksumType;
import com.echothree.model.data.sequence.server.entity.SequenceChecksumTypeDescription;
import com.echothree.model.data.sequence.server.entity.SequenceDescription;
import com.echothree.model.data.sequence.server.entity.SequenceEncoderType;
import com.echothree.model.data.sequence.server.entity.SequenceEncoderTypeDescription;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.sequence.server.entity.SequenceTypeDescription;
import com.echothree.model.data.sequence.server.entity.SequenceValue;
import com.echothree.model.data.sequence.server.factory.SequenceChecksumTypeDescriptionFactory;
import com.echothree.model.data.sequence.server.factory.SequenceChecksumTypeFactory;
import com.echothree.model.data.sequence.server.factory.SequenceDescriptionFactory;
import com.echothree.model.data.sequence.server.factory.SequenceDetailFactory;
import com.echothree.model.data.sequence.server.factory.SequenceEncoderTypeDescriptionFactory;
import com.echothree.model.data.sequence.server.factory.SequenceEncoderTypeFactory;
import com.echothree.model.data.sequence.server.factory.SequenceFactory;
import com.echothree.model.data.sequence.server.factory.SequenceTypeDescriptionFactory;
import com.echothree.model.data.sequence.server.factory.SequenceTypeDetailFactory;
import com.echothree.model.data.sequence.server.factory.SequenceTypeFactory;
import com.echothree.model.data.sequence.server.factory.SequenceValueFactory;
import com.echothree.model.data.sequence.server.value.SequenceDescriptionValue;
import com.echothree.model.data.sequence.server.value.SequenceDetailValue;
import com.echothree.model.data.sequence.server.value.SequenceTypeDescriptionValue;
import com.echothree.model.data.sequence.server.value.SequenceTypeDetailValue;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
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
public class SequenceControl
        extends BaseModelControl {
    
    /** Creates a new instance of SequenceControl */
    protected SequenceControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Sequence Transfer Caches
    // --------------------------------------------------------------------------------
    
    private SequenceTransferCaches sequenceTransferCaches;
    
    public SequenceTransferCaches getSequenceTransferCaches(UserVisit userVisit) {
        if(sequenceTransferCaches == null) {
            sequenceTransferCaches = new SequenceTransferCaches(userVisit, this);
        }
        
        return sequenceTransferCaches;
    }
    
    // --------------------------------------------------------------------------------
    //   Sequence Types
    // --------------------------------------------------------------------------------
    
    public SequenceType createSequenceType(String sequenceTypeName, String prefix, String suffix,
            SequenceEncoderType sequenceEncoderType, SequenceChecksumType sequenceChecksumType, Integer chunkSize, Boolean isDefault,
            Integer sortOrder, BasePK createdBy) {
        var defaultSequenceType = getDefaultSequenceType();
        var defaultFound = defaultSequenceType != null;
        
        if(defaultFound && isDefault) {
            var defaultSequenceTypeDetailValue = getDefaultSequenceTypeDetailValueForUpdate();
            
            defaultSequenceTypeDetailValue.setIsDefault(false);
            updateSequenceTypeFromValue(defaultSequenceTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var sequenceType = SequenceTypeFactory.getInstance().create();
        var sequenceTypeDetail = SequenceTypeDetailFactory.getInstance().create(sequenceType,
                sequenceTypeName, prefix, suffix, sequenceEncoderType, sequenceChecksumType, chunkSize, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        sequenceType = SequenceTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, sequenceType.getPrimaryKey());
        sequenceType.setActiveDetail(sequenceTypeDetail);
        sequenceType.setLastDetail(sequenceTypeDetail);
        sequenceType.store();
        
        sendEvent(sequenceType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return sequenceType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.SequenceType */
    public SequenceType getSequenceTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new SequenceTypePK(entityInstance.getEntityUniqueId());
        var sequenceType = SequenceTypeFactory.getInstance().getEntityFromPK(entityPermission, pk);

        return sequenceType;
    }

    public SequenceType getSequenceTypeByEntityInstance(EntityInstance entityInstance) {
        return getSequenceTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public SequenceType getSequenceTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getSequenceTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countSequenceTypes() {
        return session.queryForLong("""
                        SELECT COUNT(*)
                        FROM sequencetypes, sequencetypedetails
                        WHERE sqtyp_activedetailid = sqtypdt_sequencetypedetailid
                        """);
    }

    private List<SequenceType> getSequenceTypes(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM sequencetypes, sequencetypedetails " +
                    "WHERE sqtyp_activedetailid = sqtypdt_sequencetypedetailid " +
                    "ORDER BY sqtypdt_sortorder, sqtypdt_sequencetypename";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM sequencetypes, sequencetypedetails " +
                    "WHERE sqtyp_activedetailid = sqtypdt_sequencetypedetailid " +
                    "FOR UPDATE";
        }

        var ps = SequenceTypeFactory.getInstance().prepareStatement(query);
        
        return SequenceTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<SequenceType> getSequenceTypes() {
        return getSequenceTypes(EntityPermission.READ_ONLY);
    }
    
    public List<SequenceType> getSequenceTypesForUpdate() {
        return getSequenceTypes(EntityPermission.READ_WRITE);
    }
    
    public SequenceType getDefaultSequenceType(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM sequencetypes, sequencetypedetails " +
                    "WHERE sqtyp_activedetailid = sqtypdt_sequencetypedetailid AND sqtypdt_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM sequencetypes, sequencetypedetails " +
                    "WHERE sqtyp_activedetailid = sqtypdt_sequencetypedetailid AND sqtypdt_isdefault = 1 " +
                    "FOR UPDATE";
        }

        var ps = SequenceTypeFactory.getInstance().prepareStatement(query);
        
        return SequenceTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
    }
    
    public SequenceType getDefaultSequenceType() {
        return getDefaultSequenceType(EntityPermission.READ_ONLY);
    }
    
    public SequenceType getDefaultSequenceTypeForUpdate() {
        return getDefaultSequenceType( EntityPermission.READ_WRITE);
    }
    
    public SequenceTypeDetailValue getDefaultSequenceTypeDetailValueForUpdate() {
        return getDefaultSequenceTypeForUpdate().getLastDetailForUpdate().getSequenceTypeDetailValue().clone();
    }

    public SequenceType getSequenceTypeByName(String sequenceTypeName, EntityPermission entityPermission) {
        SequenceType sequenceType;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM sequencetypes, sequencetypedetails " +
                        "WHERE sqtyp_activedetailid = sqtypdt_sequencetypedetailid AND sqtypdt_sequencetypename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM sequencetypes, sequencetypedetails " +
                        "WHERE sqtyp_activedetailid = sqtypdt_sequencetypedetailid AND sqtypdt_sequencetypename = ? " +
                        "FOR UPDATE";
            }

            var ps = SequenceTypeFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, sequenceTypeName);
            
            sequenceType = SequenceTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return sequenceType;
    }
    
    public SequenceType getSequenceTypeByName(String sequenceTypeName) {
        return getSequenceTypeByName(sequenceTypeName, EntityPermission.READ_ONLY);
    }
    
    public SequenceType getSequenceTypeByNameForUpdate(String sequenceTypeName) {
        return getSequenceTypeByName(sequenceTypeName, EntityPermission.READ_WRITE);
    }
    
    private SequenceType getSequenceTypeByPrefix(String sequenceTypePrefix, EntityPermission entityPermission) {
        SequenceType sequenceType;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM sequencetypes, sequencetypedetails " +
                        "WHERE sqtyp_activedetailid = sqtypdt_sequencetypedetailid AND sqtypdt_prefix = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM sequencetypes, sequencetypedetails " +
                        "WHERE sqtyp_activedetailid = sqtypdt_sequencetypedetailid AND sqtypdt_prefix = ? " +
                        "FOR UPDATE";
            }

            var ps = SequenceTypeFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, sequenceTypePrefix);
            
            sequenceType = SequenceTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return sequenceType;
    }
    
    public SequenceType getSequenceTypeByPrefix(String sequenceTypePrefix) {
        return getSequenceTypeByPrefix(sequenceTypePrefix, EntityPermission.READ_ONLY);
    }
    
    public SequenceType getSequenceTypeByPrefixForUpdate(String sequenceTypePrefix) {
        return getSequenceTypeByPrefix(sequenceTypePrefix, EntityPermission.READ_WRITE);
    }
    
    private SequenceType getSequenceTypeBySuffix(String sequenceTypeSuffix, EntityPermission entityPermission) {
        SequenceType sequenceType;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM sequencetypes, sequencetypedetails " +
                        "WHERE sqtyp_activedetailid = sqtypdt_sequencetypedetailid AND sqtypdt_suffix = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM sequencetypes, sequencetypedetails " +
                        "WHERE sqtyp_activedetailid = sqtypdt_sequencetypedetailid AND sqtypdt_suffix = ? " +
                        "FOR UPDATE";
            }

            var ps = SequenceTypeFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, sequenceTypeSuffix);
            
            sequenceType = SequenceTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return sequenceType;
    }
    
    public SequenceType getSequenceTypeBySuffix(String sequenceTypeSuffix) {
        return getSequenceTypeBySuffix(sequenceTypeSuffix, EntityPermission.READ_ONLY);
    }
    
    public SequenceType getSequenceTypeBySuffixForUpdate(String sequenceTypeSuffix) {
        return getSequenceTypeBySuffix(sequenceTypeSuffix, EntityPermission.READ_WRITE);
    }
    
    public SequenceTypeDetailValue getSequenceTypeDetailValueForUpdate(SequenceType sequenceType) {
        return sequenceType == null? null: sequenceType.getLastDetailForUpdate().getSequenceTypeDetailValue().clone();
    }
    
    public SequenceTypeDetailValue getSequenceTypeDetailValueByNameForUpdate(String sequenceTypeName) {
        return getSequenceTypeDetailValueForUpdate(getSequenceTypeByNameForUpdate(sequenceTypeName));
    }
    
    public SequenceTypeChoicesBean getSequenceTypeChoices(String defaultSequenceTypeChoice, Language language, boolean allowNullChoice) {
        var sequenceTypes = getSequenceTypes();
        var size = sequenceTypes.size() + (allowNullChoice? 1: 0);
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultSequenceTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var sequenceType : sequenceTypes) {
            var sequenceTypeDetail = sequenceType.getLastDetail();
            var label = getBestSequenceTypeDescription(sequenceType, language);
            var value = sequenceTypeDetail.getSequenceTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultSequenceTypeChoice != null && defaultSequenceTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && sequenceTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new SequenceTypeChoicesBean(labels, values, defaultValue);
    }
    
    public SequenceTypeTransfer getSequenceTypeTransfer(UserVisit userVisit, SequenceType sequenceType) {
        return getSequenceTransferCaches(userVisit).getSequenceTypeTransferCache().getSequenceTypeTransfer(sequenceType);
    }

    public List<SequenceTypeTransfer> getSequenceTypeTransfers(UserVisit userVisit, Collection<SequenceType> sequenceTypes) {
        List<SequenceTypeTransfer> sequenceTypeTransfers = new ArrayList<>(sequenceTypes.size());
        var sequenceTypeTransferCache = getSequenceTransferCaches(userVisit).getSequenceTypeTransferCache();

        sequenceTypes.forEach((sequenceType) ->
                sequenceTypeTransfers.add(sequenceTypeTransferCache.getSequenceTypeTransfer(sequenceType))
        );

        return sequenceTypeTransfers;
    }

    public List<SequenceTypeTransfer> getSequenceTypeTransfers(UserVisit userVisit) {
        return getSequenceTypeTransfers(userVisit, getSequenceTypes());
    }

    private void updateSequenceTypeFromValue(SequenceTypeDetailValue sequenceTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        var sequenceType = SequenceTypeFactory.getInstance().getEntityFromPK(session,
                EntityPermission.READ_WRITE, sequenceTypeDetailValue.getSequenceTypePK());
        var sequenceTypeDetail = sequenceType.getActiveDetailForUpdate();
        
        sequenceTypeDetail.setThruTime(session.START_TIME_LONG);
        sequenceTypeDetail.store();

        var sequenceTypePK = sequenceTypeDetail.getSequenceTypePK();
        var sequenceTypeName = sequenceTypeDetailValue.getSequenceTypeName();
        var prefix = sequenceTypeDetailValue.getPrefix();
        var suffix = sequenceTypeDetailValue.getSuffix();
        var sequenceEncoderTypePK = sequenceTypeDetailValue.getSequenceEncoderTypePK();
        var sequenceChecksumTypePK = sequenceTypeDetailValue.getSequenceChecksumTypePK();
        var chunkSize = sequenceTypeDetailValue.getChunkSize();
        var isDefault = sequenceTypeDetailValue.getIsDefault();
        var sortOrder = sequenceTypeDetailValue.getSortOrder();
        
        if(checkDefault) {
            var defaultSequenceType = getDefaultSequenceType();
            var defaultFound = defaultSequenceType != null && !defaultSequenceType.equals(sequenceType);
            
            if(isDefault && defaultFound) {
                // If I'm the default, and a default already existed...
                var defaultSequenceTypeDetailValue = getDefaultSequenceTypeDetailValueForUpdate();
                
                defaultSequenceTypeDetailValue.setIsDefault(false);
                updateSequenceTypeFromValue(defaultSequenceTypeDetailValue, false, updatedBy);
            } else if(!isDefault && !defaultFound) {
                // If I'm not the default, and no other default exists...
                isDefault = true;
            }
        }
        
        sequenceTypeDetail = SequenceTypeDetailFactory.getInstance().create(sequenceTypePK, sequenceTypeName, prefix,
                suffix, sequenceEncoderTypePK, sequenceChecksumTypePK, chunkSize, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sequenceType.setActiveDetail(sequenceTypeDetail);
        sequenceType.setLastDetail(sequenceTypeDetail);
        sequenceType.store();
        
        sendEvent(sequenceTypePK, EventTypes.MODIFY, null, null, updatedBy);
    }
    
    public void updateSequenceTypeFromValue(SequenceTypeDetailValue sequenceTypeDetailValue, BasePK updatedBy) {
        updateSequenceTypeFromValue(sequenceTypeDetailValue, true, updatedBy);
    }
    
    public void deleteSequenceType(SequenceType sequenceType, BasePK deletedBy) {
        deleteSequencesBySequenceType(sequenceType, deletedBy);
        deleteSequenceTypeDescriptionsBySequenceType(sequenceType, deletedBy);

        var sequenceTypeDetail = sequenceType.getLastDetailForUpdate();
        sequenceTypeDetail.setThruTime(session.START_TIME_LONG);
        sequenceType.setActiveDetail(null);
        sequenceType.store();
        
        // Check for default, and pick one if necessary
        var defaultSequenceType = getDefaultSequenceType();
        if(defaultSequenceType == null) {
            var sequenceTypes = getSequenceTypesForUpdate();
            
            if(!sequenceTypes.isEmpty()) {
                var iter = sequenceTypes.iterator();
                if(iter.hasNext()) {
                    defaultSequenceType = iter.next();
                }
                var sequenceTypeDetailValue = Objects.requireNonNull(defaultSequenceType).getLastDetailForUpdate().getSequenceTypeDetailValue().clone();
                
                sequenceTypeDetailValue.setIsDefault(true);
                updateSequenceTypeFromValue(sequenceTypeDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(sequenceType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Sequence Type Descriptions
    // --------------------------------------------------------------------------------
    
    public SequenceTypeDescription createSequenceTypeDescription(SequenceType sequenceType, Language language, String description, BasePK createdBy) {
        var sequenceTypeDescription = SequenceTypeDescriptionFactory.getInstance().create(sequenceType, language, description, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEvent(sequenceType.getPrimaryKey(), EventTypes.MODIFY, sequenceTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return sequenceTypeDescription;
    }
    
    private List<SequenceTypeDescription> getSequenceTypeDescriptionsBySequenceType(SequenceType sequenceType, EntityPermission entityPermission) {
        List<SequenceTypeDescription> sequenceTypeDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM sequencetypedescriptions, languages " +
                        "WHERE sqtypd_sqtyp_sequencetypeid = ? AND sqtypd_thrutime = ? AND sqtypd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM sequencetypedescriptions " +
                        "WHERE sqtypd_sqtyp_sequencetypeid = ? AND sqtypd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = SequenceTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, sequenceType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            sequenceTypeDescriptions = SequenceTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return sequenceTypeDescriptions;
    }
    
    public List<SequenceTypeDescription> getSequenceTypeDescriptionsBySequenceType(SequenceType sequenceType) {
        return getSequenceTypeDescriptionsBySequenceType(sequenceType, EntityPermission.READ_ONLY);
    }
    
    public List<SequenceTypeDescription> getSequenceTypeDescriptionsBySequenceTypeForUpdate(SequenceType sequenceType) {
        return getSequenceTypeDescriptionsBySequenceType(sequenceType, EntityPermission.READ_WRITE);
    }
    
    private SequenceTypeDescription getSequenceTypeDescription(SequenceType sequenceType, Language language, EntityPermission entityPermission) {
        SequenceTypeDescription sequenceTypeDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM sequencetypedescriptions " +
                        "WHERE sqtypd_sqtyp_sequencetypeid = ? AND sqtypd_lang_languageid = ? AND sqtypd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM sequencetypedescriptions " +
                        "WHERE sqtypd_sqtyp_sequencetypeid = ? AND sqtypd_lang_languageid = ? AND sqtypd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = SequenceTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, sequenceType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            sequenceTypeDescription = SequenceTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return sequenceTypeDescription;
    }
    
    public SequenceTypeDescription getSequenceTypeDescription(SequenceType sequenceType, Language language) {
        return getSequenceTypeDescription(sequenceType, language, EntityPermission.READ_ONLY);
    }
    
    public SequenceTypeDescription getSequenceTypeDescriptionForUpdate(SequenceType sequenceType, Language language) {
        return getSequenceTypeDescription(sequenceType, language, EntityPermission.READ_WRITE);
    }
    
    public SequenceTypeDescriptionValue getSequenceTypeDescriptionValue(SequenceTypeDescription sequenceTypeDescription) {
        return sequenceTypeDescription == null? null: sequenceTypeDescription.getSequenceTypeDescriptionValue().clone();
    }
    
    public SequenceTypeDescriptionValue getSequenceTypeDescriptionValueForUpdate(SequenceType sequenceType, Language language) {
        return getSequenceTypeDescriptionValue(getSequenceTypeDescriptionForUpdate(sequenceType, language));
    }
    
    public String getBestSequenceTypeDescription(SequenceType sequenceType, Language language) {
        String description;
        var sequenceTypeDescription = getSequenceTypeDescription(sequenceType, language);
        
        if(sequenceTypeDescription == null && !language.getIsDefault()) {
            sequenceTypeDescription = getSequenceTypeDescription(sequenceType, getPartyControl().getDefaultLanguage());
        }
        
        if(sequenceTypeDescription == null) {
            description = sequenceType.getLastDetail().getSequenceTypeName();
        } else {
            description = sequenceTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public SequenceTypeDescriptionTransfer getSequenceTypeDescriptionTransfer(UserVisit userVisit, SequenceTypeDescription sequenceTypeDescription) {
        return getSequenceTransferCaches(userVisit).getSequenceTypeDescriptionTransferCache().getSequenceTypeDescriptionTransfer(sequenceTypeDescription);
    }
    
    public List<SequenceTypeDescriptionTransfer> getSequenceTypeDescriptionTransfers(UserVisit userVisit, SequenceType sequenceType) {
        var sequenceTypeDescriptions = getSequenceTypeDescriptionsBySequenceType(sequenceType);
        List<SequenceTypeDescriptionTransfer> sequenceTypeDescriptionTransfers = new ArrayList<>(sequenceTypeDescriptions.size());
        var sequenceTypeDescriptionTransferCache = getSequenceTransferCaches(userVisit).getSequenceTypeDescriptionTransferCache();
        
        sequenceTypeDescriptions.forEach((sequenceTypeDescription) ->
                sequenceTypeDescriptionTransfers.add(sequenceTypeDescriptionTransferCache.getSequenceTypeDescriptionTransfer(sequenceTypeDescription))
        );
        
        return sequenceTypeDescriptionTransfers;
    }
    
    public void updateSequenceTypeDescriptionFromValue(SequenceTypeDescriptionValue sequenceTypeDescriptionValue, BasePK updatedBy) {
        if(sequenceTypeDescriptionValue.hasBeenModified()) {
            var sequenceTypeDescription = SequenceTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, sequenceTypeDescriptionValue.getPrimaryKey());
            
            sequenceTypeDescription.setThruTime(session.START_TIME_LONG);
            sequenceTypeDescription.store();

            var sequenceType = sequenceTypeDescription.getSequenceType();
            var language = sequenceTypeDescription.getLanguage();
            var description = sequenceTypeDescriptionValue.getDescription();
            
            sequenceTypeDescription = SequenceTypeDescriptionFactory.getInstance().create(sequenceType, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(sequenceType.getPrimaryKey(), EventTypes.MODIFY, sequenceTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteSequenceTypeDescription(SequenceTypeDescription sequenceTypeDescription, BasePK deletedBy) {
        sequenceTypeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(sequenceTypeDescription.getSequenceTypePK(), EventTypes.MODIFY, sequenceTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteSequenceTypeDescriptionsBySequenceType(SequenceType sequenceType, BasePK deletedBy) {
        var sequenceTypeDescriptions = getSequenceTypeDescriptionsBySequenceTypeForUpdate(sequenceType);
        
        sequenceTypeDescriptions.forEach((sequenceTypeDescription) -> 
                deleteSequenceTypeDescription(sequenceTypeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Sequence Checksum Types
    // --------------------------------------------------------------------------------
    
    public SequenceChecksumType createSequenceChecksumType(String sequenceChecksumTypeName, Boolean isDefault, Integer sortOrder) {
        return SequenceChecksumTypeFactory.getInstance().create(sequenceChecksumTypeName, isDefault, sortOrder);
    }

    public long countSequenceChecksumTypes() {
        return session.queryForLong("SELECT COUNT(*) " +
                "FROM sequencechecksumtypes");
    }

    public List<SequenceChecksumType> getSequenceChecksumTypes() {
        var ps = SequenceChecksumTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM sequencechecksumtypes " +
                "ORDER BY sqct_sortorder, sqct_sequencechecksumtypename");
        
        return SequenceChecksumTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public SequenceChecksumType getSequenceChecksumTypeByName(String sequenceChecksumTypeName) {
        SequenceChecksumType sequenceChecksumType;
        
        try {
            var ps = SequenceChecksumTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM sequencechecksumtypes " +
                    "WHERE sqct_sequencechecksumtypename = ?");
            
            ps.setString(1, sequenceChecksumTypeName);
            
            sequenceChecksumType = SequenceChecksumTypeFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return sequenceChecksumType;
    }

    public SequenceChecksumTypeTransfer getSequenceChecksumTypeTransfer(UserVisit userVisit, SequenceChecksumType sequenceChecksumType) {
        return getSequenceTransferCaches(userVisit).getSequenceChecksumTypeTransferCache().getSequenceChecksumTypeTransfer(sequenceChecksumType);
    }

    public List<SequenceChecksumTypeTransfer> getSequenceChecksumTypeTransfers(UserVisit userVisit, Collection<SequenceChecksumType> sequenceChecksumTypes) {
        List<SequenceChecksumTypeTransfer> sequenceChecksumTypeTransfers = new ArrayList<>(sequenceChecksumTypes.size());
        var sequenceChecksumTypeTransferCache = getSequenceTransferCaches(userVisit).getSequenceChecksumTypeTransferCache();

        sequenceChecksumTypes.forEach((sequenceChecksumType) ->
                sequenceChecksumTypeTransfers.add(sequenceChecksumTypeTransferCache.getSequenceChecksumTypeTransfer(sequenceChecksumType))
        );

        return sequenceChecksumTypeTransfers;
    }

    public List<SequenceChecksumTypeTransfer> getSequenceChecksumTypeTransfers(UserVisit userVisit) {
        return getSequenceChecksumTypeTransfers(userVisit, getSequenceChecksumTypes());
    }

    public SequenceChecksumTypeChoicesBean getSequenceChecksumTypeChoices(String defaultSequenceChecksumTypeChoice,
            Language language, boolean allowNullChoice) {
        var sequenceChecksumTypes = getSequenceChecksumTypes();
        var size = sequenceChecksumTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultSequenceChecksumTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var sequenceChecksumType : sequenceChecksumTypes) {
            var label = getBestSequenceChecksumTypeDescription(sequenceChecksumType, language);
            var value = sequenceChecksumType.getSequenceChecksumTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultSequenceChecksumTypeChoice != null && defaultSequenceChecksumTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && sequenceChecksumType.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new SequenceChecksumTypeChoicesBean(labels, values, defaultValue);
    }
    
    // --------------------------------------------------------------------------------
    //   Sequence Checksum Type Descriptions
    // --------------------------------------------------------------------------------
    
    public SequenceChecksumTypeDescription createSequenceChecksumTypeDescription(SequenceChecksumType sequenceChecksumType, Language language, String description) {
        return SequenceChecksumTypeDescriptionFactory.getInstance().create(sequenceChecksumType, language, description);
    }
    
    public SequenceChecksumTypeDescription getSequenceChecksumTypeDescription(SequenceChecksumType sequenceChecksumType, Language language) {
        SequenceChecksumTypeDescription sequenceChecksumTypeDescription;
        
        try {
            var ps = SequenceChecksumTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM sequencechecksumtypedescriptions " +
                    "WHERE sqctd_sqct_sequencechecksumtypeid = ? AND sqctd_lang_languageid = ?");
            
            ps.setLong(1, sequenceChecksumType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            sequenceChecksumTypeDescription = SequenceChecksumTypeDescriptionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return sequenceChecksumTypeDescription;
    }
    
    public String getBestSequenceChecksumTypeDescription(SequenceChecksumType sequenceChecksumType, Language language) {
        String description;
        var sequenceChecksumTypeDescription = getSequenceChecksumTypeDescription(sequenceChecksumType, language);
        
        if(sequenceChecksumTypeDescription == null && !language.getIsDefault()) {
            sequenceChecksumTypeDescription = getSequenceChecksumTypeDescription(sequenceChecksumType, getPartyControl().getDefaultLanguage());
        }
        
        if(sequenceChecksumTypeDescription == null) {
            description = sequenceChecksumType.getSequenceChecksumTypeName();
        } else {
            description = sequenceChecksumTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Sequence Encoder Types
    // --------------------------------------------------------------------------------
    
    public SequenceEncoderType createSequenceEncoderType(String sequenceEncoderTypeName, Boolean isDefault, Integer sortOrder) {
        return SequenceEncoderTypeFactory.getInstance().create(sequenceEncoderTypeName, isDefault, sortOrder);
    }

    public long countSequenceEncoderTypes() {
        return session.queryForLong("SELECT COUNT(*) " +
                "FROM sequenceencodertypes");
    }

    public List<SequenceEncoderType> getSequenceEncoderTypes() {
        var ps = SequenceEncoderTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM sequenceencodertypes " +
                "ORDER BY sqet_sortorder, sqet_sequenceencodertypename");
        
        return SequenceEncoderTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public SequenceEncoderType getSequenceEncoderTypeByName(String sequenceEncoderTypeName) {
        SequenceEncoderType sequenceEncoderType;
        
        try {
            var ps = SequenceEncoderTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM sequenceencodertypes " +
                    "WHERE sqet_sequenceencodertypename = ?");
            
            ps.setString(1, sequenceEncoderTypeName);
            
            sequenceEncoderType = SequenceEncoderTypeFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return sequenceEncoderType;
    }

    public SequenceEncoderTypeTransfer getSequenceEncoderTypeTransfer(UserVisit userVisit, SequenceEncoderType sequenceEncoderType) {
        return getSequenceTransferCaches(userVisit).getSequenceEncoderTypeTransferCache().getSequenceEncoderTypeTransfer(sequenceEncoderType);
    }

    public List<SequenceEncoderTypeTransfer> getSequenceEncoderTypeTransfers(UserVisit userVisit, Collection<SequenceEncoderType> sequenceEncoderTypes) {
        List<SequenceEncoderTypeTransfer> sequenceEncoderTypeTransfers = new ArrayList<>(sequenceEncoderTypes.size());
        var sequenceEncoderTypeTransferCache = getSequenceTransferCaches(userVisit).getSequenceEncoderTypeTransferCache();

        sequenceEncoderTypes.forEach((sequenceEncoderType) ->
                sequenceEncoderTypeTransfers.add(sequenceEncoderTypeTransferCache.getSequenceEncoderTypeTransfer(sequenceEncoderType))
        );

        return sequenceEncoderTypeTransfers;
    }

    public List<SequenceEncoderTypeTransfer> getSequenceEncoderTypeTransfers(UserVisit userVisit) {
        return getSequenceEncoderTypeTransfers(userVisit, getSequenceEncoderTypes());
    }

    public SequenceEncoderTypeChoicesBean getSequenceEncoderTypeChoices(String defaultSequenceEncoderTypeChoice, Language language,
            boolean allowNullChoice) {
        var sequenceEncoderTypes = getSequenceEncoderTypes();
        var size = sequenceEncoderTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultSequenceEncoderTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var sequenceEncoderType : sequenceEncoderTypes) {
            var label = getBestSequenceEncoderTypeDescription(sequenceEncoderType, language);
            var value = sequenceEncoderType.getSequenceEncoderTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultSequenceEncoderTypeChoice != null && defaultSequenceEncoderTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && sequenceEncoderType.getIsDefault()))
                defaultValue = value;
        }
        
        return new SequenceEncoderTypeChoicesBean(labels, values, defaultValue);
    }
    
    // --------------------------------------------------------------------------------
    //   Sequence Encoder Type Descriptions
    // --------------------------------------------------------------------------------

    public SequenceEncoderTypeDescription createSequenceEncoderTypeDescription(SequenceEncoderType sequenceEncoderType, Language language, String description) {
        return SequenceEncoderTypeDescriptionFactory.getInstance().create(sequenceEncoderType, language, description);
    }
    
    public SequenceEncoderTypeDescription getSequenceEncoderTypeDescription(SequenceEncoderType sequenceEncoderType, Language language) {
        SequenceEncoderTypeDescription sequenceEncoderTypeDescription;
        
        try {
            var ps = SequenceEncoderTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM sequenceencodertypedescriptions " +
                    "WHERE sqetd_sqet_sequenceencodertypeid = ? AND sqetd_lang_languageid = ?");
            
            ps.setLong(1, sequenceEncoderType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            sequenceEncoderTypeDescription = SequenceEncoderTypeDescriptionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return sequenceEncoderTypeDescription;
    }
    
    public String getBestSequenceEncoderTypeDescription(SequenceEncoderType sequenceEncoderType, Language language) {
        String description;
        var sequenceEncoderTypeDescription = getSequenceEncoderTypeDescription(sequenceEncoderType, language);
        
        if(sequenceEncoderTypeDescription == null && !language.getIsDefault()) {
            sequenceEncoderTypeDescription = getSequenceEncoderTypeDescription(sequenceEncoderType, getPartyControl().getDefaultLanguage());
        }
        
        if(sequenceEncoderTypeDescription == null) {
            description = sequenceEncoderType.getSequenceEncoderTypeName();
        } else {
            description = sequenceEncoderTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Sequences
    // --------------------------------------------------------------------------------
    
    public Sequence createSequence(SequenceType sequenceType, String sequenceName, String mask, Integer chunkSize, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var defaultSequence = getDefaultSequence(sequenceType);
        var defaultFound = defaultSequence != null;
        
        if(defaultFound && isDefault) {
            var defaultSequenceDetailValue = getDefaultSequenceDetailValueForUpdate(sequenceType);
            
            defaultSequenceDetailValue.setIsDefault(false);
            updateSequenceFromValue(defaultSequenceDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var sequence = SequenceFactory.getInstance().create();
        var sequenceDetail = SequenceDetailFactory.getInstance().create(sequence, sequenceType, sequenceName,
                mask, chunkSize, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        sequence = SequenceFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, sequence.getPrimaryKey());
        sequence.setActiveDetail(sequenceDetail);
        sequence.setLastDetail(sequenceDetail);
        sequence.store();
        
        sendEvent(sequence.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return sequence;
    }

    public long countSequences() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM sequences, sequencedetails " +
                "WHERE sq_activedetailid = sqdt_sequencedetailid");
    }

    public long countSequencesBySequenceType(SequenceType sequenceType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM sequences, sequencedetails " +
                "WHERE sq_activedetailid = sqdt_sequencedetailid AND sqdt_sqtyp_sequencetypeid = ?",
                sequenceType);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.Sequence */
    public Sequence getSequenceByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new SequencePK(entityInstance.getEntityUniqueId());
        var sequence = SequenceFactory.getInstance().getEntityFromPK(entityPermission, pk);

        return sequence;
    }

    public Sequence getSequenceByEntityInstance(EntityInstance entityInstance) {
        return getSequenceByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public Sequence getSequenceByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getSequenceByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    private List<Sequence> getSequencesBySequenceType(SequenceType sequenceType, EntityPermission entityPermission) {
        List<Sequence> sequences;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM sequences, sequencedetails " +
                        "WHERE sq_activedetailid = sqdt_sequencedetailid AND sqdt_sqtyp_sequencetypeid = ? " +
                        "ORDER BY sqdt_sortorder, sqdt_sequencename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM sequences, sequencedetails " +
                        "WHERE sq_activedetailid = sqdt_sequencedetailid AND sqdt_sqtyp_sequencetypeid = ? " +
                        "FOR UPDATE";
            }

            var ps = SequenceFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, sequenceType.getPrimaryKey().getEntityId());
            
            sequences = SequenceFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return sequences;
    }
    
    public List<Sequence> getSequencesBySequenceType(SequenceType sequenceType) {
        return getSequencesBySequenceType(sequenceType, EntityPermission.READ_ONLY);
    }
    
    public List<Sequence> getSequencesBySequenceTypeForUpdate(SequenceType sequenceType) {
        return getSequencesBySequenceType(sequenceType, EntityPermission.READ_WRITE);
    }
    
    public Sequence getDefaultSequence(SequenceType sequenceType, EntityPermission entityPermission) {
        Sequence sequence;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM sequences, sequencedetails " +
                        "WHERE sq_activedetailid = sqdt_sequencedetailid AND sqdt_sqtyp_sequencetypeid = ? AND sqdt_isdefault = 1";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM sequences, sequencedetails " +
                        "WHERE sq_activedetailid = sqdt_sequencedetailid AND sqdt_sqtyp_sequencetypeid = ? AND sqdt_isdefault = 1 " +
                        "FOR UPDATE";
            }

            var ps = SequenceFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, sequenceType.getPrimaryKey().getEntityId());
            
            sequence = SequenceFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return sequence;
    }
    
    public Sequence getDefaultSequence(SequenceType sequenceType) {
        return getDefaultSequence(sequenceType, EntityPermission.READ_ONLY);
    }
    
    public Sequence getDefaultSequenceForUpdate(SequenceType sequenceType) {
        return getDefaultSequence(sequenceType, EntityPermission.READ_WRITE);
    }
    
    public SequenceDetailValue getDefaultSequenceDetailValueForUpdate(SequenceType sequenceType) {
        return getDefaultSequenceForUpdate(sequenceType).getLastDetailForUpdate().getSequenceDetailValue().clone();
    }
    
    public Sequence getDefaultSequenceUsingNames(String sequenceTypeName) {
        var sequenceType = getSequenceTypeByName(sequenceTypeName);
        Sequence sequence = null;
        
        if(sequenceType != null) {
            sequence = getDefaultSequence(sequenceType);
        }
        
        return sequence;
    }
    
    public Sequence getSequenceByName(SequenceType sequenceType, String sequenceName, EntityPermission entityPermission) {
        Sequence sequence;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM sequences, sequencedetails " +
                        "WHERE sq_activedetailid = sqdt_sequencedetailid AND sqdt_sqtyp_sequencetypeid = ? " +
                        "AND sqdt_sequencename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM sequences, sequencedetails " +
                        "WHERE sq_activedetailid = sqdt_sequencedetailid AND sqdt_sqtyp_sequencetypeid = ? " +
                        "AND sqdt_sequencename = ? " +
                        "FOR UPDATE";
            }

            var ps = SequenceFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, sequenceType.getPrimaryKey().getEntityId());
            ps.setString(2, sequenceName);
            
            sequence = SequenceFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return sequence;
    }
    
    public Sequence getSequenceByName(SequenceType sequenceType, String sequenceName) {
        return getSequenceByName(sequenceType, sequenceName, EntityPermission.READ_ONLY);
    }
    
    public Sequence getSequenceByNameForUpdate(SequenceType sequenceType, String sequenceName) {
        return getSequenceByName(sequenceType, sequenceName, EntityPermission.READ_WRITE);
    }
    
    public SequenceDetailValue getSequenceDetailValueForUpdate(Sequence sequence) {
        return sequence == null? null: sequence.getLastDetailForUpdate().getSequenceDetailValue().clone();
    }
    
    public SequenceDetailValue getSequenceDetailValueByNameForUpdate(SequenceType sequenceType, String sequenceName) {
        return getSequenceDetailValueForUpdate(getSequenceByNameForUpdate(sequenceType, sequenceName));
    }
    
    public SequenceChoicesBean getSequenceChoices(String defaultSequenceChoice, Language language, boolean allowNullChoice,
            SequenceType sequenceType) {
        var sequences = getSequencesBySequenceType(sequenceType);
        var size = sequences.size() + (allowNullChoice? 1: 0);
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultSequenceChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var sequence : sequences) {
            var sequenceDetail = sequence.getLastDetail();
            var label = getBestSequenceDescription(sequence, language);
            var value = sequenceDetail.getSequenceName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultSequenceChoice != null && defaultSequenceChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && sequenceDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new SequenceChoicesBean(labels, values, defaultValue);
    }
    
    public SequenceTransfer getSequenceTransfer(UserVisit userVisit, Sequence sequence) {
        return getSequenceTransferCaches(userVisit).getSequenceTransferCache().getSequenceTransfer(sequence);
    }

    public List<SequenceTransfer> getSequenceTransfers(UserVisit userVisit, Collection<Sequence> sequences) {
        var sequenceTransfers = new ArrayList<SequenceTransfer>(sequences.size());
        var sequenceTransferCache = getSequenceTransferCaches(userVisit).getSequenceTransferCache();

        sequences.forEach((sequence) ->
            sequenceTransfers.add(sequenceTransferCache.getSequenceTransfer(sequence))
        );

        return sequenceTransfers;
    }

    public List<SequenceTransfer> getSequenceTransfersBySequenceType(UserVisit userVisit, SequenceType sequenceType) {
        return getSequenceTransfers(userVisit, getSequencesBySequenceType(sequenceType));
    }

    private void updateSequenceFromValue(SequenceDetailValue sequenceDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        var sequence = SequenceFactory.getInstance().getEntityFromPK(session,
                EntityPermission.READ_WRITE, sequenceDetailValue.getSequencePK());
        var sequenceDetail = sequence.getActiveDetailForUpdate();
        
        sequenceDetail.setThruTime(session.START_TIME_LONG);
        sequenceDetail.store();

        var sequencePK = sequenceDetail.getSequencePK();
        var sequenceTypePK = sequenceDetail.getSequenceTypePK();
        var sequenceType = sequenceDetail.getSequenceType();
        var sequenceName = sequenceDetailValue.getSequenceName();
        var mask = sequenceDetailValue.getMask();
        var chunkSize = sequenceDetailValue.getChunkSize();
        var isDefault = sequenceDetailValue.getIsDefault();
        var sortOrder = sequenceDetailValue.getSortOrder();
        
        if(checkDefault) {
            var defaultSequence = getDefaultSequence(sequenceType);
            var defaultFound = defaultSequence != null && !defaultSequence.equals(sequence);
            
            if(isDefault && defaultFound) {
                // If I'm the default, and a default already existed...
                var defaultSequenceDetailValue = getDefaultSequenceDetailValueForUpdate(sequenceType);
                
                defaultSequenceDetailValue.setIsDefault(false);
                updateSequenceFromValue(defaultSequenceDetailValue, false, updatedBy);
            } else if(!isDefault && !defaultFound) {
                // If I'm not the default, and no other default exists...
                isDefault = true;
            }
        }
        
        sequenceDetail = SequenceDetailFactory.getInstance().create(sequencePK, sequenceTypePK, sequenceName, mask,
                chunkSize, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sequence.setActiveDetail(sequenceDetail);
        sequence.setLastDetail(sequenceDetail);
        sequence.store();
        
        sendEvent(sequencePK, EventTypes.MODIFY, null, null, updatedBy);
    }
    
    public void updateSequenceFromValue(SequenceDetailValue sequenceDetailValue, BasePK updatedBy) {
        updateSequenceFromValue(sequenceDetailValue, true, updatedBy);
    }
    
    public void deleteSequence(Sequence sequence, BasePK deletedBy) {
        var sequenceDetail = sequence.getLastDetailForUpdate();
        var sequenceTypeName = sequenceDetail.getSequenceType().getLastDetail().getSequenceTypeName();
        
        if(SequenceTypes.SALES_ORDER.name().equals(sequenceTypeName)) {
            var offerUseControl = Session.getModelController(OfferUseControl.class);

            offerUseControl.deleteOfferUsesBySalesOrderSequence(sequence, deletedBy);
        }
        
        deleteSequenceDescriptionsBySequence(sequence, deletedBy);
        
        sequenceDetail.setThruTime(session.START_TIME_LONG);
        sequence.setActiveDetail(null);
        sequence.store();
        
        // Check for default, and pick one if necessary
        var sequenceType = sequenceDetail.getSequenceType();
        var defaultSequence = getDefaultSequence(sequenceType);
        if(defaultSequence == null) {
            var sequences = getSequencesBySequenceTypeForUpdate(sequenceType);
            
            if(!sequences.isEmpty()) {
                var iter = sequences.iterator();
                if(iter.hasNext()) {
                    defaultSequence = iter.next();
                }
                var sequenceDetailValue = Objects.requireNonNull(defaultSequence).getLastDetailForUpdate().getSequenceDetailValue().clone();
                
                sequenceDetailValue.setIsDefault(true);
                updateSequenceFromValue(sequenceDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(sequence.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteSequencesBySequenceType(SequenceType sequenceType, BasePK deletedBy) {
        var sequences = getSequencesBySequenceTypeForUpdate(sequenceType);
        
        sequences.forEach((sequence) -> 
                deleteSequence(sequence, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Sequence Descriptions
    // --------------------------------------------------------------------------------

    public SequenceDescription createSequenceDescription(Sequence sequence, Language language, String description, BasePK createdBy) {
        var sequenceDescription = SequenceDescriptionFactory.getInstance().create(sequence, language,
                description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(sequence.getPrimaryKey(), EventTypes.MODIFY, sequenceDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return sequenceDescription;
    }
    
    private List<SequenceDescription> getSequenceDescriptionsBySequence(Sequence sequence, EntityPermission entityPermission) {
        List<SequenceDescription> sequenceDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM sequencedescriptions, languages " +
                        "WHERE sqd_sq_sequenceid = ? AND sqd_thrutime = ? AND sqd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM sequencedescriptions " +
                        "WHERE sqd_sq_sequenceid = ? AND sqd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = SequenceDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, sequence.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            sequenceDescriptions = SequenceDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return sequenceDescriptions;
    }
    
    public List<SequenceDescription> getSequenceDescriptionsBySequence(Sequence sequence) {
        return getSequenceDescriptionsBySequence(sequence, EntityPermission.READ_ONLY);
    }
    
    public List<SequenceDescription> getSequenceDescriptionsBySequenceForUpdate(Sequence sequence) {
        return getSequenceDescriptionsBySequence(sequence, EntityPermission.READ_WRITE);
    }
    
    private SequenceDescription getSequenceDescription(Sequence sequence, Language language, EntityPermission entityPermission) {
        SequenceDescription sequenceDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM sequencedescriptions " +
                        "WHERE sqd_sq_sequenceid = ? AND sqd_lang_languageid = ? AND sqd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM sequencedescriptions " +
                        "WHERE sqd_sq_sequenceid = ? AND sqd_lang_languageid = ? AND sqd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = SequenceDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, sequence.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            sequenceDescription = SequenceDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return sequenceDescription;
    }
    
    public SequenceDescription getSequenceDescription(Sequence sequence, Language language) {
        return getSequenceDescription(sequence, language, EntityPermission.READ_ONLY);
    }
    
    public SequenceDescription getSequenceDescriptionForUpdate(Sequence sequence, Language language) {
        return getSequenceDescription(sequence, language, EntityPermission.READ_WRITE);
    }
    
    public SequenceDescriptionValue getSequenceDescriptionValue(SequenceDescription sequenceDescription) {
        return sequenceDescription == null? null: sequenceDescription.getSequenceDescriptionValue().clone();
    }
    
    public SequenceDescriptionValue getSequenceDescriptionValueForUpdate(Sequence sequence, Language language) {
        return getSequenceDescriptionValue(getSequenceDescriptionForUpdate(sequence, language));
    }
    
    public String getBestSequenceDescription(Sequence sequence, Language language) {
        String description;
        var sequenceDescription = getSequenceDescription(sequence, language);
        
        if(sequenceDescription == null && !language.getIsDefault()) {
            sequenceDescription = getSequenceDescription(sequence, getPartyControl().getDefaultLanguage());
        }
        
        if(sequenceDescription == null) {
            description = sequence.getLastDetail().getSequenceName();
        } else {
            description = sequenceDescription.getDescription();
        }
        
        return description;
    }
    
    public SequenceDescriptionTransfer getSequenceDescriptionTransfer(UserVisit userVisit, SequenceDescription sequenceDescription) {
        return getSequenceTransferCaches(userVisit).getSequenceDescriptionTransferCache().getSequenceDescriptionTransfer(sequenceDescription);
    }
    
    public List<SequenceDescriptionTransfer> getSequenceDescriptionTransfers(UserVisit userVisit, Sequence sequence) {
        var sequenceDescriptions = getSequenceDescriptionsBySequence(sequence);
        List<SequenceDescriptionTransfer> sequenceDescriptionTransfers = new ArrayList<>(sequenceDescriptions.size());
        var sequenceDescriptionTransferCache = getSequenceTransferCaches(userVisit).getSequenceDescriptionTransferCache();
        
        sequenceDescriptions.forEach((sequenceDescription) ->
                sequenceDescriptionTransfers.add(sequenceDescriptionTransferCache.getSequenceDescriptionTransfer(sequenceDescription))
        );
        
        return sequenceDescriptionTransfers;
    }
    
    public void updateSequenceDescriptionFromValue(SequenceDescriptionValue sequenceDescriptionValue, BasePK updatedBy) {
        if(sequenceDescriptionValue.hasBeenModified()) {
            var sequenceDescription = SequenceDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, sequenceDescriptionValue.getPrimaryKey());
            
            sequenceDescription.setThruTime(session.START_TIME_LONG);
            sequenceDescription.store();

            var sequence = sequenceDescription.getSequence();
            var language = sequenceDescription.getLanguage();
            var description = sequenceDescriptionValue.getDescription();
            
            sequenceDescription = SequenceDescriptionFactory.getInstance().create(sequence, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(sequence.getPrimaryKey(), EventTypes.MODIFY, sequenceDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteSequenceDescription(SequenceDescription sequenceDescription, BasePK deletedBy) {
        sequenceDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(sequenceDescription.getSequencePK(), EventTypes.MODIFY, sequenceDescription.getPrimaryKey(), null, deletedBy);
    }
    
    public void deleteSequenceDescriptionsBySequence(Sequence sequence, BasePK deletedBy) {
        var sequenceDescriptions = getSequenceDescriptionsBySequenceForUpdate(sequence);
        
        sequenceDescriptions.forEach((sequenceDescription) -> 
                deleteSequenceDescription(sequenceDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Sequence Values
    // --------------------------------------------------------------------------------

    public SequenceValue createSequenceValue(Sequence sequence, String value) {
        return SequenceValueFactory.getInstance().create(sequence, value);
    }
    
    public SequenceValue getSequenceValue(Sequence sequence) {
        SequenceValue sequenceValue;
        
        try {
            var ps = SequenceValueFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM sequencevalues " +
                    "WHERE sqv_sq_sequenceid = ?");
            
            ps.setLong(1, sequence.getPrimaryKey().getEntityId());
            
            sequenceValue = SequenceValueFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return sequenceValue;
    }
    
    public SequenceValue getSequenceValueForUpdateInSession(Session sequenceSession, Sequence sequence) {
        SequenceValue sequenceValue;
        
        try {
            var ps = sequenceSession.prepareStatement(SequenceValueFactory.class,
                    "SELECT _ALL_ " +
                    "FROM sequencevalues " +
                    "WHERE sqv_sq_sequenceid = ? " +
                    "FOR UPDATE");
            
            ps.setLong(1, sequence.getPrimaryKey().getEntityId());
            
            sequenceValue = SequenceValueFactory.getInstance().getEntityFromQuery(sequenceSession, EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return sequenceValue;
    }
    
    public SequenceValue getSequenceValueForUpdate(Sequence sequence) {
        return getSequenceValueForUpdateInSession(session, sequence);
    }
    
}
