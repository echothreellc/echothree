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

package com.echothree.model.control.workeffort.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.workeffort.common.choice.WorkEffortScopeChoicesBean;
import com.echothree.model.control.workeffort.common.transfer.WorkEffortScopeDescriptionTransfer;
import com.echothree.model.control.workeffort.common.transfer.WorkEffortScopeTransfer;
import com.echothree.model.control.workeffort.common.transfer.WorkEffortTransfer;
import com.echothree.model.control.workeffort.common.transfer.WorkEffortTypeDescriptionTransfer;
import com.echothree.model.control.workeffort.common.transfer.WorkEffortTypeTransfer;
import com.echothree.model.control.workeffort.server.transfer.WorkEffortTransferCaches;
import com.echothree.model.control.workrequirement.server.control.WorkRequirementControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workeffort.server.entity.WorkEffort;
import com.echothree.model.data.workeffort.server.entity.WorkEffortScope;
import com.echothree.model.data.workeffort.server.entity.WorkEffortScopeDescription;
import com.echothree.model.data.workeffort.server.entity.WorkEffortType;
import com.echothree.model.data.workeffort.server.entity.WorkEffortTypeDescription;
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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class WorkEffortControl
        extends BaseModelControl {
    
    /** Creates a new instance of WorkEffortControl */
    protected WorkEffortControl() {
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
        var workEffortType = WorkEffortTypeFactory.getInstance().create();
        var workEffortTypeDetail = WorkEffortTypeDetailFactory.getInstance().create(workEffortType, workEffortTypeName, entityType,
                workEffortSequence, scheduledTime, estimatedTimeAllowed, maximumTimeAllowed, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        workEffortType = WorkEffortTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, workEffortType.getPrimaryKey());
        workEffortType.setActiveDetail(workEffortTypeDetail);
        workEffortType.setLastDetail(workEffortTypeDetail);
        workEffortType.store();
        
        sendEvent(workEffortType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
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

        var ps = WorkEffortTypeFactory.getInstance().prepareStatement(query);
        
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

            var ps = WorkEffortTypeFactory.getInstance().prepareStatement(query);
            
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
        var workEffortTypes = getWorkEffortTypes();
        List<WorkEffortTypeTransfer> workEffortTypeTransfers = new ArrayList<>(workEffortTypes.size());
        var workEffortTypeTransferCache = getWorkEffortTransferCaches(userVisit).getWorkEffortTypeTransferCache();
        
        workEffortTypes.forEach((workEffortType) ->
                workEffortTypeTransfers.add(workEffortTypeTransferCache.getWorkEffortTypeTransfer(workEffortType))
        );
        
        return workEffortTypeTransfers;
    }
    
    public void updateWorkEffortTypeFromValue(WorkEffortTypeDetailValue workEffortTypeDetailValue, BasePK updatedBy) {
        if(workEffortTypeDetailValue.hasBeenModified()) {
            var workEffortType = WorkEffortTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     workEffortTypeDetailValue.getWorkEffortTypePK());
            var workEffortTypeDetail = workEffortType.getActiveDetailForUpdate();
            
            workEffortTypeDetail.setThruTime(session.START_TIME_LONG);
            workEffortTypeDetail.store();

            var workEffortTypePK = workEffortTypeDetail.getWorkEffortTypePK();
            var workEffortTypeName = workEffortTypeDetailValue.getWorkEffortTypeName();
            var entityTypePK = workEffortTypeDetailValue.getEntityTypePK();
            var workEffortSequencePK = workEffortTypeDetailValue.getWorkEffortSequencePK();
            var scheduledTime = workEffortTypeDetailValue.getScheduledTime();
            var estimatedTimeAllowed = workEffortTypeDetailValue.getEstimatedTimeAllowed();
            var maximumTimeAllowed = workEffortTypeDetailValue.getMaximumTimeAllowed();
            var sortOrder = workEffortTypeDetailValue.getSortOrder();
            
            workEffortTypeDetail = WorkEffortTypeDetailFactory.getInstance().create(workEffortTypePK, workEffortTypeName, entityTypePK, workEffortSequencePK,
                    scheduledTime, estimatedTimeAllowed, maximumTimeAllowed, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            workEffortType.setActiveDetail(workEffortTypeDetail);
            workEffortType.setLastDetail(workEffortTypeDetail);
            
            sendEvent(workEffortTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deleteWorkEffortType(WorkEffortType workEffortType, BasePK deletedBy) {
        var workRequirementControl = Session.getModelController(WorkRequirementControl.class);
        
        deleteWorkEffortScopesByWorkEffortType(workEffortType, deletedBy);
        workRequirementControl.deleteWorkRequirementTypesByWorkEffortType(workEffortType, deletedBy);
        deleteWorkEffortTypeDescriptionsByWorkEffortType(workEffortType, deletedBy);

        var workEffortTypeDetail = workEffortType.getLastDetailForUpdate();
        workEffortTypeDetail.setThruTime(session.START_TIME_LONG);
        workEffortType.setActiveDetail(null);
        workEffortType.store();
        
        sendEvent(workEffortType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Work Effort Type Descriptions
    // --------------------------------------------------------------------------------
    
    public WorkEffortTypeDescription createWorkEffortTypeDescription(WorkEffortType workEffortType, Language language,
            String description, BasePK createdBy) {
        var workEffortTypeDescription = WorkEffortTypeDescriptionFactory.getInstance().create(workEffortType,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(workEffortType.getPrimaryKey(), EventTypes.MODIFY, workEffortTypeDescription.getPrimaryKey(),
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

            var ps = WorkEffortTypeDescriptionFactory.getInstance().prepareStatement(query);
            
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

            var ps = WorkEffortTypeDescriptionFactory.getInstance().prepareStatement(query);
            
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
        var workEffortTypeDescription = getWorkEffortTypeDescription(workEffortType, language);
        
        if(workEffortTypeDescription == null && !language.getIsDefault()) {
            workEffortTypeDescription = getWorkEffortTypeDescription(workEffortType, partyControl.getDefaultLanguage());
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
        var workEffortTypeDescriptions = getWorkEffortTypeDescriptionsByWorkEffortType(workEffortType);
        List<WorkEffortTypeDescriptionTransfer> workEffortTypeDescriptionTransfers = new ArrayList<>(workEffortTypeDescriptions.size());
        var workEffortTypeDescriptionTransferCache = getWorkEffortTransferCaches(userVisit).getWorkEffortTypeDescriptionTransferCache();
        
        workEffortTypeDescriptions.forEach((workEffortTypeDescription) ->
                workEffortTypeDescriptionTransfers.add(workEffortTypeDescriptionTransferCache.getWorkEffortTypeDescriptionTransfer(workEffortTypeDescription))
        );
        
        return workEffortTypeDescriptionTransfers;
    }
    
    public void updateWorkEffortTypeDescriptionFromValue(WorkEffortTypeDescriptionValue workEffortTypeDescriptionValue, BasePK updatedBy) {
        if(workEffortTypeDescriptionValue.hasBeenModified()) {
            var workEffortTypeDescription = WorkEffortTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     workEffortTypeDescriptionValue.getPrimaryKey());
            
            workEffortTypeDescription.setThruTime(session.START_TIME_LONG);
            workEffortTypeDescription.store();

            var workEffortType = workEffortTypeDescription.getWorkEffortType();
            var language = workEffortTypeDescription.getLanguage();
            var description = workEffortTypeDescriptionValue.getDescription();
            
            workEffortTypeDescription = WorkEffortTypeDescriptionFactory.getInstance().create(workEffortType, language,
                    description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(workEffortType.getPrimaryKey(), EventTypes.MODIFY, workEffortTypeDescription.getPrimaryKey(),
                    null, updatedBy);
        }
    }
    
    public void deleteWorkEffortTypeDescription(WorkEffortTypeDescription workEffortTypeDescription, BasePK deletedBy) {
        workEffortTypeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(workEffortTypeDescription.getWorkEffortTypePK(), EventTypes.MODIFY,
                workEffortTypeDescription.getPrimaryKey(), null, deletedBy);
    }
    
    public void deleteWorkEffortTypeDescriptionsByWorkEffortType(WorkEffortType workEffortType, BasePK deletedBy) {
        var workEffortTypeDescriptions = getWorkEffortTypeDescriptionsByWorkEffortTypeForUpdate(workEffortType);
        
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
        var defaultWorkEffortScope = getDefaultWorkEffortScope(workEffortType);
        var defaultFound = defaultWorkEffortScope != null;
        
        if(defaultFound && isDefault) {
            var defaultWorkEffortScopeDetailValue = getDefaultWorkEffortScopeDetailValueForUpdate(workEffortType);
            
            defaultWorkEffortScopeDetailValue.setIsDefault(false);
            updateWorkEffortScopeFromValue(defaultWorkEffortScopeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var workEffortScope = WorkEffortScopeFactory.getInstance().create();
        var workEffortScopeDetail = WorkEffortScopeDetailFactory.getInstance().create(workEffortScope,
                workEffortType, workEffortScopeName, workEffortSequence, scheduledTime, estimatedTimeAllowed, maximumTimeAllowed,
                isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        workEffortScope = WorkEffortScopeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                workEffortScope.getPrimaryKey());
        workEffortScope.setActiveDetail(workEffortScopeDetail);
        workEffortScope.setLastDetail(workEffortScopeDetail);
        workEffortScope.store();
        
        sendEvent(workEffortScope.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
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

            var ps = WorkEffortScopeFactory.getInstance().prepareStatement(query);
            
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

            var ps = WorkEffortScopeFactory.getInstance().prepareStatement(query);
            
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

            var ps = WorkEffortScopeFactory.getInstance().prepareStatement(query);
            
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
        var workEffortScopes = getWorkEffortScopes(workEffortType);
        var size = workEffortScopes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultWorkEffortScopeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var workEffortScope : workEffortScopes) {
            var workEffortScopeDetail = workEffortScope.getLastDetail();
            var label = getBestWorkEffortScopeDescription(workEffortScope, language);
            var value = workEffortScopeDetail.getWorkEffortScopeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultWorkEffortScopeChoice != null && defaultWorkEffortScopeChoice.equals(value);
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
        var workEffortScopes = getWorkEffortScopes(workEffortType);
        List<WorkEffortScopeTransfer> workEffortScopeTransfers = new ArrayList<>(workEffortScopes.size());
        var workEffortScopeTransferCache = getWorkEffortTransferCaches(userVisit).getWorkEffortScopeTransferCache();
        
        workEffortScopes.forEach((workEffortScope) ->
                workEffortScopeTransfers.add(workEffortScopeTransferCache.getWorkEffortScopeTransfer(workEffortScope))
        );
        
        return workEffortScopeTransfers;
    }
    
    private void updateWorkEffortScopeFromValue(WorkEffortScopeDetailValue workEffortScopeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(workEffortScopeDetailValue.hasBeenModified()) {
            var workEffortScope = WorkEffortScopeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     workEffortScopeDetailValue.getWorkEffortScopePK());
            var workEffortScopeDetail = workEffortScope.getActiveDetailForUpdate();
            
            workEffortScopeDetail.setThruTime(session.START_TIME_LONG);
            workEffortScopeDetail.store();

            var workEffortScopePK = workEffortScopeDetail.getWorkEffortScopePK();
            var workEffortType = workEffortScopeDetail.getWorkEffortType(); // Not updated
            var workEffortScopeName = workEffortScopeDetailValue.getWorkEffortScopeName();
            var workEffortSequencePK = workEffortScopeDetailValue.getWorkEffortSequencePK();
            var scheduledTime = workEffortScopeDetailValue.getScheduledTime();
            var estimatedTimeAllowed = workEffortScopeDetailValue.getEstimatedTimeAllowed();
            var maximumTimeAllowed = workEffortScopeDetailValue.getMaximumTimeAllowed();
            var isDefault = workEffortScopeDetailValue.getIsDefault();
            var sortOrder = workEffortScopeDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultWorkEffortScope = getDefaultWorkEffortScope(workEffortType);
                var defaultFound = defaultWorkEffortScope != null && !defaultWorkEffortScope.equals(workEffortScope);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultWorkEffortScopeDetailValue = getDefaultWorkEffortScopeDetailValueForUpdate(workEffortType);
                    
                    defaultWorkEffortScopeDetailValue.setIsDefault(false);
                    updateWorkEffortScopeFromValue(defaultWorkEffortScopeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            workEffortScopeDetail = WorkEffortScopeDetailFactory.getInstance().create(workEffortScopePK,
                    workEffortType.getPrimaryKey(), workEffortScopeName, workEffortSequencePK, scheduledTime, estimatedTimeAllowed,
                    maximumTimeAllowed, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            workEffortScope.setActiveDetail(workEffortScopeDetail);
            workEffortScope.setLastDetail(workEffortScopeDetail);
            
            sendEvent(workEffortScopePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateWorkEffortScopeFromValue(WorkEffortScopeDetailValue workEffortScopeDetailValue, BasePK updatedBy) {
        updateWorkEffortScopeFromValue(workEffortScopeDetailValue, true, updatedBy);
    }
    
    private void deleteWorkEffortScope(WorkEffortScope workEffortScope, boolean checkDefault, BasePK deletedBy) {
        var workRequirementControl = Session.getModelController(WorkRequirementControl.class);
        
        deleteWorkEffortsByWorkEffortScope(workEffortScope, deletedBy);
        workRequirementControl.deleteWorkRequirementScopesByWorkEffortScope(workEffortScope, deletedBy);
        deleteWorkEffortScopeDescriptionsByWorkEffortScope(workEffortScope, deletedBy);

        var workEffortScopeDetail = workEffortScope.getLastDetailForUpdate();
        workEffortScopeDetail.setThruTime(session.START_TIME_LONG);
        workEffortScope.setActiveDetail(null);
        workEffortScope.store();
        
        if(checkDefault) {
            // Check for default, and pick one if necessary
            var workEffortType = workEffortScopeDetail.getWorkEffortType();
            var defaultWorkEffortScope = getDefaultWorkEffortScope(workEffortType);
            
            if(defaultWorkEffortScope == null) {
                var workEffortScopes = getWorkEffortScopesForUpdate(workEffortType);
                
                if(!workEffortScopes.isEmpty()) {
                    var iter = workEffortScopes.iterator();
                    
                    if(iter.hasNext()) {
                        defaultWorkEffortScope = iter.next();
                    }

                    var workEffortScopeDetailValue = Objects.requireNonNull(defaultWorkEffortScope).getLastDetailForUpdate().getWorkEffortScopeDetailValue().clone();
                    
                    workEffortScopeDetailValue.setIsDefault(true);
                    updateWorkEffortScopeFromValue(workEffortScopeDetailValue, false, deletedBy);
                }
            }
        }
        
        sendEvent(workEffortScope.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteWorkEffortScope(WorkEffortScope workEffortScope, BasePK deletedBy) {
        deleteWorkEffortScope(workEffortScope, true, deletedBy);
    }
    
    public void deleteWorkEffortScopesByWorkEffortType(WorkEffortType workEffortType, BasePK deletedBy) {
        var workEffortScopes = getWorkEffortScopesForUpdate(workEffortType);
        
        workEffortScopes.forEach((workEffortScope) -> {
            deleteWorkEffortScope(workEffortScope, false, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Work Effort Scope Descriptions
    // --------------------------------------------------------------------------------
    
    public WorkEffortScopeDescription createWorkEffortScopeDescription(WorkEffortScope workEffortScope, Language language,
            String description, BasePK createdBy) {
        var workEffortScopeDescription = WorkEffortScopeDescriptionFactory.getInstance().create(workEffortScope,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(workEffortScope.getPrimaryKey(), EventTypes.MODIFY, workEffortScopeDescription.getPrimaryKey(),
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

            var ps = WorkEffortScopeDescriptionFactory.getInstance().prepareStatement(query);
            
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

            var ps = WorkEffortScopeDescriptionFactory.getInstance().prepareStatement(query);
            
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
        var workEffortScopeDescription = getWorkEffortScopeDescription(workEffortScope, language);
        
        if(workEffortScopeDescription == null && !language.getIsDefault()) {
            workEffortScopeDescription = getWorkEffortScopeDescription(workEffortScope, partyControl.getDefaultLanguage());
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
        var workEffortScopeDescriptions = getWorkEffortScopeDescriptionsByWorkEffortScope(workEffortScope);
        List<WorkEffortScopeDescriptionTransfer> workEffortScopeDescriptionTransfers = new ArrayList<>(workEffortScopeDescriptions.size());
        var workEffortScopeDescriptionTransferCache = getWorkEffortTransferCaches(userVisit).getWorkEffortScopeDescriptionTransferCache();
        
        workEffortScopeDescriptions.forEach((workEffortScopeDescription) ->
                workEffortScopeDescriptionTransfers.add(workEffortScopeDescriptionTransferCache.getWorkEffortScopeDescriptionTransfer(workEffortScopeDescription))
        );
        
        return workEffortScopeDescriptionTransfers;
    }
    
    public void updateWorkEffortScopeDescriptionFromValue(WorkEffortScopeDescriptionValue workEffortScopeDescriptionValue, BasePK updatedBy) {
        if(workEffortScopeDescriptionValue.hasBeenModified()) {
            var workEffortScopeDescription = WorkEffortScopeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     workEffortScopeDescriptionValue.getPrimaryKey());
            
            workEffortScopeDescription.setThruTime(session.START_TIME_LONG);
            workEffortScopeDescription.store();

            var workEffortScope = workEffortScopeDescription.getWorkEffortScope();
            var language = workEffortScopeDescription.getLanguage();
            var description = workEffortScopeDescriptionValue.getDescription();
            
            workEffortScopeDescription = WorkEffortScopeDescriptionFactory.getInstance().create(workEffortScope, language,
                    description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(workEffortScope.getPrimaryKey(), EventTypes.MODIFY, workEffortScopeDescription.getPrimaryKey(),
                    null, updatedBy);
        }
    }
    
    public void deleteWorkEffortScopeDescription(WorkEffortScopeDescription workEffortScopeDescription, BasePK deletedBy) {
        workEffortScopeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(workEffortScopeDescription.getWorkEffortScopePK(), EventTypes.MODIFY,
                workEffortScopeDescription.getPrimaryKey(), null, deletedBy);
    }
    
    public void deleteWorkEffortScopeDescriptionsByWorkEffortScope(WorkEffortScope workEffortScope, BasePK deletedBy) {
        var workEffortScopeDescriptions = getWorkEffortScopeDescriptionsByWorkEffortScopeForUpdate(workEffortScope);
        
        workEffortScopeDescriptions.forEach((workEffortScopeDescription) -> 
                deleteWorkEffortScopeDescription(workEffortScopeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Work Efforts
    // --------------------------------------------------------------------------------
    
    public WorkEffort createWorkEffort(String workEffortName, EntityInstance owningEntityInstanceId, WorkEffortScope workEffortScope, Long scheduledTime,
            Long scheduledStartTime, Long scheduledEndTime, Long estimatedTimeAllowed, Long maximumTimeAllowed, BasePK createdBy) {
        var workEffort = WorkEffortFactory.getInstance().create();
        var workEffortDetail = WorkEffortDetailFactory.getInstance().create(workEffort, workEffortName, owningEntityInstanceId, workEffortScope,
                scheduledTime, scheduledStartTime, scheduledEndTime, estimatedTimeAllowed, maximumTimeAllowed, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        workEffort = WorkEffortFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                workEffort.getPrimaryKey());
        workEffort.setActiveDetail(workEffortDetail);
        workEffort.setLastDetail(workEffortDetail);
        workEffort.store();
        
        sendEvent(workEffort.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return workEffort;
    }
    
    private List<WorkEffort> getWorkEffortsByWorkEffortScope(WorkEffortScope workEffortScope, EntityPermission entityPermission) {
        List<WorkEffort> workEfforts;

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

            var ps = WorkEffortFactory.getInstance().prepareStatement(query);

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
        List<WorkEffort> workEfforts;

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

            var ps = WorkEffortFactory.getInstance().prepareStatement(query);

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
        WorkEffort workEffort;

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

            var ps = WorkEffortFactory.getInstance().prepareStatement(query);

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
        WorkEffort workEffort;

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

            var ps = WorkEffortFactory.getInstance().prepareStatement(query);

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
    
    public List<WorkEffortTransfer> getWorkEffortTransfers(UserVisit userVisit, Collection<WorkEffort> workEfforts) {
        List<WorkEffortTransfer> workEffortTransfers = new ArrayList<>(workEfforts.size());
        var workEffortTransferCache = getWorkEffortTransferCaches(userVisit).getWorkEffortTransferCache();

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
            var workEffort = WorkEffortFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     workEffortDetailValue.getWorkEffortPK());
            var workEffortDetail = workEffort.getActiveDetailForUpdate();
            
            workEffortDetail.setThruTime(session.START_TIME_LONG);
            workEffortDetail.store();

            var workEffortPK = workEffortDetail.getWorkEffortPK();
            var workEffortName = workEffortDetailValue.getWorkEffortName();
            var owningEntityInstancePK = workEffortDetail.getOwningEntityInstancePK(); // Not updated
            var workEffortScopePK = workEffortDetail.getWorkEffortScopePK(); // Not updated
            var scheduledTime = workEffortDetail.getScheduledTime();
            var scheduledStartTime = workEffortDetail.getScheduledStartTime();
            var scheduledEndTime = workEffortDetail.getScheduledEndTime();
            var estimatedTimeAllowed = workEffortDetail.getEstimatedTimeAllowed();
            var maximumTimeAllowed = workEffortDetail.getMaximumTimeAllowed();
            
            workEffortDetail = WorkEffortDetailFactory.getInstance().create(workEffortPK, workEffortName, owningEntityInstancePK, workEffortScopePK,
                    scheduledTime, scheduledStartTime, scheduledEndTime, estimatedTimeAllowed, maximumTimeAllowed, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            workEffort.setActiveDetail(workEffortDetail);
            workEffort.setLastDetail(workEffortDetail);
            
            sendEvent(workEffortPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deleteWorkEffort(WorkEffort workEffort, BasePK deletedBy) {
        var workRequirementControl = Session.getModelController(WorkRequirementControl.class);
        
        workRequirementControl.deleteWorkRequirementsByWorkEffort(workEffort, deletedBy);

        var workEffortDetail = workEffort.getLastDetailForUpdate();
        workEffortDetail.setThruTime(session.START_TIME_LONG);
        workEffort.setActiveDetail(null);
        workEffort.store();
        
        sendEvent(workEffort.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
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
