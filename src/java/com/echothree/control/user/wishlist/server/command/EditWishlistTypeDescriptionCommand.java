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
import com.echothree.control.user.wishlist.common.edit.WishlistTypeDescriptionEdit;
import com.echothree.control.user.wishlist.common.result.EditWishlistTypeDescriptionResult;
import com.echothree.control.user.wishlist.common.result.WishlistResultFactory;
import com.echothree.control.user.wishlist.common.spec.WishlistTypeDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.wishlist.server.control.WishlistControl;
import com.echothree.model.control.wishlist.server.logic.WishlistTypeLogic;
import com.echothree.model.data.wishlist.server.entity.WishlistType;
import com.echothree.model.data.wishlist.server.entity.WishlistTypeDescription;
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
public class EditWishlistTypeDescriptionCommand
        extends BaseAbstractEditCommand<WishlistTypeDescriptionSpec, WishlistTypeDescriptionEdit, EditWishlistTypeDescriptionResult, WishlistTypeDescription, WishlistType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.WishlistType.name(), SecurityRoles.Description.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
            new FieldDefinition("WishlistTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
            new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    WishlistControl wishlistControl;
    @Inject
    WishlistTypeLogic wishlistTypeLogic;
    @Inject
    LanguageLogic languageLogic;

    /** Creates a new instance of EditWishlistTypeDescriptionCommand */
    public EditWishlistTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditWishlistTypeDescriptionResult getResult() {
        return WishlistResultFactory.getEditWishlistTypeDescriptionResult();
    }

    @Override
    public WishlistTypeDescriptionEdit getEdit() {
        return WishlistEditFactory.getWishlistTypeDescriptionEdit();
    }

    @Override
    public WishlistTypeDescription getEntity(EditWishlistTypeDescriptionResult result) {
        var wishlistTypeName = spec.getWishlistTypeName();
        var wishlistType = wishlistTypeLogic.getWishlistTypeByName(this, wishlistTypeName);
        WishlistTypeDescription wishlistTypeDescription = null;

        if(!hasExecutionErrors()) {
            var languageIsoName = spec.getLanguageIsoName();
            var language = languageLogic.getLanguageByName(this, languageIsoName);

            if(!hasExecutionErrors()) {
                if(editMode.equals(com.echothree.util.common.command.EditMode.LOCK) || editMode.equals(com.echothree.util.common.command.EditMode.ABANDON)) {
                    wishlistTypeDescription = wishlistControl.getWishlistTypeDescription(wishlistType, language);
                } else {
                    wishlistTypeDescription = wishlistControl.getWishlistTypeDescriptionForUpdate(wishlistType, language);
                }

                if(wishlistTypeDescription == null) {
                    addExecutionError(com.echothree.util.common.message.ExecutionErrors.UnknownWishlistTypeDescription.name(), wishlistTypeName, languageIsoName);
                }
            }
        }

        return wishlistTypeDescription;
    }

    @Override
    public WishlistType getLockEntity(WishlistTypeDescription wishlistTypeDescription) {
        return wishlistTypeDescription.getWishlistType();
    }

    @Override
    public void fillInResult(EditWishlistTypeDescriptionResult result, WishlistTypeDescription wishlistTypeDescription) {
        result.setWishlistTypeDescription(wishlistControl.getWishlistTypeDescriptionTransfer(getUserVisit(), wishlistTypeDescription));
    }

    @Override
    public void doLock(WishlistTypeDescriptionEdit edit, WishlistTypeDescription wishlistTypeDescription) {
        edit.setDescription(wishlistTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(WishlistTypeDescription wishlistTypeDescription) {
        var wishlistTypeDescriptionValue = wishlistControl.getWishlistTypeDescriptionValue(wishlistTypeDescription);

        wishlistTypeDescriptionValue.setDescription(edit.getDescription());

        wishlistControl.updateWishlistTypeDescriptionFromValue(wishlistTypeDescriptionValue, getPartyPK());
    }
    
}
