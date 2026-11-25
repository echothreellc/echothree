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
import com.echothree.control.user.payment.common.edit.PaymentProcessorResultCodeDescriptionEdit;
import com.echothree.control.user.payment.common.result.EditPaymentProcessorResultCodeDescriptionResult;
import com.echothree.control.user.payment.common.result.PaymentResultFactory;
import com.echothree.control.user.payment.common.spec.PaymentProcessorResultCodeDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.control.payment.server.control.PaymentProcessorResultCodeControl;
import com.echothree.model.control.payment.server.logic.PaymentProcessorResultCodeLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.payment.server.entity.PaymentProcessorResultCode;
import com.echothree.model.data.payment.server.entity.PaymentProcessorResultCodeDescription;
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
public class EditPaymentProcessorResultCodeDescriptionCommand
        extends BaseAbstractEditCommand<PaymentProcessorResultCodeDescriptionSpec, PaymentProcessorResultCodeDescriptionEdit, EditPaymentProcessorResultCodeDescriptionResult, PaymentProcessorResultCodeDescription, PaymentProcessorResultCode> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.PaymentProcessorResultCode.name(), SecurityRoles.Description.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PaymentProcessorResultCodeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    PaymentProcessorResultCodeControl paymentProcessorResultCodeControl;

    @Inject
    LanguageLogic languageLogic;

    @Inject
    PaymentProcessorResultCodeLogic paymentProcessorResultCodeLogic;

    /** Creates a new instance of EditPaymentProcessorResultCodeDescriptionCommand */
    public EditPaymentProcessorResultCodeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditPaymentProcessorResultCodeDescriptionResult getResult() {
        return PaymentResultFactory.getEditPaymentProcessorResultCodeDescriptionResult();
    }

    @Override
    public PaymentProcessorResultCodeDescriptionEdit getEdit() {
        return PaymentEditFactory.getPaymentProcessorResultCodeDescriptionEdit();
    }

    @Override
    public PaymentProcessorResultCodeDescription getEntity(EditPaymentProcessorResultCodeDescriptionResult result) {
        PaymentProcessorResultCodeDescription paymentProcessorResultCodeDescription = null;
        var paymentProcessorResultCodeName = spec.getPaymentProcessorResultCodeName();
        var languageIsoName = spec.getLanguageIsoName();
        var paymentProcessorResultCode = paymentProcessorResultCodeLogic.getPaymentProcessorResultCodeByName(this, paymentProcessorResultCodeName);
        var language = languageLogic.getLanguageByName(this, languageIsoName);

        if(!hasExecutionErrors()) {
            paymentProcessorResultCodeDescription = paymentProcessorResultCodeControl.getPaymentProcessorResultCodeDescription(paymentProcessorResultCode, language,
                    editModeToEntityPermission(editMode));

            if(paymentProcessorResultCodeDescription == null) {
                addExecutionError(ExecutionErrors.UnknownPaymentProcessorResultCodeDescription.name(), paymentProcessorResultCodeName, languageIsoName);
            }
        }

        return paymentProcessorResultCodeDescription;
    }

    @Override
    public PaymentProcessorResultCode getLockEntity(PaymentProcessorResultCodeDescription paymentProcessorResultCodeDescription) {
        return paymentProcessorResultCodeDescription.getPaymentProcessorResultCode();
    }

    @Override
    public void fillInResult(EditPaymentProcessorResultCodeDescriptionResult result, PaymentProcessorResultCodeDescription paymentProcessorResultCodeDescription) {
        result.setPaymentProcessorResultCodeDescription(paymentProcessorResultCodeControl.getPaymentProcessorResultCodeDescriptionTransfer(getUserVisit(), paymentProcessorResultCodeDescription));
    }

    @Override
    public void doLock(PaymentProcessorResultCodeDescriptionEdit edit, PaymentProcessorResultCodeDescription paymentProcessorResultCodeDescription) {
        edit.setDescription(paymentProcessorResultCodeDescription.getDescription());
    }

    @Override
    public void doUpdate(PaymentProcessorResultCodeDescription paymentProcessorResultCodeDescription) {
        var paymentProcessorResultCodeDescriptionValue = paymentProcessorResultCodeControl.getPaymentProcessorResultCodeDescriptionValue(paymentProcessorResultCodeDescription);

        paymentProcessorResultCodeDescriptionValue.setDescription(edit.getDescription());

        paymentProcessorResultCodeControl.updatePaymentProcessorResultCodeDescriptionFromValue(paymentProcessorResultCodeDescriptionValue, getPartyPK());
    }

}
