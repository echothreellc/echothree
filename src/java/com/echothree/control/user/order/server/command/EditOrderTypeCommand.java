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
import com.echothree.control.user.order.common.edit.OrderTypeEdit;
import com.echothree.control.user.order.common.form.EditOrderTypeForm;
import com.echothree.control.user.order.common.result.EditOrderTypeResult;
import com.echothree.control.user.order.common.result.OrderResultFactory;
import com.echothree.control.user.order.common.spec.OrderTypeUniversalSpec;
import com.echothree.model.control.order.server.control.OrderTypeControl;
import com.echothree.model.control.order.server.logic.OrderTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.order.server.entity.OrderType;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
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

public class EditOrderTypeCommand
        extends BaseAbstractEditCommand<OrderTypeUniversalSpec, OrderTypeEdit, EditOrderTypeResult, OrderType, OrderType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.OrderType.name(), SecurityRoles.Edit.name())
                    )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OrderTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OrderTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ParentOrderTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("OrderSequenceTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("OrderWorkflowName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("OrderWorkflowEntranceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditOrderTypeCommand */
    public EditOrderTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditOrderTypeResult getResult() {
        return OrderResultFactory.getEditOrderTypeResult();
    }

    @Override
    public OrderTypeEdit getEdit() {
        return OrderEditFactory.getOrderTypeEdit();
    }

    @Override
    public OrderType getEntity(EditOrderTypeResult result) {
        var orderTypeControl = Session.getModelController(OrderTypeControl.class);
        OrderType orderType;
        var orderTypeName = spec.getOrderTypeName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            orderType = OrderTypeLogic.getInstance().getOrderTypeByUniversalSpec(this, spec, false);
        } else { // EditMode.UPDATE
            orderType = OrderTypeLogic.getInstance().getOrderTypeByUniversalSpecForUpdate(this, spec, false);
        }

        if(orderType != null) {
            result.setOrderType(orderTypeControl.getOrderTypeTransfer(getUserVisit(), orderType));
        } else {
            addExecutionError(ExecutionErrors.UnknownOrderTypeName.name(), orderTypeName);
        }

        return orderType;
    }

    @Override
    public OrderType getLockEntity(OrderType orderType) {
        return orderType;
    }

    @Override
    public void fillInResult(EditOrderTypeResult result, OrderType orderType) {
        var orderTypeControl = Session.getModelController(OrderTypeControl.class);

        result.setOrderType(orderTypeControl.getOrderTypeTransfer(getUserVisit(), orderType));
    }

    OrderType parentOrderType = null;
    SequenceType orderSequenceType = null;
    Workflow orderWorkflow = null;
    WorkflowEntrance orderWorkflowEntrance = null;

    @Override
    public void doLock(OrderTypeEdit edit, OrderType orderType) {
        var orderTypeControl = Session.getModelController(OrderTypeControl.class);
        var orderTypeDescription = orderTypeControl.getOrderTypeDescription(orderType, getPreferredLanguage());
        var orderTypeDetail = orderType.getLastDetail();

        parentOrderType = orderTypeDetail.getParentOrderType();
        orderSequenceType = orderTypeDetail.getOrderSequenceType();
        orderWorkflow = orderTypeDetail.getOrderWorkflow();
        orderWorkflowEntrance = orderTypeDetail.getOrderWorkflowEntrance();

        edit.setOrderTypeName(orderTypeDetail.getOrderTypeName());
        edit.setParentOrderTypeName(parentOrderType == null ? null : parentOrderType.getLastDetail().getOrderTypeName());
        edit.setOrderSequenceTypeName(orderSequenceType == null ? null : orderSequenceType.getLastDetail().getSequenceTypeName());
        edit.setOrderWorkflowName(orderWorkflow == null ? null : orderWorkflow.getLastDetail().getWorkflowName());
        edit.setOrderWorkflowEntranceName(orderWorkflowEntrance == null ? null : orderWorkflowEntrance.getLastDetail().getWorkflowEntranceName());
        edit.setIsDefault(orderTypeDetail.getIsDefault().toString());
        edit.setSortOrder(orderTypeDetail.getSortOrder().toString());

        if(orderTypeDescription != null) {
            edit.setDescription(orderTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(OrderType orderType) {
        var orderTypeControl = Session.getModelController(OrderTypeControl.class);
        var orderTypeName = edit.getOrderTypeName();
        var duplicateOrderType = orderTypeControl.getOrderTypeByName(orderTypeName);

        if(duplicateOrderType == null || orderType.equals(duplicateOrderType)) {
            var parentOrderTypeName = edit.getParentOrderTypeName();

            if(parentOrderTypeName != null) {
                parentOrderType = orderTypeControl.getOrderTypeByName(parentOrderTypeName);
            }

            if(parentOrderTypeName == null || parentOrderType != null) {
                if(orderTypeControl.isParentOrderTypeSafe(orderType, parentOrderType)) {
                    var sequenceControl = Session.getModelController(SequenceControl.class);
                    var orderSequenceTypeName = edit.getOrderSequenceTypeName();

                    orderSequenceType = sequenceControl.getSequenceTypeByName(orderSequenceTypeName);

                    if(orderSequenceTypeName == null || orderSequenceType != null) {
                        var workflowControl = Session.getModelController(WorkflowControl.class);
                        var orderWorkflowName = edit.getOrderWorkflowName();

                        orderWorkflow = orderWorkflowName == null ? null : workflowControl.getWorkflowByName(orderWorkflowName);

                        if(orderWorkflowName == null || orderWorkflow != null) {
                            var orderWorkflowEntranceName = edit.getOrderWorkflowEntranceName();

                            if(orderWorkflowEntranceName == null || (orderWorkflow != null && orderWorkflowEntranceName != null)) {
                                orderWorkflowEntrance = orderWorkflowEntranceName == null ? null : workflowControl.getWorkflowEntranceByName(orderWorkflow, orderWorkflowEntranceName);

                                if(orderWorkflowEntranceName != null && orderWorkflowEntrance == null) {
                                    addExecutionError(ExecutionErrors.UnknownOrderWorkflowEntranceName.name(), orderWorkflowName, orderWorkflowEntranceName);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.MissingRequiredOrderWorkflowName.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownOrderWorkflowName.name(), orderWorkflowName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownOrderSequenceTypeName.name(), orderSequenceTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.InvalidParentOrderType.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownParentOrderTypeName.name(), parentOrderTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateOrderTypeName.name(), orderTypeName);
        }
    }

    @Override
    public void doUpdate(OrderType orderType) {
        var orderTypeControl = Session.getModelController(OrderTypeControl.class);
        var partyPK = getPartyPK();
        var orderTypeDetailValue = orderTypeControl.getOrderTypeDetailValueForUpdate(orderType);
        var orderTypeDescription = orderTypeControl.getOrderTypeDescriptionForUpdate(orderType, getPreferredLanguage());
        var description = edit.getDescription();

        orderTypeDetailValue.setOrderTypeName(edit.getOrderTypeName());
        orderTypeDetailValue.setParentOrderTypePK(parentOrderType == null ? null : parentOrderType.getPrimaryKey());
        orderTypeDetailValue.setOrderSequenceTypePK(orderSequenceType == null ? null : orderSequenceType.getPrimaryKey());
        orderTypeDetailValue.setOrderWorkflowPK(orderWorkflow == null ? null : orderWorkflow.getPrimaryKey());
        orderTypeDetailValue.setOrderWorkflowEntrancePK(orderWorkflow == null ? null : orderWorkflowEntrance.getPrimaryKey());
        orderTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        orderTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        OrderTypeLogic.getInstance().updateOrderTypeFromValue(orderTypeDetailValue, partyPK);

        if(orderTypeDescription == null && description != null) {
            orderTypeControl.createOrderTypeDescription(orderType, getPreferredLanguage(), description, partyPK);
        } else {
            if(orderTypeDescription != null && description == null) {
                orderTypeControl.deleteOrderTypeDescription(orderTypeDescription, partyPK);
            } else {
                if(orderTypeDescription != null && description != null) {
                    var orderTypeDescriptionValue = orderTypeControl.getOrderTypeDescriptionValue(orderTypeDescription);

                    orderTypeDescriptionValue.setDescription(description);
                    orderTypeControl.updateOrderTypeDescriptionFromValue(orderTypeDescriptionValue, partyPK);
                }
            }
        }
    }

}
