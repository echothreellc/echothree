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
import com.echothree.control.user.rating.common.edit.RatingTypeDescriptionEdit;
import com.echothree.control.user.rating.common.form.EditRatingTypeDescriptionForm;
import com.echothree.control.user.rating.common.result.EditRatingTypeDescriptionResult;
import com.echothree.control.user.rating.common.result.RatingResultFactory;
import com.echothree.control.user.rating.common.spec.RatingTypeDescriptionSpec;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.rating.server.RatingControl;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.rating.server.entity.RatingType;
import com.echothree.model.data.rating.server.entity.RatingTypeDescription;
import com.echothree.model.data.rating.server.value.RatingTypeDescriptionValue;
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

public class EditRatingTypeDescriptionCommand
        extends BaseEditCommand<RatingTypeDescriptionSpec, RatingTypeDescriptionEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
                new FieldDefinition("RatingTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditRatingTypeDescriptionCommand */
    public EditRatingTypeDescriptionCommand(UserVisitPK userVisitPK, EditRatingTypeDescriptionForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        CoreControl coreControl = getCoreControl();
        EditRatingTypeDescriptionResult result = RatingResultFactory.getEditRatingTypeDescriptionResult();
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
                    PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                    String languageIsoName = spec.getLanguageIsoName();
                    Language language = partyControl.getLanguageByIsoName(languageIsoName);
                    
                    if(language != null) {
                        if(editMode.equals(EditMode.LOCK)) {
                            RatingTypeDescription ratingTypeDescription = ratingControl.getRatingTypeDescription(ratingType, language);
                            
                            if(ratingTypeDescription != null) {
                                result.setRatingTypeDescription(ratingControl.getRatingTypeDescriptionTransfer(getUserVisit(), ratingTypeDescription));
                                
                                if(lockEntity(ratingType)) {
                                    RatingTypeDescriptionEdit edit = RatingEditFactory.getRatingTypeDescriptionEdit();
                                    
                                    result.setEdit(edit);
                                    edit.setDescription(ratingTypeDescription.getDescription());
                                } else {
                                    addExecutionError(ExecutionErrors.EntityLockFailed.name());
                                }
                                
                                result.setEntityLock(getEntityLockTransfer(ratingType));
                            } else {
                                addExecutionError(ExecutionErrors.UnknownRatingTypeDescription.name());
                            }
                        } else if(editMode.equals(EditMode.UPDATE)) {
                            RatingTypeDescriptionValue ratingTypeDescriptionValue = ratingControl.getRatingTypeDescriptionValueForUpdate(ratingType, language);
                            
                            if(ratingTypeDescriptionValue != null) {
                                if(lockEntityForUpdate(ratingType)) {
                                    try {
                                        String description = edit.getDescription();
                                        
                                        ratingTypeDescriptionValue.setDescription(description);
                                        
                                        ratingControl.updateRatingTypeDescriptionFromValue(ratingTypeDescriptionValue, getPartyPK());
                                    } finally {
                                        unlockEntity(ratingType);
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.EntityLockStale.name());
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownRatingTypeDescription.name());
                            }
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
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
