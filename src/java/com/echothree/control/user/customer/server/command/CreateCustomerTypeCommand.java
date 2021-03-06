// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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
import com.echothree.model.control.accounting.common.AccountingConstants;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.cancellationpolicy.common.CancellationKinds;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.control.customer.common.workflow.CustomerCreditStatusConstants;
import com.echothree.model.control.customer.common.workflow.CustomerStatusConstants;
import com.echothree.model.control.customer.server.control.CustomerControl;
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
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationKind;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.customer.server.entity.CustomerType;
import com.echothree.model.data.inventory.server.entity.AllocationPriority;
import com.echothree.model.data.offer.server.entity.Offer;
import com.echothree.model.data.offer.server.entity.OfferUse;
import com.echothree.model.data.offer.server.entity.Source;
import com.echothree.model.data.offer.server.entity.Use;
import com.echothree.model.data.returnpolicy.server.entity.ReturnKind;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.workflow.server.entity.Workflow;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateCustomerTypeCommand
        extends BaseSimpleCommand<CreateCustomerTypeForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.CustomerType.name(), SecurityRoles.Create.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
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
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of CreateCustomerTypeCommand */
    public CreateCustomerTypeCommand(UserVisitPK userVisitPK, CreateCustomerTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var customerControl = Session.getModelController(CustomerControl.class);
        String customerTypeName = form.getCustomerTypeName();
        CustomerType customerType = customerControl.getCustomerTypeByName(customerTypeName);
        
        if(customerType == null) {
            String customerSequenceName = form.getCustomerSequenceName();
            Sequence customerSequence = null;
            
            if(customerSequenceName != null) {
                var sequenceControl = Session.getModelController(SequenceControl.class);
                SequenceType sequenceType = sequenceControl.getSequenceTypeByName(SequenceTypes.CUSTOMER.name());
                
                if(sequenceType != null) {
                    customerSequence = sequenceControl.getSequenceByName(sequenceType, customerSequenceName);
                } // TODO: unknown sequenceType, shouldn't happen
            }
            
            if(customerSequenceName == null || customerSequence != null) {
                var offerControl = Session.getModelController(OfferControl.class);
                String defaultOfferName = form.getDefaultOfferName();
                String defaultUseName = form.getDefaultUseName();
                String defaultSourceName = form.getDefaultSourceName();
                OfferUse defaultOfferUse = null;
                boolean invalidDefaultOfferOrSourceSpecification = false;
                
                if(defaultOfferName != null && defaultUseName != null && defaultSourceName == null) {
                    Offer defaultOffer = offerControl.getOfferByName(defaultOfferName);
                    
                    if(defaultOffer != null) {
                        var useControl = Session.getModelController(UseControl.class);
                        Use defaultUse = useControl.getUseByName(defaultUseName);
                        
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
                    Source source = sourceControl.getSourceByName(defaultSourceName);
                    
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
                        String defaultCancellationPolicyName = form.getDefaultCancellationPolicyName();
                        CancellationPolicy defaultCancellationPolicy = null;
                        
                        if(defaultCancellationPolicyName != null) {
                            var cancellationPolicyControl = Session.getModelController(CancellationPolicyControl.class);
                            CancellationKind returnKind = cancellationPolicyControl.getCancellationKindByName(CancellationKinds.CUSTOMER_CANCELLATION.name());
                            
                            defaultCancellationPolicy = cancellationPolicyControl.getCancellationPolicyByName(returnKind, defaultCancellationPolicyName);
                        }
                        
                        if(defaultCancellationPolicyName == null || defaultCancellationPolicy != null) {
                            String defaultReturnPolicyName = form.getDefaultReturnPolicyName();
                            ReturnPolicy defaultReturnPolicy = null;
                            
                            if(defaultReturnPolicyName != null) {
                                var returnPolicyControl = Session.getModelController(ReturnPolicyControl.class);
                                ReturnKind returnKind = returnPolicyControl.getReturnKindByName(ReturnKinds.CUSTOMER_RETURN.name());
                                
                                defaultReturnPolicy = returnPolicyControl.getReturnPolicyByName(returnKind, defaultReturnPolicyName);
                            }
                            
                            if(defaultReturnPolicyName == null || defaultReturnPolicy != null) {
                                var workflowControl = Session.getModelController(WorkflowControl.class);
                                String defaultCustomerStatusChoice = form.getDefaultCustomerStatusChoice();
                                WorkflowEntrance defaultCustomerStatus = null;
                                
                                if(defaultCustomerStatusChoice != null) {
                                    Workflow workflow = workflowControl.getWorkflowByName(CustomerStatusConstants.Workflow_CUSTOMER_STATUS);
                                    defaultCustomerStatus = workflowControl.getWorkflowEntranceByName(workflow, defaultCustomerStatusChoice);
                                }
                                
                                if(defaultCustomerStatusChoice == null || defaultCustomerStatus != null) {
                                    String defaultCustomerCreditStatusChoice = form.getDefaultCustomerCreditStatusChoice();
                                    WorkflowEntrance defaultCustomerCreditStatus = null;
                                    
                                    if(defaultCustomerCreditStatusChoice != null) {
                                        Workflow workflow = workflowControl.getWorkflowByName(CustomerCreditStatusConstants.Workflow_CUSTOMER_CREDIT_STATUS);
                                        defaultCustomerCreditStatus = workflowControl.getWorkflowEntranceByName(workflow, defaultCustomerCreditStatusChoice);
                                    }
                                    
                                    if(defaultCustomerCreditStatusChoice == null || defaultCustomerCreditStatus != null) {
                                        var accountingControl = Session.getModelController(AccountingControl.class);
                                        String defaultArGlAccountName = form.getDefaultArGlAccountName();
                                        GlAccount defaultArGlAccount = defaultArGlAccountName == null ? null : accountingControl.getGlAccountByName(defaultArGlAccountName);

                                        if(defaultArGlAccountName == null || defaultArGlAccount != null) {
                                            String glAccountCategoryName = defaultArGlAccount == null ? null
                                                    : defaultArGlAccount.getLastDetail().getGlAccountCategory().getLastDetail().getGlAccountCategoryName();

                                            if(glAccountCategoryName == null || glAccountCategoryName.equals(AccountingConstants.GlAccountCategory_ACCOUNTS_RECEIVABLE)) {
                                                String allocationPriorityName = form.getAllocationPriorityName();
                                                AllocationPriority allocationPriority = allocationPriorityName == null ? null : AllocationPriorityLogic.getInstance().getAllocationPriorityByName(this, allocationPriorityName);
                                                
                                                if(!hasExecutionErrors()) {
                                                    var partyPK = getPartyPK();
                                                    Boolean defaultHoldUntilComplete = Boolean.valueOf(form.getDefaultHoldUntilComplete());
                                                    Boolean defaultAllowBackorders = Boolean.valueOf(form.getDefaultAllowBackorders());
                                                    Boolean defaultAllowSubstitutions = Boolean.valueOf(form.getDefaultAllowSubstitutions());
                                                    Boolean defaultAllowCombiningShipments = Boolean.valueOf(form.getDefaultAllowCombiningShipments());
                                                    Boolean defaultRequireReference = Boolean.valueOf(form.getDefaultRequireReference());
                                                    Boolean defaultAllowReferenceDuplicates = Boolean.valueOf(form.getDefaultAllowReferenceDuplicates());
                                                    String defaultReferenceValidationPattern = form.getDefaultReferenceValidationPattern();
                                                    Boolean defaultTaxable = Boolean.valueOf(form.getDefaultTaxable());
                                                    var isDefault = Boolean.valueOf(form.getIsDefault());
                                                    var sortOrder = Integer.valueOf(form.getSortOrder());
                                                    var description = form.getDescription();

                                                    customerType = customerControl.createCustomerType(customerTypeName, customerSequence, defaultOfferUse,
                                                            defaultTerm, defaultFreeOnBoard, defaultCancellationPolicy, defaultReturnPolicy, defaultCustomerStatus,
                                                            defaultCustomerCreditStatus, defaultArGlAccount, defaultHoldUntilComplete, defaultAllowBackorders,
                                                            defaultAllowSubstitutions, defaultAllowCombiningShipments, defaultRequireReference,
                                                            defaultAllowReferenceDuplicates, defaultReferenceValidationPattern, defaultTaxable,
                                                            allocationPriority, isDefault, sortOrder, partyPK);

                                                    if(description != null) {
                                                        customerControl.createCustomerTypeDescription(customerType, getPreferredLanguage(), description, partyPK);
                                                    }
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
        } else {
            addExecutionError(ExecutionErrors.DuplicateCustomerTypeName.name(), customerTypeName);
        }
        
        return null;
    }
    
}
