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

import com.echothree.control.user.item.common.edit.ItemDescriptionEdit;
import com.echothree.control.user.item.common.edit.ItemEditFactory;
import com.echothree.control.user.item.common.form.EditItemDescriptionForm;
import com.echothree.control.user.item.common.result.EditItemDescriptionResult;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.control.user.item.common.spec.ItemDescriptionSpec;
import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.core.server.control.MimeTypeControl;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.item.server.logic.ItemDescriptionLogic;
import com.echothree.model.control.item.server.logic.ItemDescriptionLogic.ImageDimensions;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemDescription;
import com.echothree.model.data.item.server.entity.ItemDescriptionType;
import com.echothree.model.data.item.server.entity.ItemImageType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.common.persistence.type.ByteArray;
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

public class EditItemDescriptionCommand
        extends BaseAbstractEditCommand<ItemDescriptionSpec, ItemDescriptionEdit, EditItemDescriptionResult, ItemDescription, ItemDescription> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ItemDescription.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("ItemDescriptionTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("MimeTypeName", FieldType.MIME_TYPE, false, null, null),
            new FieldDefinition("ItemImageTypeName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("ClobDescription", FieldType.STRING, false, 1L, null),
            new FieldDefinition("StringDescription", FieldType.STRING, false, 1L, 512L)
        ));
    }
    
    /** Creates a new instance of EditItemDescriptionCommand */
    public EditItemDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditItemDescriptionResult getResult() {
        return ItemResultFactory.getEditItemDescriptionResult();
    }

    @Override
    public ItemDescriptionEdit getEdit() {
        return ItemEditFactory.getItemDescriptionEdit();
    }

     ItemDescriptionType itemDescriptionType;
     Item item;

    @Override
    public ItemDescription getEntity(EditItemDescriptionResult result) {
        var itemControl = Session.getModelController(ItemControl.class);
        ItemDescription itemDescription = null;
        var itemDescriptionTypeName = spec.getItemDescriptionTypeName();

        itemDescriptionType = itemControl.getItemDescriptionTypeByName(itemDescriptionTypeName);

        if(itemDescriptionType != null) {
            var itemName = spec.getItemName();

            item = itemControl.getItemByName(itemName);

            if(item != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                var languageIsoName = spec.getLanguageIsoName();
                var language = partyControl.getLanguageByIsoName(languageIsoName);

                if(language != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        itemDescription = itemControl.getItemDescription(itemDescriptionType, item, language);
                    } else { // EditMode.UPDATE
                        itemDescription = itemControl.getItemDescriptionForUpdate(itemDescriptionType, item, language);
                    }

                    if(itemDescription == null) {
                        addExecutionError(ExecutionErrors.UnknownItemDescription.name(), itemDescriptionTypeName, itemName, languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownItemName.name(), itemName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownItemDescriptionTypeName.name(), itemDescriptionTypeName);
        }

        return itemDescription;
    }

    @Override
    public ItemDescription getLockEntity(ItemDescription itemDescription) {
        return itemDescription;
    }

    @Override
    public void fillInResult(EditItemDescriptionResult result, ItemDescription itemDescription) {
        var itemControl = Session.getModelController(ItemControl.class);

        result.setItemDescription(itemControl.getItemDescriptionTransfer(getUserVisit(), itemDescription));
    }

    MimeType mimeType;

    @Override
    public void doLock(ItemDescriptionEdit edit, ItemDescription itemDescription) {
        var itemControl = Session.getModelController(ItemControl.class);
        
        mimeType = itemDescription.getLastDetail().getMimeType();

        edit.setMimeTypeName(mimeType == null? null: mimeType.getLastDetail().getMimeTypeName());

        if(mimeType == null) {
            var itemStringDescription = itemControl.getItemStringDescription(itemDescription);

            if(itemStringDescription != null) {
                edit.setStringDescription(itemStringDescription.getStringDescription());
            }
        } else {
            var entityAttributeTypeName = mimeType.getLastDetail().getEntityAttributeType().getEntityAttributeTypeName();

            // EntityAttributeTypes.BLOB.name() does not return anything in edit
            if(entityAttributeTypeName.equals(EntityAttributeTypes.CLOB.name())) {
                var itemClobDescription = itemControl.getItemClobDescription(itemDescription);

                if(itemClobDescription != null) {
                    edit.setClobDescription(itemClobDescription.getClobDescription());
                }
            }
        }
    }

    ItemImageType itemImageType;
    ImageDimensions imageDimensions;

    @Override
    public void canUpdate(ItemDescription itemDescription) {
        var mimeTypeName = edit.getMimeTypeName();

        if(mimeTypeName == null) {
            if(itemDescriptionType.getLastDetail().getMimeTypeUsageType() == null) {
                var stringDescription = edit.getStringDescription();

                if(stringDescription == null) {
                    addExecutionError(ExecutionErrors.MissingStringDescription.name());
                }
            } else {
                // No mimeTypeName was supplied, but yet we required a MimeTypeUsageType
                addExecutionError(ExecutionErrors.InvalidMimeType.name());
            }
        } else {
            var mimeTypeControl = Session.getModelController(MimeTypeControl.class);

            mimeType = mimeTypeControl.getMimeTypeByName(mimeTypeName);

            if(mimeType != null) {
                var mimeTypeUsageType = itemDescriptionType.getLastDetail().getMimeTypeUsageType();

                if(mimeTypeUsageType != null) {
                    var mimeTypeUsage = mimeTypeControl.getMimeTypeUsage(mimeType, mimeTypeUsageType);

                    if(mimeTypeUsage != null) {
                        var mimeTypeDetail = mimeType.getLastDetail();
                        var entityAttributeType = mimeTypeDetail.getEntityAttributeType();
                        var entityAttributeTypeName = entityAttributeType.getEntityAttributeTypeName();

                        if(entityAttributeTypeName.equals(EntityAttributeTypes.BLOB.name())) {
                            var blobDescription = edit.getBlobDescription();

                            if(blobDescription == null) {
                                addExecutionError(ExecutionErrors.MissingBlobDescription.name());
                            } else {
                                var mimeTypeUsageTypeName = mimeTypeUsageType.getMimeTypeUsageTypeName();

                                if(mimeTypeUsageTypeName.equals(MimeTypeUsageTypes.IMAGE.name())) {
                                    var itemImageTypeName = edit.getItemImageTypeName();

                                    if(itemImageTypeName != null) {
                                        var itemControl = Session.getModelController(ItemControl.class);

                                        itemImageType = itemControl.getItemImageTypeByName(itemImageTypeName);

                                        if(itemImageType != null) {
                                            imageDimensions = ItemDescriptionLogic.getInstance().getImageDimensions(mimeTypeDetail.getMimeTypeName(), blobDescription);

                                            if(imageDimensions == null) {
                                                addExecutionError(ExecutionErrors.InvalidImage.name());
                                            } else {
                                                var itemImageDescriptionType = itemControl.getItemImageDescriptionType(itemDescriptionType);

                                                if(itemImageDescriptionType != null) {
                                                    var minimumHeight = itemImageDescriptionType.getMinimumHeight();
                                                    var minimumWidth = itemImageDescriptionType.getMinimumWidth();
                                                    var maximumHeight = itemImageDescriptionType.getMaximumHeight();
                                                    var maximumWidth = itemImageDescriptionType.getMaximumWidth();
                                                    var imageHeight = imageDimensions.getHeight();
                                                    var imageWidth = imageDimensions.getWidth();

                                                    if(minimumHeight != null && imageHeight < minimumHeight) {
                                                        addExecutionError(ExecutionErrors.ImageHeightLessThanMinimum.name(), imageHeight.toString(), minimumHeight.toString());
                                                    }

                                                    if(minimumWidth != null && imageWidth < minimumWidth) {
                                                        addExecutionError(ExecutionErrors.ImageWidthLessThanMinimum.name(), imageWidth.toString(), minimumWidth.toString());
                                                    }

                                                    if(maximumHeight != null && imageHeight > maximumHeight) {
                                                        addExecutionError(ExecutionErrors.ImageHeightGreaterThanMaximum.name(), imageHeight.toString(), maximumHeight.toString());
                                                    }

                                                    if(maximumWidth != null && imageWidth > maximumWidth) {
                                                        addExecutionError(ExecutionErrors.ImageWidthGreaterThanMaximum.name(), imageWidth.toString(), maximumWidth.toString());
                                                    }
                                                }
                                            }
                                        } else {
                                          addExecutionError(ExecutionErrors.UnknownItemImageTypeName.name(), itemImageTypeName);
                                        }
                                    } else {
                                          addExecutionError(ExecutionErrors.MissingRequiredItemImageTypeName.name());
                                    }
                                }
                            }
                        } else if(entityAttributeTypeName.equals(EntityAttributeTypes.CLOB.name())) {
                            var clobDescription = edit.getClobDescription();

                            if(clobDescription == null) {
                                addExecutionError(ExecutionErrors.MissingClobDescription.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownEntityAttributeTypeName.name(),
                                    entityAttributeTypeName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownMimeTypeUsage.name());
                    }
                } else {
                    // mimeTypeName was supplied, and there shouldn't be one
                    addExecutionError(ExecutionErrors.InvalidMimeType.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownMimeTypeName.name(), mimeTypeName);
            }
        }
    }

    @Override
    public void doUpdate(ItemDescription itemDescription) {
        var itemControl = Session.getModelController(ItemControl.class);
        var partyPK = getPartyPK();

        if(mimeType == null) {
            var stringDescription = edit.getStringDescription();

            updateItemDescription(itemControl, item, null, partyPK, itemDescription, null, null, stringDescription);
        } else {
            var entityAttributeType = mimeType.getLastDetail().getEntityAttributeType();
            var entityAttributeTypeName = entityAttributeType.getEntityAttributeTypeName();

            if(entityAttributeTypeName.equals(EntityAttributeTypes.BLOB.name())) {
                var blobDescription = edit.getBlobDescription();

                updateItemDescription(itemControl, item, mimeType, partyPK, itemDescription, blobDescription, null, null);
            } else {
                if(entityAttributeTypeName.equals(EntityAttributeTypes.CLOB.name())) {
                    var clobDescription = edit.getClobDescription();

                    updateItemDescription(itemControl, item, mimeType, partyPK, itemDescription, null, clobDescription, null);
                }
            }
        }
    }

    protected void updateItemDescription(ItemControl itemControl, Item item, MimeType mimeType, BasePK updatedBy,
            ItemDescription itemDescription, ByteArray blobDescription, String clobDescription, String stringDescription) {
        var itemDescriptionDetailValue = itemControl.getItemDescriptionDetailValueForUpdate(itemDescription);

        itemDescriptionDetailValue.setMimeTypePK(mimeType == null? null: mimeType.getPrimaryKey());
        itemControl.updateItemDescriptionFromValue(itemDescriptionDetailValue, updatedBy);

        var itemBlobDescription = itemControl.getItemBlobDescriptionForUpdate(itemDescription);

        if(itemBlobDescription != null) {
            if(blobDescription == null) {
                itemControl.deleteItemBlobDescription(itemBlobDescription, updatedBy);
                itemControl.deleteItemImageDescriptionByItemDescription(itemDescription, updatedBy);
            } else {
                var itemBlobDescriptionValue = itemControl.getItemBlobDescriptionValue(itemBlobDescription);
                var itemImageDescriptionValue = itemControl.getItemImageDescriptionValueForUpdate(itemDescription);

                itemBlobDescriptionValue.setBlobDescription(blobDescription);
                itemControl.updateItemBlobDescriptionFromValue(itemBlobDescriptionValue, updatedBy);

                if(imageDimensions != null) {
                    itemImageDescriptionValue.setHeight(imageDimensions.getHeight());
                    itemImageDescriptionValue.setWidth(imageDimensions.getWidth());
                    itemImageDescriptionValue.setScaledFromParent(false);

                    itemControl.updateItemImageDescriptionFromValue(itemImageDescriptionValue, updatedBy);
                }

                if(itemBlobDescriptionValue.hasBeenModified()) {
                    ItemDescriptionLogic.getInstance().deleteItemImageDescriptionChildren(itemDescription, updatedBy);
                }
            }
        } else if(blobDescription != null) {
            itemControl.createItemBlobDescription(itemDescription, blobDescription, updatedBy);

            if(imageDimensions != null) {
                itemControl.createItemImageDescription(itemDescription, itemImageType, imageDimensions.getHeight(), imageDimensions.getWidth(), false,
                        updatedBy);
            }

            ItemDescriptionLogic.getInstance().deleteItemImageDescriptionChildren(itemDescription, updatedBy);
        }

        var itemClobDescription = itemControl.getItemClobDescriptionForUpdate(itemDescription);

        if(itemClobDescription != null) {
            if(clobDescription == null) {
                itemControl.deleteItemClobDescription(itemClobDescription, updatedBy);
            } else {
                var itemClobDescriptionValue = itemControl.getItemClobDescriptionValue(itemClobDescription);

                itemClobDescriptionValue.setClobDescription(clobDescription);
                itemControl.updateItemClobDescriptionFromValue(itemClobDescriptionValue, updatedBy);
            }
        } else if(clobDescription != null) {
            itemControl.createItemClobDescription(itemDescription, clobDescription, updatedBy);
        }

        var itemStringDescription = itemControl.getItemStringDescriptionForUpdate(itemDescription);

        if(itemStringDescription != null) {
            if(stringDescription == null) {
                itemControl.deleteItemStringDescription(itemStringDescription, updatedBy);
            } else {
                var itemStringDescriptionValue = itemControl.getItemStringDescriptionValue(itemStringDescription);

                itemStringDescriptionValue.setStringDescription(stringDescription);
                itemControl.updateItemStringDescriptionFromValue(itemStringDescriptionValue, updatedBy);
            }
        } else if(stringDescription != null) {
            itemControl.createItemStringDescription(itemDescription, stringDescription, updatedBy);
        }
    }
    
}
