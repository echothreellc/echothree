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

package com.echothree.control.user.item.server.command;

import com.echothree.control.user.item.common.form.GetItemCountryOfOriginForm;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.item.server.logic.ItemLogic;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetItemCountryOfOriginCommand
        extends BaseSimpleCommand<GetItemCountryOfOriginForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CountryName", FieldType.ENTITY_NAME, true, null, null)
        );
    }
    
    /** Creates a new instance of GetItemCountryOfOriginCommand */
    public GetItemCountryOfOriginCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Inject
    GeoControl geoControl;

    @Inject
    ItemControl itemControl;

    @Inject
    ItemLogic itemLogic;

    @Override
    protected BaseResult execute() {
        var result = ItemResultFactory.getGetItemCountryOfOriginResult();
        var itemName = form.getItemName();
        var item = itemLogic.getItemByName(this, itemName);
        
        if(!hasExecutionErrors()) {
            var countryName = form.getCountryName();
            var countryGeoCode = geoControl.getCountryByAlias(countryName);
            
            if(countryGeoCode != null) {
                var itemCountryOfOrigin = itemControl.getItemCountryOfOrigin(item, countryGeoCode);
                
                if(itemCountryOfOrigin != null) {
                    result.setItemCountryOfOrigin(itemControl.getItemCountryOfOriginTransfer(getUserVisit(), itemCountryOfOrigin));
                } else {
                    addExecutionError(ExecutionErrors.UnknownItemCountryOfOrigin.name(), itemName, countryName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownCountryName.name(), countryName);
            }
        }
        
        return result;
    }
    
}
