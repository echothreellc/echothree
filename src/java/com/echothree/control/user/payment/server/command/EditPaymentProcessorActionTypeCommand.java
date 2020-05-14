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

package com.echothree.control.user.payment.server.command;

import com.echothree.control.user.payment.common.edit.PaymentEditFactory;
import com.echothree.control.user.payment.common.edit.PaymentProcessorActionTypeEdit;
import com.echothree.control.user.payment.common.form.EditPaymentProcessorActionTypeForm;
import com.echothree.control.user.payment.common.result.PaymentResultFactory;
import com.echothree.control.user.payment.common.result.EditPaymentProcessorActionTypeResult;
import com.echothree.control.user.payment.common.spec.PaymentProcessorActionTypeUniversalSpec;
import com.echothree.model.control.payment.server.control.PaymentProcessorActionTypeControl;
import com.echothree.model.control.payment.server.logic.PaymentProcessorActionTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.payment.server.entity.PaymentProcessorActionType;
import com.echothree.model.data.payment.server.entity.PaymentProcessorActionTypeDescription;
import com.echothree.model.data.payment.server.entity.PaymentProcessorActionTypeDetail;
import com.echothree.model.data.payment.server.value.PaymentProcessorActionTypeDescriptionValue;
import com.echothree.model.data.payment.server.value.PaymentProcessorActionTypeDetailValue;
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

public class EditPaymentProcessorActionTypeCommand
        extends BaseAbstractEditCommand<PaymentProcessorActionTypeUniversalSpec, PaymentProcessorActionTypeEdit, EditPaymentProcessorActionTypeResult, PaymentProcessorActionType, PaymentProcessorActionType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PaymentProcessorActionType.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PaymentProcessorActionTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Key", FieldType.KEY, false, null, null),
                new FieldDefinition("Guid", FieldType.GUID, false, null, null),
                new FieldDefinition("Ulid", FieldType.ULID, false, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PaymentProcessorActionTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditPaymentProcessorActionTypeCommand */
    public EditPaymentProcessorActionTypeCommand(UserVisitPK userVisitPK, EditPaymentProcessorActionTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditPaymentProcessorActionTypeResult getResult() {
        return PaymentResultFactory.getEditPaymentProcessorActionTypeResult();
    }
    
    @Override
    public PaymentProcessorActionTypeEdit getEdit() {
        return PaymentEditFactory.getPaymentProcessorActionTypeEdit();
    }
    
    @Override
    public PaymentProcessorActionType getEntity(EditPaymentProcessorActionTypeResult result) {
        return PaymentProcessorActionTypeLogic.getInstance().getPaymentProcessorActionTypeByUniversalSpec(this, spec, false, editModeToEntityPermission(editMode));
    }
    
    @Override
    public PaymentProcessorActionType getLockEntity(PaymentProcessorActionType paymentProcessorActionType) {
        return paymentProcessorActionType;
    }
    
    @Override
    public void fillInResult(EditPaymentProcessorActionTypeResult result, PaymentProcessorActionType paymentProcessorActionType) {
        var paymentProcessorActionTypeControl = (PaymentProcessorActionTypeControl)Session.getModelController(PaymentProcessorActionTypeControl.class);
        
        result.setPaymentProcessorActionType(paymentProcessorActionTypeControl.getPaymentProcessorActionTypeTransfer(getUserVisit(), paymentProcessorActionType));
    }
    
    @Override
    public void doLock(PaymentProcessorActionTypeEdit edit, PaymentProcessorActionType paymentProcessorActionType) {
        var paymentProcessorActionTypeControl = (PaymentProcessorActionTypeControl)Session.getModelController(PaymentProcessorActionTypeControl.class);
        PaymentProcessorActionTypeDescription paymentProcessorActionTypeDescription = paymentProcessorActionTypeControl.getPaymentProcessorActionTypeDescription(paymentProcessorActionType, getPreferredLanguage());
        PaymentProcessorActionTypeDetail paymentProcessorActionTypeDetail = paymentProcessorActionType.getLastDetail();
        
        edit.setPaymentProcessorActionTypeName(paymentProcessorActionTypeDetail.getPaymentProcessorActionTypeName());
        edit.setIsDefault(paymentProcessorActionTypeDetail.getIsDefault().toString());
        edit.setSortOrder(paymentProcessorActionTypeDetail.getSortOrder().toString());

        if(paymentProcessorActionTypeDescription != null) {
            edit.setDescription(paymentProcessorActionTypeDescription.getDescription());
        }
    }
        
    @Override
    public void canUpdate(PaymentProcessorActionType paymentProcessorActionType) {
        var paymentProcessorActionTypeControl = (PaymentProcessorActionTypeControl)Session.getModelController(PaymentProcessorActionTypeControl.class);
        String paymentProcessorActionTypeName = edit.getPaymentProcessorActionTypeName();
        PaymentProcessorActionType duplicatePaymentProcessorActionType = paymentProcessorActionTypeControl.getPaymentProcessorActionTypeByName(paymentProcessorActionTypeName);

        if(duplicatePaymentProcessorActionType != null && !paymentProcessorActionType.equals(duplicatePaymentProcessorActionType)) {
            addExecutionError(ExecutionErrors.DuplicatePaymentProcessorActionTypeName.name(), paymentProcessorActionTypeName);
        }
    }
    
    @Override
    public void doUpdate(PaymentProcessorActionType paymentProcessorActionType) {
        var paymentProcessorActionTypeControl = (PaymentProcessorActionTypeControl)Session.getModelController(PaymentProcessorActionTypeControl.class);
        PartyPK partyPK = getPartyPK();
        PaymentProcessorActionTypeDetailValue paymentProcessorActionTypeDetailValue = paymentProcessorActionTypeControl.getPaymentProcessorActionTypeDetailValueForUpdate(paymentProcessorActionType);
        PaymentProcessorActionTypeDescription paymentProcessorActionTypeDescription = paymentProcessorActionTypeControl.getPaymentProcessorActionTypeDescriptionForUpdate(paymentProcessorActionType, getPreferredLanguage());
        String description = edit.getDescription();

        paymentProcessorActionTypeDetailValue.setPaymentProcessorActionTypeName(edit.getPaymentProcessorActionTypeName());
        paymentProcessorActionTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        paymentProcessorActionTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        paymentProcessorActionTypeControl.updatePaymentProcessorActionTypeFromValue(paymentProcessorActionTypeDetailValue, partyPK);

        if(paymentProcessorActionTypeDescription == null && description != null) {
            paymentProcessorActionTypeControl.createPaymentProcessorActionTypeDescription(paymentProcessorActionType, getPreferredLanguage(), description, partyPK);
        } else if(paymentProcessorActionTypeDescription != null && description == null) {
            paymentProcessorActionTypeControl.deletePaymentProcessorActionTypeDescription(paymentProcessorActionTypeDescription, partyPK);
        } else if(paymentProcessorActionTypeDescription != null && description != null) {
            PaymentProcessorActionTypeDescriptionValue paymentProcessorActionTypeDescriptionValue = paymentProcessorActionTypeControl.getPaymentProcessorActionTypeDescriptionValue(paymentProcessorActionTypeDescription);

            paymentProcessorActionTypeDescriptionValue.setDescription(description);
            paymentProcessorActionTypeControl.updatePaymentProcessorActionTypeDescriptionFromValue(paymentProcessorActionTypeDescriptionValue, partyPK);
        }
    }
    
}
