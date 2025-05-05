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

import com.echothree.control.user.core.common.edit.ColorDescriptionEdit;
import com.echothree.control.user.core.common.edit.CoreEditFactory;
import com.echothree.control.user.core.common.form.EditColorDescriptionForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditColorDescriptionResult;
import com.echothree.control.user.core.common.spec.ColorDescriptionSpec;
import com.echothree.model.control.core.server.control.ColorControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.Color;
import com.echothree.model.data.core.server.entity.ColorDescription;
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

public class EditColorDescriptionCommand
        extends BaseAbstractEditCommand<ColorDescriptionSpec, ColorDescriptionEdit, EditColorDescriptionResult, ColorDescription, Color> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Color.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ColorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditColorDescriptionCommand */
    public EditColorDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditColorDescriptionResult getResult() {
        return CoreResultFactory.getEditColorDescriptionResult();
    }

    @Override
    public ColorDescriptionEdit getEdit() {
        return CoreEditFactory.getColorDescriptionEdit();
    }

    @Override
    public ColorDescription getEntity(EditColorDescriptionResult result) {
        var colorControl = Session.getModelController(ColorControl.class);
        ColorDescription colorDescription = null;
        var colorName = spec.getColorName();
        var color = colorControl.getColorByName(colorName);

        if(color != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    colorDescription = colorControl.getColorDescription(color, language);
                } else { // EditMode.UPDATE
                    colorDescription = colorControl.getColorDescriptionForUpdate(color, language);
                }

                if(colorDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownColorDescription.name(), colorName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownColorName.name(), colorName);
        }

        return colorDescription;
    }

    @Override
    public Color getLockEntity(ColorDescription colorDescription) {
        return colorDescription.getColor();
    }

    @Override
    public void fillInResult(EditColorDescriptionResult result, ColorDescription colorDescription) {
        var colorControl = Session.getModelController(ColorControl.class);

        result.setColorDescription(colorControl.getColorDescriptionTransfer(getUserVisit(), colorDescription));
    }

    @Override
    public void doLock(ColorDescriptionEdit edit, ColorDescription colorDescription) {
        edit.setDescription(colorDescription.getDescription());
    }

    @Override
    public void doUpdate(ColorDescription colorDescription) {
        var colorControl = Session.getModelController(ColorControl.class);
        var colorDescriptionValue = colorControl.getColorDescriptionValue(colorDescription);
        colorDescriptionValue.setDescription(edit.getDescription());

        colorControl.updateColorDescriptionFromValue(colorDescriptionValue, getPartyPK());
    }
    
}
