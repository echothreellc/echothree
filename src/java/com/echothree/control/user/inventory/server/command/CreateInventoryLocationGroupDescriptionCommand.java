// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.control.user.inventory.common.form.CreateInventoryLocationGroupDescriptionForm;
import com.echothree.model.control.inventory.server.InventoryControl;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.warehouse.server.WarehouseControl;
import com.echothree.model.data.inventory.server.entity.InventoryLocationGroup;
import com.echothree.model.data.inventory.server.entity.InventoryLocationGroupDescription;
import com.echothree.model.data.party.server.entity.Language;
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

public class CreateInventoryLocationGroupDescriptionCommand
        extends BaseSimpleCommand<CreateInventoryLocationGroupDescriptionForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("WarehouseName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryLocationGroupName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of CreateInventoryLocationGroupDescriptionCommand */
    public CreateInventoryLocationGroupDescriptionCommand(UserVisitPK userVisitPK, CreateInventoryLocationGroupDescriptionForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        WarehouseControl warehouseControl = (WarehouseControl)Session.getModelController(WarehouseControl.class);
        String warehouseName = form.getWarehouseName();
        Warehouse warehouse = warehouseControl.getWarehouseByName(warehouseName);
        
        if(warehouse != null) {
            InventoryControl inventoryControl = (InventoryControl)Session.getModelController(InventoryControl.class);
            Party warehouseParty = warehouse.getParty();
            String inventoryLocationGroupName = form.getInventoryLocationGroupName();
            InventoryLocationGroup inventoryLocationGroup = inventoryControl.getInventoryLocationGroupByName(warehouseParty, inventoryLocationGroupName);
            
            if(inventoryLocationGroup != null) {
                PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                String languageIsoName = form.getLanguageIsoName();
                Language language = partyControl.getLanguageByIsoName(languageIsoName);
                
                if(language != null) {
                    InventoryLocationGroupDescription inventoryLocationGroupDescription = inventoryControl.getInventoryLocationGroupDescription(inventoryLocationGroup, language);
                    
                    if(inventoryLocationGroupDescription == null) {
                        String description = form.getDescription();
                        
                        inventoryControl.createInventoryLocationGroupDescription(inventoryLocationGroup, language, description, getPartyPK());
                    } else {
                        addExecutionError(ExecutionErrors.DuplicateInventoryLocationGroupDescription.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownInventoryLocationGroupName.name(), inventoryLocationGroupName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWarehouseName.name(), warehouseName);
        }
        
        return null;
    }
    
}
