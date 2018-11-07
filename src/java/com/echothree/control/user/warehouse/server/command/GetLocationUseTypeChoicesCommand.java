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

package com.echothree.control.user.warehouse.server.command;

import com.echothree.control.user.warehouse.common.form.GetLocationUseTypeChoicesForm;
import com.echothree.control.user.warehouse.common.result.GetLocationUseTypeChoicesResult;
import com.echothree.control.user.warehouse.common.result.WarehouseResultFactory;
import com.echothree.model.control.warehouse.server.WarehouseControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetLocationUseTypeChoicesCommand
        extends BaseSimpleCommand<GetLocationUseTypeChoicesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("DefaultLocationUseTypeChoice", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("AllowNullChoice", FieldType.BOOLEAN, true, null, null)
        ));
    }
    
    /** Creates a new instance of GetLocationUseTypeChoicesCommand */
    public GetLocationUseTypeChoicesCommand(UserVisitPK userVisitPK, GetLocationUseTypeChoicesForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        WarehouseControl warehouseControl = (WarehouseControl)Session.getModelController(WarehouseControl.class);
        GetLocationUseTypeChoicesResult result = WarehouseResultFactory.getGetLocationUseTypeChoicesResult();
        String defaultLocationUseTypeChoice = form.getDefaultLocationUseTypeChoice();
        boolean allowNullChoice = Boolean.parseBoolean(form.getAllowNullChoice());
        
        result.setLocationUseTypeChoices(warehouseControl.getLocationUseTypeChoices(defaultLocationUseTypeChoice,
                getPreferredLanguage(), allowNullChoice));
        
        return result;
    }
    
}
