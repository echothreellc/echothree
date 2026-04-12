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
import com.echothree.control.user.filter.common.edit.FilterStepDescriptionEdit;
import com.echothree.control.user.filter.common.result.EditFilterStepDescriptionResult;
import com.echothree.control.user.filter.common.result.FilterResultFactory;
import com.echothree.control.user.filter.common.spec.FilterStepDescriptionSpec;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.filter.server.entity.FilterStep;
import com.echothree.model.data.filter.server.entity.FilterStepDescription;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditFilterStepDescriptionCommand
        extends BaseAbstractEditCommand<FilterStepDescriptionSpec, FilterStepDescriptionEdit, EditFilterStepDescriptionResult, FilterStepDescription, FilterStep> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.Filter.name(), SecurityRoles.FilterStep.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("FilterKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FilterTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FilterName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FilterStepName", FieldType.ENTITY_NAME, true, null, null),
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

    /** Creates a new instance of EditFilterStepDescriptionCommand */
    public EditFilterStepDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditFilterStepDescriptionResult getResult() {
        return FilterResultFactory.getEditFilterStepDescriptionResult();
    }

    @Override
    public FilterStepDescriptionEdit getEdit() {
        return FilterEditFactory.getFilterStepDescriptionEdit();
    }

    @Override
    public FilterStepDescription getEntity(EditFilterStepDescriptionResult result) {
        FilterStepDescription filterStepDescription = null;
        var filterKindName = spec.getFilterKindName();
        var filterKind = filterControl.getFilterKindByName(filterKindName);

        if(filterKind != null) {
            var filterTypeName = spec.getFilterTypeName();
            var filterType = filterControl.getFilterTypeByName(filterKind, filterTypeName);

            if(filterType != null) {
                var filterName = spec.getFilterName();
                var filter = filterControl.getFilterByName(filterType, filterName);

                if(filter != null) {
                    var filterStepName = spec.getFilterStepName();
                    var filterStep = filterControl.getFilterStepByName(filter, filterStepName);

                    if(filterStep != null) {
                        var languageIsoName = spec.getLanguageIsoName();
                        var language = partyControl.getLanguageByIsoName(languageIsoName);

                        if(language != null) {
                            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                                filterStepDescription = filterControl.getFilterStepDescription(filterStep, language);
                            } else { // EditMode.UPDATE
                                filterStepDescription = filterControl.getFilterStepDescriptionForUpdate(filterStep, language);
                            }

                            if(filterStepDescription == null) {
                                addExecutionError(ExecutionErrors.UnknownFilterStepDescription.name(), languageIsoName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownFilterStepName.name(), filterStepName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownFilterName.name(), filterName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownFilterTypeName.name(), filterTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownFilterKindName.name(), filterKindName);
        }

        return filterStepDescription;
    }

    @Override
    public FilterStep getLockEntity(FilterStepDescription filterStepDescription) {
        return filterStepDescription.getFilterStep();
    }

    @Override
    public void fillInResult(EditFilterStepDescriptionResult result, FilterStepDescription filterStepDescription) {
        result.setFilterStepDescription(filterControl.getFilterStepDescriptionTransfer(getUserVisit(), filterStepDescription));
    }

    @Override
    public void doLock(FilterStepDescriptionEdit edit, FilterStepDescription filterStepDescription) {
        edit.setDescription(filterStepDescription.getDescription());
    }

    @Override
    public void doUpdate(FilterStepDescription filterStepDescription) {
        var filterStepDescriptionValue = filterControl.getFilterStepDescriptionValue(filterStepDescription);

        filterStepDescriptionValue.setDescription(edit.getDescription());

        filterControl.updateFilterStepDescriptionFromValue(filterStepDescriptionValue, getPartyPK());
    }
    
}
