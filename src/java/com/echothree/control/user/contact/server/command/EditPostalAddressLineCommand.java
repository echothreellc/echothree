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
import com.echothree.control.user.contact.common.edit.PostalAddressLineEdit;
import com.echothree.control.user.contact.common.result.ContactResultFactory;
import com.echothree.control.user.contact.common.result.EditPostalAddressLineResult;
import com.echothree.control.user.contact.common.spec.PostalAddressLineUniversalSpec;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.contact.server.logic.PostalAddressLineLogic;
import com.echothree.model.data.contact.server.entity.PostalAddressLine;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditPostalAddressLineCommand
        extends BaseAbstractEditCommand<PostalAddressLineUniversalSpec, PostalAddressLineEdit, EditPostalAddressLineResult, PostalAddressLine, PostalAddressLine> {

    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PostalAddressFormatName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PostalAddressLineSortOrder", FieldType.SIGNED_INTEGER, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PostalAddressLineSortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Prefix", FieldType.STRING, false, 1L, 10L),
                new FieldDefinition("AlwaysIncludePrefix", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("Suffix", FieldType.STRING, false, 1L, 10L),
                new FieldDefinition("AlwaysIncludeSuffix", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("CollapseIfEmpty", FieldType.BOOLEAN, true, null, null)
        );
    }

    @Inject
    ContactControl contactControl;

    @Inject
    PostalAddressLineLogic postalAddressLineLogic;

    /** Creates a new instance of EditPostalAddressLineCommand */
    public EditPostalAddressLineCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditPostalAddressLineResult getResult() {
        return ContactResultFactory.getEditPostalAddressLineResult();
    }

    @Override
    public PostalAddressLineEdit getEdit() {
        return ContactEditFactory.getPostalAddressLineEdit();
    }

    @Override
    public PostalAddressLine getEntity(EditPostalAddressLineResult result) {
        return postalAddressLineLogic.getPostalAddressLineByUniversalSpec(this, spec, editModeToEntityPermission(editMode));
    }

    @Override
    public PostalAddressLine getLockEntity(PostalAddressLine postalAddressLine) {
        return postalAddressLine;
    }

    @Override
    public void fillInResult(EditPostalAddressLineResult result, PostalAddressLine postalAddressLine) {
        result.setPostalAddressLine(contactControl.getPostalAddressLineTransfer(getUserVisit(), postalAddressLine));
    }

    @Override
    public void doLock(PostalAddressLineEdit edit, PostalAddressLine postalAddressLine) {
        var postalAddressLineDetail = postalAddressLine.getLastDetail();

        edit.setPostalAddressLineSortOrder(postalAddressLineDetail.getPostalAddressLineSortOrder().toString());
        edit.setPrefix(postalAddressLineDetail.getPrefix());
        edit.setAlwaysIncludePrefix(postalAddressLineDetail.getAlwaysIncludePrefix().toString());
        edit.setSuffix(postalAddressLineDetail.getSuffix());
        edit.setAlwaysIncludeSuffix(postalAddressLineDetail.getAlwaysIncludeSuffix().toString());
        edit.setCollapseIfEmpty(postalAddressLineDetail.getCollapseIfEmpty().toString());
    }

    @Override
    public void canUpdate(PostalAddressLine postalAddressLine) {
        var postalAddressFormat = postalAddressLine.getLastDetail().getPostalAddressFormat();
        var postalAddressLineSortOrder = Integer.valueOf(edit.getPostalAddressLineSortOrder());
        var duplicatePostalAddressLine = contactControl.getPostalAddressLine(postalAddressFormat, postalAddressLineSortOrder);

        if(duplicatePostalAddressLine != null && !postalAddressLine.equals(duplicatePostalAddressLine)) {
            addExecutionError(ExecutionErrors.DuplicatePostalAddressLine.name(),
                    postalAddressFormat.getLastDetail().getPostalAddressFormatName(), postalAddressLineSortOrder.toString());
        }
    }

    @Override
    public void doUpdate(PostalAddressLine postalAddressLine) {
        var postalAddressLineDetailValue = contactControl.getPostalAddressLineDetailValueForUpdate(postalAddressLine);

        postalAddressLineDetailValue.setPostalAddressLineSortOrder(Integer.valueOf(edit.getPostalAddressLineSortOrder()));
        postalAddressLineDetailValue.setPrefix(edit.getPrefix());
        postalAddressLineDetailValue.setAlwaysIncludePrefix(Boolean.valueOf(edit.getAlwaysIncludePrefix()));
        postalAddressLineDetailValue.setSuffix(edit.getSuffix());
        postalAddressLineDetailValue.setAlwaysIncludeSuffix(Boolean.valueOf(edit.getAlwaysIncludeSuffix()));
        postalAddressLineDetailValue.setCollapseIfEmpty(Boolean.valueOf(edit.getCollapseIfEmpty()));

        contactControl.updatePostalAddressLineFromValue(postalAddressLineDetailValue, getPartyPK());
    }

}
