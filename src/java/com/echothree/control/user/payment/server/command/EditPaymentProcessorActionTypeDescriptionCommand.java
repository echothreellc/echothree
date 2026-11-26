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

package com.echothree.control.user.payment.server.command;

import com.echothree.control.user.payment.common.edit.PaymentEditFactory;
import com.echothree.control.user.payment.common.edit.PaymentProcessorActionTypeDescriptionEdit;
import com.echothree.control.user.payment.common.result.EditPaymentProcessorActionTypeDescriptionResult;
import com.echothree.control.user.payment.common.result.PaymentResultFactory;
import com.echothree.control.user.payment.common.spec.PaymentProcessorActionTypeDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.control.payment.server.control.PaymentProcessorActionTypeControl;
import com.echothree.model.control.payment.server.logic.PaymentProcessorActionTypeLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.payment.server.entity.PaymentProcessorActionType;
import com.echothree.model.data.payment.server.entity.PaymentProcessorActionTypeDescription;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditPaymentProcessorActionTypeDescriptionCommand
        extends BaseEditCommand<PaymentProcessorActionTypeDescriptionSpec, PaymentProcessorActionTypeDescriptionEdit, EditPaymentProcessorActionTypeDescriptionResult, PaymentProcessorActionTypeDescription, PaymentProcessorActionType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.PaymentProcessorActionType.name(), SecurityRoles.Description.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PaymentProcessorActionTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    PaymentProcessorActionTypeControl paymentProcessorActionTypeControl;

    @Inject
    LanguageLogic languageLogic;

    @Inject
    PaymentProcessorActionTypeLogic paymentProcessorActionTypeLogic;

    /** Creates a new instance of EditPaymentProcessorActionTypeDescriptionCommand */
    public EditPaymentProcessorActionTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditPaymentProcessorActionTypeDescriptionResult getResult() {
        return PaymentResultFactory.getEditPaymentProcessorActionTypeDescriptionResult();
    }

    @Override
    public PaymentProcessorActionTypeDescriptionEdit getEdit() {
        return PaymentEditFactory.getPaymentProcessorActionTypeDescriptionEdit();
    }

    @Override
    public PaymentProcessorActionTypeDescription getEntity(EditPaymentProcessorActionTypeDescriptionResult result) {
        PaymentProcessorActionTypeDescription paymentProcessorActionTypeDescription = null;
        var paymentProcessorActionTypeName = spec.getPaymentProcessorActionTypeName();
        var languageIsoName = spec.getLanguageIsoName();
        var paymentProcessorActionType = paymentProcessorActionTypeLogic.getPaymentProcessorActionTypeByName(this, paymentProcessorActionTypeName);
        var language = languageLogic.getLanguageByName(this, languageIsoName);

        if(!hasExecutionErrors()) {
            paymentProcessorActionTypeDescription = paymentProcessorActionTypeControl.getPaymentProcessorActionTypeDescription(paymentProcessorActionType, language,
                    editModeToEntityPermission(editMode));

            if(paymentProcessorActionTypeDescription == null) {
                addExecutionError(ExecutionErrors.UnknownPaymentProcessorActionTypeDescription.name(), paymentProcessorActionTypeName, languageIsoName);
            }
        }

        return paymentProcessorActionTypeDescription;
    }

    @Override
    public PaymentProcessorActionType getLockEntity(PaymentProcessorActionTypeDescription paymentProcessorActionTypeDescription) {
        return paymentProcessorActionTypeDescription.getPaymentProcessorActionType();
    }

    @Override
    public void fillInResult(EditPaymentProcessorActionTypeDescriptionResult result, PaymentProcessorActionTypeDescription paymentProcessorActionTypeDescription) {
        result.setPaymentProcessorActionTypeDescription(paymentProcessorActionTypeControl.getPaymentProcessorActionTypeDescriptionTransfer(getUserVisit(), paymentProcessorActionTypeDescription));
    }

    @Override
    public void doLock(PaymentProcessorActionTypeDescriptionEdit edit, PaymentProcessorActionTypeDescription paymentProcessorActionTypeDescription) {
        edit.setDescription(paymentProcessorActionTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(PaymentProcessorActionTypeDescription paymentProcessorActionTypeDescription) {
        var paymentProcessorActionTypeDescriptionValue = paymentProcessorActionTypeControl.getPaymentProcessorActionTypeDescriptionValue(paymentProcessorActionTypeDescription);

        paymentProcessorActionTypeDescriptionValue.setDescription(edit.getDescription());

        paymentProcessorActionTypeControl.updatePaymentProcessorActionTypeDescriptionFromValue(paymentProcessorActionTypeDescriptionValue, getPartyPK());
    }

}
