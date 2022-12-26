// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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
import com.echothree.control.user.wishlist.common.edit.WishlistTypePriorityEdit;
import com.echothree.control.user.wishlist.common.form.EditWishlistTypePriorityForm;
import com.echothree.control.user.wishlist.common.result.EditWishlistTypePriorityResult;
import com.echothree.control.user.wishlist.common.result.WishlistResultFactory;
import com.echothree.control.user.wishlist.common.spec.WishlistTypePrioritySpec;
import com.echothree.model.control.wishlist.server.control.WishlistControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.wishlist.server.entity.WishlistType;
import com.echothree.model.data.wishlist.server.entity.WishlistTypePriority;
import com.echothree.model.data.wishlist.server.entity.WishlistTypePriorityDescription;
import com.echothree.model.data.wishlist.server.entity.WishlistTypePriorityDetail;
import com.echothree.model.data.wishlist.server.value.WishlistTypePriorityDescriptionValue;
import com.echothree.model.data.wishlist.server.value.WishlistTypePriorityDetailValue;
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

public class EditWishlistTypePriorityCommand
        extends BaseEditCommand<WishlistTypePrioritySpec, WishlistTypePriorityEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("WishlistTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("WishlistTypePriorityName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("WishlistTypePriorityName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditWishlistTypePriorityCommand */
    public EditWishlistTypePriorityCommand(UserVisitPK userVisitPK, EditWishlistTypePriorityForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var wishlistControl = Session.getModelController(WishlistControl.class);
        EditWishlistTypePriorityResult result = WishlistResultFactory.getEditWishlistTypePriorityResult();
        
        if(editMode.equals(EditMode.LOCK)) {
            String wishlistTypeName = spec.getWishlistTypeName();
            WishlistType wishlistType = wishlistControl.getWishlistTypeByName(wishlistTypeName);
            
            if(wishlistType != null) {
                String wishlistTypePriorityName = spec.getWishlistTypePriorityName();
                WishlistTypePriority wishlistTypePriority = wishlistControl.getWishlistTypePriorityByName(wishlistType, wishlistTypePriorityName);
                
                if(wishlistTypePriority != null) {
                    result.setWishlistTypePriority(wishlistControl.getWishlistTypePriorityTransfer(getUserVisit(), wishlistTypePriority));
                    
                    if(lockEntity(wishlistTypePriority)) {
                        WishlistTypePriorityDescription wishlistTypePriorityDescription = wishlistControl.getWishlistTypePriorityDescription(wishlistTypePriority, getPreferredLanguage());
                        WishlistTypePriorityEdit edit = WishlistEditFactory.getWishlistTypePriorityEdit();
                        WishlistTypePriorityDetail wishlistTypePriorityDetail = wishlistTypePriority.getLastDetail();
                        
                        result.setEdit(edit);
                        edit.setWishlistTypePriorityName(wishlistTypePriorityDetail.getWishlistTypePriorityName());
                        edit.setIsDefault(wishlistTypePriorityDetail.getIsDefault().toString());
                        edit.setSortOrder(wishlistTypePriorityDetail.getSortOrder().toString());
                        
                        if(wishlistTypePriorityDescription != null)
                            edit.setDescription(wishlistTypePriorityDescription.getDescription());
                    } else {
                        addExecutionError(ExecutionErrors.EntityLockFailed.name());
                    }
                    
                    result.setEntityLock(getEntityLockTransfer(wishlistTypePriority));
                } else {
                    addExecutionError(ExecutionErrors.UnknownWishlistTypePriorityName.name(), wishlistTypePriorityName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownWishlistTypeName.name(), wishlistTypeName);
            }
        } else if(editMode.equals(EditMode.UPDATE)) {
            String wishlistTypeName = spec.getWishlistTypeName();
            WishlistType wishlistType = wishlistControl.getWishlistTypeByName(wishlistTypeName);
            
            if(wishlistType != null) {
                String wishlistTypePriorityName = spec.getWishlistTypePriorityName();
                WishlistTypePriority wishlistTypePriority = wishlistControl.getWishlistTypePriorityByNameForUpdate(wishlistType, wishlistTypePriorityName);
                
                if(wishlistTypePriority != null) {
                    wishlistTypePriorityName = edit.getWishlistTypePriorityName();
                    WishlistTypePriority duplicateWishlistTypePriority = wishlistControl.getWishlistTypePriorityByName(wishlistType, wishlistTypePriorityName);
                    
                    if(duplicateWishlistTypePriority == null || wishlistTypePriority.equals(duplicateWishlistTypePriority)) {
                        if(lockEntityForUpdate(wishlistTypePriority)) {
                            try {
                                var partyPK = getPartyPK();
                                WishlistTypePriorityDetailValue wishlistTypePriorityDetailValue = wishlistControl.getWishlistTypePriorityDetailValueForUpdate(wishlistTypePriority);
                                WishlistTypePriorityDescription wishlistTypePriorityDescription = wishlistControl.getWishlistTypePriorityDescriptionForUpdate(wishlistTypePriority, getPreferredLanguage());
                                String description = edit.getDescription();
                                
                                wishlistTypePriorityDetailValue.setWishlistTypePriorityName(edit.getWishlistTypePriorityName());
                                wishlistTypePriorityDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                                wishlistTypePriorityDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                                
                                wishlistControl.updateWishlistTypePriorityFromValue(wishlistTypePriorityDetailValue, partyPK);
                                
                                if(wishlistTypePriorityDescription == null && description != null) {
                                    wishlistControl.createWishlistTypePriorityDescription(wishlistTypePriority, getPreferredLanguage(), description, partyPK);
                                } else if(wishlistTypePriorityDescription != null && description == null) {
                                    wishlistControl.deleteWishlistTypePriorityDescription(wishlistTypePriorityDescription, partyPK);
                                } else if(wishlistTypePriorityDescription != null && description != null) {
                                    WishlistTypePriorityDescriptionValue wishlistTypePriorityDescriptionValue = wishlistControl.getWishlistTypePriorityDescriptionValue(wishlistTypePriorityDescription);
                                    
                                    wishlistTypePriorityDescriptionValue.setDescription(description);
                                    wishlistControl.updateWishlistTypePriorityDescriptionFromValue(wishlistTypePriorityDescriptionValue, partyPK);
                                }
                            } finally {
                                unlockEntity(wishlistTypePriority);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.DuplicateWishlistTypePriorityName.name(), wishlistTypePriorityName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownWishlistTypePriorityName.name(), wishlistTypePriorityName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownWishlistTypeName.name(), wishlistTypeName);
            }
        }
        
        return result;
    }
    
}
