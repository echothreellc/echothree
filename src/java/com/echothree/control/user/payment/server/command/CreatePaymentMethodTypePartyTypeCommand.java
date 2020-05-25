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

import com.echothree.control.user.payment.common.form.CreatePaymentMethodTypePartyTypeForm;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.payment.server.control.PaymentControl;
import com.echothree.model.control.payment.server.control.PaymentMethodTypeControl;
import com.echothree.model.control.payment.server.logic.PaymentMethodTypeLogic;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.payment.server.entity.PaymentMethodType;
import com.echothree.model.data.payment.server.entity.PaymentMethodTypePartyType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreatePaymentMethodTypePartyTypeCommand
        extends BaseSimpleCommand<CreatePaymentMethodTypePartyTypeForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PaymentMethodTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PartyTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PartyPaymentMethodWorkflowName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ContactMechanismWorkflowName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of CreatePaymentMethodTypePartyTypeCommand */
    public CreatePaymentMethodTypePartyTypeCommand(UserVisitPK userVisitPK, CreatePaymentMethodTypePartyTypeForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        String paymentMethodTypeName = form.getPaymentMethodTypeName();
        PaymentMethodType paymentMethodType = PaymentMethodTypeLogic.getInstance().getPaymentMethodTypeByName(this, paymentMethodTypeName);
        
        if(!hasExecutionErrors()) {
            var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String partyTypeName = form.getPartyTypeName();
            PartyType partyType = partyControl.getPartyTypeByName(partyTypeName);
            
            if(partyType != null) {
                var paymentMethodTypeControl = (PaymentMethodTypeControl)Session.getModelController(PaymentMethodTypeControl.class);
                PaymentMethodTypePartyType paymentMethodTypePartyType = paymentMethodTypeControl.getPaymentMethodTypePartyType(paymentMethodType,
                        partyType);
                
                if(paymentMethodTypePartyType == null) {
                    var workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
                    String partyPaymentMethodWorkflowName = form.getPartyPaymentMethodWorkflowName();
                    Workflow partyPaymentMethodWorkflow = partyPaymentMethodWorkflowName == null? null: workflowControl.getWorkflowByName(partyPaymentMethodWorkflowName);
                    
                    if(partyPaymentMethodWorkflowName == null || partyPaymentMethodWorkflow != null) {
                        String contactMechanismWorkflowName = form.getContactMechanismWorkflowName();
                        Workflow contactMechanismWorkflow = contactMechanismWorkflowName == null? null: workflowControl.getWorkflowByName(contactMechanismWorkflowName);
                        
                        if(contactMechanismWorkflowName == null || contactMechanismWorkflow != null) {
                            paymentMethodTypeControl.createPaymentMethodTypePartyType(paymentMethodType, partyType, partyPaymentMethodWorkflow,
                                    contactMechanismWorkflow);
                        } else {
                            addExecutionError(ExecutionErrors.UnknownContactMechanismWorkflowName.name(), contactMechanismWorkflowName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownPartyPaymentMethodWorkflowName.name(), partyPaymentMethodWorkflowName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.DuplicatePaymentMethodTypePartyType.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownPartyTypeName.name(), partyTypeName);
            }
        }
        
        return null;
    }
    
}
