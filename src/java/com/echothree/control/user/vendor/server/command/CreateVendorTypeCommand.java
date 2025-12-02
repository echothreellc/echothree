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

package com.echothree.control.user.vendor.server.command;

import com.echothree.control.user.vendor.common.form.CreateVendorTypeForm;
import com.echothree.control.user.vendor.common.result.VendorResultFactory;
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
import com.echothree.model.control.vendor.server.logic.VendorTypeLogic;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.vendor.server.entity.VendorType;
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
import javax.enterprise.context.Dependent;

@Dependent
public class CreateVendorTypeCommand
        extends BaseSimpleCommand<CreateVendorTypeForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.VendorType.name(), SecurityRoles.Create.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
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
    
    /** Creates a new instance of CreateVendorTypeCommand */
    public CreateVendorTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = VendorResultFactory.getCreateVendorTypeResult();
        VendorType vendorType = null;
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
                    var accountingControl = Session.getModelController(AccountingControl.class);
                    var defaultApGlAccountName = form.getDefaultApGlAccountName();
                    var defaultApGlAccount = defaultApGlAccountName == null ? null : accountingControl.getGlAccountByName(defaultApGlAccountName);

                    if(defaultApGlAccountName == null || defaultApGlAccount != null) {
                        var glAccountCategoryName = defaultApGlAccount == null ? null : defaultApGlAccount.getLastDetail().getGlAccountCategory().getLastDetail().getGlAccountCategoryName();

                        if(glAccountCategoryName == null || glAccountCategoryName.equals(AccountingConstants.GlAccountCategory_ACCOUNTS_PAYABLE)) {
                            var partyPK = getPartyPK();
                            var vendorTypeName = form.getVendorTypeName();
                            var defaultHoldUntilComplete = Boolean.valueOf(form.getDefaultHoldUntilComplete());
                            var defaultAllowBackorders = Boolean.valueOf(form.getDefaultAllowBackorders());
                            var defaultAllowSubstitutions = Boolean.valueOf(form.getDefaultAllowSubstitutions());
                            var defaultAllowCombiningShipments = Boolean.valueOf(form.getDefaultAllowCombiningShipments());
                            var defaultRequireReference = Boolean.valueOf(form.getDefaultRequireReference());
                            var defaultAllowReferenceDuplicates = Boolean.valueOf(form.getDefaultAllowReferenceDuplicates());
                            var defaultReferenceValidationPattern = form.getDefaultReferenceValidationPattern();
                            var isDefault = Boolean.valueOf(form.getIsDefault());
                            var sortOrder = Integer.valueOf(form.getSortOrder());
                            var description = form.getDescription();

                            vendorType = VendorTypeLogic.getInstance().createVendorType(this, vendorTypeName, defaultTerm, defaultFreeOnBoard,
                                    defaultCancellationPolicy, defaultReturnPolicy, defaultApGlAccount, defaultHoldUntilComplete,
                                    defaultAllowBackorders, defaultAllowSubstitutions, defaultAllowCombiningShipments,
                                    defaultRequireReference, defaultAllowReferenceDuplicates, defaultReferenceValidationPattern,
                                    isDefault, sortOrder, getPreferredLanguage(), description, partyPK);
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

        if(vendorType != null) {
            result.setEntityRef(vendorType.getPrimaryKey().getEntityRef());
            result.setVendorTypeName(vendorType.getLastDetail().getVendorTypeName());
        }

        return result;
    }
    
}
