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

package com.echothree.model.control.purchase.server.logic;

import com.echothree.model.control.cancellationpolicy.common.CancellationKinds;
import com.echothree.model.control.cancellationpolicy.server.logic.CancellationPolicyLogic;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.order.common.OrderRoleTypes;
import com.echothree.model.control.order.common.OrderTypes;
import com.echothree.model.control.order.server.control.OrderControl;
import com.echothree.model.control.order.server.control.OrderRoleControl;
import com.echothree.model.control.order.server.logic.BaseOrderLogic;
import com.echothree.model.control.order.server.logic.OrderPriorityLogic;
import com.echothree.model.control.purchase.common.choice.PurchaseOrderStatusChoicesBean;
import com.echothree.model.control.purchase.common.exception.DuplicateHandlingInPurchaseOrderStatusTransitionException;
import com.echothree.model.control.purchase.common.exception.InvalidPurchaseOrderReferenceException;
import com.echothree.model.control.purchase.common.exception.InvalidPurchaseOrderStatusException;
import com.echothree.model.control.purchase.common.exception.PurchaseOrderDuplicateReferenceException;
import com.echothree.model.control.purchase.common.exception.PurchaseOrderReferenceRequiredException;
import com.echothree.model.control.purchase.common.exception.UnhandledPurchaseOrderStatusTransitionException;
import com.echothree.model.control.purchase.common.exception.UnknownPurchaseOrderStatusChoiceException;
import com.echothree.model.control.purchase.common.workflow.PurchaseOrderStatusConstants;
import com.echothree.model.control.returnpolicy.common.ReturnKinds;
import com.echothree.model.control.returnpolicy.server.logic.ReturnPolicyLogic;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.control.shipment.server.control.PartyFreeOnBoardControl;
import com.echothree.model.control.shipment.server.logic.FreeOnBoardLogic;
import com.echothree.model.control.term.server.control.TermControl;
import com.echothree.model.control.term.server.logic.TermLogic;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.control.vendor.server.logic.VendorLogic;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.control.workflow.server.logic.WorkflowDestinationLogic;
import com.echothree.model.control.workflow.server.logic.WorkflowLogic;
import com.echothree.model.control.workflow.server.logic.WorkflowStepLogic;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.order.server.entity.Order;
import com.echothree.model.data.order.server.entity.OrderPriority;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.shipment.server.entity.FreeOnBoard;
import com.echothree.model.data.term.server.entity.Term;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.vendor.server.entity.Vendor;
import com.echothree.model.data.vendor.server.entity.VendorType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class PurchaseOrderLogic
        extends BaseOrderLogic {

    protected PurchaseOrderLogic() {
        super();
    }

    public static PurchaseOrderLogic getInstance() {
        return CDI.current().select(PurchaseOrderLogic.class).get();
    }
    
    public void validatePurchaseOrderReference(final ExecutionErrorAccumulator eea, final String reference,
            final Vendor vendor) {
        var requireReference = vendor.getRequireReference();

        if(requireReference != null) {
            var allowReferenceDuplicates = vendor.getAllowReferenceDuplicates();
            var referenceValidationPattern = vendor.getReferenceValidationPattern();

            if(requireReference && reference == null) {
                handleExecutionError(PurchaseOrderReferenceRequiredException.class, eea, ExecutionErrors.PurchaseOrderReferenceRequired.name());
            } else if(reference != null) {
                var orderControl = Session.getModelController(OrderControl.class);

                if(!allowReferenceDuplicates && orderControl.countOrdersByBillToAndReference(vendor.getParty(), reference) != 0) {
                    handleExecutionError(PurchaseOrderDuplicateReferenceException.class, eea, ExecutionErrors.PurchaseOrderDuplicateReference.name());
                }

                if(referenceValidationPattern != null && !reference.matches(referenceValidationPattern)) {
                    handleExecutionError(InvalidPurchaseOrderReferenceException.class, eea, ExecutionErrors.InvalidPurchaseOrderReference.name());
                }
            }
        }
    }

    public CancellationPolicy getCancellationPolicy(final ExecutionErrorAccumulator eea, final VendorType vendorType, final Vendor billToVendor) {
        return CancellationPolicyLogic.getInstance().getDefaultCancellationPolicyByKind(eea, CancellationKinds.VENDOR_CANCELLATION.name(),
                new CancellationPolicy[]{
                    billToVendor == null ? null : billToVendor.getCancellationPolicy(),
                    vendorType.getLastDetail().getDefaultCancellationPolicy()
                });
    }

    public ReturnPolicy getReturnPolicy(final ExecutionErrorAccumulator eea, final VendorType vendorType, final Vendor billToVendor) {
        return ReturnPolicyLogic.getInstance().getDefaultReturnPolicyByKind(eea, ReturnKinds.VENDOR_RETURN.name(),
                new ReturnPolicy[]{
                    billToVendor == null ? null : billToVendor.getReturnPolicy(),
                    vendorType.getLastDetail().getDefaultReturnPolicy()
                });
    }

    /**
     * Create a new Purchase Order using appropriate defaults where possible for Optional parameters.
     *
     * @param session Required.
     * @param eea Required.
     * @param userVisit Required.
     * @param vendorParty Required.
     * @param holdUntilComplete Optional.
     * @param allowBackorders Optional.
     * @param allowSubstitutions Optional.
     * @param allowCombiningShipments Optional.
     * @param reference Optional.
     * @param term Optional.
     * @param workflowEntranceName Optional.
     * @param createdByParty Required.
     * @return The newly created Order, or null if there was an error.
     */
    public Order createPurchaseOrder(final Session session, final ExecutionErrorAccumulator eea, final UserVisit userVisit,
            final Party vendorParty, Boolean holdUntilComplete, Boolean allowBackorders, Boolean allowSubstitutions,
            Boolean allowCombiningShipments, final String reference, Term term, FreeOnBoard freeOnBoard,
            final String workflowEntranceName, final Party createdByParty) {
        var orderType = getOrderTypeByName(eea, OrderTypes.PURCHASE_ORDER.name());
        var billToOrderRoleType = getOrderRoleTypeByName(eea, OrderRoleTypes.BILL_TO.name());
        var placingOrderRoleType = getOrderRoleTypeByName(eea, OrderRoleTypes.PLACING.name());
        Order order = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            var orderControl = Session.getModelController(OrderControl.class);
            var partyFreeOnBoardControl = Session.getModelController(PartyFreeOnBoardControl.class);
            var termControl = Session.getModelController(TermControl.class);
            var userControl = Session.getModelController(UserControl.class);
            var vendorControl = Session.getModelController(VendorControl.class);
            var currency = userControl.getPreferredCurrencyFromParty(vendorParty);
            var vendor = vendorControl.getVendor(vendorParty);
            var vendorType = vendor.getVendorType();

            holdUntilComplete = holdUntilComplete == null ? vendor.getHoldUntilComplete() : holdUntilComplete;
            allowBackorders = allowBackorders == null ? vendor.getAllowBackorders() : allowBackorders;
            allowSubstitutions = allowSubstitutions == null ? vendor.getAllowSubstitutions() : allowSubstitutions;
            allowCombiningShipments = allowCombiningShipments == null ? vendor.getAllowCombiningShipments() : allowCombiningShipments;

            term = term == null ? termControl.getPartyTerm(vendorParty).getTerm() : term;
            freeOnBoard = freeOnBoard == null ? partyFreeOnBoardControl.getPartyFreeOnBoard(vendorParty).getFreeOnBoard() : freeOnBoard;

            var cancellationPolicy = getCancellationPolicy(eea, vendorType, vendor);
            var returnPolicy = getReturnPolicy(eea, vendorType, vendor);

            validatePurchaseOrderReference(eea, reference, vendor);

            var sequence = SequenceGeneratorLogic.getInstance().getDefaultSequence(eea, SequenceTypes.PURCHASE_ORDER.name());

            if(eea == null || !eea.hasExecutionErrors()) {
                var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
                var orderRoleControl = Session.getModelController(OrderRoleControl.class);
                var workflowControl = Session.getModelController(WorkflowControl.class);
                var userSesson = userControl.getUserSessionByUserVisit(userVisit);;
                var createdByPartyPK = createdByParty.getPrimaryKey();

                order = createOrder(eea, orderType, sequence, null, currency, holdUntilComplete, allowBackorders,
                        allowSubstitutions, allowCombiningShipments, term, freeOnBoard, reference, null,
                        cancellationPolicy, returnPolicy, null, createdByPartyPK);

                orderControl.createOrderUserVisit(order, userVisit);

                var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(order.getPrimaryKey());
                workflowControl.addEntityToWorkflowUsingNames(null, PurchaseOrderStatusConstants.Workflow_PURCHASE_ORDER_STATUS,
                        workflowEntranceName, entityInstance, null, null, createdByPartyPK);

                orderRoleControl.createOrderRole(order, userSesson.getPartyRelationship().getFromParty(), billToOrderRoleType, createdByPartyPK);

                orderRoleControl.createOrderRole(order, createdByParty, placingOrderRoleType, createdByPartyPK);
            }
        }

        return order;
    }

    public Order createPurchaseOrder(final Session session, final ExecutionErrorAccumulator eea, final UserVisit userVisit,
            final String vendorName, final String termName, final String strHoldUntilComplete, final String strAllowBackorders,
            final String strAllowSubstitutions, final String strAllowCombiningShipments, final String reference, final String freeOnBoardName,
            final String workflowEntranceName, final Party createdByParty) {
        var vendor = VendorLogic.getInstance().getVendorByName(eea, vendorName, null, null);
        var term = termName == null ? null : TermLogic.getInstance().getTermByName(eea, termName);
        var freeOnBoard = freeOnBoardName == null ? null : FreeOnBoardLogic.getInstance().getFreeOnBoardByName(eea, freeOnBoardName);
        Order order = null;

        if(!eea.hasExecutionErrors()) {
            var holdUntilComplete = strHoldUntilComplete == null ? null : Boolean.valueOf(strHoldUntilComplete);
            var allowBackorders = strAllowBackorders == null ? null : Boolean.valueOf(strAllowBackorders);
            var allowSubstitutions = strAllowSubstitutions == null ? null : Boolean.valueOf(strAllowSubstitutions);
            var allowCombiningShipments = strAllowCombiningShipments == null ? null : Boolean.valueOf(strAllowCombiningShipments);

            order = createPurchaseOrder(session, eea, userVisit, vendor.getParty(), holdUntilComplete, allowBackorders,
                allowSubstitutions, allowCombiningShipments, reference, term, freeOnBoard, workflowEntranceName,
                createdByParty);
        }

        return order;
    }

    public boolean isOrderInWorkflowSteps(final ExecutionErrorAccumulator eea, final Order order, final String... workflowStepNames) {
        return isOrderInWorkflowSteps(eea, getEntityInstanceByBaseEntity(order), workflowStepNames);
    }

    public boolean isOrderInWorkflowSteps(final ExecutionErrorAccumulator eea, final EntityInstance entityInstance, final String... workflowStepNames) {
        return !WorkflowStepLogic.getInstance().isEntityInWorkflowSteps(eea, PurchaseOrderStatusConstants.Workflow_PURCHASE_ORDER_STATUS, entityInstance,
                workflowStepNames).isEmpty();
    }

    public Order getOrderByName(final ExecutionErrorAccumulator eea, final String orderName) {
        return getOrderByName(eea, OrderTypes.PURCHASE_ORDER.name(), orderName);
    }

    public Order getOrderByNameForUpdate(final ExecutionErrorAccumulator eea, final String orderName) {
        return getOrderByNameForUpdate(eea, OrderTypes.PURCHASE_ORDER.name(), orderName);
    }

    public OrderPriority getOrderPriorityByName(final ExecutionErrorAccumulator eea, final String orderPriorityName) {
        return OrderPriorityLogic.getInstance().getOrderPriorityByName(eea, OrderTypes.PURCHASE_ORDER.name(), orderPriorityName);
    }

    public OrderPriority getOrderPriorityByNameForUpdate(final ExecutionErrorAccumulator eea, final String orderPriorityName) {
        return OrderPriorityLogic.getInstance().getOrderPriorityByNameForUpdate(eea, OrderTypes.PURCHASE_ORDER.name(), orderPriorityName);
    }

    public PurchaseOrderStatusChoicesBean getPurchaseOrderStatusChoices(final String defaultOrderStatusChoice, final Language language, final boolean allowNullChoice,
            final Order order, final PartyPK partyPK) {
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var purchaseOrderStatusChoicesBean = new PurchaseOrderStatusChoicesBean();

        if(order == null) {
            workflowControl.getWorkflowEntranceChoices(purchaseOrderStatusChoicesBean, defaultOrderStatusChoice, language, allowNullChoice,
                    workflowControl.getWorkflowByName(PurchaseOrderStatusConstants.Workflow_PURCHASE_ORDER_STATUS), partyPK);
        } else {
            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(order.getPrimaryKey());
            var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(PurchaseOrderStatusConstants.Workflow_PURCHASE_ORDER_STATUS, entityInstance);

            workflowControl.getWorkflowDestinationChoices(purchaseOrderStatusChoicesBean, defaultOrderStatusChoice, language, allowNullChoice, workflowEntityStatus.getWorkflowStep(), partyPK);
        }

        return purchaseOrderStatusChoicesBean;
    }

    public void setPurchaseOrderStatus(final Session session, final ExecutionErrorAccumulator eea, final Order order, final String orderStatusChoice, final PartyPK modifiedBy) {
        var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var workflow = WorkflowLogic.getInstance().getWorkflowByName(eea, PurchaseOrderStatusConstants.Workflow_PURCHASE_ORDER_STATUS);
        var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(order.getPrimaryKey());
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdate(workflow, entityInstance);
        var workflowDestination = orderStatusChoice == null? null: workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), orderStatusChoice);

        if(workflowDestination != null || orderStatusChoice == null) {
            var workflowDestinationLogic = WorkflowDestinationLogic.getInstance();
            var currentWorkflowStepName = workflowEntityStatus.getWorkflowStep().getLastDetail().getWorkflowStepName();
            var map = workflowDestinationLogic.getWorkflowDestinationsAsMap(workflowDestination);
            var handled = false;
            Long triggerTime = null;

            if(currentWorkflowStepName.equals(PurchaseOrderStatusConstants.WorkflowStep_ENTRY)) {
                if(workflowDestinationLogic.workflowDestinationMapContainsStep(map, PurchaseOrderStatusConstants.Workflow_PURCHASE_ORDER_STATUS, PurchaseOrderStatusConstants.WorkflowStep_ENTRY_COMPLETE)) {
                    // TODO: What happens moving from ENTRY to ENTRY_COMPLETE?
                    handled = true;
                }

                if(workflowDestinationLogic.workflowDestinationMapContainsStep(map, PurchaseOrderStatusConstants.Workflow_PURCHASE_ORDER_STATUS, PurchaseOrderStatusConstants.WorkflowStep_CANCELED)) {
                    if(!handled) {
                        // TODO: What happens moving from ENTRY to CANCELED?
                        // TODO: Purchase Order Lines need to be canceled as well.
                        handled = true;
                    } else {
                        handleExecutionError(DuplicateHandlingInPurchaseOrderStatusTransitionException.class, eea, ExecutionErrors.DuplicateHandlingInPurchaseOrderStatusTransition.name(), orderStatusChoice);
                    }
                }
            }

            if(eea == null || !eea.hasExecutionErrors()) {
                if(handled) {
                    workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, triggerTime, modifiedBy);
                } else {
                    handleExecutionError(UnhandledPurchaseOrderStatusTransitionException.class, eea, ExecutionErrors.UnhandledPurchaseOrderStatusTransition.name(), orderStatusChoice);
                }
            }
        } else {
            handleExecutionError(UnknownPurchaseOrderStatusChoiceException.class, eea, ExecutionErrors.UnknownPurchaseOrderStatusChoice.name(), orderStatusChoice);
        }
    }

    /** Check to see if an Order is available for modification, and if it isn't, send back an error.
     *
     * @param session Required.
     * @param eea Required.
     * @param order Required.
     * @param modifiedBy Required.
     */
    public void checkOrderAvailableForModification(final Session session, final ExecutionErrorAccumulator eea, final Order order, final PartyPK modifiedBy) {
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(PurchaseOrderStatusConstants.Workflow_PURCHASE_ORDER_STATUS, getEntityInstanceByBaseEntity(order));
        var workflowStepName = workflowEntityStatus.getWorkflowStep().getLastDetail().getWorkflowStepName();

        if(!workflowStepName.equals(PurchaseOrderStatusConstants.WorkflowStep_ENTRY)) {
            handleExecutionError(InvalidPurchaseOrderStatusException.class, eea, ExecutionErrors.InvalidPurchaseOrderStatus.name(), order.getLastDetail().getOrderName(), workflowStepName);
        }
    }

    /** Find the BILL_TO Party for a given Order.
     * 
     * @param order Required.
     * @return The Party used for the BILL_TO OrderRoleType. May be null.
     */
    public Party getOrderBillToParty(final Order order) {
        var orderRoleControl = Session.getModelController(OrderRoleControl.class);
        var billToOrderRole = orderRoleControl.getOrderRoleByOrderAndOrderRoleTypeUsingNames(order, OrderRoleTypes.BILL_TO.name());
        Party party = null;
        
        if(billToOrderRole != null) {
            party = billToOrderRole.getParty();
        }
        
        return party;
    }
    
    /** Find the VendorType for a given Party.
     * 
     * @param party Optional.
     * @return The VendorType for the Party. May be null.
     */
    public VendorType getVendorTypeFromParty(final Party party) {
        var vendorControl = Session.getModelController(VendorControl.class);
        var vendor = party == null ? null : vendorControl.getVendor(party);
        VendorType vendorType = null;
        
        if(vendor != null) {
            vendorType = vendor.getVendorType();
        }
        
        return vendorType;
    }
    
    /** Find the VendorType for the BILL_TO Party for a given Order.
     * 
     * @param order Required.
     * @return The VendorType for the BILL_TO Party. May be null.
     */
    public VendorType getOrderBillToVendorType(final Order order) {
        return getVendorTypeFromParty(getOrderBillToParty(order));
    }
    
    /** Attempt to find a SHIP_TO Party for the Order. If none is found, and billToFallback is set to true, then
     * attempt to find the BILL_TO Party. If a BILL_TO Party is found, copy it to the SHIP_TO.
     * 
     * @param order Required.
     * @param billToFallback Required.
     * @param createdBy Required if billToFallback is true.
     * @return The Party that is to be used for the SHIP_TO OrderRoleType. May be null.
     */
    public Party getOrderShipToParty(final Order order, final boolean billToFallback, final BasePK createdBy) {
        var orderRoleControl = Session.getModelController(OrderRoleControl.class);
        var shipToOrderRole = orderRoleControl.getOrderRoleByOrderAndOrderRoleTypeUsingNames(order, OrderRoleTypes.SHIP_TO.name());
        
        if(shipToOrderRole == null && billToFallback) {
            shipToOrderRole = orderRoleControl.getOrderRoleByOrderAndOrderRoleTypeUsingNames(order, OrderRoleTypes.BILL_TO.name());
            
            if(shipToOrderRole != null) {
                orderRoleControl.createOrderRoleUsingNames(order, shipToOrderRole.getParty(), OrderRoleTypes.SHIP_TO.name(), createdBy);
            }
        }
        
        return shipToOrderRole == null ? null : shipToOrderRole.getParty();
    }
    
}
