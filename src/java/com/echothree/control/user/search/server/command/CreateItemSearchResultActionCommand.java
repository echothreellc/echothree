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

package com.echothree.control.user.search.server.command;

import com.echothree.control.user.search.remote.form.CreateItemSearchResultActionForm;
import com.echothree.model.control.item.server.logic.ItemLogic;
import com.echothree.model.control.search.common.SearchConstants;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateItemSearchResultActionCommand
        extends BaseCreateSearchResultActionCommand<CreateItemSearchResultActionForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SearchTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SearchResultActionTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }

    /** Creates a new instance of CreateSearchResultActionTypeCommand */
    public CreateItemSearchResultActionCommand(UserVisitPK userVisitPK, CreateItemSearchResultActionForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        Item item = ItemLogic.getInstance().getItemByName(this, form.getItemName());
        BaseResult baseResult = null;
        
        if(!hasExecutionErrors()) {
            baseResult = super.execute(SearchConstants.SearchKind_ITEM, item);
        }
        
        return baseResult;
    }
    
}
