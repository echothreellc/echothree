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

package com.echothree.control.user.filter.server.command;

import com.echothree.control.user.filter.common.edit.FilterEditFactory;
import com.echothree.control.user.filter.common.edit.FilterTypeDescriptionEdit;
import com.echothree.control.user.filter.common.form.EditFilterTypeDescriptionForm;
import com.echothree.control.user.filter.common.result.EditFilterTypeDescriptionResult;
import com.echothree.control.user.filter.common.result.FilterResultFactory;
import com.echothree.control.user.filter.common.spec.FilterTypeDescriptionSpec;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.filter.server.entity.FilterType;
import com.echothree.model.data.filter.server.entity.FilterTypeDescription;
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

public class EditFilterTypeDescriptionCommand
        extends BaseAbstractEditCommand<FilterTypeDescriptionSpec, FilterTypeDescriptionEdit, EditFilterTypeDescriptionResult, FilterTypeDescription, FilterType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.FilterType.name(), SecurityRoles.Description.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("FilterTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditFilterTypeDescriptionCommand */
    public EditFilterTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditFilterTypeDescriptionResult getResult() {
        return FilterResultFactory.getEditFilterTypeDescriptionResult();
    }

    @Override
    public FilterTypeDescriptionEdit getEdit() {
        return FilterEditFactory.getFilterTypeDescriptionEdit();
    }

    @Override
    public FilterTypeDescription getEntity(EditFilterTypeDescriptionResult result) {
        var filterControl = Session.getModelController(FilterControl.class);
        FilterTypeDescription filterTypeDescription = null;
        var filterKindName = spec.getFilterKindName();
        var filterKind = filterControl.getFilterKindByName(filterKindName);

        if(filterKind != null) {
            var filterTypeName = spec.getFilterTypeName();
            var filterType = filterControl.getFilterTypeByName(filterKind, filterTypeName);

            if(filterType != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                var languageIsoName = spec.getLanguageIsoName();
                var language = partyControl.getLanguageByIsoName(languageIsoName);

                if(language != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        filterTypeDescription = filterControl.getFilterTypeDescription(filterType, language);
                    } else { // EditMode.UPDATE
                        filterTypeDescription = filterControl.getFilterTypeDescriptionForUpdate(filterType, language);
                    }

                    if(filterTypeDescription == null) {
                        addExecutionError(ExecutionErrors.UnknownFilterTypeDescription.name(), filterKindName, filterTypeName, languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownFilterTypeName.name(), filterKindName, filterTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownFilterKindName.name(), filterKindName);
        }

        return filterTypeDescription;
    }

    @Override
    public FilterType getLockEntity(FilterTypeDescription filterTypeDescription) {
        return filterTypeDescription.getFilterType();
    }

    @Override
    public void fillInResult(EditFilterTypeDescriptionResult result, FilterTypeDescription filterTypeDescription) {
        var filterControl = Session.getModelController(FilterControl.class);

        result.setFilterTypeDescription(filterControl.getFilterTypeDescriptionTransfer(getUserVisit(), filterTypeDescription));
    }

    @Override
    public void doLock(FilterTypeDescriptionEdit edit, FilterTypeDescription filterTypeDescription) {
        edit.setDescription(filterTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(FilterTypeDescription filterTypeDescription) {
        var filterControl = Session.getModelController(FilterControl.class);
        var filterTypeDescriptionValue = filterControl.getFilterTypeDescriptionValue(filterTypeDescription);

        filterTypeDescriptionValue.setDescription(edit.getDescription());

        filterControl.updateFilterTypeDescriptionFromValue(filterTypeDescriptionValue, getPartyPK());
    }

}
