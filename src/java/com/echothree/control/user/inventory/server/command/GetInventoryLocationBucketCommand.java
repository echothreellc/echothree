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

import com.echothree.control.user.inventory.common.form.GetInventoryLocationBucketForm;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.model.control.inventory.server.control.BucketControl;
import com.echothree.model.control.inventory.server.logic.InventoryBucketTypeLogic;
import com.echothree.model.control.inventory.server.logic.InventoryLocationLogic;
import com.echothree.model.control.item.server.logic.ItemLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.warehouse.server.logic.LocationLogic;
import com.echothree.model.data.inventory.server.entity.InventoryLocationBucket;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
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
public class GetInventoryLocationBucketCommand
        extends BaseSingleEntityCommand<InventoryLocationBucket, GetInventoryLocationBucketForm> {

    private static final CommandSecurityDefinition COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
            new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
            new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                    new SecurityRoleDefinition(SecurityRoleGroups.InventoryLocationBucket.name(), SecurityRoles.Review.name())
            ))
    ));

    private static final List<FieldDefinition> FORM_FIELD_DEFINITIONS = List.of(
            new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("WarehouseName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("LocationName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("InventoryBucketTypeName", FieldType.ENTITY_NAME, true, null, null)
    );

    @Inject
    BucketControl bucketControl;

    @Inject
    LocationLogic locationLogic;

    @Inject
    ItemLogic itemLogic;

    @Inject
    InventoryLocationLogic inventoryLocationLogic;

    @Inject
    InventoryBucketTypeLogic inventoryBucketTypeLogic;

    public GetInventoryLocationBucketCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected InventoryLocationBucket getEntity() {
        InventoryLocationBucket inventoryLocationBucket = null;
        var location = locationLogic.getLocation(this, form);
        var item = itemLogic.getItemByName(this, form.getItemName());
        var inventoryBucketType = inventoryBucketTypeLogic.getInventoryBucketTypeByName(this,
                form.getInventoryBucketTypeName());

        if(!hasExecutionErrors()) {
            var inventoryLocation = inventoryLocationLogic.getInventoryLocation(this, location, item);

            if(!hasExecutionErrors()) {
                inventoryLocationBucket = bucketControl.getInventoryLocationBucket(inventoryLocation, inventoryBucketType);

                if(inventoryLocationBucket == null) {
                    addExecutionError(ExecutionErrors.UnknownInventoryLocationBucket.name(),
                            location.getLastDetail().getLocationName(), item.getLastDetail().getItemName(),
                            inventoryBucketType.getLastDetail().getInventoryBucketTypeName());
                }
            }
        }

        return inventoryLocationBucket;
    }

    @Override
    protected BaseResult getResult(InventoryLocationBucket inventoryLocationBucket) {
        var result = InventoryResultFactory.getGetInventoryLocationBucketResult();

        if(inventoryLocationBucket != null) {
            result.setInventoryLocationBucket(bucketControl.getInventoryLocationBucketTransfer(getUserVisit(),
                    inventoryLocationBucket));
        }

        return result;
    }

}
