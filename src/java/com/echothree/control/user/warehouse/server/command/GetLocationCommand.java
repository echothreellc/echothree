// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

import com.echothree.control.user.warehouse.common.form.GetLocationForm;
import com.echothree.control.user.warehouse.common.result.GetLocationResult;
import com.echothree.control.user.warehouse.common.result.WarehouseResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.warehouse.server.entity.Location;
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

public class GetLocationCommand
        extends BaseSimpleCommand<GetLocationForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("WarehouseName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("LocationName", FieldType.ENTITY_NAME, true, null, null)
        ));
    }
    
    /** Creates a new instance of GetLocationCommand */
    public GetLocationCommand(UserVisitPK userVisitPK, GetLocationForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var warehouseControl = Session.getModelController(WarehouseControl.class);
        GetLocationResult result = WarehouseResultFactory.getGetLocationResult();
        String warehouseName = form.getWarehouseName();
        Warehouse warehouse = warehouseControl.getWarehouseByName(warehouseName);
        
        if(warehouse != null) {
            Party warehouseParty = warehouse.getParty();
            String locationName = form.getLocationName();
            Location location = warehouseControl.getLocationByName(warehouseParty, locationName);
            
            if(location != null) {
                UserVisit userVisit = getUserVisit();
                
                result.setLocation(warehouseControl.getLocationTransfer(userVisit, location));
                result.setLocationNameElements(warehouseControl.getLocationNameElementTransfersByLocationType(userVisit,
                        location.getActiveDetail().getLocationType()));
                result.setLocationVolume(warehouseControl.getLocationVolumeTransfer(userVisit, location));
                result.setLocationCapacities(warehouseControl.getLocationCapacityTransfersByLocation(userVisit, location));
                
                sendEventUsingNames(location.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
            } else {
                addExecutionError(ExecutionErrors.UnknownLocationName.name(), locationName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWarehouseName.name(), warehouseName);
        }
        
        return result;
    }
    
}
