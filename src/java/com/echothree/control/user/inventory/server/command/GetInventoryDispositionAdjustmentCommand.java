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

import com.echothree.control.user.inventory.common.form.GetInventoryDispositionAdjustmentForm;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.inventory.server.control.InventoryDispositionAdjustmentControl;
import com.echothree.model.control.inventory.server.logic.InventoryDispositionAdjustmentLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.inventory.server.entity.InventoryDispositionAdjustment;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetInventoryDispositionAdjustmentCommand
        extends BaseSingleEntityCommand<InventoryDispositionAdjustment, GetInventoryDispositionAdjustmentForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.InventoryDispositionAdjustment.name(), SecurityRoles.Review.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("InventoryTransactionTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("InventoryDispositionName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("InventoryDispositionAdjustmentName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );
    }

    @Inject
    InventoryDispositionAdjustmentControl inventoryDispositionAdjustmentControl;

    @Inject
    InventoryDispositionAdjustmentLogic inventoryDispositionAdjustmentLogic;

    /** Creates a new instance of GetInventoryDispositionAdjustmentCommand */
    public GetInventoryDispositionAdjustmentCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected InventoryDispositionAdjustment getEntity() {
        var inventoryDispositionAdjustment = inventoryDispositionAdjustmentLogic.getInventoryDispositionAdjustmentByUniversalSpec(this, form, true);

        if(inventoryDispositionAdjustment != null) {
            sendEvent(inventoryDispositionAdjustment.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
        }

        return inventoryDispositionAdjustment;
    }

    @Override
    protected BaseResult getResult(InventoryDispositionAdjustment inventoryDispositionAdjustment) {
        var result = InventoryResultFactory.getGetInventoryDispositionAdjustmentResult();

        if(inventoryDispositionAdjustment != null) {
            result.setInventoryDispositionAdjustment(inventoryDispositionAdjustmentControl.getInventoryDispositionAdjustmentTransfer(getUserVisit(),
                    inventoryDispositionAdjustment));
        }

        return result;
    }
    
}
