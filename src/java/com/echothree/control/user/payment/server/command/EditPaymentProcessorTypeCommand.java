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

package com.echothree.control.user.payment.server.command;

import com.echothree.control.user.payment.common.edit.PaymentEditFactory;
import com.echothree.control.user.payment.common.edit.PaymentProcessorTypeEdit;
import com.echothree.control.user.payment.common.form.EditPaymentProcessorTypeForm;
import com.echothree.control.user.payment.common.result.PaymentResultFactory;
import com.echothree.control.user.payment.common.result.EditPaymentProcessorTypeResult;
import com.echothree.control.user.payment.common.spec.PaymentProcessorTypeUniversalSpec;
import com.echothree.model.control.payment.server.control.PaymentProcessorTypeControl;
import com.echothree.model.control.payment.server.logic.PaymentProcessorTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.payment.server.entity.PaymentProcessorType;
import com.echothree.model.data.payment.server.entity.PaymentProcessorTypeDescription;
import com.echothree.model.data.payment.server.entity.PaymentProcessorTypeDetail;
import com.echothree.model.data.payment.server.value.PaymentProcessorTypeDescriptionValue;
import com.echothree.model.data.payment.server.value.PaymentProcessorTypeDetailValue;
import com.echothree.model.data.party.common.pk.PartyPK;
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

public class EditPaymentProcessorTypeCommand
        extends BaseAbstractEditCommand<PaymentProcessorTypeUniversalSpec, PaymentProcessorTypeEdit, EditPaymentProcessorTypeResult, PaymentProcessorType, PaymentProcessorType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PaymentProcessorType.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PaymentProcessorTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Key", FieldType.KEY, false, null, null),
                new FieldDefinition("Guid", FieldType.GUID, false, null, null),
                new FieldDefinition("Ulid", FieldType.ULID, false, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PaymentProcessorTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditPaymentProcessorTypeCommand */
    public EditPaymentProcessorTypeCommand(UserVisitPK userVisitPK, EditPaymentProcessorTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditPaymentProcessorTypeResult getResult() {
        return PaymentResultFactory.getEditPaymentProcessorTypeResult();
    }
    
    @Override
    public PaymentProcessorTypeEdit getEdit() {
        return PaymentEditFactory.getPaymentProcessorTypeEdit();
    }
    
    @Override
    public PaymentProcessorType getEntity(EditPaymentProcessorTypeResult result) {
        return PaymentProcessorTypeLogic.getInstance().getPaymentProcessorTypeByUniversalSpec(this, spec, false, editModeToEntityPermission(editMode));
    }
    
    @Override
    public PaymentProcessorType getLockEntity(PaymentProcessorType paymentProcessorType) {
        return paymentProcessorType;
    }
    
    @Override
    public void fillInResult(EditPaymentProcessorTypeResult result, PaymentProcessorType paymentProcessorType) {
        var paymentProcessorTypeControl = Session.getModelController(PaymentProcessorTypeControl.class);
        
        result.setPaymentProcessorType(paymentProcessorTypeControl.getPaymentProcessorTypeTransfer(getUserVisit(), paymentProcessorType));
    }
    
    @Override
    public void doLock(PaymentProcessorTypeEdit edit, PaymentProcessorType paymentProcessorType) {
        var paymentProcessorTypeControl = Session.getModelController(PaymentProcessorTypeControl.class);
        PaymentProcessorTypeDescription paymentProcessorTypeDescription = paymentProcessorTypeControl.getPaymentProcessorTypeDescription(paymentProcessorType, getPreferredLanguage());
        PaymentProcessorTypeDetail paymentProcessorTypeDetail = paymentProcessorType.getLastDetail();
        
        edit.setPaymentProcessorTypeName(paymentProcessorTypeDetail.getPaymentProcessorTypeName());
        edit.setIsDefault(paymentProcessorTypeDetail.getIsDefault().toString());
        edit.setSortOrder(paymentProcessorTypeDetail.getSortOrder().toString());

        if(paymentProcessorTypeDescription != null) {
            edit.setDescription(paymentProcessorTypeDescription.getDescription());
        }
    }
        
    @Override
    public void canUpdate(PaymentProcessorType paymentProcessorType) {
        var paymentProcessorTypeControl = Session.getModelController(PaymentProcessorTypeControl.class);
        String paymentProcessorTypeName = edit.getPaymentProcessorTypeName();
        PaymentProcessorType duplicatePaymentProcessorType = paymentProcessorTypeControl.getPaymentProcessorTypeByName(paymentProcessorTypeName);

        if(duplicatePaymentProcessorType != null && !paymentProcessorType.equals(duplicatePaymentProcessorType)) {
            addExecutionError(ExecutionErrors.DuplicatePaymentProcessorTypeName.name(), paymentProcessorTypeName);
        }
    }
    
    @Override
    public void doUpdate(PaymentProcessorType paymentProcessorType) {
        var paymentProcessorTypeControl = Session.getModelController(PaymentProcessorTypeControl.class);
        var partyPK = getPartyPK();
        PaymentProcessorTypeDetailValue paymentProcessorTypeDetailValue = paymentProcessorTypeControl.getPaymentProcessorTypeDetailValueForUpdate(paymentProcessorType);
        PaymentProcessorTypeDescription paymentProcessorTypeDescription = paymentProcessorTypeControl.getPaymentProcessorTypeDescriptionForUpdate(paymentProcessorType, getPreferredLanguage());
        String description = edit.getDescription();

        paymentProcessorTypeDetailValue.setPaymentProcessorTypeName(edit.getPaymentProcessorTypeName());
        paymentProcessorTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        paymentProcessorTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        paymentProcessorTypeControl.updatePaymentProcessorTypeFromValue(paymentProcessorTypeDetailValue, partyPK);

        if(paymentProcessorTypeDescription == null && description != null) {
            paymentProcessorTypeControl.createPaymentProcessorTypeDescription(paymentProcessorType, getPreferredLanguage(), description, partyPK);
        } else if(paymentProcessorTypeDescription != null && description == null) {
            paymentProcessorTypeControl.deletePaymentProcessorTypeDescription(paymentProcessorTypeDescription, partyPK);
        } else if(paymentProcessorTypeDescription != null && description != null) {
            PaymentProcessorTypeDescriptionValue paymentProcessorTypeDescriptionValue = paymentProcessorTypeControl.getPaymentProcessorTypeDescriptionValue(paymentProcessorTypeDescription);

            paymentProcessorTypeDescriptionValue.setDescription(description);
            paymentProcessorTypeControl.updatePaymentProcessorTypeDescriptionFromValue(paymentProcessorTypeDescriptionValue, partyPK);
        }
    }
    
}
