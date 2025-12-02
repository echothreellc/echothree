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

package com.echothree.control.user.selector.server.command;

import com.echothree.control.user.selector.common.edit.SelectorEditFactory;
import com.echothree.control.user.selector.common.edit.SelectorTypeDescriptionEdit;
import com.echothree.control.user.selector.common.form.EditSelectorTypeDescriptionForm;
import com.echothree.control.user.selector.common.result.EditSelectorTypeDescriptionResult;
import com.echothree.control.user.selector.common.result.SelectorResultFactory;
import com.echothree.control.user.selector.common.spec.SelectorTypeDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.data.selector.server.entity.SelectorType;
import com.echothree.model.data.selector.server.entity.SelectorTypeDescription;
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
public class EditSelectorTypeDescriptionCommand
        extends BaseAbstractEditCommand<SelectorTypeDescriptionSpec, SelectorTypeDescriptionEdit, EditSelectorTypeDescriptionResult, SelectorTypeDescription, SelectorType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.SelectorType.name(), SecurityRoles.Description.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SelectorTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditSelectorTypeDescriptionCommand */
    public EditSelectorTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditSelectorTypeDescriptionResult getResult() {
        return SelectorResultFactory.getEditSelectorTypeDescriptionResult();
    }

    @Override
    public SelectorTypeDescriptionEdit getEdit() {
        return SelectorEditFactory.getSelectorTypeDescriptionEdit();
    }

    @Override
    public SelectorTypeDescription getEntity(EditSelectorTypeDescriptionResult result) {
        var selectorControl = Session.getModelController(SelectorControl.class);
        SelectorTypeDescription selectorTypeDescription = null;
        var selectorKindName = spec.getSelectorKindName();
        var selectorKind = selectorControl.getSelectorKindByName(selectorKindName);

        if(selectorKind != null) {
            var selectorTypeName = spec.getSelectorTypeName();
            var selectorType = selectorControl.getSelectorTypeByName(selectorKind, selectorTypeName);

            if(selectorType != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                var languageIsoName = spec.getLanguageIsoName();
                var language = partyControl.getLanguageByIsoName(languageIsoName);

                if(language != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        selectorTypeDescription = selectorControl.getSelectorTypeDescription(selectorType, language);
                    } else { // EditMode.UPDATE
                        selectorTypeDescription = selectorControl.getSelectorTypeDescriptionForUpdate(selectorType, language);
                    }

                    if(selectorTypeDescription == null) {
                        addExecutionError(ExecutionErrors.UnknownSelectorTypeDescription.name(), selectorKindName, selectorTypeName, languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownSelectorTypeName.name(), selectorKindName, selectorTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownSelectorKindName.name(), selectorKindName);
        }

        return selectorTypeDescription;
    }

    @Override
    public SelectorType getLockEntity(SelectorTypeDescription selectorTypeDescription) {
        return selectorTypeDescription.getSelectorType();
    }

    @Override
    public void fillInResult(EditSelectorTypeDescriptionResult result, SelectorTypeDescription selectorTypeDescription) {
        var selectorControl = Session.getModelController(SelectorControl.class);

        result.setSelectorTypeDescription(selectorControl.getSelectorTypeDescriptionTransfer(getUserVisit(), selectorTypeDescription));
    }

    @Override
    public void doLock(SelectorTypeDescriptionEdit edit, SelectorTypeDescription selectorTypeDescription) {
        edit.setDescription(selectorTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(SelectorTypeDescription selectorTypeDescription) {
        var selectorControl = Session.getModelController(SelectorControl.class);
        var selectorTypeDescriptionValue = selectorControl.getSelectorTypeDescriptionValue(selectorTypeDescription);

        selectorTypeDescriptionValue.setDescription(edit.getDescription());

        selectorControl.updateSelectorTypeDescriptionFromValue(selectorTypeDescriptionValue, getPartyPK());
    }

}
