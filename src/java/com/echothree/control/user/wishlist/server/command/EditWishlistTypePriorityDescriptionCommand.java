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

package com.echothree.control.user.wishlist.server.command;

import com.echothree.control.user.wishlist.common.edit.WishlistEditFactory;
import com.echothree.control.user.wishlist.common.edit.WishlistTypePriorityDescriptionEdit;
import com.echothree.control.user.wishlist.common.form.EditWishlistTypePriorityDescriptionForm;
import com.echothree.control.user.wishlist.common.result.EditWishlistTypePriorityDescriptionResult;
import com.echothree.control.user.wishlist.common.result.WishlistResultFactory;
import com.echothree.control.user.wishlist.common.spec.WishlistTypePriorityDescriptionSpec;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.wishlist.server.control.WishlistControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.wishlist.server.entity.WishlistType;
import com.echothree.model.data.wishlist.server.entity.WishlistTypePriority;
import com.echothree.model.data.wishlist.server.entity.WishlistTypePriorityDescription;
import com.echothree.model.data.wishlist.server.value.WishlistTypePriorityDescriptionValue;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditWishlistTypePriorityDescriptionCommand
        extends BaseEditCommand<WishlistTypePriorityDescriptionSpec, WishlistTypePriorityDescriptionEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("WishlistTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("WishlistTypePriorityName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
        ));
    }
    
    /** Creates a new instance of EditWishlistTypePriorityDescriptionCommand */
    public EditWishlistTypePriorityDescriptionCommand(UserVisitPK userVisitPK, EditWishlistTypePriorityDescriptionForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var wishlistControl = Session.getModelController(WishlistControl.class);
        EditWishlistTypePriorityDescriptionResult result = WishlistResultFactory.getEditWishlistTypePriorityDescriptionResult();
        String wishlistTypeName = spec.getWishlistTypeName();
        WishlistType wishlistType = wishlistControl.getWishlistTypeByName(wishlistTypeName);
        
        if(wishlistType != null) {
            String wishlistTypePriorityName = spec.getWishlistTypePriorityName();
            WishlistTypePriority wishlistTypePriority = wishlistControl.getWishlistTypePriorityByName(wishlistType, wishlistTypePriorityName);
            
            if(wishlistTypePriority != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                String languageIsoName = spec.getLanguageIsoName();
                Language language = partyControl.getLanguageByIsoName(languageIsoName);
                
                if(language != null) {
                    if(editMode.equals(EditMode.LOCK)) {
                        WishlistTypePriorityDescription wishlistTypePriorityDescription = wishlistControl.getWishlistTypePriorityDescription(wishlistTypePriority, language);
                        
                        if(wishlistTypePriorityDescription != null) {
                            result.setWishlistTypePriorityDescription(wishlistControl.getWishlistTypePriorityDescriptionTransfer(getUserVisit(), wishlistTypePriorityDescription));
                            
                            if(lockEntity(wishlistTypePriority)) {
                                WishlistTypePriorityDescriptionEdit edit = WishlistEditFactory.getWishlistTypePriorityDescriptionEdit();
                                
                                result.setEdit(edit);
                                edit.setDescription(wishlistTypePriorityDescription.getDescription());
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockFailed.name());
                            }
                            
                            result.setEntityLock(getEntityLockTransfer(wishlistTypePriority));
                        } else {
                            addExecutionError(ExecutionErrors.UnknownWishlistTypePriorityDescription.name());
                        }
                    } else if(editMode.equals(EditMode.UPDATE)) {
                        WishlistTypePriorityDescriptionValue wishlistTypePriorityDescriptionValue = wishlistControl.getWishlistTypePriorityDescriptionValueForUpdate(wishlistTypePriority, language);
                        
                        if(wishlistTypePriorityDescriptionValue != null) {
                            if(lockEntityForUpdate(wishlistTypePriority)) {
                                try {
                                    String description = edit.getDescription();
                                    
                                    wishlistTypePriorityDescriptionValue.setDescription(description);
                                    
                                    wishlistControl.updateWishlistTypePriorityDescriptionFromValue(wishlistTypePriorityDescriptionValue, getPartyPK());
                                } finally {
                                    unlockEntity(wishlistTypePriority);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockStale.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownWishlistTypePriorityDescription.name());
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownWishlistTypePriorityName.name(), wishlistTypePriorityName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWishlistTypeName.name(), wishlistTypeName);
        }
        
        return result;
    }
    
}
