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

package com.echothree.control.user.vendor.server.command;

import com.echothree.control.user.vendor.common.edit.ItemPurchasingCategoryEdit;
import com.echothree.control.user.vendor.common.edit.VendorEditFactory;
import com.echothree.control.user.vendor.common.form.EditItemPurchasingCategoryForm;
import com.echothree.control.user.vendor.common.result.EditItemPurchasingCategoryResult;
import com.echothree.control.user.vendor.common.result.VendorResultFactory;
import com.echothree.control.user.vendor.common.spec.ItemPurchasingCategorySpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.vendor.server.entity.ItemPurchasingCategory;
import com.echothree.model.data.vendor.server.entity.ItemPurchasingCategoryDescription;
import com.echothree.model.data.vendor.server.entity.ItemPurchasingCategoryDetail;
import com.echothree.model.data.vendor.server.value.ItemPurchasingCategoryDescriptionValue;
import com.echothree.model.data.vendor.server.value.ItemPurchasingCategoryDetailValue;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditItemPurchasingCategoryCommand
        extends BaseEditCommand<ItemPurchasingCategorySpec, ItemPurchasingCategoryEdit> {
    
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
                new FieldDefinition("ItemPurchasingCategoryName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemPurchasingCategoryName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ParentItemPurchasingCategoryName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditItemPurchasingCategoryCommand */
    public EditItemPurchasingCategoryCommand(UserVisitPK userVisitPK, EditItemPurchasingCategoryForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var vendorControl = Session.getModelController(VendorControl.class);
        EditItemPurchasingCategoryResult result = VendorResultFactory.getEditItemPurchasingCategoryResult();
        
        if(editMode.equals(EditMode.LOCK)) {
            String itemPurchasingCategoryName = spec.getItemPurchasingCategoryName();
            ItemPurchasingCategory itemPurchasingCategory = vendorControl.getItemPurchasingCategoryByName(itemPurchasingCategoryName);
            
            if(itemPurchasingCategory != null) {
                result.setItemPurchasingCategory(vendorControl.getItemPurchasingCategoryTransfer(getUserVisit(), itemPurchasingCategory));
                
                if(lockEntity(itemPurchasingCategory)) {
                    ItemPurchasingCategoryDescription itemPurchasingCategoryDescription = vendorControl.getItemPurchasingCategoryDescription(itemPurchasingCategory, getPreferredLanguage());
                    ItemPurchasingCategoryEdit edit = VendorEditFactory.getItemPurchasingCategoryEdit();
                    ItemPurchasingCategoryDetail itemPurchasingCategoryDetail = itemPurchasingCategory.getLastDetail();
                    ItemPurchasingCategory parentItemPurchasingCategory = itemPurchasingCategoryDetail.getParentItemPurchasingCategory();
                    
                    result.setEdit(edit);
                    edit.setItemPurchasingCategoryName(itemPurchasingCategoryDetail.getItemPurchasingCategoryName());
                    edit.setParentItemPurchasingCategoryName(parentItemPurchasingCategory == null? null: parentItemPurchasingCategory.getLastDetail().getItemPurchasingCategoryName());
                    edit.setIsDefault(itemPurchasingCategoryDetail.getIsDefault().toString());
                    edit.setSortOrder(itemPurchasingCategoryDetail.getSortOrder().toString());
                    
                    if(itemPurchasingCategoryDescription != null)
                        edit.setDescription(itemPurchasingCategoryDescription.getDescription());
                } else {
                    addExecutionError(ExecutionErrors.EntityLockFailed.name());
                }
                
                result.setEntityLock(getEntityLockTransfer(itemPurchasingCategory));
            } else {
                addExecutionError(ExecutionErrors.UnknownItemPurchasingCategoryName.name(), itemPurchasingCategoryName);
            }
        } else if(editMode.equals(EditMode.UPDATE)) {
            String itemPurchasingCategoryName = spec.getItemPurchasingCategoryName();
            ItemPurchasingCategory itemPurchasingCategory = vendorControl.getItemPurchasingCategoryByNameForUpdate(itemPurchasingCategoryName);
            
            if(itemPurchasingCategory != null) {
                itemPurchasingCategoryName = edit.getItemPurchasingCategoryName();
                ItemPurchasingCategory duplicateItemPurchasingCategory = vendorControl.getItemPurchasingCategoryByName(itemPurchasingCategoryName);
                
                if(duplicateItemPurchasingCategory == null || itemPurchasingCategory.equals(duplicateItemPurchasingCategory)) {
                    String parentItemPurchasingCategoryName = edit.getParentItemPurchasingCategoryName();
                    ItemPurchasingCategory parentItemPurchasingCategory = null;
                    
                    if(parentItemPurchasingCategoryName != null) {
                        parentItemPurchasingCategory = vendorControl.getItemPurchasingCategoryByName(parentItemPurchasingCategoryName);
                    }
                    
                    if(parentItemPurchasingCategoryName == null || parentItemPurchasingCategory != null) {
                        if(vendorControl.isParentItemPurchasingCategorySafe(itemPurchasingCategory, parentItemPurchasingCategory)) {
                            if(lockEntityForUpdate(itemPurchasingCategory)) {
                                try {
                                    var partyPK = getPartyPK();
                                    ItemPurchasingCategoryDetailValue itemPurchasingCategoryDetailValue = vendorControl.getItemPurchasingCategoryDetailValueForUpdate(itemPurchasingCategory);
                                    ItemPurchasingCategoryDescription itemPurchasingCategoryDescription = vendorControl.getItemPurchasingCategoryDescriptionForUpdate(itemPurchasingCategory, getPreferredLanguage());
                                    String description = edit.getDescription();
                                    
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
                                        ItemPurchasingCategoryDescriptionValue itemPurchasingCategoryDescriptionValue = vendorControl.getItemPurchasingCategoryDescriptionValue(itemPurchasingCategoryDescription);
                                        
                                        itemPurchasingCategoryDescriptionValue.setDescription(description);
                                        vendorControl.updateItemPurchasingCategoryDescriptionFromValue(itemPurchasingCategoryDescriptionValue, partyPK);
                                    }
                                } finally {
                                    unlockEntity(itemPurchasingCategory);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockStale.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.InvalidParentItemPurchasingCategory.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownParentItemPurchasingCategoryName.name(), parentItemPurchasingCategoryName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.DuplicateItemPurchasingCategoryName.name(), itemPurchasingCategoryName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownItemPurchasingCategoryName.name(), itemPurchasingCategoryName);
            }
            
            if(hasExecutionErrors()) {
                result.setItemPurchasingCategory(vendorControl.getItemPurchasingCategoryTransfer(getUserVisit(), itemPurchasingCategory));
                result.setEntityLock(getEntityLockTransfer(itemPurchasingCategory));
            }
        }
        
        return result;
    }
    
}
