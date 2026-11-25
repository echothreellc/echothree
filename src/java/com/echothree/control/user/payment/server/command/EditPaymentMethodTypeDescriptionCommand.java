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
import com.echothree.control.user.payment.common.edit.PaymentMethodTypeDescriptionEdit;
import com.echothree.control.user.payment.common.result.EditPaymentMethodTypeDescriptionResult;
import com.echothree.control.user.payment.common.result.PaymentResultFactory;
import com.echothree.control.user.payment.common.spec.PaymentMethodTypeDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.control.payment.server.control.PaymentMethodTypeControl;
import com.echothree.model.control.payment.server.logic.PaymentMethodTypeLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.payment.server.entity.PaymentMethodType;
import com.echothree.model.data.payment.server.entity.PaymentMethodTypeDescription;
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
public class EditPaymentMethodTypeDescriptionCommand
        extends BaseAbstractEditCommand<PaymentMethodTypeDescriptionSpec, PaymentMethodTypeDescriptionEdit, EditPaymentMethodTypeDescriptionResult, PaymentMethodTypeDescription, PaymentMethodType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.PaymentMethodType.name(), SecurityRoles.Description.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PaymentMethodTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    PaymentMethodTypeControl paymentMethodTypeControl;

    @Inject
    LanguageLogic languageLogic;

    @Inject
    PaymentMethodTypeLogic paymentMethodTypeLogic;

    /** Creates a new instance of EditPaymentMethodTypeDescriptionCommand */
    public EditPaymentMethodTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditPaymentMethodTypeDescriptionResult getResult() {
        return PaymentResultFactory.getEditPaymentMethodTypeDescriptionResult();
    }

    @Override
    public PaymentMethodTypeDescriptionEdit getEdit() {
        return PaymentEditFactory.getPaymentMethodTypeDescriptionEdit();
    }

    @Override
    public PaymentMethodTypeDescription getEntity(EditPaymentMethodTypeDescriptionResult result) {
        PaymentMethodTypeDescription paymentMethodTypeDescription = null;
        var paymentMethodTypeName = spec.getPaymentMethodTypeName();
        var languageIsoName = spec.getLanguageIsoName();
        var paymentMethodType = paymentMethodTypeLogic.getPaymentMethodTypeByName(this, paymentMethodTypeName);
        var language = languageLogic.getLanguageByName(this, languageIsoName);

        if(!hasExecutionErrors()) {
            paymentMethodTypeDescription = paymentMethodTypeControl.getPaymentMethodTypeDescription(paymentMethodType, language,
                    editModeToEntityPermission(editMode));

            if(paymentMethodTypeDescription == null) {
                addExecutionError(ExecutionErrors.UnknownPaymentMethodTypeDescription.name(), paymentMethodTypeName, languageIsoName);
            }
        }

        return paymentMethodTypeDescription;
    }

    @Override
    public PaymentMethodType getLockEntity(PaymentMethodTypeDescription paymentMethodTypeDescription) {
        return paymentMethodTypeDescription.getPaymentMethodType();
    }

    @Override
    public void fillInResult(EditPaymentMethodTypeDescriptionResult result, PaymentMethodTypeDescription paymentMethodTypeDescription) {
        result.setPaymentMethodTypeDescription(paymentMethodTypeControl.getPaymentMethodTypeDescriptionTransfer(getUserVisit(), paymentMethodTypeDescription));
    }

    @Override
    public void doLock(PaymentMethodTypeDescriptionEdit edit, PaymentMethodTypeDescription paymentMethodTypeDescription) {
        edit.setDescription(paymentMethodTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(PaymentMethodTypeDescription paymentMethodTypeDescription) {
        var paymentMethodTypeDescriptionValue = paymentMethodTypeControl.getPaymentMethodTypeDescriptionValue(paymentMethodTypeDescription);

        paymentMethodTypeDescriptionValue.setDescription(edit.getDescription());

        paymentMethodTypeControl.updatePaymentMethodTypeDescriptionFromValue(paymentMethodTypeDescriptionValue, getPartyPK());
    }

}
