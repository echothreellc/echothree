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

package com.echothree.model.control.workrequirement.server.control;


import com.echothree.model.control.core.common.EventTypes;
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
import com.echothree.model.data.workrequirement.common.pk.WorkRequirementPK;
import com.echothree.model.data.workrequirement.common.pk.WorkRequirementScopePK;
import com.echothree.model.data.workrequirement.common.pk.WorkRequirementTypePK;
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
import static com.echothree.model.jooq.server.keys.workeffort.WorkEffortForeignKeys.WORK_EFFORT_SCOPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.workeffort.WorkEffortForeignKeys.WORK_EFFORT_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.workrequirement.WorkRequirementForeignKeys.WORK_ASSIGNMENTS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.workrequirement.WorkRequirementForeignKeys.WORK_ASSIGNMENT_DETAILS_WORK_REQUIREMENT_FK;
import static com.echothree.model.jooq.server.keys.workrequirement.WorkRequirementForeignKeys.WORK_REQUIREMENTS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.workrequirement.WorkRequirementForeignKeys.WORK_REQUIREMENTS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.workrequirement.WorkRequirementForeignKeys.WORK_REQUIREMENT_SCOPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.workrequirement.WorkRequirementForeignKeys.WORK_REQUIREMENT_SCOPE_DETAILS_WORK_EFFORT_SCOPE_FK;
import static com.echothree.model.jooq.server.keys.workrequirement.WorkRequirementForeignKeys.WORK_REQUIREMENT_SCOPE_DETAILS_WORK_REQUIREMENT_TYPE_FK;
import static com.echothree.model.jooq.server.keys.workrequirement.WorkRequirementForeignKeys.WORK_REQUIREMENT_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.workrequirement.WorkRequirementForeignKeys.WORK_REQUIREMENT_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.workrequirement.WorkRequirementForeignKeys.WORK_REQUIREMENT_TYPE_DETAILS_WORK_EFFORT_TYPE_FK;
import static com.echothree.model.jooq.server.keys.workrequirement.WorkRequirementForeignKeys.WORK_TIMES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.workrequirement.WorkRequirementForeignKeys.WORK_TIMES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.workrequirement.WorkRequirementForeignKeys.WORK_TIME_DETAILS_WORK_REQUIREMENT_FK;
import static com.echothree.model.jooq.server.keys.workrequirement.WorkRequirementForeignKeys.WORK_TIME_USER_VISITS_USER_VISIT_FK;
import static com.echothree.model.jooq.server.keys.workrequirement.WorkRequirementForeignKeys.WORK_TIME_USER_VISITS_WORK_TIME_FK;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import static com.echothree.model.jooq.server.tables.user.UserVisits.UserVisits;
import static com.echothree.model.jooq.server.tables.workeffort.WorkEffortScopeDetails.WorkEffortScopeDetails;
import static com.echothree.model.jooq.server.tables.workeffort.WorkEffortScopes.WorkEffortScopes;
import static com.echothree.model.jooq.server.tables.workeffort.WorkEffortTypeDetails.WorkEffortTypeDetails;
import static com.echothree.model.jooq.server.tables.workeffort.WorkEffortTypes.WorkEffortTypes;
import static com.echothree.model.jooq.server.tables.workrequirement.WorkAssignmentDetails.WorkAssignmentDetails;
import static com.echothree.model.jooq.server.tables.workrequirement.WorkAssignments.WorkAssignments;
import static com.echothree.model.jooq.server.tables.workrequirement.WorkRequirementDetails.WorkRequirementDetails;
import static com.echothree.model.jooq.server.tables.workrequirement.WorkRequirementScopeDetails.WorkRequirementScopeDetails;
import static com.echothree.model.jooq.server.tables.workrequirement.WorkRequirementScopes.WorkRequirementScopes;
import static com.echothree.model.jooq.server.tables.workrequirement.WorkRequirementStatuses.WorkRequirementStatuses;
import static com.echothree.model.jooq.server.tables.workrequirement.WorkRequirementTypeDescriptions.WorkRequirementTypeDescriptions;
import static com.echothree.model.jooq.server.tables.workrequirement.WorkRequirementTypeDetails.WorkRequirementTypeDetails;
import static com.echothree.model.jooq.server.tables.workrequirement.WorkRequirementTypes.WorkRequirementTypes;
import static com.echothree.model.jooq.server.tables.workrequirement.WorkRequirements.WorkRequirements;
import static com.echothree.model.jooq.server.tables.workrequirement.WorkTimeDetails.WorkTimeDetails;
import static com.echothree.model.jooq.server.tables.workrequirement.WorkTimeUserVisits.WorkTimeUserVisits;
import static com.echothree.model.jooq.server.tables.workrequirement.WorkTimes.WorkTimes;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;

@CommandScope
public class WorkRequirementControl
        extends BaseModelControl {
    
    /** Creates a new instance of WorkRequirementControl */

    protected WorkRequirementControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Work Requirement Types
    // --------------------------------------------------------------------------------

    @Inject
    protected WorkRequirementTypeDetailFactory workRequirementTypeDetailFactory;

    @Inject
    protected WorkRequirementTypeFactory workRequirementTypeFactory;

    @Inject
    WorkRequirementTypeTransferCache workRequirementTypeTransferCache;

    public WorkRequirementType createWorkRequirementType(WorkEffortType workEffortType, String workRequirementTypeName, Sequence workRequirementSequence,
            WorkflowStep workflowStep, Long estimatedTimeAllowed, Long maximumTimeAllowed, Boolean allowReassignment, Integer sortOrder, BasePK createdBy) {
        var workRequirementType = workRequirementTypeFactory.create();
        var workRequirementTypeDetail = workRequirementTypeDetailFactory.create( workRequirementType,
                workEffortType, workRequirementTypeName, workRequirementSequence, workflowStep, estimatedTimeAllowed, maximumTimeAllowed, allowReassignment,
                sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        workRequirementType = workRequirementTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE, workRequirementType.getPrimaryKey());
        workRequirementType.setActiveDetail(workRequirementTypeDetail);
        workRequirementType.setLastDetail(workRequirementTypeDetail);
        workRequirementType.store();
        
        sendEvent(workRequirementType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return workRequirementType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.WorkRequirementType */
    public WorkRequirementType getWorkRequirementTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new WorkRequirementTypePK(entityInstance.getEntityUniqueId());

        return workRequirementTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public WorkRequirementType getWorkRequirementTypeByEntityInstance(EntityInstance entityInstance) {
        return getWorkRequirementTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public WorkRequirementType getWorkRequirementTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getWorkRequirementTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countWorkRequirementTypesByWorkEffortType(final WorkEffortType workEffortType) {
        return session.getDslContext()
                .selectCount()
                .from(WorkRequirementTypes)
                .join(WorkRequirementTypeDetails).onKey(WORK_REQUIREMENT_TYPES_ACTIVE_DETAIL_FK)
                .where(WorkRequirementTypeDetails.WORK_EFFORT_TYPE.eq(workEffortType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countWorkRequirementTypesByWorkRequirementSequence(final Sequence workRequirementSequence) {
        return session.getDslContext()
                .selectCount()
                .from(WorkRequirementTypes)
                .join(WorkRequirementTypeDetails).onKey(WORK_REQUIREMENT_TYPES_ACTIVE_DETAIL_FK)
                .where(WorkRequirementTypeDetails.WORK_REQUIREMENT_SEQUENCE.eq(workRequirementSequence.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countWorkRequirementTypesByWorkflowStep(final WorkflowStep workflowStep) {
        return session.getDslContext()
                .selectCount()
                .from(WorkRequirementTypes)
                .join(WorkRequirementTypeDetails).onKey(WORK_REQUIREMENT_TYPES_ACTIVE_DETAIL_FK)
                .where(WorkRequirementTypeDetails.WORKFLOW_STEP.eq(workflowStep.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<WorkRequirementType> getWorkRequirementTypes(WorkEffortType workEffortType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(WorkRequirementTypes.fields())
                .from(WorkRequirementTypes)
                .join(WorkRequirementTypeDetails).onKey(WORK_REQUIREMENT_TYPES_ACTIVE_DETAIL_FK)
                .where(WorkRequirementTypeDetails.WORK_EFFORT_TYPE.eq(workEffortType.getPrimaryKey()));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(WorkRequirementTypeDetails.SORT_ORDER, WorkRequirementTypeDetails.WORK_REQUIREMENT_TYPE_NAME),
                    WorkRequirementTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };
            
        return workRequirementTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<WorkRequirementType> getWorkRequirementTypes(WorkEffortType workEffortType) {
        return getWorkRequirementTypes(workEffortType, EntityPermission.READ_ONLY);
    }
    
    public List<WorkRequirementType> getWorkRequirementTypesForUpdate(WorkEffortType workEffortType) {
        return getWorkRequirementTypes(workEffortType, EntityPermission.READ_WRITE);
    }
    
    private List<WorkRequirementType> getWorkRequirementTypesByWorkflowStep(WorkflowStep workflowStep, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(WorkRequirementTypes.fields())
                    .from(WorkRequirementTypes)
                    .join(WorkRequirementTypeDetails).onKey(WORK_REQUIREMENT_TYPES_ACTIVE_DETAIL_FK)
                    .join(WorkEffortTypes).onKey(WORK_REQUIREMENT_TYPE_DETAILS_WORK_EFFORT_TYPE_FK)
                    .join(WorkEffortTypeDetails).onKey(WORK_EFFORT_TYPES_ACTIVE_DETAIL_FK)
                    .where(WorkRequirementTypeDetails.WORKFLOW_STEP.eq(workflowStep.getPrimaryKey()))
                    .orderBy(WorkRequirementTypeDetails.SORT_ORDER, WorkRequirementTypeDetails.WORK_REQUIREMENT_TYPE_NAME, WorkEffortTypeDetails.SORT_ORDER, WorkEffortTypeDetails.WORK_EFFORT_TYPE_NAME),
                    WorkRequirementTypeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(WorkRequirementTypes.fields())
                    .from(WorkRequirementTypes)
                    .join(WorkRequirementTypeDetails).onKey(WORK_REQUIREMENT_TYPES_ACTIVE_DETAIL_FK)
                    .where(WorkRequirementTypeDetails.WORKFLOW_STEP.eq(workflowStep.getPrimaryKey()))
                    .forUpdate();
        };
        
        return workRequirementTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<WorkRequirementType> getWorkRequirementTypesByWorkflowStep(WorkflowStep workflowStep) {
        return getWorkRequirementTypesByWorkflowStep(workflowStep, EntityPermission.READ_ONLY);
    }
    
    public List<WorkRequirementType> getWorkRequirementTypesByWorkflowStepForUpdate(WorkflowStep workflowStep) {
        return getWorkRequirementTypesByWorkflowStep(workflowStep, EntityPermission.READ_WRITE);
    }
    
    private WorkRequirementType getWorkRequirementTypeByName(WorkEffortType workEffortType, String workRequirementTypeName,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(WorkRequirementTypes.fields())
                .from(WorkRequirementTypes)
                .join(WorkRequirementTypeDetails).onKey(WORK_REQUIREMENT_TYPES_ACTIVE_DETAIL_FK)
                .where(WorkRequirementTypeDetails.WORK_EFFORT_TYPE.eq(workEffortType.getPrimaryKey()),
                        WorkRequirementTypeDetails.WORK_REQUIREMENT_TYPE_NAME.eq(workRequirementTypeName));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };
            
        return workRequirementTypeFactory.getEntityFromQuery(entityPermission, query);
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
    
    public List<WorkRequirementTypeTransfer> getWorkRequirementTypeTransfers(UserVisit userVisit, Collection<WorkRequirementType> workRequirementTypes) {
        List<WorkRequirementTypeTransfer> workRequirementTypeTransfers = new ArrayList<>(workRequirementTypes.size());

        workRequirementTypes.forEach((workRequirementType) ->
                workRequirementTypeTransfers.add(workRequirementTypeTransferCache.getWorkRequirementTypeTransfer(userVisit, workRequirementType))
        );

        return workRequirementTypeTransfers;
    }

    public List<WorkRequirementTypeTransfer> getWorkRequirementTypeTransfers(UserVisit userVisit, WorkEffortType workEffortType) {
        return getWorkRequirementTypeTransfers(userVisit, getWorkRequirementTypes(workEffortType));
    }
    
    public void updateWorkRequirementTypeFromValue(WorkRequirementTypeDetailValue workRequirementTypeDetailValue, BasePK updatedBy) {
        if(workRequirementTypeDetailValue.hasBeenModified()) {
            var workRequirementType = workRequirementTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
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
            
            workRequirementTypeDetail = workRequirementTypeDetailFactory.create(workRequirementTypePK, workEffortTypePK, workRequirementTypeName,
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

    @Inject
    protected WorkRequirementTypeDescriptionFactory workRequirementTypeDescriptionFactory;

    @Inject
    WorkRequirementTypeDescriptionTransferCache workRequirementTypeDescriptionTransferCache;

    public WorkRequirementTypeDescription createWorkRequirementTypeDescription(WorkRequirementType workRequirementType, Language language,
            String description, BasePK createdBy) {
        var workRequirementTypeDescription = workRequirementTypeDescriptionFactory.create(workRequirementType,
                language, description, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(workRequirementType.getPrimaryKey(), EventTypes.MODIFY, workRequirementTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return workRequirementTypeDescription;
    }
    
    public WorkRequirementTypeDescription getWorkRequirementTypeDescription(WorkRequirementType workRequirementType, Language language,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(WorkRequirementTypeDescriptions.fields())
                .from(WorkRequirementTypeDescriptions)
                .where(WorkRequirementTypeDescriptions.WORK_REQUIREMENT_TYPE.eq(workRequirementType.getPrimaryKey()),
                        WorkRequirementTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        WorkRequirementTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };
            
        return workRequirementTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(WorkRequirementTypeDescriptions.fields())
                    .from(WorkRequirementTypeDescriptions)
                    .join(Languages).onKey(WORK_REQUIREMENT_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(WorkRequirementTypeDescriptions.WORK_REQUIREMENT_TYPE.eq(workRequirementType.getPrimaryKey()),
                            WorkRequirementTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    WorkRequirementTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(WorkRequirementTypeDescriptions.fields())
                    .from(WorkRequirementTypeDescriptions)
                    .where(WorkRequirementTypeDescriptions.WORK_REQUIREMENT_TYPE.eq(workRequirementType.getPrimaryKey()),
                            WorkRequirementTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };
        
        return workRequirementTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
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
            var workRequirementTypeDescription = workRequirementTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     workRequirementTypeDescriptionValue.getPrimaryKey());
            
            workRequirementTypeDescription.setThruTime(session.getStartTime());
            workRequirementTypeDescription.store();

            var workRequirementType = workRequirementTypeDescription.getWorkRequirementType();
            var language = workRequirementTypeDescription.getLanguage();
            var description = workRequirementTypeDescriptionValue.getDescription();
            
            workRequirementTypeDescription = workRequirementTypeDescriptionFactory.create(workRequirementType, language,
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

    @Inject
    protected WorkRequirementScopeDetailFactory workRequirementScopeDetailFactory;

    @Inject
    protected WorkRequirementScopeFactory workRequirementScopeFactory;

    @Inject
    WorkRequirementScopeTransferCache workRequirementScopeTransferCache;

    public WorkRequirementScope createWorkRequirementScope(WorkEffortScope workEffortScope, WorkRequirementType workRequirementType,
            Sequence workRequirementSequence, Sequence workTimeSequence, Selector workAssignmentSelector, Long estimatedTimeAllowed,
            Long maximumTimeAllowed, BasePK createdBy) {
        var workRequirementScope = workRequirementScopeFactory.create();
        var workRequirementScopeDetail = workRequirementScopeDetailFactory.create(
                workRequirementScope, workEffortScope, workRequirementType, workRequirementSequence, workTimeSequence,
                workAssignmentSelector, estimatedTimeAllowed, maximumTimeAllowed, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        workRequirementScope = workRequirementScopeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                workRequirementScope.getPrimaryKey());
        workRequirementScope.setActiveDetail(workRequirementScopeDetail);
        workRequirementScope.setLastDetail(workRequirementScopeDetail);
        workRequirementScope.store();
        
        sendEvent(workRequirementScope.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return workRequirementScope;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.WorkRequirementScope */
    public WorkRequirementScope getWorkRequirementScopeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new WorkRequirementScopePK(entityInstance.getEntityUniqueId());

        return workRequirementScopeFactory.getEntityFromPK(entityPermission, pk);
    }

    public WorkRequirementScope getWorkRequirementScopeByEntityInstance(EntityInstance entityInstance) {
        return getWorkRequirementScopeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public WorkRequirementScope getWorkRequirementScopeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getWorkRequirementScopeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countWorkRequirementScopesByWorkEffortScope(final WorkEffortScope workEffortScope) {
        return session.getDslContext()
                .selectCount()
                .from(WorkRequirementScopes)
                .join(WorkRequirementScopeDetails).onKey(WORK_REQUIREMENT_SCOPES_ACTIVE_DETAIL_FK)
                .where(WorkRequirementScopeDetails.WORK_EFFORT_SCOPE.eq(workEffortScope.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countWorkRequirementScopesByWorkRequirementType(final WorkRequirementType workRequirementType) {
        return session.getDslContext()
                .selectCount()
                .from(WorkRequirementScopes)
                .join(WorkRequirementScopeDetails).onKey(WORK_REQUIREMENT_SCOPES_ACTIVE_DETAIL_FK)
                .where(WorkRequirementScopeDetails.WORK_REQUIREMENT_TYPE.eq(workRequirementType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countWorkRequirementScopesByWorkRequirementSequence(final Sequence workRequirementSequence) {
        return session.getDslContext()
                .selectCount()
                .from(WorkRequirementScopes)
                .join(WorkRequirementScopeDetails).onKey(WORK_REQUIREMENT_SCOPES_ACTIVE_DETAIL_FK)
                .where(WorkRequirementScopeDetails.WORK_REQUIREMENT_SEQUENCE.eq(workRequirementSequence.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countWorkRequirementScopesByWorkTimeSequence(final Sequence workTimeSequence) {
        return session.getDslContext()
                .selectCount()
                .from(WorkRequirementScopes)
                .join(WorkRequirementScopeDetails).onKey(WORK_REQUIREMENT_SCOPES_ACTIVE_DETAIL_FK)
                .where(WorkRequirementScopeDetails.WORK_TIME_SEQUENCE.eq(workTimeSequence.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countWorkRequirementScopesByWorkAssignmentSelector(final Selector workAssignmentSelector) {
        return session.getDslContext()
                .selectCount()
                .from(WorkRequirementScopes)
                .join(WorkRequirementScopeDetails).onKey(WORK_REQUIREMENT_SCOPES_ACTIVE_DETAIL_FK)
                .where(WorkRequirementScopeDetails.WORK_ASSIGNMENT_SELECTOR.eq(workAssignmentSelector.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private WorkRequirementScope getWorkRequirementScope(WorkEffortScope workEffortScope, WorkRequirementType workRequirementType,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(WorkRequirementScopes.fields())
                .from(WorkRequirementScopes)
                .join(WorkRequirementScopeDetails).onKey(WORK_REQUIREMENT_SCOPES_ACTIVE_DETAIL_FK)
                .where(WorkRequirementScopeDetails.WORK_EFFORT_SCOPE.eq(workEffortScope.getPrimaryKey()),
                        WorkRequirementScopeDetails.WORK_REQUIREMENT_TYPE.eq(workRequirementType.getPrimaryKey()));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };
            
        return workRequirementScopeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public WorkRequirementScope getWorkRequirementScope(WorkEffortScope workEffortScope, WorkRequirementType workRequirementType) {
        return getWorkRequirementScope(workEffortScope, workRequirementType, EntityPermission.READ_ONLY);
    }
    
    public WorkRequirementScope getWorkRequirementScopeForUpdate(WorkEffortScope workEffortScope, WorkRequirementType workRequirementType) {
        return getWorkRequirementScope(workEffortScope, workRequirementType, EntityPermission.READ_WRITE);
    }
    
    private List<WorkRequirementScope> getWorkRequirementScopesByWorkEffortScopeAndWorkflowStep(WorkEffortScope workEffortScope,
            WorkflowStep workflowStep, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(WorkRequirementScopes.fields())
                .from(WorkRequirementScopes)
                .join(WorkRequirementScopeDetails).onKey(WORK_REQUIREMENT_SCOPES_ACTIVE_DETAIL_FK)
                .join(WorkEffortScopes).onKey(WORK_REQUIREMENT_SCOPE_DETAILS_WORK_EFFORT_SCOPE_FK)
                .join(WorkEffortScopeDetails).onKey(WORK_EFFORT_SCOPES_ACTIVE_DETAIL_FK)
                .join(WorkRequirementTypes).onKey(WORK_REQUIREMENT_SCOPE_DETAILS_WORK_REQUIREMENT_TYPE_FK)
                .join(WorkRequirementTypeDetails).onKey(WORK_REQUIREMENT_TYPES_ACTIVE_DETAIL_FK)
                .where(WorkEffortScopes.WORK_EFFORT_SCOPE.eq(workEffortScope.getPrimaryKey()),
                        WorkRequirementTypeDetails.WORKFLOW_STEP.eq(workflowStep.getPrimaryKey()));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(WorkRequirementTypeDetails.SORT_ORDER, WorkRequirementTypeDetails.WORK_REQUIREMENT_TYPE_NAME),
                    WorkRequirementScopeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };
            
        return workRequirementScopeFactory.getEntitiesFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(WorkRequirementScopes.fields())
                    .from(WorkRequirementScopes)
                    .join(WorkRequirementScopeDetails).onKey(WORK_REQUIREMENT_SCOPES_ACTIVE_DETAIL_FK)
                    .join(WorkEffortScopes).onKey(WORK_REQUIREMENT_SCOPE_DETAILS_WORK_EFFORT_SCOPE_FK)
                    .join(WorkEffortScopeDetails).onKey(WORK_EFFORT_SCOPES_ACTIVE_DETAIL_FK)
                    .where(WorkRequirementScopeDetails.WORK_REQUIREMENT_TYPE.eq(workRequirementType.getPrimaryKey()))
                    .orderBy(WorkEffortScopeDetails.SORT_ORDER, WorkEffortScopeDetails.WORK_EFFORT_SCOPE_NAME),
                    WorkRequirementScopeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(WorkRequirementScopes.fields())
                    .from(WorkRequirementScopes)
                    .join(WorkRequirementScopeDetails).onKey(WORK_REQUIREMENT_SCOPES_ACTIVE_DETAIL_FK)
                    .where(WorkRequirementScopeDetails.WORK_REQUIREMENT_TYPE.eq(workRequirementType.getPrimaryKey()))
                    .forUpdate();
        };
        
        return workRequirementScopeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<WorkRequirementScope> getWorkRequirementScopesByWorkRequirementType(WorkRequirementType workRequirementType) {
        return getWorkRequirementScopesByWorkRequirementType(workRequirementType, EntityPermission.READ_ONLY);
    }
    
    public List<WorkRequirementScope> getWorkRequirementScopesByWorkRequirementTypeForUpdate(WorkRequirementType workRequirementType) {
        return getWorkRequirementScopesByWorkRequirementType(workRequirementType, EntityPermission.READ_WRITE);
    }
    
    private List<WorkRequirementScope> getWorkRequirementScopesByWorkEffortScope(WorkEffortScope workEffortScope, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(WorkRequirementScopes.fields())
                    .from(WorkRequirementScopes)
                    .join(WorkRequirementScopeDetails).onKey(WORK_REQUIREMENT_SCOPES_ACTIVE_DETAIL_FK)
                    .join(WorkRequirementTypes).onKey(WORK_REQUIREMENT_SCOPE_DETAILS_WORK_REQUIREMENT_TYPE_FK)
                    .join(WorkRequirementTypeDetails).onKey(WORK_REQUIREMENT_TYPES_ACTIVE_DETAIL_FK)
                    .where(WorkRequirementScopeDetails.WORK_EFFORT_SCOPE.eq(workEffortScope.getPrimaryKey()))
                    .orderBy(WorkRequirementTypeDetails.SORT_ORDER, WorkRequirementTypeDetails.WORK_REQUIREMENT_TYPE_NAME),
                    WorkRequirementScopeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(WorkRequirementScopes.fields())
                    .from(WorkRequirementScopes)
                    .join(WorkRequirementScopeDetails).onKey(WORK_REQUIREMENT_SCOPES_ACTIVE_DETAIL_FK)
                    .where(WorkRequirementScopeDetails.WORK_EFFORT_SCOPE.eq(workEffortScope.getPrimaryKey()))
                    .forUpdate();
        };
        
        return workRequirementScopeFactory.getEntitiesFromQuery(entityPermission, query);
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
            var workRequirementScope = workRequirementScopeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
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
            
            workRequirementScopeDetail = workRequirementScopeDetailFactory.create(workRequirementScopePK,
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

    @Inject
    protected WorkRequirementDetailFactory workRequirementDetailFactory;

    @Inject
    protected WorkRequirementFactory workRequirementFactory;

    @Inject
    WorkRequirementTransferCache workRequirementTransferCache;

    public WorkRequirement createWorkRequirement(String workRequirementName, WorkEffort workEffort, WorkRequirementScope workRequirementScope, Long startTime,
            Long requiredTime, BasePK createdBy) {
        var workRequirement = workRequirementFactory.create();
        var workRequirementDetail = workRequirementDetailFactory.create(workRequirement,
                workRequirementName, workEffort, workRequirementScope, startTime, requiredTime, session.getStartTime(),
                Session.MAX_TIME);
        
        // Convert to R/W
        workRequirement = workRequirementFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                workRequirement.getPrimaryKey());
        workRequirement.setActiveDetail(workRequirementDetail);
        workRequirement.setLastDetail(workRequirementDetail);
        workRequirement.store();
        
        sendEvent(workRequirement.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        createWorkRequirementStatus(workRequirement);
        
        return workRequirement;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.WorkRequirement */
    public WorkRequirement getWorkRequirementByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new WorkRequirementPK(entityInstance.getEntityUniqueId());

        return workRequirementFactory.getEntityFromPK(entityPermission, pk);
    }

    public WorkRequirement getWorkRequirementByEntityInstance(EntityInstance entityInstance) {
        return getWorkRequirementByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public WorkRequirement getWorkRequirementByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getWorkRequirementByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countWorkRequirements() {
        return session.getDslContext()
                .selectCount()
                .from(WorkRequirements)
                .join(WorkRequirementDetails).onKey(WORK_REQUIREMENTS_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countWorkRequirementsByWorkEffort(final WorkEffort workEffort) {
        return session.getDslContext()
                .selectCount()
                .from(WorkRequirements)
                .join(WorkRequirementDetails).onKey(WORK_REQUIREMENTS_ACTIVE_DETAIL_FK)
                .where(WorkRequirementDetails.WORK_EFFORT.eq(workEffort.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countWorkRequirementsByWorkRequirementScope(final WorkRequirementScope workRequirementScope) {
        return session.getDslContext()
                .selectCount()
                .from(WorkRequirements)
                .join(WorkRequirementDetails).onKey(WORK_REQUIREMENTS_ACTIVE_DETAIL_FK)
                .where(WorkRequirementDetails.WORK_REQUIREMENT_SCOPE.eq(workRequirementScope.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<WorkRequirement> getWorkRequirementsByWorkRequirementScope(WorkRequirementScope workRequirementScope, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(WorkRequirements.fields())
                .from(WorkRequirements)
                .join(WorkRequirementDetails).onKey(WORK_REQUIREMENTS_ACTIVE_DETAIL_FK)
                .where(WorkRequirementDetails.WORK_REQUIREMENT_SCOPE.eq(workRequirementScope.getPrimaryKey()));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(WorkRequirementDetails.WORK_REQUIREMENT_NAME),
                    WorkRequirementFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };
            
        return workRequirementFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<WorkRequirement> getWorkRequirementsByWorkRequirementScope(WorkRequirementScope workRequirementScope) {
        return getWorkRequirementsByWorkRequirementScope(workRequirementScope, EntityPermission.READ_ONLY);
    }
    
    public List<WorkRequirement> getWorkRequirementsByWorkRequirementScopeForUpdate(WorkRequirementScope workRequirementScope) {
        return getWorkRequirementsByWorkRequirementScope(workRequirementScope, EntityPermission.READ_WRITE);
    }
    
    private List<WorkRequirement> getWorkRequirementsByWorkEffort(WorkEffort workEffort, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(WorkRequirements.fields())
                .from(WorkRequirements)
                .join(WorkRequirementDetails).onKey(WORK_REQUIREMENTS_ACTIVE_DETAIL_FK)
                .where(WorkRequirementDetails.WORK_EFFORT.eq(workEffort.getPrimaryKey()));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(WorkRequirementDetails.WORK_REQUIREMENT_NAME),
                    WorkRequirementFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };
            
        return workRequirementFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<WorkRequirement> getWorkRequirementsByWorkEffort(WorkEffort workEffort) {
        return getWorkRequirementsByWorkEffort(workEffort, EntityPermission.READ_ONLY);
    }
    
    public List<WorkRequirement> getWorkRequirementsByWorkEffortForUpdate(WorkEffort workEffort) {
        return getWorkRequirementsByWorkEffort(workEffort, EntityPermission.READ_WRITE);
    }
    
    private WorkRequirement getWorkRequirementByName(String workRequirementName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(WorkRequirements.fields())
                .from(WorkRequirements)
                .join(WorkRequirementDetails).onKey(WORK_REQUIREMENTS_ACTIVE_DETAIL_FK)
                .where(WorkRequirementDetails.WORK_REQUIREMENT_NAME.eq(workRequirementName));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };
            
        return workRequirementFactory.getEntityFromQuery(entityPermission, query);
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
            var workRequirement = workRequirementFactory.getEntityFromPK(EntityPermission.READ_WRITE,
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
            
            workRequirementDetail = workRequirementDetailFactory.create(workRequirementPK,
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

    @Inject
    protected WorkRequirementStatusFactory workRequirementStatusFactory;

    public WorkRequirementStatus createWorkRequirementStatus(WorkRequirement workRequirement) {
        return workRequirementStatusFactory.create(workRequirement, 0, null, 0, null);
    }
    
    private WorkRequirementStatus getWorkRequirementStatus(WorkRequirement workRequirement, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(WorkRequirementStatuses.fields())
                .from(WorkRequirementStatuses)
                .where(WorkRequirementStatuses.WORK_REQUIREMENT.eq(workRequirement.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return workRequirementStatusFactory.getEntityFromQuery(entityPermission, query);
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

    @Inject
    protected WorkAssignmentDetailFactory workAssignmentDetailFactory;

    @Inject
    protected WorkAssignmentFactory workAssignmentFactory;

    @Inject
    WorkAssignmentTransferCache workAssignmentTransferCache;

    public WorkAssignment createWorkAssignment(WorkRequirement workRequirement, Party party, Long startTime, Long endTime, BasePK createdBy) {
        var workRequirementStatus = getWorkRequirementStatusForUpdate(workRequirement);
        Integer workAssignmentSequence = workRequirementStatus.getWorkAssignmentSequence() + 1;

        workRequirementStatus.setWorkAssignmentSequence(workAssignmentSequence);

        return createWorkAssignment(workRequirement,  workAssignmentSequence, party, startTime, endTime, createdBy);
    }

    public WorkAssignment createWorkAssignment(WorkRequirement workRequirement, Integer workAssignmentSequence, Party party, Long startTime, Long endTime, BasePK createdBy) {
        var workAssignment = workAssignmentFactory.create();
        var workAssignmentDetail = workAssignmentDetailFactory.create(workAssignment, workRequirement, workAssignmentSequence, party, startTime, endTime,
                session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        workAssignment = workAssignmentFactory.getEntityFromPK(EntityPermission.READ_WRITE, workAssignment.getPrimaryKey());
        workAssignment.setActiveDetail(workAssignmentDetail);
        workAssignment.setLastDetail(workAssignmentDetail);
        workAssignment.store();

        sendEvent(workAssignment.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return workAssignment;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.WorkAssignment */
    public WorkAssignment getWorkAssignmentByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new WorkAssignmentPK(entityInstance.getEntityUniqueId());

        return workAssignmentFactory.getEntityFromPK(entityPermission, pk);
    }

    public WorkAssignment getWorkAssignmentByEntityInstance(EntityInstance entityInstance) {
        return getWorkAssignmentByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public WorkAssignment getWorkAssignmentByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getWorkAssignmentByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countWorkAssignmentsByWorkRequirement(final WorkRequirement workRequirement) {
        return session.getDslContext()
                .selectCount()
                .from(WorkAssignments)
                .join(WorkAssignmentDetails).onKey(WORK_ASSIGNMENTS_ACTIVE_DETAIL_FK)
                .where(WorkAssignmentDetails.WORK_REQUIREMENT.eq(workRequirement.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countWorkAssignmentsByParty(final Party party) {
        return session.getDslContext()
                .selectCount()
                .from(WorkAssignments)
                .join(WorkAssignmentDetails).onKey(WORK_ASSIGNMENTS_ACTIVE_DETAIL_FK)
                .where(WorkAssignmentDetails.PARTY.eq(party.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private WorkAssignment getWorkAssignment(WorkRequirement workRequirement, Integer workAssignmentSequence, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(WorkAssignments.fields())
                .from(WorkAssignments)
                .join(WorkAssignmentDetails).onKey(WORK_ASSIGNMENTS_ACTIVE_DETAIL_FK)
                .where(WorkAssignmentDetails.WORK_REQUIREMENT.eq(workRequirement.getPrimaryKey()),
                        WorkAssignmentDetails.WORK_ASSIGNMENT_SEQUENCE.eq(workAssignmentSequence));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return workAssignmentFactory.getEntityFromQuery(entityPermission, query);
    }

    public WorkAssignment getWorkAssignment(WorkRequirement workRequirement, Integer workAssignmentSequence) {
        return getWorkAssignment(workRequirement, workAssignmentSequence, EntityPermission.READ_ONLY);
    }

    public WorkAssignment getWorkAssignmentForUpdate(WorkRequirement workRequirement, Integer workAssignmentSequence) {
        return getWorkAssignment(workRequirement, workAssignmentSequence, EntityPermission.READ_WRITE);
    }

    private List<WorkAssignment> getWorkAssignmentsByWorkRequirement(WorkRequirement workRequirement, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(WorkAssignments.fields())
                .from(WorkAssignments)
                .join(WorkAssignmentDetails).onKey(WORK_ASSIGNMENTS_ACTIVE_DETAIL_FK)
                .where(WorkAssignmentDetails.WORK_REQUIREMENT.eq(workRequirement.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(WorkAssignmentDetails.WORK_ASSIGNMENT_SEQUENCE),
                    WorkAssignmentFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return workAssignmentFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<WorkAssignment> getWorkAssignmentsByWorkRequirement(WorkRequirement workRequirement) {
        return getWorkAssignmentsByWorkRequirement(workRequirement, EntityPermission.READ_ONLY);
    }

    public List<WorkAssignment> getWorkAssignmentsByWorkRequirementForUpdate(WorkRequirement workRequirement) {
        return getWorkAssignmentsByWorkRequirement(workRequirement, EntityPermission.READ_WRITE);
    }

    private List<WorkAssignment> getWorkAssignmentsByParty(Party party, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(WorkAssignments.fields())
                    .from(WorkAssignments)
                    .join(WorkAssignmentDetails).onKey(WORK_ASSIGNMENTS_ACTIVE_DETAIL_FK)
                    .join(WorkRequirements).onKey(WORK_ASSIGNMENT_DETAILS_WORK_REQUIREMENT_FK)
                    .join(WorkRequirementDetails).onKey(WORK_REQUIREMENTS_LAST_DETAIL_FK)
                    .where(WorkAssignmentDetails.PARTY.eq(party.getPrimaryKey()))
                    .orderBy(WorkRequirementDetails.WORK_REQUIREMENT_NAME, WorkAssignmentDetails.WORK_ASSIGNMENT_SEQUENCE),
                    WorkAssignmentFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(WorkAssignments.fields())
                    .from(WorkAssignments)
                    .join(WorkAssignmentDetails).onKey(WORK_ASSIGNMENTS_ACTIVE_DETAIL_FK)
                    .where(WorkAssignmentDetails.PARTY.eq(party.getPrimaryKey()))
                    .forUpdate();
        };

        return workAssignmentFactory.getEntitiesFromQuery(entityPermission, query);
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
            var workAssignment = workAssignmentFactory.getEntityFromPK(EntityPermission.READ_WRITE,
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

            workAssignmentDetail = workAssignmentDetailFactory.create(workAssignmentPK, workRequirementPK, workAssignmentSequence, partyPK, startTime, endTime,
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

    @Inject
    protected WorkTimeDetailFactory workTimeDetailFactory;

    @Inject
    protected WorkTimeFactory workTimeFactory;

    @Inject
    WorkTimeTransferCache workTimeTransferCache;

    public WorkTime createWorkTime(WorkRequirement workRequirement, Party party, Long startTime, Long endTime, BasePK createdBy) {
        var workRequirementStatus = getWorkRequirementStatusForUpdate(workRequirement);
        Integer workTimeSequence = workRequirementStatus.getWorkTimeSequence() + 1;

        workRequirementStatus.setWorkTimeSequence(workTimeSequence);

        return createWorkTime(workRequirement,  workTimeSequence, party, startTime, endTime, createdBy);
    }

    public WorkTime createWorkTime(WorkRequirement workRequirement, Integer workTimeSequence, Party party, Long startTime, Long endTime, BasePK createdBy) {
        var workTime = workTimeFactory.create();
        var workTimeDetail = workTimeDetailFactory.create(workTime, workRequirement, workTimeSequence, party, startTime, endTime,
                session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        workTime = workTimeFactory.getEntityFromPK(EntityPermission.READ_WRITE, workTime.getPrimaryKey());
        workTime.setActiveDetail(workTimeDetail);
        workTime.setLastDetail(workTimeDetail);
        workTime.store();

        sendEvent(workTime.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return workTime;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.WorkTime */
    public WorkTime getWorkTimeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new WorkTimePK(entityInstance.getEntityUniqueId());

        return workTimeFactory.getEntityFromPK(entityPermission, pk);
    }

    public WorkTime getWorkTimeByEntityInstance(EntityInstance entityInstance) {
        return getWorkTimeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public WorkTime getWorkTimeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getWorkTimeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countWorkTimesByWorkRequirement(final WorkRequirement workRequirement) {
        return session.getDslContext()
                .selectCount()
                .from(WorkTimes)
                .join(WorkTimeDetails).onKey(WORK_TIMES_ACTIVE_DETAIL_FK)
                .where(WorkTimeDetails.WORK_REQUIREMENT.eq(workRequirement.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countWorkTimesByParty(final Party party) {
        return session.getDslContext()
                .selectCount()
                .from(WorkTimes)
                .join(WorkTimeDetails).onKey(WORK_TIMES_ACTIVE_DETAIL_FK)
                .where(WorkTimeDetails.PARTY.eq(party.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private WorkTime getWorkTime(WorkRequirement workRequirement, Integer workTimeSequence, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(WorkTimes.fields())
                .from(WorkTimes)
                .join(WorkTimeDetails).onKey(WORK_TIMES_ACTIVE_DETAIL_FK)
                .where(WorkTimeDetails.WORK_REQUIREMENT.eq(workRequirement.getPrimaryKey()),
                        WorkTimeDetails.WORK_TIME_SEQUENCE.eq(workTimeSequence));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return workTimeFactory.getEntityFromQuery(entityPermission, query);
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
    
    private List<WorkTime> getWorkTimesByWorkRequirement(WorkRequirement workRequirement, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(WorkTimes.fields())
                .from(WorkTimes)
                .join(WorkTimeDetails).onKey(WORK_TIMES_ACTIVE_DETAIL_FK)
                .where(WorkTimeDetails.WORK_REQUIREMENT.eq(workRequirement.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(WorkTimeDetails.WORK_TIME_SEQUENCE),
                    WorkTimeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return workTimeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<WorkTime> getWorkTimesByWorkRequirement(WorkRequirement workRequirement) {
        return getWorkTimesByWorkRequirement(workRequirement, EntityPermission.READ_ONLY);
    }
    
    public List<WorkTime> getWorkTimesByWorkRequirementForUpdate(WorkRequirement workRequirement) {
        return getWorkTimesByWorkRequirement(workRequirement, EntityPermission.READ_WRITE);
    }
    
    private List<WorkTime> getWorkTimesByParty(Party party, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(WorkTimes.fields())
                .from(WorkTimes)
                .join(WorkTimeDetails).onKey(WORK_TIMES_ACTIVE_DETAIL_FK)
                .where(WorkTimeDetails.PARTY.eq(party.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(WorkTimes.fields())
                    .from(WorkTimes)
                    .join(WorkTimeDetails).onKey(WORK_TIMES_ACTIVE_DETAIL_FK)
                    .join(WorkRequirements).onKey(WORK_TIME_DETAILS_WORK_REQUIREMENT_FK)
                    .join(WorkRequirementDetails).onKey(WORK_REQUIREMENTS_LAST_DETAIL_FK)
                    .where(WorkTimeDetails.PARTY.eq(party.getPrimaryKey()))
                    .orderBy(WorkRequirementDetails.WORK_REQUIREMENT_NAME, WorkTimeDetails.WORK_TIME_SEQUENCE),
                    WorkTimeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return workTimeFactory.getEntitiesFromQuery(entityPermission, query);
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
            var workTime = workTimeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
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

            workTimeDetail = workTimeDetailFactory.create(workTimePK, workRequirementPK, workTimeSequence, partyPK, startTime, endTime,
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

    @Inject
    protected WorkTimeUserVisitFactory workTimeUserVisitFactory;

    public WorkTimeUserVisit createWorkTimeUserVisit(WorkTime workTime, UserVisit userVisit) {
        return workTimeUserVisitFactory.create(workTime, userVisit, session.getStartTime(), Session.MAX_TIME);
    }
    
    private WorkTimeUserVisit getWorkTimeUserVisit(WorkTime workTime, UserVisit userVisit, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(WorkTimeUserVisits.fields())
                .from(WorkTimeUserVisits)
                .where(WorkTimeUserVisits.WORK_TIME.eq(workTime.getPrimaryKey()),
                        WorkTimeUserVisits.USER_VISIT.eq(userVisit.getPrimaryKey()),
                        WorkTimeUserVisits.THRU_TIME.eq(Session.MAX_TIME));
    
        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return workTimeUserVisitFactory.getEntityFromQuery(entityPermission, query);
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
    
    private List<WorkTimeUserVisit> getWorkTimeUserVisitsByWorkTime(WorkTime workTime, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(WorkTimeUserVisits.fields())
                    .from(WorkTimeUserVisits)
                    .join(UserVisits).onKey(WORK_TIME_USER_VISITS_USER_VISIT_FK)
                    .where(WorkTimeUserVisits.WORK_TIME.eq(workTime.getPrimaryKey()),
                            WorkTimeUserVisits.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(UserVisits.FROM_TIME),
                    WorkTimeUserVisitFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(WorkTimeUserVisits.fields())
                    .from(WorkTimeUserVisits)
                    .where(WorkTimeUserVisits.WORK_TIME.eq(workTime.getPrimaryKey()),
                            WorkTimeUserVisits.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return workTimeUserVisitFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<WorkTimeUserVisit> getWorkTimeUserVisitsByWorkTime(WorkTime workTime) {
        return getWorkTimeUserVisitsByWorkTime(workTime, EntityPermission.READ_ONLY);
    }
    
    public List<WorkTimeUserVisit> getWorkTimeUserVisitsByWorkTimeForUpdate(WorkTime workTime) {
        return getWorkTimeUserVisitsByWorkTime(workTime, EntityPermission.READ_WRITE);
    }
    
    private List<WorkTimeUserVisit> getWorkTimeUserVisitsByUserVisit(UserVisit userVisit, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(WorkTimeUserVisits.fields())
                    .from(WorkTimeUserVisits)
                    .join(WorkTimes).onKey(WORK_TIME_USER_VISITS_WORK_TIME_FK)
                    .join(WorkTimeDetails).onKey(WORK_TIMES_LAST_DETAIL_FK)
                    .where(WorkTimeUserVisits.USER_VISIT.eq(userVisit.getPrimaryKey()),
                            WorkTimeUserVisits.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(WorkTimeDetails.WORK_TIME_SEQUENCE),
                    WorkTimeUserVisitFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(WorkTimeUserVisits.fields())
                    .from(WorkTimeUserVisits)
                    .where(WorkTimeUserVisits.USER_VISIT.eq(userVisit.getPrimaryKey()),
                            WorkTimeUserVisits.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return workTimeUserVisitFactory.getEntitiesFromQuery(entityPermission, query);
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
