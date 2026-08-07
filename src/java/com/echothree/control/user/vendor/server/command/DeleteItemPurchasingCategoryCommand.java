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

package com.echothree.control.user.vendor.server.command;

import com.echothree.control.user.vendor.common.form.DeleteItemPurchasingCategoryForm;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.control.vendor.server.logic.ItemPurchasingCategoryLogic;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.vendor.server.entity.ItemPurchasingCategory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DeleteItemPurchasingCategoryCommand
        extends BaseSimpleCommand<DeleteItemPurchasingCategoryForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.ItemPurchasingCategory.name(), SecurityRoles.Delete.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ItemPurchasingCategoryName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );
    }

    @Inject
    VendorControl vendorControl;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    @Inject
    ItemPurchasingCategoryLogic itemPurchasingCategoryLogic;

    
    /** Creates a new instance of DeleteItemPurchasingCategoryCommand */
    public DeleteItemPurchasingCategoryCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var itemPurchasingCategoryName = form.getItemPurchasingCategoryName();
        var parameterCount = (itemPurchasingCategoryName == null ? 0 : 1) + entityInstanceLogic.countPossibleEntitySpecs(form);

        if(parameterCount == 1) {
            ItemPurchasingCategory itemPurchasingCategory = null;

            if(itemPurchasingCategoryName == null) {
                var entityInstance = entityInstanceLogic.getEntityInstance(this, form, ComponentVendors.ECHO_THREE.name(),
                        EntityTypes.ItemPurchasingCategory.name());

                if(!hasExecutionErrors()) {
                    itemPurchasingCategory = vendorControl.getItemPurchasingCategoryByEntityInstanceForUpdate(entityInstance);
                }
            } else {
                itemPurchasingCategory = itemPurchasingCategoryLogic.getItemPurchasingCategoryByNameForUpdate(this, itemPurchasingCategoryName);
            }

            if(!hasExecutionErrors()) {
                itemPurchasingCategoryLogic.checkDeleteItemPurchasingCategory(this, itemPurchasingCategory);

                if(!hasExecutionErrors()) {
                    itemPurchasingCategoryLogic.deleteItemPurchasingCategory(itemPurchasingCategory, getPartyPK());
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }

        return null;
    }
    
}
