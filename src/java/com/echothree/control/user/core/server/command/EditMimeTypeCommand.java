// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.core.server.entity.MimeTypeDescription;
import com.echothree.model.data.core.server.entity.MimeTypeDetail;
import com.echothree.model.data.core.server.value.MimeTypeDescriptionValue;
import com.echothree.model.data.core.server.value.MimeTypeDetailValue;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditMimeTypeCommand
        extends BaseAbstractEditCommand<MimeTypeSpec, MimeTypeEdit, EditMimeTypeResult, MimeType, MimeType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
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
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }

    /** Creates a new instance of EditMimeTypeCommand */
    public EditMimeTypeCommand(UserVisitPK userVisitPK, EditMimeTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
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
        CoreControl coreControl = getCoreControl();
        MimeType mimeType = null;
        String mimeTypeName = spec.getMimeTypeName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            mimeType = coreControl.getMimeTypeByName(mimeTypeName);
        } else { // EditMode.UPDATE
            mimeType = coreControl.getMimeTypeByNameForUpdate(mimeTypeName);
        }

        if(mimeType != null) {
            result.setMimeType(coreControl.getMimeTypeTransfer(getUserVisit(), mimeType));
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
        CoreControl coreControl = getCoreControl();

        result.setMimeType(coreControl.getMimeTypeTransfer(getUserVisit(), mimeType));
    }

    @Override
    public void doLock(MimeTypeEdit edit, MimeType mimeType) {
        CoreControl coreControl = getCoreControl();
        MimeTypeDescription mimeTypeDescription = coreControl.getMimeTypeDescription(mimeType, getPreferredLanguage());
        MimeTypeDetail mimeTypeDetail = mimeType.getLastDetail();

        edit.setMimeTypeName(mimeTypeDetail.getMimeTypeName());
        edit.setIsDefault(mimeTypeDetail.getIsDefault().toString());
        edit.setSortOrder(mimeTypeDetail.getSortOrder().toString());

        if(mimeTypeDescription != null) {
            edit.setDescription(mimeTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(MimeType mimeType) {
        CoreControl coreControl = getCoreControl();
        String mimeTypeName = edit.getMimeTypeName();
        MimeType duplicateMimeType = coreControl.getMimeTypeByName(mimeTypeName);

        if(duplicateMimeType != null && !mimeType.equals(duplicateMimeType)) {
            addExecutionError(ExecutionErrors.DuplicateMimeTypeName.name(), mimeTypeName);
        }
    }

    @Override
    public void doUpdate(MimeType mimeType) {
        CoreControl coreControl = getCoreControl();
        PartyPK partyPK = getPartyPK();
        MimeTypeDetailValue mimeTypeDetailValue = coreControl.getMimeTypeDetailValueForUpdate(mimeType);
        MimeTypeDescription mimeTypeDescription = coreControl.getMimeTypeDescriptionForUpdate(mimeType, getPreferredLanguage());
        String description = edit.getDescription();

        mimeTypeDetailValue.setMimeTypeName(edit.getMimeTypeName());
        mimeTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        mimeTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        coreControl.updateMimeTypeFromValue(mimeTypeDetailValue, partyPK);

        if(mimeTypeDescription == null && description != null) {
            coreControl.createMimeTypeDescription(mimeType, getPreferredLanguage(), description, partyPK);
        } else if(mimeTypeDescription != null && description == null) {
            coreControl.deleteMimeTypeDescription(mimeTypeDescription, partyPK);
        } else if(mimeTypeDescription != null && description != null) {
            MimeTypeDescriptionValue mimeTypeDescriptionValue = coreControl.getMimeTypeDescriptionValue(mimeTypeDescription);

            mimeTypeDescriptionValue.setDescription(description);
            coreControl.updateMimeTypeDescriptionFromValue(mimeTypeDescriptionValue, partyPK);
        }
    }

}
