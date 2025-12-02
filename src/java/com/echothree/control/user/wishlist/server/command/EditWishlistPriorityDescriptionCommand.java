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

package com.echothree.control.user.wishlist.server.command;

import com.echothree.control.user.wishlist.common.edit.WishlistEditFactory;
import com.echothree.control.user.wishlist.common.edit.WishlistPriorityDescriptionEdit;
import com.echothree.control.user.wishlist.common.form.EditWishlistPriorityDescriptionForm;
import com.echothree.control.user.wishlist.common.result.WishlistResultFactory;
import com.echothree.control.user.wishlist.common.spec.WishlistPriorityDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.wishlist.server.control.WishlistControl;
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
public class EditWishlistPriorityDescriptionCommand
        extends BaseEditCommand<WishlistPriorityDescriptionSpec, WishlistPriorityDescriptionEdit> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.WishlistPriority.name(), SecurityRoles.Description.name())
                )))
        )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("WishlistTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("WishlistPriorityName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        ));
    }
    
    /** Creates a new instance of EditWishlistPriorityDescriptionCommand */
    public EditWishlistPriorityDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var wishlistControl = Session.getModelController(WishlistControl.class);
        var result = WishlistResultFactory.getEditWishlistPriorityDescriptionResult();
        var wishlistTypeName = spec.getWishlistTypeName();
        var wishlistType = wishlistControl.getWishlistTypeByName(wishlistTypeName);
        
        if(wishlistType != null) {
            var wishlistPriorityName = spec.getWishlistPriorityName();
            var wishlistPriority = wishlistControl.getWishlistPriorityByName(wishlistType, wishlistPriorityName);
            
            if(wishlistPriority != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                var languageIsoName = spec.getLanguageIsoName();
                var language = partyControl.getLanguageByIsoName(languageIsoName);
                
                if(language != null) {
                    if(editMode.equals(EditMode.LOCK)) {
                        var wishlistPriorityDescription = wishlistControl.getWishlistPriorityDescription(wishlistPriority, language);
                        
                        if(wishlistPriorityDescription != null) {
                            result.setWishlistPriorityDescription(wishlistControl.getWishlistPriorityDescriptionTransfer(getUserVisit(), wishlistPriorityDescription));
                            
                            if(lockEntity(wishlistPriority)) {
                                var edit = WishlistEditFactory.getWishlistPriorityDescriptionEdit();
                                
                                result.setEdit(edit);
                                edit.setDescription(wishlistPriorityDescription.getDescription());
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockFailed.name());
                            }
                            
                            result.setEntityLock(getEntityLockTransfer(wishlistPriority));
                        } else {
                            addExecutionError(ExecutionErrors.UnknownWishlistPriorityDescription.name());
                        }
                    } else if(editMode.equals(EditMode.UPDATE)) {
                        var wishlistPriorityDescriptionValue = wishlistControl.getWishlistPriorityDescriptionValueForUpdate(wishlistPriority, language);
                        
                        if(wishlistPriorityDescriptionValue != null) {
                            if(lockEntityForUpdate(wishlistPriority)) {
                                try {
                                    var description = edit.getDescription();
                                    
                                    wishlistPriorityDescriptionValue.setDescription(description);
                                    
                                    wishlistControl.updateWishlistPriorityDescriptionFromValue(wishlistPriorityDescriptionValue, getPartyPK());
                                } finally {
                                    unlockEntity(wishlistPriority);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockStale.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownWishlistPriorityDescription.name());
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownWishlistPriorityName.name(), wishlistPriorityName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWishlistTypeName.name(), wishlistTypeName);
        }
        
        return result;
    }
    
}
