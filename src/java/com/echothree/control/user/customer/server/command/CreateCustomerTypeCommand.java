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

package com.echothree.control.user.customer.server.command;

import com.echothree.control.user.customer.common.form.CreateCustomerTypeForm;
import com.echothree.control.user.customer.common.result.CustomerResultFactory;
import com.echothree.model.control.accounting.common.AccountingConstants;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.cancellationpolicy.common.CancellationKinds;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.control.customer.common.workflow.CustomerCreditStatusConstants;
import com.echothree.model.control.customer.common.workflow.CustomerStatusConstants;
import com.echothree.model.control.customer.server.logic.CustomerTypeLogic;
import com.echothree.model.control.inventory.server.logic.AllocationPriorityLogic;
import com.echothree.model.control.offer.server.control.OfferControl;
import com.echothree.model.control.offer.server.control.OfferUseControl;
import com.echothree.model.control.offer.server.control.SourceControl;
import com.echothree.model.control.offer.server.control.UseControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.returnpolicy.common.ReturnKinds;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.shipment.server.logic.FreeOnBoardLogic;
import com.echothree.model.control.term.server.logic.TermLogic;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.customer.server.entity.CustomerType;
import com.echothree.model.data.offer.server.entity.OfferUse;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class CreateCustomerTypeCommand
        extends BaseSimpleCommand<CreateCustomerTypeForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.CustomerType.name(), SecurityRoles.Create.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("CustomerTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CustomerSequenceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DefaultOfferName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DefaultUseName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DefaultSourceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DefaultTermName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DefaultFreeOnBoardName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DefaultCancellationPolicyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DefaultReturnPolicyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DefaultCustomerStatusChoice", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DefaultCustomerCreditStatusChoice", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DefaultArGlAccountName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DefaultHoldUntilComplete", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("DefaultAllowBackorders", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("DefaultAllowSubstitutions", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("DefaultAllowCombiningShipments", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("DefaultRequireReference", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("DefaultAllowReferenceDuplicates", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("DefaultReferenceValidationPattern", FieldType.REGULAR_EXPRESSION, false, null, null),
                new FieldDefinition("DefaultTaxable", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("AllocationPriorityName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }
    
    /** Creates a new instance of CreateCustomerTypeCommand */
    public CreateCustomerTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = CustomerResultFactory.getCreateCustomerTypeResult();
        CustomerType customerType = null;
        var customerSequenceName = form.getCustomerSequenceName();
        Sequence customerSequence = null;
        
        if(customerSequenceName != null) {
            var sequenceControl = Session.getModelController(SequenceControl.class);
            var sequenceType = sequenceControl.getSequenceTypeByName(SequenceTypes.CUSTOMER.name());
            
            if(sequenceType != null) {
                customerSequence = sequenceControl.getSequenceByName(sequenceType, customerSequenceName);
            } // TODO: unknown sequenceType, shouldn't happen
        }
        
        if(customerSequenceName == null || customerSequence != null) {
            var offerControl = Session.getModelController(OfferControl.class);
            var defaultOfferName = form.getDefaultOfferName();
            var defaultUseName = form.getDefaultUseName();
            var defaultSourceName = form.getDefaultSourceName();
            OfferUse defaultOfferUse = null;
            var invalidDefaultOfferOrSourceSpecification = false;
            
            if(defaultOfferName != null && defaultUseName != null && defaultSourceName == null) {
                var defaultOffer = offerControl.getOfferByName(defaultOfferName);
                
                if(defaultOffer != null) {
                    var useControl = Session.getModelController(UseControl.class);
                    var defaultUse = useControl.getUseByName(defaultUseName);
                    
                    if(defaultUse != null) {
                        var offerUseControl = Session.getModelController(OfferUseControl.class);
                        defaultOfferUse = offerUseControl.getOfferUse(defaultOffer, defaultUse);
                        
                        if(defaultOfferUse == null) {
                            addExecutionError(ExecutionErrors.UnknownDefaultOfferUse.name());
                        }
                    }  else {
                        addExecutionError(ExecutionErrors.UnknownDefaultUseName.name(), defaultUseName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownDefaultOfferName.name(), defaultOfferName);
                }
            } else if(defaultOfferName == null && defaultUseName == null && defaultSourceName != null) {
                var sourceControl = Session.getModelController(SourceControl.class);
                var source = sourceControl.getSourceByName(defaultSourceName);
                
                if(source != null) {
                    defaultOfferUse = source.getLastDetail().getOfferUse();
                } else {
                    addExecutionError(ExecutionErrors.UnknownDefaultSourceName.name(), defaultSourceName);
                }
            } else if(defaultOfferName == null && defaultUseName == null && defaultSourceName == null) {
                // nothing
            } else {
                addExecutionError(ExecutionErrors.InvalidDefaultOfferOrSourceSpecification.name());
                invalidDefaultOfferOrSourceSpecification = true;
            }
            
            if(!invalidDefaultOfferOrSourceSpecification) {
                var defaultTermName = form.getDefaultTermName();
                var defaultFreeOnBoardName = form.getDefaultFreeOnBoardName();
                var defaultTerm = defaultTermName == null ? null : TermLogic.getInstance().getTermByName(this, defaultTermName);
                var defaultFreeOnBoard = defaultFreeOnBoardName == null ? null : FreeOnBoardLogic.getInstance().getFreeOnBoardByName(this, defaultFreeOnBoardName);

                if(!hasExecutionErrors()) {
                    var defaultCancellationPolicyName = form.getDefaultCancellationPolicyName();
                    CancellationPolicy defaultCancellationPolicy = null;
                    
                    if(defaultCancellationPolicyName != null) {
                        var cancellationPolicyControl = Session.getModelController(CancellationPolicyControl.class);
                        var returnKind = cancellationPolicyControl.getCancellationKindByName(CancellationKinds.CUSTOMER_CANCELLATION.name());
                        
                        defaultCancellationPolicy = cancellationPolicyControl.getCancellationPolicyByName(returnKind, defaultCancellationPolicyName);
                    }
                    
                    if(defaultCancellationPolicyName == null || defaultCancellationPolicy != null) {
                        var defaultReturnPolicyName = form.getDefaultReturnPolicyName();
                        ReturnPolicy defaultReturnPolicy = null;
                        
                        if(defaultReturnPolicyName != null) {
                            var returnPolicyControl = Session.getModelController(ReturnPolicyControl.class);
                            var returnKind = returnPolicyControl.getReturnKindByName(ReturnKinds.CUSTOMER_RETURN.name());
                            
                            defaultReturnPolicy = returnPolicyControl.getReturnPolicyByName(returnKind, defaultReturnPolicyName);
                        }
                        
                        if(defaultReturnPolicyName == null || defaultReturnPolicy != null) {
                            var workflowControl = Session.getModelController(WorkflowControl.class);
                            var defaultCustomerStatusChoice = form.getDefaultCustomerStatusChoice();
                            WorkflowEntrance defaultCustomerStatus = null;
                            
                            if(defaultCustomerStatusChoice != null) {
                                var workflow = workflowControl.getWorkflowByName(CustomerStatusConstants.Workflow_CUSTOMER_STATUS);
                                defaultCustomerStatus = workflowControl.getWorkflowEntranceByName(workflow, defaultCustomerStatusChoice);
                            }
                            
                            if(defaultCustomerStatusChoice == null || defaultCustomerStatus != null) {
                                var defaultCustomerCreditStatusChoice = form.getDefaultCustomerCreditStatusChoice();
                                WorkflowEntrance defaultCustomerCreditStatus = null;
                                
                                if(defaultCustomerCreditStatusChoice != null) {
                                    var workflow = workflowControl.getWorkflowByName(CustomerCreditStatusConstants.Workflow_CUSTOMER_CREDIT_STATUS);
                                    defaultCustomerCreditStatus = workflowControl.getWorkflowEntranceByName(workflow, defaultCustomerCreditStatusChoice);
                                }
                                
                                if(defaultCustomerCreditStatusChoice == null || defaultCustomerCreditStatus != null) {
                                    var accountingControl = Session.getModelController(AccountingControl.class);
                                    var defaultArGlAccountName = form.getDefaultArGlAccountName();
                                    var defaultArGlAccount = defaultArGlAccountName == null ? null : accountingControl.getGlAccountByName(defaultArGlAccountName);

                                    if(defaultArGlAccountName == null || defaultArGlAccount != null) {
                                        var glAccountCategoryName = defaultArGlAccount == null ? null
                                                : defaultArGlAccount.getLastDetail().getGlAccountCategory().getLastDetail().getGlAccountCategoryName();

                                        if(glAccountCategoryName == null || glAccountCategoryName.equals(AccountingConstants.GlAccountCategory_ACCOUNTS_RECEIVABLE)) {
                                            var allocationPriorityName = form.getAllocationPriorityName();
                                            var allocationPriority = allocationPriorityName == null ? null : AllocationPriorityLogic.getInstance().getAllocationPriorityByName(this, allocationPriorityName);
                                            
                                            if(!hasExecutionErrors()) {
                                                var customerTypeName = form.getCustomerTypeName();
                                                var defaultHoldUntilComplete = Boolean.valueOf(form.getDefaultHoldUntilComplete());
                                                var defaultAllowBackorders = Boolean.valueOf(form.getDefaultAllowBackorders());
                                                var defaultAllowSubstitutions = Boolean.valueOf(form.getDefaultAllowSubstitutions());
                                                var defaultAllowCombiningShipments = Boolean.valueOf(form.getDefaultAllowCombiningShipments());
                                                var defaultRequireReference = Boolean.valueOf(form.getDefaultRequireReference());
                                                var defaultAllowReferenceDuplicates = Boolean.valueOf(form.getDefaultAllowReferenceDuplicates());
                                                var defaultReferenceValidationPattern = form.getDefaultReferenceValidationPattern();
                                                var defaultTaxable = Boolean.valueOf(form.getDefaultTaxable());
                                                var isDefault = Boolean.valueOf(form.getIsDefault());
                                                var sortOrder = Integer.valueOf(form.getSortOrder());
                                                var description = form.getDescription();
                                                var partyPK = getPartyPK();

                                                customerType = CustomerTypeLogic.getInstance().createCustomerType(this, customerTypeName, customerSequence, defaultOfferUse,
                                                        defaultTerm, defaultFreeOnBoard, defaultCancellationPolicy, defaultReturnPolicy, defaultCustomerStatus,
                                                        defaultCustomerCreditStatus, defaultArGlAccount, defaultHoldUntilComplete, defaultAllowBackorders,
                                                        defaultAllowSubstitutions, defaultAllowCombiningShipments, defaultRequireReference,
                                                        defaultAllowReferenceDuplicates, defaultReferenceValidationPattern, defaultTaxable,
                                                        allocationPriority, isDefault, sortOrder, getPreferredLanguage(), description, partyPK);
                                            }
                                        } else {
                                            addExecutionError(ExecutionErrors.InvalidGlAccountCategory.name(), glAccountCategoryName);
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.UnknownDefaultArGlAccountName.name(), defaultArGlAccountName);
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownDefaultCustomerCreditStatusChoice.name());
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownDefaultCustomerStatusChoice.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownReturnPolicyName.name(), defaultReturnPolicyName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownCancellationPolicyName.name(), defaultCancellationPolicyName);
                    }
                }
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCustomerSequenceName.name(), customerSequenceName);
        }
        
        if(customerType != null) {
            result.setEntityRef(customerType.getPrimaryKey().getEntityRef());
            result.setCustomerTypeName(customerType.getLastDetail().getCustomerTypeName());
        }

        return result;
    }
    
}
