// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

import com.echothree.control.user.payment.common.form.CreateBillingAccountRoleTypeDescriptionForm;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.payment.server.PaymentControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.payment.server.entity.BillingAccountRoleType;
import com.echothree.model.data.payment.server.entity.BillingAccountRoleTypeDescription;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateBillingAccountRoleTypeDescriptionCommand
        extends BaseSimpleCommand<CreateBillingAccountRoleTypeDescriptionForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null)
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("BillingAccountRoleTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of CreateBillingAccountRoleTypeDescriptionCommand */
    public CreateBillingAccountRoleTypeDescriptionCommand(UserVisitPK userVisitPK, CreateBillingAccountRoleTypeDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        PaymentControl paymentControl = (PaymentControl)Session.getModelController(PaymentControl.class);
        String billingAccountRoleTypeName = form.getBillingAccountRoleTypeName();
        BillingAccountRoleType billingAccountRoleType = paymentControl.getBillingAccountRoleTypeByName(billingAccountRoleTypeName);
        
        if(billingAccountRoleType != null) {
            PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String languageIsoName = form.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);
            
            if(language != null) {
                BillingAccountRoleTypeDescription billingAccountRoleTypeDescription = paymentControl.getBillingAccountRoleTypeDescription(billingAccountRoleType, language);
                
                if(billingAccountRoleTypeDescription == null) {
                    String description = form.getDescription();
                    
                    paymentControl.createBillingAccountRoleTypeDescription(billingAccountRoleType, language, description);
                } else {
                    addExecutionError(ExecutionErrors.DuplicateBillingAccountRoleTypeDescription.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownBillingAccountRoleTypeName.name(), billingAccountRoleTypeName);
        }
        
        return null;
    }
    
}
