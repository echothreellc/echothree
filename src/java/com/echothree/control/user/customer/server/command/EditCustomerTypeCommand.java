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

import com.echothree.control.user.customer.common.edit.CustomerEditFactory;
import com.echothree.control.user.customer.common.edit.CustomerTypeEdit;
import com.echothree.control.user.customer.common.form.EditCustomerTypeForm;
import com.echothree.control.user.customer.common.result.CustomerResultFactory;
import com.echothree.control.user.customer.common.spec.CustomerTypeUniversalSpec;
import com.echothree.model.control.accounting.common.AccountingConstants;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.cancellationpolicy.common.CancellationKinds;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.control.customer.common.workflow.CustomerCreditStatusConstants;
import com.echothree.model.control.customer.common.workflow.CustomerStatusConstants;
import com.echothree.model.control.customer.server.control.CustomerControl;
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
import com.echothree.model.data.offer.server.entity.OfferUse;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditCustomerTypeCommand
        extends BaseEditCommand<CustomerTypeUniversalSpec, CustomerTypeEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.CustomerType.name(), SecurityRoles.Edit.name())
                ))
        ));
        
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("CustomerTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
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
    
    /** Creates a new instance of EditCustomerTypeCommand */
    public EditCustomerTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var customerControl = Session.getModelController(CustomerControl.class);
        var offerControl = Session.getModelController(OfferControl.class);
        var result = CustomerResultFactory.getEditCustomerTypeResult();
        
        if(editMode.equals(EditMode.LOCK)) {
            var customerType = CustomerTypeLogic.getInstance().getCustomerTypeByUniversalSpec(this, spec, false);

            if(!hasExecutionErrors()) {
                result.setCustomerType(customerControl.getCustomerTypeTransfer(getUserVisit(), customerType));
                
                if(lockEntity(customerType)) {
                    var sourceControl = Session.getModelController(SourceControl.class);
                    var customerTypeDescription = customerControl.getCustomerTypeDescription(customerType, getPreferredLanguage());
                    var edit = CustomerEditFactory.getCustomerTypeEdit();
                    var customerTypeDetail = customerType.getLastDetail();
                    var customerSequence = customerTypeDetail.getCustomerSequence();
                    var defaultOfferUse = customerTypeDetail.getDefaultOfferUse();
                    var defaultTerm = customerTypeDetail.getDefaultTerm();
                    var defaultFreeOnBoard = customerTypeDetail.getDefaultFreeOnBoard();
                    var defaultCancellationPolicy = customerTypeDetail.getDefaultCancellationPolicy();
                    var defaultReturnPolicy = customerTypeDetail.getDefaultReturnPolicy();
                    var defaultCustomerStatusChoice = customerTypeDetail.getDefaultCustomerStatus();
                    var defaultCustomerCreditStatusChoice = customerTypeDetail.getDefaultCustomerCreditStatus();
                    var defaultArGlAccount = customerTypeDetail.getDefaultArGlAccount();
                    var allocationPriority = customerTypeDetail.getAllocationPriority();

                    var sources = defaultOfferUse == null ? null : sourceControl.getSourcesByOfferUse(defaultOfferUse);
                    var sourcesIterator = sources == null ? null : sources.iterator();
                    var defaultSource = sourcesIterator == null ? null : sourcesIterator.hasNext() ? sourcesIterator.next() : null;

                    result.setEdit(edit);
                    edit.setCustomerTypeName(customerTypeDetail.getCustomerTypeName());
                    edit.setCustomerSequenceName(customerSequence == null ? null : customerSequence.getLastDetail().getSequenceName());
                    edit.setDefaultSourceName(defaultSource == null ? null : defaultSource.getLastDetail().getSourceName());
                    edit.setDefaultTermName(defaultTerm == null ? null : defaultTerm.getLastDetail().getTermName());
                    edit.setDefaultFreeOnBoardName(defaultFreeOnBoard == null ? null : defaultFreeOnBoard.getLastDetail().getFreeOnBoardName());
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
            }
        } else if(editMode.equals(EditMode.UPDATE)) {
            var customerType = CustomerTypeLogic.getInstance().getCustomerTypeByUniversalSpecForUpdate(this, spec, false);

            if(!hasExecutionErrors()) {
                var customerTypeName = edit.getCustomerTypeName();
                var duplicateCustomerType = customerControl.getCustomerTypeByName(customerTypeName);
                
                if(duplicateCustomerType == null || customerType.equals(duplicateCustomerType)) {
                    var customerSequenceName = edit.getCustomerSequenceName();
                    Sequence customerSequence = null;
                    
                    if(customerSequenceName != null) {
                        var sequenceControl = Session.getModelController(SequenceControl.class);
                        var sequenceType = sequenceControl.getSequenceTypeByName(SequenceTypes.CUSTOMER.name());
                        
                        if(sequenceType != null) {
                            customerSequence = sequenceControl.getSequenceByName(sequenceType, customerSequenceName);
                        } // TODO: unknown sequenceType, shouldn't happen
                    }
                    
                    if(customerSequenceName == null || customerSequence != null) {
                        var defaultOfferName = edit.getDefaultOfferName();
                        var defaultUseName = edit.getDefaultUseName();
                        var defaultSourceName = edit.getDefaultSourceName();
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
                            var defaultTermName = edit.getDefaultTermName();
                            var defaultFreeOnBoardName = edit.getDefaultFreeOnBoardName();
                            var defaultTerm = defaultTermName == null ? null : TermLogic.getInstance().getTermByName(this, defaultTermName);
                            var defaultFreeOnBoard = defaultFreeOnBoardName == null ? null : FreeOnBoardLogic.getInstance().getFreeOnBoardByName(this, defaultFreeOnBoardName);

                            if(!hasExecutionErrors()) {
                                var defaultCancellationPolicyName = edit.getDefaultCancellationPolicyName();
                                CancellationPolicy defaultCancellationPolicy = null;
                                
                                if(defaultCancellationPolicyName != null) {
                                    var cancellationPolicyControl = Session.getModelController(CancellationPolicyControl.class);
                                    var cancellationKind = cancellationPolicyControl.getCancellationKindByName(CancellationKinds.CUSTOMER_CANCELLATION.name());
                                    
                                    defaultCancellationPolicy = cancellationPolicyControl.getCancellationPolicyByName(cancellationKind, defaultCancellationPolicyName);
                                }
                                
                                if(defaultCancellationPolicyName == null || defaultCancellationPolicy != null) {
                                    var defaultReturnPolicyName = edit.getDefaultReturnPolicyName();
                                    ReturnPolicy defaultReturnPolicy = null;
                                    
                                    if(defaultReturnPolicyName != null) {
                                        var returnPolicyControl = Session.getModelController(ReturnPolicyControl.class);
                                        var returnKind = returnPolicyControl.getReturnKindByName(ReturnKinds.CUSTOMER_RETURN.name());
                                        
                                        defaultReturnPolicy = returnPolicyControl.getReturnPolicyByName(returnKind, defaultReturnPolicyName);
                                    }
                                    
                                    if(defaultReturnPolicyName == null || defaultReturnPolicy != null) {
                                        var workflowControl = Session.getModelController(WorkflowControl.class);
                                        var defaultCustomerStatusChoice = edit.getDefaultCustomerStatusChoice();
                                        WorkflowEntrance defaultCustomerStatus = null;
                                        
                                        if(defaultCustomerStatusChoice != null) {
                                            var workflow = workflowControl.getWorkflowByName(CustomerStatusConstants.Workflow_CUSTOMER_STATUS);
                                            defaultCustomerStatus = workflowControl.getWorkflowEntranceByName(workflow, defaultCustomerStatusChoice);
                                        }
                                        
                                        if(defaultCustomerStatusChoice == null || defaultCustomerStatus != null) {
                                            var defaultCustomerCreditStatusChoice = edit.getDefaultCustomerCreditStatusChoice();
                                            WorkflowEntrance defaultCustomerCreditStatus = null;
                                            
                                            if(defaultCustomerCreditStatusChoice != null) {
                                                var workflow = workflowControl.getWorkflowByName(CustomerCreditStatusConstants.Workflow_CUSTOMER_CREDIT_STATUS);
                                                defaultCustomerCreditStatus = workflowControl.getWorkflowEntranceByName(workflow, defaultCustomerCreditStatusChoice);
                                            }
                                            
                                            if(defaultCustomerCreditStatusChoice == null || defaultCustomerCreditStatus != null) {
                                                var accountingControl = Session.getModelController(AccountingControl.class);
                                                var defaultArGlAccountName = edit.getDefaultArGlAccountName();
                                                var defaultArGlAccount = defaultArGlAccountName == null ? null : accountingControl.getGlAccountByName(defaultArGlAccountName);

                                                if(defaultArGlAccountName == null || defaultArGlAccount != null) {
                                                    var glAccountCategoryName = defaultArGlAccount == null ? null
                                                            : defaultArGlAccount.getLastDetail().getGlAccountCategory().getLastDetail().getGlAccountCategoryName();

                                                    if(glAccountCategoryName == null || glAccountCategoryName.equals(AccountingConstants.GlAccountCategory_ACCOUNTS_RECEIVABLE)) {
                                                        var allocationPriorityName = edit.getAllocationPriorityName();
                                                        var allocationPriority = allocationPriorityName == null ? null : AllocationPriorityLogic.getInstance().getAllocationPriorityByName(this, allocationPriorityName);

                                                        if(!hasExecutionErrors()) {
                                                            if(lockEntityForUpdate(customerType)) {
                                                                try {
                                                                    var partyPK = getPartyPK();
                                                                    var customerTypeDetailValue = customerControl.getCustomerTypeDetailValueForUpdate(customerType);
                                                                    var customerTypeDescription = customerControl.getCustomerTypeDescriptionForUpdate(customerType, getPreferredLanguage());
                                                                    var description = edit.getDescription();

                                                                    customerTypeDetailValue.setCustomerTypeName(edit.getCustomerTypeName());
                                                                    customerTypeDetailValue.setCustomerSequencePK(customerSequence == null ? null : customerSequence.getPrimaryKey());
                                                                    customerTypeDetailValue.setDefaultOfferUsePK(defaultOfferUse == null ? null : defaultOfferUse.getPrimaryKey());
                                                                    customerTypeDetailValue.setDefaultTermPK(defaultTerm == null ? null : defaultTerm.getPrimaryKey());
                                                                    customerTypeDetailValue.setDefaultFreeOnBoardPK(defaultFreeOnBoard == null ? null : defaultFreeOnBoard.getPrimaryKey());
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

                                                                    CustomerTypeLogic.getInstance().updateCustomerTypeFromValue(this, customerTypeDetailValue, partyPK);

                                                                    if(customerTypeDescription == null && description != null) {
                                                                        customerControl.createCustomerTypeDescription(customerType, getPreferredLanguage(), description, partyPK);
                                                                    } else if(customerTypeDescription != null && description == null) {
                                                                        customerControl.deleteCustomerTypeDescription(customerTypeDescription, partyPK);
                                                                    } else if(customerTypeDescription != null && description != null) {
                                                                        var customerTypeDescriptionValue = customerControl.getCustomerTypeDescriptionValue(customerTypeDescription);

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
                            }
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownCustomerSequenceName.name(), customerSequenceName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.DuplicateCustomerTypeName.name(), customerTypeName);
                }
            }
        }
        
        return result;
    }
    
}
