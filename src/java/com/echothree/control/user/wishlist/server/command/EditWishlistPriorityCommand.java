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
import com.echothree.control.user.wishlist.common.edit.WishlistPriorityEdit;
import com.echothree.control.user.wishlist.common.form.EditWishlistPriorityForm;
import com.echothree.control.user.wishlist.common.result.WishlistResultFactory;
import com.echothree.control.user.wishlist.common.spec.WishlistPriorityUniversalSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.wishlist.server.control.WishlistControl;
import com.echothree.model.control.wishlist.server.logic.WishlistPriorityLogic;
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
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditWishlistPriorityCommand
        extends BaseEditCommand<WishlistPriorityUniversalSpec, WishlistPriorityEdit> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.WishlistPriority.name(), SecurityRoles.Edit.name())
                )))
        )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("WishlistTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("WishlistPriorityName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("WishlistPriorityName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditWishlistPriorityCommand */
    public EditWishlistPriorityCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var wishlistControl = Session.getModelController(WishlistControl.class);
        var result = WishlistResultFactory.getEditWishlistPriorityResult();
        
        if(editMode.equals(EditMode.LOCK)) {
            var wishlistPriority = WishlistPriorityLogic.getInstance().getWishlistPriorityByUniversalSpec(this, spec, false);

            if(!hasExecutionErrors()) {
                result.setWishlistPriority(wishlistControl.getWishlistPriorityTransfer(getUserVisit(), wishlistPriority));

                if(lockEntity(wishlistPriority)) {
                    var wishlistPriorityDescription = wishlistControl.getWishlistPriorityDescription(wishlistPriority, getPreferredLanguage());
                    var edit = WishlistEditFactory.getWishlistPriorityEdit();
                    var wishlistPriorityDetail = wishlistPriority.getLastDetail();

                    result.setEdit(edit);
                    edit.setWishlistPriorityName(wishlistPriorityDetail.getWishlistPriorityName());
                    edit.setIsDefault(wishlistPriorityDetail.getIsDefault().toString());
                    edit.setSortOrder(wishlistPriorityDetail.getSortOrder().toString());

                    if(wishlistPriorityDescription != null)
                        edit.setDescription(wishlistPriorityDescription.getDescription());
                } else {
                    addExecutionError(ExecutionErrors.EntityLockFailed.name());
                }

                result.setEntityLock(getEntityLockTransfer(wishlistPriority));
            }
        } else if(editMode.equals(EditMode.UPDATE)) {
            var wishlistPriority = WishlistPriorityLogic.getInstance().getWishlistPriorityByUniversalSpecForUpdate(this, spec, false);
                
            if(wishlistPriority != null) {
                var wishlistType = wishlistPriority.getLastDetail().getWishlistType();
                var wishlistPriorityName = edit.getWishlistPriorityName();
                var duplicateWishlistPriority = wishlistControl.getWishlistPriorityByName(wishlistType, wishlistPriorityName);

                if(duplicateWishlistPriority == null || wishlistPriority.equals(duplicateWishlistPriority)) {
                    if(lockEntityForUpdate(wishlistPriority)) {
                        try {
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
                            } else if(wishlistPriorityDescription != null && description != null) {
                                var wishlistPriorityDescriptionValue = wishlistControl.getWishlistPriorityDescriptionValue(wishlistPriorityDescription);

                                wishlistPriorityDescriptionValue.setDescription(description);
                                wishlistControl.updateWishlistPriorityDescriptionFromValue(wishlistPriorityDescriptionValue, partyPK);
                            }
                        } finally {
                            unlockEntity(wishlistPriority);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.EntityLockStale.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.DuplicateWishlistPriorityName.name(), wishlistPriorityName);
                }
            }
        }
        
        return result;
    }
    
}
