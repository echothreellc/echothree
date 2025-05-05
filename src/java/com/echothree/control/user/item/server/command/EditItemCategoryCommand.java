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

import com.echothree.control.user.item.common.edit.ItemCategoryEdit;
import com.echothree.control.user.item.common.edit.ItemEditFactory;
import com.echothree.control.user.item.common.form.EditItemCategoryForm;
import com.echothree.control.user.item.common.result.EditItemCategoryResult;
import com.echothree.control.user.item.common.result.ItemResultFactory;
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
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import com.echothree.control.user.item.common.spec.ItemCategoryUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;

public class EditItemCategoryCommand
        extends BaseAbstractEditCommand<ItemCategoryUniversalSpec, ItemCategoryEdit, EditItemCategoryResult, ItemCategory, ItemCategory> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ItemCategory.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemCategoryName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemCategoryName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ParentItemCategoryName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditItemCategoryCommand */
    public EditItemCategoryCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditItemCategoryResult getResult() {
        return ItemResultFactory.getEditItemCategoryResult();
    }
    
    @Override
    public ItemCategoryEdit getEdit() {
        return ItemEditFactory.getItemCategoryEdit();
    }
    
    @Override
    public ItemCategory getEntity(EditItemCategoryResult result) {
        var itemControl = Session.getModelController(ItemControl.class);
        ItemCategory itemCategory = null;
        var itemCategoryName = spec.getItemCategoryName();
        var parameterCount = (itemCategoryName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(spec);

        if(parameterCount == 1) {
            if(itemCategoryName == null) {
                var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(this, spec, ComponentVendors.ECHO_THREE.name(),
                        EntityTypes.ItemCategory.name());

                if(!hasExecutionErrors()) {
                    itemCategory = itemControl.getItemCategoryByEntityInstance(entityInstance, editModeToEntityPermission(editMode));
                }
            } else {
                itemCategory = ItemCategoryLogic.getInstance().getItemCategoryByName(this, itemCategoryName, editModeToEntityPermission(editMode));
            }

            if(itemCategory != null) {
                result.setItemCategory(itemControl.getItemCategoryTransfer(getUserVisit(), itemCategory));
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }

        return itemCategory;
    }
    
    @Override
    public ItemCategory getLockEntity(ItemCategory itemCategory) {
        return itemCategory;
    }
    
    @Override
    public void fillInResult(EditItemCategoryResult result, ItemCategory itemCategory) {
        var itemControl = Session.getModelController(ItemControl.class);
        
        result.setItemCategory(itemControl.getItemCategoryTransfer(getUserVisit(), itemCategory));
    }
    
    ItemCategory parentItemCategory = null;
    
    @Override
    public void doLock(ItemCategoryEdit edit, ItemCategory itemCategory) {
        var itemControl = Session.getModelController(ItemControl.class);
        var itemCategoryDescription = itemControl.getItemCategoryDescription(itemCategory, getPreferredLanguage());
        var itemCategoryDetail = itemCategory.getLastDetail();
        
        parentItemCategory = itemCategoryDetail.getParentItemCategory();
        
        edit.setItemCategoryName(itemCategoryDetail.getItemCategoryName());
        edit.setParentItemCategoryName(parentItemCategory == null? null: parentItemCategory.getLastDetail().getItemCategoryName());
        edit.setIsDefault(itemCategoryDetail.getIsDefault().toString());
        edit.setSortOrder(itemCategoryDetail.getSortOrder().toString());

        if(itemCategoryDescription != null) {
            edit.setDescription(itemCategoryDescription.getDescription());
        }
    }
        
    @Override
    public void canUpdate(ItemCategory itemCategory) {
        var itemControl = Session.getModelController(ItemControl.class);
        var itemCategoryName = edit.getItemCategoryName();
        var duplicateItemCategory = itemControl.getItemCategoryByName(itemCategoryName);

        if(duplicateItemCategory == null || itemCategory.equals(duplicateItemCategory)) {
            var parentItemCategoryName = edit.getParentItemCategoryName();
            
            parentItemCategory = parentItemCategoryName == null? null: itemControl.getItemCategoryByName(parentItemCategoryName);

            if(parentItemCategoryName == null || parentItemCategory != null) {
                if(parentItemCategory != null) {
                    if(!itemControl.isParentItemCategorySafe(itemCategory, parentItemCategory)) {
                        addExecutionError(ExecutionErrors.InvalidParentItemCategory.name());
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownParentItemCategoryName.name(), parentItemCategoryName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateItemCategoryName.name(), itemCategoryName);
        }
    }
    
    @Override
    public void doUpdate(ItemCategory itemCategory) {
        var itemControl = Session.getModelController(ItemControl.class);
        var partyPK = getPartyPK();
        var itemCategoryDetailValue = itemControl.getItemCategoryDetailValueForUpdate(itemCategory);
        var itemCategoryDescription = itemControl.getItemCategoryDescriptionForUpdate(itemCategory, getPreferredLanguage());
        var description = edit.getDescription();

        itemCategoryDetailValue.setItemCategoryName(edit.getItemCategoryName());
        itemCategoryDetailValue.setParentItemCategoryPK(parentItemCategory == null? null: parentItemCategory.getPrimaryKey());
        itemCategoryDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        itemCategoryDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        itemControl.updateItemCategoryFromValue(itemCategoryDetailValue, partyPK);

        if(itemCategoryDescription == null && description != null) {
            itemControl.createItemCategoryDescription(itemCategory, getPreferredLanguage(), description, partyPK);
        } else if(itemCategoryDescription != null && description == null) {
            itemControl.deleteItemCategoryDescription(itemCategoryDescription, partyPK);
        } else if(itemCategoryDescription != null && description != null) {
            var itemCategoryDescriptionValue = itemControl.getItemCategoryDescriptionValue(itemCategoryDescription);

            itemCategoryDescriptionValue.setDescription(description);
            itemControl.updateItemCategoryDescriptionFromValue(itemCategoryDescriptionValue, partyPK);
        }
    }
    
}
