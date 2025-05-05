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
import com.echothree.control.user.payment.common.edit.PaymentProcessorDescriptionEdit;
import com.echothree.control.user.payment.common.form.EditPaymentProcessorDescriptionForm;
import com.echothree.control.user.payment.common.result.EditPaymentProcessorDescriptionResult;
import com.echothree.control.user.payment.common.result.PaymentResultFactory;
import com.echothree.control.user.payment.common.spec.PaymentProcessorDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.payment.server.control.PaymentProcessorControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.payment.server.entity.PaymentProcessor;
import com.echothree.model.data.payment.server.entity.PaymentProcessorDescription;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.EditMode;
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

public class EditPaymentProcessorDescriptionCommand
        extends BaseAbstractEditCommand<PaymentProcessorDescriptionSpec, PaymentProcessorDescriptionEdit, EditPaymentProcessorDescriptionResult, PaymentProcessorDescription, PaymentProcessor> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.PaymentProcessor.name(), SecurityRoles.Description.name())
                    )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PaymentProcessorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditPaymentProcessorDescriptionCommand */
    public EditPaymentProcessorDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditPaymentProcessorDescriptionResult getResult() {
        return PaymentResultFactory.getEditPaymentProcessorDescriptionResult();
    }

    @Override
    public PaymentProcessorDescriptionEdit getEdit() {
        return PaymentEditFactory.getPaymentProcessorDescriptionEdit();
    }

    @Override
    public PaymentProcessorDescription getEntity(EditPaymentProcessorDescriptionResult result) {
        var paymentProcessorControl = Session.getModelController(PaymentProcessorControl.class);
        PaymentProcessorDescription paymentProcessorDescription = null;
        var paymentProcessorName = spec.getPaymentProcessorName();
        var paymentProcessor = paymentProcessorControl.getPaymentProcessorByName(paymentProcessorName);

        if(paymentProcessor != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    paymentProcessorDescription = paymentProcessorControl.getPaymentProcessorDescription(paymentProcessor, language);
                } else { // EditMode.UPDATE
                    paymentProcessorDescription = paymentProcessorControl.getPaymentProcessorDescriptionForUpdate(paymentProcessor, language);
                }

                if(paymentProcessorDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownPaymentProcessorDescription.name(), paymentProcessorName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPaymentProcessorName.name(), paymentProcessorName);
        }

        return paymentProcessorDescription;
    }

    @Override
    public PaymentProcessor getLockEntity(PaymentProcessorDescription paymentProcessorDescription) {
        return paymentProcessorDescription.getPaymentProcessor();
    }

    @Override
    public void fillInResult(EditPaymentProcessorDescriptionResult result, PaymentProcessorDescription paymentProcessorDescription) {
        var paymentProcessorControl = Session.getModelController(PaymentProcessorControl.class);

        result.setPaymentProcessorDescription(paymentProcessorControl.getPaymentProcessorDescriptionTransfer(getUserVisit(), paymentProcessorDescription));
    }

    @Override
    public void doLock(PaymentProcessorDescriptionEdit edit, PaymentProcessorDescription paymentProcessorDescription) {
        edit.setDescription(paymentProcessorDescription.getDescription());
    }

    @Override
    public void doUpdate(PaymentProcessorDescription paymentProcessorDescription) {
        var paymentProcessorControl = Session.getModelController(PaymentProcessorControl.class);
        var paymentProcessorDescriptionValue = paymentProcessorControl.getPaymentProcessorDescriptionValue(paymentProcessorDescription);
        paymentProcessorDescriptionValue.setDescription(edit.getDescription());

        paymentProcessorControl.updatePaymentProcessorDescriptionFromValue(paymentProcessorDescriptionValue, getPartyPK());
    }

}
