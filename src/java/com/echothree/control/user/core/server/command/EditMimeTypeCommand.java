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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.common.edit.CoreEditFactory;
import com.echothree.control.user.core.common.edit.MimeTypeEdit;
import com.echothree.control.user.core.common.form.EditMimeTypeForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditMimeTypeResult;
import com.echothree.control.user.core.common.spec.MimeTypeSpec;
import com.echothree.model.control.core.server.control.MimeTypeControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class EditMimeTypeCommand
        extends BaseAbstractEditCommand<MimeTypeSpec, MimeTypeEdit, EditMimeTypeResult, MimeType, MimeType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.MimeType.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("MimeTypeName", FieldType.MIME_TYPE, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("MimeTypeName", FieldType.MIME_TYPE, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditMimeTypeCommand */
    public EditMimeTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditMimeTypeResult getResult() {
        return CoreResultFactory.getEditMimeTypeResult();
    }

    @Override
    public MimeTypeEdit getEdit() {
        return CoreEditFactory.getMimeTypeEdit();
    }

    @Override
    public MimeType getEntity(EditMimeTypeResult result) {
        var mimeTypeControl = Session.getModelController(MimeTypeControl.class);
        MimeType mimeType;
        var mimeTypeName = spec.getMimeTypeName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            mimeType = mimeTypeControl.getMimeTypeByName(mimeTypeName);
        } else { // EditMode.UPDATE
            mimeType = mimeTypeControl.getMimeTypeByNameForUpdate(mimeTypeName);
        }

        if(mimeType != null) {
            result.setMimeType(mimeTypeControl.getMimeTypeTransfer(getUserVisit(), mimeType));
        } else {
            addExecutionError(ExecutionErrors.UnknownMimeTypeName.name(), mimeTypeName);
        }

        return mimeType;
    }

    @Override
    public MimeType getLockEntity(MimeType mimeType) {
        return mimeType;
    }

    @Override
    public void fillInResult(EditMimeTypeResult result, MimeType mimeType) {
        var mimeTypeControl = Session.getModelController(MimeTypeControl.class);

        result.setMimeType(mimeTypeControl.getMimeTypeTransfer(getUserVisit(), mimeType));
    }

    @Override
    public void doLock(MimeTypeEdit edit, MimeType mimeType) {
        var mimeTypeControl = Session.getModelController(MimeTypeControl.class);
        var mimeTypeDescription = mimeTypeControl.getMimeTypeDescription(mimeType, getPreferredLanguage());
        var mimeTypeDetail = mimeType.getLastDetail();

        edit.setMimeTypeName(mimeTypeDetail.getMimeTypeName());
        edit.setIsDefault(mimeTypeDetail.getIsDefault().toString());
        edit.setSortOrder(mimeTypeDetail.getSortOrder().toString());

        if(mimeTypeDescription != null) {
            edit.setDescription(mimeTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(MimeType mimeType) {
        var mimeTypeControl = Session.getModelController(MimeTypeControl.class);
        var mimeTypeName = edit.getMimeTypeName();
        var duplicateMimeType = mimeTypeControl.getMimeTypeByName(mimeTypeName);

        if(duplicateMimeType != null && !mimeType.equals(duplicateMimeType)) {
            addExecutionError(ExecutionErrors.DuplicateMimeTypeName.name(), mimeTypeName);
        }
    }

    @Override
    public void doUpdate(MimeType mimeType) {
        var mimeTypeControl = Session.getModelController(MimeTypeControl.class);
        var partyPK = getPartyPK();
        var mimeTypeDetailValue = mimeTypeControl.getMimeTypeDetailValueForUpdate(mimeType);
        var mimeTypeDescription = mimeTypeControl.getMimeTypeDescriptionForUpdate(mimeType, getPreferredLanguage());
        var description = edit.getDescription();

        mimeTypeDetailValue.setMimeTypeName(edit.getMimeTypeName());
        mimeTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        mimeTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        mimeTypeControl.updateMimeTypeFromValue(mimeTypeDetailValue, partyPK);

        if(mimeTypeDescription == null && description != null) {
            mimeTypeControl.createMimeTypeDescription(mimeType, getPreferredLanguage(), description, partyPK);
        } else if(mimeTypeDescription != null && description == null) {
            mimeTypeControl.deleteMimeTypeDescription(mimeTypeDescription, partyPK);
        } else if(mimeTypeDescription != null && description != null) {
            var mimeTypeDescriptionValue = mimeTypeControl.getMimeTypeDescriptionValue(mimeTypeDescription);

            mimeTypeDescriptionValue.setDescription(description);
            mimeTypeControl.updateMimeTypeDescriptionFromValue(mimeTypeDescriptionValue, partyPK);
        }
    }

}
