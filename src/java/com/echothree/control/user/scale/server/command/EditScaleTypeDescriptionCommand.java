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

package com.echothree.control.user.scale.server.command;

import com.echothree.control.user.scale.common.edit.ScaleEditFactory;
import com.echothree.control.user.scale.common.edit.ScaleTypeDescriptionEdit;
import com.echothree.control.user.scale.common.form.EditScaleTypeDescriptionForm;
import com.echothree.control.user.scale.common.result.EditScaleTypeDescriptionResult;
import com.echothree.control.user.scale.common.result.ScaleResultFactory;
import com.echothree.control.user.scale.common.spec.ScaleTypeDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.scale.server.control.ScaleControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.scale.server.entity.ScaleType;
import com.echothree.model.data.scale.server.entity.ScaleTypeDescription;
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
import javax.enterprise.context.Dependent;

@Dependent
public class EditScaleTypeDescriptionCommand
        extends BaseAbstractEditCommand<ScaleTypeDescriptionSpec, ScaleTypeDescriptionEdit, EditScaleTypeDescriptionResult, ScaleTypeDescription, ScaleType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ScaleType.name(), SecurityRoles.Description.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ScaleTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditScaleTypeDescriptionCommand */
    public EditScaleTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
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
        var scaleControl = Session.getModelController(ScaleControl.class);
        ScaleTypeDescription scaleTypeDescription = null;
        var scaleTypeName = spec.getScaleTypeName();
        var scaleType = scaleControl.getScaleTypeByName(scaleTypeName);

        if(scaleType != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

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
        var scaleControl = Session.getModelController(ScaleControl.class);

        result.setScaleTypeDescription(scaleControl.getScaleTypeDescriptionTransfer(getUserVisit(), scaleTypeDescription));
    }

    @Override
    public void doLock(ScaleTypeDescriptionEdit edit, ScaleTypeDescription scaleTypeDescription) {
        edit.setDescription(scaleTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(ScaleTypeDescription scaleTypeDescription) {
        var scaleControl = Session.getModelController(ScaleControl.class);
        var scaleTypeDescriptionValue = scaleControl.getScaleTypeDescriptionValue(scaleTypeDescription);

        scaleTypeDescriptionValue.setDescription(edit.getDescription());

        scaleControl.updateScaleTypeDescriptionFromValue(scaleTypeDescriptionValue, getPartyPK());
    }

}
