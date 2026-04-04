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
import com.echothree.control.user.wishlist.common.edit.WishlistPriorityEdit;
import com.echothree.control.user.wishlist.common.result.EditWishlistPriorityResult;
import com.echothree.control.user.wishlist.common.result.WishlistResultFactory;
import com.echothree.control.user.wishlist.common.spec.WishlistPriorityUniversalSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.wishlist.server.control.WishlistControl;
import com.echothree.model.control.wishlist.server.logic.WishlistPriorityLogic;
import com.echothree.model.data.wishlist.server.entity.WishlistPriority;
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
public class EditWishlistPriorityCommand
        extends BaseAbstractEditCommand<WishlistPriorityUniversalSpec, WishlistPriorityEdit, EditWishlistPriorityResult, WishlistPriority, WishlistPriority> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.WishlistPriority.name(), SecurityRoles.Edit.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("WishlistTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("WishlistPriorityName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("WishlistPriorityName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }
    
    /** Creates a new instance of EditWishlistPriorityCommand */
    public EditWishlistPriorityCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Inject
    WishlistControl wishlistControl;

    @Inject
    WishlistPriorityLogic wishlistPriorityLogic;

    @Override
    protected EditWishlistPriorityResult getResult() {
        return WishlistResultFactory.getEditWishlistPriorityResult();
    }

    @Override
    protected WishlistPriorityEdit getEdit() {
        return WishlistEditFactory.getWishlistPriorityEdit();
    }

    @Override
    protected WishlistPriority getEntity(EditWishlistPriorityResult result) {
        WishlistPriority wishlistPriority;

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            wishlistPriority = wishlistPriorityLogic.getWishlistPriorityByUniversalSpec(this, spec, false);
        } else { // EditMode.UPDATE
            wishlistPriority = wishlistPriorityLogic.getWishlistPriorityByUniversalSpecForUpdate(this, spec, false);
        }

        return wishlistPriority;
    }

    @Override
    protected WishlistPriority getLockEntity(WishlistPriority wishlistPriority) {
        return wishlistPriority;
    }

    @Override
    protected void fillInResult(EditWishlistPriorityResult result, WishlistPriority wishlistPriority) {
        result.setWishlistPriority(wishlistControl.getWishlistPriorityTransfer(getUserVisit(), wishlistPriority));
    }

    @Override
    protected void doLock(WishlistPriorityEdit edit, WishlistPriority wishlistPriority) {
        var wishlistPriorityDescription = wishlistControl.getWishlistPriorityDescription(wishlistPriority, getPreferredLanguage());
        var wishlistPriorityDetail = wishlistPriority.getLastDetail();

        edit.setWishlistPriorityName(wishlistPriorityDetail.getWishlistPriorityName());
        edit.setIsDefault(wishlistPriorityDetail.getIsDefault().toString());
        edit.setSortOrder(wishlistPriorityDetail.getSortOrder().toString());

        if(wishlistPriorityDescription != null) {
            edit.setDescription(wishlistPriorityDescription.getDescription());
        }
    }

    @Override
    protected void canUpdate(WishlistPriority wishlistPriority) {
        var wishlistType = wishlistPriority.getLastDetail().getWishlistType();
        var wishlistPriorityName = edit.getWishlistPriorityName();
        var duplicateWishlistPriority = wishlistControl.getWishlistPriorityByName(wishlistType, wishlistPriorityName);

        if(duplicateWishlistPriority != null && !wishlistPriority.equals(duplicateWishlistPriority)) {
            addExecutionError(ExecutionErrors.DuplicateWishlistPriorityName.name(), wishlistPriorityName);
        }
    }

    @Override
    protected void doUpdate(WishlistPriority wishlistPriority) {
        var partyPK = getPartyPK();
        var wishlistPriorityDetailValue = wishlistControl.getWishlistPriorityDetailValueForUpdate(wishlistPriority);
        var wishlistPriorityDescription = wishlistControl.getWishlistPriorityDescriptionForUpdate(wishlistPriority, getPreferredLanguage());
        var description = edit.getDescription();

        wishlistPriorityDetailValue.setWishlistPriorityName(edit.getWishlistPriorityName());
        wishlistPriorityDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        wishlistPriorityDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        wishlistControl.updateWishlistPriorityFromValue(wishlistPriorityDetailValue, partyPK);

        if(wishlistPriorityDescription == null && description != null) {
            wishlistControl.createWishlistPriorityDescription(wishlistPriority, getPreferredLanguage(), description, partyPK);
        } else if(wishlistPriorityDescription != null && description == null) {
            wishlistControl.deleteWishlistPriorityDescription(wishlistPriorityDescription, partyPK);
        } else if(wishlistPriorityDescription != null) {
            var wishlistPriorityDescriptionValue = wishlistControl.getWishlistPriorityDescriptionValue(wishlistPriorityDescription);

            wishlistPriorityDescriptionValue.setDescription(description);
            wishlistControl.updateWishlistPriorityDescriptionFromValue(wishlistPriorityDescriptionValue, partyPK);
        }
    }

}
