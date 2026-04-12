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

import com.echothree.control.user.filter.common.edit.FilterAdjustmentDescriptionEdit;
import com.echothree.control.user.filter.common.edit.FilterEditFactory;
import com.echothree.control.user.filter.common.result.EditFilterAdjustmentDescriptionResult;
import com.echothree.control.user.filter.common.result.FilterResultFactory;
import com.echothree.control.user.filter.common.spec.FilterAdjustmentDescriptionSpec;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.filter.server.entity.FilterAdjustment;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentDescription;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditFilterAdjustmentDescriptionCommand
        extends BaseAbstractEditCommand<FilterAdjustmentDescriptionSpec, FilterAdjustmentDescriptionEdit, EditFilterAdjustmentDescriptionResult, FilterAdjustmentDescription, FilterAdjustment> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.FilterAdjustment.name(), SecurityRoles.Description.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("FilterKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FilterAdjustmentName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    FilterControl filterControl;

    @Inject
    PartyControl partyControl;

    /** Creates a new instance of EditFilterAdjustmentDescriptionCommand */
    public EditFilterAdjustmentDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditFilterAdjustmentDescriptionResult getResult() {
        return FilterResultFactory.getEditFilterAdjustmentDescriptionResult();
    }

    @Override
    public FilterAdjustmentDescriptionEdit getEdit() {
        return FilterEditFactory.getFilterAdjustmentDescriptionEdit();
    }

    @Override
    public FilterAdjustmentDescription getEntity(EditFilterAdjustmentDescriptionResult result) {
        FilterAdjustmentDescription filterAdjustmentDescription = null;
        var filterKindName = spec.getFilterKindName();
        var filterKind = filterControl.getFilterKindByName(filterKindName);

        if(filterKind != null) {
            var filterAdjustmentName = spec.getFilterAdjustmentName();
            var filterAdjustment = filterControl.getFilterAdjustmentByName(filterKind, filterAdjustmentName);

            if(filterAdjustment != null) {
                var languageIsoName = spec.getLanguageIsoName();
                var language = partyControl.getLanguageByIsoName(languageIsoName);

                if(language != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        filterAdjustmentDescription = filterControl.getFilterAdjustmentDescription(filterAdjustment, language);
                    } else { // EditMode.UPDATE
                        filterAdjustmentDescription = filterControl.getFilterAdjustmentDescriptionForUpdate(filterAdjustment, language);
                    }

                    if(filterAdjustmentDescription == null) {
                        addExecutionError(ExecutionErrors.UnknownFilterAdjustmentDescription.name(), filterKindName, filterAdjustmentName, languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownFilterAdjustmentName.name(), filterKindName, filterAdjustmentName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownFilterKindName.name(), filterKindName);
        }

        return filterAdjustmentDescription;
    }

    @Override
    public FilterAdjustment getLockEntity(FilterAdjustmentDescription filterAdjustmentDescription) {
        return filterAdjustmentDescription.getFilterAdjustment();
    }

    @Override
    public void fillInResult(EditFilterAdjustmentDescriptionResult result, FilterAdjustmentDescription filterAdjustmentDescription) {
        result.setFilterAdjustmentDescription(filterControl.getFilterAdjustmentDescriptionTransfer(getUserVisit(), filterAdjustmentDescription));
    }

    @Override
    public void doLock(FilterAdjustmentDescriptionEdit edit, FilterAdjustmentDescription filterAdjustmentDescription) {
        edit.setDescription(filterAdjustmentDescription.getDescription());
    }

    @Override
    public void doUpdate(FilterAdjustmentDescription filterAdjustmentDescription) {
        var filterAdjustmentDescriptionValue = filterControl.getFilterAdjustmentDescriptionValue(filterAdjustmentDescription);

        if(filterAdjustmentDescriptionValue != null) {
            filterAdjustmentDescriptionValue.setDescription(edit.getDescription());

            filterControl.updateFilterAdjustmentDescriptionFromValue(filterAdjustmentDescriptionValue, getPartyPK());
        }
    }

}
