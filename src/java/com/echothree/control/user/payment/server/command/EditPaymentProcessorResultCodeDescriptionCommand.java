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
import com.echothree.control.user.payment.common.edit.PaymentProcessorResultCodeDescriptionEdit;
import com.echothree.control.user.payment.common.form.EditPaymentProcessorResultCodeDescriptionForm;
import com.echothree.control.user.payment.common.result.PaymentResultFactory;
import com.echothree.control.user.payment.common.result.EditPaymentProcessorResultCodeDescriptionResult;
import com.echothree.control.user.payment.common.spec.PaymentProcessorResultCodeDescriptionSpec;
import com.echothree.model.control.payment.server.PaymentProcessorResultCodeControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.payment.server.entity.PaymentProcessorResultCode;
import com.echothree.model.data.payment.server.entity.PaymentProcessorResultCodeDescription;
import com.echothree.model.data.payment.server.value.PaymentProcessorResultCodeDescriptionValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditPaymentProcessorResultCodeDescriptionCommand
        extends BaseEditCommand<PaymentProcessorResultCodeDescriptionSpec, PaymentProcessorResultCodeDescriptionEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PaymentProcessorResultCode.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PaymentProcessorResultCodeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditPaymentProcessorResultCodeDescriptionCommand */
    public EditPaymentProcessorResultCodeDescriptionCommand(UserVisitPK userVisitPK, EditPaymentProcessorResultCodeDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var paymentProcessorResultCodeControl = (PaymentProcessorResultCodeControl)Session.getModelController(PaymentProcessorResultCodeControl.class);
        EditPaymentProcessorResultCodeDescriptionResult result = PaymentResultFactory.getEditPaymentProcessorResultCodeDescriptionResult();
        String paymentProcessorResultCodeName = spec.getPaymentProcessorResultCodeName();
        PaymentProcessorResultCode paymentProcessorResultCode = paymentProcessorResultCodeControl.getPaymentProcessorResultCodeByName(paymentProcessorResultCodeName);
        
        if(paymentProcessorResultCode != null) {
            var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String languageIsoName = spec.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);
            
            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    PaymentProcessorResultCodeDescription paymentProcessorResultCodeDescription = paymentProcessorResultCodeControl.getPaymentProcessorResultCodeDescription(paymentProcessorResultCode, language);
                    
                    if(paymentProcessorResultCodeDescription != null) {
                        if(editMode.equals(EditMode.LOCK)) {
                            result.setPaymentProcessorResultCodeDescription(paymentProcessorResultCodeControl.getPaymentProcessorResultCodeDescriptionTransfer(getUserVisit(), paymentProcessorResultCodeDescription));

                            if(lockEntity(paymentProcessorResultCode)) {
                                PaymentProcessorResultCodeDescriptionEdit edit = PaymentEditFactory.getPaymentProcessorResultCodeDescriptionEdit();

                                result.setEdit(edit);
                                edit.setDescription(paymentProcessorResultCodeDescription.getDescription());
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockFailed.name());
                            }

                            result.setEntityLock(getEntityLockTransfer(paymentProcessorResultCode));
                        } else { // EditMode.ABANDON
                            unlockEntity(paymentProcessorResultCode);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownPaymentProcessorResultCodeDescription.name());
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    PaymentProcessorResultCodeDescriptionValue paymentProcessorResultCodeDescriptionValue = paymentProcessorResultCodeControl.getPaymentProcessorResultCodeDescriptionValueForUpdate(paymentProcessorResultCode, language);
                    
                    if(paymentProcessorResultCodeDescriptionValue != null) {
                        if(lockEntityForUpdate(paymentProcessorResultCode)) {
                            try {
                                String description = edit.getDescription();
                                
                                paymentProcessorResultCodeDescriptionValue.setDescription(description);
                                
                                paymentProcessorResultCodeControl.updatePaymentProcessorResultCodeDescriptionFromValue(paymentProcessorResultCodeDescriptionValue, getPartyPK());
                            } finally {
                                unlockEntity(paymentProcessorResultCode);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownPaymentProcessorResultCodeDescription.name());
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPaymentProcessorResultCodeName.name(), paymentProcessorResultCodeName);
        }
        
        return result;
    }
    
}
