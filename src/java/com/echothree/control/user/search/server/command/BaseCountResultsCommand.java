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

package com.echothree.control.user.search.server.command;

import com.echothree.control.user.search.common.form.BaseCountResultsForm;
import com.echothree.control.user.search.common.result.BaseCountResultsResult;
import com.echothree.model.control.search.server.logic.SearchLogic;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class BaseCountResultsCommand<F extends BaseCountResultsForm, R extends BaseCountResultsResult>
        extends BaseSimpleCommand<F> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SearchTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }

    /** Creates a new instance of BaseCountResultsCommand */
    protected BaseCountResultsCommand(CommandSecurityDefinition COMMAND_SECURITY_DEFINITION) {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    protected BaseResult execute(String searchKindName, BaseCountResultsResult result) {
        result.setCount(SearchLogic.getInstance().countUserVisitSearchResults(this, getUserVisit(), searchKindName, form.getSearchTypeName()));

        return result;
    }
    
    @Override
    protected abstract BaseResult execute();
    
}
