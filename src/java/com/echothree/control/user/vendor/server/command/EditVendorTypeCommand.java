// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.control.user.vendor.server.command;

import com.echothree.control.user.vendor.common.edit.VendorEditFactory;
import com.echothree.control.user.vendor.common.edit.VendorTypeEdit;
import com.echothree.control.user.vendor.common.form.EditVendorTypeForm;
import com.echothree.control.user.vendor.common.result.EditVendorTypeResult;
import com.echothree.control.user.vendor.common.result.VendorResultFactory;
import com.echothree.control.user.vendor.common.spec.VendorTypeSpec;
import com.echothree.model.control.accounting.common.AccountingConstants;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.cancellationpolicy.common.CancellationPolicyConstants;
import com.echothree.model.control.cancellationpolicy.server.CancellationPolicyControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.returnpolicy.common.ReturnPolicyConstants;
import com.echothree.model.control.returnpolicy.server.ReturnPolicyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.vendor.server.VendorControl;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationKind;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.returnpolicy.server.entity.ReturnKind;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.vendor.server.entity.VendorType;
import com.echothree.model.data.vendor.server.entity.VendorTypeDescription;
import com.echothree.model.data.vendor.server.entity.VendorTypeDetail;
import com.echothree.model.data.vendor.server.value.VendorTypeDescriptionValue;
import com.echothree.model.data.vendor.server.value.VendorTypeDetailValue;
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
import java.util.Collections;
import java.util.List;

public class EditVendorTypeCommand
        extends BaseEditCommand<VendorTypeSpec, VendorTypeEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.VendorType.name(), SecurityRoles.Edit.name())
                    )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("VendorTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("VendorTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("DefaultCancellationPolicyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DefaultReturnPolicyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DefaultApGlAccountName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DefaultHoldUntilComplete", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("DefaultAllowBackorders", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("DefaultAllowSubstitutions", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("DefaultAllowCombiningShipments", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("DefaultRequireReference", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("DefaultAllowReferenceDuplicates", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("DefaultReferenceValidationPattern", FieldType.REGULAR_EXPRESSION, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditVendorTypeCommand */
    public EditVendorTypeCommand(UserVisitPK userVisitPK, EditVendorTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        VendorControl vendorControl = (VendorControl)Session.getModelController(VendorControl.class);
        EditVendorTypeResult result = VendorResultFactory.getEditVendorTypeResult();
        
        if(editMode.equals(EditMode.LOCK)) {
            String vendorTypeName = spec.getVendorTypeName();
            VendorType vendorType = vendorControl.getVendorTypeByName(vendorTypeName);
            
            if(vendorType != null) {
                result.setVendorType(vendorControl.getVendorTypeTransfer(getUserVisit(), vendorType));
                
                if(lockEntity(vendorType)) {
                    VendorTypeDescription vendorTypeDescription = vendorControl.getVendorTypeDescription(vendorType, getPreferredLanguage());
                    VendorTypeEdit edit = VendorEditFactory.getVendorTypeEdit();
                    VendorTypeDetail vendorTypeDetail = vendorType.getLastDetail();
                    CancellationPolicy defaultCancellationPolicy = vendorTypeDetail.getDefaultCancellationPolicy();
                    ReturnPolicy defaultReturnPolicy = vendorTypeDetail.getDefaultReturnPolicy();
                    GlAccount defaultApGlAccount = vendorTypeDetail.getDefaultApGlAccount();
                    
                    result.setEdit(edit);
                    edit.setVendorTypeName(vendorTypeDetail.getVendorTypeName());
                    edit.setDefaultCancellationPolicyName(defaultCancellationPolicy == null? null: defaultCancellationPolicy.getLastDetail().getCancellationPolicyName());
                    edit.setDefaultReturnPolicyName(defaultReturnPolicy == null? null: defaultReturnPolicy.getLastDetail().getReturnPolicyName());
                    edit.setDefaultApGlAccountName(defaultApGlAccount == null? null: defaultApGlAccount.getLastDetail().getGlAccountName());
                    edit.setDefaultHoldUntilComplete(vendorTypeDetail.getDefaultHoldUntilComplete().toString());
                    edit.setDefaultAllowBackorders(vendorTypeDetail.getDefaultAllowBackorders().toString());
                    edit.setDefaultAllowSubstitutions(vendorTypeDetail.getDefaultAllowSubstitutions().toString());
                    edit.setDefaultAllowCombiningShipments(vendorTypeDetail.getDefaultAllowCombiningShipments().toString());
                    edit.setDefaultRequireReference(vendorTypeDetail.getDefaultRequireReference().toString());
                    edit.setDefaultAllowReferenceDuplicates(vendorTypeDetail.getDefaultAllowReferenceDuplicates().toString());
                    edit.setDefaultReferenceValidationPattern(vendorTypeDetail.getDefaultReferenceValidationPattern());
                    edit.setIsDefault(vendorTypeDetail.getIsDefault().toString());
                    edit.setSortOrder(vendorTypeDetail.getSortOrder().toString());
                    
                    if(vendorTypeDescription != null)
                        edit.setDescription(vendorTypeDescription.getDescription());
                } else {
                    addExecutionError(ExecutionErrors.EntityLockFailed.name());
                }
                
                result.setEntityLock(getEntityLockTransfer(vendorType));
            } else {
                addExecutionError(ExecutionErrors.UnknownVendorTypeName.name(), vendorTypeName);
            }
        } else if(editMode.equals(EditMode.UPDATE)) {
            String vendorTypeName = spec.getVendorTypeName();
            VendorType vendorType = vendorControl.getVendorTypeByNameForUpdate(vendorTypeName);
            
            if(vendorType != null) {
                vendorTypeName = edit.getVendorTypeName();
                VendorType duplicateVendorType = vendorControl.getVendorTypeByName(vendorTypeName);
                
                if(duplicateVendorType == null || vendorType.equals(duplicateVendorType)) {
                    String defaultCancellationPolicyName = edit.getDefaultCancellationPolicyName();
                    CancellationPolicy defaultCancellationPolicy = null;

                    if(defaultCancellationPolicyName != null) {
                        CancellationPolicyControl cancellationPolicyControl = (CancellationPolicyControl)Session.getModelController(CancellationPolicyControl.class);
                        CancellationKind cancellationKind = cancellationPolicyControl.getCancellationKindByName(CancellationPolicyConstants.CancellationKind_CUSTOMER_CANCELLATION);

                        defaultCancellationPolicy = cancellationPolicyControl.getCancellationPolicyByName(cancellationKind, defaultCancellationPolicyName);
                    }

                    if(defaultCancellationPolicyName == null || defaultCancellationPolicy != null) {
                        String defaultReturnPolicyName = edit.getDefaultReturnPolicyName();
                        ReturnPolicy defaultReturnPolicy = null;

                        if(defaultReturnPolicyName != null) {
                            ReturnPolicyControl returnPolicyControl = (ReturnPolicyControl)Session.getModelController(ReturnPolicyControl.class);
                            ReturnKind returnKind = returnPolicyControl.getReturnKindByName(ReturnPolicyConstants.ReturnKind_CUSTOMER_RETURN);

                            defaultReturnPolicy = returnPolicyControl.getReturnPolicyByName(returnKind, defaultReturnPolicyName);
                        }

                        if(defaultReturnPolicyName == null || defaultReturnPolicy != null) {
                            AccountingControl accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
                            String defaultApGlAccountName = edit.getDefaultApGlAccountName();
                            GlAccount defaultApGlAccount = defaultApGlAccountName == null ? null : accountingControl.getGlAccountByName(defaultApGlAccountName);

                            if(defaultApGlAccountName == null || defaultApGlAccount != null) {
                                String glAccountCategoryName = defaultApGlAccount == null ? null : defaultApGlAccount.getLastDetail().getGlAccountCategory().getLastDetail().getGlAccountCategoryName();

                                if(glAccountCategoryName == null || glAccountCategoryName.equals(AccountingConstants.GlAccountCategory_ACCOUNTS_PAYABLE)) {
                                    if(lockEntityForUpdate(vendorType)) {
                                        try {
                                            PartyPK partyPK = getPartyPK();
                                            VendorTypeDetailValue vendorTypeDetailValue = vendorControl.getVendorTypeDetailValueForUpdate(vendorType);
                                            VendorTypeDescription vendorTypeDescription = vendorControl.getVendorTypeDescriptionForUpdate(vendorType, getPreferredLanguage());
                                            String description = edit.getDescription();

                                            vendorTypeDetailValue.setVendorTypeName(edit.getVendorTypeName());
                                            vendorTypeDetailValue.setDefaultCancellationPolicyPK(defaultCancellationPolicy == null ? null : defaultCancellationPolicy.getPrimaryKey());
                                            vendorTypeDetailValue.setDefaultReturnPolicyPK(defaultReturnPolicy == null ? null : defaultReturnPolicy.getPrimaryKey());
                                            vendorTypeDetailValue.setDefaultApGlAccountPK(defaultApGlAccount == null ? null : defaultApGlAccount.getPrimaryKey());
                                            vendorTypeDetailValue.setDefaultHoldUntilComplete(Boolean.valueOf(edit.getDefaultHoldUntilComplete()));
                                            vendorTypeDetailValue.setDefaultAllowBackorders(Boolean.valueOf(edit.getDefaultAllowBackorders()));
                                            vendorTypeDetailValue.setDefaultAllowSubstitutions(Boolean.valueOf(edit.getDefaultAllowSubstitutions()));
                                            vendorTypeDetailValue.setDefaultAllowCombiningShipments(Boolean.valueOf(edit.getDefaultAllowCombiningShipments()));
                                            vendorTypeDetailValue.setDefaultRequireReference(Boolean.valueOf(edit.getDefaultRequireReference()));
                                            vendorTypeDetailValue.setDefaultAllowReferenceDuplicates(Boolean.valueOf(edit.getDefaultAllowReferenceDuplicates()));
                                            vendorTypeDetailValue.setDefaultReferenceValidationPattern(edit.getDefaultReferenceValidationPattern());
                                            vendorTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                                            vendorTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

                                            vendorControl.updateVendorTypeFromValue(vendorTypeDetailValue, partyPK);

                                            if(vendorTypeDescription == null && description != null) {
                                                vendorControl.createVendorTypeDescription(vendorType, getPreferredLanguage(), description, partyPK);
                                            } else {
                                                if(vendorTypeDescription != null && description == null) {
                                                    vendorControl.deleteVendorTypeDescription(vendorTypeDescription, partyPK);
                                                } else {
                                                    if(vendorTypeDescription != null && description != null) {
                                                        VendorTypeDescriptionValue vendorTypeDescriptionValue = vendorControl.getVendorTypeDescriptionValue(vendorTypeDescription);

                                                        vendorTypeDescriptionValue.setDescription(description);
                                                        vendorControl.updateVendorTypeDescriptionFromValue(vendorTypeDescriptionValue, partyPK);
                                                    }
                                                }
                                            }
                                        } finally {
                                            unlockEntity(vendorType);
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.EntityLockStale.name());
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.InvalidGlAccountCategory.name(), glAccountCategoryName);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownDefaultApGlAccountName.name(), defaultApGlAccountName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownReturnPolicyName.name(), defaultReturnPolicyName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownCancellationPolicyName.name(), defaultCancellationPolicyName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.DuplicateVendorTypeName.name(), vendorTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownVendorTypeName.name(), vendorTypeName);
            }
            
            if(hasExecutionErrors()) {
                result.setVendorType(vendorControl.getVendorTypeTransfer(getUserVisit(), vendorType));
                result.setEntityLock(getEntityLockTransfer(vendorType));
            }
        }
        
        return result;
    }
    
}
