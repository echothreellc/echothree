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

package com.echothree.control.user.item.server.command;

import com.echothree.control.user.item.common.edit.ItemDescriptionTypeEdit;
import com.echothree.control.user.item.common.edit.ItemEditFactory;
import com.echothree.control.user.item.common.form.EditItemDescriptionTypeForm;
import com.echothree.control.user.item.common.result.EditItemDescriptionTypeResult;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.control.user.item.common.spec.ItemDescriptionTypeSpec;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.item.server.logic.ItemDescriptionLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.core.server.entity.MimeTypeUsage;
import com.echothree.model.data.core.server.entity.MimeTypeUsageType;
import com.echothree.model.data.item.server.entity.ItemDescriptionType;
import com.echothree.model.data.item.server.entity.ItemDescriptionTypeDescription;
import com.echothree.model.data.item.server.entity.ItemDescriptionTypeDetail;
import com.echothree.model.data.item.server.entity.ItemImageDescriptionType;
import com.echothree.model.data.item.server.value.ItemDescriptionTypeDescriptionValue;
import com.echothree.model.data.item.server.value.ItemDescriptionTypeDetailValue;
import com.echothree.model.data.item.server.value.ItemImageDescriptionTypeValue;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.form.ValidationResult;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.validation.Validator;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditItemDescriptionTypeCommand
        extends BaseAbstractEditCommand<ItemDescriptionTypeSpec, ItemDescriptionTypeEdit, EditItemDescriptionTypeResult, ItemDescriptionType, ItemDescriptionType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> imageFieldDefinitions;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ItemDescriptionType.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemDescriptionTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemDescriptionTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ParentItemDescriptionTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("UseParentIfMissing", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("IncludeInIndex", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("CheckContentWebAddress", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("IndexDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));

        imageFieldDefinitions = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("MinimumHeight", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("MinimumWidth", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("MaximumHeight", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("MaximumWidth", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("PreferredHeight", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("PreferredWidth", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("PreferredMimeTypeName", FieldType.MIME_TYPE, false, null, null),
                new FieldDefinition("Quality", FieldType.UNSIGNED_INTEGER, false, null, 100L),
                new FieldDefinition("ScaleFromParent", FieldType.BOOLEAN, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditItemDescriptionTypeCommand */
    public EditItemDescriptionTypeCommand(UserVisitPK userVisitPK, EditItemDescriptionTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected ValidationResult validateEdit(Validator validator) {
        ValidationResult validationResult = validator.validate(edit, EDIT_FIELD_DEFINITIONS);

        if(!validationResult.getHasErrors()) {
            var itemControl = Session.getModelController(ItemControl.class);
            ItemDescriptionType itemDescriptionType = itemControl.getItemDescriptionTypeByName(spec.getItemDescriptionTypeName());
            MimeTypeUsageType mimeTypeUsageType = itemDescriptionType.getLastDetail().getMimeTypeUsageType();

            if(mimeTypeUsageType != null) {
                String mimeTypeUsageTypeName = mimeTypeUsageType.getMimeTypeUsageTypeName();

                if(mimeTypeUsageTypeName.equals(MimeTypeUsageTypes.IMAGE.name())) {
                    validationResult = validator.validate(edit, imageFieldDefinitions);
                }
            }
        }

        return validationResult;
    }

    @Override
    public EditItemDescriptionTypeResult getResult() {
        return ItemResultFactory.getEditItemDescriptionTypeResult();
    }
    
    @Override
    public ItemDescriptionTypeEdit getEdit() {
        return ItemEditFactory.getItemDescriptionTypeEdit();
    }
    
    @Override
    public ItemDescriptionType getEntity(EditItemDescriptionTypeResult result) {
        var itemControl = Session.getModelController(ItemControl.class);
        ItemDescriptionType itemDescriptionType = null;
        String itemDescriptionTypeName = spec.getItemDescriptionTypeName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            itemDescriptionType = itemControl.getItemDescriptionTypeByName(itemDescriptionTypeName);
        } else { // EditMode.UPDATE
            itemDescriptionType = itemControl.getItemDescriptionTypeByNameForUpdate(itemDescriptionTypeName);
        }

        if(itemDescriptionType != null) {
            result.setItemDescriptionType(itemControl.getItemDescriptionTypeTransfer(getUserVisit(), itemDescriptionType));
        } else {
            addExecutionError(ExecutionErrors.UnknownItemDescriptionTypeName.name(), itemDescriptionTypeName);
        }

        return itemDescriptionType;
    }
    
    @Override
    public ItemDescriptionType getLockEntity(ItemDescriptionType itemDescriptionType) {
        return itemDescriptionType;
    }
    
    @Override
    public void fillInResult(EditItemDescriptionTypeResult result, ItemDescriptionType itemDescriptionType) {
        var itemControl = Session.getModelController(ItemControl.class);
        
        result.setItemDescriptionType(itemControl.getItemDescriptionTypeTransfer(getUserVisit(), itemDescriptionType));
    }
    
    ItemDescriptionType parentItemDescriptionType;
    MimeTypeUsageType mimeTypeUsageType;
    MimeType preferredMimeType;
    
    @Override
    public void doLock(ItemDescriptionTypeEdit edit, ItemDescriptionType itemDescriptionType) {
        var itemControl = Session.getModelController(ItemControl.class);
        ItemDescriptionTypeDescription itemDescriptionTypeDescription = itemControl.getItemDescriptionTypeDescription(itemDescriptionType, getPreferredLanguage());
        ItemDescriptionTypeDetail itemDescriptionTypeDetail = itemDescriptionType.getLastDetail();

        parentItemDescriptionType = itemDescriptionTypeDetail.getParentItemDescriptionType();
        mimeTypeUsageType = itemDescriptionTypeDetail.getMimeTypeUsageType();
        
        edit.setItemDescriptionTypeName(itemDescriptionTypeDetail.getItemDescriptionTypeName());
        edit.setParentItemDescriptionTypeName(parentItemDescriptionType == null? null: parentItemDescriptionType.getLastDetail().getItemDescriptionTypeName());
        edit.setUseParentIfMissing(itemDescriptionTypeDetail.getUseParentIfMissing().toString());
        edit.setCheckContentWebAddress(itemDescriptionTypeDetail.getCheckContentWebAddress().toString());
        edit.setIncludeInIndex(itemDescriptionTypeDetail.getIncludeInIndex().toString());
        edit.setIndexDefault(itemDescriptionTypeDetail.getIndexDefault().toString());
        edit.setIsDefault(itemDescriptionTypeDetail.getIsDefault().toString());
        edit.setSortOrder(itemDescriptionTypeDetail.getSortOrder().toString());

        if(itemDescriptionTypeDescription != null) {
            edit.setDescription(itemDescriptionTypeDescription.getDescription());
        }

        if(mimeTypeUsageType != null) {
            String mimeTypeUsageTypeName = mimeTypeUsageType.getMimeTypeUsageTypeName();

            if(mimeTypeUsageTypeName.equals(MimeTypeUsageTypes.IMAGE.name())) {
                ItemImageDescriptionType itemImageDescriptionType = itemControl.getItemImageDescriptionType(itemDescriptionType);
                Integer minimumHeight = itemImageDescriptionType.getMinimumHeight();
                Integer minimumWidth = itemImageDescriptionType.getMinimumWidth();
                Integer maximumHeight = itemImageDescriptionType.getMaximumHeight();
                Integer maximumWidth = itemImageDescriptionType.getMaximumWidth();
                Integer preferredHeight = itemImageDescriptionType.getPreferredHeight();
                Integer preferredWidth = itemImageDescriptionType.getPreferredWidth();
                Integer quality = itemImageDescriptionType.getQuality();

                preferredMimeType = itemImageDescriptionType.getPreferredMimeType();

                edit.setMinimumHeight(minimumHeight == null ? null : minimumHeight.toString());
                edit.setMinimumWidth(minimumWidth == null ? null : minimumWidth.toString());
                edit.setMaximumHeight(maximumHeight == null ? null : maximumHeight.toString());
                edit.setMaximumWidth(maximumWidth == null ? null : maximumWidth.toString());
                edit.setPreferredHeight(preferredHeight == null ? null : preferredHeight.toString());
                edit.setPreferredWidth(preferredWidth == null ? null : preferredWidth.toString());
                edit.setPreferredMimeTypeName(preferredMimeType == null ? null : preferredMimeType.getLastDetail().getMimeTypeName());
                edit.setQuality(quality == null ? null : quality.toString());
                edit.setScaleFromParent(itemImageDescriptionType.getScaleFromParent().toString());
            }
        }
    }

    @Override
    public void canUpdate(ItemDescriptionType itemDescriptionType) {
        var itemControl = Session.getModelController(ItemControl.class);
        String itemDescriptionTypeName = edit.getItemDescriptionTypeName();
        ItemDescriptionType duplicateItemDescriptionType = itemControl.getItemDescriptionTypeByName(itemDescriptionTypeName);

        if(duplicateItemDescriptionType == null || itemDescriptionType.equals(duplicateItemDescriptionType)) {
            String parentItemDescriptionTypeName = edit.getParentItemDescriptionTypeName();
            
            parentItemDescriptionType = parentItemDescriptionTypeName == null? null: itemControl.getItemDescriptionTypeByName(parentItemDescriptionTypeName);

            if(parentItemDescriptionTypeName == null || parentItemDescriptionType != null) {
                mimeTypeUsageType = itemDescriptionType.getLastDetail().getMimeTypeUsageType();

                if(parentItemDescriptionType != null) {
                    if(itemControl.isParentItemDescriptionTypeSafe(itemDescriptionType, parentItemDescriptionType)) {
                        MimeTypeUsageType parentMimeTypeUsageType = parentItemDescriptionType.getLastDetail().getMimeTypeUsageType();
                        boolean invalidMimeTypeUsageType = false;

                        // Either the parent's and the new type's MimeTypeUsageTypes must match, or both must be null.
                        if(parentMimeTypeUsageType != null) {
                            if(!parentMimeTypeUsageType.equals(mimeTypeUsageType)) {
                                invalidMimeTypeUsageType = true;
                            }
                        } else if(mimeTypeUsageType != null) {
                            invalidMimeTypeUsageType = true;
                        }

                        if(invalidMimeTypeUsageType) {
                            addExecutionError(ExecutionErrors.InvalidMimeTypeUsageType.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.InvalidParentItemDescriptionType.name());
                    }
                }

                if(!hasExecutionErrors()) {
                    var coreControl = getCoreControl();
                    String preferredMimeTypeName = edit.getPreferredMimeTypeName();

                    preferredMimeType = preferredMimeTypeName == null ? null : coreControl.getMimeTypeByName(preferredMimeTypeName);

                    if(preferredMimeTypeName == null || preferredMimeType != null) {
                        if(preferredMimeType != null && mimeTypeUsageType != null) {
                            MimeTypeUsage mimeTypeUsage = coreControl.getMimeTypeUsage(preferredMimeType, mimeTypeUsageType);

                            if(mimeTypeUsage == null) {
                                addExecutionError(ExecutionErrors.UnknownMimeTypeUsage.name());
                            }
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownPreferredMimeTypeName.name(), preferredMimeType);
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownParentItemDescriptionTypeName.name(), parentItemDescriptionTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateItemDescriptionTypeName.name(), itemDescriptionTypeName);
        }
    }
    
    @Override
    public void doUpdate(ItemDescriptionType itemDescriptionType) {
        var itemControl = Session.getModelController(ItemControl.class);
        var partyPK = getPartyPK();
        ItemDescriptionTypeDetailValue itemDescriptionTypeDetailValue = itemControl.getItemDescriptionTypeDetailValueForUpdate(itemDescriptionType);
        ItemDescriptionTypeDescription itemDescriptionTypeDescription = itemControl.getItemDescriptionTypeDescriptionForUpdate(itemDescriptionType, getPreferredLanguage());
        String description = edit.getDescription();

        itemDescriptionTypeDetailValue.setItemDescriptionTypeName(edit.getItemDescriptionTypeName());
        itemDescriptionTypeDetailValue.setParentItemDescriptionTypePK(parentItemDescriptionType == null? null: parentItemDescriptionType.getPrimaryKey());
        itemDescriptionTypeDetailValue.setUseParentIfMissing(Boolean.valueOf(edit.getUseParentIfMissing()));
        itemDescriptionTypeDetailValue.setCheckContentWebAddress(Boolean.valueOf(edit.getCheckContentWebAddress()));
        itemDescriptionTypeDetailValue.setIncludeInIndex(Boolean.valueOf(edit.getIncludeInIndex()));
        itemDescriptionTypeDetailValue.setIndexDefault(Boolean.valueOf(edit.getIndexDefault()));
        itemDescriptionTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        itemDescriptionTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        itemControl.updateItemDescriptionTypeFromValue(itemDescriptionTypeDetailValue, partyPK);

        if(itemDescriptionTypeDescription == null && description != null) {
            itemControl.createItemDescriptionTypeDescription(itemDescriptionType, getPreferredLanguage(), description, partyPK);
        } else if(itemDescriptionTypeDescription != null && description == null) {
            itemControl.deleteItemDescriptionTypeDescription(itemDescriptionTypeDescription, partyPK);
        } else if(itemDescriptionTypeDescription != null && description != null) {
            ItemDescriptionTypeDescriptionValue itemDescriptionTypeDescriptionValue = itemControl.getItemDescriptionTypeDescriptionValue(itemDescriptionTypeDescription);

            itemDescriptionTypeDescriptionValue.setDescription(description);
            itemControl.updateItemDescriptionTypeDescriptionFromValue(itemDescriptionTypeDescriptionValue, partyPK);
        }

        if(mimeTypeUsageType != null) {
            String mimeTypeUsageTypeName = mimeTypeUsageType.getMimeTypeUsageTypeName();

            if(mimeTypeUsageTypeName.equals(MimeTypeUsageTypes.IMAGE.name())) {
                ItemImageDescriptionTypeValue itemImageDescriptionTypeValue = itemControl.getItemImageDescriptionTypeValueForUpdate(itemDescriptionType);
                String strMinimumHeight = edit.getMinimumHeight();
                String strMinimumWidth = edit.getMinimumWidth();
                String strMaximumHeight = edit.getMaximumHeight();
                String strMaximumWidth = edit.getMaximumWidth();
                String strPreferredHeight = edit.getPreferredHeight();
                String strPreferredWidth = edit.getPreferredWidth();
                String strQuality = edit.getQuality();

                itemImageDescriptionTypeValue.setMinimumHeight(strMinimumHeight == null ? null : Integer.valueOf(strMinimumHeight));
                itemImageDescriptionTypeValue.setMinimumWidth(strMinimumWidth == null ? null : Integer.valueOf(strMinimumWidth));
                itemImageDescriptionTypeValue.setMaximumHeight(strMaximumHeight == null ? null : Integer.valueOf(strMaximumHeight));
                itemImageDescriptionTypeValue.setMaximumWidth(strMaximumWidth == null ? null : Integer.valueOf(strMaximumWidth));
                itemImageDescriptionTypeValue.setPreferredHeight(strPreferredHeight == null ? null : Integer.valueOf(strPreferredHeight));
                itemImageDescriptionTypeValue.setPreferredWidth(strPreferredWidth == null ? null : Integer.valueOf(strPreferredWidth));
                itemImageDescriptionTypeValue.setPreferredMimeTypePK(preferredMimeType == null ? null : preferredMimeType.getPrimaryKey());
                itemImageDescriptionTypeValue.setQuality(strQuality == null ? null : Integer.valueOf(strQuality));
                itemImageDescriptionTypeValue.setScaleFromParent(Boolean.valueOf(edit.getScaleFromParent()));

                ItemDescriptionLogic.getInstance().updateItemImageDescriptionTypeFromValue(itemImageDescriptionTypeValue, partyPK);
            }
        }
    }
    
}
