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

import com.echothree.control.user.vendor.common.form.CreateItemPurchasingCategoryForm;
import com.echothree.control.user.vendor.common.result.VendorResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.vendor.server.control.VendorControl;
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
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class CreateItemPurchasingCategoryCommand
        extends BaseSimpleCommand<CreateItemPurchasingCategoryForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ItemPurchasingCategory.name(), SecurityRoles.Create.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemPurchasingCategoryName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ParentItemPurchasingCategoryName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of CreateItemPurchasingCategoryCommand */
    public CreateItemPurchasingCategoryCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = VendorResultFactory.getCreateItemPurchasingCategoryResult();
        var vendorControl = Session.getModelController(VendorControl.class);
        var itemPurchasingCategoryName = form.getItemPurchasingCategoryName();
        var itemPurchasingCategory = vendorControl.getItemPurchasingCategoryByName(itemPurchasingCategoryName);
        
        if(itemPurchasingCategory == null) {
            var parentItemPurchasingCategoryName = form.getParentItemPurchasingCategoryName();
            ItemPurchasingCategory parentItemPurchasingCategory = null;
            
            if(parentItemPurchasingCategoryName != null) {
                parentItemPurchasingCategory = vendorControl.getItemPurchasingCategoryByName(parentItemPurchasingCategoryName);
            }
            
            if(parentItemPurchasingCategoryName == null || parentItemPurchasingCategory != null) {
                var partyPK = getPartyPK();
                var isDefault = Boolean.valueOf(form.getIsDefault());
                var sortOrder = Integer.valueOf(form.getSortOrder());
                var description = form.getDescription();
                
                itemPurchasingCategory = vendorControl.createItemPurchasingCategory(itemPurchasingCategoryName,
                        parentItemPurchasingCategory, isDefault, sortOrder, partyPK);
                
                if(description != null) {
                    vendorControl.createItemPurchasingCategoryDescription(itemPurchasingCategory, getPreferredLanguage(),
                            description, partyPK);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownParentItemPurchasingCategoryName.name(), parentItemPurchasingCategoryName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateItemPurchasingCategoryName.name(), itemPurchasingCategoryName);
        }

        if(itemPurchasingCategory != null) {
            result.setItemPurchasingCategoryName(itemPurchasingCategory.getLastDetail().getItemPurchasingCategoryName());
            result.setEntityRef(itemPurchasingCategory.getPrimaryKey().getEntityRef());
        }

        return result;
    }
    
}
