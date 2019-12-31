// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.control.user.scale.server.command;

import com.echothree.control.user.scale.common.edit.ScaleEditFactory;
import com.echothree.control.user.scale.common.edit.ScaleTypeDescriptionEdit;
import com.echothree.control.user.scale.common.form.EditScaleTypeDescriptionForm;
import com.echothree.control.user.scale.common.result.EditScaleTypeDescriptionResult;
import com.echothree.control.user.scale.common.result.ScaleResultFactory;
import com.echothree.control.user.scale.common.spec.ScaleTypeDescriptionSpec;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.scale.server.ScaleControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.scale.server.entity.ScaleType;
import com.echothree.model.data.scale.server.entity.ScaleTypeDescription;
import com.echothree.model.data.scale.server.value.ScaleTypeDescriptionValue;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditScaleTypeDescriptionCommand
        extends BaseAbstractEditCommand<ScaleTypeDescriptionSpec, ScaleTypeDescriptionEdit, EditScaleTypeDescriptionResult, ScaleTypeDescription, ScaleType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ScaleType.name(), SecurityRoles.Description.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ScaleTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }

    /** Creates a new instance of EditScaleTypeDescriptionCommand */
    public EditScaleTypeDescriptionCommand(UserVisitPK userVisitPK, EditScaleTypeDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditScaleTypeDescriptionResult getResult() {
        return ScaleResultFactory.getEditScaleTypeDescriptionResult();
    }

    @Override
    public ScaleTypeDescriptionEdit getEdit() {
        return ScaleEditFactory.getScaleTypeDescriptionEdit();
    }

    @Override
    public ScaleTypeDescription getEntity(EditScaleTypeDescriptionResult result) {
        var scaleControl = (ScaleControl)Session.getModelController(ScaleControl.class);
        ScaleTypeDescription scaleTypeDescription = null;
        String scaleTypeName = spec.getScaleTypeName();
        ScaleType scaleType = scaleControl.getScaleTypeByName(scaleTypeName);

        if(scaleType != null) {
            var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String languageIsoName = spec.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    scaleTypeDescription = scaleControl.getScaleTypeDescription(scaleType, language);
                } else { // EditMode.UPDATE
                    scaleTypeDescription = scaleControl.getScaleTypeDescriptionForUpdate(scaleType, language);
                }

                if(scaleTypeDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownScaleTypeDescription.name(), scaleTypeName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownScaleTypeName.name(), scaleTypeName);
        }

        return scaleTypeDescription;
    }

    @Override
    public ScaleType getLockEntity(ScaleTypeDescription scaleTypeDescription) {
        return scaleTypeDescription.getScaleType();
    }

    @Override
    public void fillInResult(EditScaleTypeDescriptionResult result, ScaleTypeDescription scaleTypeDescription) {
        var scaleControl = (ScaleControl)Session.getModelController(ScaleControl.class);

        result.setScaleTypeDescription(scaleControl.getScaleTypeDescriptionTransfer(getUserVisit(), scaleTypeDescription));
    }

    @Override
    public void doLock(ScaleTypeDescriptionEdit edit, ScaleTypeDescription scaleTypeDescription) {
        edit.setDescription(scaleTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(ScaleTypeDescription scaleTypeDescription) {
        var scaleControl = (ScaleControl)Session.getModelController(ScaleControl.class);
        ScaleTypeDescriptionValue scaleTypeDescriptionValue = scaleControl.getScaleTypeDescriptionValue(scaleTypeDescription);

        scaleTypeDescriptionValue.setDescription(edit.getDescription());

        scaleControl.updateScaleTypeDescriptionFromValue(scaleTypeDescriptionValue, getPartyPK());
    }

}
