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

import com.echothree.control.user.order.common.edit.OrderAdjustmentTypeDescriptionEdit;
import com.echothree.control.user.order.common.edit.OrderEditFactory;
import com.echothree.control.user.order.common.form.EditOrderAdjustmentTypeDescriptionForm;
import com.echothree.control.user.order.common.result.EditOrderAdjustmentTypeDescriptionResult;
import com.echothree.control.user.order.common.result.OrderResultFactory;
import com.echothree.control.user.order.common.spec.OrderAdjustmentTypeDescriptionSpec;
import com.echothree.model.control.order.server.control.OrderAdjustmentControl;
import com.echothree.model.control.order.server.control.OrderTypeControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.order.server.entity.OrderAdjustmentType;
import com.echothree.model.data.order.server.entity.OrderAdjustmentTypeDescription;
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
public class EditOrderAdjustmentTypeDescriptionCommand
        extends BaseAbstractEditCommand<OrderAdjustmentTypeDescriptionSpec, OrderAdjustmentTypeDescriptionEdit, EditOrderAdjustmentTypeDescriptionResult, OrderAdjustmentTypeDescription, OrderAdjustmentType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.OrderAdjustmentType.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OrderTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("OrderAdjustmentTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditOrderAdjustmentTypeDescriptionCommand */
    public EditOrderAdjustmentTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditOrderAdjustmentTypeDescriptionResult getResult() {
        return OrderResultFactory.getEditOrderAdjustmentTypeDescriptionResult();
    }

    @Override
    public OrderAdjustmentTypeDescriptionEdit getEdit() {
        return OrderEditFactory.getOrderAdjustmentTypeDescriptionEdit();
    }

    @Override
    public OrderAdjustmentTypeDescription getEntity(EditOrderAdjustmentTypeDescriptionResult result) {
        var orderTypeControl = Session.getModelController(OrderTypeControl.class);
        OrderAdjustmentTypeDescription orderAdjustmentTypeDescription = null;
        var orderTypeName = spec.getOrderTypeName();
        var orderType = orderTypeControl.getOrderTypeByName(orderTypeName);

        if(orderType != null) {
            var orderAdjustmentControl = Session.getModelController(OrderAdjustmentControl.class);
            var orderAdjustmentTypeName = spec.getOrderAdjustmentTypeName();
            var orderAdjustmentType = orderAdjustmentControl.getOrderAdjustmentTypeByName(orderType, orderAdjustmentTypeName);

            if(orderAdjustmentType != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                var languageIsoName = spec.getLanguageIsoName();
                var language = partyControl.getLanguageByIsoName(languageIsoName);

                if(language != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        orderAdjustmentTypeDescription = orderAdjustmentControl.getOrderAdjustmentTypeDescription(orderAdjustmentType, language);
                    } else { // EditMode.UPDATE
                        orderAdjustmentTypeDescription = orderAdjustmentControl.getOrderAdjustmentTypeDescriptionForUpdate(orderAdjustmentType, language);
                    }

                    if(orderAdjustmentTypeDescription == null) {
                        addExecutionError(ExecutionErrors.UnknownOrderAdjustmentTypeDescription.name(), orderTypeName, orderAdjustmentTypeName, languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownOrderAdjustmentTypeName.name(), orderTypeName, orderAdjustmentTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownOrderTypeName.name(), orderTypeName);
        }

        return orderAdjustmentTypeDescription;
    }

    @Override
    public OrderAdjustmentType getLockEntity(OrderAdjustmentTypeDescription orderAdjustmentTypeDescription) {
        return orderAdjustmentTypeDescription.getOrderAdjustmentType();
    }

    @Override
    public void fillInResult(EditOrderAdjustmentTypeDescriptionResult result, OrderAdjustmentTypeDescription orderAdjustmentTypeDescription) {
        var orderAdjustmentControl = Session.getModelController(OrderAdjustmentControl.class);

        result.setOrderAdjustmentTypeDescription(orderAdjustmentControl.getOrderAdjustmentTypeDescriptionTransfer(getUserVisit(), orderAdjustmentTypeDescription));
    }

    @Override
    public void doLock(OrderAdjustmentTypeDescriptionEdit edit, OrderAdjustmentTypeDescription orderAdjustmentTypeDescription) {
        edit.setDescription(orderAdjustmentTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(OrderAdjustmentTypeDescription orderAdjustmentTypeDescription) {
        var orderAdjustmentControl = Session.getModelController(OrderAdjustmentControl.class);
        var orderAdjustmentTypeDescriptionValue = orderAdjustmentControl.getOrderAdjustmentTypeDescriptionValue(orderAdjustmentTypeDescription);
        orderAdjustmentTypeDescriptionValue.setDescription(edit.getDescription());

        orderAdjustmentControl.updateOrderAdjustmentTypeDescriptionFromValue(orderAdjustmentTypeDescriptionValue, getPartyPK());
    }
    
}
