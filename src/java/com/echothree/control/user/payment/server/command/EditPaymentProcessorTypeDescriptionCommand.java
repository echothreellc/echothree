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
import com.echothree.control.user.payment.common.edit.PaymentProcessorTypeDescriptionEdit;
import com.echothree.control.user.payment.common.result.EditPaymentProcessorTypeDescriptionResult;
import com.echothree.control.user.payment.common.result.PaymentResultFactory;
import com.echothree.control.user.payment.common.spec.PaymentProcessorTypeDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.control.payment.server.control.PaymentProcessorTypeControl;
import com.echothree.model.control.payment.server.logic.PaymentProcessorTypeLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.payment.server.entity.PaymentProcessorType;
import com.echothree.model.data.payment.server.entity.PaymentProcessorTypeDescription;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditPaymentProcessorTypeDescriptionCommand
        extends BaseAbstractEditCommand<PaymentProcessorTypeDescriptionSpec, PaymentProcessorTypeDescriptionEdit, EditPaymentProcessorTypeDescriptionResult, PaymentProcessorTypeDescription, PaymentProcessorType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.PaymentProcessorType.name(), SecurityRoles.Description.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PaymentProcessorTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    PaymentProcessorTypeControl paymentProcessorTypeControl;

    @Inject
    LanguageLogic languageLogic;

    @Inject
    PaymentProcessorTypeLogic paymentProcessorTypeLogic;

    /** Creates a new instance of EditPaymentProcessorTypeDescriptionCommand */
    public EditPaymentProcessorTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditPaymentProcessorTypeDescriptionResult getResult() {
        return PaymentResultFactory.getEditPaymentProcessorTypeDescriptionResult();
    }

    @Override
    public PaymentProcessorTypeDescriptionEdit getEdit() {
        return PaymentEditFactory.getPaymentProcessorTypeDescriptionEdit();
    }

    @Override
    public PaymentProcessorTypeDescription getEntity(EditPaymentProcessorTypeDescriptionResult result) {
        PaymentProcessorTypeDescription paymentProcessorTypeDescription = null;
        var paymentProcessorTypeName = spec.getPaymentProcessorTypeName();
        var languageIsoName = spec.getLanguageIsoName();
        var paymentProcessorType = paymentProcessorTypeLogic.getPaymentProcessorTypeByName(this, paymentProcessorTypeName);
        var language = languageLogic.getLanguageByName(this, languageIsoName);

        if(!hasExecutionErrors()) {
            paymentProcessorTypeDescription = paymentProcessorTypeControl.getPaymentProcessorTypeDescription(paymentProcessorType, language,
                    editModeToEntityPermission(editMode));

            if(paymentProcessorTypeDescription == null) {
                addExecutionError(ExecutionErrors.UnknownPaymentProcessorTypeDescription.name(), paymentProcessorTypeName, languageIsoName);
            }
        }

        return paymentProcessorTypeDescription;
    }

    @Override
    public PaymentProcessorType getLockEntity(PaymentProcessorTypeDescription paymentProcessorTypeDescription) {
        return paymentProcessorTypeDescription.getPaymentProcessorType();
    }

    @Override
    public void fillInResult(EditPaymentProcessorTypeDescriptionResult result, PaymentProcessorTypeDescription paymentProcessorTypeDescription) {
        result.setPaymentProcessorTypeDescription(paymentProcessorTypeControl.getPaymentProcessorTypeDescriptionTransfer(getUserVisit(), paymentProcessorTypeDescription));
    }

    @Override
    public void doLock(PaymentProcessorTypeDescriptionEdit edit, PaymentProcessorTypeDescription paymentProcessorTypeDescription) {
        edit.setDescription(paymentProcessorTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(PaymentProcessorTypeDescription paymentProcessorTypeDescription) {
        var paymentProcessorTypeDescriptionValue = paymentProcessorTypeControl.getPaymentProcessorTypeDescriptionValue(paymentProcessorTypeDescription);

        paymentProcessorTypeDescriptionValue.setDescription(edit.getDescription());

        paymentProcessorTypeControl.updatePaymentProcessorTypeDescriptionFromValue(paymentProcessorTypeDescriptionValue, getPartyPK());
    }

}
