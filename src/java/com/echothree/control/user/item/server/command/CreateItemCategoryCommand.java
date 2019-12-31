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

import com.echothree.control.user.item.common.form.CreateItemCategoryForm;
import com.echothree.control.user.item.common.result.CreateItemCategoryResult;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.item.server.entity.ItemCategory;
import com.echothree.model.data.party.common.pk.PartyPK;
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

public class CreateItemCategoryCommand
        extends BaseSimpleCommand<CreateItemCategoryForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ItemCategory.name(), SecurityRoles.Create.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemCategoryName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ParentItemCategoryName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of CreateItemCategoryCommand */
    public CreateItemCategoryCommand(UserVisitPK userVisitPK, CreateItemCategoryForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        CreateItemCategoryResult result = ItemResultFactory.getCreateItemCategoryResult();
        var itemControl = (ItemControl)Session.getModelController(ItemControl.class);
        String itemCategoryName = form.getItemCategoryName();
        ItemCategory itemCategory = itemControl.getItemCategoryByName(itemCategoryName);
        
        if(itemCategory == null) {
            String parentItemCategoryName = form.getParentItemCategoryName();
            ItemCategory parentItemCategory = null;
            
            if(parentItemCategoryName != null) {
                parentItemCategory = itemControl.getItemCategoryByName(parentItemCategoryName);
            }
            
            if(parentItemCategoryName == null || parentItemCategory != null) {
                PartyPK partyPK = getPartyPK();
                Boolean isDefault = Boolean.valueOf(form.getIsDefault());
                Integer sortOrder = Integer.valueOf(form.getSortOrder());
                String description = form.getDescription();
                
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
