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

package com.echothree.control.user.vendor.server.command;

import com.echothree.control.user.vendor.common.edit.VendorEditFactory;
import com.echothree.control.user.vendor.common.edit.VendorTypeEdit;
import com.echothree.control.user.vendor.common.form.EditVendorTypeForm;
import com.echothree.control.user.vendor.common.result.VendorResultFactory;
import com.echothree.control.user.vendor.common.spec.VendorTypeUniversalSpec;
import com.echothree.model.control.accounting.common.AccountingConstants;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.cancellationpolicy.common.CancellationKinds;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.returnpolicy.common.ReturnKinds;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.shipment.server.logic.FreeOnBoardLogic;
import com.echothree.model.control.term.server.logic.TermLogic;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.control.vendor.server.logic.VendorTypeLogic;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.user.common.pk.UserVisitPK;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class EditVendorTypeCommand
        extends BaseEditCommand<VendorTypeUniversalSpec, VendorTypeEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.VendorType.name(), SecurityRoles.Edit.name())
                    )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("VendorTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("VendorTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("DefaultTermName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DefaultFreeOnBoardName", FieldType.ENTITY_NAME, false, null, null),
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
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditVendorTypeCommand */
    public EditVendorTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var vendorControl = Session.getModelController(VendorControl.class);
        var result = VendorResultFactory.getEditVendorTypeResult();
        
        if(editMode.equals(EditMode.LOCK)) {
            var vendorType = VendorTypeLogic.getInstance().getVendorTypeByUniversalSpec(this, spec, false);
            
            if(!hasExecutionErrors()) {
                result.setVendorType(vendorControl.getVendorTypeTransfer(getUserVisit(), vendorType));
                
                if(lockEntity(vendorType)) {
                    var vendorTypeDescription = vendorControl.getVendorTypeDescription(vendorType, getPreferredLanguage());
                    var edit = VendorEditFactory.getVendorTypeEdit();
                    var vendorTypeDetail = vendorType.getLastDetail();
                    var defaultTerm = vendorTypeDetail.getDefaultTerm();
                    var defaultFreeOnBoard = vendorTypeDetail.getDefaultFreeOnBoard();
                    var defaultCancellationPolicy = vendorTypeDetail.getDefaultCancellationPolicy();
                    var defaultReturnPolicy = vendorTypeDetail.getDefaultReturnPolicy();
                    var defaultApGlAccount = vendorTypeDetail.getDefaultApGlAccount();
                    
                    result.setEdit(edit);
                    edit.setVendorTypeName(vendorTypeDetail.getVendorTypeName());
                    edit.setDefaultTermName(defaultTerm == null ? null : defaultTerm.getLastDetail().getTermName());
                    edit.setDefaultFreeOnBoardName(defaultFreeOnBoard == null ? null : defaultFreeOnBoard.getLastDetail().getFreeOnBoardName());
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
            }
        } else if(editMode.equals(EditMode.UPDATE)) {
            var vendorType = VendorTypeLogic.getInstance().getVendorTypeByUniversalSpecForUpdate(this, spec, false);
            
            if(!hasExecutionErrors()) {
                var vendorTypeName = edit.getVendorTypeName();
                var duplicateVendorType = vendorControl.getVendorTypeByName(vendorTypeName);
                
                if(duplicateVendorType == null || vendorType.equals(duplicateVendorType)) {
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
                                var accountingControl = Session.getModelController(AccountingControl.class);
                                var defaultApGlAccountName = edit.getDefaultApGlAccountName();
                                var defaultApGlAccount = defaultApGlAccountName == null ? null : accountingControl.getGlAccountByName(defaultApGlAccountName);

                                if(defaultApGlAccountName == null || defaultApGlAccount != null) {
                                    var glAccountCategoryName = defaultApGlAccount == null ? null : defaultApGlAccount.getLastDetail().getGlAccountCategory().getLastDetail().getGlAccountCategoryName();

                                    if(glAccountCategoryName == null || glAccountCategoryName.equals(AccountingConstants.GlAccountCategory_ACCOUNTS_PAYABLE)) {
                                        if(lockEntityForUpdate(vendorType)) {
                                            try {
                                                var partyPK = getPartyPK();
                                                var vendorTypeDetailValue = vendorControl.getVendorTypeDetailValueForUpdate(vendorType);
                                                var vendorTypeDescription = vendorControl.getVendorTypeDescriptionForUpdate(vendorType, getPreferredLanguage());
                                                var description = edit.getDescription();

                                                vendorTypeDetailValue.setVendorTypeName(edit.getVendorTypeName());
                                                vendorTypeDetailValue.setDefaultTermPK(defaultTerm == null ? null : defaultTerm.getPrimaryKey());
                                                vendorTypeDetailValue.setDefaultFreeOnBoardPK(defaultFreeOnBoard == null ? null : defaultFreeOnBoard.getPrimaryKey());
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

                                                VendorTypeLogic.getInstance().updateVendorTypeFromValue(this, vendorTypeDetailValue, partyPK);

                                                if(vendorTypeDescription == null && description != null) {
                                                    vendorControl.createVendorTypeDescription(vendorType, getPreferredLanguage(), description, partyPK);
                                                } else {
                                                    if(vendorTypeDescription != null && description == null) {
                                                        vendorControl.deleteVendorTypeDescription(vendorTypeDescription, partyPK);
                                                    } else {
                                                        if(vendorTypeDescription != null && description != null) {
                                                            var vendorTypeDescriptionValue = vendorControl.getVendorTypeDescriptionValue(vendorTypeDescription);

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
                    }
                } else {
                    addExecutionError(ExecutionErrors.DuplicateVendorTypeName.name(), vendorTypeName);
                }
            }
            
            if(hasExecutionErrors()) {
                result.setVendorType(vendorControl.getVendorTypeTransfer(getUserVisit(), vendorType));
                result.setEntityLock(getEntityLockTransfer(vendorType));
            }
        }
        
        return result;
    }
    
}
