// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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
import com.echothree.control.user.wishlist.common.result.EditWishlistPriorityDescriptionResult;
import com.echothree.control.user.wishlist.common.result.WishlistResultFactory;
import com.echothree.control.user.wishlist.common.spec.WishlistPriorityDescriptionSpec;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.wishlist.server.control.WishlistControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.wishlist.server.entity.WishlistType;
import com.echothree.model.data.wishlist.server.entity.WishlistPriority;
import com.echothree.model.data.wishlist.server.entity.WishlistPriorityDescription;
import com.echothree.model.data.wishlist.server.value.WishlistPriorityDescriptionValue;
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

public class EditWishlistPriorityDescriptionCommand
        extends BaseEditCommand<WishlistPriorityDescriptionSpec, WishlistPriorityDescriptionEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
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
    public EditWishlistPriorityDescriptionCommand(UserVisitPK userVisitPK, EditWishlistPriorityDescriptionForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var wishlistControl = Session.getModelController(WishlistControl.class);
        EditWishlistPriorityDescriptionResult result = WishlistResultFactory.getEditWishlistPriorityDescriptionResult();
        String wishlistTypeName = spec.getWishlistTypeName();
        WishlistType wishlistType = wishlistControl.getWishlistTypeByName(wishlistTypeName);
        
        if(wishlistType != null) {
            String wishlistPriorityName = spec.getWishlistPriorityName();
            WishlistPriority wishlistPriority = wishlistControl.getWishlistPriorityByName(wishlistType, wishlistPriorityName);
            
            if(wishlistPriority != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                String languageIsoName = spec.getLanguageIsoName();
                Language language = partyControl.getLanguageByIsoName(languageIsoName);
                
                if(language != null) {
                    if(editMode.equals(EditMode.LOCK)) {
                        WishlistPriorityDescription wishlistPriorityDescription = wishlistControl.getWishlistPriorityDescription(wishlistPriority, language);
                        
                        if(wishlistPriorityDescription != null) {
                            result.setWishlistPriorityDescription(wishlistControl.getWishlistPriorityDescriptionTransfer(getUserVisit(), wishlistPriorityDescription));
                            
                            if(lockEntity(wishlistPriority)) {
                                WishlistPriorityDescriptionEdit edit = WishlistEditFactory.getWishlistPriorityDescriptionEdit();
                                
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
                        WishlistPriorityDescriptionValue wishlistPriorityDescriptionValue = wishlistControl.getWishlistPriorityDescriptionValueForUpdate(wishlistPriority, language);
                        
                        if(wishlistPriorityDescriptionValue != null) {
                            if(lockEntityForUpdate(wishlistPriority)) {
                                try {
                                    String description = edit.getDescription();
                                    
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
