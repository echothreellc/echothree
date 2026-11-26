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

package com.echothree.control.user.contact.server.command;

import com.echothree.control.user.contact.common.edit.ContactEditFactory;
import com.echothree.control.user.contact.common.edit.PostalAddressFormatEdit;
import com.echothree.control.user.contact.common.result.ContactResultFactory;
import com.echothree.control.user.contact.common.result.EditPostalAddressFormatResult;
import com.echothree.control.user.contact.common.spec.PostalAddressFormatUniversalSpec;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.contact.server.logic.PostalAddressFormatLogic;
import com.echothree.model.data.contact.server.entity.PostalAddressFormat;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseEditCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditPostalAddressFormatCommand
        extends BaseEditCommand<PostalAddressFormatUniversalSpec, PostalAddressFormatEdit, EditPostalAddressFormatResult, PostalAddressFormat, PostalAddressFormat> {

    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PostalAddressFormatName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PostalAddressFormatName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }

    @Inject
    ContactControl contactControl;

    @Inject
    PostalAddressFormatLogic postalAddressFormatLogic;

    /** Creates a new instance of EditPostalAddressFormatCommand */
    public EditPostalAddressFormatCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditPostalAddressFormatResult getResult() {
        return ContactResultFactory.getEditPostalAddressFormatResult();
    }

    @Override
    public PostalAddressFormatEdit getEdit() {
        return ContactEditFactory.getPostalAddressFormatEdit();
    }

    @Override
    public PostalAddressFormat getEntity(EditPostalAddressFormatResult result) {
        return postalAddressFormatLogic.getPostalAddressFormatByUniversalSpec(this, spec, editModeToEntityPermission(editMode));
    }

    @Override
    public PostalAddressFormat getLockEntity(PostalAddressFormat postalAddressFormat) {
        return postalAddressFormat;
    }

    @Override
    public void fillInResult(EditPostalAddressFormatResult result, PostalAddressFormat postalAddressFormat) {
        result.setPostalAddressFormat(contactControl.getPostalAddressFormatTransfer(getUserVisit(), postalAddressFormat));
    }

    @Override
    public void doLock(PostalAddressFormatEdit edit, PostalAddressFormat postalAddressFormat) {
        var postalAddressFormatDescription = contactControl.getPostalAddressFormatDescription(postalAddressFormat, getPreferredLanguage());
        var postalAddressFormatDetail = postalAddressFormat.getLastDetail();

        edit.setPostalAddressFormatName(postalAddressFormatDetail.getPostalAddressFormatName());
        edit.setIsDefault(postalAddressFormatDetail.getIsDefault().toString());
        edit.setSortOrder(postalAddressFormatDetail.getSortOrder().toString());

        if(postalAddressFormatDescription != null) {
            edit.setDescription(postalAddressFormatDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(PostalAddressFormat postalAddressFormat) {
        var postalAddressFormatName = edit.getPostalAddressFormatName();
        var duplicatePostalAddressFormat = contactControl.getPostalAddressFormatByName(postalAddressFormatName);

        if(duplicatePostalAddressFormat != null && !postalAddressFormat.equals(duplicatePostalAddressFormat)) {
            addExecutionError(ExecutionErrors.DuplicatePostalAddressFormatName.name(), postalAddressFormatName);
        }
    }

    @Override
    public void doUpdate(PostalAddressFormat postalAddressFormat) {
        var partyPK = getPartyPK();
        var postalAddressFormatDetailValue = contactControl.getPostalAddressFormatDetailValueForUpdate(postalAddressFormat);
        var postalAddressFormatDescription = contactControl.getPostalAddressFormatDescriptionForUpdate(postalAddressFormat, getPreferredLanguage());
        var description = edit.getDescription();

        postalAddressFormatDetailValue.setPostalAddressFormatName(edit.getPostalAddressFormatName());
        postalAddressFormatDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        postalAddressFormatDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        contactControl.updatePostalAddressFormatFromValue(postalAddressFormatDetailValue, partyPK);

        if(postalAddressFormatDescription == null && description != null) {
            contactControl.createPostalAddressFormatDescription(postalAddressFormat, getPreferredLanguage(), description, partyPK);
        } else if(postalAddressFormatDescription != null && description == null) {
            contactControl.deletePostalAddressFormatDescription(postalAddressFormatDescription, partyPK);
        } else if(postalAddressFormatDescription != null && description != null) {
            var postalAddressFormatDescriptionValue = contactControl.getPostalAddressFormatDescriptionValue(postalAddressFormatDescription);

            postalAddressFormatDescriptionValue.setDescription(description);
            contactControl.updatePostalAddressFormatDescriptionFromValue(postalAddressFormatDescriptionValue, partyPK);
        }
    }

}
