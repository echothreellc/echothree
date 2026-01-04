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

package com.echothree.control.user.sales.server.command;

import com.echothree.control.user.sales.common.edit.SalesEditFactory;
import com.echothree.control.user.sales.common.edit.SalesOrderShipmentGroupEdit;
import com.echothree.control.user.sales.common.form.EditSalesOrderShipmentGroupForm;
import com.echothree.control.user.sales.common.result.EditSalesOrderShipmentGroupResult;
import com.echothree.control.user.sales.common.result.SalesResultFactory;
import com.echothree.control.user.sales.common.spec.SalesOrderShipmentGroupSpec;
import com.echothree.model.control.contact.server.logic.ContactMechanismLogic;
import com.echothree.model.control.contact.server.logic.PartyContactMechanismLogic;
import com.echothree.model.control.order.common.OrderTypes;
import com.echothree.model.control.order.server.control.OrderShipmentGroupControl;
import com.echothree.model.control.order.server.logic.OrderLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.sales.server.logic.SalesOrderLogic;
import com.echothree.model.control.sales.server.logic.SalesOrderShipmentGroupLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.shipping.server.logic.ShippingMethodLogic;
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.model.data.order.server.entity.OrderShipmentGroup;
import com.echothree.model.data.shipping.server.entity.ShippingMethod;
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
public class EditSalesOrderShipmentGroupCommand
        extends BaseAbstractEditCommand<SalesOrderShipmentGroupSpec, SalesOrderShipmentGroupEdit, EditSalesOrderShipmentGroupResult, OrderShipmentGroup, OrderShipmentGroup> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.SalesOrderShipmentGroup.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OrderName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("OrderShipmentGroupSequence", FieldType.UNSIGNED_INTEGER, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ContactMechanismName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ShippingMethodName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("HoldUntilComplete", FieldType.BOOLEAN, true, null, null)
                ));
    }

    /** Creates a new instance of EditSalesOrderShipmentGroupCommand */
    public EditSalesOrderShipmentGroupCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditSalesOrderShipmentGroupResult getResult() {
        return SalesResultFactory.getEditSalesOrderShipmentGroupResult();
    }

    @Override
    public SalesOrderShipmentGroupEdit getEdit() {
        return SalesEditFactory.getSalesOrderShipmentGroupEdit();
    }

    @Override
    public OrderShipmentGroup getEntity(EditSalesOrderShipmentGroupResult result) {
        var orderName = spec.getOrderName();
        var order = OrderLogic.getInstance().getOrderByName(this, OrderTypes.SALES_ORDER.name(), orderName);
        OrderShipmentGroup orderShipmentGroup = null;

        if(!hasExecutionErrors()) {
            var orderShipmentGroupSequence = Integer.valueOf(spec.getOrderShipmentGroupSequence());

            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                orderShipmentGroup = SalesOrderShipmentGroupLogic.getInstance().getOrderShipmentGroup(this, order, orderShipmentGroupSequence);
            } else { // EditMode.UPDATE
                orderShipmentGroup = SalesOrderShipmentGroupLogic.getInstance().getOrderShipmentGroupForUpdate(this, order, orderShipmentGroupSequence);
            }
        }

        return orderShipmentGroup;
    }

    @Override
    public OrderShipmentGroup getLockEntity(OrderShipmentGroup orderShipmentGroup) {
        return orderShipmentGroup;
    }

    @Override
    public void fillInResult(EditSalesOrderShipmentGroupResult result, OrderShipmentGroup orderShipmentGroup) {
        var salesOrderShipmentGroupControl = Session.getModelController(OrderShipmentGroupControl.class);

        result.setOrderShipmentGroup(salesOrderShipmentGroupControl.getOrderShipmentGroupTransfer(getUserVisit(), orderShipmentGroup));
    }

    @Override
    public void doLock(SalesOrderShipmentGroupEdit edit, OrderShipmentGroup orderShipmentGroup) {
        var orderShipmentGroupDetail = orderShipmentGroup.getLastDetail();
        var partyContactMechanism = orderShipmentGroupDetail.getPartyContactMechanism();
        var partyContactMechanismDetail = partyContactMechanism == null ? null : partyContactMechanism.getLastDetail();
        var shippingMethod = orderShipmentGroupDetail.getShippingMethod();

        edit.setIsDefault(orderShipmentGroupDetail.getIsDefault().toString());
        edit.setPartyName(partyContactMechanismDetail == null ? null : partyContactMechanismDetail.getParty().getLastDetail().getPartyName());
        edit.setContactMechanismName(partyContactMechanismDetail == null ? null : partyContactMechanismDetail.getContactMechanism().getLastDetail().getContactMechanismName());
        edit.setShippingMethodName(shippingMethod == null ? null : shippingMethod.getLastDetail().getShippingMethodName());
        edit.setHoldUntilComplete(orderShipmentGroupDetail.getHoldUntilComplete().toString());
    }

    PartyContactMechanism partyContactMechanism;
    ShippingMethod shippingMethod;

    @Override
    public void canUpdate(OrderShipmentGroup orderShipmentGroup) {
        var order = orderShipmentGroup.getLastDetail().getOrder();

        SalesOrderLogic.getInstance().checkOrderAvailableForModification(session, this, order, getPartyPK());

        if(!hasExecutionErrors()) {
            var partyName = edit.getPartyName();
            var party = partyName == null ? null : PartyLogic.getInstance().getPartyByName(this, partyName);
            var contactMechanismName = edit.getContactMechanismName();
            var contactMechanism = contactMechanismName == null ? null : ContactMechanismLogic.getInstance().getContactMechanismByName(this, contactMechanismName);
            var shippingMethodName = edit.getShippingMethodName();

            shippingMethod = shippingMethodName == null ? null : ShippingMethodLogic.getInstance().getShippingMethodByName(this, shippingMethodName);

            if(!hasExecutionErrors()) {
                var parameterCount = (party == null ? 0 : 1) + (contactMechanism == null ? 0 : 1);

                switch(parameterCount) {
                    case 2 ->
                            partyContactMechanism = PartyContactMechanismLogic.getInstance().getPartyContactMechanism(this, party, contactMechanism);
                    case 0 -> {
                    }
                    default -> addExecutionError(ExecutionErrors.InvalidParameterCount.name());
                }
            }
        }
    }

    @Override
    public void doUpdate(OrderShipmentGroup orderShipmentGroup) {
        var partyPK = getPartyPK();
        var orderShipmentGroupControl = Session.getModelController(OrderShipmentGroupControl.class);
        var orderShipmentGroupDetailValue = orderShipmentGroupControl.getOrderShipmentGroupDetailValueForUpdate(orderShipmentGroup);

        orderShipmentGroupDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        orderShipmentGroupDetailValue.setPartyContactMechanismPK(partyContactMechanism == null ? null : partyContactMechanism.getPrimaryKey());
        orderShipmentGroupDetailValue.setHoldUntilComplete(Boolean.valueOf(edit.getHoldUntilComplete()));
        orderShipmentGroupDetailValue.setShippingMethodPK(shippingMethod == null ? null : shippingMethod.getPrimaryKey());

        orderShipmentGroupControl.updateOrderShipmentGroupFromValue(orderShipmentGroupDetailValue, partyPK);
    }

}
