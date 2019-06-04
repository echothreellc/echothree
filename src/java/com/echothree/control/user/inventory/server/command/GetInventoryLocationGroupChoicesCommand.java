// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

import com.echothree.control.user.inventory.common.form.GetInventoryLocationGroupChoicesForm;
import com.echothree.control.user.inventory.common.result.GetInventoryLocationGroupChoicesResult;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.model.control.inventory.server.InventoryControl;
import com.echothree.model.control.warehouse.server.WarehouseControl;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.warehouse.server.entity.Warehouse;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetInventoryLocationGroupChoicesCommand
        extends BaseSimpleCommand<GetInventoryLocationGroupChoicesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("WarehouseName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("DefaultInventoryLocationGroupChoice", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("AllowNullChoice", FieldType.BOOLEAN, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetInventoryLocationGroupChoicesCommand */
    public GetInventoryLocationGroupChoicesCommand(UserVisitPK userVisitPK, GetInventoryLocationGroupChoicesForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var warehouseControl = (WarehouseControl)Session.getModelController(WarehouseControl.class);
        GetInventoryLocationGroupChoicesResult result = InventoryResultFactory.getGetInventoryLocationGroupChoicesResult();
        String warehouseName = form.getWarehouseName();
        Warehouse warehouse = warehouseControl.getWarehouseByName(warehouseName);
        
        if(warehouse != null) {
            var inventoryControl = (InventoryControl)Session.getModelController(InventoryControl.class);
            String defaultInventoryLocationGroupChoice = form.getDefaultInventoryLocationGroupChoice();
            Party warehouseParty = warehouse.getParty();
            boolean allowNullChoice = Boolean.parseBoolean(form.getAllowNullChoice());
            
            result.setInventoryLocationGroupChoices(inventoryControl.getInventoryLocationGroupChoicesByWarehouseParty(defaultInventoryLocationGroupChoice,
                    getPreferredLanguage(), allowNullChoice, warehouseParty));
        } else {
            addExecutionError(ExecutionErrors.UnknownWarehouseName.name(), warehouseName);
        }
        
        return result;
    }
    
}
