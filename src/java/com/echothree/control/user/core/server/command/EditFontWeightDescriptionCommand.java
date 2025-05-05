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
import com.echothree.control.user.core.common.edit.FontWeightDescriptionEdit;
import com.echothree.control.user.core.common.form.EditFontWeightDescriptionForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditFontWeightDescriptionResult;
import com.echothree.control.user.core.common.spec.FontWeightDescriptionSpec;
import com.echothree.model.control.core.server.control.FontControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.FontWeight;
import com.echothree.model.data.core.server.entity.FontWeightDescription;
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

public class EditFontWeightDescriptionCommand
        extends BaseAbstractEditCommand<FontWeightDescriptionSpec, FontWeightDescriptionEdit, EditFontWeightDescriptionResult, FontWeightDescription, FontWeight> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.FontWeight.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("FontWeightName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditFontWeightDescriptionCommand */
    public EditFontWeightDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditFontWeightDescriptionResult getResult() {
        return CoreResultFactory.getEditFontWeightDescriptionResult();
    }

    @Override
    public FontWeightDescriptionEdit getEdit() {
        return CoreEditFactory.getFontWeightDescriptionEdit();
    }

    @Override
    public FontWeightDescription getEntity(EditFontWeightDescriptionResult result) {
        var fontControl = Session.getModelController(FontControl.class);
        FontWeightDescription fontWeightDescription = null;
        var fontWeightName = spec.getFontWeightName();
        var fontWeight = fontControl.getFontWeightByName(fontWeightName);

        if(fontWeight != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    fontWeightDescription = fontControl.getFontWeightDescription(fontWeight, language);
                } else { // EditMode.UPDATE
                    fontWeightDescription = fontControl.getFontWeightDescriptionForUpdate(fontWeight, language);
                }

                if(fontWeightDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownFontWeightDescription.name(), fontWeightName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownFontWeightName.name(), fontWeightName);
        }

        return fontWeightDescription;
    }

    @Override
    public FontWeight getLockEntity(FontWeightDescription fontWeightDescription) {
        return fontWeightDescription.getFontWeight();
    }

    @Override
    public void fillInResult(EditFontWeightDescriptionResult result, FontWeightDescription fontWeightDescription) {
        var fontControl = Session.getModelController(FontControl.class);

        result.setFontWeightDescription(fontControl.getFontWeightDescriptionTransfer(getUserVisit(), fontWeightDescription));
    }

    @Override
    public void doLock(FontWeightDescriptionEdit edit, FontWeightDescription fontWeightDescription) {
        edit.setDescription(fontWeightDescription.getDescription());
    }

    @Override
    public void doUpdate(FontWeightDescription fontWeightDescription) {
        var fontControl = Session.getModelController(FontControl.class);
        var fontWeightDescriptionValue = fontControl.getFontWeightDescriptionValue(fontWeightDescription);
        fontWeightDescriptionValue.setDescription(edit.getDescription());

        fontControl.updateFontWeightDescriptionFromValue(fontWeightDescriptionValue, getPartyPK());
    }
    
}
