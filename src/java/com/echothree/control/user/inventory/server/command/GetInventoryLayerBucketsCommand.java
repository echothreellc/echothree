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

import com.echothree.control.user.inventory.common.form.GetInventoryLayerBucketsForm;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.model.control.inventory.server.control.BucketControl;
import com.echothree.model.control.inventory.server.control.InventoryBucketTypeControl;
import com.echothree.model.control.inventory.server.control.InventoryLayerControl;
import com.echothree.model.control.inventory.server.logic.InventoryBucketTypeLogic;
import com.echothree.model.control.inventory.server.logic.InventoryLayerLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.inventory.server.entity.InventoryBucketType;
import com.echothree.model.data.inventory.server.entity.InventoryLayer;
import com.echothree.model.data.inventory.server.entity.InventoryLayerBucket;
import com.echothree.model.data.inventory.server.factory.InventoryLayerBucketFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetInventoryLayerBucketsCommand
        extends BasePaginatedMultipleEntitiesCommand<InventoryLayerBucket, GetInventoryLayerBucketsForm> {

    private static final CommandSecurityDefinition COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
            new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
            new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                    new SecurityRoleDefinition(SecurityRoleGroups.InventoryLayerBucket.name(), SecurityRoles.List.name())
            ))
    ));

    private static final List<FieldDefinition> FORM_FIELD_DEFINITIONS = List.of(
            new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("CompanyName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("InventoryConditionName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("InventoryLayerSequence", FieldType.UNSIGNED_INTEGER, false, null, null),
            new FieldDefinition("ItemName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("InventoryBucketTypeName", FieldType.ENTITY_NAME, false, null, null)
    );

    @Inject
    BucketControl bucketControl;

    @Inject
    InventoryBucketTypeControl inventoryBucketTypeControl;

    @Inject
    InventoryLayerControl inventoryLayerControl;

    @Inject
    InventoryBucketTypeLogic inventoryBucketTypeLogic;

    @Inject
    InventoryLayerLogic inventoryLayerLogic;

    private InventoryLayer inventoryLayer;
    private InventoryBucketType inventoryBucketType;

    public GetInventoryLayerBucketsCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected void handleForm() {
        var partyName = form.getPartyName();
        var companyName = form.getCompanyName();
        var itemName = form.getItemName();
        var inventoryConditionName = form.getInventoryConditionName();
        var inventoryLayerSequence = form.getInventoryLayerSequence();
        var inventoryBucketTypeName = form.getInventoryBucketTypeName();
        var companyParameterCount = (partyName == null ? 0 : 1) + (companyName == null ? 0 : 1);
        var hasAnyInventoryLayerParameter = companyParameterCount != 0 || itemName != null
                || inventoryConditionName != null || inventoryLayerSequence != null;
        var hasCompleteInventoryLayer = companyParameterCount == 1 && itemName != null
                && inventoryConditionName != null && inventoryLayerSequence != null;
        var selectorCount = (hasCompleteInventoryLayer ? 1 : 0) + (inventoryBucketTypeName == null ? 0 : 1);

        if((hasAnyInventoryLayerParameter && !hasCompleteInventoryLayer) || selectorCount != 1) {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        } else if(hasCompleteInventoryLayer) {
            inventoryLayer = inventoryLayerLogic.getInventoryLayerByName(this, companyName, partyName,
                    itemName, inventoryConditionName, Integer.valueOf(inventoryLayerSequence));
        } else {
            inventoryBucketType = inventoryBucketTypeLogic.getInventoryBucketTypeByName(this,
                    inventoryBucketTypeName);
        }
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : inventoryLayer != null
                ? bucketControl.countInventoryLayerBucketsByInventoryLayer(inventoryLayer)
                : bucketControl.countInventoryLayerBucketsByInventoryBucketType(inventoryBucketType);
    }

    @Override
    protected Collection<InventoryLayerBucket> getEntities() {
        return hasExecutionErrors() ? null : inventoryLayer != null
                ? bucketControl.getInventoryLayerBucketsByInventoryLayer(inventoryLayer)
                : bucketControl.getInventoryLayerBucketsByInventoryBucketType(inventoryBucketType);
    }

    @Override
    protected BaseResult getResult(Collection<InventoryLayerBucket> entities) {
        var result = InventoryResultFactory.getGetInventoryLayerBucketsResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            if(inventoryLayer != null) {
                result.setInventoryLayer(inventoryLayerControl.getInventoryLayerTransfer(userVisit,
                        inventoryLayer));
            } else {
                result.setInventoryBucketType(inventoryBucketTypeControl.getInventoryBucketTypeTransfer(userVisit,
                        inventoryBucketType));
            }

            if(session.hasLimit(InventoryLayerBucketFactory.class)) {
                result.setInventoryLayerBucketCount(getTotalEntities());
            }

            result.setInventoryLayerBuckets(bucketControl.getInventoryLayerBucketTransfers(userVisit, entities));
        }

        return result;
    }

}
