// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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
import com.echothree.model.control.batch.server.logic.BatchLogic;
import com.echothree.model.control.cancellationpolicy.common.CancellationPolicyConstants;
import com.echothree.model.control.cancellationpolicy.server.logic.CancellationPolicyLogic;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.customer.common.exception.MissingDefaultCustomerTypeException;
import com.echothree.model.control.customer.common.exception.UnknownCustomerTypePaymentMethodException;
import com.echothree.model.control.customer.common.exception.UnknownCustomerTypeShippingMethodException;
import com.echothree.model.control.customer.server.CustomerControl;
import com.echothree.model.control.inventory.common.exception.UnknownDefaultInventoryConditionException;
import com.echothree.model.control.inventory.server.InventoryControl;
import com.echothree.model.control.item.common.ItemPriceTypes;
import com.echothree.model.control.item.common.exception.UnknownDefaultItemUnitOfMeasureTypeException;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.control.offer.common.exception.MissingDefaultSourceException;
import com.echothree.model.control.offer.common.exception.UnknownOfferItemPriceException;
import com.echothree.model.control.offer.server.OfferControl;
import com.echothree.model.control.offer.server.logic.OfferItemLogic;
import com.echothree.model.control.order.common.OrderConstants;
import com.echothree.model.control.order.common.exception.MissingDefaultOrderPriorityException;
import com.echothree.model.control.order.common.exception.MissingRequiredBillToPartyException;
import com.echothree.model.control.order.server.OrderControl;
import com.echothree.model.control.order.server.logic.OrderLogic;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.returnpolicy.common.ReturnPolicyConstants;
import com.echothree.model.control.returnpolicy.server.logic.ReturnPolicyLogic;
import com.echothree.model.control.sales.common.exception.BillToPartyMustMatchPartyPaymentMethodsPartyException;
import com.echothree.model.control.sales.common.exception.BillToRequiredWhenUsingPartyPaymentMethodException;
import com.echothree.model.control.sales.common.exception.CurrentTimeAfterSalesOrderEndTimeException;
import com.echothree.model.control.sales.common.exception.CurrentTimeBeforeSalesOrderStartTimeException;
import com.echothree.model.control.sales.common.exception.InvalidSalesOrderBatchStatusException;
import com.echothree.model.control.sales.common.exception.InvalidSalesOrderReferenceException;
import com.echothree.model.control.sales.common.exception.InvalidSalesOrderStatusException;
import com.echothree.model.control.sales.common.exception.ItemDiscontinuedException;
import com.echothree.model.control.sales.common.exception.QuantityAboveMaximumItemUnitCustomerTypeLimitException;
import com.echothree.model.control.sales.common.exception.QuantityAboveMaximumItemUnitLimitException;
import com.echothree.model.control.sales.common.exception.QuantityBelowMinimumItemUnitCustomerTypeLimitException;
import com.echothree.model.control.sales.common.exception.QuantityBelowMinimumItemUnitLimitException;
import com.echothree.model.control.sales.common.exception.SalesOrderDuplicateReferenceException;
import com.echothree.model.control.sales.common.exception.SalesOrderReferenceRequiredException;
import com.echothree.model.control.sales.common.exception.UnitAmountAboveMaximumItemUnitPriceLimitException;
import com.echothree.model.control.sales.common.exception.UnitAmountAboveMaximumUnitPriceException;
import com.echothree.model.control.sales.common.exception.UnitAmountBelowMinimumItemUnitPriceLimitException;
import com.echothree.model.control.sales.common.exception.UnitAmountBelowMinimumUnitPriceException;
import com.echothree.model.control.sales.common.exception.UnitAmountNotMultipleOfUnitPriceIncrementException;
import com.echothree.model.control.sales.common.exception.UnitAmountRequiredException;
import com.echothree.model.control.sales.common.exception.UnknownSalesOrderStatusChoiceException;
import com.echothree.model.control.sales.common.choice.SalesOrderStatusChoicesBean;
import com.echothree.model.control.sales.server.SalesControl;
import com.echothree.model.control.term.server.logic.TermLogic;
import com.echothree.model.control.user.server.UserControl;
import com.echothree.model.control.item.common.workflow.ItemStatusConstants;
import com.echothree.model.control.sales.common.workflow.SalesOrderStatusConstants;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.control.workflow.server.logic.WorkflowLogic;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.associate.server.entity.AssociateReferral;
import com.echothree.model.data.batch.server.entity.Batch;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.customer.server.entity.Customer;
import com.echothree.model.data.customer.server.entity.CustomerType;
import com.echothree.model.data.customer.server.entity.CustomerTypeDetail;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemDeliveryType;
import com.echothree.model.data.item.server.entity.ItemDetail;
import com.echothree.model.data.item.server.entity.ItemUnitCustomerTypeLimit;
import com.echothree.model.data.item.server.entity.ItemUnitLimit;
import com.echothree.model.data.item.server.entity.ItemUnitOfMeasureType;
import com.echothree.model.data.item.server.entity.ItemUnitPriceLimit;
import com.echothree.model.data.offer.server.entity.Offer;
import com.echothree.model.data.offer.server.entity.OfferCustomerType;
import com.echothree.model.data.offer.server.entity.OfferItem;
import com.echothree.model.data.offer.server.entity.OfferItemFixedPrice;
import com.echothree.model.data.offer.server.entity.OfferItemPrice;
import com.echothree.model.data.offer.server.entity.OfferItemVariablePrice;
import com.echothree.model.data.offer.server.entity.OfferUse;
import com.echothree.model.data.offer.server.entity.Source;
import com.echothree.model.data.order.server.entity.Order;
import com.echothree.model.data.order.server.entity.OrderDetail;
import com.echothree.model.data.order.server.entity.OrderLine;
import com.echothree.model.data.order.server.entity.OrderPaymentPreference;
import com.echothree.model.data.order.server.entity.OrderPriority;
import com.echothree.model.data.order.server.entity.OrderRole;
import com.echothree.model.data.order.server.entity.OrderRoleType;
import com.echothree.model.data.order.server.entity.OrderShipmentGroup;
import com.echothree.model.data.order.server.entity.OrderShipmentGroupDetail;
import com.echothree.model.data.order.server.entity.OrderType;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.payment.server.entity.PartyPaymentMethod;
import com.echothree.model.data.payment.server.entity.PaymentMethod;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.sales.server.entity.SalesOrder;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.shipping.server.entity.ShippingMethod;
import com.echothree.model.data.term.server.entity.PartyTerm;
import com.echothree.model.data.term.server.entity.Term;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowDestination;
import com.echothree.model.data.workflow.server.entity.WorkflowEntityStatus;
import com.echothree.model.data.workflow.server.entity.WorkflowTrigger;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import java.util.Map;
import java.util.Set;

public class SalesOrderLogic
        extends OrderLogic {

    private SalesOrderLogic() {
        super();
    }

    private static class LogicHolder {
        static SalesOrderLogic instance = new SalesOrderLogic();
    }

    public static SalesOrderLogic getInstance() {
        return LogicHolder.instance;
    }
    
    final static long AllocatedInventoryTimeout = 5 * 60 * 1000; // 5 Minutes

    public CustomerType getCustomerType(final ExecutionErrorAccumulator eea, final Offer offer, final Customer customer) {
        var customerControl = (CustomerControl)Session.getModelController(CustomerControl.class);
        CustomerType customerType = null;

        // 1) Try to get it from the customer, if one was supplied.
        if(customer != null) {
            customerType = customer.getCustomerType();
        }

        // 2) Try to get it from the offer, if one was supplied.
        if(customerType == null && offer != null) {
            var offerControl = (OfferControl)Session.getModelController(OfferControl.class);
            OfferCustomerType offerCustomerType = offerControl.getDefaultOfferCustomerType(offer);

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
            CustomerTypeDetail customerTypeDetail = customerType.getLastDetail();

            requireReference = customerTypeDetail.getDefaultRequireReference();
            allowReferenceDuplicates = customerTypeDetail.getDefaultAllowReferenceDuplicates();
            referenceValidationPattern = customerTypeDetail.getDefaultReferenceValidationPattern();
        }

        if(requireReference != null) {
            if(requireReference && reference == null) {
                handleExecutionError(SalesOrderReferenceRequiredException.class, eea, ExecutionErrors.SalesOrderReferenceRequired.name());
            } else if(reference != null) {
                var orderControl = (OrderControl)Session.getModelController(OrderControl.class);

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
        return CancellationPolicyLogic.getInstance().getDefaultCancellationPolicyByKind(eea, CancellationPolicyConstants.CancellationKind_CUSTOMER_CANCELLATION,
                new CancellationPolicy[]{
                    billToCustomer == null ? null : billToCustomer.getCancellationPolicy(),
                    customerType.getLastDetail().getDefaultCancellationPolicy()
                });
    }

    public ReturnPolicy getReturnPolicy(final ExecutionErrorAccumulator eea, final CustomerType customerType, final Customer billToCustomer) {
        return ReturnPolicyLogic.getInstance().getDefaultReturnPolicyByKind(eea, ReturnPolicyConstants.ReturnKind_CUSTOMER_RETURN,
                new ReturnPolicy[]{
                    billToCustomer == null ? null : billToCustomer.getReturnPolicy(),
                    customerType.getLastDetail().getDefaultReturnPolicy()
                });
    }

    /**
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
            Boolean allowCombiningShipments, final String reference, Term term, Boolean taxable, final String workflowEntranceName, final Party createdByParty) {
        var orderControl = (OrderControl)Session.getModelController(OrderControl.class);
        Order order = null;
        OrderType orderType = getOrderTypeByName(eea, OrderConstants.OrderType_SALES_ORDER);
        OrderRoleType billToOrderRoleType = getOrderRoleTypeByName(eea, OrderConstants.OrderRoleType_BILL_TO);
        OrderRoleType placingOrderRoleType = getOrderRoleTypeByName(eea, OrderConstants.OrderRoleType_PLACING);

        if(batch != null) {
            if(SalesOrderBatchLogic.getInstance().checkBatchAvailableForEntry(eea, batch)) {
                Currency orderBatchCurrency = orderControl.getOrderBatch(batch).getCurrency();

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
                var offerControl = (OfferControl)Session.getModelController(OfferControl.class);

                source = offerControl.getDefaultSource();

                if(source == null) {
                    handleExecutionError(MissingDefaultSourceException.class, eea, ExecutionErrors.MissingDefaultSource.name());
                }
            }

            if(orderPriority == null) {
                orderPriority = orderControl.getDefaultOrderPriority(orderType);

                if(orderPriority == null) {
                    handleExecutionError(MissingDefaultOrderPriorityException.class, eea, ExecutionErrors.MissingDefaultOrderPriority.name(), OrderConstants.OrderType_SALES_ORDER);
                }
            }

            if(currency == null) {
                var userControl = (UserControl)Session.getModelController(UserControl.class);

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
                PartyLogic.getInstance().checkPartyType(eea, billToParty, PartyConstants.PartyType_CUSTOMER);
            }

            if(eea == null || !eea.hasExecutionErrors()) {
                var customerControl = (CustomerControl)Session.getModelController(CustomerControl.class);
                Customer billToCustomer = billToParty == null ? null : customerControl.getCustomer(billToParty);
                OfferUse offerUse = source.getLastDetail().getOfferUse();
                CustomerType customerType = getCustomerType(eea, offerUse.getLastDetail().getOffer(), billToCustomer);

                if(eea == null || !eea.hasExecutionErrors()) {
                    PartyTerm partyTerm = billToParty == null ? null : TermLogic.getInstance().getPartyTerm(eea, billToParty);
                    CancellationPolicy cancellationPolicy = getCancellationPolicy(eea, customerType, billToCustomer);
                    ReturnPolicy returnPolicy = getReturnPolicy(eea, customerType, billToCustomer);
                    
                    if(billToCustomer != null) {
                        validateSalesOrderReference(eea, reference, customerType, billToCustomer);
                    }

                    if(eea == null || !eea.hasExecutionErrors()) {
                        CustomerTypeDetail customerTypeDetail = customerType.getLastDetail();
                        Sequence sequence = offerUse.getLastDetail().getSalesOrderSequence();
                        PartyPK createdBy = createdByParty == null ? null : createdByParty.getPrimaryKey();

                        // If term or taxable were not set, then try to come up with sensible defaults, first from a PartyTerm if it
                        // was available, and then falling back on the CustomerType.
                        if(term == null || taxable == null) {
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
                                allowSubstitutions, allowCombiningShipments, term, reference, null, cancellationPolicy, returnPolicy, taxable, createdBy);

                        if(eea == null || !eea.hasExecutionErrors()) {
                            var coreControl = (CoreControl)Session.getModelController(CoreControl.class);
                            var salesControl = (SalesControl)Session.getModelController(SalesControl.class);
                            var workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
                            AssociateReferral associateReferral = userVisit == null ? null : userVisit.getAssociateReferral();
                            EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(order.getPrimaryKey());

                            salesControl.createSalesOrder(order, offerUse, associateReferral, createdBy);

                            orderControl.createOrderUserVisit(order, userVisit);
                            
                            workflowControl.addEntityToWorkflowUsingNames(null, SalesOrderStatusConstants.Workflow_SALES_ORDER_STATUS, workflowEntranceName,
                                    entityInstance, null, session.START_TIME + AllocatedInventoryTimeout, createdBy);
                            
                            if(billToParty != null) {
                                orderControl.createOrderRole(order, billToParty, billToOrderRoleType, createdBy);
                            }

                            if(createdByParty != null) {
                                orderControl.createOrderRole(order, createdByParty, placingOrderRoleType, createdBy);
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

    public boolean isOrderInWorkflowSteps(final ExecutionErrorAccumulator eea, final Order order, final String... workflowStepNames) {
        return isOrderInWorkflowSteps(eea, getEntityInstanceByBaseEntity(order), workflowStepNames);
    }

    public boolean isOrderInWorkflowSteps(final ExecutionErrorAccumulator eea, final EntityInstance entityInstance, final String... workflowStepNames) {
        return !WorkflowLogic.getInstance().isEntityInWorkflowSteps(eea, SalesOrderStatusConstants.Workflow_SALES_ORDER_STATUS, entityInstance,
                workflowStepNames).isEmpty();
    }

    public Order getOrderByName(final ExecutionErrorAccumulator eea, final String orderName) {
        return getOrderByName(eea, OrderConstants.OrderType_SALES_ORDER, orderName);
    }

    public Order getOrderByNameForUpdate(final ExecutionErrorAccumulator eea, final String orderName) {
        return getOrderByNameForUpdate(eea, OrderConstants.OrderType_SALES_ORDER, orderName);
    }

    public OrderPriority getOrderPriorityByName(final ExecutionErrorAccumulator eea, final String orderPriorityName) {
        return getOrderPriorityByName(eea, OrderConstants.OrderType_SALES_ORDER, orderPriorityName);
    }

    public OrderPriority getOrderPriorityByNameForUpdate(final ExecutionErrorAccumulator eea, final String orderPriorityName) {
        return getOrderPriorityByNameForUpdate(eea, OrderConstants.OrderType_SALES_ORDER, orderPriorityName);
    }

    public SalesOrderStatusChoicesBean getSalesOrderStatusChoices(final String defaultOrderStatusChoice, final Language language, final boolean allowNullChoice,
            final Order order, final PartyPK partyPK) {
        var workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
        SalesOrderStatusChoicesBean salesOrderStatusChoicesBean = new SalesOrderStatusChoicesBean();

        if(order == null) {
            workflowControl.getWorkflowEntranceChoices(salesOrderStatusChoicesBean, defaultOrderStatusChoice, language, allowNullChoice,
                    workflowControl.getWorkflowByName(SalesOrderStatusConstants.Workflow_SALES_ORDER_STATUS), partyPK);
        } else {
            var coreControl = (CoreControl)Session.getModelController(CoreControl.class);
            EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(order.getPrimaryKey());
            WorkflowEntityStatus workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(SalesOrderStatusConstants.Workflow_SALES_ORDER_STATUS, entityInstance);

            workflowControl.getWorkflowDestinationChoices(salesOrderStatusChoicesBean, defaultOrderStatusChoice, language, allowNullChoice, workflowEntityStatus.getWorkflowStep(), partyPK);
        }

        return salesOrderStatusChoicesBean;
    }

    public void setSalesOrderStatus(final Session session, final ExecutionErrorAccumulator eea, final Order order, final String orderStatusChoice, final PartyPK modifiedBy) {
        var coreControl = (CoreControl)Session.getModelController(CoreControl.class);
        var workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
        WorkflowLogic workflowLogic = WorkflowLogic.getInstance();
        Workflow workflow = workflowLogic.getWorkflowByName(eea, SalesOrderStatusConstants.Workflow_SALES_ORDER_STATUS);
        EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(order.getPrimaryKey());
        WorkflowEntityStatus workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdate(workflow, entityInstance);
        WorkflowDestination workflowDestination = orderStatusChoice == null? null: workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), orderStatusChoice);

        if(workflowDestination != null || orderStatusChoice == null) {
            String currentWorkflowStepName = workflowEntityStatus.getWorkflowStep().getLastDetail().getWorkflowStepName();
            Map<String, Set<String>> map = workflowLogic.getWorkflowDestinationsAsMap(workflowDestination);
            boolean handled = false;
            Long triggerTime = null;
            
            if(currentWorkflowStepName.equals(SalesOrderStatusConstants.WorkflowStep_ENTRY_ALLOCATED)) {
                if(workflowLogic.workflowDestinationMapContainsStep(map, SalesOrderStatusConstants.Workflow_SALES_ORDER_STATUS, SalesOrderStatusConstants.WorkflowStep_ENTRY_UNALLOCATED)) {
                    // TODO: Unallocate inventory.
                    handled = true;
                } else if(workflowLogic.workflowDestinationMapContainsStep(map, SalesOrderStatusConstants.Workflow_SALES_ORDER_STATUS, SalesOrderStatusConstants.WorkflowStep_ENTRY_COMPLETE)) {
                    // TODO: Verify the order is not part of a batch.
                    // TODO: Verify all aspects of the order are valid.
                    handled = true;
                } else if(workflowLogic.workflowDestinationMapContainsStep(map, SalesOrderStatusConstants.Workflow_SALES_ORDER_STATUS, SalesOrderStatusConstants.WorkflowStep_BATCH_AUDIT)) {
                    // TODO: Verify the order is part of a batch.
                    // TODO: Verify all aspects of the order are valid.
                    handled = true;
                }
            } else if(currentWorkflowStepName.equals(SalesOrderStatusConstants.WorkflowStep_ENTRY_UNALLOCATED)) {
                if(workflowLogic.workflowDestinationMapContainsStep(map, SalesOrderStatusConstants.Workflow_SALES_ORDER_STATUS, SalesOrderStatusConstants.WorkflowStep_ENTRY_ALLOCATED)) {
                    // TODO: Allocate inventory.
                    
                    triggerTime = session.START_TIME + AllocatedInventoryTimeout;
                    handled = true;
                }
            } else if(currentWorkflowStepName.equals(SalesOrderStatusConstants.WorkflowStep_BATCH_AUDIT)) {
                if(workflowLogic.workflowDestinationMapContainsStep(map, SalesOrderStatusConstants.Workflow_SALES_ORDER_STATUS, SalesOrderStatusConstants.WorkflowStep_ENTRY_COMPLETE)) {
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
        var workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
        WorkflowEntityStatus workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(SalesOrderStatusConstants.Workflow_SALES_ORDER_STATUS, getEntityInstanceByBaseEntity(order));
        String workflowStepName = workflowEntityStatus.getWorkflowStep().getLastDetail().getWorkflowStepName();
        
        if(workflowStepName.equals(SalesOrderStatusConstants.WorkflowEntrance_ENTRY_ALLOCATED)) {
            WorkflowTrigger workflowTrigger = workflowControl.getWorkflowTriggerForUpdate(workflowEntityStatus);
            
            workflowTrigger.setTriggerTime(session.START_TIME + AllocatedInventoryTimeout);
        } else if(workflowStepName.equals(SalesOrderStatusConstants.WorkflowEntrance_ENTRY_UNALLOCATED)) {
            setSalesOrderStatus(session, eea, order, SalesOrderStatusConstants.WorkflowDestination_ENTRY_UNALLOCATED_TO_ALLOCATED, modifiedBy);
        } else {
            handleExecutionError(InvalidSalesOrderStatusException.class, eea, ExecutionErrors.InvalidSalesOrderStatus.name(), order.getLastDetail().getOrderName(), workflowStepName);
        }
    }
    
    /** Verify that the CustomerType is authorized to use the PaymentMethod. If there are no CustomerTypePaymentMethods for any PaymentMethod,
     * then it is assumed they're authorized.
     * 
     * @param eea Required.
     * @param customerType Required.
     * @param paymentMethod Required.
     */
    public void checkCustomerTypePaymentMethod(final ExecutionErrorAccumulator eea, CustomerType customerType, PaymentMethod paymentMethod) {
        var customerControl = (CustomerControl)Session.getModelController(CustomerControl.class);
        
        if(!customerControl.getCustomerTypePaymentMethodExists(customerType, paymentMethod)
                && customerControl.countCustomerTypePaymentMethodsByCustomerType(customerType) != 0) {
            handleExecutionError(UnknownCustomerTypePaymentMethodException.class, eea, ExecutionErrors.UnknownCustomerTypePaymentMethod.name(),
                    customerType.getLastDetail().getCustomerTypeName(), paymentMethod.getLastDetail().getPaymentMethodName());
        }
    }
    
    /**
     * 
     * @param eea Required.
     * @param order Required.
     * @param orderPaymentPreferenceSequence Optional.
     * @param paymentMethod Required for all types except CREDIT_CARDs, GIFT_CARDs, GIFT_CERTIFICATEs.
     * @param partyPaymentMethod Required for CREDIT_CARDs, GIFT_CARDs, GIFT_CERTIFICATEs, otherwise null.
     * @param wasPresent Required for CREDIT_CARD, otherwise null.
     * @param maximumAmount Optional.
     * @param sortOrder Required.
     * @param createdBy Required.
     * @return The newly created OrderPaymentPreference, or null if there was an error.
     */
    public OrderPaymentPreference createSalesOrderPaymentPreference(final Session session, final ExecutionErrorAccumulator eea, final Order order,
            final Integer orderPaymentPreferenceSequence, final PaymentMethod paymentMethod, final PartyPaymentMethod partyPaymentMethod,
            final Boolean wasPresent, final Long maximumAmount, final Integer sortOrder, final PartyPK createdBy) {
        OrderPaymentPreference orderPaymentPreference = null;
        
        checkOrderAvailableForModification(session, eea, order, createdBy);
        
        if(eea == null || !eea.hasExecutionErrors()) {
            Party billTo = getOrderBillToParty(order);
            CustomerType customerType = billTo == null ? null : getCustomerTypeFromParty(billTo);

            if(customerType != null) {
                checkCustomerTypePaymentMethod(eea, customerType, paymentMethod);
            }

            if(eea == null || !eea.hasExecutionErrors()) {
                if(partyPaymentMethod != null) {
                    if(billTo == null) {
                        // Order must have a bill to before a payment method that requires a partyPaymentMethod may be set.
                        handleExecutionError(BillToRequiredWhenUsingPartyPaymentMethodException.class, eea, ExecutionErrors.BillToRequiredWhenUsingPartyPaymentMethod.name());
                    } else if(!billTo.equals(partyPaymentMethod.getLastDetail().getParty())) {
                        // Verify partyPaymentMethod belongs to the BILL_TO Party on the order.
                        handleExecutionError(BillToPartyMustMatchPartyPaymentMethodsPartyException.class, eea, ExecutionErrors.BillToPartyMustMatchPartyPaymentMethodsParty.name());
                    }
                }

                if(eea == null || !eea.hasExecutionErrors()) {
                    orderPaymentPreference = OrderLogic.getInstance().createOrderPaymentPreference(session, eea, order, orderPaymentPreferenceSequence, paymentMethod,
                            partyPaymentMethod, wasPresent, maximumAmount, sortOrder, createdBy);
                }
            }
        }
        
        return orderPaymentPreference;
    }
    
    /**
     * 
     * @param session Required.
     * @param eea Required.
     * @param order Required.
     * @param orderShipmentGroupSequence Optional.
     * @param itemDeliveryType Required.
     * @param isDefault Required.
     * @param partyContactMechanism Optional.
     * @param shippingMethod Optional.
     * @param holdUntilComplete Required.
     * @param createdBy Required.
     * @return The newly created OrderShipmentGroup, or null if there was an error.
     */
    public OrderShipmentGroup createSalesOrderShipmentGroup(final Session session, final ExecutionErrorAccumulator eea, final Order order,
            Integer orderShipmentGroupSequence, final ItemDeliveryType itemDeliveryType, final Boolean isDefault, PartyContactMechanism partyContactMechanism,
            ShippingMethod shippingMethod, final Boolean holdUntilComplete, final PartyPK createdBy) {
        OrderShipmentGroup orderShipmentGroup = null;
        
        checkOrderAvailableForModification(session, eea, order, createdBy);
        
        if(eea == null || !eea.hasExecutionErrors()) {
            orderShipmentGroup = createOrderShipmentGroup(eea, order, orderShipmentGroupSequence, itemDeliveryType, isDefault, partyContactMechanism,
                    shippingMethod, holdUntilComplete, createdBy);
        }
        
        return orderShipmentGroup;
    }

    /** Find the BILL_TO Party for a given Order.
     * 
     * @param order Required.
     * @return The Party used for the BILL_TO OrderRoleType. May be null.
     */
    public Party getOrderBillToParty(final Order order) {
        var orderControl = (OrderControl)Session.getModelController(OrderControl.class);
        OrderRole billToOrderRole = orderControl.getOrderRoleByOrderAndOrderRoleTypeUsingNames(order, OrderConstants.OrderRoleType_BILL_TO);
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
        var customerControl = (CustomerControl)Session.getModelController(CustomerControl.class);
        Customer customer = party == null ? null : customerControl.getCustomer(party);
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
        var orderControl = (OrderControl)Session.getModelController(OrderControl.class);
        OrderRole shipToOrderRole = orderControl.getOrderRoleByOrderAndOrderRoleTypeUsingNames(order, OrderConstants.OrderRoleType_SHIP_TO);
        
        if(shipToOrderRole == null && billToFallback) {
            shipToOrderRole = orderControl.getOrderRoleByOrderAndOrderRoleTypeUsingNames(order, OrderConstants.OrderRoleType_BILL_TO);
            
            if(shipToOrderRole != null) {
                orderControl.createOrderRoleUsingNames(order, shipToOrderRole.getParty(), OrderConstants.OrderRoleType_SHIP_TO, createdBy);
            }
        }
        
        return shipToOrderRole == null ? null : shipToOrderRole.getParty();
    }
    
    /** Verify that the CustomerType is authorized to use the ShippingMethod. If there are no CustomerTypeShippingMethods for any ShippingMethod,
     * then it is assumed they're authorized.
     * 
     * @param eea Required.
     * @param customerType Required.
     * @param shippingMethod Required.
     */
    public void checkCustomerTypeShippingMethod(final ExecutionErrorAccumulator eea, CustomerType customerType, ShippingMethod shippingMethod) {
        var customerControl = (CustomerControl)Session.getModelController(CustomerControl.class);
        
        if(!customerControl.getCustomerTypeShippingMethodExists(customerType, shippingMethod)
                && customerControl.countCustomerTypeShippingMethodsByCustomerType(customerType) != 0) {
            handleExecutionError(UnknownCustomerTypeShippingMethodException.class, eea, ExecutionErrors.UnknownCustomerTypeShippingMethod.name(),
                    customerType.getLastDetail().getCustomerTypeName(), shippingMethod.getLastDetail().getShippingMethodName());
        }
    }
    
    /**
     * 
     * @param eea Optional.
     * @param order Required.
     * @param orderShipmentGroup Optional.
     * @param orderShipmentGroupSequence Optional.
     * @param orderLineSequence Optional.
     * @param parentOrderLine Optional.
     * @param partyContactMechanism Optional.
     * @param shippingMethod Optional.
     * @param item Required.
     * @param inventoryCondition Optional.
     * @param unitOfMeasureType Optional.
     * @param quantity Required.
     * @param unitAmount Optional for Items with a FIXED ItemPriceType, Required for VARIABLE.
     * @param description Optional.
     * @param taxable Optional.
     * @param offerUse Optional.
     * @param associateReferral Optional.
     * @param createdBy Required.
     * @return The newly created OrderLine, otherwise null if there was an error.
     */
    public OrderLine createSalesOrderLine(final Session session, final ExecutionErrorAccumulator eea, final Order order, OrderShipmentGroup orderShipmentGroup,
            final Integer orderShipmentGroupSequence, Integer orderLineSequence, final OrderLine parentOrderLine,
            PartyContactMechanism partyContactMechanism, ShippingMethod shippingMethod, final Item item,
            InventoryCondition inventoryCondition, UnitOfMeasureType unitOfMeasureType, final Long quantity, Long unitAmount,
            final String description, CancellationPolicy cancellationPolicy, ReturnPolicy returnPolicy, Boolean taxable, OfferUse offerUse,
            final AssociateReferral associateReferral, final PartyPK createdBy) {
        OrderLine orderLine = null;
        
        checkOrderAvailableForModification(session, eea, order, createdBy);

        if(eea == null || !eea.hasExecutionErrors()) {
            var itemControl = (ItemControl)Session.getModelController(ItemControl.class);
            var offerControl = (OfferControl)Session.getModelController(OfferControl.class);
            var salesControl = (SalesControl)Session.getModelController(SalesControl.class);
            OrderDetail orderDetail = order.getLastDetail();
            ItemDetail itemDetail = item.getLastDetail();
            ItemDeliveryType itemDeliveryType = itemDetail.getItemDeliveryType();
            Currency currency = orderDetail.getCurrency();
            CustomerType customerType = getOrderBillToCustomerType(order);

            if(customerType != null && shippingMethod != null) {
                checkCustomerTypeShippingMethod(eea, customerType, shippingMethod);
            }

            if(eea == null || !eea.hasExecutionErrors()) {
                // ItemDeliveryType must be checked against the ContactMechanismType for the partyContactMechanism.

                // Check to see if an orderShipmentGroup was supplied. If it wasn't, try to get a default one for this order and itemDeliveryType.
                // If a default does't exist, then create one.
                if(orderShipmentGroup == null) {
                    orderShipmentGroup = getDefaultOrderShipmentGroup(order, itemDeliveryType);

                    if(orderShipmentGroup == null) {
                        Boolean holdUntilComplete = order.getLastDetail().getHoldUntilComplete();
                        Party orderShipToParty = getOrderShipToParty(order, true, createdBy);

                        // If partyContactMechanism is null, attempt to get from SHIP_TO party for the order.
                        // If no SHIP_TO party exists, try to copy from the BILL_TO party.

                        // TODO.

                        // Select an appropriate partyContactMechanism for the itemDeliveryType.

                        // TODO.

                        orderShipmentGroup = createSalesOrderShipmentGroup(session, eea, order, orderShipmentGroupSequence, itemDeliveryType, Boolean.TRUE,
                                partyContactMechanism, shippingMethod, holdUntilComplete, createdBy);
                    } else {
                        OrderShipmentGroupDetail orderShipmentGroupDetail = orderShipmentGroup.getLastDetail();

                        partyContactMechanism = orderShipmentGroupDetail.getPartyContactMechanism();
                        shippingMethod = orderShipmentGroupDetail.getShippingMethod();
                    }
                }

                // If shippingMethod was specified, check to see if it can be used for this item and partyContactMechanism.

                // Check InventoryCondition.
                if(inventoryCondition == null) {
                    var inventoryControl = (InventoryControl)Session.getModelController(InventoryControl.class);

                    inventoryCondition = inventoryControl.getDefaultInventoryCondition();

                    if(inventoryControl == null) {
                        handleExecutionError(UnknownDefaultInventoryConditionException.class, eea, ExecutionErrors.UnknownDefaultInventoryCondition.name());
                    }
                }

                // Check UnitOfMeasureType.
                if(unitOfMeasureType == null) {
                    ItemUnitOfMeasureType itemUnitOfMeasureType = itemControl.getDefaultItemUnitOfMeasureType(item);

                    if(itemUnitOfMeasureType == null) {
                        handleExecutionError(UnknownDefaultItemUnitOfMeasureTypeException.class, eea, ExecutionErrors.UnknownDefaultItemUnitOfMeasureType.name());
                    } else {
                        unitOfMeasureType = itemUnitOfMeasureType.getUnitOfMeasureType();
                    }
                }

                // Verify the OfferItem exists.
                if(offerUse == null) {
                    SalesOrder salesOrder = salesControl.getSalesOrder(order);

                    offerUse = salesOrder.getOfferUse();
                }

                OfferItem offerItem = OfferItemLogic.getInstance().getOfferItem(eea, offerUse.getLastDetail().getOffer(), item);

                // Verify unitAmount.
                if(offerItem != null) {
                    OfferItemPrice offerItemPrice = offerControl.getOfferItemPrice(offerItem, inventoryCondition, unitOfMeasureType, currency);

                    if(offerItemPrice == null) {
                        handleExecutionError(UnknownOfferItemPriceException.class, eea, ExecutionErrors.UnknownOfferItemPrice.name());
                    } else {
                        String itemPriceTypeName = itemDetail.getItemPriceType().getItemPriceTypeName();

                        if(itemPriceTypeName.equals(ItemPriceTypes.FIXED.name())) {
                            // We'll accept the supplied unitAmount as long as it passes the limit checks later on. Any enforcement of
                            // security should come in the UC.
                            if(unitAmount == null) {
                                OfferItemFixedPrice offerItemFixedPrice = offerControl.getOfferItemFixedPrice(offerItemPrice);

                                unitAmount = offerItemFixedPrice.getUnitPrice();
                            }
                        } else if(itemPriceTypeName.equals(ItemPriceTypes.VARIABLE.name())) {
                            if(unitAmount == null) {
                                handleExecutionError(UnitAmountRequiredException.class, eea, ExecutionErrors.UnitAmountRequired.name());
                            } else {
                                OfferItemVariablePrice offerItemVariablePrice = offerControl.getOfferItemVariablePrice(offerItemPrice);

                                if(unitAmount < offerItemVariablePrice.getMinimumUnitPrice()) {
                                    handleExecutionError(UnitAmountBelowMinimumUnitPriceException.class, eea, ExecutionErrors.UnitAmountBelowMinimumUnitPrice.name());
                                }

                                if(unitAmount > offerItemVariablePrice.getMaximumUnitPrice()) {
                                    handleExecutionError(UnitAmountAboveMaximumUnitPriceException.class, eea, ExecutionErrors.UnitAmountAboveMaximumUnitPrice.name());
                                }

                                if(unitAmount % offerItemVariablePrice.getUnitPriceIncrement() != 0) {
                                    handleExecutionError(UnitAmountNotMultipleOfUnitPriceIncrementException.class, eea, ExecutionErrors.UnitAmountNotMultipleOfUnitPriceIncrement.name());
                                }
                            }
                        } else {
                            handleExecutionError(UnknownOfferItemPriceException.class, eea, ExecutionErrors.UnknownItemPriceType.name(), itemPriceTypeName);
                        }
                    }
                }

                // Check ItemUnitPriceLimits.
                if(unitAmount != null) {
                    ItemUnitPriceLimit itemUnitPriceLimit = itemControl.getItemUnitPriceLimit(item, inventoryCondition, unitOfMeasureType, currency);

                    // This isn't required. If it is missing, no check is performed.
                    if(itemUnitPriceLimit != null) {
                        Long minimumUnitPrice = itemUnitPriceLimit.getMinimumUnitPrice();
                        Long maximumUnitPrice = itemUnitPriceLimit.getMaximumUnitPrice();

                        if(minimumUnitPrice != null && unitAmount < minimumUnitPrice) {
                            handleExecutionError(UnitAmountBelowMinimumItemUnitPriceLimitException.class, eea, ExecutionErrors.UnitAmountBelowMinimumItemUnitPriceLimit.name());
                        }

                        if(maximumUnitPrice != null && unitAmount > maximumUnitPrice) {
                            handleExecutionError(UnitAmountAboveMaximumItemUnitPriceLimitException.class, eea, ExecutionErrors.UnitAmountAboveMaximumItemUnitPriceLimit.name());
                        }
                    }
                }

                // Check quantity being ordered and make sure that it's within acceptible limits. Both ItemUnitLimits and ItemUnitCustomerTypeLimits.
                if(inventoryCondition != null && unitOfMeasureType != null) {
                    ItemUnitLimit itemUnitLimit = itemControl.getItemUnitLimit(item, inventoryCondition, unitOfMeasureType);

                    if(itemUnitLimit != null) {
                        Long minimumQuantity = itemUnitLimit.getMinimumQuantity();
                        Long maximumQuantity = itemUnitLimit.getMaximumQuantity();

                        if(minimumQuantity != null && quantity < minimumQuantity) {
                            handleExecutionError(QuantityBelowMinimumItemUnitLimitException.class, eea, ExecutionErrors.QuantityBelowMinimumItemUnitLimit.name());
                        }

                        if(maximumQuantity != null && quantity > maximumQuantity) {
                            handleExecutionError(QuantityAboveMaximumItemUnitLimitException.class, eea, ExecutionErrors.QuantityAboveMaximumItemUnitLimit.name());
                        }
                    }

                    if(customerType != null) {
                        ItemUnitCustomerTypeLimit itemUnitCustomerTypeLimit = itemControl.getItemUnitCustomerTypeLimit(item, inventoryCondition, unitOfMeasureType, customerType);

                        if(itemUnitCustomerTypeLimit != null) {
                            Long minimumQuantity = itemUnitCustomerTypeLimit.getMinimumQuantity();
                            Long maximumQuantity = itemUnitCustomerTypeLimit.getMaximumQuantity();

                            if(minimumQuantity != null && quantity < minimumQuantity) {
                                handleExecutionError(QuantityBelowMinimumItemUnitCustomerTypeLimitException.class, eea, ExecutionErrors.QuantityBelowMinimumItemUnitCustomerTypeLimit.name());
                            }

                            if(maximumQuantity != null && quantity > maximumQuantity) {
                                handleExecutionError(QuantityAboveMaximumItemUnitCustomerTypeLimitException.class, eea, ExecutionErrors.QuantityAboveMaximumItemUnitCustomerTypeLimit.name());
                            }
                        }
                    }
                }

                // Check Item's SalesOrderStartTime and SalesOrderEndTime.
                Long salesOrderStartTime = itemDetail.getSalesOrderStartTime();
                if(salesOrderStartTime != null && session.START_TIME < salesOrderStartTime) {
                    handleExecutionError(CurrentTimeBeforeSalesOrderStartTimeException.class, eea, ExecutionErrors.CurrentTimeBeforeSalesOrderStartTime.name());
                }

                Long salesOrderEndTime = itemDetail.getSalesOrderEndTime();
                if(salesOrderEndTime != null && session.START_TIME > salesOrderEndTime) {
                    handleExecutionError(CurrentTimeAfterSalesOrderEndTimeException.class, eea, ExecutionErrors.CurrentTimeAfterSalesOrderEndTime.name());
                }

                // Check Item's status.
                if(!WorkflowLogic.getInstance().isEntityInWorkflowSteps(eea, ItemStatusConstants.Workflow_ITEM_STATUS, item,
                        ItemStatusConstants.WorkflowStep_ITEM_STATUS_DISCONTINUED).isEmpty()) {
                    handleExecutionError(ItemDiscontinuedException.class, eea, ExecutionErrors.ItemDiscontinued.name(), item.getLastDetail().getItemName());
                }

                // Create the line.
                if(eea == null || !eea.hasExecutionErrors()) {
                    // If a specific CancellationPolicy was specified, use that. Otherwise, try to use the one for the Item.
                    // If that's null, we'll leave it null for the OrderLine, which indicates that we should fall back to the
                    // one on the Order if it's ever needed.
                    if(cancellationPolicy == null) {
                        cancellationPolicy = itemDetail.getCancellationPolicy();
                    }

                    // If a specific ReturnPolicy was specified, use that. Otherwise, try to use the one for the Item.
                    // If that's null, we'll leave it null for the OrderLine, which indicates that we should fall back to the
                    // one on the Order if it's ever needed.
                    if(returnPolicy == null) {
                        returnPolicy = itemDetail.getReturnPolicy();
                    }

                    // If there was no taxable flag passed in, then get the taxable value from the Item. If that is true,
                    // the taxable flag from the Order will override it.
                    if(taxable == null) {
                        // taxable = itemDetail.getTaxable();
                        taxable = Boolean.TRUE; // TODO: This needs to consider the GeoCode-aware taxing system.
                        
                        if(taxable) {
                            taxable = orderDetail.getTaxable();
                        }
                    }

                    orderLine = createOrderLine(session, eea, order, orderLineSequence, parentOrderLine, orderShipmentGroup, item, inventoryCondition,
                            unitOfMeasureType, quantity, unitAmount, description, cancellationPolicy, returnPolicy, taxable, createdBy);

                    if(eea == null || !eea.hasExecutionErrors()) {
                        salesControl.createSalesOrderLine(orderLine, offerUse, associateReferral, createdBy);
                    }
                }
            }
        }
        
        return orderLine;
    }

    public OrderLine getOrderLineByName(final ExecutionErrorAccumulator eea, final String orderName, final String orderLineSequence) {
        return getOrderLineByName(eea, OrderConstants.OrderType_SALES_ORDER, orderName, orderLineSequence);
    }

    public OrderLine getOrderLineByNameForUpdate(final ExecutionErrorAccumulator eea, final String orderName, final String orderLineSequence) {
        return getOrderLineByNameForUpdate(eea, OrderConstants.OrderType_SALES_ORDER, orderName, orderLineSequence);
    }

}
