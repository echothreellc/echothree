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

package com.echothree.control.user.vendor.server.command;

import com.echothree.control.user.vendor.common.form.CreateVendorTypeForm;
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
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateVendorTypeCommand
        extends BaseSimpleCommand<CreateVendorTypeForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.VendorType.name(), SecurityRoles.Create.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
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
    
    /** Creates a new instance of CreateVendorTypeCommand */
    public CreateVendorTypeCommand(UserVisitPK userVisitPK, CreateVendorTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var vendorControl = (VendorControl)Session.getModelController(VendorControl.class);
        String vendorTypeName = form.getVendorTypeName();
        VendorType vendorType = vendorControl.getVendorTypeByName(vendorTypeName);

        if(vendorType == null) {
            String defaultCancellationPolicyName = form.getDefaultCancellationPolicyName();
            CancellationPolicy defaultCancellationPolicy = null;

            if(defaultCancellationPolicyName != null) {
                var cancellationPolicyControl = (CancellationPolicyControl)Session.getModelController(CancellationPolicyControl.class);
                CancellationKind returnKind = cancellationPolicyControl.getCancellationKindByName(CancellationPolicyConstants.CancellationKind_CUSTOMER_CANCELLATION);

                defaultCancellationPolicy = cancellationPolicyControl.getCancellationPolicyByName(returnKind, defaultCancellationPolicyName);
            }

            if(defaultCancellationPolicyName == null || defaultCancellationPolicy != null) {
                String defaultReturnPolicyName = form.getDefaultReturnPolicyName();
                ReturnPolicy defaultReturnPolicy = null;

                if(defaultReturnPolicyName != null) {
                    var returnPolicyControl = (ReturnPolicyControl)Session.getModelController(ReturnPolicyControl.class);
                    ReturnKind returnKind = returnPolicyControl.getReturnKindByName(ReturnPolicyConstants.ReturnKind_CUSTOMER_RETURN);

                    defaultReturnPolicy = returnPolicyControl.getReturnPolicyByName(returnKind, defaultReturnPolicyName);
                }

                if(defaultReturnPolicyName == null || defaultReturnPolicy != null) {
                    var accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
                    String defaultApGlAccountName = form.getDefaultApGlAccountName();
                    GlAccount defaultApGlAccount = defaultApGlAccountName == null ? null : accountingControl.getGlAccountByName(defaultApGlAccountName);

                    if(defaultApGlAccountName == null || defaultApGlAccount != null) {
                        String glAccountCategoryName = defaultApGlAccount == null ? null : defaultApGlAccount.getLastDetail().getGlAccountCategory().getLastDetail().getGlAccountCategoryName();

                        if(glAccountCategoryName == null || glAccountCategoryName.equals(AccountingConstants.GlAccountCategory_ACCOUNTS_PAYABLE)) {
                            PartyPK partyPK = getPartyPK();
                            Boolean defaultHoldUntilComplete = Boolean.valueOf(form.getDefaultHoldUntilComplete());
                            Boolean defaultAllowBackorders = Boolean.valueOf(form.getDefaultAllowBackorders());
                            Boolean defaultAllowSubstitutions = Boolean.valueOf(form.getDefaultAllowSubstitutions());
                            Boolean defaultAllowCombiningShipments = Boolean.valueOf(form.getDefaultAllowCombiningShipments());
                            Boolean defaultRequireReference = Boolean.valueOf(form.getDefaultRequireReference());
                            Boolean defaultAllowReferenceDuplicates = Boolean.valueOf(form.getDefaultAllowReferenceDuplicates());
                            String defaultReferenceValidationPattern = form.getDefaultReferenceValidationPattern();
                            Boolean isDefault = Boolean.valueOf(form.getIsDefault());
                            Integer sortOrder = Integer.valueOf(form.getSortOrder());
                            String description = form.getDescription();

                            vendorType = vendorControl.createVendorType(vendorTypeName, defaultCancellationPolicy, defaultReturnPolicy, defaultApGlAccount,
                                    defaultHoldUntilComplete, defaultAllowBackorders, defaultAllowSubstitutions, defaultAllowCombiningShipments,
                                    defaultRequireReference, defaultAllowReferenceDuplicates, defaultReferenceValidationPattern, isDefault, sortOrder, partyPK);

                            if(description != null) {
                                vendorControl.createVendorTypeDescription(vendorType, getPreferredLanguage(), description, partyPK);
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

        return null;
    }
    
}
