// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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
import com.echothree.control.user.rating.common.edit.RatingTypeEdit;
import com.echothree.control.user.rating.common.form.EditRatingTypeForm;
import com.echothree.control.user.rating.common.result.EditRatingTypeResult;
import com.echothree.control.user.rating.common.result.RatingResultFactory;
import com.echothree.control.user.rating.common.spec.RatingTypeSpec;
import com.echothree.model.control.rating.server.control.RatingControl;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.rating.server.entity.RatingType;
import com.echothree.model.data.rating.server.entity.RatingTypeDescription;
import com.echothree.model.data.rating.server.entity.RatingTypeDetail;
import com.echothree.model.data.rating.server.value.RatingTypeDescriptionValue;
import com.echothree.model.data.rating.server.value.RatingTypeDetailValue;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.sequence.server.entity.SequenceType;
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

public class EditRatingTypeCommand
        extends BaseEditCommand<RatingTypeSpec, RatingTypeEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
                new FieldDefinition("RatingTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("RatingTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("RatingSequenceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditRatingTypeCommand */
    public EditRatingTypeCommand(UserVisitPK userVisitPK, EditRatingTypeForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var coreControl = getCoreControl();
        EditRatingTypeResult result = RatingResultFactory.getEditRatingTypeResult();
        String componentVendorName = spec.getComponentVendorName();
        ComponentVendor componentVendor = coreControl.getComponentVendorByName(componentVendorName);
        
        if(componentVendor != null) {
            String entityTypeName = spec.getEntityTypeName();
            EntityType entityType = coreControl.getEntityTypeByName(componentVendor, entityTypeName);
            
            if(entityType != null) {
                var ratingControl = Session.getModelController(RatingControl.class);
                
                if(editMode.equals(EditMode.LOCK)) {
                    String ratingTypeName = spec.getRatingTypeName();
                    RatingType ratingType = ratingControl.getRatingTypeByName(entityType, ratingTypeName);
                    
                    if(ratingType != null) {
                        result.setRatingType(ratingControl.getRatingTypeTransfer(getUserVisit(), ratingType));
                        
                        if(lockEntity(ratingType)) {
                            RatingTypeDescription ratingTypeDescription = ratingControl.getRatingTypeDescription(ratingType, getPreferredLanguage());
                            RatingTypeEdit edit = RatingEditFactory.getRatingTypeEdit();
                            RatingTypeDetail ratingTypeDetail = ratingType.getLastDetail();
                            Sequence ratingSequence = ratingTypeDetail.getRatingSequence();
                            
                            result.setEdit(edit);
                            edit.setRatingTypeName(ratingTypeDetail.getRatingTypeName());
                            edit.setRatingSequenceName(ratingSequence == null? null: ratingSequence.getLastDetail().getSequenceName());
                            edit.setSortOrder(ratingTypeDetail.getSortOrder().toString());
                            
                            if(ratingTypeDescription != null)
                                edit.setDescription(ratingTypeDescription.getDescription());
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                        }
                        
                        result.setEntityLock(getEntityLockTransfer(ratingType));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownRatingTypeName.name(), ratingTypeName);
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    String ratingTypeName = spec.getRatingTypeName();
                    RatingType ratingType = ratingControl.getRatingTypeByNameForUpdate(entityType, ratingTypeName);
                    
                    if(ratingType != null) {
                        ratingTypeName = edit.getRatingTypeName();
                        RatingType duplicateRatingType = ratingControl.getRatingTypeByName(entityType, ratingTypeName);
                        
                        if(duplicateRatingType == null || ratingType.equals(duplicateRatingType)) {
                            String ratingSequenceName = edit.getRatingSequenceName();
                            Sequence ratingSequence = null;
                            
                            if(ratingSequenceName != null) {
                                var sequenceControl = Session.getModelController(SequenceControl.class);
                                SequenceType sequenceType = sequenceControl.getSequenceTypeByName(SequenceTypes.RATING.name());
                                
                                if(sequenceType != null) {
                                    ratingSequence = sequenceControl.getSequenceByName(sequenceType, ratingSequenceName);
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownSequenceTypeName.name(), SequenceTypes.RATING.name());
                                }
                            }
                            
                            if(ratingSequenceName == null || ratingSequence != null) {
                                if(lockEntityForUpdate(ratingType)) {
                                    try {
                                        var partyPK = getPartyPK();
                                        RatingTypeDetailValue ratingTypeDetailValue = ratingControl.getRatingTypeDetailValueForUpdate(ratingType);
                                        RatingTypeDescription ratingTypeDescription = ratingControl.getRatingTypeDescriptionForUpdate(ratingType, getPreferredLanguage());
                                        String description = edit.getDescription();
                                        
                                        ratingTypeDetailValue.setRatingTypeName(edit.getRatingTypeName());
                                        ratingTypeDetailValue.setRatingSequencePK(ratingSequence.getPrimaryKey());
                                        ratingTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                                        
                                        ratingControl.updateRatingTypeFromValue(ratingTypeDetailValue, partyPK);
                                        
                                        if(ratingTypeDescription == null && description != null) {
                                            ratingControl.createRatingTypeDescription(ratingType, getPreferredLanguage(), description, partyPK);
                                        } else if(ratingTypeDescription != null && description == null) {
                                            ratingControl.deleteRatingTypeDescription(ratingTypeDescription, partyPK);
                                        } else if(ratingTypeDescription != null && description != null) {
                                            RatingTypeDescriptionValue ratingTypeDescriptionValue = ratingControl.getRatingTypeDescriptionValue(ratingTypeDescription);
                                            
                                            ratingTypeDescriptionValue.setDescription(description);
                                            ratingControl.updateRatingTypeDescriptionFromValue(ratingTypeDescriptionValue, partyPK);
                                        }
                                    } finally {
                                        unlockEntity(ratingType);
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.EntityLockStale.name());
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownRatingSequenceName.name(), ratingSequenceName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.DuplicateRatingTypeName.name(), ratingTypeName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownRatingTypeName.name(), ratingTypeName);
                    }
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
