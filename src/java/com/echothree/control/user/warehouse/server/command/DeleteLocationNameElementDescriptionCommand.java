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

package com.echothree.control.user.warehouse.server.command;

import com.echothree.control.user.warehouse.common.form.DeleteLocationNameElementDescriptionForm;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.warehouse.server.WarehouseControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.warehouse.server.entity.LocationNameElement;
import com.echothree.model.data.warehouse.server.entity.LocationNameElementDescription;
import com.echothree.model.data.warehouse.server.entity.LocationType;
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

public class DeleteLocationNameElementDescriptionCommand
        extends BaseSimpleCommand<DeleteLocationNameElementDescriptionForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("WarehouseName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LocationTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LocationNameElementName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of DeleteLocationNameElementDescriptionCommand */
    public DeleteLocationNameElementDescriptionCommand(UserVisitPK userVisitPK, DeleteLocationNameElementDescriptionForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var warehouseControl = (WarehouseControl)Session.getModelController(WarehouseControl.class);
        String warehouseName = form.getWarehouseName();
        Warehouse warehouse = warehouseControl.getWarehouseByName(warehouseName);
        
        if(warehouse != null) {
            Party warehouseParty = warehouse.getParty();
            String locationTypeName = form.getLocationTypeName();
            LocationType locationType = warehouseControl.getLocationTypeByName(warehouseParty, locationTypeName);
            
            if(locationType != null) {
                String locationNameElementName = form.getLocationNameElementName();
                LocationNameElement locationNameElement = warehouseControl.getLocationNameElementByName(locationType, locationNameElementName);
                
                if(locationNameElement != null) {
                    var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                    String languageIsoName = form.getLanguageIsoName();
                    Language language = partyControl.getLanguageByIsoName(languageIsoName);
                    
                    if(language != null) {
                        LocationNameElementDescription locationNameElementDescription = warehouseControl.getLocationNameElementDescriptionForUpdate(locationNameElement, language);
                        
                        if(locationNameElementDescription != null) {
                            warehouseControl.deleteLocationNameElementDescription(locationNameElementDescription, getPartyPK());
                        } else {
                            addExecutionError(ExecutionErrors.UnknownLocationNameElementDescription.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLocationNameElementName.name(), locationNameElementName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLocationTypeName.name(), locationTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWarehouseName.name(), warehouseName);
        }
        
        return null;
    }
    
}
