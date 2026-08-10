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

package com.echothree.control.user.picklist.server.command;

import com.echothree.control.user.picklist.common.form.GetPicklistAliasesForm;
import com.echothree.control.user.picklist.common.result.PicklistResultFactory;
import com.echothree.control.user.picklist.server.command.util.PicklistAliasUtil;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.picklist.server.control.PicklistControl;
import com.echothree.model.control.picklist.server.logic.PicklistLogic;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.picklist.server.entity.Picklist;
import com.echothree.model.data.picklist.server.entity.PicklistAlias;
import com.echothree.model.data.picklist.server.factory.PicklistAliasFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetPicklistAliasesCommand
        extends BasePaginatedMultipleEntitiesCommand<PicklistAlias, GetPicklistAliasesForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PicklistTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PicklistName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    @Inject
    PicklistControl picklistControl;

    @Inject
    PicklistLogic picklistLogic;

    /** Creates a new instance of GetPicklistAliasesCommand */
    public GetPicklistAliasesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected CommandSecurityDefinition getCommandSecurityDefinition() {
        return new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(PicklistAliasUtil.getInstance().getSecurityRoleGroupNameByPicklistTypeSpec(form), SecurityRoles.List.name())
                ))
        ));
    }

    Picklist picklist;

    @Override
    protected void handleForm() {
        var picklistTypeName = form.getPicklistTypeName();
        var picklistName = form.getPicklistName();

        picklist = picklistLogic.getPicklistByName(this, picklistTypeName, picklistName);
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : picklistControl.countPicklistAliasesBy(picklist);
    }

    @Override
    protected Collection<PicklistAlias> getEntities() {
        return hasExecutionErrors() ? null : picklistControl.getPicklistAliasesByPicklist(picklist);
    }

    @Override
    protected BaseResult getResult(Collection<PicklistAlias> entities) {
        var result = PicklistResultFactory.getGetPicklistAliasesResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            result.setPicklist(picklistControl.getPicklistTransfer(userVisit, picklist));

            if(session.hasLimit(PicklistAliasFactory.class)) {
                result.setPicklistAliasCount(getTotalEntities());
            }

            result.setPicklistAliases(picklistControl.getPicklistAliasTransfersByPicklist(userVisit, picklist));
        }

        return result;
    }

}
