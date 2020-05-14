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
import com.echothree.control.user.payment.common.edit.PaymentProcessorTypeDescriptionEdit;
import com.echothree.control.user.payment.common.form.EditPaymentProcessorTypeDescriptionForm;
import com.echothree.control.user.payment.common.result.PaymentResultFactory;
import com.echothree.control.user.payment.common.result.EditPaymentProcessorTypeDescriptionResult;
import com.echothree.control.user.payment.common.spec.PaymentProcessorTypeDescriptionSpec;
import com.echothree.model.control.payment.server.control.PaymentProcessorTypeControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.payment.server.entity.PaymentProcessorType;
import com.echothree.model.data.payment.server.entity.PaymentProcessorTypeDescription;
import com.echothree.model.data.payment.server.value.PaymentProcessorTypeDescriptionValue;
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

public class EditPaymentProcessorTypeDescriptionCommand
        extends BaseEditCommand<PaymentProcessorTypeDescriptionSpec, PaymentProcessorTypeDescriptionEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PaymentProcessorType.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PaymentProcessorTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditPaymentProcessorTypeDescriptionCommand */
    public EditPaymentProcessorTypeDescriptionCommand(UserVisitPK userVisitPK, EditPaymentProcessorTypeDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var paymentProcessorTypeControl = (PaymentProcessorTypeControl)Session.getModelController(PaymentProcessorTypeControl.class);
        EditPaymentProcessorTypeDescriptionResult result = PaymentResultFactory.getEditPaymentProcessorTypeDescriptionResult();
        String paymentProcessorTypeName = spec.getPaymentProcessorTypeName();
        PaymentProcessorType paymentProcessorType = paymentProcessorTypeControl.getPaymentProcessorTypeByName(paymentProcessorTypeName);
        
        if(paymentProcessorType != null) {
            var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String languageIsoName = spec.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);
            
            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    PaymentProcessorTypeDescription paymentProcessorTypeDescription = paymentProcessorTypeControl.getPaymentProcessorTypeDescription(paymentProcessorType, language);
                    
                    if(paymentProcessorTypeDescription != null) {
                        if(editMode.equals(EditMode.LOCK)) {
                            result.setPaymentProcessorTypeDescription(paymentProcessorTypeControl.getPaymentProcessorTypeDescriptionTransfer(getUserVisit(), paymentProcessorTypeDescription));

                            if(lockEntity(paymentProcessorType)) {
                                PaymentProcessorTypeDescriptionEdit edit = PaymentEditFactory.getPaymentProcessorTypeDescriptionEdit();

                                result.setEdit(edit);
                                edit.setDescription(paymentProcessorTypeDescription.getDescription());
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockFailed.name());
                            }

                            result.setEntityLock(getEntityLockTransfer(paymentProcessorType));
                        } else { // EditMode.ABANDON
                            unlockEntity(paymentProcessorType);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownPaymentProcessorTypeDescription.name());
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    PaymentProcessorTypeDescriptionValue paymentProcessorTypeDescriptionValue = paymentProcessorTypeControl.getPaymentProcessorTypeDescriptionValueForUpdate(paymentProcessorType, language);
                    
                    if(paymentProcessorTypeDescriptionValue != null) {
                        if(lockEntityForUpdate(paymentProcessorType)) {
                            try {
                                String description = edit.getDescription();
                                
                                paymentProcessorTypeDescriptionValue.setDescription(description);
                                
                                paymentProcessorTypeControl.updatePaymentProcessorTypeDescriptionFromValue(paymentProcessorTypeDescriptionValue, getPartyPK());
                            } finally {
                                unlockEntity(paymentProcessorType);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownPaymentProcessorTypeDescription.name());
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPaymentProcessorTypeName.name(), paymentProcessorTypeName);
        }
        
        return result;
    }
    
}
