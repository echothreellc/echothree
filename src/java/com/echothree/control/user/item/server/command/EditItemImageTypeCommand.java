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

package com.echothree.control.user.item.server.command;

import com.echothree.control.user.item.common.edit.ItemEditFactory;
import com.echothree.control.user.item.common.edit.ItemImageTypeEdit;
import com.echothree.control.user.item.common.form.EditItemImageTypeForm;
import com.echothree.control.user.item.common.result.EditItemImageTypeResult;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.control.user.item.common.spec.ItemImageTypeUniversalSpec;
import com.echothree.model.control.core.server.control.MimeTypeControl;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.item.server.logic.ItemDescriptionLogic;
import com.echothree.model.control.item.server.logic.ItemImageTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.item.server.entity.ItemImageType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditItemImageTypeCommand
        extends BaseAbstractEditCommand<ItemImageTypeUniversalSpec, ItemImageTypeEdit, EditItemImageTypeResult, ItemImageType, ItemImageType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ItemImageType.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemImageTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemImageTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PreferredMimeTypeName", FieldType.MIME_TYPE, false, null, null),
                new FieldDefinition("Quality", FieldType.UNSIGNED_INTEGER, false, null, 100L),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditItemImageTypeCommand */
    public EditItemImageTypeCommand(UserVisitPK userVisitPK, EditItemImageTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditItemImageTypeResult getResult() {
        return ItemResultFactory.getEditItemImageTypeResult();
    }
    
    @Override
    public ItemImageTypeEdit getEdit() {
        return ItemEditFactory.getItemImageTypeEdit();
    }
    
    @Override
    public ItemImageType getEntity(EditItemImageTypeResult result) {
        return ItemImageTypeLogic.getInstance().getItemImageTypeByUniversalSpec(this,
                spec, false, editModeToEntityPermission(editMode));
    }
    
    @Override
    public ItemImageType getLockEntity(ItemImageType itemImageType) {
        return itemImageType;
    }
    
    @Override
    public void fillInResult(EditItemImageTypeResult result, ItemImageType itemImageType) {
        var itemControl = Session.getModelController(ItemControl.class);
        
        result.setItemImageType(itemControl.getItemImageTypeTransfer(getUserVisit(), itemImageType));
    }

    MimeType preferredMimeType;

    @Override
    public void doLock(ItemImageTypeEdit edit, ItemImageType itemImageType) {
        var itemControl = Session.getModelController(ItemControl.class);
        var itemImageTypeDescription = itemControl.getItemImageTypeDescription(itemImageType, getPreferredLanguage());
        var itemImageTypeDetail = itemImageType.getLastDetail();
        var quality = itemImageTypeDetail.getQuality();

        preferredMimeType = itemImageTypeDetail.getPreferredMimeType();

        edit.setItemImageTypeName(itemImageTypeDetail.getItemImageTypeName());
        edit.setPreferredMimeTypeName(preferredMimeType == null ? null : preferredMimeType.getLastDetail().getMimeTypeName());
        edit.setQuality(quality == null ? null : quality.toString());
        edit.setIsDefault(itemImageTypeDetail.getIsDefault().toString());
        edit.setSortOrder(itemImageTypeDetail.getSortOrder().toString());

        if(itemImageTypeDescription != null) {
            edit.setDescription(itemImageTypeDescription.getDescription());
        }
    }
        
    @Override
    public void canUpdate(ItemImageType itemImageType) {
        var itemControl = Session.getModelController(ItemControl.class);
        var itemImageTypeName = edit.getItemImageTypeName();
        var duplicateItemImageType = itemControl.getItemImageTypeByName(itemImageTypeName);

        if(duplicateItemImageType == null || itemImageType.equals(duplicateItemImageType)) {
            var mimeTypeControl = Session.getModelController(MimeTypeControl.class);
            var preferredMimeTypeName = edit.getPreferredMimeTypeName();

            preferredMimeType = preferredMimeTypeName == null ? null : mimeTypeControl.getMimeTypeByName(preferredMimeTypeName);

            if(preferredMimeTypeName != null && preferredMimeType == null) {
                addExecutionError(ExecutionErrors.UnknownPreferredMimeTypeName.name(), preferredMimeTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateItemImageTypeName.name(), itemImageTypeName);
        }
    }
    
    @Override
    public void doUpdate(ItemImageType itemImageType) {
        var itemControl = Session.getModelController(ItemControl.class);
        var partyPK = getPartyPK();
        var itemImageTypeDetailValue = itemControl.getItemImageTypeDetailValueForUpdate(itemImageType);
        var itemImageTypeDescription = itemControl.getItemImageTypeDescriptionForUpdate(itemImageType, getPreferredLanguage());
        var strQuality = edit.getQuality();
        var description = edit.getDescription();

        itemImageTypeDetailValue.setItemImageTypeName(edit.getItemImageTypeName());
        itemImageTypeDetailValue.setPreferredMimeTypePK(preferredMimeType == null ? null : preferredMimeType.getPrimaryKey());
        itemImageTypeDetailValue.setQuality(strQuality == null ? null : Integer.valueOf(strQuality));
        itemImageTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        itemImageTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        ItemDescriptionLogic.getInstance().updateItemImageTypeFromValue(itemImageTypeDetailValue, partyPK);

        if(itemImageTypeDescription == null && description != null) {
            itemControl.createItemImageTypeDescription(itemImageType, getPreferredLanguage(), description, partyPK);
        } else if(itemImageTypeDescription != null && description == null) {
            itemControl.deleteItemImageTypeDescription(itemImageTypeDescription, partyPK);
        } else if(itemImageTypeDescription != null && description != null) {
            var itemImageTypeDescriptionValue = itemControl.getItemImageTypeDescriptionValue(itemImageTypeDescription);

            itemImageTypeDescriptionValue.setDescription(description);
            itemControl.updateItemImageTypeDescriptionFromValue(itemImageTypeDescriptionValue, partyPK);
        }
    }
    
}
