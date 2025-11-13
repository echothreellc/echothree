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

package com.echothree.model.control.workflow.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.selector.common.SelectorKinds;
import com.echothree.model.control.selector.server.evaluator.CachedSelector;
import com.echothree.model.control.selector.server.evaluator.PostalAddressSelectorEvaluator;
import com.echothree.model.control.workflow.common.choice.BaseWorkflowChoicesBean;
import com.echothree.model.control.workflow.common.choice.WorkflowChoicesBean;
import com.echothree.model.control.workflow.common.choice.WorkflowStepChoicesBean;
import com.echothree.model.control.workflow.common.choice.WorkflowStepTypeChoicesBean;
import com.echothree.model.control.workflow.common.transfer.WorkflowDescriptionTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowDestinationDescriptionTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowDestinationPartyTypeTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowDestinationSecurityRoleTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowDestinationSelectorTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowDestinationStepTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowDestinationTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityTypeTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntranceDescriptionTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntrancePartyTypeTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntranceSecurityRoleTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntranceSelectorTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntranceStepTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntranceTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowSelectorKindTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowStepDescriptionTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowStepTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowStepTypeTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowTransfer;
import com.echothree.model.control.workflow.server.logic.WorkflowSecurityLogic;
import com.echothree.model.control.workflow.server.transfer.WorkflowTransferCaches;
import com.echothree.model.control.workrequirement.server.control.WorkRequirementControl;
import com.echothree.model.control.workrequirement.server.logic.WorkRequirementLogic;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.security.server.entity.SecurityRole;
import com.echothree.model.data.security.server.entity.SecurityRoleGroup;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.selector.server.entity.SelectorKind;
import com.echothree.model.data.selector.server.entity.SelectorType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workeffort.server.entity.WorkEffort;
import com.echothree.model.data.workeffort.server.entity.WorkEffortScope;
import com.echothree.model.data.workflow.common.pk.WorkflowDestinationPK;
import com.echothree.model.data.workflow.common.pk.WorkflowEntrancePK;
import com.echothree.model.data.workflow.common.pk.WorkflowPK;
import com.echothree.model.data.workflow.common.pk.WorkflowStepPK;
import com.echothree.model.data.workflow.common.pk.WorkflowStepTypePK;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowDescription;
import com.echothree.model.data.workflow.server.entity.WorkflowDestination;
import com.echothree.model.data.workflow.server.entity.WorkflowDestinationDescription;
import com.echothree.model.data.workflow.server.entity.WorkflowDestinationPartyType;
import com.echothree.model.data.workflow.server.entity.WorkflowDestinationSecurityRole;
import com.echothree.model.data.workflow.server.entity.WorkflowDestinationSelector;
import com.echothree.model.data.workflow.server.entity.WorkflowDestinationStep;
import com.echothree.model.data.workflow.server.entity.WorkflowEntityStatus;
import com.echothree.model.data.workflow.server.entity.WorkflowEntityType;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.model.data.workflow.server.entity.WorkflowEntranceDescription;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrancePartyType;
import com.echothree.model.data.workflow.server.entity.WorkflowEntranceSecurityRole;
import com.echothree.model.data.workflow.server.entity.WorkflowEntranceSelector;
import com.echothree.model.data.workflow.server.entity.WorkflowEntranceStep;
import com.echothree.model.data.workflow.server.entity.WorkflowSelectorKind;
import com.echothree.model.data.workflow.server.entity.WorkflowStep;
import com.echothree.model.data.workflow.server.entity.WorkflowStepDescription;
import com.echothree.model.data.workflow.server.entity.WorkflowStepType;
import com.echothree.model.data.workflow.server.entity.WorkflowStepTypeDescription;
import com.echothree.model.data.workflow.server.entity.WorkflowTrigger;
import com.echothree.model.data.workflow.server.factory.WorkflowDescriptionFactory;
import com.echothree.model.data.workflow.server.factory.WorkflowDestinationDescriptionFactory;
import com.echothree.model.data.workflow.server.factory.WorkflowDestinationDetailFactory;
import com.echothree.model.data.workflow.server.factory.WorkflowDestinationFactory;
import com.echothree.model.data.workflow.server.factory.WorkflowDestinationPartyTypeFactory;
import com.echothree.model.data.workflow.server.factory.WorkflowDestinationSecurityRoleFactory;
import com.echothree.model.data.workflow.server.factory.WorkflowDestinationSelectorFactory;
import com.echothree.model.data.workflow.server.factory.WorkflowDestinationStepFactory;
import com.echothree.model.data.workflow.server.factory.WorkflowDetailFactory;
import com.echothree.model.data.workflow.server.factory.WorkflowEntityStatusFactory;
import com.echothree.model.data.workflow.server.factory.WorkflowEntityTypeFactory;
import com.echothree.model.data.workflow.server.factory.WorkflowEntranceDescriptionFactory;
import com.echothree.model.data.workflow.server.factory.WorkflowEntranceDetailFactory;
import com.echothree.model.data.workflow.server.factory.WorkflowEntranceFactory;
import com.echothree.model.data.workflow.server.factory.WorkflowEntrancePartyTypeFactory;
import com.echothree.model.data.workflow.server.factory.WorkflowEntranceSecurityRoleFactory;
import com.echothree.model.data.workflow.server.factory.WorkflowEntranceSelectorFactory;
import com.echothree.model.data.workflow.server.factory.WorkflowEntranceStepFactory;
import com.echothree.model.data.workflow.server.factory.WorkflowFactory;
import com.echothree.model.data.workflow.server.factory.WorkflowSelectorKindFactory;
import com.echothree.model.data.workflow.server.factory.WorkflowStepDescriptionFactory;
import com.echothree.model.data.workflow.server.factory.WorkflowStepDetailFactory;
import com.echothree.model.data.workflow.server.factory.WorkflowStepFactory;
import com.echothree.model.data.workflow.server.factory.WorkflowStepTypeDescriptionFactory;
import com.echothree.model.data.workflow.server.factory.WorkflowStepTypeFactory;
import com.echothree.model.data.workflow.server.factory.WorkflowTriggerFactory;
import com.echothree.model.data.workflow.server.value.WorkflowDescriptionValue;
import com.echothree.model.data.workflow.server.value.WorkflowDestinationDescriptionValue;
import com.echothree.model.data.workflow.server.value.WorkflowDestinationDetailValue;
import com.echothree.model.data.workflow.server.value.WorkflowDetailValue;
import com.echothree.model.data.workflow.server.value.WorkflowEntranceDescriptionValue;
import com.echothree.model.data.workflow.server.value.WorkflowEntranceDetailValue;
import com.echothree.model.data.workflow.server.value.WorkflowStepDescriptionValue;
import com.echothree.model.data.workflow.server.value.WorkflowStepDetailValue;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.security.InvalidParameterException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class WorkflowControl
        extends BaseModelControl {
    
    /** Creates a new instance of WorkflowControl */
    protected WorkflowControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Work Transfer Caches
    // --------------------------------------------------------------------------------
    
    private WorkflowTransferCaches workflowTransferCaches;
    
    public WorkflowTransferCaches getWorkflowTransferCaches() {
        if(workflowTransferCaches == null) {
            workflowTransferCaches = new WorkflowTransferCaches(userVisit, this);
        }
        
        return workflowTransferCaches;
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Step Types
    // --------------------------------------------------------------------------------
    
    public WorkflowStepType createWorkflowStepType(String workflowStepTypeName, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var workflowStepType = WorkflowStepTypeFactory.getInstance().create(workflowStepTypeName, isDefault, sortOrder);

        sendEvent(workflowStepType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return workflowStepType;
    }

    public long countWorkflowStepTypes() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM workflowsteptypes");
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.WorkflowStepType */
    public WorkflowStepType getWorkflowStepTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new WorkflowStepTypePK(entityInstance.getEntityUniqueId());

        return WorkflowStepTypeFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public WorkflowStepType getWorkflowStepTypeByEntityInstance(EntityInstance entityInstance) {
        return getWorkflowStepTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public WorkflowStepType getWorkflowStepTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getWorkflowStepTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getWorkflowStepTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM workflowsteptypes " +
                        "WHERE wkflst_workflowsteptypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM workflowsteptypes " +
                        "WHERE wkflst_workflowsteptypename = ? " +
                        "FOR UPDATE");
        getWorkflowStepTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    public WorkflowStepType getWorkflowStepTypeByName(String workflowStepTypeName, EntityPermission entityPermission) {
        return WorkflowStepTypeFactory.getInstance().getEntityFromQuery(entityPermission, getWorkflowStepTypeByNameQueries, workflowStepTypeName);
    }

    public WorkflowStepType getWorkflowStepTypeByName(String workflowStepTypeName) {
        return getWorkflowStepTypeByName(workflowStepTypeName, EntityPermission.READ_ONLY);
    }

    public WorkflowStepType getWorkflowStepTypeByNameForUpdate(String workflowStepTypeName) {
        return getWorkflowStepTypeByName(workflowStepTypeName, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getDefaultWorkflowStepTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM workflowsteptypes " +
                "WHERE wkflst_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM workflowsteptypes " +
                "WHERE wkflst_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultWorkflowStepTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    public WorkflowStepType getDefaultWorkflowStepType(EntityPermission entityPermission) {
        return WorkflowStepTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultWorkflowStepTypeQueries);
    }

    public WorkflowStepType getDefaultWorkflowStepType() {
        return getDefaultWorkflowStepType(EntityPermission.READ_ONLY);
    }

    public WorkflowStepType getDefaultWorkflowStepTypeForUpdate() {
        return getDefaultWorkflowStepType(EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getWorkflowStepTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM workflowsteptypes " +
                "ORDER BY wkflst_sortorder, wkflst_workflowsteptypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM workflowsteptypes " +
                "FOR UPDATE");
        getWorkflowStepTypesQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<WorkflowStepType> getWorkflowStepTypes(EntityPermission entityPermission) {
        return WorkflowStepTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getWorkflowStepTypesQueries);
    }
    
    public List<WorkflowStepType> getWorkflowStepTypes() {
        return getWorkflowStepTypes(EntityPermission.READ_ONLY);
    }
    
    public List<WorkflowStepType> getWorkflowStepTypesForUpdate() {
        return getWorkflowStepTypes(EntityPermission.READ_WRITE);
    }
    
    public WorkflowStepTypeTransfer getWorkflowStepTypeTransfer(UserVisit userVisit, WorkflowStepType workflowStepType) {
        return getWorkflowTransferCaches().getWorkflowStepTypeTransferCache().getWorkflowStepTypeTransfer(userVisit, workflowStepType);
    }
    
    public List<WorkflowStepTypeTransfer> getWorkflowStepTypeTransfers(UserVisit userVisit, Collection<WorkflowStepType> workflowStepTypes) {
        List<WorkflowStepTypeTransfer> workflowStepTypeTransfers = new ArrayList<>(workflowStepTypes.size());
        var workflowStepTypeTransferCache = getWorkflowTransferCaches(userVisit).getWorkflowStepTypeTransferCache();
        
        workflowStepTypes.forEach((workflowStepType) ->
                workflowStepTypeTransfers.add(workflowStepTypeTransferCache.getWorkflowStepTypeTransfer(workflowStepType))
        );
        
        return workflowStepTypeTransfers;
    }
    
    public List<WorkflowStepTypeTransfer> getWorkflowStepTypeTransfers(UserVisit userVisit) {
        return getWorkflowStepTypeTransfers(userVisit, getWorkflowStepTypes());
    }

    public WorkflowStepTypeChoicesBean getWorkflowStepTypeChoices(String defaultWorkflowStepTypeChoice,
            Language language, boolean allowNullChoice) {
        var workflowStepTypes = getWorkflowStepTypes();
        var size = workflowStepTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultWorkflowStepTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var workflowStepType : workflowStepTypes) {
            var label = getBestWorkflowStepTypeDescription(workflowStepType, language);
            var value = workflowStepType.getWorkflowStepTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultWorkflowStepTypeChoice != null && defaultWorkflowStepTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && workflowStepType.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new WorkflowStepTypeChoicesBean(labels, values, defaultValue);
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Step Type Descriptions
    // --------------------------------------------------------------------------------
    
    public WorkflowStepTypeDescription createWorkflowStepTypeDescription(WorkflowStepType workflowStepType,
            Language language, String description, BasePK createdBy) {
        var workflowStepTypeDescription = WorkflowStepTypeDescriptionFactory.getInstance().create(workflowStepType, language, description);

        sendEvent(workflowStepType.getPrimaryKey(), EventTypes.MODIFY, workflowStepTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return workflowStepTypeDescription;
    }
    
    public WorkflowStepTypeDescription getWorkflowStepTypeDescription(WorkflowStepType workflowStepType, Language language) {
        WorkflowStepTypeDescription workflowStepTypeDescription;
        
        try {
            var ps = WorkflowStepTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM workflowsteptypedescriptions " +
                    "WHERE wkflstd_wkflst_workflowsteptypeid = ? AND wkflstd_lang_languageid = ?");
            
            ps.setLong(1, workflowStepType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            workflowStepTypeDescription = WorkflowStepTypeDescriptionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return workflowStepTypeDescription;
    }
    
    public String getBestWorkflowStepTypeDescription(WorkflowStepType workflowStepType, Language language) {
        String description;
        var workflowStepTypeDescription = getWorkflowStepTypeDescription(workflowStepType, language);
        
        if(workflowStepTypeDescription == null && !language.getIsDefault()) {
            workflowStepTypeDescription = getWorkflowStepTypeDescription(workflowStepType, partyControl.getDefaultLanguage());
        }
        
        if(workflowStepTypeDescription == null) {
            description = workflowStepType.getWorkflowStepTypeName();
        } else {
            description = workflowStepTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Workflows
    // --------------------------------------------------------------------------------
    
    public Workflow createWorkflow(String workflowName, SelectorType selectorType,
            SecurityRoleGroup securityRoleGroup, Integer sortOrder, BasePK createdBy) {
        var workflow = WorkflowFactory.getInstance().create();
        var workflowDetail = WorkflowDetailFactory.getInstance().create(workflow, workflowName,
                selectorType, securityRoleGroup, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        workflow = WorkflowFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, workflow.getPrimaryKey());
        workflow.setActiveDetail(workflowDetail);
        workflow.setLastDetail(workflowDetail);
        workflow.store();
        
        sendEvent(workflow.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return workflow;
    }

    public long countWorkflows() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM workflows, workflowdetails " +
                "WHERE wkfl_activedetailid = wkfldt_workflowdetailid");
    }

    public long countWorkflowsBySelectorKind(SelectorKind selectorKind) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM workflowselectorkinds, workflows, workflowdetails " +
                "WHERE wkflslk_slk_selectorkindid = ? AND wkflslk_thrutime = ? AND wkflslk_wkfl_workflowid = wkfl_workflowid " +
                "AND wkfl_activedetailid = wkfldt_workflowdetailid",
                selectorKind, Session.MAX_TIME);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.Workflow */
    public Workflow getWorkflowByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new WorkflowPK(entityInstance.getEntityUniqueId());

        return WorkflowFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public Workflow getWorkflowByEntityInstance(EntityInstance entityInstance) {
        return getWorkflowByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public Workflow getWorkflowByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getWorkflowByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public List<Workflow> getWorkflows() {
        var ps = WorkflowFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM workflows, workflowdetails " +
                "WHERE wkfl_activedetailid = wkfldt_workflowdetailid " +
                "ORDER BY wkfldt_sortorder, wkfldt_workflowname " +
                "_LIMIT_");
        
        return WorkflowFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public List<Workflow> getWorkflowsBySelectorKind(SelectorKind selectorKind) {
        var ps = WorkflowFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM workflowselectorkinds, workflows, workflowdetails " +
                "WHERE wkflslk_slk_selectorkindid = ? AND wkflslk_thrutime = ? " +
                "AND wkflslk_wkfl_workflowid = wkfl_workflowid AND wkfl_lastdetailid = wkfldt_workflowdetailid " +
                "ORDER BY wkfldt_sortorder, wkfldt_workflowname " +
                "_LIMIT_");

        return WorkflowFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps,
                selectorKind, Session.MAX_TIME);
    }
    
    public List<Workflow> getWorkflowsByEntityType(final EntityType entityType) {
        var ps = WorkflowFactory.getInstance().prepareStatement(
                "SELECT _ALL_ "
                + "FROM workflows, workflowdetails, workflowentitytypes "
                + "WHERE wkfl_activedetailid = wkfldt_workflowdetailid "
                + "AND wkfl_workflowid = wkflent_wkfl_workflowid AND wkflent_ent_entitytypeid = ? AND wkflent_thrutime = ? "
                + "ORDER BY wkfldt_sortorder, wkfldt_workflowname "
                + "_LIMIT_");

        return WorkflowFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps,
                entityType, Session.MAX_TIME);
    }
    
    private static final Map<EntityPermission, String> getWorkflowByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM workflows, workflowdetails " +
                "WHERE wkfl_activedetailid = wkfldt_workflowdetailid AND wkfldt_workflowname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM workflows, workflowdetails " +
                "WHERE wkfl_activedetailid = wkfldt_workflowdetailid AND wkfldt_workflowname = ? " +
                "FOR UPDATE");
        getWorkflowByNameQueries = Collections.unmodifiableMap(queryMap);
    }
    
    public Workflow getWorkflowByName(String workflowName, EntityPermission entityPermission) {
        return WorkflowFactory.getInstance().getEntityFromQuery(entityPermission, getWorkflowByNameQueries,
                workflowName);
    }
    
    public Workflow getWorkflowByName(String workflowName) {
        return getWorkflowByName(workflowName, EntityPermission.READ_ONLY);
    }
    
    public Workflow getWorkflowByNameForUpdate(String workflowName) {
        return getWorkflowByName(workflowName, EntityPermission.READ_WRITE);
    }
    
    public WorkflowDetailValue getWorkflowDetailValueForUpdate(Workflow workflow) {
        return workflow == null? null: workflow.getLastDetailForUpdate().getWorkflowDetailValue().clone();
    }
    
    public WorkflowDetailValue getWorkflowDetailValueByNameForUpdate(String workflowName) {
        return getWorkflowDetailValueForUpdate(getWorkflowByNameForUpdate(workflowName));
    }
    
    public WorkflowTransfer getWorkflowTransfer(UserVisit userVisit, Workflow workflow) {
        return getWorkflowTransferCaches().getWorkflowTransferCache().getWorkflowTransfer(userVisit, workflow);
    }
    
    public List<WorkflowTransfer> getWorkflowTransfers(UserVisit userVisit, Collection<Workflow> workflows) {
        List<WorkflowTransfer> workflowTransfers = new ArrayList<>(workflows.size());
        var workflowTransferCache = getWorkflowTransferCaches(userVisit).getWorkflowTransferCache();
        
        workflows.forEach((workflow) ->
                workflowTransfers.add(workflowTransferCache.getWorkflowTransfer(workflow))
        );
        
        return workflowTransfers;
    }
    
    public List<WorkflowTransfer> getWorkflowTransfers(UserVisit userVisit) {
        return getWorkflowTransfers(userVisit, getWorkflows());
    }
    
    public List<WorkflowTransfer> getWorkflowTransfersBySelectorKind(UserVisit userVisit, SelectorKind selectorKind) {
        return getWorkflowTransfers(userVisit, getWorkflowsBySelectorKind(selectorKind));
    }

    public WorkflowChoicesBean getWorkflowChoices(final String defaultWorkflowChoice, final Language language,
            final boolean allowNullChoice) {
        var workflows = getWorkflows();
        var size = workflows.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultWorkflowChoice == null) {
                defaultValue = "";
            }
        }

        for(var workflow : workflows) {
            var workflowDetail = workflow.getLastDetail();
            var label = getBestWorkflowDescription(workflow, language);
            var value = workflowDetail.getWorkflowName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultWorkflowChoice != null && defaultWorkflowChoice.equals(value);
            if(usingDefaultChoice || defaultValue == null)
                defaultValue = value;
        }

        return new WorkflowChoicesBean(labels, values, defaultValue);
    }

    public void updateWorkflowFromValue(WorkflowDetailValue workflowDetailValue, BasePK updatedBy) {
        if(workflowDetailValue.hasBeenModified()) {
            var workflow = WorkflowFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, workflowDetailValue.getWorkflowPK());
            var workflowDetail = workflow.getActiveDetailForUpdate();
            
            workflowDetail.setThruTime(session.START_TIME_LONG);
            workflowDetail.store();

            var workflowPK = workflowDetail.getWorkflowPK();
            var workflowName = workflowDetailValue.getWorkflowName();
            var selectorTypePK = workflowDetailValue.getSelectorTypePK();
            var securityRoleGroupPK = workflowDetailValue.getSecurityRoleGroupPK();
            var sortOrder = workflowDetailValue.getSortOrder();
            
            workflowDetail = WorkflowDetailFactory.getInstance().create(workflowPK, workflowName, selectorTypePK, securityRoleGroupPK,
                    sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            workflow.setActiveDetail(workflowDetail);
            workflow.setLastDetail(workflowDetail);
            
            sendEvent(workflowPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deleteWorkflow(Workflow workflow, BasePK deletedBy) {
        deleteWorkflowDescriptionsByWorkflow(workflow, deletedBy);
        deleteWorkflowStepsByWorkflow(workflow, deletedBy);
        deleteWorkflowEntityTypesByWorkflow(workflow, deletedBy);
        deleteWorkflowEntrancesByWorkflow(workflow, deletedBy);
        deleteWorkflowSelectorKindsByWorkflow(workflow, deletedBy);

        var workflowDetail = workflow.getLastDetailForUpdate();
        workflowDetail.setThruTime(session.START_TIME_LONG);
        workflow.setActiveDetail(null);
        workflow.store();
        
        sendEvent(workflow.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Descriptions
    // --------------------------------------------------------------------------------
    
    public WorkflowDescription createWorkflowDescription(Workflow workflow, Language language, String description, BasePK createdBy) {
        var workflowDescription = WorkflowDescriptionFactory.getInstance().create(workflow, language,
                description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(workflow.getPrimaryKey(), EventTypes.MODIFY, workflowDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return workflowDescription;
    }
    
    private static final Map<EntityPermission, String> getWorkflowDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM workflowdescriptions " +
                "WHERE wkfld_wkfl_workflowid = ? AND wkfld_lang_languageid = ? AND wkfld_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM workflowdescriptions " +
                "WHERE wkfld_wkfl_workflowid = ? AND wkfld_lang_languageid = ? AND wkfld_thrutime = ? " +
                "FOR UPDATE");
        getWorkflowDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private WorkflowDescription getWorkflowDescription(Workflow workflow, Language language, EntityPermission entityPermission) {
        return WorkflowDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getWorkflowDescriptionQueries,
                workflow, language, Session.MAX_TIME_LONG);
    }
    
    public WorkflowDescription getWorkflowDescription(Workflow workflow, Language language) {
        return getWorkflowDescription(workflow, language, EntityPermission.READ_ONLY);
    }
    
    public WorkflowDescription getWorkflowDescriptionForUpdate(Workflow workflow, Language language) {
        return getWorkflowDescription(workflow, language, EntityPermission.READ_WRITE);
    }
    
    public WorkflowDescriptionValue getWorkflowDescriptionValue(WorkflowDescription workflowDescription) {
        return workflowDescription == null? null: workflowDescription.getWorkflowDescriptionValue().clone();
    }
    
    public WorkflowDescriptionValue getWorkflowDescriptionValueForUpdate(Workflow workflow, Language language) {
        return getWorkflowDescriptionValue(getWorkflowDescriptionForUpdate(workflow, language));
    }
    
    private static final Map<EntityPermission, String> getWorkflowDescriptionsByWorkflowQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);
        
        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM workflowdescriptions, languages " +
                "WHERE wkfld_wkfl_workflowid = ? AND wkfld_thrutime = ? AND wkfld_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM workflowdescriptions " +
                "WHERE wkfld_wkfl_workflowid = ? AND wkfld_thrutime = ? " +
                "FOR UPDATE");
        getWorkflowDescriptionsByWorkflowQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<WorkflowDescription> getWorkflowDescriptionsByWorkflow(Workflow workflow, EntityPermission entityPermission) {
        return WorkflowDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getWorkflowDescriptionsByWorkflowQueries,
                workflow, Session.MAX_TIME_LONG);
    }
    
    public List<WorkflowDescription> getWorkflowDescriptionsByWorkflow(Workflow workflow) {
        return getWorkflowDescriptionsByWorkflow(workflow, EntityPermission.READ_ONLY);
    }
    
    public List<WorkflowDescription> getWorkflowDescriptionsByWorkflowForUpdate(Workflow workflow) {
        return getWorkflowDescriptionsByWorkflow(workflow, EntityPermission.READ_WRITE);
    }
    
    public String getBestWorkflowDescription(Workflow workflow, Language language) {
        String description;
        var workflowDescription = getWorkflowDescription(workflow, language);
        
        if(workflowDescription == null && !language.getIsDefault()) {
            workflowDescription = getWorkflowDescription(workflow, partyControl.getDefaultLanguage());
        }
        
        if(workflowDescription == null) {
            description = workflow.getLastDetail().getWorkflowName();
        } else {
            description = workflowDescription.getDescription();
        }
        
        return description;
    }
    
    public WorkflowDescriptionTransfer getWorkflowDescriptionTransfer(UserVisit userVisit, WorkflowDescription workflowDescription) {
        return getWorkflowTransferCaches().getWorkflowDescriptionTransferCache().getWorkflowDescriptionTransfer(userVisit, workflowDescription);
    }
    
    public List<WorkflowDescriptionTransfer> getWorkflowDescriptionTransfersByWorkflow(UserVisit userVisit, Workflow workflow) {
        var workflowDescriptions = getWorkflowDescriptionsByWorkflow(workflow);
        List<WorkflowDescriptionTransfer> workflowDescriptionTransfers = new ArrayList<>(workflowDescriptions.size());
        var workflowDescriptionTransferCache = getWorkflowTransferCaches(userVisit).getWorkflowDescriptionTransferCache();
        
        workflowDescriptions.forEach((workflowDescription) ->
                workflowDescriptionTransfers.add(workflowDescriptionTransferCache.getWorkflowDescriptionTransfer(workflowDescription))
        );
        
        return workflowDescriptionTransfers;
    }
    
    public void updateWorkflowDescriptionFromValue(WorkflowDescriptionValue workflowDescriptionValue, BasePK updatedBy) {
        if(workflowDescriptionValue.hasBeenModified()) {
            var workflowDescription = WorkflowDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, workflowDescriptionValue.getPrimaryKey());
            
            workflowDescription.setThruTime(session.START_TIME_LONG);
            workflowDescription.store();

            var workflow = workflowDescription.getWorkflow();
            var language = workflowDescription.getLanguage();
            var description = workflowDescriptionValue.getDescription();
            
            workflowDescription = WorkflowDescriptionFactory.getInstance().create(workflow, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(workflow.getPrimaryKey(), EventTypes.MODIFY, workflowDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteWorkflowDescription(WorkflowDescription workflowDescription, BasePK deletedBy) {
        workflowDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(workflowDescription.getWorkflowPK(), EventTypes.MODIFY, workflowDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteWorkflowDescriptionsByWorkflow(Workflow workflow, BasePK deletedBy) {
        var workflowDescriptions = getWorkflowDescriptionsByWorkflowForUpdate(workflow);
        
        workflowDescriptions.forEach((workflowDescription) -> 
                deleteWorkflowDescription(workflowDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Steps
    // --------------------------------------------------------------------------------
    
    public WorkflowStep createWorkflowStep(Workflow workflow, String workflowStepName, WorkflowStepType workflowStepType,
            Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultWorkflowStep = getDefaultWorkflowStep(workflow);
        var defaultFound = defaultWorkflowStep != null;

        if(defaultFound && isDefault) {
            var defaultWorkflowStepDetailValue = getDefaultWorkflowStepDetailValueForUpdate(workflow);

            defaultWorkflowStepDetailValue.setIsDefault(false);
            updateWorkflowStepFromValue(defaultWorkflowStepDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var workflowStep = WorkflowStepFactory.getInstance().create();
        var workflowStepDetail = WorkflowStepDetailFactory.getInstance().create(workflowStep, workflow,
                workflowStepName, workflowStepType, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        workflowStep = WorkflowStepFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, workflowStep.getPrimaryKey());
        workflowStep.setActiveDetail(workflowStepDetail);
        workflowStep.setLastDetail(workflowStepDetail);
        workflowStep.store();
        
        sendEvent(workflow.getPrimaryKey(), EventTypes.MODIFY, workflowStep.getPrimaryKey(), null, createdBy);
        
        return workflowStep;
    }

    public long countWorkflowStepsByWorkflow(Workflow workflow) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM workflowsteps, workflowstepdetails " +
                "WHERE wkfls_activedetailid = wkflsdt_workflowstepdetailid " +
                "AND wkflsdt_wkfl_workflowid = ?",
                workflow);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.WorkflowStep */
    public WorkflowStep getWorkflowStepByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new WorkflowStepPK(entityInstance.getEntityUniqueId());

        return WorkflowStepFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public WorkflowStep getWorkflowStepByEntityInstance(EntityInstance entityInstance) {
        return getWorkflowStepByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public WorkflowStep getWorkflowStepByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getWorkflowStepByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getDefaultWorkflowStepQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM workflowsteps, workflowstepdetails " +
                "WHERE wkfls_activedetailid = wkflsdt_workflowstepdetailid AND wkflsdt_wkfl_workflowid = ? " +
                "AND wkflsdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM workflowsteps, workflowstepdetails " +
                "WHERE wkfls_activedetailid = wkflsdt_workflowstepdetailid AND wkflsdt_wkfl_workflowid = ? " +
                "AND wkflsdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultWorkflowStepQueries = Collections.unmodifiableMap(queryMap);
    }
    
    public WorkflowStep getDefaultWorkflowStep(Workflow workflow, EntityPermission entityPermission) {
        return WorkflowStepFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultWorkflowStepQueries,
                workflow);
    }
    
    public WorkflowStep getDefaultWorkflowStep(Workflow workflow) {
        return getDefaultWorkflowStep(workflow, EntityPermission.READ_ONLY);
    }
    
    public WorkflowStep getDefaultWorkflowStepForUpdate(Workflow workflow) {
        return getDefaultWorkflowStep(workflow, EntityPermission.READ_WRITE);
    }
    
    public WorkflowStepDetailValue getDefaultWorkflowStepDetailValueForUpdate(Workflow workflow) {
        return getDefaultWorkflowStepForUpdate(workflow).getLastDetailForUpdate().getWorkflowStepDetailValue().clone();
    }
    
    private static final Map<EntityPermission, String> getWorkflowStepsByWorkflowQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM workflowsteps, workflowstepdetails " +
                "WHERE wkfls_activedetailid = wkflsdt_workflowstepdetailid AND wkflsdt_wkfl_workflowid = ? " +
                "ORDER BY wkflsdt_sortorder, wkflsdt_workflowstepname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM workflowsteps, workflowstepdetails " +
                "WHERE wkfls_activedetailid = wkflsdt_workflowstepdetailid AND wkflsdt_wkfl_workflowid = ? " +
                "ORDER BY wkflsdt_sortorder, wkflsdt_workflowstepname " +
                "FOR UPDATE");
        getWorkflowStepsByWorkflowQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<WorkflowStep> getWorkflowStepsByWorkflow(Workflow workflow, EntityPermission entityPermission) {
        return WorkflowStepFactory.getInstance().getEntitiesFromQuery(entityPermission, getWorkflowStepsByWorkflowQueries,
                workflow);
    }
    
    public List<WorkflowStep> getWorkflowStepsByWorkflow(Workflow workflow) {
        return getWorkflowStepsByWorkflow(workflow, EntityPermission.READ_ONLY);
    }
    
    public List<WorkflowStep> getWorkflowStepsByWorkflowForUpdate(Workflow workflow) {
        return getWorkflowStepsByWorkflow(workflow, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getWorkflowStepByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM workflowsteps, workflowstepdetails " +
                "WHERE wkfls_activedetailid = wkflsdt_workflowstepdetailid AND wkflsdt_wkfl_workflowid = ? AND wkflsdt_workflowstepname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM workflowsteps, workflowstepdetails " +
                "WHERE wkfls_activedetailid = wkflsdt_workflowstepdetailid AND wkflsdt_wkfl_workflowid = ? AND wkflsdt_workflowstepname = ? " +
                "FOR UPDATE");
        getWorkflowStepByNameQueries = Collections.unmodifiableMap(queryMap);
    }
    
    public WorkflowStep getWorkflowStepByName(Workflow workflow, String workflowStepName, EntityPermission entityPermission) {
        return WorkflowStepFactory.getInstance().getEntityFromQuery(entityPermission, getWorkflowStepByNameQueries,
                workflow, workflowStepName);
    }
    
    public WorkflowStep getWorkflowStepByName(Workflow workflow, String workflowStepName) {
        return getWorkflowStepByName(workflow, workflowStepName, EntityPermission.READ_ONLY);
    }
    
    public WorkflowStep getWorkflowStepByNameForUpdate(Workflow workflow, String workflowStepName) {
        return getWorkflowStepByName(workflow, workflowStepName, EntityPermission.READ_WRITE);
    }
    
    public WorkflowStep getWorkflowStepUsingNames(String workflowName, String workflowStepName) {
        var workflow = getWorkflowByName(workflowName);
        
        return workflow == null? null: getWorkflowStepByName(workflow, workflowStepName);
    }
    
    public WorkflowStepDetailValue getWorkflowStepDetailValueForUpdate(WorkflowStep workflowStep) {
        return workflowStep == null? null: workflowStep.getLastDetailForUpdate().getWorkflowStepDetailValue().clone();
    }
    
    public WorkflowStepDetailValue getWorkflowStepDetailValueByNameForUpdate(Workflow workflow, String workflowStepName) {
        return getWorkflowStepDetailValueForUpdate(getWorkflowStepByNameForUpdate(workflow, workflowStepName));
    }
    
    public BaseWorkflowChoicesBean getWorkflowStepChoices(BaseWorkflowChoicesBean baseWorkflowChoicesBean,
            String defaultWorkflowStepChoice, Language language, boolean allowNullChoice, Workflow workflow) {
        var workflowSteps = getWorkflowStepsByWorkflow(workflow);
        var size = workflowSteps.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultWorkflowStepChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var workflowStep : workflowSteps) {
            var workflowStepDetail = workflowStep.getLastDetail();
            
            var label = getBestWorkflowStepDescription(workflowStep, language);
            var value = workflowStepDetail.getWorkflowStepName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultWorkflowStepChoice != null && defaultWorkflowStepChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && workflowStepDetail.getIsDefault()))
                defaultValue = value;
        }
        
        baseWorkflowChoicesBean.setLabels(labels);
        baseWorkflowChoicesBean.setValues(values);
        baseWorkflowChoicesBean.setDefaultValue(defaultValue);
        
        return baseWorkflowChoicesBean;
    }
    
    public WorkflowStepChoicesBean getWorkflowStepChoices(String defaultWorkflowStepChoice, Language language,
            boolean allowNullChoice, Workflow workflow) {
        var workflowStepChoicesBean = new WorkflowStepChoicesBean();
        
        getWorkflowStepChoices(workflowStepChoicesBean, defaultWorkflowStepChoice, language, allowNullChoice, workflow);
        
        return workflowStepChoicesBean;
    }
    
    public WorkflowStepTransfer getWorkflowStepTransfer(UserVisit userVisit, WorkflowStep workflowStep) {
        return getWorkflowTransferCaches().getWorkflowStepTransferCache().getWorkflowStepTransfer(userVisit, workflowStep);
    }
    
    public List<WorkflowStepTransfer> getWorkflowStepTransfers(UserVisit userVisit, Collection<WorkflowStep> workflowSteps) {
        List<WorkflowStepTransfer> workflowStepTransfers = new ArrayList<>(workflowSteps.size());
        var workflowStepTransferCache = getWorkflowTransferCaches(userVisit).getWorkflowStepTransferCache();
        
        workflowSteps.forEach((workflowStep) ->
                workflowStepTransfers.add(workflowStepTransferCache.getWorkflowStepTransfer(workflowStep))
        );
        
        return workflowStepTransfers;
    }
    
    public List<WorkflowStepTransfer> getWorkflowStepTransfersByWorkflow(UserVisit userVisit, Workflow workflow) {
        return getWorkflowStepTransfers(userVisit, getWorkflowStepsByWorkflow(workflow));
    }

    private void updateWorkflowStepFromValue(WorkflowStepDetailValue workflowStepDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(workflowStepDetailValue.hasBeenModified()) {
            var workflowStep = WorkflowStepFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    workflowStepDetailValue.getWorkflowStepPK());
            var workflowStepDetail = workflowStep.getActiveDetailForUpdate();
            
            workflowStepDetail.setThruTime(session.START_TIME_LONG);
            workflowStepDetail.store();

            var workflowStepPK = workflowStepDetail.getWorkflowStepPK();
            var workflow = workflowStepDetail.getWorkflow();
            var workflowPK = workflow.getPrimaryKey(); // Not updated
            var workflowStepName = workflowStepDetailValue.getWorkflowStepName();
            var workflowStepTypePK = workflowStepDetailValue.getWorkflowStepTypePK();
            var isDefault = workflowStepDetailValue.getIsDefault();
            var sortOrder = workflowStepDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultWorkflowStep = getDefaultWorkflowStep(workflow);
                var defaultFound = defaultWorkflowStep != null && !defaultWorkflowStep.equals(workflowStep);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultWorkflowStepDetailValue = getDefaultWorkflowStepDetailValueForUpdate(workflow);
                    
                    defaultWorkflowStepDetailValue.setIsDefault(false);
                    updateWorkflowStepFromValue(defaultWorkflowStepDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            workflowStepDetail = WorkflowStepDetailFactory.getInstance().create(workflowStepPK, workflowPK, workflowStepName, workflowStepTypePK,
                    isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            workflowStep.setActiveDetail(workflowStepDetail);
            workflowStep.setLastDetail(workflowStepDetail);
            
            sendEvent(workflowStep.getLastDetail().getWorkflowPK(), EventTypes.MODIFY, workflowStep.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void updateWorkflowStepFromValue(WorkflowStepDetailValue workflowStepDetailValue, BasePK updatedBy) {
        updateWorkflowStepFromValue(workflowStepDetailValue, true, updatedBy);
    }
    
    private void deleteWorkflowStep(WorkflowStep workflowStep, boolean checkDefault, BasePK deletedBy) {
        var workRequirementControl = Session.getModelController(WorkRequirementControl.class);
        
        workRequirementControl.deleteWorkRequirementTypesByWorkflowStep(workflowStep, deletedBy);
        deleteWorkflowEntityStatusesByWorkflowStep(workflowStep, deletedBy);
        deleteWorkflowEntranceStepsByWorkflowStep(workflowStep, deletedBy);
        deleteWorkflowDestinationStepsByWorkflowStep(workflowStep, deletedBy);
        deleteWorkflowStepDescriptionsByWorkflowStep(workflowStep, deletedBy);

        var workflowStepDetail = workflowStep.getLastDetailForUpdate();
        workflowStepDetail.setThruTime(session.START_TIME_LONG);
        workflowStep.setActiveDetail(null);
        workflowStep.store();
        
        if(checkDefault) {
            // Check for default, and pick one if necessary
            var workflow = workflowStepDetail.getWorkflow();
            var defaultWorkflowStep = getDefaultWorkflowStep(workflow);
            if(defaultWorkflowStep == null) {
                var workflowSteps = getWorkflowStepsByWorkflowForUpdate(workflow);

                if(!workflowSteps.isEmpty()) {
                    var iter = workflowSteps.iterator();
                    if(iter.hasNext()) {
                        defaultWorkflowStep = iter.next();
                    }
                    var workflowStepDetailValue = Objects.requireNonNull(defaultWorkflowStep).getLastDetailForUpdate().getWorkflowStepDetailValue().clone();

                    workflowStepDetailValue.setIsDefault(true);
                    updateWorkflowStepFromValue(workflowStepDetailValue, false, deletedBy);
                }
            }
        }
        
        sendEvent(workflowStepDetail.getWorkflowPK(), EventTypes.MODIFY, workflowStep.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteWorkflowStep(WorkflowStep workflowStep, BasePK deletedBy) {
        deleteWorkflowStep(workflowStep, true, deletedBy);
    }
    
    private void deleteWorkflowSteps(List<WorkflowStep> workflowSteps, boolean checkDefault, BasePK deletedBy) {
        workflowSteps.forEach((workflowStep) -> deleteWorkflowStep(workflowStep, checkDefault, deletedBy));
    }
    
    public void deleteWorkflowStepsByWorkflow(Workflow workflow, BasePK deletedBy) {
        deleteWorkflowSteps(getWorkflowStepsByWorkflowForUpdate(workflow), false, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Step Descriptions
    // --------------------------------------------------------------------------------
    
    public WorkflowStepDescription createWorkflowStepDescription(WorkflowStep workflowStep, Language language, String description,
            BasePK createdBy) {
        var workflowStepDescription = WorkflowStepDescriptionFactory.getInstance().create(workflowStep,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(workflowStep.getLastDetail().getWorkflowPK(), EventTypes.MODIFY, workflowStepDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return workflowStepDescription;
    }
    
    private WorkflowStepDescription getWorkflowStepDescription(WorkflowStep workflowStep, Language language, EntityPermission entityPermission) {
        WorkflowStepDescription workflowStepDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowstepdescriptions " +
                        "WHERE wkflsd_wkfls_workflowstepid = ? AND wkflsd_lang_languageid = ? AND wkflsd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowstepdescriptions " +
                        "WHERE wkflsd_wkfls_workflowstepid = ? AND wkflsd_lang_languageid = ? AND wkflsd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = WorkflowStepDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, workflowStep.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            workflowStepDescription = WorkflowStepDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return workflowStepDescription;
    }
    
    public WorkflowStepDescription getWorkflowStepDescription(WorkflowStep workflowStep, Language language) {
        return getWorkflowStepDescription(workflowStep, language, EntityPermission.READ_ONLY);
    }
    
    public WorkflowStepDescription getWorkflowStepDescriptionForUpdate(WorkflowStep workflowStep, Language language) {
        return getWorkflowStepDescription(workflowStep, language, EntityPermission.READ_WRITE);
    }
    
    public WorkflowStepDescriptionValue getWorkflowStepDescriptionValue(WorkflowStepDescription workflowStepDescription) {
        return workflowStepDescription == null? null: workflowStepDescription.getWorkflowStepDescriptionValue().clone();
    }
    
    public WorkflowStepDescriptionValue getWorkflowStepDescriptionValueForUpdate(WorkflowStep workflowStep, Language language) {
        return getWorkflowStepDescriptionValue(getWorkflowStepDescriptionForUpdate(workflowStep, language));
    }
    
    private List<WorkflowStepDescription> getWorkflowStepDescriptionsByWorkflowStep(WorkflowStep workflowStep, EntityPermission entityPermission) {
        List<WorkflowStepDescription> workflowStepDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowstepdescriptions, languages " +
                        "WHERE wkflsd_wkfls_workflowstepid = ? AND wkflsd_thrutime = ? AND wkflsd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowstepdescriptions " +
                        "WHERE wkflsd_wkfls_workflowstepid = ? AND wkflsd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = WorkflowStepDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, workflowStep.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            workflowStepDescriptions = WorkflowStepDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return workflowStepDescriptions;
    }
    
    public List<WorkflowStepDescription> getWorkflowStepDescriptionsByWorkflowStep(WorkflowStep workflowStep) {
        return getWorkflowStepDescriptionsByWorkflowStep(workflowStep, EntityPermission.READ_ONLY);
    }
    
    public List<WorkflowStepDescription> getWorkflowStepDescriptionsByWorkflowStepForUpdate(WorkflowStep workflowStep) {
        return getWorkflowStepDescriptionsByWorkflowStep(workflowStep, EntityPermission.READ_WRITE);
    }
    
    public String getBestWorkflowStepDescription(WorkflowStep workflowStep, Language language) {
        String description;
        var workflowStepDescription = getWorkflowStepDescription(workflowStep, language);
        
        if(workflowStepDescription == null && !language.getIsDefault()) {
            workflowStepDescription = getWorkflowStepDescription(workflowStep, partyControl.getDefaultLanguage());
        }
        
        if(workflowStepDescription == null) {
            description = workflowStep.getLastDetail().getWorkflowStepName();
        } else {
            description = workflowStepDescription.getDescription();
        }
        
        return description;
    }
    
    public WorkflowStepDescriptionTransfer getWorkflowStepDescriptionTransfer(UserVisit userVisit, WorkflowStepDescription workflowStepDescription) {
        return getWorkflowTransferCaches().getWorkflowStepDescriptionTransferCache().getWorkflowStepDescriptionTransfer(userVisit, workflowStepDescription);
    }
    
    public List<WorkflowStepDescriptionTransfer> getWorkflowStepDescriptionTransfersByWorkflowStep(UserVisit userVisit, WorkflowStep workflowStep) {
        var workflowStepDescriptions = getWorkflowStepDescriptionsByWorkflowStep(workflowStep);
        List<WorkflowStepDescriptionTransfer> workflowStepDescriptionTransfers = new ArrayList<>(workflowStepDescriptions.size());
        var workflowStepDescriptionTransferCache = getWorkflowTransferCaches(userVisit).getWorkflowStepDescriptionTransferCache();
        
        workflowStepDescriptions.forEach((workflowStepDescription) ->
                workflowStepDescriptionTransfers.add(workflowStepDescriptionTransferCache.getWorkflowStepDescriptionTransfer(workflowStepDescription))
        );
        
        return workflowStepDescriptionTransfers;
    }
    
    public void updateWorkflowStepDescriptionFromValue(WorkflowStepDescriptionValue workflowStepDescriptionValue, BasePK updatedBy) {
        if(workflowStepDescriptionValue.hasBeenModified()) {
            var workflowStepDescription = WorkflowStepDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, workflowStepDescriptionValue.getPrimaryKey());
            
            workflowStepDescription.setThruTime(session.START_TIME_LONG);
            workflowStepDescription.store();

            var workflowStep = workflowStepDescription.getWorkflowStep();
            var language = workflowStepDescription.getLanguage();
            var description = workflowStepDescriptionValue.getDescription();
            
            workflowStepDescription = WorkflowStepDescriptionFactory.getInstance().create(workflowStep, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(workflowStep.getLastDetail().getWorkflowPK(), EventTypes.MODIFY, workflowStepDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteWorkflowStepDescription(WorkflowStepDescription workflowStepDescription, BasePK deletedBy) {
        workflowStepDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(workflowStepDescription.getWorkflowStep().getLastDetail().getWorkflowPK(), EventTypes.MODIFY, workflowStepDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteWorkflowStepDescriptionsByWorkflowStep(WorkflowStep workflowStep, BasePK deletedBy) {
        var workflowStepDescriptions = getWorkflowStepDescriptionsByWorkflowStepForUpdate(workflowStep);
        
        workflowStepDescriptions.forEach((workflowStepDescription) -> 
                deleteWorkflowStepDescription(workflowStepDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Entity Types
    // --------------------------------------------------------------------------------
    
    public WorkflowEntityType createWorkflowEntityType(Workflow workflow, EntityType entityType, BasePK createdBy) {
        var workflowEntityType = WorkflowEntityTypeFactory.getInstance().create(workflow, entityType,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(workflow.getPrimaryKey(), EventTypes.MODIFY, workflowEntityType.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return workflowEntityType;
    }

    public long countWorkflowEntityTypesByWorkflow(Workflow workflow) {
        return session.queryForLong("""
                        SELECT COUNT(*)
                        FROM workflowentitytypes
                        WHERE wkflent_wkfl_workflowid = ? AND wkflent_thrutime = ?
                        """, workflow, Session.MAX_TIME);
    }

    public long countWorkflowEntityTypesByEntityType(EntityType entityType) {
        return session.queryForLong("""
                        SELECT COUNT(*)
                        FROM workflowentitytypes
                        WHERE wkflent_ent_entitytypeid = ? AND wkflent_thrutime = ?
                        """, entityType, Session.MAX_TIME);
    }

    public boolean workflowEntityTypeExists(final Workflow workflow, final EntityType entityType) {
        return session.queryForLong("""
                        SELECT COUNT(*)
                        FROM workflowentitytypes
                        WHERE wkflent_wkfl_workflowid = ? AND wkflent_ent_entitytypeid = ? AND wkflent_thrutime = ?
                        """, workflow, entityType, Session.MAX_TIME) == 1;
    }

    private static final Map<EntityPermission, String> getWorkflowEntityTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM workflowentitytypes " +
                "WHERE wkflent_wkfl_workflowid = ? AND wkflent_ent_entitytypeid = ? AND wkflent_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM workflowentitytypes " +
                "WHERE wkflent_wkfl_workflowid = ? AND wkflent_ent_entitytypeid = ? AND wkflent_thrutime = ? " +
                "FOR UPDATE");
        getWorkflowEntityTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private WorkflowEntityType getWorkflowEntityType(Workflow workflow, EntityType entityType, EntityPermission entityPermission) {
        return WorkflowEntityTypeFactory.getInstance().getEntityFromQuery(entityPermission, getWorkflowEntityTypeQueries,
                workflow, entityType, Session.MAX_TIME_LONG);
    }

    public WorkflowEntityType getWorkflowEntityType(Workflow workflow, EntityType entityType) {
        return getWorkflowEntityType(workflow, entityType, EntityPermission.READ_ONLY);
    }

    public WorkflowEntityType getWorkflowEntityTypeForUpdate(Workflow workflow, EntityType entityType) {
        return getWorkflowEntityType(workflow, entityType, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getWorkflowEntityTypesByWorkflowQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM workflowentitytypes, entitytypes, entitytypedetails " +
                "WHERE wkflent_wkfl_workflowid = ? AND wkflent_thrutime = ? " +
                "AND wkflent_ent_entitytypeid = ent_entitytypeid AND ent_activedetailid = entdt_entitytypedetailid " +
                "ORDER BY entdt_sortorder, entdt_entitytypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM workflowentitytypes " +
                "WHERE wkflent_wkfl_workflowid = ? AND wkflent_thrutime = ? " +
                "FOR UPDATE");
        getWorkflowEntityTypesByWorkflowQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<WorkflowEntityType> getWorkflowEntityTypesByWorkflow(Workflow workflow, EntityPermission entityPermission) {
        return WorkflowEntityTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getWorkflowEntityTypesByWorkflowQueries,
                workflow, Session.MAX_TIME_LONG);
    }
    
    public List<WorkflowEntityType> getWorkflowEntityTypesByWorkflow(Workflow workflow) {
        return getWorkflowEntityTypesByWorkflow(workflow, EntityPermission.READ_ONLY);
    }
    
    public List<WorkflowEntityType> getWorkflowEntityTypesByWorkflowForUpdate(Workflow workflow) {
        return getWorkflowEntityTypesByWorkflow(workflow, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getWorkflowEntityTypesByEntityTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM workflowentitytypes, workflows, workflowdetails " +
                "WHERE wkflent_ent_entitytypeid = ? AND wkflent_thrutime = ? " +
                "AND wkflent_wkfl_workflowid = wkfl_workflowid AND wkfl_lastdetailid = wkfldt_workflowdetailid " +
                "ORDER BY wkfldt_sortorder, wkfldt_workflowname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM workflowentitytypes " +
                "WHERE wkflent_wkfl_workflowid = ? AND wkflent_thrutime = ? " +
                "FOR UPDATE");
        getWorkflowEntityTypesByEntityTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<WorkflowEntityType> getWorkflowEntityTypesByEntityType(EntityType entityType, EntityPermission entityPermission) {
        return WorkflowEntityTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getWorkflowEntityTypesByEntityTypeQueries,
                entityType, Session.MAX_TIME_LONG);
    }

    public List<WorkflowEntityType> getWorkflowEntityTypesByEntityType(EntityType entityType) {
        return getWorkflowEntityTypesByEntityType(entityType, EntityPermission.READ_ONLY);
    }

    public List<WorkflowEntityType> getWorkflowEntityTypesByEntityTypeForUpdate(EntityType entityType) {
        return getWorkflowEntityTypesByEntityType(entityType, EntityPermission.READ_WRITE);
    }

    public WorkflowEntityTypeTransfer getWorkflowEntityTypeTransfer(UserVisit userVisit, WorkflowEntityType workflowEntityType) {
        return getWorkflowTransferCaches().getWorkflowEntityTypeTransferCache().getWorkflowEntityTypeTransfer(userVisit, workflowEntityType);
    }
    
    public List<WorkflowEntityTypeTransfer> getWorkflowEntityTypeTransfers(UserVisit userVisit, Collection<WorkflowEntityType> workflowEntityTypes) {
        List<WorkflowEntityTypeTransfer> workflowEntityTypeTransfers = new ArrayList<>(workflowEntityTypes.size());
        var workflowEntityTypeTransferCache = getWorkflowTransferCaches(userVisit).getWorkflowEntityTypeTransferCache();

        workflowEntityTypes.forEach((workflowEntityType) ->
                workflowEntityTypeTransfers.add(workflowEntityTypeTransferCache.getWorkflowEntityTypeTransfer(workflowEntityType))
        );

        return workflowEntityTypeTransfers;
    }

    public List<WorkflowEntityTypeTransfer> getWorkflowEntityTypeTransfersByWorkflow(UserVisit userVisit, Workflow workflow) {
        return getWorkflowEntityTypeTransfers(userVisit, getWorkflowEntityTypesByWorkflow(workflow));
    }

    public List<WorkflowEntityTypeTransfer> getWorkflowEntityTypeTransfersByEntityType(UserVisit userVisit, EntityType entityTYpe) {
        return getWorkflowEntityTypeTransfers(userVisit, getWorkflowEntityTypesByEntityType(entityTYpe));
    }

    public void deleteWorkflowEntityType(WorkflowEntityType workflowEntityType, BasePK deletedBy) {
        workflowEntityType.setThruTime(session.START_TIME_LONG);
        
        sendEvent(workflowEntityType.getWorkflowPK(), EventTypes.MODIFY, workflowEntityType.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteWorkflowEntityTypes(List<WorkflowEntityType> workflowEntityTypes, BasePK deletedBy) {
        workflowEntityTypes.forEach((workflowEntityType) -> 
                deleteWorkflowEntityType(workflowEntityType, deletedBy)
        );
    }
    
    public void deleteWorkflowEntityTypesByWorkflow(Workflow workflow, BasePK deletedBy) {
        deleteWorkflowEntityTypes(getWorkflowEntityTypesByWorkflowForUpdate(workflow),  deletedBy);
    }

    public void deleteWorkflowEntityTypesByEntityType(EntityType entityType, BasePK deletedBy) {
        deleteWorkflowEntityTypes(getWorkflowEntityTypesByEntityTypeForUpdate(entityType),  deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Workflow Entrances
    // --------------------------------------------------------------------------------
    
    public WorkflowEntrance createWorkflowEntrance(Workflow workflow, String workflowEntranceName, Boolean isDefault,
            Integer sortOrder, BasePK createdBy) {
        var defaultWorkflowEntrance = getDefaultWorkflowEntrance(workflow);
        var defaultFound = defaultWorkflowEntrance != null;

        if(defaultFound && isDefault) {
            var defaultWorkflowEntranceDetailValue = getDefaultWorkflowEntranceDetailValueForUpdate(workflow);

            defaultWorkflowEntranceDetailValue.setIsDefault(false);
            updateWorkflowEntranceFromValue(defaultWorkflowEntranceDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var workflowEntrance = WorkflowEntranceFactory.getInstance().create();
        var workflowEntranceDetail = WorkflowEntranceDetailFactory.getInstance().create(workflowEntrance,
                workflow, workflowEntranceName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        workflowEntrance = WorkflowEntranceFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                workflowEntrance.getPrimaryKey());
        workflowEntrance.setActiveDetail(workflowEntranceDetail);
        workflowEntrance.setLastDetail(workflowEntranceDetail);
        workflowEntrance.store();
        
        sendEvent(workflow.getPrimaryKey(), EventTypes.MODIFY, workflowEntrance.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return workflowEntrance;
    }

    public long countWorkflowEntrancesByWorkflow(Workflow workflow) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM workflowentrances, workflowentrancedetails " +
                "WHERE wkflen_activedetailid = wkflendt_workflowentrancedetailid " +
                "AND wkflendt_wkfl_workflowid = ?",
                workflow);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.WorkflowEntrance */
    public WorkflowEntrance getWorkflowEntranceByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new WorkflowEntrancePK(entityInstance.getEntityUniqueId());

        return WorkflowEntranceFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public WorkflowEntrance getWorkflowEntranceByEntityInstance(EntityInstance entityInstance) {
        return getWorkflowEntranceByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public WorkflowEntrance getWorkflowEntranceByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getWorkflowEntranceByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getDefaultWorkflowEntranceQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM workflowentrances, workflowentrancedetails " +
                "WHERE wkflen_activedetailid = wkflendt_workflowentrancedetailid AND wkflendt_wkfl_workflowid = ? " +
                "AND wkflendt_isdefault = 1 " +
                "FOR UPDATE");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM workflowentrances, workflowentrancedetails " +
                "WHERE wkflen_activedetailid = wkflendt_workflowentrancedetailid AND wkflendt_wkfl_workflowid = ? " +
                "AND wkflendt_isdefault = 1");
        getDefaultWorkflowEntranceQueries = Collections.unmodifiableMap(queryMap);
    }
    
    public WorkflowEntrance getDefaultWorkflowEntrance(Workflow workflow, EntityPermission entityPermission) {
        return WorkflowEntranceFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultWorkflowEntranceQueries,
                workflow);
    }
    
    public WorkflowEntrance getDefaultWorkflowEntrance(Workflow workflow) {
        return getDefaultWorkflowEntrance(workflow, EntityPermission.READ_ONLY);
    }
    
    public WorkflowEntrance getDefaultWorkflowEntranceForUpdate(Workflow workflow) {
        return getDefaultWorkflowEntrance(workflow, EntityPermission.READ_WRITE);
    }
    
    public WorkflowEntranceDetailValue getDefaultWorkflowEntranceDetailValueForUpdate(Workflow workflow) {
        return getDefaultWorkflowEntranceForUpdate(workflow).getLastDetailForUpdate().getWorkflowEntranceDetailValue().clone();
    }
    
    private static final Map<EntityPermission, String> getWorkflowEntrancesByWorkflowQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM workflowentrances, workflowentrancedetails " +
                "WHERE wkflen_activedetailid = wkflendt_workflowentrancedetailid AND wkflendt_wkfl_workflowid = ? " +
                "ORDER BY wkflendt_sortorder, wkflendt_workflowentrancename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM workflowentrances, workflowentrancedetails " +
                "WHERE wkflen_activedetailid = wkflendt_workflowentrancedetailid AND wkflendt_wkfl_workflowid = ? " +
                "FOR UPDATE");
        getWorkflowEntrancesByWorkflowQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<WorkflowEntrance> getWorkflowEntrancesByWorkflow(Workflow workflow, EntityPermission entityPermission) {
        return WorkflowEntranceFactory.getInstance().getEntitiesFromQuery(entityPermission, getWorkflowEntrancesByWorkflowQueries,
                workflow);
    }
    
    public List<WorkflowEntrance> getWorkflowEntrancesByWorkflow(Workflow workflow) {
        return getWorkflowEntrancesByWorkflow(workflow, EntityPermission.READ_ONLY);
    }
    
    public List<WorkflowEntrance> getWorkflowEntrancesByWorkflowForUpdate(Workflow workflow) {
        return getWorkflowEntrancesByWorkflow(workflow, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getWorkflowEntranceByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM workflowentrances, workflowentrancedetails " +
                "WHERE wkflen_activedetailid = wkflendt_workflowentrancedetailid AND wkflendt_wkfl_workflowid = ? " +
                "AND wkflendt_workflowentrancename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM workflowentrances, workflowentrancedetails " +
                "WHERE wkflen_activedetailid = wkflendt_workflowentrancedetailid AND wkflendt_wkfl_workflowid = ? " +
                "AND wkflendt_workflowentrancename = ? " +
                "FOR UPDATE");
        getWorkflowEntranceByNameQueries = Collections.unmodifiableMap(queryMap);
    }
    
    public WorkflowEntrance getWorkflowEntranceByName(Workflow workflow, String workflowEntranceName, EntityPermission entityPermission) {
        return WorkflowEntranceFactory.getInstance().getEntityFromQuery(entityPermission, getWorkflowEntranceByNameQueries,
                workflow, workflowEntranceName);
    }
    
    public WorkflowEntrance getWorkflowEntranceByName(Workflow workflow, String workflowEntranceName) {
        return getWorkflowEntranceByName(workflow, workflowEntranceName, EntityPermission.READ_ONLY);
    }
    
    public WorkflowEntrance getWorkflowEntranceByNameForUpdate(Workflow workflow, String workflowEntranceName) {
        return getWorkflowEntranceByName(workflow, workflowEntranceName, EntityPermission.READ_WRITE);
    }
    
    public WorkflowEntranceDetailValue getWorkflowEntranceDetailValueForUpdate(WorkflowEntrance workflowEntrance) {
        return workflowEntrance == null? null: workflowEntrance.getLastDetailForUpdate().getWorkflowEntranceDetailValue().clone();
    }
    
    public WorkflowEntranceDetailValue getWorkflowEntranceDetailValueByNameForUpdate(Workflow workflow, String workflowEntranceName) {
        return getWorkflowEntranceDetailValueForUpdate(getWorkflowEntranceByNameForUpdate(workflow, workflowEntranceName));
    }
    
    public WorkflowEntrance getWorkflowEntranceUsingNames(final ExecutionErrorAccumulator eea, final String workflowName, final String workflowEntranceName) {
        var workflow = getWorkflowByName(workflowName);
        WorkflowEntrance workflowEntrance = null;

        if(workflow != null) {
            if(workflowEntranceName == null) {
                workflowEntrance = getDefaultWorkflowEntrance(workflow);

                if(workflowEntrance == null) {
                    if(eea == null) {
                        throw new InvalidParameterException("Missing Default WorkflowEntrance, workflowName = " + workflowName);
                    } else {
                        eea.addExecutionError(ExecutionErrors.MissingDefaultWorkflowEntrance.name(), workflowName);
                    }
                }
            } else {
                workflowEntrance = getWorkflowEntranceByName(workflow, workflowEntranceName);

                if(workflowEntrance == null) {
                    if(eea == null) {
                        throw new InvalidParameterException("Unknown WorkflowEntranceName, workflowName = " + workflowName + ", workflowEntranceName = " + workflowEntranceName);
                    } else {
                        eea.addExecutionError(ExecutionErrors.UnknownWorkflowEntranceName.name(), workflowName, workflowEntranceName);
                    }
                }
            }
        } else {
            if(eea == null) {
                throw new InvalidParameterException("Unknown WorkflowName, workflowName = " + workflowName);
            } else {
                eea.addExecutionError(ExecutionErrors.UnknownWorkflowName.name(), workflowName);
            }
        }

        return workflowEntrance;
    }
    
    public BaseWorkflowChoicesBean getWorkflowEntranceChoices(BaseWorkflowChoicesBean baseWorkflowChoicesBean, String defaultWorkflowEntranceChoice, Language language, boolean allowNullChoice,
            Workflow workflow, PartyPK partyPK) {
        var workflowSecurityLogic = WorkflowSecurityLogic.getInstance();
        var workflowEntrances = getWorkflowEntrancesByWorkflow(workflow);
        var size = workflowEntrances.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultWorkflowEntranceChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var workflowEntrance : workflowEntrances) {
            if(workflowSecurityLogic.checkWorkflowEntranceAvailable(workflowEntrance, partyPK)) {
                var workflowEntranceDetail = workflowEntrance.getLastDetail();

                var label = getBestWorkflowEntranceDescription(workflowEntrance, language);
                var value = workflowEntranceDetail.getWorkflowEntranceName();

                labels.add(label == null? value: label);
                values.add(value);

                var usingDefaultChoice = Objects.equals(defaultWorkflowEntranceChoice, value);
                if(usingDefaultChoice || (defaultValue == null && workflowEntranceDetail.getIsDefault())) {
                    defaultValue = value;
                }
            }
        }
        
        if(defaultValue == null) {
            // defaultValue may not have been set if the Party didn't have access to the default choice. We'll just use
            // the first one from the value List. If there are no values, then we'll leave it null.
            if(values.size() > 0) {
                defaultValue = values.get(1);
            }
        }
        
        baseWorkflowChoicesBean.setLabels(labels);
        baseWorkflowChoicesBean.setValues(values);
        baseWorkflowChoicesBean.setDefaultValue(defaultValue);
        
        return baseWorkflowChoicesBean;
    }
    
    public WorkflowEntranceTransfer getWorkflowEntranceTransfer(UserVisit userVisit, WorkflowEntrance workflowEntrance) {
        return getWorkflowTransferCaches().getWorkflowEntranceTransferCache().getWorkflowEntranceTransfer(userVisit, workflowEntrance);
    }
    
    public List<WorkflowEntranceTransfer> getWorkflowEntranceTransfers(UserVisit userVisit, Collection<WorkflowEntrance> workflowEntrances) {
        List<WorkflowEntranceTransfer> workflowEntranceTransfers = new ArrayList<>(workflowEntrances.size());
        var workflowEntranceTransferCache = getWorkflowTransferCaches(userVisit).getWorkflowEntranceTransferCache();
        
        workflowEntrances.forEach((workflowEntrance) ->
                workflowEntranceTransfers.add(workflowEntranceTransferCache.getWorkflowEntranceTransfer(workflowEntrance))
        );
        
        return workflowEntranceTransfers;
    }
    
    public List<WorkflowEntranceTransfer> getWorkflowEntranceTransfersByWorkflow(UserVisit userVisit, Workflow workflow) {
        return getWorkflowEntranceTransfers(userVisit, getWorkflowEntrancesByWorkflow(workflow));
    }

    private void updateWorkflowEntranceFromValue(WorkflowEntranceDetailValue workflowEntranceDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(workflowEntranceDetailValue.hasBeenModified()) {
            var workflowEntrance = WorkflowEntranceFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    workflowEntranceDetailValue.getWorkflowEntrancePK());
            var workflowEntranceDetail = workflowEntrance.getActiveDetailForUpdate();
            
            workflowEntranceDetail.setThruTime(session.START_TIME_LONG);
            workflowEntranceDetail.store();

            var workflowEntrancePK = workflowEntranceDetail.getWorkflowEntrancePK();
            var workflow = workflowEntranceDetail.getWorkflow();
            var workflowPK = workflow.getPrimaryKey(); // Not updated
            var workflowEntranceName = workflowEntranceDetailValue.getWorkflowEntranceName();
            var isDefault = workflowEntranceDetailValue.getIsDefault();
            var sortOrder = workflowEntranceDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultWorkflowEntrance = getDefaultWorkflowEntrance(workflow);
                var defaultFound = defaultWorkflowEntrance != null && !defaultWorkflowEntrance.equals(workflowEntrance);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultWorkflowEntranceDetailValue = getDefaultWorkflowEntranceDetailValueForUpdate(workflow);
                    
                    defaultWorkflowEntranceDetailValue.setIsDefault(false);
                    updateWorkflowEntranceFromValue(defaultWorkflowEntranceDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            workflowEntranceDetail = WorkflowEntranceDetailFactory.getInstance().create(workflowEntrancePK, workflowPK, workflowEntranceName,
                    isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            workflowEntrance.setActiveDetail(workflowEntranceDetail);
            workflowEntrance.setLastDetail(workflowEntranceDetail);
            
            sendEvent(workflow.getPrimaryKey(), EventTypes.MODIFY, workflowEntrance.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void updateWorkflowEntranceFromValue(WorkflowEntranceDetailValue workflowEntranceDetailValue, BasePK updatedBy) {
        updateWorkflowEntranceFromValue(workflowEntranceDetailValue, true, updatedBy);
    }
    
    private void deleteWorkflowEntrance(WorkflowEntrance workflowEntrance, boolean checkDefault, BasePK deletedBy) {
        deleteWorkflowEntranceDescriptionsByWorkflowEntrance(workflowEntrance, deletedBy);
        deleteWorkflowEntranceStepsByWorkflowEntrance(workflowEntrance, deletedBy);
        deleteWorkflowEntrancePartyTypesByWorkflowEntrance(workflowEntrance, deletedBy);
        deleteWorkflowEntranceSelectorsByWorkflowEntrance(workflowEntrance, deletedBy);

        var workflowEntranceDetail = workflowEntrance.getLastDetailForUpdate();
        workflowEntranceDetail.setThruTime(session.START_TIME_LONG);
        workflowEntrance.setActiveDetail(null);
        workflowEntrance.store();

        var workflow = workflowEntranceDetail.getWorkflow();
            
        if(checkDefault) {
            // Check for default, and pick one if necessary
            var defaultWorkflowEntrance = getDefaultWorkflowEntrance(workflow);
            if(defaultWorkflowEntrance == null) {
                var workflowEntrances = getWorkflowEntrancesByWorkflowForUpdate(workflow);

                if(!workflowEntrances.isEmpty()) {
                    var iter = workflowEntrances.iterator();
                    if(iter.hasNext()) {
                        defaultWorkflowEntrance = iter.next();
                    }
                    var workflowEntranceDetailValue = Objects.requireNonNull(defaultWorkflowEntrance).getLastDetailForUpdate().getWorkflowEntranceDetailValue().clone();

                    workflowEntranceDetailValue.setIsDefault(true);
                    updateWorkflowEntranceFromValue(workflowEntranceDetailValue, false, deletedBy);
                }
            }
        }
        
        sendEvent(workflow.getPrimaryKey(), EventTypes.MODIFY, workflowEntrance.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteWorkflowEntrance(WorkflowEntrance workflowEntrance, BasePK deletedBy) {
        deleteWorkflowEntrance(workflowEntrance, true, deletedBy);
    }
    
    private void deleteWorkflowEntrances(List<WorkflowEntrance> workflowEntrances, boolean checkDefault, BasePK deletedBy) {
        workflowEntrances.forEach((workflowEntrance) -> deleteWorkflowEntrance(workflowEntrance, checkDefault, deletedBy));
    }
    
    public void deleteWorkflowEntrancesByWorkflow(Workflow workflow, BasePK deletedBy) {
        deleteWorkflowEntrances(getWorkflowEntrancesByWorkflowForUpdate(workflow), false, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Entrance Descriptions
    // --------------------------------------------------------------------------------
    
    public WorkflowEntranceDescription createWorkflowEntranceDescription(WorkflowEntrance workflowEntrance, Language language,
            String description, BasePK createdBy) {
        var workflowEntranceDescription = WorkflowEntranceDescriptionFactory.getInstance().create(session,
                workflowEntrance, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(workflowEntrance.getLastDetail().getWorkflowPK(), EventTypes.MODIFY, workflowEntranceDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return workflowEntranceDescription;
    }
    
    private WorkflowEntranceDescription getWorkflowEntranceDescription(WorkflowEntrance workflowEntrance, Language language, EntityPermission entityPermission) {
        WorkflowEntranceDescription workflowEntranceDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowentrancedescriptions " +
                        "WHERE wkflend_wkflen_workflowentranceid = ? AND wkflend_lang_languageid = ? AND wkflend_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowentrancedescriptions " +
                        "WHERE wkflend_wkflen_workflowentranceid = ? AND wkflend_lang_languageid = ? AND wkflend_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = WorkflowEntranceDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, workflowEntrance.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            workflowEntranceDescription = WorkflowEntranceDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return workflowEntranceDescription;
    }
    
    public WorkflowEntranceDescription getWorkflowEntranceDescription(WorkflowEntrance workflowEntrance, Language language) {
        return getWorkflowEntranceDescription(workflowEntrance, language, EntityPermission.READ_ONLY);
    }
    
    public WorkflowEntranceDescription getWorkflowEntranceDescriptionForUpdate(WorkflowEntrance workflowEntrance, Language language) {
        return getWorkflowEntranceDescription(workflowEntrance, language, EntityPermission.READ_WRITE);
    }
    
    public WorkflowEntranceDescriptionValue getWorkflowEntranceDescriptionValue(WorkflowEntranceDescription workflowEntranceDescription) {
        return workflowEntranceDescription == null? null: workflowEntranceDescription.getWorkflowEntranceDescriptionValue().clone();
    }
    
    public WorkflowEntranceDescriptionValue getWorkflowEntranceDescriptionValueForUpdate(WorkflowEntrance workflowEntrance, Language language) {
        return getWorkflowEntranceDescriptionValue(getWorkflowEntranceDescriptionForUpdate(workflowEntrance, language));
    }
    
    private List<WorkflowEntranceDescription> getWorkflowEntranceDescriptionsByWorkflowEntrance(WorkflowEntrance workflowEntrance, EntityPermission entityPermission) {
        List<WorkflowEntranceDescription> workflowEntranceDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowentrancedescriptions, languages " +
                        "WHERE wkflend_wkflen_workflowentranceid = ? AND wkflend_thrutime = ? AND wkflend_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowentrancedescriptions " +
                        "WHERE wkflend_wkflen_workflowentranceid = ? AND wkflend_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = WorkflowEntranceDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, workflowEntrance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            workflowEntranceDescriptions = WorkflowEntranceDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return workflowEntranceDescriptions;
    }
    
    public List<WorkflowEntranceDescription> getWorkflowEntranceDescriptionsByWorkflowEntrance(WorkflowEntrance workflowEntrance) {
        return getWorkflowEntranceDescriptionsByWorkflowEntrance(workflowEntrance, EntityPermission.READ_ONLY);
    }
    
    public List<WorkflowEntranceDescription> getWorkflowEntranceDescriptionsByWorkflowEntranceForUpdate(WorkflowEntrance workflowEntrance) {
        return getWorkflowEntranceDescriptionsByWorkflowEntrance(workflowEntrance, EntityPermission.READ_WRITE);
    }
    
    public String getBestWorkflowEntranceDescription(WorkflowEntrance workflowEntrance, Language language) {
        String description;
        var workflowEntranceDescription = getWorkflowEntranceDescription(workflowEntrance, language);
        
        if(workflowEntranceDescription == null && !language.getIsDefault()) {
            workflowEntranceDescription = getWorkflowEntranceDescription(workflowEntrance, partyControl.getDefaultLanguage());
        }
        
        if(workflowEntranceDescription == null) {
            description = workflowEntrance.getLastDetail().getWorkflowEntranceName();
        } else {
            description = workflowEntranceDescription.getDescription();
        }
        
        return description;
    }
    
    public WorkflowEntranceDescriptionTransfer getWorkflowEntranceDescriptionTransfer(UserVisit userVisit, WorkflowEntranceDescription workflowEntranceDescription) {
        return getWorkflowTransferCaches().getWorkflowEntranceDescriptionTransferCache().getWorkflowEntranceDescriptionTransfer(userVisit, workflowEntranceDescription);
    }
    
    public List<WorkflowEntranceDescriptionTransfer> getWorkflowEntranceDescriptionTransfersByWorkflowEntrance(UserVisit userVisit, WorkflowEntrance workflowEntrance) {
        var workflowEntranceDescriptions = getWorkflowEntranceDescriptionsByWorkflowEntrance(workflowEntrance);
        List<WorkflowEntranceDescriptionTransfer> workflowEntranceDescriptionTransfers = new ArrayList<>(workflowEntranceDescriptions.size());
        var workflowEntranceDescriptionTransferCache = getWorkflowTransferCaches(userVisit).getWorkflowEntranceDescriptionTransferCache();
        
        workflowEntranceDescriptions.forEach((workflowEntranceDescription) ->
                workflowEntranceDescriptionTransfers.add(workflowEntranceDescriptionTransferCache.getWorkflowEntranceDescriptionTransfer(workflowEntranceDescription))
        );
        
        return workflowEntranceDescriptionTransfers;
    }
    
    public void updateWorkflowEntranceDescriptionFromValue(WorkflowEntranceDescriptionValue workflowEntranceDescriptionValue, BasePK updatedBy) {
        if(workflowEntranceDescriptionValue.hasBeenModified()) {
            var workflowEntranceDescription = WorkflowEntranceDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, workflowEntranceDescriptionValue.getPrimaryKey());
            
            workflowEntranceDescription.setThruTime(session.START_TIME_LONG);
            workflowEntranceDescription.store();

            var workflowEntrance = workflowEntranceDescription.getWorkflowEntrance();
            var language = workflowEntranceDescription.getLanguage();
            var description = workflowEntranceDescriptionValue.getDescription();
            
            workflowEntranceDescription = WorkflowEntranceDescriptionFactory.getInstance().create(workflowEntrance, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(workflowEntrance.getLastDetail().getWorkflowPK(), EventTypes.MODIFY, workflowEntranceDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteWorkflowEntranceDescription(WorkflowEntranceDescription workflowEntranceDescription, BasePK deletedBy) {
        workflowEntranceDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(workflowEntranceDescription.getWorkflowEntrance().getLastDetail().getWorkflowPK(), EventTypes.MODIFY, workflowEntranceDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteWorkflowEntranceDescriptionsByWorkflowEntrance(WorkflowEntrance workflowEntrance, BasePK deletedBy) {
        var workflowEntranceDescriptions = getWorkflowEntranceDescriptionsByWorkflowEntranceForUpdate(workflowEntrance);
        
        workflowEntranceDescriptions.forEach((workflowEntranceDescription) -> 
                deleteWorkflowEntranceDescription(workflowEntranceDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Entrance Selectors
    // --------------------------------------------------------------------------------
    
    public WorkflowEntranceSelector createWorkflowEntranceSelector(WorkflowEntrance workflowEntrance, Selector selector,
            BasePK createdBy) {
        var workflowEntranceSelector = WorkflowEntranceSelectorFactory.getInstance().create(workflowEntrance,
                selector, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(workflowEntrance.getLastDetail().getWorkflowPK(), EventTypes.MODIFY, workflowEntranceSelector.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return workflowEntranceSelector;
    }

    public long countWorkflowEntranceSelectorsByWorkflowEntrance(WorkflowEntrance workflowEntrance) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM workflowentranceselectors " +
                "WHERE wkflensl_wkflen_workflowentranceid = ? AND wkflensl_thrutime = ?",
                workflowEntrance, Session.MAX_TIME_LONG);
    }

    public long countWorkflowEntranceSelectorsBySelector(Selector selector) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM workflowentranceselectors " +
                "WHERE wkflensl_sl_selectorid = ? AND wkflensl_thrutime = ?",
                selector, Session.MAX_TIME_LONG);
    }

    private List<WorkflowEntranceSelector> getWorkflowEntranceSelectorsBySelectorForUpdate(Selector selector, EntityPermission entityPermission) {
        List<WorkflowEntranceSelector> workflowEntranceSelectors;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowentranceselectors, workflowentrances, workflowentrancedetails, workflows, workflowdetails " +
                        "WHERE wkflensl_wkflen_workflowentranceid = ? AND wkflensl_thrutime = ? " +
                        "AND wkflensl_wkflen_workflowentranceid = wkflen_workflowentranceid AND wkflen_lastdetailid = wkflendt_workflowentrancedetailid " +
                        "AND wkflendt_wkfl_workflowid = wkfl_workflowid AND wkfl_lastdetailid = wkfldt_workflowdetailid " +
                        "ORDER BY wkflendt_sortorder, wkflendt_workflowentrancename, wkfldt_sortorder, wkfldt_workflowname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowentranceselectors " +
                        "WHERE wkflensl_sl_selectorid = ? AND wkflensl_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = WorkflowEntranceSelectorFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, selector.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            workflowEntranceSelectors = WorkflowEntranceSelectorFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return workflowEntranceSelectors;
    }
    
    public List<WorkflowEntranceSelector> getWorkflowEntranceSelectorsBySelector(Selector selector) {
        return getWorkflowEntranceSelectorsBySelectorForUpdate(selector, EntityPermission.READ_ONLY);
    }
    
    public List<WorkflowEntranceSelector> getWorkflowEntranceSelectorsBySelectorForUpdate(Selector selector) {
        return getWorkflowEntranceSelectorsBySelectorForUpdate(selector, EntityPermission.READ_WRITE);
    }
    
    private List<WorkflowEntranceSelector> getWorkflowEntranceSelectorsByWorkflowEntrance(WorkflowEntrance workflowEntrance,
            EntityPermission entityPermission) {
        List<WorkflowEntranceSelector> workflowEntranceSelectors;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowentranceselectors, selectors, selectordetails, selectortypes, selectortypedetails, selectorkinds, selectorkinddetails " +
                        "WHERE wkflensl_wkflen_workflowentranceid = ? AND wkflensl_thrutime = ? " +
                        "AND wkflensl_sl_selectorid = sl_selectorid AND sl_lastdetailid = sldt_selectordetailid " +
                        "AND sldt_slt_selectortypeid = slt_selectortypeid " +
                        "AND slt_lastdetailid = sltdt_selectortypedetailid AND sltdt_slk_selectorkindid = slk_selectorkindid " +
                        "AND slk_lastdetailid = slkdt_selectorkinddetailid " +
                        "ORDER BY sldt_sortorder, sldt_selectorname, sltdt_sortorder, sltdt_selectortypename, slkdt_sortorder, slkdt_selectorkindname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowentranceselectors " +
                        "WHERE wkflensl_wkflen_workflowentranceid = ? AND wkflensl_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = WorkflowEntranceSelectorFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, workflowEntrance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            workflowEntranceSelectors = WorkflowEntranceSelectorFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return workflowEntranceSelectors;
    }
    
    public List<WorkflowEntranceSelector> getWorkflowEntranceSelectorsByWorkflowEntrance(WorkflowEntrance workflowEntrance) {
        return getWorkflowEntranceSelectorsByWorkflowEntrance(workflowEntrance, EntityPermission.READ_ONLY);
    }
    
    public List<WorkflowEntranceSelector> getWorkflowEntranceSelectorsByWorkflowEntranceForUpdate(WorkflowEntrance workflowEntrance) {
        return getWorkflowEntranceSelectorsByWorkflowEntrance(workflowEntrance, EntityPermission.READ_WRITE);
    }
    
    private List<WorkflowEntranceSelector> getWorkflowEntranceSelectorsByWorkflow(Workflow workflow, EntityPermission entityPermission) {
        List<WorkflowEntranceSelector> workflowEntranceSelectors;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowentrances, workflowentrancedetails, workflowentranceselectors, selectors, selectordetails, selectortypes, selectortypedetails, selectorkinds, selectorkinddetails " +
                        "WHERE wkflen_activedetailid = wkflendt_workflowentrancedetailid AND wkflendt_wkfl_workflowid = ? " +
                        "AND wkflen_workflowentranceid = wkflensl_wkflen_workflowentranceid AND wkflensl_thrutime = ? " +
                        "AND wkflensl_sl_selectorid = sl_selectorid AND sl_lastdetailid = sldt_selectordetailid " +
                        "AND sldt_slt_selectortypeid = slt_selectortypeid AND slt_lastdetailid = sltdt_selectortypedetailid AND sltdt_slk_selectorkindid = slk_selectorkindid AND slk_lastdetailid = slkdt_selectorkinddetailid " +
                        "ORDER BY wkflendt_sortorder, wkflendt_workflowentrancename, sldt_sortorder, sldt_selectorname, " +
                        "sltdt_sortorder, sltdt_selectortypename, slkdt_sortorder, slkdt_selectorkindname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowentrances, workflowentrancedetails, workflowentranceselectors " +
                        "WHERE wkflen_activedetailid = wkflendt_workflowentrancedetailid AND wkflendt_wkfl_workflowid = ? " +
                        "AND wkflen_workflowentranceid = wkflensl_wkflen_workflowentranceid AND wkflensl_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = WorkflowEntranceSelectorFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, workflow.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            workflowEntranceSelectors = WorkflowEntranceSelectorFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return workflowEntranceSelectors;
    }
    
    public List<WorkflowEntranceSelector> getWorkflowEntranceSelectorsByWorkflow(Workflow workflow) {
        return getWorkflowEntranceSelectorsByWorkflow(workflow, EntityPermission.READ_ONLY);
    }
    
    public List<WorkflowEntranceSelector> getWorkflowEntranceSelectorsByWorkflowForUpdate(Workflow workflow) {
        return getWorkflowEntranceSelectorsByWorkflow(workflow, EntityPermission.READ_WRITE);
    }
    
    public WorkflowEntranceSelector getWorkflowEntranceSelector(WorkflowEntrance workflowEntrance, Selector selector,
            EntityPermission entityPermission) {
        WorkflowEntranceSelector workflowEntranceSelector;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowentranceselectors " +
                        "WHERE wkflensl_wkflen_workflowentranceid = ? AND wkflensl_sl_selectorid = ? AND wkflensl_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowentranceselectors " +
                        "WHERE wkflensl_wkflen_workflowentranceid = ? AND wkflensl_sl_selectorid = ? AND wkflensl_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = WorkflowEntranceSelectorFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, workflowEntrance.getPrimaryKey().getEntityId());
            ps.setLong(2, selector.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            workflowEntranceSelector = WorkflowEntranceSelectorFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return workflowEntranceSelector;
    }
    
    public WorkflowEntranceSelector getWorkflowEntranceSelector(WorkflowEntrance workflowEntrance, Selector selector) {
        return getWorkflowEntranceSelector(workflowEntrance, selector, EntityPermission.READ_ONLY);
    }
    
    public WorkflowEntranceSelector getWorkflowEntranceSelectorForUpdate(WorkflowEntrance workflowEntrance, Selector selector) {
        return getWorkflowEntranceSelector(workflowEntrance, selector, EntityPermission.READ_WRITE);
    }
    
    public WorkflowEntranceSelectorTransfer getWorkflowEntranceSelectorTransfer(UserVisit userVisit, WorkflowEntranceSelector workflowEntranceSelector) {
        return getWorkflowTransferCaches().getWorkflowEntranceSelectorTransferCache().getWorkflowEntranceSelectorTransfer(userVisit, workflowEntranceSelector);
    }
    
    public List<WorkflowEntranceSelectorTransfer> getWorkflowEntranceSelectorTransfers(UserVisit userVisit, Collection<WorkflowEntranceSelector> workflowEntranceSelectors) {
        List<WorkflowEntranceSelectorTransfer> workflowEntranceSelectorTransfers = new ArrayList<>(workflowEntranceSelectors.size());
        var workflowEntranceSelectorTransferCache = getWorkflowTransferCaches(userVisit).getWorkflowEntranceSelectorTransferCache();
        
        workflowEntranceSelectors.forEach((workflowEntranceSelector) ->
                workflowEntranceSelectorTransfers.add(workflowEntranceSelectorTransferCache.getWorkflowEntranceSelectorTransfer(workflowEntranceSelector))
        );
        
        return workflowEntranceSelectorTransfers;
    }
    
    public List<WorkflowEntranceSelectorTransfer> getWorkflowEntranceSelectorTransfersByWorkflowEntrance(UserVisit userVisit, WorkflowEntrance workflowEntrance) {
        return getWorkflowEntranceSelectorTransfers(userVisit, getWorkflowEntranceSelectorsByWorkflowEntrance(workflowEntrance));
    }
    
    public void deleteWorkflowEntranceSelector(WorkflowEntranceSelector workflowEntranceSelector, BasePK deletedBy) {
        workflowEntranceSelector.setThruTime(session.START_TIME_LONG);
        workflowEntranceSelector.store();
        
        sendEvent(workflowEntranceSelector.getWorkflowEntrance().getLastDetail().getWorkflowPK(), EventTypes.MODIFY, workflowEntranceSelector.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteWorkflowEntranceSelectors(List<WorkflowEntranceSelector> workflowEntranceSelectors, BasePK deletedBy) {
        workflowEntranceSelectors.forEach((workflowEntranceSelector) -> 
                deleteWorkflowEntranceSelector(workflowEntranceSelector, deletedBy)
        );
    }
    
    public void deleteWorkflowEntranceSelectorsByWorkflowEntrance(WorkflowEntrance workflowEntrance, BasePK deletedBy) {
        deleteWorkflowEntranceSelectors(getWorkflowEntranceSelectorsByWorkflowEntranceForUpdate(workflowEntrance), deletedBy);
    }
    
    public void deleteWorkflowEntranceSelectorsBySelector(Selector selector, BasePK deletedBy) {
        deleteWorkflowEntranceSelectors(getWorkflowEntranceSelectorsBySelectorForUpdate(selector), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Entrance Party Types
    // --------------------------------------------------------------------------------
    
    public WorkflowEntrancePartyType createWorkflowEntrancePartyType(WorkflowEntrance workflowEntrance, PartyType partyType,
            BasePK createdBy) {
        var workflowEntrancePartyType = WorkflowEntrancePartyTypeFactory.getInstance().create(session,
                workflowEntrance, partyType, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(workflowEntrance.getLastDetail().getWorkflowPK(), EventTypes.MODIFY, workflowEntrancePartyType.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return workflowEntrancePartyType;
    }

    public long countWorkflowEntrancePartyTypesByWorkflowEntrance(WorkflowEntrance workflowEntrance) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM workflowentrancepartytypes " +
                "WHERE wkflenptyp_wkflen_workflowentranceid = ? AND wkflenptyp_thrutime = ?",
                workflowEntrance, Session.MAX_TIME_LONG);
    }

    public long countWorkflowEntrancePartyTypesByPartyType(PartyType partyType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM workflowentrancepartytypes " +
                "WHERE wkflenptyp_ptyp_partytypeid = ? AND wkflenptyp_thrutime = ?",
                partyType, Session.MAX_TIME_LONG);
    }

    private List<WorkflowEntrancePartyType> getWorkflowEntrancePartyTypesByWorkflowEntrance(WorkflowEntrance workflowEntrance,
            EntityPermission entityPermission) {
        List<WorkflowEntrancePartyType> workflowEntrancePartyTypes;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowentrancepartytypes, partytypes " +
                        "WHERE wkflenptyp_wkflen_workflowentranceid = ? AND wkflenptyp_thrutime = ? " +
                        "AND wkflenptyp_ptyp_partytypeid = ptyp_partytypeid " +
                        "ORDER BY ptyp_sortorder, ptyp_partytypename " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowentrancepartytypes " +
                        "WHERE wkflenptyp_wkflen_workflowentranceid = ? AND wkflenptyp_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = WorkflowEntrancePartyTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, workflowEntrance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            workflowEntrancePartyTypes = WorkflowEntrancePartyTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return workflowEntrancePartyTypes;
    }
    
    public List<WorkflowEntrancePartyType> getWorkflowEntrancePartyTypesByWorkflowEntrance(WorkflowEntrance workflowEntrance) {
        return getWorkflowEntrancePartyTypesByWorkflowEntrance(workflowEntrance, EntityPermission.READ_ONLY);
    }
    
    public List<WorkflowEntrancePartyType> getWorkflowEntrancePartyTypesByWorkflowEntranceForUpdate(WorkflowEntrance workflowEntrance) {
        return getWorkflowEntrancePartyTypesByWorkflowEntrance(workflowEntrance, EntityPermission.READ_WRITE);
    }
    
    public WorkflowEntrancePartyType getWorkflowEntrancePartyType(WorkflowEntrance workflowEntrance, PartyType partyType,
            EntityPermission entityPermission) {
        WorkflowEntrancePartyType workflowEntrancePartyType;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowentrancepartytypes " +
                        "WHERE wkflenptyp_wkflen_workflowentranceid = ? AND wkflenptyp_ptyp_partytypeid = ? AND wkflenptyp_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowentrancepartytypes " +
                        "WHERE wkflenptyp_wkflen_workflowentranceid = ? AND wkflenptyp_ptyp_partytypeid = ? AND wkflenptyp_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = WorkflowEntrancePartyTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, workflowEntrance.getPrimaryKey().getEntityId());
            ps.setLong(2, partyType.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            workflowEntrancePartyType = WorkflowEntrancePartyTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return workflowEntrancePartyType;
    }
    
    public WorkflowEntrancePartyType getWorkflowEntrancePartyType(WorkflowEntrance workflowEntrance, PartyType partyType) {
        return getWorkflowEntrancePartyType(workflowEntrance, partyType, EntityPermission.READ_ONLY);
    }
    
    public WorkflowEntrancePartyType getWorkflowEntrancePartyTypeForUpdate(WorkflowEntrance workflowEntrance, PartyType partyType) {
        return getWorkflowEntrancePartyType(workflowEntrance, partyType, EntityPermission.READ_WRITE);
    }
    
    public WorkflowEntrancePartyTypeTransfer getWorkflowEntrancePartyTypeTransfer(UserVisit userVisit, WorkflowEntrancePartyType workflowEntrancePartyType) {
        return getWorkflowTransferCaches().getWorkflowEntrancePartyTypeTransferCache().getWorkflowEntrancePartyTypeTransfer(userVisit, workflowEntrancePartyType);
    }
    
    public List<WorkflowEntrancePartyTypeTransfer> getWorkflowEntrancePartyTypeTransfers(UserVisit userVisit, Collection<WorkflowEntrancePartyType> workflowEntrancePartyTypes) {
        List<WorkflowEntrancePartyTypeTransfer> workflowEntrancePartyTypeTransfers = new ArrayList<>(workflowEntrancePartyTypes.size());
        var workflowEntrancePartyTypeTransferCache = getWorkflowTransferCaches(userVisit).getWorkflowEntrancePartyTypeTransferCache();
        
        workflowEntrancePartyTypes.forEach((workflowEntrancePartyType) ->
                workflowEntrancePartyTypeTransfers.add(workflowEntrancePartyTypeTransferCache.getWorkflowEntrancePartyTypeTransfer(workflowEntrancePartyType))
        );
        
        return workflowEntrancePartyTypeTransfers;
    }
    
    public List<WorkflowEntrancePartyTypeTransfer> getWorkflowEntrancePartyTypeTransfersByWorkflowEntrance(UserVisit userVisit, WorkflowEntrance workflowEntrance) {
        return getWorkflowEntrancePartyTypeTransfers(userVisit, getWorkflowEntrancePartyTypesByWorkflowEntrance(workflowEntrance));
    }
    
    public void deleteWorkflowEntrancePartyType(WorkflowEntrancePartyType workflowEntrancePartyType, BasePK deletedBy) {
        deleteWorkflowEntranceSecurityRolesByWorkflowEntrancePartyType(workflowEntrancePartyType, deletedBy);
        
        workflowEntrancePartyType.setThruTime(session.START_TIME_LONG);
        workflowEntrancePartyType.store();
        
        sendEvent(workflowEntrancePartyType.getWorkflowEntrance().getLastDetail().getWorkflowPK(), EventTypes.MODIFY, workflowEntrancePartyType.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteWorkflowEntrancePartyTypes(List<WorkflowEntrancePartyType> workflowEntrancePartyTypes, BasePK deletedBy) {
        workflowEntrancePartyTypes.forEach((workflowEntrancePartyType) -> 
                deleteWorkflowEntrancePartyType(workflowEntrancePartyType, deletedBy)
        );
    }
    
    public void deleteWorkflowEntrancePartyTypesByWorkflowEntrance(WorkflowEntrance workflowEntrance, BasePK deletedBy) {
        deleteWorkflowEntrancePartyTypes(getWorkflowEntrancePartyTypesByWorkflowEntranceForUpdate(workflowEntrance), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Entrance Security Roles
    // --------------------------------------------------------------------------------
    
    public WorkflowEntranceSecurityRole createWorkflowEntranceSecurityRole(WorkflowEntrancePartyType workflowEntrancePartyType, SecurityRole securityRole,
            BasePK createdBy) {
        var workflowEntranceSecurityRole = WorkflowEntranceSecurityRoleFactory.getInstance().create(session,
                workflowEntrancePartyType, securityRole, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(workflowEntrancePartyType.getWorkflowEntrance().getLastDetail().getWorkflowPK(), EventTypes.MODIFY, workflowEntranceSecurityRole.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return workflowEntranceSecurityRole;
    }

    public long countWorkflowEntranceSecurityRolesByWorkflowEntrancePartyType(WorkflowEntrancePartyType workflowEntrancePartyType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM workflowentrancesecurityroles " +
                "WHERE wkflensrol_wkflenptyp_workflowentrancepartytypeid = ? AND wkflensrol_thrutime = ?",
                workflowEntrancePartyType, Session.MAX_TIME);
    }

    public long countWorkflowEntranceSecurityRolesBySecurityRole(SecurityRole securityRole) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM workflowentrancesecurityroles " +
                "WHERE wkflens_wkfls_worwkflensrol_srol_securityroleidkflowstepid = ? AND wkflensrol_thrutime = ?",
                securityRole, session.MAX_TIME);
    }

    private List<WorkflowEntranceSecurityRole> getWorkflowEntranceSecurityRolesByWorkflowEntrancePartyType(WorkflowEntrancePartyType workflowEntrancePartyType,
            EntityPermission entityPermission) {
        List<WorkflowEntranceSecurityRole> workflowEntranceSecurityRoles;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowentrancesecurityroles, securityroles, securityroledetails, securityrolegroups, securityrolegroupdetails " +
                        "WHERE wkflensrol_wkflenptyp_workflowentrancepartytypeid = ? AND wkflensrol_thrutime = ? " +
                        "AND wkflensrol_srol_securityroleid = srol_securityroleid AND srol_activedetailid = sroldt_securityroledetailid " +
                        "AND sroldt_srg_securityrolegroupid = srg_securityrolegroupid AND srg_activedetailid = srgdt_securityrolegroupdetailid " +
                        "ORDER BY sroldt_sortorder, sroldt_securityrolename, srgdt_sortorder, srgdt_securityrolegroupname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowentrancesecurityroles " +
                        "WHERE wkflensrol_wkflenptyp_workflowentrancepartytypeid = ? AND wkflensrol_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = WorkflowEntranceSecurityRoleFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, workflowEntrancePartyType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            workflowEntranceSecurityRoles = WorkflowEntranceSecurityRoleFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return workflowEntranceSecurityRoles;
    }
    
    public List<WorkflowEntranceSecurityRole> getWorkflowEntranceSecurityRolesByWorkflowEntrancePartyType(WorkflowEntrancePartyType workflowEntrancePartyType) {
        return getWorkflowEntranceSecurityRolesByWorkflowEntrancePartyType(workflowEntrancePartyType, EntityPermission.READ_ONLY);
    }
    
    public List<WorkflowEntranceSecurityRole> getWorkflowEntranceSecurityRolesByWorkflowEntrancePartyTypeForUpdate(WorkflowEntrancePartyType workflowEntrancePartyType) {
        return getWorkflowEntranceSecurityRolesByWorkflowEntrancePartyType(workflowEntrancePartyType, EntityPermission.READ_WRITE);
    }
    
    public WorkflowEntranceSecurityRole getWorkflowEntranceSecurityRole(WorkflowEntrancePartyType workflowEntrancePartyType, SecurityRole securityRole,
            EntityPermission entityPermission) {
        WorkflowEntranceSecurityRole workflowEntranceSecurityRole;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowentrancesecurityroles " +
                        "WHERE wkflensrol_wkflenptyp_workflowentrancepartytypeid = ? AND wkflensrol_srol_securityroleid = ? AND wkflensrol_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowentrancesecurityroles " +
                        "WHERE wkflensrol_wkflenptyp_workflowentrancepartytypeid = ? AND wkflensrol_srol_securityroleid = ? AND wkflensrol_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = WorkflowEntranceSecurityRoleFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, workflowEntrancePartyType.getPrimaryKey().getEntityId());
            ps.setLong(2, securityRole.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            workflowEntranceSecurityRole = WorkflowEntranceSecurityRoleFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return workflowEntranceSecurityRole;
    }
    
    public WorkflowEntranceSecurityRole getWorkflowEntranceSecurityRole(WorkflowEntrancePartyType workflowEntrancePartyType, SecurityRole securityRole) {
        return getWorkflowEntranceSecurityRole(workflowEntrancePartyType, securityRole, EntityPermission.READ_ONLY);
    }
    
    public WorkflowEntranceSecurityRole getWorkflowEntranceSecurityRoleForUpdate(WorkflowEntrancePartyType workflowEntrancePartyType, SecurityRole securityRole) {
        return getWorkflowEntranceSecurityRole(workflowEntrancePartyType, securityRole, EntityPermission.READ_WRITE);
    }
    
    public WorkflowEntranceSecurityRoleTransfer getWorkflowEntranceSecurityRoleTransfer(UserVisit userVisit, WorkflowEntranceSecurityRole workflowEntranceSecurityRole) {
        return getWorkflowTransferCaches().getWorkflowEntranceSecurityRoleTransferCache().getWorkflowEntranceSecurityRoleTransfer(userVisit, workflowEntranceSecurityRole);
    }
    
    public List<WorkflowEntranceSecurityRoleTransfer> getWorkflowEntranceSecurityRoleTransfers(UserVisit userVisit, Collection<WorkflowEntranceSecurityRole> workflowEntranceSecurityRoles) {
        List<WorkflowEntranceSecurityRoleTransfer> workflowEntranceSecurityRoleTransfers = new ArrayList<>(workflowEntranceSecurityRoles.size());
        var workflowEntranceSecurityRoleTransferCache = getWorkflowTransferCaches(userVisit).getWorkflowEntranceSecurityRoleTransferCache();
        
        workflowEntranceSecurityRoles.forEach((workflowEntranceSecurityRole) ->
                workflowEntranceSecurityRoleTransfers.add(workflowEntranceSecurityRoleTransferCache.getWorkflowEntranceSecurityRoleTransfer(workflowEntranceSecurityRole))
        );
        
        return workflowEntranceSecurityRoleTransfers;
    }
    
    public List<WorkflowEntranceSecurityRoleTransfer> getWorkflowEntranceSecurityRoleTransfersByWorkflowEntrancePartyType(UserVisit userVisit, WorkflowEntrancePartyType workflowEntrancePartyType) {
        return getWorkflowEntranceSecurityRoleTransfers(userVisit, getWorkflowEntranceSecurityRolesByWorkflowEntrancePartyType(workflowEntrancePartyType));
    }
    
    public void deleteWorkflowEntranceSecurityRole(WorkflowEntranceSecurityRole workflowEntranceSecurityRole, BasePK deletedBy) {
        workflowEntranceSecurityRole.setThruTime(session.START_TIME_LONG);
        workflowEntranceSecurityRole.store();
        
        sendEvent(workflowEntranceSecurityRole.getWorkflowEntrancePartyType().getWorkflowEntrance().getLastDetail().getWorkflowPK(), EventTypes.MODIFY, workflowEntranceSecurityRole.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteWorkflowEntranceSecurityRoles(List<WorkflowEntranceSecurityRole> workflowEntranceSecurityRoles, BasePK deletedBy) {
        workflowEntranceSecurityRoles.forEach((workflowEntranceSecurityRole) -> 
                deleteWorkflowEntranceSecurityRole(workflowEntranceSecurityRole, deletedBy)
        );
    }
    
    public void deleteWorkflowEntranceSecurityRolesByWorkflowEntrancePartyType(WorkflowEntrancePartyType workflowEntrancePartyType, BasePK deletedBy) {
        deleteWorkflowEntranceSecurityRoles(getWorkflowEntranceSecurityRolesByWorkflowEntrancePartyTypeForUpdate(workflowEntrancePartyType), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Entrance Steps
    // --------------------------------------------------------------------------------
    
    public WorkflowEntranceStep createWorkflowEntranceStep(WorkflowEntrance workflowEntrance, WorkflowStep workflowStep,
            BasePK createdBy) {
        var workflowEntranceStep = WorkflowEntranceStepFactory.getInstance().create(workflowEntrance,
                workflowStep, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(workflowEntrance.getLastDetail().getWorkflowPK(), EventTypes.MODIFY, workflowEntranceStep.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return workflowEntranceStep;
    }

    public long countWorkflowEntranceStepsByWorkflowEntrance(WorkflowEntrance workflowEntrance) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM workflowentrancesteps " +
                "WHERE wkflens_wkflen_workflowentranceid = ? AND wkflens_thrutime = ?",
                workflowEntrance, Session.MAX_TIME);
    }
    
    public long countWorkflowEntranceStepsByWorkflowStep(WorkflowStep workflowStep) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM workflowentrancesteps " +
                "WHERE wkflens_wkfls_workflowstepid = ? AND wkflens_thrutime = ?",
                workflowStep, session.MAX_TIME);
    }

    private static final Map<EntityPermission, String> getWorkflowEntranceStepsByWorkflowStepQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM workflowentrancesteps " +
                "WHERE wkflens_wkfls_workflowstepid = ? AND wkflens_thrutime = ? " +
                "_LIMIT_"); // TODO: ORDER BY
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM workflowentrancesteps " +
                "WHERE wkflens_wkfls_workflowstepid = ? AND wkflens_thrutime = ? " +
                "FOR UPDATE");
        getWorkflowEntranceStepsByWorkflowStepQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<WorkflowEntranceStep> getWorkflowEntranceStepsByWorkflowStep(WorkflowStep workflowStep, EntityPermission entityPermission) {
        return WorkflowEntranceStepFactory.getInstance().getEntitiesFromQuery(entityPermission, getWorkflowEntranceStepsByWorkflowStepQueries,
                workflowStep, Session.MAX_TIME_LONG);
    }
    
    public List<WorkflowEntranceStep> getWorkflowEntranceStepsByWorkflowStep(WorkflowStep workflowStep) {
        return getWorkflowEntranceStepsByWorkflowStep(workflowStep, EntityPermission.READ_ONLY);
    }
    
    public List<WorkflowEntranceStep> getWorkflowEntranceStepsByWorkflowStepForUpdate(WorkflowStep workflowStep) {
        return getWorkflowEntranceStepsByWorkflowStep(workflowStep, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getWorkflowEntranceStepsByWorkflowEntranceQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM workflowentrancesteps, workflowsteps, workflowstepdetails, workflows, workflowdetails " +
                "WHERE wkflens_wkflen_workflowentranceid = ? AND wkflens_thrutime = ? " +
                "AND wkflens_wkfls_workflowstepid = wkfls_workflowstepid AND wkfls_activedetailid = wkflsdt_workflowstepdetailid " +
                "AND wkflsdt_wkfl_workflowid = wkfl_workflowid AND wkfl_activedetailid = wkfldt_workflowdetailid " +
                "ORDER BY wkfldt_sortorder, wkfldt_workflowname, wkflsdt_sortorder, wkflsdt_workflowstepname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM workflowentrancesteps " +
                "WHERE wkflens_wkflen_workflowentranceid = ? AND wkflens_thrutime = ? " +
                "FOR UPDATE");
        getWorkflowEntranceStepsByWorkflowEntranceQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<WorkflowEntranceStep> getWorkflowEntranceStepsByWorkflowEntrance(WorkflowEntrance workflowEntrance, EntityPermission entityPermission) {
        return WorkflowEntranceStepFactory.getInstance().getEntitiesFromQuery(entityPermission, getWorkflowEntranceStepsByWorkflowEntranceQueries,
                workflowEntrance, Session.MAX_TIME_LONG);
    }
    
    public List<WorkflowEntranceStep> getWorkflowEntranceStepsByWorkflowEntrance(WorkflowEntrance workflowEntrance) {
        return getWorkflowEntranceStepsByWorkflowEntrance(workflowEntrance, EntityPermission.READ_ONLY);
    }
    
    public List<WorkflowEntranceStep> getWorkflowEntranceStepsByWorkflowEntranceForUpdate(WorkflowEntrance workflowEntrance) {
        return getWorkflowEntranceStepsByWorkflowEntrance(workflowEntrance, EntityPermission.READ_WRITE);
    }
    
    private WorkflowEntranceStep getWorkflowEntranceStep(WorkflowEntrance workflowEntrance, WorkflowStep workflowStep, EntityPermission entityPermission) {
        WorkflowEntranceStep workflowEntranceStep;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowentrancesteps " +
                        "WHERE wkflens_wkflen_workflowentranceid = ? AND wkflens_wkfls_workflowstepid = ? AND wkflens_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowentrancesteps " +
                        "WHERE wkflens_wkflen_workflowentranceid = ? AND wkflens_wkfls_workflowstepid = ? AND wkflens_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = WorkflowEntranceStepFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, workflowEntrance.getPrimaryKey().getEntityId());
            ps.setLong(2, workflowStep.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            workflowEntranceStep = WorkflowEntranceStepFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return workflowEntranceStep;
    }
    
    public WorkflowEntranceStep getWorkflowEntranceStep(WorkflowEntrance workflowEntrance, WorkflowStep workflowStep) {
        return getWorkflowEntranceStep(workflowEntrance, workflowStep, EntityPermission.READ_ONLY);
    }
    
    public WorkflowEntranceStep getWorkflowEntranceStepForUpdate(WorkflowEntrance workflowEntrance, WorkflowStep workflowStep) {
        return getWorkflowEntranceStep(workflowEntrance, workflowStep, EntityPermission.READ_WRITE);
    }
    
    public WorkflowEntranceStepTransfer getWorkflowEntranceStepTransfer(UserVisit userVisit, WorkflowEntranceStep workflowEntranceStep) {
        return getWorkflowTransferCaches().getWorkflowEntranceStepTransferCache().getWorkflowEntranceStepTransfer(userVisit, workflowEntranceStep);
    }
    
    public List<WorkflowEntranceStepTransfer> getWorkflowEntranceStepTransfers(UserVisit userVisit, Collection<WorkflowEntranceStep> workflowEntranceSteps) {
        List<WorkflowEntranceStepTransfer> workflowEntranceStepTransfers = new ArrayList<>(workflowEntranceSteps.size());
        var workflowEntranceStepTransferCache = getWorkflowTransferCaches(userVisit).getWorkflowEntranceStepTransferCache();
        
        workflowEntranceSteps.forEach((workflowEntranceStep) ->
                workflowEntranceStepTransfers.add(workflowEntranceStepTransferCache.getWorkflowEntranceStepTransfer(workflowEntranceStep))
        );
        
        return workflowEntranceStepTransfers;
    }
    
    public List<WorkflowEntranceStepTransfer> getWorkflowEntranceStepTransfersByWorkflowEntrance(UserVisit userVisit, WorkflowEntrance workflowEntrance) {
        return getWorkflowEntranceStepTransfers(userVisit, getWorkflowEntranceStepsByWorkflowEntrance(workflowEntrance));
    }

    public void deleteWorkflowEntranceStep(WorkflowEntranceStep workflowEntranceStep, BasePK deletedBy) {
        workflowEntranceStep.setThruTime(session.START_TIME_LONG);
        
        sendEvent(workflowEntranceStep.getWorkflowEntrance().getLastDetail().getWorkflowPK(), EventTypes.MODIFY, workflowEntranceStep.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteWorkflowEntranceSteps(List<WorkflowEntranceStep> workflowEntranceSteps, BasePK deletedBy) {
        workflowEntranceSteps.forEach((workflowEntranceStep) -> 
                deleteWorkflowEntranceStep(workflowEntranceStep, deletedBy)
        );
    }
    
    public void deleteWorkflowEntranceStepsByWorkflowEntrance(WorkflowEntrance workflowEntrance, BasePK deletedBy) {
        deleteWorkflowEntranceSteps(getWorkflowEntranceStepsByWorkflowEntranceForUpdate(workflowEntrance), deletedBy);
    }
    
    public void deleteWorkflowEntranceStepsByWorkflowStep(WorkflowStep workflowStep, BasePK deletedBy) {
        deleteWorkflowEntranceSteps(getWorkflowEntranceStepsByWorkflowStepForUpdate(workflowStep), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Destinations
    // --------------------------------------------------------------------------------
    
    public WorkflowDestination createWorkflowDestination(WorkflowStep workflowStep, String workflowDestinationName, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var defaultWorkflowDestination = getDefaultWorkflowDestination(workflowStep);
        var defaultFound = defaultWorkflowDestination != null;

        if(defaultFound && isDefault) {
            var defaultWorkflowDestinationDetailValue = getDefaultWorkflowDestinationDetailValueForUpdate(workflowStep);

            defaultWorkflowDestinationDetailValue.setIsDefault(false);
            updateWorkflowDestinationFromValue(defaultWorkflowDestinationDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var workflowDestination = WorkflowDestinationFactory.getInstance().create();
        var workflowDestinationDetail = WorkflowDestinationDetailFactory.getInstance().create(workflowDestination, workflowStep,
                workflowDestinationName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        workflowDestination = WorkflowDestinationFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, workflowDestination.getPrimaryKey());
        workflowDestination.setActiveDetail(workflowDestinationDetail);
        workflowDestination.setLastDetail(workflowDestinationDetail);
        workflowDestination.store();
        
        sendEvent(workflowStep.getLastDetail().getWorkflowPK(), EventTypes.MODIFY, workflowDestination.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return workflowDestination;
    }

    public long countWorkflowDestinationsByWorkflowStep(WorkflowStep workflowStep) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM workflowdestinations, workflowdestinationdetails " +
                "WHERE wkfldn_activedetailid = wkfldndt_workflowdestinationdetailid " +
                "AND wkfldndt_wkfls_workflowstepid = ?",
                workflowStep);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.WorkflowDestination */
    public WorkflowDestination getWorkflowDestinationByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new WorkflowDestinationPK(entityInstance.getEntityUniqueId());

        return WorkflowDestinationFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public WorkflowDestination getWorkflowDestinationByEntityInstance(EntityInstance entityInstance) {
        return getWorkflowDestinationByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public WorkflowDestination getWorkflowDestinationByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getWorkflowDestinationByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getDefaultWorkflowDestinationQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM workflowdestinations, workflowdestinationdetails " +
                "WHERE wkfldn_activedetailid = wkfldndt_workflowdestinationdetailid AND wkfldndt_wkfls_workflowstepid = ? " +
                "AND wkfldndt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM workflowdestinations, workflowdestinationdetails " +
                "WHERE wkfldn_activedetailid = wkfldndt_workflowdestinationdetailid AND wkfldndt_wkfls_workflowstepid = ? " +
                "AND wkfldndt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultWorkflowDestinationQueries = Collections.unmodifiableMap(queryMap);
    }
    
    public WorkflowDestination getDefaultWorkflowDestination(WorkflowStep workflowStep, EntityPermission entityPermission) {
        return WorkflowDestinationFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultWorkflowDestinationQueries,
                workflowStep);
    }
    
    public WorkflowDestination getDefaultWorkflowDestination(WorkflowStep workflowStep) {
        return getDefaultWorkflowDestination(workflowStep, EntityPermission.READ_ONLY);
    }
    
    public WorkflowDestination getDefaultWorkflowDestinationForUpdate(WorkflowStep workflowStep) {
        return getDefaultWorkflowDestination(workflowStep, EntityPermission.READ_WRITE);
    }
    
    public WorkflowDestinationDetailValue getDefaultWorkflowDestinationDetailValueForUpdate(WorkflowStep workflowStep) {
        return getDefaultWorkflowDestinationForUpdate(workflowStep).getLastDetailForUpdate().getWorkflowDestinationDetailValue().clone();
    }
    
    private static final Map<EntityPermission, String> getWorkflowDestinationsByWorkflowStepQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM workflowdestinations, workflowdestinationdetails " +
                "WHERE wkfldn_activedetailid = wkfldndt_workflowdestinationdetailid AND wkfldndt_wkfls_workflowstepid = ? " +
                "ORDER BY wkfldndt_sortorder, wkfldndt_workflowdestinationname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM workflowdestinations, workflowdestinationdetails " +
                "WHERE wkfldn_activedetailid = wkfldndt_workflowdestinationdetailid AND wkfldndt_wkfls_workflowstepid = ? " +
                "FOR UPDATE");
        getWorkflowDestinationsByWorkflowStepQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<WorkflowDestination> getWorkflowDestinationsByWorkflow(WorkflowStep workflowStep, EntityPermission entityPermission) {
        return WorkflowDestinationFactory.getInstance().getEntitiesFromQuery(entityPermission, getWorkflowDestinationsByWorkflowStepQueries,
                workflowStep);
    }
    
    public List<WorkflowDestination> getWorkflowDestinationsByWorkflowStep(WorkflowStep workflowStep) {
        return getWorkflowDestinationsByWorkflow(workflowStep, EntityPermission.READ_ONLY);
    }
    
    public List<WorkflowDestination> getWorkflowDestinationsByWorkflowStepForUpdate(WorkflowStep workflowStep) {
        return getWorkflowDestinationsByWorkflow(workflowStep, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getWorkflowDestinationByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM workflowdestinations, workflowdestinationdetails " +
                "WHERE wkfldn_activedetailid = wkfldndt_workflowdestinationdetailid AND wkfldndt_wkfls_workflowstepid = ? " +
                "AND wkfldndt_workflowdestinationname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM workflowdestinations, workflowdestinationdetails " +
                "WHERE wkfldn_activedetailid = wkfldndt_workflowdestinationdetailid AND wkfldndt_wkfls_workflowstepid = ? " +
                "AND wkfldndt_workflowdestinationname = ? " +
                "FOR UPDATE");
        getWorkflowDestinationByNameQueries = Collections.unmodifiableMap(queryMap);
    }
    
    public WorkflowDestination getWorkflowDestinationByName(WorkflowStep workflowStep, String workflowDestinationName, EntityPermission entityPermission) {
        return WorkflowDestinationFactory.getInstance().getEntityFromQuery(entityPermission, getWorkflowDestinationByNameQueries,
                workflowStep, workflowDestinationName);
    }
    
    public WorkflowDestination getWorkflowDestinationByName(WorkflowStep workflowStep, String workflowDestinationName) {
        return getWorkflowDestinationByName(workflowStep, workflowDestinationName, EntityPermission.READ_ONLY);
    }
    
    public WorkflowDestination getWorkflowDestinationByNameForUpdate(WorkflowStep workflowStep, String workflowDestinationName) {
        return getWorkflowDestinationByName(workflowStep, workflowDestinationName, EntityPermission.READ_WRITE);
    }
    
    public WorkflowDestinationDetailValue getWorkflowDestinationDetailValueForUpdate(WorkflowDestination workflowDestination) {
        return workflowDestination == null? null: workflowDestination.getLastDetailForUpdate().getWorkflowDestinationDetailValue().clone();
    }
    
    public WorkflowDestinationDetailValue getWorkflowDestinationDetailValueByNameForUpdate(WorkflowStep workflowStep, String workflowDestinationName) {
        return getWorkflowDestinationDetailValueForUpdate(getWorkflowDestinationByNameForUpdate(workflowStep, workflowDestinationName));
    }
    
    public BaseWorkflowChoicesBean getWorkflowDestinationChoices(BaseWorkflowChoicesBean baseWorkflowChoicesBean, String defaultWorkflowDestinationChoice,
            Language language, boolean allowNullChoice, WorkflowStep workflowStep, PartyPK partyPK) {
        var workflowSecurityLogic = WorkflowSecurityLogic.getInstance();
        var workflowDestinations = getWorkflowDestinationsByWorkflowStep(workflowStep);
        var size = workflowDestinations.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultWorkflowDestinationChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var workflowDestination : workflowDestinations) {
            if(workflowSecurityLogic.checkWorkflowDestinationAvailable(workflowDestination, partyPK)) {
                var workflowDestinationDetail = workflowDestination.getLastDetail();

                var label = getBestWorkflowDestinationDescription(workflowDestination, language);
                var value = workflowDestinationDetail.getWorkflowDestinationName();

                labels.add(label == null? value: label);
                values.add(value);

                var usingDefaultChoice = Objects.equals(defaultWorkflowDestinationChoice, value);
                if(usingDefaultChoice || (defaultValue == null && workflowDestinationDetail.getIsDefault())) {
                    defaultValue = value;
                }
            }
        }
        
        if(defaultValue == null) {
            // defaultValue may not have been set if the Party didn't have access to the default choice. We'll just use
            // the first one from the value List. If there are no values, then we'll leave it null.
            if(values.size() > 0) {
                defaultValue = values.get(1);
            }
        }
        
        baseWorkflowChoicesBean.setLabels(labels);
        baseWorkflowChoicesBean.setValues(values);
        baseWorkflowChoicesBean.setDefaultValue(defaultValue);
        
        return baseWorkflowChoicesBean;
    }
    
    public WorkflowDestinationTransfer getWorkflowDestinationTransfer(UserVisit userVisit, WorkflowDestination workflowDestination) {
        return getWorkflowTransferCaches().getWorkflowDestinationTransferCache().getWorkflowDestinationTransfer(userVisit, workflowDestination);
    }
    
    public List<WorkflowDestinationTransfer> getWorkflowDestinationTransfers(UserVisit userVisit, Collection<WorkflowDestination> workflowDestinations) {
        List<WorkflowDestinationTransfer> workflowDestinationTransfers = new ArrayList<>(workflowDestinations.size());
        var workflowDestinationTransferCache = getWorkflowTransferCaches(userVisit).getWorkflowDestinationTransferCache();
        
        workflowDestinations.forEach((workflowDestination) ->
                workflowDestinationTransfers.add(workflowDestinationTransferCache.getWorkflowDestinationTransfer(workflowDestination))
        );
        
        return workflowDestinationTransfers;
    }
    
    public List<WorkflowDestinationTransfer> getWorkflowDestinationTransfersByWorkflowStep(UserVisit userVisit, WorkflowStep workflowStep) {
        return getWorkflowDestinationTransfers(userVisit, getWorkflowDestinationsByWorkflowStep(workflowStep));
    }

    private void updateWorkflowDestinationFromValue(WorkflowDestinationDetailValue workflowDestinationDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(workflowDestinationDetailValue.hasBeenModified()) {
            var workflowDestination = WorkflowDestinationFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    workflowDestinationDetailValue.getWorkflowDestinationPK());
            var workflowDestinationDetail = workflowDestination.getActiveDetailForUpdate();
            
            workflowDestinationDetail.setThruTime(session.START_TIME_LONG);
            workflowDestinationDetail.store();

            var workflowDestinationPK = workflowDestinationDetail.getWorkflowDestinationPK();
            var workflowStep = workflowDestinationDetail.getWorkflowStep();
            var workflowStepPK = workflowStep.getPrimaryKey(); // Not updated
            var workflowDestinationName = workflowDestinationDetailValue.getWorkflowDestinationName();
            var isDefault = workflowDestinationDetailValue.getIsDefault();
            var sortOrder = workflowDestinationDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultWorkflowDestination = getDefaultWorkflowDestination(workflowStep);
                var defaultFound = defaultWorkflowDestination != null && !defaultWorkflowDestination.equals(workflowDestination);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultWorkflowDestinationDetailValue = getDefaultWorkflowDestinationDetailValueForUpdate(workflowStep);
                    
                    defaultWorkflowDestinationDetailValue.setIsDefault(false);
                    updateWorkflowDestinationFromValue(defaultWorkflowDestinationDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            workflowDestinationDetail = WorkflowDestinationDetailFactory.getInstance().create(workflowDestinationPK, workflowStepPK, workflowDestinationName,
                    isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            workflowDestination.setActiveDetail(workflowDestinationDetail);
            workflowDestination.setLastDetail(workflowDestinationDetail);
            
            sendEvent(workflowStep.getLastDetail().getWorkflowPK(), EventTypes.MODIFY, workflowDestination.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void updateWorkflowDestinationFromValue(WorkflowDestinationDetailValue workflowDestinationDetailValue, BasePK updatedBy) {
        updateWorkflowDestinationFromValue(workflowDestinationDetailValue, true, updatedBy);
    }
    
    private void deleteWorkflowDestination(WorkflowDestination workflowDestination, boolean checkDefault, BasePK deletedBy) {
        deleteWorkflowDestinationDescriptionsByWorkflowDestination(workflowDestination, deletedBy);
        deleteWorkflowDestinationStepsByWorkflowDestination(workflowDestination, deletedBy);
        deleteWorkflowDestinationPartyTypesByWorkflowDestination(workflowDestination, deletedBy);
        deleteWorkflowDestinationSelectorsByWorkflowDestination(workflowDestination, deletedBy);

        var workflowDestinationDetail = workflowDestination.getLastDetailForUpdate();
        workflowDestinationDetail.setThruTime(session.START_TIME_LONG);
        workflowDestination.setActiveDetail(null);
        workflowDestination.store();

        var workflowStep = workflowDestinationDetail.getWorkflowStep();
        
        if(checkDefault) {
            // Check for default, and pick one if necessary
            var defaultWorkflowDestination = getDefaultWorkflowDestination(workflowStep);
            if(defaultWorkflowDestination == null) {
                var workflowDestinations = getWorkflowDestinationsByWorkflowStepForUpdate(workflowStep);

                if(!workflowDestinations.isEmpty()) {
                    var iter = workflowDestinations.iterator();
                    if(iter.hasNext()) {
                        defaultWorkflowDestination = iter.next();
                    }
                    var workflowDestinationDetailValue = Objects.requireNonNull(defaultWorkflowDestination).getLastDetailForUpdate().getWorkflowDestinationDetailValue().clone();

                    workflowDestinationDetailValue.setIsDefault(true);
                    updateWorkflowDestinationFromValue(workflowDestinationDetailValue, false, deletedBy);
                }
            }
        }
        
        sendEvent(workflowStep.getLastDetail().getWorkflowPK(), EventTypes.MODIFY, workflowDestination.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteWorkflowDestination(WorkflowDestination workflowDestination, BasePK deletedBy) {
        deleteWorkflowDestination(workflowDestination, true, deletedBy);
    }
    
    private void deleteWorkflowDestinations(List<WorkflowDestination> workflowDestinations, boolean checkDefault, BasePK deletedBy) {
        workflowDestinations.forEach((workflowDestination) -> deleteWorkflowDestination(workflowDestination, checkDefault, deletedBy));
    }
    
    public void deleteWorkflowDestinationsByWorkflowStep(WorkflowStep workflowStep, BasePK deletedBy) {
        deleteWorkflowDestinations(getWorkflowDestinationsByWorkflowStepForUpdate(workflowStep), false, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Destination Descriptions
    // --------------------------------------------------------------------------------
    
    public WorkflowDestinationDescription createWorkflowDestinationDescription(WorkflowDestination workflowDestination, Language language, String description, BasePK createdBy) {
        var workflowDestinationDescription = WorkflowDestinationDescriptionFactory.getInstance().create(workflowDestination, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(workflowDestination.getLastDetail().getWorkflowStep().getLastDetail().getWorkflowPK(), EventTypes.MODIFY, workflowDestinationDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return workflowDestinationDescription;
    }
    
    private WorkflowDestinationDescription getWorkflowDestinationDescription(WorkflowDestination workflowDestination, Language language, EntityPermission entityPermission) {
        WorkflowDestinationDescription workflowDestinationDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowdestinationdescriptions " +
                        "WHERE wkfldnd_wkfldn_workflowdestinationid = ? AND wkfldnd_lang_languageid = ? AND wkfldnd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowdestinationdescriptions " +
                        "WHERE wkfldnd_wkfldn_workflowdestinationid = ? AND wkfldnd_lang_languageid = ? AND wkfldnd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = WorkflowDestinationDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, workflowDestination.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            workflowDestinationDescription = WorkflowDestinationDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return workflowDestinationDescription;
    }
    
    public WorkflowDestinationDescription getWorkflowDestinationDescription(WorkflowDestination workflowDestination, Language language) {
        return getWorkflowDestinationDescription(workflowDestination, language, EntityPermission.READ_ONLY);
    }
    
    public WorkflowDestinationDescription getWorkflowDestinationDescriptionForUpdate(WorkflowDestination workflowDestination, Language language) {
        return getWorkflowDestinationDescription(workflowDestination, language, EntityPermission.READ_WRITE);
    }
    
    public WorkflowDestinationDescriptionValue getWorkflowDestinationDescriptionValue(WorkflowDestinationDescription workflowDestinationDescription) {
        return workflowDestinationDescription == null? null: workflowDestinationDescription.getWorkflowDestinationDescriptionValue().clone();
    }
    
    public WorkflowDestinationDescriptionValue getWorkflowDestinationDescriptionValueForUpdate(WorkflowDestination workflowDestination, Language language) {
        return getWorkflowDestinationDescriptionValue(getWorkflowDestinationDescriptionForUpdate(workflowDestination, language));
    }
    
    private List<WorkflowDestinationDescription> getWorkflowDestinationDescriptionsByWorkflowDestination(WorkflowDestination workflowDestination, EntityPermission entityPermission) {
        List<WorkflowDestinationDescription> workflowDestinationDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowdestinationdescriptions, languages " +
                        "WHERE wkfldnd_wkfldn_workflowdestinationid = ? AND wkfldnd_thrutime = ? AND wkfldnd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowdestinationdescriptions " +
                        "WHERE wkfldnd_wkfldn_workflowdestinationid = ? AND wkfldnd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = WorkflowDestinationDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, workflowDestination.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            workflowDestinationDescriptions = WorkflowDestinationDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return workflowDestinationDescriptions;
    }
    
    public List<WorkflowDestinationDescription> getWorkflowDestinationDescriptionsByWorkflowDestination(WorkflowDestination workflowDestination) {
        return getWorkflowDestinationDescriptionsByWorkflowDestination(workflowDestination, EntityPermission.READ_ONLY);
    }
    
    public List<WorkflowDestinationDescription> getWorkflowDestinationDescriptionsByWorkflowDestinationForUpdate(WorkflowDestination workflowDestination) {
        return getWorkflowDestinationDescriptionsByWorkflowDestination(workflowDestination, EntityPermission.READ_WRITE);
    }
    
    public String getBestWorkflowDestinationDescription(WorkflowDestination workflowDestination, Language language) {
        String description;
        var workflowDestinationDescription = getWorkflowDestinationDescription(workflowDestination, language);
        
        if(workflowDestinationDescription == null && !language.getIsDefault()) {
            workflowDestinationDescription = getWorkflowDestinationDescription(workflowDestination, partyControl.getDefaultLanguage());
        }
        
        if(workflowDestinationDescription == null) {
            description = workflowDestination.getLastDetail().getWorkflowDestinationName();
        } else {
            description = workflowDestinationDescription.getDescription();
        }
        
        return description;
    }
    
    public WorkflowDestinationDescriptionTransfer getWorkflowDestinationDescriptionTransfer(UserVisit userVisit, WorkflowDestinationDescription workflowDestinationDescription) {
        return getWorkflowTransferCaches().getWorkflowDestinationDescriptionTransferCache().getWorkflowDestinationDescriptionTransfer(userVisit, workflowDestinationDescription);
    }
    
    public List<WorkflowDestinationDescriptionTransfer> getWorkflowDestinationDescriptionTransfersByWorkflowDestination(UserVisit userVisit, WorkflowDestination workflowDestination) {
        var workflowDestinationDescriptions = getWorkflowDestinationDescriptionsByWorkflowDestination(workflowDestination);
        List<WorkflowDestinationDescriptionTransfer> workflowDestinationDescriptionTransfers = new ArrayList<>(workflowDestinationDescriptions.size());
        var workflowDestinationDescriptionTransferCache = getWorkflowTransferCaches(userVisit).getWorkflowDestinationDescriptionTransferCache();
        
        workflowDestinationDescriptions.forEach((workflowDestinationDescription) ->
                workflowDestinationDescriptionTransfers.add(workflowDestinationDescriptionTransferCache.getWorkflowDestinationDescriptionTransfer(workflowDestinationDescription))
        );
        
        return workflowDestinationDescriptionTransfers;
    }
    
    public void updateWorkflowDestinationDescriptionFromValue(WorkflowDestinationDescriptionValue workflowDestinationDescriptionValue, BasePK updatedBy) {
        if(workflowDestinationDescriptionValue.hasBeenModified()) {
            var workflowDestinationDescription = WorkflowDestinationDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, workflowDestinationDescriptionValue.getPrimaryKey());
            
            workflowDestinationDescription.setThruTime(session.START_TIME_LONG);
            workflowDestinationDescription.store();

            var workflowDestination = workflowDestinationDescription.getWorkflowDestination();
            var language = workflowDestinationDescription.getLanguage();
            var description = workflowDestinationDescriptionValue.getDescription();
            
            workflowDestinationDescription = WorkflowDestinationDescriptionFactory.getInstance().create(workflowDestination, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(workflowDestination.getLastDetail().getWorkflowStep().getLastDetail().getWorkflowPK(), EventTypes.MODIFY, workflowDestinationDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteWorkflowDestinationDescription(WorkflowDestinationDescription workflowDestinationDescription, BasePK deletedBy) {
        workflowDestinationDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(workflowDestinationDescription.getWorkflowDestination().getLastDetail().getWorkflowStep().getLastDetail().getWorkflowPK(), EventTypes.MODIFY, workflowDestinationDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteWorkflowDestinationDescriptionsByWorkflowDestination(WorkflowDestination workflowDestination, BasePK deletedBy) {
        var workflowDestinationDescriptions = getWorkflowDestinationDescriptionsByWorkflowDestinationForUpdate(workflowDestination);
        
        workflowDestinationDescriptions.forEach((workflowDestinationDescription) -> 
                deleteWorkflowDestinationDescription(workflowDestinationDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Destination Selectors
    // --------------------------------------------------------------------------------
    
    public WorkflowDestinationSelector createWorkflowDestinationSelector(WorkflowDestination workflowDestination, Selector selector,
            BasePK createdBy) {
        var workflowDestinationSelector = WorkflowDestinationSelectorFactory.getInstance().create(session,
                workflowDestination, selector, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(workflowDestination.getLastDetail().getWorkflowStep().getLastDetail().getWorkflowPK(), EventTypes.MODIFY, workflowDestinationSelector.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return workflowDestinationSelector;
    }

    public long countWorkflowDestinationSelectorsByWorkflowDestination(WorkflowDestination workflowDestination) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM workflowdestinationselectors " +
                "WHERE wkfldnsl_wkfldn_workflowdestinationid = ? AND wkfldnsl_thrutime = ?",
                workflowDestination, Session.MAX_TIME_LONG);
    }

    public long countWorkflowDestinationSelectorsBySelector(Selector selector) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM workflowdestinationselectors " +
                "WHERE wkfldnsl_sl_selectorid = ? AND wkfldnsl_thrutime = ?",
                selector, Session.MAX_TIME_LONG);
    }

    private List<WorkflowDestinationSelector> getWorkflowDestinationSelectorsBySelector(Selector selector, EntityPermission entityPermission) {
        List<WorkflowDestinationSelector> workflowDestinationSelectors;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowdestinationselectors, workflowdestinations, workflowdestinationdetails, workflowsteps, workflowstepdetails, workflows, workflowdetails " +
                        "WHERE wkfldnsl_sl_selectorid = ? AND wkfldnsl_thrutime = ? " +
                        "AND wkfldnsl_wkfldn_workflowdestinationid = wkfldn_workflowdestinationid AND wkfldn_lastdetailid AND wkfldndt_workflowdestinationdetailid " +
                        "AND wkfldndt_wkfls_workflowstepid = wkfls_workflowstepid AND wkfls_lastdetailid = wkflsdt_workflowstepdetailid " +
                        "AND wkflsdt_wkfl_workflowid = wkfl_workflowid AND wkfl_lastdetailid = wkfldt_workflowdetailid " +
                        "ORDER BY wkfldndt_sortorder, wkfldndt_workflowdestinationname, wkflsdt_sortorder, wkflsdt_workflowstepname, wkfldt_sortorder, wkfldt_workflowname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowdestinationselectors " +
                        "WHERE wkfldnsl_sl_selectorid = ? AND wkfldnsl_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = WorkflowDestinationSelectorFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, selector.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            workflowDestinationSelectors = WorkflowDestinationSelectorFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return workflowDestinationSelectors;
    }
    
    public List<WorkflowDestinationSelector> getWorkflowDestinationSelectorsBySelector(Selector selector) {
        return getWorkflowDestinationSelectorsBySelector(selector, EntityPermission.READ_ONLY);
    }
    
    public List<WorkflowDestinationSelector> getWorkflowDestinationSelectorsBySelectorForUpdate(Selector selector) {
        return getWorkflowDestinationSelectorsBySelector(selector, EntityPermission.READ_WRITE);
    }
    
    private List<WorkflowDestinationSelector> getWorkflowDestinationSelectorsByWorkflowDestination(WorkflowDestination workflowDestination, EntityPermission entityPermission) {
        List<WorkflowDestinationSelector> workflowDestinationSelectors;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowdestinationselectors, selectors, selectordetails, selectortypes, selectortypedetails, selectorkinds, selectorkinddetails " +
                        "WHERE wkfldnsl_wkfldn_workflowdestinationid = ? AND wkfldnsl_thrutime = ? " +
                        "AND wkfldnsl_sl_selectorid = sl_selectorid AND sl_lastdetailid = sldt_selectordetailid " +
                        "AND sldt_slt_selectortypeid = slt_selectortypeid " +
                        "AND slt_lastdetailid = sltdt_selectortypedetailid AND sltdt_slk_selectorkindid = slk_selectorkindid " +
                        "AND slk_lastdetailid = slkdt_selectorkinddetailid " +
                        "ORDER BY sldt_sortorder, sldt_selectorname, sltdt_sortorder, sltdt_selectortypename, slkdt_sortorder, slkdt_selectorkindname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowdestinationselectors " +
                        "WHERE wkfldnsl_wkfldn_workflowdestinationid = ? AND wkfldnsl_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = WorkflowDestinationSelectorFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, workflowDestination.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            workflowDestinationSelectors = WorkflowDestinationSelectorFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return workflowDestinationSelectors;
    }
    
    public List<WorkflowDestinationSelector> getWorkflowDestinationSelectorsByWorkflowDestination(WorkflowDestination workflowDestination) {
        return getWorkflowDestinationSelectorsByWorkflowDestination(workflowDestination, EntityPermission.READ_ONLY);
    }
    
    public List<WorkflowDestinationSelector> getWorkflowDestinationSelectorsByWorkflowDestinationForUpdate(WorkflowDestination workflowDestination) {
        return getWorkflowDestinationSelectorsByWorkflowDestination(workflowDestination, EntityPermission.READ_WRITE);
    }
    
    public WorkflowDestinationSelector getWorkflowDestinationSelector(WorkflowDestination workflowDestination, Selector selector, EntityPermission entityPermission) {
        WorkflowDestinationSelector workflowDestinationSelector;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowdestinationselectors " +
                        "WHERE wkfldnsl_wkfldn_workflowdestinationid = ? AND wkfldnsl_sl_selectorid = ? AND wkfldnsl_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowdestinationselectors " +
                        "WHERE wkfldnsl_wkfldn_workflowdestinationid = ? AND wkfldnsl_sl_selectorid = ? AND wkfldnsl_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = WorkflowDestinationSelectorFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, workflowDestination.getPrimaryKey().getEntityId());
            ps.setLong(2, selector.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            workflowDestinationSelector = WorkflowDestinationSelectorFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return workflowDestinationSelector;
    }
    
    public WorkflowDestinationSelector getWorkflowDestinationSelector(WorkflowDestination workflowDestination, Selector selector) {
        return getWorkflowDestinationSelector(workflowDestination, selector, EntityPermission.READ_ONLY);
    }
    
    public WorkflowDestinationSelector getWorkflowDestinationSelectorForUpdate(WorkflowDestination workflowDestination, Selector selector) {
        return getWorkflowDestinationSelector(workflowDestination, selector, EntityPermission.READ_WRITE);
    }
    
    public WorkflowDestinationSelectorTransfer getWorkflowDestinationSelectorTransfer(UserVisit userVisit, WorkflowDestinationSelector workflowDestinationSelector) {
        return getWorkflowTransferCaches().getWorkflowDestinationSelectorTransferCache().getWorkflowDestinationSelectorTransfer(userVisit, workflowDestinationSelector);
    }
    
    public List<WorkflowDestinationSelectorTransfer> getWorkflowDestinationSelectorTransfers(UserVisit userVisit, Collection<WorkflowDestinationSelector> workflowDestinationSelectors) {
        List<WorkflowDestinationSelectorTransfer> workflowDestinationSelectorTransfers = new ArrayList<>(workflowDestinationSelectors.size());
        var workflowDestinationSelectorTransferCache = getWorkflowTransferCaches(userVisit).getWorkflowDestinationSelectorTransferCache();
        
        workflowDestinationSelectors.forEach((workflowDestinationSelector) ->
                workflowDestinationSelectorTransfers.add(workflowDestinationSelectorTransferCache.getWorkflowDestinationSelectorTransfer(workflowDestinationSelector))
        );
        
        return workflowDestinationSelectorTransfers;
    }
    
    public List<WorkflowDestinationSelectorTransfer> getWorkflowDestinationSelectorTransfersByWorkflowDestination(UserVisit userVisit, WorkflowDestination workflowDestination) {
        return getWorkflowDestinationSelectorTransfers(userVisit, getWorkflowDestinationSelectorsByWorkflowDestination(workflowDestination));
    }
    
    public List<WorkflowDestinationSelectorTransfer> getWorkflowDestinationSelectorTransfersBySelector(UserVisit userVisit, Selector selector) {
        return getWorkflowDestinationSelectorTransfers(userVisit, getWorkflowDestinationSelectorsBySelector(selector));
    }
    
    public void deleteWorkflowDestinationSelector(WorkflowDestinationSelector workflowDestinationSelector, BasePK deletedBy) {
        workflowDestinationSelector.setThruTime(session.START_TIME_LONG);
        workflowDestinationSelector.store();
        
        sendEvent(workflowDestinationSelector.getWorkflowDestination().getLastDetail().getWorkflowStep().getLastDetail().getWorkflowPK(), EventTypes.MODIFY, workflowDestinationSelector.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteWorkflowDestinationSelectors(List<WorkflowDestinationSelector> workflowDestinationSelectors, BasePK deletedBy) {
        workflowDestinationSelectors.forEach((workflowDestinationSelector) -> 
                deleteWorkflowDestinationSelector(workflowDestinationSelector, deletedBy)
        );
    }
    
    public void deleteWorkflowDestinationSelectorsByWorkflowDestination(WorkflowDestination workflowDestination, BasePK deletedBy) {
        deleteWorkflowDestinationSelectors(getWorkflowDestinationSelectorsByWorkflowDestinationForUpdate(workflowDestination), deletedBy);
    }
    
    public void deleteWorkflowDestinationSelectorsBySelector(Selector selector, BasePK deletedBy) {
        deleteWorkflowDestinationSelectors(getWorkflowDestinationSelectorsBySelectorForUpdate(selector), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Destination Party Types
    // --------------------------------------------------------------------------------
    
    public WorkflowDestinationPartyType createWorkflowDestinationPartyType(WorkflowDestination workflowDestination, PartyType partyType,
            BasePK createdBy) {
        var workflowDestinationPartyType = WorkflowDestinationPartyTypeFactory.getInstance().create(session,
                workflowDestination, partyType, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(workflowDestination.getLastDetail().getWorkflowStep().getLastDetail().getWorkflowPK(), EventTypes.MODIFY, workflowDestinationPartyType.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return workflowDestinationPartyType;
    }

    public long countWorkflowDestinationPartyTypesByWorkflowDestination(WorkflowDestination workflowDestination) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM workflowdestinationpartytypes " +
                "WHERE wkfldnptyp_wkfldn_workflowdestinationid = ? AND wkfldnptyp_thrutime = ?",
                workflowDestination, Session.MAX_TIME_LONG);
    }

    public long countWorkflowDestinationPartyTypesByPartyType(PartyType partyType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM workflowdestinationpartytypes " +
                "WHERE wkfldnptyp_ptyp_partytypeid = ? AND wkfldnptyp_thrutime = ?",
                partyType, Session.MAX_TIME_LONG);
    }

    private List<WorkflowDestinationPartyType> getWorkflowDestinationPartyTypesByWorkflowDestination(WorkflowDestination workflowDestination,
            EntityPermission entityPermission) {
        List<WorkflowDestinationPartyType> workflowDestinationPartyTypes;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowdestinationpartytypes, partytypes " +
                        "WHERE wkfldnptyp_wkfldn_workflowdestinationid = ? AND wkfldnptyp_thrutime = ? " +
                        "AND wkfldnptyp_ptyp_partytypeid = ptyp_partytypeid " +
                        "ORDER BY ptyp_sortorder, ptyp_partytypename " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowdestinationpartytypes " +
                        "WHERE wkfldnptyp_wkfldn_workflowdestinationid = ? AND wkfldnptyp_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = WorkflowDestinationPartyTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, workflowDestination.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            workflowDestinationPartyTypes = WorkflowDestinationPartyTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return workflowDestinationPartyTypes;
    }
    
    public List<WorkflowDestinationPartyType> getWorkflowDestinationPartyTypesByWorkflowDestination(WorkflowDestination workflowDestination) {
        return getWorkflowDestinationPartyTypesByWorkflowDestination(workflowDestination, EntityPermission.READ_ONLY);
    }
    
    public List<WorkflowDestinationPartyType> getWorkflowDestinationPartyTypesByWorkflowDestinationForUpdate(WorkflowDestination workflowDestination) {
        return getWorkflowDestinationPartyTypesByWorkflowDestination(workflowDestination, EntityPermission.READ_WRITE);
    }
    
    public WorkflowDestinationPartyType getWorkflowDestinationPartyType(WorkflowDestination workflowDestination, PartyType partyType,
            EntityPermission entityPermission) {
        WorkflowDestinationPartyType workflowDestinationPartyType;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowdestinationpartytypes " +
                        "WHERE wkfldnptyp_wkfldn_workflowdestinationid = ? AND wkfldnptyp_ptyp_partytypeid = ? AND wkfldnptyp_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowdestinationpartytypes " +
                        "WHERE wkfldnptyp_wkfldn_workflowdestinationid = ? AND wkfldnptyp_ptyp_partytypeid = ? AND wkfldnptyp_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = WorkflowDestinationPartyTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, workflowDestination.getPrimaryKey().getEntityId());
            ps.setLong(2, partyType.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            workflowDestinationPartyType = WorkflowDestinationPartyTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return workflowDestinationPartyType;
    }
    
    public WorkflowDestinationPartyType getWorkflowDestinationPartyType(WorkflowDestination workflowDestination, PartyType partyType) {
        return getWorkflowDestinationPartyType(workflowDestination, partyType, EntityPermission.READ_ONLY);
    }
    
    public WorkflowDestinationPartyType getWorkflowDestinationPartyTypeForUpdate(WorkflowDestination workflowDestination, PartyType partyType) {
        return getWorkflowDestinationPartyType(workflowDestination, partyType, EntityPermission.READ_WRITE);
    }
    
    public long countWorkflowDestinationPartyTypes(WorkflowDestination workflowDestination) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM workflowdestinationpartytypes " +
                "WHERE wkfldnptyp_wkfldn_workflowdestinationid = ?",
                workflowDestination);
    }
    
    public WorkflowDestinationPartyTypeTransfer getWorkflowDestinationPartyTypeTransfer(UserVisit userVisit, WorkflowDestinationPartyType workflowDestinationPartyType) {
        return getWorkflowTransferCaches().getWorkflowDestinationPartyTypeTransferCache().getWorkflowDestinationPartyTypeTransfer(userVisit, workflowDestinationPartyType);
    }
    
    public List<WorkflowDestinationPartyTypeTransfer> getWorkflowDestinationPartyTypeTransfers(UserVisit userVisit, Collection<WorkflowDestinationPartyType> workflowDestinationPartyTypes) {
        List<WorkflowDestinationPartyTypeTransfer> workflowDestinationPartyTypeTransfers = new ArrayList<>(workflowDestinationPartyTypes.size());
        var workflowDestinationPartyTypeTransferCache = getWorkflowTransferCaches(userVisit).getWorkflowDestinationPartyTypeTransferCache();
        
        workflowDestinationPartyTypes.forEach((workflowDestinationPartyType) ->
                workflowDestinationPartyTypeTransfers.add(workflowDestinationPartyTypeTransferCache.getWorkflowDestinationPartyTypeTransfer(workflowDestinationPartyType))
        );
        
        return workflowDestinationPartyTypeTransfers;
    }
    
    public List<WorkflowDestinationPartyTypeTransfer> getWorkflowDestinationPartyTypeTransfersByWorkflowDestination(UserVisit userVisit, WorkflowDestination workflowDestination) {
        return getWorkflowDestinationPartyTypeTransfers(userVisit, getWorkflowDestinationPartyTypesByWorkflowDestination(workflowDestination));
    }
    
    public void deleteWorkflowDestinationPartyType(WorkflowDestinationPartyType workflowDestinationPartyType, BasePK deletedBy) {
        deleteWorkflowDestinationSecurityRolesByWorkflowDestinationPartyType(workflowDestinationPartyType, deletedBy);
        
        workflowDestinationPartyType.setThruTime(session.START_TIME_LONG);
        workflowDestinationPartyType.store();
        
        sendEvent(workflowDestinationPartyType.getWorkflowDestination().getLastDetail().getWorkflowStep().getLastDetail().getWorkflowPK(), EventTypes.MODIFY, workflowDestinationPartyType.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteWorkflowDestinationPartyTypes(List<WorkflowDestinationPartyType> workflowDestinationPartyTypes, BasePK deletedBy) {
        workflowDestinationPartyTypes.forEach((workflowDestinationPartyType) -> 
                deleteWorkflowDestinationPartyType(workflowDestinationPartyType, deletedBy)
        );
    }
    
    public void deleteWorkflowDestinationPartyTypesByWorkflowDestination(WorkflowDestination workflowDestination, BasePK deletedBy) {
        deleteWorkflowDestinationPartyTypes(getWorkflowDestinationPartyTypesByWorkflowDestinationForUpdate(workflowDestination), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Destination Security Roles
    // --------------------------------------------------------------------------------
    
    public WorkflowDestinationSecurityRole createWorkflowDestinationSecurityRole(WorkflowDestinationPartyType workflowDestinationPartyType, SecurityRole securityRole,
            BasePK createdBy) {
        var workflowDestinationSecurityRole = WorkflowDestinationSecurityRoleFactory.getInstance().create(session,
                workflowDestinationPartyType, securityRole, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(workflowDestinationPartyType.getWorkflowDestination().getLastDetail().getWorkflowStep().getLastDetail().getWorkflowPK(), EventTypes.MODIFY, workflowDestinationSecurityRole.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return workflowDestinationSecurityRole;
    }

    public long countWorkflowDestinationSecurityRolesByWorkflowDestinationPartyType(WorkflowDestinationPartyType workflowDestinationPartyType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM workflowdestinationsecurityroles " +
                "WHERE wkfldnsrol_wkfldnptyp_workflowdestinationpartytypeid = ? AND wkfldnsrol_thrutime = ?",
                workflowDestinationPartyType, Session.MAX_TIME);
    }

    public long countWorkflowDestinationSecurityRolesBySecurityRole(SecurityRole securityRole) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM workflowdestinationsecurityroles " +
                "WHERE wkfldnsrol_srol_securityroleid = ? AND wkfldnsrol_thrutime = ?",
                securityRole, session.MAX_TIME);
    }

    private List<WorkflowDestinationSecurityRole> getWorkflowDestinationSecurityRolesByWorkflowDestinationPartyType(WorkflowDestinationPartyType workflowDestinationPartyType,
            EntityPermission entityPermission) {
        List<WorkflowDestinationSecurityRole> workflowDestinationSecurityRoles;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowdestinationsecurityroles, securityroles, securityroledetails, securityrolegroups, securityrolegroupdetails " +
                        "WHERE wkfldnsrol_wkfldnptyp_workflowdestinationpartytypeid = ? AND wkfldnsrol_thrutime = ? " +
                        "AND wkfldnsrol_srol_securityroleid = srol_securityroleid AND srol_activedetailid = sroldt_securityroledetailid " +
                        "AND sroldt_srg_securityrolegroupid = srg_securityrolegroupid AND srg_activedetailid = srgdt_securityrolegroupdetailid " +
                        "ORDER BY sroldt_sortorder, sroldt_securityrolename, srgdt_sortorder, srgdt_securityrolegroupname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowdestinationsecurityroles " +
                        "WHERE wkfldnsrol_wkfldnptyp_workflowdestinationpartytypeid = ? AND wkfldnsrol_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = WorkflowDestinationSecurityRoleFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, workflowDestinationPartyType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            workflowDestinationSecurityRoles = WorkflowDestinationSecurityRoleFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return workflowDestinationSecurityRoles;
    }
    
    public List<WorkflowDestinationSecurityRole> getWorkflowDestinationSecurityRolesByWorkflowDestinationPartyType(WorkflowDestinationPartyType workflowDestinationPartyType) {
        return getWorkflowDestinationSecurityRolesByWorkflowDestinationPartyType(workflowDestinationPartyType, EntityPermission.READ_ONLY);
    }
    
    public List<WorkflowDestinationSecurityRole> getWorkflowDestinationSecurityRolesByWorkflowDestinationPartyTypeForUpdate(WorkflowDestinationPartyType workflowDestinationPartyType) {
        return getWorkflowDestinationSecurityRolesByWorkflowDestinationPartyType(workflowDestinationPartyType, EntityPermission.READ_WRITE);
    }
    
    public WorkflowDestinationSecurityRole getWorkflowDestinationSecurityRole(WorkflowDestinationPartyType workflowDestinationPartyType, SecurityRole securityRole,
            EntityPermission entityPermission) {
        WorkflowDestinationSecurityRole workflowDestinationSecurityRole;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowdestinationsecurityroles " +
                        "WHERE wkfldnsrol_wkfldnptyp_workflowdestinationpartytypeid = ? AND wkfldnsrol_srol_securityroleid = ? AND wkfldnsrol_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowdestinationsecurityroles " +
                        "WHERE wkfldnsrol_wkfldnptyp_workflowdestinationpartytypeid = ? AND wkfldnsrol_srol_securityroleid = ? AND wkfldnsrol_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = WorkflowDestinationSecurityRoleFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, workflowDestinationPartyType.getPrimaryKey().getEntityId());
            ps.setLong(2, securityRole.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            workflowDestinationSecurityRole = WorkflowDestinationSecurityRoleFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return workflowDestinationSecurityRole;
    }
    
    public WorkflowDestinationSecurityRole getWorkflowDestinationSecurityRole(WorkflowDestinationPartyType workflowDestinationPartyType, SecurityRole securityRole) {
        return getWorkflowDestinationSecurityRole(workflowDestinationPartyType, securityRole, EntityPermission.READ_ONLY);
    }
    
    public WorkflowDestinationSecurityRole getWorkflowDestinationSecurityRoleForUpdate(WorkflowDestinationPartyType workflowDestinationPartyType, SecurityRole securityRole) {
        return getWorkflowDestinationSecurityRole(workflowDestinationPartyType, securityRole, EntityPermission.READ_WRITE);
    }
    
    public WorkflowDestinationSecurityRoleTransfer getWorkflowDestinationSecurityRoleTransfer(UserVisit userVisit, WorkflowDestinationSecurityRole workflowDestinationSecurityRole) {
        return getWorkflowTransferCaches().getWorkflowDestinationSecurityRoleTransferCache().getWorkflowDestinationSecurityRoleTransfer(userVisit, workflowDestinationSecurityRole);
    }
    
    public List<WorkflowDestinationSecurityRoleTransfer> getWorkflowDestinationSecurityRoleTransfers(UserVisit userVisit, Collection<WorkflowDestinationSecurityRole> workflowDestinationSecurityRoles) {
        List<WorkflowDestinationSecurityRoleTransfer> workflowDestinationSecurityRoleTransfers = new ArrayList<>(workflowDestinationSecurityRoles.size());
        var workflowDestinationSecurityRoleTransferCache = getWorkflowTransferCaches(userVisit).getWorkflowDestinationSecurityRoleTransferCache();
        
        workflowDestinationSecurityRoles.forEach((workflowDestinationSecurityRole) ->
                workflowDestinationSecurityRoleTransfers.add(workflowDestinationSecurityRoleTransferCache.getWorkflowDestinationSecurityRoleTransfer(workflowDestinationSecurityRole))
        );
        
        return workflowDestinationSecurityRoleTransfers;
    }
    
    public List<WorkflowDestinationSecurityRoleTransfer> getWorkflowDestinationSecurityRoleTransfersByWorkflowDestinationPartyType(UserVisit userVisit, WorkflowDestinationPartyType workflowDestinationPartyType) {
        return getWorkflowDestinationSecurityRoleTransfers(userVisit, getWorkflowDestinationSecurityRolesByWorkflowDestinationPartyType(workflowDestinationPartyType));
    }
    
    public void deleteWorkflowDestinationSecurityRole(WorkflowDestinationSecurityRole workflowDestinationSecurityRole, BasePK deletedBy) {
        workflowDestinationSecurityRole.setThruTime(session.START_TIME_LONG);
        workflowDestinationSecurityRole.store();
        
        sendEvent(workflowDestinationSecurityRole.getWorkflowDestinationPartyType().getWorkflowDestination().getLastDetail().getWorkflowStep().getLastDetail().getWorkflowPK(), EventTypes.MODIFY, workflowDestinationSecurityRole.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteWorkflowDestinationSecurityRoles(List<WorkflowDestinationSecurityRole> workflowDestinationSecurityRoles, BasePK deletedBy) {
        workflowDestinationSecurityRoles.forEach((workflowDestinationSecurityRole) -> 
                deleteWorkflowDestinationSecurityRole(workflowDestinationSecurityRole, deletedBy)
        );
    }
    
    public void deleteWorkflowDestinationSecurityRolesByWorkflowDestinationPartyType(WorkflowDestinationPartyType workflowDestinationPartyType, BasePK deletedBy) {
        deleteWorkflowDestinationSecurityRoles(getWorkflowDestinationSecurityRolesByWorkflowDestinationPartyTypeForUpdate(workflowDestinationPartyType), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Destination Steps
    // --------------------------------------------------------------------------------
    
    public WorkflowDestinationStep createWorkflowDestinationStep(WorkflowDestination workflowDestination, WorkflowStep workflowStep,
            BasePK createdBy) {
        var workflowDestinationStep = WorkflowDestinationStepFactory.getInstance().create(session,
                workflowDestination, workflowStep, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(workflowDestination.getLastDetail().getWorkflowStep().getLastDetail().getWorkflowPK(), EventTypes.MODIFY, workflowDestinationStep.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return workflowDestinationStep;
    }

    public long countWorkflowDestinationStepsByWorkflowDestination(WorkflowDestination workflowDestination) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM workflowdestinationsteps " +
                "WHERE wkfldns_wkfldn_workflowdestinationid = ? AND wkfldns_thrutime = ?",
                workflowDestination, Session.MAX_TIME);
    }


    public long countWorkflowDestinationStepsByWorkflowStep(WorkflowStep workflowStep) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM workflowdestinationsteps " +
                "WHERE wkfldns_wkfls_workflowstepid = ? AND wkfldns_thrutime = ?",
                workflowStep, session.MAX_TIME);
    }

    private static final Map<EntityPermission, String> getWorkflowDestinationStepsByWorkflowStepQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM workflowdestinationsteps " +
                "WHERE wkfldns_wkfls_workflowstepid = ? AND wkfldns_thrutime = ? " +
                "_LIMIT_"); // TODO: ORDER BY
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM workflowdestinationsteps " +
                "WHERE wkfldns_wkfls_workflowstepid = ? AND wkfldns_thrutime = ? " +
                "FOR UPDATE");
        getWorkflowDestinationStepsByWorkflowStepQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<WorkflowDestinationStep> getWorkflowDestinationStepsByWorkflowStep(WorkflowStep workflowStep, EntityPermission entityPermission) {
        return WorkflowDestinationStepFactory.getInstance().getEntitiesFromQuery(entityPermission, getWorkflowDestinationStepsByWorkflowStepQueries,
                workflowStep, Session.MAX_TIME_LONG);
    }
    
    public List<WorkflowDestinationStep> getWorkflowDestinationStepsByWorkflowStep(WorkflowStep workflowStep) {
        return getWorkflowDestinationStepsByWorkflowStep(workflowStep, EntityPermission.READ_ONLY);
    }
    
    public List<WorkflowDestinationStep> getWorkflowDestinationStepsByWorkflowStepForUpdate(WorkflowStep workflowStep) {
        return getWorkflowDestinationStepsByWorkflowStep(workflowStep, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getWorkflowDestinationStepsByWorkflowDestinationQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM workflowdestinationsteps, workflowsteps, workflowstepdetails, workflows, workflowdetails " +
                "WHERE wkfldns_wkfldn_workflowdestinationid = ? AND wkfldns_thrutime = ? " +
                "AND wkfldns_wkfls_workflowstepid = wkfls_workflowstepid AND wkfls_activedetailid = wkflsdt_workflowstepdetailid " +
                "AND wkflsdt_wkfl_workflowid = wkfl_workflowid AND wkfl_activedetailid = wkfldt_workflowdetailid " +
                "ORDER BY wkfldt_sortorder, wkfldt_workflowname, wkflsdt_sortorder, wkflsdt_workflowstepname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM workflowdestinationsteps " +
                "WHERE wkfldns_wkfldn_workflowdestinationid = ? AND wkfldns_thrutime = ? " +
                "FOR UPDATE");
        getWorkflowDestinationStepsByWorkflowDestinationQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<WorkflowDestinationStep> getWorkflowDestinationStepsByWorkflowDestination(WorkflowDestination workflowDestination, EntityPermission entityPermission) {
        return WorkflowDestinationStepFactory.getInstance().getEntitiesFromQuery(entityPermission, getWorkflowDestinationStepsByWorkflowDestinationQueries,
                workflowDestination, Session.MAX_TIME_LONG);
    }
    
    public List<WorkflowDestinationStep> getWorkflowDestinationStepsByWorkflowDestination(WorkflowDestination workflowDestination) {
        return getWorkflowDestinationStepsByWorkflowDestination(workflowDestination, EntityPermission.READ_ONLY);
    }
    
    public List<WorkflowDestinationStep> getWorkflowDestinationStepsByWorkflowDestinationForUpdate(WorkflowDestination workflowDestination) {
        return getWorkflowDestinationStepsByWorkflowDestination(workflowDestination, EntityPermission.READ_WRITE);
    }
    
    private WorkflowDestinationStep getWorkflowDestinationStep(WorkflowDestination workflowDestination, WorkflowStep workflowStep, EntityPermission entityPermission) {
        WorkflowDestinationStep workflowDestinationStep;
        
        try {
           String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowdestinationsteps " +
                        "WHERE wkfldns_wkfldn_workflowdestinationid = ? AND wkfldns_wkfls_workflowstepid = ? AND wkfldns_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowdestinationsteps " +
                        "WHERE wkfldns_wkfldn_workflowdestinationid = ? AND wkfldns_wkfls_workflowstepid = ? AND wkfldns_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = WorkflowDestinationStepFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, workflowDestination.getPrimaryKey().getEntityId());
            ps.setLong(2, workflowStep.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            workflowDestinationStep = WorkflowDestinationStepFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return workflowDestinationStep;
    }
    
    public WorkflowDestinationStep getWorkflowDestinationStep(WorkflowDestination workflowDestination, WorkflowStep workflowStep) {
        return getWorkflowDestinationStep(workflowDestination, workflowStep, EntityPermission.READ_ONLY);
    }
    
    public WorkflowDestinationStep getWorkflowDestinationStepForUpdate(WorkflowDestination workflowDestination, WorkflowStep workflowStep) {
        return getWorkflowDestinationStep(workflowDestination, workflowStep, EntityPermission.READ_WRITE);
    }
    
    public WorkflowDestinationStepTransfer getWorkflowDestinationStepTransfer(UserVisit userVisit, WorkflowDestinationStep workflowDestinationStep) {
        return getWorkflowTransferCaches().getWorkflowDestinationStepTransferCache().getWorkflowDestinationStepTransfer(userVisit, workflowDestinationStep);
    }
    
    public List<WorkflowDestinationStepTransfer> getWorkflowDestinationStepTransfers(UserVisit userVisit, Collection<WorkflowDestinationStep> workflowDestinationSteps) {
        List<WorkflowDestinationStepTransfer> workflowDestinationStepTransfers = new ArrayList<>(workflowDestinationSteps.size());
        var workflowDestinationStepTransferCache = getWorkflowTransferCaches(userVisit).getWorkflowDestinationStepTransferCache();
        
        workflowDestinationSteps.forEach((workflowDestinationStep) ->
                workflowDestinationStepTransfers.add(workflowDestinationStepTransferCache.getWorkflowDestinationStepTransfer(workflowDestinationStep))
        );
        
        return workflowDestinationStepTransfers;
    }
    
    public List<WorkflowDestinationStepTransfer> getWorkflowDestinationStepTransfersByWorkflowDestination(UserVisit userVisit, WorkflowDestination workflowDestination) {
        return getWorkflowDestinationStepTransfers(userVisit, getWorkflowDestinationStepsByWorkflowDestination(workflowDestination));
    }

    public void deleteWorkflowDestinationStep(WorkflowDestinationStep workflowDestinationStep, BasePK deletedBy) {
        workflowDestinationStep.setThruTime(session.START_TIME_LONG);
        
        sendEvent(workflowDestinationStep.getWorkflowDestination().getLastDetail().getWorkflowStep().getLastDetail().getWorkflowPK(), EventTypes.MODIFY, workflowDestinationStep.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteWorkflowDestinationSteps(List<WorkflowDestinationStep> workflowDestinationSteps, BasePK deletedBy) {
        workflowDestinationSteps.forEach((workflowDestinationStep) -> 
                deleteWorkflowDestinationStep(workflowDestinationStep, deletedBy)
        );
    }
    
    public void deleteWorkflowDestinationStepsByWorkflowDestination(WorkflowDestination workflowDestination, BasePK deletedBy) {
        deleteWorkflowDestinationSteps(getWorkflowDestinationStepsByWorkflowDestinationForUpdate(workflowDestination), deletedBy);
    }
    
    public void deleteWorkflowDestinationStepsByWorkflowStep(WorkflowStep workflowStep, BasePK deletedBy) {
        deleteWorkflowDestinationSteps(getWorkflowDestinationStepsByWorkflowStepForUpdate(workflowStep), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Selector Kinds
    // --------------------------------------------------------------------------------
    
    public WorkflowSelectorKind createWorkflowSelectorKind(Workflow workflow, SelectorKind selectorKind,
            BasePK createdBy) {
        var workflowSelectorKind = WorkflowSelectorKindFactory.getInstance().create(workflow,
                selectorKind, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(workflow.getLastDetail().getWorkflowPK(), EventTypes.MODIFY, workflowSelectorKind.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return workflowSelectorKind;
    }

    public long countWorkflowSelectorKindsByWorkflow(Workflow workflow) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM workflowselectorkinds " +
                "WHERE wkflslk_wkfl_workflowid = ? AND wkflslk_thrutime = ?",
                workflow, Session.MAX_TIME);
    }

    public long countWorkflowSelectorKindsBySelectorKind(SelectorKind selectorKind) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM workflowselectorkinds " +
                "WHERE wkflslk_slk_selectorkindid = ? AND wkflslk_thrutime = ?",
                selectorKind, Session.MAX_TIME);
    }

    private List<WorkflowSelectorKind> getWorkflowSelectorKindsByWorkflow(Workflow workflow,
            EntityPermission entityPermission) {
        List<WorkflowSelectorKind> workflowSelectorKinds;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowselectorkinds, selectorkinds, selectorkinddetails " +
                        "WHERE wkflslk_wkfl_workflowid = ? AND wkflslk_thrutime = ? " +
                        "AND wkflslk_slk_selectorkindid = slk_selectorkindid " +
                        "AND slk_lastdetailid = slkdt_selectorkinddetailid " +
                        "ORDER BY slkdt_sortorder, slkdt_selectorkindname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowselectorkinds " +
                        "WHERE wkflslk_wkfl_workflowid = ? AND wkflslk_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = WorkflowSelectorKindFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, workflow.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            workflowSelectorKinds = WorkflowSelectorKindFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return workflowSelectorKinds;
    }
    
    public List<WorkflowSelectorKind> getWorkflowSelectorKindsByWorkflow(Workflow workflow) {
        return getWorkflowSelectorKindsByWorkflow(workflow, EntityPermission.READ_ONLY);
    }
    
    public List<WorkflowSelectorKind> getWorkflowSelectorKindsByWorkflowForUpdate(Workflow workflow) {
        return getWorkflowSelectorKindsByWorkflow(workflow, EntityPermission.READ_WRITE);
    }
    
    private List<WorkflowSelectorKind> getWorkflowSelectorKindsBySelectorKindForUpdate(SelectorKind selectorKind,
            EntityPermission entityPermission) {
        List<WorkflowSelectorKind> workflowSelectorKinds;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowselectorkinds, workflows, workflowdetails " +
                        "WHERE wkflslk_slk_selectorkindid = ? AND wkflslk_thrutime = ? " +
                        "AND wkflslk_wkfl_workflowid = wkfl_workflowid AND wkfl_lastdetailid = wkfldt_workflowdetailid " +
                        "ORDER BY wkfldt_sortorder, wkfldt_workflowname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowselectorkinds " +
                        "WHERE wkflslk_slk_selectorkindid = ? AND wkflslk_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = WorkflowSelectorKindFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, selectorKind.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            workflowSelectorKinds = WorkflowSelectorKindFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return workflowSelectorKinds;
    }
    
    public List<WorkflowSelectorKind> getWorkflowSelectorKindsBySelectorKind(SelectorKind selectorKind) {
        return getWorkflowSelectorKindsBySelectorKindForUpdate(selectorKind, EntityPermission.READ_ONLY);
    }
    
    public List<WorkflowSelectorKind> getWorkflowSelectorKindsBySelectorKindForUpdate(SelectorKind selectorKind) {
        return getWorkflowSelectorKindsBySelectorKindForUpdate(selectorKind, EntityPermission.READ_WRITE);
    }
    
    private WorkflowSelectorKind getWorkflowSelectorKind(Workflow workflow, SelectorKind selectorKind, EntityPermission entityPermission) {
        WorkflowSelectorKind workflowSelectorKind;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowselectorkinds " +
                        "WHERE wkflslk_wkfl_workflowid = ? AND wkflslk_slk_selectorkindid = ? AND wkflslk_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowselectorkinds " +
                        "WHERE wkflslk_wkfl_workflowid = ? AND wkflslk_slk_selectorkindid = ? AND wkflslk_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = WorkflowSelectorKindFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, workflow.getPrimaryKey().getEntityId());
            ps.setLong(2, selectorKind.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            workflowSelectorKind = WorkflowSelectorKindFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return workflowSelectorKind;
    }
    
    public WorkflowSelectorKind getWorkflowSelectorKind(Workflow workflow, SelectorKind selectorKind) {
        return getWorkflowSelectorKind(workflow, selectorKind, EntityPermission.READ_ONLY);
    }
    
    public WorkflowSelectorKind getWorkflowSelectorKindForUpdate(Workflow workflow, SelectorKind selectorKind) {
        return getWorkflowSelectorKind(workflow, selectorKind, EntityPermission.READ_WRITE);
    }
    
    public WorkflowSelectorKindTransfer getWorkflowSelectorKindTransfer(UserVisit userVisit, WorkflowSelectorKind workflowSelectorKind) {
        return getWorkflowTransferCaches().getWorkflowSelectorKindTransferCache().getWorkflowSelectorKindTransfer(userVisit, workflowSelectorKind);
    }
    
    public List<WorkflowSelectorKindTransfer> getWorkflowSelectorKindTransfers(UserVisit userVisit, Collection<WorkflowSelectorKind> workflowSelectorKinds) {
        List<WorkflowSelectorKindTransfer> workflowSelectorKindTransfers = new ArrayList<>(workflowSelectorKinds.size());
        var workflowSelectorKindTransferCache = getWorkflowTransferCaches(userVisit).getWorkflowSelectorKindTransferCache();
        
        workflowSelectorKinds.forEach((workflowSelectorKind) ->
                workflowSelectorKindTransfers.add(workflowSelectorKindTransferCache.getWorkflowSelectorKindTransfer(workflowSelectorKind))
        );
        
        return workflowSelectorKindTransfers;
    }
    
    public List<WorkflowSelectorKindTransfer> getWorkflowSelectorKindTransfersByWorkflow(UserVisit userVisit, Workflow workflow) {
        return getWorkflowSelectorKindTransfers(userVisit, getWorkflowSelectorKindsByWorkflow(workflow));
    }
    
    public List<WorkflowSelectorKindTransfer> getWorkflowSelectorKindTransfersBySelectorKind(UserVisit userVisit, SelectorKind selectorKind) {
        return getWorkflowSelectorKindTransfers(userVisit, getWorkflowSelectorKindsBySelectorKind(selectorKind));
    }
    
    public void deleteWorkflowSelectorKind(WorkflowSelectorKind workflowSelectorKind, BasePK deletedBy) {
        workflowSelectorKind.setThruTime(session.START_TIME_LONG);
        
        sendEvent(workflowSelectorKind.getWorkflow().getLastDetail().getWorkflowPK(), EventTypes.MODIFY, workflowSelectorKind.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteWorkflowSelectorKinds(List<WorkflowSelectorKind> workflowSelectorKinds, BasePK deletedBy) {
        workflowSelectorKinds.forEach((workflowSelectorKind) -> 
                deleteWorkflowSelectorKind(workflowSelectorKind, deletedBy)
        );
    }
    
    public void deleteWorkflowSelectorKindsByWorkflow(Workflow workflow, BasePK deletedBy) {
        deleteWorkflowSelectorKinds(getWorkflowSelectorKindsByWorkflowForUpdate(workflow), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Entity Statuses
    // --------------------------------------------------------------------------------
    
    public WorkflowEntityStatus createWorkflowEntityStatus(EntityInstance entityInstance, WorkflowStep workflowStep,
            WorkEffortScope workEffortScope, BasePK createdBy) {
        var workflowEntityStatus = WorkflowEntityStatusFactory.getInstance().create(entityInstance, workflowStep, workEffortScope, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEvent(entityInstance, EventTypes.MODIFY, workflowEntityStatus.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return workflowEntityStatus;
    }

    public long countWorkflowEntityStatusesByEntityInstance(EntityInstance entityInstance) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM workflowentitystatuses " +
                        "WHERE wkfles_eni_entityinstanceid = ? AND wkfles_thrutime = ?",
                entityInstance, Session.MAX_TIME);
    }

    public long countWorkflowEntityStatusesByWorkflowStep(WorkflowStep workflowStep) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM workflowentitystatuses " +
                        "WHERE wkfles_wkfls_workflowstepid = ? AND wkfles_thrutime = ?",
                workflowStep, Session.MAX_TIME);
    }

    public long countWorkflowEntityStatusesByWorkEffortScope(WorkEffortScope workEffortScope) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM workflowentitystatuses " +
                        "WHERE wkfles_wes_workeffortscopeid = ? AND wkfles_thrutime = ?",
                workEffortScope, Session.MAX_TIME);
    }

    public long countWorkflowEntityStatusesByWorkflow(Workflow workflow) {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM workflowentitystatuses, workflowsteps, workflowstepdetails, workflows, workflowdetails
                WHERE wkfles_thrutime = ?
                AND wkfles_wkfls_workflowstepid = wkfls_workflowstepid AND wkfls_activedetailid = wkflsdt_workflowstepdetailid
                AND wkflsdt_wkfl_workflowid = ?
                AND wkflsdt_wkfl_workflowid = wkfl_workflowid AND wkfl_activedetailid = wkfldt_workflowdetailid
                """, Session.MAX_TIME, workflow);
    }

    public long countWorkflowEntityStatusesByWorkflowAndEntityInstance(Workflow workflow, EntityInstance entityInstance) {
        return session.queryForLong("""
                        SELECT COUNT(*)
                        FROM workflowentitystatuses
                        JOIN workflowstepdetails ON wkfles_wkfls_workflowstepid = wkflsdt_wkfls_workflowstepid
                        WHERE wkfles_eni_entityinstanceid = ? AND wkfles_thrutime = ?
                        AND wkflsdt_wkfl_workflowid = ? AND wkflsdt_thrutime = ?
                        """, entityInstance, Session.MAX_TIME, workflow, Session.MAX_TIME);
    }


    private List<WorkflowEntityStatus> getWorkflowEntityStatusesByWorkEffortScope(WorkEffortScope workEffortScope,
            EntityPermission entityPermission) {
        List<WorkflowEntityStatus> workflowEntityStatuses;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowentitystatuses, workflowsteps, workflowstepdetails, workflows, workflowdetails " +
                        "WHERE wkfles_wes_workeffortscopeid = ? AND wkfles_thrutime = ? " +
                        "AND wkfles_wkfls_workflowstepid = wkfls_activedetailid AND wkfls_activedetailid = wkflsdt_workflowstepdetailid " +
                        "AND wkflsdt_wkfl_workflowid = wkfl_workflowid AND wkfl_activedetailid = wkfldt_workflowdetailid " +
                        "ORDER BY wkfles_fromtime, wkflsdt_sortorder, wkflsdt_workflowstepname, wkfldt_sortorder, wkfldt_sortorder " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowentitystatuses " +
                        "WHERE wkfles_wes_workeffortscopeid = ? AND wkfles_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = WorkflowEntityStatusFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, workEffortScope.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            workflowEntityStatuses = WorkflowEntityStatusFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return workflowEntityStatuses;
    }
    
    public List<WorkflowEntityStatus> getWorkflowEntityStatusesByWorkEffortScope(WorkEffortScope workEffortScope) {
        return getWorkflowEntityStatusesByWorkEffortScope(workEffortScope, EntityPermission.READ_ONLY);
    }
    
    public List<WorkflowEntityStatus> getWorkflowEntityStatusesByWorkEffortScopeForUpdate(WorkEffortScope workEffortScope) {
        return getWorkflowEntityStatusesByWorkEffortScope(workEffortScope, EntityPermission.READ_WRITE);
    }

    public List<WorkflowEntityStatus> getWorkflowEntityStatusesByEntityInstance(Workflow workflow, EntityInstance entityInstance, EntityPermission entityPermission) {
        List<WorkflowEntityStatus> workflowEntityStatuses;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowentitystatuses, workflowstepdetails " +
                        "WHERE wkfles_eni_entityinstanceid = ? AND wkfles_thrutime = ? " +
                        "AND wkfles_wkfls_workflowstepid = wkflsdt_wkfls_workflowstepid " +
                        "AND wkflsdt_wkfl_workflowid = ? AND wkflsdt_thrutime = ? " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowentitystatuses, workflowstepdetails " +
                        "WHERE wkfles_eni_entityinstanceid = ? AND wkfles_thrutime = ? " +
                        "AND wkfles_wkfls_workflowstepid = wkflsdt_wkfls_workflowstepid " +
                        "AND wkflsdt_wkfl_workflowid = ? AND wkflsdt_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = WorkflowEntityStatusFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            ps.setLong(3, workflow.getPrimaryKey().getEntityId());
            ps.setLong(4, Session.MAX_TIME);
            
            workflowEntityStatuses = WorkflowEntityStatusFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return workflowEntityStatuses;
    }
    
    public List<WorkflowEntityStatus> getWorkflowEntityStatusesByEntityInstance(Workflow workflow, EntityInstance entityInstance) {
        return getWorkflowEntityStatusesByEntityInstance(workflow, entityInstance, EntityPermission.READ_ONLY);
    }
    
    public List<WorkflowEntityStatus> getWorkflowEntityStatusesByEntityInstanceForUpdate(Workflow workflow, EntityInstance entityInstance) {
        return getWorkflowEntityStatusesByEntityInstance(workflow, entityInstance, EntityPermission.READ_WRITE);
    }
    
    public List<WorkflowEntityStatus> getWorkflowEntityStatusesByEntityInstanceUsingNames(String workflowName, EntityInstance entityInstance) {
        return getWorkflowEntityStatusesByEntityInstance(getWorkflowByName(workflowName), entityInstance);
    }
    
    public List<WorkflowEntityStatus> getWorkflowEntityStatusesByEntityInstanceForUpdateUsingNames(String workflowName, EntityInstance entityInstance) {
        return getWorkflowEntityStatusesByEntityInstanceForUpdate(getWorkflowByName(workflowName), entityInstance);
    }
    
    public WorkflowEntityStatus getWorkflowEntityStatusByEntityInstance(Workflow workflow, EntityInstance entityInstance) {
        var workflowEntityStatuses = getWorkflowEntityStatusesByEntityInstance(workflow, entityInstance);
        WorkflowEntityStatus workflowEntityStatus;
        
        if(workflowEntityStatuses.size() > 1) {
            throw new IllegalStateException();
        } else {
            workflowEntityStatus = workflowEntityStatuses.isEmpty() ? null : workflowEntityStatuses.iterator().next();
        }
        
        return workflowEntityStatus;
    }
    
    public WorkflowEntityStatus getWorkflowEntityStatusByEntityInstanceUsingNames(String workflowName, EntityInstance entityInstance) {
        return getWorkflowEntityStatusByEntityInstance(getWorkflowByName(workflowName), entityInstance);
    }
    
    private WorkflowEntityStatus getWorkflowEntityStatus(List<WorkflowEntityStatus> workflowEntityStatuses) {
        WorkflowEntityStatus workflowEntityStatus;
        
        if(workflowEntityStatuses.size() != 1) {
            throw new IllegalStateException();
        } else {
            workflowEntityStatus = workflowEntityStatuses.iterator().next();
        }
        
        return workflowEntityStatus;
    }
    
    public WorkflowEntityStatus getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(String workflowName, EntityInstance entityInstance) {
        return getWorkflowEntityStatus(getWorkflowEntityStatusesByEntityInstanceForUpdateUsingNames(workflowName, entityInstance));
    }
    
    public WorkflowEntityStatus getWorkflowEntityStatusByEntityInstanceForUpdate(Workflow workflow, EntityInstance entityInstance) {
        return getWorkflowEntityStatus(getWorkflowEntityStatusesByEntityInstanceForUpdate(workflow, entityInstance));
    }
    
    private List<WorkflowEntityStatus> getWorkflowEntityStatusesByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        List<WorkflowEntityStatus> workflowEntityStatuses;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowentitystatuses, workflowsteps, workflowstepdetails, workflows, workflowdetails " +
                        "WHERE wkfles_eni_entityinstanceid = ? AND wkfles_thrutime = ? " +
                        "AND wkfles_wkfls_workflowstepid = wkfls_workflowstepid AND wkfls_lastdetailid = wkflsdt_workflowstepdetailid " +
                        "AND wkflsdt_wkfl_workflowid = wkfl_workflowid AND wkfl_lastdetailid = wkfldt_wkfl_workflowid " +
                        "ORDER BY wkflsdt_sortorder, wkflsdt_workflowstepname, wkfldt_sortorder, wkfldt_workflowname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM workflowentitystatuses " +
                        "WHERE wkfles_eni_entityinstanceid = ? AND wkfles_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = WorkflowEntityStatusFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            workflowEntityStatuses = WorkflowEntityStatusFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return workflowEntityStatuses;
    }
    
    public List<WorkflowEntityStatus> getWorkflowEntityStatusesByEntityInstance(EntityInstance entityInstance) {
        return getWorkflowEntityStatusesByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }
    
    public List<WorkflowEntityStatus> getWorkflowEntityStatusesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getWorkflowEntityStatusesByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    public List<WorkflowEntityStatus> getWorkflowEntityStatusesByWorkflow(Workflow workflow) {
        List<WorkflowEntityStatus> workflowEntityStatuses;
        
        try {
            var ps = WorkflowEntityStatusFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM workflowentitystatuses, workflowsteps, workflowstepdetails, workflows, workflowdetails " +
                    "WHERE wkfles_thrutime = ? " +
                    "AND wkfles_wkfls_workflowstepid = wkfls_workflowstepid AND wkfls_activedetailid = wkflsdt_workflowstepdetailid " +
                    "AND wkflsdt_wkfl_workflowid = ? " +
                    "AND wkflsdt_wkfl_workflowid = wkfl_workflowid AND wkfl_activedetailid = wkfldt_workflowdetailid " +
                    "ORDER BY wkfldt_sortorder, wkfldt_workflowname, wkflsdt_sortorder, wkflsdt_workflowstepname " +
                    "_LIMIT_");
            
            ps.setLong(1, Session.MAX_TIME);
            ps.setLong(2, workflow.getPrimaryKey().getEntityId());
            
            workflowEntityStatuses = WorkflowEntityStatusFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return workflowEntityStatuses;
    }

    private static final Map<EntityPermission, String> getWorkflowEntityStatusesByWorkflowStepQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM workflowentitystatuses " +
                        "WHERE wkfles_wkfls_workflowstepid = ? AND wkfles_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM workflowentitystatuses " +
                        "WHERE wkfles_wkfls_workflowstepid = ? AND wkfles_thrutime = ? " +
                        "FOR UPDATE");
        getWorkflowEntityStatusesByWorkflowStepQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<WorkflowEntityStatus> getWorkflowEntityStatusesByWorkflowStep(WorkflowStep workflowStep, EntityPermission entityPermission) {
        return WorkflowEntityStatusFactory.getInstance().getEntitiesFromQuery(entityPermission, getWorkflowEntityStatusesByWorkflowStepQueries,
                workflowStep, Session.MAX_TIME_LONG);
    }

    public List<WorkflowEntityStatus> getWorkflowEntityStatusesByWorkflowStep(WorkflowStep workflowStep) {
        return getWorkflowEntityStatusesByWorkflowStep(workflowStep, EntityPermission.READ_ONLY);
    }

    public List<WorkflowEntityStatus> getWorkflowEntityStatusesByWorkflowStepForUpdate(WorkflowStep workflowStep) {
        return getWorkflowEntityStatusesByWorkflowStep(workflowStep, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getWorkflowEntityStatusesByWorkflowAndEntityTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY, """
                        SELECT _ALL_
                        FROM workflowentitystatuses
                        JOIN workflowsteps ON wkfles_wkfls_workflowstepid = wkfls_workflowstepid
                        JOIN workflowstepdetails ON wkfls_activedetailid = wkflsdt_workflowstepdetailid
                        JOIN entityinstances ON wkfles_eni_entityinstanceid = eni_entityinstanceid
                        WHERE wkflsdt_wkfl_workflowid = ? AND eni_ent_entitytypeid = ? AND wkfles_thrutime = ?
                        ORDER BY eni_entityuniqueid
                        """);
        queryMap.put(EntityPermission.READ_WRITE, """
                        SELECT _ALL_
                        FROM workflowentitystatuses
                        JOIN workflowsteps ON wkfles_wkfls_workflowstepid = wkfls_workflowstepid
                        JOIN workflowstepdetails ON wkfls_activedetailid = wkflsdt_workflowstepdetailid
                        JOIN entityinstances ON wkfles_eni_entityinstanceid = eni_entityinstanceid
                        WHERE wkflsdt_wkfl_workflowid = ? AND eni_ent_entitytypeid = ? AND wkfles_thrutime = ?
                        FOR UPDATE
                        """);
        getWorkflowEntityStatusesByWorkflowAndEntityTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<WorkflowEntityStatus> getWorkflowEntityStatusesByWorkflowAndEntityType(Workflow workflow, EntityType entityType, EntityPermission entityPermission) {
        return WorkflowEntityStatusFactory.getInstance().getEntitiesFromQuery(entityPermission, getWorkflowEntityStatusesByWorkflowAndEntityTypeQueries,
                workflow, entityType, Session.MAX_TIME_LONG);
    }

    public List<WorkflowEntityStatus> getWorkflowEntityStatusesByWorkflowAndEntityType(Workflow workflow, EntityType entityType) {
        return getWorkflowEntityStatusesByWorkflowAndEntityType(workflow, entityType, EntityPermission.READ_ONLY);
    }

    public List<WorkflowEntityStatus> getWorkflowEntityStatusesByWorkflowAndEntityTypeForUpdate(Workflow workflow, EntityType entityType) {
        return getWorkflowEntityStatusesByWorkflowAndEntityType(workflow, entityType, EntityPermission.READ_WRITE);
    }

    public WorkflowEntityStatusTransfer getWorkflowEntityStatusTransfer(UserVisit userVisit, WorkflowEntityStatus workflowEntityStatus) {
        return getWorkflowTransferCaches().getWorkflowEntityStatusTransferCache().getWorkflowEntityStatusTransfer(userVisit, workflowEntityStatus);
    }
    
    public WorkflowEntityStatusTransfer getWorkflowEntityStatusTransferByEntityInstance(UserVisit userVisit,
            Workflow workflow, EntityInstance entityInstance) {
        var workflowEntityStatus = getWorkflowEntityStatusByEntityInstance(workflow, entityInstance);
        var workflowEntityStatusTransfer = workflowEntityStatus == null? null: getWorkflowEntityStatusTransfer(userVisit,
                workflowEntityStatus);
        
        return workflowEntityStatusTransfer;
    }
    
    public WorkflowEntityStatusTransfer getWorkflowEntityStatusTransferByEntityInstanceUsingNames(UserVisit userVisit,
            String workflowName, EntityInstance entityInstance) {
        return getWorkflowEntityStatusTransferByEntityInstance(userVisit, getWorkflowByName(workflowName), entityInstance);
    }
    
    public List<WorkflowEntityStatusTransfer> getWorkflowEntityStatusTransfers(UserVisit userVisit, Collection<WorkflowEntityStatus> workflowEntityStatuses) {
        List<WorkflowEntityStatusTransfer> workflowEntityStatusTransfers = new ArrayList<>(workflowEntityStatuses.size());
        
        workflowEntityStatuses.forEach((workflowEntityStatus) -> {
            workflowEntityStatusTransfers.add(getWorkflowTransferCaches().getWorkflowEntityStatusTransferCache().getWorkflowEntityStatusTransfer(userVisit, workflowEntityStatus));
        });
        
        return workflowEntityStatusTransfers;
    }
    
    public List<WorkflowEntityStatusTransfer> getWorkflowEntityStatusTransfersByWorkflow(UserVisit userVisit, Workflow workflow) {
        return getWorkflowEntityStatusTransfers(userVisit, getWorkflowEntityStatusesByWorkflow(workflow));
    }
    
    public List<WorkflowEntityStatusTransfer> getWorkflowEntityStatusTransfersByEntityInstance(UserVisit userVisit, Workflow workflow,
            EntityInstance entityInstance) {
        return getWorkflowEntityStatusTransfers(userVisit, getWorkflowEntityStatusesByEntityInstance(workflow, entityInstance));
    }
    
    public void deleteWorkflowEntityStatus(WorkflowEntityStatus workflowEntityStatus, BasePK deletedBy) {
        var workflowTrigger = getWorkflowTrigger(workflowEntityStatus);
        
        workflowEntityStatus.setThruTime(session.START_TIME_LONG);
        workflowEntityStatus.store();
        
        if(workflowTrigger != null) {
            removeWorkflowTrigger(workflowTrigger);
        }
        
        sendEvent(workflowEntityStatus.getEntityInstance(), EventTypes.MODIFY, workflowEntityStatus.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteWorkflowEntityStatuses(List<WorkflowEntityStatus> workflowEntityStatuses, BasePK deletedBy) {
        workflowEntityStatuses.forEach((workflowEntityStatus) -> 
                deleteWorkflowEntityStatus(workflowEntityStatus, deletedBy)
        );
    }
    
    public void deleteWorkflowEntityStatusesByEntityInstance(Workflow workflow, EntityInstance entityInstance, BasePK deletedBy) {
        deleteWorkflowEntityStatuses(getWorkflowEntityStatusesByEntityInstanceForUpdate(workflow, entityInstance), deletedBy);
    }
    
    public void deleteWorkflowEntityStatusesByEntityInstanceUsingNames(String workflowName, EntityInstance entityInstance, BasePK deletedBy) {
        deleteWorkflowEntityStatuses(getWorkflowEntityStatusesByEntityInstanceForUpdateUsingNames(workflowName, entityInstance), deletedBy);
    }
    
    public void deleteWorkflowEntityStatusesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteWorkflowEntityStatuses(getWorkflowEntityStatusesByEntityInstanceForUpdate(entityInstance), deletedBy);
    }
    
    public void deleteWorkflowEntityStatusesByWorkflowStep(WorkflowStep workflowStep, BasePK deletedBy) {
        deleteWorkflowEntityStatuses(getWorkflowEntityStatusesByWorkflowStepForUpdate(workflowStep), deletedBy);
    }

    public void deleteWorkflowEntityStatusesByWorkflowAndEntityType(Workflow workflow, EntityType entityType, BasePK deletedBy) {
        deleteWorkflowEntityStatuses(getWorkflowEntityStatusesByWorkflowAndEntityTypeForUpdate(workflow, entityType), deletedBy);
    }

    // -------------------------------------------------------------------------
    //   Workflow Triggers
    // -------------------------------------------------------------------------
    
    public WorkflowTrigger createWorkflowTrigger(WorkflowEntityStatus workflowEntityStatus, Long triggerTime, Boolean errorsOccurred) {
        return WorkflowTriggerFactory.getInstance().create(workflowEntityStatus, triggerTime, errorsOccurred);
    }
    
    private static final Map<EntityPermission, String> getWorkflowTriggerQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM workflowtriggers " +
                "WHERE wkfltrg_wkfles_workflowentitystatusid = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM workflowtriggers " +
                "WHERE wkfltrg_wkfles_workflowentitystatusid = ? " +
                "FOR UPDATE");
        getWorkflowTriggerQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private WorkflowTrigger getWorkflowTrigger(WorkflowEntityStatus workflowEntityStatus, EntityPermission entityPermission) {
        return WorkflowTriggerFactory.getInstance().getEntityFromQuery(entityPermission, getWorkflowTriggerQueries,
                workflowEntityStatus);
    }
    
    public WorkflowTrigger getWorkflowTrigger(WorkflowEntityStatus workflowEntityStatus) {
        return getWorkflowTrigger(workflowEntityStatus, EntityPermission.READ_ONLY);
    }
    
    public WorkflowTrigger getWorkflowTriggerForUpdate(WorkflowEntityStatus workflowEntityStatus) {
        return getWorkflowTrigger(workflowEntityStatus, EntityPermission.READ_WRITE);
    }

    public Long getWorkflowTriggerTime(WorkflowEntityStatus workflowEntityStatus) {
        var workflowTrigger = getWorkflowTrigger(workflowEntityStatus);

        return workflowTrigger == null ? null : workflowTrigger.getTriggerTime();
    }
    
    private static final Map<EntityPermission, String> getWorkflowTriggersByTriggerTimeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM workflowtriggers " +
                "WHERE wkfltrg_triggertime < ? " +
                "ORDER BY wkfltrg_triggertime, wkfltrg_workflowtriggerid " +
                "_LIMIT_");
        getWorkflowTriggersByTriggerTimeQueries = Collections.unmodifiableMap(queryMap);
    }
    
    public List<WorkflowTrigger> getWorkflowTriggersByTriggerTime(Long triggerTime) {
        return WorkflowTriggerFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getWorkflowTriggersByTriggerTimeQueries,
                triggerTime);
    }
    
    public void removeWorkflowTrigger(WorkflowTrigger workflowTrigger) {
        workflowTrigger.remove();
    }
    
    // -------------------------------------------------------------------------
    //   Utilities
    // -------------------------------------------------------------------------
    
    public Boolean isEntityInWorkflow(final Workflow workflow, final EntityInstance entityInstance) {
        var workflowEntityStatuses = getWorkflowEntityStatusesByEntityInstance(workflow, entityInstance);

        return workflowEntityStatuses != null && !workflowEntityStatuses.isEmpty();
    }

    public void addEntityToWorkflow(final WorkflowEntrance workflowEntrance, final EntityInstance entityInstance, final WorkEffort workEffort,
            final Long triggerTime, final BasePK createdBy) {
        var workRequirementControl = Session.getModelController(WorkRequirementControl.class);
        var workflowEntranceSteps = getWorkflowEntranceStepsByWorkflowEntrance(workflowEntrance);
        var workEffortScope = workEffort == null ? null : workEffort.getLastDetail().getWorkEffortScope();

        workflowEntranceSteps.stream().map(WorkflowEntranceStep::getWorkflowStep).forEach((workflowStep) -> {
            var workflowEntityStatus = createWorkflowEntityStatus(entityInstance, workflowStep, workEffortScope, createdBy);

            if(workEffortScope != null) {
                var workRequirementScopes = workRequirementControl.getWorkRequirementScopesByWorkRequirementType(workEffortScope,
                        workflowStep);
                workRequirementScopes.forEach((workRequirementScope) -> {
                    WorkRequirementLogic.getInstance().createWorkRequirement(session, workEffort, workRequirementScope, null, null, null, createdBy);
                });
            }

            if (triggerTime != null) {
                createWorkflowTrigger(workflowEntityStatus, triggerTime, null);
            }
        });
    }

    public WorkflowEntrance addEntityToWorkflow(final Workflow workflow, final EntityInstance entityInstance, final WorkEffort workEffort,
            final Long triggerTime, final BasePK createdBy) {
        var selectorType = workflow.getLastDetail().getSelectorType();
        WorkflowEntrance workflowEntrance = null;

        if(selectorType == null) {
            workflowEntrance = getDefaultWorkflowEntrance(workflow);
        } else {
            var workflowEntranceSelectors = getWorkflowEntranceSelectorsByWorkflow(workflow);
            var selectorKindName = selectorType.getLastDetail().getSelectorKind().getLastDetail().getSelectorKindName();

            if(selectorKindName.equals(SelectorKinds.POSTAL_ADDRESS.name())) {
                var postalAddressSelectorEvaluator = new PostalAddressSelectorEvaluator(session, createdBy);

                for(var workflowEntranceSelector : workflowEntranceSelectors) {
                    var cachedSelector = new CachedSelector(workflowEntranceSelector.getSelector());

                    if(postalAddressSelectorEvaluator.isPostalAddressSelected(cachedSelector, entityInstance)) {
                        workflowEntrance = workflowEntranceSelector.getWorkflowEntrance();
                        break;
                    }
                }
            }

            if(workflowEntrance == null) {
                workflowEntrance = getDefaultWorkflowEntrance(workflow);
            }
        }

        addEntityToWorkflow(workflowEntrance, entityInstance, workEffort, triggerTime, createdBy);

        return workflowEntrance;
    }

    public WorkflowEntrance addEntityToWorkflowUsingNames(final ExecutionErrorAccumulator eea, final String workflowName, final EntityInstance entityInstance,
            final WorkEffort workEffort, final Long triggerTime, final BasePK createdBy) {
        var workflowEntrance = getWorkflowEntranceUsingNames(eea, workflowName, null);

        if(eea == null ? workflowEntrance != null : !eea.hasExecutionErrors()) {
            addEntityToWorkflow(workflowEntrance, entityInstance, workEffort, triggerTime, createdBy);
        }

        return workflowEntrance;
    }

    public WorkflowEntrance addEntityToWorkflowUsingNames(final ExecutionErrorAccumulator eea, final String workflowName, final String workflowEntranceName,
            final EntityInstance entityInstance, final WorkEffort workEffort, final Long triggerTime, final BasePK createdBy) {
        var workflowEntrance = getWorkflowEntranceUsingNames(eea, workflowName, workflowEntranceName);

        if(eea == null ? workflowEntrance != null : !eea.hasExecutionErrors()) {
            addEntityToWorkflow(workflowEntrance, entityInstance, workEffort, triggerTime, createdBy);
        }

        return workflowEntrance;
    }

    public void transitionEntityInWorkflow(final ExecutionErrorAccumulator eea, WorkflowEntityStatus workflowEntityStatus,
            final WorkflowDestination workflowDestination, final Long triggerTime, final PartyPK modifiedBy) {
        if(eea == null || WorkflowSecurityLogic.getInstance().checkTransitionEntityInWorkflow(eea, workflowDestination, modifiedBy)) {
            deleteWorkflowEntityStatus(workflowEntityStatus, modifiedBy);

            if(workflowDestination != null) {
                var entityInstance = workflowEntityStatus.getEntityInstance();
                var workflowEffortScope = workflowEntityStatus.getWorkEffortScope();
                var workflowDestinationSteps = getWorkflowDestinationStepsByWorkflowDestination(workflowDestination);

                for(var workflowDestinationStep : workflowDestinationSteps) {
                    workflowEntityStatus = createWorkflowEntityStatus(entityInstance, workflowDestinationStep.getWorkflowStep(),
                            workflowEffortScope, modifiedBy);

                    if(triggerTime != null) {
                        createWorkflowTrigger(workflowEntityStatus, triggerTime, null);
                    }
                }
            }
        }
    }
    
    public void transitionEntityInWorkflowUsingNames(final ExecutionErrorAccumulator eea, WorkflowEntityStatus workflowEntityStatus,
            final String workflowDestinationName, final Long triggerTime, final PartyPK modifiedBy) {
        var workflowStep = workflowEntityStatus.getWorkflowStep();
        var workflowDestination = getWorkflowDestinationByName(workflowStep, workflowDestinationName);
        
        if(workflowDestination != null) {
            transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, triggerTime, modifiedBy);
        } else {
            var workflowStepDetail = workflowStep.getLastDetail();
            
            eea.addExecutionError(ExecutionErrors.UnknownWorkflowDestinationName.name(), workflowStepDetail.getWorkflow().getLastDetail().getWorkflowName(),
                    workflowStepDetail.getWorkflowStepName(), workflowDestinationName);
        }
    }
    
}
