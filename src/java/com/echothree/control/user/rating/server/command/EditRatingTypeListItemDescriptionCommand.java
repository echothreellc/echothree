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
import com.echothree.control.user.rating.common.edit.RatingTypeListItemDescriptionEdit;
import com.echothree.control.user.rating.common.result.EditRatingTypeListItemDescriptionResult;
import com.echothree.control.user.rating.common.result.RatingResultFactory;
import com.echothree.control.user.rating.common.spec.RatingTypeListItemDescriptionSpec;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.control.rating.server.control.RatingControl;
import com.echothree.model.control.rating.server.logic.RatingTypeListItemLogic;
import com.echothree.model.data.rating.server.entity.RatingTypeListItem;
import com.echothree.model.data.rating.server.entity.RatingTypeListItemDescription;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseEditCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditRatingTypeListItemDescriptionCommand
        extends BaseEditCommand<RatingTypeListItemDescriptionSpec, RatingTypeListItemDescriptionEdit, EditRatingTypeListItemDescriptionResult, RatingTypeListItemDescription, RatingTypeListItem> {

    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
                new FieldDefinition("RatingTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("RatingTypeListItemName", FieldType.ENTITY_NAME, true, null, null),
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
    RatingTypeListItemLogic ratingTypeListItemLogic;

    /** Creates a new instance of EditRatingTypeListItemDescriptionCommand */
    public EditRatingTypeListItemDescriptionCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditRatingTypeListItemDescriptionResult getResult() {
        return RatingResultFactory.getEditRatingTypeListItemDescriptionResult();
    }

    @Override
    public RatingTypeListItemDescriptionEdit getEdit() {
        return RatingEditFactory.getRatingTypeListItemDescriptionEdit();
    }

    @Override
    public RatingTypeListItemDescription getEntity(EditRatingTypeListItemDescriptionResult result) {
        RatingTypeListItemDescription ratingTypeListItemDescription = null;
        var componentVendorName = spec.getComponentVendorName();
        var entityTypeName = spec.getEntityTypeName();
        var ratingTypeName = spec.getRatingTypeName();
        var ratingTypeListItemName = spec.getRatingTypeListItemName();
        var languageIsoName = spec.getLanguageIsoName();
        var ratingTypeListItem = ratingTypeListItemLogic.getRatingTypeListItemByName(this, componentVendorName, entityTypeName, ratingTypeName, ratingTypeListItemName);
        var language = languageLogic.getLanguageByName(this, languageIsoName);

        if(!hasExecutionErrors()) {
            ratingTypeListItemDescription = ratingControl.getRatingTypeListItemDescription(ratingTypeListItem, language,
                    editModeToEntityPermission(editMode));

            if(ratingTypeListItemDescription == null) {
                addExecutionError(ExecutionErrors.UnknownRatingTypeListItemDescription.name(), componentVendorName, entityTypeName, ratingTypeName, ratingTypeListItemName, languageIsoName);
            }
        }

        return ratingTypeListItemDescription;
    }

    @Override
    public RatingTypeListItem getLockEntity(RatingTypeListItemDescription ratingTypeListItemDescription) {
        return ratingTypeListItemDescription.getRatingTypeListItem();
    }

    @Override
    public void fillInResult(EditRatingTypeListItemDescriptionResult result, RatingTypeListItemDescription ratingTypeListItemDescription) {
        result.setRatingTypeListItemDescription(ratingControl.getRatingTypeListItemDescriptionTransfer(getUserVisit(), ratingTypeListItemDescription));
    }

    @Override
    public void doLock(RatingTypeListItemDescriptionEdit edit, RatingTypeListItemDescription ratingTypeListItemDescription) {
        edit.setDescription(ratingTypeListItemDescription.getDescription());
    }

    @Override
    public void doUpdate(RatingTypeListItemDescription ratingTypeListItemDescription) {
        var ratingTypeListItemDescriptionValue = ratingControl.getRatingTypeListItemDescriptionValue(ratingTypeListItemDescription);

        ratingTypeListItemDescriptionValue.setDescription(edit.getDescription());

        ratingControl.updateRatingTypeListItemDescriptionFromValue(ratingTypeListItemDescriptionValue, getPartyPK());
    }

}
