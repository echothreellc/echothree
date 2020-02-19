// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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
import com.echothree.control.user.order.common.spec.OrderTypeSpec;
import com.echothree.model.control.order.server.OrderControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.sequence.server.SequenceControl;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.order.server.entity.OrderType;
import com.echothree.model.data.order.server.entity.OrderTypeDescription;
import com.echothree.model.data.order.server.entity.OrderTypeDetail;
import com.echothree.model.data.order.server.value.OrderTypeDescriptionValue;
import com.echothree.model.data.order.server.value.OrderTypeDetailValue;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
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

public class EditOrderTypeCommand
        extends BaseAbstractEditCommand<OrderTypeSpec, OrderTypeEdit, EditOrderTypeResult, OrderType, OrderType> {
    
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
                new FieldDefinition("OrderTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OrderTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ParentOrderTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("OrderSequenceTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("OrderWorkflowName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("OrderWorkflowEntranceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditOrderTypeCommand */
    public EditOrderTypeCommand(UserVisitPK userVisitPK, EditOrderTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
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
        var orderControl = (OrderControl)Session.getModelController(OrderControl.class);
        OrderType orderType = null;
        String orderTypeName = spec.getOrderTypeName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            orderType = orderControl.getOrderTypeByName(orderTypeName);
        } else { // EditMode.UPDATE
            orderType = orderControl.getOrderTypeByNameForUpdate(orderTypeName);
        }

        if(orderType != null) {
            result.setOrderType(orderControl.getOrderTypeTransfer(getUserVisit(), orderType));
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
        var orderControl = (OrderControl)Session.getModelController(OrderControl.class);

        result.setOrderType(orderControl.getOrderTypeTransfer(getUserVisit(), orderType));
    }

    OrderType parentOrderType = null;
    SequenceType orderSequenceType = null;
    Workflow orderWorkflow = null;
    WorkflowEntrance orderWorkflowEntrance = null;

    @Override
    public void doLock(OrderTypeEdit edit, OrderType orderType) {
        var orderControl = (OrderControl)Session.getModelController(OrderControl.class);
        OrderTypeDescription orderTypeDescription = orderControl.getOrderTypeDescription(orderType, getPreferredLanguage());
        OrderTypeDetail orderTypeDetail = orderType.getLastDetail();

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
        var orderControl = (OrderControl)Session.getModelController(OrderControl.class);
        String orderTypeName = edit.getOrderTypeName();
        OrderType duplicateOrderType = orderControl.getOrderTypeByName(orderTypeName);

        if(duplicateOrderType == null || orderType.equals(duplicateOrderType)) {
            String parentOrderTypeName = edit.getParentOrderTypeName();

            if(parentOrderTypeName != null) {
                parentOrderType = orderControl.getOrderTypeByName(parentOrderTypeName);
            }

            if(parentOrderTypeName == null || parentOrderType != null) {
                if(orderControl.isParentOrderTypeSafe(orderType, parentOrderType)) {
                    var sequenceControl = (SequenceControl)Session.getModelController(SequenceControl.class);
                    String orderSequenceTypeName = edit.getOrderSequenceTypeName();

                    orderSequenceType = sequenceControl.getSequenceTypeByName(orderSequenceTypeName);

                    if(orderSequenceTypeName == null || orderSequenceType != null) {
                        var workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
                        String orderWorkflowName = edit.getOrderWorkflowName();

                        orderWorkflow = orderWorkflowName == null ? null : workflowControl.getWorkflowByName(orderWorkflowName);

                        if(orderWorkflowName == null || orderWorkflow != null) {
                            String orderWorkflowEntranceName = edit.getOrderWorkflowEntranceName();

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
        var orderControl = (OrderControl)Session.getModelController(OrderControl.class);
        PartyPK partyPK = getPartyPK();
        OrderTypeDetailValue orderTypeDetailValue = orderControl.getOrderTypeDetailValueForUpdate(orderType);
        OrderTypeDescription orderTypeDescription = orderControl.getOrderTypeDescriptionForUpdate(orderType, getPreferredLanguage());
        String description = edit.getDescription();

        orderTypeDetailValue.setOrderTypeName(edit.getOrderTypeName());
        orderTypeDetailValue.setParentOrderTypePK(parentOrderType == null ? null : parentOrderType.getPrimaryKey());
        orderTypeDetailValue.setOrderSequenceTypePK(orderSequenceType == null ? null : orderSequenceType.getPrimaryKey());
        orderTypeDetailValue.setOrderWorkflowPK(orderWorkflow == null ? null : orderWorkflow.getPrimaryKey());
        orderTypeDetailValue.setOrderWorkflowEntrancePK(orderWorkflow == null ? null : orderWorkflowEntrance.getPrimaryKey());
        orderTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        orderTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        orderControl.updateOrderTypeFromValue(orderTypeDetailValue, partyPK);

        if(orderTypeDescription == null && description != null) {
            orderControl.createOrderTypeDescription(orderType, getPreferredLanguage(), description, partyPK);
        } else {
            if(orderTypeDescription != null && description == null) {
                orderControl.deleteOrderTypeDescription(orderTypeDescription, partyPK);
            } else {
                if(orderTypeDescription != null && description != null) {
                    OrderTypeDescriptionValue orderTypeDescriptionValue = orderControl.getOrderTypeDescriptionValue(orderTypeDescription);

                    orderTypeDescriptionValue.setDescription(description);
                    orderControl.updateOrderTypeDescriptionFromValue(orderTypeDescriptionValue, partyPK);
                }
            }
        }
    }

}
