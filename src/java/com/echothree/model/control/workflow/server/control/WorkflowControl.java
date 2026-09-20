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
import com.echothree.model.control.workflow.server.transfer.WorkflowDescriptionTransferCache;
import com.echothree.model.control.workflow.server.transfer.WorkflowDestinationDescriptionTransferCache;
import com.echothree.model.control.workflow.server.transfer.WorkflowDestinationPartyTypeTransferCache;
import com.echothree.model.control.workflow.server.transfer.WorkflowDestinationSecurityRoleTransferCache;
import com.echothree.model.control.workflow.server.transfer.WorkflowDestinationSelectorTransferCache;
import com.echothree.model.control.workflow.server.transfer.WorkflowDestinationStepTransferCache;
import com.echothree.model.control.workflow.server.transfer.WorkflowDestinationTransferCache;
import com.echothree.model.control.workflow.server.transfer.WorkflowEntityStatusTransferCache;
import com.echothree.model.control.workflow.server.transfer.WorkflowEntityTypeTransferCache;
import com.echothree.model.control.workflow.server.transfer.WorkflowEntranceDescriptionTransferCache;
import com.echothree.model.control.workflow.server.transfer.WorkflowEntrancePartyTypeTransferCache;
import com.echothree.model.control.workflow.server.transfer.WorkflowEntranceSecurityRoleTransferCache;
import com.echothree.model.control.workflow.server.transfer.WorkflowEntranceSelectorTransferCache;
import com.echothree.model.control.workflow.server.transfer.WorkflowEntranceStepTransferCache;
import com.echothree.model.control.workflow.server.transfer.WorkflowEntranceTransferCache;
import com.echothree.model.control.workflow.server.transfer.WorkflowSelectorKindTransferCache;
import com.echothree.model.control.workflow.server.transfer.WorkflowStepDescriptionTransferCache;
import com.echothree.model.control.workflow.server.transfer.WorkflowStepTransferCache;
import com.echothree.model.control.workflow.server.transfer.WorkflowStepTypeTransferCache;
import com.echothree.model.control.workflow.server.transfer.WorkflowTransferCache;
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
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.COMPONENT_VENDORS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_INSTANCES_ENTITY_TYPE_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_TYPE_DETAILS_COMPONENT_VENDOR_FK;
import static com.echothree.model.jooq.server.keys.security.SecurityForeignKeys.SECURITY_ROLES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.security.SecurityForeignKeys.SECURITY_ROLE_DETAILS_SECURITY_ROLE_GROUP_FK;
import static com.echothree.model.jooq.server.keys.security.SecurityForeignKeys.SECURITY_ROLE_GROUPS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.selector.SelectorForeignKeys.SELECTORS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.selector.SelectorForeignKeys.SELECTOR_DETAILS_SELECTOR_TYPE_FK;
import static com.echothree.model.jooq.server.keys.selector.SelectorForeignKeys.SELECTOR_KINDS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.selector.SelectorForeignKeys.SELECTOR_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.selector.SelectorForeignKeys.SELECTOR_TYPE_DETAILS_SELECTOR_KIND_FK;
import static com.echothree.model.jooq.server.keys.workflow.WorkflowForeignKeys.WORKFLOWS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.workflow.WorkflowForeignKeys.WORKFLOWS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.workflow.WorkflowForeignKeys.WORKFLOW_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.workflow.WorkflowForeignKeys.WORKFLOW_DESTINATIONS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.workflow.WorkflowForeignKeys.WORKFLOW_DESTINATIONS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.workflow.WorkflowForeignKeys.WORKFLOW_DESTINATION_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.workflow.WorkflowForeignKeys.WORKFLOW_DESTINATION_DETAILS_WORKFLOW_STEP_FK;
import static com.echothree.model.jooq.server.keys.workflow.WorkflowForeignKeys.WORKFLOW_DESTINATION_PARTY_TYPES_PARTY_TYPE_FK;
import static com.echothree.model.jooq.server.keys.workflow.WorkflowForeignKeys.WORKFLOW_DESTINATION_SECURITY_ROLES_SECURITY_ROLE_FK;
import static com.echothree.model.jooq.server.keys.workflow.WorkflowForeignKeys.WORKFLOW_DESTINATION_SELECTORS_SELECTOR_FK;
import static com.echothree.model.jooq.server.keys.workflow.WorkflowForeignKeys.WORKFLOW_DESTINATION_SELECTORS_WORKFLOW_DESTINATION_FK;
import static com.echothree.model.jooq.server.keys.workflow.WorkflowForeignKeys.WORKFLOW_DESTINATION_STEPS_WORKFLOW_DESTINATION_FK;
import static com.echothree.model.jooq.server.keys.workflow.WorkflowForeignKeys.WORKFLOW_DESTINATION_STEPS_WORKFLOW_STEP_FK;
import static com.echothree.model.jooq.server.keys.workflow.WorkflowForeignKeys.WORKFLOW_ENTITY_STATUSES_ENTITY_INSTANCE_FK;
import static com.echothree.model.jooq.server.keys.workflow.WorkflowForeignKeys.WORKFLOW_ENTITY_STATUSES_WORKFLOW_STEP_FK;
import static com.echothree.model.jooq.server.keys.workflow.WorkflowForeignKeys.WORKFLOW_ENTITY_TYPES_ENTITY_TYPE_FK;
import static com.echothree.model.jooq.server.keys.workflow.WorkflowForeignKeys.WORKFLOW_ENTITY_TYPES_WORKFLOW_FK;
import static com.echothree.model.jooq.server.keys.workflow.WorkflowForeignKeys.WORKFLOW_ENTRANCES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.workflow.WorkflowForeignKeys.WORKFLOW_ENTRANCES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.workflow.WorkflowForeignKeys.WORKFLOW_ENTRANCE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.workflow.WorkflowForeignKeys.WORKFLOW_ENTRANCE_DETAILS_WORKFLOW_FK;
import static com.echothree.model.jooq.server.keys.workflow.WorkflowForeignKeys.WORKFLOW_ENTRANCE_PARTY_TYPES_PARTY_TYPE_FK;
import static com.echothree.model.jooq.server.keys.workflow.WorkflowForeignKeys.WORKFLOW_ENTRANCE_SECURITY_ROLES_SECURITY_ROLE_FK;
import static com.echothree.model.jooq.server.keys.workflow.WorkflowForeignKeys.WORKFLOW_ENTRANCE_SELECTORS_SELECTOR_FK;
import static com.echothree.model.jooq.server.keys.workflow.WorkflowForeignKeys.WORKFLOW_ENTRANCE_SELECTORS_WORKFLOW_ENTRANCE_FK;
import static com.echothree.model.jooq.server.keys.workflow.WorkflowForeignKeys.WORKFLOW_ENTRANCE_STEPS_WORKFLOW_ENTRANCE_FK;
import static com.echothree.model.jooq.server.keys.workflow.WorkflowForeignKeys.WORKFLOW_ENTRANCE_STEPS_WORKFLOW_STEP_FK;
import static com.echothree.model.jooq.server.keys.workflow.WorkflowForeignKeys.WORKFLOW_SELECTOR_KINDS_SELECTOR_KIND_FK;
import static com.echothree.model.jooq.server.keys.workflow.WorkflowForeignKeys.WORKFLOW_SELECTOR_KINDS_WORKFLOW_FK;
import static com.echothree.model.jooq.server.keys.workflow.WorkflowForeignKeys.WORKFLOW_STEPS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.workflow.WorkflowForeignKeys.WORKFLOW_STEPS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.workflow.WorkflowForeignKeys.WORKFLOW_STEP_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.workflow.WorkflowForeignKeys.WORKFLOW_STEP_DETAILS_WORKFLOW_FK;
import static com.echothree.model.jooq.server.tables.core.ComponentVendorDetails.ComponentVendorDetails;
import static com.echothree.model.jooq.server.tables.core.ComponentVendors.ComponentVendors;
import static com.echothree.model.jooq.server.tables.core.EntityInstances.EntityInstances;
import static com.echothree.model.jooq.server.tables.core.EntityTypeDetails.EntityTypeDetails;
import static com.echothree.model.jooq.server.tables.core.EntityTypes.EntityTypes;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import static com.echothree.model.jooq.server.tables.party.PartyTypes.PartyTypes;
import static com.echothree.model.jooq.server.tables.security.SecurityRoleDetails.SecurityRoleDetails;
import static com.echothree.model.jooq.server.tables.security.SecurityRoleGroupDetails.SecurityRoleGroupDetails;
import static com.echothree.model.jooq.server.tables.security.SecurityRoleGroups.SecurityRoleGroups;
import static com.echothree.model.jooq.server.tables.security.SecurityRoles.SecurityRoles;
import static com.echothree.model.jooq.server.tables.selector.SelectorDetails.SelectorDetails;
import static com.echothree.model.jooq.server.tables.selector.SelectorKindDetails.SelectorKindDetails;
import static com.echothree.model.jooq.server.tables.selector.SelectorKinds.SelectorKinds;
import static com.echothree.model.jooq.server.tables.selector.SelectorTypeDetails.SelectorTypeDetails;
import static com.echothree.model.jooq.server.tables.selector.SelectorTypes.SelectorTypes;
import static com.echothree.model.jooq.server.tables.selector.Selectors.Selectors;
import static com.echothree.model.jooq.server.tables.workflow.WorkflowDescriptions.WorkflowDescriptions;
import static com.echothree.model.jooq.server.tables.workflow.WorkflowDestinationDescriptions.WorkflowDestinationDescriptions;
import static com.echothree.model.jooq.server.tables.workflow.WorkflowDestinationDetails.WorkflowDestinationDetails;
import static com.echothree.model.jooq.server.tables.workflow.WorkflowDestinationPartyTypes.WorkflowDestinationPartyTypes;
import static com.echothree.model.jooq.server.tables.workflow.WorkflowDestinationSecurityRoles.WorkflowDestinationSecurityRoles;
import static com.echothree.model.jooq.server.tables.workflow.WorkflowDestinationSelectors.WorkflowDestinationSelectors;
import static com.echothree.model.jooq.server.tables.workflow.WorkflowDestinationSteps.WorkflowDestinationSteps;
import static com.echothree.model.jooq.server.tables.workflow.WorkflowDestinations.WorkflowDestinations;
import static com.echothree.model.jooq.server.tables.workflow.WorkflowDetails.WorkflowDetails;
import static com.echothree.model.jooq.server.tables.workflow.WorkflowEntityStatuses.WorkflowEntityStatuses;
import static com.echothree.model.jooq.server.tables.workflow.WorkflowEntityTypes.WorkflowEntityTypes;
import static com.echothree.model.jooq.server.tables.workflow.WorkflowEntranceDescriptions.WorkflowEntranceDescriptions;
import static com.echothree.model.jooq.server.tables.workflow.WorkflowEntranceDetails.WorkflowEntranceDetails;
import static com.echothree.model.jooq.server.tables.workflow.WorkflowEntrancePartyTypes.WorkflowEntrancePartyTypes;
import static com.echothree.model.jooq.server.tables.workflow.WorkflowEntranceSecurityRoles.WorkflowEntranceSecurityRoles;
import static com.echothree.model.jooq.server.tables.workflow.WorkflowEntranceSelectors.WorkflowEntranceSelectors;
import static com.echothree.model.jooq.server.tables.workflow.WorkflowEntranceSteps.WorkflowEntranceSteps;
import static com.echothree.model.jooq.server.tables.workflow.WorkflowEntrances.WorkflowEntrances;
import static com.echothree.model.jooq.server.tables.workflow.WorkflowSelectorKinds.WorkflowSelectorKinds;
import static com.echothree.model.jooq.server.tables.workflow.WorkflowStepDescriptions.WorkflowStepDescriptions;
import static com.echothree.model.jooq.server.tables.workflow.WorkflowStepDetails.WorkflowStepDetails;
import static com.echothree.model.jooq.server.tables.workflow.WorkflowStepTypeDescriptions.WorkflowStepTypeDescriptions;
import static com.echothree.model.jooq.server.tables.workflow.WorkflowStepTypes.WorkflowStepTypes;
import static com.echothree.model.jooq.server.tables.workflow.WorkflowSteps.WorkflowSteps;
import static com.echothree.model.jooq.server.tables.workflow.WorkflowTriggers.WorkflowTriggers;
import static com.echothree.model.jooq.server.tables.workflow.Workflows.Workflows;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;

@CommandScope
public class WorkflowControl
        extends BaseModelControl {
    
    @Inject
    protected WorkRequirementControl workRequirementControl;

    @Inject
    protected WorkRequirementLogic workRequirementLogic;

    @Inject
    protected WorkflowSecurityLogic workflowSecurityLogic;

    /** Creates a new instance of WorkflowControl */
    protected WorkflowControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Step Types
    // --------------------------------------------------------------------------------

    @Inject
    protected WorkflowStepTypeFactory workflowStepTypeFactory;

    @Inject
    WorkflowStepTypeTransferCache workflowStepTypeTransferCache;

    public WorkflowStepType createWorkflowStepType(String workflowStepTypeName, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var workflowStepType = workflowStepTypeFactory.create(workflowStepTypeName, isDefault, sortOrder);

        sendEvent(workflowStepType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return workflowStepType;
    }

    public long countWorkflowStepTypes() {
        return session.getDslContext()
                .selectCount()
                .from(WorkflowStepTypes)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.WorkflowStepType */
    public WorkflowStepType getWorkflowStepTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new WorkflowStepTypePK(entityInstance.getEntityUniqueId());

        return workflowStepTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public WorkflowStepType getWorkflowStepTypeByEntityInstance(EntityInstance entityInstance) {
        return getWorkflowStepTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public WorkflowStepType getWorkflowStepTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getWorkflowStepTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public WorkflowStepType getWorkflowStepTypeByName(String workflowStepTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(WorkflowStepTypes.fields())
                .from(WorkflowStepTypes)
                .where(WorkflowStepTypes.WORKFLOW_STEP_TYPE_NAME.eq(workflowStepTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return workflowStepTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public WorkflowStepType getWorkflowStepTypeByName(String workflowStepTypeName) {
        return getWorkflowStepTypeByName(workflowStepTypeName, EntityPermission.READ_ONLY);
    }

    public WorkflowStepType getWorkflowStepTypeByNameForUpdate(String workflowStepTypeName) {
        return getWorkflowStepTypeByName(workflowStepTypeName, EntityPermission.READ_WRITE);
    }

    public WorkflowStepType getDefaultWorkflowStepType(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(WorkflowStepTypes.fields())
                .from(WorkflowStepTypes)
                .where(WorkflowStepTypes.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return workflowStepTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public WorkflowStepType getDefaultWorkflowStepType() {
        return getDefaultWorkflowStepType(EntityPermission.READ_ONLY);
    }

    public WorkflowStepType getDefaultWorkflowStepTypeForUpdate() {
        return getDefaultWorkflowStepType(EntityPermission.READ_WRITE);
    }

    private List<WorkflowStepType> getWorkflowStepTypes(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(WorkflowStepTypes.fields())
                .from(WorkflowStepTypes);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(WorkflowStepTypes.SORT_ORDER, WorkflowStepTypes.WORKFLOW_STEP_TYPE_NAME),
                    WorkflowStepTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return workflowStepTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<WorkflowStepType> getWorkflowStepTypes() {
        return getWorkflowStepTypes(EntityPermission.READ_ONLY);
    }
    
    public List<WorkflowStepType> getWorkflowStepTypesForUpdate() {
        return getWorkflowStepTypes(EntityPermission.READ_WRITE);
    }
    
    public WorkflowStepTypeTransfer getWorkflowStepTypeTransfer(UserVisit userVisit, WorkflowStepType workflowStepType) {
        return workflowStepTypeTransferCache.getWorkflowStepTypeTransfer(userVisit, workflowStepType);
    }
    
    public List<WorkflowStepTypeTransfer> getWorkflowStepTypeTransfers(UserVisit userVisit, Collection<WorkflowStepType> workflowStepTypes) {
        List<WorkflowStepTypeTransfer> workflowStepTypeTransfers = new ArrayList<>(workflowStepTypes.size());
        
        workflowStepTypes.forEach((workflowStepType) ->
                workflowStepTypeTransfers.add(workflowStepTypeTransferCache.getWorkflowStepTypeTransfer(userVisit, workflowStepType))
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

    @Inject
    protected WorkflowStepTypeDescriptionFactory workflowStepTypeDescriptionFactory;

    public WorkflowStepTypeDescription createWorkflowStepTypeDescription(WorkflowStepType workflowStepType,
            Language language, String description, BasePK createdBy) {
        var workflowStepTypeDescription = workflowStepTypeDescriptionFactory.create(workflowStepType, language, description);

        sendEvent(workflowStepType.getPrimaryKey(), EventTypes.MODIFY, workflowStepTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return workflowStepTypeDescription;
    }
    
    public WorkflowStepTypeDescription getWorkflowStepTypeDescription(WorkflowStepType workflowStepType, Language language) {
        var query = session.getDslContext()
                .select(WorkflowStepTypeDescriptions.fields())
                .from(WorkflowStepTypeDescriptions)
                .where(WorkflowStepTypeDescriptions.WORKFLOW_STEP_TYPE.eq(workflowStepType.getPrimaryKey()),
                        WorkflowStepTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()));
        
        return workflowStepTypeDescriptionFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
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

    @Inject
    protected WorkflowDetailFactory workflowDetailFactory;

    @Inject
    protected WorkflowFactory workflowFactory;

    @Inject
    WorkflowTransferCache workflowTransferCache;

    public Workflow createWorkflow(String workflowName, SelectorType selectorType,
            SecurityRoleGroup securityRoleGroup, Integer sortOrder, BasePK createdBy) {
        var workflow = workflowFactory.create();
        var workflowDetail = workflowDetailFactory.create(workflow, workflowName,
                selectorType, securityRoleGroup, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        workflow = workflowFactory.getEntityFromPK(EntityPermission.READ_WRITE, workflow.getPrimaryKey());
        workflow.setActiveDetail(workflowDetail);
        workflow.setLastDetail(workflowDetail);
        workflow.store();
        
        sendEvent(workflow.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return workflow;
    }

    public long countWorkflows() {
        return session.getDslContext()
                .selectCount()
                .from(Workflows)
                .join(WorkflowDetails).onKey(WORKFLOWS_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countWorkflowsBySelectorKind(SelectorKind selectorKind) {
        return session.getDslContext()
                .selectCount()
                .from(WorkflowSelectorKinds)
                .join(Workflows).onKey(WORKFLOW_SELECTOR_KINDS_WORKFLOW_FK)
                .join(WorkflowDetails).onKey(WORKFLOWS_ACTIVE_DETAIL_FK)
                .where(WorkflowSelectorKinds.SELECTOR_KIND.eq(selectorKind.getPrimaryKey()),
                        WorkflowSelectorKinds.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.Workflow */
    public Workflow getWorkflowByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new WorkflowPK(entityInstance.getEntityUniqueId());

        return workflowFactory.getEntityFromPK(entityPermission, pk);
    }

    public Workflow getWorkflowByEntityInstance(EntityInstance entityInstance) {
        return getWorkflowByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public Workflow getWorkflowByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getWorkflowByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public List<Workflow> getWorkflows() {
        var query = session.applyLimit(session.getDslContext()
                .select(Workflows.fields())
                .from(Workflows)
                .join(WorkflowDetails).onKey(WORKFLOWS_ACTIVE_DETAIL_FK)
                .orderBy(WorkflowDetails.SORT_ORDER, WorkflowDetails.WORKFLOW_NAME),
                WorkflowFactory.class);
        
        return workflowFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public List<Workflow> getWorkflowsBySelectorKind(SelectorKind selectorKind) {
        var query = session.applyLimit(session.getDslContext()
                .select(Workflows.fields())
                .from(Workflows)
                .join(WorkflowSelectorKinds).onKey(WORKFLOW_SELECTOR_KINDS_WORKFLOW_FK)
                .join(WorkflowDetails).onKey(WORKFLOWS_LAST_DETAIL_FK)
                .where(WorkflowSelectorKinds.SELECTOR_KIND.eq(selectorKind.getPrimaryKey()),
                        WorkflowSelectorKinds.THRU_TIME.eq(Session.MAX_TIME))
                .orderBy(WorkflowDetails.SORT_ORDER, WorkflowDetails.WORKFLOW_NAME),
                WorkflowFactory.class);

        return workflowFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public List<Workflow> getWorkflowsByEntityType(final EntityType entityType) {
        var query = session.applyLimit(session.getDslContext()
                .select(Workflows.fields())
                .from(Workflows)
                .join(WorkflowDetails).onKey(WORKFLOWS_ACTIVE_DETAIL_FK)
                .join(WorkflowEntityTypes).onKey(WORKFLOW_ENTITY_TYPES_WORKFLOW_FK)
                .where(WorkflowEntityTypes.ENTITY_TYPE.eq(entityType.getPrimaryKey()),
                        WorkflowEntityTypes.THRU_TIME.eq(Session.MAX_TIME))
                .orderBy(WorkflowDetails.SORT_ORDER, WorkflowDetails.WORKFLOW_NAME),
                WorkflowFactory.class);

        return workflowFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public Workflow getWorkflowByName(String workflowName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Workflows.fields())
                .from(Workflows)
                .join(WorkflowDetails).onKey(WORKFLOWS_ACTIVE_DETAIL_FK)
                .where(WorkflowDetails.WORKFLOW_NAME.eq(workflowName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return workflowFactory.getEntityFromQuery(entityPermission, query);
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
        return workflowTransferCache.getWorkflowTransfer(userVisit, workflow);
    }
    
    public List<WorkflowTransfer> getWorkflowTransfers(UserVisit userVisit, Collection<Workflow> workflows) {
        List<WorkflowTransfer> workflowTransfers = new ArrayList<>(workflows.size());
        
        workflows.forEach((workflow) ->
                workflowTransfers.add(workflowTransferCache.getWorkflowTransfer(userVisit, workflow))
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
            var workflow = workflowFactory.getEntityFromPK(EntityPermission.READ_WRITE, workflowDetailValue.getWorkflowPK());
            var workflowDetail = workflow.getActiveDetailForUpdate();
            
            workflowDetail.setThruTime(session.getStartTime());
            workflowDetail.store();

            var workflowPK = workflowDetail.getWorkflowPK();
            var workflowName = workflowDetailValue.getWorkflowName();
            var selectorTypePK = workflowDetailValue.getSelectorTypePK();
            var securityRoleGroupPK = workflowDetailValue.getSecurityRoleGroupPK();
            var sortOrder = workflowDetailValue.getSortOrder();
            
            workflowDetail = workflowDetailFactory.create(workflowPK, workflowName, selectorTypePK, securityRoleGroupPK,
                    sortOrder, session.getStartTime(), Session.MAX_TIME);
            
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
        workflowDetail.setThruTime(session.getStartTime());
        workflow.setActiveDetail(null);
        workflow.store();
        
        sendEvent(workflow.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Workflow Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected WorkflowDescriptionFactory workflowDescriptionFactory;

    @Inject
    WorkflowDescriptionTransferCache workflowDescriptionTransferCache;

    public WorkflowDescription createWorkflowDescription(Workflow workflow, Language language, String description, BasePK createdBy) {
        var workflowDescription = workflowDescriptionFactory.create(workflow, language,
                description, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(workflow.getPrimaryKey(), EventTypes.MODIFY, workflowDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return workflowDescription;
    }
    
    private WorkflowDescription getWorkflowDescription(Workflow workflow, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(WorkflowDescriptions.fields())
                .from(WorkflowDescriptions)
                .where(WorkflowDescriptions.WORKFLOW.eq(workflow.getPrimaryKey()),
                        WorkflowDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        WorkflowDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return workflowDescriptionFactory.getEntityFromQuery(entityPermission, query);
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
    
    private List<WorkflowDescription> getWorkflowDescriptionsByWorkflow(Workflow workflow, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(WorkflowDescriptions.fields())
                    .from(WorkflowDescriptions)
                    .join(Languages).onKey(WORKFLOW_DESCRIPTIONS_LANGUAGE_FK)
                    .where(WorkflowDescriptions.WORKFLOW.eq(workflow.getPrimaryKey()),
                            WorkflowDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    WorkflowDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(WorkflowDescriptions.fields())
                    .from(WorkflowDescriptions)
                    .where(WorkflowDescriptions.WORKFLOW.eq(workflow.getPrimaryKey()),
                            WorkflowDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return workflowDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
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
        return workflowDescriptionTransferCache.getWorkflowDescriptionTransfer(userVisit, workflowDescription);
    }
    
    public List<WorkflowDescriptionTransfer> getWorkflowDescriptionTransfersByWorkflow(UserVisit userVisit, Workflow workflow) {
        var workflowDescriptions = getWorkflowDescriptionsByWorkflow(workflow);
        List<WorkflowDescriptionTransfer> workflowDescriptionTransfers = new ArrayList<>(workflowDescriptions.size());
        
        workflowDescriptions.forEach((workflowDescription) ->
                workflowDescriptionTransfers.add(workflowDescriptionTransferCache.getWorkflowDescriptionTransfer(userVisit, workflowDescription))
        );
        
        return workflowDescriptionTransfers;
    }
    
    public void updateWorkflowDescriptionFromValue(WorkflowDescriptionValue workflowDescriptionValue, BasePK updatedBy) {
        if(workflowDescriptionValue.hasBeenModified()) {
            var workflowDescription = workflowDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE, workflowDescriptionValue.getPrimaryKey());
            
            workflowDescription.setThruTime(session.getStartTime());
            workflowDescription.store();

            var workflow = workflowDescription.getWorkflow();
            var language = workflowDescription.getLanguage();
            var description = workflowDescriptionValue.getDescription();
            
            workflowDescription = workflowDescriptionFactory.create(workflow, language, description,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(workflow.getPrimaryKey(), EventTypes.MODIFY, workflowDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteWorkflowDescription(WorkflowDescription workflowDescription, BasePK deletedBy) {
        workflowDescription.setThruTime(session.getStartTime());
        
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

    @Inject
    protected WorkflowStepDetailFactory workflowStepDetailFactory;

    @Inject
    protected WorkflowStepFactory workflowStepFactory;

    @Inject
    WorkflowStepTransferCache workflowStepTransferCache;

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

        var workflowStep = workflowStepFactory.create();
        var workflowStepDetail = workflowStepDetailFactory.create(workflowStep, workflow,
                workflowStepName, workflowStepType, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        workflowStep = workflowStepFactory.getEntityFromPK(EntityPermission.READ_WRITE, workflowStep.getPrimaryKey());
        workflowStep.setActiveDetail(workflowStepDetail);
        workflowStep.setLastDetail(workflowStepDetail);
        workflowStep.store();
        
        sendEvent(workflow.getPrimaryKey(), EventTypes.MODIFY, workflowStep.getPrimaryKey(), null, createdBy);
        
        return workflowStep;
    }

    public long countWorkflowStepsByWorkflow(Workflow workflow) {
        return session.getDslContext()
                .selectCount()
                .from(WorkflowSteps)
                .join(WorkflowStepDetails).onKey(WORKFLOW_STEPS_ACTIVE_DETAIL_FK)
                .where(WorkflowStepDetails.WORKFLOW.eq(workflow.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.WorkflowStep */
    public WorkflowStep getWorkflowStepByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new WorkflowStepPK(entityInstance.getEntityUniqueId());

        return workflowStepFactory.getEntityFromPK(entityPermission, pk);
    }

    public WorkflowStep getWorkflowStepByEntityInstance(EntityInstance entityInstance) {
        return getWorkflowStepByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public WorkflowStep getWorkflowStepByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getWorkflowStepByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public WorkflowStep getDefaultWorkflowStep(Workflow workflow, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(WorkflowSteps.fields())
                .from(WorkflowSteps)
                .join(WorkflowStepDetails).onKey(WORKFLOW_STEPS_ACTIVE_DETAIL_FK)
                .where(WorkflowStepDetails.WORKFLOW.eq(workflow.getPrimaryKey()),
                        WorkflowStepDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return workflowStepFactory.getEntityFromQuery(entityPermission, query);
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
    
    private List<WorkflowStep> getWorkflowStepsByWorkflow(Workflow workflow, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(WorkflowSteps.fields())
                .from(WorkflowSteps)
                .join(WorkflowStepDetails).onKey(WORKFLOW_STEPS_ACTIVE_DETAIL_FK)
                .where(WorkflowStepDetails.WORKFLOW.eq(workflow.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(WorkflowStepDetails.SORT_ORDER, WorkflowStepDetails.WORKFLOW_STEP_NAME),
                    WorkflowStepFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return workflowStepFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<WorkflowStep> getWorkflowStepsByWorkflow(Workflow workflow) {
        return getWorkflowStepsByWorkflow(workflow, EntityPermission.READ_ONLY);
    }
    
    public List<WorkflowStep> getWorkflowStepsByWorkflowForUpdate(Workflow workflow) {
        return getWorkflowStepsByWorkflow(workflow, EntityPermission.READ_WRITE);
    }
    
    public WorkflowStep getWorkflowStepByName(Workflow workflow, String workflowStepName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(WorkflowSteps.fields())
                .from(WorkflowSteps)
                .join(WorkflowStepDetails).onKey(WORKFLOW_STEPS_ACTIVE_DETAIL_FK)
                .where(WorkflowStepDetails.WORKFLOW.eq(workflow.getPrimaryKey()),
                        WorkflowStepDetails.WORKFLOW_STEP_NAME.eq(workflowStepName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return workflowStepFactory.getEntityFromQuery(entityPermission, query);
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
        return workflowStepTransferCache.getWorkflowStepTransfer(userVisit, workflowStep);
    }
    
    public List<WorkflowStepTransfer> getWorkflowStepTransfers(UserVisit userVisit, Collection<WorkflowStep> workflowSteps) {
        List<WorkflowStepTransfer> workflowStepTransfers = new ArrayList<>(workflowSteps.size());
        
        workflowSteps.forEach((workflowStep) ->
                workflowStepTransfers.add(workflowStepTransferCache.getWorkflowStepTransfer(userVisit, workflowStep))
        );
        
        return workflowStepTransfers;
    }
    
    public List<WorkflowStepTransfer> getWorkflowStepTransfersByWorkflow(UserVisit userVisit, Workflow workflow) {
        return getWorkflowStepTransfers(userVisit, getWorkflowStepsByWorkflow(workflow));
    }

    private void updateWorkflowStepFromValue(WorkflowStepDetailValue workflowStepDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(workflowStepDetailValue.hasBeenModified()) {
            var workflowStep = workflowStepFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    workflowStepDetailValue.getWorkflowStepPK());
            var workflowStepDetail = workflowStep.getActiveDetailForUpdate();
            
            workflowStepDetail.setThruTime(session.getStartTime());
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
            
            workflowStepDetail = workflowStepDetailFactory.create(workflowStepPK, workflowPK, workflowStepName, workflowStepTypePK,
                    isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            workflowStep.setActiveDetail(workflowStepDetail);
            workflowStep.setLastDetail(workflowStepDetail);
            
            sendEvent(workflowStep.getLastDetail().getWorkflowPK(), EventTypes.MODIFY, workflowStep.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void updateWorkflowStepFromValue(WorkflowStepDetailValue workflowStepDetailValue, BasePK updatedBy) {
        updateWorkflowStepFromValue(workflowStepDetailValue, true, updatedBy);
    }
    
    private void deleteWorkflowStep(WorkflowStep workflowStep, boolean checkDefault, BasePK deletedBy) {
        
        workRequirementControl.deleteWorkRequirementTypesByWorkflowStep(workflowStep, deletedBy);
        deleteWorkflowEntityStatusesByWorkflowStep(workflowStep, deletedBy);
        deleteWorkflowEntranceStepsByWorkflowStep(workflowStep, deletedBy);
        deleteWorkflowDestinationStepsByWorkflowStep(workflowStep, deletedBy);
        deleteWorkflowStepDescriptionsByWorkflowStep(workflowStep, deletedBy);

        var workflowStepDetail = workflowStep.getLastDetailForUpdate();
        workflowStepDetail.setThruTime(session.getStartTime());
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

    @Inject
    protected WorkflowStepDescriptionFactory workflowStepDescriptionFactory;

    @Inject
    WorkflowStepDescriptionTransferCache workflowStepDescriptionTransferCache;

    public WorkflowStepDescription createWorkflowStepDescription(WorkflowStep workflowStep, Language language, String description,
            BasePK createdBy) {
        var workflowStepDescription = workflowStepDescriptionFactory.create(workflowStep,
                language, description, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(workflowStep.getLastDetail().getWorkflowPK(), EventTypes.MODIFY, workflowStepDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return workflowStepDescription;
    }
    
    private WorkflowStepDescription getWorkflowStepDescription(WorkflowStep workflowStep, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(WorkflowStepDescriptions.fields())
                .from(WorkflowStepDescriptions)
                .where(WorkflowStepDescriptions.WORKFLOW_STEP.eq(workflowStep.getPrimaryKey()),
                        WorkflowStepDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        WorkflowStepDescriptions.THRU_TIME.eq(Session.MAX_TIME));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };
            
        return workflowStepDescriptionFactory.getEntityFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(WorkflowStepDescriptions.fields())
                    .from(WorkflowStepDescriptions)
                    .join(Languages).onKey(WORKFLOW_STEP_DESCRIPTIONS_LANGUAGE_FK)
                    .where(WorkflowStepDescriptions.WORKFLOW_STEP.eq(workflowStep.getPrimaryKey()),
                            WorkflowStepDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    WorkflowStepDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(WorkflowStepDescriptions.fields())
                    .from(WorkflowStepDescriptions)
                    .where(WorkflowStepDescriptions.WORKFLOW_STEP.eq(workflowStep.getPrimaryKey()),
                            WorkflowStepDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return workflowStepDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
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
        return workflowStepDescriptionTransferCache.getWorkflowStepDescriptionTransfer(userVisit, workflowStepDescription);
    }
    
    public List<WorkflowStepDescriptionTransfer> getWorkflowStepDescriptionTransfersByWorkflowStep(UserVisit userVisit, WorkflowStep workflowStep) {
        var workflowStepDescriptions = getWorkflowStepDescriptionsByWorkflowStep(workflowStep);
        List<WorkflowStepDescriptionTransfer> workflowStepDescriptionTransfers = new ArrayList<>(workflowStepDescriptions.size());
        
        workflowStepDescriptions.forEach((workflowStepDescription) ->
                workflowStepDescriptionTransfers.add(workflowStepDescriptionTransferCache.getWorkflowStepDescriptionTransfer(userVisit, workflowStepDescription))
        );
        
        return workflowStepDescriptionTransfers;
    }
    
    public void updateWorkflowStepDescriptionFromValue(WorkflowStepDescriptionValue workflowStepDescriptionValue, BasePK updatedBy) {
        if(workflowStepDescriptionValue.hasBeenModified()) {
            var workflowStepDescription = workflowStepDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE, workflowStepDescriptionValue.getPrimaryKey());
            
            workflowStepDescription.setThruTime(session.getStartTime());
            workflowStepDescription.store();

            var workflowStep = workflowStepDescription.getWorkflowStep();
            var language = workflowStepDescription.getLanguage();
            var description = workflowStepDescriptionValue.getDescription();
            
            workflowStepDescription = workflowStepDescriptionFactory.create(workflowStep, language, description,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(workflowStep.getLastDetail().getWorkflowPK(), EventTypes.MODIFY, workflowStepDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteWorkflowStepDescription(WorkflowStepDescription workflowStepDescription, BasePK deletedBy) {
        workflowStepDescription.setThruTime(session.getStartTime());
        
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

    @Inject
    protected WorkflowEntityTypeFactory workflowEntityTypeFactory;

    @Inject
    WorkflowEntityTypeTransferCache workflowEntityTypeTransferCache;

    public WorkflowEntityType createWorkflowEntityType(Workflow workflow, EntityType entityType, BasePK createdBy) {
        var workflowEntityType = workflowEntityTypeFactory.create(workflow, entityType,
                session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(workflow.getPrimaryKey(), EventTypes.MODIFY, workflowEntityType.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return workflowEntityType;
    }

    public long countWorkflowEntityTypesByWorkflow(Workflow workflow) {
        return session.getDslContext()
                .selectCount()
                .from(WorkflowEntityTypes)
                .where(WorkflowEntityTypes.WORKFLOW.eq(workflow.getPrimaryKey()),
                        WorkflowEntityTypes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countWorkflowEntityTypesByEntityType(EntityType entityType) {
        return session.getDslContext()
                .selectCount()
                .from(WorkflowEntityTypes)
                .where(WorkflowEntityTypes.ENTITY_TYPE.eq(entityType.getPrimaryKey()),
                        WorkflowEntityTypes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public boolean workflowEntityTypeExists(final Workflow workflow, final EntityType entityType) {
        return session.getDslContext()
                .selectCount()
                .from(WorkflowEntityTypes)
                .where(WorkflowEntityTypes.WORKFLOW.eq(workflow.getPrimaryKey()),
                        WorkflowEntityTypes.ENTITY_TYPE.eq(entityType.getPrimaryKey()),
                        WorkflowEntityTypes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L) == 1L;
    }

    private WorkflowEntityType getWorkflowEntityType(Workflow workflow, EntityType entityType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(WorkflowEntityTypes.fields())
                .from(WorkflowEntityTypes)
                .where(WorkflowEntityTypes.WORKFLOW.eq(workflow.getPrimaryKey()),
                        WorkflowEntityTypes.ENTITY_TYPE.eq(entityType.getPrimaryKey()),
                        WorkflowEntityTypes.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return workflowEntityTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public WorkflowEntityType getWorkflowEntityType(Workflow workflow, EntityType entityType) {
        return getWorkflowEntityType(workflow, entityType, EntityPermission.READ_ONLY);
    }

    public WorkflowEntityType getWorkflowEntityTypeForUpdate(Workflow workflow, EntityType entityType) {
        return getWorkflowEntityType(workflow, entityType, EntityPermission.READ_WRITE);
    }

    private List<WorkflowEntityType> getWorkflowEntityTypesByWorkflow(Workflow workflow, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(WorkflowEntityTypes.fields())
                    .from(WorkflowEntityTypes)
                    .join(EntityTypes).onKey(WORKFLOW_ENTITY_TYPES_ENTITY_TYPE_FK)
                    .join(EntityTypeDetails).onKey(ENTITY_TYPES_ACTIVE_DETAIL_FK)
                    .where(WorkflowEntityTypes.WORKFLOW.eq(workflow.getPrimaryKey()),
                            WorkflowEntityTypes.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(EntityTypeDetails.SORT_ORDER, EntityTypeDetails.ENTITY_TYPE_NAME),
                    WorkflowEntityTypeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(WorkflowEntityTypes.fields())
                    .from(WorkflowEntityTypes)
                    .where(WorkflowEntityTypes.WORKFLOW.eq(workflow.getPrimaryKey()),
                            WorkflowEntityTypes.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return workflowEntityTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<WorkflowEntityType> getWorkflowEntityTypesByWorkflow(Workflow workflow) {
        return getWorkflowEntityTypesByWorkflow(workflow, EntityPermission.READ_ONLY);
    }
    
    public List<WorkflowEntityType> getWorkflowEntityTypesByWorkflowForUpdate(Workflow workflow) {
        return getWorkflowEntityTypesByWorkflow(workflow, EntityPermission.READ_WRITE);
    }
    
    private List<WorkflowEntityType> getWorkflowEntityTypesByEntityType(EntityType entityType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(WorkflowEntityTypes.fields())
                    .from(WorkflowEntityTypes)
                    .join(Workflows).onKey(WORKFLOW_ENTITY_TYPES_WORKFLOW_FK)
                    .join(WorkflowDetails).onKey(WORKFLOWS_LAST_DETAIL_FK)
                    .where(WorkflowEntityTypes.ENTITY_TYPE.eq(entityType.getPrimaryKey()),
                            WorkflowEntityTypes.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(WorkflowDetails.SORT_ORDER, WorkflowDetails.WORKFLOW_NAME),
                    WorkflowEntityTypeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(WorkflowEntityTypes.fields())
                    .from(WorkflowEntityTypes)
                    .where(WorkflowEntityTypes.ENTITY_TYPE.eq(entityType.getPrimaryKey()),
                            WorkflowEntityTypes.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return workflowEntityTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<WorkflowEntityType> getWorkflowEntityTypesByEntityType(EntityType entityType) {
        return getWorkflowEntityTypesByEntityType(entityType, EntityPermission.READ_ONLY);
    }

    public List<WorkflowEntityType> getWorkflowEntityTypesByEntityTypeForUpdate(EntityType entityType) {
        return getWorkflowEntityTypesByEntityType(entityType, EntityPermission.READ_WRITE);
    }

    public WorkflowEntityTypeTransfer getWorkflowEntityTypeTransfer(UserVisit userVisit, WorkflowEntityType workflowEntityType) {
        return workflowEntityTypeTransferCache.getWorkflowEntityTypeTransfer(userVisit, workflowEntityType);
    }
    
    public List<WorkflowEntityTypeTransfer> getWorkflowEntityTypeTransfers(UserVisit userVisit, Collection<WorkflowEntityType> workflowEntityTypes) {
        List<WorkflowEntityTypeTransfer> workflowEntityTypeTransfers = new ArrayList<>(workflowEntityTypes.size());

        workflowEntityTypes.forEach((workflowEntityType) ->
                workflowEntityTypeTransfers.add(workflowEntityTypeTransferCache.getWorkflowEntityTypeTransfer(userVisit, workflowEntityType))
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
        workflowEntityType.setThruTime(session.getStartTime());
        
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

    @Inject
    protected WorkflowEntranceDetailFactory workflowEntranceDetailFactory;

    @Inject
    protected WorkflowEntranceFactory workflowEntranceFactory;

    @Inject
    WorkflowEntranceTransferCache workflowEntranceTransferCache;

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

        var workflowEntrance = workflowEntranceFactory.create();
        var workflowEntranceDetail = workflowEntranceDetailFactory.create(workflowEntrance,
                workflow, workflowEntranceName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        workflowEntrance = workflowEntranceFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                workflowEntrance.getPrimaryKey());
        workflowEntrance.setActiveDetail(workflowEntranceDetail);
        workflowEntrance.setLastDetail(workflowEntranceDetail);
        workflowEntrance.store();
        
        sendEvent(workflow.getPrimaryKey(), EventTypes.MODIFY, workflowEntrance.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return workflowEntrance;
    }

    public long countWorkflowEntrancesByWorkflow(Workflow workflow) {
        return session.getDslContext()
                .selectCount()
                .from(WorkflowEntrances)
                .join(WorkflowEntranceDetails).onKey(WORKFLOW_ENTRANCES_ACTIVE_DETAIL_FK)
                .where(WorkflowEntranceDetails.WORKFLOW.eq(workflow.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.WorkflowEntrance */
    public WorkflowEntrance getWorkflowEntranceByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new WorkflowEntrancePK(entityInstance.getEntityUniqueId());

        return workflowEntranceFactory.getEntityFromPK(entityPermission, pk);
    }

    public WorkflowEntrance getWorkflowEntranceByEntityInstance(EntityInstance entityInstance) {
        return getWorkflowEntranceByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public WorkflowEntrance getWorkflowEntranceByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getWorkflowEntranceByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public WorkflowEntrance getDefaultWorkflowEntrance(Workflow workflow, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(WorkflowEntrances.fields())
                .from(WorkflowEntrances)
                .join(WorkflowEntranceDetails).onKey(WORKFLOW_ENTRANCES_ACTIVE_DETAIL_FK)
                .where(WorkflowEntranceDetails.WORKFLOW.eq(workflow.getPrimaryKey()),
                        WorkflowEntranceDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return workflowEntranceFactory.getEntityFromQuery(entityPermission, query);
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
    
    private List<WorkflowEntrance> getWorkflowEntrancesByWorkflow(Workflow workflow, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(WorkflowEntrances.fields())
                .from(WorkflowEntrances)
                .join(WorkflowEntranceDetails).onKey(WORKFLOW_ENTRANCES_ACTIVE_DETAIL_FK)
                .where(WorkflowEntranceDetails.WORKFLOW.eq(workflow.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(WorkflowEntranceDetails.SORT_ORDER, WorkflowEntranceDetails.WORKFLOW_ENTRANCE_NAME),
                    WorkflowEntranceFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return workflowEntranceFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<WorkflowEntrance> getWorkflowEntrancesByWorkflow(Workflow workflow) {
        return getWorkflowEntrancesByWorkflow(workflow, EntityPermission.READ_ONLY);
    }
    
    public List<WorkflowEntrance> getWorkflowEntrancesByWorkflowForUpdate(Workflow workflow) {
        return getWorkflowEntrancesByWorkflow(workflow, EntityPermission.READ_WRITE);
    }
    
    public WorkflowEntrance getWorkflowEntranceByName(Workflow workflow, String workflowEntranceName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(WorkflowEntrances.fields())
                .from(WorkflowEntrances)
                .join(WorkflowEntranceDetails).onKey(WORKFLOW_ENTRANCES_ACTIVE_DETAIL_FK)
                .where(WorkflowEntranceDetails.WORKFLOW.eq(workflow.getPrimaryKey()),
                        WorkflowEntranceDetails.WORKFLOW_ENTRANCE_NAME.eq(workflowEntranceName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return workflowEntranceFactory.getEntityFromQuery(entityPermission, query);
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
            if(!values.isEmpty()) {
                defaultValue = values.get(1);
            }
        }
        
        baseWorkflowChoicesBean.setLabels(labels);
        baseWorkflowChoicesBean.setValues(values);
        baseWorkflowChoicesBean.setDefaultValue(defaultValue);
        
        return baseWorkflowChoicesBean;
    }
    
    public WorkflowEntranceTransfer getWorkflowEntranceTransfer(UserVisit userVisit, WorkflowEntrance workflowEntrance) {
        return workflowEntranceTransferCache.getWorkflowEntranceTransfer(userVisit, workflowEntrance);
    }
    
    public List<WorkflowEntranceTransfer> getWorkflowEntranceTransfers(UserVisit userVisit, Collection<WorkflowEntrance> workflowEntrances) {
        List<WorkflowEntranceTransfer> workflowEntranceTransfers = new ArrayList<>(workflowEntrances.size());
        
        workflowEntrances.forEach((workflowEntrance) ->
                workflowEntranceTransfers.add(workflowEntranceTransferCache.getWorkflowEntranceTransfer(userVisit, workflowEntrance))
        );
        
        return workflowEntranceTransfers;
    }
    
    public List<WorkflowEntranceTransfer> getWorkflowEntranceTransfersByWorkflow(UserVisit userVisit, Workflow workflow) {
        return getWorkflowEntranceTransfers(userVisit, getWorkflowEntrancesByWorkflow(workflow));
    }

    private void updateWorkflowEntranceFromValue(WorkflowEntranceDetailValue workflowEntranceDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(workflowEntranceDetailValue.hasBeenModified()) {
            var workflowEntrance = workflowEntranceFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    workflowEntranceDetailValue.getWorkflowEntrancePK());
            var workflowEntranceDetail = workflowEntrance.getActiveDetailForUpdate();
            
            workflowEntranceDetail.setThruTime(session.getStartTime());
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
            
            workflowEntranceDetail = workflowEntranceDetailFactory.create(workflowEntrancePK, workflowPK, workflowEntranceName,
                    isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
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
        workflowEntranceDetail.setThruTime(session.getStartTime());
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

    @Inject
    protected WorkflowEntranceDescriptionFactory workflowEntranceDescriptionFactory;

    @Inject
    WorkflowEntranceDescriptionTransferCache workflowEntranceDescriptionTransferCache;

    public WorkflowEntranceDescription createWorkflowEntranceDescription(WorkflowEntrance workflowEntrance, Language language,
            String description, BasePK createdBy) {
        var workflowEntranceDescription = workflowEntranceDescriptionFactory.create(
                workflowEntrance, language, description, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(workflowEntrance.getLastDetail().getWorkflowPK(), EventTypes.MODIFY, workflowEntranceDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return workflowEntranceDescription;
    }
    
    private WorkflowEntranceDescription getWorkflowEntranceDescription(WorkflowEntrance workflowEntrance, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(WorkflowEntranceDescriptions.fields())
                .from(WorkflowEntranceDescriptions)
                .where(WorkflowEntranceDescriptions.WORKFLOW_ENTRANCE.eq(workflowEntrance.getPrimaryKey()),
                        WorkflowEntranceDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        WorkflowEntranceDescriptions.THRU_TIME.eq(Session.MAX_TIME));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };
            
        return workflowEntranceDescriptionFactory.getEntityFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(WorkflowEntranceDescriptions.fields())
                    .from(WorkflowEntranceDescriptions)
                    .join(Languages).onKey(WORKFLOW_ENTRANCE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(WorkflowEntranceDescriptions.WORKFLOW_ENTRANCE.eq(workflowEntrance.getPrimaryKey()),
                            WorkflowEntranceDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    WorkflowEntranceDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(WorkflowEntranceDescriptions.fields())
                    .from(WorkflowEntranceDescriptions)
                    .where(WorkflowEntranceDescriptions.WORKFLOW_ENTRANCE.eq(workflowEntrance.getPrimaryKey()),
                            WorkflowEntranceDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return workflowEntranceDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
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
        return workflowEntranceDescriptionTransferCache.getWorkflowEntranceDescriptionTransfer(userVisit, workflowEntranceDescription);
    }
    
    public List<WorkflowEntranceDescriptionTransfer> getWorkflowEntranceDescriptionTransfersByWorkflowEntrance(UserVisit userVisit, WorkflowEntrance workflowEntrance) {
        var workflowEntranceDescriptions = getWorkflowEntranceDescriptionsByWorkflowEntrance(workflowEntrance);
        List<WorkflowEntranceDescriptionTransfer> workflowEntranceDescriptionTransfers = new ArrayList<>(workflowEntranceDescriptions.size());
        
        workflowEntranceDescriptions.forEach((workflowEntranceDescription) ->
                workflowEntranceDescriptionTransfers.add(workflowEntranceDescriptionTransferCache.getWorkflowEntranceDescriptionTransfer(userVisit, workflowEntranceDescription))
        );
        
        return workflowEntranceDescriptionTransfers;
    }
    
    public void updateWorkflowEntranceDescriptionFromValue(WorkflowEntranceDescriptionValue workflowEntranceDescriptionValue, BasePK updatedBy) {
        if(workflowEntranceDescriptionValue.hasBeenModified()) {
            var workflowEntranceDescription = workflowEntranceDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE, workflowEntranceDescriptionValue.getPrimaryKey());
            
            workflowEntranceDescription.setThruTime(session.getStartTime());
            workflowEntranceDescription.store();

            var workflowEntrance = workflowEntranceDescription.getWorkflowEntrance();
            var language = workflowEntranceDescription.getLanguage();
            var description = workflowEntranceDescriptionValue.getDescription();
            
            workflowEntranceDescription = workflowEntranceDescriptionFactory.create(workflowEntrance, language, description,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(workflowEntrance.getLastDetail().getWorkflowPK(), EventTypes.MODIFY, workflowEntranceDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteWorkflowEntranceDescription(WorkflowEntranceDescription workflowEntranceDescription, BasePK deletedBy) {
        workflowEntranceDescription.setThruTime(session.getStartTime());
        
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

    @Inject
    protected WorkflowEntranceSelectorFactory workflowEntranceSelectorFactory;

    @Inject
    WorkflowEntranceSelectorTransferCache workflowEntranceSelectorTransferCache;

    public WorkflowEntranceSelector createWorkflowEntranceSelector(WorkflowEntrance workflowEntrance, Selector selector,
            BasePK createdBy) {
        var workflowEntranceSelector = workflowEntranceSelectorFactory.create(workflowEntrance,
                selector, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(workflowEntrance.getLastDetail().getWorkflowPK(), EventTypes.MODIFY, workflowEntranceSelector.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return workflowEntranceSelector;
    }

    public long countWorkflowEntranceSelectorsByWorkflowEntrance(WorkflowEntrance workflowEntrance) {
        return session.getDslContext()
                .selectCount()
                .from(WorkflowEntranceSelectors)
                .where(WorkflowEntranceSelectors.WORKFLOW_ENTRANCE.eq(workflowEntrance.getPrimaryKey()),
                        WorkflowEntranceSelectors.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countWorkflowEntranceSelectorsBySelector(Selector selector) {
        return session.getDslContext()
                .selectCount()
                .from(WorkflowEntranceSelectors)
                .where(WorkflowEntranceSelectors.SELECTOR.eq(selector.getPrimaryKey()),
                        WorkflowEntranceSelectors.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<WorkflowEntranceSelector> getWorkflowEntranceSelectorsBySelectorForUpdate(Selector selector, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(WorkflowEntranceSelectors.fields())
                    .from(WorkflowEntranceSelectors)
                    .join(WorkflowEntrances).onKey(WORKFLOW_ENTRANCE_SELECTORS_WORKFLOW_ENTRANCE_FK)
                    .join(WorkflowEntranceDetails).onKey(WORKFLOW_ENTRANCES_LAST_DETAIL_FK)
                    .join(Workflows).onKey(WORKFLOW_ENTRANCE_DETAILS_WORKFLOW_FK)
                    .join(WorkflowDetails).onKey(WORKFLOWS_LAST_DETAIL_FK)
                    .where(WorkflowEntranceSelectors.SELECTOR.eq(selector.getPrimaryKey()),
                            WorkflowEntranceSelectors.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(WorkflowEntranceDetails.SORT_ORDER, WorkflowEntranceDetails.WORKFLOW_ENTRANCE_NAME, WorkflowDetails.SORT_ORDER, WorkflowDetails.WORKFLOW_NAME),
                    WorkflowEntranceSelectorFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(WorkflowEntranceSelectors.fields())
                    .from(WorkflowEntranceSelectors)
                    .where(WorkflowEntranceSelectors.SELECTOR.eq(selector.getPrimaryKey()),
                            WorkflowEntranceSelectors.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return workflowEntranceSelectorFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<WorkflowEntranceSelector> getWorkflowEntranceSelectorsBySelector(Selector selector) {
        return getWorkflowEntranceSelectorsBySelectorForUpdate(selector, EntityPermission.READ_ONLY);
    }
    
    public List<WorkflowEntranceSelector> getWorkflowEntranceSelectorsBySelectorForUpdate(Selector selector) {
        return getWorkflowEntranceSelectorsBySelectorForUpdate(selector, EntityPermission.READ_WRITE);
    }
    
    private List<WorkflowEntranceSelector> getWorkflowEntranceSelectorsByWorkflowEntrance(WorkflowEntrance workflowEntrance,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(WorkflowEntranceSelectors.fields())
                    .from(WorkflowEntranceSelectors)
                    .join(Selectors).onKey(WORKFLOW_ENTRANCE_SELECTORS_SELECTOR_FK)
                    .join(SelectorDetails).onKey(SELECTORS_LAST_DETAIL_FK)
                    .join(SelectorTypes).onKey(SELECTOR_DETAILS_SELECTOR_TYPE_FK)
                    .join(SelectorTypeDetails).onKey(SELECTOR_TYPES_LAST_DETAIL_FK)
                    .join(SelectorKinds).onKey(SELECTOR_TYPE_DETAILS_SELECTOR_KIND_FK)
                    .join(SelectorKindDetails).onKey(SELECTOR_KINDS_LAST_DETAIL_FK)
                    .where(WorkflowEntranceSelectors.WORKFLOW_ENTRANCE.eq(workflowEntrance.getPrimaryKey()),
                            WorkflowEntranceSelectors.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(SelectorDetails.SORT_ORDER, SelectorDetails.SELECTOR_NAME, SelectorTypeDetails.SORT_ORDER, SelectorTypeDetails.SELECTOR_TYPE_NAME, SelectorKindDetails.SORT_ORDER, SelectorKindDetails.SELECTOR_KIND_NAME),
                    WorkflowEntranceSelectorFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(WorkflowEntranceSelectors.fields())
                    .from(WorkflowEntranceSelectors)
                    .where(WorkflowEntranceSelectors.WORKFLOW_ENTRANCE.eq(workflowEntrance.getPrimaryKey()),
                            WorkflowEntranceSelectors.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return workflowEntranceSelectorFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<WorkflowEntranceSelector> getWorkflowEntranceSelectorsByWorkflowEntrance(WorkflowEntrance workflowEntrance) {
        return getWorkflowEntranceSelectorsByWorkflowEntrance(workflowEntrance, EntityPermission.READ_ONLY);
    }
    
    public List<WorkflowEntranceSelector> getWorkflowEntranceSelectorsByWorkflowEntranceForUpdate(WorkflowEntrance workflowEntrance) {
        return getWorkflowEntranceSelectorsByWorkflowEntrance(workflowEntrance, EntityPermission.READ_WRITE);
    }
    
    private List<WorkflowEntranceSelector> getWorkflowEntranceSelectorsByWorkflow(Workflow workflow, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(WorkflowEntranceSelectors.fields())
                    .from(WorkflowEntranceSelectors)
                    .join(WorkflowEntrances).onKey(WORKFLOW_ENTRANCE_SELECTORS_WORKFLOW_ENTRANCE_FK)
                    .join(WorkflowEntranceDetails).onKey(WORKFLOW_ENTRANCES_ACTIVE_DETAIL_FK)
                    .join(Selectors).onKey(WORKFLOW_ENTRANCE_SELECTORS_SELECTOR_FK)
                    .join(SelectorDetails).onKey(SELECTORS_LAST_DETAIL_FK)
                    .join(SelectorTypes).onKey(SELECTOR_DETAILS_SELECTOR_TYPE_FK)
                    .join(SelectorTypeDetails).onKey(SELECTOR_TYPES_LAST_DETAIL_FK)
                    .join(SelectorKinds).onKey(SELECTOR_TYPE_DETAILS_SELECTOR_KIND_FK)
                    .join(SelectorKindDetails).onKey(SELECTOR_KINDS_LAST_DETAIL_FK)
                    .where(WorkflowEntranceDetails.WORKFLOW.eq(workflow.getPrimaryKey()),
                            WorkflowEntranceSelectors.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(WorkflowEntranceDetails.SORT_ORDER, WorkflowEntranceDetails.WORKFLOW_ENTRANCE_NAME, SelectorDetails.SORT_ORDER, SelectorDetails.SELECTOR_NAME, SelectorTypeDetails.SORT_ORDER, SelectorTypeDetails.SELECTOR_TYPE_NAME, SelectorKindDetails.SORT_ORDER, SelectorKindDetails.SELECTOR_KIND_NAME),
                    WorkflowEntranceSelectorFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(WorkflowEntranceSelectors.fields())
                    .from(WorkflowEntranceSelectors)
                    .join(WorkflowEntrances).onKey(WORKFLOW_ENTRANCE_SELECTORS_WORKFLOW_ENTRANCE_FK)
                    .join(WorkflowEntranceDetails).onKey(WORKFLOW_ENTRANCES_ACTIVE_DETAIL_FK)
                    .where(WorkflowEntranceDetails.WORKFLOW.eq(workflow.getPrimaryKey()),
                            WorkflowEntranceSelectors.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return workflowEntranceSelectorFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<WorkflowEntranceSelector> getWorkflowEntranceSelectorsByWorkflow(Workflow workflow) {
        return getWorkflowEntranceSelectorsByWorkflow(workflow, EntityPermission.READ_ONLY);
    }
    
    public List<WorkflowEntranceSelector> getWorkflowEntranceSelectorsByWorkflowForUpdate(Workflow workflow) {
        return getWorkflowEntranceSelectorsByWorkflow(workflow, EntityPermission.READ_WRITE);
    }
    
    public WorkflowEntranceSelector getWorkflowEntranceSelector(WorkflowEntrance workflowEntrance, Selector selector,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(WorkflowEntranceSelectors.fields())
                .from(WorkflowEntranceSelectors)
                .where(WorkflowEntranceSelectors.WORKFLOW_ENTRANCE.eq(workflowEntrance.getPrimaryKey()),
                        WorkflowEntranceSelectors.SELECTOR.eq(selector.getPrimaryKey()),
                        WorkflowEntranceSelectors.THRU_TIME.eq(Session.MAX_TIME));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };
            
        return workflowEntranceSelectorFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public WorkflowEntranceSelector getWorkflowEntranceSelector(WorkflowEntrance workflowEntrance, Selector selector) {
        return getWorkflowEntranceSelector(workflowEntrance, selector, EntityPermission.READ_ONLY);
    }
    
    public WorkflowEntranceSelector getWorkflowEntranceSelectorForUpdate(WorkflowEntrance workflowEntrance, Selector selector) {
        return getWorkflowEntranceSelector(workflowEntrance, selector, EntityPermission.READ_WRITE);
    }
    
    public WorkflowEntranceSelectorTransfer getWorkflowEntranceSelectorTransfer(UserVisit userVisit, WorkflowEntranceSelector workflowEntranceSelector) {
        return workflowEntranceSelectorTransferCache.getWorkflowEntranceSelectorTransfer(userVisit, workflowEntranceSelector);
    }
    
    public List<WorkflowEntranceSelectorTransfer> getWorkflowEntranceSelectorTransfers(UserVisit userVisit, Collection<WorkflowEntranceSelector> workflowEntranceSelectors) {
        List<WorkflowEntranceSelectorTransfer> workflowEntranceSelectorTransfers = new ArrayList<>(workflowEntranceSelectors.size());
        
        workflowEntranceSelectors.forEach((workflowEntranceSelector) ->
                workflowEntranceSelectorTransfers.add(workflowEntranceSelectorTransferCache.getWorkflowEntranceSelectorTransfer(userVisit, workflowEntranceSelector))
        );
        
        return workflowEntranceSelectorTransfers;
    }
    
    public List<WorkflowEntranceSelectorTransfer> getWorkflowEntranceSelectorTransfersByWorkflowEntrance(UserVisit userVisit, WorkflowEntrance workflowEntrance) {
        return getWorkflowEntranceSelectorTransfers(userVisit, getWorkflowEntranceSelectorsByWorkflowEntrance(workflowEntrance));
    }
    
    public void deleteWorkflowEntranceSelector(WorkflowEntranceSelector workflowEntranceSelector, BasePK deletedBy) {
        workflowEntranceSelector.setThruTime(session.getStartTime());
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

    @Inject
    protected WorkflowEntrancePartyTypeFactory workflowEntrancePartyTypeFactory;

    @Inject
    WorkflowEntrancePartyTypeTransferCache workflowEntrancePartyTypeTransferCache;

    public WorkflowEntrancePartyType createWorkflowEntrancePartyType(WorkflowEntrance workflowEntrance, PartyType partyType,
            BasePK createdBy) {
        var workflowEntrancePartyType = workflowEntrancePartyTypeFactory.create(
                workflowEntrance, partyType, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(workflowEntrance.getLastDetail().getWorkflowPK(), EventTypes.MODIFY, workflowEntrancePartyType.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return workflowEntrancePartyType;
    }

    public long countWorkflowEntrancePartyTypesByWorkflowEntrance(WorkflowEntrance workflowEntrance) {
        return session.getDslContext()
                .selectCount()
                .from(WorkflowEntrancePartyTypes)
                .where(WorkflowEntrancePartyTypes.WORKFLOW_ENTRANCE.eq(workflowEntrance.getPrimaryKey()),
                        WorkflowEntrancePartyTypes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countWorkflowEntrancePartyTypesByPartyType(PartyType partyType) {
        return session.getDslContext()
                .selectCount()
                .from(WorkflowEntrancePartyTypes)
                .where(WorkflowEntrancePartyTypes.PARTY_TYPE.eq(partyType.getPrimaryKey()),
                        WorkflowEntrancePartyTypes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<WorkflowEntrancePartyType> getWorkflowEntrancePartyTypesByWorkflowEntrance(WorkflowEntrance workflowEntrance,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(WorkflowEntrancePartyTypes.fields())
                    .from(WorkflowEntrancePartyTypes)
                    .join(PartyTypes).onKey(WORKFLOW_ENTRANCE_PARTY_TYPES_PARTY_TYPE_FK)
                    .where(WorkflowEntrancePartyTypes.WORKFLOW_ENTRANCE.eq(workflowEntrance.getPrimaryKey()),
                            WorkflowEntrancePartyTypes.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(PartyTypes.SORT_ORDER, PartyTypes.PARTY_TYPE_NAME),
                    WorkflowEntrancePartyTypeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(WorkflowEntrancePartyTypes.fields())
                    .from(WorkflowEntrancePartyTypes)
                    .where(WorkflowEntrancePartyTypes.WORKFLOW_ENTRANCE.eq(workflowEntrance.getPrimaryKey()),
                            WorkflowEntrancePartyTypes.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return workflowEntrancePartyTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<WorkflowEntrancePartyType> getWorkflowEntrancePartyTypesByWorkflowEntrance(WorkflowEntrance workflowEntrance) {
        return getWorkflowEntrancePartyTypesByWorkflowEntrance(workflowEntrance, EntityPermission.READ_ONLY);
    }
    
    public List<WorkflowEntrancePartyType> getWorkflowEntrancePartyTypesByWorkflowEntranceForUpdate(WorkflowEntrance workflowEntrance) {
        return getWorkflowEntrancePartyTypesByWorkflowEntrance(workflowEntrance, EntityPermission.READ_WRITE);
    }
    
    public WorkflowEntrancePartyType getWorkflowEntrancePartyType(WorkflowEntrance workflowEntrance, PartyType partyType,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(WorkflowEntrancePartyTypes.fields())
                .from(WorkflowEntrancePartyTypes)
                .where(WorkflowEntrancePartyTypes.WORKFLOW_ENTRANCE.eq(workflowEntrance.getPrimaryKey()),
                        WorkflowEntrancePartyTypes.PARTY_TYPE.eq(partyType.getPrimaryKey()),
                        WorkflowEntrancePartyTypes.THRU_TIME.eq(Session.MAX_TIME));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };
            
        return workflowEntrancePartyTypeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public WorkflowEntrancePartyType getWorkflowEntrancePartyType(WorkflowEntrance workflowEntrance, PartyType partyType) {
        return getWorkflowEntrancePartyType(workflowEntrance, partyType, EntityPermission.READ_ONLY);
    }
    
    public WorkflowEntrancePartyType getWorkflowEntrancePartyTypeForUpdate(WorkflowEntrance workflowEntrance, PartyType partyType) {
        return getWorkflowEntrancePartyType(workflowEntrance, partyType, EntityPermission.READ_WRITE);
    }
    
    public WorkflowEntrancePartyTypeTransfer getWorkflowEntrancePartyTypeTransfer(UserVisit userVisit, WorkflowEntrancePartyType workflowEntrancePartyType) {
        return workflowEntrancePartyTypeTransferCache.getWorkflowEntrancePartyTypeTransfer(userVisit, workflowEntrancePartyType);
    }
    
    public List<WorkflowEntrancePartyTypeTransfer> getWorkflowEntrancePartyTypeTransfers(UserVisit userVisit, Collection<WorkflowEntrancePartyType> workflowEntrancePartyTypes) {
        List<WorkflowEntrancePartyTypeTransfer> workflowEntrancePartyTypeTransfers = new ArrayList<>(workflowEntrancePartyTypes.size());
        
        workflowEntrancePartyTypes.forEach((workflowEntrancePartyType) ->
                workflowEntrancePartyTypeTransfers.add(workflowEntrancePartyTypeTransferCache.getWorkflowEntrancePartyTypeTransfer(userVisit, workflowEntrancePartyType))
        );
        
        return workflowEntrancePartyTypeTransfers;
    }
    
    public List<WorkflowEntrancePartyTypeTransfer> getWorkflowEntrancePartyTypeTransfersByWorkflowEntrance(UserVisit userVisit, WorkflowEntrance workflowEntrance) {
        return getWorkflowEntrancePartyTypeTransfers(userVisit, getWorkflowEntrancePartyTypesByWorkflowEntrance(workflowEntrance));
    }
    
    public void deleteWorkflowEntrancePartyType(WorkflowEntrancePartyType workflowEntrancePartyType, BasePK deletedBy) {
        deleteWorkflowEntranceSecurityRolesByWorkflowEntrancePartyType(workflowEntrancePartyType, deletedBy);
        
        workflowEntrancePartyType.setThruTime(session.getStartTime());
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

    @Inject
    protected WorkflowEntranceSecurityRoleFactory workflowEntranceSecurityRoleFactory;

    @Inject
    WorkflowEntranceSecurityRoleTransferCache workflowEntranceSecurityRoleTransferCache;

    public WorkflowEntranceSecurityRole createWorkflowEntranceSecurityRole(WorkflowEntrancePartyType workflowEntrancePartyType, SecurityRole securityRole,
            BasePK createdBy) {
        var workflowEntranceSecurityRole = workflowEntranceSecurityRoleFactory.create(
                workflowEntrancePartyType, securityRole, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(workflowEntrancePartyType.getWorkflowEntrance().getLastDetail().getWorkflowPK(), EventTypes.MODIFY, workflowEntranceSecurityRole.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return workflowEntranceSecurityRole;
    }

    public long countWorkflowEntranceSecurityRolesByWorkflowEntrancePartyType(WorkflowEntrancePartyType workflowEntrancePartyType) {
        return session.getDslContext()
                .selectCount()
                .from(WorkflowEntranceSecurityRoles)
                .where(WorkflowEntranceSecurityRoles.WORKFLOW_ENTRANCE_PARTY_TYPE.eq(workflowEntrancePartyType.getPrimaryKey()),
                        WorkflowEntranceSecurityRoles.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countWorkflowEntranceSecurityRolesBySecurityRole(SecurityRole securityRole) {
        return session.getDslContext()
                .selectCount()
                .from(WorkflowEntranceSecurityRoles)
                .where(WorkflowEntranceSecurityRoles.SECURITY_ROLE.eq(securityRole.getPrimaryKey()),
                        WorkflowEntranceSecurityRoles.THRU_TIME.eq(session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<WorkflowEntranceSecurityRole> getWorkflowEntranceSecurityRolesByWorkflowEntrancePartyType(WorkflowEntrancePartyType workflowEntrancePartyType,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(WorkflowEntranceSecurityRoles.fields())
                    .from(WorkflowEntranceSecurityRoles)
                    .join(SecurityRoles).onKey(WORKFLOW_ENTRANCE_SECURITY_ROLES_SECURITY_ROLE_FK)
                    .join(SecurityRoleDetails).onKey(SECURITY_ROLES_ACTIVE_DETAIL_FK)
                    .join(SecurityRoleGroups).onKey(SECURITY_ROLE_DETAILS_SECURITY_ROLE_GROUP_FK)
                    .join(SecurityRoleGroupDetails).onKey(SECURITY_ROLE_GROUPS_ACTIVE_DETAIL_FK)
                    .where(WorkflowEntranceSecurityRoles.WORKFLOW_ENTRANCE_PARTY_TYPE.eq(workflowEntrancePartyType.getPrimaryKey()),
                            WorkflowEntranceSecurityRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(SecurityRoleDetails.SORT_ORDER, SecurityRoleDetails.SECURITY_ROLE_NAME, SecurityRoleGroupDetails.SORT_ORDER, SecurityRoleGroupDetails.SECURITY_ROLE_GROUP_NAME),
                    WorkflowEntranceSecurityRoleFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(WorkflowEntranceSecurityRoles.fields())
                    .from(WorkflowEntranceSecurityRoles)
                    .where(WorkflowEntranceSecurityRoles.WORKFLOW_ENTRANCE_PARTY_TYPE.eq(workflowEntrancePartyType.getPrimaryKey()),
                            WorkflowEntranceSecurityRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return workflowEntranceSecurityRoleFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<WorkflowEntranceSecurityRole> getWorkflowEntranceSecurityRolesByWorkflowEntrancePartyType(WorkflowEntrancePartyType workflowEntrancePartyType) {
        return getWorkflowEntranceSecurityRolesByWorkflowEntrancePartyType(workflowEntrancePartyType, EntityPermission.READ_ONLY);
    }
    
    public List<WorkflowEntranceSecurityRole> getWorkflowEntranceSecurityRolesByWorkflowEntrancePartyTypeForUpdate(WorkflowEntrancePartyType workflowEntrancePartyType) {
        return getWorkflowEntranceSecurityRolesByWorkflowEntrancePartyType(workflowEntrancePartyType, EntityPermission.READ_WRITE);
    }
    
    public WorkflowEntranceSecurityRole getWorkflowEntranceSecurityRole(WorkflowEntrancePartyType workflowEntrancePartyType, SecurityRole securityRole,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(WorkflowEntranceSecurityRoles.fields())
                .from(WorkflowEntranceSecurityRoles)
                .where(WorkflowEntranceSecurityRoles.WORKFLOW_ENTRANCE_PARTY_TYPE.eq(workflowEntrancePartyType.getPrimaryKey()),
                        WorkflowEntranceSecurityRoles.SECURITY_ROLE.eq(securityRole.getPrimaryKey()),
                        WorkflowEntranceSecurityRoles.THRU_TIME.eq(Session.MAX_TIME));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };
            
        return workflowEntranceSecurityRoleFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public WorkflowEntranceSecurityRole getWorkflowEntranceSecurityRole(WorkflowEntrancePartyType workflowEntrancePartyType, SecurityRole securityRole) {
        return getWorkflowEntranceSecurityRole(workflowEntrancePartyType, securityRole, EntityPermission.READ_ONLY);
    }
    
    public WorkflowEntranceSecurityRole getWorkflowEntranceSecurityRoleForUpdate(WorkflowEntrancePartyType workflowEntrancePartyType, SecurityRole securityRole) {
        return getWorkflowEntranceSecurityRole(workflowEntrancePartyType, securityRole, EntityPermission.READ_WRITE);
    }
    
    public WorkflowEntranceSecurityRoleTransfer getWorkflowEntranceSecurityRoleTransfer(UserVisit userVisit, WorkflowEntranceSecurityRole workflowEntranceSecurityRole) {
        return workflowEntranceSecurityRoleTransferCache.getWorkflowEntranceSecurityRoleTransfer(userVisit, workflowEntranceSecurityRole);
    }
    
    public List<WorkflowEntranceSecurityRoleTransfer> getWorkflowEntranceSecurityRoleTransfers(UserVisit userVisit, Collection<WorkflowEntranceSecurityRole> workflowEntranceSecurityRoles) {
        List<WorkflowEntranceSecurityRoleTransfer> workflowEntranceSecurityRoleTransfers = new ArrayList<>(workflowEntranceSecurityRoles.size());
        
        workflowEntranceSecurityRoles.forEach((workflowEntranceSecurityRole) ->
                workflowEntranceSecurityRoleTransfers.add(workflowEntranceSecurityRoleTransferCache.getWorkflowEntranceSecurityRoleTransfer(userVisit, workflowEntranceSecurityRole))
        );
        
        return workflowEntranceSecurityRoleTransfers;
    }
    
    public List<WorkflowEntranceSecurityRoleTransfer> getWorkflowEntranceSecurityRoleTransfersByWorkflowEntrancePartyType(UserVisit userVisit, WorkflowEntrancePartyType workflowEntrancePartyType) {
        return getWorkflowEntranceSecurityRoleTransfers(userVisit, getWorkflowEntranceSecurityRolesByWorkflowEntrancePartyType(workflowEntrancePartyType));
    }
    
    public void deleteWorkflowEntranceSecurityRole(WorkflowEntranceSecurityRole workflowEntranceSecurityRole, BasePK deletedBy) {
        workflowEntranceSecurityRole.setThruTime(session.getStartTime());
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

    @Inject
    protected WorkflowEntranceStepFactory workflowEntranceStepFactory;

    @Inject
    WorkflowEntranceStepTransferCache workflowEntranceStepTransferCache;

    public WorkflowEntranceStep createWorkflowEntranceStep(WorkflowEntrance workflowEntrance, WorkflowStep workflowStep,
            BasePK createdBy) {
        var workflowEntranceStep = workflowEntranceStepFactory.create(workflowEntrance,
                workflowStep, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(workflowEntrance.getLastDetail().getWorkflowPK(), EventTypes.MODIFY, workflowEntranceStep.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return workflowEntranceStep;
    }

    public long countWorkflowEntranceStepsByWorkflowEntrance(WorkflowEntrance workflowEntrance) {
        return session.getDslContext()
                .selectCount()
                .from(WorkflowEntranceSteps)
                .where(WorkflowEntranceSteps.WORKFLOW_ENTRANCE.eq(workflowEntrance.getPrimaryKey()),
                        WorkflowEntranceSteps.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }
    
    public long countWorkflowEntranceStepsByWorkflowStep(WorkflowStep workflowStep) {
        return session.getDslContext()
                .selectCount()
                .from(WorkflowEntranceSteps)
                .where(WorkflowEntranceSteps.WORKFLOW_STEP.eq(workflowStep.getPrimaryKey()),
                        WorkflowEntranceSteps.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }
    
    private List<WorkflowEntranceStep> getWorkflowEntranceStepsByWorkflowStep(WorkflowStep workflowStep, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(WorkflowEntranceSteps.fields())
                .from(WorkflowEntranceSteps)
                .where(WorkflowEntranceSteps.WORKFLOW_STEP.eq(workflowStep.getPrimaryKey()),
                        WorkflowEntranceSteps.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(WorkflowEntranceSteps.fields())
                    .from(WorkflowEntranceSteps)
                    .join(WorkflowEntrances).onKey(WORKFLOW_ENTRANCE_STEPS_WORKFLOW_ENTRANCE_FK)
                    .join(WorkflowEntranceDetails).onKey(WORKFLOW_ENTRANCES_LAST_DETAIL_FK)
                    .where(WorkflowEntranceSteps.WORKFLOW_STEP.eq(workflowStep.getPrimaryKey()),
                            WorkflowEntranceSteps.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(WorkflowEntranceDetails.SORT_ORDER, WorkflowEntranceDetails.WORKFLOW_ENTRANCE_NAME),
                    WorkflowEntranceStepFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return workflowEntranceStepFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<WorkflowEntranceStep> getWorkflowEntranceStepsByWorkflowStep(WorkflowStep workflowStep) {
        return getWorkflowEntranceStepsByWorkflowStep(workflowStep, EntityPermission.READ_ONLY);
    }
    
    public List<WorkflowEntranceStep> getWorkflowEntranceStepsByWorkflowStepForUpdate(WorkflowStep workflowStep) {
        return getWorkflowEntranceStepsByWorkflowStep(workflowStep, EntityPermission.READ_WRITE);
    }
    
    private List<WorkflowEntranceStep> getWorkflowEntranceStepsByWorkflowEntrance(WorkflowEntrance workflowEntrance, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(WorkflowEntranceSteps.fields())
                    .from(WorkflowEntranceSteps)
                    .join(WorkflowSteps).onKey(WORKFLOW_ENTRANCE_STEPS_WORKFLOW_STEP_FK)
                    .join(WorkflowStepDetails).onKey(WORKFLOW_STEPS_ACTIVE_DETAIL_FK)
                    .join(Workflows).onKey(WORKFLOW_STEP_DETAILS_WORKFLOW_FK)
                    .join(WorkflowDetails).onKey(WORKFLOWS_ACTIVE_DETAIL_FK)
                    .where(WorkflowEntranceSteps.WORKFLOW_ENTRANCE.eq(workflowEntrance.getPrimaryKey()),
                            WorkflowEntranceSteps.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(WorkflowDetails.SORT_ORDER, WorkflowDetails.WORKFLOW_NAME, WorkflowStepDetails.SORT_ORDER, WorkflowStepDetails.WORKFLOW_STEP_NAME),
                    WorkflowEntranceStepFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(WorkflowEntranceSteps.fields())
                    .from(WorkflowEntranceSteps)
                    .where(WorkflowEntranceSteps.WORKFLOW_ENTRANCE.eq(workflowEntrance.getPrimaryKey()),
                            WorkflowEntranceSteps.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return workflowEntranceStepFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<WorkflowEntranceStep> getWorkflowEntranceStepsByWorkflowEntrance(WorkflowEntrance workflowEntrance) {
        return getWorkflowEntranceStepsByWorkflowEntrance(workflowEntrance, EntityPermission.READ_ONLY);
    }
    
    public List<WorkflowEntranceStep> getWorkflowEntranceStepsByWorkflowEntranceForUpdate(WorkflowEntrance workflowEntrance) {
        return getWorkflowEntranceStepsByWorkflowEntrance(workflowEntrance, EntityPermission.READ_WRITE);
    }
    
    private WorkflowEntranceStep getWorkflowEntranceStep(WorkflowEntrance workflowEntrance, WorkflowStep workflowStep, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(WorkflowEntranceSteps.fields())
                .from(WorkflowEntranceSteps)
                .where(WorkflowEntranceSteps.WORKFLOW_ENTRANCE.eq(workflowEntrance.getPrimaryKey()),
                        WorkflowEntranceSteps.WORKFLOW_STEP.eq(workflowStep.getPrimaryKey()),
                        WorkflowEntranceSteps.THRU_TIME.eq(Session.MAX_TIME));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };
            
        return workflowEntranceStepFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public WorkflowEntranceStep getWorkflowEntranceStep(WorkflowEntrance workflowEntrance, WorkflowStep workflowStep) {
        return getWorkflowEntranceStep(workflowEntrance, workflowStep, EntityPermission.READ_ONLY);
    }
    
    public WorkflowEntranceStep getWorkflowEntranceStepForUpdate(WorkflowEntrance workflowEntrance, WorkflowStep workflowStep) {
        return getWorkflowEntranceStep(workflowEntrance, workflowStep, EntityPermission.READ_WRITE);
    }
    
    public WorkflowEntranceStepTransfer getWorkflowEntranceStepTransfer(UserVisit userVisit, WorkflowEntranceStep workflowEntranceStep) {
        return workflowEntranceStepTransferCache.getWorkflowEntranceStepTransfer(userVisit, workflowEntranceStep);
    }
    
    public List<WorkflowEntranceStepTransfer> getWorkflowEntranceStepTransfers(UserVisit userVisit, Collection<WorkflowEntranceStep> workflowEntranceSteps) {
        List<WorkflowEntranceStepTransfer> workflowEntranceStepTransfers = new ArrayList<>(workflowEntranceSteps.size());
        
        workflowEntranceSteps.forEach((workflowEntranceStep) ->
                workflowEntranceStepTransfers.add(workflowEntranceStepTransferCache.getWorkflowEntranceStepTransfer(userVisit, workflowEntranceStep))
        );
        
        return workflowEntranceStepTransfers;
    }
    
    public List<WorkflowEntranceStepTransfer> getWorkflowEntranceStepTransfersByWorkflowEntrance(UserVisit userVisit, WorkflowEntrance workflowEntrance) {
        return getWorkflowEntranceStepTransfers(userVisit, getWorkflowEntranceStepsByWorkflowEntrance(workflowEntrance));
    }

    public void deleteWorkflowEntranceStep(WorkflowEntranceStep workflowEntranceStep, BasePK deletedBy) {
        workflowEntranceStep.setThruTime(session.getStartTime());
        
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

    @Inject
    protected WorkflowDestinationDetailFactory workflowDestinationDetailFactory;

    @Inject
    protected WorkflowDestinationFactory workflowDestinationFactory;

    @Inject
    WorkflowDestinationTransferCache workflowDestinationTransferCache;

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

        var workflowDestination = workflowDestinationFactory.create();
        var workflowDestinationDetail = workflowDestinationDetailFactory.create(workflowDestination, workflowStep,
                workflowDestinationName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        workflowDestination = workflowDestinationFactory.getEntityFromPK(EntityPermission.READ_WRITE, workflowDestination.getPrimaryKey());
        workflowDestination.setActiveDetail(workflowDestinationDetail);
        workflowDestination.setLastDetail(workflowDestinationDetail);
        workflowDestination.store();
        
        sendEvent(workflowStep.getLastDetail().getWorkflowPK(), EventTypes.MODIFY, workflowDestination.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return workflowDestination;
    }

    public long countWorkflowDestinationsByWorkflowStep(WorkflowStep workflowStep) {
        return session.getDslContext()
                .selectCount()
                .from(WorkflowDestinations)
                .join(WorkflowDestinationDetails).onKey(WORKFLOW_DESTINATIONS_ACTIVE_DETAIL_FK)
                .where(WorkflowDestinationDetails.WORKFLOW_STEP.eq(workflowStep.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.WorkflowDestination */
    public WorkflowDestination getWorkflowDestinationByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new WorkflowDestinationPK(entityInstance.getEntityUniqueId());

        return workflowDestinationFactory.getEntityFromPK(entityPermission, pk);
    }

    public WorkflowDestination getWorkflowDestinationByEntityInstance(EntityInstance entityInstance) {
        return getWorkflowDestinationByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public WorkflowDestination getWorkflowDestinationByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getWorkflowDestinationByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public WorkflowDestination getDefaultWorkflowDestination(WorkflowStep workflowStep, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(WorkflowDestinations.fields())
                .from(WorkflowDestinations)
                .join(WorkflowDestinationDetails).onKey(WORKFLOW_DESTINATIONS_ACTIVE_DETAIL_FK)
                .where(WorkflowDestinationDetails.WORKFLOW_STEP.eq(workflowStep.getPrimaryKey()),
                        WorkflowDestinationDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return workflowDestinationFactory.getEntityFromQuery(entityPermission, query);
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
    
    private List<WorkflowDestination> getWorkflowDestinationsByWorkflow(WorkflowStep workflowStep, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(WorkflowDestinations.fields())
                .from(WorkflowDestinations)
                .join(WorkflowDestinationDetails).onKey(WORKFLOW_DESTINATIONS_ACTIVE_DETAIL_FK)
                .where(WorkflowDestinationDetails.WORKFLOW_STEP.eq(workflowStep.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(WorkflowDestinationDetails.SORT_ORDER, WorkflowDestinationDetails.WORKFLOW_DESTINATION_NAME),
                    WorkflowDestinationFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return workflowDestinationFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<WorkflowDestination> getWorkflowDestinationsByWorkflowStep(WorkflowStep workflowStep) {
        return getWorkflowDestinationsByWorkflow(workflowStep, EntityPermission.READ_ONLY);
    }
    
    public List<WorkflowDestination> getWorkflowDestinationsByWorkflowStepForUpdate(WorkflowStep workflowStep) {
        return getWorkflowDestinationsByWorkflow(workflowStep, EntityPermission.READ_WRITE);
    }
    
    public WorkflowDestination getWorkflowDestinationByName(WorkflowStep workflowStep, String workflowDestinationName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(WorkflowDestinations.fields())
                .from(WorkflowDestinations)
                .join(WorkflowDestinationDetails).onKey(WORKFLOW_DESTINATIONS_ACTIVE_DETAIL_FK)
                .where(WorkflowDestinationDetails.WORKFLOW_STEP.eq(workflowStep.getPrimaryKey()),
                        WorkflowDestinationDetails.WORKFLOW_DESTINATION_NAME.eq(workflowDestinationName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return workflowDestinationFactory.getEntityFromQuery(entityPermission, query);
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
        return workflowDestinationTransferCache.getWorkflowDestinationTransfer(userVisit, workflowDestination);
    }
    
    public List<WorkflowDestinationTransfer> getWorkflowDestinationTransfers(UserVisit userVisit, Collection<WorkflowDestination> workflowDestinations) {
        List<WorkflowDestinationTransfer> workflowDestinationTransfers = new ArrayList<>(workflowDestinations.size());
        
        workflowDestinations.forEach((workflowDestination) ->
                workflowDestinationTransfers.add(workflowDestinationTransferCache.getWorkflowDestinationTransfer(userVisit, workflowDestination))
        );
        
        return workflowDestinationTransfers;
    }
    
    public List<WorkflowDestinationTransfer> getWorkflowDestinationTransfersByWorkflowStep(UserVisit userVisit, WorkflowStep workflowStep) {
        return getWorkflowDestinationTransfers(userVisit, getWorkflowDestinationsByWorkflowStep(workflowStep));
    }

    private void updateWorkflowDestinationFromValue(WorkflowDestinationDetailValue workflowDestinationDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(workflowDestinationDetailValue.hasBeenModified()) {
            var workflowDestination = workflowDestinationFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    workflowDestinationDetailValue.getWorkflowDestinationPK());
            var workflowDestinationDetail = workflowDestination.getActiveDetailForUpdate();
            
            workflowDestinationDetail.setThruTime(session.getStartTime());
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
            
            workflowDestinationDetail = workflowDestinationDetailFactory.create(workflowDestinationPK, workflowStepPK, workflowDestinationName,
                    isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
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
        workflowDestinationDetail.setThruTime(session.getStartTime());
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

    @Inject
    protected WorkflowDestinationDescriptionFactory workflowDestinationDescriptionFactory;

    @Inject
    WorkflowDestinationDescriptionTransferCache workflowDestinationDescriptionTransferCache;

    public WorkflowDestinationDescription createWorkflowDestinationDescription(WorkflowDestination workflowDestination, Language language, String description, BasePK createdBy) {
        var workflowDestinationDescription = workflowDestinationDescriptionFactory.create(workflowDestination, language, description,
                session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(workflowDestination.getLastDetail().getWorkflowStep().getLastDetail().getWorkflowPK(), EventTypes.MODIFY, workflowDestinationDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return workflowDestinationDescription;
    }
    
    private WorkflowDestinationDescription getWorkflowDestinationDescription(WorkflowDestination workflowDestination, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(WorkflowDestinationDescriptions.fields())
                .from(WorkflowDestinationDescriptions)
                .where(WorkflowDestinationDescriptions.WORKFLOW_DESTINATION.eq(workflowDestination.getPrimaryKey()),
                        WorkflowDestinationDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        WorkflowDestinationDescriptions.THRU_TIME.eq(Session.MAX_TIME));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };
            
        return workflowDestinationDescriptionFactory.getEntityFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(WorkflowDestinationDescriptions.fields())
                    .from(WorkflowDestinationDescriptions)
                    .join(Languages).onKey(WORKFLOW_DESTINATION_DESCRIPTIONS_LANGUAGE_FK)
                    .where(WorkflowDestinationDescriptions.WORKFLOW_DESTINATION.eq(workflowDestination.getPrimaryKey()),
                            WorkflowDestinationDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    WorkflowDestinationDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(WorkflowDestinationDescriptions.fields())
                    .from(WorkflowDestinationDescriptions)
                    .where(WorkflowDestinationDescriptions.WORKFLOW_DESTINATION.eq(workflowDestination.getPrimaryKey()),
                            WorkflowDestinationDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return workflowDestinationDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
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
        return workflowDestinationDescriptionTransferCache.getWorkflowDestinationDescriptionTransfer(userVisit, workflowDestinationDescription);
    }
    
    public List<WorkflowDestinationDescriptionTransfer> getWorkflowDestinationDescriptionTransfersByWorkflowDestination(UserVisit userVisit, WorkflowDestination workflowDestination) {
        var workflowDestinationDescriptions = getWorkflowDestinationDescriptionsByWorkflowDestination(workflowDestination);
        List<WorkflowDestinationDescriptionTransfer> workflowDestinationDescriptionTransfers = new ArrayList<>(workflowDestinationDescriptions.size());
        
        workflowDestinationDescriptions.forEach((workflowDestinationDescription) ->
                workflowDestinationDescriptionTransfers.add(workflowDestinationDescriptionTransferCache.getWorkflowDestinationDescriptionTransfer(userVisit, workflowDestinationDescription))
        );
        
        return workflowDestinationDescriptionTransfers;
    }
    
    public void updateWorkflowDestinationDescriptionFromValue(WorkflowDestinationDescriptionValue workflowDestinationDescriptionValue, BasePK updatedBy) {
        if(workflowDestinationDescriptionValue.hasBeenModified()) {
            var workflowDestinationDescription = workflowDestinationDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE, workflowDestinationDescriptionValue.getPrimaryKey());
            
            workflowDestinationDescription.setThruTime(session.getStartTime());
            workflowDestinationDescription.store();

            var workflowDestination = workflowDestinationDescription.getWorkflowDestination();
            var language = workflowDestinationDescription.getLanguage();
            var description = workflowDestinationDescriptionValue.getDescription();
            
            workflowDestinationDescription = workflowDestinationDescriptionFactory.create(workflowDestination, language, description,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(workflowDestination.getLastDetail().getWorkflowStep().getLastDetail().getWorkflowPK(), EventTypes.MODIFY, workflowDestinationDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteWorkflowDestinationDescription(WorkflowDestinationDescription workflowDestinationDescription, BasePK deletedBy) {
        workflowDestinationDescription.setThruTime(session.getStartTime());
        
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

    @Inject
    protected WorkflowDestinationSelectorFactory workflowDestinationSelectorFactory;

    @Inject
    WorkflowDestinationSelectorTransferCache workflowDestinationSelectorTransferCache;

    public WorkflowDestinationSelector createWorkflowDestinationSelector(WorkflowDestination workflowDestination, Selector selector,
            BasePK createdBy) {
        var workflowDestinationSelector = workflowDestinationSelectorFactory.create(
                workflowDestination, selector, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(workflowDestination.getLastDetail().getWorkflowStep().getLastDetail().getWorkflowPK(), EventTypes.MODIFY, workflowDestinationSelector.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return workflowDestinationSelector;
    }

    public long countWorkflowDestinationSelectorsByWorkflowDestination(WorkflowDestination workflowDestination) {
        return session.getDslContext()
                .selectCount()
                .from(WorkflowDestinationSelectors)
                .where(WorkflowDestinationSelectors.WORKFLOW_DESTINATION.eq(workflowDestination.getPrimaryKey()),
                        WorkflowDestinationSelectors.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countWorkflowDestinationSelectorsBySelector(Selector selector) {
        return session.getDslContext()
                .selectCount()
                .from(WorkflowDestinationSelectors)
                .where(WorkflowDestinationSelectors.SELECTOR.eq(selector.getPrimaryKey()),
                        WorkflowDestinationSelectors.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<WorkflowDestinationSelector> getWorkflowDestinationSelectorsBySelector(Selector selector, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(WorkflowDestinationSelectors.fields())
                    .from(WorkflowDestinationSelectors)
                    .join(WorkflowDestinations).onKey(WORKFLOW_DESTINATION_SELECTORS_WORKFLOW_DESTINATION_FK)
                    .join(WorkflowDestinationDetails).onKey(WORKFLOW_DESTINATIONS_LAST_DETAIL_FK)
                    .join(WorkflowSteps).onKey(WORKFLOW_DESTINATION_DETAILS_WORKFLOW_STEP_FK)
                    .join(WorkflowStepDetails).onKey(WORKFLOW_STEPS_LAST_DETAIL_FK)
                    .join(Workflows).onKey(WORKFLOW_STEP_DETAILS_WORKFLOW_FK)
                    .join(WorkflowDetails).onKey(WORKFLOWS_LAST_DETAIL_FK)
                    .where(WorkflowDestinationSelectors.SELECTOR.eq(selector.getPrimaryKey()),
                            WorkflowDestinationSelectors.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(WorkflowDestinationDetails.SORT_ORDER, WorkflowDestinationDetails.WORKFLOW_DESTINATION_NAME, WorkflowStepDetails.SORT_ORDER, WorkflowStepDetails.WORKFLOW_STEP_NAME, WorkflowDetails.SORT_ORDER, WorkflowDetails.WORKFLOW_NAME),
                    WorkflowDestinationSelectorFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(WorkflowDestinationSelectors.fields())
                    .from(WorkflowDestinationSelectors)
                    .where(WorkflowDestinationSelectors.SELECTOR.eq(selector.getPrimaryKey()),
                            WorkflowDestinationSelectors.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return workflowDestinationSelectorFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<WorkflowDestinationSelector> getWorkflowDestinationSelectorsBySelector(Selector selector) {
        return getWorkflowDestinationSelectorsBySelector(selector, EntityPermission.READ_ONLY);
    }
    
    public List<WorkflowDestinationSelector> getWorkflowDestinationSelectorsBySelectorForUpdate(Selector selector) {
        return getWorkflowDestinationSelectorsBySelector(selector, EntityPermission.READ_WRITE);
    }
    
    private List<WorkflowDestinationSelector> getWorkflowDestinationSelectorsByWorkflowDestination(WorkflowDestination workflowDestination, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(WorkflowDestinationSelectors.fields())
                    .from(WorkflowDestinationSelectors)
                    .join(Selectors).onKey(WORKFLOW_DESTINATION_SELECTORS_SELECTOR_FK)
                    .join(SelectorDetails).onKey(SELECTORS_LAST_DETAIL_FK)
                    .join(SelectorTypes).onKey(SELECTOR_DETAILS_SELECTOR_TYPE_FK)
                    .join(SelectorTypeDetails).onKey(SELECTOR_TYPES_LAST_DETAIL_FK)
                    .join(SelectorKinds).onKey(SELECTOR_TYPE_DETAILS_SELECTOR_KIND_FK)
                    .join(SelectorKindDetails).onKey(SELECTOR_KINDS_LAST_DETAIL_FK)
                    .where(WorkflowDestinationSelectors.WORKFLOW_DESTINATION.eq(workflowDestination.getPrimaryKey()),
                            WorkflowDestinationSelectors.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(SelectorDetails.SORT_ORDER, SelectorDetails.SELECTOR_NAME, SelectorTypeDetails.SORT_ORDER, SelectorTypeDetails.SELECTOR_TYPE_NAME, SelectorKindDetails.SORT_ORDER, SelectorKindDetails.SELECTOR_KIND_NAME),
                    WorkflowDestinationSelectorFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(WorkflowDestinationSelectors.fields())
                    .from(WorkflowDestinationSelectors)
                    .where(WorkflowDestinationSelectors.WORKFLOW_DESTINATION.eq(workflowDestination.getPrimaryKey()),
                            WorkflowDestinationSelectors.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return workflowDestinationSelectorFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<WorkflowDestinationSelector> getWorkflowDestinationSelectorsByWorkflowDestination(WorkflowDestination workflowDestination) {
        return getWorkflowDestinationSelectorsByWorkflowDestination(workflowDestination, EntityPermission.READ_ONLY);
    }
    
    public List<WorkflowDestinationSelector> getWorkflowDestinationSelectorsByWorkflowDestinationForUpdate(WorkflowDestination workflowDestination) {
        return getWorkflowDestinationSelectorsByWorkflowDestination(workflowDestination, EntityPermission.READ_WRITE);
    }
    
    public WorkflowDestinationSelector getWorkflowDestinationSelector(WorkflowDestination workflowDestination, Selector selector, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(WorkflowDestinationSelectors.fields())
                .from(WorkflowDestinationSelectors)
                .where(WorkflowDestinationSelectors.WORKFLOW_DESTINATION.eq(workflowDestination.getPrimaryKey()),
                        WorkflowDestinationSelectors.SELECTOR.eq(selector.getPrimaryKey()),
                        WorkflowDestinationSelectors.THRU_TIME.eq(Session.MAX_TIME));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };
            
        return workflowDestinationSelectorFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public WorkflowDestinationSelector getWorkflowDestinationSelector(WorkflowDestination workflowDestination, Selector selector) {
        return getWorkflowDestinationSelector(workflowDestination, selector, EntityPermission.READ_ONLY);
    }
    
    public WorkflowDestinationSelector getWorkflowDestinationSelectorForUpdate(WorkflowDestination workflowDestination, Selector selector) {
        return getWorkflowDestinationSelector(workflowDestination, selector, EntityPermission.READ_WRITE);
    }
    
    public WorkflowDestinationSelectorTransfer getWorkflowDestinationSelectorTransfer(UserVisit userVisit, WorkflowDestinationSelector workflowDestinationSelector) {
        return workflowDestinationSelectorTransferCache.getWorkflowDestinationSelectorTransfer(userVisit, workflowDestinationSelector);
    }
    
    public List<WorkflowDestinationSelectorTransfer> getWorkflowDestinationSelectorTransfers(UserVisit userVisit, Collection<WorkflowDestinationSelector> workflowDestinationSelectors) {
        List<WorkflowDestinationSelectorTransfer> workflowDestinationSelectorTransfers = new ArrayList<>(workflowDestinationSelectors.size());
        
        workflowDestinationSelectors.forEach((workflowDestinationSelector) ->
                workflowDestinationSelectorTransfers.add(workflowDestinationSelectorTransferCache.getWorkflowDestinationSelectorTransfer(userVisit, workflowDestinationSelector))
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
        workflowDestinationSelector.setThruTime(session.getStartTime());
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

    @Inject
    protected WorkflowDestinationPartyTypeFactory workflowDestinationPartyTypeFactory;

    @Inject
    WorkflowDestinationPartyTypeTransferCache workflowDestinationPartyTypeTransferCache;

    public WorkflowDestinationPartyType createWorkflowDestinationPartyType(WorkflowDestination workflowDestination, PartyType partyType,
            BasePK createdBy) {
        var workflowDestinationPartyType = workflowDestinationPartyTypeFactory.create(
                workflowDestination, partyType, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(workflowDestination.getLastDetail().getWorkflowStep().getLastDetail().getWorkflowPK(), EventTypes.MODIFY, workflowDestinationPartyType.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return workflowDestinationPartyType;
    }

    public long countWorkflowDestinationPartyTypesByWorkflowDestination(WorkflowDestination workflowDestination) {
        return session.getDslContext()
                .selectCount()
                .from(WorkflowDestinationPartyTypes)
                .where(WorkflowDestinationPartyTypes.WORKFLOW_DESTINATION.eq(workflowDestination.getPrimaryKey()),
                        WorkflowDestinationPartyTypes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countWorkflowDestinationPartyTypesByPartyType(PartyType partyType) {
        return session.getDslContext()
                .selectCount()
                .from(WorkflowDestinationPartyTypes)
                .where(WorkflowDestinationPartyTypes.PARTY_TYPE.eq(partyType.getPrimaryKey()),
                        WorkflowDestinationPartyTypes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<WorkflowDestinationPartyType> getWorkflowDestinationPartyTypesByWorkflowDestination(WorkflowDestination workflowDestination,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(WorkflowDestinationPartyTypes.fields())
                    .from(WorkflowDestinationPartyTypes)
                    .join(PartyTypes).onKey(WORKFLOW_DESTINATION_PARTY_TYPES_PARTY_TYPE_FK)
                    .where(WorkflowDestinationPartyTypes.WORKFLOW_DESTINATION.eq(workflowDestination.getPrimaryKey()),
                            WorkflowDestinationPartyTypes.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(PartyTypes.SORT_ORDER, PartyTypes.PARTY_TYPE_NAME),
                    WorkflowDestinationPartyTypeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(WorkflowDestinationPartyTypes.fields())
                    .from(WorkflowDestinationPartyTypes)
                    .where(WorkflowDestinationPartyTypes.WORKFLOW_DESTINATION.eq(workflowDestination.getPrimaryKey()),
                            WorkflowDestinationPartyTypes.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return workflowDestinationPartyTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<WorkflowDestinationPartyType> getWorkflowDestinationPartyTypesByWorkflowDestination(WorkflowDestination workflowDestination) {
        return getWorkflowDestinationPartyTypesByWorkflowDestination(workflowDestination, EntityPermission.READ_ONLY);
    }
    
    public List<WorkflowDestinationPartyType> getWorkflowDestinationPartyTypesByWorkflowDestinationForUpdate(WorkflowDestination workflowDestination) {
        return getWorkflowDestinationPartyTypesByWorkflowDestination(workflowDestination, EntityPermission.READ_WRITE);
    }
    
    public WorkflowDestinationPartyType getWorkflowDestinationPartyType(WorkflowDestination workflowDestination, PartyType partyType,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(WorkflowDestinationPartyTypes.fields())
                .from(WorkflowDestinationPartyTypes)
                .where(WorkflowDestinationPartyTypes.WORKFLOW_DESTINATION.eq(workflowDestination.getPrimaryKey()),
                        WorkflowDestinationPartyTypes.PARTY_TYPE.eq(partyType.getPrimaryKey()),
                        WorkflowDestinationPartyTypes.THRU_TIME.eq(Session.MAX_TIME));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };
            
        return workflowDestinationPartyTypeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public WorkflowDestinationPartyType getWorkflowDestinationPartyType(WorkflowDestination workflowDestination, PartyType partyType) {
        return getWorkflowDestinationPartyType(workflowDestination, partyType, EntityPermission.READ_ONLY);
    }
    
    public WorkflowDestinationPartyType getWorkflowDestinationPartyTypeForUpdate(WorkflowDestination workflowDestination, PartyType partyType) {
        return getWorkflowDestinationPartyType(workflowDestination, partyType, EntityPermission.READ_WRITE);
    }
    
    public long countWorkflowDestinationPartyTypes(WorkflowDestination workflowDestination) {
        return session.getDslContext()
                .selectCount()
                .from(WorkflowDestinationPartyTypes)
                .where(WorkflowDestinationPartyTypes.WORKFLOW_DESTINATION.eq(workflowDestination.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }
    
    public WorkflowDestinationPartyTypeTransfer getWorkflowDestinationPartyTypeTransfer(UserVisit userVisit, WorkflowDestinationPartyType workflowDestinationPartyType) {
        return workflowDestinationPartyTypeTransferCache.getWorkflowDestinationPartyTypeTransfer(userVisit, workflowDestinationPartyType);
    }
    
    public List<WorkflowDestinationPartyTypeTransfer> getWorkflowDestinationPartyTypeTransfers(UserVisit userVisit, Collection<WorkflowDestinationPartyType> workflowDestinationPartyTypes) {
        List<WorkflowDestinationPartyTypeTransfer> workflowDestinationPartyTypeTransfers = new ArrayList<>(workflowDestinationPartyTypes.size());
        
        workflowDestinationPartyTypes.forEach((workflowDestinationPartyType) ->
                workflowDestinationPartyTypeTransfers.add(workflowDestinationPartyTypeTransferCache.getWorkflowDestinationPartyTypeTransfer(userVisit, workflowDestinationPartyType))
        );
        
        return workflowDestinationPartyTypeTransfers;
    }
    
    public List<WorkflowDestinationPartyTypeTransfer> getWorkflowDestinationPartyTypeTransfersByWorkflowDestination(UserVisit userVisit, WorkflowDestination workflowDestination) {
        return getWorkflowDestinationPartyTypeTransfers(userVisit, getWorkflowDestinationPartyTypesByWorkflowDestination(workflowDestination));
    }
    
    public void deleteWorkflowDestinationPartyType(WorkflowDestinationPartyType workflowDestinationPartyType, BasePK deletedBy) {
        deleteWorkflowDestinationSecurityRolesByWorkflowDestinationPartyType(workflowDestinationPartyType, deletedBy);
        
        workflowDestinationPartyType.setThruTime(session.getStartTime());
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

    @Inject
    protected WorkflowDestinationSecurityRoleFactory workflowDestinationSecurityRoleFactory;

    @Inject
    WorkflowDestinationSecurityRoleTransferCache workflowDestinationSecurityRoleTransferCache;

    public WorkflowDestinationSecurityRole createWorkflowDestinationSecurityRole(WorkflowDestinationPartyType workflowDestinationPartyType, SecurityRole securityRole,
            BasePK createdBy) {
        var workflowDestinationSecurityRole = workflowDestinationSecurityRoleFactory.create(
                workflowDestinationPartyType, securityRole, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(workflowDestinationPartyType.getWorkflowDestination().getLastDetail().getWorkflowStep().getLastDetail().getWorkflowPK(), EventTypes.MODIFY, workflowDestinationSecurityRole.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return workflowDestinationSecurityRole;
    }

    public long countWorkflowDestinationSecurityRolesByWorkflowDestinationPartyType(WorkflowDestinationPartyType workflowDestinationPartyType) {
        return session.getDslContext()
                .selectCount()
                .from(WorkflowDestinationSecurityRoles)
                .where(WorkflowDestinationSecurityRoles.WORKFLOW_DESTINATION_PARTY_TYPE.eq(workflowDestinationPartyType.getPrimaryKey()),
                        WorkflowDestinationSecurityRoles.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countWorkflowDestinationSecurityRolesBySecurityRole(SecurityRole securityRole) {
        return session.getDslContext()
                .selectCount()
                .from(WorkflowDestinationSecurityRoles)
                .where(WorkflowDestinationSecurityRoles.SECURITY_ROLE.eq(securityRole.getPrimaryKey()),
                        WorkflowDestinationSecurityRoles.THRU_TIME.eq(session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<WorkflowDestinationSecurityRole> getWorkflowDestinationSecurityRolesByWorkflowDestinationPartyType(WorkflowDestinationPartyType workflowDestinationPartyType,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(WorkflowDestinationSecurityRoles.fields())
                    .from(WorkflowDestinationSecurityRoles)
                    .join(SecurityRoles).onKey(WORKFLOW_DESTINATION_SECURITY_ROLES_SECURITY_ROLE_FK)
                    .join(SecurityRoleDetails).onKey(SECURITY_ROLES_ACTIVE_DETAIL_FK)
                    .join(SecurityRoleGroups).onKey(SECURITY_ROLE_DETAILS_SECURITY_ROLE_GROUP_FK)
                    .join(SecurityRoleGroupDetails).onKey(SECURITY_ROLE_GROUPS_ACTIVE_DETAIL_FK)
                    .where(WorkflowDestinationSecurityRoles.WORKFLOW_DESTINATION_PARTY_TYPE.eq(workflowDestinationPartyType.getPrimaryKey()),
                            WorkflowDestinationSecurityRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(SecurityRoleDetails.SORT_ORDER, SecurityRoleDetails.SECURITY_ROLE_NAME, SecurityRoleGroupDetails.SORT_ORDER, SecurityRoleGroupDetails.SECURITY_ROLE_GROUP_NAME),
                    WorkflowDestinationSecurityRoleFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(WorkflowDestinationSecurityRoles.fields())
                    .from(WorkflowDestinationSecurityRoles)
                    .where(WorkflowDestinationSecurityRoles.WORKFLOW_DESTINATION_PARTY_TYPE.eq(workflowDestinationPartyType.getPrimaryKey()),
                            WorkflowDestinationSecurityRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return workflowDestinationSecurityRoleFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<WorkflowDestinationSecurityRole> getWorkflowDestinationSecurityRolesByWorkflowDestinationPartyType(WorkflowDestinationPartyType workflowDestinationPartyType) {
        return getWorkflowDestinationSecurityRolesByWorkflowDestinationPartyType(workflowDestinationPartyType, EntityPermission.READ_ONLY);
    }
    
    public List<WorkflowDestinationSecurityRole> getWorkflowDestinationSecurityRolesByWorkflowDestinationPartyTypeForUpdate(WorkflowDestinationPartyType workflowDestinationPartyType) {
        return getWorkflowDestinationSecurityRolesByWorkflowDestinationPartyType(workflowDestinationPartyType, EntityPermission.READ_WRITE);
    }
    
    public WorkflowDestinationSecurityRole getWorkflowDestinationSecurityRole(WorkflowDestinationPartyType workflowDestinationPartyType, SecurityRole securityRole,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(WorkflowDestinationSecurityRoles.fields())
                .from(WorkflowDestinationSecurityRoles)
                .where(WorkflowDestinationSecurityRoles.WORKFLOW_DESTINATION_PARTY_TYPE.eq(workflowDestinationPartyType.getPrimaryKey()),
                        WorkflowDestinationSecurityRoles.SECURITY_ROLE.eq(securityRole.getPrimaryKey()),
                        WorkflowDestinationSecurityRoles.THRU_TIME.eq(Session.MAX_TIME));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };
            
        return workflowDestinationSecurityRoleFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public WorkflowDestinationSecurityRole getWorkflowDestinationSecurityRole(WorkflowDestinationPartyType workflowDestinationPartyType, SecurityRole securityRole) {
        return getWorkflowDestinationSecurityRole(workflowDestinationPartyType, securityRole, EntityPermission.READ_ONLY);
    }
    
    public WorkflowDestinationSecurityRole getWorkflowDestinationSecurityRoleForUpdate(WorkflowDestinationPartyType workflowDestinationPartyType, SecurityRole securityRole) {
        return getWorkflowDestinationSecurityRole(workflowDestinationPartyType, securityRole, EntityPermission.READ_WRITE);
    }
    
    public WorkflowDestinationSecurityRoleTransfer getWorkflowDestinationSecurityRoleTransfer(UserVisit userVisit, WorkflowDestinationSecurityRole workflowDestinationSecurityRole) {
        return workflowDestinationSecurityRoleTransferCache.getWorkflowDestinationSecurityRoleTransfer(userVisit, workflowDestinationSecurityRole);
    }
    
    public List<WorkflowDestinationSecurityRoleTransfer> getWorkflowDestinationSecurityRoleTransfers(UserVisit userVisit, Collection<WorkflowDestinationSecurityRole> workflowDestinationSecurityRoles) {
        List<WorkflowDestinationSecurityRoleTransfer> workflowDestinationSecurityRoleTransfers = new ArrayList<>(workflowDestinationSecurityRoles.size());
        
        workflowDestinationSecurityRoles.forEach((workflowDestinationSecurityRole) ->
                workflowDestinationSecurityRoleTransfers.add(workflowDestinationSecurityRoleTransferCache.getWorkflowDestinationSecurityRoleTransfer(userVisit, workflowDestinationSecurityRole))
        );
        
        return workflowDestinationSecurityRoleTransfers;
    }
    
    public List<WorkflowDestinationSecurityRoleTransfer> getWorkflowDestinationSecurityRoleTransfersByWorkflowDestinationPartyType(UserVisit userVisit, WorkflowDestinationPartyType workflowDestinationPartyType) {
        return getWorkflowDestinationSecurityRoleTransfers(userVisit, getWorkflowDestinationSecurityRolesByWorkflowDestinationPartyType(workflowDestinationPartyType));
    }
    
    public void deleteWorkflowDestinationSecurityRole(WorkflowDestinationSecurityRole workflowDestinationSecurityRole, BasePK deletedBy) {
        workflowDestinationSecurityRole.setThruTime(session.getStartTime());
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

    @Inject
    protected WorkflowDestinationStepFactory workflowDestinationStepFactory;

    @Inject
    WorkflowDestinationStepTransferCache workflowDestinationStepTransferCache;

    public WorkflowDestinationStep createWorkflowDestinationStep(WorkflowDestination workflowDestination, WorkflowStep workflowStep,
            BasePK createdBy) {
        var workflowDestinationStep = workflowDestinationStepFactory.create(
                workflowDestination, workflowStep, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(workflowDestination.getLastDetail().getWorkflowStep().getLastDetail().getWorkflowPK(), EventTypes.MODIFY, workflowDestinationStep.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return workflowDestinationStep;
    }

    public long countWorkflowDestinationStepsByWorkflowDestination(WorkflowDestination workflowDestination) {
        return session.getDslContext()
                .selectCount()
                .from(WorkflowDestinationSteps)
                .where(WorkflowDestinationSteps.WORKFLOW_DESTINATION.eq(workflowDestination.getPrimaryKey()),
                        WorkflowDestinationSteps.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countWorkflowDestinationStepsByWorkflowStep(WorkflowStep workflowStep) {
        return session.getDslContext()
                .selectCount()
                .from(WorkflowDestinationSteps)
                .where(WorkflowDestinationSteps.WORKFLOW_STEP.eq(workflowStep.getPrimaryKey()),
                        WorkflowDestinationSteps.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }
    
    private List<WorkflowDestinationStep> getWorkflowDestinationStepsByWorkflowStep(WorkflowStep workflowStep, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(WorkflowDestinationSteps.fields())
                .from(WorkflowDestinationSteps)
                .where(WorkflowDestinationSteps.WORKFLOW_STEP.eq(workflowStep.getPrimaryKey()),
                        WorkflowDestinationSteps.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(WorkflowDestinationSteps.fields())
                    .from(WorkflowDestinationSteps)
                    .join(WorkflowDestinations).onKey(WORKFLOW_DESTINATION_STEPS_WORKFLOW_DESTINATION_FK)
                    .join(WorkflowDestinationDetails).onKey(WORKFLOW_DESTINATIONS_LAST_DETAIL_FK)
                    .where(WorkflowDestinationSteps.WORKFLOW_STEP.eq(workflowStep.getPrimaryKey()),
                            WorkflowDestinationSteps.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(WorkflowDestinationDetails.SORT_ORDER, WorkflowDestinationDetails.WORKFLOW_DESTINATION_NAME),
                    WorkflowDestinationStepFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return workflowDestinationStepFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<WorkflowDestinationStep> getWorkflowDestinationStepsByWorkflowStep(WorkflowStep workflowStep) {
        return getWorkflowDestinationStepsByWorkflowStep(workflowStep, EntityPermission.READ_ONLY);
    }
    
    public List<WorkflowDestinationStep> getWorkflowDestinationStepsByWorkflowStepForUpdate(WorkflowStep workflowStep) {
        return getWorkflowDestinationStepsByWorkflowStep(workflowStep, EntityPermission.READ_WRITE);
    }
    
    private List<WorkflowDestinationStep> getWorkflowDestinationStepsByWorkflowDestination(WorkflowDestination workflowDestination, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(WorkflowDestinationSteps.fields())
                    .from(WorkflowDestinationSteps)
                    .join(WorkflowSteps).onKey(WORKFLOW_DESTINATION_STEPS_WORKFLOW_STEP_FK)
                    .join(WorkflowStepDetails).onKey(WORKFLOW_STEPS_ACTIVE_DETAIL_FK)
                    .join(Workflows).onKey(WORKFLOW_STEP_DETAILS_WORKFLOW_FK)
                    .join(WorkflowDetails).onKey(WORKFLOWS_ACTIVE_DETAIL_FK)
                    .where(WorkflowDestinationSteps.WORKFLOW_DESTINATION.eq(workflowDestination.getPrimaryKey()),
                            WorkflowDestinationSteps.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(WorkflowDetails.SORT_ORDER, WorkflowDetails.WORKFLOW_NAME, WorkflowStepDetails.SORT_ORDER, WorkflowStepDetails.WORKFLOW_STEP_NAME),
                    WorkflowDestinationStepFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(WorkflowDestinationSteps.fields())
                    .from(WorkflowDestinationSteps)
                    .where(WorkflowDestinationSteps.WORKFLOW_DESTINATION.eq(workflowDestination.getPrimaryKey()),
                            WorkflowDestinationSteps.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return workflowDestinationStepFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<WorkflowDestinationStep> getWorkflowDestinationStepsByWorkflowDestination(WorkflowDestination workflowDestination) {
        return getWorkflowDestinationStepsByWorkflowDestination(workflowDestination, EntityPermission.READ_ONLY);
    }
    
    public List<WorkflowDestinationStep> getWorkflowDestinationStepsByWorkflowDestinationForUpdate(WorkflowDestination workflowDestination) {
        return getWorkflowDestinationStepsByWorkflowDestination(workflowDestination, EntityPermission.READ_WRITE);
    }
    
    private WorkflowDestinationStep getWorkflowDestinationStep(WorkflowDestination workflowDestination, WorkflowStep workflowStep, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(WorkflowDestinationSteps.fields())
                .from(WorkflowDestinationSteps)
                .where(WorkflowDestinationSteps.WORKFLOW_DESTINATION.eq(workflowDestination.getPrimaryKey()),
                        WorkflowDestinationSteps.WORKFLOW_STEP.eq(workflowStep.getPrimaryKey()),
                        WorkflowDestinationSteps.THRU_TIME.eq(Session.MAX_TIME));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };
            
        return workflowDestinationStepFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public WorkflowDestinationStep getWorkflowDestinationStep(WorkflowDestination workflowDestination, WorkflowStep workflowStep) {
        return getWorkflowDestinationStep(workflowDestination, workflowStep, EntityPermission.READ_ONLY);
    }
    
    public WorkflowDestinationStep getWorkflowDestinationStepForUpdate(WorkflowDestination workflowDestination, WorkflowStep workflowStep) {
        return getWorkflowDestinationStep(workflowDestination, workflowStep, EntityPermission.READ_WRITE);
    }
    
    public WorkflowDestinationStepTransfer getWorkflowDestinationStepTransfer(UserVisit userVisit, WorkflowDestinationStep workflowDestinationStep) {
        return workflowDestinationStepTransferCache.getWorkflowDestinationStepTransfer(userVisit, workflowDestinationStep);
    }
    
    public List<WorkflowDestinationStepTransfer> getWorkflowDestinationStepTransfers(UserVisit userVisit, Collection<WorkflowDestinationStep> workflowDestinationSteps) {
        List<WorkflowDestinationStepTransfer> workflowDestinationStepTransfers = new ArrayList<>(workflowDestinationSteps.size());
        
        workflowDestinationSteps.forEach((workflowDestinationStep) ->
                workflowDestinationStepTransfers.add(workflowDestinationStepTransferCache.getWorkflowDestinationStepTransfer(userVisit, workflowDestinationStep))
        );
        
        return workflowDestinationStepTransfers;
    }
    
    public List<WorkflowDestinationStepTransfer> getWorkflowDestinationStepTransfersByWorkflowDestination(UserVisit userVisit, WorkflowDestination workflowDestination) {
        return getWorkflowDestinationStepTransfers(userVisit, getWorkflowDestinationStepsByWorkflowDestination(workflowDestination));
    }

    public void deleteWorkflowDestinationStep(WorkflowDestinationStep workflowDestinationStep, BasePK deletedBy) {
        workflowDestinationStep.setThruTime(session.getStartTime());
        
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

    @Inject
    protected WorkflowSelectorKindFactory workflowSelectorKindFactory;

    @Inject
    WorkflowSelectorKindTransferCache workflowSelectorKindTransferCache;

    public WorkflowSelectorKind createWorkflowSelectorKind(Workflow workflow, SelectorKind selectorKind,
            BasePK createdBy) {
        var workflowSelectorKind = workflowSelectorKindFactory.create(workflow,
                selectorKind, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(workflow.getLastDetail().getWorkflowPK(), EventTypes.MODIFY, workflowSelectorKind.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return workflowSelectorKind;
    }

    public long countWorkflowSelectorKindsByWorkflow(Workflow workflow) {
        return session.getDslContext()
                .selectCount()
                .from(WorkflowSelectorKinds)
                .where(WorkflowSelectorKinds.WORKFLOW.eq(workflow.getPrimaryKey()),
                        WorkflowSelectorKinds.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countWorkflowSelectorKindsBySelectorKind(SelectorKind selectorKind) {
        return session.getDslContext()
                .selectCount()
                .from(WorkflowSelectorKinds)
                .where(WorkflowSelectorKinds.SELECTOR_KIND.eq(selectorKind.getPrimaryKey()),
                        WorkflowSelectorKinds.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<WorkflowSelectorKind> getWorkflowSelectorKindsByWorkflow(Workflow workflow,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(WorkflowSelectorKinds.fields())
                    .from(WorkflowSelectorKinds)
                    .join(SelectorKinds).onKey(WORKFLOW_SELECTOR_KINDS_SELECTOR_KIND_FK)
                    .join(SelectorKindDetails).onKey(SELECTOR_KINDS_LAST_DETAIL_FK)
                    .where(WorkflowSelectorKinds.WORKFLOW.eq(workflow.getPrimaryKey()),
                            WorkflowSelectorKinds.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(SelectorKindDetails.SORT_ORDER, SelectorKindDetails.SELECTOR_KIND_NAME),
                    WorkflowSelectorKindFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(WorkflowSelectorKinds.fields())
                    .from(WorkflowSelectorKinds)
                    .where(WorkflowSelectorKinds.WORKFLOW.eq(workflow.getPrimaryKey()),
                            WorkflowSelectorKinds.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return workflowSelectorKindFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<WorkflowSelectorKind> getWorkflowSelectorKindsByWorkflow(Workflow workflow) {
        return getWorkflowSelectorKindsByWorkflow(workflow, EntityPermission.READ_ONLY);
    }
    
    public List<WorkflowSelectorKind> getWorkflowSelectorKindsByWorkflowForUpdate(Workflow workflow) {
        return getWorkflowSelectorKindsByWorkflow(workflow, EntityPermission.READ_WRITE);
    }
    
    private List<WorkflowSelectorKind> getWorkflowSelectorKindsBySelectorKindForUpdate(SelectorKind selectorKind,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(WorkflowSelectorKinds.fields())
                    .from(WorkflowSelectorKinds)
                    .join(Workflows).onKey(WORKFLOW_SELECTOR_KINDS_WORKFLOW_FK)
                    .join(WorkflowDetails).onKey(WORKFLOWS_LAST_DETAIL_FK)
                    .where(WorkflowSelectorKinds.SELECTOR_KIND.eq(selectorKind.getPrimaryKey()),
                            WorkflowSelectorKinds.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(WorkflowDetails.SORT_ORDER, WorkflowDetails.WORKFLOW_NAME),
                    WorkflowSelectorKindFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(WorkflowSelectorKinds.fields())
                    .from(WorkflowSelectorKinds)
                    .where(WorkflowSelectorKinds.SELECTOR_KIND.eq(selectorKind.getPrimaryKey()),
                            WorkflowSelectorKinds.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return workflowSelectorKindFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<WorkflowSelectorKind> getWorkflowSelectorKindsBySelectorKind(SelectorKind selectorKind) {
        return getWorkflowSelectorKindsBySelectorKindForUpdate(selectorKind, EntityPermission.READ_ONLY);
    }
    
    public List<WorkflowSelectorKind> getWorkflowSelectorKindsBySelectorKindForUpdate(SelectorKind selectorKind) {
        return getWorkflowSelectorKindsBySelectorKindForUpdate(selectorKind, EntityPermission.READ_WRITE);
    }
    
    private WorkflowSelectorKind getWorkflowSelectorKind(Workflow workflow, SelectorKind selectorKind, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(WorkflowSelectorKinds.fields())
                .from(WorkflowSelectorKinds)
                .where(WorkflowSelectorKinds.WORKFLOW.eq(workflow.getPrimaryKey()),
                        WorkflowSelectorKinds.SELECTOR_KIND.eq(selectorKind.getPrimaryKey()),
                        WorkflowSelectorKinds.THRU_TIME.eq(Session.MAX_TIME));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };
            
        return workflowSelectorKindFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public WorkflowSelectorKind getWorkflowSelectorKind(Workflow workflow, SelectorKind selectorKind) {
        return getWorkflowSelectorKind(workflow, selectorKind, EntityPermission.READ_ONLY);
    }
    
    public WorkflowSelectorKind getWorkflowSelectorKindForUpdate(Workflow workflow, SelectorKind selectorKind) {
        return getWorkflowSelectorKind(workflow, selectorKind, EntityPermission.READ_WRITE);
    }
    
    public WorkflowSelectorKindTransfer getWorkflowSelectorKindTransfer(UserVisit userVisit, WorkflowSelectorKind workflowSelectorKind) {
        return workflowSelectorKindTransferCache.getWorkflowSelectorKindTransfer(userVisit, workflowSelectorKind);
    }
    
    public List<WorkflowSelectorKindTransfer> getWorkflowSelectorKindTransfers(UserVisit userVisit, Collection<WorkflowSelectorKind> workflowSelectorKinds) {
        List<WorkflowSelectorKindTransfer> workflowSelectorKindTransfers = new ArrayList<>(workflowSelectorKinds.size());
        
        workflowSelectorKinds.forEach((workflowSelectorKind) ->
                workflowSelectorKindTransfers.add(workflowSelectorKindTransferCache.getWorkflowSelectorKindTransfer(userVisit, workflowSelectorKind))
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
        workflowSelectorKind.setThruTime(session.getStartTime());
        
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

    @Inject
    protected WorkflowEntityStatusFactory workflowEntityStatusFactory;

    @Inject
    WorkflowEntityStatusTransferCache workflowEntityStatusTransferCache;

    public WorkflowEntityStatus createWorkflowEntityStatus(EntityInstance entityInstance, WorkflowStep workflowStep,
            WorkEffortScope workEffortScope, BasePK createdBy) {
        var workflowEntityStatus = workflowEntityStatusFactory.create(entityInstance, workflowStep, workEffortScope, session.getStartTime(),
                Session.MAX_TIME);
        
        sendEvent(entityInstance, EventTypes.MODIFY, workflowEntityStatus.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return workflowEntityStatus;
    }

    public long countWorkflowEntityStatusesByEntityInstance(EntityInstance entityInstance) {
        return session.getDslContext()
                .selectCount()
                .from(WorkflowEntityStatuses)
                .where(WorkflowEntityStatuses.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                        WorkflowEntityStatuses.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countWorkflowEntityStatusesByWorkflowStep(WorkflowStep workflowStep) {
        return session.getDslContext()
                .selectCount()
                .from(WorkflowEntityStatuses)
                .where(WorkflowEntityStatuses.WORKFLOW_STEP.eq(workflowStep.getPrimaryKey()),
                        WorkflowEntityStatuses.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countWorkflowEntityStatusesByWorkEffortScope(WorkEffortScope workEffortScope) {
        return session.getDslContext()
                .selectCount()
                .from(WorkflowEntityStatuses)
                .where(WorkflowEntityStatuses.WORK_EFFORT_SCOPE.eq(workEffortScope.getPrimaryKey()),
                        WorkflowEntityStatuses.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countWorkflowEntityStatusesByWorkflow(Workflow workflow) {
        return session.getDslContext()
                .selectCount()
                .from(WorkflowEntityStatuses)
                .join(WorkflowSteps).onKey(WORKFLOW_ENTITY_STATUSES_WORKFLOW_STEP_FK)
                .join(WorkflowStepDetails).onKey(WORKFLOW_STEPS_ACTIVE_DETAIL_FK)
                .join(Workflows).onKey(WORKFLOW_STEP_DETAILS_WORKFLOW_FK)
                .join(WorkflowDetails).onKey(WORKFLOWS_ACTIVE_DETAIL_FK)
                .where(WorkflowEntityStatuses.THRU_TIME.eq(Session.MAX_TIME),
                        WorkflowStepDetails.WORKFLOW.eq(workflow.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countWorkflowEntityStatusesByWorkflowAndEntityInstance(Workflow workflow, EntityInstance entityInstance) {
        return session.getDslContext()
                .selectCount()
                .from(WorkflowEntityStatuses)
                .join(WorkflowStepDetails).on(WorkflowEntityStatuses.WORKFLOW_STEP.eq(WorkflowStepDetails.WORKFLOW_STEP))
                .where(WorkflowEntityStatuses.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                        WorkflowEntityStatuses.THRU_TIME.eq(Session.MAX_TIME),
                        WorkflowStepDetails.WORKFLOW.eq(workflow.getPrimaryKey()),
                        WorkflowStepDetails.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<WorkflowEntityStatus> getWorkflowEntityStatusesByWorkEffortScope(WorkEffortScope workEffortScope,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(WorkflowEntityStatuses.fields())
                    .from(WorkflowEntityStatuses)
                    .join(WorkflowSteps).onKey(WORKFLOW_ENTITY_STATUSES_WORKFLOW_STEP_FK)
                    .join(WorkflowStepDetails).onKey(WORKFLOW_STEPS_ACTIVE_DETAIL_FK)
                    .join(Workflows).onKey(WORKFLOW_STEP_DETAILS_WORKFLOW_FK)
                    .join(WorkflowDetails).onKey(WORKFLOWS_ACTIVE_DETAIL_FK)
                    .where(WorkflowEntityStatuses.WORK_EFFORT_SCOPE.eq(workEffortScope.getPrimaryKey()),
                            WorkflowEntityStatuses.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(WorkflowDetails.SORT_ORDER, WorkflowDetails.WORKFLOW_NAME, WorkflowStepDetails.SORT_ORDER, WorkflowStepDetails.WORKFLOW_STEP_NAME),
                    WorkflowEntityStatusFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(WorkflowEntityStatuses.fields())
                    .from(WorkflowEntityStatuses)
                    .where(WorkflowEntityStatuses.WORK_EFFORT_SCOPE.eq(workEffortScope.getPrimaryKey()),
                            WorkflowEntityStatuses.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return workflowEntityStatusFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<WorkflowEntityStatus> getWorkflowEntityStatusesByWorkEffortScope(WorkEffortScope workEffortScope) {
        return getWorkflowEntityStatusesByWorkEffortScope(workEffortScope, EntityPermission.READ_ONLY);
    }
    
    public List<WorkflowEntityStatus> getWorkflowEntityStatusesByWorkEffortScopeForUpdate(WorkEffortScope workEffortScope) {
        return getWorkflowEntityStatusesByWorkEffortScope(workEffortScope, EntityPermission.READ_WRITE);
    }

    public List<WorkflowEntityStatus> getWorkflowEntityStatusesByEntityInstance(Workflow workflow, EntityInstance entityInstance, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(WorkflowEntityStatuses.fields())
                .from(WorkflowEntityStatuses)
                .join(WorkflowStepDetails).on(WorkflowEntityStatuses.WORKFLOW_STEP.eq(WorkflowStepDetails.WORKFLOW_STEP))
                .where(WorkflowEntityStatuses.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                        WorkflowEntityStatuses.THRU_TIME.eq(Session.MAX_TIME),
                        WorkflowStepDetails.WORKFLOW.eq(workflow.getPrimaryKey()),
                        WorkflowStepDetails.THRU_TIME.eq(Session.MAX_TIME));
        
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(WorkflowStepDetails.SORT_ORDER, WorkflowStepDetails.WORKFLOW_STEP_NAME),
                    WorkflowEntityStatusFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };
            
        return workflowEntityStatusFactory.getEntitiesFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(WorkflowEntityStatuses.fields())
                    .from(WorkflowEntityStatuses)
                    .join(WorkflowSteps).onKey(WORKFLOW_ENTITY_STATUSES_WORKFLOW_STEP_FK)
                    .join(WorkflowStepDetails).onKey(WORKFLOW_STEPS_LAST_DETAIL_FK)
                    .join(Workflows).onKey(WORKFLOW_STEP_DETAILS_WORKFLOW_FK)
                    .join(WorkflowDetails).onKey(WORKFLOWS_LAST_DETAIL_FK)
                    .where(WorkflowEntityStatuses.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                            WorkflowEntityStatuses.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(WorkflowStepDetails.SORT_ORDER, WorkflowStepDetails.WORKFLOW_STEP_NAME, WorkflowDetails.SORT_ORDER, WorkflowDetails.WORKFLOW_NAME),
                    WorkflowEntityStatusFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(WorkflowEntityStatuses.fields())
                    .from(WorkflowEntityStatuses)
                    .where(WorkflowEntityStatuses.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                            WorkflowEntityStatuses.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return workflowEntityStatusFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<WorkflowEntityStatus> getWorkflowEntityStatusesByEntityInstance(EntityInstance entityInstance) {
        return getWorkflowEntityStatusesByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }
    
    public List<WorkflowEntityStatus> getWorkflowEntityStatusesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getWorkflowEntityStatusesByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    public List<WorkflowEntityStatus> getWorkflowEntityStatusesByWorkflow(Workflow workflow) {
        var query = session.applyLimit(session.getDslContext()
                .select(WorkflowEntityStatuses.fields())
                .from(WorkflowEntityStatuses)
                .join(WorkflowSteps).onKey(WORKFLOW_ENTITY_STATUSES_WORKFLOW_STEP_FK)
                .join(WorkflowStepDetails).onKey(WORKFLOW_STEPS_ACTIVE_DETAIL_FK)
                .join(Workflows).onKey(WORKFLOW_STEP_DETAILS_WORKFLOW_FK)
                .join(WorkflowDetails).onKey(WORKFLOWS_ACTIVE_DETAIL_FK)
                .where(WorkflowEntityStatuses.THRU_TIME.eq(Session.MAX_TIME),
                        WorkflowStepDetails.WORKFLOW.eq(workflow.getPrimaryKey()))
                .orderBy(WorkflowDetails.SORT_ORDER, WorkflowDetails.WORKFLOW_NAME, WorkflowStepDetails.SORT_ORDER, WorkflowStepDetails.WORKFLOW_STEP_NAME),
                WorkflowEntityStatusFactory.class);
        
        return workflowEntityStatusFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }

    private List<WorkflowEntityStatus> getWorkflowEntityStatusesByWorkflowStep(WorkflowStep workflowStep, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(WorkflowEntityStatuses.fields())
                .from(WorkflowEntityStatuses)
                .where(WorkflowEntityStatuses.WORKFLOW_STEP.eq(workflowStep.getPrimaryKey()),
                        WorkflowEntityStatuses.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(WorkflowEntityStatuses.fields())
                    .from(WorkflowEntityStatuses)
                    .join(EntityInstances).onKey(WORKFLOW_ENTITY_STATUSES_ENTITY_INSTANCE_FK)
                    .join(EntityTypes).onKey(ENTITY_INSTANCES_ENTITY_TYPE_FK)
                    .join(EntityTypeDetails).onKey(ENTITY_TYPES_LAST_DETAIL_FK)
                    .join(ComponentVendors).onKey(ENTITY_TYPE_DETAILS_COMPONENT_VENDOR_FK)
                    .join(ComponentVendorDetails).onKey(COMPONENT_VENDORS_LAST_DETAIL_FK)
                    .where(WorkflowEntityStatuses.WORKFLOW_STEP.eq(workflowStep.getPrimaryKey()),
                            WorkflowEntityStatuses.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ComponentVendorDetails.COMPONENT_VENDOR_NAME, EntityTypeDetails.ENTITY_TYPE_NAME, EntityInstances.ENTITY_UNIQUE),
                    WorkflowEntityStatusFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return workflowEntityStatusFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<WorkflowEntityStatus> getWorkflowEntityStatusesByWorkflowStep(WorkflowStep workflowStep) {
        return getWorkflowEntityStatusesByWorkflowStep(workflowStep, EntityPermission.READ_ONLY);
    }

    public List<WorkflowEntityStatus> getWorkflowEntityStatusesByWorkflowStepForUpdate(WorkflowStep workflowStep) {
        return getWorkflowEntityStatusesByWorkflowStep(workflowStep, EntityPermission.READ_WRITE);
    }

    private List<WorkflowEntityStatus> getWorkflowEntityStatusesByWorkflowAndEntityType(Workflow workflow, EntityType entityType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(WorkflowEntityStatuses.fields())
                .from(WorkflowEntityStatuses)
                .join(WorkflowSteps).onKey(WORKFLOW_ENTITY_STATUSES_WORKFLOW_STEP_FK)
                .join(WorkflowStepDetails).onKey(WORKFLOW_STEPS_ACTIVE_DETAIL_FK)
                .join(EntityInstances).onKey(WORKFLOW_ENTITY_STATUSES_ENTITY_INSTANCE_FK)
                .where(WorkflowStepDetails.WORKFLOW.eq(workflow.getPrimaryKey()),
                        EntityInstances.ENTITY_TYPE.eq(entityType.getPrimaryKey()),
                        WorkflowEntityStatuses.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(EntityInstances.ENTITY_UNIQUE),
                    WorkflowEntityStatusFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return workflowEntityStatusFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<WorkflowEntityStatus> getWorkflowEntityStatusesByWorkflowAndEntityType(Workflow workflow, EntityType entityType) {
        return getWorkflowEntityStatusesByWorkflowAndEntityType(workflow, entityType, EntityPermission.READ_ONLY);
    }

    public List<WorkflowEntityStatus> getWorkflowEntityStatusesByWorkflowAndEntityTypeForUpdate(Workflow workflow, EntityType entityType) {
        return getWorkflowEntityStatusesByWorkflowAndEntityType(workflow, entityType, EntityPermission.READ_WRITE);
    }

    public WorkflowEntityStatusTransfer getWorkflowEntityStatusTransfer(UserVisit userVisit, WorkflowEntityStatus workflowEntityStatus) {
        return workflowEntityStatusTransferCache.getWorkflowEntityStatusTransfer(userVisit, workflowEntityStatus);
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
            workflowEntityStatusTransfers.add(workflowEntityStatusTransferCache.getWorkflowEntityStatusTransfer(userVisit, workflowEntityStatus));
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
        
        workflowEntityStatus.setThruTime(session.getStartTime());
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

    @Inject
    protected WorkflowTriggerFactory workflowTriggerFactory;

    public WorkflowTrigger createWorkflowTrigger(WorkflowEntityStatus workflowEntityStatus, Long triggerTime, Boolean errorsOccurred) {
        return workflowTriggerFactory.create(workflowEntityStatus, triggerTime, errorsOccurred);
    }
    
    private WorkflowTrigger getWorkflowTrigger(WorkflowEntityStatus workflowEntityStatus, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(WorkflowTriggers.fields())
                .from(WorkflowTriggers)
                .where(WorkflowTriggers.WORKFLOW_ENTITY_STATUS.eq(workflowEntityStatus.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return workflowTriggerFactory.getEntityFromQuery(entityPermission, query);
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
    
    public List<WorkflowTrigger> getWorkflowTriggersByTriggerTime(Long triggerTime) {
        var query = session.applyLimit(session.getDslContext()
                .select(WorkflowTriggers.fields())
                .from(WorkflowTriggers)
                .where(WorkflowTriggers.TRIGGER_TIME.lt(triggerTime))
                .orderBy(WorkflowTriggers.TRIGGER_TIME, WorkflowTriggers.WORKFLOW_TRIGGER),
                WorkflowTriggerFactory.class);

        return workflowTriggerFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
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
        var workflowEntranceSteps = getWorkflowEntranceStepsByWorkflowEntrance(workflowEntrance);
        var workEffortScope = workEffort == null ? null : workEffort.getLastDetail().getWorkEffortScope();

        workflowEntranceSteps.stream().map(WorkflowEntranceStep::getWorkflowStep).forEach((workflowStep) -> {
            var workflowEntityStatus = createWorkflowEntityStatus(entityInstance, workflowStep, workEffortScope, createdBy);

            if(workEffortScope != null) {
                var workRequirementScopes = workRequirementControl.getWorkRequirementScopesByWorkRequirementType(workEffortScope,
                        workflowStep);
                workRequirementScopes.forEach((workRequirementScope) -> {
                    workRequirementLogic.createWorkRequirement(session, workEffort, workRequirementScope, null, null, null, createdBy);
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

            if(selectorKindName.equals(com.echothree.model.control.selector.common.SelectorKinds.POSTAL_ADDRESS.name())) {
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
        if(eea == null || workflowSecurityLogic.checkTransitionEntityInWorkflow(eea, workflowDestination, modifiedBy)) {
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
