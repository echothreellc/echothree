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

package com.echothree.control.user.warehouse.server.command;

import com.echothree.control.user.warehouse.common.form.GetWarehouseForm;
import com.echothree.control.user.warehouse.common.result.GetWarehouseResult;
import com.echothree.control.user.warehouse.common.result.WarehouseResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyType;
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

public class GetWarehouseCommand
        extends BaseSimpleCommand<GetWarehouseForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("WarehouseName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetWarehouseCommand */
    public GetWarehouseCommand(UserVisitPK userVisitPK, GetWarehouseForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        GetWarehouseResult result = WarehouseResultFactory.getGetWarehouseResult();
        String warehouseName = form.getWarehouseName();
        String partyName = form.getPartyName();
        int parameterCount = (warehouseName == null? 0: 1) + (partyName == null? 0: 1);
        
        if(parameterCount == 1) {
            var warehouseControl = (WarehouseControl)Session.getModelController(WarehouseControl.class);
            Warehouse warehouse = null;
            
            if(warehouseName != null) {
                warehouse = warehouseControl.getWarehouseByName(warehouseName);
                
                if(warehouse == null) {
                    addExecutionError(ExecutionErrors.UnknownWarehouseName.name(), warehouseName);
                }
            } else {
                var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                Party party = partyControl.getPartyByName(partyName);
                
                if(party != null) {
                    PartyType partyType = partyControl.getPartyTypeByName(PartyTypes.WAREHOUSE.name());
                    
                    if(party.getLastDetail().getPartyType().equals(partyType)) {
                        warehouse = warehouseControl.getWarehouse(party);
                    } else {
                        addExecutionError(ExecutionErrors.InvalidPartyType.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
                }
            }
            
            if(!hasExecutionErrors()) {
                result.setWarehouse(warehouseControl.getWarehouseTransfer(getUserVisit(), warehouse));
                
                sendEventUsingNames(warehouse.getPartyPK(), EventTypes.READ.name(), null, null, getPartyPK());
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return result;
    }
    
}
