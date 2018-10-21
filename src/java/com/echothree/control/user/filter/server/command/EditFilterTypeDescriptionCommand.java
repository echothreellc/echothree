// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.control.user.filter.remote.edit.FilterEditFactory;
import com.echothree.control.user.filter.remote.edit.FilterTypeDescriptionEdit;
import com.echothree.control.user.filter.remote.form.EditFilterTypeDescriptionForm;
import com.echothree.control.user.filter.remote.result.EditFilterTypeDescriptionResult;
import com.echothree.control.user.filter.remote.result.FilterResultFactory;
import com.echothree.control.user.filter.remote.spec.FilterTypeDescriptionSpec;
import com.echothree.model.control.filter.server.FilterControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.filter.server.entity.FilterKind;
import com.echothree.model.data.filter.server.entity.FilterType;
import com.echothree.model.data.filter.server.entity.FilterTypeDescription;
import com.echothree.model.data.filter.server.value.FilterTypeDescriptionValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.EditMode;
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
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.FilterType.name(), SecurityRoles.Description.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("FilterTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }

    /** Creates a new instance of EditFilterTypeDescriptionCommand */
    public EditFilterTypeDescriptionCommand(UserVisitPK userVisitPK, EditFilterTypeDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
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
        FilterControl filterControl = (FilterControl)Session.getModelController(FilterControl.class);
        FilterTypeDescription filterTypeDescription = null;
        String filterKindName = spec.getFilterKindName();
        FilterKind filterKind = filterControl.getFilterKindByName(filterKindName);

        if(filterKind != null) {
            String filterTypeName = spec.getFilterTypeName();
            FilterType filterType = filterControl.getFilterTypeByName(filterKind, filterTypeName);

            if(filterType != null) {
                PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                String languageIsoName = spec.getLanguageIsoName();
                Language language = partyControl.getLanguageByIsoName(languageIsoName);

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
        FilterControl filterControl = (FilterControl)Session.getModelController(FilterControl.class);

        result.setFilterTypeDescription(filterControl.getFilterTypeDescriptionTransfer(getUserVisit(), filterTypeDescription));
    }

    @Override
    public void doLock(FilterTypeDescriptionEdit edit, FilterTypeDescription filterTypeDescription) {
        edit.setDescription(filterTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(FilterTypeDescription filterTypeDescription) {
        FilterControl filterControl = (FilterControl)Session.getModelController(FilterControl.class);
        FilterTypeDescriptionValue filterTypeDescriptionValue = filterControl.getFilterTypeDescriptionValue(filterTypeDescription);

        filterTypeDescriptionValue.setDescription(edit.getDescription());

        filterControl.updateFilterTypeDescriptionFromValue(filterTypeDescriptionValue, getPartyPK());
    }

}
