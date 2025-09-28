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

package com.echothree.control.user.item.server.command;

import com.echothree.control.user.item.common.form.CreateItemWeightForm;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.item.server.logic.ItemWeightTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.uom.server.util.Conversion;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.List;

public class CreateItemWeightCommand
        extends BaseSimpleCommand<CreateItemWeightForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.ItemWeight.name(), SecurityRoles.Create.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ItemWeightTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("WeightUnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Weight", FieldType.UNSIGNED_LONG, true, null, null)
        );
    }
    
    /** Creates a new instance of CreateItemWeightCommand */
    public CreateItemWeightCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var itemControl = Session.getModelController(ItemControl.class);
        var itemName = form.getItemName();
        var item = itemControl.getItemByName(itemName);
        
        if(item != null) {
            var uomControl = Session.getModelController(UomControl.class);
            var unitOfMeasureTypeName = form.getUnitOfMeasureTypeName();
            var unitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(item.getLastDetail().getUnitOfMeasureKind(),
                    unitOfMeasureTypeName);
            
            if(unitOfMeasureType != null) {
                var itemUnitOfMeasureType = itemControl.getItemUnitOfMeasureType(item, unitOfMeasureType);
                
                if(itemUnitOfMeasureType != null) {
                    var itemWeightType = ItemWeightTypeLogic.getInstance().getItemWeightTypeByName(this, form.getItemWeightTypeName());

                    if(!hasExecutionErrors()) {
                        var itemWeight = itemControl.getItemWeight(item, unitOfMeasureType, itemWeightType);

                        if(itemWeight == null) {
                            var weightUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_WEIGHT);
                            var weightUnitOfMeasureTypeName = form.getWeightUnitOfMeasureTypeName();
                            var weightUnitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(weightUnitOfMeasureKind,
                                    weightUnitOfMeasureTypeName);

                            if(weightUnitOfMeasureType != null) {
                                var weight = Long.valueOf(form.getWeight());

                                if(weight > 0) {
                                    var conversion = new Conversion(uomControl, weightUnitOfMeasureType, weight);

                                    conversion.convertToLowestUnitOfMeasureType();
                                    itemControl.createItemWeight(item, unitOfMeasureType, itemWeightType, conversion.getQuantity(), getPartyPK());
                                } else {
                                    addExecutionError(ExecutionErrors.InvalidWeight.name(), weight);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownWeightUnitOfMeasureTypeName.name(), weightUnitOfMeasureTypeName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.DuplicateItemWeight.name(), item.getLastDetail().getItemName(),
                                    unitOfMeasureType.getLastDetail().getUnitOfMeasureTypeName(),
                                    itemWeightType.getLastDetail().getItemWeightTypeName());
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownItemUnitOfMeasureType.name(), itemName, unitOfMeasureTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownUnitOfMeasureTypeName.name(), unitOfMeasureTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownItemName.name(), itemName);
        }
        
        return null;
    }
    
}
