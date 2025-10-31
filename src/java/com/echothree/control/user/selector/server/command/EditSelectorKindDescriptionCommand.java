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
import com.echothree.control.user.selector.common.edit.SelectorKindDescriptionEdit;
import com.echothree.control.user.selector.common.form.EditSelectorKindDescriptionForm;
import com.echothree.control.user.selector.common.result.EditSelectorKindDescriptionResult;
import com.echothree.control.user.selector.common.result.SelectorResultFactory;
import com.echothree.control.user.selector.common.spec.SelectorKindDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.data.selector.server.entity.SelectorKind;
import com.echothree.model.data.selector.server.entity.SelectorKindDescription;
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
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditSelectorKindDescriptionCommand
        extends BaseAbstractEditCommand<SelectorKindDescriptionSpec, SelectorKindDescriptionEdit, EditSelectorKindDescriptionResult, SelectorKindDescription, SelectorKind> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.SelectorKind.name(), SecurityRoles.Description.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SelectorKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditSelectorKindDescriptionCommand */
    public EditSelectorKindDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditSelectorKindDescriptionResult getResult() {
        return SelectorResultFactory.getEditSelectorKindDescriptionResult();
    }

    @Override
    public SelectorKindDescriptionEdit getEdit() {
        return SelectorEditFactory.getSelectorKindDescriptionEdit();
    }

    @Override
    public SelectorKindDescription getEntity(EditSelectorKindDescriptionResult result) {
        var selectorControl = Session.getModelController(SelectorControl.class);
        SelectorKindDescription selectorKindDescription = null;
        var selectorKindName = spec.getSelectorKindName();
        var selectorKind = selectorControl.getSelectorKindByName(selectorKindName);

        if(selectorKind != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    selectorKindDescription = selectorControl.getSelectorKindDescription(selectorKind, language);
                } else { // EditMode.UPDATE
                    selectorKindDescription = selectorControl.getSelectorKindDescriptionForUpdate(selectorKind, language);
                }

                if(selectorKindDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownSelectorKindDescription.name(), selectorKindName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownSelectorKindName.name(), selectorKindName);
        }

        return selectorKindDescription;
    }

    @Override
    public SelectorKind getLockEntity(SelectorKindDescription selectorKindDescription) {
        return selectorKindDescription.getSelectorKind();
    }

    @Override
    public void fillInResult(EditSelectorKindDescriptionResult result, SelectorKindDescription selectorKindDescription) {
        var selectorControl = Session.getModelController(SelectorControl.class);

        result.setSelectorKindDescription(selectorControl.getSelectorKindDescriptionTransfer(getUserVisit(), selectorKindDescription));
    }

    @Override
    public void doLock(SelectorKindDescriptionEdit edit, SelectorKindDescription selectorKindDescription) {
        edit.setDescription(selectorKindDescription.getDescription());
    }

    @Override
    public void doUpdate(SelectorKindDescription selectorKindDescription) {
        var selectorControl = Session.getModelController(SelectorControl.class);
        var selectorKindDescriptionValue = selectorControl.getSelectorKindDescriptionValue(selectorKindDescription);

        selectorKindDescriptionValue.setDescription(edit.getDescription());

        selectorControl.updateSelectorKindDescriptionFromValue(selectorKindDescriptionValue, getPartyPK());
    }

}
