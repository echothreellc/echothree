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

package com.echothree.control.user.inventory.server.command;

import com.echothree.control.user.inventory.common.form.CreateInventoryDispositionAdjustmentForm;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.model.control.inventory.server.logic.InventoryDispositionAdjustmentLogic;
import com.echothree.model.control.inventory.server.logic.InventoryTransactionTypeLogic;
import com.echothree.model.control.inventory.server.logic.InventoryDispositionLogic;
import com.echothree.model.control.inventory.server.logic.InventoryAdjustmentTypeLogic;
import com.echothree.model.control.inventory.server.logic.InventoryBucketTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.inventory.server.entity.InventoryDispositionAdjustment;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class CreateInventoryDispositionAdjustmentCommand
        extends BaseSimpleCommand<CreateInventoryDispositionAdjustmentForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.InventoryDispositionAdjustment.name(), SecurityRoles.Create.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("InventoryTransactionTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryDispositionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryDispositionAdjustmentName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryAdjustmentTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryBucketTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }

    @Inject
    InventoryDispositionAdjustmentLogic inventoryDispositionAdjustmentLogic;

    @Inject
    InventoryTransactionTypeLogic inventoryTransactionTypeLogic;

    @Inject
    InventoryDispositionLogic inventoryDispositionLogic;

    @Inject
    InventoryAdjustmentTypeLogic inventoryAdjustmentTypeLogic;

    @Inject
    InventoryBucketTypeLogic inventoryBucketTypeLogic;

    /** Creates a new instance of CreateInventoryDispositionAdjustmentCommand */
    public CreateInventoryDispositionAdjustmentCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = InventoryResultFactory.getCreateInventoryDispositionAdjustmentResult();
        var inventoryTransactionTypeName = form.getInventoryTransactionTypeName();
        var inventoryTransactionType = inventoryTransactionTypeLogic.getInventoryTransactionTypeByName(this, inventoryTransactionTypeName);
        InventoryDispositionAdjustment inventoryDispositionAdjustment = null;

        if(!hasExecutionErrors()) {
            var inventoryDispositionAdjustmentName = form.getInventoryDispositionAdjustmentName();
            var inventoryDispositionName = form.getInventoryDispositionName();
            var inventoryDisposition = inventoryDispositionLogic.getInventoryDispositionByName(this, inventoryTransactionType,
                    inventoryDispositionName);

            if(!hasExecutionErrors()) {
                var inventoryAdjustmentType = inventoryAdjustmentTypeLogic.getInventoryAdjustmentTypeByName(this,
                        form.getInventoryAdjustmentTypeName());
                var inventoryBucketType = inventoryBucketTypeLogic.getInventoryBucketTypeByName(this,
                        form.getInventoryBucketTypeName());
                var isDefault = Boolean.valueOf(form.getIsDefault());
                var sortOrder = Integer.valueOf(form.getSortOrder());
                var description = form.getDescription();
                var partyPK = getPartyPK();

                if(!hasExecutionErrors()) {
                    inventoryDispositionAdjustment = inventoryDispositionAdjustmentLogic.createInventoryDispositionAdjustment(this,
                            inventoryDisposition, inventoryDispositionAdjustmentName, inventoryAdjustmentType, inventoryBucketType,
                            isDefault, sortOrder, getPreferredLanguage(), description, partyPK);
                }
            }
        }

        if(inventoryDispositionAdjustment != null) {
            var inventoryDispositionAdjustmentDetail = inventoryDispositionAdjustment.getLastDetail();

            result.setEntityRef(inventoryDispositionAdjustment.getPrimaryKey().getEntityRef());
            result.setInventoryTransactionTypeName(inventoryTransactionType.getLastDetail().getInventoryTransactionTypeName());
            result.setInventoryDispositionName(inventoryDispositionAdjustmentDetail.getInventoryDisposition().getLastDetail().getInventoryDispositionName());
            result.setInventoryDispositionAdjustmentName(inventoryDispositionAdjustmentDetail.getInventoryDispositionAdjustmentName());
        }

        return result;
    }
    
}
