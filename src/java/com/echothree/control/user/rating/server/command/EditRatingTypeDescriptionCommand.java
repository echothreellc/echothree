// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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
import com.echothree.control.user.rating.common.result.EditRatingTypeDescriptionResult;
import com.echothree.control.user.rating.common.result.RatingResultFactory;
import com.echothree.control.user.rating.common.spec.RatingTypeDescriptionSpec;
import com.echothree.model.control.rating.server.control.RatingControl;
import com.echothree.model.control.rating.server.logic.RatingTypeLogic;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.data.rating.server.entity.RatingType;
import com.echothree.model.data.rating.server.entity.RatingTypeDescription;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditRatingTypeDescriptionCommand
        extends BaseAbstractEditCommand<RatingTypeDescriptionSpec, RatingTypeDescriptionEdit, EditRatingTypeDescriptionResult, RatingTypeDescription, RatingType> {

    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
                new FieldDefinition("RatingTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    RatingControl ratingControl;

    @Inject
    LanguageLogic languageLogic;

    @Inject
    RatingTypeLogic ratingTypeLogic;

    /** Creates a new instance of EditRatingTypeDescriptionCommand */
    public EditRatingTypeDescriptionCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditRatingTypeDescriptionResult getResult() {
        return RatingResultFactory.getEditRatingTypeDescriptionResult();
    }

    @Override
    public RatingTypeDescriptionEdit getEdit() {
        return RatingEditFactory.getRatingTypeDescriptionEdit();
    }

    @Override
    public RatingTypeDescription getEntity(EditRatingTypeDescriptionResult result) {
        RatingTypeDescription ratingTypeDescription = null;
        var componentVendorName = spec.getComponentVendorName();
        var entityTypeName = spec.getEntityTypeName();
        var ratingTypeName = spec.getRatingTypeName();
        var languageIsoName = spec.getLanguageIsoName();
        var ratingType = ratingTypeLogic.getRatingTypeByName(this, componentVendorName, entityTypeName, ratingTypeName);
        var language = languageLogic.getLanguageByName(this, languageIsoName);

        if(!hasExecutionErrors()) {
            ratingTypeDescription = ratingControl.getRatingTypeDescription(ratingType, language,
                    editModeToEntityPermission(editMode));

            if(ratingTypeDescription == null) {
                addExecutionError(ExecutionErrors.UnknownRatingTypeDescription.name(), componentVendorName, entityTypeName, ratingTypeName, languageIsoName);
            }
        }

        return ratingTypeDescription;
    }

    @Override
    public RatingType getLockEntity(RatingTypeDescription ratingTypeDescription) {
        return ratingTypeDescription.getRatingType();
    }

    @Override
    public void fillInResult(EditRatingTypeDescriptionResult result, RatingTypeDescription ratingTypeDescription) {
        result.setRatingTypeDescription(ratingControl.getRatingTypeDescriptionTransfer(getUserVisit(), ratingTypeDescription));
    }

    @Override
    public void doLock(RatingTypeDescriptionEdit edit, RatingTypeDescription ratingTypeDescription) {
        edit.setDescription(ratingTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(RatingTypeDescription ratingTypeDescription) {
        var ratingTypeDescriptionValue = ratingControl.getRatingTypeDescriptionValue(ratingTypeDescription);

        ratingTypeDescriptionValue.setDescription(edit.getDescription());

        ratingControl.updateRatingTypeDescriptionFromValue(ratingTypeDescriptionValue, getPartyPK());
    }

}
