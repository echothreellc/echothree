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

package com.echothree.model.control.workeffort.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.workeffort.common.choice.WorkEffortScopeChoicesBean;
import com.echothree.model.control.workeffort.common.transfer.WorkEffortScopeDescriptionTransfer;
import com.echothree.model.control.workeffort.common.transfer.WorkEffortScopeTransfer;
import com.echothree.model.control.workeffort.common.transfer.WorkEffortTransfer;
import com.echothree.model.control.workeffort.common.transfer.WorkEffortTypeDescriptionTransfer;
import com.echothree.model.control.workeffort.common.transfer.WorkEffortTypeTransfer;
import com.echothree.model.control.workeffort.server.transfer.WorkEffortScopeDescriptionTransferCache;
import com.echothree.model.control.workeffort.server.transfer.WorkEffortScopeTransferCache;
import com.echothree.model.control.workeffort.server.transfer.WorkEffortTransferCache;
import com.echothree.model.control.workeffort.server.transfer.WorkEffortTransferCaches;
import com.echothree.model.control.workeffort.server.transfer.WorkEffortTypeDescriptionTransferCache;
import com.echothree.model.control.workeffort.server.transfer.WorkEffortTypeTransferCache;
import com.echothree.model.control.workrequirement.server.control.WorkRequirementControl;
import com.echothree.model.data.core.common.pk.EntityInstancePK;
import com.echothree.model.data.core.common.pk.EntityTypePK;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.sequence.common.pk.SequencePK;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workeffort.common.pk.WorkEffortPK;
import com.echothree.model.data.workeffort.common.pk.WorkEffortScopePK;
import com.echothree.model.data.workeffort.common.pk.WorkEffortTypePK;
import com.echothree.model.data.workeffort.server.entity.WorkEffort;
import com.echothree.model.data.workeffort.server.entity.WorkEffortDetail;
import com.echothree.model.data.workeffort.server.entity.WorkEffortScope;
import com.echothree.model.data.workeffort.server.entity.WorkEffortScopeDescription;
import com.echothree.model.data.workeffort.server.entity.WorkEffortScopeDetail;
import com.echothree.model.data.workeffort.server.entity.WorkEffortType;
import com.echothree.model.data.workeffort.server.entity.WorkEffortTypeDescription;
import com.echothree.model.data.workeffort.server.entity.WorkEffortTypeDetail;
import com.echothree.model.data.workeffort.server.factory.WorkEffortDetailFactory;
import com.echothree.model.data.workeffort.server.factory.WorkEffortFactory;
import com.echothree.model.data.workeffort.server.factory.WorkEffortScopeDescriptionFactory;
import com.echothree.model.data.workeffort.server.factory.WorkEffortScopeDetailFactory;
import com.echothree.model.data.workeffort.server.factory.WorkEffortScopeFactory;
import com.echothree.model.data.workeffort.server.factory.WorkEffortTypeDescriptionFactory;
import com.echothree.model.data.workeffort.server.factory.WorkEffortTypeDetailFactory;
import com.echothree.model.data.workeffort.server.factory.WorkEffortTypeFactory;
import com.echothree.model.data.workeffort.server.value.WorkEffortDetailValue;
import com.echothree.model.data.workeffort.server.value.WorkEffortScopeDescriptionValue;
import com.echothree.model.data.workeffort.server.value.WorkEffortScopeDetailValue;
import com.echothree.model.data.workeffort.server.value.WorkEffortTypeDescriptionValue;
import com.echothree.model.data.workeffort.server.value.WorkEffortTypeDetailValue;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WorkEffortControl
        extends BaseModelControl {
    
    /** Creates a new instance of WorkEffortControl */
    public WorkEffortControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Work Effort Transfer Caches
    // --------------------------------------------------------------------------------
    
    private WorkEffortTransferCaches workEffortTransferCaches;
    
    public WorkEffortTransferCaches getWorkEffortTransferCaches(UserVisit userVisit) {
        if(workEffortTransferCaches == null) {
            workEffortTransferCaches = new WorkEffortTransferCaches(userVisit, this);
        }
        
        return workEffortTransferCaches;
    }
    
    // --------------------------------------------------------------------------------
    //   Work Effort Types
    // --------------------------------------------------------------------------------
    
    public WorkEffortType createWorkEffortType(String workEffortTypeName, EntityType entityType, Sequence workEffortSequence, Long scheduledTime,
            Long estimatedTimeAllowed, Long maximumTimeAllowed, Integer sortOrder, BasePK createdBy) {
        WorkEffortType workEffortType = WorkEffortTypeFactory.getInstance().create();
        WorkEffortTypeDetail workEffortTypeDetail = WorkEffortTypeDetailFactory.getInstance().create(workEffortType, workEffortTypeName, entityType,
                workEffortSequence, scheduledTime, estimatedTimeAllowed, maximumTimeAllowed, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        workEffortType = WorkEffortTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, workEffortType.getPrimaryKey());
        workEffortType.setActiveDetail(workEffortTypeDetail);
        workEffortType.setLastDetail(workEffortTypeDetail);
        workEffortType.store();
        
        sendEventUsingNames(workEffortType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return workEffortType;
    }
    
    private List<WorkEffortType> getWorkEffortTypes(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM workefforttypes, workefforttypedetails " +
                    "WHERE wet_activedetailid = wetdt_workefforttypedetailid " +
                    "ORDER BY wetdt_sortorder, wetdt_workefforttypename";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM workefforttypes, workeffortscopedetails " +
                    "WHERE wet_activedetailid = wetdt_workefforttypedetailid " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = WorkEffortTypeFactory.getInstance().prepareStatement(query);
        
        return WorkEffortTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<WorkEffortType> getWorkEffortTypes() {
        return getWorkEffortTypes(EntityPermission.READ_ONLY);
    }
    
    public List<WorkEffortType> getWorkEffortTypesForUpdate() {
        return getWorkEffortTypes(EntityPermission.READ_WRITE);
    }
    
    private WorkEffortType getWorkEffortTypeByName(String workEffortTypeName, EntityPermission entityPermission) {
        WorkEffortType workEffortType;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM workefforttypes, workefforttypedetails " +
                        "WHERE wet_activedetailid = wetdt_workefforttypedetailid " +
                        "AND wetdt_workefforttypename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM workefforttypes, workefforttypedetails " +
                        "WHERE wet_activedetailid = wetdt_workefforttypedetailid " +
                        "AND wetdt_workefforttypename = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = WorkEffortTypeFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, workEffortTypeName);
            
            workEffortType = WorkEffortTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return workEffortType;
    }
    
    public WorkEffortType getWorkEffortTypeByName(String workEffortTypeName) {
        return getWorkEffortTypeByName(workEffortTypeName, EntityPermission.READ_ONLY);
    }
    
    public WorkEffortType getWorkEffortTypeByNameForUpdate(String workEffortTypeName) {
        return getWorkEffortTypeByName(workEffortTypeName, EntityPermission.READ_WRITE);
    }
    
    public WorkEffortTypeDetailValue getWorkEffortTypeDetailValueForUpdate(WorkEffortType workEffortType) {
        return workEffortType == null? null: workEffortType.getLastDetailForUpdate().getWorkEffortTypeDetailValue().clone();
    }
    
    public WorkEffortTypeDetailValue getWorkEffortTypeDetailValueByNameForUpdate(String workEffortTypeName) {
        return getWorkEffortTypeDetailValueForUpdate(getWorkEffortTypeByNameForUpdate(workEffortTypeName));
    }
    
    public WorkEffortTypeTransfer getWorkEffortTypeTransfer(UserVisit userVisit, WorkEffortType workEffortType) {
        return getWorkEffortTransferCaches(userVisit).getWorkEffortTypeTransferCache().getWorkEffortTypeTransfer(workEffortType);
    }
    
    public List<WorkEffortTypeTransfer> getWorkEffortTypeTransfers(UserVisit userVisit) {
        List<WorkEffortType> workEffortTypes = getWorkEffortTypes();
        List<WorkEffortTypeTransfer> workEffortTypeTransfers = new ArrayList<>(workEffortTypes.size());
        WorkEffortTypeTransferCache workEffortTypeTransferCache = getWorkEffortTransferCaches(userVisit).getWorkEffortTypeTransferCache();
        
        workEffortTypes.forEach((workEffortType) ->
                workEffortTypeTransfers.add(workEffortTypeTransferCache.getWorkEffortTypeTransfer(workEffortType))
        );
        
        return workEffortTypeTransfers;
    }
    
    public void updateWorkEffortTypeFromValue(WorkEffortTypeDetailValue workEffortTypeDetailValue, BasePK updatedBy) {
        if(workEffortTypeDetailValue.hasBeenModified()) {
            WorkEffortType workEffortType = WorkEffortTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     workEffortTypeDetailValue.getWorkEffortTypePK());
            WorkEffortTypeDetail workEffortTypeDetail = workEffortType.getActiveDetailForUpdate();
            
            workEffortTypeDetail.setThruTime(session.START_TIME_LONG);
            workEffortTypeDetail.store();
            
            WorkEffortTypePK workEffortTypePK = workEffortTypeDetail.getWorkEffortTypePK();
            String workEffortTypeName = workEffortTypeDetailValue.getWorkEffortTypeName();
            EntityTypePK entityTypePK = workEffortTypeDetailValue.getEntityTypePK();
            SequencePK workEffortSequencePK = workEffortTypeDetailValue.getWorkEffortSequencePK();
            Long scheduledTime = workEffortTypeDetailValue.getScheduledTime();
            Long estimatedTimeAllowed = workEffortTypeDetailValue.getEstimatedTimeAllowed();
            Long maximumTimeAllowed = workEffortTypeDetailValue.getMaximumTimeAllowed();
            Integer sortOrder = workEffortTypeDetailValue.getSortOrder();
            
            workEffortTypeDetail = WorkEffortTypeDetailFactory.getInstance().create(workEffortTypePK, workEffortTypeName, entityTypePK, workEffortSequencePK,
                    scheduledTime, estimatedTimeAllowed, maximumTimeAllowed, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            workEffortType.setActiveDetail(workEffortTypeDetail);
            workEffortType.setLastDetail(workEffortTypeDetail);
            
            sendEventUsingNames(workEffortTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void deleteWorkEffortType(WorkEffortType workEffortType, BasePK deletedBy) {
        var workRequirementControl = (WorkRequirementControl)Session.getModelController(WorkRequirementControl.class);
        
        deleteWorkEffortScopesByWorkEffortType(workEffortType, deletedBy);
        workRequirementControl.deleteWorkRequirementTypesByWorkEffortType(workEffortType, deletedBy);
        deleteWorkEffortTypeDescriptionsByWorkEffortType(workEffortType, deletedBy);
        
        WorkEffortTypeDetail workEffortTypeDetail = workEffortType.getLastDetailForUpdate();
        workEffortTypeDetail.setThruTime(session.START_TIME_LONG);
        workEffortType.setActiveDetail(null);
        workEffortType.store();
        
        sendEventUsingNames(workEffortType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Work Effort Type Descriptions
    // --------------------------------------------------------------------------------
    
    public WorkEffortTypeDescription createWorkEffortTypeDescription(WorkEffortType workEffortType, Language language,
            String description, BasePK createdBy) {
        WorkEffortTypeDescription workEffortTypeDescription = WorkEffortTypeDescriptionFactory.getInstance().create(workEffortType,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(workEffortType.getPrimaryKey(), EventTypes.MODIFY.name(), workEffortTypeDescription.getPrimaryKey(),
                null, createdBy);
        
        return workEffortTypeDescription;
    }
    
    private WorkEffortTypeDescription getWorkEffortTypeDescription(WorkEffortType workEffortType, Language language,
            EntityPermission entityPermission) {
        WorkEffortTypeDescription workEffortTypeDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM workefforttypedescriptions " +
                        "WHERE wetd_wet_workefforttypeid = ? AND wetd_lang_languageid = ? AND wetd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM workefforttypedescriptions " +
                        "WHERE wetd_wet_workefforttypeid = ? AND wetd_lang_languageid = ? AND wetd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = WorkEffortTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, workEffortType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            workEffortTypeDescription = WorkEffortTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return workEffortTypeDescription;
    }
    
    public WorkEffortTypeDescription getWorkEffortTypeDescription(WorkEffortType workEffortType, Language language) {
        return getWorkEffortTypeDescription(workEffortType, language, EntityPermission.READ_ONLY);
    }
    
    public WorkEffortTypeDescription getWorkEffortTypeDescriptionForUpdate(WorkEffortType workEffortType, Language language) {
        return getWorkEffortTypeDescription(workEffortType, language, EntityPermission.READ_WRITE);
    }
    
    public WorkEffortTypeDescriptionValue getWorkEffortTypeDescriptionValue(WorkEffortTypeDescription workEffortTypeDescription) {
        return workEffortTypeDescription == null? null: workEffortTypeDescription.getWorkEffortTypeDescriptionValue().clone();
    }
    
    public WorkEffortTypeDescriptionValue getWorkEffortTypeDescriptionValueForUpdate(WorkEffortType workEffortType, Language language) {
        return getWorkEffortTypeDescriptionValue(getWorkEffortTypeDescriptionForUpdate(workEffortType, language));
    }
    
    private List<WorkEffortTypeDescription> getWorkEffortTypeDescriptionsByWorkEffortType(WorkEffortType workEffortType, EntityPermission entityPermission) {
        List<WorkEffortTypeDescription> workEffortTypeDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM workefforttypedescriptions, languages " +
                        "WHERE wetd_wet_workefforttypeid = ? AND wetd_thrutime = ? AND wetd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM workefforttypedescriptions " +
                        "WHERE wetd_wet_workefforttypeid = ? AND wetd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = WorkEffortTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, workEffortType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            workEffortTypeDescriptions = WorkEffortTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return workEffortTypeDescriptions;
    }
    
    public List<WorkEffortTypeDescription> getWorkEffortTypeDescriptionsByWorkEffortType(WorkEffortType workEffortType) {
        return getWorkEffortTypeDescriptionsByWorkEffortType(workEffortType, EntityPermission.READ_ONLY);
    }
    
    public List<WorkEffortTypeDescription> getWorkEffortTypeDescriptionsByWorkEffortTypeForUpdate(WorkEffortType workEffortType) {
        return getWorkEffortTypeDescriptionsByWorkEffortType(workEffortType, EntityPermission.READ_WRITE);
    }
    
    public String getBestWorkEffortTypeDescription(WorkEffortType workEffortType, Language language) {
        String description;
        WorkEffortTypeDescription workEffortTypeDescription = getWorkEffortTypeDescription(workEffortType, language);
        
        if(workEffortTypeDescription == null && !language.getIsDefault()) {
            workEffortTypeDescription = getWorkEffortTypeDescription(workEffortType, getPartyControl().getDefaultLanguage());
        }
        
        if(workEffortTypeDescription == null) {
            description = workEffortType.getLastDetail().getWorkEffortTypeName();
        } else {
            description = workEffortTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public WorkEffortTypeDescriptionTransfer getWorkEffortTypeDescriptionTransfer(UserVisit userVisit, WorkEffortTypeDescription workEffortTypeDescription) {
        return getWorkEffortTransferCaches(userVisit).getWorkEffortTypeDescriptionTransferCache().getWorkEffortTypeDescriptionTransfer(workEffortTypeDescription);
    }
    
    public List<WorkEffortTypeDescriptionTransfer> getWorkEffortTypeDescriptionTransfers(UserVisit userVisit, WorkEffortType workEffortType) {
        List<WorkEffortTypeDescription> workEffortTypeDescriptions = getWorkEffortTypeDescriptionsByWorkEffortType(workEffortType);
        List<WorkEffortTypeDescriptionTransfer> workEffortTypeDescriptionTransfers = new ArrayList<>(workEffortTypeDescriptions.size());
        WorkEffortTypeDescriptionTransferCache workEffortTypeDescriptionTransferCache = getWorkEffortTransferCaches(userVisit).getWorkEffortTypeDescriptionTransferCache();
        
        workEffortTypeDescriptions.forEach((workEffortTypeDescription) ->
                workEffortTypeDescriptionTransfers.add(workEffortTypeDescriptionTransferCache.getWorkEffortTypeDescriptionTransfer(workEffortTypeDescription))
        );
        
        return workEffortTypeDescriptionTransfers;
    }
    
    public void updateWorkEffortTypeDescriptionFromValue(WorkEffortTypeDescriptionValue workEffortTypeDescriptionValue, BasePK updatedBy) {
        if(workEffortTypeDescriptionValue.hasBeenModified()) {
            WorkEffortTypeDescription workEffortTypeDescription = WorkEffortTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     workEffortTypeDescriptionValue.getPrimaryKey());
            
            workEffortTypeDescription.setThruTime(session.START_TIME_LONG);
            workEffortTypeDescription.store();
            
            WorkEffortType workEffortType = workEffortTypeDescription.getWorkEffortType();
            Language language = workEffortTypeDescription.getLanguage();
            String description = workEffortTypeDescriptionValue.getDescription();
            
            workEffortTypeDescription = WorkEffortTypeDescriptionFactory.getInstance().create(workEffortType, language,
                    description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(workEffortType.getPrimaryKey(), EventTypes.MODIFY.name(), workEffortTypeDescription.getPrimaryKey(),
                    null, updatedBy);
        }
    }
    
    public void deleteWorkEffortTypeDescription(WorkEffortTypeDescription workEffortTypeDescription, BasePK deletedBy) {
        workEffortTypeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(workEffortTypeDescription.getWorkEffortTypePK(), EventTypes.MODIFY.name(),
                workEffortTypeDescription.getPrimaryKey(), null, deletedBy);
    }
    
    public void deleteWorkEffortTypeDescriptionsByWorkEffortType(WorkEffortType workEffortType, BasePK deletedBy) {
        List<WorkEffortTypeDescription> workEffortTypeDescriptions = getWorkEffortTypeDescriptionsByWorkEffortTypeForUpdate(workEffortType);
        
        workEffortTypeDescriptions.forEach((workEffortTypeDescription) -> 
                deleteWorkEffortTypeDescription(workEffortTypeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Work Effort Scopes
    // --------------------------------------------------------------------------------
    
    public WorkEffortScope createWorkEffortScope(WorkEffortType workEffortType, String workEffortScopeName,
            Sequence workEffortSequence, Long scheduledTime, Long estimatedTimeAllowed, Long maximumTimeAllowed, Boolean isDefault,
            Integer sortOrder, BasePK createdBy) {
        WorkEffortScope defaultWorkEffortScope = getDefaultWorkEffortScope(workEffortType);
        boolean defaultFound = defaultWorkEffortScope != null;
        
        if(defaultFound && isDefault) {
            WorkEffortScopeDetailValue defaultWorkEffortScopeDetailValue = getDefaultWorkEffortScopeDetailValueForUpdate(workEffortType);
            
            defaultWorkEffortScopeDetailValue.setIsDefault(Boolean.FALSE);
            updateWorkEffortScopeFromValue(defaultWorkEffortScopeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        WorkEffortScope workEffortScope = WorkEffortScopeFactory.getInstance().create();
        WorkEffortScopeDetail workEffortScopeDetail = WorkEffortScopeDetailFactory.getInstance().create(workEffortScope,
                workEffortType, workEffortScopeName, workEffortSequence, scheduledTime, estimatedTimeAllowed, maximumTimeAllowed,
                isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        workEffortScope = WorkEffortScopeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                workEffortScope.getPrimaryKey());
        workEffortScope.setActiveDetail(workEffortScopeDetail);
        workEffortScope.setLastDetail(workEffortScopeDetail);
        workEffortScope.store();
        
        sendEventUsingNames(workEffortScope.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return workEffortScope;
    }
    
    private List<WorkEffortScope> getWorkEffortScopes(WorkEffortType workEffortType, EntityPermission entityPermission) {
        List<WorkEffortScope> workEffortScopes;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM workeffortscopes, workeffortscopedetails " +
                        "WHERE wes_activedetailid = wesdt_workeffortscopedetailid AND wesdt_wet_workefforttypeid = ? " +
                        "ORDER BY wesdt_sortorder, wesdt_workeffortscopename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM workeffortscopes, workeffortscopedetails " +
                        "WHERE wes_activedetailid = wesdt_workeffortscopedetailid AND wesdt_wet_workefforttypeid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = WorkEffortScopeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, workEffortType.getPrimaryKey().getEntityId());
            
            workEffortScopes = WorkEffortScopeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return workEffortScopes;
    }
    
    public List<WorkEffortScope> getWorkEffortScopes(WorkEffortType workEffortType) {
        return getWorkEffortScopes(workEffortType, EntityPermission.READ_ONLY);
    }
    
    public List<WorkEffortScope> getWorkEffortScopesForUpdate(WorkEffortType workEffortType) {
        return getWorkEffortScopes(workEffortType, EntityPermission.READ_WRITE);
    }
    
    private WorkEffortScope getDefaultWorkEffortScope(WorkEffortType workEffortType, EntityPermission entityPermission) {
        WorkEffortScope workEffortScope;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM workeffortscopes, workeffortscopedetails " +
                        "WHERE wes_activedetailid = wesdt_workeffortscopedetailid " +
                        "AND wesdt_wet_workefforttypeid = ? AND wesdt_isdefault = 1";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM workeffortscopes, workeffortscopedetails " +
                        "WHERE wes_activedetailid = wesdt_workeffortscopedetailid " +
                        "AND wesdt_wet_workefforttypeid = ? AND wesdt_isdefault = 1 " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = WorkEffortScopeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, workEffortType.getPrimaryKey().getEntityId());
            
            workEffortScope = WorkEffortScopeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return workEffortScope;
    }
    
    public WorkEffortScope getDefaultWorkEffortScope(WorkEffortType workEffortType) {
        return getDefaultWorkEffortScope(workEffortType, EntityPermission.READ_ONLY);
    }
    
    public WorkEffortScope getDefaultWorkEffortScopeForUpdate(WorkEffortType workEffortType) {
        return getDefaultWorkEffortScope(workEffortType, EntityPermission.READ_WRITE);
    }
    
    public WorkEffortScopeDetailValue getDefaultWorkEffortScopeDetailValueForUpdate(WorkEffortType workEffortType) {
        return getDefaultWorkEffortScopeForUpdate(workEffortType).getLastDetailForUpdate().getWorkEffortScopeDetailValue().clone();
    }
    
    private WorkEffortScope getWorkEffortScopeByName(WorkEffortType workEffortType, String workEffortScopeName, EntityPermission entityPermission) {
        WorkEffortScope workEffortScope;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM workeffortscopes, workeffortscopedetails " +
                        "WHERE wes_activedetailid = wesdt_workeffortscopedetailid " +
                        "AND wesdt_wet_workefforttypeid = ? AND wesdt_workeffortscopename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM workeffortscopes, workeffortscopedetails " +
                        "WHERE wes_activedetailid = wesdt_workeffortscopedetailid " +
                        "AND wesdt_wet_workefforttypeid = ? AND wesdt_workeffortscopename = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = WorkEffortScopeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, workEffortType.getPrimaryKey().getEntityId());
            ps.setString(2, workEffortScopeName);
            
            workEffortScope = WorkEffortScopeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return workEffortScope;
    }
    
    public WorkEffortScope getWorkEffortScopeByName(WorkEffortType workEffortType, String workEffortScopeName) {
        return getWorkEffortScopeByName(workEffortType, workEffortScopeName, EntityPermission.READ_ONLY);
    }
    
    public WorkEffortScope getWorkEffortScopeByNameForUpdate(WorkEffortType workEffortType, String workEffortScopeName) {
        return getWorkEffortScopeByName(workEffortType, workEffortScopeName, EntityPermission.READ_WRITE);
    }
    
    public WorkEffortScopeDetailValue getWorkEffortScopeDetailValueForUpdate(WorkEffortScope workEffortScope) {
        return workEffortScope == null? null: workEffortScope.getLastDetailForUpdate().getWorkEffortScopeDetailValue().clone();
    }
    
    public WorkEffortScopeDetailValue getWorkEffortScopeDetailValueByNameForUpdate(WorkEffortType workEffortType, String workEffortScopeName) {
        return getWorkEffortScopeDetailValueForUpdate(getWorkEffortScopeByNameForUpdate(workEffortType, workEffortScopeName));
    }
    
    public WorkEffortScopeChoicesBean getWorkEffortScopeChoices(String defaultWorkEffortScopeChoice, Language language,
            boolean allowNullChoice, WorkEffortType workEffortType) {
        List<WorkEffortScope> workEffortScopes = getWorkEffortScopes(workEffortType);
        int size = workEffortScopes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultWorkEffortScopeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(WorkEffortScope workEffortScope: workEffortScopes) {
            WorkEffortScopeDetail workEffortScopeDetail = workEffortScope.getLastDetail();
            String label = getBestWorkEffortScopeDescription(workEffortScope, language);
            String value = workEffortScopeDetail.getWorkEffortScopeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultWorkEffortScopeChoice != null && defaultWorkEffortScopeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && workEffortScopeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new WorkEffortScopeChoicesBean(labels, values, defaultValue);
    }
    
    public WorkEffortScopeTransfer getWorkEffortScopeTransfer(UserVisit userVisit, WorkEffortScope workEffortScope) {
        return getWorkEffortTransferCaches(userVisit).getWorkEffortScopeTransferCache().getWorkEffortScopeTransfer(workEffortScope);
    }
    
    public List<WorkEffortScopeTransfer> getWorkEffortScopeTransfers(UserVisit userVisit, WorkEffortType workEffortType) {
        List<WorkEffortScope> workEffortScopes = getWorkEffortScopes(workEffortType);
        List<WorkEffortScopeTransfer> workEffortScopeTransfers = new ArrayList<>(workEffortScopes.size());
        WorkEffortScopeTransferCache workEffortScopeTransferCache = getWorkEffortTransferCaches(userVisit).getWorkEffortScopeTransferCache();
        
        workEffortScopes.forEach((workEffortScope) ->
                workEffortScopeTransfers.add(workEffortScopeTransferCache.getWorkEffortScopeTransfer(workEffortScope))
        );
        
        return workEffortScopeTransfers;
    }
    
    private void updateWorkEffortScopeFromValue(WorkEffortScopeDetailValue workEffortScopeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(workEffortScopeDetailValue.hasBeenModified()) {
            WorkEffortScope workEffortScope = WorkEffortScopeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     workEffortScopeDetailValue.getWorkEffortScopePK());
            WorkEffortScopeDetail workEffortScopeDetail = workEffortScope.getActiveDetailForUpdate();
            
            workEffortScopeDetail.setThruTime(session.START_TIME_LONG);
            workEffortScopeDetail.store();
            
            WorkEffortScopePK workEffortScopePK = workEffortScopeDetail.getWorkEffortScopePK();
            WorkEffortType workEffortType = workEffortScopeDetail.getWorkEffortType(); // Not updated
            String workEffortScopeName = workEffortScopeDetailValue.getWorkEffortScopeName();
            SequencePK workEffortSequencePK = workEffortScopeDetailValue.getWorkEffortSequencePK();
            Long scheduledTime = workEffortScopeDetailValue.getScheduledTime();
            Long estimatedTimeAllowed = workEffortScopeDetailValue.getEstimatedTimeAllowed();
            Long maximumTimeAllowed = workEffortScopeDetailValue.getMaximumTimeAllowed();
            Boolean isDefault = workEffortScopeDetailValue.getIsDefault();
            Integer sortOrder = workEffortScopeDetailValue.getSortOrder();
            
            if(checkDefault) {
                WorkEffortScope defaultWorkEffortScope = getDefaultWorkEffortScope(workEffortType);
                boolean defaultFound = defaultWorkEffortScope != null && !defaultWorkEffortScope.equals(workEffortScope);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    WorkEffortScopeDetailValue defaultWorkEffortScopeDetailValue = getDefaultWorkEffortScopeDetailValueForUpdate(workEffortType);
                    
                    defaultWorkEffortScopeDetailValue.setIsDefault(Boolean.FALSE);
                    updateWorkEffortScopeFromValue(defaultWorkEffortScopeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            workEffortScopeDetail = WorkEffortScopeDetailFactory.getInstance().create(workEffortScopePK,
                    workEffortType.getPrimaryKey(), workEffortScopeName, workEffortSequencePK, scheduledTime, estimatedTimeAllowed,
                    maximumTimeAllowed, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            workEffortScope.setActiveDetail(workEffortScopeDetail);
            workEffortScope.setLastDetail(workEffortScopeDetail);
            
            sendEventUsingNames(workEffortScopePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void updateWorkEffortScopeFromValue(WorkEffortScopeDetailValue workEffortScopeDetailValue, BasePK updatedBy) {
        updateWorkEffortScopeFromValue(workEffortScopeDetailValue, true, updatedBy);
    }
    
    private void deleteWorkEffortScope(WorkEffortScope workEffortScope, boolean checkDefault, BasePK deletedBy) {
        var workRequirementControl = (WorkRequirementControl)Session.getModelController(WorkRequirementControl.class);
        
        deleteWorkEffortsByWorkEffortScope(workEffortScope, deletedBy);
        workRequirementControl.deleteWorkRequirementScopesByWorkEffortScope(workEffortScope, deletedBy);
        deleteWorkEffortScopeDescriptionsByWorkEffortScope(workEffortScope, deletedBy);
        
        WorkEffortScopeDetail workEffortScopeDetail = workEffortScope.getLastDetailForUpdate();
        workEffortScopeDetail.setThruTime(session.START_TIME_LONG);
        workEffortScope.setActiveDetail(null);
        workEffortScope.store();
        
        if(checkDefault) {
            // Check for default, and pick one if necessary
            WorkEffortType workEffortType = workEffortScopeDetail.getWorkEffortType();
            WorkEffortScope defaultWorkEffortScope = getDefaultWorkEffortScope(workEffortType);
            
            if(defaultWorkEffortScope == null) {
                List<WorkEffortScope> workEffortScopes = getWorkEffortScopesForUpdate(workEffortType);
                
                if(!workEffortScopes.isEmpty()) {
                    Iterator<WorkEffortScope> iter = workEffortScopes.iterator();
                    
                    if(iter.hasNext()) {
                        defaultWorkEffortScope = iter.next();
                    }
                    
                    WorkEffortScopeDetailValue workEffortScopeDetailValue = defaultWorkEffortScope.getLastDetailForUpdate().getWorkEffortScopeDetailValue().clone();
                    
                    workEffortScopeDetailValue.setIsDefault(Boolean.TRUE);
                    updateWorkEffortScopeFromValue(workEffortScopeDetailValue, false, deletedBy);
                }
            }
        }
        
        sendEventUsingNames(workEffortScope.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    public void deleteWorkEffortScope(WorkEffortScope workEffortScope, BasePK deletedBy) {
        deleteWorkEffortScope(workEffortScope, true, deletedBy);
    }
    
    public void deleteWorkEffortScopesByWorkEffortType(WorkEffortType workEffortType, BasePK deletedBy) {
        List<WorkEffortScope> workEffortScopes = getWorkEffortScopesForUpdate(workEffortType);
        
        workEffortScopes.stream().forEach((workEffortScope) -> {
            deleteWorkEffortScope(workEffortScope, false, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Work Effort Scope Descriptions
    // --------------------------------------------------------------------------------
    
    public WorkEffortScopeDescription createWorkEffortScopeDescription(WorkEffortScope workEffortScope, Language language,
            String description, BasePK createdBy) {
        WorkEffortScopeDescription workEffortScopeDescription = WorkEffortScopeDescriptionFactory.getInstance().create(workEffortScope,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(workEffortScope.getPrimaryKey(), EventTypes.MODIFY.name(), workEffortScopeDescription.getPrimaryKey(),
                null, createdBy);
        
        return workEffortScopeDescription;
    }
    
    private WorkEffortScopeDescription getWorkEffortScopeDescription(WorkEffortScope workEffortScope, Language language,
            EntityPermission entityPermission) {
        WorkEffortScopeDescription workEffortScopeDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM workeffortscopedescriptions " +
                        "WHERE wesd_wes_workeffortscopeid = ? AND wesd_lang_languageid = ? AND wesd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM workeffortscopedescriptions " +
                        "WHERE wesd_wes_workeffortscopeid = ? AND wesd_lang_languageid = ? AND wesd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = WorkEffortScopeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, workEffortScope.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            workEffortScopeDescription = WorkEffortScopeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return workEffortScopeDescription;
    }
    
    public WorkEffortScopeDescription getWorkEffortScopeDescription(WorkEffortScope workEffortScope, Language language) {
        return getWorkEffortScopeDescription(workEffortScope, language, EntityPermission.READ_ONLY);
    }
    
    public WorkEffortScopeDescription getWorkEffortScopeDescriptionForUpdate(WorkEffortScope workEffortScope, Language language) {
        return getWorkEffortScopeDescription(workEffortScope, language, EntityPermission.READ_WRITE);
    }
    
    public WorkEffortScopeDescriptionValue getWorkEffortScopeDescriptionValue(WorkEffortScopeDescription workEffortScopeDescription) {
        return workEffortScopeDescription == null? null: workEffortScopeDescription.getWorkEffortScopeDescriptionValue().clone();
    }
    
    public WorkEffortScopeDescriptionValue getWorkEffortScopeDescriptionValueForUpdate(WorkEffortScope workEffortScope, Language language) {
        return getWorkEffortScopeDescriptionValue(getWorkEffortScopeDescriptionForUpdate(workEffortScope, language));
    }
    
    private List<WorkEffortScopeDescription> getWorkEffortScopeDescriptionsByWorkEffortScope(WorkEffortScope workEffortScope, EntityPermission entityPermission) {
        List<WorkEffortScopeDescription> workEffortScopeDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM workeffortscopedescriptions, languages " +
                        "WHERE wesd_wes_workeffortscopeid = ? AND wesd_thrutime = ? AND wesd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM workeffortscopedescriptions " +
                        "WHERE wesd_wes_workeffortscopeid = ? AND wesd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = WorkEffortScopeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, workEffortScope.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            workEffortScopeDescriptions = WorkEffortScopeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return workEffortScopeDescriptions;
    }
    
    public List<WorkEffortScopeDescription> getWorkEffortScopeDescriptionsByWorkEffortScope(WorkEffortScope workEffortScope) {
        return getWorkEffortScopeDescriptionsByWorkEffortScope(workEffortScope, EntityPermission.READ_ONLY);
    }
    
    public List<WorkEffortScopeDescription> getWorkEffortScopeDescriptionsByWorkEffortScopeForUpdate(WorkEffortScope workEffortScope) {
        return getWorkEffortScopeDescriptionsByWorkEffortScope(workEffortScope, EntityPermission.READ_WRITE);
    }
    
    public String getBestWorkEffortScopeDescription(WorkEffortScope workEffortScope, Language language) {
        String description;
        WorkEffortScopeDescription workEffortScopeDescription = getWorkEffortScopeDescription(workEffortScope, language);
        
        if(workEffortScopeDescription == null && !language.getIsDefault()) {
            workEffortScopeDescription = getWorkEffortScopeDescription(workEffortScope, getPartyControl().getDefaultLanguage());
        }
        
        if(workEffortScopeDescription == null) {
            description = workEffortScope.getLastDetail().getWorkEffortScopeName();
        } else {
            description = workEffortScopeDescription.getDescription();
        }
        
        return description;
    }
    
    public WorkEffortScopeDescriptionTransfer getWorkEffortScopeDescriptionTransfer(UserVisit userVisit, WorkEffortScopeDescription workEffortScopeDescription) {
        return getWorkEffortTransferCaches(userVisit).getWorkEffortScopeDescriptionTransferCache().getWorkEffortScopeDescriptionTransfer(workEffortScopeDescription);
    }
    
    public List<WorkEffortScopeDescriptionTransfer> getWorkEffortScopeDescriptionTransfers(UserVisit userVisit, WorkEffortScope workEffortScope) {
        List<WorkEffortScopeDescription> workEffortScopeDescriptions = getWorkEffortScopeDescriptionsByWorkEffortScope(workEffortScope);
        List<WorkEffortScopeDescriptionTransfer> workEffortScopeDescriptionTransfers = new ArrayList<>(workEffortScopeDescriptions.size());
        WorkEffortScopeDescriptionTransferCache workEffortScopeDescriptionTransferCache = getWorkEffortTransferCaches(userVisit).getWorkEffortScopeDescriptionTransferCache();
        
        workEffortScopeDescriptions.forEach((workEffortScopeDescription) ->
                workEffortScopeDescriptionTransfers.add(workEffortScopeDescriptionTransferCache.getWorkEffortScopeDescriptionTransfer(workEffortScopeDescription))
        );
        
        return workEffortScopeDescriptionTransfers;
    }
    
    public void updateWorkEffortScopeDescriptionFromValue(WorkEffortScopeDescriptionValue workEffortScopeDescriptionValue, BasePK updatedBy) {
        if(workEffortScopeDescriptionValue.hasBeenModified()) {
            WorkEffortScopeDescription workEffortScopeDescription = WorkEffortScopeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     workEffortScopeDescriptionValue.getPrimaryKey());
            
            workEffortScopeDescription.setThruTime(session.START_TIME_LONG);
            workEffortScopeDescription.store();
            
            WorkEffortScope workEffortScope = workEffortScopeDescription.getWorkEffortScope();
            Language language = workEffortScopeDescription.getLanguage();
            String description = workEffortScopeDescriptionValue.getDescription();
            
            workEffortScopeDescription = WorkEffortScopeDescriptionFactory.getInstance().create(workEffortScope, language,
                    description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(workEffortScope.getPrimaryKey(), EventTypes.MODIFY.name(), workEffortScopeDescription.getPrimaryKey(),
                    null, updatedBy);
        }
    }
    
    public void deleteWorkEffortScopeDescription(WorkEffortScopeDescription workEffortScopeDescription, BasePK deletedBy) {
        workEffortScopeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(workEffortScopeDescription.getWorkEffortScopePK(), EventTypes.MODIFY.name(),
                workEffortScopeDescription.getPrimaryKey(), null, deletedBy);
    }
    
    public void deleteWorkEffortScopeDescriptionsByWorkEffortScope(WorkEffortScope workEffortScope, BasePK deletedBy) {
        List<WorkEffortScopeDescription> workEffortScopeDescriptions = getWorkEffortScopeDescriptionsByWorkEffortScopeForUpdate(workEffortScope);
        
        workEffortScopeDescriptions.forEach((workEffortScopeDescription) -> 
                deleteWorkEffortScopeDescription(workEffortScopeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Work Efforts
    // --------------------------------------------------------------------------------
    
    public WorkEffort createWorkEffort(String workEffortName, EntityInstance owningEntityInstanceId, WorkEffortScope workEffortScope, Long scheduledTime,
            Long scheduledStartTime, Long scheduledEndTime, Long estimatedTimeAllowed, Long maximumTimeAllowed, BasePK createdBy) {
        WorkEffort workEffort = WorkEffortFactory.getInstance().create();
        WorkEffortDetail workEffortDetail = WorkEffortDetailFactory.getInstance().create(workEffort, workEffortName, owningEntityInstanceId, workEffortScope,
                scheduledTime, scheduledStartTime, scheduledEndTime, estimatedTimeAllowed, maximumTimeAllowed, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        workEffort = WorkEffortFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                workEffort.getPrimaryKey());
        workEffort.setActiveDetail(workEffortDetail);
        workEffort.setLastDetail(workEffortDetail);
        workEffort.store();
        
        sendEventUsingNames(workEffort.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return workEffort;
    }
    
    private List<WorkEffort> getWorkEffortsByWorkEffortScope(WorkEffortScope workEffortScope, EntityPermission entityPermission) {
        List<WorkEffort> workEfforts = null;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM workefforts, workeffortdetails " +
                        "WHERE weff_activedetailid = weffdt_workeffortdetailid " +
                        "AND weffdt_wes_workeffortscopeid = ? " +
                        "ORDER BY weffdt_workeffortname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM workefforts, workeffortdetails " +
                        "WHERE weff_activedetailid = weffdt_workeffortdetailid " +
                        "AND weffdt_wes_workeffortscopeid = ? " +
                        "FOR UPDATE";
            }

            PreparedStatement ps = WorkEffortFactory.getInstance().prepareStatement(query);

            ps.setLong(1, workEffortScope.getPrimaryKey().getEntityId());

            workEfforts = WorkEffortFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return workEfforts;
    }

    public List<WorkEffort> getWorkEffortsByWorkEffortScope(WorkEffortScope workEffortScope) {
        return getWorkEffortsByWorkEffortScope(workEffortScope, EntityPermission.READ_ONLY);
    }

    public List<WorkEffort> getWorkEffortsByWorkEffortScopeForUpdate(WorkEffortScope workEffortScope) {
        return getWorkEffortsByWorkEffortScope(workEffortScope, EntityPermission.READ_WRITE);
    }

    private List<WorkEffort> getWorkEffortsByOwningEntityInstance(EntityInstance owningEntityInstanceId, EntityPermission entityPermission) {
        List<WorkEffort> workEfforts = null;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM workefforts, workeffortdetails " +
                        "WHERE weff_activedetailid = weffdt_workeffortdetailid " +
                        "AND weffdt_owningentityinstanceid = ? " +
                        "ORDER BY weffdt_workeffortname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM workefforts, workeffortdetails " +
                        "WHERE weff_activedetailid = weffdt_workeffortdetailid " +
                        "AND weffdt_owningentityinstanceid = ? " +
                        "FOR UPDATE";
            }

            PreparedStatement ps = WorkEffortFactory.getInstance().prepareStatement(query);

            ps.setLong(1, owningEntityInstanceId.getPrimaryKey().getEntityId());

            workEfforts = WorkEffortFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return workEfforts;
    }

    public List<WorkEffort> getWorkEffortsByOwningEntityInstance(EntityInstance owningEntityInstanceId) {
        return getWorkEffortsByOwningEntityInstance(owningEntityInstanceId, EntityPermission.READ_ONLY);
    }

    public List<WorkEffort> getWorkEffortsByOwningEntityInstanceForUpdate(EntityInstance owningEntityInstanceId) {
        return getWorkEffortsByOwningEntityInstance(owningEntityInstanceId, EntityPermission.READ_WRITE);
    }

    private WorkEffort getWorkEffortByName(String workEffortName, EntityPermission entityPermission) {
        WorkEffort workEffort = null;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM workefforts, workeffortdetails " +
                        "WHERE weff_activedetailid = weffdt_workeffortdetailid " +
                        "AND weffdt_workeffortname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM workefforts, workeffortdetails " +
                        "WHERE weff_activedetailid = weffdt_workeffortdetailid " +
                        "AND weffdt_workeffortname = ? " +
                        "FOR UPDATE";
            }

            PreparedStatement ps = WorkEffortFactory.getInstance().prepareStatement(query);

            ps.setString(1, workEffortName);

            workEffort = WorkEffortFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return workEffort;
    }

    public WorkEffort getWorkEffortByName(String workEffortName) {
        return getWorkEffortByName(workEffortName, EntityPermission.READ_ONLY);
    }

    public WorkEffort getWorkEffortByNameForUpdate(String workEffortName) {
        return getWorkEffortByName(workEffortName, EntityPermission.READ_WRITE);
    }

    private WorkEffort getWorkEffort(EntityInstance owningEntityInstanceId, WorkEffortScope workEffortScope, EntityPermission entityPermission) {
        WorkEffort workEffort = null;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM workefforts, workeffortdetails " +
                        "WHERE weff_activedetailid = weffdt_workeffortdetailid " +
                        "AND weffdt_owningentityinstanceid = ? AND weffdt_wes_workeffortscopeid = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM workefforts, workeffortdetails " +
                        "WHERE weff_activedetailid = weffdt_workeffortdetailid " +
                        "AND weffdt_owningentityinstanceid = ? AND weffdt_wes_workeffortscopeid = ? " +
                        "FOR UPDATE";
            }

            PreparedStatement ps = WorkEffortFactory.getInstance().prepareStatement(query);

            ps.setLong(1, owningEntityInstanceId.getPrimaryKey().getEntityId());
            ps.setLong(2, workEffortScope.getPrimaryKey().getEntityId());

            workEffort = WorkEffortFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return workEffort;
    }

    public WorkEffort getWorkEffort(EntityInstance owningEntityInstanceId, WorkEffortScope workEffortScope) {
        return getWorkEffort(owningEntityInstanceId, workEffortScope, EntityPermission.READ_ONLY);
    }

    public WorkEffort getWorkEffortForUpdate(EntityInstance owningEntityInstanceId, WorkEffortScope workEffortScope) {
        return getWorkEffort(owningEntityInstanceId, workEffortScope, EntityPermission.READ_WRITE);
    }

    public WorkEffortDetailValue getWorkEffortDetailValueForUpdate(WorkEffort workEffort) {
        return workEffort == null? null: workEffort.getLastDetailForUpdate().getWorkEffortDetailValue().clone();
    }
    
    public WorkEffortDetailValue getWorkEffortDetailValueByNameForUpdate(String workEffortName) {
        return getWorkEffortDetailValueForUpdate(getWorkEffortByNameForUpdate(workEffortName));
    }
    
    public WorkEffortTransfer getWorkEffortTransfer(UserVisit userVisit, WorkEffort workEffort) {
        return getWorkEffortTransferCaches(userVisit).getWorkEffortTransferCache().getWorkEffortTransfer(workEffort);
    }
    
    public List<WorkEffortTransfer> getWorkEffortTransfers(UserVisit userVisit, List<WorkEffort> workEfforts) {
        List<WorkEffortTransfer> workEffortTransfers = new ArrayList<>(workEfforts.size());
        WorkEffortTransferCache workEffortTransferCache = getWorkEffortTransferCaches(userVisit).getWorkEffortTransferCache();

        workEfforts.forEach((workEffort) ->
                workEffortTransfers.add(workEffortTransferCache.getWorkEffortTransfer(workEffort))
        );

        return workEffortTransfers;
    }

    public List<WorkEffortTransfer> getWorkEffortTransfersByWorkEffortScope(UserVisit userVisit, WorkEffortScope workEffortScope) {
        return getWorkEffortTransfers(userVisit, getWorkEffortsByWorkEffortScope(workEffortScope));
    }

    public List<WorkEffortTransfer> getWorkEffortTransfersByOwningEntityInstance(UserVisit userVisit, EntityInstance owningEntityInstanceId) {
        return getWorkEffortTransfers(userVisit, getWorkEffortsByOwningEntityInstance(owningEntityInstanceId));
    }

    public void updateWorkEffortFromValue(WorkEffortDetailValue workEffortDetailValue, BasePK updatedBy) {
        if(workEffortDetailValue.hasBeenModified()) {
            WorkEffort workEffort = WorkEffortFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     workEffortDetailValue.getWorkEffortPK());
            WorkEffortDetail workEffortDetail = workEffort.getActiveDetailForUpdate();
            
            workEffortDetail.setThruTime(session.START_TIME_LONG);
            workEffortDetail.store();
            
            WorkEffortPK workEffortPK = workEffortDetail.getWorkEffortPK();
            String workEffortName = workEffortDetailValue.getWorkEffortName();
            EntityInstancePK owningEntityInstancePK = workEffortDetail.getOwningEntityInstancePK(); // Not updated
            WorkEffortScopePK workEffortScopePK = workEffortDetail.getWorkEffortScopePK(); // Not updated
            Long scheduledTime = workEffortDetail.getScheduledTime();
            Long scheduledStartTime = workEffortDetail.getScheduledStartTime();
            Long scheduledEndTime = workEffortDetail.getScheduledEndTime();
            Long estimatedTimeAllowed = workEffortDetail.getEstimatedTimeAllowed();
            Long maximumTimeAllowed = workEffortDetail.getMaximumTimeAllowed();
            
            workEffortDetail = WorkEffortDetailFactory.getInstance().create(workEffortPK, workEffortName, owningEntityInstancePK, workEffortScopePK,
                    scheduledTime, scheduledStartTime, scheduledEndTime, estimatedTimeAllowed, maximumTimeAllowed, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            workEffort.setActiveDetail(workEffortDetail);
            workEffort.setLastDetail(workEffortDetail);
            
            sendEventUsingNames(workEffortPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void deleteWorkEffort(WorkEffort workEffort, BasePK deletedBy) {
        var workRequirementControl = (WorkRequirementControl)Session.getModelController(WorkRequirementControl.class);
        
        workRequirementControl.deleteWorkRequirementsByWorkEffort(workEffort, deletedBy);
        
        WorkEffortDetail workEffortDetail = workEffort.getLastDetailForUpdate();
        workEffortDetail.setThruTime(session.START_TIME_LONG);
        workEffort.setActiveDetail(null);
        workEffort.store();
        
        sendEventUsingNames(workEffort.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    public void deleteWorkEfforts(List<WorkEffort> workEfforts, BasePK deletedBy) {
        workEfforts.forEach((workEffort) -> 
                deleteWorkEffort(workEffort, deletedBy)
        );
    }

    public void deleteWorkEffortsByWorkEffortScope(WorkEffortScope workEffortScope, BasePK deletedBy) {
        deleteWorkEfforts(getWorkEffortsByWorkEffortScopeForUpdate(workEffortScope), deletedBy);
    }

    public void deleteWorkEffortsByOwningEntityInstance(EntityInstance owningEntityInstance, BasePK deletedBy) {
        deleteWorkEfforts(getWorkEffortsByOwningEntityInstanceForUpdate(owningEntityInstance), deletedBy);
    }

}
