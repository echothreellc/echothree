// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.control.user.selector.server.command;

import com.echothree.control.user.selector.common.form.GetSelectorComparisonTypeChoicesForm;
import com.echothree.control.user.selector.common.result.GetSelectorComparisonTypeChoicesResult;
import com.echothree.control.user.selector.common.result.SelectorResultFactory;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetSelectorComparisonTypeChoicesCommand
        extends BaseSimpleCommand<GetSelectorComparisonTypeChoicesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("DefaultSelectorComparisonTypeChoice", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("AllowNullChoice", FieldType.BOOLEAN, true, null, null)
        ));
    }
    
    /** Creates a new instance of GetSelectorComparisonTypeChoicesCommand */
    public GetSelectorComparisonTypeChoicesCommand(UserVisitPK userVisitPK, GetSelectorComparisonTypeChoicesForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var selectorControl = Session.getModelController(SelectorControl.class);
        GetSelectorComparisonTypeChoicesResult result = SelectorResultFactory.getGetSelectorComparisonTypeChoicesResult();
        String defaultSelectorComparisonTypeChoice = form.getDefaultSelectorComparisonTypeChoice();
        boolean allowNullChoice = Boolean.parseBoolean(form.getAllowNullChoice());
        
        result.setSelectorComparisonTypeChoices(selectorControl.getSelectorComparisonTypeChoices(defaultSelectorComparisonTypeChoice,
                getPreferredLanguage(), allowNullChoice));
        
        return result;
    }
    
}
