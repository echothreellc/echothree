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

import com.echothree.control.user.item.common.form.CreateItemCategoryForm;
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
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class CreateItemCategoryCommand
        extends BaseSimpleCommand<CreateItemCategoryForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ItemCategory.name(), SecurityRoles.Create.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemCategoryName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ParentItemCategoryName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of CreateItemCategoryCommand */
    public CreateItemCategoryCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = ItemResultFactory.getCreateItemCategoryResult();
        var itemControl = Session.getModelController(ItemControl.class);
        var itemCategoryName = form.getItemCategoryName();
        var itemCategory = itemControl.getItemCategoryByName(itemCategoryName);
        
        if(itemCategory == null) {
            var parentItemCategoryName = form.getParentItemCategoryName();
            ItemCategory parentItemCategory = null;
            
            if(parentItemCategoryName != null) {
                parentItemCategory = itemControl.getItemCategoryByName(parentItemCategoryName);
            }
            
            if(parentItemCategoryName == null || parentItemCategory != null) {
                var partyPK = getPartyPK();
                var isDefault = Boolean.valueOf(form.getIsDefault());
                var sortOrder = Integer.valueOf(form.getSortOrder());
                var description = form.getDescription();
                
                itemCategory = itemControl.createItemCategory(itemCategoryName, parentItemCategory, null,
                        isDefault, sortOrder, partyPK);
                
                if(description != null) {
                    itemControl.createItemCategoryDescription(itemCategory, getPreferredLanguage(),
                            description, partyPK);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownParentItemCategoryName.name(), parentItemCategoryName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateItemCategoryName.name(), itemCategoryName);
        }
                
        if(itemCategory != null) {
            result.setItemCategoryName(itemCategory.getLastDetail().getItemCategoryName());
            result.setEntityRef(itemCategory.getPrimaryKey().getEntityRef());
        }

        return result;
    }
    
}
