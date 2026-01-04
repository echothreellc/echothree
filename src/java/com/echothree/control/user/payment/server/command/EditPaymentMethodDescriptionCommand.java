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
import com.echothree.control.user.payment.common.edit.PaymentMethodDescriptionEdit;
import com.echothree.control.user.payment.common.form.EditPaymentMethodDescriptionForm;
import com.echothree.control.user.payment.common.result.EditPaymentMethodDescriptionResult;
import com.echothree.control.user.payment.common.result.PaymentResultFactory;
import com.echothree.control.user.payment.common.spec.PaymentMethodDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.payment.server.control.PaymentMethodControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.payment.server.entity.PaymentMethod;
import com.echothree.model.data.payment.server.entity.PaymentMethodDescription;
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
import javax.enterprise.context.Dependent;

@Dependent
public class EditPaymentMethodDescriptionCommand
        extends BaseAbstractEditCommand<PaymentMethodDescriptionSpec, PaymentMethodDescriptionEdit, EditPaymentMethodDescriptionResult, PaymentMethodDescription, PaymentMethod> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.PaymentMethod.name(), SecurityRoles.Description.name())
                    )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PaymentMethodName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditPaymentMethodDescriptionCommand */
    public EditPaymentMethodDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditPaymentMethodDescriptionResult getResult() {
        return PaymentResultFactory.getEditPaymentMethodDescriptionResult();
    }

    @Override
    public PaymentMethodDescriptionEdit getEdit() {
        return PaymentEditFactory.getPaymentMethodDescriptionEdit();
    }

    @Override
    public PaymentMethodDescription getEntity(EditPaymentMethodDescriptionResult result) {
        var paymentMethodControl = Session.getModelController(PaymentMethodControl.class);
        PaymentMethodDescription paymentMethodDescription = null;
        var paymentMethodName = spec.getPaymentMethodName();
        var paymentMethod = paymentMethodControl.getPaymentMethodByName(paymentMethodName);

        if(paymentMethod != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    paymentMethodDescription = paymentMethodControl.getPaymentMethodDescription(paymentMethod, language);
                } else { // EditMode.UPDATE
                    paymentMethodDescription = paymentMethodControl.getPaymentMethodDescriptionForUpdate(paymentMethod, language);
                }

                if(paymentMethodDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownPaymentMethodDescription.name(), paymentMethodName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPaymentMethodName.name(), paymentMethodName);
        }

        return paymentMethodDescription;
    }

    @Override
    public PaymentMethod getLockEntity(PaymentMethodDescription paymentMethodDescription) {
        return paymentMethodDescription.getPaymentMethod();
    }

    @Override
    public void fillInResult(EditPaymentMethodDescriptionResult result, PaymentMethodDescription paymentMethodDescription) {
        var paymentMethodControl = Session.getModelController(PaymentMethodControl.class);

        result.setPaymentMethodDescription(paymentMethodControl.getPaymentMethodDescriptionTransfer(getUserVisit(), paymentMethodDescription));
    }

    @Override
    public void doLock(PaymentMethodDescriptionEdit edit, PaymentMethodDescription paymentMethodDescription) {
        edit.setDescription(paymentMethodDescription.getDescription());
    }

    @Override
    public void doUpdate(PaymentMethodDescription paymentMethodDescription) {
        var paymentMethodControl = Session.getModelController(PaymentMethodControl.class);
        var paymentMethodDescriptionValue = paymentMethodControl.getPaymentMethodDescriptionValue(paymentMethodDescription);
        paymentMethodDescriptionValue.setDescription(edit.getDescription());

        paymentMethodControl.updatePaymentMethodDescriptionFromValue(paymentMethodDescriptionValue, getPartyPK());
    }

}
