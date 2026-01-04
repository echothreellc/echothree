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

import com.echothree.control.user.vendor.common.edit.ItemPurchasingCategoryDescriptionEdit;
import com.echothree.control.user.vendor.common.edit.VendorEditFactory;
import com.echothree.control.user.vendor.common.form.EditItemPurchasingCategoryDescriptionForm;
import com.echothree.control.user.vendor.common.result.VendorResultFactory;
import com.echothree.control.user.vendor.common.spec.ItemPurchasingCategoryDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.vendor.server.control.VendorControl;
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
import javax.enterprise.context.Dependent;

@Dependent
public class EditItemPurchasingCategoryDescriptionCommand
        extends BaseEditCommand<ItemPurchasingCategoryDescriptionSpec, ItemPurchasingCategoryDescriptionEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.ItemPurchasingCategory.name(), SecurityRoles.Description.name())
                    )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemPurchasingCategoryName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditItemPurchasingCategoryDescriptionCommand */
    public EditItemPurchasingCategoryDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var vendorControl = Session.getModelController(VendorControl.class);
        var result = VendorResultFactory.getEditItemPurchasingCategoryDescriptionResult();
        var itemPurchasingCategoryName = spec.getItemPurchasingCategoryName();
        var itemPurchasingCategory = vendorControl.getItemPurchasingCategoryByName(itemPurchasingCategoryName);
        
        if(itemPurchasingCategory != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);
            
            if(language != null) {
                if(editMode.equals(EditMode.LOCK)) {
                    var itemPurchasingCategoryDescription = vendorControl.getItemPurchasingCategoryDescription(itemPurchasingCategory, language);
                    
                    if(itemPurchasingCategoryDescription != null) {
                        result.setItemPurchasingCategoryDescription(vendorControl.getItemPurchasingCategoryDescriptionTransfer(getUserVisit(), itemPurchasingCategoryDescription));
                        
                        if(lockEntity(itemPurchasingCategory)) {
                            var edit = VendorEditFactory.getItemPurchasingCategoryDescriptionEdit();
                            
                            result.setEdit(edit);
                            edit.setDescription(itemPurchasingCategoryDescription.getDescription());
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                        }
                        
                        result.setEntityLock(getEntityLockTransfer(itemPurchasingCategory));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownItemPurchasingCategoryDescription.name());
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    var itemPurchasingCategoryDescriptionValue = vendorControl.getItemPurchasingCategoryDescriptionValueForUpdate(itemPurchasingCategory, language);
                    
                    if(itemPurchasingCategoryDescriptionValue != null) {
                        if(lockEntityForUpdate(itemPurchasingCategory)) {
                            try {
                                var description = edit.getDescription();
                                
                                itemPurchasingCategoryDescriptionValue.setDescription(description);
                                
                                vendorControl.updateItemPurchasingCategoryDescriptionFromValue(itemPurchasingCategoryDescriptionValue, getPartyPK());
                            } finally {
                                unlockEntity(itemPurchasingCategory);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownItemPurchasingCategoryDescription.name());
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownItemPurchasingCategoryName.name(), itemPurchasingCategoryName);
        }
        
        return result;
    }
    
}
