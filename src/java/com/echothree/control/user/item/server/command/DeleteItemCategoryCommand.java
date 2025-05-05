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

import com.echothree.control.user.item.common.form.DeleteItemCategoryForm;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.item.server.logic.ItemCategoryLogic;
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

public class DeleteItemCategoryCommand
        extends BaseSimpleCommand<DeleteItemCategoryForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ItemCategory.name(), SecurityRoles.Delete.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemCategoryName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
                ));
    }
    
    /** Creates a new instance of DeleteItemCategoryCommand */
    public DeleteItemCategoryCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var itemCategoryName = form.getItemCategoryName();
        var parameterCount = (itemCategoryName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(form);

        if(parameterCount == 1) {
            var itemControl = Session.getModelController(ItemControl.class);
            ItemCategory itemCategory = null;
            
            if(itemCategoryName == null) {
                var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(this, form, ComponentVendors.ECHO_THREE.name(),
                        EntityTypes.ItemCategory.name());
                
                if(!hasExecutionErrors()) {
                    itemCategory = itemControl.getItemCategoryByEntityInstanceForUpdate(entityInstance);
                }
            } else {
                itemCategory = ItemCategoryLogic.getInstance().getItemCategoryByNameForUpdate(this, itemCategoryName);
            }
            
            if(!hasExecutionErrors()) {
                var itemCategoryLogic = ItemCategoryLogic.getInstance();

                itemCategoryLogic.checkDeleteItemCategory(this, itemCategory);

                if(!hasExecutionErrors()) {
                    itemCategoryLogic.deleteItemCategory(itemCategory, getPartyPK());
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return null;
    }
    
}
