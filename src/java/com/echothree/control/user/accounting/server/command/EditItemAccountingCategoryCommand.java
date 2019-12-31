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

package com.echothree.control.user.accounting.server.command;

import com.echothree.control.user.accounting.common.edit.AccountingEditFactory;
import com.echothree.control.user.accounting.common.edit.ItemAccountingCategoryEdit;
import com.echothree.control.user.accounting.common.form.EditItemAccountingCategoryForm;
import com.echothree.control.user.accounting.common.result.AccountingResultFactory;
import com.echothree.control.user.accounting.common.result.EditItemAccountingCategoryResult;
import com.echothree.control.user.accounting.common.spec.ItemAccountingCategorySpec;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.accounting.server.entity.ItemAccountingCategory;
import com.echothree.model.data.accounting.server.entity.ItemAccountingCategoryDescription;
import com.echothree.model.data.accounting.server.entity.ItemAccountingCategoryDetail;
import com.echothree.model.data.accounting.server.value.ItemAccountingCategoryDescriptionValue;
import com.echothree.model.data.accounting.server.value.ItemAccountingCategoryDetailValue;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.user.common.pk.UserVisitPK;
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

public class EditItemAccountingCategoryCommand
        extends BaseEditCommand<ItemAccountingCategorySpec, ItemAccountingCategoryEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.ItemAccountingCategory.name(), SecurityRoles.Edit.name())
                    )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemAccountingCategoryName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemAccountingCategoryName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ParentItemAccountingCategoryName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditItemAccountingCategoryCommand */
    public EditItemAccountingCategoryCommand(UserVisitPK userVisitPK, EditItemAccountingCategoryForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
        EditItemAccountingCategoryResult result = AccountingResultFactory.getEditItemAccountingCategoryResult();
        
        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            String itemAccountingCategoryName = spec.getItemAccountingCategoryName();
            ItemAccountingCategory itemAccountingCategory = accountingControl.getItemAccountingCategoryByName(itemAccountingCategoryName);
            
            if(itemAccountingCategory != null) {
                if(editMode.equals(EditMode.LOCK)) {
                    if(lockEntity(itemAccountingCategory)) {
                        ItemAccountingCategoryDescription itemAccountingCategoryDescription = accountingControl.getItemAccountingCategoryDescription(itemAccountingCategory, getPreferredLanguage());
                        ItemAccountingCategoryEdit edit = AccountingEditFactory.getItemAccountingCategoryEdit();
                        ItemAccountingCategoryDetail itemAccountingCategoryDetail = itemAccountingCategory.getLastDetail();
                        ItemAccountingCategory parentItemAccountingCategory = itemAccountingCategoryDetail.getParentItemAccountingCategory();

                        result.setItemAccountingCategory(accountingControl.getItemAccountingCategoryTransfer(getUserVisit(), itemAccountingCategory));
                        sendEventUsingNames(itemAccountingCategory.getPrimaryKey(), EventTypes.READ.name(), null, null, getPartyPK());

                        result.setEdit(edit);
                        edit.setItemAccountingCategoryName(itemAccountingCategoryDetail.getItemAccountingCategoryName());
                        edit.setParentItemAccountingCategoryName(parentItemAccountingCategory == null? null: parentItemAccountingCategory.getLastDetail().getItemAccountingCategoryName());
                        edit.setIsDefault(itemAccountingCategoryDetail.getIsDefault().toString());
                        edit.setSortOrder(itemAccountingCategoryDetail.getSortOrder().toString());

                        if(itemAccountingCategoryDescription != null) {
                            edit.setDescription(itemAccountingCategoryDescription.getDescription());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.EntityLockFailed.name());
                    }

                    result.setEntityLock(getEntityLockTransfer(itemAccountingCategory));
                } else { // EditMode.ABANDON
                    unlockEntity(itemAccountingCategory);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownItemAccountingCategoryName.name(), itemAccountingCategoryName);
            }
        } else if(editMode.equals(EditMode.UPDATE)) {
            String itemAccountingCategoryName = spec.getItemAccountingCategoryName();
            ItemAccountingCategory itemAccountingCategory = accountingControl.getItemAccountingCategoryByNameForUpdate(itemAccountingCategoryName);
            
            if(itemAccountingCategory != null) {
                itemAccountingCategoryName = edit.getItemAccountingCategoryName();
                ItemAccountingCategory duplicateItemAccountingCategory = accountingControl.getItemAccountingCategoryByName(itemAccountingCategoryName);
                
                if(duplicateItemAccountingCategory == null || itemAccountingCategory.equals(duplicateItemAccountingCategory)) {
                    String parentItemAccountingCategoryName = edit.getParentItemAccountingCategoryName();
                    ItemAccountingCategory parentItemAccountingCategory = null;
                    
                    if(parentItemAccountingCategoryName != null) {
                        parentItemAccountingCategory = accountingControl.getItemAccountingCategoryByName(parentItemAccountingCategoryName);
                    }
                    
                    if(parentItemAccountingCategoryName == null || parentItemAccountingCategory != null) {
                        if(accountingControl.isParentItemAccountingCategorySafe(itemAccountingCategory, parentItemAccountingCategory)) {
                            if(lockEntityForUpdate(itemAccountingCategory)) {
                                try {
                                    PartyPK partyPK = getPartyPK();
                                    ItemAccountingCategoryDetailValue itemAccountingCategoryDetailValue = accountingControl.getItemAccountingCategoryDetailValueForUpdate(itemAccountingCategory);
                                    ItemAccountingCategoryDescription itemAccountingCategoryDescription = accountingControl.getItemAccountingCategoryDescriptionForUpdate(itemAccountingCategory, getPreferredLanguage());
                                    String description = edit.getDescription();
                                    
                                    itemAccountingCategoryDetailValue.setItemAccountingCategoryName(edit.getItemAccountingCategoryName());
                                    itemAccountingCategoryDetailValue.setParentItemAccountingCategoryPK(parentItemAccountingCategory == null? null: parentItemAccountingCategory.getPrimaryKey());
                                    itemAccountingCategoryDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                                    itemAccountingCategoryDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                                    
                                    accountingControl.updateItemAccountingCategoryFromValue(itemAccountingCategoryDetailValue, partyPK);
                                    
                                    if(itemAccountingCategoryDescription == null && description != null) {
                                        accountingControl.createItemAccountingCategoryDescription(itemAccountingCategory, getPreferredLanguage(), description, partyPK);
                                    } else if(itemAccountingCategoryDescription != null && description == null) {
                                        accountingControl.deleteItemAccountingCategoryDescription(itemAccountingCategoryDescription, partyPK);
                                    } else if(itemAccountingCategoryDescription != null && description != null) {
                                        ItemAccountingCategoryDescriptionValue itemAccountingCategoryDescriptionValue = accountingControl.getItemAccountingCategoryDescriptionValue(itemAccountingCategoryDescription);
                                        
                                        itemAccountingCategoryDescriptionValue.setDescription(description);
                                        accountingControl.updateItemAccountingCategoryDescriptionFromValue(itemAccountingCategoryDescriptionValue, partyPK);
                                    }
                                } finally {
                                    unlockEntity(itemAccountingCategory);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockStale.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.InvalidParentItemAccountingCategory.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownParentItemAccountingCategoryName.name(), parentItemAccountingCategoryName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.DuplicateItemAccountingCategoryName.name(), itemAccountingCategoryName);
                }
            
                if(hasExecutionErrors()) {
                    result.setItemAccountingCategory(accountingControl.getItemAccountingCategoryTransfer(getUserVisit(), itemAccountingCategory));
                    result.setEntityLock(getEntityLockTransfer(itemAccountingCategory));
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownItemAccountingCategoryName.name(), itemAccountingCategoryName);
            }
        }
        
        return result;
    }
    
}
