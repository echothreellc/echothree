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

package com.echothree.model.control.vendor.server.logic;

import com.echothree.control.user.core.common.spec.UniversalEntitySpec;
import com.echothree.control.user.vendor.common.spec.VendorUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.common.exception.UnknownPartyNameException;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.common.exception.MissingDefaultSequenceException;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.control.user.server.logic.UserKeyLogic;
import com.echothree.model.control.user.server.logic.UserSessionLogic;
import com.echothree.model.control.vendor.common.exception.UnknownVendorNameException;
import com.echothree.model.control.vendor.common.exception.UnknownVendorStatusChoiceException;
import com.echothree.model.control.vendor.common.workflow.VendorStatusConstants;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.control.workflow.server.logic.WorkflowDestinationLogic;
import com.echothree.model.control.workflow.server.logic.WorkflowLogic;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.filter.server.entity.Filter;
import com.echothree.model.data.item.server.entity.ItemAliasType;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.vendor.server.entity.Vendor;
import com.echothree.model.data.vendor.server.entity.VendorType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class VendorLogic
        extends BaseLogic {

    protected VendorLogic() {
        super();
    }

    public static VendorLogic getInstance() {
        return CDI.current().select(VendorLogic.class).get();
    }

    private String getVendorName(final ExecutionErrorAccumulator eea) {
        String vendorName = null;
        var vendorSequence = SequenceGeneratorLogic.getInstance().getDefaultSequence(eea, SequenceTypes.VENDOR.name());

        if(!hasExecutionErrors(eea)) {
            vendorName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(eea, vendorSequence);
        } else {
            handleExecutionError(MissingDefaultSequenceException.class, eea, ExecutionErrors.MissingDefaultSequence.name(),
                    SequenceTypes.VENDOR.name());
        }

        return vendorName;
    }
    
    public Vendor createVendor(final ExecutionErrorAccumulator eea, final Party party, String vendorName,
            final VendorType vendorType, final Integer minimumPurchaseOrderLines, final Integer maximumPurchaseOrderLines,
            final Long minimumPurchaseOrderAmount, final Long maximumPurchaseOrderAmount,
            final Boolean useItemPurchasingCategories, final ItemAliasType defaultItemAliasType,
            final CancellationPolicy cancellationPolicy, final ReturnPolicy returnPolicy, final GlAccount apGlAccount,
            final Boolean holdUntilComplete, final Boolean allowBackorders, final Boolean allowSubstitutions,
            final Boolean allowCombiningShipments, final Boolean requireReference, final Boolean allowReferenceDuplicates,
            final String referenceValidationPattern, final Selector vendorItemSelector, final Filter vendorItemCostFilter,
            final BasePK createdBy) {
        var vendorControl = Session.getModelController(VendorControl.class);

        if(vendorName == null) {
            vendorName = getVendorName(eea);
        }

        return vendorControl.createVendor(party, vendorName, vendorType, minimumPurchaseOrderLines, maximumPurchaseOrderLines,
                minimumPurchaseOrderAmount, maximumPurchaseOrderAmount, useItemPurchasingCategories, defaultItemAliasType,
                cancellationPolicy, returnPolicy, apGlAccount, holdUntilComplete, allowBackorders, allowSubstitutions,
                allowCombiningShipments, requireReference, allowReferenceDuplicates, referenceValidationPattern,
                vendorItemSelector, vendorItemCostFilter, createdBy);
    }

    public Vendor getVendorByName(final ExecutionErrorAccumulator eea, final String vendorName, final String partyName,
            final UniversalEntitySpec universalEntitySpec, final EntityPermission entityPermission) {
        var parameterCount = (vendorName == null ? 0 : 1) + (partyName == null ? 0 : 1) +
                EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalEntitySpec);
        Vendor vendor = null;

        if(parameterCount == 1) {
            var vendorControl = Session.getModelController(VendorControl.class);
            var partyControl = Session.getModelController(PartyControl.class);

            if(vendorName != null) {
                vendor = vendorControl.getVendorByName(vendorName, entityPermission);

                if(vendor == null) {
                    handleExecutionError(UnknownVendorNameException.class, eea, ExecutionErrors.UnknownVendorName.name(), vendorName);
                }
            } else if(partyName != null) {
                var party = partyControl.getPartyByName(partyName);

                if(party != null) {
                    PartyLogic.getInstance().checkPartyType(eea, party, PartyTypes.VENDOR.name());

                    vendor = vendorControl.getVendor(party, entityPermission);
                } else {
                    handleExecutionError(UnknownPartyNameException.class, eea, ExecutionErrors.UnknownPartyName.name(), partyName);
                }
            } else if(universalEntitySpec != null){
                var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalEntitySpec,
                        ComponentVendors.ECHO_THREE.name(), EntityTypes.Party.name());

                if(!eea.hasExecutionErrors()) {
                    var party = partyControl.getPartyByEntityInstance(entityInstance);

                    PartyLogic.getInstance().checkPartyType(eea, party, PartyTypes.VENDOR.name());

                    vendor = vendorControl.getVendor(party, entityPermission);
                }
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return vendor;
    }

    public Vendor getVendorByName(final ExecutionErrorAccumulator eea, final String vendorName, final String partyName,
            final UniversalEntitySpec universalEntitySpec) {
        return getVendorByName(eea, vendorName, partyName, universalEntitySpec, EntityPermission.READ_ONLY);
    }

    public Vendor getVendorByNameForUpdate(final ExecutionErrorAccumulator eea, final String vendorName, final String partyName,
            final UniversalEntitySpec universalEntitySpec) {
        return getVendorByName(eea, vendorName, partyName, universalEntitySpec, EntityPermission.READ_WRITE);
    }

    public Vendor getVendorByUniversalSpec(final ExecutionErrorAccumulator eea, final VendorUniversalSpec universalSpec) {
        return getVendorByName(eea, universalSpec.getVendorName(), universalSpec.getPartyName(), universalSpec);
    }

    public Vendor getVendorByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea, final VendorUniversalSpec universalSpec) {
        return getVendorByNameForUpdate(eea, universalSpec.getVendorName(), universalSpec.getPartyName(), universalSpec);
    }

    public void setVendorStatus(final Session session, ExecutionErrorAccumulator eea, Party party, String vendorStatusChoice, PartyPK modifiedBy) {
        var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var workflow = WorkflowLogic.getInstance().getWorkflowByName(eea, VendorStatusConstants.Workflow_VENDOR_STATUS);
        var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(party.getPrimaryKey());
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdate(workflow, entityInstance);
        var workflowDestination = vendorStatusChoice == null ? null : workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), vendorStatusChoice);

        if(workflowDestination != null || vendorStatusChoice == null) {
            var workflowDestinationLogic = WorkflowDestinationLogic.getInstance();
            var currentWorkflowStepName = workflowEntityStatus.getWorkflowStep().getLastDetail().getWorkflowStepName();
            var map = workflowDestinationLogic.getWorkflowDestinationsAsMap(workflowDestination);
            Long triggerTime = null;

            if(currentWorkflowStepName.equals(VendorStatusConstants.WorkflowStep_ACTIVE)) {
                if(workflowDestinationLogic.workflowDestinationMapContainsStep(map, VendorStatusConstants.Workflow_VENDOR_STATUS, VendorStatusConstants.WorkflowStep_INACTIVE)) {
                    UserKeyLogic.getInstance().clearUserKeysByParty(party);
                    UserSessionLogic.getInstance().deleteUserSessionsByParty(party);
                }
            } else if(currentWorkflowStepName.equals(VendorStatusConstants.WorkflowStep_INACTIVE)) {
                if(workflowDestinationLogic.workflowDestinationMapContainsStep(map, VendorStatusConstants.Workflow_VENDOR_STATUS, VendorStatusConstants.WorkflowStep_ACTIVE)) {
                    // Nothing at this time.
                }
            }

            if(eea == null || !eea.hasExecutionErrors()) {
                workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, triggerTime, modifiedBy);
            }
        } else {
            handleExecutionError(UnknownVendorStatusChoiceException.class, eea, ExecutionErrors.UnknownVendorStatusChoice.name(), vendorStatusChoice);
        }
    }

}
