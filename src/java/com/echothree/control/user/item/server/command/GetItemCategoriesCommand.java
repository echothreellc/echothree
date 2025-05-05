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

package com.echothree.control.user.item.server.command;

import com.echothree.control.user.item.common.form.GetItemCategoriesForm;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.item.server.entity.ItemCategory;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GetItemCategoriesCommand
        extends BaseMultipleEntitiesCommand<ItemCategory, GetItemCategoriesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ItemCategory.name(), SecurityRoles.List.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ParentItemCategoryName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetItemCategoriesCommand */
    public GetItemCategoriesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    private ItemCategory parentItemCategory;
    
    @Override
    protected Collection<ItemCategory> getEntities() {
        var itemControl = Session.getModelController(ItemControl.class);
        var parentItemCategoryName = form.getParentItemCategoryName();
        Collection<ItemCategory> itemCategories = null;
        
        parentItemCategory = parentItemCategoryName == null? null: itemControl.getItemCategoryByName(parentItemCategoryName);

        if(parentItemCategoryName == null || parentItemCategory != null) {
            itemCategories = parentItemCategory == null ? itemControl.getItemCategories()
                    : itemControl.getItemCategoriesByParentItemCategory(parentItemCategory);
        } else {
            addExecutionError(ExecutionErrors.UnknownParentItemCategoryName.name(), parentItemCategoryName);
        }
        
        return itemCategories;
    }
    
    @Override
    protected BaseResult getResult(Collection<ItemCategory> entities) {
        var result = ItemResultFactory.getGetItemCategoriesResult();
        var itemControl = Session.getModelController(ItemControl.class);
        var userVisit = getUserVisit();
        
        result.setParentItemCategory(parentItemCategory == null ? null : itemControl.getItemCategoryTransfer(userVisit, parentItemCategory));
        result.setItemCategories(itemControl.getItemCategoryTransfers(userVisit, entities));
        
        return result;
    }
    
}
