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
import com.echothree.control.user.order.common.edit.OrderTimeTypeDescriptionEdit;
import com.echothree.control.user.order.common.form.EditOrderTimeTypeDescriptionForm;
import com.echothree.control.user.order.common.result.EditOrderTimeTypeDescriptionResult;
import com.echothree.control.user.order.common.result.OrderResultFactory;
import com.echothree.control.user.order.common.spec.OrderTimeTypeDescriptionSpec;
import com.echothree.model.control.order.server.control.OrderTimeControl;
import com.echothree.model.control.order.server.control.OrderTypeControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.order.server.entity.OrderTimeType;
import com.echothree.model.data.order.server.entity.OrderTimeTypeDescription;
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
public class EditOrderTimeTypeDescriptionCommand
        extends BaseAbstractEditCommand<OrderTimeTypeDescriptionSpec, OrderTimeTypeDescriptionEdit, EditOrderTimeTypeDescriptionResult, OrderTimeTypeDescription, OrderTimeType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.OrderTimeType.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OrderTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("OrderTimeTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditOrderTimeTypeDescriptionCommand */
    public EditOrderTimeTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditOrderTimeTypeDescriptionResult getResult() {
        return OrderResultFactory.getEditOrderTimeTypeDescriptionResult();
    }

    @Override
    public OrderTimeTypeDescriptionEdit getEdit() {
        return OrderEditFactory.getOrderTimeTypeDescriptionEdit();
    }

    @Override
    public OrderTimeTypeDescription getEntity(EditOrderTimeTypeDescriptionResult result) {
        var orderTypeControl = Session.getModelController(OrderTypeControl.class);
        OrderTimeTypeDescription orderTimeTypeDescription = null;
        var orderTypeName = spec.getOrderTypeName();
        var orderType = orderTypeControl.getOrderTypeByName(orderTypeName);

        if(orderType != null) {
            var orderTimeControl = Session.getModelController(OrderTimeControl.class);
            var orderTimeTypeName = spec.getOrderTimeTypeName();
            var orderTimeType = orderTimeControl.getOrderTimeTypeByName(orderType, orderTimeTypeName);

            if(orderTimeType != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                var languageIsoName = spec.getLanguageIsoName();
                var language = partyControl.getLanguageByIsoName(languageIsoName);

                if(language != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        orderTimeTypeDescription = orderTimeControl.getOrderTimeTypeDescription(orderTimeType, language);
                    } else { // EditMode.UPDATE
                        orderTimeTypeDescription = orderTimeControl.getOrderTimeTypeDescriptionForUpdate(orderTimeType, language);
                    }

                    if(orderTimeTypeDescription == null) {
                        addExecutionError(ExecutionErrors.UnknownOrderTimeTypeDescription.name(), orderTypeName, orderTimeTypeName, languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownOrderTimeTypeName.name(), orderTypeName, orderTimeTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownOrderTypeName.name(), orderTypeName);
        }

        return orderTimeTypeDescription;
    }

    @Override
    public OrderTimeType getLockEntity(OrderTimeTypeDescription orderTimeTypeDescription) {
        return orderTimeTypeDescription.getOrderTimeType();
    }

    @Override
    public void fillInResult(EditOrderTimeTypeDescriptionResult result, OrderTimeTypeDescription orderTimeTypeDescription) {
        var orderTimeControl = Session.getModelController(OrderTimeControl.class);

        result.setOrderTimeTypeDescription(orderTimeControl.getOrderTimeTypeDescriptionTransfer(getUserVisit(), orderTimeTypeDescription));
    }

    @Override
    public void doLock(OrderTimeTypeDescriptionEdit edit, OrderTimeTypeDescription orderTimeTypeDescription) {
        edit.setDescription(orderTimeTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(OrderTimeTypeDescription orderTimeTypeDescription) {
        var orderTimeControl = Session.getModelController(OrderTimeControl.class);
        var orderTimeTypeDescriptionValue = orderTimeControl.getOrderTimeTypeDescriptionValue(orderTimeTypeDescription);
        
        orderTimeTypeDescriptionValue.setDescription(edit.getDescription());

        orderTimeControl.updateOrderTimeTypeDescriptionFromValue(orderTimeTypeDescriptionValue, getPartyPK());
    }
    
}
