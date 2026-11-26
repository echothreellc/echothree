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
import com.echothree.control.user.contact.common.edit.PostalAddressLineElementEdit;
import com.echothree.control.user.contact.common.result.ContactResultFactory;
import com.echothree.control.user.contact.common.result.EditPostalAddressLineElementResult;
import com.echothree.control.user.contact.common.spec.PostalAddressLineElementSpec;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.contact.server.logic.PostalAddressElementTypeLogic;
import com.echothree.model.control.contact.server.logic.PostalAddressLineElementLogic;
import com.echothree.model.data.contact.server.entity.PostalAddressElementType;
import com.echothree.model.data.contact.server.entity.PostalAddressLineElement;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditPostalAddressLineElementCommand
        extends BaseAbstractEditCommand<PostalAddressLineElementSpec, PostalAddressLineElementEdit, EditPostalAddressLineElementResult, PostalAddressLineElement, PostalAddressLineElement> {

    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PostalAddressFormatName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PostalAddressLineSortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("PostalAddressLineElementSortOrder", FieldType.SIGNED_INTEGER, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PostalAddressLineElementSortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("PostalAddressElementTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Prefix", FieldType.STRING, false, 1L, 10L),
                new FieldDefinition("AlwaysIncludePrefix", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("Suffix", FieldType.STRING, false, 1L, 10L),
                new FieldDefinition("AlwaysIncludeSuffix", FieldType.BOOLEAN, true, null, null)
        );
    }

    private PostalAddressElementType postalAddressElementType;

    @Inject
    ContactControl contactControl;

    @Inject
    PostalAddressElementTypeLogic postalAddressElementTypeLogic;

    @Inject
    PostalAddressLineElementLogic postalAddressLineElementLogic;

    /** Creates a new instance of EditPostalAddressLineElementCommand */
    public EditPostalAddressLineElementCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditPostalAddressLineElementResult getResult() {
        return ContactResultFactory.getEditPostalAddressLineElementResult();
    }

    @Override
    public PostalAddressLineElementEdit getEdit() {
        return ContactEditFactory.getPostalAddressLineElementEdit();
    }

    @Override
    public PostalAddressLineElement getEntity(EditPostalAddressLineElementResult result) {
        return postalAddressLineElementLogic.getPostalAddressLineElement(this, spec.getPostalAddressFormatName(),
                Integer.valueOf(spec.getPostalAddressLineSortOrder()), Integer.valueOf(spec.getPostalAddressLineElementSortOrder()),
                editModeToEntityPermission(editMode));
    }

    @Override
    public PostalAddressLineElement getLockEntity(PostalAddressLineElement postalAddressLineElement) {
        return postalAddressLineElement;
    }

    @Override
    public void fillInResult(EditPostalAddressLineElementResult result, PostalAddressLineElement postalAddressLineElement) {
        result.setPostalAddressLineElement(contactControl.getPostalAddressLineElementTransfer(getUserVisit(), postalAddressLineElement));
    }

    @Override
    public void doLock(PostalAddressLineElementEdit edit, PostalAddressLineElement postalAddressLineElement) {
        edit.setPostalAddressLineElementSortOrder(postalAddressLineElement.getPostalAddressLineElementSortOrder().toString());
        edit.setPostalAddressElementTypeName(postalAddressLineElement.getPostalAddressElementType().getPostalAddressElementTypeName());
        edit.setPrefix(postalAddressLineElement.getPrefix());
        edit.setAlwaysIncludePrefix(postalAddressLineElement.getAlwaysIncludePrefix().toString());
        edit.setSuffix(postalAddressLineElement.getSuffix());
        edit.setAlwaysIncludeSuffix(postalAddressLineElement.getAlwaysIncludeSuffix().toString());
    }

    @Override
    public void canUpdate(PostalAddressLineElement postalAddressLineElement) {
        var postalAddressLine = postalAddressLineElement.getPostalAddressLine();
        var postalAddressLineElementSortOrder = Integer.valueOf(edit.getPostalAddressLineElementSortOrder());
        var duplicatePostalAddressLineElement = contactControl.getPostalAddressLineElement(postalAddressLine, postalAddressLineElementSortOrder);

        postalAddressElementType = postalAddressElementTypeLogic.getPostalAddressElementTypeByName(this, edit.getPostalAddressElementTypeName());

        if(duplicatePostalAddressLineElement != null && !postalAddressLineElement.equals(duplicatePostalAddressLineElement)) {
            var postalAddressLineDetail = postalAddressLine.getLastDetail();

            addExecutionError(ExecutionErrors.DuplicatePostalAddressLineElement.name(),
                    postalAddressLineDetail.getPostalAddressFormat().getLastDetail().getPostalAddressFormatName(),
                    postalAddressLineDetail.getPostalAddressLineSortOrder().toString(), postalAddressLineElementSortOrder.toString());
        }
    }

    @Override
    public void doUpdate(PostalAddressLineElement postalAddressLineElement) {
        var postalAddressLineElementValue = contactControl.getPostalAddressLineElementValueForUpdate(postalAddressLineElement);

        postalAddressLineElementValue.setPostalAddressLineElementSortOrder(Integer.valueOf(edit.getPostalAddressLineElementSortOrder()));
        postalAddressLineElementValue.setPostalAddressElementTypePK(postalAddressElementType.getPrimaryKey());
        postalAddressLineElementValue.setPrefix(edit.getPrefix());
        postalAddressLineElementValue.setAlwaysIncludePrefix(Boolean.valueOf(edit.getAlwaysIncludePrefix()));
        postalAddressLineElementValue.setSuffix(edit.getSuffix());
        postalAddressLineElementValue.setAlwaysIncludeSuffix(Boolean.valueOf(edit.getAlwaysIncludeSuffix()));

        contactControl.updatePostalAddressLineElementFromValue(postalAddressLineElementValue, getPartyPK());
    }

}
