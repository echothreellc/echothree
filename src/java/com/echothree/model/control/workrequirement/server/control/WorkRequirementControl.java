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

package com.echothree.model.control.workrequirement.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.workrequirement.common.choice.WorkAssignmentStatusChoicesBean;
import com.echothree.model.control.workrequirement.common.choice.WorkRequirementStatusChoicesBean;
import com.echothree.model.control.workrequirement.common.choice.WorkTimeStatusChoicesBean;
import com.echothree.model.control.workrequirement.common.transfer.WorkAssignmentTransfer;
import com.echothree.model.control.workrequirement.common.transfer.WorkRequirementScopeTransfer;
import com.echothree.model.control.workrequirement.common.transfer.WorkRequirementTransfer;
import com.echothree.model.control.workrequirement.common.transfer.WorkRequirementTypeDescriptionTransfer;
import com.echothree.model.control.workrequirement.common.transfer.WorkRequirementTypeTransfer;
import com.echothree.model.control.workrequirement.common.transfer.WorkTimeTransfer;
import com.echothree.model.control.workrequirement.common.workflow.WorkAssignmentStatusConstants;
import com.echothree.model.control.workrequirement.common.workflow.WorkRequirementStatusConstants;
import com.echothree.model.control.workrequirement.common.workflow.WorkTimeStatusConstants;
import com.echothree.model.control.workrequirement.server.transfer.WorkAssignmentTransferCache;
import com.echothree.model.control.workrequirement.server.transfer.WorkRequirementScopeTransferCache;
import com.echothree.model.control.workrequirement.server.transfer.WorkRequirementTransferCache;
import com.echothree.model.control.workrequirement.server.transfer.WorkRequirementTypeDescriptionTransferCache;
import com.echothree.model.control.workrequirement.server.transfer.WorkRequirementTypeTransferCache;
import com.echothree.model.control.workrequirement.server.transfer.WorkTimeTransferCache;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workeffort.server.entity.WorkEffort;
import com.echothree.model.data.workeffort.server.entity.WorkEffortScope;
import com.echothree.model.data.workeffort.server.entity.WorkEffortType;
import com.echothree.model.data.workflow.server.entity.WorkflowStep;
import com.echothree.model.data.workrequirement.common.pk.WorkAssignmentPK;
import com.echothree.model.data.workrequirement.common.pk.WorkTimePK;
import com.echothree.model.data.workrequirement.server.entity.WorkAssignment;
import com.echothree.model.data.workrequirement.server.entity.WorkRequirement;
import com.echothree.model.data.workrequirement.server.entity.WorkRequirementScope;
import com.echothree.model.data.workrequirement.server.entity.WorkRequirementStatus;
import com.echothree.model.data.workrequirement.server.entity.WorkRequirementType;
import com.echothree.model.data.workrequirement.server.entity.WorkRequirementTypeDescription;
import com.echothree.model.data.workrequirement.server.entity.WorkTime;
import com.echothree.model.data.workrequirement.server.entity.WorkTimeUserVisit;
import com.echothree.model.data.workrequirement.server.factory.WorkAssignmentDetailFactory;
import com.echothree.model.data.workrequirement.server.factory.WorkAssignmentFactory;
import com.echothree.model.data.workrequirement.server.factory.WorkRequirementDetailFactory;
import com.echothree.model.data.workrequirement.server.factory.WorkRequirementFactory;
import com.echothree.model.data.workrequirement.server.factory.WorkRequirementScopeDetailFactory;
import com.echothree.model.data.workrequirement.server.factory.WorkRequirementScopeFactory;
import com.echothree.model.data.workrequirement.server.factory.WorkRequirementStatusFactory;
import com.echothree.model.data.workrequirement.server.factory.WorkRequirementTypeDescriptionFactory;
import com.echothree.model.data.workrequirement.server.factory.WorkRequirementTypeDetailFactory;
import com.echothree.model.data.workrequirement.server.factory.WorkRequirementTypeFactory;
import com.echothree.model.data.workrequirement.server.factory.WorkTimeDetailFactory;
import com.echothree.model.data.workrequirement.server.factory.WorkTimeFactory;
import com.echothree.model.data.workrequirement.server.factory.WorkTimeUserVisitFactory;
import com.echothree.model.data.workrequirement.server.value.WorkAssignmentDetailValue;
import com.echothree.model.data.workrequirement.server.value.WorkRequirementDetailValue;
import com.echothree.model.data.workrequirement.server.value.WorkRequirementScopeDetailValue;
import com.echothree.model.data.workrequirement.server.value.WorkRequirementTypeDescriptionValue;
import com.echothree.model.data.workrequirement.server.value.WorkRequirementTypeDetailValue;
import com.echothree.model.data.workrequirement.server.value.WorkTimeDetailValue;
import com.echothree.model.data.workrequirement.server.value.WorkTimeUserVisitValue;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.echothree.util.server.cdi.CommandScope;
import javax.inject.Inject;

@CommandScope
public class WorkRequirementControl
        extends BaseModelControl {
    
    /** Creates a new instance of WorkRequirementControl */
    protected WorkRequirementControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Work Requirement Transfer Caches
    // --------------------------------------------------------------------------------

    @Inject
    WorkRequirementTypeDescriptionTransferCache workRequirementTypeDescriptionTransferCache;

    @Inject
    WorkRequirementTypeTransferCache workRequirementTypeTransferCache;

    @Inject
    WorkRequirementScopeTransferCache workRequirementScopeTransferCache;

    @Inject
    WorkRequirementTransferCache workRequirementTransferCache;

    @Inject
    WorkAssignmentTransferCache workAssignmentTransferCache;

    @Inject
    WorkTimeTransferCache workTimeTransferCache;
    
    // --------------------------------------------------------------------------------
    //   Work Requirement Types
    // --------------------------------------------------------------------------------
    
    public WorkRequirementType createWorkRequirementType(WorkEffortType workEffortType, String workRequirementTypeName, Sequence workRequirementSequence,
            WorkflowStep workflowStep, Long estimatedTimeAllowed, Long maximumTimeAllowed, Boolean allowReassignment, Integer sortOrder, BasePK createdBy) {
        var workRequirementType = WorkRequirementTypeFactory.getInstance().create();
        var workRequirementTypeDetail = WorkRequirementTypeDetailFactory.getInstance().create(session, workRequirementType,
                workEffortType, workRequirementTypeName, workRequirementSequence, workflowStep, estimatedTimeAllowed, maximumTimeAllowed, allowReassignment,
                sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        workRequirementType = WorkRequirementTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, workRequirementType.getPrimaryKey());
        workRequirementType.setActiveDetail(workRequirementTypeDetail);
        workRequirementType.setLastDetail(workRequirementTypeDetail);
        workRequirementType.store();
        
        sendEvent(workRequirementType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return workRequirementType;
    }
    
    private List<WorkRequirementType> getWorkRequirementTypes(WorkEffortType workEffortType, EntityPermission entityPermission) {
        List<WorkRequirementType> workRequirementTypes;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM workrequirementtypes, workrequirementtypedetails " +
                        "WHERE wrt_activedetailid = wrtdt_workrequirementtypedetailid AND wrtdt_wet_workefforttypeid = ? " +
                        "ORDER BY wrtdt_sortorder, wrtdt_workrequirementtypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM workrequirementtypes, workrequirementtypedetails " +
                        "WHERE wrt_activedetailid = wrtdt_workrequirementtypedetailid AND wrtdt_wet_workefforttypeid = ? " +
                        "FOR UPDATE";
            }

            var ps = WorkRequirementTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, workEffortType.getPrimaryKey().getEntityId());
            
            workRequirementTypes = WorkRequirementTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return workRequirementTypes;
    }
    
    public List<WorkRequirementType> getWorkRequirementTypes(WorkEffortType workEffortType) {
        return getWorkRequirementTypes(workEffortType, EntityPermission.READ_ONLY);
    }
    
    public List<WorkRequirementType> getWorkRequirementTypesForUpdate(WorkEffortType workEffortType) {
        return getWorkRequirementTypes(workEffortType, EntityPermission.READ_WRITE);
    }
    
    private List<WorkRequirementType> getWorkRequirementTypesByWorkflowStep(WorkflowStep workflowStep, EntityPermission entityPermission) {
        List<WorkRequirementType> workRequirementTypes;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM workrequirementtypes, workrequirementtypedetails, workefforttypes, workefforttypedetails " +
                        "WHERE wrt_activedetailid = wrtdt_workrequirementtypedetailid AND wrtdt_wkfls_workflowstepid = ? " +
                        "AND wrtdt_wet_workefforttypeid = wet_workefforttypeid AND wet_activedetailid = wetdt_workefforttypedetailid " +
                        "ORDER BY wrtdt_sortorder, wrtdt_workrequirementtypename, wetdt_sortorder, wetdt_workefforttypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM workrequirementtypes, workrequirementtypedetails " +
                        "WHERE wrt_activedetailid = wrtdt_workrequirementtypedetailid AND wrtdt_wkfls_workflowstepid = ? " +
                        "FOR UPDATE";
            }

            var ps = WorkRequirementTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, workflowStep.getPrimaryKey().getEntityId());
            
            workRequirementTypes = WorkRequirementTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return workRequirementTypes;
    }
    
    public List<WorkRequirementType> getWorkRequirementTypesByWorkflowStep(WorkflowStep workflowStep) {
        return getWorkRequirementTypesByWorkflowStep(workflowStep, EntityPermission.READ_ONLY);
    }
    
    public List<WorkRequirementType> getWorkRequirementTypesByWorkflowStepForUpdate(WorkflowStep workflowStep) {
        return getWorkRequirementTypesByWorkflowStep(workflowStep, EntityPermission.READ_WRITE);
    }
    
    private WorkRequirementType getWorkRequirementTypeByName(WorkEffortType workEffortType, String workRequirementTypeName,
            EntityPermission entityPermission) {
        WorkRequirementType workRequirementType;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM workrequirementtypes, workrequirementtypedetails " +
                        "WHERE wrt_activedetailid = wrtdt_workrequirementtypedetailid " +
                        "AND wrtdt_wet_workefforttypeid = ? AND wrtdt_workrequirementtypename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM workrequirementtypes, workrequirementtypedetails " +
                        "WHERE wrt_activedetailid = wrtdt_workrequirementtypedetailid " +
                        "AND wrtdt_wet_workefforttypeid = ? AND wrtdt_workrequirementtypename = ? " +
                        "FOR UPDATE";
            }

            var ps = WorkRequirementTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, workEffortType.getPrimaryKey().getEntityId());
            ps.setString(2, workRequirementTypeName);
            
            workRequirementType = WorkRequirementTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return workRequirementType;
    }
    
    public WorkRequirementType getWorkRequirementTypeByName(WorkEffortType workEffortType, String workRequirementTypeName) {
        return getWorkRequirementTypeByName(workEffortType, workRequirementTypeName, EntityPermission.READ_ONLY);
    }
    
    public WorkRequirementType getWorkRequirementTypeByNameForUpdate(WorkEffortType workEffortType, String workRequirementTypeName) {
        return getWorkRequirementTypeByName(workEffortType, workRequirementTypeName, EntityPermission.READ_WRITE);
    }
    
    public WorkRequirementTypeDetailValue getWorkRequirementTypeDetailValueForUpdate(WorkRequirementType workRequirementType) {
        return workRequirementType == null? null: workRequirementType.getLastDetailForUpdate().getWorkRequirementTypeDetailValue().clone();
    }
    
    public WorkRequirementTypeDetailValue getWorkRequirementTypeDetailValueByNameForUpdate(WorkEffortType workEffortType, String workRequirementTypeName) {
        return getWorkRequirementTypeDetailValueForUpdate(getWorkRequirementTypeByNameForUpdate(workEffortType, workRequirementTypeName));
    }
    
    public WorkRequirementTypeTransfer getWorkRequirementTypeTransfer(UserVisit userVisit, WorkRequirementType workRequirementType) {
        return workRequirementTypeTransferCache.getWorkRequirementTypeTransfer(userVisit, workRequirementType);
    }
    
    public List<WorkRequirementTypeTransfer> getWorkRequirementTypeTransfers(UserVisit userVisit, WorkEffortType workEffortType) {
        var workRequirementTypes = getWorkRequirementTypes(workEffortType);
        List<WorkRequirementTypeTransfer> workRequirementTypeTransfers = new ArrayList<>(workRequirementTypes.size());
        
        workRequirementTypes.forEach((workRequirementType) ->
                workRequirementTypeTransfers.add(workRequirementTypeTransferCache.getWorkRequirementTypeTransfer(userVisit, workRequirementType))
        );
        
        return workRequirementTypeTransfers;
    }
    
    public void updateWorkRequirementTypeFromValue(WorkRequirementTypeDetailValue workRequirementTypeDetailValue, BasePK updatedBy) {
        if(workRequirementTypeDetailValue.hasBeenModified()) {
            var workRequirementType = WorkRequirementTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     workRequirementTypeDetailValue.getWorkRequirementTypePK());
            var workRequirementTypeDetail = workRequirementType.getActiveDetailForUpdate();
            
            workRequirementTypeDetail.setThruTime(session.getStartTime());
            workRequirementTypeDetail.store();

            var workRequirementTypePK = workRequirementTypeDetail.getWorkRequirementTypePK();
            var workEffortTypePK = workRequirementTypeDetail.getWorkEffortTypePK(); // Not updated
            var workRequirementTypeName = workRequirementTypeDetailValue.getWorkRequirementTypeName();
            var workRequirementSequencePK = workRequirementTypeDetailValue.getWorkRequirementSequencePK();
            var workflowStepPK = workRequirementTypeDetailValue.getWorkflowStepPK();
            var estimatedTimeAllowed = workRequirementTypeDetailValue.getEstimatedTimeAllowed();
            var maximumTimeAllowed = workRequirementTypeDetailValue.getMaximumTimeAllowed();
            var allowReassignment = workRequirementTypeDetailValue.getAllowReassignment();
            var sortOrder = workRequirementTypeDetailValue.getSortOrder();
            
            workRequirementTypeDetail = WorkRequirementTypeDetailFactory.getInstance().create(workRequirementTypePK, workEffortTypePK, workRequirementTypeName,
                    workRequirementSequencePK, workflowStepPK, estimatedTimeAllowed, maximumTimeAllowed, allowReassignment, sortOrder, session.getStartTime(),
                    Session.MAX_TIME);
            
            workRequirementType.setActiveDetail(workRequirementTypeDetail);
            workRequirementType.setLastDetail(workRequirementTypeDetail);
            
            sendEvent(workRequirementTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deleteWorkRequirementType(WorkRequirementType workRequirementType, BasePK deletedBy) {
        deleteWorkRequirementTypeDescriptionsByWorkRequirementType(workRequirementType, deletedBy);
        deleteWorkRequirementScopesByWorkRequirementType(workRequirementType, deletedBy);

        var workRequirementTypeDetail = workRequirementType.getLastDetailForUpdate();
        workRequirementTypeDetail.setThruTime(session.getStartTime());
        workRequirementType.setActiveDetail(null);
        workRequirementType.store();
        
        sendEvent(workRequirementType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteWorkRequirementTypesByWorkEffortType(WorkEffortType workEffortType, BasePK deletedBy) {
        var workRequirementTypes = getWorkRequirementTypesForUpdate(workEffortType);
        
        workRequirementTypes.forEach((workRequirementType) -> 
                deleteWorkRequirementType(workRequirementType, deletedBy)
        );
    }
    
    public void deleteWorkRequirementTypesByWorkflowStep(WorkflowStep workflowStep, BasePK deletedBy) {
        var workRequirementTypes = getWorkRequirementTypesByWorkflowStepForUpdate(workflowStep);
        
        workRequirementTypes.forEach((workRequirementType) -> 
                deleteWorkRequirementType(workRequirementType, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Work Requirement Type Descriptions
    // --------------------------------------------------------------------------------
    
    public WorkRequirementTypeDescription createWorkRequirementTypeDescription(WorkRequirementType workRequirementType, Language language,
            String description, BasePK createdBy) {
        var workRequirementTypeDescription = WorkRequirementTypeDescriptionFactory.getInstance().create(workRequirementType,
                language, description, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(workRequirementType.getPrimaryKey(), EventTypes.MODIFY, workRequirementTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return workRequirementTypeDescription;
    }
    
    private WorkRequirementTypeDescription getWorkRequirementTypeDescription(WorkRequirementType workRequirementType, Language language,
            EntityPermission entityPermission) {
        WorkRequirementTypeDescription workRequirementTypeDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM workrequirementtypedescriptions " +
                        "WHERE wrtd_wrt_workrequirementtypeid = ? AND wrtd_lang_languageid = ? AND wrtd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM workrequirementtypedescriptions " +
                        "WHERE wrtd_wrt_workrequirementtypeid = ? AND wrtd_lang_languageid = ? AND wrtd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = WorkRequirementTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, workRequirementType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            workRequirementTypeDescription = WorkRequirementTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return workRequirementTypeDescription;
    }
    
    public WorkRequirementTypeDescription getWorkRequirementTypeDescription(WorkRequirementType workRequirementType, Language language) {
        return getWorkRequirementTypeDescription(workRequirementType, language, EntityPermission.READ_ONLY);
    }
    
    public WorkRequirementTypeDescription getWorkRequirementTypeDescriptionForUpdate(WorkRequirementType workRequirementType, Language language) {
        return getWorkRequirementTypeDescription(workRequirementType, language, EntityPermission.READ_WRITE);
    }
    
    public WorkRequirementTypeDescriptionValue getWorkRequirementTypeDescriptionValue(WorkRequirementTypeDescription workRequirementTypeDescription) {
        return workRequirementTypeDescription == null? null: workRequirementTypeDescription.getWorkRequirementTypeDescriptionValue().clone();
    }
    
    public WorkRequirementTypeDescriptionValue getWorkRequirementTypeDescriptionValueForUpdate(WorkRequirementType workRequirementType, Language language) {
        return getWorkRequirementTypeDescriptionValue(getWorkRequirementTypeDescriptionForUpdate(workRequirementType, language));
    }
    
    private List<WorkRequirementTypeDescription> getWorkRequirementTypeDescriptionsByWorkRequirementType(WorkRequirementType workRequirementType, EntityPermission entityPermission) {
        List<WorkRequirementTypeDescription> workRequirementTypeDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM workrequirementtypedescriptions, languages " +
                        "WHERE wrtd_wrt_workrequirementtypeid = ? AND wrtd_thrutime = ? AND wrtd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM workrequirementtypedescriptions " +
                        "WHERE wrtd_wrt_workrequirementtypeid = ? AND wrtd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = WorkRequirementTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, workRequirementType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            workRequirementTypeDescriptions = WorkRequirementTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return workRequirementTypeDescriptions;
    }
    
    public List<WorkRequirementTypeDescription> getWorkRequirementTypeDescriptionsByWorkRequirementType(WorkRequirementType workRequirementType) {
        return getWorkRequirementTypeDescriptionsByWorkRequirementType(workRequirementType, EntityPermission.READ_ONLY);
    }
    
    public List<WorkRequirementTypeDescription> getWorkRequirementTypeDescriptionsByWorkRequirementTypeForUpdate(WorkRequirementType workRequirementType) {
        return getWorkRequirementTypeDescriptionsByWorkRequirementType(workRequirementType, EntityPermission.READ_WRITE);
    }
    
    public String getBestWorkRequirementTypeDescription(WorkRequirementType workRequirementType, Language language) {
        String description;
        var workRequirementTypeDescription = getWorkRequirementTypeDescription(workRequirementType, language);
        
        if(workRequirementTypeDescription == null && !language.getIsDefault()) {
            workRequirementTypeDescription = getWorkRequirementTypeDescription(workRequirementType, partyControl.getDefaultLanguage());
        }
        
        if(workRequirementTypeDescription == null) {
            description = workRequirementType.getLastDetail().getWorkRequirementTypeName();
        } else {
            description = workRequirementTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public WorkRequirementTypeDescriptionTransfer getWorkRequirementTypeDescriptionTransfer(UserVisit userVisit, WorkRequirementTypeDescription workRequirementTypeDescription) {
        return workRequirementTypeDescriptionTransferCache.getWorkRequirementTypeDescriptionTransfer(userVisit, workRequirementTypeDescription);
    }
    
    public List<WorkRequirementTypeDescriptionTransfer> getWorkRequirementTypeDescriptionTransfers(UserVisit userVisit, WorkRequirementType workRequirementType) {
        var workRequirementTypeDescriptions = getWorkRequirementTypeDescriptionsByWorkRequirementType(workRequirementType);
        List<WorkRequirementTypeDescriptionTransfer> workRequirementTypeDescriptionTransfers = new ArrayList<>(workRequirementTypeDescriptions.size());
        
        workRequirementTypeDescriptions.forEach((workRequirementTypeDescription) ->
                workRequirementTypeDescriptionTransfers.add(workRequirementTypeDescriptionTransferCache.getWorkRequirementTypeDescriptionTransfer(userVisit, workRequirementTypeDescription))
        );
        
        return workRequirementTypeDescriptionTransfers;
    }
    
    public void updateWorkRequirementTypeDescriptionFromValue(WorkRequirementTypeDescriptionValue workRequirementTypeDescriptionValue, BasePK updatedBy) {
        if(workRequirementTypeDescriptionValue.hasBeenModified()) {
            var workRequirementTypeDescription = WorkRequirementTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     workRequirementTypeDescriptionValue.getPrimaryKey());
            
            workRequirementTypeDescription.setThruTime(session.getStartTime());
            workRequirementTypeDescription.store();

            var workRequirementType = workRequirementTypeDescription.getWorkRequirementType();
            var language = workRequirementTypeDescription.getLanguage();
            var description = workRequirementTypeDescriptionValue.getDescription();
            
            workRequirementTypeDescription = WorkRequirementTypeDescriptionFactory.getInstance().create(workRequirementType, language,
                    description, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(workRequirementType.getPrimaryKey(), EventTypes.MODIFY, workRequirementTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteWorkRequirementTypeDescription(WorkRequirementTypeDescription workRequirementTypeDescription, BasePK deletedBy) {
        workRequirementTypeDescription.setThruTime(session.getStartTime());
        
        sendEvent(workRequirementTypeDescription.getWorkRequirementTypePK(), EventTypes.MODIFY, workRequirementTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteWorkRequirementTypeDescriptionsByWorkRequirementType(WorkRequirementType workRequirementType, BasePK deletedBy) {
        var workRequirementTypeDescriptions = getWorkRequirementTypeDescriptionsByWorkRequirementTypeForUpdate(workRequirementType);
        
        workRequirementTypeDescriptions.forEach((workRequirementTypeDescription) -> 
                deleteWorkRequirementTypeDescription(workRequirementTypeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Work Requirement Scopes
    // --------------------------------------------------------------------------------
    
    public WorkRequirementScope createWorkRequirementScope(WorkEffortScope workEffortScope, WorkRequirementType workRequirementType,
            Sequence workRequirementSequence, Sequence workTimeSequence, Selector workAssignmentSelector, Long estimatedTimeAllowed,
            Long maximumTimeAllowed, BasePK createdBy) {
        var workRequirementScope = WorkRequirementScopeFactory.getInstance().create();
        var workRequirementScopeDetail = WorkRequirementScopeDetailFactory.getInstance().create(session,
                workRequirementScope, workEffortScope, workRequirementType, workRequirementSequence, workTimeSequence,
                workAssignmentSelector, estimatedTimeAllowed, maximumTimeAllowed, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        workRequirementScope = WorkRequirementScopeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                workRequirementScope.getPrimaryKey());
        workRequirementScope.setActiveDetail(workRequirementScopeDetail);
        workRequirementScope.setLastDetail(workRequirementScopeDetail);
        workRequirementScope.store();
        
        sendEvent(workRequirementScope.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return workRequirementScope;
    }
    
    private WorkRequirementScope getWorkRequirementScope(WorkEffortScope workEffortScope, WorkRequirementType workRequirementType,
            EntityPermission entityPermission) {
        WorkRequirementScope workRequirementScope;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM workrequirementscopes, workrequirementscopedetails " +
                        "WHERE wrs_activedetailid = wrsdt_workrequirementscopedetailid AND wrsdt_wes_workeffortscopeid = ? AND wrsdt_wrt_workrequirementtypeid = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM workrequirementscopes, workrequirementscopedetails " +
                        "WHERE wrs_activedetailid = wrsdt_workrequirementscopedetailid AND wrsdt_wes_workeffortscopeid = ? AND wrsdt_wrt_workrequirementtypeid = ?  " +
                        "FOR UPDATE";
            }

            var ps = WorkRequirementScopeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, workEffortScope.getPrimaryKey().getEntityId());
            ps.setLong(2, workRequirementType.getPrimaryKey().getEntityId());
            
            workRequirementScope = WorkRequirementScopeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return workRequirementScope;
    }
    
    public WorkRequirementScope getWorkRequirementScope(WorkEffortScope workEffortScope, WorkRequirementType workRequirementType) {
        return getWorkRequirementScope(workEffortScope, workRequirementType, EntityPermission.READ_ONLY);
    }
    
    public WorkRequirementScope getWorkRequirementScopeForUpdate(WorkEffortScope workEffortScope, WorkRequirementType workRequirementType) {
        return getWorkRequirementScope(workEffortScope, workRequirementType, EntityPermission.READ_WRITE);
    }
    
    private List<WorkRequirementScope> getWorkRequirementScopesByWorkEffortScopeAndWorkflowStep(WorkEffortScope workEffortScope,
            WorkflowStep workflowStep, EntityPermission entityPermission) {
        List<WorkRequirementScope> workRequirementScopes;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM workeffortscopes, workeffortscopedetails, workrequirementscopes, workrequirementscopedetails, workrequirementtypes, workrequirementtypedetails " +
                        "WHERE wes_activedetailid = wesdt_workeffortscopedetailid AND wes_workeffortscopeid = ? " +
                        "AND wrs_activedetailid = wrsdt_workrequirementscopedetailid AND wes_workeffortscopeid = wrsdt_wes_workeffortscopeid " +
                        "AND wrt_activedetailid = wrtdt_workrequirementtypedetailid AND wrtdt_wkfls_workflowstepid = ? " +
                        "AND wrt_workrequirementtypeid = wrsdt_wrt_workrequirementtypeid"; // TODO: ORDER BY something
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM workeffortscopes, workeffortscopedetails, workrequirementscopes, workrequirementscopedetails, workrequirementtypes, workrequirementtypedetails " +
                        "WHERE wes_activedetailid = wesdt_workeffortscopedetailid AND wes_workeffortscopeid = ? " +
                        "AND wrs_activedetailid = wrsdt_workrequirementscopedetailid AND wes_workeffortscopeid = wrsdt_wes_workeffortscopeid " +
                        "AND wrt_activedetailid = wrtdt_workrequirementtypedetailid AND wrtdt_wkfls_workflowstepid = ? " +
                        "AND wrt_workrequirementtypeid = wrsdt_wrt_workrequirementtypeid " +
                        "FOR UPDATE";
            }

            var ps = WorkRequirementScopeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, workEffortScope.getPrimaryKey().getEntityId());
            ps.setLong(2, workflowStep.getPrimaryKey().getEntityId());
            
            workRequirementScopes = WorkRequirementScopeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return workRequirementScopes;
    }
    
    public List<WorkRequirementScope> getWorkRequirementScopesByWorkRequirementType(WorkEffortScope workEffortScope,
            WorkflowStep workflowStep) {
        return getWorkRequirementScopesByWorkEffortScopeAndWorkflowStep(workEffortScope, workflowStep, EntityPermission.READ_ONLY);
    }
    
    public List<WorkRequirementScope> getWorkRequirementScopesByWorkRequirementTypeForUpdate(WorkEffortScope workEffortScope,
            WorkflowStep workflowStep) {
        return getWorkRequirementScopesByWorkEffortScopeAndWorkflowStep(workEffortScope, workflowStep, EntityPermission.READ_WRITE);
    }
    
    private List<WorkRequirementScope> getWorkRequirementScopesByWorkRequirementType(WorkRequirementType workRequirementType,
            EntityPermission entityPermission) {
        List<WorkRequirementScope> workRequirementScopes;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM workrequirementscopes, workrequirementscopedetails, workeffortscopes, workeffortscopedetails " +
                        "WHERE wrs_activedetailid = wrsdt_workrequirementscopedetailid AND wrsdt_wrt_workrequirementtypeid = ? " +
                        "AND wrsdt_wes_workeffortscopeid = wes_workeffortscopeid AND wes_activedetailid = wesdt_workeffortscopedetailid " +
                        "ORDER BY wesdt_sortorder, wesdt_workeffortscopename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM workrequirementscopes, workrequirementscopedetails " +
                        "WHERE wrs_activedetailid = wrsdt_workrequirementscopedetailid AND wrsdt_wrt_workrequirementtypeid = ? " +
                        "FOR UPDATE";
            }

            var ps = WorkRequirementScopeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, workRequirementType.getPrimaryKey().getEntityId());
            
            workRequirementScopes = WorkRequirementScopeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return workRequirementScopes;
    }
    
    public List<WorkRequirementScope> getWorkRequirementScopesByWorkRequirementType(WorkRequirementType workRequirementType) {
        return getWorkRequirementScopesByWorkRequirementType(workRequirementType, EntityPermission.READ_ONLY);
    }
    
    public List<WorkRequirementScope> getWorkRequirementScopesByWorkRequirementTypeForUpdate(WorkRequirementType workRequirementType) {
        return getWorkRequirementScopesByWorkRequirementType(workRequirementType, EntityPermission.READ_WRITE);
    }
    
    private List<WorkRequirementScope> getWorkRequirementScopesByWorkEffortScope(WorkEffortScope workEffortScope, EntityPermission entityPermission) {
        List<WorkRequirementScope> workRequirementScopes;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM workrequirementscopes, workrequirementscopedetails, workrequirementtypes, workrequirementtypedetails " +
                        "WHERE wrs_activedetailid = wrsdt_workrequirementscopedetailid AND wrsdt_wes_workeffortscopeid = ? " +
                        "AND wrsdt_wrt_workrequirementtypeid = wrt_workrequirementtypeid AND wrt_activedetailid = wrtdt_workrequirementtypedetailid " +
                        "ORDER BY wrtdt_sortorder, wrtdt_workrequirementtypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM workrequirementscopes, workrequirementscopedetails " +
                        "WHERE wrs_activedetailid = wrsdt_workrequirementscopedetailid AND wrsdt_wes_workeffortscopeid = ? " +
                        "FOR UPDATE";
            }

            var ps = WorkRequirementScopeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, workEffortScope.getPrimaryKey().getEntityId());
            
            workRequirementScopes = WorkRequirementScopeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return workRequirementScopes;
    }
    
    public List<WorkRequirementScope> getWorkRequirementScopesByWorkEffortScope(WorkEffortScope workEffortScope) {
        return getWorkRequirementScopesByWorkEffortScope(workEffortScope, EntityPermission.READ_ONLY);
    }
    
    public List<WorkRequirementScope> getWorkRequirementScopesByWorkEffortScopeForUpdate(WorkEffortScope workEffortScope) {
        return getWorkRequirementScopesByWorkEffortScope(workEffortScope, EntityPermission.READ_WRITE);
    }
    
    public WorkRequirementScopeDetailValue getWorkRequirementScopeDetailValueForUpdate(WorkRequirementScope workRequirementScope) {
        return workRequirementScope == null? null: workRequirementScope.getLastDetailForUpdate().getWorkRequirementScopeDetailValue().clone();
    }
    
    public WorkRequirementScopeTransfer getWorkRequirementScopeTransfer(UserVisit userVisit, WorkRequirementScope workRequirementScope) {
        return workRequirementScopeTransferCache.getWorkRequirementScopeTransfer(userVisit, workRequirementScope);
    }
    
    public List<WorkRequirementScopeTransfer> getWorkRequirementScopeTransfers(UserVisit userVisit, Collection<WorkRequirementScope> workRequirementScopes) {
        List<WorkRequirementScopeTransfer> workRequirementScopeTransfers = new ArrayList<>(workRequirementScopes.size());
        
        workRequirementScopes.forEach((workRequirementScope) ->
                workRequirementScopeTransfers.add(workRequirementScopeTransferCache.getWorkRequirementScopeTransfer(userVisit, workRequirementScope))
        );
        
        return workRequirementScopeTransfers;
    }
    
    public List<WorkRequirementScopeTransfer> getWorkRequirementScopeTransfersByWorkEffortScope(UserVisit userVisit, WorkEffortScope workEffortScope) {
        return getWorkRequirementScopeTransfers(userVisit, getWorkRequirementScopesByWorkEffortScope(workEffortScope));
    }
    
    public List<WorkRequirementScopeTransfer> getWorkRequirementScopeTransfersByWorkRequirementType(UserVisit userVisit, WorkRequirementType workRequirementType) {
        return getWorkRequirementScopeTransfers(userVisit, getWorkRequirementScopesByWorkRequirementType(workRequirementType));
    }
    
    public void updateWorkRequirementScopeFromValue(WorkRequirementScopeDetailValue workRequirementScopeDetailValue, BasePK updatedBy) {
        if(workRequirementScopeDetailValue.hasBeenModified()) {
            var workRequirementScope = WorkRequirementScopeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     workRequirementScopeDetailValue.getWorkRequirementScopePK());
            var workRequirementScopeDetail = workRequirementScope.getActiveDetailForUpdate();
            
            workRequirementScopeDetail.setThruTime(session.getStartTime());
            workRequirementScopeDetail.store();

            var workRequirementScopePK = workRequirementScopeDetail.getWorkRequirementScopePK();
            var workEffortScopePK = workRequirementScopeDetail.getWorkEffortScopePK(); // Not updated
            var workRequirementTypePK = workRequirementScopeDetail.getWorkRequirementTypePK();
            var workRequirementSequencePK = workRequirementScopeDetailValue.getWorkRequirementSequencePK();
            var workTimeSequencePK = workRequirementScopeDetailValue.getWorkTimeSequencePK();
            var workAssignmentSelectorPK = workRequirementScopeDetailValue.getWorkAssignmentSelectorPK();
            var estimatedTimeAllowed = workRequirementScopeDetailValue.getEstimatedTimeAllowed();
            var maximumTimeAllowed = workRequirementScopeDetailValue.getMaximumTimeAllowed();
            
            workRequirementScopeDetail = WorkRequirementScopeDetailFactory.getInstance().create(workRequirementScopePK,
                    workEffortScopePK, workRequirementTypePK, workRequirementSequencePK, workTimeSequencePK,
                    workAssignmentSelectorPK, estimatedTimeAllowed, maximumTimeAllowed, session.getStartTime(),
                    Session.MAX_TIME);
            
            workRequirementScope.setActiveDetail(workRequirementScopeDetail);
            workRequirementScope.setLastDetail(workRequirementScopeDetail);
            
            sendEvent(workRequirementScopePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deleteWorkRequirementScope(WorkRequirementScope workRequirementScope, BasePK deletedBy) {
        deleteWorkRequirementsByWorkRequirementScope(workRequirementScope, deletedBy);

        var workRequirementScopeDetail = workRequirementScope.getLastDetailForUpdate();
        workRequirementScopeDetail.setThruTime(session.getStartTime());
        workRequirementScope.setActiveDetail(null);
        workRequirementScope.store();
        
        sendEvent(workRequirementScope.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteWorkRequirementScopesByWorkRequirementType(WorkRequirementType workRequirementType, BasePK deletedBy) {
        var workRequirementScopes = getWorkRequirementScopesByWorkRequirementTypeForUpdate(workRequirementType);
        
        workRequirementScopes.forEach((workRequirementScope) -> 
                deleteWorkRequirementScope(workRequirementScope, deletedBy)
        );
    }
    
    public void deleteWorkRequirementScopesByWorkEffortScope(WorkEffortScope workEffortScope, BasePK deletedBy) {
        var workRequirementScopes = getWorkRequirementScopesByWorkEffortScopeForUpdate(workEffortScope);
        
        workRequirementScopes.forEach((workRequirementScope) -> 
                deleteWorkRequirementScope(workRequirementScope, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Work Requirements
    // --------------------------------------------------------------------------------
    
    public WorkRequirement createWorkRequirement(String workRequirementName, WorkEffort workEffort, WorkRequirementScope workRequirementScope, Long startTime,
            Long requiredTime, BasePK createdBy) {
        var workRequirement = WorkRequirementFactory.getInstance().create();
        var workRequirementDetail = WorkRequirementDetailFactory.getInstance().create(workRequirement,
                workRequirementName, workEffort, workRequirementScope, startTime, requiredTime, session.getStartTime(),
                Session.MAX_TIME);
        
        // Convert to R/W
        workRequirement = WorkRequirementFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                workRequirement.getPrimaryKey());
        workRequirement.setActiveDetail(workRequirementDetail);
        workRequirement.setLastDetail(workRequirementDetail);
        workRequirement.store();
        
        sendEvent(workRequirement.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        createWorkRequirementStatus(workRequirement);
        
        return workRequirement;
    }
    
    private List<WorkRequirement> getWorkRequirementsByWorkRequirementScope(WorkRequirementScope workRequirementScope, EntityPermission entityPermission) {
        List<WorkRequirement> workRequirements;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM workrequirements, workrequirementdetails " +
                        "WHERE wr_activedetailid = wrdt_workrequirementdetailid AND wrdt_wrs_workrequirementscopeid = ? " +
                        "ORDER BY wrdt_workrequirementname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM workrequirements, workrequirementdetails " +
                        "WHERE wr_activedetailid = wrdt_workrequirementdetailid AND wrdt_wrs_workrequirementscopeid = ? " +
                        "FOR UPDATE";
            }

            var ps = WorkRequirementFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, workRequirementScope.getPrimaryKey().getEntityId());
            
            workRequirements = WorkRequirementFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return workRequirements;
    }
    
    public List<WorkRequirement> getWorkRequirementsByWorkRequirementScope(WorkRequirementScope workRequirementScope) {
        return getWorkRequirementsByWorkRequirementScope(workRequirementScope, EntityPermission.READ_ONLY);
    }
    
    public List<WorkRequirement> getWorkRequirementsByWorkRequirementScopeForUpdate(WorkRequirementScope workRequirementScope) {
        return getWorkRequirementsByWorkRequirementScope(workRequirementScope, EntityPermission.READ_WRITE);
    }
    
    private List<WorkRequirement> getWorkRequirementsByWorkEffort(WorkEffort workEffort, EntityPermission entityPermission) {
        List<WorkRequirement> workRequirements;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM workrequirements, workrequirementdetails " +
                        "WHERE wr_activedetailid = wrdt_workrequirementdetailid AND wrdt_weff_workeffortid = ? " +
                        "ORDER BY wrdt_workrequirementname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM workrequirements, workrequirementdetails " +
                        "WHERE wr_activedetailid = wrdt_workrequirementdetailid AND wrdt_weff_workeffortid = ? " +
                        "FOR UPDATE";
            }

            var ps = WorkRequirementFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, workEffort.getPrimaryKey().getEntityId());
            
            workRequirements = WorkRequirementFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return workRequirements;
    }
    
    public List<WorkRequirement> getWorkRequirementsByWorkEffort(WorkEffort workEffort) {
        return getWorkRequirementsByWorkEffort(workEffort, EntityPermission.READ_ONLY);
    }
    
    public List<WorkRequirement> getWorkRequirementsByWorkEffortForUpdate(WorkEffort workEffort) {
        return getWorkRequirementsByWorkEffort(workEffort, EntityPermission.READ_WRITE);
    }
    
    private WorkRequirement getWorkRequirementByName(String workRequirementName, EntityPermission entityPermission) {
        WorkRequirement workRequirement;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM workrequirements, workrequirementdetails " +
                        "WHERE wr_activedetailid = wrdt_workrequirementdetailid " +
                        "AND wrdt_workrequirementname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM workrequirements, workrequirementdetails " +
                        "WHERE wr_activedetailid = wrdt_workrequirementdetailid " +
                        "AND wrdt_workrequirementname = ? " +
                        "FOR UPDATE";
            }

            var ps = WorkRequirementFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, workRequirementName);
            
            workRequirement = WorkRequirementFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return workRequirement;
    }
    
    public WorkRequirement getWorkRequirementByName(String workRequirementName) {
        return getWorkRequirementByName(workRequirementName, EntityPermission.READ_ONLY);
    }
    
    public WorkRequirement getWorkRequirementByNameForUpdate(String workRequirementName) {
        return getWorkRequirementByName(workRequirementName, EntityPermission.READ_WRITE);
    }
    
    public WorkRequirementDetailValue getWorkRequirementDetailValueForUpdate(WorkRequirement workRequirement) {
        return workRequirement == null? null: workRequirement.getLastDetailForUpdate().getWorkRequirementDetailValue().clone();
    }
    
    public WorkRequirementDetailValue getWorkRequirementDetailValueByNameForUpdate(String workRequirementName) {
        return getWorkRequirementDetailValueForUpdate(getWorkRequirementByNameForUpdate(workRequirementName));
    }
    
    public WorkRequirementStatusChoicesBean getWorkRequirementStatusChoices(String defaultWorkRequirementStatusChoice, Language language,
            boolean allowNullChoice, WorkRequirement workRequirement, PartyPK partyPK) {
        var workRequirementStatusChoicesBean = new WorkRequirementStatusChoicesBean();

        if(workRequirement == null) {
            workflowControl.getWorkflowEntranceChoices(workRequirementStatusChoicesBean, defaultWorkRequirementStatusChoice, language, allowNullChoice,
                    workflowControl.getWorkflowByName(WorkRequirementStatusConstants.Workflow_WORK_REQUIREMENT_STATUS), partyPK);
        } else {
            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(workRequirement.getPrimaryKey());
            var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(WorkRequirementStatusConstants.Workflow_WORK_REQUIREMENT_STATUS,
                    entityInstance);

            workflowControl.getWorkflowDestinationChoices(workRequirementStatusChoicesBean, defaultWorkRequirementStatusChoice, language, allowNullChoice,
                    workflowEntityStatus.getWorkflowStep(), partyPK);
        }

        return workRequirementStatusChoicesBean;
    }

    public void setWorkRequirementStatus(ExecutionErrorAccumulator eea, WorkRequirement workRequirement, String workRequirementStatusChoice, PartyPK modifiedBy) {
        var entityInstance = getEntityInstanceByBaseEntity(workRequirement);
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(WorkRequirementStatusConstants.Workflow_WORK_REQUIREMENT_STATUS,
                entityInstance);
        var workflowDestination = workRequirementStatusChoice == null ? null
                : workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), workRequirementStatusChoice);

        if(workflowDestination != null || workRequirementStatusChoice == null) {
            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, null, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownWorkRequirementStatusChoice.name(), workRequirementStatusChoice);
        }
    }

    public WorkRequirementTransfer getWorkRequirementTransfer(UserVisit userVisit, WorkRequirement workRequirement) {
        return workRequirementTransferCache.getWorkRequirementTransfer(userVisit, workRequirement);
    }
    
    public List<WorkRequirementTransfer> getWorkRequirementTransfers(UserVisit userVisit, Collection<WorkRequirement> workRequirements) {
        List<WorkRequirementTransfer> workRequirementTransfers = new ArrayList<>(workRequirements.size());
        
        workRequirements.forEach((workRequirement) ->
                workRequirementTransfers.add(workRequirementTransferCache.getWorkRequirementTransfer(userVisit, workRequirement))
        );
        
        return workRequirementTransfers;
    }
    
    public List<WorkRequirementTransfer> getWorkRequirementTransfersByWorkEffort(UserVisit userVisit, WorkEffort workEffort) {
        return getWorkRequirementTransfers(userVisit, getWorkRequirementsByWorkEffort(workEffort));
    }
    
    public List<WorkRequirementTransfer> getWorkRequirementTransfersByWorkRequirementScope(UserVisit userVisit, WorkRequirementScope workRequirementScope) {
        return getWorkRequirementTransfers(userVisit, getWorkRequirementsByWorkRequirementScope(workRequirementScope));
    }
    
    public void updateWorkRequirementFromValue(WorkRequirementDetailValue workRequirementDetailValue, BasePK updatedBy) {
        if(workRequirementDetailValue.hasBeenModified()) {
            var workRequirement = WorkRequirementFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     workRequirementDetailValue.getWorkRequirementPK());
            var workRequirementDetail = workRequirement.getActiveDetailForUpdate();
            
            workRequirementDetail.setThruTime(session.getStartTime());
            workRequirementDetail.store();

            var workRequirementPK = workRequirementDetail.getWorkRequirementPK();
            var workRequirementName = workRequirementDetailValue.getWorkRequirementName();
            var workEffortPK = workRequirementDetail.getWorkEffortPK(); // Not updated
            var workRequirementScopePK = workRequirementDetail.getWorkRequirementScopePK(); // Not updated
            var startTime = workRequirementDetail.getStartTime();
            var requiredTime = workRequirementDetail.getRequiredTime();
            
            workRequirementDetail = WorkRequirementDetailFactory.getInstance().create(workRequirementPK,
                    workRequirementName, workEffortPK, workRequirementScopePK, startTime, requiredTime, session.getStartTime(),
                    Session.MAX_TIME);
            
            workRequirement.setActiveDetail(workRequirementDetail);
            workRequirement.setLastDetail(workRequirementDetail);
            
            sendEvent(workRequirementPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deleteWorkRequirement(WorkRequirement workRequirement, BasePK deletedBy) {
        removeWorkRequirementStatusByWorkRequirement(workRequirement);
        deleteWorkAssignmentsByWorkRequirement(workRequirement, deletedBy);
        deleteWorkTimesByWorkRequirement(workRequirement, deletedBy);

        var workRequirementDetail = workRequirement.getLastDetailForUpdate();
        workRequirementDetail.setThruTime(session.getStartTime());
        workRequirement.setActiveDetail(null);
        workRequirement.store();
        
        sendEvent(workRequirement.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteWorkRequirementsByWorkRequirementScope(WorkRequirementScope workRequirementScope, BasePK deletedBy) {
        var workRequirements = getWorkRequirementsByWorkRequirementScopeForUpdate(workRequirementScope);
        
        workRequirements.forEach((workRequirement) -> 
                deleteWorkRequirement(workRequirement, deletedBy)
        );
    }
    
    public void deleteWorkRequirementsByWorkEffort(WorkEffort workEffort, BasePK deletedBy) {
        var workRequirements = getWorkRequirementsByWorkEffortForUpdate(workEffort);
        
        workRequirements.forEach((workRequirement) -> 
                deleteWorkRequirement(workRequirement, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Work Requirement Statuses
    // --------------------------------------------------------------------------------
    
    public WorkRequirementStatus createWorkRequirementStatus(WorkRequirement workRequirement) {
        return WorkRequirementStatusFactory.getInstance().create(workRequirement, 0, null, 0, null);
    }
    
    private static final Map<EntityPermission, String> getWorkRequirementStatusQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM workrequirementstatuses " +
                "WHERE wrst_wr_workrequirementid = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM workrequirementstatuses " +
                "WHERE wrst_wr_workrequirementid = ? " +
                "FOR UPDATE");
        getWorkRequirementStatusQueries = Collections.unmodifiableMap(queryMap);
    }

    private WorkRequirementStatus getWorkRequirementStatus(WorkRequirement workRequirement, EntityPermission entityPermission) {
        return WorkRequirementStatusFactory.getInstance().getEntityFromQuery(entityPermission, getWorkRequirementStatusQueries,
                workRequirement);
    }

    public WorkRequirementStatus getWorkRequirementStatus(WorkRequirement workRequirement) {
        return getWorkRequirementStatus(workRequirement, EntityPermission.READ_ONLY);
    }

    public WorkRequirementStatus getWorkRequirementStatusForUpdate(WorkRequirement workRequirement) {
        return getWorkRequirementStatus(workRequirement, EntityPermission.READ_WRITE);
    }

    public void removeWorkRequirementStatusByWorkRequirement(WorkRequirement workRequirement) {
        var workRequirementStatus = getWorkRequirementStatusForUpdate(workRequirement);

        if(workRequirementStatus != null) {
            workRequirementStatus.remove();
        }
    }

    // --------------------------------------------------------------------------------
    //   Work Assignments
    // --------------------------------------------------------------------------------
    
    public WorkAssignment createWorkAssignment(WorkRequirement workRequirement, Party party, Long startTime, Long endTime, BasePK createdBy) {
        var workRequirementStatus = getWorkRequirementStatusForUpdate(workRequirement);
        Integer workAssignmentSequence = workRequirementStatus.getWorkAssignmentSequence() + 1;

        workRequirementStatus.setWorkAssignmentSequence(workAssignmentSequence);

        return createWorkAssignment(workRequirement,  workAssignmentSequence, party, startTime, endTime, createdBy);
    }

    public WorkAssignment createWorkAssignment(WorkRequirement workRequirement, Integer workAssignmentSequence, Party party, Long startTime, Long endTime, BasePK createdBy) {
        var workAssignment = WorkAssignmentFactory.getInstance().create();
        var workAssignmentDetail = WorkAssignmentDetailFactory.getInstance().create(workAssignment, workRequirement, workAssignmentSequence, party, startTime, endTime,
                session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        workAssignment = WorkAssignmentFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, workAssignment.getPrimaryKey());
        workAssignment.setActiveDetail(workAssignmentDetail);
        workAssignment.setLastDetail(workAssignmentDetail);
        workAssignment.store();

        sendEvent(workAssignment.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return workAssignment;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.WorkAssignment */
    public WorkAssignment getWorkAssignmentByEntityInstance(EntityInstance entityInstance) {
        var pk = new WorkAssignmentPK(entityInstance.getEntityUniqueId());
        var workAssignment = WorkAssignmentFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);

        return workAssignment;
    }

    private static final Map<EntityPermission, String> getWorkAssignmentQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM workassignments, workassignmentdetails " +
                "WHERE wasgn_activedetailid = wasgndt_workassignmentdetailid " +
                "AND wasgndt_wr_workrequirementid = ? AND wasgndt_workassignmentsequence = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM workassignments, workassignmentdetails " +
                "WHERE wasgn_activedetailid = wasgndt_workassignmentdetailid " +
                "AND wasgndt_wr_workrequirementid = ? AND wasgndt_workassignmentsequence = ? " +
                "FOR UPDATE");
        getWorkAssignmentQueries = Collections.unmodifiableMap(queryMap);
    }

    private WorkAssignment getWorkAssignment(WorkRequirement workRequirement, Integer workAssignmentSequence, EntityPermission entityPermission) {
        return WorkAssignmentFactory.getInstance().getEntityFromQuery(entityPermission, getWorkAssignmentQueries,
                workRequirement, workAssignmentSequence);
    }

    public WorkAssignment getWorkAssignment(WorkRequirement workRequirement, Integer workAssignmentSequence) {
        return getWorkAssignment(workRequirement, workAssignmentSequence, EntityPermission.READ_ONLY);
    }

    public WorkAssignment getWorkAssignmentForUpdate(WorkRequirement workRequirement, Integer workAssignmentSequence) {
        return getWorkAssignment(workRequirement, workAssignmentSequence, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getWorkAssignmentsByWorkRequirementQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM workassignments, workassignmentdetails " +
                "WHERE wasgn_activedetailid = wasgndt_workassignmentdetailid " +
                "AND wasgndt_wr_workrequirementid = ? " +
                "ORDER BY wasgndt_workassignmentsequence " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM workassignments, workassignmentdetails " +
                "WHERE wasgn_activedetailid = wasgndt_workassignmentdetailid " +
                "AND wasgndt_wr_workrequirementid = ? " +
                "FOR UPDATE");
        getWorkAssignmentsByWorkRequirementQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<WorkAssignment> getWorkAssignmentsByWorkRequirement(WorkRequirement workRequirement, EntityPermission entityPermission) {
        return WorkAssignmentFactory.getInstance().getEntitiesFromQuery(entityPermission, getWorkAssignmentsByWorkRequirementQueries,
                workRequirement);
    }

    public List<WorkAssignment> getWorkAssignmentsByWorkRequirement(WorkRequirement workRequirement) {
        return getWorkAssignmentsByWorkRequirement(workRequirement, EntityPermission.READ_ONLY);
    }

    public List<WorkAssignment> getWorkAssignmentsByWorkRequirementForUpdate(WorkRequirement workRequirement) {
        return getWorkAssignmentsByWorkRequirement(workRequirement, EntityPermission.READ_WRITE);
    }

    public long countWorkAssignmentsByParty(Party party) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM workassignments, workassignmentdetails " +
                "WHERE wasgn_activedetailid = wasgndt_workassignmentdetailid " +
                "AND wasgndt_par_partyid = ?",
                party, Session.MAX_TIME);
    }
    private static final Map<EntityPermission, String> getWorkAssignmentsByPartyQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM workassignments, workassignmentdetails, workrequirements, workrequirementdetails " +
                "WHERE wasgn_activedetailid = wasgndt_workassignmentdetailid " +
                "AND wasgndt_par_partyid = ? " +
                "AND wasgndt_wr_workrequirementid = wr_workrequirementid AND wr_lastdetailid = wrdt_workrequirementdetailid " +
                "ORDER BY wrdt_workrequirementname, wasgndt_workassignmentsequence " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM workassignments, workassignmentdetails " +
                "WHERE wasgn_activedetailid = wasgndt_workassignmentdetailid " +
                "AND wasgndt_wr_workrequirementid = ? " +
                "FOR UPDATE");
        getWorkAssignmentsByPartyQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<WorkAssignment> getWorkAssignmentsByParty(Party party, EntityPermission entityPermission) {
        return WorkAssignmentFactory.getInstance().getEntitiesFromQuery(entityPermission, getWorkAssignmentsByPartyQueries,
                party);
    }

    public List<WorkAssignment> getWorkAssignmentsByParty(Party party) {
        return getWorkAssignmentsByParty(party, EntityPermission.READ_ONLY);
    }

    public List<WorkAssignment> getWorkAssignmentsByPartyForUpdate(Party party) {
        return getWorkAssignmentsByParty(party, EntityPermission.READ_WRITE);
    }

    public WorkAssignmentStatusChoicesBean getWorkAssignmentStatusChoices(String defaultWorkAssignmentStatusChoice, Language language,
            boolean allowNullChoice, WorkAssignment workAssignment, PartyPK partyPK) {
        var workAssignmentStatusChoicesBean = new WorkAssignmentStatusChoicesBean();

        if(workAssignment == null) {
            workflowControl.getWorkflowEntranceChoices(workAssignmentStatusChoicesBean, defaultWorkAssignmentStatusChoice, language, allowNullChoice,
                    workflowControl.getWorkflowByName(WorkAssignmentStatusConstants.Workflow_WORK_ASSIGNMENT_STATUS), partyPK);
        } else {
            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(workAssignment.getPrimaryKey());
            var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(WorkAssignmentStatusConstants.Workflow_WORK_ASSIGNMENT_STATUS,
                    entityInstance);

            workflowControl.getWorkflowDestinationChoices(workAssignmentStatusChoicesBean, defaultWorkAssignmentStatusChoice, language, allowNullChoice,
                    workflowEntityStatus.getWorkflowStep(), partyPK);
        }

        return workAssignmentStatusChoicesBean;
    }

    public void setWorkAssignmentStatus(ExecutionErrorAccumulator eea, WorkAssignment workAssignment, String workAssignmentStatusChoice, PartyPK modifiedBy) {
        var entityInstance = getEntityInstanceByBaseEntity(workAssignment);
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(WorkAssignmentStatusConstants.Workflow_WORK_ASSIGNMENT_STATUS,
                entityInstance);
        var workflowDestination = workAssignmentStatusChoice == null ? null
                : workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), workAssignmentStatusChoice);

        if(workflowDestination != null || workAssignmentStatusChoice == null) {
            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, null, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownWorkAssignmentStatusChoice.name(), workAssignmentStatusChoice);
        }
    }

    public WorkAssignmentTransfer getWorkAssignmentTransfer(UserVisit userVisit, WorkAssignment workAssignment) {
        return workAssignmentTransferCache.getWorkAssignmentTransfer(userVisit, workAssignment);
    }

    public List<WorkAssignmentTransfer> getWorkAssignmentTransfers(UserVisit userVisit, Collection<WorkAssignment> workAssignments) {
        List<WorkAssignmentTransfer> workAssignmentTransfers = new ArrayList<>(workAssignments.size());

        workAssignments.forEach((workAssignment) ->
                workAssignmentTransfers.add(workAssignmentTransferCache.getWorkAssignmentTransfer(userVisit, workAssignment))
        );

        return workAssignmentTransfers;
    }

    public List<WorkAssignmentTransfer> getWorkAssignmentTransfersByWorkRequirement(UserVisit userVisit, WorkRequirement workRequirement) {
        return getWorkAssignmentTransfers(userVisit, getWorkAssignmentsByWorkRequirement(workRequirement));
    }

    public List<WorkAssignmentTransfer> getWorkAssignmentTransfersByParty(UserVisit userVisit, Party party) {
        return getWorkAssignmentTransfers(userVisit, getWorkAssignmentsByParty(party));
    }

    public void updateWorkAssignmentFromValue(WorkAssignmentDetailValue workAssignmentDetailValue, BasePK updatedBy) {
        if(workAssignmentDetailValue.hasBeenModified()) {
            var workAssignment = WorkAssignmentFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     workAssignmentDetailValue.getWorkAssignmentPK());
            var workAssignmentDetail = workAssignment.getActiveDetailForUpdate();

            workAssignmentDetail.setThruTime(session.getStartTime());
            workAssignmentDetail.store();

            var workAssignmentPK = workAssignmentDetailValue.getWorkAssignmentPK();
            var workRequirementPK = workAssignmentDetailValue.getWorkRequirementPK();
            var workAssignmentSequence = workAssignmentDetailValue.getWorkAssignmentSequence();
            var partyPK = workAssignmentDetailValue.getPartyPK();
            var startTime = workAssignmentDetailValue.getStartTime();
            var endTime = workAssignmentDetailValue.getEndTime();

            workAssignmentDetail = WorkAssignmentDetailFactory.getInstance().create(workAssignmentPK, workRequirementPK, workAssignmentSequence, partyPK, startTime, endTime,
                    session.getStartTime(), Session.MAX_TIME);

            workAssignment.setActiveDetail(workAssignmentDetail);
            workAssignment.setLastDetail(workAssignmentDetail);

            sendEvent(workAssignmentPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void deleteWorkAssignment(WorkAssignment workAssignment, BasePK deletedBy) {
        var workAssignmentDetail = workAssignment.getLastDetailForUpdate();
        workAssignmentDetail.setThruTime(session.getStartTime());
        workAssignment.setActiveDetail(null);
        workAssignment.store();

        sendEvent(workAssignment.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteWorkAssignments(List<WorkAssignment> workAssignments, BasePK deletedBy) {
        workAssignments.forEach((workAssignment) -> 
                deleteWorkAssignment(workAssignment, deletedBy)
        );
    }

    public void deleteWorkAssignmentsByWorkRequirement(WorkRequirement workRequirement, BasePK deletedBy) {
        deleteWorkAssignments(getWorkAssignmentsByWorkRequirementForUpdate(workRequirement), deletedBy);
    }

    public void deleteWorkAssignmentsByParty(Party party, BasePK deletedBy) {
        deleteWorkAssignments(getWorkAssignmentsByPartyForUpdate(party), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Work Times
    // --------------------------------------------------------------------------------
    
    public WorkTime createWorkTime(WorkRequirement workRequirement, Party party, Long startTime, Long endTime, BasePK createdBy) {
        var workRequirementStatus = getWorkRequirementStatusForUpdate(workRequirement);
        Integer workTimeSequence = workRequirementStatus.getWorkTimeSequence() + 1;

        workRequirementStatus.setWorkTimeSequence(workTimeSequence);

        return createWorkTime(workRequirement,  workTimeSequence, party, startTime, endTime, createdBy);
    }

    public WorkTime createWorkTime(WorkRequirement workRequirement, Integer workTimeSequence, Party party, Long startTime, Long endTime, BasePK createdBy) {
        var workTime = WorkTimeFactory.getInstance().create();
        var workTimeDetail = WorkTimeDetailFactory.getInstance().create(workTime, workRequirement, workTimeSequence, party, startTime, endTime,
                session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        workTime = WorkTimeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, workTime.getPrimaryKey());
        workTime.setActiveDetail(workTimeDetail);
        workTime.setLastDetail(workTimeDetail);
        workTime.store();

        sendEvent(workTime.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return workTime;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.WorkTime */
    public WorkTime getWorkTimeByEntityInstance(EntityInstance entityInstance) {
        var pk = new WorkTimePK(entityInstance.getEntityUniqueId());
        var workTime = WorkTimeFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);

        return workTime;
    }

    private static final Map<EntityPermission, String> getWorkTimeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM worktimes, worktimedetails " +
                "WHERE wtm_activedetailid = wtmdt_worktimedetailid " +
                "AND wtmdt_wr_workrequirementid = ? AND wtmdt_worktimesequence = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM worktimes, worktimedetails " +
                "WHERE wtm_activedetailid = wtmdt_worktimedetailid " +
                "AND wtmdt_wr_workrequirementid = ? AND wtmdt_worktimesequence = ? " +
                "FOR UPDATE");
        getWorkTimeQueries = Collections.unmodifiableMap(queryMap);
    }

    private WorkTime getWorkTime(WorkRequirement workRequirement, Integer workTimeSequence, EntityPermission entityPermission) {
        return WorkTimeFactory.getInstance().getEntityFromQuery(entityPermission, getWorkTimeQueries,
                workRequirement, workTimeSequence);
    }

    public WorkTime getWorkTime(WorkRequirement workRequirement, Integer workTimeSequence) {
        return getWorkTime(workRequirement, workTimeSequence, EntityPermission.READ_ONLY);
    }
    
    public WorkTime getWorkTimeForUpdate(WorkRequirement workRequirement, Integer workTimeSequence) {
        return getWorkTime(workRequirement, workTimeSequence, EntityPermission.READ_WRITE);
    }
    
    public WorkTimeDetailValue getWorkTimeDetailValueForUpdate(WorkTime workTime) {
        return workTime == null ? null : workTime.getLastDetailForUpdate().getWorkTimeDetailValue().clone();
    }

    public WorkTimeDetailValue getWorkTimeDetailValueForUpdate(WorkRequirement workRequirement, Integer workTimeSequence) {
        return getWorkTimeDetailValueForUpdate(getWorkTimeForUpdate(workRequirement, workTimeSequence));
    }
    
    private static final Map<EntityPermission, String> getWorkTimesByWorkRequirementQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM worktimes, worktimedetails " +
                "WHERE wtm_activedetailid = wtmdt_worktimedetailid " +
                "AND wtmdt_wr_workrequirementid = ? " +
                "ORDER BY wtmdt_worktimesequence " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM worktimes, worktimedetails " +
                "WHERE wtm_activedetailid = wtmdt_worktimedetailid " +
                "AND wtmdt_wr_workrequirementid = ? " +
                "FOR UPDATE");
        getWorkTimesByWorkRequirementQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<WorkTime> getWorkTimesByWorkRequirement(WorkRequirement workRequirement, EntityPermission entityPermission) {
        return WorkTimeFactory.getInstance().getEntitiesFromQuery(entityPermission, getWorkTimesByWorkRequirementQueries,
                workRequirement);
    }

    public List<WorkTime> getWorkTimesByWorkRequirement(WorkRequirement workRequirement) {
        return getWorkTimesByWorkRequirement(workRequirement, EntityPermission.READ_ONLY);
    }
    
    public List<WorkTime> getWorkTimesByWorkRequirementForUpdate(WorkRequirement workRequirement) {
        return getWorkTimesByWorkRequirement(workRequirement, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getWorkTimesByPartyQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM worktimes, worktimedetails " +
                "WHERE wtm_activedetailid = wtmdt_worktimedetailid " +
                "AND wtmdt_par_partyid = ? " +
                "_LIMIT_"); // TODO: ORDER BY
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM worktimes, worktimedetails " +
                "WHERE wtm_activedetailid = wtmdt_worktimedetailid " +
                "AND wtmdt_par_partyid = ? " +
                "FOR UPDATE");
        getWorkTimesByPartyQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<WorkTime> getWorkTimesByParty(Party party, EntityPermission entityPermission) {
        return WorkTimeFactory.getInstance().getEntitiesFromQuery(entityPermission, getWorkTimesByPartyQueries,
                party);
    }

    public List<WorkTime> getWorkTimesByParty(Party party) {
        return getWorkTimesByParty(party, EntityPermission.READ_ONLY);
    }
    
    public List<WorkTime> getWorkTimesByPartyForUpdate(Party party) {
        return getWorkTimesByParty(party, EntityPermission.READ_WRITE);
    }
    
    public WorkTimeStatusChoicesBean getWorkTimeStatusChoices(String defaultWorkTimeStatusChoice, Language language,
            boolean allowNullChoice, WorkTime workTime, PartyPK partyPK) {
        var workTimeStatusChoicesBean = new WorkTimeStatusChoicesBean();

        if(workTime == null) {
            workflowControl.getWorkflowEntranceChoices(workTimeStatusChoicesBean, defaultWorkTimeStatusChoice, language, allowNullChoice,
                    workflowControl.getWorkflowByName(WorkTimeStatusConstants.Workflow_WORK_TIME_STATUS), partyPK);
        } else {
            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(workTime.getPrimaryKey());
            var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(WorkTimeStatusConstants.Workflow_WORK_TIME_STATUS,
                    entityInstance);

            workflowControl.getWorkflowDestinationChoices(workTimeStatusChoicesBean, defaultWorkTimeStatusChoice, language, allowNullChoice,
                    workflowEntityStatus.getWorkflowStep(), partyPK);
        }

        return workTimeStatusChoicesBean;
    }

    public void setWorkTimeStatus(ExecutionErrorAccumulator eea, WorkTime workTime, String workTimeStatusChoice, PartyPK modifiedBy) {
        var entityInstance = getEntityInstanceByBaseEntity(workTime);
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(WorkTimeStatusConstants.Workflow_WORK_TIME_STATUS,
                entityInstance);
        var workflowDestination = workTimeStatusChoice == null ? null
                : workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), workTimeStatusChoice);

        if(workflowDestination != null || workTimeStatusChoice == null) {
            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, null, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownWorkTimeStatusChoice.name(), workTimeStatusChoice);
        }
    }

    public WorkTimeTransfer getWorkTimeTransfer(UserVisit userVisit, WorkTime workTime) {
        return workTimeTransferCache.getWorkTimeTransfer(userVisit, workTime);
    }
    
    public List<WorkTimeTransfer> getWorkTimeTransfers(UserVisit userVisit, Collection<WorkTime> workTimes) {
        List<WorkTimeTransfer> workTimeTransfers = new ArrayList<>(workTimes.size());

        workTimes.forEach((workTime) ->
                workTimeTransfers.add(workTimeTransferCache.getWorkTimeTransfer(userVisit, workTime))
        );

        return workTimeTransfers;
    }

    public List<WorkTimeTransfer> getWorkTimeTransfersByWorkRequirement(UserVisit userVisit, WorkRequirement workRequirement) {
        return getWorkTimeTransfers(userVisit, getWorkTimesByWorkRequirement(workRequirement));
    }

    public void updateWorkTimeFromValue(WorkTimeDetailValue workTimeDetailValue, BasePK updatedBy) {
        if(workTimeDetailValue.hasBeenModified()) {
            var workTime = WorkTimeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     workTimeDetailValue.getWorkTimePK());
            var workTimeDetail = workTime.getActiveDetailForUpdate();

            workTimeDetail.setThruTime(session.getStartTime());
            workTimeDetail.store();

            var workTimePK = workTimeDetailValue.getWorkTimePK();
            var workRequirementPK = workTimeDetailValue.getWorkRequirementPK();
            var workTimeSequence = workTimeDetailValue.getWorkTimeSequence();
            var partyPK = workTimeDetailValue.getPartyPK();
            var startTime = workTimeDetailValue.getStartTime();
            var endTime = workTimeDetailValue.getEndTime();

            workTimeDetail = WorkTimeDetailFactory.getInstance().create(workTimePK, workRequirementPK, workTimeSequence, partyPK, startTime, endTime,
                    session.getStartTime(), Session.MAX_TIME);

            workTime.setActiveDetail(workTimeDetail);
            workTime.setLastDetail(workTimeDetail);

            sendEvent(workTimePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void deleteWorkTime(WorkTime workTime, BasePK deletedBy) {
        removeWorkTimeUserVisitsByWorkTime(workTime);

        var workTimeDetail = workTime.getLastDetailForUpdate();
        workTimeDetail.setThruTime(session.getStartTime());
        workTime.setActiveDetail(null);
        workTime.store();

        sendEvent(workTime.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteWorkTimes(List<WorkTime> workTimes, BasePK deletedBy) {
        workTimes.forEach((workTime) -> 
                deleteWorkTime(workTime, deletedBy)
        );
    }
    
    public void deleteWorkTimesByWorkRequirement(WorkRequirement workRequirement, BasePK deletedBy) {
        deleteWorkTimes(getWorkTimesByWorkRequirementForUpdate(workRequirement), deletedBy);
    }
    
    public void deleteWorkTimesByParty(Party party, BasePK deletedBy) {
        deleteWorkTimes(getWorkTimesByPartyForUpdate(party), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Work Time User Visits
    // --------------------------------------------------------------------------------
    
    public WorkTimeUserVisit createWorkTimeUserVisit(WorkTime workTime, UserVisit userVisit) {
        return WorkTimeUserVisitFactory.getInstance().create(workTime, userVisit, session.getStartTime(), Session.MAX_TIME);
    }
    
    private static final Map<EntityPermission, String> getWorkTimeUserVisitQueries;
    
    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM worktimeuservisits "
                + "WHERE wtmuvis_wtm_worktimeid = ? AND wtmuvis_uvis_uservisitid = ? AND wtmuvis_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM worktimeuservisits "
                + "WHERE wtmuvis_wtm_worktimeid = ? AND wtmuvis_uvis_uservisitid = ? AND wtmuvis_thrutime = ? "
                + "FOR UPDATE");
        getWorkTimeUserVisitQueries = Collections.unmodifiableMap(queryMap);
    }

    private WorkTimeUserVisit getWorkTimeUserVisit(WorkTime workTime, UserVisit userVisit, EntityPermission entityPermission) {
        return WorkTimeUserVisitFactory.getInstance().getEntityFromQuery(entityPermission, getWorkTimeUserVisitQueries,
                workTime, userVisit, Session.MAX_TIME);
    }
    
    public WorkTimeUserVisit getWorkTimeUserVisit(WorkTime workTime, UserVisit userVisit) {
        return getWorkTimeUserVisit(workTime, userVisit, EntityPermission.READ_ONLY);
    }
    
    public WorkTimeUserVisit getWorkTimeUserVisitForUpdate(WorkTime workTime, UserVisit userVisit) {
        return getWorkTimeUserVisit(workTime, userVisit, EntityPermission.READ_WRITE);
    }
    
    public WorkTimeUserVisitValue getWorkTimeUserVisitValue(WorkTimeUserVisit workTimeUserVisit) {
        return workTimeUserVisit == null? null: workTimeUserVisit.getWorkTimeUserVisitValue().clone();
    }
    
    public WorkTimeUserVisitValue getWorkTimeUserVisitValueForUpdate(WorkTime workTime, UserVisit userVisit) {
        return getWorkTimeUserVisitValue(getWorkTimeUserVisitForUpdate(workTime, userVisit));
    }
    
    private static final Map<EntityPermission, String> getWorkTimeUserVisitsByWorkTimeQueries;
    
    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM worktimeuservisits, uservisits "
                + "WHERE wtmuvis_wtm_worktimeid = ? AND wtmuvis_thrutime = ? "
                + "AND wtmuvis_uvis_uservisitid = uvis_uservisitid "
                + "ORDER BY uvis_fromtime");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM worktimeuservisits "
                + "WHERE wtmuvis_wtm_worktimeid = ? AND wtmuvis_thrutime = ? "
                + "FOR UPDATE");
        getWorkTimeUserVisitsByWorkTimeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<WorkTimeUserVisit> getWorkTimeUserVisitsByWorkTime(WorkTime workTime, EntityPermission entityPermission) {
        return WorkTimeUserVisitFactory.getInstance().getEntitiesFromQuery(entityPermission, getWorkTimeUserVisitsByWorkTimeQueries,
                workTime, Session.MAX_TIME);
    }
    
    public List<WorkTimeUserVisit> getWorkTimeUserVisitsByWorkTime(WorkTime workTime) {
        return getWorkTimeUserVisitsByWorkTime(workTime, EntityPermission.READ_ONLY);
    }
    
    public List<WorkTimeUserVisit> getWorkTimeUserVisitsByWorkTimeForUpdate(WorkTime workTime) {
        return getWorkTimeUserVisitsByWorkTime(workTime, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getWorkTimeUserVisitsByUserVisitQueries;
    
    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM worktimeuservisits, worktimes, worktimedetails "
                + "WHERE wtmuvis_wtm_worktimeid = ? AND wtmuvis_thrutime = ? "
                + "AND wtmuvis_wtm_worktimeid = wtm_worktimeid AND wtm_lastdetailid = wtmdt_worktimedetailid "
                + "ORDER BY wtmdt_workTimename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM worktimeuservisits "
                + "WHERE wtmuvis_wtm_worktimeid = ? AND wtmuvis_thrutime = ? "
                + "FOR UPDATE");
        getWorkTimeUserVisitsByUserVisitQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<WorkTimeUserVisit> getWorkTimeUserVisitsByUserVisit(UserVisit userVisit, EntityPermission entityPermission) {
        return WorkTimeUserVisitFactory.getInstance().getEntitiesFromQuery(entityPermission, getWorkTimeUserVisitsByUserVisitQueries,
                userVisit, Session.MAX_TIME);
    }
    
    public List<WorkTimeUserVisit> getWorkTimeUserVisitsByUserVisit(UserVisit userVisit) {
        return getWorkTimeUserVisitsByUserVisit(userVisit, EntityPermission.READ_ONLY);
    }
    
    public List<WorkTimeUserVisit> getWorkTimeUserVisitsByUserVisitForUpdate(UserVisit userVisit) {
        return getWorkTimeUserVisitsByUserVisit(userVisit, EntityPermission.READ_WRITE);
    }
    
    public void deleteWorkTimeUserVisit(WorkTimeUserVisit workTimeUserVisit) {
        workTimeUserVisit.setThruTime(session.getStartTime());
        workTimeUserVisit.store();
    }
    
    public void deleteWorkTimeUserVisits(List<WorkTimeUserVisit> workTimeUserVisits) {
        workTimeUserVisits.forEach((workTimeUserVisit) -> {
            deleteWorkTimeUserVisit(workTimeUserVisit);
        });
    }
    
    public void deleteWorkTimeUserVisitsByWorkTime(WorkTime workTime) {
        deleteWorkTimeUserVisits(getWorkTimeUserVisitsByWorkTimeForUpdate(workTime));
    }
    
    public void deleteWorkTimeUserVisitsByUserVisit(UserVisit userVisit) {
        deleteWorkTimeUserVisits(getWorkTimeUserVisitsByUserVisitForUpdate(userVisit));
    }
    
    public void removeWorkTimeUserVisit(WorkTimeUserVisit workTimeUserVisit) {
        workTimeUserVisit.remove();
    }
    
    public void removeWorkTimeUserVisits(List<WorkTimeUserVisit> workTimeUserVisits) {
        workTimeUserVisits.forEach((workTimeUserVisit) -> {
            removeWorkTimeUserVisit(workTimeUserVisit);
        });
    }
    
    public void removeWorkTimeUserVisitsByWorkTime(WorkTime workTime) {
        removeWorkTimeUserVisits(getWorkTimeUserVisitsByWorkTimeForUpdate(workTime));
    }
    
    public void removeWorkTimeUserVisitsByUserVisit(UserVisit userVisit) {
        removeWorkTimeUserVisits(getWorkTimeUserVisitsByUserVisitForUpdate(userVisit));
    }
    
}
