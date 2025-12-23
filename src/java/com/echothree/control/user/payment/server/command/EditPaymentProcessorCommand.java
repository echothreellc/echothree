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
import com.echothree.control.user.payment.common.edit.PaymentProcessorEdit;
import com.echothree.control.user.payment.common.form.EditPaymentProcessorForm;
import com.echothree.control.user.payment.common.result.EditPaymentProcessorResult;
import com.echothree.control.user.payment.common.result.PaymentResultFactory;
import com.echothree.control.user.payment.common.spec.PaymentProcessorSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.payment.server.control.PaymentProcessorControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.payment.server.entity.PaymentProcessor;
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
public class EditPaymentProcessorCommand
        extends BaseAbstractEditCommand<PaymentProcessorSpec, PaymentProcessorEdit, EditPaymentProcessorResult, PaymentProcessor, PaymentProcessor> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.PaymentProcessor.name(), SecurityRoles.Edit.name())
                    )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PaymentProcessorName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PaymentProcessorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditPaymentProcessorCommand */
    public EditPaymentProcessorCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditPaymentProcessorResult getResult() {
        return PaymentResultFactory.getEditPaymentProcessorResult();
    }

    @Override
    public PaymentProcessorEdit getEdit() {
        return PaymentEditFactory.getPaymentProcessorEdit();
    }

    @Override
    public PaymentProcessor getEntity(EditPaymentProcessorResult result) {
        var paymentProcessorControl = Session.getModelController(PaymentProcessorControl.class);
        PaymentProcessor paymentProcessor;
        var paymentProcessorName = spec.getPaymentProcessorName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            paymentProcessor = paymentProcessorControl.getPaymentProcessorByName(paymentProcessorName);
        } else { // EditMode.UPDATE
            paymentProcessor = paymentProcessorControl.getPaymentProcessorByNameForUpdate(paymentProcessorName);
        }

        if(paymentProcessor != null) {
            result.setPaymentProcessor(paymentProcessorControl.getPaymentProcessorTransfer(getUserVisit(), paymentProcessor));
        } else {
            addExecutionError(ExecutionErrors.UnknownPaymentProcessorName.name(), paymentProcessorName);
        }

        return paymentProcessor;
    }

    @Override
    public PaymentProcessor getLockEntity(PaymentProcessor paymentProcessor) {
        return paymentProcessor;
    }

    @Override
    public void fillInResult(EditPaymentProcessorResult result, PaymentProcessor paymentProcessor) {
        var paymentProcessorControl = Session.getModelController(PaymentProcessorControl.class);

        result.setPaymentProcessor(paymentProcessorControl.getPaymentProcessorTransfer(getUserVisit(), paymentProcessor));
    }

    @Override
    public void doLock(PaymentProcessorEdit edit, PaymentProcessor paymentProcessor) {
        var paymentProcessorControl = Session.getModelController(PaymentProcessorControl.class);
        var paymentProcessorDescription = paymentProcessorControl.getPaymentProcessorDescription(paymentProcessor, getPreferredLanguage());
        var paymentProcessorDetail = paymentProcessor.getLastDetail();

        edit.setPaymentProcessorName(paymentProcessorDetail.getPaymentProcessorName());
        edit.setIsDefault(paymentProcessorDetail.getIsDefault().toString());
        edit.setSortOrder(paymentProcessorDetail.getSortOrder().toString());

        if(paymentProcessorDescription != null) {
            edit.setDescription(paymentProcessorDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(PaymentProcessor paymentProcessor) {
        var paymentProcessorControl = Session.getModelController(PaymentProcessorControl.class);
        var paymentProcessorName = edit.getPaymentProcessorName();
        var duplicatePaymentProcessor = paymentProcessorControl.getPaymentProcessorByName(paymentProcessorName);

        if(duplicatePaymentProcessor != null && !paymentProcessor.equals(duplicatePaymentProcessor)) {
            addExecutionError(ExecutionErrors.DuplicatePaymentProcessorName.name(), paymentProcessorName);
        }
    }

    @Override
    public void doUpdate(PaymentProcessor paymentProcessor) {
        var paymentProcessorControl = Session.getModelController(PaymentProcessorControl.class);
        var partyPK = getPartyPK();
        var paymentProcessorDetailValue = paymentProcessorControl.getPaymentProcessorDetailValueForUpdate(paymentProcessor);
        var paymentProcessorDescription = paymentProcessorControl.getPaymentProcessorDescriptionForUpdate(paymentProcessor, getPreferredLanguage());
        var description = edit.getDescription();

        paymentProcessorDetailValue.setPaymentProcessorName(edit.getPaymentProcessorName());
        paymentProcessorDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        paymentProcessorDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        paymentProcessorControl.updatePaymentProcessorFromValue(paymentProcessorDetailValue, partyPK);

        if(paymentProcessorDescription == null && description != null) {
            paymentProcessorControl.createPaymentProcessorDescription(paymentProcessor, getPreferredLanguage(), description, partyPK);
        } else if(paymentProcessorDescription != null && description == null) {
            paymentProcessorControl.deletePaymentProcessorDescription(paymentProcessorDescription, partyPK);
        } else if(paymentProcessorDescription != null && description != null) {
            var paymentProcessorDescriptionValue = paymentProcessorControl.getPaymentProcessorDescriptionValue(paymentProcessorDescription);

            paymentProcessorDescriptionValue.setDescription(description);
            paymentProcessorControl.updatePaymentProcessorDescriptionFromValue(paymentProcessorDescriptionValue, partyPK);
        }
    }

}
