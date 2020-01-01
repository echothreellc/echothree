// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.control.user.customer.common.edit.CustomerEditFactory;
import com.echothree.control.user.customer.common.edit.CustomerTypeEdit;
import com.echothree.control.user.customer.common.form.EditCustomerTypeForm;
import com.echothree.control.user.customer.common.result.CustomerResultFactory;
import com.echothree.control.user.customer.common.result.EditCustomerTypeResult;
import com.echothree.control.user.customer.common.spec.CustomerTypeSpec;
import com.echothree.model.control.accounting.common.AccountingConstants;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.cancellationpolicy.common.CancellationPolicyConstants;
import com.echothree.model.control.cancellationpolicy.server.CancellationPolicyControl;
import com.echothree.model.control.customer.server.CustomerControl;
import com.echothree.model.control.inventory.server.logic.AllocationPriorityLogic;
import com.echothree.model.control.offer.server.OfferControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.returnpolicy.common.ReturnPolicyConstants;
import com.echothree.model.control.returnpolicy.server.ReturnPolicyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.sequence.common.SequenceConstants;
import com.echothree.model.control.sequence.server.SequenceControl;
import com.echothree.model.control.term.server.TermControl;
import com.echothree.model.control.customer.common.workflow.CustomerCreditStatusConstants;
import com.echothree.model.control.customer.common.workflow.CustomerStatusConstants;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationKind;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.customer.server.entity.CustomerType;
import com.echothree.model.data.customer.server.entity.CustomerTypeDescription;
import com.echothree.model.data.customer.server.entity.CustomerTypeDetail;
import com.echothree.model.data.customer.server.value.CustomerTypeDescriptionValue;
import com.echothree.model.data.customer.server.value.CustomerTypeDetailValue;
import com.echothree.model.data.inventory.server.entity.AllocationPriority;
import com.echothree.model.data.offer.server.entity.Offer;
import com.echothree.model.data.offer.server.entity.OfferUse;
import com.echothree.model.data.offer.server.entity.Source;
import com.echothree.model.data.offer.server.entity.Use;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.returnpolicy.server.entity.ReturnKind;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.term.server.entity.Term;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class EditCustomerTypeCommand
        extends BaseEditCommand<CustomerTypeSpec, CustomerTypeEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.CustomerType.name(), SecurityRoles.Edit.name())
                    )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CustomerTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CustomerTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CustomerSequenceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DefaultOfferName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DefaultUseName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DefaultSourceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DefaultTermName", FieldType.ENTITY_NAME, false, null, null),
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
    
    /** Creates a new instance of EditCustomerTypeCommand */
    public EditCustomerTypeCommand(UserVisitPK userVisitPK, EditCustomerTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var customerControl = (CustomerControl)Session.getModelController(CustomerControl.class);
        var offerControl = (OfferControl)Session.getModelController(OfferControl.class);
        EditCustomerTypeResult result = CustomerResultFactory.getEditCustomerTypeResult();
        
        if(editMode.equals(EditMode.LOCK)) {
            String customerTypeName = spec.getCustomerTypeName();
            CustomerType customerType = customerControl.getCustomerTypeByName(customerTypeName);
            
            if(customerType != null) {
                result.setCustomerType(customerControl.getCustomerTypeTransfer(getUserVisit(), customerType));
                
                if(lockEntity(customerType)) {
                    CustomerTypeDescription customerTypeDescription = customerControl.getCustomerTypeDescription(customerType, getPreferredLanguage());
                    CustomerTypeEdit edit = CustomerEditFactory.getCustomerTypeEdit();
                    CustomerTypeDetail customerTypeDetail = customerType.getLastDetail();
                    Sequence customerSequence = customerTypeDetail.getCustomerSequence();
                    OfferUse defaultOfferUse = customerTypeDetail.getDefaultOfferUse();
                    Term defaultTerm = customerTypeDetail.getDefaultTerm();
                    CancellationPolicy defaultCancellationPolicy = customerTypeDetail.getDefaultCancellationPolicy();
                    ReturnPolicy defaultReturnPolicy = customerTypeDetail.getDefaultReturnPolicy();
                    WorkflowEntrance defaultCustomerStatusChoice = customerTypeDetail.getDefaultCustomerStatus();
                    WorkflowEntrance defaultCustomerCreditStatusChoice = customerTypeDetail.getDefaultCustomerCreditStatus();
                    GlAccount defaultArGlAccount = customerTypeDetail.getDefaultArGlAccount();
                    AllocationPriority allocationPriority = customerTypeDetail.getAllocationPriority();
                    
                    Collection<Source> sources = defaultOfferUse == null ? null : offerControl.getSourcesByOfferUse(defaultOfferUse);
                    Iterator<Source> sourcesIterator = sources == null ? null : sources.iterator();
                    Source defaultSource = sourcesIterator == null ? null : sourcesIterator.hasNext() ? sourcesIterator.next() : null;

                    result.setEdit(edit);
                    edit.setCustomerTypeName(customerTypeDetail.getCustomerTypeName());
                    edit.setCustomerSequenceName(customerSequence == null ? null : customerSequence.getLastDetail().getSequenceName());
                    edit.setDefaultSourceName(defaultSource == null ? null : defaultSource.getLastDetail().getSourceName());
                    edit.setDefaultTermName(defaultTerm == null ? null : defaultTerm.getLastDetail().getTermName());
                    edit.setDefaultCancellationPolicyName(defaultCancellationPolicy == null ? null : defaultCancellationPolicy.getLastDetail().getCancellationPolicyName());
                    edit.setDefaultReturnPolicyName(defaultReturnPolicy == null ? null : defaultReturnPolicy.getLastDetail().getReturnPolicyName());
                    edit.setDefaultCustomerStatusChoice(defaultCustomerStatusChoice == null ? null : defaultCustomerStatusChoice.getLastDetail().getWorkflowEntranceName());
                    edit.setDefaultCustomerCreditStatusChoice(defaultCustomerCreditStatusChoice == null ? null : defaultCustomerCreditStatusChoice.getLastDetail().getWorkflowEntranceName());
                    edit.setDefaultArGlAccountName(defaultArGlAccount == null ? null : defaultArGlAccount.getLastDetail().getGlAccountName());
                    edit.setDefaultHoldUntilComplete(customerTypeDetail.getDefaultHoldUntilComplete().toString());
                    edit.setDefaultAllowBackorders(customerTypeDetail.getDefaultAllowBackorders().toString());
                    edit.setDefaultAllowSubstitutions(customerTypeDetail.getDefaultAllowSubstitutions().toString());
                    edit.setDefaultAllowCombiningShipments(customerTypeDetail.getDefaultAllowCombiningShipments().toString());
                    edit.setDefaultRequireReference(customerTypeDetail.getDefaultRequireReference().toString());
                    edit.setDefaultAllowReferenceDuplicates(customerTypeDetail.getDefaultAllowReferenceDuplicates().toString());
                    edit.setDefaultReferenceValidationPattern(customerTypeDetail.getDefaultReferenceValidationPattern());
                    edit.setDefaultTaxable(customerTypeDetail.getDefaultTaxable().toString());
                    edit.setAllocationPriorityName(allocationPriority == null ? null : allocationPriority.getLastDetail().getAllocationPriorityName());
                    edit.setIsDefault(customerTypeDetail.getIsDefault().toString());
                    edit.setSortOrder(customerTypeDetail.getSortOrder().toString());
                    
                    if(customerTypeDescription != null)
                        edit.setDescription(customerTypeDescription.getDescription());
                } else {
                    addExecutionError(ExecutionErrors.EntityLockFailed.name());
                }
                
                result.setEntityLock(getEntityLockTransfer(customerType));
            } else {
                addExecutionError(ExecutionErrors.UnknownCustomerTypeName.name(), customerTypeName);
            }
        } else if(editMode.equals(EditMode.UPDATE)) {
            String customerTypeName = spec.getCustomerTypeName();
            CustomerType customerType = customerControl.getCustomerTypeByNameForUpdate(customerTypeName);
            
            if(customerType != null) {
                customerTypeName = edit.getCustomerTypeName();
                CustomerType duplicateCustomerType = customerControl.getCustomerTypeByName(customerTypeName);
                
                if(duplicateCustomerType == null || customerType.equals(duplicateCustomerType)) {
                    String customerSequenceName = edit.getCustomerSequenceName();
                    Sequence customerSequence = null;
                    
                    if(customerSequenceName != null) {
                        var sequenceControl = (SequenceControl)Session.getModelController(SequenceControl.class);
                        SequenceType sequenceType = sequenceControl.getSequenceTypeByName(SequenceConstants.SequenceType_CUSTOMER);
                        
                        if(sequenceType != null) {
                            customerSequence = sequenceControl.getSequenceByName(sequenceType, customerSequenceName);
                        } // TODO: unknown sequenceType, shouldn't happen
                    }
                    
                    if(customerSequenceName == null || customerSequence != null) {
                        String defaultOfferName = edit.getDefaultOfferName();
                        String defaultUseName = edit.getDefaultUseName();
                        String defaultSourceName = edit.getDefaultSourceName();
                        OfferUse defaultOfferUse = null;
                        boolean invalidDefaultOfferOrSourceSpecification = false;
                        
                        if(defaultOfferName != null && defaultUseName != null && defaultSourceName == null) {
                            Offer defaultOffer = offerControl.getOfferByName(defaultOfferName);
                            
                            if(defaultOffer != null) {
                                Use defaultUse = offerControl.getUseByName(defaultUseName);
                                
                                if(defaultUse != null) {
                                    defaultOfferUse = offerControl.getOfferUse(defaultOffer, defaultUse);
                                    
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
                            Source source = offerControl.getSourceByName(defaultSourceName);
                            
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
                            String defaultTermName = edit.getDefaultTermName();
                            Term defaultTerm = null;
                            
                            if(defaultTermName != null) {
                                var termControl = (TermControl)Session.getModelController(TermControl.class);
                                
                                defaultTerm = termControl.getTermByName(defaultTermName);
                            }
                            
                            if(defaultTermName == null || defaultTerm != null) {
                                String defaultCancellationPolicyName = edit.getDefaultCancellationPolicyName();
                                CancellationPolicy defaultCancellationPolicy = null;
                                
                                if(defaultCancellationPolicyName != null) {
                                    var cancellationPolicyControl = (CancellationPolicyControl)Session.getModelController(CancellationPolicyControl.class);
                                    CancellationKind cancellationKind = cancellationPolicyControl.getCancellationKindByName(CancellationPolicyConstants.CancellationKind_CUSTOMER_CANCELLATION);
                                    
                                    defaultCancellationPolicy = cancellationPolicyControl.getCancellationPolicyByName(cancellationKind, defaultCancellationPolicyName);
                                }
                                
                                if(defaultCancellationPolicyName == null || defaultCancellationPolicy != null) {
                                    String defaultReturnPolicyName = edit.getDefaultReturnPolicyName();
                                    ReturnPolicy defaultReturnPolicy = null;
                                    
                                    if(defaultReturnPolicyName != null) {
                                        var returnPolicyControl = (ReturnPolicyControl)Session.getModelController(ReturnPolicyControl.class);
                                        ReturnKind returnKind = returnPolicyControl.getReturnKindByName(ReturnPolicyConstants.ReturnKind_CUSTOMER_RETURN);
                                        
                                        defaultReturnPolicy = returnPolicyControl.getReturnPolicyByName(returnKind, defaultReturnPolicyName);
                                    }
                                    
                                    if(defaultReturnPolicyName == null || defaultReturnPolicy != null) {
                                        var workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
                                        String defaultCustomerStatusChoice = edit.getDefaultCustomerStatusChoice();
                                        WorkflowEntrance defaultCustomerStatus = null;
                                        
                                        if(defaultCustomerStatusChoice != null) {
                                            Workflow workflow = workflowControl.getWorkflowByName(CustomerStatusConstants.Workflow_CUSTOMER_STATUS);
                                            defaultCustomerStatus = workflowControl.getWorkflowEntranceByName(workflow, defaultCustomerStatusChoice);
                                        }
                                        
                                        if(defaultCustomerStatusChoice == null || defaultCustomerStatus != null) {
                                            String defaultCustomerCreditStatusChoice = edit.getDefaultCustomerCreditStatusChoice();
                                            WorkflowEntrance defaultCustomerCreditStatus = null;
                                            
                                            if(defaultCustomerCreditStatusChoice != null) {
                                                Workflow workflow = workflowControl.getWorkflowByName(CustomerCreditStatusConstants.Workflow_CUSTOMER_CREDIT_STATUS);
                                                defaultCustomerCreditStatus = workflowControl.getWorkflowEntranceByName(workflow, defaultCustomerCreditStatusChoice);
                                            }
                                            
                                            if(defaultCustomerCreditStatusChoice == null || defaultCustomerCreditStatus != null) {
                                                var accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
                                                String defaultArGlAccountName = edit.getDefaultArGlAccountName();
                                                GlAccount defaultArGlAccount = defaultArGlAccountName == null ? null : accountingControl.getGlAccountByName(defaultArGlAccountName);

                                                if(defaultArGlAccountName == null || defaultArGlAccount != null) {
                                                    String glAccountCategoryName = defaultArGlAccount == null ? null
                                                            : defaultArGlAccount.getLastDetail().getGlAccountCategory().getLastDetail().getGlAccountCategoryName();

                                                    if(glAccountCategoryName == null || glAccountCategoryName.equals(AccountingConstants.GlAccountCategory_ACCOUNTS_RECEIVABLE)) {
                                                        String allocationPriorityName = edit.getAllocationPriorityName();
                                                        AllocationPriority allocationPriority = allocationPriorityName == null ? null : AllocationPriorityLogic.getInstance().getAllocationPriorityByName(this, allocationPriorityName);

                                                        if(!hasExecutionErrors()) {
                                                            if(lockEntityForUpdate(customerType)) {
                                                                try {
                                                                    PartyPK partyPK = getPartyPK();
                                                                    CustomerTypeDetailValue customerTypeDetailValue = customerControl.getCustomerTypeDetailValueForUpdate(customerType);
                                                                    CustomerTypeDescription customerTypeDescription = customerControl.getCustomerTypeDescriptionForUpdate(customerType, getPreferredLanguage());
                                                                    String description = edit.getDescription();

                                                                    customerTypeDetailValue.setCustomerTypeName(edit.getCustomerTypeName());
                                                                    customerTypeDetailValue.setCustomerSequencePK(customerSequence == null ? null : customerSequence.getPrimaryKey());
                                                                    customerTypeDetailValue.setDefaultOfferUsePK(defaultOfferUse == null ? null : defaultOfferUse.getPrimaryKey());
                                                                    customerTypeDetailValue.setDefaultTermPK(defaultTerm == null ? null : defaultTerm.getPrimaryKey());
                                                                    customerTypeDetailValue.setDefaultCancellationPolicyPK(defaultCancellationPolicy == null ? null : defaultCancellationPolicy.getPrimaryKey());
                                                                    customerTypeDetailValue.setDefaultReturnPolicyPK(defaultReturnPolicy == null ? null : defaultReturnPolicy.getPrimaryKey());
                                                                    customerTypeDetailValue.setDefaultCustomerStatusPK(defaultCustomerStatus == null ? null : defaultCustomerStatus.getPrimaryKey());
                                                                    customerTypeDetailValue.setDefaultCustomerCreditStatusPK(defaultCustomerCreditStatus == null ? null : defaultCustomerCreditStatus.getPrimaryKey());
                                                                    customerTypeDetailValue.setDefaultArGlAccountPK(defaultArGlAccount == null ? null : defaultArGlAccount.getPrimaryKey());
                                                                    customerTypeDetailValue.setDefaultHoldUntilComplete(Boolean.valueOf(edit.getDefaultHoldUntilComplete()));
                                                                    customerTypeDetailValue.setDefaultAllowBackorders(Boolean.valueOf(edit.getDefaultAllowBackorders()));
                                                                    customerTypeDetailValue.setDefaultAllowSubstitutions(Boolean.valueOf(edit.getDefaultAllowSubstitutions()));
                                                                    customerTypeDetailValue.setDefaultAllowCombiningShipments(Boolean.valueOf(edit.getDefaultAllowCombiningShipments()));
                                                                    customerTypeDetailValue.setDefaultRequireReference(Boolean.valueOf(edit.getDefaultRequireReference()));
                                                                    customerTypeDetailValue.setDefaultAllowReferenceDuplicates(Boolean.valueOf(edit.getDefaultAllowReferenceDuplicates()));
                                                                    customerTypeDetailValue.setDefaultReferenceValidationPattern(edit.getDefaultReferenceValidationPattern());
                                                                    customerTypeDetailValue.setDefaultTaxable(Boolean.valueOf(edit.getDefaultTaxable()));
                                                                    customerTypeDetailValue.setAllocationPriorityPK(allocationPriority == null ? null : allocationPriority.getPrimaryKey());
                                                                    customerTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                                                                    customerTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

                                                                    customerControl.updateCustomerTypeFromValue(customerTypeDetailValue, partyPK);

                                                                    if(customerTypeDescription == null && description != null) {
                                                                        customerControl.createCustomerTypeDescription(customerType, getPreferredLanguage(), description, partyPK);
                                                                    } else if(customerTypeDescription != null && description == null) {
                                                                        customerControl.deleteCustomerTypeDescription(customerTypeDescription, partyPK);
                                                                    } else if(customerTypeDescription != null && description != null) {
                                                                        CustomerTypeDescriptionValue customerTypeDescriptionValue = customerControl.getCustomerTypeDescriptionValue(customerTypeDescription);

                                                                        customerTypeDescriptionValue.setDescription(description);
                                                                        customerControl.updateCustomerTypeDescriptionFromValue(customerTypeDescriptionValue, partyPK);
                                                                    }
                                                                } finally {
                                                                    unlockEntity(customerType);
                                                                }
                                                            } else {
                                                                addExecutionError(ExecutionErrors.EntityLockStale.name());
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
                            } else {
                                addExecutionError(ExecutionErrors.UnknownTermName.name(), defaultTermName);
                            }
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownCustomerSequenceName.name(), customerSequenceName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.DuplicateCustomerTypeName.name(), customerTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownCustomerTypeName.name(), customerTypeName);
            }
        }
        
        return result;
    }
    
}
