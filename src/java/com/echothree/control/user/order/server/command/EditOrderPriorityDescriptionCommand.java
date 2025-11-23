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

package com.echothree.control.user.order.server.command;

import com.echothree.control.user.order.common.edit.OrderEditFactory;
import com.echothree.control.user.order.common.edit.OrderPriorityDescriptionEdit;
import com.echothree.control.user.order.common.form.EditOrderPriorityDescriptionForm;
import com.echothree.control.user.order.common.result.EditOrderPriorityDescriptionResult;
import com.echothree.control.user.order.common.result.OrderResultFactory;
import com.echothree.control.user.order.common.spec.OrderPriorityDescriptionSpec;
import com.echothree.model.control.order.server.control.OrderPriorityControl;
import com.echothree.model.control.order.server.control.OrderTypeControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.order.server.entity.OrderPriority;
import com.echothree.model.data.order.server.entity.OrderPriorityDescription;
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
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditOrderPriorityDescriptionCommand
        extends BaseAbstractEditCommand<OrderPriorityDescriptionSpec, OrderPriorityDescriptionEdit, EditOrderPriorityDescriptionResult, OrderPriorityDescription, OrderPriority> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.OrderPriority.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OrderTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("OrderPriorityName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditOrderPriorityDescriptionCommand */
    public EditOrderPriorityDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditOrderPriorityDescriptionResult getResult() {
        return OrderResultFactory.getEditOrderPriorityDescriptionResult();
    }

    @Override
    public OrderPriorityDescriptionEdit getEdit() {
        return OrderEditFactory.getOrderPriorityDescriptionEdit();
    }

    @Override
    public OrderPriorityDescription getEntity(EditOrderPriorityDescriptionResult result) {
        var orderTypeControl = Session.getModelController(OrderTypeControl.class);
        OrderPriorityDescription orderPriorityDescription = null;
        var orderTypeName = spec.getOrderTypeName();
        var orderType = orderTypeControl.getOrderTypeByName(orderTypeName);

        if(orderType != null) {
            var orderPriorityControl = Session.getModelController(OrderPriorityControl.class);
            var orderPriorityName = spec.getOrderPriorityName();
            var orderPriority = orderPriorityControl.getOrderPriorityByName(orderType, orderPriorityName);

            if(orderPriority != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                var languageIsoName = spec.getLanguageIsoName();
                var language = partyControl.getLanguageByIsoName(languageIsoName);

                if(language != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        orderPriorityDescription = orderPriorityControl.getOrderPriorityDescription(orderPriority, language);
                    } else { // EditMode.UPDATE
                        orderPriorityDescription = orderPriorityControl.getOrderPriorityDescriptionForUpdate(orderPriority, language);
                    }

                    if(orderPriorityDescription == null) {
                        addExecutionError(ExecutionErrors.UnknownOrderPriorityDescription.name(), orderTypeName, orderPriorityName, languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownOrderPriorityName.name(), orderTypeName, orderPriorityName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownOrderTypeName.name(), orderTypeName);
        }

        return orderPriorityDescription;
    }

    @Override
    public OrderPriority getLockEntity(OrderPriorityDescription orderPriorityDescription) {
        return orderPriorityDescription.getOrderPriority();
    }

    @Override
    public void fillInResult(EditOrderPriorityDescriptionResult result, OrderPriorityDescription orderPriorityDescription) {
        var orderPriorityControl = Session.getModelController(OrderPriorityControl.class);

        result.setOrderPriorityDescription(orderPriorityControl.getOrderPriorityDescriptionTransfer(getUserVisit(), orderPriorityDescription));
    }

    @Override
    public void doLock(OrderPriorityDescriptionEdit edit, OrderPriorityDescription orderPriorityDescription) {
        edit.setDescription(orderPriorityDescription.getDescription());
    }

    @Override
    public void doUpdate(OrderPriorityDescription orderPriorityDescription) {
        var orderPriorityControl = Session.getModelController(OrderPriorityControl.class);
        var orderPriorityDescriptionValue = orderPriorityControl.getOrderPriorityDescriptionValue(orderPriorityDescription);
        orderPriorityDescriptionValue.setDescription(edit.getDescription());

        orderPriorityControl.updateOrderPriorityDescriptionFromValue(orderPriorityDescriptionValue, getPartyPK());
    }
    
}
