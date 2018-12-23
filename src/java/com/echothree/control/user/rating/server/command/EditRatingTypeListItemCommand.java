// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.control.user.rating.server.command;

import com.echothree.control.user.rating.common.edit.RatingEditFactory;
import com.echothree.control.user.rating.common.edit.RatingTypeListItemEdit;
import com.echothree.control.user.rating.common.form.EditRatingTypeListItemForm;
import com.echothree.control.user.rating.common.result.EditRatingTypeListItemResult;
import com.echothree.control.user.rating.common.result.RatingResultFactory;
import com.echothree.control.user.rating.common.spec.RatingTypeListItemSpec;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.rating.server.RatingControl;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.rating.server.entity.RatingType;
import com.echothree.model.data.rating.server.entity.RatingTypeListItem;
import com.echothree.model.data.rating.server.entity.RatingTypeListItemDescription;
import com.echothree.model.data.rating.server.entity.RatingTypeListItemDetail;
import com.echothree.model.data.rating.server.value.RatingTypeListItemDescriptionValue;
import com.echothree.model.data.rating.server.value.RatingTypeListItemDetailValue;
import com.echothree.model.data.user.common.pk.UserVisitPK;
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

public class EditRatingTypeListItemCommand
        extends BaseEditCommand<RatingTypeListItemSpec, RatingTypeListItemEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
                new FieldDefinition("RatingTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("RatingTypeListItemName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("RatingTypeListItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditRatingTypeListItemCommand */
    public EditRatingTypeListItemCommand(UserVisitPK userVisitPK, EditRatingTypeListItemForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        CoreControl coreControl = getCoreControl();
        EditRatingTypeListItemResult result = RatingResultFactory.getEditRatingTypeListItemResult();
        String componentVendorName = spec.getComponentVendorName();
        ComponentVendor componentVendor = coreControl.getComponentVendorByName(componentVendorName);
        
        if(componentVendor != null) {
            String entityTypeName = spec.getEntityTypeName();
            EntityType entityType = coreControl.getEntityTypeByName(componentVendor, entityTypeName);
            
            if(entityType != null) {
                RatingControl ratingControl = (RatingControl)Session.getModelController(RatingControl.class);
                String ratingTypeName = spec.getRatingTypeName();
                RatingType ratingType = ratingControl.getRatingTypeByName(entityType, ratingTypeName);
                
                if(ratingType != null) {
                    if(editMode.equals(EditMode.LOCK)) {
                        String ratingTypeListItemName = spec.getRatingTypeListItemName();
                        RatingTypeListItem ratingTypeListItem = ratingControl.getRatingTypeListItemByName(ratingType, ratingTypeListItemName);
                        
                        if(ratingTypeListItem != null) {
                            result.setRatingTypeListItem(ratingControl.getRatingTypeListItemTransfer(getUserVisit(), ratingTypeListItem));
                            
                            if(lockEntity(ratingTypeListItem)) {
                                RatingTypeListItemDescription ratingTypeListItemDescription = ratingControl.getRatingTypeListItemDescription(ratingTypeListItem, getPreferredLanguage());
                                RatingTypeListItemEdit edit = RatingEditFactory.getRatingTypeListItemEdit();
                                RatingTypeListItemDetail ratingTypeListItemDetail = ratingTypeListItem.getLastDetail();
                                
                                result.setEdit(edit);
                                edit.setRatingTypeListItemName(ratingTypeListItemDetail.getRatingTypeListItemName());
                                edit.setIsDefault(ratingTypeListItemDetail.getIsDefault().toString());
                                edit.setSortOrder(ratingTypeListItemDetail.getSortOrder().toString());
                                
                                if(ratingTypeListItemDescription != null)
                                    edit.setDescription(ratingTypeListItemDescription.getDescription());
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockFailed.name());
                            }
                            
                            result.setEntityLock(getEntityLockTransfer(ratingTypeListItem));
                        } else {
                            addExecutionError(ExecutionErrors.UnknownRatingTypeListItemName.name(), ratingTypeListItemName);
                        }
                    } else if(editMode.equals(EditMode.UPDATE)) {
                        String ratingTypeListItemName = spec.getRatingTypeListItemName();
                        RatingTypeListItem ratingTypeListItem = ratingControl.getRatingTypeListItemByNameForUpdate(ratingType, ratingTypeListItemName);
                        
                        if(ratingTypeListItem != null) {
                            ratingTypeListItemName = edit.getRatingTypeListItemName();
                            RatingTypeListItem duplicateRatingTypeListItem = ratingControl.getRatingTypeListItemByName(ratingType, ratingTypeListItemName);
                            
                            if(duplicateRatingTypeListItem == null || ratingTypeListItem.equals(duplicateRatingTypeListItem)) {
                                if(lockEntityForUpdate(ratingTypeListItem)) {
                                    try {
                                        PartyPK partyPK = getPartyPK();
                                        RatingTypeListItemDetailValue ratingTypeListItemDetailValue = ratingControl.getRatingTypeListItemDetailValueForUpdate(ratingTypeListItem);
                                        RatingTypeListItemDescription ratingTypeListItemDescription = ratingControl.getRatingTypeListItemDescriptionForUpdate(ratingTypeListItem, getPreferredLanguage());
                                        String description = edit.getDescription();
                                        
                                        ratingTypeListItemDetailValue.setRatingTypeListItemName(edit.getRatingTypeListItemName());
                                        ratingTypeListItemDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                                        ratingTypeListItemDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                                        
                                        ratingControl.updateRatingTypeListItemFromValue(ratingTypeListItemDetailValue, partyPK);
                                        
                                        if(ratingTypeListItemDescription == null && description != null) {
                                            ratingControl.createRatingTypeListItemDescription(ratingTypeListItem, getPreferredLanguage(), description, partyPK);
                                        } else if(ratingTypeListItemDescription != null && description == null) {
                                            ratingControl.deleteRatingTypeListItemDescription(ratingTypeListItemDescription, partyPK);
                                        } else if(ratingTypeListItemDescription != null && description != null) {
                                            RatingTypeListItemDescriptionValue ratingTypeListItemDescriptionValue = ratingControl.getRatingTypeListItemDescriptionValue(ratingTypeListItemDescription);
                                            
                                            ratingTypeListItemDescriptionValue.setDescription(description);
                                            ratingControl.updateRatingTypeListItemDescriptionFromValue(ratingTypeListItemDescriptionValue, partyPK);
                                        }
                                    } finally {
                                        unlockEntity(ratingTypeListItem);
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.EntityLockStale.name());
                                }
                            } else {
                                addExecutionError(ExecutionErrors.DuplicateRatingTypeListItemName.name(), ratingTypeListItemName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownRatingTypeListItemName.name(), ratingTypeListItemName);
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownRatingTypeName.name(), ratingTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownEntityTypeName.name(), entityTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownComponentVendorName.name(), componentVendorName);
        }
        
        return result;
    }
    
}
