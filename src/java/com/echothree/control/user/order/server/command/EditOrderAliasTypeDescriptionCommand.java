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

import com.echothree.control.user.order.common.edit.OrderAliasTypeDescriptionEdit;
import com.echothree.control.user.order.common.edit.OrderEditFactory;
import com.echothree.control.user.order.common.form.EditOrderAliasTypeDescriptionForm;
import com.echothree.control.user.order.common.result.EditOrderAliasTypeDescriptionResult;
import com.echothree.control.user.order.common.result.OrderResultFactory;
import com.echothree.control.user.order.common.spec.OrderAliasTypeDescriptionSpec;
import com.echothree.model.control.order.server.control.OrderAliasControl;
import com.echothree.model.control.order.server.control.OrderTypeControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.order.server.entity.OrderAliasType;
import com.echothree.model.data.order.server.entity.OrderAliasTypeDescription;
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
public class EditOrderAliasTypeDescriptionCommand
        extends BaseAbstractEditCommand<OrderAliasTypeDescriptionSpec, OrderAliasTypeDescriptionEdit, EditOrderAliasTypeDescriptionResult, OrderAliasTypeDescription, OrderAliasType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.OrderAliasType.name(), SecurityRoles.Description.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OrderTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("OrderAliasTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditOrderAliasTypeDescriptionCommand */
    public EditOrderAliasTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditOrderAliasTypeDescriptionResult getResult() {
        return OrderResultFactory.getEditOrderAliasTypeDescriptionResult();
    }

    @Override
    public OrderAliasTypeDescriptionEdit getEdit() {
        return OrderEditFactory.getOrderAliasTypeDescriptionEdit();
    }

    @Override
    public OrderAliasTypeDescription getEntity(EditOrderAliasTypeDescriptionResult result) {
        var orderTypeControl = Session.getModelController(OrderTypeControl.class);
        OrderAliasTypeDescription orderAliasTypeDescription = null;
        var orderTypeName = spec.getOrderTypeName();
        var orderType = orderTypeControl.getOrderTypeByName(orderTypeName);

        if(orderType != null) {
            var orderAliasControl = Session.getModelController(OrderAliasControl.class);
            var orderAliasTypeName = spec.getOrderAliasTypeName();
            var orderAliasType = orderAliasControl.getOrderAliasTypeByName(orderType, orderAliasTypeName);

            if(orderAliasType != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                var languageIsoName = spec.getLanguageIsoName();
                var language = partyControl.getLanguageByIsoName(languageIsoName);

                if(language != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        orderAliasTypeDescription = orderAliasControl.getOrderAliasTypeDescription(orderAliasType, language);
                    } else { // EditMode.UPDATE
                        orderAliasTypeDescription = orderAliasControl.getOrderAliasTypeDescriptionForUpdate(orderAliasType, language);
                    }

                    if(orderAliasTypeDescription == null) {
                        addExecutionError(ExecutionErrors.UnknownOrderAliasTypeDescription.name(), orderTypeName, orderAliasTypeName, languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownOrderAliasTypeName.name(), orderTypeName, orderAliasTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownOrderTypeName.name(), orderTypeName);
        }

        return orderAliasTypeDescription;
    }

    @Override
    public OrderAliasType getLockEntity(OrderAliasTypeDescription orderAliasTypeDescription) {
        return orderAliasTypeDescription.getOrderAliasType();
    }

    @Override
    public void fillInResult(EditOrderAliasTypeDescriptionResult result, OrderAliasTypeDescription orderAliasTypeDescription) {
        var orderAliasControl = Session.getModelController(OrderAliasControl.class);

        result.setOrderAliasTypeDescription(orderAliasControl.getOrderAliasTypeDescriptionTransfer(getUserVisit(), orderAliasTypeDescription));
    }

    @Override
    public void doLock(OrderAliasTypeDescriptionEdit edit, OrderAliasTypeDescription orderAliasTypeDescription) {
        edit.setDescription(orderAliasTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(OrderAliasTypeDescription orderAliasTypeDescription) {
        var orderAliasControl = Session.getModelController(OrderAliasControl.class);
        var orderAliasTypeDescriptionValue = orderAliasControl.getOrderAliasTypeDescriptionValue(orderAliasTypeDescription);

        orderAliasTypeDescriptionValue.setDescription(edit.getDescription());

        orderAliasControl.updateOrderAliasTypeDescriptionFromValue(orderAliasTypeDescriptionValue, getPartyPK());
    }


}
