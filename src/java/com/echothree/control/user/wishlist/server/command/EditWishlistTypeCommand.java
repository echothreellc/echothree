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

package com.echothree.control.user.wishlist.server.command;

import com.echothree.control.user.wishlist.common.edit.WishlistEditFactory;
import com.echothree.control.user.wishlist.common.edit.WishlistTypeEdit;
import com.echothree.control.user.wishlist.common.result.EditWishlistTypeResult;
import com.echothree.control.user.wishlist.common.result.WishlistResultFactory;
import com.echothree.control.user.wishlist.common.spec.WishlistTypeUniversalSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.wishlist.server.control.WishlistControl;
import com.echothree.model.control.wishlist.server.logic.WishlistTypeLogic;
import com.echothree.model.data.wishlist.server.entity.WishlistType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditWishlistTypeCommand
        extends BaseAbstractEditCommand<WishlistTypeUniversalSpec, WishlistTypeEdit, EditWishlistTypeResult, WishlistType, WishlistType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.WishlistType.name(), SecurityRoles.Edit.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("WishlistTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("WishlistTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }
    
    /** Creates a new instance of EditWishlistTypeCommand */
    public EditWishlistTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Inject
    WishlistControl wishlistControl;

    @Inject
    WishlistTypeLogic wishlistTypeLogic;

    @Override
    protected EditWishlistTypeResult getResult() {
        return WishlistResultFactory.getEditWishlistTypeResult();
    }

    @Override
    protected WishlistTypeEdit getEdit() {
        return WishlistEditFactory.getWishlistTypeEdit();
    }

    @Override
    protected WishlistType getEntity(EditWishlistTypeResult result) {
        WishlistType wishlistType;

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            wishlistType = wishlistTypeLogic.getWishlistTypeByUniversalSpec(this, spec, false);
        } else { // EditMode.UPDATE
            wishlistType = wishlistTypeLogic.getWishlistTypeByUniversalSpecForUpdate(this, spec, false);
        }

        return wishlistType;
    }

    @Override
    protected WishlistType getLockEntity(WishlistType wishlistType) {
        return wishlistType;
    }

    @Override
    protected void fillInResult(EditWishlistTypeResult result, WishlistType wishlistType) {
        result.setWishlistType(wishlistControl.getWishlistTypeTransfer(getUserVisit(), wishlistType));
    }

    @Override
    protected void doLock(WishlistTypeEdit edit, WishlistType wishlistType) {
        var wishlistTypeDescription = wishlistControl.getWishlistTypeDescription(wishlistType, getPreferredLanguage());
        var wishlistTypeDetail = wishlistType.getLastDetail();

        edit.setWishlistTypeName(wishlistTypeDetail.getWishlistTypeName());
        edit.setIsDefault(wishlistTypeDetail.getIsDefault().toString());
        edit.setSortOrder(wishlistTypeDetail.getSortOrder().toString());

        if(wishlistTypeDescription != null) {
            edit.setDescription(wishlistTypeDescription.getDescription());
        }
    }

    @Override
    protected void canUpdate(WishlistType wishlistType) {
        var wishlistTypeName = edit.getWishlistTypeName();
        var duplicateWishlistType = wishlistControl.getWishlistTypeByName(wishlistTypeName);

        if(duplicateWishlistType != null && !wishlistType.equals(duplicateWishlistType)) {
            addExecutionError(ExecutionErrors.DuplicateWishlistTypeName.name(), wishlistTypeName);
        }
    }

    @Override
    protected void doUpdate(WishlistType wishlistType) {
        var partyPK = getPartyPK();
        var wishlistTypeDetailValue = wishlistControl.getWishlistTypeDetailValueForUpdate(wishlistType);
        var wishlistTypeDescription = wishlistControl.getWishlistTypeDescriptionForUpdate(wishlistType, getPreferredLanguage());
        var description = edit.getDescription();

        wishlistTypeDetailValue.setWishlistTypeName(edit.getWishlistTypeName());
        wishlistTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        wishlistTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        wishlistTypeLogic.updateWishlistTypeFromValue(wishlistTypeDetailValue, partyPK);

        if(wishlistTypeDescription == null && description != null) {
            wishlistControl.createWishlistTypeDescription(wishlistType, getPreferredLanguage(), description, partyPK);
        } else if(wishlistTypeDescription != null && description == null) {
            wishlistControl.deleteWishlistTypeDescription(wishlistTypeDescription, partyPK);
        } else if(wishlistTypeDescription != null) {
            var wishlistTypeDescriptionValue = wishlistControl.getWishlistTypeDescriptionValue(wishlistTypeDescription);

            wishlistTypeDescriptionValue.setDescription(description);
            wishlistControl.updateWishlistTypeDescriptionFromValue(wishlistTypeDescriptionValue, partyPK);
        }
    }
    
}
