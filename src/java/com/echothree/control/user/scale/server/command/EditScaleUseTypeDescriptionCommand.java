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

package com.echothree.control.user.scale.server.command;

import com.echothree.control.user.scale.common.edit.ScaleEditFactory;
import com.echothree.control.user.scale.common.edit.ScaleUseTypeDescriptionEdit;
import com.echothree.control.user.scale.common.form.EditScaleUseTypeDescriptionForm;
import com.echothree.control.user.scale.common.result.EditScaleUseTypeDescriptionResult;
import com.echothree.control.user.scale.common.result.ScaleResultFactory;
import com.echothree.control.user.scale.common.spec.ScaleUseTypeDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.scale.server.control.ScaleControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.scale.server.entity.ScaleUseType;
import com.echothree.model.data.scale.server.entity.ScaleUseTypeDescription;
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

public class EditScaleUseTypeDescriptionCommand
        extends BaseAbstractEditCommand<ScaleUseTypeDescriptionSpec, ScaleUseTypeDescriptionEdit, EditScaleUseTypeDescriptionResult, ScaleUseTypeDescription, ScaleUseType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ScaleUseType.name(), SecurityRoles.Description.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ScaleUseTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditScaleUseTypeDescriptionCommand */
    public EditScaleUseTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditScaleUseTypeDescriptionResult getResult() {
        return ScaleResultFactory.getEditScaleUseTypeDescriptionResult();
    }

    @Override
    public ScaleUseTypeDescriptionEdit getEdit() {
        return ScaleEditFactory.getScaleUseTypeDescriptionEdit();
    }

    @Override
    public ScaleUseTypeDescription getEntity(EditScaleUseTypeDescriptionResult result) {
        var scaleControl = Session.getModelController(ScaleControl.class);
        ScaleUseTypeDescription scaleUseTypeDescription = null;
        var scaleUseTypeName = spec.getScaleUseTypeName();
        var scaleUseType = scaleControl.getScaleUseTypeByName(scaleUseTypeName);

        if(scaleUseType != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    scaleUseTypeDescription = scaleControl.getScaleUseTypeDescription(scaleUseType, language);
                } else { // EditMode.UPDATE
                    scaleUseTypeDescription = scaleControl.getScaleUseTypeDescriptionForUpdate(scaleUseType, language);
                }

                if(scaleUseTypeDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownScaleUseTypeDescription.name(), scaleUseTypeName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownScaleUseTypeName.name(), scaleUseTypeName);
        }

        return scaleUseTypeDescription;
    }

    @Override
    public ScaleUseType getLockEntity(ScaleUseTypeDescription scaleUseTypeDescription) {
        return scaleUseTypeDescription.getScaleUseType();
    }

    @Override
    public void fillInResult(EditScaleUseTypeDescriptionResult result, ScaleUseTypeDescription scaleUseTypeDescription) {
        var scaleControl = Session.getModelController(ScaleControl.class);

        result.setScaleUseTypeDescription(scaleControl.getScaleUseTypeDescriptionTransfer(getUserVisit(), scaleUseTypeDescription));
    }

    @Override
    public void doLock(ScaleUseTypeDescriptionEdit edit, ScaleUseTypeDescription scaleUseTypeDescription) {
        edit.setDescription(scaleUseTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(ScaleUseTypeDescription scaleUseTypeDescription) {
        var scaleControl = Session.getModelController(ScaleControl.class);
        var scaleUseTypeDescriptionValue = scaleControl.getScaleUseTypeDescriptionValue(scaleUseTypeDescription);

        scaleUseTypeDescriptionValue.setDescription(edit.getDescription());

        scaleControl.updateScaleUseTypeDescriptionFromValue(scaleUseTypeDescriptionValue, getPartyPK());
    }

}
