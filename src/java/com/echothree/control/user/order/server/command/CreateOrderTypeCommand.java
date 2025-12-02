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

import com.echothree.control.user.order.common.form.CreateOrderTypeForm;
import com.echothree.control.user.order.common.result.OrderResultFactory;
import com.echothree.model.control.order.common.exception.UnknownOrderWorkflowEntranceNameException;
import com.echothree.model.control.order.common.exception.UnknownOrderWorkflowNameException;
import com.echothree.model.control.order.server.logic.OrderTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.sequence.server.logic.SequenceTypeLogic;
import com.echothree.model.control.workflow.server.logic.WorkflowEntranceLogic;
import com.echothree.model.control.workflow.server.logic.WorkflowLogic;
import com.echothree.model.data.order.server.entity.OrderType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.EntityPermission;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class CreateOrderTypeCommand
        extends BaseSimpleCommand<CreateOrderTypeForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.OrderType.name(), SecurityRoles.Create.name())
                    )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OrderTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("OrderSequenceTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("OrderWorkflowName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("OrderWorkflowEntranceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of CreateOrderTypeCommand */
    public CreateOrderTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = OrderResultFactory.getCreateOrderTypeResult();
        var orderSequenceTypeName = form.getOrderSequenceTypeName();
        var orderSequenceType = orderSequenceTypeName == null ? null : SequenceTypeLogic.getInstance().getSequenceTypeByName(this, orderSequenceTypeName);
        OrderType orderType = null;

        if(!hasExecutionErrors()) {
            var orderWorkflowName = form.getOrderWorkflowName();
            var orderWorkflow = orderWorkflowName == null ? null : WorkflowLogic.getInstance().getWorkflowByName(
                    UnknownOrderWorkflowNameException.class, ExecutionErrors.UnknownOrderWorkflowName, this,
                    orderWorkflowName, EntityPermission.READ_ONLY);

            if(!hasExecutionErrors()) {
                var orderWorkflowEntranceName = form.getOrderWorkflowEntranceName();

                if(orderWorkflowEntranceName == null || orderWorkflow != null) {
                    var orderWorkflowEntrance = orderWorkflowEntranceName == null ? null : WorkflowEntranceLogic.getInstance().getWorkflowEntranceByName(
                            UnknownOrderWorkflowEntranceNameException.class, ExecutionErrors.UnknownOrderWorkflowEntranceName, this,
                            orderWorkflow, orderWorkflowEntranceName);

                    if(!hasExecutionErrors()) {
                        var orderTypeName = form.getOrderTypeName();
                        var isDefault = Boolean.valueOf(form.getIsDefault());
                        var sortOrder = Integer.valueOf(form.getSortOrder());
                        var description = form.getDescription();
                        var partyPK = getPartyPK();

                        orderType = OrderTypeLogic.getInstance().createOrderType(this, orderTypeName, orderSequenceType, orderWorkflow,
                                orderWorkflowEntrance, isDefault, sortOrder, getPreferredLanguage(), description, partyPK);
                    }
                } else {
                    addExecutionError(ExecutionErrors.MissingRequiredOrderWorkflowName.name());
                }
            }
        }

        if(orderType != null) {
            result.setEntityRef(orderType.getPrimaryKey().getEntityRef());
            result.setOrderTypeName(orderType.getLastDetail().getOrderTypeName());
        }

        return result;
    }
    
}
