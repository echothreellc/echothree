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

package com.echothree.control.user.vendor.server.command;

import com.echothree.control.user.vendor.common.form.CreateVendorItemForm;
import com.echothree.control.user.vendor.common.result.VendorResultFactory;
import com.echothree.model.control.cancellationpolicy.common.CancellationKinds;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.returnpolicy.common.ReturnKinds;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.vendor.common.workflow.VendorItemStatusConstants;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.vendor.server.entity.VendorItem;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateVendorItemCommand
        extends BaseSimpleCommand<CreateVendorItemForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.VendorItem.name(), SecurityRoles.Create.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("VendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("VendorItemName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L),
                new FieldDefinition("Priority", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("CancellationPolicyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ReturnPolicyName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of CreateVendorItemCommand */
    public CreateVendorItemCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = VendorResultFactory.getCreateVendorItemResult();
        VendorItem vendorItem = null;
        var vendorControl = Session.getModelController(VendorControl.class);
        var vendorName = form.getVendorName();
        var vendor = vendorControl.getVendorByName(vendorName);
        
        if(vendor != null) {
            var itemControl = Session.getModelController(ItemControl.class);
            var itemName = form.getItemName();
            var item = itemControl.getItemByNameThenAlias(itemName);
            
            if(item != null) {
                var vendorItemName = form.getVendorItemName();
                
                if(vendorItemName == null) {
                    var defaultItemAliasType = vendor.getDefaultItemAliasType();
                    
                    if(defaultItemAliasType == null) {
                        addExecutionError(ExecutionErrors.UnknownDefaultItemAliasType.name());
                    } else {
                        var itemUnitOfMeasureType = itemControl.getDefaultItemUnitOfMeasureType(item);
                        
                        if(itemUnitOfMeasureType == null) {
                            addExecutionError(ExecutionErrors.UnknownDefaultItemUnitOfMeasureType.name());
                        } else {
                            var unitOfMeasureType = itemUnitOfMeasureType.getUnitOfMeasureType();
                            var itemAliases = itemControl.getItemAliases(item, unitOfMeasureType, defaultItemAliasType);
                            var iter = itemAliases.iterator();
                            
                            if(iter.hasNext()) {
                                vendorItemName = iter.next().getAlias();
                            } else {
                                addExecutionError(ExecutionErrors.NoItemAliasesFound.name(), itemName,
                                        unitOfMeasureType.getLastDetail().getUnitOfMeasureTypeName(),
                                        defaultItemAliasType.getLastDetail().getItemAliasTypeName());
                            }
                        }
                    }
                }
                
                if(!hasExecutionErrors()) {
                    var vendorParty = vendor.getParty();
                    
                    vendorItem = vendorControl.getVendorItemByVendorPartyAndVendorItemName(vendorParty, vendorItemName);
                    
                    if(vendorItem == null) {
                        var cancellationPolicyName = form.getCancellationPolicyName();
                        CancellationPolicy cancellationPolicy = null;
                        
                        if(cancellationPolicyName != null) {
                            var cancellationPolicyControl = Session.getModelController(CancellationPolicyControl.class);
                            var cancellationKind = cancellationPolicyControl.getCancellationKindByName(CancellationKinds.VENDOR_CANCELLATION.name());
                            
                            cancellationPolicy = cancellationPolicyControl.getCancellationPolicyByName(cancellationKind, cancellationPolicyName);
                        }
                        
                        if(cancellationPolicyName == null || cancellationPolicy != null) {
                            var returnPolicyName = form.getReturnPolicyName();
                            ReturnPolicy returnPolicy = null;
                            
                            if(returnPolicyName != null) {
                                var returnPolicyControl = Session.getModelController(ReturnPolicyControl.class);
                                var returnKind = returnPolicyControl.getReturnKindByName(ReturnKinds.VENDOR_RETURN.name());
                                
                                returnPolicy = returnPolicyControl.getReturnPolicyByName(returnKind, returnPolicyName);
                            }
                            
                            if(returnPolicyName == null || returnPolicy != null) {
                                var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
                                var workflowControl = Session.getModelController(WorkflowControl.class);
                                var description = form.getDescription();
                                var priority = Integer.valueOf(form.getPriority());
                                BasePK createdBy = getPartyPK();
                                
                                vendorItem = vendorControl.createVendorItem(item, vendorParty, vendorItemName, description, priority, cancellationPolicy,
                                        returnPolicy, createdBy);

                                var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(vendorItem.getPrimaryKey());
                                workflowControl.addEntityToWorkflowUsingNames(null, VendorItemStatusConstants.Workflow_VENDOR_ITEM_STATUS,
                                        VendorItemStatusConstants.WorkflowEntrance_NEW_ACTIVE, entityInstance, null, null, createdBy);
                            } else {
                                addExecutionError(ExecutionErrors.UnknownReturnPolicyName.name(), returnPolicyName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownCancellationPolicyName.name(), cancellationPolicyName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.DuplicateVendorItemName.name(), vendorName, vendorItemName);
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownItemName.name(), itemName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownVendorName.name(), vendorName);
        }

        if(vendorItem != null) {
            result.setEntityRef(vendorItem.getPrimaryKey().getEntityRef());
            result.setVendorItemName(vendorItem.getLastDetail().getVendorItemName());
        }

        return result;
    }
    
}
