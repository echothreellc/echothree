// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.control.user.rating.remote.edit.RatingEditFactory;
import com.echothree.control.user.rating.remote.edit.RatingTypeListItemDescriptionEdit;
import com.echothree.control.user.rating.remote.form.EditRatingTypeListItemDescriptionForm;
import com.echothree.control.user.rating.remote.result.EditRatingTypeListItemDescriptionResult;
import com.echothree.control.user.rating.remote.result.RatingResultFactory;
import com.echothree.control.user.rating.remote.spec.RatingTypeListItemDescriptionSpec;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.rating.server.RatingControl;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.rating.server.entity.RatingType;
import com.echothree.model.data.rating.server.entity.RatingTypeListItem;
import com.echothree.model.data.rating.server.entity.RatingTypeListItemDescription;
import com.echothree.model.data.rating.server.value.RatingTypeListItemDescriptionValue;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.remote.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditRatingTypeListItemDescriptionCommand
        extends BaseEditCommand<RatingTypeListItemDescriptionSpec, RatingTypeListItemDescriptionEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
                new FieldDefinition("RatingTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("RatingTypeListItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditRatingTypeListItemDescriptionCommand */
    public EditRatingTypeListItemDescriptionCommand(UserVisitPK userVisitPK, EditRatingTypeListItemDescriptionForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        CoreControl coreControl = getCoreControl();
        EditRatingTypeListItemDescriptionResult result = RatingResultFactory.getEditRatingTypeListItemDescriptionResult();
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
                    String ratingTypeListItemName = spec.getRatingTypeListItemName();
                    RatingTypeListItem ratingTypeListItem = ratingControl.getRatingTypeListItemByName(ratingType, ratingTypeListItemName);
                    
                    if(ratingTypeListItem != null) {
                        PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                        String languageIsoName = spec.getLanguageIsoName();
                        Language language = partyControl.getLanguageByIsoName(languageIsoName);
                        
                        if(language != null) {
                            if(editMode.equals(EditMode.LOCK)) {
                                RatingTypeListItemDescription ratingTypeListItemDescription = ratingControl.getRatingTypeListItemDescription(ratingTypeListItem, language);
                                
                                if(ratingTypeListItemDescription != null) {
                                    result.setRatingTypeListItemDescription(ratingControl.getRatingTypeListItemDescriptionTransfer(getUserVisit(), ratingTypeListItemDescription));
                                    
                                    if(lockEntity(ratingTypeListItem)) {
                                        RatingTypeListItemDescriptionEdit edit = RatingEditFactory.getRatingTypeListItemDescriptionEdit();
                                        
                                        result.setEdit(edit);
                                        edit.setDescription(ratingTypeListItemDescription.getDescription());
                                    } else {
                                        addExecutionError(ExecutionErrors.EntityLockFailed.name());
                                    }
                                    
                                    result.setEntityLock(getEntityLockTransfer(ratingTypeListItem));
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownRatingTypeListItemDescription.name());
                                }
                            } else if(editMode.equals(EditMode.UPDATE)) {
                                RatingTypeListItemDescriptionValue ratingTypeListItemDescriptionValue = ratingControl.getRatingTypeListItemDescriptionValueForUpdate(ratingTypeListItem, language);
                                
                                if(ratingTypeListItemDescriptionValue != null) {
                                    if(lockEntityForUpdate(ratingTypeListItem)) {
                                        try {
                                            String description = edit.getDescription();
                                            
                                            ratingTypeListItemDescriptionValue.setDescription(description);
                                            
                                            ratingControl.updateRatingTypeListItemDescriptionFromValue(ratingTypeListItemDescriptionValue, getPartyPK());
                                        } finally {
                                            unlockEntity(ratingTypeListItem);
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.EntityLockStale.name());
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownRatingTypeListItemDescription.name());
                                }
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownRatingTypeListItemName.name(), ratingTypeListItemName);
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
