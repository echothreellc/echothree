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

package com.echothree.control.user.item.server.command;

import com.echothree.control.user.item.common.form.GetItemCountryOfOriginForm;
import com.echothree.control.user.item.common.result.GetItemCountryOfOriginResult;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemCountryOfOrigin;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetItemCountryOfOriginCommand
        extends BaseSimpleCommand<GetItemCountryOfOriginForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("CountryName", FieldType.ENTITY_NAME, true, null, null)
        ));
    }
    
    /** Creates a new instance of GetItemCountryOfOriginCommand */
    public GetItemCountryOfOriginCommand(UserVisitPK userVisitPK, GetItemCountryOfOriginForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var itemControl = Session.getModelController(ItemControl.class);
        GetItemCountryOfOriginResult result = ItemResultFactory.getGetItemCountryOfOriginResult();
        String itemName = form.getItemName();
        Item item = itemControl.getItemByName(itemName);
        
        if(item != null) {
            var geoControl = Session.getModelController(GeoControl.class);
            String countryName = form.getCountryName();
            GeoCode countryGeoCode = geoControl.getCountryByAlias(countryName);
            
            if(countryGeoCode != null) {
                ItemCountryOfOrigin itemCountryOfOrigin = itemControl.getItemCountryOfOrigin(item, countryGeoCode);
                
                if(itemCountryOfOrigin != null) {
                    result.setItemCountryOfOrigin(itemControl.getItemCountryOfOriginTransfer(getUserVisit(), itemCountryOfOrigin));
                } else {
                    addExecutionError(ExecutionErrors.UnknownItemCountryOfOrigin.name(), itemName, countryName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownCountryName.name(), countryName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownItemName.name(), itemName);
        }
        
        return result;
    }
    
}
