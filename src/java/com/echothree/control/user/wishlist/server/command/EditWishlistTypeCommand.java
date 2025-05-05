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
import com.echothree.control.user.wishlist.common.edit.WishlistTypeEdit;
import com.echothree.control.user.wishlist.common.form.EditWishlistTypeForm;
import com.echothree.control.user.wishlist.common.result.WishlistResultFactory;
import com.echothree.control.user.wishlist.common.spec.WishlistTypeUniversalSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.wishlist.server.control.WishlistControl;
import com.echothree.model.control.wishlist.server.logic.WishlistTypeLogic;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditWishlistTypeCommand
        extends BaseEditCommand<WishlistTypeUniversalSpec, WishlistTypeEdit> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.WishlistType.name(), SecurityRoles.Edit.name())
                )))
        )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("WishlistTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("WishlistTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditWishlistTypeCommand */
    public EditWishlistTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var wishlistControl = Session.getModelController(WishlistControl.class);
        var result = WishlistResultFactory.getEditWishlistTypeResult();
        
        if(editMode.equals(EditMode.LOCK)) {
            var wishlistTypeName = spec.getWishlistTypeName();
            var wishlistType = WishlistTypeLogic.getInstance().getWishlistTypeByUniversalSpec(this, spec, false);
            
            if(!hasExecutionErrors()) {
                result.setWishlistType(wishlistControl.getWishlistTypeTransfer(getUserVisit(), wishlistType));
                
                if(lockEntity(wishlistType)) {
                    var wishlistTypeDescription = wishlistControl.getWishlistTypeDescription(wishlistType, getPreferredLanguage());
                    var edit = WishlistEditFactory.getWishlistTypeEdit();
                    var wishlistTypeDetail = wishlistType.getLastDetail();
                    
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
            var wishlistTypeName = spec.getWishlistTypeName();
            var wishlistType = WishlistTypeLogic.getInstance().getWishlistTypeByUniversalSpecForUpdate(this, spec, false);
            
            if(!hasExecutionErrors()) {
                wishlistTypeName = edit.getWishlistTypeName();
                var duplicateWishlistType = wishlistControl.getWishlistTypeByName(wishlistTypeName);
                
                if(duplicateWishlistType == null || wishlistType.equals(duplicateWishlistType)) {
                    if(lockEntityForUpdate(wishlistType)) {
                        try {
                            var partyPK = getPartyPK();
                            var wishlistTypeDetailValue = wishlistControl.getWishlistTypeDetailValueForUpdate(wishlistType);
                            var wishlistTypeDescription = wishlistControl.getWishlistTypeDescriptionForUpdate(wishlistType, getPreferredLanguage());
                            var description = edit.getDescription();
                            
                            wishlistTypeDetailValue.setWishlistTypeName(edit.getWishlistTypeName());
                            wishlistTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                            wishlistTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                            
                            WishlistTypeLogic.getInstance().updateWishlistTypeFromValue(wishlistTypeDetailValue, partyPK);
                            
                            if(wishlistTypeDescription == null && description != null) {
                                wishlistControl.createWishlistTypeDescription(wishlistType, getPreferredLanguage(), description, partyPK);
                            } else if(wishlistTypeDescription != null && description == null) {
                                wishlistControl.deleteWishlistTypeDescription(wishlistTypeDescription, partyPK);
                            } else if(wishlistTypeDescription != null && description != null) {
                                var wishlistTypeDescriptionValue = wishlistControl.getWishlistTypeDescriptionValue(wishlistTypeDescription);
                                
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
