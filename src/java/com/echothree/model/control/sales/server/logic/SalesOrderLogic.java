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

package com.echothree.model.control.sales.server.logic;

import com.echothree.model.control.accounting.common.exception.InvalidCurrencyException;
import com.echothree.model.control.accounting.server.logic.CurrencyLogic;
import com.echothree.model.control.associate.server.logic.AssociateReferralLogic;
import com.echothree.model.control.batch.server.logic.BatchLogic;
import com.echothree.model.control.cancellationpolicy.common.CancellationKinds;
import com.echothree.model.control.cancellationpolicy.server.logic.CancellationPolicyLogic;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.customer.common.exception.MissingDefaultCustomerTypeException;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.offer.common.exception.MissingDefaultSourceException;
import com.echothree.model.control.offer.server.control.OfferControl;
import com.echothree.model.control.offer.server.control.SourceControl;
import com.echothree.model.control.offer.server.logic.SourceLogic;
import com.echothree.model.control.order.common.OrderRoleTypes;
import com.echothree.model.control.order.common.OrderTypes;
import com.echothree.model.control.order.common.exception.MissingDefaultOrderPriorityException;
import com.echothree.model.control.order.common.exception.MissingRequiredBillToPartyException;
import com.echothree.model.control.order.server.control.OrderBatchControl;
import com.echothree.model.control.order.server.control.OrderControl;
import com.echothree.model.control.order.server.control.OrderPriorityControl;
import com.echothree.model.control.order.server.control.OrderRoleControl;
import com.echothree.model.control.order.server.logic.OrderLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.returnpolicy.common.ReturnKinds;
import com.echothree.model.control.returnpolicy.server.logic.ReturnPolicyLogic;
import com.echothree.model.control.sales.common.choice.SalesOrderStatusChoicesBean;
import com.echothree.model.control.sales.common.exception.InvalidSalesOrderBatchStatusException;
import com.echothree.model.control.sales.common.exception.InvalidSalesOrderReferenceException;
import com.echothree.model.control.sales.common.exception.InvalidSalesOrderStatusException;
import com.echothree.model.control.sales.common.exception.SalesOrderDuplicateReferenceException;
import com.echothree.model.control.sales.common.exception.SalesOrderReferenceRequiredException;
import com.echothree.model.control.sales.common.exception.UnknownSalesOrderStatusChoiceException;
import com.echothree.model.control.sales.common.workflow.SalesOrderStatusConstants;
import com.echothree.model.control.sales.server.control.SalesOrderControl;
import com.echothree.model.control.shipment.server.control.PartyFreeOnBoardControl;
import com.echothree.model.control.shipment.server.logic.FreeOnBoardLogic;
import com.echothree.model.control.term.server.control.TermControl;
import com.echothree.model.control.term.server.logic.TermLogic;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.control.workflow.server.logic.WorkflowDestinationLogic;
import com.echothree.model.control.workflow.server.logic.WorkflowLogic;
import com.echothree.model.control.workflow.server.logic.WorkflowStepLogic;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.batch.server.entity.Batch;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.customer.server.entity.Customer;
import com.echothree.model.data.customer.server.entity.CustomerType;
import com.echothree.model.data.offer.server.entity.Offer;
import com.echothree.model.data.offer.server.entity.Source;
import com.echothree.model.data.order.server.entity.Order;
import com.echothree.model.data.order.server.entity.OrderPriority;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.shipment.server.entity.FreeOnBoard;
import com.echothree.model.data.term.server.entity.Term;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class SalesOrderLogic
        extends OrderLogic {

    protected SalesOrderLogic() {
        super();
    }

    public static SalesOrderLogic getInstance() {
        return CDI.current().select(SalesOrderLogic.class).get();
    }
    
    final static long AllocatedInventoryTimeout = 5 * 60 * 1000; // 5 Minutes

    public CustomerType getCustomerType(final ExecutionErrorAccumulator eea, final Offer offer, final Customer customer) {
        var customerControl = Session.getModelController(CustomerControl.class);
        CustomerType customerType = null;

        // 1) Try to get it from the customer, if one was supplied.
        if(customer != null) {
            customerType = customer.getCustomerType();
        }

        // 2) Try to get it from the offer, if one was supplied.
        if(customerType == null && offer != null) {
            var offerControl = Session.getModelController(OfferControl.class);
            var offerCustomerType = offerControl.getDefaultOfferCustomerType(offer);

            if(offerCustomerType != null) {
                customerType = offerCustomerType.getCustomerType();
            }
        }

        // 3) Try to get the default CustomerType, error if it isn't available.
        if(customerType == null) {
            customerType = customerControl.getDefaultCustomerType();

            if(customerType == null) {
                handleExecutionError(MissingDefaultCustomerTypeException.class, eea, ExecutionErrors.MissingDefaultCustomerType.name());
            }
        }

        return customerType;
    }

    public void validateSalesOrderReference(final ExecutionErrorAccumulator eea, final String reference, final CustomerType customerType, final Customer billToCustomer) {
        Boolean requireReference = null;
        Boolean allowReferenceDuplicates = null;
        String referenceValidationPattern = null;

        if(billToCustomer != null) {
            requireReference = billToCustomer.getRequireReference();
            allowReferenceDuplicates = billToCustomer.getAllowReferenceDuplicates();
            referenceValidationPattern = billToCustomer.getReferenceValidationPattern();
        } else if(customerType != null) {
            var customerTypeDetail = customerType.getLastDetail();

            requireReference = customerTypeDetail.getDefaultRequireReference();
            allowReferenceDuplicates = customerTypeDetail.getDefaultAllowReferenceDuplicates();
            referenceValidationPattern = customerTypeDetail.getDefaultReferenceValidationPattern();
        }

        if(requireReference != null) {
            if(requireReference && reference == null) {
                handleExecutionError(SalesOrderReferenceRequiredException.class, eea, ExecutionErrors.SalesOrderReferenceRequired.name());
            } else if(reference != null) {
                var orderControl = Session.getModelController(OrderControl.class);

                if(!allowReferenceDuplicates) {
                    if(billToCustomer == null) {
                        handleExecutionError(MissingRequiredBillToPartyException.class, eea, ExecutionErrors.MissingRequiredBillToParty.name());
                    } else if(orderControl.countOrdersByBillToAndReference(billToCustomer.getParty(), reference) != 0) {
                        handleExecutionError(SalesOrderDuplicateReferenceException.class, eea, ExecutionErrors.SalesOrderDuplicateReference.name());
                    }
                }

                if(referenceValidationPattern != null && !reference.matches(referenceValidationPattern)) {
                    handleExecutionError(InvalidSalesOrderReferenceException.class, eea, ExecutionErrors.InvalidSalesOrderReference.name());
                }
            }
        }
    }

    public CancellationPolicy getCancellationPolicy(final ExecutionErrorAccumulator eea, final CustomerType customerType, final Customer billToCustomer) {
        return CancellationPolicyLogic.getInstance().getDefaultCancellationPolicyByKind(eea, CancellationKinds.CUSTOMER_CANCELLATION.name(),
                new CancellationPolicy[]{
                    billToCustomer == null ? null : billToCustomer.getCancellationPolicy(),
                    customerType.getLastDetail().getDefaultCancellationPolicy()
                });
    }

    public ReturnPolicy getReturnPolicy(final ExecutionErrorAccumulator eea, final CustomerType customerType, final Customer billToCustomer) {
        return ReturnPolicyLogic.getInstance().getDefaultReturnPolicyByKind(eea, ReturnKinds.CUSTOMER_RETURN.name(),
                new ReturnPolicy[]{
                    billToCustomer == null ? null : billToCustomer.getReturnPolicy(),
                    customerType.getLastDetail().getDefaultReturnPolicy()
                });
    }

    /**
     * Create a new Sales Order given a set of parameters. Default values will be determined as best as possible when
     * a parameter is marked as Optional. If a previous preference has not been set then it may not be possible to
     * to determine appropriate defaults.
     *
     * @param session Required.
     * @param eea Required.
     * @param userVisit Required.
     * @param source Optional.
     * @param billToParty Optional.
     * @param orderPriority Optional.
     * @param currency Optional.
     * @param holdUntilComplete Optional.
     * @param allowBackorders Optional.
     * @param allowSubstitutions Optional.
     * @param allowCombiningShipments Optional.
     * @param reference Optional.
     * @param term Optional.
     * @param taxable Optional.
     * @param workflowEntranceName Optional.
     * @param createdByParty Required.
     * @return The newly created Order, or null if there was an error.
     */
    public Order createSalesOrder(final Session session, final ExecutionErrorAccumulator eea, final UserVisit userVisit, final Batch batch, Source source,
            final Party billToParty, OrderPriority orderPriority, Currency currency, Boolean holdUntilComplete, Boolean allowBackorders, Boolean allowSubstitutions,
            Boolean allowCombiningShipments, final String reference, Term term, FreeOnBoard freeOnBoard, Boolean taxable, final String workflowEntranceName, final Party createdByParty) {
        var orderType = getOrderTypeByName(eea, OrderTypes.SALES_ORDER.name());
        var billToOrderRoleType = getOrderRoleTypeByName(eea, OrderRoleTypes.BILL_TO.name());
        var placingOrderRoleType = getOrderRoleTypeByName(eea, OrderRoleTypes.PLACING.name());
        Order order = null;

        if(batch != null) {
            if(SalesOrderBatchLogic.getInstance().checkBatchAvailableForEntry(eea, batch)) {
                var orderBatchControl = Session.getModelController(OrderBatchControl.class);
                var orderBatchCurrency = orderBatchControl.getOrderBatch(batch).getCurrency();

                if(currency == null) {
                    currency = orderBatchCurrency;
                } else {
                    if(!currency.equals(orderBatchCurrency)) {
                        handleExecutionError(InvalidCurrencyException.class, eea, ExecutionErrors.InvalidCurrency.name(), currency.getCurrencyIsoName(),
                                orderBatchCurrency.getCurrencyIsoName());
                    }
                }
            } else {
                handleExecutionError(InvalidSalesOrderBatchStatusException.class, eea, ExecutionErrors.InvalidSalesOrderBatchStatus.name(),
                        batch.getLastDetail().getBatchName());
            }
        }

        if(eea == null || !eea.hasExecutionErrors()) {
            if(source == null) {
                var sourceControl = Session.getModelController(SourceControl.class);

                source = sourceControl.getDefaultSource();

                if(source == null) {
                    handleExecutionError(MissingDefaultSourceException.class, eea, ExecutionErrors.MissingDefaultSource.name());
                }
            }

            if(orderPriority == null) {
                var orderPriorityControl = Session.getModelController(OrderPriorityControl.class);

                orderPriority = orderPriorityControl.getDefaultOrderPriority(orderType);

                if(orderPriority == null) {
                    handleExecutionError(MissingDefaultOrderPriorityException.class, eea, ExecutionErrors.MissingDefaultOrderPriority.name(), OrderTypes.SALES_ORDER.name());
                }
            }

            if(currency == null) {
                var userControl = Session.getModelController(UserControl.class);

                if(billToParty != null) {
                    currency = userControl.getPreferredCurrencyFromParty(billToParty);
                }

                if(currency == null && userVisit != null) {
                    currency = userControl.getPreferredCurrencyFromUserVisit(userVisit);
                }

                if(currency == null) {
                    currency = CurrencyLogic.getInstance().getDefaultCurrency(eea);
                }
            }

            if(billToParty != null) {
                PartyLogic.getInstance().checkPartyType(eea, billToParty, PartyTypes.CUSTOMER.name());
            }

            if(eea == null || !eea.hasExecutionErrors()) {
                var customerControl = Session.getModelController(CustomerControl.class);
                var billToCustomer = billToParty == null ? null : customerControl.getCustomer(billToParty);
                var offerUse = source.getLastDetail().getOfferUse();
                var customerType = getCustomerType(eea, offerUse.getLastDetail().getOffer(), billToCustomer);

                if(eea == null || !eea.hasExecutionErrors()) {
                    var cancellationPolicy = getCancellationPolicy(eea, customerType, billToCustomer);
                    var returnPolicy = getReturnPolicy(eea, customerType, billToCustomer);
                    
                    if(billToCustomer != null) {
                        validateSalesOrderReference(eea, reference, customerType, billToCustomer);
                    }

                    if(eea == null || !eea.hasExecutionErrors()) {
                        var customerTypeDetail = customerType.getLastDetail();
                        var sequence = offerUse.getLastDetail().getSalesOrderSequence();
                        var createdBy = createdByParty == null ? null : createdByParty.getPrimaryKey();

                        // If term or taxable were not set, then try to come up with sensible defaults, first from a PartyTerm if it
                        // was available, and then falling back on the CustomerType.
                        if(term == null || taxable == null) {
                            var termControl = Session.getModelController(TermControl.class);
                            var partyTerm = billToParty == null ? null : termControl.getPartyTerm(billToParty);

                            if(partyTerm == null) {
                                if(term == null) {
                                    term = customerTypeDetail.getDefaultTerm();
                                }

                                if(taxable == null) {
                                    taxable = customerTypeDetail.getDefaultTaxable();
                                }
                            } else {
                                if(term == null) {
                                    term = partyTerm.getTerm();
                                }

                                if(taxable == null) {
                                    taxable = partyTerm.getTaxable();
                                }
                            }

                            // If no better answer was found, use the default Term.
                            if(term == null) {
                                termControl.getDefaultTerm();
                            }

                            // If no better answer was found, the order is taxable.
                            if(taxable == null) {
                                taxable = true;
                            }
                        }

                        // If FreeOnBoard wasn't specified, using the bill to Party, fir look for a preference for the Party,
                        // and then check the CustomerType.
                        if(freeOnBoard == null) {
                            var partFreeOnBoardControl = Session.getModelController(PartyFreeOnBoardControl.class);
                            var partyFreeOnBoard = billToParty == null ? null : partFreeOnBoardControl.getPartyFreeOnBoard(billToParty);

                            if(partyFreeOnBoard == null) {
                                if(freeOnBoard == null) {
                                    freeOnBoard = customerTypeDetail.getDefaultFreeOnBoard();
                                }
                            } else {
                                if(freeOnBoard == null) {
                                    freeOnBoard = partyFreeOnBoard.getFreeOnBoard();
                                }
                            }

                            // If no better answer was found, use the default FreeOnBoard.
                            if(freeOnBoard == null) {
                                freeOnBoard = FreeOnBoardLogic.getInstance().getDefaultFreeOnBoard(eea);
                            }
                        }

                        // If any of these flags were not set, try to get them from either the Customer, or the CustomerType.
                        if(holdUntilComplete == null || allowBackorders == null || allowSubstitutions == null || allowCombiningShipments == null) {
                            if(billToCustomer == null) {
                                    if(holdUntilComplete == null) {
                                        holdUntilComplete = customerTypeDetail.getDefaultHoldUntilComplete();
                                    }

                                    if(allowBackorders == null) {
                                        allowBackorders = customerTypeDetail.getDefaultAllowBackorders();
                                    }

                                    if(allowSubstitutions == null) {
                                        allowSubstitutions = customerTypeDetail.getDefaultAllowSubstitutions();
                                    }

                                    if(allowCombiningShipments == null) {
                                        allowCombiningShipments = customerTypeDetail.getDefaultAllowCombiningShipments();
                                    }
                            } else {
                                if(holdUntilComplete == null) {
                                    holdUntilComplete = billToCustomer.getHoldUntilComplete();
                                }

                                if(allowBackorders == null) {
                                    allowBackorders = billToCustomer.getAllowBackorders();
                                }

                                if(allowSubstitutions == null) {
                                    allowSubstitutions = billToCustomer.getAllowSubstitutions();
                                }

                                if(allowCombiningShipments == null) {
                                    allowCombiningShipments = billToCustomer.getAllowCombiningShipments();
                                }
                            }
                        }

                        order = createOrder(eea, orderType, sequence, orderPriority, currency, holdUntilComplete, allowBackorders,
                                allowSubstitutions, allowCombiningShipments, term, freeOnBoard, reference, null,
                                cancellationPolicy, returnPolicy, taxable, createdBy);

                        if(eea == null || !eea.hasExecutionErrors()) {
                            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
                            var orderControl = Session.getModelController(OrderControl.class);
                            var orderRoleControl = Session.getModelController(OrderRoleControl.class);
                            var salesOrderControl = Session.getModelController(SalesOrderControl.class);
                            var workflowControl = Session.getModelController(WorkflowControl.class);
                            var associateReferral = AssociateReferralLogic.getInstance().getAssociateReferral(session, userVisit);
                            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(order.getPrimaryKey());

                            salesOrderControl.createSalesOrder(order, offerUse, associateReferral, createdBy);

                            orderControl.createOrderUserVisit(order, userVisit);
                            
                            workflowControl.addEntityToWorkflowUsingNames(null, SalesOrderStatusConstants.Workflow_SALES_ORDER_STATUS, workflowEntranceName,
                                    entityInstance, null, session.START_TIME + AllocatedInventoryTimeout, createdBy);
                            
                            if(billToParty != null) {
                                orderRoleControl.createOrderRole(order, billToParty, billToOrderRoleType, createdBy);
                            }

                            if(createdByParty != null) {
                                orderRoleControl.createOrderRole(order, createdByParty, placingOrderRoleType, createdBy);
                            }
                            
                            if(batch != null) {
                                // eea is passed in as null to createBatchEntity(...) so that an Exception will be thrown is something
                                // goes wrong. No real way to back out at this point if it does, except by Exception.
                                BatchLogic.getInstance().createBatchEntity(null, entityInstance, batch, createdBy);
                            }
                        }
                    }
                }
            }
        }

        return order;
    }

    public Order createSalesOrder(final Session session, final ExecutionErrorAccumulator eea, final UserVisit userVisit,
            final String batchName, final String sourceName, final String billToPartyName, final String orderPriorityName,
            final String currencyIsoName, final String termName, final String strHoldUntilComplete, final String strAllowBackorders,
            final String strAllowSubstitutions, final String strAllowCombiningShipments, final String reference, final String freeOnBoardName,
            final String strTaxable, final String workflowEntranceName, final Party createdByParty) {
        var batch = batchName == null ? null : SalesOrderBatchLogic.getInstance().getBatchByName(eea, batchName);
        var source = sourceName == null ? null : SourceLogic.getInstance().getSourceByName(eea, sourceName);
        var billToParty = billToPartyName == null ? null : PartyLogic.getInstance().getPartyByName(eea, billToPartyName, PartyTypes.CUSTOMER.name());
        var orderPriority = orderPriorityName == null ? null : SalesOrderLogic.getInstance().getOrderPriorityByName(eea, orderPriorityName);
        var currency = currencyIsoName == null ? null : CurrencyLogic.getInstance().getCurrencyByName(eea, currencyIsoName);
        var term = termName == null ? null : TermLogic.getInstance().getTermByName(eea, termName);
        var freeOnBoard = freeOnBoardName == null ? null : FreeOnBoardLogic.getInstance().getFreeOnBoardByName(eea, freeOnBoardName);
        Order order = null;

        if(!eea.hasExecutionErrors()) {
            var holdUntilComplete = strHoldUntilComplete == null ? null : Boolean.valueOf(strHoldUntilComplete);
            var allowBackorders = strAllowBackorders == null ? null : Boolean.valueOf(strAllowBackorders);
            var allowSubstitutions = strAllowSubstitutions == null ? null : Boolean.valueOf(strAllowSubstitutions);
            var allowCombiningShipments = strAllowCombiningShipments == null ? null : Boolean.valueOf(strAllowCombiningShipments);
            var taxable = strTaxable == null ? null : Boolean.valueOf(strTaxable);

            order = createSalesOrder(session, eea, userVisit, batch, source, billToParty, orderPriority, currency,
                    holdUntilComplete, allowBackorders, allowSubstitutions, allowCombiningShipments, reference, term,
                    freeOnBoard, taxable, workflowEntranceName, createdByParty);

        }

        return order;
    }

    public boolean isOrderInWorkflowSteps(final ExecutionErrorAccumulator eea, final Order order, final String... workflowStepNames) {
        return isOrderInWorkflowSteps(eea, getEntityInstanceByBaseEntity(order), workflowStepNames);
    }

    public boolean isOrderInWorkflowSteps(final ExecutionErrorAccumulator eea, final EntityInstance entityInstance, final String... workflowStepNames) {
        return !WorkflowStepLogic.getInstance().isEntityInWorkflowSteps(eea, SalesOrderStatusConstants.Workflow_SALES_ORDER_STATUS, entityInstance,
                workflowStepNames).isEmpty();
    }

    public Order getOrderByName(final ExecutionErrorAccumulator eea, final String orderName) {
        return getOrderByName(eea, OrderTypes.SALES_ORDER.name(), orderName);
    }

    public Order getOrderByNameForUpdate(final ExecutionErrorAccumulator eea, final String orderName) {
        return getOrderByNameForUpdate(eea, OrderTypes.SALES_ORDER.name(), orderName);
    }

    public OrderPriority getOrderPriorityByName(final ExecutionErrorAccumulator eea, final String orderPriorityName) {
        return SalesOrderPriorityLogic.getInstance().getOrderPriorityByName(eea, OrderTypes.SALES_ORDER.name(), orderPriorityName);
    }

    public OrderPriority getOrderPriorityByNameForUpdate(final ExecutionErrorAccumulator eea, final String orderPriorityName) {
        return SalesOrderPriorityLogic.getInstance().getOrderPriorityByNameForUpdate(eea, OrderTypes.SALES_ORDER.name(), orderPriorityName);
    }

    public SalesOrderStatusChoicesBean getSalesOrderStatusChoices(final String defaultOrderStatusChoice, final Language language, final boolean allowNullChoice,
            final Order order, final PartyPK partyPK) {
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var salesOrderStatusChoicesBean = new SalesOrderStatusChoicesBean();

        if(order == null) {
            workflowControl.getWorkflowEntranceChoices(salesOrderStatusChoicesBean, defaultOrderStatusChoice, language, allowNullChoice,
                    workflowControl.getWorkflowByName(SalesOrderStatusConstants.Workflow_SALES_ORDER_STATUS), partyPK);
        } else {
            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(order.getPrimaryKey());
            var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(SalesOrderStatusConstants.Workflow_SALES_ORDER_STATUS, entityInstance);

            workflowControl.getWorkflowDestinationChoices(salesOrderStatusChoicesBean, defaultOrderStatusChoice, language, allowNullChoice, workflowEntityStatus.getWorkflowStep(), partyPK);
        }

        return salesOrderStatusChoicesBean;
    }

    public void setSalesOrderStatus(final Session session, final ExecutionErrorAccumulator eea, final Order order, final String orderStatusChoice, final PartyPK modifiedBy) {
        var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var workflow = WorkflowLogic.getInstance().getWorkflowByName(eea, SalesOrderStatusConstants.Workflow_SALES_ORDER_STATUS);
        var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(order.getPrimaryKey());
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdate(workflow, entityInstance);
        var workflowDestination = orderStatusChoice == null? null: workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), orderStatusChoice);

        if(workflowDestination != null || orderStatusChoice == null) {
            var workflowDestinationLogic = WorkflowDestinationLogic.getInstance();
            var currentWorkflowStepName = workflowEntityStatus.getWorkflowStep().getLastDetail().getWorkflowStepName();
            var map = workflowDestinationLogic.getWorkflowDestinationsAsMap(workflowDestination);
            var handled = false;
            Long triggerTime = null;
            
            if(currentWorkflowStepName.equals(SalesOrderStatusConstants.WorkflowStep_ENTRY_ALLOCATED)) {
                if(workflowDestinationLogic.workflowDestinationMapContainsStep(map, SalesOrderStatusConstants.Workflow_SALES_ORDER_STATUS, SalesOrderStatusConstants.WorkflowStep_ENTRY_UNALLOCATED)) {
                    // TODO: Unallocate inventory.
                    handled = true;
                } else if(workflowDestinationLogic.workflowDestinationMapContainsStep(map, SalesOrderStatusConstants.Workflow_SALES_ORDER_STATUS, SalesOrderStatusConstants.WorkflowStep_ENTRY_COMPLETE)) {
                    // TODO: Verify the order is not part of a batch.
                    // TODO: Verify all aspects of the order are valid.
                    handled = true;
                } else if(workflowDestinationLogic.workflowDestinationMapContainsStep(map, SalesOrderStatusConstants.Workflow_SALES_ORDER_STATUS, SalesOrderStatusConstants.WorkflowStep_BATCH_AUDIT)) {
                    // TODO: Verify the order is part of a batch.
                    // TODO: Verify all aspects of the order are valid.
                    handled = true;
                }
            } else if(currentWorkflowStepName.equals(SalesOrderStatusConstants.WorkflowStep_ENTRY_UNALLOCATED)) {
                if(workflowDestinationLogic.workflowDestinationMapContainsStep(map, SalesOrderStatusConstants.Workflow_SALES_ORDER_STATUS, SalesOrderStatusConstants.WorkflowStep_ENTRY_ALLOCATED)) {
                    // TODO: Allocate inventory.
                    
                    triggerTime = session.START_TIME + AllocatedInventoryTimeout;
                    handled = true;
                }
            } else if(currentWorkflowStepName.equals(SalesOrderStatusConstants.WorkflowStep_BATCH_AUDIT)) {
                if(workflowDestinationLogic.workflowDestinationMapContainsStep(map, SalesOrderStatusConstants.Workflow_SALES_ORDER_STATUS, SalesOrderStatusConstants.WorkflowStep_ENTRY_COMPLETE)) {
                    handled = true;
                }
            }
            
            if(eea == null || !eea.hasExecutionErrors()) {
                if(handled) {
                    workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, triggerTime, modifiedBy);
                } else {
                    // TODO: An error of some sort.
                }
            }
        } else {
            handleExecutionError(UnknownSalesOrderStatusChoiceException.class, eea, ExecutionErrors.UnknownSalesOrderStatusChoice.name(), orderStatusChoice);
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
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(SalesOrderStatusConstants.Workflow_SALES_ORDER_STATUS, getEntityInstanceByBaseEntity(order));
        var workflowStepName = workflowEntityStatus.getWorkflowStep().getLastDetail().getWorkflowStepName();
        
        if(workflowStepName.equals(SalesOrderStatusConstants.WorkflowEntrance_ENTRY_ALLOCATED)) {
            var workflowTrigger = workflowControl.getWorkflowTriggerForUpdate(workflowEntityStatus);
            
            workflowTrigger.setTriggerTime(session.START_TIME + AllocatedInventoryTimeout);
        } else if(workflowStepName.equals(SalesOrderStatusConstants.WorkflowEntrance_ENTRY_UNALLOCATED)) {
            setSalesOrderStatus(session, eea, order, SalesOrderStatusConstants.WorkflowDestination_ENTRY_UNALLOCATED_TO_ALLOCATED, modifiedBy);
        } else {
            handleExecutionError(InvalidSalesOrderStatusException.class, eea, ExecutionErrors.InvalidSalesOrderStatus.name(), order.getLastDetail().getOrderName(), workflowStepName);
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
    
    /** Find the BILL_TO Party for a given Order.
     * 
     * @param party Optional.
     * @return The CustomerType for the Party. May be null.
     */
    public CustomerType getCustomerTypeFromParty(final Party party) {
        var customerControl = Session.getModelController(CustomerControl.class);
        var customer = party == null ? null : customerControl.getCustomer(party);
        CustomerType customerType = null;
        
        if(customer != null) {
            customerType = customer.getCustomerType();
        }
        
        return customerType;
    }
    
    /** Find the BILL_TO Party for a given Order.
     * 
     * @param order Required.
     * @return The CustomerType for the BILL_TO Party. May be null.
     */
    public CustomerType getOrderBillToCustomerType(final Order order) {
        return getCustomerTypeFromParty(getOrderBillToParty(order));
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
