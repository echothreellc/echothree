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
import com.echothree.control.user.rating.common.edit.RatingTypeListItemEdit;
import com.echothree.control.user.rating.common.result.EditRatingTypeListItemResult;
import com.echothree.control.user.rating.common.result.RatingResultFactory;
import com.echothree.control.user.rating.common.spec.RatingTypeListItemUniversalSpec;
import com.echothree.model.control.rating.server.control.RatingControl;
import com.echothree.model.control.rating.server.logic.RatingTypeListItemLogic;
import com.echothree.model.data.rating.server.entity.RatingTypeListItem;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseEditCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditRatingTypeListItemCommand
        extends BaseEditCommand<RatingTypeListItemUniversalSpec, RatingTypeListItemEdit, EditRatingTypeListItemResult, RatingTypeListItem, RatingTypeListItem> {

    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, false, null, null),
                new FieldDefinition("RatingTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("RatingTypeListItemName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("RatingTypeListItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }

    @Inject
    RatingControl ratingControl;

    @Inject
    RatingTypeListItemLogic ratingTypeListItemLogic;

    /** Creates a new instance of EditRatingTypeListItemCommand */
    public EditRatingTypeListItemCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditRatingTypeListItemResult getResult() {
        return RatingResultFactory.getEditRatingTypeListItemResult();
    }

    @Override
    public RatingTypeListItemEdit getEdit() {
        return RatingEditFactory.getRatingTypeListItemEdit();
    }

    @Override
    public RatingTypeListItem getEntity(EditRatingTypeListItemResult result) {
        return ratingTypeListItemLogic.getRatingTypeListItemByUniversalSpec(this, spec, false, editModeToEntityPermission(editMode));
    }

    @Override
    public RatingTypeListItem getLockEntity(RatingTypeListItem ratingTypeListItem) {
        return ratingTypeListItem;
    }

    @Override
    public void fillInResult(EditRatingTypeListItemResult result, RatingTypeListItem ratingTypeListItem) {
        result.setRatingTypeListItem(ratingControl.getRatingTypeListItemTransfer(getUserVisit(), ratingTypeListItem));
    }

    @Override
    public void doLock(RatingTypeListItemEdit edit, RatingTypeListItem ratingTypeListItem) {
        var ratingTypeListItemDescription = ratingControl.getRatingTypeListItemDescription(ratingTypeListItem, getPreferredLanguage());
        var ratingTypeListItemDetail = ratingTypeListItem.getLastDetail();

        edit.setRatingTypeListItemName(ratingTypeListItemDetail.getRatingTypeListItemName());
        edit.setIsDefault(ratingTypeListItemDetail.getIsDefault().toString());
        edit.setSortOrder(ratingTypeListItemDetail.getSortOrder().toString());

        if(ratingTypeListItemDescription != null) {
            edit.setDescription(ratingTypeListItemDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(RatingTypeListItem ratingTypeListItem) {
        var ratingType = ratingTypeListItem.getLastDetail().getRatingType();
        var ratingTypeListItemName = edit.getRatingTypeListItemName();
        var duplicateRatingTypeListItem = ratingControl.getRatingTypeListItemByName(ratingType, ratingTypeListItemName);

        if(duplicateRatingTypeListItem != null && !ratingTypeListItem.equals(duplicateRatingTypeListItem)) {
            var ratingTypeDetail = ratingType.getLastDetail();
            var entityTypeDetail = ratingTypeDetail.getEntityType().getLastDetail();

            addExecutionError(ExecutionErrors.DuplicateRatingTypeListItemName.name(),
                    entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(), entityTypeDetail.getEntityTypeName(),
                    ratingTypeDetail.getRatingTypeName(), ratingTypeListItemName);
        }
    }

    @Override
    public void doUpdate(RatingTypeListItem ratingTypeListItem) {
        var partyPK = getPartyPK();
        var ratingTypeListItemDetailValue = ratingControl.getRatingTypeListItemDetailValueForUpdate(ratingTypeListItem);
        var ratingTypeListItemDescription = ratingControl.getRatingTypeListItemDescriptionForUpdate(ratingTypeListItem, getPreferredLanguage());
        var description = edit.getDescription();

        ratingTypeListItemDetailValue.setRatingTypeListItemName(edit.getRatingTypeListItemName());
        ratingTypeListItemDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        ratingTypeListItemDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        ratingControl.updateRatingTypeListItemFromValue(ratingTypeListItemDetailValue, partyPK);

        if(ratingTypeListItemDescription == null && description != null) {
            ratingControl.createRatingTypeListItemDescription(ratingTypeListItem, getPreferredLanguage(), description, partyPK);
        } else if(ratingTypeListItemDescription != null && description == null) {
            ratingControl.deleteRatingTypeListItemDescription(ratingTypeListItemDescription, partyPK);
        } else if(ratingTypeListItemDescription != null && description != null) {
            var ratingTypeListItemDescriptionValue = ratingControl.getRatingTypeListItemDescriptionValue(ratingTypeListItemDescription);

            ratingTypeListItemDescriptionValue.setDescription(description);
            
            ratingControl.updateRatingTypeListItemDescriptionFromValue(ratingTypeListItemDescriptionValue, partyPK);
        }
    }

}
