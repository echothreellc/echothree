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

import com.echothree.control.user.item.common.form.CreateItemDescriptionTypeForm;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.core.server.control.MimeTypeControl;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.MimeTypeUsageType;
import com.echothree.model.data.item.server.entity.ItemDescriptionType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.form.ValidationResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.validation.Validator;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateItemDescriptionTypeCommand
        extends BaseSimpleCommand<CreateItemDescriptionTypeForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> imageFieldDefinitions;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ItemDescriptionType.name(), SecurityRoles.Create.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemDescriptionTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ParentItemDescriptionTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("UseParentIfMissing", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("MimeTypeUsageTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CheckContentWebAddress", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("IncludeInIndex", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("IndexDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
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
    
    /** Creates a new instance of CreateItemDescriptionTypeCommand */
    public CreateItemDescriptionTypeCommand(UserVisitPK userVisitPK, CreateItemDescriptionTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected ValidationResult validate() {
        var validator = new Validator(this);
        var validationResult = validator.validate(form, FORM_FIELD_DEFINITIONS);

        if(!validationResult.getHasErrors()) {
            var mimeTypeUsageTypeName = form.getMimeTypeUsageTypeName();

            if(mimeTypeUsageTypeName != null) {
                if(mimeTypeUsageTypeName.equals(MimeTypeUsageTypes.IMAGE.name())) {
                    validationResult = validator.validate(form, imageFieldDefinitions);
                }
            }
        }

        return validationResult;
    }
    
    @Override
    protected BaseResult execute() {
        var result = ItemResultFactory.getCreateItemDescriptionTypeResult();
        var itemControl = Session.getModelController(ItemControl.class);
        var itemDescriptionTypeName = form.getItemDescriptionTypeName();
        var itemDescriptionType = itemControl.getItemDescriptionTypeByName(itemDescriptionTypeName);
        
        if(itemDescriptionType == null) {
            var parentItemDescriptionTypeName = form.getParentItemDescriptionTypeName();
            ItemDescriptionType parentItemDescriptionType = null;
            
            if(parentItemDescriptionTypeName != null) {
                parentItemDescriptionType = itemControl.getItemDescriptionTypeByName(parentItemDescriptionTypeName);
            }
            
            if(parentItemDescriptionTypeName == null || parentItemDescriptionType != null) {
                var mimeTypeControl = Session.getModelController(MimeTypeControl.class);
                MimeTypeUsageType mimeTypeUsageType = null;
                var mimeTypeUsageTypeName = form.getMimeTypeUsageTypeName();

                if(mimeTypeUsageTypeName != null) {
                    mimeTypeUsageType = mimeTypeControl.getMimeTypeUsageTypeByName(mimeTypeUsageTypeName);
                }

                if(mimeTypeUsageTypeName == null || mimeTypeUsageType != null) {
                    if(parentItemDescriptionType != null) {
                        var parentMimeTypeUsageType = parentItemDescriptionType.getLastDetail().getMimeTypeUsageType();
                        var invalidMimeTypeUsageType = false;

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
                    }
                    
                    if(!hasExecutionErrors()) {
                        var preferredMimeTypeName = form.getPreferredMimeTypeName();
                        var preferredMimeType = preferredMimeTypeName == null ? null : mimeTypeControl.getMimeTypeByName(preferredMimeTypeName);

                        if(preferredMimeTypeName == null || preferredMimeType != null) {
                            if(preferredMimeType != null && mimeTypeUsageType != null) {
                                var mimeTypeUsage = mimeTypeControl.getMimeTypeUsage(preferredMimeType, mimeTypeUsageType);

                                if(mimeTypeUsage == null) {
                                    addExecutionError(ExecutionErrors.UnknownMimeTypeUsage.name());
                                }
                            }

                            if(!hasExecutionErrors()) {
                                var partyPK = getPartyPK();
                                var useParentIfMissing = Boolean.valueOf(form.getUseParentIfMissing());
                                var includeInIndex = Boolean.valueOf(form.getIncludeInIndex());
                                var checkContentWebAddress = Boolean.valueOf(form.getCheckContentWebAddress());
                                var indexDefault = Boolean.valueOf(form.getIndexDefault());
                                var isDefault = Boolean.valueOf(form.getIsDefault());
                                var sortOrder = Integer.valueOf(form.getSortOrder());
                                var description = form.getDescription();

                                itemDescriptionType = itemControl.createItemDescriptionType(itemDescriptionTypeName, parentItemDescriptionType,
                                        useParentIfMissing, mimeTypeUsageType, checkContentWebAddress, includeInIndex, indexDefault, isDefault, sortOrder,
                                        getPartyPK());

                                if(description != null) {
                                    itemControl.createItemDescriptionTypeDescription(itemDescriptionType, getPreferredLanguage(), description, partyPK);
                                }

                                if(mimeTypeUsageType != null) {
                                    mimeTypeUsageTypeName = mimeTypeUsageType.getMimeTypeUsageTypeName();

                                    if(mimeTypeUsageTypeName.equals(MimeTypeUsageTypes.IMAGE.name())) {
                                        var strMinimumHeight = form.getMinimumHeight();
                                        var minimumHeight = strMinimumHeight == null ? null : Integer.valueOf(strMinimumHeight);
                                        var strMinimumWidth = form.getMinimumWidth();
                                        var minimumWidth = strMinimumWidth == null ? null : Integer.valueOf(strMinimumWidth);
                                        var strMaximumHeight = form.getMaximumHeight();
                                        var maximumHeight = strMaximumHeight == null ? null : Integer.valueOf(strMaximumHeight);
                                        var strMaximumWidth = form.getMaximumWidth();
                                        var maximumWidth = strMaximumWidth == null ? null : Integer.valueOf(strMaximumWidth);
                                        var strPreferredHeight = form.getPreferredHeight();
                                        var preferredHeight = strPreferredHeight == null ? null : Integer.valueOf(strPreferredHeight);
                                        var strPreferredWidth = form.getPreferredWidth();
                                        var preferredWidth = strPreferredWidth == null ? null : Integer.valueOf(strPreferredWidth);
                                        var strQuality = form.getQuality();
                                        var quality = strQuality == null ? null : Integer.valueOf(strQuality);
                                        var scaleFromParent = Boolean.valueOf(form.getScaleFromParent());

                                        itemControl.createItemImageDescriptionType(itemDescriptionType, minimumHeight, minimumWidth, maximumHeight, maximumWidth,
                                                preferredHeight, preferredWidth, preferredMimeType, quality, scaleFromParent, partyPK);
                                    }
                                }
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownPreferredMimeTypeName.name(), preferredMimeType);
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownMimeTypeUsageTypeName.name(), mimeTypeUsageTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownParentItemDescriptionTypeName.name(), parentItemDescriptionTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateItemDescriptionTypeName.name(), itemDescriptionTypeName);
        }

        if(itemDescriptionType != null) {
            result.setItemDescriptionTypeName(itemDescriptionType.getLastDetail().getItemDescriptionTypeName());
            result.setEntityRef(itemDescriptionType.getPrimaryKey().getEntityRef());
        }

        return result;
    }
    
}
