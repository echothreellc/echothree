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

package com.echothree.control.user.wishlist.server.command;

import com.echothree.control.user.wishlist.common.edit.WishlistEditFactory;
import com.echothree.control.user.wishlist.common.edit.WishlistTypeEdit;
import com.echothree.control.user.wishlist.common.form.EditWishlistTypeForm;
import com.echothree.control.user.wishlist.common.result.EditWishlistTypeResult;
import com.echothree.control.user.wishlist.common.result.WishlistResultFactory;
import com.echothree.control.user.wishlist.common.spec.WishlistTypeSpec;
import com.echothree.model.control.wishlist.server.WishlistControl;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.wishlist.server.entity.WishlistType;
import com.echothree.model.data.wishlist.server.entity.WishlistTypeDescription;
import com.echothree.model.data.wishlist.server.entity.WishlistTypeDetail;
import com.echothree.model.data.wishlist.server.value.WishlistTypeDescriptionValue;
import com.echothree.model.data.wishlist.server.value.WishlistTypeDetailValue;
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

public class EditWishlistTypeCommand
        extends BaseEditCommand<WishlistTypeSpec, WishlistTypeEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("WishlistTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("WishlistTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditWishlistTypeCommand */
    public EditWishlistTypeCommand(UserVisitPK userVisitPK, EditWishlistTypeForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var wishlistControl = (WishlistControl)Session.getModelController(WishlistControl.class);
        EditWishlistTypeResult result = WishlistResultFactory.getEditWishlistTypeResult();
        
        if(editMode.equals(EditMode.LOCK)) {
            String wishlistTypeName = spec.getWishlistTypeName();
            WishlistType wishlistType = wishlistControl.getWishlistTypeByName(wishlistTypeName);
            
            if(wishlistType != null) {
                result.setWishlistType(wishlistControl.getWishlistTypeTransfer(getUserVisit(), wishlistType));
                
                if(lockEntity(wishlistType)) {
                    WishlistTypeDescription wishlistTypeDescription = wishlistControl.getWishlistTypeDescription(wishlistType, getPreferredLanguage());
                    WishlistTypeEdit edit = WishlistEditFactory.getWishlistTypeEdit();
                    WishlistTypeDetail wishlistTypeDetail = wishlistType.getLastDetail();
                    
                    result.setEdit(edit);
                    edit.setWishlistTypeName(wishlistTypeDetail.getWishlistTypeName());
                    edit.setIsDefault(wishlistTypeDetail.getIsDefault().toString());
                    edit.setSortOrder(wishlistTypeDetail.getSortOrder().toString());
                    
                    if(wishlistTypeDescription != null)
                        edit.setDescription(wishlistTypeDescription.getDescription());
                } else {
                    addExecutionError(ExecutionErrors.EntityLockFailed.name());
                }
                
                result.setEntityLock(getEntityLockTransfer(wishlistType));
            } else {
                addExecutionError(ExecutionErrors.UnknownWishlistTypeName.name(), wishlistTypeName);
            }
        } else if(editMode.equals(EditMode.UPDATE)) {
            String wishlistTypeName = spec.getWishlistTypeName();
            WishlistType wishlistType = wishlistControl.getWishlistTypeByNameForUpdate(wishlistTypeName);
            
            if(wishlistType != null) {
                wishlistTypeName = edit.getWishlistTypeName();
                WishlistType duplicateWishlistType = wishlistControl.getWishlistTypeByName(wishlistTypeName);
                
                if(duplicateWishlistType == null || wishlistType.equals(duplicateWishlistType)) {
                    if(lockEntityForUpdate(wishlistType)) {
                        try {
                            PartyPK partyPK = getPartyPK();
                            WishlistTypeDetailValue wishlistTypeDetailValue = wishlistControl.getWishlistTypeDetailValueForUpdate(wishlistType);
                            WishlistTypeDescription wishlistTypeDescription = wishlistControl.getWishlistTypeDescriptionForUpdate(wishlistType, getPreferredLanguage());
                            String description = edit.getDescription();
                            
                            wishlistTypeDetailValue.setWishlistTypeName(edit.getWishlistTypeName());
                            wishlistTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                            wishlistTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                            
                            wishlistControl.updateWishlistTypeFromValue(wishlistTypeDetailValue, partyPK);
                            
                            if(wishlistTypeDescription == null && description != null) {
                                wishlistControl.createWishlistTypeDescription(wishlistType, getPreferredLanguage(), description, partyPK);
                            } else if(wishlistTypeDescription != null && description == null) {
                                wishlistControl.deleteWishlistTypeDescription(wishlistTypeDescription, partyPK);
                            } else if(wishlistTypeDescription != null && description != null) {
                                WishlistTypeDescriptionValue wishlistTypeDescriptionValue = wishlistControl.getWishlistTypeDescriptionValue(wishlistTypeDescription);
                                
                                wishlistTypeDescriptionValue.setDescription(description);
                                wishlistControl.updateWishlistTypeDescriptionFromValue(wishlistTypeDescriptionValue, partyPK);
                            }
                        } finally {
                            unlockEntity(wishlistType);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.EntityLockStale.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.DuplicateWishlistTypeName.name(), wishlistTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownWishlistTypeName.name(), wishlistTypeName);
            }
        }
        
        return result;
    }
    
}
