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

package com.echothree.control.user.filter.server.command;

import com.echothree.control.user.filter.common.edit.FilterEditFactory;
import com.echothree.control.user.filter.common.edit.FilterKindDescriptionEdit;
import com.echothree.control.user.filter.common.form.EditFilterKindDescriptionForm;
import com.echothree.control.user.filter.common.result.EditFilterKindDescriptionResult;
import com.echothree.control.user.filter.common.result.FilterResultFactory;
import com.echothree.control.user.filter.common.spec.FilterKindDescriptionSpec;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.filter.server.entity.FilterKind;
import com.echothree.model.data.filter.server.entity.FilterKindDescription;
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
public class EditFilterKindDescriptionCommand
        extends BaseAbstractEditCommand<FilterKindDescriptionSpec, FilterKindDescriptionEdit, EditFilterKindDescriptionResult, FilterKindDescription, FilterKind> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.FilterKind.name(), SecurityRoles.Description.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("FilterKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditFilterKindDescriptionCommand */
    public EditFilterKindDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditFilterKindDescriptionResult getResult() {
        return FilterResultFactory.getEditFilterKindDescriptionResult();
    }

    @Override
    public FilterKindDescriptionEdit getEdit() {
        return FilterEditFactory.getFilterKindDescriptionEdit();
    }

    @Override
    public FilterKindDescription getEntity(EditFilterKindDescriptionResult result) {
        var filterControl = Session.getModelController(FilterControl.class);
        FilterKindDescription filterKindDescription = null;
        var filterKindName = spec.getFilterKindName();
        var filterKind = filterControl.getFilterKindByName(filterKindName);

        if(filterKind != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    filterKindDescription = filterControl.getFilterKindDescription(filterKind, language);
                } else { // EditMode.UPDATE
                    filterKindDescription = filterControl.getFilterKindDescriptionForUpdate(filterKind, language);
                }

                if(filterKindDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownFilterKindDescription.name(), filterKindName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownFilterKindName.name(), filterKindName);
        }

        return filterKindDescription;
    }

    @Override
    public FilterKind getLockEntity(FilterKindDescription filterKindDescription) {
        return filterKindDescription.getFilterKind();
    }

    @Override
    public void fillInResult(EditFilterKindDescriptionResult result, FilterKindDescription filterKindDescription) {
        var filterControl = Session.getModelController(FilterControl.class);

        result.setFilterKindDescription(filterControl.getFilterKindDescriptionTransfer(getUserVisit(), filterKindDescription));
    }

    @Override
    public void doLock(FilterKindDescriptionEdit edit, FilterKindDescription filterKindDescription) {
        edit.setDescription(filterKindDescription.getDescription());
    }

    @Override
    public void doUpdate(FilterKindDescription filterKindDescription) {
        var filterControl = Session.getModelController(FilterControl.class);
        var filterKindDescriptionValue = filterControl.getFilterKindDescriptionValue(filterKindDescription);

        filterKindDescriptionValue.setDescription(edit.getDescription());

        filterControl.updateFilterKindDescriptionFromValue(filterKindDescriptionValue, getPartyPK());
    }

}
