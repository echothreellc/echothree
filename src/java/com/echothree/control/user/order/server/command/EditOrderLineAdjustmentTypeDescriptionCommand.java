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

package com.echothree.control.user.order.server.command;

import com.echothree.control.user.order.common.edit.OrderEditFactory;
import com.echothree.control.user.order.common.edit.OrderLineAdjustmentTypeDescriptionEdit;
import com.echothree.control.user.order.common.form.EditOrderLineAdjustmentTypeDescriptionForm;
import com.echothree.control.user.order.common.result.EditOrderLineAdjustmentTypeDescriptionResult;
import com.echothree.control.user.order.common.result.OrderResultFactory;
import com.echothree.control.user.order.common.spec.OrderLineAdjustmentTypeDescriptionSpec;
import com.echothree.model.control.order.server.OrderControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.order.server.entity.OrderLineAdjustmentType;
import com.echothree.model.data.order.server.entity.OrderLineAdjustmentTypeDescription;
import com.echothree.model.data.order.server.entity.OrderType;
import com.echothree.model.data.order.server.value.OrderLineAdjustmentTypeDescriptionValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditOrderLineAdjustmentTypeDescriptionCommand
        extends BaseAbstractEditCommand<OrderLineAdjustmentTypeDescriptionSpec, OrderLineAdjustmentTypeDescriptionEdit, EditOrderLineAdjustmentTypeDescriptionResult, OrderLineAdjustmentTypeDescription, OrderLineAdjustmentType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.OrderLineAdjustmentType.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OrderTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("OrderLineAdjustmentTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditOrderLineAdjustmentTypeDescriptionCommand */
    public EditOrderLineAdjustmentTypeDescriptionCommand(UserVisitPK userVisitPK, EditOrderLineAdjustmentTypeDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
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
        OrderControl orderControl = (OrderControl)Session.getModelController(OrderControl.class);
        OrderLineAdjustmentTypeDescription orderLineAdjustmentTypeDescription = null;
        String orderTypeName = spec.getOrderTypeName();
        OrderType orderType = orderControl.getOrderTypeByName(orderTypeName);

        if(orderType != null) {
            String orderLineAdjustmentTypeName = spec.getOrderLineAdjustmentTypeName();
            OrderLineAdjustmentType orderLineAdjustmentType = orderControl.getOrderLineAdjustmentTypeByName(orderType, orderLineAdjustmentTypeName);

            if(orderLineAdjustmentType != null) {
                PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                String languageIsoName = spec.getLanguageIsoName();
                Language language = partyControl.getLanguageByIsoName(languageIsoName);

                if(language != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        orderLineAdjustmentTypeDescription = orderControl.getOrderLineAdjustmentTypeDescription(orderLineAdjustmentType, language);
                    } else { // EditMode.UPDATE
                        orderLineAdjustmentTypeDescription = orderControl.getOrderLineAdjustmentTypeDescriptionForUpdate(orderLineAdjustmentType, language);
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
        OrderControl orderControl = (OrderControl)Session.getModelController(OrderControl.class);

        result.setOrderLineAdjustmentTypeDescription(orderControl.getOrderLineAdjustmentTypeDescriptionTransfer(getUserVisit(), orderLineAdjustmentTypeDescription));
    }

    @Override
    public void doLock(OrderLineAdjustmentTypeDescriptionEdit edit, OrderLineAdjustmentTypeDescription orderLineAdjustmentTypeDescription) {
        edit.setDescription(orderLineAdjustmentTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(OrderLineAdjustmentTypeDescription orderLineAdjustmentTypeDescription) {
        OrderControl orderControl = (OrderControl)Session.getModelController(OrderControl.class);
        OrderLineAdjustmentTypeDescriptionValue orderLineAdjustmentTypeDescriptionValue = orderControl.getOrderLineAdjustmentTypeDescriptionValue(orderLineAdjustmentTypeDescription);
        orderLineAdjustmentTypeDescriptionValue.setDescription(edit.getDescription());

        orderControl.updateOrderLineAdjustmentTypeDescriptionFromValue(orderLineAdjustmentTypeDescriptionValue, getPartyPK());
    }
    
}
