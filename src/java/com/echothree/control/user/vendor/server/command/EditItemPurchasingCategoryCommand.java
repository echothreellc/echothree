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

import com.echothree.control.user.vendor.common.edit.ItemPurchasingCategoryEdit;
import com.echothree.control.user.vendor.common.edit.VendorEditFactory;
import com.echothree.control.user.vendor.common.form.EditItemPurchasingCategoryForm;
import com.echothree.control.user.vendor.common.result.EditItemPurchasingCategoryResult;
import com.echothree.control.user.vendor.common.result.VendorResultFactory;
import com.echothree.control.user.vendor.common.spec.ItemPurchasingCategoryUniversalSpec;
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
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditItemPurchasingCategoryCommand
        extends BaseAbstractEditCommand<ItemPurchasingCategoryUniversalSpec, ItemPurchasingCategoryEdit, EditItemPurchasingCategoryResult, ItemPurchasingCategory, ItemPurchasingCategory> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.ItemPurchasingCategory.name(), SecurityRoles.Edit.name())
                    )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemPurchasingCategoryName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemPurchasingCategoryName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ParentItemPurchasingCategoryName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditItemPurchasingCategoryCommand */
    public EditItemPurchasingCategoryCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditItemPurchasingCategoryResult getResult() {
        return VendorResultFactory.getEditItemPurchasingCategoryResult();
    }

    @Override
    public ItemPurchasingCategoryEdit getEdit() {
        return VendorEditFactory.getItemPurchasingCategoryEdit();
    }

    @Override
    public ItemPurchasingCategory getEntity(EditItemPurchasingCategoryResult result) {
        var vendorControl = Session.getModelController(VendorControl.class);
        ItemPurchasingCategory itemPurchasingCategory = null;
        var itemPurchasingCategoryName = spec.getItemPurchasingCategoryName();
        var parameterCount = (itemPurchasingCategoryName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(spec);

        if(parameterCount == 1) {
            if(itemPurchasingCategoryName == null) {
                var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(this, spec, ComponentVendors.ECHO_THREE.name(),
                        EntityTypes.ItemPurchasingCategory.name());

                if(!hasExecutionErrors()) {
                    itemPurchasingCategory = vendorControl.getItemPurchasingCategoryByEntityInstance(entityInstance, editModeToEntityPermission(editMode));
                }
            } else {
                itemPurchasingCategory = ItemPurchasingCategoryLogic.getInstance().getItemPurchasingCategoryByName(this, itemPurchasingCategoryName, editModeToEntityPermission(editMode));
            }

            if(itemPurchasingCategory != null) {
                result.setItemPurchasingCategory(vendorControl.getItemPurchasingCategoryTransfer(getUserVisit(), itemPurchasingCategory));
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }

        return itemPurchasingCategory;
    }

    @Override
    public ItemPurchasingCategory getLockEntity(ItemPurchasingCategory itemPurchasingCategory) {
        return itemPurchasingCategory;
    }

    @Override
    public void fillInResult(EditItemPurchasingCategoryResult result, ItemPurchasingCategory itemPurchasingCategory) {
        var vendorControl = Session.getModelController(VendorControl.class);

        result.setItemPurchasingCategory(vendorControl.getItemPurchasingCategoryTransfer(getUserVisit(), itemPurchasingCategory));
    }

    ItemPurchasingCategory parentItemPurchasingCategory = null;

    @Override
    public void doLock(ItemPurchasingCategoryEdit edit, ItemPurchasingCategory itemPurchasingCategory) {
        var vendorControl = Session.getModelController(VendorControl.class);
        var itemPurchasingCategoryDescription = vendorControl.getItemPurchasingCategoryDescription(itemPurchasingCategory, getPreferredLanguage());
        var itemPurchasingCategoryDetail = itemPurchasingCategory.getLastDetail();

        parentItemPurchasingCategory = itemPurchasingCategoryDetail.getParentItemPurchasingCategory();

        edit.setItemPurchasingCategoryName(itemPurchasingCategoryDetail.getItemPurchasingCategoryName());
        edit.setParentItemPurchasingCategoryName(parentItemPurchasingCategory == null? null: parentItemPurchasingCategory.getLastDetail().getItemPurchasingCategoryName());
        edit.setIsDefault(itemPurchasingCategoryDetail.getIsDefault().toString());
        edit.setSortOrder(itemPurchasingCategoryDetail.getSortOrder().toString());

        if(itemPurchasingCategoryDescription != null) {
            edit.setDescription(itemPurchasingCategoryDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(ItemPurchasingCategory itemPurchasingCategory) {
        var vendorControl = Session.getModelController(VendorControl.class);
        var itemPurchasingCategoryName = edit.getItemPurchasingCategoryName();
        var duplicateItemPurchasingCategory = vendorControl.getItemPurchasingCategoryByName(itemPurchasingCategoryName);

        if(duplicateItemPurchasingCategory == null || itemPurchasingCategory.equals(duplicateItemPurchasingCategory)) {
            var parentItemPurchasingCategoryName = edit.getParentItemPurchasingCategoryName();

            parentItemPurchasingCategory = parentItemPurchasingCategoryName == null? null: vendorControl.getItemPurchasingCategoryByName(parentItemPurchasingCategoryName);

            if(parentItemPurchasingCategoryName == null || parentItemPurchasingCategory != null) {
                if(parentItemPurchasingCategory != null) {
                    if(!vendorControl.isParentItemPurchasingCategorySafe(itemPurchasingCategory, parentItemPurchasingCategory)) {
                        addExecutionError(ExecutionErrors.InvalidParentItemPurchasingCategory.name());
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownParentItemPurchasingCategoryName.name(), parentItemPurchasingCategoryName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateItemPurchasingCategoryName.name(), itemPurchasingCategoryName);
        }
    }

    @Override
    public void doUpdate(ItemPurchasingCategory itemPurchasingCategory) {
        var vendorControl = Session.getModelController(VendorControl.class);
        var partyPK = getPartyPK();
        var itemPurchasingCategoryDetailValue = vendorControl.getItemPurchasingCategoryDetailValueForUpdate(itemPurchasingCategory);
        var itemPurchasingCategoryDescription = vendorControl.getItemPurchasingCategoryDescriptionForUpdate(itemPurchasingCategory, getPreferredLanguage());
        var description = edit.getDescription();

        itemPurchasingCategoryDetailValue.setItemPurchasingCategoryName(edit.getItemPurchasingCategoryName());
        itemPurchasingCategoryDetailValue.setParentItemPurchasingCategoryPK(parentItemPurchasingCategory == null? null: parentItemPurchasingCategory.getPrimaryKey());
        itemPurchasingCategoryDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        itemPurchasingCategoryDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        vendorControl.updateItemPurchasingCategoryFromValue(itemPurchasingCategoryDetailValue, partyPK);

        if(itemPurchasingCategoryDescription == null && description != null) {
            vendorControl.createItemPurchasingCategoryDescription(itemPurchasingCategory, getPreferredLanguage(), description, partyPK);
        } else if(itemPurchasingCategoryDescription != null && description == null) {
            vendorControl.deleteItemPurchasingCategoryDescription(itemPurchasingCategoryDescription, partyPK);
        } else if(itemPurchasingCategoryDescription != null && description != null) {
            var itemPurchasingCategoryDescriptionValue = vendorControl.getItemPurchasingCategoryDescriptionValue(itemPurchasingCategoryDescription);

            itemPurchasingCategoryDescriptionValue.setDescription(description);
            vendorControl.updateItemPurchasingCategoryDescriptionFromValue(itemPurchasingCategoryDescriptionValue, partyPK);
        }
    }
    
}
