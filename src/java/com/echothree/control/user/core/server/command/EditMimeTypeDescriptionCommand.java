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
import com.echothree.control.user.core.common.edit.MimeTypeDescriptionEdit;
import com.echothree.control.user.core.common.form.EditMimeTypeDescriptionForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditMimeTypeDescriptionResult;
import com.echothree.control.user.core.common.spec.MimeTypeDescriptionSpec;
import com.echothree.model.control.core.server.control.MimeTypeControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.core.server.entity.MimeTypeDescription;
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
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditMimeTypeDescriptionCommand
        extends BaseAbstractEditCommand<MimeTypeDescriptionSpec, MimeTypeDescriptionEdit, EditMimeTypeDescriptionResult, MimeTypeDescription, MimeType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.MimeType.name(), SecurityRoles.Description.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("MimeTypeName", FieldType.MIME_TYPE, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditMimeTypeDescriptionCommand */
    public EditMimeTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditMimeTypeDescriptionResult getResult() {
        return CoreResultFactory.getEditMimeTypeDescriptionResult();
    }

    @Override
    public MimeTypeDescriptionEdit getEdit() {
        return CoreEditFactory.getMimeTypeDescriptionEdit();
    }

    @Override
    public MimeTypeDescription getEntity(EditMimeTypeDescriptionResult result) {
        var mimeTypeControl = Session.getModelController(MimeTypeControl.class);
        MimeTypeDescription mimeTypeDescription = null;
        var mimeTypeName = spec.getMimeTypeName();
        var mimeType = mimeTypeControl.getMimeTypeByName(mimeTypeName);

        if(mimeType != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    mimeTypeDescription = mimeTypeControl.getMimeTypeDescription(mimeType, language);
                } else { // EditMode.UPDATE
                    mimeTypeDescription = mimeTypeControl.getMimeTypeDescriptionForUpdate(mimeType, language);
                }

                if(mimeTypeDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownMimeTypeDescription.name(), mimeTypeName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownMimeTypeName.name(), mimeTypeName);
        }

        return mimeTypeDescription;
    }

    @Override
    public MimeType getLockEntity(MimeTypeDescription mimeTypeDescription) {
        return mimeTypeDescription.getMimeType();
    }

    @Override
    public void fillInResult(EditMimeTypeDescriptionResult result, MimeTypeDescription mimeTypeDescription) {
        var mimeTypeControl = Session.getModelController(MimeTypeControl.class);

        result.setMimeTypeDescription(mimeTypeControl.getMimeTypeDescriptionTransfer(getUserVisit(), mimeTypeDescription));
    }

    @Override
    public void doLock(MimeTypeDescriptionEdit edit, MimeTypeDescription mimeTypeDescription) {
        edit.setDescription(mimeTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(MimeTypeDescription mimeTypeDescription) {
        var mimeTypeControl = Session.getModelController(MimeTypeControl.class);
        var mimeTypeDescriptionValue = mimeTypeControl.getMimeTypeDescriptionValue(mimeTypeDescription);

        mimeTypeDescriptionValue.setDescription(edit.getDescription());

        mimeTypeControl.updateMimeTypeDescriptionFromValue(mimeTypeDescriptionValue, getPartyPK());
    }

}
