// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.control.user.accounting.server.command;

import com.echothree.control.user.accounting.common.form.DeleteItemAccountingCategoryForm;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.accounting.server.logic.ItemAccountingCategoryLogic;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.accounting.server.entity.ItemAccountingCategory;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DeleteItemAccountingCategoryCommand
        extends BaseSimpleCommand<DeleteItemAccountingCategoryForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ItemAccountingCategory.name(), SecurityRoles.Delete.name())
                        )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemAccountingCategoryName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Key", FieldType.KEY, false, null, null),
                new FieldDefinition("Guid", FieldType.GUID, false, null, null),
                new FieldDefinition("Ulid", FieldType.ULID, false, null, null)
        ));
    }
    
    /** Creates a new instance of DeleteItemAccountingCategoryCommand */
    public DeleteItemAccountingCategoryCommand(UserVisitPK userVisitPK, DeleteItemAccountingCategoryForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        String itemAccountingCategoryName = form.getItemAccountingCategoryName();
        var parameterCount = (itemAccountingCategoryName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(form);

        if(parameterCount == 1) {
            var accountingControl = Session.getModelController(AccountingControl.class);
            ItemAccountingCategory itemAccountingCategory = null;

            if(itemAccountingCategoryName == null) {
                var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(this, form, ComponentVendors.ECHOTHREE.name(),
                        EntityTypes.ItemAccountingCategory.name());

                if(!hasExecutionErrors()) {
                    itemAccountingCategory = accountingControl.getItemAccountingCategoryByEntityInstanceForUpdate(entityInstance);
                }
            } else {
                itemAccountingCategory = ItemAccountingCategoryLogic.getInstance().getItemAccountingCategoryByNameForUpdate(this, itemAccountingCategoryName);
            }

            if(!hasExecutionErrors()) {
                ItemAccountingCategoryLogic itemAccountingCategoryLogic = ItemAccountingCategoryLogic.getInstance();

                itemAccountingCategoryLogic.checkDeleteItemAccountingCategory(this, itemAccountingCategory);

                if(!hasExecutionErrors()) {
                    itemAccountingCategoryLogic.deleteItemAccountingCategory(itemAccountingCategory, getPartyPK());
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }

        return null;
    }
    
}
