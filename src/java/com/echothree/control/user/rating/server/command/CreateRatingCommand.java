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

package com.echothree.control.user.rating.server.command;

import com.echothree.control.user.rating.common.form.CreateRatingForm;
import com.echothree.control.user.rating.common.result.CreateRatingResult;
import com.echothree.control.user.rating.common.result.RatingResultFactory;
import com.echothree.model.control.rating.server.control.RatingControl;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.rating.server.entity.Rating;
import com.echothree.model.data.rating.server.entity.RatingType;
import com.echothree.model.data.rating.server.entity.RatingTypeListItem;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserLogin;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateRatingCommand
        extends BaseSimpleCommand<CreateRatingForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("RatedByUsername", FieldType.STRING, false, 1L, 80L),
            new FieldDefinition("EntityRef", FieldType.ENTITY_REF, true, null, null),
            new FieldDefinition("RatingTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("RatingTypeListItemName", FieldType.ENTITY_NAME, true, null, null)
        ));
    }
    
    /** Creates a new instance of CreateRatingCommand */
    public CreateRatingCommand(UserVisitPK userVisitPK, CreateRatingForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        CreateRatingResult result = RatingResultFactory.getCreateRatingResult();
        var coreControl = getCoreControl();
        String ratingName = null;
        String entityRef = form.getEntityRef();
        EntityInstance ratedEntityInstance = coreControl.getEntityInstanceByEntityRef(entityRef);
        
        if(ratedEntityInstance != null) {
            var ratingControl = Session.getModelController(RatingControl.class);
            EntityInstance ratedByEntityInstance = null;
            BasePK createdBy = getPartyPK();
            String ratedByUsername = form.getRatedByUsername();
            
            if(ratedByUsername != null) {
                UserControl userControl = getUserControl();
                UserLogin userLogin = userControl.getUserLoginByUsername(ratedByUsername);
                
                if(userLogin != null) {
                    ratedByEntityInstance = coreControl.getEntityInstanceByBasePK(userLogin.getPartyPK());
                } else {
                    addExecutionError(ExecutionErrors.UnknownRatedByUsername.name(), ratedByUsername);
                }
            } else {
                ratedByEntityInstance = coreControl.getEntityInstanceByBasePK(createdBy);
            }
            
            if(!hasExecutionErrors()) {
                Rating rating = ratingControl.getRating(ratedEntityInstance, ratedByEntityInstance);
                
                if(rating == null) {
                    String ratingTypeName = form.getRatingTypeName();
                    RatingType ratingType = ratingControl.getRatingTypeByName(ratedEntityInstance.getEntityType(),
                            ratingTypeName);
                    
                    if(ratingType != null) {
                        String ratingTypeListItemName = form.getRatingTypeListItemName();
                        RatingTypeListItem ratingTypeListItem = ratingControl.getRatingTypeListItemByName(ratingType, ratingTypeListItemName);
                        
                        if(ratingTypeListItem != null) {
                            var sequenceControl = Session.getModelController(SequenceControl.class);
                            Sequence ratingSequence = ratingType.getLastDetail().getRatingSequence();
                            
                            if(ratingSequence == null) {
                                ratingSequence = sequenceControl.getDefaultSequenceUsingNames(SequenceTypes.RATING.name());
                            }
                            
                            ratingName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(ratingSequence);
                            
                            ratingControl.createRating(ratingName, ratingTypeListItem, ratedEntityInstance, ratedByEntityInstance,
                                    createdBy);
                        } else {
                            addExecutionError(ExecutionErrors.UnknownRatingTypeListItemName.name(), ratingTypeListItemName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownRatingTypeName.name(), ratingTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.DuplicateRating.name());
                }
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownEntityRef.name(), entityRef);
        }
        
        result.setRatingName(ratingName);
        
        return result;
    }
    
}
