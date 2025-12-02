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

import com.echothree.control.user.filter.common.form.CreateFilterStepForm;
import com.echothree.control.user.filter.common.result.FilterResultFactory;
import com.echothree.model.control.filter.server.logic.FilterStepLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class CreateFilterStepCommand
        extends BaseSimpleCommand<CreateFilterStepForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.Filter.name(), SecurityRoles.FilterStep.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("FilterKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FilterTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FilterName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FilterStepName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FilterItemSelectorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }
    
    /** Creates a new instance of CreateFilterStepCommand */
    public CreateFilterStepCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = FilterResultFactory.getCreateFilterStepResult();
        var filterKindName = form.getFilterKindName();
        var filterTypeName = form.getFilterTypeName();
        var filterName = form.getFilterName();
        var filterStepName = form.getFilterStepName();
        var filterItemSelectorName = form.getFilterItemSelectorName();
        var description = form.getDescription();

        var filterStep = FilterStepLogic.getInstance().createFilterStep(this, filterKindName, filterTypeName,
                filterName, filterStepName, filterItemSelectorName, getPreferredLanguage(), description, getPartyPK());

        if(filterStep != null) {
            var filterStepDetail = filterStep.getLastDetail();
            var filterDetail = filterStepDetail.getFilter().getLastDetail();
            var filterTypeDetail = filterDetail.getFilterType().getLastDetail();

            result.setEntityRef(filterStep.getPrimaryKey().getEntityRef());
            result.setFilterKindName(filterTypeDetail.getFilterKind().getLastDetail().getFilterKindName());
            result.setFilterTypeName(filterTypeDetail.getFilterTypeName());
            result.setFilterName(filterDetail.getFilterName());
            result.setFilterStepName(filterStepDetail.getFilterStepName());
        }

        return result;
    }
    
}
