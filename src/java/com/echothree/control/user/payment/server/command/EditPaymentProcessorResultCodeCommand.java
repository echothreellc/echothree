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

package com.echothree.control.user.payment.server.command;

import com.echothree.control.user.payment.common.edit.PaymentEditFactory;
import com.echothree.control.user.payment.common.edit.PaymentProcessorResultCodeEdit;
import com.echothree.control.user.payment.common.form.EditPaymentProcessorResultCodeForm;
import com.echothree.control.user.payment.common.result.PaymentResultFactory;
import com.echothree.control.user.payment.common.result.EditPaymentProcessorResultCodeResult;
import com.echothree.control.user.payment.common.spec.PaymentProcessorResultCodeUniversalSpec;
import com.echothree.model.control.payment.server.control.PaymentProcessorResultCodeControl;
import com.echothree.model.control.payment.server.logic.PaymentProcessorResultCodeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.payment.server.entity.PaymentProcessorResultCode;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class EditPaymentProcessorResultCodeCommand
        extends BaseAbstractEditCommand<PaymentProcessorResultCodeUniversalSpec, PaymentProcessorResultCodeEdit, EditPaymentProcessorResultCodeResult, PaymentProcessorResultCode, PaymentProcessorResultCode> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PaymentProcessorResultCode.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PaymentProcessorResultCodeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PaymentProcessorResultCodeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditPaymentProcessorResultCodeCommand */
    public EditPaymentProcessorResultCodeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditPaymentProcessorResultCodeResult getResult() {
        return PaymentResultFactory.getEditPaymentProcessorResultCodeResult();
    }
    
    @Override
    public PaymentProcessorResultCodeEdit getEdit() {
        return PaymentEditFactory.getPaymentProcessorResultCodeEdit();
    }
    
    @Override
    public PaymentProcessorResultCode getEntity(EditPaymentProcessorResultCodeResult result) {
        return PaymentProcessorResultCodeLogic.getInstance().getPaymentProcessorResultCodeByUniversalSpec(this, spec, false, editModeToEntityPermission(editMode));
    }
    
    @Override
    public PaymentProcessorResultCode getLockEntity(PaymentProcessorResultCode paymentProcessorResultCode) {
        return paymentProcessorResultCode;
    }
    
    @Override
    public void fillInResult(EditPaymentProcessorResultCodeResult result, PaymentProcessorResultCode paymentProcessorResultCode) {
        var paymentProcessorResultCodeControl = Session.getModelController(PaymentProcessorResultCodeControl.class);
        
        result.setPaymentProcessorResultCode(paymentProcessorResultCodeControl.getPaymentProcessorResultCodeTransfer(getUserVisit(), paymentProcessorResultCode));
    }
    
    @Override
    public void doLock(PaymentProcessorResultCodeEdit edit, PaymentProcessorResultCode paymentProcessorResultCode) {
        var paymentProcessorResultCodeControl = Session.getModelController(PaymentProcessorResultCodeControl.class);
        var paymentProcessorResultCodeDescription = paymentProcessorResultCodeControl.getPaymentProcessorResultCodeDescription(paymentProcessorResultCode, getPreferredLanguage());
        var paymentProcessorResultCodeDetail = paymentProcessorResultCode.getLastDetail();
        
        edit.setPaymentProcessorResultCodeName(paymentProcessorResultCodeDetail.getPaymentProcessorResultCodeName());
        edit.setIsDefault(paymentProcessorResultCodeDetail.getIsDefault().toString());
        edit.setSortOrder(paymentProcessorResultCodeDetail.getSortOrder().toString());

        if(paymentProcessorResultCodeDescription != null) {
            edit.setDescription(paymentProcessorResultCodeDescription.getDescription());
        }
    }
        
    @Override
    public void canUpdate(PaymentProcessorResultCode paymentProcessorResultCode) {
        var paymentProcessorResultCodeControl = Session.getModelController(PaymentProcessorResultCodeControl.class);
        var paymentProcessorResultCodeName = edit.getPaymentProcessorResultCodeName();
        var duplicatePaymentProcessorResultCode = paymentProcessorResultCodeControl.getPaymentProcessorResultCodeByName(paymentProcessorResultCodeName);

        if(duplicatePaymentProcessorResultCode != null && !paymentProcessorResultCode.equals(duplicatePaymentProcessorResultCode)) {
            addExecutionError(ExecutionErrors.DuplicatePaymentProcessorResultCodeName.name(), paymentProcessorResultCodeName);
        }
    }
    
    @Override
    public void doUpdate(PaymentProcessorResultCode paymentProcessorResultCode) {
        var paymentProcessorResultCodeControl = Session.getModelController(PaymentProcessorResultCodeControl.class);
        var partyPK = getPartyPK();
        var paymentProcessorResultCodeDetailValue = paymentProcessorResultCodeControl.getPaymentProcessorResultCodeDetailValueForUpdate(paymentProcessorResultCode);
        var paymentProcessorResultCodeDescription = paymentProcessorResultCodeControl.getPaymentProcessorResultCodeDescriptionForUpdate(paymentProcessorResultCode, getPreferredLanguage());
        var description = edit.getDescription();

        paymentProcessorResultCodeDetailValue.setPaymentProcessorResultCodeName(edit.getPaymentProcessorResultCodeName());
        paymentProcessorResultCodeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        paymentProcessorResultCodeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        paymentProcessorResultCodeControl.updatePaymentProcessorResultCodeFromValue(paymentProcessorResultCodeDetailValue, partyPK);

        if(paymentProcessorResultCodeDescription == null && description != null) {
            paymentProcessorResultCodeControl.createPaymentProcessorResultCodeDescription(paymentProcessorResultCode, getPreferredLanguage(), description, partyPK);
        } else if(paymentProcessorResultCodeDescription != null && description == null) {
            paymentProcessorResultCodeControl.deletePaymentProcessorResultCodeDescription(paymentProcessorResultCodeDescription, partyPK);
        } else if(paymentProcessorResultCodeDescription != null && description != null) {
            var paymentProcessorResultCodeDescriptionValue = paymentProcessorResultCodeControl.getPaymentProcessorResultCodeDescriptionValue(paymentProcessorResultCodeDescription);

            paymentProcessorResultCodeDescriptionValue.setDescription(description);
            paymentProcessorResultCodeControl.updatePaymentProcessorResultCodeDescriptionFromValue(paymentProcessorResultCodeDescriptionValue, partyPK);
        }
    }
    
}
