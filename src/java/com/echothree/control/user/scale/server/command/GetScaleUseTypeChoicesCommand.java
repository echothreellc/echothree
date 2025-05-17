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

package com.echothree.control.user.scale.server.command;

import com.echothree.control.user.scale.common.form.GetScaleUseTypeChoicesForm;
import com.echothree.control.user.scale.common.result.ScaleResultFactory;
import com.echothree.model.control.scale.server.control.ScaleControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetScaleUseTypeChoicesCommand
        extends BaseSimpleCommand<GetScaleUseTypeChoicesForm> {
    
   private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("DefaultScaleUseTypeChoice", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("AllowNullChoice", FieldType.BOOLEAN, true, null, null)
                ));
    }

    /** Creates a new instance of GetScaleUseTypeChoicesCommand */
    public GetScaleUseTypeChoicesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
   @Override
    protected BaseResult execute() {
        var scaleControl = Session.getModelController(ScaleControl.class);
       var result = ScaleResultFactory.getGetScaleUseTypeChoicesResult();
       var defaultScaleUseTypeChoice = form.getDefaultScaleUseTypeChoice();
       var allowNullChoice = Boolean.parseBoolean(form.getAllowNullChoice());
        
        result.setScaleUseTypeChoices(scaleControl.getScaleUseTypeChoices(defaultScaleUseTypeChoice, getPreferredLanguage(),
                allowNullChoice));
        
        return result;
    }
    
}
