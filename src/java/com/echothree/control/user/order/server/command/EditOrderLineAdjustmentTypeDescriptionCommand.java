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

package com.echothree.control.user.order.server.command;

import com.echothree.control.user.order.common.edit.OrderEditFactory;
import com.echothree.control.user.order.common.edit.OrderLineAdjustmentTypeDescriptionEdit;
import com.echothree.control.user.order.common.form.EditOrderLineAdjustmentTypeDescriptionForm;
import com.echothree.control.user.order.common.result.EditOrderLineAdjustmentTypeDescriptionResult;
import com.echothree.control.user.order.common.result.OrderResultFactory;
import com.echothree.control.user.order.common.spec.OrderLineAdjustmentTypeDescriptionSpec;
import com.echothree.model.control.order.server.control.OrderLineAdjustmentControl;
import com.echothree.model.control.order.server.control.OrderTypeControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.order.server.entity.OrderLineAdjustmentType;
import com.echothree.model.data.order.server.entity.OrderLineAdjustmentTypeDescription;
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
public class EditOrderLineAdjustmentTypeDescriptionCommand
        extends BaseAbstractEditCommand<OrderLineAdjustmentTypeDescriptionSpec, OrderLineAdjustmentTypeDescriptionEdit, EditOrderLineAdjustmentTypeDescriptionResult, OrderLineAdjustmentTypeDescription, OrderLineAdjustmentType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.OrderLineAdjustmentType.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OrderTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("OrderLineAdjustmentTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditOrderLineAdjustmentTypeDescriptionCommand */
    public EditOrderLineAdjustmentTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditOrderLineAdjustmentTypeDescriptionResult getResult() {
        return OrderResultFactory.getEditOrderLineAdjustmentTypeDescriptionResult();
    }

    @Override
    public OrderLineAdjustmentTypeDescriptionEdit getEdit() {
        return OrderEditFactory.getOrderLineAdjustmentTypeDescriptionEdit();
    }

    @Override
    public OrderLineAdjustmentTypeDescription getEntity(EditOrderLineAdjustmentTypeDescriptionResult result) {
        var orderTypeControl = Session.getModelController(OrderTypeControl.class);
        OrderLineAdjustmentTypeDescription orderLineAdjustmentTypeDescription = null;
        var orderTypeName = spec.getOrderTypeName();
        var orderType = orderTypeControl.getOrderTypeByName(orderTypeName);

        if(orderType != null) {
            var orderLineAdjustmentControl = Session.getModelController(OrderLineAdjustmentControl.class);
            var orderLineAdjustmentTypeName = spec.getOrderLineAdjustmentTypeName();
            var orderLineAdjustmentType = orderLineAdjustmentControl.getOrderLineAdjustmentTypeByName(orderType, orderLineAdjustmentTypeName);

            if(orderLineAdjustmentType != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                var languageIsoName = spec.getLanguageIsoName();
                var language = partyControl.getLanguageByIsoName(languageIsoName);

                if(language != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        orderLineAdjustmentTypeDescription = orderLineAdjustmentControl.getOrderLineAdjustmentTypeDescription(orderLineAdjustmentType, language);
                    } else { // EditMode.UPDATE
                        orderLineAdjustmentTypeDescription = orderLineAdjustmentControl.getOrderLineAdjustmentTypeDescriptionForUpdate(orderLineAdjustmentType, language);
                    }

                    if(orderLineAdjustmentTypeDescription == null) {
                        addExecutionError(ExecutionErrors.UnknownOrderLineAdjustmentTypeDescription.name(), orderTypeName, orderLineAdjustmentTypeName, languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownOrderLineAdjustmentTypeName.name(), orderTypeName, orderLineAdjustmentTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownOrderTypeName.name(), orderTypeName);
        }

        return orderLineAdjustmentTypeDescription;
    }

    @Override
    public OrderLineAdjustmentType getLockEntity(OrderLineAdjustmentTypeDescription orderLineAdjustmentTypeDescription) {
        return orderLineAdjustmentTypeDescription.getOrderLineAdjustmentType();
    }

    @Override
    public void fillInResult(EditOrderLineAdjustmentTypeDescriptionResult result, OrderLineAdjustmentTypeDescription orderLineAdjustmentTypeDescription) {
        var orderLineAdjustmentControl = Session.getModelController(OrderLineAdjustmentControl.class);

        result.setOrderLineAdjustmentTypeDescription(orderLineAdjustmentControl.getOrderLineAdjustmentTypeDescriptionTransfer(getUserVisit(), orderLineAdjustmentTypeDescription));
    }

    @Override
    public void doLock(OrderLineAdjustmentTypeDescriptionEdit edit, OrderLineAdjustmentTypeDescription orderLineAdjustmentTypeDescription) {
        edit.setDescription(orderLineAdjustmentTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(OrderLineAdjustmentTypeDescription orderLineAdjustmentTypeDescription) {
        var orderLineAdjustmentControl = Session.getModelController(OrderLineAdjustmentControl.class);
        var orderLineAdjustmentTypeDescriptionValue = orderLineAdjustmentControl.getOrderLineAdjustmentTypeDescriptionValue(orderLineAdjustmentTypeDescription);

        orderLineAdjustmentTypeDescriptionValue.setDescription(edit.getDescription());

        orderLineAdjustmentControl.updateOrderLineAdjustmentTypeDescriptionFromValue(orderLineAdjustmentTypeDescriptionValue, getPartyPK());
    }
    
}
