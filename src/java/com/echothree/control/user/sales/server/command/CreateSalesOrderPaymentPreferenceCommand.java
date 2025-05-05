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

package com.echothree.control.user.sales.server.command;

import com.echothree.control.user.sales.common.form.CreateSalesOrderPaymentPreferenceForm;
import com.echothree.control.user.sales.common.result.SalesResultFactory;
import com.echothree.model.control.order.server.logic.OrderLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.payment.server.logic.PartyPaymentMethodLogic;
import com.echothree.model.control.payment.server.logic.PaymentMethodLogic;
import com.echothree.model.control.sales.server.logic.SalesOrderLogic;
import com.echothree.model.control.sales.server.logic.SalesOrderPaymentPreferenceLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.order.server.entity.OrderPaymentPreference;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.validation.Validator;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateSalesOrderPaymentPreferenceCommand
        extends BaseSimpleCommand<CreateSalesOrderPaymentPreferenceForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.SalesOrderPaymentPreference.name(), SecurityRoles.Create.name())
                    )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OrderName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("OrderPaymentPreferenceSequence", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("PaymentMethodName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PartyPaymentMethodName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("WasPresent", FieldType.BOOLEAN, false, null, null),
                new FieldDefinition("MaximumAmount", FieldType.PRICE_UNIT, false, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
                ));
    }

    /** Creates a new instance of CreateSalesOrderPaymentPreferenceCommand */
    public CreateSalesOrderPaymentPreferenceCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }

    @Override
    protected void setupValidator(Validator validator) {
        var orderName = form.getOrderName();
        var order = orderName == null ? null : SalesOrderLogic.getInstance().getOrderByName(this, orderName);
        
        if(order != null) {
            validator.setCurrency(OrderLogic.getInstance().getOrderCurrency(order));
        }
    }
    
    @Override
    protected BaseResult execute() {
        var result = SalesResultFactory.getCreateSalesOrderPaymentPreferenceResult();
        var orderName = form.getOrderName();
        var order = SalesOrderLogic.getInstance().getOrderByName(this, orderName);
        var paymentMethodName = form.getPaymentMethodName();
        var paymentMethod = paymentMethodName == null ? null : PaymentMethodLogic.getInstance().getPaymentMethodByName(this, paymentMethodName);
        var partyPaymentMethodName = form.getPartyPaymentMethodName();
        var partyPaymentMethod = partyPaymentMethodName == null ? null : PartyPaymentMethodLogic.getInstance().getPartyPaymentMethodByName(this, partyPaymentMethodName);
        OrderPaymentPreference orderPaymentPreference = null;
        
        if(!hasExecutionErrors()) {
            var strOrderPaymentPreferenceSequence = form.getOrderPaymentPreferenceSequence();
            var orderPaymentPreferenceSequence = strOrderPaymentPreferenceSequence == null ? null : Integer.valueOf(strOrderPaymentPreferenceSequence);
            var strWasPresent = form.getWasPresent();
            var wasPresent = strWasPresent == null ? null : Boolean.valueOf(strWasPresent);
            var strMaximumAmount = form.getMaximumAmount();
            var maximumAmount = strMaximumAmount == null ? null : Long.valueOf(strMaximumAmount);
            var sortOrder = Integer.valueOf(form.getSortOrder());

            orderPaymentPreference = SalesOrderPaymentPreferenceLogic.getInstance().createSalesOrderPaymentPreference(session,
                    this, order, orderPaymentPreferenceSequence, paymentMethod, partyPaymentMethod, wasPresent, maximumAmount,
                    sortOrder, getPartyPK());
        }
        
        if(orderPaymentPreference != null) {
            var orderPaymentPreferenceDetail = orderPaymentPreference.getLastDetail();
            
            result.setOrderName(orderPaymentPreferenceDetail.getOrder().getLastDetail().getOrderName());
            result.setOrderPaymentPreferenceSequence(orderPaymentPreferenceDetail.getOrderPaymentPreferenceSequence().toString());
            result.setEntityRef(orderPaymentPreference.getPrimaryKey().getEntityRef());
        }

        return result;
    }

}
