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

package com.echothree.control.user.search.server.command;

import com.echothree.control.user.search.common.form.BaseGetResultsFacetForm;
import com.echothree.control.user.search.common.result.BaseGetResultsFacetResult;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.server.logic.EntityAttributeLogic;
import com.echothree.model.control.core.server.logic.EntityTypeLogic;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.control.search.server.logic.SearchLogic;
import com.echothree.model.control.search.server.logic.UserVisitSearchFacetLogic;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.search.server.entity.SearchType;
import com.echothree.model.data.search.server.entity.UserVisitSearch;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class BaseGetResultsFacetCommand<F extends BaseGetResultsFacetForm, R extends BaseGetResultsFacetResult>
        extends BaseSimpleCommand<F> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SearchTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityAttributeName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }

    /** Creates a new instance of BaseGetResultsFacetCommand */
    protected BaseGetResultsFacetCommand(UserVisitPK userVisitPK, F form, CommandSecurityDefinition COMMAND_SECURITY_DEFINITION) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    protected BaseResult execute(String searchKindName, BaseGetResultsFacetResult result) {
        var searchControl = Session.getModelController(SearchControl.class);
        SearchType searchType = SearchLogic.getInstance().getSearchTypeByName(this, searchKindName, form.getSearchTypeName());

        if(!hasExecutionErrors()) {
            UserVisit userVisit = getUserVisit();
            UserVisitSearch userVisitSearch = searchControl.getUserVisitSearch(userVisit, searchType);

            if(userVisitSearch != null) {
                EntityType entityType = EntityTypeLogic.getInstance().getEntityTypeByName(this, ComponentVendors.ECHOTHREE.name(), EntityTypes.Item.name());
                
                if(!hasExecutionErrors()) {
                    EntityAttribute entityAttribute = EntityAttributeLogic.getInstance().getEntityAttributeByName(this, entityType, form.getEntityAttributeName());
                    
                    if(!hasExecutionErrors()) {
                        result.setUserVisitSearchFacet(UserVisitSearchFacetLogic.getInstance().getUserVisitSearchFacetTransfer(this, userVisitSearch, entityAttribute));
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownUserVisitSearch.name());
            }
        }
        
        return result;
    }

    @Override
    protected abstract BaseResult execute();
    
}
