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

import com.echothree.control.user.order.common.edit.OrderAdjustmentTypeDescriptionEdit;
import com.echothree.control.user.order.common.edit.OrderEditFactory;
import com.echothree.control.user.order.common.form.EditOrderAdjustmentTypeDescriptionForm;
import com.echothree.control.user.order.common.result.EditOrderAdjustmentTypeDescriptionResult;
import com.echothree.control.user.order.common.result.OrderResultFactory;
import com.echothree.control.user.order.common.spec.OrderAdjustmentTypeDescriptionSpec;
import com.echothree.model.control.order.server.OrderControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.order.server.entity.OrderAdjustmentType;
import com.echothree.model.data.order.server.entity.OrderAdjustmentTypeDescription;
import com.echothree.model.data.order.server.entity.OrderType;
import com.echothree.model.data.order.server.value.OrderAdjustmentTypeDescriptionValue;
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

public class EditOrderAdjustmentTypeDescriptionCommand
        extends BaseAbstractEditCommand<OrderAdjustmentTypeDescriptionSpec, OrderAdjustmentTypeDescriptionEdit, EditOrderAdjustmentTypeDescriptionResult, OrderAdjustmentTypeDescription, OrderAdjustmentType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.OrderAdjustmentType.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OrderTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("OrderAdjustmentTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditOrderAdjustmentTypeDescriptionCommand */
    public EditOrderAdjustmentTypeDescriptionCommand(UserVisitPK userVisitPK, EditOrderAdjustmentTypeDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
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
        OrderControl orderControl = (OrderControl)Session.getModelController(OrderControl.class);
        OrderAdjustmentTypeDescription orderAdjustmentTypeDescription = null;
        String orderTypeName = spec.getOrderTypeName();
        OrderType orderType = orderControl.getOrderTypeByName(orderTypeName);

        if(orderType != null) {
            String orderAdjustmentTypeName = spec.getOrderAdjustmentTypeName();
            OrderAdjustmentType orderAdjustmentType = orderControl.getOrderAdjustmentTypeByName(orderType, orderAdjustmentTypeName);

            if(orderAdjustmentType != null) {
                PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                String languageIsoName = spec.getLanguageIsoName();
                Language language = partyControl.getLanguageByIsoName(languageIsoName);

                if(language != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        orderAdjustmentTypeDescription = orderControl.getOrderAdjustmentTypeDescription(orderAdjustmentType, language);
                    } else { // EditMode.UPDATE
                        orderAdjustmentTypeDescription = orderControl.getOrderAdjustmentTypeDescriptionForUpdate(orderAdjustmentType, language);
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
        OrderControl orderControl = (OrderControl)Session.getModelController(OrderControl.class);

        result.setOrderAdjustmentTypeDescription(orderControl.getOrderAdjustmentTypeDescriptionTransfer(getUserVisit(), orderAdjustmentTypeDescription));
    }

    @Override
    public void doLock(OrderAdjustmentTypeDescriptionEdit edit, OrderAdjustmentTypeDescription orderAdjustmentTypeDescription) {
        edit.setDescription(orderAdjustmentTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(OrderAdjustmentTypeDescription orderAdjustmentTypeDescription) {
        OrderControl orderControl = (OrderControl)Session.getModelController(OrderControl.class);
        OrderAdjustmentTypeDescriptionValue orderAdjustmentTypeDescriptionValue = orderControl.getOrderAdjustmentTypeDescriptionValue(orderAdjustmentTypeDescription);
        orderAdjustmentTypeDescriptionValue.setDescription(edit.getDescription());

        orderControl.updateOrderAdjustmentTypeDescriptionFromValue(orderAdjustmentTypeDescriptionValue, getPartyPK());
    }
    
}
