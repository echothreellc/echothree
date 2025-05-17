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

package com.echothree.control.user.vendor.server.command;

import com.echothree.control.user.vendor.common.form.GetItemPurchasingCategoriesForm;
import com.echothree.control.user.vendor.common.result.VendorResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.vendor.server.entity.ItemPurchasingCategory;
import com.echothree.model.data.vendor.server.factory.ItemPurchasingCategoryFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GetItemPurchasingCategoriesCommand
        extends BaseMultipleEntitiesCommand<ItemPurchasingCategory, GetItemPurchasingCategoriesForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ItemPurchasingCategory.name(), SecurityRoles.List.name())
                )))
        )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
        ));
    }

    /** Creates a new instance of GetItemPurchasingCategoriesCommand */
    public GetItemPurchasingCategoriesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected Collection<ItemPurchasingCategory> getEntities() {
        var vendorControl = Session.getModelController(VendorControl.class);

        return vendorControl.getItemPurchasingCategories();
    }

    @Override
    protected BaseResult getResult(Collection<ItemPurchasingCategory> entities) {
        var result = VendorResultFactory.getGetItemPurchasingCategoriesResult();

        if(entities != null) {
            var vendorControl = Session.getModelController(VendorControl.class);

            if(session.hasLimit(ItemPurchasingCategoryFactory.class)) {
                result.setItemPurchasingCategoryCount(vendorControl.countItemPurchasingCategories());
            }

            result.setItemPurchasingCategories(vendorControl.getItemPurchasingCategoryTransfers(getUserVisit(), entities));
        }

        return result;
    }

}
