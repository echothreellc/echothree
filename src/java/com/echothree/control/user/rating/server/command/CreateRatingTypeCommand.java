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

package com.echothree.control.user.rating.server.command;

import com.echothree.control.user.rating.common.form.CreateRatingTypeForm;
import com.echothree.control.user.rating.common.result.RatingResultFactory;
import com.echothree.model.control.core.server.logic.EntityTypeLogic;
import com.echothree.model.control.rating.server.control.RatingControl;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.data.rating.server.entity.RatingType;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class CreateRatingTypeCommand
        extends BaseSimpleCommand<CreateRatingTypeForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
                new FieldDefinition("RatingTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("RatingSequenceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of CreateRatingTypeCommand */
    public CreateRatingTypeCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = RatingResultFactory.getCreateRatingTypeResult();
        var componentVendorName = form.getComponentVendorName();
        var entityTypeName = form.getEntityTypeName();
        var entityType = EntityTypeLogic.getInstance().getEntityTypeByName(this, componentVendorName, entityTypeName);
        RatingType ratingType = null;

        if(!hasExecutionErrors()) {
            var entityTypeDetail = entityType.getLastDetail();

            if(entityTypeDetail.getIsExtensible()) {
                var ratingControl = Session.getModelController(RatingControl.class);
                var ratingTypeName = form.getRatingTypeName();
                
                ratingType = ratingControl.getRatingTypeByName(entityType, ratingTypeName);
                
                if(ratingType == null) {
                    var ratingSequenceName = form.getRatingSequenceName();
                    Sequence ratingSequence = null;
                    
                    if(ratingSequenceName != null) {
                        var sequenceControl = Session.getModelController(SequenceControl.class);
                        var sequenceType = sequenceControl.getSequenceTypeByName(SequenceTypes.RATING.name());
                        
                        if(sequenceType != null) {
                            ratingSequence = sequenceControl.getSequenceByName(sequenceType, ratingSequenceName);
                        } else {
                             addExecutionError(ExecutionErrors.UnknownSequenceTypeName.name(), SequenceTypes.RATING.name());
                        }
                    }
                    
                    if(ratingSequenceName == null || ratingSequence != null) {
                        var partyPK = getPartyPK();
                        var sortOrder = Integer.valueOf(form.getSortOrder());
                        var description = form.getDescription();
                        
                        ratingType = ratingControl.createRatingType(entityType, ratingTypeName, ratingSequence, sortOrder, partyPK);
                        
                        if(description != null) {
                            ratingControl.createRatingTypeDescription(ratingType, getPreferredLanguage(), description, partyPK);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownRatingSequenceName.name(), ratingSequenceName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.DuplicateRatingTypeName.name(), ratingTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.EntityTypeIsNotExtensible.name(),
                        entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                        entityTypeDetail.getEntityTypeName());
            }
        }

        if(ratingType != null) {
            var basePK = ratingType.getPrimaryKey();
            var ratingTypeDetail = ratingType.getLastDetail();
            var entityTypeDetail = ratingTypeDetail.getEntityType().getLastDetail();

            result.setComponentVendorName(entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName());
            result.setEntityTypeName(entityTypeDetail.getEntityTypeName());
            result.setRatingTypeName(ratingTypeDetail.getRatingTypeName());
            result.setEntityRef(basePK.getEntityRef());
        }

        return result;
    }
    
}
