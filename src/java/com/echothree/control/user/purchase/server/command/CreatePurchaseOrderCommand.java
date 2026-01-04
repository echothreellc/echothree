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

package com.echothree.control.user.purchase.server.command;

import com.echothree.control.user.purchase.common.form.CreatePurchaseOrderForm;
import com.echothree.control.user.purchase.common.result.PurchaseResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.purchase.server.logic.PurchaseOrderLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class CreatePurchaseOrderCommand
        extends BaseSimpleCommand<CreatePurchaseOrderForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.PurchaseOrder.name(), SecurityRoles.Create.name())
                    )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("VendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TermName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("HoldUntilComplete", FieldType.BOOLEAN, false, null, null),
                new FieldDefinition("AllowBackorders", FieldType.BOOLEAN, false, null, null),
                new FieldDefinition("AllowSubstitutions", FieldType.BOOLEAN, false, null, null),
                new FieldDefinition("AllowCombiningShipments", FieldType.BOOLEAN, false, null, null),
                new FieldDefinition("Reference", FieldType.STRING, false, 1L, 40L),
                new FieldDefinition("FreeOnBoardName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("WorkflowEntranceName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }

    /** Creates a new instance of CreatePurchaseOrderCommand */
    public CreatePurchaseOrderCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }

    @Override
    protected BaseResult execute() {
        var result = PurchaseResultFactory.getCreatePurchaseOrderResult();
        var vendorName = form.getVendorName();
        var termName = form.getTermName();
        var holdUntilComplete = form.getHoldUntilComplete();
        var allowBackorders = form.getAllowBackorders();
        var allowSubstitutions = form.getAllowSubstitutions();
        var allowCombiningShipments = form.getAllowCombiningShipments();
        var reference = form.getReference();
        var freeOnBoardName = form.getFreeOnBoardName();
        var workflowEntranceName = form.getWorkflowEntranceName();

        var order = PurchaseOrderLogic.getInstance().createPurchaseOrder(session, this, getUserVisit(), vendorName,
                termName, holdUntilComplete, allowBackorders, allowSubstitutions, allowCombiningShipments, reference,
                freeOnBoardName, workflowEntranceName, getParty());

        if(!hasExecutionErrors()) {
            result.setOrderName(order.getLastDetail().getOrderName());
            result.setEntityRef(order.getPrimaryKey().getEntityRef());
        }

        return result;
    }

}
