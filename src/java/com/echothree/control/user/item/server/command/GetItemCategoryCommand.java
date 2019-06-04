// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

import com.echothree.control.user.item.common.form.GetItemCategoryForm;
import com.echothree.control.user.item.common.result.GetItemCategoryResult;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.control.item.server.logic.ItemCategoryLogic;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.item.server.entity.ItemCategory;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetItemCategoryCommand
        extends BaseSingleEntityCommand<ItemCategory, GetItemCategoryForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ItemCategory.name(), SecurityRoles.Review.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemCategoryName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Key", FieldType.KEY, false, null, null),
                new FieldDefinition("Guid", FieldType.GUID, false, null, null),
                new FieldDefinition("Ulid", FieldType.ULID, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetItemCategoryCommand */
    public GetItemCategoryCommand(UserVisitPK userVisitPK, GetItemCategoryForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected ItemCategory getEntity() {
        var itemControl = (ItemControl)Session.getModelController(ItemControl.class);
        ItemCategory itemCategory = null;
        String itemCategoryName = form.getItemCategoryName();
        int parameterCount = (itemCategoryName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(form);

        if(parameterCount == 1) {
            if(itemCategoryName == null) {
                EntityInstance entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(this, form,
                        ComponentVendors.ECHOTHREE.name(), EntityTypes.ItemCategory.name());

                if(!hasExecutionErrors()) {
                    itemCategory = itemControl.getItemCategoryByEntityInstance(entityInstance);
                }
            } else {
                itemCategory = ItemCategoryLogic.getInstance().getItemCategoryByName(this, itemCategoryName);
            }

            if(itemCategory != null) {
                sendEventUsingNames(itemCategory.getPrimaryKey(), EventTypes.READ.name(), null, null, getPartyPK());
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return itemCategory;
    }
    
    @Override
    protected BaseResult getTransfer(ItemCategory itemCategory) {
        var itemControl = (ItemControl)Session.getModelController(ItemControl.class);
        GetItemCategoryResult result = ItemResultFactory.getGetItemCategoryResult();

        if(itemCategory != null) {
            result.setItemCategory(itemControl.getItemCategoryTransfer(getUserVisit(), itemCategory));
        }

        return result;
    }
    
}
